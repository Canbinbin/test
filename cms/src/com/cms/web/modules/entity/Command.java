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
public class Command implements BaseModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commandId; //不用传，服务器生成
    private String command;
    private Integer status;/*0-命令已到服务器，1-命令下发设备中，2-命令已被设备收到处理中，3-命令已被处理, 4-命令发送失败，5-命令已归档*/
    private Integer resendCounts;
    private String entityId;
    private String description;
    private Timestamp createTime;
    private Timestamp updateTime;
}
