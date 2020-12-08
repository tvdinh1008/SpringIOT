package com.iot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.iot.dao.ISensorDao;
import com.iot.entity.SensorEntity;

@Repository
public class SensorDao extends AbstractDao<Long, SensorEntity> implements ISensorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SensorEntity> findByListDeviceId(Long deviceId) {
		List<SensorEntity> result = new ArrayList<SensorEntity>();
		String sql = "Select * from sensor t where t.device=:device and t.status=1";
		Query q = entityManager.createNativeQuery(sql, SensorEntity.class);
		q.setParameter("device", deviceId);
		result = q.getResultList();
		return result;
	}

	@Override
	public SensorEntity findAllDataSensor(Long id) {
		SensorEntity entity = null;
		try {
			String sql = "select t from " + getPersistenceClassName()
					+ " t JOIN FETCH t.sensorDataList where t.id=:id and t.status=1";
			Query q = entityManager.createQuery(sql);
			q.setParameter("id", id);
			entity = (SensorEntity) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return entity;
	}

	@Override
	public SensorEntity findByCode(String code) {
		SensorEntity entity = null;
		try {
			String sql = "Select * from sensor t where t.code=:code and t.status=1";
			Query q = entityManager.createNativeQuery(sql, SensorEntity.class);
			q.setParameter("code", code);
			entity = (SensorEntity) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return entity;
	}

}
