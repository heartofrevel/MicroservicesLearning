package com.revel.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revel.photoapp.api.users.service.UsersService;
import com.revel.photoapp.api.users.shared.UserDto;
import com.revel.photoapp.api.users.ui.model.request.CreateUserRequest;
import com.revel.photoapp.api.users.ui.model.response.CreateUserResponse;
import com.revel.photoapp.api.users.ui.model.response.UserResponse;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsersService usersService;
	
	private static final String KEY_SECRET = "token.secret";
	
	@GetMapping("/status/check")
	public String status() {
		return "Working on port " + env.getProperty("local.server.port")+ ", with token = "+env.getProperty(KEY_SECRET);
	}
	
	@PostMapping(	consumes = { 
					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE }, 
					produces = { 
					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE 
				})
	public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = usersService.createUser(userDto);
		
		CreateUserResponse returnValue = modelMapper.map(createdUser, CreateUserResponse.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	@GetMapping(path = "/{userId}", 
			produces = { 
			MediaType.APPLICATION_XML_VALUE, 
			MediaType.APPLICATION_JSON_VALUE 
			})
	public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
		UserDto userDto= usersService.getUserByUserId(userId);
		UserResponse returnValue = new ModelMapper().map(userDto, UserResponse.class);	
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	} 
}
