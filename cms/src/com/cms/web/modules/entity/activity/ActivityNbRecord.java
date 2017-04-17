/**    
 * 文件名：ActivityNbRecord.java    
 *    
 * 版本信息：    
 * 日期：2016年1月21日    
 * Copyright 广州找塑料网络科技有限公司 Corporation 2016     
 * 版权所有    
 *    
 */
package com.cms.web.modules.entity.activity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**    
 *     
 * 项目名称：zhg-web    
 * 类名称：ActivityNbRecord    
 * 类描述：    
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月21日  
 * @version 1.0    
 *     
 */
@Data
public class ActivityNbRecord implements BaseModel,Serializable{

	
	private static final long serialVersionUID = -5195252101812765179L;
	
	//主键
	private Long id;
	
	//mcpfp_activity_nb_user的id
	private Long uid;
	
	//奖品id
	private Long prizeId;
	
	//奖品价值,只对红包有效
	private Double prizeValue;
	
	//会员id
	private Long mid;
	
	//手机号
	private String mobile;
	
	//状态  0：未发奖  1：已发奖
	private Integer status;
	
	//中奖时间
	private Date createTime;

}
