package com.iot.converter;

import com.iot.dto.SensorDataDto;
import com.iot.dto.SensorDto;
import com.iot.entity.SensorDataEntity;
import com.iot.entity.SensorEntity;

public class SensorBeanUtil {

	public static SensorDto entity2Dto(SensorEntity entity, int k) {
		SensorDto dto = new SensorDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		try {
			if (entity.getSensorTypeEntity() != null) {
				dto.setSensorTypeDto(SensorTypeBeanUtil.entity2Dto(entity.getSensorTypeEntity()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			if (entity.getDeviceEntity() != null && k != 1) {
				dto.setDeviceDto(DeviceBeanUtil.entity2Dto(entity.getDeviceEntity()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			for (SensorDataEntity sensorData : entity.getSensorDataList()) {
				dto.getSensorDataList().add(SensorDataBeanUtil.entity2Dto(sensorData, 1));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dto;
	}

	public static SensorEntity dto2Entity(SensorDto dto) {
		SensorEntity entity = new SensorEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDeviceEntity(DeviceBeanUtil.dto2Entity(dto.getDeviceDto()));
		entity.setSensorTypeEntity(SensorTypeBeanUtil.dto2Entity(dto.getSensorTypeDto()));
		try {
			for (SensorDataDto sensorDataDto : dto.getSensorDataList()) {
				entity.getSensorDataList().add(SensorDataBeanUtil.dto2Entity(sensorDataDto));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return entity;
	}

}
