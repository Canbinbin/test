package com.cms.web.modules.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.cms.web.modules.dao.GylDutyDao;
import com.cms.web.modules.entity.GylDuty;
import com.cms.web.modules.service.GylDutyService;

@Service("gylDutyService")
public class GylDutyServiceImpl extends BaseServiceImpl<GylDuty, Long> implements GylDutyService {

	@Resource(name = "gylDutyDao")
	private GylDutyDao gylDutyDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(gylDutyDao);
	}


	@Override
	public List<GylDuty> treeSelect() {
		List<GylDuty> list = this.getDB().list("select id,pid,name from mcpfp_gyl_duty where flag = 1", GylDuty.class);
		return list;
	}


	@Override
	public List<GylDuty> findByPid(Long id) {
		return gylDutyDao.findByPid(id);
	}
	
}
