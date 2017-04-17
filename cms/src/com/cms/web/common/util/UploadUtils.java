package com.cms.web.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;


/**
 * 	上传工具类
 * @author liujunqing
 * @version 1.0
 */
public class UploadUtils {
	
	public static final String allowUploadImageType = "jpg,jpge,bmp,gif,png";
	private static final Logger log = LoggerFactory.getLogger(UploadUtils.class);
	
	/**
	 * 
	 *@param request
	 *@param file
	 *@return map 返回key: 文件名（fileName）,md5码：md5Code:
	 *@return module 存放的目录名
	 */
	public static Map<String,String> saveMartipartFileAndHd5File(HttpServletRequest request,MultipartFile file,String module) {
		Preconditions.checkNotNull(module);
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = simpleDateFormat.format(new Date());
			
			File dir = new File(request.getSession().getServletContext()
					.getRealPath("/" )+"/upload/"+module+ "/"+dateString);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
			File newFile = new File(dir, fileName);
			file.transferTo(newFile);
			Map<String,String> map = Maps.newHashMap();
			map.put("fileName","/upload/"+module+ "/"+dateString +"/"+ fileName);
			String md5Code= MD5FileUtils.getFileMD5String(newFile);
			map.put("md5Code", md5Code);
			return map;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}
	
	public static String saveMartipartFile(HttpServletRequest request,MultipartFile file,String module) {
		if(file != null){
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
				String dateString = simpleDateFormat.format(new Date());
				File dir = new File(request.getSession().getServletContext()
						.getRealPath("/")+"/upload/"+module+ "/"+dateString );
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						throw new Exception("创建保存目录失败");
					}
				}
				String fileName = UUID.randomUUID().toString() + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
				file.transferTo(new File(dir, fileName));
				return "/upload/"+module+ "/"+dateString + "/" + fileName;
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	//上传文件
	public static String  uploadFile(File sourceFile,String classPath, String fileName) throws IOException {
		String targetFilePath = getTargetFilePath(classPath,fileName,null);
		
		File targetFile = new File(targetFilePath);

		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//  将临时文件拷贝到目标目录,delete 是否删除临时文件默认为true
		FileUtils.copyFile(sourceFile, targetFile,true);
		return targetFilePath;
	}
	
	
	
	
	
	//上传图片并剪切
//	public static void uploadAndCutFile(String classPath,String fileName,String moduleFlag,Integer x,Integer y, Integer w,Integer h){
//		
//		String sourcePath = getSourceFilePath(classPath,fileName, moduleFlag);
//		
//		String targetPath = getTargetFilePath(classPath,fileName, moduleFlag);
//		
//		ImageUtils.abscut(sourcePath, targetPath, x, y, w, h);
//		
//	}
//	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 创建目标文件路径
	 * @param classPath
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	public static String getTargetFilePath(String classPath,String fileName, String module) {
		
		// 上传文件后缀名
		String ext = FileUtils.getFileExt(fileName);

		// 上传文件资源类型
		String type = FileUtils.getSourceFileType(ext);
		type = StringUtils.isNotEmpty(type) == true ? type : "UNKWON";

		if (type.equalsIgnoreCase("IMG") || type.equalsIgnoreCase("VID")
				|| type.equalsIgnoreCase("DOC")) {
			//目录路径
			String direct = classPath + File.separator + "upload"
					+ File.separator + type.toLowerCase() + File.separator
					+ module.toLowerCase() + File.separator
					+ DateTimeUtils.getFormatDate(new Date(), "yyyyMM") + File.separator ;


			File filedirect = new File(direct);
			//判断是否目录存在,不存在则创建目录
			if (!filedirect.isDirectory()) {
				filedirect.mkdirs();
			}
		
			return direct + UUID.randomUUID() + "." + ext;
		}
		
		return null ;
	}
	/**
	 * 创建源文件路径
	 * @param classPath
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	private static String getSourceFilePath(String classPath ,String fileName, String module) {
		// 上传文件后缀名
		String ext = FileUtils.getFileExt(fileName);

		// 上传文件资源类型
		String type = FileUtils.getSourceFileType(ext);
		type = StringUtils.isNotEmpty(type) == true ? type : "UNKWON";

		if (type.equalsIgnoreCase("IMG") || type.equalsIgnoreCase("VID")
				|| type.equalsIgnoreCase("DOC")) {
			//目录路径
			String direct = classPath + File.separator + "resource"
					+ File.separator + type.toLowerCase() + File.separator
					+ module.toLowerCase() + File.separator
					+ DateTimeUtils.getFormatDate(new Date(), "yyyyMM") + File.separator ;

			File filedirect = new File(direct);
			//判断是否目录存在,不存在则创建目录
			if (!filedirect.isDirectory()) {
				filedirect.mkdirs();
			}
		
			return direct + fileName;
		}
		return null ;
	}
}
