$(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	gridOptions.api.setRowData();


	$("#myGrid").show();
	$("#delete").attr("disabled", true);
	$('#paymentApprove').attr("disabled", true);

	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var todate = toDateString.toString();
	$("#toDate").val(todate);
	$("#fromDate").val(todate);
	viewFilteredData();
	var dateFormat = localStorage.getItem("dateFormat");

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})
	$('#fromDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
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
});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})
//search bar

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var length = gridOptions.api.getModel().getRowCount();
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
const columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		//checkboxSelection : true,
		checkboxSelection: function(params) {
			const approveStatus = params.data.approvalStatus;
			return approveStatus !== 'Approved' && approveStatus !== 'Rejected';
		},
		width: 8,
		sortable: false,
		filter: false,
		resizable: true

	}, {
		headerName: 'Payment Id',
		field: "paymentId",
		width: 140,
		cellRenderer: function(params) {
			return '<a onclick=getPaymentDetails("' + params.data.paymentId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.paymentId + '</a>';
		}
	}, {
		headerName: "GRN Id",
		field: "vendorGrnId",
		width: 140,
	}, {
		headerName: "Vendor Name",
		field: "vendorId",
		width: 230,
	}, {
		headerName: "Amount Sent",
		field: "vendorGrnAmount",
		width: 140,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			const formattedValue = amountFormatter(parseFloat(params.data.vendorGrnAmount).toFixed(2));
			return '<div style="color:black;font-weight: bold;">' +
				formattedValue +
				'</div>';
		}

	}, {
		headerName: "Payment Status",
		field: "payStatus",
		width: 140,
	}, {
		headerName: "Approval Status",
		field: "approvalStatus",
		width: 140,
		hide: true,
		cellRenderer: function(params) {
			if (params.data.approvalStatus == "Pending") {
				return '<div style="color:red;font-weight: bold;">' + params.data.approvalStatus + '</div>';
			} else {
				return '<div style="color:#0642f5;font-weight: bold;">' + params.data.approvalStatus + '</div>';
			}
		}
	}, {
		headerName: "Approved By",
		field: "approvedBy",
		hide: true,
		width: 140,
	}, {
		headerName: "Sent By",
		field: "sentBy",
		width: 140,
	}, {
		headerName: "Payment Date & Time",
		field: "paymentDate",
		width: 230,
	}];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.paymentId;
	}
};


var deleteId = "";
function rowSelect() {
	//alert('hello select');
	var selectedRows = gridOptions.api.getSelectedRows();
	var payId = selectedRows[0].paymentId;
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '"' + selectedRows[i].paymentId + '",';
		// deleteId = deleteId  + selectedRows[i].incentiveId + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	console.log(deleteId)
	var rowCount = 0;

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		if (selectedData.map(node => node.approvalStatus) == "Approved") {
			$('#paymentApprove').attr("disabled", true);
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", true);
		} else {
			$('#paymentApprove').attr("disabled", false);
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		}

		getPaymentDetails(payId);

	} else {
		$('#delete').attr("disabled", true);
		$('#add').attr("disabled", false);
		$('#paymentApprove').attr("disabled", true);
	}
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
	$("#clubMemberId").empty();
	$("#paymentApprove").hide();


	$("#vendorName").val("");
	$("#vendorId").val("");

	$("#vendorGrnAmount").val("");
	$("#vendorGrnPendingAmount").val("");
	$("#vendorGrnBulkAmount").val("");
	$("#vendorBankName").val("");
	$("#vendorBankBranch").val("");
	$("#vendorAccountNumber").val("");
	$("#vendorTransactionNumber").val("");
	$("#vendorRemarks").val("");
	$("#vendorGrnId").empty();
	var option = $("<option></option>");
	$(option).val(null);
	$(option).html("Select");
	$("#vendorGrnId").append(option);
	$("#demo").show();

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
	$("#paymentApprove").show();
	$('#delete').attr("disabled", true);
	$('#add').attr("disabled", false);
	$('#paymentApprove').attr("disabled", true);
	$('#clubMemberId').val("");
	$('#clubMemberName').val("");
	$('#formatCode').val("");
	$('#effectiveStartDate').val("");
	$('#effectiveEndDate').val("");
	$('#salesRangeFrom').val("");
	$('#salesRangeTo').val("");
	$('#tradeDiscountRemark').val("");
	$('#status').val("");

	gridOptions.api.setRowData();
	viewFilteredData();
}


// Edit & stage change 
function editPage(id) {
	//alert(id);
	var editId = id.split(",");

	var paymentId = editId[0];
	var modal = editId[1];
	//alert('colorId------'+colorId);
	$("#demo").show();
	$("#add").hide();
	$("#copy").hide();
	$("#save").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").show();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#paymentApprove").hide();

	$.ajax({
		type: "GET",
		url: "view-send-amount-getDataEdit?id=" + paymentId,
		async: false,
		success: function(response) {
			//console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {
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

				$(".container").hide();

				$("#paymentId").html(response.body[0].paymentId);
				$("#vendorId").val(response.body[0].vendorId);
				$("#vendorName").val(response.body[0].sentTo);
				$("#vendorGrnId").val(response.body[0].vendorGrnId);
				$("#vendorGrnAmount").val(response.body[0].vendorGrnAmount);
				$("#vendorBankName").val(response.body[0].vendorBankName);
				$("#vendorBankBranch").val(response.body[0].vendorBankBranch);
				$("#vendorAccountNumber").val(response.body[0].vendorAccountNumber);
				$("#vendorTransactionNumber").val(response.body[0].vendorTransactionNumber);
				$("#vendorRemarks").val(response.body[0].vendorRemarks);

				$("#vendorGrnId").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html(response.body[0].vendorGrnId);
				$("#vendorGrnId").append(option);
			}

		}
	})
}

function viewFilteredData() {

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	agGrid.simpleHttpRequest({
		url: "view-send-amount-through-ajax?fromdate=" + fromDate + "&todate=" + toDate
	}).then(function(data) {
		$(".loader").hide();
		var len = data.length;
		$('#totalReq').find('span').html(len);
		var rowData = [];
		gridOptions.api.setRowData(rowData);

		gridOptions.api.setRowData(data);

		if (data && data.length > 0) {
			gridOptions.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});
		}


		//gridOptions.api.setRowData(data);

	});

}




function addPayment() {

	var obj = {};


	var selectedOption = $("#vendorGrnId option:selected");
	var grnId = selectedOption.val();
	obj.paymentId = $('#paymentId').html();
	obj.vendorId = $('#vendorId').val();
	obj.vendorGrnId = grnId;
	obj.vendorGrnAmount = $('#vendorGrnAmount').val();
	//obj.vendorGrnPendingAmount = $('#vendorGrnPendingAmount').val();
	obj.vendorGrnBulkAmount = $('#vendorGrnBulkAmount').val();
	obj.vendorBankName = $('#vendorBankName').val();
	obj.vendorBankBranch = $('#vendorBankBranch').val();
	obj.vendorAccountNumber = $('#vendorAccountNumber').val();
	obj.vendorTransactionNumber = $('#vendorTransactionNumber').val();
	obj.vendorRemarks = $('#vendorRemarks').val();
	if (obj.vendorGrnId == "") {
		obj.vendorPayToggle = 'multi';
	} else {
		obj.vendorPayToggle = 'single';
	}

	console.log("object on add-----------" + JSON.stringify(obj));
	//return false;

	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.vendorId == null || obj.vendorId == "") {
		validation = validationUpdated("Vendor Name Required",
			"vendorName");
		return validation;
	}

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-send-amount-saveData",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					console.log(response);
					$("#messageParagraph").text("Sent Successfully").css("color", "red");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					cancelBtn();


				}
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}

function deleteCustomer() {
	//console.log("delete ids--------------"+deleteId);

	$.ajax({
		type: "GET",
		url: "view-send-amount-delete?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				//swal("Club member deleted successfully!", " ", "success");
				$("#messageParagraph").text("Deleted Successfully").css("color", "red");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				cancelBtn();


			}
		}

	});

	$('#delete').attr("disabled", true);
}

function getVendorList() {
	var search = $("#vendorName").val();
	if (search) {

		$.ajax({
			type: "POST",
			url: "quotation-details-get-vendor-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					$('.loader').hide();
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:-30px; font-weight:100; font-size:12px;"   class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].vendorId
								+ '\',\''
								+ response.body[i].vendorName
								+ '\',\''
								+ response.body[i].custGSTNo
								+ '\',\''
								+ response.body[i].taxType
								+ '\')">'
								+ response.body[i].vendorName
								+ '</li>';
						}

						content += '</ul>';
						//console.log("content " + content)
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;" onClick="selectAutocompleteValue()">'
							+ "No Data Found" + '</li>';

						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
						$('.loader').hide();
					}
				} else {
					$("#search").css("background", "#FFF");
					var content = '<ul id="autocomplete-list1">';
					content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;" onClick="selectAutocompleteValue()">'
						+ "No Data Found" + '</li>';

					content += '</ul>';
					$("#suggesstion-box1_").show();
					$("#suggesstion-box1_").html(content);
					$('.loader').hide();
				}
			},
			error: function(data) {
				console.log(data);
				$('.loader').hide();
			}
		})
	} else {
		$("#suggesstion-box1_").hide();
		$('.loader').hide();
	}

}

function selectAutocompleteValue1(vendorId, vendorName, custGSTNo, taxType) {

	if (vendorId) {

		$("#vendorId").val(vendorId);

		$("#vendorName").val(vendorName);
		$("#custGSTNo").val(custGSTNo);
		$("#taxType").val(taxType);
		$("#search").val(vendorName);
		$("#search").attr('data-procat', vendorId);
		$("#suggesstion-box1_").hide();
		$("#vendorGrnBulkAmount").attr("readonly", true);
		$("#vendorGrnBAmount").attr("readonly", true);

		$("#vendorGrnAmount").val("");
		$("#vendorGrnPendingAmount").val("");
		$("#vendorGrnBulkAmount").val("");
		$("#vendorBankName").val("");
		$("#vendorBankBranch").val("");
		$("#vendorAccountNumber").val("");
		$("#vendorTransactionNumber").val("");
		$("#vendorRemarks").val("");

		getGrnList(vendorId);

	} else {
		$("#vendorId").val("");
		$("#vendorName").val("");
		$("#custGSTNo").val("");
		$("#taxType").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();
	}
}
function selectAutocompleteValue() {
	$("#vendorId").val("");
	$("#vendorName").val("");
	$("#custGSTNo").val("");
	$("#taxType").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box1_").hide();
}

function getGrnList(vendorId) {
	if (vendorId) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-send-amount-getGrnList?id=" + vendorId,
			success: function(response) {
				if (response.message == "success") {
					console.log("grnList====" + JSON.stringify(response.body))

					$("#vendorGrnId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#vendorGrnId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].key + " (" + response.body[i].name + ")");
						$(option).attr('data-amount', response.body[i].name);
						$("#vendorGrnId").append(option);
						//alert("hello");
					}

				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#vendorGrnId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("vendorGrnId");
		$("#vendorGrnId").append(option);
		$("#vendorGrnId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#salesOrderId").append(option);
	}


}

function getGrnAmount() {

	var selectedOption = $("#vendorGrnId option:selected");
	var amount = selectedOption.attr('data-amount');
	if (amount !== undefined) {
		$("#vendorGrnAmount").val(amount)
		$("#vendorGrnPendingAmount").val('')
		$("#vendorGrnBulkAmount").attr("readonly", true);
	} else {
		var totalAmount = 0;
		$("#vendorGrnId option").each(function() {
			var dataAmount = $(this).data("amount");
			if (dataAmount) {
				totalAmount += parseFloat(dataAmount);
			}
		});
		console.log("Total Amount: " + totalAmount);
		$("#vendorGrnPendingAmount").val(totalAmount)
		$("#vendorGrnAmount").val("")
		$("#vendorGrnBulkAmount").removeAttr("readonly");
	}
}

function approvePayment() {
	$('#approveModal').modal('show');
}

function approveResultbtn() {
	$('#approveModal').modal('hide');
}

function approveResult() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var paymentId = selectedRows[0].paymentId;
	var userId = $("#userId").val();

	$.ajax({
		type: "GET",
		url: "view-send-amount-approval?paymentId=" + paymentId + "&userId=" + userId,
		async: false,
		success: function(response) {

			if (response.code == "success") {
				//cancel();
				$("#messageParagraph").text("Approved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#approveModal').modal('hide');
				cancelBtn();

			}

		},
	});

}

function cancelPaymentBtn() {
	$("#invPayment").modal("hide");
}

function getPaymentDetails(id) {
	/*	var editId = id.split(",");
		var modal = editId[1];
	*/
	var paymentId = id;
	$("#salesInvoiceTableList").empty();
	$("#debitNoteTableList").empty();
	$("#methodOfAdjTableList").empty();
	$("#invPayment").modal("show");

	$.ajax({
		type: "GET",
		url: "view-send-amount-getPaymentDetails?paymentId=" + paymentId,
		async: false,
		success: function(response) {
			var jsonObject = JSON.parse(response.body);
			console.log("response------" + JSON.stringify(jsonObject));
			console.log("viewDebitNoteListUsed===" + JSON.stringify(jsonObject.viewDebitNoteListUsed))
			console.log("viewDebitNoteGenerated===" + JSON.stringify(jsonObject.viewDebitNoteGenerated))
			console.log("viewInvoicePaymentData===" + JSON.stringify(jsonObject.viewInvoicePaymentData))
			console.log("viewMethodsOfAdjData===" + JSON.stringify(jsonObject.viewMethodsOfAdjData))

			if (jsonObject.viewInvoicePaymentData != null && jsonObject.viewInvoicePaymentData != "null" && jsonObject.viewInvoicePaymentData != "") {
				var emptyBodyViewMethodsOfAdjData = '<tr align="left" valign="top">'
					+ '<td colspan="3" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				$("#methodOfAdjTableList").append(emptyBodyViewMethodsOfAdjData);
				//for invoice lists
				var invoiceLen = jsonObject.viewInvoicePaymentData.length;
				for (var i = 0; i < invoiceLen; i++) {
					var getInvoice = '<tr>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].invoiceId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].poId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].vendorName + '</td>'
						+ '<td style="text-align:right">' + parseFloat(jsonObject.viewInvoicePaymentData[i].dueAmount).toFixed(2) + '</td>'
						+ '<td style="text-align:right">' + parseFloat(jsonObject.viewInvoicePaymentData[i].paidAmount).toFixed(2) + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].payStatus + '</td>'
						+ '</tr>';
					$("#salesInvoiceTableList").append(getInvoice);

				}
				$("#bulkCustAmount").val(jsonObject.viewInvoicePaymentData[0].finalPaidAmount);
			}
			else {
				var emptyBodyViewDebitNoteListUsed = '<tr align="left" valign="top">'
					+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';

				$("#salesInvoiceTableList").append(emptyBodyViewDebitNoteListUsed);
				$("#bulkCustAmount").val('');
			}

			if (jsonObject.viewDebitNoteListUsed != null && jsonObject.viewDebitNoteListUsed != "null" && jsonObject.viewDebitNoteListUsed != "") {

				var debitNoteLen = jsonObject.viewDebitNoteListUsed.length;
				for (var i = 0; i < debitNoteLen; i++) {
					var getInvoice = '<tr>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].voucherId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].orderId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].paymentId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].methodOfAdj + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].createdOn + '</td>'
						+ '<td style="text-align:right">' + parseFloat(jsonObject.viewDebitNoteListUsed[i].totalAmount).toFixed(2) + '</td>'
						+ '</tr>';
					$("#debitNoteTableList").append(getInvoice);

				}

			}
			else {
				$("#debitNoteTableList").empty();
				var emptyBodyViewDebitNoteListUsed = '<tr align="left" valign="top">'
					+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				//$("#debitNoteTableList").append(emptyBodyViewDebitNoteListUsed);

			}

			if (jsonObject.viewDebitNoteGenerated != null && jsonObject.viewDebitNoteGenerated != "null" && jsonObject.viewDebitNoteGenerated != "") {

				var debitNoteLen = jsonObject.viewDebitNoteGenerated.length;
				for (var i = 0; i < debitNoteLen; i++) {
					var getInvoice = '<tr>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].voucherId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].orderId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].paymentId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].methodOfAdj + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewDebitNoteListUsed[i].createdOn + '</td>'
						+ '<td style="text-align:right">' + parseFloat(jsonObject.viewDebitNoteListUsed[i].totalAmount).toFixed(2) + '</td>'
						+ '</tr>';
					$("#debitNoteTableList").append(getInvoice);

				}

			}
			else {
				var emptyBodyViewDebitNoteListUsed = '<tr align="left" valign="top">'
					+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				$("#debitNoteTableList").append(emptyBodyViewDebitNoteListUsed);

			}
		}
	})
}

function downloadExcelFromGrid() {
	var selectedHeaders = ['Payment Id', 'GRN Id', 'Vendor Name', 'Amount Sent', 'Payment Status',
		'Approval Status', 'Approved By', 'Sent By', 'Payment Date & Time'];

	var fileName = 'SentAmount_Register_' + '.xlsx';
	var totalSentAmount = 0;
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
			if (field === 'vendorGrnAmount') {
				value = parseFloat(value);
				value = isNaN(value) ? '0.00' : value.toFixed(2);
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});
		rowData.push(data);
		var sentAmount = parseFloat(node.data.vendorGrnAmount) || 0; parseFloat(node.data[fieldMap['Amount Sent']]) || 0;
		totalSentAmount += sentAmount;
	});

	totalSentAmount = amountFormatter(totalSentAmount.toFixed(2));

	var totalRow = {
		'Payment Id': "Total",
		'GRN Id': "",
		'Vendor Name': "",
		'Amount Sent': totalSentAmount,
		'Payment Status': "",
		'Approval Status': "",
		'Approved By': "",
		'Sent By': "",
		'Payment Date & Time': "",
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, 'Sent Amount Register');
	XLSX.writeFile(wb, fileName);
}
