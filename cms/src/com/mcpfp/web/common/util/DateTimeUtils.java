package com.mcpfp.web.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 日期处理工具类
 * @author liujunqing
 * @version 1.0
 */
public class DateTimeUtils extends DateUtils{
	
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_DATE_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String PART_DATE_FORMAT = "yyyy-MM-dd";
	public static final String PART_DATE_FORMAT_DOT = "yyyy.MM.dd";
	public static final String PART_DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static final String PART_DATE_FORMAT_SHORT = "yyyyMMdd";
	public static final String PART_DATE_FORMAT_CN = "yyyy年MM月dd日";
	public static final String YEAR_DATE_FORMAT = "yyyy";
	public static final String MONTH_DATE_FORMAT = "MM";
	public static final String DAY_DATE_FORMAT = "dd";
	public static final String WEEK_DATE_FORMAT = "week";
	public static final String HH_MM_SS_DATE_FORMAT = "HH:mm:ss";
	
	
	
	
	/**
	 * 将日期类型转换为字符串
	 * @param date      日期
	 * @param xFormat   格式
	 * @return
	 */
	public static String getFormatDate(Date date,String xFormat){
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : FULL_DATE_FORMAT;
		SimpleDateFormat sdf  = new SimpleDateFormat(xFormat);
		return sdf.format(date);
	}
	
	
	
	
	/**
	 * 比较日期大小
	 * @param dateX
	 * @param dateY
	 * @return x < y return [-1];
	 * 		   x = y return [0] ; 
	 *         x > y return [1] ;
	 */
	public static int compareDate(Date dateX,Date dateY){
		return dateX.compareTo(dateY);
	}
	
	
	
	
	/**
	 * 将日期字符串转换为日期格式类型
	 * @param xDate
	 * @param xFormat 为NULL则转换如：2012-06-25
	 * @return
	 */
	public static Date parseString2Date(String xDate, String xFormat) {
		while(!isNotDate(xDate)){
			xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : PART_DATE_FORMAT;
			SimpleDateFormat sdf  = new SimpleDateFormat(xFormat);
			Date date = null ;
			try {
				date = sdf.parse(xDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			return date;
		}
		return null;
	}
	
	/**
	 * 将日期字符串转换为日期格式类型，支持格式:yyyy-MM-dd 、yyyy.MM.dd、 yyyy/MM/dd
	 *@param xDate
	 *@return
	 */
	public static Date parseString2Date(String xDate) {
		if(StringUtils.isEmpty(xDate)){
			return null;
		}
		String[] formats = {PART_DATE_FORMAT,PART_DATE_FORMAT_DOT,PART_DATE_FORMAT_SLASH};
		Date date = null ;
		for(String format:formats){
			SimpleDateFormat sdf  = new SimpleDateFormat(format);
			try {
				date = sdf.parse(xDate);
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	
	}
	
	/**
	 * 判断需要转换类型的日期字符串是否符合格式要求
	 * @param xDate   
	 * @param xFormat 可以为NULL
	 * @return
	 */
	public static boolean isNotDate(String xDate){
		SimpleDateFormat sdf  = new SimpleDateFormat(PART_DATE_FORMAT);
		try {
			if(StringUtils.isEmpty(xDate)){
				return true;
			}
			sdf.parse(xDate);
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * 获取俩个日期之间相差天数
	 * @param dateX
	 * @param dateY
	 * @return
	 */
	public static int getDiffDays(Date dateX,Date dateY){
		if ((dateX == null) || (dateY == null)){
			return 0;
		}
		
		int dayX = (int)(dateX.getTime() / (60 * 60 * 1000 * 24)); 
		int dayY = (int)(dateY.getTime() / (60 * 60 * 1000 * 24)); 
		
		return dayX > dayY ? dayX - dayY : dayY - dayX;
	}
	
	
	
	
	
	/**
	 * 获取传值日期之后几天的日期并转换为字符串类型
	 * @param date       需要转换的日期 date 可以为NULL 此条件下则获取当前日期
	 * @param after      天数
	 * @param xFormat    转换字符串类型 (可以为NULL)
	 * @return
	 */
	public static String getAfterCountDate(Date date, int after, String xFormat) {
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : PART_DATE_FORMAT;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, after);
		return getFormatDate(calendar.getTime(), xFormat);
	}
	
	
	
	
	
	/**
	 * 获取日期的参数 如：年 , 月 , 日 , 星期几
	 * 
	 * @param xDate 日期 可以为日期格式,可以是字符串格式; 为NULL或者其他格式时都判定为当前日期
	 * @param xFormat 年 yyyy 月 MM 日 dd 星期 week ;其他条件下都返回0
	 */
	public static int getDateTimeParam(Object xDate,String xFormat) {
		xDate = xDate == null ? new Date() : xDate;
		Date date = null;
		if(xDate instanceof String){
			date = parseString2Date(xDate.toString(), null);
		} else if(xDate instanceof Date){
			date = (Date) xDate;
		} else {
			date = new Date();
		}
		date = date == null ? new Date() : date;
		if (StringUtils.isNotEmpty(xFormat) 
				&& (xFormat.equals(YEAR_DATE_FORMAT) 
						|| xFormat.equals(MONTH_DATE_FORMAT)
						|| xFormat.equals(DAY_DATE_FORMAT)) ){
			return Integer.parseInt(getFormatDate(date, xFormat));
		} else if(StringUtils.isNotEmpty(xFormat) 
				&& (WEEK_DATE_FORMAT.equals(xFormat))) {
			Calendar cal = Calendar.getInstance(); cal.setTime(date);
			int week = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1 == 0 ? 
					7 : cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
			return week;
		} else {
			return 0;
		}
	}
	
	
	/**
	 * 日期格式转换为时间戳
	 * @param time
	 * @param format
	 * @return
	 */
	public static Long getLongTime(String time,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = sdf.parse(time);
				return (date.getTime()/1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
	/**
	 * 当前时间增加几分钟(比如：获取半小时后的时间，就是加30分钟)
	 * @param num
	 * @return
	 */
	public static String addLongTime(Integer num){
		 SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_FORMAT);
		Calendar nowTime = Calendar.getInstance();
		  nowTime.add(Calendar.MINUTE, num);
		return sdf.format(nowTime.getTime());
	}
	
	
	/**
	 * 获取星期字符串
	 * @param xDate
	 * @return
	 */
	public static String getWeekString(Object xDate){
		int week = getDateTimeParam(xDate,WEEK_DATE_FORMAT);
		switch (week) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		default : 
			return "";
		}
	}
	
	/**
	 * 获得十位时间
	 */
	public static Long getTenBitTimestamp(){
		return System.currentTimeMillis()/1000;
	}
	
	/**
	 * 判断一个时间在另一个时间段内（时分秒判断）
	 * @throws ParseException 
	 */
	public static boolean isInDates(Date myDate,String strDateBegin,String strDateEnd) throws ParseException{   
        SimpleDateFormat fullsdf = new SimpleDateFormat(FULL_DATE_FORMAT);  
        SimpleDateFormat hmsdf = new SimpleDateFormat(HH_MM_SS_DATE_FORMAT);  
        Date dateBegin  = hmsdf.parse(strDateBegin); 
        Date dateEnd =  hmsdf.parse(strDateEnd); 
        
        String strDate = fullsdf.format(myDate);
        strDateBegin = fullsdf.format(dateBegin);
        strDateEnd = fullsdf.format(dateEnd);  
          
        int strDateH = Integer.parseInt(strDate.substring(11,13));  
        int strDateM = Integer.parseInt(strDate.substring(14,16));  
        int strDateS = Integer.parseInt(strDate.substring(17,19));  
          
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(11,13));  
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(14,16));  
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(17,19));  
          
        int strDateEndH = Integer.parseInt(strDateEnd.substring(11,13));  
        int strDateEndM = Integer.parseInt(strDateEnd.substring(14,16));  
        int strDateEndS = Integer.parseInt(strDateEnd.substring(17,19));  
          
        if((strDateH>=strDateBeginH && strDateH<=strDateEndH)){  
            if(strDateH>strDateBeginH && strDateH<strDateEndH){  
                return true;  
            }else if(strDateH==strDateBeginH && strDateM>strDateBeginM && strDateH<strDateEndH){  
                return true;  
            }else if(strDateH==strDateBeginH && strDateM==strDateBeginM && strDateS>strDateBeginS && strDateH<strDateEndH){  
                return true;  
            }else if(strDateH==strDateBeginH && strDateM==strDateBeginM && strDateS==strDateBeginS && strDateH<strDateEndH){  
                return true;  
            }else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM<strDateEndM){  
                return true;  
            }else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM==strDateEndM && strDateS<strDateEndS){  
                return true;  
            }else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM==strDateEndM && strDateS==strDateEndS){  
                return true;  
            }else{  
                return false;  
            }  
        }else{  
            return false;  
        }  
    }  
	
	public static void main(String[] args) {
		try {
			System.out.println(isInDates(new Date(), "8:00:00","13:21:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer StringToTimestamp(String time) {

		int times = 0;
		try {
			times = (int) ((Timestamp.valueOf(time).getTime()) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (times == 0) {
			System.out.println("String转10位时间戳失败");
		}
		return times;

	}
	
    /**
     * 获取某天开始时间
     */
    public static Date getStartTime(Date date){
        
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();  
    }
    
    /**
     * 获取某天结束时间
     */
    public static Date getEndTime(Date date){ 
        
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        calendar.set(Calendar.MILLISECOND, 999);
        
        return calendar.getTime();  
    }
	
	/**
	 * 获取某天开始时间戳_10位
	 */
	public static Long getStartTimestamp(Date date){
        return getStartTime(date).getTime()/1000;
    }
	
	/**
	 * 获取某天结束时间戳_10位
	 */
	public static Long getEndTimestamp(Date date){ 
	    return getEndTime(date).getTime()/1000;  
    }
	
	/**
	 * //当前月的第一天     
	 *@return
	 */
	public static Long getCurrentMothdFirstDay(){
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat datep=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	           
	     cal.set(Calendar.DAY_OF_MONTH, 1); 
	      Date beginTime=cal.getTime();
	     String beginTime1=datef.format(beginTime)+" 00:00:00";
	     try {
			Date parse = datep.parse(beginTime1);
			return parse.getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     return 0L;
	}
	
	//当月最后一天
	public static Long getCurrentMothdLastDay(){
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat datep=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     cal.set( Calendar.DATE, 1 );
	     cal.roll(Calendar.DATE, - 1 );
	     Date endTime=cal.getTime();
	     String endTime1=datef.format(endTime)+" 23:59:59";
	     try {
				Date parse = datep.parse(endTime1);
				return parse.getTime()/1000;
		} catch (ParseException e) {
				e.printStackTrace();
		}
		return 0L;
	}
	
	//把日期往后增加一天.整数往后推,负数往前移动
	public static Date getAddDay(int day){
		Date date=new   Date();//取时间 
	     return getAddDay(date,day);
	}

	//把日期往后增加一天.整数往后推,负数往前移动
	public static Date getAddDay(Date date ,int day){
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	     return date;
	}


	/**
	 * 10位时间戳转Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date TimestampToDate(Integer time) {
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		Date date = new Date();
		try {
			date = ts;
			// System.out.println(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time
	 * @return
	 */
	public static String timestampToString(Integer time,String format) {
		// int转long时，先进行转型再进行计算，否则会是计算结束后在转型
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		String tsStr = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			// 方法一
			tsStr = dateFormat.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}


	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
}
