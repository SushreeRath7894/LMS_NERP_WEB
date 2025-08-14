$(function() {

	$("#ledgerType").select2();

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	agGrid.simpleHttpRequest({
		url: "manage-ledger-view"
	}).then(function(data) {
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
	$("#myGrid").show();
	$("#delete").attr("disabled", true);


	$("#ledgerCountry").select2({
		placeholder: "Select Country",
		allowClear: true
	});

	$("#ledgerState").select2({
		placeholder: "Select State",
		allowClear: true
	});
});

$(document).ready(function() {
	function getStartOfFinancialYear() {
		var today = new Date();
		var financialYearStartMonth = 3;
		var currentYear = today.getFullYear();
		if (today.getMonth() < financialYearStartMonth) {
			currentYear--;
		}
		return new Date(currentYear, financialYearStartMonth, 1);
	}
	updateOpeningBalanceLabel(getStartOfFinancialYear());
	function updateOpeningBalanceLabel(date) {
		var financialYear = date.getFullYear();
		var label = "Opening Balance(01-04-" + financialYear + ")";
		$("#openingBalanceLabel").text(label);
	}
	$(document).on("change", "#openinbalanceDate", function() {
		var selectedDate = new Date($(this).val());
		//updateOpeningBalanceLabel(selectedDate);
	});

	$('#ledgerGst').on('keydown', function(event) {
		var gstValue = $(this).val();
		if (gstValue.length === 12 && event.keyCode === 8) {
			event.preventDefault();
		}
	});

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
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
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
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Ledger Id',
		field: "leadgerId",
		width: 100,
		/*cellRenderer: function(params) {
			return '<a onclick=editPage("' + params.data.leadgerId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.leadgerId + ' <i class="fa fa-edit"></i></a>';
		}*/
	},

	{
		headerName: "Ledger Name",
		field: "ledgername",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Group",
		field: "groupId",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Opening Balance",
		field: "openinbalanceDate",
		width: 150,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	}, {
		headerName: "E-Mail",
		field: "lname",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Address",
		field: "address",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Ledger Country",
		field: "leadgercountry",
		width: 140,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Ledger State",
		field: "leadgerstate",
		width: 140,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Pin Code",
		field: "pincode",
		width: 140,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Pan | It No",
		field: "panitn",
		width: 140,
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
		width: 200,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	pagination: true,
	paginationPageSize: 15,
	getRowNodeId: function(data) {
		return data.leadgerId;
	}
};

var deleteId = "";
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	deleteId = "";
	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '"' + selectedRows[i].leadgerId + '",';
		// deleteId = deleteId  + selectedRows[i].incentiveId + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		var ledgerid = selectedRows[0].leadgerId;
		var ledgerName = selectedRows[0].ledgername;
		$("#ledgerNameText").text(ledgerName);
		$('#delete').attr("disabled", false);
		$('#add').attr("disabled", true);
		$("#add-btn").removeClass("d-none");
		$("#ledger_edit_btn").removeClass("d-none");
		$("#ledger_delete_btn").removeClass("d-none");
		$("#ledger_save_btn").addClass("d-none");
		$("#ledger_cancel_btn").addClass("d-none");
		
		let ledgerType = selectedRows[0]?.ledgerType;
		
		if(ledgerType) {
			$('#ledgerType').val(ledgerType).select2();
		} else {
			$('#ledgerType').val("").select2();
		}
		
		editPage(ledgerid);
	} else {

		$('#leadgerId').val("");
		$('#ledgerType').val("").select2();
		$('#ledgername').val("");
		$('#groupName').val("");
		$('#undergroupId').val("");
		$('#ledgerEmail').val("");
		$('#ledgerAddress1').val("");
		$('#ledgerAddress2').val("");
		$('#ledgerAddress3').val("");
		$('#ledgerCountry').val("").trigger('change');
		$('#ledgerState').val("").trigger('change');
		$('#ledgerPinCode').val("");
		$('#ledgerPan').val("");
		$('#ledgerMobile').val("");
		$('#ledgerGst').val("");
		$('#openinbalanceDate').val("");

		$('#delete').attr("disabled", true);
		$('#add').attr("disabled", false);
		$("#ledgerNameText").text('');
		$('#ledgername,#ledgerType').attr("disabled", false);
		$('#groupName').attr("disabled", false);
		$('#undergroupId').attr("disabled", false);
		$('#ledgerEmail').attr("disabled", false);
		$('#ledgerAddress1').attr("disabled", false);
		$('#ledgerAddress2').attr("disabled", false);
		$('#ledgerAddress3').attr("disabled", false);
		$('#ledgerCountry').attr("disabled", false);
		$('#ledgerState').attr("disabled", false);
		$('#ledgerPinCode').attr("disabled", false);
		$('#ledgerPan').attr("disabled", false);
		$('#ledgerMobile').attr("disabled", false);
		$('#ledgerGst').attr("disabled", false);
		$('#openinbalanceDate').attr("disabled", false);

		$("#add-btn").addClass("d-none");
		$("#ledger_edit_btn").addClass("d-none");
		$("#ledger_delete_btn").addClass("d-none");
		$("#ledger_save_btn").removeClass("d-none");
		$("#ledger_cancel_btn").removeClass("d-none");
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
	$("#leadgerId").empty();

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

	$('#leadgerId').val("");
	$('#ledgername').val("");
	$('#groupName').val("");
	$('#undergroupId').val("");
	$('#ledgerEmail').val("");
	$('#ledgerAddress1').val("");
	$('#ledgerAddress2').val("");
	$('#ledgerAddress3').val("");
	$('#ledgerCountry').val("").trigger('change');
	$('#ledgerState').val("").trigger('change');
	$('#ledgerPinCode').val("");
	$('#ledgerPan').val("");
	$('#ledgerMobile').val("");
	$('#ledgerGst').val("");
	$('#openinbalanceDate').val("");

	/*	agGrid.simpleHttpRequest({
			url: "manage-ledger-view"
		}).then(function(data) {
			//gridOptions.api.setRowData(data);
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
		});*/
}

// Edit & stage change 
function editPage(id) {
	//alert(id);
	//var editId = id.split(",");

	//	var leadgerId = editId[0];

	//	var modal = editId[1];
	//alert('colorId------'+colorId);
	//$("#demo").show();
	//$("#add").hide();
	//$("#copy").hide();
	//$("#delete").hide();
	//$("#myGrid").hide();
	//$("#searchRowDiv").hide();
	//$("#totalReq").hide();
	//$("#statusDiv").hide();
	//$("#idDiv").show();
	//$("#collapseFour").hide();
	//$("#headingFour").hide();
	//$("#myGridActivity").hide();

	$.ajax({
		type: "GET",
		url: "manage-ledger-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {

				//$("#add").hide();
				//$("#copy").hide();
				//$("#delete").hide();
				//$("#myGrid").hide();
				//$("#searchRowDiv").hide();
				//$("#totalReq").hide();
				//$("#statusDiv").hide();
				//$("#idDiv").hide();
				//$("#collapseFour").hide();
				//$("#headingFour").hide();
				//$("#myGridActivity").hide();

				$("#demo").show();
				DisableFalse();

				//$(".container").hide();

				$("#leadgerId").text(response.body[0].leadgerId);
				$("#ledgername").val(response.body[0].ledgername);
				$("#groupName").val(response.body[0].groupName);
				$("#undergroupId").val(response.body[0].groupId);

				$("#ledgerEmail").val(response.body[0].ledgerEmail);
				$("#ledgerAddress1").val(response.body[0].ledgerAddress1);
				$("#ledgerAddress2").val(response.body[0].ledgerAddress2);
				$("#ledgerAddress3").val(response.body[0].ledgerAddress3);
				$("#ledgerCountry").val(response.body[0].ledgerCountry).trigger('change');
				getStateDataOnEdit(response.body[0].ledgerState)
				//$("#ledgerState").val(response.body[0].ledgerState);
				$("#ledgerPinCode").val(response.body[0].ledgerPinCode);
				$("#ledgerPan").val(response.body[0].ledgerPan);

				if (response.body[0].openinbalanceDate == 'null') {
					$("#openinbalanceDate").val('');
				}
				else {
					$("#openinbalanceDate").val(response.body[0].openinbalanceDate);
				}
				$("#ledgerMobile").val(response.body[0].ledgerMobile);
				$("#ledgerGst").val(response.body[0].ledgerGst);
			}

		}
	})
}


function addLedgerInfo() {
	$(".loader").show();
	var obj = {};

	/* 		``obj.leadgerId = $('#leadgerId').text();
			``obj.ledgername = $('#ledgername').val();
			``obj.groupId = $('#undergroupId').val(); */
	//obj.ledgerEmail = $('#ledgerEmail').val();
	//obj.ledgerAddress = $('#ledgerAddress').val();
	//obj.ledgerCountry = $('#ledgerCountry').val();
	//obj.ledgerState = $('#ledgerState').val();
	//obj.ledgerPinCode = $('#ledgerPinCode').val();
	//obj.ledgerPan = $('#ledgerPan').val();
	//obj.openinbalanceDate = $('#openinbalanceDate').val();

	obj.leadgerId = $('#leadgerId').text();
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
	obj.ledgerType = $('#ledgerType').val();
	obj.openinbalanceDate = $('#openinbalanceDate').val();
	let ledgerId = globalLedgerId;

	console.log("object on add-----------" + JSON.stringify(obj));

	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.ledgername == null || obj.ledgername == "") {
		$(".loader").hide();
		toastr.error("Ledger Name Required");
		return false;
	}
	if (obj.groupId == null || obj.groupId == "") {
		$(".loader").hide();
		toastr.error("Under Group Required");
		return false;
	}
	/*	if (obj.ledgerEmail == null || obj.ledgerEmail == "") {
			validation = validationUpdated(" Name Required", "ledgerEmail");
			return validation;
		}
		if (obj.address == null || obj.address == "") {
			validation = validationUpdated("Address Required", "address");
			return validation;
		}
		if (obj.ledgerCountry == null || obj.ledgerCountry == "") {
			validation = validationUpdated("Ledger Country Required",
					"ledgerCountry");
			return validation;
		}
		if (obj.ledgerState == null || obj.ledgerState == "") {
			validation = validationUpdated("Ledger State Required",
					"ledgerState");
			return validation;
		}
		if (obj.ledgerPinCode == null || obj.ledgerPinCode == "") {
			validation = validationUpdated("Pin Code Required", "ledgerPinCode");
			return validation;
		}
		if (obj.ledgerPan == null || obj.ledgerPan == "") {
			validation = validationUpdated("Pan | It No Required", "ledgerPan");
			return validation;
		}
		if (obj.openinbalanceDate == null || obj.openinbalanceDate == "") {
			validation = validationUpdated("Opening Balance Required",
					"openinbalanceDate");
			return validation;
		}
		*/

	//	alert('Hello submit');

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "manage-ledger-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					//console.log(response);
					$(".loader").hide();
					if (obj.leadgerId == "" || obj.leadgerId == null) {
						toastr.success("Ledger added successfully!", " ", "success");
					} else {
						toastr.success("Modified successfully!", " ", "success");
					}

					cancelBtn();
					agGrid.simpleHttpRequest({
						url: "manage-ledger-view"
					}).then(function(data) {
						var rowData = [];
						gridOptions.api.setRowData(rowData);
						gridOptions.api.setRowData(data);

						/*	if (ledgerId) {
								gridOptions.api.forEachNode(function(node) {
									if (node.data && node.data.leadgerId === ledgerId) {
										node.setSelected(true);
										return;
									}
								});
							} else {
								var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
								if (firstRowNode) {
									firstRowNode.setSelected(true);
								}
							}*/

						if (ledgerId) {
							let targetIndex = -1;

							gridOptions.api.forEachNode((node, index) => {
								if (node.data && node.data.leadgerId === ledgerId) {
									targetIndex = node.rowIndex;
								}
							});

							if (targetIndex !== -1) {
								const pageSize = gridOptions.api.paginationGetPageSize();
								const targetPage = Math.floor(targetIndex / pageSize);

								gridOptions.api.paginationGoToPage(targetPage);

									gridOptions.api.forEachNode((node) => {
										if (node.data && node.data.leadgerId === ledgerId) {
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
					});

				}
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}

function deleteLedger() {
	//console.log("delete ids--------------"+deleteId);
	var updatedId = deleteId;
	$.ajax({
		type: "GET",
		url: "manage-ledger-delete?id=" + updatedId,
		success: function(response) {
			if (response.message == "Success") {
				toastr.success("Leadger deleted successfully!");
				cancelBtn();
				agGrid.simpleHttpRequest({
					url: "manage-ledger-view"
				}).then(function(data) {
					//gridOptions.api.setRowData(data);
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
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
}

//Drop down
function getStateList() {
	var cname = $('#ledgerCountry').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "manage-ledger-stateList?id=" + cname,
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


function getStateDataOnEdit(stateId) {
	var country = $("#ledgerCountry").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "manage-ledger-stateList?id=" + country,
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
					$("#ledgerState").val(stateId).trigger('change');
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
	}
}


//search

function getGroupList() {
	$("#undergroupId").val("");
	var search = $("#groupName").val();
	if (search) {
		//alert('hello get cust2');
		$
			.ajax({
				type: "POST",
				url: "manage-ledger-group-list",
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
								content += '<li class ="autocompletedata cp" onClick="selectAutocompleteValue1(\''
									+ response.body[i].groupId
									+ '\',\''
									+ response.body[i].groupName
									+ '\')">'
									+ response.body[i].groupName
									+ '</li>';
							}
							/* content += '<li style="margin-left:-30px;" >'
									+ '</li>'; */
							content += '</ul>';
							////console.log("content " + content)
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li class ="autocompletedata cp" onClick="selectAutocompleteValue1()">'
								+ "No Data Found" + '</li>';
							/* content += '<li style="margin-left:-30px;" '
									+ '</li>'; */
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);
						}
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li onClick="selectAutocompleteValue1()">'
							+ "No Data Found" + '</li>';
						/* content += '<li style="margin-left:-30px;" '
								+ '</li>'; */
						content += '</ul>';
						$("#suggesstion-box11_").show();
						$("#suggesstion-box11_").html(content);
					}
				},
				error: function(data) {
					//console.log(data);
				}
			})
	} else {
		$("#suggesstion-box11_").hide();
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

function stateCode() {
	var selectedOption = $('#ledgerState option:selected');
	var code = selectedOption.attr('code');
	console.log("code-22->", code);
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
		console.log("code-->", code);
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


function addLedger() {
	editLedger();

	$('#leadgerId').text("");
	$('#ledgername').val("");
	$('#groupName').val("");
	$('#undergroupId').val("");
	$('#ledgerEmail').val("");
	$('#ledgerAddress1').val("");
	$('#ledgerAddress2').val("");
	$('#ledgerAddress3').val("");
	$('#ledgerCountry').val("");
	$('#ledgerState').val("");
	$('#ledgerPinCode').val("");
	$('#ledgerPan').val("");
	$('#ledgerMobile').val("");
	$('#ledgerGst').val("");
	$('#openinbalanceDate').val("");
	globalLedgerId = "";
	gridOptions.api.deselectAll();
}

function DisableFalse() {

	$('#ledgername,#ledgerType').attr("disabled", true);
	$('#groupName').attr("disabled", true);
	$('#undergroupId').attr("disabled", true);
	$('#ledgerEmail').attr("disabled", true);
	$('#ledgerAddress1').attr("disabled", true);
	$('#ledgerAddress2').attr("disabled", true);
	$('#ledgerAddress3').attr("disabled", true);
	$('#ledgerCountry').attr("disabled", true);
	$('#ledgerState').attr("disabled", true);
	$('#ledgerPinCode').attr("disabled", true);
	$('#ledgerPan').attr("disabled", true);
	$('#ledgerMobile').attr("disabled", true);
	$('#ledgerGst').attr("disabled", true);
	$('#openinbalanceDate').attr("disabled", true);
}

var globalLedgerId = "";
function editLedger() {

	$('#ledgername,#ledgerType').attr("disabled", false);
	$('#groupName').attr("disabled", false);
	$('#undergroupId').attr("disabled", false);
	$('#ledgerEmail').attr("disabled", false);
	$('#ledgerAddress1').attr("disabled", false);
	$('#ledgerAddress2').attr("disabled", false);
	$('#ledgerAddress3').attr("disabled", false);
	$('#ledgerCountry').attr("disabled", false);
	$('#ledgerState').attr("disabled", false);
	$('#ledgerPinCode').attr("disabled", false);
	$('#ledgerPan').attr("disabled", false);
	$('#ledgerMobile').attr("disabled", false);
	$('#ledgerGst').attr("disabled", false);
	$('#openinbalanceDate').attr("disabled", false);


	$("#ledger_save_btn").removeClass("d-none");
	$("#ledger_cancel_btn").removeClass("d-none");
	$("#add-btn").addClass("d-none");
	$("#ledger_edit_btn").addClass("d-none");
	$("#ledger_delete_btn").addClass("d-none");


	var selectedRows = gridOptions.api.getSelectedRows();
	var ledgerid = selectedRows[0].leadgerId;
	globalLedgerId = ledgerid;

}


function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChanged();
	}
}

/*function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}*/

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


function ledgerCancelBtnFunction() {

	let leadgerId = globalLedgerId;

	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		//firstRowNode.setSelected(true);
		$("#add-btn").removeClass("d-none");
		$("#ledger_edit_btn").removeClass("d-none");
		$("#ledger_delete_btn").removeClass("d-none");
		$("#ledger_save_btn").addClass("d-none");
		$("#ledger_cancel_btn").addClass("d-none");
		DisableFalse();

		if (leadgerId) {

			gridOptions.api.forEachNode(function(node) {
				if (node.data && node.data.leadgerId === leadgerId) {
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
		$("#ledger_edit_btn").addClass("d-none");
		$("#ledger_delete_btn").addClass("d-none");
		$("#ledger_save_btn").addClass("d-none");
		$("#ledger_cancel_btn").addClass("d-none");


	}

}