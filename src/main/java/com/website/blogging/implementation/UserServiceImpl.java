package com.website.blogging.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.website.blogging.entities.Role;
import com.website.blogging.entities.User;
import com.website.blogging.exception.ResourceNotFoundException;
import com.website.blogging.payload.UserDto;
import com.website.blogging.repository.RoleRepo;
import com.website.blogging.repository.UserRepo;
import com.website.blogging.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo repo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	ModelMapper mapper;
	@Override
	public UserDto createUser(UserDto userDto) {
		User u =this.userDtoToUser(userDto);
		u.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));
		u.setRole(List.of(repo.findById(2).get()));
		User save = userRepo.save(u);
		// TODO Auto-generated method stub
		return this.userToUserDto(save);
	}

	@Override
	public UserDto updateUser(UserDto userDto,int id) {
		User user =this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));
		Role role = new Role();
		role.setRole("ROLE_ADMIN");
		user.setRole(List.of(role));
		User UpdatedUser = this.userRepo.save(user);
		// TODO Auto-generated method stub
		return this.userToUserDto(UpdatedUser);
	}

	@Override
	public void deleteUser(int id) {
		
		User user =this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
		this.userRepo.delete(user);
		
			}

	@Override
	public UserDto getUserById(int id) {
		User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
		
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUserById() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> collectUser = users.stream().map(e->this.userToUserDto(e)).collect(Collectors.toList());
		return collectUser;
	}

	private User userDtoToUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		
		return user;
	}

	private UserDto userToUserDto(User user) {
		UserDto userDto = this.mapper.map(user, UserDto.class);
	
		return userDto;
	}
}
