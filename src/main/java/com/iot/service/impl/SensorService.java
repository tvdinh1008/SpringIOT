package com.iot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.converter.SensorBeanUtil;
import com.iot.dao.IDeviceDao;
import com.iot.dao.ISensorDao;
import com.iot.dao.ISensorDataDao;
import com.iot.dto.SensorDto;
import com.iot.entity.DeviceEntity;
import com.iot.entity.SensorDataEntity;
import com.iot.entity.SensorEntity;
import com.iot.mqtt.CollectDataModel;
import com.iot.mqtt.DataModel;
import com.iot.service.ISensorService;

@Service
public class SensorService implements ISensorService {

	@Autowired
	private ISensorDao sensorDao;
	@Autowired
	private ISensorDataDao sensorDataDao;
	@Autowired
	private IDeviceDao deviceDao;

	@Override
	public SensorDto save(SensorDto dto) {
		SensorDto result = null;
		if (dto != null && dto.getId() != null) {

		} else if (dto != null && dto.getId() == null) {
			result = SensorBeanUtil.entity2Dto(sensorDao.save(SensorBeanUtil.dto2Entity(dto)), 0);
		}
		return result;
	}

	@Override
	public List<SensorDto> findByListDeviceId(Long id) {
		List<SensorDto> result = new ArrayList<SensorDto>();
		for (SensorEntity entity : sensorDao.findByListDeviceId(id)) {
			result.add(SensorBeanUtil.entity2Dto(entity, 0));
		}
		return result;
	}

	@Override
	public void saveCollectData(CollectDataModel collectData) {
		/*
		 * Gửi là múi giờ thứ 7 (tức 7*60*60=25200) mà hàm dưới tự lấy múi giờ của mình(+7) =>Bị cộng 2 lần múi giờ thứ 7
		 */
		Date time = new Date(collectData.getTime()*1000l - 25200000l);
		for (DataModel data : collectData.getSensorDataList()) {
			SensorDataEntity entity = new SensorDataEntity();
			SensorEntity sensor = sensorDao.findByCode(data.getCode(),collectData.getDeviceId());
			if (sensor == null || sensor.getStatus() == 0) {
				continue;
			}
			entity.setSensorEntity(sensor);
			entity.setTime(time);
			entity.setValue(data.getValue());
			sensorDataDao.save(entity);
		}
	}

	@Override
	public List<SensorDto> getAllData(Long deviceid) {
		List<Long> ids = new ArrayList<Long>();
		//
		String fetch = "sensorList";
		DeviceEntity device = deviceDao.findByIdWithProp(deviceid, fetch,1);
		if (device != null) {
			for (SensorEntity sensor : device.getSensorList()) {
				ids.add(sensor.getId());
			}
		}
		List<SensorDto> result = new ArrayList<SensorDto>();
		for (Long id : ids) {
			SensorEntity entity = sensorDao.findAllDataSensor(id);
			if (entity != null) {
				result.add(SensorBeanUtil.entity2Dto(entity, 0));
			}
		}

		return result;
	}

}
