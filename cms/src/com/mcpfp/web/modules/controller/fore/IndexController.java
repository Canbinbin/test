package com.mcpfp.web.modules.controller.fore;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.common.controller.BaseController.MessageTypeEnum;
import com.mcpfp.web.modules.controller.backend.GylAdminController;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.service.GylAdminService;
  
/**  
 * 
* @Title: IndexController.java 
* @Package com.mcpfp.web.modules.controller.fore 
* @Description: 找化工主页controller
* @author name:冼智聪(Feco Xian) phone:13826075988 QQ:260546078
* @date 2015年12月23日 上午11:20:57 
* @version V1.0
 */
@Controller(value = "indexController")
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	
	@RequestMapping(value = "index",method = RequestMethod.GET)
	public String index(HttpServletRequest request){
		return "/fore/index/index";
	}
	@RequestMapping(value = "exam",method = RequestMethod.GET)
	public String indexSecond(HttpServletRequest request){
		return "/fore/index/index_second";
	}
	@RequestMapping(value = "doc",method = RequestMethod.GET)
	public String indexThird(HttpServletRequest request){
		return "/fore/index/index_third";
	}
	@RequestMapping(value = "support",method = RequestMethod.GET)
	public String indexFour(HttpServletRequest request){
		return "/fore/index/index_four";
	}
	
	@RequestMapping(value = "register",method = RequestMethod.GET)
	public String registerView(HttpServletRequest request,ModelMap model){
		return "/backend/main/register";
	}
	
	@RequestMapping(value = "register",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
	public String register(GylAdmin admin,RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isNotBlank(admin.getPassword())){
				admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
			}
			admin.setModifyTime(new Date());
			admin.setCreateTime(new Date());
			admin.setRoleId(new Long(18));
			gylAdminService.save(admin);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "注册成功，请登录");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "注册失败，请重新注册"+e.getMessage());
		}
		 return "redirect:/admin/main/login";
	}
	
	@RequestMapping(value="is_exist_username",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Boolean checkLoginName(String username){
		GylAdmin admin = gylAdminService.findByUsername(username.toLowerCase().trim());
		return admin== null ? true : false;
	}
}
