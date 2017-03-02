package com.mcpfp.web.common.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcpfp.web.common.shiro.Principal;
import com.mcpfp.web.common.util.DateEditor;


/**
 *     
 * 项目名称：inCms    
 * 类名称：BaseController    
 * 类描述：控制层基类    
 * 创建人：liujunqing    
 * 创建时间：2015年10月15日  
 * @version 1.0    
 *
 */
@Slf4j
public class BaseController {
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 瞬时消息名称
	 */
	public static final String FLASH_MESSAGE_REDIRECT_ATTRIBUTES = "FLASH_MESSAGE";
	
	
	public enum MessageTypeEnum{
		WARN(0),		//警告
		SUCCESS(1),		//成功
		ERROR(2);		//失败
		private Integer key;
		private MessageTypeEnum(Integer key){
			this.key = key;
		}
		public Integer getKey() {
			return this.key;
		}
		public void setKey(Integer key) {
			this.key = key;
		}
		
		
	}
	
	/**
	 * 获得登录身份信息
	 *@return
	 */
	public Principal getPrincipal(){
		return (Principal)SecurityUtils.getSubject().getPrincipal();
	}
	/**
	 * 加入瞬时消息
	 *@param redirectAttributes 重定向对象
	 *@param type				类型@see MessageTypeEnum
	 *@param content			瞬时内容
	 */
	public void addFlashMessage(RedirectAttributes redirectAttributes,MessageTypeEnum type,String content){
		Map<String, String> mesMap = new HashMap<String, String>();
		mesMap.put("type", type.getKey().toString());
		mesMap.put("content", content);
		redirectAttributes.addFlashAttribute(FLASH_MESSAGE_REDIRECT_ATTRIBUTES,gson.toJson(mesMap));
	}
	
	/**
	 * 初始化数据绑定
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new DateEditor(true));
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.unescapeHtml(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString().trim() : "";
			}
		});
	}
	
	protected String jsonPrint(Integer status,String msg,Object data){
		 JSONObject jo = new JSONObject();
		 jo.put("status",status);
		 jo.put("msg",msg);
		 jo.put("data", data);
		 return jo.toString();
	}
	
	protected Boolean isPost(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getMethod().equals("POST");
	}
	
	
	protected Boolean isGet(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getMethod().equals("GET");
	}
	
}
