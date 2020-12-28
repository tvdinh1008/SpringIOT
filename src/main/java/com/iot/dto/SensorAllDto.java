package com.iot.dto;

import java.util.ArrayList;
import java.util.List;

public class SensorAllDto {
	Long id;
	String code;
	Integer status;
	List<DataDto> listData = new ArrayList<DataDto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<DataDto> getListData() {
		return listData;
	}

	public void setListData(List<DataDto> listData) {
		this.listData = listData;
	}

}
