package com.javatpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.javatpoint.authentication.CustomAuthenticationProvider;
import com.javatpoint.authentication.CustomUsernamePasswordAuthFilter;
import com.javatpoint.authentication.UserAthenticationManager;

@Configuration
@EnableWebSecurity(debug=true)
public class WebSecurityConfig {

	/*@Configuration
	@Component
	@Order(2)
	public static class OauthSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.anonymous().disable()
			.requestMatchers().antMatchers("/oauth/token")
			.and().httpBasic().authenticationEntryPoint(oauthEntryPoint());
		}

		@Bean
		public OAuth2AuthenticationEntryPoint oauthEntryPoint(){
			OAuth2AuthenticationEntryPoint entryPoint =	new OAuth2AuthenticationEntryPoint();
			entryPoint.setRealmName("springsec/client");
			entryPoint.setTypeName("Basic");
			return entryPoint;
		}
	}

	@Configuration
	@Component
	public static class ServiceSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.anonymous().disable()
			.requestMatchers().antMatchers("/service/**")
			.and().httpBasic().authenticationEntryPoint(serviceEntryPoint());
		}

		@Bean
		public OAuth2AuthenticationEntryPoint serviceEntryPoint(){
			OAuth2AuthenticationEntryPoint entryPoint =	new OAuth2AuthenticationEntryPoint();
			return entryPoint;
		}
	}
*/

	@Configuration
	@Component
	public static class WebSecurity extends WebSecurityConfigurerAdapter {	
		
		@Bean
		public CustomUsernamePasswordAuthFilter authenticationFilter(){
			CustomUsernamePasswordAuthFilter filter = new CustomUsernamePasswordAuthFilter();
			filter.setAuthenticationManager(new UserAthenticationManager());
			return filter;
		};
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.authorizeRequests()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
			.and().formLogin()
			.and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.parentAuthenticationManager(new UserAthenticationManager());
			auth.authenticationProvider(new CustomAuthenticationProvider());
		}
		
		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}
