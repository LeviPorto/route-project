package com.levi.route.api.security.dto;

public class JwtAuthenticationDto {
	
	private String username;
	private String password;

	public JwtAuthenticationDto() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [username=" + username + ", password=" + password + "]";
	}

}
