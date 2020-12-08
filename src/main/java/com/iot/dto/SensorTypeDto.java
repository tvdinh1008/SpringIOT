package com.iot.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SensorTypeDto implements Serializable {

	private static final long serialVersionUID = 4440653054941439294L;

	private Long id;
	private String name;
	private Set<SensorDto> sensorList = new HashSet<SensorDto>();

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

	public Set<SensorDto> getSensorList() {
		return sensorList;
	}

	public void setSensorList(Set<SensorDto> sensorList) {
		this.sensorList = sensorList;
	}

}
