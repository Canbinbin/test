package com.cms.web.modules.controller.backend;

import cn.edu.jnu.fastbits.entity.CmdCandidateEntity;
import cn.edu.jnu.fastbits.entity.CommandEntity;
import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.entity.PointTypeEntity;
import cn.edu.jnu.fastbits.rest.MessageCode;
import cn.edu.jnu.fastbits.rest.Resp;

import java.util.ArrayList;
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

import com.cms.TestCms;
import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.service.SensorService;

@Controller
@RequestMapping(value="/admin/sen")
public class SensorController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(SensorController.class);
	
	@Resource(name = "sensorService")
	private SensorService sensorService;
	

	@RequestMapping(value="")
	public String list(ModelMap model){
		Long userId = getPrincipal().getUserId();
		Resp<List<PointEntity>> lists = sensorService.findByUersId(userId);
		if (!lists.getMsgCode().equals(MessageCode.SUCCESS)) {
			model.put("msg", lists.getMsgDesc());
		}
		model.put("pageList", lists.getData());
		return "/backend/sensor/sen_list";
	}
	
	@RequestMapping(value="/add")
	public String add(ModelMap model){
		Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
		Resp<List<PointEntity>> pResp = sensorService.findByUersId(getPrincipal().getUserId());
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
			pointEntity.setOwner(getPrincipal().getUserId()+"");
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
			pointEntity.setOwner(getPrincipal().getUserId()+"");
			pointEntity.setCommandResendTimes(3);
			String msg = sensorService.updatePoint(pointEntity.getUniqueId(), pointEntity);
			if (msg.equals("更新成功")) {
				String[] des = allDesc.split("(_)");
				String[] com = allCommand.split("(_)");
				for (int i = 0; i < com.length; i++) {
					if(com[i] !=null && com[i].length() > 0){
						CommandEntity commandEntity = new CommandEntity();
						commandEntity.setCommand(com[i]);
					}
				}
//				sensorService.sendCommand(commandEntity)
			}
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, msg);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/sen";
	}
	@RequestMapping(value = "/queryCommands",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
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
	@RequestMapping(value = "/sendCommand",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String sendCommand(CommandEntity commandEntity,RedirectAttributes redirectAttributes){
		try {
			String commands = sensorService.sendCommand(commandEntity);
			return this.jsonPrint(-1, commands, null);
			
		} catch (Exception e) {
			return this.jsonPrint(1, "出错了……", null);
		}
	}
	
	@RequestMapping(value = "/update")
	public String update(String uniqueId,ModelMap model){
		Resp<List<PointTypeEntity>> resp = sensorService.findAllPointType();
		Resp<List<PointEntity>> pResp = sensorService.findByUersId(getPrincipal().getUserId());
		model.put("parent", pResp.getData());
		model.put("type", resp.getData());
		Resp<PointEntity> pointEntity = sensorService.findPointById(uniqueId);
		model.put("point", pointEntity.getData());
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
}
