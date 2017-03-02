package com.mcpfp.web.modules.service;

import java.util.List;
import java.util.Set;

import com.framework.generic.service.BaseService;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.enums.RoleTagEnum;

public interface GylAdminService extends BaseService<GylAdmin, Long> {
	public GylAdmin findByUsername(String username);
	
	public Boolean updateProfile(GylAdmin admin);
	
	public List<GylAdmin> findByOrgId(Long orgId);

	public List<GylAdmin> findByDutyId(Long id);
	
	public List<GylAdmin> findByUsernameLike(String username);
	
	/**
	 * 获取客服列表
	 * @return
	 */
	public List<GylAdmin> findCustomerList();
	/**
	 * 获取所有的交易员或者客服或者其他（通过角色标签查询所有的管理员）
	 */
	public List<GylAdmin> findByRoleTag(RoleTagEnum roleTag);
	
	public Set<GylAdmin> findChildren(Long adminId);
	
	public List<Long> findChildeIds(Long adminId);
	/**
	 * 随机获取交易员一名，分配交易员
	 */
	public Long setFollow();
	
	/**
	 * 查询所有交易员
	 * @return
	 */
	public List<GylAdmin> getAllTranz();
}


