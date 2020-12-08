package com.iot.converter;

import com.iot.dto.DeviceDto;
import com.iot.dto.SensorDto;
import com.iot.entity.DeviceEntity;
import com.iot.entity.SensorEntity;

public class DeviceBeanUtil {

	public static DeviceDto entity2Dto(DeviceEntity entity) {
		DeviceDto dto = new DeviceDto();
		dto.setId(entity.getId());
		dto.setAlive(entity.getAlive());
		dto.setName(entity.getName());
		dto.setCreated_at(entity.getCreated_at());
		dto.setUpdated_at(entity.getUpdated_at());
		try {
			if (entity.getUser() != null) {
				dto.setUserDto(UserBeanUtil.entity2Dto(entity.getUser()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			for (SensorEntity sensor : entity.getSensorList()) {
				dto.getSensorList().add(SensorBeanUtil.entity2Dto(sensor, 1));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dto;
	}

	public static DeviceEntity dto2Entity(DeviceDto dto) {
		DeviceEntity entity = new DeviceEntity();
		entity.setId(dto.getId());
		entity.setAlive(dto.getAlive());
		entity.setName(dto.getName());
		entity.setCreated_at(dto.getCreated_at());
		entity.setUpdated_at(dto.getUpdated_at());

		entity.setUser(UserBeanUtil.dto2Entity(dto.getUserDto()));
		try {
			for (SensorDto sensor : dto.getSensorList()) {
				entity.getSensorList().add(SensorBeanUtil.dto2Entity(sensor));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return entity;
	}

	/*
	 * Do khi update thì những thông số như createdBy, modifiedDate,.. thì nó sẽ
	 * không có và =null Do đó ta sẽ tìm đối tượng cũ và lấy những dữ những thuộc
	 * tính cũ đó
	 */
	public static DeviceEntity dto2Entity(DeviceDto dto, DeviceEntity entity) {

		return entity;
	}
}