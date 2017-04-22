package com.cms.web.common.util;

import cn.edu.jnu.fastbits.entity.SysInfoEntity;
import cn.edu.jnu.fastbits.rest.Resp;
import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UpperFastbitsIO upperFastbitsIO = new UpperFastbitsIO.Builder()
			.baseUrl("http://192.168.191.2:8080/fastbits/")
			.create();
		
		Resp<SysInfoEntity> resp = upperFastbitsIO.sayHello();
		
		System.out.println(resp);
	}

}
