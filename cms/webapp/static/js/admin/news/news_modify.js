(function(window, $, undefined) {
    var Main = {
        init: function() {
            this._cacheElement();
            this._initEditor();
            this._bindEvent();


        },
        _cacheElement: function() {
            var that = this;
            $.each([
                "#myform",
                "#submitList",
                "#submitStay",
                "#pickImgCheckbox",
                "#subContentCheckbox",
                ".previewImage",
                "#picPath",
                ".uploadPlus",
                "#file",
                "#description"
            ], function(i, item) {
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
            that.$submitStay.on("click", function() {
                that._initValidate(2);
            });
            $(document).on("change", ".upload-image-btn", function() {
                that._imageUpload();
            });
            that.$subContentCheckbox.on("click", function() {
                if ($(this).prop("checked")) {
                    that.subText(this);
                }
            });
            that.$pickImgCheckbox.on("click", function() {
                if ($(this).prop("checked")) {
                    that.subImage(this);
                }
            });
        },
        _initValidate: function(flag) {
            var that = this;
            that.$myform.validate({
                submitHandler: function(form) {
                    if (!$.trim(that._editor.getContentTxt()))
                    {
                        layer.msg("请填写正文", {
                            time: 2000
                        }, function() {
                            that._editor.focus();
                        });
                    } 
                    else 
                    {
                        $.ajax({
                            url: "/admin/news/save", //登录处理页
                            dataType: "json",
                            type:   "POST",
                            data: $(myform).serialize(),
                            success: function(data) { //登录成功后返回的数据
                                layer.msg(data.msg, {
                                    time: 2000
                                }, function() {
                                    if (flag == 1) {
                                        window.location.href = "/admin/news";
                                    } else if (flag == 2) {
                                        // window.location.reload(true);
                                    	window.location.href = "/admin/news/add";
                                    }
                                })

                            },
                            error: function(data) {
                                layer.msg(data.msg, {
                                    time: 2000
                                });
                            }
                        })
                    }
                }
            })
        },

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
                        url: "/admin/news/upload_image?date=" + new Date().getTime(),
                        success: function(data) {
                            if (data.status == 200) {
                                that.$picPath.val(data.data);
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
        },
        subText: function(obj) {
            var that = this;
            var num = $(obj).closest(".input-checkbox-wrapper").find('[data-validate="number"]').val();
            num = Number(num);
            var str = that._editor.getContentTxt();
            str = str.substring(0, num);

            that.$description.val(str);

        },
        subImage: function(obj) {
            var that = this;
            var num = $(obj).closest(".input-checkbox-wrapper").find('[data-validate="number"]').val();
            var str=that._editor.getPlainTxt();
            var reg = /<img[^>]*src[=\'\"\s]+([^\"\']*)[\"\']?[^>]*>/gi; 
            var arr=[];
            while (reg.exec(str)){
                arr.push( (RegExp.$1) ); 
            };
            if(arr.length<1){
            	layer.msg("编辑框没有上传图片");
            	return false;
            };
            if ((num - 1) > arr.length) {
                layer.msg("输入截取数字有误");
                return false;
            };
        
            that.$picPath.val(arr[num-1]);
        }

    }

    $(function() {
        Main.init();
    });
})(this, jQuery);