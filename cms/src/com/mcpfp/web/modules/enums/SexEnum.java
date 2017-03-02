package com.mcpfp.web.modules.enums;

public enum SexEnum {
	F(1,"男"),
	M(2,"女");
	private Integer key;
	private String value;
	private SexEnum(Integer key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public static String getValue(Integer key) {
        for (SexEnum c : SexEnum.values()) {
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
