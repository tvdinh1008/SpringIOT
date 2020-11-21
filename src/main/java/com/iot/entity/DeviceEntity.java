package com.iot.entity;

import java.util.Date;
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
@Table (name = "device")
public class DeviceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "alive")
	private Integer alive;
	@Column(name = "created_at")
	private Date created_at;
	@Column(name="updated_at")
	private Date updated_at;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	@JsonIgnore
	@OneToMany(mappedBy = "deviceEntity",fetch = FetchType.LAZY)
	private Set<SensorEntity> sensorList;
	
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
	public UserEntity getUser() {
		return userEntity;
	}

	public void setUser(UserEntity user) {
		this.userEntity = user;
	}
	
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

	public Set<SensorEntity> getSensorList() {
		return sensorList;
	}

	public void setSensorList(Set<SensorEntity> sensorList) {
		this.sensorList = sensorList;
	}
}