package com.iot.service;

import java.util.List;

import com.iot.entity.SensorTypeEntity;

public interface ISensorTypeService {
	SensorTypeEntity save(SensorTypeEntity entity);
	SensorTypeEntity findByCode(String code);
	List<SensorTypeEntity> findAll();
}
