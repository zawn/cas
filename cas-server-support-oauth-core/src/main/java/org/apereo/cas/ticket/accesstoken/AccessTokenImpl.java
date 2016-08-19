package org.apereo.cas.ticket.accesstoken;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.code.OAuthCodeImpl;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;
import org.apereo.cas.ticket.refreshtoken.RefreshTokenImpl;

import javax.persistence.*;
import java.util.*;

/**
 * An OAuth access token implementation.
 *
 * @author Jerome Leleu
 * @since 5.0.0
 */
@Entity
@Table(name="OAUTH_ACCESS_TOKENS")
@DiscriminatorValue(AccessToken.PREFIX)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AccessTokenImpl extends OAuthCodeImpl implements AccessToken {

    private static final long serialVersionUID = -8287917546624594512L;

    @Lob
    @Column(name="SCOPES")
    private Set<String> scope;

    @ManyToOne(targetEntity = RefreshTokenImpl.class)
    private TicketGrantingTicket ticketGrantingTicket;

    /**
     * Instantiates a new OAuth access token.
     */
    public AccessTokenImpl() {
        // exists for JPA purposes
    }

    /**
     * Constructs a new access token with unique id for a service and authentication.
     *
     * @param id the unique identifier for the ticket.
     * @param service the service this ticket is for.
     * @param authentication the authentication.
     * @param expirationPolicy the expiration policy.
     * @throws IllegalArgumentException if the service or authentication are null.
     */
    public AccessTokenImpl(final String id, final Service service, final Authentication authentication,
                           final ExpirationPolicy expirationPolicy) {
        super(id, service, authentication, expirationPolicy);
        scope = null;
    }

    /**
     * Constructs a new limited scope access token with unique id for a service and authentication.
     *
     * @param id               the unique identifier for the ticket.
     * @param service          the service this ticket is for.
     * @param authentication   the authentication.
     * @param expirationPolicy the expiration policy.
     * @param scopes           limited scope
     * @throws IllegalArgumentException if the service or authentication are null.
     */
    public AccessTokenImpl(final String id, final Service service, final Authentication authentication,
                           final ExpirationPolicy expirationPolicy, List<String> scopes) {
        super(id, service, authentication, expirationPolicy);
        scope = new HashSet<>(scopes);
    }

    @Override
    public boolean isValidFor(Service serviceToValidate) {
        update();
        if (scope == null)
            return true;
        return this.scope.contains(serviceToValidate.getId());
    }

    @Override
    public TicketGrantingTicket getGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.ticketGrantingTicket = refreshToken;
    }

    @Override
    public void setPrincipal(Principal principal) {

    }

    @Override
    public boolean matches(Service service) {
        return this.getId().matches(service.getId());
    }

    @Override
    public Map<String, Object> getAttributes() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("scope", scope);
        return stringObjectHashMap;
    }
}
