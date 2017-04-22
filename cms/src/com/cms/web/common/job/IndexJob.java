package com.cms.web.common.job;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Job - 5分钟静态化首页一次
 * 
 * @author linjiande
 * @version 1.0
 */
@Component("indexJob")
@Lazy(false)
public class IndexJob {

	private static Logger log = LoggerFactory.getLogger(IndexJob.class);
	
	

	/**
	 * 5分钟轮询
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void staticIndex() {
		System.out.println("每5分钟查询");
	}
	

}