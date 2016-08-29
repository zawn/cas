package org.apereo.cas.config;

import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.AbstractProtocol;
import org.apache.logging.log4j.web.Log4jServletContextListener;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.SocketUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.MessageInterpolator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This is {@link CasWebAppConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("casWebAppConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CasWebAppConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    @Qualifier("messageInterpolator")
    private MessageInterpolator messageInterpolator;

    @Autowired
    private CasConfigurationProperties casProperties;

    @Lazy
    @Bean
    public LocalValidatorFactoryBean credentialsValidator() {
        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(this.messageInterpolator);
        return bean;
    }

    @RefreshScope
    @Bean
    public ThemeChangeInterceptor themeChangeInterceptor() {
        final ThemeChangeInterceptor bean = new ThemeChangeInterceptor();
        bean.setParamName(casProperties.getTheme().getParamName());
        return bean;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        final CookieLocaleResolver bean = new CookieLocaleResolver() {
            @Override
            protected Locale determineDefaultLocale(final HttpServletRequest request) {
                final Locale locale = request.getLocale();
                if (StringUtils.isBlank(casProperties.getLocale().getDefaultValue())
                        || !locale.getLanguage().equals(casProperties.getLocale().getDefaultValue())) {
                    return locale;
                }
                return new Locale(casProperties.getLocale().getDefaultValue());
            }
        };


        return bean;
    }

    @RefreshScope
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final LocaleChangeInterceptor bean = new LocaleChangeInterceptor();
        bean.setParamName(this.casProperties.getLocale().getParamName());
        return bean;
    }

    @Bean
    public Map serviceThemeResolverSupportedBrowsers() {
        final Map<String, String> map = new HashMap<>();
        map.put(".*iPhone.*", "iphone");
        map.put(".*Android.*", "android");
        map.put(".*Safari.*Pre.*", "safari");
        map.put(".*iPhone.*", "iphone");
        map.put(".*Nokia.*AppleWebKit.*", "nokiawebkit");
        return map;
    }

    @Bean
    protected Controller rootController() {
        return new ParameterizableViewController() {
            @Override
            protected ModelAndView handleRequestInternal(final HttpServletRequest request,
                                                         final HttpServletResponse response)
                    throws Exception {
                final String queryString = request.getQueryString();
                final String url = request.getContextPath() + "/login"
                        + (queryString != null ? '?' + queryString : "");
                return new ModelAndView(new RedirectView(response.encodeURL(url)));
            }

        };
    }

    @Bean
    public ServletListenerRegistrationBean log4jServletContextListener() {
        final ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setEnabled(true);
        bean.setName("log4jServletContextListener");
        bean.setListener(new Log4jServletContextListener());
        return bean;
    }

    @Bean
    public SimpleUrlHandlerMapping handlerMapping() {
        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setAlwaysUseFullPath(true);
        mapping.setRootHandler(rootController());
        
        final Map urls = new HashMap();
        urls.put("/", rootController());
        
        mapping.setUrlMap(urls);
        return mapping;
    }

    @Bean
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
        return new SimpleControllerHandlerAdapter();
    }


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        final TomcatEmbeddedServletContainerFactory tomcat =
                new TomcatEmbeddedServletContainerFactory();

        if (casProperties.getServer().getAjp().isEnabled()) {
            final Connector ajpConnector = new Connector(casProperties.getServer().getAjp().getProtocol());
            ajpConnector.setProtocol(casProperties.getServer().getAjp().getProtocol());
            ajpConnector.setPort(casProperties.getServer().getAjp().getPort());
            ajpConnector.setSecure(casProperties.getServer().getAjp().isSecure());
            ajpConnector.setAllowTrace(casProperties.getServer().getAjp().isAllowTrace());
            ajpConnector.setScheme(casProperties.getServer().getAjp().getScheme());
            ajpConnector.setAsyncTimeout(casProperties.getServer().getAjp().getAsyncTimeout());
            ajpConnector.setEnableLookups(casProperties.getServer().getAjp().isEnableLookups());
            ajpConnector.setMaxPostSize(casProperties.getServer().getAjp().getMaxPostSize());

            if (casProperties.getServer().getAjp().getProxyPort() > 0) {
                ajpConnector.setProxyPort(casProperties.getServer().getAjp().getProxyPort());
            }

            if (casProperties.getServer().getAjp().getRedirectPort() > 0) {
                ajpConnector.setRedirectPort(casProperties.getServer().getAjp().getRedirectPort());
            }
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        if (casProperties.getServer().getHttp().isEnabled()) {
            final Connector connector = new Connector(casProperties.getServer().getHttp().getProtocol());

            int port = casProperties.getServer().getHttp().getPort();
            if (port <= 0) {
                port = SocketUtils.findAvailableTcpPort();
            }
            connector.setPort(port);
            tomcat.addAdditionalTomcatConnectors(connector);
        }

        tomcat.getAdditionalTomcatConnectors()
                .stream()
                .filter(connector -> connector.getProtocolHandler() instanceof AbstractProtocol)
                .forEach(connector -> {
                    final AbstractProtocol handler = (AbstractProtocol) connector.getProtocolHandler();
                    handler.setSoTimeout(casProperties.getServer().getConnectionTimeout());
                    handler.setConnectionTimeout(casProperties.getServer().getConnectionTimeout());
                });
        return tomcat;
    }
}
