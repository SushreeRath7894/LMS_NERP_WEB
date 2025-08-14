var gridOptions3;
var gridOptions4;
$(function() {

	$("#costCenter").select2({
		placeholder: "Select Cost Center",
		allowClear: true
	});
	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var taxGridDiv = document.querySelector('#myGridTaxDeduction');
	new agGrid.Grid(taxGridDiv, TDSOptions2);


	$("#invoiceListPopup").modal('hide');
	$("#transNameDiv").hide();
	$("#transAmountDiv").hide();

	$("#invoiceInfoModal").modal('hide');
	$("#methodBoxDiv").hide();
	$("#methodOfAdjustmentDivInfo").hide();


	$("#totalPaymentAmount").val("0.00");

	/* agGrid.simpleHttpRequest({
		url : "view-account-journal-voucher-throughAjax"
	}).then(function(data) {
		console.log(JSON.stringify(data))
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewJournalVoucher;
		if(allData == "" || allData == "null" || allData == null){
			$('#totalReq').find('span').html('0');
			gridOptions.api.setRowData();
		}else{
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
		}
	}); */


	/* Intialize The CKEditor For Narration Start */

	CKEDITOR.replace('description', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	/* Intialize The CKEditor For Narration End */

	$("#myGrid").show();
	$("#delete").attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);


	const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var d = new Date();
	//var month = monthNames[d.getMonth()+1];
	var month = monthNames[d.getMonth()];
	var day = d.getDate();
	var output = (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + d.getFullYear();
	$("#recieptDate").text(output);
	var a = new Date(output);
	var b = weekday[a.getDay()];
	$("#recieptDay").text(b);


	$("#voucherDate").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
	})

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
	var fromDateString = '01-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var toDate = dateString.toString();

	$("#toDate").val(toDate);
	$("#fromDate").val(fromDate);

	// Set initial date range based on current selection
	handleDropdownChange();

	viewFilteredData();



	$("#toDateCalendar3").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		maxDate: today,
	}).on("change", function() {
		$('#voucherDate').val($(this).val());
	})

	$('#voucherDate').blur(function() {
		$("#toDateCalendar3").val($(this).val());
	})

});

function getVoucherNumber() {
	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-vouchernumber",
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#voucherNumber").text(response.body[0].key);
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


/*function onQuickFilterChanged() {

	var quickFilterValue = $('#quickFilter').val();
	gridOptions.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}*/

function cancelBar() {

	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');

}

function resetQuickFilter() {

	gridOptions.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();

}

function updateTotalTaskCount() {
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);

	if (displayedRowCount > 0) {
		$("#purchaseRegisterExcel").prop("disabled", false);
		$("#journalVoucherPdf").prop("disabled", false);
	} else {
		$("#purchaseRegisterExcel").prop("disabled", true);
		$("#journalVoucherPdf").prop("disabled", true);
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
// column Defs
const columnDefs =
	[
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 30,
			maxWidth: 30,
			sortable: false,
			filter: false,
			pinned: 'left',
			resizable: true
		}, {
			headerName: 'Journal Voucher Id',
			field: "journalVoucher",
			hide: true,
			cellRenderer: function(params) {
				return '<a  onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.journalVoucher + '</a>';
			}
		},


		{
			headerName: "Date",
			field: "voucherDate",
			maxWidth: 100,
			pinned: 'left',
			cellStyle: {
				textAlign: 'left'
			},
			/*cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.voucherDate + ' <i class="fa fa-edit"></i></a>';
			}*/
		}, {
			headerName: "Voucher No",
			field: "transactionOrder",
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "Voucher Type",
			field: "voucherType",
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},

		{
			headerName: "Debit",
			field: "debitAccountName",
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Credit",
			field: "creditAccountName",
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Description",
			field: "description",
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: 'Amount',
			field: 'totalAmount',
			headerClass: 'amountAlign',
			valueFormatter: params => amountFormatter(params.data.totalAmount.toFixed(2)),
			cellStyle: {
				textAlign: 'right'
			}
		}/* ,
			{
				headerName : "Created Date",
				field : "createdOn",
				width :150,
				cellStyle : {
					textAlign : 'left'
				}
			} */];


const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1,
		minWidth: 100
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	pagination: true,
	paginationPageSize: 15,
	getRowNodeId: function(data) {
		return data.journalVoucher;
	}

};

var deleteId = "";
function rowSelect() {
	//alert('hello select');
	var selectedRows = gridOptions.api.getSelectedRows();

	console.log("slRows-->", selectedRows);
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '' + selectedRows[i].journalVoucher + ',';
		// deleteId = deleteId  + selectedRows[i].journalVoucherId + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	console.log(deleteId)
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		var journalVoucherId = selectedRows[0].journalVoucher;
		editPage(journalVoucherId);
		disableTrueAll();

		$("#delete-journal-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#add-btn").removeClass("d-none");
		CKEDITOR.instances['description'].setReadOnly(true);
		$("#cancel-journal-btn").addClass("d-none");
		$("#journal-save-btn").addClass("d-none");
		$(".journalVchrHeaderInfo").removeClass("d-none");
	} else {
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
		$("#journalVoucher").text('');
		paymentSectionComntainer();
		CKEDITOR.instances['description'].setData('');
		CKEDITOR.instances['description'].setReadOnly(false);
		$("#costCenter").attr("disabled", false);
		$("#toDateCalendar3").removeClass("pointeerEventsProperty");
		//$(".journalVchrHeaderInfo").addClass("d-none");

	}
}


function paymentSectionComntainer() {

	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');
	$("#voucherDate").val("");
	$("#description").val("");
	var today = new Date();
	var day = String(today.getDate()).padStart(2, '0');
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var year = today.getFullYear();

	var formattedDate = day + '-' + month + '-' + year;
	$("#voucherDate").val(formattedDate);
	//$("#purchaseRegisterExcel").hide();
	//$("#journalVoucherPdf").hide();

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountSubGroupId_0" class="form-control creditAccountGroupIdCls">'
		+/* '<p class="mb-0">'	
							+'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>'		
							+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" oninput="formatAmount(this);" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();"> '
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#creditTbodyData").append(abc);

	$("#debitTbodyData").empty();
	var abc = '<tr class="tr_clone" id="debit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="debitAccountSubGroup_0" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+/* '<p class="mb-0">'
										+'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>'
										+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" oninput="formatAmount(this);" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);
	$("#add-btn").addClass("d-none");
	$("#delete-journal-btn").addClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	$("#journal-save-btn").removeClass("d-none");
	CKEDITOR.instances['description'].setData('');
	CKEDITOR.instances['description'].setReadOnly(false);
	$("#costCenter").attr("disabled", false);
	$("#cancel-journal-btn").removeClass("d-none");



	getVoucherNumber();

}

//TDS Ag-Grid

const TDSdefs =
	[
		{
			headerCheckboxSelection: true,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			pinned: 'left',
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: 'Voucher Id',
			field: "voucherId",
		},
		{
			headerName: "Date",
			field: "date",
			width: 150
		},
		{
			headerName: "Particulars",
			field: "Particulars",
			width: 150
		},
		{
			headerName: "invoice Id",
			field: "invoiceid",
			width: 150
		},
		{
			headerName: "Amount",
			field: "amount",
			valueFormatter: params => amountFormatter(params.data.amount.toFixed(2)),
			cellStyle: {
				textAlign: 'right'
			},
			width: 150
		},
		{
			headerName: "Due Date Warning",
			field: "dueDateWarning",
			width: 200,
			cellRenderer: function(params) {
				return getDueDateWarningRenderer(params.data.dueDateWarning);
			},
			cellStyle: function(params) {
				return {
					textAlign: 'left',
					fontFamily: 'Montserrat, sans-serif',
					fontSize: 'small'
				};
			}
		}
	];


const TDSOptions2 = {
	columnDefs: TDSdefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: tdsRowSelect

};

function tdsRowSelect() {
	var selectedRows = TDSOptions2.api.getSelectedRows();
	var selectedTdsAmount = 0;
	selectedRows.forEach(rows => {
		selectedTdsAmount += rows.amount;
	});

	selectedTdsAmount = parseFloat(selectedTdsAmount.toFixed(2));
	$("#totalTDSAmount").val(amountFormatter(selectedTdsAmount.toFixed(2)));
}

var columnDefs3 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		//checkboxSelection : true,
		checkboxSelection: function(params) {
			const paymentStatus = params.data.payStatus;
			return paymentStatus !== 'Fully Paid';
		},
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "INVOICE Id",
		field: "invoiceId",
		width: 120,
		pinned: 'left',
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
		/* cellRenderer : function(params) {
			return '<a onclick=editInvoice("' + params.data.invoiceId
			+'","'+params.data.poId+'","'+params.data.appoveStatus+'") href="javascript:void(0)">'
					+ params.data.invoiceId + '</a>';
		} */
	}, {
		headerName: 'Po Id',
		field: "poId",
		width: 150,
		cellStyle: {
			textAlign: 'left',
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: 'Tax Invoice No',
		field: "taxInvNo",
		width: 150,
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: 'Tax Invoice Date',
		field: "taxInvDate",
		width: 150,
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: 'Vendor name',
		field: "vendorName",
		width: 250,
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: 'Vendor ID',
		field: "vendorId",
		width: 150,
		hide: true,
		cellStyle: {
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Approve Status',
		field: "appoveStatus",
		width: 150,
		hide: true,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			if (params.data.appoveStatus == "Pending") {
				return '<div style="color:#a9a9a9">Pending</div>';
			} else {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	}, {

		headerName: 'Amount',
		field: "grandTotal",
		width: 110,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.grandTotal.toFixed(2)),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Paid Amount',
		field: "paidAmount",
		width: 110,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.paidAmount.toFixed(2)),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Outstanding Amount',
		field: "outstandingAmount",
		width: 110,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.outstandingAmount.toFixed(2)),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Payment Status',
		field: "payStatus",
		width: 150,
		cellStyle: {
			textAlign: 'center',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			if (params.data.payStatus == "Pending") {
				return '<div style="color:red;font-weight: bold;">' + params.data.payStatus + '</div>';
			} if (params.data.payStatus == "Fully Paid") {
				return '<div style="color:blue;font-weight: bold;">' + params.data.payStatus + '</div>';
			} else {
				return '<div style="color:orange;font-weight: bold;">' + params.data.payStatus + '</div>';
			}
		}
	}, {
		headerName: 'Due date',
		field: "qutValidDate",
		width: 150,
		cellStyle: {
			textAlign: 'left',
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: 'Scheduled date',
		field: "scheduleDate",
		width: 150,
		cellStyle: {
			textAlign: 'center',
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},

	}];

gridOptions3 = {
	columnDefs: columnDefs3,
	rowSelection: 'multiple',
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
	onSelectionChanged: multiPayRowSelect


};

function multiPayRowSelect() {
	var selectedRows = gridOptions3.api.getSelectedRows();
	var selectedTdsAmount = 0;
	selectedRows.forEach(rows => {
		selectedTdsAmount += rows.outstandingAmount;
	});

	selectedTdsAmount = parseFloat(selectedTdsAmount.toFixed(2));
	$("#totalPaymentAmount").val(amountFormatter(selectedTdsAmount.toFixed(2)));
}


var columnDefs4 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "INVOICE Id",
		field: "invoiceid",
		width: 140,
		pinned: 'left',
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}

	}, {

		headerName: 'Amount',
		field: "Amount",
		width: 150,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.Amount),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Paid Amount',
		field: "PaidAmount",
		width: 150,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.PaidAmount),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Outstanding Amount',
		field: "OutStandingAmount",
		width: 150,
		type: 'rightAligned',
		valueFormatter: params => amountFormatter(params.data.OutStandingAmount),
		cellStyle: {
			color: 'black!important',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {

		headerName: 'Payment Status',
		field: "PaymentStatus",
		width: 150,
		cellStyle: {
			textAlign: 'center',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			if (params.data.PaymentStatus == "Pending") {
				return '<div style="color:red;font-weight: bold;">' + params.data.PaymentStatus + '</div>';
			} if (params.data.PaymentStatus == "Full Paid") {
				return '<div style="color:blue;font-weight: bold;">' + params.data.PaymentStatus + '</div>';
			} else {
				return '<div style="color:orange;font-weight: bold;">' + params.data.PaymentStatus + '</div>';
			}
		}
	}];

gridOptions4 = {
	columnDefs: columnDefs4,
	rowSelection: 'multiple',
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
};

var gridDiv4 = document.querySelector('#invoiceListInfoGrid');
new agGrid.Grid(gridDiv4, gridOptions4);

// for new button
function newBtn() {
	//	alert('hello');


	$("#ticket-editBtn").removeClass('d-none');
	$("#journal-save-btn").removeClass('d-none');
	$("#add-btn").addClass("d-none");
	$(".journalVchrHeaderInfo").addClass("d-none");

	$("#approve").hide();
	$("#return").hide();
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	gridOptions.api.deselectAll();
	$("#journalVoucher").text('');
	disableFalse();
	$("#delete-journal-btn").hide();
	$("#ticket-editBtn").hide();

	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#fyearDiv").hide();
	$("#fdateDiv").hide();
	$("#tdateDiv").hide();
	$("#filterDiv").hide();
	//$('#totalAmountFooterDiv').hide();
	$("#fdDiv").hide();
	$("#demo").show();
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');
	$("#voucherDate").val("");
	$("#description").val("");
	var today = new Date();
	var day = String(today.getDate()).padStart(2, '0');
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var year = today.getFullYear();

	var formattedDate = day + '-' + month + '-' + year;
	console.log("formatted date is --", formattedDate);
	$("#voucherDate").val(formattedDate);
	//$("#purchaseRegisterExcel").hide();
	//$("#journalVoucherPdf").hide();

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountSubGroupId_0" class="form-control creditAccountGroupIdCls">'
		+/* '<p class="mb-0">'	
						+'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>'		
						+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" oninput="formatAmount(this);" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();"> '
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#creditTbodyData").append(abc);

	$("#debitTbodyData").empty();
	var abc = '<tr class="tr_clone" id="debit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="debitAccountSubGroup_0" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+/* '<p class="mb-0">'
									+'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>'
									+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" oninput="formatAmount(this);" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);
	getVoucherNumber();
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	$("#cancel-journal-btn").removeClass("d-none");
}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#fdDiv").show();
	$("#filter").show();
	$("#demo").hide();
	$('#totalAmountFooterDiv').show();
	$("#fyearDiv").show();
	$("#fdateDiv").show();
	$("#tdateDiv").show();
	$("#filterDiv").show();
	$("#journalVoucher").text("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("");
	$('#accountNumber').val("");
	$('#status').val("");
	$("#purchaseRegisterExcel").show();
	$("#journalVoucherPdf").show();

	CKEDITOR.instances.description.setData("");
	/* current date filter data */
	/* 	var today = new Date();
		var dateString  = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
		var fromDate = dateString .toString();
		var todate = dateString .toString(); */

	$("#toDate").val(todate);
	$("#fromDate").val(fromDate);
	viewFilteredData()
}

// Edit & stage change 
function editPage(id) {

	var editId = id.split(",");

	var journalVoucher = editId[0];

	var modal = editId[1];
	//	alert(id);

	//$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#fyearDiv").hide();
	$("#fdateDiv").hide();
	$("#tdateDiv").hide();
	$("#filterDiv").hide();
	$("#fdDiv").hide();
	$(".container").hide();
	$("#demo").show();
	$("#delete-journal-btn").show();
	$("#ticket-editBtn").show();
	//$("#purchaseRegisterExcel").hide();
	//$("#journalVoucherPdf").hide();
	$("#infoIconBtn").hide();


	//	alert('journalVoucherId------'+journalVoucher);

	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-edit?id=" + journalVoucher,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				//console.log("response for edit------"+JSON.stringify(response));
				$("#debitTbodyData").empty();
				$("#creditTbodyData").empty();
				$('#costCenter').val(response.body[0].costCenter).trigger('change');
				//$('#description').val(response.body[0].description);
				$('#voucherNumber').text(response.body[0].journalVoucher);
				$('#journalVoucher').text(response.body[0].journalVoucher);
				$('#voucherDate').val(response.body[0].voucherDate);
				CKEDITOR.instances['description'].setData(response.body[0].description);

				var toTotalAmount = 0;
				var fromTotalAmount = 0;
				for (var i = 0; i < response.body.length; i++) {
					if (response.body[i].transactionType == "Debit") {
						var amount = response.body[i].fromAmount;
						fromTotalAmount = fromTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"  id="debit_' + i + '">'
							+ '<td><input type="text" id="debitAccountSubGroup_' + i + '" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
							+ '<div id="suggesstion-box1_0"></div>'
							+ '<input type="hidden" id="debitAccountGroupId_' + i + '" class="form-control debitAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'
							/* +'<p class="mb-0">Balance: ₹<span id="debitCurrentBalance_'+i+'" class="debitAccountGroupBalance">'+response.body[i].currentBalance+'</span>'
							+'</p>' */
							+ '</td>'
							/* +'<td><input type="text" id="debitName_'+i+'" class="form-control debitNameCls" value="'+response.body[i].fromName+'">'
							+'<p class="mb-0" style="height:22px;"></p>'
							+'</td>' */
							+ '<td>'
							+ '<input type="text" id="debitAmount_' + i + '" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ ''
							+ '</td>'
							+ '<td class="d-flex">'
							+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
							+ '<span class="ti-plus"></span></button>&nbsp;</td>'
							+ '</tr>';
						$("#debitTbodyData").append(abc);
					}
					if (response.body[i].transactionType == "Credit") {
						toTotalAmount = toTotalAmount + response.body[i].fromAmount;
						var amount = response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"   id="credit_' + i + '">'
							+ '<td class ="legerSearchDiv "><input type="text" id="creditAccountSubGroup_' + i + '" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" value="' + response.body[i].subGroupName + '"><i class="bi bi-info-square-fill infoIcon" id = "infoIconBtn" onclick="getInvoiceInfo(this);"></i>'
							+ '<div id="suggesstion-box2_0"></div>'
							+ '<input type="hidden" id="creditAccountSubGroupId_' + i + '"  class="form-control creditAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'
							/* 	+'<p class="mb-0">Balance: ₹<span id="debitCurrentBalance_'+i+'" class="debitAccountGroupBalance">'+response.body[i].currentBalance+'</span>'
								+'</p>' */
							+ '</td>'
							/* +'<td><input type="text" id="creditName_'+i+'"	class="form-control creditNameCls" value="'+response.body[i].fromName+'"></td>' */
							+ '<td><input type="text" id="creditAmount_' + i + '" class="form-control creditAmountCls"  onblur="changeToDecimal(this),getToSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ ''
							+ '</td>'
							+ '<td class="d-flex"><button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
							+ '<span class="ti-plus"></span>'
							+ '</button>&nbsp;</td>'
							+ '</tr>';

						$("#creditTbodyData").append(abc);
					}
					$('#fromTotalAmount').text(amountFormatter((parseFloat(fromTotalAmount)).toFixed(2)));
					$('#toTotalAmount').text(amountFormatter((parseFloat(toTotalAmount)).toFixed(2)));
				}

				if (response.body[0].newRefLedgerId == "True") {
					$("#infoIconBtn").show();
				}
				else if (response.body[0].newRefLedgerId == "" || response.body[0].newRefLedgerId == "False") {

					$("#infoIconBtn").hide();
				}

			}
		}
	})
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


let globalContraVoucherId = '';
function addJournalInfo() {
	var dataset = [];
	item = {};

	$("#description").val(CKEDITOR.instances.description.getData());
	globalContraVoucherId = $("#journalVoucher").text();
	$("#debitTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#fromTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#description").val();
		item['voucherDate'] = $("#voucherDate").val();
		item['fromAccountSubGroup'] = $(this).find(".debitAccountGroupIdCls").val();
		item['fromName'] = $(this).find(".debitNameCls").val();
		item['fromAmount'] = $(this).find(".debitAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "JOURNAL";
		item['taxType'] = $("#taxType").val();
		item['paymentType'] = $("#modAdjustment").val();
		dataset.push(item);
	});//table tbody tr loop ends
	$("#creditTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#toTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#description").val();
		item['voucherDate'] = $("#voucherDate").val();
		item['toAccountSubGroup'] = $(this).find(".creditAccountGroupIdCls").val();
		item['toName'] = $(this).find(".creditNameCls").val();
		item['toAmount'] = $(this).find(".creditAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "JOURNAL";
		item['taxType'] = $("#taxType").val();
		item['paymentType'] = $("#modAdjustment").val();
		item['paymentDesc'] = $("#transName").val();
		item['advOrNewRfAmount'] = $("#transAmount").val();
		item['newRefLedgerId'] = $("#debitLegderId").val();
		item['advReceiveObj'] = JSON.stringify(advanceReceiveDetails);
		dataset.push(item);
	});//table tbody tr loop ends

	console.log(item);
	console.log("add journal dataset----------------------" + JSON.stringify(dataset));
	console.log("Data set-->", dataset);
	//submitJournal(dataset);

	//Blank Validations

	/*if (!blankValidation("costCenter", "SelectBox", "Please Select Cost Center Name"))
		return false;

	if (!blankValidation("description", "TextField", "Please Enter Narration"))
		return false;
	var debitAccountGroup = true;
	$('.debitAccountSubGroupCls').each(function() {
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Account Ledger")) {

			debitAccountGroup = false;
			return false;
		}

	});

	var debitName = true;
	if (debitAccountGroup) {
		$('.debitNameCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Name")) {

				debitName = false;
				return false;
			}

		});
	}
	var debitAmount = true;
	if (debitAccountGroup && debitName) {
		$('.debitAmountCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Amount ")) {

				debitAmount = false;
				return false;
			}

		});
	}
	var creditAccountGroup = true;
	if (debitAccountGroup && debitName && debitAmount) {
		$('.creditAccountSubGroupCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Account Ledger")) {

				creditAccountGroup = false;
				return false;
			}

		});
	}
	var creditName = true;
	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup) {
		$('.creditNameCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Name")) {

				creditName = false;
				return false;
			}

		});
	}
	var creditAmount = true;
	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup && creditName) {
		$('.creditAmountCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Amount ")) {

				creditAmount = false;
				return false;
			}

		});
	}

	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup && creditName && creditAmount) {
		var credit = 0;
		var debit = 0;
		$(".debitAmountCls").each(function(i) {
			debit = debit + parseFloat($(this).val().replace(/,/g, ''));
		});

		$(".creditAmountCls").each(function(i) {
			credit = credit + parseFloat($(this).val().replace(/,/g, ''));
		});

		if (parseFloat(credit).toFixed(2) == parseFloat(debit).toFixed(2)) {
			submitJournal(dataset);
		}
		else {
			swal("Debit Amount not same with credit Amount");
			return false;
		}
	}*/

	var debitAccountGroup = true;
	$('.debitAccountSubGroupCls').each(function() {
		if (!$(this).val()) { // Check if the value is empty
			toastr.error("Please Enter Ledger");
			debitAccountGroup = false;
			return false; // Stop the loop if validation fails
		}
	});

	if (debitAccountGroup) {
		var debitName = true;
		$('.debitNameCls').each(function() {
			if (!$(this).val()) { // Check if the value is empty
				toastr.error("Please Enter Name");
				debitName = false;
				return false; // Stop the loop if validation fails
			}
		});
	}

	if (debitAccountGroup && debitName) {
		var debitAmount = true;
		$('.debitAmountCls').each(function() {
			if (!$(this).val()) { // Check if the value is empty
				toastr.error("Please Enter Amount");
				debitAmount = false;
				return false; // Stop the loop if validation fails
			}
		});
	}

	if (debitAccountGroup && debitName && debitAmount) {
		var creditAccountGroup = true;
		$('.creditAccountSubGroupCls').each(function() {
			if (!$(this).val()) { // Check if the value is empty
				toastr.error("Please Enter Ledger");
				creditAccountGroup = false;
				return false; // Stop the loop if validation fails
			}
		});
	}

	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup) {
		var creditName = true;
		$('.creditNameCls').each(function() {
			if (!$(this).val()) { // Check if the value is empty
				toastr.error("Please Enter Name");
				creditName = false;
				return false; // Stop the loop if validation fails
			}
		});
	}

	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup && creditName) {
		var creditAmount = true;
		$('.creditAmountCls').each(function() {
			if (!$(this).val()) { // Check if the value is empty
				toastr.error("Please Enter Amount");
				creditAmount = false;
				return false; // Stop the loop if validation fails
			}
		});
	}


	if (!validateSameLedgerInDebitCredit()) {
		return false;
	}

	// Now check the #costCenter and #description after all previous validations
	if (debitAccountGroup && debitName && debitAmount && creditAccountGroup && creditName && creditAmount) {
		// Check #costCenter
		if ($("#costCenter").val() === "") {
			toastr.error("Please Select Cost Center Name");
			return false; // Stop the program
		}

		// Check #description
		if ($("#description").val() === "") {
			toastr.error("Please Enter Narration");
			return false; // Stop the program
		}

		// If everything is validated, proceed with further logic
		var debit = 0, credit = 0;

		$(".debitAmountCls").each(function() {
			debit += parseFloat($(this).val().replace(/,/g, ''));
		});

		$(".creditAmountCls").each(function() {
			credit += parseFloat($(this).val().replace(/,/g, ''));
		});

		if (parseFloat(credit).toFixed(2) === parseFloat(debit).toFixed(2)) {
			submitJournal(dataset); // Proceed with the submission
		} else {
			toastr.error("Debit Amount not same with credit Amount");
			return false; // Stop the program if debit and credit don't match
		}
	}

}
function deleteJournalVoucher() {

	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "200") {
				//cancelBtn();
				toastr.success(response.message);
				viewFilteredData();

			}
		}

	});


}


function approveVoucher() {
	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-approve-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();

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

			}
		}

	});

	$('#delete').attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#return").attr("disabled", true);
	$("#reject").attr("disabled", true);
}

//view-account-journal-voucher


function debitSubGroup(id) {

	var data = $("#debitAccountSubGroup_0").val();
	if (data.length == 0) {
		$("#suggesstion-box1_0").hide();
	}

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
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box1_" + counter).show();
					$("#suggesstion-box1_" + counter).html(content);

				}
				else {
					console.log("else: " + response);
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li  onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box1_" + counter).show();
					$("#suggesstion-box1_" + counter).html(content);
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


	let fullLedgerName = sGroupName + '( ' + sGroupId + ' )';

	if (isDuplicateLedger(fullLedgerName, sGroupId, "debitType")) {
		toastr.error("Ledger already added! Please select a different ledger.");

		return;
	}

	if (sGroupId) {
		$("#debitAccountSubGroup_" + counter).val(sGroupName + '( ' + sGroupId + ' )');
		$("#debitAccountGroupId_" + counter).val(sGroupId);
		$("#debitCurrentBalance_" + counter).text(amount);
		$("#debitAccountSubGroup_" + counter).attr('data-procat', sGroupName);
		$("#suggesstion-box1_" + counter).hide();


	} else {
		$("#debitAccountSubGroup_" + counter).val("");
		$("#debitAccountGroupId_" + counter).val("");
		$("#debitCurrentBalance_" + counter).val("");
		$("#debitAccountSubGroup_" + counter).attr('data-procat', "");
		$("#suggesstion-box1_" + counter).hide();
	}
}

function addLedger(selectedId) {

	$('#selectedIdToggle').val(selectedId)
	$('#ledgerId').text('');
	$('#ledgername').val('');
	$('#undergroupId').val('');
	$('#groupName').val('');
	$('#ledgerEmail').val('');
	$('#ledgerAddress').val('');
	$('#ledgerCountry').val('');
	$('#ledgerState').val('');
	$('#ledgerPinCode').val('');
	$('#ledgerMobile').val('');
	$('#ledgerPan').val('');
	$('#ledgerGst').val('');
	$("#suggesstion-box11_").hide();

	$('#addLedgerModal').modal('show');
}

//function for auto filled of item Requisition
function creditSubGroup(id) {
	var l = id.split("_");
	var counter = l[1];
	var search = $("#" + id).val();

	$.ajax({
		type: "POST",
		url: "view-account-journal-voucher-getAccountCreditGroup",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			console.log(response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					/* for (var i = 0; i < response.body.length; i++) {
						content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: blue; color: white" onClick="autocompleteValue2(\''+response.body[i].debAccGroupId+'\',\''+response.body[i].debitedAccGroup+'\',\''+id+'\',\''+response.body[i].amount+'\')">'+response.body[i].debitedAccGroup+'('+response.body[i].debAccGroupId+')'+'</li>';
						}  */

					for (var i = 0; i < response.body.length; i++) {
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\',\'' + response.body[i].cvDescription + '\',\'' + response.body[i].contraVoucherType + '\',\'' + response.body[i].createdBy + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}

					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);

					/*if (response.body[i].contraVoucherType == "False") {
						$("#modAdjustment").val("");
					}*/

				}
				else {
					console.log("else: " + response);
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li  onClick="autocompleteValue2(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);
				}
			}
		},
		error: function(data) {
			console.log(data);
		}
	})

	if (search.length == 0) {
		$("#suggesstion-box2_" + counter).hide();
	}

	$("#amountDivId").val(id);

}

/* 	function autocompleteValue2(sGroupId,sGroupName,id,amount) { */
function autocompleteValue2(sGroupId, sGroupName, id, amount, taxType, multiRcvType, vendorId) {

	console.log()
	var l = id.split("_");
	var counter = l[1];

	let fullLedgerName = sGroupName + '( ' + sGroupId + ' )';

	if (isDuplicateLedger(fullLedgerName, sGroupId, "credit")) {
		toastr.error("Ledger already added! Please select a different ledger.");

		return;
	}

	if (taxType != "False") {
		TdsVoucherList(sGroupId, taxType);

	}

	if (multiRcvType != "False" && (vendorId != "" || vendorId != null)) {

		$("#ledgerNameModal").text(sGroupName);
		$("#ledgerNameInfo").text(sGroupName);
		$("#ledgerNameModalId").val(vendorId);
		$("#debitLegderId").val(sGroupId);
		getInvoiceDataList(vendorId);
	}

	/*if (sGroupId) {
		$("#creditAccountSubGroup_" + counter).val(sGroupName + '( ' + sGroupId + ' )');
		//$("#creditAccountSubGroupId_" + counter).val(sGroupId);
		$("#creditAccountGroupId_" + counter).val(sGroupId);
		$("#creditCurrentBalance_" + counter).text(amount);
		$("#creditAccountSubGroup_" + counter).attr('data-procat', sGroupName);
		$("#suggesstion-box2_" + counter).hide();

	} else {
		$("#creditAccountSubGroup_" + counter).val("");
		$("#creditAccountSubGroupId_" + counter).val("");
		$("#creditCurrentBalance_" + counter).text("");
		$("#creditAccountSubGroup_" + counter).attr('data-procat', "");
		$("#suggesstion-box2_" + counter).hide();
	}*/

	if (sGroupId) {
		$("#creditAccountSubGroup_" + counter).val(sGroupName + '( ' + sGroupId + ' )');
		$("#creditAccountSubGroupId_" + counter).val(sGroupId);
		$("#creditCurrentBalance_" + counter).text(amount);
		$("#creditAccountSubGroup_" + counter).attr('data-procat', sGroupName);
		$("#suggesstion-box2_" + counter).hide();

	} else {
		$("#creditAccountSubGroup_" + counter).val("");
		$("#creditAccountSubGroupId_" + counter).val("");
		$("#creditCurrentBalance_" + counter).text("");
		$("#creditAccountSubGroup_" + counter).attr('data-procat', "");
		$("#suggesstion-box2_" + counter).hide();
	}
}

function TdsVoucherList(ledgerId, taxType) {
	$("#taxType").val(taxType);
	var totalSelectedAmount = 0;

	//console.log("TaxType-->",taxType)
	$.ajax({
		type: "GET",
		url: "receipt-voucher-getTDSVoucherList?id=" + ledgerId,
		success: function(response) {
			if (response.code == "success") {
				var responseData = JSON.parse(response.body);
				var tdsData = responseData.getTdsVoucherList;
				TDSOptions2.api.setRowData(tdsData);
				TDSOptions2.api.forEachNode(function(node) {
					node.setSelected(true);
				});
				TDSOptions2.api.forEachNode(rows => {
					//console.log("rows-->",rows);
					totalSelectedAmount += rows.data.amount;
				})
				$("#myModalTaxDeduction").modal('show');
				$("#totalTDSAmount").val(amountFormatter(totalSelectedAmount));
			}
		}

	});

}



/*function debitCheckEmpty() {
	let valid = true;

	$('.debitAccountSubGroupCls').each(function() {
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {
			valid = false;
			return false;
		}
	});

	if (valid) {
		$('.debitAmountCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Amount")) {
				valid = false;
				return false;
			}
		});
	}

	if (valid) {
		debitAddMore();
	}
}*/

function debitCheckEmpty() {
	let valid = true;

	$('.debitAccountSubGroupCls').each(function() {
		if ($(this).val().trim() === "") {
			toastr.error("Please Enter Ledger");
			valid = false;
			return false; // break the loop
		}
	});

	if (valid) {
		$('.debitAmountCls').each(function() {
			if ($(this).val().trim() === "") {
				toastr.error("Please Enter Amount");
				valid = false;
				return false; // break the loop
			}
		});
	}

	if (valid) {
		debitAddMore();
	}
}



function debitAddMore() {
	let tbody = $("#debitTbodyData");
	let rowCount = tbody.children('tr').length;

	// Clone first row
	let clone = tbody.children('tr:first').clone();

	// Clear values
	clone.find('input').val('');
	clone.find('input').prop('disabled', false);

	// Update IDs
	clone.attr('id', 'debit_' + rowCount);
	clone.find('.debitAccountSubGroupCls').attr('id', 'debitAccountSubGroup_' + rowCount);
	clone.find('.debitAccountGroupIdCls').attr('id', 'debitAccountGroupId_' + rowCount);
	clone.find('.debitAmountCls').attr('id', 'debitAmount_' + rowCount);
	clone.find("div[id^='suggesstion-box1_']").attr('id', 'suggesstion-box1_' + rowCount);

	// Add buttons
	let addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>';
	let removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" onclick="removeDebitRow(this);"><span class="ti-minus"></span></button>';
	clone.find('td:last').html(addBtn + '&nbsp;' + removeBtn);

	// Remove all add buttons from existing rows
	tbody.find('.tr_clone_add').remove();

	// Append new row
	tbody.append(clone);
}


function removeDebitRow(button) {
	// Remove the clicked row
	$(button).closest('tr').remove();

	let rows = $("#debitTbodyData").children('tr');

	// Reassign IDs
	rows.each(function(i) {
		$(this).attr('id', 'debit_' + i);
		$(this).find('.debitAccountSubGroupCls').attr('id', 'debitAccountSubGroup_' + i);
		$(this).find('.debitAccountGroupIdCls').attr('id', 'debitAccountGroupId_' + i);
		$(this).find('.debitAmountCls').attr('id', 'debitAmount_' + i);
		$(this).find("div[id^='suggesstion-box1_']").attr('id', 'suggesstion-box1_' + i);
	});

	//getFromSubtotal();

	rows.find('.tr_clone_add').remove();

	if (rows.length > 0) {
		let lastRow = rows.last();
		let addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>';

		lastRow.find('td:last').html(addBtn + '&nbsp;');

		if (rows.length > 1) {
			let removeBtn = '<button type="button" class="btn btn-warning go-btn" onclick="removeDebitRow(this);"><span class="ti-minus"></span></button>';
			lastRow.find('td:last').append(removeBtn);
		}
	}

	var sum = 0;
	$(".debitAmountCls").each(function(i) {
		let val = $(this).val().replace(/,/g, '');
		val = val.trim(); // remove spaces

		if (val === '' || isNaN(val)) {
			val = 0;
		}
		sum += parseFloat(val);
	});

	$("#fromTotalAmount").html(amountFormatter(sum.toFixed(2)));
	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
	$("#creditAmount_0").val(amountFormatter(sum.toFixed(2)));

	//getToSubtotal()
}


/*function creditCheckEmpty() {
	let creditValid = true;

	$('.creditAccountSubGroupCls').each(function() {
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {
			creditValid = false;
			return false;
		}
	});

	if (creditValid) {
		$('.creditAmountCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Amount")) {
				creditValid = false;
				return false;
			}
		});
	}

	if (creditValid) {
		creditAddMore();
	}
}*/

function creditCheckEmpty() {
	let creditValid = true;

	$('.creditAccountSubGroupCls').each(function() {
		if ($(this).val().trim() === "") {
			toastr.error("Please Enter Ledger");
			creditValid = false;
			return false;
		}
	});

	if (creditValid) {
		$('.creditAmountCls').each(function() {
			if ($(this).val().trim() === "") {
				toastr.error("Please Enter Amount");
				creditValid = false;
				return false;
			}
		});
	}

	if (creditValid) {
		creditAddMore();
	}
}

function creditAddMore() {
	let rowCount = $("#creditTbodyData").children('tr').length;
	let $clone = $("#creditMyTable tbody tr:first").clone();

	// Clear values in the cloned row
	$clone.find('input').val('');
	$clone.find("div[id^='suggesstion-box2_']").hide();
	$clone.find("ul#autocomplete-list").empty();

	// Remove existing buttons
	$clone.find('td:last').html('');

	// Append cloned row
	$("#creditTbodyData").append($clone);

	// Reassign IDs
	$("#creditTbodyData").children('tr').each(function(i) {
		$(this).attr('id', 'credit_' + i);
		$(this).find('.creditAccountSubGroupCls').attr('id', 'creditAccountSubGroup_' + i);
		$(this).find('.creditAccountGroupIdCls').attr('id', 'creditAccountSubGroupId_' + i);
		$(this).find('.creditAmountCls').attr('id', 'creditAmount_' + i);
		$(this).find("div[id^='suggesstion-box2_']").attr('id', 'suggesstion-box2_' + i);
		$(this).find('.creditNameCls').attr('id', 'creditName_' + i);
	});

	// Remove all add buttons
	$("#creditTbodyData").find('.tr_clone_add').remove();

	let rows = $("#creditTbodyData").children('tr');
	if (rows.length > 0) {
		let lastRow = rows.last();
		let addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>';
		lastRow.find('td:last').html(addBtn + '&nbsp;');

		if (rows.length > 1) {
			let removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" onclick="removeCreditRow(this);"><span class="ti-minus"></span></button>';
			lastRow.find('td:last').append(removeBtn);
		}
	}
}

/*function creditAddMore() {
	let rowCount = $("#creditTbodyData").children('tr').length;
	let $clone = $("#creditMyTable tbody tr:first").clone();

	// Clear values in the cloned row
	$clone.find('input').val('');
	$clone.find("div[id^='suggesstion-box2_']").hide();
	$clone.find("ul#autocomplete-list").empty();

	// Remove existing buttons
	$clone.find('td:last').html('');

	// Append cloned row
	$("#creditTbodyData").append($clone);

	// Reassign IDs
	$("#creditTbodyData").children('tr').each(function(i) {
		$(this).attr('id', 'credit_' + i);
		$(this).find('.creditAccountSubGroupCls').attr('id', 'creditAccountSubGroup_' + i);
		$(this).find('.creditAccountGroupIdCls').attr('id', 'creditAccountGroupId_' + i);
		$(this).find('.creditAmountCls').attr('id', 'creditAmount_' + i);
		$(this).find("div[id^='suggesstion-box2_']").attr('id', 'suggesstion-box2_' + i);
		$(this).find('.creditNameCls').attr('id', 'creditName_' + i);
	});

	// Remove all add buttons
	$("#creditTbodyData").find('.tr_clone_add').remove();

	let rows = $("#creditTbodyData").children('tr');
	if (rows.length > 0) {
		let lastRow = rows.last();
		let addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>';
		lastRow.find('td:last').html(addBtn + '&nbsp;');

		if (rows.length > 1) {
			let removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" onclick="removeCreditRow(this);"><span class="ti-minus"></span></button>';
			lastRow.find('td:last').append(removeBtn);
		}
	}
}*/

function removeCreditRow(button) {
	$(button).closest('tr').remove();

	let rows = $("#creditTbodyData").children('tr');

	// Reassign IDs
	rows.each(function(i) {
		$(this).attr('id', 'credit_' + i);
		$(this).find('.creditAccountSubGroupCls').attr('id', 'creditAccountSubGroup_' + i);
		$(this).find('.creditAccountGroupIdCls').attr('id', 'creditAccountGroupId_' + i);
		$(this).find('.creditAmountCls').attr('id', 'creditAmount_' + i);
		$(this).find("div[id^='suggesstion-box2_']").attr('id', 'suggesstion-box2_' + i);
		$(this).find('.creditNameCls').attr('id', 'creditName_' + i);
	});

	// Remove all add buttons
	rows.find('.tr_clone_add').remove();

	// Add add-button to last row
	if (rows.length > 0) {
		let lastRow = rows.last();
		let addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>';
		lastRow.find('td:last').html(addBtn + '&nbsp;');

		if (rows.length > 1) {
			let removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" onclick="removeCreditRow(this);"><span class="ti-minus"></span></button>';
			lastRow.find('td:last').append(removeBtn);
		}
	}

	// Update subtotal if needed
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		let val = $(this).val().replace(/,/g, '').trim();

		if (val === '' || isNaN(val)) {
			val = 0;
		}
		sum += parseFloat(val);
	});
	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2))); // Adjust this ID as needed
	//getFromSubtotal(); // If needed
}



/*function creditCheckEmpty() {
	var creditAccountGroup = true;
	$('.creditAccountSubGroupCls').each(function() {
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Account Ledger")) {

			creditAccountGroup = false;
			return false;
		}

	});
	var creditName = true;
	if (creditAccountGroup) {
		$('.creditNameCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Name")) {

				creditName = false;
				return false;
			}

		});
	}
	var creditAmount = true;
	if (creditAccountGroup && creditName) {
		$('.creditAmountCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Amount ")) {

				creditAmount = false;
				return false;
			}

		});
	}
	if (creditAccountGroup && creditName && creditAmount) {

		creditAddMore();
	}
}*/
//Function for adding more Table row

/*function debitAddMore() {
	var lengthOfTableRow = $("#debitTbodyData").children('tr').length;
	var cloneHtml = $("#debitMyTable tbody tr:first").clone();
	$("#debitMyTable tbody tr:last").find('td:last').html('');
	$("#debitMyTable tbody").append($("#debitMyTable tbody tr:first").clone());
	$("#debitMyTable tbody tr td:last").html("");
	var addMore = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
	var removeMore = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';


	$("#debitMyTable tbody tr:last").find('td:last').append(addMore);
	$("#debitMyTable tbody tr:last").find('td:last').append(removeMore);
	$("#debitMyTable tbody tr:last").find('.debitAccountSubGroupCls').val("");
	$("#debitMyTable tbody tr:last").find('.debitNameCls').val("");
	$("#debitMyTable tbody tr:last").find('.debitAmountCls').val("");
	var editTr = 0;

	if (lengthOfTableRow > editTr) {
		$("#debitMyTable tbody tr").eq(lengthOfTableRow - 1).find('td:last').append(removeMore);

	}

	// BLANK FIELD START
	//$("#debitMyTable tbody tr:last").find(".tableNameCls").val("");

	$("#debitMyTable > tbody > tr").each(function(i) {
		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var divInput = $(this).find('div');
		var hiddenInput = $(this).find('hidden');
		var tr = $(this).find('tr');
		textInput.eq(0).attr('id', "debitAccountSubGroup_" + i);
		textInput.eq(2).attr('id', "debitAmount_" + i);
		textInput.eq(3).attr('id', "debitAmount_" + i);
		divInput.eq(0).attr('id', "suggesstion-box1_" + i);
		textInput.eq(1).attr('id', "debitAccountGroupId_" + i);
		tr.eq(0).attr('id', "debit_" + i);

	})
}*/
//Function for adding more Table row

/*function creditAddMore() {
	var lengthOfTableRow = $("#creditTbodyData").children('tr').length;
	var cloneHtml = $("#creditMyTable tbody tr:first").clone();
	$("#creditMyTable tbody tr:last").find('td:last').html('');
	$("#creditMyTable tbody").append($("#creditMyTable tbody tr:first").clone());
	$("#creditMyTable tbody tr td:last").html("");
	var addMore = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
	var removeMore = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';


	$("#creditMyTable tbody tr:last").find('td:last').append(addMore);
	$("#creditMyTable tbody tr:last").find('td:last').append(removeMore);
	$("#creditMyTable tbody tr:last").find('.creditAccountSubGroupCls').val("");
	$("#creditMyTable tbody tr:last").find('.creditNameCls').val("");
	$("#creditMyTable tbody tr:last").find('.creditAmountCls').val("");
	var editTr = 0;

	if (lengthOfTableRow > editTr) {
		$("#creditMyTable tbody tr").eq(lengthOfTableRow - 1).find('td:last').append(removeMore);

	}

	// BLANK FIELD START
	//$("#creditMyTable tbody tr:last").find(".tableNameCls").val("");

	$("#creditMyTable > tbody > tr").each(function(i) {
		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var divInput = $(this).find('div');
		var hiddenInput = $(this).find('hidden');
		var tr = $(this).find('tr');
		textInput.eq(0).attr('id', "creditAccountSubGroup_" + i);
		textInput.eq(2).attr('id', "creditAmount_" + i);
		textInput.eq(3).attr('id', "creditAmount_" + i);
		divInput.eq(0).attr('id', "suggesstion-box2_" + i);
		textInput.eq(1).attr('id', "creditAccountSubGroupId_" + i);
		tr.eq(0).attr('id', "credit_" + i);

	})
}*/
//add More at edit time
$(document).ready(function() {
	var lengthOfTableRow = $("#debitTbodyData").children('tr').length;
	$('.tbll').on('click', '.rmv', function() {
		$(this).closest('tr').remove();

		$("#debitMyTable tbody tr:last").find('td:last').html('');
		var add = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
		var remove = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';

		if ($("#debitMyTable").children('tr').length > 1) {
			$("#debitMyTable tbody tr:last").find('td:last').append(add);
			$("#debitMyTable tbody tr:last").find('td:last').append(remove);
		}
		else {
			$("#debitMyTable tbody tr:last").find('td:last').append(add);
		}
	});
	var lengthOfTableRow = $("#creditTbodyData").children('tr').length;
	$('.tbll').on('click', '.rmv', function() {
		$(this).closest('tr').remove();

		$("#creditMyTable tbody tr:last").find('td:last').html('');
		var add = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
		var remove = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';

		if ($("#creditMyTable").children('tr').length > 1) {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
			$("#creditMyTable tbody tr:last").find('td:last').append(remove);
		}
		else {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
		}
	});




	//Function for getting Table Name on change of spa Name
});

function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	value = value.replace(/,/g, '');
	var floatValue = (parseFloat(value).toFixed(2));
	console.log("float value==>", floatValue);
	if (isNaN(floatValue)) {
		floatValue = 0.00;
	}
	if (id.includes('debitAmount')) {
		$("#" + id).val(amountFormatter(floatValue));
		var counter = id.split('_')[1];
		$("#creditAmount_" + counter).val(amountFormatter(floatValue));
	} else if (id.includes('creditAmount')) {
		if (floatValue > 0) {
			$("#" + id).val(floatValue);
		} else {
			$("#" + id).val(0.00);
		}
	}

}

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
					const today = new Date();
					// Determine financial year
					const currentMonth = today.getMonth() + 1; // Jan = 0, so +1
					const currentYear = today.getFullYear();
					let fyStartYear, fyEndYear;

					if (currentMonth >= 4) {
						// April to December => same year
						fyStartYear = currentYear;
						fyEndYear = currentYear + 1;
					} else {
						// Jan to March => previous year start
						fyStartYear = currentYear - 1;
						fyEndYear = currentYear;
					}

					const fyString = `${fyStartYear}-${fyEndYear}`;

					// Set dropdown to current FY
					document.getElementById('orderStatusFilter').value = fyString;

					// Set from date as 1st of current month
					const fromDate = `01-${pad(today.getMonth() + 1)}-${today.getFullYear()}`;

					// Set to date as today
					const toDate = `${pad(today.getDate())}-${pad(today.getMonth() + 1)}-${today.getFullYear()}`;

					document.getElementById('fromDate').value = fromDate;
					document.getElementById('toDate').value = toDate;
					if (response.code == "201" || response.code == "200") {
						/*swal({
							title: "Voucher Status",
							text: "Journal voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/view-account-journal-voucher";
						})*/

						toastr.success(response.message);
						setTimeout(() => {
							viewFilteredData();
						}, 1000);
						$("#journal-save-btn").addClass("d-none");
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
		let val = $(this).val().replace(/,/g, '');
		if (val) sum += parseFloat(val);
	});

	$("#fromTotalAmount").html(amountFormatter(sum.toFixed(2)));

	// Optional: Set same amount in credit first row for autofill
	$("#creditAmount_0").val(amountFormatter(sum.toFixed(2)));
	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
}


function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		let val = $(this).val().replace(/,/g, '');
		if (val) sum += parseFloat(val);
	});

	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
}


/*function getFromSubtotal() {
	var sum = 0;
	$(".debitAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val().replace(/,/g, ''));
	})

	$("#fromTotalAmount").html(amountFormatter(sum.toFixed(2)));
	$("#creditAmount_0").val(amountFormatter(sum.toFixed(2)));
	getToSubtotal();
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val().replace(/,/g, ''));
	})

	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
}*/

function cancelLedgerBtn() {
	$("#addLedgerModal").modal('hide');
	$(".formValidation:contains('Ledger Name Required'):first").hide();
	$(".formValidation:contains('Under Group Name Required'):first").hide();
}

function getStateList() {
	var cname = $('#ledgerCountry').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-account-journal-voucher-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#ledgerState").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#ledgerState").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#ledgerState").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#ledgerState").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#ledgerState").append(option);
		$("#ledgerState").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}

}

function getGroupList() {
	$("#undergroupId").val("");
	var search = $("#groupName").val();
	if (search) {

		$
			.ajax({
				type: "POST",
				url: "view-account-contra-voucher-group-list",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.code == "success") {
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
									+ response.body[i].groupId
									+ '\',\''
									+ response.body[i].groupName
									+ '\')">'
									+ response.body[i].groupName
									+ '</li>';
							}
							content += '<li style="margin-left:-30px;" >'
								+ '</li>';
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li  onClick="selectAutocompleteValue1()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;" '
								+ '</li>';
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);
						}
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li  onClick="selectAutocompleteValue1()">'
							+ "No Data Found" + '</li>';
						content += '<li style="margin-left:-30px;" '
							+ '</li>';
						content += '</ul>';
						$("#suggesstion-box11_").show();
						$("#suggesstion-box11_").html(content);
					}
				},
				error: function(data) {


				}
			})
	} else {
		$("#search").css("background", "#FFF");
		var content = '<ul id="autocomplete-list1">';
		content += '<li  onClick="selectAutocompleteValue1()">'
			+ "No Data Found" + '</li>';
		content += '<li style="margin-left:-30px;" '
			+ '</li>';
		content += '</ul>';
		$("#suggesstion-box11_").show();
		$("#suggesstion-box11_").html(content);
	}

}
function selectAutocompleteValue1(categoryId, categoryName, custGSTNo,
	taxType) {

	if (categoryId) {
		$("#undergroupId").val(categoryId);
		$("#groupName").val(categoryName);

		$("#search").val(categoryName);
		$("#search").attr('data-procat', categoryId);
		$("#suggesstion-box11_").hide();

	} else {

		$("#undergroupId").val("");
		$("#groupName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box11_").hide();

	}
}

function removeAutoSearchList() {
	var search = $("#groupName").val();
	if (search == '' || search == null) {
		$("#suggesstion-box11_").hide();
	}
}


function ledgernameName() {
	var ledgernameLength = $("#ledgername").val();
	if (ledgernameLength.length > 0) {
		$(".formValidation:contains('Ledger Name Required'):first").hide();
	}
}

function groupName() {
	var groupNameLength = $("#groupName").val();
	if (groupNameLength.length > 0) {
		$(".formValidation:contains('Under Group Name Required'):first").hide();
	}
}

function saveLedgerDetails() {

	var obj = {};

	obj.leadgerId = $('#ledgerId').text();
	obj.ledgername = $('#ledgername').val();
	obj.groupId = $('#undergroupId').val();
	obj.ledgerEmail = $('#ledgerEmail').val();
	obj.ledgerAddress = $('#ledgerAddress').val();
	obj.ledgerCountry = $('#ledgerCountry').val();
	obj.ledgerState = $('#ledgerState').val();
	obj.ledgerPinCode = $('#ledgerPinCode').val();
	obj.ledgerMobile = $('#ledgerMobile').val();
	obj.ledgerPan = $('#ledgerPan').val();
	obj.ledgerGst = $('#ledgerGst').val();

	console.log("object on add-----------" + JSON.stringify(obj));


	var validation = true;

	if (obj.ledgername == null || obj.ledgername == "") {
		validation = validationUpdated("Ledger Name Required",
			"ledgername");
	}

	if (obj.groupId == null || obj.groupId == "") {
		validation = validationUpdated("Under Group Name Required",
			"undergroupId");
	}


	/* Validation for ledgerName And GroupId */

	if (obj.ledgername && obj.groupId) {

		agGrid.simpleHttpRequest({
			url: "view-account-journal-voucher-getLedgerName"
		}).then(function(data) {

			console.log("Data", data);

			console.log(obj.ledgername, "ledger name");
			console.log(obj.groupId, "groupId name");

			for (var i = 0; i < data.length; i++) {
				if (obj.ledgername.toLowerCase() == data[i].groupName.toLowerCase() && obj.groupId == data[i].groupId) {

					console.log(data[i].groupName);
					console.log(data[i].groupId)
					Swal.fire({
						icon: 'error',
						title: 'Oops...',
						text: 'Ledger Name Is Already Exists',

					});
					validation = false;
					break;
				}
			}

			if (validation) {
				$.ajax({
					type: "POST",
					url: "view-account-journal-voucher-group-addLedger",
					contentType: "application/json",
					data: JSON.stringify(obj),
					success: function(response) {
						if (response.message == "Success") {
							//console.log(response);
							$("#addLedgerModal").modal('hide');
							var toggle = $('#selectedIdToggle').val();
							if (toggle == 'debitLedger') {
								debitSubGroup('debitAccountSubGroup_0');
							} else if (toggle == 'creditLedger') {
								creditSubGroup('creditAccountSubGroup_0');
							}

							toastr.error("Ledger added successfully!", " ", "success");
						}
					},
					error: function(data) {

						//console.log(data);
					}
				})
			}
		});

	}






}

function viewFilteredData() {
	let voucherId = globalContraVoucherId;
	var fromDateFilter = $("#fromDate").val();
	var toDateFilter = $("#toDate").val();

	function convertToDate(dateStr) {
		var parts = dateStr.split("-");
		return new Date(parts[2], parts[1] - 1, parts[0]);
	}

	var fromDate = convertToDate(fromDateFilter);
	var toDate = convertToDate(toDateFilter);

	if (fromDate > toDate) {
		toastr.error("From date should not be greater than To date");
		return false;

	}


	var validation = true;
	if (fromDateFilter == null || fromDateFilter == "") {
		toastr.error("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		toastr.error("Please Provide To Date");
		validation = false;
	}
	if (validation) {

		agGrid.simpleHttpRequest({
			url: "view-account-journal-voucher-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			console.log(JSON.stringify(data))
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewJournalVoucher;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#totalAmountFooter").val("0.00");
				$("#purchaseRegisterExcel").prop("disabled", true);
				$("#journalVoucherPdf").prop("disabled", true);
				$("#toDateCalendar3").removeClass("pointeerEventsProperty");
				$("#journalVoucher").text('');
				$(".journalVchrHeaderInfo").addClass("d-none");

				paymentSectionComntainer();
			} else {
				$(".journalVchrHeaderInfo").removeClass("d-none");
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				//gridOptions.api.setRowData(allData);
				var rowData = [];
				gridOptions.api.setRowData(rowData);
				gridOptions.api.setRowData(allData);
				if (voucherId) {
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
				}
				var totalSum = allData.reduce(function(acc, curr) {
					return acc + parseFloat(curr.totalAmount);
				}, 0);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
				$("#purchaseRegisterExcel").prop("disabled", false);
				$("#journalVoucherPdf").prop("disabled", false);
			}
		});

	}
}

function downloadExcelFromGrid() {
	var selectedHeaders = ['Journal Voucher Id', 'Debit', 'Credit', 'Description', 'Amount', 'Date'];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Journal_Voucher_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

	var totalAmount = 0;

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
			if (field === 'totalAmount') {
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});

		rowData.push(data);

		var amount = parseFloat(node.data[fieldMap['Amount']]) || 0;
		totalAmount += amount;
	});

	totalAmount = amountFormatter(totalAmount.toFixed(2));

	var totalRow = {
		'Journal Voucher Id': "Total",
		'Debit': "",
		'Credit': "",
		'Description': "",
		'Amount': totalAmount,
		'Date': ""
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Journal Voucher');
	XLSX.writeFile(wb, fileName);
}

function downloadjournalVoucherPdf() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	window.open("/account/view-account-journal-voucher-pdf?fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}

function indianCurreny(value) {
	if (value !== null && value !== undefined) {
		value = value.replace(/[^\d.,]/g, '');

		var parts = value.toString().split('.');
		var integerPart = parts[0].replace(/,/g, '');
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		if (decimalPart.length > 3) {
			decimalPart = decimalPart.substring(0, 3);
		}

		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		console.log("decimalPart->", decimalPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}

function formatAmount(element) {
	let value = element.value;
	if (value) {
		let formattedValue = indianCurreny(value);
		element.value = formattedValue;
	}
}

function closeModelAddTaxDeduction() {

	$("#myModalTaxDeduction").modal("hide");
}

function getDueDateWarningRenderer(transactionDate) {
	const today = new Date();
	const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
	const dueDate = new Date(today.getFullYear(), today.getMonth(), 7);
	if (today >= startDate && today <= dueDate) {
		const daysRemaining = Math.ceil((dueDate - today) / (1000 * 60 * 60 * 24));
		return `
		            <span class="countdown-text">${daysRemaining} days remaining</span>
		            <span class="countdown-text due-animation">DUE</span>
		        `;
	}

	return '';
}

function TDSAmountConfirmBtn() {
	var selectionRows = TDSOptions2.api.getSelectedRows();
	var ids = []
	selectionRows.forEach(rows => {
		ids.push('"' + rows.voucherId + '"');

	})
	console.log("idss-->", ids);
	var selectionIds = ids.join(",");
	console.log("selectionIds-->", selectionIds);
	$("#selectionIds").val(selectionIds);
	$("#debitAmount_0").val($("#totalTDSAmount").val());
	changeToDecimal($("#debitAmount_0"));
	getFromSubtotal();
	closeModelAddTaxDeduction();

}

//Get All The Invoice lISt

/* function getInvoiceDataList(vendorId){
		
		$('.loader').show();
		$("#transAmount").val('');
		$.ajax({
			type: "GET",
			url: "receipt-voucher-customerOrderList?id=" + vendorId,
			async: true,
			success: function (response) {
				var jsondata = response.body;
				console.log("jsondata",jsondata);
				var gridDiv = document.querySelector('#multipaymentgrid');
				if (!gridOptions3.api) {
						new agGrid.Grid(gridDiv, gridOptions3);
				}
				 gridOptions3.api.setRowData([]);
				 var updateData = response.body;
				 console.log("updateData",updateData);
				gridOptions3.api.setRowData(updateData);
				var dataLength = jsondata.length;
				$("#totalInvoice2").find('span').html(dataLength);
				$('.loader').hide();
				$("#modAdjustment").val("againstRef");
				$("#invoiceListPopup").modal('show');
			}
		});
		
	} */

function getInvoiceDataList(vendorId) {
	$("#modAdjustment").val("newRef");
	//$("#invoiceListPopup").modal('show');
	checkModeOfPay();
	$("#transName2").val('');
	$("#transAmount2").val('');
	$("#payemntSectionDiv").hide();
	$("#childSectionDiv").hide();
	$("#methodOfAdjustmentDivInfo").show();
	$("#modInfoDiv").show();
	$("#referenceDataNotFound").hide();

}

/* function getInvoiceDataList(vendorId){
	
	$('.loader').show();
	$("#transAmount").val('');
	$.ajax({
		type: "GET",
		url: "payment-voucher-get-invoiceList?id=" + vendorId,
		async: true,
		success: function (response) {
			var jsondata = JSON.parse(response.body);
			var alldata = jsondata.InvoiceDetails;
			var newData = [];
			 $.each(alldata, function(index, obj) {
					if (obj.payStatus !="Fully Paid"){
					newData.push(obj);
					}
				});
			gridOptions3.api.setRowData(newData);
			var dataLength = newData.length;
			$("#totalInvoice2").find('span').html(dataLength);
			$('.loader').hide();
			$("#modAdjustment").val("newRef");
			$("#invoiceListPopup").modal('show');
		}
	})
} */

function cancelModal() {
	$('#invoiceListPopup').modal('hide');
}


function cancelInvoiceInfoModal() {
	$("#payemntSectionDiv").show();
	$("#childSectionDiv").show();
	$("#methodOfAdjustmentDivInfo").hide();
}


function checkModeOfPay() {
	var modeVal = $("#modAdjustment2").val();

	console.log("data-->", modeVal);


	if (modeVal == "advance" || modeVal == "newRef") {
		$("#parentTransNameDiv").show();
		$("#transNameDiv").show();
		$("#transAmountDiv").show();
		$(".totalPaymentAmountDiv").hide();
		$("#totalInvoice2").hide();
	}
	else {
		if (modeVal == "onAcc") {
			$("#parentTransNameDiv").hide();
			$("#transNameDiv").hide();
			$("#transAmountDiv").show();
			$(".totalPaymentAmountDiv").hide();
			$("#totalInvoice2").hide();

		}
	}
}



var selectedInvoiceid = [];
var advanceReceiveDetails = [];
function SaveFinalAmount() {

	var vendorid = $("#ledgerNameModalId").val();
	var sellerLdgerid = $("#debitLegderId").val();
	$("#transName2").val('');
	//$("#modAdjustment2").val('');

	//$("#transAmount2").val('');
	var divId = $("#amountDivId").val();

	console.log("divid-->", divId);

	/*var advAmount = $("#transAmount2").val();

	var parts = advAmount.toString().split('.');
	
	console.log("parts is-->" , parts);
	
	var integerPart = parts[0];
	var decimalPart = parts.length > 1 ? '.' + parts[1] : '.' + '00';
	var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
	advAmount = integerPart + decimalPart;*/

	var advAmount = $("#transAmount2").val();

	// Remove commas from the input value
	/*	advAmount = advAmount.replace(/,/g, '');
	
		// Now safely split into integer and decimal parts
		var parts = advAmount.toString().split('.');
	
		console.log("parts is -->", parts);
	
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '.00';
	
		// Format the integer part with commas
		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
	
		// Recombine formatted integer with decimal part
		advAmount = formattedIntegerPart + decimalPart;*/

	console.log("Formatted advAmount -->", advAmount);


	var indexNumber = divId.charAt(divId.length - 1);

	var receiptLedgerId = $("#creditAccountSubGroupId_" + indexNumber).val();
	var receiptMsg = $("#transName2").val();
	var rcvType = $("#modAdjustment2").val();

	var receiptAmount = $("#transAmount2").val();
	var modOfAdjust = $("#modAdjustment2").val();


	console.log("amount is-->", advAmount);

	if ($("#modAdjustment2").val() == "advance" || $("#modAdjustment2").val() == "newRef" || $("#onAcc").val() == "advance") {
		$("#" + "creditAmount_" + indexNumber).val(advAmount);
		advanceReceiveDetails.push({
			rcvLedgerId: receiptLedgerId,
			receiveMsg: receiptMsg,
			receiptAmount: receiptAmount.replace(/,/g, ''),
			methodOfAdjustment: modOfAdjust
		});
		console.log("advanceReceiveDetails", advanceReceiveDetails);
	}
	getToSubtotal();
	cancelModal();

	cancelInvoiceInfoModal();
}




function getInvoiceInfo(iconElement) {
	$(".loader").show();
	$("#methodOfAdjustmentDivInfo").show();
	var parentTd = iconElement.closest('td');
	var hiddenInput = parentTd.querySelector('.creditAccountGroupIdCls');
	var accountGroupId = hiddenInput.value;

	var legderNameTag = parentTd.querySelector('.creditAccountSubGroupCls');
	var ledgerName = legderNameTag.value;
	$("#ledgerNameInfo").text(ledgerName);
	$("#payemntSectionDiv").hide();
	$("#childSectionDiv").hide();



	var voucherid = $("#voucherNumber").text();

	$.ajax({
		type: "GET",
		url: "view-account-journal-voucher-getNewRefData?ledgerid=" + accountGroupId + "&voucherid=" + voucherid,
		async: false,
		success: function(response) {
			var jsondata = JSON.parse(response.body);
			var alldata = jsondata.getInvoiceListInfo;

			console.log("All data-->", alldata);

			console.log("alldata-->", alldata)
			if (alldata != null) {
				$("#modInfoDiv").show();
				$("#referenceDataNotFound").hide();
				$("#transName2").val(alldata[0].RefMsg);
				$("#transAmount2").val(amountFormatter(parseFloat(alldata[0].RefAmount).toFixed(2)));
				$("#modAdjustment2").val(alldata[0].method);

			}
			else {
				$("#modInfoDiv").hide();
				$("#referenceDataNotFound").show();
			}

			$(".loader").hide();
		}

	});

	$(".loader").hide();


}


function disableTrueAll() {
	$(".debitAmountCls").attr("disabled", true);
	$(".debitAccountSubGroupCls").attr("disabled", true);
	$(".creditAccountSubGroupCls").attr("disabled", true);
	$(".creditAmountCls").attr("disabled", true);
	$("#costCenter").attr("disabled", true);
	$("#description").attr("disabled", true);
	$(".tr_clone_add").attr("disabled", true);
}


function disableFalse() {
	$(".debitAmountCls").attr("disabled", false);
	$(".debitAccountSubGroupCls").attr("disabled", false);
	$(".creditAccountSubGroupCls").attr("disabled", false);
	$(".creditAmountCls").attr("disabled", false);
	$("#costCenter").attr("disabled", false);
	$("#description").attr("disabled", false);
	$(".tr_clone_add").attr("disabled", false);
}

function editItems() {
	disableFalse();
	$("#journal-save-btn").removeClass("d-none");
	CKEDITOR.instances['description'].setReadOnly(false);
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	$("#add-btn").addClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	$("#delete-journal-btn").addClass("d-none");
	$("#cancel-journal-btn").removeClass("d-none");
}

function journalCancelBtn() {
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	disableTrueAll();
	$("#toDateCalendar3").addClass("pointeerEventsProperty");
	if (firstRowNode) {
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
		$("#cancel-journal-btn").addClass("d-none");
		$("#journal-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#delete-journal-btn").removeClass("d-none");
		$(".journalVchrHeaderInfo").removeClass("d-none");

	}
	else {
		$(".journalVchrHeaderInfo").addClass("d-none");
		$("#cancel-journal-btn").addClass("d-none");
		$("#journal-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");

	}
}


function isDuplicateLedger(newLedgerName, newLedgerId, type) {


	if (type == "debitType") {
		let isDuplicate = false;

		$('.debitAccountSubGroupCls').each(function() {
			let ledgerName = $(this).val().trim();
			let idFieldId = $(this).attr('id').replace("debitAccountSubGroup_", "debitAccountGroupId_");
			let ledgerId = $("#" + idFieldId).val();

			if (ledgerName === newLedgerName && ledgerId === newLedgerId) {
				isDuplicate = true;
				return false; // break loop
			}
		});

		return isDuplicate;
	}
	else if (type == "credit") {

		let isDuplicate = false;

		$('.creditAccountSubGroupCls').each(function() {
			let ledgerName = $(this).val().trim();
			let idFieldId = $(this).attr('id').replace("creditAccountSubGroup_", "creditAccountSubGroupId_");
			let ledgerId = $("#" + idFieldId).val();

			if (ledgerName === newLedgerName && ledgerId === newLedgerId) {
				isDuplicate = true;
				return false; // break loop
			}
		});

		return isDuplicate;

	}

}


function validateSameLedgerInDebitCredit() {

	console.log("function call -->")
	let duplicateFound = false;

	$(".debitAccountSubGroupCls").each(function(i) {
		let debitLedgerName = $(this).val().trim();
		let debitLedgerId = $("#debitAccountGroupId_" + i).val()?.trim() || "";

		$(".creditAccountSubGroupCls").each(function(j) {
			let creditLedgerName = $(this).val().trim();
			let creditLedgerId = $("#creditAccountSubGroupId_" + j).val()?.trim() || "";

			//console.log("Checking:", debitLedgerName, "2--", creditLedgerName, "3--", debitLedgerId, "4--", creditLedgerId);

			if (
				debitLedgerName !== "" && creditLedgerName !== "" &&
				debitLedgerName === creditLedgerName &&
				debitLedgerId === creditLedgerId
			) {
				toastr.error("Same ledger is used in both debit and credit.\nLedger: " + debitLedgerName);
				duplicateFound = true;
				return false; // Exit inner loop
			}
		});

		if (duplicateFound) return false; // Exit outer loop
	});

	return !duplicateFound; // return true if no duplicate found, false if duplicate found
}




function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChanged();
	}
}


function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	getMostClosestRow();
	updateTotalTaskCount();


	// update the footer total amount

	const visibleRows = [];
	gridOptions.api.forEachNodeAfterFilter((node) => {
		visibleRows.push(node.data);
	});

	const totalSum = visibleRows.reduce((sum, item) => sum + Number(item.totalAmount), 0);
	console.log("Total Amount Sum:", totalSum);
	$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));

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

