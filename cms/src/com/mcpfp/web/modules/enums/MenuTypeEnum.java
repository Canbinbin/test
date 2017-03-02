package com.mcpfp.web.modules.enums;

public enum MenuTypeEnum {
	NAV(1,"导航"),
	MENU(2,"菜单"),
	OPT(3,"操作");
	private Integer key;
	private String value;
	private MenuTypeEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public static String getValue(Integer key) {
        for (MenuTypeEnum c : MenuTypeEnum.values()) {
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
