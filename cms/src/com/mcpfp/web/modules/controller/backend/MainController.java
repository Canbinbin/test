package com.mcpfp.web.modules.controller.backend;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.common.shiro.AuthenticationUsernameToken;
import com.mcpfp.web.modules.enums.MenuTypeEnum;
import com.mcpfp.web.modules.service.GylMenuService;

@Controller(value = "mainController")
@RequestMapping(value="/admin/main/")
public class MainController extends BaseController {
	private Producer captchaProducer = null;
	
	@Resource(name = "gylMenuService")
	private GylMenuService gylMenuService;
	

	@Autowired
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}
	
	/**
	 * 主页
	 */
	//@RequiresPermissions("admin:main:index")
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,ModelMap model){
	//	SystemAuthorizingRealm.isValidateCodeLogin(request.getRemoteHost(), false, true);
		model.addAttribute("navList", gylMenuService.findMenuBy(this.getPrincipal().getUserId(), null, MenuTypeEnum.NAV));
		return "/backend/main/main";
	}
	
	/**
	 * 登录页面
	 */
	@RequestMapping(value = "login",method = RequestMethod.GET)
	public String loginView(HttpServletRequest request,ModelMap model){
		//model.put("isShowValidateCode",SystemAuthorizingRealm.isShowValidateCode(request.getRemoteHost()));
		return "/backend/main/login";
	}
	
	/**
	 * 登录页面
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request,String username,String password,String validateCode,ModelMap model,RedirectAttributes redirectAttributes){
		AuthenticationUsernameToken token = new AuthenticationUsernameToken(username, password, validateCode, true, request.getRemoteHost());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (Exception e) {
			this.addFlashMessage(redirectAttributes, BaseController.MessageTypeEnum.ERROR, e.getMessage());
			return "redirect:login";
		}
		return "redirect:/admin/main/";
	}
	/**
	 * 注册页面
	 */
	@RequestMapping(value = "register",method = RequestMethod.GET)
	public String registerView(){
		return "/backend/main/register";
	}
	
	/**
	 * 注销
	 */
	@RequestMapping(value = "logout",method = RequestMethod.GET)
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}
	
	
	/**
	 * 验证验证码
	 */
	@RequestMapping(value = "validateCode", method = RequestMethod.GET)
	public @ResponseBody
	Boolean validateCode(HttpServletRequest request,String  validateCode) {
		HttpSession session = request.getSession();
		String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		return code.equalsIgnoreCase(validateCode);
	}

	/**
	 * 生成验证码
	 */
	@RequestMapping("captcha-image")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			// write the data out
			ImageIO.write(bi, "jpg", out);
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
