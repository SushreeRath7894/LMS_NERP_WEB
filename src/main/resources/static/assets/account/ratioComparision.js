function getRatioComparisionWithDate() {
	ratioComparision();
}
function ratioComparision() {
	// Return on Equity Trend

	var fromDate = $("#fromDate6").val();
	var toDate = $("#toDate6").val();
	var org = $("#accOrgRatioCom").find('option:selected').text();
	var orgDiv = $("#accOrgDivRatioCom").find('option:selected').text();
	var loc = $("#ratioComLoc").find('option:selected').text();
	$.ajax({
		type: "GET",
		url: "dashboard-ratio-comparison-count",
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
				//var cashRatio = parseFloat(cashEquivalent/shortTimeLiabilities).toFixed(2);
				let cashRatio = safeDivide(cashEquivalent, shortTimeLiabilities);
				let absoluteLiquidityRatio = safeDivide(parseFloat(cashEquivalent + marketableSecurities), shortTimeLiabilities);
				//var absoluteLiquidityRatio = parseFloat((cashEquivalent+marketableSecurities)/shortTimeLiabilities).toFixed(2);

				//alert("cashRatio-------------"+cashRatio);
				//alert("absoluteLiquidityRatio-------------"+absoluteLiquidityRatio);

				//$("#liquidityCashRatio").text(parseFloat(cashRatio).toFixed(2));
				//$("#absoluteLiquidityRatio").text(parseFloat(absoluteLiquidityRatio).toFixed(2));


				//-------------------------------------------------------------


				/*	formula for	currentRatio=currentAssets/currentLibilities	*/

				var currentRatio = parseFloat(totalWorkingCapital / totalCurrentLibilities).toFixed(2);
				if (currentRatio === null || currentRatio === undefined || currentRatio === "Infinity") {
					currentRatio = 0;
				}
				var currentRatio1 = parseInt(currentRatio);
				//$("#currentRatio").text(currentRatio);
				//$("#currentRatio1").text(currentRatio);
				//$("#liquidityCurrentRatio").text(currentRatio);





				/*	formula for	quickRatio=(totalAssests-inventory)/totalLibilities	*/

				var inventory = allData[0].inventory;
				var quickRatio = (totalWorkingCapital - inventory) / totalCurrentLibilities;
				if (quickRatio === null || quickRatio === undefined || quickRatio === Infinity) {
					quickRatio = 0;
				}
				//$("#quickRatio").text(parseFloat(quickRatio).toFixed(2));
				//$("#liquidityQuickRatio").text(parseFloat(quickRatio).toFixed(2));

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
				//$("#dpo").text(parseFloat(dpo).toFixed(2));

				var ccc = dso + dio - dpo;
				//$("#ccc").text(parseFloat(ccc).toFixed(2));



			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/////////////////////////////////////////////////////////
	$.ajax({
		type: "GET",
		url: "dashboard-ratio-return-on-equity-trend",
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
				Highcharts.chart('returnonequity', {
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



	/////////////////////////////////////////////////////////

	$.ajax({
		type: "GET",
		url: "dashboard-ratio-earning-margin-trend",
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

				Highcharts.chart('earningsmartrend', {
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
						data: [23, 24, 26, 31, 35, 27],
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


}

/*Function for reset date*/

function resetRatioComparisionDate() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate6").val(fromDate);
	$("#toDate6").val(toDate);

	getRatioComparisionWithDate();

}

/*Function for get data on Onchange of orgDiv*/

function getAccaccRatioComOrgDivData() {
	getRatioComparisionWithDate();
}