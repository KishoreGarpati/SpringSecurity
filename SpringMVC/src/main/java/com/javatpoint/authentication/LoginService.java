package com.javatpoint.authentication;

import org.springframework.stereotype.Component;

import com.javatpoint.model.LoginDTO;

@Component
public class LoginService {

	public boolean findUser(LoginDTO loginDTO) {
		
		if("kishore".equalsIgnoreCase(loginDTO.getUsername()) && 
				"kishore".equals(loginDTO.getPassword())){
			return true;
		}
		return false;
	}

}
