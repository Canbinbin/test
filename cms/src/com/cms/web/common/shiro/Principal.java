package com.cms.web.common.shiro;

import java.io.Serializable;

import com.framework.generic.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 身份信息
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Principal implements BaseModel, Serializable {
	private static final long serialVersionUID = 5798882004228239559L;
	public static final String  SESSION_PRINCIPAL_ATTR = "principal";

	/** ID */
	private Long userId;

	/** 用户名 */
	private String username;
	/** 角色*/
	private Long roleId;
	/** 车间ID*/
	private Long orgId;

}