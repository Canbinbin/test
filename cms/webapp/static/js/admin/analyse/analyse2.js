$.ajax({
       "url":"/admin/ana/info",
       "type":"post",
       "success":function(result){
    	    var res = JSON.parse(result)
	        if(res.status == "1"){
              var dat = {};
              dat = res.data;
              for(var i=0;i<dat.length;i++){
            	  dataAxis[i] = dat[i].name;
            	  data[i] = Number(dat[i].num);
              }
              for (var i = 0; i < data.length; i++) {
            	    dataShadow.push(yMax);
            	}

            	option = {
            	    title: {
            	        text: '豆瓣读书',
            	        subtext: 'Feature Sample: Gradient Color, Shadow, Click Zoom'
            	    },
            	    xAxis: {
            	    	name:"豆瓣评分"
            	        data: dataAxis,
            	        axisLabel: {
            	            inside: true,
            	            textStyle: {
            	                color: '#003333'
            	            }
            	        },
            	        axisTick: {
            	            show: false
            	        },
            	        axisLine: {
            	            show: false
            	        },
            	        z: 10
            	    },
            	    yAxis: {
            	    	name:"书名",
            	        axisLine: {
            	            show: false
            	        },
            	        axisTick: {
            	            show: false
            	        },
            	        axisLabel: {
            	            textStyle: {
            	                color: '#999'
            	            }
            	        }
            	    },
            	    dataZoom: [
            	        {
            	            type: 'inside'
            	        }
            	    ],
            	    series: [
            	        { // For shadow
            	            type: 'bar',
            	            itemStyle: {
            	                normal: {color: 'rgba(0,0,0,0.05)'}
            	            },
            	            barGap:'-100%',
            	            barCategoryGap:'40%',
            	            data: dataShadow,
            	            animation: false
            	        },
            	        {
            	            type: 'bar',
            	            itemStyle: {
            	                normal: {
            	                    color: new echarts.graphic.LinearGradient(
            	                        0, 0, 0, 1,
            	                        [
            	                            {offset: 0, color: '#CC3366'},
				                            {offset: 0.5, color: '#CC3333'},
				                            {offset: 1, color: '#CC3300'}
            	                        ]
            	                    )
            	                },
            	                emphasis: {
            	                    color: new echarts.graphic.LinearGradient(
            	                        0, 0, 0, 1,
            	                        [
            	                            {offset: 0, color: '#2378f7'},
            	                            {offset: 0.7, color: '#2378f7'},
            	                            {offset: 1, color: '#83bff6'}
            	                        ]
            	                    )
            	                }
            	            },
            	            data: data
            	        }
            	    ]
            	};
            	myChart.setOption(option);
	        }
        }
	                       
});
var myChart = echarts.init(document.getElementById('test'));

var dataAxis=[];
var data=[] ;
var yMax = 10;
var dataShadow = [];

// Enable data zoom when user click bar.
var zoomSize = 6;
myChart.on('click', function (params) {
    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
    myChart.dispatchAction({
        type: 'dataZoom',
        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
    });
});



