package com.cms.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.Info;
import com.cms.web.modules.entity.Sensor;

public interface InfoDao  extends BaseDao<Info, Long>{

	
	List<Info> findAllInfo();

	
}
