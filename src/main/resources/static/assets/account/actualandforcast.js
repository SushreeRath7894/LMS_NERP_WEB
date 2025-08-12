function getActualAndForcasteWithDate() {
	actualForecast();
}


function actualForecast() {

	var fromDate = $("#fromDate4").val();
	var toDate = $("#toDate4").val();
	var org = $("#accOrgActVsForec").find('option:selected').text();
	var orgDiv = $("#accOrgDivActVsForec").find('option:selected').text();
	var loc = $("#actVsForecLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-netprofit",
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
				var allData = jsonData.Actualandforcast;

				// revenue 

				$("#revActual").text(allData[0].revenue[0].actaul);
				$("#revForcast").text(allData[0].revenue[0].forcast);
				$("#revAbso").text(allData[0].revenue[0].absolute);
				var perc = getPercentage(allData[0].revenue[0].absolute, allData[0].revenue[0].forcast);
				$("#revPrcnt").text(perc);

				// cogs

				$("#cogsActual").text(allData[0].cogs[0].actaul);
				$("#cogsForcast").text(allData[0].cogs[0].forcast);
				$("#cogsAbso").text(allData[0].cogs[0].absolute);
				var perc = getPercentage(allData[0].cogs[0].absolute, allData[0].revenue[0].forcast);
				$("#cogsPrcnt").text(perc);

				// cost

				$("#costActual").text(allData[0].costs[0].actaul);
				$("#costForcast").text(allData[0].costs[0].forcast);
				$("#costAbso").text(allData[0].costs[0].absolute);
				var perc = getPercentage(allData[0].costs[0].absolute, allData[0].costs[0].forcast);
				$("#costPrcnt").text(perc);

				// taxes

				$("#taxesActual").text(allData[0].taxes[0].actaul);
				$("#taxesForcast").text(allData[0].taxes[0].forcast);
				$("#taxesAbso").text(allData[0].taxes[0].absolute);
				var perc = getPercentage(allData[0].taxes[0].absolute, allData[0].taxes[0].forcast);
				$("#taxesPrcnt").text(perc);

				// net profit

				$("#netActual").text(allData[0].netprofit[0].actaul);
				$("#netForcast").text(allData[0].netprofit[0].forcast);
				$("#netAbso").text(allData[0].netprofit[0].absolute);
				var perc = getPercentage(allData[0].netprofit[0].absolute, allData[0].netprofit[0].forcast);
				$("#netPercnt").text(perc);






			}
		}, error: function(data) {
			console.log(data);
		}
	})






	//finance avf revenue
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-revenues",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('financeAvfRevenue', {
					chart: {
						zoomType: 'xy',
						animation: true,
						height: 250
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
						categories: ['Apr2021', 'May2021', 'Jun2021', 'Jul2021', 'Aug2021'],
						crosshair: true,
						labels: {
							rotation: -45,
							style: {
								fontSize: '12px',
							}
						}
					}],
					yAxis: [{
						labels: {
							format: '{value}',
							style: {
								color: '#000000'
							}
						},
						title: {
							text: '',
						}
					},
					],
					series: [{
						name: 'Actual',
						type: 'column',
						data: [27.6, 28.8, 21.7, 34.1, 43.2], color: '#BF05FF',

					},
					{
						name: 'Forecast',
						type: 'spline',
						data: [13.6, 14.9, 15.8, 15.7, 15.1], color: '#F79C92',
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	//finance avf cost
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-finance-costs",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}
				Highcharts.chart('financeAvfCost', {
					chart: {
						zoomType: 'xy',
						animation: true,
						height: 250
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
						categories: ['Apr2021', 'May2021', 'Jun2021', 'Jul2021', 'Aug2021'],
						crosshair: true,
						labels: {
							rotation: -45,
							style: {
								fontSize: '12px',
							}
						}
					}],
					yAxis: [{
						labels: {
							format: '{value}',
							style: {
								color: '#000000'
							}
						},
						title: {
							text: '',
						}
					},
					],
					series: [{
						name: 'Actual',
						type: 'column',
						data: [27.6, 28.8, 21.7, 34.1, 43.2], color: '#F79C92',

					},
					{
						name: 'Forecast',
						type: 'spline',
						data: [13.6, 14.9, 15.8, 15.7, 15.1], color: '#BF05FF',
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	//finance revenue gauge
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-revenue",
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
				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend

				Highcharts.chart('financeRevenueGauge', {
					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 110,
						marging: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: ''
						// text: '22%',
						// align: 'center',
						// verticalAlign: 'center',
						// floating: true,
						// y: 50,
						// margin: 0,
						// style: { "fontSize": '16', "color": '#453f7a' }

					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -98,
						endAngle: 99.9,
						background: null,
						center: ['50%', '75%'],
						size: '140%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50, lineWidth: 0,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 22,
							color: '#BF05FF',
							thickness: 25
						}, {
							from: 22,
							to: 70,
							color: '#d9d9d9',
							thickness: 25
						}]
					},

					series: [{
						name: '',
						data: [22],

						dataLabels: {
							borderWidth: 0,
							color: '#00f7ff',
							style: {
								fontSize: '16px'
							},
							enabled: false,
						},
						dial: {
							radius: '0%',
						},
						pivot: {
							radius: 0
						},

					}]

				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})







	/*Highcharts.chart('financeRevenueGauge', {
	chart: {
		type: 'gauge'
	},
	title: {
		text: 'Gauge Chart with Two Indicators'
	},
	pane: {
		startAngle: -150,
		endAngle: 150
	},
	yAxis: [{
		min: 0,
		max: 100,
		plotBands: [{
			from: 0,
			to: 60,
			color: '#55BF3B', // green
			innerRadius: '100%',
			outerRadius: '105%'
		}],
		title: {
			text: 'Indicator 1'
		},
		plotOptions: {
			solidgauge: {
				dataLabels: {
					y: 5,
					borderWidth: 0,
					useHTML: true
				}
			}
		}
	}, {
		min: 0,
		max: 100,
		plotBands: [{
			from: 0,
			to: 40,
			color: '#DDDF0D', // yellow
			innerRadius: '100%',
			outerRadius: '105%'
		}],
		title: {
			text: 'Indicator 2'
		},
		plotOptions: {
			solidgauge: {
				dataLabels: {
					y: 5,
					borderWidth: 0,
					useHTML: true
				}
			}
		}
	}],
	series: [{
		name: 'Indicator 1',
		data: [80],
		yAxis: 0
	}, {
		name: 'Indicator 2',
		data: [30],
		yAxis: 1
	}]
});
*/
	//finance cogs gauge
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-cogs",
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
				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('financeCOGSGauge', {
					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 110,
						marging: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: ''
						// text: '22%',
						// align: 'center',
						// verticalAlign: 'center',
						// floating: true,
						// y: 50,
						// margin: 0,
						// style: { "fontSize": '16', "color": '#453f7a' }

					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -98,
						endAngle: 99.9,
						background: null,
						center: ['50%', '75%'],
						size: '140%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50, lineWidth: 0,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 22,
							color: '#F79C92',
							thickness: 25
						}, {
							from: 22,
							to: 70,
							color: '#d9d9d9',
							thickness: 25
						}]
					},

					series: [{
						name: '',
						data: [22],

						dataLabels: {
							borderWidth: 0,
							color: '#00f7ff',
							style: {
								fontSize: '16px'
							},
							enabled: false,
						},
						dial: {
							radius: '0%',
						},
						pivot: {
							radius: 0
						},

					}]

				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	//finance costs gauge
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-costs",
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
				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('financeCostsGauge', {
					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 110,
						marging: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: ''
						// text: '22%',
						// align: 'center',
						// verticalAlign: 'center',
						// floating: true,
						// y: 50,
						// margin: 0,
						// style: { "fontSize": '16', "color": '#453f7a' }

					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -98,
						endAngle: 99.9,
						background: null,
						center: ['50%', '75%'],
						size: '140%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50, lineWidth: 0,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 22,
							color: '#B422B6',
							thickness: 25
						}, {
							from: 22,
							to: 70,
							color: '#d9d9d9',
							thickness: 25
						}]
					},

					series: [{
						name: '',
						data: [22],

						dataLabels: {
							borderWidth: 0,
							color: '#00f7ff',
							style: {
								fontSize: '16px'
							},
							enabled: false,
						},
						dial: {
							radius: '0%',
						},
						pivot: {
							radius: 0
						},

					}]

				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})

	//finance taxes gauge
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-taxes",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('financeTaxesGauge', {
					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 110,
						marging: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: ''
						// text: '22%',
						// align: 'center',
						// verticalAlign: 'center',
						// floating: true,
						// y: 50,
						// margin: 0,
						// style: { "fontSize": '16', "color": '#453f7a' }

					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -98,
						endAngle: 99.9,
						background: null,
						center: ['50%', '75%'],
						size: '140%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50, lineWidth: 0,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 22,
							color: '#56156C',
							thickness: 25
						}, {
							from: 22,
							to: 70,
							color: '#d9d9d9',
							thickness: 25
						}]
					},

					series: [{
						name: '',
						data: [22],

						dataLabels: {
							borderWidth: 0,
							color: '#00f7ff',
							style: {
								fontSize: '16px'
							},
							enabled: false,
						},
						dial: {
							radius: '0%',
						},
						pivot: {
							radius: 0
						},

					}]

				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})

	//finance avf breakdown
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-breakdown-costs",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('financeAvfBreakdown', {
					chart: {
						animated: true,
						height: 250
					},
					title: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: { enabled: false, },
					xAxis: {
						categories: ['Sales', 'Marketing', 'General & Admin', 'Other']
					},
					yAxis: {
						title: {
							text: ''
						}
					},

					series: [{
						type: 'bar',
						name: 'Actual',
						data: [59, 83, 65, 228],
						color: '#BF05FF',
					}, {
						name: 'Forecast',
						color: 'transparent',
						lineColor: 'transparent',
						data: [47, 83.33, 70.66, 239.33],
						type: 'spline',
						dataLabels: {
							enabled: false,
							// rotation: 0,
							// color: '#FFFFFF',
							// backgroundColor: '#005c9f',
							// align: 'right',
							// format: '{point.y:.1f}',
							// style: {
							//     fontSize: '10px',
							// }
						},
						marker: {
							lineWidth: 2,
							fillColor: '#F79C92'
						}
					},]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	//Income Budget
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-incomebudget",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('IncomeBudget', {
					chart: {
						type: 'pie',
						height: 250,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#ff5d5d', '#dfdfdf'],
					title: {
						text: '88%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 120,
						margin: 0,
						style: { "fontSize": '14', "color": '#000000' }
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
							shadow: false,
							colors: [
								'#ca7ce5', '#cccccc',

							],
							allowPointSelect: true,
						}
					},
					series: [{
						name: '',
						data: [["Income Budget", 88], ["Actual Income", 12]],
						size: '100%',
						innerSize: '80%',
						showInLegend: false,
						color: '#ca7ce5',
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	//Expenses Budget
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-expensesbudget",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart('ExpensesBudget', {
					chart: {
						type: 'pie',
						height: 250,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#ff5d5d', '#dfdfdf'],
					title: {
						text: '84%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 120,
						margin: 0,
						style: { "fontSize": '14', "color": '#000000' }
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
							shadow: false,
							colors: [
								'#F79C92', '#cccccc',

							],
							allowPointSelect: true,
						}
					},
					series: [{
						name: '',
						data: [["Contracted", 84], ["", 16]],
						size: '100%',
						innerSize: '80%',
						showInLegend: false,
						color: '#ca7ce5',
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	// Actual Income
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-actualincome",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart("ActualIncome", {
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

							],
							labels: { style: { fontSize: "10px" } },
						},
					],
					yAxis: [
						{
							// Primary yAxis
							title: {
								text: "Actual Income",
							},
							labels: {
								format: "{value}K",
								style: { fontSize: "9px" },
							},
						},
						{
							// Secondary yAxis
							title: {
								text: "Income Budget",
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
					legend: {
						itemStyle: { fontSize: "10px" },
					},
					series: [
						{
							name: "Actual Income",
							type: "column",
							data: [20, 40, 40, 50, 50, 50, 45, 42, 46, 42, 45, 50, 42, 40, 40, 20],
							color: "#BF05FF",
						},
						{
							name: "Income Budget",
							type: "line",
							data: [10, 38, 39, 51, 50, 50, 42, 40, 45, 40, 50, 38, 35, 36, 37, 10],
							color: "#F79C92",
							yAxis: 1,
							zIndex: 2
						},
					],
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})



	// Actual Expense
	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-actualexpenses",
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

				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						//alert("cashEquivalent cash trend------------"+cashEquivalent);
						//alert("liabilitiesAmount cash trend------------"+liabilitiesAmount);
						let cashRatio = safeDivide(cashEquivalent, liabilitiesAmount);
						//alert("cashRatio cash trend------------"+cashRatio);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("cashRatioDataSet--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet--------------------" + targetCashRatioDataSet);
				//cash ratio trend
				Highcharts.chart("ActualExpense", {
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

							],
							labels: { style: { fontSize: "10px" } },
						},
					],
					yAxis: [
						{
							// Primary yAxis
							title: {
								text: "Requests",
							},
							labels: {
								format: "{value}K",
								style: { fontSize: "9px" },
							},
						},
						{
							// Secondary yAxis
							title: {
								text: "% of Requests Answered",
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
					legend: {
						itemStyle: { fontSize: "10px" },
					},
					series: [
						{
							name: "New Customers",
							type: "column",
							data: [20, 40, 40, 50, 50, 50, 45, 42, 46, 42, 45, 50, 42, 40, 40, 20],
							color: "#F79C92",
						},
						{
							name: "Net Retention Rate",
							type: "line",
							data: [10, 38, 39, 51, 50, 50, 42, 40, 45, 40, 50, 38, 35, 36, 37, 10],
							color: "#BF05FF",
							yAxis: 1,
							zIndex: 2
						},
					],
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	function getPercentage(abs, forcast) {
		return parseFloat((abs / forcast) * 100).toFixed(2);

	}




	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-budget-actual-table-income",
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

				if (jsonData != null) {
					var tableBody = $('#budgetActualTableBody');
					tableBody.empty(); // Clear existing data

					jsonData.forEach(function(item) {
						var difference = item.budget - item.actual;
						var avgPercent = ((item.actual / item.budget) * 100).toFixed(2);

						var row = '<tr>' +
							'<td>' + item.category + '</td>' +
							'<td>' + item.budget + '</td>' +
							'<td>' + item.actual + '</td>' +
							'<td>' + difference + '</td>' +
							'<td><span class="avgbtn">' + avgPercent + '%</span></td>' +
							'</tr>';

						tableBody.append(row);
					});
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data: ", status, error);
		}
	});


	$.ajax({
		type: "GET",
		url: "dashboard-actual-forecast-budget-actual-table-expense",
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

				if (jsonData != null) {
					var tableBody = $('#budgetActualTableBody1');
					tableBody.empty(); // Clear existing data

					jsonData.forEach(function(item) {
						var difference = item.budget - item.actual;
						var avgPercent = ((item.actual / item.budget) * 100).toFixed(2);

						var row = '<tr>' +
							'<td>' + item.category + '</td>' +
							'<td>' + item.budget + '</td>' +
							'<td>' + item.actual + '</td>' +
							'<td>' + difference + '</td>' +
							'<td><span class="avgbtn">' + avgPercent + '%</span></td>' +
							'</tr>';

						tableBody.append(row);
					});
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data: ", status, error);
		}
	});

}

function getAccActVsForecDataOrgDiv() {
	actualForecast();
}

function resetActualAndForcasteDate() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate4").val(fromDate);
	$("#toDate4").val(toDate);
	actualForecast();
}