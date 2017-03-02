;;(function(window, $, undefined){
	var Common = window.Common || {};
	Common.util = {
		validateFiled: function(){
			var namespace = ".commonValidateFiled";
			$(document)
				.off(namespace)
	            .on("keydown" + namespace, "[data-validate=\"number\"]", function(e){
	                var name = this.name,
	                    keyCode = e.keyCode;
	                if(e.altKey || e.ctrlKey || e.shiftKey || ({"8":1, "9":1, "37":1, "38":1, "39":1, "40":1, "110": 1, "190": 1})[keyCode]
	                        || (keyCode >= 48 && keyCode <= 57) 
	                            || (keyCode >= 96 && keyCode <= 105)){
	                    //数字或者backspace
	                }else{
	                    e.preventDefault();
	                }
	            });				
		},
		autoMenu:function(fid,sid,url){
			$(".nav_ul li").each(function(){
				if($(this).data("id")==fid){
					switchNav($(this))
				}
			});
			$(".menu li").each(function(){
				if($(this).data("id")==sid){
					switchMenu($(this),url);
				}
			});
		},
		switchNav:function(o){
			var index=o.index();
			o.addClass('active').siblings().removeClass('active');
			$(".menu").eq(index).show().siblings().hide();
		},
		switchMenu:function(o,url){
			var index=o.index();
			o.find("a").addClass('active');
			o.siblings().find("a").removeClass('active');
			$("#my_iframe").attr("src",url==undefined ? o.eq(index).find("a").attr("href") : url);
		},
		flashMessage:function(){
			if($("#flash_message").length>0 && $("#flash_message").val()!=""){
				layer.msg($("#flash_message").val());
			}
		}
	};
	window.Common = Common;
})(this, jQuery);

$(function(){
	Common.util.flashMessage();
});