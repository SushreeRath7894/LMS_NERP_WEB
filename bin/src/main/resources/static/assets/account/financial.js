function financialKPI() {

	$("#tabKeyId").val(1);

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();


	$.ajax({
		type: "GET",
		url: "dashboard-current-working-capital",
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
				var netProfitMargin = 0;

				if (allData[0].profitmargin === null || allData[0].profitmargin === undefined) {
					netProfitMargin = 0.00;
				} else {
					netProfitMargin = allData[0].profitmargin;
				}

				$("#profitMargin").text(parseFloat(netProfitMargin).toFixed(2));
				var totalProfitMargin = parseInt(netProfitMargin);

				//	*********************** current working assests ***************************/
				$("#prepaid").text(parseFloat(allData[0].prepaid).toFixed(2));
				$("#inventory").text(parseFloat(allData[0].inventory).toFixed(2));
				$("#receivable").text(parseFloat(allData[0].receivableamt).toFixed(2));
				$("#cashamnt").text(parseFloat(allData[0].cashamount).toFixed(2));
				var totalWorkingCapital = parseFloat(allData[0].prepaid + allData[0].inventory + allData[0].receivableamt + allData[0].cashamount).toFixed(2)
				$("#totalWorkAmount").text(totalWorkingCapital);

				//	*********************** current libilities ***************************/
				$("#payableAccount").text(parseFloat(allData[0].accountpayable).toFixed(2));
				$("#creditcarddebt").text(parseFloat(allData[0].creditcarddebit).toFixed(2));
				$("#bankoperating").text(parseFloat(allData[0].bankoperatingcredit).toFixed(2));
				$("#accruedExp").text(parseFloat(allData[0].accruedexpenses).toFixed(2));
				$("#taxesPayAmnt").text(parseFloat(allData[0].taxespayable).toFixed(2));
				var totalCurrentLibilities = parseFloat(allData[0].accountpayable + allData[0].creditcarddebit + allData[0].bankoperatingcredit +
					allData[0].accruedexpenses + allData[0].taxespayable).toFixed(2);
				$("#totalCurrentLibilities").text(totalCurrentLibilities);


				var workingCapital = totalWorkingCapital - totalCurrentLibilities;
				$("#workingCapital").text(parseFloat(workingCapital).toFixed(2));

				/*	formula for	currentRatio=currentAssets/currentLibilities	*/

				var currentRatio = parseFloat(totalWorkingCapital / totalCurrentLibilities).toFixed(2);
				if (currentRatio === null || currentRatio === undefined || currentRatio === "Infinity") {
					currentRatio = 0;
				}
				var currentRatio1 = parseInt(currentRatio);
				$("#currentRatio").text(currentRatio);
				$("#currentRatio1").text(currentRatio);


				/*	formula for	quickRatio=(totalAssests-inventory)/totalLibilities	*/

				var inventory = allData[0].inventory;
				var quickRatio = (totalWorkingCapital - inventory) / totalCurrentLibilities;
				if (quickRatio === null || quickRatio === undefined || quickRatio === Infinity) {
					quickRatio = 0;
				}
				$("#quickRatio").text(parseFloat(quickRatio).toFixed(2));
				var quickRatio1 = parseInt(quickRatio);

				var dso = 0.00;

				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = allData[0].dso;
				}

				$("#dso").text(parseFloat(dso).toFixed(2));


				var dio = 0.00;

				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = allData[0].dio;
				}
				$("#dio").text(parseFloat(dio).toFixed(2));

				var dpo = 0.00;

				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = allData[0].dpo;
				}
				$("#dpo").text(parseFloat(dpo).toFixed(2));

				var ccc = dso + dio - dpo;
				$("#ccc").text(parseFloat(ccc).toFixed(2));

				//cash coversion in cycle ends



				//current quick stats - Quick Ratio
				Highcharts.chart('cqsQuickRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Quick Ratio',
						style: { fontSize: '11px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#bf05ff',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#9792e8',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#f58d68',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [quickRatio1],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - quick ratio - spline
				Highcharts.chart('cqsQuickRatioSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [100, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 200],
						lineWidth: 2,
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




				//current quick stats - Curent Ratio
				Highcharts.chart('cqsCurrentRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Current Ratio',
						style: { fontSize: '10px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#bf05ff',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#9792e8',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#f58d68',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [currentRatio1],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - Current ratio - spline
				Highcharts.chart('cqsCurrentRatioSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0
						
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [100, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 200],
						lineWidth: 2,
						color: '#BF05FF'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
						}]
					}

				});





				//current quick stats - Net Profit Margin
				Highcharts.chart('cqsNetProfitMarginRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Net Profit Margin',
						style: { fontSize: '11px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#bf05ff',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#9792e8',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#f58d68',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [totalProfitMargin],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - Net Profit Margin - spline
				Highcharts.chart('cqsNetProfitMarginSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0,
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [800, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 800],
						lineWidth: 2
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


	//cost conversion cycle in days
	Highcharts.chart('costConversionCycle', {
		chart: {
			height: 199
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
		credits: false,
		xAxis: [{
			categories: ['2020', '2021', '2022', '2023'],
			crosshair: true
		}],
		yAxis: [{ // Primary yAxis
			labels: {
				format: '{value}',
			},
			min: 0,
			title: {
				text: 'DSO | DIO | DPO ',
			}
		}, { // Secondary yAxis
			title: {
				text: 'CCC',
			},
			min: -100,
			labels: {
				format: '{value}',
			},
			opposite: true
		}],
		tooltip: {
			shared: true
		},
		plotOptions: {
			column: {
				stacking: 'normal'
			},
			dataLabels: {
				enabled: true
			}
		},
		legend: {
			enabled: false,
		},
		series: [{
			name: 'DSO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#CA7CE5'

		}, {
			name: 'DIO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#F79C92'

		}, {
			name: 'DPO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#BF05FF'

		}, {
			name: 'CCC',
			type: 'spline',
			data: [46.0, 36.9, 49.5, 58.5],
		}]
	});





	$.ajax({
		type: "GET",
		url: "dashboard-current-budget-varience",
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
				var allData = jsonData.varienceList;

				$("#varienceList").empty();
				//
				var totalActual = 0;  // Initialize totalActual
				var totalBudget = 0;  // Initialize totalBudget
				var totalVarience = 0;  // Initialize totalVarience

				for (var i = 0; i < allData.length; i++) {
					totalActual += parseFloat(allData[i].actual);  // Add actual to totalActual
					totalBudget += parseFloat(allData[i].budget);  // Add budget to totalBudget
					totalVarience += parseFloat(allData[i].varience);
					//alert("totalActual-----------"+allData[i].actual)


					var abc = '<tr>'
						+ '<td>' + allData[i].projects + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].actual).toFixed(2) + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].budget).toFixed(2) + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].varience).toFixed(2) + '</td>'
						+ '</tr>';

					$("#varienceList").append(abc);
				}
				$("#totalActual").text(totalActual.toFixed(2));
				$("#totalBudget").text(totalBudget.toFixed(2));
				$("#totalVarience").text(totalVarience.toFixed(2));

			}
		}, error: function(data) {
			console.log(data);
		}
	})



	$.ajax({
		type: "GET",
		url: "dashboard-vendor-payment-errorrate",
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
				var allData = jsonData.vendorPayList;

				var catogaries = [];
				var avg = [];
				var errorRate = [];
				for (var i = 0; i < allData.length; i++) {
					catogaries.push(allData[i].month)
					avg.push(parseInt(allData[i].average))
					errorRate.push(parseInt(allData[i].errorrate));
				}



				//vendor payment error rate
				Highcharts.chart('vendorPaymentErrorRate', {
					chart: {
						animation: true,
						height: 207
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
					credits: false,
					yAxis: {
						title: {
							text: ''
						}
					},

					xAxis: {
						categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023',
							'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023',
							'Oct 2023', 'Nov 2023', 'Dec 2023']
					},

					// legend: {
					//     enabled: false,
					// },

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
						}
					},

					series: [{
						type: 'spline',
						name: 'Vendor Payment Error Rate',
						data: [4916, 7941, 9742, 19851, 22490, 10282,
							8121, 6885, 13726, 24243, 31050, 26735],
						marker: false,
						lineWidth: 2,
						color: '#BF05FF'
					}, {
						type: 'line',
						name: 'Average Vendor Pay',
						data: [11744, 11744, 11744, 11744, 11744, 11744,
							11744, 11744, 11744, 11744, 11744, 11744],
						marker: false,
						lineWidth: 3,
						lineColor: '#F58D68',
						color: '#F58D68'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
							chartOptions: {
								legend: {
									layout: 'horizontal',
									align: 'center',
									verticalAlign: 'bottom'
								}
							}
						}]
					}

				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})

}
// Financial KPI Chart End //	


function safeDivide(numerator, denominator) {
	if (denominator === 0) {
		return 0;
	}
	return numerator / denominator;
}

// Usage example


function getLiquidityRatiosData() {

	$("#tabKeyId").val(2);
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-financial-kpi-liquidity-ratios",
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
				var netProfitMargin = 0;

				if (allData[0].profitmargin === null || allData[0].profitmargin === undefined) {
					netProfitMargin = 0.00;
				} else {
					netProfitMargin = allData[0].profitmargin;
				}

				//$("#profitMargin").text(parseFloat(netProfitMargin).toFixed(2));
				var totalProfitMargin = parseInt(netProfitMargin);

				//	*********************** current working assests ***************************/
				var totalWorkingCapital = parseFloat(allData[0].prepaid +
					allData[0].inventory +
					allData[0].receivableamt +
					allData[0].cashamount).toFixed(2)

				var totalCurrentLibilities = parseFloat(allData[0].accountpayable +
					allData[0].creditcarddebit +
					allData[0].bankoperatingcredit +
					allData[0].accruedexpenses +
					allData[0].taxespayable).toFixed(2);
				var workingCapital = totalWorkingCapital - totalCurrentLibilities;

				//-------------------------------------------------------------
				var marketableSecurities = allData[0].markatableSecurities;
				var cashEquivalent = (parseFloat(allData[0].cashamount) + parseFloat(allData[0].receivableamt));
				var shortTimeLiabilities = parseFloat(allData[0].accountpayable);
				let cashRatio = safeDivide(cashEquivalent, shortTimeLiabilities);
				let absoluteLiquidityRatio = safeDivide(parseFloat(cashEquivalent + marketableSecurities), shortTimeLiabilities);
				$("#liquidityCashRatio").text(parseFloat(cashRatio).toFixed(2));
				$("#absoluteLiquidityRatio").text(parseFloat(absoluteLiquidityRatio).toFixed(2));


				//-------------------------------------------------------------


				/*	formula for	currentRatio=currentAssets/currentLibilities	*/

				var currentRatio = parseFloat(totalWorkingCapital / totalCurrentLibilities).toFixed(2);
				if (currentRatio === null || currentRatio === undefined || currentRatio === "Infinity") {
					currentRatio = 0;
				}
				var currentRatio1 = parseInt(currentRatio);;
				$("#liquidityCurrentRatio").text(currentRatio);


				/*	formula for	quickRatio=(totalAssests-inventory)/totalLibilities	*/

				var inventory = allData[0].inventory;
				var quickRatio = (totalWorkingCapital - inventory) / totalCurrentLibilities;
				if (quickRatio === null || quickRatio === undefined || quickRatio === Infinity) {
					quickRatio = 0;
				}
				$("#liquidityQuickRatio").text(parseFloat(quickRatio).toFixed(2));

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-cash-ratio-trend",
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

				Highcharts.chart('cashrationtrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: cashRatioDataSet,
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetCashRatioDataSet,
						color: '#F79C92'
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-quick-ratio-trend",
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
				var quickRatioDataSet = [];
				var totalQuickRatio = 0.00;
				var averageQuickRatio = 0.00;
				var targetQuickRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var prepaidAmount = parseFloat(cashRatioData.prepaidAmount);
						var shortTermInvestment = parseFloat(cashRatioData.shortTermInvestment);
						var receivableAmount = parseFloat(cashRatioData.receivableAmount);
						var assetsForQuick = cashEquivalent + prepaidAmount + shortTermInvestment + receivableAmount;

						var payableAmount = parseFloat(cashRatioData.payableAmount);
						var creditcarddebit = parseFloat(cashRatioData.creditcarddebit);
						var bankoperatingcredit = parseFloat(cashRatioData.bankoperatingcredit);
						var AdvancePayment = parseFloat(cashRatioData.AdvancePayment);
						var taxesPayable = parseFloat(cashRatioData.taxesPayable);
						var currentLiabilities = payableAmount + creditcarddebit + bankoperatingcredit + AdvancePayment + taxesPayable;

						//var quickRatio = parseFloat(assetsForQuick/currentLiabilities).toFixed(2);
						let quickRatio = safeDivide(assetsForQuick, currentLiabilities);
						quickRatioDataSet.push(parseFloat(quickRatio));
						totalQuickRatio = (parseFloat(totalQuickRatio) + parseFloat(quickRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageQuickRatio = totalQuickRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetQuickRatioDataSet.push(parseFloat(averageQuickRatio));
					}
				}

				console.log("quickRatioDataSet--------------------" + quickRatioDataSet);
				console.log("targetQuickRatioDataSet--------------" + targetQuickRatioDataSet);


				// Quick Ration Trend  

				Highcharts.chart('quickrationtrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: quickRatioDataSet,
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetQuickRatioDataSet,
						color: '#F79C92'
					}]
				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-current-ratio-trend",
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
				var currentRatioDataSet = [];
				var totalCurrentRatio = 0.00;
				var averageCurrentRatio = 0.00;
				var targetCurrentRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDatas.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var prepaidAmount = parseFloat(cashRatioData.prepaidAmount);
						var inventoryBoughtAmount = parseFloat(cashRatioData.inventoryBoughtAmount);
						var receivableAmount = parseFloat(cashRatioData.receivableAmount);
						var currentAssets = cashEquivalent + prepaidAmount + inventoryBoughtAmount + receivableAmount;

						var payableAmount = parseFloat(cashRatioData.payableAmount);
						var creditcarddebit = parseFloat(cashRatioData.creditcarddebit);
						var bankoperatingcredit = parseFloat(cashRatioData.bankoperatingcredit);
						var AdvancePayment = parseFloat(cashRatioData.AdvancePayment);
						var taxesPayable = parseFloat(cashRatioData.taxesPayable);
						var currentLiabilities = payableAmount + creditcarddebit + bankoperatingcredit + AdvancePayment + taxesPayable;
						let currentRatio = safeDivide(currentAssets, currentLiabilities);
						currentRatioDataSet.push(parseFloat(currentRatio));
						totalCurrentRatio = (parseFloat(totalCurrentRatio) + parseFloat(currentRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCurrentRatio = totalCurrentRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCurrentRatioDataSet.push(parseFloat(averageCurrentRatio));
					}
				}

				console.log("currentRatioDataSet--------------------" + currentRatioDataSet);
				console.log("targetCurrentRatioDataSet--------------------" + targetCurrentRatioDataSet);

				// Current Ratio Trend  

				Highcharts.chart('currentratiotrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: currentRatioDataSet,
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetCurrentRatioDataSet,
						color: '#F79C92'
					}]
				});





			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-absolute-liquid-ratio-trend",
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

				var monthDataSet = [];
				var cashRatioDataSet = [];
				var totalCashRatio = 0.00;
				var averageCashRatio = 0.00;
				var targetCashRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashRatioData = jsonData[i];
						monthDataSet.push(cashRatioData.monthYear);
						var cashEquivalent = parseFloat(cashRatioData.cashEndOfMonth);
						var liabilitiesAmount = parseFloat(cashRatioData.payableAmount);
						var marketableSecurities = parseFloat(cashRatioData.marketableSecurities);
						//var cashRatio = parseFloat((cashEquivalent+marketableSecurities)/liabilitiesAmount).toFixed(2);
						let cashRatio = safeDivide(parseFloat(cashEquivalent + marketableSecurities), liabilitiesAmount);
						cashRatioDataSet.push(parseFloat(cashRatio));
						totalCashRatio = (parseFloat(totalCashRatio) + parseFloat(cashRatio));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCashRatio = totalCashRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCashRatioDataSet.push(parseFloat(averageCashRatio));
					}
				}

				console.log("monthDatas absolute--------------------" + monthDataSet);
				console.log("cashRatioDataSet absolute--------------------" + cashRatioDataSet);
				console.log("targetCashRatioDataSet absolute--------------------" + targetCashRatioDataSet);
				//cash ratio trend



				// Absolutely Liquidity Ratio Trend  

				Highcharts.chart('absolutelyrationtrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDataSet,

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: cashRatioDataSet,
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetCashRatioDataSet,
						color: '#F79C92'
					}]
				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})
}

function getWorkingRatiosData() {
	$("#tabKeyId").val(3);

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "dashboard-working-ratios-count",
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
				var allData = jsonData.AllData;

				var netCreditSales = parseFloat(allData[0].receivableamt);
				var averageReceivableAmnt = parseFloat(allData[0].averageReceivableAmnt);

				let receivableTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);
				let debatorsTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);

				$("#receivableTurnOver").text(parseFloat(receivableTurnOver).toFixed(2));
				$("#debatorsTurnOver").text(parseFloat(debatorsTurnOver).toFixed(2));


				var netCreditPurchase = parseFloat(allData[0].accountpayable);
				var averagePayableAmnt = parseFloat(allData[0].averageAccountPayable);

				let payableTurnOver = safeDivide(netCreditPurchase, averagePayableAmnt)
				$("#creditorsTurnOver").text(parseFloat(payableTurnOver).toFixed(2));
				var dso = 0.00;

				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = allData[0].dso;
				}


				var dio = 0.00;
				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = allData[0].dio;
				}

				var dpo = 0.00;

				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = allData[0].dpo;
				}

				var ccc = dso + dio - dpo;
				$("#cccConversionCycle").text(parseFloat(ccc).toFixed(2));




			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-working-receivable-trend",
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
				var receivableTurnOverDataSet = [];
				var totalReceivableTurnOverRatio = 0.00;
				var averageReceivableTurnOver = 0.00;
				var targetReceivableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var receivableSet = jsonData[i];
						monthDatas.push(receivableSet.monthYear);
						var netCreditSales = parseFloat(receivableSet.receivableAmount);
						var averageReceivableAmnt = parseFloat(receivableSet.averageReceivableAmount);
						let receivableTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);

						receivableTurnOverDataSet.push(parseFloat(receivableTurnOver));
						totalReceivableTurnOverRatio = (parseFloat(totalReceivableTurnOverRatio) + parseFloat(receivableTurnOver));


					}
					averageReceivableTurnOver = totalReceivableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetReceivableTurnOverDataSet.push(parseFloat(averageReceivableTurnOver));
					}
				}

				console.log("receivableTurnOverDataSet--------------------" + receivableTurnOverDataSet);
				console.log("targetReceivableTurnOverDataSet---------------" + targetReceivableTurnOverDataSet);
				//cash ratio trend



				// Receivable Turnover  

				Highcharts.chart('receivableturnover', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,//['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: receivableTurnOverDataSet,//[0.91, 0.80, 1.10, 1.02, 0.88, 0.95],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetReceivableTurnOverDataSet,//[0.91, 0.91, 0.91, 0.91, 0.91, 0.91],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})
	/////////////////////////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-working-cash-conversion-trend",
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
				var cccRatioDataSet = [];
				var totalCCCRatio = 0.00;
				var averageCCCRatio = 0.00;
				var targetCCCRatioDataSet = [];
				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cccData = jsonData[i];
						monthDatas.push(cccData.monthYear);
						var cccData = parseFloat(cccData.ccc);
						cccRatioDataSet.push(parseFloat(cccData));
						totalCCCRatio = (parseFloat(totalCCCRatio) + parseFloat(cccData));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageCCCRatio = totalCCCRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetCCCRatioDataSet.push(parseFloat(averageCCCRatio));
					}
				}

				console.log("cccRatioDataSet--------------------" + cccRatioDataSet);
				console.log("targetCCCRatioDataSet--------------------" + targetCCCRatioDataSet);
				//cash ratio trend


				// Cash Conversion

				Highcharts.chart('cashconversion', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,//['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: cccRatioDataSet,//[41, 55, 57, 42, 36, 32],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetCCCRatioDataSet,//[45, 45, 45, 45, 45, 45],
						color: '#F79C92'
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-working-debators-trend",
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
				var receivableTurnOverDataSet = [];
				var totalReceivableTurnOverRatio = 0.00;
				var averageReceivableTurnOver = 0.00;
				var targetReceivableTurnOverDataSet = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var receivableSet = jsonData[i];
						monthDatas.push(receivableSet.monthYear);
						var netCreditSales = parseFloat(receivableSet.receivableAmount);
						var averageReceivableAmnt = parseFloat(receivableSet.averageReceivableAmount);
						let receivableTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);

						receivableTurnOverDataSet.push(parseFloat(receivableTurnOver));
						totalReceivableTurnOverRatio = (parseFloat(totalReceivableTurnOverRatio) + parseFloat(receivableTurnOver));


					}
					///targetCashRatio can be done after budget till that date i will do ir as average of cash ratio
					averageReceivableTurnOver = totalReceivableTurnOverRatio / (jsonData.length);
					for (var i = 0; i < jsonData.length; i++) {
						targetReceivableTurnOverDataSet.push(parseFloat(averageReceivableTurnOver));
					}
				}
				//cash ratio trend


				// Debators Turnover

				Highcharts.chart('debatorsturnovertrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,//['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: receivableTurnOverDataSet,//[0.90, 0.94, 0.97, 0.90, 0.82, 0.83],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetReceivableTurnOverDataSet,//[0.85, 0.85, 0.85, 0.85, 0.85, 0.85],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-working-creditors-trend",
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


				// Creditors Turnover

				Highcharts.chart('creditorsturnovertrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: monthDatas,//['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: payableTurnOverDataSet,//[3.1, 2.9, 2.8, 3.4, 3.8, 3.6],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: targetPayableTurnOverDataSet,//[3.5, 3.5, 3.5, 3.5, 3.5, 3.5],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})

}

/////////////////////////////////////////////////////

function getRiskRatiosData() {
	$("#tabKeyId").val(4);

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();


	$.ajax({
		type: "GET",
		url: "dashboard-risk-ratios-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code == "success1") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.AllData;

				var netCreditSales = parseFloat(allData[0].receivableamt);
				var averageReceivableAmnt = parseFloat(allData[0].averageReceivableAmnt);

				let receivableTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);
				let debatorsTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);

				//$("#receivableTurnOver").text(parseFloat(receivableTurnOver).toFixed(2));
				//$("#debatorsTurnOver").text(parseFloat(debatorsTurnOver).toFixed(2));


				var netCreditPurchase = parseFloat(allData[0].accountpayable);
				var averagePayableAmnt = parseFloat(allData[0].averageAccountPayable);

				let payableTurnOver = safeDivide(netCreditPurchase, averagePayableAmnt)
				//$("#creditorsTurnOver").text(parseFloat(payableTurnOver).toFixed(2));
				var dso = 0.00;

				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = allData[0].dso;
				}


				var dio = 0.00;
				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = allData[0].dio;
				}

				var dpo = 0.00;

				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = allData[0].dpo;
				}

				var ccc = dso + dio - dpo;
				//$("#cccConversionCycle").text(parseFloat(ccc).toFixed(2));


				/*operatingLeverageValue
				financialLeverageValue
				debtToEquityRatioValue
				interestCoverRatioValue*/

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	///////////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		type: "GET",
		url: "dashboard-operating-leverage-trend",
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

				// Operating Leverage 

				Highcharts.chart('operatingleveragetrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [0.91, 0.80, 1.10, 1.02, 0.88, 0.95],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [0.91, 0.91, 0.91, 0.91, 0.91, 0.91],
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
		url: "dashboard-financial-leverage-trend",
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

				// Financial Leverage Trend

				Highcharts.chart('financialleveragetrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [0.90, 0.94, 0.97, 0.90, 0.82, 0.83],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [0.85, 0.85, 0.85, 0.85, 0.85, 0.85],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-dept-to-equity-trend",
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




				// Debt to Equity Ratio

				Highcharts.chart('debtequityratio', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [0.60, 0.60, 0.54, 0.54, 0.52, 0.47],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [0.50, 0.50, 0.50, 0.50, 0.50, 0.50],
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
		url: "dashboard-interest-coverage-ratio-trend",
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




				// Interest Cover Ratio

				Highcharts.chart('interestcoverratio', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [3, 4, 4, 4, 4, 5],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [6, 6, 6, 6, 6, 6],
						color: '#F79C92'
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})

}

///////////////////////////////////////////////////


function getProfitabilityRatiosData() {
	$("#tabKeyId").val(5);

	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-profitability-ratio-count",
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
				var allData = jsonData.AllData;

				var netCreditSales = parseFloat(allData[0].receivableamt);
				var averageReceivableAmnt = parseFloat(allData[0].averageReceivableAmnt);

				let receivableTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);
				let debatorsTurnOver = safeDivide(netCreditSales, averageReceivableAmnt);

				//$("#receivableTurnOver").text(parseFloat(receivableTurnOver).toFixed(2));
				//$("#debatorsTurnOver").text(parseFloat(debatorsTurnOver).toFixed(2));


				var netCreditPurchase = parseFloat(allData[0].accountpayable);
				var averagePayableAmnt = parseFloat(allData[0].averageAccountPayable);

				let payableTurnOver = safeDivide(netCreditPurchase, averagePayableAmnt)
				//$("#creditorsTurnOver").text(parseFloat(payableTurnOver).toFixed(2));
				var dso = 0.00;

				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = allData[0].dso;
				}


				var dio = 0.00;
				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = allData[0].dio;
				}

				var dpo = 0.00;

				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = allData[0].dpo;
				}

				var ccc = dso + dio - dpo;
				//$("#cccConversionCycle").text(parseFloat(ccc).toFixed(2));



				/*earningMarginValue
				  returnOnInvestmentValue
				  returnOnEquityValue
				  earningPerShareValue*/




			}
		}, error: function(data) {
			console.log(data);
		}
	})


	///////////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-earning-margin-trend",
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




				// Earnings Margin Trend

				Highcharts.chart('earningsmargintrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [23, 24, 28, 31, 35, 27],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [25, 25, 25, 25, 25, 25],
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
		url: "dashboard-return-on-equity-trend",
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



				// Return on Equity Trend

				Highcharts.chart('returnequitytrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [13, 15, 16, 17, 13, 14],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [15, 15, 15, 15, 15, 15],
						color: '#F79C92'
					}]
				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-return-on-investment-trend",
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





				// Return on Investment Trend

				Highcharts.chart('returninvestment', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [32, 28, 32, 33, 33, 34],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [30, 30, 30, 30, 30, 30],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-earning-per-share-trend",
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




				// Earnings per Share Trend

				Highcharts.chart('earningsharetrend', {
					chart: {
						type: 'spline',
						height: 200,
					},
					title: {
						text: ''
					},

					xAxis: {
						categories: ['Nov 22', 'Dec 22', 'Jan 23', 'Feb 23', 'Mar 23', 'Apr 23'],

						accessibility: {
							description: 'Months of the year'
						}
					},
					yAxis: {
						gridLineColor: 'transparent',
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
						name: 'Actual',
						marker: {
							symbol: 'circle'
						},
						data: [54, 55, 57, 53, 59, 64],
						color: '#BF05FF'

					}, {
						name: 'Target',
						marker: {
							symbol: 'circle'
						},
						data: [70, 70, 70, 70, 70, 70],
						color: '#F79C92'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})

}

function getFinKPICurrentWorkingCapital() {
	var tabType = $("#tabKeyId").val();
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	if (tabType === "1") {
		financialKPIWithDate(fromDate, toDate);
	} else if (tabType === "2") {
		getLiquidityRatiosData();
	} else if (tabType === "3") {
		getWorkingRatiosData();
	} else if (tabType === "4") {
		getRiskRatiosData();
	} else if (tabType === "5") {
		getProfitabilityRatiosData();
	}
}

function resetFinancialKPI() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate1").val(fromDate);
	$("#toDate1").val(toDate);
	getFinKPICurrentWorkingCapital();
}

/*-------------------------------With Date filter from and To-----------------------------------------------------------*/

function financialKPIWithDate(fromDate, toDate) {

	var org = $("#accOrgFin").find('option:selected').text();
	var orgDiv = $("#accOrgDivFin").find('option:selected').text();
	var loc = $("#accFinLoc").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "dashboard-current-working-capital",
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
				var netProfitMargin = 0;

				if (allData[0].profitmargin === null || allData[0].profitmargin === undefined) {
					netProfitMargin = 0.00;
				} else {
					netProfitMargin = allData[0].profitmargin;
				}

				$("#profitMargin").text(parseFloat(netProfitMargin).toFixed(2));
				var totalProfitMargin = parseInt(netProfitMargin);

				//	*********************** current working assests ***************************/
				$("#prepaid").text(parseFloat(allData[0].prepaid).toFixed(2));
				$("#inventory").text(parseFloat(allData[0].inventory).toFixed(2));
				$("#receivable").text(parseFloat(allData[0].receivableamt).toFixed(2));
				$("#cashamnt").text(parseFloat(allData[0].cashamount).toFixed(2));
				var totalWorkingCapital = parseFloat(allData[0].prepaid + allData[0].inventory + allData[0].receivableamt + allData[0].cashamount).toFixed(2)
				$("#totalWorkAmount").text(totalWorkingCapital);

				//	*********************** current libilities ***************************/
				$("#payableAccount").text(parseFloat(allData[0].accountpayable).toFixed(2));
				$("#creditcarddebt").text(parseFloat(allData[0].creditcarddebit).toFixed(2));
				$("#bankoperating").text(parseFloat(allData[0].bankoperatingcredit).toFixed(2));
				$("#accruedExp").text(parseFloat(allData[0].accruedexpenses).toFixed(2));
				$("#taxesPayAmnt").text(parseFloat(allData[0].taxespayable).toFixed(2));
				var totalCurrentLibilities = parseFloat(allData[0].accountpayable + allData[0].creditcarddebit + allData[0].bankoperatingcredit +
					allData[0].accruedexpenses + allData[0].taxespayable).toFixed(2);
				$("#totalCurrentLibilities").text(totalCurrentLibilities);


				var workingCapital = totalWorkingCapital - totalCurrentLibilities;
				$("#workingCapital").text(parseFloat(workingCapital).toFixed(2));

				/*	formula for	currentRatio=currentAssets/currentLibilities	*/

				var currentRatio = parseFloat(totalWorkingCapital / totalCurrentLibilities).toFixed(2);
				if (currentRatio === null || currentRatio === undefined || currentRatio === "Infinity") {
					currentRatio = 0;
				}
				var currentRatio1 = parseInt(currentRatio);
				$("#currentRatio").text(currentRatio);
				$("#currentRatio1").text(currentRatio);


				/*	formula for	quickRatio=(totalAssests-inventory)/totalLibilities	*/

				var inventory = allData[0].inventory;
				var quickRatio = (totalWorkingCapital - inventory) / totalCurrentLibilities;
				if (quickRatio === null || quickRatio === undefined || quickRatio === Infinity) {
					quickRatio = 0;
				}
				$("#quickRatio").text(parseFloat(quickRatio).toFixed(2));
				var quickRatio1 = parseInt(quickRatio);

				var dso = 0.00;

				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = allData[0].dso;
				}

				$("#dso").text(parseFloat(dso).toFixed(2));

				var dio = 0.00;
				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = allData[0].dio;
				}
				$("#dio").text(parseFloat(dio).toFixed(2));

				var dpo = 0.00;

				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = allData[0].dpo;
				}
				$("#dpo").text(parseFloat(dpo).toFixed(2));

				var ccc = dso + dio - dpo;
				$("#ccc").text(parseFloat(ccc).toFixed(2));

				//cash coversion in cycle ends



				//current quick stats - Quick Ratio
				Highcharts.chart('cqsQuickRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Quick Ratio',
						style: { fontSize: '10px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#bf05ff',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#9792e8',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#f58d68',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [quickRatio1],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - quick ratio - spline
				Highcharts.chart('cqsQuickRatioSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0,
						color:'#F79C92'
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [100, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 200],
						lineWidth: 2,
						color: '#BF05FF'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
						}]
					}

				});




				//current quick stats - Curent Ratio
				Highcharts.chart('cqsCurrentRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Current Ratio',
						style: { fontSize: '11px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#339db3',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#d9d9d9',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#5badfa',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [currentRatio1],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - Current ratio - spline
				Highcharts.chart('cqsCurrentRatioSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0,
						color:'#F79C92'
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [100, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 200],
						lineWidth: 2
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
						}]
					}

				});





				//current quick stats - Net Profit Margin
				Highcharts.chart('cqsNetProfitMarginRatio', {

					chart: {
						type: 'gauge',
						height: 80,
						margin: 0,
						marginTop: 10
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: 'Net Profit Margin',
						style: { fontSize: '11px' }
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},
					pane: {
						startAngle: -90,
						endAngle: 89.9,
						background: null,
						center: ['50%', '75%'],
						size: '110%'
					},

					// the value axis
					yAxis: {
						min: 0,
						max: 50,
						tickPixelInterval: 0,
						tickPosition: 'inside',
						tickColor: '#FFFFFF',
						tickLength: 0,
						minorTickInterval: null,
						labels: {
							// distance: 0,
							// y: 16,
							// style: {
							//     fontSize: '12px'
							// }
							enabled: false
						},
						plotBands: [{
							from: 0,
							to: 15,
							color: '#339db3',
							thickness: 20
						}, {
							from: 15,
							to: 40,
							color: '#d9d9d9',
							thickness: 20
						}, {
							from: 40,
							to: 50,
							color: '#5badfa',
							thickness: 20
						}]
					},

					series: [{
						name: '',
						data: [totalProfitMargin],

						dataLabels: {
							// borderWidth: 0,
							// color: '#333333',
							// style: {
							//     fontSize: '16px'
							// }
							enabled: false
						},
						dial: {
							radius: '100%',
							backgroundColor: '#730101',
							topWidth: 1,
							baseWidth: 7,
							baseLength: '5%',
							rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});


				//current quick stats - Net Profit Margin - spline
				Highcharts.chart('cqsNetProfitMarginSpline', {
					chart: {
						type: 'spline',
						height: 80,
						margin: 0,
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
					credits: false,

					yAxis: {
						title: {
							text: ''
						},
						labels: { enabled: false, },
						gridLineColor: 'transparent'
					},

					xAxis: {
						title: {
							text: ''
						},
						lineColor: 'transparent',
						tickWidth: 0,
						labels: { enabled: false, },
					},

					legend: {
						enabled: false
					},

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							marker: false
						}
					},

					series: [{
						name: '',
						data: [800, 230, 103, 92, 87, 130, 180, 200, 82.92, 100, 130, 120, 150, 160, 170, 180, 800],
						lineWidth: 2
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


	//cost conversion cycle in days
	Highcharts.chart('costConversionCycle', {
		chart: {
			height: 197
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
		credits: false,
		xAxis: [{
			categories: ['2020', '2021', '2022', '2023'],
			crosshair: true
		}],
		yAxis: [{ // Primary yAxis
			labels: {
				format: '{value}',
			},
			min: 0,
			title: {
				text: 'DSO | DIO | DPO ',
			}
		}, { // Secondary yAxis
			title: {
				text: 'CCC',
			},
			min: -100,
			labels: {
				format: '{value}',
			},
			opposite: true
		}],
		tooltip: {
			shared: true
		},
		plotOptions: {
			column: {
				stacking: 'normal'
			},
			dataLabels: {
				enabled: true
			}
		},
		legend: {
			enabled: false,
		},
		series: [{
			name: 'DSO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#CA7CE5'

		}, {
			name: 'DIO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#F79C92'

		}, {
			name: 'DPO',
			type: 'column',
			stack: 1,
			yAxis: 1,
			data: [49.9, 71.5, 106.4, 129.2],
			color: '#BF05FF'
		}, {
			name: 'CCC',
			type: 'spline',
			data: [46.0, 36.9, 49.5, 58.5],
		}]
	});





	$.ajax({
		type: "GET",
		url: "dashboard-current-budget-varience",
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
				var allData = jsonData.varienceList;

				$("#varienceList").empty();
				//
				for (var i = 0; i < allData.length; i++) {
					totalActual += parseFloat(allData[i].actual);  // Add actual to totalActual
					totalBudget += parseFloat(allData[i].budget);  // Add budget to totalBudget
					totalVarience += parseFloat(allData[i].varience);
					//alert("totalActual-----------"+allData[i].actual)


					var abc = '<tr>'
						+ '<td>' + allData[i].projects + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].actual).toFixed(2) + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].budget).toFixed(2) + '</td>'
						+ '<td style="text-align: right;">' + parseFloat(allData[i].varience).toFixed(2) + '</td>'
						+ '</tr>';

					$("#varienceList").append(abc);
				}
				$("#totalActual").text(totalActual);
				$("#totalBudget").text(totalBudget);
				$("#totalVarience").text(totalVarience);

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-vendor-payment-errorrate",
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
				var allData = jsonData.vendorPayList;

				var catogaries = [];
				var avg = [];
				var errorRate = [];
				for (var i = 0; i < allData.length; i++) {
					catogaries.push(allData[i].month)
					avg.push(parseInt(allData[i].average))
					errorRate.push(parseInt(allData[i].errorrate));
				}


				Highcharts.chart('vendorPaymentErrorRate', {
					chart: {
						animation: true,
						height: 203
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
					credits: false,
					yAxis: {
						title: {
							text: ''
						}
					},

					xAxis: {
						categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023',
							'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023',
							'Oct 2023', 'Nov 2023', 'Dec 2023']
					},

					// legend: {
					//     enabled: false,
					// },

					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
						}
					},

					series: [{
						type: 'spline',
						name: 'Vendor Payment Error Rate',
						data: [4916, 7941, 9742, 19851, 22490, 10282,
							8121, 6885, 13726, 24243, 31050, 26735],
						marker: false,
						lineWidth: 2,
						color: '#BF05FF'
					}, {
						type: 'line',
						name: 'Average Vendor Pay',
						data: [11744, 11744, 11744, 11744, 11744, 11744,
							11744, 11744, 11744, 11744, 11744, 11744],
						marker: false,
						lineWidth: 3,
						lineColor: '#F58D68',
						color: '#F58D68'
					}],

					responsive: {
						rules: [{
							condition: {
								maxWidth: 500
							},
							chartOptions: {
								legend: {
									layout: 'horizontal',
									align: 'center',
									verticalAlign: 'bottom'
								}
							}
						}]
					}

				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})


}

/*Function for orgDiv Change*/

function getAccFinOrgDivData() {
	var tabType = $("#tabKeyId").val();
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	if (tabType === "1") {
		financialKPIWithDate(fromDate, toDate);
	} else if (tabType === "2") {
		getLiquidityRatiosData();
	} else if (tabType === "3") {
		getWorkingRatiosData();
	} else if (tabType === "4") {
		getRiskRatiosData();
	} else if (tabType === "5") {
		getProfitabilityRatiosData();
	}
}