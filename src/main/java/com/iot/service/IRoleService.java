package com.iot.service;

import com.iot.entity.RoleEntity;

public interface IRoleService {
	RoleEntity save(RoleEntity entity);
	RoleEntity findByCode(String code);
}
