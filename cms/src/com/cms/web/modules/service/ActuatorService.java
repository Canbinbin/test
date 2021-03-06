package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.Actuator;

public interface ActuatorService extends BaseService<Actuator, Long> {
	
	List<Actuator> findByUersId(Long userId);

}
