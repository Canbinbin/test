package com.cms.web.modules.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.generic.dao.BaseDao;
import com.cms.web.modules.entity.GylDuty;

public interface GylDutyDao  extends BaseDao<GylDuty, Long>{

	List<GylDuty> findByPid(@Param("pid") Long pid);
	
}
