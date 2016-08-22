package org.apereo.cas.support.oauth.web;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.BasicCredentialMetaData;
import org.apereo.cas.authentication.BasicIdentifiableCredential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthenticationBuilder;
import org.apereo.cas.authentication.DefaultHandlerResult;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.profile.OAuthClientProfile;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.services.OAuthWebApplicationService;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.support.oauth.validator.OAuthValidator;
import org.apereo.cas.ticket.OAuthToken;
import org.apereo.cas.ticket.accesstoken.AccessToken;
import org.apereo.cas.ticket.accesstoken.AccessTokenFactory;
import org.apereo.cas.ticket.refreshtoken.RefreshToken;
import org.apereo.cas.ticket.refreshtoken.RefreshTokenFactory;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * The OAuth 20 Access Token grant.
 *
 * @author zhangzhenli
 * @since 5.0.0
 */
public abstract class OAuth20Grant {

    /**
     * The logger.
     */
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The services manager.
     */
    @Autowired
    @Qualifier("servicesManager")
    protected ServicesManager servicesManager;

    /**
     * The ticket registry.
     */
    @Autowired
    @Qualifier("ticketRegistry")
    protected TicketRegistry ticketRegistry;

    /**
     * The access token timeout.
     */

    @Value("${oauth.access.token.maxTimeToLiveInSeconds:28800}")
    protected long timeout;

    /**
     * The OAuth validator.
     */
    @Autowired
    @Qualifier("oAuthValidator")
    protected OAuthValidator validator;

    /**
     * The JSON factory.
     */
    protected final JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());


    @Autowired
    @Qualifier("defaultAccessTokenFactory")
    private AccessTokenFactory accessTokenFactory;


    @Autowired
    @Qualifier("defaultPrincipalFactory")
    private PrincipalFactory principalFactory;

    @Autowired
    @Qualifier("defaultRefreshTokenFactory")
    private RefreshTokenFactory refreshTokenFactory;

    /**
     * Generate an access token from a service and authentication.
     *
     * @param refreshToken        the refreshToken
     * @return an access token
     */
    protected AccessToken generateAccessToken(final RefreshToken refreshToken) {
        final AccessToken accessToken = this.accessTokenFactory.create(refreshToken);
        this.ticketRegistry.addTicket(accessToken);
        return accessToken;
    }

    /**
     * Create an OAuth service from a registered service.
     *
     * @param registeredService the registered service
     * @return the OAuth service
     */
    protected OAuthWebApplicationService createService(final RegisteredService registeredService) {
        return new OAuthWebApplicationService(registeredService);
    }

    /**
     * Create an authentication from a user profile.
     *
     * @param profile the given user profile
     * @param service the registered service
     * @return the built authentication
     */
    protected Authentication createAuthentication(final UserProfile profile, final RegisteredService service) {
        final Principal principal = this.principalFactory.createPrincipal(profile.getId(), profile.getAttributes());

        final Map<String, Object> newAttributes = service.getAttributeReleasePolicy().getAttributes(principal);
        final Principal newPrincipal = principalFactory.createPrincipal(profile.getId(), newAttributes);

        final String authenticator = profile.getClass().getCanonicalName();
        final CredentialMetaData metadata = new BasicCredentialMetaData(
                new BasicIdentifiableCredential(profile.getId()));
        final HandlerResult handlerResult = new DefaultHandlerResult(authenticator, metadata, newPrincipal, new ArrayList<>());

        return DefaultAuthenticationBuilder.newInstance()
                .addAttribute("permissions", profile.getPermissions())
                .addAttribute("roles", profile.getRoles())
                .addCredential(metadata)
                .setPrincipal(newPrincipal)
                .setAuthenticationDate(ZonedDateTime.now())
                .addSuccess(profile.getClass().getCanonicalName(), handlerResult)
                .build();
    }

    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    public void setServicesManager(final ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    public void setTicketRegistry(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public TicketRegistry getTicketRegistry() {
        return this.ticketRegistry;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(final long timeout) {
        this.timeout = timeout;
    }

    public AccessTokenFactory getAccessTokenFactory() {
        return this.accessTokenFactory;
    }

    public void setAccessTokenFactory(final AccessTokenFactory accessTokenFactory) {
        this.accessTokenFactory = accessTokenFactory;
    }

    public OAuthValidator getValidator() {
        return this.validator;
    }

    public void setValidator(final OAuthValidator validator) {
        this.validator = validator;
    }

    public PrincipalFactory getPrincipalFactory() {
        return this.principalFactory;
    }

    public void setPrincipalFactory(final PrincipalFactory principalFactory) {
        this.principalFactory = principalFactory;
    }

    public abstract String accessTokenGrantType();

    public ModelAndView accessToken(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");

        final J2EContext context = new J2EContext(request, response);
        final ProfileManager<CommonProfile> manager = new ProfileManager(context);
        final UserProfile profile = manager.get(true).orElse(null);
        if (profile == null || !(profile instanceof OAuthClientProfile)) {
            throw new UnauthorizedServiceException("unauthorized client");
        }
        final String clientId = profile.getId();

        if (!verifyAccessTokenRequest(request, response)) {
            logger.error("Access token request verification fails");
            return OAuthUtils.writeTextError(response, OAuthConstants.INVALID_REQUEST);
        }

        final OAuthRegisteredService registeredService = OAuthUtils.getRegisteredOAuthService(this.servicesManager, clientId);

        final boolean generateRefreshToken = isGenerateRefreshToken(registeredService);
        final boolean jsonFormat = isJsonFormat(registeredService);


        final OAuthToken token = getOAuthToken(request, response);
        if (token == null) {
            logger.error("Invalid authorization token: {}", token);
            return OAuthUtils.writeText(response, "error=" + OAuthConstants.INVALID_GRANT, HttpStatus.SC_UNAUTHORIZED);
        }
        if (token.isExpired()) {
            logger.error("Expired authorization token: {} ", token);
            this.ticketRegistry.deleteTicket(token.getId());
            return OAuthUtils.writeText(response, "error=" + OAuthConstants.INVALID_GRANT, HttpStatus.SC_UNAUTHORIZED);
        }
        if (!token.isValidFor(createService(registeredService))) {
            return OAuthUtils.writeText(response, "error=" + OAuthConstants.INVALID_GRANT, HttpStatus.SC_UNAUTHORIZED);
        }
        if (token.isExpired()) {
            this.ticketRegistry.deleteTicket(token.getId());
        } else {
            this.ticketRegistry.updateTicket(token);
        }

        final Service service = token.getService();
        final Authentication authentication = token.getAuthentication();

        final RefreshToken refreshToken = generateRefreshToken(service, authentication);

        final AccessToken accessToken = generateAccessToken(refreshToken);
        final String accessTokenId = accessToken.getId();
        String refreshTokenId = null;
        if (generateRefreshToken) {
            refreshTokenId = refreshToken.getId();
        }

        logger.debug("access token: {} / timeout: {} / refresh token: {}", accessTokenId, this.timeout, refreshTokenId);
        if (jsonFormat) {
            response.setContentType("application/json");
            try (final JsonGenerator jsonGenerator = this.jsonFactory.createGenerator(response.getWriter())) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(OAuthConstants.ACCESS_TOKEN, accessTokenId);
                jsonGenerator.writeNumberField(OAuthConstants.EXPIRES_IN, this.timeout);
                if (CommonHelper.isNotBlank(refreshTokenId)) {
                    jsonGenerator.writeStringField(OAuthConstants.REFRESH_TOKEN, refreshTokenId);
                }
                jsonGenerator.writeEndObject();
            }
            return null;
        } else {
            String text = String.format("%s=%s&%s=%s", OAuthConstants.ACCESS_TOKEN, accessTokenId, OAuthConstants.EXPIRES_IN, this.timeout);
            if (CommonHelper.isNotBlank(refreshTokenId)) {
                text += '&' + OAuthConstants.REFRESH_TOKEN + '=' + refreshTokenId;
            }
            return OAuthUtils.writeText(response, text, HttpStatus.SC_OK);
        }
    }

    protected RefreshToken generateRefreshToken(Service service, Authentication authentication) {
        final RefreshToken refreshToken = this.refreshTokenFactory.create(service, authentication);
        this.ticketRegistry.addTicket(refreshToken);
        return refreshToken;
    }

    protected boolean isJsonFormat(OAuthRegisteredService registeredService) {
        return registeredService.isJsonFormat();
    }

    protected boolean isGenerateRefreshToken(OAuthRegisteredService registeredService) {
        return registeredService.isGenerateRefreshToken();
    }

    /**
     * Verify the access token request.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return true, if successful
     */
    protected boolean verifyAccessTokenRequest(final HttpServletRequest request, final HttpServletResponse response) {
        return true;
    }


    public RefreshTokenFactory getRefreshTokenFactory() {
        return this.refreshTokenFactory;
    }

    public void setRefreshTokenFactory(final RefreshTokenFactory refreshTokenFactory) {
        this.refreshTokenFactory = refreshTokenFactory;
    }

    /**
     * Return the OAuth token (a code or a refresh token).
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the OAuth token
     */
    protected OAuthToken getOAuthToken(final HttpServletRequest request, final HttpServletResponse response) {
        final String ticketId = getTicketId(request, response);
        if (ticketId == null)
            return null;
        final OAuthToken token = this.ticketRegistry.getTicket(ticketId, OAuthToken.class);
        // token should not be expired
        if (token == null || token.isExpired()) {
            logger.error("Code or refresh token invalid: {}", token);
            if (token != null) {
                this.ticketRegistry.deleteTicket(token.getId());
            }
            return null;
        }
        return token;
    }

    protected abstract String getTicketId(HttpServletRequest request, HttpServletResponse response);
}
