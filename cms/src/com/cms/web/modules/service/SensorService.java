package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.Sensor;

public interface SensorService extends BaseService<Sensor, Long> {
	
	List<Sensor> findByUersId(Long userId);

}
