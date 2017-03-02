package com.mcpfp.web.modules.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mcpfp.web.common.shiro.Principal;
import com.mcpfp.web.modules.dao.GylMenuDao;
import com.mcpfp.web.modules.entity.GylMenu;
import com.mcpfp.web.modules.enums.MenuTypeEnum;
import com.mcpfp.web.modules.service.GylMenuService;

@Service("gylMenuService")
public class GylMenuServiceImpl extends BaseServiceImpl<GylMenu, Long> implements GylMenuService {
	
	@Resource(name="gylMenuDao")
	private GylMenuDao gylMenuDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(gylMenuDao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GylMenu> findMenuBy(Long adminId, Long pid, MenuTypeEnum type) {
		Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
		List<GylMenu> list = Lists.newArrayList() ;
		if (principal.getRoleId() == 1L){
			list = this.getDB().list("select id, pid, `name`, type, `level`, sort, tree_path as treePath, url, permission, flag, create_time as createTime, modify_time as modifyTime from mcpfp_gyl_menu m where  m.flag = 1 and m.type=?",GylMenu.class, type.getKey());
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DISTINCT m.* FROM `mcpfp_gyl_menu` m  "+
						"  LEFT JOIN mcpfp_gyl_role_menu rm ON rm.`menu_id` = m.`id`"+
						"  LEFT JOIN mcpfp_gyl_role r ON rm.`role_id` = r.`id`"+
						"  LEFT JOIN mcpfp_gyl_admin a ON a.`role_id` = r.`id`"+
						"  WHERE m.`flag` =1 AND r.`flag` = 1 AND a.`flag` = 1 AND m.level=1 ");
			Map<String,Object> param = Maps.newHashMap();
			if(adminId != null && adminId > 0){
				sql.append("AND a.id = #{adminId}");
				param.put("adminId", adminId);
			}
			if(pid != null && pid > 0){
				sql.append(" AND　m.pid = #{pid}");
				param.put("pid", pid);
			}
			if (type != null){
				sql.append(" AND m.`type` = #{type}");
				param.put("type", type.getKey());
			}
			 list = this.getDB().listScript(sql.toString(), GylMenu.class, param);
		}
		list.forEach((m) -> {
			List<GylMenu> children = findByPid(m.getId());
			m.setChildMenus(children);
		});
		return list;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<GylMenu> findByPid(Long pid){
		return this.getDB().list("select id, pid, `name`, type, `level`, sort, tree_path as treePath, url, permission, flag, create_time as createTime, modify_time as modifyTime from mcpfp_gyl_menu m where m.flag = 1 and  m.pid = ?", GylMenu.class, pid);
	}
	
	
	@Override
	public List<GylMenu> treeSelect() {
		List<GylMenu> list = this.getDB().list("select id,pid,name from mcpfp_gyl_menu where flag = 1", GylMenu.class);
		return list;
	}


}
