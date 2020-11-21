package com.iot.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sensor_type")
public class SensorTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="name", columnDefinition = "nvarchar(250)")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sensorTypeEntity",fetch = FetchType.LAZY)
	private Set<SensorEntity> sensorList;

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

	public Set<SensorEntity> getSensorList() {
		return sensorList;
	}

	public void setSensorList(Set<SensorEntity> sensorList) {
		this.sensorList = sensorList;
	}


}