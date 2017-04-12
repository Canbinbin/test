package com.mcpfp.web.modules.controller.backend;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
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
import com.mcpfp.web.modules.entity.Info;
import com.mcpfp.web.modules.entity.Sensor;
import com.mcpfp.web.modules.service.ActuatorService;
import com.mcpfp.web.modules.service.InfoService;
import com.mcpfp.web.modules.service.SensorService;
@Controller
@RequestMapping(value="/admin/ana")
public class AnalyseController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(AnalyseController.class);
	
	@Resource(name = "sensorService")
	private SensorService sensorService;
	
	@Resource(name = "infoService")
	private InfoService infoService;
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
	
	
	
	@RequestMapping(value = "viewAct",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String viewAct(Long id,ModelMap model){
		System.out.println("viewAct id = "+id);
		return "/backend/analyse/ana_detail";
	}
	
	
	@RequestMapping(value = "viewSen",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String viewSen(Long id,ModelMap model){
		System.out.println("viewSen id = "+id);
		
		return "/backend/analyse/ana_detail";
	}
	
	@RequestMapping(value="/info", method={RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public @ResponseBody String findinfo() {
		List<Info> infos = infoService.findAllInfo();
		
		return this.jsonPrint(1,"成功", infos);
	}
	@RequestMapping(value="/infonum", method={RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public @ResponseBody String info() {
		List<Info> infos = infoService.findAllInfo();
		int[] score = new int[100];
		for (int i = 0; i < score.length; i++) {
			score[i] = 0;
		}
		for (int i = 0; i < infos.size(); i++) {
			try {
				Float k= Float.valueOf(infos.get(i).getNum());
				score[(int) (k*10)]++;
			} catch (Exception e) {
				continue;
			}
		}
		return this.jsonPrint(1,"成功", score);
	}
}
