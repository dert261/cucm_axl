package ru.obelisk.cucmaxl.config.web;

import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ru.obelisk.cucmaxl.*", "ru.obelisk.database.*"})
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

