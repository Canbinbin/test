package com.mcpfp.web.modules.dao;

import java.util.List;

import com.framework.generic.dao.BaseDao;
import com.mcpfp.web.modules.entity.Info;
import com.mcpfp.web.modules.entity.Sensor;

public interface InfoDao  extends BaseDao<Info, Long>{

	
	List<Info> findAllInfo();

	
}
