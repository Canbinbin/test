package com.cms;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;










import cn.edu.jnu.fastbits.entity.CmdCandidateEntity;
import cn.edu.jnu.fastbits.rest.api.upper.UpperFastbitsIO;
import cn.edu.jnu.fastbits.rest.http.MessageCode;
import cn.edu.jnu.fastbits.rest.http.Resp;

import com.cms.config.UpperFastbitsIOSingleton;




public class TestCms {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

//		UpperFastbitsIO upperFastbitsIO = UpperFastbitsIOSingleton.upperFastbitsIO();
//
//		Resp<List<PointEntity>> resp = upperFastbitsIO.findPointByOwner(1+"");

		TestCms testCms =new TestCms();
		Resp<Integer> resp = testCms.findByUersId();
		System.out.println(resp);

	}
	public Resp<Integer> findByUersId() {

		UpperFastbitsIO upperFastbitsIO = UpperFastbitsIOSingleton.upperFastbitsIO();
		List<CmdCandidateEntity> list = new ArrayList<CmdCandidateEntity>();
	
				CmdCandidateEntity cmdCandidateEntity = new CmdCandidateEntity();
				cmdCandidateEntity.setCommand("ceshi");
				cmdCandidateEntity.setDescription("ceshishish");
				cmdCandidateEntity.setEntityId("5290116a-9724-4cd2-9443-e7c6eb3e755f");
				list.add(cmdCandidateEntity);
		
			Resp<Integer> resp = upperFastbitsIO.addCmdCandidates(list);
		if (resp.getMsgCode().equals(MessageCode.SUCCESS)) {
			System.out.println(resp.getData());
		}
		else {
			System.out.println(resp.getMsgDesc());
		}
		return resp;
		
	}

}
