package ru.obelisk.cucmaxl.config.web;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ru.obelisk.cucmaxl.config.web.security.SecurityConfig;

@Order(1)
public class SpringWebMvcInitializer extends
	AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SecurityConfig.class };
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppConfig.class };
	}
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}


}
