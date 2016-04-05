package ru.obelisk.cucmaxl.config.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    	    	
    	DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
    	if( defaultSavedRequest != null ) {
    		String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
    		log.info("RequestUrl: {}",requestUrl);
    		
    		getRedirectStrategy().sendRedirect(request, response, requestUrl);
    	} else {
    		this.setDefaultTargetUrl("/");
    		
    		super.onAuthenticationSuccess(request, response, authentication);
    	}
    }
}
