package com.ebenyx.oraklus.security.json.response;

import java.io.Serializable;

import com.ebenyx.oraklus.security.entity.User;

public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private final String username;

	private final String fullName;

	private final String email;

	private final String authorities;

	private final String token;

	public JwtAuthenticationResponse(String username, String fullName, String email, String authorities, String token) {
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.authorities = authorities;
		this.token = token;
	}

	public String getUsername() {
		return this.username;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAuthorities() {
		return this.authorities;
	}

	public String getToken() {
		return this.token;
	}

}

