package com.mcpfp.web.modules.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.generic.activerecord.Record;
import com.framework.generic.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.mcpfp.web.modules.dao.GylRoleDao;
import com.mcpfp.web.modules.entity.GylRole;
import com.mcpfp.web.modules.entity.GylRoleMenu;
import com.mcpfp.web.modules.service.GylRoleService;

@Service("gylRoleService")
public class GylRoleServiceImpl extends BaseServiceImpl<GylRole, Long> implements GylRoleService {
	
	@Resource(name = "gylRoleDao")
	private GylRoleDao gylRoleDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(gylRoleDao);
	}

	@Override
	@Transactional(readOnly=true)
	public Long delete(Long id) {
		//判断当前roleid是否有关连的信息
		int count = this.getDB().count("select count(1) from mcpfp_gyl_admin where role_id=?", id); 
		if (count > 0){
			throw new RuntimeException("请先删除角色关键的下用户信息");
		}
		int count2 = this.getDB().count("select count(1) from mcpfp_gyl_role_menu where role_id=?", id);
		if (count2 > 0){
			throw new RuntimeException("请先删除角色关键的下菜单信息");
		}
		return Long.valueOf(this.getDB().update("update mcpfp_gyl_role set flag =-1 where id=?", id));
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getMenuIds(Long roleId){
		List<Record> list = this.getDB().list("SELECT menu_id FROM mcpfp_gyl_role_menu WHERE role_id=?", roleId);
		StringBuffer ids = new StringBuffer();
		int i = 0;
		for(Record r : list){
			Object id = r.get("menu_id");
			if(i==0){
				ids.append(id);
			}else {
				ids.append("," + id);
			}
			i++;
		}
		return ids.toString();
	}

	@Override
	@Transactional
	public Long save(GylRole role,String menuIds) {
		role.setCreateTime(new Date());
		role.setModifyTime(new Date());
		Long res = this.save(role);
		
		if(StringUtils.isNotBlank(menuIds)){
			String[] ids = menuIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				this.getDB().update("insert into mcpfp_gyl_role_menu(`role_id`, `menu_id`)VALUES (?, ?) ", role.getId(),ids[i]);
			}
		}
		return res;
	}

	@Override
	@Transactional
	public void update(GylRole role, String menuIds) {
		this.getDB().update("delete from mcpfp_gyl_role_menu where role_id=? ",role.getId() );
		if(StringUtils.isNotBlank(menuIds)){
			String[] ids = menuIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				this.getDB().update("insert into mcpfp_gyl_role_menu(`role_id`, `menu_id`)VALUES (?, ?) ", role.getId(),ids[i]);
			}
		}
		this.update(role);
	}
	
}
