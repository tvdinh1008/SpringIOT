package com.iot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.dao.ISensorTypeDao;
import com.iot.entity.SensorTypeEntity;
import com.iot.service.ISensorTypeService;

@Service
public class SensorTypeService implements ISensorTypeService{
	@Autowired
	private ISensorTypeDao sensorTypeDao;
	
	@Override
	public SensorTypeEntity save(SensorTypeEntity entity) {
		SensorTypeEntity result=null;
		if(entity!=null && entity.getId()==null) {
			result=sensorTypeDao.save(entity);
		}
		return result;
	}

	@Override
	public SensorTypeEntity findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SensorTypeEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
