package com.iot.service;

import java.util.List;

import com.iot.entity.SensorDataEntity;

public interface ISensorDataService {
	List<SensorDataEntity> findAllDataSensorId(List<Long> ids);
	List<SensorDataEntity> findAllDataLastSensorId(List<Long> ids);
	
}
