package com.levi.route.api.services;

import java.util.Optional;

import com.levi.route.api.entities.User;

public interface UserService {

	User persist(User user);
	
	Optional<User> findByUsername(String username);
}
