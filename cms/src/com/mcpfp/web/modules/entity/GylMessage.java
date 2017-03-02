package com.mcpfp.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_消息
 *
 */
@Data
public class GylMessage implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Integer id;

	/** 消息类型 */
	private Integer type;

	/** 类型标识 */
	private Long flag;

	/** 发送者id */
	private Integer fromId;

	/** 接收者id */
	private Integer toId;

	/** 内容 */
	private String content;

	/** 消息链接 */
	private String url;

	/** 消息状态(0：未读，1：已读，2：失效) */
	private Integer status;

	/** 添加时间 */
	private java.util.Date createTime;

	/** 发送时间 */
	private java.util.Date sendTime;

	/** 阅读时间 */
	private java.util.Date viewTime;

}
