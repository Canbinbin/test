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
public class GylMenu implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 父id */
	private Long pid;

	/** 名称 */
	private String name;

	/** 1-导航，2-菜单，3-操作 */
	private Integer type;

	/** 级别 */
	private Integer level;

	/** 排序 */
	private Integer sort;

	/** 父ids树 */
	private String treePath;

	/** 菜单链接 */
	private String url;

	/** 权限标识 */
	private String permission;

	/** 删除标识 1：正常，-1：删除 */
	private Integer flag;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date modifyTime;
	
	/** 父菜单*/
	private GylMenu parentMenu;
	
	/** 子菜单*/
	private List<GylMenu> childMenus;

}
