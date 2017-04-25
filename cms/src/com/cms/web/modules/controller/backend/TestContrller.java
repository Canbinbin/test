package com.cms.web.modules.controller.backend;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.jnu.fastbits.entity.PointEntity;
import cn.edu.jnu.fastbits.rest.MessageCode;
import cn.edu.jnu.fastbits.rest.Resp;
import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;

import com.cms.TestCms;
import com.cms.config.UpperFastbitsIOSingleton;

@Controller

public class TestContrller {

	@RequestMapping(value="/test")
	public String list(ModelMap model){
//		Long userId = getPrincipal().getUserId();
		UpperFastbitsIO upperFastbitsIO = UpperFastbitsIOSingleton.upperFastbitsIO();
		Resp<List<PointEntity>> resp = upperFastbitsIO.findPointByOwner("1");
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else {
			System.out.println(resp.getMsgDesc());
		}
		 return "/fore/index/index";
	}
}
