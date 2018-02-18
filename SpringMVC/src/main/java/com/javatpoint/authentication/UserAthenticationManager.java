package com.javatpoint.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.javatpoint.model.LoginDTO;

public class UserAthenticationManager implements AuthenticationManager{

	@Autowired
	private LoginService loginService;

	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(auth.getPrincipal().toString());
		loginDTO.setPassword(auth.getCredentials().toString());
		
		boolean isSuccess = false;
		
		if("kishore".equalsIgnoreCase(loginDTO.getUsername()) && 
				"kishore".equals(loginDTO.getPassword())){
			isSuccess = true;
		}
		
		UsernamePasswordAuthenticationToken authToken = null;
		if(isSuccess){
			Collection<GrantedAuthority>  authorities = getAuthorities(loginDTO);
			UserDetails userdetails = new UserDetailsImpl(auth.getName(), auth.getName(), 
												true, true, true, true, authorities, loginDTO);
			authToken = new UsernamePasswordAuthenticationToken(userdetails, auth.getCredentials(), authorities);
		} else {
			// session invalidate
			authToken = new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials());
			authToken.setAuthenticated(false);
		}
		return authToken;
	}
	
	Collection<GrantedAuthority> getAuthorities(LoginDTO loginDTO) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}
	
}
