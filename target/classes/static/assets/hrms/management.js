function managementHighChat(){
	var fromDate = $("#fromDate4").val();
	var toDate = $("#toDate4").val();
	var org = $("#managementHrmsOrganization").val();
	var orgDiv = $("#managementHrmsDivision").find('option:selected').text();
	var location = $("#managementHrmsLocation").find('option:selected').text();

	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
		
				$("#totalEmpManagementId").text(allData[0].totalEmpCount);
				$("#totalFemaleEmpManagementId").text(allData[0].femaleCount);
				$("#totalMaleEmpManagementId").text(allData[0].maleCount);
				$("#totalAttributionRateManagementId").text(allData[0].attritionRate);
				$("#totalHeadCntManagementId").text(allData[0].totalHeadCount);
				$("#totalNewHiresManagementId").text(allData[0].newHireCount);
				$("#totalVacancyManagementId").text(allData[0].totalVacancyCount);
				$("#salaryCostToGrossRevenueManagementId").text(allData[0].salaryCostToGrossRevenuePercentage);
										
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-emp-count-by-dept?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var deptNameList = [];
				var seriesData = [];
				var colorList = ['#F79C92', '#E976AD', '#DB5199', '#DB51C9', '#BF05FF']; // Extend this list as needed
				
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				    var item = allData[i];
				    deptNameList.push(item.deptName);
				    seriesData.push({
				        name: item.deptName,
				        data: [item.totalCount],
				        color: colorList[i % colorList.length]  // Cycle through the color list
				    });
				}
				
				Highcharts.chart('employeedept', {
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
				        categories: deptNameList,
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
				    series: seriesData // Dynamically set the series here
				});

				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	  
	  
	  
	  $.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-emp-count-by-age?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var ageRangeBetween18to24 = allData[0].ageRange_18_24;
				var ageRangeBetween24to30 = allData[0].ageRange_24_30;
				var ageRangeBetween30to40 = allData[0].ageRange_30_40;
				var ageRangeBetween40to50 = allData[0].ageRange_40_50;
				var ageRangeBetween50to65 = allData[0].ageRange_50_65;
								   
				Highcharts.chart('employeeage', {
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
				        categories: ['50-65','40-50','30-40','24-30','18-24'],  // Set the categories directly
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
				    series: [
				        {
				            name: '50-65',
				            data: [ageRangeBetween50to65],
				            color: '#F79C92'
				        },
				        
				        {
				            name: '40-50',
				            data: [ageRangeBetween40to50],
				            color: '#E976AD'
				        },
				        
				        {
				            name: '30-40',
				            data: [ageRangeBetween30to40],
				            color: '#DB5199'                
				        },
				        
				        {
				            name: '24-30',
				            data: [ageRangeBetween24to30],
				            color: '#DB51C9'
				        },
				        
				        {
				            name: '18-24',
				            data: [ageRangeBetween18to24],
				            color: '#BF05FF'
				        }, 
				    ]
				}); 							
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	/* $.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-emp-count-by-dept-experience?fromDate=" + fromDate + "&toDate=" + toDate + 
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
	})*/
	
	Highcharts.chart('employeexperience', {
			    chart: {
			        type: 'bar',
			        height: 250
			    },
			    title: {
			        text: ''
			    },
			    xAxis: {
				categories: ['Junior', 'Mid', 'Senior']
			        
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
			        enabled: false
			            },
				
			    series: [{
			        name: '',
			        data: [18, 12, 14],
					color:'#E1A4F6',
					dataLabels: {
			                    enabled: true,
			                    color:'#000000',
			                    style: {
			                      textOutline: false 
			                    }
			                }
			    }, {
			        name: '',
			        data: [20, 16, 18],
					color:'#bf05ff',
					dataLabels: {
			                enabled: true,
			                color:'#000000',
			                style: {
			                  textOutline: false 
			                }
			                }
			    },
				
				{
			        name: '',
			        data: [24, 22, 25],
					color:'#F79C92',
					dataLabels: {
			                enabled: true,
			                color:'#000000',
			                style: {
			                  textOutline: false 
			                }
			                }
			    },
				
				{
			        name: '',
			        data: [24, 22, 25],
					color:'#DB79FC',
					dataLabels: {
			              enabled: true,
			              color:'#000000',
			              style: {
			                textOutline: false 
			              }
			              }
			    },
				
				{
			        name: '',
			        data: [24, 22, 28],
					color:'#8E04BD',
					dataLabels: {
			              enabled: true,
			              color:'#000000',
			              style: {
			                textOutline: false 
			              }
			              }
			    }
				
				]
			});
	
	
	  
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-headcount-development?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var joiningList = [];
				var leavingList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					monthYearList.push(item.monthYear);
					joiningList.push(item.joiningCount);
					leavingList.push(item.leavingCount);
				}	
				
	
				//Headcount Development

				Highcharts.chart('hrmsheadcount', {
				    chart: {
				        type: 'spline',
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
				    credits: false,
				    xAxis: [{
				        categories: monthYearList,//['Q2 2021', 'Q3 2021', 'Q4 2021', 'Q1 2022', 'Q2 2022', 'Q3 2022', 'Q4 2022', 'Q1 2023', 'Q2 2023','Q3 2023'],
				        crosshair: true,
				        tickWidth: 1
				    }],
				    yAxis: [{ // Primary yAxis
				        labels: {
				            format: '{value} %',
				        },
				        min: 0,
				        title: {
				            text: 'Total',
				        }
				    }, { // Secondary yAxis
				        title: {
				            text: 'New',
				        },
				        labels: {
				            format: '{value} %',
				        }, opposite: true,
				    }],
				    tooltip: {
				        shared: true
				    },
				    plotOptions: {
				        dataLabels: {
				            enabled: true
				        }
				    },
				    series: [{
				        name: 'Lost',
				        type: 'spline',
				        data: leavingList,//[69.9, 71.5, 35.4, 54.3, 106.4, 129.2, 35.4, 54.3,64.3,75.2],
				        color: '#BF05FF'
				
				    }, {
				        name: 'New',
				        type: 'spline',
				        yAxis: 1,
				        data: joiningList,//[46.0, 36.9, 100.4, 130.4, 99.3, 29.5, 58.5, 102.3,110.2,115.4],
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
		url: "hrms-dashboard-management-emp-pay-by-dept?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				var deptNameList = [];
				var payList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					deptNameList.push(item.deptName);
					payList.push(item.totalSpendOnSalary);
				}		
				
				 //Employee Pay By Department
				 Highcharts.chart('hrmsemployeepay', {
				    chart: {
				        type: 'bar',
				        height: 250,
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
				        categories: deptNameList,//['Admin', 'Sales', 'Marketing', 'Finance', 'Technology'],
				        title: {
				            text: null
				        },
				        gridLineColor: 'transparent',
				    },
				    yAxis: {
				        min: 0,
				        title: {
				            text: ''
				        },
				        labels: {
				            enabled: false,
				        },
				        gridLineColor: 'transparent',
				    },
				    plotOptions: {
				        bar: {
				            dataLabels: {
				                enabled: true,
				              //  format: '₹{y}',
				                format: '{y}',
				                style: {
				                  color: 'black', 
				                  textOutline: 'none' 
				              }
				            },
				            pointWidth: 20,
				            groupPadding: 0
				        }
				    },
				    legend: {
				        enabled: false
				    },
				    series: [{
				        data: payList,//[254000, 357820, 425358, 489620, 512560],
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
		url: "hrms-dashboard-management-hire-by-emp-type?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
			//Emplyoee Type	
			
			var data = allData.map(function(item) {
			    return [item.employmentType, item.totalCount];
			});
			
			Highcharts.chart('hrmsemployeetype', {
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
			                  '#F79C92',
			                  '#bf05ff',
			                  '#B422B6'
			                 
			              ],
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
			      data: data,
			    }]
			  });	
												
			}
		}, error: function(data) {
			console.log(data);
		}
	}) 
	
	
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-management-salary-by-age-group?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var ageRange18To24Salary = allData[0].salaryForThisRange;
				var ageRange24To30Salary = allData[1].salaryForThisRange;
				var ageRange30To40Salary = allData[2].salaryForThisRange;
				var ageRange40To50Salary = allData[3].salaryForThisRange;
				var ageRange50To65Salary = allData[4].salaryForThisRange;				
				var totalEarning = allData[0].totalSalary;
				
				var ageRange18To24SalaryPercent = parseInt((ageRange18To24Salary/totalEarning)*100);
				var ageRange24To30SalaryPercent = parseInt((ageRange24To30Salary/totalEarning)*100);
				var ageRange30To40SalaryPercent = parseInt((ageRange30To40Salary/totalEarning)*100);
				var ageRange40To50SalaryPercent = parseInt((ageRange40To50Salary/totalEarning)*100);
				var ageRange50To65SalaryPercent = parseInt((ageRange50To65Salary/totalEarning)*100);
				
								
			Highcharts.chart('hrmssalaryage', {
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
			                  '#CA7CE5',
			                  '#bf05ff',
			                  '#B422B6',
			                  '#F79C92'
			                      
			              ],
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
			      data: [
			        ['18-24',ageRange18To24SalaryPercent],
			        ['24-30',ageRange24To30SalaryPercent],
			        ['30-40',ageRange30To40SalaryPercent],
			        ['40-50',ageRange40To50SalaryPercent],
			        ['50-65',ageRange50To65SalaryPercent]
			        
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
		url: "hrms-dashboard-management-emp-by-gender?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
			
				var maleCount = allData[0].maleCount;
				var femaleCount = allData[0].femaleCount;
				
				Highcharts.chart('hrmsemployeegender', {
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
				                  '#B422B6'
				                 
				              ],
				        dataLabels: {
				          enabled: true,
				          distance: 2,
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
				      
				        ['Male',maleCount],
				        ['Female',femaleCount],
				   
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
		url: "hrms-dashboard-management-emp-count-by-city?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var cityNameList = [];
				var maleCountList = [];
				var femaleCountList = [];
				// Prepare the deptNameList and seriesData
				for (var i = 0; i < allData.length; i++) {
				   var item = allData[i];
					cityNameList.push(item.cityName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
				}
				
				
				
										
				
				Highcharts.chart('hrmsemployeecity', {
				    chart: {
				        type: 'spline',
				        height:250
						
				    },
				    title: {
				               text: ''
				            },
				   
				    xAxis: {
				        categories: cityNameList,//['Delhi', 'Mumbai', 'Pune', 'Hyderabad', 'Bangalore', 'Kolkota'],
				            
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
				        },
						  gridLineColor: 'transparent',
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
				        name: 'Male',
				        marker: {
				            symbol: 'circle'
				        },
				        data: maleCountList,//[22, 18, 10, 14, 9, 5],
						color: '#BF05FF'
				
				    }, {
				        name: 'Female',
				        marker: {
				            symbol: 'circle'
				        },
				        data: femaleCountList,//[28, 20, 16, 8, 13, 6],
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
		url: "hrms-dashboard-management-emp-by-experience?fromDate=" + fromDate + "&toDate=" + toDate + 
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
				
				Highcharts.chart('hrmsemployeexp', {
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
		url: "hrms-dashboard-management-cost-per-emp?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
			
				var costPerEmployee = allData[0].perEmployeeCost;
				
				Highcharts.chart('hrmssalarycost', {
				
				    chart: {
				        type: 'gauge',
				        plotBackgroundColor: null,
				        plotBackgroundImage: null,
				        plotBorderWidth: 0,
				        plotShadow: false,
				        height: 250
				    },
				
				    title: {
				        text: ''
				    },
				
				    pane: {
				        startAngle: -90,
				        endAngle: 89.9,
				        background: null,
				        center: ['50%', '75%'],
				        size: '110%'
				    },
				
				    // the value axis
				    yAxis: {
				        min: 0,
				        max: 200,
				        tickPixelInterval: 72,
				        tickPosition: 'inside',
				        tickColor: Highcharts.defaultOptions.chart.backgroundColor || '#FFFFFF',
				        tickLength: 20,
				        tickWidth: 2,
				        minorTickInterval: null,
				        labels: {
				            distance: 20,
				            style: {
				                fontSize: '14px'
				            }
				        },
				        lineWidth: 0,
				        plotBands: [{
				            from: 0,
				            to: 120,
				            color: '#bf05ff', // green
				            thickness: 20
				        }, {
				            from: 120,
				            to: 160,
				            color: '#F79C92', // yellow
				            thickness: 20
				        }, {
				            from: 160,
				            to: 200,
				            color: '#B422B6', // red
				            thickness: 20
				        }]
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
				        name: 'Cost',
				        data: [Math.round(costPerEmployee / 1000)],
				        tooltip: {
				            valueSuffix: 'K per/Emp'
				        },
				        dataLabels: {
				            format: '{y}K per/Emp',
				            borderWidth: 0,
				            color: (
				                Highcharts.defaultOptions.title &&
				                Highcharts.defaultOptions.title.style &&
				                Highcharts.defaultOptions.title.style.color
				            ) || '#333333',
				            style: {
				                fontSize: '14px'
				            }
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
		}, error: function(data) {
			console.log(data);
		}
	})
	
	







  

//Emplyoee Salary Age Group



//Emplyoee By Gender

     


  // Employoee  Count By City

	


//Emplyoee By Experience
/*
Highcharts.chart('hrmsemployeexp', {
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
                  '#CA7CE5',
                  '#bf05ff',
                  '#B422B6',
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
        ['5-7 Yr',14],
        ['0-1 Yr',21],
        ['3-5 Yr',36],
        ['1-3 Yr',29]
        
        
      ]
    }]
  });		
 */
  

// Salary Cost	






/*
 Highcharts.chart('employeedept', {
        chart: {
          type: 'treemap',
          height: 90,
          marginBottom: 0,
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
            name: 'Sales',
            value: 50,
            colorValue: 5
          }, {
            name: 'Technical',
            value: 40,
            colorValue: 4
          }, {
            name: 'Marketing',
            value: 40,
            colorValue: 3
          }, {
            name: 'Admin',
            value: 40,
            colorValue: 2
          }, {
            name: 'Finance',
            value: 30,
            colorValue: 1
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
     
     
  
        
   /*     // Employee Age Chart
     
      Highcharts.chart('employeeage', {
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
            name: '22-30',
            value: 80,
            colorValue: 3
          }, {
            name: '31-40',
            value: 70,
            colorValue: 2
          }, {
            name: '41-58',
            value: 60,
            colorValue: 1
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

}