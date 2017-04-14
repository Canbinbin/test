package com.mcpfp.web.modules.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.generic.service.impl.BaseServiceImpl;
import com.mcpfp.web.modules.dao.ActuatorDao;
import com.mcpfp.web.modules.dao.CameraDao;
import com.mcpfp.web.modules.entity.Camera;
import com.mcpfp.web.modules.service.CameraService;

@Service("cameraService")
public class CameraServiceImpl extends BaseServiceImpl<Camera, Long> implements CameraService {
	
	@Resource(name="cameraDao")
	private CameraDao cameraDao;
	
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(cameraDao);
	}

	@Override
	public List<Camera> findByUersId(Long pid) {
		// TODO Auto-generated method stub
		return cameraDao.findByUserId(pid);
	}

	

	


}
