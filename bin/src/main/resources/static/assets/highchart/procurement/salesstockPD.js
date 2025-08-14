function salesstockHighChat() {
	getHeadData();
	let H = Highcharts;

	var fromDate = $("#fromDate18").val();
	var toDate = $("#toDate18").val();
	var org = $("#purSalVsStoOrg").find('option:selected').text();
	var orgDiv = $("#purSalVsStoOrgDiv").find('option:selected').text();
	var loc = $("#purRepLoc").find('option:selected').text();


	$.ajax({
		url: "view-dashboard-sale-stock-treemap",
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


		}
	});

	H.chart('salesVsStockTreeMap2', {
		chart: {
			type: 'treemap',
			height: 70,
			marginBottom: 10,
		},
		colorAxis: {
			minColor: '#F79C92',
			maxColor: '#BF05FF',
		},
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
		title: {
			text: ''
		},
		series: [{
			layoutAlgorithm: 'squarified',
			layoutStartingDirection: 'horizontal',
			data: [{
				name: 'A',
				value: 6,
				colorValue: 4
			}, {
				name: 'B',
				value: 6,
				colorValue: 3
			}, {
				name: 'C',
				value: 2,
				colorValue: 2
			}, {
				name: 'D',
				value: 2,
				colorValue: 1
			}],
			dataLabels: {
				enabled: false,
				style: {
					color: '#FFFFFF',
				},
				format: '<span>{point.name} ({point.value})</span>',
			},
		}]
	});


	$.ajax({
		url: "view-dashboard-product-stock-movement",
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

		}
	});

	H.chart('salesVsStockStackedBar', {
		chart: {
			type: 'bar',
			height: 150,
			marginBottom: 5,
		},
		title: {
			text: ''
		},
		xAxis: {
			categories: ['Fast Moving', 'Slow Moving', 'Non Moving', 'New Item']
		},
		yAxis: {
			labels: {
				enabled: false
			},
			min: 0,
			title: {
				text: ''
			}
		},
		legend: {
			align: 'left',
			verticalAlign: 'top',
			x: 0,
			y: 0,
		},
		plotOptions: {
			series: {
				stacking: 'normal',
				dataLabels: {
					enabled: true,
					align: 'right',
					backgroundColor: 'rgba(255, 255, 255, 0.5)',
				},
				pointPadding: 0.1,
				groupPadding: 0.1
			}
		},
		credits: {
			enabled: false
		},
		exporting: {
			enabled: false
		},
		series: [{
			name: 'Normal Stock',
			data: [25, 30, 50, 20],
			color: '#F79C92',
		},
		{
			name: 'Short Stock',
			data: [50, 25, 40, 35],
			color: '#CA7CE5',
		},
		{
			name: 'Excess Stock',
			data: [30, 40, 15, 60],
			color: '#BF05FF',
		}]
	});

	$.ajax({
		url: "view-dashboard-sale-stock-product-excess-short-report",
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


		}
	});
}




function getHeadData() {

	var fromDate = $("#fromDate18").val();
	var toDate = $("#toDate18").val();
	var org = $("#purSalVsStoOrg").find('option:selected').text();
	var orgDiv = $("#purSalVsStoOrgDiv").find('option:selected').text();
	var loc = $("#purRepLoc").find('option:selected').text();



	$.ajax({
		url: "view-dashboard-sale-stock-head-count",
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
			var allData = jsonData.saleVsStockData;
			$("#inventoryValue").text(parseFloat(allData[0].totalInventoryValue).toFixed(2));
			$("#numberOfProducts").text(allData[0].totalProduct);
			$("#inventoryValuePercentage").text(allData[0].inventoryValuePercentage);
			$("#averageDailySales").text(parseFloat(allData[0].averageDailySales).toFixed(2));


			//let H = Highcharts;

			Highcharts.chart('salesVsStockTreeMap1', {
				chart: {
					// width: 400,
					height: 342,
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
					layoutStartingDirection: 'horizontal',
					data: [{
						name: 'Excess Stock',
						value: allData[0].excessStock,
						color: '#BF05FF',
					}, {
						name: 'Short Stock',
						value: allData[0].shortStock,
						color: '#CA7CE5',
					}],
					dataLabels: {
						enabled: true,
						style: {
							color: '#FFFFFF',
						},
						format: '<span>{point.name} ({point.value})</span>',
					},
				}]
			});
		},
		error: function(error) {
			console.error(error);
		}
	});
}


function getSaleVsStockFilterData() {
	salesstockHighChat();
}


function resetSalesVsStock() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate18").val(fromDate);
	$("#toDate18").val(toDate);
	salesstockHighChat();

}

/*Function for onchange*/

function getPurSalVsStoOrgData() {
	salesstockHighChat();
}
function getPurSalVsStoOrgDivData() {
	salesstockHighChat();
}
