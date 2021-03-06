package com.javatpoint.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private InMemoryTokenStore tokenStore;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.tokenStore(tokenStore);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 clients.inMemory()
         .withClient("my-trusted-client")
         .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
         .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
         .scopes("read", "write", "trust")
         .secret("secret")
         .resourceIds("oauth2-server")
         .accessTokenValiditySeconds(1200).//Access token is only valid for 2 minutes.
         refreshTokenValiditySeconds(60000);//Refresh token is only valid for 10 minutes.
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}
}
