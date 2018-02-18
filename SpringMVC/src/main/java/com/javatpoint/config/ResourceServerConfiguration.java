package com.javatpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String SERVER_RESOURCE_ID = "oauth2-server";

	@Bean
	public InMemoryTokenStore tokenStore(){
		return new InMemoryTokenStore();
	}
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable()
		.requestMatchers().antMatchers("/service/**")
		.and().authorizeRequests()
		.antMatchers("/service/**").access("hasRole('ADMIN')");
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore()).resourceId(SERVER_RESOURCE_ID);
	}
}
