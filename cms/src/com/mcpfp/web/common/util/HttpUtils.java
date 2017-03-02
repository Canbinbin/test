/**    
 * 文件名：HttpUtils.java    
 *    
 * 版本信息：    
 * 日期：2016年1月19日    
 * Copyright 广州找塑料网络科技有限公司 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mcpfp.web.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
//	public static final MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml; charset=utf-8");
	
//	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
	
	 //连接超时时间，默认10秒
    private static final int SOCKET_TIMEOUT = 10000;

    //传输超时时间，默认30秒
    private static final int CONNECT_TIMEOUT = 30000;
	
	private HttpUtils() {}
	
	
//	public static String post(String reqStr,String url,MediaType mediaType){
//		OkHttpClient client = new OkHttpClient();
//		if(null == mediaType){
//			mediaType = MEDIA_TYPE_XML;
//		}
//		RequestBody  body = RequestBody.create(mediaType, reqStr);
//		  Request request = new Request.Builder()
//		      .url(url)
//		      .post(body)
//		      .build();
//		  Response response = null;
//		  String result = null;
//		  try{
//			  response = client.newCall(request).execute();
//			  result = response.body().string();
//		  }catch(IOException e){
//			  logger.error(e.getMessage(), e);
//		  }
//		  
//		  return result;
//	}
//	
//	public static String postXml(String reqXml,String url){
//		return post(reqXml,url,MEDIA_TYPE_XML);
//	}
//	
//	
//	public static String postJson(String reqJson,String url){
//		return post(reqJson,url,MEDIA_TYPE_JSON);
//	}
//	
//	public static String get(String url){
//		OkHttpClient client = new OkHttpClient();
//
//		Request request = new Request.Builder().url(url).build();
//		Response response = null;
//		String result = null;
//		try {
//			response = client.newCall(request).execute();
//			result = response.body().string();
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
//		return result;
//	}
	
	
	
	private static CloseableHttpClient init(boolean isSecure,String certPath,String certPassword) throws KeyStoreException, IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException{
		CloseableHttpClient httpClient = null;
		if(isSecure){
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        FileInputStream instream = new FileInputStream(new File(certPath));//加载本地的证书进行https加密传输
	        try {
	            keyStore.load(instream, certPassword.toCharArray());//设置证书密码
	        } catch (CertificateException e) {
	            e.printStackTrace();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } finally {
	        	IOUtils.closeQuietly(instream);
	        }
	
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, certPassword.toCharArray())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[]{"TLSv1"},
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	
	        httpClient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
		}else{
			httpClient = HttpClients.custom().build();
		}
       
		return httpClient;
	}
	
	/**
	 * 发送http post请求
	 *@param url
	 *@param xml 要发送的xml的字符串
	 *@param isSecure		是否加密
	 *@param certPath		证书路径
	 *@param certPassword	证书密码
	 *@return
	 */

    public static String post(String url, String xml,boolean isSecure,String certPath,String certPassword)  {
		String result = null;
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = init(isSecure, certPath, certPassword);
			httpPost = new HttpPost(url);

			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(xml, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);

			// 根据默认超时限制初始化requestConfig
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).build();

			// 设置请求器的配置
			httpPost.setConfig(requestConfig);

			logger.info("发送http post 请求：{}" ,httpPost.getRequestLine());

			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

			logger.info("得到应答：{}",result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			httpPost.abort();
			IOUtils.closeQuietly(httpClient);
		}

		return result;
    }
    
    
   public static String get(String url,boolean isSecure,String certPath,String certPassword){
		String result = null;
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		try {
			httpClient = init(isSecure, certPath, certPassword);
			httpGet = new HttpGet(url);
			
			// 根据默认超时限制初始化requestConfig
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).build();

			// 设置请求器的配置
			httpGet.setConfig(requestConfig);

			logger.info("发送http get 请求:{}", httpGet.getRequestLine());

			HttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");
			
			logger.info("得到应答：{}",result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			httpGet.abort();
			IOUtils.closeQuietly(httpClient);
		}

		return result;
   }
    
   public static String get(String url){
	   return get(url,false,null,null);
   }
	
	public static void main(String[] args) throws Exception {
	
		HttpUtils.get("http://www.baidu.com");
	}
}

