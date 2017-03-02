(function(){
	 $.fn.adminSelect=function(opts){
	       	var hiddedId = opts.hiddedId;
	        var url = opts.url;
	        var that = $(this);
	        $(this).click(function(){
	        	layer.open({
	                type: 2,
	                title: "请选择负责人",
	                area: ['600px', '550px'],
	                btn:['确定','取消'],
	                content: url,
	                yes:function(index){
		               	var obj=layer.getChildFrame('body')
		               	var str=obj.find('input[name="radio"]:checked').val();
		               	var id=obj.find('input[name="radio"]:checked').attr('data-id');
		               	if(str===undefined || str==''){
		               		layer.msg('请选择');
		               		return false;
		               	}else{
		               		$("#"+hiddedId).val(id);
			               	that.val(str);
			               	layer.close(index);
		               	}
	                }
	            });
	        });
     };
	
})(this,jQuery,undefined)