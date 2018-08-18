package com.levi.route.api.service;

import java.util.Optional;

import com.levi.route.api.entity.User;

public interface UserService {

	User persist(User user);
	
	Optional<User> findByUsername(String username);
}
