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

	/** 编号 */
	private Long id;

	/** 用户id */
	private String status;
	
	/** 节点id 一般用mac地址 */
	private String uniqueId;
	
	/** 节点类型 */
	private String pointType;
	
	/** 节点创建时间 */
	private String createTimeStart;
	
	/** 节点更新时间 */
	private String updateTimeStart;
	
	/** 节点创建时间 */
	private String createTimeEnd;
	
	/** 节点更新时间 */
	private String updateTimeEnd;

	/** 节点名字 */
	private String name;
	
	/** 父节点 */
	private String parent;
	
}
