package com.mcpfp.web.modules.controller.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mcpfp.web.common.controller.BaseController;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.entity.GylDuty;
import com.mcpfp.web.modules.service.GylAdminService;
import com.mcpfp.web.modules.service.GylDutyService;
@Controller
@RequestMapping(value="/admin/duty")
public class GylDutyController extends BaseController {
	
	@Resource(name = "gylDutyService")
	private GylDutyService gylDutyService;
	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	
	

	/**
	 * 列表
	 */
	@RequestMapping(value = "")
	public String index(ModelMap model){
		List<GylDuty> treeList = treeList(gylDutyService.findAll(),0L);
		model.addAttribute("dutyList",treeList );
		return "/backend/duty/duty_list";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	public String add(GylDuty duty,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("pduty", gylDutyService.find(duty.getPid()));
			return "/backend/duty/duty_add";
		} else {
			List<Long> treePathList = treePathList(duty.getPid());
			duty.setFlag(1);
			duty.setCreateTime(new Date());
			duty.setModifyTime(new Date());
			duty.setTreePath(getTreePath(treePathList));
			gylDutyService.save(duty);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/duty";
		}
	}
	
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(GylDuty duty,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			GylDuty findduty = gylDutyService.find(duty.getId());
			model.addAttribute("duty",findduty );
			model.addAttribute("pduty", gylDutyService.find(findduty.getPid()));
			return "/backend/duty/duty_edit";
		} else {
			List<Long> treePathList = treePathList(duty.getPid());
			duty.setFlag(1);
			duty.setCreateTime(new Date());
			duty.setModifyTime(new Date());
			duty.setTreePath(getTreePath(treePathList));
			gylDutyService.update(duty);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/duty";
		}
	}
	
	/**
	 * 获得treepathlist
	 */
	private List<Long> treePathList(Long pid) {
		List<Long> result = Lists.newArrayList();
		while(pid != null && pid != 0L){
			GylDuty duty = gylDutyService.find(pid);
			result.add(0, pid);
			pid = duty.getPid();
		}
		return result;
	}
	
	/**
	 * 获得treePaht字符串，ps: 1,2,3,4,
	 *@param list
	 *@return
	 */
	private String getTreePath(List<Long> list){
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			ids.append(GylDuty.TREE_PATH_SEPARATOR+list.get(i));
		}
		ids.append(GylDuty.TREE_PATH_SEPARATOR);
		return ids.toString();
	}
	
	/**
	 * 返回所有菜单的json数据
	 */
	@RequestMapping( value ="/get_list",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getList(){
		List<GylDuty> dutys = gylDutyService.findAll();
		return JSON.toJSONString(dutys);
	}
	
	/**
	 * 树结构
	 */
	private List<GylDuty> treeList(List<GylDuty> dutys, Long id){
		List<GylDuty> result = new ArrayList<GylDuty>();
		if(dutys != null && dutys.size() >0 ){
			dutys.forEach((m)->{
				if(m.getPid().equals(id)){
					result.add(m);
					result.addAll(treeList(dutys, m.getId()));
				}
			});
		}
		return result;
	}
	
	
	@RequestMapping(value = "delete", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id){
		List<GylDuty> parentList = gylDutyService.findByPid(id);
		if(parentList != null && parentList.size() > 0){
			return this.jsonPrint(-1, "操作失败，请先删除下级职位！", null);
		}
		List<GylAdmin> admins = gylAdminService.findByDutyId(id);
		if(admins != null && admins.size() > 0 ){
			return this.jsonPrint(-2, "操作失败，请先删除当前职位下的人员", null);
		}
		Long delete = gylDutyService.delete(id);
		if(delete <= 0 ){
			return this.jsonPrint(-1, "删除失败", null);
		}
		return this.jsonPrint(1, "操作成功", null);
	}
	

	/**
	 * 选择duty
	 *@return
	 */
	@RequestMapping(value ="tree_select",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String treeSelect(){
		List<GylDuty> treeSelect = gylDutyService.treeSelect();
		return JSON.toJSONString(treeSelect);
	}
}
