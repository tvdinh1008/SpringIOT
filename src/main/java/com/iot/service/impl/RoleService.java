package com.iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.dao.IRoleDao;
import com.iot.entity.RoleEntity;
import com.iot.service.IRoleService;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Override
	public RoleEntity save(RoleEntity entity) {
		RoleEntity result=null;
		if(entity !=null && entity.getId()!=null) {
			//update
		} if(entity !=null && entity.getId()==null) {
			//save
			result=roleDao.save(entity);
		}
		
		return result;
	}

	@Override
	public RoleEntity findByCode(String code) {
		RoleEntity result=roleDao.findByCode(code);
		return result;
	}

}
