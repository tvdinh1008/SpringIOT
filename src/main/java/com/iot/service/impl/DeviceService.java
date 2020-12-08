package com.iot.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.converter.DeviceBeanUtil;
import com.iot.dao.IDeviceDao;
import com.iot.dao.ISensorDao;
import com.iot.dto.DeviceDto;
import com.iot.entity.DeviceEntity;
import com.iot.entity.SensorEntity;
import com.iot.service.IDeviceService;

@Service
public class DeviceService implements IDeviceService {
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private ISensorDao sensorDao;

	@Override
	public DeviceDto findById(Long id) {
		return DeviceBeanUtil.entity2Dto(deviceDao.findById(id));
	}

	@Override
	@Transactional
	public DeviceDto save(DeviceDto dto) {
		DeviceDto result = null;
		if (dto != null && dto.getId() != null) {
			// cập nhật device

		} else if (dto != null && dto.getId() == null) {
			dto.setCreated_at(new Date());
			dto.setUpdated_at(new Date());
			try {
				DeviceEntity entity = DeviceBeanUtil.dto2Entity(dto);
				entity = deviceDao.save(entity);
				// String code[]= {"SENSOR1","SENSOR2","SENSOR3","SENSOR4","SENSOR5"};
				for(SensorEntity sensor:entity.getSensorList()) {
					sensor.setDeviceEntity(entity);
					sensorDao.save(sensor);
				}
				result=DeviceBeanUtil.entity2Dto(entity);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public DeviceDto getListSensor(Long id) {
		DeviceDto result = null;
		String JOIN_FETCH = "sensorList";
		result = DeviceBeanUtil.entity2Dto(deviceDao.findByIdWithProp(id, JOIN_FETCH));
		return result;
	}

}
