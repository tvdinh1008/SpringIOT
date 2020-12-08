package com.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.converter.SensorDataBeanUtil;
import com.iot.dao.IDeviceDao;
import com.iot.dao.ISensorDataDao;
import com.iot.dto.SensorDataDto;
import com.iot.entity.DeviceEntity;
import com.iot.entity.SensorDataEntity;
import com.iot.entity.SensorEntity;
import com.iot.service.ISensorDataService;

@Service
public class SensorDataService implements ISensorDataService{
	
	@Autowired
	private ISensorDataDao sensorDataDao;
	
	@Autowired
	private IDeviceDao deviceDao;
	
	@Override
	public List<SensorDataDto> findAllDataSensorId(Long deviceID){
		List<Long> ids=new ArrayList<Long>();
		//
		String fetch="sensorList";
		DeviceEntity device=deviceDao.findByIdWithProp(deviceID, fetch);
		if(device!=null) {
			for(SensorEntity sensor: device.getSensorList()) {
				ids.add(sensor.getId());
			}
		}
		List<SensorDataDto> result= new ArrayList<SensorDataDto>();
		for(SensorDataEntity item:sensorDataDao.findAllDataSensorId(ids)) {
			result.add(SensorDataBeanUtil.entity2Dto(item));
		}
		
		return result;
	}

	@Override
	public List<SensorDataDto> findAllDataLastSensorId(Long deviceID) {
		List<Long> ids=new ArrayList<Long>();
		//
		String fetch="sensorList";
		DeviceEntity device=deviceDao.findByIdWithProp(deviceID, fetch);
		if(device!=null) {
			for(SensorEntity sensor: device.getSensorList()) {
				ids.add(sensor.getId());
			}
		}
		List<SensorDataDto> result= new ArrayList<SensorDataDto>();
		for(SensorDataEntity item:sensorDataDao.findAllDataLastSensorId(ids)) {
			result.add(SensorDataBeanUtil.entity2Dto(item));
		}
		
		return result;
	}
}
