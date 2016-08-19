package org.apereo.cas.support.oauth.grantflow;

import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.web.OAuth20Grant;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For the Refresh Token Grant.
 *
 * @author zhangzhenli
 * @since 5.0.0
 */
@Component
public class RefreshTokenGrant extends OAuth20Grant {


    @Override
    public String accessTokenGrantType() {
        return "refresh_token";
    }

    @Override
    protected String getTicketId(HttpServletRequest request, HttpServletResponse response) {
        final String parameter = request.getParameter(OAuthConstants.REFRESH_TOKEN);
        if (parameter == null) {
            throw new AuthenticationException("refresh_token parameter error");
        }
        return parameter;
    }

    @Override
    protected boolean verifyAccessTokenRequest(HttpServletRequest request, HttpServletResponse response) {
        return super.verifyAccessTokenRequest(request, response) && this.validator.checkParameterExist(request, OAuthConstants.REFRESH_TOKEN);
    }

    @Override
    protected boolean isGenerateRefreshToken(OAuthRegisteredService registeredService) {
        return false;
    }
}
