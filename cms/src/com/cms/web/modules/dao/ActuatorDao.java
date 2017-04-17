package com.cms.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.Actuator;
import com.cms.web.modules.entity.Sensor;

public interface ActuatorDao  extends BaseDao<Actuator, Long>{

	List<Actuator> findByUserId(Long pid);
	
}
