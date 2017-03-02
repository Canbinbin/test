/**    
 * 文件名：ActivityNbUser.java    
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

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**    
 *     
 * 项目名称：zhg-web    
 * 类名称：ActivityNbUser    
 * 类描述： 挑战年兽活动的用户   
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月21日  
 * @version 1.0    
 *     
 */
@Data
public class ActivityNbUser implements BaseModel,Serializable{    
	 
	
	private static final long serialVersionUID = 3311560985303702625L;

	private Long id;
	
	//服务号openid（找化工官网）
	private String wxFwOpenId;
	
	//订阅号openId（找化工视点）
	private String wxDyOpenId;
	
	//会员id
	private Long mid;
	
	//手机号
	private String mobile;
	
	//领奖地址
	private String address;
	
	//创建时间
	private Date createTime;
	
	
}
