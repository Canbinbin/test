package com.cms.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 
 *
 */
@Data
public class GylTel implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;

	/** 管理员id */
	private Long adminId;

	/** 分机号 */
	private String ext;

	/**  */
	private Integer telNum;

	/** 呼叫日期 */
	private java.util.Date callDate;

	/** 创建时间 */
	private java.util.Date createTime;

}
