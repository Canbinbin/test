package com.mcpfp.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.mcpfp.web.modules.entity.Info;
import com.mcpfp.web.modules.entity.Sensor;

public interface InfoService extends BaseService<Info, Long> {
	
	List<Info> findAllInfo();

}
