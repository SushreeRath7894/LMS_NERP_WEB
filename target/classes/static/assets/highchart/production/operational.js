function operationalHighChat() {
	productionHeadData();
	
}
		


function productionHeadData() {
	var tabvalue = $("#tabIdVal").val();
	if (tabvalue == null || tabvalue === "" || tabvalue === undefined) {
	    tabvalue = "plant";
	}
	var org = $("#oprProductionOrganization").val();
	var orgDiv = $("#oprProductionDivision").find('option:selected').text();
	var location = $("#operationalLocation").find('option:selected').text();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	//alert("toDate: " + toDate);
	
	
	$.ajax({
		type: "GET",

		url: "dashboard-operation-count1?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,

		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#noOfMachineIdOprtnl").text(allData[0].numberOfMachine);
				$("#machineBreakdownIdOprtnl").text(allData[0].machineBreakDown);
				$("#downTimeHoursIdOprtnl").text(allData[0].downtimeHours);
				$("#ooeIdOprtnl").text(allData[0].ooeCount);
				$("#oeeIdOprtnl").text(allData[0].oeeCount);
				$("#teepIdOprtnl").text(allData[0].teepCount);
				
			}
		}, error: function(data) {
			console.log(data);
		}
	})


	$.ajax({
		type: "GET",

		url: "dashboard-operation-count2?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,

		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#totalPlantsOprtnlId").text(allData[0].plantCount);
				$("#totalBrandsOprtnlId").text(allData[0].brandCount);
				$("#totalRawMaterialOprtnlId").text(allData[0].rawMaterialCount);
				$("#totalProductsOprtnlId").text(allData[0].productCount);
				$("#totalPlanVsActualOprtnlId").text(allData[0].planVsActualCost);
				$("#totalDownTimesOprtnlId").text(allData[0].downtimeCount);
				
				

			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	getAllData(tabvalue);
}


let storedId = "";
function getAllData(id) {
	storedId = id;
	
	var org = $("#oprProductionOrganization").val();
	var orgDiv = $("#oprProductionDivision").find('option:selected').text();
	var location = $("#operationalLocation").find('option:selected').text();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	$("#grid6workorder").hide();
	$("#grid5insurance").hide();
	$("#grid1assetinventory").hide();
	$("#grid2assetstock").hide();
	$("#gird3assettransit").hide();
	$("#grid4receivedss").hide();
	$("#grid7ScrapedTable").hide();

	if (id == "plant") {
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
		////////////////////////////////////////Plant
		
		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} else if (id == "brand") {
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
		////////////////////////////////////////Brand
		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} else if (id == "raw_material") {
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
		////////////////////////////////////////Raw material
		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} else if (id == "product") {
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
		////////////////////////////////////////Product
		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} else if (id == "plan_vs_actual") {
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
		////////////////////////////////////////Plan vs Actual
		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} else if (id == "down_time") {
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
		////////////////////////////////////////Downtime

		$.ajax({
			type: "GET",
			url: "dashboard-operation-getAllRecord?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + 
			"&location=" + location +"&org=" + org + "&orgDiv=" + orgDiv,
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
	} 
	
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
		headerName: 'Brand Id',
		field: 'brandId',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'Brand Name',
		field: 'brandName',
		cellStyle: { textAlign: 'left' },
		width: 160,
		pinned: 'left'
	},
	{
		headerName: 'Brand Code'
		, field: 'brandCode',
		cellStyle: { textAlign: 'left' },
		width: 190
	},
	{
		headerName: 'Category',
		field: 'brandCategory',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Organization Name',
		field: 'orgName',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Organization Division',
		field: 'orgDivision',
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
		headerName: 'Product Id',
		field: 'productId',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Product Name',
		field: 'productName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'HSN Code',
		field: 'hsnCode',
		cellStyle: { textAlign: 'left' },
		width: 190,

	},
	{
		headerName: 'Category',
		field: 'productCategory',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
	{
		headerName: 'Organization Name',
		field: 'organizationName',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
	{
		headerName: 'Organization Division',
		field: 'organizationDivision',
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
		headerName: 'Product Id',
		field: 'productId',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Product Name',
		field: 'productName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'HSN Code',
		field: 'hsnCode',
		cellStyle: { textAlign: 'left' },
		width: 190,

	},
	{
		headerName: 'Category',
		field: 'productCategory',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
	{
		headerName: 'Organization Name',
		field: 'organizationName',
		cellStyle: { textAlign: 'left' },

		width: 190
	},
	{
		headerName: 'Organization Division',
		field: 'organizationDivision',
		cellStyle: { textAlign: 'left' },

		width: 190
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
		headerName: 'Plan Id',
		field: 'planId',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Plan Date',
		field: 'planDate',
		width: 160,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Machine Name',
		field: 'machineName',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Estimated Production in TON',
		field: 'estimatedProductionInTON',
		width: 250,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Estimated Production Units',
		field: 'estimatedProductionInUnits',
		width: 250,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Actual Production in TON',
		field: 'actualProductionInTON',
		width: 250,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Actual Production Units',
		field: 'actualProductionInUnits',
		width: 250,
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
		headerName: 'Breakdown Id',
		field: 'breakDownId',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},

	{
		headerName: 'Machine Name',
		field: 'machineName',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Downtime in Minutes'
		, field: 'downtimeInMinutes',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Downtime Cause',
		field: 'downtimeCause',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	
	{
		headerName: 'Downtime Duration',
		field: 'downtimeDuration',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	
	{
		headerName: 'Organization Name',
		field: 'orgName',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Organization Division',
		field: 'orgDivision',
		width: 180,
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
		headerName: 'Plant Id',
		field: 'plantId',
		width: 170,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},

	{
		headerName: 'Plant Name',
		field: 'plantName',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	}
	,
	{
		headerName: 'Organization',
		field: 'orgName',
		cellStyle: { textAlign: 'left' },
		width: 190
	},
	
	{
		headerName: 'Phone No',
		field: 'phoneNo',
		cellStyle: { textAlign: 'left' },
		width: 190
	},
	
	
	{
		headerName: 'Registration No',
		field: 'registrationNo',
		width: 190,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'GSTIN No',
		field: 'gstinNo',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'City',
		field: 'cityName',
		width: 190,
		cellStyle: { textAlign: 'left' }
	},
	{
		headerName: 'Address',
		field: 'address',
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


