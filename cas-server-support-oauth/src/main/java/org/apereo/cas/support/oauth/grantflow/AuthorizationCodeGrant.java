package org.apereo.cas.support.oauth.grantflow;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.PrincipalException;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredServiceAccessStrategyUtils;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.profile.OAuthClientProfile;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.ticket.code.OAuthCode;
import org.apereo.cas.ticket.code.OAuthCodeFactory;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * For the Authorization Code Grant.
 *
 * @author zhangzhenli
 * @since 5.0.0
 */
@Component
public class AuthorizationCodeGrant extends IndirectGrant {

    @Autowired
    @Qualifier("defaultOAuthCodeFactory")
    protected OAuthCodeFactory oAuthCodeFactory;

    @Override
    public String authorizeResponseType() {
        return "code";
    }

    @Override
    public boolean needPreAuthorization() {
        return true;
    }

    @Override
    public ModelAndView authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!verifyAuthorizeRequest(request)) {
            logger.error("Authorize request parameter error");
            return new ModelAndView(OAuthConstants.ERROR_VIEW);
        }

        final String clientId = request.getParameter(OAuthConstants.CLIENT_ID);
        final String bypassApprovalParameter = request.getParameter(OAuthConstants.BYPASS_APPROVAL_PROMPT);
        logger.debug("bypassApprovalParameter: {}", bypassApprovalParameter);

        final OAuthRegisteredService registeredService = OAuthUtils.getRegisteredOAuthService(this.servicesManager, clientId);
        try {
            RegisteredServiceAccessStrategyUtils.ensureServiceAccessIsAllowed(clientId, registeredService);
        } catch (final UnauthorizedServiceException e) {
            logger.error(e.getMessage(), e);
            return new ModelAndView(OAuthConstants.ERROR_VIEW);
        }

        final boolean bypassApprovalService = registeredService.isBypassApprovalPrompt();
        logger.debug("bypassApprovalService: {}", bypassApprovalService);

        final J2EContext context = new J2EContext(request, response);

        final UserProfile profile = getUserProfile(context);

        if (profile == null) {
            logger.error("Unexpected null profile");
            return new ModelAndView(OAuthConstants.ERROR_VIEW);
        }

        // bypass approval -> redirect to the application with code or access token
        if (bypassApprovalService || bypassApprovalParameter != null) {
            final Service service = createService(registeredService);
            final Authentication authentication = createAuthentication(profile, registeredService);

            try {
                RegisteredServiceAccessStrategyUtils.ensurePrincipalAccessIsAllowedForService(service,
                        registeredService, authentication);
            } catch (final UnauthorizedServiceException | PrincipalException e) {
                logger.error(e.getMessage(), e);
                return new ModelAndView(OAuthConstants.ERROR_VIEW);
            }

            final OAuthCode code = generateAuthCode(context, authentication, service);

            return issueAuthCode(context, code);
        }

        return redirectToApproveView(registeredService, context);
    }

    @Override
    public String accessTokenGrantType() {
        return "authorization_code";
    }

    @Override
    protected String getTicketId(HttpServletRequest request, HttpServletResponse response) {
        final String parameter = request.getParameter(OAuthConstants.CODE);
        return parameter == null ? null : parameter.trim();
    }


    /**
     * Verify the authorize request.
     *
     * @param request the HTTP request
     * @return whether the authorize request is valid
     */
    protected boolean verifyAuthorizeRequest(final HttpServletRequest request) {

        final boolean checkParameterExist = this.validator.checkParameterExist(request, OAuthConstants.CLIENT_ID)
                && this.validator.checkParameterExist(request, OAuthConstants.REDIRECT_URI)
                && this.validator.checkParameterExist(request, OAuthConstants.RESPONSE_TYPE);

        final String clientId = request.getParameter(OAuthConstants.CLIENT_ID);
        final String redirectUri = request.getParameter(OAuthConstants.REDIRECT_URI);
        final OAuthRegisteredService registeredService = OAuthUtils.getRegisteredOAuthService(this.servicesManager, clientId);

        return checkParameterExist
                && this.validator.checkServiceValid(registeredService)
                && this.validator.checkCallbackValid(registeredService, redirectUri);
    }

    private ModelAndView redirectToApproveView(final OAuthRegisteredService registeredService, final J2EContext context) {
        String callbackUrl = context.getFullRequestURL();
        callbackUrl = CommonHelper.addParameter(callbackUrl, OAuthConstants.BYPASS_APPROVAL_PROMPT, "true");
        final Map<String, Object> model = new HashMap<>();
        model.put("callbackUrl", callbackUrl);
        model.put("serviceName", registeredService.getName());
        logger.debug("callbackUrl: {}", callbackUrl);
        return new ModelAndView(OAuthConstants.CONFIRM_VIEW, model);
    }

    protected UserProfile getUserProfile(J2EContext context) {
        final ProfileManager<CommonProfile> manager = new ProfileManager(context);
        final UserProfile profile = manager.get(true).orElse(null);
        return profile;
    }

    protected OAuthCode generateAuthCode(WebContext context, Authentication authentication, Service service) {
        final OAuthCode code = this.oAuthCodeFactory.create(service, authentication);
        logger.debug("Generated OAuth code: {}", code);
        this.ticketRegistry.addTicket(code);
        return code;
    }

    protected ModelAndView issueAuthCode(WebContext context, OAuthCode code) {
        final String state = context.getRequestParameter(OAuthConstants.STATE);
        String callbackUrl = context.getRequestParameter(OAuthConstants.REDIRECT_URI);
        callbackUrl = CommonHelper.addParameter(callbackUrl, OAuthConstants.CODE, code.getId());
        if (state != null) {
            callbackUrl = CommonHelper.addParameter(callbackUrl, OAuthConstants.STATE, state);
        }
        logger.debug("callbackUrl: {}", callbackUrl);
        return OAuthUtils.redirectTo(callbackUrl);
    }


    /**
     * Get the OAuth code factory.
     *
     * @return the OAuth code factory
     */
    public OAuthCodeFactory getoAuthCodeFactory() {
        return this.oAuthCodeFactory;
    }

    /**
     * Set the OAuth code factory.
     *
     * @param oAuthCodeFactory the OAuth code factory
     */
    public void setoAuthCodeFactory(final OAuthCodeFactory oAuthCodeFactory) {
        this.oAuthCodeFactory = oAuthCodeFactory;
    }

    @Override
    protected boolean verifyAccessTokenRequest(HttpServletRequest request, HttpServletResponse response) {
        final WebContext context = new J2EContext(request, response);
        logger.debug("url: {}", context.getFullRequestURL());

        final ProfileManager<OAuthClientProfile> manager = new ProfileManager(context);
        OAuthClientProfile clientProfile = manager.get(false).orElse(null);
        final String clientId = clientProfile.getId();
        final String redirectUri = request.getParameter(OAuthConstants.REDIRECT_URI);
        final OAuthRegisteredService registeredService = OAuthUtils.getRegisteredOAuthService(this.servicesManager, clientId);

        return super.verifyAccessTokenRequest(request, response)
                && this.validator.checkParameterExist(request, OAuthConstants.REDIRECT_URI)
                && this.validator.checkParameterExist(request, OAuthConstants.CODE)
                && this.validator.checkCallbackValid(registeredService, redirectUri);
    }
}
