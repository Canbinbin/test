package com.mcpfp.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.mcpfp.web.modules.entity.Camera;

public interface CameraService extends BaseService<Camera, Long> {
	
	List<Camera> findByUersId(Long userId);

}
