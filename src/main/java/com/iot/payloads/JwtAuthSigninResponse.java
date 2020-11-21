package com.iot.payloads;

public class JwtAuthSigninResponse {
	String username;
	String jwt;
	
	public JwtAuthSigninResponse(String username, String jwt) {
		this.username = username;
		this.jwt = jwt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
