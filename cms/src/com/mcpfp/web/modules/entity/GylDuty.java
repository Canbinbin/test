package com.mcpfp.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_职位
 *
 */
@Data
public class GylDuty implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 父编号 */
	private Long pid;

	/** 职位名称 */
	private String name;

	/** 排序 */
	private Integer sort;

	/** 结构树 */
	private String treePath;

	/** 删除标识(1：正常，-1：删除) */
	private Integer flag;

	/** 修改时间 */
	private java.util.Date createTime;

	/** 修改时间 */
	private java.util.Date modifyTime;

}
