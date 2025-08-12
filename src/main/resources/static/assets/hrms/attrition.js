function attritionHighChat(){

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#attritionHrmsOrganization").val();
	var orgDiv = $("#attritionHrmsDivision").find('option:selected').text();
	var location = $("#attritionHrmsLocation").find('option:selected').text();
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				$("#empTotalAttrition").text(allData[0].empCountTotal);
				$("#empJoiningTotalAttrition").text(allData[0].shortlistedCountTotal);
				$("#empLeavingTotalAttrition").text(allData[0].leavingCountTotal);
				$("#empInboundOutBoundRatioAttrition").text(allData[0].inboundOutboundRatio);
				$("#avgEmployeeTenureAttrition").text(allData[0].avgEmployeeTenure);
				
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-involuntary-voluntary?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var voluntaryAttrition = allData[0].voluntaryAttrition;
				var inVoluntaryAttrition = 100-voluntaryAttrition;
				
Highcharts.chart('involuntary', {
    chart: {
      plotBackgroundColor: null,
      plotBorderWidth: 0,
      plotShadow: false,
      height:250
    },
    title: {
      text: '',
      align: 'center',
      verticalAlign: 'middle',
      y: 60
    },
    tooltip: {
      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    accessibility: {
      point: {
        valueSuffix: '%'
      }
    },
      credits: {
      enabled: false
    },
    
    exporting: { enabled: false },
    plotOptions: {
      pie: {
      
      colors: [
                  '#CA7CE5',
                  '#bf05ff'
                      
              ],
      
      
        dataLabels: {
          enabled: true,
          format: '<span style="font-size: 10px"><b>{point.name}</b></span><br>' +
          '<span style="opacity: 1">{point.percentage:1f} %</span>',
          distance: 10,
          style: {
            color: '#000000'
          }
        },
        startAngle: -180,
        endAngle: 180,
        center: ['50%', '50%'],
        size: '80%'
      }
      
      
    },
    series: [{
      type: 'pie',
      name: '',
      innerSize: '50%',
      data: [
        ['Involuntary',inVoluntaryAttrition],
        ['Voluntary',voluntaryAttrition]
      ]
    }]
  });	
      

								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-emp-tenure?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var employeeTenureLessThan1YearCount = allData[0].employeeTenureLessThan1YearCount;
				var employeeTenure1to2YearCount = allData[0].employeeTenure1to2YearCount;
				var employeeTenure2to3YearCount = allData[0].employeeTenure2to3YearCount;
				var employeeTenure3to5YearCount = allData[0].employeeTenure3to5YearCount;
				var employeeTenure5PlusYearCount = allData[0].employeeTenure5PlusYearCount;
				
				
								
				//Emplyoee Tenure		
				
				Highcharts.chart('tenure', {
				    chart: {
				      plotBackgroundColor: null,
				      plotBorderWidth: 0,
				      plotShadow: false,
				      height:250
				    },
				    title: {
				      text: '',
				      align: 'center',
				      verticalAlign: 'middle',
				      y: 60
				    },
				    tooltip: {
				      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				    },
				    accessibility: {
				      point: {
				        valueSuffix: '%'
				      }
				    },
				      credits: {
				      enabled: false
				    },
				    
				    exporting: { enabled: false },
				    plotOptions: {
				      pie: {
				      colors: [
				                  '#CA7CE5',
				                  '#bf05ff',
				                  '#B422B6',
				                  '#56156C',	
				                  '#F79C92'	
				              ],
				        dataLabels: {
				          enabled: true,
				          format: '<span>{point.name}</span><br>' +
				          '<span>{point.percentage:.0f} %</span>',
				          distance: 10,
				          style: {
				            fontWeight: 'bold',
				            color: '#000000'
				          }
				        },
				        startAngle: -180,
				        endAngle: 180,
				        center: ['50%', '50%'],
				        size: '70%'
				      }
				    },
				    series: [{
				      type: 'pie',
				      name: '',
				      innerSize: '50%',
				      data: [
				        ['Less Than 1 Year',employeeTenureLessThan1YearCount],
				        ['1-2 Year',employeeTenure1to2YearCount],
				        ['2-3 Year',employeeTenure2to3YearCount],
				        ['3-5 Year',employeeTenure3to5YearCount],
				        ['5+ Year',employeeTenure5PlusYearCount],
				        
				      ]
				    }]
				  });	
				  
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-ratio?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var employeeJoiningCountList = [];
				var employeeLeavingCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					monthYearList.push(item.monthYear);
					employeeJoiningCountList.push(item.employeeJoiningCount);
					employeeLeavingCountList.push(item.employeeLeavingCount);
				}
				
				
				//Attrition Trend Chart Start
		
				  Highcharts.chart('trend', {
				      chart: {
				          type: 'spline',
				          height:250
				      },
				      title: {
				                 text: ''
				              },
				     
				      xAxis: {
				          categories: monthYearList,
				              
				          accessibility: {
				              description: 'Months of the year'
				          }
				      },
				      yAxis: {
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
				          name: 'Emplyoees Joining',
				          marker: {
				              symbol: 'circle'
				          },
				          data: employeeJoiningCountList,
				          color: '#BF05FF'
				  
				      }, {
				          name: 'Emplyoees Leaving',
				          marker: {
				              symbol: 'circle'
				          },
				          data: employeeLeavingCountList,
				          color: '#DEAAF0'
				      }]
				  });
				
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-dept?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var deptList = [];
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					deptList.push(item.deptName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				
				 //Department	

			Highcharts.chart('department', {
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
			      categories: deptList,
			      crosshair: true
			    },
			     exporting: { enabled: false },
			   credits: {
			      enabled: false
			      },
			    yAxis: {
			      min: 0,
			      title: {
			        text: ''
			      }
			    },
			    tooltip: {
			      headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			      pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			        '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			      footerFormat: '</table>',
			      shared: true,
			      useHTML: true
			    },
			    plotOptions: {
			      column: {
			        pointPadding: 0.2,
			        pointWidth: 9,
			        borderWidth: 1
			      }
			    },
			    series: [{
			      name: 'Male',
			      data: maleCountList,
			      color:'#bf05ff'
			    }, {
			      name: 'Female',
			      data: femaleCountList,
			      color:'#CA7CE5'
			    },
			    {
			      name: 'Others',
			      data: othersCountList,
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
		url: "hrms-dashboard-attrition-voluntary-by?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var deptList = [];
				var voluntaryList = [];
				var InVoluntaryList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					deptList.push(item.deptName);
					voluntaryList.push(item.voluntatyCount);
					InVoluntaryList.push(item.inVoluntatyCount);
				}
				
				//Attrition By voluntary.
	
				Highcharts.chart('voluntary', {
				    chart: {
				        type: 'bar',
				        height:250
				    },
				    title: {
				        text: ''
				    },
				    xAxis: {
				        categories: deptList,//['Marketing', 'Enginnering', 'IT', 'Sales', 'HR']
				    },
				    yAxis: {
				        min: 0,
				        title: {
				            text: ''
				        }
				    },
				    legend: {
				        reversed: true
				    },
				    plotOptions: {
				        series: {
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
					navigation: {
				        buttonOptions: {
				            enabled: false
				        }
				    },
					
					credits: {
				    enabled: false
					},
				    series: [{
				        name: 'Voluntary',
				        data: voluntaryList,//[4, 4, 6, 15, 12],
						color:'#CA7CE5'
				    }, {
				        name: 'Involuntary',
				        data: InVoluntaryList,//[5, 3, 12, 6, 11],
						color:'#bf05ff'
				    }]
				});
		
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	

	
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-reason-distribution?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var reasonList = [];
				var maleCountList = [];
				var femaleCountList = [];				
				var othersCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					reasonList.push(item.reasonName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				
				
				// Distribution 
				
				Highcharts.chart('distribution', {
				    chart: {
				        type: 'bar',
				        backgroundColor: '#ffffff',
				        color: 'black',
				        height:250
				    },
				    title: {
				        text: ''
				    },
				    exporting: { enabled: false },
				 credits: {
				    enabled: false
				    },
				    xAxis: {
				        categories: reasonList,
				        title: {
				            text: null
				        }
				         
				    },
				    yAxis: {
				        min: 0,
				        color: 'black',
				        gridLineWidth: 0,
				        title: {
				            text: '',
				            align: 'high'
				        },
				       labels: {
				        format: '{value}'
				       }
				    },
				    tooltip: {
				        valueSuffix: ' '
				    },
				    plotOptions: {
				        bar: {
				            dataLabels: {
				                enabled: true
				            }
				        }
				    },
				    
				    credits: {
				        enabled: false
				    },
				    series: [{
				        name: 'Male',
				        data: maleCountList,
				        color:'#bf05ff'
				    },
				    {
				        name: 'Female',
				        data: femaleCountList,
				        color:'#CA7CE5'
				    },
				    {
				        name: 'Others',
				        data: othersCountList,
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
		url: "hrms-dashboard-attrition-by-tenure-salary?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var tenureList = [];
				var lessThan50kList = [];
				var greaterThan301KList = [];				
				var between51kTo100KList = [];
				var between101kTo150KList = [];
				var between151kTo300KList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					tenureList.push(item.tenure);
					lessThan50kList.push(item.lessThan50k);					
					between51kTo100KList.push(item.between51kTo100K);
					between101kTo150KList.push(item.between101kTo150K);
					between151kTo300KList.push(item.between151kTo300K);
					greaterThan301KList.push(item.greaterThan301K);
				}
				/*
				
					Highcharts.chart('salaryband', {
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
				        categories: tenureList,//['<1 Y', '1-2 Y', '2-3 Y', '3-5 Y', '5+ Y'],
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
				        data: lessThan50kList,//[4, 11, 14, 30, 23],
				        color: '#CA7CE5',
				        fontSize: '8px' 
				    }, {
				        name: '51K-100K',
				        data: between51kTo100KList,//[6, 12, 12, 20, 21],
				        color: '#BF05FF',
				        fontSize: '8px'
				    }, {
				        name: '101K-150K',
				        data: between101kTo150KList,//[7, 23, 32, 21, 44],
				        color: '#F4DEFC',
				        fontSize: '8px'
				    }, {
				        name: '151K-300K',
				        data: between151kTo300KList,//[8, 43, 34, 19, 15],
				        color: '#9C27B0',
				        fontSize: '8px',
				    }, {
				        name: '301K+',
				        data: greaterThan301KList,//[7, 21, 29, 17, 8],
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
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-attrition-by-job-role?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var jobRoleList = [];
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					jobRoleList.push(item.jobRoleName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				
				// Job Role	
				
				Highcharts.chart('jobrole', {
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
				      categories: jobRoleList,
				      crosshair: true
				    },
				     exporting: { enabled: false },
				   credits: {
				      enabled: false
				      },
				    yAxis: {
				      min: 0,
				      title: {
				        text: ''
				      }
				    },
				    tooltip: {
				      headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				      pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				        '<td style="padding:0"><b>{point.y:.f}</b></td></tr>',
				      footerFormat: '</table>',
				      shared: true,
				      useHTML: true
				    },
				    plotOptions: {
				      column: {
				        pointPadding: 0.2,
				        pointWidth: 9,
				        borderWidth: 1
				      }
				    },
				    series: [{
				      name: 'Male',
				      data: maleCountList,//[49.9, 71.5, 23.5,40,35],
				      color:'#bf05ff'
				    }, {
				      name: 'Female',
				      data: femaleCountList,//[83.6, 78.8, 45.5,25,20],
				      color:'#CA7CE5'
				    },
				    {
				      name: 'Others',
				      data: othersCountList,//[35.6, 40.8, 30.5,39,31],
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
		url: "hrms-dashboard-attrition-by-tenure?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var tenureList = [];
				var voluntaryList = [];
				var InVoluntaryList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					tenureList.push(item.tenure);
					voluntaryList.push(item.voluntaryAttrition);
					InVoluntaryList.push(item.inVoluntaryAttrition);
				}
				
				// Attrition Involuntary
	
				Highcharts.chart('attrinvoluntary', {
				    chart: {
				        type: 'bar',
						height:250
				    },
				    title: {
				        text: ''
				    },
				    xAxis: {
				        categories: tenureList,
				    },
				    yAxis: {
				        min: 0,
				        title: {
				            text: ''
				        }
				    },
				    legend: {
				        reversed: true
				    },
				    plotOptions: {
				        series: {
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
					
					navigation: {
				        buttonOptions: {
				            enabled: false
				        }
				    },
					
					credits: {
				    enabled: false
					},
					
				    series: [{
				        name: 'Voluntary',
				        data: voluntaryList,//[18, 12, 14, 15, 12],
						color:'#CA7CE5'
				    }, {
				        name: 'Involuntary',
				        data: InVoluntaryList,//[20, 16, 18, 6, 22],
						color:'#bf05ff'
				    }]
				});
					
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	

//salaryband

Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('salaryband', {
    
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