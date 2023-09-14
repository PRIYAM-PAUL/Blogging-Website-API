package com.website.blogging.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private  JwtWebTokenHelper helper;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		//authorization
		//get Token
		String requestToken = request.getHeader("Authorization");
		
		//Bearer 15141235....
		System.out.println(requestToken);
		
		String userEmail=null;
		String token =null;
		
		if(requestToken ==null || !requestToken.startsWith("Bearer ")) {
			
			try {
				userEmail= this.helper.getUserName(token);
			}catch(IllegalArgumentException e) {
				System.out.println("unable to get Token from JWT Token");;
			}catch(ExpiredJwtException e) {
				System.out.println("Jwt Token has expired");
			}
			catch (MalformedJwtException e) {
				System.out.println("invalid jwt");
			}
			filterChain.doFilter(request, response);
			return;
		}
		else {
			System.out.println("Jwt token doesn't start with Bearer");

		}
		token=requestToken.substring(7);
		//token and username getted
		//validation--->
		userEmail = this.helper.getUserName(token);
		if(userEmail!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			System.out.println(userDetails.getUsername());
			if(helper.isTokenValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			else {
				System.out.println("Invalid Jwt Token");
			}
			
		}else {
			System.out.println("username or context is null ");
		}
		filterChain.doFilter(request, response);	
		}
	}