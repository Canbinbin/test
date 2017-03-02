package com.mcpfp.web.common.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class SnUtils implements Serializable {
		private static final long serialVersionUID = 7921724644078006L;
		private static SnUtils primaryGenerater = null;
	  /*  private SimpleDateFormat sdFormat = new SimpleDateFormat("yyMMddhhmmssSSS");*/
	    private long salt = 79217246440780L;
	    private SnUtils() {
	    }
	 
	    /**
	     * 取得SnUtils的单例实现
	     *
	     * @return
	     */
	    public static SnUtils getInstance() {
	        if (primaryGenerater == null) {
	            synchronized (SnUtils.class) {
	                if (primaryGenerater == null) {
	                    primaryGenerater = new SnUtils();
	                }
	            }
	        }
	        return primaryGenerater;
	    }
	 
	    /**
	     * 生成编号
	     */
	    public synchronized String genSn() {
    		long myTime = System.currentTimeMillis();
    		int nextInt = RandomUtils.nextInt(999);
    		 DecimalFormat df = new DecimalFormat("000");
    		 String format = df.format(nextInt);
    		 String sn = String.valueOf(myTime).concat(format);
    		 Long lsn =Long.valueOf(sn)+salt;
    		return String.valueOf(lsn);
	    }
	    
	    
	    /**
	     * 单据号自动生成原则：FKSQ+日期+四位流水号(金融付款申请)
	     */
	    public synchronized String genPaymentApplySn(String prefix,Integer codeNum) {
	    	StringBuilder sn = new StringBuilder(prefix);
	    	//8位日期
	    	String ymd = DateFormatUtils.format(new Date(), "yyyyMMdd");
	    	sn.append(ymd);
	    	DecimalFormat df = new DecimalFormat("0000");
	    	String format = df.format(codeNum);
	    	sn.append(format);
	    	return sn.toString();
	    }
	    
	    
}
