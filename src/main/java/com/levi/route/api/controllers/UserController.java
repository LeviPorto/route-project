package com.levi.route.api.controllers;

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

import com.levi.route.api.dtos.UserDto;
import com.levi.route.api.entities.User;
import com.levi.route.api.enums.RoleEnum;
import com.levi.route.api.response.Response;
import com.levi.route.api.services.UserService;
import com.levi.route.api.utils.PasswordUtils;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Response<UserDto>> create(@Valid @RequestBody UserDto userDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Creating user: {}", userDto.toString());
		Response<UserDto> response = new Response<UserDto>();

		User user = this.convertDtoToUser(userDto, result);
		
		this.userService.persist(user);

		response.setData(this.convertUserToDto(user));
		return ResponseEntity.ok(response);
	}
	
	private User convertDtoToUser(UserDto userDto, BindingResult result)
			throws NoSuchAlgorithmException {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(PasswordUtils.generateBCrypt(userDto.getPassword()));
		user.setRole(RoleEnum.ROLE_ADMIN);
		return user;
	}

	private UserDto convertUserToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
