$(function(){
	//地址选择
    $(".dwt").dwt({"url":"/admin/common/get_region"});
    $("#cate_id").on("change",function(){
    	var cate_id=$("#cate_id").val();
    	if(cate_id>0){
    		$("#cate").attr("value",$("#cate_id").find("option:selected").text());
    	}else{
    		$("#cate").attr("value","");
    	}
    });
	$("#myform").validate({
		errorPlacement:function(error,element) {
			if (element.attr("name") == "number" || element.attr("name") == "price" || element.attr("name")=="deliveryTimeStart" ||
					element.attr("name")=="deliveryTimeEnd" || element.attr("name")=="warehouse"){
				element.closest(".input_group").append(error);
			}else{
				element.after(error);
			} 	
		},
		submitHandler:function(form){
			$(form).find(".btn-submit").prop("disabled", true).val("提交中...");
			$.post('/admin/product/product_add',$(form).serialize(),function(rs){
				if(rs.status<0){
					layer.msg(rs.msg);
					$(form).find(".btn-submit").prop("disabled", false).val("提交");
				}
				else{
					layer.msg(rs.msg);
					setTimeout(function(){
						location.href='/admin/product/product_list';
					},1000);
					
				}
			},"json");
		},
		messages:{
			warehouse:{
				maxlength: $.validator.format("最多可以选择{0} 个交割库"),
				minlength: $.validator.format("最少要选择 {0} 个交割库")
			}
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
			if(value.mid !=''){
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




	//解析生成货物信息
	$("#recognizePicBtn").click(function(){
		var filePath = $(".preview-wrapper").attr("data-name");
		if(filePath == undefined){
			filePath = "";
		}
		
		var content = $("#pasteUploadInput").val();
		$.ajax({
			url:'/admin/product/ocr/imageAnalyze', //解析图片后台url
			type:'POST',         
			cache:false,
			dataType:'json',
			data:{fileToUpload:filePath,content:content},
			success:function(rs){
			/* 后台返回的数据格式,status = 1时成功， 否则失败
				{
					"msg": "",
					"data": [{
						"deliveryTimeStart": "2016-03-21",
						"number": "500",
						"cate": "苯乙烯",
						"deliveryTimeEnd": "2016-03-31",
						"cateId": 5,
						"warehouseList": ["主流库区","张家港-孚宝"],
						"warehouseIdList":[1,2],
						"price": "8450"
					},
					{
						"deliveryTimeStart": "2016-04-21",
						"number": "500",
						"cate": "苯乙烯",
						"deliveryTimeEnd": "2016-04-30",
						"cateId": 5,
						"warehouseList": ["主流库区","张家港-孚宝"],
						"warehouseIdList":[1,2],
						"price": "8450"
					},
					{
						"deliveryTimeStart": "2016-06-21",
						"number": "500",
						"cate": "苯乙烯",
						"deliveryTimeEnd": "2016-06-30",
						"cateId": 5,
						"warehouseList": ["主流库区","张家港-孚宝"],
						"warehouseIdList":[1,2],
						"price": "8450"
					}],
					"status": 1
				}
			*/
				if(rs.status == 1){
					$(".distinguish").show();
					$(".distinguish_no").hide();
				}
			},
			error:function(){
				
			}
		});
		
		
	})


	//选择交割库弹窗
	$(".choose_jgk").click(function(){
		$(".choose_jgk_mask_d,.choose_jgk_mask").show();
	});
	$(".choose_jgk_mask .close").click(function(){
		$(".choose_jgk_mask_d,.choose_jgk_mask").hide();
	});
	$(".choose_jgk_mask .qd").click(function(){
		var warehouse=$(".choose_jgk_mask").find("input[name='warehouse']");
		var value="";
		for (var i = 0; i < warehouse.length; i++) {
			if(warehouse[i].checked){
				value+=warehouse[i].value+"，";
			}
		};
		if (value=="") {
			alert("请至少选择一个交割库！")
		}else{
			$(".choose_jgk").val(value);
			$(".choose_jgk_mask_d,.choose_jgk_mask").hide();
		};
	});

	//不保存识别货物
	$(".no_save").click(function(){
		$(".distinguish").hide();
		$(".distinguish_no").show();
	});

	//上传图片
	$("#fileToUpload").on("change", function(e){
		var filePath = $("#fileToUpload").val();
        var imageType = ["png", "jpeg", "jpg", "gif", "bmp"];
	     if(isImageType(filePath)){
	         $.ajaxFileUpload({
	             global: false,
	             secureuri: false,
	             dataType: "json",
	             url: "/admin/product/ocr/imageUpload", //上传图片后台url
	             fileElementId: "fileToUpload",
	             success: function (data){
	            	 if(data.status == 1){
	            		 $(".preview-wrapper").html('<img src="' + data.data.fileName + '">');
	            		 $(".preview-wrapper").attr("data-name",data.data.fileName);
	            	 }else{
	            		 layer.msg(data.data.msg);
	            	 }
	             },
	             error: function (){
	                 layer.msg("上传图片失败，请稍后重试");
	             }
	         });                    
	     }else{
	         layer.msg("请上传格式为" + imageType.join("/") + "的图片", "warning");
	     }
    })
});


function isImageType(filePath){
	var lastIndexOf;
    filePath = (filePath || "");
    lastIndexOf = filePath.lastIndexOf(".");
    var fileType =  ~lastIndexOf ? (filePath.substring(lastIndexOf + 1)).toLowerCase() : "";
    var imageType = ["png", "jpeg", "jpg", "gif", "bmp"];
	return ~imageType.join(",").lastIndexOf(fileType);
}

	