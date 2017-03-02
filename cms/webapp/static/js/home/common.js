



$(function() {
    //模板消息
    function flashMessage() {
        if ($("#flash_message").length > 0 && $("#flash_message").val() != "") {
            layer.msg($("#flash_message").val());
        }
    }
    flashMessage();
    
	var headerSearch={
	        init:function(url1,url2,url3){
	            this.cacheElement();
	            this.tabToggle();
	            this.textToggle();
	            this.submitCallback(url1,url2,url3);
	            this.keyupEvent();
	        },
			cacheElement:function(){
	            var that=this;
				$.each([
	                ".tab",
	                "#searchForm"
	                ],function(i,item){
	                    that["$"+item.replace(/\.|\#/g,"")]=$(item);
	            })
			},
			
			tabToggle:function(){
				var that=this;
	            that.$tab.on("click","a",function(){
	                var idx=$(this).attr("val");
	                    that.$tab.find("a").removeClass("on");
	                    $(this).addClass("on");
	                    that.$searchForm.find(".input").css('color','#999');
	                    if(idx==1){
	                        that.$searchForm.find(".input").val('请输入品种');
	                    }else if(idx==2){
	                        that.$searchForm.find(".input").val('请输入品种/公司名称');
	                    }else if(idx==3){
	                         that.$searchForm.find(".input").val('请输采购品种');
	                    }
	                    
	            });
			},
	        textToggle:function(){
	            var that=this;
	            var idx=0;
	                that.$searchForm.on("focus",".input",function(){
	                    var str=$(this).val();
	                    idx=that.$tab.find(".on").attr('val');
	                    if(str=='请输入品种' || str=='请输入品种/公司名称' || str=='请输采购品种'){
	                        $(this).val("");
	                        $(this).css('color','#000');  
	                    }
	                });
	                that.$searchForm.on("blur",".input",function(){
	                    if($(this).val()==''){
	                        $(this).css('color','#999');
	                        if(idx==1){
	                            that.$searchForm.find(".input").val('请输入品种');
	                        }else if(idx==2){
	                            that.$searchForm.find(".input").val('请输入品种/公司名称');
	                        }else if(idx==3){
	                            that.$searchForm.find(".input").val('请输采购品种');
	                        }
	                    }
	                })
	
	        },
	        submitCallback:function(url1,url2,url3){
	            var that=this;
	            var idx=0;
	            that.$searchForm.find(".button").click(function(){
	                idx=that.$tab.find(".on").attr('val');
	                if(idx==1){
	                     that.$searchForm.attr("action",url1);
	                     that.$searchForm.submit();
	                }else if(idx==2){
	                     that.$searchForm.attr("action",url2);
	                     that.$searchForm.submit();
	                }else if(idx==3){
	                     that.$searchForm.attr("action",url3);
	                     that.$searchForm.submit();
	                }
	            });
	        },
	        keyupEvent:function(){
				var that=this;
				that.$searchForm.find(".input").keydown(function(e){
					if(e.keyCode==13){
						that.$searchForm.find(".button").trigger("click");
						e.preventDefault();
						return false;
					}
				})
			}
	}
	/*
	参数1 搜供应商 url
	参数2 搜现货 url
	参数3 搜采购url   
	*/
	headerSearch.init('/product', '/company','/purchase');
    //修复不支持placeholder属性浏览器
    if($.fn.placeholder){
        $("[placeholder]").placeholder();
    };
    
    
    var loginBoxShow={
    		init:function(){
    			if($("#loginmask").length>0 && $(".login_form2").length>0){
    				$("#loginmask").show();
					$(".login_form2").show();
    			}else{
    				this.createHtml();
    			};
    			this.bindEvent();
    		},
    		createHtml:function(){
    			var str='';
		    		str+='<div id="loginmask" style=""></div>';
		    		str+='<div class="login_form2">';
		    		str+='	<div class="title">';    
		    		str+='	    <span>会员登录</span>';
		    		str+='	    <a href="javascript:void(0)">X</a>';
		    		str+='	</div>';
		    		str+='	<form id="loginform">';
		    		str+='	   <div class="form_item input_item clearfloat">';
		    		str+='	       <span class="fl icon_wrapper"><i class="icons username_icon">&nbsp;</i></span>';
		    		str+='	       <input type="text" name="mobile" id="mobile" class="input_text fl" placeholder="请输入手机号码">';
		    		str+='	    </div>';
		    		str+='	    <div class="form_item input_item clearfloat">';
		    		str+='	        <span class="fl icon_wrapper"><i class="icons password_icon">&nbsp;</i></span> ';
		    		str+='	        <input type="password" name="password" id="password" class="input_text input_pasword fl" placeholder="请输入密码" >';
		    		str+='	    </div>';
		    		str+='	    <div class="form_item label_item clearfloat">';
		    		str+='	       <label class="checkbox_label fl">';
		    		str+='	            <i class="checkbox_box icons checkbox_box_icon"></i><input type="checkbox" class="input_checkbox" name="autologin" id="autologin" value="1" />2周自动登录';
		    		str+='	       </label>';
		    		str+='	            <a href="#" class="fr" onclick="javascript:window.top.location.href=\'/user/find_password\';">忘记登录密码？</a>';
		    		str+='	    </div>'; 
		    		str+='	    <div class="form_item clearfloat">';
		    		str+='	          <input type="button" value="登录" class="btn btn_submit">';
		    		str+='	          <input type="hidden" name="redirect" value="${referer}" />';
		    		str+='	     </div>';  
		    		str+='	        <div class="form_item no_margin_bottom clearfloat">';
		    		str+='	            <a href="#" class="btn btn_register" onclick="javascript:window.top.location.href=\'/user/register\';">注册找化工网账户</a>';
		    		str+='	        </div>';                                          
		    		str+='	    </form>';            
		    		str+='	</div>';
		    		
		    		$("body").append(str);
    		},
    		bindEvent:function(){
    			$(".form_item .btn_submit").on("click",function(){
    				var mobileVal=$.trim($(".login_form2").find("#mobile").val());
    				var passwordVal=$.trim($(".login_form2").find("#password").val());
    				if(mobileVal==''){
    					layer.msg('帐户不能为空',{time:2000});
    					return false;
    				}else if(passwordVal==''){
    					layer.msg('密码不能为空',{time:2000});
    					return false;
    				}
    				var json=$("#loginform").serialize();
    				$.ajax({
    					url:'user/login',
    					type:'post',
    					dataType:'json',
    					data:json,
    					success:function(data){
    						if(data.status==1){
    							layer.msg(data.msg, {time:2000});
    							$("#loginmask").hide();
    							$(".login_form2").hide();
    							$("#js-login-info").html('尊敬的 '+ data.data.linkman +' <a href="/member/logout">【退出】</a>  欢迎来到找化工网!');
    						}else{
    							layer.msg(data.msg, {time:2000});
    						}
    					},
    					error:function(){
    						layer.msg('登录失败，请稍候再试！',{time:2000});
    						$("#loginmask").hide();
							$(".login_form2").hide();
    					}
    				})
    			});
    			
    			$(".login_form2 .title a").on("click",function(){
    				$("#loginmask").hide();
					$(".login_form2").hide();
    			});
    		}
    };
    window.loginBoxShow=loginBoxShow;
    
    var linkman = $.getcookie("linkman");
	if (linkman != "" && linkman != "null" && linkman != null) {
		$("#js-login-info").html('尊敬的 '+ linkman +'<a href="/member/logout">【退出】</a>  欢迎来到找化工网!');
	}else{
		$("#js-login-info").html('您好，欢迎来到找化工网!【<a href="/user/login">会员登录</a> | <a href="/user/register">免费注册</a>】');
	}
});

(function(window, $, undefined) {
    var Common = window.Common || {};

    Common.util = Common.util || {};
    $.extend(Common.util, {
        //文本框输入限制
        restrictFiled: function() {
            var namespace = ".commonRestrictFiled";
            $(document)
                .off(namespace)
                .on("keydown" + namespace, "[data-restrict=\"number\"]", function(e) {
                    var name = this.name,
                        keyCode = e.keyCode;

                    if (e.altKey || e.ctrlKey || e.shiftKey || ({
                        "8": 1,
                        "9": 1,
                        "37": 1,
                        "38": 1,
                        "39": 1,
                        "40": 1,
                        "110": 1,
                        "190": 1
                    })[keyCode] || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105)) {
                        //数字或者backspace
                    } else {
                        e.preventDefault();
                    }
                });
        }
    });

    Common.validate = Common.validate || {};
    $.extend(Common.validate, {
        mobile: {
            msg: "手机号码格式不正确",
            validate: function(value){
                return value.length === 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(15[0-9]{1}))+\d{8})$/.test(value);
            }
        },

        telephone: {
            msg: "电话号码格式不正确",
            validate: function(value){
                return /^(?:[\(|（]?\s*\d{3,4}[\)|）]?\s*[-|－]?\s*)?[1-9]\d{6,7}(?:\s*-\s*\d+)?$/.test(value);
            }            
        },

        password: {
            msg: "请输入6-16个字符",
            validate: function(value){
                return /^.{6,16}$/.test($.trim(value));
            }            
        },

        username: {
            msg: "请输入2-30个汉字",
            validate: function(value){
                return /^[\u4e00-\u9fa5]{2,30}$/.test($.trim(value));
            }             
        },

        company: {
            msg: "请输入完整的公司全称",
            validate: function(value){
                var result = false;

                $.each([
                    '公司',
                    'CO',
                    'COMPANY',
                    'CO.,LTD',
                    'Ltd',
                    'limited',
                    'CORP',
                    'Corporation',
                    'Inc',
                    'S.A.',
                    'GMBH',
                    'BHD',
                    'SDN',
                    'Office',
                    'Rep.Office',
                    'Representative Office',
                    '(AG)',
                    '商社',
                    '商事',
                    '株式会社',
                    '办事处',
                    '代表处',
                    'BRANCH',
                    '厂',
                    '经营部'
                ], function(i, item){
                    if(~value.lastIndexOf(item.toLowerCase())){
                        result = true;
                        return false;
                    }
                });
                
                return result;
            }  
        },

        captcha: {
            msg: "验证码不正确",
            validate: function(value, value1){
                return value !== value1;    
            }    
        }
    });

    window.Common = Common;
})(this, jQuery);