package com.cms.web.modules.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.cms.web.modules.dao.GylOrgDao;
import com.cms.web.modules.entity.GylOrg;
import com.cms.web.modules.service.GylOrgService;

@Service("gylOrgService")
public class GylOrgServiceImpl extends BaseServiceImpl<GylOrg, Long> implements GylOrgService {

	@Resource(name = "gylOrgDao")
	private GylOrgDao gylOrgDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(gylOrgDao);
	}

	@Override
	public List<GylOrg> treeSelect() {
		List<GylOrg> list = this.getDB().list("select id,pid,name from mcpfp_gyl_org where flag = 1", GylOrg.class);
		return list;
	}

	@Override
	public List<GylOrg> findByPid(Long id) {
		List<GylOrg> list = this.getDB().list("select id, pid, `name`, `level`, type, sort, tree_path as treePath, chief_id as chiefId, chief_name as chiefName, higher_id as higherId, higher_name as higherName, tag, flag, create_time from mcpfp_gyl_org where flag = 1 and pid=?", GylOrg.class,id);
		return list;
	}

	@Override
	public List<Long> getOrgChidrenAll(Long oid){
		List<Long> result = Lists.newArrayList();
		List<GylOrg> orgs = this.findByPid(oid);
		while(orgs != null && orgs.size() > 0){
			List<GylOrg> allChildren = Lists.newArrayList();
			for (GylOrg o : orgs) {
				if(!result.contains(o.getId())){
					result.add(o.getId());
				}
				List<GylOrg> childOrgs = this.findByPid(o.getId());
				allChildren.addAll(childOrgs);
			}
			orgs = allChildren;
		}
		return result;
	}

	@Override
	public List<GylOrg> findByCheifId(Long cheifId) {
		return gylOrgDao.findByCheifId(cheifId);
	}
}
