package com.iot.service;

import java.util.List;

import com.iot.entity.UserEntity;

public interface IUserService {
	UserEntity save(UserEntity entity);
	List<UserEntity> findAll();
	UserEntity getUserWithUsername(String username);
}
