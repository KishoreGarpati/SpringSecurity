package com.javatpoint.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OAuthToken extends AbstractAuthenticationToken {


	private static final long serialVersionUID = -2422322405199194813L;

	private final Object principle;
	private Object credentials;
	
	public Object getPrinciple() {
		return principle;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public OAuthToken(Object principle, Object credentials, Collection<? extends GrantedAuthority> arg0) {
		super(arg0);
		this.principle = principle;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
