package com.iot.service;

import com.iot.dto.RoleDto;

public interface IRoleService {
	RoleDto save(RoleDto dto);
	RoleDto findByCode(String code);
}
