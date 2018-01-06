package com.luv.face2face.config;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  21:21 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories("com.luv.face2face.repository")
@PropertySource(value = "classpath:hibernate.properties", ignoreResourceNotFound = true)
@EnableTransactionManagement(proxyTargetClass =true)
public class HibernateConfiguration
{

    private Environment environment;

    @Resource
    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }

    @Resource
    private DataSource dataSource;

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.luv.face2face.domain");
        factory.setDataSource(dataSource);
        factory.setJpaPropertyMap(jpaProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.dialect",environment.getProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.show_sql",environment.getProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql",environment.getProperty("hibernate.format_sql"));
        jpaProperties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.connection.url",environment.getProperty("hibernate.connection.url"));
        jpaProperties.put("hibernate.jdbc.batch_size",environment.getProperty("hibernate.jdbc.batch_size"));
        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}
