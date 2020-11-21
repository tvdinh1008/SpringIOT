package com.iot.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.iot.dao.ISensorTypeDao;
import com.iot.entity.SensorTypeEntity;

@Repository
public class SensorTypeDao extends AbstractDao<Long, SensorTypeEntity> implements ISensorTypeDao {

	@Override
	public SensorTypeEntity findByName(String name) {
		SensorTypeEntity result=null;
		String sql="SELECT t FROM "+getPersistenceClassName()+" t WHERE t.name=:name";
		Query q = entityManager.createQuery(sql);
		q.setParameter("name", name);
		result=(SensorTypeEntity) q.getSingleResult();
		return result;
	}
	
}
