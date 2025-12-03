function getMaintenanceFilterWithDate() {
	maintainance();
}

function maintainance() {
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#mainOrg").find('option:selected').text();
	var orgDiv = $("#mainDiv").find('option:selected').text();


	//eam Monthly Backlog Analysis
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-monthly-backlog-analysis?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData;
				var preventiveData = [];
				var correctiveData = [];
				var projectData = [];
				var monitoringExceptionData = [];
				var safetyData = [];
				var totalData = [];
				var monthlist = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					monthlist.push(item.monthYear);

					preventiveData.push(item.preventiveCount);
					correctiveData.push(item.correctiveCount);
					projectData.push(item.projectCount);
					monitoringExceptionData.push(item.monitoringExpenseCount);
					safetyData.push(item.safetyCount);
					totalData.push(item.totalCount);
				}

				//cash ratio trend
				Highcharts.chart("eamMonthlyBacklogAnalysis", {
					chart: {
						zoomType: "xy",
						animation: true,
						height: 288,
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					credits: { enabled: false },
					xAxis: [
						{
							categories: monthlist,
							labels: { style: { fontSize: "10px" } },
						},
					],
					yAxis: [
						{
							title: {
								text: "",
							},
							labels: {
								format: "{value}",
								style: { fontSize: "9px" },
							},
						}
					],
					tooltip: {
						shared: true,
					},
					plotOptions: {
						column: {
							stacking: "normal",
							reversedStacks: false,
						},
					},
					legend: {
						itemStyle: { fontSize: "10px" },
					},
					series: [
						{
							name: "Preventive",
							type: "column",
							data: preventiveData,//[2, 2, 2, 3, 3, 2, 2, 2],
							color: "#56156c",
							zIndex: 2,
						},
						{
							name: "Corrective",
							type: "column",
							data: correctiveData,//[2, 2, 2, 3, 3, 2, 2, 2],
							color: "#f79c92",
							zIndex: 2,
						},
						{
							name: "Project",
							type: "column",
							data: projectData,//[2, 2, 2, 3, 3, 2, 2, 2],
							color: "#ca7ce5",
							zIndex: 2,
						},
						{
							name: "Monitoring Exceptions",
							type: "column",
							data: monitoringExceptionData,//[2, 2, 2, 3, 3, 2, 2, 2],
							color: "#bf05ff",
							zIndex: 2,
						},
						{
							name: "Safety",
							type: "column",
							data: safetyData,//[2, 2, 2, 3, 3, 2, 2, 2],
							color: "#9792e8",
							zIndex: 2,
						},
						{
							name: "Total",
							type: "area",
							data: totalData,//[6, 6, 6, 15, 15, 6, 6, 6],
							color: "#e6b6f7",
							marker: false,
							zIndex: 1,
						},
					],
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Scheduled vs Assigned
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-scheduled-assigned?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.scheduleAssignedData;
				var scheduleCount = (allData.scheduleCountData);
				var assignedCount = (allData.assignedCountData);

				Highcharts.chart('eamScheduledvsAssigned', {
					chart: {
						type: 'column',
						animation: true,
						height: 287
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: ['Scheduled', 'Assigned'],
						title: {
							text: null
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
					},

					plotOptions: {
						column: {
							dataLabels: {
								enabled: true,
								color: '#000000'
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: [scheduleCount, assignedCount],
						color: '#f58d68'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	//eam Preventive Maintainance
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-preventive-maintainance?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {
				//alert("ww")

				var jsonData = JSON.parse(response.body); // Parse the JSON data

				// alert(jsonData)
				var categories = [];
				var createdData = [];
				var closedData = [];

				// Process each month data
				jsonData.forEach(function(month) {
					categories.push(month.monthYear);
					createdData.push(month.Created);
					closedData.push(month.Close); // Note: 'Close' should match the case-sensitive key in your JSON
				});

				// Create Highcharts chart
				Highcharts.chart('eamPreventiveMaintainance', {
					chart: {
						type: 'column',
						animation: true,
						height: 260
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categories,
						title: {
							text: 'Month'
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: '',
						},
					},
					plotOptions: {
						column: {
							dataLabels: {
								enabled: true,
							},
						}
					},
					credits: {
						enabled: false
					},
					series: [{
						name: 'Created',
						data: createdData,
						color: "#bf05ff"
					}, {
						name: 'Closed',
						data: closedData,
						color: "#deaaf0"
					}]
				});
			} else {
				console.error("Error: " + response.message);
			}
		},
		error: function(data) {
			console.error("Error fetching data: ", data);
		}
	});





	//eam Planned Maintainance Percentage
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-planned-maintainance-percentage?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var allData = JSON.parse(response.body);
				//var allData = jsonData.dashboardData;
				var monthList = [];
				var correctiveMaintenanceList = [];
				var preventiveMaintenanceList = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];

					var item = allData[i];
					monthList.push(item.monthYear);
					correctiveMaintenanceList.push(item.cmCount);
					preventiveMaintenanceList.push(item.pmCount);
				}

				Highcharts.chart("eamPlannedMaintainancePercentage", {
					chart: {
						animation: true,
						height: 260,
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					yAxis: {
						title: {
							text: "",
						},
					},
					credits: { enabled: false },

					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						categories: monthList,//['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','July'],
					},
					plotOptions: {
						series: {
							label: {
								connectorAllowed: false,
							},
						},
					},

					series: [
						{
							type: "line",
							name: "Corrective",
							data: correctiveMaintenanceList,//[40, 50, 60, 20, 40, 80, 20],
							color: '#bf05ff'
						},
						{
							type: "line",
							name: "PM",
							data: preventiveMaintenanceList,//[30, 30, 30, 30, 30, 30, 30],
							color: "#f79c92",
						},
						{
							type: "line",
							name: "CM",
							data: correctiveMaintenanceList,//[70, 70, 70, 70, 70, 70, 70],
							color: "#81cc9c",
						},
					],

					responsive: {
						rules: [
							{
								condition: {
									maxWidth: 500,
								},
								chartOptions: {
									legend: {
										layout: "horizontal",
										align: "center",
										verticalAlign: "bottom",
									},
								},
							},
						],
					},
				});
				//eam Work Order Overv 

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Asset Group By Assets
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-asset-group-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var categoryNamesList = [];
				var assetCountList = [];
				var colors = ['#deaaf0', '#bf05ff', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92'];

				var assetsData = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryNamesList.push(item.categoryName);
					assetCountList.push(item.assetCount);

					// Assign a color based on the index
					var color = colors[i % colors.length];

					// Create the asset data object for this category
					assetsData.push({
						name: item.categoryName,
						data: [item.assetCount],
						color: color,
						showInLegend: false
					});
				}


				Highcharts.chart('eamAssetGroupByAssets', {
					chart: {
						type: 'bar',
						animation: true,
						height: 260
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categoryNamesList,
						title: {
							text: 'Asset Group'
						},
						labels: { enabled: false }
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Assets',
						},
						labels: { format: '{value}%' }
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: false,
							},
						}
					},
					credits: {
						enabled: false
					},
					series: assetsData/*[{
                name: 'Assets',
                data: assetCountList,
                color: "#deaaf0",
                showInLegend: false
            },]*/
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Departments Assets by Criticality
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-departments-assets-criticality?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var deptData = [];
				var lowData = [];
				var mediumData = [];
				var highData = [];
				var criticalData = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					deptData.push(item.DeptName);
					lowData.push(item.totalLowPriority);
					mediumData.push(item.totalMediumPriority);
					highData.push(item.totalHighPriority);
					criticalData.push(item.totalCriticalPriority);
				}

				Highcharts.chart('eamDepartmentsAssetsbyCriticality', {
					chart: {
						type: 'bar',
						animation: true,
						height: 260
					},
					title: {
						text: ''
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: deptData,
						title: {
							text: 'Assets Department'
						},
						labels: {
							style: {
								fontSize: '10px'
							}
						}
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Asset'
						}
					},
					legend: {
						reversed: true
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: true
							}
						}
					},
					series: [{
						name: 'Critical',
						data: criticalData,
						color: "#c43b84"
					}, {
						name: 'High',
						data: highData,
						color: "#81cc9c"
					}, {
						name: 'Medium',
						data: mediumData,
						color: "#ca7ce5"
					}, {
						name: 'Low',
						data: lowData,
						color: "#FFA500"
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Work Order Overview
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-work-order-overview?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);

				var year_6 = jsonData[0].year_6;
				var allData = [];
				var sixthFifthData = [];
				var fourthThirdData = [];


				allData.push(jsonData[0].sixthYearCountWOOverdue);
				allData.push(jsonData[0].fifthYearCountWOOverdue);
				allData.push(jsonData[0].fourthYearCountWOOverdue);
				allData.push(jsonData[0].thirdYearCountWOOverdue);
				allData.push(jsonData[0].secondYearCountWOOverdue);
				allData.push(jsonData[0].firstYearCountWOOverdue);

				//alert("allData-----------"+allData);

				sixthFifthData.push(jsonData[0].sixthYearCountWOOverdue);
				sixthFifthData.push(jsonData[0].fifthYearCountWOOverdue);
				sixthFifthData.push('null');
				sixthFifthData.push('null');
				sixthFifthData.push('null');
				sixthFifthData.push('null');

				fourthThirdData.push('null');
				fourthThirdData.push('null');
				fourthThirdData.push(jsonData[0].fourthYearCountWOOverdue);
				fourthThirdData.push(jsonData[0].thirdYearCountWOOverdue);
				fourthThirdData.push('null');
				fourthThirdData.push('null');


				Highcharts.chart("eamWorkOrderOverview", {
					chart: {
						type: "area",
						height: 260,
						animation: true,
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					xAxis: {
						categories: [],//["0", "1", "2", "3", "4", "5"],
						title: {
							text: "Asset Location Priority",
						},
					},
					yAxis: {
						title: {
							text: "No of Overdue Work Orders",
						},
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					plotOptions: {
						series: {
							pointStart: year_6,
						},
						area: {
							threshold: null,
							stacking: "normal",
							lineColor: "transparent",
							marker: {
								enabled: false,
							},
						},
					},
					legend: { enabled: false },
					series: [
						{
							name: "",
							data: sixthFifthData,//[0, 0, null, null, null, null],
							color: "#c313ff",
						},
						{
							name: "",
							data: fourthThirdData,//[null, null, 0, 0, null, null],
							color: "#9792e8",
							//   zIndex:2
						},
						{
							name: "",
							data: allData,//[0, 1, 5, 4, 2, 3],
							color: "#f58d68",
							//   zIndex:0
						},
					],
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Work Orders By Type
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-work-orders-type?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);

				var RoutineCount_today = jsonData[0].RoutineCount_today;
				var PreventiveCount_today = jsonData[0].PreventiveCount_today;
				var PlannedCount_today = jsonData[0].PlannedCount_today;

				var RoutineCount_1_7_days = jsonData[0].RoutineCount_1_7_days;
				var PreventiveCount_1_7_days = jsonData[0].PreventiveCount_1_7_days;
				var PlannedCount_1_7_days = jsonData[0].PlannedCount_1_7_days;

				var RoutineCount_8_14_days = jsonData[0].RoutineCount_8_14_days;
				var PreventiveCount_8_14_days = jsonData[0].PreventiveCount_8_14_days;
				var PlannedCount_8_14_days = jsonData[0].PlannedCount_8_14_days;


				var routineData = [RoutineCount_today, RoutineCount_1_7_days, RoutineCount_8_14_days];
				var preventiveData = [PreventiveCount_today, PreventiveCount_1_7_days, PreventiveCount_8_14_days];
				var plannedData = [PlannedCount_today, PlannedCount_1_7_days, PlannedCount_8_14_days];




				Highcharts.chart('eamWorkOrdersByType', {
					chart: {
						type: 'column',
						animation: true,
						height: 268
					},
					title: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: false,
					xAxis: {
						title: {
							text: 'Scheduled Start Days'
						},
						categories: ['Today', 'Next 1-7', 'Next 8-14']
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Work Orders'
						},
						stackLabels: {
							enabled: false
						}
					},
					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: 'Routine',
						data: routineData,//[5, 8, 1],
						color: "#9792e8"
					}, {
						name: 'Preventive',
						data: preventiveData,//[5, 7, 6],
						color: "#ca7ce5"
					}, {
						name: 'Planned',
						data: plannedData,//[null, null, 6],
						color: "#bf05ff"
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Work Orders By Status 
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-work-orders-status?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				Highcharts.chart('eamWorkOrdersByStatus', {
					chart: {
						type: 'column',
						animation: true,
						height: 268
					},
					title: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: false,
					xAxis: {
						title: {
							text: 'Scheduled Start Days'
						},
						categories: ['Today', 'Next 1-7', 'Next 8-14']
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Work Orders'
						},
						stackLabels: {
							enabled: false
						}
					},
					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: 'On Hold',
						data: [null, null, 10],
						color: "#b422b6"
					}, {
						name: 'Pending',
						data: [null, null, 6],
						color: "#bf05ff"
					}, {
						name: 'Unreleased',
						data: [null, 12, 6],
						color: "#ca7ce5"
					}, {
						name: 'Draft',
						data: [null, 10, 16],
						color: "#f79c92"
					}, {
						name: 'Released',
						data: [null, 9, 12],
						color: "#9792e8"
					},]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Perc Unplanned WO Labor Hours 
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-unplanned-labor-hours?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				Highcharts.chart('eamPercUnplannedWOLaborHours', {
					chart: {
						type: 'bar',
						animation: true,
						height: 269
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: ['0', '1', '2', '3', '4', '5'],
						title: {
							text: 'Asset Location Priority'
						},
						labels: { enabled: false }
					},
					yAxis: {
						min: 0,
						title: {
							text: '% Unplanned WO Labour Hours',
						},
						labels: { format: '{value}%' }
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: false,
							},
						}
					},
					credits: {
						enabled: false
					},
					series: [{
						name: 'Planned',
						data: [40, 34, 65, 76, 28, 93],
						color: "#deaaf0"
					}, {
						name: 'Unplanned',
						data: [65, 76, 5, 48, 83, 61],
						color: "#bf05ff"
					},]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	//eam Work Order Actual to Estimated Costs
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-workOrder-actual-estimated-costs?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				Highcharts.chart('eamWorkOrderActualtoEstimatedCosts', {
					chart: {
						type: 'column',
						animation: true,
						height: 269
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: ['0', '1', '2', '3', '4', '5'],
						title: {
							text: 'Asset Location Priority'
						},
						// labels: {enabled:false}
					},
					yAxis: {
						min: 0,
						title: {
							text: '% Unplanned WO Labour Hours',
						},
						labels: { format: '{value}%' }
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: false,
							},
						}
					},
					credits: {
						enabled: false
					},
					series: [{
						name: 'Bedford',
						data: [40, 34, 65, 76, 28, 93],
						color: "#bf05ff"
					}, {
						name: 'Lareto',
						data: [65, 76, 5, 48, 83, 61],
						color: "#deaaf0"
					}, {
						name: 'Texas',
						data: [65, 76, 5, 48, 83, 61],
						color: "#f58d68"
					},]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Count And Percentage of Unsolved Tickets
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-count-percentage-unsolved-tickets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var assignedToNameList = [];
				var employeeAssignedOpenPercentageList = [];
				var result = [];
				var solvedTicketCount = allData[0].ClosedTicketCount;
				var unsolvedTicketCount = allData[0].OpenTicketCount;
				var solvedByUnsolvedPercentage = allData[0].solvedByUnsolvedPercentage;

				$("#solvedTicket").text(parseFloat(solvedTicketCount).toFixed(2));
				$("#unsolvedTicket").text(parseFloat(unsolvedTicketCount).toFixed(2));
				$("#solvedByUnsolvedPercentage").text(parseFloat(solvedByUnsolvedPercentage).toFixed(2));


				//var colors = ["#56156c","#f79c92","#ca7ce5","#bf05ff","#cc617e","#81cc9c","#c43b84","#cbae9b", "#b6dbdb", "#805b6e"];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					result.push([item.assignedToName, item.employeeAssignedOpenPercentage]);
				}

				var colors = ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#cc617e", "#81cc9c", "#c43b84", "#cbae9b", "#b6dbdb", "#805b6e"];
				var result1 = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					var color = colors[i % colors.length]; // Use modulo to cycle through colors if there are more items than colors
					result1.push({
						name: item.assignedToName,
						percentage: item.employeeAssignedOpenPercentage,
						color: color
					});
				}



				// Now to generate the HTML
				var html = '';

				for (var j = 0; j < result1.length; j++) {
					var dataItem = result1[j];
					html += `<tr>
			                <td style="width: 60%">
			                    <div>
			                        <span style="color: ${dataItem.color}; font-size: 9px"><i class="fa-solid fa-circle"></i></span> ${dataItem.name}
			                    </div>
			                </td>
			                <td style="width: 40%">${parseFloat(dataItem.percentage).toFixed(2)}%</td>
			            </tr>`;
				}


				$("#unsolvedEmployeeListTbody").html(html);

				Highcharts.chart('eamCountAndPercentageOfUnsolvedTickets', {
					chart: {
						type: 'pie',
						height: 220,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#cc617e', '#81cc9c', '#c43b84', '#cbae9b', '#b6dbdb', '#805b6e'],
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false,
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					plotOptions: {
						pie: {
							shadow: false
						}
					},
					series: [{
						name: '',
						data: result,
						size: '100%',
						innerSize: '70%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	// eam Mean Time To Repair Third
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-mean-time-repair?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var ticketSubCatNameList = [];
				var totalTicketCount = 0;
				var result = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					totalTicketCount = totalTicketCount + item.Number_Of_Records;
				}

				var html = '';
				var html1 = '';
				var colors = ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#cc617e", "#81cc9c", "#c43b84", "#cbae9b", "#b6dbdb", "#805b6e"];


				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					var color = colors[i % colors.length];
					result.push([item.ticketSubCatName, item.Number_Of_Records]);
					var percentageOfTotalRecord = ((item.Number_Of_Records / totalTicketCount) * 100);
					html += `<tr>
			                <td style="width: 60%">
			                    <div>
			                        <span style="color: ${color}; font-size: 9px"><i class="fa-solid fa-circle"></i></span> ${item.ticketSubCatName}
			                    </div>
			                </td>
			                <td style="width: 40%">${parseFloat(percentageOfTotalRecord).toFixed(2)}%</td>
			            </tr>`;

					html1 += `<tr>
	                <td>${item.ticketSubCatName}</td>
					<td>${item.Average_Resolution_Time}</td>
					<td>${item.Average_Resolution_Time}</td>
	            </tr>`;
				}
				$("#meanTimeToRepairTbody").html(html);
				$("#meanTimeToRepairTbody1").html(html1);




				Highcharts.chart('eamMeanTimeToRepairThird', {
					chart: {
						type: 'pie',
						height: 190,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#56156c', '#f79c92', '#bf05ff', '#81cc9c'],
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false,
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					plotOptions: {
						pie: {
							shadow: false
						}
					},
					series: [{
						name: '',
						data: result,
						size: '100%',
						innerSize: '70%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	function timeToSeconds(time) {
		var parts = time.split(':');
		return parseInt(parts[0], 10) * 3600 + parseInt(parts[1], 10) * 60 + parseInt(parts[2], 10);
	}

	// Function to convert seconds to hh:mm:ss
	function secondsToTime(seconds) {
		var hours = Math.floor(seconds / 3600);
		var minutes = Math.floor((seconds % 3600) / 60);
		var seconds = seconds % 60;

		return [hours, minutes, seconds].map(part => String(part).padStart(2, '0')).join(':');
	}


	//eam Mean Time To Detect Line
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-mean-time-detect?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				/*	var currentYear = new Date().getFullYear();
				   var startYear = currentYear - 11;
			   	
				   for (var a = startYear; a <= currentYear; a++) {
					 for (var b = 0; b < allData.length; b++) {
					   var itemData = allData[b];
					   var YearData = itemData.Year;
					   if (a == YearData) {
						 alert("YearData: " + YearData);
						 // Perform your desired actions here
					   }		    
					 }
				   }
					 */

				/*	var currentYear = new Date().getFullYear();
				   var startYear = currentYear - 11;
			   	
				   var totalCountNumber_Of_Records = [];
			   	
				   for (var a = startYear; a <= currentYear; a++) {
					 var found = false;
					 var totalNumberOfRecordsYearWise = 0;
					 for (var b = 0; b < allData.length; b++) {
					   var itemData = allData[b];
					   var YearData = itemData.Year;
					   if (a == YearData) {
						 totalNumberOfRecordsYearWise = totalNumberOfRecordsYearWise+itemData.Number_Of_Records;
						 totalCountNumber_Of_Records.push(totalNumberOfRecordsYearWise);
						 found = true;
						 break; // Exit the inner loop once a match is found for the year
					   }
					 }
					 if (!found) {
					   totalCountNumber_Of_Records.push(0); // Push 0 if no match found for the year
					 }
				   }
			   	
				   alert("totalCountNumber_Of_Records-----------"+totalCountNumber_Of_Records);
					 */

				var currentYear = new Date().getFullYear();
				var startYear = currentYear - 11;

				var totalCountNumber_Of_Records = [];

				for (var a = startYear; a <= currentYear; a++) {
					var totalNumberOfRecordsYearWise = 0;
					for (var b = 0; b < allData.length; b++) {
						var itemData = allData[b];
						var YearData = itemData.Year;
						if (a == YearData) {
							totalNumberOfRecordsYearWise += itemData.Number_Of_Records;
						}
					}
					totalCountNumber_Of_Records.push(totalNumberOfRecordsYearWise);
				}




				var ticketSubCatNameList = [];
				var totalTicketCount = 0;
				var result = [];
				var resolutionTimeTotal = 0;

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					totalTicketCount = totalTicketCount + item.Number_Of_Records;

					var sampleTimeTotal = item.Total_Resolution_Time; // Example time, replace with your actual time data
					resolutionTimeTotal += timeToSeconds(sampleTimeTotal);

				}

				//alert("totalTicketCount=================="+totalTicketCount);

				// Convert total seconds back to hh:mm:ss
				var totalResolutionTime = secondsToTime(resolutionTimeTotal / totalTicketCount);

				//alert("totalResolutionTime-------------"+totalResolutionTime); 




				var html = '';
				var html1 = '';
				var colors = ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#cc617e", "#81cc9c", "#c43b84", "#cbae9b", "#b6dbdb", "#805b6e"];


				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					var color = colors[i % colors.length];
					result.push([item.ticketSubCatName, item.Number_Of_Records]);
					var percentageOfTotalRecord = ((item.Number_Of_Records / totalTicketCount) * 100);

				}




				// alert("a-----------"+a);
				// alert("b-----------"+b);

				Highcharts.chart("eamMeanTimeToDetectLine", {
					chart: {
						animation: true,
						height: 180,
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					yAxis: {
						title: {
							text: "",
						},
					},
					credits: { enabled: false },

					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						categories: [],
					},
					legend: {
						enabled: false,
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false,
							},
							pointStart: startYear,
						},
					},

					series: [
						{
							type: "line",
							name: "",
							data: totalCountNumber_Of_Records,//[40, 50, 60, 20, 40, 80, 20, 60, 50, 40, 30, 40],
							zones: [
								{
									value: 30,
									color: "#9792e8",
								},
								{
									value: 70,
									color: "#bf05ff",
								},
								{
									color: "#dc3545",
								},
							],
						},
						/* {
							 type: "line",
							 name: "",
							 data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30],
							 color: "#dddddd",
							 marker: {
								 enabled: false,
							 },
						 },
						 {
							 type: "line",
							 name: "",
							 data: [70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70],
							 color: "#dddddd",
							 marker: {
								 enabled: false,
							 },
						 },*/
					],

					responsive: {
						rules: [
							{
								condition: {
									maxWidth: 500,
								},
								chartOptions: {
									legend: {
										layout: "horizontal",
										align: "center",
										verticalAlign: "bottom",
									},
								},
							},
						],
					},
				});




				//eam Mean Time To Detect Gauge
				Highcharts.chart("eamMeanTimeToDetectGauge", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						backgroundColor: "transparent",
					},

					title: {
						text: totalResolutionTime,
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 0,
						margin: 0,
						style: { fontSize: "12", color: "#bf05ff" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -98,
						endAngle: 97.9,
						background: null,
						center: ["50%", "75%"],
						size: "100%",
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 100,
						// tickPixelInterval: 72,
						tickPosition: "inside",
						tickColor: "transparent",
						tickLength: 0,
						tickWidth: 0,
						minorTickInterval: null,
						labels: {
							enabled: false,
						},
						lineWidth: 0,
						plotBands: [
							{
								from: 0,
								to: 50,
								color: "#bf05ff",
								thickness: 15,
							},
							{
								from: 50,
								to: 70,
								color: "#9792e8",
								thickness: 15,
							},
							{
								from: 70,
								to: 100,
								color: "#deaaf0",
								thickness: 15,
							},
						],
					},

					series: [
						{
							name: "",
							data: [78],
							tooltip: {
								valueSuffix: "",
							},
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "90%",
								backgroundColor: "gray",
								baseWidth: 6,
								baseLength: "0%",
								rearLength: "0%",
							},
							pivot: {
								backgroundColor: "gray",
								radius: 3,
							},
						},
					],
				});


				//eam Mean Time To Detect Bar
				Highcharts.chart("eamMeanTimeToDetectBar", {
					chart: {
						type: "bar",
						backgroundColor: "transparent",
						height: 150,
					},
					credits: false,
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					xAxis: {
						type: "category",
						title: {
							text: "",
						},
						lineColor: "#cccccc",
						labels: {
							rotation: 0,
							style: {
								fontSize: "11px",
							},
						},
					},
					yAxis: {
						title: {
							text: "",
						},
						labels: {
							enabled: false,
						},
						gridLineColor: "transparent",
					},
					legend: {
						enabled: false,
					},
					tooltip: {
						enabled: false,
					},
					series: [
						{
							name: "",
							data: result,
							color: '#9792e8',
							dataLabels: {
								enabled: true,
								rotation: 0,
								color: "#000000",
								align: "center",
								style: {
									fontSize: "11px",
								},
							},
						},
					],
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})



	//eam Reopened Tickets
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-reopened-tickets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var monthYearArray = [];
				var reopenCountArray = [];
				var totalReopenCount = 0;

				$.each(data, function(index, value) {
					monthYearArray.push(value.monthYear);
					reopenCountArray.push(value.reopenCount);
					totalReopenCount += value.reopenCount;
				});

				$("#reOpenTotalTicketId").text(totalReopenCount);

				console.log("MonthYear Array: ", monthYearArray);
				console.log("ReopenCount Array: ", reopenCountArray);
				Highcharts.chart("eamReopenedTickets", {
					chart: {
						type: "spline",
						animation: true,
						backgroundColor: "transparent",
						height: 185,
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},

					credits: {
						enabled: false,
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					xAxis: {
						categories: monthYearArray,
					},
					yAxis: {
						gridLineColor: "transparent",
						title: {
							text: "",
						},
						lineColor: "transparent",
						labels: { enabled: false }
					},
					plotOptions: {
						spline: {
							dataLabels: {
								enabled: true,
							},
							showInLegend: false,
						},
					},
					series: [
						{
							name: "",
							data: reopenCountArray,//[16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2],
							color: "#bf05ff",
							showInLegend: false,
						},
					],
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})




	//eam Total PM CM Ratio Percentage
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-total-ratio-percentage?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalTicketCount = allData.cmCount + allData.pmCount;
				var pmCount = allData.pmCount;
				var cmCount = allData.cmCount;
				var pmPercentageList = [];

				var pmPercentage = (pmCount / totalTicketCount) * 100;

				// alert("pmPercentage---"+pmPercentage)

				pmPercentageList.push(pmPercentage);



				Highcharts.chart("eamTotalPMCMRatioPercentage", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 242,
						backgroundColor: "transparent",
						margin: 0,
					},

					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -98,
						endAngle: 97.9,
						background: null,
						center: ["50%", "75%"],
						size: "100%",
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 100,
						// tickPixelInterval: 72,
						tickPosition: "inside",
						tickColor: "transparent",
						tickLength: 0,
						tickWidth: 0,
						minorTickInterval: null,
						labels: {
							enabled: false,
						},
						lineWidth: 0,
						plotBands: [
							{
								from: 0,
								to: pmPercentage,
								color: "#bf05ff",
								thickness: 10,
							},
							{
								from: pmPercentage,
								to: 100,
								color: "#d4c9d8",
								thickness: 10,
							},
						],
					},

					series: [
						{
							name: "",
							data: pmPercentageList,
							tooltip: {
								valueSuffix: "",
							},
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "90%",
								backgroundColor: "gray",
								baseWidth: 6,
								baseLength: "0%",
								rearLength: "0%",
							},
							pivot: {
								backgroundColor: "gray",
								radius: 3,
							},
						},
					],
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//eam Total CM PM Ratio Per Year
	$.ajax({
		type: "GET",
		url: "dashboard-maintainance-totat-ratio-per-year?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {


				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var yearList = [];
				var cmCountList = [];
				var pmCountList = [];


				yearList.push(allData[0].thisYear);
				yearList.push(allData[0].lastYear);
				yearList.push(allData[0].secondLastYear);
				yearList.push(allData[0].thirdLastYear);
				yearList.push(allData[0].fourthLastYear);
				cmCountList.push(allData[0].cm_countThisYear);
				cmCountList.push(allData[0].cm_countLastYear);
				cmCountList.push(allData[0].cm_countSecondLastYear);
				cmCountList.push(allData[0].cm_countThirdLastYear);
				cmCountList.push(allData[0].cm_countFourthLastYear);
				pmCountList.push(allData[0].pm_countThisYear);
				pmCountList.push(allData[0].pm_countLastYear);
				pmCountList.push(allData[0].pm_countSecondLastYear);
				pmCountList.push(allData[0].pm_countThirdLastYear);
				pmCountList.push(allData[0].pm_countFourthLastYear);





				Highcharts.chart('eamTotalCMPMRatioPerYear', {
					chart: {
						type: 'column',
						animation: true,
						height: 260
					},
					title: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: false,
					xAxis: {
						title: {
							text: null
						},
						categories: yearList,//['2021', '2022', '2023', '2024', '2025']
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
						stackLabels: {
							enabled: false
						}
					},
					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: 'CM',
						data: cmCountList,//[5, 8, 1, 4, 8],
						color: "#f79c92"
					}, {
						name: 'PM',
						data: pmCountList,//[5, 7, 6, 2, 3],
						color: "#deaaf0"
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})

}
function getAllMaintanenceDataOrg() {
	maintainance();
}
function getAllMaintanenceDataOrgDiv() {
	maintainance();
}

function resetMaintenanceTab() {
	var today = new Date();

	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}

	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
		+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();

	$("#toDate1").val(toDate);
	$("#fromDate1").val(fromDate);

	maintainance();
}