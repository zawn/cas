package org.apereo.cas.support.oauth.grantflow;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.ticket.accesstoken.AccessToken;
import org.apereo.cas.ticket.code.OAuthCode;
import org.apereo.cas.util.EncodingUtils;
import org.pac4j.core.context.WebContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For the Implicit Grant.
 *
 * @author zhangzhenli
 * @since 5.0.0
 */
@Component
public class ImplicitGrant extends AuthorizationCodeGrant {
    @Override
    public String authorizeResponseType() {
        return "token";
    }

    @Override
    public String accessTokenGrantType() {
        return null;
    }

    @Override
    protected ModelAndView issueAuthCode(WebContext context, OAuthCode code) {
        final AccessToken accessToken = generateAccessToken(generateRefreshToken(code.getService(), code.getAuthentication()));
        logger.debug("Generated Oauth access token: {}", accessToken);
        final String state = context.getRequestParameter(OAuthConstants.STATE);
        String callbackUrl = context.getRequestParameter(OAuthConstants.REDIRECT_URI);
        callbackUrl += "#access_token=" + accessToken.getId() + "&token_type=bearer&expires_in=" + this.timeout;
        if (state != null) {
            callbackUrl += "&state=" + EncodingUtils.urlEncode(state);
        }
        return OAuthUtils.redirectTo(callbackUrl);
    }

    @Override
    protected OAuthCode generateAuthCode(WebContext context, Authentication authentication, Service service) {
        // Implicit grant no need to generate an authorization code
        return null;
    }

    @Override
    public ModelAndView accessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Implicit Grant does not support token endpoint access");
    }
}
