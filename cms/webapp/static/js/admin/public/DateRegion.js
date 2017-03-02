$(function(){
	//月区间选择
	$("#monthRegion").on("change",function(){
		var ret=$("#monthRegion").val();
		var date=new Date();
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		var nextYear=year;
		var nextMonth=month+1;
		if (month==12) {
			nextYear=year+1;
			nextMonth=1;
		}
		if (nextMonth<=9) {
			nextMonth="0"+nextMonth;
		}
		if (month<=9) {
			month="0"+month;
		}
	
		var monthRegion=year+"-"+month;
		var nextMonthRegion=nextYear+"-"+nextMonth;
		var lastDay=new Date(year,month,0).getDate();
		var nextLastDay=new Date(nextYear,nextMonth,0).getDate();
		if(ret > 0){
			if (ret==1) {
				//本月上旬
				$("#d4311").val(monthRegion+"-01");
				$("#d4312").val(monthRegion+"-10");
			}
			if (ret==2) {
				//本月中旬
				$("#d4311").val(monthRegion+"-11");
				$("#d4312").val(monthRegion+"-20");
			}
			if (ret==3) {
				//本月下旬
				$("#d4311").val(monthRegion+"-21");
				$("#d4312").val(monthRegion+"-"+lastDay);
			}
			if (ret==4) {
				//下月上旬
				$("#d4311").val(nextMonthRegion+"-01");
				$("#d4312").val(nextMonthRegion+"-10");
			}
			if (ret==5) {
				//下月中旬
				$("#d4311").val(nextMonthRegion+"-11");
				$("#d4312").val(nextMonthRegion+"-20");
			}
			if (ret==6) {
				//下月下旬
				$("#d4311").val(nextMonthRegion+"-21");
				$("#d4312").val(nextMonthRegion+"-"+nextLastDay);
			}
		}
	});
})