package org.apereo.cas.ticket.accesstoken;

import org.apereo.cas.ticket.TicketFactory;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;

/**
 * Factory to create OAuth access tokens.
 *
 * @author Jerome Leleu
 * @since 5.0.0
 */
public interface AccessTokenFactory extends TicketFactory {

    /**
     * Create an access token.
     *
     * @param refreshToken the refreshToken
     * @return the access token
     */
    AccessToken create(RefreshToken refreshToken);
}
