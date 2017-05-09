(function(){
var myChart = echarts.init(document.getElementById('testTime'));

function getData() {
	var uniqueId = $("#getuniqueId").val();
	$.ajax({
		"url":"/admin/ana/getData?uniqueId="+uniqueId,
		"type":"get",
		"success":function(result){
			 var res = JSON.parse(result);
			 if(res.status == "-1"){
				 layer.msg(res.msg);
				 return;
			 }
			var da = JSON.parse(res.data);
			var numericalVal = da.numericalVal;
			
			now = [now.getFullYear(),now.getMonth() + 1, now.getDate()].join('-')+" "+[now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
		    date.push(now);
		    data.push(numericalVal);

		    if (true) {
		        date.shift();
		        data.shift();
		    }

		    now = new Date(+new Date(now) + one);
			if(da.id != lastId){
				var html = "";
				html += "<td>"+da.id+"</id>";
				html += "<td>"+da.numericalVal+"</id>";
				html += "<td>"+da.longitude+"</id>";
				html += "<td>"+da.latitude+"</id>";
				html += "<td>"+(da[i].textVal==undefined?"无":da[i].textVal)+"</id>";
				html += "<td>"+toDateTime(da.updateTime)+"</id>";
				html += "<td>"+"正常"+"</td>";
				$("#info").html(html);
				lastId = da.id;
			}
		}
	});

}
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
var lastId =0;
var base = +new Date();
var one =  1000;
var date = [];

var data = [0];
var now = new Date(base);

function addData(shift) {
    now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-')+" "+[now.getHours(), now.getMinutes(), now.getSeconds()].join(':');
    date.push(now);
    data.push(0);

    if (shift) {
        date.shift();
        data.shift();
    }

    now = new Date(+new Date(now) + one);
}

for (var i = 1; i < 20; i++) {
    addData();
}

option = {
		tooltip: {
	        trigger: 'axis',
	        position: function (pt) {
	            return [pt[0], '10%'];
	        }
	    },
		title: {
	        text: '节点实时数据展示'
	    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: date
    },
    yAxis: {
        boundaryGap: [0, '50%'],
        type: 'value'
    },
    series: [
        {
            name:'数值',
            type:'line',
            smooth:false,
            symbol: 'none',
            stack: 'a',
//            areaStyle: {
//                normal: {}
//            },
            data: data
        }
    ]
};

setInterval(function () {
	getData();
    myChart.setOption({
        xAxis: {
            data: date
        },
        series: [{
            name:'数值',
            data: data
        }]
    });
}, one);
myChart.setOption(option);
})();