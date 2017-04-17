package com.cms.web.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

/**
 * 短信模型
 * @author linjiande
 *
 */
public class SMSUtils {
	
	private static Logger log = LoggerFactory.getLogger(SMSUtils.class);
	
	public static final String corpId = "301729";
	public static final String loginName = "Admin";
	public static final String passwd = "310415";
	
	public static final String sendUrl = "http://sms3.mobset.com/SDK/Sms_Send.asp?CorpID="+corpId+"&LoginName="+loginName+"&Passwd="+passwd+"&send_no={0}&Timer=&LongSms=0&msg={1}";
	
	/**
	 * 信息类型枚举
	 *  状态    1：注册验证码   2：修改登录账户验证码   3：修改登录账户后的新账户验证码  4：找回密码的验证码   5：注册成功的短信信息
	 *       6：修改账号成功的短信信息   7：找回密码成功后的短信信息  8：客服发送给注册会员属于他的跟进交易员信息  9:短信发送app下载链接
	 */ 
	public static enum SMS_TYPE_ENUM{
		st1(1,"注册验证码"),st2(2,"修改登录账户验证码"),st3(3,"修改登录账户后的新账户验证码"),
		st4(4,"找回密码的验证码"),st5(5,"注册成功的短信信息"),st6(6,"修改账号成功的短信信息"),
		st7(7,"找回密码成功后的短信信息"),st8(8,"客服发送给注册会员属于他的跟进交易员信息"),
		st9(9,"短信发送app下载链接");
		private Integer code;
		private String value;
		private SMS_TYPE_ENUM(Integer code,String value) {
			this.code = code;
			this.value = value;
		}
		public Integer getCode() {
			return this.code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getValue() {
			return this.value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static enum SMS_TEMPLATE_ID_ENUM{
		TMPL1(64822,"注册验证码"),TMPL2(64823,"修改登录账户验证码"),TMPL3(64824,"修改登录账户后的新账户验证码"),
		TMPL4(64825,"找回密码的验证码"),TMPL5(64826,"注册成功的短信信息"),TMPL6(64827,"修改账号成功的短信信息"),
		TMPL7(64828,"找回密码成功后的短信信息"),TMPL8(64829,"客服发送给注册会员属于他的跟进交易员信息");
		private Integer code;
		private String value;
		private SMS_TEMPLATE_ID_ENUM(Integer code,String value) {
			this.code = code;
			this.value = value;
		}
		public Integer getCode() {
			return this.code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getValue() {
			return this.value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	
	private SMSUtils() {
	}

	/**
	 * 手机信息发送
	 * @param mobile  手机号码
	 * @param verifyCode   验证码，如果没有可以为空
	 * @param content   手机内容
	 * @return
	 *//*
	public synchronized static boolean sendSms(String mobile,String content){
//		if("off".equals(SwicthSMSUtil.getOnOFF())){
//		return true;
//	}
	String result ="";
	try {
		content= URLEncoder.encode(content, "GBK");
        URL url = new URL(sendUrl.replace("{0}", mobile).replace("{1}", content));
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream())));
        result = in.readLine().toString();
     }catch (UnsupportedEncodingException e) {
		e.printStackTrace();
     }catch(IOException e){
        e.printStackTrace();
     }
	if(result.length()>0){
		log.info("短信发送返回结果：【"+getResult(Integer.valueOf(result.charAt(0)))+"】");
		return true;
	}
	return false;
}*/
    
	
	public synchronized static boolean sendTemplateSMS(String to,String[] datas, String tempId) {
		//主帐号,对应开官网发者主账号下的 ACCOUNT SID
		String accountSid= "aaf98f894f16fdb7014f26baa174165b";
		
		//主帐号令牌,对应官网开发者主账号下的 AUTH TOKEN
		String accountToken= "9b3de7d022df4d57bfedabd4f5a05bd7";
		
		//应用Id，在官网应用列表中点击应用，对应应用详情中的APP ID
		//在开发调试的时候，可以使用官网自动为您分配的测试Demo的APP ID
		String appId="aaf98f894f16fdb7014f2a152c761881";
		
		//请求地址
		//沙盒环境（用于应用开发调试）：sandboxapp.cloopen.com
		//生产环境（用户应用上线使用）：app.cloopen.com
		String serverIP="app.cloopen.com";
		
		
		//请求端口，生产环境和沙盒环境一致
		String serverPort="8883";
		
		//REST版本号，在官网文档REST介绍中获得。
		String softVersion="2013-12-26";
		// 初始化REST SDK
		//global $accountSid, $accountToken, $appId, $serverIP, $serverPort, $softVersion;
		
		HashMap result = null; 
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(serverIP,serverPort);
		// 初始化服务器地址和端口，沙盒环境配置成sandboxapp.cloopen.com，生产环境配置成app.cloopen.com，端口都是8883. 
		 restAPI.setAccount(accountSid, accountToken);
		 // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在"控制台-应用"中看到开发者主账号ACCOUNT SID和 
		 //主账号令牌AUTH TOKEN。
		 restAPI.setAppId(appId);
		 // 初始化应用ID，如果是在沙盒环境开发，请配置"控制台-应用-测试DEMO"中的APPID。
		 //如切换到生产环境，请使用自己创建应用的APPID

		// 初始化应用ID，如果是在沙盒环境开发，请配置"控制台-应用-测试DEMO"中的APPID。
		 //如切换到生产环境，请使用自己创建应用的APPID
		 result = restAPI.sendTemplateSMS(to,tempId ,datas);
//		 System.out.println("SDKTestGetSubAccounts result=" + result);
		 log.info("发送短信返回结果：{}",result);
		 if("000000".equals(result.get("statusCode"))){
			 //正常返回输出data包体信息（map）
			 HashMap data = (HashMap) result.get("data");
			 Set keySet = data.keySet();
			 for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
					Object key = (Object) iterator.next();
//					 System.out.println(key +" = "+data.get(key)); 
					log.info("{}={}",key,data.get(key));
			}
			 return true;
		 }else{
			 //异常返回输出错误码和错误信息
//			 System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			 log.error("错误码 = {}， 错误信息 = {}", result.get("statusCode"),result.get("statusMsg"));
			 return false;
		 }
		
		/*$rest->setAccount ( $accountSid, $accountToken );
		$rest->setAppId ( $appId );
		
		// 发送模板短信
		$result = $rest->sendTemplateSMS ( $to, $datas, $tempId );
		if ($result == NULL) {
			$arr['msg']="发送失败!";
			$arr['status']=-1;
			return $arr;
		}
		if ($result->statusCode != 0) {
			$arr['msg']=$result->statusMsg;
			$arr['status']=$result->statusCode;
		} else {
			$this->add ( array (
					'mobile' => $to,
					'verify_code' => trim($datas[0]),
					'send_time' => date ( "Y-m-d H:i:s", time ()),
					'expires_time' => date ( "Y-m-d H:i:s", time () + 1800 ),
					'sms_type' => $tempId,
					'verify_ip' => x::get_client_ip () 
			) );
			$arr['msg']="发送成功";
			$arr['status']=1;
		}
		return $arr;*/
	}
	
	
	 /** 
     * @param 短信模板选择
     * @return 短信内容
     */
    public static String getSmsTemplate(SMS_TYPE_ENUM type,String... paras){
        String content = null;
    	switch(type){
            case st1:
                //注册验证码( 31226)
                //content="欢迎注册找化工网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
            	content = "31226";
                break;
            case st2:
                //修改登录账户验证码
                content="尊敬的找化工网会员，您本次修改登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st3:
                //修改登录账户后的新账户验证码
                content="尊敬的找化工网会员，您绑定新登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st4:
                //找回密码的验证码
                content="尊敬的找化工网会员，您本次修改登录密码的手机验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st5:
                //注册成功的短信信息
                content="感谢您注册找化工网，您的账号：{0}，密码：{1}。客服热线:020-83939808。";
                break;
            case st6:
                //修改账号成功的短信信息
                content="您在找化工网注册的登录账号已修改成功,新登录账号：{0}，请妥善保管您的注册信息。";
                break;
            case st7:
                //找回密码成功后的短信信息
                content="尊敬的找化工网会员，您已成功修改了账号名为：{0}的登录密码，新登录密码是：{1}。";
                break;
            case st8:
                //客服发送给注册会员属于他的跟进交易员信息
                content="尊敬的找化工网会员，找化工网为您分配专职交易服务人员{0}，{1}。一个电话，买卖搞定！";
                break;
            case st9:
                //短信发送app下载链接
                content="感谢您下载找化工网手机客户端，请点击以下链接进行下载：http://url.cn/SkymOa。";
                break;
            default:
                //注册验证码
                content="欢迎注册找化工网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
        }
    	for(int i = 0 ; i < paras.length ; i++){
    		content = content.replace("{"+i+"}", paras[i]);
        }
    	return content;
    }
	
    
  /*  *//** 
     * @param 短信模板选择
     * @return 短信内容
     *//*
    public static String getSmsTemplate(SMS_TYPE_ENUM type,String... paras){
        String content = null;
    	switch(type){
            case st1:
                //注册验证码
                content="欢迎注册找化工网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
                break;
            case st2:
                //修改登录账户验证码
                content="尊敬的找化工网会员，您本次修改登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st3:
                //修改登录账户后的新账户验证码
                content="尊敬的找化工网会员，您绑定新登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st4:
                //找回密码的验证码
                content="尊敬的找化工网会员，您本次修改登录密码的手机验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st5:
                //注册成功的短信信息
                content="感谢您注册找化工网，您的账号：{0}，密码：{1}。客服热线:020-83939808。";
                break;
            case st6:
                //修改账号成功的短信信息
                content="您在找化工网注册的登录账号已修改成功,新登录账号：{0}，请妥善保管您的注册信息。";
                break;
            case st7:
                //找回密码成功后的短信信息
                content="尊敬的找化工网会员，您已成功修改了账号名为：{0}的登录密码，新登录密码是：{1}。";
                break;
            case st8:
                //客服发送给注册会员属于他的跟进交易员信息
                content="尊敬的找化工网会员，找化工网为您分配专职交易服务人员{0}，{1}。一个电话，买卖搞定！";
                break;
            case st9:
                //短信发送app下载链接
                content="感谢您下载找化工网手机客户端，请点击以下链接进行下载：http://url.cn/SkymOa。";
                break;
            default:
                //注册验证码
                content="欢迎注册找化工网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
        }
    	for(int i = 0 ; i < paras.length ; i++){
    		content = content.replace("{"+i+"}", paras[i]);
        }
    	return content;
    }*/

    public static String getResult(Integer code){
        String errorMessage ="";
        if(code==0){
        	return "发送成功";
        }else{
           switch(code){
           case 0:
               case -1:
                    errorMessage="参数不全";
                    break;
               case -2:
                    errorMessage="用户名或密码验证错误";
                    break;
               case -3:
                    errorMessage="发送短信余额不足";
                    break;
               case -4:
                    errorMessage="没有手机号码或手机号码超过100个";
                    break;
               case -5:
                    errorMessage="发送手机里含有错误号码";
                    break;
               case -6:
                    errorMessage="内容超长";
                    break;
               case -7:
                    errorMessage="短信中含有非法字符或非法词汇";
                    break;
              // case "非法字":$error='短信中含有非法字符或非法词汇';break;
               case -8:
                    errorMessage="未开放HTTP接口";
                    break;
               case -9:
                    errorMessage="IP地址认证失败";
                    break;
               case -10:
                    errorMessage="发送时间限制";
                    break;
               case -11:
                    errorMessage="短信类别ID不存在或不正确";
                    break;
               case -12:
                    errorMessage="提交的短信条数不正确";
                    break;
               default:
                    errorMessage="未知的错误";
           } 
        }
        return errorMessage;
    }

    /**
     * 群发短信
     */
    /*public function send_sms_batch($data)
    {
        $para=$this->_config;
        $para['Timer']='';
        $para['LongSms']=1;

        foreach($data as $k=>$v)
        {
            $para['send_no']=$k;
            $para['msg']=rawurlencode(iconv('UTF-8', 'GBK', $v));
            $url = $this->_url.'?'.x::query_string($para);
            $string = file_get_contents($url);
            $log_file=ROOT_PATH.'/temp/log/sms.log';
            error_log(date('Y-m-d H:i:s').' | '.$string.' | '.$k.' | '.$v."\n",3,$log_file);
        }

    }*/
}
