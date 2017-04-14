package com.mcpfp.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.mcpfp.web.modules.entity.Camera;

public interface CameraDao extends BaseDao<Camera, Long>{

	List<Camera> findByUserId(Long pid);
	
}
