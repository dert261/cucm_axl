package ru.obelisk.cucmaxl.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration 
public class ThymeleafConfig {

	@Bean 
	public ServletContextTemplateResolver templateResolver() {
	    ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
	    resolver.setPrefix("classpath:/templates/views/");
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode("HTML5");
	    resolver.setCacheable(false); //Must true in prod-mod
	    resolver.setCharacterEncoding("UTF-8");
	    resolver.setOrder(1);
	    return resolver;
	}
		
	@Bean 
	public TemplateEngine templateEngine() {
	    SpringTemplateEngine engine = new SpringTemplateEngine();
	    engine.addTemplateResolver(templateResolver());
	    return engine;
	}
	
	/*@Bean
	public SpringStandardDialect springStandardDialect() {
	    return new SpringStandardDialect();
	}*/
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
	    return new Java8TimeDialect();
	}
	
	@Bean 
	public ThymeleafViewResolver thymeleafViewResolver() {
	    final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	    resolver.setTemplateEngine((SpringTemplateEngine) templateEngine());
	    resolver.setCharacterEncoding("UTF-8");
	    resolver.setOrder(1);
	    return resolver;
	}
}