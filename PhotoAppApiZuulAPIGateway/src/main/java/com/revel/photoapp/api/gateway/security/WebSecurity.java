package com.revel.photoapp.api.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private final Environment environment;
	
	private static final String LOGIN_PATH_KEY="api.login.url.path";
	private static final String REGISTRATION_PATH_KEY = "api.registration.url.path";
	private static final String H2_CONSOLE_PATH_KEY = "api.h2console.path";
	private static final String ZUUL_ACTUATOR_PATH_KEY = "api.zuul.actuator.url.path";
	private static final String USERS_ACTUATOR_PATH_KEY = "api.users.actuator.url.path";
	
	@Autowired
	public WebSecurity(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers(environment.getProperty(USERS_ACTUATOR_PATH_KEY)).permitAll() 
		.antMatchers(environment.getProperty(ZUUL_ACTUATOR_PATH_KEY)).permitAll() 
		.antMatchers(environment.getProperty(H2_CONSOLE_PATH_KEY)).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty(LOGIN_PATH_KEY)).permitAll()
		.antMatchers(HttpMethod.POST, environment.getProperty(REGISTRATION_PATH_KEY)).permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new AuthenticationFilter(authenticationManager(), environment));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
