function requirementHighChat() {

	getRequirementHeadData();


	var fromDate = $("#fromDate9").val();
	var toDate = $("#toDate9").val();
	var org = $("#qualRequiredOrg").find('option:selected').text();
	var orgDiv = $("#qualRequiredOrgDiv").find('option:selected').text();
	var loc = $("#reqLoc").find('option:selected').text();


	//Ajax for Requirement progress Distribution pie chart
	$.ajax({
		url: "view-dashboard-requirement-progress-distribution-pie",
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
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});

	Highcharts.chart('newRequirementProgressDistribution', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie',
			height: 221,
		},
		title: {
			text: ''
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		accessibility: {
			point: {
				valueSuffix: '%'
			}
		},
		navigation: {
			buttonOptions: {
				enabled: false
			}
		},
		plotOptions: {
			pie: {
				colors: [
					'#B422B6',
					'#F79C92',
					'#BF05FF',
					'#CA7CE5'
				],
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<span>{point.name}</span><br>' +
						'<span>{point.percentage:.0f} %</span>',
					connectorColor: 'transparent',
					distance: 4,
				},
				center: ['50%', '50%'],
				size: '100%'
			}
		},
		series: [{
			name: 'Share',
			data: [{
				name: 'Fulfilled',
				y: 40
			},
			{
				name: 'On Track',
				y: 33
			},
			{
				name: 'Delayed',
				y: 27
			}
			]
		}],
		credits: {
			enabled: false
		}
	});

	//Ajax for Requirement progress Distribution bar chart
	$.ajax({
		url: "view-dashboard-requirement-progress-distribution-bar",
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
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});
	Highcharts.chart('newRequirementDelayDistribution', {
		chart: {
			type: 'column',
			height: 220,
		},
		title: null,
		xAxis: {
			categories: [
				'1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'
			]
		},
		yAxis: {
			title: '',
			min: 0,
			max: 150,
		},
		plotOptions: {
			series: {
				grouping: false,
				groupPadding: -0.134,
				borderWidth: 0,
			}
		},
		credits: {
			enabled: false
		},
		exporting: {
			enabled: false
		},
		legend: {
			align: 'left',
			verticalAlign: 'top',
			x: 0,
		},
		series: [{
			name: 'Ontrack',
			data: [
				29.9, 45.5, 56.4, 69.2, 28.0, 76.0,
				15.6, 77.8, 5.7, 28.5, 16.4, 14.1
			],
			zIndex: 1,
			color: '#BF05FF',
		}, {
			name: 'Initiated',
			data: [
				74.1, 55.6, 64.4, 79.9, 71.5, 116.4,
				29.2, 104.0, 11.0, 65.6, 65.5, 16.4
			],
			zIndex: 0,
			color: '#CA7CE5',
		}, {
			name: 'Delayed',
			data: [
				104.1, 85.6, 94.4, 99.9, 107.5, 126.4,
				109.2, 144.0, 101.0, 85.6, 125.5, 66.4
			],
			zIndex: -1,
			color: '#F79C92',
		}]
	});

	//Ajax for Requirement progress Delay Distribution table
	$.ajax({
		url: "view-dashboard-requirement-delay-distribution",
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
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});

	//Ajax for Requirement Summary Table
	$.ajax({
		url: "view-dashboard-requirement-summary",
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
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});
}

function getRequirementHeadData() {

	var fromDate = $("#fromDate9").val();
	var toDate = $("#toDate9").val();
	var org = $("#qualRequiredOrg").find('option:selected').text();
	var orgDiv = $("#qualRequiredOrgDiv").find('option:selected').text();
	var loc = $("#reqLoc").find('option:selected').text();

	//Ajax for Requirement Head Count 
	$.ajax({
		url: "view-dashboard-requirement-head-count",
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
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});
}

function getRequirementFilterData() {
	requirementHighChat()
}

function resetRequirementData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate7").val(fromDate);
	$("#toDate7").val(toDate);
	getRequirementFilterData();
}

/*function for onchange of ord & div*/

function puRequiredOrg() {
getRequirementFilterData();
}


function purRequiredOrgDiv() {
getRequirementFilterData();
}
