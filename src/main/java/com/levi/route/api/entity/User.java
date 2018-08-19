package com.levi.route.api.entity;

import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi.route.api.dto.UserDto;
import com.levi.route.api.enun.Role;
import com.levi.route.api.util.PasswordUtils;

@Entity
@Table(name = "user")
public class User {
	
	private Long id;
	private String username;
	private String password;
	private Role role;
	
	public User() {
		
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public static User fromDto(UserDto userDto) throws NoSuchAlgorithmException {
		User user = new User();
		
		user.setUsername(userDto.getUsername());
		user.setPassword(PasswordUtils.generateBCrypt(userDto.getPassword()));
		user.setRole(Role.ROLE_ADMIN);
		
		return user;
	}
	
}
