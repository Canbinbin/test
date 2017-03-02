;;(function(){
	var Main = {
        init: function(){
            var that = this;

            that._cacheParam()
                ._initValidate()
                ._bindEventListener();
        },	
        
        _cacheParam: function(){
            var that = this;

            $.each([
                "#myform",
                "#picPath",
                ".upload-plus",
                ".preview-image"
            ], function(i, item){
                that["_$" + $.camelCase(item.replace(/\#|\./g, ""))] = $(item);
            });
            if($.trim(that._$previewImage.attr("src"))){
                that._$previewImage.show();
                that._$uploadPlus.hide();    
            }else{
                that._$previewImage.hide();
                that._$uploadPlus.show();                
            }

            return that;
        },

        _initValidate: function(){
        	var that = this;

			that._$myform.validate({
				submitHandler: function(form){
                    if(!that._$picPath.val()){
                        layer.msg("请上传图片", {
                            time: 1500
                        });
                    }else{
                        $(form).find(".btn-submit").prop("disabled", true).val("提交中...");
                        form.submit();                          
                    }
				}				
			});

			return that;	
        },

        _bindEventListener: function(){
            var that = this,
                eventCallback = that._eventCallback;

            $(document)
                .on("change", ".upload-image-btn", function(e){
                    eventCallback.previewImage.call(that, e, $(this));            
                });

            return that;
        },

        _eventCallback: {
            //预览图片
            previewImage: function(e, $target){
                var file,
                    fileReader,
                    that = this,
                    value = $.trim($target.val()),
                    imageType = ["jpg","png","gif","jpeg"];

                if(value){
                    if(~$.inArray(value.split("\.").pop(),imageType)){
                        $.ajaxFileUpload({
                            global: false, 
                            dataType: "json",                           
                            secureuri: false,
                            fileElementId: "file",
                          	url: "/admin/web/upload_image?date=" + new Date().getTime(),
                            success: function (data, status){
                                var message;
                                if(data.status == 200){
                                    message = "上传成功";
                                    that._$picPath.val(data.data);
                                    that._$uploadPlus.hide();
                                    that._$previewImage.attr("src", data.data).show();
                                }else{
                                    message = data.msg;
                                }
                                layer.msg(message, {
                                    time: 2000
                                });
                            },
                            error: function (data, status, e){ 
                                layer.msg("上传失败", {
                                    time: 2000
                                });                                
                            }
                        });                           
                    }else{                   
                        layer.msg("上传图片格式不正确", {
                            time: 1500
                        });
                    }                    
                }
            }
        }
	};
	$(function(){
		Main.init();
	});
})();