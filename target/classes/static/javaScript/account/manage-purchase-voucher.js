let vtype = '';
$(function() {

	const urlParams = new URLSearchParams(window.location.search);
	vtype = urlParams.get('type');

	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var gridDiv11 = document.querySelector('#tdsInvoiceGrid');
	new agGrid.Grid(gridDiv11, tdsInvoiceGridOptions);
	
	$("#invListDiv").hide();

	$("#editLedgerButton").attr("disabled", true);
	$("#viewItemDtlsSecId,#viewItemDtlsSecId2").addClass("d-none");
	$("#itemLedgerId").select2();
	gridOptions.api.setRowData("");
	/* agGrid.simpleHttpRequest({
		url : "purchase-voucher-view"
	}).then(function(data) {
		
		console.log(JSON.stringify(data))
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewPurchaseVoucher;
		var len = allData.length;
		$('#totalReq').find('span').html(len);
		gridOptions.api.setRowData(allData);

	}); */

	/* current date filter data */
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();

	$("#tdsTransactionDate").val(fromDate);

	$("#myGrid").show();
	$("#delete").attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
	//$("#voucherClass").attr("disabled", true);

	const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var d = new Date();
	var month = monthNames[d.getMonth() + 1];
	var day = d.getDate();
	var output = (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + d.getFullYear();
	$("#purchaseDate").text(output);
	var a = new Date(output);
	var b = weekday[a.getDay()];
	$("#purchaseDay").text(b);

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);
	itemOptions.api.setRowData();

	var dateFormat = localStorage.getItem("dateFormat");
	function parseDate(dateStr, format) {
		// Example assumes format is DD-MM-YYYY
		var parts = dateStr.split('-');
		return new Date(parts[2], parts[1] - 1, parts[0]); // YYYY, MM, DD
	}

	// Function to update date pickers based on selected range
	function updateDatePickers(yearStart, yearEnd) {
		var minDate = `01-04-${yearStart}`;
		var maxDate = `31-03-${yearEnd}`;
		var minDateParsed = parseDate(minDate, dateFormat);
		var maxDateParsed = parseDate(maxDate, dateFormat);

		$("#toDateCalendar").datetimepicker('destroy').datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			minDate: minDateParsed,
			maxDate: maxDateParsed
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		});

		$('#fromDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		});

		$("#toDateCalendar2").datetimepicker('destroy').datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			minDate: minDateParsed,
			maxDate: maxDateParsed
		}).on("change", function() {
			$('#toDate').val($(this).val());
		});

		$('#toDate').blur(function() {
			$("#toDateCalendar2").val($(this).val());
		});
	}

	// Function to handle dropdown change
	function handleDropdownChange() {
		var datee = $("#orderStatusFilter").val();

		// Extract year range from selected value
		var yearRange = datee.split('-');
		if (yearRange.length === 2) {
			var yearStart = yearRange[0];
			var yearEnd = yearRange[1];

			// Update date pickers with new date range
			updateDatePickers(yearStart, yearEnd);
		}
	}

	// Attach change event handler to dropdown
	$("#orderStatusFilter").change(function() {
		handleDropdownChange();
	});

	// Initialize date format and default date values
	var dateFormat = localStorage.getItem("dateFormat");
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var toDate = dateString.toString();

	$("#toDate").val(toDate);
	$("#fromDate").val(fromDate);

	// Set initial date range based on current selection
	handleDropdownChange();

	viewFilteredData();

	// transaction date for tds
	$("#tdsTransactionDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#tdsTransactionDate').val($(this).val());
	})

	$('#tdsTransactionDate').blur(function() {
		$("#tdsTransactionDateCalendar").val($(this).val());
	})
	
	$("#invoiceDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#invoiceDate').val($(this).val());
	})

	$('#invoiceDate').blur(function() {
		$("#invoiceDateCalendar").val($(this).val());
	})
	
	$("#voucherDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#voucherDate').val($(this).val());
	})

	$('#voucherDate').blur(function() {
		$("#voucherDateCalendar").val($(this).val());
	})

	$("#itemLedgerId1,#itemId1").select2();
});

const columnDefs11 = [
	{ headerName: 'Invoice Id', field: 'invoiceId' },
	{ headerName: 'Invoice Date', field: 'invoiceDate', 
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	},
	{ 
		headerName: 'Amount', 
		field: 'subTotal', 
		cellStyle: params => {
			if (params.node.rowPinned === 'bottom') {
				return {
					textAlign: 'right',
					fontWeight: 'bold',
					backgroundColor: '#f0f0f0'
				};
			}
			return {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			};
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	}
];

const tdsInvoiceGridOptions = {
	columnDefs: columnDefs11,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 238
	},
	getRowStyle: function(params) {
		if (params.node.rowPinned) {
			return {
				fontWeight: 'bold',
				backgroundColor: '#e6f2ff'  // light blue
			};
		}
	}
};

function addLedger() {
	$("#viewItemListSecId,#viewItemDtlsSecId").addClass("d-none");
	$("#viewItemDtlsSecId2").removeClass("d-none");
	
	$("#itemId1,#itemLedgerId1").val('').select2();
	$("#itemHSNCOde1,#itemUnit1,#itemUnitId1,#itemGSTRate1,#itemQty1,#itemUnitPrice1").val('');
}

function closeLedger2() {
	$("#viewItemDtlsSecId,#viewItemDtlsSecId2").addClass("d-none");
	$("#viewItemListSecId").removeClass("d-none");
	
	$("#itemId1,#itemLedgerId1").val('').select2();
	$("#itemHSNCOde1,#itemUnit1,#itemUnitId1,#itemGSTRate1,#itemQty1,#itemUnitPrice1").val('');
}

function setItemDetails() {
	const select = document.getElementById('itemId1');
	const selectedOption = select.options[select.selectedIndex];
	const hsn = selectedOption.getAttribute('data-hsn');
	const unitid = selectedOption.getAttribute('data-unitid');
	const unit = selectedOption.getAttribute('data-unit');
	const gstrate = selectedOption.getAttribute('data-gstrate');
	const ledger = selectedOption.getAttribute('data-ledger');
	
	$("#itemLedgerId1").val(ledger).select2();
	$("#itemHSNCOde1").val(hsn);
	$("#itemUnitId1").val(unitid);
	$("#itemUnit1").val(unit);
	$("#itemGSTRate1").val(gstrate);
}

function editLedger() {
	$("#viewItemListSecId,#viewItemDtlsSecId2").addClass("d-none");
	$("#viewItemDtlsSecId").removeClass("d-none");
	$("#itemName,#itemQty,#itemUnitPrice").val('');
	$("#itemLedgerId").val('').select2();

	let selectedRows = itemOptions.api.getSelectedRows();

	let skuName = selectedRows[0]?.skuName;
	let ledgerId = selectedRows[0]?.ledgerId;
	let invItemId = selectedRows[0]?.invItemId;
	let productQuantity = selectedRows[0]?.productQuantity;
	let productUnitPrice = selectedRows[0]?.productUnitPrice;
	if (selectedRows && selectedRows.length > 0) {
		$("#itemName").val(skuName);
		$("#itemQty").val(productQuantity);
		$("#itemUnitPrice").val(productUnitPrice);
		$("#invItemId").val(invItemId);
		$("#itemLedgerId").val(ledgerId).select2();
	}
}

function closeLedger() {
	$("#viewItemDtlsSecId,#viewItemDtlsSecId2").addClass("d-none");
	$("#viewItemListSecId").removeClass("d-none");
	$("#itemName,#invItemId,#itemQty,#itemUnitPrice").val('');
	$("#itemLedgerId").val('').select2();
}

function updateVoucher() {
	
	let selectedRows = gridOptions.api.getSelectedRows();

	let invId = '';
	let voucherId = '';
	if (selectedRows && selectedRows.length > 0) {
		invId = selectedRows[0]?.invoiceId;
		voucherId = selectedRows[0]?.journalVoucher;
	}
	
	let chNo = $("#purchaseNumber").val();
	let chDate = $("#invoiceDate").val();
	let vchDate = $("#voucherDate").val();
	
	let obj = { invId, voucherId, chNo, chDate, vchDate };

	showLoader();
	
	$.ajax({
		type: "POST",
		url: "purchase-voucher-update-invoice",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				$("#editLedgerButton").attr("disabled",true);
				toastr.success("Invoice modified successfully");
				viewFilteredData(voucherId);
				closeLedger();
			} else {
				toastr.error(resp.message);
			}
		}, error: err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function updateItemLedger() {

	let selectedRows = itemOptions.api.getSelectedRows();

	let invId = '';
	let voucherId = '';
	let productGst = 0;
	if (selectedRows && selectedRows.length > 0) {
		invId = selectedRows[0]?.productInvoiceId;
		voucherId = selectedRows[0]?.voucherId;
		productGst = selectedRows[0]?.productGst;
	}

	let invItemId = $("#invItemId").val();
	let ledgerId = $("#itemLedgerId").val();
	
	let itemQty = $("#itemQty").val();
	let itemPrice = $("#itemUnitPrice").val();
	
	if (!ledgerId) return showError('Ledger Required');
	if (!itemQty) return showError('Quantity Required');
	if (!itemPrice) return showError('Unit Price Required');

	let obj = { invItemId, ledgerId, invId, voucherId, itemQty, itemPrice, productGst };

	showLoader();

	$.ajax({
		type: "POST",
		url: "purchase-voucher-update-item-ledger",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				$("#editLedgerButton").attr("disabled",true);
				toastr.success("Item modified successfully");
				viewFilteredData(voucherId);
				closeLedger();
			} else {
				toastr.error(resp.message);
			}
		}, error: err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function addItemLedger() {

	let selectedRows = gridOptions.api.getSelectedRows();

	let invId = '';
	let voucherId = '';
	if (selectedRows && selectedRows.length > 0) {
		invId = selectedRows[0]?.invoiceId;
		voucherId = selectedRows[0]?.journalVoucher;
	}

	let itemId = $("#itemId1").val();
	let ledgerId = $("#itemLedgerId1").val();
	let hsn = $("#itemHSNCOde1").val();
	let unit = $("#itemUnitId1").val();
	let productGst = $("#itemGSTRate1").val();
	
	let itemQty = $("#itemQty1").val();
	let itemPrice = $("#itemUnitPrice1").val();
	
	if (!itemId) return showError('Item/SKU Required');
	if (!ledgerId) return showError('Ledger Required');
	if (!itemQty) return showError('Quantity Required');
	if (!itemPrice) return showError('Unit Price Required');

	let obj = { itemId, ledgerId, invId, voucherId, hsn, unit, itemQty, itemPrice, productGst };

	showLoader();

	$.ajax({
		type: "POST",
		url: "purchase-voucher-add-item-ledger",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				$("#editLedgerButton").attr("disabled",true);
				toastr.success("Item added successfully");
				viewFilteredData(voucherId);
				closeLedger();
			} else {
				toastr.error(resp.message);
			}
		}, error: err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function addNarration() {

	let selectedRows = gridOptions.api.getSelectedRows();

	let voucherId = '';
	if (selectedRows && selectedRows.length > 0) {
		voucherId = selectedRows[0]?.journalVoucher;
	}

	let vchNarration = $("#vchNarration").val();

	if (!vchNarration) return showError('Narration required');

	let obj = { vchNarration, voucherId };

	showLoader();

	$.ajax({
		type: "POST",
		url: "purchase-voucher-update-narration",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				toastr.success("Narration updated successfully");
				viewFilteredData(voucherId);
			} else {
				toastr.error(resp.message);
			}
		}, error: err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function approvePVoucher() {

	let selectedRows = gridOptions.api.getSelectedRows();

	let voucherId = '';
	if (selectedRows && selectedRows.length > 0) {
		voucherId = selectedRows[0]?.journalVoucher;
	}

	let status = "1";

	let obj = { status, voucherId };

	showLoader();

	$.ajax({
		type: "POST",
		url: "purchase-voucher-approve-voucher",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				toastr.success("Voucher approved successfully");
				viewFilteredData(voucherId);
			} else {
				toastr.error(resp.message);
			}
		}, error: err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function getVoucherNumber() {
	$.ajax({
		type: "GET",
		url: "receipt-voucher-vouchernumber",
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#purchaseNumber").text(response.body[0].key);
			}
		}
	});

}

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})
//search bar

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

$('input,textarea').focus(
	function() {
		$(this).data('placeholder', $(this).attr('placeholder')).attr(
			'placeholder', '');
	}).blur(function() {
		$(this).attr('placeholder', $(this).data('placeholder'));
	});

var count = 0;
function allCheck() {
	count++;

	if (count == 1) {
		$('.checkCls').prop("checked", true);
	} else {
		count = 0;
		$('.checkCls').prop("checked", false);
	}
}

var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
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

function valueFormatForQN(value) {

	if (value !== null && value !== undefined) {
		var parts = value.toString().split(' ');
		var numberPart = parts[0];
		var suffix = parts.length > 1 ? ' ' + parts[1] : '';

		var numberParts = numberPart.split('.');
		var integerPart = numberParts[0];
		var decimalPart = numberParts.length > 1 ? '.' + numberParts[1] : '';

		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);

		return formattedIntegerPart + decimalPart + suffix;
	} else {
		return '';
	}
}

function customCellRenderer(params) {
	if (params.value && typeof params.value === 'string') {
		const values = params.value.split(',').map((value, index) => {
			return `<span style="color: #3467af;  font-size:12px; font-weight:700;">(${index + 1})</span> ${value.trim()}`;
		});
		return values.join(', ');
	}
	return params.value;
}

function customCellForUnitPrice(params) {
	if (params.value && typeof params.value === 'string') {
		const values = params.value.split(',').map((value, index) => {
			var priceValue = amountFormatter(value.trim());
			return `<span style="color: #3467af;  font-size:12px; font-weight:700;">(${index + 1})</span> ${priceValue}`;
		});
		return values.join(', ');
	}
	return params.value;
}
// column Defs
const columnDefs =
	[
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			maxWidth: 30,
			pinned: 'left',
			sortable: false,
			filter: false,
			resizable: true
		}, {
			headerName: 'Voucher No',
			field: "journalVoucher",
			pinned: 'left',
			width: 120
		},
		{
			headerName: "Voucher Date",
			field: "TransactionDate",
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "Tax Invoice",
			field: "challanInvoice",
			width: 120,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "Invoice Date",
			field: "challanDate",
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "Cost Center Name",
			field: "costCenter",
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "NAME OF THE SUPPLIER",
			field: "vendorName",
			minWidth: 200,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "GSTIN NO",
			field: "gstno",
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},

		{
			headerName: "Voucher Type",
			field: "voucherType",
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "Voucher Class",
			field: "voucherClass",
			width: 150,
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "GRR Number",
			field: "grrNumber",
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "MATERIAL CODE",
			field: "materialCode",
			hide: true,
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},

			cellRenderer: customCellRenderer
		}
		,
		{
			headerName: "NAME OF THE MATERIAL",
			field: "itemname",
			hide: true,
			width: 250,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},


		{
			headerName: "HSN Code",
			field: "hsncode",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "UNIT",
			field: "unit",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "QTY BILLED",
			field: "billqnty",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "QTY RECEIVED",
			field: "rcvqnty",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "SHORTAGE",
			field: "shortage",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "RATE P/U",
			field: "unitprice",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			/* cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			} */
			cellRenderer: customCellForUnitPrice
		},
		{
			headerName: "FREIGHT",
			field: "frightAmount",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "QNTY & UNIT",
			field: "qunty",
			width: 150,
			hide: true,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return valueFormatForQN(value);
			}
		},

		{
			headerName: "GST RATE",
			field: "gstrate",
			hide: true,
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},

		{
			headerName: "TAXABLE AMOUNT",
			field: "taxableamt",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "IGST",
			field: "igst",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "CGST",
			field: "cgst",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "SGST",
			field: "sgst",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},

		{
			headerName: "INVOICE VALUE",
			field: "invoiceval",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: "TDS Amount",
			field: "tdsAmount",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: "Payable Amount",
			field: "finalPayableAmt",
			width: 150,
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "Status",
			field: "approveStatus",
			width: 150,
			cellStyle: params => {
				if (!params.value || params.value === "PENDING") {
					return {
						textAlign: 'center',
						fontFamily: 'Montserrat, sans-serif',
						color: '#ff5757 !important',
						fontSize: 'smaller',
						fontWeight: 'bold'
					};
				} else if (params.value === "APPROVED") {
					return {
						textAlign: 'center',
						fontFamily: 'Montserrat, sans-serif',
						fontSize: 'smaller',
						color: '#1b21e9 !important',
						fontWeight: 'bold'
					};
				}
				return {
					textAlign: 'center',
					fontFamily: 'Montserrat, sans-serif',
					fontSize: 'smaller',
					fontWeight: 'bold'
				};
			}
		},
		{
			headerName: "Payment Status",
			field: "paymentStatus",
			width: 150,
			valueGetter: params => {
				return params.data.paymentStatus ? params.data.paymentStatus : "Pending";
			},
			cellStyle: params => {
				if (!params.value || params.value === "Pending") {
					return {
						textAlign: 'center',
						fontFamily: 'Montserrat, sans-serif',
						color: '#ff5757 !important',
						fontSize: 'smaller'
					};
				} else if (params.value === "Fully Paid") {
					return {
						textAlign: 'center',
						fontFamily: 'Montserrat, sans-serif',
						fontSize: 'smaller',
						color: '#1b21e9 !important',
						fontWeight: 'bold'
					};
				}
				return {
					textAlign: 'center',
					fontFamily: 'Montserrat, sans-serif',
					fontSize: 'smaller'
				};
			}
		},
		{
			headerName: "Invoice Id",
			field: "invoiceId",
			maxWidth: 120,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
	];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		minWidth: 100,
		flex: 1
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.journalVoucher;
	}

};


var itemDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: "SlNo",
		field: "serialNumber",
		width: 70,
		/*		cellRenderer: function(params) {
					if (params.data.slNo) {
						return '<a onclick=editProduct("' + params.data.serialNumber
							+ '") href="javascript:void(0)">'
							+ params.data.serialNumber + '</a>';
					} else {
						return '<a onclick=editProduct("' + params.data.serialNumber
							+ '") href="javascript:void(0)">'
							+ params.data.serialNumber + '</a>';
					}
				}*/
	}, {

		headerName: 'HSN CODE',
		field: "productHsncode",
		width: 100,
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.productHsncode
				+ '</div>';
		}
	},
	{
		headerName: 'Item Name',
		field: "skuName",
		width: 170,
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.skuName
				+ '</div>';
		}
	},
	{
		headerName: 'Ledger',
		field: "ledgerName",
		width: 170,
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.ledgerName
				+ '</div>';
		}
	},
	{
		headerName: 'Quantity',
		field: 'productQuantity',
		width: 100,
		valueFormatter: currencyFormatter,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var quantity = parseFloat(params.data.productQuantity).toFixed(2);
			if (isNaN(quantity)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(quantity) + '</div>';
			}
		}
	}, {
		headerName: 'Unit',
		field: "quantityUnit",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.quantityUnit
				+ '</div>';
		}
	}, {
		headerName: 'Unit Price',
		field: 'productUnitPrice',
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var unitPrice = parseFloat(params.data.productUnitPrice).toFixed(2);
			if (isNaN(unitPrice)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(unitPrice) + '</div>';
			}
		}
	},
	{
		headerName: 'Discount',
		field: "productDiscount",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var Discount = parseFloat(params.data.productDiscount).toFixed(2);
			if (isNaN(Discount)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(Discount) + '</div>';
			}
		}
	},
	{
		headerName: 'Taxable Amount',
		field: "productAmount",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var Amount = parseFloat(params.data.productAmount).toFixed(2);
			if (isNaN(Amount)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(Amount) + '</div>';
			}
		}
	},
	{
		headerName: 'GST Rate',
		field: "productGst",
		width: 80,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var gstRate = parseFloat(params.data.productGst).toFixed(2);
			if (isNaN(gstRate)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(gstRate) + '</div>';
			}
		}
	},
	{
		headerName: 'CGST',
		field: "productCgst",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var Cgst = parseFloat(params.data.productCgst).toFixed(2);
			if (isNaN(Cgst)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(Cgst) + '</div>';
			}
		}
	}, {
		headerName: 'SGST',
		field: "productSgst",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var Sgst = parseFloat(params.data.productSgst).toFixed(2);
			if (isNaN(Sgst)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(Sgst) + '</div>';
			}
		}
	}, {
		headerName: 'IGST',
		field: "productIgst",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var Igst = parseFloat(params.data.productIgst).toFixed(2);
			if (isNaN(Igst)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(Igst) + '</div>';
			}
		}
	}, {
		headerName: 'Total Amount',
		field: "productTaxableAmount",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var taxableAmount = parseFloat(params.data.productTaxableAmount).toFixed(2);
			if (isNaN(taxableAmount)) {
				return '<div style="color:black;font-weight: bold;">N/A</div>';
			} else {
				return '<div style="color:black;font-weight: bold;">' + amountFormatter(taxableAmount) + '</div>';
			}
		}
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
		width: 251,
		height: 10
	},
	onSelectionChanged: rowSelectItem,
	pagination: false,
	paginationPageSize: 5,
	getRowNodeId: function(data) {
		return data.invItemId;
	}
};
function rowSelectItem() {

	var selectedRows = itemOptions.api.getSelectedRows();

	if (selectedRows && selectedRows.length > 0) {
		$("#editLedgerButton").attr("disabled", false);

	} else {
		$("#editLedgerButton").attr("disabled", true);
	}
}
var deleteId = "";
var gridSelectedValue;
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	console.log("selected rows data --->", selectedRows);
	
	$("#accountGroup").val('');

	deleteId = "";
	voucherType = "";
	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '' + selectedRows[i].journalVoucher + ',';
		voucherType = voucherType + selectedRows[i].voucherType + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	voucherType = voucherType.substring(0, voucherType.length - 1);
	console.log(deleteId)
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		const selectedNode = gridOptions.api.getSelectedNodes()[0];
		gridOptions._lastSelectedNode = selectedNode;
		$('#normalPayment').attr("disabled", true);
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		gridSelectedValue = selectedRows;
		var purchaseId = selectedRows[0].invoiceId;
		var vcIDD = selectedRows[0].journalVoucher;
		editPage(purchaseId);
		getTdsSection(purchaseId);
		$(".invoice_id_info_text").removeClass("d-none");
		$("#invoiceIdText").text(vcIDD);
		$("#parent_next_btn").removeClass("d-none");

		$("#purchaseRegisterExcel").removeClass("d-none");
		$("#purchaseRegisterPdf").removeClass("d-none");

		$("#voucherClassTab").removeClass("d-none");

		nextBtnFunction('manageControl')
		
		$("#accountGroup").val(selectedRows[0].groupName);

		//tds 
		var tdsAmount = selectedRows[0].tdsAmount;
		if (tdsAmount > 0) {
			console.log("Tds Amount is present -->", tdsAmount);
			// $("#tdsTab").addClass("d-none");
			// $("#vchr_Nxt_btn").addClass("d-none");
		}
		else {
			console.log("Tds amount is not present -->", tdsAmount);
			$("#tdsTab").removeClass("d-none");
			$("#vchr_Nxt_btn").removeClass("d-none");
		}


		$("#partyGSTNo").val(selectedRows[0]?.gstNo || '');
		$("#partyPANNo").val(selectedRows[0]?.panNo || '');
		$("#vchNarration").val(selectedRows[0]?.narration || '');

		if (selectedRows[0]?.approveStatus == 'APPROVED') {
			$("#parent_apv_btn,#saveNarration,#saveTdsBtn,#editLedgerButton,#addLedgerButton,#update_btn").addClass("d-none");
			$("#vchNarration,#payTdsAmount,#tdsLedgerId,#offAmount,#tdsSection,#purchaseNumber,#invoiceDateCalendar").attr("disabled", true);
			$("#invoiceDateCalendar,#voucherDateCalendar").addClass('icon-dis');
		} else {
			$("#parent_apv_btn,#saveNarration,#saveTdsBtn,#editLedgerButton,#addLedgerButton,#update_btn").removeClass("d-none");
			$("#vchNarration,#payTdsAmount,#tdsLedgerId,#offAmount,#tdsSection,#purchaseNumber,#invoiceDateCalendar").attr("disabled", false);
			$("#invoiceDateCalendar,#voucherDateCalendar").removeClass('icon-dis');
			
			if(selectedRows[0]?.tdsStatus == '1') {
				$("#editLedgerButton,#addLedgerButton").addClass("d-none");
				//$("#payTdsAmount,#tdsLedgerId,#offAmount,#tdsSection").attr("disabled", true);
			} else {
				$("#saveTdsBtn,#editLedgerButton,#addLedgerButton").removeClass("d-none");
				$("#payTdsAmount,#tdsLedgerId,#offAmount,#tdsSection").attr("disabled", false);
			}
		}

	} else {
		$('#normalPayment').attr("disabled", false);
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
		$("#invoiceIdText").text('');
		$(".invoice_id_info_text").addClass("d-none");
		$("#parent_next_btn").addClass("d-none");
		$("#purchaseRegisterExcel").addClass("d-none");
		$("#purchaseRegisterPdf").addClass("d-none");
		$("#voucherClassTab").addClass("d-none");
		if (gridOptions._lastSelectedNode) {
			gridOptions._lastSelectedNode.setSelected(true);
		}

		$("#partyGSTNo").val('');
		$("#partyPANNo").val('');
		$("#vchNarration").val('');

	}

	/* if(voucherType=='PURCHASE'){
		$("#voucherClass").attr("disabled", false);
	}
	else{
		$("#voucherClass").attr("disabled", true);
	} */


}
// for new button
function newBtn() {
	//	alert('hello');

	$("#approve").hide();
	$("#return").hide();
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$('#totalAmountFooterDiv').hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#fdateDiv").hide();
	$("#tdateDiv").hide();
	$("#fyearDiv").hide();
	$("#filterDiv").hide();
	$("#normalPayment").hide();
	$("#voucherClass").show();
	$("#demo").show();
	getVoucherNumber();
	$("#tdsbtn").show();
	//$("#purchaseRegisterExcel").hide();
	//$("#purchaseRegisterPdf").hide();
}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$("#demo").hide();
	$("#fdateDiv").show();
	$("#tdateDiv").show();
	$("#fyearDiv").show();
	$('#totalAmountFooterDiv').show();
	$("#filterDiv").show();
	$("#journalVoucher").text("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("");
	$('#accountNumber').val("");
	$('#status').val("");
	$("#normalPayment").show();
	$("#normalPayment").show();
	$("#tdsbtn").hide();
	$("#purchaseRegisterPdf").show();
	$("#purchaseRegisterExcel").show();
	$("#voucherClass").hide();
	gridOptions.api.deselectAll();

	viewFilteredData();
	$("#normalPayment").attr("disabled", false);
}



// Edit & stage change 
function editPage(id) {
	var editId = id.split(",");
	//var purchaseId = editId[0];
	var purchaseId = id;
	var modal = editId[1];

	/*var rowNode = gridOptions.api.getDisplayedRowAtIndex(rowIndex);
	if (rowNode) {
		rowNode.setSelected(true);
	} else {
		console.log("Row node not found for row index:", rowIndex);
	}*/

	// Hide various elements
	$("#normalPayment").hide();
	$("#tdsbtn").show();
	//$("#purchaseRegisterPdf").hide();
	//$("#purchaseRegisterExcel").hide();
	$("#addNew").hide();
	$.ajax({
		type: "GET",
		url: "purchase-voucher-edit?id=" + purchaseId + "&vtype=" + vtype,
		dataType: "json",
		async: false,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);

				console.log("json data-->", jsonData);

				var purchaseVoucherData = jsonData.editPurchaseVoucher[0];
				var productDetails = jsonData.viewProductDetails;
				var totalProductQuantity = 0.00;
				var quantityUnit = "";
				$('#buyerName').val(purchaseVoucherData.buyerName);
				$('#sellerName').val(purchaseVoucherData.sellerName);
				$('#voucherId').val(purchaseVoucherData.voucherId);
				$('#voucherDate').val(purchaseVoucherData.voucherDate);
				$('#voucherDateCalendar').val(purchaseVoucherData.voucherDate);
				$('#productGrnId').val(purchaseVoucherData.productGrnId);
				$('#purchaseNumber').val(purchaseVoucherData.invoiceId);
				$('#invoiceDate').val(purchaseVoucherData.invoiceDate);
				$('#invoiceDateCalendar').val(purchaseVoucherData.invoiceDate);
				$('#subTotal').val(amountFormatter(purchaseVoucherData.subTotal.toFixed(2)));
				$('#qSGST').val(amountFormatter(purchaseVoucherData.sgst.toFixed(2)));
				$('#qCGST').val(amountFormatter(purchaseVoucherData.cgst.toFixed(2)));
				$('#qIGST').val(amountFormatter(purchaseVoucherData.igst.toFixed(2)));
				$('#grandTotal').val(purchaseVoucherData.grandTotal);
				$('#tdsAmount').val(amountFormatter(parseFloat(purchaseVoucherData.tdsAmount).toFixed(2)));
				$('#vType').val(purchaseVoucherData.voucherType);
				$('#vClass').val(purchaseVoucherData.voucherClass);
				$('#tdsRate').val(purchaseVoucherData.tdsRate);

				//$('#totalQuantity').val(productDetails[0].productTotalQuantity);

				productDetails.map((element, index) => {
					let elementQuantity = element.productTotalQuantity.split(" ")[0];
					//console.log('elementQuantity==',elementQuantity)
					totalProductQuantity += parseFloat(elementQuantity?.toString()?.replace(/,/g, ""));
				})

				$('#totalQuantity').val(parseFloat(totalProductQuantity) + " " + quantityUnit);

				console.log('productDetails===', productDetails);
				itemOptions.api.setRowData(productDetails);

				if (purchaseVoucherData.tdsAmount > 0) {
					$('#tdsbtn').prop('disabled', true);
				}
				else {
					$('#tdsbtn').prop('disabled', false);
				}
			} else {
				console.error("Response code not success: ", response);
			}
		},
		error: function(xhr, status, error) {
			console.error("AJAX Error: ", status, error);
		}
	});
}

function getTdsSection(id) {
	
	$('#tdsSection').empty();
	$('#tdsSection').append("<option value=''>Select</option>");
	
	$.ajax({
		type: "GET",
		url: "purchase-voucher-get-tds-section?id=" + id + "&type=Purchase",
		dataType: "json",
		async: false,
		success: function(response) {
			if (response.code === "success") {
				if(response.body) {
					var jsonData = JSON.parse(response.body);
					console.log(jsonData)
					if(jsonData && jsonData.length > 0) {
						jsonData?.forEach(a => {
							let d = "<option value='"+a?.id+"' data-code='"+a?.rate+"'>"+a?.name+"</option>";
							$('#tdsSection').append(d);
							
							
						})
						
						let sel_tds_sec = jsonData[0]?.sel_tds_sec || '';
						if(sel_tds_sec) {
							$('#tdsSection').val(sel_tds_sec);
						}
					}
				}
				
			}
		}, error: function(err) {
			console.log(err);
		}
	});
	
}


function addReceiptInfo() {

}
function deleteJournalVoucher() {

	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "purchase-voucher-view"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
}


function approveVoucher() {
	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-approve-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "purchase-voucher-view"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
}

function rejectVoucher(id) {
	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-reject-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "purchase-voucher-view"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
}


function returnVoucher() {
	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-return-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "purchase-voucher-view"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
}

//view-account-journal-voucher


function sellerNameList(id) {
	var l = id.split("_");
	var counter = l[1];
	$.ajax({
		type: "POST",
		url: "view-account-journal-voucher-getAccountDebitGroup",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			console.log(response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#sellerName").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					content += '</ul>';
					$("#ledger_suggesstion").show();
					$("#ledger_suggesstion").html(content);

				}
				else {
					console.log("else: " + response);
					$("#sellerName").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					content += '</ul>';
					$("#ledger_suggesstion").show();
					$("#ledger_suggesstion").html(content);
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}

function autocompleteValue1(sGroupId, sGroupName, id, amount) {
	var l = id.split("_");
	var counter = l[1];

	if (sGroupId) {
		$("#sellerName").val(sGroupName + '( ' + sGroupId + ' )');
		$("#sellerNameId").val(sGroupId);
		//$("#debitCurrentBalance_"+counter).text(amount);
		$("#sellerName").attr('data-procat', sGroupName);
		$("#ledger_suggesstion").hide();


	} else {
		$("#sellerName").val("");
		$("#sellerNameId").val("");
		$("#sellerName").val("");
		$("#debitAccountSubGroup_" + counter).attr('data-procat', "");
		$("#ledger_suggesstion").hide();
	}
}

//function for auto filled of item Requisition






//function to validate Discount field

function extractNumber(obj, decimalPlaces, allowNegative) {
	var temp = obj.value;
	// avoid changing things if already formatted correctly
	var reg0Str = '[0-9]*';
	if (decimalPlaces > 0) {
		reg0Str += '\\.?[0-9]{0,' + decimalPlaces + '}';
	} else if (decimalPlaces < 0) {
		reg0Str += '\\.?[0-9]*';
	}
	reg0Str = allowNegative ? '^-?' + reg0Str : '^' + reg0Str;
	reg0Str = reg0Str + '$';
	var reg0 = new RegExp(reg0Str);
	if (reg0.test(temp)) return true;

	// first replace all non numbers
	var reg1Str = '[^0-9' + (decimalPlaces != 0 ? '.' : '') + (allowNegative ? '-' : '') + ']';
	var reg1 = new RegExp(reg1Str, 'g');
	temp = temp.replace(reg1, '');

	if (allowNegative) {
		// replace extra negative
		var hasNegative = temp.length > 0 && temp.charAt(0) == '-';
		var reg2 = /-/g;
		temp = temp.replace(reg2, '');
		if (hasNegative) temp = '-' + temp;
	}

	if (decimalPlaces != 0) {
		var reg3 = /\./g;
		var reg3Array = reg3.exec(temp);
		if (reg3Array != null) {
			// keep only first occurrence of .
			//  and the number of places specified by decimalPlaces or the entire string if decimalPlaces < 0
			var reg3Right = temp.substring(reg3Array.index + reg3Array[0].length);
			reg3Right = reg3Right.replace(reg3, '');
			reg3Right = decimalPlaces > 0 ? reg3Right.substring(0, decimalPlaces) : reg3Right;
			temp = temp.substring(0, reg3Array.index) + '.' + reg3Right;
		}
	}

	obj.value = temp;
}
function submitJournal(dataset) {
	swal.fire({
		title: "Are you sure want to Submit?",
		text: "Once Submited,Can't revert back !",
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6BB5",
		confirmButtonText: "Submit",
		showLoaderOnConfirm: true,
		reverseButtons: true,
		confirmButtonAriaLabel: 'Thumbs up, great!',
		cancelButtonText: 'Cancel',
		cancelButtonAriaLabel: 'Thumbs down',
		preConfirm: () => {
			return new Promise((resolve) => {
				setTimeout(() => {
					console.log("Doing async operation");
					resolve()
				}, 3000)
			})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-journal-voucher-add-journal",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(dataset),
				success: function(response) {
					if (response.message == "Success") {
						toastr.success({
							title: "Data saved successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/view-account-journal-voucher";
						})
					} else {
						toastr.error({
							title: response.code,
							text: response.message,
							type: "warning"
						})
					}
				}, error: function(response) {
					toastr.error(response.code);
				}
			}) //ajax ends
		}
	})//swal function block ends
}//submit function ends


//Function for cancel button
function funcLoad() {
	location.reload();
}

function getFromSubtotal() {
	var sum = 0;
	$(".debitAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val());
	})

	$("#fromTotalAmount").html(sum);
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val());
	})

	$("#toTotalAmount").html(sum);
}


/************for child table/********/
function openNav() {
	$("#itemName").val("");
	$("#quantity").val("");
	$("#categoryId").val("");
	$("#categoryName").val("");
	$("#itemUnitPrice").val("");
	$("#discount").val("");
	$("#gstRate").val("");
	$("#lineTotal").val("");
	$("#size").val("");
	$("#editProduct").val("");
	$("#dealerCode").val("");
	$("#productDimension").val("");
	$("#itemDiscount").val("");
	$("#customLength").val("");
	$("#customWidth").val("");
	$("#customThickness").val("");
	$('#regular').prop('checked', true);

	document.getElementById("mySidenav").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto; top:150px;";
	document.getElementById("main_content").style.width = "70%";


}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main_content").style.width = "100%";
	$('.formValidation').remove();
	$("#itemId").val('');
	$("#categoryId").val("");
	$("#categoryName").val("");
	$("#itemName").val('');
	$("#quantity").val('');
	$("#itemUnitPrice").val('');
	$("#discount").val('');
	$("#gstRate").val('');
	$("#size").val("");
	$("#lineTotal").val('');
	$("#editProduct").val(null);
	$("#productDimension").val("");
	$("#itemDiscount").val("");


}
function saveTableData() {
	var editProduct = $("#editProduct").val();
	var item = {};
	var data = 1;
	var validation = true;
	var sum = 0.0;
	var gstRate = 0.0;
	var qSGST = 0.0;
	var qCGST = 0.0;
	var qIGST = 0.0;

	var itemIgst = 0.0;
	var itemCgst = 0.0;
	var itemSgst = 0.0;
	var subtotal = 0.0;

	var perItemCGST = 0.0;
	var perItemSGST = 0.0;
	var productOrderQty = 0.0;
	if (validation) {
		itemOptions.api.forEachNode(function(rowNode, index) {
			if (!editProduct) {
				data = data + 1;
			}
		});

		var line = $('#lineTotal').val();
		var gst = $('#gstRate').val();
		perItemCGST = (parseFloat(line) - (parseFloat(line) / ((parseFloat(gst) + 100) / 100))) / 2;
		perItemSGST = (parseFloat(line) - (parseFloat(line) / ((parseFloat(gst) + 100) / 100))) / 2;
		productOrderQty = $('#quantity').val();
		item.itemId = $('#itemId').val();
		item.itemName = $('#itemName').val();
		item.quantity = $('#quantity').val();
		item.itemUnitPrice = $('#itemUnitPrice').val();
		item.discount = $('#itemDiscount').val();
		item.dealerCode = $('#dealerCode').val();
		item.gstRate = $('#gstRate').val();
		item.lineTotal = $('#lineTotal').val();
		item.categoryName = $('#categoryName').val();
		item.categoryId = $('#categoryId').val();
		item.productDimension = $('#productDimension').val();
		item.toggleRegularCustom = $('#toggleRegularCustom').val();
		item.itemCgst = perItemCGST;
		item.itemSgst = perItemSGST;


		if (item.quantity == "" || item.quantity == null) {
			toastr.error("Please Enter Valid</br> Quantity")
			return false;
		}
		if (editProduct > 0) {
			item.slNo = editProduct;
		} else {
			item.slNo = data;
		}
		//console.log(item)
		var quot = [];

		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);

			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});

			var len = quot.length;

			for (var i = 0; i < len; i++) {
				sum = sum + (parseFloat(quot[i].lineTotal));
				var taxType = $("#taxType").val();

				//console.log('taxtype===='+taxType)
				if (taxType == "true") {

					quot[i].itemIgst = quot[i].lineTotal * quot[i].gstRate / 100;
					qIGST = qIGST + quot[i].itemIgst;
					grandTotal = sum;
				} else {

					var totalGstAmnt = (parseFloat(quot[i].lineTotal) - (parseFloat(quot[i].lineTotal) / ((parseFloat(quot[i].gstRate) + 100) / 100)));
					quot[i].itemCgst = totalGstAmnt / 2;
					quot[i].itemSgst = totalGstAmnt / 2;
					qCGST = qCGST + quot[i].itemCgst;
					qSGST = qSGST + quot[i].itemSgst;
					grandTotal = sum;
					subtotal = (sum - (qCGST + qSGST));
				}
			}

			$("#subTotal").val(subtotal.toFixed(2));
			$("#qIGST").val(qIGST.toFixed(2));
			$("#qCGST").val(qCGST.toFixed(2));
			$("#qSGST").val(qSGST.toFixed(2));
			$("#grandTotal").val(grandTotal.toFixed(2))

		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			itemOptions.api.setRowData(quot);



			var len = quot.length;
			//console.log('length====>>>'+len)
			for (var i = 0; i < len; i++) {
				sum = sum + (parseFloat(quot[i].lineTotal));
				var taxType = $("#taxType").val();

				if (taxType == "true") {

					quot[i].itemIgst = quot[i].lineTotal * quot[i].gstRate / 100;
					qIGST = qIGST + quot[i].itemIgst;
					grandTotal = sum;
				} else {

					var totalGstAmnt = (parseFloat(quot[i].lineTotal) - (parseFloat(quot[i].lineTotal) / ((parseFloat(quot[i].gstRate) + 100) / 100)));

					quot[i].itemCgst = totalGstAmnt / 2;
					quot[i].itemSgst = totalGstAmnt / 2;

					qCGST = qCGST + quot[i].itemCgst;
					qSGST = qSGST + quot[i].itemSgst;
					grandTotal = sum;
					subtotal = (sum - (qCGST + qSGST));

				}
			}



			$("#subTotal").val(subtotal.toFixed(2));
			$("#qIGST").val(qIGST.toFixed(2));
			$("#qCGST").val(qCGST.toFixed(2));
			$("#qSGST").val(qSGST.toFixed(2));
			$("#grandTotal").val(grandTotal.toFixed(2))

		}


		$("#itemId").val('');
		$("#itemName").val('');
		$("#quantity").val('');
		$("#categoryName").val('');
		$("#productDimension").val('');
		$("#categoryId").val('');
		$("#itemUnitPrice").val('');
		$("#itemDiscount").val('');
		$("#dealerCode").val('');
		$("#gstRate").val('');
		$("#lineTotal").val('');
		$("#productDimensionId").val('');
		$("#editProduct").val(null);
	}
	closeNav();
}

//calculate Amount=(quantity*itemUnitPrice)-Discount
var grandTotal = 0;
var subTotal = 0;
var totalGst = 0;
var totalSgst = 0;
var totalCgst = 0;
var totalIgst = 0;
function calculateLineTotal() {
	var itemUnitPrice = parseFloat($("#itemUnitPrice").val());
	var quantity = parseFloat($("#quantity").val());
	var itemDiscount = parseInt($("#itemDiscount").val());
	var gstRate = ($("#gstRate").val());

	if (quantity) {
		$("#offeredProductQty").val('');
		var id = $("#productDimension").val();
		var length = id.substring(3, 5);
		if (length < 48) {
			$("#offeredProductQty").val('1');
		} else if (length >= 48) {
			$("#offeredProductQty").val('2');
		}
	}
	if (quantity) {
		if (itemDiscount > 0) {
			var priceAfterDiscount = ((itemUnitPrice * quantity) * (100 - itemDiscount)) / 100;
		} else {
			var priceAfterDiscount = (itemUnitPrice * quantity);
		}
		priceAfterDiscount = priceAfterDiscount.toFixed(2)
		$("#lineTotal").val(priceAfterDiscount);
	} else {
		$("#lineTotal").val("");
		return false;
	}

}

function getCurrentFinancialYear() {
	var fiscalyear = "";
	var today = new Date();
	if ((today.getMonth() + 1) <= 3) {
		fiscalyear = (today.getFullYear() - 1) + "-" + today.getFullYear()

	} else {
		fiscalyear = today.getFullYear() + "-" + (today.getFullYear() + 1)

	}
	$("#orderStatusFilter").val(fiscalyear);
	return fiscalyear
}


function viewFilteredData(vchId = '') {
	$('.loader').show();
	var fromDateFilter = $("#fromDate").val();
	var toDateFilter = $("#toDate").val();
	var validation = true;
	var totalIGSTAmount = 0.00;
	var totalCGSTAmount = 0.00;
	var totalSGSTAmount = 0.00;
	var totalPayableAmount = 0.00;
	var totalTdsAmount = 0.00;
	var totalInvoiceAmount = 0.00;
	
	$("#invListDiv").hide();
	tdsInvoiceGridOptions.api.setRowData([]);
	
	if (fromDateFilter == null || fromDateFilter == "") {
		toastr.error("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		toastr.error("Please Provide To Date");
		validation = false;
	}
	if (validation) {
		agGrid.simpleHttpRequest({
			url: "purchase-voucher-view-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter + "&vtype=" + vtype,
		}).then(function(data) {
			$('.loader').hide();

			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewPurchaseVoucher;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#totalAmountFooter").val("0.00");
				$("#no_purchase_voucher").removeClass("d-none");
				$("#purchase_vchr_dtls").addClass("d-none");
				// $("#tdsTab").addClass("d-none");
				$("#purchaseRegisterExcel").addClass("d-none");
				$("#purchaseRegisterPdf,#saveTdsBtn,#saveNarration,#voucherClassTab,#tdsTab").addClass("d-none");
				$("#vchNarration").val('');
			} else {

				$("#tdsTab,#saveTdsBtn,#saveNarration,#voucherClassTab,#tdsTab").removeClass("d-none");
				$("#no_purchase_voucher").addClass("d-none");
				$("#purchase_vchr_dtls").removeClass("d-none");
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				//gridOptions.api.setRowData(allData);
				var rowData = [];
				gridOptions.api.setRowData(rowData);

				gridOptions.api.setRowData(allData);

				if (allData && allData.length > 0) {

					if (vchId) {
						const rowNode = gridOptions.api.getRowNode(vchId);
						if (rowNode) {
							rowNode.setSelected(true);
						} else {
							gridOptions.api.forEachNode(function(node) {
								if (node.rowIndex === 0) {
									node.setSelected(true); // Select the first row
								}
							});
						}

					} else {
						gridOptions.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true); // Select the first row
							}
						});
					}

				}

				/*if (voucherId) {
					gridOptions.api.forEachNode(function(node) {
						if (node.data && node.data.journalVoucher === voucherId) {
							node.setSelected(true); // Select the matching row
							return; // Stop further iteration once found
						}
					});
				} else {
					var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
					if (firstRowNode) {
						firstRowNode.setSelected(true);
					}
				}*/
				var totalSum = allData.reduce(function(acc, curr) {
					return acc + parseFloat(curr.taxableamt);
				}, 0);

				for (var i = 0; i < len; i++) {
					totalIGSTAmount = totalIGSTAmount + parseFloat(allData[i].igst);
					totalCGSTAmount = totalCGSTAmount + parseFloat(allData[i].cgst);
					totalSGSTAmount = totalSGSTAmount + parseFloat(allData[i].sgst);
					totalPayableAmount = totalPayableAmount + parseFloat(allData[i].finalPayableAmt);
					totalTdsAmount = totalTdsAmount + parseFloat(allData[i].tdsAmount);
					totalInvoiceAmount = totalInvoiceAmount + parseFloat(allData[i].invoiceval);

				}
				$("#purchaseRegisterPdf").prop("disabled", false);
				$("#purchaseRegisterExcel").prop("disabled", false);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
				$("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
				$("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
				$("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
				$("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
				$("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
				$("#totalTdsAmount").val(amountFormatter(totalTdsAmount.toFixed(2)));
				$("#totalInvoiceAmount").val(amountFormatter(totalInvoiceAmount.toFixed(2)));
				var amount = amountFormatter(totalSum.toFixed(2))
				$("#totalAmountFooter").val(amount);

			}
		});
	}
}


function showTdsModal() {

	$('#invoiceListPopup').modal('show');
	$("#vendorInvoiceList").empty();
	$("#pVendorid").val('');
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();

	$("#tdsTransactionDate").val(todate);

	let selTdsRate = 0;
	
	$("#tdsSection").val('');
	$("#invListDiv").hide();

	var selectedValue = gridSelectedValue;
	var journalVoucher = selectedValue[0].journalVoucher;
	var invoiceid = selectedValue[0].invoiceId;
	$.ajax({
		type: "GET",
		url: "purchase-voucher-view-TdsAmount?voucherid=" + journalVoucher + "&invoiceid=" + invoiceid + "&vtype=" + vtype,
		async: false,
		success: function(response) {
			console.log(response.body);
			var data = JSON.parse(response.body);
			console.log(data.vendorTdsAmount);
			
			let s_obj = data?.vendorTdsAmount[0] || null;
			
			$("#totalAmnt_all").val(s_obj?.totalAmnt_all ||'0.00');
			$("#totalAmnt_noTds").val(s_obj?.totalAmnt_noTds || '0.00');
			var ddata=s_obj?.invoiceList || [];
			tdsInvoiceGridOptions.api.setRowData(ddata);
			
			selTdsRate = s_obj?.tdsRate || 0;
			
			let totalst = 0;
					
					ddata.forEach(row => {
						totalst += parseFloat(row.subTotal) || 0;
					});

					// ✅ Set raw numbers (not formatted) for valueFormatter to apply
					const pinnedRow = [{
						invoiceId: 'Total',
						subTotal: totalst
					}];

					tdsInvoiceGridOptions.api.setPinnedBottomRowData(pinnedRow);

			let selectedRows = gridOptions.api.getSelectedRows();
			let selTDSAmnt = '0.0';
			if (selectedRows && selectedRows.length > 0) {
				selTDSAmnt = selectedRows[0]?.tdsAmount || '0.0';
			}

			let tdsamnt = (selTDSAmnt != '0.0' && selTDSAmnt != '0.00' && selTDSAmnt != 0) ? selTDSAmnt : data.vendorTdsAmount[0].TDS;

			$("#tdsLedgerId").val(selectedRows[0]?.tdsLedgerId || '')
			$("#tdsSection").val(s_obj?.tdsSection || '')

			selectedValue[0]['tds'] = tdsamnt;
			$("#pVendorid").val(data.vendorTdsAmount[0].VENDORID);
			
			$("#tdsTransactionDate").val(data.vendorTdsAmount[0].invoiceDate);
			let offAmnt = data.vendorTdsAmount[0]?.roundOff || '0';
			$("#offAmount").val(offAmnt);

		}
	})

	for (var i = 0; i < selectedValue.length; i++) {
		
		var amount = parseFloat(selectedValue[i].invoiceval);
		$("#ledgerNameModal").html(selectedValue[i].vendorName);
		
		var tdsDecimal = parseFloat(selectedValue[i].tds);
		
		var finalAmount = amount - tdsDecimal;

		var gstRateCal = (((parseFloat(selectedValue[i].igst)
			+ parseFloat(selectedValue[i].cgst)
			+ parseFloat(selectedValue[i].sgst)) / parseFloat(selectedValue[i].taxableamt)) * 100).toFixed(2);
			
		let invoiceval = (parseFloat(selectedValue[i].invoiceval) - parseFloat(selectedValue[i].tds)).toFixed(2);
			
		var rdf = 0;
		var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #bcb6b6;';
		var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
			+ '<td style="text-align: center;" id="invoiceIdTd">' + selectedValue[i].invoiceId + '</td>'
			+ '<td style="text-align: left;" id="voucherIdTd">' + selectedValue[i].journalVoucher + '</td>'
			+ '<td class="alnright">' + gstRateCal + '%</td>'
			+ '<td class="alnright" id="taxableAmtTd">₹' + selectedValue[i].taxableamt + '</td>'
			+ '<td class="alnright" id="igstAmt">₹' + selectedValue[i].igst + '</td>'
			+ '<td class="alnright" id="cgstAmt">₹' + selectedValue[i].cgst + '</td>'
			+ '<td class="alnright" id="sgstAmt">₹' + selectedValue[i].sgst + '</td>'
			+ '<td class="alnright" id="invAmtTd">₹' + invoiceval + '</td>'
			+ '<td class="alnright" id="tdsAmtTd">₹' + selectedValue[i].tds + '</td>'
			+ '<td class="alnright" id="offAmtTd">₹' + rdf + '</td>'
			+ '<td class="alnright" id="fnlAmtTd">₹' + finalAmount.toFixed(2) + '</td>'
			+ '</tr>';
		$("#vendorInvoiceList").append(bdy);
		$("#payTdsAmount").val(selectedValue[i].tds);
		$("#payTdsAmountGlobal").val(selectedValue[i].tds);
		$("#payTdsPercentGlobal").val(selTdsRate);
		$("#tdsPercentageAmt").text(selTdsRate + "%");
		$("#PercentageTDS").text(selTdsRate + "%");
		offAmountInput('');
	}
}

function cancelInvoiceModalBtn() {
	$('#invoiceListPopup').modal('hide');
	$("#pVendorid").val('');
	$("#payTdsAmount").val('');
	$("#payTdsAmountGlobal").val('');
	$("#payTdsPercentGlobal").val('');
}

var globalTdsAmount = 0;
function TdsAmountInput(val) {
	globalTdsAmount = 0;
	var tdsAmount = $("#payTdsAmountGlobal").val();
	globalTdsAmount = tdsAmount;
}

function checkPayAmount() {

	var inputAmount = $("#payTdsAmount").val()
	var taxableAmount = parseFloat($("#taxableAmtTd").text().replace(/[^\d.-]/g, ''));
	var tdsPercentage = $("#tdsPercentageAmt").text();
	var finalamtText = $("#invAmtTd").text().replace(/[^\d.-]/g, '');
	var tdsAmount = parseFloat(inputAmount);
	var parsefAmount = parseFloat(finalamtText);
	var percentageTds = parseFloat((tdsAmount / taxableAmount) * 100).toFixed(2);
	let offAmount = $("#offAmount").val() || '0.0';
	var deductAmount = parsefAmount - tdsAmount + parseFloat(offAmount?.toString());
	console.log("inputAmount.length-->", inputAmount.length)
	if (inputAmount.length == 0) {
		$("#tdsAmtTd").text('₹' + '0.00');
		$("#fnlAmtTd").text('₹' + '0.00');

	}
	else {

		$("#tdsAmtTd").text('₹' + tdsAmount.toFixed(2));
		$("#fnlAmtTd").text('₹' + deductAmount.toFixed(2));
	}

	if (percentageTds > 0) {
		$("#tdsPercentageAmt").text(percentageTds + "%");
		$("#PercentageTDS").text(percentageTds + "%");
	}
	else {
		$("#tdsPercentageAmt").text('0.00' + "%");
		$("#PercentageTDS").text('0.00' + "%");
	}

	if (tdsAmount > globalTdsAmount) {
		var tdsPerView = $("#payTdsPercentGlobal").val();
		$("#payTdsAmount").val(inputAmount);
		var prvAmount = parseFloat($("#payTdsAmount").val());
		var previousPercentage = parseFloat((prvAmount / taxableAmount) * 100).toFixed(2);
		$("#tdsPercentageAmt").text(previousPercentage + "%");
		$("#PercentageTDS").text(previousPercentage + "%");
		var prvtdsAmount = inputAmount;
		var calculateAmnt = parsefAmount - prvtdsAmount + parseFloat(offAmount?.toString());
		$("#tdsAmtTd").text('₹' + inputAmount);
		$("#fnlAmtTd").text('₹' + parseFloat(calculateAmnt).toFixed(2));
	}


	/* 	if(inputAmount.length >0){
			
				var deductAmount = parsefAmount - tdsAmount;
				$("#tdsAmtTd").text('₹' + tdsAmount.toFixed(2));
				$("#fnlAmtTd").text('₹' + deductAmount.toFixed(2)); 
			
		}
		else{
			  $("#tdsAmtTd").text('0.00');
				$("#fnlAmtTd").text($("#invAmtTd").text());
		} */
}

function checkNumberInput(id) {
	var tagname = $("#" + id).val();
	var replaceValue = tagname.replace(/[^\d.]/g, '');
	var decimalIndex = replaceValue.indexOf('.');

	if (decimalIndex !== -1) {
		replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '').substring(0, 2);
	}

	$("#" + id).val(replaceValue);
}






function tdsEdit() {
	var validation = true;
	var tdsAmount = $("#payTdsAmount").val();
	var tdsLedgerId = $("#tdsLedgerId").val();
	var vendorid = $("#pVendorid").val();
	var invoiceId = $("#invoiceIdTd").text();
	var voucherId = $("#voucherIdTd").text();
	var taxableAmt = $("#taxableAmtTd").text().replace(/[^\d.-]/g, '');
	var finalPayableAmt = $("#fnlAmtTd").text().replace(/[^\d.-]/g, '');
	var roundOffAmount = $("#offAmount").val();
	var tdsRate = ((parseFloat(tdsAmount) / parseFloat(taxableAmt)) * 100).toFixed(2);
	var tdsTransactionDate = $("#tdsTransactionDate").val();
	var tdsNarration = $("#tdsNarration").val();
	var tdsSection = $("#tdsSection").val();
	var tdsInvList=[];
	
	// if(!tdsSection) return showError('TDS Section Required');

	var inputFromDate = $("#fromDate").val();
	var inputToDate = $("#toDate").val();

	if (tdsAmount == 0 && roundOffAmount == 0) return showError('Cannot save: TDS amount and round-off amount are both zero.');

	if (tdsAmount == "" || tdsAmount == null || tdsAmount == 'null') {
		toastr.error("Please Enter TDS Amount");
		return false;
	}
	if ((tdsLedgerId == "" || tdsLedgerId == null || tdsLedgerId == 'null') && parseFloat(tdsAmount?.toString()) != 0.0) {
		toastr.error("Please Select TDS Type");
		return false;
	}
	
	console.log(tdsInvList)
	
	if (tdsAmount) {
		roundOffAmount = roundOffAmount || '0.0';
		roundOffAmount = parseFloat(roundOffAmount?.toString()) || 0.0;

		showLoader();

		$.ajax({
			type: "GET",
			url: "purchase-voucher-view-TdsUpdate?tdsAmount=" + tdsAmount + "&vendorid=" + vendorid + "&invoiceId=" + invoiceId
				+ "&voucherId=" + voucherId + "&tdsRate=" + tdsRate + "&finalPayableAmt=" + finalPayableAmt + "&tdsLedgerId=" + tdsLedgerId
				+ "&tdsTransactionDate=" + tdsTransactionDate + "&tdsNarration=" + tdsNarration + "&roundOffAmount=" + roundOffAmount?.toString()+"&tdsSection="+tdsSection+"&tdsInvList="+tdsInvList,
			async: false,
			success: function(response) {
				hideLoader();
				cancelInvoiceModalBtn();
				toastr.success("Data Saved Successfully");
				viewFilteredData(voucherId);
				$("#pVendorid").val('');
				$("#payTdsAmount").val('');
				$("#tdsLedgerId").val('');
				$("#tdsNarration").val('');
			}, error: err => {
				hideLoader();
				console.log(err)
			}
		})
	} else {
		toastr.error("Please Add TDS Amount");
	}

}


function downloadExcelFromGrid() {
	var selectedHeaders = ['Purchase Voucher Id', 'Invoice Date', 'Invoice Id', 'NAME OF THE SUPPLIER'
		, 'GSTIN NO', 'MATERIAL CODE', 'NAME OF THE MATERIAL', 'HSN Code', 'UNIT',
		'QTY BILLED', 'QTY RECEIVED', 'SHORTAGE', 'RATE P/U', 'FREIGHT', 'GST RATE', 'TAXABLE AMOUNT', 'IGST', 'CGST', 'SGST', 'INVOICE VALUE', 'TDS Amount', 'Payable Amount'];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Purchase_Voucher_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

	var totalTaxableAmount = 0;
	var totalIGST = 0;
	var totalCGST = 0;
	var totalSGST = 0;
	var Invoicvalue = 0;
	var totaltdsAmount = 0;
	var totalFinicialAmount = 0;

	var rowData = [];

	var columns = gridOptions.columnApi.getAllColumns();
	var fieldMap = {};
	columns.forEach(function(col) {
		var colDef = col.getColDef();
		fieldMap[colDef.headerName] = colDef.field;
	});

	gridOptions.api.forEachNodeAfterFilterAndSort(function(node) {
		var data = {};
		selectedHeaders.forEach(function(header) {
			var field = fieldMap[header];
			var value = node.data[field] || '';
			if (field === 'taxableamt' || field === 'igst' || field === 'cgst' || field === 'sgst' || field === 'invoiceval' || field === 'tdsAmount' || field === 'finalPayableAmt') {
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});

		rowData.push(data);

		var taxableAmount = parseFloat(node.data[fieldMap['TAXABLE AMOUNT']]) || 0;
		var IGST = parseFloat(node.data[fieldMap['IGST']]) || 0;
		var CGST = parseFloat(node.data[fieldMap['CGST']]) || 0;
		var SGST = parseFloat(node.data[fieldMap['SGST']]) || 0;
		var invoiceval = parseFloat(node.data[fieldMap['INVOICE VALUE']]) || 0;
		var tdsAmount = parseFloat(node.data[fieldMap['TDS Amount']]) || 0;
		var finalPayableAmt = parseFloat(node.data[fieldMap['Payable Amount']]) || 0;

		totalTaxableAmount += taxableAmount;
		totalIGST += IGST;
		totalSGST += SGST;
		totalCGST += CGST;
		Invoicvalue += invoiceval;
		totaltdsAmount += tdsAmount;
		totalFinicialAmount += finalPayableAmt;
	});

	totalTaxableAmount = amountFormatter(totalTaxableAmount.toFixed(2));
	totalIGST = amountFormatter(totalIGST.toFixed(2));
	totalSGST = amountFormatter(totalSGST.toFixed(2));
	totalCGST = amountFormatter(totalCGST.toFixed(2));
	Invoicvalue = amountFormatter(Invoicvalue.toFixed(2));
	totaltdsAmount = amountFormatter(totaltdsAmount.toFixed(2));
	totalFinicialAmount = amountFormatter(totalFinicialAmount.toFixed(2));

	var totalRow = {
		'Purchase Voucher Id': "Total",
		'Invoice Date': "",
		'Invoice Id': "",
		'NAME OF THE SUPPLIER': "",
		'GSTIN NO': "",
		'MATERIAL CODE': "",
		'NAME OF THE MATERIAL': "",
		'HSN Code': "",
		'UNIT': "",
		'QTY BILLED': "",
		'QTY RECEIVED': "",
		'SHORTAGE': "",
		'RATE P/U': "",
		'FREIGHT': "",
		'GST RATE': "",
		'TAXABLE AMOUNT': totalTaxableAmount,
		'IGST': totalIGST,
		'CGST': totalCGST,
		'SGST': totalSGST,
		'INVOICE VALUE': Invoicvalue,
		'TDS Amount': totaltdsAmount,
		'Payable Amount': totalFinicialAmount

	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Purchase Voucher');
	XLSX.writeFile(wb, fileName);
}

function generatePdfPurchaseRegister() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var voucherType = "PURCHASE";
	var ActivityType = "PURCHASE VOUCHER";
	window.open("/account/purchase-register-Pdf?voucherType=" + voucherType + "&activityType=" + ActivityType + "&fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}
function voucherClassModal() {
	$('#voucherClassnewModal').modal('show');


	$('#current').val("");
	$('#voucherTypeId').val("");
	$('#voucherClassId').val("");
	$('#ledgerName').val("");
	$('#ledgerId').val("");

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	var selectedRowsString1 = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
			selectedRowsString1 += ',';
		}
		selectedRowsString += selectedRow.invoiceId;
		selectedRowsString1 += selectedRow.journalVoucher;
	});
	var id = selectedRowsString;
	$('#pVoucherId').val(selectedRowsString1);
	$.ajax({
		type: "GET",
		url: "purchase-voucher-get-current-voucher?id=" + id,
		success: function(response) {
			if (response.message == "success") {
				$("#current").val(
					response.body[0].name);
			}
		},
		error: function(e) {
		}
	});
}
function cancelBtnNew() {
	$('#voucherClassnewModal').modal('hide');
}


function getVoucherClass() {

	var id = $('#voucherTypeId').val();
	if (id) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "purchase-voucher-get-voucherClass-List?id=" + id,
			success: function(response) {
				if (response.message == "success") {
					$("#voucherClassId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#voucherClassId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#voucherClassId").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#voucherClassId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#voucherClassId").append(option);
		$("#voucherClassId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
	}
}
function getLedger() {

	var id = $('#voucherClassId').val();
	if (id) {
		$.ajax({
			type: "GET",
			url: "purchase-voucher-get-ledger-voucher?id=" + id,
			success: function(response) {
				if (response.message == "success") {
					$("#ledgerName").val(response.body[0].name);
					$("#ledgerId").val(response.body[0].key);
				}
			},
			error: function(e) {
			}
		});
	}
}

function addDetailss() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	var selectedRowsString1 = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
			selectedRowsString1 += ',';
		}
		selectedRowsString += selectedRow.invoiceId;
		selectedRowsString1 += selectedRow.journalVoucher;
	});
	var id = selectedRowsString;
	var id2 = selectedRowsString1;


	obj = {};
	var current = $('#current').val();
	var voucherTypeId = $('#voucherTypeId').val();
	var voucherClassId = $('#voucherClassId').val();
	var ledgerName = $('#ledgerName').val();
	var ledgerId = $('#ledgerId').val();
	var pVoucherId = id2;
	var invoiceId = id;

	$(".formValidation").remove();

	allPValid = true;
	if ($("#voucherClassId").val() == null || $("#voucherClassId").val() == "") {
		toastr.error("Voucher Class Required");
		allPValid = false;
		//	validationModal("Voucher Class Required", "voucherClassId");
	}
	if ($("#voucherTypeId").val() == null || $("#voucherTypeId").val() == "") {
		toastr.error("Voucher Type Required")
		allPValid = false;
		//validationModal("Voucher Type Required", "voucherTypeId");
	}
	if ($("#ledgerName").val() == null || $("#ledgerName").val() == "") {
		toastr.error("Ledger Name Required");
		allPValid = false;
		//validationModal("Ledger Name Required", "ledgerName");
	}

	if (allPValid) {
		$.ajax({
			type: "GET",
			url: "purchase-voucher-save-voucher-details?voucherTypeId=" + voucherTypeId + "&voucherClassId=" + voucherClassId + "&ledgerName=" + ledgerName
				+ "&ledgerId=" + ledgerId + "&pVoucherId=" + pVoucherId + "&invoiceId=" + invoiceId,
			async: false,
			success: function(response) {
				if (response.message == "Success") {
					toastr.success("Data saved successfully")
					cancelBtnNew();
					var jsonData = JSON.parse(response.body);
					var purchaseVoucherData = jsonData.editPurchaseVoucher[0];
					var productDetails = jsonData.viewProductDetails;
					$('#buyerName').val(purchaseVoucherData.buyerName);
					$('#sellerName').val(purchaseVoucherData.sellerName);
					$('#voucherId').val(purchaseVoucherData.voucherId);
					$('#voucherDate').val(purchaseVoucherData.voucherDate);
					$('#productGrnId').val(purchaseVoucherData.productGrnId);
					$('#purchaseNumber').val(purchaseVoucherData.invoiceId);
					$('#invoiceDate').val(purchaseVoucherData.invoiceDate);
					$('#invoiceDateCalendar').val(purchaseVoucherData.invoiceDate);
					$('#subTotal').val(amountFormatter(purchaseVoucherData.subTotal.toFixed(2)));
					$('#qSGST').val(amountFormatter(purchaseVoucherData.sgst.toFixed(2)));
					$('#qCGST').val(amountFormatter(purchaseVoucherData.cgst.toFixed(2)));
					$('#qIGST').val(amountFormatter(purchaseVoucherData.igst.toFixed(2)));
					$('#grandTotal').val(purchaseVoucherData.grandTotal);
					$('#tdsAmount').val(amountFormatter(purchaseVoucherData.tdsAmount));
					$('#vType').val(purchaseVoucherData.voucherType);
					$('#vClass').val(purchaseVoucherData.voucherClass);
					$('#tdsRate').val(purchaseVoucherData.tdsRate);

					$('#totalQuantity').val(productDetails[0].productTotalQuantity);

					itemOptions.api.setRowData(productDetails);
				}
			}
		})
	}

}



function financialYearChange() {
	const selectedFY = document.getElementById('orderStatusFilter').value;
	if (selectedFY) {
		const [startYear, endYear] = selectedFY.split('-');
		const fromDate = `01-04-${startYear}`;
		const toDate = `31-03-${endYear}`;

		document.getElementById('fromDate').value = fromDate;
		document.getElementById('toDate').value = toDate;
	} else {
		document.getElementById('fromDate').value = '';
		document.getElementById('toDate').value = '';
	}
}

function pad(n) {
	return n < 10 ? '0' + n : n;
}


function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	console.log("tabElement-->", tabElement);

	if (tabElement.id == "tdsTab") {
		showTdsModal();
	}

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);

		tabTrigger.show();
	}
}


function offAmountInput(val) {

	var finalamtText = $("#invAmtTd").text().replace(/[^\d.-]/g, '');
	var parsefAmount = parseFloat(finalamtText);
	
	var rfAmount = parseFloat($("#offAmount").val());
	
	if (isNaN(rfAmount)) {
		rfAmount = 0;
	}
	var calculateAmnt = parsefAmount + rfAmount;
	
	$("#offAmtTd").text('₹' + rfAmount?.toFixed(2));
	$("#fnlAmtTd").text('₹' + parseFloat(calculateAmnt).toFixed(2));
}

function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChanged();
	}
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	onQuickFilterChanged();
	setTimeout(() => {
		gridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}

/*var invListData='';
$(document).on('change', '#tdsSection', function () {
    const totalAmnt_all = parseFloat($("#totalAmnt_all").val()) || 0.00;
    const totalAmnt_noTds = parseFloat($("#totalAmnt_noTds").val()) || 0.00;
    const tdssid = $("#tdsSection").val();
    
    $("#invListDiv").hide();
    let showError = false;
    let errorMsg = "";
    
    var invoiceIds = [];  // Step 1: initialize array
    invListData='';
    tdsInvoiceGridOptions.api.forEachNode((rowNode) => {
        if (rowNode.data && rowNode.data.invoiceId) {
            invoiceIds.push(rowNode.data.invoiceId); // Step 2: push invoiceId
        }
    });
 
    let totTaxAmt = 0.00;
 
    if (tdssid === "TDS008") {
        if (totalAmnt_all <= 5000000.00) {
            showError = true;
            errorMsg = "Vendor is not eligible under Section 194Q.";
        } else {
            totTaxAmt = totalAmnt_noTds;
            $("#invListDiv").show();
            invListData = invoiceIds.map(id => `"${id}"`).join(',');
            
        }
    }
    if (tdssid === "TDS006" || tdssid === "TDS005" || tdssid === "TDS007") {
        if (totalAmnt_all > 5000000.00) {
            showError = true;
            errorMsg = "Vendor falls under Section 194Q, not 194C.";
        } else if (totalAmnt_noTds <= 100000.00) {
            let largeInvoiceFound = false;
            $("#vendorInvoiceList tr").each(function () {
                const amt = parseFloat($(this).find("#invAmtTd").text().replace(/[₹,]/g, '').trim()) || 0;
                if (amt > 30000.00) {
                    totTaxAmt = amt;
                    largeInvoiceFound = true;
                }
            });
 
            if (!largeInvoiceFound) {
                showError = true;
                errorMsg = "Vendor is not eligible under Section 194C.";
            }
        } else {
            totTaxAmt = totalAmnt_noTds;
            $("#invListDiv").show();
            invListData = invoiceIds.map(id => `"${id}"`).join(',');
        }
    }
 
    if (showError) {
        document.getElementById('tdsSection').selectedIndex = 0;
        toastr.error(errorMsg);
        
        $("#payTdsPercentGlobal").val("0.00%");
	    $("#PercentageTDS").text('0.00');
	    $("#tdsPercentageAmt").text("0.00%");
	 
	    let totalInvoiceAmount = 0;
	    let totalTdsAmount = 0;
	 
	    $("#vendorInvoiceList tr").each(function () {
	        const $row = $(this);
	        const invAmtText = $row.find("#invAmtTd").text().replace(/[₹,]/g, '').trim();
	        const invAmt = parseFloat(totTaxAmt) || 0;
	 
	        // Apply TDS only to totTaxAmt value
	        let taxable = invAmt <= totTaxAmt ? invAmt : 0.00;
	 
	        const tdsAmt = (0 / 100) * taxable;
	        const finalAmt = parseFloat(invAmtText) - tdsAmt;
	 
	        $row.find("#tdsAmtTd").text("₹" + tdsAmt.toFixed(2));
	        $row.find("#fnlAmtTd").text("₹" + finalAmt.toFixed(2));
	 
	        totalInvoiceAmount += invAmt;
	        totalTdsAmount += tdsAmt;
	    });
	 
	    $("#payTdsAmount").val('0.00');
	    $("#payTdsAmountGlobal").val('0.00');
        
        return;
    }
 
    const selectedRate = $(this).find('option:selected').data('code');
    $('#tdsSectionRate').val(selectedRate ? selectedRate : '0.00');
 
    const tdsRate = parseFloat(selectedRate) || 0.00;
 
    $("#payTdsPercentGlobal").val(tdsRate.toFixed(2) + "%");
    $("#PercentageTDS").text(tdsRate.toFixed(2));
    $("#tdsPercentageAmt").text(tdsRate.toFixed(2) + "%");
 
    let totalInvoiceAmount = 0;
    let totalTdsAmount = 0;
 
    $("#vendorInvoiceList tr").each(function () {
        const $row = $(this);
        const invAmtText = $row.find("#invAmtTd").text().replace(/[₹,]/g, '').trim();
        const invAmt = parseFloat(totTaxAmt) || 0;
 
        // Apply TDS only to totTaxAmt value
        let taxable = invAmt <= totTaxAmt ? invAmt : 0.00;
 
        const tdsAmt = (tdsRate / 100) * taxable;
        const finalAmt = parseFloat(invAmtText) - tdsAmt;
 
        $row.find("#tdsAmtTd").text("₹" + tdsAmt.toFixed(2));
        $row.find("#fnlAmtTd").text("₹" + finalAmt.toFixed(2));
 
        totalInvoiceAmount += invAmt;
        totalTdsAmount += tdsAmt;
    });
 
    $("#payTdsAmount").val(totalTdsAmount.toFixed(2));
    $("#payTdsAmountGlobal").val(totalTdsAmount.toFixed(2));
});
*/

$(document).on('change', '#tdsSection', function () {
	const tdssid = $("#tdsSection").val();
	
	const selectedRate = $(this).find('option:selected').data('code');
 
    const tdsRate = parseFloat(selectedRate) || 0.00;
    
    $("#payTdsPercentGlobal").val(tdsRate.toFixed(2) + "%");
    $("#PercentageTDS").text(tdsRate.toFixed(2));
    $("#tdsPercentageAmt").text(tdsRate.toFixed(2) + "%");
    
    var taxableAmtTd = $("#taxableAmtTd").text().replace(/[^\d.-]/g, '');
	var parsetaxableAmtTd = parseFloat(taxableAmtTd);
	
    var igstAmt = $("#igstAmt").text().replace(/[^\d.-]/g, '');
	var parseigstAmt = parseFloat(igstAmt);
	
    var cgstAmt = $("#cgstAmt").text().replace(/[^\d.-]/g, '');
	var parsecgstAmt = parseFloat(cgstAmt);
	
    var sgstAmt = $("#sgstAmt").text().replace(/[^\d.-]/g, '');
	var parsesgstAmt = parseFloat(sgstAmt);
	
    var offAmtTd = $("#offAmtTd").text().replace(/[^\d.-]/g, '');
	var parseoffAmtTd = parseFloat(offAmtTd);
	
	let tdAmount =  parsetaxableAmtTd * tdsRate / 100;
	
	let invAmtTd = (parsetaxableAmtTd + parseigstAmt + parsecgstAmt + parsesgstAmt - tdAmount)?.toFixed(2)
	let fnlAmtTd = (parsetaxableAmtTd + parseigstAmt + parsecgstAmt + parsesgstAmt - tdAmount + parseoffAmtTd)?.toFixed(2)
	
	$("#payTdsAmount").val(parseFloat(tdAmount?.toString())?.toFixed(2));
    $("#payTdsAmountGlobal").val(parseFloat(tdAmount?.toString())?.toFixed(2));
	$("#tdsAmtTd").text('₹' + parseFloat(tdAmount?.toString())?.toFixed(2));
	
	let fnlAmnt = parseFloat(tdAmount?.toString())
	$("#invAmtTd").text('₹' + invAmtTd);
	$("#fnlAmtTd").text('₹' + fnlAmtTd);
})


function validateNumber(input) {
  // Allow only digits and one dot
  let value = input.value;
  value = value.replace(/[^0-9.]/g, '')           // Remove anything that's not a digit or dot
               .replace(/(\..*?)\..*/g, '$1');     // Keep only the first dot
  input.value = value;
}
