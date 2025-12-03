$(function() {

	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	$('.loader').show();

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
		$('#voucherDate').val($(this).val());
	})

	$('#voucherDate').blur(function() {
		$("#toDateCalendar3").val($(this).val());
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

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);

	var length = gridOptions.api.getDisplayedRowCount();
	$("#totalReq").find('span').html(length);

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

// column Defs
const columnDefs =
	[
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		}, {
			headerName: 'Payment Voucher Id',
			field: "journalVoucher",
			/*cellRenderer: function(params) {
				return '<a onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.journalVoucher + '</a>';
			}*/
		},
		{
			headerName: "Voucher Date",
			field: "createdOn",
			hide: true,
			width: 210,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "TRANSACTION DATE",
			field: "TRANSACTION_DATE",
			width: 210,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Invoice Id",
			field: "invoiceId",
			cellStyle: {
				textAlign: 'left'
			}
		}, {
			headerName: "Payment Id",
			field: "paymentId",
			cellStyle: {
				textAlign: 'left'
			}
		}, {
			headerName: "Cost Center Name",
			field: "costCenter",
			cellStyle: {
				textAlign: 'left'
			}
		}, {
			headerName: "Vendor Name",
			field: "vendorName",
			cellStyle: {
				textAlign: 'left'
			}
		}, /* {
					headerName: "Description",
					field: "description",
					cellStyle: {
						textAlign: 'left'
					}
				}, */

		{
			headerName: "Amount",
			field: "totalAmount",
			valueFormatter: params => amountFormatter(params.data.totalAmount.toFixed(2)),
			cellStyle: {
				textAlign: 'right'
			}
		}];


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
	getRowNodeId: function(data) {
		return data.journalVoucher;
	}

};

var deleteId = "";
function rowSelect() {
	//alert('hello select');
	var selectedRows = gridOptions.api.getSelectedRows();
	var paymentId = selectedRows[0].journalVoucher;
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
		$('#delete').attr("disabled", false);
		$("#approve").attr("disabled", false);
		$("#return").attr("disabled", false);
		$("#reject").attr("disabled", false);
		editPage(paymentId);

	} else {
		$('#delete').attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#return").attr("disabled", true);
		$("#reject").attr("disabled", true);
	}
}
// for new button
function newBtn() {
	//	alert('hello');

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

	$("#demo").show();
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("");
	$("#voucherDate").val("");
	$("#description").val("");

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" id="creditAccountSubGroup_0" placeholder="Search SubGroup" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountSubGroupId_0" class="form-control creditAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ 'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();">'
		+ '<p class="mb-0" style="height: 22px;"></p></td>'
		/* +'<td style="vertical-align: baseline;" align="center">'
		+'<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="creditCheckEmpty();">'	
		+'<span class="ti-plus"></span>'		
		+'</button>'	
		+'</td>' */
		+ '</tr>'
	$("#creditTbodyData").append(abc);

	$("#debitTbodyData").empty();
	var abc = '<tr class="tr_clone" id="debit_0">'
		+ '<td><input type="text" id="debitAccountSubGroup_0" placeholder="Search SubGroup" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);">'
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+ '<p class="mb-0">'
		+ 'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>'
		+ '</p></td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;">'
		+ '<p class="mb-0" style="height:22px;"></p></td>'
		/* +'<td style="vertical-align: baseline;" align="center">'
		+'<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="debitCheckEmpty();">'
		+'<span class="ti-plus"></span>'
		+'</button>'
		+'</td>' */
		+ '</tr>'
	$("#debitTbodyData").append(abc);
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
	$("#paymentRegisterPdf").removeClass('hideBtn');
	$("#dwnldExcel").removeClass('hideBtn');

	viewFilteredData()

}



// Edit & stage change 
function editPage(id) {

	var editId = id.split(",");

	var journalVoucher = editId[0];

	var modal = editId[1];
	//	alert(id);



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
				$('#costCenter').val(response.body[0].costCenter);
				$('#description').val(response.body[0].description);
				$('#voucherNumber').text(response.body[0].journalVoucher);
				$('#journalVoucher').text(response.body[0].journalVoucher);
				$('#voucherDate').val(response.body[0].voucherDate);
				$('#costCenter').prop('disabled', true);
				$('#voucherDate').prop('disabled', true);
				$('#description').prop('readonly', true);


				var toTotalAmount = 0;
				var fromTotalAmount = 0;
				for (var i = 0; i < response.body.length; i++) {
					if (response.body[i].transactionType == "Debit") {
						var amount = response.body[i].fromAmount;
						fromTotalAmount = fromTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"  id="debit_' + i + '">'
							+ '<td><input readonly type="text" id="debitAccountSubGroup_' + i + '" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
							+ '<div id="suggesstion-box1_0"></div>'
							+ '<input type="hidden" id="debitAccountGroupId_' + i + '" class="form-control debitAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'

							+ '</td>'
							/* +'<td><input type="text" id="debitName_'+i+'" class="form-control debitNameCls" value="'+response.body[i].fromName+'">'
							+'<p class="mb-0" style="height:22px;"></p>'
							+'</td>' */
							+ '<td>'
							+ '<input readonly type="text" id="debitAmount_' + i + '" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ '<p class="mb-0" style="height:22px;"></p>'
							+ '</td>'
							/* +'<td>'
							+'<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="debitCheckEmpty();">'
									+'<span class="ti-plus"></span></button>&nbsp;</td>' */
							+ '</tr>';
						$("#debitTbodyData").append(abc);
					}
					if (response.body[i].transactionType == "Credit") {
						var amount = response.body[i].fromAmount;
						toTotalAmount = toTotalAmount + response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"   id="credit_' + i + '">'
							+ '<td><input readonly type="text" id="creditAccountSubGroup_' + i + '" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
							+ '<div id="suggesstion-box2_0"></div>'
							+ '<input type="hidden" id="creditAccountSubGroupId_' + i + '"  class="form-control creditAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'

							+ '</td>'
							/* +'<td><input type="text" id="creditName_'+i+'"	class="form-control creditNameCls" value="'+response.body[i].fromName+'"></td>' */
							+ '<td><input readonly type="text" id="creditAmount_' + i + '" class="form-control creditAmountCls"  onblur="changeToDecimal(this),getToSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
							+ '<p class="mb-0" style="height:22px;"></p>'
							+ '</td>'
							/* +'<td><button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="creditCheckEmpty();">'
									+'<span class="ti-plus"></span>'
								+'</button>&nbsp;</td>' */
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

//addVendorInfo

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

function addPaymentInfo() {
	var dataset = [];
	item = {};

	$("#debitTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#fromTotalAmount").text();
		item['description'] = $("#description").val();
		item['voucherDate'] = $("#voucherDate").val();
		item['fromAccountSubGroup'] = $(this).find(".debitAccountGroupIdCls").val();
		item['fromName'] = $(this).find(".debitNameCls").val();
		item['fromAmount'] = $(this).find(".debitAmountCls").val();
		item['voucherType'] = "TVTM003";
		dataset.push(item);
	});//table tbody tr loop ends
	$("#creditTbodyData > tr").each(function() {
		item = {};
		item['costCenter'] = $("#costCenter").val();
		item['journalVoucher'] = $("#journalVoucher").text();
		item['totalAmount'] = $("#toTotalAmount").text();
		item['description'] = $("#description").val();
		item['voucherDate'] = $("#voucherDate").val();
		item['toAccountSubGroup'] = $(this).find(".creditAccountGroupIdCls").val();
		item['toName'] = $(this).find(".creditNameCls").val();
		item['toAmount'] = $(this).find(".creditAmountCls").val();
		item['voucherType'] = "PAYMENT";
		dataset.push(item);
	});//table tbody tr loop ends

	//console.log(item);
	//console.log("add journal dataset----------------------" + JSON.stringify(dataset));
	//return false;
	//alert("totalAmnt------"+$("#fromTotalAmount").text());
	submitJournal(dataset);

	//Blank Validations

	if (!blankValidation("costCenter", "SelectBox", "Please Select Cost Center Name"))
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
			debit = debit + parseFloat($(this).val());
		});

		$(".creditAmountCls").each(function(i) {
			credit = credit + parseFloat($(this).val());
		});

		if (credit == debit) {
			submitJournal(dataset);
		}
		else {
			swal("Debit Amount not same with credit Amount");
			return false;
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
	$.ajax({
		type: "POST",
		url: "payment-voucher-getAccountDebitGroup",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			//console.log(response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					content += '</ul>';
					$("#suggesstion-box1_" + counter).show();
					$("#suggesstion-box1_" + counter).html(content);

				}
				else {
					//console.log("else: " + response);
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
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
}

function autocompleteValue1(sGroupId, sGroupName, id, amount) {
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
}

//function for auto filled of item Requisition
function creditSubGroup(id) {

	var l = id.split("_");
	var counter = l[1];

	$.ajax({
		type: "POST",
		url: "payment-voucher-getAccountCreditGroup",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			//console.log(response);
			if (response.code == "Success") {
				if (response.body.length != 0) {
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);

				}
				else {
					//console.log("else: " + response);
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li onClick="autocompleteValue2(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
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
}

function autocompleteValue2(sGroupId, sGroupName, id, amount) {
	var l = id.split("_");
	var counter = l[1];

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

function debitCheckEmpty() {
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
}
function creditCheckEmpty() {
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
}
//Function for adding more Table row

function debitAddMore() {
	var lengthOfTableRow = $("#debitTbodyData").children('tr').length;
	var cloneHtml = $("#debitMyTable tbody tr:first").clone();
	$("#debitMyTable tbody tr:last").find('td:last').html('');
	$("#debitMyTable tbody").append($("#debitMyTable tbody tr:first").clone());
	$("#debitMyTable tbody tr td:last").html("");
	var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
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
		textInput.eq(2).attr('id', "debitName_" + i);
		textInput.eq(3).attr('id', "debitAmount_" + i);
		divInput.eq(0).attr('id', "suggesstion-box1_" + i);
		textInput.eq(1).attr('id', "debitAccountGroupId_" + i);
		tr.eq(0).attr('id', "debit_" + i);

	})
}
//Function for adding more Table row

function creditAddMore() {
	var lengthOfTableRow = $("#creditTbodyData").children('tr').length;
	var cloneHtml = $("#creditMyTable tbody tr:first").clone();
	$("#creditMyTable tbody tr:last").find('td:last').html('');
	$("#creditMyTable tbody").append($("#creditMyTable tbody tr:first").clone());
	$("#creditMyTable tbody tr td:last").html("");
	var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
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
}

var gridOptions2;
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
		var add = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="debitCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
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
		var add = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="creditCheckEmpty();"><span class="ti-plus"></span></button>&nbsp;'
		var remove = '<button type="button" class="btn btn-warning rmv" name="Remove"><span class="ti-minus"></span></button>';

		if ($("#creditMyTable").children('tr').length > 1) {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
			$("#creditMyTable tbody tr:last").find('td:last').append(remove);
		}
		else {
			$("#creditMyTable tbody tr:last").find('td:last').append(add);
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
				color: 'black!important'
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
				color: 'black!important'
			}
		}, {
			headerName: 'Tax Invoice No',
			field: "taxInvNo",
			width: 150,
			cellStyle: {
				color: 'black!important'
			}
		}, {
			headerName: 'Tax Invoice Date',
			field: "taxInvDate",
			width: 150,
			cellStyle: {
				color: 'black!important'
			}
		}, {
			headerName: 'Vendor name',
			field: "vendorName",
			width: 250,
			cellStyle: {
				color: 'black!important'
			}
		}, {
			headerName: 'Vendor ID',
			field: "vendorId",
			width: 150,
			hide: true,
		}, {

			headerName: 'Approve Status',
			field: "appoveStatus",
			width: 150,
			hide: true,
			cellStyle: {
				textAlign: 'left'
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
			valueFormatter: params => params.data.grandTotal.toFixed(2),
			cellStyle: {
				color: 'black!important'
			}
		}, {

			headerName: 'Paid Amount',
			field: "paidAmount",
			width: 110,
			type: 'rightAligned',
			valueFormatter: params => params.data.paidAmount.toFixed(2),
			cellStyle: {
				color: 'black!important'
			}
		}, {

			headerName: 'Outstanding Amount',
			field: "outstandingAmount",
			width: 110,
			type: 'rightAligned',
			valueFormatter: params => params.data.outstandingAmount.toFixed(2),
			cellStyle: {
				color: 'black!important'
			}
		}, {

			headerName: 'Payment Status',
			field: "payStatus",
			width: 150,
			cellStyle: {
				textAlign: 'center'
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
				color: 'black!important'
			}
		}, {
			headerName: 'Scheduled date',
			field: "scheduleDate",
			width: 150,
			cellStyle: {
				textAlign: 'center',
				color: 'black!important'
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

function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	var floatValue = (parseFloat(value).toFixed(2));
	if (floatValue > 0) {
		$("#" + id).val(floatValue);
	} else {
		$("#" + id).val(0.00);
	}
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
					if (response.message == "Success") {
						swal({
							title: "Voucher Status",
							text: "Payment voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/payment-voucher";
						})
					} else {
						swal({
							title: response.code,
							text: response.message,
							type: "warning"
						})
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


function getFromSubtotal() {
	var sum = 0;
	$(".debitAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val());
	})

	$("#fromTotalAmount").html(sum.toFixed(2));
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val());
	})

	$("#toTotalAmount").html(sum.toFixed(2));
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
		swal("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		swal("Please Provide To Date");
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
				$("#paymentRegisterPdf").prop("disabled", true);
				$("#dwnldExcel").prop("disabled", true);
				$("#totalAmountFooter").val("0.00");
			} else {
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				//gridOptions.api.setRowData(allData);
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
					return acc + parseFloat(curr.totalAmount);
				}, 0);
				$("#paymentRegisterPdf").prop("disabled", false);
				$("#dwnldExcel").prop("disabled", false);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
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
			async: false,
			success: function(response) {
				var jsondata = JSON.parse(response.body);
				var alldata = jsondata.InvoiceDetails;
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
		swal("Please Select Invoices To Pay");
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
		swal("Please Select Invoices To Pay");
		return false;
	}
	var payAmount = $("#payingAmount").val();
	var remainAmount = $("#remainAmount").val();
	//console.log("addPayment()==="+payAmount)

	if (payAmount == '' || remainAmount == "NaN") {
		swal("Please Enter Some Amount");
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
	$("#totalPayableAmount").val(selectedPayableAmount.toFixed(2));


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

		var payAmount = $("#payingAmount").val();
		$("#bulkAmount").val(payAmount);
		$("#bulkCustAmount").val(payAmount);
		$("#payableAmountFinal").val(payAmount);
		checkBulkPayment();
	}
	else {
		$("#paymentPopup").modal('hide');
		$("#invPayment").modal("show");
		var payAmount = $("#bulkAmount").val();
		var newBulkAmount = parseFloat($("#payingAmount").val())
		$("#bulkAmount").val(newBulkAmount)
		$("#bulkCustAmount").val(newBulkAmount);
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
	var inputAmount = $("#payAmount").val();
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
	obj.payAmount = $("#payAmount").val();
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
			swal("Please Select Bank Account");
			return false
		}
	}

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

					swal({
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
}


function checkPayAmount() {
	var payAmount = $("#payingAmount").val();
	$("#remainAmount").val(payAmount);
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
								content += '<li style="font-weight:700;     padding: 5px 0px; color:#121212;     border-bottom: 1px solid white;  font-size:12px; background-color: #9ec5ff;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
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
	if (vendorId) {
		$("#vendorId").val(vendorId);
		$("#vendorName").val(vendorName);
		$("#search").val(vendorName);
		$("#search").attr('data-procat', vendorId);
		$("#suggesstion-box1_").hide();
		$("#adjustment").attr("disabled", false);

		$("#outstandingAmountDiv").show();
		$("#outstandingAmountView").val(outstandingAmt);

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
	obj.payAmount = $("#payingAmount").val();
	obj.bankSelectPayment = $("#bankSelectPaymentMethod").val();
	var methodOfAdj = $("#adjustment").val();
	if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
		obj.bankSelectPayment = "TLM0001";
	} else {
		if (obj.bankSelectPayment == "" || obj.bankSelectPayment == null || obj.bankSelectPayment == "null") {
			swal("Please Select Bank Account");
			return false;
		}
	}
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
						swal({
							title: "Success",
							text: "Advance Added Successfully!",
							type: "success"
						}).then(function() {
							location.reload();
						}
						);
					} else if (methodOfAdj == "newRef") {
						swal({
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
	$("#currentBalanceBank").val(selectedBalance)
	if (selectedBalance == null || selectedBalance == "" || selectedBalance == "null") {
		$("#updatedBalanceBank").val('');
	} else {
		var currentBulkAmount = parseFloat($("#bulkCustAmount").val()).toFixed(2);
		var currentRemainAmount = parseFloat(selectedBalance).toFixed(2) - currentBulkAmount;
		$("#updatedBalanceBank").val(currentRemainAmount.toFixed(2));
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

function generatePdfPaymentRegister() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var voucherType = "PAYMENT";
	window.open("/account/payment-register-Pdf?voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}


function downloadExcelFromGrid() {
	var selectedHeaders = [
		'Payment Voucher Id', 'TRANSACTION DATE', 'Invoice Id',
		'Payment Id', 'Cost Center Name', 'Vendor Name', 'Amount'
	];
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Payment_Register_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';
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
		'TRANSACTION DATE': "",
		'Invoice Id': "",
		'Payment Id': "",
		'Cost Center Name': "",
		'Vendor Name': "",
		'Amount': totalAmount
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Payment Register');
	XLSX.writeFile(wb, fileName);
}
