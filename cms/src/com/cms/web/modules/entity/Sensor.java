package com.cms.web.modules.entity;

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
public class Sensor implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 用户id */
	private Long userId;
	
	/** 车间id */
	private Long orgId;

	/** 名称 */
	private String senName;
	
	/** 传感器通道 */
	private String senChan;
	
	/** 传感器类型 */
	private String senType;
	
	/** 传感器曲线 */
	private String curve;
	
	/** 传感器曲线 */
	private String senOpen;

	/** 创建时间 */
	private java.util.Date senTime;
	
	

}
