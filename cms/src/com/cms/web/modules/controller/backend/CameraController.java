package com.cms.web.modules.controller.backend;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.generic.page.domain.Page;
import com.cms.web.common.controller.BaseController;
import com.cms.web.common.util.CommonUtil;
import com.cms.web.common.util.HttpUtils;
import com.cms.web.modules.entity.Actuator;
import com.cms.web.modules.entity.Camera;
import com.cms.web.modules.service.ActuatorService;
import com.cms.web.modules.service.CameraService;
@Controller
@RequestMapping(value="/admin/camera")
public class CameraController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(CameraController.class);
	
	@Resource(name = "cameraService")
	private CameraService cameraService;
	
	@RequestMapping(value="")
	public String list(ModelMap model){
		Long userId = getPrincipal().getUserId();
		List<Camera> lists = cameraService.findByUersId(userId);
		model.put("pageList", lists);
		return "/backend/camera/cam_list";
	}
	@RequestMapping(value="/cameraView")
	public String cameraView(ModelMap model,String liveAddress){
		model.put("address", liveAddress);
		return "/backend/camera/video";
	}
	@RequestMapping(value="/video")
	public String vedio_list(ModelMap model){
		Long userId = getPrincipal().getUserId();
		List<Camera> lists = cameraService.findByUersId(userId);
	    List<Camera> ll = new ArrayList<Camera>();
	    for (int i = 0;i<lists.size();i++) {
	    	Camera camera = lists.get(i);
			String live = camera.getLiveAddress();
	    	if ((live!=null && live.length()>0)) {
				ll.add(camera);
			}
			
		}
		model.put("pageList", ll);
		return "/backend/camera/video_list";
	}
	
	@RequestMapping(value="/add")
	public String add(){
		return "/backend/camera/cam_add";
	}
	
	@RequestMapping(value = "save")
	public String save(Camera camera,RedirectAttributes redirectAttributes){
		try {
			String open_url="https://open.ys7.com/api/lapp/token/get?appKey="+camera.getAppkey()+"&appSecret="+camera.getSecret();
			JSONObject jsonObject = CommonUtil.httpsRequest(open_url, "POST", null);
			if (null != jsonObject)
			{
				String code = jsonObject.getString("code");
				if (code.equals("200")) {
					String accessToken = jsonObject.getJSONObject("data").getString("accessToken");
					String time = jsonObject.getJSONObject("data").getString("expireTime");
					camera.setAccessToken(accessToken);
					camera.setTokenTime(time);
				}
				else {
					this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器添加失败!请检查appkey与appsecret是否正确");
				}
			}
			camera.setCamTime(new Date());
			camera.setUserId(getPrincipal().getUserId());
			long orgId = getPrincipal().getOrgId();
			camera.setOrgId(orgId);
			cameraService.save(camera);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "添加失败:"+e.getMessage());
		}
		return "redirect:/admin/camera";
	}
	@RequestMapping(value = "updatesave")
	public String update(Camera camera,RedirectAttributes redirectAttributes){
		try {
			
			camera.setCamTime(new Date());
			camera.setUserId(getPrincipal().getUserId());
			cameraService.update(camera);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.SUCCESS, "操作成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "传感器操作失败:"+e.getMessage());
		}
		return "redirect:/admin/camera";
	}
	
	@RequestMapping(value = "update")
	public String update(Long id,ModelMap model){
		Camera camera = cameraService.find(id);
		model.put("camera", camera);
		return "/backend/camera/cam_edit";
	}
	
	@RequestMapping(value = "device",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String device(String appKey,String secret,RedirectAttributes redirectAttributes){
		try {
			String accessToken;
			String expireTime;
			String open_url="https://open.ys7.com/api/lapp/token/get?appKey="+appKey+"&appSecret="+secret;
			JSONObject jsonObject = CommonUtil.httpsRequest(open_url, "POST", null);
			Camera camera = new Camera();
			camera.setAppkey(appKey);
			camera.setSecret(secret);
			if (null != jsonObject)
			{
				String code = jsonObject.getString("code");
				if (code.equals("200")) {
					accessToken = jsonObject.getJSONObject("data").getString("accessToken");
					expireTime = jsonObject.getJSONObject("data").getString("expireTime");
					camera.setTokenTime(expireTime);
					String res = getDeviceList(accessToken,camera);
					this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "res");
					return this.jsonPrint(1, res, null);
				}
				else {
					this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "查询失败!请检查appkey与appsecret是否正确");
					return this.jsonPrint(-1, "查询失败!请检查appkey与appsecret是否正确", null);
				}
			}
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
	
	private String getDeviceList(String accessToken,Camera camera) {

		String res="操作成功！";
		String open_url="https://open.ys7.com/api/lapp/camera/list?accessToken="+accessToken;
		JSONObject jsonObject = CommonUtil.httpsRequest(open_url, "POST", null);
		
		if (null != jsonObject)
		{
			String code = jsonObject.getString("code");
			if (code.equals("200")) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if (jsonArray.size()<=0) {
					res = "没有摄像头！";
				}
				else {
					for (int i = 0; i < jsonArray.size(); i++) {
						camera.setDeviceSerial(jsonArray.getJSONObject(i).getString("deviceSerial"));
						camera.setChannelNo(jsonArray.getJSONObject(i).getString("channelNo"));
						camera.setAccessToken(accessToken);
						camera.setCamName("摄像头"+camera.getDeviceSerial());
						camera.setCamTime(new Date());
						camera.setUserId(getPrincipal().getUserId());
						long orgId = getPrincipal().getOrgId();
						camera.setOrgId(orgId);
						cameraService.save(camera);
					}
				}
			}
			else {
				res= "查询出错！";
			}
		}
		return res;
	}

	@RequestMapping(value = "delete",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(Long id,RedirectAttributes redirectAttributes){
		try {
			cameraService.delete(id);
			return this.jsonPrint(1, "操作成功", null);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			this.addFlashMessage(redirectAttributes, MessageTypeEnum.ERROR, "操作失败:"+e.getMessage());
			return this.jsonPrint(-1, "操作失败:"+e.getMessage(), null);
		}
	}
}
