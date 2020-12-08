package com.iot.service;

import com.iot.dto.DeviceDto;

public interface IDeviceService {
	DeviceDto findById(Long id);
	DeviceDto save(DeviceDto dto);
	DeviceDto getListSensor(Long id);
}
