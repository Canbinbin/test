package com.cms.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.cms.web.modules.dao.InfoDao;
import com.cms.web.modules.dao.SensorDao;
import com.cms.web.modules.entity.Info;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.InfoService;
import com.cms.web.modules.service.SensorService;

@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info, Long> implements InfoService {
	
	@Resource(name="infoDao")
	private InfoDao infoDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(infoDao);
	}


	@Override
	public List<Info> findAllInfo() {
		// TODO Auto-generated method stub
		return infoDao.findAllInfo();
	}

	
	

	


}
