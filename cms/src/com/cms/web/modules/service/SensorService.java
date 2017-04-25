package com.cms.web.modules.service;

import java.util.List;
import java.util.Map;

import cn.edu.jnu.fastbits.entity.CmdCandidateEntity;
import cn.edu.jnu.fastbits.entity.CommandEntity;
import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.entity.PointTypeEntity;
import cn.edu.jnu.fastbits.entity.ValueEntity;
import cn.edu.jnu.fastbits.rest.Resp;

import com.framework.generic.service.BaseService;
import com.cms.web.modules.entity.Sensor;

public interface SensorService extends BaseService<Sensor, Long> {
	
	Resp<PointEntity> findPointById(String uniqueId);
	
	Resp<List<PointEntity>> findByUersId(Long userId);
	
	String addPointEntity(PointEntity pointEntity);

	String deletePoint(String uniqueId);
	
	String updatePoint(String uniqueId, PointEntity pointEntity);
	
	/**
     * 设备下线
     */
	String offline(String uniqueId);
	
	Resp<List<PointTypeEntity>> findAllPointType();
	
	Resp<PointTypeEntity> findPointTypeByName(String typeName);
	
	/**
     * 获取指定结点的当前value，要传入的参数为空时，返回当前所有结点的最新value
     */
    Resp<Map<String, ValueEntity>> getCurrentValues(List<String> uniqueIds);
    
    /**
     * 获取指定结点的指定时间范围内的value
     * @param beginTime 开始时间，格式 yyyyMMddHHmmss
     * @param endTime 结束时间，格式 yyyyMMddHHmmss
     */
    Resp<List<ValueEntity>> getValueWithTimeInterval(String uniqueId, String beginTime, String endTime);
    
    /**
     * 用户下发命令
     */
    String sendCommand(CommandEntity commandEntity);
    
    /**
     * 查看结点的上一次命令(执行状态等)
     */
    Resp<CommandEntity> queryLastCommand(String entityId);
    
    /**
     * 查看某个结点的所有命令(执行状态等)
     */
    Resp<List<CommandEntity>> queryCommands(String entityId);

	Resp<Integer> addCmdCandidates(List<CmdCandidateEntity> list);

	
}
