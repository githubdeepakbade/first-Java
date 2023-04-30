package com.codewithdurgesh.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codewithdurgesh.blog.exception.*;
import com.codewithdurgesh.blog.Payloads.UserDto;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.service.UserService;
@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) 
	{
		User user=this.dtoToUser(userDto);
		User savedUser= this.userRepo.save(user);
		System.out.println("My name is Aniket");
		
		return this.userToDto(savedUser);
		
		
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) 
	{
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser =this.userRepo.save(user);
	UserDto userDto1=this.userToDto(updatedUser);
	
 		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.userRepo.findAll();
		List<UserDto> userDtos=users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
	}
	public User dtoToUser(UserDto userDto) 
	
	{User user =this.modelMapper.map(userDto, User.class);
	
	//user.setId(userDto.getId());
	//user.setName(userDto.getName());
	//user.setEmai(userDto.getEmail());
	//user.setAbout(userDto.getAbout());
	//user.setPassword(userDto.getPassword());
return user;
		
	}
public UserDto userToDto(User users)

{
	UserDto userDto= this.modelMapper.map(users, UserDto.class);
	
	//userDto.setId(users.getId());
	//userDto.setName(users.getName());
	//userDto.setEmail(users.getEmai());
	//userDto.setPassword(users.getPassword());
	//userDto.setAbout(users.getAbout());
	return userDto;
	
}

}