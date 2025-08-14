function performance() {
	changeheadDateOfChart();
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val()
	var org = $("#performanceOrg").find('option:selected').text();
	var orgDiv = $("#performanceOrgDiv").find('option:selected').text();

	//Top Mean Time To Repair
	$.ajax({
		type: "GET",
		url: "dashboard-performance-top-mean-time-repair-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var assetNameList = [];
				var repairMeanTime = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					//totalTicketCount = totalTicketCount + item.Number_Of_Records;
					assetNameList.push([item.assetName]);
					var time = item.Average_Resolution_Time;
					var timeParts = time.split(':');
					var hours = parseInt(timeParts[0], 10);
					var minutes = parseInt(timeParts[1], 10);
					var seconds = parseInt(timeParts[2], 10);

					var totalSeconds = (hours * 3600) + (minutes * 60) + seconds;

					var days = totalSeconds / (24 * 3600);
					repairMeanTime.push(days);

				}

				Highcharts.chart('assetTopMeanTimeToRepair', {
					chart: {
						type: 'column',
						animation: true,
						height: 150
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
						categories: assetNameList,//['abc','xyz'],
						title: {
							text: ''
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: ''
						},
						stackLabels: {
							enabled: false
						}
					},
					legend: { enabled: false },

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: '',
						data: repairMeanTime,//[0.10,0],
						color: '#bf05ff'
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});


	//assetAvgMaintExpenses  
	$.ajax({
		type: "GET",
		url: "dashboard-performance-maint-expenses-since-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);

				var monthDatas = [];
				Highcharts.chart('assetAvgMaintExpenses', {
					chart: {
						type: 'area',
						height: 150,
						animation: true,
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					}, xAxis: {
						categories: ['Mar', 'Apr', 'May', 'Jun'],
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					plotOptions: {
						series: {
							pointStart: 2012
						},
						area: {
							threshold: null,
							stacking: 'normal',
							lineColor: 'transparent',
							marker: {
								enabled: false,
							}
						}
					},
					series: [{
						name: 'Infrastructure',
						data: [21, 5, 12, 3],
						color: '#bf05ff'
					}, {
						name: 'Digital Assets',
						data: [24, 5, 12, 4],
						color: '#ca7ce5'

					}, {
						name: 'Machinaries',
						data: [22, 5, 12, 3],
						color: '#f79c92'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});

	//Incident Resolution And Response Time
	$.ajax({
		type: "GET",
		url: "dashboard-performance-incident-resolution-response-time?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var ticketSubCatNameList = [];
				var AverageResolutionTimeList = [];
				var textList = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					ticketSubCatNameList.push(item.ticketSubCatName);
					AverageResolutionTimeList.push(item.Average_Resolution_Time);

					var text = `{
				        name: '${item.ticketSubCatName}',
				        type: 'column',
				        color: '#ce7fea',
				        yAxis: 1,
				        data: [${item.Average_Resolution_Time}]
				    }`;
					textList.push(text);
				}



				//	alert("textList---------"+textList);
				Highcharts.chart('assetIncidentResolutionAndResponseTime', {
					chart: {
						animation: true,
						height: 150,
						marginLeft: 0
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
					credits: { enabled: false },
					xAxis: [{
						categories: ['Supplier #1', 'Supplier #2', 'Supplier #3', 'Supplier #4'],
						crosshair: true,
						labels: {
							style: {
								fontSize: '8'
							}
						},
						lineColor: '#cccccc'
					}],
					yAxis: [{ // Primary yAxis
						labels: {
							format: '{value}',
							style: {
								color: '#136e6d'
							}
						},
						min: 0,
						title: {
							text: '',
							style: {
								display: 'none',
							}
						}
					}, { // Secondary yAxis
						title: {
							text: '',
							style: {
								display: 'none',
							}
						},
						min: 0,
						labels: {
							format: '',
							style: {
								display: 'none',
							}
						},
						opposite: false
					}],
					tooltip: {
						shared: false
					},
					legend: {
						align: 'center',
						verticalAlign: 'bottom',
						floating: false,
						backgroundColor: 'transparent'
					},
					series: [{
						name: 'TRsl LAB (d)',
						type: 'column',
						color: '#ce7fea',
						yAxis: 1,
						data: [1.6, 2.8, 0.7, 1.1],

					}, {
						name: 'Limit',
						type: 'spline',
						data: [3, 3, 3, 3],
						color: '#810bab',
						lineWidth: 2,
						dataLabels: {
							enabled: false,
						},
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//Avg Asset Utilization
	$.ajax({
		type: "GET",
		url: "dashboard-performance-asset-utilization-since-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthYearList = jsonData.map(function(item) {
					return item.monthYear;
				});
				var uniqueMonthYearArray = Array.from(new Set(monthYearList));

				// Step 3: Extract unique category names
				var categoryList = jsonData.map(function(item) {
					return item.categoryName;
				}).filter(function(name) {
					return name !== null;
				});
				var uniqueCategoryArray = Array.from(new Set(categoryList));

				// Step 4: Create the series array
				var seriesArray = uniqueCategoryArray.map(function(categoryName) {
					var data = uniqueMonthYearArray.map(function(monthYear) {
						var item = jsonData.find(function(record) {
							return record.monthYear === monthYear && record.categoryName === categoryName;
						});
						return item ? item.totalUtilizedAsset : 0;
					});
					return {
						name: categoryName,
						data: data,
						color: '#' + Math.floor(Math.random() * 16777215).toString(16) // Generate random color
					};
				});

				console.log(seriesArray);


				Highcharts.chart('assetAvgAssetUtilization', {
					chart: {
						type: 'area',
						height: 150,
						animation: true,
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					}, xAxis: {
						categories: uniqueMonthYearArray,//['Mar','Apr','May','Jun'],
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					/* plotOptions: {
						 series: {
							 pointStart: 2012
						 },
						 area: {threshold: null,
							 stacking: 'normal',
							 lineColor: 'transparent',
							 marker: {
								 enabled: false,
							 }
						 }
					 },*/
					series: seriesArray,

					/*[{
						name: 'Mixing',
						data: [21,5,12,3],
						color: '#bf05ff'
					}, {
						name: 'Transport',
						data: [24,5,12,4],
						color: '#ca7ce5'
		
					}, {
						name: 'Surface',
						data: [22,5,12,3],
						color: '#f79c92'
					}]*/
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});

	//Top Mean Time To Failure
	$.ajax({
		type: "GET",
		url: "dashboard-performance-top-mean-time-failure-since-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData;
				var assetNameList = [];
				var countAssetNumber = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					//totalTicketCount = totalTicketCount + item.Number_Of_Records;
					assetNameList.push([item.assetName]);
					countAssetNumber.push([item.totalFailureCount]);

				}
				Highcharts.chart('assetTopMeanTimeToFailure', {
					chart: {
						type: 'column',
						animation: true,
						height: 358
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
						categories: assetNameList,/*['bb3ku63','0a3n00k','858r4xa','7ryp1zq','96rv8su','8128cm5',
                'gmhb95e','7tnmoy6','fcdbof8','hs2gb6x','prkdckc','12cjims','yk9fcgy','89k9aa5',
                '6ihyjvq','vkf15rf','ldw75ay','cy5k5r3','pq6i03m','0t2e3hz','31t2f4n','n3hdxk9',
                '6ezfg6i','v1ngkg8','pao22qx','qb00v1b','0qa0vn2','dk4m13v','rh5am4b','qrq6guo'],*/
						title: {
							text: ''
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: ''
						},
						stackLabels: {
							enabled: false
						}
					},
					legend: { enabled: false },

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: '',
						data: countAssetNumber,//[1,2,3,2,2,2,2,2,3,3,4,4,5,6,7,8,8,20,20.5,20,20,21,20,20,20,20.5,21,20,20,20],
						color: '#bf05ff'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});


	//Top Asset By Wo Labor
	$.ajax({
		type: "GET",
		url: "dashboard-performance-top-asset-labor-since-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var assetNameList = [];
				var countAssetNumber = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					//totalTicketCount = totalTicketCount + item.Number_Of_Records;
					assetNameList.push([item.assetName]);
					countAssetNumber.push([item.number_Of_Records]);

				}
				Highcharts.chart('assetTopAssetByWoLabor', {
					chart: {
						type: 'column',
						animation: true,
						height: 358
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
						categories: assetNameList,//['abc','def','ghi','jkl','mno','pqr','stu','vwz'],
						title: {
							text: ''
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: ''
						},
						stackLabels: {
							enabled: false
						}
					},
					legend: { enabled: false },

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: '',
						data: countAssetNumber,//[10,8,6,5,4,3,2,1],
						color: '#f79c92'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//Top Asset By Downtime
	$.ajax({
		type: "GET",
		url: "dashboard-performance-top-asset-downtime-april?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var assetNameList = [];
				var countAssetNumber = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					//totalTicketCount = totalTicketCount + item.Number_Of_Records;
					assetNameList.push([item.assetName]);
					countAssetNumber.push([item.number_Of_Records]);

				}
				Highcharts.chart('assetTopAssetByDowntime', {
					chart: {
						type: 'column',
						animation: true,
						height: 358
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
						categories: assetNameList,//['abc','def','ghi'],
						title: {
							text: ''
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: ''
						},
						stackLabels: {
							enabled: false
						}
					},
					legend: { enabled: false },

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: '',
						data: countAssetNumber,//[10,7,5],
						color: '#9a6dd2'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});
}

function getPerformanceFilterWithDate() {
	performance();
	changeheadDateOfChart();
}
function resetPerformanceTab() {
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

	$("#toDate3").val(toDate);
	$("#fromDate3").val(fromDate);
	performance();
	changeheadDateOfChart();
}
function changeheadDateOfChart() {
	var fromDate = $("#fromDate3").val();



	if (fromDate && fromDate.length === 10) {

		var dateParts = fromDate.split('-');

		var day = dateParts[0];
		var month = dateParts[1];
		var year = dateParts[2];


		var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];


		var monthIndex = parseInt(month) - 1;


		if (monthIndex >= 0 && monthIndex < 12) {
			var monthName = monthNames[monthIndex];


			$("#fromYearPerMeanToRepair").text(monthName + " " + year);
			$("#fromYearAvgMaintExp").text(monthName + " " + year);

			$("#fromYearAvgAssetUtilization").text(monthName + " " + year);
			$("#fromYearPerMeanToFailure").text(monthName + " " + year);
			$("#fromYearPerAssetByWoLabor").text(monthName + " " + year);
			$("#fromYearPerAssetByDowntime").text(monthName + " " + year);
		} else {
			console.error("Invalid month value:", month);
		}
	} else {
		console.error("Invalid fromDate value:", fromDate);
	}


}