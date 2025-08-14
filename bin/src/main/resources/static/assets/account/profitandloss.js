function getProfitAndLossWithDate() {
	profitAndLoss();
}

function profitAndLoss() {
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#accOrgProfitLoss").find('option:selected').text();
	var orgDiv = $("#accOrgDivProfitLoss").find('option:selected').text();
	var loc = $("#proLossLoc").find('option:selected').text();
	var dateParts = toDate.split('-');
	var thisYear = parseInt(dateParts[2], 10);
	var lastYear = thisYear - 1;

	$.ajax({
		type: "GET",
		url: "dashboard-pl-outstanding-revenue-expenses-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			thisYear: thisYear,
			lastYear: lastYear

		},
		async: true,
		success: function(response) {
			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				// Calculate the percentage revenue change
				var revenueLastYear = parseInt(allData[0].revenueLastYear);
				var revenueThisYear = parseInt(allData[0].revenueThisYear);
				var change = revenueThisYear - revenueLastYear;
				if (revenueLastYear == 0) {
					revenueLastYear = 1;
				}
				var changePercentageRevenue = (change / revenueLastYear) * 100;

				// Calculate the percentage expense change
				var expenseLastYear = parseInt(allData[0].expenseLastYear);
				var expenseThisYear = parseInt(allData[0].expenseThisYear);
				var change = expenseThisYear - expenseLastYear;
				if (expenseLastYear == 0) {
					expenseLastYear = 1;
				}
				var changePercentageExpense = (change / expenseLastYear) * 100;


				$("#plExpenseRevenue").text(changePercentageExpense);
				$("#plRevenue").text(changePercentageRevenue);
				$("#plOutstandingRevenue").text(allData[0].outstandingRevenue);
			}
		}, error: function(data) {
			console.log(data);
		}
	})

	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-profit-loss-cost-statement",
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
				var allData = jsonData.profitAndLoss;

				$("#totalRevenue").text(allData[0].totalRevenue);
				$("#totalSales").text(allData[0].totalSales);
				$("#totalMarketing").text(allData[0].totalMarketing);
				$("#totalAdmin").text(allData[0].totalAdmin);
				$("#otherIncome").text(allData[0].otherIncome);
				$("#otherExpenses").text(allData[0].otherExpenses);
				$("#totalInterest").text(allData[0].totalInterest);

				$("#totalPurchase").text(allData[0].totalPurchase);


				var totalOpex = allData[0].totalSales + allData[0].totalMarketing + allData[0].totalAdmin + allData[0].otherIncome + allData[0].otherExpenses;
				$("#totalOpex").text(totalOpex);


				var operatingProfit = allData[0].totalInterest;
				$("#operatingProfit").text(operatingProfit);

				var grossProfit = (allData[0].totalSales + allData[0].totalMarketing + allData[0].totalAdmin + allData[0].otherIncome + allData[0].otherExpenses) + allData[0].totalPurchase;
				$("#grossProfit").text(grossProfit);

				var totalNetProfit = ((allData[0].totalSales + allData[0].totalMarketing + allData[0].totalAdmin +
					allData[0].otherIncome + allData[0].otherExpenses) - allData[0].totalPurchase) - allData[0].totalInterest
				$("#totalNetProfit").text(totalNetProfit);

				/*var longTermsassests=allData[0].longtermassets;
				$("#longTermsAssets").text(longTermsassests);
				
				var totalAssets= currentAssets+longTermsassests;
				$("#totalAssets").text(totalAssets);*/



				var salesList = [];
				for (var i = 0; i < allData[0].salesList.length; i++) {
					var datas = [];
					datas.push(parseInt(allData[0].salesList[i].amount));
					var avg = (allData[0].amount) / 100;
					datas.push(parseInt(allData[0].salesList[i].avg));
					salesList.push(datas);
					salesList.push(avg);
				}

				var marketingList = [];
				for (var i = 0; i < allData[0].marketingList.length; i++) {
					var datas = [];

					datas.push(allData[0].marketingList[i].month);
					datas.push(allData[0].marketingList[i].amount);
					marketingList.push(datas);
				}

				var generalList = [];
				for (var i = 0; i < allData[0].generalList.length; i++) {
					var datas = [];
					datas.push(allData[0].generalList[i].month);
					datas.push(parseInt(allData[0].generalList[i].amount));
					generalList.push(datas);
				}



				/*	***************************** total liblities part **************************************	*/


				$("#totalAccountPayable").text(allData[0].acntPayAmnt);
				$("#totalOtherLib").text(allData[0].otherLibilities);
				var totalCurrentLib = allData[0].acntPayAmnt + allData[0].otherLibilities;
				$("#totalCurrentLib").text(totalCurrentLib);


				$("#commonStock").text(allData[0].commonStock);
				$("#totalCurrEarnings").text(allData[0].currentEarnings);
				var shareholdereqty = allData[0].commonStock + allData[0].currentEarnings;
				$("#shareholdereqty").text(shareholdereqty);


				var totalLiablities = totalCurrentLib + shareholdereqty;
				$("#totalLiablities").text(totalLiablities);


				var acntPayAmntList = [];
				for (var i = 0; i < allData[0].acntPayAmntList.length; i++) {
					var datas = [];

					datas.push(allData[0].acntPayAmntList[i].month);
					datas.push(allData[0].acntPayAmntList[i].amount);
					acntPayAmntList.push(datas);
				}


				var otherLibList = [];
				for (var i = 0; i < allData[0].otherLibList.length; i++) {
					var datas = [];

					datas.push(allData[0].otherLibList[i].month);
					datas.push(allData[0].otherLibList[i].amount);
					otherLibList.push(datas);
				}
				var commonStockList = [];
				for (var i = 0; i < allData[0].commonStockList.length; i++) {
					var datas = [];

					datas.push(allData[0].commonStockList[i].month);
					datas.push(allData[0].commonStockList[i].amount);
					commonStockList.push(datas);
				}

				var currentEarningsList = [];
				for (var i = 0; i < allData[0].currentEarningsList.length; i++) {
					var datas = [];

					datas.push(allData[0].currentEarningsList[i].month);
					datas.push(allData[0].currentEarningsList[i].amount);
					currentEarningsList.push(datas);
				}

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-pl-gross-profit-margin-prcnt",
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
				var totalGrossProfit = allData[0].totalGrossProfit;


				//financeGross Profit Margin
				Highcharts.chart('financeGrossProfitMargin', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 80,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: '22%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '14', "color": '#453f7a' }

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
						// labels: {
						//     distance: 0,
						//     y: 16,
						//     style: {
						//         fontSize: '12px'
						//     }
						// },
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
						data: [totalGrossProfit],

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
							// backgroundColor: '#730101',
							// topWidth: 7,
							// baseWidth: 1,
							// baseLength: '0%',
							// rearLength: '0%'
						},
						pivot: {
							radius: 0
						},

					}]

				});
				Highcharts.charts[Highcharts.charts.length - 1].setTitle({
					text: totalGrossProfit + '%'  // Update the title text with the value
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	//Opex Ratio

	$.ajax({
		type: "GET",
		url: "dashboard-pl-opex-ratio-prcnt",
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
				var totalOpexRatio = allData[0].totalOpexRatio;

				Highcharts.chart('financeOpexRatio', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 80,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: '22%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '14', "color": '#453f7a' }

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
						data: [totalOpexRatio],

						dataLabels: {
							/*borderWidth: 0,
							color: '#00f7ff',
							style: {
								fontSize: '16px'
							},*/
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

				Highcharts.charts[Highcharts.charts.length - 1].setTitle({
					text: totalOpexRatio + '%'  // Update the title text with the value
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});


	$.ajax({
		type: "GET",
		url: "dashboard-pl-operating-profit-prcnt",
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
				var operatingProfit = allData[0].operatingProfit;

				//Operating Profit Margin
				Highcharts.chart('financeOperatingProfitMargin', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 80,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: '22%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '14', "color": '#453f7a' }

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
							color: '#8077cb',
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
						data: [operatingProfit],

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

				Highcharts.charts[Highcharts.charts.length - 1].setTitle({
					text: operatingProfit + '%'  // Update the title text with the value
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});




	$.ajax({
		type: "GET",
		url: "dashboard-pl-net-profit-margin-prcnt",
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
				var netProfit = allData[0].netProfit;


				//Net Profit Margin
				Highcharts.chart('financeNetProfitMargin', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 80,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: '22%',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 50,
						margin: 0,
						style: { "fontSize": '14', "color": '#453f7a' }

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
						data: [netProfit],

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

				Highcharts.charts[Highcharts.charts.length - 1].setTitle({
					text: netProfit + '%'  // Update the title text with the value
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	$.ajax({
		type: "GET",
		url: "dashboard-pl-opex-year-to-date",
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
				var allData = jsonData.salesList;

				var salesAvg = [];
				var otherAvg = [];
				var avgMarketing = [];

				for (var i = 0; i < allData.length; i++) {
					salesAvg.push(parseInt(allData[i].salesAvg))
					otherAvg.push(parseInt(allData[i].otherAvg))
					avgMarketing.push(parseInt(allData[i].avgMarketing))
				}

				//opex year to date
				Highcharts.chart('financeOpex', {
					chart: {
						type: 'bar',
						animation: true,
						backgroundColor: 'transparent',
						height: 190
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
					credits: {
						enabled: false
					},
					xAxis: {
						lineColor: 'transparent',
						labels: {
							enabled: false
						},
						gridLineColor: 'transparent',
						tickWidth: 0
					},
					yAxis: {
						min: 0,
						max: 100,
						title: {
							text: ''
						},
						lineColor: 'transparent',
						labels: {
							enabled: false
						},
						gridLineColor: 'transparent',
					},
					legend: {
						enabled: true,
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							dataLabels: {
								enabled: true,
								format: '{y}%',
								style: {
									textOutline: false 
								  }
							},
						}
					},
					series: [{
						name: 'Sales',
						//	data: salesAvg, color: '#B422B6'
						data: [49], color: '#B422B6'
					}, {
						name: 'Marketing',
						//	data: avgMarketing, color: '#F79C92'
						data: [25], color: '#F79C92'

					}, {
						name: 'General & Admin',
						//data: otherAvg, color: '#BF05FF'
						data: [26], color: '#BF05FF'
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",
		url: "dashboard-pl-revenue-and-cogs-list",
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
				var allData = jsonData.revenueList;

				var catogaries = [];
				var revenue = [];
				var cogs = [];
				for (var i = 0; i < allData.length; i++) {
					catogaries.push(allData[i].month)
					revenue.push(parseInt(allData[i].revenue))
					cogs.push(parseInt(allData[i].cogs));
				}
				//Revenue and COGS
				Highcharts.chart('financeRevenueAndCOGS', {
					chart: {
						type: 'column',
						zoomType: 'xy',
						animation: true,
						height: 190
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
					credits: {
						enabled: false
					},
					xAxis: [{
						//categories: catogaries,
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sep 2024', 'Oct 2024', 'Nov 2024', 'Dec 2024', 'Jan 2025', 'Feb 2025', 'Mar 2025'],

						crosshair: true
					}],
					yAxis: [{
						labels: {
							format: '{value}',
						},
						title: {
							text: 'INR',
						},
					}
					],
					plotOptions: {
						column: {
							pointPadding: 0,
							borderWidth: 0
						}
					},
					legend: {
						enabled: true
					},
					series: [{
						name: 'Revenue',
						data: [85, 85, 85, 85, 85, 85, 85, 45, 67, 98, 61, 45],
						color: '#bf05ff'
					}, {
						name: 'Cogs',
						data: [25, 25, 25, 25, 25, 25, 25, 56, 34, 29, 50, 23],
						color: '#F79C92'
					}

					]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	})




	/*earning before taxes
	*/
	$.ajax({
		type: "GET",
		url: "dashboard-pl-earning-before-interest-and-taxes-list",
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
				var allData = jsonData.revenueList;

				var saleMonth = [];
				var sotalSubTotal = [];


				for (var i = 0; i < allData.length; i++) {
					saleMonth.push(allData[i].saleMonth)
					sotalSubTotal.push(parseInt(allData[i].totalSubTotal))

				}

				//earning before taxes
				Highcharts.chart('financeEarningBeforeTaxes', {
					chart: {
						type: 'line',
						animation: true,
						height: 185
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

					yAxis: {
						title: {
							text: 'INR',
						}
					},
					credits: { enabled: false },

					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						// labels: {
						//     rotation: -45,
						//     style: {
						//         fontSize: '10px',
						//         fontFamily: 'Verdana, sans-serif',
						//         color: '#000000'
						//     }
						// },
						/*categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']*/
						//	categories: saleMonth
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sep 2024', 'Oct 2024', 'Nov 2024', 'Dec 2024', 'Jan 2025', 'Feb 2025', 'Mar 2025'],
					},
					plotOptions: {
						series: {
							label: {
								connectorAllowed: false
							},
							// pointStart: 2010
						}
					},

					/*series: [{
						name: 'EBIT Actual',
						//data: sotalSubTotal,
						color: '#78daa9'
					}, {
						name: 'EBIT Target',
						data: [],
						dashStyle: 'ShortDash',
						color: '#107f8c',
						marker: false,
					},],*/

					series: [
						{
							name: 'Cogs',
							data: [25, 35, 25, 26, 30, 32, 33, 34, 38, 40, 40],
							color: '#F79C92'

						},

						{
							name: 'Revenue',
							data: [25, 27, 28, 30, 32, 34, 36, 38, 40, 42, 44],
							color: '#bf05ff',
							dashStyle: 'dash',
							marker: {
								enabled: false
							}
						}

					],

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



	//dashboard-pl-opex-mom-ytd


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-opex-mom-ytd",
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
				//Opex Month to Month
				Highcharts.chart('OpexMonthtoMonth', {
					chart: {
						height: 185
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
						categories: ['Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sep 2024', 'Oct 2024', 'Nov 2024', 'Dec 2024', 'Jan 2025', 'Feb 2025', 'Mar 2025'],
						crosshair: true
					}],
					yAxis: [{
						labels: {
							format: '{value}',
						},
						title: {
							text: 'INR',
						},
					}, { // Secondary yAxis
						title: {
							text: '',
						},
						labels: {
							format: '{value}',
							enabled: false
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
					series: [{
						name: 'Sales',
						type: 'column',
						stack: 1,
						yAxis: 1,
						data: [49.9, 71.5, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2],
						color: '#CA7CE5'

					}, {
						name: 'Marketing',
						type: 'column',
						stack: 1,
						yAxis: 1,
						data: [49.9, 71.5, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2],
						color: '#F79C92'

					}, {
						name: 'General & Admin',
						type: 'column',
						stack: 1,
						yAxis: 1,
						data: [49.9, 71.5, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2, 106.4, 129.2],
						color: '#BF05FF'

					}, {
						name: 'Opex Ratio',
						type: 'line',
						data: [46.0, 36.9, 49.5, 58.5, 49.5, 58.5, 49.5, 58.5, 49.5, 58.5, 49.5, 58.5],
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})
	/////////////////////////////////////////////////////////


	//dashboard-pl-payment-status

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-payment-status",
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
				//Opex Month to Month
				//Payment Status

				Highcharts.chart('paymentStatus', {
					chart: {
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 242
					},
					title: {
						text: '',
						align: 'center',
						verticalAlign: 'middle',
						y: 60
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
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
								'#F79C92',
								'#bf05ff',
								'#B422B6'

							],
							dataLabels: {
								enabled: true,
								distance: 10,
								format: '<span>{point.name}</span><br>' +
									'<span>{point.percentage:.0f} %</span>',
								style: {
									fontWeight: 'bold',
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
							['Partial', 25],
							['Paid', 25],
							['Unpaid', 50],

						]
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})
	/////////////////////////////////////////////////////////



	//dashboard-pl-gross-profit-margin

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-gross-profit-margin",
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
				//Gross Profit Margin

				Highcharts.chart("GrossProfitMargin", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 180
					},

					title: {
						text: "69.91 %",
						align: "center",
						verticalAlign: "center",
						floating: true,
						y: 170,
						margin: 0,
						style: { fontSize: "14", color: "#bf05ff" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -128,
						endAngle: 127.9,
						background: null,
						center: ["50%", "75%"],
						size: "100%",
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
								to: 69,
								color: "#bf05ff",
								thickness: 15,
							},
							{
								from: 69,
								to: 100,
								color: "#f4ddfc",
								thickness: 15,
							},
						],
					},

					series: [
						{
							name: "",
							data: [69],
							tooltip: {
								valueSuffix: " km/h",
							},
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
		url: "dashboard-pl-payment-status",
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
				//Net Profit Margin

				Highcharts.chart("NetProfitMargin", {
					chart: {
						type: "gauge",
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						height: 180
					},

					title: {
						text: "57.05 %",
						align: "center",
						verticalAlign: "center",
						floating: true,
						y: 170,
						margin: 0,
						style: { fontSize: "14", color: "#bf05ff" },
					},
					navigation: {
						buttonOptions: {
							enabled: false,
						},
					},
					credits: { enabled: false },

					pane: {
						startAngle: -128,
						endAngle: 127.9,
						background: null,
						center: ["50%", "75%"],
						size: "100%",
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
								to: 57,
								color: "#F79C92",
								thickness: 15,
							},
							{
								from: 57,
								to: 100,
								color: "#f4ddfc",
								thickness: 15,
							},
						],
					},

					series: [
						{
							name: "",
							data: [57],
							tooltip: {
								valueSuffix: " km/h",
							},
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

	//dashboard-pl-top-expenses-by-cat-prcnt

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-top-expenses-by-cat-prcnt",
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
				//Expenses by Category		

				Highcharts.chart('ExpensesbyCategory', {
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
								format: '<span style="font-size: 1.2em"><b>{point.name}</b></span><br>' +
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
							['Rent Or Lease', 24],
							['Advertising', 20],
							['Education or Training', 18],
							['Contractors', 18],
							['Airfare', 16]

						]
					}]
				});



			}
		}, error: function(data) {
			console.log(data);
		}
	})

	//dashboard-pl-revenue-trend

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-revenue-trend",
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


				// Revenue Trend


				Highcharts.chart('RevenueTrend', {
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
							format: '{value}k'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						line: {

							marker: {
								radius: 0,

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
						name: 'Revenue',
						data: [63.23, 57.70, 49.67, 32.77, 65.39, 51.29],
						color: '#BF05FF'

					}, {
						name: 'Revenue Trend',
						data: [59, 58, 57, 56, 55, 54],
						color: '#F79C92',
						dashStyle: 'dash'
					}]
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	})
	/////////////////////////////////////////////////////////




	//dashboard-pl-customer-growth

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-customer-growth",
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


				// Customer Growth


				Highcharts.chart('CustomerGrowth', {
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
							format: '{value}k'
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true
					},
					plotOptions: {
						line: {

							marker: {
								radius: 0,

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
						name: 'Customers',
						data: [87, 86, 84, 84, 87, 86],
						color: '#BF05FF'

					}, {
						name: 'Customers Growth',
						data: [86, 85.9, 85.8, 85.7, 85.6, 85.5],
						color: '#F79C92',
						dashStyle: 'dash'
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	//dashboard-pl-top-revenue-generating-countries

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-top-revenue-generating-countries",
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


				// Revenue Generating Countries

				Highcharts.chart('RevenueGeneratingCountries', {
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
						categories: ['Canada', 'India', 'United States', 'Japan', 'China'],
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
						data: [15, 8, 7, 6.10, 6],
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



	//dashboard-pl-income-and-expenses-by-cat-prcnt

	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-income-and-expenses-by-cat-prcnt",
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


				//Income Expenses

				Highcharts.chart('IncomeExpenses', {
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
							pointWidth: 40,
							borderWidth: 1
						}
					},
					series: [{
						name: 'Income',
						data: [53.59, 46.90, 40.85, 27.33, 53.70, 42.22],
						color: '#bf05ff',
						dataLabels: {
							enabled: true,
						}
					}, {
						name: 'Expenses',
						data: [9.65, 10.80, 8.82, 5.44, 11.68, 9.02],
						color: '#F79C92',
						dataLabels: {
							enabled: true,
						}
					},
					{
						name: 'Profit',
						type: 'spline',
						data: [54, 47, 41, 28, 54, 43],
						color: '#B422B6'
					}



					]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})

	//dashboard-pl-profit-and-loss-report


	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-pl-profit-and-loss-report",
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

/*For reset button*/
function resetProfitAndLossDate() {
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
	$("#fromDate3").val(fromDate);
	$("#toDate3").val(toDate);

	profitAndLoss();
}
function getAccProfitLossDataOrgDiv() {
	profitAndLoss();
}

