package com.iot.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sensor")
public class SensorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name", columnDefinition = "nvarchar(250)")
	private String name;
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "sensor_type")
	private SensorTypeEntity sensorTypeEntity;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sensorEntity",fetch = FetchType.LAZY)
	private Set<SensorDataEntity> sensorDataList;
	
	@ManyToOne(optional = false,fetch = FetchType.LAZY)
	@JoinColumn(name = "device")
	private DeviceEntity deviceEntity;
	
	public DeviceEntity getDeviceEntity() {
		return deviceEntity;
	}
	public void setDeviceEntity(DeviceEntity deviceEntity) {
		this.deviceEntity = deviceEntity;
	}
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
	public SensorTypeEntity getSensorTypeEntity() {
		return sensorTypeEntity;
	}
	public void setSensorTypeEntity(SensorTypeEntity sensorTypeEntity) {
		this.sensorTypeEntity = sensorTypeEntity;
	}
	public Set<SensorDataEntity> getSensorDataList() {
		return sensorDataList;
	}
	public void setSensorDataList(Set<SensorDataEntity> sensorDataList) {
		this.sensorDataList = sensorDataList;
	}
	
	
}