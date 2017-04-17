<%@page import="com.cms.web.common.shiro.SystemAuthorizingRealm"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.apache.shiro.SecurityUtils" %>
<%@page import="org.apache.shiro.subject.Subject" %>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.cms.web.common.util.SpringUtils"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
boolean validateCode = false;
String username = request.getParameter("username");
if (applicationContext != null) {
	if(username != null){
		validateCode = SystemAuthorizingRealm.isValidateCodeLogin(username, true, false);
	}
%>
<shiro:authenticated>
<%
response.sendRedirect(base + "/common/main");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
<%

String message = null;
if (applicationContext != null) {
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException") || loginFailure.equals("com.zsl.web.common.security.CaptchaException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		} else if(loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if(loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("com.zsl.web.common.security.CloseTimeException")) {
			message = "admin.login.closeTimeException";
		} else {
			message = "admin.login.otherException";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" /><meta http-equiv="Pragma" content="no-cache" /><meta http-equiv="Expires" content="0" />
<meta name="author" content="http://www.zhaohuagong.com"/><meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
<script src="<%=base%>/resources/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=base%>/resources/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
<link href="<%=base%>/resources/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=base%>/resources/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
<link href="<%=base%>/resources/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lte IE 6]><link href="<%=base%>/resources/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]> <script src="<%=base%>/resources/common/html5.js"></script><![endif]-->
<script src="<%=base%>/resources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=base%>/resources/common/mustache.min.js" type="text/javascript"></script>
<script src="<%=base%>/resources/list/list.min.js" type="text/javascript"></script>
<script src="<%=base%>/resources/select2/select2.js" type="text/javascript"></script>
<script src="<%=base%>/resources/select2/select2_locale_zh-CN.js" type="text/javascript"/>
<link href="<%=base%>/resources/select2/select2.css" type="text/css" rel="stylesheet" />
<link href="<%=base%>/resources/x-editable/css/bootstrap-editable.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/x-editable/js/bootstrap-editable.js" type="text/javascript"></script>
<link href="<%=base%>/resources/fancyBox/source/jquery.fancybox.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/fancyBox/source/jquery.fancybox.js" type="text/javascript"></script>
<link href="<%=base%>/resources/common/baisu.min.css" type="text/css" rel="stylesheet" />
<script src="<%=base%>/resources/common/baisu.min.js" type="text/javascript"></script>
<link href="<%=base%>/resources/jquery-jbox/2.3/Skins/Bootstrap/jbox.css" rel="stylesheet" />
<script src="<%=base%>/resources/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="<%=base%>/resources/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.min.js" type="text/javascript"></script>
<link rel="shortcut icon" href="<%=base%>/resources/favicon.ico"/>	
<link rel="stylesheet" href="<%=base%>/resources/common/typica-login.css"/>
<script src="<%=base%>/resources/common/common.js" type="text/javascript"></script>
<style type="text/css">
	.control-group{border-bottom:0px;}
	/*登录验证码图片样式 */
    .verify{width:80px;height:30px;}
</style>
   <script src="<%=base%>/resources/common/backstretch.min.js"></script>
   <%
   	
   %>
<script type="text/javascript">
	$(document).ready(function() {
		var $username = $("#username");	
		var $password = $("#password");	
	  	var $isRememberUsername = $("#rememberMe");
	  	var $validateCode = $("#validateCode");
		 $.backstretch([
		      "<%=base%>/resources/images/bg1.jpg", 
		      "<%=base%>/resources/images/bg2.jpg",
		      "<%=base%>/resources/images/bg3.jpg"
		  	], {duration: 10000, fade: 2000}); 
		
		$("#loginForm").validate({
			rules: {
				 validateCode: {remote: "<%=base%>/admin/common/validateCode"}
				/* username: "required",
				password: "required",
				validateCode: "required" */
			}, 
			messages: {
				username: {required: "请填写用户名."},password: {required: "请填写密码."},
				validateCode: {remote:"验证码不正确.", required: "请填写验证码."}
			},
			errorLabelContainer: "#messageBox",
			errorPlacement: function(error, element) {
				error.appendTo($("#loginError").parent());
			} 
		});
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		  	
		<%if (message != null) {%>
			$.jBox.tip("<%=SpringUtils.getMessage(message)%>","error");
		<%}%>
		// 表单验证、记住用户名
		$("#loginForm").submit( function() {
			if ($username.val() == "") {
				$.jBox.tip("用户名不能空","error");
				return false;
			}
			if ($password.val() == "") {
				$.jBox.tip("密码不能空","error");
				return false;
			}
			if ($validateCode.val() == "") {
				$.jBox.tip("验证码","error");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
		});
	});
	// 如果在框架中，则跳转刷新上级页面
	if(self.frameElement && self.frameElement.tagName=="IFRAME"){
		parent.location.reload();
	}
</script>
<%} %>
	</head>
	<body>
		
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#"><img src="<%=base%>/resources/images/logo.gif" alt="" style="height:40px;"></a>
        </div>
      </div>
    </div>

    <div class="container">
		<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
		
		<div id="messageBox" class="alert alert-error <% if(message == null){ out.print("hide");}%>"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">
				<%if (message != null) {%>
					<%=SpringUtils.getMessage(message)%>
				<%}%>
			</label>
		</div>
        <div id="login-wraper">
            <form id="loginForm"  class="form login-form" action="<%=base%>/admin/login.jsp" method="post">
                <fieldset><legend><span style="color:#08c;">系统登录</span></legend></fieldset>
                <div class="body">
					<div class="control-group">
						<div class="controls">
							<label for="control-group">用户名：</label>
							<input type="text" id="username" name="username" class="required" value="" placeholder="登录名">
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<label for="control-group">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
							<input type="password" id="password" name="password" class="required"  placeholder="密码"/>
						</div>
					</div>
					<%if(validateCode) {%>
						<div class="validateCode">
								<label for="validateCode">验&nbsp;&nbsp;证&nbsp;&nbsp;码：</label>
								<input type="text" id="validateCode" name="validateCode" maxlength="5" class="txt required" style="font-weight:bold;width:45px;margin-bottom:0;" placeholder="验证码"/>
								<img src="<%=base%>/yzm/captcha-image?timestamp='"' + (new Date()).valueOf()+'"' onclick="$('.validateCodeRefresh').click();" class="mid validateCodeImg .validateCode verify"/>
								<a href="javascript:" onclick="$('.validateCodeImg').attr('src','<%=base%>/yzm/captcha-image?timestamp='+new Date().getTime());" class="mid validateCodeRefresh">看不清</a>
						</div>
					<%}%>
                </div>
                <div class="footer">
                    <label class="checkbox inline">
                        <input type="checkbox" id="rememberMe" name="rememberMe1"> <span style="color:#08c;">记住我</span>
                    </label>
                    <input class="btn btn-primary" type="submit" value="登 录"/>
                </div>
				<%-- <div id="themeSwitch" class="dropdown pull-right">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">默认主题<b class="caret"></b></a>
					<ul class="dropdown-menu">
					  <li><a href="#" onclick="location='<%=base%>/admin/common/theme/default?url='+location.href">默认主题</a></li>
					  <li><a href="#" onclick="location='<%=base%>/admin/common//theme/cerulean?url='+location.href">天蓝主题</a></li>
					  <li><a href="#" onclick="location='<%=base%>/admin/common//theme/readable?url='+location.href">橙色主题</a></li>
					  <li><a href="#" onclick="location='<%=base%>/admin/common//theme/united?url='+location.href">红色主题</a></li>
					</ul>
					<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
				</div> --%>
            </form>
        </div>
    </div>
    <footer class="white navbar-fixed-bottom">
		<!-- Copyright &copy; 2012-2013 <a href="zsl/f">baisu Admin</a> - Powered By <a href="http://www.zhaohuagong.com/" target="_blank">baisu</a> V1.1.0 -->
    </footer>
  
	</body>
</html>
