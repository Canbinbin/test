package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.GylMenu;
import com.cms.web.modules.enums.MenuTypeEnum;

public interface GylMenuService extends BaseService<GylMenu, Long> {
	
	List<GylMenu> findMenuBy(Long adminId, Long pid, MenuTypeEnum type);

	List<GylMenu> findByPid(Long pid);

	List<GylMenu> treeSelect();
	
}
