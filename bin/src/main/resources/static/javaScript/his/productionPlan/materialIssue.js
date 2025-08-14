$(document).ready(function() {
	// $(".max-btn").on("click", function() {
	// 	var parentCol = $(this).closest(".col-md-6, .col-md-12");

	// 	if (parentCol.hasClass("col-md-6")) {
	// 		parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
	// 		parentCol.siblings(".col-md-6").hide().fadeOut(500);
	// 	} else {
	// 		parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
	// 		parentCol.siblings(".col-md-6").show().fadeIn(500);
	// 	}
	// });

	view();

	/*setTimeout(function() {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		console.log("First Row Node:", firstRowNode);
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Select the first row
			var selectedRows = gridOptions.api.getSelectedRows();
			if (selectedRows.length > 0) {
				var id = selectedRows[0].slipNo;
				var status = selectedRows[0].aprroveStatus;
				editRequisition(id, status);
				editTicket(id);
			}
		}
	}, 500);*/

	var dateFormat = localStorage.getItem("dateFormat");
	$("#receiveDateCalendar1").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,

	}).on("change", function() {
		$('#materialIssueDate').val($(this).val());
	})
	$('#materialIssueDate').blur(function() {
		$("#receiveDateCalendar1").val($(this).val());
	})

	var dateFormat = localStorage.getItem("dateFormat");
	$("#receiveDateCalendar2").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,

	}).on("change", function() {
		$('#receiveDate').val($(this).val());
	})
	$('#receiveDate').blur(function() {
		$("#receiveDateCalendar2").val($(this).val());
	});

	disableAll();
	$("#dltProductDetails").hide();
	readOnlyField();

	$("#ticket-editBtn").hide();
	$("#approve-btn").hide();
	$("#delete-btn").hide();

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
				var id = selectedRows[0].slipNo;
				var status = selectedRows[0].aprroveStatus;
				editRequisition(id, status);
				/*editTicket(id);*/
			}
		}
	}, 100);
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
		headerName: "Issue Slip No",
		field: "slipNo",
		cellRenderer: function(params) {
			return '<a onclick=editRequisition("' + params.data.slipNo + '","' + params.data.aprroveStatus
				+ '") href="javascript:void(0)">'
				+ params.data.slipNo + '</a>';
		}

	}, {
		headerName: "Requested Receiveing Date",
		field: "receivingDate",
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Requested By",
		field: "requestedBy",
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
		headerName: "Issued By",
		field: "createdBy",
		width: 250,
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Issued On Date",
		field: "createdDate",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		}

	}];

// for product table

var productDefs = [
	{
		headerCheckboxSelection: true,
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
			if (params.data.slNo) {
				return '<a onclick=editProduct("' + params.data.slNo
					+ '") href="javascript:void(0)">'
					+ params.data.slNo + '</a>';
			} else {
				return '<a onclick=editProduct("' + params.data.slNo
					+ '") href="javascript:void(0)">'
					+ params.data.slNo + '</a>';
			}
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
		headerName: "Grn No",
		field: "grnNo",
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Quantity Requested",
		field: "quantity",
		cellStyle: {
			textAlign: 'center'
		}

	}, {
		headerName: "Quantity Issued",
		field: "quantityIssued",
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

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#product');
	new agGrid.Grid(gridDiv, productOptions);


});



function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var approveStatus = selectedData.map(node => node.aprroveStatus);
	if (rowCount > 0) {
		$("#ticket-editBtn").show();

		$("#delete-btn").show();
		if (approveStatus == "Pending") {

			$("#approve-btn").show();
		} else {

			$("#approve-btn").hide();
		}
		var id = selectedRows[0].slipNo;
		var status = selectedRows[0].aprroveStatus;
		editRequisition(id, status);
	} else {
		$("#ticket-editBtn").hide();
		$("#approve-btn").hide();
		$("#delete-btn").hide();
	}

}

function onSelectionChangedItem() {
	var selectedRows = productOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var selectedNodes = productOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	// var approveStatus= selectedData.map(node => node.approveStatus);
	if (rowCount > 0) {
		$('#rfq').attr('disabled', false);
		$('#addProduct').attr('disabled', true);
		$('#dltProduct').attr('disabled', false);
		$("#dltProductDetails").show();
	} else {
		$('#rfq').attr('disabled', true);
		$('#addProduct').attr('disabled', false);
		$('#dltProduct').attr('disabled', true);
		$("#dltProductDetails").hide();
	}

}


function view() {
	$('.loader').show();
	agGrid.simpleHttpRequest({
		url: 'rmpm-materialIssue-view'
	}).then(function(data) {
		$('.loader').hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.view;
		console.log("allData>>>", allData);

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
}


function editRequisition(id, aprroveStatus) {
	console.log("id>>>>>>>>>>---", id);
	//var data = id.split(",");
	var reqId = id;
	//var approveStatus = data[1];
	$("#rfq").attr('disabled', true);

	if (aprroveStatus == 'Pending') {
		$("#save1").show();
		$("#addProduct").show();
		$("#dltProduct").show();
		$("#dltProduct").attr('disabled', true);
	}
	else {
		$("#dltProduct").hide();
		$("#addProduct").hide();
		$("#save1").hide();
	}
	$('.loader').show();
	$("body").addClass("overlay");
	agGrid.simpleHttpRequest({
		url: 'rmpm-materialIssue-edit?id=' + reqId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.edit;
		$("#materialIssueDate").val(allData[0].materialIssueDate);
		$("#desc").val(allData[0].desc);
		$("#receiveDate").val(allData[0].receivingDate);
		$("#reqId").val(allData[0].reqId);
		$("#reqHeadId").html(allData[0].slipNo);
		$("#rfqValue").val(allData[0].reqId);

		console.log("edit data-->", allData);


		productOptions.api.setRowData(allData);
		$("#doctbodyData").empty();

		/* if (data[0].documentList != null
				&& data[0].documentList != "") {
			for (var i = 0; i < data[0].documentList.length; i++) {
				var tbl = '<tr>'
						+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="'+data[0].documentList[i].documnentName+'" class="form-control docNoclss" id="docnoid_'+i+'"> </div></td>'
						+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_'+i+'" id="uploadFor_'+i+'"> <i class="ti-pencil" id="clickImg_'+i+'"></i> </label>'
						+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
						+ i
						+ '"name="userImage" onchange="saveMultiFile(event)" /> </div>'
						+ '</div> <input type="hidden" id="uploadHidden_'+i+'" value="'+data[0].documentList[i].fileName+'" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_'+i+'" align="center" class="uploadedBillCls"><div class="uploadicon position-l">'
						+ data[0].documentList[i].action
						+ '</div></div>'
						+ '<div id="imageName_'+i+'" class="imageName">'
						+ data[0].documentList[i].fileName
						+ '</div>'
						+ '<input type="hidden" id="editId_'+i+'" value="'+ data[0].documentList[i].vendorRfqId+'">'
						+ '<div id="dltImage_'+i+'" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
						+ '</tr>';

				$("#doctbodyData").append(tbl);
			}
		} else {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
					+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
					+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
					+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
					+ '</tr>';
			$("#doctbodyData").append(tbl);
		} */

		//putOnHold();
		//headerStatus();
		$('.loader').hide();
		$("body").removeClass("overlay");
	});


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

function selectAutocompleteValueItem2() {

	$("#itemId").val("");
	$("#model").val("");
	$("#gstRate").val("");
	$("#sku").val("");
	$("#itemName").val("");
	$("#itemName").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

}




function getRfqDetails() {
	var search = $("#rfqValue").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "rmpm-materialIssue-getRFQList",
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
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem2(\''
								+ response.body[i].rfqId
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
								+ response.body[i].rfqId
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);

					} else {
						$("#itemName").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem2()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);
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
function selectAutocompleteValueItem2(rfqId, productId, productName, brandId, brandName,
	hsnCode, unit, gstRate, model) {
	if (rfqId) {
		//$("#sku").val(sku);
		$("#rfqValue").val(rfqId);
		$("#suggesstion-box2_").hide();
		getItemDetails(rfqId);
	} else {


		$("#suggesstion-box2_").hide();

	}
}

function getItemDetails(id) {
	console.log("id>>>>>>>>>>---", id);
	var reqId = id;
	$('.loader').show();
	$("body").addClass("overlay");
	agGrid.simpleHttpRequest({
		url: 'rmpm-materialIssue-getItemDetails?id=' + reqId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.edit;
		console.log("AllData-->", allData);
		/*const filteredData = allData.filter((element) => {
			return element.quantityIssued !== element.quantity;
		});*/

		if (allData) {
			productOptions.api.setRowData(allData);
			$('.loader').hide();
		}
		else {
			productOptions.api.setRowData([]);
			$('.loader').hide();
		}
		$("body").removeClass("overlay");
	});


}

function disableAll() {

	$("#desc").prop("readonly", true);
	$("#rfqValue").prop("readonly", true);
	$("#receiveDateCalendar1").css("pointer-events", "none");
	$("#receiveDateCalendar1").css("opacity", "0.5");
	$("#receiveDateCalendar2").css("pointer-events", "none");
	$("#receiveDateCalendar2").css("opacity", "0.5");
	$("#unit").attr("disabled", true);
}


function getRowDataBySlNo(targetSlNo) {
	let targetRowData = null;
	console.log("targetSlNo-->", targetSlNo);

	productOptions.api.forEachNode((node) => {
		const currentSlNo = node.rowIndex + 1;
		console.log("slno-->", currentSlNo);

		if (currentSlNo === parseInt(targetSlNo)) {
			targetRowData = node.data;
			console.log("in if->", currentSlNo);
		}
	});

	if (targetRowData) {
		console.log("Data for SlNo", targetSlNo, ":", targetRowData);
		return targetRowData;
	} else {
		return null;
	}
}

function editProduct(slNo) {

	//var reqId = $("#reqHeadId").html();
	var data = getRowDataBySlNo(slNo);
	console.log("rowNode.data==", data)
	

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
		$("#quantityIssued").val(data.quantity);


		//readonly for all the field
		readOnlyField();

	
	//$("#unitName").val(rowNode.data.unitName);


}

function readOnlyField() {
	$("#sku").prop("readonly", true);
	$("#itemId").prop("readonly", true);
	$("#itemName").prop("readonly", true);
	$("#model").prop("readonly", true);
	$("#quantity").prop("readonly", true);
	$("#unit").prop("readonly", true);
	$("#search").prop("readonly", true);
	$("#hsnCode").prop("readonly", true);
	$("#quantityIssued").prop("readonly", true);
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
	/*if ($('#grnNo').val() == null || $('#grnNo').val() == "") {
		validation = validationUpdated("GRN No Required", 'grnNo');
	}*/
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
		item.quantityIssued = $("#quantityIssued").val();
		item.grnNo = $("#grnNo").val();
		item.unit = $('#unit').val();
		item.unitName = $("#unit option:selected").text();
		var datas = [];

		console.log("datas-->", datas);
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
		$("#grnNo").val('');
		$("#quantityIssued").val('');
		$("#editProduct").val(null);
		console.log("datas-->", datas);
	}

}


function disableFalseAll() {

	$("#desc").prop("readonly", false);
	$("#rfqValue").prop("readonly", false);
	$("#receiveDateCalendar1").css("pointer-events", "all");
	$("#receiveDateCalendar1").css("opacity", "1");
	$("#receiveDateCalendar2").css("pointer-events", "all");
	$("#receiveDateCalendar2").css("opacity", "1");
	$("#unit").attr("disabled", false);
}


function editItems() {
	disableFalseAll();

}

function newMaterialIssueData() {
	disableFalseAll();
	$("#rfqValue").val('');
	$("#materialIssueDate").val('');
	$("#receiveDate").val('');
	gridOptions.api.deselectAll();

	productOptions.api.setRowData([]);
	$("#quantityIssued").val('');


	var currentDate = new Date();
	var day = currentDate.getDate();
	var month = currentDate.getMonth() + 1;
	var year = currentDate.getFullYear();

	var formattedDay = day < 10 ? "0" + day : day;
	var formattedMonth = month < 10 ? "0" + month : month;
	var formattedDate = formattedDay + "-" + formattedMonth + "-" + year;
	$("#materialIssueDate").val(formattedDate);
	//$("#receiveDate").val(allData[0].receiveDate);
	$("#reqHeadId").html('');
	$("#rfqValue").val('');
	$("#desc").val('');


}


function saveData() {
	console.log("Okkk");
	var item = {};
	var validation = true;

	/* if (item.receiveDate == null || item.receiveDate == "") {
		validation = validationUpdated("Date Required", 'receiveDate');
	} */

	var totalRowCount1 = productOptions.api.getModel().getRowCount();

	if (totalRowCount1 > 0) {
		item.desc = $("#desc").val();
		item.receiveDate = $("#receiveDate").val();
		item.slipId = $("#reqHeadId").html();
		item.reqId = $("#rfqValue").val();
		item.materialIssueDate = $("#materialIssueDate").val();

		var datas = [];
		productOptions.api.forEachNode(function(rowNode, index) {
			var item1 = rowNode.data;
			datas.push(item1);

		});
		item.details = datas;
		console.log("item>>>>", item);
		console.log("datas>>>>", datas);
		if (validation) {
			saveAllRequisition(item);
		}
	} else {
		$("#messageParagraph").text(
			"Add Atlest One Row");
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
		url: "rmpm-materialIssue-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Data Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				view();
				//selectFirstRow();
			}

		},
		error: function(datas) {
			console.log(datas)
		}
	})

}


function deleteProduct(event) {
	event.preventDefault();
	var selectedRows = productOptions.api.getSelectedRows();
	productOptions.api.applyTransaction({
		remove: selectedRows
	});

}



function approveOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.slipNo;
	});
	var reqId = selectedRowsString;

	var approveStatus = 1;
	//$('#approvePQtn').modal('show');
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "rmpm-materialIssue-approve?id=" + reqId,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("#messageParagraph").text("Requisition Approved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				$('#approve-btn').hide();
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

			} else {
				$('.loader').hide();
				$('#approvePQtn').modal('show');
			}

		},
		error: function(data) {
		}
	});
}


function approveRequisition() {
	$('#approvePQtn').modal('show');
}

function cancelApproveModalBtn() {
	$('#approvePQtn').modal('hide');
}

