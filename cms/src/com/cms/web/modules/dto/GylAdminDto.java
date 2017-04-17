package com.cms.web.modules.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GylAdminDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String orgName;
	private Long orgId;
	private String username;
	private String name;
}
