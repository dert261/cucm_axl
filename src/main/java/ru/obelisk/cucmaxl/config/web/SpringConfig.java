package ru.obelisk.cucmaxl.config.web;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.MessageSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import ru.obelisk.cucmaxl.config.DataConfig;

@SuppressWarnings("deprecation")
@Configuration
public class SpringConfig{
	
	@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
	
	@Bean
	public FilterRegistrationBean hiddenFilterRegistrationBean() {
		return new FilterRegistrationBean(new HiddenHttpMethodFilter());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
	    return encoder;
	}
	  
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
	
	@Autowired private DataConfig dataConfig;
	
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping(){
		Object[] interceptors = {dataConfig.openEntityManagerInViewInterceptor()};
		RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
		mapping.setInterceptors(interceptors);
		return mapping;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
	    return messageSource;
	}
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
	    return new Java8TimeDialect();
	}
	
	@Bean
	public CamelContext camelContext(){
		return new DefaultCamelContext();
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	    return new EmbeddedServletContainerCustomizer() {
			
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
	            	            	 
	            container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
			}
		};
	}
}