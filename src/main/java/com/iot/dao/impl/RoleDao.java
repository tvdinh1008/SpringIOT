package com.iot.dao.impl;

import org.springframework.stereotype.Repository;

import com.iot.dao.IRoleDao;
import com.iot.entity.RoleEntity;

@Repository
public class RoleDao extends AbstractDao<Long, RoleEntity> implements IRoleDao{

	@Override
	public RoleEntity findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
