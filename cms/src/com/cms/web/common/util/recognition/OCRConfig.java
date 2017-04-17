package com.cms.web.common.util.recognition;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class OCRConfig{
	private Properties props;
	
	/**
	 * 返回单例
	 * @return Configuration单例
	 */
	public static OCRConfig getInstance(){
		return new OCRConfig();
	}
	
	/*
	 * 初始化配置文件
	 */
	private OCRConfig(){
		props = new Properties();
		
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(OCRConstant.OCR_CFG_FILE_NAME);
		if(input != null){
			try {
				props.loadFromXML(input);
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * ABBYY引擎动态连接库所在的目录
	 */
	public String getDllPath_32(){
		return props.getProperty(OCRConstant.OCR_DLL_PATH_32);
	}
	
	/*
	 * ABBYY引擎动态连接库所在的目录
	 */
	public String getDllPath_64(){
		return props.getProperty(OCRConstant.OCR_DLL_PATH_64);
	}
	
	/*
	 * ABBYY引擎的激活码
	 */
	public String getActiveCode(){
/*		String hostName = getHostNameForLiunx();
		if(null == hostName || "UnknownHost".equals(hostName) || "".equals(hostName))return "";
		
		//Server-1
		if(props.getProperty(OCRConstant.RUN_SERVER_1) != null && hostName.equals(props.getProperty(OCRConstant.RUN_SERVER_1))){
			return props.getProperty(OCRConstant.OCR_ACTIVE_CODE_SRV_1);
		}
		//Server-2
		if(props.getProperty(OCRConstant.RUN_SERVER_2) != null && hostName.equals(props.getProperty(OCRConstant.RUN_SERVER_2))){
			return props.getProperty(OCRConstant.OCR_ACTIVE_CODE_SRV_2);
		}
		//测试服务器
		if(props.getProperty(OCRConstant.DEV_SERVER_1) != null && hostName.equals(props.getProperty(OCRConstant.DEV_SERVER_1))){
			return props.getProperty(OCRConstant.OCR_ACTIVE_CODE_DEV_0);
		}*/
		
		return props.getProperty(OCRConstant.OCR_ACTIVE_CODE_DEV_0);
	}
	
	/*
	 * ABBYY引擎动态连接库所在的目录
	 */
	public String getDllPath(){
		if( is64BitJVMArchitecture() ) {
			return getDllPath_64();
		} else {
			return getDllPath_32();
		}
	}

	/*
	 * 服务器名
	 */
	public String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }
	
	/*
	 * Linux服务器名
	 */
	public String getHostNameForLiunx() {
	    try {
	    	return (InetAddress.getLocalHost()).getHostName();
	    } catch (UnknownHostException uhe) {
	    	String host = uhe.getMessage();
	    	if (host != null) {
	    		int colon = host.indexOf(':');
	    		if (colon > 0) {
	    			return host.substring(0, colon);
	    		}
	    	}
	    	return "UnknownHost";
	    }
	}
	
	/*
	 * Determines whether the JVM architecture is a 64-bit architecture
	 */
	private boolean is64BitJVMArchitecture(){
		String jvmKeys [] = {
			"sun.arch.data.model", 
			"com.ibm.vm.bitmode", 
			"os.arch"
		};
		for( String key : jvmKeys ) {
			String property = System.getProperty( key );
			if( property != null ) {
				if( property.indexOf( "64" ) >= 0 ) {
					return true;
				} else if( property.indexOf( "32" ) >= 0 ) {
					return false;
				} else if( property.indexOf( "86" ) >= 0 ) {
					return false;
				}
			}
		}
		return false;
	}
}