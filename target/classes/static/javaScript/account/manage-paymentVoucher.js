$(function() {

	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var taxGridDiv = document.querySelector('#myGridTaxDeduction');
	new agGrid.Grid(taxGridDiv, TDSOptions2);

	$('.form-section').hide();
	$('input[name="paymentMethod"]').on('change', function() {
		$('.form-section').hide();
		$('#section-' + $(this).val()).fadeIn();
	});



	$("#costCenter").select2({
		placeholder: "Select Cost Center",
		allowClear: true
	});

	$('.loader').show();
	$("#invoiceListPopup").modal('hide');
	$("#transNameDiv").hide();
	$("#transAmountDiv").hide();
	$("#methodBoxDiv").hide();
	$("#invoiceInfoModal").modal('hide');
	$("#totalPaymentAmount").val("0.00");
	$("#totalInvoiceListNo").hide();
	$("#invoiceInfoGrid").hide();
	$("#modAdjustment").val('');
	$('input[name=parentChildRadio]').prop('checked', false);
	$("#mod_content").hide();
	$("#referenceDataNotFound").hide();
	$("#taxDeduction-content").hide();

	$("input[name=parentChildRadio]").on('change', function() {

		if ($(this).val() === "parent") {
			openParent();
		} else if ($(this).val() === "child") {
			openChild();
			parentList();
			$("#childAddDiv").html("");
			console.log("Child Click");
		}
	});

	$('#myModalParent').modal({
		backdrop: 'static',
		keyboard: false
	});



	/* Intialize The CKEditor For Narration Start */

	CKEDITOR.replace('description', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	/* Intialize The CKEditor For Narration End */

	/* agGrid.simpleHttpRequest({
		url: "view-account-payment-voucher"
	}).then(function (data) {
		$('.loader').hide();
		//console.log(JSON.stringify(data))
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewPaymentVoucher;
		if (allData == "" || allData == "null" || allData == null) {
			$('#totalReq').find('span').html('0');
			gridOptions.api.setRowData();
		} else {
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
		}
	}); */

	/* current date filter data */
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();
	$("#voucherDate").val(fromDate);
	$("#voucherDateMethod").val(todate);
	$("#voucherDateReceipt").val(todate);


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

	setTimeout(function() {
		viewFilteredData()
	}, 100);


	$("#toDateCalendar3").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#voucherDate').val($(this).val());
	})

	$('#voucherDate').blur(function() {
		$("#toDateCalendar3").val($(this).val());
	})

	$("#ChequeCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#ChequeDate').val($(this).val());
	})

	$('#ChequeDate').blur(function() {
		$("#ChequeCalendar").val($(this).val());
	})


	$("#UPITransferCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#UPITrnsDate').val($(this).val());
	})

	$('#UPITrnsDate').blur(function() {
		$("#UPITransferCalendar").val($(this).val());
	})


	$("#onlineTransferCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#onlineTrnsDate').val($(this).val());
	})

	$('#onlineTrnsDate').blur(function() {
		$("#onlineTransferCalendar").val($(this).val());
	})




	$("#toDateCalendarMethod").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#voucherDateMethod').val($(this).val());
	})
	$('#voucherDateMethod').blur(function() {
		$("#toDateCalendarMethod").val($(this).val());
	})

	$("#toDateCalendarReceipt").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#voucherDateReceipt').val($(this).val());
	})

	$('#voucherDateReceipt').blur(function() {
		$("#toDateCalendarReceipt").val($(this).val());
	})

	$("#fromDate").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
	});

	$("#toDate").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
	});


});

function getVoucherNumber() {
	$.ajax({
		type: "GET",
		url: "payment-voucher-vouchernumber",
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
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);

	var length = gridOptions.api.getDisplayedRowCount();
	$("#totalReq").find('span').html(length);

}*/

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
		},
		{
			headerName: 'Payment Voucher Id',
			field: "journalVoucher",
			pinned: 'left',
			hide: true,
			cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.journalVoucher + ' <i class="fa fa-edit"></i></a>';
			}
		},
		{
			headerName: "Voucher Date",
			field: "createdOn",
			pinned: 'left',
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "DATE",
			field: "TRANSACTION_DATE",
			pinned: 'left',
			maxWidth: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			},
			/*cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.TRANSACTION_DATE + ' <i class="fa fa-edit"></i></a>';
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
			width: 150,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "Invoice Id",
			field: "invoiceId",
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "Payment Id",
			field: "paymentId",
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "Cost Center Name",
			field: "costCenter",
			hide: true,
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, /* {
					headerName: "Vendor Name",
					field: "vendorName",
					cellStyle: {
						textAlign: 'left'
					}
				}, */

		{
			headerName: "Debit",
			field: "debitAccountName",
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: "Credit",
			field: "creditAccountName",
			cellStyle: {
				textAlign: 'left',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {
			headerName: "Narration",
			field: "description",
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: "Amount",
			field: "totalAmount",
			headerClass: 'amountAlign',
			valueFormatter: params => amountFormatter(params.data.totalAmount.toFixed(2)),
			cellStyle: {
				textAlign: 'right',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
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
			headerName: 'PDF Download',
			width: 100,
			cellRenderer: function(params) {
				const id = params.data.journalVoucher;

				if (!id || id.trim() === "") {
					return '<div style="color:#ff8242">Not Available</div>';
				}

				return '<a id="id" onclick=viewQr("' + id + '","' + id + '") href="/template/idcard/' + id + '" target="_blank">' +
					'<div style="color: var(--mainColor);"><i class="bi bi-filetype-pdf pdf_icon"></i> Download</i></div>' +
					'</a>';
			},
			cellStyle: {
				textAlign: 'center'
			},
		}
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
	onSelectionChanged: rowSelect,
	pagination: true,
	paginationPageSize: 15,
	getRowNodeId: function(data) {
		return data.journalVoucher;
	}

};



function viewQr(qr, id) {
	console.log("qr-->", qr);
	window.open("payment-voucher-details-pdf?id=" + qr, '_blank');
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

var deleteId = "";
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '' + selectedRows[i].journalVoucher + ',';
		// deleteId = deleteId  + selectedRows[i].journalVoucherId + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	//console.log(deleteId)
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		var paymentVoucherid = selectedRows[0].journalVoucher;
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		editPage(paymentVoucherid);
		disableTrueAll();
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#toDateCalendar3").addClass("pointeerEventsProperty");
		$("#cancel-payment-btn").addClass("d-none");
		$("#payment-save-btn").addClass("d-none");
		$("#narration_parent_text_container").addClass("textareadisable");
		$(".payment_vchr_header_info").removeClass("d-none");
		$("#voucherDate").attr("disabled", true);

		let approveStatus = selectedRows[0].approveStatus;
		if (approveStatus == 'APPROVED') {
			$("#ticket-editBtn,#parent_apv_btn,#payment-save-btn").addClass("d-none");
		} else {
			$("#ticket-editBtn,#parent_apv_btn").removeClass("d-none");
		}
		
		let t_date = selectedRows[0].TRANSACTION_DATE;
		
		$("#recieptDate").text(formatDate(t_date));

	} else {
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
		paymentSectionComntainer();
		$("#ticket-editBtn,#parent_apv_btn").addClass("d-none");
		$("#voucherDate").attr("disabled", false);

	}
}

function formatDate(dateStr) {
  const [day, month, year] = dateStr.split('-');
  const monthNames = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];
  const monthName = monthNames[parseInt(month, 10) - 1];
  return `${day}-${monthName}-${year}`;
}


function paymentSectionComntainer() {
	$("#journalVoucher").text('');
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');
	$(".payment_vchr_header_info").addClass("d-none");
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
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
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
	$("#payment-save-btn").removeClass("d-none");
	$("#cancel-payment-btn").removeClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	CKEDITOR.instances['description'].setData('');
	CKEDITOR.instances['description'].setReadOnly(false);
	$("#costCenter").attr("disabled", false);
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	$("#narration_parent_text_container").removeClass("textareadisable");
	getVoucherNumber();

}

/* 		function to calculate the selectedRows TDS Amount */
function tdsRowSelect() {
	var selectedRows = TDSOptions2.api.getSelectedRows();
	var selectedTdsAmount = 0;
	selectedRows.forEach(rows => {
		selectedTdsAmount += rows.amount;
	});

	selectedTdsAmount = parseFloat(selectedTdsAmount.toFixed(2));
	$("#totalTDSAmount").val(amountFormatter(selectedTdsAmount.toFixed(2)));
}



// for new button
function newBtn() {
	//	alert('hello');

	$("#journalVoucher").text('');
	gridOptions.api.deselectAll();
	$("#demo").show();
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');;
	$("#voucherDate").val("");
	$("#description").val("");
	$("#save").show();

	$("#normalPayment").hide();
	$("#addpayment").hide();
	$('#costCenter').attr('disabled', false);
	$("#voucherDate").attr('disabled', false);
	$("#description").attr('readonly', false);
	//$("#purchaseRegisterExcel").hide();
	//$("#paymentVoucherPdf").hide();
	$("#payment_method_container").addClass("d-none");
	resetFields();

	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	$("#voucherDate").val(fromDate);

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" id="creditAccountSubGroup_0" placeholder="Search SubGroup" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" oninput="formatAmount(this);" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#creditTbodyData").append(abc);

	$("#debitTbodyData").empty();
	var abc = '<tr class="tr_clone" id="debit_0">'
		+ '<td class ="legerSearchDiv"><input type="text" id="debitAccountSubGroup_0" placeholder="Search SubGroup" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" autocomplete="off"> '
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" oninput="formatAmount(this);" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);
	$("#cancel-payment-btn").removeClass("d-none");
	$("#payment-save-btn").removeClass("d-none");
	$("#add-btn").addClass("d-none");
	paymentSectionComntainer();
	CKEDITOR.instances['description'].setData('');
	CKEDITOR.instances['description'].setReadOnly(false);
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	getVoucherNumber();
}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$('#totalAmountFooterDiv').show();
	$("#demo").hide();
	$("#fdateDiv").show();
	$("#tdateDiv").show();
	$("#fyearDiv").show();
	$("#filterDiv").show();
	$("#journalVoucher").text("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("");
	$('#accountNumber').val("");
	$('#status').val("");
	$("#addpayment").show();
	$("#normalPayment").show();
	$("#addpayment").show();
	$("#filter").show();
	$("#purchaseRegisterExcel").show();
	$("#paymentVoucherPdf").show();

	CKEDITOR.instances.description.setData("");

	/* 	var today = new Date();
		var dateString  = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
		var fromDate = dateString .toString();
		var todate = dateString .toString();
			
		$("#toDate").val(todate);
		$("#fromDate").val(fromDate); */
	viewFilteredData()
}



// Edit & stage change 
function editPage(id) {

	var editId = id.split(",");

	var journalVoucher = editId[0];

	var modal = editId[1];
	//	alert(id);


	$("#demo").show();
	$("#addpayment").hide();
	$("#normalPayment").hide();
	$("#save").show();
	//$("#purchaseRegisterExcel").hide();
	//$("#paymentVoucherPdf").hide();

	//	alert('journalVoucherId------'+journalVoucher);

	$.ajax({
		type: "GET",
		url: "view-account-payment-voucher-edit?id=" + journalVoucher,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				//console.log("response for edit------"+JSON.stringify(response));
				$("#debitTbodyData").empty();
				$("#creditTbodyData").empty();
				$('#costCenter').val(response.body[0].costCenter).trigger('change');
				//$('#description').val(atob(response.body[0].description));
				$('#voucherNumber').text(response.body[0].journalVoucher);
				$('#journalVoucher').text(response.body[0].journalVoucher);
				$('#voucherDate').val(response.body[0].voucherDate);

				CKEDITOR.instances['description'].setData(response.body[0].description);

				//$('#costCenter').prop('disabled', true);
				//$('#voucherDate').prop('disabled', true);
				//$('#description').prop('readonly', true);


				console.log("response data-->", response.body[0].paymentMethodDetails);
				let paymentMethodDetails = response.body[0].paymentMethodDetails;
				console.log("paymentMethodDetails-->", paymentMethodDetails);
				setPaymentData(paymentMethodDetails);


				var toTotalAmount = 0;
				var fromTotalAmount = 0;
				for (var i = 0; i < response.body.length; i++) {
					if (response.body[i].transactionType == "Debit") {
						var amount = response.body[i].fromAmount;
						fromTotalAmount = fromTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"  id="debit_' + i + '">'
							+ '<td class ="legerSearchDiv"><input type="text" id="debitAccountSubGroup_' + i + '" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" value="' + response.body[i].subGroupName + '"> <i class="bi bi-info-square-fill infoIcon" onclick="getInvoiceInfo(this);"></i>'
							+ '<div id="suggesstion-box1_' + i + '"></div>'
							+ '<input type="hidden" id="debitAccountGroupId_' + i + '" class="form-control debitAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'
							/* + '<p class="mb-0">Balance: ₹<span id="debitCurrentBalance_' + i + '" class="debitAccountGroupBalance">' + response.body[i].currentBalance + '</span>'
							+ '</p>' */
							+ '</td>'
							/* +'<td><input type="text" id="debitName_'+i+'" class="form-control debitNameCls" value="'+response.body[i].fromName+'">'
							+'<p class="mb-0" style="height:22px;"></p>'
							+'</td>' */
							+ '<td>'
							+ '<input type="text" id="debitAmount_' + i + '" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ ''
							+ '</td>'
							+ '<td>'
							+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
							+ '<span class="ti-plus"></span></button>&nbsp;</td>'
							+ '</tr>';
						$("#debitTbodyData").append(abc);
					}
					if (response.body[i].transactionType == "Credit") {
						var amount = response.body[i].fromAmount;
						toTotalAmount = toTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"   id="credit_' + i + '">'
							+ '<td><input type="text" id="creditAccountSubGroup_' + i + '" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
							+ '<div id="suggesstion-box2_' + i + '"></div>'
							+ '<input type="hidden" id="creditAccountGroupId_' + i + '"  class="form-control creditAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'
							/* + '<p class="mb-0">Balance: ₹<span id="debitCurrentBalance_' + i + '" class="debitAccountGroupBalance">' + response.body[i].currentBalance + '</span>'
							+ '</p>' */
							+ '</td>'
							/* +'<td><input type="text" id="creditName_'+i+'"	class="form-control creditNameCls" value="'+response.body[i].fromName+'"></td>' */
							+ '<td><input type="text" id="creditAmount_' + i + '" class="form-control creditAmountCls"  onblur="changeToDecimal(this),getToSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ ''
							+ '</td>'
							+ '<td><button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
							+ '<span class="ti-plus"></span>'
							+ '</button>&nbsp;</td>'
							+ '</tr>';

						$("#creditTbodyData").append(abc);
					}
					$('#fromTotalAmount').text(amountFormatter((parseFloat(fromTotalAmount)).toFixed(2)));
					$('#toTotalAmount').text(amountFormatter((parseFloat(toTotalAmount)).toFixed(2)));
				}

			}
		}
	})
}



function addReceiptInfo12() {
	var obj = {};
	obj.journalVoucher = $('#journalVoucher').text();
	obj.bankName = $('#bankName').val();
	obj.accountHolder = $('#accountHolder').val();
	obj.branchName = $('#branchName').val();
	obj.accountType = $('#accountType').val();
	obj.accountNumber = $('#accountNumber').val();
	obj.status = $('#status').val();
	//console.log("object on add account-----------" + JSON.stringify(obj));
	var validation = true;
	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-account-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {
					//console.log(response);

					$("#add").show();
					$("#copy").show();
					$("#delete").show();
					$("#totalReq").show();
					$("#myGrid").show();
					$("#searchRowDiv").show();
					$("#demo").hide();

					agGrid.simpleHttpRequest({
						url: "view-account-payment-voucher"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});

				}
			},
			error: function(data) {
			}
		})
	}

}
let globalContraVoucherId = '';
function addPaymentInfo() {
	var dataset = [];
	item = {};


	if ($("#toTotalAmount").text() == "") {
		$("#toTotalAmount").text($("#fromTotalAmount").text());
	}

	let jsonOutput = {};
	const paymentData = paymentMethodSelection();
	if (paymentData) {
		jsonOutput = paymentData;
	}
	else {
		jsonOutput = "";
	}

	$("#description").val(CKEDITOR.instances.description.getData());
	globalContraVoucherId = $("#journalVoucher").text();
	$("#debitTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#fromTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#description").val()
		item['voucherDate'] = $("#voucherDate").val();
		item['fromAccountSubGroup'] = $(this).find(".debitAccountGroupIdCls").val();
		item['fromName'] = $(this).find(".debitNameCls").val();
		item['fromAmount'] = $(this).find(".debitAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "TVTM003";
		item['listTdsId'] = $("#selectionIds").val();
		item['taxType'] = $("#taxType").val();
		dataset.push(item);
	});//table tbody tr loop ends
	$("#creditTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#toTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#description").val()
		item['voucherDate'] = $("#voucherDate").val();
		item['toAccountSubGroup'] = $(this).find(".creditAccountGroupIdCls").val();
		item['toName'] = $(this).find(".creditNameCls").val();
		item['toAmount'] = $(this).find(".creditAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "PAYMENT";
		item['listTdsId'] = $("#selectionIds").val();
		item['taxType'] = $("#taxType").val();
		item['paymentType'] = $("#modAdjustment").val();
		item['paymentDesc'] = $("#transName").val();
		item['advOrNewRfAmount'] = $("#transAmount").val().replace(/,/g, '');
		item['invoiceObj'] = JSON.stringify(selectedInvoiceid);
		item['advReceiveObj'] = JSON.stringify(advanceReceiveDetails);
		item['advanceRemaingDetails'] = JSON.stringify(advanceListArray);
		item['paymentMethodDetails'] = jsonOutput;
		dataset.push(item);
	});//table tbody tr loop ends

	console.log(dataset);

	var dscValue = CKEDITOR.instances.description.getData();

	console.log("dsc value-->", dscValue);

	console.log("dataset-->", dataset);

	//return false;
	//submitJournal(dataset);

	//Blank Validations

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
		/*if ($("#costCenter").val() === "") {
			toastr.error("Please Select Cost Center Name");
			return false; // Stop the program
		}*/

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
		url: "payment-voucher-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "view-account-payment-voucher"
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
					url: "view-account-payment-voucher"
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
					url: "view-account-payment-voucher"
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
					url: "view-account-payment-voucher"
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


function debitSubGroup(id) {
	var l = id.split("_");
	var counter = l[1];
	var search = $("#" + id).val();
	$.ajax({
		type: "POST",
		url: "payment-voucher-getAccountDebitGroup",
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
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\',\'' + response.body[i].cvDescription + '\',\'' + response.body[i].contraVoucherType + '\',\'' + response.body[i].createdBy + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
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
					content += '<li  class ="autocompletedata cp" onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box1_" + counter).show();
					$("#suggesstion-box1_" + counter).html(content);
				}
			}
		},
		error: function(data) {
			//console.log(data);
		}
	})

	if (search.length == 0) {
		$("#suggesstion-box1_" + counter).hide();
	}

	$("#amountDivId").val(id);
}


function TdsVoucherList(ledgerId, taxType) {
	//$("#taxType").val(taxType);
	var totalSelectedAmount = 0;

	//console.log("TaxType-->",taxType)
	$.ajax({
		type: "GET",
		url: "payment-voucher-getTDSVoucherList?id=" + ledgerId,
		success: function(response) {
			if (response.code == "success") {
				var responseData = JSON.parse(response.body);
				var tdsData = responseData.getTdsVoucherList;
				TDSOptions2.api.setRowData(tdsData);
				TDSOptions2.api.forEachNode(function(node) {
					node.setSelected(true);
				});
				TDSOptions2.api.forEachNode(rows => {
					console.log("rows-->", rows);
					totalSelectedAmount += rows.data.amount;
				})
				//$("#myModalTaxDeduction").modal('show');
				$("#childSection").hide();
				$("#parentPaymentSection").hide();
				$("#taxDeduction-content").show();
				$("#totalTDSAmount").val(amountFormatter(totalSelectedAmount));
				console.log("hello");
			}
		}

	});

}

function autocompleteValue1(sGroupId, sGroupName, id, amount, taxType, multiPayType, vendorId) {
	var l = id.split("_");
	var counter = l[1];
	$("#parentTransNameDiv").hide();
	$("#transAmountDiv").hide();

	let fullLedgerName = sGroupName + '( ' + sGroupId + ' )';

	if (isDuplicateLedger(fullLedgerName, sGroupId, "debitType")) {
		toastr.error("Ledger already added! Please select a different ledger.");

		return;
	}

	if (taxType != "False") {
		TdsVoucherList(sGroupId, taxType);

	}

	if (multiPayType != "False" && (vendorId != "" || vendorId != null)) {

		$("#ledgerNameModal").text(sGroupName);
		$("#ledgerNameModalId").val(vendorId);
		$("#debitLegderId").val(sGroupId);
		getInvoiceDataList(vendorId);
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


function getInvoiceDataList(vendorId) {
	$("#invoiceListgrid").show();
	$("#invoiceInfoGrid").hide();

	$("#referenceDataNotFound").hide();
	$(".parent_content_element").show();

	$('.loader').show();
	$("#transAmount").val('');
	$.ajax({
		type: "GET",
		url: "payment-voucher-get-invoiceList?id=" + vendorId,
		async: true,
		success: function(response) {
			var jsondata = JSON.parse(response.body);
			var alldata = jsondata.InvoiceDetails;
			var advanceData = jsondata.AdvancePayments;

			console.log("in invoide list ajax")
			if (alldata == null) {
				console.log("Data is null")
				$("#childSection").hide();
				$("#parentPaymentSection").hide();
				$("#chidSaveBtn").show();
				$("#referenceDataNotFound").hide();
				$(".parent_content_element").show();
			}
			else {
				console.log("data is not null");
			}
			var newData = [];
			$.each(alldata, function(index, obj) {
				if (obj.payStatus != "Fully Paid") {
				console.log('Data List == ',obj)
					newData.push(obj);
				}
			});
			gridOptions3.api.setRowData(newData);
			if (advanceData) {
				gridOptions5.api.setRowData(advanceData);
				//$("#advanceInvoiceListContainer").find('span').innerText(advanceData.length)
				$("#advance_listCount").html(advanceData.length);


			}
			else {
				gridOptions5.api.setRowData([]);
				$("#advance_listCount").html('0');

			}

			var dataLength = newData.length;
			$("#totalInvoice").find('span').html(dataLength);
			$("#totalInvoiceListNo").show();
			$("#totalInvoiceListNo").find('span').html(dataLength);
			$('.loader').hide();
			$("#modAdjustment").val("againstRef");
			//$("#invoiceListPopup").modal('show');
			$("#childSection").hide();
			$("#parentPaymentSection").hide();
			$("#mod_content").show();
			$("#chidSaveBtn").show();
			$(".totalPaymentAmountDiv").show();
		}
	})
}


function getEditPopUpView(id) {
	$("#invoiceListgrid").show();
	$("#invoiceListPopup").modal('show');
}

function checkModeOfPay() {
	var modeVal = $("#modAdjustment").val();

	if (modeVal === "againstRef") {

		$("#transNameDiv").hide();
		$("#transAmountDiv").hide();
		$("#multipaymentgrid").show();
		$(".totalPaymentAmountDiv").show();
		$("#totalInvoiceListNo").show();

		console.log("in agan");

		$("#advanceInvoiceListContainer").removeClass("d-none");
		$(".remainingAmountDiv").removeClass("d-none");

	}
	else {
		if (modeVal == "advance" || modeVal == "newRef") {
			$("#parentTransNameDiv").show();
			$("#transNameDiv").show();
			$("#transAmountDiv").show();
			$("#multipaymentgrid").hide();
			$(".totalPaymentAmountDiv").hide();
			$("#totalInvoiceListNo").hide();

			$("#advanceInvoiceListContainer").addClass("d-none");
			$(".remainingAmountDiv").addClass("d-none");

		}
		else {
			if (modeVal == "onAcc") {
				$("#parentTransNameDiv").hide();
				$("#transNameDiv").hide();
				$("#transAmountDiv").show();
				$(".totalPaymentAmountDiv").hide();
				$("#multipaymentgrid").hide();
				$("#totalInvoiceListNo").hide();

				$("#advanceInvoiceListContainer").addClass("d-none");
				$(".remainingAmountDiv").addClass("d-none");

			}
		}
	}
}





function cancelModal() {
	$("#childSection").show();
	$("#parentPaymentSection").show();
	$("#mod_content").hide()
	$("#totalPaymentAmount").val('');
}


function cancelInvoiceInfoModal() {
	$('#invoiceInfoModal').modal('hide');
}

var selectedInvoiceid = [];
var advanceReceiveDetails = [];
var advanceListArray = [];
function SaveFinalAmount() {


	var selectedRows = gridOptions3.api.getSelectedRows();
	var vendorid = $("#ledgerNameModalId").val();
	var sellerLdgerid = $("#debitLegderId").val();
	var rcvType = $("#modAdjustment").val();

	selectedRows.forEach(rows => {
		selectedInvoiceid.push({
			invoiceId: rows.invoiceId,
			outstandingAmount: rows.outstandingAmount,
			vendorId: vendorid,
			legerid: sellerLdgerid,
			rcvType: rcvType

		});
	});

	var divId = $("#amountDivId").val();

	console.log("divId-->", divId);

	var advAmount = $("#transAmount").val();

	var parts = advAmount.toString().split('.');
	var integerPart = parts[0];
	var decimalPart = parts.length > 1 ? '.' + parts[1] : '.' + '00';
	var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
	advAmount = integerPart + decimalPart;


	console.log("selectedInvoiceid-->", selectedInvoiceid);
	//var advAmount = $("#transAmount").val();
	var indexNumber = divId.charAt(divId.length - 1);

	var receiptLedgerId = $("#debitAccountGroupId_" + indexNumber).val();
	var receiptMsg = $("#transName").val();


	var receiptAmount = $("#transAmount").val();
	console.log("receipt adv amount -->", receiptAmount.replace(/,/g, ''));
	if (parseInt(receiptAmount.replace(/,/g, '')) > 0) {

		advanceReceiveDetails.push({
			rcvLedgerId: receiptLedgerId,
			receiveMsg: receiptMsg,
			receiptAmount: receiptAmount.replace(/,/g, ''),
			rcvType: rcvType
		});
	}
	/*advanceReceiveDetails.push({
		rcvLedgerId: receiptLedgerId,
		receiveMsg: receiptMsg,
		receiptAmount: receiptAmount.replace(/,/g, ''),
		rcvType: rcvType
	});*/

	let totalAmountCondWise = 0;

	let selRows = gridOptions5.api.getSelectedRows();

	if (selRows && selRows.length > 0) {
		totalAmountCondWise = $("#remainInvoiceAmouunt").val();
	} else {
		totalAmountCondWise = $("#totalPaymentAmount").val();
	}

	if ($("#modAdjustment").val() == "againstRef") {
		$("#" + "debitAmount_" + indexNumber).val(totalAmountCondWise);

		//$("#" + "creditAmount_" + indexNumber).val($("#totalPaymentAmount").val());
		//getFromSubtotal();
		// $("#toTotalAmount").text($("#fromTotalAmount").text());
	}
	else {
		$("#" + "debitAmount_" + indexNumber).val(advAmount);
		//$("#" + "creditAmount_" + indexNumber).val(advAmount);
		//$("#toTotalAmount").text($("#fromTotalAmount").text());
	}
	getFromSubtotal();
	cancelModal();
	$("#adjustment").val("advance");
	$("#transNameDiv").hide();
	$("#transAmountDiv").hide();
	$("#multipaymentgrid").show();
	$(".totalPaymentAmountDiv").show();
	$("#transName").val('');





	let methodType = $("#modAdjustment").val();
	if (methodType == "advance") {
		selectedInvoiceid = [];
	}
	else if (methodType == "againstRef") {
		advanceReceiveDetails = [];
	}

	console.log("advanceReceiveDetails--->", advanceReceiveDetails);
	console.log("advanceListArray-->", advanceListArray);
	console.log("selectedInvoiceid-->", selectedInvoiceid);

}



function getInvoiceInfo(iconElement) {
	$(".loader").show();

	var parentTd = iconElement.closest('td');
	var hiddenInput = parentTd.querySelector('.debitAccountGroupIdCls');
	var accountGroupId = hiddenInput.value;

	var legderNameTag = parentTd.querySelector('.debitAccountSubGroupCls');
	var ledgerName = legderNameTag.value;
	var totalAmount = 0.00;

	$("#ledgerNameModal").text(ledgerName);
	$("#invoiceListgrid").hide();
	$("#mod_content").show();
	var voucherid = $("#voucherNumber").text();
	$("#payment_method_container").addClass("d-none");
	

	$.ajax({
		type: "GET",
		url: "payment-voucher-get-invoiceListInfo?ledgerid=" + accountGroupId + "&voucherid=" + voucherid,
		async: false,
		success: function(response) {
			var jsondata = JSON.parse(response.body);
			var alldata = jsondata.getInvoiceListInfo;

			console.log("alldata-->", alldata);

			if (alldata && alldata.length > 0) {
				//$("#invoiceListPopup").modal('show');
				$("#childSection").hide();
				$("#parentPaymentSection").hide();
				$("#referenceDataNotFound").hide();
				$(".parent_content_element").show();
				$("#chidSaveBtn").hide();
				if (alldata[0].PayType == "againstRef") {
					$("#invoiceListPopup").show();
					$("#transAmountDiv").hide();
					$("#parentTransNameDiv").hide();
					$("#modAdjustment").val("againstRef");
					$("#methodBoxDiv").hide();
					gridOptions4.api.setRowData(alldata);
					$("#invoiceInfoGrid").show();
					var dataLength = alldata.length;
					$("#totalInvoiceListNo").show();
					$("#totalInvoiceListNo").find('span').html(dataLength);

					totalAmount = alldata.reduce(function(acc, curr) {
						return acc + parseFloat(curr.PaidAmount);
					}, 0);

					$("#totalPaymentAmount").val(amountFormatter(totalAmount.toFixed(2)));
					$(".totalPaymentAmountDiv").show();
				} else {
					$("#methodBoxDiv").show();
					if (alldata[0].PayType == "advance") {
						$("#modAdjustment").val("advance");
						$("#invoiceInfoGrid").hide();
						$("#transAmountDiv").show();
						$("#transNameDiv").show();
						$(".totalPaymentAmountDiv").hide();
						$("#parentTransNameDiv").show();
						gridOptions4.api.setRowData();
						$("#totalInvoiceListNo").hide();
					} else if (alldata[0].PayType == "newRef") {
						$("#modAdjustment").val("newRef");
						$("#transAmountDiv").show();
						$("#transNameDiv").show();
						$("#parentTransNameDiv").show();
						$(".totalPaymentAmountDiv").hide();
						$("#invoiceInfoGrid").hide();
						gridOptions4.api.setRowData();
						$("#totalInvoiceListNo").hide();
					} else if (alldata[0].PayType == "onAcc") {
						$("#modAdjustment").val("onAcc");
						$("#parentTransNameDiv").hide();
						$("#transAmountDiv").show();
						$(".totalPaymentAmountDiv").hide();
						$("#invoiceInfoGrid").hide();
						gridOptions4.api.setRowData();
						$("#totalInvoiceListNo").hide();
					}
					$("#transName").val(alldata[0].PayMsg);
					$("#transAmount").val(amountFormatter(alldata[0].PaidAmount));
				}
			} else {
				$("#childSection").hide();
				$("#parentPaymentSection").hide();
				$("#chidSaveBtn").hide();
				$("#referenceDataNotFound").show();
				$(".parent_content_element").hide();

			}
			$(".loader").hide();
		},
		error: function() {
			//$("#invoiceListPopup").modal('hide');
			/*Swal.fire({
				title: 'Error!',
				text: 'Something went wrong while fetching data!',
				icon: 'error'
			});*/
			$(".loader").hide();
		}
	});



}
//function for auto filled of item Requisition
function creditSubGroup(id) {

	var l = id.split("_");
	var counter = l[1];
	var search = $("#" + id).val();

	$.ajax({
		type: "POST",
		url: "payment-voucher-getAccountCreditGroup",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			console.log(response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\',\'' + response.body[i].description + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);

				}
				else {
					console.log("else: " + response);
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					//content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);
				}
			}
		},
		error: function(data) {
			//console.log(data);
		}
	})

	if (search.length == 0) {
		$("#suggesstion-box2_" + counter).hide();
	}

	var fromDataAmount = $("#fromTotalAmount").text();

	console.log("fromamount -->", fromDataAmount);
	var indexNumber = id.charAt(id.length - 1);

	console.log("indexNumber -->", indexNumber);
	//$("#"+"creditAmount_" + indexNumber).text(fromDataAmount);
	//$("#toTotalAmount").text(fromDataAmount);

}

function autocompleteValue2(sGroupId, sGroupName, id, amount, bankid) {
	var l = id.split("_");
	var counter = l[1];

	let fullLedgerName = sGroupName + '( ' + sGroupId + ' )';

	if (isDuplicateLedger(fullLedgerName, sGroupId, "credit")) {
		toastr.error("Ledger already added! Please select a different ledger.");

		return;
	}

	if (sGroupId) {
		$("#creditAccountSubGroup_" + counter).val(sGroupName + '( ' + sGroupId + ' )');
		$("#creditAccountGroupId_" + counter).val(sGroupId);
		$("#creditCurrentBalance_" + counter).text(amount);
		$("#creditAccountSubGroup_" + counter).attr('data-procat', sGroupName);
		$("#suggesstion-box2_" + counter).hide();

		if (bankid == "1") {
			$("#payment_method_container").removeClass("d-none");
		}
		else {
			$("#payment_method_container").addClass("d-none");
		}


	} else {
		$("#creditAccountSubGroup_" + counter).val("");
		$("#creditAccountGroupId_" + counter).val("");
		$("#creditCurrentBalance_" + counter).text("");
		$("#creditAccountSubGroup_" + counter).attr('data-procat', "");
		$("#suggesstion-box2_" + counter).hide();
	}
}

/*function debitCheckEmpty() {
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

	if (debitAccountGroup && debitName && debitAmount) {

		debitAddMore();
	}
}*/
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
	var removeMore = '<button type="button" class="btn btn-warning rmv" name="Remove"><span class="ti-minus"></span></button>';


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
	var removeMore = '<button type="button" class="btn btn-warning rmv" name="Remove"><span class="ti-minus"></span></button>';


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
		textInput.eq(2).attr('id', "creditName_" + i);
		textInput.eq(3).attr('id', "creditAmount_" + i);
		divInput.eq(0).attr('id', "suggesstion-box2_" + i);
		textInput.eq(1).attr('id', "creditAccountGroupId_" + i);
		tr.eq(0).attr('id', "credit_" + i);

	})
}*/

var gridOptions2;
var gridOptions3;
var gridOptions4;
var gridOptions5;
//add More at edit time
$(document).ready(function() {
	$("#paymentPopup").hide();
	$("#paymentgrid").hide();
	$("#parentDiv").hide();
	$("#split").hide();
	$("#payBtn").hide();
	$("#remainingAmtDiv").hide();
	$(".bankSelectionMethodDiv").hide();

	$("#invPayment").hide();
	var lengthOfTableRow = $("#debitTbodyData").children('tr').length;
	$('.tbll').on('click', '.rmv', function() {
		$(this).closest('tr').remove();

		$("#debitMyTable tbody tr:last").find('td:last').html('');
		var add = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
		var remove = '<button type="button" class="btn btn-warning rmv" name="Remove"><span class="ti-minus"></span></button>';

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
		var remove = '<button type="button" class="btn btn-warning rmv" name="Remove"><span class="ti-minus"></span></button>';

		if ($("#creditMyTable").children('tr').length > 1) {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
			$("#creditMyTable tbody tr:last").find('td:last').append(remove);
		}
		else {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
		}
	});


	$('#ledgerGst').on('keydown', function(event) {
		var gstValue = $(this).val();
		if (gstValue.length === 12 && event.keyCode === 8) {
			event.preventDefault();
		}
	});


	/* purchase Invoice Ag-grid */
	var columnDefs2 = [
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

	gridOptions2 = {
		columnDefs: columnDefs2,
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

	var gridDiv2 = document.querySelector('#paymentgrid');
	new agGrid.Grid(gridDiv2, gridOptions2);

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

			headerName: 'Total',
			field: "beforeTdsAmount",
			width: 110,
			type: 'rightAligned',
			valueFormatter: params => amountFormatter(params.data.beforeTdsAmount.toFixed(2)),
			cellStyle: {
				color: 'black!important',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		}, {

			headerName: 'TDS Amount',
			field: "tdsAmount",
			width: 110,
			type: 'rightAligned',
			valueFormatter: params => amountFormatter(params.data.tdsAmount.toFixed(2)),
			cellStyle: {
				color: 'black!important',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
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

	var gridDiv3 = document.querySelector('#multipaymentgrid');
	new agGrid.Grid(gridDiv3, gridOptions3);


	var selectedInvoicelIst = [];
	function multiPayRowSelect() {
		let selectedRows = gridOptions3.api.getSelectedRows();
		selectedInvoicelIst = selectedRows;
		var selectedTdsAmount = 0;
		selectedRows.forEach(rows => {
			selectedTdsAmount += rows.outstandingAmount;
		});

		selectedTdsAmount = parseFloat(selectedTdsAmount.toFixed(2));
		$("#totalPaymentAmount").val(amountFormatter(selectedTdsAmount.toFixed(2)));

		if (selectedRows.length > 0) {
			$("#advanceInvoiceListContainer").removeClass("d-none");
			$(".remainingAmountDiv").removeClass("d-none");
		}
		else {
			$("#advanceInvoiceListContainer").addClass("d-none");
			$(".remainingAmountDiv").addClass("d-none");
		}
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



	var columnDefs5 = [
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
		},
		{
			headerName: "Reference  Id",
			field: "paymentId",
			width: 120,
			pinned: 'left'
		}, {
			headerName: 'Ledger Name',
			field: "ledgerName",
			hide: true,
			cellStyle: {
				color: 'black!important',
				fontFamily: 'Montserrat, sans-serif',
				fontSize: 'smaller'
			}
		},
		{
			headerName: 'Created Date',
			field: "createdDate",
		},
		{
			headerName: 'Type',
			field: "type",
		}, {

			headerName: 'Amount',
			field: "paidAmount",
			type: 'rightAligned',
			valueFormatter: params => amountFormatter(params.data.paidAmount)
		}, {

			headerName: 'Remaining Amount',
			field: "payableAmountFinal",
			type: 'rightAligned',
			valueFormatter: params => amountFormatter(params.data.payableAmountFinal)
		}];

	gridOptions5 = {
		columnDefs: columnDefs5,
		rowSelection: 'multiple',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 251,
			height: 10,
			flex: 1
		},
		onSelectionChanged: advanceValueSelect


	};

	var gridDiv5 = document.querySelector('#advanceGridList');
	new agGrid.Grid(gridDiv5, gridOptions5);


	//var advanceListArray = [];

	/*	function advanceValueSelect() {
			let selectedAdvanceListRows = gridOptions5.api.getSelectedRows();
			let totalPaymentAmount = $("#totalPaymentAmount").val().replace(/,/g, '');
			if(selectedAdvanceListRows.length>0){
				console.log("selectedAdvanceListRows-->" , selectedAdvanceListRows);
				console.log("totalPaymentAmount-->",totalPaymentAmount);
			}
			
	
		}*/



	function advanceValueSelect() {
		let selectedAdvanceListRows = gridOptions5.api.getSelectedRows();
		let totalPaymentAmount = parseFloat($("#totalPaymentAmount").val().replace(/,/g, '')) || 0;
		let remainingInvoiceAmount = totalPaymentAmount;

		advanceListArray = []; // Reset array on each click

		if (selectedAdvanceListRows.length > 0) {
			selectedAdvanceListRows.forEach(row => {
				let advanceAmount = parseFloat(row.payableAmountFinal) || 0;
				let usedAmount = 0;
				let remainingAdvance = 0;
				let status = "";

				if (remainingInvoiceAmount > 0) {
					if (advanceAmount <= remainingInvoiceAmount) {
						// Full advance used
						usedAmount = advanceAmount;
						remainingAdvance = 0;
						status = "Settled";
					} else {
						// Partial advance used
						usedAmount = remainingInvoiceAmount;
						remainingAdvance = advanceAmount - remainingInvoiceAmount;
						status = "Not Settled";
					}

					remainingInvoiceAmount -= usedAmount;

					advanceListArray.push({
						advanceId: row.paymentId,
						amount: usedAmount,
						remainingAmount: remainingAdvance,
						status: status
					});
				}
			});

			console.log("Final Advance Allocation:", advanceListArray);
			console.log("Remaining Invoice Amount:", remainingInvoiceAmount);
			$("#remainInvoiceAmouunt").val(amountFormatter(remainingInvoiceAmount.toFixed(2)));
		} else {
			toastr.error("Please select at least one advance entry.");
		}
	}



	//Purchase Ag-grid End

	/* Radio Button select */
	var $radios = $('input:radio[name=CashChequeOrOnline]');
	if ($radios.is(':checked') === false) {
		$radios.filter('[value=Cash]').prop('checked', true);
		$(".bankDiv").hide();
		$(".chequeDiv").hide();
		$(".upiDiv").hide();
		$(".bankSelectionDiv").hide();
		$("#bankSelectPayment").val("");
		$("#currentBalanceBank").val("");
		$("#updatedBalanceBank").val("");
	}

	$("input[name=CashChequeOrOnline]:radio").click(function() {

		if ($('input[name=CashChequeOrOnline]:checked').val() == "Cash") {
			$(".bankDiv").hide();
			$(".chequeDiv").hide();
			$(".upiDiv").hide();
			$(".bankSelectionDiv").hide();
			$("#bankSelectPayment").val("");
			$("#currentBalanceBank").val("");
			$("#updatedBalanceBank").val("");
			$("#chequeNo").val("");
			$("#chequeBankName").val("");
			$("#chequeBankBranch").val("");
			$("#chequeAccountNumber").val("");
			$("#payRemarks").val("");
			$("#bankSelect").val("");
			$("#transactionNumber").val("");
			$("#receiverBankBranch").val("");
			$("#receiverBankName").val("");
			$("#receiverIfscCode").val("");
			$("#receiverAccountNumber").val("");
			$("#onlineUpiID").val("");
			$("#receiverOnlineUpiID").val("");
			$("#receiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Cheque") {
			$(".bankDiv").hide();
			$(".chequeDiv").show();
			$(".upiDiv").hide();
			$(".bankSelectionDiv").show();
			$("#bankSelectPayment").val("");
			$("#currentBalanceBank").val("");
			$("#updatedBalanceBank").val("");
			$("#chequeNo").val("");
			$("#chequeBankName").val("");
			$("#chequeBankBranch").val("");
			$("#chequeAccountNumber").val("");
			$("#payRemarks").val("");
			$("#bankSelect").val("");
			$("#transactionNumber").val("");
			$("#receiverBankBranch").val("");
			$("#receiverBankName").val("");
			$("#receiverIfscCode").val("");
			$("#receiverAccountNumber").val("");
			$("#onlineUpiID").val("");
			$("#receiverOnlineUpiID").val("");
			$("#receiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Online") {
			$(".bankDiv").show();
			$(".chequeDiv").hide();
			$(".upiDiv").hide();
			$(".bankSelectionDiv").show();
			$("#bankSelectPayment").val("");
			$("#currentBalanceBank").val("");
			$("#updatedBalanceBank").val("");
			$("#chequeNo").val("");
			$("#chequeBankName").val("");
			$("#chequeBankBranch").val("");
			$("#chequeAccountNumber").val("");
			$("#payRemarks").val("");
			$("#bankSelect").val("");
			$("#transactionNumber").val("");
			$("#receiverBankBranch").val("");
			$("#receiverBankName").val("");
			$("#receiverIfscCode").val("");
			$("#receiverAccountNumber").val("");
			$("#onlineUpiID").val("");
			$("#receiverOnlineUpiID").val("");
			$("#receiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Upi") {
			$(".bankDiv").hide();
			$(".chequeDiv").hide();
			$(".upiDiv").show();
			$(".bankSelectionDiv").show();
			$("#bankSelectPayment").val("");
			$("#currentBalanceBank").val("");
			$("#updatedBalanceBank").val("");
			$("#chequeNo").val("");
			$("#chequeBankName").val("");
			$("#chequeBankBranch").val("");
			$("#chequeAccountNumber").val("");
			$("#payRemarks").val("");
			$("#bankSelect").val("");
			$("#transactionNumber").val("");
			$("#receiverBankBranch").val("");
			$("#receiverBankName").val("");
			$("#receiverIfscCode").val("");
			$("#receiverAccountNumber").val("");
			$("#onlineUpiID").val("");
			$("#receiverOnlineUpiID").val("");
			$("#receiverUpiTransactionID").val("");
		}
	});

	//radio button setting for invoice payment
	var $methodRadios = $('input:radio[name=CashChequeOrOnlineMethod]');
	if ($methodRadios.is(':checked') === false) {
		$methodRadios.filter('[value=Cash]').prop('checked', true);
		$(".methodBankDiv").hide();
		$(".methodChequeDiv").hide();
		$(".methodUpiDiv").hide();
		$(".bankSelectionMethodDiv").hide();
		$("#bankSelectPaymentMethod").val("");
		$("#currentBalanceBankMethod").val("");
		$("#updatedBalanceBankMethod").val("");
	}

	$("input[name=CashChequeOrOnlineMethod]:radio").click(function() {

		if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
			$(".methodBankDiv").hide();
			$(".methodChequeDiv").hide();
			$(".methodUpiDiv").hide();
			$(".bankSelectionMethodDiv").hide();
			$("#bankSelectPaymentMethod").val("");
			$("#currentBalanceBankMethod").val("");
			$("#updatedBalanceBankMethod").val("");
			$("#methodChequeNo").val("");
			$("#methodChequeBankName").val("");
			$("#methodChequeBankBranch").val("");
			$("#methodChequeAccountNumber").val("");
			$("#methodPayRemarks").val("");
			$("#methodBankSelect").val("");
			$("#methodTransactionNumber").val("");
			$("#methodReceiverBankBranch").val("");
			$("#methodReceiverBankName").val("");
			$("#methodReceiverIfscCode").val("");
			$("#methodReceiverAccountNumber").val("");
			$("#methodOnlineUpiID").val("");
			$("#methodReceiverOnlineUpiID").val("");
			$("#methodReceiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cheque") {
			$(".methodBankDiv").hide();
			$(".methodChequeDiv").show();
			$(".methodUpiDiv").hide();
			$(".bankSelectionMethodDiv").show();
			$("#bankSelectPaymentMethod").val("");
			$("#currentBalanceBankMethod").val("");
			$("#updatedBalanceBankMethod").val("");
			$("#methodChequeNo").val("");
			$("#methodChequeBankName").val("");
			$("#methodChequeBankBranch").val("");
			$("#methodChequeAccountNumber").val("");
			$("#methodPayRemarks").val("");
			$("#methodBankSelect").val("");
			$("#methodTransactionNumber").val("");
			$("#methodReceiverBankBranch").val("");
			$("#methodReceiverBankName").val("");
			$("#methodReceiverIfscCode").val("");
			$("#methodReceiverAccountNumber").val("");
			$("#methodOnlineUpiID").val("");
			$("#methodReceiverOnlineUpiID").val("");
			$("#methodReceiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Online") {
			$(".methodBankDiv").show();
			$(".methodChequeDiv").hide();
			$(".methodUpiDiv").hide();
			$(".bankSelectionMethodDiv").show();
			$("#bankSelectPaymentMethod").val("");
			$("#currentBalanceBankMethod").val("");
			$("#updatedBalanceBankMethod").val("");
			$("#methodChequeNo").val("");
			$("#methodChequeBankName").val("");
			$("#methodChequeBankBranch").val("");
			$("#methodChequeAccountNumber").val("");
			$("#methodPayRemarks").val("");
			$("#methodBankSelect").val("");
			$("#methodTransactionNumber").val("");
			$("#methodReceiverBankBranch").val("");
			$("#methodReceiverBankName").val("");
			$("#methodReceiverIfscCode").val("");
			$("#methodReceiverAccountNumber").val("");
			$("#methodOnlineUpiID").val("");
			$("#methodReceiverOnlineUpiID").val("");
			$("#methodReceiverUpiTransactionID").val("");
		} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Upi") {
			$(".methodBankDiv").hide();
			$(".methodChequeDiv").hide();
			$(".methodUpiDiv").show();
			$(".bankSelectionMethodDiv").show();
			$("#bankSelectPaymentMethod").val("");
			$("#currentBalanceBankMethod").val("");
			$("#updatedBalanceBankMethod").val("");
			$("#methodChequeNo").val("");
			$("#methodChequeBankName").val("");
			$("#methodChequeBankBranch").val("");
			$("#methodChequeAccountNumber").val("");
			$("#methodPayRemarks").val("");
			$("#methodBankSelect").val("");
			$("#methodTransactionNumber").val("");
			$("#methodReceiverBankBranch").val("");
			$("#methodReceiverBankName").val("");
			$("#methodReceiverIfscCode").val("");
			$("#methodReceiverAccountNumber").val("");
			$("#methodOnlineUpiID").val("");
			$("#methodReceiverOnlineUpiID").val("");
			$("#methodReceiverUpiTransactionID").val("");
		}
	});

});


/* For Getting The Select And Unselect Row In Ag-Grid */

$(document).on('change', '#paymentgrid input[type="checkbox"]', function() {
	var rowIndex = $(this).closest('div[role="row"]').index();
	var selectedRowData = gridOptions2.api.getRowNode(rowIndex).data;

	var payAmount = parseFloat($("#payingAmount").val());
	var totalAmounts = {}; // Object to store outstanding amounts for each row

	var totalBulkAmount = 0;
	if ($(this).prop('checked')) {
		totalBulkAmount += selectedRowData.outstandingAmount;
	} else {
		totalBulkAmount -= selectedRowData.outstandingAmount;
	}


	$('input[type="checkbox"]:checked', '#paymentgrid').each(function() {
		var checkedRowIndex = $(this).closest('div[role="row"]').index();
		var checkedRowData = gridOptions2.api.getRowNode(checkedRowIndex).data;
		totalAmounts[checkedRowIndex] = parseFloat(checkedRowData.outstandingAmount);
	});

	var totalAmount = payAmount - Object.values(totalAmounts).reduce((a, b) => a + b, 0);

	//console.log("Total Amount is ", totalAmount);
	$("#remainAmount").val(totalAmount.toFixed(2));
	$("#bulkAmount").val(payAmount - totalAmount);


});
//function to validate Discount field

/* 		function changeToDecimal(elemt) {
			id= $(elemt).attr('id');
			value= $("#"+id).val();
			var floatValue=(parseFloat(value).toFixed(2));
			if (isNaN(floatValue)) {
				floatValue = 0.00;
			}
			if (id.includes('debitAmount')) {
				$("#" + id).val(floatValue);
				var counter = id.split('_')[1];
				$("#creditAmount_" + counter).val(floatValue);
			}else if (id.includes('creditAmount')) {
				 if(floatValue>0){		    	
						$("#"+id).val(floatValue);
				}else{
					$("#"+id).val(0.00);
				}
			}
		    
		}
		  */



/*function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	value = value.replace(/,/g, '');
	var floatValue = (parseFloat(value).toFixed(2));
	if (floatValue > 0) {
		$("#" + id).val(amountFormatter(floatValue));
		$("#creditAmount_0").val(amountFormatter(floatValue))
		getToSubtotal();
	} else {
		$("#" + id).val(0.00);
		$("#creditAmount_0").val(0.00);

	}
}*/


function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	value = value.replace(/,/g, '');
	/*	var floatValue = (parseFloat(value).toFixed(2));
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
		}*/

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
		confirmButtonColor: "#264c82",
		confirmButtonText: "Submit",
		showLoaderOnConfirm: true,
		reverseButtons: true,
		confirmButtonAriaLabel: 'Thumbs up, great!',
		cancelButtonText: 'Cancel',
		cancelButtonAriaLabel: 'Thumbs down',
		preConfirm: () => {
			return new Promise((resolve) => {
				setTimeout(() => {
					//console.log("Doing async operation");
					resolve()
				}, 3000)
			})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-payment-voucher-add",
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

					const fyString = `${fyStartYear} - ${fyEndYear}`;

					// Set dropdown to current FY
					document.getElementById('orderStatusFilter').value = fyString;

					// Set from date as 1st of current month
					const fromDate = `01-${pad(today.getMonth() + 1)}-${today.getFullYear()}`;

					// Set to date as today
					const toDate = `${pad(today.getDate())}-${pad(today.getMonth() + 1)}-${today.getFullYear()}`;

					document.getElementById('fromDate').value = fromDate;
					document.getElementById('toDate').value = toDate;
					if (response.message == "Success") {
						/*swal({
							title: "Voucher Status",
							text: "Payment voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/payment-voucher";
						})*/

						toastr.success("Payment voucher created successfully.");
						setTimeout(() => {
							viewFilteredData();
						}, 1000);
						$("#payment-save-btn").addClass("d-none");
					} else {
						/*swal({
							title: response.code,
							text: response.message,
							type: "warning"
						})*/
					}
					gridOptions5.api.setRowData([]);

					// update the objects 
					selectedInvoiceid = [];
					advanceReceiveDetails = [];
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
	$("#toTotalAmount").text(amountFormatter(sum.toFixed(2)));
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val().replace(/,/g, ''));
	})

	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
}*/

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

function viewFilteredData() {
	let voucherId = globalContraVoucherId;
	$(".loader").show();
	var fromDateFilter = $("#fromDate").val();
	var toDateFilter = $("#toDate").val();
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
			url: "view-account-payment-voucher-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			$('.loader').hide();
			//console.log(JSON.stringify(data))
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewPaymentVoucher;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#purchaseRegisterExcel").prop("disabled", true);
				$("#paymentVoucherPdf").prop("disabled", true);
				$("#totalAmountFooter").val("0.00");
				$("#narration_parent_text_container").removeClass("textareadisable");
				paymentSectionComntainer();
				$("#parent_apv_btn").addClass("d-none");
			} else {
				$("#narration_parent_text_container").addClass("textareadisable");
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
				$("#paymentVoucherPdf").prop("disabled", false);
			}
		});
	}
}



/* 	New Payment MOdal */



function paymentmodal() {

	$('#invoicePayBtn').prop('disabled', false);
	$("#paymentPopup").modal("show");
	$("#paymentModeMethodDiv").hide();
	$("#outstandingAmountDiv").hide();
	$("#outstandingAmountView").val("");
	$(".methodRemark").hide();


}

function cancelModalBtn() {
	$("#paymentPopup").modal('hide');
	$("#vendorName").val('');
	$("#vendorName").html('');
	$("#adjustment").attr("disabled", true);
	$("#amount").attr("disabled", true);
	$("#payingAmount").val('');
	$("#remainingAmtDiv").hide();
	$("#split").hide();

	// Hide Ag-grid On Cancel Button Click 
	var emptydata = [];
	gridOptions2.api.setRowData(emptydata);
	$("#totalInvoice").find('span').html(0);
	$("#adjustment").val("--Select--");
	$("#amount").val('');
	$("#parentDiv").hide();

}

function selectMode() {
	var modeVal = $("#adjustment").val();
	$("#split").hide();
	$("#payBtn").hide();
	$("#parentDiv").hide();
	$("#remainingAmtDiv").hide();
	$("#paymentModeMethodDiv").hide();
	$(".methodRemark").hide();

	if (modeVal === "againstRef") {
		$(".loader").show();
		var $methodRadios = $('input:radio[name=CashChequeOrOnlineMethod]');
		$methodRadios.filter('[value=Cash]').prop('checked', true);
		$(".methodBankDiv").hide();
		$(".methodChequeDiv").hide();
		$(".methodUpiDiv").hide();
		$(".bankSelectionMethodDiv").hide();
		$("#parentDiv").val('');
		$("#paymentgrid").show();
		$("#parentDiv").show();
		$("#paymentModeMethodDiv").hide();
		$("#amount").attr("disabled", true);
		$("#split").show();
		$("#payBtn").hide();

		$("#remainingAmtDiv").show();
		$("#amountDiv").hide();
		$("#bankSelectPaymentMethod").val("");
		$("#updatedBalanceBankMethod").val("");
		$("#currentBalanceBankMethod").val("");

		var vendorid = $("#vendorId").val();

		$.ajax({
			type: "GET",
			url: "payment-voucher-get-invoiceList?id=" + vendorid,
			success: function(response) {
				var jsondata = JSON.parse(response.body);
				var alldata = jsondata.InvoiceDetails;
				console.log("in invoide list ajax")
				if (alldata == null) {
					$("#childSection").hide();
					$("#parentPaymentSection").hide();
					$("#chidSaveBtn").show();
					$("#referenceDataNotFound").hide();
					$(".parent_content_element").show();
				}
				else {
					console.log("data is not null");
				}
				var newData = [];
				$.each(alldata, function(index, obj) {
					// Access properties of each object
					if (obj.payStatus != "Fully Paid") {
						newData.push(obj);
					}
				});
				gridOptions2.api.setRowData(newData);
				var dataLength = newData.length;
				$("#totalInvoice").find('span').html(dataLength);
				$(".loader").hide();
			}
		})

	}
	else {
		if (modeVal == "advance" || modeVal == "newRef" || modeVal == "onAcc") {
			$("#split").hide();
			$("#payBtn").show();
			$(".bankSelectionMethodDiv").show();
			$("#paymentModeMethodDiv").show();
			$(".methodRemark").show();
			$("#parentDiv").hide();
			$("#remainingAmtDiv").hide();
			$("#bankSelectPayment").val("");
			$("#updatedBalanceBank").val("");
			$("#currentBalanceBank").val("");
			if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
				$(".bankSelectionMethodDiv").hide();
				$("#bankSelectPaymentMethod").val("");
				$("#currentBalanceBankMethod").val("");
				$("#updatedBalanceBankMethod").val("");
			}
		}
	}
}

function splitamount() {
	var inputAmount = $("#amount").val();
	var floatAmount = parseFloat(inputAmount);
	//console.log(floatAmount, "float")


	var remainingBulkAmount = floatAmount;
	var rowSelect = gridOptions2.api.getSelectedRows();

	if (rowSelect.length == "0") {
		toastr.error("Please Select Invoices To Pay");
		return false;
	}
	else {

		$("#paymentPopup").hide();
		$("#invPayment").modal("show");
	}
}


function getAmount() {
	var amount = $("#amount").val();
	var modeValue = $("#adjustment").val();

	if (amount.length > 0 && modeValue == "againstRef") {
		$("#split").attr("disabled", false);
	}
	else {
		$("#split").attr("disabled", true);
	}

}

function paymentCancelBtn() {
	$("#paymentPopup").show();
	$("#paymentPopup").addClass("show");
	$("#invPayment").hide();
}




/* ************************************** */

function addPayment() {

	var rowSelect = gridOptions2.api.getSelectedRows();

	if (rowSelect.length == "0") {
		toastr.error("Please Select Invoices To Pay");
		return false;
	}
	var payAmount = $("#payingAmount").val().replace(/,/g, '');
	var remainAmount = $("#remainAmount").val().replace(/,/g, '');
	//console.log("addPayment()==="+payAmount)

	if (payAmount == '' || remainAmount == "NaN") {
		toastr.error("Please Enter Some Amount");
		return false;
	}

	$("#bulkPayBtn").hide();

	deleteFileAccount();
	$('#offeredTableDebitNote').hide();
	$("#invoicePayBtn").prop("disabled", true);
	$("#chequeNo").val('')
	$("#chequeBankName").val('')
	$("#chequeBankBranch").val('')
	$("#chequeAccountNumber").val('')
	$("#bankSelect").val('')
	$("#onlineUpiID").val('')
	$("#transactionNumber").val('')
	$("#receiverBankBranch").val("");
	$("#receiverBankName").val("");
	$("#receiverIfscCode").val("");
	$("#receiverAccountNumber").val("");
	$("#onlineUpiID").val("");
	$("#receiverOnlineUpiID").val("");
	$("#receiverUpiTransactionID").val("");

	$("#salesInvoiceId").val('');
	$("#payPoId").val('');
	$("#totalOrderAmount").val('');
	$("#totalPayableAmount").val('');
	$("#payAmount").val('');
	$("#dueAmount").val('');
	$("#paymentStatus").val('');
	$('#paymentPopup').modal('show');
	var selectedRows = gridOptions2.api.getSelectedRows();
	var selectedRowsString = '';
	var selectedPoId = '';
	var selectedAmount = '';
	var selectedTotalAmount = 0;
	var selectedPayableAmount = 0;
	var selectedPayStatus = '';
	var selectedDueAmount = '';
	var selectedVendorId = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
			selectedPoId += ',';
			selectedAmount += ',';
			selectedVendorId += ',';
			selectedDueAmount += ',';
		}
		selectedRowsString += selectedRow.invoiceId;
		selectedPoId += selectedRow.poId;
		selectedAmount += selectedRow.grandTotal;
		selectedVendorId += selectedRow.vendorId;
		selectedTotalAmount += parseFloat(selectedRow.grandTotal);
		selectedPayableAmount += parseFloat(selectedRow.outstandingAmount);
		selectedPayStatus = selectedRow.payStatus;
		selectedDueAmount += selectedRow.outstandingAmount;
	});
	var invoiceId = selectedRowsString;
	var poId = selectedPoId;
	var amountList = selectedAmount;
	var vendorId = selectedVendorId;
	var dueAmountList = selectedDueAmount;

	$("#salesInvoiceId").val(invoiceId);
	$("#payPoId").val(poId);
	$("#totalOrderAmount").val(selectedTotalAmount);
	$("#payAmount").val(amountList);
	$("#paymentStatus").val(selectedPayStatus);
	$("#vendorIdList").val(vendorId);
	$("#dueAmount").val(dueAmountList);
	$("#totalPayableAmount").val(amountFormatter(selectedPayableAmount.toFixed(2)));


	var $radios = $('input:radio[name=CashChequeOrOnline]');
	if ($radios.is(':checked') === false || $radios.is(':checked') === true) {
		$radios.filter('[value=Cash]').prop('checked', true);
		$(".bankDiv").hide();
		$(".chequeDiv").hide();
		$(".upiDiv").hide();
	}

	var tableBody = document.getElementById('salesInvoiceTableList');
	tableBody.innerHTML = '';
	selectedRows.forEach(function(selectedRow) {
		var newRow = document.createElement('tr');
		var salesInvoiceIdCell = document.createElement('td');
		salesInvoiceIdCell.textContent = selectedRow.invoiceId;
		newRow.appendChild(salesInvoiceIdCell);

		var poIdCell = document.createElement('td');
		poIdCell.textContent = selectedRow.poId;
		newRow.appendChild(poIdCell);

		var customerNameCell = document.createElement('td');
		customerNameCell.textContent = selectedRow.vendorName;
		newRow.appendChild(customerNameCell);

		var amountCell = document.createElement('td');
		amountCell.textContent = parseFloat(selectedRow.outstandingAmount).toFixed(2);
		amountCell.style.textAlign = 'right';
		newRow.appendChild(amountCell);

		var bulkAmountCell = document.createElement('td');
		bulkAmountCell.textContent = '';
		newRow.appendChild(bulkAmountCell);

		var paymentStatusCell = document.createElement('td');
		paymentStatusCell.textContent = selectedRow.payStatus;
		newRow.appendChild(paymentStatusCell);

		tableBody.appendChild(newRow);
	});

	//debit note table 
	var vendorIdList = $("#vendorIdList").val();
	var vendorIds = vendorIdList.split(',');
	var uniqueVendorIds = new Set(vendorIds);
	var sameVendorId = '';

	if (uniqueVendorIds.size === 1) {
		$('#offeredTableDebitNote').show();
		sameVendorId = vendorIds[0];
		//ajax call for debit note details against vendor starts
		$.ajax({
			type: "GET",
			url: "view-account-payment-voucher-getDebitNoteList?vendorId=" + sameVendorId,
			success: function(response) {
				if (response.code == "success") {
					//console.log(JSON.stringify(response));
					var tableBodyDebitNote = document.getElementById('debitNoteTableList');
					tableBodyDebitNote.innerHTML = '';
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.getDebitNoteList;
					if (allData == "" || allData == "null" || allData == null) {
						var emptybdy = '<tr align="left" valign="top">'
							+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
							+ '</tr>';
						$("#debitNoteTableList").append(emptybdy);
					} else {
						//console.log(JSON.stringify(allData))
						var len = allData.length;
						for (var i = 0; i < len; i++) {
							var checkbox = '<td style="text-align:center;"><input type="checkbox" name="debitNoteCheckbox" onclick="getDebitNoteDetails(event);" data-journal-voucher="' + allData[i].journalVoucher + '" id="' + allData[i].journalVoucher + '" data-total-amount="' + parseFloat(allData[i].totalAmount).toFixed(2) + '"></td>';
							var debitNoteData = '<tr>'
								+ checkbox
								+ '<td style="text-align:left">' + allData[i].journalVoucher + '</td>'
								+ '<td style="text-align:left">' + allData[i].invoiceId + '</td>'
								+ '<td style="text-align:left">' + allData[i].paymentId + '</td>'
								+ '<td style="text-align:left">' + allData[i].description + '</td>'
								+ '<td style="text-align:left">' + allData[i].createdOn + '</td>'
								+ '<td style="text-align:right">' + parseFloat(allData[i].totalAmount).toFixed(2) + '</td>'
								+ '</tr>';

							$("#debitNoteTableList").append(debitNoteData);

						}
					}


				}
			},
			error: function(e) {
			}
		});
		//ajax call for debit note details against vendor ends


	} else {
		var tableBodyDebitNote = document.getElementById('debitNoteTableList');
		tableBodyDebitNote.innerHTML = '';
	}

	var remainingAmount = $("#remainAmount").val();

	if (remainingAmount <= 0) {
		$("#paymentPopup").modal('hide');
		$("#invPayment").modal("show");

		var payAmount = $("#payingAmount").val().replace(/,/g, '');
		$("#bulkAmount").val(payAmount);
		$("#bulkCustAmount").val(amountFormatter(payAmount));
		$("#payableAmountFinal").val(payAmount);
		checkBulkPayment();
	}
	else {
		$("#paymentPopup").modal('hide');
		$("#invPayment").modal("show");
		var payAmount = $("#bulkAmount").val();
		var newBulkAmount = parseFloat($("#payingAmount").val().replace(/,/g, ''))
		$("#bulkAmount").val(newBulkAmount)
		$("#bulkCustAmount").val(amountFormatter(newBulkAmount));
		$("#payableAmountFinal").val(newBulkAmount);
		checkBulkPayment();
	}


}

function cancelPaymentBtn() {
	$("#paymentPopup").modal('show');
	$("#invPayment").modal('hide');
}

function checkNumericPay(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9.]/g, '');

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (tempVal.split('.').length > 2) {
		tempVal = tempVal.replace(/\.+$/g, '');
	}

	$("#" + fieldId).val(tempVal);
}

function checkInputAmount() {
	var inputAmount = $("#payAmount").val().replace(/,/g, '');
	var selectedRows = gridOptions2.api.getSelectedRows();
	var selectedTotalAmount = '';
	selectedRows.forEach(function(selectedRow, index) {
		selectedTotalAmount = selectedRow.grandTotal;
	});
	if (inputAmount > selectedTotalAmount) {
		$("#messageParagraph").text("Amount Cannot Exceed Than Total Amount").css("color", "red");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		$("#payAmount").val("");
	}
}

function getPayAmounts() {
	var tableHeaderRow = document.querySelector('#offeredTable thead tr');
	var payAmounts = [];
	if (tableHeaderRow) {
		Array.from(tableHeaderRow.cells).forEach(function(cell, index) {
			if (cell.textContent.trim() === 'Pay Amount') {
				var payAmountColumnIndex = index;
				var tableRows = document.querySelectorAll('#offeredTable tbody tr');
				if (tableRows.length > 0) {
					tableRows.forEach(function(row) {
						var payAmountCell = row.cells[payAmountColumnIndex];
						if (payAmountCell && payAmountCell.querySelector('input')) {
							var payAmount = payAmountCell.querySelector('input').value.trim();
							payAmounts.push(payAmount);
						} else {
							console.warn('Pay Amount cell is empty or undefined in one or more rows.');
						}
					});
				} else {
					console.error('No rows found in the table body.');
				}
			}
		});
	} else {
		console.error('Table header row not found.');
	}
	var commaSeparatedPayAmounts = payAmounts.join(',');
	return commaSeparatedPayAmounts;
}

function getInvoiceStatus() {
	var tableHeaderRow = document.querySelector('#offeredTable thead tr');
	var payStatusSet = [];
	Array.from(tableHeaderRow.cells).forEach(function(cell, index) {
		if (cell.textContent.trim() === 'Payment Status') {
			var payStatusColumnIndex = index;
			var tableRows = document.querySelectorAll('#offeredTable tbody tr');
			tableRows.forEach(function(row) {
				var payStatusCell = row.cells[payStatusColumnIndex];
				if (payStatusCell) {
					var payStatus = payStatusCell.textContent.trim();
					payStatusSet.push(payStatus);
				} else {
					console.error('Pay Status cell is undefined in one or more rows.');
				}
			});
		}
	});
	var commaSeparatedPayStatus = payStatusSet.join(',');
	return commaSeparatedPayStatus;
}



function invoicePayment() {

	$('#invoicePayBtn').prop('disabled', true);

	var selectedRows = gridOptions2.api.getSelectedRows();
	var remainText = $(".remaining-amount-cell").text();
	var remainValue = parseFloat(remainText.match(/-?\d+\.\d+/));
	if (remainValue < 0) {
		$('#invoicePayBtn').prop('disabled', false);
		$("#messageParagraph").text("Amount Cannot Exceed Than Total Amount").css("color", "red");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}
	var payAmounts = getPayAmounts();
	var invoiceStatus = getInvoiceStatus();

	var obj = {};
	obj.salesInvoiceId = $("#salesInvoiceId").val();
	obj.payPoId = $("#payPoId").val();
	obj.totalAmount = $("#totalOrderAmount").val();
	obj.payAmount = $("#payAmount").val().replace(/,/g, '');
	obj.payRemarks = $("#payRemarks").val();
	if ($('input[name=CashChequeOrOnline]:checked').val() == "Cash") {
		var paymentMode = "Cash";
	} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Cheque") {
		var paymentMode = "Cheque";
	} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Online") {
		var paymentMode = "Online";
	} else if ($('input[name=CashChequeOrOnline]:checked').val() == "Upi") {
		var paymentMode = "Upi";
	}
	obj.paymentMode = paymentMode;
	obj.chequeNo = $("#chequeNo").val();
	obj.chequeBankName = $("#chequeBankName").val();
	obj.chequeBankBranch = $("#chequeBankBranch").val();
	obj.chequeAccountNumber = $("#chequeAccountNumber").val();
	obj.bankSelect = $("#bankSelect").val();
	obj.transactionNumber = $("#transactionNumber").val();
	obj.receiverBankName = $("#receiverBankName").val();
	obj.receiverBankBranch = $("#receiverBankBranch").val();
	obj.receiverIfscCode = $("#receiverIfscCode").val();
	obj.receiverAccountNumber = $("#receiverAccountNumber").val();
	obj.onlineUpiID = $("#onlineUpiID").val();
	obj.receiverOnlineUpiID = $("#receiverOnlineUpiID").val();
	obj.receiverUpiTransactionID = $("#receiverUpiTransactionID").val();
	obj.vendorId = $("#vendorIdList").val();
	obj.amountPaid = payAmounts;
	obj.paymentStatus = invoiceStatus;
	obj.usedDebitNotes = $("#usedDebitNotes").val();
	obj.payableAmountFinal = $("#payableAmountFinal").val();
	obj.remainAmount = remainingAmount;
	obj.dueAmount = $("#dueAmount").val();
	obj.bankSelectPayment = $("#bankSelectPayment").val();

	if (obj.paymentMode == "Cash") {
		obj.bankSelectPayment = "TLM0001"
	} else {
		if (obj.bankSelectPayment == null || obj.bankSelectPayment == "null" || obj.bankSelectPayment == "") {
			$('#invoicePayBtn').prop('disabled', false);
			toastr.error("Please Select Bank Account");
			return false
		}
	}
	obj.transactionDate = $("#voucherDateReceipt").val();

	console.log("object on add-----------" + JSON.stringify(obj));
	//return false
	var validation = true;
	/* if (obj.payAmount == null || obj.payAmount == "") {
		validation = false;
		$("#errmsg_payAmount").css('color', 'red');
		$("#errmsg_payAmount").css('font-size', '10px');
			$("#errmsg_payAmount").html("*Minimum Amount Required").show().fadeOut(6000);
		return validation;
	} */
	//return false
	if (validation) {
		$.ajax({
			type: "POST",
			url: "outstanding-payment-ledger-payment-sent",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {
					$("#paymentPopup").modal("hide");

					toastr.success({
						title: "Success",
						text: "Paid Successfully!",
						type: "success"
					}).then(function() {
						location.reload();
					}
					);



					$("#invPayment").modal('hide');
					$("#paymentPopup").modal('hide');
					cancelModalBtn();
					$("#payRemarks").val('');

					agGrid.simpleHttpRequest({
						url: "view-account-payment-voucher"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});
				}


			},
			error: function(data) {
			}
		})
	}
}

function deleteFileAccount() {

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "outstanding-payment-ledger-delete-file",
		success: function(response) {
		},
		error: function(e) {

		}
	});

}

function saveFileAccount() {
	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "outstanding-payment-ledger-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});

}


var initialBulkAmount = 0;
var remainingAmount = 0;

function getDebitNoteDetails(event) {
	var selectedJournalVouchers = [];
	var voucherAmount = 0;
	var payableAmountFinal = parseFloat($("#bulkAmount").val());
	var errorCheckbox = null;
	$('input[name="debitNoteCheckbox"]:checked').each(function() {
		selectedJournalVouchers.push($(this).data('journal-voucher'));
	});
	if (event.target.checked) {
		var id = event.target.id;
		var totalAmount = parseFloat(event.target.dataset.totalAmount) || 0;
		voucherAmount += totalAmount;
		//remainingAmount += totalAmount;
		payableAmountFinal += totalAmount;
		$("#bulkAmount").val(payableAmountFinal);
		checkBulkPayment();
		/* var remainingAmountCell = document.querySelector('.remaining-amount-cell');
		remainingAmountCell.textContent = 'Remaining Amount: ' + remainingAmount.toFixed(2); */
	} else {
		var id = event.target.id;
		var totalAmount = parseFloat(event.target.dataset.totalAmount) || 0;
		voucherAmount -= totalAmount;
		//remainingAmount -= totalAmount;
		payableAmountFinal -= totalAmount;
		$("#bulkAmount").val(payableAmountFinal);
		checkBulkPayment();
		/* var remainingAmountCell = document.querySelector('.remaining-amount-cell');
		remainingAmountCell.textContent = 'Remaining Amount: ' + remainingAmount.toFixed(2); */
	}

	var commaSeparatedList = selectedJournalVouchers.join(',');

	$("#usedDebitNotes").val(commaSeparatedList);
}

function checkBulkPayment() {
	var bulkAmountEmptyCheck = $("#bulkAmount").val();
	var getBulkAmount = parseFloat($("#bulkAmount").val());
	if (bulkAmountEmptyCheck == null || bulkAmountEmptyCheck == "") {
		$("#messageParagraph").text("Pleasae Enter Amount");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}
	var remainingBulkAmount = getBulkAmount;
	var selectedRows = gridOptions2.api.getSelectedRows();
	selectedRows.sort(function(a, b) {
		return parseFloat(a.outstandingAmount) - parseFloat(b.outstandingAmount);
	});
	initialBulkAmount = 0;

	selectedRows.forEach(function(selectedRow) {
		var invoiceAmount = parseFloat(selectedRow.outstandingAmount);

		if (remainingBulkAmount >= invoiceAmount) {
			selectedRow.bulkAmount = invoiceAmount.toFixed(2);
			remainingBulkAmount -= invoiceAmount;
		} else {
			selectedRow.bulkAmount = remainingBulkAmount.toFixed(2);
			remainingBulkAmount = 0;
		}
		initialBulkAmount += parseFloat(selectedRow.bulkAmount) || 0;
	});
	remainingAmount = remainingBulkAmount;
	gridOptions2.api.applyTransaction({ update: selectedRows });
	redrawTable();
	$("#invoicePayBtn").prop("disabled", false);
}

function redrawTable() {
	var selectedRows = gridOptions2.api.getSelectedRows();
	var tableBody = document.getElementById('salesInvoiceTableList');
	tableBody.innerHTML = '';
	var totalBulkAmount = 0;
	var totalSplitAmount = 0;
	selectedRows.forEach(function(selectedRow) {
		var bulkAmount = parseFloat(selectedRow.bulkAmount) || 0;
		totalBulkAmount += bulkAmount;
		if (selectedRow.splitAmount) {
			totalSplitAmount += parseFloat(selectedRow.splitAmount);
		}
	});

	function updateRemainingAmountCell() {
		var remainingAmountCell = document.querySelector('.remaining-amount-cell');
		remainingAmountCell.textContent = 'Remaining Amount: ' + remainingAmount.toFixed(2);
	}

	selectedRows.forEach(function(selectedRow) {
		var newRow = document.createElement('tr');
		var salesInvoiceIdCell = document.createElement('td');
		salesInvoiceIdCell.textContent = selectedRow.invoiceId;
		newRow.appendChild(salesInvoiceIdCell);

		var poIdCell = document.createElement('td');
		poIdCell.textContent = selectedRow.poId;
		newRow.appendChild(poIdCell);

		var customerNameCell = document.createElement('td');
		customerNameCell.textContent = selectedRow.vendorName;
		newRow.appendChild(customerNameCell);

		var amountCell = document.createElement('td');
		amountCell.textContent = parseFloat(selectedRow.outstandingAmount).toFixed(2);
		amountCell.style.textAlign = 'right';
		newRow.appendChild(amountCell);

		var bulkAmountCell = document.createElement('td');
		var payAmountInput = createInput(selectedRow.bulkAmount, function() {
			var previousBulkAmount = parseFloat(selectedRow.bulkAmount) || 0;
			var originalAmount = parseFloat(selectedRow.outstandingAmount) || 0;

			var maxAmount = parseFloat(payAmountInput.getAttribute('data-max-amount')) || originalAmount;
			if (parseFloat(payAmountInput.value) > maxAmount) {
				$("#messageParagraph").text("Amount Exceeds");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				payAmountInput.value = maxAmount.toFixed(2);
			}

			selectedRow.bulkAmount = payAmountInput.value;
			var updatedBulkAmount = parseFloat(selectedRow.bulkAmount) || 0;
			remainingAmount += previousBulkAmount - updatedBulkAmount;
			updateRemainingAmountCell();
			redrawTable();
		}, originalAmount);

		bulkAmountCell.appendChild(payAmountInput);
		newRow.appendChild(bulkAmountCell);

		var paymentStatusCell = document.createElement('td');
		var paymentAmount = parseFloat(selectedRow.bulkAmount) || 0;
		if (isNaN(paymentAmount) || paymentAmount <= 0) {
			paymentStatusCell.textContent = 'Pending';
		} else if (paymentAmount >= parseFloat(selectedRow.outstandingAmount)) {
			paymentStatusCell.textContent = 'Fully Paid';
		} else {
			paymentStatusCell.textContent = 'Partial Paid';
		}
		newRow.appendChild(paymentStatusCell);

		tableBody.appendChild(newRow);
	});

	var remainingAmountRow = document.createElement('tr');
	var remainingAmountCell = document.createElement('td');
	remainingAmountCell.className = 'remaining-amount-cell';
	remainingAmountCell.colSpan = 3;
	remainingAmountCell.textContent = 'Remaining Amount: ' + remainingAmount.toFixed(2);
	remainingAmountRow.appendChild(remainingAmountCell);
	tableBody.appendChild(remainingAmountRow);

}

var originalAmount;

function createInput(value, eventHandler, originalAmount, remainingAmount) {

	var input = document.createElement('input');
	input.type = 'text';
	input.value = value || '0.00';
	input.className = 'pay-amount-input';
	input.style.textAlign = 'right';

	input.addEventListener('input', function() {
		var sanitizedValue = input.value.replace(/[^0-9.]/g, '');
		input.value = sanitizedValue;
	});

	input.addEventListener('blur', function() {
		var enteredAmount = parseFloat(input.value) || 0;
		if (enteredAmount > remainingAmount) {
			input.value = remainingAmount.toFixed(2);
			$("#messageParagraph").text("Amount Exceeds");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
		}
		eventHandler();
	});

	return input;
}

function checkNumberValidation() {
	var inputElement = $('#bulkAmount');
	var inputValue = inputElement.val();
	var totalAmount = $('#totalPayableAmount').val();
	var sanitizedValue = inputValue.replace(/[^0-9.]/g, '');
	inputElement.val(sanitizedValue);
	//check if input value exceeds the total amount
	if (parseFloat(inputValue) > parseFloat(totalAmount)) {
		$("#messageParagraph").text("Amount Exceeds Than Total Amount");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		$('#bulkAmount').val(totalAmount)
	}
}


/* function checkAmountValidation() {
	refreshGrid();
	var payAmount = $("#payingAmount").val();
	var replaceValue = payAmount.replace(/[^0-9.]/g, '');
	var truncatedValue = replaceValue.slice(0, 9);
	$("#payingAmount").val(truncatedValue);
	$("#remainAmount").val(truncatedValue);
}
 */

function checkNumberInput(id) {
	refreshGrid();
	var tagname = $("#" + id).val()
	var replaceValue = tagname.replace(/[^\d.]/g, '');
	var decimalIndex = replaceValue.indexOf('.');
	if (decimalIndex !== -1) {
		replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '');
	}
	$("#" + id).val(replaceValue);
	$("#payingAmount").val(truncatedValue);
	$("#remainAmount").val(truncatedValue);
}




/* ************************* */


/* Auto Search Vendor Name */

function getVendorList() {
	$('.loader').show();
	var search = $("#vendorName").val();

	//console.log("search value",search)
	$("#addre").hide();
	if (search) {

		$
			.ajax({
				type: "POST",
				url: "payment-voucher-vendorList",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {

						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li style="font-weight:700;     padding: 5px 0px; color:#121212;     border-bottom: 1px solid #080808;  font-size:12px; background-color: #9ec5ff;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
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
							content += '<li style="font-weight:600; color:#fff;  font-size:12px; background-color: #07449b;" onClick="selectAutocompleteValue()">'
								+ "No Data Found" + '</li>';
							/* content += '<li style="margin-left:-30px;"  onclick="openModalForVendor();"><a class="addCust" href="#">Add New Vendor</a>'
									+ '</li>'; */
							content += '</ul>';
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);
						}
						$('.loader').hide();
					}
				},
				error: function(data) {
					//console.log(data);
					$('.loader').hide();
				}
			})
	} else {
		$("#suggesstion-box1_").hide();
	}

}

function selectAutocompleteValue1(vendorId, vendorName, outstandingAmt) {

	$("#transNameDiv").hide();
	$("#transAmountDiv").hide();
	$("#multipaymentgrid").show();
	$(".totalPaymentAmountDiv").show();

	console.log("click")
	if (vendorId) {
		$("#vendorId").val(vendorId);
		$("#vendorName").val(vendorName);
		$("#search").val(vendorName);
		$("#search").attr('data-procat', vendorId);
		$("#suggesstion-box1_").hide();
		$("#adjustment").attr("disabled", false);

		$("#outstandingAmountDiv").show();
		$("#outstandingAmountView").val(amountFormatter(outstandingAmt));

		var methodOfAdj = $("#adjustment").val();

		if (methodOfAdj == "againstRef") {
			$("#parentDiv").val('');
			$("#paymentgrid").show();
			$("#parentDiv").show();
			$("#amount").attr("disabled", true);
			$("#split").show();
			$("#payBtn").hide();

			$("#remainingAmtDiv").show();
			$("#amountDiv").hide();

			var vendorid = $("#vendorId").val();


			$.ajax({
				type: "GET",
				url: "payment-voucher-get-invoiceList?id=" + vendorid,
				async: false,
				success: function(response) {
					var jsondata = JSON.parse(response.body);
					var alldata = jsondata.InvoiceDetails;

					console.log("in invoide list ajax")
					if (alldata == null) {
						console.log("Data is null")
						$("#childSection").hide();
						$("#parentPaymentSection").hide();
						$("#chidSaveBtn").show();
						$("#referenceDataNotFound").hide();
						$(".parent_content_element").show();
					}
					else {
						console.log("data is not null");
					}




					var newData = [];
					$.each(alldata, function(index, obj) {
						if (obj.payStatus != "Fully Paid") {
							newData.push(obj);
						}
					});
					gridOptions2.api.setRowData(newData);
					var dataLength = newData.length;
					$("#totalInvoice").find('span').html(dataLength);
				}
			})
		}

	} else {

		$("#vendorId").val("");
		$("#vendorName").val("");
		$("#search").val("");

		$("#outstandingAmountDiv").hide();
		$("#outstandingAmountView").val("");

		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();
		$("#adjustment").attr("disabled", true);

	}
}
function selectAutocompleteValue() {
	$("#vendorId").val("");
	$("#vendorName").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box1_").hide();

}


function refreshGrid() {
	var api = gridOptions2.api;
	var selectedNodes = api.getSelectedNodes();
	selectedNodes.forEach(function(node) {
		node.setSelected(false);
	});
}

function amountPayBtn() {
	var obj = {};

	obj.payRemarks = $("#methodPayRemarks").val();
	if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
		var paymentMode = "Cash";
	} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cheque") {
		var paymentMode = "Cheque";
	} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Online") {
		var paymentMode = "Online";
	} else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Upi") {
		var paymentMode = "Upi";
	}
	obj.paymentMode = paymentMode;
	obj.chequeNo = $("#methodChequeNo").val();
	obj.chequeBankName = $("#methodChequeBankName").val();
	obj.chequeBankBranch = $("#methodChequeBankBranch").val();
	obj.chequeAccountNumber = $("#methodChequeAccountNumber").val();
	obj.bankSelect = $("#methodBankSelect").val();
	obj.transactionNumber = $("#methodTransactionNumber").val();
	obj.receiverBankName = $("#methodReceiverBankName").val();
	obj.receiverBankBranch = $("#methodReceiverBankBranch").val();
	obj.receiverIfscCode = $("#methodReceiverIfscCode").val();
	obj.receiverAccountNumber = $("#methodReceiverAccountNumber").val();
	obj.onlineUpiID = $("#methodOnlineUpiID").val();
	obj.receiverOnlineUpiID = $("#methodReceiverOnlineUpiID").val();
	obj.receiverUpiTransactionID = $("#methodReceiverUpiTransactionID").val();
	obj.methodOfAdj = $("#adjustment").val();
	obj.vendorId = $("#vendorId").val();
	obj.payAmount = $("#payingAmount").val().replace(/,/g, '');
	obj.bankSelectPayment = $("#bankSelectPaymentMethod").val();
	var methodOfAdj = $("#adjustment").val();
	if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
		obj.bankSelectPayment = "TLM0001";
	} else {
		if (obj.bankSelectPayment == "" || obj.bankSelectPayment == null || obj.bankSelectPayment == "null") {
			toastr.error("Please Select Bank Account");
			return false;
		}
	}
	obj.transactionDate = $("#voucherDateMethod").val();
	//	var vendorId = $("#vendorId").val();
	//	var payAmount = $("#payingAmount").val();
	console.log(JSON.stringify(obj))
	//return false;
	if (methodOfAdj == "advance" || methodOfAdj == "newRef") {
		$.ajax({
			type: "POST",
			url: "view-account-payment-voucher-addDebitNote",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					$("#paymentPopup").hide();
					if (methodOfAdj == "advance") {
						toastr.success({
							title: "Success",
							text: "Advance Added Successfully!",
							type: "success"
						}).then(function() {
							location.reload();
						}
						);
					} else if (methodOfAdj == "newRef") {
						toastr.success({
							title: "Success",
							text: "New Reference Added Successfully!",
							type: "success"
						}).then(function() {
							location.reload();
						}
						);
					}


				}
			}

		});
	}
}

function getCurrentBalance(selectElement) {

	var selectedOption = selectElement.options[selectElement.selectedIndex];
	var selectedBalance = selectedOption.getAttribute("data-balance");
	$("#currentBalanceBank").val(amountFormatter(selectedBalance))
	if (selectedBalance == null || selectedBalance == "" || selectedBalance == "null") {
		$("#updatedBalanceBank").val('');
	} else {
		var currentBulkAmount = parseFloat($("#bulkCustAmount").val().replace(/,/g, '')).toFixed(2);
		var currentRemainAmount = parseFloat(selectedBalance).toFixed(2) - currentBulkAmount;
		$("#updatedBalanceBank").val(amountFormatter(currentRemainAmount.toFixed(2)));
	}

}

function getCurrentBalanceMethod(selectElement) {

	var selectedOption = selectElement.options[selectElement.selectedIndex];
	var selectedBalance = selectedOption.getAttribute("data-balance");
	$("#currentBalanceBankMethod").val(selectedBalance)
	if (selectedBalance == null || selectedBalance == "" || selectedBalance == "null") {
		$("#updatedBalanceBankMethod").val('');
	} else {
		var currentBulkAmount = parseFloat($("#payingAmount").val()).toFixed(2);
		var currentRemainAmount = parseFloat(selectedBalance).toFixed(2) - currentBulkAmount;
		$("#updatedBalanceBankMethod").val(currentRemainAmount.toFixed(2));
	}

}



function saveLedgerDetails() {

	var obj = {};

	obj.leadgerId = $('#ledgerId').text();
	obj.ledgername = $('#ledgername').val();
	obj.groupId = $('#undergroupId').val();
	obj.ledgerEmail = $('#ledgerEmail').val();
	obj.ledgerAddress1 = $('#ledgerAddress1').val();
	obj.ledgerAddress2 = $('#ledgerAddress2').val();
	obj.ledgerAddress3 = $('#ledgerAddress3').val();
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

							toastr.success("Ledger added successfully!", " ", "success");
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

function showAccountModel() {

	$("#addLedgerModal").modal('hide');
	$('#addAccountGrpModal').modal('show');
	$("#childAddDiv").html("");
	$('input[name="parentChildRadio"][value="child"]').prop('checked', false);
	$('input[name="parentChildRadio"][value="parent"]').prop('checked', false);
}

function cancelAccountModel() {

	$("#addLedgerModal").modal('show');
	$('#addAccountGrpModal').modal('hide');
}


function openParent() {
	$("#myModalParent").modal('show');
	$("#addLedgerModal").modal('hide');
	$('#addAccountGrpModal').modal('hide');
}

function closeModelAddParent() {
	$("#myModalParent").modal('hide');
	$("#addLedgerModal").modal('show');
	$('#addAccountGrpModal').modal('hide');
	$('input[name=parentChildRadio]').prop('checked', false);
	$("#myModalChild").modal('hide');
}


function openChild() {

	parentList();
}


/* This Function Is For Add Account Group  */

function addParentInfo() {
	const ipAPI = 'https://api.ipify.org?format=json';


	if (!blankValidation("parentName", "TextField", "Parent Name can not be left blank"))
		return false;
	var parentName = $('#parentName').val();

	//submitAndNext(data);
	swal.fire({
		title: "Are you sure to Submit?",
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
			return fetch(ipAPI)
				.then(response => response.json())
				.then(data => Swal.insertQueueStep(data.ip))
				.catch(() => {
					Swal.insertQueueStep({
						type: 'error',
						title: 'Unable to get your public IP'
						,
					})
				})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-group-add-parent",
				data: { 'parentName': parentName },
				success: function(response) {
					console.log(response);
					if (response.message) {
						toastr.success({
							title: "Data saved successfully.",
							type: "success",
						}).then(function() {
							closeModelAddParent();

						})
					} else {
						toastr.success("Data saved successfully");

					}
				}, error: function(response) {
					toastr.error(response.code);
					closeModelAddParent();
				}
			})
		}
	})

}


function parentList() {
	$.ajax({
		type: "POST",
		url: "view-account-journal-voucher-parentNameList",
		dataType: "json",
		contentType: "application/json",
		data: "a",
		success: function(response) {
			if (response.message === "success") {
				var data = response.body;
				var responseSection = $("#childAddDiv");
				responseSection.empty();

				function buildTree(list) {
					var map = {}, node, roots = [], i;

					for (i = 0; i < list.length; i += 1) {
						map[list[i].groupId] = i;
						list[i].children = [];
					}

					for (i = 0; i < list.length; i += 1) {
						node = list[i];
						if (node.parentName !== null) {
							list[map[node.parentName]].children.push(node);
						} else {
							roots.push(node);
						}
					}
					return roots;
				}

				function renderTree(nodes, $ul) {
					if (!nodes) { return; }
					$.each(nodes, function(index, node) {


						var $li = $('<li>').text(node.groupName).appendTo($ul);
						var a = $('<a/>', {
							onclick: "openChildAdd('" + node.groupId + "', '" + node.levelName + "')"
						});

						var icon = $('<i/>', {
							class: 'fa fa-plus-square clickIcon',
							'aria-hidden': 'true'
						});

						a.append(icon);
						$li.append(a);

						if (node.children.length) {
							var $subUl = $('<ul>').appendTo($li);
							renderTree(node.children, $subUl);
						}
					});
				}

				var tree = buildTree(data);
				var $treeContainer = $('<ul>');
				renderTree(tree, $treeContainer);
				responseSection.append($treeContainer);
			}
		},
		error: function(xhr, status, error) {
			console.log(error);
		}
	});
}




function openChildAdd(id, level) {

	$("#myModalChild").modal('show');
	$("#parentId").val(id);
	$("#addAccountGrpModal").modal('hide');
	var currentLevel = level;

	var numericPart = parseInt(currentLevel.substring(1));
	var nextNumericPart = numericPart + 1;

	var nextLevel = "L" + nextNumericPart;

	console.log(nextLevel);

	$("#childLevel").val(nextLevel);

}

function addChildInfo() {
	const ipAPI = 'https://api.ipify.org?format=json';
	if (!blankValidation("childName", "TextField", "Child Name can not be left blank"))
		return false;
	var groupName = $('#childName').val();

	console.log(groupName);
	var parentId = $("#parentId").val();
	var levelName = $("#childLevel").val();

	//submitAndNext(data);
	swal.fire({
		title: "Are you sure to Submit?",
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
			return fetch(ipAPI)
				.then(response => response.json())
				.then(data => Swal.insertQueueStep(data.ip))
				.catch(() => {
					Swal.insertQueueStep({
						type: 'error',
						title: 'Unable to get your public IP'
						,
					})
				})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-group-add-child",
				data: { 'groupName': groupName, 'parentId': parentId, 'levelName': levelName },
				success: function(response) {
					console.log(response);
					if (response.message) {
						swatoastr.success({
							title: "Data saved successfully.",
							type: "success",
						}).then(function() {
							closeModelAddParent();
						})
					} else {
						toastr.success("Data saved successfully.");
						/* swal({
							title:response.code,
							text: response.message,
							type:"warning"
						}) */
					}
				}, error: function(response) {
					toastr.error(response.code);
					closeModelAddParent();
				}
			})
		}
	})
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
								content += '<li style="font-weight:700; padding: 5px 3px; color:#121212; border-bottom: 2px solid #080808;  font-size:12px; background-color: #9ec5ff;"  class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
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
							content += '<li style=" font-weight:bold; font-size:14px; color:#ccc;background-color: #85a3ce;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue2()">'
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
						content += '<li style="font-weight:700; padding: 5px 3px; color:#121212; border-bottom: 2px solid #080808;  font-size:12px; background-color: #9ec5ff;"  class="autocompletedata cp" onClick="selectAutocompleteValue2()">'
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
		content += '<li style="font-weight:700; padding: 5px 3px; color:#121212; border-bottom: 2px solid #080808;  font-size:12px; background-color: #9ec5ff;"  class="autocompletedata cp" onClick="selectAutocompleteValue2()">'
			+ "No Data Found" + '</li>';
		content += '<li style="margin-left:-30px;" '
			+ '</li>';
		content += '</ul>';
		$("#suggesstion-box11_").hide();
		$("#suggesstion-box11_").html(content);
	}

}
function selectAutocompleteValue2(categoryId, categoryName, custGSTNo,
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
						$(option).attr("code", response.body[i].code);
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


function cancelLedgerBtn() {
	$("#addLedgerModal").modal('hide');
	$(".formValidation:contains('Ledger Name Required'):first").hide();
	$(".formValidation:contains('Under Group Name Required'):first").hide();
}



function stateCode() {
	var selectedOption = $('#ledgerState option:selected');
	var code = selectedOption.attr('code');
}

function checkPan(id) {
	var pvalue = $("#" + id).val();
	var panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]$/;
	if (!panRegex.test(pvalue.toUpperCase())) {
		$("#Panvalidate").text("Invalid Pan.")
		return false;
	}
	else {
		$("#Panvalidate").text("");
		var selectedOption = $('#ledgerState option:selected');
		var code = selectedOption.attr('code');
		var gstnumber = code.concat(pvalue.toUpperCase());
		$("#ledgerGst").val(gstnumber);
	}
}

function turnCapital(id) {
	var gstValue = $("#" + id).val();
	var uppercaseVal = gstValue.toUpperCase();
	$("#" + id).val(uppercaseVal);
	if (gstValue.length > 15) {
		var newValue = gstValue.substring(0, 15);
		$("#" + id).val(newValue);
	}

}

function downloadExcelFromGrid() {
	var selectedHeaders = [
		'Payment Voucher Id', 'DATE', 'Invoice Id',
		'Payment Id', 'Credit', 'Debit', 'Amount'
	];
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Payment_Voucher_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';
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
		'Payment Voucher Id': "Total",
		'DATE': "",
		'Invoice Id': "",
		'Payment Id': "",
		'Debit': "",
		'Credit': "",
		'Amount': totalAmount
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Payment Voucher');
	XLSX.writeFile(wb, fileName);
}

function downloadPaymentPdfFromGrid() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	window.open("/account/payment-voucher-pdf?fromDate=" + fromDate + "&toDate=" + toDate, '_blank');

}


function indianCurreny(value) {
	if (value !== null && value !== undefined) {
		value = value.replace(/[^\d.,-]/g, '');


		var parts = value.toString().split('.');
		var integerPart = parts[0].replace(/,/g, '');
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

		if (decimalPart.length > 3) {
			decimalPart = decimalPart.substring(0, 3);
		}

		var numberValue = parseFloat(integerPart + decimalPart);

		// If not a number, return original cleaned value
		if (isNaN(numberValue)) {
			return value;
		}

		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
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
		$("#remainAmount").val(value);
	}

}

function closeModelAddTaxDeduction() {

	//$("#myModalTaxDeduction").modal("hide");
	$("#childSection").show();
	$("#parentPaymentSection").show();
	$("#taxDeduction-content").hide();
}

function getDueDateWarningRenderer(transactionDate) {
	const today = new Date();
	const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
	const dueDate = new Date(today.getFullYear(), today.getMonth(), 7);
	if (today >= startDate && today <= dueDate) {
		const daysRemaining = Math.ceil((dueDate - today) / (1000 * 60 * 60 * 24));
		return `
		< span class= "countdown-text" > ${daysRemaining} days remaining</span >
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

	$("#taxType").val('TDS');

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
	$("#payment-save-btn").removeClass("d-none");
	$("#ticket-editBtn,#parent_apv_btn").addClass("d-none");
	$("#add-btn").addClass("d-none");
	$("#cancel-payment-btn").removeClass("d-none");
	CKEDITOR.instances['description'].setReadOnly(false);
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	$("#narration_parent_text_container").removeClass("textareadisable");
}


function paymentsectioncancel() {

	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	disableTrueAll();
	if (firstRowNode) {
		if (firstRowNode) {
			firstRowNode.setSelected(true);
			rowSelect();
		}
		$("#cancel-payment-btn").addClass("d-none");
		$("#payment-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#toDateCalendar3").addClass("pointeerEventsProperty");
		$("#narration_parent_text_container").addClass("textareadisable");
		$(".payment_vchr_header_info").removeClass("d-none");
		$(".parent_content_element").hide();
		$("#referenceDataNotFound").hide();
		cancelModal();
	}
	else {
		$("#cancel-payment-btn").addClass("d-none");
		$("#payment-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");
		$("#narration_parent_text_container").addClass("textareadisable");
		$(".payment_vchr_header_info").addClass("d-none");

		$("#voucherDate").attr("disabled", true);
		$("#toDateCalendar3").addClass("pointeerEventsProperty");
		$(".parent_content_element").hide();
		$("#referenceDataNotFound").hide();
		cancelModal();



		$("#creditTbodyData").empty();
		var abc = '<tr class="tr_clone" id="credit_0">'
			+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
			+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
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

		disableTrueAll();
	}
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

	// Remove all add buttons from existing rows
	tbody.find('.tr_clone_add').remove();

	// Create new row HTML with updated IDs
	let newRow = `
        <tr class="tr_clone" id="debit_${rowCount}">
            <td>
                <input type="text" placeholder="Search Ledger" id="debitAccountSubGroup_${rowCount}" 
                    class="form-control debitAccountSubGroupCls" 
                    onkeyup="debitSubGroup(this.id);" autocomplete="off">
                <div id="suggesstion-box1_${rowCount}"></div>
                <input type="hidden" id="debitAccountGroupId_${rowCount}" class="form-control debitAccountGroupIdCls">
            </td>
            <td>
                <input type="text" placeholder="Enter Amount" id="debitAmount_${rowCount}" 
                    oninput="formatAmount(this);" 
                    class="form-control debitAmountCls" 
                    onblur="changeToDecimal(this),getFromSubtotal();" autocomplete="off">
            </td>
            <td style="vertical-align: baseline;" align="center" class="d-flex">
                <button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="debitCheckEmpty();">
                    <span class="ti-plus"></span>
                </button>&nbsp;
                <button type="button" class="btn btn-warning rmv go-btn" onclick="removeDebitRow(this);">
                    <span class="ti-minus"></span>
                </button>
            </td>
        </tr>
    `;

	console.log(newRow);
	// Append new row
	tbody.append(newRow);
}



/*function debitAddMore() {
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
}*/

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



	// Remove from advanceReceiveDetails and selectedInvoiceid based on removed ledgerId
	let removedLedgerId = $(button).closest('tr').find('.debitAccountGroupIdCls').val();

	if (removedLedgerId) {
		advanceReceiveDetails = advanceReceiveDetails.filter(item => item.rcvLedgerId !== removedLedgerId);
		selectedInvoiceid = selectedInvoiceid.filter(item => item.legerid !== removedLedgerId);

		console.log("Updated advanceReceiveDetails -->", advanceReceiveDetails);
		console.log("Updated selectedInvoiceid -->", selectedInvoiceid);
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

	getToSubtotal()
}


function creditCheckEmpty() {
	let creditValid = true;

	$('.creditAccountSubGroupCls').each(function() {
		if ($(this).val().trim() === "") {
			toastr.error("Please Enter Ledger");
			creditValid = false;
			return false; // break loop
		}
	});

	if (creditValid) {
		$('.creditAmountCls').each(function() {
			if ($(this).val().trim() === "") {
				toastr.error("Please Enter Amount");
				creditValid = false;
				return false; // break loop
			}
		});
	}

	if (creditValid) {
		creditAddMore();
	}
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


function creditAddMore() {
	let rowCount = $("#creditTbodyData").children('tr').length;

	// Remove all .tr_clone_add buttons from existing rows
	$("#creditTbodyData .tr_clone_add").remove();

	let html = '<tr class="tr_clone" id="credit_' + rowCount + '">'
		+ '<td>'
		+ '<input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_' + rowCount + '" '
		+ 'class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_' + rowCount + '"></div>'
		+ '<input type="hidden" id="creditAccountGroupId_' + rowCount + '" class="form-control creditAccountGroupIdCls">'
		+ '</td>'
		+ '<td>'
		+ '<input type="text" placeholder="Enter Amount" id="creditAmount_' + rowCount + '" '
		+ 'oninput="formatAmount(this);" class="form-control creditAmountCls" '
		+ 'onblur="changeToDecimal(this),getToSubtotal();">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">';

	// Add buttons to last row
	html += '<button type="button" class="btn btn-primary tr_clone_add go-btn" onclick="creditCheckEmpty();">'
		+ '<span class="ti-plus"></span></button>';

	if (rowCount > 0) {
		html += '&nbsp;<button type="button" class="btn btn-warning rmv go-btn" onclick="removeCreditRow(this);">'
			+ '<span class="ti-minus"></span></button>';
	}

	html += '</td></tr>';

	// Append to tbody
	$("#creditTbodyData").append(html);
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



function validateSameLedgerInDebitCredit() {
	let duplicateFound = false;

	$(".debitAccountSubGroupCls").each(function(i) {
		let debitLedgerName = $(this).val().trim();
		let debitLedgerId = $("#debitAccountGroupId_" + i).val()?.trim() || "";

		$(".creditAccountSubGroupCls").each(function(j) {
			let creditLedgerName = $(this).val().trim();
			let creditLedgerId = $("#creditAccountGroupId_" + j).val()?.trim() || "";

			console.log("Checking:", debitLedgerName, creditLedgerName, debitLedgerId, creditLedgerId);

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
			let idFieldId = $(this).attr('id').replace("creditAccountSubGroup_", "creditAccountGroupId_");
			let ledgerId = $("#" + idFieldId).val();

			if (ledgerName === newLedgerName && ledgerId === newLedgerId) {
				isDuplicate = true;
				return false; // break loop
			}
		});

		return isDuplicate;

	}

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
		url: "payment-voucher-approve-voucher",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if (resp.code == "success") {
				toastr.success("Voucher approved successfully");
				globalContraVoucherId = voucherId;
				viewFilteredData();
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





function paymentMethodSelection() {
	const selected = $('input[name="paymentMethod"]:checked').val();


	let data = { mode: selected };

	switch (selected) {
		case 'cash':
			data.remarks = $('#cashRemarks').val();
			break;

		case 'cheque':
			data.chequeNumber = $('#chequeNumber').val();
			data.chequeDate = $('#ChequeDate').val();
			data.bankName = $('#bankName').val();
			data.bankBranch = $('#bankBranch').val();
			data.accountNumber = $('#accountNumber').val();
			data.remarks = $('#chequeRemarks').val();
			break;

		case 'online':
			data.transactionNumber = $('#transactionNumber').val();
			data.receiverBankName = $('#receiverBankName').val();
			data.receiverBankBranch = $('#receiverBankBranch').val();
			data.receiverIFSC = $('#receiverIFSC').val();
			data.receiverAccountNumber = $('#receiverAccountNumber').val();
			data.chequeDate = $('#onlineTrnsDate').val();
			data.remarks = $('#onlineRemarks').val();
			break;

		case 'upi':
			data.upiID = $('#upiID').val();
			data.receiverUpiID = $('#receiverUpiID').val();
			data.upiTransactionID = $('#upiTransactionID').val();
			data.chequeDate = $('#UPITrnsDate').val();
			data.remarks = $('#upiRemarks').val();
			break;
	}

	const jsonOutput = JSON.stringify(data, null, 2);
	console.log("Payment JSON:", jsonOutput);
	return jsonOutput;
}



function setPaymentData(responseData) {
	// Step 1: Show the container if hidden
	//$('#payment_method_container').removeClass('d-none');

	let data = "";
	if (responseData) {

		data = JSON.parse(responseData);

		if (data.mode) {
			$("#payment_method_container").removeClass("d-none");
		}
		else {
			$("#payment_method_container").addClass("d-none");
		}
	}
	else {
		$("#payment_method_container").addClass("d-none");
	}
	// Step 2: Select the payment mode radio button
	$('input[name="paymentMethod"][value="' + data.mode + '"]').prop('checked', true).trigger('change');

	// Step 3: Fill form fields based on the mode
	switch (data.mode) {
		case 'cheque':
			$('#chequeNumber').val(data.chequeNumber || '');
			$("#ChequeDate").val(data.chequeDate || '');
			$('#bankName').val(data.bankName || '');
			$('#bankBranch').val(data.bankBranch || '');
			$('#accountNumber').val(data.accountNumber || '');
			$('#chequeRemarks').val(data.remarks || '');
			break;

		case 'online':
			$('#transactionNumber').val(data.transactionNumber || '');
			$('#receiverBankName').val(data.receiverBankName || '');
			$('#receiverBankBranch').val(data.receiverBankBranch || '');
			$('#receiverIFSC').val(data.receiverIFSC || '');
			$('#receiverAccountNumber').val(data.receiverAccountNumber || '');
			$('#onlineRemarks').val(data.remarks || '');
			$("#onlineTrnsDate").val(data.chequeDate || '');
			break;

		case 'upi':
			$('#upiID').val(data.upiID || '');
			$('#receiverUpiID').val(data.receiverUpiID || '');
			$('#upiTransactionID').val(data.upiTransactionID || '');
			$('#upiRemarks').val(data.remarks || '');
			$("#UPITrnsDate").val(data.chequeDate || '');
			break;
	}
}


function resetFields() {

	$('#chequeNumber').val('');
	$("#ChequeDate").val('');
	$('#bankName').val('');
	$('#bankBranch').val('');
	$('#accountNumber').val('');
	$('#chequeRemarks').val('');

	$('#transactionNumber').val('');
	$('#receiverBankName').val('');
	$('#receiverBankBranch').val('');
	$('#receiverIFSC').val('');
	$('#receiverAccountNumber').val('');
	$('#onlineRemarks').val('');
	$("#onlineTrnsDate").val('');

	$('#upiID').val('');
	$('#receiverUpiID').val('');
	$('#upiTransactionID').val('');
	$('#upiRemarks').val('');
	$("#UPITrnsDate").val('');



}