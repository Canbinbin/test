package com.mcpfp.web.common.util;

import java.util.List;

public class ListUtils {
	
	/**
	 * 把数组转为字符以p分割
	 */
	public static String implode(List<?> l, String p){
		StringBuffer sb = new StringBuffer();
		int i=0;
		for(Object o : l){
			if(i==0){
				sb.append(o);
			}else {
				sb.append(p+o);
			}
			i++;
		}
		return sb.toString();
	}

}
