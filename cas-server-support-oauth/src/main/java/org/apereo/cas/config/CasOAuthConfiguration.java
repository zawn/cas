package org.apereo.cas.config;

import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.support.oauth.DefaultOAuthCasClientRedirectActionBuilder;
import org.apereo.cas.support.oauth.OAuthCasClientRedirectActionBuilder;
import org.apereo.cas.support.oauth.OAuthConstants;
import org.apereo.cas.support.oauth.authenticator.OAuthClientAuthenticator;
import org.apereo.cas.support.oauth.authenticator.OAuthUserAuthenticator;
import org.apereo.cas.support.oauth.grantflow.IndirectGrant;
import org.apereo.cas.support.oauth.services.OAuthCallbackAuthorizeService;
import org.apereo.cas.support.oauth.validator.OAuth20ValidationServiceSelectionStrategy;
import org.apereo.cas.support.oauth.validator.OAuthValidator;
import org.apereo.cas.support.oauth.web.*;
import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.accesstoken.AccessTokenFactory;
import org.apereo.cas.ticket.accesstoken.DefaultAccessTokenFactory;
import org.apereo.cas.ticket.accesstoken.OAuthAccessTokenExpirationPolicy;
import org.apereo.cas.ticket.code.DefaultOAuthCodeFactory;
import org.apereo.cas.ticket.code.OAuthCodeExpirationPolicy;
import org.apereo.cas.ticket.code.OAuthCodeFactory;
import org.apereo.cas.ticket.refreshtoken.DefaultRefreshTokenFactory;
import org.apereo.cas.ticket.refreshtoken.OAuthRefreshTokenExpirationPolicy;
import org.apereo.cas.ticket.refreshtoken.RefreshTokenFactory;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.DefaultUniqueTicketIdGenerator;
import org.apereo.cas.validation.ValidationServiceSelectionStrategy;
import org.jasig.cas.client.util.URIBuilder;
import org.pac4j.cas.client.CasClient;
import org.pac4j.core.client.RedirectAction;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.http.CallbackUrlResolver;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.DirectFormClient;
import org.pac4j.springframework.web.CallbackController;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * This this {@link CasOAuthConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("oauthConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CasOAuthConfiguration extends WebMvcConfigurerAdapter {

    private static final String CAS_OAUTH_CLIENT = "CasOAuthClient";

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("webApplicationServiceFactory")
    private ServiceFactory webApplicationServiceFactory;

    @Autowired
    @Qualifier("validationServiceSelectionStrategies")
    private List validationServiceSelectionStrategies;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    @Qualifier("defaultAuthenticationSystemSupport")
    private AuthenticationSystemSupport authenticationSystemSupport;

    @Autowired
    @Qualifier("ticketRegistry")
    private TicketRegistry ticketRegistry;

    @Autowired
    private IndirectGrant[] indirectGrants;


    @ConditionalOnMissingBean(name = "accessTokenResponseGenerator")
    @Bean(autowire = Autowire.BY_NAME)
    public AccessTokenResponseGenerator accessTokenResponseGenerator() {
        return new OAuth20AccessTokenResponseGenerator();
    }

    @ConditionalOnMissingBean(name = "oauthCasClientRedirectActionBuilder")
    @Bean(autowire = Autowire.BY_NAME)
    public OAuthCasClientRedirectActionBuilder oauthCasClientRedirectActionBuilder() {
        return new DefaultOAuthCasClientRedirectActionBuilder();
    }

    @Bean
    public Config oauthSecConfig() {
        final CasClient oauthCasClient = new CasClient(casProperties.getServer().getLoginUrl()) {
            @Override
            protected RedirectAction retrieveRedirectAction(final WebContext context) {
                return oauthCasClientRedirectActionBuilder().build(this, context);
            }
        };

        oauthCasClient.setName(CAS_OAUTH_CLIENT);
        oauthCasClient.setCallbackUrlResolver(buildOAuthCasCallbackUrlResolver());

        final DirectBasicAuthClient basicAuthClient = new DirectBasicAuthClient(oAuthClientAuthenticator());
        basicAuthClient.setName("clientBasicAuth");

        final DirectFormClient directFormClient = new DirectFormClient(oAuthClientAuthenticator());
        directFormClient.setName("clientForm");
        directFormClient.setUsernameParameter(OAuthConstants.CLIENT_ID);
        directFormClient.setPasswordParameter(OAuthConstants.CLIENT_SECRET);

        final AnonymousClient anonymousClient = new AnonymousClient();
        anonymousClient.setName("clientAnonymous");

        final String callbackUrl = casProperties.getServer().getPrefix().concat("/oauth2.0/callbackAuthorize");
        return new Config(callbackUrl, oauthCasClient, basicAuthClient, directFormClient, anonymousClient);
    }

    private CallbackUrlResolver buildOAuthCasCallbackUrlResolver() {
        return (url, context) -> {
            final String callbackUrl = casProperties.getServer().getPrefix().concat("/oauth2.0/callbackAuthorize");
            if (url.startsWith(callbackUrl)) {
                final URIBuilder builder = new URIBuilder(url);
                final URIBuilder builderContext = new URIBuilder(context.getFullRequestURL());
                Optional<URIBuilder.BasicNameValuePair> parameter = builderContext.getQueryParams()
                        .stream().filter(p -> p.getName().equals(OAuthConstants.CLIENT_ID))
                        .findFirst();

                if (parameter.isPresent()) {
                    builder.addParameter(parameter.get().getName(), parameter.get().getValue());
                }
                parameter = builderContext.getQueryParams()
                        .stream().filter(p -> p.getName().equals(OAuthConstants.REDIRECT_URI))
                        .findFirst();
                if (parameter.isPresent()) {
                    builder.addParameter(parameter.get().getName(), parameter.get().getValue());
                }
                return builder.build().toString();
            }
            return url;
        };
    }

    @ConditionalOnMissingBean(name = "requiresAuthenticationAuthorizeInterceptor")
    @Bean
    public SecurityInterceptor requiresAuthenticationAuthorizeInterceptor() {
        return new SecurityInterceptor(oauthSecConfig(), CAS_OAUTH_CLIENT);
    }

    @ConditionalOnMissingBean(name = "consentApprovalViewResolver")
    @Bean
    public ConsentApprovalViewResolver consentApprovalViewResolver() {
        return new OAuth20ConsentApprovalViewResolver();
    }

    @ConditionalOnMissingBean(name = "callbackAuthorizeViewResolver")
    @Bean
    public OAuth20CallbackAuthorizeViewResolver callbackAuthorizeViewResolver() {
        return new OAuth20CallbackAuthorizeViewResolver() {
        };
    }

    @Bean
    public SecurityInterceptor requiresAuthenticationAccessTokenInterceptor() {
        return new SecurityInterceptor(oauthSecConfig(), "clientBasicAuth,clientForm,clientAnonymous");
    }

    @Bean
    public HandlerInterceptorAdapter oauthInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                                     final Object handler) throws Exception {
                final String requestPath = request.getRequestURI();
                Pattern pattern = Pattern.compile('/' + OAuthConstants.ACCESS_TOKEN_URL + "(/)*$");

                if (pattern.matcher(requestPath).find()) {
                    return requiresAuthenticationAccessTokenInterceptor().preHandle(request, response, handler);
                }
                StringBuilder typeRegxBuilder = new StringBuilder().append("^");
                for (IndirectGrant indirectGrant : indirectGrants) {
                    if (!indirectGrant.needPreAuthorization())
                        typeRegxBuilder.append(indirectGrant.authorizeResponseType()).append("|");
                }
                typeRegxBuilder.append("$");
                pattern = Pattern.compile('/' + OAuthConstants.AUTHORIZE_URL + "(/)*$");
                if (pattern.matcher(requestPath).find() && !request.getParameter(OAuthConstants.RESPONSE_TYPE).matches(typeRegxBuilder.toString())) {
                    return requiresAuthenticationAuthorizeInterceptor().preHandle(request, response, handler);
                }
                return true;

            }
        };
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(oauthInterceptor())
                .addPathPatterns(OAuthConstants.BASE_OAUTH20_URL.concat("/").concat("*"));
    }

    @Bean
    public OAuthCasClientRedirectActionBuilder defaultOAuthCasClientRedirectActionBuilder() {
        return new DefaultOAuthCasClientRedirectActionBuilder();
    }

    @Bean
    public Authenticator<UsernamePasswordCredentials> oAuthClientAuthenticator() {
        final OAuthClientAuthenticator c = new OAuthClientAuthenticator();
        c.setValidator(oAuthValidator());
        c.setServicesManager(this.servicesManager);
        return c;
    }

    @Bean
    public Authenticator<UsernamePasswordCredentials> oAuthUserAuthenticator() {
        final OAuthUserAuthenticator w = new OAuthUserAuthenticator();
        w.setAuthenticationSystemSupport(authenticationSystemSupport);
        return w;
    }

    @Bean
    public OAuthValidator oAuthValidator() {
        return new OAuthValidator();
    }

    @Bean
    public AccessTokenResponseGenerator oauthAccessTokenResponseGenerator() {
        return new OAuth20AccessTokenResponseGenerator();
    }

    @Bean
    @RefreshScope
    public AccessTokenFactory defaultAccessTokenFactory() {
        final DefaultAccessTokenFactory f = new DefaultAccessTokenFactory();
        f.setAccessTokenIdGenerator(accessTokenIdGenerator());
        f.setExpirationPolicy(accessTokenExpirationPolicy());
        return f;
    }

    private ExpirationPolicy accessTokenExpirationPolicy() {
        return new OAuthAccessTokenExpirationPolicy(
                casProperties.getAuthn().getOauth().getAccessToken().getPolicy().getTimeToKillInSeconds(),
                casProperties.getAuthn().getOauth().getAccessToken().getPolicy().getMaxTimeToLiveInSeconds(),
                TimeUnit.SECONDS);
    }

    private ExpirationPolicy oAuthCodeExpirationPolicy() {
        return new OAuthCodeExpirationPolicy(
                casProperties.getAuthn().getOauth().getCode().getPolicy().getNumberOfUses(),
                casProperties.getAuthn().getOauth().getCode().getPolicy().getMaxTimeToLiveInSeconds(),
                TimeUnit.SECONDS);
    }

    @Bean
    public UniqueTicketIdGenerator oAuthCodeIdGenerator() {
        return new DefaultUniqueTicketIdGenerator();
    }

    @Bean
    public UniqueTicketIdGenerator refreshTokenIdGenerator() {
        return new DefaultUniqueTicketIdGenerator();
    }

    @Bean
    @RefreshScope
    public OAuthCodeFactory defaultOAuthCodeFactory() {
        final DefaultOAuthCodeFactory f = new DefaultOAuthCodeFactory();
        f.setExpirationPolicy(oAuthCodeExpirationPolicy());
        f.setoAuthCodeIdGenerator(oAuthCodeIdGenerator());
        return f;
    }

    @Bean
    public OAuth20CallbackAuthorizeController callbackAuthorizeController() {
        final OAuth20CallbackAuthorizeController c = new OAuth20CallbackAuthorizeController();
        c.setCallbackController(callbackController());
        c.setConfig(oauthSecConfig());
        c.setAuth20CallbackAuthorizeViewResolver(callbackAuthorizeViewResolver());
        return c;
    }

    @Bean
    public PrincipalFactory oauthPrincipalFactory() {
        return new DefaultPrincipalFactory();
    }

    @Bean
    @RefreshScope
    public RefreshTokenFactory defaultRefreshTokenFactory() {
        final DefaultRefreshTokenFactory f = new DefaultRefreshTokenFactory();
        f.setExpirationPolicy(refreshTokenExpirationPolicy());
        f.setRefreshTokenIdGenerator(refreshTokenIdGenerator());
        return f;
    }

    private ExpirationPolicy refreshTokenExpirationPolicy() {
        return new OAuthRefreshTokenExpirationPolicy(
                casProperties.getAuthn().getOauth().getRefreshToken().getPolicy().getNumberOfUses(),
                casProperties.getAuthn().getOauth().getRefreshToken().getPolicy().getTimeToKillInSeconds(),
                casProperties.getAuthn().getOauth().getRefreshToken().getPolicy().getMaxTimeToLiveInSeconds(),
                TimeUnit.SECONDS);
    }

    @Bean
    public ValidationServiceSelectionStrategy oauth20ValidationServiceSelectionStrategy() {
        final OAuth20ValidationServiceSelectionStrategy s = new OAuth20ValidationServiceSelectionStrategy();
        s.setServicesManager(servicesManager);
        s.setWebApplicationServiceFactory(webApplicationServiceFactory);
        return s;
    }

    @Bean
    public CallbackController callbackController() {
        final CallbackController c = new CallbackController();
        c.setConfig(oauthSecConfig());
        return c;
    }


    @Bean
    public UniqueTicketIdGenerator accessTokenIdGenerator() {
        return new DefaultUniqueTicketIdGenerator();
    }

    @PostConstruct
    public void initializeServletApplicationContext() {
        final String oAuthCallbackUrl = casProperties.getServer().getPrefix() + OAuthConstants.BASE_OAUTH20_URL + '/'
                + OAuthConstants.CALLBACK_AUTHORIZE_URL_DEFINITION;

        final Service callbackService = this.webApplicationServiceFactory.createService(oAuthCallbackUrl);
        final RegisteredService svc = servicesManager.findServiceBy(callbackService);

        if (svc == null || !svc.getServiceId().equals(oAuthCallbackUrl)) {
            final OAuthCallbackAuthorizeService service = new OAuthCallbackAuthorizeService();
            service.setName("OAuth Callback url");
            service.setDescription("OAuth Wrapper Callback Url");
            service.setServiceId(oAuthCallbackUrl);
            service.setEvaluationOrder(Integer.MIN_VALUE);

            servicesManager.save(service);
            servicesManager.load();
        }

        this.validationServiceSelectionStrategies.add(0, oauth20ValidationServiceSelectionStrategy());
    }
}
