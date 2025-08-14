function openNav() {
	document.getElementById("mySidenav").style.cssText = "width: 350px; position: absolute; right:-10px; overflow: hidden; height:auto; top:300px;";

	document.getElementById("main_content").style.width = "70%";
	$("#gstCalS").hide();
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main_content").style.width = "100%";
	$("#gstCalS").show();


	$("#itemId").val('');
	$("#itemName").val('');
	$("#productMode").val('');
	$("#hsnCode").val('');
	$("#quantity").val('');
	$("#returnQuantity").val('');
	$("#priceRate").val('');
	$("#discountCode").val('');
	$("#gstRate").val('');
	$("#totalAmount").val('');
	$("#editProduct").val(null);

}

var count1 = 0;
function allCheck1() {
	count1++;

	if (count1 == 1) {
		$('.checkCls1').prop("checked", true);
	} else {
		count1 = 0;
		$('.checkCls1').prop("checked", false);
	}
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
		id.style.display = "none";
	}
}



$(document).ready(function() {
	//$(".innercontent").hide();
	getCurrentFinancialYear();
	/*$("#gstCalS").hide();
	$("#statusDiv").hide();*/
	/*$('#reqDltBtn').attr('disabled', true);
	$('#dwnldBtn').attr('disabled', true);

	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	$('.collapse').on('hide.bs.collapse', function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});*/



	/* Intialize The CKEditor For Narration Start */

	$("#adjustment").select2({
		placeholder: "Select Method",
		allowClear: true
	});

	$("#costCenterCredit").select2({
		placeholder: "Select Cost Center",
		allowClear: true
	});

	CKEDITOR.replace('descriptionCredit', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	/* Intialize The CKEditor For Narration End */

	/*var gridDiv3 = document.querySelector('#multipaymentgrid');
	new agGrid.Grid(gridDiv3, gridOptions3);*/

	$("#newBtn").click(function() {
		getVoucherNumber();
		$("#gstCalS").show();
		$("#myGrid").hide();
		$("#reqTable").hide();
		$(".btn-hs").hide();
		$("#demo").show();
		$("#addProduct").show();
		$("#dltProduct").show();
		$('#totalAmountFooterDiv').hide();
		$("#save1").show();
		$("#fyearDiv").hide();
		$("#fdateDiv").hide();
		$("#tdateDiv").hide();
		$("#filterDiv").hide();
		$("#creditHeadId").text("");
		$("#partyLedgerId").val("");
		$("#partyLedger").val("");
		$("#creditNoteDate").val("");
		$("#costCenter").val("");
		$("#orderNumber").val("");
		$("#subTotal").val("");
		$("#qSGST").val("");
		$("#qCGST").val("");
		$("#qIGST").val("");
		$("#grandTotal").val("");
		$("#taxType").val("");

		$(".innercontent").hide();
		$("#amount").val("");
		productOptions.api.setRowData("");

	})

	$("#quarter").click(function() {
		$("#myTbl").hide();
		$(".btn-hs").hide();
		$("#demo").show();
	})

	$("#receiveDate").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
	})

	$("#browseBtn").click(function() {
		$("#myModal").modal('show');
		getCategoryList();
	})

	//productOptions.api.setRowData("");
	gridOptions.api.setRowData("");


	const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var d = new Date();
	var month = monthNames[d.getMonth()];
	var day = d.getDate();
	var output = (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + d.getFullYear();
	$("#creditDate").text(output);
	var a = new Date(output);
	var b = weekday[a.getDay()];
	$("#creditDay").text(b);

	$("#creditNoteDate").datetimepicker({
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
	var fromDate = dateString.toString();
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
		//minDate : 0,
	}).on("change", function() {
		$('#creditNoteDate').val($(this).val());
	})

	$('#creditNoteDate').blur(function() {
		$("#toDateCalendar3").val($(this).val());
	})

	$("#toDateCalendarCREDIT").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#voucherDateCREDIT').val($(this).val());
	})

	$('#voucherDateCREDIT').blur(function() {
		$("#toDateCalendarCREDIT").val($(this).val());
	})
});

function getVoucherNumber() {
	$.ajax({
		type: "GET",
		url: "debit-note-vouchernumber",
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#voucherNumber").text(response.body[0].key);
			}
		}
	});

}

$("input[class='myCheckbox']").click(function() {
	if ($("#formID input:checkbox:checked").length > 0) {
		$(".checkSubmit").addClass('active');
	} else {
		$(".checkSubmit").removeClass('active');
	}
});

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}




function getOrderNUmber(id) {
	if (id) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "debit-note-getorderList?id=" + id,
			success: function(response) {
				if (response.message == "success") {
					$("#orderNumber").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select Order Number");
					$("#orderNumber").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#orderNumber").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	}
}

function getProductList() {
	var ord = $("#orderNumber").val();
	if (ord) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "debit-note-getProductList?id=" + ord,
			success: function(response) {
				if (response.message == "success") {
					$("#quantity").val("");
					$("#priceRate").val("");
					$("#productMode").val("");
					$("#hsnCode").val("");
					$("#discountCode").val("");
					$("#discount").val("");
					$("#gstRate").val("");
					$("#discount").val("");
					$("#totalAmount").val("");
					$("#itemSgst").val("");
					$("#itemCgst").val("");
					$("#returnQuantity").val("");
					$("#itemName").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select Product");
					$("#itemName").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#itemName").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	}
}


function getProductDetails() {
	var proname = $("#itemName :selected").text();
	$("#proName").val(proname);
	var proid = $("#itemName").val();
	var ord = $("#orderNumber").val();

	$.ajax({
		type: "GET",
		url: "debit-note-getProductDetails?oid=" + ord + "&proid=" + proid,
		success: function(response) {
			if (response.message == "Success") {
				$("#quantity").val(response.body[0].quantity);
				$("#priceRate").val(response.body[0].unitPrice);
				$("#hsnCode").val(response.body[0].hsnCode);
				$("#productSize").val(response.body[0].productDimension);
				$("#discountCode").val(response.body[0].dealerCode);
				$("#discount").val(response.body[0].discount);
				$("#gstRate").val(response.body[0].gstRate);
				$("#totalAmount").val(response.body[0].lineTotal);
				$("#itemSgst").val(response.body[0].itemSgst);
				$("#itemCgst").val(response.body[0].itemCgst);
			}
		},
		error: function(e) {
		}
	});

}



var grandTotal = 0;
var subTotal = 0;
var totalGst = 0;
var totalSgst = 0;
var totalCgst = 0;
var totalIgst = 0;
function calculateLineTotal() {

	var itemUnitPrice = parseFloat($("#priceRate").val());
	//	var quantity =parseFloat($("#quantity").val());
	var quantity = parseFloat($("#returnQuantity").val());
	var itemDiscount = parseInt($("#discount").val());
	var gstRate = ($("#gstRate").val());


	if (quantity) {
		if (itemDiscount > 0) {
			var priceAfterDiscount = ((itemUnitPrice * quantity) * (100 - itemDiscount)) / 100;
		} else {
			var priceAfterDiscount = (itemUnitPrice * quantity);
		}

		priceAfterDiscount = priceAfterDiscount.toFixed(2)
		$("#totalAmount").val(priceAfterDiscount);
	} else {
		$("#totalAmount").val("");
		return false;
	}

}


function alphabetCheck() {
	var ordQty = $("#quantity").val();
	var qty = $("#returnQuantity").val();
	var reg = /^[a-zA-Z]*$/;
	if (qty != "") {
		if (reg.test(qty)) {
			toastr.error("Invalid Input");
			$("#returnQuantity").val("");
			return false;
		}
		if (qty) {
			if (qty > ordQty) {
				toastr.error("Return quantity should be less than ordered quantity");
				$("#returnQuantity").val("");
				return false;

			}
		}
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
var columnDefs = [
	{
		//	headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		pinned: 'left',
		resizable: true,
		width: 20
	},

	{
		headerName: "DEBIT Note Id",
		field: "creditNoteId",
		pinned: 'left',
		/*cellRenderer: function(params) {
			return '<a onclick=editDebitNOte("' + params.data.creditNoteId
				+ '") href="javascript:void(0)">'
				+ params.data.creditNoteId + '</a>';
		}*/

	},
	{
		headerName: "Created Date",
		field: "createdOn",
		pinned: 'left',
		width: 150,

	}, {
		headerName: "Party Name",
		field: "partyLedger",
		width: 205,
	}, {
		headerName: "Ledger Account",
		field: "salesLedger"
	}, {
		headerName: "Debit Date",
		field: "creditNoteDate",

	}, {
		headerName: "Order Number",
		field: "orderNumber",
		hide: true
	}, {
		headerName: "Total Amount",
		field: "grandTotal",
		valueFormatter: params => amountFormatter(params.data.grandTotal.toFixed(2)),
		cellStyle: {
			textAlign: 'right'
		}
	},

	{
		headerName: "Debit Status",
		field: "useFlag",
		width: 150,
		cellRenderer: function(params) {

			if (params.data.useFlag == "1") {
				return '<div style="color:orange;font-weight: bold;">' + 'Used' + '</div>';
			}
			else {
				return '<div style="color:red;font-weight: bold;">' + 'Unused' + '</div>';
			}

		}



	},

	{
		headerName: "Cost Center",
		field: "costCenter",
	}, {
		headerName: "Created By",
		field: "createdBy"

	}];

// for product table


var productDefs = [
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
		valueGetter: "node.rowIndex + 1",
		field: "slNo",
		width: 110,
		cellStyle: {
			textAlign: 'center'
		},
		/* 	 cellRenderer : function(params) {
				if (params.data.slNo) {
					return '<a onclick=editProduct("' + params.data.slNo
							+ '") href="javascript:void(0)">'
							+ params.data.slNo + '</a>';
				} else {
					return '<a onclick=editProduct("' + params.data.slNo
							+ '") href="javascript:void(0)">'
							+ params.data.slNo + '</a>';
				}
			} */
	}, {

		headerName: 'Product Name',
		field: "categoryName",
		width: 170,
		cellStyle: {
			textAlign: 'center'
		},

	},
	{
		headerName: 'HSN Code',
		field: "hsnCode",
		width: 170,
		cellStyle: {
			textAlign: 'center'
		},

	}, {
		headerName: 'Quantity',
		field: "quantity",
		width: 150,
		valueFormatter: currencyFormatter,
		cellStyle: {
			textAlign: 'center'
		},

	}, {
		headerName: 'Unit Price',
		field: "unitPrice",
		width: 180,
		cellStyle: {
			textAlign: 'right'
		},

	},
	{
		headerName: 'GST Rate',
		field: "gstRate",
		width: 160,
		valueFormatter: currencyFormatter,
		cellStyle: {
			textAlign: 'center'
		},

	}, {
		headerName: 'Amount',
		field: "lineTotal",
		width: 188,
		cellStyle: {
			textAlign: 'right'
		},


	}, {
		headerName: 'CGST',
		field: "itemCgst",
		type: 'rightAligned',
		//valueFormatter : currencyFormatter
	}, {
		headerName: 'SGST',
		field: "itemSgst",
		type: 'rightAligned',
		//valueFormatter : currencyFormatter
	}, {
		headerName: 'IGST',
		field: "itemIgst",
		type: 'rightAligned',
		//valueFormatter : currencyFormatter
	}, {
		headerName: 'Taxable Amount',
		field: "taxableAmt",
		type: 'rightAligned',
		width: 155,
	}];

// let the grid know which columns and what data to use activity table
/* 	var productOptions = {
		columnDefs : productDefs,
		rowSelection : 'multiple',
		groupSelectsChildren : true,
		suppressRowClickSelection : true,
		suppressAggFuncInHeader : true,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 153
		},
		getRowNodeId : function(data) {
			return data.slNo;
		}

	}; */

// let the grid know which columns and what data to use
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},
	onSelectionChanged: onSelectionChanged,
	pagination: true,
	paginationPageSize: 10

};


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

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#product');
	new agGrid.Grid(gridDiv, productOptions);

	var gridDiv2 = document.querySelector('#paymentgrid');
	new agGrid.Grid(gridDiv2, gridOptions2);

	/* var gridDiv3 = document.querySelector('#multipaymentgrid');
	new agGrid.Grid(gridDiv3, gridOptions3); */


});
agGrid.simpleHttpRequest({
	url: "debit-note-view"
}).then(function(data) {
	var jsonData = JSON.parse(data.body);
	console.log(jsonData)
	var allData = jsonData.DebitNoteData;
	console.log(allData)
	var len = allData.length;
	$('#totalReq').find('span').html(len);
	gridOptions.api.setRowData(allData);
});



function resetState() {
	gridOptions.columnApi.resetColumnState();

}

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var length = gridOptions.api.getDisplayedRowCount();
	console.log("length-->", length);
	$("#totalReq").find('span').html(length);
}
function cancel() {
	$("#searchRowDiv").show();
	$("#reqTable").show();
	$(".btn-hs").show();
	$("#myGrid").show();
	$("#demo").hide();
	$("#fyearDiv").show();
	$("#fdateDiv").show();
	$("#tdateDiv").show();
	$("#filterDiv").show();
	$('#totalAmountFooterDiv').show();
	$("#desc").val('');
	$("#reqType").val('');
	$("#reqPrior").val('');
	$("#receiveDate").val('');
	$("#isHold").val('');
	$("#source").html('');
	//$("#rfqId").val(rfqId);
	$("#reqHeadId").html('');
	$("#createdDate").append('');
	$("#sku").val('');
	$("#skuEditId").val('');
	$("#itemId").val('');
	$("#itemName").val('');
	$("#locationId").val('');
	$("#quantity").val('');
	$("#unit").val('');
	$("#taxType").val('');
	$("#search").val('');

	CKEDITOR.instances.descriptionCredit.setData("");
}
function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var approveStatus = selectedData.map(node => node.approveStatus);
	if (rowCount > 0) {
		$('#reqDltBtn').attr('disabled', false);
		var debitNotedId = selectedRows[0].creditNoteId
		$('#newBtn').attr('disabled', true);
		$('#dwnldBtn').attr('disabled', false);
		editDebitNOte(debitNotedId);
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#debit_save_btn").addClass("d-none");
		$("#debit_cancel_btn").addClass("d-none");
		$("#debit_delete_btn").removeClass("d-none");
		$(".debit_note_container").addClass("textareadisable");
		$("#costCenterCredit").attr("disabled", true);
		$("#adjustment").attr("disabled", true);
		$("#toDateCalendarCREDIT").addClass("pointeerEventsProperty");
		CKEDITOR.instances['descriptionCredit'].setReadOnly(true);
		$("#narration_parent_text_container").addClass("textareadisable");
		$(".drNoteInfo").removeClass("d-none");


	} else {
		$('#reqDltBtn').attr('disabled', true);
		$('#newBtn').attr('disabled', false);
		$('#dwnldBtn').attr('disabled', true);
		$(".debit_note_container").removeClass("textareadisable");
		$("#costCenterCredit").attr("disabled", false);
		$("#adjustment").val("");
		$("#costCenterCredit").val("");
		$("#adjustment").attr("disabled", false);
		$("#toDateCalendarCREDIT").removeClass("pointeerEventsProperty");
		$("#debit_delete_btn").addClass("d-none");
		$(".drNoteInfo").addClass("d-none");
		$("#narration_parent_text_container").removeClass("textareadisable");
		$(".drNoteInfo").addClass("d-none");
		debitNoteContainer();

		CKEDITOR.instances['descriptionCredit'].setReadOnly(false);

	}

}



function debitNoteContainer() {
	$("#journalVoucher").text('');
	$(".debit_note_container").removeClass("textareadisable");
	$("#toDateCalendarCREDIT").removeClass("pointeerEventsProperty");
	$("#costCenterCredit").attr("disabled", false);
	$("#adjustment").attr("disabled", false);
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
	$("#debit_save_btn").removeClass("d-none");
	$("#debit_cancel_btn").removeClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	CKEDITOR.instances['descriptionCredit'].setData('');
	CKEDITOR.instances['descriptionCredit'].setReadOnly(false);
	$("#toDateCalendarCREDIT").removeClass("pointeerEventsProperty");
	$("#narration_parent_text_container").removeClass("textareadisable");
	getVoucherNumber();

}
// for edit indivisual product
function editProduct(slNo) {

	var reqId = $("#reqHeadId").html();
	var rowNode = productOptions.api.getRowNode(slNo);

	$("#sku").val(rowNode.data.sku);
	//$("#skuEditId").val(prodId);
	$("#itemName").val(rowNode.data.itemId);
	$("#hsnCode").val(rowNode.data.hsnCode);
	$("#proName").val(rowNode.data.itemName);
	$("#locationId").val(rowNode.data.locationId);
	$("#productEdit").val(slNo);
	$("#quantity").val(rowNode.data.quantity);
	$("#unit").val(rowNode.data.unit);
	$("#priceRate").val(rowNode.data.unitPrice);
	$("#skuTemp").val(rowNode.data.sku);
	$("#totalAmount").val(rowNode.data.lineTotal);
	$("#gstRate").val(rowNode.data.gstRate);
	$("#itemIdTemp").val(rowNode.data.itemId);
	//$("#unitName").val(rowNode.data.unitName);

	openNav();

}

function editDebitNOte(id) {
	$(".innercontent").show();
	$(".drNoteInfo").show();
	//$("#purchaseRegisterExcel").hide();

	$.ajax({
		type: "GET",
		url: "debit-note-editVoucher?id=" + id,
		success: function(response) {
			if (response.code == "Success") {

				//hideShowS();
				$("#debitTbodyData").empty();
				$("#creditTbodyData").empty();

				const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
				var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
				var d = new Date();
				var month = monthNames[d.getMonth()];
				var day = d.getDate();
				var output = (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + d.getFullYear();
				$("#recieptDate").text(output);
				var a = new Date(output);
				var b = weekday[a.getDay()];
				$("#recieptDay").text(b);

				var dateString = ('0' + d.getDate()).slice(-2) + '-' + ('0' + (d.getMonth() + 1)).slice(-2) + '-' + d.getFullYear();
				var todate = dateString.toString();
				$("#voucherDateCREDIT").val(todate)

				$('#voucherNumber2').text(response.body[0].journalVoucher);
				$("#costCenterCredit").val(response.body[0].costCenter).trigger('change');
				//$("#descriptionCredit").val(response.body[0].description);
				$("#journalVoucher").text(response.body[0].journalVoucher);
				$("#voucherDateCREDIT").val(response.body[0].voucherDate);
				$("#adjustment").val(response.body[0].methodOfNote).trigger('change');

				CKEDITOR.instances['descriptionCredit'].setData(response.body[0].description);

				var toTotalAmount = 0;
				var fromTotalAmount = 0;
				for (var i = 0; i < response.body.length; i++) {
					if (response.body[i].transactionType == "Debit") {
						var amount = response.body[i].fromAmount;
						fromTotalAmount = fromTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"  id="debit_' + i + '">'
							+ '<td class ="legerSearchDiv"><input type="text" id="debitAccountSubGroup_' + i + '" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" value="' + response.body[i].subGroupName + '"> '
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
							+ '<td class="d-flex">'
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
							+ '<td class="d-flex"><button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();">'
							+ '<span class="ti-plus"></span>'
							+ '</button>&nbsp;</td>'
							+ '</tr>';

						$("#creditTbodyData").append(abc);
					}
					$('#fromTotalAmount').text(amountFormatter((parseFloat(fromTotalAmount)).toFixed(2)));
					$('#toTotalAmount').text(amountFormatter((parseFloat(toTotalAmount)).toFixed(2)));
				}
			}
		},
		error: function(e) {
		}
	});
}


function getOrderNUmber2(id1, id2) {
	if (id1) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "debit-note-getorderList?id=" + id1,
			success: function(response) {
				if (response.message == "success") {
					$("#orderNumber").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select Order Number");
					$("#orderNumber").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#orderNumber").append(option);
					}

					if (id2) {
						$("#orderNumber").val(id2);
					}
				}
			},
			error: function(e) {
			}
		});

	}

}

function deleteFun() {
	$('#delete').modal('show');
}
function deleteProduct() {
	$('#deleteProduct').modal('show');
}
function deleteProductIndConfirm() {
	$('#deleteProductInd').modal('show');
}

// delete requisition 
function deleteOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var deletedId = selectedRows[0].creditNoteId

	$.ajax({
		type: "GET",
		url: "debit-note-delete-id?id=" + deletedId,
		success: function(response) {
			if (response.code == "Success") {
				console.log("okokok")
				//cancelBtn();
				toastr.success("Debit note deleted successfully.")
				viewFilteredData();

			}
		}

	});

}
// delete selected record from ag grid
function deleteProductOnclick() {
	var selectedRows = productOptions.api.getSelectedRows();
	productOptions.api.applyTransaction({
		remove: selectedRows
	});
	cancelModalProductBtn();

}

function cancelModalBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
}
//for closeing modal box for dlt  product
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteProduct').modal('hide');
}
//for closeing modal box for dlt ind product
function cancelModalBtnDPI() {
	$('#deleteProductInd').modal('hide');
}
//for approve requisition


function saveTableData() {
	var editProduct = $("#editProduct").val();

	var item = {};
	var data = 1;
	var validation = true;
	/* var sum = 0.0;
	var gstRate = 0.0;
	var qSGST = 0.0;
	var qCGST = 0.0;
	var qIGST = 0.0;
	
	var itemIgst = 0.0;
	var itemCgst = 0.0;
	var itemSgst = 0.0;
	var subtotal =0.0;
	
	var perItemCGST=0.0;
	var perItemSGST=0.0; */

	if (validation) {
		item.slNo = data;
		productOptions.api.forEachNode(function(rowNode, index) {
			if (!editProduct) {
				data = data + 1;
				item.slNo = data;
			} else {
				item.slNo = editProduct;
			}
		});
		item.itemId = $('#itemName').val();
		item.itemName = $("#proName").val();
		item.hsnCode = $("#hsnCode").val();
		item.quantity = $('#returnQuantity').val();
		item.unitPrice = $('#priceRate').val();
		item.discount = $('#discount').val();
		item.gstRate = $('#gstRate').val();
		item.lineTotal = $('#totalAmount').val();
		item.categoryName = $("#proName").val();
		item.categoryId = $('#itemName').val();



		var taxType = $("#taxType").val();
		if (taxType == 'true') {
			var itemSgstFix = (parseFloat(item.lineTotal) * parseFloat(item.gstRate)) / 200;
			var itemCgstFix = (parseFloat(item.lineTotal) * parseFloat(item.gstRate)) / 200;

			item.itemCgst = parseFloat(itemCgstFix).toFixed(2);
			item.itemSgst = parseFloat(itemSgstFix).toFixed(2);
			item.itemIgst = 0;
			item.taxableAmt = (parseFloat(item.lineTotal) + parseFloat(item.itemCgst)
				+ parseFloat(item.itemSgst)).toFixed(2);


		} else {
			item.itemCgst = 0;
			item.itemSgst = 0;
			item.itemIgst = (item.lineTotal * item.gstRate) / 100;
			item.taxableAmt = (parseFloat(item.lineTotal) + parseFloat(item.itemIgst)).toFixed(2);
		}
		//item.slNo = data;
		var quot = [];

		if (editProduct) {
			var rowNode = productOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
		} else {
			productOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			productOptions.api.setRowData(quot);


		}
		/* 		var line= $('#lineTotal').val();
				var gst= $('#gstRate').val();
				perItemCGST = (parseFloat(line) - (parseFloat(line) / ((parseFloat(gst)+100)/100))) / 2;
				perItemSGST = (parseFloat(line) - (parseFloat(line) / ((parseFloat(gst)+100)/100))) / 2;
				item.itemId = $('#itemName').val();
				item.itemName = $("#proName").val();
				item.hsnCode = $("#hsnCode").val();
				item.quantity = $('#returnQuantity').val();
				item.unitPrice = $('#priceRate').val();
				item.discount = $('#discount').val();
				item.gstRate = $('#gstRate').val();
				item.lineTotal = $('#totalAmount').val();
				item.categoryName = $("#proName").val();
				item.categoryId = $('#itemName').val();
				item.itemCgst = perItemCGST;
				item.itemSgst = perItemSGST;
	
				
				if(editProduct>0){
					item.slNo = editProduct;
				}else{
					item.slNo = data;
				}
				//console.log(item)
				var quot = [];
				
				if (editProduct) {
					var rowNode = productOptions.api.getRowNode(editProduct);
					rowNode.setData(item);
					
					productOptions.api.forEachNode(function(rowNode, index) {
						quot.push(rowNode.data);
					});
				
					var len = quot.length;
				    
					for (var i = 0; i < len; i++) {
					sum = sum + (parseFloat(quot[i].lineTotal));
					var taxType = $("#taxType").val();
					
					if (taxType == "true") {
	
						quot[i].itemIgst = quot[i].lineTotal * quot[i].gstRate/ 100;
						qIGST = qIGST + quot[i].itemIgst;
						grandTotal = sum;
					} else {
					
					var totalGstAmnt = (parseFloat(quot[i].lineTotal) - (parseFloat(quot[i].lineTotal) / ((parseFloat(quot[i].gstRate)+100)/100)));
						quot[i].itemCgst = totalGstAmnt/2;
						quot[i].itemSgst =  totalGstAmnt/2;
						qCGST = qCGST + quot[i].itemCgst;
						qSGST = qSGST + quot[i].itemSgst;
						grandTotal = sum;
						subtotal =(sum-(qCGST + qSGST));
					}
				}
								
				}  else {
					productOptions.api.forEachNode(function(rowNode, index) {
						quot.push(rowNode.data);
					});
					quot.push(item)
					productOptions.api.setRowData(quot);
	
			
				
				var len = quot.length;
					for (var i = 0; i < len; i++) {
					sum = sum + (parseFloat(quot[i].lineTotal));
					var taxType = $("#taxType").val();
	
					if (taxType == "true") {
	
						quot[i].itemIgst = quot[i].lineTotal * quot[i].gstRate/ 100;
						qIGST = qIGST + quot[i].itemIgst;
						grandTotal = sum;
					} else {
					
					var totalGstAmnt = (parseFloat(quot[i].lineTotal) - (parseFloat(quot[i].lineTotal) / ((parseFloat(quot[i].gstRate)+100)/100)));
							
							quot[i].itemCgst = totalGstAmnt/2;
							quot[i].itemSgst =  totalGstAmnt/2;
	
						qCGST = qCGST + quot[i].itemCgst;
						qSGST = qSGST + quot[i].itemSgst;
						grandTotal = sum;
						subtotal =(sum-(qCGST + qSGST));
						
					}
				}
					$("#subTotal").val(subtotal.toFixed(2));
					$("#qIGST").val(qIGST.toFixed(2));
					$("#qCGST").val(qCGST.toFixed(2));
					$("#qSGST").val(qSGST.toFixed(2));
					$("#grandTotal").val(grandTotal.toFixed(2))
				
			} */
		priceCalculation();
		closeNav();

	}

}

function priceCalculation() {
	var grid = [];
	productOptions.api.forEachNode(function(rowNode, index) {

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

	$("#subTotal").val(val);
	$("#total").val(val);
	$("#qIGST").val(val4);
	$("#qCGST").val(val3);
	$("#qSGST").val(val2);
	var gTotal = 0.0;


	gTotal = parseFloat(val) + parseFloat(val2) + parseFloat(val3)
		+ parseFloat(val4)

	$("#grandTotal").val(gTotal.toFixed(2))


}
//add product
/* function saveData() {
	var datas = [];
	
	$("#loader").show();
	$(".overlay:not(#loader)").show()
	$('body,html').css('pointer-events','none');
	
	setTimeout(function() {
		var obj = {};
		
		var item_data = [];
		var qnty=0;
		
		productOptions.api.forEachNode(function(rowNode, indrx) {
			var item_obj = {};
			item_obj.productId = rowNode.data.categoryId;
			item_obj.productName = rowNode.data.categoryName;
			item_obj.quantity = rowNode.data.quantity;
			item_obj.unitPrice = rowNode.data.unitPrice;
			item_obj.discount = rowNode.data.discount;
			item_obj.gstRate = rowNode.data.gstRate;
			item_obj.lineTotal = rowNode.data.lineTotal;
			item_obj.cgst = rowNode.data.itemCgst;
			item_obj.sgst = rowNode.data.itemSgst;
			item_obj.itemIgst = rowNode.data.itemIgst;
			item_obj.taxableAmt = rowNode.data.taxableAmt;
			item_obj.hsnCode = rowNode.data.hsnCode;
			qnty=qnty+parseInt(rowNode.data.quantity);
			item_data.push(item_obj);
		});
		
		
		obj.creditNoteId = $('#creditHeadId').val();
		obj.partyLedgerId = $('#partyLedgerId').val();
		obj.partyLedger = $('#partyLedger').val();
		obj.salesLedgerId = $('#salesLedgerId').val();
		obj.salesLedger = $('#salesLedger').val();
		obj.prtyLedgerCurBal = $('#prtyLedgerCurBal').val();
		obj.salesLedgerCurBal = $('#salesLedgerCurBal').val();
		obj.creditNoteDate= $('#creditNoteDate').val();
		obj.orderNumber= $('#orderNumber').val();
		obj.qSGST= $('#qSGST').val();
		obj.qCGST= $('#qCGST').val();
		obj.qIGST= $('#qIGST').val();
		obj.grandTotal= $('#grandTotal').val();
		obj.subTotal= $('#subTotal').val();
		obj.totalItem=qnty;
		obj.costCenter=$('#costCenter').val();
		obj.description=$('#description').val();
		obj.taxType=$('#taxType').val();
		obj.debitItemAttribute = item_data;
		
		datas.push(obj);
		console.log("Object mapping for Product--------", JSON.stringify(datas));
		saveProduct(datas);
	}, 1000)

} */


function saveData() {
	var obj = {};

	obj.creditNoteId = $('#creditHeadId').val();
	obj.partyLedgerId = $('#partyLedgerId').val();
	obj.partyLedger = $('#partyLedger').val();
	obj.salesLedgerId = $('#salesLedgerId').val();
	obj.salesLedger = $('#salesLedger').val();
	obj.prtyLedgerCurBal = $('#prtyLedgerCurBal').val();
	obj.salesLedgerCurBal = $('#salesLedgerCurBal').val();
	obj.creditNoteDate = $('#creditNoteDate').val();
	obj.orderNumber = $('#orderNumber').val();
	obj.qSGST = $('#qSGST').val();
	obj.qCGST = $('#qCGST').val();
	obj.qIGST = $('#qIGST').val();
	obj.grandTotal = $('#grandTotal').val();
	obj.subTotal = $('#subTotal').val();
	//obj.totalItem=qnty;
	obj.costCenter = $('#costCenter').val();
	obj.description = $('#description').val();
	obj.taxType = $('#taxType').val();
	obj.amount = $('#amount').val();

	console.log("object on add-----------" + JSON.stringify(obj));
	//return false;


	var validation = true;



	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "debit-note-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {

					//$("#add").show();
					//$("#copy").show();
					//$("#delete").show();
					//$("#totalReq").show();
					$("#myGrid").show();
					//$("#searchRowDiv").show();
					$("#demo").hide();

					$('#creditNoteId').text("");
					$('#partyLedgerId').val("");
					$('#partyLedger').val("");
					$('#salesLedgerId').val("");
					$('#salesLedger').val("");
					$('#prtyLedgerCurBal').val("");
					$('#salesLedgerCurBal').val("");
					$('#creditNoteDate').val("");
					$('#orderNumber').val("");
					$('#qSGST').val("");
					$('#qCGST').val("");
					$('#qIGST').val("");
					$('#grandTotal').val("");

					$('#subTotal').val("");
					$('#costCenter').val("");
					$('#description').val("");
					$('#taxType').val("");
					$('#amount').val("");

					agGrid.simpleHttpRequest({
						url: "debit-note-view"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});

				}
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}

/* function saveProduct(datas) {
	$.ajax({
				type : "POST",
				url : "debit-note-add",
				contentType : "application/json",
				data : JSON.stringify(datas),
				success : function(response) {
					if (response.code == "Success") {
						$("#loader").hide();
						$(".overlay:not(#loader)").hide()
						$('body,html').css('pointer-events','auto');
						swal({
							title: "Voucher Status", 
							text: "Debit voucher created Successfully!",
							type: "success"
							}).then(function(){
						   location.reload();
						   }
						);
					} else {

						$("body").removeClass("overlay");
					}
				},
				error : function(data) {
					$('.loader').hide();
					$("body").removeClass("overlay");
				}
			}) 

} */

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}



/*function onQuickFilterChanged() {
	$(".ti-search srchicon").hide();
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}
*/
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

function getPartyLedger() {
	var search = $("#partyLedger").val();
	if (search) {
		$
			.ajax({
				type: "POST",
				url: "debit-note-ledgerList",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.code == "success") {
						//console.log("response data"
						//	+ JSON.stringify(response))
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li   class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
									+ response.body[i].categoryId
									+ '\',\''
									+ response.body[i].categoryName
									+ '\',\''
									+ response.body[i].price
									+ '\',\''
									+ response.body[i].taxType
									+ '\')">'
									+ response.body[i].categoryName
									+ '</li>';
							}
							content += '<li style="margin-left:-30px;" >'
								+ '</li>';
							content += '</ul>';
							////console.log("content " + content)
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;" onClick="selectAutocompleteValuee()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;" '
								+ '</li>';
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);
						}
					}
				},
				error: function(data) {
					//console.log(data);
				}
			})
	}

}


function selectAutocompleteValue1(categoryId, categoryName, price, taxType) {

	if (categoryId) {
		$("#partyLedgerId").val(categoryId);
		$("#partyLedger").val(categoryName);
		$("#prtyLedgerCurBal").val(price);
		$("#taxType").val(taxType);
		$("#suggesstion-box11_").hide();
		hideShowS();

	} else {
		$("#partyLedgerId").val("");
		$("#partyLedger").val("");
		$("#search").val("");
		$("#prtyLedgerCurBal").val("");
		$("#taxType").val(taxType);
		$("#search").attr('data-procat', "");
		$("#suggesstion-box11_").hide();

	}

	getOrderNUmber(categoryId);
}

function selectAutocompleteValuee() {
	$("#partyLedgerId").val("");
	$("#partyLedger").val("");
	$("#prtyLedgerCurBal").val("");
	$("#taxType").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box11_").hide();
}

function hideShowS() {
	var taxType1 = $("#taxType").val();
	if (taxType1 == 'true') {
		$('#igstTR').hide();
		$('#cgstTR').show();
		$('#sgstTR').show();
	} else {
		$('#igstTR').show();
		$('#cgstTR').hide();
		$('#sgstTR').hide();
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
			url: "debit-note-view-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			$('.loader').hide();

			var jsonData = JSON.parse(data.body);
			var allData = jsonData.DebitNoteData;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#totalAmountFooter").val("0.00");
				$("#purchaseRegisterExcel").attr("disabled", true);
				$("#debit_save_btn").removeClass("d-none");
				$("#debit_cancel_btn").removeClass("d-none");
				$("#debit_delete_btn").addClass("d-none");
				debitNoteContainer();
			} else {
				var len = allData.length;
				$("#debit_save_btn").addClass("d-none");
				$("#debit_delete_btn").removeClass("d-none");
				$("#debit_cancel_btn").addClass("d-none");
				$('#totalReq').find('span').html(len);
				$("#purchaseRegisterExcel").attr("disabled", false);
				var rowData = [];
				gridOptions.api.setRowData(rowData);

				gridOptions.api.setRowData(allData);

				if (allData && allData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}
				var totalSum = allData.reduce(function(acc, curr) {
					return acc + parseFloat(curr.grandTotal);
				}, 0);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
			}
		});
	}
}


function downloadExcelFromGrid() {
	var selectedColumns = ['creditNoteId', 'createdOn', 'partyLedger', 'salesLedger',
		'creditNoteDate', 'orderNumber', 'grandTotal', 'useFlag', 'costCenter', 'createdBy'];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Debit Note_Register' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

	var totalAmount = 0;

	var rowData = [];

	gridOptions.api.forEachNodeAfterFilterAndSort(function(node) {
		var data = {};
		selectedColumns.forEach(function(col) {
			var value = node.data[col] || '';
			if (col === 'grandTotal') {
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			if (col === 'useFlag') {
				if (value === "0") {
					value = "Unused"
				}
				else {
					value = "Used"
				}

			}
			data[col] = value;
		});

		rowData.push(data);

		var amount = parseFloat(node.data.grandTotal) || 0;

		totalAmount += amount;
	});

	totalAmount = amountFormatter(totalAmount.toFixed(2));

	var totalRow = {
		creditNoteId: "Total",
		createdOn: "",
		partyLedger: "",
		salesLedger: "",
		creditNoteDate: "",
		orderNumber: "",
		grandTotal: totalAmount,
		useFlag: "",
		costCenter: "",
		createdBy: ""
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedColumns });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'DebitNote Register');
	XLSX.writeFile(wb, fileName);
}





function getPartyLedger() {
	var search = $("#partyLedger").val();
	if (search) {
		$
			.ajax({
				type: "POST",
				url: "credit-note-ledgerList",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.code == "success") {
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li   class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
									+ response.body[i].categoryId
									+ '\',\''
									+ response.body[i].categoryName
									+ '\',\''
									+ response.body[i].price
									+ '\',\''
									+ response.body[i].taxType
									+ '\')">'
									+ response.body[i].categoryName
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
							content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;" onClick="selectAutocompleteValuee()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;" '
								+ '</li>';
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);
						}
					}
				},
				error: function(data) {
				}
			})
	}

}


function selectAutocompleteValue1(categoryId, categoryName, price, taxType) {

	if (categoryId) {
		$("#partyLedgerId").val(categoryId);
		$("#partyLedger").val(categoryName);
		$("#prtyLedgerCurBal").val(price);
		$("#taxType").val(taxType);
		$("#suggesstion-box11_").hide();
		hideShowS();

	} else {
		$("#partyLedgerId").val("");
		$("#partyLedger").val("");
		$("#prtyLedgerCurBal").val("");
		$("#taxType").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box11_").hide();

	}

	getOrderNUmber(categoryId);
}

function selectAutocompleteValuee() {
	$("#partyLedgerId").val("");
	$("#partyLedger").val("");
	$("#prtyLedgerCurBal").val("");
	$("#taxType").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box11_").hide();
}
var txtLen = 0;
function textCount(event) {
	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
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

function hideShowS() {
	var taxType1 = $("#taxType").val();
	if (taxType1 == 'true') {
		$('#igstTR').hide();
		$('#cgstTR').show();
		$('#sgstTR').show();
	} else {
		$('#igstTR').show();
		$('#cgstTR').hide();
		$('#sgstTR').hide();
	}
}


function voucherCreationBtn() {

	gridOptions.api.deselectAll();
	$("#journalVoucher").text('');
	$(".drNoteInfo").hide();
	$(".debit_note_container").removeClass("textareadisable");
	$("#toDateCalendarCREDIT").removeClass("pointeerEventsProperty");
	$("#costCenterCredit").attr("disabled", false);
	$("#adjustment").attr("disabled", false);
	CKEDITOR.instances['descriptionCredit'].setReadOnly(false);
	$("#narration_parent_text_container").removeClass("textareadisable");
	CKEDITOR.instances['descriptionCredit'].setData('');

	/* To Get The Vouche Number */
	$.ajax({
		type: "GET",
		url: "debit-note-vouchernumber",
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#voucherNumber2").text(response.body[0].key);
			}
		}
	});


	$("#add-btn").addClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	$("#debit_save_btn").removeClass("d-none");
	$("#debit_cancel_btn").removeClass("d-none");
	$("#debit_delete_btn").addClass("d-none");

	const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	var weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var d = new Date();
	var month = monthNames[d.getMonth()];
	var day = d.getDate();
	var output = (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + d.getFullYear();
	$("#recieptDate").text(output);
	var a = new Date(output);
	var b = weekday[a.getDay()];
	$("#recieptDay").text(b);

	var dateString = ('0' + d.getDate()).slice(-2) + '-' + ('0' + (d.getMonth() + 1)).slice(-2) + '-' + d.getFullYear();
	var todate = dateString.toString();
	$("#voucherDateCREDIT").val(todate)

	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendarCREDIT").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#voucherDateCREDIT').val($(this).val());
	})

	$('#voucherDateCREDIT').blur(function() {
		$("#toDateCalendarCREDIT").val($(this).val());
	})




	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" id="creditAccountSubGroup_0" placeholder="Search Ledger" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" oninput="formatAmount(this);" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();" autocomplete="off">'
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
		+ '<td ><input type="text" id="debitAccountSubGroup_0" placeholder="Search Ledger" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" oninput="formatAmount(this);" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);


	$("#fromTotalAmount").html("");
	$("#toTotalAmount").html("");
	$("#costCenterCredit").val("").trigger('change');
	$("#descriptionCredit").val("");
	$("#narration_parent_text_container").removeClass("textareadisable");

}

function cancelVoucherBtn() {
	$("#fyearDiv").show();
	$("#fdateDiv").show();
	$("#tdateDiv").show();
	$("#filterDiv").show();
	$("#totalReq").show();
	$("#searchRowDiv").show();
	$("#myGrid").show();
	$("#reqDltBtn").show();
	$("#totalAmountFooterDiv").show();
	$("#newBtn").show();
	$("#asAvoucherBtn").show();
	$(".innercontent").hide();

}



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
}

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
	getFromSubtotal(); // If needed
}



//function for auto search for the credit part
function creditSubGroup(id) {
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
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\',\'' + response.body[i].cvDescription + '\',\'' + response.body[i].contraVoucherType + '\',\'' + response.body[i].createdBy + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					//	content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);

				}
				else {
					console.log("else: " + response);
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					//	content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
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

	$("#amountDivId").val(id);
}

function autocompleteValue2(sGroupId, sGroupName, id, amount, taxType, multiPayType, vendorId) {
	var l = id.split("_");
	var counter = l[1];

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
		$("#creditAccountSubGroup_" + counter).val(sGroupName + '( ' + sGroupId + ' )');
		$("#creditAccountGroupId_" + counter).val(sGroupId);
		$("#creditCurrentBalance_" + counter).text(amount);
		$("#creditAccountSubGroup_" + counter).attr('data-procat', sGroupName);
		$("#suggesstion-box2_" + counter).hide();

	} else {
		$("#creditAccountSubGroup_" + counter).val("");
		$("#creditAccountGroupId_" + counter).val("");
		$("#creditCurrentBalance_" + counter).text("");
		$("#creditAccountSubGroup_" + counter).attr('data-procat', "");
		$("#suggesstion-box2_" + counter).hide();
	}
}

/* Function TO Search For The Debit Part Ledger Name */

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
					//	content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
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
	$("#taxType").val(taxType);
	var totalSelectedAmount = 0;

	console.log("TaxType-->", taxType)
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
				$("#myModalTaxDeduction").modal('show');
				$("#totalTDSAmount").val(amountFormatter(totalSelectedAmount));
			}
		}

	});

}

function autocompleteValue1(sGroupId, sGroupName, id, amount, taxType, multiPayType, vendorId) {
	var l = id.split("_");
	var counter = l[1];

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
	$('.loader').show();
	$("#transAmount").val('');

	$.ajax({
		type: "GET",
		url: "payment-voucher-get-invoiceList?id=" + vendorId,
		async: true,
		success: function(response) {
			// Check if response and response.body are valid
			if (response && response.body) {
				var jsondata = JSON.parse(response.body);
				var alldata = jsondata.InvoiceDetails || [];
				var newData = [];

				$.each(alldata, function(index, obj) {
					if (obj.payStatus !== "Fully Paid") {
						newData.push(obj);
					}
				});

				// Check if gridOptions3.api is defined
				if (gridOptions3 && gridOptions3.api) {
					gridOptions3.api.setRowData(newData);
				} else {
					console.error('Grid API is not available.');
				}

				var dataLength = newData.length;
				$("#totalInvoice").find('span').html(dataLength);
				$('.loader').hide();
				$("#modAdjustment").val("againstRef");
				$("#invoiceListPopup").modal('show');

				$("#parentTransNameDiv").hide();
				$("#transNameDiv").hide();
				checkModeOfPay();
				$("#totalPaymentAmount").val("0.00");
			} else {
				console.error('Invalid response received.');
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX request failed:', status, error);
			$('.loader').hide();
		}
	});
}

function checkModeOfPay() {
	var modeVal = $("#modAdjustment").val();

	if (modeVal === "againstRef") {

		$("#transNameDiv").hide();
		$("#transAmountDiv").hide();
		$("#multipaymentgrid").show();
		$(".totalPaymentAmountDiv").show();
	}
	else {
		if (modeVal == "advance" || modeVal == "newRef") {
			$("#parentTransNameDiv").show();
			$("#transNameDiv").show();
			$("#transAmountDiv").show();
			$("#multipaymentgrid").hide();
			$(".totalPaymentAmountDiv").hide();
		}
		else {
			if (modeVal == "onAcc") {
				$("#parentTransNameDiv").hide();
				$("#transNameDiv").hide();
				$("#transAmountDiv").show();
				$(".totalPaymentAmountDiv").hide();

			}
		}
	}
}


function cancelModal() {
	$('#invoiceListPopup').modal('hide');
}



/* function to convert amount into indian currency format */

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

function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	value = value.replace(/,/g, '');
	var floatValue = (parseFloat(value).toFixed(2));
	if (floatValue > 0) {
		//$("#" + id).val(amountFormatter(floatValue));
		//$("#creditAmount_0").val(amountFormatter(floatValue))
		getToSubtotal();
	} else {
		//$("#" + id).val(0.00);
		//$("#creditAmount_0").val(0.00);

	}
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




/* Function To Save Btn  */

function creditNoteVoucherSave() {

	var voucherDate = $("#voucherDateCREDIT").val();
	console.log($("#costCenterCredit").val());
	var dataset = [];
	item = {};

	$("#descriptionCredit").val(CKEDITOR.instances.descriptionCredit.getData());

	$("#debitTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenterCredit").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#fromTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#descriptionCredit").val();
		item['voucherDate'] = voucherDate
		item['fromAccountSubGroup'] = $(this).find(".debitAccountGroupIdCls").val();
		item['fromName'] = $(this).find(".debitNameCls").val();
		item['fromAmount'] = $(this).find(".debitAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "CREDITNOTE";
		item['methodOfNote'] = $("#adjustment").val();
		dataset.push(item);
	});//table tbody tr loop ends
	$("#creditTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenterCredit").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#toTotalAmount").text().replace(/,/g, '');
		item['description'] = $("#descriptionCredit").val();
		item['voucherDate'] = voucherDate;
		item['toAccountSubGroup'] = $(this).find(".creditAccountGroupIdCls").val();
		item['toName'] = $(this).find(".creditNameCls").val();
		item['toAmount'] = $(this).find(".creditAmountCls").val().replace(/,/g, '');
		item['voucherType'] = "CREDITNOTE";
		item['methodOfNote'] = $("#adjustment").val();
		dataset.push(item);
	});//table tbody tr loop ends


	//console.log("add journal dataset----------------------"+JSON.stringify(dataset));
	//return false;
	console.log("dataset----->", dataset);
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
		if ($("#costCenterCredit").val() === "") {
			toastr.error("Please Select Cost Center Name");
			return false; // Stop the program
		}

		// Check #description
		if ($("#adjustment").val() === "") {
			toastr.error("Please Enter adjustment");
			return false; // Stop the program
		}

		if ($("#descriptionCredit").val() === "") {
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
				url: "debit-note-addDebitNoteVoucher",
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
					if (response.message == "Success") {
						toastr.success("Debit Note voucher created successfully.");
						setTimeout(() => {
							viewFilteredData();
						}, 1000);
						$("#debit_save_btn").addClass("d-none");
						/*swal({
							title: "Voucher Status",
							text: "Debit Note voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/debit-note";
						})*/
					} else {
						toastr.error(response.message);
					}
				}, error: function(response) {
					toastr.error(response.code);
				}
			}) //ajax ends
		}
	})
}

var selectedInvoiceid = [];
var advanceReceiveDetails = [];
function SaveFinalAmount() {

	var selectedRows = gridOptions3.api.getSelectedRows();
	var vendorid = $("#ledgerNameModalId").val();
	var sellerLdgerid = $("#debitLegderId").val();

	selectedRows.forEach(rows => {
		selectedInvoiceid.push({
			invoiceId: rows.invoiceId,
			outstandingAmount: rows.outstandingAmount,
			vendorId: vendorid,
			legerid: sellerLdgerid

		});
	});

	var divId = $("#amountDivId").val();

	console.log("selectedInvoiceid-->", selectedInvoiceid);
	var advAmount = $("#transAmount").val();
	var indexNumber = divId.charAt(divId.length - 1);

	var receiptLedgerId = $("#debitAccountGroupId_" + indexNumber).val();
	var receiptMsg = $("#transName").val();
	var rcvType = $("#modAdjustment").val();

	var receiptAmount = $("#transAmount").val();
	advanceReceiveDetails.push({
		rcvLedgerId: receiptLedgerId,
		receiveMsg: receiptMsg,
		receiptAmount: receiptAmount.replace(/,/g, '')
	});

	if ($("#modAdjustment").val() == "againstRef") {
		$("#" + "debitAmount_" + indexNumber).val($("#totalPaymentAmount").val());





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

}



function editItems() {
	$("#add-btn").addClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
	$("#debit_save_btn").removeClass("d-none");
	$("#debit_cancel_btn").removeClass("d-none");
	$("#debit_delete_btn").addClass("d-none");

	$(".debit_note_container").removeClass("textareadisable");
	$("#toDateCalendarCREDIT").removeClass("pointeerEventsProperty");

	$("#costCenterCredit").attr("disabled", false);
	$("#adjustment").attr("disabled", false);
	$("#narration_parent_text_container").removeClass("textareadisable");
	CKEDITOR.instances['descriptionCredit'].setReadOnly(false);
}


function debitNoteCancel() {

	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#debit_save_btn").addClass("d-none");
		$("#debit_cancel_btn").addClass("d-none");
		$("#debit_delete_btn").removeClass("d-none");
		$(".debit_note_container").addClass("textareadisable");

		$("#costCenterCredit").attr("disabled", true);
		$("#adjustment").attr("disabled", true);
		$("#narration_parent_text_container").addClass("textareadisable");
		$("#toDateCalendarCREDIT").addClass("pointeerEventsProperty");
		$(".drNoteInfo").removeClass("d-none");
		CKEDITOR.instances['descriptionCredit'].setReadOnly(true);
	}
	else {
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").addClass("d-none");
		$("#debit_save_btn").addClass("d-none");
		$("#debit_cancel_btn").addClass("d-none");
		$("#debit_delete_btn").addClass("d-none");
		$(".debit_note_container").addClass("textareadisable");

		$("#costCenterCredit").attr("disabled", true);
		$("#adjustment").attr("disabled", true);
		$("#toDateCalendarCREDIT").addClass("pointeerEventsProperty");
		$("#narration_parent_text_container").addClass("textareadisable");
		$(".drNoteInfo").addClass("d-none");
		CKEDITOR.instances['descriptionCredit'].setReadOnly(true);

	}
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

