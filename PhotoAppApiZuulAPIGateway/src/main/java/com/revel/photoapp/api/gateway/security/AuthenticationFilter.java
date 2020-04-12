package com.revel.photoapp.api.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;


public class AuthenticationFilter extends BasicAuthenticationFilter {
	
	private final Environment environment;
	
	private static final String AUTH_TOKEN_HEADER_KEY = "authorization.token.header.name";
	private static final String AUTH_TOKEN_HEADER_PREFIX_KEY = "authorization.token.header.prefix";
	private static final String TOKEN_SECRET_KEY = "token.secret";
	
	public AuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment = environment; 
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader(environment.getProperty(AUTH_TOKEN_HEADER_KEY));
		
		if(authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty(AUTH_TOKEN_HEADER_PREFIX_KEY))) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(environment.getProperty(AUTH_TOKEN_HEADER_KEY));
		
		if(authorizationHeader == null) {
			return null;
		}
		
		String token = authorizationHeader.replace(environment.getProperty(AUTH_TOKEN_HEADER_PREFIX_KEY), "");
		
		String userId = Jwts.parser()
				.setSigningKey(environment.getProperty(TOKEN_SECRET_KEY))
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		if(userId == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}
	
	
}
