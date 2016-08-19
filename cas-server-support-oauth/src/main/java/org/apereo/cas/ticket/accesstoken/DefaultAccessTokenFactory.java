package org.apereo.cas.ticket.accesstoken;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.*;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    public AccessToken create(final RefreshToken refreshToken) {
        final String codeId = this.accessTokenIdGenerator.getNewTicketId(AccessToken.PREFIX);
        ServiceTicket ticket = refreshToken.grantServiceTicket(codeId, null, this.expirationPolicy, refreshToken.getAuthentication(), false);
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
