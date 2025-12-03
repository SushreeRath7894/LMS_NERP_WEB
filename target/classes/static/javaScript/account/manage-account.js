
$(function() {


	$("#accountType").select2({
		placeholder: "Select Account Type",
		allowClear: true
	});

	$("#status").select2({
		placeholder: "Select Status",
		allowClear: true
	});

	$("input[name=ReminderYesOrNo]:radio").click(function() {
		if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
			$('.reminderBtn').show();

		} else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
			$('.reminderBtn').hide();
			$('#reminderDateid').val("");
			$('#reminderTime').val("");
			$('#taskAlertBy').val("");

		}
	});


	var dateFormat = localStorage.getItem("dateFormat");
	$("#startDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#startDate').val($(this).val());
	})

	$('#startDate').blur(function() {
		$("#startDateCalendar").val($(this).val());
	})


	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})

	$('#endDate').blur(function() {
		$("#endDateCalendar").val($(this).val());
	})




	$("#toDateCalendarTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#reminderTime').val($(this).val());
	})

	$('#reminderTime').blur(function() {
		$("#toDateCalendarTime").val($(this).val());
	})
});

$(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	fetchAccountInfo();


	$("#myGrid").show();
	$("#delete").attr("disabled", true);

	disableAllFields();
});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})
//search bar

/*function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}
*/


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
// column Defs
const columnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Account Number",
		field: "accountNumber",
		pinned: 'left',
	}, {

		headerName: "IFSC CODE",
		field: "ifscNumber",
		width: 110,
	}, {
		headerName: "Bank Name",
		field: "bankName",
		width: 150,
	},
	{
		headerName: "Branch Name",
		field: "branchName",
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Account Holder",
		field: "accountHolder",
		width: 150,
	},
	{
		headerName: "Account Type",
		field: "accountType",
	}, {
		headerName: "Status",
		field: "status",
	}, {
		headerName: 'Account Id',
		field: "accountId",
	}, {
		headerName: "Created Date",
		field: "createdDate",
	}];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 130,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	pagination: true,
	paginationPageSize: 15,
	getRowNodeId: function(data) {
		return data.accountId;
	}
};


function statusFormatter(params) {
	return params.value === 'true' ? 'Active' : 'Inactive';
}
var deleteId = "";
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();

	console.log("Selected rows-->", selectedRows);
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '"' + selectedRows[i].accountId + '",';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	console.log(deleteId)
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		var accountid = selectedRows[0].accountId;
		$('#delete').attr("disabled", false);
		$('#add').attr("disabled", true);
		$("#add-btn").removeClass("d-none");
		$("#accountEditbtn").removeClass("d-none");
		$("#parentDeleteBtn").removeClass("d-none");

		$("#bankAccountName").text(selectedRows[0].bankName);

		editPage(accountid);
		disableAllFields();
		$("#parentCancelBtn").addClass("d-none");
		$("#parentSaveBtn").addClass("d-none");
	} else {
		$('#delete').attr("disabled", true);
		$('#add').attr("disabled", false);
		$("#accountEditId").text('');
		$("#bankAccountName").text('');
		uncheckCheckbox();
	}
}



function uncheckCheckbox() {

	$("#accountId").val("");
	$("#accountId").text("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("").trigger('change');
	$('#accountNumber').val("");
	$('#ifscNumber').val("");
	$('#status').val("").trigger('change');
	$('#openingBalance').val("");

	$("#BankAccountName").val('');
	$("#bankNameId").val('');

	$("#bankName").attr("disabled", false);
	$("#branchName").attr("disabled", false);
	$("#accountHolder").attr("disabled", false);
	$("#accountType").attr("disabled", false);
	$("#accountNumber").attr("disabled", false);
	$("#ifscNumber").attr("disabled", false);
	$("#openingBalance").attr("disabled", false);
	$("#status").attr("disabled", false);
	$("#BankAccountName").attr("disabled", false);

	$("#parentCancelBtn").removeClass("d-none");
	$("#parentSaveBtn").removeClass("d-none");
	$("#add-btn").addClass("d-none");
	$("#accountEditbtn").addClass("d-none");
	$("#parentDeleteBtn").addClass("d-none");
}

// for new button
function newBtn() {
	//	alert('hello');
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").show();

	$("#accountId").val("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("").trigger('change');
	$('#accountNumber').val("");
	$('#ifscNumber').val("");
	$('#status').val("").trigger('change');
	$('#openingBalance').val("");

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

	$("#accountId").val("");
	$('#bankName').val("");
	$('#accountHolder').val("");
	$('#branchName').val("");
	$('#accountType').val("").trigger('change');
	$('#accountNumber').val("");
	$('#ifscNumber').val("");
	$('#status').val("").trigger('change');
	$('#openingBalance').val("");
	$('#add').attr("disabled", false);

	agGrid.simpleHttpRequest({
		url: "view-account-throughAjax"
	}).then(function(data) {
		gridOptions.api.setRowData(data);
	});
}

$(document).ready(function() {

	$("#addLedgerModal").modal('hide');
	$('#addBankBranchModal').modal('hide');

	$("#date").datetimepicker({
		format: "d-m-Y",
		closeOnDateSelect: true,
		minDate: new Date(),
		timepicker: false,
	});
	$("#accountId").val("");

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})

	$("#fromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$("#toTime").datetimepicker({
		format: "H:i",
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

});


function brnachNameList(bankingId, branchId) {


	$.ajax({
		type: "GET",
		url: "view-account-get-branch?id=" + bankingId,
		success: function(response) {
			if (response.message == "success") {
				$("#branchName").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select Branch");
				$("#branchName").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#branchName").append(option);
				}

				$("#branchName").val(branchId);


			}
		},
		error: function(e) {
		}
	});

}


// Edit & stage change 
var globalAccountId = "";
function editPage(id) {
	var accountId = id;
	$("#accountId").text(id);


	globalAccountId = accountId;

	$.ajax({
		type: "GET",
		url: "view-account-edit?id=" + accountId,
		async: false,
		success: function(response) {
			console.log("response for edit------" + JSON.stringify(response));
			if (response.code == "Success") {
				$("#accountId").text(accountId);

				$('#bankNameId').val(response.body[0].journalVoucher);
				$("#BankAccountName").val(response.body[0].costCenter);

				var bankingId = response.body[0].journalVoucher;
				var branchId = response.body[0].description;
				brnachNameList(bankingId, branchId);

				$('#accountHolder').val(response.body[0].fromAccountSubGroup);
				$('#branchName').val(response.body[0].description);
				$('#accountType').val(response.body[0].fromName).trigger('change');
				$('#accountNumber').val(response.body[0].toName);
				$('#ifscNumber').val(response.body[0].createdOn);
				$('#status').val(response.body[0].toAccountSubGroup).trigger('change');
				$('#openingBalance').val(amountFormatter(response.body[0].createdBy));
			}
		}
	})
}



//addVendorInfo

function addAccountInfo() {
	//alert('hello');return false;
	$(".loader").show();
	var obj = {};

	obj.accountId = $('#accountId').text();
	obj.bankName = $('#bankNameId').val();
	obj.accountHolder = $('#accountHolder').val();
	obj.branchName = $('#branchName').val();
	obj.accountType = $('#accountType').val();
	obj.accountNumber = $('#accountNumber').val();
	obj.ifscNumber = $('#ifscNumber').val();
	obj.status = $('#status').val();
	obj.openingBalance = $('#openingBalance').val().replace(/,/g, '');
	console.log("object on add account-----------" + JSON.stringify(obj));
	//return false;

	/* FORM VALIDATION STARTS*/





	var validation = true;

	if (obj.bankName === null || obj.bankName === "") {
		$(".loader").hide();
		toastr.error("Bank Name Required");
		return false;
	}

	if (obj.accountHolder === null || obj.accountHolder === "") {
		$(".loader").hide();
		toastr.error("Account Holder Name Required");
		return false;
	}
	if (obj.accountNumber === null || obj.accountNumber === "") {
		$(".loader").hide();
		toastr.error("Account Number Required");
		return false;
	}
	if (obj.ifscNumber === null || obj.ifscNumber === "") {
		$(".loader").hide();
		toastr.error("IFSC Code Required");
		return false;
	}
	if (!validateIfsc()) {
		$(".loader").hide();
		return false;
	}



	//validation check for the duplicate account number


	let agGridRows = [];
	gridOptions.api.forEachNode(function(node) {
		agGridRows.push(node.data);
	});



	let duplicateExists = agGridRows.some((element) => {
		return element.accountNumber === $('#accountNumber').val() && $("#accountId").text() === "";
	});

	if (duplicateExists) {
		$(".loader").hide();
		toastr.error("Bank account number already exists.");
		return false;
	}



	console.log("object --->", obj);

	/* FORM VALIDATION ENDS*/



	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-account-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "201" || response.code == "200") {
					toastr.success(response.message);

					setTimeout(() => {
						gridOptions.api.forEachNode((node) => {
							if (obj.accountId == "") {
								let firstRow = gridOptions.api.getDisplayedRowAtIndex(0);
								if (firstRow) {
									firstRow.setSelected(true);
								}
							} else if (node.data.accountId == obj.accountId) {
								node.setSelected(true);
							}
						});
					}, 1000);

					$("#bankName").val('');
					$("#branchName").val('');
					$("#accountHolder").val('');
					$("#accountType").val('').trigger('change');
					$("#accountNumber").val('');
					$('#openingBalance').val("");
					$("#ifscNumber").val('');
					$("#status").val('').trigger('change');

					$(".loader").hide();
					$("#add").show();
					$("#copy").show();
					$("#delete").show();
					$("#totalReq").show();
					$("#myGrid").show();
					$("#searchRowDiv").show();
					$("#demo").hide();
					$("#parentSaveBtn").addClass("d-none");
					$("#parentCancelBtn").addClass("d-none");

					agGrid.simpleHttpRequest({
						url: "view-account-throughAjax"
					}).then(function(data) {
						var len = data.length;
						$('#totalReq').find('span').html(len);

						var rowData = [];
						gridOptions.api.setRowData(rowData);

						gridOptions.api.setRowData(data);
					});

				}
				else {
					toastr.error(response.message);
				}
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}



function deleteAccount() {
	//console.log("delete ids--------------"+deleteId);

	globalAccountId = "";
	var updatedId = $("#accountId").text();
	$.ajax({
		type: "GET",
		url: "view-account-delete-id?id=" + updatedId,
		success: function(response) {
			if (response.code == "200") {
				toastr.success(response.message);
				fetchAccountInfo();

			}
			else {
				toastr.error(response.message);
			}
		}

	});

	$('#delete').attr("disabled", true);
}



function saveFile() {
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
		url: "view-crm-vendors-upload-file",
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

function deleteFile() {

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-crm-vendors-delete-file",
		enctype: "multipart/form-data",
		contentType: false,
		/* data        : fileData, */
		processData: false,
		cache: false,
		success: function(response) {
		},
		error: function(e) {

		}
	});
}


function getStateDetails() {

	var cname = $('#country').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-account-branch-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDataOnEdit(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-crm-leads-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
					$("#states").val(stateId);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
	}
}


function addnewbank() {


	console.log("ok done");
	$('#bankName').val('');
	$('#addLedgerModal').modal('show');
}



function cancelLedgerBtn() {

	$("#addLedgerModal").modal('hide');
	$('#addBankBranchModal').modal('hide');
	$('#bankName').val('');

	$(".formValidation:contains('Bank Description Required'):first").hide();
	$(".formValidation:contains('Bank Status Required'):first").hide();
	$(".formValidation:contains('Bank Name Required'):first").hide();

}

function cancelbranchmodal() {
	$('#addBankBranchModal').modal('hide');
	$("#branchName").val('');


	$(".formValidation:contains('Ifsc Code Required'):first").hide();
	$(".formValidation:contains('Contact Number Required'):first").hide();
	$(".formValidation:contains('Email Required'):first").hide();
	$(".formValidation:contains('City Required'):first").hide();
	$(".formValidation:contains('Zip Code Required'):first").hide();



}


function getallbankname() {

	$.ajax({
		type: "GET",
		url: "view-account-get-bankname",
		success: function(response) {
			if (response.message == "success") {
				$("#bankName").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select Bank");
				$("#bankName").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#bankName").append(option);
				}
				var addBankOption = $("<option></option>");
				$(addBankOption).val("addbank");
				$(addBankOption).html("+ Add Bank");
				$("#bankName").append(addBankOption);

			}
		},
		error: function(e) {
		}
	});

}


function saveLedgerDetails() {

	var obj = {};

	obj.bankId = $('#bankId').text();
	obj.bankName = $('#addbankName').val();
	obj.status = $('#bankstatus').val();
	obj.description = $('#description').val();

	console.log(obj)

	var validation = true;

	if (obj.bankName == null || obj.bankName == "") {
		validation = validationUpdated("Bank Name Required",
			"addbankName");
	}

	if (obj.bankstatus == null || obj.bankstatus == "") {
		validation = validationUpdated("Bank Status Required",
			"bankstatus");
	}

	if (obj.bankdescription == null || obj.bankdescription == "") {
		validation = validationUpdated("Bank Description Required",
			"description");
	}

	if (validation) {
		$("#addLedgerModal").modal('hide');
		$('#bankName').val('');
		$('#addbankName').val('');
		$('#bankstatus').val('');
		$('#description').val('');
		$('#charNumSN').find('span').html(0);

		/* ajax */

		$.ajax({
			type: "POST",
			url: "view-account-bank-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {

				getallbankname();

			},
			error: function(data) {

				//console.log(data);
			}
		});
	}


}


function addbankbranch2() {

	var bankbranchval = $("#branchName").val();
	var selectedOption = $("#branchName").find("option:selected");
	var branchIfscCode = selectedOption.data("code");
	$("#ifscNumber").val(branchIfscCode);
	if (bankbranchval == "addbankbranch") {
		$('#addBankBranchModal').modal('show');

	}
}



function getbranchname() {



	var bankid = $("#bankName").val();

	if (bankid == "addbank") {
		addnewbank();

	}



	$.ajax({
		type: "GET",
		url: "view-account-get-branch?id=" + bankid,
		success: function(response) {
			if (response.message == "success") {
				$("#branchName").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select Branch");
				$("#branchName").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code", response.body[i].code);
					$("#branchName").append(option);
				}

				var addBankOption = $("<option></option>");
				$(addBankOption).val("addbankbranch");
				$(addBankOption).html("+ Add Bank Branch");
				$("#branchName").append(addBankOption);


			}
		},
		error: function(e) {
		}
	});
}


function savebranchdetails() {

	var bankid = $("#bankName").val();


	var obj = {};
	obj.branchId = "";
	obj.bank = $('#bankName').val();
	obj.branchName = $('#addbranchName').val();
	obj.ifscCode = $('#ifscCode').val();
	obj.contactNo = $('#contactNo').val();
	obj.email = $('#email').val();
	obj.status = $('#addBranchStatus').val();
	obj.country = $('#country').val();
	obj.states = $('#states').val();
	obj.city = $('#city').val();
	obj.addressStreet = $('#addressStreet').val();
	obj.zip = $('#zip').val();
	obj.address = $('#address').val();

	console.log(obj, "......................");


	var validation = true;

	if (obj.bank == null || obj.bank == "") {
		validation = validationUpdated("Bank Name Required",
			"bank");
	}

	if (obj.status == null || obj.status == "") {
		validation = validationUpdated("Bank Status Required",
			"status");
	}


	if (obj.branchName == null || obj.branchName == "") {
		validation = validationUpdated("Branch Name Required",
			"branchName");
	}


	if (obj.ifscCode == null || obj.ifscCode == "") {
		validation = validationUpdated("Ifsc Code Required",
			"ifscCode");
	}

	if (obj.zip == null || obj.zip == "") {
		validation = validationUpdated("Zip Code Required",
			"zip");
	}

	if (obj.contactNo == null || obj.contactNo == "") {
		validation = validationUpdated("Contact Number Required",
			"contactNo");
	}


	if (obj.email == null || obj.email == "") {
		validation = validationUpdated("Email Required",
			"email");
	}

	if (obj.city == null || obj.city == "") {
		validation = validationUpdated("City Required",
			"city");
	}



	/* FORM VALIDATION ENDS*/

	if (validation) {

		$.ajax({
			type: "POST",
			url: "view-account-branch-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				$('#addBankBranchModal').modal('hide');
				$("#branchName").val('')

				/* function call for branchlist */
				$.ajax({
					type: "GET",
					url: "view-account-get-branch?id=" + bankid,
					success: function(response) {
						if (response.message == "success") {


							$('#addbranchName').val('');
							$('#ifscCode').val('');
							$('#contactNo').val('');
							$('#email').val('');
							$('#status').val('');
							$('#country').val('');
							$('#states').val('');
							$('#city').val('');
							$('#addressStreet').val('');
							$('#zip').val('');
							$('#address').val('');
							$("#branchName").empty();
							var option = $("<option></option>");
							$(option).val(null);
							$(option).html("Select Branch");
							$("#branchName").append(option);
							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option).val(response.body[i].key);
								$(option).html(response.body[i].name);
								$(option).attr("data-code", response.body[i].code);
								$("#branchName").append(option);
							}

							var addBankOption = $("<option></option>");
							$(addBankOption).val("addbankbranch");
							$(addBankOption).html("+ Add Bank Branch");
							$("#branchName").append(addBankOption);


						}
					},
					error: function(e) {
					}
				});

			},
			error: function(data) {


			}
		})
	}
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


function newFolderDetailsAdd() {

	$("#BankAccountName").val('');
	$("#bankNameId").val('');
	$("#branchName").val('');
	$("#accountHolder").val('');
	$("#accountType").val('').trigger('change');
	$("#accountNumber").val('');
	$("#ifscNumber").val('');
	$("#openingBalance").val('');
	$("#status").val('').trigger('change');
	$("#accountEditId").text('');
	globalAccountId = "";
	editFolder();
	$("#accountId").text('');
	gridOptions.api.deselectAll();

	$("#add-btn").addClass("d-none");
	$("#parentSaveBtn").removeClass("d-none");
	$("#parentCancelBtn").removeClass("d-none");
	$("#accountEditbtn").addClass("d-none");
	$("#parentDeleteBtn").addClass("d-none");
}


function disableAllFields() {
	$("#bankName").attr("disabled", true);
	$("#branchName").attr("disabled", true);
	$("#accountHolder").attr("disabled", true);
	$("#accountType").attr("disabled", true);
	$("#accountNumber").attr("disabled", true);
	$("#ifscNumber").attr("disabled", true);
	$("#openingBalance").attr("disabled", true);
	$("#status").attr("disabled", true);
	$("#BankAccountName").attr("disabled", true);
}



/*function cancelAccountBtn() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	let accountId = selectedData.length > 0 ? selectedData[0].accountId : null;

	disableAllFields();

	let matchedNode = null;
	gridOptions.api.deselectAll();
	if (accountId) {
		gridOptions.api.forEachNode(function(node) {
			if (node.data && node.data.accountId === accountId) {
				matchedNode = node;
			}
		});
	}
	if (matchedNode) {
		matchedNode.setSelected(true);
	} else {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	}
	// Button visibility logic
	$("#add-btn").removeClass("d-none");
	$("#parentSaveBtn").addClass("d-none");
	$("#parentCancelBtn").addClass("d-none");
	$("#accountEditbtn").removeClass("d-none");
	$("#parentDeleteBtn").removeClass("d-none");
	$("#suggesstion-box1_0").hide();
}
*/

function cancelAccountBtn() {

	let leadgerId = globalAccountId;
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		$("#add-btn").removeClass("d-none");
		$("#accountEditbtn").removeClass("d-none");
		$("#parentDeleteBtn").removeClass("d-none");
		$("#parentSaveBtn").addClass("d-none");
		$("#parentCancelBtn").addClass("d-none");
		disableAllFields();
		console.log("ledger id-->", leadgerId);
		if (leadgerId) {
			gridOptions.api.forEachNode(function(node) {
				if (node.data && node.data.accountId === leadgerId) {
					node.setSelected(true);
					return;
				}
			});
		}
		else {
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
			firstRowNode.setSelected(true);
		}
	}
	else {
		$("#add-btn").removeClass("d-none");
		$("#parentSaveBtn").addClass("d-none");
		$("#parentCancelBtn").addClass("d-none");
		$("#accountEditbtn").removeClass("d-none");
		$("#parentDeleteBtn").removeClass("d-none");
		$("#suggesstion-box1_0").hide();
	}
}



function editFolder() {
	$("#bankName").attr("disabled", false);
	$("#branchName").attr("disabled", false);
	$("#accountHolder").attr("disabled", false);
	$("#accountType").attr("disabled", false);
	$("#accountNumber").attr("disabled", false);
	$("#ifscNumber").attr("disabled", false);
	$("#openingBalance").attr("disabled", false);
	$("#status").attr("disabled", false);
	$("#BankAccountName").attr("disabled", false);


	$("#parentCancelBtn").removeClass("d-none");
	$("#parentSaveBtn").removeClass("d-none");
	$("#parentDeleteBtn").addClass("d-none");
	$("#accountEditbtn").addClass("d-none");
	$("#add-btn").addClass("d-none")
}

function fetchAccountInfo() {
	let exitAccountId = globalAccountId;
	agGrid.simpleHttpRequest({
		url: "view-account-throughAjax"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);


		var rowData = [];
		gridOptions.api.setRowData(rowData);
		gridOptions.api.setRowData(data);

		if (data && data.length > 0) {
			/*gridOptions.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});*/

			if (exitAccountId) {
				let targetIndex = -1;

				gridOptions.api.forEachNode((node, index) => {
					if (node.data && node.data.accountId === exitAccountId) {
						targetIndex = node.rowIndex;
					}
				});

				if (targetIndex !== -1) {
					const pageSize = gridOptions.api.paginationGetPageSize();
					const targetPage = Math.floor(targetIndex / pageSize);

					gridOptions.api.paginationGoToPage(targetPage);

					gridOptions.api.forEachNode((node) => {
						if (node.data && node.data.accountId === exitAccountId) {
							node.setSelected(true);
							gridOptions.api.ensureIndexVisible(node.rowIndex, 'middle'); // Optional: scroll to center
						}
					});
				}
			} else {
				const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
			}
		}
		else {
			console.log("data is not present-->", data);
			$("#add-btn").removeClass("d-none");
			$("#accountEditbtn").addClass("d-none");
			$("#parentDeleteBtn").addClass("d-none");


			$("#BankAccountName").val('');
			$("#bankNameId").val('');
			$("#branchName").val('');
			$("#accountHolder").val('');
			$("#accountType").val('').trigger('change');
			$("#accountNumber").val('');
			$("#ifscNumber").val('');
			$("#openingBalance").val('');
			$("#status").val('').trigger('change');
			$("#accountEditId").text('');
			globalAccountId = "";
		}
	});
}


function getBankAccountName(id) {

	var l = id.split("_");
	var counter = l[1];

	var search = $("#" + id).val();
	$.ajax({
		type: "POST",
		url: "view-account-get-bankName",
		dataType: 'json',
		contentType: 'application/json',
		data: $("#" + id).val(),
		success: function(response) {
			console.log("----->>>>>>> -------->>>>>>> ", response);
			if (response.message == "success") {

				if (response.body.length != 0) {
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					for (var i = 0; i < response.body.length; i++) {
						content += '<li  class="autocompletedata cp" onClick="autocompleteValue1(\'' + response.body[i].bankName + '\', \'' + response.body[i].bankId + '\', \'' + response.body[i].description + '\')">' + response.body[i].bankName + '</li>';
					}
					content += '</ul>';

					$("#suggesstion-box1_0").show();
					$("#suggesstion-box1_0").html(content);

					//console.log("content-->", content);

				}
				else {
					console.log("else: " + response);
					$("#debitAccountSubGroup_0").css("background", "#FFF");
					var content = '<ul id="autocomplete-list">';
					content += '<li  class ="autocompletedata cp" onClick="autocompleteValue1(\'' + '' + '\',\'' + '' + '\')">' + "No Data Found" + '</li>';
					content += '</ul>';
					$("#suggesstion-box1_0").show();
					$("#suggesstion-box1_0").html(content);
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

function autocompleteValue1(bankName, bankId, bankDesc, amount) {

	if (bankId) {
		$("#bankNameId").val(bankId);
		$("#BankAccountName").val(bankName)
		$("#suggesstion-box1_0").hide();


	} else {
		$("#bankNameId").val('');
		$("#BankAccountName").val('')
		$("#suggesstion-box1_0").hide();
	}
}

function validateIfsc() {
	const ifscCode = $("#ifscNumber").val().trim();
	const ifscError = $("#ifscError");
	var bankName = $("#BankAccountName").val();

	const ifscRegex = /^[A-Z]{4}0[A-Z0-9]{6}$/;

	if (ifscRegex.test(ifscCode)) {
		ifscError.hide();
		let isValid = true;
		// Call Razorpay IFSC API
		$.ajax({
			url: `https://ifsc.razorpay.com/${ifscCode}`,
			type: "GET",
			async: false,
			success: function(data) {
				console.log("API Response:", data);
				if (data) {
					$("#branchName").val(data.BRANCH);
					var responseBankName = data.BANK;
					if (!bankName.includes(responseBankName)) {
						toastr.error(`Error: The entered IFSC code '${responseBankName}' does not match the bank name '${bankName}'. Please verify and try again.`).show();
						$("#ifscNumber").val('');
						$("#branchName").val('');
						isValid = false;
					}
				}
			},
			error: function() {
				ifscError.text("No branch found for this IFSC code.").show();
				isValid = false;
			}
		});

		return isValid;
	} else {
		ifscError.text("Invalid IFSC code. Please enter a valid code.").show();
		return false;
	}
}



function generateNotificationBox(invalidField) {
	let notificationHtml = `
    <div class="notification" id="errorNotification">
        <div class="notification-icon">❌</div>
        <div class="notification-message">${invalidField} is invalid. Please try again.</div>
        <button class="notification-close" onclick="cancelNotification(event)" id="closeNotification">CLOSE</button>
    </div>`;
	var notificationPrentDiv = $("#notificationBox");
	console.log("elementis -->", notificationPrentDiv);
	notificationPrentDiv.html(notificationHtml);
}

function cancelNotification(event) {
	event.preventDefault(); // Prevent the default behavior
	const errorNotification = document.getElementById('errorNotification');
	errorNotification.style.animation = 'slideOut 0.5s ease forwards';
	setTimeout(() => {
		errorNotification.style.display = 'none'; // Hide notification after animation
	}, 500); // Match the animation duration
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
