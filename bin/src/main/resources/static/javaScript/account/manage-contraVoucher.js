$(function() {

	$("#costCenter").select2({
		placeholder: "Select",
		allowClear: true
	});


	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);


	/* Intialize The CKEditor For Narration Start */

	CKEDITOR.replace('description', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	/* Intialize The CKEditor For Narration End */

	//	viewFilteredData()
	$("#myGrid").show();

	$("#singleClick").hide();

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
		url: "view-account-contra-voucher-vouchernumber",
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


// column Defs
const columnDefs =
	[
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 30,
			maxWidth: 30,
			pinned: 'left',
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: 'Contra Voucher Id',
			field: "journalVoucher",
			hide: true,
			pinned: 'left',
			cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.journalVoucher + ' <i class="fa fa-edit"></i></a>';
			}
		},
		{
			headerName: "Date",
			field: "createdOn",
			maxWidth: 100,
			pinned: 'left',
			cellStyle: {
				textAlign: 'left'
			},
			/*cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.createdOn + ' <i class="fa fa-edit"></i></a>';
			}*/
		}, {
			headerName: "Voucher No",
			field: "transactionOrder",
			hide: true,
			maxWidth: 100,
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
			headerName: "Cost Center Name",
			field: "costCenter",
			hide: true,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Debit To",
			field: "debitAccountName",
			valueGetter: function(params) {
				return params.data.debitAccountName ? params.data.debitAccountName : 'N/A';
			},
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Credit To",
			field: "creditAccountName",
			valueGetter: function(params) {
				return params.data.creditAccountName ? params.data.creditAccountName : 'N/A';
			},
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
			valueFormatter: params => amountFormatter(params.data.totalAmount.toFixed(2)),
			cellStyle: {
				textAlign: 'right'
			},
			headerClass: 'amountAlign'
		},
		{
			headerName: 'Created Date',
			field: 'createdOn',
			hide: true
		}];


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

	var selectedRows = gridOptions.api.getSelectedRows();
	console.log("Selected rows-->", selectedRows);
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '' + selectedRows[i].journalVoucher + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	console.log(deleteId)
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		var contraVoucherId = selectedRows[0].journalVoucher;
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		$("#sEntry").hide();
		$("#dEntry").hide();
		editPage(contraVoucherId);
		$(".voucher-info").show();
		$(".voucherNumberDetails").show();
		disableTrueAll();
		$("#contra-delete-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#add-btn").removeClass("d-none");
		$("#contra-vchr-save-btn").addClass("d-none");
		$("#contra-vchr-cancel-btn").addClass("d-none");
		CKEDITOR.instances['description'].setReadOnly(true);
		//
	} else {
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
		$("#sEntry").show();
		$("#dEntry").show();
		//	newBtn();
		$(".voucher-info").hide();
		$(".voucherNumberDetails").hide();
		//button hide
		$("#ticket-editBtn").addClass("d-none");
		$("#contra-delete-btn").addClass("d-none");
		$("#contra-vchr-cancel-btn").removeClass("d-none");
		paymentSectionComntainer();
		CKEDITOR.instances['description'].setData('');
		CKEDITOR.instances['description'].setReadOnly(false);
		$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	}
}

function paymentSectionComntainer() {


	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
		+ '<p class="mb-0"><input type="hidden" id="ledgerId" class="form-control creditAccountGroupIdCls">'
		/* + 'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>' */
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" class="form-control creditAmountCls" oninput="formatAmount(this);" onblur="changeToDecimal(this),getToSubtotal();" onkeyup="checkNumberInput(this.id)" autocomplete="off">'
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
		+ '<p class="mb-0">'
		/* + 'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>' */
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0"  oninput="formatAmount(this);" class="form-control debitAmountCls" onkeyup="checkNumberInput(this.id);" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);

	$("#headingDiv").addClass('hideFiltering');
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');
	$("#voucherDate").val("");
	$("#description").val("");
	$("#ledgerId").val("");
	var today = new Date();
	var day = String(today.getDate()).padStart(2, '0');
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var year = today.getFullYear();

	var formattedDate = day + '-' + month + '-' + year;
	$("#voucherDate").val(formattedDate);


	$("#contra-vchr-save-btn").removeClass("d-none");
	$("#sEntry").removeClass("d-none");
	$("#dEntry").removeClass("d-none");
	$("#add-btn").addClass("d-none");
	$("#costCenter").attr("disabled", false);
	$("#journalVoucher").text('');
	//CKEDITOR.instances['description'].setReadOnly(false);
	getVoucherNumber();
	CKEDITOR.instances['description'].setData('');

}

// for new button
function newBtn() {

	doubleDataEntry();
	disableFalse();
	$("#ticket-editBtn").hide();
	$("#contra-delete-btn").hide();
	$("#sEntry").removeClass('d-none');
	$("#dEntry").removeClass('d-none');
	$("#contra-vchr-save-btn").removeClass('d-none');
	$("#add-btn").addClass("d-none");

	$('#dEntry').addClass('blinkbtn');
	$('#sEntry').removeClass('active');
	//$("#purchaseRegisterExcel").hide();
	$("#journalVoucher").text('');
	gridOptions.api.deselectAll();
	$(".voucher-info").hide();
	$(".voucherNumberDetails").hide();
	$("#sEntry").show();
	$("#dEntry").show();

	$("#sEntry").click(function() {
		$('#dEntry').removeClass('blinkbtn');
		$('#sEntry').addClass('blinkbtn');
		$('#debitedId').prop('checked', true);
	})

	//$("#taskInform").fadeOut();

	$("#taskInform").css("opacity", 0);

	$("#dEntry").click(function() {
		$('#sEntry').removeClass('blinkbtn');
		$('#dEntry').addClass('blinkbtn');
	})

	bankNameList();

	$("#headingDiv").addClass('hideFiltering');
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("").trigger('change');
	$("#voucherDate").val("");
	$("#description").val("");
	$("#ledgerId").val("");
	var today = new Date();
	var day = String(today.getDate()).padStart(2, '0');
	var month = String(today.getMonth() + 1).padStart(2, '0');
	var year = today.getFullYear();

	var formattedDate = day + '-' + month + '-' + year;
	$("#voucherDate").val(formattedDate);

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" autocomplete="off">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountGroupId_0" class="form-control creditAccountGroupIdCls">'
		+ '<p class="mb-0"><input type="hidden" id="ledgerId" class="form-control creditAccountGroupIdCls">'
		/* + 'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>' */
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" class="form-control creditAmountCls" oninput="formatAmount(this);" onblur="changeToDecimal(this),getToSubtotal();" onkeyup="checkNumberInput(this.id)" autocomplete="off">'
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
		+ '<p class="mb-0">'
		/* + 'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>' */
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0"  oninput="formatAmount(this);" class="form-control debitAmountCls" onkeyup="checkNumberInput(this.id);" onblur="changeToDecimal(this),getFromSubtotal() ;" autocomplete="off">'
		+ '</td>'
		+ '<td style="vertical-align: baseline;" align="center" class="d-flex">'
		+ '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();">'
		+ '<span class="ti-plus"></span>'
		+ '</button>'
		+ '</td>'
		+ '</tr>'
	$("#debitTbodyData").append(abc);

	getVoucherNumber();

	/*$("#creditTbodyData").find("input, button").prop("disabled", false);

	// Disable all inputs inside debitTbodyData
	$("#debitTbodyData").find("input, button").prop("disabled", false);*/
	CKEDITOR.instances['description'].setData('');
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
}

// Function For The bank Dropdown

function bankNameList() {
	$.ajax({
		type: "GET",
		url: "view-account-contra-voucher-banklist",
		success: function(response) {
			if (response.message == "success") {
				$("#ledgerId").empty();
				var option = $("<option></option>");
				/* $(option).val(null);
				$(option).html("Select State"); */
				$("#states").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#ledgerId").append(option);
				}
				/* $("#ledgerId").val(stateid); */
			}

		},
		error: function(e) {
		}
	});

}


// for cancel button
function cancelBtn() {

	$('#dEntry').removeClass('blinkbtn');
	$('#sEntry').removeClass('blinkbtn');

	$("#add").show();
	$('#totalAmountFooterDiv').show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$("#demo").hide();
	$("#headingDiv").show();
	$("#journalVoucher").text("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("");
	$('#accountNumber').val("");
	$('#status').val("");
	$("#headingDiv").removeClass('hideFiltering');
	$("#purchaseRegisterExcel").removeClass('hideBtn');
	$("#purchaseRegisterExcel").show();

	CKEDITOR.instances.description.setData("");
	$("#sEntry").val("");
	/* $("#dEntry").val(""); */

	viewFilteredData()

}


// Edit & stage change 
function editPage(id) {



	var editId = id.split(",");

	var journalVoucher = editId[0];

	var modal = editId[1];
	//	alert(id);

	$("#headingDiv").addClass('hideFiltering');
	$(".container").hide();
	//$("#purchaseRegisterExcel").addClass('hideBtn');
	$(".voucher-info").show();
	$(".voucherNumberDetails").show();
	$("#ticket-editBtn").show();
	$("#contra-delete-btn").show();

	//	alert('journalVoucherId------'+journalVoucher);

	$.ajax({
		type: "GET",
		url: "view-account-contra-voucher-edit?id=" + journalVoucher,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				console.log("response for edit------" + JSON.stringify(response));
				$("#debitTbodyData").empty();
				$("#creditTbodyData").empty();
				$('#costCenter').val(response.body[0].costCenter).trigger('change');
				//$('#description').val(response.body[0].description);
				$('#voucherNumber').text(response.body[0].journalVoucher);
				$('#journalVoucher').text(response.body[0].journalVoucher);
				$('#voucherDate').val(response.body[0].voucherDate);
				const formattedDate = formatDate(response.body[0].voucherDate);
				$("#recieptDate").text(formattedDate);
				console.log("format date-->", formattedDate);
				CKEDITOR.instances['description'].setData(response.body[0].description);

				$('#ledgerId').val(response.body[0].ledgerId);

				var toTotalAmount = 0;
				var fromTotalAmount = 0;
				for (var i = 0; i < response.body.length; i++) {
					if (response.body[i].transactionType == "Debit") {
						var amount = response.body[i].fromAmount;
						fromTotalAmount = fromTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"  id="debit_' + i + '">'
							+ '<td><input type="text" id="debitAccountSubGroup_' + i + '" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
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
							+ '<td><input type="text" id="creditAmount_' + i + '" class="form-control creditAmountCls" oninput="formatAmount(this);"  onblur="changeToDecimal(this),getToSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
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
	console.log("object on add account-----------" + JSON.stringify(obj));
	var validation = true;
	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-account-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {
					console.log(response);

					$("#add").show();
					$("#copy").show();
					$("#delete").show();
					$("#totalReq").show();
					$("#myGrid").show();
					$("#searchRowDiv").show();
					$("#demo").hide();

					/* current date filter data */
					var today = new Date();
					var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
					var fromDate = dateString.toString();
					var todate = dateString.toString();

					$("#toDate").val(todate);
					$("#fromDate").val(fromDate);
					viewFilteredData()

				}
			},
			error: function(data) {
			}
		})
	}

}

let globalContraVoucherId = '';

function addContraInfo() {

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
		item['voucherType'] = "CONTRA";
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
		item['voucherType'] = "CONTRA";
		dataset.push(item);
	});//table tbody tr loop ends

	console.log(item);
	console.log("add journal dataset----------------------" + JSON.stringify(dataset));

	// Blank Validations

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






/*function addContraInfo() {
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
		item['voucherType'] = "CONTRA";
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
		item['voucherType'] = "CONTRA";
		dataset.push(item);
	});//table tbody tr loop ends

	console.log(item);
	console.log("add journal dataset----------------------" + JSON.stringify(dataset));
	//return false;
	//submitJournal(dataset);

	//Blank Validations





	if (!blankValidation("description", "TextField", "Please Enter Narration"))
		return false;
	var debitAccountGroup = true;
	$('.debitAccountSubGroupCls').each(function() {

		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {
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
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {

				creditAccountGroup = false;
				return false;
			}

		});
	}

	var creditAccountGroup = true;

	// Check if either checkbox is selected
	var isDebitedChecked = $("#debitedId").is(":checked");
	var isCreditedChecked = $("#creditedId").is(":checked");

	if ((debitAccountGroup && debitName && debitAmount) && !(isDebitedChecked || isCreditedChecked)) {
		$('.creditAccountSubGroupCls').each(function() {
			if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {
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


	if (!validateSameLedgerInDebitCredit()) {
		return false;
	}

	console.log("cost-->", $("#costCenter").val());
	if ($("#costCenter").val() == "") {
		toastr.error("Please Select Cost Center Name");
		return false;
	}
	//if (!blankValidation("costCenter", "SelectBox", "Please Select Cost Center Name"))


	if ($("#description").val() == "") {
		toastr.error("Please Enter Narration");
		return false;
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
			toastr.error("Debit Amount not same with credit Amount");
			return false;
		}
	}

}*/
function deleteJournalVoucher() {
	$.ajax({
		type: "GET",
		url: "view-account-contra-voucher-deleteId?id=" + deleteId,
		success: function(response) {
			if (response.code == "200") {
				toastr.success(response.message);
				cancelBtn();

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
				/* current date filter data */
				var today = new Date();
				var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
				var fromDate = dateString.toString();
				var todate = dateString.toString();

				$("#toDate").val(todate);
				$("#fromDate").val(fromDate);
				viewFilteredData()

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
				/* current date filter data */
				var today = new Date();
				var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
				var fromDate = dateString.toString();
				var todate = dateString.toString();

				$("#toDate").val(todate);
				$("#fromDate").val(fromDate);
				viewFilteredData()

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
				/* current date filter data */
				var today = new Date();
				var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
				var fromDate = dateString.toString();
				var todate = dateString.toString();

				$("#toDate").val(todate);
				$("#fromDate").val(fromDate);
				viewFilteredData()

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
		url: "view-account-contra-voucher-debit",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			console.log("----->>>>>>> -------->>>>>>> ", response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					/*content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';*/
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
			console.log(data);
		}
	})

	if (search.length == 0) {
		$("#suggesstion-box1_" + counter).hide();
	}
}

/*function autocompleteValue1(sGroupId, sGroupName, id, amount) {
	var l = id.split("_");
	var counter = l[1];

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
}*/

function autocompleteValue1(sGroupId, sGroupName, id, amount) {
	var l = id.split("_");
	var counter = l[1];

	let fullLedgerName = sGroupName + '( ' + sGroupId + ' )';

	if (isDuplicateLedger(fullLedgerName, sGroupId, "debitType")) {
		toastr.error("Ledger already added! Please select a different ledger.");

		return;
	}

	if (sGroupId) {
		$("#debitAccountSubGroup_" + counter).val(fullLedgerName);
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
		url: "view-account-contra-voucher-credit",
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
						content += '<li  class ="autocompletedata cp" onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
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
			console.log(data);
		}
	})

	if (search.length == 0) {
		$("#suggesstion-box2_" + counter).hide();
	}
}

function autocompleteValue2(sGroupId, sGroupName, id, amount) {
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
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {
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





/*function creditCheckEmpty() {
	var creditAccountGroup = true;
	$('.creditAccountSubGroupCls').each(function() {
		if (!blankValidation($(this).attr('id'), "TextField", "Please Enter Ledger")) {

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
	//getFromSubtotal(); // If needed
}


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
		textInput.eq(2).attr('id', "debitName_" + i);
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
		textInput.eq(2).attr('id', "creditName_" + i);
		textInput.eq(3).attr('id', "creditAmount_" + i);
		divInput.eq(0).attr('id', "suggesstion-box2_" + i);
		textInput.eq(1).attr('id', "creditAccountGroupId_" + i);
		tr.eq(0).attr('id', "credit_" + i);

	})
}*/
//add More at edit time
$(document).ready(function() {

	console.log("hello");
	var lengthOfTableRow = $("#debitTbodyData").children('tr').length;
	/*$('.tbll').on('click', '.rmv', function() {
		$(this).closest('tr').remove();

		// Recalculate total amount after row deletion
		getFromSubtotal();

		// Clear the last column of the last row
		$("#debitMyTable tbody tr:last").find('td:last').html('');

		// Button HTML
		var addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;';
		var removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';

		// Check how many rows are left
		var rowCount = $("#debitMyTable tbody tr").length;

		// Append buttons accordingly
		if (rowCount > 1) {
			// If more than one row, add both buttons to the last row
			$("#debitMyTable tbody tr:last").find('td:last').append(addBtn + removeBtn);
		} else {
			// Only one row left, add only the add button
			$("#debitMyTable tbody tr:last").find('td:last').append(addBtn);
		}
	});*/


	var lengthOfTableRow = $("#creditTbodyData").children('tr').length;
	$('.tbll').on('click', '.rmv', function() {
		$(this).closest('tr').remove();

		// Recalculate credit subtotal (optional, call your subtotal function here)
		getToSubtotal(); // <-- Call your actual function to update total

		// Clear the button cell in the last row
		$("#creditMyTable tbody tr:last").find('td:last').html('');

		// Define the buttons
		var addBtn = '<button type="button" class="btn btn-primary tr_clone_add go-btn" name="add" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;';
		var removeBtn = '<button type="button" class="btn btn-warning rmv go-btn" name="Remove"><span class="ti-minus"></span></button>';

		// Count remaining rows
		var rowCount = $("#creditMyTable tbody tr").length;

		// Add appropriate buttons to the last row
		if (rowCount > 1) {
			$("#creditMyTable tbody tr:last").find('td:last').append(addBtn + removeBtn);
		} else {
			$("#creditMyTable tbody tr:last").find('td:last').append(addBtn);
		}
	});


	$('#ledgerGst').on('keydown', function(event) {
		var gstValue = $(this).val();
		if (gstValue.length === 12 && event.keyCode === 8) {
			event.preventDefault();
		}
	});




	//Function for getting Table Name on change of spa Name
});
//function to validate Discount field


/*function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	value = value.replace(/,/g, '');
	var floatValue = (parseFloat(value).toFixed(2));
	console.log("floatvalue-->", floatValue);
	if (isNaN(floatValue)) {
		floatValue = 0.00;
	}
	if (id.includes('debitAmount')) {
		$("#" + id).val(amountFormatter(floatValue));
		var counter = id.split('_')[1];
		$("#creditAmount_" + counter).val(amountFormatter(floatValue));
	} else if (id.includes('creditAmount')) {
		  if(floatValue>0){		    	
				$("#"+id).val(floatValue);
		}else{
			$("#"+id).val(0.00);
		} 

		$("#" + id).val(floatValue);
		var counter = id.split('_')[1];
		$("#debitAmount_" + counter).val(floatValue);
	}
}*/


function changeToDecimal(elemt) {
	let id = $(elemt).attr('id');
	let value = $("#" + id).val().replace(/,/g, '');

	let floatValue = parseFloat(value);
	if (isNaN(floatValue)) {
		floatValue = 0.00;
	}

	let formattedValue = amountFormatter(floatValue.toFixed(2));
	$("#" + id).val(formattedValue);
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
				url: "view-account-contra-voucher-add",
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
					if (response.code == "201") {
						/*swal({
							title: "Voucher Status",
							text: "Contra voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/view-account-contra-voucher";
						})*/
						toastr.success(response.message);
						setTimeout(() => {
							viewFilteredData();
						}, 1000);
						$("#contra-vchr-save-btn").addClass("d-none");
					}
					else if (response.code == "200") {
						toastr.success(response.message);
						setTimeout(() => {
							viewFilteredData();
						}, 1000);
						$("#contra-vchr-save-btn").addClass("d-none");
					} else {
						/*swal({
							title: response.code,
							text: response.message,
							type: "warning"
						})*/

						toastr.error(response.message);
					}


				}, error: function(response) {
					swal(response.code);
				}
			}) //ajax ends
		}
	})//swal function block ends
}//submit function ends


//Function for cancel button
function funcLoad() {
	location.reload();
}


/*function getFromSubtotal() {
	var sum = 0;
	$(".debitAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val().replace(/,/g, ''));
	})

	$("#fromTotalAmount").html(amountFormatter(sum.toFixed(2)));
	$("#creditAmount_0").val(amountFormatter(sum.toFixed(2)));
	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
	getToSubtotal()
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val().replace(/,/g, ''));
	})

	$("#toTotalAmount").html(amountFormatter(sum.toFixed(2)));
	getFromSubtotal()
}*/



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

//document.getElementById("spFY").innerHTML=getCurrentFinancialYear();

function cancelLedgerBtn() {
	$("#addLedgerModal").modal('hide');
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
			url: "view-account-contra-voucher-stateList?id=" + cname,
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
								content += '<li style="font-weight:700;padding: 5px 0px; color:#121212;  border-bottom: 1px solid #080808;  font-size:12px; background-color: #9ec5ff;" class ="autocompletedata cp" onClick="selectAutocompleteValue1(\''
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
							content += '<li style="font-weight:700;  padding: 5px 0px; color:#121212; border-bottom: 1px solid #080808;  font-size:12px; background-color: #9ec5ff;" class ="autocompletedata cp" onClick="selectAutocompleteValue1()">'
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
						content += '<li style="font-weight:bold; font-size:14px; color:#ccc;background-color: blue;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue1()">'
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
		content += '<li style="font-weight:bold; font-size:14px; color:#ccc;background-color: blue;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue1()">'
			+ "No Data Found" + '</li>';
		content += '<li style="margin-left:-30px;" '
			+ '</li>';
		content += '</ul>';
		$("#suggesstion-box11_").hide();
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

	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.ledgername == null || obj.ledgername == "") {
		toastr.error("Ledger Name Required");
		return false;
	}

	if (obj.groupId == null || obj.groupId == "") {
		toastr.error("Ledger Group Required");
		return false;
	}

	/* if (obj.ledgerPan == null || obj.ledgerPan == "") {
		swal("Ledger Pan Required");
		return false;
	}
	
	if (obj.ledgerGst == null || obj.ledgerGst == "") {
		swal("Ledger Gst Required");
		return false;
	} */

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-account-contra-voucher-group-addLedger",
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

				$('#ledgerAddress1').val('');
				$('#ledgerAddress2').val('');
				$('#ledgerAddress3').val('');
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}

function viewFilteredData() {
	let voucherId = globalContraVoucherId;
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
			url: "view-account-contra-voucher-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			console.log(JSON.stringify(data))
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewVoucher;
			if (allData == "" || allData == null || allData == "null") {
				console.log("in null handeler")
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				$("#totalAmountFooter").val("0.00");
				$("#purchaseRegisterExcel").prop("disabled", true);
				paymentSectionComntainer();
				$("#ticket-editBtn").addClass("d-none");
				$("#contra-delete-btn").addClass("d-none");
				$("#contra-vchr-cancel-btn").removeClass("d-none");
				$(".voucherNumberDetails").hide();
				CKEDITOR.instances['description'].setReadOnly(false);
			} else {
				$(".voucherNumberDetails").show();
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				//	gridOptions.api.setRowData(allData);
				var rowData = [];
				gridOptions.api.setRowData(rowData);

				gridOptions.api.setRowData(allData);
				/*gridOptions.api.setRowData(allData);

				if (allData && allData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}
				*/

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
			}
		});
	}
}

function singleDataEntry() {
	/*$("#singleClick").show();
	$('#debitedId').prop('checked', true);
	var checkBox = $("#creditedId");
	checkBox.prop("checked", false);
	 debitShow(); 

	 creditShow(); 

		  $("#creditAmount_0").val('');
		$("#debitAmount_0").val('');
		$("#creditAccountSubGroup_0").val('');
		$("#debitAccountSubGroup_0").val(''); 

	var checkBox = document.getElementById("creditedId");

	$('#creditedId').prop('checked', false);

	if (checkBox.checked == true) {
		$("#creditIDD").show();
		$("#debitIDD").hide();
	} else {
		$("#creditIDD").hide();
		$("#debitIDD").show();
	}

	var ledgerName = $('#ledgerId option:selected').text();
	var ledgerId = $('#ledgerId option:selected').val();
	$("#debitAccountGroupId_0").val(ledgerId)
	$("#debitAccountSubGroup_0").val(ledgerName)*/



	$("#singleClick").show();
	$('#debitedId').prop('checked', true);
	$('#creditedId').prop('checked', false);
	//debitShow();
	$("#creditAmount_0").val('');
	$("#debitAmount_0").val('');
	$("#creditAccountSubGroup_0").val('');
	$("#debitAccountSubGroup_0").val('');

	var checkBox = document.getElementById("creditedId");

	$('#creditedId').prop('checked', false);

	if (checkBox.checked == true) {
		$("#creditIDD").show();
		$("#debitIDD").hide();
	} else {
		$("#creditIDD").hide();
		$("#debitIDD").show();
	}

	var ledgerName = $('#ledgerId option:selected').text();
	var ledgerId = $('#ledgerId option:selected').val();
	$("#debitAccountGroupId_0").val(ledgerId)
	$("#debitAccountSubGroup_0").val(ledgerName)

}





function doubleDataEntry() {


	/* 	var ledgerName = $('#ledgerId option:selected').text();
		var ledgerId = $('#ledgerId option:selected').val(); */
	$("#debitAccountGroupId_0").val('');
	$("#debitAccountSubGroup_0").val('');

	$("#sEntry").val('');
	$('#dEntry').addClass('blinkbtn');

	$("#singleClick").hide();
	$("#debitIDD").show();
	$("#creditIDD").show();


}

function debitShow() {
	//$("#deliveryPartner").val("");
	var checkBox = document.getElementById("debitedId");

	$('#creditedId').prop('checked', false);

	//$("#debitAccountGroupId_0").val('');
	//$("#debitAccountSubGroup_0").val('');

	if (checkBox.checked == true) {
		$("#debitIDD").show();
		$("#creditIDD").hide();
	} else {
		$("#debitIDD").hide();
		$("#creditIDD").show();
		//$("#creditIDD").hide();
	}
	var ledgerName = $('#ledgerId option:selected').text();
	var ledgerId = $('#ledgerId option:selected').val();
	$("#creditAccountSubGroupId_0").val(ledgerId)
	$("#creditAccountSubGroup_0").val(ledgerName)
}

function creditShow() {
	//$("#deliveryPartner").val("");
	var checkBox = document.getElementById("creditedId");

	$('#debitedId').prop('checked', false);

	//$("#creditAccountSubGroup_0").val('');
	//$("#creditAccountSubGroupId_0").val('');

	if (checkBox.checked == true) {
		$("#creditIDD").show();
		$("#debitIDD").hide();
	} else {
		$("#creditIDD").hide();
		$("#debitIDD").show();
	}

	var ledgerName = $('#ledgerId option:selected').text();
	var ledgerId = $('#ledgerId option:selected').val();
	$("#debitAccountGroupId_0").val(ledgerId)
	$("#debitAccountSubGroup_0").val(ledgerName)
}

function getLedgerName() {
	var ledgerName = $('#ledgerId option:selected').text();
	var ledgerId = $('#ledgerId option:selected').val();

	var debitCheckBox = document.getElementById("debitedId");
	var creditCheckBox = document.getElementById("creditedId");

	if (creditCheckBox.checked == true) {
		console.log("Credit Checkbox", ledgerId, ledgerName);
		$("#debitAccountGroupId_0").val(ledgerId)
		$("#debitAccountSubGroup_0").val(ledgerName)
	}
	else if (debitCheckBox.checked == true) {

		console.log("Debit Checkbox", ledgerId, ledgerName);
		$("#creditAccountSubGroupId_0").val(ledgerId)
		$("#creditAccountSubGroup_0").val(ledgerName)
	}

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

/* 		function downloadExcelFromGrid() {
			var selectedColumns = ['journalVoucher', 'costCenter', 'debitAccountName', 'creditAccountName', 
				'description', 'totalAmount', 'createdOn'];
		    
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			var fileName = 'ContraVoucher_Register' + '('+fromDate +' To '+ toDate+')' + '.xlsx';

			var totalAmount = 0;

			var rowData = [];

			gridOptions.api.forEachNodeAfterFilterAndSort(function (node) {
				var data = {};
				selectedColumns.forEach(function (col) {
					var value = node.data[col] || '';
					if (col === 'totalAmount') {
						value = parseFloat(value);
						value = isNaN(value) ? '0.00' : value.toFixed(2);
						value = amountFormatter(value);
					}
					data[col] = value;
				});

				rowData.push(data);

				var amount = parseFloat(node.data.totalAmount) || 0;
			    
				totalAmount += amount;
			});

			totalAmount = amountFormatter(totalAmount.toFixed(2));

			var totalRow = {
					journalVoucher:"Total",
					costCenter:"",
					debitAccountName:"",
					creditAccountName:"",
					description:"",
					totalAmount:totalAmount,
					createdOn:"",
			};

			rowData.push(totalRow);
			var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedColumns });
			var wb = XLSX.utils.book_new();
			XLSX.utils.book_append_sheet(wb, ws, 'Contra Voucher');
			XLSX.writeFile(wb, fileName);
		} */

function downloadExcelFromGrid() {
	var selectedHeaders = ['Contra Voucher Id', 'Voucher No', 'Debit To', 'Credit To', 'Description', 'Amount', 'Created Date'];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Contra_Voucher_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

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
		'Contra Voucher Id': "Total",
		'Voucher No': "",
		'Debit To': "",
		'Credit To': "",
		'Description': "",
		'Amount': totalAmount,
		'Created Date': ""
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Contra Voucher');
	XLSX.writeFile(wb, fileName);
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

function tabAccess(tabName) {

	console.log("tabName-->", tabName);
	if (typeof tabName !== "string") {
		return;
	}
	$("#" + tabName).show();

}


function formatDate(dateString) {
	const [day, month, year] = dateString.split("-").map(Number);

	const date = new Date(year, month - 1, day); // Month is 0-indexed

	const monthNames = [
		"January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"
	];

	const dayNames = [
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	];

	const formattedDate = `${day}-${monthNames[month - 1]}-${year},${dayNames[date.getDay()]}`;

	return formattedDate;
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
	CKEDITOR.instances['description'].setReadOnly(false);

}

function editItems() {
	disableFalse();
	$("#contra-vchr-save-btn").removeClass("d-none");
	$("#toDateCalendar3").removeClass("pointeerEventsProperty");
	$("#contra-delete-btn").addClass("d-none");
	$("#contra-vchr-cancel-btn").removeClass("d-none");
	$("#add-btn").addClass("d-none");
	$("#ticket-editBtn").addClass("d-none");
}

/*function checkNumberInput(id) {
	var tagname = $("#" + id).val()
	var replaceValue = tagname.replace(/[^\d.]/g, '');
	var decimalIndex = replaceValue.indexOf('.');
	if (decimalIndex !== -1) {
		replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '');
	}
	$("#" + id).val(replaceValue);
}*/



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
	
	console.log("visible rows-->" , visibleRows);

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


function cancelContraBtn() {
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	disableTrueAll();
	if (firstRowNode) {
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
		$("#contra-vchr-cancel-btn").addClass("d-none");
		$("#contra-vchr-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");
		$("#ticket-editBtn").removeClass("d-none");
		$("#contra-delete-btn").removeClass("d-none");
	}
	else {
		$("#contra-vchr-cancel-btn").addClass("d-none");
		$("#contra-vchr-save-btn").addClass("d-none");
		$("#add-btn").removeClass("d-none");

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
