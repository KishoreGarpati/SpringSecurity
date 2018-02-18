package com.javatpoint.authentication;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = super.obtainUsername(request);
		String password = super.obtainPassword(request);
		
		if(username == null){
			username = "";
		}
		if(password == null){
			password = "";
		}
		username = username.trim();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		setDetails(request, authRequest);
		return super.attemptAuthentication(request, response);
	}
	
	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		ArrayList<Object> details = new ArrayList<Object>();
		details.add(1, request.getSession());
		authRequest.setDetails(details);
	}
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter("j_username");
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter("j_password");
	}
}
