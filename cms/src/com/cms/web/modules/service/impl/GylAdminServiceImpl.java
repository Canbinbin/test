package com.cms.web.modules.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.cms.web.modules.dao.GylAdminDao;
import com.cms.web.modules.entity.GylAdmin;
import com.cms.web.modules.entity.GylOrg;
import com.cms.web.modules.enums.RoleTagEnum;
import com.cms.web.modules.service.GylAdminService;
import com.cms.web.modules.service.GylOrgService;

@Service("gylAdminService")
public class GylAdminServiceImpl extends BaseServiceImpl<GylAdmin, Long> implements GylAdminService {

	@Resource(name = "gylAdminDao")
	private GylAdminDao gylAdminDao;
	
	@Resource(name = "gylOrgService")
	private GylOrgService gylOrgService;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(gylAdminDao);
	}
	
	@Override
	public GylAdmin findByUsername(String username) {
		return gylAdminDao.findByUsername(username);
	}

	@Override
	public Boolean updateProfile(GylAdmin admin) {
		admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		int row = this.getDB().update("update mcpfp_gyl_admin a set a.modify_time=?, a.password=? where a.id=?", new Date(), admin.getPassword(),admin.getId());
		return row > 0;
	}

	@Override
	public List<GylAdmin> findByOrgId(Long orgId) {
		return gylAdminDao.findByOrgId(orgId);
	}

	@Override
	public List<GylAdmin> findByDutyId(Long id) {
		return gylAdminDao.findByDutyId(id);
	}

	@Override
	public List<GylAdmin> findByUsernameLike(String username) {
		return gylAdminDao.findByUsernameLike(username);
	}

	/**
	 * 获取客服列表
	 */
	@Override
	public List<GylAdmin> findCustomerList() {
		return gylAdminDao.findCustomerList();
	}

	@Override
	public List<GylAdmin> findByRoleTag(RoleTagEnum roleTag) {
		return gylAdminDao.findByRoleTag(roleTag.getKey());
	}
	
	@Override
	public Set<GylAdmin> findChildren(Long adminId) {
		Set<GylAdmin> admins = Sets.newHashSet();
		GylAdmin self = gylAdminDao.find(adminId);
		//由于可能出现一个部门的交易员是另外一个部门的交易主管的情况，所以把自己先加入
		admins.add(self);
		if(adminId == 1){
			//超级管理员显示全部人
			admins.addAll(gylAdminDao.findAll());
		}else{
			//当前所有负责的部门
			List<GylOrg> chiefOrgs = gylOrgService.findByCheifId(adminId);
			for (GylOrg org:chiefOrgs) {
				if(org != null){
					List<GylAdmin> admin = gylAdminDao.findByOrgId(org.getId());
					admins.addAll(admin);
					
					//所有下属部门id
					List<Long> childrenOrgList = gylOrgService.getOrgChidrenAll(org.getId());
					
					for(Long childOrgId:childrenOrgList){
						//下属部门的交易员
						List<GylAdmin> childrenAdminList = gylAdminDao.findByOrgId(childOrgId);
						admins.addAll(childrenAdminList);
					}
				}
			}
		}
		return admins;
	}

	@Override
	public List<Long> findChildeIds(Long adminId) {
		List<Long> ids = Lists.newArrayList();
		Set<GylAdmin> admins = this.findChildren(adminId);
		if(admins != null && admins.size() > 0){
			for(GylAdmin a:admins){
				ids.add(a.getId());
			}
		}
		return ids;
	}

	@Override
	public Long setFollow() {
		// TODO Auto-generated method stub
		return gylAdminDao.setFollow();
	}
	
	/**
	 * 获取所有的交易员
	 */
	@Override
	public List<GylAdmin> getAllTranz() {
		return gylAdminDao.getAllTranz();
	}

}
