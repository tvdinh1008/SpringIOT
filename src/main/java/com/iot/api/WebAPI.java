package com.iot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iot.authentication.JwtTokenProvider;
import com.iot.entity.RoleEntity;
import com.iot.entity.UserEntity;
import com.iot.payloads.JwtResponse;
import com.iot.service.IRoleService;
import com.iot.service.IUserService;

@RestController(value = "homeApiControllerOfWeb")
public class WebAPI {
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
    AuthenticationManager authenticationManager;
	@Autowired
    private JwtTokenProvider tokenProvider;

	
	@PostMapping(value = "/api/role")
	private String saveRole(@RequestBody RoleEntity role) {
		RoleEntity result=roleService.save(role);
		if(result!=null) {
			return "Save role success"; 
		}
		return "Save role false"; 
	}
	@PostMapping(value = "/api/auth/signup")
	private String signUp(@RequestBody UserEntity user) {
		UserEntity result=userService.save(user);
		if(result!=null) {
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							user.getUsername(), 
							user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
	        String jwt = tokenProvider.generateJwtToken(authentication);
	        String token="token: "+ jwt;
			return token; 
		}
		return "Save user false"; 
	}
	@PostMapping("/api/auth/signin")
	public JwtResponse  signin( @RequestBody UserEntity loginRequest) {
		
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		// Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateJwtToken(authentication);
		return new JwtResponse(jwt,loginRequest.getUsername());
	}
	@PostMapping("/api/auth/edit")
	public UserEntity updateUser(@RequestBody UserEntity user) {
		UserEntity result=userService.save(user);
		
		return result;
	}
	
	
	
	
}
