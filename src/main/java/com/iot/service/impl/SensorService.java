package com.iot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.dao.ISensorDao;
import com.iot.dao.ISensorDataDao;
import com.iot.entity.SensorDataEntity;
import com.iot.entity.SensorEntity;
import com.iot.mqtt.CollectDataModel;
import com.iot.service.ISensorService;

@Service
public class SensorService implements ISensorService{
	
	@Autowired
	private ISensorDao sensorDao;
	@Autowired
	private ISensorDataDao sensorDataDao;
	
	@Override
	public SensorEntity save(SensorEntity entity) {
		SensorEntity result=null;
		if(entity!=null && entity.getId()!=null) {
			
		}else if(entity !=null && entity.getId()==null) {
			result=sensorDao.save(entity);
		}
		return result;
	}

	@Override
	public List<SensorEntity> findByListDeviceId(Long id) {
		return sensorDao.findByListDeviceId(id);
	}

	@Override
	public void saveCollectData(CollectDataModel collectData) {
		List<SensorEntity> result=findByListDeviceId(collectData.getId());
		for(SensorEntity item:result) {
			SensorDataEntity entity=new SensorDataEntity();
			entity.setSensorEntity(item);
			if(item.getName().equals("temperature")) {
				entity.setValue(collectData.getTemperature());
			}else if(item.getName().equals("humidity")) {
				entity.setValue(collectData.getHumidity());
			}else if(item.getName().equals("ec")) {
				entity.setValue(collectData.getEc());
			}
			sensorDataDao.save(entity);
		}
		
	}
	
	
}
