package com.cms.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.Camera;

public interface CameraDao extends BaseDao<Camera, Long>{

	List<Camera> findByUserId(Long pid);
	
	void updateBydeviceSerial(Camera camera);
}
