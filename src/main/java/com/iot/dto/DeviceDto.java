package com.iot.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DeviceDto implements Serializable {

	private static final long serialVersionUID = 223210570332708483L;

	private Long id;
	private Integer alive;
	private String name;
	private Date created_at;
	private Date updated_at;
	private UserDto userDto;
	private Set<SensorDto> sensorList = new HashSet<SensorDto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAlive() {
		return alive;
	}

	public void setAlive(Integer alive) {
		this.alive = alive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public Set<SensorDto> getSensorList() {
		return sensorList;
	}

	public void setSensorList(Set<SensorDto> sensorList) {
		this.sensorList = sensorList;
	}

}
