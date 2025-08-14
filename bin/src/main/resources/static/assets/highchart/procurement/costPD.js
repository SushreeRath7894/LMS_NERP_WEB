function costHighChat() {
	allCostChart();
}


function allCostChart() {

	var fromDate = $("#fromDate5").val();
	var toDate = $("#toDate5").val();
	var org = $("#purcCostOrg").find('option:selected').text();
	var orgDiv = $("#purcCostOrgDiv").find('option:selected').text();
	var loc = $("#costLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-procurement-roi",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//COST REDUCTION BY SUPPLIER CATEGORY
	Highcharts.chart('procurementROI', {
		chart: {
			animation: true,
			height: 155,
			marginLeft: 30
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
			categories: ['Battery', 'Display', 'Others', 'Sensors', 'Switches', 'Transistors'],
			crosshair: true,
			lineColor: '#cccccc'
		}],
		yAxis: [{
			labels: {
				format: '{value}',
				style: {
					color: '#136e6d'
				}
			},
			title: {
				text: 'Temperature',
				style: {
					display: 'none',
				}
			}
		}, {
			title: {
				text: 'Precipitation',
				style: {
					display: 'none',
				}
			},
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
			name: 'Precipitation',
			type: 'column',
			color: '#B422B6',
			yAxis: 1,
			data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7],

		}, {
			name: 'Benchmark',
			type: 'spline',
			data: [10, 10, 10, 10, 10, 10],
			color: '#b4617c',
			lineWidth: 3,
			dataLabels: {
				enabled: false,
			},
			marker: {
				enabled: false
			}
		}]
	});



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-fiveyear-trend",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//cost 5 year trend
	Highcharts.chart('costFiveYearTrend', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 5
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
			tickLength: 0,
			tickWidth: 0,
			lineColor: 'transparent',
			labels: {
				enabled: false,
			},
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
		},]
	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-fiveyear-trend1",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost 5 year trend 1
	Highcharts.chart('costFiveYearTrend1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 5
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
			tickLength: 0,
			tickWidth: 0,
			lineColor: 'transparent',
			labels: {
				enabled: false,
			},
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
		},]
	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-saving-fiveyear-trend",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost 5 year trend 2
	Highcharts.chart('costFiveYearTrend2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 5
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
			tickLength: 0,
			tickWidth: 0,
			lineColor: 'transparent',
			labels: {
				enabled: false,
			},
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
		},]
	});

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-avoidance-fiveyear-trend",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost 5 year trend 3 COST AVOIDANCE
	Highcharts.chart('costFiveYearTrend3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 5
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
			tickLength: 0,
			tickWidth: 0,
			lineColor: 'transparent',
			labels: {
				enabled: false,
			},
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
		},]
	});

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-fiveyear-trend",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost 5 year trend 4  PROCUREMENT ROI
	Highcharts.chart('costFiveYearTrend4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 5
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
			tickLength: 0,
			tickWidth: 0,
			lineColor: 'transparent',
			labels: {
				enabled: false,
			},
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
		},]
	});



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-reduction-supplier",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost reduction by supplier category
	Highcharts.chart('costReductionBySupplier', {
		chart: {
			type: 'pie',
			animation: true,
			height: 300
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
				shadow: false
			}
		},
		tooltip: {
			formatter: function() {
				return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
			}
		},
		series: [{
			name: '',
			data: [
				{
					name: 'Transistor',
					y: 34,
					color: "#56156C"
				},
				{
					name: 'Switches',
					y: 19,
					color: "#F79C92"
				},
				{
					name: 'Sensors',
					y: 16,
					color: "#bf05ff"
				},
				{
					name: 'Battery',
					y: 15,
					color: "#B422B6"
				},
				{
					name: 'Display',
					y: 10,
					color: "#CA7CE5"
				},
				{
					name: 'Other',
					y: 6,
					color: "#10A1E4"
				}
			],

			size: '50%',
			innerSize: '45%',
			showInLegend: false,
			dataLabels: {
				enabled: true,
				format: '{point.percentage:.1f} %'
			}
		}]
	});

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-saving",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost savings
	//COST REDUCTION BY SUPPLIER CATEGORY
	Highcharts.chart('costSavings', {
		chart: {
			type: 'bar',
			animation: true,
			height: 150
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
		xAxis: {
			categories: ['Switches', 'Display', 'Transistors', 'Others', 'Sensors', 'Battery'],
			title: {
				text: 'SAVINGS',
			},
			gridLineColor: 'transparent',
			lineColor: '#cccccc'
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			},
			labels: {
				enabled: false,
			},
			gridLineWidth: 0
		},
		tooltip: {
			valueSuffix: '%'
		},
		plotOptions: {
			bar: {
				dataLabels: {
					enabled: true,
					format: '{y} %'
				},
				groupPadding: 0.1
			}
		},
		legend: {
			enabled: false,
		},
		credits: {
			enabled: false
		},
		series: [{
			name: '',
			data: [17, 16, 15, 15, 12, 9],
			color: '#bf05ff'
		}],

	});

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-cost-avoidance",
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

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//cost avoidance
	//COST REDUCTION BY SUPPLIER CATEGORY
	Highcharts.chart('costAvoidance', {
		chart: {
			type: 'bar',
			animation: true,
			height: 150
		}, navigation: {
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
		xAxis: {
			categories: ['Switches', 'Display', 'Transistors', 'Others', 'Sensors', 'Battery'],
			title: {
				text: 'AVOIDANCE',
			},
			gridLineColor: 'transparent',
			lineColor: '#cccccc'
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			},
			labels: {
				enabled: false,
			},
			gridLineWidth: 0
		},
		tooltip: {
			valueSuffix: '%'
		},
		plotOptions: {
			bar: {
				dataLabels: {
					enabled: true,
					format: '{y} %'
				},
				groupPadding: 0.1
			}
		},
		legend: {
			enabled: false,
		},
		credits: {
			enabled: false
		},
		series: [{
			name: '',
			data: [17, 16, 15, 15, 12, 9],
			color: '#F79C92'
		}],

	});

}

function getCostFilter() {
	allCostChart();
}

function resetCostFilter() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate5").val(fromDate);
	$("#toDate5").val(toDate);

	allCostChart();
}
/*For onchange of org div*/
function purCostChangeOrg() {
	allCostChart();
}

function purDeliCostOrgDiv() {
	allCostChart();
}