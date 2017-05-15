package com.cms.web.modules.controller.backend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hdf.extractor.data.LST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.edu.jnu.fastbits.entity.CmdCandidateEntity;
import cn.edu.jnu.fastbits.entity.CommandEntity;
import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.entity.PointSearchCondition;
import cn.edu.jnu.fastbits.entity.PointStatusEntity;
import cn.edu.jnu.fastbits.entity.PointTypeEntity;
import cn.edu.jnu.fastbits.rest.http.MessageCode;
import cn.edu.jnu.fastbits.rest.http.Page;
import cn.edu.jnu.fastbits.rest.http.Resp;

import com.alibaba.druid.sql.visitor.functions.If;
import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.SensorService;

@Controller
@RequestMapping(value="/admin/sen")
public class SensorController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(SensorController.class);
	
	@Resource(name = "sensorService")
	private SensorService sensorService;
	

	@RequestMapping(value="")
	public String list(String sPageNow,String sPageSize,ModelMap model){
		Long userId = getPrincipal().getOrgId();
		Resp<Page<PointEntity>> lists = sensorService.findByUersId(userId, sPageNow, sPageSize);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		else{
			List<PointEntity> pointEntities = lists.getData().getData();
			List<Sensor> sensors = new ArrayList<Sensor>();
			for (int i = 0; i < pointEntities.size(); i++) {
				Sensor sensor = new Sensor();
				sensor.setName(pointEntities.get(i).getName());
				sensor.setUniqueId(pointEntities.get(i).getUniqueId());
				sensor.setDes(pointEntities.get(i).getDescription());
				sensor.setCreateTime(pointEntities.get(i).getCreateTime());
				sensor.setUpdateTime(pointEntities.get(i).getUpdateTime());
				Resp<PointStatusEntity> po = sensorService.queryPointStatus(sensor.getUniqueId());
				if (po.getData().getStatus()==1) {
					sensor.setStatus("在线");
				}
				else{
					sensor.setStatus("下线");
				}
				sensors.add(sensor);
			}
			model.put("pageList", sensors);
			model.put("totalPages", Integer.valueOf(lists.getData().getTotalPages()));
			model.put("pageNow", Integer.valueOf(lists.getData().getPageNow()));
			model.put("totalRows", Integer.valueOf(lists.getData().getTotalRows()));
			Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
			model.put("type", resp.getData());
		}
		return "/backend/sensor/sen_list";
	}
	
	@RequestMapping(value="findlist")
	public String findlist(Sensor sensor,String sPageNow,String sPageSize,ModelMap model){
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
		condition.setPageNow((null==sPageNow)? "1":sPageNow);
		condition.setPageSize((null==sPageSize)?"10":sPageSize);
		
		Long userId = getPrincipal().getOrgId();
		Resp<Page<PointEntity>> lists = sensorService.findPointByCondition(userId+"", condition);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		else {
			List<PointEntity> pointEntities = lists.getData().getData();
			List<Sensor> sensors = new ArrayList<Sensor>();
			for (int i = 0; i < pointEntities.size(); i++) {
				Sensor sen = new Sensor();
				sen.setName(pointEntities.get(i).getName());
				sen.setUniqueId(pointEntities.get(i).getUniqueId());
				sen.setDes(pointEntities.get(i).getDescription());
				sen.setCreateTime(pointEntities.get(i).getCreateTime());
				sen.setUpdateTime(pointEntities.get(i).getUpdateTime());
				Resp<PointStatusEntity> po = sensorService.queryPointStatus(sen.getUniqueId());
				if (po.getData().getStatus()==1) {
					sen.setStatus("在线");
				}
				else{
					sen.setStatus("下线");
				}
				sensors.add(sen);
			}
			model.put("pageList", sensors);
			model.put("totalPages", Integer.valueOf(lists.getData().getTotalPages()));
			model.put("pageNow", Integer.valueOf(lists.getData().getPageNow()));
			model.put("totalRows", Integer.valueOf(lists.getData().getTotalRows()));
			Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
			model.put("type", resp.getData());
			model.put("point", sensor);
		}
		return "/backend/sensor/sen_list";
	}
	
	@RequestMapping(value="/add")
	public String add(ModelMap model){
		Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
		Resp<List<PointEntity>> pResp = sensorService.findByUersId(getPrincipal().getOrgId());
		model.put("parent", pResp.getData());
		model.put("type", resp.getData());
		return "/backend/sensor/sen_add";
	}
	
	@RequestMapping(value = "/save")
	public String save(String name,String uniqueId,Integer pointType,String parent,String description,String allCommand,String allDesc,RedirectAttributes redirectAttributes){
		try {
			PointEntity pointEntity = new PointEntity();
			pointEntity.setName(name);
			pointEntity.setUniqueId(uniqueId);
			pointEntity.setPointType(pointType);
			pointEntity.setParent(parent);
			pointEntity.setDescription(description);
			pointEntity.setOwner(getPrincipal().getOrgId()+"");
			pointEntity.setCommandResendTimes(3);
			String msg = sensorService.addPointEntity(pointEntity);
			Boolean sendBoolean = false;
			if (msg.equals("添加成功")) {
				String[] des = allDesc.split("\\|-\\|");
				String[] com = allCommand.split("\\|-\\|");
				List<CmdCandidateEntity> list = new ArrayList<CmdCandidateEntity>();
				for (int i = 0; i < com.length; i++) {
					if(com[i] !=null && com[i].length() > 0){
						CmdCandidateEntity cmdCandidateEntity = new CmdCandidateEntity();
						cmdCandidateEntity.setCommand(com[i]);
						cmdCandidateEntity.setDescription(des[i]);
						cmdCandidateEntity.setEntityId(uniqueId);
						sendBoolean = true;
						list.add(cmdCandidateEntity);
					}
				}
				if (sendBoolean) {
					Resp<Integer> resp = sensorService.addCmdCandidates(list);
					msg += "，并"+resp.getMsgDesc();
				}
				
			}
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, msg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器添加失败:"+e.getMessage());
		}
		return "redirect:/admin/sen";
	}

	@RequestMapping(value = "/updatesave")
	public String update(String name,String uniqueId,Integer pointType,String parent,String description,String allCommand,String allDesc,RedirectAttributes redirectAttributes){
		try {
			PointEntity pointEntity = new PointEntity();
			pointEntity.setName(name);
			pointEntity.setUniqueId(uniqueId);
			pointEntity.setPointType(pointType);
			pointEntity.setParent(parent);
			pointEntity.setDescription(description);
			pointEntity.setOwner(getPrincipal().getOrgId()+"");
			pointEntity.setCommandResendTimes(3);
			Boolean sendBoolean = false;
			String msg = sensorService.updatePoint(pointEntity.getUniqueId(), pointEntity);
			if (msg.equals("更新成功")) {
				String[] des = allDesc.split("\\|-\\|");
				String[] com = allCommand.split("\\|-\\|");
				List<CmdCandidateEntity> list = new ArrayList<CmdCandidateEntity>();
				for (int i = 0; i < com.length; i++) {
					if(com[i] !=null && com[i].length() > 0){
						CmdCandidateEntity cmdCandidateEntity = new CmdCandidateEntity();
						cmdCandidateEntity.setCommand(com[i]);
						cmdCandidateEntity.setDescription(des[i]);
						cmdCandidateEntity.setEntityId(uniqueId);
						sendBoolean = true;
						list.add(cmdCandidateEntity);
					}
				}
				if (sendBoolean) {
					Resp<Integer> resp = sensorService.addCmdCandidates(list);
					msg += "，并"+resp.getMsgDesc();
				}
			}
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, msg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/sen";
	}
	@RequestMapping(value = "/getInfo",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getInfo(String uniqueId,RedirectAttributes redirectAttributes){
		try {
			Resp<PointEntity> pointResp = sensorService.findPointById(uniqueId);
			if (pointResp.getMsgCode().equals(MessageCode.SUCCESS)) {
				Resp<List<PointTypeEntity>> type = sensorService.findAllPointType();
				List<PointTypeEntity> pointTypeEntities = type.getData();
				String typeName = pointTypeEntities.get(pointResp.getData().getPointType()-1).getPointTypeName();
				pointResp.getData().setUnit(typeName);
				List<PointEntity> list = new ArrayList<PointEntity>();
				list.add(pointResp.getData());
				return this.jsonPrint(1, pointResp.getMsgDesc(),list);
			}
			else {
				return this.jsonPrint(-1, pointResp.getMsgDesc(), null);
			}
		} catch (Exception e) {
			return this.jsonPrint(-1, "出错了……", null);
		}
	}
	@RequestMapping(value = "/queryCommands",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryCommands(String entityId,RedirectAttributes redirectAttributes){
		try {
			Resp<List<CommandEntity>> commands = sensorService.queryCommands(entityId);
			if (commands.getMsgCode().equals(MessageCode.SUCCESS)) {
				return this.jsonPrint(1, commands.getMsgDesc(), commands.getData());
			}
			else {
				return this.jsonPrint(-1, commands.getMsgDesc(), null);
			}
		} catch (Exception e) {
			return this.jsonPrint(1, "出错了……", null);
		}
	}
	@RequestMapping(value = "/sendCommandByid",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String sendCommandByid(Long id,String entityId,RedirectAttributes redirectAttributes){
		try {
			Resp<CommandEntity> commands = sensorService.sendCommand(id, entityId);
			return this.jsonPrint(1, commands.getMsgDesc(), null);
			
		} catch (Exception e) {
			return this.jsonPrint(-1, "出错了……", null);
		}
	}
	@RequestMapping(value = "/getCommands",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCommands(String uniqueId,RedirectAttributes redirectAttributes){
		try {
			Resp<List<CmdCandidateEntity>> resp = sensorService.queryCmdCandidates(uniqueId);
			if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
				return this.jsonPrint(1,"操作成功", resp.getData());
			}
			return this.jsonPrint(-1,resp.getMsgDesc() ,null);
		} catch (Exception e) {
			return this.jsonPrint(1, "出错了……", null);
		}
	}
	
	@RequestMapping(value = "/update")
	public String update(String uniqueId,ModelMap model){
		Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
		Resp<List<PointEntity>> pResp = sensorService.findByUersId(getPrincipal().getOrgId());
		model.put("parent", pResp.getData());
		model.put("type", resp.getData());
		Resp<PointEntity> pointEntity = sensorService.findPointById(uniqueId);
		model.put("point", pointEntity.getData());
		Resp<List<CmdCandidateEntity>> respCmResp = sensorService.queryCmdCandidates(uniqueId);
		model.put("cand", respCmResp.getData());
		if (!resp.getMsgCode().equals(MessageCode.SUCCESS)||!pResp.getMsgCode().equals(MessageCode.SUCCESS)||!pointEntity.getMsgCode().equals(MessageCode.SUCCESS)) {
			return "redirect:/admin/sen";
		}
		return "/backend/sensor/sen_edit";
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(String uniqueId,RedirectAttributes redirectAttributes){
		try {
			String msg = sensorService.deletePoint(uniqueId);
			return this.jsonPrint(1, msg, null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
	@RequestMapping(value = "/deleteCommand",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteCommand(String uniqueId,Long id,RedirectAttributes redirectAttributes){
		try {
			Resp<Integer> msg = sensorService.deleteCmdCandidate(id, uniqueId);
			if (msg.getMsgCode().equals(MessageCode.SUCCESS)) {
				return this.jsonPrint(1, msg.getMsgDesc(), null);
			}
			else {
				return this.jsonPrint(-1, msg.getMsgDesc(), null);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
	@RequestMapping(value = "/queryLastCommand",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryLastCommand(String uniqueId,RedirectAttributes redirectAttributes){
		try {
			Resp<CommandEntity> resp = sensorService.queryLastCommand(uniqueId);
			if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
				List<CommandEntity> list = new ArrayList<CommandEntity>();
				list.add(resp.getData());
				return this.jsonPrint(1, resp.getMsgDesc(), list);
			}
			else {
				return this.jsonPrint(-1, resp.getMsgDesc(), resp.getData());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
}
