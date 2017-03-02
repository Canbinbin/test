package com.mcpfp.web.common.util.recognition.IK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Dictionary {
	private static Dictionary singleton;
	
	private String cateFile = "com/zhg/web/common/util/recognition/dict/mcpfp_cate_match.dic";
	private String warehouseFile = "com/zhg/web/common/util/recognition/dict/mcpfp_warehouse_match.dic";
	
	private Map<String,String> cateDictionary;
	private Map<String,String> warehouseDictionary;
	
	private Dictionary(){
		this.loadCateDictionary();//加载品类词典
		this.loadWarehouseDictionary();//加载交割库区词典
	}
	

	/**
	 * 获取品类词典
	 *@return
	 */
	public Map<String, String> getCateDictionary() {
		return cateDictionary;
	}


	/**
	 * 获取交割库区词典
	 *@return
	 */
	public Map<String, String> getWarehouseDictionary() {
		return warehouseDictionary;
	}


	/**
	 * 词典初始化
	 */
	public static Dictionary initial(){
		try{
			if(singleton == null){
				synchronized(Dictionary.class){
					if(singleton == null){
						singleton = new Dictionary();
						return singleton;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}

		return singleton;
	}
	
	/**
	 * 获取词典单例对象
	 * @return Dictionary 单例对象
	 */
	public static Dictionary getSingleton(){
		if(singleton == null){
			throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
		}
		return singleton;
	}
	
	
	private void loadCateDictionary(){
		cateDictionary = new HashMap<String,String>();
		
		//读取品种扩展词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cateFile);
        if(is == null){
        	throw new RuntimeException("Cate Dictionary not found!!!");
        }
        
		try {
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = bufferReader.readLine();
				if(StringUtils.isBlank(theWord)){
					continue;
				}
				
				String[] theWordArray = theWord.trim().split("=");
				if(null == theWordArray || (theWordArray.length != 1 && theWordArray.length != 2)){
					continue;
				}
				String key = theWordArray[0];
				String value = key;
				if(theWordArray.length == 2){
					value = theWordArray[1];
				}
				if (!cateDictionary.containsKey(key)) {
					cateDictionary.put(key.trim().toUpperCase(), value.trim().toUpperCase());
				}
				
			} while (theWord != null);
		} catch (IOException ex) {
			System.err.println("Cate Extension Dictionary loading exception.");
			ex.printStackTrace();
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void loadWarehouseDictionary(){
		warehouseDictionary = new HashMap<String,String>();
		
		//读取交割库区扩展词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(warehouseFile);
        if(is == null){
        	throw new RuntimeException("Warehouse Dictionary not found!!!");
        }
        
		try {
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = bufferReader.readLine();
				if(StringUtils.isBlank(theWord)){
					continue;
				}
				
				String[] theWordArray = theWord.trim().split("=");
				if(null == theWordArray || (theWordArray.length != 1 && theWordArray.length != 2)){
					continue;
				}
				String key = theWordArray[0];
				String value = key;
				if(theWordArray.length == 2){
					value = theWordArray[1];
				}
				
				if (!warehouseDictionary.containsKey(theWordArray[0])) {
					warehouseDictionary.put(key.trim().toUpperCase(), value.trim().toUpperCase());
				}
			} while (theWord != null);
		} catch (IOException ex) {
			System.err.println("Warehouse Extension Dictionary loading exception.");
			ex.printStackTrace();
		}finally{
			try {
				if(is != null){
                    is.close();
                    is = null;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Dictionary d = Dictionary.initial();
		System.out.println(d.getCateDictionary());
		System.out.println(d.getWarehouseDictionary());
	}
}