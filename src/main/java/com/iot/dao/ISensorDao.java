package com.iot.dao;

import java.util.List;

import com.iot.entity.SensorEntity;

public interface ISensorDao extends GenericDao<Long, SensorEntity>{
	List<SensorEntity> findByListDeviceId(Long deviceId);
}
