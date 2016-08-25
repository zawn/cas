package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.jpa.JpaConfigDataHolder;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.support.events.dao.CasEventRepository;
import org.apereo.cas.support.events.jpa.JpaCasEventRepository;
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

/**
 * This is {@link JpaEventsConfiguration}, defines certain beans via configuration
 * while delegating some to Spring namespaces inside the context config file.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("jpaEventsConfiguration")
@EnableConfigurationProperties({CasConfigurationProperties.class, JpaProperties.class})
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaEventsConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private JpaProperties springJpaProperties;

    @RefreshScope
    @Bean
    public HibernateJpaVendorAdapter jpaEventVendorAdapter() {
        return Beans.newHibernateJpaVendorAdapter(casProperties.getJdbc());
    }
    
    @RefreshScope
    @Bean
    public DataSource dataSourceEvent() {
        return Beans.newHickariDataSource(casProperties.getEvents().getJpa().getDatabase());
    }
    
    public String[] jpaEventPackagesToScan() {
        return new String[]{"org.apereo.cas.support.events.dao"};
    }
    
    @Lazy
    @Bean
    public LocalContainerEntityManagerFactoryBean eventsEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean =
                Beans.newEntityManagerFactoryBean(springJpaProperties,
                        new JpaConfigDataHolder(
                                jpaEventVendorAdapter(),
                                "jpaEventRegistryContext",
                                jpaEventPackagesToScan(),
                                dataSourceEvent()),
                        casProperties.getEvents().getJpa().getDatabase());

        bean.getJpaPropertyMap().put("hibernate.enable_lazy_load_no_trans", Boolean.TRUE);
        return bean;
    }
    
    @Autowired
    @Bean
    public PlatformTransactionManager transactionManagerEvents(@Qualifier("eventsEntityManagerFactory")
                                                          final EntityManagerFactory emf) {
        final JpaTransactionManager mgmr = new JpaTransactionManager();
        mgmr.setEntityManagerFactory(emf);
        return mgmr;
    }
    
    @Bean
    public CasEventRepository casEventRepository() {
        return new JpaCasEventRepository();
    }
}
