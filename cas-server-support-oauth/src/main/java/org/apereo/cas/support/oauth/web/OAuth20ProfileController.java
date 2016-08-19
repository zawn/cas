package org.apereo.cas.support.oauth.web;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.util.OAuthUtils;
import org.apereo.cas.ticket.accesstoken.AccessToken;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.pac4j.core.context.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This controller returns a profile for the authenticated user
 * (identifier + attributes), found with the access token.
 *
 * @author Jerome Leleu
 * @since 3.5.0
 */
@RefreshScope
@Controller("profileController")
public class OAuth20ProfileController {

    /**
     * The logger.
     */
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The ticket registry.
     */
    @Autowired
    @Qualifier("ticketRegistry")
    protected TicketRegistry ticketRegistry;

    /**
     * The JSON factory.
     */
    protected final JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());


    private static final String ID = "id";
    private static final String ATTRIBUTES = "attributes";

    /**
     * Handle request internal response entity.
     *
     * @param request  the request
     * @param response the response
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(path = OAuthConstants.BASE_OAUTH20_URL + '/' + OAuthConstants.PROFILE_URL,
            produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<String> handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws
            Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String accessToken = request.getParameter(OAuthConstants.ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            final String authHeader = request.getHeader(HttpConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotBlank(authHeader)
                    && authHeader.toLowerCase().startsWith(OAuthConstants.BEARER_TOKEN.toLowerCase() + ' ')) {
                accessToken = authHeader.substring(OAuthConstants.BEARER_TOKEN.length() + 1);
            }
        }
        logger.debug("{}: {}", OAuthConstants.ACCESS_TOKEN, accessToken);

        if (StringUtils.isBlank(accessToken)) {
            logger.error("Missing {}", OAuthConstants.ACCESS_TOKEN);
            final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>(1);
            map.add(OAuthConstants.ERROR, OAuthConstants.MISSING_ACCESS_TOKEN);
            final String value = OAuthUtils.jsonify(map);
            return new ResponseEntity<>(value, HttpStatus.UNAUTHORIZED);
        }

        final AccessToken accessTokenTicket = this.ticketRegistry.getTicket(accessToken, AccessToken.class);
        if (accessTokenTicket == null || accessTokenTicket.isExpired()) {
            logger.error("Expired access token: {}", OAuthConstants.ACCESS_TOKEN);
            final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>(1);
            map.add(OAuthConstants.ERROR, OAuthConstants.EXPIRED_ACCESS_TOKEN);
            final String value = OAuthUtils.jsonify(map);
            return new ResponseEntity<>(value, HttpStatus.UNAUTHORIZED);
        }

        final Map<String, Object> map =
                writeOutProfileResponse(accessTokenTicket.getAuthentication().getPrincipal());
        final String value = OAuthUtils.jsonify(map);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    /**
     * Write out profile response.
     *
     * @param principal the principal
     * @return the linked multi value map
     * @throws IOException the io exception
     */
    protected Map<String, Object> writeOutProfileResponse(final Principal principal) throws IOException {
        final Map<String, Object> map = new HashMap<>();
        map.put(ID, principal.getId());
        map.put(ATTRIBUTES, principal.getAttributes());
        return map;
    }

    public TicketRegistry getTicketRegistry() {
        return this.ticketRegistry;
    }

}
