package com.iot.dao.impl;

import org.springframework.stereotype.Repository;

import com.iot.dao.IUserDao;
import com.iot.entity.UserEntity;

@Repository
public class UserDao extends AbstractDao<Long, UserEntity> implements IUserDao {

	@Override
	public UserEntity findOneUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
