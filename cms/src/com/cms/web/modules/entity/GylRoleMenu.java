package com.cms.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_角色_菜单
 *
 */
@Data
public class GylRoleMenu implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色id */
	private Long roleId;

	/** 菜单id */
	private Long menuId;

}
