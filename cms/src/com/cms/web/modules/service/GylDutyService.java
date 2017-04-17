package com.cms.web.modules.service;

import java.util.List;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.GylDuty;
/**
 * 
 * 项目名称：zhg-web    
 * 类名称：GylOrgService    
 * 类描述：职位
 * 创建人：liujunqing    
 * 创建时间：2015年12月9日  
 * @version 1.0    
 *
 */
public interface GylDutyService extends BaseService<GylDuty, Long> {
	
	public List<GylDuty> treeSelect();

	public List<GylDuty> findByPid(Long id);
}
