package com.website.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogging.config.JwtWebTokenHelper;
import com.website.blogging.payload.JwtAuthRequest;
import com.website.blogging.payload.JwtAuthResponse;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JwtWebTokenHelper helper;
	@Autowired
	private UserDetailsService detailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest ) throws Exception{
		
		this.authenticate(authRequest.getEmail(),authRequest.getPassword());
		UserDetails userDetails = this.detailsService.loadUserByUsername(authRequest.getEmail());
		String generateToken = this.helper.generateToken(userDetails);
		System.out.println(generateToken);
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(generateToken);
		return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
		
	}
	
	private void authenticate(String username,String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
		this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Invalid User name nad password");
			throw new Exception("invalid User details");
			
		}
	}
}
