package com.cms.web.modules.controller.backend;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.entity.PointLogEntity;
import cn.edu.jnu.fastbits.entity.PointSearchCondition;
import cn.edu.jnu.fastbits.entity.PointTypeEntity;
import cn.edu.jnu.fastbits.entity.ValueEntity;
import cn.edu.jnu.fastbits.rest.http.MessageCode;
import cn.edu.jnu.fastbits.rest.http.Page;
import cn.edu.jnu.fastbits.rest.http.Resp;

import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.ActuatorService;
import com.cms.web.modules.service.InfoService;
import com.cms.web.modules.service.SensorService;
import com.google.common.base.Preconditions;
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
	public String list(String sPageNow,String sPageSize,ModelMap model){
		Long userId = getPrincipal().getOrgId();
		Resp<Page<PointEntity>> lists = sensorService.findByUersId(userId, sPageNow, sPageSize);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		else {
			model.put("pageList", lists.getData().getData());
			model.put("totalPages", Integer.valueOf(lists.getData().getTotalPages()));
			model.put("pageNow", Integer.valueOf(lists.getData().getPageNow()));
			model.put("totalRows", Integer.valueOf(lists.getData().getTotalRows()));
			Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
			model.put("type", resp.getData());
		}
		return "/backend/analyse/ana_list";
	}
	@RequestMapping(value="findlist")
	public String findlist(Sensor sensor,ModelMap model){
		PointSearchCondition condition = new PointSearchCondition();
		condition.setCreateTimeEnd(sensor.getCreateTimeEnd());
		condition.setCreateTimeStart(sensor.getCreateTimeStart());
		condition.setName(sensor.getName());
		condition.setParent(sensor.getParent());
		condition.setPointStatus(sensor.getStatus());
		condition.setPointType(sensor.getPointType());
		condition.setUniqueId(sensor.getUniqueId());
		condition.setUpdateTimeEnd(sensor.getUpdateTimeEnd());
		condition.setUpdateTimeStart(sensor.getUpdateTimeStart());
		Long userId = getPrincipal().getOrgId();
		Resp<Page<PointEntity>> lists = sensorService.findPointByCondition(userId+"", condition);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		model.put("pageList", lists.getData().getData());
		model.put("totalPages", Integer.valueOf(lists.getData().getTotalPages()));
		model.put("pageNow", Integer.valueOf(lists.getData().getPageNow()));
		model.put("totalRows", Integer.valueOf(lists.getData().getTotalRows()));
		Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
		model.put("type", resp.getData());
		model.put("point", sensor);
		return "/backend/analyse/ana_list";
	}
	@RequestMapping(value="/log",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String findLog(String sPageNow,String sPageSize,ModelMap model){
		
		Long userId = getPrincipal().getOrgId();
		Resp<Page<PointLogEntity>> lists = sensorService.findPointLogByOwner(userId+"", sPageNow, sPageSize);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		model.put("pageList", lists.getData().getData());
		model.put("totalPages", Integer.valueOf(lists.getData().getTotalPages()));
		model.put("pageNow", Integer.valueOf(lists.getData().getPageNow()));
		model.put("totalRows", Integer.valueOf(lists.getData().getTotalRows()));
		return "/backend/analyse/log_list";
	}
	
	@RequestMapping(value = "view",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String viewSen(String uniqueId,ModelMap model){
		System.out.println("uniqueId = "+uniqueId);
		Preconditions.checkNotNull(uniqueId);
		List<String> uniqueIds = new ArrayList<String>(1);
		uniqueIds.add(uniqueId);
		Resp<Map<String, ValueEntity>> resp = sensorService.getCurrentValues(uniqueIds);
		ValueEntity valueEntity = resp.getData().get(uniqueId);
		Resp<PointEntity> respPointEntity = sensorService.findPointById(uniqueId);
		PointEntity pointEntity = respPointEntity.getData();
		if (!resp.getMsgCode().equals(MessageCode.SUCCESS)|| !respPointEntity.getMsgCode().equals(MessageCode.SUCCESS)) {
			Preconditions.checkState(false, resp.getMsgDesc()+"/"+respPointEntity.getMsgDesc());
		}
		model.put("point", pointEntity);
		model.put("value", valueEntity);
		return "/backend/analyse/ana_detail";
	}
	
	@RequestMapping(value="/getData", method={RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getData(String uniqueId) {
		List<String> uniqueIds = new ArrayList<String>(1);
		uniqueIds.add(uniqueId);
		Resp<Map<String, ValueEntity>> resp = sensorService.getCurrentValues(uniqueIds);
		if (!resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			return this.jsonPrint(-1,resp.getMsgDesc(), null);
		}
		return this.jsonPrint(1,resp.getMsgDesc(), resp.getData().get(uniqueId));
	}
	
	@RequestMapping(value="/getDataByTime", method={RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getDataByTime(String uniqueId,String beginTime,String endTime) {
		beginTime = beginTime.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
		endTime = endTime.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
		Resp<List<ValueEntity>> resp = sensorService.getValueWithTimeInterval(uniqueId, beginTime, endTime);
		if (!resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			return this.jsonPrint(-1,resp.getMsgDesc(), null);
		}
		return this.jsonPrint(1,resp.getMsgDesc(), resp.getData());
	}
}
