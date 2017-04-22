package com.cms.web.modules.entity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**
 *
 * 管理员_菜单
 *
 */
@Data
public class Camera implements BaseModel, Serializable {

	public static String TREE_PATH_SEPARATOR = ",";

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 用户id */
	private Long userId;
	
	/** 用户id */
	private Long orgId;

	/** 名称 */
	private String camName;
	
//	/**  物理地址*/
//	private String camMac;
	
	/** 设备序列号 */
	private String deviceSerial;
	
	/** 设备验证码，设备机身上的六位大写字母 */
	private String validateCode;
	
	/** 访问令牌*/
	private String accessToken;
	
	/** 通道号*/
	private String channelNo;
	
	private String appkey;
	
	private String secret;

	/** 创建时间 */
	private java.util.Date camTime;
	
	private String tokenTime;
	
	private String liveAddress;
	
	private String hdAddress;

}
