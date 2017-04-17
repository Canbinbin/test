package com.cms.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.cms.web.modules.dao.SensorDao;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.SensorService;

@Service("sensorService")
public class SensorServiceImpl extends BaseServiceImpl<Sensor, Long> implements SensorService {
	
	@Resource(name="sensorDao")
	private SensorDao sensorDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(sensorDao);
	}

	@Override
	public List<Sensor> findByUersId(Long pid) {
		// TODO Auto-generated method stub
		return sensorDao.findByUserId(pid);
	}

	

	


}
