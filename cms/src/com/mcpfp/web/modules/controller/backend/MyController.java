package com.mcpfp.web.modules.controller.backend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.generic.activerecord.Record;
import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.common.util.DateTimeUtils;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.service.GylAdminService;


@Controller
@RequestMapping(value="/admin/my")
public class MyController extends BaseController {

	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	
	/**
	 * 主页
	 */
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,ModelMap model){
		//当日日期
		String nowTime = DateTimeUtils.getFormatDate(new Date(), DateTimeUtils.PART_DATE_FORMAT);
		
		model.put("now_time", nowTime);
		
		return "/backend/my/my_index";
		
	}
	
	
	
	@RequestMapping(value="edit_profile",method = {RequestMethod.GET,RequestMethod.POST})
	public String myProfile(HttpServletRequest request,GylAdmin adminForm,ModelMap model,RedirectAttributes redirectAttributes){
		if(request.getMethod().equals("GET")){
			GylAdmin admin = gylAdminService.find(this.getPrincipal().getUserId());
			model.addAttribute("admin", admin);
			return "/backend/my/my_profile";
		}else {
			gylAdminService.updateProfile(adminForm);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:edit_profile";
		}
		
	}
	
	
}
