function cashmanagement() {
	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();
	var org = $("#accOrgCasMan").find('option:selected').text();
	var orgDiv = $("#accOrgDivCasMan").find('option:selected').text();
	var loc = $("#casmanLoc").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "dashboard-cashmanagementWorkingCap",
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
				var allData = jsonData.Cashmanagement;
				var totalWorkingCapital = parseFloat(allData[0].prepaid + allData[0].inventory
					+ allData[0].receivableamt
					+ allData[0].cashamount);
				var totalCurrentLibilities = parseFloat(allData[0].accountpayable
					+ allData[0].creditcarddebit
					+ allData[0].bankoperatingcredit
					+ allData[0].accruedexpenses
					+ allData[0].taxespayable).toFixed(2);
				var workingCapital = totalWorkingCapital - totalCurrentLibilities;
				var currentRatio = parseFloat(totalWorkingCapital / totalCurrentLibilities).toFixed(2);


				var inventory = allData[0].inventory;
				var quickRatio = (totalWorkingCapital - inventory) / totalCurrentLibilities;

				if (isNaN(currentRatio) || !isFinite(currentRatio)) {
					currentRatio = 0.00;
				}

				if (isNaN(quickRatio) || !isFinite(quickRatio)) {
					quickRatio = 0.00;
				}

				$("#cmquickratio").text(parseFloat(quickRatio).toFixed(2));
				$("#cmcurrentratio").text(parseFloat(currentRatio).toFixed(2));
				$("#totalCashBalance").text(parseFloat(allData[0].cashamount).toFixed(2));
				$("#cashInBalance").text(parseFloat(allData[0].cashInAmount).toFixed(2));
				$("#cashOutBalance").text(parseFloat(allData[0].cashOutAmount).toFixed(2));

				var dio = 0.00;
				var dso = 0.00;
				var dpo = 0.00;


				if (allData[0].dio === null || allData[0].dio === undefined) {
					dio = 0.00;
				} else {
					dio = parseFloat(allData[0].dio).toFixed(2);
				}


				if (allData[0].dso === null || allData[0].dso === undefined) {
					dso = 0.00;
				} else {
					dso = parseFloat(allData[0].dso).toFixed(2);
				}


				if (allData[0].dpo === null || allData[0].dpo === undefined) {
					dpo = 0.00;
				} else {
					dpo = parseFloat(allData[0].dpo).toFixed(2);
				}
				/*	-------------------------------------------------------------------------*/
				//finance cm DSO
				Highcharts.chart('financeCMDso', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 120,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: dso,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '16', "color": '#212529' }

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
							to: 40,
							color: '#9792E8',
							thickness: 25
						}, {
							from: 40,
							to: 70,
							color: '#F58D68',
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
				//finance cm DIO
				Highcharts.chart('financeCMDio', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 120,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: dio,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '16', "color": '#212529' }

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
							to: 40,
							color: '#CA7CE5',
							thickness: 25
						}, {
							from: 40,
							to: 70,
							color: '#F79C92',
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
				//finance cm DPO
				Highcharts.chart('financeCMDpo', {

					chart: {
						type: 'gauge',
						plotBackgroundColor: null,
						plotBackgroundImage: null,
						plotBorderWidth: 0,
						plotShadow: false,
						backgroundColor: 'transparent',
						height: 120,
						margin: 0
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#003961'],
					title: {
						text: dpo,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '16', "color": '#212529	' }

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
							to: 40,
							color: '#F79C92',
							thickness: 25
						}, {
							from: 40,
							to: 70,
							color: '#CA7CE5',
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
				/*	-------------------------------------------------------------------------*/
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();

	$.ajax({
		type: "GET",
		url: "dashboard-cashbalance",
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
				var cashEndDatas = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var cashEndData = jsonData[i];
						monthDatas.push(cashEndData.monthYear);
						cashEndDatas.push(cashEndData.cashEndOfMonth);
					}
				}

				console.log("monthDatas after Load Page-------------------" + monthDatas);
				console.log("cashEndDatas after Load Page-------------------" + cashEndDatas);


				Highcharts.chart('financeCMCashEOM', {
					chart: {
						type: 'column',
						animation: true,
						height: 197
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
						text: 'Cash',
						style: { fontSize: '13px' },
						align: 'left'
					},
					subtitle: {
						text: 'at End Of Month',
						style: { fontSize: '11px', },
						align: 'left'
					},
					xAxis: {
						categories: monthDatas,
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
						data: cashEndDatas,
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



	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();

	$.ajax({
		type: "GET",
		url: "dashboard-cminventory",
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

				var monthData = [];
				var expenseOnInventroy = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var inventoryData = jsonData[i];
						monthData.push(inventoryData.monthYear);
						expenseOnInventroy.push(inventoryData.total_paidAmnt);
					}
				}

				console.log("monthData for inventory----------------------" + monthData);
				console.log("expenseOnInventroy for inventory----------------------" + expenseOnInventroy);


				console.log("expenseOnInventroy-----------" + expenseOnInventroy);

				//finance cm inventory
				Highcharts.chart('financeCMDInventory', {
					chart: {
						type: 'line',
						height: 190,
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
						gridLineColor: 'transparent'
					},
					legend: { enabled: true },

					xAxis: {
						categories: monthData,
						rotation: '45deg',
						labels: {
							style: {
								fontSize: '10px',
							}
						}
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
						name: '',
						data: expenseOnInventroy,
						color: '#B422B6',
						lineWidth: 2,
						marker: false
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


	//finance cm quick ratio
	Highcharts.chart('financeCMQuickRatio', {
		chart: {
			type: 'area',
			height: 80,
			animation: true,
			margin: 0
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
			categories: ['Q1 2021', 'Q2 2021', 'Q3 2021', 'Q4 2021', 'Q1 2022', 'Q2 2022', 'Q3 2022'],
			rotation: '45deg',
			labels: { enabled: false },
			lineColor: 'transparent'
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
			data: [29139, 31175, 31255, 29561, 27552, 26008, 31830],
			color: '#BF05FF'
		},]
	});
	//finance cm current ratio
	Highcharts.chart('financeCMCurrentRatio', {
		chart: {
			type: 'area',
			height: 80,
			animation: true,
			margin: 0
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
			categories: ['Q1 2021', 'Q2 2021', 'Q3 2021', 'Q4 2021', 'Q1 2022', 'Q2 2022', 'Q3 2022'],
			rotation: '45deg',
			labels: { enabled: false },
			lineColor: 'transparent'
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
			data: [29139, 31175, 31255, 29561, 27552, 26008, 31830,
			],
			color: '#F79C92'
		},]
	});

	/* ---------------------------Receivable Vs Payable count Start----------------------------*/

	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();


	$.ajax({
		type: "GET",
		url: "dashboard-cash-management-payable-vs-receivable",
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
				var receivableDatas = [];
				var payableDatas = [];

				if (jsonData != null) {
					for (var i = 0; i < jsonData.length; i++) {
						var payableRecievableData = jsonData[i];
						monthDatas.push(payableRecievableData.monthYear);
						payableDatas.push(payableRecievableData.payableTurnOver);
						receivableDatas.push(payableRecievableData.receivableTurnOver);
					}
				}

				console.log("receivableDatas-----------------------------" + receivableDatas)
				console.log("payableDatas--------------------------------" + payableDatas)


				Highcharts.chart('financeCMDArt', {
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
						categories: monthDatas,
						crosshair: true,
						labels: {
							style: {
								fontSize: '11px',
							}
						}
					}],
					yAxis: [{
						labels: {
							format: '{value}',
						},
						title: {
							text: '',
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
						enabled: true,
						fontSize: '10px'
					},
					series: [{
						name: 'Accounts Receivable Turnover',
						data: receivableDatas,
						color: '#BF05FF',
						fontSize: '10px'

					}, {
						name: 'Accounts Payable Turnover',
						data: payableDatas,
						color: '#F58D68',
						margin: '0',
						fontSize: '10px'

					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})


	/* ---------------------------Receivable Vs Payable count End----------------------------*/

	/* --------------------------------Payable count start-------------------------------	*/

	$.ajax({
		type: "GET",
		url: "dashboard-cash-management-payable-count",
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
				var allData = jsonData.payableCount;

				var noDueTotalCount = allData[0].totalNoDueCount;
				var dueWithIn30DaysCount = allData[0].payableWithIn30Days;
				var dueWithIn60DaysCount = allData[0].payableWithIn60Days;
				var dueWithIn90DaysCount = allData[0].payableWithIn90Days;
				var allDueCount = allData[0].payableAllCount;



				//finance cm accounts payable
				Highcharts.chart('financeCMDAccountsPayable', {
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
					credits: {
						enabled: false
					},
					title: {
						text: ''
					},
					subtitle: {
						text:
							''
					},
					xAxis: {
						labels: { enabled: false },
						showInLegend: true,
						lineColor: 'transparent',
						tickWidth: 0
					},
					yAxis: {
						min: 0,

						labels: { enabled: false },
						title: {
							text: ''
						},
						gridLineColor: 'transparent'
					},



					series: [
						{
							name: 'Not Due',
							data: [noDueTotalCount],
							color: '#BF05FF'
						},
						{
							name: '< 30 days',
							data: [dueWithIn30DaysCount],
							color: '#B422B6'
						}, {
							name: '< 60 days',
							data: [dueWithIn60DaysCount],
							color: '#56156C'
						},
						{
							name: '< 90 days',
							data: [dueWithIn90DaysCount],
							color: '#F79C92'
						},
						{
							name: 'All Due',
							data: [allDueCount],
							color: '#CA7CE5'
						}
					]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	})



	/* --------------------------------Payable count End-------------------------------	*/

}

function resetCashManagement() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate2").val(fromDate);
	$("#toDate2").val(toDate);
	cashmanagement();
}

function cashManagementWithDate() {
	cashmanagement();
}

/*oncahnge of orgDiv*/

function getAccaccCasManOrgDivData() {
	cashmanagement();
}





