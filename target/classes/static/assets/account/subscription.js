function getSubscriptionWithDate() {
	subscription();
}

function subscription() {
	var fromDate = $("#fromDate8").val();
	var toDate = $("#toDate8").val();
	var org = $("#accOrgsubs").find('option:selected').text();
	var orgDiv = $("#accOrgDivsubs").find('option:selected').text();
	var loc = $("#subsLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-subscription-count",
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

				//Churn Rate

				Highcharts.chart("ChurnRate", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 200,
						margin: 0,
					},

					title: {
						text: "7.28%",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 30,
						margin: 0,
						style: { fontSize: "14", color: "#61426c" },
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




				//Subscription Cancellation Rate

				Highcharts.chart("SubscriptionCancellationRate", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 200,
						margin: 0,
					},

					title: {
						text: "14.66%",
						align: "center",
						verticalAlign: "bottom",
						floating: true,
						y: 30,
						margin: 0,
						style: { fontSize: "14", color: "#61426c" },
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


			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-monthly-recurring-revenue",
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

				// Monthly Recurring Revenue
				Highcharts.chart('MonthlyRecurringRevenue', {
					chart: {
						type: 'area',
						animation: true,
						height: 200,
					},
					title: {
						text: ''
					},
					credits: {
						enabled: false
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						categories: ['Jan 2024', 'Feb 2024', 'Mar 2024'],
					},
					yAxis: {
						title: {
							text: ''
						},
						gridLineColor: 'transparent',

					},
					legend: false,
					plotOptions: {

						area: {
							stacking: 'normal',
							lineColor: '#666666',
							lineWidth: 1,
							marker: {
								lineWidth: 1,
								lineColor: '#666666'
							}
						}
					},
					series: [{
						type: 'area',
						name: 'Special Request',
						data: [174, 54, 177],
						marker: { fillColor: '#b54c00', symbol: 'circle', radius: 2 },
						color: '#E6BFF4',
						lineColor: '#BF05FF'
					}]
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
		url: "dashboard-top-cust-by-monthly-recur-revenue",
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


				// Customers Recurring Revenue

				Highcharts.chart('CustomersRecurringRevenue', {
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
						categories: ['Customer_131', 'Customer_5', 'Customer_36', 'Customer_65', 'Customer_75'],
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
							format: '{value}K'
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
						data: [4.5, 3.75, 3.45, 3.08, 2.49],
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-top-cust-by-revenue",
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



				// Top Customers by Revenue

				Highcharts.chart('TopCustomersbyRevenue', {
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
						categories: ['Customer_88', 'Customer_49', 'Customer_150', 'Customer_91', 'Customer_80'],
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
							format: '{value}K'
						}
					},
					tooltip: {
						valueSuffix: ' '
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: true,
								style: {
									textOutline: false 
								  }
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
						data: [700, 700, 700, 550, 550],
						color: '#F58D68'
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
		url: "dashboard-top-compny-monthly-recur-revenue",
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


				// Companies Monthly Recurring Revenue

				Highcharts.chart('CompaniesMonthlyRecurringRevenue', {
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
						categories: ['Nexus Innovation', 'SmartBiz Systems', 'Mastermind Innovations', 'QuantumLeap Innovations'],
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
							format: '{value}K'
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
						data: [98, 89.78, 65.48, 65.47],
						color: '#B422B6'
					}

					]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-cust-account-status-breakdown",
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


				// Companies Monthly Recurring Revenue

				//Customer Account Status Breakdown	

				Highcharts.chart('CustomerAccountStatusBreakdown', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 250
					},
					title: {
						text: '',
						align: 'center',
						verticalAlign: 'middle',
						y: 60
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:0f}%</b>'
					},
					accessibility: {
						point: {
							valueSuffix: '%'
						}
					},
					credits: {
						enabled: false
					},

					exporting: { enabled: false },
					plotOptions: {
						pie: {
							colors: [
								'#CA7CE5',
								'#bf05ff',
								'#B422B6',
								'#56156C',
								'#F79C92'
							],
							dataLabels: {
								enabled: true,
								format: '<span style="font-size: 12px"><b>{point.name}</b></span><br>' +
									'<span style="opacity: 1">{point.percentage:.2f} %</span>',
								distance: 10,
								style: {
									fontWeight: 'normal',
									color: 'black'
								}
							},
							startAngle: -180,
							endAngle: 180,
							center: ['50%', '50%'],
							size: '70%'
						}
					},
					series: [{
						type: 'pie',
						name: '',
						innerSize: '50%',
						data: [
							['Active', 73.41],
							['Draft', 13.87],
							['Hold', 6.36],
							['Suspended', 3.47],
							['Cancelled', 2.89]

						]
					}]
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
		url: "dashboard-payment-month-breakdown",
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


				//Payment Method Breakdown

				Highcharts.chart('PaymentMethodBreakdown', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 250
					},
					title: {
						text: '',
						align: 'center',
						verticalAlign: 'middle',
						y: 60
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:0f}%</b>'
					},
					accessibility: {
						point: {
							valueSuffix: '%'
						}
					},
					credits: {
						enabled: false
					},

					exporting: { enabled: false },
					plotOptions: {
						pie: {
							colors: [
								'#CA7CE5',
								'#bf05ff',
								'#B422B6',
								'#56156C',
								'#F79C92'
							],
							dataLabels: {
								enabled: true,
								format: '<span style="font-size: 12px"><b>{point.name}</b></span><br>' +
									'<span style="opacity: 1">{point.percentage:.2f} %</span>',
								distance: 10,
								style: {
									fontWeight: 'normal',
									color: 'black'
								}
							},
							startAngle: -180,
							endAngle: 180,
							center: ['50%', '50%'],
							size: '70%'
						}
					},
					series: [{
						type: 'pie',
						name: '',
						innerSize: '50%',
						data: [
							['Check', 35.98],
							['Credit Card', 15.52],
							['Cash', 13.54],
							['Direct Deposit', 12.1],
							['ACH', 11.92],
							['Paypal', 10.93]
						]
					}]
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
		url: "dashboard-cust-subscription-by-status",
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


				//Customer Subscription by Status

				Highcharts.chart('CustomerSubscriptionbyStatus', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 250
					},
					title: {
						text: '',
						align: 'center',
						verticalAlign: 'middle',
						y: 60
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:0f}%</b>'
					},
					accessibility: {
						point: {
							valueSuffix: '%'
						}
					},
					credits: {
						enabled: false
					},

					exporting: { enabled: false },
					plotOptions: {
						pie: {
							colors: [
								'#CA7CE5',
								'#bf05ff',
								'#B422B6',
								'#F79C92'
							],
							dataLabels: {
								enabled: true,
								format: '<span style="font-size: 12px"><b>{point.name}</b></span><br>' +
									'<span style="opacity: 1">{point.percentage:.2f} %</span>',
								distance: 10,
								style: {
									fontWeight: 'normal',
									color: 'black'
								}
							},
							startAngle: -180,
							endAngle: 180,
							center: ['50%', '50%'],
							size: '70%'
						}
					},
					series: [{
						type: 'pie',
						name: '',
						innerSize: '50%',
						data: [
							['Lead', 38.46],
							['Cancelled', 21.8],
							['Active', 20.51],
							['Past Due', 19.23]
						]
					}]
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
		url: "dashboard-subscriptn-by-plan-status",
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

				//Subscriptions by Plan and Status

				Highcharts.chart('SubscriptionsbyPlanandStatus', {

					chart: {
						type: 'column',
						height: 250
					},

					title: {
						text: ''
					},

					xAxis: {
						categories: ['Active', 'Cancelled', 'Draft', 'Expired', 'Migrated', 'Provisioning', 'Suspended']
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
						data: [10, 1, 3, 2, 2, 3, 1],
						stack: 'male',
						color: '#CA7CE5'
					}, {
						name: 'On Time',
						data: [31, 2, 7, 8, 6, 7, 2],
						stack: 'male',
						color: '#F79C92'
					}, {
						name: 'Overdue',
						data: [45, 3, 15, 10, 10, 12, 3],
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
		url: "dashboard-account-receivble-by-pay-target",
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

				//Over all Cash by Year

				Highcharts.chart('AccountsReceivablebyPaymentTarget', {
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
						categories: ['61-90', 'Over 90'],
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
						data: [617, 6820],
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-payments-by-month",
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



				//Payments by Month

				Highcharts.chart('PaymentsbyMonth', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Jan 2024', 'Feb 2024', 'Mar 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}K'
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
						name: 'Refunded',
						marker: {
							symbol: 'circle'
						},
						data: [6.81, 23.77, 25.38],
						color: '#BF05FF',
						dataLabels: {
							enabled: true,
						}

					}, {
						name: 'Collected',
						marker: {
							symbol: 'circle'
						},
						data: [5.11, 8.19, 4.48],
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-transaction-count-by-month-type",
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


				//Transaction Count by Month

				Highcharts.chart('TransactionCountbyMonth', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}K'
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
						name: 'Refund',
						marker: {
							symbol: 'circle'
						},
						data: [8, 14, 58, 5, 7],
						color: '#BF05FF',
						dataLabels: {
							enabled: true,
						}

					}, {
						name: 'Payment',
						marker: {
							symbol: 'circle'
						},
						data: [1, 2, 4, 3, 2],
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-monthly-revenue-trend-analysis",
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


				//Monthly Recurring Revenue Trend Analysis

				Highcharts.chart('MonthlyRecurringRevenueTrendAnalysis', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}K'
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
						name: 'Refund',
						marker: {
							symbol: 'circle'
						},
						data: [0.63, 65.48, 97.46, 3.10, 89.78, 65.47],
						color: '#BF05FF',
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-annual-run-rate-trend-analysis",
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

				//Annual Run Rate Trend Analysis

				Highcharts.chart('AnnualRunRateTrendAnalysis', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}K'
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
					legend: {
						enabled: true
					},

					series: [{
						name: 'Annual Run Rate Trend Analysis',
						marker: {
							symbol: 'circle'
						},
						data: [7.56, 785.76, 1017, 37.20, 1008, 785.64],
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


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-monthly-recurring-revenue-churn",
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
				console.log(
					//Monthly Recurring Revenue Churn Rate

					Highcharts.chart('MonthlyRecurringRevenueChurnRate', {
						chart: {
							type: 'spline',
							height: 250
						},
						title: {
							text: ''
						},

						xAxis: {
							categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],

							accessibility: {
								description: 'Months of the year'
							}

						},
						yAxis: {
							title: {
								text: ''
							},
							labels: {
								format: '{value}K'
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
						legend: {
							enabled: true
						},

						series: [{
							name: 'Monthly Recurring Revenue Churn Rate',
							marker: {
								symbol: 'circle'
							},
							data: [60.50, -23.33, 6.54, 5.54, 60.50, 21.12],
							color: '#56156C',
							dataLabels: {
								enabled: true,
							}

						}]
					}));


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-customer-churn-rate-trend-analysis",
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


				//Customer Churn Rate Trend Analysis

				Highcharts.chart('CustomerChurnRateTrendAnalysis', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}%'
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
					legend: {
						enabled: true
					},

					series: [{
						name: 'Customer Churn Rate Trend Analysis',
						marker: {
							symbol: 'circle'
						},
						data: [29.74, 33.33, 12.50, 9.09, 8.33, 33.33],
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


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-avg-revenue-per-account",
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



				//Avg Revenue per Account

				Highcharts.chart('AvgRevenueperAccount', {
					chart: {
						type: 'spline',
						height: 250
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],

						accessibility: {
							description: 'Months of the year'
						}

					},
					yAxis: {
						title: {
							text: ''
						},
						labels: {
							format: '{value}K'
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
					legend: {
						enabled: true
					},
					series: [{
						name: 'Avg. Revenue per Account',
						marker: {
							symbol: 'circle'
						},
						data: [102.74, 140.20, 350.16, 939.89, 246.99, 2260],
						color: '#B422B6',
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




	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-invoice-summary-dtls",
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






				//Revenue Company Subscription


				let sampleData = [
					{ name: 'Company_1', value: 0.18, color: "#E7ACFB" },
					{ name: 'Company_2', value: 0.28, color: "#E7ACFB" },
					{ name: 'Company_3', value: 5.48, color: "#E7ACFB" },
					{ name: 'Company_4', value: 1.48, color: "#E7ACFB" },
					{ name: 'Company_5', value: 8.52, color: "#E7ACFB" },
					{ name: 'Company_6', value: 5.34, color: "#E7ACFB" },
					{ name: 'Company_7', value: 1.34, color: "#E7ACFB" },
					{ name: 'Company_8', value: 1.34, color: "#E7ACFB" },
					{ name: 'Company_9', value: 5.95, color: "#E7ACFB" },
					{ name: 'Company_10', value: 1.95, color: "#E7ACFB" },
					{ name: 'Company_11', value: 1.95, color: "#E7ACFB" },
					{ name: 'Company_12', value: 6.32, color: "#E7ACFB" },
					{ name: 'Company_13', value: 2.32, color: "#E7ACFB" },
					{ name: 'Company_14', value: 2.32, color: "#E7ACFB" },
					{ name: 'Company_15', value: 2.32, color: "#E7ACFB" },
					{ name: 'Company_16', value: 8.12, color: "#E7ACFB" },
					{ name: 'Company_17', value: 11.27, color: "#E7ACFB" },
					{ name: 'Company_18', value: 11.07, color: "#E7ACFB" },
					{ name: 'Company_19', value: 61.92, color: "#BF05FF" },
					{ name: 'Company_20', value: 6.12, color: "#E7ACFB" },
					{ name: 'Company_21', value: 5.85, color: "#E7ACFB" },
					{ name: 'Company_22', value: 6.89, color: "#E7ACFB" },
					{ name: 'Company_23', value: 5.89, color: "#E7ACFB" },
					{ name: 'Company_24', value: 8.04, color: "#E7ACFB" },
					{ name: 'Company_25', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_26', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_27', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_28', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_29', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_30', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_31', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_32', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_33', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_34', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_35', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_36', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_37', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_38', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_39', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_40', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_41', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_42', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_43', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_44', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_45', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_46', value: 2.04, color: "#E7ACFB" },
					{ name: 'Company_47', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_48', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_49', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_50', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_51', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_52', value: 1.04, color: "#E7ACFB" },
					{ name: 'Company_53', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_54', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_55', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_56', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_57', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_58', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_59', value: 0.14, color: "#E7ACFB" },
					{ name: 'Company_60', value: 0.14, color: "#E7ACFB" },

				];

				Highcharts.chart('RevenueCompanySubscription', {
					credits: { enabled: false },
					exporting: { enabled: false },
					series: [{
						type: 'treemap',
						layoutAlgorithm: 'squarified',
						data: sampleData,
					}],
					title: {
						text: null,
					},
					plotOptions: {
						treemap: {
							dataLabels: {
								formatter: function() {
									let p = this.point;
									if (p.value >= 5.5) {
										return `<b>${p.name}</b><br>${Highcharts.numberFormat(p.value, 2)}K`;
									}
									else {
										return null;
									}
								},
								align: 'left',
								verticalAlign: 'top',
								x: 10,
								y: 10,
								style: {
									color: 'black', // Text color
									textOutline: 'none' // Removes the text outline or border
								}
							}
						}
					}
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
		url: "dashboard-invoice-summary-dtls-tbl",
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
/*function to get onchange orgDiv*/
function getAccaccSubscOrgDivData() {
	subscription();
}

function resetSubscriptionDate() {
	var today = new Date();
	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();
	$("#fromDate8").val(fromDate);
	$("#toDate8").val(toDate);

	subscription();
}