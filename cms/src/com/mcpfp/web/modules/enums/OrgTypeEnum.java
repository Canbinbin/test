package com.mcpfp.web.modules.enums;

public enum OrgTypeEnum {
	NAV(1,"公司"),
	MENU(2,"部门");
	private Integer key;
	private String value;
	private OrgTypeEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public static String getValue(Integer key) {
        for (OrgTypeEnum c : OrgTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.getValue();
            }
        }
        return null;
    }
	
	public Integer getKey() {
		return this.key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
