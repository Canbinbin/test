package com.mcpfp.web.common.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * 项目名称：gyl_zsl    
 * 类名称：LogInterceptor    
 * 类描述：    系统拦截器
 * 创建人：liujunqing    
 * 创建时间：2015年3月31日  
 * @version 1.0    
 *
 */
@Slf4j
public class ExceptionInterceptor  implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	@Transactional(readOnly = false)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		if(ex != null){
			log.error(ex.getMessage(),ex);
		}
		try {
			String requestRri = request.getRequestURI();
			String uriPrefix = request.getContextPath() ;
			StringBuilder params = new StringBuilder();
			if(!requestRri.contains("logout")){
				//Principal principal = (Principal)request.getSession(true).getAttribute(Principal.SESSION_PRINCIPAL_ATTR);
				//log.debug("principal:" + principal);
			}
			
			int index = 0;
			for (Object param : request.getParameterMap().keySet()){ 
				params.append((index++ == 0 ? "" : "&") + param + "=");
				params.append(StringUtils.abbreviate(StringUtils.endsWithIgnoreCase((String)param, "password") ? "" : request.getParameter((String)param), 100));
			}
			log.warn("url:"+uriPrefix+"/"+requestRri+"===>:".concat(params.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		
		
	}
	

}
