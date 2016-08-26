package org.apereo.cas.web.flow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apereo.cas.logout.LogoutRequestStatus;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.LogoutRequest;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * Logout action for front SLO : find the next eligible service and perform front logout.
 *
 * @author Jerome Leleu
 * @since 4.0.0
 */
public class FrontChannelLogoutAction extends AbstractLogoutAction {
    /** Defines the default logout parameter for requests. */
    public static final String DEFAULT_LOGOUT_PARAMETER = "SAMLRequest";

    /** Defines the parameter name that is passed to the flow which contains the logout request. */
    public static final String DEFAULT_FLOW_ATTRIBUTE_LOGOUT_URL = "logoutUrl";

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontChannelLogoutAction.class);

    private String logoutRequestParameter = DEFAULT_LOGOUT_PARAMETER;

    @Autowired
    @Qualifier("logoutManager")
    private LogoutManager logoutManager;

    public FrontChannelLogoutAction() {
    }

    /**
     * Build from the logout manager.
     *
     * @param logoutManager a logout manager.
     */
    public FrontChannelLogoutAction(final LogoutManager logoutManager) {
        this.logoutManager = logoutManager;
    }

    @Override
    protected Event doInternalExecute(final HttpServletRequest request, final HttpServletResponse response,
            final RequestContext context) throws Exception {

        final List<LogoutRequest> logoutRequests = WebUtils.getLogoutRequests(context);
        final Integer startIndex = getLogoutIndex(context);
        if (logoutRequests != null) {
            for (int i = startIndex; i < logoutRequests.size(); i++) {
                final LogoutRequest logoutRequest = logoutRequests.get(i);
                if (logoutRequest.getStatus() == LogoutRequestStatus.NOT_ATTEMPTED) {
                    // assume it has been successful
                    logoutRequest.setStatus(LogoutRequestStatus.SUCCESS);

                    // save updated index
                    putLogoutIndex(context, i + 1);

                    final String logoutUrl = logoutRequest.getLogoutUrl().toExternalForm();
                    LOGGER.debug("Using logout url [{}] for front-channel logout requests", logoutUrl);

                    final String logoutMessage = this.logoutManager.createFrontChannelLogoutMessage(logoutRequest);
                    LOGGER.debug("Front-channel logout message to send under [{}] is [{}]",
                            this.logoutRequestParameter, logoutMessage);

                    // redirect to application with SAML logout message
                    final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(logoutUrl);
                    builder.queryParam(this.logoutRequestParameter, EncodingUtils.urlEncode(logoutMessage));

                    return result(REDIRECT_APP_EVENT, DEFAULT_FLOW_ATTRIBUTE_LOGOUT_URL, builder.build().toUriString());
                }
            }
        }

        // no new service with front-channel logout -> finish logout
        return new Event(this, FINISH_EVENT);
    }

    public LogoutManager getLogoutManager() {
        return this.logoutManager;
    }

    public void setLogoutRequestParameter(final String logoutRequestParameter) {
        this.logoutRequestParameter = logoutRequestParameter;
    }
}
