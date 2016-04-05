package ru.obelisk.cucmaxl.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories("ru.obelisk.cucmaxl.database.models.repository")
public class DataConfig {
	private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROP_HIBERNATE_GENERATE_STATISTICS = "hibernate.generate_statistics";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROP_HIBERNATE_EVENT_MERGE_ENTITYCOPYOBSERVER = "hibernate.event.merge.entity_copy_observer";
    
    private static final String PROP_HIBERNATE_CACHE_REGION_FACTORYCLASS = "hibernate.cache.region.factory_class";
    private static final String PROP_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String PROP_HIBERNATE_CACHE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    
    private static final String PROP_HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";
    
    
    @Resource
    private Environment env;
 
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return dataSource;
    }
		    
    @Bean(name="openEntityManagerInViewInterceptor")
	public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
    	OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
		interceptor.setEntityManagerFactory(entityManagerFactory().getObject());
		return interceptor; 
	}
    	    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
 
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
 
        return entityManagerFactoryBean;
    }
    
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
 
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROP_HIBERNATE_FORMAT_SQL));
        
        properties.put(PROP_HIBERNATE_GENERATE_STATISTICS, env.getRequiredProperty(PROP_HIBERNATE_GENERATE_STATISTICS));
        
        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        
        properties.put(PROP_HIBERNATE_EVENT_MERGE_ENTITYCOPYOBSERVER, env.getRequiredProperty(PROP_HIBERNATE_EVENT_MERGE_ENTITYCOPYOBSERVER));
        
        properties.put(PROP_HIBERNATE_CACHE_REGION_FACTORYCLASS, env.getRequiredProperty(PROP_HIBERNATE_CACHE_REGION_FACTORYCLASS));
        properties.put(PROP_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE, env.getRequiredProperty(PROP_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
        properties.put(PROP_HIBERNATE_CACHE_USE_QUERY_CACHE, env.getRequiredProperty(PROP_HIBERNATE_CACHE_USE_QUERY_CACHE));
        
        properties.put(PROP_HIBERNATE_DEFAULT_SCHEMA, env.getRequiredProperty(PROP_HIBERNATE_DEFAULT_SCHEMA));
       
        return properties;
    }
}
