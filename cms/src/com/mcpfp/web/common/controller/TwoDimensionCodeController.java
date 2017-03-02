package com.mcpfp.web.common.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mcpfp.web.common.util.CommonAttributes;
import com.mcpfp.web.common.util.TwoDimensionCodeUtils;


/**
 * 二维码生成控制器
 * @author zhoukai
 *
 */
@Controller
@RequestMapping("/two")
public class TwoDimensionCodeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(String content,HttpServletRequest request, HttpServletResponse response) {
		
		if(StringUtils.isNotEmpty(content)){
			content = CommonAttributes.fore_domain+content;
			content = content.replace('@', '&');
		}else{
			return null;
		}
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			TwoDimensionCodeUtils.encoderQRCode(content, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
		return null;
	}

}