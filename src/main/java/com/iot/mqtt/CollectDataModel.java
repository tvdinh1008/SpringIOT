package com.iot.mqtt;

public class CollectDataModel {
	private Long id;
	private String device_token;
	private Float humidity;
	private Float temperature;
	private Float ec;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDevice_token() {
		return device_token;
	}
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
	public Float getHumidity() {
		return humidity;
	}
	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Float getEc() {
		return ec;
	}
	public void setEc(Float ec) {
		this.ec = ec;
	}
	
	
}
