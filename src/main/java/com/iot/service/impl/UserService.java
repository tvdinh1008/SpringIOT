package com.iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iot.dao.IRoleDao;
import com.iot.dao.IUserDao;
import com.iot.entity.RoleEntity;
import com.iot.entity.UserEntity;
import com.iot.service.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserEntity save(UserEntity entity) {
		UserEntity result = null;
		if (entity != null && entity.getId() != null) {
			UserEntity old=userDao.findByIdUser(entity.getId());
			old.setPassword(passwordEncoder.encode(entity.getPassword()));
			//set các giá trị thay đổi
			
			result=userDao.update(old);
		} else if (entity != null && entity.getId() == null) {
			RoleEntity role=roleDao.findByCode("USER");
			if(role!=null) {
				entity.setRoleEntity(role);
				entity.setPassword(passwordEncoder.encode(entity.getPassword()));
				result = userDao.save(entity);
			}
		}
		return result;
	}

}
