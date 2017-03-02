package com.mcpfp.web.modules.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.framework.generic.model.BaseModel;
import com.mcpfp.web.modules.enums.SexEnum;

/**
 *
 * 管理员
 *
 */
@Data
public class GylAdmin implements BaseModel, Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long id;

	/** 用户名（登录名） */
	private String username;

	/** 密码 */
	private String password;

	/** 姓名 */
	private String name;

	/** 性别：1-男，2-女 */
	private Integer sex;

	/** 手机 */
	private String mobile;

	/** 固定电话 */
	private String tel;

	/** 分机号 */
	private String ext;

	/** qq */
	private String qq;

	/** 邮箱 */
	private String email;

	/** 删除标识(1：正常，-1：删除) */
	private Integer flag=1;

	/** 部门编号 */
	private Long orgId;

	/** 职位编号 */
	private Long dutyId;

	/** 角色编号 */
	private Long roleId;

	/** 入职日期 */
	private java.util.Date entryDate;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 修改时间 */
	private java.util.Date modifyTime;
	
	private GylOrg org;
	
	private GylDuty duty;
	
	private GylRole role;
	
	@Transient
	public String getSexValue(){
		return SexEnum.getValue(this.sex);
	}
	
	public String getNameTxt(){
		String txt = null;
		if(StringUtils.isNotBlank(name)){
			if(sex == 1){
				txt = name.substring(0,1) + "先生";
			}else{
				txt = name.substring(0, 1) + "女士";
			}
		}
		return txt;
	}

}
