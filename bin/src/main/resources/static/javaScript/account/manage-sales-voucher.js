$(function() {
	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

$("#editLedgerButton").attr("disabled",true);
	$("#viewItemDtlsSecId").addClass("d-none");
	$("#itemLedgerId").select2();


	gridOptions.api.setRowData("");
	//$("#TDS").hide();
	$("#voucherclass").hide();
	/* 		agGrid.simpleHttpRequest({
				url : "sales-voucher-view"
			}).then(function(data) {
	
				console.log(JSON.stringify(data))
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.viewSalesVoucher;
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				gridOptions.api.setRowData(allData);
				
			}); */
	/* current date filter data */



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
	$("#purchaseDate").text(output);
	var a = new Date(output);
	var b = weekday[a.getDay()];
	$("#purchaseDay").text(b);

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);
	itemOptions.api.setRowData();


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


	/* var datee=$("#orderStatusFilter").val();
	
	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
		//minDate : 0,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})
	
	$("#toDateCalendar2").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
		//minDate : 0,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar2").val($(this).val());
	}) */

	viewFilteredData();
});

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
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);
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

// column Defs
const columnDefs =
	[
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			pinned: 'left',
			sortable: false,
			filter: false,
			resizable: true
		},

		{
			headerName: 'SALES VOUCHER ID',
			field: "journalVoucher",
			pinned: 'left',
			hide: true,

		},
		{
			headerName: "VOUCHER DATE",
			field: "createdOn",
			width: 150,
			pinned: 'left',
			hide: true,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "COMPANY VOUCHER ID",
			field: "orgVoucherId",
			width: 200,
			pinned: 'left',
			hide: true,
			cellStyle: {
				textAlign: 'left'
			},
			cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.invoiceId
					+ '") href="javascript:void(0)">'
					+ params.data.orgVoucherId + ' <i class="fa fa-edit"></i></a>';
			}
		},
		{
			headerName: "Date",
			field: "TransactionDate",
			width: 150,
			cellStyle: {
				textAlign: 'left'
			},
			/*	cellRenderer: function(params) {
					return '<a onclick=editPage("'
						+ params.data.invoiceId
						+ '") href="javascript:void(0)">'
						+ params.data.TransactionDate + ' <i class="fa fa-edit"></i></a>';
				}*/
		},

		{
			headerName: "INVOICE NUMBER",
			field: "sapid",
			width: 150,
			hide: true,
			pinned: 'left',
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: "TAX INVOICE NUMBER",
			field: "invoiceId",
			pinned: 'left',
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "BILLING DATE",
			field: "invDate",
			width: 150,
			//pinned: 'left',
			cellStyle: {
				textAlign: 'left'
			}
		}, {
			headerName: "VENDOR NAME",
			field: "vendorName",
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: "GSTIN NO",
			field: "gstno",
			width: 150,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "DESTINATION STATE",
			field: "stateCode",
			width: 150,
			hide: true,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "MATERIAL CODE",
			field: "sku",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'left'
			},
			cellRenderer: customCellRenderer
		},
		{
			headerName: "MATERIAL DESCRIPTION",
			field: "itemname",
			hide: true,
			width: 250,
			cellStyle: {
				textAlign: 'left'
			},
			cellRenderer: customCellRenderer
		}, {
			headerName: "HSN CODE",
			field: "hsncode",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'left'
			},
			cellRenderer: customCellRenderer
		},

		{
			headerName: "COST CENTER NAME",
			field: "costCenter",
			hide: true,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Quantity",
			field: "qnt",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				if (params.value && typeof params.value === 'string') {
					const values = params.value.split(',').map((value, index) => {
						var qntyValue = value.split(' ')[0];
						var qntType = value.split(' ')[1]
						return `<span style="color: #3467af;  font-size:12px; font-weight:700;">(${index + 1})</span> ${amountFormatter(qntyValue)} ${qntType}`;
					});
					return values.join(', ');
				}
			}
		},
		{
			headerName: "Quantity(Case)",
			field: "weightInCase",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				if (params.value && typeof params.value === 'string') {
					const values = params.value.split(',').map((value, index) => {
						var qntyValue = value.split(' ')[0];
						return `<span style="color: #3467af;  font-size:12px; font-weight:700;">(${index + 1})</span> ${amountFormatter(qntyValue)}`;
					});
					return values.join(', ');
				}
			}
		},
		{
			headerName: "Quantity(KG)",
			field: "weight",
			hide: true,
			width: 120,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				if (params.value && typeof params.value === 'string') {
					const values = params.value.split(',').map((value, index) => {
						var qntyValue = value.split(' ')[0];
						var qntType = value.split(' ')[1]
						return `<span style="color: #3467af;  font-size:12px; font-weight:700;">(${index + 1})</span> ${amountFormatter(qntyValue)} ${qntType}`;
					});
					return values.join(', ');
				}
			}
		},
		{
			headerName: "GST RATE",
			field: "gstrate",
			hide: true,
			width: 100,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "TAXABLE AMOUNT",
			field: "Taxableamt",
			width: 150,
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "IGST",
			field: "igst",
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
			headerName: "CGST",
			field: "cgst",
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
			headerName: "SGST",
			field: "sgst",
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
			headerName: "INVOICE VALUE",
			field: "invoicevalue",
			width: 150,
			valueFormatter: params => params.data.totalAmount.toFixed(2),
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "TDS",
			field: "TdsAmount",
			width: 150,
			// valueFormatter: params => params.data.totalAmount.toFixed(2),
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "TCS",
			field: "tcsAmount",
			width: 150,
			// valueFormatter: params => params.data.totalAmount.toFixed(2),
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},
		{
			headerName: "NET RECEIVABLE",
			field: "NetAmount",
			width: 150,
			valueFormatter: params => params.data.totalAmount.toFixed(2),
			cellStyle: {
				textAlign: 'right'
			},
			cellRenderer: function(params) {
				var value = params.value;
				return amountFormatter(value);
			}
		},

		{
			headerName: "Invoice Status",
			field: "invoicePayStatus",
			width: 150,
			hide:true,
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
			}
		},
		{
			headerName: "Voucher Type",
			field: "voucherType",
			width: 150,
			hide:true,
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
			hide:true,
			cellStyle: {
				textAlign: 'left',
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
	];


const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
		height: 10
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
		field: "slNo",
		width: 70,
	}, {

		headerName: 'HSN CODE',
		field: "hsnCode",
		width: 100,
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.hsnCode
				+ '</div>';
		}
	},
	{
		headerName: 'Item Name',
		field: "productName",
		width: 170,
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.productName
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
		field: "qunatity",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div >'
				+ amountFormatter(parseFloat(params.data.qunatity).toFixed(2)) + " " + params.data.unit
				+ '</div>';
		}
	}, {
		headerName: "Quantity(KG)",
		field: "itemWeightKg",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			if (params.value && typeof params.value === 'string') {
				const values = params.value.split(',').map((value, index) => {
					var qntyValue = value.split(' ')[0];
					var qntType = value.split(' ')[1]
					return `${amountFormatter(qntyValue)} ${qntType}`;
				});
				return values.join(', ');
			}
		}
	}, {
		headerName: "Quantity(Case)",
		field: "itemWeightCase",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		}
	},


		/* {
			headerName : 'Unit',
			field : "unit",
			width : 180,
			cellRenderer : function(params) {
				return '<div>'
				+ params.data.unit 
				+ '</div>';
         	}
		}, */ {
		headerName: 'Unit Price',
		field: "unitPrice",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.unitPrice).toFixed(2))
				+ '</div>';
		}
	},
	{
		headerName: 'Discount',
		field: "discount",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.discount).toFixed(2))
				+ '</div>';
		}
	},
	{
		headerName: 'Taxable Amount',
		field: "amount",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.amount).toFixed(2))
				+ '</div>';
		}
	}, 
	{
		headerName: 'GST Rate',
		field: "gstRate",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.gstRate).toFixed(2))
				+ '</div>';
		}
	}, 
	{
		headerName: 'CGST',
		field: "cgst",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.cgst).toFixed(2))
				+ '</div>';
		}
	}, {
		headerName: 'SGST',
		field: "sgst",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.sgst).toFixed(2))
				+ '</div>';
		}
	}, {
		headerName: 'IGST',
		field: "igst",
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.igst).toFixed(2))
				+ '</div>';
		}
	}, {
		headerName: 'Total Amount',
		field: "taxAbleAmount",
		width: 110,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div>'
				+ amountFormatter(parseFloat(params.data.taxAbleAmount).toFixed(2))
				+ '</div>';
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
	/*getRowNodeId: function(data) {
		return data.invItemId;
	}*/
};

function rowSelectItem() {

	var selectedRows = itemOptions.api.getSelectedRows();

	if (selectedRows && selectedRows.length > 0) {
		$("#editLedgerButton").attr("disabled",false);
		
	} else {
		$("#editLedgerButton").attr("disabled",true);
	}
}

var deleteId = "";
var gridSelectedValue;
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	console.log("Selectedrows-->", selectedRows);
nextBtnFunction('manageControl');
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
		var salesId = selectedRows[0].invoiceId;
		$("#taxInvoiceNumber").text(salesId);
		$(".tax_invoiceInfo").removeClass("d-none");
		$("#voucherClass").attr("disabled", false);
		$("#tdsbtn").removeAttr("disabled");
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		gridSelectedValue = selectedRows;
		if (parseFloat(gridSelectedValue[0].salesTds).toFixed(2) > 0) {
			$('#tdsbtn').attr("disabled", true);
		}
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		$('#normalPayment').attr("disabled", true);
		editPage(salesId);
		//Logic To Hide Show the Tds Button
		if (selectedRows[0].TdsAmount > 0) {
			//$("#TDSTab").hide();
			$("#next-btn-voucherclass").hide();
		}
		else {
			$("#TDSTab").show();
			$("#next-btn-voucherclass").show();
		}
		
		$("#sellerName").val(selectedRows[0]?.vendorName || '');
		$("#sellerNameId").val(selectedRows[0]?.vendorId || '');
		$("#productGrnId").val(selectedRows[0]?.invoiceId || '');
		$("#partyGSTNo").val(selectedRows[0]?.gstno || '');
		$("#partyPANNo").val(selectedRows[0]?.panno || '');
		$("#tdsAmount").val(selectedRows[0]?.TdsAmount || '0.00');
		$("#vchNarration").val(selectedRows[0]?.narration || '');
		
		if(selectedRows[0]?.approveStatus == 'APPROVED') {
			$("#parent_apv_btn,#saveNarration,#saveTdsBtn,#editLedgerButton").addClass("d-none");
			$("#vchNarration,#payTdsAmount,#tdsLedgerId,#offAmount").attr("disabled",true);
		} else {
			$("#parent_apv_btn,#saveNarration,#saveTdsBtn,#editLedgerButton").removeClass("d-none");
			$("#vchNarration,#payTdsAmount,#tdsLedgerId,#offAmount").attr("disabled",false);
		}
	} else {
		$("#voucherClass").attr("disabled", true);
		$("#tdsbtn").prop("disabled", true);
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
		$('#normalPayment').attr("disabled", false);
		$("#taxInvoiceNumber").text(salesId);
		$(".tax_invoiceInfo").addClass("d-none");
		
		$("#sellerName").val('');
		$("#sellerNameId").val('');
		$("#productGrnId").val('');
		$("#partyGSTNo").val('');
		$("#partyPANNo").val('');
		$("#tdsAmount").val('0.00');
		$("#vchNarration").val('');
	}
	/* 	
		if(voucherType=='SALE'){
			$("#voucherClass").attr("disabled", false);
		}
		else{
			$("#voucherClass").attr("disabled", true);
		} */
}
// for new button
function newBtn() {
	$("#voucherClass").hide();
	$("#approve").hide();
	$("#return").hide();
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$('#totalAmountFooterDiv').hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
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
	$("#dwnldExcel").hide();
	$("#salesRegisterPdf").hide();
	$("#demo").show();
	getVoucherNumber();
}
// for cancel button
function cancelBtn() {
	$("#copy").show();
	$("#voucherClass").attr("disabled", true);
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
	$("#normalPayment").show();
	$("#salesRegisterPdf").removeClass('hideBtn');
	$("#dwnldExcel").removeClass('hideBtn');
	$("#dwnldExcel").show();
	$("#salesRegisterPdf").show();


	viewFilteredData();
	$("#tdsbtn").show();
	$("#tdsbtn").attr("disabled", true);
	$("#voucherClass").show();
	$("#voucherClass").attr("disabled", true);
}



// Edit & stage change 
function editPage(id) {

	/*var editId = id.split(",");

	var salesId = editId[0];
*/
	//	var modal = editId[1];

	//$("#add").hide();
	//$("#return").hide();
	//$("#approve").hide();
	//$("#copy").hide();
	//$("#delete").hide();
	//$("#myGrid").hide();
	//$("#searchRowDiv").hide();
	//$("#totalReq").hide();
	//$("#statusDiv").hide();
	//$("#idDiv").hide();
	//$('#totalAmountFooterDiv').hide();
	//$("#collapseFour").hide();
	//$("#headingFour").hide();
	//$("#myGridActivity").hide();
	//$(".container").hide();
	//$("#demo").show();
	//$("#fdateDiv").hide();
	//$("#tdateDiv").hide();
	//$("#fyearDiv").hide();
	//$("#filterDiv").hide();
	//$("#normalPayment").hide();
	//$("#salesRegisterPdf").addClass('hideBtn');
	//$("#dwnldExcel").addClass('hideBtn');
	//$("#addNew").hide();
	//$("#voucherClass").hide();
	//$("#tdsbtn").hide();

	//	alert('journalVoucherId------'+journalVoucher);

	$.ajax({
		type: "GET",
		url: "sales-voucher-edit?id=" + id,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				let jsonData = response.body;
				var productDetails = jsonData[0].productList;
				let totalProductQuantity = 0.00;
				$('#buyerName').val(response.body[0].buyerName);
				$('#buyerId').val(response.body[0].buyerNameId);
				//$('#sellerName').val(response.body[0].sellerName);
				//$('#sellerNameId').val(response.body[0].sellerNameId);
				$('#orderDate').text(response.body[0].purchaseDate);
				$('#purchaseNumber').text(response.body[0].purchaseId);
				$('#orderDate').val(response.body[0].purchaseDate);

				$('#vType').val(response.body[0].vType);
				$('#vClass').val(response.body[0].vClass);

				$('#subTotal').val(amountFormatter(parseFloat(response.body[0].subTotal).toFixed(2)));
				$('#qSGST').val(amountFormatter(parseFloat(response.body[0].sgst).toFixed(2)));
				$('#qCGST').val(amountFormatter(parseFloat(response.body[0].cgst).toFixed(2)));
				$('#qIGST').val(amountFormatter(parseFloat(response.body[0].igst).toFixed(2)));
				$('#grandTotal').val(amountFormatter(parseFloat(response.body[0].totalAmount).toFixed(2)));
				$("#voucherId").val(response.body[0].voucherId);
				$("#voucherDate").val(response.body[0].voucherDate);
				$("#purchaseNumber").val(response.body[0].purchaseId);
				$("#invoiceDate").val(response.body[0].invoiceDate);
				$("#productSapId").val(response.body[0].sapId);
				$("#totalQuantUnit").val(response.body[0].totalQuantUnit);
				$("#totalQuantKg").val(response.body[0].totalQuantKg);
				$("#totalQuantCase").val(response.body[0].totalQuantCase);
				$("#totalItemCount").val(response.body[0].totalItemCount);
				$("#tdsValue").val(amountFormatter(parseFloat(response.body[0].tdsValue).toFixed(2)));
				$("#tcsValue").val(amountFormatter(parseFloat(response.body[0].tcsValue).toFixed(2)));
				$("#tdsRate").val(response.body[0].tdsRate);
				var nr = response.body[0].netReceivable;
				if (nr == '0.00') {
					$("#netReceivable").val(amountFormatter(parseFloat(response.body[0].totalAmount).toFixed(2)));
				}
				else {
					$("#netReceivable").val(amountFormatter(parseFloat(nr).toFixed(2)));
				}

				productDetails.map((element, index) => {
					let elementQuantity = element.qunatity;
					quantityUnit = element.unit;
					totalProductQuantity += parseFloat(elementQuantity);
				})

				$("#totalQuantity").val(totalProductQuantity);
				itemOptions.api.setRowData(response.body[0].productList);

			}
		}
	});


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

function viewFilteredData(vchId="") {
	$('.loader').show();
	var fromDateFilter = $("#fromDate").val();
	var toDateFilter = $("#toDate").val();
	var validation = true;
	var totalIGSTAmount = 0.00;
	var totalCGSTAmount = 0.00;
	var totalSGSTAmount = 0.00;
	var totalPayableAmount = 0.00;
	var totalTdsAmount = 0.00;
	var totalTcsAmount = 0.00;
	var totalNetRecvAmount = 0.00;
	if (fromDateFilter == null || fromDateFilter == "") {
		toastr.error("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		toastr.error("Please Provide To Date");
		validation = false;
	}
	if (validation) {
		agGrid.simpleHttpRequest({
			url: "sales-voucher-view-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			$('.loader').hide();

			var jsonData = JSON.parse(data.body);
			var allData = jsonData?.viewSalesVoucher;
			var allDataQuantity = jsonData?.getSumTotalFooter;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#salesRegisterPdf").prop("disabled", true);
				$("#dwnldExcel").prop("disabled", true);
				$("#totalAmountFooter").val("");
				$("#totalIGSTAmount").val("");
				$("#totalCGSTAmount").val("");
				$("#totalSGSTAmount").val("");
				$("#totalPayableAmount").val("");
				$("#totalQuantityUnitFooter").val("");
				$("#totalQuantityKgFooter").val("");
				$("#totalQuantityCaseFooter").val("");


				$(".innercontent,#parent_apv_btn").addClass("d-none");
				$("#no_purchase_voucher").removeClass("d-none");
				$("#next-btn-1").hide();
				$("#voucherNarrationTab").hide();
				$("#TDSTab").hide();
				$("#vchNarration").val('');
			} else {
				$(".innercontent").removeClass("d-none");
				$("#no_purchase_voucher").addClass("d-none");
				$("#next-btn-1").show();
				$("#voucherNarrationTab").show();
				$("#TDSTab").show();
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				var rowData = [];
				gridOptions.api.setRowData(rowData);

				gridOptions.api.setRowData(allData);

				if (allData && allData.length > 0) {
					if(vchId) {
						const rowNode = gridOptions.api.getRowNode(vchId);
						if (rowNode) {
						  rowNode.setSelected(true);
						}
						
					} else {
						gridOptions.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true); // Select the first row
							}
						});
					}
				}
				var totalSum = allData.reduce(function(acc, curr) {
					return acc + parseFloat(curr.Taxableamt);
				}, 0);
				for (var i = 0; i < len; i++) {
					totalIGSTAmount = totalIGSTAmount + parseFloat(allData[i].igst);
					totalCGSTAmount = totalCGSTAmount + parseFloat(allData[i].cgst);
					totalSGSTAmount = totalSGSTAmount + parseFloat(allData[i].sgst);
					totalPayableAmount = totalPayableAmount + parseFloat(allData[i].invoicevalue);
					totalTdsAmount = totalTdsAmount + parseFloat(allData[i].TdsAmount);
					totalNetRecvAmount = totalNetRecvAmount + parseFloat(allData[i].NetAmount);
					totalTcsAmount = totalTcsAmount + parseFloat(allData[i].tcsAmount);
				}
				$("#salesRegisterPdf").prop("disabled", false);
				$("#dwnldExcel").prop("disabled", false);
				var amountVal = amountFormatter(totalSum.toFixed(2))
				$("#totalAmountFooter").val(amountVal);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
				$("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
				$("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
				$("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
				$("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
				$("#totalQuantityUnitFooter").val(allDataQuantity[0].totalQuantityUnitFooter);
				$("#totalQuantityKgFooter").val(allDataQuantity[0].totalQuantityKgFooter);
				$("#totalQuantityCaseFooter").val(allDataQuantity[0].totalQuantityCaseFooter);
				$("#totalTdsAmount").val(amountFormatter(totalTdsAmount.toFixed(2)));
				$("#totalNetRcvAmount").val(amountFormatter(totalNetRecvAmount.toFixed(2)))
				$("#totalTcsAmount").val(amountFormatter(totalTcsAmount.toFixed(2)))
			}
		});
	}
}

function downloadExcelFromGrid() {
	var selectedHeaders = [
		'TAX INVOICE NUMBER', 'Date', 'BILLING DATE', 'VENDOR NAME',
		'MATERIAL CODE', 'HSN CODE', 'Quantity', 'Quantity(Case)', 'Quantity(KG)',
		'GST RATE', 'TAXABLE AMOUNT', 'IGST', 'cGST', 'SGST', 'TDS', 'NET RECEIVABLE'
	];
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Sales_Voucher_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

	var totalTaxableAmount = 0;
	var totalIGST = 0;
	var totalCGST = 0;
	var totalSGST = 0;
	var Invoicvalue = 0;
	var tds = 0;
	var netReceivable = 0;

	var rowData = [];
	var fieldMap = {};
	columnDefs.forEach(function(colDef) {
		fieldMap[colDef.headerName] = colDef.field;
	});

	gridOptions.api.forEachNodeAfterFilterAndSort(function(node) {
		var data = {};
		selectedHeaders.forEach(function(header) {
			var field = fieldMap[header];
			var value = node.data[field] || '';
			if (['Taxableamt', 'igst', 'cgst', 'sgst', 'invoicevalue', 'salesTds', 'NetReceivable'].includes(field)) {
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});
		rowData.push(data);

		var taxableAmount = parseFloat(node.data[fieldMap['TAXABLE AMOUNT']]) || 0;
		var IGST = parseFloat(node.data[fieldMap['IGST']]) || 0;
		var CGST = parseFloat(node.data[fieldMap['cGST']]) || 0;
		var SGST = parseFloat(node.data[fieldMap['SGST']]) || 0;
		var invoiceVal = parseFloat(node.data[fieldMap['TOTAL VALUE']]) || 0;
		var tdsVal = parseFloat(node.data[fieldMap['TDS']]) || 0;
		var receivableValue = parseFloat(node.data[fieldMap['NET RECEIVABLE']]) || 0;

		totalTaxableAmount += taxableAmount;
		totalIGST += IGST;
		totalSGST += SGST;
		totalCGST += CGST;
		Invoicvalue += invoiceVal;
		tds += tdsVal;
		netReceivable += receivableValue;

	});

	totalTaxableAmount = amountFormatter(totalTaxableAmount.toFixed(2));
	totalIGST = amountFormatter(totalIGST.toFixed(2));
	totalSGST = amountFormatter(totalSGST.toFixed(2));
	totalCGST = amountFormatter(totalCGST.toFixed(2));
	Invoicvalue = amountFormatter(Invoicvalue.toFixed(2));
	tds = amountFormatter(tds.toFixed(2));
	netReceivable = amountFormatter(netReceivable.toFixed(2));

	var totalRow = {
		'TAX INVOICE NUMBER': "Total",
		'Date': "",
		'BILLING DATE': "",
		'VENDOR NAME': "",
		'MATERIAL CODE': "",
		'HSN CODE': "",
		'Quantity': "",
		'Quantity(Case)': "",
		'Quantity(KG)': "",
		'GST RATE': "",
		'TAXABLE AMOUNT': totalTaxableAmount,
		'IGST': totalIGST,
		'cGST': totalCGST,
		'SGST': totalSGST,
		//'TOTAL VALUE': Invoicvalue,
		'TDS': tds,
		'NET RECEIVABLE': netReceivable
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	const headerStyle = {
		font: { bold: true },
		fill: { fgColor: { rgb: "FFFF00" } }, // Yellow background
		border: {
			top: { style: "thin" },
			bottom: { style: "thin" },
			left: { style: "thin" },
			right: { style: "thin" }
		}
	};

	// Apply header styles
	for (let i = 0; i < selectedHeaders.length; i++) {
		const cellAddress = XLSX.utils.encode_cell({ r: 0, c: i });
		if (!ws[cellAddress]) ws[cellAddress] = {};
		ws[cellAddress].s = headerStyle;
	}
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Sales Voucher');
	XLSX.writeFile(wb, fileName);
}


function generatePdfSalesRegister() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var voucherType = "SALE";
	var activityType = "SALE VOUCHER";
	window.open("/account/sales-register-Pdf?voucherType=" + voucherType + "&activityType=" + activityType + "&fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}

function showTdsModal() {

	//$('#invoiceListPopup').modal('show');
	$("#vendorInvoiceList").empty();
	$("#pVendorid").val('');
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();

	$("#tdsTransactionDate").val(todate);


	var selectedValue = gridSelectedValue;

	var journalVoucher = selectedValue[0].journalVoucher;
	var invoiceid = selectedValue[0].invoiceId;
	$.ajax({
		type: "GET",
		url: "sales-voucher-view-TdsAmount?voucherid=" + journalVoucher + "&invoiceid=" + invoiceid,
		async: false,
		success: function(response) {
			var data = JSON.parse(response.body);

			let selectedRows = gridOptions.api.getSelectedRows();
			let selTDSAmnt = '0.0';
			if(selectedRows && selectedRows.length > 0) {
				selTDSAmnt = selectedRows[0]?.TdsAmount || '0.0';
			}
			

			let tdsamnt = (selTDSAmnt != '0.0' && selTDSAmnt != '0.00' && selTDSAmnt != 0) ? selTDSAmnt : data.vendorTdsAmount[0].TDS;

			
			$("#tdsLedgerId").val(selectedRows[0]?.tdsLedgerId || '')

			selectedValue[0]['tds'] = tdsamnt;
			
			$("#pVendorid").val(data?.vendorTdsAmount[0]?.VENDORID);
			$("#tdsPercentageAmt").text(data?.vendorTdsAmount[0]?.TDSPERCENTAGE + '%')
			$("#PercentageTDS").text(data?.vendorTdsAmount[0]?.TDSPERCENTAGE + '%')
			$("#tdsTransactionDate").val(data?.vendorTdsAmount[0]?.invoicedate);
			let offAmnt = data.vendorTdsAmount[0]?.roundOff || '0';
			$("#offAmount").val(offAmnt);
		}
	})

	for (var i = 0; i < selectedValue.length; i++) {
		var amount = parseFloat(selectedValue[i].invoicevalue);
		$("#ledgerNameModal").html(selectedValue[i].vendorName);
		/* var tdsPercentage = parseFloat(selectedValue[i].tds);  */
		var tdsDecimal = parseFloat(selectedValue[i].tds);
		/* var tdsAmount = amount * tdsDecimal; */
		var finalAmount = amount - tdsDecimal;

		var gstRateCal = (((parseFloat(selectedValue[i].igst)
			+ parseFloat(selectedValue[i].cgst)
			+ parseFloat(selectedValue[i].sgst)) / parseFloat(selectedValue[i].Taxableamt)) * 100).toFixed(2);

		var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #bcb6b6;';
		var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
			+ '<td style="text-align: center;" id="invoiceIdTd">' + selectedValue[i].invoiceId + '</td>'
			+ '<td style="text-align: left;" id="voucherIdTd">' + selectedValue[i].journalVoucher + '</td>'
			/* +'<td class="alnright ">'+selectedValue[i].vendorName+'</td>' */
			+ '<td class="alnright">' + gstRateCal + '%</td>'
			+ '<td class="alnright" id="taxableAmtTd">₹' + selectedValue[i].Taxableamt + '</td>'
			+ '<td class="alnright">₹' + selectedValue[i].igst + '</td>'
			+ '<td class="alnright">₹' + selectedValue[i].cgst + '</td>'
			+ '<td class="alnright">₹' + selectedValue[i].sgst + '</td>'
			+ '<td class="alnright" id="invAmtTd">₹' + selectedValue[i].invoicevalue + '</td>'
			+ '<td class="alnright" id="tdsAmtTd">₹' + selectedValue[i].tds + '</td>'
			+ '<td class="alnright" id="offAmtTd">₹0.00</td>'
			+ '<td class="alnright" id="fnlAmtTd">₹' + finalAmount.toFixed(2) + '</td>'
			+ '</tr>';
		$("#vendorInvoiceList").append(bdy);
		$("#payTdsAmount").val(selectedValue[i].tds);
		$("#payTdsAmountGlobal").val(selectedValue[i].tds);
		var tdsPercentView = parseFloat((selectedValue[i].TdsAmount / selectedValue[i].Taxableamt) * 100).toFixed(2);
		$("#payTdsPercentGlobal").val(tdsPercentView);
		$("#tdsPercentageAmt").text(tdsPercentView + "%");
		$("#PercentageTDS").text(tdsPercentView + "%");
		offAmountInput('');
	}
}

function offAmountInput(val) {

	let selectedRows = gridOptions.api.getSelectedRows();

	var finalamtText = $("#invAmtTd").text().replace(/[^\d.-]/g, '');
	// var finalamtText = selectedRows[0].invoiceval || '0';
	// console.log('finalamtText==',finalamtText)
	var parsefAmount = parseFloat(finalamtText);
	// console.log(parsefAmount)
	var tdAmount = parseFloat($("#payTdsAmount").val() || '0.00');
	// var tdAmount = selectedRows[0].tdsAmount || '0';
	console.log($("#payTdsAmount").val())
	var rfAmount = parseFloat($("#offAmount").val());
	// console.log(rfAmount)
	if (isNaN(rfAmount)) {
		rfAmount = 0;
	}
	var calculateAmnt = parsefAmount - tdAmount + rfAmount;
	console.log(calculateAmnt)
	$("#tdsAmtTd").text('₹' + tdAmount);
	$("#offAmtTd").text('₹' + rfAmount);
	$("#fnlAmtTd").text('₹' + parseFloat(calculateAmnt).toFixed(2));
}

function cancelInvoiceModalBtn() {
	$('#invoiceListPopup').modal('hide');
	$("#pVendorid").val('');
	$("#payTdsAmount").val('');
	$("#payTdsAmountGlobal").val('');
	$("#payTdsPercentGlobal").val('');
}

var globalTdsAmount = 0;
/* function TdsAmountInput(val){
	globalTdsAmount = 0;
	var tdsAmount = $("#payTdsAmountGlobal").val();
	globalTdsAmount = tdsAmount;
} */

/*function checkPayAmount() {

	var inputAmount = $("#payTdsAmount").val()
	console.log("tdsamount --> ", inputAmount);
	var taxableAmount = parseFloat($("#taxableAmtTd").text().replace(/[^\d.-]/g, ''));
	var tdsPercentage = $("#tdsPercentageAmt").text();
	var finalamtText = $("#invAmtTd").text().replace(/[^\d.-]/g, '');
	var tdsAmount = parseFloat(inputAmount);
	var parsefAmount = parseFloat(finalamtText);
	var percentageTds = parseFloat((tdsAmount / taxableAmount) * 100).toFixed(2);
	var deductAmount = parsefAmount - tdsAmount;
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

		var prvAmount = parseFloat($("#payTdsAmount").val());
		var previousPercentage = parseFloat((prvAmount / taxableAmount) * 100).toFixed(2);
		$("#payTdsAmount").val(inputAmount);
		$("#tdsPercentageAmt").text(previousPercentage + "%");
		$("#PercentageTDS").text(previousPercentage + "%");
		var prvtdsAmount = prvAmount;
		var calculateAmnt = parsefAmount - prvtdsAmount;
		$("#tdsAmtTd").text('₹' + prvAmount);
		$("#fnlAmtTd").text('₹' + parseFloat(calculateAmnt).toFixed(2));
	}
}*/

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
	var tdsRate = ((parseFloat(tdsAmount) / parseFloat(taxableAmt)) * 100).toFixed(2);
	var tdsTransactionDate = $("#tdsTransactionDate").val();
	var tdsNarration = $("#tdsNarration").val();

	var inputFromDate = $("#fromDate").val();
	var inputToDate = $("#toDate").val();

	var roundOffAmount = $("#offAmount").val();

	if(tdsAmount == 0 && roundOffAmount == 0) return showError('Cannot save: TDS amount and round-off amount are both zero.');

	if (tdsAmount == "" || tdsAmount == null || tdsAmount == 'null') {
		toastr.error("Please Enter TDS Amount");
		return false;
	}
	if ((tdsLedgerId == "" || tdsLedgerId == null || tdsLedgerId == 'null') && parseFloat(tdsAmount?.toString()) != 0.0) {
		toastr.error("Please Select TDS Type");
		return false;
	}
	if (tdsAmount) {
		
		roundOffAmount = roundOffAmount || '0.0';
		roundOffAmount = parseFloat(roundOffAmount?.toString()) || 0.0;
	
		$.ajax({
			type: "GET",
			url: "sale-voucher-view-TdsUpdate?tdsAmount=" + tdsAmount + "&vendorid=" + vendorid + "&invoiceId=" + invoiceId
				+ "&voucherId=" + voucherId + "&tdsRate=" + tdsRate + "&finalPayableAmt=" + finalPayableAmt + "&tdsLedgerId=" + tdsLedgerId
				+ "&tdsTransactionDate=" + tdsTransactionDate + "&tdsNarration=" + tdsNarration + "&roundOffAmount=" + roundOffAmount,
			async: false,
			success: function(response) {
				cancelInvoiceModalBtn();
				//swal("Tax Deducted At Source Successfully");
				toastr.success("Data Saved Successfully");
				viewFilteredData(voucherId);
				nextBtnFunction('manageControl');
				
				$("#pVendorid").val('');
				$("#payTdsAmount").val('');
				$("#tdsLedgerId").val('');
				$("#tdsNarration").val('');
			}
		})
	}
	else {
		toastr.error("Please Add TDS Amount");
	}

}
function voucherClassModal() {
	//$('#voucherClassnewModal').modal('show');

	$('#current').val("");
	$('#voucherTypeId').val("");
	$('#voucherClassId').val("");
	$('#ledgerName').val("");
	$('#ledgerId').val("");

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.invoiceId;
	});
	var id = selectedRowsString;
	$.ajax({
		type: "GET",
		url: "sale-voucher-get-current-voucher?id=" + id,
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
			url: "sale-voucher-get-voucherClass-List?id=" + id,
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
			url: "sale-voucher-get-ledger-voucher?id=" + id,
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
		allPValid = false;
		validationModal("Voucher Class Required", "voucherClassId");
	}
	if ($("#voucherTypeId").val() == null || $("#voucherTypeId").val() == "") {
		allPValid = false;
		validationModal("Voucher Type Required", "voucherTypeId");
	}
	if ($("#ledgerName").val() == null || $("#ledgerName").val() == "") {
		allPValid = false;
		validationModal("Ledger Name Required", "ledgerName");
	}

	if (allPValid) {
		$.ajax({
			type: "GET",
			url: "sales-voucher-save-voucher-details?voucherTypeId=" + voucherTypeId + "&voucherClassId=" + voucherClassId + "&ledgerName=" + ledgerName
				+ "&ledgerId=" + ledgerId + "&pVoucherId=" + pVoucherId + "&invoiceId=" + invoiceId,
			async: false,
			success: function(response) {
				if (response.message == "Success") {

					toastr.success("Data saved successfully");

					cancelBtnNew();
					$("#voucherClass").attr("disabled", true);
					viewFilteredData();
				}
			}
		})
	}

}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	console.log("tabElement-->", tabElement);

	if (tabElement.id == "TDSTab") {
		showTdsModal();
	}

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);

		tabTrigger.show();
	}
}

function addNarration() {

	let selectedRows = gridOptions.api.getSelectedRows();
	
	let voucherId = '';
	if(selectedRows && selectedRows.length > 0) {
		voucherId = selectedRows[0]?.journalVoucher;
	}

	let vchNarration = $("#vchNarration").val();
	
	if(!vchNarration) return showError('Narration required');

	let obj = { vchNarration, voucherId };
	
	showLoader();
	
	$.ajax({
		type: "POST",
		url: "sales-voucher-update-narration",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if(resp.code == "success") {
				toastr.success("Narration updated successfully");
				viewFilteredData(voucherId);
			} else {
				toastr.error(resp.message);
			}
		}, error : err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}

function approveSVoucher() {

	let selectedRows = gridOptions.api.getSelectedRows();
	
	let voucherId = '';
	if(selectedRows && selectedRows.length > 0) {
		voucherId = selectedRows[0]?.journalVoucher;
	}

	let status = "1";

	let obj = { status, voucherId };
	
	showLoader();
	
	$.ajax({
		type: "POST",
		url: "sales-voucher-approve-voucher",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if(resp.code == "success") {
				toastr.success("Voucher approved successfully");
				viewFilteredData(voucherId);
			} else {
				toastr.error(resp.message);
			}
		}, error : err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
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


function editLedger() {
	$("#viewItemListSecId").addClass("d-none");
	$("#viewItemDtlsSecId").removeClass("d-none");
	$("#itemName").val('');
	$("#itemLedgerId").val('').select2();
	
	let selectedRows = itemOptions.api.getSelectedRows();
	
	let skuName = selectedRows[0]?.productName;
	let ledgerId = selectedRows[0]?.ledgerId;
	let invItemId = selectedRows[0]?.invItemId;
	if(selectedRows && selectedRows.length > 0) {
		$("#itemName").val(skuName);
		$("#invItemId").val(invItemId);
		$("#itemLedgerId").val(ledgerId).select2();
	}
}

function closeLedger() {
	$("#viewItemDtlsSecId").addClass("d-none");
	$("#viewItemListSecId").removeClass("d-none");
	$("#itemName,#invItemId").val('');
	$("#itemLedgerId").val('').select2();
}

function updateItemLedger() {

	let selectedRows = itemOptions.api.getSelectedRows();
	
	let invId = '';
	let voucherId = '';
	if(selectedRows && selectedRows.length > 0) {
		invId = selectedRows[0]?.purchaseId;
		voucherId = selectedRows[0]?.voucherId;
	}

	let invItemId = $("#invItemId").val();
	let ledgerId = $("#itemLedgerId").val();
	
	let obj = { invItemId, ledgerId, invId, voucherId };
	
	showLoader();
	
	$.ajax({
		type: "POST",
		url: "sales-voucher-update-item-ledger",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(resp) {
			hideLoader();
			if(resp.code == "success") {
				toastr.success("Ledger modified successfully");
				/*itemOptions.api.setRowData([]);
				let data = resp.body;
				if(data) {
					let d = JSON.parse(data);
					itemOptions.api.setRowData(d);
				}*/
				viewFilteredData(voucherId);
				closeLedger();
			} else {
				toastr.error(resp.message);
			}
		}, error : err => {
			hideLoader();
			console.log(err);
			toastr.error('Something went wrong');
		}
	});
}