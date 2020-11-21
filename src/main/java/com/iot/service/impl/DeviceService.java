package com.iot.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.dao.IDeviceDao;
import com.iot.dao.ISensorDao;
import com.iot.dao.ISensorTypeDao;
import com.iot.entity.DeviceEntity;
import com.iot.entity.SensorEntity;
import com.iot.entity.SensorTypeEntity;
import com.iot.service.IDeviceService;

@Service
public class DeviceService implements IDeviceService{
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private ISensorTypeDao sensorTypeDao;
	@Autowired
	private ISensorDao sensorDao;
	
	@Override
	@Transactional
	public DeviceEntity save(DeviceEntity entity) {
		DeviceEntity result=null;
		if(entity!=null && entity.getId()!=null) {
			//Cập nhật 
			
		}else if(entity !=null && entity.getId()==null) {
			//Thêm mới device và các cảm biến gắn với device đó.
			result=deviceDao.save(entity);
			if(result!=null) {
				SensorTypeEntity type1=sensorTypeDao.findByName("DHT22");
				SensorTypeEntity type2=sensorTypeDao.findByName("EC");
				
				SensorEntity sensor1=new SensorEntity();
				sensor1.setDeviceEntity(result);
				sensor1.setName("humidity");
				sensor1.setSensorTypeEntity(type1);
				
				sensorDao.save(sensor1);
				
				SensorEntity sensor2=new SensorEntity();
				sensor2.setDeviceEntity(result);
				sensor2.setName("temperature");
				sensor2.setSensorTypeEntity(type1);
				
				sensorDao.save(sensor2);
				
				SensorEntity sensor3=new SensorEntity();
				sensor3.setDeviceEntity(result);
				sensor3.setName("ec");
				sensor3.setSensorTypeEntity(type2);
				
				sensorDao.save(sensor3);
			}
		}
				
		return result;
	}
	
}
