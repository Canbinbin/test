package com.cms.config;

import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;

public class UpperFastbitsIOSingleton {
	
	private static UpperFastbitsIO upperFastbitsIO = new UpperFastbitsIO.Builder().baseUrl("http://localhost:8089/fastbits/").create();
	
	public static UpperFastbitsIO upperFastbitsIO() {
		return upperFastbitsIO;
	}
}
