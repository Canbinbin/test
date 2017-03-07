package com.mcpfp.web.common.shiro;

import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.mcpfp.web.modules.entity.GylAdmin;
import com.mcpfp.web.modules.service.GylAdminService;
import com.mcpfp.web.modules.service.GylMenuService;
/**
 * 项目名称：inCms    
 * 类名称：SystemAuthorizingRealm    
 * 类描述：    系统安全认证实现类
 * 创建人：liujunqing    
 * 创建时间：2015年10月20日  
 * @version 1.0    
 *
 */
@Service(value="systemAuthorizingRealm")
@Slf4j
//@Data
//@EqualsAndHashCode(callSuper=false)
public class SystemAuthorizingRealm extends AuthorizingRealm {
	public static Map<String, Map<String, Integer>> cacheMap = Maps.newHashMap();
	
	@Resource(name = "gylAdminService")
	private GylAdminService gylAdminService;
	@Resource(name = "gylMenuService")
	private GylMenuService gylMenuService;
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("call AuthorizationInfo doGetAuthorizationInfo。。。。。。。。。。");
		Principal principal = (Principal) getAvailablePrincipal(principals);
		GylAdmin sysUser = gylAdminService.find(principal.getUserId());
		if (sysUser != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			List<GylMenu> list = gylMenuService.findMenuBy(principal.getUserId(), null, null);
//			for (GylMenu menu : list){
//				if (StringUtils.isNotBlank(menu.getAuthFlag())){
//					System.out.println(menu.getChildMenus());
//					// 添加基于Permission的权限信息
//						info.addStringPermission(menu.getAuthFlag());
//					log.info("【"+sysUser.getUsername()+"】："+menu.getAuthFlag());
//				}
//			}
			return info;
		} else {
			return null;
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		AuthenticationUsernameToken token = (AuthenticationUsernameToken) authcToken;
		Session session = SecurityUtils.getSubject().getSession();
		// 判断验证码
		/*if (isValidateCodeLogin(token.getHost(), true, false)){
			String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (token.getCaptcha() == null || !token.getCaptcha().toLowerCase().equals(code)){
				throw new AuthenticationException("验证码错误.");
			}
		}*/
		String username = token.getUsername();
		String password = new String(token.getPassword());
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
			GylAdmin sysUser = null;
			try {
				sysUser = gylAdminService.findByUsername(username);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			if(sysUser == null){
				throw new UnknownAccountException("当前账号不存在.");
			}
			if(!DigestUtils.md5Hex(password).equals(sysUser.getPassword())){
				throw new AuthenticationException("用户名与密码不正确.");
			}
			Principal principal = new Principal(sysUser.getId(),sysUser.getUsername(),sysUser.getRoleId(),sysUser.getOrgId());
			session.setAttribute(Principal.SESSION_PRINCIPAL_ATTR, principal);
			return new SimpleAuthenticationInfo(principal, token.getPassword(),token.getUsername());
		}
		throw new UnknownAccountException("登录出错.");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	public static boolean isValidateCodeLogin(String ip, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)cacheMap.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			cacheMap.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(ip);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(ip, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(ip);
		}
		return loginFailNum >= 3;
	}
	
	/**
	 * 是否显示验证码
	 *@return
	 */
	public static boolean isShowValidateCode(String ip){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)cacheMap.get("loginFailMap");
		if(loginFailMap != null){
			Integer loginFailNum = loginFailMap.get(ip);
			return loginFailNum != null && loginFailNum >= 3;
		}else{
			return false;
		}
		
	}

}
