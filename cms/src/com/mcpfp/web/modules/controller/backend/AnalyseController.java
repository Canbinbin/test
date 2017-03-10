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
import com.mcpfp.web.common.controller.BaseController.MessageTypeEnum;
import com.mcpfp.web.modules.entity.Actuator;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.entity.Sensor;
import com.mcpfp.web.modules.service.ActuatorService;
import com.mcpfp.web.modules.service.SensorService;
@Controller
@RequestMapping(value="/admin/ana")
public class AnalyseController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(AnalyseController.class);
	
	@Resource(name = "sensorService")
	private SensorService sensorService;
	@Resource(name = "actuatorService")
	private ActuatorService actuatorService;
	
	@RequestMapping(value="")
	public String list(ModelMap model){
		Long userId = getPrincipal().getUserId();
		List<Sensor> lists = sensorService.findByUersId(userId);
		model.put("pageList", lists);
		List<Actuator> actLists = actuatorService.findByUersId(userId);
		model.put("actList", actLists);
		return "/backend/analyse/ana_list";
	}
	
	@RequestMapping(value="/add")
	public String add(Page<Sensor> page, Long userId,ModelMap model){
		
		return "/backend/sensor/sen_add";
	}
	
	@RequestMapping(value = "save")
	public String save(Sensor sensor,RedirectAttributes redirectAttributes){
		try {
			
			sensor.setSenTime(new Date());
			sensor.setUserId(getPrincipal().getUserId());
			long orgId = getPrincipal().getOrgId();
			sensor.setOrgId(orgId);
			sensorService.save(sensor);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器添加失败:"+e.getMessage());
		}
		return "redirect:/admin/sen";
	}
	@RequestMapping(value = "updatesave")
	public String update(Sensor sensor,RedirectAttributes redirectAttributes){
		try {
			
			sensor.setSenTime(new Date());
			sensor.setUserId(getPrincipal().getUserId());
			sensorService.update(sensor);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/sen";
	}
	
	@RequestMapping(value = "update")
	public String update(Long id,ModelMap model){
		Sensor sensor = sensorService.find(id);
		model.put("sensor", sensor);
		return "/backend/sensor/sen_edit";
	}
	
	@RequestMapping(value = "delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id,RedirectAttributes redirectAttributes){
		try {
			sensorService.delete(id);
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
}
