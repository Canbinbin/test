package com.mcpfp.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.mcpfp.web.modules.entity.Sensor;

public interface SensorDao  extends BaseDao<Sensor, Long>{

	List<Sensor> findByUserId(Long pid);
	
}
