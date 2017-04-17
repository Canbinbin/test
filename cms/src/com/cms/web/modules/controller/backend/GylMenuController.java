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
import com.cms.web.modules.entity.GylMenu;
import com.cms.web.modules.enums.MenuTypeEnum;
import com.cms.web.modules.service.GylMenuService;

@Controller
@RequestMapping(value="/admin/menu")
public class GylMenuController extends BaseController {
	
	@Resource(name = "gylMenuService")
	GylMenuService gylMenuService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "")
	public String index(ModelMap model){
		List<GylMenu> treeList = treeList(gylMenuService.findAll(),0L);
		model.addAttribute("menuList",treeList );
		return "/backend/menu/menu_list";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	public String add(GylMenu menu,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("menuTypes", MenuTypeEnum.values());
			model.addAttribute("pmenu", gylMenuService.find(menu.getPid()));
			return "/backend/menu/menu_add";
		} else {
			List<Long> treePathList = treePathList(menu.getPid());
			menu.setLevel(treePathList.size());
			menu.setFlag(1);
			menu.setCreateTime(new Date());
			menu.setModifyTime(new Date());
			menu.setTreePath(getTreePath(treePathList));
			gylMenuService.save(menu);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/menu";
		}
	}
	
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update")
	public String update(GylMenu menu,ModelMap model,RedirectAttributes redirectAttributes){
		if(this.isGet()){
			model.addAttribute("menuTypes", MenuTypeEnum.values());
			GylMenu findmenu = gylMenuService.find(menu.getId());
			model.addAttribute("menu",findmenu );
			model.addAttribute("pname", gylMenuService.find(findmenu.getPid()).getName());
			return "/backend/menu/menu_edit";
		} else {
			List<Long> treePathList = treePathList(menu.getPid());
			menu.setLevel(treePathList.size());
			menu.setFlag(1);
			menu.setCreateTime(new Date());
			menu.setModifyTime(new Date());
			menu.setTreePath(getTreePath(treePathList));
			gylMenuService.update(menu);
			addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
			return "redirect:/admin/menu";
		}
	}
	
	/**
	 * 获得treepathlist
	 */
	private List<Long> treePathList(Long pid) {
		List<Long> result = Lists.newArrayList();
		while(pid != null && pid != 0L){
			GylMenu menu = gylMenuService.find(pid);
			result.add(0, pid);
			pid = menu.getPid();
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
			ids.append(GylMenu.TREE_PATH_SEPARATOR+list.get(i));
		}
		ids.append(GylMenu.TREE_PATH_SEPARATOR);
		return ids.toString();
	}
	
	/**
	 * 返回所有菜单的json数据
	 */
	@RequestMapping( value ="/get_list",method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getList(){
		List<GylMenu> menus = gylMenuService.findAll();
		return JSON.toJSONString(menus);
	}
	
	/**
	 * 树结构
	 */
	private List<GylMenu> treeList(List<GylMenu> menus, Long id){
		List<GylMenu> result = new ArrayList<GylMenu>();
		if(menus != null && menus.size() >0 ){
			menus.forEach((m)->{
				if(m.getPid().equals(id)){
					result.add(m);
					result.addAll(treeList(menus, m.getId()));
				}
			});
		}
		return result;
	}
	
	
	/**
	 * 选择duty
	 *@return
	 */
	@RequestMapping(value ="tree_select",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String treeSelect(){
		List<GylMenu> treeSelect = gylMenuService.treeSelect();
		return JSON.toJSONString(treeSelect);
	}
	
	
	@RequestMapping(value = "delete", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id){
		List<GylMenu> parentList = gylMenuService.findByPid(id);
		if(parentList != null && parentList.size() > 0){
			return this.jsonPrint(-1, "操作失败，请先删除下级菜单！", null);
		}
		Long delete = gylMenuService.delete(id);
		if(delete <= 0 ){
			return this.jsonPrint(-1, "删除失败", null);
		}
		return this.jsonPrint(1, "操作成功", null);
	}
	
}
