package com.cms.web.modules.enums;
/**
 * 项目名称：zhg-web    
 * 类名称：RoleTagMenu    
 * 类描述： 角色标签
 * 创建人：liujunqing    
 * 创建时间：2015年12月10日  
 * @version 1.0    
 *
 */
public enum RoleTagEnum {
	TRANZ("TRANZ","交易员"),
	CUSTOMER("CUSTOMER","客服"),
	OTHER("OTHER","其它");
	private String key;
	private String value;
	private RoleTagEnum(String key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public static String getValue(String key) {
        for (RoleTagEnum c : RoleTagEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.getValue();
            }
        }
        return null;
    }
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
