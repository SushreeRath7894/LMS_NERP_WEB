
$(document).ready(function() {
	
	const currentDate = new Date();

    // Current month and year
    const currentMonth = currentDate.getMonth() + 1; // Months are zero-indexed
    const currentYear = currentDate.getFullYear();
   // alert("Current Month: " + currentMonth + ", Current Year: " + currentYear);

    // Last month and year
    let lastMonth = currentMonth - 1;
    let lastYear = currentYear;

    if (lastMonth === 0) {
        lastMonth = 12; // December of the previous year
        lastYear -= 1;
    }
	
    $('#productIdAnalysis').change(function() {
        var selectedValue = $(this).val(); // Get the selected value (key)
        if (selectedValue) {
		var org = $("#analysisOrganization").val();
		var orgDiv = $("#analysisDivision").find('option:selected').text();
		var location = $("#analysisLocation").find('option:selected').text();
		var fromDate = $("#fromDate6").val();
		var toDate = $("#toDate6").val();
		var productSkuId = $('#productIdAnalysis').val(); 
		$.ajax({
				url: "dashboard-analysis-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
				"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&productSkuId=" + productSkuId
				+ "&currentMonth=" + currentMonth+ "&currentYear=" + currentYear+ "&lastMonth=" + lastMonth
				+ "&lastYear=" + lastYear,
				
				type: 'GET',
				dataType: 'json',
				success: function(response) {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
								   
					var quantityValueAnalysisId = allData[0].productQty;
					var actualProductionAnalysisId = allData[0].productProductionActual;
					var targetProductionAnalysisId = allData[0].productProductionEstimate;
					
					var reworkQtyAnalysisId = allData[0].reworkQty;
					var reworkQtyCurrentMonthAnalysisId = allData[0].reworkQtyCurrentMonth;
					var reworkQtyLastMonthAnalysisId = allData[0].reworkQtyLastMonth;
					
					var manufacturingCostAnalysisId = allData[0].manufacturingQtyPercentage;
					var manufacturingCostCurrentMonthAnalysisId = allData[0].manufacturingCostThisMonth;
					var manufacturingCostLastMonthAnalysisId = allData[0].manufacturingCostLastMonth;
					
					var labourCostAnalysisId = allData[0].labourCostPercentage;
					var labourCostCurrentMonthAnalysisId = allData[0].labourCostThisMonth;
					var labourCostLastMonthAnalysisId = allData[0].labourCostLastMonth;
					
					
					$("#quantityValueAnalysisId").text(quantityValueAnalysisId + "%");
					$("#actualProductionAnalysisId").text(actualProductionAnalysisId);
					$("#targetProductionAnalysisId").text(targetProductionAnalysisId);
					
					
					$("#reworkQtyAnalysisId").text(reworkQtyAnalysisId + "%");
					$("#reworkQtyCurrentMonthAnalysisId").text(reworkQtyCurrentMonthAnalysisId);
					$("#reworkQtyLastMonthAnalysisId").text(reworkQtyLastMonthAnalysisId);
					
					$("#manufacturingCostAnalysisId").text(manufacturingCostAnalysisId + "%");
					$("#manufacturingCostCurrentMonthAnalysisId").text(manufacturingCostCurrentMonthAnalysisId);
					$("#manufacturingCostLastMonthAnalysisId").text(manufacturingCostLastMonthAnalysisId);
					
					$("#labourCostAnalysisId").text(labourCostAnalysisId + "%");
					$("#labourCostCurrentMonthAnalysisId").text(labourCostCurrentMonthAnalysisId);
					$("#labourCostLastMonthAnalysisId").text(labourCostLastMonthAnalysisId);
								
					
				},
				error: function(error) {
					console.error(error);
				}
			});
           
           
        } else {
            alert('No product selected');
        }
    });
});

function analysisHighChat(){
	
var org = $("#analysisOrganization").val();
var orgDiv = $("#analysisDivision").find('option:selected').text();
var location = $("#analysisLocation").find('option:selected').text();
var fromDate = $("#fromDate6").val();
var toDate = $("#toDate6").val();
var productSkuId = $('#productIdAnalysis').val();

const currentDate = new Date();

    // Current month and year
    const currentMonth = currentDate.getMonth() + 1; // Months are zero-indexed
    const currentYear = currentDate.getFullYear();
   // alert("Current Month: " + currentMonth + ", Current Year: " + currentYear);

    // Last month and year
    let lastMonth = currentMonth - 1;
    let lastYear = currentYear;

    if (lastMonth === 0) {
        lastMonth = 12; // December of the previous year
        lastYear -= 1;
    }
 	


$.ajax({
		url: "dashboard-analysis-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&productSkuId=" + productSkuId
		+ "&currentMonth=" + currentMonth+ "&currentYear=" + currentYear+ "&lastMonth=" + lastMonth
		+ "&lastYear=" + lastYear,
		
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
						   
			var quantityValueAnalysisId = allData[0].productQty;
			var actualProductionAnalysisId = allData[0].productProductionActual;
			var targetProductionAnalysisId = allData[0].productProductionEstimate;
			
			var reworkQtyAnalysisId = allData[0].reworkQty;
			var reworkQtyCurrentMonthAnalysisId = allData[0].reworkQtyCurrentMonth;
			var reworkQtyLastMonthAnalysisId = allData[0].reworkQtyLastMonth;
			
			var manufacturingCostAnalysisId = allData[0].manufacturingQtyPercentage;
			var manufacturingCostCurrentMonthAnalysisId = allData[0].manufacturingCostThisMonth;
			var manufacturingCostLastMonthAnalysisId = allData[0].manufacturingCostLastMonth;
			
			var labourCostAnalysisId = allData[0].labourCostPercentage;
			var labourCostCurrentMonthAnalysisId = allData[0].labourCostThisMonth;
			var labourCostLastMonthAnalysisId = allData[0].labourCostLastMonth;
			
			
			$("#quantityValueAnalysisId").text(quantityValueAnalysisId + "%");
			$("#actualProductionAnalysisId").text(actualProductionAnalysisId);
			$("#targetProductionAnalysisId").text(targetProductionAnalysisId);
			
			
			$("#reworkQtyAnalysisId").text(reworkQtyAnalysisId + "%");
			$("#reworkQtyCurrentMonthAnalysisId").text(reworkQtyCurrentMonthAnalysisId);
			$("#reworkQtyLastMonthAnalysisId").text(reworkQtyLastMonthAnalysisId);
			
			$("#manufacturingCostAnalysisId").text(manufacturingCostAnalysisId + "%");
			$("#manufacturingCostCurrentMonthAnalysisId").text(manufacturingCostCurrentMonthAnalysisId);
			$("#manufacturingCostLastMonthAnalysisId").text(manufacturingCostLastMonthAnalysisId);
			
			$("#labourCostAnalysisId").text(labourCostAnalysisId + "%");
			$("#labourCostCurrentMonthAnalysisId").text(labourCostCurrentMonthAnalysisId);
			$("#labourCostLastMonthAnalysisId").text(labourCostLastMonthAnalysisId);
						
			
		},
		error: function(error) {
			console.error(error);
		}
	});



$.ajax({
		url: "dashboard-analysis-runtime-downtime?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var machineNameList = [];
			var totalRunTimeInHoursList = [];
			var totalBreakDownInHoursList = [];
			
			for (var i = 0; i < allData.length; i++) {
				var item = allData[i];
				machineNameList.push(item.machineName);
				totalRunTimeInHoursList.push(item.totalRunTimeInHours);
				totalBreakDownInHoursList.push(item.totalBreakDownInHours);				
			}	
			
			//Run Time vs Downtime
Highcharts.chart('runTimeVsDowntime', {
            chart: {
                type: 'column',
                animation: true,
                height: 250,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                categories: machineNameList,

            },
            yAxis: {
                title: {
                    text: ''
                },
                min: 0,
                max: 500
            },
            plotOptions: {
                column: {
                pointPadding: 0,
                borderWidth:0
                },
                series: {
                    dataLabels: {
                        enabled: true,
                    }

                }
            },
            legend: { enabled: true, },

            series: [{
                name: 'Run Time',
                data: totalRunTimeInHoursList,
                color: '#bf05ff'
            }, {
                name: 'Downtime',
                data: totalBreakDownInHoursList,
                color: '#F79C92'
            }]
        });


					
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-analysis-production-cost?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			//alert("asghfasgfas")
			
		var monthList = [];
		var productionCostList = [];
		var allCostForTwelveMonth = 0;
		
				
		for (var i = 1; i < allData.length; i++) {				
			var item = allData[i];				
			monthList.push(item.monthYear);
			var productionExpenseValue = item.productionExpense/1000000;
		//	alert("productionExpenseValue-----------"+productionExpenseValue);
			productionCostList.push(parseInt(productionExpenseValue));
			allCostForTwelveMonth = allCostForTwelveMonth + productionExpenseValue;
		}	
			
			//alert("productionCostList-----------"+productionCostList);
			var averageCostForTwelveMonth = (parseInt(allCostForTwelveMonth/12));
			var averageCostForTwelveMonthList = [];
			
			for (var i = 0; i < 12; i++) {			
				averageCostForTwelveMonthList.push(averageCostForTwelveMonth);
			}
			



		    
		 Highcharts.chart('prodCostLastTwelveMonths', {
		            chart: {
		                // type: 'area',
		                // zoomType: 'xy',
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
		
		            xAxis: {
		        type: 'datetime',
		        accessibility: {
		            rangeDescription: 'Range: Jul 1st 2022 to Jul 31st 2022.'
		        }
		    },
		
		    yAxis: {
		        title: {
		            text: 'Production Cost'
		        }
		    },
		
		    tooltip: {
		        crosshairs: true,
		        shared: true,
		        valueSuffix: ''
		    },
		
		    plotOptions: {
		        series: {
		            pointStart: Date.UTC(2022, 6, 1),
		            pointIntervalUnit: 'day',
		            dataLabels: {
		                enabled: true
		            }
		        }
		    },
		
		    series: [{
		        name: 'Production Cost',
		      //  data: [10, 8, 20, 22, 30, 35, 50,null,null, null, null,null],
		        data: productionCostList,
		        color: '#bf05ff',
		    },{
		        name: 'Forecast',
		       // data: averages,
		        data: averageCostForTwelveMonthList,
		        zIndex: 1,
		        color: '#bf05ff',
		        marker: {
		            fillColor: 'white',
		            lineWidth: 2,
		            lineColor: '#bf05ff',
		            symbol: 'circle'
		        }
		    }, {
		        name: 'Upper and Lower bound',
		        data: '',
		        //data: ranges,
		        type: 'arearange',
		        lineWidth: 0,
		        linkedTo: ':previous',
		        // color: Highcharts.getOptions().colors[0],
				color: '#bf05ff',
		        fillOpacity: 0.3,
		        zIndex: 0,
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
		url: "dashboard-analysis-availability-performance-quality-effectiveness?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var performanceValue = 0;
			var availabilityValue = 0;
			var qualityValue = 0;
			var oeeValue = 0;
			
			
			performanceValue = allData[0].performance;
			availabilityValue = allData[0].availablity;
			qualityValue = allData[0].quality;
			oeeValue = allData[0].ooeCount;
			
			
			
		
		//production Analysis availability
		Highcharts.chart('productionAnalysisAvailability', {
		
		chart: {
		    type: 'gauge',
		    plotBackgroundColor: null,
		    plotBackgroundImage: null,
		    plotBorderWidth: 0,
		    plotShadow: false,
		    height: '71.2%'
		},
		
		title: {
		    text: availabilityValue + "%",
		    align: 'center',
		    verticalAlign: 'center',
		    floating: true,
		    y: 170,
		    margin: 0,
		    style: { "fontSize": '14', "color": '#000000' }
		},
		navigation: {
		    buttonOptions: {
		        enabled: false
		    }
		},
		credits: { enabled: false, },
		
		pane: {
		    startAngle: -98,
		    endAngle: 97.9,
		    background: null,
		    center: ['50%', '75%'],
		    size: '100%'
		},
		
		// the value axis
		yAxis: {
		    min: 0,
		    max: 100,
		    tickPixelInterval: 40,
		    tickPosition: 'inside',
		    tickColor: 'transparent',
		    tickLength: 0,
		    tickWidth: 0,
		    minorTickInterval: null,
		    labels: {
		        distance: 20,
		        style: {
		            fontSize: '11px'
		        }
		    },
		    lineWidth: 0,
		    plotBands: [{
		        from: 0,
		        to: 100,
		        color: '#CA7CE5',
		        thickness: 40
		    }]
		},
		
		series: [{
		    name: '',
		    data: [100],
		    tooltip: {
		        valueSuffix: ' km/h'
		    },
		    dataLabels: {
		        enabled: false,
		    },
		    dial: {
		        radius: '0',
		        backgroundColor: 'gray',
		        baseWidth: 12,
		        baseLength: '0%',
		        rearLength: '0%'
		    },
		    pivot: {
		        backgroundColor: 'gray',
		        radius: 0
		    }
		
		}]
		
		});
		//production Analysis performance
		Highcharts.chart('productionAnalysisPerformance', {
		
		chart: {
		    type: 'gauge',
		    plotBackgroundColor: null,
		    plotBackgroundImage: null,
		    plotBorderWidth: 0,
		    plotShadow: false,
		    height: '71.2%'
		},
		
		title: {
		    text: performanceValue + "%",
		    align: 'center',
		    verticalAlign: 'center',
		    floating: true,
		    y: 170,
		    margin: 0,
		    style: { "fontSize": '14', "color": '#000000' }
		},
		navigation: {
		    buttonOptions: {
		        enabled: false
		    }
		},
		credits: { enabled: false, },
		
		pane: {
		    startAngle: -98,
		    endAngle: 97.9,
		    background: null,
		    center: ['50%', '75%'],
		    size: '100%'
		},
		
		// the value axis
		yAxis: {
		    min: 0,
		    max: 100,
		    tickPixelInterval: 40,
		    tickPosition: 'inside',
		    tickColor: 'transparent',
		    tickLength: 0,
		    tickWidth: 0,
		    minorTickInterval: null,
		    labels: {
		        distance: 20,
		        style: {
		            fontSize: '11px'
		        }
		    },
		    lineWidth: 0,
		    plotBands: [{
		        from: 0,
		        to: 100,
		        color: '#bf05ff', // red
		        thickness: 40
		    }]
		},
		
		series: [{
		    name: '',
		    data: [100],
		    tooltip: {
		        valueSuffix: ' km/h'
		    },
		    dataLabels: {
		        enabled: false,
		    },
		    dial: {
		        radius: '0',
		        backgroundColor: 'gray',
		        baseWidth: 12,
		        baseLength: '0%',
		        rearLength: '0%'
		    },
		    pivot: {
		        backgroundColor: 'gray',
		        radius: 0
		    }
		
		}]
		
		});
		//production Analysis quality
		Highcharts.chart('productionAnalysisQuality', {
		
		chart: {
		    type: 'gauge',
		    plotBackgroundColor: null,
		    plotBackgroundImage: null,
		    plotBorderWidth: 0,
		    plotShadow: false,
		    height: '71.2%'
		},
		
		title: {
		    text: qualityValue + "%",
		    align: 'center',
		    verticalAlign: 'center',
		    floating: true,
		    y: 170,
		    margin: 0,
		    style: { "fontSize": '14', "color": '#000000' }
		},
		navigation: {
		    buttonOptions: {
		        enabled: false
		    }
		},
		credits: { enabled: false, },
		
		pane: {
		    startAngle: -98,
		    endAngle: 97.9,
		    background: null,
		    center: ['50%', '75%'],
		    size: '100%'
		},
		
		// the value axis
		yAxis: {
		    min: 0,
		    max: 100,
		    tickPixelInterval: 40,
		    tickPosition: 'inside',
		    tickColor: 'transparent',
		    tickLength: 0,
		    tickWidth: 0,
		    minorTickInterval: null,
		    labels: {
		        distance: 20,
		        style: {
		            fontSize: '11px'
		        }
		    },
		    lineWidth: 0,
		    plotBands: [{
		        from: 0,
		        to: 100,
		        color: '#F79C92', // green
		        thickness: 40
		    }]
		},
		
		series: [{
		    name: '',
		    data: [100],
		    tooltip: {
		        valueSuffix: ' km/h'
		    },
		    dataLabels: {
		        enabled: false,
		    },
		    dial: {
		        radius: '0',
		        backgroundColor: 'gray',
		        baseWidth: 12,
		        baseLength: '0%',
		        rearLength: '0%'
		    },
		    pivot: {
		        backgroundColor: 'gray',
		        radius: 0
		    }
		
		}]
		
		});
		//production Analysis oee
		Highcharts.chart('productionAnalysisOEE', {
		
		chart: {
		    type: 'gauge',
		    plotBackgroundColor: null,
		    plotBackgroundImage: null,
		    plotBorderWidth: 0,
		    plotShadow: false,
		    height: '71.2%'
		},
		
		title: {
		    text: oeeValue + "%",
		    align: 'center',
		    verticalAlign: 'center',
		    floating: true,
		    y: 170,
		    margin: 0,
		    style: { "fontSize": '14', "color": '#000000' }
		},
		navigation: {
		    buttonOptions: {
		        enabled: false
		    }
		},
		credits: { enabled: false, },
		
		pane: {
		    startAngle: -98,
		    endAngle: 97.9,
		    background: null,
		    center: ['50%', '75%'],
		    size: '100%'
		},
		
		// the value axis
		yAxis: {
		    min: 0,
		    max: 100,
		    tickPixelInterval: 40,
		    tickPosition: 'inside',
		    tickColor: 'transparent',
		    tickLength: 0,
		    tickWidth: 0,
		    minorTickInterval: null,
		    labels: {
		        distance: 20,
		        style: {
		            fontSize: '11px'
		        }
		    },
		    lineWidth: 0,
		    plotBands: [{
		        from: 0,
		        to: 100,
		        color: '#B422B6', // blue
		        thickness: 40
		    }]
		},
		
		series: [{
		    name: '',
		    data: [100],
		    tooltip: {
		        valueSuffix: ' km/h'
		    },
		    dataLabels: {
		        enabled: false,
		    },
		    dial: {
		        radius: '0',
		        backgroundColor: 'gray',
		        baseWidth: 12,
		        baseLength: '0%',
		        rearLength: '0%'
		    },
		    pivot: {
		        backgroundColor: 'gray',
		        radius: 0
		    }
		
		}]
		
		});

		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
		 
	
}