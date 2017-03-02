/**    
 * 文件名：ActivityNbNum.java    
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
 * 类名称：ActivityNbNum    
 * 类描述：    
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月21日  
 * @version 1.0    
 *     
 */
@Data
public class ActivityNbNum implements BaseModel,Serializable{

	private static final long serialVersionUID = 7643945036249630825L;
	
	//主键
	private Long id;
	//mcpfp_activity_nb_user的id
	private Long uid;
	
	//消耗赠送的玩游戏次数
	private Integer freeNum;
	
	//消耗交易获得的玩游戏次数
	private Integer earnNum;
	
	//最后玩游戏的时间
	private Date modifyTime;

}
