//默认图片
default_image = '/images/nopic.gif';

function uploadify_success(data, objid) {
    json_str = JSON.parse(data);

    if (json_str.code == 0) {
        $("#" + objid + "_id").val(json_str.file_id);
        $("#" + objid).val(json_str.path);

        if (json_str.type == 'img') {
            $("#img_" + objid).attr('src', json_str.path);
        }
		else
		{
			app_str = '<img align="absmiddle" src=""/images/ext/' + json_str.ext + '.gif""> ' + json_str.name + '' + json_str.file_size;
			
        	$("#" + objid + '_con').empty().append(app_str);
		}
        //console.log(json_str);
    } else {
        alert(json_str.message);
    }
}

function uploadify_multi_success(data, objid) {
    json_str = JSON.parse(data);

    if (json_str.code == 0) {
        var app_str = '';

        app_str = app_str + '<div id="mf_' + json_str.file_id + '"><a href="javascript:;" onclick="multi_file_remove(\'mf_' + json_str.file_id + '\')"><img align="absmiddle" src="/images/drop.png"></a> <input name="mf_title[' + json_str.file_id + ']" type="text" value="' + json_str.name + '" size="40" /> <a href="javascript:;" onclick="show_file_info(\'' + json_str.file_id + '\')" title="查看文件信息"><img align="absmiddle" src="/images/ext/' + json_str.ext + '.gif"></a> ' + json_str.file_size + '</div>';

        $("#" + objid + '_mf_con').append(app_str);
        //console.log(app_str);
    } else {
        alert(json_str.message);
    }
}

function multi_file_remove(id) {
    $("#" + id).remove();
}

function unset_thumb(objid) {
    $("#" + objid + "_id").val('');
    $("#" + objid).val('');
    $("#img_" + objid).attr('src', default_image);
}

function unset_thumb(objid) {
    $("#" + objid + "_id").val('');
    $("#" + objid).val('');
    $("#img_" + objid).attr('src', default_image);
}

function show_uploadify_file(objid) {
    alpha_id = $("#" + objid + "_id").val();
    if (alpha_id == '') {
        alert('请先上传文件，然后再次点击');
        return false;
    }
	show_file_info(alpha_id);
}

/*
function zy_upload_complete(file, id) {
    json_str = JSON.parse(file);
    $("#" + id).val(json_str.alpha_id);
    $("#img_" + id).val(json_str.path);
    //console.log(file);
}
*/

function online_crop_jump(objid, crop_config) {
    alpha_id = $("#" + objid + "_id").val();
    if (alpha_id == '') {
        alert('请先上传图片，然后再次点击“裁剪”');
        return false;
    }

    location.href = '/crop/on_line/' + alpha_id;
    return false;
}

function online_uploadify_success(data, objid) {
    uploadify_success(data, objid);

    $("#cropPath").val($("#photo").val());
}


function show_crop_modal(objid, crop_config) {
    alpha_id = $("#" + objid + "_id").val();
    if (alpha_id == '') {
        alert('请先上传图片，然后再次点击“裁剪”');
        return false;
    }

    var ee = top.dialog({
        title: '在线裁剪图片',
        url: '/crop/modal_show/' + alpha_id + '/' + crop_config,
        width: 1000,
        height: 600,
        //quickClose: true,
        onshow: function() {
            //console.log('onshow');
        },
        oniframeload: function() {
            //console.log('oniframeload');
        },
        onclose: function() {
            if (this.returnValue) {

                closeReturnData = JSON.parse(this.returnValue);

                $("#img_" + objid).attr('src', closeReturnData.crop_path);
                $("#" + objid).val(closeReturnData.crop_path);
                $("#" + objid + "_id").val(closeReturnData.alpha_id);
                //console.log('Return:' + this.returnValue);
                //console.log(JSON.parse(this.returnValue));
            }
            //console.log('onclose');
        },
        onremove: function() {
            //console.log('onremove');
        }
    });


    ee.showModal();
}


//图像预览函数
function ias_select_change(img, selection) {

    realWidth = img_area_json.width * img_area_json.scale;

    realHeight = img_area_json.height * img_area_json.scale;

    //var scaleX = img_area_json.target_width / (selection.width || 1);
    // var scaleY = img_area_json.target_height / (selection.height || 1);
    var scaleX = pre_width / (selection.width || 1);
    var scaleY = pre_height / (selection.height || 1);


    $("#imgSelectWidth").text(Math.round(selection.width / img_area_json.scale));
    $("#imgSelectHeight").text(Math.round(selection.height / img_area_json.scale));
    /*console.log(selection);
    console.log('scaleX：'+scaleX);
    console.log('selection.x1：'+selection.x1);*/
    $('#preview img').css({

        width: Math.round(scaleX * realWidth) + 'px',

        height: Math.round(scaleY * realHeight) + 'px',

        marginLeft: -Math.round(scaleX * selection.x1) + 'px',

        marginTop: -Math.round(scaleY * selection.y1) + 'px'

    });
};


function ias_init(img, selection) {
    //将现有的选择框清除
    ias.setOptions({
        hide: true
    });

    if (!img_area_json.target_width && !img_area_json.ratio_width) {
        $('#preview').empty().html('如不限制图片输出大小，无法显示预览');
        return false;
    }

    //输出宽度
    if (img_area_json.target_width > 0) {
        if (img_area_json.target_width > pre_max_width) {
            pre_width = pre_max_width;

            pre_height = pre_max_width / img_area_json.target_width * img_area_json.target_height;
        } else {
            pre_width = img_area_json.target_width;
            pre_height = img_area_json.target_height;
        }

    }
    //输出比例
    else {
        pre_width = pre_max_width;
        pre_height = pre_max_width / img_area_json.ratio_width * img_area_json.ratio_height;
    }



    //console.log(img_area_json);


    $('#preview').empty().html('<img src="' + img_area_json.path + '" style="position:relative" />').css({
        overflow: 'hidden',
        width: pre_width + 'px',
        height: pre_height + 'px'
    });

    // width="'+pre_width+'" height="'+pre_height+'"
}

function ias_select_end(img, selection) {
    img_area_json.x1 = selection.x1;
    img_area_json.y1 = selection.y1;
    img_area_json.cropWidth = selection.x2 - selection.x1;
    img_area_json.cropHeight = selection.y2 - selection.y1;
    //console.log(selection);
}

function ias_change_ratio(ratio_w, ratios_h, target_w, target_h) {
    if (target_w > 0) {
        ias.setOptions({
            aspectRatio: ratio_w + ':' + target_h
        });
    } else if (ratio_w > 0) {
        ias.setOptions({
            aspectRatio: ratio_w + ':' + ratios_h
        });
    } else {
        ias.setOptions({
            aspectRatio: ''
        });
    }

    img_area_json.target_width = target_w;
    img_area_json.target_height = target_h;

    img_area_json.ratio_width = ratio_w;
    img_area_json.ratio_height = ratios_h;

    init_preview();
}

function crop_do() {
    if ((typeof img_area_json == 'undefined') || !img_area_json.cropWidth) {
        alert('请选择要截取的区域。');
        return false;
    }

    jsondata = 'data=' + JSON.stringify(img_area_json);
	
	//console.log(jsondata);

    $.ajax({
        type: "POST",
        url: "/crop/crop_do",
        data: jsondata, //$('#cropData').serialize(),
        dataType: 'json',
        success: function(data) {
			
			//console.log(data);
            if (data.status == 200) {
                var msg = data.msg;
                $("#cropResult").html('<a href="' + msg.new_image + '" target="_blank"><img src="' + msg.new_image + '" width="' + msg.dis_width + '" height="' + msg.dis_height + '" class="img-thumbnail" title="弹出新窗口查看图片真实大小" /></a>');
                $("#cropId").val(msg.alpha_id);
                $("#cropPath").val(msg.new_image);
            } else {
                alert('裁剪错误');
            }

            //成功之后就清除发表内容
            //$("#cropResult").html(msg);
        }
    });
			//console.log('----');
    //关闭默认的提交动作    
    return false;
}

function crop_use() {
    var cropId = $('#cropId').val();
    var cropPath = $('#cropPath').val();

    if (!cropId || !cropPath) {
        alert('请先剪裁。');
        return false;
    }

    try {
        var dialog = top.dialog.get(window);

    } catch (e) {
        $('body').append(
            '<p><strong>Error:</strong> 跨域无法无法操作 iframe 对象</p>' + '<p>chrome 浏览器本地会认为跨域，请使用 http 方式访问当前页面</p>'
        );
        return;
    }

    var jsonArr = new Array();
    jsonArr['alpha_id'] = cropId;
    jsonArr['crop_path'] = cropPath;

    jsonDataString = '{"alpha_id":"' + cropId + '","crop_path":"' + cropPath + '"}';

    dialog.close(jsonDataString); // 关闭（隐藏）对话框
    dialog.remove();
    //关闭默认的提交动作    
    return false;
}


/**
 * onclick='return(Checkform());'
 */

function Checkform() {
    var ConfirmMessage = "是否执行您所做的操作，请确认！";
    if (window.confirm(ConfirmMessage)) {
        return true;
    } else {
        return false;
    }
}



/**
 * <input name='chkAll' type='checkbox' id='chkAll' onclick='CheckAll(this.form)' value='checkbox' class='input_button'> 全选
 */
function unselectall() {
    if (document.myform.chkAll.checked) {
        document.myform.chkAll.checked = document.myform.chkAll.checked & 0;
    }
}

function CheckAll(form) {
    for (var i = 0; i < form.elements.length; i++) {
        var e = form.elements[i];
        if (e.Name != "chkAll" && e.disabled == false)
            e.checked = form.chkAll.checked;
    }
}


function show_file_info(id)
{
    if (!id) {
        alert('参数错误');
        return false;
    }

	$.ajax({
        type: "get",
        url: "/file/show_file/"+id,
        dataType: 'json',
        success: function(data) {
            if (data.status == 200) {
                var msg = data.msg;
                var d = dialog({
				title: '文件信息',
				content: msg
				});
				d.show();
            } else {
                alert('参数错误');
            }
        }
    });
	return false;
}


function submitTo(url, title, extra) {

    if(!title) title = '编辑';
    var mm = top.dialog({
        title: title,
        url: url,
        width: 800,
        height: 650,
        //quickClose: true,
        onshow: function() {
            //console.log('onshow');
        },
        oniframeload: function() {
            //console.log('oniframeload');
        },
        onclose: function() {
        },
        onremove: function() {
            //console.log('onremove');
        }
    });
    mm.showModal();
}


function video_player(url) {
    if (url == '') {
        alert('参数错误');
        return false;
    }

    var vp = top.dialog({
        title: '播放窗口',
        url: url,
        width: 1000,
        height: 650,
        //quickClose: true,
        onshow: function() {
            //console.log('onshow');
        },
        oniframeload: function() {
            //console.log('oniframeload');
        },
        onclose: function() {
			//console.log('onclose');
        },
        onremove: function() {
            //console.log('onremove');
        }
    });


    vp.showModal();
}

function playerstop() {
    setTimeend();
}
function setTimeend() {//获取下一部视频的播放ID
	nowD++;
	if (nowD >= videoarr.length ) {
		nowD = 0;
	}
	playvideo(nowD);
}
function Close() {//关闭播放列表
	CKobject._K_('a2').style.display = 'none';
	CKobject._K_('playerlist').style.display = 'none';
	CKobject._K_('a3').style.display = 'block';
	CKobject._K_('a1').style.width = '870px';
	CKobject.getObjectById('ckplayer_a1').width = 870;
}
function Open() {//打开播放列表
	CKobject._K_('a2').style.display = 'block';
	CKobject._K_('playerlist').style.display = 'block';
	CKobject._K_('a3').style.display = 'none';
	CKobject._K_('a1').style.width = '670px';
	CKobject.getObjectById('ckplayer_a1').width = 670;
}
