package org.apereo.cas.support.oauth.grantflow;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.support.oauth.profile.OAuthUserProfile;
import org.apereo.cas.support.oauth.services.OAuthRegisteredService;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.support.oauth.web.OAuth20Grant;
import org.apereo.cas.ticket.code.OAuthCode;
import org.apereo.cas.ticket.code.OAuthCodeFactory;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.UsernamePasswordAuthenticator;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.http.client.direct.DirectFormClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For the Resource Owner Password Grant.
 *
 * @author zhangzhenli
 * @since 5.0.0
 */
@Component
public class PasswordGrant extends OAuth20Grant {

    public DirectFormClient userFormClient;

    @Autowired
    @Qualifier("defaultOAuthCodeFactory")
    private OAuthCodeFactory oAuthCodeFactory;

    @Autowired
    public PasswordGrant(UsernamePasswordAuthenticator oAuthUserAuthenticator) {
        userFormClient = new DirectFormClient(oAuthUserAuthenticator);
        userFormClient.setName("userForm");
    }

    @Override
    public String accessTokenGrantType() {
        return "password";
    }

    @Override
    protected String getTicketId(HttpServletRequest request, HttpServletResponse response) {
        final WebContext context = new J2EContext(request, response);
        logger.debug("url: {}", context.getFullRequestURL());

        final ProfileManager<CommonProfile> manager = new ProfileManager(context);
        CommonProfile clientProfile = manager.get(false).orElse(null);
        logger.debug("profile: {}", clientProfile);

        // no profile and some current clients
        CommonProfile profile = null;
        if (profile == null || !(profile instanceof OAuthUserProfile)) {
            logger.debug("Performing authentication for client: {}", userFormClient);
            final UsernamePasswordCredentials credentials;
            try {
                credentials = userFormClient.getCredentials(context);
                logger.debug("credentials: {}", credentials);
                if (credentials != null){
                    profile = userFormClient.getUserProfile(credentials, context);
                }else {
                    throw new AuthenticationException("user authentication failed");
                }
            } catch (final HttpAction e) {
                logger.debug("extra HTTP action required: {}", e.getCode());
                throw new AuthenticationException(e.getMessage());
            }
            logger.debug("profile: {}", profile);
            if (profile != null) {
                manager.save(false, profile, false);
            }
        }
        if (profile != null) {
            final OAuthRegisteredService registeredService = OAuthUtils.getRegisteredOAuthService(this.servicesManager, clientProfile.getId());
            final Authentication authentication = createAuthentication(profile, registeredService);
            final Service service = createService(registeredService);
            final OAuthCode code = this.oAuthCodeFactory.create(service, authentication);
            logger.debug("Generated OAuth code: {}", code);
            this.ticketRegistry.addTicket(code);
            return code.getId();
        } else {
            throw new AuthenticationException("user authentication failed");
        }
    }
}
