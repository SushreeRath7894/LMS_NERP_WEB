function qualityHighChat (){

	var org = $("#qualityOrganization").val();
	var orgDiv = $("#qualityDivision").find('option:selected').text();
	var location = $("#qualityLocation").find('option:selected').text();
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	
	
	$.ajax({
		url: "dashboard-right-first-time?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var rightFirstTimePercentageList = [];
					
			for (var i = 0; i < allData.length; i++) {				
				var item = allData[i];
				monthYearList.push(item.monthYear);
				rightFirstTimePercentageList.push(item.averageRightFirstTimePercentageThisMonth);
								
			}	
			
				
	Highcharts.chart('manRightFirstTime', {
			chart: {
				type: 'line',
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},
			credits: {enabled: false},
			xAxis: [{
				categories: monthYearList,
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			legend: {enabled: false},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: '',
				type: 'line',
				color: '#F79C92',
				data: rightFirstTimePercentageList,
				dataLabels: {
					enabled: false,
				},
				dashStyle: 'ShortDashDot',
				marker: {
					enabled: false
				}

			}]
		});		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-avg-right-first-time?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var averageRightFirstTimePercentage = allData[0].averageRightFirstTimePercentage;	
			var averageRightFirstTimePercentageInteger = parseInt(averageRightFirstTimePercentage);	
			//alert("averageRightFirstTimePercentageInteger---------"+averageRightFirstTimePercentageInteger);	
			
				
		Highcharts.chart('manAvgRightFirstTime', {
			chart: {
				type: 'pie',
				height: 250,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#bf05ff', '#dfdfdf'],
			title: {
				text: averageRightFirstTimePercentage + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 110,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: 'Achieved target '+averageRightFirstTimePercentageInteger + '!',
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 130,
				margin: 0,
				style: {"fontSize": '12', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [["Target", averageRightFirstTimePercentageInteger], ["Target Not Achieved", 100-averageRightFirstTimePercentageInteger]],
				size: '100%',
				innerSize: '70%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-most-common-defect?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
	var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;

			// Initialize the series array
			var series = [{
			    size: '100%',
			    data: [],
			    colors: ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', '#FF5733', '#4CAF50', '#1E88E5']
			}];
			
			// Iterate through the data and push the required format to the 'data' array
			allData.forEach(function(item) {
			    series[0].data.push([item.impactName, item.totalreturnPercentByImpact]);
			});

			//console.log(series);
            
            const colors = ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', '#FF5733', '#4CAF50', '#1E88E5'];
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				var impactName= item.impactName;
				var color = 	colors[i];		
	   		   		
		   		var defectTypeQuality = `<div class="d-flex align-items-center">
			   		<div class="man-legend" style="background: ${color}"></div>
	                <div class="man-legend-name">
	                    ${impactName}
	                </div>
	            </div>`;
	                   
	
				$("#defectTypeQualityId").append(defectTypeQuality);
}
			
			
			//man Downtime Causes
	
		Highcharts.chart('manMostCommonDefects', {
			chart: {
				type: 'pie',
				animation: true,
				height: 190
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},

			title: {
				text: '',
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},

			plotOptions: {
				pie: {
					dataLabels: {
						distance: '-40',
						format: '{point.percentage:.1f}%',
						style: {fontSize: '10', color: '#ffffff',textOutline: false }
					}
				}
			},
            
			series: series
		});
	
	
		/*	var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;

			// Initialize the series array
			var series = [{
			    size: '100%',
			    data: [],
			    colors: ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', '#FF5733', '#4CAF50', '#1E88E5']
			}];
			
			// Iterate through the data and push the required format to the 'data' array
			allData.forEach(function(item) {
			    series[0].data.push([item.impactName, item.totalreturnPercentByImpact]);
			});

			console.log(series);

			
			
			//man Downtime Causes
	
		Highcharts.chart('manMostCommonDefects', {
    chart: {
        type: 'pie',
        animation: true,
        height: 220
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    title: {
        text: '',
    },
    subtitle: {
        text: ''
    },

    credits: {
        enabled: false
    },

    plotOptions: {
        pie: {
            dataLabels: {
                distance: -40, // Adjust this to change the label's position
                style: {
                    fontSize: '10px',
                    color: '#ffffff',
                    textOutline: false
                },
                formatter: function() {
                    return this.point.name; // Display the impactName
                }
            },
            showInLegend: true
        }
    },

    series: series
});

      */      
			
		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-rate-of-return?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			    monthYearList.push(item.monthYear);
			    purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			    salesRateOfReturnList.push(item.salesRateOfReturn);
			}
			
			
			
		Highcharts.chart('manRateOfReturn', {
			chart: {
				type: 'column',
				zoomType: 'xy',
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
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
			xAxis: [{
				categories: monthYearList,
				crosshair: true
			}],
			yAxis: [{
				labels: {format: '{value} %', },
				title: {
					text: '',
				},
			}
			],
			plotOptions: {
				column: {
					pointPadding: 0,
					borderWidth: 0
				}
			},
			legend: {
				enabled: true
			},
			series: [{
				name: 'Purchase',
				data: purchaseRateOfReturnList,
				tooltip: {
					valueSuffix: ' mm'
				},
				color: '#bf05ff',

			}, {
				name: 'Sales',
				data: salesRateOfReturnList,
				color: '#F79C92',
				margin: '0',

			}]
		});	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-defect-density?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			    monthYearList.push(item.monthYear);
			    purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			    salesRateOfReturnList.push(item.salesRateOfReturn);
			}
			
			
		Highcharts.chart('manQualityDefectDensity', {
			chart: {
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},
			credits: {enabled: false},
			xAxis: [{
				categories: monthYearList,
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Purchase',
				type: 'line',
				color: '#F79C92',
				data: purchaseRateOfReturnList,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}

			}, {
				name: 'Sales',
				type: 'line',
				data: salesRateOfReturnList,
				color: '#B422B6',
				lineWidth: 1,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
					
			
		},
		error: function(error) {
			console.error(error);
		}
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
				color: '#00536e',
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
				color: '#00536e',
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
				color: '#00536e',
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
				color: '#00536e',
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
				color: '#00cddb',
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
				color: '#00cddb',
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
				color: '#00cddb',
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
				color: '#00cddb',
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


function rateOfReturnQuality(id){
		var org = $("#qualityOrganization").val();
		var orgDiv = $("#qualityDivision").find('option:selected').text();
		var location = $("#qualityLocation").find('option:selected').text();
		var fromDate = $("#fromDate3").val();
		var toDate = $("#toDate3").val();
	
		$.ajax({
		url: "dashboard-rate-of-return-by-type?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&type=" + id,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
			
			
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			   
			    if (id === '0') {
			         monthYearList.push(item.monthYear);
			   		 purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			   		 salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			    
			    if (id === '1') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			        //purchaseRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(0);
			    }
			    
			     if (id === '2') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(0);
			        //salesRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			}
			
			
			
		Highcharts.chart('manRateOfReturn', {
			chart: {
				type: 'column',
				zoomType: 'xy',
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
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
			xAxis: [{
				categories: monthYearList,
				crosshair: true
			}],
			yAxis: [{
				labels: {format: '{value} %', },
				title: {
					text: '',
				},
			}
			],
			plotOptions: {
				column: {
					pointPadding: 0,
					borderWidth: 0
				}
			},
			legend: {
				enabled: true
			},
			series: [{
				name: 'Purchase',
				data: purchaseRateOfReturnList,
				tooltip: {
					valueSuffix: ' mm'
				},
				color: '#bf05ff',

			}, {
				name: 'Sales',
				data: salesRateOfReturnList,
				color: '#F79C92',
				margin: '0',

			}]
		});	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
}


function defectDensityQuality(id){
	var org = $("#qualityOrganization").val();
	var orgDiv = $("#qualityDivision").find('option:selected').text();
	var location = $("#qualityLocation").find('option:selected').text();
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	
		
	$.ajax({
		url: "dashboard-defect-density?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			   
			    if (id === '0') {
			         monthYearList.push(item.monthYear);
			   		 purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			   		 salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			    
			    if (id === '1') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			        //purchaseRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(0);
			    }
			    
			     if (id === '2') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(0);
			        //salesRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			}
			
			
			
		Highcharts.chart('manQualityDefectDensity', {
			chart: {
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},
			credits: {enabled: false},
			xAxis: [{
				categories: monthYearList,
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Purchase',
				type: 'line',
				color: '#F79C92',
				data: purchaseRateOfReturnList,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}

			}, {
				name: 'Sales',
				type: 'line',
				data: salesRateOfReturnList,
				color: '#B422B6',
				lineWidth: 1,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
}