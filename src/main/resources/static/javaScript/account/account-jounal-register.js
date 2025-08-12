
$(function() {
	getCurrentFinancialYear();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

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
			headerName: 'Journal Voucher Id',
			field: "journalVoucher",
			width: 250,
			/*cellRenderer: function(params) {
				return '<a  class="journalRegisterId" onclick=editPage("'
					+ params.data.journalVoucher
					+ '") href="javascript:void(0)">'
					+ params.data.journalVoucher + '</a>';
			}*/
		},


		{
			headerName: "Cost Center Name",
			field: "costCenter",
			width: 150,
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: "Debit To",
			field: "debitAccountName",
			width: 250,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Credit To",
			field: "creditAccountName",
			width: 250,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Description",
			field: "description",
			width: 150,
			cellStyle: {
				textAlign: 'left'
			}
		},

		{
			headerName: 'Amount',
			field: 'totalAmount',
			width: 150,
			valueFormatter: params => amountFormatter(params.data.totalAmount.toFixed(2)),
			cellStyle: {
				textAlign: 'right'
			}
		},
		{
			headerName: "Created Date",
			field: "createdOn",
			width: 150,
			cellStyle: {
				textAlign: 'left'
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
	var journalRegisterId = selectedRows[0].journalVoucher;
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
		editPage(journalRegisterId);

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
	$("#myGrid").hide();


	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#fyearDiv").hide();
	$("#fdateDiv").hide();
	$("#tdateDiv").hide();
	$("#filterDiv").hide();
	$('#totalAmountFooterDiv').hide();
	$("#fdDiv").hide();
	$("#demo").show();
	$("#voucherNumber").html("");
	$("#toTotalAmount").html("");
	$("#fromTotalAmount").html("");
	$("#costCenter").val("");
	$("#voucherDate").val("");
	$("#description").val("");
	var today = new Date();
	var formattedDate = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
	$("#voucherDate").val(formattedDate);

	$("#creditTbodyData").empty();
	var abc = '<tr class="tr_clone" id="credit_0">'
		+ '<td><input type="text" placeholder="Search Ledger" id="creditAccountSubGroup_0" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);">'
		+ '<div id="suggesstion-box2_0"></div> <input type="hidden" id="creditAccountSubGroupId_0" class="form-control creditAccountGroupIdCls">'
		+/* '<p class="mb-0">'	
						+'Balance: ₹<span id="creditCurrentBalance_0" class="creditAccountGroupBalance"></span>'		
						+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="creditAmount_0" class="form-control creditAmountCls" onblur="changeToDecimal(this),getToSubtotal();" onkeyup="checkNumberInput(this.id)">'
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
		+ '<td><input type="text" placeholder="Search Ledger" id="debitAccountSubGroup_0" class="form-control debitAccountSubGroupCls" onkeyup="debitSubGroup(this.id);">'
		+ '<div id="suggesstion-box1_0"></div> <input type="hidden" id="debitAccountGroupId_0" class="form-control debitAccountGroupIdCls">'
		+/* '<p class="mb-0">'
									+'Balance: ₹<span id="debitCurrentBalance_0" class="debitAccountGroupBalance"></span>'
									+'</p> */ '</td>'
		+ '<td><input type="text" placeholder="Enter Amount" id="debitAmount_0" class="form-control debitAmountCls" onblur="changeToDecimal(this),getFromSubtotal() ;" onkeyup="checkNumberInput(this.id)">'
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
	//$("#journalRegisterPdf").removeClass('hideBtn');
	//$("#dwnldExcel").removeClass('hideBtn');


	/* current date filter data */
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();

	$("#toDate").val(todate);
	$("#fromDate").val(fromDate);
	viewFilteredData()

	/* agGrid.simpleHttpRequest({
		url : "view-account-journal-voucher-throughAjax"
	}).then(function(data) {
		console.log(JSON.stringify(data))
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewJournalVoucher;
		var len = allData.length;
		$('#totalReq').find('span').html(len);
		gridOptions.api.setRowData(allData);

	}); */
}


function checkNumberInput(id) {
	var tagname = $("#" + id).val()
	var replaceValue = tagname.replace(/[^\d.]/g, '');
	var decimalIndex = replaceValue.indexOf('.');
	if (decimalIndex !== -1) {
		replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '');
	}
	$("#" + id).val(replaceValue);
}


// Edit & stage change 
function editPage(id) {

	var editId = id.split(",");

	var journalVoucher = editId[0];

	var modal = editId[1];
	//	alert(id);

	//$("#journalRegisterPdf").addClass('hideBtn');
	//$("#dwnldExcel").addClass('hideBtn');

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
				$('#costCenter').val(response.body[0].costCenter);
				$('#description').val(response.body[0].description);
				$('#voucherNumber').text(response.body[0].journalVoucher);
				$('#journalVoucher').text(response.body[0].journalVoucher);
				$('#voucherDate').val(response.body[0].voucherDate);

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
							+ '<p class="mb-0" style="height:22px;"></p>'
							+ '</td>'
							/* +'<td>'
						  +'<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="debitCheckEmpty();">'
								  +'<span class="ti-plus"></span></button>&nbsp;</td>' */
							+ '</tr>';
						$("#debitTbodyData").append(abc);
					}
					if (response.body[i].transactionType == "Credit") {
						toTotalAmount = toTotalAmount + response.body[i].fromAmount;
						var amount = response.body[i].fromAmount;
						var abc = '<tr class="tr_clone" th:unless="${id}"   id="credit_' + i + '">'
							+ '<td><input type="text" id="creditAccountSubGroup_' + i + '" class="form-control creditAccountSubGroupCls" onkeyup="creditSubGroup(this.id);" value="' + response.body[i].subGroupName + '">'
							+ '<div id="suggesstion-box2_0"></div>'
							+ '<input type="hidden" id="creditAccountSubGroupId_' + i + '"  class="form-control creditAccountGroupIdCls" value="' + response.body[i].fromAccountSubGroup + '"/>'
							/* 	+'<p class="mb-0">Balance: ₹<span id="debitCurrentBalance_'+i+'" class="debitAccountGroupBalance">'+response.body[i].currentBalance+'</span>'
								+'</p>' */
							+ '</td>'
							/* +'<td><input type="text" id="creditName_'+i+'"	class="form-control creditNameCls" value="'+response.body[i].fromName+'"></td>' */
							+ '<td><input type="text" id="creditAmount_' + i + '" class="form-control creditAmountCls"  onblur="changeToDecimal(this),getToSubtotal() ;" value="' + amountFormatter((parseFloat(amount)).toFixed(2)) + '">'
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
//addVendorInfo



function addJournalInfo() {
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
		item['voucherType'] = "JOURNAL";
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
		item['voucherType'] = "JOURNAL";
		dataset.push(item);
	});//table tbody tr loop ends

	console.log(item);
	console.log("add journal dataset----------------------" + JSON.stringify(dataset));
	//return false;
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
		url: "view-account-journal-voucher-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "view-account-journal-voucher"
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
						content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: blue; color: white" onClick="autocompleteValue1(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box1_" + counter).show();
					$("#suggesstion-box1_" + counter).html(content);

				}
				else {
					console.log("else: " + response);
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: blue; color: white" onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="debitLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
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
					for (var i = 0; i < response.body.length; i++) {
						content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: blue; color: white" onClick="autocompleteValue2(\'' + response.body[i].debAccGroupId + '\',\'' + response.body[i].debitedAccGroup + '\',\'' + id + '\',\'' + response.body[i].amount + '\')">' + response.body[i].debitedAccGroup + '(' + response.body[i].debAccGroupId + ')' + '</li>';
					}
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
					content += '</ul>';
					$("#suggesstion-box2_" + counter).show();
					$("#suggesstion-box2_" + counter).html(content);

				}
				else {
					console.log("else: " + response);
					$("#creditAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: blue; color: white" onClick="autocompleteValue2(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					content += '<li style="margin-left: -26px; border-bottom: solid 1px #6189c2; cursor: pointer; background-color: #0c0c6b; color: white" id="creditLedger" onclick="addLedger(this.id)">+ Add Ledger</button></li>';
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
//add More at edit time
$(document).ready(function() {
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




	//Function for getting Table Name on change of spa Name
});

function changeToDecimal(elemt) {
	id = $(elemt).attr('id');
	value = $("#" + id).val();
	var floatValue = (parseFloat(value).toFixed(2));
	if (isNaN(floatValue)) {
		floatValue = 0.00;
	}
	if (id.includes('debitAmount')) {
		$("#" + id).val(floatValue);
		var counter = id.split('_')[1];
		$("#creditAmount_" + counter).val(floatValue);
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
					if (response.message == "Success") {
						swal({
							title: "Voucher Status",
							text: "Journal voucher created successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "/account/view-account-journal-voucher";
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
	getToSubtotal();
}
function getToSubtotal() {
	var sum = 0;
	$(".creditAmountCls").each(function(i) {
		sum = sum + parseFloat($(this).val());
	})

	$("#toTotalAmount").html(sum.toFixed(2));
}

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
								content += '<li style=" font-weight:bold; font-size:14px; color:#ccc;background-color: #85a3ce;border-bottom: solid 1px #6189c2;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
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
							content += '<li style=" font-weight:bold; font-size:14px; color:#ccc;background-color: #85a3ce;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue1()">'
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
						content += '<li style="margin-left:-26px; font-weight:bold; font-size:14px; color:#ccc;background-color: #85a3ce;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue1()">'
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
		content += '<li style="margin-left:-26px; font-weight:bold; font-size:14px; color:#ccc;background-color: blue;border-bottom: solid 1px #6189c2;" onClick="selectAutocompleteValue1()">'
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

							swal("Ledger added successfully!", " ", "success");
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
			url: "view-account-journal-voucher-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
		}).then(function(data) {
			console.log(JSON.stringify(data))
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewJournalVoucher;
			if (allData == "" || allData == "null" || allData == null) {
				$('#totalReq').find('span').html('0');
				gridOptions.api.setRowData();
				//	$("#journalRegisterPdf").prop("disabled", true);
				//	$("#dwnldExcel").prop("disabled", true);
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
				//	$("#journalRegisterPdf").prop("disabled", false);
				//	$("#dwnldExcel").prop("disabled", false);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
			}
		});

	}
}

function generatePdfJournalRegister() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var voucherType = "JOURNAL";
	var ActivityType = "JOURNAL REGISTER";
	window.open("/account/journal-register-Pdf?voucherType=" + voucherType + "&activityType=" + ActivityType + "&fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}

function downloadExcelFromGrid() {
	var selectedHeaders = ['Journal Voucher Id', 'Cost Center Name', 'Debit To', 'Credit To', 'Description', 'Amount', 'Created Date'];

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fileName = 'Journal_Register_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';

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
		'Cost Center Name': "",
		'Debit To': "",
		'Credit To': "",
		'Description': "",
		'Amount': totalAmount,
		'Created Date': ""
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Journal Register');
	XLSX.writeFile(wb, fileName);
}
