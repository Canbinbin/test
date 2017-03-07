package com.mcpfp.web.modules.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_菜单
 *
 */
@Data
public class Actuator implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 用户id */
	private Long userId;
	
	/** 用户id */
	private Long orgId;

	/** 名称 */
	private String actName;
	
	/** 传感器通道 */
	private String actMac;
	
	/** 传感器类型 */
	private String actType;
	
	/** 传感器曲线 */
	private String actOrder;
	
	/** 传感器曲线 */
	private String actOpen;

	/** 创建时间 */
	private java.util.Date actTime;
	
	

}
