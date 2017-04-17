package com.cms.web.modules.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.GylOrg;


/**
 *
 * GylOrgDao数据库操作接口类
 *
 */

public interface GylOrgDao extends BaseDao<GylOrg,Long>{

	List<GylOrg> findName(@Param("name")String name,@Param("id") int id);

	List<GylOrg> findByCheifId(Long cheifId);

}