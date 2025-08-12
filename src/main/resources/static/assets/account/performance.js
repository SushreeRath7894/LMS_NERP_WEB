function getFinancialPerformanceWithDate() {
	performance();
}

function performance() {


	var fromDate = $("#fromDate5").val();
	var toDate = $("#toDate5").val();
	var org = $("#accOrgPerf").find('option:selected').text();
	var orgDiv = $("#accOrgDivPerf").find('option:selected').text();
	var loc = $("#prefLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-per-main-dso-dio-dpo-count",
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-per-return-on-assets",
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

				//finance perf roa
				Highcharts.chart('financePerfRoa', {
					chart: {
						type: 'column',
						height: 140,
						animation: true,
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
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sept 2024'],
						rotation: '45deg'
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false },
						gridLineColor: 'transparent'
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#BF05FF'],
						colorByPoint: true,
						groupPadding: 0,
						data: [18, 27, 19, 24, 16, 18],
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
		url: "dashboard-per-working-capital-ratio",
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

				//finance perf wcr
				Highcharts.chart('financePerfWcr', {
					chart: {
						height: 140,
						animation: true,
					},
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
					credits: {
						enabled: false
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sept 2024'],
						rotation: '45deg'
					},

					legend: {
						enabled: false,
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
						}
					},

					series: [{
						name: '',
						data: [18, 27, 19, 24, 16, 18],
						color: '#B422B6'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
						}]
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
		url: "dashboard-per-return-on-equity",
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


				//finance perf roe
				Highcharts.chart('financePerfRoe', {
					chart: {
						height: 130,
						animation: true,
					},
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
					credits: {
						enabled: false
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sept 2024'],
						rotation: '45deg'
					},

					legend: {
						enabled: false,
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
						}
					},

					series: [{
						name: '',
						data: [18, 27, 19, 24, 16, 18],
						color: '#F79C92'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
						}]
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
		url: "dashboard-per-debt-equity-ratio",
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

				//finance perf der
				Highcharts.chart('financePerfDer', {
					chart: {
						type: 'area',
						height: 130,
						animation: true
					},

					title: {
						text: ''
					},
					subtitle: {
						text: ''
					}, navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: {
						enabled: false
					},
					xAxis: {
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sept 2024'],
						rotation: '45deg'
					},
					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false },
						gridLineColor: 'transparent'
					},
					plotOptions: {
						area: {
							marker: {
								enabled: false,
								symbol: 'circle',
								radius: 2,
								states: {
									hover: {
										enabled: true
									}
								}
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: [18, 27, 19, 24, 16, 18],
						color: '#5d8caf'
					},]
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
		url: "dashboard-per-balancesheet-count-list",
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


				//finance perf bl 1
				Highcharts.chart('financePerfBL1', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#F79C92'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['JAN', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
						dataLabels: {
							enabled: false,
						}
					}]
				});
				//finance perf bl 2
				Highcharts.chart('financePerfBL2', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#BF05FF'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['Jan', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
						dataLabels: {
							enabled: false,
						}
					}]
				});

				//finance perf bl 6
				Highcharts.chart('financePerfBL6', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#6A27C0'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['Jan', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
						dataLabels: {
							enabled: false,
						}
					}]
				});
				//finance perf bl 7
				Highcharts.chart('financePerfBL7', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#E55C20'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['Jan', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
						dataLabels: {
							enabled: false,
						}
					}]
				});
				//finance perf bl 8
				Highcharts.chart('financePerfBL8', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#5d8caf'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['Jan', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
						dataLabels: {
							enabled: false,
						}
					}]
				});

				//finance perf bl 11
				Highcharts.chart('financePerfBL11', {
					chart: {
						type: 'column',
						height: 25,
						animation: true, margin: 0
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
						type: 'category',
						labels: { enabled: false },
						lineColor: 'transparent'
					},
					yAxis: {
						min: 0,
						gridLineColor: 'transparent',
						title: {
							text: ''
						},
						labels: {
							enabled: false
						}
					},
					legend: {
						enabled: false
					},
					series: [{
						name: '',
						colors: ['#CA7CE5'],
						colorByPoint: true,
						groupPadding: 0,
						pointWidth: 15,
						data: [
							['Jan', 15],
							['Feb', 20],
							['Mar', 25],
							['Apr', 30],
							['May', 35],
							['Jun', 30],
							['Jul', 35],
						],
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









	// Days Sales Outstanding
	Highcharts.chart("DaysOutstanding", {
		chart: {
			type: "gauge",
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 200,
		},

		title: {
			text: "7 Days",
			align: "center",
			verticalAlign: "bottom",
			floating: true,
			y: 18,
			margin: 0,
			style: { fontSize: "13", color: "#61426c" },
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
			size: "150%",
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
				enabled: true,
			},
			lineWidth: 0,
			plotBands: [
				{
					from: 0,
					to: 33,
					thickness: 15,
					color: "#bf05ff",
				},
				{
					from: 33,
					to: 66,
					thickness: 15,
					color: "#9792e8",
				},
				{
					from: 66,
					to: 100,
					thickness: 15,
					color: "#f58d68",
				},
			],
		},

		series: [
			{
				name: "",
				data: [7],
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





	// Days Inventory Outstanding
	Highcharts.chart("InventoryOutstanding", {
		chart: {
			type: "gauge",
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 200,
		},

		title: {
			text: "28 Days",
			align: "center",
			verticalAlign: "bottom",
			floating: true,
			y: 18,
			margin: 0,
			style: { fontSize: "13", color: "#61426c" },
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
			size: "150%",
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
				enabled: true,
			},
			lineWidth: 0,
			plotBands: [
				{
					from: 0,
					to: 33,
					thickness: 15,
					color: "#bf05ff",
				},
				{
					from: 33,
					to: 66,
					thickness: 15,
					color: "#9792e8",
				},
				{
					from: 66,
					to: 100,
					thickness: 15,
					color: "#f58d68",
				},
			],
		},

		series: [
			{
				name: "",
				data: [28],
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

	// Days Payable Outstanding
	Highcharts.chart("PayableOutstanding", {
		chart: {
			type: "gauge",
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 200,
		},

		title: {
			text: "11 Days",
			align: "center",
			verticalAlign: "bottom",
			floating: true,
			y: 18,
			margin: 0,
			style: { fontSize: "13", color: "#61426c" },
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
			size: "150%",
		},

		// the value axis
		yAxis: {
			min: 0,
			max: 100,
			tickPosition: "inside",
			tickColor: "transparent",
			tickLength: 0,
			tickWidth: 0,
			minorTickInterval: null,
			labels: {
				enabled: true,
			},
			lineWidth: 0,
			plotBands: [
				{
					from: 0,
					to: 33,
					thickness: 15,
					color: "#bf05ff",
				},
				{
					from: 33,
					to: 66,
					thickness: 15,
					color: "#9792e8",
				},
				{
					from: 66,
					to: 100,
					thickness: 15,
					color: "#f58d68",
				},
			],
		},

		series: [
			{
				name: "",
				data: [11],
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




	// Days Sales Outstanding
	Highcharts.chart("DaysOutstanding", {
		chart: {
			type: "gauge",
			plotBackgroundColor: null,
			plotBackgroundImage: null,
			plotBorderWidth: 0,
			plotShadow: false,
			height: 200,
		},

		title: {
			text: "7 Days",
			align: "center",
			verticalAlign: "bottom",
			floating: true,
			y: 18,
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
			size: "150%",
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
				enabled: true,
			},
			lineWidth: 0,
			plotBands: [
				{
					from: 0,
					to: 33,
					thickness: 15,
					color: "#bf05ff",
				},
				{
					from: 33,
					to: 66,
					thickness: 15,
					color: "#9792e8",
				},
				{
					from: 66,
					to: 100,
					thickness: 15,
					color: "#f58d68",
				},
			],
		},

		series: [
			{
				name: "",
				data: [7],
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


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-per-profit-loss-summry",
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

				// Profit Loss Summary
				Highcharts.chart("ProfitLossSummary", {
					chart: {
						zoomType: "xy",
						animation: true,
						height: 250,
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					title: {
						text: "",
					},
					subtitle: {
						text: "",
					},
					credits: { enabled: false },
					xAxis: [
						{
							categories: [
								"Q1 2022",
								"Q2 2022"

							],
							labels: { style: { fontSize: "10px" } },
						},
					],
					yAxis: [
						{
							// Primary yAxis
							title: {
								text: "",
							},
							labels: {
								format: "{value}K",
								style: { fontSize: "9px" },
							},
						},
						{
							// Secondary yAxis
							title: {
								text: "",
							},
							labels: {
								format: "{value}K",
								style: { fontSize: "9px" },
							},
							opposite: true,
						},
					],
					tooltip: {
						shared: true,
					},
					plotOptions: {
						column: {
							stacking: "normal",
							reversedStacks: false,
						},
					},
					legend: {
						itemStyle: { fontSize: "10px" },
					},
					series: [
						{
							name: "Sales",
							type: "column",
							data: [80, 160],
							color: "#f58d68",
						},
						{
							name: "Profit Loss Result",
							type: "column",
							data: [60, 0],
							color: "#deaaf0",
						},

						{
							name: "Result",
							type: "column",
							data: [20, 0],
							color: "#BF05FF",
						}

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
		url: "dashboard-per-net-gross-working-capital",
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


				//Net Vs Gross Working Capital		
				Highcharts.chart('NetVsGrossWorkingCapital', {
					chart: {
						type: 'line',
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
						name: 'Debt to Asset Ratio',
						marker: {
							symbol: 'Net Working Capital'
						},
						data: [15.87, 278.78, 193.45, 110.18, 78.77, 136.32],
						color: '#BF05FF',
						dataLabels: {
							enabled: true,
						}

					}, {
						name: 'Gross Working Capital',
						marker: {
							symbol: 'circle'
						},
						data: [14, 214.30, 137.39, 53.51, 6.62, 64.57],
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
		url: "dashboard-per-acc-rec-acc-pay-turnover",
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


				//AR Turnover Vs AP Turnover

				Highcharts.chart('ARTurnoverVsAPTurnover', {
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
							'Oct 2023',
							'Nov 2023',
							'Dec 2023',
							'Jan 2024',
							'Feb 2024',
							'Mar 2024'
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
							pointWidth: 16,
							borderWidth: 1
						}
					},
					series: [{
						name: 'Account Receivable Turnover',
						data: [11, 25, 35, 18, 25, 13],
						color: '#bf05ff',
						dataLabels: {
							enabled: true,
						}
					}, {
						name: 'Account Payable Turnover',
						data: [7, 15, 16, 12, 14, 12],
						color: '#F79C92',
						dataLabels: {
							enabled: true,
						}
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
		url: "dashboard-per-inventory-dtls",
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


				//Inventory Finance

				Highcharts.chart('InventoryFinance', {
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
						data: [1.91, 6.97, 3.79, 3.67, 2.02, 5.45],
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
	$.ajax({
		type: "GET",
		url: "dashboard-per-acc-paybl-by-pay-target",
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


				//Accounts Payable by Payment

				Highcharts.chart('AccountsPayablebyPayment', {
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
						categories: ['Current', '91+', '61-90', '31-60', '1-20'],
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
						data: [420, 100, 60, 250, 440],
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
		url: "dashboard-per-expenses-breakdown-list-count",
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


				// Expenses Breakdown

				Highcharts.chart('ExpensesBreakdown', {
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
						categories: ['Entertainment', 'Bank Fees', 'Advertising', 'Cleaning', 'Freight & Courier', 'Rent', 'Light,Power,Heating', 'Consulting & Accounting', 'Telephone & Internet', 'Motor Vehicle Expenses', 'Printing & Stationery', 'General Expenses', 'Repairs & Maintainance', 'Travel-National', 'Office Expenses'],
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
								enabled: false
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
						data: [4, 2.9, 2.4, 2.2, 1.5, 1, 1, 1, 0.9, 0.8, 0.8, 0.7, 0.3, 0.2, 0.1],
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
	$.ajax({
		type: "GET",
		url: "dashboard-per-invoice-due-dtls-by-cust-tbl",
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

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-per-profit-loss-summary-tbl",
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
		url: "dashboard-per-executive-summary-tbl",
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






	// Function to calculate the greatest common divisor (GCD)
	function gcd(a, b) {
		return b === 0 ? a : gcd(b, a % b);
	}

	// Function to simplify the ratio
	function simplifyRatio(numerator, denominator) {
		const divisor = gcd(numerator, denominator);
		return {
			numerator: numerator / divisor,
			denominator: denominator / divisor
		};
	}

	// Function to convert a decimal value to a ratio
	function decimalToRatio(decimal) {
		// const precision = 1e9; // adjust as needed for your specific use case
		const precision = 1; // adjust as needed for your specific use case
		const denominator = precision;
		const numerator = Math.round(decimal * precision);
		const simplified = simplifyRatio(numerator, denominator);
		return simplified.numerator + ':' + simplified.denominator;
	}


}

function resetPerformanceDate() {
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
	performance();
}

function getAccaccOrgPerfOrgDivData() {
	performance();
}