$.treeSelect=function(url,box_id,name_id,id_id){
    var setting = {  
        data: {  
            simpleData: {  
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0  
            }  
        },
        callback: {
            onClick: onClick
        }
    };

    function onClick(){
      var arr=[];
      var treeObj=$.fn.zTree.getZTreeObj(box_id);
      var nodes = treeObj.getSelectedNodes();
      if(nodes.length>0){
          arr.push(nodes['0'].name);
          arr.push(nodes['0'].id);
          return arr;
      };
          
    };
  
    $("#"+name_id).click(function(){
                var idx;
                layer.closeAll();
                var num=$(this).attr('data_val');
                $.ajax({
                    "url":url,
                    "dataType":"json",
                    "type":"get",
                    "beforeSend":function(){
                        idx=layer.load(0, {shade: false});
                    },
                    "success":function(data){
                        layer.close(idx);
                          layer.open({
                            type: 1,
                            shade: false,
                            title: '选择上级菜单', //标题
                            content: $('#'+box_id), //捕获的元素
                            btn:['确定','取消'],
                            yes: function(index){
                                this.content.hide();
                                layer.close(index);
                                var arr=onClick();
                                if(arr!=undefined){
                                    $("#"+name_id).val(arr[0]);
                                    $("#"+id_id).val(arr[1]);
                                }
                                
                            }
                        });
                        $.fn.zTree.init($("#"+box_id), setting, data); 
                    }

                });
       
    });
};