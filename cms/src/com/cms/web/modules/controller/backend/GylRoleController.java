package com.cms.web.modules.controller.backend;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.generic.page.domain.Page;
import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.entity.GylRole;
import com.cms.web.modules.enums.RoleTagEnum;
import com.cms.web.modules.service.GylAdminService;
import com.cms.web.modules.service.GylMenuService;
import com.cms.web.modules.service.GylRoleService;
/**
 * 
 * 项目名称：zhg-web    
 * 类名称：GylRoleController    
 * 类描述： 角色控制类   
 * 创建人：liujunqing    
 * 创建时间：2015年12月10日  
 * @version 1.0    
 *
 */
@Controller
@RequestMapping(value="/admin/role")
public class GylRoleController extends BaseController{
	@Resource(name="gylRoleService")
	GylRoleService gylRoleService;
	
	@Resource(name = "gylAdminService")
	GylAdminService gylAdminService;
	
	@Resource(name = "gylMenuService")
	GylMenuService gylMenuService;
	
	/**
	 *列表 
	 */
	@RequestMapping(value="")
	public String index(Page<GylRole> page, GylRole role, ModelMap model){
		model.addAttribute("roleList", gylRoleService.findAll());
		return "/backend/role/role_list";
	}
	
	/**
	 * 增加
	 */
	@RequestMapping(value="/add")
	public String add(GylRole role,String menu_id,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("roleTags", RoleTagEnum.values());
			return "/backend/role/role_add";
		}else{
			gylRoleService.save(role,menu_id);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/role";
		}
		
	}
	
	/**
	 *修改 
	 */
	@RequestMapping(value = "/edit")
	public String edit(GylRole role,String menu_id, ModelMap model, RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("roleTags", RoleTagEnum.values());
			model.addAttribute("role", gylRoleService.find(role.getId()));
			model.addAttribute("menuIds", gylRoleService.getMenuIds(role.getId()));
			return "/backend/role/role_edit";
		} else {
			role.setModifyTime(new Date());
			gylRoleService.update(role,menu_id);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/role";
		}
	}
	
	
	/**
	 *删除 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String delete(Long id,ModelMap model){
		try {
			gylRoleService.delete(id);
		} catch (Exception e) {
			return this.jsonPrint(-1, e.getMessage(), null);
		}
		return this.jsonPrint(1, "操作成功", null);
	}
	
}
