function deliveryHighChat() {
	getSearchCountDeliveryData();
	getSuppliersDefectRateAndDefectType();
	getSuppliersDeliveryTime();
	getSupplierDefectDelivery();
	getSupplierAvailability();
	getSupplierLeadTimeDays();



	//-------------------------------procurement-vendor-totalOrder start--------------------------------->>
}
function getSearchCountDeliveryData() {
	var financialYear = '2024-2025';
	var frDate = $("#fromDate4").val();
	var toDate = $("#toDate4").val();
	var org = $("#purcDelOrg").find('option:selected').text();
	var orgDiv = $("#purcDelOrgDiv").find('option:selected').text();
	var location = $("#purDeliLoc").find('option:selected').text();



	$.ajax({
		url: "procurement-vendor-SearchDeliveryData",
		data: {
			financialYear: financialYear,
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		type: "GET",
		dataType: "json",
		async: true,
		success: function(response) {
			console.log("AJAX Response:", response);

			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);

				console.log("Transformed Data:", jsonData);


				var availablityValue = 0;
				var totalOrderQty = 0;
				var totalReturnQty = 0;

				var totalDiffReceiptOrderDate = 0;
				var totalDiffReceiptExpectedDate = 0;
				var qualityScoreTotal = 0;

				if (jsonData === null) {
					$("#defectRate").text(0);
					$("#leadTimeDays").text(0);
					$("#onTimeSupplies").text(0);
					$("#suppliesWithoutDefect").text(0);
				} else {
					for (var i = 0; i < jsonData.length; i++) {
						var deliveryData = jsonData[i];
						totalOrderQty = totalOrderQty + deliveryData.orderedQtyTtl;
						totalReturnQty = totalReturnQty + deliveryData.totalReturnQtyTotal;
						totalDiffReceiptOrderDate = totalDiffReceiptOrderDate + deliveryData.diffReceipt_Order_Date;
						totalDiffReceiptExpectedDate = totalDiffReceiptExpectedDate + deliveryData.diffReceipt_Expected_Date;
						qualityScoreTotal = (qualityScoreTotal + deliveryData.quality_score);

					}
					var totalOrderCount = deliveryData.totalOrder;
					availablityValue = ((totalOrderQty - totalReturnQty) / totalOrderQty) * 100;
					var defectRate = (100 - availablityValue).toFixed(2);
					var LeadTimeDays = (totalDiffReceiptOrderDate / totalOrderCount).toFixed(2);

					var onTimeSupplies = (totalDiffReceiptExpectedDate / totalOrderCount).toFixed(2);
					var suppliesWithoutDefect = (qualityScoreTotal / totalOrderCount);



					$("#defectRate").text(defectRate);
					$("#leadTimeDays").text(LeadTimeDays);
					$("#onTimeSupplies").text(onTimeSupplies);
					$("#suppliesWithoutDefect").text(suppliesWithoutDefect.toFixed(2));
				}

				Highcharts.chart('availability', {
					chart: {
						type: 'pie',
						height: 200,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#ff5d5d', '#dfdfdf'],
					title: {
						text: 'Availability',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 100,
						margin: 0,
						style: { "fontSize": '16', "color": '#000000', fontWeight: 'normal' }
					},
					subtitle: {
						text: availablityValue.toFixed(2) + "%",
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 70,
						margin: 0,
						style: { "fontSize": '20', "color": '#000000', fontWeight: 'bold' }
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
						data: [["Availability", availablityValue], ["Defect Rate", 100 - availablityValue]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						color: '#ca7ce5',
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		},
		error: function(xhr, status, error) {
			var errorMessage = status + ': ' + error;
			console.error("AJAX request error:", errorMessage);
		}
	});
}




function getSupplierDefectDelivery() {


	var financialYear = '2024-2025';
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
	var toYear = dateRange[1]

	var fromDate = fromYear + "-04-01";
	var toDate = toYear + "-03-31";


	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	var day = currentDate.getDate().toString().padStart(2, '0');
	currentDate = year + '-' + month + '-' + day;


	var project = $("#projectDelivery").val();
	var division = $("#divisionDelivery").val();
	var vendorId = $("#supplierDelivery").val();

	var params = {
		project: project,
		financialYear: financialYear,
		fromYear: fromYear,
		toYear: toYear,
		divSearchId: division,
		vendorId: vendorId,
		fromDate4: fromDate,
		toDate4: toDate,

	};

	$.ajax({
		type: "GET",
		url: "procurement-vendor-supplierDefectRate?" + $.param(params),
		async: true,
		success: function(response) {
			console.log("Response:", response);

			if (response && response.code === "success" && response.body) {
				try {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.defectRateByMonth;

					var monthDatas = [];
					monthDatas.push(allData[0].first)
					monthDatas.push(allData[0].second)
					monthDatas.push(allData[0].third)
					monthDatas.push(allData[0].fourth)
					monthDatas.push(allData[0].fifth)
					monthDatas.push(allData[0].sixth)
					monthDatas.push(allData[0].seventh)
					monthDatas.push(allData[0].eighth)
					monthDatas.push(allData[0].ninth)
					monthDatas.push(allData[0].tenth)
					monthDatas.push(allData[0].eleventh)
					monthDatas.push(allData[0].twelfth)


					var supplierDefectRateDatas = [];
					if (new Date(currentDate) >= new Date(fromDate)) {

						if (allData[0].defectRateApr01 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateApr01);
						} else {
							supplierDefectRateDatas.push(0);
						}

						if (allData[0].defectRateMay02 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateMay02);
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateJun03 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateJun03)
						} else {
							supplierDefectRateDatas.push(0);
						}



						if (allData[0].defectRateJul04 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateJul04)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateAug05 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateAug05)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateSep06 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateSep06)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateOct07 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateOct07)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateNov08 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateNov08)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateDec09 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateDec09)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateJan10 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateJan10)
						} else {
							supplierDefectRateDatas.push(0);
						}

						if (allData[0].defectRateFeb11 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateFeb11)
						} else {
							supplierDefectRateDatas.push(0);
						}


						if (allData[0].defectRateMarch12 !== null) {
							supplierDefectRateDatas.push(allData[0].defectRateMarch12)
						} else {
							supplierDefectRateDatas.push(0);
						}

					}

					var notableDefectRateDatas = [];

					for (var i = 0; i < supplierDefectRateDatas.length; i++) {
						if (supplierDefectRateDatas[i] >= 2.5) {
							notableDefectRateDatas.push(supplierDefectRateDatas[i]);
						} else {
							notableDefectRateDatas.push(null);
						}
					}


					Highcharts.chart('supplierDefectRate', {
						chart: {
							zoomType: 'xy',
							animation: true,
							height: 180
						},
						navigation: {
							buttonOptions: {
								enabled: false
							}
						},
						title: {
							text: '',
							align: 'Center',
							style: { 'fontSize': '11px' }
						},
						subtitle: {
							text: ''
						},
						credits: { enabled: false },

						yAxis: {
							title: {
								text: ''
							},
							gridLineColor: 'transparent',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						xAxis: {
							//categories: ['Jan 2024', 'Feb 2024', 'Mar 2024', 'Apr 2024', 'May 2024', 'Jun 2024',	'Jul 2024', 'Aug 2024', 'Sep 2024', 'Oct 2024', 'Nov 2024', 'Dec 2024'],
							categories: monthDatas,
							crosshair: true,
							lineColor: '#cccccc',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						legend: {
							layout: 'horizontal',
							align: 'center',
							verticalAlign: 'center',
							itemStyle: {
								fontSize: '9px'
							},

						},
						tooltip: { enabled: false },

						series: [{
							name: 'Defect Rate',
							data: supplierDefectRateDatas,
							lineColor: 'transparent',
							marker: {
								enabled: false,
								fillColor: '#BF05FF',
								border: '#BF05FF'
							},
							lineColor: '#BF05FF',

						}, {
							name: 'Defect Rate >= 2.5',
							data: notableDefectRateDatas,
							marker: {
								symbol: 'circle',
								fillColor: '#F79C92'
							}

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
				} catch (error) {
					console.error("Error parsing JSON:", error);
				}
			} else {
				console.error("Invalid or empty response");
			}
		},
		error: function(data) {
			console.error("Ajax request failed:", data);
		}
	});
}
//-------------------------------procurement-vendor-totalOrder End--------------------------------->>


//-------------------------------procurement-vendor-returned start--------------------------------->>
function getSupplierAvailability() {

	var financialYear = '2024-2025';
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
	var toYear = dateRange[1]

	var fromDate = fromYear + "-04-01";
	var toDate = toYear + "-03-31";

	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	var day = currentDate.getDate().toString().padStart(2, '0');
	currentDate = year + '-' + month + '-' + day;


	var project = $("#projectDelivery").val();
	var division = $("#divisionDelivery").val();
	var vendorId = $("#supplierDelivery").val();

	var params = {
		project: project,
		financialYear: financialYear,
		fromYear: fromYear,
		toYear: toYear,
		divSearchId: division,
		vendorId: vendorId
	};
	$.ajax({
		type: "GET",
		url: "procurement-vendor-supplierAvaibility?" + $.param(params),
		async: true,
		success: function(response) {
			console.log("Response:", response);

			if (response && response.code === "success" && response.body) {
				try {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.availabilityByMonth;

					var monthDatas = [];
					monthDatas.push(allData[0].first)
					monthDatas.push(allData[0].second)
					monthDatas.push(allData[0].third)
					monthDatas.push(allData[0].fourth)
					monthDatas.push(allData[0].fifth)
					monthDatas.push(allData[0].sixth)
					monthDatas.push(allData[0].seventh)
					monthDatas.push(allData[0].eighth)
					monthDatas.push(allData[0].ninth)
					monthDatas.push(allData[0].tenth)
					monthDatas.push(allData[0].eleventh)
					monthDatas.push(allData[0].twelfth)

					//
					var supplierAvailabilityDatas = [];
					if (new Date(currentDate) >= new Date(fromDate)) {

						if (allData[0].availabilityApr01 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityApr01);
						} else {
							supplierAvailabilityDatas.push(0);
						}

						if (allData[0].availabilityMay02 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityMay02);
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityJun03 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityJun03)
						} else {
							supplierAvailabilityDatas.push(0);
						}



						if (allData[0].availabilityJul04 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityJul04)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityAug05 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityAug05)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilitySep06 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilitySep06)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityOct07 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityOct07)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityNov08 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityNov08)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityDec09 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityDec09)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityJan10 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityJan10)
						} else {
							supplierAvailabilityDatas.push(0);
						}

						if (allData[0].availabilityFeb11 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityFeb11)
						} else {
							supplierAvailabilityDatas.push(0);
						}


						if (allData[0].availabilityMarch12 !== null) {
							supplierAvailabilityDatas.push(allData[0].availabilityMarch12)
						} else {
							supplierAvailabilityDatas.push(0);
						}

					}

					var notableAvailabilityDatas = [];

					for (var i = 0; i < supplierAvailabilityDatas.length; i++) {
						if (supplierAvailabilityDatas[i] >= 85 && supplierAvailabilityDatas[i] <= 90) {
							notableAvailabilityDatas.push(supplierAvailabilityDatas[i]);
						} else {
							notableAvailabilityDatas.push(null);
						}
					}

					Highcharts.chart('supplierAvailability', {
						chart: {
							zoomType: 'xy',
							animation: true,
							height: 180
						},
						navigation: {
							buttonOptions: {
								enabled: false
							}
						},
						title: {
							text: '',
							align: 'Center',
							style: { 'fontSize': '11px' }
						},
						subtitle: {
							text: ''
						},
						credits: { enabled: true },

						yAxis: {
							title: {
								text: ''
							},
							gridLineColor: 'transparent',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						xAxis: {
							categories: monthDatas,
							crosshair: false,
							lineColor: '#cccccc',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						legend: {
							layout: 'horizontal',
							align: 'center',
							verticalAlign: 'center',
							itemStyle: {
								fontSize: '9px'
							},
						},
						tooltip: { enabled: false },

						series: [{
							name: 'Supplier',
							data: supplierAvailabilityDatas,
							lineColor: 'transparent',
							marker: {
								enabled: false,
								fillColor: '#BF05FF',
								border: '#BF05FF'
							},
							lineColor: '#BF05FF',

						}, {
							name: 'Supplier >= 85 && < 90',
							data: notableAvailabilityDatas,

							marker: {
								symbol: 'circle',
								fillColor: '#F79C92'
							}
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
				} catch (error) {
					console.error("Error parsing JSON:", error);
				}
			} else {
				console.error("Invalid or empty response");
			}
		},
		error: function(data) {
			console.error("Ajax request failed:", data);
		}
	});
}

//---------------------------procurement-vendor-returned End---------------------------->>

//---------------------------procurement-vendor-defectRate Start---------------------------->>
function getSupplierLeadTimeDays() {

	var financialYear = '2024-2025';
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
	var toYear = dateRange[1]

	var fromDate = fromYear + "-04-01";
	var toDate = toYear + "-03-31";

	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	var day = currentDate.getDate().toString().padStart(2, '0');
	currentDate = year + '-' + month + '-' + day;


	var project = $("#projectDelivery").val();
	var division = $("#divisionDelivery").val();
	var vendorId = $("#supplierDelivery").val();

	var params = {
		project: project,
		financialYear: financialYear,
		fromYear: fromYear,
		toYear: toYear,
		divSearchId: division,
		vendorId: vendorId
	};
	$.ajax({
		type: "GET",
		url: "procurement-vendor-supplierLeadTimeDays?" + $.param(params),
		async: true,
		success: function(response) {
			console.log("Response:", response);

			if (response && response.code === "success" && response.body) {
				try {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.LeadTimeDaysByMonth;

					var monthDatas = [];
					monthDatas.push(allData[0].first)
					monthDatas.push(allData[0].second)
					monthDatas.push(allData[0].third)
					monthDatas.push(allData[0].fourth)
					monthDatas.push(allData[0].fifth)
					monthDatas.push(allData[0].sixth)
					monthDatas.push(allData[0].seventh)
					monthDatas.push(allData[0].eighth)
					monthDatas.push(allData[0].ninth)
					monthDatas.push(allData[0].tenth)
					monthDatas.push(allData[0].eleventh)
					monthDatas.push(allData[0].twelfth)


					var supplierLeadTimeDatas = [];
					if (new Date(currentDate) >= new Date(fromDate)) {

						if (allData[0].LeadTimeDaysApr01 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysApr01);
						} else {
							supplierLeadTimeDatas.push(0);
						}

						if (allData[0].LeadTimeDaysMay02 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysMay02);
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysJun03 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysJun03)
						} else {
							supplierLeadTimeDatas.push(0);
						}



						if (allData[0].LeadTimeDaysJul04 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysJul04)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysAug05 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysAug05)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysSep06 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysSep06)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysOct07 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysOct07)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysNov08 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysNov08)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysDec09 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysDec09)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysJan10 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysJan10)
						} else {
							supplierLeadTimeDatas.push(0);
						}

						if (allData[0].LeadTimeDaysFeb11 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysFeb11)
						} else {
							supplierLeadTimeDatas.push(0);
						}


						if (allData[0].LeadTimeDaysMarch12 !== null) {
							supplierLeadTimeDatas.push(allData[0].LeadTimeDaysMarch12)
						} else {
							supplierLeadTimeDatas.push(0);
						}

					}

					var notableLeadTimeDaysDatas = [];

					for (var i = 0; i < supplierLeadTimeDatas.length; i++) {
						if (supplierLeadTimeDatas[i] >= 16) {
							notableLeadTimeDaysDatas.push(supplierLeadTimeDatas[i]);
						} else {
							notableLeadTimeDaysDatas.push(null);
						}
					}

					console.log("monthDatas---------------------------------" + monthDatas);
					console.log("supplierLeadTimeDatas----------------------" + supplierLeadTimeDatas);
					console.log("notableLeadTimeDaysDatas-------------------" + notableLeadTimeDaysDatas);

					//lead time(in days)
					Highcharts.chart('leadTime', {
						chart: {
							zoomType: 'xy',
							animation: true,
							height: 180
						},
						navigation: {
							buttonOptions: {
								enabled: false
							}
						},
						title: {
							text: '',
							align: 'Center',
							style: { 'fontSize': '11px' }
						},
						subtitle: {
							text: ''
						},
						credits: { enabled: false },

						yAxis: {
							title: {
								text: ''
							},
							gridLineColor: 'transparent',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						xAxis: {
							categories: monthDatas,
							crosshair: true,
							lineColor: '#cccccc',
							labels: {
								style: { fontSize: '10px' }
							}
						},

						legend: {
							layout: 'horizontal',
							align: 'center',
							verticalAlign: 'center',
							itemStyle: {
								fontSize: '9px'
							},
						},
						tooltip: { enabled: false },



						series: [{
							name: 'Lead Time',
							data: supplierLeadTimeDatas,
							lineColor: 'transparent',
							marker: {
								enabled: false,
								fillColor: '#BF05FF',
								border: '#BF05FF'
							},
							lineColor: '#BF05FF',
						}, {
							name: 'Lead Time > 16',
							data: notableLeadTimeDaysDatas,

							marker: {
								symbol: 'circle',
								fillColor: '#F79C92'
							}

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

				} catch (error) {
					console.error("Error parsing JSON:", error);
				}
			} else {
				console.error("Invalid or empty response");
			}
		},
		error: function(data) {
			console.error("Ajax request failed:", data);
		}
	});
}

function getSuppliersDefectRateAndDefectType() {
	var financialYear = '2024-2025';
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
	var toYear = dateRange[1]

	var fromDate = fromYear + "-04-01";
	var toDate = toYear + "-03-31";

	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	var day = currentDate.getDate().toString().padStart(2, '0');
	currentDate = year + '-' + month + '-' + day;


	var project = $("#projectDelivery").val();
	var division = $("#divisionDelivery").val();
	var vendorId = $("#supplierDelivery").val();

	var params = {
		project: project,
		financialYear: financialYear,
		fromDate: fromDate,
		toDate: toDate,
		divSearchId: division,
		vendorId: vendorId
	};

	$.ajax({
		type: "GET",
		url: "procurement-vendor-suppliersDefectRateType?" + $.param(params),
		async: true,
		success: function(response) {
			console.log("Response:", response);

			$('#supplierDefectType').empty();
			$('#DeliverySuppliersDefectRate').empty();
			if (response && response.code === "success" && response.body != null) {
				try {
					var allData = JSON.parse(response.body);
					var vendorName = [];
					var rejectedData = [];
					var impactData = [];
					var noImpactData = [];
					var defectData = [];

					var td = '';

					for (var i = 0; i < allData.length; i++) {
						var dataItem = allData[i];
						vendorName.push(dataItem.VendorName);
						rejectedData.push(dataItem.defectTypeRejectedCount);
						impactData.push(dataItem.defectTypeImpactCount);
						noImpactData.push(dataItem.defectTypeNoImpactCount);
						defectData.push(dataItem.defectRate);
						td += ('<td>' + parseFloat(dataItem.defectRate).toFixed(2) + '</td>');
					}
					$('#DeliverySuppliersDefectRate').append(td);

					Highcharts.chart('supplierDefectType', {
						chart: {
							type: 'column',
							animation: true,
							height: 222
						},
						title: {
							text: '',
						},
						navigation: {
							buttonOptions: {
								enabled: false
							}
						},
						xAxis: {
							categories: vendorName,
							lineColor: '#e8e8e8',
						},
						credits: { enabled: false },
						yAxis: {
							min: 0,
							title: {
								text: 'Defect Type'
							},
							stackLabels: {
								enabled: false
							},
							labels: {
								enabled: false,
							},
							lineColor: 'transparent',
							gridLineColor: 'transparent'
						},
						legend: {
							align: 'center',
							verticalAlign: 'bottom',
							floating: false,
						},
						plotOptions: {
							column: {
								stacking: 'normal',
								dataLabels: {
									enabled: true
								}
							}
						},
						series: [{
							name: 'Rejected',
							data: rejectedData,
							color: '#BF05FF'
						}, {
							name: 'Impact',
							data: impactData,
							color: '#F79C92'
						}, {
							name: 'No Impact',
							data: noImpactData,
							color: '#B422B6'
						}]
					});

				} catch (error) {
					console.error("Error parsing JSON:", error);
				}
			} else {
				console.error("Invalid or empty response");
			}
		},
		error: function(data) {
			console.error("Ajax request failed:", data);
		}
	});
}


////-------------------------------------------------getSuppliersDeliveryTime
function getSuppliersDeliveryTime() {
	var financialYear = '2024-2025';
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
	var toYear = dateRange[1]

	var fromDate = fromYear + "-04-01";
	var toDate = toYear + "-03-31";

	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	var day = currentDate.getDate().toString().padStart(2, '0');
	currentDate = year + '-' + month + '-' + day;



	var project = $("#projectDelivery").val();
	var division = $("#divisionDelivery").val();
	var vendorId = $("#supplierDelivery").val();

	var params = {
		project: project,
		financialYear: financialYear,
		fromDate: fromDate,
		toDate: toDate,
		divSearchId: division,
		vendorId: vendorId
	};

	$.ajax({
		type: "GET",
		url: "procurement-vendor-suppliersDeliveryTime?" + $.param(params),
		async: true,
		success: function(response) {
			console.log("Response:", response);
			$("#deliveryTime").empty();
			$("#deliveryTimeDatatable").empty();
			if (response && response.code === "success" && response.body != null) {
				try {
					var allData = JSON.parse(response.body);
					var vendorName = [];
					var ontimeData = [];
					var lateData = [];
					var earlyData = [];
					var trData = '';



					for (var i = 0; i < allData.length; i++) {
						var dataItem = allData[i];
						vendorName.push(dataItem.vendorName);
						var totalOrderCount = parseInt(dataItem.totalOrderCount);
						var ontimeCount = parseInt(dataItem.ontimeDeliveryStatusCount);
						var earlyCount = parseInt(dataItem.earlyDeliveryStatusCount);
						var lateCount = parseInt(dataItem.lateDeliveryStatusCount);

						var ontimePercentage = (ontimeCount / totalOrderCount) * 100;
						var earltPercentage = (earlyCount / totalOrderCount) * 100;
						var latePercentage = (lateCount / totalOrderCount) * 100;
						var ontimeAndEarlyTotal = (ontimePercentage + earltPercentage);

						ontimeData.push(parseInt(ontimePercentage));
						lateData.push(parseInt(latePercentage));
						earlyData.push(parseInt(earltPercentage));

						var trData = '<tr>' +
							'<td style="width: 25%">' + parseInt(ontimeAndEarlyTotal) + '%</td>' +
							'<td style="width: 25%">90%</td>' +
							'<td style="width: 50%">' +
							(ontimeAndEarlyTotal > 90 ?
								'<div class="d-flex justify-content-between">' +
								'Acceptable<span class="early"><i class="fa-solid fa-circle"></i></span>' +
								'</div>' :
								(ontimeAndEarlyTotal >= 80 && ontimeAndEarlyTotal <= 90 ?
									'<div>' +
									'Tolerable<span class="on-time"><i class="fa-solid fa-circle"></i></span>' +
									'</div>' :
									'<div class="d-flex justify-content-between">' +
									'UnAcceptable<span class="late"><i class="fa-solid fa-circle"></i></span>' +
									'</div>'
								)
							) +
							'</td>' +
							'</tr>';

						$("#deliveryTimeDatatable").append(trData);


					}

					//Delivery Time
					Highcharts.chart('deliveryTime', {
						chart: {
							type: 'bar',
							animation: true,
							height: 290,
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
							categories: vendorName,
						},
						yAxis: {
							min: 0,
							max: 100,
							title: {
								text: ''
							},
							labels: {
								enabled: false
							},
							gridLineColor: 'transparent',
							lineColor: '#cccccc'
						},
						legend: {
							reversed: true
						},
						plotOptions: {
							series: {
								stacking: 'normal',
								dataLabels: {
									enabled: true,
									format: '{y} %',
									color: '#ffffff'
								}
							}
						},
						series: [
							{
								name: 'Late',
								data: lateData,
								color: '#BF05FF'
							}
							,
							{
								name: 'Early',
								data: earlyData,
								color: '#F79C92'
							},
							{
								name: 'On-Time',
								data: ontimeData,
								color: '#B422B6'
							}
						]
					});

				} catch (error) {
					console.error("Error parsing JSON:", error);
				}
			} else {
				console.error("Invalid or empty response");
			}
		},
		error: function(data) {
			console.error("Ajax request failed:", data);
		}
	});

}


/*Saurav*/

function getTotalDelivery() {
	getSearchCountDeliveryData();
	getSuppliersDefectRateAndDefectType();
	getSuppliersDeliveryTime();
	getSupplierDefectDelivery();
	getSupplierAvailability();
	getSupplierLeadTimeDays();
}

function resetTotalDelivery() {
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

	getSearchCountDeliveryData();
	getSuppliersDefectRateAndDefectType();
	getSuppliersDeliveryTime();
	getSupplierDefectDelivery();
	getSupplierAvailability();
	getSupplierLeadTimeDays();
}
function purDeliChangeOrg() {
	getSearchCountDeliveryData();
	getSuppliersDefectRateAndDefectType();
	getSuppliersDeliveryTime();
	getSupplierDefectDelivery();
	getSupplierAvailability();
	getSupplierLeadTimeDays();
}
function purDeliChangeOrgDiv() {
	getSearchCountDeliveryData();
	getSuppliersDefectRateAndDefectType();
	getSuppliersDeliveryTime();
	getSupplierDefectDelivery();
	getSupplierAvailability();
	getSupplierLeadTimeDays();

}


