function operational() {
	OprationalHeadingData1();
	OprationalHeadingData2();
}


function OprationalHeadingData1() {

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organisation").find('option:selected').text();
	var orgDiv = $("#division").find('option:selected').text();
	var activity = 'Asset';

	$.ajax({
		type: "GET",
		url: "dashboard-asset-oprational-count?fromDate=" + fromDate + "&toDate=" + toDate + "&activity=" + activity + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				console.log("header count:", jsonData)
				$("#total_work_order").text(allData.workOrders);
				//	alert("workOrders" + allData.workOrders)
				$("#asset_inventory").text(allData.assetInventory);
				$("#asset_stock").text(allData.assetInStock);
				$("#asset_transit").text(allData.assetInTransit);
				$("#asset_damaged").text(allData.damagedAsset);
				$("#asset_inssurance").text(allData.assetInssurance);
				$("#total_scrap_asset").text(allData.scrapedAsset);

				$("#mtFail").text(allData.mtFail);
				$("#mtRepair").text(allData.mtRepair);
				$("#mtIntervence").text(allData.mtIntervence);
				$("#averagedh").text(allData.averagedh);
				$("#averagelh").text(allData.averagelh);
				$("#failureFreq").text(allData.failureFreq);
			}
		}, error: function(data) {
			console.log(data);
		}
	})
}


function OprationalHeadingData2() {

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organisation").find('option:selected').text();
	var orgDiv = $("#division").find('option:selected').text();
	var activity = 'Asset';

	$.ajax({
		type: "GET",
		url: "dashboard-asset-oprational-count-for-mean-tab?fromDate=" + fromDate + "&toDate=" + toDate + "&activity=" + activity + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#maintenanceBacklogCount").text(allData.maintenanceBacklogCount);
				$("#meanTimeToRepairCount").text(allData.meanTimeToRepairCount);
				$("#meanTimeBwtFailureCount").text(allData.meanTimeBwtFailureCount);
				$("#meanTimeToInterveneCount").text(allData.meanTimeToInterveneCount);
				$("#avgDownTimeCount").text(allData.avgDownTimeCount);
				$("#oeECount").text(allData.oeECount);
				$("#pmComplianceCount").text(allData.pmComplianceCount);
				$("#plannedMaintenanceCount").text(allData.plannedMaintenanceCount);

			}
		}, error: function(data) {
			console.log(data);
		}
	})
}

let storedId = "";
function getAllData(id) {
	storedId = id;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organisation").find('option:selected').text();
	var orgDiv = $("#division").find('option:selected').text();

	$("#grid6workorder").hide();
	$("#grid5insurance").hide();
	$("#grid1assetinventory").hide();
	$("#grid2assetstock").hide();
	$("#gird3assettransit").hide();
	$("#grid4receivedss").hide();
	$("#grid7ScrapedTable").hide();

	if (id == "total_work_order") {
		$("#grid6workorder").show().empty();
		$("#workorder").show();
		$("#assetinventory").hide();
		$("#assetstock").hide();
		$("#assettransit").hide();
		$("#receiveds").hide();
		$("#insurance").hide();
		$("#scrapedTable").hide();

		var gridDiv = document.querySelector('#grid6workorder');
		new agGrid.Grid(gridDiv, gridOptions6);

		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					$("#workorder").show();

					gridOptions6.api.setRowData([]);

					if (!allData || allData.length === 0) {
						gridOptions6.api.setRowData([]);
					} else {
						gridOptions6.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "asset_inventory") {
		$("#grid1assetinventory").show().empty();

		$("#assetinventory").show();
		$("#workorder").hide();
		$("#assetstock").hide();
		$("#assettransit").hide();
		$("#receiveds").hide();
		$("#insurance").hide();
		$("#scrapedTable").hide();

		var gridDiv = document.querySelector('#grid1assetinventory');
		new agGrid.Grid(gridDiv, gridOptions1);

		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;



					gridOptions1.api.setRowData([]);

					if (!allData || allData.length === 0) {
						gridOptions1.api.setRowData([]);
					} else {
						gridOptions1.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "asset_stock") {
		$("#grid2assetstock").show().empty();

		$("#assetstock").show();
		$("#assetinventory").hide();
		$("#workorder").hide();
		$("#assettransit").hide();
		$("#receiveds").hide();
		$("#insurance").hide();
		$("#scrapedTable").hide();

		var gridDiv = document.querySelector('#grid2assetstock');
		new agGrid.Grid(gridDiv, gridOptions2);

		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					gridOptions2.api.setRowData([]);
					if (!allData || allData.length === 0) {
						gridOptions2.api.setRowData([]);
					} else {
						gridOptions2.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "asset_transit") {
		$("#gird3assettransit").show().empty();

		$("#assettransit").show();
		$("#assetstock").hide();
		$("#assetinventory").hide();
		$("#workorder").hide();
		$("#receiveds").hide();
		$("#insurance").hide();
		$("#scrapedTable").hide();


		var gridDiv = document.querySelector('#gird3assettransit');
		new agGrid.Grid(gridDiv, gridOptions3);

		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					gridOptions3.api.setRowData([]);
					if (!allData || allData.length === 0) {
						gridOptions3.api.setRowData([]);
					} else {
						gridOptions3.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "asset_damaged") {
		$("#grid4receivedss").show().empty();

		$("#receiveds").show();
		$("#assettransit").hide();
		$("#assetstock").hide();
		$("#assetinventory").hide();
		$("#workorder").hide();
		$("#insurance").hide();
		$("#scrapedTable").hide();


		var gridDiv = document.querySelector('#grid4receivedss');
		new agGrid.Grid(gridDiv, gridOptions4);

		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					gridOptions4.api.setRowData([]);
					if (!allData || allData.length === 0) {
						gridOptions4.api.setRowData([]);
					} else {
						gridOptions4.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "asset_inssurance") {
		$("#grid5insurance").show().empty();

		$("#insurance").show();
		$("#receiveds").hide();
		$("#assettransit").hide();
		$("#assetstock").hide();
		$("#assetinventory").hide();
		$("#workorder").hide();
		$("#scrapedTable").hide();


		var gridDiv = document.querySelector('#grid5insurance');
		new agGrid.Grid(gridDiv, gridOptions5);


		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					gridOptions5.api.setRowData([]);
					if (!allData || allData.length === 0) {
						gridOptions5.api.setRowData([]);
					} else {
						gridOptions5.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "total_scrap_asset") {
		$("#grid7ScrapedTable").show().empty();

		$("#scrapedTable").show();
		$("#insurance").hide();
		$("#receiveds").hide();
		$("#assettransit").hide();
		$("#assetstock").hide();
		$("#assetinventory").hide();
		$("#workorder").hide();


		var gridDiv = document.querySelector('#grid7ScrapedTable');
		new agGrid.Grid(gridDiv, gridOptions7);


		$.ajax({
			type: "GET",
			url: "dashboard-get-all-data?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;

					gridOptions7.api.setRowData([]);
					if (!allData || allData.length === 0) {
						gridOptions7.api.setRowData([]);
					} else {
						gridOptions7.api.setRowData(allData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	}
}

function getFilterAsset() {
	getAllData(storedId);
	OprationalHeadingData1();
	OprationalHeadingData2();

}
function resetOperationTab() {
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

	$("#toDate").val(toDate);
	$("#fromDate").val(fromDate);

	getAllData(storedId);
	OprationalHeadingData1();
	OprationalHeadingData2();
}
function getAssetOperationalData() {
	getAllData(storedId);
	OprationalHeadingData1();
	OprationalHeadingData2();
}
function getAssetOperationalDataDiv() {
	getAllData(storedId);
	OprationalHeadingData1();
	OprationalHeadingData2();
}
/*column Defination*/
const columnDefs1 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'
	},
	{
		headerName: 'Asset Id',
		field: 'assetId',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'Asset type',
		field: 'assetType',
		cellStyle: { textAlign: 'left' },
		width: 160,
		pinned: 'left'
	},
	{
		headerName: 'Asset category'
		, field: 'categoryName',
		cellStyle: { textAlign: 'left' },
		width: 190
	},
	{
		headerName: 'Asset Sub category',
		field: 'subcategoryName',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Asset Item name',
		field: 'assetName',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase date',
		field: 'purchaseDate',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase no',
		field: 'purchaseNo',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},

	{
		headerName: 'Life Span',
		field: 'lifeSpan',
		width: 190,
		cellStyle: { textAlign: 'left' },
		cellRenderer: function(params) {
			return `${params.value} (days)`;
		}
	},
	{
		headerName: 'Working status',
		field: 'workingStatus',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Approve status',
		field: 'approveRequests',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Assign status',
		field: 'assignName',
		width: 190,
		cellStyle: { textAlign: 'left' },

	}

];

const gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,

	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs2 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Asset Type',
		field: 'assettype',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Asset Category',
		field: 'assetcat',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Asset Sub Category',
		field: 'assetsubcat',
		cellStyle: { textAlign: 'left' },
		width: 190,

	},
	{
		headerName: 'Asset Total Quantity',
		field: 'total',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
	{
		headerName: 'Stock Available',
		field: 'available',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
];

const gridOptions2 = {
	columnDefs: columnDefs2,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs3 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'
	},
	{
		headerName: 'Asset Id',
		field: 'assetId',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'Asset type',
		field: 'assetType',
		cellStyle: { textAlign: 'left' },
		width: 160,
		pinned: 'left'
	},
	{
		headerName: 'Asset category'
		, field: 'categoryName',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	{
		headerName: 'Asset Sub category',
		field: 'subcategoryName',
		width: 150,
		cellStyle: { textAlign: 'left' }

	},
	{
		headerName: 'Asset Item name',
		field: 'assetName',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase date',
		field: 'purchaseDate',
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase no',
		field: 'purchaseNo',
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Life Span',
		field: 'lifeSpan',
		cellRenderer: function(params) {
			return `${params.value} (days)`;
		},
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Working status',
		field: 'workingStatus',
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Approve status',
		field: 'approveRequests',
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Assign status',
		field: 'assignName',
		//field: 'invoice',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},

];

const gridOptions3 = {
	columnDefs: columnDefs3,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs4 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Asset Id',
		field: 'assetId',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Asset type',
		field: 'assetType',
		width: 160,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Asset category'
		, field: 'categoryName',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Asset Sub category',
		field: 'subcategoryName',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Asset Item name',
		field: 'assetName',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase date',
		field: 'purchaseDate',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase no',
		field: 'purchaseNo',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Life Span',
		field: 'lifeSpan',
		width: 150,
		cellStyle: { textAlign: 'left' },
		cellRenderer: function(params) {
			return `${params.value} (days)`;
		}
	},
	{
		headerName: 'Working status',
		field: 'workingStatus',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Approve status',
		field: 'approveRequests',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Assign status',
		field: 'assignName',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},

];

const gridOptions4 = {
	columnDefs: columnDefs4,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs5 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Asset Id',
		field: 'assetId',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Asset Name',
		field: 'assetName',
		width: 220,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Insurance Id'
		, field: 'insuranceId',
		cellStyle: { textAlign: 'left' },
		width: 215
	},
	{
		headerName: 'Insurance Name',
		field: 'insuranceName',
		width: 235,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Insurance Start Date',
		field: 'insuranceStartDate',
		width: 215,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Insurance End Date',
		field: 'insuranceEndDate',
		width: 215,
		cellStyle: { textAlign: 'left' },

	},

];

const gridOptions5 = {
	columnDefs: columnDefs5,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs6 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'
	},
	{
		headerName: 'Ticket Id',
		field: 'ttdmTicketId',
		width: 170,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	{
		headerName: 'Ticket Name',
		field: 'tttmTicketName',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	}
	,
	{
		headerName: 'Raised By',
		field: 'ttdmTcktRaisedBy',
		cellStyle: { textAlign: 'left' },
		width: 190
	},
	{
		headerName: 'Holder',
		field: 'ticketHolderName',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Priority',
		field: 'ttdmTicketPriority',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'Department',
		field: 'tdmDptName',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'Category',
		field: 'ttcmCategoryName',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'Subcategory',
		field: 'ttscmSubCategoryName',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'Asset Name',
		field: 'tamAssetItemName',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
];

// Grid Options
const gridOptions6 = {
	columnDefs: columnDefs6,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};
const columnDefs7 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Asset Id',
		field: 'assetId',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Asset type',
		field: 'assetType',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Asset category'
		, field: 'categoryName',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Sub category',
		field: 'subcategoryName',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Asset Item name',
		field: 'assetName',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase date',
		field: 'purchaseDate',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Purchase no',
		field: 'purchaseNo',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Life Span',
		field: 'lifeSpan',
		cellStyle: { textAlign: 'left' },
		cellRenderer: function(params) {
			return `${params.value} (days)`;
		},
		width: 180
	},
	{
		headerName: 'Working status',
		field: 'workingStatus',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Approve status',
		field: 'approveRequests',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Assign status',
		field: 'assignName',
		cellStyle: { textAlign: 'left' },
		width: 180
	},

];

const gridOptions7 = {
	columnDefs: columnDefs7,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};