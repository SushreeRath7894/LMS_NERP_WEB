function manufacturingKPIHighChat (){
	var org = $("#KPIOrganization").val();
	var orgDiv = $("#KPIDivision").find('option:selected').text();
	var location = $("#KPILocation").find('option:selected').text();
	var fromDate = $("#fromDate5").val();
	var toDate = $("#toDate5").val();
	
	$.ajax({
		url: "dashboard-kpi-effectiveness?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-kpi-quality-performance?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-kpi-production?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-kpi-cost-revenue?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});


	Highcharts.chart('efectiveness1', {
	         chart: {
	             type: 'line',
	             animation: true,
	             backgroundColor: 'transparent',
	             height: 30,
	             margin: 10
	         },
	         title: {
	             text: ''
	         },
	         subtitle: {
	             text: ''
	         },

	         credits: {
	             enabled: false
	         },
	         xAxis: {
	             tickLength: 0,
	             tickWidth: 0,
	             lineColor: 'transparent',
	             labels: {
	                 enabled: false,
	             },
	             categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
	         },
	         yAxis: {
	             gridLineColor: 'transparent',
	             title: {
	                 text: ''
	             },
	             labels: {
	                 enabled: false
	             },
	             showInLegend: false
	         },
	         plotOptions: {
	             line: {
	                 dataLabels: {
	                     enabled: false
	                 },
	                 showInLegend: false,
	                 enableMouseTracking: false
	             }
	         },
	         navigation: {
	             buttonOptions: {
	                 enabled: false
	             }
	         },
	         series: [{
	             name: '',
	             data: [16.0, 28.2, 23.1, 17.9, 32.2],
	             color: '#bf05ff',
	             showInLegend: false,
	             marker: {
	                 radius: 3,
	             }
	         },]
	     });
	     
	     //efectiveness 2
	     Highcharts.chart('efectiveness2', {
	         chart: {
	             type: 'line',
	             animation: true,
	             backgroundColor: 'transparent',
	             height: 30,
	             margin: 10
	         },
	         title: {
	             text: ''
	         },
	         subtitle: {
	             text: ''
	         },

	         credits: {
	             enabled: false
	         },
	         xAxis: {
	             tickLength: 0,
	             tickWidth: 0,
	             lineColor: 'transparent',
	             labels: {
	                 enabled: false,
	             },
	             categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
	         },
	         yAxis: {
	             gridLineColor: 'transparent',
	             title: {
	                 text: ''
	             },
	             labels: {
	                 enabled: false
	             },
	             showInLegend: false
	         },
	         plotOptions: {
	             line: {
	                 dataLabels: {
	                     enabled: false
	                 },
	                 showInLegend: false,
	                 enableMouseTracking: false
	             }
	         },
	         navigation: {
	             buttonOptions: {
	                 enabled: false
	             }
	         },
	         series: [{
	             name: '',
	             data: [16.0, 28.2, 23.1, 17.9, 32.2],
	             color: '#bf05ff',
	             showInLegend: false,
	             marker: {
	                 radius: 3,
	             }
	         },]
	     });
	     
		//return on assets
		
		Highcharts.chart('efectiveness3', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#bf05ff',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//efectiveness 4
		Highcharts.chart('efectiveness4', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#bf05ff',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//efectiveness curr prev 1
		Highcharts.chart('efectivenessCurrPrev1', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [59],
				color: '#bf05ff',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [47],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//efectiveness curr prev 2
		Highcharts.chart('efectivenessCurrPrev2', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [45],
				color: '#bf05ff',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [34],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//efectiveness curr prev 3
		Highcharts.chart('efectivenessCurrPrev3', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [32],
				color: '#bf05ff',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [40],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//efectiveness curr prev 4
		Highcharts.chart('efectivenessCurrPrev4', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [25],
				color: '#bf05ff',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [20],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});

		  
		Highcharts.chart('qualityPerf1', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#F79C92',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//qualityPerf 2
		Highcharts.chart('qualityPerf2', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#F79C92',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//qualityPerf 3
		Highcharts.chart('qualityPerf3', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#F79C92',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//qualityPerf 4
		Highcharts.chart('qualityPerf4', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#F79C92',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});	
		
		//qualityPerf curr prev 1
		Highcharts.chart('qualityPerfCurrPrev1', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [59],
				color: '#F79C92',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [47],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//qualityPerf curr prev 2
		Highcharts.chart('qualityPerfCurrPrev2', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [45],
				color: '#F79C92',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [34],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//qualityPerf curr prev 3
		Highcharts.chart('qualityPerfCurrPrev3', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [32],
				color: '#F79C92',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [40],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//qualityPerf curr prev 4
		Highcharts.chart('qualityPerfCurrPrev4', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [25],
				color: '#F79C92',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [20],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
	Highcharts.chart('production1', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#B422B6',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//production 2
		Highcharts.chart('production2', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#B422B6',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//production 3
		Highcharts.chart('production3', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#B422B6',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//production 4
		Highcharts.chart('production4', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#B422B6',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//production curr prev 1
		Highcharts.chart('productionCurrPrev1', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [59],
				color: '#B422B6',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [47],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//production curr prev 2
		Highcharts.chart('productionCurrPrev2', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [45],
				color: '#B422B6',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [34],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//production curr prev 3
		Highcharts.chart('productionCurrPrev3', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [32],
				color: '#B422B6',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [40],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//production curr prev 4
		Highcharts.chart('productionCurrPrev4', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [25],
				color: '#B422B6',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [20],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		
		Highcharts.chart('costRevenue1', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#CA7CE5',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//costRevenue 2
		Highcharts.chart('costRevenue2', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#CA7CE5',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//costRevenue 3
		Highcharts.chart('costRevenue3', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#CA7CE5',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//costRevenue 4
		Highcharts.chart('costRevenue4', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 30,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#CA7CE5',
				showInLegend: false,
				marker: {
					radius: 3,
				}
			},]
		});
		//costRevenue curr prev 1
		Highcharts.chart('costRevenueCurrPrev1', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [59],
				color: '#CA7CE5',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [47],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//costRevenue curr prev 2
		Highcharts.chart('costRevenueCurrPrev2', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [45],
				color: '#CA7CE5',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [34],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//costRevenue curr prev 3
		Highcharts.chart('costRevenueCurrPrev3', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [32],
				color: '#CA7CE5',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [40],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
		//costRevenue curr prev 4
		Highcharts.chart('costRevenueCurrPrev4', {
			chart: {
				animated: true,
				height: 40,
				margin: 0
			},
			title: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			credits: {enabled: false, },
			xAxis: {
				categories: ['6 months'],
				labels: {enabled: false, },
				lineColor: 'transparent',
			},
			yAxis: {
				min: 0,
				max: 50,
				title: {
					text: ''
				},
				labels: {enabled: false, },
				lineColor: 'transparent',
				gridLineColor: 'transparent'
			},
			legend: {enabled: false, },

			series: [{
				type: 'bar',
				name: '',
				data: [25],
				color: '#CA7CE5',
			}, {
				name: '',
				color: 'transparent',
				lineColor: 'transparent',
				data: [20],
				type: 'spline',
				dataLabels: {
					enabled: false,
				},
				marker: {
					lineWidth: 2,
					fillColor: '#005c9f'
				}
			},]
		});
         
}