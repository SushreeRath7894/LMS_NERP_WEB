/*For Summary Tab*/
function qualityHighChat() {
	
	callingAllQualityFunction();
	supplierQualityRating();
	getLossDefectSuppliers();
	lossDueToDefect();
}


function callingAllQualityFunction() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-spend-under-management",
		data: {
			org: org,
			orgDiv: orgDiv,
		},
		async: true,
		success: function(response) {
			console.log("Response:", response);

			if (response.code === "success") {
				try {
					var jsonData = JSON.parse(response.body);
					console.log("jsonData Data============:", jsonData);

					var projects = [];
					var years = [];
					var grandTotals = [];

					for (var i = 0; i < jsonData.length; i++) {
						projects.push(jsonData[i].project);
						years.push(JSON.stringify(jsonData[i].year));
						grandTotals.push(JSON.stringify(jsonData[i].yearlySpending));

						console.log('Projects', projects);
						console.log('Years', years);
						console.log('GrandTotals', grandTotals);
					}

					// Create series dynamically based on projects
					var seriesData = projects.map(function(project, index) {
						return {
							name: project,
							data: JSON.parse(grandTotals[index])
								.map(function(yearlyData) {
									return {
										x: yearlyData.year,
										y: yearlyData.grandTotal
									};
								})
						};
					});



					Highcharts.chart('spendUnderManagement', {
						chart: {
							zoomType: 'xy',
							animation: true,
							height: 228
						},
						title: {
							text: 'Spend Under Management',
							align: 'left',
							style: { 'fontSize': '13px' }
						},
						subtitle: {
							text: 'by Project',
							align: 'left',
							style: { 'fontSize': '10px' }
						},
						credits: { enabled: false },

						yAxis: {
							title: {
								text: ''
							},
						},

						xAxis: {
							categories: [
								'Jan 2024', 'Feb 2024', 'Mar 2024', 'Apr 2024', 'May 2024', 'Jun 2024',
								'Jul 2024', 'Aug 2024', 'Sep 2024', 'Oct 2024', 'Nov 2024', 'Dec 2024'
							],
							crosshair: true,
							lineColor: '#cccccc'
						},

						legend: {
							align: 'center',
							verticalAlign: 'bottom',
							floating: false,
							backgroundColor: 'transparent'
						},
						navigation: {
							buttonOptions: {
								enabled: false
							}
						},

						/*   plotOptions: {
							   series: {
								   label: {
									   connectorAllowed: false
								   },
								   pointStart: 2010
							   }
						   },
			   */
						series: [{
							name: 'ER-289',
							data: [43934, 48656, 65165, 81827, 11243, 14283,
								17533, 15174, 15517, 16154, 15610],
							color: '#BF05FF'
						}, {
							name: 'ER-569',
							data: [24916, 37941, 29742, 29851, 32490, 30282,
								38121, 36885, 33726, 34243, 31050],
							color: '#F79C92'
						}, {
							name: 'LA-273',
							data: [11744, 30000, 16005, 19771, 20185, 24377,
								32147, 30912, 29243, 29213, 25663],
							color: '#B422B6'
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
			}
		},
		error: function(data) {
			console.log(data);
		}
	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-return-cost-analysis",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {
				$("#returnCostAnalysis").empty();
				var allData = JSON.parse(response.body);
				var vendorName = [];
				var costReturnedData = [];
				var rejectionData = [];
				if (allData.length > 0) {

					for (var i = 0; i < allData.length; i++) {
						var dataItem = allData[i];

						vendorName.push(dataItem.VendorName);
						rejectionData.push(dataItem.rejectionRate);
						var returnAmount = parseInt(dataItem.totalReturnAmount);

						var newItem = {
							y: (returnAmount / 1000),
							color: "#F79C92"
						};

						costReturnedData.push(newItem);
					}
					Highcharts.chart("returnCostAnalysis", {
						chart: {
							zoomType: "xy",
							animation: true,
							height: 262,
						},
						colors: ["#bf05ff"],
						title: {
							text: "",
						},
						subtitle: {
							text: "",
						},
						credits: {
							enabled: false,
						},
						navigation: {
							buttonOptions: {
								enabled: false,
							},
						},
						xAxis: [
							{
								categories: vendorName,
								crosshair: true,
								lineColor: "#cccccc",
								labels: {
									rotation: 0,
									style: {
										fontSize: "11px",
									},
								},
							},
						],
						yAxis: [
							{
								labels: {
									format: "",
									style: {
										display: "none",
									},
								},
								title: {
									text: "Return Costs",
									style: {
										display: "none",
									},
								},
							},
							{
								title: {
									text: "Rejection Rates",
									style: {
										display: "none",
									},
								},
								labels: {
									format: "",
									style: {
										display: "none",
									},
								},
								opposite: false,
							},
						],
						tooltip: {
							shared: false,
						},
						legend: {
							align: "center",
							verticalAlign: "bottom",
							floating: false,
							backgroundColor: "transparent",
						},
						series: [
							{
								name: "Return Costs",
								type: "column",
								color: "#F79C92",
								yAxis: 1,
								data: costReturnedData,
								dataLabels: {
									enabled: true,
									rotation: 0,
									color: "#000000",
									align: "center",
									verticalAlign: "center",
									format: "{point.y:.1f}",
									style: {
										fontSize: "10px",
									},
								},
								dataLabels: {
									enabled: true,
									rotation: 0,
									color: "#000000",
									align: "center",
									verticalAlign: "center",
									format: "{point.y:.1f}K",
									style: {
										fontSize: "10px",
									},
								},
							},
							{
								name: "Rejection Rate",
								type: "spline",
								data: rejectionData,
								color: "#bf05ff",
								dataLabels: {
									enabled: true,
									rotation: 0,
									color: "#FFFFFF",
									backgroundColor: "#bf05ff",
									align: "right",
									format: "{point.y:.1f}",
									style: {
										fontSize: "10px",
									},
								},
								marker: {
									lineWidth: 2,
									fillColor: "#bf05ff",
								},
							},
						],
					});
				} else {
					$('#returnCostAnalysis').html('<div class="no-data-message">No data found</div>');
					console.log("No valid data received.");
				}
			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	$.ajax({
		url: "procurement-vendor-count-vendor-month-wise",
		data: {
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
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);

				if (jsonData === null) {
					$("#supplierCount").text(0);
					$("#valueOrdered").text(0);
					$("#valueRejected").text(0);
					$("#rejectedRate").text(0);

					$("#returnVendorCost").text(0);
					$("#spendUnderManagementValue").text(0);
					$("#emergencyPurchaseRatio").text(0);
				} else {
					var countData = jsonData[0];

					$("#supplierCount").text(countData.totalVendorCount || 0);
					$("#valueOrdered").text((parseFloat(countData.totalTaxableAmount) || 0).toFixed(2));
					$("#valueRejected").text((parseFloat(countData.totalRejectedAmount) || 0).toFixed(2));
					$("#rejectedRate").text((parseFloat(countData.rejectionRate) || 0).toFixed(2));
					$("#returnVendorCost").text((parseFloat(countData.totalReturnedAmount) || 0).toFixed(2));
					$("#spendUnderManagementValue").text((parseFloat(countData.spendUnderManagementValue) || 0).toFixed(2));
					$("#emergencyPurchaseRatio").text(countData.emergencyPurchaseRatio || 0);



				}
			}
		},
		error: function(status, error) {
			var errorMessage = status + ': ' + error;
			console.error("AJAX request error:", errorMessage);
		}
	});
}

//==========================================================
function supplierQualityRating() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-vendor-supplier-quality-rating",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				console.log("jsonData", jsonData);

				$("#supplierQualityRatingTable").show();
				$("#supplierQualityRatingList").empty();

				var totalOrdered = 0;
				var totalReturned = 0;
				var totalAvailability = 0;
				var totalDefectRate = 0;
				var totalQualityScore = 0;


				$("#supplierQualityRatingList").empty();
				if (jsonData != null && jsonData.length > 0) {
					for (var i = 0; i < jsonData.length; i++) {
						var row = '<tr>'
							+ '<td width="12" style="text-align: center !important;">' + jsonData[i].VendorName + '</td>'
							+ '<td width="18" style="text-align: right;">'
							+ (parseFloat(jsonData[i].totalOrderAmount) / 1000).toFixed(2) + "K" + '</td>'

						if (parseFloat(jsonData[i].returned).toFixed(2) < 5) {
							row += '<td width="16" style="text-align: right; background-color: #AED581  !important;">'
								+ parseFloat(jsonData[i].returned).toFixed(2) + '</td>';
						} else {
							row += '<td width="16" style="text-align: right; background-color: #E8F5E9   !important;">'
								+ parseFloat(jsonData[i].returned).toFixed(2) + '</td>';
						}


						if (parseFloat(jsonData[i].available).toFixed(2) > 90) {
							row += '<td width="14" style="text-align: right; background-color: #AED581  !important;">'
								+ parseFloat(jsonData[i].available).toFixed(2) + '</td>';
						} else {
							row += '<td width="14" style="text-align: right; background-color: #E8F5E9   !important;">'
								+ parseFloat(jsonData[i].available).toFixed(2) + '</td>';
						}



						if (parseFloat(jsonData[i].defect_rate).toFixed(2) < 5) {
							row += '<td width="15" style="text-align: right; background-color: #AED581  !important;">'
								+ parseFloat(jsonData[i].defect_rate).toFixed(2) + '</td>';
						} else {
							row += '<td width="15" style="text-align: right; background-color: #E8F5E9   !important;">'
								+ parseFloat(jsonData[i].defect_rate).toFixed(2) + '</td>';
						}


						if (parseFloat(jsonData[i].quality_score).toFixed(2) > 90) {
							row += '<td width="15" style="text-align: right; background-color: #80CBC4  !important;">'
								+ parseFloat(jsonData[i].quality_score).toFixed(2) + '</td>';
						} else if (parseFloat(jsonData[i].quality_score).toFixed(2) >= 85 && parseFloat(jsonData[i].quality_score).toFixed(2) <= 90) {
							row += '<td width="15" style="text-align: right; background-color: #FFFF00 !important;">'
								+ parseFloat(jsonData[i].quality_score).toFixed(2) + '</td>';
						} else {
							row += '<td width="15" style="text-align: right; background-color: #d4331e !important;">'
								+ parseFloat(jsonData[i].quality_score).toFixed(2) + '</td>';
						}

						row += '</tr>';
						$("#supplierQualityRatingList").append(row);


						// Accumulate totals
						totalOrdered += (parseInt(jsonData[i].totalOrderAmount));
						totalReturned += parseInt(jsonData[i].returned);
						totalAvailability += parseInt(jsonData[i].available);
						totalDefectRate += parseInt(jsonData[i].defect_rate);
						totalQualityScore += parseInt(jsonData[i].quality_score);
					}
				} else {
					var noDataMessage = '<tr><td colspan="7" class="text-center">No data found</td></tr>';
					$("#supplierQualityRatingList").append(noDataMessage); // Ensure it's using the correct ID
				}

				var totalRows = jsonData.length;
				var avgReturned = (totalReturned / totalRows).toFixed(2);
				var avgAvailability = (totalAvailability / totalRows).toFixed(2);
				var avgDefectRate = (totalDefectRate / totalRows).toFixed(2);
				var avgQualityScore = (totalQualityScore / totalRows).toFixed(2);

				// Add the row with average values
				var totalRow = '<tr style="text-align: center; font-weight: bold;">' +
					'<td width="12" style="text-align: center !important;"> </td>' +
					'<td width="18" style="text-align: right;">' + (totalOrdered / 1000).toFixed(2) + "K" + '</td>' +
					'<td width="16" style="text-align: right;">' + avgReturned + '%</td>' +
					'<td width="14" style="text-align: right">' + avgAvailability + '%</td>' +
					'<td width="15" style="text-align: right">' + avgDefectRate + '%</td>' +
					'<td width="15" style="text-align: right">' + avgQualityScore + '%</td>' +
					'</tr>';
				$("#supplierQualityRatingList").append(totalRow);
			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	var mon = $("#fromDate2").val();
	var yr = $("#toDate2").val();

	var div = $("#division1").find('option:selected').text();




	Highcharts.chart('sampleHeatMap', {
		chart: {
			height: 337,
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
				name: 'High Impact',
				value: 4,
				color: '#BF05FF',
			}, {
				name: 'Medium Impact',
				value: 2,
				color: '#CA7CE5',
			}, {
				name: 'Low Impact',
				value: 2,
				color: '#F79C92',
				dataLabels: {
					enabled: true,
				},
			}],
			dataLabels: {
				enabled: true,
				style: {
					color: '#FFFFFF',
				},
				format: '<span>{point.name} ({point.value}M)</span>',
			},
		}]
	});

}

function getLossDefectSuppliers() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-loss-defect-products",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//loss due to defect products
	Highcharts.chart('lossDefectProducts', {

		chart: {
			type: 'heatmap',
			marginTop: 40,
			marginBottom: 10,
			plotBorderWidth: 1,
			height: 300,
		},

		exporting: {
			enabled: false
		},
		title: {
			text: '',
			style: {
				fontSize: '1em'
			}
		},
		xAxis: {
			categories: [
				'Data 1', 'Data 2', 'Data 3', 'Data 4', 'Data 5',
			],
			opposite: true
		},
		yAxis: {
			categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
			title: null,
			reversed: true
		},
		accessibility: {
			point: {
				descriptionFormat: '{(add index 1)}. ' +
					'{series.xAxis.categories.(x)} sales ' +
					'{series.yAxis.categories.(y)}, {value}.'
			}
		},
		colorAxis: {
			min: 0,
			minColor: '#FFFFFF',
			maxColor: '#BF05FF',
			height: '76%'
		},
		legend: {
			align: 'right',
			layout: 'vertical',
			margin: 0,
			verticalAlign: 'middle',
			y: 25,
			symbolHeight: 280
		},

		tooltip: {
			format: '<b>{series.xAxis.categories.(point.x)}</b> sold<br>' +
				'<b>{point.value}</b> items on <br>' +
				'<b>{series.yAxis.categories.(point.y)}</b>'
		},

		series: [{
			name: 'Sales per employee',
			borderWidth: 1,
			borderColor: '#F2CEFF',
			data: [
				[0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67],
				[1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48],
				[2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52],
				[3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16],
				[4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115]
			],
			dataLabels: {
				enabled: true,
				color: '#000000',
				style: {
					textOutline: false
				}
			}
		}],
		responsive: {
			rules: [{
				condition: {
					maxWidth: 500
				},
				chartOptions: {
					yAxis: {
						labels: {
							format: '{substr value 0 1}'
						}
					}
				}
			}]
		}

	});





	$.ajax({
		type: "GET",
		url: "procurement-dashboard-loss-defect-suppliers",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//loss due to defects by supplier
	Highcharts.chart('lossDefectSuppliers', {

		chart: {
			type: 'heatmap',
			marginTop: 40,
			marginBottom: 10,
			plotBorderWidth: 1,
			height: 300,
		},

		exporting: {
			enabled: false
		},
		title: {
			text: '',
			style: {
				fontSize: '1em'
			}
		},
		xAxis: {
			categories: [
				'Data 1', 'Data 2', 'Data 3', 'Data 4', 'Data 5',
			],
			opposite: true
		},
		yAxis: {
			categories: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
			title: null,
			reversed: true
		},
		accessibility: {
			point: {
				descriptionFormat: '{(add index 1)}. ' +
					'{series.xAxis.categories.(x)} sales ' +
					'{series.yAxis.categories.(y)}, {value}.'
			}
		},
		colorAxis: {
			min: 0,
			minColor: '#FFFFFF',
			maxColor: '#BF05FF',
			height: '76%'
		},
		legend: {
			align: 'right',
			layout: 'vertical',
			margin: 0,
			verticalAlign: 'middle',
			y: 25,
			symbolHeight: 280
		},

		tooltip: {
			format: '<b>{series.xAxis.categories.(point.x)}</b> sold<br>' +
				'<b>{point.value}</b> items on <br>' +
				'<b>{series.yAxis.categories.(point.y)}</b>'
		},

		series: [{
			name: 'Sales per employee',
			borderWidth: 1,
			borderColor: '#F2CEFF',
			data: [
				[0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67],
				[1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48],
				[2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52],
				[3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16],
				[4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115]
			],
			dataLabels: {
				enabled: true,
				color: '#000000',
				style: {
					textOutline: false
				}
			}
		}],
		responsive: {
			rules: [{
				condition: {
					maxWidth: 500
				},
				chartOptions: {
					yAxis: {
						labels: {
							format: '{substr value 0 1}'
						}
					}
				}
			}]
		}

	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-state-wise-loss",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//State wise loss due to defects
	(async () => {

		const topology = await fetch('/extend/json/us-all.topo.json').then(response => response.json());

		const data = await fetch('/extend/json/us-population-density.json').then(response => response.json());



		// Make codes uppercase to match the map data
		data.forEach(function(p) {
			p.code = p.code.toUpperCase();
		});

		// Instantiate the map
		Highcharts.mapChart('stateWiseLoss', {
			credits: {
				enabled: false
			},
			chart: {
				map: topology,
				height: '250px'
			},

			title: {
				text: ''
			},

			exporting: {
				enabled: true,
			},

			legend: {
				layout: 'horizontal',
				borderWidth: 0,
				backgroundColor: 'rgba(255,255,255,0.85)',
				floating: true,
				verticalAlign: 'top',
				y: 0,
			},

			mapNavigation: {
				enabled: true
			},

			colorAxis: {
				min: 1,
				type: 'logarithmic',
				minColor: '#EEEEFF',
				maxColor: '#000022',
				stops: [
					[0, '#FFD9D5'],
					[0.5, '#BF05FF'],
					[1, '#F4DEFC']
				]
			},

			series: [{
				accessibility: {
					point: {
						valueDescriptionFormat: '{xDescription}, {point.value} people per square kilometer.'
					}
				},
				animation: {
					duration: 1
				},
				data: data,
				joinBy: ['postal-code', 'code'],
				dataLabels: {
					enabled: false,
					color: '#FFFFFF',
					format: '{point.code}'
				},
				name: 'Population density',
				tooltip: {
					pointFormat: '{point.code}: {point.value}/km²'
				}
			}]
		});
	})();

}



/*Saurav*/
function getQualityFilter() {
	callingAllQualityFunction();
	supplierQualityRating();
	getLossDefectSuppliers();
	lossDueToDefect();
}

function resetQualityFilter() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate3").val(fromDate);
	$("#toDate3").val(toDate);

	callingAllQualityFunction();
	supplierQualityRating();
	getLossDefectSuppliers();
	lossDueToDefect();
}


/*Function for Defect Rate Tabs */
function lossDueToDefect() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-trend-of-loss",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//trend of loss due to defectas
	Highcharts.chart('trendOfLoss', {
		chart: {
			type: 'areaspline',
			height: '250px',
			marginTop: 20,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 4),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},

		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},

		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});
}

function defectRate() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-trend-of-defect",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//trends of defect rate
	Highcharts.chart('trendOfDefect', {
		chart: {
			type: 'areaspline',
			height: '250px',
			marginTop: 20,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 4),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},

		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},

		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

}

function rorInbound() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-trend-of-inbound",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	//trends of ror inbound
	Highcharts.chart('trendOfInbound', {
		chart: {
			type: 'areaspline',
			height: '250px',
			marginTop: 20,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 4),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},

		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},

		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

}

function rorOutbound() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-trend-of-outbound",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//trend of ror outbound
	Highcharts.chart('trendOfOutbound', {
		chart: {
			type: 'areaspline',
			height: '250px',
			marginTop: 20,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 4),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},

		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},

		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});
}

function complaintFrequency() {

	var frDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	var org = $("#qualProfileOrg").find('option:selected').text();
	var orgDiv = $("#qualProfileOrgDiv").find('option:selected').text();
	var location = $("#profileLocation").find('option:selected').text();

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-trend-of-frequency",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	//trend of complaint frequency
	Highcharts.chart('trendOfFrequency', {
		chart: {
			type: 'areaspline',
			height: '250px',
			marginTop: 20,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2020, 9),
			max: Date.UTC(2021, 4),
			tickInterval: 3 * 30 * 24 * 36e5,
			labels: {
				formatter: function() {
					const date = new Date(this.value);
					const month = date.getUTCMonth();
					const year = date.getUTCFullYear();
					const quarter = Math.floor(month / 3) + 1;
					return `QTR ${quarter} ${year}`;
				}
			},
			title: {
				text: ''
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},

		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},

		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2020, 9), 30],
				[Date.UTC(2020, 12), 38.28],
				[Date.UTC(2021, 3, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});
}

/*For profile Tab*/
function getProfileData() {

	var frDate = $("#fromDate13").val();
	var toDate = $("#toDate13").val();
	var org = $("#qualSummaryOrg").find('option:selected').text();
	var orgDiv = $("#qualSummaryOrgDiv").find('option:selected').text();
	var location = $("#summaryLocation").find('option:selected').text();



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-loss-by-suppliers",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//loss incurred by suppliers
	Highcharts.chart('lossBySuppliers', {
		chart: {
			type: 'areaspline',
			height: 236,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 2, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: ''
			},
			min: 16,
			tickAmount: 6
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},
		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});



	$.ajax({
		type: "GET",
		url: "procurement-dashboard-defect-type-distribution",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//defect type distribution
	Highcharts.chart('defectTypeDistribution', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie',
			height: 256,
		},
		title: {
			text: ''
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
					'#BF05FF',
					'#CA7CE5'
				],
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<span>{point.name}</span><br>' +
						'<span>{point.percentage:.0f} %</span>',
					connectorColor: 'transparent',
					distance: 4,
				},
				center: ['50%', '50%'],
				size: '70%'
			}
		},
		series: [{
			name: 'Share',
			data: [{
				name: 'High Impact',
				y: 40
			},
			{
				name: 'Medium Impact',
				y: 33
			},
			{
				name: 'Low Impact',
				y: 27
			}
			]
		}],
		credits: {
			enabled: false
		}
	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-distribution-issues-remarks",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//Distribution of issues and remarks
	Highcharts.chart('distributionIssuesRemarks', {
		chart: {
			type: 'heatmap',
			marginTop: 40,
			marginBottom: 10,
			plotBorderWidth: 1,
			height: 256,
		},
		title: { text: '' },
		xAxis: {
			// categories: ['95+', '90+', '80+', '70+', 'Below 70'],
			categories: ['Data A', 'Data B', 'Data C', 'Data D', 'Data E'],
			opposite: true,
		},
		yAxis: {
			// categories: ['0-3 Y', '3-5 Y', '5-10 Y', '15-20 Y', '20+ Y'],
			categories: ['Data 1', 'Data 2', 'Data 3', 'Data 4', 'Data 5'],
			title: null,
			reversed: true
		},
		accessibility: {
			point: {
				descriptionFormat: '{(add index 1)}. ' +
					'{series.xAxis.categories.(x)} sales ' +
					'{series.yAxis.categories.(y)}, {value}.'
			}
		},
		colorAxis: {
			reversed: false,
			min: 0,
			stops: [
				[0, '#FFD9D5'],
				[0.5, '#BF05FF'],
				[1, '#F4DEFC']
			]
		},
		legend: {
			align: 'right',
			layout: 'vertical',
			margin: 0,
			verticalAlign: 'middle',
			y: 25,
			symbolHeight: 280
		},
		tooltip: {
			format: '<strong>{series.xAxis.categories.(point.x)}</strong> sold<br>' +
				'<strong>{point.value}</strong> items on <br>' +
				'<strong>{series.yAxis.categories.(point.y)}</strong>'
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
			name: 'Sales per employee',
			borderWidth: 1,
			borderColor: '#F2CEFF',
			data: [[0, 0, 4], [0, 1, 6], [0, 2, 7], [0, 3, 8], [0, 4, 7],
			[1, 0, 11], [1, 1, 12], [1, 2, 23], [1, 3, 43], [1, 4, 21],
			[2, 0, 14], [2, 1, 12], [2, 2, 32], [2, 3, 34], [2, 4, 29],
			[3, 0, 30], [3, 1, 20], [3, 2, 21], [3, 3, 19], [3, 4, 17],
			[4, 0, 23], [4, 1, 20], [4, 2, 44], [4, 3, 15], [4, 4, 8]
			],
			dataLabels: {
				enabled: false,
				color: '#000000'
			}
		}],

		responsive: {
			rules: [{
				condition: {
					maxWidth: 500
				},
				chartOptions: {
					yAxis: {
						labels: {
							format: '{substr value 0 1}'
						}
					}
				}
			}]
		}

	});

	/*For tr td table  suppliers summary for the product*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-supplier-summary-product",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

}

function getFilterQualityProfile() {
	getProfileData()
}
function getResetQualityProfile() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate13").val(fromDate);
	$("#toDate13").val(toDate);

	getProfileData();

}

function getDefectTypesData() {

	var frDate = $("#fromDate14").val();
	var toDate = $("#toDate14").val();
	var org = $("#qualDefTypesOrg").find('option:selected').text();
	var orgDiv = $("#qualDefTypesOrgDiv").find('option:selected').text();
	var location = $("#defTypeLocation").find('option:selected').text();


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-distribution-defects",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	//Defect type distributions for defect types
	Highcharts.chart('defectTypeDistributionInDefects', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie',
			height: 218,
		},
		title: {
			text: ''
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
					'#BF05FF',
					'#CA7CE5'
				],
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<span>{point.name}</span><br>' +
						'<span>{point.percentage:.0f} %</span>',
					connectorColor: 'transparent',
					distance: 4,
				},
				center: ['50%', '50%'],
				size: '100%'
			}
		},
		series: [{
			name: 'Share',
			data: [{
				name: 'Text A',
				y: 40
			},
			{
				name: 'Text B',
				y: 33
			},
			{
				name: 'Text C',
				y: 27
			}
			]
		}],
		credits: {
			enabled: false
		}
	});


	$.ajax({
		type: "GET",
		url: "procurement-dashboard-defect-rate-trends",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
	//Defect rate trends
	Highcharts.chart('defectRateTrends', {
		chart: {
			type: 'areaspline',
			height: 220,
			marginBottom: 30,
		},
		title: {
			text: ''
		},
		xAxis: {
			type: 'datetime',
			min: Date.UTC(2022, 0, 1),
			max: Date.UTC(2022, 5, 1),
			tickInterval: 30 * 24 * 36e5,
		},
		yAxis: {
			title: {
				text: ''
			},
			floor: 15,
			ceiling: 60,
			tickAmount: 10
		},
		tooltip: {
			headerFormat: '<b>{series.name}</b><br>',
			pointFormat: '{point.x:%e. %b}: {point.y:.2f} INR'
		},
		plotOptions: {
			areaspline: {
				marker: {
					enabled: true
				},
				color: '#BF05FF',
			}
		},
		series: [{
			name: "Series Name 2020-2021",
			data: [
				[Date.UTC(2022, 0, 1), 30],
				[Date.UTC(2022, 1, 1), 38.28],
				[Date.UTC(2022, 2, 1), 15],
				[Date.UTC(2022, 3, 1), 59.97],
				[Date.UTC(2022, 4, 1), 39.97],
				[Date.UTC(2022, 5, 1), 19.97],
			]
		}],
		legend: { enabled: false },
		credits: { enabled: false },
		exporting: { enabled: false },
	});

	/*DEFECT TYPE DISTRIBUTION*/

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-defect-type-distribution-table",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*BOTTOM SUPPLIERS BY DEFECT RATE*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-bottom-suppliers-defect",
		data: {
			fromDate: frDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: location
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

}

/*Deefect tsbs end here*/


function getFilterDefTypes() {
	getDefectTypesData();
}

function getResetDefTypes() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate14").val(fromDate);
	$("#toDate14").val(toDate);
	getDefectTypesData();
}

/*onchange function for summary tab organisation*/

function purQualSummaryOrg() {
	callingAllQualityFunction();
	supplierQualityRating();
	getLossDefectSuppliers();
	lossDueToDefect();
}

function purQualSummaryOrgDiv() {
	callingAllQualityFunction();
	supplierQualityRating();
	getLossDefectSuppliers();
	lossDueToDefect();
}

/*onchange function for profile tab organisation*/
function purQualProfileOrg() {
	getProfileData();
}

function purQualProfileOrgDiv() {
	getProfileData();
}
/*onchange function for Defect types tab organisation*/
function purQualDefTypeOrg() {
	getDefectTypesData();
}

function purQualDefTypeOrgDiv() {
	getDefectTypesData();
}


