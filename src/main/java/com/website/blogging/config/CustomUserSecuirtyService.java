package com.website.blogging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.website.blogging.entities.User;
import com.website.blogging.exception.ResourceNotFoundException;
import com.website.blogging.repository.UserRepo;

@Service
public class CustomUserSecuirtyService implements UserDetailsService {

	@Autowired
	UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.repo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "Email Address :"+username, 1));
		CustomUserDetails customUserDetails= new CustomUserDetails(user);
		return customUserDetails;
	}

}
