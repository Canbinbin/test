package com.mcpfp.web.modules.service;

import com.framework.generic.service.BaseService;
import com.mcpfp.web.modules.entity.GylRole;

public interface GylRoleService extends BaseService<GylRole, Long> {

	String getMenuIds(Long roleId);

	Long save(GylRole role, String menuIds);

	void update(GylRole role, String menu_id);

}
