$(function(){
	    $("#myform").validate({
	    	rules:{
	    		companyName:"required",
	    		xls:"required"
	    	},
	    	messages:{
	    		companyName:"请填写公司名",
	    		xls:"请选择报价单"
	    	},
	    	submitHandler:function(form){
	    		var url="/admin/product/product_batch_add";
	    		var mid=$.trim($("[name='mid']").val());
	    		var contactDisplay = $.trim($("[name='contactDisplay']:checked").val());
	    		var paymentType=$.trim($("[name='paymentType']:checked").val());
	    		var deliveryType=$.trim($("[name='deliveryType']:checked").val());
	    		var receiptType=$.trim($("[name='receiptType']:checked").val());
	    		var companyId=$.trim($("[name='companyId']").val());
	    		var xlsxType=['xls','xlsx'];
	    		var value=$("#xls").val();
	    		if(mid){
		    		url+='?mid='+mid;
		    	}
		    	if(contactDisplay){
		    		url+='&contactDisplay='+contactDisplay;
		    	}
		    	if(paymentType){
		    		url+='&paymentType='+paymentType;
		    	}
		    	if(deliveryType){
		    		url+='&deliveryType='+deliveryType;
		    	}
		    	if(receiptType){
		    		url+='&receiptType='+receiptType;
		    	}
		    	if(companyId){
		    		url += '&companyId=' + companyId;
		    	}
		    	if(~$.inArray(value.split("\.").pop(), xlsxType)){
		    		$.ajaxFileUpload({
    		            url: url+'&random='+new Date().getTime(),
    		            global: false, 
                        dataType: "json",                           
                        secureuri: false,
    		            fileElementId: "xls",
    		            success: function(rs){
    		                if(rs.status==-100)
    		                {
    		                    show_dialog_login();
    		                }
    		                else if(rs.status<0)
    		                {
    		                	layer.msg(rs.msg);
    		                }
    		                else
    		                {
    		                	layer.msg(rs.msg);
    		                
    		                }
    		            }
		    		});
		    		 
		    	}else{
		    		 layer.msg('请上传xls,xlsx格式文件');
		    	};
	        } 
	    });
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
							"<a href=\"#\">{{{company}}}({{{linkman}}}|{{{mobile}}})</a>",
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
					$("input[name='mobile']").attr('value',value.mobile);
				}else{
					$("input[name='mobile']").attr('value','');
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
				}
				if(value.mid!=''){
					$("input[name='mid']").attr('value',value.mid);
				}else{
					$("input[name='mid']").attr('value','');
				}
				if(value.companyId !=''){
					$("input[name='companyId']").attr('value',value.companyId);
				}else{
					$("input[name='companyId']").attr('value','');
				}
				
			}		
		});
	});