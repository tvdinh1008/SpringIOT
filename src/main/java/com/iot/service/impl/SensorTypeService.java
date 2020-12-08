package com.iot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.converter.SensorTypeBeanUtil;
import com.iot.dao.ISensorTypeDao;
import com.iot.dto.SensorTypeDto;
import com.iot.service.ISensorTypeService;

@Service
public class SensorTypeService implements ISensorTypeService {
	@Autowired
	private ISensorTypeDao sensorTypeDao;

	@Override
	public SensorTypeDto save(SensorTypeDto dto) {
		SensorTypeDto result = null;
		if (dto != null && dto.getId() == null) {
			result = SensorTypeBeanUtil.entity2Dto(sensorTypeDao.save(SensorTypeBeanUtil.dto2Entity(dto)));
		}
		return result;
	}

	@Override
	public SensorTypeDto findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SensorTypeDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
