package com.cms.web.modules.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_菜单
 *
 */
@Data
public class Sensor implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_EXPIRE = 90;
    private static final double DEFAULT_FILTER_VALUE = 0.1;
	/** 编号 */
	private Long id;

	/** 用户id */
	private Long owner;
	
	/** 节点id 一般用mac地址 */
	private String uniqueId;
	
	/** 过期时间 */
	private Integer expire = DEFAULT_EXPIRE;
	
	/** 过滤类型 */
	private Integer filterType;
	
	/** 过滤值 */
	private Double filterValue = DEFAULT_FILTER_VALUE;
	
	/** 节点类型 */
	private String pointType;
	
	/** 节点描述 */
	private String description;
	
	/** 命令重发次数 */
	private Integer commandResendTimes;
	
	/** 节点创建时间 */
	private String createTime;
	
	/** 节点更新时间 */
	private String updateTime;

	/** 节点名字 */
	private String name;
	
	/** 父节点 */
	private String parent;
	
	private Long innerId;
    private String batchId;
    private Double deltaAlarm;
    private Boolean deltaAlarmOn = false;
    private Integer deltaSeconds;
    
    private Double highAlarm = 0.0;
    private Boolean highAlarmOn = false;
    private Boolean idleAlarmOn = false;
    private Boolean idleAlarmSent = false;
    private Integer idleSeconds = 0;
    private Boolean inferLocation = false;
    private Double lowAlarm = 0.0;
    private Boolean lowAlarmOn = false;
    private Integer precision;
    private String unit;
    private Integer entityType;
    
}
