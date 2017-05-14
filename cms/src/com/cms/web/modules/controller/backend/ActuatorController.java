package com.cms.web.modules.controller.backend;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.generic.page.domain.Page;
import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.entity.Actuator;
import com.cms.web.modules.service.ActuatorService;
@Controller
@RequestMapping(value="/admin/act")
public class ActuatorController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(ActuatorController.class);
	
	@Resource(name = "actuatorService")
	private ActuatorService actuatorService;
	
	@RequestMapping(value="")
	public String list(ModelMap model){
		Long userId = getPrincipal().getOrgId();
		List<Actuator> lists = actuatorService.findByUersId(userId);
		model.put("pageList", lists);
		return "/backend/actuator/act_list";
	}
	
	@RequestMapping(value="/add")
	public String add(){
		return "/backend/actuator/act_add";
	}
	
	@RequestMapping(value = "save")
	public String save(Actuator actuator,RedirectAttributes redirectAttributes){
		try {
			
			actuator.setActTime(new Date());
			actuator.setUserId(getPrincipal().getUserId());
			long orgId = getPrincipal().getOrgId();
			actuator.setOrgId(orgId);
			actuatorService.save(actuator);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器添加失败:"+e.getMessage());
		}
		return "redirect:/admin/act";
	}
	@RequestMapping(value = "updatesave")
	public String update(Actuator actuator,RedirectAttributes redirectAttributes){
		try {
			
			actuator.setActTime(new Date());
			actuator.setUserId(getPrincipal().getUserId());
			actuatorService.update(actuator);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/act";
	}
	
	@RequestMapping(value = "update")
	public String update(Long id,ModelMap model){
		Actuator actuator = actuatorService.find(id);
		model.put("actuator", actuator);
		return "/backend/actuator/act_edit";
	}
	
	@RequestMapping(value = "delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id,RedirectAttributes redirectAttributes){
		try {
			actuatorService.delete(id);
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
}
