package org.apereo.cas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.CurrentCredentialsAndAuthentication;
import org.apereo.cas.authentication.DefaultAuthenticationBuilder;
import org.apereo.cas.authentication.MixedPrincipalException;
import org.apereo.cas.authentication.PrincipalException;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.LogoutRequest;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAccessStrategyUtils;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.ServiceContext;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.UnauthorizedProxyingException;
import org.apereo.cas.services.UnauthorizedSsoServiceException;
import org.apereo.cas.support.events.CasProxyGrantingTicketCreatedEvent;
import org.apereo.cas.support.events.CasProxyTicketGrantedEvent;
import org.apereo.cas.support.events.CasServiceTicketGrantedEvent;
import org.apereo.cas.support.events.CasServiceTicketValidatedEvent;
import org.apereo.cas.support.events.CasTicketGrantingTicketCreatedEvent;
import org.apereo.cas.support.events.CasTicketGrantingTicketDestroyedEvent;
import org.apereo.cas.ticket.AbstractTicketException;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.ServiceTicketFactory;
import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicketFactory;
import org.apereo.cas.ticket.UnrecognizableServiceForServiceTicketValidationException;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicketFactory;
import org.apereo.cas.ticket.proxy.ProxyTicket;
import org.apereo.cas.ticket.proxy.ProxyTicketFactory;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.ImmutableAssertion;
import org.apereo.inspektr.audit.annotation.Audit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Concrete implementation of a {@link CentralAuthenticationService}, and also the
 * central, organizing component of CAS's internal implementation.
 * This class is threadsafe.
 *
 * @author William G. Thompson, Jr.
 * @author Scott Battaglia
 * @author Dmitry Kopylenko
 * @author Misagh Moayyed
 * @since 3.0.0
 */
@Transactional(readOnly = false, transactionManager = "ticketTransactionManager")
public class CentralAuthenticationServiceImpl extends AbstractCentralAuthenticationService {

    private static final long serialVersionUID = -8943828074939533986L;

    /**
     * Instantiates a new Central authentication service impl.
     */
    public CentralAuthenticationServiceImpl() {
    }

    /**
     * Build the central authentication service implementation.
     *
     * @param ticketRegistry  the tickets registry.
     * @param ticketFactory   the ticket factory
     * @param servicesManager the services manager.
     * @param logoutManager   the logout manager.
     */
    public CentralAuthenticationServiceImpl(
            final TicketRegistry ticketRegistry,
            final TicketFactory ticketFactory,
            final ServicesManager servicesManager,
            final LogoutManager logoutManager) {

        super(ticketRegistry, ticketFactory, servicesManager, logoutManager);
    }

    /**
     * {@inheritDoc}
     * Destroy a TicketGrantingTicket and perform back channel logout. This has the effect of invalidating any
     * Ticket that was derived from the TicketGrantingTicket being destroyed. May throw an
     * {@link IllegalArgumentException} if the TicketGrantingTicket ID is null.
     *
     * @param ticketGrantingTicketId the id of the ticket we want to destroy
     * @return the logout requests.
     */
    @Audit(
            action = "TICKET_GRANTING_TICKET_DESTROYED",
            actionResolverName = "DESTROY_TICKET_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "DESTROY_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "DESTROY_TICKET_GRANTING_TICKET_TIMER")
    @Metered(name = "DESTROY_TICKET_GRANTING_TICKET_METER")
    @Counted(name = "DESTROY_TICKET_GRANTING_TICKET_COUNTER", monotonic = true)
    @Override
    public List<LogoutRequest> destroyTicketGrantingTicket(final String ticketGrantingTicketId) {
        try {
            logger.debug("Removing ticket [{}] from registry...", ticketGrantingTicketId);
            final TicketGrantingTicket ticket = getTicket(ticketGrantingTicketId, TicketGrantingTicket.class);
            logger.debug("Ticket found. Processing logout requests and then deleting the ticket...");

            CurrentCredentialsAndAuthentication.bindCurrent(ticket.getAuthentication());

            final List<LogoutRequest> logoutRequests = this.logoutManager.performLogout(ticket);
            this.ticketRegistry.deleteTicket(ticketGrantingTicketId);

            doPublishEvent(new CasTicketGrantingTicketDestroyedEvent(this, ticket));

            return logoutRequests;
        } catch (final InvalidTicketException e) {
            logger.debug("TicketGrantingTicket [{}] cannot be found in the ticket registry.", ticketGrantingTicketId);
        }
        return Collections.emptyList();
    }

    @Audit(
            action = "SERVICE_TICKET",
            actionResolverName = "GRANT_SERVICE_TICKET_RESOLVER",
            resourceResolverName = "GRANT_SERVICE_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "GRANT_SERVICE_TICKET_TIMER")
    @Metered(name = "GRANT_SERVICE_TICKET_METER")
    @Counted(name = "GRANT_SERVICE_TICKET_COUNTER", monotonic = true)
    @Override
    public ServiceTicket grantServiceTicket(
            final String ticketGrantingTicketId,
            final Service service, final AuthenticationResult authenticationResult)
            throws AuthenticationException, AbstractTicketException {

        final TicketGrantingTicket ticketGrantingTicket = getTicket(ticketGrantingTicketId, TicketGrantingTicket.class);
        final RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service, registeredService, ticketGrantingTicket);

        final Authentication currentAuthentication = evaluatePossibilityOfMixedPrincipals(authenticationResult, ticketGrantingTicket);
        RegisteredServiceAccessStrategyUtils.ensureServiceSsoAccessIsAllowed(registeredService, service, ticketGrantingTicket);
        evaluateProxiedServiceIfNeeded(service, ticketGrantingTicket, registeredService);

        // Perform security policy check by getting the authentication that satisfies the configured policy
        // This throws if no suitable policy is found
        getAuthenticationSatisfiedByPolicy(currentAuthentication, new ServiceContext(service, registeredService));

        final List<Authentication> authentications = ticketGrantingTicket.getChainedAuthentications();
        final Authentication latestAuthentication = authentications.get(authentications.size() - 1);
        CurrentCredentialsAndAuthentication.bindCurrent(latestAuthentication);
        final Principal principal = latestAuthentication.getPrincipal();
        final ServiceTicketFactory factory = this.ticketFactory.get(ServiceTicket.class);
        final ServiceTicket serviceTicket = factory.create(ticketGrantingTicket, service, currentAuthentication);
        this.ticketRegistry.updateTicket(ticketGrantingTicket);
        this.ticketRegistry.addTicket(serviceTicket);

        logger.info("Granted ticket [{}] for service [{}] and principal [{}]",
                serviceTicket.getId(), service.getId(), principal.getId());

        doPublishEvent(new CasServiceTicketGrantedEvent(this, ticketGrantingTicket, serviceTicket));

        return serviceTicket;
    }

    private static Authentication evaluatePossibilityOfMixedPrincipals(final AuthenticationResult context,
                                                                final TicketGrantingTicket ticketGrantingTicket)
            throws MixedPrincipalException {
        Authentication currentAuthentication = null;
        if (context != null) {
            currentAuthentication = context.getAuthentication();
            if (currentAuthentication != null) {
                final Authentication original = ticketGrantingTicket.getAuthentication();
                if (!currentAuthentication.getPrincipal().equals(original.getPrincipal())) {
                    throw new MixedPrincipalException(
                            currentAuthentication, currentAuthentication.getPrincipal(), original.getPrincipal());
                }
            }
        }
        return currentAuthentication;
    }

    @Audit(
            action = "PROXY_TICKET",
            actionResolverName = "GRANT_PROXY_TICKET_RESOLVER",
            resourceResolverName = "GRANT_PROXY_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "GRANT_PROXY_TICKET_TIMER")
    @Metered(name = "GRANT_PROXY_TICKET_METER")
    @Counted(name = "GRANT_PROXY_TICKET_COUNTER", monotonic = true)
    @Override
    public ProxyTicket grantProxyTicket(final String proxyGrantingTicket, final Service service)
            throws AbstractTicketException {

        final ProxyGrantingTicket proxyGrantingTicketObject = getTicket(proxyGrantingTicket, ProxyGrantingTicket.class);
        final RegisteredService registeredService = this.servicesManager.findServiceBy(service);

        try {
            RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service,
                    registeredService, proxyGrantingTicketObject);
            RegisteredServiceAccessStrategyUtils.ensureServiceSsoAccessIsAllowed(registeredService, service, proxyGrantingTicketObject);
        } catch (final PrincipalException e) {
            throw new UnauthorizedSsoServiceException();
        }

        evaluateProxiedServiceIfNeeded(service, proxyGrantingTicketObject, registeredService);

        // Perform security policy check by getting the authentication that satisfies the configured policy
        // This throws if no suitable policy is found
        getAuthenticationSatisfiedByPolicy(proxyGrantingTicketObject.getRoot().getAuthentication(), 
                new ServiceContext(service, registeredService));

        final List<Authentication> authentications = proxyGrantingTicketObject.getChainedAuthentications();
        final Authentication authentication = authentications.get(authentications.size() - 1);
        CurrentCredentialsAndAuthentication.bindCurrent(authentication);

        final Principal principal = authentication.getPrincipal();
        final ProxyTicketFactory factory = this.ticketFactory.get(ProxyTicket.class);
        final ProxyTicket proxyTicket = factory.create(proxyGrantingTicketObject, service);

        this.ticketRegistry.updateTicket(proxyGrantingTicketObject);
        this.ticketRegistry.addTicket(proxyTicket);

        logger.info("Granted ticket [{}] for service [{}] for user [{}]",
                proxyTicket.getId(), service.getId(), principal.getId());

        doPublishEvent(new CasProxyTicketGrantedEvent(this, proxyGrantingTicketObject, proxyTicket));
        return proxyTicket;
    }

    @Audit(
            action = "PROXY_GRANTING_TICKET",
            actionResolverName = "CREATE_PROXY_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "CREATE_PROXY_GRANTING_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "CREATE_PROXY_GRANTING_TICKET_TIMER")
    @Metered(name = "CREATE_PROXY_GRANTING_TICKET_METER")
    @Counted(name = "CREATE_PROXY_GRANTING_TICKET_COUNTER", monotonic = true)
    @Override
    public ProxyGrantingTicket createProxyGrantingTicket(final String serviceTicketId, final AuthenticationResult authenticationResult)
            throws AuthenticationException, AbstractTicketException {

        CurrentCredentialsAndAuthentication.bindCurrent(authenticationResult.getAuthentication());

        final ServiceTicket serviceTicket = this.ticketRegistry.getTicket(serviceTicketId, ServiceTicket.class);

        if (serviceTicket == null || serviceTicket.isExpired()) {
            logger.debug("ServiceTicket [{}] has expired or cannot be found in the ticket registry", serviceTicketId);
            throw new InvalidTicketException(serviceTicketId);
        }

        final RegisteredService registeredService = this.servicesManager.findServiceBy(serviceTicket.getService());

        RegisteredServiceAccessStrategyUtils
                .ensurePrincipalAccessIsAllowedForService(serviceTicket, authenticationResult, registeredService);

        if (!registeredService.getProxyPolicy().isAllowedToProxy()) {
            logger.warn("ServiceManagement: Service [{}] attempted to proxy, but is not allowed.", serviceTicket.getService().getId());
            throw new UnauthorizedProxyingException();
        }

        final Authentication authentication = authenticationResult.getAuthentication();
        final ProxyGrantingTicketFactory factory = this.ticketFactory.get(ProxyGrantingTicket.class);
        final ProxyGrantingTicket proxyGrantingTicket = factory.create(serviceTicket, authentication);

        logger.debug("Generated proxy granting ticket [{}] based off of [{}]", proxyGrantingTicket, serviceTicketId);
        this.ticketRegistry.addTicket(proxyGrantingTicket);

        doPublishEvent(new CasProxyGrantingTicketCreatedEvent(this, proxyGrantingTicket));

        return proxyGrantingTicket;

    }


    @Audit(
            action = "SERVICE_TICKET_VALIDATE",
            actionResolverName = "VALIDATE_SERVICE_TICKET_RESOLVER",
            resourceResolverName = "VALIDATE_SERVICE_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "VALIDATE_SERVICE_TICKET_TIMER")
    @Metered(name = "VALIDATE_SERVICE_TICKET_METER")
    @Counted(name = "VALIDATE_SERVICE_TICKET_COUNTER", monotonic = true)
    @Override
    public Assertion validateServiceTicket(final String serviceTicketId, final Service service) throws AbstractTicketException {
        final ServiceTicket serviceTicket = this.ticketRegistry.getTicket(serviceTicketId, ServiceTicket.class);

        if (serviceTicket == null) {
            logger.info("Service ticket [{}] does not exist.", serviceTicketId);
            throw new InvalidTicketException(serviceTicketId);
        }

        try {
            /**
             * Synchronization on ticket object in case of cache based registry doesn't serialize
             * access to critical section. The reason is that cache pulls serialized data and
             * builds new object, most likely for each pull. Is this synchronization needed here?
             */
            synchronized (serviceTicket) {
                if (serviceTicket.isExpired()) {
                    logger.info("ServiceTicket [{}] has expired.", serviceTicketId);
                    throw new InvalidTicketException(serviceTicketId);
                }

                if (!serviceTicket.isValidFor(service)) {
                    logger.error("Service ticket [{}] with service [{}] does not match supplied service [{}]",
                            serviceTicketId, serviceTicket.getService().getId(), service);
                    throw new UnrecognizableServiceForServiceTicketValidationException(serviceTicket.getService());
                }
            }

            final Service selectedService = this.validationServiceSelectionStrategies.stream()
                    .sorted()
                    .filter(s -> s.supports(service))
                    .findFirst()
                    .get()
                    .resolveServiceFrom(service);
            
            final RegisteredService registeredService = this.servicesManager.findServiceBy(selectedService);
            logger.debug("Located registered service definition {} from {} to handle validation request",
                    registeredService, selectedService);
            RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(selectedService, registeredService);

            final TicketGrantingTicket root = serviceTicket.getGrantingTicket().getRoot();
            final Authentication authentication = getAuthenticationSatisfiedByPolicy(
                    root.getAuthentication(), new ServiceContext(selectedService, registeredService));
            final Principal principal = authentication.getPrincipal();

            final RegisteredServiceAttributeReleasePolicy attributePolicy = registeredService.getAttributeReleasePolicy();
            logger.debug("Attribute policy [{}] is associated with service [{}]", attributePolicy, registeredService);

            @SuppressWarnings("unchecked")
            final Map<String, Object> attributesToRelease = attributePolicy != null
                    ? attributePolicy.getAttributes(principal) : new HashMap<>();

            final String principalId = registeredService.getUsernameAttributeProvider().resolveUsername(principal, selectedService);
            final Principal modifiedPrincipal = this.principalFactory.createPrincipal(principalId, attributesToRelease);
            final AuthenticationBuilder builder = DefaultAuthenticationBuilder.newInstance(authentication);
            builder.setPrincipal(modifiedPrincipal);

            final Authentication finalAuthentication = builder.build();
            CurrentCredentialsAndAuthentication.bindCurrent(finalAuthentication);

            final Assertion assertion = new ImmutableAssertion(
                    finalAuthentication,
                    serviceTicket.getGrantingTicket().getChainedAuthentications(),
                    selectedService,
                    serviceTicket.isFromNewLogin());

            doPublishEvent(new CasServiceTicketValidatedEvent(this, serviceTicket, assertion));

            return assertion;
        } finally {
            if (serviceTicket.isExpired()) {
                this.ticketRegistry.deleteTicket(serviceTicketId);
            } else {
                this.ticketRegistry.updateTicket(serviceTicket);
            }
        }
    }

    @Audit(
            action = "TICKET_GRANTING_TICKET",
            actionResolverName = "CREATE_TICKET_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "CREATE_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER")
    @Timed(name = "CREATE_TICKET_GRANTING_TICKET_TIMER")
    @Metered(name = "CREATE_TICKET_GRANTING_TICKET_METER")
    @Counted(name = "CREATE_TICKET_GRANTING_TICKET_COUNTER", monotonic = true)
    @Override
    public TicketGrantingTicket createTicketGrantingTicket(final AuthenticationResult authenticationResult)
            throws AuthenticationException, AbstractTicketException {

        final Authentication authentication = authenticationResult.getAuthentication();
        final Service service = authenticationResult.getService();
        CurrentCredentialsAndAuthentication.bindCurrent(authentication);

        if (service != null) {
            final RegisteredService registeredService = this.servicesManager.findServiceBy(service);
            RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service, registeredService, authentication);
        }


        final TicketGrantingTicketFactory factory = this.ticketFactory.get(TicketGrantingTicket.class);
        final TicketGrantingTicket ticketGrantingTicket = factory.create(authentication);

        this.ticketRegistry.addTicket(ticketGrantingTicket);

        doPublishEvent(new CasTicketGrantingTicketCreatedEvent(this, ticketGrantingTicket));

        return ticketGrantingTicket;
    }


}
