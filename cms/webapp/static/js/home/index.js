$.fn.scrollcontent=function(interval){
    var $this=$(this);
    var box=$this.closest("div")
    var m=box.height();
    var n=$this.height();
    if(n>=m){
        $this.append($this.html());
        var i=0;
        var timer;
        var start=function(){
            timer=setInterval(function(){
                box.scrollTop(i);
                i===n ? i=0 : i++;
            },interval);
        }
        var stop=function(){
            clearInterval(timer);
        }
        start();
        box.on("mouseover",stop).on("mouseout",start);
    }
};



(function(window,$){
	var arr=[];
	var arr2=[];
	var Main={
			init:function(){
				this.cacheElement();
				this.bindEvent();
			},
			cacheElement:function(){
				var that=this;
				$.each([
				        ".select-box",
				        ".select-content",
				        ".select-list",
				        "#publishBtn",
				        "#myform",
				        "#warehouseId",
				        "#cateId"
				        ], function(i,item){
					that["$"+$.camelCase(item.replace(/\.|\#/,""))]=$(item);
				})
			},
			bindEvent:function(){
				var that=this;
			
				that.$selectBox.eq(0).on("mouseover",function(e){
					$(this).find(".select-list").show();
				
				}).on("mouseout",function(){
					$(this).find(".select-list").hide();
				});
				that.$selectBox.eq(0).find("li").on("click",function(e){
					var id=$(this).attr("data-value");
					var str=$(this).find("span").html();
					that.$selectContent.eq(0).val(str);
					that.$selectList.hide();
					that.$cateId.val(id);
					
				});
				that.$selectBox.eq(1).on("mouseover",function(e){
					$(this).find(".select-list").show();
					
				}).on("mouseout",function(){
					$(this).find(".select-list").hide();
				});
		
				that.$selectList.eq(1).find("input").on("click",function(e){
					var id=$(this).val();
					var str=$(this).siblings("span").html();
					if($(this).prop("checked")){
						if(that.$selectList.eq(1).find("input[type='checkbox']:checked").length >2){
							layer.msg("交割地最多只能选择两个", {
							    time: 1000 
							});
							$(this).prop("checked",false);
							return false;
						}else{
							arr.push(str);
							arr2.push(id);
						}
					}else{
						arr.splice($.inArray(str,arr),1);
						arr2.splice($.inArray(str,arr2),1);
					};
					
					that.$selectContent.eq(1).val(arr.join(","));
					that.$warehouseId.val(arr2.join(","));
					
				});
				that.$publishBtn.on("click",function(){
					that.submitCallback();
				});
			},
			submitCallback:function(){
				var that=this;
				var price=$.trim($("#myform").find('input[name="price"]').val());
				var number=$.trim($("#myform").find('input[name="number"]').val());
				var cate=$.trim($("#myform").find('input[name="cate"]').val());
				var warehouse=$.trim($("#myform").find('input[name="warehouse"]').val());
				var deliveryTimeStart=$.trim($("#myform").find('input[name="deliveryTimeStart"]').val());
				
				if(cate==''){
					layer.msg('请选择品类',{time:1400});
				}else if(warehouse==''){
					layer.msg('请选择交割库',{time:1400});
				}else if(deliveryTimeStart==''){
					layer.msg('请选择交割时间',{time:1400});
				}else if(price==''){
					layer.msg('请输入价格',{time:1400});
				}else if(price<1000 || price>99999){
					layer.msg('请输入1000-99999的价格',{time:1400});
				}else if(number==''){
					layer.msg('请输入数量',{time:1400});
				}else if(number<10 || number>99999){
					layer.msg('请输入10-99999的数量',{time:1400});
				}else{
					var json=that.$myform.serialize();
					$.ajax({
						url:'/purchase/release',
						type:"post",
						dataType:'json',
						data:json,
						success:function(data){
							if(data.status==-1){
								loginBoxShow.init();
							}else if(data.status==-2){
								layer.msg(data.msg,{time:1400});
							}else if(data.status==1){
								layer.msg(data.msg,{time:1400},function(){
									$("#myform").find("input").val("");
									$("input[id^=warehouse_id_]").attr("checked",false);
									$("#reservation").val("");
								});
								
							}
						}
					})
				}	
			}
	};
	
	$(function(){
		Main.init();
	})
	
})(this,jQuery,undefined)


$(function() {
	$(".buy").find(".list_data").scrollcontent(100);
    /*首页轮播*/
    function LeftAuto() {
        this.w = 0;
        this.len = 0;
        this.times = null;
        this.opts = null;
        this.iNow = 0;
    };
    LeftAuto.setings = {
        'parents': '',
        'child': '',
        'loop': true,
        'time': 3000,
        'prev': '.prev',
        'next': '.next',
        'icon': '.icon'
    }
    $.extend(LeftAuto.prototype, {
        'init': function(options) {
            var that = this;
            this.opts = $.extend({}, LeftAuto.setings, options);
            if (that.opts.loop) {
                $(that.opts.child).children().css({
                    'float': 'left'
                });
                that.leftloop();
            };

        },
        'leftloop': function() {
            var that = this;
            that.count();
            that.loop();
            that.nextEvent();
            that.prevEvent();
            that.iconEvent();
            $(this.opts.parents).on('mouseover', function() {
                that.stop();
            }).on('mouseout', function() {
                that.start();
            });
        },
        'prevEvent': function() {
            var that = this;
            $(this.opts.parents).find(that.opts.prev).on('click', function() {
                that.iNow--;
                that.move(that.iNow);
            });
        },
        'iconEvent': function() {
            var that = this;
            $(that.opts.parents).find(that.opts.icon).on('mouseover', function() {
                that.iNow = $(this).index();
                that.move(that.iNow);
            });
        },
        'nextEvent': function() {
            var that = this;
            $(this.opts.parents).find(that.opts.next).on('click', function() {
                that.iNow++;
                that.move(that.iNow);
            });
        },
        'loop': function() {
            var that = this;
            this.times = setInterval(function() {
                that.iNow++;
                that.move(that.iNow);
            }, that.opts.time)
        },
        'move': function(num) {
            if (num > this.len - 1) {
                $(this.opts.parents).find(this.opts.child).css({
                    'left': 0
                });
                num = 1;
                this.iNow = num;

            };
            if (num < 0) {
                $(this.opts.parents).find(this.opts.child).css({
                    'left': -((this.len - 1) * this.w)
                });
                num = this.len - 2;
                this.iNow = num;
            };
            if (num > this.len - 2) {
                
                $(this.opts.parents).find(this.opts.icon).removeClass('on').eq(0).addClass('on');
            } else {
                $(this.opts.parents).find(this.opts.icon).removeClass('on').eq(num).addClass('on');
            };

            $(this.opts.parents).find(this.opts.child).stop().animate({
                'left': -(num * this.w)
            }, 600);
        },
        'stop': function() {
            clearInterval(this.times);
        },
        'start': function() {
            this.loop();
        },
        'count': function() {
            this.w = $(this.opts.parents).width();
            var cloneObj = $(this.opts.parents).find(this.opts.child).children().eq(0).clone(true);
            $(this.opts.parents).find(this.opts.child).append(cloneObj);
            this.len = $(this.opts.parents).find(this.opts.child).children().length;
            $(this.opts.parents).find(this.opts.child).css({
                'width': this.len * this.w
            });
        }
    });

    var l1 = new LeftAuto();
    l1.init({
        'parents': '#box',
        'child': '.ad_img',
        'loop': true,
        'time': 3000,
        'icon': '.icon'
    });

    $(".popular_products .duan").last().css('border-bottom',0);
    $(".more_type .duan3").last().css('border-bottom',0);
    $(".duan2").mouseover(function(){
        $(this).css({background:'#eee'}).find(".more_type").show();
    }).mouseout(function(){
         $(this).css({background:''}).find(".more_type").hide();
    });



    /*现货资源显示切换*/
    $(".xhzy_list li").mouseover(function(){
        $(this).find(".float_div").show();
        $(this).css('z-index','9999');
    }).mouseout(function(){
          $(this).find(".float_div").hide();
          $(this).css('z-index','0');
    });

    $(document).on("mousemove",".list_data li",function(e){
        
            var e=e || window.event;
            var x=e.clientX;
            var y=e.clientY;
            var t=$(document).scrollTop()
            var str=$(this).find(".sp2").html();
            $(".message_tip").html(str).show().css({'left':x+20,'top':y+t-25});
    }).on("mouseout",".list_data li",function(){
         $(".message_tip").hide();
    });

    setInterval(function(){
        window.location.reload(true);
    },600000);
    

    $('#reservation').daterangepicker(null,function(start,end ) {
    	function formatDate(date){
    		var month = date. getMonth() + 1,
    			year = date.getFullYear(),
    			day = date.getDate();
    		return [year, month < 10 ? ("0" + month) : month, day < 10 ? ("0" + day) : day].join("-");
    	}
    	var startTime=(formatDate(start._d));
    	var endTime=(formatDate(end._d));
    	$("#startTime").val(startTime);
    	$("#endTime").val(endTime);
    	$("#reservation").val(startTime+'-'+endTime);
    });
   
    $(".daterangepicker").on("mouseleave",function(){
		  $(this).hide();
	});
   
	var linkman = $.getcookie("linkman");
	if (linkman != "" && linkman != "null" && linkman != null) {
		$("#js-login-info").html('尊敬的 '+ linkman +'<a href="/member/logout">【退出】</a>  欢迎来到找化工网!');
	}else{
		$("#js-login-info").html('您好，欢迎来到找化工网!【<a href="/user/login">会员登录</a> | <a href="/user/register">免费注册</a>】');
	}
});