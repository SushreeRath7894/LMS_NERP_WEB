$(document).ready(function() {
	$(".max-btn").on("click", function() {
		var parentCol = $(this).closest(".col-md-6, .col-md-12");

		if (parentCol.hasClass("col-md-6")) {
			parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
			parentCol.siblings(".col-md-6").hide().fadeOut(500);
		} else {
			parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
			parentCol.siblings(".col-md-6").show().fadeIn(500);
		}
	});


	//Automatically Select And Call The Starting Data
	/*setTimeout(function() {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		console.log("First Row Node:", firstRowNode);
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Select the first row
			var selectedRows = gridOptions.api.getSelectedRows();
			if (selectedRows.length > 0) {
				var id = selectedRows[0].reqId;
				var status = selectedRows[0].aprroveStatus;
				editRequisition(id, status);
				editTicket(id);
			}
		}
	}, 100);
*/

	//$('#dltProductDetails').attr('disabled', true);
	$("#dltProductDetails").hide();
	$('#addProduct').attr('disabled', false);
	$('#createRfq').attr('disabled', true);
	//$("#approve-btn").attr("disabled", true);
	$("#desc").prop("readonly", true);
	//$("#delete-btn").attr("disabled", true);
	$("#delete-btn").hide();
	//$("#receiveDateCalendar1").css("pointer-events", "none");
	//$("#receiveDateCalendar1").css("opacity", "0.5");
	$("#assignIssue").attr("disabled", true);

});

function selectFirstRow() {

	//Automatically Select And Call The Starting Data
	setTimeout(function() {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		console.log("First Row Node:", firstRowNode);
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Select the first row
			var selectedRows = gridOptions.api.getSelectedRows();
			if (selectedRows.length > 0) {
				var id = selectedRows[0].reqId;
				var status = selectedRows[0].aprroveStatus;
				console.log("Scheduled ID of first row:", id);
				editRequisition(id, status);
				/*$("#receiveDateCalendar1").css("pointer-events", "none");
				$("#receiveDateCalendar1").css("opacity", "0.5");*/
			}
		}
	}, 100);
}


function view() {
	$('.loader').show();
	agGrid.simpleHttpRequest({
		url: 'rmpm-requisition-view'
	}).then(function(data) {
		$('.loader').hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.view;
		window.gridOptions.api.setRowData(allData);

					if (allData && allData.length > 0) {
						window.gridOptions.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true);
							}
						});
					}
		//gridOptions.api.setRowData(allData);
		var totalRowCount = gridOptions.api.getModel().getRowCount();
		$('#totalReq').find('span').html(totalRowCount);
	});

	var dateFormat = localStorage.getItem("dateFormat");
	$("#receiveDateCalendar1").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,

	}).on("change", function() {
		$('#receiveDate').val($(this).val());
	})
	$('#receiveDate').blur(function() {
		$("#receiveDateCalendar1").val($(this).val());
	})
}


function onQuickFilterChanged() {
	$(".ti-search srchicon").hide();
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);

	var totalRowCount2 = gridOptions.api.getModel().getRowCount();
	$('#totalReq').find('span').html(totalRowCount2);
}


var columnDefs = [
	{
		//	headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 20
	},
	{
		headerName: "Requisition Id",
		field: "reqId",
		/*cellRenderer: function(params) {
			return '<a onclick=editRequisition("' + params.data.reqId + '","' + params.data.aprroveStatus
				+ '") href="javascript:void(0)">'
				+ params.data.reqId + '</a>';
		}*/

	}, {
		headerName: "Plan Id",
		field: "planId",
		hide: true,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Receive By Date",
		field: "receiveDate",
		cellStyle: {
			textAlign: 'center'
		}
	},

	{
		headerName: "Purpose",
		field: "desc",
		width: 250,
	}, {
		headerName: "Status",
		field: "aprroveStatus",
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.aprroveStatus == "Pending") {
				return '<div style="color:#a9a9a9">Pending</div>';
			} else {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	},
	{
		headerName: "Created By",
		field: "createdBy",
		width: 250,
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Created Date",
		field: "createdDate",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		}

	}];

// for product table

var productDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 20
	},
	{
		headerName: "SlNo",
		field: "slNo",
		width: 70,
		cellRenderer: function(params) {
			// Dynamically generate the serial number based on the row index
			const slNo = params.node.rowIndex + 1; // Adding 1 for 1-based index

			return '<a onclick="editProduct(' + slNo + ')" href="javascript:void(0)">' + slNo + '</a>';
		}
	},
	{
		headerName: "MATERIAL CODE",
		field: "sku",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "HSN CODE",
		field: "hsnCode",
		cellStyle: {
			textAlign: 'center'
		}
	},
	{
		headerName: "MATERIAL NAME",
		field: "itemName",
		width: 275,

	}, {
		headerName: "itemId",
		field: "itemId",
		hide: "true"
	}, {
		headerName: "Model/Size",
		field: "model",
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Quantity",
		field: "quantity",
		cellStyle: {
			textAlign: 'center'
		}

	}, {

		headerName: 'Unit',
		field: "unitName",
		width: 175,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Unit',
		field: "unit",
		hide: true,

	},];


// let the grid know which columns and what data to use activity table
var productOptions = {
	columnDefs: productDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},
	onSelectionChanged: onSelectionChangedItem,
	getRowNodeId: function(data) {
		return data.slNo;
	}

};

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

};

function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	}); 
	
	if (rowCount == 0) {
		newRmPmDetails();
	} else {
		disableField();
	}
	
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var approveStatus = selectedData.map(node => node.aprroveStatus);
	clearItemFields();
	if (rowCount > 0) {
		/* $('#reqDltBtn').attr('disabled', false);
		$('#reqAppvBtn').attr('disabled', false);
		$('#newBtn').attr('disabled', true); */
		if (approveStatus == "Pending") {
			$('#reqDltBtn').attr('disabled', false);
			$('#reqAppvBtn').attr('disabled', false);
			$('#newBtn').attr('disabled', true);
			//$("#approve-btn").attr("disabled", false);
			
			$("#approve-btn").show();
		} else {
			$('#reqDltBtn').attr('disabled', true);
			$('#reqAppvBtn').attr('disabled', true);
			$('#newBtn').attr('disabled', true);
			//$("#approve-btn").attr("disabled", true);
			$("#approve-btn").hide();
		}
		$(".requisition_ids_text").show();
		//$("#delete-btn").attr("disabled", false);
		$("#delete-btn").show();
		var id = selectedRows[0].reqId;
		var status = selectedRows[0].aprroveStatus;

		console.log("is-->", id);
		console.log("status-->", status);

		$(".formValidation").hide();
		editRequisition(id, status);
		$("#ticket-editBtn").show();
	} else {
		$('#newBtn').attr('disabled', false);
		$('#reqDltBtn').attr('disabled', true);
		$('#reqAppvBtn').attr('disabled', true);
		//$("#delete-btn").attr("disabled", true);
		$("#delete-btn").hide();
		$("#approve-btn").hide();
		$("#ticket-editBtn").hide();
		

		noSelection();
	}

}

function onSelectionChangedItem() {
	var selectedRows = productOptions.api.getSelectedRows();
	var requisitionSelect = gridOptions.api.getSelectedRows();
	var status = requisitionSelect[0].aprroveStatus;
	console.log("Ststauts->", status);
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var selectedNodes = productOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	// var approveStatus= selectedData.map(node => node.approveStatus);
	if (rowCount > 0) {
		$("#issueMaterial").attr('disabled', false);
		$('#rfq').attr('disabled', false);
		$('#addProduct').attr('disabled', true);
		//$('#dltProductDetails').attr('disabled', false);
		$("#dltProductDetails").show();
		if (status == "Approved") {
			$("#assignIssue").attr("disabled", false);
		}
		else {
			$("#assignIssue").attr("disabled", true);
		}
	} else {
		$("#issueMaterial").attr('disabled', true);
		$('#rfq').attr('disabled', true);
		$('#addProduct').attr('disabled', false);
		//$('#dltProductDetails').attr('disabled', true);
		$("#dltProductDetails").hide();
		$("#assignIssue").attr("disabled", true);
	}

}




// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	view();
	var gridDiv = document.querySelector('#product');
	new agGrid.Grid(gridDiv, productOptions);


});




function nextBtnFunction() {

	var validation = true;

	var receiveDate = $("#receiveDate").val();

	if (receiveDate == null || receiveDate == "") {
		validation = validationUpdated("Excepted Receive Date Required", 'receiveDate');
	}
	if (validation) {

		var tabTrigger = new bootstrap.Tab(document.querySelector('a.nav-link[href="#itemDetails"]'));
		tabTrigger.show();
	}
}


function previousButton() {

	var tabTrigger = new bootstrap.Tab(document.querySelector('a.nav-link[href="#raiseTicket"]'));
	tabTrigger.show();
}

function saveTableData(event) {
	event.preventDefault();
	var item = {};
	var data = 1;
	var validation = true;
	var editProduct = $("#editProduct").val();
	if (item.itemName == null || item.itemName == "") {
		validation = validationUpdated("Item Name Required", 'itemName');
	}
	if (item.quantity == null || item.quantity == "") {
		validation = validationUpdated("Quantity Required", 'quantity');
	}
	if (validation) {
		item.slNo = data;
		productOptions.api.forEachNode(function(rowNode, index) {
			if (!editProduct) {
				data = data + 1;
				item.slNo = data;
			}
			else {
				item.slNo = editProduct;
			}

		});
		item.itemId = $('#itemIdTemp').val();
		item.sku = $('#skuTemp').val();
		item.itemName = $('#itemName').val();
		item.hsnCode = $('#hsnCode').val();
		item.model = $("#model").val();
		item.quantity = $("#quantity").val();
		item.unit = $('#unit').val();
		item.unitName = $("#unit option:selected").text();
		var datas = [];

		if (editProduct) {
			//console.log(editProduct)
			productOptions.api.forEachNode((node) => {
				var currentSlNo = node.rowIndex + 1;
				if (currentSlNo == editProduct) {
					node.setData(item);
				}
			});
		} else {
			productOptions.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
			});
			datas.push(item)

			console.log("datas-->", datas);

			productOptions.api.setRowData(datas);
		}
		$("#sku").val('');
		$("#skuEditId").html('');
		$("#itemId").val('');
		$("#itemName").val('');
		$("#model").val('');
		$("#quantity").val('');
		$("#unit").val('');
		$("#search").val('');
		$("#hsnCode").val('');
		$("#editProduct").val(null);
	}

}

function validFormData() {
	var allValid = true;

	if (!validationUpdated("Department Required", 'deptId'))
		allValid = false;
	if (!validationUpdated("Expected Receive Date Required",
		'receiveDate'))
		allValid = false;
	return allValid;
}

function validProductData() {
	var item = null;
	productOptions.api.forEachNode(function(rowNode, index) {
		item = item + rowNode.data;
	});
	if (item) {
		return true;
	} else {
		$("#messageParagraph").text("Please Add Item Details");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}
}


function getItemQutotationList() {
	var search = $("#itemName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "rmpm-requisition-item-get-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					if (response.body.length != 0) {
						$("#itemId").val("");
						$("#gstRate").val("");
						$("#sku").val("");
						$("#brandName").val("");
						$("#brandId").val("");
						$("#hsnCode").val("");
						$("#unit").val("");
						$("#model").val("");
						$("#itemName").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem1(\''
								+ response.body[i].sku
								+ '\',\''
								+ response.body[i].productId
								+ '\',\''
								+ window.btoa(response.body[i].productName)
								+ '\',\''
								+ response.body[i].brandId
								+ '\',\''
								+ window.btoa(response.body[i].brandName)
								+ '\',\''
								+ window.btoa(response.body[i].hsnCode)
								+ '\',\''
								+ window.btoa(response.body[i].unit)
								+ '\',\''
								+ window.btoa(response.body[i].gstRate)
								+ '\',\''
								+ window.btoa(response.body[i].model)
								+ '\')">'
								+ response.body[i].sku
								+ " - "
								+ response.body[i].productName
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box_").show();
						$("#suggesstion-box_").html(content);

					} else {
						$("#itemName").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box_").show();
						$("#suggesstion-box_").html(content);
					}
				}
			},
			error: function(data) {
				//console.log(data);
			}
		})
	} else {
		$("#itemId").val("");
		$("#itemName").val("");
		$("#gstRate").val("");
		$("#sku").val("");
		$("#brandName").val("");
		$("#brandId").val("");
		$("#hsnCode").val("");
		$("#unit").val("");
		$("#model").val("");
		$("#suggesstion-box_").hide();
	}
}
function selectAutocompleteValueItem1(sku, productId, productName, brandId, brandName,
	hsnCode, unit, gstRate, model) {
	if (sku) {
		//$("#sku").val(sku);
		$("#itemName").val(window.atob(productName));
		$("#itemIdTemp").val(productId);
		$("#brandName").val(window.atob(brandName));
		$("#brandId").val(brandId);
		$("#skuTemp").val(sku);
		$("#sku").val(sku);
		$("#hsnCode").val(window.atob(hsnCode));
		$("#unit").val(window.atob(unit));
		$("#gstRate").val(window.atob(gstRate));
		$("#model").val(window.atob(model));
		$("#itemName").attr('data-procat', itemId);
		$("#suggesstion-box_").hide();
		//hideShowS();
	} else {

		$("#sku").val("");
		$("#itemId").val("");
		$("#itemName").val("");
		$("#gstRate").val("");
		$("#brandName").val("");
		$("#brandId").val("");
		$("#hsnCode").val("");
		$("#unit").val("");
		$("#model").val("");
		$("#itemName").attr('data-procat', "");
		$("#suggesstion-box_").hide();

	}
}
function selectAutocompleteValueItem() {

	$("#itemId").val("");
	$("#model").val("");
	$("#gstRate").val("");
	$("#sku").val("");
	$("#itemName").val("");
	$("#itemName").attr('data-procat', "");
	$("#suggesstion-box_").hide();

}

function deleteProduct(event) {
	var selectedRows = productOptions.api.getSelectedRows();
	productOptions.api.applyTransaction({
		remove: selectedRows
	});

}



function saveData() {
	//console.log("Okkk");
	var item = {};
	var validation = true;

	if (item.receiveDate == null || item.receiveDate == "") {
		validation = validationUpdated("Date Required", 'receiveDate');
	}

	var totalRowCount1 = productOptions.api.getModel().getRowCount();

	if (totalRowCount1 > 0) {
		item.planId = $("#planId").val();
		item.desc = $("#desc").val();
		item.receiveDate = $("#receiveDate").val();
		item.reqId = $("#reqHeadId").html();

		var datas = [];
		productOptions.api.forEachNode(function(rowNode, index) {
			var item1 = rowNode.data;
			//console.log(item1)
			datas.push(item1);

		});
		item.details = datas;
		//console.log("item",item)
		if (validation) {
			saveAllRequisition(item);
		}
	} else {
		$("#messageParagraph").text("Add Atleast One Item");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}
}
function saveAllRequisition(datas) {
	$('.loader').show();
	$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "rmpm-requisition-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.code == "success") {
				view();
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Data Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				//selectFirstRow();
				//$(".btn-hs").show();
				$("#desc").prop("readonly", true);
				previousButton();
			}

		},
		error: function(datas) {
			//console.log(datas)
		}
	})

}

function editRequisition(id, approveStatus) {
	var reqId = id;
	//$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#reqTable").hide();
	$(".btn-hs").hide();
	$("#demo").show();
	$("#rfq").attr('disabled', true);

	$("#planId").attr('disabled', true);
	//$("#receiveDateCalendar1").attr('disabled', true);
	//$("#receiveDate").attr('disabled', true);



	if (approveStatus == 'Pending') {
		$("#save1").show();
		$("#addProduct").show();
		$("#dltProduct").show();
		$("#dltProduct").attr('disabled', true);
		$("#saveTableData").show();
		$("#issueMaterial").hide();
		$("#issueMaterial").attr('disabled', true);
	}
	else {
		$("#dltProduct").hide();
		$("#addProduct").hide();
		$("#save1").hide();
		$("#saveTableData").hide();
		$("#issueMaterial").show();
		$("#issueMaterial").attr('disabled', true);
	}
	$('.loader').show();
	$("body").addClass("overlay");
	agGrid.simpleHttpRequest({
		url: 'rmpm-requisition-edit?id=' + reqId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.edit;
		$("#planId").val(allData[0].planId);
		$("#desc").val(allData[0].desc);
		$("#receiveDate").val(allData[0].receiveDate);
		$("#reqId").val(allData[0].reqId);
		$("#reqHeadId").html(allData[0].reqId);

		productOptions.api.setRowData(allData);
		$("#doctbodyData").empty();

		var fromDate = allData[0].fromDate;
		var toDate = allData[0].toDate;
		var receiveDate = allData[0].receiveDate;
		// Initialize datetimepicker with date range restriction
		/* var dateFormat = localStorage.getItem("dateFormat");
			$("#receiveDateCalendar1").datetimepicker({
				format: 'd-m-Y',
				closeOnDateSelect: true,
				timepicker: false,
				datepicker: true,
				scrollMonth: false,
				//minDate: convertToDate(fromDate), // Minimum selectable date
				//maxDate: convertToDate(toDate),   // Maximum selectable date
			}).on("change", function() {
				$('#receiveDate').val($(this).val());
			})
			$('#receiveDate').blur(function() {
				$("#receiveDateCalendar1").val($(this).val());
			});
			$("#receiveDateCalendar1").datetimepicker({ value: allData[0].receiveDate }) */

		$('.loader').hide();
		$("body").removeClass("overlay");
	});


}

function newRmPmDetails() {
	enableField();
	gridOptions.api.deselectAll();
	$("#receiveDate").val('');
	$("#desc").val('');
	productOptions.api.setRowData([]);
	$("#reqHeadId").html('');
	$(".requisition_ids_text").hide();
	$("#desc").prop("readonly", false);

	/*$("#receiveDateCalendar1").css("pointer-events", "all");
	$("#receiveDateCalendar1").css("opacity", "1");*/
	$("#ticket-editBtn").hide();

}

function approveRequisition() {
	$('#approvePQtn').modal('show');
}

function cancelApproveModalBtn() {
	$('#approvePQtn').modal('hide');
}

function approveOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.reqId;
	});
	var reqId = selectedRowsString;

	var approveStatus = 1;
	//$('#approvePQtn').modal('show');
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "rmpm-requisition-approve?id=" + reqId,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("#messageParagraph").text("Requisition Approved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				$('#newBtn').attr("disabled", false);
				$('#reqDltBtn').attr("disabled", true);
				$('#reqAppvBtn').attr("disabled", true);

				$('#approvePQtn').modal('hide');
				$("#total").show();
				$("#totalCandidate").show();
				$("#btndiv").show();

				//$("#reqTable").show();
				$('#dwnld').show();
				$("#addData").hide();
				$("#myGrid").show();
				$("#searchRowDiv").show();
				$("#reqTable").show();
				view();
				//selectFirstRow();
				$("#desc").prop("readonly", true);

			} else {
				$('.loader').hide();
				$('#approvePQtn').modal('show');
			}

		},
		error: function(data) {
		}
	});
}


function editItems() {
	$("#desc").prop("readonly", false);
	//$("#receiveDateCalendar1").css("pointer-events", "all");
	//$("#receiveDateCalendar1").css("opacity", "1");

}


function deleteFun() {
	$('#deleteAttachment').modal('show');
}

function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}
function deleteOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].reqId;


	$.ajax({
		type: "GET",
		url: "rmpm-requisition-delete?id=" + id,
		success: function(response) {
			if (response.code == "success") {

				$('#delete').modal('hide');

				$('#reqDltBtn').attr("disabled", true);
				$('#newBtn').attr("disabled", false);
				$('#reqAppvBtn').attr("disabled", true);
				view();
				//selectFirstRow();
				$("#desc").prop("readonly", true);

			} else {

			}
		},
		error: function(data) {
			//console.log(data)
		}
	})
}
// delete selected record from ag grid
function deleteProductOnclick() {
	var selectedRows = productOptions.api.getSelectedRows();
	productOptions.api.applyTransaction({
		remove: selectedRows
	});

}


function getRowDataBySlNo(targetSlNo) {
	let targetRowData = null;

	productOptions.api.forEachNode((node) => {
		const currentSlNo = node.rowIndex + 1; // Dynamically calculate slNo based on row index

		if (currentSlNo === targetSlNo) {
			// If the dynamically generated slNo matches the target, get the row data
			targetRowData = node.data;
		}
	});

	if (targetRowData) {
		//console.log("Data for SlNo", targetSlNo, ":", targetRowData);
		return targetRowData;
	} else {
		//console.log("No data found for SlNo", targetSlNo);
		return null;
	}
}

function editProduct(slNo) {

	var reqId = $("#reqHeadId").html();
	var data = getRowDataBySlNo(slNo);
	//console.log("rowNode.data==",data)
	$("#editProduct").val(slNo);
	$("#sku").val(data.sku);
	//$("#skuEditId").val(prodId);
	$("#itemId").val(data.itemId);
	$("#hsnCode").val(data.hsnCode);
	$("#itemName").val(data.itemName);
	$("#model").val(data.model);
	$("#editProduct").val(slNo);
	$("#quantity").val(data.quantity);
	$("#unit").val(data.unit);
	$("#unitPrice").val(data.unitPrice);
	$("#skuTemp").val(data.sku);
	$("#lineTotal").val(data.lineTotal);
	$("#itemIdTemp").val(data.itemId);
	//$("#unitName").val(rowNode.data.unitName);


}


function noSelection() {
	$("#receiveDate").val('');
	$("#desc").val('');
	productOptions.api.setRowData([]);
	$("#reqHeadId").html('');
	$(".requisition_ids_text").hide();
}

function materialIssue() {

	var selectedRows = productOptions.api.getSelectedNodes();

	console.log("selected rowss-MI-->", selectedRows[0].data);
	var selectedRowsString = '';
	var selectedRowsData = selectedRows[0].data;
	selectedRowsData.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += '"' + selectedRow.sku + '"';
		//selectedRowsString += selectedRow.reqId;
	});
	var skuId = selectedRowsString;


	var reqId = $("#reqHeadId").text();

	console.log("skuId>>>", skuId);
	console.log("reqId>>>", reqId);

	if (reqId) {
		sessionStorage.setItem('activity', 'ACT0491');

		agGrid.simpleHttpRequest({
			url: 'inventory-requisition-getDataForIssue?id=' + reqId + "&skuId=" + skuId
		}).then(function(data) {
			//console.log("data.body", data.body);
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.edit;
			console.log("allData<<<<>>>>>>", allData);
			localStorage.setItem('ReqMaterialData', data.body);
			window.location.href = "/purchase/rmpm-materialIssue"
		});

	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}
}



function clearItemFields(){
	
	$("#itemName").val('');
	$("#sku").val('');
	$("#hsnCode").val('');
	$("#model").val('');
	$("#quantity").val('');
	$("#unit").val('');
}

function disableField() {

	$("#receiveDateCalendar1").css("pointer-events", "none");
	$("#receiveDateCalendar1").css("opacity", "0.5");
	
	$("#itemName").attr("disabled", true);
	$("#unit").attr("disabled", true);
	$("#quantity").attr("disabled", true);

}

function enableField() {

	$("#receiveDateCalendar1").css("pointer-events", "all");
	$("#receiveDateCalendar1").css("opacity", "1");
	
	$("#itemName").attr("disabled", false);
	$("#unit").attr("disabled", false);
	$("#quantity").attr("disabled", false);
	

}

function addNewRmPmDetails(){
	gridOptions.api.deselectAll();
}

