package com.cms.web.modules.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.GylAdmin;

public interface GylAdminDao  extends BaseDao<GylAdmin, Long>{
	/**
	 * 根据用户名查询用户 
	 */
	GylAdmin findByUsername(String username);

	List<GylAdmin> findByOrgId(@Param("orgId") Long orgId);

	List<GylAdmin> findByDutyId(@Param("dutyId")Long id);
	
	
	List<GylAdmin> findByUsernameLike(String username);
	/**
	 * 获取所有的交易员或者客服或者其他（通过角色标签查询所有的管理员）
	 */
	List<GylAdmin> findByRoleTag(String roleTag);
	
	
	public List<GylAdmin> findCustomerList();
	/**
	 * 随机获取交易员一名，分配交易员
	 */
	public Long setFollow();
	
	
	/**
	 * 查询交易部门下的交易员
	 * @param orgId
	 * @return
	 */
	public List<GylAdmin> findTranzList(@Param("orgId")Long orgId);
	
	/**
	 * 查询部门下交易员的交易记录并按照交易总额降序排列
	 * @param idStr
	 * @param startDate
	 * @param endDate
	 * @return
	 */
//	public List<TvPhbDto> getTvPhbInfoByIdstr(@Param("idStr")String idStr, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	/**
	 * 获取所有的交易员
	 * @return
	 */
	List<GylAdmin> getAllTranz();
}
