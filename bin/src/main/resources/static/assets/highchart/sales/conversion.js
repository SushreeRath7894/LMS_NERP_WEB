function conversionHighChartSales(){

	  const data = [
            ['1', 2],
            ['2', 3],
            ['3', 1],
            ['4', 0],
            ['5', 0],
            ['6', 2],
            ['7', 1],
            ['8', 1],
            ['9', 5],
            ['10', 3],
            ['11', 1],
            ['12', 0],
            ['13', 2],
            ['14', 0],
            ['15', 4],
            ['16', 1],
            ['17', 0],
            ['18', 2],
            ['19', 1],
            ['20', 3],
            ['21', 2],
            ['22', 3],
            ['23', 1],
            ['24', 0],
            ['25', 0],
            ['26', 2],
            ['27', 1],
            ['28', 1],
            ['29', 5],
            ['30', 3]
        ];

        // Calculate the max value for the Y-axis to fit the data
        let maxY = Math.max(...data.map(item => item[1])) + 1; // Adding 1 for padding

        // Create the chart
        Highcharts.chart('convertedLeads', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 400,  // Adjust height as needed
            },
            colors: ['#ff9393'],  // Set bar color to light red
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
                    text: "Converted Leads - Last 30 days"
                },
                lineColor: '#ccc',  // Set line color for x-axis
                min: 0,
                max: 29,
                tickLength: 0,
                categories: [
                    '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15',
                    '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'
                ],
                labels: {
                    rotation: 0,
                    style: {
                        fontSize: '12px',
                        color: '#333333'
                    }
                }
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    enabled: true
                },
                gridLineColor: 'transparent',
                min: 0,   // Start Y-axis from 0
                max: maxY, // Dynamically adjust max Y-axis value
            },
            legend: {
                enabled: false
            },
            tooltip: {
                enabled: false
            },
            series: [{
                name: 'IPS',
                data: data,  // Pass the data array here
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#000000',
                    align: 'center',  // Centering data label
                    y: -10,  // Position the data labels slightly above the bars
                    style: {
                        fontSize: '12px',
                        fontWeight: 'bold',
                    }
                }
            }]
        });
	
	/*	
	$.ajax({
	    type: "GET",
	    url: "customer-dashboard-convertedLeads",
	    async: true,
	    success: function (response) {
	        if (response.code == "success") {
	            var jsonData = JSON.parse(response.body);
	            var allData = jsonData.AllData;
	
	            var datalist = [];
	            for (var i = 0; i < allData.length; i++) {
	                var data = [];
	                
	                data.push(allData[i].lead_creaton); 
	                data.push(allData[i].total_lead_Owner); 
	                datalist.push(data);
	            }
	
	            Highcharts.chart('convertedLeads', {
	                chart: {
	                    type: 'column',
	                    backgroundColor: 'transparent',
	                    height: 200,
	                },
	                colors: ['#ff9393'],
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
	                        text: "Converted Leads - Last 30 days",
	                         style: {
	                            fontSize: '15px',
	                             color: '#000000'                     
	                        }
	                    },
	                    lineColor: 'total_lead_Owner',
	                    min: 1,
	                    max: 31,
	                    tickLength: 0,
	                    labels: {
	                        rotation: 0,
	                        style: {
	                            fontSize: '14px',
	                            color: '#000000'
	                        }
	                    }
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
	                    data: datalist, // Use the correct data array
	                    dataLabels: {
	                        enabled: true,
	                        rotation: 0,
	                        color: '#FFFFFF',
	                        align: 'right',
	                        y: 0,
	                        style: {
	                            fontSize: '10px',
	                             color: '#000000'                     
	                        }
	                    }
	                }]
	            });
	        }
	    },
	    error: function (data) {
	        console.log(data);
	    }
	});
*/

     //lead to opportunity ratio
        Highcharts.chart('leadOpportunityRatio', {
             chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 140,
                // margin: 0
            },
            colors: ['#003961'],
            title: {
                text: '22%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 80,
                margin: 0,
                style: { "fontSize": '16', "color": '#453f7a' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
             
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#8077cb',
                    thickness: 25
                }, {
                    from: 22,
                    to: 70,
                    color: '#d9d9d9',
                    thickness: 25
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#00f7ff',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '0%',
                                        
                },
                pivot: {
                    radius: 0
                },
            }]
        });		
        
//opportunity to win ratio
      Highcharts.chart('opportunityWinRatio', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 140,
                // margin: 0
            },
            colors: ['#003961'],
            title: {
                text: '32%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 80,
                margin: 0,
                style: { "fontSize": '16', "color": '#453f7a' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },
           // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,               
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 40,
                    color: '#8077cb',
                    thickness: 25
                }, {
                    from: 40,
                    to: 80,
                    color: '#d9d9d9',
                    thickness: 25
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '0%',
                    // backgroundColor: '#730101',
                    // topWidth: 7,
                    // baseWidth: 1,
                    // baseLength: '0%',
                    // rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });

      //conversion rate
        Highcharts.chart('conversionRate', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 58,
                margin: 0
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

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#003256',
            }],

        });
        
        
        
        //conversion rate 1
  $.ajax({
    type: "GET",
    url: "customer-dashboard-conversionRate1",
    async: true,
    success: function (response) {
        if (response.code == "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.AllData;

            var datalist = [];
            for (var i = 0; i < allData.length; i++) {
                var data = [];

                data.push(allData[i].lead_Owner);
                data.push(allData[i].total_lead_Owner);
                datalist.push(data);
            }

            Highcharts.chart('conversionRate1', {
                chart: {
                    type: 'line',
                    animation: true,
                    backgroundColor: 'transparent',
                    height: 58,
                    margin: 0
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
                yAxis: {
                    title: {
                        text: ''
                    },
                    showInLegend: false,
                    lineColor: 'transparent',
                    labels: {
                        enabled: false
                    },
                    gridLineColor: 'transparent',
                },
                xAxis: {
                    lineColor: 'transparent',
                    labels: {
                        enabled: false
                    },
                    tickWidth: 0,
                    tickLength: 0,
                    showInLegend: false
                },
                plotOptions: {
                    series: {
                        label: {
                            connectorAllowed: false
                        },
                        pointStart: 0
                    }
                },
                series: [{
                    marker: {
                        enabled: false
                    },
                    name: '',
                    data: datalist, // Use the correct data array
                    color: '#003256',
                }],
            });
        }
    },
    error: function (data) {
        console.log(data);
    }
});

        
      /*    $.ajax({
					type	: "GET",
					url : "customer-dashboard-conversionRate1",
					async:true,
					success	: function(response){
						if(response.code=="success"){
							
							var jsonData = JSON.parse(response.body);
						//	alert(jsonData)
						var allData = jsonData.AllData;
							//console.log(allData)
							alert(allData.length)
							var datalist=[];
							for(var i=0;i<allData.length;i++){
								var data=[];
								
								data.push(allData[i].lead_Owner);
								data.push(allData[i].total_lead_Owner);
								datalist.push(data);
							
									
		            Highcharts.chart('conversionRate1', {
		            chart: {
		                type: 'line',
		                animation: true,
		                backgroundColor: 'transparent',
		                height: 58,
		                margin: 0
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
		
		            yAxis: {
		                title: {
		                    text: ''
		                },
		                showInLegend: false,
		                lineColor: 'transparent',
		                labels: {
		                    enabled: false
		                },
		                gridLineColor: 'transparent',
		            },
		
		            xAxis: {
		                // accessibility: {
		                //     rangeDescription: 'Range: 2010 to 2020'
		                // }, 
		                lineColor: 'transparent',
		                labels: {
		                    enabled: false
		                },
		                tickWidth: 0,
		                tickLength: 0,
		                showInLegend: false
		            },
		
		
		            plotOptions: {
		                series: {
		                    label: {
		                        connectorAllowed: false
		                    },
		                    pointStart: 0
		                }
		            },
		
		            series: [{
		                marker: {
		                    enabled: false
		                },
		                name: '',
		                data: [4, 5, 10, 2, 5, 6,
		                    8, 1, 5, 3, 5],
		                color: '#003256',
		            }],
		
		        });
		        }	
		        }
				},error	: function(data){
					console.log(data);	
				}
			})*/
        //conversion rate 2
        Highcharts.chart('conversionRate2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 58,
                margin: 0
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

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#003256',
            }],

        });
}