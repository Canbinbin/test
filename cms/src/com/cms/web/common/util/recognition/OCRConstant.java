package com.cms.web.common.util.recognition;

public class OCRConstant {
	/* 分词后每个分词所属的类型 */
	public static final String ENGLISH    = "ENGLISH";    //英文词
	public static final String ARABIC     = "ARABIC";     //阿拉伯数字，比如11,314.55
	public static final String LETTER     = "LETTER";     //英文数字混合，比如d9000
	public static final String CN_WORD    = "CN_WORD";    //中文词    比如"你好"
	public static final String CN_CHAR    = "CN_CHAR";    //中文词，只包含一个字符
	public static final String OTHER_CJK  = "OTHER_CJK";  //日韩文辞
	public static final String TYPE_CNUM  = "TYPE_CNUM";  //中文数字  比如"十二"
	public static final String COUNT      = "COUNT";      //中文量词，比如"平方公里"
	public static final String TYPE_CQUAN = "TYPE_CQUAN"; //中文量词，比如"平方公里"
	public static final String UNKONW     = "UNKONW";     //未定义类型
	
	/* PVC、 PP PE的默认吨数 */
	public static final String AMOUNT_PVC = "100";
	public static final String AMOUNT_PPPE = "30";
	
	/* 如果含有【提单】，则放入到备注中 */
	public static final String TIDAN = "提单";
	
	/* ABBYY引擎配置文件路径*/
	public static final String OCR_CFG_FILE_NAME = "ocr.cfg.xml";
	public static final String OCR_DLL_PATH_32 = "OCR_DLL_PATH_32";
	public static final String OCR_DLL_PATH_64 = "OCR_DLL_PATH_64";
	/* ABBYY Runtime 激活码*/
	public static final String OCR_ACTIVE_CODE_DEV_0 = "OCR_ACTIVE_CODE_DEV_0";
	public static final String OCR_ACTIVE_CODE_SRV_1 = "OCR_ACTIVE_CODE_SRV_1";
	public static final String OCR_ACTIVE_CODE_SRV_2 = "OCR_ACTIVE_CODE_SRV_2";
	
	/* ABBYY引擎配置文件路径*/
	public static final String DEV_SERVER_1 = "DEV_SERVER_1";
	public static final String RUN_SERVER_1 = "RUN_SERVER_1";
	public static final String RUN_SERVER_2 = "RUN_SERVER_2";
	
	/* 默认类型*/
	public static final String DEFAULT_TYPE_PVC = "PVC";
	public static final String DEFAULT_TYPE_PPPE = "HDPE";
}