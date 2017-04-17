package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.Camera;

public interface CameraService extends BaseService<Camera, Long> {
	
	List<Camera> findByUersId(Long userId);

}
