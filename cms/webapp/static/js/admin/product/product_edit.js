$(function(){
	
		var companyAutoComplete = AutoCompleteSelector({
			target: "#companyName",
			remote: {
				url: "/admin/member/get_common_member",
				requestType: "post",
				paramName: "companyName",
				parse: function(result){
					var data;
					if(typeof(result) === "string"){
						result = $.parseJSON(result);
					}
					if(!result.error){
						data = result;
					}
					return {data: data};
				},
				tpl: [
					"{{#list}}",
						"<li data-value=\"{{{id}}}\">",
							"<a href=\"#\">{{{company}}}|{{{linkman}}}|{{{mobile}}}</a>",
						"</li>",
					"{{/list}}"
				].join("")
			},
			selectedCallback: function(data){
				var value = companyAutoComplete.getCacheDataById(data.value);
				var result="";
				if(value.company!=''){
					result=value.company;
				}
				if(value.linkman!=''){
					result+='&nbsp;<span style="font-weight:bold;color:red;">|</span>&nbsp;'+value.linkman;
				}
				if(value.mobile!=''){
					result+='&nbsp;<span style="font-weight:bold;color:red;">|</span>&nbsp;'+value.mobile;
					$("input[name='mid']").attr('value',value.mid);
					$("input[name='companyId']").attr('value',value.companyId);
				}else{
					$("input[name='mid']").attr('value','');
					$("input[name='companyId']").attr('value','');
				};
				if(value.main_products!=''){
					$("#main_products").html(value.main_products);
				}else{
					$("#main_products").html('暂无信息');
				};
				if(result!=''){
					$("#result").html(result);
				}else{
					$("#result").html('暂无信息');
				};
				
				loop_ajax(value);
			}		
		});
	});
function loop_ajax(value){
	$.ajax({
		url:'/admin/product/get_goods_list?mid='+value.mid + "&companyId=" + value.companyId, //后台处理程序
		type:'GET',         
		cache:false,
		dataType:'json',  
		success:function(rs){
			if(rs.status != 1){
				$("#goods_list").html("<span style='margin:60px;font-weight:bold;'>"+rs.msg+"</span>");	
			}else{
				var htm="<div class='gl_set'>"
					+"<span><input type='checkbox' class='select_checked'/>全选</span>"
        			+"<a href='javascript:void(0);' class='goods_status' data-val='1'>上架</a>"
        			+"<a href='javascript:void(0);' class='goods_status' data-val='-1'>下架</a>"
        			+"<a href='javascript:void(0);' class='goods_delete'>批量删除</a>"
        			+"<a href='javascript:void(0);' class='goods_edit'>保存更新</a></div>"
        			+"<table class='gl_list'><thead><tr><td>选择</td><td>品种</td><!--<td>厂家</td>--><td>数量（吨）</td><td>价格（元/吨）</td><!--<td>交货地</td>--><td>交割库</td>"
                	+"<td>状态</td><td>更新时间</td><td>操作</td></tr></thead><tbody>";
                	var data = rs.data;
                	for(var j in data){
						var st=data[j]['status'];
						var status=st>0?'上架':'下架';
						htm+="<tr>"
							+"<td><input type='checkbox' name='pid[]' value="+rs.data[j]['id']+" /></td>"
	                        +"<td>"+data[j]['cate']+"</td>"
	                        +"<!--<td>"+data[j]['manufacturer']+"</td>-->"
	                        +'<td><span class="td_text">'+data[j]['number']+"</span><input type='text' maxlength='5' value='"+data[j]['number']+"' name='number[]' class='td_inp'/></td>"
	                        +'<td><span class="td_text2">'+data[j]['price']+"</span><input type='text' maxlength='5' value='"+data[j]['price']+"' name='price[]' class='td_inp2'/></td>"
	                        +"<!--<td>"+data[j]['city']+"</td>-->"
	                        +"<td>"+data[j]['warehouse']+"</td>"
	                        +"<td><span class='color1'>"+status+"</span></td>"
	                        +"<td>"+data[j]['modifyTime']+"</td>"
	                        +"<td><a class='color1 update' href='javascript:void(0)'>编辑</a></td>"
						+"</tr>";	
					}
				htm+="</tbody></table><div class='gl_set'><span><input type='checkbox' class='select_checked'/>全选</span>"
	                +"<a href='javascript:void(0);' class='goods_status' data-val='1'>上架</a>"
	                +"<a href='javascript:void(0);' class='goods_status' data-val='-1'>下架</a>"
	                +"<a href='javascript:void(0);' class='goods_delete'>批量删除</a>"
	                +"<a href='javascript:void(0);' class='goods_edit'>保存更新</a></div>";
				$("#goods_list").html(htm);	
			} 
			Main.init(value);      
			return false;				
		}
	});	

};
(function(window, $, undefined){
	var Main=window.Main||{};
	var Main={
		'price':[],
		'num':[],
		'flag':true,
		'value':{},
		'init':function(value){
			this.value=value;
			this.bindEvent().save();
		},
		'save':function(){
			var that=this;
			$(".td_text2").each(function(){
				that.price.push($(this).html());
			});
			$(".td_text").each(function(){
				that.num.push($(this).html());
			});
			return this;
		},
		'bindEvent':function(){
			var that=this;
			$(document)
				.on("click", ".select_checked", function(){
					that.selectedCallback(this);
				})
				.on("click", ".update", function(){
					that.updateCallback(this);
				})
				.on("click",".gl_list input[type=\"checkbox\"]",function(){
					that.checkCallback(this);
				})
				.on("keyup",".td_inp",function(){
					that.inputCallback(this);
				})
				.on("keyup",".td_inp2",function(){
					that.inputCallback2(this);
				})
				.on('blur',".td_inp",function(){
					that.blurCallback(this);
				})
				.on("blur",".td_inp2",function(){
					that.blurCallback2(this);
				})
				.on("click",'.goods_status',function(){
					that.goodsStatus(this);
				})
				.on("click",".goods_delete",function(){
					that.goodsDelete(this);
				})
				.on("click",".goods_edit",function(){
					that.goodsSave(this);
				});
			return this;
		},
		'selectedCallback':function(obj){
			var that=this;
			if($(obj).prop("checked")){
				$('input[type="checkbox"]').prop('checked',true);
				$(".td_text2").hide();
				$(".td_text").hide();
				$(".td_inp").show();
				$(".td_inp2").show();
			}else{
				$('input[type="checkbox"]').prop('checked',false);
				$(".td_inp").each(function(i){
					var str=$(this).val();
					if(str<=0){
						$(".gl_list").find('input[type="checkbox"]').eq(i).prop("checked",true);
					}else{
						$(this).hide().siblings(".td_text").html(str).show();
						if(str!=that.num[i]){
							$(".gl_list .td_text").eq(i).css('color','red');
						}else{
							$(".gl_list .td_text").eq(i).css('color','#000');
						}
					}
				});
				$(".td_inp2").each(function(i){
					var str=$(this).val();
					if(str<1000 || str>99999){
						$(".gl_list").find('input[type="checkbox"]').eq(i).prop("checked",true);
					}else{
						$(this).hide().siblings(".td_text2").html(str).show();
						if(str!=that.price[i]){
							$(".gl_list .td_text2").eq(i).css('color','red');
						}else{
							$(".gl_list .td_text2").eq(i).css('color','#000');
						}
					}
				});

			}
		},
		'updateCallback':function(obj){
			var parentElement=$(obj).closest("tr");
			parentElement.find(".td_text").hide();
			parentElement.find(".td_text2").hide();
			parentElement.find(".td_inp").show();
			parentElement.find(".td_inp2").show();
			parentElement.find("input[type=\"checkbox\"]").prop('checked',true);
		},
		'checkCallback':function(obj){
			var parentElement=$(obj).closest("tr");
			var idx=$(".gl_list input[type=\"checkbox\"]").index($(obj));
			var val1=parentElement.find(".td_inp").val();
			var val2=parentElement.find(".td_inp2").val();
			if($(obj).prop('checked')){
				parentElement.find(".td_text").hide();
				parentElement.find(".td_text2").hide();
				parentElement.find(".td_inp").show();
				parentElement.find(".td_inp2").show();
			}else{
				
				if(val1 != this.num[idx]){
					parentElement.find(".td_text").html(val1).css('color','red');
				}else{
					parentElement.find(".td_text").html(val1).css('color','#000');
				}
				if(val2 != this.price[idx]){        
					parentElement.find(".td_text2").html(val2).css('color','red');
				}else{
					parentElement.find(".td_text2").html(val2).css('color','#000');
				}
				if(val1<10){
					layer.msg('请输入10-99999的数量');
					$(obj).prop("checked",true);
					return false;
				}else if(val2<1000 || val2>99999){
					layer.msg('请输入1000-99999的价格');
					$(obj).prop("checked",true);
					return false;
				}else{
					parentElement.find(".td_text").show();
					parentElement.find(".td_text2").show();
					parentElement.find(".td_inp").hide();
					parentElement.find(".td_inp2").hide();
				}
				
			}
		},
		'inputCallback':function(obj){
			var _this=$(obj);
			var idx=$(".td_inp").index(_this);
			var val=_this.val();
			_this.val(val.replace(/[^\d\.]/g,''));
			if(val != this.num[idx]){
				_this.css('color','red');
			}else{
				_this.css('color','#000');
			};
		},
		'inputCallback2':function(obj){
			var _this=$(obj);
			var idx=$(".td_inp2").index(_this);
			var val=_this.val();
			_this.val(val.replace(/[^\d\.]/g,''));
			if(val != this.price[idx]){
				_this.css('color','red');
			}else{
				_this.css('color','#000');
			};
		},
		'blurCallback':function(obj){
			if($(obj).val()<10){
				layer.msg('请输入10-99999的数量');
			};
		},
		"blurCallback2":function(obj){
			var val=$(obj).val();
			if(val<1000 || val>99999){
				layer.msg('请输入1000-99999的价格');
			};
		},
		'isFlag':function(){
			var that=this;
			var _this=$(".td_inp2");
			var _this2=$(".td_inp");
			var len=$(".td_inp2").length;
			for(var i=0;i<len;i++){
				if( _this.eq(i).val()<1000 || _this.eq(i).val() >99999 || _this2.eq(i).val()<10){
					that.flag=false;
					break;
				}else{
					that.flag=true;
				}
			}
		
			 return that.flag;
		},
		'goodsStatus':function(obj){
			var that=this;
			var _this=$(obj);
			var isFlag=that.isFlag();
			var obj=$(".gl_list").find("input[type=\"checkbox\"]:checked");
			if(obj.length<1){
				layer.msg('请选择货物');
				return false;
			}else{
				if(!isFlag){
					layer.msg('请输入1000-99999的价格和请输入10-99999的数量');
					return false;
				};
				var pid='';
				var arr=[];
				var status=_this.attr('data-val');
				for(var i=0;i<obj.length;i++){
					arr.push(obj.eq(i).val());
				}
				pid=arr.join(",");
				$.ajax({
				'url':'/admin/product/product_set_status?pid='+pid+"&status="+status+"&mid="+$("input[name='mid']").val(),
				'type':'get',
				'dataType':'json',
					'success':function(data){
						if(data.status>0){
							layer.msg(data.msg);
							if(status==1){
								for(var i=0;i<obj.length;i++){
									obj.eq(i).closest("tr").find("td").eq(7).html("上架").css('color','#229922');
								}
							}else if(status==-1){
								for(var i=0;i<obj.length;i++){
									obj.eq(i).closest("tr").find("td").eq(7).html("下架").css('color','#FD8833');
								}
							}	
						}else{
							layer.msg(data.msg);
						}
					}
				})
			}
		},
		'goodsDelete':function(obj){
			var that=this;
			var pid='';
			var arr=[];
			var obj=$(".gl_list").find("input[type=\"checkbox\"]:checked");
			for(var i=0;i<obj.length;i++){
				arr.push(obj.eq(i).val());
			}
			pid=arr.join(",");
			if(obj.length<1){
				layer.msg('请选择货物');
				return false;
			}else{
				$.ajax({
					'url':'/admin/product/product_del?id='+pid,
					'type':'get',
					'dataType':'json',
					'success':function(data){
						if(data.status>0){
							loop_ajax(that.value);
							layer.msg(data.msg);
						}else{
							layer.msg(data.msg);
						}
					}
				})
			}
			
		},
		'goodsSave':function(o){
		
			var that=this;
			var pid='';
			var arr=[];
			var arr2=[];
			var arr3=[];
			var isFlag=that.isFlag();
			var obj=$(".gl_list").find("input[type=\"checkbox\"]:checked");
			if(obj.length<1){
				layer.msg('请选择货物');
				return false;
			}
			if(!isFlag){
				layer.msg('请输入1000-99999的价格和请输入10-99999的数量');
				return false;
			};
			for(var i=0;i<obj.length;i++){
				arr.push(obj.eq(i).val());
				arr2.push(obj.eq(i).closest("tr").find(".td_inp").val());
				arr3.push(obj.eq(i).closest("tr").find(".td_inp2").val());
				obj.eq(i).closest("tr").find(".td_inp").hide();
				obj.eq(i).closest("tr").find(".td_inp2").hide();
				obj.eq(i).closest("tr").find(".td_text").html(arr2[i]).show();
				obj.eq(i).closest("tr").find(".td_text2").html(arr3[i]).show();
			}
			pid=arr.join(",");
			onsale_number=arr2.join(",");
			price=arr3.join(",");
			
			$.ajax({
				'url':'/admin/product/product_update?pid='+pid+"&number="+onsale_number+"&price="+price+"&mid="+$("input[name='mid']").val(),
				'type':'get',
				'dataType':'json',
				'success':function(data){
				
					if(data.status>0){
						
						layer.msg(data.msg);
					}else{
						layer.msg(data.msg);
					}
				}
			})
		}
	};
   window.Main=Main;
})(this, jQuery)