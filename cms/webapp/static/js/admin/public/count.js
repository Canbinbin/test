	//计算实际数量和实际单价
	function count(one_id,two_id,result_id){
		$("#"+one_id).keyup(function(){
			if($(this).val()!='' && $("#"+two_id).val()!=''){
				$("#"+result_id).val($("#"+one_id).val()*$("#"+two_id).val());
			}else{
				$("#"+result_id).val('');
			};
		});
		$("#"+two_id).keyup(function(){
			if($(this).val()!='' && $("#"+one_id).val()!=''){
				$("#"+result_id).val($("#"+one_id).val()*$("#"+two_id).val());
			}else{
				$("#"+result_id).val('');
			};
		});
	};
	



