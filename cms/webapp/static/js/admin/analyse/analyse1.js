$.ajax({
	"url":"/admin/ana/infonum",
	"type":"post",
	"success":function(result){
		var res = JSON.parse(result)
		if(res.status == "1"){
			var dat = {};
			dat = res.data;
			var j=0
			for(var i=0;i<dat.length;i++){
				dataAxis1[i] = i/10;
				data1[i] = Number(dat[i]);
				if(dat[i] != 0){
					dd[j] = {};
					dd[j].value = dat[i];
					dd[j].name = i/10+"分"; 
					dataAxis2[i] = i/10;
					j++;
					
				}
			}
			for (var i = 0; i < data1.length; i++) {
				dataShadow1.push(yMax1);
			}
			
			option = {
					title: {
						text: '评分分布'
					},
					xAxis: {
						text:"书的数量",
						name:"分数",
						data: dataAxis1,
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
						z: 100
					},
					yAxis: {
						name:"数量",
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
					                    	data: dataShadow1,
					                    	animation: false
					                    },
					                    {
					                    	type: 'bar',
					                    	itemStyle: {
					                    		normal: {
					                    			color: new echarts.graphic.LinearGradient(
					                    					0, 0, 0, 1,
					                    					[
					                    					 {offset: 0, color: '#83bff6'},
								                            {offset: 0.5, color: '#188df0'},
								                            {offset: 1, color: '#188df0'}
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
					                    	data: data1,
					                    	text: "分数"
					                    }
					                    ]
			};
			myChart1.setOption(option);
			option = {
				    title : {
				        text: '分数比例',
				        subtext: '来源：豆瓣',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: dataAxis2
				    },
				    series : [
				        {
				            name: '数量',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:dd,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
			myChart2.setOption(option);
		}
	}

});
var myChart1 = echarts.init(document.getElementById('test1'));
var myChart2 = echarts.init(document.getElementById('test2'));
var dd = [];
var dataAxis1=[];
var dataAxis2=[];
var data1=[] ;
var yMax1 = 100;
var dataShadow1 = [];

// Enable data zoom when user click bar.
var zoomSize1 = 6;
myChart1.on('click', function (params) {
	console.log(dataAxis1[Math.max(params.dataIndex - zoomSize1 / 2, 0)]);
	myChart1.dispatchAction({
		type: 'dataZoom',
		startValue: dataAxis1[Math.max(params.dataIndex - zoomSize1 / 2, 0)],
		endValue: dataAxis1[Math.min(params.dataIndex + zoomSize1 / 2, data.length - 1)]
	});
});

