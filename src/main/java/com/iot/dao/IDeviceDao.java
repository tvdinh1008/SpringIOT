package com.iot.dao;

import com.iot.entity.DeviceEntity;

public interface IDeviceDao extends GenericDao<Long, DeviceEntity>{
	DeviceEntity findByIdAndUsername(String username, Long id);
}
