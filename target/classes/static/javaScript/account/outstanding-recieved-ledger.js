var itemOptionsSales = "";
$(document).ready(function() {
	
	alert("in receive ledger");

	var gridDiv = document.querySelector('#receivedLedgerGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();
	// Date For Form Date And To Date Field One Month Gave
	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var todate = toDateString.toString();
	$("#toDateReceiveLedger").val(todate);
	$("#fromDateReceiveLedger").val(fromDate);
	
	var dateFormat = localStorage.getItem("dateFormat");

	$("#toDateCalendarReceiveLedger").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#fromDateReceiveLedger').val($(this).val());
	})
	$('#fromDateReceiveLedger').blur(function() {
		$("#toDateCalendarReceiveLedger").val($(this).val());
	})


	$("#toDateCalendar2").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})
	$('#toDate').blur(function() {
		$("#toDateCalendar2").val($(this).val());
	})

	$("#toDateCalendarInvoice").datetimepicker({
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
		$("#toDateCalendarInvoice").val($(this).val());
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
	/*   agGrid.simpleHttpRequest({
		url: "outstanding-recieved-ledger-view"
	  }).then(function(data) {
		$(".loader").hide();
		var len = data.length;
		$('#totalCandidate').find('span').html(len);
		gridOptions.api.setRowData(data);
	  }); */


	$("#modeOfSearch").val('invDate');
	//viewFilteredData();
	$('#voucherDetailSalesPopup').modal('hide');


	/* sales item ag grid initialization starts */

	var itemDefsSales = [
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
			field: "rowIndex",
			width: 80,
			cellStyle: {
				textAlign: 'center'
			}
		}, {

			headerName: 'HSN CODE',
			field: "hsnCode",
			width: 120
		},
		{
			headerName: 'Item Name',
			field: "sItemName",
			width: 200
		}, {
			headerName: 'Quantity',
			field: "sItemQty",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: 'Unit',
			field: "sItemUnit",
			width: 100
		}, {
			headerName: 'Unit Price',
			field: "sItmUnitPrice",
			width: 130,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: 'Discount',
			field: "sItmDiscount",
			width: 120,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: 'GST Rate',
			field: "sItmGSTRate",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value.toFixed(2));
			}
		}, {
			headerName: 'Taxable Amount',
			field: "taxableAmt",
			width: 170,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: 'Cgst',
			field: "sItmCGST",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: 'Sgst',
			field: "sItmSGST",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: 'Igst',
			field: "sItmIGST",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}, {
			headerName: 'Amount',
			field: "sItmLineAmount",
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		}];

	itemOptionsSales = {
		columnDefs: itemDefsSales,
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
		}
	};

	var gridDivSales = document.querySelector('#itemGridSales');
	new agGrid.Grid(gridDivSales, itemOptionsSales);
	itemOptionsSales.api.setRowData("");
});

function closeAllNav() {
	closeNav();
	closeNav1();
	closeNav2();
}
var columnDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	//checkboxSelection : true,
	checkboxSelection: function(params) {
		const paymentStatus = params.data.paymentStatus;
		return paymentStatus !== 'Paid';
	},
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Sales Invoice Id",
	field: "SALE_VOUCHER_ID",
	pinned: 'left',
	width: 150,
	/*cellRenderer: function(params) {
		return '<a onclick=viewVoucherDetail("'
			+ params.data.SALE_VOUCHER_ID + '") href="javascript:void(0)">'
			+ params.data.SALE_VOUCHER_ID + '</a>';
	}*/
},
{
	headerName: 'COMPANY VOUCHER ID',
	field: "COMPANY_INVOICE_NO",
	width: 180,
	cellStyle: {
		textAlign: 'center'
	}
},
{
	headerName: 'INVOICE NUMBER',
	field: "SAP_ID",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	}
},
{
	headerName: 'TAX INVOICE NUMBER',
	field: "TAX_INVOICE_NUMBER",
	width: 180,
	cellStyle: {
		textAlign: 'center'
	}
},
{
	headerName: 'PO Id',
	field: "poId",
	width: 150,
	hide: true
},
{
	headerName: 'CUSTOMER NAME',
	field: "LEDGERNAME"
},
{
	headerName: 'TOTAL BILL AMOUNT',
	field: "TOTALBILLAMOUNT",
	width: 150,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = params.value;
		return amountFormatter(value.toFixed(2));
	}
},
{
	headerName: 'RECEIVED AMOUNT',
	field: "RECEIVE_AMOUNT",
	width: 150,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = params.value;
		return amountFormatter(value.toFixed(2));
	}
},
{
	headerName: 'PENDING AMOUNT',
	field: "PENDING_AMOUNT",
	width: 150,
	type: 'rightAligned',
	cellRenderer: function(params) {
		var value = params.value;
		return amountFormatter(value.toFixed(2));
	}

},
{
	headerName: 'PAYMENT STATUS',
	field: "INVOICE_STATUS",
	width: 140,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.INVOICE_STATUS === null) {
			return '<div style="color: red; font-weight: bold;">Pending</div>';
		} else {
			var statusStyle = getStatusStyle(params.data.INVOICE_STATUS);
			return `<div style="${statusStyle}">${params.data.INVOICE_STATUS}</div>`;
		}
	}
},

{
	headerName: 'INVOICE DATE',
	field: "INVOICE_DATE",
	width: 150,
	type: 'left'
},
{
	headerName: 'DUE  DATE',
	field: "DueDate",
	width: 150,
	type: 'left'
}

];
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	onSelectionChanged: rowSelect,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	}
};

function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var outstandingLedgerId = selectedRows[0].SALE_VOUCHER_ID;
	console.log("Selected rows-->", selectedRows);

	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		viewVoucherDetail(outstandingLedgerId);
	} else {
		console.log("");
	}
}

function getStatusStyle(paymentStatus) {
	switch (paymentStatus) {
		case "Fully Paid":
			return 'color: green; font-weight: bold;';
		case "Pending":
			return 'color: red; font-weight: bold;';
		case "Partial Paid":
			return 'color: orange; font-weight: bold;';
		default:
			return 'color: black; font-weight: bold;';
	}
}

function dateWithAnimationCellRenderer(params) {
	const scheduledDate = params.data.scheduleDate;
	const payStatus = params.data.paymentStatus;
	if (!scheduledDate) {
		return '';
	}
	const [day, month, year] = scheduledDate.split('-');
	const scheduledDateObj = new Date(`${year}-${month}-${day}`);
	const today = new Date();
	const timeDiff = scheduledDateObj - today;
	const pendingThreshold = 7 * 24 * 60 * 60 * 1000;
	if (timeDiff >= -pendingThreshold && timeDiff <= pendingThreshold && payStatus == "Pending") {
		return ' < div class = "pending-animation" > DUE < /div> &nbsp;' + scheduledDate;
	} else {
		return scheduledDate;
	}
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

function scheduleDueDate() {
	var scheduledDate = $("#scheduleDate").val();
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += '"' + selectedRow.saleInvoice + '"';
	});
	$.ajax({
		type: "GET",
		url: "outstanding-recieved-ledger-scheduleDate?invoiceId=" + selectedRowsString + "&scheduledDate=" + scheduledDate,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				//cancel();
				$("#messageParagraph").text("Scheduled Date Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#schedulePopup').modal('hide');
				agGrid.simpleHttpRequest({
					url: "outstanding-recieved-ledger-view"
				}).then(function(data) {
					$(".loader").hide();
					var len = data.length;
					$('#paymentInvoice').attr("disabled", true);
					$('#paymentSchedule').attr("disabled", true);
					$('#totalCandidate').find('span').html(len);
					gridOptions.api.setRowData(data);
				});
			}
		},
	});
}

function downloadExcelFromGrid() {
	var selectedHeaders = ['Sales Invoice Id',
		'COMPANY VOUCHER ID',
		'INVOICE NUMBER',
		'TAX INVOICE NUMBER',
		'CUSTOMER NAME',
		'TOTAL BILL AMOUNT',
		'RECEIVED AMOUNTv',
		'PENDING AMOUNT',
		'PAYMENT STATUS',
		'INVOICE DATE',
		'DUE  DATE'
	];
	var currentDate = new Date().toLocaleDateString('en-GB', {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	});
	currentDate = currentDate.split('/').join('_');
	var fileName = 'OutstandingReceive_Register_' + currentDate + '.xlsx';
	var grandTotalAmount = 0;
	var pendingAmount = 0;
	var receiveAmount = 0;

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
			if (['TOTALBILLAMOUNT', 'RECEIVE_AMOUNT', 'PENDING_AMOUNT'].includes(field)) {
				value = parseFloat(value);
				value = isNaN(value) ? '0.00' : value.toFixed(2);
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});
		rowData.push(data);
		var billamount = parseFloat(node.data[fieldMap['TOTAL BILL AMOUNT']]) || 0;
		var receive = parseFloat(node.data[fieldMap['RECEIVED AMOUNT']]) || 0;
		var pending = parseFloat(node.data[fieldMap['PENDING AMOUNT']]) || 0;

		grandTotalAmount += billamount;
		pendingAmount += pending;
		receiveAmount += receive;
	});

	grandTotalAmount = amountFormatter(grandTotalAmount.toFixed(2));
	pendingAmount = amountFormatter(pendingAmount.toFixed(2));
	receiveAmount = amountFormatter(receiveAmount.toFixed(2));

	var totalRow = {
		'Sales Invoice Id': "Total",
		'COMPANY VOUCHER ID': "",
		'INVOICE NUMBER': "",
		'TAX INVOICE NUMBER': "",
		'CUSTOMER NAME': "",
		'TOTAL BILL AMOUNT': grandTotalAmount,
		'RECEIVED AMOUNT': receiveAmount,
		'PENDING AMOUNT': pendingAmount,
		'PAYMENT STATUS': "",
		'INVOICE DATE': "",
		'DUE  DATE': ""
	};
	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, {
		header: selectedHeaders
	});
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Outstanding Receive Register');
	XLSX.writeFile(wb, fileName);
}


function viewFilteredData() {
	var validation = true;
	var fromDateFilter = $("#fromDateReceiveLedger").val();
	var toDateFilter = $("#toDate").val();
	var modOfSearch = $("#modeOfSearch").val();
	var customerId = $("#customerId").val();
	var totalBillAmount = 0.00;
	var totalPendingAmount = 0.00;
	var totalReceiableAmount = 0.00;
	if (fromDateFilter == null || fromDateFilter == "") {
		swal("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		swal("Please Provide To Date");
		validation = false;
	}
	if (validation) {
		agGrid.simpleHttpRequest({
			url: "outstanding-recieved-ledger-view?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter + "&modOfSearch=" + modOfSearch + "&customerId=" + customerId,
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			console.log("jsonData-->", jsonData);
			var allData = jsonData.viewOutStandingData;

			if (allData == "" || allData == "null" || allData == null) {
				$('#totalCandidate').find('span').html('0');
				var emptyArray = [];
				gridOptions.api.setRowData(emptyArray);
			} else {
				var filteredData = allData.filter(function(row) {
					return row.INVOICE_STATUS !== "Fully Paid";
				});
				//var filteredData = allData;

				console.log("filteredData-->", filteredData);
				var len = filteredData.length;

				for (var i = 0; i < len; i++) {
					totalBillAmount = totalBillAmount + parseFloat(filteredData[i].TOTALBILLAMOUNT);
					totalPendingAmount = totalPendingAmount + parseFloat(filteredData[i].PENDING_AMOUNT);
					totalReceiableAmount = totalReceiableAmount + parseFloat(filteredData[i].RECEIVE_AMOUNT);
				}


				console.log("bill amount-->", totalBillAmount);
				$("#totalBillAmount").val(amountFormatter(totalBillAmount.toFixed(2)));
				$("#totalReceiveAmount").val(amountFormatter(totalReceiableAmount.toFixed(2)));
				$("#totalPendingAmount").val(amountFormatter(totalPendingAmount.toFixed(2)));
				$('#totalCandidate').find('span').html(len);
				//gridOptions.api.setRowData(filteredData);
				var rowData = [];
				gridOptions.api.setRowData(rowData);

				gridOptions.api.setRowData(filteredData);

				if (filteredData && filteredData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); 
						}
					});
				}
			}
		});
	}
}


/* CUSTOMER AUTOSEARCH */

function getCustomerList() {
	$('.loader').show();
	var search = $("#customerName").val();


	$("#addre").hide();
	if (search) {

		$
			.ajax({
				type: "POST",
				url: "receipt-voucher-customerList",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {

						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li   class="autocompletedata cp" onClick="selectAutocompleteValue3(\''
									+ response.body[i].vendorId
									+ '\',\''
									+ response.body[i].vendorName
									+ '\',\''
									+ response.body[i].outstandingAmt
									+ '\')">'
									+ response.body[i].vendorName
									+ '</li>';
							}
							content += '</ul>';

							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li  onClick="selectAutocompleteValue()">'
								+ "No Data Found" + '</li>';
							content += '</ul>';
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);
						}
						$('.loader').hide();
					}
				},
				error: function(data) {

					$('.loader').hide();
					$("#customerId").val('');
				}
			})
	} else {
		$("#suggesstion-box1_").hide();
		$('.loader').hide();
	}

	if (search.length == 0) {
		$("#suggesstion-box1_").hide();
		$('.loader').hide();
		$("#customerId").val('');
	}

}

function selectAutocompleteValue3(customerId, customerName, outstandingAmt) {
	if (customerId) {
		$("#customerId").val(customerId);
		$("#customerName").val(customerName);
		$("#search").val(customerName);
		$("#search").attr('data-procat', customerName);
		$("#suggesstion-box1_").hide();
		$("#adjustment").attr("disabled", false);

		$("#outstandingAmountDiv").show();

		var outAmount = amountFormatter(outstandingAmt);
		$("#outstandingAmountView").val(outAmount);

		$("#remainingAmtDiv").hide();
		$("#parentDiv").hide();
		selectMode()
	} else {

		$("#customerId").val("");
		$("#customerName").val("");
		$("#search").val("");

		$("#outstandingAmountDiv").hide();
		$("#outstandingAmountView").val("");

		$("#search").attr('data-procat', "");
		$("#suggesstion-box2_").hide();
		$("#adjustment").attr("disabled", true);

	}
}
function selectAutocompleteValue() {
	$("#customerId").val("");
	$("#customerName").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box1_").hide();

}

function viewVoucherDetail(voucherId) {
	$.ajax({
		type: "GET",
		url: "outstanding-recieved-ledger-voucherDetails?voucherId=" + voucherId,
		async: false,
		success: function(response) {
			console.log(response)
			var data = JSON.parse(response.body);
			var parentData = data.editParentSales;
			var childData = data.editChildSales;
			$('#ledgerDetailsPopup').modal('hide');
			$('#voucherDetailSalesPopup').modal('show');
			$("#buyerNameSales").val(parentData[0].buyerName);
			$("#sellerNameSales").val(parentData[0].sellerName);
			$("#orderDateSales").val(parentData[0].invoiceDate);
			$("#companyVoucherNo").val(parentData[0].COMPANY_INVOICE_NO);
			$("#sapNo").val(parentData[0].SAP_ID);
			$("#voucherNumberSales").val(voucherId);
			$("#invoiceNumberSales").val(parentData[0].saleInvId);
			$("#subTotalSales").val(amountFormatter(parentData[0].subTotal));
			$("#qSGSTSales").val(amountFormatter(parentData[0].invSgst));
			$("#qCGSTSales").val(amountFormatter(parentData[0].invCgst));
			$("#qIGSTSales").val(amountFormatter(parentData[0].invIgst));
			$("#grandTotalSales").val(amountFormatter(parentData[0].grandTotal));
			itemOptionsSales.api.setRowData(childData);

		},
		error: function(e) {
			console.error("An error occurred: " + e);
		}
	});


}

function cancelLedgerDetailSalesModalBtn() {
	$('#voucherDetailSalesPopup').modal('hide');
	$('#ledgerDetailsPopup').modal('show');

}

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	$('#totalCandidate span').html(displayedRowCount);
}
