package com.mcpfp.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.mcpfp.web.modules.entity.Sensor;

public interface SensorService extends BaseService<Sensor, Long> {
	
	List<Sensor> findByUersId(Long userId);

}
