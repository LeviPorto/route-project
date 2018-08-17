package com.levi.route.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.levi.route.api.entities.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
