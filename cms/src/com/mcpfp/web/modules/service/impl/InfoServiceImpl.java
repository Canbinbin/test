package com.mcpfp.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.mcpfp.web.modules.dao.InfoDao;
import com.mcpfp.web.modules.dao.SensorDao;
import com.mcpfp.web.modules.entity.Info;
import com.mcpfp.web.modules.entity.Sensor;
import com.mcpfp.web.modules.service.InfoService;
import com.mcpfp.web.modules.service.SensorService;

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
