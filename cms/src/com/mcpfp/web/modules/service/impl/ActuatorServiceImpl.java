package com.mcpfp.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.mcpfp.web.modules.dao.ActuatorDao;
import com.mcpfp.web.modules.dao.SensorDao;
import com.mcpfp.web.modules.entity.Actuator;
import com.mcpfp.web.modules.entity.Sensor;
import com.mcpfp.web.modules.service.ActuatorService;
import com.mcpfp.web.modules.service.SensorService;

@Service("actuatorService")
public class ActuatorServiceImpl extends BaseServiceImpl<Actuator, Long> implements ActuatorService {
	
	@Resource(name="actuatorDao")
	private ActuatorDao actuatorDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(actuatorDao);
	}

	@Override
	public List<Actuator> findByUersId(Long pid) {
		// TODO Auto-generated method stub
		return actuatorDao.findByUserId(pid);
	}

	

	


}
