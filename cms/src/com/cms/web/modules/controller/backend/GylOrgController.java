package com.cms.web.modules.controller.backend;

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
import com.cms.web.common.controller.BaseController;
import com.cms.web.modules.entity.GylAdmin;
import com.cms.web.modules.entity.GylOrg;
import com.cms.web.modules.enums.OrgTypeEnum;
import com.cms.web.modules.service.GylAdminService;
import com.cms.web.modules.service.GylOrgService;


@Controller
@RequestMapping(value="/admin/org")
public class GylOrgController extends BaseController {

	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	
	@Resource(name = "gylOrgService")
	private GylOrgService gylOrgService;
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "")
	public String index(ModelMap model){
		List<GylOrg> treeList = treeList(gylOrgService.findAll(),0L);
		model.addAttribute("orgList",treeList );
		return "/backend/org/org_list";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	public String add(GylOrg org,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("orgTypes", OrgTypeEnum.values());
			model.addAttribute("porg", gylOrgService.find(org.getPid()));
			return "/backend/org/org_add";
		} else {
			List<Long> treePathList = treePathList(org.getPid());
			org.setLevel(treePathList.size());
			org.setFlag(1);
			org.setCreateTime(new Date());
			org.setModifyTime(new Date());
			org.setTreePath(getTreePath(treePathList));
			gylOrgService.save(org);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/org";
		}
	}
	
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(GylOrg org,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("orgTypes", OrgTypeEnum.values());
			GylOrg findorg = gylOrgService.find(org.getId());
			model.addAttribute("org",findorg );
			model.addAttribute("porg", gylOrgService.find(findorg.getPid()));
			return "/backend/org/org_edit";
		} else {
			List<Long> treePathList = treePathList(org.getPid());
			org.setLevel(treePathList.size());
			org.setFlag(1);
			org.setCreateTime(new Date());
			org.setModifyTime(new Date());
			org.setTreePath(getTreePath(treePathList));
			gylOrgService.update(org);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/org";
		}
	}
	
	/**
	 * 获得treepathlist
	 */
	private List<Long> treePathList(Long pid) {
		List<Long> result = Lists.newArrayList();
		while(pid != null && pid != 0L){
			GylOrg org = gylOrgService.find(pid);
			result.add(0, pid);
			pid = org.getPid();
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
			ids.append(GylOrg.TREE_PATH_SEPARATOR+list.get(i));
		}
		ids.append(GylOrg.TREE_PATH_SEPARATOR);
		return ids.toString();
	}
	
	/**
	 * 返回所有菜单的json数据
	 */
	@RequestMapping( value ="/get_list",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getList(){
		List<GylOrg> orgs = gylOrgService.findAll();
		return JSON.toJSONString(orgs);
	}
	
	/**
	 * 树结构
	 */
	private List<GylOrg> treeList(List<GylOrg> orgs, Long id){
		List<GylOrg> result = new ArrayList<GylOrg>();
		if(orgs != null && orgs.size() >0 ){
			orgs.forEach((m)->{
				if(m.getPid().equals(id)){
					result.add(m);
					result.addAll(treeList(orgs, m.getId()));
				}
			});
		}
		return result;
	}
	
	
	@RequestMapping(value = "delete", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id){
		List<GylOrg> parentList = gylOrgService.findByPid(id);
		if(parentList != null && parentList.size() > 0){
			return this.jsonPrint(-1, "操作失败，请先删除下级部门！", null);
		}
		List<GylAdmin> admins = gylAdminService.findByOrgId(id);
		if(admins != null && admins.size() > 0 ){
			return this.jsonPrint(-2, "操作失败，请先删除当前部门下的人员", null);
		}
		Long delete = gylOrgService.delete(id);
		if(delete <= 0 ){
			return this.jsonPrint(-1, "删除失败", null);
		}
		return this.jsonPrint(1, "操作成功", null);
	}
	

	/**
	 * 选择org
	 *@return
	 */
	@RequestMapping(value ="tree_select",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String treeSelect(){
		List<GylOrg> treeSelect = gylOrgService.treeSelect();
		return JSON.toJSONString(treeSelect);
	}
}
