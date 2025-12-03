$(function() {
	getIdentity('Perform');
	var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
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
		minDate: 0
		//minDate : new Date()
	}).on("change", function() {
		$('#expectedShipmentDate').val($(this).val());
	})
	$('#expectedShipmentDate').blur(function() {
		$("#expectedShipmentDateCalendar").val($(this).val());
	});
	//

	$("#invoiceDateCalendarInvoice").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#invoiceDate').val($(this).val());
	})
	$('#invoiceDate').blur(function() {
		$("#invoiceDateCalendarInvoice").val($(this).val());
	});

	$("#dueDateCalendarInvoice").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#dueDate').val($(this).val());
	})
	$('#dueDate').blur(function() {
		$("#dueDateCalendarInvoice").val($(this).val());
	});

	$("#dateofSupplyCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#dateofSupply').val($(this).val());
	})
	$('#dateofSupply').blur(function() {
		$("#dateofSupplyCalendar").val($(this).val());
	});
//
$("#toDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#toDateQT').val($(this).val());
					})

					$('#toDateQT').blur(function () {
						$("#toDateQTCalendar").val($(this).val());
					})

//
					$("#fromDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#fromDateQT').val($(this).val());
					})

					$('#fromDateQT').blur(function () {
						$("#fromDateQTCalendar").val($(this).val());
					})

const today = new Date();
const currentYear = today.getFullYear();
const currentMonth = today.getMonth();

const fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
const firstDayOfFY = new Date(fyStartYear, 3, 1);

$("#fromDateQT").val(formatDate(firstDayOfFY));
$("#toDateQT").val(formatDate(today));		
//
	//
	disableFields();
	CKEDITOR.replace('qutDescription');
	/*CKEDITOR.replace('termCondition', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});*/
	pno = 1;
	viewSalesOrder();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);

	/*var gridDiv = document.querySelector('#itemInvoice');
	new agGrid.Grid(gridDiv, itemOptionsInvoice);*/


	$("#addshippingAddressSec").addClass("d-none");
	$('#invoiceReportId').addClass('d-none');
	$('#invoiceReport').addClass('d-none');

	$('#docTbl').on('click', '.rmv1', function() {

		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
		openDeleteConfirm();

	});

	const rowCount = gridOptions.api.getDisplayedRowCount();

	setTimeout(() => {
		if (rowCount == 0) {
			add();
			console.log("Data exists in the grid.");
		} else {
			console.log("Grid is empty.");
		}
	}, 1500); // 500 milliseconds (0.5 seconds) delay



	$('#mySAGrid').hide();

	//var today = new Date();
	var formattedDate = ("0" + today.getDate()).slice(-2) + "-" +
		("0" + (today.getMonth() + 1)).slice(-2) + "-" +
		today.getFullYear();
	$("#orderReceiveDate").val(formattedDate);
	$("#orderReceiveDateCalendar").val(formattedDate);



	$(".br-s-btn").hide();

	$('#itemName').select2({
		placeholder: "Select",
		allowClear: true
	});
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

	CKEDITOR.replace('itemDesc', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
});
function formatDate(date) {
    const d = String(date.getDate()).padStart(2, '0');
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const y = date.getFullYear();
    return `${d}-${m}-${y}`;
}

var pno;
function viewSalesOrder() {
	var pages;
	var pageno = pno;
	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();	
	
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-saleorder-through-ajax?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewSalesOrder;

		if (allData && allData.length > 0) {
			gridOptions.api.setRowData(allData);
			var totalRowCount = gridOptions.api.getModel().getRowCount();
			$('#totalCandidate').find('span').html(totalRowCount);
			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;
			
			const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
			  firstRowNode.setSelected(true);
			}
			

		} else {
			gridOptions.api.setRowData([]);
			itemOptions.api.setRowData([]);
			add();

		}
		//createPagination(pages, pageno);

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
var columnDefs = [
	{
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
		headerName: "Sales Order No",
		field: "salesOrder",
		pinned: 'left',
		width: 150,
	}, {
		headerName: 'Created By',
		field: "qutCreatedBy",
		width: 120,
	}, {
		headerName: 'Customer Name',
		field: "custName",
		width: 150,
	}, {
	headerName: 'Order Amount',
	field: "totalAmount",
	type: 'rightAligned',
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		valueFormatter: indianNumberFormatterWithDecimal
		
	}, {

		headerName: 'Order Qty',
		field: "quantity",
		width: 100,
		type: 'rightAligned',
		cellRenderer: function(params) {
			var value = parseFloat(params.value).toFixed(2);
			return amountFormatter(value);
		}
	}, {
		headerName: 'Pending Qty',
		field: "pendingQut",
		width: 110,
		type: 'rightAligned',
		cellRenderer: function(params) {
			var value = parseFloat(params.value).toFixed(2);
			return amountFormatter(value);
		}
	}, {
		headerName: "SO PDF",
		field: "list",
		width: 100,
		cellRenderer: function(params) {
			if (params.data.salesOrder || params.data.poId) {
				return '<a id="registerId" onclick="getSalesOrderPdf(\''
					+ params.data.salesOrder + '\', \'' + params.data.poId +
					'\')" href="javascript:void(0)"><i class="bi bi-cloud-download"> Download PDF SO</i></a>';
			} else {
				return '<a>N/A</a>';
			}
		},
	},
	{
		headerName: "PO No",
		field: "poNo",
		width: 100,
	}, {

		headerName: 'Receive Date',
		field: "orderReceiveDate",
		width: 120,
	}, {
		headerName: 'Expected Delivery',
		field: "expectedShipmentDate",
		width: 120,
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
			} else if (params.data.approveStatus == "Revised") {
				return '<div style="color:#bf05ff">' + params.data.approveStatus + '</div>';
			} else {
				return '<div style="color:#a9a9a9">' + params.data.approveStatus + '</div>';
			}
		}
	}, {
		headerName: 'Packed Qty',
		field: "packedQut",
		width: 110,
		type: 'rightAligned',
		cellRenderer: function(params) {
			var value = parseFloat(params.value).toFixed(2);
			return amountFormatter(value);
		}
	}, {
		headerName: 'SO Ref',
		field: "soRef",
		width: 110
	}, {
		headerName: 'Version',
		field: "version",
		width: 75
	}, {
		headerName: 'Invoice ID',
		field: "invoiceId",
		width: 110,
		hide: true,
	}, {

		headerName: 'Expected Shipment Date',
		field: "expectedShipmentDate",
		width: 110,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true,
	}, {

		headerName: 'Delivery Term',
		field: "deliveryTerm",
		width: 110,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true,
	}, {

		headerName: 'Delivery Mode',
		field: "deliveryMode",
		width: 110,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true,
	},];

var gridOptions = {
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

function getSalesOrderPdf(id, poId) {
	window.open("/sales/view-saleorder-pdf-downloads?id=" + window.btoa(id) + "&poId=" + window.btoa(poId), '_blank');
}

function revision() {
	let selectedRows = gridOptions.api.getSelectedRows();
	let soId = selectedRows[0]?.salesOrder;
	let version = selectedRows[0]?.version || '0';
	enableFields()
	$("#salesOrderheadId").val('');
	$("#saveSo").removeClass("d-none");
	$("#addSo,#soRevisionBtn,#cancelSo").addClass("d-none");
	$("#salesReferenceId").val(updateQuotationVersion(soId, parseInt(version)));
}

function updateQuotationVersion(id, version) {
	if (/-\d+$/.test(id)) {
		return id.replace(/-\d+$/, `-${version}`);
	} else {
		return `${id}-${version}`;
	}
}

function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	cancelItemDetails();
	closeAddress();
	$("#subTotal,#qSGST,#qCGST,#qIGST,#grandTotal").val("0.00");
	if (selectedNodes.length === 0) {
		// $('#deleteSo').addClass('d-none');
		$('#editSo').addClass('d-none');
		$('#addSo').addClass('d-none');
		$('#saveSo').removeClass('d-none');
		$('#cancelSo').removeClass('d-none');
		$('#soApproveBtn,#soRevisionBtn').addClass('d-none');
		addNewSo();
		return;
	} else {
		// $('#deleteSo').removeClass('d-none');
		$('#editSo').removeClass('d-none');
		$('#addSo').removeClass('d-none');
		//$('#saveSo').removeClass('d-none');
		$('#cancelSo').addClass('d-none');
		$('#soApproveBtn').removeClass('d-none');
		disableFields()
	}
	$('#salesRId').val("");
	var selectedData = selectedNodes.map(node => node.data);

	if (selectedData.length > 0) {
		var salesOrder = selectedData[0].salesOrder;
		var soRef = selectedData[0].soRef;
		var aproveStatus = selectedData[0].approveStatus;
		if (aproveStatus === 'Approved') {
			// $("#soRevisionBtn").removeClass("d-none");
		} else {
			$("#soRevisionBtn").addClass("d-none");
		}
		var salesOrderStatus = selectedData[0].salesOrderStatus;

		$("#custName1").val(selectedData[0].custName).attr("disabled", true);
		$("#custId1").val(selectedData[0].custId);
		setTimeout(() => {
			$("#soId").val(selectedData[0].salesOrder).attr("disabled", true);
		}, 3000);
		$("#deliveryTerm1").val(selectedData[0].deliveryTerm).attr("disabled", true);
		$("#deliveryMode1").val(selectedData[0].deliveryMode).attr("disabled", true);
		setTimeout(() => {
			$("#expectedShipmentDate1").val(selectedData[0].expectedShipmentDate).attr("disabled", true);
		}, 2000);
		setTimeout(function() {
			$("#type").find("option").each(function() {
				if ($(this).text().trim() === "Performa Invoice") {
					$(this).prop("selected", true);
					$("#type").trigger("change");
				}
			});
		}, 1500); // Waits 100ms – adjust if needed


		editsalesOrder(salesOrder);
		salesOrderTimeline(soRef);

		if (aproveStatus === 'Approved') {
			$('#soApproveBtn').addClass('d-none');
			// $('#deleteSo').addClass('d-none');
			$('#editSo').addClass('d-none');
			$('#saveSo').addClass('d-none');
		} else if(aproveStatus === 'Revised') {
			$('#soApproveBtn').addClass('d-none');
			$('#editSo').addClass('d-none');
			$('#saveSo').addClass('d-none');
		} else {
			$('#soApproveBtn').removeClass('d-none');
			// $('#deleteSo').removeClass('d-none');
			$('#editSo').removeClass('d-none');
			$('#saveSo').removeClass('d-none');
		}
	}
}


var itemDefs = [
	{
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
		headerName: "SlNo",
		field: "slNo",
		width: 50,
		pinned: 'left',
		valueFormatter: (params) => params.node ? params.node.rowIndex + 1 : ''
	}, {
		headerName: 'salesOrderId',
		field: "salesOrderId",
		hide: true,
	}, {
		headerName: 'Material Code',
		field: "sku",
		width: 100,
		cellClass: function(params) {
			if (params.data.blockeOrder == 'Blocked') {
				return 'blocked-cell';
			} else {
				return 'unblocked-cell';
			}
		}
	}
	, {
		headerName: 'HSN Code',
		field: "hsnCode",
		width: 90,
		hide: true,
	}, {

		headerName: 'Material Name',
		field: "itemName",
		width: 150,
	}, {
		headerName: 'Item Description',
		field: "itemDesc",
		width: 183,
		cellRenderer: params => params.value
	}, {

		headerName: 'Item Id',
		field: "itemId",
		width: 175,
		hide: true,
	}, {
		headerName: 'Quantity',
		field: "quantity",
		type: 'rightAligned',
		width: 90,
		valueFormatter: currencyFormatter
	}, {
		headerName: 'Unit',
		field: "unitName",
		width: 60,
	}, {
		headerName: 'Unit',
		field: "unit",
		hide: true,
	},
	{
		headerName: 'Size In MM',
		field: "sizeInMM",
		width: 90,
		hide: true,
	},
	{

		headerName: 'Thickness In MM',
		field: "thicknessInMM",
		width: 90,
		hide: true,
	},
	{
		headerName: 'Unit Price',
		field: "unitPrice",
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Discount',
		field: "discount",
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Amount',
		field: "lineTotal",
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal,
		aggFunc: 'sum',
	}, {
		headerName: 'GST Rate',
		field: "gstRate",
		type: 'rightAligned',
		width: 100,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'CGST',
		field: "itemCgst",
		type: 'rightAligned',
		width: 100,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'SGST',
		field: "itemSgst",
		type: 'rightAligned',
		width: 100,
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'IGST',
		field: "itemIgst",
		type: 'rightAligned',
		width: 100,
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Taxable Amount',
		field: "taxableAmt",
		type: 'rightAligned',
		width: 155,
		valueFormatter: indianNumberFormatterWithDecimal
	},
	{
		headerName: 'Remarks',
		field: "itemRemarks",
		width: 183,
		hide: true,
	},
	{
		headerName: 'No. Of Item',
		field: "noOfItem",
		width: 90,
	},
	{
		headerName: "Status",
		field: "blockeOrder",
		width: 90,

	}];

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
//
var itemDefsInvoice = [
	{
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
		headerName: "SlNo",
		field: "slNo",
		width: 50,
		pinned: 'left',
		valueFormatter: (params) => params.node ? params.node.rowIndex + 1 : ''
	}, {
		headerName: 'salesOrderId',
		field: "salesOrderId",
		hide: true,
	}, {
		headerName: 'Material Code',
		field: "sku",
		width: 100,
		cellClass: function(params) {
			if (params.data.blockeOrder == 'Blocked') {
				return 'blocked-cell';
			} else {
				return 'unblocked-cell';
			}
		}
	}
	, {
		headerName: 'Material Name',
		field: "itemName",
		width: 150,
	}, {
		headerName: 'HSN Code',
		field: "hsnCode",
		width: 90,
	}, {
		headerName: 'Item Id',
		field: "itemId",
		width: 175,
		hide: true,
	}, {
		headerName: 'Quantity',
		field: "quantity",
		type: 'rightAligned',
		width: 90,
		valueFormatter: currencyFormatter
	}, {
		headerName: 'Unit',
		field: "unitName",
		width: 60,
	}, {
		headerName: 'Unit',
		field: "unit",
		hide: true,
	},
	{

		headerName: 'Size In MM',
		field: "sizeInMM",
		width: 90,
	},
	{

		headerName: 'Thickness In MM',
		field: "thicknessInMM",
		width: 90,
	}, {
		headerName: 'Discount',
		field: "discount",
		type: 'rightAligned',
		valueFormatter: currencyFormatter
	}, {
		headerName: 'Amount',
		field: "lineTotal",
		type: 'rightAligned',
		valueFormatter: currencyFormatter,
		aggFunc: 'sum',
	}, {
		headerName: 'GST Rate',
		field: "gstRate",
		type: 'rightAligned',
		width: 100,
		valueFormatter: currencyFormatter,
	}, {
		headerName: 'CGST',
		field: "itemCgst",
		type: 'rightAligned',
		width: 100,
		valueFormatter: currencyFormatter,
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
		valueFormatter: currencyFormatter
	}, {
		headerName: 'Taxable Amount',
		field: "taxableAmt",
		type: 'rightAligned',
		width: 155,
		valueFormatter: currencyFormatter
	},
	{
		headerName: 'Item Description',
		field: "itemDesc",
		width: 183,
		cellRenderer: params => params.value
	},
	{
		headerName: 'Remarks',
		field: "itemRemarks",
		width: 183,
		hide: true,
	},
	{
		headerName: 'No. Of Item',
		field: "noOfItem1",
		width: 90,
		hide: true,
	},
	{
		headerName: "Status",
		field: "blockeOrder",
		width: 90,
		hide: true,
	}];

var itemOptionsInvoice = {
	columnDefs: itemDefsInvoice,
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
	//onSelectionChanged: rowSelectItemInvoice,
	getRowNodeId: function(data) {
		return data.slNo;
	}
};

//
function rowSelectItem() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var blockOrderSts = selectedRows.length > 0 ? selectedRows[0].blockeOrder : null;
	var selectedQuoteRows = gridOptions.api.getSelectedRows();
	var approveStatus = '';

	if (selectedRows.length > 0) {
		if (selectedQuoteRows.length > 0) {
			let approveSts = selectedQuoteRows[0].approveStatus;
			if (approveSts === 'Approved' && blockOrderSts !== 'Blocked') {
				$('#blockOrder').addClass('d-none');
			}
			else {
				$('#blockOrder').addClass('d-none');
				$(".br-dis").prop("disabled", false);
			}
		} else {
			$('#blockOrder').addClass('d-none');
		}
	} else {
		$('#blockOrder').addClass('d-none');
	}

	if (selectedQuoteRows.length > 0) {

		$(".br-dis").prop("disabled", true);
		approveStatus = selectedQuoteRows[0].approveStatus;
		$(".br-dis").prop("disabled", approveStatus == 'Approved');
		return;
	} else {

		$(".br-dis").prop("disabled", false);
	}

	var rowCount = selectedRows.length;
	$(".br-dis").prop("disabled", rowCount == 0);

}


function nextTab(id) {
	if (id === 'shippingAddressId') {
		var custName = $("#custName").val();
		if (custName == null || custName.trim() == "") {
			nextTab('salesInfLiId');
			toastr.error('Customer Name Required');
			return false;
		}
	}
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();

	var selectedNodes = gridOptions.api.getSelectedNodes();
	if (selectedNodes.length > 0 && selectedNodes[0].data && selectedNodes[0].data.invoiceId) {
		var id = selectedNodes[0].data.invoiceId;

		$('#downloadInvoice').removeClass('d-none');
		$('#saveInvoice').addClass('d-none');
		if (selectedNodes[0].data.approveStatus === "Pending") {
			$('#downloadInvoice').removeClass('d-none');
			$('#saveInvoice').removeClass('d-none');
		} else if (selectedNodes[0].data.approveStatus === "Approved") {
			$('#downloadInvoice').removeClass('d-none');
			$('#saveInvoice').addClass('d-none');
		} else {
			$('#downloadInvoice').addClass('d-none');
			$('#saveInvoice').removeClass('d-none');
		}
	}
	else {
		$('#downloadInvoice').addClass('d-none');
		$('#saveInvoice').removeClass('d-none');
	}

}
const columnDefsa = [
	{
		headerCheckboxSelection: true,
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
			return '<a onclick=openDetails("' + params.data.addressId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.addressId + '</a>';
		},
		cellRenderer: function(params) {
			return '<a onclick=("' + params.data.addressId + '","' + params.data.customerId + '") href="javascript:void(0)">' + params.data.addressId + '</a>';
		}
	},
	{
		headerName: "Country",
		field: "country",
		width: 90,
		hide: true,
	}, {
		headerName: "Country",
		field: "countryName",
		width: 90,
	}, {
		headerName: "State",
		field: "state",
		width: 90,
		hide: true,

	}, {
		headerName: "State",
		field: "stateName",
		width: 90,
	}, {
		headerName: "City",
		field: "city",
		width: 90,
	}, {
		headerName: "Street",
		field: "street1",
		width: 90,
	}, {
		headerName: "Street",
		field: "street2",
		width: 90,
	}, {
		headerName: "Zip Code",
		field: "zipcode",
		width: 90,
	}, {
		headerName: "Phone",
		field: "phone",
		width: 90,
	}, {
		headerName: "Fax",
		field: "fax",
		cellStyle: {
			textAlign: 'left'
		},

	}, {
		headerName: "Default Address",
		field: "defaultStatus",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true,
		cellRenderer: function(params) {
			if (params.data.defaultStatus == "Yes") {
				return '<div style="color:#0642f5">' + params.data.defaultStatus + '</div>';
			} else {
				return '<div style="color:#a9a9a9">' + params.data.defaultStatus + '</div>';
			}
		}
	}];

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

/* customer AutoSearch */
function getCustomerList() {
	var search = $("#custName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "view-quotation-get-customer-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:-30px; font-weight:100; font-size:12px;" class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].custId
								+ '\',\''
								+ response.body[i].custName
								+ '\',\''
								+ response.body[i].custGSTNo
								+ '\',\''
								+ response.body[i].taxType
								+ '\',\''
								+ response.body[i].custPan
								+ '\')">'
								+ response.body[i].custName
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;" onClick="selectAutocompleteValue()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	} else {
		$("#addre").hide();
		$("#suggesstion-box1_").hide();
	}
}
function handleCustomerChange() {
	$('#shippingAddressId').removeClass('d-none');
	$('#shippingAddress').removeClass('d-none');


}
function handleCustomerChange1() {



}
function selectAutocompleteValue() {
	$("#custId").val("");
	$("#custName").val("");
	$("#gstNo").val("");
	$("#pan").val("");
	$("#taxType").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box1_").hide();
}
function selectAutocompleteValue1(custId, custName, custGSTNo, taxType, custPan) {
	if (custId) {
		$("#custId").val(custId);
		$("#custName").val(custName);

		$("#taxType").val(taxType);
		$("#search").val(custName);
		$("#search").attr('data-procat', custId);
		$("#suggesstion-box1_").hide();
		$("#addre").show();
		if ((custPan && custPan !== 'null')) {
			$("#pan").val(custPan).attr("disabled", true);
		} else {
			$("#pan").val('').attr("disabled", false);
		}
		if ((custGSTNo && custGSTNo !== 'null')) {
			$("#gstNo").val(custGSTNo).attr("disabled", true);
		} else {
			$("#gstNo").val('').attr("disabled", false);
		}

		//hideShowS();
		//checkForDuplicate(key,counter);
		getAddressDetails(custId, "")
		$.ajax({
			type: "GET",
			url: "view-saleorder-get-po?custId=" + custId,
			dataType: "json",
			success: function(response) {
				if (response && response.body && response.body.length > 0) {
					// Parse the first stringified JSON inside response.body array
					const parsed = JSON.parse(response.body[0]);
					const poList = parsed.List;
					updatePoDropdown(poList);
				}
			},
			error: function(xhr, status, error) {
				console.error("Error fetching quotation data:", error);
			}
		});
	} else {
		$("#custId").val("");

		$("#custName").val("");
		$("#custGSTNo").val("");
		$("#taxType").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();


	}
}
/*function updatePoDropdown(po) {
	var $dropdown = $("#purchaseOrderId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select PO</option>');
	po.forEach(function(po) {
		$dropdown.append('<option value="' + po + '">' + po + '</option>');
	});
}*/
function updatePoDropdown(poList) {
	var $dropdown = $("#purchaseOrderId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select PO</option>');

	poList.forEach(function(po) {
		//$dropdown.append('<option value="' + po.Po_Id + '">' + po.PO_No + '</option>');
		$dropdown.append(
			'<option value="' + po.Po_Id + '" data-pono="' + po.PO_No + '">' + po.PO_No + '</option>'
		);
	});
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
			error: function(e) {
			}
		});
	} else {
		$("#" + sid).empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#" + sid).append(option);
	}
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
function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;

	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if (cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});

}
function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;

	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}

	let fnametext = '<div id="imageName_' + counter + '" class="imageName" style="margin-left: 2px;">' + fileName + '</div><span><i class="ti-close red close_sec1 deleteFileDoc" onclick="openDeleteConfirm(' + counter + ')"></i></span>';

	if (extension[1] === "jpg" || extension[1] === "png" || extension[1] === "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] === "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon'></i></a>";
	} else if (extension[1] === "xls" || extension[1] === "xlsx" || extension[1] === "csv") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] === "doc" || extension[1] === "docx" || extension[1] === "dox") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}

	$("#clickImg_" + counter).removeClass('ti-plus').addClass('ti-pencil');
	// $("#uploadHidden_"+ counter).val(fileName);
	$("#uploadedBillDiv_" + counter).html(LightImg + fnametext);

}
function checkEmptyQuot() {

	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclsss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error('Please Choose a File');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMoreQuot()
	}
}
function addMoreQuot() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:last").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclsss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	// $("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');
		selectInput.eq(0).attr('id', "docid_" + i);

		textInput.eq(1).attr('id', "docnoid_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(5).attr('id', "uploadedBillDiv_" + i);
		/*divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);*/
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

	$("#uploadedBillDiv_" + lengthOfTableRow).empty();

}

function checkForDuplicateEntry(event) {
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var count = 0;
	$(".docNoclsss").each(function() {
		if (document == $(this).val()) {
			count++;
		}
	})
	if (count >= 2) {
		toastr.error('Document Name Already Entered');
		return false;
	} else {
		return true;
	}

}
function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;

	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if (cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});

}
function onPoChange(poId) {
	$.ajax({
		type: "GET",
		url: "view-saleorder-get-purchase-order-details?poId=" + poId,
		dataType: "json",
		success: function(response) {
			if (response.code === 'success') {
				let poDetails = JSON.parse(response.body);

				if (poDetails.poData && poDetails.poData.length > 0) {
					let po = poDetails.poData[0];
					$('#project').val(po.projectId).select2();
					$('#reference').val(po.quotationReference);
					$('#endCustomerName').val(po.endCustName);
					$('#salesPaymentTerm').val(po.payTerm);
					CKEDITOR.instances.qutDescription.setData(po.termsAndCondition);
					$('#expectedShipmentDate').val(po.expectedShipmentDate);

					itemOptions.api.setRowData(po.itemDetails || []);
					$("#subTotal").val(commaFormattedWithDecimal(po?.billingDetails?.subTotal) || '0.00');
					$("#qSGST").val(commaFormattedWithDecimal(po?.billingDetails?.totalSGST) || '0.00');
					$("#qCGST").val(commaFormattedWithDecimal(po?.billingDetails?.totalCGST) || '0.00');
					$("#qIGST").val(commaFormattedWithDecimal(po?.billingDetails?.totalIGST) || '0.00');
					$("#grandTotal").val(commaFormattedWithDecimal(po?.billingDetails?.grandTotal) || '0.00');

					let docDetails = po.documentDetails;
					$("#doctbodyData").empty();

					if (docDetails && docDetails.length > 0) {
						for (var i = 0; i < docDetails.length; i++) {

							let documentList = docDetails;

							console

							let cls = 'ti-plus';

							if (documentList[i].fileName) {
								cls = 'ti-pencil';
							}

							let a = '<div class="form-group d-flex"><div class="">' +
								'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
								'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
								'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
								'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
								'<div id="validationDiv"></div></div>';

							var tbl = '<tr>' +
								'<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
								'<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>' +
								'<td><div class="form-group"> <input type="text" value="' + docDetails[i].documentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>' +
								'<td> ' + a + ' </td>' +
								'</tr>';

							$("#doctbodyData").append(tbl);
						}
					} else {
						appendEmptyRow();
					}
				} else {
					clearPoFields();
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching purchase order data:", error);
			clearPoFields();
		}
	});
}

function clearPoFields() {
	$('#project').val('').trigger('change');
	$('#reference').val('');
	itemOptions.api.setRowData([]);
	$("#doctbodyData").empty();
	appendEmptyRow();
}
function appendEmptyRow() {
	let i = 0;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
		+ '<td>' + a + '</td>'
		+ '</tr>';
	$("#doctbodyData").append(tbl);
}

function toggleSection() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#sku").val('');
	$("#editProduct").val('').attr("disabled", false);
	$("#itemId").val('').attr("disabled", false);
	$("#itemName").val('').trigger('change').attr("disabled", false);
	$("#sizeInMM").val('').attr("disabled", false);
	$("#thicknessInMM").val('').attr("disabled", false);
	$("#unit").val('').attr("disabled", false);
	$("#quantity").val('').attr("disabled", false);
	$("#unitPrice").val('').attr("disabled", false);
	$("#gstRate").val('').attr("disabled", false);
	$("#lineTotal").val('').attr("disabled", false);
	$("#itemDesc").val('').attr("disabled", false);
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
	$("#sizeInMM").val('');
	$("#thicknessInMM").val('');
	$("#unit").val('');
	$("#quantity").val('');
	$("#unitPrice").val('');
	$("#gstRate").val('');
	$("#lineTotal").val('');
	$("#itemDesc").val('');
	$("#hsnCode").val('');
	$("#productName").val('');
	$("#itemId").val('');
	$("#discount").val('');
	$("#gstRate").val('');
	CKEDITOR.instances.itemDesc.setData("");
}
function deleteProductOnclick() {
	var selectedRows = itemOptions.api.getSelectedRows();
	itemOptions.api.applyTransaction({
		remove: selectedRows

	});
	toastr.success('Item Deleted Successfully');
	//cancelModalProductBtn();
	//DELETE GST,SUBTOTAL,GRANDTOTAL,SGST,CGST,IGST
	var sum = $("#subTotal").val();
	var gstRate = $("#gstRate").val();
	var qCGST = $("#qCGST").val();
	var qSGST = $("#qSGST").val();
	var qIGST = $("#qIGST").val();
	var grandTotal = $("#grandTotal").val();
	var itemIgst = $("#itemIgst").val();
	var itemCgst = $("#itemCgst").val();
	var itemSgst = $("#itemSgst").val();
	var len = selectedRows.length;
	for (var i = 0; i < len; i++) {

		sum = sum - selectedRows[i].lineTotal;
		var taxType = $("#taxType").val()
		if (taxType == "true") {

			selectedRows[i].itemIgst = selectedRows[i].lineTotal
				* selectedRows[i].gstRate / 100;
			qIGST = qIGST - selectedRows[i].itemIgst;
			grandTotal = sum + qIGST;
		} else {
			selectedRows[i].itemCgst = selectedRows[i].lineTotal
				* selectedRows[i].gstRate / 200;
			selectedRows[i].itemSgst = selectedRows[i].lineTotal
				* selectedRows[i].gstRate / 200;

			qCGST = qCGST - selectedRows[i].itemCgst;
			qSGST = qSGST - selectedRows[i].itemSgst;
			grandTotal = sum + qCGST + qSGST;
		}

	}

	$("#itemIgst").val(itemIgst);
	$("#itemCgst").val(itemCgst);
	$("#itemSgst").val(itemSgst);
	$("#subTotal").val(sum);
	$("#qIGST").val(qIGST);
	$("#qCGST").val(qCGST);
	$("#qSGST").val(qSGST);
	$("#grandTotal").val(grandTotal)
}
function editItemDetails() {
	toggleSection();
	let selectedData = itemOptions.api.getSelectedRows();
	$("#sku").val(selectedData[0].sku).attr("disabled", true);
	$("#editProduct").val(selectedData[0].slNo);
	$("#itemId").val(selectedData[0].itemId);
	$("#itemName").val(selectedData[0].sku).select2().attr("disabled", true);
	$("#sizeInMM").val(selectedData[0].sizeInMM);
	$("#thicknessInMM").val(selectedData[0].thicknessInMM);
	$("#unit").val(selectedData[0].unit).attr("disabled", true);
	$("#quantity").val(selectedData[0].quantity);
	$("#unitPrice").val(selectedData[0].unitPrice).attr("disabled", true);
	$("#gstRate").val(selectedData[0].gstRate).attr("disabled", true);
	$("#lineTotal").val(selectedData[0].lineTotal).attr("disabled", true);
	// $("#itemDesc").val(selectedData[0].itemDesc);
	$("#charNumSN span").text((selectedData[0].itemDesc || "").length);
	$("#hsnCode").val(selectedData[0].hsnCode).attr("disabled", true);
	$("#discount").val(selectedData[0].discount).attr("disabled", true);
	if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData(selectedData[0].itemDesc || "");
	}

}
function saveTableData() {
	var editProduct = $("#editProduct").val();
	var item = {};
	var data = 1;
	var validation = true;

	let itemDesc = CKEDITOR.instances['itemDesc'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');

	if (!$('#itemName').val().trim()) {
		toastr.error("Item Name Required");
		validation = false;
		return false;
	}
	if (!$('#sku').val().trim()) {
		toastr.error("Stock Keeping Unit Required");
		validation = false;
		return false;
	}
	if (!$('#quantity').val().trim()) {
		toastr.error("Quantity Required");
		validation = false;
		return false;
	}
	if (!$('#unit').val().trim()) {
		toastr.error("Unit Required");
		validation = false;
		return false;
	}


	if (validation) {
		item.slNo = data;
		itemOptions.api.forEachNode(function(rowNode, index) {

			if (!editProduct) {
				data = data + 1;
				item.slNo = data;
			} else {
				item.slNo = editProduct;
			}
		});

		item.itemId = $('#itemId').val();
		item.sku = $('#sku').val();
		item.hsnCode = $('#hsnCode').val();
		item.itemName = $('#itemName option:selected').text();
		item.quantity = $('#quantity').val();
		item.unit = $('#unit').val();
		item.unitName = $("#unit option:selected").text();
		item.unitPrice = $('#unitPrice').val();
		item.discount = $('#discount').val();
		item.gstRate = $('#gstRate').val();
		item.lineTotal = $('#lineTotal').val();
		item.sizeInMM = $('#sizeInMM').val();
		item.thicknessInMM = $('#thicknessInMM').val();
		item.itemDesc = itemDesc;
		var taxType = $("#taxType").val();
		if (taxType == 'true') {

			item.itemCgst = (item.lineTotal * item.gstRate) / 200;
			item.itemSgst = (item.lineTotal * item.gstRate) / 200;
			item.itemIgst = 0;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemCgst
				+ item.itemSgst;


		} else {
			item.itemCgst = 0;
			item.itemSgst = 0;
			item.itemIgst = (item.lineTotal * item.gstRate) / 100;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemIgst;
		}
		//item.slNo = data;
		var quot = [];

		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			itemOptions.api.setRowData(quot);


		}
		//priceCalculation();
		cancelItemDetails();
		$("#sku").val('');
		$("#itemId").val('');
		$("#itemName").val('').trigger('change');
		$("#quantity").val('');
		$("#unit").val('');
		$("#unitPrice").val('');
		$("#discount").val('');
		$("#gstRate").val('');
		$("#lineTotal").val('');
		$("#sizeInMM").val('');
		$("#thicknessInMM").val('');
		$("#itemDesc").val('');
		$("#editProduct").val(null);
	}

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
function cancelSo() {
	if (gridOptions.api) {
		gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		rowSelect();
	}
	$('#addSo').removeClass('d-none');
	$('#cancelSo').addClass('d-none');

	$('#salesReferenceId').val('');
	$('#orderReceiveDate').val('');
}
function addNewSo() {
	enableFields();
	showNoRecordsFound();
	$("#salesCustName1").html("");
	$("#salesCustName2").html("");
	$("#salesCustName3,#salesCustName4").html("");
	$('.formValidation').remove();
	$('#saveSo').removeClass('d-none')
	$('#blockOrder').addClass('d-none')
	let fieldsToClear = [
		"#orderReceiveDate", "#salesReferenceId", "#purchaseOrderId", "#custGSTNo", "#approveStatus", "#salesOrderheadId",
		"#custId", "#custName", "#reference", "#expectedShipmentDate","ourRefNo", "#pan", "#gstNo", "#deliveryMode", "#deliveryTerm", "#piRemarks", "#soRef", "#salesPaymentTerm","#ourRefNo"
	];
	$('#salesOrderheadId').val('');
	$('#endCustomerName').val('');
	$('#pan').val('');
	$('#gstNo').val('');
	//$('#version').html('');
	//$('#savePoWo').removeClass('d-none')
	updatePoDropdown([]);
	fieldsToClear.forEach(field => $(field).val(""));
	$("#project").val("").trigger('change');
	let elementsToHide = ['#mailId', '#mail', '#approveBtn', '#pdfButton', '#shippingAddressId', '#shippingAddress', '#invoiceReportId'];
	elementsToHide.forEach(el => $(el).addClass('d-none'));
	itemOptions.api.setRowData();
	CKEDITOR.instances.qutDescription.setData("");
	let date = (new Date()).toISOString().split('T')[0];
	let newDate = changeDateFormat(date);
	$("#orderReceiveDate").val(newDate);
	$("#invoiceDate").val(newDate);
	$.ajax({
		type: "GET",
		url: "view-saleorder-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				//console.log("Response--------------"+JSON.stringify(response.body[0].name));
				$("#salesReferenceId").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});
	$("#doctbodyData").empty();
	let tbl = `<tr> 
        <td style="display:none" align="center" class="pdb-24">
            <input class="checkCls" type="checkbox" id="check2"><label for="check2"></label>
        </td>
        <td style="display:none">
            <div class="form-group">
                <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">
                    <option value="">Select</option>
                </select>
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" value="" class="form-control docNoclsss" id="docnoid_">
            </div>
        </td>
        <td>
            <div class="control-group position-r">
                <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">
                    <i class="ti-plus" id="clickImg_0"></i>
                </label>
                <div class="controls">
                    <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />
                </div>
            </div>
            <input type="hidden" id="uploadHidden_0" class="uploadHidCls">
            <div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>
            <div id="imageName_0" class="imageName"></div>
            <div id="dltImage_0"></div>
            <input type="hidden" id="editId_0">
        </td>
    </tr>`;

	$("#doctbodyData").append(tbl);
}
function add() {
	addNewSo();
	gridOptions.api.deselectAll();
}
function validFormData() {
	var custName = $("#custName").val();
	var purchaseOrderId = $("#purchaseOrderId").val();
	var soDate = $("#orderReceiveDate").val();
	var expectedShipmentDate = $("#expectedShipmentDate").val();
	var salesPaymentTerm = $("#salesPaymentTerm").val();
	var project = $("#project").val();
	var pan = $("#pan").val();
	var gstNo = $("#gstNo").val();

	if (custName == null || custName.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}
	if (purchaseOrderId == null || purchaseOrderId.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('PO NO Required');
		return false;
	}
	if (soDate == null || soDate.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Sales Order Date Required');
		return false;
	}
	if (pan == null || pan.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('PAN No Required');
		return false;
	}
	let panerrormsg = pancardVal();
	if(panerrormsg) {
		nextTab('salesInfLiId');
		return showError(panerrormsg);
	} 
	if (gstNo == null || gstNo.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('GST No Required');
		return false;
	}
	let msg = ValidateGSTNumber();
	if(msg) {
		nextTab('salesInfLiId');
		return showError(msg);
	} 
	if (expectedShipmentDate == null || expectedShipmentDate.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Expected Delivery Date Required');
		return false;
	}
	if (salesPaymentTerm == null || salesPaymentTerm.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Payment Term Required');
		return false;
	}

	return true;
}


function validProductData() {
	return true;
}
function saveSo() {
	if (validProductData() && validFormData()) {
		var datas = [];
		var imageValid = true;
		var uploadList = [];
		var qutDescription = CKEDITOR.instances.qutDescription.getData();
		$("#doctbodyData > tr").each(
			function() {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined'
					&& fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
					};
				} else {
					if ($("#salesOrder").val()) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['salesOrder'] = $("#salesOrder").val();
				uploadData['documnentName'] = $(this)
					.find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(
					".uploadHidCls").val();
				uploadList.push(uploadData);


			});

		setTimeout(function() {
			if (itemOptions.api.getDisplayedRowCount() > 0) {
				itemOptions.api.forEachNode(function(rowNode, indrx) {
					var obj = rowNode.data;

					obj.itemDesc = obj?.itemDesc?.replace(/"/g, '\\"');
					//obj.poId = $("#purchaseOrderId").val();
					obj.custId = $("#custId").val();
					obj.custName = $("#custName").val();
					obj.qutDescription = qutDescription;
					obj.subTotal = parseFloat($("#subTotal").val().replace(/,/g, '')) || 0;
					obj.grandTotal = parseFloat($("#grandTotal").val().replace(/,/g, '')) || 0;
					obj.qIGST = parseFloat($('#qIGST').val().replace(/,/g, '')) || 0;
					obj.qCGST = parseFloat($('#qCGST').val().replace(/,/g, '')) || 0;
					obj.qSGST = parseFloat($('#qSGST').val().replace(/,/g, '')) || 0;

					var tt = $('#taxType').val();
					if (tt == 'true') {
						obj.taxType = true;
					} else {
						obj.taxType = false;
					}
					//obj.storeId = $('#storeId').val();
					//obj.orderReceiveDate = $('#orderReceiveDate').val();

					obj.salesOrder = $("#salesOrderheadId").val();

					obj.orderReceiveDate = $('#orderReceiveDate').val();

					obj.expectedShipmentDate = $('#expectedShipmentDate').val();
					obj.ourRefNo = $('#ourRefNo').val();
					obj.salesPaymentTerm = $('#salesPaymentTerm').val();
					obj.deliveryMode = $('#deliveryMode').val();
					obj.deliveryTerm = $('#deliveryTerm').val();
					obj.soRef = $('#soRef').val();
					obj.paymentTermId = $('#paymentTermId').val();
					obj.orderType = $("#orderType").val();
					obj.deliveryMethodId = $('#deliveryMethodId').val();
					obj.salesPerson = $('#salesPersonId').val();
					obj.reference = $("#reference").val();
					obj.terms = $("#termCondition").val();

					obj.tcs = $("#tcsName").val();
					obj.tcsAmount = $("#tcsAmount").val();
					obj.salesOrderId = $("#salesReferenceId").val();
					obj.shippingHiddenId = $("#shippingHiddenId").val();
					obj.project = $('#project').val();
					obj.documentList = uploadList;

					obj.pan = $('#pan').val();
					obj.gstNo = $('#gstNo').val();
					obj.endCustomerName = $('#endCustomerName').val();

					let selectedOption = $("#purchaseOrderId option:selected");
					obj.poId = selectedOption.val();                  // TSPO_PoId
					obj.poNo = selectedOption.data("pono");           // TSPO_PoNo


					// console.log('Object================', obj)
					datas.push(obj);

				});
				console.log(datas)
				saveAllSalesOreder(datas);
			} else {
				$("#messageParagraph").text("Items Details Required");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		}, 1000)

	}
}

function saveAllSalesOreder(datas) {
	$('.loader').show();
	$.ajax({

		type: "POST",
		url: "view-saleorder-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				viewSalesOrder();
				toastr.success(response.code);



				let salesOrderId = datas.length > 0 ? datas[0].salesOrder : "";
				setTimeout(() => {
					gridOptions.api.forEachNode((node) => {
						if (salesOrderId == "") {
							let firstRow = gridOptions.api.getDisplayedRowAtIndex(0);
							if (firstRow) {
								firstRow.setSelected(true);
							}
						} else if (node.data.salesOrder == salesOrderId) {
							node.setSelected(true);
						}
					});
				}, 3000);
			}

		},
		error: function(datas) {
			console.log(datas)
			$('.loader').hide();
		}
	})

}
function Cancel() {
	//$("#reqTable, .btn-hs, #myGrid, #sendEmail").show();
	//$("#addData, #suggesstion-box1_, #suggesstion-box2_, #suggesstion-box4_").hide();

	let fieldsToClear = [
		"#orderReceiveDate", "#salesReferenceId", "#purchaseOrderId", "#custGSTNo", "#approveStatus", "#salesOrderheadId",
		"#custId", "#custName", "reference",
	];

	fieldsToClear.forEach(field => $(field).val(""));
	itemOptions.api.setRowData();
	CKEDITOR.instances.qutDescription.setData("");
	$("#project").val("").trigger('change');
	$('#salesOrderheadId').html('');
	$('.formValidation').remove();

	$("#doctbodyData").empty().append(
		'<tr>' +
		'<td style="display:none" align="center" class="pdb-24">' +
		'<input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
		'<td style="display:none"><div class="form-group">' +
		'<select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">' +
		'<option value="">Select</option> </select> </div></td>' +
		'<td><div class="form-group">' +
		'<input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
		'<td> <div class="control-group position-r">' +
		'<label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">' +
		'<i class="ti-plus" id="clickImg_0"></i> </label>' +
		'<div class="controls">' +
		'<input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />' +
		'</div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
		'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>' +
		'<div id="imageName_0" class="custom-file-upload"></div>' +
		'<input type="hidden" id="editId_0"> </td>' +
		'</tr>'
	);

	//$('#delete, #reqAppvBtn, #copyQuotation, #purchaseOrder').attr("disabled", true);
	// $('#add').attr("disabled", false);
}
function getEditTaxType(search) {

	$.ajax({
		type: "POST",
		url: "view-po-or-wo-get-customer-list",
		dataType: 'json',
		contentType: 'application/json',
		data: search,
		success: function(response) {
			if (response.message == "success") {
				$("#taxType").val(response.body[0].taxType);
				//hideShowS();
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}

function editsalesOrder(salesOrder, pendingQut, approveStatus) {
	Cancel();
	nextTab('salesInfLiId');
	$('.loader').show();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var invoiceId = selectedNodes[0].data.invoiceId;
	agGrid
		.simpleHttpRequest({
			url: 'view-saleorder-edit-new?id=' + salesOrder + '&invoiceId=' + invoiceId,
		})
		.then(
			function(data) {
				$('.loader').hide();
				$("#salesOrderheadId").val(data[0].salesOrder);
				//$("#quotationId").val(data[0].quotationId);
				$("#custId").val(data[0].custId);
				$("#custName").val(data[0].custName).trigger("change");
				$("#salesCustName1").html(data[0].custName);
				$("#salesCustName2").html(data[0].custName);
				$("#salesCustName3,#salesCustName4").html(data[0].custName);
				/*let poIdArray = data[0].poNo ? data[0].poNo.split(",") : [];
				updatePoDropdown(poIdArray);
				setTimeout(() => {
					$("#purchaseOrderId").val(data[0].poNo).select2();
				}, 1000);
				$("#purchaseOrderId").val(data[0].poNo);*/
				if (data[0].salesOrder != '') {
					$('#invoiceReportId').removeClass('d-none');
					$('#invoiceReport').removeClass('d-none');
				} else {
					$('#invoiceReportId').addClass('d-none');
					$('#invoiceReport').addClass('d-none');
				}

				let poId = data[0].poId; // TSPO_PoId
				let poNo = data[0].poNo; // TSPO_PoNo

				let poList = [{ Po_Id: poId, PO_No: poNo }];
				updatePoDropdown(poList);

				setTimeout(() => {
					$("#purchaseOrderId").val(poId).select2();
				}, 300);



				let desc = data[0].qutDescription;
				//CKEDITOR.instances['qutDescription'].setData(data[0].qutDescription);
				setTimeout(() => {
					CKEDITOR.instances['qutDescription'].setData(data[0].qutDescription);
				}, 500);
				$("#shippingHiddenId").val(data[0].shippingHiddenId);
				getAddressDetails(data[0].custId, data[0].shippingHiddenId);
				$("#project").val(data[0].project).select2();
				$("#projectName").val(data[0].projectName);
				$("#endCustomerName").val(data[0].endCustomerName);
				//$("#qutDescription").val(data[0].qutDescription);
				$("#taxtype").val(data[0].taxType);
				$("#salesOrder").val(data[0].salesOrder);
				$("#orderReceiveDate").val(data[0].orderReceiveDate);
				$("#orderReceiveTime").val(data[0].orderReceiveTime);
				$("#expectedShipmentDate").val(data[0].expectedShipmentDate);
				$("#ourRefNo").val(data[0].ourRefNo);
				$("#salesPaymentTerm").val(data[0].salesPaymentTerm);
				$("#deliveryMode").val(data[0].deliveryMode);
				$("#deliveryTerm").val(data[0].deliveryTerm);

				$("#soRef").val(data[0].soRef);
				$("#salesReferenceId").val(data[0].salesOrder);
				//$("#poId").val(data[0].poId);
				$("#tcsName").val(data[0].tcsId);
				$("#tcsId").val(data[0].tcsId);
				$("#tcsValue").val(data[0].tcsRate);
				$("#tcsAmount").val(data[0].tcsAmount);
				$("#terms").val(data[0].terms);
				$("#reference").val(data[0].reference);


				let itemSgst = 0;
				let itemCgst = 0;
				let itemIgst = 0;
				let lineTotal = 0;
				let taxableAmt = 0;

				data?.forEach(a => {
					itemSgst += a?.itemSgst || 0;
					itemCgst += a?.itemCgst || 0;
					itemIgst += a?.itemIgst || 0;
					lineTotal += a?.lineTotal || 0;
					taxableAmt += a?.taxableAmt || 0;
				});
				$("#grandTotal").val(commaFormattedWithDecimal(taxableAmt));
				$("#qSGST").val(commaFormattedWithDecimal(itemSgst));
				$("#qCGST").val(commaFormattedWithDecimal(itemCgst));
				$("#subTotal").val(commaFormattedWithDecimal(lineTotal));
				$("#qIGST").val(commaFormattedWithDecimal(itemIgst));

				$("#grandTotal1").val(commaFormattedWithDecimal(taxableAmt));
				$("#qSGST1").val(commaFormattedWithDecimal(itemSgst));
				$("#qCGST1").val(commaFormattedWithDecimal(itemCgst));
				$("#subTotal1").val(commaFormattedWithDecimal(lineTotal));
				$("#qIGST1").val(commaFormattedWithDecimal(itemIgst));

				$("#pan").val(data[0].pan);
				$("#gstNo").val(data[0].gstNo);

				getEditTaxType(data[0].custName);

				setTimeout(() => {
					$("#dateofSupply").val(data[0].dateOfSupply).trigger("change");
					$("#paymentTermId").val(data[0].paymentTerm);
				}, 1500);

				$("#invoiceDate").val(data[0].invoiceDate);
				$("#piRemarks").val(data[0].soRemarks);
				$("#charNumSN_piRemarks span").text((data[0].soRemarks || "").length);
				$("#dueDate").val(data[0].dueDate);
				$("#lrNumber").val(data[0].lrNumber);
				$("#transporterName").val(data[0].transporterName);
				$("#transporterId").val(data[0].transporterId);
				$("#vehicleNo").val(data[0].vehicleNo);
				$("#tMode").val(data[0].tMode).trigger("change");
				$("#type").val(data[0].type).trigger("change");

				$("#salesRId").val(data[0].salesRId);
				$("#saleDeliverysales1").val(data[0].salesRId);

				itemOptions.api.setRowData(data);
				// itemOptionsInvoice.api.setRowData(data);
				$("#doctbodyData").empty();

				if (data[0].documentList != null && data[0].documentList != "") {
					let documentList = data[0].documentList;
					for (var i = 0; i < documentList.length; i++) {

						let cls = 'ti-plus';

						if (documentList[i].fileName) {
							cls = 'ti-pencil';
						}

						let a = '<div class="form-group d-flex"><div class="">' +
							'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
							'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword,application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
							'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
							'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
							'<div id="validationDiv"></div></div>';

						var tbl = '<tr>'
							+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
							+ '<td>' + a + '</td>'
							+ '</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}
			});

}
function approveSoOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	var pendingQut = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
			reference += ',';
		}
		selectedRowsString += selectedRow.salesOrder;
		pendingQut += selectedRow.pendingQut;
	});
	var salesOrder = selectedRowsString;
	var approveStatus = 1;
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "view-saleorder-approve-th-ajax?approveStatus=" + approveStatus + "&salesOrder=" + salesOrder + "&pendingQut=" + pendingQut,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				viewSalesOrder();
				toastr.success('Sales Order Approved Successfully');
				setTimeout(() => {
					const allNodes = [];
					gridOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});
					const rowNode = allNodes.find(node => node.data.salesOrder == salesOrder);
					if (rowNode) {
						rowNode.setSelected(true);
						gridOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 2500);
			} else {
				$('.loader').hide();
			}
		},
		error: function(data) {
		}
	});
}


function deleteSoOnclick() {

	Swal.fire({
				title: 'Are you sure?',
				text: 'Do you really want to delete this order?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes, delete it!',
				cancelButtonText: 'No, keep it',
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
			}).then((result) => {
				if (result.value) {
					var selectedRows = gridOptions.api.getSelectedRows();
					var selectedRowsString = '';
					selectedRows.forEach(function(selectedRow, index) {
						if (index > 0) {
							selectedRowsString += ',';
						}
						selectedRowsString += selectedRow.salesOrder;
					});
					if (selectedRowsString) {
						var item = {};
						item.salesOrder = selectedRowsString;
						$.ajax({
							type: "POST",
							url: "view-saleorder-delete",
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify(item),
							success: function(response) {
								if (response.message == "Success") {
									viewSalesOrder();
									// showLoader();
									toastr.success('Sales Order Deleted Successfully');
									/*setTimeout(() => {
										if (gridOptions.api) {
											gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
											hideLoader();
										}
									}, 1000);*/
				
								} else {
				
								}
							},
							error: function(data) {
								console.log(data)
							}
						})
					} else {
						document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
					}
				}
			})

}
function blockeOrderOnclick() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.sku;
	});

	var sku = selectedRowsString;
	var salesOrder = $("#salesOrder").val();
	var blockeOrder = 1;
	$.ajax({
		type: "GET",
		url: "view-saleorder-blockeOrder-th-ajax?blockeOrder=" + blockeOrder + "&salesOrder=" + salesOrder + "&sku=" + sku,
		async: false,
		success: function(response) {
			gridOptions.api.deselectAll();
			if (response.message == "Success") {
				toastr.success('Order Blocked Successfully');
				setTimeout(() => {
					const allNodes = [];
					gridOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});
					const rowNode = allNodes.find(node => node.data.salesOrder == salesOrder);
					if (rowNode) {
						rowNode.setSelected(true);
						gridOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
				$('#blockOrder').addClass('d-none');
			}

		},
		error: function(data) {
		}
	});
}
function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}
function disableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "endCustomerName", "orderReceiveDate", "expectedShipmentDate","ourRefNo", "project", "pan", "gstNo", "deliveryMode", "deliveryTerm", "salesPaymentTerm"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});

	CKEDITOR.instances.qutDescription?.setReadOnly(true);

}
function enableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "endCustomerName", "orderReceiveDate", "expectedShipmentDate","ourRefNo", "project", "pan", "gstNo", "deliveryMode", "deliveryTerm", "salesPaymentTerm"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});

	CKEDITOR.instances.qutDescription?.setReadOnly(false);
}
function enableFieldsEdit() {
	let fieldIds = ["custName", "purchaseOrderId", "endCustomerName", "orderReceiveDate", "expectedShipmentDate","ourRefNo", "project", "pan", "gstNo", "deliveryMode", "deliveryTerm", "piRemarks", "salesPaymentTerm"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	$('#cancelSo').removeClass('d-none');
	$('#editSo').addClass('d-none');
	// $('#deleteSo').addClass('d-none');
	$('#soApproveBtn').addClass('d-none');
	$('#addSo').addClass('d-none');
	CKEDITOR.instances.qutDescription?.setReadOnly(false);
}


var totalLine;
function calculateLineTotal() {
	var price = $("#unitPrice").val();
	var quantity = $("#quantity").val();
	var discount = $("#discount").val();
	var discountAmount = $("#discountAmount").val();

	if (discountAmount == '1') {
		if (price && quantity) {
			var mul = price * quantity;

			if (discount > mul) {

				$("#discount").val("0");
				totalLine = mul - discount;
				$("#lineTotal").val((mul).toFixed(2));
			} else {
				totalLine = mul - discount;
				$("#lineTotal").val((totalLine).toFixed(2));
			}

		} else {
			var mul = 0;
			$("#lineTotal").val((mul).toFixed(2));

		}
	} else {
		if (price && quantity) {
			var mul = price * quantity;
			if (discount > mul) {

				$("#discount").val("0");
				totalLine1 = (mul / 100) * discount;
				totalLine = mul - totalLine1;
				$("#lineTotal").val((mul).toFixed(2));
			} else {
				totalLine1 = (mul / 100) * discount;
				totalLine = mul - totalLine1;
				$("#lineTotal").val((totalLine).toFixed(2));
			}

		} else {
			var mul = 0;
			$("#lineTotal").val((mul).toFixed(2));

		}
	}

}
function priceCalculation() {
	var grid = [];
	itemOptions.api.forEachNode(function(rowNode, index) {

		grid.push(rowNode.data);
	});
	var len = grid.length;
	var val = 0;
	var val2 = 0;
	var val3 = 0;
	var val4 = 0;

	var taxType1 = $("#taxType").val();
	for (i = 0; i < len; i++) {

		if (taxType1 == 'true') {

			val2 = parseFloat(val2) + parseFloat(grid[i].itemSgst);
			val3 = parseFloat(val3) + parseFloat(grid[i].itemCgst);
		} else {

			val4 = parseFloat(val4) + parseFloat(grid[i].itemIgst);

		}

		val = parseFloat(val) + parseFloat(grid[i].lineTotal);


	}

	$("#subTotal").val(val.toFixed(2));
	$("#qIGST").val(val4.toFixed(2));
	$("#qCGST").val(val3.toFixed(2));
	$("#qSGST").val(val2.toFixed(2));

	var gTotal = 0.0;


	gTotal = parseFloat(val) + parseFloat(val2) + parseFloat(val3)
		+ parseFloat(val4);

	$("#grandTotal").val(gTotal.toFixed(2))

}
function calculateAdjustment() {
	var adjustment = $("#adjustment").val();
	if (adjustment == "" || adjustment == null || adjustment == "null") {
		adjustment = 0;
		$("#adjustment").val(adjustment);
	}
	var grandTotal = $("#grandTotal").val();
	if (grandTotal == "" || grandTotal == null || grandTotal == "null") {
		grandTotal = 0;
		$("#grandTotal").val(grandTotal);
	}
	var subTotal = $("#subTotal").val();
	var qSGST = $("#qSGST").val();
	var qCGST = $("#qCGST").val();
	var qIGST = $("#qIGST").val();
	let tcsAmount = '0';
	var totVal = parseFloat(subTotal) + parseFloat(qSGST)
		+ parseFloat(qCGST) + parseFloat(qIGST) + parseFloat(tcsAmount);
	if (adjustment && totVal) {
		var add = parseFloat(adjustment) + parseFloat(totVal);
		$("#grandTotal").val((add).toFixed(2));
	} else {
		var add = 0;
		$("#grandTotal").val((totVal).toFixed(2));
	}
}

function getGSTRateCal() { }

var pancardValid;

function pancardVal() {
	// var ctype = $('#customerType').val();
	var pancard = $('#pan').val();
	var pancardid = /^([A-Z]{5})([0-9]{4})([A-Z]{1})$/;
	var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;



	// PAN validation only for Business and Individual
	// if (ctype === 'Business' || ctype === 'Individual') {
	if (pancard !== '' && pancard === pancard.toUpperCase() && !format.test(pancard)) {
		if (pancardid.test(pancard)) {
			$("#error4").hide();  // Assuming this div is used for error display
			pancardValid = true;
			return '';
		} else {
			// $("#error4").show().html("Please enter a valid PAN card no.");
			pancardValid = false;
			return "Please enter a valid PAN card no.";
		}
	} else {
		$('#pan').val('');
		// $("#error4").show().html("PAN must be uppercase and without special characters.");
		pancardValid = false;
		return "PAN must be uppercase and without special characters.";
	}
	// }

	// For other types, skip PAN validation
	pancardValid = true;
	return '';
}

var GSTValid;
function ValidateGSTNumber() {
	var gstNumber = $('#gstNo').val().toUpperCase();
	var panNumber = $('#pan').val().toUpperCase();

	var gstRegex = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;
	var panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;

	if (gstNumber !== "") {

		if (panNumber === "" || !panRegex.test(panNumber)) {
			// toastr.error('Please enter a valid PAN before entering GST number.');
			GSTValid = false;
			return 'Please enter a valid PAN before entering GST number.';
		}

		if (gstRegex.test(gstNumber)) {
			var gstPan = gstNumber.substring(2, 12);

			if (!panRegex.test(gstPan)) {
				// toastr.error('PAN format inside GST is invalid.');
				GSTValid = false;
				return 'PAN format inside GST is invalid.';
			}

			if (gstPan !== panNumber) {
				// toastr.error('PAN number does not match the PAN inside the GST number.');
				GSTValid = false;
				return 'PAN number does not match the PAN inside the GST number.';
			}

			GSTValid = true;
			return '';
		} else {
			// toastr.error('Invalid GST format.');
			GSTValid = false;
			return 'Invalid GST format.';
		}
	}

	GSTValid = true;
	return '';
}

//
function changeAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		//nextTab('quotationInfLiId');
		return;
	}



	$('#mySAGrid').show();
	$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
	$('#changeAddress,#saveAddress').addClass('d-none');
	$('#closeAddress').removeClass('d-none');

}

function addAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		//nextTab('quotationInfLiId');
		return;
	}



	$('#mySAGrid').hide();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress,#addAddress').addClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').removeClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
}

function closeAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec,#addAddress').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
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

function checkAlphabet(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z. ]/g, '');
	tempVal = tempVal.replace(/^\w/, c => c.toUpperCase());

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (position == 1 && tempVal.charAt(0) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

function checkNumeric(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (tempVal.slice(-1) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

//Amount validation
function checkAmount(fieldId) {
	var myField = document.getElementById("openingBalance")
	var reg = /^\d{0,9}(\.\d{0,2})?$/;
	if (reg.test(myField.value)) {
		$("#" + fieldId).val();
		reg = '';
	} else {
		$("#" + fieldId).val(null);
	}
}
function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function saveAddress() {

	let fax = $("#fax2").val() ? $("#fax2").val() : "";
	let gstIn = $("#gstIn2").val() ? $("#gstIn2").val() : "";
	let city = $("#city2").val();
	let phone = $("#phone2").val() ? $("#phone2").val() : "";
	let state = $("#states2").val();
	let stateName = $("#states2 option:selected").text();
	let country = $("#country2").val();
	let countryName = $("#country2 option:selected").text();
	let zipcode = $("#zipCode2").val();
	let street1 = $("#street12").val();
	let street2 = $("#street22").val() ? $("#street22").val() : "";

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
			url: "view-saleorder-add-shipping-address",
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

function getStateDetails2() {

	var cname = $('#country2').val();

	$("#states2").empty();
	$("#states2").append('<option value="">Select</option>');

	if (cname) {
		$.ajax({
			type: "GET",
			url: "view-saleorder-stateListDataSO?id=" + cname,
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
function validFormDataInvoice() {
	var custName = $("#custName1").val();
	var type = $("#type").val();
	var invoiceDate = $("#invoiceDate").val();

	if (custName == null || custName.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}
	if (type == null || type.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Invoice Type Required');
		return false;
	}
	if (invoiceDate == null || invoiceDate.trim() == "") {
		nextTab('salesInfLiId');
		toastr.error('Proforma Invoice Date is required');
		return false;
	}

	return true;
}

function saveInvoice() {
	if (validProductData() && validFormDataInvoice()) {
		var datas = [];
		if (itemOptions.api.getDisplayedRowCount() > 0) {
			itemOptions.api.forEachNode(function(rowNode, indrx) {
				var obj = rowNode.data;
				obj.saleDeliverysales1 = $("#saleDeliverysales1").val();
				obj.type = $("#type").val();
				obj.custId1 = $('#custId1').val();
				obj.custName1 = $('#custName1').val();
				obj.soId = $("#soId").val();
				obj.salesRId = $("#salesRId").val();
				obj.invoiceDate = $("#invoiceDate").val();
				obj.piRemarks = $("#piRemarks").val();

				obj.expectedShipmentDate1 = $("#expectedShipmentDate1").val();

				obj.deliveryMode1 = $("#deliveryMode1").val();
				obj.deliveryTerm1 = $("#deliveryTerm1").val();
				obj.subTotal = parseFloat($("#subTotal1").val().replace(/,/g, '')) || 0;
				obj.grandTotal = parseFloat($("#grandTotal1").val().replace(/,/g, '')) || 0;
				obj.qIGST = parseFloat($("#qIGST1").val().replace(/,/g, '')) || 0;
				obj.qCGST = parseFloat($("#qCGST1").val().replace(/,/g, '')) || 0;
				obj.qSGST = parseFloat($("#qSGST1").val().replace(/,/g, '')) || 0;

				var tt = $('#taxType').val();
				if (tt == 'true') {
					obj.taxType = true;
				} else {
					obj.taxType = false;
				}
				obj.shippingHiddenId = $("#shippingHiddenId").val();
				obj.total = $("#total").val();


				datas.push(obj);
			});
		} else {
			var obj = {};
			obj.saleDeliverysales1 = $("#saleDeliverysales1").val();
			obj.type = $("#type").val();
			obj.custId1 = $('#custId1').val();
			obj.custName1 = $('#custName1').val();
			obj.soId = $("#soId").val();
			obj.salesRId = $("#salesRId").val();
			obj.invoiceDate = $("#invoiceDate").val();
			obj.piRemarks = $("#piRemarks").val();

			obj.expectedShipmentDate1 = $("#expectedShipmentDate1").val();
			obj.ourRefNo = $("#ourRefNo").val();

			obj.deliveryMode1 = $("#deliveryMode1").val();
			obj.deliveryTerm1 = $("#deliveryTerm1").val();
			obj.subTotal = parseFloat($("#subTotal1").val().replace(/,/g, '')) || 0;
			obj.grandTotal = parseFloat($("#grandTotal1").val().replace(/,/g, '')) || 0;
			obj.qIGST = parseFloat($("#qIGST1").val().replace(/,/g, '')) || 0;
			obj.qCGST = parseFloat($("#qCGST1").val().replace(/,/g, '')) || 0;
			obj.qSGST = parseFloat($("#qSGST1").val().replace(/,/g, '')) || 0;


			var tt = $('#taxType').val();
			if (tt == 'true') {
				obj.taxType = true;
			} else {
				obj.taxType = false;
			}
			obj.shippingHiddenId = $("#shippingHiddenId").val();
			obj.total = $("#total").val();

			datas.push(obj);
		}
	}


	saveAllDeliverysales(datas);

}

function saveAllDeliverysales(datas) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-saleorder-add-invoice",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				viewSalesOrder();
				toastr.success('Data saved successfully');

				let saleDeliverysales1 = datas.length > 0 ? datas[0].saleDeliverysales1 : "";
				setTimeout(() => {
					let found = false;
					gridOptions.api.forEachNode((node) => {
						if (node.data.saleDeliverysales1 === saleDeliverysales1) {
							node.setSelected(true);
							gridOptions.api.ensureIndexVisible(node.rowIndex); // scroll to the row
							found = true;
						}
					});
					if (!found) {
						let firstRow = gridOptions.api.getDisplayedRowAtIndex(0);
						if (firstRow) {
							firstRow.setSelected(true);
						}
					}
				}, 2000); // try 100ms instead of 2000ms

			}

		},
		error: function(datas) {
			$('.loader').hide();
		}
	});
}

function getIdentity(type) {
	$.ajax({
		type: "GET",
		url: "view-saleorder-get-insertedid-data?type=" + type,
		success: function(response) {
			if (response.message == "success") {
				setTimeout(() => {
					$("#salesRId").val(response.body[0].key);
				}, 1000);

			}
		},
		error: function(e) {
		}
	});
}

function downloadBtn() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	var id = selectedData[0].invoiceId;
	var poId = selectedData[0].poNo;

	window.open("view-saleorder-invoice-Pdf?id=" + window.btoa(id) + "&poId=" + window.btoa(poId), '_blank');
}
//function for download
function excelDownload() {
	var params = {
		fileName: 'Sales_Order_list.csv', // Specify your custom filename here
	};
	gridOptions.api.exportDataAsCsv(params);
}
//
var pno;
function filterView() {
	var pages;
	var pageno = pno;
	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();	
	
	if (fDate && tDate) {
        var fromDateObj = new Date(fDate.split("-").reverse().join("-")); // assumes DD-MM-YYYY
        var toDateObj = new Date(tDate.split("-").reverse().join("-"));

        if (toDateObj < fromDateObj) {
            toastr.error("Please choose 'To Date' greater than or equal to 'From Date'");

            // Clear grid data if invalid date
            gridOptions.api.setRowData([]);
 			itemOptions.api.setRowData([]);
            add();
            $('#totalCandidate').find('span').html(0);
            $('#totalPageno').val(0);

            return; // Stop execution
        }
    }
	
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-saleorder-filter-data?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
        var allData = jsonData.viewSalesOrder;

        if (allData && allData.length > 0) {
            gridOptions.api.setRowData(allData);

            var totalRowCount = gridOptions.api.getModel().getRowCount();
            $('#totalCandidate').find('span').html(totalRowCount);
            $('#totalPageno').val(allData[0].totalPageno);
            pages = allData[0].totalPageno;

            // Only setTimeout added here
            setTimeout(function() {
                const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
                if (firstRowNode) {
                    firstRowNode.setSelected(true);
                }
            }, 100);

        } else {
            gridOptions.api.setRowData([]);
            itemOptions.api.setRowData([]);
            add();
        }
        //createPagination(pages, pageno);
    });
}