function recruitmentHighChat() {
	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#recruitmentHrmsOrganization").val();
	var orgDiv = $("#recruitmentHrmsDivision").find('option:selected').text();
	var location = $("#recruitmentHrmsLocation").find('option:selected').text();

	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
		
				$("#totalVacanciesInRecuitId").text(allData[0].totalVacancyCount);
				$("#totalApplicationsInRecuitId").text(allData[0].totalApplicationsCount);
				$("#totalShortListedInRecuitId").text(allData[0].totalShortlistedCount);
				$("#totalAssessmentInRecuitId").text(allData[0].totalAssessmentCount);
				$("#totalInterviewsInRecuitId").text(allData[0].totalInterviewedCount);
				$("#totalOfferedHandedInRecuitId").text(allData[0].totalOfferedHandedCount);
				$("#totalHiredInRecuitId").text(allData[0].totalHiredCount);
				
				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-avg-cost-hiring-by-seniority-level?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var juniorLevelHiringCostPercentage  = allData[0].juniorLevelHiringCostPercentage;
				var midLevelHiringCostPercentage  = allData[0].midLevelHiringCostPercentage;
				var seniorLevelHiringCostPercentage  = allData[0].seniorLevelHiringCostPercentage;
				
					Highcharts.chart('avgCostHiringSeniorityLevel', {
					chart: {
						type: 'pie',
						animation: true,
						height: 323
					},
					credits: { enabled: false },
					title: {
						text: ''
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
						pie: {
							shadow: false,
							showInLegend: true,
							colors: [
								'#F79C92',
								'#B422B6',
								'#BF05FF'						
							]
						}
					},
					tooltip: {
						formatter: function() {
							return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
						}
					},
					series: [{
						name: '',
						data: [["Junior", juniorLevelHiringCostPercentage],
						 ["Mid-Level", midLevelHiringCostPercentage], 
						 ["Senior", seniorLevelHiringCostPercentage]],
						 
						
						innerSize: '50%',
						showInLegend: true,
						dataLabels: {
							enabled: true,
							distance: 10,
							format: '{point.percentage:.1f} %'
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
		url: "hrms-dashboard-recruitment-funnel?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				
				$("#totalApplicationCnt").text(allData[0].totalApplication);
				$("#totalInterviewCnt").text(allData[0].totalInterview);
				$("#totalAssesmentCnt").text(allData[0].totalAssesment);
				$("#totalHiredCnt").text(allData[0].totalHired);
				
				$("#totalApplicationToHirePercentage").text(allData[0].totalApplicationToHiredPercentage);
				$("#totalApplicationToAssesmentPercentage").text(allData[0].totalApplicationToAssesmentPercentage);
				$("#totalAssesmentToInterviewPercentage").text(allData[0].totalAssesmentToInterviewPercentage);
				$("#totalInterviewToHiredPercentage").text(allData[0].totalInterviewToHiredPercentage);
				
														
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	
	
	


	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-hired-by-source?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
			var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				// Prepare the dynamic data for the chart
				var pieChartData = [];
				for (var i = 0; i < allData.length; i++) {
				    var item = allData[i];
				    pieChartData.push([item.sourceName, item.percentageForThisSource]);
				}
				
				// Render the Highcharts chart dynamically
				Highcharts.chart('hrmshiredcandidate', {
				    chart: {
				        plotBackgroundColor: null,
				        plotBorderWidth: 0,
				        plotShadow: false,
				        height: 314
				    },
				    title: {
				        text: '',
				        align: 'center',
				        verticalAlign: 'middle',
				        y: 60
				    },
				    tooltip: {
				        pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
				                '#F79C92',
				                '#FFB94E', // Adding another color for the "Others" category if needed
				            ],
				            showInLegend: true,
				            dataLabels: {
				                enabled: true,
				                distance: 10,
				                format: '<span>{point.name}</span><br>' +
				                        '<span>{point.percentage:.0f} %</span>',
				                style: {
				                    fontWeight: 'bold',
				                    color: 'black'
				                }
				            },
				            startAngle: -180,
				            endAngle: 180,
				            center: ['50%', '50%'],
				            size: '100%'
				        }
				    },
				    series: [{
				        type: 'pie',
				        name: '',
				        innerSize: '50%',
				        data: pieChartData // Using the dynamically created data
				    }]
				});
						
										
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-hiring-vacancy-trend?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var vacancyCountList = [];
				var hiredCountList = [];
				
				
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					monthYearList.push(item.monthYear);
					vacancyCountList.push(item.vacancyCount);
					hiredCountList.push(item.hiredCount);
				}
				
				
				//Hiring to Vacancy Trend 
		

				Highcharts.chart('hrmsvacancytrend', {
				    chart: {
				        type: 'spline',
						height:250
				    },
				    title: {
				               text: ''
				            },
				   
				    xAxis: {
				        categories: monthYearList,//['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
				            
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
				        name: 'Vacancies',
				        marker: {
				            symbol: 'circle'
				        },
				        data: vacancyCountList,//[11, 8, 10, 14, 9, 15],
						color: '#BF05FF'
				
				    }, {
				        name: 'Hired',
				        marker: {
				            symbol: 'circle'
				        },
				        data: hiredCountList,//[10, 12, 8, 15, 8, 14],
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
		url: "hrms-dashboard-recruitment-hired-by-jobs-role?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var designationRoleNameList = [];
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					designationRoleNameList.push(item.designationRoleName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				
				//Hired by Job Roles
				
				Highcharts.chart('hrmshiredjobroles', {
				
					chart: {
					  type: 'column',
					  height:250
					},
				  
					title: {
					  text: ''
					},
				  
					xAxis: {
					  categories: designationRoleNameList,//['DA', 'Senior A', 'Associate','HRBP','DE']
					},
				  
					yAxis: {
					  allowDecimals: false,
					  min: 0,
					  title: {
						text: ''
					  }
					},
					exporting: { enabled: false },
					   credits: {
					   enabled: false
					  },
					tooltip: {
					  formatter: function () {
						return '<b>' + this.x + '</b><br/>' +
						  this.series.name + ': ' + this.y + '<br/>' +
						  'Total: ' + this.point.stackTotal;
					  }
					},
				  
					plotOptions: {
					  column: {
						stacking: 'normal'
					  }
					},
				  
					series: [{
					  name: 'Male',
					  data: maleCountList,//[20, 22, 18,32,28],
					  stack: 'male',
					  color:'#CA7CE5'
					  }, {
					  name: 'Female',
					  data: femaleCountList,//[15, 17, 16,18,18],
					  stack: 'male',
					  color:'#F79C92'
					},{
					  name: 'Others',
					  data: othersCountList,//[10, 18, 15,16,10],
					  stack: 'male',
					  color:'#BF05FF'
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
		url: "hrms-dashboard-recruitment-avg-dept-by-fullfill-in-day?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var deptNameList = [];
				var actualDaysCountList = [];
				var forecastDaysCountList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					deptNameList.push(item.departmentName);
					actualDaysCountList.push(item.actualCount);
					forecastDaysCountList.push(item.forecastCount);
				}	
				
				
				Highcharts.chart('avgTimeToFillByDepartment', {
				    chart: {
				        animated: true,
				        height: 250
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
				    xAxis: {
				        categories: deptNameList,//['HR', 'IT', 'Finance', 'Marketing', 'Sales', 'Operations'],  // Static categories (Departments)
				    },
				    yAxis: {
				        title: {
				            text: ''
				        }
				    },
				    series: [{
				        type: 'column',
				        name: 'Actual',
				        data: actualDaysCountList,//[10, 15, 12, 9, 14, 13], 
				        color: '#F79C92',
				    }, {
				        name: 'Forecast',
				        color: 'transparent',
				        lineColor: 'transparent',
				        data: forecastDaysCountList,//[12, 14, 11, 10, 13, 12], 
				        type: 'spline',
				        dataLabels: {
				            enabled: false,
				        },
				        marker: {
				            lineWidth: 2,
				            fillColor: '#BF05FF'
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
		url: "hrms-dashboard-recruitment-hired-by-department?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var departmentNameList = [];
				var hiredCountList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					departmentNameList.push(item.departmentName);
					hiredCountList.push(item.hiredCount);
					
				}
				
				 // Hired Charts

				Highcharts.chart('hrmshired', {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				    chart: {
				        type: 'bar',
				        backgroundColor: '#ffffff',
				        color: 'black',
						height:246
				    },
				    title: {
				        text: ''
				    },
				    exporting: { enabled: false },
				 credits: {
				    enabled: false
				    },
				    xAxis: {
				        categories: departmentNameList,//['Project 1', 'Project 2','Project 3','Project 4','Project 5','Project 6','Project 7'],
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
				        name: 'Hired Number',
				        data: hiredCountList,//[30, 40,50,60,80,90,110],
				        color:'#bf05ff'
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
		url: "hrms-dashboard-recruitment-salary-distribution-by-exp-hired-emp?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
										
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-hired-by-age-brackets?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
				// Prepare the deptNameList and seriesData
			
				maleCountList.push(allData[0].maleTotal18to22);
				maleCountList.push(allData[0].maleTotal23to27);
				maleCountList.push(allData[0].maleTotal28to32);
				maleCountList.push(allData[0].maleTotal33to37);
				maleCountList.push(allData[0].maleTotal38to45);
				maleCountList.push(allData[0].maleTotalGreaterTo45);
				
				femaleCountList.push(allData[0].femaleTotal18to22);
				femaleCountList.push(allData[0].femaleTotal23to27);
				femaleCountList.push(allData[0].femaleTotal28t32);
				femaleCountList.push(allData[0].femaleTotal33to37);
				femaleCountList.push(allData[0].femaleTotal38to45);
				femaleCountList.push(allData[0].femaleTotalGreaterTo45);
								
				othersCountList.push(allData[0].otherTotal18to22);
				othersCountList.push(allData[0].otherTotal23to27);
				othersCountList.push(allData[0].otherTotal28to32);
				othersCountList.push(allData[0].otherTotal33to37);
				othersCountList.push(allData[0].otherTotal38to45);
				othersCountList.push(allData[0].otherTotalGreaterTo45);
				
				// Hired by Age Brackets

					Highcharts.chart('hrmshiredagebracket', {
						chart: {
						  type: 'column',
						  height:265
						},
						title: {
						  text: ''
						},
						subtitle: {
						  text: ''
						},
						xAxis: {
						  categories: ['18-22','23-27','28-32','33-37','38-45','45+'  ],
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
							'<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
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
						  data: maleCountList,//[49.9, 71.5, 23.5,40,35,55],
						  color:'#bf05ff'
						}, {
						  name: 'Female',
						  data: femaleCountList,//[83.6, 78.8, 45.5,25,20,46],
						  color:'#CA7CE5'
						},
						{
						  name: 'Others',
						  data: othersCountList,//[35.6, 40.8, 30.5,39,31,38],
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
		url: "hrms-dashboard-recruitment-offer-decline-reason?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				
				var declineReasonList = [];
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					declineReasonList.push(item.declineReasonName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				
				
				// Offer decline reason

				Highcharts.chart('hrmsofferdecline', {
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
				        categories: declineReasonList,//['Reason1', 'Reason2','Reason3'],
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
				        data: maleCountList,//[50, 60,70],
				        color:'#bf05ff'
				    },
				    {
				        name: 'Female',
				        data: femaleCountList,//[50, 15,35],
				        color:'#CA7CE5'
				    },
				    {
				        name: 'Others',
				        data: othersCountList,//[20, 25,46],
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
		url: "hrms-dashboard-recruitment-progress-distribution?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
					var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				// Prepare the dynamic data for the pie chart
				var pieChartData = [];
				for (var i = 0; i < allData.length; i++) {
				    var item = allData[i];
				    pieChartData.push([item.distributionName, item.distributionPercentForThis]);
				}
				
				// Initialize the Highcharts chart
				Highcharts.chart('progressdistribution', {
				    chart: {
				        plotBackgroundColor: null,
				        plotBorderWidth: 0,
				        plotShadow: false,
				        height: 250
				    },
				    title: {
				        text: '',
				        align: 'center',
				        verticalAlign: 'middle',
				        y: 60
				    },
				    tooltip: {
				        pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
				                '#bf05ff',
				                '#B422B6',
				                '#56156C',
				                '#F79C92',
				                '#0E98FF',
				                '#FF9632', // Add more colors if necessary
				            ],
				            dataLabels: {
				                enabled: true,
				                distance: 5,
				                format: '<span>{point.name}</span><br>' +
				                        '<span>{point.percentage:.0f} %</span>',
				                style: {
				                    fontWeight: 'bold',
				                    color: 'black'
				                }
				            },
				            startAngle: -180,
				            endAngle: 180,
				            center: ['50%', '50%'],
				            size: '100%'
				        }
				    },
				    series: [{
				        type: 'pie',
				        name: 'Distribution',
				        innerSize: '50%',
				        data: pieChartData // Use dynamic data here
				    }]
				});
				
									
									
					
										
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-recruitment-acceptance-rejection-distribution?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var acceptancePercentage  = allData[0].acceptancePercentage;
				var rejectionPercentage  = allData[0].rejectionPercentage;
				
				// Acceptance Rate White

				Highcharts.chart('acceptancerare', {
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
					  pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
								  
								  '#bf05ff',	
								  '#F79C92'	
							  ],
						dataLabels: {
						  enabled: true,
						  distance: 4,
						  format: '<span>{point.name}</span><br>' +
						  '<span>{point.percentage:.0f} %</span>', 
						  style: {
							fontWeight: 'bold',
							color: 'black'
						  }
						},
						startAngle: -180,
						endAngle: 180,
						center: ['50%', '50%'],
						size: '100%'
					  }
					},
					series: [{
					  type: 'pie',
					  name: '',
					  innerSize: '50%',
					  data: [
					   
						['Rejected',rejectionPercentage],
						['Accepated',acceptancePercentage]
				   
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
		url: "hrms-dashboard-recruitment-conversion-rate-by-hr?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
			success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var length = allData.length;
				if(length == 1){
					$("#conversionRateDivId1").show();
					$("#conversionRateDivId2").hide();
					$("#conversionRateDivId3").hide();
				}
				
				if(length == 2){
					$("#conversionRateDivId1").hide();
					$("#conversionRateDivId2").show();					
					$("#conversionRateDivId3").hide();
				}
				
				if(length == 3){					
					$("#conversionRateDivId1").hide();
					$("#conversionRateDivId2").hide();
					$("#conversionRateDivId3").show();
				}
										
			}
		}, error: function(data) {
			console.log(data);
		}
	})


		
		
// Substring template helper for the responsive labels
Highcharts.Templating.helpers.substr = (s, from, length) =>
    s.substr(from, length);

// Create the chart
Highcharts.chart('hrmssalarydistribution', {

    chart: {
        type: 'heatmap',
        marginTop: 5,
        marginBottom: 55,
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
        categories: ['<50K', '51K-100K', '101K-200K', '201K-300K', '301K+' ] ,
		labels: {
            style: {
                fontSize: '10px' 
            }
        }  
    },

    yAxis: {
        categories: ['18-22', '23-27', '28-32', '33-37', '45+'],
        title: null,
        reversed: true,
		labels: {
            style: {
                fontSize: '10px' 
            }
        }  
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
        format: '<b>{series.xAxis.categories.(point.x)}</b> Salary<br>' +
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
				textOutline: false 
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
	
			
			
	
  //recruitment conversion rate 1
	Highcharts.chart('recruitmentConversionRate1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//recruitment conversion rate 2
	Highcharts.chart('recruitmentConversionRate2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#F79C92',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//recruitment conversion rate 3
	Highcharts.chart('recruitmentConversionRate3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#B422B6',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	
	
	//recruitment conversion rate 4
	Highcharts.chart('recruitmentConversionRate4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#B422B6',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	
	//recruitment conversion rate 5
	Highcharts.chart('recruitmentConversionRate5', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#B422B6',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	
	
	//recruitment conversion rate 6
	Highcharts.chart('recruitmentConversionRate6', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 150,
		},
		title: {
			text: '',
			style: { fontSize: '12px', },
			align: 'left'
		},
		subtitle: {
			text: '12%',
			style: { fontSize: '15px', fontWeight: 'bold', },
			verticalAlign: 'bottom',
			align: 'right'
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
			dashStyle: 'ShortDashDot',
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2,
				16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#B422B6',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	
	
  //Salary Distribution by Experience
/*
		
	Highcharts.chart('hrmssalarydistribution', {
    chart: {
        type: 'column',
        marginTop: 10,
        marginBottom: 110,
        plotBorderWidth: 1,
        height: 265
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


}



