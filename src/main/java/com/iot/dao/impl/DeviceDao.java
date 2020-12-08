package com.iot.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.iot.dao.IDeviceDao;
import com.iot.entity.DeviceEntity;

@Repository
public class DeviceDao extends AbstractDao<Long, DeviceEntity> implements IDeviceDao {

	@Override
	public DeviceEntity findByIdAndUsername(String username, Long id) {
		DeviceEntity result = null;
		try {
			String sql = "Select t from " + getPersistenceClassName() + " t" + " JOIN FETCH t.userEntity u"
					+ " where u.username=:username and t.id=:id";

			Query q = entityManager.createQuery(sql);
			q.setParameter("username", username);
			q.setParameter("id", id);
			result = (DeviceEntity) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public DeviceEntity findByIdAndUserID(Long userID, Long id) {
		DeviceEntity result = null;
		try {
			String sql = "Select t from " + getPersistenceClassName() + " t" + " JOIN FETCH t.userEntity u"
					+ " where u.id=:userid and t.id=:id";

			Query q = entityManager.createQuery(sql);
			q.setParameter("userid", userID);
			q.setParameter("id", id);
			result = (DeviceEntity) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public DeviceEntity findByIdWithProp(Long id, String JOIN_FETCH) {
		DeviceEntity result = null;
		try {
			String sql = "Select t from " + getPersistenceClassName() + " t" + " JOIN FETCH t." + JOIN_FETCH
					+ " where t.id=:id";

			Query q = entityManager.createQuery(sql);
			q.setParameter("id", id);
			result = (DeviceEntity) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

}
