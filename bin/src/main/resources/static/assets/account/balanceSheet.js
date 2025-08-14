/*function balanceSheet() {

	getBalanceSheetData();
	
}

*/
function getBalanceSheetData() {
	$("#tabIdBalanceSheet").val('balanceSheet');
	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "dashboard-balance-sheet-days-or-others-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllocationData;

				///////////////////////////////  All Days Start  ///////////////////////////////////////////

				Highcharts.chart("DaysSalesInventory", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						margin: 0,
					},

					title: {
						text: "10 Days",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 25,
						margin: 0,
						style: { fontSize: "12", color: "#61426c" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -89,
						endAngle: 88,
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
								to: 20,
								thickness: 20,
								color: "#bf05ff",
							},
							{
								from: 20,
								to: 100,
								thickness: 20,
								color: "#f1d3fc",
							},
						],
					},

					series: [
						{
							name: "",
							data: [20],
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "70%",
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



				//Days Sales Outstanding

				Highcharts.chart("DaysSalesOutstanding", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						margin: 0,
					},

					title: {
						text: "23 Days",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 25,
						margin: 0,
						style: { fontSize: "12", color: "#61426c" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -89,
						endAngle: 88,
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
								to: 80,
								thickness: 20,
								color: "#F79C92",
							},
							{
								from: 80,
								to: 100,
								thickness: 20,
								color: "#f1d3fc",
							},
						],
					},

					series: [
						{
							name: "",
							data: [80],
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "70%",
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





				//Days Inventory Outstanding

				Highcharts.chart("DaysInventoryOutstanding", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						margin: 0,
					},

					title: {
						text: "20 Days",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 25,
						margin: 0,
						style: { fontSize: "12", color: "#61426c" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -89,
						endAngle: 88,
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
								to: 20,
								thickness: 20,
								color: "#56156C",
							},
							{
								from: 20,
								to: 100,
								thickness: 20,
								color: "#f1d3fc",
							},
						],
					},

					series: [
						{
							name: "",
							data: [20],
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "70%",
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





				//Days Payable Outstanding

				Highcharts.chart("DaysPayableOutstanding", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						margin: 0,
					},

					title: {
						text: "52 Days",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 25,
						margin: 0,
						style: { fontSize: "12", color: "#61426c" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -89,
						endAngle: 88,
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
								to: 52,
								thickness: 20,
								color: "#B422B6",
							},
							{
								from: 52,
								to: 100,
								thickness: 20,
								color: "#f1d3fc",
							},
						],
					},

					series: [
						{
							name: "",
							data: [52],
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "70%",
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




				//Cash to Cash Cycle

				Highcharts.chart("CashtoCashCycle", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 150,
						margin: 0,
					},

					title: {
						text: "55 Days",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 25,
						margin: 0,
						style: { fontSize: "12", color: "#61426c" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -89,
						endAngle: 88,
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
								to: 55,
								thickness: 20,
								color: "#BF05FF",
							},
							{
								from: 55,
								to: 100,
								thickness: 20,
								color: "#f1d3fc",
							},
						],
					},

					series: [
						{
							name: "",
							data: [55],
							dataLabels: {
								enabled: false,
							},
							dial: {
								radius: "70%",
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




				///////////////////////////////  All Days End  ///////////////////////////////////////////




			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-over-all-cash-by-year",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				// Creditors Turnover chart

				Highcharts.chart('OverallCashbyYear', {
					chart: {
						type: 'column',
						animation: true,
						height: 200
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'Jun 23'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 50, 15, 80, 45, 16],
						color: '#bf05ff',
						dataLabels: {
							enabled: false,
						}
					}]
				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})



	//Over all Cash by Year

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-debt-ratios",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				//Debt Ratios


				Highcharts.chart('DebtRatios', {
					chart: {
						type: 'spline',
						height: 200
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['2019', '2020', '2021', '2022', '2023', '2024', '2025'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						name: 'Debt to Asset Ratio',
						marker: {
							symbol: 'circle'
						},
						data: [1.31, 1.29, 1.67, 1.91, 1.31, 1.15, 1.38],
						color: '#BF05FF'

					}, {
						name: 'Debt to Equity Ratio',
						marker: {
							symbol: 'circle'
						},
						data: [0.57, 0.56, 0.62, 0.66, 0.57, 0.53, 0],
						color: '#F79C92'
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-receivable-payable-turnover",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				//Account Receivable Turnover

				Highcharts.chart('AccountReceivableTurnover', {
					chart: {
						type: 'column',
						height: 250
					},
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: [
							'2019',
							'2020',
							'2021',
							'2022',
							'2023',
							'2024',
							'2025'
						],
						crosshair: true
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					yAxis: {
						gridLineColor: 'transparent',
						min: 0,
						title: {
							text: ''
						}
					},
					tooltip: {
						headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
						pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
							'<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
						footerFormat: '</table>',
						shared: true,
						useHTML: true
					},
					plotOptions: {
						column: {
							pointPadding: 0.2,
							pointWidth: 40,
							borderWidth: 1
						}
					},
					series: [{
						name: 'Account Receivable Turnover',
						data: [1.72, 1.56, 1.67, 1.57, 1.55, 1.52, 1.67],
						color: '#bf05ff',
						dataLabels: {
							enabled: true,
						}
					}, {
						name: 'Account Payable Turnover',
						data: [2.66, 2.60, 2.57, 2.55, 2.38, 2.45, 2.58],
						color: '#F79C92',
						dataLabels: {
							enabled: true,
						}
					}]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-over-all-financial-statment",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-data-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////



}


function getBalanceSheetPayableData() {
	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "dashboard-balance-sheet-payable-tab-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllocationData;


			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////


	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-count-volume-vs-age",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				//Count/Voume vs Age

				Highcharts.chart('countvolume', {

					chart: {
						type: 'column',
						height: 250
					},

					title: {
						text: ''
					},

					xAxis: {
						categories: ['0-30', '31-60', '61-90', '91-120', '120+']
					},

					yAxis: {
						allowDecimals: false,
						min: 0,
						title: {
							text: ''
						}
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					tooltip: {
						formatter: function() {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>' +
								'Total: ' + this.point.stackTotal;
						}
					},

					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},

					legend: {
						enabled: false
					},

					series: [{
						name: 'Overdue',
						data: [5, 3, 4, 8, 10],
						color: '#CA7CE5'
					}, {
						name: 'Outstanding',
						data: [3, 4, 4, 6, 9],
						color: '#F79C92'
					}

					]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-payable-par-vs-discount",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Payable at Par vs at Discount


				Highcharts.chart('payablepardis', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						name: 'At Par',
						marker: {
							symbol: 'circle'
						},
						data: [11, 8, 10, 14, 9, 15],
						color: '#BF05FF'

					}, {
						name: 'Discount',
						marker: {
							symbol: 'circle'
						},
						data: [10, 12, 8, 15, 8, 14],
						color: '#F79C92'
					}]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-department-wise-outstanding",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);

				//Department wise Outstanding amount

				Highcharts.chart('deptoutstandamount', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						type: 'pie',
						height: 250
					},
					title: {
						text: '',
						align: 'left'
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
								style: {
									fontWeight: 'bold',
									color: 'white'
								},
								connectorColor: 'rgba(128,128,128,1)',
								distance: -40,
							},
							center: ['50%', '50%'],
							size: '110%'
						}
					},
					series: [{
						name: 'Share',
						data: [
							{ name: 'Home', y: 21 },
							{ name: 'Fashion', y: 32 },
							{ name: 'Food', y: 26 },
							{ name: 'Wellness', y: 21 }

						]
					}],

					credits: {
						enabled: false
					}


				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-top-vendor-by-due",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				//Top 5 Vendors by amount due 

				Highcharts.chart('vendorsbyamount', {
					chart: {
						type: 'column',
						animation: true,
						height: 180
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Fashion', 'Product', 'Food', 'Medicine', 'Home'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [123, 543, 242, 132, 444],
						color: '#bf05ff',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-upcoming-payment",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



				//Upcoming Payments 

				Highcharts.chart('upcomingpayment', {
					chart: {
						type: 'column',
						animation: true,
						height: 180
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'Jun 23'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 50, 15, 80, 45, 16],
						color: '#F79C92',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-purchase-payable-vs-payable",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				//Purchase/Account Payable vs Time  

				Highcharts.chart('purchaseaccount', {
					chart: {
						type: 'column',
						animation: true,
						height: 180
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'Jun 23'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 80, 15, 85, 45, 20],
						color: '#B422B6',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-expense-type-wise-outstanding",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);






				//Department wise Outstanding amount

				Highcharts.chart('expnsamount', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						type: 'pie',
						height: 250
					},
					title: {
						text: '',
						align: 'left'
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
								style: {
									fontWeight: 'bold',
									color: 'white'
								},
								connectorColor: 'rgba(128,128,128,1)',
								distance: -40,
							},
							center: ['50%', '50%'],
							size: '110%'
						}
					},
					series: [{
						name: 'Share',
						data: [
							{ name: 'Marketing', y: 21 },
							{ name: 'Supplies', y: 32 },
							{ name: 'Maintainance', y: 26 },
							{ name: 'Insurance', y: 21 }

						]
					}],

					credits: {
						enabled: false
					}


				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-ytd-amount-paid",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				Highcharts.chart('ytdamount', {

					chart: {
						type: 'column',
						height: 250
					},

					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'June 23']
					},

					yAxis: {
						allowDecimals: false,
						min: 0,
						title: {
							text: ''
						}
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					tooltip: {
						formatter: function() {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>' +
								'Total: ' + this.point.stackTotal;
						}
					},

					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},

					series: [{
						name: 'Amount Paid',
						data: [20, 22, 18, 32, 28, 35],
						stack: 'male',
						color: '#CA7CE5'
					}, {
						name: 'On Time',
						data: [15, 17, 16, 18, 18, 28],
						stack: 'male',
						color: '#F79C92'
					}, {
						name: 'Overdue',
						data: [10, 18, 15, 16, 10, 22],
						stack: 'male',
						color: '#BF05FF'
					}

					]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////







	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-avg-payment-age",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);

				//Average Age at Payment  
				Highcharts.chart('averagepayment', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 24', 'Feb 24', 'Mar 24', 'Apr 24', 'May 24', 'Jun 24'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineWidth: 0,
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						showInLegend: false,
						marker: {
							symbol: 'circle'
						},
						data: [28, 48, 30, 14, 31, 15],
						color: '#BF05FF'

					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-overdue-invoice-details-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-upcoming-payment-details-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})


}


function getBalanceSheetReceivableData() {

	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-balance-sheet-receivable-tab-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllocationData;



			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-receivable-count-vs-age",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				//Count/Volume vs Age

				Highcharts.chart('countvolumeage', {

					chart: {
						type: 'column',
						height: 274
					},

					title: {
						text: ''
					},

					xAxis: {
						categories: ['Inv-001', 'Inv-002', 'Inv-003', 'Inv-004', 'Inv-005', 'Inv-006']
					},

					yAxis: {
						allowDecimals: false,
						min: 0,
						title: {
							text: ''
						}
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					tooltip: {
						formatter: function() {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>' +
								'Total: ' + this.point.stackTotal;
						}
					},

					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},

					series: [{
						name: 'Aging Category',
						data: [70, 85, 70, 85, 75, 98],
						color: '#CA7CE5'
					}, {
						name: 'Outstanding',
						data: [75, 86, 95, 85, 95, 65],
						color: '#F79C92'
					}
						, {
						name: 'Overdue',
						data: [75, 86, 95, 85, 95, 80],
						color: '#BF05FF'
					}

					]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-invoice-category-breakdown",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				//Invoice Category

				Highcharts.chart('invoicecatbreakdown', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: null,
						plotShadow: false,
						type: 'pie',
						height: 274
					},
					title: {
						text: '',
						align: 'left'
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
								'#BF05FF'


							],
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<span style="font-size: 12px"><b>{point.name}</b></span><br>' +
									'<span style="opacity: 1">{point.percentage:.1f} %</span>',
								connectorColor: 'rgba(128,128,128,1)',
								distance: -40,
							},
							center: ['50%', '50%'],
							size: '110%'
						}
					},
					series: [{
						name: 'Share',
						data: [
							{ name: 'Dispute', y: 27 },
							{ name: 'Current', y: 40 },
							{ name: 'Overdue', y: 33 }


						]
					}],

					credits: {
						enabled: false
					}


				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////


	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-top-customer-by-amount-due",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				// Top5 Customer by amount due

				Highcharts.chart('customeramountdue', {
					chart: {
						type: 'bar',
						backgroundColor: '#ffffff',
						color: 'black',
						height: 274
					},
					title: {
						text: ''
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					xAxis: {
						categories: ['Fashion', 'Retail', 'Food', 'Technology', 'Health'],
						title: {
							text: null
						}

					},
					yAxis: {
						min: 0,
						color: 'black',
						gridLineWidth: 0,
						title: {
							text: '',
							align: 'high'
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						valueSuffix: ' '
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: true
							}
						}
					},

					credits: {
						enabled: false
					},

					legend: {
						enabled: false
					},
					series: [{
						name: 'Normal Hours',
						data: [50, 60, 80, 90, 110],
						color: '#bf05ff'
					}

					]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-avg-age-payment-rcvble",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				// Average Age at Payment

				Highcharts.chart('averageagepayment', {
					chart: {
						type: 'bar',
						backgroundColor: '#ffffff',
						color: 'black',
						height: 250
					},
					title: {
						text: ''
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					xAxis: {
						categories: ['Cust-001', 'Cust-002', 'Cust-003', 'Cust-004', 'Cust-005'],
						title: {
							text: null
						}

					},
					yAxis: {
						min: 0,
						color: 'black',
						gridLineWidth: 0,
						title: {
							text: '',
							align: 'high'
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						valueSuffix: ' '
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: true
							}
						}
					},

					credits: {
						enabled: false
					},

					legend: {
						enabled: false
					},
					series: [{
						name: 'Normal Hours',
						data: [70, 60, 50, 120, 90],
						color: '#F79C92'
					}

					]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-upcoming-receivable",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Upcoming Receivables 

				Highcharts.chart('upcomingrecv', {
					chart: {
						type: 'column',
						animation: true,
						height: 250
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23', 'Jun 23'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 50, 15, 80, 45, 16],
						color: '#bf05ff',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-overdue-invoice-details-receivable-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})




	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-upcoming-payment-dtls-receivable-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-sales-account-receivable-vs-time",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);





				//Sales Accnt Receivables  vs time

				Highcharts.chart('salesaccounttime', {
					chart: {
						type: 'column',
						animation: true,
						height: 250
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 23', 'Feb 23', 'Mar 23', 'Apr 23', 'May 23'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [80, 50, 60, 80, 45],
						color: '#B422B6',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


}
/*Receivables ends here*/


function getBalanceSheetFixedAssetsData() {

	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-balance-sheet-fixed-assets-tab-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllocationData;



			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-fixed-assets-balance-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Fixed assets balance trend

				Highcharts.chart('fixedassetsbalance', {
					chart: {
						type: 'column',
						animation: true,
						height: 250
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jun 21', 'Jul 21', 'Aug 21', 'Sep 21', 'Oct 21', 'Nov 21', 'Dec 21', 'Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 50, 15, 80, 45, 16, 60, 45, 90, 120, 40, 70, 65],
						color: '#bf05ff',
						dataLabels: {
							enabled: false,
						}
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-assets-turn-over-ratio-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Average Age at Payment  


				Highcharts.chart('assetsturnoverratio', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jul 21', 'Aug 21', 'Sept 21', 'Oct 21', 'Nov 21', 'Dec 21', 'Jan 22', 'Feb 22', 'Mar 22', 'Apr 22'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineWidth: 0,
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						showInLegend: false,
						marker: {
							symbol: 'circle'
						},
						data: [2700, 3800, 2300, 5500, 4500, 2000, 2450, 3000, 3200, 3400, 4500],
						color: '#B422B6'

					}]
				});






			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-fixed-assets-movement-by-month",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Fixed assets movements

				Highcharts.chart('fixedassetsmovements', {
					chart: {
						type: 'column',
						animation: true,
						height: 226
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jun 21', 'Jul 21', 'Aug 21', 'Sep 21', 'Oct 21', 'Nov 21', 'Dec 21', 'Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [30, 50, 15, 80, 45, 16, 60, 45, 90, 120, 40, 70, 65],
						color: '#F79C92',
						dataLabels: {
							enabled: false,
						}
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-assets-turnover-ratio-trend-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);



			}
		}, error: function(data) {
			console.log(data);
		}
	})

}


function getBalanceSheetWorkingCapitalData() {
	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-balance-sheet-working-capital-tab-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllocationData;



			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-current-ratio-mom-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				//Current Ratio MOM Trend 

				Highcharts.chart('currentrationmom', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineWidth: 0,
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						showInLegend: false,
						marker: {
							symbol: 'circle'
						},
						data: [0.2, 0.5, 0, 0.7, 0.3, 0.1],
						color: '#F79C92'

					}]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////


	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-net-working-capital-by-month",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);






				//Upcoming Receivables 

				Highcharts.chart('networkingcapital', {
					chart: {
						type: 'column',
						animation: true,
						height: 250
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					title: {
						text: '',
					},
					subtitle: {
						text: ''
					},
					xAxis: {
						categories: ['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							style: {
								fontSize: '10px',
							}
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						groupPadding: 0,
						data: [10000, 25000, 8000, 40000, 35000, 8000],
						color: '#bf05ff',
						dataLabels: {
							enabled: false,
						}
					}]
				});




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-quick-ratio-mom-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);






				//Current Ratio MOM Trend 

				Highcharts.chart('quickrationmomtrend', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineWidth: 0,
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						showInLegend: false,
						marker: {
							symbol: 'circle'
						},
						data: [0.2, 0.5, 1, 0.5, 0.8, 0.9],
						color: '#BF05FF'

					}]
				});






			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-asset-vs-liabilities",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


				//Assets vs Liabillities

				Highcharts.chart('assetsvsliability', {

					chart: {
						type: 'column',
						height: 250
					},

					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22']
					},

					yAxis: {
						gridLineColor: 'transparent',
						allowDecimals: false,
						min: 0,
						title: {
							text: ''
						}
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					tooltip: {
						formatter: function() {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>' +
								'Total: ' + this.point.stackTotal;
						}
					},

					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},

					legend: {
						enabled: true
					},

					series: [{
						name: 'Liabilities',
						data: [15, 3, 4, 8, 10, 15],
						color: '#CA7CE5'
					}, {
						name: 'Assets',
						data: [3, 4, 4, 6, 9, 11],
						color: '#F79C92'
					}

					]
				});






			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-inventory-turn-over-ratio-mom-trend",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				// Inventory Turnover

				Highcharts.chart('inventoryturnoverratio', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 22', 'Feb 22', 'Mar 22', 'Apr 22', 'May 22', 'Jun 22'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineWidth: 0,
						title: {
							text: ''
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						spline: {

							marker: {
								radius: 4,
								lineColor: '#666666',
								lineWidth: 1
							}
						}
					},

					navigation: {
						buttonOptions: {
							enabled: false
						}
					},

					credits: {
						enabled: false
					},

					series: [{
						showInLegend: false,
						marker: {
							symbol: 'circle'
						},
						data: [1, 3, 7, 1, 4, 2],
						color: '#F79C92'

					}]
				});






			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////






}

function getBalanceSheetComparisionData() {

	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#accOrgBalanSheet").find('option:selected').text();
	var orgDiv = $("#accOrgDivBalanSheet").find('option:selected').text();
	var loc = $("#balanSheetLoc").find('option:selected').text();


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-base-line-vs-comparision-table",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-balancesheet-base-line-vs-comparision-graph",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var monthDatas = [];
				var payableTurnOverDataSet = [];
				var totalPayableTurnOverRatio = 0.00;
				var averagePayableTurnOver = 0.00;
				var targetPayableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableSet = jsonData[i];
						monthDatas.push(payableSet.monthYear);
						var netCreditPurchase = parseFloat(payableSet.payableAmount);
						var averagePayableSetAmnt = parseFloat(payableSet.averagePayableAmount);
						let payableTurnOver = safeDivide(netCreditPurchase, averagePayableSetAmnt);

						payableTurnOverDataSet.push(parseFloat(payableTurnOver));
						totalPayableTurnOverRatio = (parseFloat(totalPayableTurnOverRatio) + parseFloat(payableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averagePayableTurnOver = totalPayableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetPayableTurnOverDataSet.push(parseFloat(averagePayableTurnOver));
					}
				}
				console.log("payableTurnOverDataSet--------------------" + payableTurnOverDataSet);
				console.log("targetPayableTurnOverDataSet--------------------" + targetPayableTurnOverDataSet);




				// Baseline vs Comparison 

				Highcharts.chart('baselinecomparision', {
					chart: {
						type: 'bar',
						backgroundColor: '#ffffff',
						color: 'black',
						height: 340
					},
					title: {
						text: ''
					},
					exporting: { enabled: false },
					credits: {
						enabled: false
					},
					xAxis: {
						categories: ['Assets', 'Liabilities', 'Equity'],
						title: {
							text: null
						}

					},
					yAxis: {
						min: 0,
						color: 'black',
						gridLineWidth: 0,
						title: {
							text: '',
							align: 'high'
						},
						labels: {
							format: '{value}'
						}
					},
					tooltip: {
						valueSuffix: ' '
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: true
							}
						}
					},

					credits: {
						enabled: false
					},
					series: [{
						name: 'Baseline',
						data: [50, 80, 90],
						color: '#bf05ff'
					},
					{
						name: 'Comparision',
						data: [55, 70, 80],
						color: '#F79C92'
					}


					]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})

}


function getBalaneSheetWithDate() {
	var tabValueForBalanceSheet = $("#tabIdBalanceSheet").val();
	if (tabValueForBalanceSheet === "balanceSheet") {
		getBalanceSheetData();
	} else if (tabValueForBalanceSheet === "payable") {
		getBalanceSheetPayableData();
	} else if (tabValueForBalanceSheet === "receivable") {
		getBalanceSheetReceivableData();
	} else if (tabValueForBalanceSheet === "fixedAssets") {
		getBalanceSheetFixedAssetsData();
	} else if (tabValueForBalanceSheet === "workingCapital") {
		getBalanceSheetWorkingCapitalData();
	} else if (tabValueForBalanceSheet === "comparision") {
		getBalanceSheetComparisionData();
	}
}


/*Function for reset button*/
function resetBalaneSheetDate() {
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
	getBalaneSheetWithDate();
}

/*Function for get data onchange of orgDiv*/
function getAccaccBalanSheetOrgDivData() {
	getBalaneSheetWithDate();
}
