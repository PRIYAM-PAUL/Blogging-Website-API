package com.website.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.blogging.implementation.UserServiceImpl;
import com.website.blogging.payload.ApiResponse;
import com.website.blogging.payload.UserDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserServiceImpl serviceImpl;
	@PostMapping("/")
	
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUser = serviceImpl.createUser(userDto);
		return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") int id){
		UserDto userById = this.serviceImpl.getUserById(id);
		return new ResponseEntity<UserDto>(userById,HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		List<UserDto> allUserById = this.serviceImpl.getAllUserById();
		return new ResponseEntity<List<UserDto>>(allUserById,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("id") int id){
		this.serviceImpl.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Succesfully",true),HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateById(@Valid @RequestBody UserDto userdto,@PathVariable("id") int id){
		UserDto updateUser = this.serviceImpl.updateUser(userdto,id);
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
	}
}
