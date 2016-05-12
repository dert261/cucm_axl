package ru.obelisk.cucmaxl.config.web;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import ru.obelisk.cucmaxl.config.DataConfig;
import ru.obelisk.cucmaxl.config.web.security.SecurityInterceptor;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

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
	
	/*@Bean
	@Primary
	public ObjectMapper serializingObjectMapper() {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JavaTimeModule javaTimeModule = new JavaTimeModule();
	    javaTimeModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
	    javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
	    objectMapper.registerModule(javaTimeModule);
	    return objectMapper;
	}*/
	  
	/*@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		return builder;
	}
	
	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		ObjectMapper mapper = new ObjectMapper();
	    //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    return mapper;
	}*/
	
	@Bean
	public SecurityInterceptor securityInterceptor(){
		return new SecurityInterceptor();
	}
		
	@Autowired
	private DataConfig dataConfig;
	  
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping(){
		Object[] interceptors = {securityInterceptor(), dataConfig.openEntityManagerInViewInterceptor()};
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
	public CamelContext camelContext(){
		return new DefaultCamelContext();
	}
}