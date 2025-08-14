function purchaseHighChat() {


	getHeadCountData();

	let H = Highcharts;

	var fromDate = $("#fromDate16").val();
	var toDate = $("#toDate16").val();
	var org = $("#purSumOrg").find('option:selected').text();
	var orgDiv = $("#purSumOrgDiv").find('option:selected').text();
	var loc = $("#purSumLoc").find('option:selected').text();

	//Ajax for po value highchart
	$.ajax({
		url: "view-dashboard-purchase-po-value",
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
			var categories = jsonData.categories;
		}
	});

	H.chart('purchaseOrderSummaryPOValueProducts', {
		chart: {
			height: 272,
			marginBottom: 10,
		},
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
		title: {
			text: ''
		},
		series: [{
			type: 'treemap',
			layoutAlgorithm: 'squarified',
			dataLabels: {
				enabled: true,
				style: {
					color: '#FFFFFF',
				},
				formatter: function() {
					return this.point.name + ' (' + this.point.value + 'M)';
				}
			},
			data: [
				{
					name: 'Data 1',
					value: 2,
					color: '#CA7CE5',
				},
				{
					name: 'Data 2',
					value: 8,
					color: '#BF05FF',
				},
				{
					name: 'Data 3',
					value: 2,
					color: '#CA7CE5',
				},
				{
					name: 'Data 4',
					value: 1,
					color: '#F79C92',
				},
				{
					name: 'Data 5',
					value: 1,
					color: '#F79C92',
				}
			]
		}]
	});
	//Ajax for summary trend highchart
	$.ajax({
		url: "view-dashboard-purchase-summary-trend",
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
			var categories = jsonData.categories;
		}
	});
	H.chart('purchaseOrderSummaryTrend', {
		chart: {
			type: 'spline',
			height: 273,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 6),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			floor: 15,
			ceiling: 56,
			tickAmount: 5,
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			spline: {
				marker: {
					enabled: true
				},
			}
		},
		series: [{
			name: "Total Value",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3), 15],
				[Date.UTC(2021, 6, 1), 55],
			],
			color: '#BF05FF',
		},
		{
			name: "Maverick Value",
			data: [
				[Date.UTC(2020, 9), 50],
				[Date.UTC(2020, 12), 25.28],
				[Date.UTC(2021, 3), 40],
				[Date.UTC(2021, 6, 1), 35],
			],
			color: '#CA7CE5',
		}],
		credits: { enabled: false },
		exporting: { enabled: false },
		legend: {
			align: 'left',
			verticalAlign: 'top',
			x: 0,
		},
	});
	// Ajax for reason wise bucketed table  

	$.ajax({
		url: "view-dashboard-purchase-order-reasonwise-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			var tableBody = $('#dynamicTable tbody');
			tableBody.empty();

		},
		error: function(error) {
			console.log('Error fetching data:', error);
		}
	});

	//Ajax for pos value highchart
	$.ajax({
		url: "view-dashboard-purchase-pos-value",
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
			var categories = jsonData.categories;
		}
	});
	H.chart('purchaseOrderSummaryPOValue', {
		chart: {
			type: 'treemap',
			width: 200,
			height: 292,
			marginBottom: 10,
		},
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
		title: {
			text: ''
		},
		series: [{
			layoutAlgorithm: 'squarified',
			alternateStartingDirection: true,
			dataLabels: {
				enabled: true,
				style: {
					color: '#FFFFFF',
				},
				formatter: function() {
					return this.point.name + ' (' + this.point.value + ')';
				}
			},
			data: [{
				name: '',
				value: 30,
				color: '#CA7CE5',
				dataLabels: {
					enabled: false
				}
			},
			{
				name: 'Strategic',
				value: 70,
				color: '#BF05FF',
			}]
		}]
	});
}

function getHeadCountData() {

	var fromDate = $("#fromDate16").val();
	var toDate = $("#toDate16").val();
	var org = $("#purSumOrg").find('option:selected').text();
	var orgDiv = $("#purSumOrgDiv").find('option:selected').text();
	var loc = $("#purSumLoc").find('option:selected').text();

	$.ajax({
		url: "view-dashboard-purchase-order-head-count",
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

function filterPurchaseOrder() {
	purchaseHighChat();

}
function resetSummayData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate16").val(fromDate);
	$("#toDate16").val(toDate);
	purchaseHighChat();
}


/*On Change of Org Div*/

function getPurSumOrgData() {
	purchaseHighChat();
}

function getPurSumOrgDivData() {
	purchaseHighChat();
}
/*End Summary Tab*/



/*Pending Tb started*/
function getPendingData() {


	let H = Highcharts;

	getPendingHeadData();
	getStratigicData();
	getPendingPurchaseStratigicData();

	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();

	//Ajax for purchase order pending trend data
	$.ajax({
		url: "view-dashboard-purchase-order-pending-trend-data",
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
			var categories = jsonData.categories;
		}
	});
	H.chart('purchaseOrderPendingTrend', {
		chart: {
			type: 'spline',
			height: 194,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 6),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			floor: 15,
			ceiling: 56,
			tickAmount: 5,
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			spline: {
				marker: {
					enabled: true
				},
			}
		},
		series: [{
			name: "Total Value",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3), 15],
				[Date.UTC(2021, 6, 1), 55],
			],
			color: '#BF05FF',
		},
		{
			name: "Maverick Value",
			data: [
				[Date.UTC(2020, 9), 50],
				[Date.UTC(2020, 12), 25.28],
				[Date.UTC(2021, 3), 40],
				[Date.UTC(2021, 6, 1), 35],
			],
			color: '#CA7CE5',
		}],
		credits: { enabled: false },
		exporting: { enabled: false },
		legend: {
			align: 'left',
			verticalAlign: 'top',
			x: 0,
		},
	});



}

function getPendingHeadData() {

	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();

	//Ajax for distribution of reason highchart Strategic,filter and pending order strategic
	$.ajax({
		url: "view-dashboard-purchase-order-pending-head-count",
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
			var categories = jsonData.categories;
		}
	});

}


function getPurchasePendingFilter() {
	getPendingData();

}


function resetPendingData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate11").val(fromDate);
	$("#toDate11").val(toDate);
	getPendingData();
}



/*Strategic tab in pending  for highchat */

function getStratigicData() {

	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();


	//Ajax for purchase order pending Strategic
	$.ajax({
		url: "view-dashboard-purchase-distribution-reason-strategic",
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
			var categories = jsonData.categories;
		}
	});
	Highcharts.chart('purchaseOrderStrategicPie1', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie',
			height: 200,
			marginTop: 20,
			marginBottom: 0,
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
					format: '<span>{point.percentage:.0f} %</span>',
					connectorColor: 'transparent',
					distance: 4,
				},
				center: ['50%', '50%'],
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
}

/*maverick tab in pending for highchat*/

function getMaverickData() {

	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();

	//Ajax for distribution of reason highchart Marveric
	$.ajax({
		url: "view-dashboard-purchase-distribution-reason-maveric",
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
			var categories = jsonData.categories;
		}
	});

	Highcharts.chart('purchaseOrderStrategicPie2', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie',
			height: 200,
			marginTop: 20,
			marginBottom: 0,
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
					format: '<span>{point.percentage:.0f} %</span>',
					connectorColor: 'transparent',
					distance: 4,
				},
				center: ['50%', '50%'],
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
}
/*For Stratigic And Maverick grid of pending tab */

function getPendingPurchaseStratigicData() {
	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();

	//Ajax for purchase order pending Strategic
	$.ajax({
		url: "view-dashboard-pending-purchase-order-strategic",
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
			var categories = jsonData.categories;
		}
	});
}

function getPendingPurchaseMavericData() {


	var fromDate = $("#fromDate11").val();
	var toDate = $("#toDate11").val();
	var org = $("#purPendOrg").find('option:selected').text();
	var orgDiv = $("#purPendOrgDiv").find('option:selected').text();
	var loc = $("#purPendLoc").find('option:selected').text();

	//Ajax for pending purchase order maveric
	$.ajax({
		url: "view-dashboard-purchase-order-pending-marveric-data",
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
			var categories = jsonData.categories;
		}
	});

}


/*Onchnange of Pending Tab Fopr org&div*/

function getPurPendOrgData() {
	getPendingData();
}

function getPurPendOrgDivData() {
	getPendingData();
}
/*Pending tab end*/


/*Report Tab started*/
function getReportData() {

	var fromDate = $("#fromDate12").val();
	var toDate = $("#toDate12").val();
	var org = $("#purRepOrg").find('option:selected').text();
	var orgDiv = $("#purRepOrgDiv").find('option:selected').text();
	var loc = $("#purRepLoc").find('option:selected').text();


	$.ajax({
		url: "view-dashboard-purchase-requisition",
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
			var categories = jsonData.categories;
		}
	});




	$.ajax({
		url: "view-dashboard-purchase-order",
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
			var categories = jsonData.categories;
		}
	});
}




function getReportFilterData() {
	getReportData();
}
function resetReportData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate12").val(fromDate);
	$("#toDate12").val(toDate);
	getReportData();
}

/*function for org&div onchange*/

function getPurRepoOrgData() {
	getReportData();
}

function getPurRepOrgDivData() {
	getReportData();
}
