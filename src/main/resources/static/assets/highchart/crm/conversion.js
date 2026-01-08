
			function conversionHighChart() {
	
				var fromDate = $("#fromDate1").val();
				var toDate = $("#toDate1").val();
				var org = $("#organizationCrmCoversion").find('option:selected').text();
				var orgDiv = $("#divisionCrmCoversion").find('option:selected').text();
				var loc = $("#locationCrmCoversion").val();
				var executive = $("#executiveConversionId").val();
				var allKeyRoles = $("#allKeyRoles").val();
				
				
				$.ajax({
					type: "GET",
					url: "crm-dashboard-conversion-top-five-sales-executive",
					data: {
						fromDate: fromDate,
						toDate: toDate,
						org: org,
						orgDiv: orgDiv,
						loc: loc,
						executive: executive,
						allKeyRoles: allKeyRoles
						
								},
					async: true,
					success: function(response) {
						console.log(response);
						if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
							var allData = jsonData.dashboardData;
			
							$('#top5SalesExecutiveId').empty();
						    var length = allData ? allData.length : 0; // Ensure length is 0 if allData is null

							
							
							if(length > 0){
								$.each(allData, function(index, data) {
							 var newRow = `<tr>
							                  <td>${data.leadOwner}</td>
							                  <td>${data.designation}</td>
							                  <td>${data.leadConversionRatio}%</td>
							                </tr>`;
							  
							  $('#top5SalesExecutiveId').append(newRow);
							});
							}else{
								var newRow = `<tr>
							                  <td colspan="3" class="text-center">No data found.</td>							                 
							                </tr>`;
							    $('#top5SalesExecutiveId').append(newRow);
							}
							
							
							
					
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
				
				
				$.ajax({
					type: "GET",
					url: "crm-dashboard-conversion-count-funnel-and-avg-days",
					data: {
						fromDate: fromDate,
						toDate: toDate,
						org: org,
						orgDiv: orgDiv,
						loc: loc,
						executive: executive,
						allKeyRoles: allKeyRoles
						
								},
					async: true,
					success: function(response) {
						console.log(response);
						if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
							var allData = jsonData.dashboardData;
						
			
							$("#opportunitesForFunnel").text(allData[0].opportunitiesCount);
							$("#proposalForFunnel").text(allData[0].proposalCount);
							$("#negotationForFunnel").text(allData[0].negotiationCount);
							$("#closedForFunnel").text(allData[0].winCount);
							
							
					
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
				
				
				
				$.ajax({
					type: "GET",
					url: "crm-dashboard-conversion-last-30-days-leads-conversion",
					data: {
						fromDate: fromDate,
						toDate: toDate,
						org: org,
						orgDiv: orgDiv,
						loc: loc,
						executive: executive,
						allKeyRoles: allKeyRoles
						
						},
					async: true,
					success: function(response) {
						console.log(response);
						if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
							var allData = jsonData.dashboardData;
			
			
							var dayMonthList = [];
							var valueList = [];
							// Iterate over the dashboardData array and append each row
							$.each(allData.reverse(), function(index, data) {
							    dayMonthList.push(data.month_day);
							    valueList.push(data.conversionCount);
							});
														
							
							Highcharts.chart('cRMConvConvertedLeads', {
							    chart: {
							        type: 'column',
							        backgroundColor: 'transparent',
							        height: 198,
							    },
							    colors: ['#b422b6'],
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
							            text: '',
							            style: {
							                fontSize: '15px',
							                color: '#000000'
							            }
							        },
							        lineColor: '#000000',  // Assuming a color value for lineColor
							        categories: dayMonthList,
							        tickLength: 1,
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
							            text: 'Converted Leads - Last 30 days'
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
							        name: 'Total Conversion',
							        data: valueList,
							         // Static data values for series
							        dataLabels: {
							            enabled: true,
							            rotation: 0,
							            color: '#b422b6',
							            align: 'right',
							            y: 1,
							            style: {
							                fontSize: '15px',
							                color: '#b422b6'
							            }
							        }
							    }]
							});

					
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
				
				
				
				$.ajax({
					type: "GET",
					url: "crm-dashboard-conversion-count-all-ratios",
					data: {
						fromDate: fromDate,
						toDate: toDate,
						org: org,
						orgDiv: orgDiv,
						loc: loc,
						executive: executive,
						allKeyRoles: allKeyRoles
						
								},
					async: true,
					success: function(response) {
						console.log(response);
						if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
							var allData = jsonData.dashboardData;
						
							var leadConversionRate = allData[0].leadConversionRate;
							var leadConversionRateList = [];
							leadConversionRateList.push(leadConversionRate);
							var leadToOpportunityRatio = allData[0].leadToOpportunityRatio;
							var leadToOpportunityRatioList = [];
							leadToOpportunityRatioList.push(leadToOpportunityRatio);
							var opportunityToWinRatio = allData[0].opportunityToWinRatio;
							var opportunityToWinRatioList = [];
							opportunityToWinRatioList.push(opportunityToWinRatio);
			
							$("#leadConversionRateId").text(leadConversionRate);
							$("#leadToOpportunityRatioId").text(leadToOpportunityRatio);
							$("#opportunityToWinRatioId").text(opportunityToWinRatio);
							
							$("#leadToOpportunityRatioConversionId").text(leadToOpportunityRatio);
							$("#opportunityToWinRatioConversionId").text(opportunityToWinRatio);
							
								Highcharts.chart('cRMConvLeadOpportunityRatio', {
						        chart: {
						            type: 'gauge',
						            backgroundColor: 'transparent',
						            height: 214
						        },
						
						        title: null,
						
						        pane: {
						            startAngle: -90,
						            endAngle: 90,
						            background: null,
						            center: ['50%', '85%'],
						            size: '140%'
						        },
						
						        tooltip: {
						            enabled: true,
						            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: <b>{point.y}%</b><br/>'
						        },
						
						        // Value axis
						        yAxis: {
						            min: 0,
						            max: 100,
						            tickInterval: 20,
						            labels: {
						                enabled: true
						            },
						            lineWidth: 0,
						            tickWidth: 0,
						            minorTickInterval: null,
						            plotBands: [{
						                from: 0,
						                to: leadToOpportunityRatio,
						                color: '#f79c92',
						                thickness: '20%'
						            }, {
						                from: leadToOpportunityRatio,
						                to: 100,
						                color: '#b422b6',
						                thickness: '20%'
						            }]
						        },
						
						        series: [{
						            name: 'Lead to Opportunity Ratio',
						            data: leadToOpportunityRatioList,
						            dial: {
						                radius: '90%',
						                backgroundColor: 'black',
						                baseWidth: 5,
						                topWidth: 1,
						                baseLength: '0%',
						                rearLength: '0%'
						            },
						            pivot: {
						                backgroundColor: '#000',
						                radius: 5
						            },
						            dataLabels: {
						                enabled: true,
						                format: '<span style="font-size:16px;">{y}%</span>'
						            }
						        }]
						    });
						
						
						
						
					
						
						////////////////////////////////////////////////////////////////
						Highcharts.chart('cRMConvOpportunityWinRatio', {
						        chart: {
						            type: 'gauge',
						            backgroundColor: 'transparent',
						            height: 214
						        },
						
						        title: null,
						
						        pane: {
						            startAngle: -90,
						            endAngle: 90,
						            background: null,
						            center: ['50%', '85%'],
						            size: '140%'
						        },
						
						        tooltip: {
						            enabled: true,
						            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: <b>{point.y}%</b><br/>'
						        },
						
						        // Value axis
						        yAxis: {
						            min: 0,
						            max: 100,
						            tickInterval: 20,
						            labels: {
						                enabled: true
						            },
						            lineWidth: 0,
						            tickWidth: 0,
						            minorTickInterval: null,
						            plotBands: [{
						                from: 0,
						                to: opportunityToWinRatio,
						                color: '#f79c92',
						                thickness: '20%'
						            }, {
						                from: opportunityToWinRatio,
						                to: 100,
						                color: '#b422b6',
						                thickness: '20%'
						            }]
						        },
						
						        series: [{
						            name: 'Opportunity to Win Ratio',
						            data: opportunityToWinRatioList,
						            dial: {
						                radius: '90%',
						                backgroundColor: 'black',
						                baseWidth: 5,
						                topWidth: 1,
						                baseLength: '0%',
						                rearLength: '0%'
						            },
						            pivot: {
						                backgroundColor: '#000',
						                radius: 5
						            },
						            dataLabels: {
						                enabled: true,
						                format: '<span style="font-size:16px;">{y}%</span>'
						            }
						        }]
						    });
						
					
					
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
				
				
				$.ajax({
					type: "GET",
					url: "crm-dashboard-conversion-sales-target-length",
					data: {
						fromDate: fromDate,
						toDate: toDate,
						org: org,
						orgDiv: orgDiv,
						loc: loc,
						executive: executive,
						allKeyRoles: allKeyRoles
						
								},
					async: true,
					success: function(response) {
						console.log(response);
						if (response.code == "success") {
							var jsonData = JSON.parse(response.body);
							var allData = jsonData.dashboardData;
							
							var opportunityLength = allData[0].opprtunityLength;
							var proposalLength = allData[0].proposalLength;
							var negotiationLength = allData[0].negotiationLength;
							var winLength = allData[0].winLength;
							
							
							$("#timeSpentInOppotunities").text(opportunityLength  +' '+'days on average');
							$("#timeSpentInProposal").text(proposalLength  +' '+'days on average');
							$("#timeSpentInNegotiation").text(negotiationLength   +' '+'days on average');
							$("#timeSpentInClosing").text(winLength  +' '+'days on average');
			
					
						}
					},
					error: function(data) {
						console.log(data);
					}
				});
				
	

	


}