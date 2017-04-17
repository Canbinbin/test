package com.cms.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_部门
 *
 */
@Data
public class GylOrg implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TREE_PATH_SEPARATOR = ",";

	/** 编号 */
	private Long id;

	/** 父id */
	private Long pid;

	/** 名称 */
	private String name;

	/** 级别 */
	private Integer level;

	/** 类型（1：公司；2：部门；） */
	private Integer type=2;

	/** 排序 */
	private Integer sort=999;

	/** 父结构树（父ids） */
	private String treePath;

	/** 负责人id */
	private Long chiefId = 0L;

	/** 负责人名称 */
	private String chiefName = "";

	/** 上级领导id */
	private Long higherId = 0L;

	/** 上级领导名称 */
	private String higherName = "";

	/** 部门标签 */
	private String tag = "OTHER";

	/** 删除标识(1：正常，-1：删除) */
	private Integer flag;

	/** 更新时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date modifyTime;
	
}
