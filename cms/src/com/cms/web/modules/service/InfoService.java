package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.Info;
import com.cms.web.modules.entity.Sensor;

public interface InfoService extends BaseService<Info, Long> {
	
	List<Info> findAllInfo();

}
