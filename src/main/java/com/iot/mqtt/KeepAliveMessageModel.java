package com.iot.mqtt;

public class KeepAliveMessageModel {
	
	/*
	 * crop ID, user ID and device ID 
	 */
	String device_token;
	/*
	 * 
	 */
	Long deviceId;
	
	public String getDevice_token() {
		return device_token;
	}
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
}
