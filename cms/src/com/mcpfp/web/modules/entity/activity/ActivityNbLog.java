/**    
 * 文件名：ActivityNbLog.java    
 *    
 * 版本信息：    
 * 日期：2016年1月21日    
 * Copyright 广州找塑料网络科技有限公司 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mcpfp.web.modules.entity.activity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**    
 *     
 * 项目名称：zhg-web    
 * 类名称：ActivityNbLog    
 * 类描述：    
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月21日  
 * @version 1.0    
 *     
 */
@Data
public class ActivityNbLog implements BaseModel,Serializable{

	private static final long serialVersionUID = -2096018079677942581L;
	
	//
	private Long id;
	
	//mcpfp_activity_nb_user表的id
	private Long uid;
	
	//
	private String wxFwOpenId;
	
	//mcpfp_member表的id
	private Long mid;
	
	//手机号
	private String mobile;
	
	//姓名
	private String linkman;
	
	//公司名称
	private String companyName;
	
	//mcpfp_activity_nb_prize的id
	private Long prizeId;
	
	//奖品名称
	private String prizeName;
	
	//奖品价值，只对红包有效
	private Double prizeValue;
	
	//红包发送状态
	private Integer sendStatus;
	
	//红包发送返回消息
	private String sendMsg;
	
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	//是否是会员
	private String isMember;
	
	//奖品是否发放成功
	private String isSend;
	
	private String createTimeText;
	

}
