function operationalHighChat() {

	productionHeadData();
	
	var id = "totalRequisition";
	getAllReport(id);
}

function productionHeadData() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organisation").find('option:selected').text();
	var orgDiv = $("#division").find('option:selected').text();
	var loc = $("#operLoc").val();

	$.ajax({
		type: "GET",
		url: "manage-dashboard-getAllHeadCount",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#totalRequisitionid").text(allData[0].requisitionCount);
				$("#totalQuotationId").text(allData[0].poSchedulesCount);
				$("#totalPurchaseOrderId").text(allData[0].poReleasedCount);
				$("#totalPurchaseTransitId").text(allData[0].poInTransitCount);
				$("#totalGrnReceiptId").text(allData[0].grnReceiptCount);
				$("#totalGrnReturnId").text(allData[0].grnReturnCount);

				$("#totalVendorId").text(allData[0].vendorCount);
				$("#valueSechudledId").text(allData[0].poScheduleValue);
				$("#valueReleasedId").text(allData[0].poReleaseValue);
				$("#valueInTransistId").text(allData[0].poReleaseValue);
				$("#valueReceivedId").text(allData[0].poGrnReceiptValue);
				$("#valueReturnedId").text(allData[0].poGrnReturnValue);
			
            
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
}


//==============================================

let storedId = "";
function getAllReport(id) {
	storedId = id;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organisation").find('option:selected').text();
	var orgDiv = $("#division").find('option:selected').text();
	var loc = $("#operLoc").val();


	$("#myGrid5").hide();
	$("#myGrid1").hide();
	$("#myGrid2").hide();
	$("#myGrid3").hide();
	$("#myGrid4").hide();
	$("#myGrid6").hide();


	$(function() {


		if (id == "totalRequisition") {

			$("#myGrid6").hide().empty();
			var gridDiv = document.querySelector('#myGrid6');
			new agGrid.Grid(gridDiv, gridOptions6);

			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
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
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#totalRequisitionid").text(length);


						if (!allData || allData === null) {
							gridOptions6.api.setRowData([]);
						} else {
							gridOptions6.api.setRowData(allData);
						}
					}
					$("#myGrid6").show();
					$("#totalRequisition").show();

					$("#myGrid1").hide().empty();
					$("#totalSchedule").hide();
					$("#myGrid2").hide().empty();
					$("#totalReleased").hide();
					$("#myGrid3").hide().empty();
					$("#totalTransist").hide();
					$("#myGrid4").hide().empty();
					$("#totalReceiveds").hide();
					$("#myGrid5").hide().empty();
					$("#totalReceivedReturn").hide();


				},
				error: function(data) {
					console.log(data);
				}
			});
		}
		else if (id == "totalPoScheduled") {

			$("#myGrid1").hide().empty();
			var gridDiv = document.querySelector('#myGrid1');
			new agGrid.Grid(gridDiv, gridOptions1);
			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
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
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#totalQuotationId").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions1.api.setRowData([]);
						} else {
							gridOptions1.api.setRowData(allData);
							//alert("jsonData------------" + jsonData)
						}
					}
					$("#myGrid1").show();
					$("#totalSchedule").show();

					$("#myGrid6").hide().empty();
					$("#totalRequisition").hide();
					$("#myGrid2").hide().empty();
					$("#totalReleased").hide();
					$("#myGrid3").hide().empty();
					$("#totalTransist").hide();
					$("#myGrid4").hide().empty();
					$("#totalReceiveds").hide();
					$("#myGrid5").hide().empty();
					$("#totalReceivedReturn").hide();


				},
				error: function(data) {
					console.log(data);
				}
			});

		} else if (id == "totoalPoReleased") {

			$("#myGrid2").hide().empty();
			var gridDiv = document.querySelector('#myGrid2');
			new agGrid.Grid(gridDiv, gridOptions2);
			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
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
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#totalPurchaseOrderId").text(length);
					
						if (!jsonData || jsonData === null) {
							gridOptions2.api.setRowData([]);
						} else {
							gridOptions2.api.setRowData(allData);
							//alert("jsonData------------" + jsonData)
						}
					}
					//$("#myGrid1").show();
					$("#myGrid2").show();
					$("#totalReleased").show();

					$("#myGrid6").hide().empty();
					$("#totalRequisition").hide();
					$("#myGrid1").hide().empty();
					$("#totalSchedule").hide();
					$("#myGrid3").hide().empty();
					$("#totalTransist").hide();
					$("#myGrid4").hide().empty();
					$("#totalReceiveds").hide();
					$("#myGrid5").hide().empty();
					$("#totalReceivedReturn").hide();


				},
				error: function(data) {
					console.log(data);
				}

			});

		} else if (id == "totalTransit") {

			$("#myGrid3").hide().empty();
			var gridDiv = document.querySelector('#myGrid3');
			new agGrid.Grid(gridDiv, gridOptions3);

			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#totalPurchaseTransitId").text(length);
						
						if (!jsonData || jsonData === null) {
							gridOptions3.api.setRowData([]);
						} else {
							gridOptions3.api.setRowData(allData);
							//alert("jsonData------------" + jsonData)
						}
					}
					$("#myGrid3").show();
					$("#totalTransist").show();

					$("#myGrid6").hide().empty();
					$("#totalRequisition").hide();
					$("#myGrid1").hide().empty();
					$("#totalSchedule").hide();
					$("#myGrid2").hide().empty();
					$("#totalReleased").hide();
					$("#myGrid4").hide().empty();
					$("#totalReceiveds").hide();
					$("#myGrid5").hide().empty();
					$("#totalReceivedReturn").hide();



				}, error: function(data) {
					console.log(data);
				}
			});
		} else if (id == "totalReceived") {

			$("#myGrid4").hide().empty();
			var gridDiv = document.querySelector('#myGrid4');
			new agGrid.Grid(gridDiv, gridOptions4);
			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#totalGrnReceiptId").text(length);
						
						if (!jsonData || jsonData === null) {
							gridOptions4.api.setRowData([]);
						} else {
							gridOptions4.api.setRowData(allData);
							//alert("jsonData------------" + jsonData)
						}
					}
					$("#myGrid4").show();
					$("#totalReceiveds").show();

					$("#myGrid6").hide().empty();
					$("#totalRequisition").hide();
					$("#myGrid1").hide().empty();
					$("#totalSchedule").hide();
					$("#myGrid2").hide().empty();
					$("#totalReleased").hide();
					$("#myGrid3").hide().empty();
					$("#totalTransist").hide();
					$("#myGrid5").hide().empty();
					$("#totalReceivedReturn").hide();

				}, error: function(data) {
					console.log(data);
				}
			});
		}
		else if (id == "totalReturned") {

			$("#myGrid5").hide().empty();
			var gridDiv = document.querySelector('#myGrid5');
			new agGrid.Grid(gridDiv, gridOptions5);
			$.ajax({
				type: "GET",
				url: "manage-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#totalGrnReturnId").text(length);
						if (!jsonData || jsonData === null) {
							gridOptions5.api.setRowData([]);
						} else {
							gridOptions5.api.setRowData(allData);
							//	alert("jsonData------------" + jsonData)
						}
					}
					$("#myGrid5").show();
					$("#totalReceivedReturn").show();

					$("#myGrid6").hide().empty();
					$("#totalRequisition").hide();
					$("#myGrid1").hide().empty();
					$("#totalSchedule").hide();
					$("#myGrid2").hide().empty();
					$("#totalReleased").hide();
					$("#myGrid3").hide().empty();
					$("#totalTransist").hide();
					$("#myGrid4").hide().empty();
					$("#totalReceiveds").hide();

				}, error: function(data) {
					console.log(data);
				}
			});
		}

		$("#delete").attr("disabled", true);
	});

}

function getFilterOperational() {
	productionHeadData();
	getAllReport(storedId);

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

	productionHeadData();
	getAllReport(storedId);
}

function getPurchaseOperationalData() {
	productionHeadData();
	getAllReport(storedId);
}
function getPurchaseOperationalDataDiv() {
	productionHeadData();
	getAllReport(storedId);
}
/*Column Def Starts*/

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
		headerName: 'REQUISITION ID',
		field: 'requisitionId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	{
		headerName: 'REQUIRED DATE',
		field: 'requiredDate',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'CREATED BY',
		field: 'createdBy',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		width: 180,
		cellStyle: { textAlign: 'left' },


	}
	,
	{
		headerName: 'MODEL/SIZE',
		field: 'modelName',
		cellStyle: { textAlign: 'left' },
		width: 120
	},
	
	
	{
		headerName: 'Unit Type',
		field: 'uomType',
		width: 100,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'UNIT QUANTITY',
		field: 'qty',
		width: 130,
		cellStyle: { textAlign: 'right' }

	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName : 'Requisition Status',
		field : "status",
		width : 130,
		cellStyle: { textAlign: 'left' },
		cellRenderer : function(params) {
			if (params.data.status == 0) { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 1) {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	},
	
	{
		headerName: 'PURPOSE',
		field: 'purpose',
		width: 260,
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
		headerName: 'QUOTATION ID',
		field: 'quotationId',
		width: 140,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	
	{
		headerName: 'VENDOR NAME',
		field: 'vendorName',
		cellStyle: { textAlign: 'left' },
		width: 200
	},
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		width: 220,
		cellStyle: { textAlign: 'left' }

	},
	{
		headerName: 'QUANTITY',
		field: 'unitQty',
		width: 120,
		cellStyle: { textAlign: 'left' }, 
	},
	
	{
		headerName: 'UNIT PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 120
	},
	{
		headerName: 'DISCOUNT %',
		field: 'discount',
		cellStyle: { textAlign: 'left' },
		width: 120
	},
	{
		headerName: 'LINE AMOUNT',
		field: 'lineTotal',
		width: 130,
		cellStyle: { textAlign: 'right' }, // Align content to the left

	},
	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		cellStyle: { textAlign: 'left' },
		width: 130
	},
	{
		headerName: 'APPROVAL STATUS',
		field: 'status',
		cellStyle: { textAlign: 'left' },
		width: 160,
		cellRenderer : function(params) {
			if (params.data.status == 'Pending') { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 'Approved') {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	},
	
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
		headerName: 'PO ID',
		field: 'poId',
		width: 100,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'VENDOR NAME'
		, field: 'vendorName',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		cellStyle: { textAlign: 'left' },
		width: 180,

	},
	{
		headerName: 'MODEL/SIZE',
		field: 'modelName',
		cellStyle: { textAlign: 'left' }, 
		width: 110
	}, 
	{
		headerName: 'Measure Unit',
		field: 'unitMeasureType',
		cellStyle: { textAlign: 'left' }, 
		width: 110
	}, 
	{
		headerName: 'QUANTITY',
		field: 'qty',
		cellStyle: { textAlign: 'left' },
		width: 100
	}, 
	 {
		headerName: 'UNIT PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 100
	},
	 {
		headerName: 'DISCOUNT %',
		field: 'discount',
		cellStyle: { textAlign: 'right' },
		width: 100
	},{
		headerName: 'CGST',
		field: 'cgst',
		cellStyle: { textAlign: 'right' }, // Align content to the left

		width: 100
	}, {
		headerName: 'SGST',
		field: 'sgst',
		cellStyle: { textAlign: 'right' },
		width: 100
	}, {
		headerName: 'IGST',
		field: 'igst',
		cellStyle: { textAlign: 'right' },
		width: 100
	}, {
		headerName: 'TAXABLE AMOUNT',
		field: 'lineTotal',
		cellStyle: { textAlign: 'right' },
		width: 120
	},
	
	{
		headerName: 'REFERENCE',
		field: 'reference',
		cellStyle: { textAlign: 'left' },
		width: 140
	},
	
	{
		headerName: 'EXPECTED DELIVERY DATE',
		field: 'expectedDeliveryDate',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdDate',
		cellStyle: { textAlign: 'left' },
		width: 130
	},
	
	{
		headerName: 'APPROVAL STATUS',
		field: 'status',
		cellStyle: { textAlign: 'left' },
		width: 160,
		cellRenderer : function(params) {
			if (params.data.status == 'Pending') { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 'Approved') {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
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
		headerName: 'PO ID',
		field: 'poId',
		width: 100,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'VENDOR NAME'
		, field: 'vendorName',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		cellStyle: { textAlign: 'left' },
		width: 180,

	},
	{
		headerName: 'MODEL/SIZE',
		field: 'modelName',
		cellStyle: { textAlign: 'left' }, 
		width: 110
	}, 
	{
		headerName: 'Measure Unit',
		field: 'unitMeasureType',
		cellStyle: { textAlign: 'left' }, 
		width: 110
	}, 
	{
		headerName: 'QUANTITY',
		field: 'qty',
		cellStyle: { textAlign: 'left' },
		width: 100
	}, 
	 {
		headerName: 'UNIT PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 100
	},
	 {
		headerName: 'DISCOUNT %',
		field: 'discount',
		cellStyle: { textAlign: 'right' },
		width: 100
	},{
		headerName: 'CGST',
		field: 'cgst',
		cellStyle: { textAlign: 'right' }, // Align content to the left

		width: 100
	}, {
		headerName: 'SGST',
		field: 'sgst',
		cellStyle: { textAlign: 'right' },
		width: 100
	}, {
		headerName: 'IGST',
		field: 'igst',
		cellStyle: { textAlign: 'right' },
		width: 100
	}, {
		headerName: 'TAXABLE AMOUNT',
		field: 'lineTotal',
		cellStyle: { textAlign: 'right' },
		width: 120
	},
	
	{
		headerName: 'REFERENCE',
		field: 'reference',
		cellStyle: { textAlign: 'left' },
		width: 140
	},
	
	{
		headerName: 'EXPECTED DELIVERY DATE',
		field: 'expectedDeliveryDate',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdDate',
		cellStyle: { textAlign: 'left' },
		width: 130
	},
	
	{
		headerName: 'APPROVAL STATUS',
		field: 'status',
		cellStyle: { textAlign: 'left' },
		width: 160,
		cellRenderer : function(params) {
			if (params.data.status == 'Pending') { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 'Approved') {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
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
		headerName: 'GRN RECEIPT NO.',
		field: 'grnId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'GRN RECEIPT DATE',
		field: 'grnReceiptDate',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'VENDOR NAME',
		field: 'vendorName',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'PO ID',
		field: 'poId',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		width: 150,
		cellStyle: { textAlign: 'right' }, // Align content to the left

	},
	{
		headerName: 'MODEL/SIZE',
		field: 'modelSize',
		width: 150,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'GRN RECEIPT QTY',
		field: 'quantity',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	,
	{
		headerName: 'MEASUREMENT UNIT ',
		field: 'uomName',
		width: 150,
		cellStyle: { textAlign: 'left' },

	},
	,
	{
		headerName: 'DISCOUNT %',
		field: 'discount',
		width: 150,
		cellStyle: { textAlign: 'left' },
	}
	,
	{
		headerName: 'LINE TOTAL',
		field: 'lineTotal',
		width: 150,
		cellStyle: { textAlign: 'right' },
	}
	,
	{
		headerName: 'CGST',
		field: 'cgst',
		width: 150,
		cellStyle: { textAlign: 'right' },
	},
	
	{
		headerName: 'SGST',
		field: 'sgst',
		width: 150,
		cellStyle: { textAlign: 'right' },
	},
	{
		headerName: 'IGST',
		field: 'igst',
		width: 150,
		cellStyle: { textAlign: 'right' },
	},
	
	{
		headerName: 'TAXABLE AMOUNT',
		field: 'taxableAmount',
		width: 150,
		cellStyle: { textAlign: 'right' },
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
		headerName: 'GRN RETURN ID',
		field: 'grnReturnId',
		width: 120,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'PO ID',
		field: 'poId',
		width: 100,
		cellStyle: { textAlign: 'left' },
		//pinned: 'left'

	},
	{
		headerName: 'VENDOR NAME',
		field: 'vendorName',
		cellStyle: { textAlign: 'left' },
		width: 180,
	},
	{
		headerName: 'GRN RECEIPT ID',
		field: 'grnReceiptId',
		width: 140,
		cellStyle: { textAlign: 'left' },
	},
	
	
	
	{
		headerName: 'ITEM NAME',
		field: 'itemName',
		width: 180,
		cellStyle: { textAlign: 'left' },
	},
	
	{
		headerName: 'RETURN QTY',
		field: 'quantity',
		cellStyle: { textAlign: 'left' },
		width: 100
	},
	{
		headerName: 'MEASUREMENT UNIT',
		field: 'uom',
		width: 150,
		cellStyle: { textAlign: 'right' }, // Align content to the left

	},
	{
		headerName: 'TOTAL AMOUNT',
		field: 'TAII_Line_Total',
		cellStyle: { textAlign: 'right' }, 
		width: 140
	},
	
	{
		headerName: 'GRN RETURN DATE',
		field: 'grnReturnDate',
		width: 140,
		cellStyle: { textAlign: 'left' },
	},
	
	{
		headerName: 'APPROVAL STATUS',
		field: 'status',
		cellStyle: { textAlign: 'left' },
		width: 140,
		cellRenderer : function(params) {
			if (params.data.status == 'Pending') { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 'Approved') {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
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