package com.website.blogging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	@Autowired
	JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	JwtAuthenticationFilter authenticationFilter;
	@Autowired
	CustomUserSecuirtyService customUserSecuirtyService;
	@Autowired
	UserDetailsService detailsService;
	
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider getauthenticationProvider(){
	DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(customUserSecuirtyService);
	authenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());
	return authenticationProvider;
	}
	
	@Bean
	 SecurityFilterChain chain(HttpSecurity httpSecurity ) throws Exception {
		
		httpSecurity
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/auth/**")
		.permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
		.requestMatchers("/api/users/**").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(this.authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		httpSecurity.authenticationProvider(getauthenticationProvider());
		DefaultSecurityFilterChain build = httpSecurity.build();
		return build;
	}
	

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	
}
