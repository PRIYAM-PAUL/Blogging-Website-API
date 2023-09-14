package com.website.blogging.service;

import java.util.List;

import com.website.blogging.payload.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,int id);
	void deleteUser(int id);
	UserDto getUserById(int id);
	List<UserDto> getAllUserById(); 
}
