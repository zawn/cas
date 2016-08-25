package org.apereo.cas.ticket.accesstoken;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.*;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;
import org.apereo.cas.ticket.refreshtoken.RefreshTokenImpl;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * Default OAuth access token factory.
 *
 * @author Jerome Leleu
 * @since 5.0.0
 */
public class DefaultAccessTokenFactory implements AccessTokenFactory {

    protected transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Default instance for the ticket id generator. */
    protected UniqueTicketIdGenerator accessTokenIdGenerator = new DefaultUniqueTicketIdGenerator();

    /** ExpirationPolicy for access tokens. */
    protected ExpirationPolicy expirationPolicy;

    @Autowired
    private CasConfigurationProperties casProperties;

    /**
     * The ticket registry.
     */
    @Autowired
    @Qualifier("ticketRegistry")
    protected TicketRegistry ticketRegistry;

    @Override
    public AccessToken create(final RefreshToken refreshToken) {
        final String codeId = this.accessTokenIdGenerator.getNewTicketId(AccessToken.PREFIX);
        boolean onlyTrackMostRecentSession = casProperties.getTicket().getTgt().isOnlyTrackMostRecentSession();
        ServiceTicket ticket = refreshToken.grantServiceTicket(codeId, null, this.expirationPolicy, refreshToken.getAuthentication(), onlyTrackMostRecentSession);
        if (onlyTrackMostRecentSession && refreshToken instanceof RefreshTokenImpl) {
            Map<String, AccessToken> accessTokenHashMap = ((RefreshTokenImpl) refreshToken).getAccessTokenHashMap();
            AccessToken accessToken = accessTokenHashMap.get(ticket.getId());
            accessTokenHashMap.entrySet().stream().filter(entry -> !entry.getKey().equals(accessToken.getId())).forEach(entry -> {
                ticketRegistry.deleteTicket(entry.getKey());
            });
            accessTokenHashMap.clear();
            accessTokenHashMap.put(accessToken.getId(), accessToken);
        }
        return (AccessToken) ticket;
    }

    @Override
    public <T extends TicketFactory> T get(final Class<? extends Ticket> clazz) {
        return (T) this;
    }

    public UniqueTicketIdGenerator getAccessTokenIdGenerator() {
        return this.accessTokenIdGenerator;
    }

    public void setAccessTokenIdGenerator(final UniqueTicketIdGenerator accessTokenIdGenerator) {
        this.accessTokenIdGenerator = accessTokenIdGenerator;
    }

    public ExpirationPolicy getExpirationPolicy() {
        return this.expirationPolicy;
    }

    public void setExpirationPolicy(final ExpirationPolicy expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }
}
