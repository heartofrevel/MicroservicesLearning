package com.revel.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.revel.photoapp.api.users.shared.UserDto;

public interface UsersService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDetails);
	public UserDto getUserDetailsByUsername(String email);
	public UserDto getUserByUserId(String userId);
}
