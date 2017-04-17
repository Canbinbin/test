/**    
 * 文件名：XmlUtils.java    
 *    
 * 版本信息：    
 * 日期：2016年1月19日    
 * Copyright 广州找塑料网络科技有限公司 Corporation 2016     
 * 版权所有    
 *    
 */
package com.cms.web.common.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**    
 *     
 * 项目名称：zhg-web    
 * 类名称：XmlUtils    
 * 类描述：    
 * 创建人：zhangyonghao    
 * 创建时间：2016年1月19日  
 * @version 1.0    
 *     
 */
public class XmlUtils {

	private XmlUtils(){}
	
	/**
	 * 将xmlStr转为 w3c Document
	 * @param xmlStr
	 * @return
	 */
	public static Document parse(String xmlStr) {
		DocumentBuilderFactory dbf = getDocumentBuilderFactory();
		
		StringReader sr = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			sr = new StringReader(xmlStr);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			
			return document;
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(sr);
		}
	}
	
	/**
	 * 获取xml节点中的文本
	 * @param element
	 * @param name
	 * @return
	 */
	public static String elementText(Element element, String name) {
		NodeList node = element.getElementsByTagName(name);
		if (node.getLength() == 0) {
			return null;
		}
		return node.item(0).getTextContent();
	}
	
	/**
	 * 获取xml文档中的文本
	 * @param element
	 * @param name
	 * @return
	 */
	public static String documentText(Document doc, String name) {
		NodeList node = doc.getElementsByTagName(name);
		if (node.getLength() == 0) {
			return null;
		}
		return node.item(0).getTextContent();
	}
	
	/**
	 * 获取节点下的xml文档
	 * @param element
	 * @param name
	 * @return
	 */
	public static Document element(Element element, String name) {
		NodeList list = element.getElementsByTagName(name);
		if (list.getLength() == 0) {
			return null;
		}
		return list.item(0).getOwnerDocument();
	}
	
	private static DocumentBuilderFactory getDocumentBuilderFactory(){
		return DocumentBuilderFactoryHolder.INSTANCE;
	}
	
	private static class DocumentBuilderFactoryHolder {
		private static DocumentBuilderFactory INSTANCE = DocumentBuilderFactory.newInstance();
	}
}
