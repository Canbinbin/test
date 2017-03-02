;;(function(){
	var Main={
			arr:[],
			init:function(){
				this.cacheElement();
				this.bindEvent();
			},
			cacheElement:function(){
				var that=this;
				$.each([".checkboxAll","#downAll","#upAll","#newList",".inputCheckbox"], function(i,item){
					that["$"+item.replace(/\.|\#/g,"")] = $(item);
				})
			},
			bindEvent:function(){
				var that=this;
				that.$checkboxAll.on("click",function(){
					if($(this).prop("checked")){
						that.selectedAll();
					}else{
						that.cancelAll();
					}
				});
				that.$downAll.on("click",function(){
					that.downCallback();
				});
				that.$upAll.on("click",function(){
					that.upCallback();
				});
				that.$inputCheckbox.on("click",function(){
					var str=$(this).val();
					if($(this).prop("checked")){
						that.arr.push(str)
					}else{
						that.arr.splice($.inArray(str,that.arr),1);
					};
					if(that.$newList.find(".inputCheckbox:checked").length>1){
						that.$downAll.removeClass("disabled");
						that.$upAll.removeClass("disabled");
					}else{
						that.$downAll.addClass("disabled");
						that.$upAll.addClass("disabled");
					}
					
				})
			},
			selectedAll:function(){
				var that=this;
				that.$newList.find('[type="checkbox"]').prop("checked",true);
				that.$downAll.removeClass("disabled");
				that.$upAll.removeClass("disabled");
				that.$newList.find(".inputCheckbox").each(function(){
					that.arr.push($(this).val());
				});
			},
			cancelAll:function(){
				var that=this;
				that.$newList.find('[type="checkbox"]').prop("checked",false);
				that.$downAll.addClass("disabled");
				that.$upAll.addClass("disabled");
				that.arr=[];
			},
			downCallback:function(){
				var that=this;
				var str=that.arr.join(",");
				window.location.href='/admin/news/setStatus?ids='+ (str) + '&statusValue=-1';
			},
			upCallback:function(){
				var that=this;
				var str=that.arr.join(",");
				window.location.href='/admin/news/setStatus?ids='+ (str) + '&statusValue=1';
				
			}
	}
	
	$(function(){
		Main.init();
	})
})();