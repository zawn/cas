package org.apereo.cas.support.oauth.web;

import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.grantflow.IndirectGrant;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.support.oauth.validator.OAuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller is in charge of responding to the authorize call in OAuth v2 protocol.
 * This url is protected by a CAS authentication. It returns an OAuth code or directly an access token.
 *
 * @author Jerome Leleu
 * @since 3.5.0
 */
@RefreshScope
@Controller("authorizeController")
public class OAuth20AuthorizeController extends AbstractController {

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
    private IndirectGrant[] indirectGrants;

    @RequestMapping(path = OAuthConstants.BASE_OAUTH20_URL + '/' + OAuthConstants.AUTHORIZE_URL)
    @Override
    public ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!this.validator.checkParameterExist(request, OAuthConstants.RESPONSE_TYPE)) {
            logger.error("Authorize request verification fails");
            return new ModelAndView(OAuthConstants.ERROR_VIEW);
        }

        final String responseType = request.getParameter(OAuthConstants.RESPONSE_TYPE);
        try {
            for (IndirectGrant indirectGrant : indirectGrants) {
                if (responseType.equals(indirectGrant.authorizeResponseType())) {
                    return indirectGrant.authorize(request, response);
                }
            }
        } catch (RuntimeException e) {
            return OAuthUtils.writeTextError(response, e);
        }

        // Unsupported response_type.
        logger.error("Unsupported response type: {}", responseType);
        return new ModelAndView(OAuthConstants.ERROR_VIEW);
    }
}
