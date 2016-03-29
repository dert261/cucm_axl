package ru.obelisk.cucmaxl.web.controllers;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.annotation.Secured;

@Controller
public class HomeController {
		
	private static Logger logger = LogManager.getLogger(HomeController.class);
		
	@RequestMapping(value = {"/", "index.html"}, method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_MANAGER"})
	public String homePage(ModelMap model) {
		logger.info("Requesting index page");
		
		return "index";
	}
	
	/*@RequestMapping(value = {"/channels"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String channelsPage(Model model)	{
		logger.info("Requesting channels page");
		return "channels";
	}
	
	@RequestMapping(value = {"/peers"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public String peersPage(Model model) {
		logger.info("Requesting peers page");
		return "peers";
	}
	*/	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest req, Exception e) {
		logger.warn("In handleException");
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("timestamp", new Date());
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("exception");
		return mav;
	}
}
