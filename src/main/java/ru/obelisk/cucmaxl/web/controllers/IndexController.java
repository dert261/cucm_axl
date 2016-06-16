package ru.obelisk.cucmaxl.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.access.annotation.Secured;

@Controller
public class IndexController {
		
	private static Logger logger = LogManager.getLogger(IndexController.class);
		
	@RequestMapping(value = {"/", "index.html"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER","ROLE_SECURITY","ROLE_OPERATOR","ROLE_USER","ROLE_CMEADMIN"})
	public String homePage(ModelMap model) {
		logger.info("Requesting index page");
		return "index";
	}
	
	@RequestMapping(value = {"/401.html"}, method = RequestMethod.GET)
	public String unuthorizedPage(ModelMap model) {
		logger.info("Requesting 401 page");
		return "401";
	}
	
	@RequestMapping(value = {"/403.html"}, method = RequestMethod.GET)
	public String forbiddenPage(ModelMap model) {
		logger.info("Requesting 403 page");
		return "403";
	}
	
	@RequestMapping(value = {"/404.html"}, method = RequestMethod.GET)
	public String notFoundPage(ModelMap model) {
		logger.info("Requesting 404 page");
		return "404";
	}
	
	@RequestMapping(value = {"/500.html"}, method = RequestMethod.GET)
	public String internalErrorPage(ModelMap model) {
		logger.info("Requesting 500 page");
		return "500";
	}
}
