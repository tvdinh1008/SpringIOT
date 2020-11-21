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
		List<SensorEntity> result=new ArrayList<SensorEntity>();
		String sql="Select * from sensor t where t.device=:device";
		Query q = entityManager.createNativeQuery(sql,SensorEntity.class);
		q.setParameter("device", deviceId);
		result=q.getResultList();
		return result;
	}

}
