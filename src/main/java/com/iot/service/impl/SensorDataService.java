package com.iot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iot.entity.SensorDataEntity;
import com.iot.service.ISensorDataService;

@Service
public class SensorDataService implements ISensorDataService{
	
	@Override
	public List<SensorDataEntity> findAllDataSensorId(List<Long> ids){
		
		
		return null;
	}

	@Override
	public List<SensorDataEntity> findAllDataLastSensorId(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}
}
