package com.iot.mqtt;

public class AuthenticationModel {
	//token được tạo ra khi người dùng đăng ký esp32
	String user_token;
	
	//ID của esp32 khi insert vào database
	Long id;
	
	
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
