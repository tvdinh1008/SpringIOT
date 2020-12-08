package com.iot.service;

import java.util.List;

import com.iot.dto.SensorTypeDto;

public interface ISensorTypeService {
	SensorTypeDto save(SensorTypeDto entity);
	SensorTypeDto findByCode(String code);
	List<SensorTypeDto> findAll();
}
