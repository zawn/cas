package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.jpa.JpaConfigDataHolder;
import org.apereo.cas.services.JpaServiceRegistryDaoImpl;
import org.apereo.cas.services.ServiceRegistryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.apereo.cas.configuration.support.Beans.newEntityManagerFactoryBean;
import static org.apereo.cas.configuration.support.Beans.newHibernateJpaVendorAdapter;
import static org.apereo.cas.configuration.support.Beans.newHickariDataSource;

/**
 * This this {@link JpaServiceRegistryConfiguration}.
 *
 * @author Misagh Moayyed
 * @author Dmitriy Kopylenko
 * @since 5.0.0
 */
@Configuration("jpaServiceRegistryConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class, JpaProperties.class})
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaServiceRegistryConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private JpaProperties springJpaProperties;

    @RefreshScope
    @Bean
    public HibernateJpaVendorAdapter jpaServiceVendorAdapter() {
        return newHibernateJpaVendorAdapter(casProperties.getJdbc());
    }

    @Bean
    public String[] jpaServicePackagesToScan() {
        return new String[]{
                "org.apereo.cas.services",
                "org.apereo.cas.support.oauth.services",
                "org.apereo.cas.support.saml.services"
        };
    }

    @Lazy
    @Bean
    public LocalContainerEntityManagerFactoryBean serviceEntityManagerFactory() {
        return newEntityManagerFactoryBean(springJpaProperties,
                new JpaConfigDataHolder(
                        jpaServiceVendorAdapter(),
                        "jpaServiceRegistryContext",
                        jpaServicePackagesToScan(),
                        dataSourceService()),
                casProperties.getServiceRegistry().getJpa());
    }

    @Autowired
    @Bean
    public PlatformTransactionManager transactionManagerServiceReg(@Qualifier("serviceEntityManagerFactory")
                                                                   final EntityManagerFactory emf) {
        final JpaTransactionManager mgmr = new JpaTransactionManager();
        mgmr.setEntityManagerFactory(emf);
        return mgmr;
    }

    @RefreshScope
    @Bean
    public DataSource dataSourceService() {
        return newHickariDataSource(casProperties.getServiceRegistry().getJpa());
    }

    @Bean(name = {"jpaServiceRegistryDao", "serviceRegistryDao"})
    public ServiceRegistryDao jpaServiceRegistryDao() {
        return new JpaServiceRegistryDaoImpl();
    }
}
