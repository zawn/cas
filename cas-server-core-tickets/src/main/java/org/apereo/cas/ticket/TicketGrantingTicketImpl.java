package org.apereo.cas.ticket;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.proxy.ProxyGrantingTicket;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of a TicketGrantingTicket. A TicketGrantingTicket is
 * the global identifier of a principal into the system. It grants the Principal
 * single-sign on access to any service that opts into single-sign on.
 * Expiration of a TicketGrantingTicket is controlled by the ExpirationPolicy
 * specified as object creation.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
@Entity
@Table(name="Ticket_Granting_Ticket")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(TicketGrantingTicket.PREFIX)
public class TicketGrantingTicketImpl extends AbstractTicket implements TicketGrantingTicket {

    /** Unique Id for serialization. */
    private static final long serialVersionUID = -8608149809180911599L;

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketGrantingTicketImpl.class);

    /** The authenticated object for which this ticket was generated for. */
    @Lob
    @Column(name="AUTHENTICATION", nullable=false, length = Integer.MAX_VALUE)
    private Authentication authentication;

    /** Flag to enforce manual expiration. */
    @Column(name="EXPIRED", nullable=false)
    private Boolean expired = Boolean.FALSE;

    /** Service that produced a proxy-granting ticket. */
    @Lob
    @Column(name="PROXIED_BY", nullable=true, length = Integer.MAX_VALUE)
    private Service proxiedBy;

    /** The services associated to this ticket. */
    @Lob
    @Column(name="SERVICES_GRANTED_ACCESS_TO", nullable=false, length = Integer.MAX_VALUE)
    private HashMap<String, Service> services = new HashMap<>();

    /** The {@link TicketGrantingTicket} this is associated with. */
    @ManyToOne(targetEntity = TicketGrantingTicketImpl.class)
    private TicketGrantingTicket ticketGrantingTicket;

    /** The PGTs associated to this ticket. */
    @OneToMany(targetEntity = TicketGrantingTicketImpl.class, mappedBy = "ticketGrantingTicket", fetch = FetchType.EAGER)
    private Set<ProxyGrantingTicket> proxyGrantingTickets = new HashSet<>();
    
    /**
     * Instantiates a new ticket granting ticket impl.
     */
    public TicketGrantingTicketImpl() {
        // nothing to do
    }

    /**
     * Constructs a new TicketGrantingTicket.
     * May throw an {@link IllegalArgumentException} if the Authentication object is null.
     *
     * @param id the id of the Ticket
     * @param proxiedBy Service that produced this proxy ticket.
     * @param parentTicketGrantingTicket the parent ticket
     * @param authentication the Authentication request for this ticket
     * @param policy the expiration policy for this ticket.
     */
    public TicketGrantingTicketImpl(final String id,
        final Service proxiedBy,
        final TicketGrantingTicket parentTicketGrantingTicket,
         final Authentication authentication, final ExpirationPolicy policy) {

        super(id, policy);

        if (parentTicketGrantingTicket != null && proxiedBy == null) {
            throw new IllegalArgumentException("Must specify proxiedBy when providing parent TGT");
        }
        Assert.notNull(authentication, "authentication cannot be null");
        this.ticketGrantingTicket = parentTicketGrantingTicket;
        this.authentication = authentication;
        this.proxiedBy = proxiedBy;
    }

    /**
     * Constructs a new TicketGrantingTicket without a parent
     * TicketGrantingTicket.
     *
     * @param id the id of the Ticket
     * @param authentication the Authentication request for this ticket
     * @param policy the expiration policy for this ticket.
     */
    public TicketGrantingTicketImpl(final String id,
        final Authentication authentication, final ExpirationPolicy policy) {
        this(id, null, null, authentication, policy);
    }

    @Override
    public TicketGrantingTicket getGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    @Override
    public Authentication getAuthentication() {
        return this.authentication;
    }

    /**
     * {@inheritDoc}
     * <p>The state of the ticket is affected by this operation and the
     * ticket will be considered used. The state update subsequently may
     * impact the ticket expiration policy in that, depending on the policy
     * configuration, the ticket may be considered expired.
     */
    @Override
    public synchronized ServiceTicket grantServiceTicket(final String id,
        final Service service, final ExpirationPolicy expirationPolicy,
        final Authentication currentAuthentication, final boolean onlyTrackMostRecentSession) {
        
        final ServiceTicket serviceTicket = new ServiceTicketImpl(id, this,
                service, currentAuthentication,
                expirationPolicy);
        
        trackServiceSession(serviceTicket.getId(), service, onlyTrackMostRecentSession);
        return serviceTicket;
    }

    /**
     * Update service and track session.
     *
     * @param id                         the id
     * @param service                    the service
     * @param onlyTrackMostRecentSession the only track most recent session
     */
    protected void trackServiceSession(final String id, final Service service, final boolean onlyTrackMostRecentSession) {
        update();
        
        final List<Authentication> authentications = getChainedAuthentications();
        service.setPrincipal(authentications.get(authentications.size()-1).getPrincipal());

        if (onlyTrackMostRecentSession) {
            final String path = normalizePath(service);
            final Collection<Service> existingServices = this.services.values();
            // loop on existing services
            existingServices.stream()
                    .filter(existingService -> path.equals(normalizePath(existingService)))
                    .findFirst().ifPresent(existingService -> {
                        existingServices.remove(existingService);
                        LOGGER.trace("Removed previous tickets for service: {}", existingService);
                    });
        }
        this.services.put(id, service);
    }

    /**
     * Normalize the path of a service by removing the query string and everything after a semi-colon.
     *
     * @param service the service to normalize
     * @return the normalized path
     */
    private static String normalizePath(final Service service) {
        String path = service.getId();
        path = StringUtils.substringBefore(path, "?");
        path = StringUtils.substringBefore(path, ";");
        path = StringUtils.substringBefore(path, "#");
        return path;
    }

    /**
     * Gets an immutable map of service ticket and services accessed by this ticket-granting ticket.
     * Unlike {@link java.util.Collections#unmodifiableMap(java.util.Map)},
     * which is a view of a separate map which can still change, an instance of {@link ImmutableMap}
     * contains its own data and will never change.
     *
     * @return an immutable map of service ticket and services accessed by this ticket-granting ticket.
    */
    @Override
    public synchronized Map<String, Service> getServices() {
        return ImmutableMap.copyOf(this.services);
    }

    @Override
    public Collection<ProxyGrantingTicket> getProxyGrantingTickets() {
        return this.proxyGrantingTickets;
    }

    /**
     * Remove all services of the TGT (at logout).
     */
    @Override
    public void removeAllServices() {
        this.services.clear();
    }

    /**
     * Return if the TGT has no parent.
     *
     * @return if the TGT has no parent.
     */
    @Override
    public boolean isRoot() {
        return this.getGrantingTicket() == null;
    }


    @Override
    public void markTicketExpired() {
        this.expired = Boolean.TRUE;
    }


    @Override
    public TicketGrantingTicket getRoot() {
        TicketGrantingTicket current = this;
        TicketGrantingTicket parent = current.getGrantingTicket();
        while (parent != null) {
            current = parent;
            parent = current.getGrantingTicket();
        }
        return current;
    }

    /**
     * Return if the TGT is expired.
     *
     * @return if the TGT is expired.
     */
    @Override
    public boolean isExpiredInternal() {
        return this.expired;
    }
    
    @Override
    public List<Authentication> getChainedAuthentications() {
        final List<Authentication> list = new ArrayList<>();

        list.add(getAuthentication());

        if (getGrantingTicket() == null) {
            return Collections.unmodifiableList(list);
        }

        list.addAll(getGrantingTicket().getChainedAuthentications());
        return Collections.unmodifiableList(list);
    }

    @Override
    public Service getProxiedBy() {
        return this.proxiedBy;
    }


    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof TicketGrantingTicket)) {
            return false;
        }

        final Ticket ticket = (Ticket) object;

        return new EqualsBuilder()
                .append(ticket.getId(), this.getId())
                .isEquals();
    }


}
