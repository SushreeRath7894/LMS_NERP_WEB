function performanceHighChart(){
	
	var org = $("#performaSalesOrganization").find('option:selected').text();
	var orgDiv = $("#performaSalesDivision").find('option:selected').text();
	var loc = $("#performaSalesLocation").val();
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();	
	
	//customer-dashboard-performance-count
	$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var totalCustomer = allData[0].totalCustomer;		
				var totalRevenue = allData[0].salesRevenueTotal;		
				var totalProfitLoss = allData[0].totalProfitLoss;	
				
			    $("#new_customer").text(totalCustomer);
				$("#sales_review").text(totalRevenue);
				$("#sales_profit").text(totalProfitLoss);	
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
	

//customer-dashboard-performance-weekly-sales-revenue
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-weekly-sales-revenue",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
			
				var revenueList = [];
				var weekList = [];			
				$.each(allData, function(index, data) {						
					weekList.push(data.week_number);
					revenueList.push(data.revenueTotal);
				});
							
				Highcharts.chart('performanceDashboard', {
			    chart: {
			        type: 'column',
			        backgroundColor: 'transparent',
			        height: 250,
			        marginTop: 30,
			        animation: true,
			    },
			    credits: {
			        enabled: false
			    },
			    navigation: {
			        buttonOptions: {
			            enabled: false
			        }
			    },
			    title: {
			        text: 'Weekly Sales Revenue', // Static Title
			        style: {
			            color: '#bf05ff',
			            fontSize: '20px'
			        }
			    },
			    subtitle: {
			       // text: 'AVERAGE WEEKLY SALES REVIEW',
			        style: {
			            color: '#bf05ff',
			            fontSize: '16px'
			        }
			    },
			    xAxis: {
			        categories: weekList,//['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'], // Static weeks
			        tickLength: 0,
			        lineColor: '#000000',
			        labels: {
			            rotation: -90,
			            style: {
			                fontSize: '10px',
			                fontFamily: 'Verdana, sans-serif',
			                color: '#000000'
			            }
			        }
			    },
			    yAxis: {
			        min: 0,
			        gridLineColor: 'transparent',
			        title: {
			            text: 'Sales Revenue'
			        },
			        labels: {
			            enabled: true
			        }
			    },
			    legend: {
			        enabled: false
			    },
			    tooltip: {
			        pointFormat: 'Weekly sales: <b>${point.y:.1f} thousand</b>' // Static tooltip
			    },
			    series: [{
			        name: 'Weekly Sales Revenue',
			        colors: ['#bf05ff'],
			        colorByPoint: true,
			        groupPadding: 0,
			        pointWidth: 7,
			        data: revenueList,//[12, 15, 18, 10, 20], // Static weekly sales data
			        dataLabels: {
			            enabled: true, // Enable data labels for visibility
			            style: {
			                color: '#04427d'
			            }
			        }
			    }]
			});
					
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
//customer-dashboard-performance-state-wise-performance
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-state-wise-performance",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				const colorList = [
				    '#9B4DFF',  // Purple shade
				    '#FF00FF',  // Magenta shade
				    '#D200FF',  // Deep pink shade
				    '#FF7F80',  // Light pink shade
				    '#800080'   // Dark purple shade
				];
				
				// Dynamically generate statelist based on the allData
				let statelist = allData.map((item, index) => ({
				    name: item.state,
				    y: item.salesPercentageInThisState,
				    color: colorList[index] || '#000000' // Default to black if out of bounds
				}));
				
				// Now use the dynamically generated statelist in the series
				var series = [{
				    name: 'Revenue Share',
				    data: statelist,
				    colorByPoint: true  // Ensures each slice uses the assigned color
				}];
				
				Highcharts.chart('salesCountryPerformance', {
				    chart: {
				        type: 'pie',
				        animation: true,
				        backgroundColor: 'transparent',
				        height: 280,
				    },
				    title: {
				        text: null
				    },
				    credits: {
				        enabled: false
				    },
				    exporting: {
				        enabled: false // Disables the menu button
				    },
				    plotOptions: {
				        pie: {
				            dataLabels: {
				                enabled: true, // Enable data labels to display percentages
				                format: '{point.name}: {point.percentage:.1f}%', // Display name and percentage
				                style: {
				                    fontSize: '12px',
				                    color: '#333'
				                }
				            },
				            showInLegend: true
				        }
				    },
				    legend: {
				        enabled: true,
				        floating: false,
				        borderWidth: 0,
				        align: 'center',
				        layout: 'vertical',
				        verticalAlign: 'bottom',
				        margin: 0,
				        padding: 0,
				        labelFormatter: function () {
				            // Accessing the color from the color list
				            var color = colorList[this.index] || this.color;
				            return '<span style="color:' + color + '">' + this.name + '</span>: <b>' + this.y + '%</b>';
				        }
				    },
				    series: series,
				});

	
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
//customer-dashboard-performance-accumulated-revenue
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-accumulated-revenue",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var monthYearList = [];
				var revenueList = [];
				
				// Reverse the data array
				allData.reverse();
				
				// Loop through reversed data
				$.each(allData, function(index, data) {
				    // Extract month name and revenue
				    monthYearList.push(data.month_name); // No change here, it's already in "Month - Year" format
				    revenueList.push(data.revenueTotalInThisMonth);
				});
				
				// Generate dynamic datalist
				var datalist = monthYearList.map(function(monthName, index) {
				    return {
				        name: monthName,  // Directly use the full "Month - Year" string
				        y: revenueList[index]
				    };
				});


				// Highcharts configuration
				Highcharts.chart('accumulatedRevenue', {
				    chart: {
				        type: 'column',
				        backgroundColor: 'transparent',
				        height: 150,
				        marginTop: 30,
				        animation: true,
				    },
				    navigation: {
				        buttonOptions: {
				            enabled: false
				        }
				    },
				    credits: {
				        enabled: false
				    },
				    title: {
				        text: '',
				    },
				    subtitle: {
				        text: ''
				    },
				    xAxis: {
				        type: 'category',
				        tickLength: 0,
				        lineColor: 'transparent',
				        labels: {
				            enabled: true // Enable labels for months (which now include the year)
				        }
				    },
				    yAxis: {
				        min: 0,
				        gridLineColor: 'transparent',
				        title: {
				            text: ''
				        },
				        labels: {
				            enabled: false
				        }
				    },
				    legend: {
				        enabled: false
				    },
				    series: [{
				        name: 'Average monthly sales',
				        colors: ['#bf05ff'],
				        colorByPoint: true,
				        groupPadding: 0,
				        pointWidth: 10,
				        data: datalist,
				        dataLabels: {
				            enabled: false,
				        }
				    }]
				});
					
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
//customer-dashboard-performance-average-revenue-per-order
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-average-revenue-per-order",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var monthYearList = [];
				var revenueList = [];
				
				// Reverse the data array
				allData.reverse();
				
				// Loop through reversed data
				$.each(allData, function(index, data) {
				    // Extract month name and revenue
				    monthYearList.push(data.month_name); // No change here, it's already in "Month - Year" format
				    revenueList.push(data.revenueTotalInThisMonth);
				});
				
				// Generate dynamic datalist
				var datalist = monthYearList.map(function(monthName, index) {
				    return {
				        name: monthName,  // Directly use the full "Month - Year" string
				        y: revenueList[index]
				    };
				});
				
				
		Highcharts.chart('averageRevenuePerUnit', {
            chart: {
                type: 'areaspline',
                backgroundColor: 'transparent',
                height: 100,
                animation: true,
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
            legend: {
                enabled: false
            },
            xAxis: {
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                visible: false,
            },
            yAxis: {
                title: {
                    text: ''
                },
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            tooltip: {
                enabled: false,
                shared: true,
                // headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                series: {
                    pointStart: 2000
                },
                areaspline: {
                    fillOpacity: 0.5,
                    color: '#bf05ff'
                },
            },
            series: [{
                name: monthYearList,
                data: revenueList,
                dataLabels: {
                    enabled: false,
                }
            },]
        }); 
        	
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
//customer-dashboard-performance-life-time-value
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-life-time-value",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {				
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
				    var monthYearList = [];
					var revenueList = [];							
					for (var i = allData.length - 1; i >= 0; i--) {
					    var data = allData[i];
					    monthYearList.push(data.month_name);
					    revenueList.push(data.revenueTotalInThisMonth);
					}
				
					Highcharts.chart('customerLifetimeValue', {
				    chart: {
				        type: 'line',
				        animation: true,
				        backgroundColor: 'transparent',
				        height: 100
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
				    xAxis: {
				        lineColor: 'transparent',
				        labels: {
				            enabled: true
				        },
				        showInLegend: false,
				        categories: monthYearList,// ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
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
				                enabled: true
				            },
				            showInLegend: false,
				            enableMouseTracking: false
				        }
				    },
				    series: [{
				        name: 'Customer Lifetime Value',
				        data: revenueList,//[1000, 1200, 1400, 1100, 900, 850, 1300, 1500, 1700, 1600, 1800, 2000],
				        color: '#F79C92',
				        showInLegend: false,
				    }]
				});		
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
//customer-dashboard-performance-acquisition-cost
$.ajax({
		type: "GET",
		url: "customer-dashboard-performance-acquisition-cost",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc			
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
var allData = jsonData.dashboardData;
var monthYearList = [];
var revenueList = [];

// Reverse the data array
allData.reverse();

// Loop through reversed data
$.each(allData, function(index, data) {
    // Extract month name and revenue
    monthYearList.push(data.month_name); // No change here, it's already in "Month - Year" format
    revenueList.push(data.revenueTotalInThisMonth);
});

// Generate dynamic datalist
var datalist = monthYearList.map(function(monthName, index) {
    return {
        name: monthName,  // Directly use the full "Month - Year" string
        y: revenueList[index]
    };
});
				
				
Highcharts.chart('customerAcquisitionCost', {
    chart: {
        type: 'column',
        backgroundColor: 'transparent',
        height: 100,
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },
    colors: ['#bf05ff'],
    credits: false,
    title: {
        text: ''
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        type: 'category',
        title: {
            text: ""
        },
        labels: {
            enabled: true
        },
        lineColor: 'transparent'
    },
    yAxis: {
        title: {
            text: ''
        },
        labels: {
            enabled: false
        },
        gridLineColor: 'transparent',
    },
    legend: {
        enabled: false
    },
    tooltip: {
        enabled: false
    },
    series: [{
        name: 'IPS',
        data: datalist,
        dataLabels: {
            enabled: true,
            rotation: 0,
            color: '#000000',
            align: 'right',
            y: 0, // 10 pixels down from the top
            style: {
                fontSize: '10px'
            }
        }
    }]
});		
			}
		},
		error: function(data) {
			console.log(data);
		}
	});



  
								
    Highcharts.chart('aboveSalesTarget', {
        chart: {
            type: 'pie',
            animation: true,
            backgroundColor: 'transparent',
            height: 250,
        },navigation: {
            buttonOptions: {
                enabled: false
            }
        },
        credits: {
            enabled: false,
        },
        plotOptions: {
            pie: {
                innerSize: '60%',
                dataLabels: {
                    distance: '-30%',
                    enabled: false
                },
                showInLegend: true,
                colors: [
                    '#bf05ff',
                    '#F79C92',
                ],
            }

        },
        title: {
            text: '124,924',
            style: {
                color: '#bf05ff',
                fontSize: '18px'
            }
        },
        subtitle: {
            text: 'ABOVE SALES TARGET | YTD',
            style: {
                color: '#000000',
                fontSize: '14px'
            }
        },
        series: [{
            showInLegend: false,
            data: [
                ['x', 80],
                ['y', 20],
            ],
        }]
    })
			      
        
}