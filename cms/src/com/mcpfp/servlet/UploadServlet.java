package com.mcpfp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mcpfp.web.modules.service.CoreService;


/**
 * 请求处理的核心类
 * 
 * @author liufeng
 * @date 2013-09-29
 */
public class UploadServlet extends HttpServlet
{
    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = 8235307207195444262L;

    /** 
    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
    */ 

    private static Logger log = LoggerFactory.getLogger(UploadServlet.class);
    
    private CoreService coreService; 
    
    @Override     
    public void init() throws ServletException     
    {         
        super.init();         
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());         
        coreService = (CoreService) wac.getBean("coreService");     
    }

    /**
     * 请求校验（确认请求来自微信服务器）
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
       
        out.close();
        out = null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        String str = null;
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String jsonString = request.getParameter("jsonstring");
        
//        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        System.out.println(jsonString);
     
        
//        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
        
        String result = null;
        
        
        
        
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);

        str = JSONObject.fromObject(map).toString();

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(str);
        out.close();
        

    }
}
