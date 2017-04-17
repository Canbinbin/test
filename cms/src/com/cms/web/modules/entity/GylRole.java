package com.cms.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;
import com.cms.web.modules.enums.RoleTagEnum;

/**
 *
 * 管理员_角色
 *
 */
@Data
public class GylRole implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 名称 */
	private String name;

	/** 角色标签 */
	private String tag;

	/** 删除标识(1：正常，-1：删除) */
	private Integer flag=1;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date modifyTime;
	
	
	public String getTagValue(){
		return RoleTagEnum.getValue(this.tag);
	}

}
