package ru.obelisk.cucmaxl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration 
public class LDAPConfig {
	
	@Bean
	public LdapContextSource ldapContextSource(){
		LdapContextSource context = new LdapContextSource();
		context.setBase("dc=bin,dc=bank");
		context.setUrl("ldap://10.18.1.179:389");
		context.setUserDn("CN=Боженков Владимир Петрович,OU=Users,OU=RostBank,DC=BIN,DC=BANK");
		context.setPassword("ndf^8f22G5");
		return context;
	}
	
	@Bean
	public LdapTemplate ldapTemplate(){
		LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
		ldapTemplate.setIgnoreNameNotFoundException(true);
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setIgnoreSizeLimitExceededException(true);
		return ldapTemplate;
	}
}
