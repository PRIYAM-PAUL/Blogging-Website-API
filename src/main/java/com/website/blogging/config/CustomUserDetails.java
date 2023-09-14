package com.website.blogging.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.website.blogging.entities.User;


public class CustomUserDetails implements UserDetails{

	private User user;
	public CustomUserDetails(User user) {
		this.user=user;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auth = this.user.getRole().stream().map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
		return auth;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

}