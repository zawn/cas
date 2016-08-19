package org.apereo.cas.support.oauth.web;

import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.support.oauth.validator.OAuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller returns an access token according to the given OAuth code and client credentials (authorization code grant type)
 * or according to the refresh token and client credentials (refresh token grant type) or according to the user identity
 * (resource owner password grant type).
 *
 * @author Jerome Leleu
 * @since 3.5.0
 */
@RefreshScope
@Controller("accessTokenController")
public class OAuth20AccessTokenController extends AbstractController {

    /**
     * The logger.
     */
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The OAuth validator.
     */
    @Autowired
    @Qualifier("oAuthValidator")
    protected OAuthValidator validator;

    @Autowired
    private OAuth20Grant[] oAuth20Grants;

    @RequestMapping(path = OAuthConstants.BASE_OAUTH20_URL + '/' + OAuthConstants.ACCESS_TOKEN_URL, method = RequestMethod.POST)
    @Override
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!this.validator.checkParameterExist(request, OAuthConstants.GRANT_TYPE)) {
            logger.error("Authorize request verification fails");
            return OAuthUtils.writeTextError(response, OAuthConstants.INVALID_REQUEST);
        }

        final String grantType = request.getParameter(OAuthConstants.GRANT_TYPE);
        try {
            for (OAuth20Grant oAuth20Grant : oAuth20Grants) {
                if (grantType.equals(oAuth20Grant.accessTokenGrantType())) {
                    return oAuth20Grant.accessToken(request, response);
                }
            }
        } catch (RuntimeException e) {
            return OAuthUtils.writeTextError(response, e);
        }

        // Unsupported grant_type.
        logger.error("Unsupported grant type: {}", grantType);
        return OAuthUtils.writeTextError(response, OAuthConstants.INVALID_REQUEST);
    }
}
