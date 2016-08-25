package org.apereo.cas.audit.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.jpa.JpaConfigDataHolder;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.inspektr.audit.AuditTrailManager;
import org.apereo.inspektr.audit.support.JdbcAuditTrailManager;
import org.apereo.inspektr.audit.support.MaxAgeWhereClauseMatchCriteria;
import org.apereo.inspektr.audit.support.WhereClauseMatchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * This is {@link CasSupportJdbcAuditConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("casJdbcAuditConfiguration")
@EnableAspectJAutoProxy
@EnableConfigurationProperties({CasConfigurationProperties.class, JpaProperties.class})
@EnableTransactionManagement(proxyTargetClass = true)
public class CasSupportJdbcAuditConfiguration {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    private JpaProperties springJpaProperties;

    @Bean(name = {"jdbcAuditTrailManager", "auditTrailManager"})
    public AuditTrailManager jdbcAuditTrailManager() {
        final JdbcAuditTrailManager t =
                new JdbcAuditTrailManager(inspektrAuditTransactionTemplate());
        t.setCleanupCriteria(auditCleanupCriteria());
        t.setDataSource(inspektrAuditTrailDataSource());
        return t;
    }

    @Lazy
    @Bean
    public LocalContainerEntityManagerFactoryBean inspektrAuditEntityManagerFactory() {
        return Beans.newEntityManagerFactoryBean(springJpaProperties,
                new JpaConfigDataHolder(
                        Beans.newHibernateJpaVendorAdapter(casProperties.getJdbc()),
                        "jpaInspektrAuditContext",
                        new String[]{"org.apereo.cas.audit.entity"},
                        inspektrAuditTrailDataSource()),
                casProperties.getAudit().getJdbc());
    }

    @Bean
    public WhereClauseMatchCriteria auditCleanupCriteria() {
        return new MaxAgeWhereClauseMatchCriteria(casProperties.getAudit().getJdbc().getMaxAgeDays());
    }

    @Bean
    public PlatformTransactionManager inspektrAuditTransactionManager() {
        return new DataSourceTransactionManager(inspektrAuditTrailDataSource());
    }

    @Bean
    public DataSource inspektrAuditTrailDataSource() {
        return Beans.newHickariDataSource(casProperties.getAudit().getJdbc());
    }

    @Bean
    public TransactionTemplate inspektrAuditTransactionTemplate() {
        final TransactionTemplate t =
                new TransactionTemplate(inspektrAuditTransactionManager());
        t.setIsolationLevelName(casProperties.getAudit().getJdbc().getIsolationLevelName());
        t.setPropagationBehaviorName(casProperties.getAudit().getJdbc().getPropagationBehaviorName());
        return t;
    }
}
