package com.cms.config;

import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;

public class UpperFastbitsIOSingleton {
	
	private static UpperFastbitsIO upperFastbitsIO = new UpperFastbitsIO.Builder()
	.baseUrl("http://localhost:8089/fastbits/").authInfo("admin","TAz40ZLTkzNTerfP").create();
	
	public static UpperFastbitsIO upperFastbitsIO() {
		return upperFastbitsIO;
	}
}
