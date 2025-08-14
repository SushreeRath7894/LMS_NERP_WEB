function kpi() {
	
	var organization=$("#kpiOrgSelect").find('option:selected').text();
	var division=$("#kpiDivSelect").find('option:selected').text();
	var location=$("#kpiLocation").find('option:selected').text();
	var fromDate=$("#kpiFromDate").val();
	var toDate=$("#kpiToDate").val();
	
	
	 //Ajax for Ticket Module Dashboard Kpi First Response Time 
        $.ajax({
        url: "manage-dashboard-kpi-first-response-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });
	// First Response Time
	Highcharts.chart('helpdeskFirstResponseTime', {
		chart: {
			type: 'area',
			animation: true,
			height: 210,
		},
		title: {
			text: ''
		},
		subtitle: {
			text: '1h 38min'
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
			tickLength: 5,
			tickWidth: 1,
			categories: ['11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'],

		},
		yAxis: {
			title: {
				text: 'per daytime(hours)'
			},
		},
		legend: false,
		plotOptions: {
			area: {
				stacking: 'normal',
				lineColor: '#666666',
				lineWidth: 1,
				marker: {
					lineWidth: 1,
					lineColor: '#666666'
				}
			}
		},
		series: [{
			type: 'area',
			name: '',
			data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214, 14325, 18953, 14325],
			marker: { enabled: false },
			color: '#e3cff8',
		}, {
			type: 'line',
			name: '',
			dashStyle: 'ShortDash',
			data: [10000, 9700, 9400, 9100, 8800, 8500, 8200, 7900, 7600, 7300, 7000, 6700],
			marker: { enabled: false },
			color: '#BF05FF',
			showInLegend: false
		}]
	});



 //Ajax for Ticket Module Dashboard Kpi Full Resolution Time 
        $.ajax({
        url: "manage-dashboard-kpi-full-resolution-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });

	// Full Resolution Time 
	Highcharts.chart('helpdeskFullResolutionTime', {
		chart: {
			type: 'area',
			animation: true,
			height: 210,
		},
		title: {
			text: ''
		},
		subtitle: {
			text: '2h 34min'
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
			tickLength: 5,
			tickWidth: 1,
			categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],

		},
		yAxis: {
			title: {
				text: 'per weekday(hours)'
			},
		},
		legend: false,
		plotOptions: {
			area: {
				stacking: 'normal',
				lineColor: '#666666',
				lineWidth: 1,
				marker: {
					lineWidth: 1,
					lineColor: '#666666'
				}
			}
		},
		series: [{
			type: 'area',
			name: '',
			data: [13234, 12729, 11533, 17798, 10398, 12811, 15483],
			marker: { enabled: false },
			color: '#FFECEA',
		}, {
			type: 'line',
			name: '',
			dashStyle: 'ShortDash',
			data: [10000, 9700, 9400, 9100, 8800, 8500, 8200],
			marker: { enabled: false },
			color: '#F79C92',
			showInLegend: false
		}]
	});


 //Ajax for Ticket Module Dashboard Kpi Average Answer Time 
        $.ajax({
        url: "manage-dashboard-kpi-average-answer-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });

	// Average Answer Time
	Highcharts.chart('helpdeskAverageAnswerTime', {

		chart: {
			type: 'gauge',
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 140
		},

		title: {
			text: ''
		},
		navigation: {
			buttonOptions: {
				enabled: false
			}
		},
		credits: { enabled: false, },

		pane: {
			startAngle: -140,
			endAngle: 139.9,
			background: null,
			center: ['50%', '75%'],
			size: '100%'
		},

		// the value axis
		yAxis: {
			min: -100,
			max: 100,
			// tickPixelInterval: 72,
			tickPosition: 'inside',
			tickColor: 'transparent',
			tickLength: 20,
			tickWidth: 2,
			minorTickInterval: null,
			labels: {
				// distance: 20,
				// style: {
				//     fontSize: '11px'
				// }
				enabled: false,
			},
			lineWidth: 0,
			plotBands: [{
				from: -100,
				to: 0,
				color: '#bf05ff', // green
				thickness: 20
			}, {
				from: 0,
				to: 50,
				color: '#9792e8', // yellow
				thickness: 20
			}, {
				from: 50,
				to: 100,
				color: '#f58d68', // red
				thickness: 20
			}]
		},

		series: [{
			name: '',
			data: [1580],
			dataLabels: {
				enabled: false,
			},
			dial: {
				radius: '80%',
				backgroundColor: '#000000',
				baseWidth: 8,
				baseLength: '0%',
				rearLength: '0%'
			},
			pivot: {
				backgroundColor: '#000000',
				radius: 4
			}

		}]

	});

 //Ajax for Ticket Module Dashboard Kpi Ticket by Type
 $.ajax({
    url: "manage-dashboard-kpi-ticket-by-type?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        var jsonData = JSON.parse(response.body);
		var ticketTypeData = jsonData.ticketByTypeData;
		
		var chartData = ticketTypeData.map(function(item) {
		    return [item.ticketName, item.typeCount];
		});
		
		var totalTypeCount = 0;
		$.each(ticketTypeData, function(index, item) {
		    totalTypeCount += item.typeCount;
		});
		
		var tableBody = $('#ticketByTypeTbodyId');
		tableBody.empty();
		var colors = ['#CA7CE5', '#bf05ff', '#B422B6', '#56156C', '#F79C92'];
		
		ticketTypeData.forEach(function(ticket, index) {
		    var countVal = ticket.typeCount;
		    var percentageValue = 0;
		
		    if (countVal != 0) {
		        percentageValue = (countVal / totalTypeCount) * 100;
		    }
		
		    var color = colors[index % colors.length];
		    var row = '<tr>' +
		        '<td style="width: 80%">' +
		        '<div>' +
		        '<span style="color: ' + color + '; font-size: 9px"><i class="fa-solid fa-circle" aria-hidden="true"></i></span>' +
		        ticket.ticketName +
		        '</div>' +
		        '</td>' +
		        '<td style="width: 20%">' +
		        percentageValue.toFixed(2) + "%" + '</td>' +
		        '</tr>';
		    tableBody.append(row);
		});

       
		 
		 
        
       // alert("chartData--------"+chartData);

        Highcharts.chart('helpdeskTicketsByType', { 
            chart: {
                type: 'pie',
                height: 210,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: null,
            },
            subtitle: {
                text: null,
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
                    shadow: false,
                    colors: [
                        '#CA7CE5',
                        '#bf05ff',
                        '#B422B6',
                        '#56156C',
                        '#F79C92'
                    ]
                }
            },
            legend: {
                itemStyle: {
                    fontSize: '10px',
                },
                enabled: false
            },
            series: [{
                name: 'Tickets',
                data: chartData,
                size: '120%',
                innerSize: '60%',
                showInLegend: true,
                dataLabels: {
                    enabled: true,
                    format: '{y}',
                    distance: -3,
                }
            }]
        });
    },
    error: function(error) {
        console.error(error);
    }
});

 //Ajax for Ticket Module Dashboard Kpi Ticket By Category
 
 $.ajax({
    url: "manage-dashboard-kpi-ticket-by-category?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        var jsonData = JSON.parse(response.body);
        var ticketCategoryData = jsonData.ticketByCategoryData;

       
        var categoryChartData = ticketCategoryData.map(function(item) {
            return [item.ticketName, item.categoryCount];
        });
        
        
        
        
        var totalCatCount = 0;
		$.each(ticketCategoryData, function(index, item) {
		    totalCatCount += item.categoryCount;
		});
		
		var tableBody = $('#ticketByCategoryTbodyId');
		tableBody.empty();
		var colors = ['#CA7CE5', '#bf05ff', '#B422B6', '#56156C', '#F79C92', '#0683d6', '#a81123'];
		
		ticketCategoryData.forEach(function(ticket, index) {
		    var countVal = ticket.categoryCount;
		    var percentageValue = 0;
		
		    if (countVal != 0) {
		        percentageValue = (countVal / totalCatCount) * 100;
		    }
		
		    var color = colors[index % colors.length];
		    var row = '<tr>' +
		        '<td style="width: 80%">' +
		        '<div>' +
		        '<span style="color: ' + color + '; font-size: 9px"><i class="fa-solid fa-circle" aria-hidden="true"></i></span>' +
		        ticket.ticketName +
		        '</div>' +
		        '</td>' +
		        '<td style="width: 20%">' +
		        percentageValue.toFixed(2) + "%" + '</td>' +
		        '</tr>';
		    tableBody.append(row);
		});

        
        Highcharts.chart('ticketByCategory', {
            chart: {
                type: 'pie',
                height: 210,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: null,
            },
            subtitle: {
                text: null,
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
                    shadow: false,
                    colors: [
                        '#CA7CE5',
                        '#bf05ff',
                        '#B422B6',
                        '#56156C',
                        '#F79C92',
                        '#0683d6',
                        '#a81123'
                    ]
                }
            },
            legend: {
                itemStyle: {
                    fontSize: '10px',
                },
            },
            series: [{
                name: 'Tickets',
                data: categoryChartData,
                size: '120%',
                innerSize: '60%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    format: '{y}',
                    distance: -3,
                }
            }]
        });
    },
    error: function(error) {
        console.error(error);
    }
});


 //Ajax for Ticket Module Dashboard Kpi Call By Answer Time 
        $.ajax({
        url: "manage-dashboard-kpi-call-answer-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });

	//Calls by answer time brackets
	Highcharts.chart('helpdeskCallsByAnswerTimeBrackets', {
		chart: {
			animated: true,
			height: 300
		},
		title: {
			text: ''
		},
		navigation: {
			buttonOptions: {
				enabled: false
			}
		},
		credits: { enabled: false, },
		xAxis: {
			categories: ['>300 sec', '60-300 sec', '30-60 sec', '15-30 sec', '5-15 sec', '0-5 sec', 'Not Recorded'],
			labels: {
				style: {
					fontSize: '10px'
				}
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			labels: { enabled: false, },
			lineColor: 'transparent',
			gridLineColor: 'transparent'
		},
		legend: { enabled: false, },

		series: [{
			type: 'bar',
			name: 'Actual',
			data: [59, 83, 65, 28, 32, 45, 32],
			color: '#BF05FF',
			dataLabels: {
				enabled: true,
				format: '{y}%'
			},
		}]
	});


// AJAX for Ticket Module Dashboard KPI Net Promoter Score 
$.ajax({
    url: "manage-dashboard-kpi-net-promoter-score?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        try {
            // Parse the response correctly
            var jsonData = JSON.parse(response.body[1]);
            var kpiPromoterScore = jsonData;

            // Extract values
            var total = kpiPromoterScore.total;
            var promoters = kpiPromoterScore.promoters;
            var passives = kpiPromoterScore.passives;
            var detractors = kpiPromoterScore.detractors;
            var promotersPercent = kpiPromoterScore.promotersPercent;
            var detractorsPercent = kpiPromoterScore.detractorsPercent;
            var netPromoterScore = kpiPromoterScore.netPromoterScore;

             
            if ($('#helpdeskNetPromoterScoreGauge').length) {
                // Net Promoter Score
                Highcharts.chart('helpdeskNetPromoterScoreGauge', {
                    chart: {
                        type: 'gauge',
                        plotBackgroundColor: null,
                        plotBackgroundImage: null,
                        plotBorderWidth: 0,
                        plotShadow: false,
                        height: 116
                    },
                    title: {
                        text: netPromoterScore.toFixed(2),  
                        align: 'center',
                        verticalAlign: 'center',
                        floating: true,
                        y: 90,
                        margin: 0,
                        style: { "fontSize": '14px', "color": '#005c9f' }
                    },
                    navigation: {
                        buttonOptions: {
                            enabled: false
                        }
                    },
                    credits: { enabled: false },
                    pane: {
                        startAngle: -95,
                        endAngle: 94.9,
                        background: null,
                        center: ['50%', '75%'],
                        size: '100%'
                    },
                    yAxis: {
                        min: -100,  
                        max: 100,
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
                            from: -100,
                            to: 0,
                            color: '#bf05ff', 
                            thickness: 20
                        }, {
                            from: 0,
                            to: 30,
                            color: '#9792e8',  
                            thickness: 20
                        }, {
                            from: 30,
                            to: 100,
                            color: '#f58d68', 
                            thickness: 20
                        }]
                    },
                    series: [{
                        name: 'NPS',
                        data: [netPromoterScore],  
                        tooltip: {
                            valueSuffix: ''
                        },
                        dataLabels: {
                            enabled: false,
                        },
                        dial: {
                            radius: '80%',
                            backgroundColor: '#000000',
                            baseWidth: 6,
                            baseLength: '0%',
                            rearLength: '0%'
                        },
                        pivot: {
                            backgroundColor: '#000000',
                            radius: 3
                        }
                    }]
                });
            }
            
            if ($('#promotersCount').length) $('#promotersCount').text(promoters + '(' + promotersPercent.toFixed(2) + '%)');
            if ($('#passivesCount').length) $('#passivesCount').text(passives + '(' + ((passives / total) * 100).toFixed(2) + '%)');
            if ($('#detractorsCount').length) $('#detractorsCount').text(detractors + '(' + detractorsPercent.toFixed(2) + '%)');
            if ($('#netPromoterScore').length) $('#netPromoterScore').text(netPromoterScore.toFixed(2));

        } catch (e) {
            console.error("Error processing response:", e);
        }
    },
    error: function(error) {
        console.error("AJAX Error:", error);
    }
});


	 

 //Ajax for Ticket Module Dashboard Kpi Customer Retention Gauge
        $.ajax({
        url: "manage-dashboard-kpi-customer-retention?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });

	// Customer Retention
	Highcharts.chart('helpdeskCustomerRetentionGauge', {

		chart: {
			type: 'gauge',
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 116
		},

		title: {
			text: '92%',
			align: 'center',
			verticalAlign: 'center',
			floating: true,
			y: 90,
			margin: 0,
			style: { "fontSize": '14', "color": '#005c9f' }
		},
		navigation: {
			buttonOptions: {
				enabled: false
			}
		},
		credits: { enabled: false, },

		pane: {
			startAngle: -95,
			endAngle: 94.9,
			background: null,
			center: ['50%', '75%'],
			size: '100%'
		},

		// the value axis
		yAxis: {
			min: 0,
			max: 100,
			// tickPixelInterval: 72,
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
				to: 30,
				color: '#bf05ff', // green
				thickness: 20
			}, {
				from: 30,
				to: 70,
				color: '#9792e8', // yellow
				thickness: 20
			}, {
				from: 70,
				to: 100,
				color: '#f58d68', // red
				thickness: 20
			}]
		},

		series: [{
			name: '',
			data: [60],
			tooltip: {
				valueSuffix: ' km/h'
			},
			dataLabels: {
				enabled: false,
			},
			dial: {
				radius: '80%',
				backgroundColor: '#000000',
				baseWidth: 6,
				baseLength: '0%',
				rearLength: '0%'
			},
			pivot: {
				backgroundColor: '#000000',
				radius: 3
			}

		}]

	});
	
	//Customer Retention Column
	Highcharts.chart('helpdeskCustomerRetentionColumn', {
		chart: {
			type: 'column',
			height: 146,
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
			text: null,
		},
		subtitle: {
			text: null,
		},
		xAxis: {
			type: 'category',
		},
		yAxis: {
			min: 0,
			gridLineColor: 'transparent',
			title: {
				text: ''
			},
			format: '{y}%'
		},
		legend: {
			enabled: false
		},
		series: [{
			name: '',
			colors: ['#ef6909'],
			colorByPoint: true,
			groupPadding: 0,
			pointWidth: 10,
			data: [
				['Jan 2016', 15],
				['Feb 2016', 20],
				['Mar 2016', 25],
				['Apr 2016', 30],
				['May 2016', 35],
				['Jun 2016', 15],
				['Jul 2016', 20],
				['Aug 2016', 25],
				['Sep 2016', 26],
				['Oct 2016', 32],
				['Nov 2016', 18],
				['Dec 2016', 35],
			],
			dataLabels: {
				enabled: false,
			}
		}]
	});



 //Ajax for Ticket Module Dashboard Kpi Customer Effort Score Area 
        $.ajax({
        url: "manage-dashboard-kpi-customer-effort-score?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });

	//Customer Effort Score Area
	Highcharts.chart('helpdeskCustomerEffortScoreArea', {
		chart: {
			type: 'area',
			animation: true,
			height: 146,
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
			categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
		},
		yAxis: {
			gridLineColor: 'transparent',
			title: {
				text: ''
			},
			labels: {
				enabled: false
			}
		},
		series: [{
			type: 'area',
			name: '',
			color: '#F5DCFE',
			lineColor: '#078771',
			lineWidth: 1,
			data: [1.2, 2.4, 3, 2, 1.7, 2, 2.8, 3.5, 1.9],
			marker: {
				enabled: true, fillColor: '#F58D68', lineWidth: 1,
				lineColor: '#F58D68'
			},
			dataLabels: { enabled: true },
			showInLegend: false
		}]
	});

 
	// Customer Effort Score
	Highcharts.chart('helpdeskCustomerEffortScore', {

		chart: {
			type: 'gauge',
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 116
		},

		title: {
			text: '1.7',
			align: 'center',
			verticalAlign: 'center',
			floating: true,
			y: 100,
			margin: 0,
			style: { "fontSize": '14', "color": '#005c9f' }
		},
		navigation: {
			buttonOptions: {
				enabled: false
			}
		},
		credits: { enabled: false, },

		pane: {
			startAngle: -140,
			endAngle: 139.9,
			background: null,
			center: ['50%', '75%'],
			size: '100%'
		},

		// the value axis
		yAxis: {
			min: -100,
			max: 100,
			// tickPixelInterval: 72,
			tickPosition: 'inside',
			tickColor: 'transparent',
			tickLength: 20,
			tickWidth: 2,
			minorTickInterval: null,
			labels: {
				// distance: 20,
				// style: {
				//     fontSize: '11px'
				// }
				enabled: false,
			},
			lineWidth: 0,
			plotBands: [{
				from: -100,
				to: 0,
				color: '#bf05ff', // green
				thickness: 20
			}, {
				from: 0,
				to: 50,
				color: '#9792e8', // yellow
				thickness: 20
			}, {
				from: 50,
				to: 100,
				color: '#f58d68', // red
				thickness: 20
			}]
		},

		series: [{
			name: '',
			data: [1580],
			dataLabels: {
				enabled: false,
			},
			dial: {
				radius: '80%',
				backgroundColor: '#000000',
				baseWidth: 8,
				baseLength: '0%',
				rearLength: '0%'
			},
			pivot: {
				backgroundColor: '#000000',
				radius: 4
			}

		}]

	});
}

function getKpiFilterData(){
	kpi();
}

function resetKpiOperation(){
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#kpiFromDate").val(fromDate);
	$("#kpiToDate").val(toDate);
	getKpiFilterData();
}