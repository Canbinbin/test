package com.mcpfp.web.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mcpfp.web.common.util.UploadUtils;
;

@Controller
@RequestMapping("/file")
public class UploadFileController { 
    
    /**
     * 凭证异步上传
     *@param request
     *@return
     */
    @RequestMapping(value = "/ajaxUpload",produces={"text/html;charset=UTF-8"})  
    @ResponseBody
    public String upload(HttpServletRequest request,HttpServletResponse response){  
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	MultipartFile multipartFile = multipartRequest.getFile("fileToUpload"); 
    	long size = multipartFile.getSize();
    	if((size/(1024*1024)) > 10){
    		String json = "{fileName:\""+""+"\",md5Code:\""+""+"\",error:\""+"1"+"\"}";
            return json;
    	}
    	try {
    		Map<String,String> resultmap =  UploadUtils.saveMartipartFileAndHd5File(request, multipartFile,"receipt");
    		String json = "{fileName:\""+resultmap.get("fileName")+"\",md5Code:\""+resultmap.get("md5Code")+"\",error:\""+"0"+"\"}";
            return json;
		} catch (Exception e) {
			String json = "{fileName:\""+""+"\",md5Code:\""+""+"\",error:\""+"2"+"\",,errorMsg:\""+e.getMessage()+"\"}";
			return json;
		}
    }
}
