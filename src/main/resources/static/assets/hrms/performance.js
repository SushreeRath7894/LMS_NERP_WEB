function performanceHighChat(){
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#performanceHrmsOrganization").val();
	var orgDiv = $("#performanceHrmsDivision").find('option:selected').text();
	var location = $("#performanceHrmsLocation").find('option:selected').text();

	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				$("#totalNoOfEmpPerformanceId").text(allData[0].totalNoOfEmpPerformance);
				$("#totalNoOfSickLeaveTakenId").text(allData[0].totalNoOfSickLeaveTaken+" Days");
				$("#totalNoOfCasulLeaveTakenId").text(allData[0].totalNoOfCasulLeaveTaken+" Days");
				$("#overtimeHoursCommittedId").text(allData[0].overtimeHoursCommitted/1000+" Hours");
				$("#avgEmployeeTenureId").text(allData[0].avgEmployeeTenure+" Years");
				$("#avgAbsenteenismDaysId").text(allData[0].avgAbsenteenismDays+" Days");
				$("#avgAbsenteenismRateId").text(allData[0].avgAbsenteenismRate+"%");
				
				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-employee-rating-distri-by-dept?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);  // Parse the JSON response
				var allData = jsonData.dashboardData; // Extract the data array
				
				var deptNameList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					deptNameList.push(item.deptName);
				}
				
				var colors = ["#BF05FF","#DB51C9","#DB5199","#E976AD","#F79C92"];
				
				var series = allData.map(function(item, index) {
				    return {
				        name: item.deptName,
				        data: [item.ratingPercentage],
				        color: colors[index % colors.length] // Cycle through colors if there are more departments than colors
				    };
				});
				
				Highcharts.chart('employeerating', {
			    chart: {
			        type: 'bar',
			        height: 200,
			        spacingTop: 0,
			        spacingBottom: 0,
			        spacingLeft: 0,
			        spacingRight: 0,
			        groupPadding: 0,  // Remove the padding between groups
			        pointPadding: 0   // Remove the padding between individual bars
			    },
			    title: {
			        text: ''
			    },
			    xAxis: {
			        categories: deptNameList,//['IT','Marketing','Sales','Administration','Engineering'],  
			        visible: false,
			        title: {
			            text: ''
			        },
			        labels: {
			            enabled: true
			        },
			        tickmarkPlacement: 'on',
			        gridLineWidth: 0, // Remove the grid lines for a cleaner look
			    },
			    yAxis: {
			        gridLineColor: 'transparent',
			        title: {
			            text: false,
			            visible: false,
			            lineWidth: 0,
			            minorGridLineWidth: 0,
			            lineColor: 'transparent'
			        },
			        labels: {
			            enabled: false
			        }
			    },
			    plotOptions: {
			        series: {
			            stacking: 'normal',
			            dataLabels: {
			                enabled: true,
			                style: {
			                    color: 'black',
			                    textOutline: 'none',
			                    fontWeight: 'bold'
			                },
			                formatter: function() {
			                    return this.series.name + ': ' + this.y; // Display name and value
			                },
			                align: 'center' // Align the labels to the left side
			            }
			        }
			    },
			    navigation: {
			        buttonOptions: {
			            enabled: false
			        }
			    },
			    credits: {
			        enabled: false
			    },
			    legend: {
			        enabled: false
			    },
			    series: series
			});
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-employee-tenure-trend-with-last-year?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var thisYearCountList = [];
				var lastYearCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					monthYearList.push(item.monthYear);
					thisYearCountList.push(item.thisYearCount);
					lastYearCountList.push(item.lastYearCount);
				}
				
				
				
				// Performance Trend Chart 
            
			    Highcharts.chart('performancetrend', {
			        chart: {
			            type: 'spline',
			            height:250
			        },
			        title: {
			                   text: ''
			                },
			       
			        xAxis: {
			            categories: monthYearList,//['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],
			                
			            accessibility: {
			                description: 'Months of the year'
			            }
			        },
			        yAxis: {
			            gridLineColor: 'transparent',
			            title: {
			                text: ''
			            },
			            labels: {
			                format: '{value}'
			            }
			        },
			        tooltip: {
			            crosshairs: true,
			            shared: true
			        },
			        plotOptions: {
			            spline: {
			            
			                marker: {
			                    radius: 4,
			                    lineColor: '#666666',
			                    lineWidth: 1
			                }
			            }
			        },
			        
			        navigation: {
			            buttonOptions: {
			                enabled: false
			            }
			        },
			        
			        credits: {
			        enabled: false
			        },
			        
			        series: [{
			            name: 'This Year',
			            marker: {
			                symbol: 'circle'
			            },
			            data: thisYearCountList,//[1, 2, 1.8, 1.7, 1.4, 1.2],
			            color: '#BF05FF'
			    
			        }, {
			            name: 'Last Year',
			            marker: {
			                symbol: 'circle'
			            },
			            data: lastYearCountList,//[0.5,1.8,1.3,1.6, 0.9, 1],
			            color: '#F79C92'
			        }]
			    });
			        
				
				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-overtime-by-age-group?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var ageRangeList = [];
				var maleList = [];
				var femaleList = [];
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					ageRangeList.push(item.ageRange);
					maleList.push(item.maleCount);
					femaleList.push(item.femaleCount);
				}
				
				  //Performance Overtime
    
			    Highcharts.chart('performanceovertime', {
			        chart: {
			          type: 'column',
			          height:250
			        },
			        title: {
			          text: ''
			        },
			        subtitle: {
			          text: ''
			        },
			        xAxis: {
			          categories: ageRangeList,
			          crosshair: true
			        },
			         exporting: { enabled: false },
			       credits: {
			          enabled: false
			          },
			        yAxis: {
			         gridLineColor: 'transparent',
			          min: 0,
			          title: {
			            text: ''
			          }
			        },
			        tooltip: {
			          headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			          pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			            '<td style="padding:0"><b>{point.y:.0f}</b></td></tr>',
			          footerFormat: '</table>',
			          shared: true,
			          useHTML: true
			        },
			        plotOptions: {
			          column: {
			            pointPadding: 0.2,
			            pointWidth: 16,
			            borderWidth: 1
			          }
			        },
			        series: [{
			          name: 'Male',
			          data: maleList,//[49.9, 71.5, 23.5,40,35,65],
			          color:'#bf05ff'
			        }, {
			          name: 'Female',
			          data: femaleList,//[83.6, 78.8, 45.5,25,20,35],
			          color:'#F79C92'
			        }
			       
			         
			        ]
			      });	
				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-absenteeism-rate?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				var absteenismRateList = [];
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					absteenismRateList.push(item.absenteeismRateMon);
					absteenismRateList.push(item.absenteeismRateTues);
					absteenismRateList.push(item.absenteeismRateWed);
					absteenismRateList.push(item.absenteeismRateThurs);
					absteenismRateList.push(item.absenteeismRateFri);
					absteenismRateList.push(item.absenteeismRateSat);
				}
				
				
				
    
			      //Absenteeism Chart
			      Highcharts.chart('absenteeism', {
			        chart: {
			            type: 'column',
			            animation: true,
			            height:237
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
			            categories: ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat'],
			            labels: {
			                style: {
			                    fontSize: '10px',
			                }
			            }
			        },
			        yAxis: {
			            gridLineColor: 'transparent',
			            title: {
			                text: ''
			            },
			            labels: {
			                style: {
			                    fontSize: '10px',
			                }
			            }
			        },
			        legend: {
			            enabled: false
			        },
			        series: [{
			            groupPadding: 0,
			            data: absteenismRateList,//[4.5, 1.5, 2.5, 1.8, 3.5, 4.9],
			            color:'#bf05ff',
			            dataLabels: {
			                enabled: true,
			            }
			        }]
			    });
			    
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-employee-count-by-rating?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				var levelNameList = [];
				var below60List = [];
				var range60to70List = [];
				var range70to80List = [];
				var range80to90List = [];
				var above90List = [];
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					levelNameList.push(item.levelName);
					below60List.push(item.below60);
					range60to70List.push(item.range60to70);
					range70to80List.push(item.range70to80);
					range80to90List.push(item.range80to90);
					above90List.push(item.up90);
				}
				
				
				Highcharts.chart('employeecount', {
			        chart: {
			            type: 'bar',
			            height:231
			        },
			        title: {
			            text: ''
			        },
			        xAxis: {
			        categories: levelNameList,//['High Level', 'Mid Level', 'Low Level']
			            
			        },
			        yAxis: {
			             gridLineColor: 'transparent',
			            min: 0,
			           title: {
			                text: ''
			            },
			            labels: {
			            enabled: false
			          }
			             
			        },
			        legend: {
			            reversed: true
			        },
			        plotOptions: {
			            series: {
			                stacking: 'normal',
			                dataLabels: {
			                    enabled: true
			                }
			            }
			        },
			        
			        navigation: {
			            buttonOptions: {
			                enabled: false
			            }
			        },
			        
			        credits: {
			        enabled: false
			        },
			        
			        legend: {
			            enabled: true
			                },
			                
			                                
			        
			        series: [        
			        {
			            name: 'Below 60',
			            data: below60List,//[24, 22, 28],
			            color:'#F79C92',
			            dataLabels: {
			                enabled: true,
			                style: {
			                  color: '#000000',
			                  textOutline: false 
			                }             
			                    }
			        },
			        {
			            name: '60-70',
			            data: range60to70List,//[24, 22, 25],
			            color:'#E1A4F6',
			            dataLabels: {
			                enabled: true,
			                style: {
			                  color: '#000000',
			                  textOutline: false 
			                }             
			                    }
			        },
			        
			         {
			            name: '70-80',
			            data: range70to80List,//[24, 22, 25],
			            color:'#DB79FC',
			            dataLabels: {
			                enabled: true,
			                style: {
			                  color: '#000000',
			                  textOutline: false 
			                }             
			                    }
			        },
			        
			        {
			            name: '80-90',
			            data: range80to90List,//[20, 16, 18],
			            color:'#bf05ff',
			            dataLabels: {
			                enabled: true,
			                style: {
			                  color: '#000000',
			                  textOutline: false 
			                }             
			                    }
			        },
			        
			        
			        {
			            name: '90+',
			            data: above90List,//[18, 12, 14],
			            color:'#8E04BD',
			            dataLabels: {
			                enabled: true,
			                style: {
			                  color: '#000000',
			                  textOutline: false 
			                }             
			              }
			        }
			        
			        
			        ]
			    });	
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-performance-employee-salary-by-tenure-range?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				//$("#totalNoEmployee").text(allData[0].totalNoEmployee);
				//$("#grossSalaryPaidAmount").text(allData[0].grossSalaryPaidAmount/1000000 + "M");
				//$("#netSalaryPaidAmount").text(allData[0].netSalaryPaidAmount/1000000 + "M");
				//$("#totalDeductionAmount").text(allData[0].totalDeductionAmount/1000 + "K");
				//$("#failedNoOfPayment").text(allData[0].failedNoOfPayment);
				//$("#avgAppraisalPercentage").text(allData[0].avgAppraisalPercentage+ "%");
				//$("#avgTenureOfAnEmployee").text(allData[0].avgTenureOfAnEmployee);
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
    
    
    // Performance Employee Count
    
  



    
         
   /*  Highcharts.chart('employeerating', {
        chart: {
          type: 'treemap',
          height: 90,
          marginBottom: 10,
        },
        colorAxis: {
          minColor: '#F79C92',
          maxColor: '#BF05FF',
        },
        legend: {enabled: false},
        credits: {enabled: false},
        exporting: {enabled: false},
        title: {
          text: ''
        },
        series: [{
          layoutAlgorithm: 'squarified',
          layoutStartingDirection: 'horizontal',
          data: [{
            name: 'Engineering',
            value: 8,
            colorValue: 4
          }, {
            name: 'Administration',
            value: 3,
            colorValue: 3
          }, {
            name: 'Sales',
            value: 2,
            colorValue: 2
          }, {
            name: 'Marketing',
            value: 2,
            colorValue: 1
          }, {
            name: 'IT',
            value: 2,
            colorValue: 0
          }
          
          
          ],
          dataLabels: {
            enabled: true,
            style: {
              color: '#FFFFFF',
            },
            format: '<span>{point.name} ({point.value})</span>',
          },
        }]
      });
    */
        
    
   
   
    // Create the chart
	/*
	Highcharts.chart('employeecountsalary', {
    chart: {
        type: 'column',
        marginTop: 0,
        marginBottom: 110,
        plotBorderWidth: 1,
        height: 250
    },

    title: {
        text: '',
        style: {
            fontSize: '8px'
        }
    },

    xAxis: {
        categories: ['0-3 Y', '3-5 Y', '5-10 Y', '15-20 Y', '20+ Y'],
        title: {
            text: '',
            style: {
                fontSize: '8px'
            }
        }
    },

    yAxis: {
        min: 0,
        title: {
            text: ''
        },
        stackLabels: {
            enabled: true,
            style: {
                fontWeight: 'bold',
                color: '#000000'
            }
        }
    },

    tooltip: {
        pointFormat: '<b>{series.name}</b><br>' +
            'Numbers: <b>{point.y}</b><br>' +
            'For: <b>{point.category}</b>'
    },

    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                    textOutline: false
                }
            }
        }
    },
    legend: {
        labelStyle: {
            fontSize: '8px' // Adjust the font size here for series names
        }
    },

    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    series: [{
        name: '<50K',
        data: [4, 6, 7, 8, 7],
        color: '#CA7CE5',
        fontSize: '8px'
    }, {
        name: '51K-100K',
        data: [11, 12, 23, 43, 21],
        color: '#BF05FF',
        fontSize: '8px'
    }, {
        name: '101K-150K',
        data: [14, 12, 32, 34, 29],
        color: '#F4DEFC',
        fontSize: '8px'
    }, {
        name: '151K-300K',
        data: [30, 20, 21, 19, 17],
        color: '#9C27B0',
        fontSize: '8px',
    }, {
        name: '301K+',
        data: [23, 20, 44, 15, 8],
        color: '#673AB7',
        fontSize: '8px'
    }],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                yAxis: {
                    labels: {
                        format: '{value}'
                    }
                }
            }
        }]
    }
});

*/



   Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('employeecountsalary', {
    
        chart: {
            type: 'heatmap',
            marginTop: 0,
            marginBottom: 50,
            plotBorderWidth: 1,
            height:250
        },
    
    
        title: {
            text: '',
            style: {
                fontSize: '1em'
            }
        },
        
    
        xAxis: {
            categories: ['20-30', '31-35', '36-40', '41-45', '46+' ]   
        },
    
        yAxis: {
            categories: ['< Month', '1-2 Months', '2-6 Months', '6-12 Months', '1+ Years'],
            title: null,
            reversed: true
        },
    
        accessibility: {
            point: {
                descriptionFormat: '{(add index 1)}. ' +
                    '{series.xAxis.categories.(x)} sales ' +
                    '{series.yAxis.categories.(y)}, {value}.'
            }
        },
        
        
    
     colorAxis: {
        reversed: false,
        min: 0,
        stops: [
            [0, '#FFD9D5'],
            [0.5, '#BF05FF'],
            [1, '#F4DEFC']
        ]
    },
    
        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 280
        },
    
        tooltip: {
            format: '<b>{series.xAxis.categories.(point.x)}</b> Projects<br>' +
                '<b>{point.value}</b> distribution <br>' +
                '<b>{series.yAxis.categories.(point.y)}</b>'
        },
        
        navigation: {
            buttonOptions: {
                enabled: false
            }
        },
        
        credits: {
        enabled: false
        },
    
        series: [{
            name: 'Sales per employee',
            borderWidth: 1,
            borderColor: '#F2CEFF',
            data: [[0, 0, 4], [0, 1, 6], [0, 2, 7], [0, 3, 8], [0, 4, 7],
                [1, 0, 11], [1, 1, 12], [1, 2, 23], [1, 3, 43], [1, 4, 21],
                [2, 0, 14], [2, 1, 12], [2, 2, 32], [2, 3, 34], [2, 4, 29],
                [3, 0, 30], [3, 1, 20], [3, 2, 21], [3, 3, 19], [3, 4, 17],
                [4, 0, 23], [4, 1, 20], [4, 2, 44], [4, 3, 15], [4, 4, 8]
               ],
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                    textOutline: false,
                    fontSize:'11px' 
                  }
            }
        }],
    
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    yAxis: {
                        labels: {
                            format: '{substr value 0 1}'
                        }
                    }
                }
            }]
        }
    
    });
              
    
    
    
    }