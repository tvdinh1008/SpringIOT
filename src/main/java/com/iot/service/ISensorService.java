package com.iot.service;

import java.util.List;

import com.iot.entity.SensorEntity;
import com.iot.mqtt.CollectDataModel;

public interface ISensorService {
	SensorEntity save(SensorEntity entity);
	List<SensorEntity> findByListDeviceId(Long id);
	void saveCollectData(CollectDataModel collectData);
}
