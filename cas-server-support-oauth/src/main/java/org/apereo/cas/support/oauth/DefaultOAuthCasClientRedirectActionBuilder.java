package org.apereo.cas.support.oauth;

import org.apereo.cas.CasProtocolConstants;
import org.jasig.cas.client.util.CommonUtils;
import org.pac4j.cas.client.CasClient;
import org.pac4j.core.client.RedirectAction;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is {@link DefaultOAuthCasClientRedirectActionBuilder}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class DefaultOAuthCasClientRedirectActionBuilder implements OAuthCasClientRedirectActionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOAuthCasClientRedirectActionBuilder.class);
    
    @Override
    public RedirectAction build(final CasClient casClient, final WebContext context) {
        try {
            final String redirectionUrl = CommonUtils.constructRedirectUrl(casClient.getConfiguration().getLoginUrl(),
                    CasProtocolConstants.PARAMETER_SERVICE,
                    casClient.computeFinalCallbackUrl(context), casClient.getConfiguration().isRenew(), casClient.getConfiguration().isGateway());
            LOGGER.debug("Final redirect url is {}", redirectionUrl);
            return RedirectAction.redirect(redirectionUrl);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
