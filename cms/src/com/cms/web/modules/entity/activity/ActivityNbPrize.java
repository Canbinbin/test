/**    
 * 文件名：ActivityNbPrize.java    
 *    
 * 版本信息：    
 * 日期：2016年1月21日    
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
 * 类名称：ActivityNbPrize    
 * 类描述：    
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月21日  
 * @version 1.0    
 *     
 */
@Data 
public class ActivityNbPrize implements BaseModel,Serializable{


	private static final long serialVersionUID = -6029885226992678466L;

	//主键
	private Long id;
	
	//奖品名称
	private String prizeName;
	
	//奖品等级，对应效果分类，2、小鞭炮  （  红包奖）    3、小烟花   （外接摄像头、移动电源） 4、 礼花     （300元油卡） 5、原子弹       （拍立得）
	private Integer level;
	
	//奖品数量，这个字段只对非红包有效
	private Integer prizeCount;
	
	//红包的最小值 单位：分
	private Integer minValue;
	
	//红包的最大值 单位：分
	private Integer maxValue;
	
	//中奖概率，单位千分之X
	private Integer rate;
	
	//用户类型 10：没有历史成交记录 20:有历史成交记录但活动期间没成交记录的 30：活动期间成交数1~2笔 40：活动期间成交数3~4笔 50：活动期间成交数5~6笔 60：活动期间成交数7~n笔
	private Integer userType;

}
