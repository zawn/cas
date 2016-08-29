package org.apereo.cas.support.pac4j.web.flow;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.CasProtocolConstants;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.principal.ClientCredential;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.support.WebUtils;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents an action to put at the beginning of the webflow.
 * <p>
 * Before any authentication, redirection urls are computed for the different clients defined as well as the theme,
 * locale, method and service are saved into the web session.</p>
 * After authentication, appropriate information are expected on this callback url to finish the authentication
 * process with the provider.
 * @author Jerome Leleu
 * @since 3.5.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClientAction extends AbstractAction {
    /**
     * All the urls and names of the pac4j clients.
     */
    public static final String PAC4J_URLS = "pac4jUrls";

    private transient Logger logger = LoggerFactory.getLogger(ClientAction.class);

    private Clients clients;

    private AuthenticationSystemSupport authenticationSystemSupport;

    private CentralAuthenticationService centralAuthenticationService;

    /**
     * Build the ClientAction.
     */
    public ClientAction() {}

    @Override
    protected Event doExecute(final RequestContext context) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
        final HttpSession session = request.getSession();

        // web context
        final WebContext webContext = new J2EContext(request, response);

        // get client
        final String clientName = request.getParameter(this.clients.getClientNameParameter());
        logger.debug("clientName: {}", clientName);

        // it's an authentication
        if (StringUtils.isNotBlank(clientName)) {
            try {
                // get client
                final BaseClient<Credentials, CommonProfile> client =
                        (BaseClient<Credentials, CommonProfile>) this.clients
                                .findClient(clientName);
                logger.debug("Client: {}", client);

                // get credentials
                Credentials credentials;
                try {
                    credentials = client.getCredentials(webContext);
                    logger.debug("credentials: {}", credentials);
                } catch (final Exception e) {
                    logger.debug("requires http action", e);
                    credentials = null;
                }

                // retrieve parameters from web session
                final Service service = (Service) session.getAttribute(CasProtocolConstants.PARAMETER_SERVICE);
                context.getFlowScope().put(CasProtocolConstants.PARAMETER_SERVICE, service);
                logger.debug("retrieve service: {}", service);
                if (service != null) {
                    request.setAttribute(CasProtocolConstants.PARAMETER_SERVICE, service.getId());
                }
                restoreRequestAttribute(request, session, ThemeChangeInterceptor.DEFAULT_PARAM_NAME);
                restoreRequestAttribute(request, session, LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
                restoreRequestAttribute(request, session, CasProtocolConstants.PARAMETER_METHOD);

                // credentials not null -> try to authenticate
                if (credentials != null) {
                    final AuthenticationResult authenticationResult =
                            this.authenticationSystemSupport.handleAndFinalizeSingleAuthenticationTransaction(service,
                                    new ClientCredential(credentials));

                    final TicketGrantingTicket tgt = this.centralAuthenticationService.createTicketGrantingTicket(authenticationResult);
                    WebUtils.putTicketGrantingTicketInScopes(context, tgt);
                    return success();
                } else {
                    throw new TechnicalException("Credential is Null");
                }
            } catch (final TechnicalException e) {
                prepareForLoginPage(context);
                return new Event(this, CasWebflowConstants.TRANSITION_ID_AUTHENTICATION_FAILURE);
            }
        }

        // no or aborted authentication : go to login page
        prepareForLoginPage(context);
        return error();
    }

    /**
     * Prepare the data for the login page.
     *
     * @param context The current webflow context
     * @throws HttpAction the http action
     */
    protected void prepareForLoginPage(final RequestContext context) throws HttpAction {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
        final HttpSession session = request.getSession();

        // web context
        final WebContext webContext = new J2EContext(request, response);

        // save parameters in web session
        final WebApplicationService service = WebUtils.getService(context);
        logger.debug("save service: {}", service);
        session.setAttribute(CasProtocolConstants.PARAMETER_SERVICE, service);
        saveRequestParameter(request, session, ThemeChangeInterceptor.DEFAULT_PARAM_NAME);
        saveRequestParameter(request, session, LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        saveRequestParameter(request, session, CasProtocolConstants.PARAMETER_METHOD);

        final Set<ProviderLoginPageConfiguration> urls = new LinkedHashSet<>();
        // for all clients, generate redirection urls
        for (final Client client : this.clients.findAllClients()) {
            try {
                final IndirectClient indirectClient = (IndirectClient) client;
                // clean Client suffix for default names
                final String name = client.getName().replace("Client", "");
                final String redirectionUrl = indirectClient.getRedirectAction(webContext).getLocation();
                logger.debug("{} -> {}", name, redirectionUrl);
                urls.add(new ProviderLoginPageConfiguration(name, redirectionUrl, name.toLowerCase()));
            } catch (final Exception e) {
                logger.error("Cannot process client {}", client, e);
            }
        }
        context.getFlowScope().put(PAC4J_URLS, urls);
    }

    /**
     * Restore an attribute in web session as an attribute in request.
     *
     * @param request The HTTP request
     * @param session The HTTP session
     * @param name The name of the parameter
     */
    private void restoreRequestAttribute(final HttpServletRequest request, final HttpSession session,
            final String name) {
        final String value = (String) session.getAttribute(name);
        request.setAttribute(name, value);
    }

    /**
     * Save a request parameter in the web session.
     *
     * @param request The HTTP request
     * @param session The HTTP session
     * @param name The name of the parameter
     */
    private void saveRequestParameter(final HttpServletRequest request, final HttpSession session,
            final String name) {
        final String value = request.getParameter(name);
        if (value != null) {
            session.setAttribute(name, value);
        }
    }

    public Clients getClients() {
        return this.clients;
    }

    public void setClients(final Clients clients) {
        this.clients = clients;
    }

    public CentralAuthenticationService getCentralAuthenticationService() {
        return this.centralAuthenticationService;
    }

    public void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    public AuthenticationSystemSupport getAuthenticationSystemSupport() {
        return this.authenticationSystemSupport;
    }

    public void setAuthenticationSystemSupport(final AuthenticationSystemSupport authenticationSystemSupport) {
        this.authenticationSystemSupport = authenticationSystemSupport;
    }

    /**
     * The Provider login page configuration.
     */
    public static class ProviderLoginPageConfiguration implements Serializable {
        private static final long serialVersionUID = 6216882278086699364L;
        private String name;
        private String redirectUrl;
        private String type;

        /**
         * Instantiates a new Provider ui configuration.
         *
         * @param name        the name
         * @param redirectUrl the redirect url
         * @param type        the type
         */
        ProviderLoginPageConfiguration(final String name, final String redirectUrl, final String type) {
            this.name = name;
            this.redirectUrl = redirectUrl;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public String getType() {
            return type;
        }
    }
}
