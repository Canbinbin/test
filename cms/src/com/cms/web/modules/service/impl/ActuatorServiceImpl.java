package com.cms.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.cms.web.modules.dao.ActuatorDao;
import com.cms.web.modules.dao.SensorDao;
import com.cms.web.modules.entity.Actuator;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.ActuatorService;
import com.cms.web.modules.service.SensorService;

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
