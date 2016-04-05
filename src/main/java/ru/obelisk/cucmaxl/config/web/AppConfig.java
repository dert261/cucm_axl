package ru.obelisk.cucmaxl.config.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import ru.obelisk.cucmaxl.web.databinding.DatatableCriteriasHandlerMethodArgumentResolver;

@Configuration 
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(basePackages = "ru.obelisk.cucmaxl.*")
public class AppConfig extends WebMvcConfigurerAdapter {
    
	@Value("${ssl.key.store}") private String sslKeyStore;
	@Value("${ssl.key.store.password}") private String sslKeyStorePassword;
	@Value("${ssl.key.store.type}") private String sslKeyStoreType;
	
	@Value("${ssl.trust.store}") private String sslTrustStore;
	@Value("${ssl.trust.store.password}") private String sslTrustStorePassword;
	@Value("${ssl.trust.store.type}") private String sslTrustStoreType;
	
	//Maps resources path to webapp/resources
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/static/**")) {
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new DatatableCriteriasHandlerMethodArgumentResolver());
		// equivalent to <mvc:argument-resolvers>
	}
	
	/**
	    *  Total customization - see below for explanation.
	    */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
			
		configurer.favorPathExtension(true).
	    	favorParameter(false).
	        //parameterName("mediaType").
	        ignoreAcceptHeader(true).
	        useJaf(false).
	        defaultContentType(MediaType.APPLICATION_JSON).
	        mediaType("xml", MediaType.APPLICATION_XML).
	        mediaType("json", MediaType.APPLICATION_JSON).
	        mediaType("html", MediaType.TEXT_HTML);
	}
	
	@Autowired
	private ThymeleafConfig thymeleafConfig;
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
	    // Define the view resolvers
	    List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
	    resolvers.add(thymeleafConfig.thymeleafViewResolver());
	    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	    resolver.setViewResolvers(resolvers);
	    resolver.setContentNegotiationManager(manager);
	    return resolver;
	}
	
	@PostConstruct
	public void setEnviroment(){
		Properties systemProps = System.getProperties();
		systemProps.put("javax.net.ssl.keyStore", sslKeyStore);
		systemProps.put("javax.net.ssl.keyStorePassword", sslKeyStorePassword);
		systemProps.put("javax.net.ssl.keyStoreType", sslKeyStoreType);

		systemProps.put("javax.net.ssl.trustStore", sslTrustStore);
		systemProps.put("javax.net.ssl.trustStorePassword", sslTrustStorePassword);
		systemProps.put("javax.net.ssl.trustStoreType", sslTrustStoreType);
		System.setProperties(systemProps);
	}
	
	
	
	
}

