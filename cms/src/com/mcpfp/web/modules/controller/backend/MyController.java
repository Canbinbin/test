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
		String beginTime = nowTime + " 00:00:00";
		String endTime = nowTime + " 23:59:59";
		Record purchase = new Record();
		purchase.put("day", indexData(beginTime,endTime));
		
//		Record 	scheduleRecord = getScheduleNowDate();
//		if(scheduleRecord != null){
//			////当周日期
//			purchase.put("week", indexData(scheduleRecord.getString("week_start_time") + " 00:00:00",scheduleRecord.getString("week_end_time")+ " 23:59:59"));
//			////当月日期
//			purchase.put("month",indexData(scheduleRecord.getString("start_time") + " 00:00:00",scheduleRecord.getString("end_time")+ " 23:59:59"));
//		}
		
		model.put("purchase", purchase);
		model.put("now_time", nowTime);
		
		return "/backend/my/my_index";
	}
	
	
//	private Record getScheduleNowDate() {
//	
//		String now_time = DateTimeUtils.getFormatDate(new Date(), DateTimeUtils.FULL_DATE_FORMAT);
//		Record result = new Record();
//		GylSchedule schedule = gylAdminService.getDB().one("select id,"+
//		"demo,"+
//		"start_time as startTime,"+
//		"end_time as endTime,"+
//		"first_week_start_time as firstWeekStartTime,"+
//		"first_week_end_time as firstWeekEndTime,"+
//		"second_week_start_time as secondWeekStartTime ,"+
//		"second_week_end_time secondWeekEndTime,"+
//		"third_week_start_time as thirdWeekStartTime,"+
//		"third_week_end_time as thirdWeekEndTime ,"+
//		"fourth_week_start_time as fourthWeekStartTime,"+
//		"fourth_week_end_time as fourthWeekEndTime,"+
//		"first_week_day as firstWeekDay,"+
//		"second_week_day as secondWeekDay,"+
//		"three_week_day as threeWeekDay,"+
//		"four_week_day as fourWeekDay,"+
//		"flag_status as flagStatus,"+
//		"create_time as createTime,"+
//		"creator_id as creatorId from mcpfp_gyl_schedule where start_time<=? and end_time>=? limit 1",GylSchedule.class,now_time,now_time);
//		if (schedule != null){
//			result.put("start_time", DateTimeUtils.getFormatDate(schedule.getStartTime(), DateTimeUtils.PART_DATE_FORMAT));
//			result.put("end_time", DateTimeUtils.getFormatDate(schedule.getEndTime(), DateTimeUtils.PART_DATE_FORMAT));
//			
//			Date nowDate = new Date();
//			Date first_week_start_time = schedule.getFirstWeekStartTime();
//			Date first_week_end_time = schedule.getFirstWeekEndTime();
//			
//			Date second_week_start_time = schedule.getSecondWeekStartTime();
//			Date second_week_end_time = schedule.getSecondWeekEndTime();
//			
//			Date third_week_start_time = schedule.getThirdWeekStartTime();
//			Date third_week_end_time = schedule.getThirdWeekEndTime();
//			
//			
//			Date fourth_week_start_time = schedule.getFourthWeekStartTime();
//			Date fourth_week_end_time = schedule.getFourthWeekEndTime();
//			
//			if (nowDate.getTime()>=first_week_start_time.getTime() && nowDate.getTime()<=first_week_end_time.getTime()){
//				result.put("week_start_time", DateTimeUtils.getFormatDate(first_week_start_time, DateTimeUtils.PART_DATE_FORMAT));
//				result.put("week_end_time", DateTimeUtils.getFormatDate(first_week_end_time, DateTimeUtils.PART_DATE_FORMAT));
//			}else if (nowDate.getTime()>=second_week_start_time.getTime() && nowDate.getTime()<=second_week_end_time.getTime()){
//				result.put("week_start_time", DateTimeUtils.getFormatDate(second_week_start_time, DateTimeUtils.PART_DATE_FORMAT));
//				result.put("week_end_time", DateTimeUtils.getFormatDate(second_week_end_time, DateTimeUtils.PART_DATE_FORMAT));
//			}else if (nowDate.getTime()>=third_week_start_time.getTime() && nowDate.getTime()<=third_week_end_time.getTime()){
//				result.put("week_start_time", DateTimeUtils.getFormatDate(third_week_start_time, DateTimeUtils.PART_DATE_FORMAT));
//				result.put("week_end_time", DateTimeUtils.getFormatDate(third_week_end_time, DateTimeUtils.PART_DATE_FORMAT));
//			}else if (nowDate.getTime()>=fourth_week_start_time.getTime() && nowDate.getTime()<=fourth_week_end_time.getTime()){
//				result.put("week_start_time", DateTimeUtils.getFormatDate(fourth_week_start_time, DateTimeUtils.PART_DATE_FORMAT));
//				result.put("week_end_time", DateTimeUtils.getFormatDate(fourth_week_end_time, DateTimeUtils.PART_DATE_FORMAT));
//			}
//		}
//		return result;
//	}
	
	private Object indexData(String beginTime, String endTime) {
		Record res = new Record();
		Long userId = this.getPrincipal().getUserId();
		Record purchaseInfo = gylAdminService.getDB().one("select IFNULL(SUM(fact_number),0) as total_number,IFNULL(SUM(fact_amount),0) as total_amount,count(case when flag_tip=0 then id end) as total_order from mcpfp_purchase where status=50 and coop_admin_id=0 and check_time>='"+beginTime+"' and check_time<'"+endTime+"' and follow_id=?",Record.class, userId);
		Record coopInfo = gylAdminService.getDB().one("select IFNULL(SUM(fact_number),0) as total_number,IFNULL(SUM(fact_amount),0) as total_amount,count(case when flag_tip=0 then id end) as total_order from mcpfp_purchase where status=50 and coop_admin_id>0 and check_time>='"+beginTime+"' and check_time<'"+endTime+"' and (follow_id=? or coop_admin_id=?)",Record.class,userId,userId);
		BigDecimal p_total_number = purchaseInfo.getBigDecimal("total_number");
		BigDecimal c_total_number = coopInfo.getBigDecimal("total_number").divide(new BigDecimal(2));
		res.put("total_number", p_total_number.add(c_total_number));
		
		BigDecimal p_total_order = purchaseInfo.getBigDecimal("total_order");
		BigDecimal c_total_order = coopInfo.getBigDecimal("total_order").divide(new BigDecimal(2));
		res.put("total_order",p_total_order.add(c_total_order));
		
		BigDecimal p_total_amount = purchaseInfo.getBigDecimal("total_amount");
		BigDecimal c_total_amount = coopInfo.getBigDecimal("total_amount").divide(new BigDecimal(2));
		res.put("total_amount",(p_total_amount.add(c_total_amount)).divide(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP));
		res.put("purchase_num", gylAdminService.getDB().one("select count(*) as purchase_num  from mcpfp_purchase where create_time>='"+beginTime+"' and create_time<'"+endTime+"' and status!=10  and follow_id=?",userId).get("purchase_num"));
    	return res;
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
