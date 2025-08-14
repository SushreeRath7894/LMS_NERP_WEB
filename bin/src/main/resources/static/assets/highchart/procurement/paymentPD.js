function paymentHighChat() {
	getPaymentHeadData()


	let H = Highcharts;

	var fromDate = $("#fromDate10").val();
	var toDate = $("#toDate10").val();
	var org = $("#quaPaySummOrg").find('option:selected').text();
	var orgDiv = $("#quaPaySummOrgDiv").find('option:selected').text();
	var loc = $("#paySummLoc").find('option:selected').text();

	//Ajax for trend separate by category
	$.ajax({
		url: "view-dashboard-payment-summary-trend-data",
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

	H.chart('paymentSummaryTrendCategory', {
		chart: {
			type: 'areaspline',
			height: 237,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 5, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: ''
			},
			floor: 15,
			ceiling: 60,
			tickAmount: 10
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
			}
		},
		series: [{
			name: "Series 1 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
				[Date.UTC(2022, 3, 1), 59.97],
				[Date.UTC(2022, 4, 1), 39.97],
				[Date.UTC(2022, 5, 1), 19.97],
			],
			color: '#BF05FF',
		},
		{
			name: "Series 2 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 50],
				[Date.UTC(2022, 1, 1), 24.28],
				[Date.UTC(2022, 2, 1), 41],
				[Date.UTC(2022, 3, 1), 36.97],
				[Date.UTC(2022, 4, 1), 49.97],
				[Date.UTC(2022, 5, 1), 59.97],
			],
			color: '#CA7CE5',
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

	//Ajax for tree Map2
	$.ajax({
		url: "view-dashboard-payment-summary-treemap1",
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

	H.chart('paymentSummaryTreeMaps1', {
		chart: {
			height: 292,
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
			alternateStartingDirection: true,
			data: [{
				name: 'Licenses (30)',
				value: 10,
				color: '#F79C92',
			}, {
				name: 'Goods (30)',
				value: 15,
				color: '#CA7CE5',
			}, {
				name: 'Services (30)',
				value: 20,
				color: '#BF05FF',
			}]
		}]
	});

	//Ajax for tree Map2
	$.ajax({
		url: "view-dashboard-payment-summary-treemap2",
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
	H.chart('paymentSummaryTreeMaps2', {
		chart: {
			height: 292,
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
			alternateStartingDirection: true,
			data: [{
				name: 'EURODOLLARS (30)',
				value: 10,
				color: '#F79C92',
			}, {
				name: 'USD (30)',
				value: 15,
				color: '#CA7CE5',
			}, {
				name: 'POUNDS (30)',
				value: 20,
				color: '#BF05FF',
			}]
		}]
	});

	//Ajax for three way matching1
	$.ajax({
		url: "view-dashboard-payment-grn-amount",
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
	H.chart('paymentSummaryThreeWayMatching1', {
		chart: {
			type: 'spline',
			width: 280,
			height: 180,
			marginBottom: 50,
			marginLeft: 50,
		},
		title: {
			text: ''
		},
		xAxis: {
			title: {
				text: 'GRN Amount'
			},
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 3, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: 'PO Amount'
			},
			floor: 15,
			ceiling: 60,
			tickAmount: 10
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
			name: "Series 1 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
				[Date.UTC(2022, 3, 1), 59.97]
			],
			color: '#BF05FF',
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});


	//Ajax for three way matching 2
	$.ajax({
		url: "view-dashboard-payment-supplier-invoice-amount",
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
	H.chart('paymentSummaryThreeWayMatching2', {
		chart: {
			type: 'spline',
			width: 280,
			height: 146,
			marginBottom: 50,
			marginLeft: 50,
		},
		title: {
			text: ''
		},
		xAxis: {
			title: {
				text: 'Supplier Invoice Amount'
			},
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 3, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: 'PO Amount'
			},
			floor: 15,
			ceiling: 60,
			tickAmount: 10
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
			name: "Series 1 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
				[Date.UTC(2022, 3, 1), 59.97]
			],
			color: '#BF05FF',
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

	//Ajax for discrepancy summary
	$.ajax({
		url: "view-dashboard-payment-discrepancy-summary",
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

function getPaymentHeadData() {

	var fromDate = $("#fromDate10").val();
	var toDate = $("#toDate10").val();
	var org = $("#quaPaySummOrg").find('option:selected').text();
	var orgDiv = $("#quaPaySummOrgDiv").find('option:selected').text();
	var loc = $("#paySummLoc").find('option:selected').text();

	$.ajax({
		url: "view-dashboard-payment-head-count",
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


function getPaymentFilterData() {
	paymentHighChat()
}

function resetPaymentData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate10").val(fromDate);
	$("#toDate10").val(toDate);
	paymentHighChat();
}

/*get Data onChange of org&div of summary*/
function puPaySummOrg() {
	paymentHighChat();
}


function purPaySummOrgDiv() {
	paymentHighChat();
}

/*Pending Tab Started*/
function getPaymentPendingData() {

	getPaymentPendingHeadData();

	let H = Highcharts;

	var fromDate = $("#fromDate15").val();
	var toDate = $("#toDate15").val();
	var org = $("#quaPayPendOrg").find('option:selected').text();
	var orgDiv = $("#quaPayPendOrgDiv").find('option:selected').text();
	var loc = $("#payPendLoc").find('option:selected').text();

	//Ajax for payment Pending trend separated by category
	$.ajax({
		url: "view-dashboard-payment-pending-trend-data",
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

	H.chart('paymentPendingTrendCategory', {
		chart: {
			type: 'areaspline',
			height: 230,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 5, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: ''
			},
			floor: 15,
			ceiling: 60,
			tickAmount: 10
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
			}
		},
		series: [{
			name: "Series 1 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
				[Date.UTC(2022, 3, 1), 59.97],
				[Date.UTC(2022, 4, 1), 39.97],
				[Date.UTC(2022, 5, 1), 19.97],
			],
			color: '#BF05FF',
		},
		{
			name: "Series 2 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 50],
				[Date.UTC(2022, 1, 1), 24.28],
				[Date.UTC(2022, 2, 1), 41],
				[Date.UTC(2022, 3, 1), 36.97],
				[Date.UTC(2022, 4, 1), 49.97],
				[Date.UTC(2022, 5, 1), 59.97],
			],
			color: '#CA7CE5',
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

	//Ajax for payment Pending reason-wise bucketed
	$.ajax({
		url: "view-dashboard-payment-pending-reason-bucket",
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

	//Ajax for payment Pending summary
	$.ajax({
		url: "view-dashboard-pending-payment-summary",
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

function getPaymentPendingHeadData() {

	var fromDate = $("#fromDate15").val();
	var toDate = $("#toDate15").val();
	var org = $("#quaPayPendOrg").find('option:selected').text();
	var orgDiv = $("#quaPayPendOrgDiv").find('option:selected').text();
	var loc = $("#payPendLoc").find('option:selected').text();

	//Ajax for payment Pending head data
	$.ajax({
		url: "view-dashboard-payment-pending-head-count",
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

function getPaymentPendingFilterData() {
	getPaymentPendingData();
}
function resetPaymentPendingData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate15").val(fromDate);
	$("#toDate15").val(toDate);
	getPaymentPendingData();
}

/*onchanange of Pending Tab org&div*/


function puPayPendOrg() {
	getPaymentPendingData();
}

function purPayPendOrgDiv() {
	getPaymentPendingData();
}
