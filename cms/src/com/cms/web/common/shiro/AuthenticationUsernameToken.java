package com.cms.web.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 *     
 * 项目名称：inCms    
 * 类名称：AuthenticationUsernameToken    
 * 类描述： 登录令牌   
 * 创建人：liujunqing    
 * 创建时间：2015年10月20日  
 * @version 1.0    
 *
 */
public class AuthenticationUsernameToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	
	private String captcha;

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param captchaId
	 *            验证码ID
	 * @param captcha
	 *            验证码
	 * @param rememberMe
	 *            记住我
	 * @param host
	 *            IP
	 */
	public AuthenticationUsernameToken(String username, String password,  String captcha, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}
