package ru.obelisk.cucmaxl.config.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	public NullRequestCache sds;
	
	/*@Autowired
    private UserDetailsService customUserDetailsService;*/
	
	@Autowired
	@Qualifier("dbLdapAuthenticationProvider2")
    private AuthenticationProvider dbLdapAuthenticationProvider;
	

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(dbLdapAuthenticationProvider).
			inMemoryAuthentication().withUser("admin").password("system").roles("ADMIN");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
	    		.antMatchers("/downloads/**")
	    		.antMatchers("/static/**")
	    		.antMatchers("/resources/**")
	        	.antMatchers("/assets/**")
	        	.antMatchers("/dandelion/**")
	        	.antMatchers("/dandelion-assets/**")
	        	.antMatchers("/css/**")
	        	.antMatchers("/webjars/**")
	        	.antMatchers("/images/**")
	        	.antMatchers("/error*")
	        	.antMatchers("/login*")
	        	.antMatchers("/logout*")
	        	.antMatchers("/favicon.ico");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.authorizeRequests()
            	.antMatchers("/**").hasAnyRole("ADMIN","MANAGER")
            	.antMatchers("/rest/*").hasRole("ADMIN")
            	.anyRequest().authenticated()
            	.and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?failed")
                .loginProcessingUrl("/authentication")
                .defaultSuccessUrl("/", false)
                .successHandler(new SuccessHandler())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .rememberMe()
            	.tokenValiditySeconds(1209600)
            	.key("remember-me");
            	
    }
}

