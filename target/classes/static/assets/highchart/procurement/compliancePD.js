var chart = '';
function complianceHighChat() {
	callingAllFunction();
}


function callingAllFunction() {

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#comOrg").find('option:selected').text();
	var orgDiv = $("#comOrgDiv").find('option:selected').text();
	var loc = $("#comLoc").val();

	$.ajax({
		url: "view-dashboard-supllier-clasification",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var parsedData = JSON.parse(response.body);
			let allData = parsedData.ServiceData[0];
			$('#shortCycle').html(allData.shortCycle);
			$('#mediumCycle').html(allData.mediumCycle);
			$('#longCycle').html(allData.longCycle);


			var short_count1 = parseFloat(allData.short_count);
			var medium_count1 = parseFloat(allData.medium_count);
			var long_count1 = parseFloat(allData.long_count);

			// Update Highcharts series data
			chart.series[0].update({
				data: [
					['1', short_count1],
					['2', medium_count1],
					['3', long_count1]
				]
			});

		},
		error: function(error) {
			console.error(error);
		}
	});



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-suppliers?div=" + orgDiv + "&org=" + org,
		async: true,
		success: function(response) {
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData;
				var datalist = [];

				for (var i = 0; i < allData.length; i++) {
					var data = {
						name: allData[i].totalVendor,
						y: allData[i].totalVendor
					};
					datalist.push(data);
				}

				var totalVendor = 0;

				for (var i = 0; i < allData.length; i++) {
					totalVendor += allData[i].totalVendor;
				}
				Highcharts.chart('suppliers', {
					chart: {
						type: 'pie',
						height: 120,
						margin: 0,
						width: 160
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#BF05FF'],
					title: {
						text: totalVendor,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 35,
						margin: 0,
						style: { "fontSize": '20', "color": '#000000' }
					},
					subtitle: {
						text: 'SUPPLIERS',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '11', "color": '#000000' }
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
						data: datalist,
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
	//suppliers



	//contracted
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-contracted?div=" + orgDiv + "&org=" + org,
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData[0];
				var contractedVendor = parseInt(allData.contractedVendor);
				var totalVendor = parseInt(allData.totalVendor);
				var percentage = parseFloat(allData.percentage);

				var totalContractedPercentage = percentage + "%";
				var totalContracted = contractedVendor;
				var totalNonContracted = totalVendor;



				Highcharts.chart('contracted', {
					chart: {
						type: 'pie',
						height: 120,
						margin: 0,
						width: 150
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#F79C92', '#dfdfdf'],
					title: {
						text: totalContractedPercentage,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 35,
						margin: 0,
						style: { "fontSize": '20', "color": '#000000' }
					},
					subtitle: {
						text: 'PRODUCT',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '10', "color": '#000000' }
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
						data: [["Product", totalContracted], ["TotalSuppliers", totalNonContracted]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});




	//unlisted

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-service?div=" + orgDiv + "&org=" + org,
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData[0];
				var service = parseInt(allData.service);
				var totalVendor = parseInt(allData.totalVendor);
				var percentage = parseFloat(allData.percentage);

				var totalServicesPercentage = percentage + "%";
				var totalServices = service;
				var totaluppliers = totalVendor;


				Highcharts.chart('unlisted', {
					chart: {
						type: 'pie',
						height: 120,
						margin: 0,
						width: 160
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#d14f57', '#dfdfdf'],
					title: {
						text: totalServicesPercentage,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 35,
						margin: 0,
						style: { "fontSize": '20', "color": '#000000' }
					},
					subtitle: {
						text: 'SERVICES',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '11', "color": '#000000' }
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
						data: [["TotalServices", totalServices], ["TotalSuppliers", totaluppliers]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});

			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	//fiveYearTrend
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-five-year-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData;
				var totalSum = allData.total_sum;

				var thirdYear = allData.thirdYear;
				var secondYear = allData.secondYear;
				var lastYear = allData.lastYear;
				$('.spending-amount').html(totalSum);


				// Sample data points for three years
				var dataPoints = [thirdYear, secondYear, lastYear]; // Replace with your actual data

				Highcharts.chart('fiveYearTrend', {
					chart: {
						type: 'line',
						animation: true,
						backgroundColor: 'transparent',
						height: 50,
						margin: 10,
						// Disable the context menu
						events: {
							load: function() {
								this.container.oncontextmenu = function(e) {
									e.preventDefault();
								};
							}
						}
					},
					exporting: {
						enabled: false
					},
					legend: { enabled: false },
					title: {
						text: '' // Remove the chart title
					},
					xAxis: {
						categories: ['Year1', 'Year2', 'Year3'], // Replace with your actual years
						labels: {
							enabled: true
						}
					},
					yAxis: {
						title: {
							text: 'Amount'
						}
					},
					plotOptions: {
						line: {
							dataLabels: {
								enabled: true,
								formatter: function() {
									return this.y;
								}
							}
						}
					},
					series: [{
						name: 'Trend',
						data: dataPoints,
						color: '#BF05FF',
						dashStyle: 'ShortDashDot',
						marker: {
							symbol: 'circle',
							radius: 6,
							fillColor: '#BF05FF',
							lineColor: '#ffffff',
							lineWidth: 2
						}
					}]
				});

			}
		},
		error: function(data) {
			console.log(data);
		}
	});
	//fiveYearTrend1

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-five-year-trend1",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData;
				var totalDiff = allData.total_diff;



				var datalist = [[totalDiff]];
				$('.saving-amount').html(totalDiff);
				var thirdYear = allData.thirdYear;
				var secondYear = allData.secondYear;
				var lastYear = allData.lastYear;


				var dataPoints = [thirdYear, secondYear, lastYear];  // Replace with your actual data

				Highcharts.chart('fiveYearTrend1', {
					chart: {
						type: 'line',
						animation: true,
						backgroundColor: 'transparent',
						height: 50,
						margin: 10,
						// Disable the context menu
						events: {
							load: function() {
								this.container.oncontextmenu = function(e) {
									e.preventDefault();
								};
							}
						}
					},
					exporting: {
						enabled: false
					},
					legend: { enabled: false },
					title: {
						text: '' // Remove the chart title
					},
					xAxis: {
						categories: ['Year1', 'Year2', 'Year3'], // Replace with your actual years
						labels: {
							enabled: true
						}
					},
					yAxis: {
						title: {
							text: 'Amount'
						}
					},
					plotOptions: {
						line: {
							dataLabels: {
								enabled: true,
								formatter: function() {
									return this.y;
								}
							}
						}
					},
					series: [{
						name: 'Trend',
						data: dataPoints,
						color: '#F79C92',
						dashStyle: 'ShortDashDot',
						marker: {
							symbol: 'circle',
							radius: 6,
							fillColor: '#F79C92',
							lineColor: '#ffffff',
							lineWidth: 2
						}
					}]
				});


			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	//fiveYearTrend2

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-five-year-trend2",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData;
				var totalTax = allData.total_tax;

				$('.foregone-amount').html(totalTax);

				var datalist = [[totalTax]];

				var dataPoints = [0, 0, 0];

				Highcharts.chart('fiveYearTrend2', {
					chart: {
						type: 'line',
						animation: true,
						backgroundColor: 'transparent',
						height: 50,
						margin: 10,
						// Disable the context menu
						events: {
							load: function() {
								this.container.oncontextmenu = function(e) {
									e.preventDefault();
								};
							}
						}
					},
					exporting: {
						enabled: false
					},
					legend: { enabled: false },
					title: {
						text: '' // Remove the chart title
					},
					xAxis: {
						categories: ['Year1', 'Year2', 'Year3'], // Replace with your actual years
						labels: {
							enabled: true
						}
					},
					yAxis: {
						title: {
							text: 'Amount'
						}
					},
					plotOptions: {
						line: {
							dataLabels: {
								enabled: true,
								formatter: function() {
									return this.y;
								}
							}
						}
					},
					series: [{
						name: 'Trend',
						data: dataPoints,
						color: '#B422B6',
						dashStyle: 'ShortDashDot',
						marker: {
							symbol: 'circle',
							radius: 6,
							fillColor: '#B422B6',
							lineColor: '#ffffff',
							lineWidth: 2
						}
					}]
				});

			}
		},
		error: function(data) {
			console.log(data);
		}
	});




	//customer acquisition cost
	chart = Highcharts.chart('nrOfSuppliers', {
		chart: {
			type: 'column',
			backgroundColor: 'transparent',
			height: 100,
			margin: 0
		},
		colors: [
			'#B422B6',
			'#F79C92',
			'#CA7CE5'
		],

		plotOptions: {
			column: {
				colorByPoint: true
			}
		},
		credits: false,
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
			type: 'category',
			title: {
				text: ""
			},
			labels: {
				enabled: false
			},
			lineColor: 'transparent'
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
			data: [
				['1', 21],
				['2', 109],
				['3', 67]
			],
			pointWidth: 100,
			dataLabels: {
				enabled: true,
				rotation: 0,
				color: '#000000',
				align: 'center',
				style: {
					fontSize: '13px'

				}
			}
		}]
	});
}
function getComplanceFilter() {
	callingAllFunction();
}
function resetComplanceFilter() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate1").val(fromDate);
	$("#toDate1").val(toDate);
	callingAllFunction();
}
function getPurchaseCompData() {
	callingAllFunction();
}
function getPurchaseComplDataDiv() {
	callingAllFunction();
}