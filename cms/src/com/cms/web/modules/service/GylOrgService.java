package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.GylOrg;
/**
 * 
 * 项目名称：zhg-web    
 * 类名称：GylOrgService    
 * 类描述：组织架构 
 * 创建人：liujunqing    
 * 创建时间：2015年12月9日  
 * @version 1.0    
 *
 */
public interface GylOrgService extends BaseService<GylOrg, Long> {
	
	public List<GylOrg> treeSelect();

	public List<GylOrg> findByPid(Long id);
	
	public List<Long> getOrgChidrenAll(Long oid);
	
	/**
	 * 获取下属的部门
	 *@param cheifId
	 *@return
	 */
	public List<GylOrg> findByCheifId(Long cheifId);
}
