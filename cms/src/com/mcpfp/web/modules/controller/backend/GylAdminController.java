package com.mcpfp.web.modules.controller.backend;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.generic.page.domain.Page;
import com.framework.generic.page.domain.PageList;
import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.modules.dto.GylAdminDto;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.entity.GylRole;
import com.mcpfp.web.modules.enums.SexEnum;
import com.mcpfp.web.modules.service.GylAdminService;
import com.mcpfp.web.modules.service.GylOrgService;
import com.mcpfp.web.modules.service.GylRoleService;
@Controller
@RequestMapping(value="/admin/admin")
public class GylAdminController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(GylAdminController.class);
	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	@Resource(name = "gylOrgService")
	private GylOrgService gylOrgService;
	@Resource(name = "gylRoleService")
	private GylRoleService gylRoleService;
	
	@RequestMapping(value="")
	public String list(Page<GylAdmin> page, GylAdminDto gylAdminDto,ModelMap model){
		page.setParameter(gylAdminDto);
		PageList<GylAdmin> findPage = gylAdminService.findPage( page);
		model.put("pageList", findPage);
		model.put("gylAdminDto", gylAdminDto);
		return "/backend/admin/admin_list";
	}
	
	@RequestMapping(value="is_exist_username",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Boolean checkLoginName(String username){
		GylAdmin admin = gylAdminService.findByUsername(username.toLowerCase().trim());
		return admin== null ? true : false;
	}
	
	@RequestMapping(value = "add")
	public String add(ModelMap modeMap){
		List<GylRole> roles = gylRoleService.findAll();
		modeMap.addAttribute("roles",roles);
		modeMap.addAttribute("sexValues",SexEnum.values());
		return "/backend/admin/admin_add";
	}
	
	@RequestMapping(value = "edit")
	public String edit(Long id,ModelMap modeMap){
		List<GylRole> roles = gylRoleService.findAll();
		GylAdmin gylAdmin = gylAdminService.find(id);
		modeMap.addAttribute("roles",roles);
		modeMap.addAttribute("sexValues",SexEnum.values());
		modeMap.addAttribute("gylAdmin", gylAdmin);
		return "/backend/admin/admin_edit";
	}
	
	@RequestMapping(value = "save")
	public String save(GylAdmin admin,RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isNotBlank(admin.getPassword())){
				admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			}
			admin.setModifyTime(new Date());
			admin.setCreateTime(new Date());
			gylAdminService.save(admin);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "用户操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "用户操作失败:"+e.getMessage());
		}
		return "redirect:/admin/admin";
	}
	@RequestMapping(value = "register",produces="application/json;charset=UTF-8")
	public String register(GylAdmin admin,RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isNotBlank(admin.getPassword())){
				admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			}
			admin.setModifyTime(new Date());
			admin.setCreateTime(new Date());
			admin.setRoleId(new Long(18));
			gylAdminService.save(admin);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "注册成功，请登录");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "注册失败，请重新注册"+e.getMessage());
		}
		 return "redirect:/admin/main/login";
	}
	
	
	@RequestMapping(value = "update")
	public String update(GylAdmin admin,RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isNotBlank(admin.getPassword())){
				admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			}
			admin.setModifyTime(new Date());
			gylAdminService.update(admin);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "用户操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "用户操作失败:"+e.getMessage());
		}
		return "redirect:/admin/admin";
	}
	
	@RequestMapping(value = "delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id,RedirectAttributes redirectAttributes){
		try {
			gylAdminService.delete(id);
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "用户操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "用户操作失败:"+e.getMessage(), null);
		}
	}
	
	
	/**
	 * 选择admin
	 *@return
	 */
	@RequestMapping(value ="admin_select",method = RequestMethod.GET)
	public String select(Page<GylAdmin> page, GylAdminDto gylAdminDto,ModelMap model){
		page.setParameter(gylAdminDto);
		PageList<GylAdmin> findPage = gylAdminService.findPage( page);
		model.put("pageList", findPage);
		model.put("gylAdminDto", gylAdminDto);
		return "/backend/admin/admin_select";
	}
	
}
