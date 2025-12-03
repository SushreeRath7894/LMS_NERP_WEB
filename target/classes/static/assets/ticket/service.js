function service(){
	var organization = $("#serviceOrgSelect").find('option:selected').text();
	var division = $("#serviceDivSelect").find('option:selected').text();
	var fromDate = $("#serviceFromDate").val();
	var toDate = $("#seviceToDate").val();
	var location = $("#serviceLocation").find('option:selected').text();
	
	
	
	  //Ajax for Ticket Module Dashboard Service  Head Data
        $.ajax({
        url: "manage-dashboard-service-head-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });
	
	
	  //Ajax for Ticket Module Dashboard Service Percentage
        $.ajax({
        url: "manage-dashboard-service-percentage-call?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });
	 
	 //Percentage of Calls
        Highcharts.chart('helpdeskPercentageOfCalls', {
            chart: {
                type: 'line',
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
            yAxis: {
                title: {
                    text: 'Percentage of Calls'
                },
                labels: { format: '{value}%' }
            },

            xAxis: {
                categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],

            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                }
            },
            legend: {
                enabled: false,
            },

            series: [{
                name: 'First Call',
                data: [43, 48, 65, 81, 112, 142,
                    171, 165, 155, 113, 81, 48],
                dashStyle: 'ShortDash',
                marker: false,
                color:'#56156C'
            }, {
                name: 'Second Call',
                data: [24, 37, 29, 21, 32, 30,
                    81, 68, 36, 33, 50, 90],
                dashStyle: 'ShortDot',
                marker: false,
                color:'#F79C92'
            }, {
                name: 'Third Call or More',
                data: [114, 30, 16, 19, 20, 24,
                    32, 30, 27, 29, 25, 19],
                dashStyle: 'LongDashDot',
                marker: false,
                color:'#10A1E4'
            }, {
                name: 'Unresolved',
                data: [21, 55, 81, 112, 89, 118, 182,
                    173, 130, 119, 100, 31],
                dashStyle: 'Dash',
                marker: false,
                color:'#BF05FF'
            }],

            responsive: {
                rules: [{
                    condition: {
                        maxWidth: 500
                    },
                    chartOptions: {
                        legend: {
                            layout: 'horizontal',
                            align: 'center',
                            verticalAlign: 'bottom'
                        }
                    }
                }]
            }
        });
        
         //Ajax for Ticket Module Dashboard Service Monthly Ticket 
        $.ajax({
        url: "manage-dashboard-service-monthly-ticket?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });
        //Monthly Tickets
        Highcharts.chart('helpdeskMonthlyTickets', {
            chart: {
                type: 'column',
                animation: true,
                height: 250
            }, navigation: {
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
            credits: false,
            xAxis: {
                categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
            },
            yAxis: {
                min: 0,
                title: {
                    text: null
                },
                stackLabels: {
                    enabled: false
                }, opposite: true,
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: false
                    }
                }
            },
            series: [{
                name: 'Calls',
                data: [3, 5, 1, 13, 12, 4, 5, 7, 2, 9, 11, 4],
                color:'#B422B6'
            }, {
                name: 'E-mail',
                data: [14, 8, 8, 12, 17, 12, 3, 5, 1, 6, 9, 2],
                color:'#F79C92'
            }, {
                name: 'Chat',
                data: [0, 2, 6, 3, 2, 5, 6, 2, 8, 4, 15, 13],
                color:'#CA7CE5'
            }]
        });
        
        
        //Ajax for Ticket Module Dashboard Service Call Response Time By Weekday  
        $.ajax({
        url: "manage-dashboard-service-call-response-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    });   
      
        Highcharts.chart('helpdeskCallResponseTimeByWeekday', {
            chart: {
                type: 'column',
                animation: true,
                height: 226,
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
                categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],

            },
            yAxis: {
                title: {
                    text: 'Response Time in Sec'
                },
                labels: { enabled: false },
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        color: '#000000'
                    }

                }
            },
            legend: { enabled: false, },

            series: [{
                name: '',
                data: [74, 73, 47, 57, 64, 35, 36],
                color: '#F79C92'
            }]
        });
        
         //Ajax for Ticket Module Dashboard Service Top Performers  
        $.ajax({
        url: "manage-dashboard-service-top-performers?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization+"&division="+division+"&location="+location,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
             
        },
        error: function(error) {
            console.error(error);
        }
    }); 
        
}
function getServiceFilterData(){
	service();
}
function resetServiceOperation(){
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#serviceFromDate").val(fromDate);
	$("#seviceToDate").val(toDate);
	getServiceFilterData();
}