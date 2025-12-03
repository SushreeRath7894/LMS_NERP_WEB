function analytics() {

	var fromDate = $("#fromDate4").val();
	var toDate = $("#toDate4").val()
	var org = $("#analyticsOrg").find('option:selected').text();
	var orgDiv = $("#analyticsDiv").find('option:selected').text();
	var date = toDate;
	var parts = date.split("-");
	var day = parts[0];
	var month = parts[1];
	var year = parts[2];


	var formattedDate = year + "-" + month + "-" + day;

	$("#assetDisposalDate").val(formattedDate);
	$("#endOfLifeMethodDate").val(formattedDate);
	$("#eligibleDate").val(formattedDate);



	//eam Incomplete Assets
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-incomplete-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);

				var allData = jsonData;
				var categoryList = [];
				var recordList = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryList.push(item.categoryName);
					recordList.push(item.totalCount);
				}



				Highcharts.chart('eamIncompleteAssets', {
					chart: {
						type: 'column',
						animation: true,
						height: 230
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categoryList,//['Computers', 'Printers', 'Server', 'Database', 'Web Server', 'Windows Server', 'Linux Server', 'Monitor', 'Unix Server', 'Others'],
						title: {
							text: 'Model Category'
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Asset Count'
						},
					},

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: recordList,//[200, 30, 32, 43, 32, 12, 17, 10, 18, 10],
						color: '#bf05ff'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-incomplete-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var tableBody = $('#incompleteAssetTable');
				tableBody.empty(); // Clear existing table rows           

				data.forEach(function(asset) {
					var row = '<tr>' +
						'<td>' + asset.categoryName + '</td>' +
						'<td>' + asset.subCatName + '</td>' +
						'<td>' + asset.assetId + '</td>' +
						'<td>' + asset.assetName + '</td>' +
						'<td>' + asset.locationName + '</td>' +
						'<td>' + asset.purchaseNo + '</td>' +
						'</tr>';
					tableBody.append(row);
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});
*/

	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-incomplete-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#frequncyAssetGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string
				console.log("Data is coming=========> ", data)

				$("#incompleteAssetGrid").show().empty();

				var gridDiv = document.querySelector('#incompleteAssetGrid');
				new agGrid.Grid(gridDiv, incompleteGrid); // Initialize the grid

				incompleteGrid.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					incompleteGrid.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					incompleteGrid.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});



	//eam Eligible For Refresh Assets
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-eligible-for-refresh-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);

				var allData = jsonData;
				var categoryList = [];
				var recordList = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryList.push(item.categoryName);
					recordList.push(item.assetCount);
				}
				Highcharts.chart('eamEligibleForRefreshAssets', {
					chart: {
						type: 'column',
						animation: true,
						height: 230
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categoryList,//['Laptop-Dell', 'Cleaning Machine', 'Printer', 'AC', 'Fan', 'Furniture'],
						title: {
							text: 'Model Category'
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Asset Count'
						},
					},

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: recordList,//[56, 46, 26, 76, 58, 39],
						color: '#f58d68'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});

	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-eligible-for-refresh-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#eligibleAssetGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string

				console.log("Data is coming=========> ", data)

				$("#eligibleAssetGrid").show().empty();

				var gridDiv = document.querySelector('#eligibleAssetGrid');
				new agGrid.Grid(gridDiv, eligibilityGrid);
				
				
				

				eligibilityGrid.api.setRowData([]);

				if (data && data.length > 0) {
					eligibilityGrid.api.setRowData(data);
				} else {
					eligibilityGrid.api.setRowData([]);
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});

*/

	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-eligible-for-refresh-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#eligibleAssetGrid, #eligibleAssetGrid1").hide().empty(); // Hide and empty both grids initially

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string

				console.log("Data is coming=========> ", data);


				$("#eligibleAssetGrid").show().empty();

				var gridDiv1 = document.querySelector('#eligibleAssetGrid');
				var gridOptions1 = { ...eligibilityGrid };
				new agGrid.Grid(gridDiv1, gridOptions1);


				$("#eligibleAssetGrid1").show().empty();
				var gridDiv2 = document.querySelector('#eligibleAssetGrid1');
				var gridOptions2 = { ...eligibilityGrid };
				new agGrid.Grid(gridDiv2, gridOptions2);

				if (data && data.length > 0) {
					gridOptions1.api.setRowData(data);
					gridOptions2.api.setRowData(data);
				} else {
					gridOptions1.api.setRowData([]);
					gridOptions2.api.setRowData([]);
				}


			}
		},
		error: function(data) {
			console.log(data);
		}
	});

	//eam Active Assets Not Discovered
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-active-assets-not-discovered?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData;
				var categoryList = [];
				var recordList = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryList.push(item.categoryName);
					recordList.push(item.categoryCount);
				}

				var monthDatas = [];
				Highcharts.chart('eamActiveAssetsNotDiscovered', {
					chart: {
						type: 'bar',
						animation: true,
						height: 230
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categoryList,//['Computers', 'Printers', 'Server', 'Database', 'Web Server', 'Windows Server', 'Linux Server', 'Monitor', 'Unix Server', 'Others'],
						title: {
							text: 'Model Category'
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Asset Count'
						},
					},

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: recordList,//[200, 30, 32, 43, 32, 12, 17, 10, 18, 10],
						color: '#9792e8'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-active-assets-not-discovered?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv= " + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var tableBody = $('#notDiscoveredAssetTbody');

				tableBody.empty(); // Clear existing table rows            

				data.forEach(function(asset) {
					if (asset.categoryName !== null) {
						var row = '<tr>' +
							'<td>' + asset.categoryName + '</td>' +
							'<td>' + asset.subCatName + '</td>' +
							'<td>' + asset.assetId + '</td>' +
							'<td>' + asset.assetName + '</td>' +
							'<td>' + asset.locationName + '</td>' +
							'<td>' + asset.notDiscoveredDays + '</td>' +
							'</tr>';
					}
					tableBody.append(row);
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});*/
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-active-assets-not-discovered?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#notDiscoveredAssetGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string
				console.log("Data is coming=========> ", data)

				$("#notDiscoveredAssetGrid").show().empty();

				var gridDiv = document.querySelector('#notDiscoveredAssetGrid');
				new agGrid.Grid(gridDiv, notDiscoveredGrid); // Initialize the grid

				notDiscoveredGrid.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					notDiscoveredGrid.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					notDiscoveredGrid.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});





	//eam Asset Incident Frequency
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-asset-incident-frequency?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData;
				var categoryList = [];
				var recordList = [];
				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryList.push(item.categoryName);
					recordList.push(item.assetCount);
				}
				Highcharts.chart("eamAssetIncidentFrequency", {
					chart: {
						animation: true,
						height: 230,
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
					yAxis: {
						title: {
							text: "",
						},
					},
					credits: { enabled: false },

					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						categories: categoryList,
					},
					plotOptions: {
						series: {
							label: {
								connectorAllowed: false,
							},
						},
					},

					series: [
						{
							type: "line",
							name: "Incidents",
							data: recordList,
							color: '#bf05ff'
						}
					],

					responsive: {
						rules: [
							{
								condition: {
									maxWidth: 500,
								},
								chartOptions: {
									legend: {
										layout: "horizontal",
										align: "center",
										verticalAlign: "bottom",
									},
								},
							},
						],
					},
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-asset-incident-frequency?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#frequncyAssetGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string

				$("#frequncyAssetGrid").show().empty();

				var gridDiv = document.querySelector('#frequncyAssetGrid');
				new agGrid.Grid(gridDiv, frequencyGrid); // Initialize the grid

				frequencyGrid.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					frequencyGrid.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					frequencyGrid.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});




	//eam Lifecycle Overview
	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-lifecycle-overview?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.warrantyExtendedCount;
				var yearDatas = [];
				var warrantyDatas = [];
				var extendedWarrantyDatas = [];

				yearDatas.push(allData[0].thisyear);
				yearDatas.push(allData[0].secondYear);
				yearDatas.push(allData[0].thirdYear);
				yearDatas.push(allData[0].fourthYear);
				yearDatas.push(allData[0].fifthYear);
				yearDatas.push(allData[0].greaterThanFifthYear);

				warrantyDatas.push(allData[0].thisYearWarrantyCount);
				warrantyDatas.push(allData[0].secondYearWarrantyCount);
				warrantyDatas.push(allData[0].thirdYearWarrantyCount);
				warrantyDatas.push(allData[0].fourthYearWarrantyCount);
				warrantyDatas.push(allData[0].fifththYearWarrantyCount);
				warrantyDatas.push(allData[0].greaterToFifththYearWarrantyCount);

				extendedWarrantyDatas.push(allData[0].thisYearExtendedWarrantyCount);
				extendedWarrantyDatas.push(allData[0].secondYearExtendedWarrantyCount);
				extendedWarrantyDatas.push(allData[0].thirdYearExtendedWarrantyCount);
				extendedWarrantyDatas.push(allData[0].fourthYearExtendedWarrantyCount);
				extendedWarrantyDatas.push(allData[0].fifthYearExtendedWarrantyCount);
				extendedWarrantyDatas.push(allData[0].greaterToFifthYearExtendedWarrantyCount);


				Highcharts.chart("eamLifecycleOverview", {
					chart: {
						animation: true,
						height: 230,
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
					yAxis: {
						title: {
							text: "",
						},
					},
					credits: { enabled: false },

					xAxis: {
						tickLength: 0,
						tickWidth: 0,
						categories: yearDatas,
					},
					plotOptions: {
						series: {
							label: {
								connectorAllowed: false,
							},
						},
					},

					series: [
						{
							type: "line",
							name: "End Of Support",
							data: warrantyDatas,
							color: '#bf05ff'
						},
						{
							type: "line",
							name: "End Of Extended Support",
							data: extendedWarrantyDatas,
							color: "#f79c92",
						}
					],

					responsive: {
						rules: [
							{
								condition: {
									maxWidth: 500,
								},
								chartOptions: {
									legend: {
										layout: "horizontal",
										align: "center",
										verticalAlign: "bottom",
									},
								},
							},
						],
					},
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});




	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-lifecycle-overview?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var tableBody = $('#supportExtendedSupportTbody');

				tableBody.empty(); // Clear existing table rows            

				data.forEach(function(asset) {
					if (asset.assetId !== null) {
						var row = '<tr>' +
							'<td>' + asset.categoryName + '</td>' +
							'<td>' + asset.subCatName + '</td>' +
							'<td>' + asset.assetId + '</td>' +
							'<td>' + asset.assetName + '</td>' +
							'<td>' + (asset.locationName !== null ? asset.locationName : 'N/A') + '</td>' +
							'<td>' + asset.supportEndDate + '</td>';

						if (asset.totalCountSupport > 2) {
							row += '<td>' + asset.extendedSupportEndDate + '</td>';
						} else {
							row += '<td>N/A</td>';
						}

						row += '</tr>';
					}

					tableBody.append(row);
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});*/


	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-lifecycle-overview?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#supportExtendedSupportGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string
				console.log("Data is coming=========> ", data)

				$("#supportExtendedSupportGrid").show().empty();

				var gridDiv = document.querySelector('#supportExtendedSupportGrid');
				new agGrid.Grid(gridDiv, supportExtendedGrid); // Initialize the grid

				supportExtendedGrid.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					supportExtendedGrid.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					supportExtendedGrid.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});





	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-asset-disposal-status?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var tableBody = $('#disposalTrData');

				tableBody.empty(); // Clear existing table rows            

				data.forEach(function(asset) {
					var row = '<tr>' +
						'<td>' + asset.assetId + '</td>' +
						'<td>' + asset.assetName + '</td>' +
						'<td>' + (asset.categoryName ? asset.categoryName : 'N/A') + '</td>' +
						'<td>' + (asset.subCatName ? asset.subCatName : 'N/A') + '</td>' +
						'<td>' + (asset.locationName ? asset.locationName : 'N/A') + '</td>' +
						'<td>' + asset.status + '</td>' +
						'<td>' + asset.remark + '</td>' +
						'</tr>';
					tableBody.append(row);
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});*/

	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-asset-disposal-status?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#disposalGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string
				console.log("Data is coming=========> ", data)

				$("#disposalGrid").show().empty();

				var gridDiv = document.querySelector('#disposalGrid');
				new agGrid.Grid(gridDiv, disposalGridOption); // Initialize the grid

				disposalGridOption.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					disposalGridOption.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					disposalGridOption.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});







	/*$.ajax({
		url: "dashboard-analytics-reports-table-eligible-refresh-assets?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		method: 'GET',
		dataType: 'json',
		success: function(data) {
			var tableBody = $('#tableeligiblerefreshassets');
			tableBody.empty(); // Clear existing table rows

			data.forEach(function(asset) {
				var row = '<tr>' +
					'<td>' + (asset.category || 'null') + '</td>' +
					'<td>' + (asset.subCategory || 'null') + '</td>' +
					'<td>' + (asset.assetId || 'null') + '</td>' +
					'<td>' + (asset.name || 'null') + '</td>' +
					'<td>' + (asset.location || 'null') + '</td>' +
					'<td>' + (asset.withoutPOGRN || 'null') + '</td>' +
					'</tr>';
				tableBody.append(row);
			});
		},
		error: function(error) {
			console.error('Error fetching data:', error);
		}
	});*/



	/*$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-end-life-method?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var data = JSON.parse(response.body);
				var tableBody = $('#endOfLifeAssetTable');

				tableBody.empty(); // Clear existing table rows            

				data.forEach(function(asset) {
					var row = '<tr>' +
						'<td>' + asset.categoryName + '</td>' +
						'<td>' + asset.subCatName + '</td>' +
						'<td>' + asset.assetId + '</td>' +
						'<td>' + asset.assetName + '</td>' +
						'<td>' + asset.locationName + '</td>' +
						'<td>' + asset.status + '</td>' +
						'</tr>';
					tableBody.append(row);
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});*/

	$.ajax({
		type: "GET",
		url: "dashboard-analytics-reports-table-end-life-method?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			$("#endOfLifeAssetGrid").hide().empty();

			if (response.code === "success") {
				var data = JSON.parse(response.body); // Parse the JSON string
				console.log("Data is coming=========> ", data)

				$("#endOfLifeAssetGrid").show().empty();

				var gridDiv = document.querySelector('#endOfLifeAssetGrid');
				new agGrid.Grid(gridDiv, endOfLifeGridOption); // Initialize the grid

				endOfLifeGridOption.api.setRowData([]); // Clear existing row data

				if (data && data.length > 0) {
					endOfLifeGridOption.api.setRowData(data); // Set the row data with parsed JSON
				} else {
					endOfLifeGridOption.api.setRowData([]); // Handle empty data case
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	});



}


function getAnalyticsFilterWithDate() {
	analytics();
}

function resetAnalyticsTab() {
	var today = new Date();

	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		// Otherwise, set the from year to the current year
		var fromYear = today.getFullYear();
	}

	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
		+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();

	$("#toDate5").val(toDate);
	$("#fromDate5").val(fromDate);
	analytics();
}
function getAllAnalyticsDataOrg() {
	analytics();
}

function getAllAnalyticsDataOrgDiv() {
	analytics();
}

/*Agrid for all tr td table*/

var columnDefs = [
	{ headerName: "Asset ID", field: "assetId" },
	{ headerName: "Asset Name", field: "assetName" },
	{ headerName: "Sub Category Name", field: "subCatName" },
	{ headerName: "Category Name", field: "categoryName" },
	{ headerName: "Location Name", field: "locationName" },
	{ headerName: "Frequency Count", field: "frequencyCount" }
];



var frequencyGrid = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};

var columnDefsElig = [
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub-Category", field: "subCatName" },
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Name", field: "assetName" },
	{ headerName: "Location", field: "locationName", width: 400 },
	{ headerName: "End Of Life", field: "remainingSpanDay" }
];



var eligibilityGrid = {
	columnDefs: columnDefsElig,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};

var columnDefs112 = [
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub-Category", field: "subCatName" },
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Name", field: "assetName" },
	{ headerName: "Location", field: "locationName" },
	{ headerName: "Without PO/GRN", field: "purchaseNo" }
];

var incompleteGrid = {
	columnDefs: columnDefs112,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};


var columnDefs113 = [
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub-Category", field: "subCatName" },
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Name", field: "assetName" },
	{ headerName: "Location", field: "locationName" },
	{ headerName: "Not Dicovered(Days)", field: "notDiscoveredDays" }
];

var notDiscoveredGrid = {
	columnDefs: columnDefs113,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};

var columnDefs114 = [
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub-Category", field: "subCatName" },
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Name", field: "assetName" },
	{ headerName: "Location", field: "locationName" },
	{ headerName: "End Of  Support", field: "supportEndDate" },
	{
		headerName: 'Extended Support End Date', field: 'extendedSupportEndDate',
		cellRenderer: function(params) {
			if (params.data.totalCountSupport > 2) {
				return params.data.extendedSupportEndDate ? params.data.extendedSupportEndDate : 'N/A';
			} else {
				return 'N/A';
			}
		}
	}];



var supportExtendedGrid = {
	columnDefs: columnDefs114,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};


var columnDefs115 = [
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Asset Name", field: "assetName" },
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub Category", field: "subCatName" },
	{ headerName: "Location", field: "locationName" },
	{ headerName: "Status", field: "status" },
	{ headerName: "Remarks", field: "remark" }
];

var disposalGridOption = {
	columnDefs: columnDefs115,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};

var columnDefs116 = [
	{ headerName: "Category", field: "categoryName" },
	{ headerName: "Sub-Category", field: "subCatName" },
	{ headerName: "Asset Id", field: "assetId" },
	{ headerName: "Name", field: "assetName" },
	{ headerName: "Location", field: "locationName", width: 400 },
	{ headerName: "Status", field: "status" }

];

var endOfLifeGridOption = {
	columnDefs: columnDefs116,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		cellStyle: { textAlign: 'left' }
	},
	rowSelection: 'single'
};


