package com.levi.route.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.UserDto;
import com.levi.route.api.entity.User;
import com.levi.route.api.enun.Role;
import com.levi.route.api.response.Response;
import com.levi.route.api.service.UserService;
import com.levi.route.api.util.PasswordUtils;


@RestController
@RequestMapping("/routeProcessor/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserDto create(@Valid @RequestBody UserDto userDto) throws NoSuchAlgorithmException {
		log.info("Creating user: {}", userDto.toString());

		User user = User.fromDto(userDto);
		this.userService.persist(user);
		
		return userDto;
	}
	
	

}
