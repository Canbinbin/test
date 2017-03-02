package com.mcpfp.web.modules.service;

import java.util.Map;

/**
 * Service - 静态化
 * 
 * @author jiande
 * @version 1.0
 */
public interface IStaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath);

	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int buildIndex();

	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);

	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

}