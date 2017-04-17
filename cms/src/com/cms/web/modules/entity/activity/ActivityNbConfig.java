/**    
 * 文件名：ActivityNbConfig.java    
 *    
 * 版本信息：    
 * 日期：2016年1月22日    
 * Copyright 广州找塑料网络科技有限公司 Corporation 2016     
 * 版权所有    
 *    
 */
package com.cms.web.modules.entity.activity;

import java.io.Serializable;

import lombok.Data;

import com.framework.generic.model.BaseModel;

/**    
 *     
 * 项目名称：zhg-web    
 * 类名称：ActivityNbConfig    
 * 类描述： 挑战年兽活动配置表   
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月22日  
 * @version 1.0    
 *     
 */
@Data
public class ActivityNbConfig  implements BaseModel,Serializable{

	private static final long serialVersionUID = 3885663834516174280L;
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private String value;

}
