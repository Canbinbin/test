package com.cms.web.modules.dao.activity;

import org.apache.ibatis.annotations.Param;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.activity.ActivityNbConfig;


/**
 *
 * ActivityNbConfigDao数据库操作接口类
 *
 */

public interface ActivityNbConfigDao extends BaseDao<ActivityNbConfig,Long>{

	/**
	 * 根据code查找配置
	 *@param code
	 *@return
	 */
	public ActivityNbConfig findByCode(String code);
	
	/**
	 * 根据code更新对应的value
	 *@param code
	 *@param vlaue
	 */
	public void updateByCode(@Param("code")String code,@Param("value")String vlaue);
}