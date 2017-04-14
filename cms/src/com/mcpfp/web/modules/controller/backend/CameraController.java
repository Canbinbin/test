package com.mcpfp.web.modules.controller.backend;


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
import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.modules.entity.Actuator;
import com.mcpfp.web.modules.entity.Camera;
import com.mcpfp.web.modules.service.ActuatorService;
import com.mcpfp.web.modules.service.CameraService;
@Controller
@RequestMapping(value="/admin/camera")
public class CameraController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(CameraController.class);
	
	@Resource(name = "cameraService")
	private CameraService cameraService;
	
	@RequestMapping(value="")
	public String list(ModelMap model){
		Long userId = getPrincipal().getUserId();
		List<Camera> lists = cameraService.findByUersId(userId);
		model.put("pageList", lists);
		return "/backend/camera/cam_list";
	}
	
	@RequestMapping(value="/add")
	public String add(){
		return "/backend/camera/cam_add";
	}
	
	@RequestMapping(value = "save")
	public String save(Camera camera,RedirectAttributes redirectAttributes){
		try {
			
			camera.setCamTime(new Date());
			camera.setUserId(getPrincipal().getUserId());
			long orgId = getPrincipal().getOrgId();
			camera.setOrgId(orgId);
			camera.setAccessToken("accessToken");
			cameraService.save(camera);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器添加失败:"+e.getMessage());
		}
		return "redirect:/admin/camera";
	}
	@RequestMapping(value = "updatesave")
	public String update(Camera camera,RedirectAttributes redirectAttributes){
		try {
			
			camera.setCamTime(new Date());
			camera.setUserId(getPrincipal().getUserId());
			cameraService.update(camera);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/camera";
	}
	
	@RequestMapping(value = "update")
	public String update(Long id,ModelMap model){
		Camera camera = cameraService.find(id);
		model.put("camera", camera);
		return "/backend/camera/cam_edit";
	}
	
	@RequestMapping(value = "delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id,RedirectAttributes redirectAttributes){
		try {
			cameraService.delete(id);
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
}
