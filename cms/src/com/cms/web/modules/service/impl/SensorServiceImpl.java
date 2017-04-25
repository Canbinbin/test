package com.cms.web.modules.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import com.cms.web.modules.dao.SensorDao;
import com.cms.web.modules.entity.Sensor;
import com.cms.web.modules.service.SensorService;
import com.framework.generic.service.impl.BaseServiceImpl;

import cn.edu.jnu.fastbits.entity.CmdCandidateEntity;
import cn.edu.jnu.fastbits.entity.CommandEntity;
import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.entity.PointTypeEntity;
import cn.edu.jnu.fastbits.entity.ValueEntity;
import cn.edu.jnu.fastbits.rest.MessageCode;
import cn.edu.jnu.fastbits.rest.Resp;
import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;

import com.cms.config.UpperFastbitsIOSingleton;
@Service("sensorService")
public class SensorServiceImpl extends BaseServiceImpl<Sensor, Long> implements SensorService {
	
	@Resource(name="sensorDao")
	private SensorDao sensorDao;

	UpperFastbitsIO upperFastbitsIO = UpperFastbitsIOSingleton.upperFastbitsIO();
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(sensorDao);
	}

	@Override
	public Resp<List<PointEntity>> findByUersId(Long userId) {

		Resp<List<PointEntity>> resp = upperFastbitsIO.findPointByOwner(userId+"");
		System.out.println("findPointByOwner:"+userId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else {
			System.out.println(resp.getMsgDesc());
		}
		return resp;
		
	}
	public String addPointEntity(PointEntity pointEntity){
		String res;
		System.out.println("addPointEntity:"+pointEntity.toString());
		Resp<PointEntity> resp = upperFastbitsIO.addPoint(pointEntity);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			res = "添加成功";
		}
		else{
			res = "添加失败："+resp.getMsgDesc();
		}
		return res;
	}

	@Override
	public String deletePoint(String uniqueId) {
		String res;
		System.out.println("deletePoint:"+uniqueId);
		Resp<Integer> resp = upperFastbitsIO.deletePoint(uniqueId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			res = "删除成功";
		}
		else{
			res = "删除失败："+resp.getMsgDesc();
		}
		return res;
	}

	@Override
	public String updatePoint(String uniqueId, PointEntity pointEntity) {
		String res;
		System.out.println("updatePoint:"+pointEntity.toString());
		Resp<PointEntity> resp = upperFastbitsIO.updatePoint(uniqueId, pointEntity);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			res = "更新成功";
		}
		else{
			res = "更新失败："+resp.getMsgDesc();
		}
		return res;
	}

	@Override
	public String offline(String uniqueId) {
		String res;
		System.out.println("offline:"+uniqueId);
		Resp<Integer> resp = upperFastbitsIO.offline(uniqueId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			res = "操作成功";
		}
		else{
			res = "操作失败："+resp.getMsgDesc();
		}
		return res;
	}

	@Override
	public Resp<List<PointTypeEntity>> findAllPointType() {
		System.out.println("findAllPointType:");
		Resp<List<PointTypeEntity>> resp = upperFastbitsIO.findAllPointType();
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public Resp<PointTypeEntity> findPointTypeByName(String typeName) {
		System.out.println("findPointTypeByName:"+typeName);
		Resp<PointTypeEntity> resp = upperFastbitsIO.findPointTypeByName(typeName);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public Resp<Map<String, ValueEntity>> getCurrentValues(List<String> uniqueIds) {
		System.out.println("getCurrentValues:"+uniqueIds);
		Resp<Map<String, ValueEntity>> resp = upperFastbitsIO.getCurrentValues(uniqueIds);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public Resp<List<ValueEntity>> getValueWithTimeInterval(String uniqueId,
			String beginTime, String endTime) {
		System.out.println("getValueWithTimeInterval:"+uniqueId);
		Resp<List<ValueEntity>> resp = upperFastbitsIO.getValueWithTimeInterval(uniqueId, beginTime, endTime);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public String sendCommand(CommandEntity commandEntity) {
		String res;
		System.out.println("sendCommand:"+commandEntity.toString());
		Resp<CommandEntity> resp = upperFastbitsIO.sendCommand(commandEntity);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			res = "操作成功";
		}
		else{
			res = "操作失败："+resp.getMsgDesc();
		}
		return res;
	}

	@Override
	public Resp<CommandEntity> queryLastCommand(String entityId) {
		System.out.println("queryLastCommand:"+entityId);
		Resp<CommandEntity> resp = upperFastbitsIO.queryLastCommand(entityId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public Resp<List<CommandEntity>> queryCommands(String entityId) {
		System.out.println("queryCommands:"+entityId);
		Resp<List<CommandEntity>> resp = upperFastbitsIO.queryCommands(entityId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}

	@Override
	public Resp<PointEntity> findPointById(String uniqueId) {
		System.out.println("findPointById:"+uniqueId);
		Resp<PointEntity> resp = upperFastbitsIO.findPointById(uniqueId);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}
	@Override
	public Resp<Integer> addCmdCandidates(List<CmdCandidateEntity> list) {
		System.out.println("addCmdCandidates:"+list);
		Resp<Integer> resp = upperFastbitsIO.addCmdCandidates(list);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else{
			System.out.println(resp.getMsgDesc());
		}
		return resp;
	}
}
