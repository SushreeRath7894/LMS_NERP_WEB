function talentHighchart() {
	
	var fromDate = $("#fromDate8").val();
	var toDate = $("#toDate8").val();
	var org = $("#talentHrmsOrganization").val();
	var orgDiv = $("#talentHrmsDivision").find('option:selected').text();
	var location = $("#talentHrmsLocation").find('option:selected').text();

	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
		
				$("#totalEmployeeInTalentId").text(allData[0].totalNoOfEmployee);
				$("#motnhlySalaryInTalentId").text(allData[0].monthlySalaryTotal);
				$("#totalVacancyInTalentId").text(allData[0].vacanciesTotal);
				$("#timetoFillInTalentId").text(allData[0].timeToFillInDays);
				$("#newHiresInTalentId").text(allData[0].newHiresTotalEmp);
				$("#netTrainingCostInTalentId").text(allData[0].netTrainingCostTotal);
				$("#costPerHireInTalentId").text(allData[0].costPerHires);
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-turnover-rate?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var deptNameList = [];
				var involuntaryCountList = [];
				var voluntaryCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					deptNameList.push(item.deptName);
					involuntaryCountList.push(item.involuntaryCount);
					voluntaryCountList.push(item.voluntaryCount);
				}
				
				Highcharts.chart('talentTurnoverRate', {
			    chart: {
			        type: 'bar',
			        zoomType: 'xy',
			        animation: false, // Disable animation for static chart
			        height: 200
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
			    xAxis: [{
			        categories: deptNameList, // Static department list
			        crosshair: true,
			        gridLineColor: 'transparent'
			    }],
			    yAxis: [{
			        labels: {
			            enabled: false
			        },
			        title: {
			            text: '',
			        },
			        lineColor: 'transparent',
			        gridLineColor: 'transparent'
			    }],
			    plotOptions: {
			        column: {
			            pointPadding: 0,
			            borderWidth: 0
			        }
			    },
			    legend: {
			        enabled: true
			    },
			    series: [{
			        name: 'Involuntary',
			        data: involuntaryCountList, // Static data for Involuntary
			        color: '#F79C92'
			    }, {
			        name: 'Voluntary',
			        data: voluntaryCountList, // Static data for Voluntary
			        color: '#BF05FF'
			    }]
			});
											
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-fired-talent-by-employment-period?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var lessThanOneYearFiredPercentage = allData[0].lessThanOneYearFiredPercentage;
				var betweenOneToTwoYearsFiredPercentage = allData[0].betweenOneToTwoYearsFiredPercentage;
				var betweenTwoToThreeYearsFiredPercentage = allData[0].betweenTwoToThreeYearsFiredPercentage;
				var betweenThreeToFourYearsFiredPercentage = allData[0].betweenThreeToFourYearsFiredPercentage;
				var moreThanFiveYearsFiredPercentage = allData[0].moreThanFiveYearsFiredPercentage;
				
				var firedPercentageList = [];
				
				firedPercentageList.push(lessThanOneYearFiredPercentage);
				firedPercentageList.push(betweenOneToTwoYearsFiredPercentage);
				firedPercentageList.push(betweenTwoToThreeYearsFiredPercentage);
				firedPercentageList.push(betweenThreeToFourYearsFiredPercentage);
				firedPercentageList.push(moreThanFiveYearsFiredPercentage);
		
					//fired Talents
				Highcharts.chart('firedTalents', {
					chart: {
						animated: true,
						height: 200
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
						categories: ['>1 Year', '1-2 Years', '2-3 Years', '3-4 Years', '5 Years+'],
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
						name: 'Fired Percentage',
						data: firedPercentageList,//[59, 83, 65, 228,300],
						color: '#F79C92',
					}, 
]
				});							
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-satisfaction-month-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
		
				var monthYearList = [];
				var talentSatisfactionList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					monthYearList.push(item.monthYear);
					talentSatisfactionList.push(item.talentSatisfactionScore);
				}
					
				//talent satisfaction (nfs)
				Highcharts.chart('talentSatisfactionNFS', {
					chart: {
						type: 'areaspline',
						zoomType: 'xy',
						animation: true,
						height: 216
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
						categories: monthYearList,//['Jan', 'Feb', 'Mar'],
						allowDecimals: false,
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},
					legend: { enabled: false, },
					plotOptions: {
						area: {
							marker: {
								enabled: false,
								symbol: 'circle',
								radius: 2,
								states: {
									hover: {
										enabled: true
									}
								}
							}
						}
					},
					series: [{
						name: 'Talent Satisfaction',
						data: talentSatisfactionList,//[10, 8, 10],
						color:'#BF05FF'
					}]
				});
															
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-trend-years-wise-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var lastSixMonthTalentTrendScore = allData[0].lastSixMonthTalentTrendScore;
				var lastOneYearTalentTrendScore = allData[0].lastOneYearTalentTrendScore;
				var lastTwoYearTalentTrendScore = allData[0].lastTwoYearTalentTrendScore;
				var lastFiveYearTalentTrendScore = allData[0].lastFiveYearTalentTrendScore;
				var lastSixMonthTalentTrendScoreList = [];
				lastSixMonthTalentTrendScoreList.push(lastSixMonthTalentTrendScore);
				$("#firstYearTrendId").text(lastOneYearTalentTrendScore);
				$("#secondYearTrendId").text(lastTwoYearTalentTrendScore);
				$("#thirdYearTrendId").text(lastFiveYearTalentTrendScore);
				


				
				  Highcharts.chart('trendLastSixMonths', {
				    chart: {
				        type: 'gauge',
				        height: 138,
				        animation: false,  // Disable animation
				        panning: false,    // Disable panning
				        zoomType: null,    // Disable zooming
				        events: {
				            load: function() {
				                this.pointer.reset(); // Make sure pointer doesn't move
				            }
				        }
				    },
				    navigation: {
				        buttonOptions: {
				            enabled: false // Disable navigation buttons (export, fullscreen, etc.)
				        }
				    },
				    colors: ['#f57e7a'],
				    title: {
				        text: lastSixMonthTalentTrendScore,
				        verticalAlign: 'bottom',
				    },
				    subtitle: {
				        text: ''
				    },
				    credits: {
				        enabled: false // Disable Highcharts credits
				    },
				    pane: {
				        startAngle: -148,
				        endAngle: 149.9,
				        background: null,
				        center: ['50%', '75%'],
				        size: '120%'
				    },
				
				    yAxis: {
				        min: 0,
				        max: 50,
				        lineWidth: 0,
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
				            color: '#B422B6',
				            thickness: 16
				        }, {
				            from: 22,
				            to: 30,
				            color: '#CA7CE5',
				            thickness: 16
				        }, {
				            from: 30,
				            to: 70,
				            color: '#F79C92',
				            thickness: 16
				        }]
				    },
				
				    series: [{
				        name: '',
				        data: lastSixMonthTalentTrendScoreList,//[22], // Static value
				        dataLabels: {
				            borderWidth: 0,
				            color: '#00f7ff',
				            style: {
				                fontSize: '13px'
				            },
				            enabled: false,
				        },
				        dial: {
				            radius: '100%',
				            backgroundColor: '#8f3936',
				            topWidth: 1,
				            baseWidth: 7,
				            baseLength: '5%',
				            rearLength: '0%'
				        },
				        pivot: {
				            radius: 5,
				            backgroundColor: '#8f3936',
				        },
				    }]
				});
									
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-ratings-month-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var talentRatingScoreList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					monthYearList.push(item.monthYear);
					talentRatingScoreList.push(item.talentRatingScore);
				}
		
		 

				Highcharts.chart('talentRating', {
				    chart: {
				        type: 'column',
				        height: 155,
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
				        categories: monthYearList,//['Jan', 'Feb', 'March', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'], // Categories for months
				        type: 'category',
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
				    legend: {
				        enabled: false
				    },
				    series: [{
				        name: '',
				        colors: ['#BF05FF'],
				        colorByPoint: true,
				        groupPadding: 0,
				        pointWidth: 30,
				        data: talentRatingScoreList,//[5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60], // Static data for each month
				        dataLabels: {
				            enabled: false,
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
		url: "hrms-dashboard-talent-by-rating-by-percentage?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var excellentRatingPercentage = allData[0].excellentRatingPercentage;
				var veryGoodRatingPercentage = allData[0].veryGoodRatingPercentage;
				var goodRatingPercentage = allData[0].goodRatingPercentage;
				var fairRatingPercentage = allData[0].fairRatingPercentage;
				var poorRatingPercentage = allData[0].poorRatingPercentage;
				
				
				$("#excellentTalentId").text(excellentRatingPercentage);
				$("#veryGoodTalentId").text(veryGoodRatingPercentage);
				$("#goodTalentId").text(goodRatingPercentage);
				$("#fairTalentId").text(fairRatingPercentage);
				$("#poorTalentId").text(poorRatingPercentage);
		
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-last-six-month-category-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var catNameList = [];
				var ratingScoreList = [];				
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					catNameList.push(item.catName);
					ratingScoreList.push(item.ratingScore);
				}
				
				//trend by category last 6 months
				Highcharts.chart('trendByCategory6Months', {
				    chart: {
				        polar: true,
				        type: 'line',
				        height: 250
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
				    pane: {
				        size: '80%'
				    },
				    xAxis: {
				        categories: catNameList,//['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5', 'Category 6'],
				        tickmarkPlacement: 'on',
				        lineWidth: 0,
				    },
				    yAxis: {
				        gridLineInterpolation: 'pentagon',
				        lineWidth: 0,
				        min: 0
				    },
				    legend: {
				        enabled: false,
				    },
				    series: [{
				        name: '',
				        data: ratingScoreList,//[10, 20, 30, 40, 50, 60],
				        pointPlacement: 'on',
				        color: '#BF05FF'
				    }],
				    responsive: {
				        rules: [{
				            condition: {
				                maxWidth: 500
				            },
				            chartOptions: {
				                pane: {
				                    size: '70%'
				                }
				            }
				        }]
				    }
				});

									
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	 
	 
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-last-one-year-category-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var catNameList = [];
				var ratingScoreList = [];				
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					catNameList.push(item.catName);
					ratingScoreList.push(item.ratingScore);
				}
		
				//trend by category 1 year
				Highcharts.chart('trendByCategoryOneYear', {
				    chart: {
				        polar: true,
				        type: 'line',
				        height: 250,
				        margin: 0
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
				    pane: {
				        size: '80%'
				    },
				    xAxis: {
				        categories: catNameList,//['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5', 'Category 6'],
				        tickmarkPlacement: 'on',
				        lineWidth: 0,
				        labels: { enabled: false }
				    },
				    yAxis: {
				        gridLineInterpolation: 'pentagon',
				        lineWidth: 0,
				        min: 0,
				        labels: { enabled: false }
				    },
				    legend: {
				        enabled: false,
				    },
				    series: [{
				        name: '',
				        data: ratingScoreList,//[15, 25, 35, 45, 55, 65],
				        pointPlacement: 'on',
				        color: '#F79C92'
				    }],
				    responsive: {
				        rules: [{
				            condition: {
				                maxWidth: 500
				            },
				            chartOptions: {
				                pane: {
				                    size: '70%'
				                }
				            }
				        }]
				    }
				});
				
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-last-two-year-category-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var catNameList = [];
				var ratingScoreList = [];				
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					catNameList.push(item.catName);
					ratingScoreList.push(item.ratingScore);
				}
				//trend by category 2 years
				Highcharts.chart('trendByCategoryTwoYears', {
				    chart: {
				        polar: true,
				        type: 'line',
				        height: 250,
				        margin: 0
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
				    pane: {
				        size: '80%'
				    },
				    xAxis: {
				        categories: catNameList,//['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5', 'Category 6'],
				        tickmarkPlacement: 'on',
				        lineWidth: 0,
				        labels: { enabled: false }
				    },
				    yAxis: {
				        gridLineInterpolation: 'pentagon',
				        lineWidth: 0,
				        min: 0,
				        labels: { enabled: false }
				    },
				    legend: {
				        enabled: false,
				    },
				    series: [{
				        name: '',
				        data: ratingScoreList,//[20, 30, 40, 50, 60, 70],
				        pointPlacement: 'on',
				        color: '#56156C'
				    }],
				    responsive: {
				        rules: [{
				            condition: {
				                maxWidth: 500
				            },
				            chartOptions: {
				                pane: {
				                    size: '70%'
				                }
				            }
				        }]
				    }
				});
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-talent-last-three-year-category-wise?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var catNameList = [];
				var ratingScoreList = [];				
					
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					catNameList.push(item.catName);
					ratingScoreList.push(item.ratingScore);
				}
				
				
				//trend by category 5 years
				Highcharts.chart('trendByCategoryFiveYears', {
				    chart: {
				        polar: true,
				        type: 'line',
				        height: 250,
				        margin: 0
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
				    pane: {
				        size: '80%'
				    },
				    xAxis: {
				        categories: catNameList,//['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5', 'Category 6'],
				        tickmarkPlacement: 'on',
				        lineWidth: 0,
				        labels: { enabled: false }
				    },
				    yAxis: {
				        gridLineInterpolation: 'pentagon',
				        lineWidth: 0,
				        min: 0,
				        labels: { enabled: false }
				    },
				    legend: {
				        enabled: false,
				    },
				    series: [{
				        name: '',
				        data: ratingScoreList,//[25, 35, 45, 55, 65, 75],
				        pointPlacement: 'on',
				        color: '#B422B6'
				    }],
				    responsive: {
				        rules: [{
				            condition: {
				                maxWidth: 500
				            },
				            chartOptions: {
				                pane: {
				                    size: '70%'
				                }
				            }
				        }]
				    }
				});

																
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	

	







}