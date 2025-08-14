function teamHighChart (){
var org = $("#teamOrganization").val();
var orgDiv = $("#teamDivision").find('option:selected').text();
var location = $("#teamLocation").find('option:selected').text();
var fromDate = $("#fromDate8").val();
var toDate = $("#toDate8").val();

var calendar1; 
var calendar2; 
var calendar3; 
var calendar4; 

$.ajax({
    url: "dashboard-team-safety-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
    "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        var jsonData = response.body ? JSON.parse(response.body) : {};
        var allData = jsonData.dashboardData;
        var safetyScoresMap = {};
        for (var i = 0; i < allData.length; i++) {
            var item = allData[i];
            var safetyScore = item.safetyScore;
            var dateRange = item.dateRange; 
            safetyScoresMap[dateRange] = safetyScore;  
        }

        if (allData && allData.length > 0) {
            fromDate = allData[0].fromDate;
            toDate = allData[0].toDate;
            renderCalendarSafety(fromDate, toDate, safetyScoresMap);
        } else {
            console.error("No data found in the response.");
        }
    },
    error: function(error) {
        console.error("Error fetching data:", error);
    }
});


function renderCalendarSafety(fromDate, toDate, safetyScoresMap) {
    var toDateObj = new Date(toDate);
    toDateObj.setDate(toDateObj.getDate() + 1);  // Add 1 day
    toDate = toDateObj.toISOString().split('T')[0];

    var calendarSafety = document.getElementById('safetyCalendar');
    if (!calendar1) {
        calendar1 = new FullCalendar.Calendar(calendarSafety, {
            validRange: {
                start: fromDate,
                end: toDate
            },

            dayCellDidMount: function(info) {
                $.each(safetyScoresMap, function(dateRange, score) {
                    var dateParts = dateRange.split('-');
                    var year = parseInt(dateParts[0], 10);
                    var month = parseInt(dateParts[1], 10) - 1; 
                    var day = parseInt(dateParts[2], 10);
                    
                    var safetyDate = new Date(year, month, day);
                    
                    if (info.date.getFullYear() === safetyDate.getFullYear() &&
                        info.date.getMonth() === safetyDate.getMonth() &&
                        info.date.getDate() === safetyDate.getDate()) {
	
	
                        if (score >= 85 && score <= 100) {
                            info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                            info.el.style.color = 'white';
                        } else if (score >= 75 && score < 85) {
                            info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                            info.el.style.color = 'white';
                        } else if (score >= 60 && score < 75) {
                            info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                            info.el.style.color = 'white';
                        } else if (score < 60) {
                            info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                            info.el.style.color = 'white';
                        }
                    }
                });
            }
        });

        calendar1.render();
    } else {
        calendar1.setOption('validRange', {
            start: fromDate,
            end: toDate
        });
        updateSafetyScores(safetyScoresMap);
    }
}

function updateSafetyScores(safetyScoresMap) {
    var allDayEls = document.querySelectorAll('.fc-daygrid-day');
    allDayEls.forEach(function(dayEl) {
        var dateStr = dayEl.getAttribute('data-date'); 
        if (safetyScoresMap[dateStr]) {
            var score = safetyScoresMap[dateStr];
            if (score >= 85 && score <= 100) {
                dayEl.style.backgroundColor = 'rgb(191, 5, 255)';
                dayEl.style.color = 'white';
            } else if (score >= 75 && score < 85) {
                dayEl.style.backgroundColor = 'rgb(145, 140, 223)';
                dayEl.style.color = 'white';
            } else if (score >= 60 && score < 75) {
                dayEl.style.backgroundColor = 'rgb(242, 139, 103)';
                dayEl.style.color = 'white';
            } else if (score < 60) {
                dayEl.style.backgroundColor = 'rgb(255, 0, 0)';
                dayEl.style.color = 'white';
            }
        }
    });
}
 
///////////////////////////////////Safety Calender End////////////////////////////////////

///////////////////////////////////Team Calender Start//////////////////////////////////
$.ajax({
		url: "dashboard-team-quality-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = response.body ? JSON.parse(response.body) : {};
	        var allData = jsonData.dashboardData;
	        var teamScoresMap = {};
	
	        for (var i = 0; i < allData.length; i++) {
	            var item = allData[i];
	            var qualityScore = item.qualityScore;
	            var dateRange = item.dateRange; 
	            teamScoresMap[dateRange] = qualityScore;  
	        }
	        if (allData && allData.length > 0) {
	            fromDate = allData[0].fromDate;
	            toDate = allData[0].toDate;
	            renderCalendarQuality(fromDate, toDate, teamScoresMap);
	        } else {
	            console.error("No data found in the response.");
	        }
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
function renderCalendarQuality(fromDate, toDate, teamScoresMap) {
    var toDateObj = new Date(toDate);
    toDateObj.setDate(toDateObj.getDate() + 1);  // Add 1 day
    toDate = toDateObj.toISOString().split('T')[0];

    var calendarQuality = document.getElementById('qualityCalendar');
    if (!calendar2) {
        calendar2 = new FullCalendar.Calendar(calendarQuality, {
            validRange: {
                start: fromDate,
                end: toDate
            },

            dayCellDidMount: function(info) {
                $.each(teamScoresMap, function(dateRange, score) {
                    var dateParts = dateRange.split('-');
                    var year = parseInt(dateParts[0], 10);
                    var month = parseInt(dateParts[1], 10) - 1;
                    var day = parseInt(dateParts[2], 10);

                    var qualityDate = new Date(year, month, day);

                    if (info.date.getFullYear() === qualityDate.getFullYear() &&
                        info.date.getMonth() === qualityDate.getMonth() &&
                        info.date.getDate() === qualityDate.getDate()) {

                        if (score >= 85 && score <= 100) {
                            info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                            info.el.style.color = 'white';
                        } else if (score >= 75 && score < 85) {
                            info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                            info.el.style.color = 'white';
                        } else if (score >= 60 && score < 75) {
                            info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                            info.el.style.color = 'white';
                        } else if (score < 60) {
                            info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                            info.el.style.color = 'white';
                        }
                    }
                });
            }
        });

        calendar2.render();
    } else {
        calendar2.setOption('validRange', {
            start: fromDate,
            end: toDate
        });
        updateQualityScores(teamScoresMap);
    }
}

function updateQualityScores(teamScoresMap) {
    var allDayEls = document.querySelectorAll('.fc-daygrid-day');
    allDayEls.forEach(function(dayEl) {
        var dateStr = dayEl.getAttribute('data-date');
        if (teamScoresMap[dateStr]) {
            var score = teamScoresMap[dateStr];
            if (score >= 85 && score <= 100) {
                dayEl.style.backgroundColor = 'rgb(191, 5, 255)';
                dayEl.style.color = 'white';
            } else if (score >= 75 && score < 85) {
                dayEl.style.backgroundColor = 'rgb(145, 140, 223)';
                dayEl.style.color = 'white';
            } else if (score >= 60 && score < 75) {
                dayEl.style.backgroundColor = 'rgb(242, 139, 103)';
                dayEl.style.color = 'white';
            } else if (score < 60) {
                dayEl.style.backgroundColor = 'rgb(255, 0, 0)';
                dayEl.style.color = 'white';
            }
        }
    });
}
	
///////////////////////////////////Team Calender End////////////////////////////////////


///////////////////////////////////Delivery Calender Start//////////////////////////////////
$.ajax({
		url: "dashboard-team-delivery-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = response.body ? JSON.parse(response.body) : {};
	        var allData = jsonData.dashboardData;
	        var deliveryScoresMap = {};
	        for (var i = 0; i < allData.length; i++) {
	            var item = allData[i];
	            var deliveryScore = item.deliveryScore;
	            var dateRange = item.dateRange; 
	            deliveryScoresMap[dateRange] = deliveryScore;  
	        }
	
	        if (allData && allData.length > 0) {
	            fromDate = allData[0].fromDate;
	            toDate = allData[0].toDate;
	            renderCalendarDelivery(fromDate, toDate, deliveryScoresMap);
	        } else {
	            console.error("No data found in the response.");
	        }
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
function renderCalendarDelivery(fromDate, toDate, deliveryScoresMap) {
    var toDateObj = new Date(toDate);
    toDateObj.setDate(toDateObj.getDate() + 1);
    toDate = toDateObj.toISOString().split('T')[0];

    var calendarDelivery = document.getElementById('deliveryCalendar');
    if (!calendar3) {
        calendar3 = new FullCalendar.Calendar(calendarDelivery, {
            validRange: {
                start: fromDate,
                end: toDate
            },

            dayCellDidMount: function(info) {
                $.each(deliveryScoresMap, function(dateRange, score) {
                    var dateParts = dateRange.split('-');
                    var year = parseInt(dateParts[0], 10);
                    var month = parseInt(dateParts[1], 10) - 1; 
                    var day = parseInt(dateParts[2], 10);
                    var deliveryDate = new Date(year, month, day);
                    if (info.date.getFullYear() === deliveryDate.getFullYear() &&
                        info.date.getMonth() === deliveryDate.getMonth() &&
                        info.date.getDate() === deliveryDate.getDate()) {

                        if (score >= 85 && score <= 100) {
                            info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                            info.el.style.color = 'white';
                        } else if (score >= 75 && score < 85) {
                            info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                            info.el.style.color = 'white';
                        } else if (score >= 60 && score < 75) {
                            info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                            info.el.style.color = 'white';
                        } else if (score < 60) {
                            info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                            info.el.style.color = 'white';
                        }
                    }
                });
            }
        });
        calendar3.render();
    } else {
        calendar3.setOption('validRange', {
            start: fromDate,
            end: toDate
        });
        updateDeliveryScores(deliveryScoresMap);
    }
}

function updateDeliveryScores(deliveryScoresMap) {
    var allDayEls = document.querySelectorAll('.fc-daygrid-day');
    allDayEls.forEach(function(dayEl) {
        var dateStr = dayEl.getAttribute('data-date'); 
        if (deliveryScoresMap[dateStr]) {
            var score = deliveryScoresMap[dateStr];
            if (score >= 85 && score <= 100) {
                dayEl.style.backgroundColor = 'rgb(191, 5, 255)';
                dayEl.style.color = 'white';
            } else if (score >= 75 && score < 85) {
                dayEl.style.backgroundColor = 'rgb(145, 140, 223)';
                dayEl.style.color = 'white';
            } else if (score >= 60 && score < 75) {
                dayEl.style.backgroundColor = 'rgb(242, 139, 103)';
                dayEl.style.color = 'white';
            } else if (score < 60) {
                dayEl.style.backgroundColor = 'rgb(255, 0, 0)';
                dayEl.style.color = 'white';
            }
        }
    });
}
	
///////////////////////////////////Delivery Calender End////////////////////////////////////



///////////////////////////////////Cost Calender Start//////////////////////////////////
$.ajax({
		url: "dashboard-team-cost-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = response.body ? JSON.parse(response.body) : {};
	        var allData = jsonData.dashboardData;
	        var costScoresMap = {};
	        for (var i = 0; i < allData.length; i++) {
	            var item = allData[i];
	            var costScore = item.costScore;
	            var dateRange = item.dateRange; 
	            costScoresMap[dateRange] = costScore;  
	        }
	
	        if (allData && allData.length > 0) {
	            fromDate = allData[0].fromDate;
	            toDate = allData[0].toDate;
	            renderCalendarCost(fromDate, toDate, costScoresMap);
	        } else {
	            console.error("No data found in the response.");
	        }
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
function renderCalendarCost(fromDate, toDate, costScoresMap) {
    var toDateObj = new Date(toDate);
    toDateObj.setDate(toDateObj.getDate() + 1); 
    toDate = toDateObj.toISOString().split('T')[0];

    var costQuality = document.getElementById('costCalendar');
    if (!calendar4) {
        calendar4 = new FullCalendar.Calendar(costQuality, {
            validRange: {
                start: fromDate,
                end: toDate
            },

            dayCellDidMount: function(info) {
                $.each(costScoresMap, function(dateRange, score) {
                    var dateParts = dateRange.split('-');
                    var year = parseInt(dateParts[0], 10);
                    var month = parseInt(dateParts[1], 10) - 1; 
                    var day = parseInt(dateParts[2], 10);

                    var costDate = new Date(year, month, day);
                    if (info.date.getFullYear() === costDate.getFullYear() &&
                        info.date.getMonth() === costDate.getMonth() &&
                        info.date.getDate() === costDate.getDate()) {

                        if (score >= 85 && score <= 100) {
                            info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                            info.el.style.color = 'white';
                        } else if (score >= 75 && score < 85) {
                            info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                            info.el.style.color = 'white';
                        } else if (score >= 60 && score < 75) {
                            info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                            info.el.style.color = 'white';
                        } else if (score < 60) {
                            info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                            info.el.style.color = 'white';
                        }
                    }
                });
            }
        });
        calendar4.render();
    } else {
        calendar4.setOption('validRange', {
            start: fromDate,
            end: toDate
        });
        updateCostScores(costScoresMap);
    }
}

function updateCostScores(costScoresMap) {
    var allDayEls = document.querySelectorAll('.fc-daygrid-day');
    allDayEls.forEach(function(dayEl) {
        var dateStr = dayEl.getAttribute('data-date'); 
        if (costScoresMap[dateStr]) {
            var score = costScoresMap[dateStr];
            if (score >= 85 && score <= 100) {
                dayEl.style.backgroundColor = 'rgb(191, 5, 255)';
                dayEl.style.color = 'white';
            } else if (score >= 75 && score < 85) {
                dayEl.style.backgroundColor = 'rgb(145, 140, 223)';
                dayEl.style.color = 'white';
            } else if (score >= 60 && score < 75) {
                dayEl.style.backgroundColor = 'rgb(242, 139, 103)';
                dayEl.style.color = 'white';
            } else if (score < 60) {
                dayEl.style.backgroundColor = 'rgb(255, 0, 0)';
                dayEl.style.color = 'white';
            }
        }
    });
}
	
///////////////////////////////////Cost Calender End////////////////////////////////////
      

	
	$.ajax({
		url: "dashboard-team-notes?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
				
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			    
			    var createdDate = item.createdDate;
			    var description = item.description;
			    
			      var teamNoteString = `<div class="d-flex w-100 mt-9 gap-2">
		              <div class="team-tn-icon"><i class="fa-solid fa-file"></i></div>
		              <div class="team-tn-txt-cont text-start">
		                <p class="team-tn-date">${createdDate}</p>
		                <p class="team-tn-txt">${description}</p>
		              </div>
		            </div>`;
		            
		            $("#teamNotesId").append(teamNoteString);
					    
					}
            
	
				
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-team-safety-summary-mtd-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var totalMinorCnt = allData[0].totalMinorIssues;
			var sinceTotalLastMinorDaysCnt = allData[0].sinceLastMinorIssueDays;
			var lastFiveYearsIssueCnt = allData[0].inLastFiveYearIssues;
			var lastTwoYearsCntIssue = allData[0].inLastTwoYearIssues;
			
			$("#totalMinorCntId").text(totalMinorCnt + " Issues");
			$("#sinceTotalLastMinorDaysCntId").text(sinceTotalLastMinorDaysCnt + " Days");
			$("#lastFiveYearsIssueCntId").text(lastFiveYearsIssueCnt + " Issues");
			$("#lastTwoYearsCntIssueId").text(lastTwoYearsCntIssue + " Issues");
						
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-team-safety-corrective-action?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var safetyCorrectiveMajorCount = allData[0].safetyMajorCntTotal;
			var safetyCorrectiveMinorCount = allData[0].safetyMinorCntTotal;
			var safetyCorrectiveImprovementCount = allData[0].safetyImprovementCntTotal;
			var safetyCorrectiveObservationCount = allData[0].safetyObservationCntTotal;
			
			$("#safetyCorrectiveMajorCountId").text(safetyCorrectiveMajorCount);
			$("#safetyCorrectiveMinorCountId").text(safetyCorrectiveMinorCount);
			$("#safetyCorrectiveImprovementCountId").text(safetyCorrectiveImprovementCount);
			$("#safetyCorrectiveObservationCountId").text(safetyCorrectiveObservationCount);
			
				
			
		},
		error: function(error) {
			console.error(error);
		}
	});



	$.ajax({
		url: "dashboard-team-quality-corrective-action?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var qualityCorrectiveMajorCount = allData[0].qualityMajorCntTotal;
			var qualityCorrectiveMinorCount = allData[0].qualityMinorCntTotal;
			var qualityCorrectiveImprovementCount = allData[0].qualityImprovementCntTotal;
			var qualityCorrectiveObservationCount = allData[0].qualityObservationCntTotal;
			
			$("#qualityCorrectiveMajorCountId").text(qualityCorrectiveMajorCount);
			$("#qualityCorrectiveMinorCountId").text(qualityCorrectiveMinorCount);
			$("#qualityCorrectiveImprovementCountId").text(qualityCorrectiveImprovementCount);
			$("#qualityCorrectiveObservationCountId").text(qualityCorrectiveObservationCount);
			
		},
		error: function(error) {
			console.error(error);
		}
	});
		
	$.ajax({
		url: "dashboard-team-delivery-corrective-action?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var deliveryCorrectiveMajorCount = allData[0].deliveryMajorCntTotal;
			var deliveryCorrectiveMinorCount = allData[0].deliveryMinorCntTotal;
			var deliveryCorrectiveImprovementCount = allData[0].deliveryImprovementCntTotal;
			var deliveryCorrectiveObservationCount = allData[0].deliveryObservationCntTotal;
			
			$("#deliveryCorrectiveMajorCountId").text(deliveryCorrectiveMajorCount);
			$("#deliveryCorrectiveMinorCountId").text(deliveryCorrectiveMinorCount);
			$("#deliveryCorrectiveImprovementCountId").text(deliveryCorrectiveImprovementCount);
			$("#deliveryCorrectiveObservationCountId").text(deliveryCorrectiveObservationCount);		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-team-quality-trend?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var toDateRangeList = [];
			var qualityTrendScoreList = [];
			for (var i = allData.length - 1; i >= 0; i--) {				
			    var item = allData[i];				
			    toDateRangeList.push(item.toDateRange);
			    qualityTrendScoreList.push(item.qualityTrendScore);
			}

			
			   //Teams Quality Trend
	      Highcharts.chart('teamsQualityTrend', {
	        chart: {
	          type: 'scatter',
	          height: 208
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
	        legend: { enabled: false },
	        credits: { enabled: false },
	  
	        xAxis: {
	          categories: toDateRangeList,
	          title: {
	            text: ''
	          },
	          labels: { style: { fontSize: '10px' }, rotation: -45 }
	        },
	        yAxis: {
	          min: 0,
	          max: 100,
	          title: {
	            text: ''
	          },
	          labels: { style: { fontSize: '10px' } },
	          plotLines: [{
	            color: '#b505ff',
	            value: 90,
	            width: 2,
	            label: {
	              enabled: false,
	            }
	          }, {
	            color: '#f58d68',
	            value: 75,
	            width: 2,
	            label: {
	              enabled: false,
	            }
	          }]
	        },
	        series: [{
	          name: 'Observations',
	          data: qualityTrendScoreList,
	          marker: {
	            radius: 3,
	            symbol: 'circle',
	            fillColor: '#56156c',
	          }
	        }]
	      });
				
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	  
  
	
	$.ajax({
		url: "dashboard-team-weekly-scheduled-attainment?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var toDateRangeList = [];
			var weeklyScheduleAttainmentList = [];
			for (var i = allData.length - 1; i >= 0; i--) {				
			    var item = allData[i];				
			    toDateRangeList.push(item.toDateRange);
			    weeklyScheduleAttainmentList.push(item.weeklyScheduleAttainment);
			}

			
			  //Teams Weekly Schedule Attainment
		      Highcharts.chart('teamsWeeklyScheduleAttainment', {
		        chart: {
		          type: 'scatter',
		          height: 208
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
		        legend: { enabled: false },
		        credits: { enabled: false },
		  
		        xAxis: {
		          categories: toDateRangeList,
		          title: {
		            text: ''
		          },
		          labels: { style: { fontSize: '10px' }, rotation: -45 }
		        },
		        yAxis: {
		          min: 0,
		          max: 100,
		          title: {
		            text: ''
		          },
		          labels: { style: { fontSize: '10px' } },
		          plotLines: [{
		            color: '#b505ff',
		            value: 90,
		            width: 2,
		            label: {
		              enabled: false,
		            }
		          }, {
		            color: '#f58d68',
		            value: 75,
		            width: 2,
		            label: {
		              enabled: false,
		            }
		          }]
		        },
		        series: [{
		          name: 'Observations',
		          data: weeklyScheduleAttainmentList,
		          marker: {
		            radius: 3,
		            symbol: 'circle',
		            fillColor: '#56156c',
		          }
		        }]
		      });
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	  
  
	
	$.ajax({
		url: "dashboard-team-cost-trend?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var toDateRangeList = [];
			var costTrendScoreList = [];
			for (var i = allData.length - 1; i >= 0; i--) {				
			    var item = allData[i];				
			    toDateRangeList.push(item.toDateRange);
			    costTrendScoreList.push(item.costTrendScore);
			}
	
			
			 Highcharts.chart('teamsCostsTrend', {
	        chart: {
	          type: 'scatter',
	          height: 208
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
	        legend: { enabled: false },
	        credits: { enabled: false },
	  
	        xAxis: {
	          categories: toDateRangeList,
	          title: {
	            text: ''
	          },
	          labels: { style: { fontSize: '10px' }, rotation: -45 }
	        },
	        yAxis: {
	          min: 0,
	          max: 100,
	          title: {
	            text: ''
	          },
	          labels: { style: { fontSize: '10px' } },
	          plotLines: [{
	            color: '#b505ff',
	            value: 90,
	            width: 2,
	            label: {
	              enabled: false,
	            }
	          }, {
	            color: '#f58d68',
	            value: 75,
	            width: 2,
	            label: {
	              enabled: false,
	            }
	          }]
	        },
	        series: [{
	          name: 'Observations',
	          data: costTrendScoreList,
	          marker: {
	            radius: 3,
	            symbol: 'circle',
	            fillColor: '#56156c',
	          }
	        }]
	      });
			
				
			},
			error: function(error) {
				console.error(error);
			}
		});
	
	    //Teams Costs Trend
     
	
	$.ajax({
		url: "dashboard-team-top-safety-concerns?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-team-top-defect-categories?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			$("#topDefectCategoriesTeamId").empty();		
			for (var i = allData.length - 1; i >= 0; i--) {				
			    var item = allData[i];				
			 
					
					
			var topDefectString = `<div class="d-flex justify-content-between w-100">
              <p class="ovrvw-tsp-text">${item.impactName}</p>
              <p class="ovrvw-tsp-text">${item.totalreturnPercentByImpact}</p>
            </div>
            <div class="progress w-100" style="height: 5px;">
              <div class="progress-bar" role="progressbar" style="width: ${item.totalreturnPercentByImpact}%; background-color: #bf05ff;"
                aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
              </div>
            </div>`;
            
            $("#topDefectCategoriesTeamId").append(topDefectString);
			    
			}
		
	

			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-team-daily-scheduled-attainment?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-team-top-lines?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			var totalLength = 0;
			totalLength = allData.length;
			
			var machineName1 = "";
			var machineName2 = "";
			var machineName3 = "";
			var machineName4 = "";
			var machineName5 = "";
			
			
			var machine1production = "";
			var machine2production = "";
			var machine3production = "";
			var machine4production = "";
			var machine5production = "";
			
			if(totalLength>0){
				machineName1 = allData[0].machineName;
				machine1production = allData[0].convertionInMtTotalProduction;
			}
			if(totalLength>1){
				machineName2 = allData[1].machineName;
				machine2production = allData[1].convertionInMtTotalProduction;
			}
			if(totalLength>2){
				machineName3 = allData[2].machineName;
				machine3production = allData[2].convertionInMtTotalProduction;
			}
			if(totalLength>3){
				machineName4 = allData[3].machineName;
				machine4production = allData[3].convertionInMtTotalProduction;
			}
			if(totalLength>4){
				machineName5 = allData[4].machineName;
				machine5production = allData[4].convertionInMtTotalProduction;
			}
				
		
			
			
			
			var totalProductionInTone = allData[0].totalProduction;
			if(machine1production == 0){
				machine1ProductionPercentage=0.00;
			}else{
				machine1ProductionPercentage = ((machine1production/totalProductionInTone)*100);
			}
			
			if(machine2production == 0){
				machine2ProductionPercentage=0.00;
			}else{
				machine2ProductionPercentage = ((machine2production/totalProductionInTone)*100);
			}
			
			if(machine3production == 0){
				machine3ProductionPercentage=0.00;
			}else{
				machine3ProductionPercentage = ((machine3production/totalProductionInTone)*100);
			}
			
			if(machine4production == 0){
				machine4ProductionPercentage=0.00;
			}else{
				machine4ProductionPercentage = ((machine4production/totalProductionInTone)*100);
			}
			
			if(machine5production == 0){
				machine5ProductionPercentage=0.00;
			}else{
				machine5ProductionPercentage = ((machine5production/totalProductionInTone)*100);
				
			}
						
			 
			$("#topLinesStringTeamId").empty();		
					
					
              
			var topLinesString = `
			  <div class="d-flex justify-content-between w-100">
			    <p class="ovrvw-tsp-text">${machineName1}</p>
			    <p class="ovrvw-tsp-text">${machine1ProductionPercentage.toFixed(2)}</p>
			  </div>
			  <div class="progress w-100" style="height: 5px;">
			    <div class="progress-bar" role="progressbar" style="width: ${machine1ProductionPercentage.toFixed(2)}%; background-color: #bf05ff;"
			      aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
			    </div>
			  </div>
			  
			  <div class="d-flex justify-content-between w-100">
			    <p class="ovrvw-tsp-text">${machineName2}</p>
			    <p class="ovrvw-tsp-text">${machine2ProductionPercentage.toFixed(2)}</p>
			  </div>
			  <div class="progress w-100" style="height: 5px;">
			    <div class="progress-bar" role="progressbar" style="width: ${machine2ProductionPercentage.toFixed(2)}%; background-color: #bf05ff;"
			      aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
			    </div>
			  </div>
			  
			  <div class="d-flex justify-content-between w-100">
			    <p class="ovrvw-tsp-text">${machineName3}</p>
			    <p class="ovrvw-tsp-text">${machine3ProductionPercentage.toFixed(2)}</p>
			  </div>
			  <div class="progress w-100" style="height: 5px;">
			    <div class="progress-bar" role="progressbar" style="width: ${machine3ProductionPercentage.toFixed(2)}%; background-color: #bf05ff;"
			      aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
			    </div>
			  </div>
			  
			  <div class="d-flex justify-content-between w-100">
			    <p class="ovrvw-tsp-text">${machineName4}</p>
			    <p class="ovrvw-tsp-text">${machine4ProductionPercentage.toFixed(2)}</p>
			  </div>
			  <div class="progress w-100" style="height: 5px;">
			    <div class="progress-bar" role="progressbar" style="width: ${machine4ProductionPercentage.toFixed(2)}%; background-color: #bf05ff;"
			      aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
			    </div>
			  </div>
			  
			  <div class="d-flex justify-content-between w-100">
			    <p class="ovrvw-tsp-text">${machineName5}</p>
			    <p class="ovrvw-tsp-text">${machine5ProductionPercentage.toFixed(2)}</p>
			  </div>
			  <div class="progress w-100" style="height: 5px;">
			    <div class="progress-bar" role="progressbar" style="width: ${machine5ProductionPercentage.toFixed(2)}%; background-color: #bf05ff;"
			      aria-valuenow="58" aria-valuemin="0" aria-valuemax="100">
			    </div>
			  </div>
			`;
			
			$("#topLinesStringTeamId").append(topLinesString);
			
					
		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	


	$.ajax({
		url: "dashboard-team-cost-corrective-action?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	


    Highcharts.chart('teamsDailySchedulesAttainment', {
        chart: {
          zoomType: 'xy',
          animation: true,
          height: 208
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
        legend: { enabled: false },
        credits: { enabled: false },
        xAxis: [{
          categories: ['S', 'M', 'T', 'W', 'T', 'F', 'S'],
          crosshair: true,
          labels: {
            style: {
              fontSize: '9px'
            }
          }
        }],
        yAxis: [{
          title: {
            text: '',
          },
          labels: {
            style: {
              fontSize: '9px'
            }
          }
        },
        ],
        series: [{
          name: '',
          type: 'column',
          data: [10, 30, 60, 90, 120, 180, 0], color: '#ca7ce5',
  
        },
        {
          name: 'Target',
          type: 'line',
          data: [7, 25, 55, 85, 110, 160, 160], color: '#ea34a1',
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false,
          },
        }]
      });
 
  
}





 
      
/////////////////////////////////Quality Calender Start//////////////////////////////      
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('QualityCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      
        // Set the date range for the calendar view
        validRange: {
            start: '2025-01-01',
            end: '2025-12-31'
        },

        dayCellDidMount: function(info) {
            // Check for a specific date (2025-01-05)
            if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 1) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 2) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 3) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 4) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                info.el.style.color = 'white'; // Ensure text is white
            }
        }
    });

    calendar.render();
});
///////////////////////////////////Quality Calender End////////////////////////////////////


    
/////////////////////////////////Delivery Calender Start//////////////////////////////      
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('DeliveryCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      
        // Set the date range for the calendar view
        validRange: {
            start: '2025-01-01',
            end: '2025-12-31'
        },

        dayCellDidMount: function(info) {
            // Check for a specific date (2025-01-05)
            if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 1) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 2) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 3) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 4) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                info.el.style.color = 'white'; // Ensure text is white
            }
        }
    });

    calendar.render();
});
///////////////////////////////////Delivery Calender End////////////////////////////////////


/////////////////////////////////Cost Calender Start//////////////////////////////      
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('CostCalendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      
        // Set the date range for the calendar view
        validRange: {
            start: '2025-01-01',
            end: '2025-12-31'
        },

        dayCellDidMount: function(info) {
            // Check for a specific date (2025-01-05)
            if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 1) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 2) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 3) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                info.el.style.color = 'white'; // Ensure text is white
            }
            
             if (info.date.getFullYear() === 2025 && info.date.getMonth() === 0 && info.date.getDate() === 4) {
                // Apply custom style to the whole day cell
                info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                info.el.style.color = 'white'; // Ensure text is white
            }
        }
    });

    calendar.render();
});
///////////////////////////////////Cost Calender End////////////////////////////////////



var calendar1, calendar2, calendar3, calendar4;

function renderCalendar1(fromDate, toDate, location, org, orgDiv) {
    $.ajax({
        url: "dashboard-team-safety-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
            "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            var jsonData = response.body ? JSON.parse(response.body) : {};
            var allData = jsonData.dashboardData;
            var safetyScoresMap = {};
            for (var i = 0; i < allData.length; i++) {
                var item = allData[i];
                var safetyScore = item.safetyScore;
                var dateRange = item.dateRange;
                safetyScoresMap[dateRange] = safetyScore;  
            }

            if (allData && allData.length > 0) {
                fromDate = allData[0].fromDate;
                toDate = allData[0].toDate;
                var toDateObj = new Date(toDate);
                toDateObj.setDate(toDateObj.getDate() + 1);
                toDate = toDateObj.toISOString().split('T')[0];

                if (calendar1) {
                    calendar1.destroy();  // Destroy previous calendar instance
                }

                var calendarSafety = document.getElementById('safetyCalendar');
                calendar1 = new FullCalendar.Calendar(calendarSafety, {
                    validRange: {
                        start: fromDate,
                        end: toDate
                    },

                    dayCellDidMount: function(info) {
                        $.each(safetyScoresMap, function(dateRange, score) {
                            var dateParts = dateRange.split('-');
                            var year = parseInt(dateParts[0], 10);
                            var month = parseInt(dateParts[1], 10) - 1;
                            var day = parseInt(dateParts[2], 10);
                            var safetyDate = new Date(year, month, day);
                            if (info.date.getFullYear() === safetyDate.getFullYear() &&
                                info.date.getMonth() === safetyDate.getMonth() &&
                                info.date.getDate() === safetyDate.getDate()) {
                                if (score >= 85 && score <= 100) {
                                    info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                                    info.el.style.color = 'white';
                                } else if (score >= 75 && score < 85) {
                                    info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                                    info.el.style.color = 'white';
                                } else if (score >= 60 && score < 75) {
                                    info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                                    info.el.style.color = 'white';
                                } else if (score < 60) {
                                    info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                                    info.el.style.color = 'white';
                                }
                            }
                        });
                    }
                });
                calendar1.render();
            } else {
                console.error("No data found in the response.");
            }
        },
        error: function(error) {
            console.error("Error fetching data:", error);
        }
    });
}

function renderCalendar2(fromDate, toDate, location, org, orgDiv) {
    $.ajax({
        url: "dashboard-team-quality-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
            "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            var jsonData = response.body ? JSON.parse(response.body) : {};
            var allData = jsonData.dashboardData;
            var qualityScoresMap = {};
            for (var i = 0; i < allData.length; i++) {
                var item = allData[i];
                var qualityScore = item.qualityScore;
                var dateRange = item.dateRange;
                qualityScoresMap[dateRange] = qualityScore;  
            }

            if (allData && allData.length > 0) {
                fromDate = allData[0].fromDate;
                toDate = allData[0].toDate;
                var toDateObj = new Date(toDate);
                toDateObj.setDate(toDateObj.getDate() + 1);  // Add 1 day
                toDate = toDateObj.toISOString().split('T')[0];

                if (calendar2) {
                    calendar2.destroy();  // Destroy previous calendar instance
                }

                var calendarQuality = document.getElementById('qualityCalendar');
                calendar2 = new FullCalendar.Calendar(calendarQuality, {
                    validRange: {
                        start: fromDate,
                        end: toDate
                    },

                    dayCellDidMount: function(info) {
                        $.each(qualityScoresMap, function(dateRange, score) {
                            var dateParts = dateRange.split('-');
                            var year = parseInt(dateParts[0], 10);
                            var month = parseInt(dateParts[1], 10) - 1; // JavaScript months are 0-indexed
                            var day = parseInt(dateParts[2], 10);
                            var qualityDate = new Date(year, month, day);
                            if (info.date.getFullYear() === qualityDate.getFullYear() &&
                                info.date.getMonth() === qualityDate.getMonth() &&
                                info.date.getDate() === qualityDate.getDate()) {
                                if (score >= 85 && score <= 100) {
                                    info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                                    info.el.style.color = 'white';
                                } else if (score >= 75 && score < 85) {
                                    info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                                    info.el.style.color = 'white';
                                } else if (score >= 60 && score < 75) {
                                    info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                                    info.el.style.color = 'white';
                                } else if (score < 60) {
                                    info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                                    info.el.style.color = 'white';
                                }
                            }
                        });
                    }
                });
                calendar2.render();
            } else {
                console.error("No data found in the response.");
            }
        },
        error: function(error) {
            console.error("Error fetching data:", error);
        }
    });
}
   
   
   function renderCalendar3(fromDate, toDate, location, org, orgDiv) {
    $.ajax({
        url: "dashboard-team-delivery-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
            "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            var jsonData = response.body ? JSON.parse(response.body) : {};
            var allData = jsonData.dashboardData;
            var deliveryScoresMap = {};
            for (var i = 0; i < allData.length; i++) {
                var item = allData[i];
                var deliveryScore = item.deliveryScore;
                var dateRange = item.dateRange;
                deliveryScoresMap[dateRange] = deliveryScore;  
            }

            if (allData && allData.length > 0) {
                fromDate = allData[0].fromDate;
                toDate = allData[0].toDate;
                var toDateObj = new Date(toDate);
                toDateObj.setDate(toDateObj.getDate() + 1);
                toDate = toDateObj.toISOString().split('T')[0];

                if (calendar3) {
                    calendar3.destroy();  // Destroy previous calendar instance
                }

                var calendarDelivery = document.getElementById('deliveryCalendar');
                calendar3 = new FullCalendar.Calendar(calendarDelivery, {
                    validRange: {
                        start: fromDate,
                        end: toDate
                    },

                    dayCellDidMount: function(info) {
                        $.each(deliveryScoresMap, function(dateRange, score) {
                            var dateParts = dateRange.split('-');
                            var year = parseInt(dateParts[0], 10);
                            var month = parseInt(dateParts[1], 10) - 1;
                            var day = parseInt(dateParts[2], 10);
                            var deliveryDate = new Date(year, month, day);
                            if (info.date.getFullYear() === deliveryDate.getFullYear() &&
                                info.date.getMonth() === deliveryDate.getMonth() &&
                                info.date.getDate() === deliveryDate.getDate()) {
                                if (score >= 85 && score <= 100) {
                                    info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                                    info.el.style.color = 'white';
                                } else if (score >= 75 && score < 85) {
                                    info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                                    info.el.style.color = 'white';
                                } else if (score >= 60 && score < 75) {
                                    info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                                    info.el.style.color = 'white';
                                } else if (score < 60) {
                                    info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                                    info.el.style.color = 'white';
                                }
                            }
                        });
                    }
                });
                calendar3.render();
            } else {
                console.error("No data found in the response.");
            }
        },
        error: function(error) {
            console.error("Error fetching data:", error);
        }
    });
}

function renderCalendar4(fromDate, toDate, location, org, orgDiv) {
    $.ajax({
        url: "dashboard-team-cost-calender?fromDate=" + fromDate + "&toDate=" + toDate + 
            "&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            var jsonData = response.body ? JSON.parse(response.body) : {};
            var allData = jsonData.dashboardData;
            var costScoresMap = {};
            for (var i = 0; i < allData.length; i++) {
                var item = allData[i];
                var costScore = item.costScore;
                var dateRange = item.dateRange;
                costScoresMap[dateRange] = costScore;  
            }

            if (allData && allData.length > 0) {
                fromDate = allData[0].fromDate;
                toDate = allData[0].toDate;
                var toDateObj = new Date(toDate);
                toDateObj.setDate(toDateObj.getDate() + 1);  // Add 1 day
                toDate = toDateObj.toISOString().split('T')[0];

                if (calendar4) {
                    calendar4.destroy();  // Destroy previous calendar instance
                }

                var calendarCost = document.getElementById('costCalendar');
                calendar4 = new FullCalendar.Calendar(calendarCost, {
                    validRange: {
                        start: fromDate,
                        end: toDate
                    },

                    dayCellDidMount: function(info) {
                        $.each(costScoresMap, function(dateRange, score) {
                            var dateParts = dateRange.split('-');
                            var year = parseInt(dateParts[0], 10);
                            var month = parseInt(dateParts[1], 10) - 1; // JavaScript months are 0-indexed
                            var day = parseInt(dateParts[2], 10);
                            var costDate = new Date(year, month, day);
                            if (info.date.getFullYear() === costDate.getFullYear() &&
                                info.date.getMonth() === costDate.getMonth() &&
                                info.date.getDate() === costDate.getDate()) {
                                if (score >= 85 && score <= 100) {
                                    info.el.style.backgroundColor = 'rgb(191, 5, 255)';
                                    info.el.style.color = 'white';
                                } else if (score >= 75 && score < 85) {
                                    info.el.style.backgroundColor = 'rgb(145, 140, 223)';
                                    info.el.style.color = 'white';
                                } else if (score >= 60 && score < 75) {
                                    info.el.style.backgroundColor = 'rgb(242, 139, 103)';
                                    info.el.style.color = 'white';
                                } else if (score < 60) {
                                    info.el.style.backgroundColor = 'rgb(255, 0, 0)';
                                    info.el.style.color = 'white';
                                }
                            }
                        });
                    }
                });
                calendar4.render();
                
            } else {
                console.error("No data found in the response.");
            }
        },
        error: function(error) {
            console.error("Error fetching data:", error);
        }
    });
}