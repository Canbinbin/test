package com.mcpfp.web.modules.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 核心服务类
 * 
 * @author mocanbin
 * @date 2017-03-23
 */
@Service("CoreService")
public class CoreService
{
    private static Logger log = LoggerFactory.getLogger(CoreService.class);
  
    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request)
    {
        String respMessage = null;
        // 默认返回的文本消息内容
        String respContent = "";
        // 回复文本消息
       
        return respMessage;
    }
    
   
    /** */
    /**
     * 格式化中文字符，防止出现乱码
     * 
     * @param str
     * @return
     */
    public static String codeToString(String str)
    {
        String strString = str;
        try
        {
            byte tempB[] = strString.getBytes("ISO-8859-1");
            strString = new String(tempB);
            return strString;
        }
        catch(Exception e)
        {
            return strString;
        }
    }

    /** */
    /**
     * 获取完整的Url
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static String getBackUrl(HttpServletRequest request)
            throws Exception
    {
        String strBackUrl = "";
        try
        {
            strBackUrl = "http://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath();
//                    + request.getServletPath() + "?"
//                    + codeToString(request.getQueryString());
            //strBackUrl = java.net.URLEncoder.encode(strBackUrl, "gbk");
        }
        catch(Exception e)
        {
            throw e;
        }
        return strBackUrl;
    }
    
    
    /**
     * 获取星期几
     * 
     * @return
     */
    public static  String getWeekStr() 
    {
        String weekStr = null;
        Calendar   calendar   =   Calendar.getInstance();   
        int   week   =   calendar.get(Calendar.DAY_OF_WEEK)-1;   
        switch(week){   
                case   0:   
                        weekStr   =   "星期日";   
                        break;   
                case   1:   
                        weekStr   =   "星期一";   
                        break;   
                case   2:   
                        weekStr   =   "星期二";   
                        break;   
                case   3:   
                        weekStr   =   "星期三";   
                        break;   
                case   4:   
                        weekStr   =   "星期四";   
                        break;   
                case   5:   
                        weekStr   =   "星期五";   
                        break;   
                case   6:   
                        weekStr   =   "星期六";   
                        break;   
        }   
        
        return weekStr;

    }
    public String GetNowDate()
    {   
        String tempStr="";   
        Date dt = new Date();   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        tempStr=sdf.format(dt);   
        return tempStr;   
    }  
}