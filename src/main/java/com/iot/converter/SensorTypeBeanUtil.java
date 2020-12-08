package com.iot.converter;

import com.iot.dto.SensorDto;
import com.iot.dto.SensorTypeDto;
import com.iot.entity.SensorEntity;
import com.iot.entity.SensorTypeEntity;

public class SensorTypeBeanUtil {

	public static SensorTypeDto entity2Dto(SensorTypeEntity entity) {
		SensorTypeDto dto = new SensorTypeDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		try {
			for (SensorEntity sensor : entity.getSensorList()) {
				dto.getSensorList().add(SensorBeanUtil.entity2Dto(sensor,0));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dto;
	}

	public static SensorTypeEntity dto2Entity(SensorTypeDto dto) {
		SensorTypeEntity entity = new SensorTypeEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		try {
			for (SensorDto sensor : dto.getSensorList()) {
				entity.getSensorList().add(SensorBeanUtil.dto2Entity(sensor));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return entity;
	}

}
