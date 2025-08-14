function valuation() {

	var fromDate = $("#fromDate4").val();
	var toDate = $("#toDate4").val();
	var org = $("#valuationOrg").find('option:selected').text();
	var orgDiv = $("#valuationOrgDiv").find('option:selected').text();


	//eam VALUE OF ASSETS added by Ganesh
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-value-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var totalAssetsPrice = allData.assetAmount;

				Highcharts.chart('assetValueOfAssets', {
					chart: {
						type: 'pie',
						height: 110,
						margin: 0,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#f58d68', '#dfdfdf'],
					title: {
						text: totalAssetsPrice,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: ''
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
							shadow: false
						}
					},
					series: [{
						name: '',
						data: [["", 100], ["", 0]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//eam NET ASSET VALUE pie
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-net-asset-value?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalAssetsNetPrice = allData.netAssetAmount;

				Highcharts.chart('assetNetAssetValuePie', {
					chart: {
						type: 'pie',
						height: 110,
						margin: 0,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#bf05ff', '#dfdfdf'],
					title: {
						text: totalAssetsNetPrice,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: ''
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
							shadow: false
						}
					},
					series: [{
						name: '',
						data: [["", 100], ["", 0]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});




	//eam Net Purchase Value
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-net-purchase-value?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalPOAmount = allData.poAmount;


				Highcharts.chart('assetNetPurchaseValue', {
					chart: {
						type: 'pie',
						height: 110,
						margin: 0,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#deaaf0', '#dfdfdf'],
					title: {
						text: totalPOAmount,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: ''
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
							shadow: false
						}
					},
					series: [{
						name: '',
						data: [["", 100], ["", 0]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});


	//eam Net Scrap Value
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-net-scrap-value?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalScrapAssetAmount = allData.scrapAssetAmount;

				Highcharts.chart('assetNetScrapValue', {
					chart: {
						type: 'pie',
						height: 110,
						margin: 0,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#9792e8', '#dfdfdf'],
					title: {
						text: totalScrapAssetAmount,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: ''
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
							shadow: false
						}
					},
					series: [{
						name: '',
						data: [["", 100], ["", 0]],
						size: '120%',
						innerSize: '80%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});




	//Asset Depriciation Value
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-asset-depriciation-value?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalAssetPrice = 0;
				var firstYearDepreciationTotal = 0;
				var secondYearDepreciationTotal = 0;
				var thirdYearDepreciationTotal = 0;
				var fourthYearDepreciationTotal = 0;
				var fifthYearDepreciationTotal = 0;
				var sixthYearDepreciationTotal = 0;
				var seventhYearDepreciationTotal = 0;
				var eightYearDepreciationTotal = 0;
				var ninthYearDepreciationTotal = 0;
				var depreciationListForNineYearList = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					totalAssetPrice += parseInt(item.TAM_Asset_Price);
					firstYearDepreciationTotal += parseInt(item.firstYearDepreciation);
					secondYearDepreciationTotal += parseInt(item.secondYearDepreciation);
					thirdYearDepreciationTotal += parseInt(item.thirdYearDepreciation);
					fourthYearDepreciationTotal += parseInt(item.fourthYearDepreciation);
					fifthYearDepreciationTotal += parseInt(item.fifthYearDepreciation);
					sixthYearDepreciationTotal += parseInt(item.sixthYearDepreciation);
					seventhYearDepreciationTotal += parseInt(item.seventhYearDepreciation);
					eightYearDepreciationTotal += parseInt(item.eightYearDepreciation);
					ninthYearDepreciationTotal += parseInt(item.ninthYearDepreciation);
				}


				depreciationListForNineYearList.push(totalAssetPrice);

				depreciationListForNineYearList.push(-firstYearDepreciationTotal);
				depreciationListForNineYearList.push(-secondYearDepreciationTotal);
				depreciationListForNineYearList.push(-thirdYearDepreciationTotal);

				depreciationListForNineYearList.push(-fourthYearDepreciationTotal);
				depreciationListForNineYearList.push(-fifthYearDepreciationTotal);
				depreciationListForNineYearList.push(-sixthYearDepreciationTotal);

				depreciationListForNineYearList.push(-seventhYearDepreciationTotal);
				depreciationListForNineYearList.push(-eightYearDepreciationTotal);
				depreciationListForNineYearList.push(-ninthYearDepreciationTotal);

				var currentYear = new Date().getFullYear();

				var yearsList = [];
				for (var i = 0; i < 9; i++) {
					yearsList.push(currentYear + i);
				}

				Highcharts.chart('assetAssetDepriciationValue', {
					chart: {
						type: 'waterfall'
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					title: {
						text: ''
					},
					xAxis: {
						type: 'category',
						title: {
							text: 'Year'
						}
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					legend: {
						enabled: false
					},
					tooltip: {
						pointFormat: '<b>{point.y:,.0f}</b> '
					},
					credits: {
						enabled: false
					},
					series: [{
						data: (function() {
							var names = yearsList;
							var values = depreciationListForNineYearList;//[25000, -6250, -4688, -3516, -2637, -1978, -1483, -1112, -834];
							var colors = ['#BF05FF', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92'];

							// Combine the separate arrays into the required format
							var data = [];
							for (var i = 0; i < names.length; i++) {
								data.push({
									name: names[i],
									y: values[i],
									color: colors[i]
								});
							}
							return data;
						})(),
						dataLabels: {
							enabled: true
						},
						pointPadding: 0
					}]
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	});




	//eam scrap value
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-scrap-value-month-wise?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var data = JSON.parse(response.body);
				const categoryCounts = {};
				const monthNames = new Set();  // Initialize an empty set for MonthNames

				// Define an array of color codes
				const colors = ['#B422B6', '#22B4B6', '#B6B422', '#22B642', '#B62222', '#4222B6', '#22B66A'];

				$.each(data, function(index, entry) {
					const monthSequence = entry.MonthSequence;
					const category = entry.categoryName;
					const count = entry.CategoryCount;

					// Add the MonthName to the set
					monthNames.add(entry.MonthName);

					if (!categoryCounts[category]) {
						categoryCounts[category] = [];
					}

					categoryCounts[category][monthSequence - 1] = count;

					for (let i = 0; i < monthSequence; i++) {
						if (!categoryCounts[category][i]) {
							categoryCounts[category][i] = 0;
						}
					}
				});

				const seriesDataArray = [];
				Object.keys(categoryCounts).forEach((category, index) => {
					const counts = categoryCounts[category];

					// Get color for the category
					const color = colors[index % colors.length];

					const seriesData = {
						name: category,
						data: counts,
						color: color
					};

					seriesDataArray.push(seriesData);
				});

				const seriesDataList = seriesDataArray;
				const monthNamesArray = Array.from(monthNames);
				console.log("seriesDataList-------------", seriesDataList);


				Highcharts.chart('assetScrapValue', {
					chart: {
						type: 'bar',
						animation: true,
						height: 278.5
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
					xAxis: {
						categories: monthNamesArray,//['August', 'July', 'June', 'May', 'April'],
						title: {
							text: null
						},
						gridLineWidth: 1,
						lineWidth: 0
					},
					yAxis: {
						min: 0,
						title: {
							text: '',
						},
						labels: {
							overflow: 'justify'
						},
						gridLineWidth: 0
					},
					plotOptions: {
						bar: {

							dataLabels: {
								enabled: true,
								style: { fontSize: '7' }
							},
							groupPadding: 0.1
						}
					},
					credits: {
						enabled: false
					},
					series: seriesDataList
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});





	//eam Net Asset Value
	$.ajax({
		type: "GET",
		url: "dashboard-valuation-net-asset-values-month-wise?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {


				var data = JSON.parse(response.body);
				const categoryCounts = {};
				const monthNames = new Set();  // Initialize an empty set for MonthNames

				// Define an array of color codes
				const colors = ['#B422B6', '#22B4B6', '#B6B422', '#22B642', '#B62222', '#4222B6', '#22B66A'];

				$.each(data, function(index, entry) {
					const monthSequence = entry.MonthSequence;
					const category = entry.categoryName;
					const count = entry.CategoryCount;

					// Add the MonthName to the set
					monthNames.add(entry.MonthName);

					if (!categoryCounts[category]) {
						categoryCounts[category] = [];
					}

					categoryCounts[category][monthSequence - 1] = count;

					for (let i = 0; i < monthSequence; i++) {
						if (!categoryCounts[category][i]) {
							categoryCounts[category][i] = 0;
						}
					}
				});

				const seriesDataArray = [];
				Object.keys(categoryCounts).forEach((category, index) => {
					const counts = categoryCounts[category];

					// Get color for the category
					const color = colors[index % colors.length];

					const seriesData = {
						name: category,
						data: counts,
						color: color
					};

					seriesDataArray.push(seriesData);
				});

				const seriesDataList = seriesDataArray;
				const monthNamesArray = Array.from(monthNames);
				console.log("seriesDataList-------------", seriesDataList);

				Highcharts.chart('assetNetAssetValue', {
					chart: {
						type: 'bar',
						animation: true,
						height: 278.5
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
					xAxis: {
						categories: monthNamesArray,
						title: {
							text: null
						},
						gridLineWidth: 1,
						lineWidth: 0
					},
					yAxis: {
						min: 0,
						title: {
							text: '',
						},
						labels: {
							overflow: 'justify'
						},
						gridLineWidth: 0
					},
					plotOptions: {
						bar: {
							dataLabels: {
								enabled: true,
								style: { fontSize: '7' }
							},
							groupPadding: 0.1
						}
					},
					credits: {
						enabled: false
					},
					series: seriesDataList
				});


			}
		}, error: function(data) {
			console.log(data);
		}
	});
}

function getValuationFilterWithDate() {
	valuation();
}


function resetValuationTab() {
	var today = new Date();

	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}

	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
		+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();

	$("#toDate4").val(toDate);
	$("#fromDate4").val(fromDate);
	valuation();
}

function getValuationOrgData() {
	valuation();
}

function getValuationOrgDivData() {
	valuation();
}