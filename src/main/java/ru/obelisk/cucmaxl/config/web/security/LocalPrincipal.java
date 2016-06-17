package ru.obelisk.cucmaxl.config.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.service.UserService;

@Component
public class LocalPrincipal {
	private User user = null;
	@Autowired private UserService userService;
		
	public User getUser(){
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			if(this.user==null){
				init(SecurityContextHolder.getContext().getAuthentication().getName());
			}
		}
		return this.user;
	}
	
	public boolean isIsset(){
		return this.user!=null;
	}
	
	private void init(String username){
		user = userService.getUserByUsername(username); 
	}
}