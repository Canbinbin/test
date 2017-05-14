
var myChart1 = echarts.init(document.getElementById('main'));

function getDateByTime(){
	myChart1.showLoading();
	var uniqueId = $("#getuniqueId").val();
	endtime = $("#publishDateEnd").val();
	starttime = $("#publishDateStart").val();
	if(endtime == "" || starttime == ""){
		layer.msg("请选择开始时间和结束时间！");
		return;
	}
	var endTime = endtime;
	var beginTime = starttime;
	$.ajax({
		"url":"/admin/ana/getDataByTime",
		"type":"post",
		"data":{
			uniqueId : uniqueId,
			beginTime : beginTime,
			endTime : endTime
		},
		"success":function(result){
			var res = JSON.parse(result);
			if(res.status == "-1"){
				 layer.msg(res.msg);
				 return;
			 }
			var da = res.data;
			if(da.length <= 0){
				layer.msg("该事件段无数据！请查询其他时间段");
				return;
			}
			showDate(da);
		}
	});
}
//"2017-04-24 21:32:58"

$(document).ready(function(){
	
	showDate("");
});

function toDateTime(timape){
	var date = new Date(timape);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds(); 
	return Y+M+D+h+m+s; //
}
function showDate(da){
	var base = +new Date();
	var oneDay = 1000;
	var date = [];

	if(da != "" && da.length>0){
		var data=[];
		var html = "";
		for(var i = 0; i<da.length;i++){
			date.push(da[i].updateTime.split(".")[0]);
			data.push(da[i].numericalVal);
			html += "<tr class='info'><td>"+da[i].id+"</td>";
			html += "<td>"+da[i].numericalVal+"</td>";
			html += "<td>"+da[i].longitude+"</td>";
			html += "<td>"+da[i].latitude+"</td>";
			html += "<td>"+(da[i].textVal==undefined?"无":da[i].textVal)+"</td>";
			html += "<td>"+toDateTime(da[i].updateTime)+"</td></tr>";
		}
		$("#historyInfo").html(html);
	}
	else{
		var data = [0];
		
		for (var i = 1; i < 20000; i++) {
			var now = new Date(base);
			date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-')+" "+[now.getHours(), now.getMinutes(), now.getSeconds()].join(':'));
			data.push(0);
		}
	}

	option = {
	    tooltip: {
	        trigger: 'axis',
	        position: function (pt) {
	            return [pt[0], '10%'];
	        }
	    },
	    title: {
	        text: '选择时间区间显示',
	        subtext:'x轴可拖动显示'
	    },
	    toolbox: {
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: date
	    },
	    yAxis: {
	        type: 'value',
	        boundaryGap: [0, '50%']
	    },
	    dataZoom: [{
	        type: 'inside',
	        start: 0,
	        end: 10
	    }, {
	        start: 0,
	        end: 10,
	        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
	        handleSize: '80%',
	        handleStyle: {
	            color: '#fff',
	            shadowBlur: 3,
	            shadowColor: 'rgba(0, 0, 0, 0.6)',
	            shadowOffsetX: 2,
	            shadowOffsetY: 2
	        }
	    }],
	    series: [
	        {
	            name:'节点数据',
	            type:'line',
	            smooth:false,
	            symbol: 'none',
	            sampling: 'average',
	            itemStyle: {
	                normal: {
	                    color: 'rgb(255, 70, 131)'
	                }
	            },
//	            areaStyle: {
//	                normal: {
//	                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
//	                        offset: 0,
//	                        color: 'rgb(255, 158, 68)'
//	                    }, {
//	                        offset: 1,
//	                        color: 'rgb(255, 70, 131)'
//	                    }])
//	                }
//	            },
	            data: data
	        }
	    ]
	};
    myChart1.hideLoading();
	myChart1.setOption(option);
}
