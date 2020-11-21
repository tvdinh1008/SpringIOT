package com.iot.dao;

import com.iot.entity.SensorTypeEntity;

public interface ISensorTypeDao extends GenericDao<Long, SensorTypeEntity>{
	SensorTypeEntity findByName(String name);
}
