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
public class Info implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	private String name;
	
	private String num;
	
	

}
