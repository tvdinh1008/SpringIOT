package com.iot.payloads;

import com.iot.authentication.MyUser;

public class JwtAuthSigninResponse {
	MyUser user;
	String jwt;

	public JwtAuthSigninResponse(MyUser user, String jwt) {
		this.user = user;
		this.jwt = jwt;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
