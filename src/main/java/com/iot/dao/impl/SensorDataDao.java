package com.iot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.iot.dao.ISensorDataDao;
import com.iot.entity.SensorDataEntity;

@Repository
public class SensorDataDao extends AbstractDao<Long, SensorDataEntity> implements ISensorDataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SensorDataEntity> findAllDataSensorId(List<Long> ids) {
		List<SensorDataEntity> result = new ArrayList<SensorDataEntity>();
		try {
			String sql = "Select t from " + getPersistenceClassName() + " t where t.sensor in(";
			int flag = 0;
			for (Long id : ids) {
				if (flag == 0) {
					sql += id.toString();
				} else {
					sql += "," + id.toString();
					flag++;
				}
			}
			sql += ")";
			Query q = entityManager.createQuery(sql);
			result = q.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<SensorDataEntity> findAllDataLastSensorId(List<Long> ids) {
		
		
		return null;
	}

}
