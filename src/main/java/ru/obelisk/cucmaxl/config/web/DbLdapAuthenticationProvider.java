package ru.obelisk.cucmaxl.config.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.database.models.entity.LdapAuthenticationParameters;
import ru.obelisk.database.models.entity.LdapAuthenticationServer;
import ru.obelisk.database.models.entity.User;
import ru.obelisk.database.models.entity.UserRole;
import ru.obelisk.database.models.service.UserService;
import ru.obelisk.database.models.service.LdapAuthenticationParametersService;


@Service
@Qualifier("dbLdapAuthenticationProvider")
public class DbLdapAuthenticationProvider implements AuthenticationProvider{

	private static Logger logger = LogManager.getLogger(DbLdapAuthenticationProvider.class);
	@Autowired private UserService userService;
	@Autowired private LdapAuthenticationParametersService ldapService;
	@Autowired private PasswordEncoder passwordEncoder=null;
		
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		 	
		String username = authentication.getName();
	    String password = authentication.getCredentials().toString();
	    User localUser = loadDbUserByUsername(username);
	    
	    if(localUser!=null && !localUser.isBlocked()){
	    	LdapAuthenticationParameters ldapAuthenticationParameters = ldapService.getParameters();
	        if(ldapAuthenticationParameters.isActive() && localUser.isLdapUser()){
	        	if(ldapAuth(ldapAuthenticationParameters, authentication)){
	        		return buildUserForAuthentication(username, password, 
	        				buildUserAuthority( new HashSet<UserRole>(localUser.getRoles() )));
	        	}
	        } else {
	        	boolean dbAuthResult = passwordEncoder!=null ? 
	        								passwordEncoder.matches(password, localUser.getPass()) : 
	        									localUser.getPass().equals(password); 
	        	if(dbAuthResult)
	        		return buildUserForAuthentication(username, password, 
    					buildUserAuthority( new HashSet<UserRole>(localUser.getRoles() )));
	        }
	    }
	    
	    return null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	@Transactional(readOnly=true)	
	private User loadDbUserByUsername(final String username){
		return userService.getUserByUsername(username);
	}
	
	private Authentication buildUserForAuthentication(String username, String password,
            List<GrantedAuthority> authorities) {
		Authentication authUser = new UsernamePasswordAuthenticationToken(username, password, authorities);
		return authUser; 
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRoleName()));
		}
		return new ArrayList<GrantedAuthority>(setAuths);
	}
	
	private boolean ldapAuth(LdapAuthenticationParameters ldapAuthenticationParameters, Authentication authentication){
		
		boolean authResult = false;
		
		String username = authentication.getName();
	    String password = authentication.getCredentials().toString();
	    		 
		LdapTemplate ldapTemplate = new LdapTemplate(buildLdapContextSource(ldapAuthenticationParameters));
		ldapTemplate.setIgnoreNameNotFoundException(true);
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setIgnoreSizeLimitExceededException(true);
		
		Filter filter = new EqualsFilter("sAMAccountName", username);
		try {
			ldapTemplate.afterPropertiesSet();
			authResult = ldapTemplate.authenticate(LdapUtils.emptyLdapName(),filter.toString(),password);
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
			authResult = false;
		}
		return authResult;
	}
	
	private LdapContextSource buildLdapContextSource(LdapAuthenticationParameters ldapAuthenticationParameters){
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setBase(ldapAuthenticationParameters.getSearchBase());
		contextSource.setUserDn(ldapAuthenticationParameters.getDistinguishedName());
		contextSource.setPassword(ldapAuthenticationParameters.getPassword());
		contextSource.setUrls(buildLdapUrls(ldapAuthenticationParameters.getLdapServers()));
		contextSource.afterPropertiesSet();
		return contextSource;
	}
	
	private String[] buildLdapUrls(List<LdapAuthenticationServer> ldapServers){
		List<String> serversPool = new ArrayList<String>();
		Iterator<LdapAuthenticationServer> server = ldapServers.iterator();
		while(server.hasNext()){
			LdapAuthenticationServer currentServer = server.next();
			StringBuilder ldapServer = new StringBuilder();
			ldapServer.append(currentServer.isUseSSL()==true ? "ldaps://" : "ldap://");  
			ldapServer.append(currentServer.getHost());
			ldapServer.append(":");
			ldapServer.append(currentServer.getPort());
			serversPool.add(ldapServer.toString());
		}
		String[] simpleArray = new String[serversPool.size()];
		return serversPool.toArray(simpleArray);
	}
}
