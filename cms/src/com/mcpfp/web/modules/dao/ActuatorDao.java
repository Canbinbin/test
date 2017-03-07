package com.mcpfp.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.mcpfp.web.modules.entity.Actuator;
import com.mcpfp.web.modules.entity.Sensor;

public interface ActuatorDao  extends BaseDao<Actuator, Long>{

	List<Actuator> findByUserId(Long pid);
	
}
