$(function() {
	Main.init();
});
var Main = {
	init: function() {
		this._cacheElement();
		this._initEditor();
		this._bindEvent();
	},
	_cacheElement: function() {
		var that = this;
		$.each(["#pickImgCheckbox", "#subContentCheckbox", ".previewImage", "#imagePath", ".uploadPlus", "#file"], function(i, item) {
			that["$" + item.replace(/\.|\#/g, "")] = $(item);
		});
		return that;
	},
	_initEditor: function() {
		var that = this;
		var options = {
			autoHeightEnabled: false,
			autoFloatEnabled: false,
			disabledTableInTable: false,
			initialFrameWidth: 860,
			initialFrameHeight: 400
		};
		that._editor = UE.getEditor("editor", options);
		return that;
	},
	_bindEvent: function() {
		var that = this;
		$(document).on("change", ".upload-image-btn", function() {
			that._imageUpload();
		});

	},
	_initValidate: function(flag) {},
	_imageUpload: function() {
		var that = this,
			value = $.trim(that.$file.val()),
			imageType = ["jpg", "png", "gif", "jpeg"];
		if (value) {
			if (~$.inArray(value.split("\.").pop(), imageType)) {
				$.ajaxFileUpload({
					global: false,
					dataType: "json",
					secureuri: false,
					fileElementId: "file",
					url: "/admin/press/upload_image?date=" + new Date().getTime(),
					success: function(data) {
						if (data.status == 200) {
							that.$imagePath.val(data.data);
							that.$uploadPlus.hide();
							that.$previewImage.attr("src", data.data).show();
							layer.msg(data.msg, {
								time: 2000
							});
						}
					},
					error: function(data) {
						layer.msg(data.msg, {
							time: 2000
						});

					}
				});
			} else {
				layer.msg("上传图片格式不正确", {
					time: 1500
				});
			}
		}
	}
}
//报刊页面对应方法
var Issue = {
	init: function() {
			this._bindEvent();
	},
	_bindEvent: function() {
		_that = this;
		$("#submitList").on("click", function() {
			if (this.dataset.type == "add") _that._submitList();
			else if (this.dataset.type == "edit") _that._submitUpdate();
			else layer.msg("提交出错");
		});
		$("#type").val($("#typeSelect option:selected").val())
		$("#typeSelect").change(function(){
			$("#type").val($("#typeSelect option:selected").val());
		});
		$("#searchTypeSelect").change(function(){
			$("#searchType").val($("#searchTypeSelect option:selected").val());
		});
		if($("#searchType").val())
		{
			setSelectVal("searchTypeSelect", $("#searchType").val(),"","");
		}
	},
	_submitList: function() {
		showMsg("提交中", 500);
		//上传数据，成功后返回列表
		$.ajax({
			url: "/admin/press/issue_save",
			//保存数据
			dataType: "json",
			type: "POST",
			data: $("#myForm").serialize(),
			success: function(data) { //保存成功后返回的数据
				layer.msg(data.msg, {
					time: 2000
				});
				if (data.status == 200) {
					setTimeout(function() {
						location.href = "/admin/press/list_issue"
					}, 1000);
				}
			},
			error: function(data) {
				layer.msg(data.msg, {
					time: 2000
				});
			}
		})


	},
	_isAdd: function() {
		Issue._cleanReg();
		$("#reg").fadeIn(200);

	},
	_isEdit: function(_this) {
		showMsg("正在启动", 500);
		$.getJSON("/admin/press/issue_get", {
			"id": _this.dataset.id
		}, function(result) {
			if (result.status == 200) {
				data = result.data[0]
				var that = this;
				array = ["h2", "type", "issue", "pageCount", "publishDate", "previewImage", "imagePath", "id", "submitList"];
				$.each(["#myForm h2", "#myForm input[name='type']", "#myForm input[name='issue']", "#myForm input[name='pageCount']", "#myForm #publishDate", "#myForm .previewImage", "#myForm #imagePath", "#myForm input[name='id']", "#myForm #submitList"], function(i, item) {
					that["$" + array[i].replace(/\.|\#/g, "")] = $(item);
				});
				($(that["$h2"][0])).text("编辑");
				($(that["$type"][0])).val(data.type);
				setSelectVal("typeSelect", data.type,"","");
				($(that["$issue"][0])).val(data.issue);
				($(that["$pageCount"][0])).val(data.pageCount);
				($(that["$publishDate"][0])).val(formatDate(new Date(data.publishDate), "yyyy-MM-dd"));
				($(that["$imagePath"][0])).val(data.viewImg);
				($(that["$previewImage"][0])).attr("src", data.viewImg);
				($(that["$id"][0])).val(data.id);
				(that["$submitList"][0]).dataset.type = "edit";
				if (data.viewImg) {
					($(that["$previewImage"][0])).css("display", "inline");
					that["$previewImage"][0].parentNode.firstElementChild.style.display = "none";
				}
				$("#reg").fadeIn(200);
			} else {
				showMsg(result.msg, 1000)
			}
		});
	},
	_submitUpdate: function() {
		showMsg("提交中", 500);
		$.ajax({
			url: "/admin/press/issue_update",
			//保存数据
			dataType: "json",
			type: "POST",
			data: $("#myForm").serialize(),
			success: function(data) { //保存成功后返回的数据
				layer.msg(data.msg, {
					time: 2000
				});
				if (data.status == 200) {
					setTimeout(function() {
						location.href = "/admin/press/list_issue"
					}, 1000);
				}
			},
			error: function(data) {
				layer.msg(data.msg, {
					time: 2000
				});
			}
		})
	},
	_isDelete: function(_this) {
		layer.confirm('确定删除？', {
			icon: 5
		}, function() {
			$.getJSON("/admin/press/issue_delete", {
				"id": _this.dataset.id
			}, function(rs) {
				layer.msg(rs.msg, {
					time: 500
				}, function() {
					if (rs.status > 0) {
						location.reload();
					}
				});
			});
		});
	},
	_cleanReg: function() {
		var that = this;
		array = ["h2", "type", "issue", "pageCount", "publishDate", "previewImage", "imagePath", "id", "submitList"];
		$.each(["#myForm h2", "#myForm input[name='type']", "#myForm input[name='issue']", "#myForm input[name='pageCount']", "#myForm #publishDate", "#myForm .previewImage", "#myForm  #imagePath", "#myForm input[name='id']", "#myForm #submitList"], function(i, item) {
			that["$" + array[i].replace(/\.|\#/g, "")] = $(item);
		});
		($(that["$h2"][0])).text("添加");
		($(that["$type"][0])).val("");
		refreshSelect("typeSelect");
		($(that["$type"][0])).val($($("#typeSelect option:selected")[0]).val());
		($(that["$issue"][0])).val("");
		($(that["$pageCount"][0])).val("4");
		($(that["$publishDate"][0])).val("");
		($(that["$imagePath"][0])).val("");
		($(that["$previewImage"][0])).attr("src", "");
		($(that["$id"][0])).val(null);
		(that["$submitList"][0]).dataset.type = "add";
		($(that["$previewImage"][0])).css("display", "none");
		that["$previewImage"][0].parentNode.firstElementChild.style.display = "block";
	}
}
//版位页面对应方法
var Page = {
	init: function() {
		this._bindEvent();
	},
	_bindEvent: function() {
		_that = this;
		$("#submitList").on("click", function() {
			if (this.dataset.type == "add") _that._submitList();
			else if (this.dataset.type == "edit") _that._submitUpdate();
			else layer.msg("提交出错");
		});
		//表单
		$("#type").val($("#typeSelect option:selected").val())
		_that._refreshIssueSelect();
		$("#typeSelect").change(function(){
			$("#type").val($("#typeSelect option:selected").val());
			_that._refreshIssueSelect();
		});
		//搜索框
		$("#searchTypeSelect").change(function(){
			$("#searchType").val($("#searchTypeSelect option:selected").val());
		});
		if($("#searchType").val())
		{
			setSelectVal("searchTypeSelect", $("#searchType").val(),"","");
		}
		
	},
	_submitList: function() {
		showMsg("提交中", 500);
		//上传数据，成功后返回列表
	        $.ajax({
	            url: "/admin/press/page_save",
	            //保存数据
	            dataType: "json",
	            type: "POST",
	            data: $("#myForm").serialize(),
	            success: function(data) { //保存成功后返回的数据
	            	layer.msg(data.msg, {time: 2000});
	                if (data.status == 200) 
	                {
	                	setTimeout(function(){
	                		location.href="/admin/press/list_page"
	                	},1000);
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
	},
	_isAdd: function() {
		Page._cleanReg();
		$("#reg").fadeIn(200);
	},
	_isEdit: function(_this) {
		var _that = this;
		showMsg("正在启动", 500);
		$.getJSON("/admin/press/page_get", {
			"id": _this.dataset.id
		}, function(result) {
			if (result.status == 200) {
				data = result.data[0]
				var that = this;
				array = ["h2", "type","issue", "pageNum", "previewImage", "imagePath", "id", "submitList"];
				$.each(["#myForm h2","#myForm #type","#myForm #issue", "#myForm input[name='pageNum']", "#myForm .previewImage", "#myForm #imagePath", "#myForm input[name='id']", "#myForm #submitList"], function(i, item) {
					that["$" + array[i].replace(/\.|\#/g, "")] = $(item);
				});
				($(that["$h2"][0])).text("编辑");
				($(that["$type"][0])).val(data.type);
				setSelectVal("typeSelect", data.type,"","");
				_that._refreshIssueSelect();
				($(that["$issue"][0])).val(data.issue);
				setSelectVal("issueSelect", data.issue,"第","期");
				($(that["$pageNum"][0])).val(data.pageNum);
				setSelectVal("pageNumSelect", data.pageNum,"第","版");
				($(that["$imagePath"][0])).val(data.image);
				($(that["$previewImage"][0])).attr("src", data.image);
				($(that["$id"][0])).val(data.id);
				(that["$submitList"][0]).dataset.type = "edit";
				if (data.image) {
					($(that["$previewImage"][0])).css("display", "inline");
					that["$previewImage"][0].parentNode.firstElementChild.style.display = "none";
				}
				$("#reg").fadeIn(200);
			} else {
				showMsg(result.msg, 1000)
			}
		});
	},
	_submitUpdate: function() {
		showMsg("提交中", 500);
		$.ajax({
	            url: "/admin/press/page_update",
	            //保存数据
	            dataType: "json",
	            type: "POST",
	            data: $("#myForm").serialize(),
	            success: function(data) { //保存成功后返回的数据
	            	layer.msg(data.msg, {time: 2000});
	                if (data.status == 200) 
	                {
	                	setTimeout(function(){
	                		location.href="/admin/press/list_page"
	                	},1000);
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
	},
	_isDelete: function(_this) {
		layer.confirm('确定删除？', {
			icon: 5
		}, function() {
			$.getJSON("/admin/press/page_delete", {
				"id": _this.dataset.id
			}, function(rs) {
				layer.msg(rs.msg, {
					time: 500
				}, function() {
					if (rs.status > 0) {
						location.reload();
					}
				});
			});
		});
	},
	_cleanReg: function() {
		var that = this;
		array = ["h2", "issue", "pageNum", "previewImage", "imagePath", "id", "submitList"];
		$.each(["#myForm h2", "#myForm #issue", "#myForm input[name='pageNum']", "#myForm .previewImage", "#myForm #imagePath", "#myForm input[name='id']", "#myForm #submitList"], function(i, item) {
			that["$" + array[i].replace(/\.|\#/g, "")] = $(item);
		});
		refreshSelect("issueSelect");
		($(that["$issue"][0])).val($($("#issueSelect option:selected")[0]).val());
		refreshSelect("pageNumSelect");
		($(that["$pageNum"][0])).val($($("#pageNumSelect option:selected")[0]).val());
		($(that["$imagePath"][0])).val(null);
		($(that["$previewImage"][0])).attr("src", "");
		($(that["$id"][0])).val(null);
		(that["$submitList"][0]).dataset.type = "add";
		($(that["$previewImage"][0])).css("display", "none");
		that["$previewImage"][0].parentNode.firstElementChild.style.display = "block";
		$("#reg").fadeIn(200);
	},
	_refreshIssueSelect:function(){//刷新版页选择框
		$.ajax({
			async: false,
      		url: "/admin/press/issue_list_get",
            //保存数据
            dataType: "json",
            type:  "POST",
            data: {type:$("#type").val()},
            success: function(data) { //保存成功后返回的数据
                if (data.status == 200) 
                {
  					SelectHtml = "<optgroup>"              
  					$.each(data.data,function(){
                    	SelectHtml += "<option value='"+this.issue+"'>"+"第"+this.issue+"期";
                    });
                    SelectHtml += "</optgroup> ";
                    $("#issueSelect").html("");
                    $("#issueSelect").append(SelectHtml);
                    $("#issue").val($("#issueSelect option:selected").val());
                } 
            },
            error: function(data) {
                layer.msg(data.msg, {
                    time: 2000
                });
            }
        })
	}
}
//新闻页面对应方法
var News = {
		init: function() {
			this._bindEvent();
		},
		_bindEvent: function() {
			_that = this;
			$("#submitList").on("click", function() {
				if (this.dataset.type == "add") {_that._submitList();}
				else if (this.dataset.type == "edit") {_that._submitUpdate();}
				else layer.msg("提交出错");
			});
			$("#submitStay").on("click", function() {
				if (this.dataset.type == "add") {_that._submitStay();}
				else if (this.dataset.type == "edit") {_that._submitUpdate();}
				else layer.msg("提交出错");
			});
			$("#typeSelect").change(function(){
				$("#type").val($("#typeSelect option:selected").val());
				_that._refreshIssueSelect();
				_that._refreshPageSelect();
			});
			$("#issueSelect").change(function(){
		  		$("#issue").val($("#issueSelect option:selected").val());
		  		_that._refreshPageSelect();
		  	})
		  	$("#pageNumSelect").change(function(){
		  		$("#pageNum").val($("#pageNumSelect option:selected").val());
		  	});
		    issue = $("#issue").val(); 
		    pageNum = $("#pageNum").val();
			//如果type的值不为空，则为编辑
		    $("#type").val()&&setSelectVal("typeSelect",$("#type").val(),"","");
		    $("#type").val($("#typeSelect option:selected").val())
		    _that._refreshIssueSelect();//刷新期数选择框
		    //如果issue值不为空，则为编辑
		    issue?setSelectVal("issueSelect",issue,"第","期"):refreshSelect("issueSelect");
		    $("#issue").val($("#issueSelect option:selected").val())
		    _that._refreshPageSelect();//刷新版页选择框
		    //如果pageNum值不为空，则为编辑
		    pageNum&&setSelectVal("pageNumSelect",pageNum,"第","版");    
		  	$("#pageNum").val($("#pageNumSelect option:selected").val())
		},
		_submitList: function() {
			showMsg("提交中", 500);
	        $.ajax({
	        	async: false,
	            url: "/admin/press/news_save",
	            //保存数据
	            dataType: "json",
	            type: "POST",
	            data: $("#myForm").serialize(),
	            success: function(data) { //保存成功后返回的数据
	            	layer.msg(data.msg, {time: 2000});
	                if (data.status == 200) 
	                {
	                	setTimeout(function(){
	                		location.href="/admin/press/list_news"
	                	},1000);
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
		},
		_submitStay: function() {
			_that = this;
			showMsg("提交中", 500);
			//上传数据，成功后留在此页面
	        $.ajax({
	        	async: false,
	            url: "/admin/press/news_save",
	            //保存数据
	            dataType: "json",
	            type: "POST",
	            data: $("#myForm").serialize(),
	            success: function(data) { //保存成功后返回的数据
	            	layer.msg(data.msg, {time: 2000});
	                if (data.status == 200) 
	                {
	                    $("#title").val("");
	                    $("#position").val("");
	                    $("#keyword").val("");
	                    $("#editor").val("");
	                    $("#description").val("");
	                    $("#publishTime").val("");
	                    _that._cleanEdtorContent();
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
		},
		_submitUpdate: function() {
			showMsg("提交中", 500);
	        $.ajax({
	        	async: false,
	            url: "/admin/press/news_update",
	            //保存数据
	            dataType: "json",
	            type: "POST",
	            data: $("#myForm").serialize(),
	            success: function(data) { //保存成功后返回的数据
	            	layer.msg(data.msg, {time: 2000});
	                if (data.status == 200) 
	                {
	                	setTimeout(function(){
	                		location.href="/admin/press/list_news"
	                	},1000);
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
		},
		_isDelete: function(_this) {
			layer.confirm('确定删除？', {
				icon: 5
			}, function() {
				$.getJSON("/admin/press/news_delete", {
					"id": _this.dataset.id
				}, function(rs) {
					layer.msg(rs.msg, {
						time: 500
					}, function() {
						if (rs.status > 0) {
							location.reload();
						}
					});
				});
			});
		},
		_isEdit: function(_this) {
			location.href="/admin/press/news_add?id="+_this.dataset.id;
		},
		_cleanEdtorContent:function(){//清空富文本編輯器內容
			ueditIframe=document.getElementById("ueditor_0").contentWindow;  
			content=ueditIframe.document.body; 
			content.innerHTML = "";
		},
		_refreshIssueSelect:function(){//刷新期数选择框
			$.ajax({
				async: false,
	      		url: "/admin/press/issue_list_get",
	            //保存数据
	            dataType: "json",
	            type:  "POST",
	            data: {type:$("#type").val()},
	            success: function(data) { //保存成功后返回的数据
	                if (data.status == 200) 
	                {
	  					SelectHtml = "<optgroup>"              
	  					$.each(data.data,function(){
	                    	SelectHtml += "<option value='"+this.issue+"'>"+"第"+this.issue+"期";
	                    });
	                    SelectHtml += "</optgroup> ";
	                    $("#issueSelect").html("");
	                    $("#issueSelect").append(SelectHtml);
	                    $("#issue").val($("#issueSelect option:selected").val());
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
		},
		_refreshPageSelect:function(){//刷新版页选择框
			$.ajax({
				async: false,
	      		url: "/admin/press/page_list_get",
	            //保存数据
	            dataType: "json",
	            type:  "POST",
	            data: {issue:$("#issue").val(),type:$("#type").val()},
	            success: function(data) { //保存成功后返回的数据
	                if (data.status == 200) 
	                {
	  					pSelectHtml = "<optgroup>"              
	  					$.each(data.data,function(){
	                    	pSelectHtml += "<option value='"+this.pageNum+"'>"+"第"+this.pageNum+"版";
	                    });
	                    pSelectHtml += "</optgroup> ";
	                    $("#pageNumSelect").html("");
	                    $("#pageNumSelect").append(pSelectHtml);
	                    $("#pageNum").val($("#pageNumSelect option:selected").val());
	                } 
	            },
	            error: function(data) {
	                layer.msg(data.msg, {
	                    time: 2000
	                });
	            }
	        })
		}
}
//改變搜索框值並提交表單
function page(n,s){
    $("#pageNo").val(n);
    $("#pageSize").val(s);
    $("#searchForm").submit();
    return false;
}
//刷新选择框
function refreshSelect(id) {
	$($("#select2-" + id + "-container")[0]).text($($("#" + id).find("option:first")[0]).text());
	$("#" + id).find("option:first").attr("selected", true);
}
//改变选择框值

function setSelectVal(id, value,ls,rs) {
	$($("#select2-" + id + "-container")[0]).text(ls+value+rs);
	$("#"+id).find("option[value='" + value + "']").attr("selected", true);
}

//弹出提示信息

function showMsg(msg, time) {
	layer.msg(msg, {
		time: time
	})
}
//格式化日期

function formatDate(date, format) {
	var paddNum = function(num) {
			num += "";
			return num.replace(/^(\d)$/, "0$1");
		}
		//指定格式字符  
	var cfg = {
		yyyy: date.getFullYear() //年 : 4位  
		,
		yy: date.getFullYear().toString().substring(2) //年 : 2位  
		,
		M: date.getMonth() + 1 //月 : 如果1位的时候不补0  
		,
		MM: paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0  
		,
		d: date.getDate() //日 : 如果1位的时候不补0  
		,
		dd: paddNum(date.getDate()) //日 : 如果1位的时候补0  
		,
		hh: date.getHours() //时  
		,
		mm: date.getMinutes() //分  
		,
		ss: date.getSeconds() //秒  
	}
	format || (format = "yyyy-MM-dd hh:mm:ss");
	return format.replace(/([a-z])(\1)*/ig, function(m) {
		return cfg[m];
	});
}