package com.iot.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SensorDto implements Serializable {

	private static final long serialVersionUID = -6910416474795713503L;

	private Long id;
	private String name;
	private SensorTypeDto sensorTypeDto;
	private Set<SensorDataDto> sensorDataList = new HashSet<SensorDataDto>();
	private DeviceDto deviceDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SensorTypeDto getSensorTypeDto() {
		return sensorTypeDto;
	}

	public void setSensorTypeDto(SensorTypeDto sensorTypeDto) {
		this.sensorTypeDto = sensorTypeDto;
	}

	public Set<SensorDataDto> getSensorDataList() {
		return sensorDataList;
	}

	public void setSensorDataList(Set<SensorDataDto> sensorDataList) {
		this.sensorDataList = sensorDataList;
	}

	public DeviceDto getDeviceDto() {
		return deviceDto;
	}

	public void setDeviceDto(DeviceDto deviceDto) {
		this.deviceDto = deviceDto;
	}

}
