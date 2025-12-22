$(function() {
	CKEDITOR.replace('qutDescription');

	pno = 1;
	viewSalesOrder();
	var gridDiv = document.querySelector('#mySalesGrid');
	new agGrid.Grid(gridDiv, salesGridOptions);

	var gridDiv = document.querySelector('#myPackageGrid');
	new agGrid.Grid(gridDiv, packageGridOptions);

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);

	var gridDiv = document.querySelector('#itemDetailsGrid');
	new agGrid.Grid(gridDiv, itemDetailsOptions);

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);

	$("#packageSearchDiv").addClass("d-none");
	$('#docTbl').on('click', '.rmv1', function() {

		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
		openDeleteConfirm();

	});


	$('#mySAGrid').hide();
	var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
	var today = new Date();
	var formattedDate = ("0" + today.getDate()).slice(-2) + "-" +
		("0" + (today.getMonth() + 1)).slice(-2) + "-" +
		today.getFullYear();
	$("#orderReceiveDate").val(formattedDate);
	$("#orderReceiveDateCalendar").val(formattedDate);

	$("#orderReceiveDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		value: formattedDate
	}).on("change", function() {
		$('#orderReceiveDate').val($(this).val());
	});

	$('#orderReceiveDate').blur(function() {
		$("#orderReceiveDateCalendar").val($(this).val());
	});

	$("#expectedShipmentDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#expectedShipmentDate').val($(this).val());
	})
	$('#expectedShipmentDate').blur(
		function() {
			$("#expectedShipmentDateCalendar").val($(this).val());
	});
	$("#packagingDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		maxDate : new Date()
	}).on("change", function() {
		$('#packagingDate').val($(this).val());
	})
	$('#packagingDate').blur(
		function() {
			$("#packagingDateCalendar").val($(this).val());
	});

	$(".br-s-btn").hide();
	$('#project').select2({
		placeholder: "Select",
		allowClear: true
	});
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
	itemDetailsOptions.api.setRowData([]);
	$("#addshippingAddressSec").addClass("d-none");
});
var pno;

function viewSalesOrder() {
	var pages;
	var pageno = pno;

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-packages-get-sales-order?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewSalesOrder;
		console.log("dataALl=================", allData);
		salesGridOptions.api.setRowData(allData);

		var totalRowCount = salesGridOptions.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);

		if (allData.length > 0) {
			console.log("AAAAAAllll", allData[0].totalPageno)
			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;

		}

	});
}

function amountFormatter(value) {
	if (value !== null && value !== undefined) {
		var parts = value.toString().split('.');
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}
var columnDefsPackage = [{
	//headerCheckboxSelection : false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 8,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Package",
	field: "salePackageId",
	width: 140,

},
{
	headerName: "Sales Order ID",
	field: "salesOrder",
	width: 150,
}, {

	headerName: 'Customer Name',
	field: "custName",
	width: 150,
}, {
	headerName: 'Package Date',
	field: "qutUpdatedOn",
	width: 100,
}, {
	headerName: 'Po Id',
	field: "poId",
	width: 100,
}, {
	headerName: 'Challan',
	field: "dchallanStatus",
	width: 100,
	cellRenderer: function(params) {
		if (params.data.dchallanStatus == "Generated") {
			return '<div style="color:#0642f5">' + params.data.dchallanStatus + '</div>';
		} else if (params.data.dchallanStatus == "Partial Generated") {
			return '<div style="color:var(--mainColor)">' + params.data.dchallanStatus + '</div>';
		} else {
			return '<div style="color:#ff8242">' + params.data.dchallanStatus + '</div>';
		}
	}


}, {

	headerName: 'Quantity',
	field: "quantity",
	width: 100,
}, {

	headerName: 'Pending Qty',
	field: "pendingQty",
	width: 100,

}
];

var packageGridOptions = {
	columnDefs: columnDefsPackage,
	rowSelection: 'single',

	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,

	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	},
	getRowNodeId: function(data) {
		return data.salePackageId;
	},
	onSelectionChanged: rowSelectPackage,
	onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 1000);
	},

};

function rowSelectPackage() {
	var selectedNodes = packageGridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	nextTab('packageInfLid');

	if (selectedData.length > 0) {
		var salePackageId = selectedData[0].salePackageId;
		var dchallanStatus = selectedData[0].dchallanStatus;




		editSalesPackage(salePackageId);
		cancelItemDetails();

		if (dchallanStatus == "Generated") {
			$('#deletePackage').addClass('d-none');
			$('#savePackage').addClass('d-none');
			$('#cacelPackageGrid').addClass('d-none');
			//$('#packItem').addClass('d-none');
			$('#pacakgeBtn').removeClass('d-none');
		} else {
			$('#deletePackage').removeClass('d-none');
			$('#savePackage').addClass('d-none');
			$('#pacakgeBtn').removeClass('d-none');
			$('#cacelPackageGrid').addClass('d-none');
			//$('#packItem').addClass('d-none');
		}
	} else {
		// Now this will run properly if nothing is selected
		console.log("No package selected");

		$("#custName").val("");
		$("#packageViewId,#packageViewId1").html("");
		$("#purchaseOrderId").val("");
		$("#packageSlip").val("");
		$("#packagingDate").val("");
		$("#country1").val("");
		$("#states1").val("");
		$("#city1").val("");
		$("#street11").val("");
		$("#street21").val("");
		$("#zipCode1").val("");
		$("#phone1").val("");
		$("#fax1").val("");


		gridSAOptions.api.setRowData([]);
		itemOptions.api.setRowData([]);

		$('#deletePackage').addClass('d-none');
		$('#savePackage').addClass('d-none');


	}
}

var columnDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Sales Order ID",
	field: "salesOrder",
	width: 140,
},
{
	headerName: 'Customer Name',
	field: "custName",
	width: 170,
},
{
	headerName: "PO No",
	field: "poId",
	width: 150,
},

{

	headerName: 'Receive Date',
	field: "orderReceiveDate",
	width: 100,
	cellStyle: {
		textAlign: 'center'
	}
},
{

	headerName: 'Expected Shipment',
	field: "expectedShipmentDate",
	width: 100,
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: 'Approve Status',
	field: "approveStatus",
	width: 100,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.approveStatus == "Approved") {
			return '<div style="color:#0642f5">' + params.data.approveStatus + '</div>';
		} else {
			return '<div style="color:#a9a9a9">' + params.data.approveStatus + '</div>';
		}
	},
	hide: true,
}, {

	headerName: 'Order Quantity',
	field: "quantity",
	width: 100,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = parseFloat(params.value).toFixed(2);
		return amountFormatter(value);
	}
}, {
	headerName: 'Packed Quantity',
	field: "packedQut",
	width: 100,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = parseFloat(params.value).toFixed(2);
		return amountFormatter(value);
	}
}, {
	headerName: 'Pending Quantity',
	field: "pendingQut",
	width: 100,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = parseFloat(params.value).toFixed(2);
		return amountFormatter(value);
	}
}, {
	headerName: 'Created By',
	field: "qutCreatedBy",
	width: 100,
	hide: true,
}, {
	headerName: 'Customer Id',
	field: "custId",
	width: 100,
	hide: true,
}
];

var salesGridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	onSelectionChanged: rowSelect,
	onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 1000);
	}
};

function rowSelect() {
	var selectedNodes = salesGridOptions.api.getSelectedNodes();
	var rowCount = selectedNodes.length;

	console.log("Row count:", rowCount);

	if (rowCount > 0) {
	changeSoOrderList();
		var selectedData = selectedNodes.map(node => node.data);
		console.log('Selected data for SO:', selectedData);

		var selectedRow = selectedData[0]; // just using the first row for now

		// 👉 Fill form inputs directly

		$("#salesViewId").html(selectedRow.custName);
		$("#salesCustName").val(selectedRow.custName);
		$("#salesReferenceId").val(selectedRow.salesOrder);
		$("#expectedShipmentDate").val(selectedRow.expectedShipmentDate);
		$("#orderReceiveDate").val(selectedRow.orderReceiveDate);
		$("#salesOrder").val(selectedRow.salesOrder);
		$("#custId").val(selectedRow.custId);


		// 👉 Set itemDetails data to agGrid
		//   var itemDetailsData = selectedRow.itemDetails || [];
		//  itemDetailsOptions.api.setRowData(itemDetailsData);
		var itemDetailsData = selectedRow.itemDetails;
		if (itemDetailsData == null || itemDetailsData == '') { // catches both null and undefined
			itemDetailsData = [];
		}
		itemDetailsOptions.api.setRowData(itemDetailsData);


		// 👉 Handle buttons
		if (selectedRow.approveStatus === 'Approved') {
			$('#soApproveBtn').addClass('d-none');
			$('#deleteSo').addClass('d-none');
			$('#editSo').addClass('d-none');
			$('#saveSo').addClass('d-none');
			$('#pacakgeBtn').removeClass('d-none');
		} else {
			$('#soApproveBtn').removeClass('d-none');
			$('#deleteSo').removeClass('d-none');
			$('#editSo').removeClass('d-none');
			$('#saveSo').removeClass('d-none');

			$('#pacakgeBtn').removeClass('d-none');
		}

		let qty = selectedRow?.quantity;
		let packedQty = selectedRow?.packedQut;

		if (parseFloat(qty) === parseFloat(packedQty)) {
			$("#pacakgeBtn").addClass("d-none");
		} else {
			$("#pacakgeBtn").removeClass("d-none");
		}

	} else {
		console.log("No rows selected");

		// Reset form and itemDetails grid
		$("#salesOrderheadId").html('');
		$("#salesCustName").val('');
		$("#salesReferenceId").val('');
		$("#expectedShipmentDate").val('');
		$("#orderReceiveDate").val('');
		$("#orderReceiveTime").val('');
		$("#salesOrder").val('');
		$("#taxtype").val('');
		$("#salesViewId").html('');

		itemDetailsOptions.api.setRowData([]);

		// Reset buttons
		$('#soApproveBtn').removeClass('d-none');
		$('#deleteSo').removeClass('d-none');
		$('#editSo').removeClass('d-none');
		$('#saveSo').removeClass('d-none');
		$('#pacakgeBtn').addClass('d-none');
	}
}


var itemDefs = [{
	colId: 'selectionColumn',
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 8,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "SlNo",
	field: "slNo",
	width: 60,
	pinned: 'left',
}, {
	headerName: 'SKU',
	field: "sku",
	width: 60,
}, {
	headerName: 'Item Name',
	field: "skuName",
	width: 150,
}, 

{
	headerName: 'Item Description',
	field: "itemDesc",
	cellRenderer: params => params.value
},

{
	headerName: 'salesOrderId',
	field: "salesOrderId",
	hide: true,
}, 

 {
	headerName: 'HSN Code',
	field: "hsnCode",
	width: 80,
}, {
	headerName: 'Total Quantity',
	field: "quantity",
	type: 'rightAligned',
	width: 120,
}, {
	headerName: 'Unit',
	field: "unitName",
	width: 80,
}, {

	headerName: 'Packed Quantity',
	field: "packedQut",
	type: 'rightAligned',
	width: 120,
	//valueFormatter : currencyFormatter
},
{
	headerName: 'Pending Quantity',
	field: "pendingQut",
	width: 130,
	type: 'rightAligned',
	//valueFormatter : currencyFormatter
},
{
	headerName: 'Unit',
	field: "unit",
	hide: true,
},
{
	headerName: 'packing Quantity',
	field: "packingQut",
	hide: true,
}, {
	headerName: 'packType',
	field: "packType",
	hide: true,
}, {
	headerName: 'packDate',
	field: "packDate",
	hide: true,
}, {
	headerName: 'packDesc',
	field: "packDesc",
	hide: true,
},
{
	headerName: 'Packaging Quantity',
	field: "packQut",
	type: 'rightAligned',
	width: 120,
},
{

	headerName: 'No. Of Item',
	field: "noOfItem",
	width: 100,
}, {
	headerName: 'Packaging Type',
	field: "packType",
	hide: true

}, {
	headerName: 'Packaging Type ',
	field: "packName",
	width: 100,
	type: 'leftAligned',

}, {
	headerName: 'Packaging Date ',
	field: "packDate",
	width: 120
}, 
{
	headerName: 'Remarks ',
	field: "packDesc",
	width: 200,

},
];


var itemOptions = {
	columnDefs: itemDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120,
		height: 10
	},
	onSelectionChanged: rowSelectItem,
	getRowNodeId: function(data) {
		return data.slNo;
	}
};

function rowSelectItem() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#packItem').prop('disabled', false);
		let pendingQut = selectedRows[0]?.pendingQut;
		if(pendingQut == 0) {
			itemOptions.api.deselectAll();
			return showError('Cannot proceed: No pending quantity available for this item');
		}

	} else {
		$('#packItem').prop('disabled', true);
	}
}

var itemDetailsDefs = [{
	headerName: "Sl No",
	field: "slNo",
	width: 65,
	pinned: 'left',
}, {
	headerName: 'salesOrderId',
	field: "salesOrderId",
	hide: true,
}, {
	headerName: 'Material Code',
	field: "sku",
	width: 110,
}, {

	headerName: 'Material Name',
	field: "itemName",
	width: 150,
}, 
{
	headerName: 'Item Description',
	field: "itemDesc",
	width: 200,
	cellRenderer: params => params.value
},
{
	headerName: 'HSN Code',
	field: "hsnCode",
	width: 100,
}, {

	headerName: 'Item Id',
	field: "itemId",
	width: 175,
	hide: true,
}, {
	headerName: 'Quantity',
	field: "quantity",
	type: 'rightAligned',
	width: 120,
	valueFormatter: currencyFormatter
}, {
	headerName: 'Unit',
	field: "unitName",
	width: 80,
}, {
	headerName: 'Unit',
	field: "unit",
	hide: true,
},
{

	headerName: 'Size In MM',
	field: "sizeInMM",
	width: 65,
	hide: true,
},
{

	headerName: 'Thickness In MM',
	field: "thicknessInMM",
	width: 60,
	hide: true,
}, {
	headerName: 'Discount',
	field: "discount",
	type: 'rightAligned',
	hide: true,
	valueFormatter: currencyFormatter
}, {
	headerName: 'Amount',
	field: "lineTotal",
	type: 'rightAligned',
	valueFormatter: currencyFormatter,
	aggFunc: 'sum',
	hide: true,
}, {
	headerName: 'GST Rate',
	field: "gstRate",
	type: 'rightAligned',
	width: 100,
	valueFormatter: currencyFormatter,
	hide: true,
}, {
	headerName: 'CGST',
	field: "itemCgst",
	type: 'rightAligned',
	width: 100,
	valueFormatter: currencyFormatter,
	hide: true,
}, {
	headerName: 'SGST',
	field: "itemSgst",
	type: 'rightAligned',
	width: 100,
	hide: true,
	valueFormatter: currencyFormatter
}, {
	headerName: 'IGST',
	field: "itemIgst",
	type: 'rightAligned',
	width: 100,
	hide: true,
	valueFormatter: currencyFormatter
}, {
	headerName: 'Taxable Amount',
	field: "taxableAmt",
	type: 'rightAligned',
	width: 155,
	hide: true,
	valueFormatter: currencyFormatter
},

/*{
	headerName: 'Remarks',
	field: "itemRemarks",
	width: 183,
},
{
	headerName: 'No. Of Item',
	field: "noOfItem",
	width: 183,
},*/
{
	headerName: "Status",
	field: "blockeOrder",
	width: 100,
}
];

var itemDetailsOptions = {
	columnDefs: itemDetailsDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120,
		height: 10
	},
	getRowNodeId: function(data) {
		return data.slNo;
	}
};
const columnDefsa = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true

},
{
	headerName: "Address Id",
	field: "shippingId",
	hide: true,
	cellRenderer: function(params) {
		return '<a onclick=openDetails("' + params.data.addressId +
			',' + '0' + '") href="javascript:void(0)">' +
			params.data.addressId + '</a>';
	},
	cellRenderer: function(params) {
		return '<a onclick=("' + params.data.addressId + '","' + params.data.customerId + '") href="javascript:void(0)">' + params.data.addressId + '</a>';
	}
},

{
	headerName: "Country",
	field: "country",
	width: 200,
	hide: true,
}, {
	headerName: "Country",
	field: "countryName",
	width: 200,
}, {
	headerName: "State",
	field: "state",
	width: 200,
	hide: true,

}, {
	headerName: "State",
	field: "stateName",
	width: 200,
}, {
	headerName: "City",
	field: "city",
	width: 200,
}, {
	headerName: "Street",
	field: "street1",
	width: 200,
}, {
	headerName: "Street",
	field: "street2",
	width: 200,
}, {
	headerName: "Zip Code",
	field: "zipcode",
	width: 200,
}, {
	headerName: "Phone",
	field: "phone",
	width: 200,
}, {
	headerName: "Fax",
	field: "fax",
	cellStyle: {
		textAlign: 'left'
	}
}, {
	headerName: "Default Address",
	field: "defaultStatus",
	width: 200,
	hide: true,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.defaultStatus == "Yes") {
			return '<div style="color:#0642f5">' + params.data.defaultStatus + '</div>';
		} else {
			return '<div style="color:#a9a9a9">' + params.data.defaultStatus + '</div>';
		}
	}
}
];

const gridSAOptions = {
	columnDefs: columnDefsa,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectAddress,
	/* getRowNodeId : function(data) {
		return data.customerId;
	} */
};

function rowSelectAddress() {
	var selectedRows = gridSAOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#selectAddress').removeClass('d-none');

	} else {
		$('#selectAddress').addClass('d-none');
	}
}

function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}
var pno;

function viewPackage() {
	var pages;
	var pageno = pno;

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-packages-through-ajax?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewPackage;
		/* var len = allData.length;
		$('#totalItem').find('span').html(len); */
		packageGridOptions.api.setRowData(allData);

		var totalRowCount = packageGridOptions.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);

		if (allData.length > 0) {
			console.log("AAAAAAllll", allData[0].totalPageno)
			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;

		}
		//createPagination(pages, pageno);

	});
}

function editsalesOrder(salesOrder) {
	//Cancel();
	$('#salesInfLiId').removeClass('d-none');
	$('#salesInfo').removeClass('d-none');
	$('#packageInfLid').addClass('d-none');
	$('#packageInfo').addClass('d-none');
	$('#salesItemId').addClass('d-none');
	$('#itemDetails').addClass('d-none');
	$('#shippingAddressId').addClass('d-none');
	$('#shippingAddress').addClass('d-none');
	$('#salesInfo').addClass('active show');
	$('#salesInfLiId').children().addClass('active');
	//$('#salesInfLiId').addClass('active');
	$('.loader').show();
	agGrid
		.simpleHttpRequest({
			url: 'view-saleorder-edit-new?id=' + salesOrder
		})
		.then(
			function(data) {
				console.log(data);
				$('.loader').hide();
				$("#salesOrderheadId").html(data[0].salesOrder);
				//$("#quotationId").val(data[0].quotationId);
				//$("#custId").val(data[0].custId);
				$("#salesCustName").val(data[0].custName);
				$("#taxtype").val(data[0].taxType);
				$("#salesOrder").val(data[0].salesOrder);
				$("#orderReceiveDate").val(data[0].orderReceiveDate);
				$("#orderReceiveTime").val(data[0].orderReceiveTime);
				$("#expectedShipmentDate").val(data[0].expectedShipmentDate);
				$("#salesReferenceId").val(data[0].salesOrder);
				let filteredData = data.filter(item => item.blockeOrder === "Open");
				itemDetailsOptions.api.setRowData(filteredData);
			});

}

function handleCustomerChange() {
	$('#shippingAddressId').removeClass('d-none');
	$('#shippingAddress').removeClass('d-none');
}

function getAddressDetails(custId, shipId) {
	$.ajax({
		type: "GET",
		url: "view-quotation-get-address?id=" + custId,
		async: false,
		success: function(response) {
			if (response.message === "Success") {
				var shippingDetails = JSON.parse(response.body.shippingDetails);

				var validShippingDetails = shippingDetails.filter(item => item.delFlag === 0);
				if (shipId === "null" || shipId === "" || shipId === null) {
					gridSAOptions.api.setRowData(validShippingDetails);

					var firstShippingAddress = validShippingDetails.length > 0 ? validShippingDetails[0] : null;
					console.log("1st Shipping Address------>", firstShippingAddress);
					if (firstShippingAddress) {
						$("#shippingHiddenId").val(firstShippingAddress.shippingId || "");
						$("#country1").val(firstShippingAddress.country || "").attr("disabled", true);
						$("#states1").val(firstShippingAddress.state || "").attr("disabled", true);
						$("#city1").val(firstShippingAddress.city || "").attr("disabled", true);
						$("#street11").val(firstShippingAddress.street1 || "").attr("disabled", true);
						$("#street21").val(firstShippingAddress.street2 || "").attr("disabled", true);
						$("#zipCode1").val(firstShippingAddress.zipcode || "").attr("disabled", true);
						$("#phone1").val(firstShippingAddress.phone || "").attr("disabled", true);
						$("#fax1").val(firstShippingAddress.fax || "").attr("disabled", true);
						$("#gstIn1").val(firstShippingAddress.gstIn || "").attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', firstShippingAddress.state);
					} else {
						$("#shippingHiddenId").val("").attr("disabled", true);
						$("#country1").val("").attr("disabled", true);
						$("#states1").val("").attr("disabled", true);
						$("#city1").val("").attr("disabled", true);
						$("#street11").val("").attr("disabled", true);
						$("#street21").val("").attr("disabled", true);
						$("#zipCode1").val("").attr("disabled", true);
						$("#phone1").val("").attr("disabled", true);
						$("#fax1").val("").attr("disabled", true);
						$("#gstIn1").val("").attr("disabled", true);
					}
				} else {
					gridSAOptions.api.setRowData(validShippingDetails);
					console.log("Shipping Details during edit------>", validShippingDetails);
					var selectedShippingAddress = validShippingDetails.find(item => item.shippingId === shipId);
					if (selectedShippingAddress) {
						$("#shippingHiddenId").val(selectedShippingAddress.shippingId);
						$("#country1").val(selectedShippingAddress.country).attr("disabled", true);
						$("#states1").val(selectedShippingAddress.state).attr("disabled", true);
						$("#city1").val(selectedShippingAddress.city).attr("disabled", true);
						$("#street11").val(selectedShippingAddress.street1).attr("disabled", true);
						$("#street21").val(selectedShippingAddress.street2).attr("disabled", true);
						$("#zipCode1").val(selectedShippingAddress.zipcode).attr("disabled", true);
						$("#phone1").val(selectedShippingAddress.phone).attr("disabled", true);
						$("#fax1").val(selectedShippingAddress.fax).attr("disabled", true);
						$("#gstIn1").val(selectedShippingAddress.gstIn).attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', selectedShippingAddress.state);
					}
				}
			}
		}
	});
}

function changeDateFormat(inputDate) {
	var splitDate = inputDate.split('-');
	if (splitDate.count == 0) {
		return null;
	}
	var year = splitDate[0];
	var month = splitDate[1];
	var day = splitDate[2];
	return day + '-' + month + '-' + year;
}

function getStateDataOnEditMul(cid, sid, stateId) {
	var country = $("#" + cid).val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#" + sid).empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#" + sid).append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#" + sid).append(option);
					}
					$("#" + sid).val(stateId);
				}
			},
			error: function(e) { }
		});
	} else {
		$("#" + sid).empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#" + sid).append(option);
	}
}


function Package() {
	nextTab('packageInfLid');
	$('#savePackage').removeClass('d-none');
	$('#cacelPackage').removeClass('d-none');
	$('#salesInfLiId').addClass('d-none');

	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress').addClass('d-none');
	$('#mySAGrid').show();

	$('#salesInfLiId').find('.active').removeClass('active');
	$('#salesInfo').addClass('d-none');
	$('#salesInfo').removeClass('active show');
	$('#packageInfo').addClass('active show');
	$('#packageInfLid').removeClass('d-none');
	$('#itemDetailsInfLid').removeClass('d-none', 'active');
	$('#cacelPackageGrid').addClass('d-none');
	$('#packageInfo').removeClass('d-none');

	$('#salesItemId').removeClass('d-none');
	$('#itemDetails').removeClass('d-none');
	$('#packItem').removeClass('d-none');


	var selectedNodes = salesGridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	console.log('selected data for so', selectedData);
	var salesOrderId = selectedData[0].salesOrder;
	var poidd = selectedData[0].poId;
	var custName = selectedData[0].custName;

	$("#packageViewId").html(custName);
	$("#packageViewId1").html(custName);
	$("#custName").val(custName);
	$("#purchaseOrderId").val(salesOrderId);
	$("#salesOrder").val(salesOrderId);
	/*
	$("#shippingHiddenId,#shippingId,#city1,#street11,#street21,#zipCode1,#phone1,#fax1,#gstIn1").val('');
	$("#country1").val('').select2();
*/
	$('.loader').show();
	agGrid.simpleHttpRequest({
		url: 'view-saleorder-for-packing?id=' + salesOrderId + '&poidd=' + poidd
	}).then(function(data) {
		$('.loader').hide();

		var jsonData = JSON.parse(data.body);
		
		
		var shippingData = jsonData.shipping_address;
		var itemDtls = jsonData.item_details;
		var rowData1 = [shippingData]; // agGrid expects an array
		var rowData2 = itemDtls; // agGrid expects an array
		$("#packageSlip").val(jsonData.new_inserted_id);

		console.log(shippingData)
		
		itemOptions.api.setRowData(rowData2);
		itemOptions.columnApi.setColumnVisible('pendingQut', true); // hide
		itemOptions.columnApi.setColumnVisible('packQut', true); // hide
		itemOptions.columnApi.setColumnVisible('selectionColumn', true);

		$('#changeAddress').removeClass('d-none');
		$('#shippingAddressSec').removeClass('d-none');

		$('#mySAGrid').hide();
		
		console.log('rowData1',rowData1)

		$("#shippingHiddenId").val(rowData1[0]?.shippingId);
		getAddressDetails(selectedData[0].custId, rowData1[0]?.shippingId);
		$("#country1").val(rowData1[0]?.country).attr("disabled", true);
		$("#states1").val(rowData1[0]?.state).attr("disabled", true);
		$("#city1").val(rowData1[0]?.city).attr("disabled", true);
		$("#street11").val(rowData1[0]?.street1).attr("disabled", true);
		$("#street21").val(rowData1[0]?.street2).attr("disabled", true);
		$("#zipCode1").val(rowData1[0]?.zipcode).attr("disabled", true);
		$("#phone1").val(rowData1[0]?.phone).attr("disabled", true);
		$("#fax1").val(rowData1[0]?.fax).attr("disabled", true);
		getStateDataOnEditMul('country1', 'states1', rowData1[0]?.state);
		//  gridSAOptions.api.deselectAll();


	});
	$('.loader').hide();
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#packagingDate").val(newDate).prop("disable", true);
	$('#packagePO').val(poidd);
	$('#salePackageId').val('')

}

function getItemdetailsBySku() {
	var id = $("#itemName").val();
	if (id) {
		$.ajax({
			type: "GET",
			url: "view-quotation-get-item-bySku?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.itemDetails;
					console.log("Product Details=====>", allData)
					if (allData != null) {
						$("#sku").val(allData.productSku);
						$("#unit").val(allData.productUnit);
						$("#hsnCode").val(allData.hsnCode);
						$("#productName").val(allData.productName);
						$("#itemId").val(allData.productId);
					} else {
						toastr.error("Something Went Wrong!");
						$("#itemName").val("").trigger('change');
						$("#sku").val("");
						$("#unit").val("");
						$("#model").val("");
						$("#unit").val("");
						$("#hsnCode").val("");
						$("#itemId").val("");
					}
				} else {
					toastr.error("Something Went Wrong!");
					$("#itemName").val("").trigger('change');
					$("#skuName").val("");
					$("#itemId").val("");
					$("#model").val("");
					$("#unit").val("");
					$("#hsnCode").val("");
					$("#itemId").val("");
				}
			},
			error: function(e) { }
		});
	}
}

function toggleSection() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#sku").val('');
	$("#itemName").val('').attr("disabled", false);
	$("#editProduct").val('').attr("disabled", false);
	$("#productId").val('').attr("disabled", false);
	$("#productName").val('').attr("disabled", false);
	$("#packedQut").val('').attr("disabled", false);
	$("#pendingQut").val('').attr("disabled", false);
	$("#unit").val('').attr("disabled", false);
	// $("#quantity").val('').attr("disabled", false);
	$("#packType").val('').attr("disabled", false);
	$("#packingQut").val('').attr("disabled", false);
	$("#noOfItem").val('').attr("disabled", false);
	$("#itemDesc").val('').attr("disabled", false);
	$("#packDesc").val('').attr("disabled", false);
	$("#hsnCode").val('').attr("disabled", false);
}

function cancelItemDetails() {
	itemOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#sku").val('');
	$("#editProduct").val(null);
	$("#itemId").val('');
	$("#itemName").val('').trigger('change');
	//$("#itemName").text('');
	$("#packedQut").val('');
	$("#pendingQut").val('');
	$("#unit").val('');
	$("#quantity").val('');
	$("#unitPrice").val('');
	$("#gstRate").val('');
	$("#packType").val('');
	$("#itemDesc").val('');
	$("#hsnCode").val('');
	$("#productName").val('');
	$("#itemId").val('');
	$("#packingQut").val('');
	$("#noOfItem").val('');
}

function packedItem() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var slNo = selectedRows[0].slNo;
	var rowNode = itemOptions.api.getRowNode(slNo);

	console.log("SELECTED DATA=================>>", selectedRows)
	toggleSection();
	var date1 = (new Date()).toISOString().split('T')[0];
	var newDate1 = changeDateFormat(date1);
	$("#packDate").val(newDate1);
	$("#editProduct").val(slNo);
	$("#sku").val(rowNode.data.sku);
	$("#itemName").val(rowNode.data.skuName);
	$("#productId").val(rowNode.data.productId);
	$("#productName").val(rowNode.data.productName);
	$("#hsnCode").val(rowNode.data.hsnCode);
	$("#quantity").val(rowNode.data.quantity).attr("disabled", true);
	$("#packedQut").val(rowNode.data.packedQut);
	$("#pendingQut").val(rowNode.data.pendingQut);
	$("#packQut").val(rowNode.data.pendingQut);
	$("#packingQut").val(rowNode.data.pendingQut);
	$("#unit").val(rowNode.data.unit);
	$("#unit").attr("disabled", true);
	$("#packType").val(rowNode.data.packType);
	$("#noOfItem").val(rowNode.data.noOfItem);
	$("#packDesc").val(rowNode.data.packDesc);
	$("#itemDesc").val(rowNode.data.itemDesc);
	$("#isalesOrderId").val(rowNode.data.salesOrderId);


}

function addPackQut(fieldId) {
	var pending = parseFloat($("#pendingQut").val());
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	if (tempVal <= pending) {
		$("#" + fieldId).val(tempVal);
		$("#packQut").val(tempVal);
	} else {
		toastr.error('Please add less than or same Packing Quantity as Pending Quantity');
		$("#" + fieldId).val(null);
		$("#packQut").val(null);
	}

}

function saveTableData() {
	var editProduct = $("#editProduct").val();
	var item = {};
	var data = 1;

	// Populate item fields
	item.itemId = $('#productId').val();
	item.itemName = $('#productName').val();
	item.sku = $('#sku').val();
	item.skuName = $('#itemName').val();
	item.salesOrderId = $('#isalesOrderId').val();
	item.hsnCode = $('#hsnCode').val();
	item.quantity = $('#quantity').val();
	item.unit = $('#unit').val();
	item.packedQut = $('#packedQut').val();
	item.pendingQut = $('#pendingQut').val();
	item.unitName = $("#unit option:selected").text();
	item.noOfItem = $("#noOfItem").val();
	item.packQut = $("#packQut").val();
	item.packingQut = $("#packingQut").val();
	item.packType = $("#packType").val();
	item.packName = $("#packType").val() ? $("#packType option:selected").text() : "";
	item.packDate = $("#packDate").val();
	item.itemDesc = $("#itemDesc").val();
	item.packDesc = $("#packDesc").val();

	// Validation logic
	var validation = true;

	if (!item.sku) {
		toastr.error('Stock Keeping Unit Required');
		validation = false;
		return false;
	}
	if (!item.skuName) {
		toastr.error('Item Name Required');
		validation = false;
		return false;
	}
	if (!item.quantity) {
		toastr.error('Quantity Required');
		validation = false;
		return false;
	}
	if (!item.unit) {
		toastr.error('Unit Required');
		validation = false;
		return false;
	}
	if (!item.packingQut) {
		toastr.error('Packing Item Required');
		validation = false;
		return false;
	}


	if (!validation) return;

	item.slNo = data;
	itemOptions.api.forEachNode(function(rowNode, index) {
		if (!editProduct) {
			data = data + 1;
			item.slNo = data;
		} else {
			item.slNo = editProduct;
		}
	});
	console.log("Item Data Before Grid Update:", item);

	var quot = [];

	if (editProduct) {
		var rowNode = itemOptions.api.getRowNode(editProduct);
		rowNode.setData(item);
	} else {
		itemOptions.api.forEachNode(function(rowNode, index) {
			quot.push(rowNode.data);
		});
		quot.push(item);
		itemOptions.api.setRowData(quot);
	}

	cancelItemDetails();
	$("#sku").val('');
	$("#itemId").val('');
	$("#itemName").val('');
	$("#quantity").val('');
	$("#unit").val('');
	$("#noOfItem").val('');
	$("#packedQut").val('');
	$("#pendingQut").val('');

	$("#editProduct").val(null);
}

function validFormData() {
	var allValid = true;
	var poId = $("#purchaseOrderId").val();
	if (!poId) {
		toastr.error('PO No. Required');
		allValid = false;
		return false;
	}
	return allValid;
}

function validProductData() {
	let totalQty = 0;

	if (itemOptions.api.getDisplayedRowCount() > 0) {
		itemOptions.api.forEachNode((rowNode) => {
			const qty = parseFloat(rowNode.data.packQut) || 0;
			totalQty += qty;
		});
	}

	if (totalQty > 0) {
		return true;
	} else {
		toastr.error('Please pack more than one item to proceed..');
		return false;
	}
}

// && validFormData()
function savePackage() {
	if (validProductData()) {
		var datas = [];

		if (itemOptions.api.getDisplayedRowCount() > 0) {
			itemOptions.api.forEachNode(function(rowNode, index) {
				var obj = rowNode.data;

				// Only include rows with packQut > 0
				if (parseFloat(obj.packQut) > 0) {
					obj.salePackageId = $("#salePackageId").val();
					obj.packagingDate = $("#packagingDate").val();
					obj.poId = $("#purchaseOrderId").val();
					obj.salesOrder = $("#salesOrderIdHid").val();
					obj.packageSlip = $("#packageSlip").val();
					obj.custId = $('#custId').val();
					obj.custName = $('#custName').val();
					obj.qutActive = $("#qutActive:checkbox:checked").val();
					obj.shippingHiddenId = $("#shippingHiddenId").val();
					obj.project = $("#projectId").val();

					// Handle undefined qutActive
					obj.qutActive = obj.qutActive === undefined ? "0" : "1";

					datas.push(obj);
				}
			});
		}

		// If no valid items found, show error and stop
		if (datas.length === 0) {
			toastr.error("No valid items to save. Please ensure at least one item has quantity > 0.");
			return;
		}

		console.log("DATA===================", datas);
		saveAllPackages(datas);
	}
}

function saveAllPackages(datas) {
	console.log(JSON.stringify(datas))
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-packages-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				console.log(JSON.stringify(datas))
				$('.loader').hide();
				toastr.success(response.code);
				viewPackageGrid();
				nextTabPackage('packageInfLid');
			}

		},
		error: function(datas) {
			console.log(datas)
			$('.loader').hide();
		}
	})

}

function viewPackageGrid() {
	nextTab('packageInfLid');
	$('#salesInfLiId').addClass('d-none');
	$('#salesInfLiId').find('.active').removeClass('active');
	$('#salesInfo').addClass('d-none');
	$('#salesInfo').removeClass('active show');
	$('#packageInfo').addClass('active show');
	$('#packageInfLid').removeClass('d-none');
	//$('#packageInfLid').addClass('active');
	$('#packageInfo').removeClass('d-none');
	$('#salesItemId').removeClass('d-none');
	$('#itemDetailsInfLid').removeClass('d-none');
	$('#itemDetails').removeClass('d-none active');
	//$('#itemDetails').removeClass('active');
	$('#myPackageGrid').removeClass('d-none');
	$('#mySalesGrid').addClass('d-none');
	$('#viewPackage').addClass('d-none');
	$('#cacelPackage').addClass('d-none');
	$('#viewSalesOrder').removeClass('d-none');
	$('#cacelPackageGrid').removeClass('d-none');

	$("#custName").attr('disabled',true);
	$("#salesOrderSearchDiv").addClass("d-none");
	$("#packageSearchDiv").removeClass("d-none");

	viewPackage();

	setTimeout(() => {
		if (packageGridOptions.api) {
			packageGridOptions.api.deselectAll();

			const firstRowNode = packageGridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		}
	}, 1000);

	salesGridOptions.api.deselectAll();
}

function editSalesPackage(salePackageId) {
	$('#packItem').addClass('d-none');
	$('#packagePO').val("");
	agGrid.simpleHttpRequest({
		url: 'view-packages-edit-new?id=' + salePackageId
	}).then(
		function(data) {
			console.log("DATA 1=============", data);

			var jsonData = JSON.parse(data.body);
			var shippingData = jsonData.shipping_address;
			var itemDtls = jsonData.item_details;
			var rowData1 = [shippingData]; // agGrid expects an array
			var rowData2 = itemDtls; // agGrid expects an array
			console.log("package item data==================", rowData2);
			$("#packageSlip").val(jsonData.new_inserted_id);
			//  $('#item').hide();
			itemOptions.api.setRowData(rowData2);
			itemOptions.columnApi.setColumnVisible('pendingQut', false); // hide
			itemOptions.columnApi.setColumnVisible('packQut', false); // hide
			itemOptions.columnApi.setColumnVisible('selectionColumn', false);

			var selectedNodes = packageGridOptions.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);

			$("#packageViewId").html(selectedData[0].custName);
			$("#packageViewId1").html(selectedData[0].custName);
			$("#custName").val(selectedData[0].custName);
			$("#purchaseOrderId").val(selectedData[0].salesOrder);
			$("#packageSlip").val(selectedData[0].salePackageId);
			$("#packagingDate").val(selectedData[0].qutUpdatedOn);
			$("#packagingDateCalendar").val(selectedData[0].qutUpdatedOn);
			$('#packagePO').val(selectedData[0].poId);
			



			$('#changeAddress').removeClass('d-none');
			$('#shippingAddressSec').removeClass('d-none');
			//$('#selectAddress').addClass('d-none');
			$('#mySAGrid').hide();
			if(rowData1[0].state){
				getStateDataOnEditMul('country1', 'states1', rowData1[0].state);
			}
			$("#shippingHiddenId").val(rowData1[0].shippingId || "");
			$("#country1").val(rowData1[0].country).attr("disabled", true);
			$("#states1").val(rowData1[0].state).attr("disabled", true);
			$("#city1").val(rowData1[0].city).attr("disabled", true);
			$("#street11").val(rowData1[0].street1).attr("disabled", true);
			$("#street21").val(rowData1[0].street2).attr("disabled", true);
			$("#zipCode1").val(rowData1[0].zipcode).attr("disabled", true);
			$("#phone1").val(rowData1[0].phone).attr("disabled", true);
			$("#fax1").val(rowData1[0].fax).attr("disabled", true);
		});

}

function viewSalesGrid() {
	$('#myPackageGrid').addClass('d-none');
	$('#mySalesGrid').removeClass('d-none');
	$('#viewPackage').removeClass('d-none');
	$('#viewSalesOrder').addClass('d-none');

	$("#salesOrderSearchDiv").removeClass("d-none");
	$("#packageSearchDiv").addClass("d-none");
	packageGridOptions.api.deselectAll();
	setTimeout(() => {
		if (salesGridOptions.api) {
			salesGridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 500);
	cacelPackage();

}

function deletePackageOnclick() {

	Swal.fire({
				title: 'Are you sure?',
				text: 'Do you really want to delete this package?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes, delete it!',
				cancelButtonText: 'No, keep it',
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
			}).then((result) => {
				if (result.value) {
					var selectedRows = packageGridOptions.api.getSelectedRows();
					var id = selectedRows[0].salePackageId;
					$.ajax({
						type: "POST",
						url: "view-packages-delete?id=" + id,
						success: function(response) {
							if (response.message == "success") {
								//Cancel();
								viewPackageGrid();
				
								toastr.success('Package Deleted Successfully');
							}
						},
						error: function(data) {
							console.log(data);
						}
					})
				}
			})
	
}

function getActiveGrid() {
	// Check which grid is visible
	if (!$('#mySalesGrid').hasClass('d-none')) {
		return salesGridOptions;
	} else if (!$('#myPackageGrid').hasClass('d-none')) {
		return packageGridOptions;
	}
	return null;
}


function onQuickFilterChanged() {
	salesGridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (salesGridOptions.api) {
			salesGridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged()
	}
}

function onQuickFilterChanged1() {
	gridDraftOptions.api
		.setQuickFilter(document.getElementById('quickFilter1').value);
	setTimeout(() => {
		if (gridDraftOptions.api) {
			gridDraftOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter1(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged1()
	}
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function resetBtn1() {
	$("#quickFilter1").val('');
	packageGridOptions.api.setQuickFilter('');
	packageGridOptions.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (packageGridOptions.api) {
			packageGridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function cacelPackage() {

	setTimeout(() => {
		if (salesGridOptions.api) {
			salesGridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 500);
	viewSalesOrder();
	$('#savePackage').addClass('d-none');
	$('#cacelPackage').addClass('d-none');
	$('#salesInfLiId').removeClass('d-none');

	$('#shippingAddressSec').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#mySAGrid').hide();

	$('#salesInfLiId').find('.active').removeClass('active');
	$('#salesInfo').removeClass('d-none');
	$('#salesInfo').addClass('active show');
	$('#packageInfo').removeClass('active show');
	$('#packageInfLid').addClass('d-none');
	$('#itemDetailsInfLid').addClass('d-none');
	$('#itemDetails').addClass('d-none');
	$('#cacelPackageGrid').removeClass('d-none');
	$('#packageInfo').addClass('d-none');

	$('#salesItemId').addClass('d-none');
}

function changeSoOrderList() {

	$('#savePackage,#closeAddress,#addshippingAddressSec,#saveAddress').addClass('d-none');
	$('#cacelPackage').addClass('d-none');
	$('#salesInfLiId,#addAddress').removeClass('d-none');

	$('#shippingAddressSec').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#mySAGrid').hide();

	//$('#salesInfLiId').find('.active').removeClass('active');
	$('#salesInfo').removeClass('d-none');
	$('#salesInfo').addClass('active show');
	$('#packageInfo').removeClass('active show');
	$('#packageInfLid').addClass('d-none');
	$('#itemDetailsInfLid').addClass('d-none');
	$('#itemDetails').addClass('d-none');
	$('#cacelPackageGrid').removeClass('d-none');
	$('#packageInfo').addClass('d-none');

	$('#salesItemId').addClass('d-none');
	nextTab('salesInfLiId');
}

function cacelPackageGrid() {

	var selectedNodes = packageGridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var id = selectedData.map(node => node.salePackageId);

	console.log("Id>>>>>", id[0]);
	let a = id[0];
	if (a) {
		const rowNode = packageGridOptions.api.getRowNode(a);
		console.log("rowNode>>>>>", rowNode);
		if (rowNode) {
			const rowIndex = rowNode.rowIndex; // Get the row index
			console.log("rowIndex>>>>>", rowIndex);
			const pageSize = packageGridOptions.api.paginationGetPageSize(); // Get the number of rows per page
			const pageNumber = Math.floor(rowIndex / pageSize); // Calculate page number
			packageGridOptions.api.paginationGoToPage(pageNumber); // Switch to the calculated page
			packageGridOptions.api.getDisplayedRowAtIndex(rowIndex).setSelected(true); // Select row at index
			rowSelectPackage();
		}
	}

}

function closeTooltip(button) {
	let tooltip = bootstrap.Tooltip.getInstance(button);
	if (tooltip) {
		tooltip.hide();
	}
}
function nextTabPackage(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}
function changeAddress() {
	$('#mySAGrid').show();
	$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
	$('#changeAddress,#saveAddress').addClass('d-none');
	$('#closeAddress').removeClass('d-none');

}

function addAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress,#addAddress').addClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').removeClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function closeAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec,#addAddress').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function saveAddress() {

	let fax = $("#fax2").val() ? $("#fax2").val() : "";
	let city = $("#city2").val();
	let phone = $("#phone2").val() ? $("#phone2").val() : "";
	let state = $("#states2").val();
	let stateName = $("#states2 option:selected").text();
	let country = $("#country2").val();
	let countryName = $("#country2 option:selected").text();
	let zipcode = $("#zipCode2").val();
	let street1 = $("#street12").val();
	let street2 = $("#street22").val() ? $("#street22").val() : "";
	let gstIn = $("#gstIn2").val();

	if (!country) {
		toastr.error('Country Required');
		return;
	}
	if (!state) {
		toastr.error('State Required');
		return;
	}
	if (!city) {
		toastr.error('City Required');
		return;
	}
	if (!street1) {
		toastr.error('Street 1 Required');
		return;
	}
	if (!zipcode) {
		toastr.error('Zipcode Required');
		return;
	}

	let obj = {
		"fax": fax,
		"city": city,
		"phone": phone,
		"state": state,
		"country": country,
		"delFlag": 0,
		"street1": street1,
		"street2": street2,
		"zipcode": zipcode,
		"stateName": stateName,
		"shippingId": generateUUID()?.toString(),
		"countryName": countryName,
		"gstIn": gstIn
	}



	if (zipcodeValid2) {

		let dataset = [];
		gridSAOptions.api.forEachNode(a => {
			dataset.push(a.data);
		})

		dataset.push(obj);

		let key = $("#custId").val();
		let code = JSON.stringify(dataset);

		let o = { key, code };

		$('.loader').show();
		$('body').addClass('overlay');

		$.ajax({
			type: "POST",
			url: "view-quotation-add-shipping-address",
			contentType: "application/json",
			data: JSON.stringify(o),
			success: function(resp) {
				$('.loader').hide();
				$('body').removeClass('overlay');
				if (resp.code === 'success') {
					toastr.success('Shipping address saved successfully');
					if (resp.body) {
						gridSAOptions.api.setRowData([]);
						gridSAOptions.api.setRowData(JSON.parse(resp.body));

						$('#mySAGrid').show();
						$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
						$('#saveAddress,#closeAddress').addClass('d-none');
						$('#addAddress,#changeAddress').removeClass('d-none');
						$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
					}

				} else {
					toastr.error(resp.message);
				}
			},
			error: data => {
				console.log(data)
				$('.loader').hide();
				$('body').removeClass('overlay');
				toastr.error('Something went wrong');
			}
		})
	} else {
		toastr.error('Zipcode is not valid');
		return;
	}
}

function selectAddress() {
	$('#changeAddress').removeClass('d-none');
	$('#shippingAddressSec').removeClass('d-none');
	//$('#selectAddress').addClass('d-none');
	$('#mySAGrid').hide();
	let selectedData = gridSAOptions.api.getSelectedRows();
	$("#shippingHiddenId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).attr("disabled", true);
	$("#states1").val(selectedData[0].state).attr("disabled", true);
	$("#city1").val(selectedData[0].city).attr("disabled", true);
	$("#street11").val(selectedData[0].street1).attr("disabled", true);
	$("#street21").val(selectedData[0].street2).attr("disabled", true);
	$("#zipCode1").val(selectedData[0].zipcode).attr("disabled", true);
	$("#phone1").val(selectedData[0].phone).attr("disabled", true);
	$("#fax1").val(selectedData[0].fax).attr("disabled", true);
	$("#gstIn1").val(selectedData[0].gstIn).attr("disabled", true);
	getStateDataOnEditMul('country1', 'states1', selectedData[0].state);
	gridSAOptions.api.deselectAll();
}

var zipcodeValid2;
function zipcodeVal2() {

	var zipcode = $('#zipCode2').val();

	var zipcodeid = /^\d{6}(-\d{6})?$/;
	var zipcodeid1 = /^\d{6}(-\d{5})?$/;
	if (zipcode != '') {
		if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {

			$("#error152").hide();
			zipcodeValid2 = true;
			return true;
		} else {
			$("#error152").show();
			$("#error152").html("Please enter a valid Zip Code No.");
			zipcodeValid2 = false;
			return false;
		}

	} else {
		$("#error152").hide();
		zipcodeValid2 = true;
		return true;
	}

}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function getStateDetails2() {

	var cname = $('#country2').val();

	$("#states2").empty();
	$("#states2").append('<option value="">Select</option>');

	if (cname) {
		$.ajax({
			type: "GET",
			url: "view-quotation-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states2").append(option);
					}
				}
			},
			error: function(e) { }
		});
	}
}