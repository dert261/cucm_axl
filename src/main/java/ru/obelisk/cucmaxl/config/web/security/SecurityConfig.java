package ru.obelisk.cucmaxl.config.web.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired private DataSource dataSource;
	@Autowired private UserDetailsService userDetailsService;
	
	@Autowired private AuthenticationProvider dbLdapAuthenticationProvider;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(dbLdapAuthenticationProvider);/*.
			inMemoryAuthentication().withUser("admin").password("system").roles("ADMIN");*/
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/downloads/**")
	    		.antMatchers("/static/**")
	    		.antMatchers("/resources/**")
	        	.antMatchers("/assets/**")
	        	.antMatchers("/css/**")
	        	.antMatchers("/images/**")
	        	.antMatchers("/error*")
	        	.antMatchers("/login*")
	        	.antMatchers("/access_denied")
	        	.antMatchers("/favicon.ico");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.authorizeRequests()
        		.antMatchers("/**").hasAnyRole("ADMIN","MANAGER","SECURITY","OPERATOR","CMEADMIN")
            	.antMatchers("/rest/*").hasRole("ADMIN")
            	.anyRequest().authenticated()
            	.and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?failed")
                .loginProcessingUrl("/authentication")
                .passwordParameter("password")
                .usernameParameter("username")
                .defaultSuccessUrl("/", false)
                .permitAll()
                .and()
            .logout()
            	.logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
        	.rememberMe()
        		.rememberMeServices(tokenService())
        		.key("3edh2uklashuio23");
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    	tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
    
    @Bean
    public RememberMeServices tokenService() {
    	return new PersistentTokenBasedRememberMeServices("3edh2uklashuio23", userDetailsService, persistentTokenRepository());
    }
}

