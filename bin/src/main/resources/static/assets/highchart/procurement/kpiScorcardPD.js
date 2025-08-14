function kpiScorecardHighChat() {
	getCostSavingData();
	getDepartmentKpiData();
	getSupplierPerformanceData();
	getOperationalData();
	getAllSpendUnderManagementData();
	getAllMaverickData();
}

function getCostSavingData() {

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-kpi-cost-saving",
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

	/*COST SAVINGS*/
	Highcharts.chart('kpiCostSaving1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//kpiCostSaving2  
	Highcharts.chart('kpiCostSaving2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});

	//kpiCostSaving3 
	Highcharts.chart('kpiCostSaving3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//kpiCostSaving4  
	Highcharts.chart('kpiCostSaving4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});

}

function getDepartmentKpiData() {

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-department-kpis",
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

	/*department kpi1*/
	Highcharts.chart('departmentKPIs1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//department KPIs2
	Highcharts.chart('departmentKPIs2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//department KPIs3
	Highcharts.chart('departmentKPIs3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//department KPIs4
	Highcharts.chart('departmentKPIs4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
}

function getSupplierPerformanceData() {

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-kpi-supplier-performance",
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
	/*supplierPerformance1*/
	Highcharts.chart('supplierPerformance1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//supplier performance2
	Highcharts.chart('supplierPerformance2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//supplier performance3
	Highcharts.chart('supplierPerformance3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//supplier performance4
	Highcharts.chart('supplierPerformance4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
}

function getOperationalData() {
	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-kpi-operational",
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

	/*operationalKPIs1*/
	Highcharts.chart('operationalKPIs1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//operational KPIs2
	Highcharts.chart('operationalKPIs2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//operational KPIs3
	Highcharts.chart('operationalKPIs3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//operational KPIs4
	Highcharts.chart('operationalKPIs4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
}
function getAllSpendUnderManagementData() {

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-kpi-supplier-performance",
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
	/*spendUnderManagement1*/
	Highcharts.chart('spendUnderManagement1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Spend under management 2
	Highcharts.chart('spendUnderManagement2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Spend under management 3
	Highcharts.chart('spendUnderManagement3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Spend under management 4
	Highcharts.chart('spendUnderManagement4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
}

function getAllMaverickData() {

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#purcKpiOrg").find('option:selected').text();
	var orgDiv = $("#purcKpiOrgDiv").find('option:selected').text();
	var loc = $("#kpiLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-kpi-maverick",
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
	/*maverick1*/
	Highcharts.chart('maverick1', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Maverick 2
	Highcharts.chart('maverick2', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Maverick 3
	Highcharts.chart('maverick3', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
	//Maverick 4
	Highcharts.chart('maverick4', {
		chart: {
			type: 'line',
			animation: true,
			backgroundColor: 'transparent',
			height: 30,
			margin: 10
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
			data: [16.0, 28.2, 23.1, 17.9, 32.2],
			color: '#BF05FF',
			showInLegend: false,
			marker: {
				enabled: false
			}
		},]
	});
}

/*Saurav*/
function getKpiScorecardFilter() {
	getCostSavingData();
	getDepartmentKpiData();
	getSupplierPerformanceData();
	getOperationalData();
	getAllSpendUnderManagementData();
	getAllMaverickData();
}

function resetKpiScorecardFilter() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate6").val(fromDate);
	$("#toDate6").val(toDate);

	getCostSavingData();
	getDepartmentKpiData();
	getSupplierPerformanceData();
	getOperationalData();
	getAllSpendUnderManagementData();
	getAllMaverickData();
}

/*function for onchange Org & Div*/

function purcKpiChsngeOrg() {
	getCostSavingData();
	getDepartmentKpiData();
	getSupplierPerformanceData();
	getOperationalData();
	getAllSpendUnderManagementData();
	getAllMaverickData();
}

function purcKpiChsngeOrgDiv() {
	getCostSavingData();
	getDepartmentKpiData();
	getSupplierPerformanceData();
	getOperationalData();
	getAllSpendUnderManagementData();
	getAllMaverickData();
}
