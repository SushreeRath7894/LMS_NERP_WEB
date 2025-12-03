function quality() {

	var organization = $("#qualityOrgSelect").find('option:selected').text();
	var division = $("#qualityDivSelect").find('option:selected').text();
	var fromDate = $("#qualityfromDate").val();
	var toDate = $("#qualitytoDate").val();
	var location = $("#qualityLocation").find('option:selected').text();
	
	
		//Ajax for Ticket Module Dashboard quality Phone Help Desk
	$.ajax({
    url: "manage-dashboard-quality-gauge-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division + "&location=" + location,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        
        var jsonData = JSON.parse(response.body);
        
        var totalTickets = jsonData.qualityGaugeData.reduce(function(total, item) {
            return total + item.typeCount;
        }, 0);

        var gaugeData = {
            'PHONE': 0,
            'EMAIL': 0,
            'CHAT': 0,
            'SOCIAL': 0,
            'VOICEMAIL': 0
        };

        var countData = {
            'PHONE': 0,
            'EMAIL': 0,
            'CHAT': 0,
            'SOCIAL': 0,
            'VOICEMAIL': 0
        };

        jsonData.qualityGaugeData.forEach(function(item) {
            if (totalTickets > 0) {
                const percentage = (item.typeCount / totalTickets) * 100;
                gaugeData[item.ticketSource] = parseFloat(percentage.toFixed(2));
                countData[item.ticketSource] = item.typeCount;
            }
        });

     function createGauge(containerId, data, count) {
    var container = $('#' + containerId);
    container.html('<div class="gauge-count"><strong>' + count  + '</strong></div><div class="gauge-chart"></div>');

    Highcharts.chart(container.find('.gauge-chart')[0], {
        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false,
            height: 116
        },
        title: {
            text: ''
        },
        navigation: {
            buttonOptions: {
                enabled: false
            }
        },
        credits: { enabled: false },
        pane: {
            startAngle: -120,
            endAngle: 119.9,
            background: null,
            center: ['50%', '75%'],
            size: '100%'
        },
        yAxis: {
            min: -100,
            max: 100,
            tickPosition: 'inside',
            tickColor: 'transparent',
            tickLength: 20,
            tickWidth: 2,
            minorTickInterval: null,
            labels: {
                enabled: false // Disable the labels
            },
            lineWidth: 0,
            plotBands: [{
                from: -100,
                to: 0,
                color: '#BF05FF',
                thickness: 20
            }, {
                from: 0,
                to: 50,
                color: '#9792E8',
                thickness: 20
            }, {
                from: 50,
                to: 100,
                color: '#F58D68',
                thickness: 20
            }]
        },
        series: [{
            name: name,
            data: [data],
            tooltip: {
                valueSuffix: '%'
            },
            dataLabels: {
                enabled: false
            },
            dial: {
                radius: '80%',
                backgroundColor: 'gray',
                baseWidth: 12,
                baseLength: '0%',
                rearLength: '0%'
            },
            pivot: {
                backgroundColor: 'gray',
                radius: 6
            }
        }]
    });
}

        // Create gauges for each source
        createGauge('helpdeskQualityPhone', gaugeData['PHONE'] , countData['PHONE']);
        createGauge('helpdeskQualityEmail', gaugeData['EMAIL'] , countData['EMAIL']);
        createGauge('helpdeskQualityLiveChat', gaugeData['CHAT'] , countData['CHAT']);
        createGauge('helpdeskQualitySocialMedia', gaugeData['SOCIAL'] , countData['SOCIAL']);
        createGauge('helpdeskQualityVoicemail', gaugeData['VOICEMAIL'] , countData['VOICEMAIL']);
    },
    error: function(error) {
        console.error(error);
    }
});

$.ajax({
		url: "manage-dashboard-quality-gauge-sub-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division + "&location=" + location,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});



	//Ajax for Ticket Module Dashboard quality Help Desk Average Resolution Time
	$.ajax({
		url: "manage-dashboard-quality-average-resolution-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division + "&location=" + location,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});
	//Average Resolution Time in Minutes
	Highcharts.chart('helpdeskAverageResolutionTimeinMinutes', {
		chart: {
			type: 'area',
			animation: true,
			height: 200,
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
		yAxis: {
			title: {
				text: ''
			}
		},
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
			name: 'Special Request',
			data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214],
			marker: { enabled: false },
			color: '#BF05FF'
		}, {
			type: 'area',
			name: 'Standard Request',
			data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039], marker: { enabled: false },
			color: '#F58D68'
		}, {
			type: 'line',
			name: '',
			data: [7200, 7200, 7200, 7200, 7200, 7200, 7200, 7200, 7200],
			marker: { symbol: 'circle', radius: 4 }, color: '#EF6909', showInLegend: false
		}, {
			type: 'line',
			name: '',
			data: [11000, 11000, 11000, 11000, 11000, 11000, 11000, 11000, 11000], marker: { symbol: 'circle', radius: 4 }, color: '#EEBDFF', showInLegend: false
		}]
	});



//Ajax for Ticket Module Dashboard quality Help Desk Average
$.ajax({
    url: "manage-dashboard-quality-help-desk?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division + "&location=" + location,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        var jsonData = JSON.parse(response.body).qualityHelpDesk;

        var emailData = [];
        var phoneData = [];
        var chatData = [];
        var socialData = [];
        var voicemailData = [];
        var totalTickets = 0;
        var totalSolvedTickets = 0;
        var totalResolutionTime = 0;
        var totalCpr = 0;
        var totalAvgCostResolution = 0;
        var totalAvgResolutionTime = 0;

        jsonData.forEach(function(source) {
            var data = source.yearlyData.sort((a, b) => a.year - b.year).map(function(yearData) {
                return {
                    year: yearData.year,
                    typeCount: yearData.typeCount,
                    closedCount: yearData.closedCount,
                    resolutionTime: yearData.avgResolutionTime || 0,
                    costPerResolution: parseFloat(yearData.costPerResolution.replace(/[^0-9.-]+/g,"")) || 0
                };
            });

            switch (source.ticketSource) {
                case "EMAIL":
                    emailData = data;
                    $('#emailTotalTickets').text(source.typeCount);
                    $('#emailSolvedTickets').text(source.closedCount);
                    $('#emailResolutionTime').text(source.avg_total_time_diff_in_minute);
                    $('#emailCpr').text(source.avgCostResolution.toFixed(2));
                    break;
                case "PHONE":
                    phoneData = data;
                    $('#phoneTotalTickets').text(source.typeCount);
                    $('#phoneSolvedTickets').text(source.closedCount);
                    $('#phoneResolutionTime').text(source.avg_total_time_diff_in_minute);
                    $('#phoneCpr').text(source.avgCostResolution.toFixed(2));
                    break;
                case "CHAT":
                    chatData = data;
                    $('#chatTotalTickets').text(source.typeCount);
                    $('#chatSolvedTickets').text(source.closedCount);
                    $('#chatResolutionTime').text(source.avg_total_time_diff_in_minute);
                    $('#chatCpr').text(source.avgCostResolution.toFixed(2));
                    break;
                case "SOCIAL":
                    socialData = data;
                    $('#socialTotalTickets').text(source.typeCount);
                    $('#socialSolvedTickets').text(source.closedCount);
                    $('#socialResolutionTime').text(source.avg_total_time_diff_in_minute);
                    $('#socialCpr').text(source.avgCostResolution.toFixed(2));
                    break;
                case "VOICEMAIL":
                    voicemailData = data;
                    $('#voicemailTotalTickets').text(source.typeCount);
                    $('#voicemailSolvedTickets').text(source.closedCount);
                    $('#voiceMailResolutionTime').text(source.avg_total_time_diff_in_minute);
                    $('#voiceMailCpr').text(source.avgCostResolution.toFixed(2));
                    break;
            }

            totalTickets += source.typeCount;
            totalSolvedTickets += source.closedCount;
            totalResolutionTime += source.avg_total_time_diff_in_minute;
            totalCpr += source.avgCostResolution;
            totalAvgCostResolution += source.avgCostResolution;
            totalAvgResolutionTime += source.avg_total_time_diff_in_minute;
        });

        
        $('#totalTickets').text(totalTickets);
        $('#totalSolvedTickets').text(totalSolvedTickets);
        $('#totalResolutionTime').text(totalResolutionTime.toFixed(2));
        $('#totalCpr').text(totalCpr.toFixed(2));

        
        function createChart(container, data, lineColor) {
            Highcharts.chart(container, {
                chart: {
                    type: 'line',
                    animation: true,
                    height: 40,
                    margin: 10
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
                yAxis: {
                    title: {
                        text: null
                    },
                    labels: { enabled: false },
                    gridLineColor: 'transparent'
                },
                xAxis: {
                    title: {
                        text: null
                    },
                    labels: { enabled: false },
                    tickWidth: 0,
                    lineColor: 'transparent'
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        label: {
                            connectorAllowed: false
                        },
                        pointStart: data.length ? data[0].year : 2010
                    }
                },
                series: [{
                    name: 'Tickets',
                    data: data.map(item => item.typeCount),
                    color: lineColor
                }, {
                    name: 'Closed Tickets',
                    data: data.map(item => item.closedCount),
                    color: '#F58D68'
                }]
            });
        }

        createChart('helpdeskQualityEmailLine', emailData, '#BF05FF');
        createChart('helpdeskQualityPhoneLine', phoneData, '#BF05FF');
        createChart('helpdeskQualityChatLine', chatData, '#BF05FF');
        createChart('helpdeskQualitySocialLine', socialData, '#BF05FF');
        createChart('helpdeskQualityVoicemailLine', voicemailData, '#BF05FF');
    },
    error: function(error) {
        console.error(error);
    }
});





	//Ajax for Ticket Module Dashboard quality Help Desk Abandon Rate
	$.ajax({
		url: "manage-dashboard-quality-abandon-rate?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division + "&location=" + location,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
		
		var jsonData = JSON.parse(response.body);
		categoryData = [];
		abandonData = [];
		jsonData.forEach(function(ticket) {
			categoryData.push(ticket.ticketType);
			abandonData.push(ticket.abondonRate);
		});
			
		//Abandon Rate
	    Highcharts.chart('helpdeskAbandonRate', {
		chart: {
			type: 'bar',
			animation: true,
			height: 200,
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
			//categories: categoryData,//['Live Chat', 'Phone'],
			categories: ['Live Chat', 'Phone'],

		},
		yAxis: {
			title: {
				text: ''
			},
			labels: { enabled: false },
		},
		plotOptions: {
			series: {
				dataLabels: {
					enabled: true,
					format: '{y} %',
					color: '#000000'
				}

			}
		},
		legend: { enabled: false, },

		series: [{
			name: '',
			//data: abandonData,//[74, 47],
			data: [74, 47],
			color: '#F58D68'
		}]
	});
			

		},
		error: function(error) {
			console.error(error);
		}
	});


}
function getQualityFilterData(){
	quality();
}
function resetQualityOperation(){
	 var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#qualityfromDate").val(fromDate);
	$("#qualitytoDate").val(toDate);
	getQualityFilterData();
}