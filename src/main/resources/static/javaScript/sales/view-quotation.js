let roleArray = [];
let accessedRole = ['rol001','rol009', 'rol011'];
$(function() {
	pno = 1;
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);

	var gridDiv = document.querySelector('#bomitem');
	new agGrid.Grid(gridDiv, bomitemOptions);

	var gridDiv = document.querySelector('#myGridDraft');
	new agGrid.Grid(gridDiv, gridDraftOptions);

	bomitemOptions.api.setRowData([]);

	$("#quotDraftSearchDiv,#addshippingAddressSec").addClass("d-none");

	


	$('#docTbl').on('click', '.rmv1', function() {

		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
		openDeleteConfirm();

	});

	CKEDITOR.replace('qutDescription', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('termCondition', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('itemDesc', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('skuDesc', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('bomDescription', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	$('#mySAGrid').hide();
	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#qutValidDate').val($(this).val());
	})

	$('#qutValidDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})
	$("#quotDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#quotationDate').val($(this).val());
	})

	$('#quotationDate').blur(function() {
		$("#quotDateCalendar").val($(this).val());
	})
	
//
//
				 $("#toDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#toDateQT').val($(this).val());
					})

					$('#toDateQT').blur(function () {
						$("#toDateQTCalendar").val($(this).val());
					})

//
					$("#fromDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#fromDateQT').val($(this).val());
					})

					$('#fromDateQT').blur(function () {
						$("#fromDateQTCalendar").val($(this).val());
					})
					
//
    function formatDate(date) {
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    }

const today = new Date();
const currentYear = today.getFullYear();
const currentMonth = today.getMonth();

const fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
const firstDayOfFY = new Date(fyStartYear, 3, 1);

$("#fromDateQT").val(formatDate(firstDayOfFY));
$("#toDateQT").val(formatDate(today));
						
			viewQuotation();		
//
	$(".br-s-btn,.br-s-btn-b,.br-s-btn-p,.br-s-btn-bom").hide();

	$('#itemName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#project').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#skuName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#bomItemName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
	$("#quickFilterBom").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterBom();
		}
	});


	let myRole = $("#myRole").val();
	if (myRole) {
		myRole = myRole?.replace('[', '')?.replace(']', '');
		roleArray = myRole.split(',').map(item => item.trim());
	}

	/*Mail CC And BCC Integration By Manoj*/
	$('#multipleMailCustomerCc').chosen();
	$('#multipleMailCustomerBcc').chosen();

	$('#multipleMailCustomerCc').on('change', function() {
		var selectedValues8 = $(this).val();
		console.log("Selected Values from multipleMailCc: ", selectedValues8);

		$('#toHiddenIdCc').val(selectedValues8 ? selectedValues8.join(',') : '');
		console.log("Updated Hidden Input Value (multipleMailCustomerCc): ", $('#toHiddenIdCc').val());

		updateDisabledOptions();
	});

	$('#multipleMailCustomerBcc').on('change', function() {
		var selectedValues9 = $(this).val();
		console.log("Selected Values from multipleMailBcc: ", selectedValues9);

		$('#toHiddenIdBcc').val(selectedValues9 ? selectedValues9.join(',') : '');
		console.log("Updated Hidden Input Value (multipleMailCustomerBcc): ", $('#toHiddenIdBcc').val());

		updateDisabledOptions();
	});

});
function updateDisabledOptions() {
	var selectedValues8 = $('#multipleMailCustomerCc').val() || [];
	var selectedValues9 = $('#multipleMailCustomerBcc').val() || [];

	// Disable options in multiple9 based on selected values in multiple8
	$('#multipleMailCustomerBcc option').each(function() {
		if (selectedValues8.includes($(this).val())) {
			$(this).attr('disabled', 'disabled');
		} else {
			$(this).removeAttr('disabled');
		}
	});

	// Disable options in multiple8 based on selected values in multiple9
	$('#multipleMailCustomerCc option').each(function() {
		if (selectedValues9.includes($(this).val())) {
			$(this).attr('disabled', 'disabled');
		} else {
			$(this).removeAttr('disabled');
		}
	});

	// Trigger chosen updates if using Chosen jQuery plugin
	$('#multipleMailCustomerCc').trigger('chosen:updated');
	$('#multipleMailCustomerBcc').trigger('chosen:updated');
}
//Quotation Draft

var columnDraftDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

},
{
	headerName: "Draft Id",
	field: "draftId",
	pinned: 'left',
	width: 140,
}, {
	headerName: 'Customer Name',
	field: "custName",
	width: 200,
	flex: 1
}, {
	headerName: 'Project',
	field: "project",
	width: 200,
	hide: true
}, {

	headerName: 'Subject',
	field: "subject",
	width: 200,
	flex: 1
}, {

	headerName: 'Valid Up To Date',
	field: "qutValidDate",
	width: 160,
	flex: 1,
	cellStyle: {
		textAlign: 'center'
	}


}
];

var gridDraftOptions = {
	columnDefs: columnDraftDefs,
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

	onSelectionChanged: rowSelectDraft,

};


function Cancel() {
	//$("#reqTable, .btn-hs, #myGrid, #sendEmail").show();
	//$("#addData, #suggesstion-box1_, #suggesstion-box2_, #suggesstion-box4_").hide();

	let fieldsToClear = [
		"#shippingHiddenId", "#quotationId", "#quotationReference", "#qutName", "#custId", "#custName", "#endCustName",
		"#custGSTNo", "#qutValidDate", "#qutDescription", "#qutActive", "#qutUpdatedOn", "#itemId", "#itemName",
		"#quantity", "#unitPrice", "#subject", "#customerAddress", "#project", "#sku", "#itemIgst", "#itemCgst",
		"#itemSgst", "#salespersonName", "#salesPersonId", "#dealName", "#terms", "#version", "#quotationDate",
		"#reference", "#salesPerson", "#qutCreatedBy", "#subTotal", "#grandTotal", "#slNo", "#qutNo", "#draftId",
		"#toDateCalendar", "#quotType", "#projectName"
	];

	fieldsToClear.forEach(field => $(field).val(""));
	itemOptions.api.setRowData();
	CKEDITOR.instances.qutDescription.setData("");
	CKEDITOR.instances.termCondition.setData("");
	CKEDITOR.instances?.itemDesc.setData("");
	CKEDITOR.instances?.skuDesc.setData("");
	CKEDITOR.instances?.bomDescription.setData("");
	$("#project").val("").trigger('change');
	$("#quotationReference").html("");
	$("#quotationReference").val("");
	$("#version").html("");
	$("#version").val("");
	$('.formValidation').remove();
	$('#quotationSaveBtn').removeClass('d-none');

	$("#doctbodyData").empty().append(
		'<tr>' +
		'<td style="display:none" align="center" class="pdb-24">' +
		'<input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
		'<td style="display:none"><div class="form-group">' +
		'<select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">' +
		'<option value="">Select</option> </select> </div></td>' +
		'<td><div class="form-group">' +
		'<input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
		'<td> <div class="control-group position-r">' +
		'<label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">' +
		'<i class="ti-plus" id="clickImg_0"></i> </label>' +
		'<div class="controls">' +
		'<input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />' +
		'</div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
		'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>' +
		'<div id="imageName_0" class="custom-file-upload"></div>' +
		'<input type="hidden" id="editId_0"> </td>' +
		'</tr>'
	);

	//$('#delete, #reqAppvBtn, #copyQuotation, #purchaseOrder').attr("disabled", true);
	// $('#add').attr("disabled", false);
}

function qoutationPdfDownload(qid) {
	var quotationId = '';
	if (qid == "") {
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		quotationId = selectedData[0].quotationId;
	} else {
		quotationId = qid;
	}
	if (quotationId) {
		var organization = $("#sessionOrganization").val();
		var orgDivision = $("#sessionOrgDivision").val();
		var userId = $("#sessionId").val();
		window.open("/sales/view-quotation-pdf-downloads?quotationId=" +
			window.btoa(quotationId) + "&organization=" +
			window.btoa(organization) + "&orgDivision=" +
			window.btoa(orgDivision) + "&userId=" +
			window.btoa(userId), '_blank');
	}
}

function setDescription() {
	
	$('#itemDesc').val('');
	let descData = '';
	if($('#itemName').val())
		descData = $('#itemName option:selected').text();
		
	if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData(descData || "");
	}
}

/* //quotation item  table add */
function saveTableData() {
	var editProduct = $("#editProduct").val();
	var item = {};
	var data = 1;
	var validation = true;

	if (!$('#itemName').val().trim()) {
		toastr.error("Item Name Required");
		validation = false;
		return false;
	}
	
	let itemDesc = CKEDITOR.instances['itemDesc'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');
	// itemDesc = itemDesc?.replace(/"/g, '\\"');
	
	if (!itemDesc.trim()) {
		toastr.error("Item Description Required");
		validation = false;
		return false;
	}

	if (validation) {
		item.slNo = data;
		itemOptions.api.forEachNode(function(rowNode, index) {
			if (!editProduct) {
				data = data + 1;
				item.slNo = data;
			} else {
				item.slNo = editProduct;
			}

		});

		let dataset = [];
		itemOptions.api.forEachNode(function(rowNode, index) {
			dataset.push(rowNode.data);
		});

		let itemId = $('#itemName').val();
		let filteredSet = dataset.filter(f => f.itemId === itemId && f.level === 'L1');

		if (filteredSet && filteredSet.length > 0 && filteredSet[0].slNo !== editProduct) {
			toastr.error("This Item Is Already Existed");
			return;
		}

		let cnt = dataset.filter(f => f.level === 'L1')?.length;
		if (!editProduct) {
			data = cnt + 1;
			item.slNo = data?.toString();
		} else {
			item.slNo = editProduct;
		}
		item.itemId = $('#itemName').val();
		item.itemName = $('#itemName option:selected').text();
		item.itemDesc = itemDesc;
		item.level = 'L1';

		var quot = [];
		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
			
			itemOptions.api.refreshCells({
		        rowNodes: [rowNode],
		        force: true,
		    });
		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			quot = quot.sort((x, y) => x?.slNo?.toString().localeCompare(y?.slNo?.toString()));
			itemOptions.api.setRowData(quot);
		}

		cancelItemDetails();

	}

}

let arrayAlpha = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

function saveTableData1() {
	var editProduct = $("#editProductSKU").val();
	var item = {};
	var data = 1;
	var validation = true;
	
	let itemDesc = CKEDITOR.instances['skuDesc'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');
	// itemDesc = itemDesc?.replace(/"/g, '\\"');

	if (!$('#skuName').val().trim()) {
		toastr.error("SKU Required");
		validation = false;
		return false;
	}
	if (!$('#quantity').val().trim()) {
		toastr.error("Quantity Required");
		validation = false;
		return false;
	}
	if (!$('#unitPrice').val().trim()) {
		toastr.error("Price Required");
		validation = false;
		return false;
	}
	if (!$('#unit').val().trim()) {
		toastr.error("Unit Measurement Required");
		validation = false;
		return false;
	}
	if (!$('#lineTotal').val().trim()) {
		toastr.error("Total Amount Required");
		validation = false;
		return false;
	}
	if (!itemDesc?.trim()) {
		toastr.error("Description Required");
		validation = false;
		return false;
	}

	let itemId = $("#skuItemName").val();

	if (validation) {

		let dataset = [];
		itemOptions.api.forEachNode(function(rowNode, index) {
			dataset.push(rowNode.data);
		});

		let fList = dataset.filter(f => f.itemId === itemId);
		let slno = 1;
		if (fList && fList.length > 0) {
			slno = fList[0].slNo;
		}

		let cnt = dataset.filter(f => f.parentItemId === itemId && f.level === 'L2')?.length;
		if (!editProduct) {
			data = slno + '.' + (cnt + 1);
			item.slNo = data;
		} else {
			item.slNo = editProduct;
			item.type = 'Added';
		}

		item.parentItemId = itemId;
		item.itemId = $('#skuName').val();
		item.hsnCode = $('#hsnCode').val();
		item.itemName = $('#skuName option:selected').text();
		item.quantity = $('#quantity').val();
		item.unitPrice = $('#unitPrice').val();
		item.sizeInMM = $('#sizeInMM').val();
		item.unit = $('#unit').val();
		item.unitName = $("#unit option:selected").text();
		item.gstRate = 0.0;
		item.lineTotal = $('#lineTotal').val();
		item.thicknessInMM = $('#thicknessInMM').val();
		item.itemDesc = itemDesc;
		var taxType = $("#taxType").val();
		item.level = 'L2';

		/* if (taxType == 'true') {
			item.itemCgst = (item.lineTotal * item.gstRate) / 200;
			item.itemSgst = (item.lineTotal * item.gstRate) / 200;
			item.itemIgst = 0;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemCgst
					+ item.itemSgst;
		} else {
			item.itemCgst = 0;
			item.itemSgst = 0;
			item.itemIgst = (item.lineTotal * item.gstRate) / 100;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemIgst;
		}
		 */

		if (taxType == 'true') {

			/* item.itemCgst = (item.lineTotal * item.gstRate) / 200;
			item.itemSgst = (item.lineTotal * item.gstRate) / 200;
			item.itemIgst = 0;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemCgst
					+ item.itemSgst; */

			var itemSgstFix = (parseFloat(item.lineTotal) * parseFloat(item.gstRate)) / 200;
			var itemCgstFix = (parseFloat(item.lineTotal) * parseFloat(item.gstRate)) / 200;

			item.itemCgst = parseFloat(itemCgstFix).toFixed(2);
			item.itemSgst = parseFloat(itemSgstFix).toFixed(2);
			item.itemIgst = 0;
			item.taxableAmt = (parseFloat(item.lineTotal) + parseFloat(item.itemCgst) +
				parseFloat(item.itemSgst)).toFixed(2);
		} else {
			item.itemCgst = 0;
			item.itemSgst = 0;
			item.itemIgst = (item.lineTotal * item.gstRate) / 100;
			item.taxableAmt = (parseFloat(item.lineTotal) + parseFloat(item.itemIgst)).toFixed(2);
		}
		//item.slNo = data;
		var quot = [];

		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
			
			itemOptions.api.refreshCells({
		        rowNodes: [rowNode],
		        force: true,
		    });
		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			quot = quot.sort((x, y) => x?.slNo?.toString().localeCompare(y?.slNo?.toString()));
			itemOptions.api.setRowData(quot);
			//calculation GST,IGST,SGST,CGST,SUBTOTAL,GRANDTOTAL
			var sum = 0.0;
			var gstRate = 0.0;
			var qSGST = 0.0;
			var qCGST = 0.0;
			var qIGST = 0.0;
			var itemIgst = 0.0;
			var itemCgst = 0.0;
			var itemSgst = 0.0;
			var len = quot.length;
			for (var i = 0; i < len; i++) {
				sum = sum + parseFloat(quot[i].lineTotal);
				if (taxType == 'false') {
					quot[i].itemIgst = quot[i].lineTotal * quot[i].gstRate /
						100;
					qIGST = qIGST + quot[i].itemIgst;
					grandTotal = sum + qIGST;
				} else {
					quot[i].itemCgst = quot[i].lineTotal * quot[i].gstRate /
						200;
					quot[i].itemSgst = quot[i].lineTotal * quot[i].gstRate /
						200;

					qCGST = qCGST + quot[i].itemCgst;
					qSGST = qSGST + quot[i].itemSgst;
					grandTotal = sum + qCGST + qSGST;
				}
			}
			$("#subTotal").val(sum);
			$("#qIGST").val(qIGST);
			$("#qCGST").val(qCGST);
			$("#qSGST").val(qSGST);
			$("#grandTotal").val(grandTotal)

		}

		//closeNav();
		cancelItemDetails1();
	}

}

function saveTableDataBom() {
	var item = {};
	var data = 1;
	var validation = true;

	let editProduct = $('#editProductBom').val().trim();
	
	let itemDesc = CKEDITOR.instances['bomDescription'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');
	// itemDesc = itemDesc?.replace(/"/g, '\\"');

	if (!itemDesc.trim()) {
		toastr.error("Description Required");
		validation = false;
		return false;
	}
	if (!$('#totalBomPrice').val().trim()) {
		toastr.error("Total BOM Price Required");
		validation = false;
		return false;
	}

	if (validation) {

		let itemId = $("#bomItemId").val();
		let skuId = $("#bomSkuId").val();

		let dataset = [];
		itemOptions.api.forEachNode(function(rowNode, index) {
			dataset.push(rowNode.data);
		});

		let fList = dataset.filter(f => f.itemId === itemId);
		let slno = 1;
		if (fList && fList.length > 0) {
			slno = fList[0].slNo;
		}
		let sList = dataset.filter(f => f.itemId === skuId && f.parentItemId === itemId);

		let slno2 = '1';
		let quantity = '0.00';
		let iName = '';
		if (sList && sList.length > 0) {
			slno2 = sList[0].slNo;
			quantity = sList[0].quantity;
			iName = sList[0].itemName;
		}

		if (!editProduct) {
			data = slno2 + '.1';
			item.slNo = data;
		} else {
			item.slNo = editProduct;
		}

		let desc = $("bomDescription").val();

		let bomD = [];
		bomitemOptions.api.forEachNode(function(rowNode, index) {
			bomD.push(rowNode.data);
		});

		item.parentItemId = itemId;
		item.parentSkuId = skuId;
		item.itemId = '';
		item.itemName = 'BOM Charge of ' + iName;
		item.parentSkuName = iName;
		item.quantity = quantity;
		item.unitId = '';
		item.unitName = '';
		item.itemDesc = itemDesc;
		item.unitPrice = $('#totalBomPrice').val();
		item.lineTotal = parseFloat(item.quantity) * parseFloat(item.unitPrice);
		item.level = 'L3';
		item.bomDetails = bomD;

		var quot = [];

		var rowNode1 = itemOptions.api.getRowNode(slno2);
		rowNode1.setDataValue("type", 'Added');

		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
			
			itemOptions.api.refreshCells({
		        rowNodes: [rowNode],
		        force: true,
		    });
		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			quot = quot.sort((x, y) => x?.slNo?.toString().localeCompare(y?.slNo?.toString()));
			itemOptions.api.setRowData(quot);
		}

		cancelItemDetailsBom();

	}

}



/* //Quotation  table add */

function save() {
	if (validProductData() && validFormData()) {
		var datas = [];
		var scopematrix = [];
		/* scopeMatrixOptions.api.forEachNode(function(rowNode, index) {
			console.log(rowNode.data)
			scopematrix.push(rowNode.data);
		}); */
		//console.log(scopematrix);
		var imageValid = true;
		var uploadList = [];
		var qutDescription = CKEDITOR.instances.qutDescription.getData();
		var termCondition = CKEDITOR.instances.termCondition.getData();
		$("#doctbodyData > tr").each(

			function() {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined' &&
					fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
					};
				} else {
					if ($("#quotationId").val()) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['quotationId'] = $("#quotationId").val();
				uploadData['documnentName'] = $(this).find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				if ($(this).find(".docNoclsss").val() != "" && $(this).find(".docNoclsss").val() != 'null' && fileName != "" && fileName != "null") {
					uploadList.push(uploadData);

				}

			});


		setTimeout(() => {
			if (itemOptions.api.getDisplayedRowCount() > 0) {

				let itemList = [];
				let bomDetails = [];
				itemOptions.api.forEachNode(rowNode12 => {
					//itemList.push({...rowNode12.data});
					itemList.push(JSON.parse(JSON.stringify(rowNode12.data)));
					if (rowNode12.data.level === 'L3') {
						let a = { itemId: rowNode12.data.parentSkuId, itemName: rowNode12.data.parentSkuName, bomDetails: rowNode12.data.bomDetails };
						bomDetails.push(a);
					}
				})

				console.log(itemList)

				itemOptions.api.forEachNode(function(rowNode, indrx) {
					if (rowNode.data.level === 'L2') {
						/*var obj = rowNode.data;
						obj.quotationId = $("#quotationId").val();
						obj.quotationNo = $("#qutNo").val();
						obj.version = $("#version").val();
						var version = $("#version").html();
						if (version == '' || version == null) {
							obj.version = 1;
						} else {
							obj.version = version;
						}
						obj.quotationDate = $("#quotationDate").val();
						obj.reference = $("#quotationReference").html();
						obj.qutValidDate = $("#qutValidDate").val();
						obj.custName = $("#custName").val();
						obj.custId = $("#custId").val();
						obj.subject = $("#subject").val();
						obj.scopematrix = scopematrix;
						obj.qutDescription = qutDescription;
						obj.termCondition = termCondition;
						obj.documentList = uploadList;
						obj.subTotal = 0;
						obj.grandTotal = 0;
						obj.qIGST = 0;
						obj.qCGST = 0;
						obj.qSGST = 0;
						obj.quotType = $('#quotType').val();
						obj.shippingHiddenId = $('#shippingHiddenId').val();
						obj.project = $('#project').val();
						var tt = $('#taxType').val();
						if (tt == 'true') {
							obj.taxType = true;
						} else {
							obj.taxType = false;
						}
					    
						let jsonString1 = JSON.stringify(bomDetails);
						jsonString1 = jsonString1.replace(/[\u0000-\u001F\u007F-\u009F]/g, "");
						obj.bomList = jsonString1.replace(/'/g, "\\'");
					    
						let jsonString = JSON.stringify(itemList);
						jsonString = jsonString.replace(/[\u0000-\u001F\u007F-\u009F]/g, "");
					    
						obj.itemDetails = jsonString.replace(/'/g, "\\'");
						obj.draftId = $("#draftId").val();
						datas.push(obj);*/

						const obj = {
							...rowNode.data, // shallow copy
							quotationId: $("#quotationId").val(),
							quotationNo: $("#qutNo").val(),
							version: $("#version").val() || 1,
							quotationDate: $("#quotationDate").val(),
							reference: $("#quotationReference").html(),
							qutValidDate: $("#qutValidDate").val(),
							custName: $("#custName").val(),
							endCustName: $("#endCustName").val(),
							custId: $("#custId").val(),
							subject: $("#subject").val(),
							scopematrix: scopematrix,
							qutDescription: qutDescription,
							termCondition: termCondition,
							documentList: uploadList,
							subTotal: 0,
							grandTotal: 0,
							qIGST: 0,
							qCGST: 0,
							qSGST: 0,
							quotType: $('#quotType').val(),
							shippingHiddenId: $('#shippingHiddenId').val(),
							project: $('#project').val(),
							taxType: $('#taxType').val() === 'true',
							bomList: JSON.stringify(bomDetails).replace(/[\u0000-\u001F\u007F-\u009F]/g, "").replace(/'/g, "\\'")?.replace(/"/g, '\\"'),
							itemDetails: JSON.stringify(itemList).replace(/[\u0000-\u001F\u007F-\u009F]/g, "").replace(/'/g, "\\'")?.replace(/"/g, '\\"'),
							draftId: $("#draftId").val()
						};

						datas.push(obj);
					}
				});

				if (datas && datas.length > 0) {
					console.log(datas)
					saveAllQuotation(datas);
				} else {
					toastr.error('Please Add SKU');
				};
			} else {
				toastr.error('Please Add SKU');
			}
		}, 1000);

	}
}

//Qutation Draft Save

function saveAsDraft() {
	if (validProductData() && validFormData()) {
		var datas = [];
		/* var scopematrix = [];
		scopeMatrixOptions.api.forEachNode(function(rowNode, index) {
			scopematrix.push(rowNode.data);
		}); */
		var imageValid = true;
		var uploadList = [];
		var qutDescription = CKEDITOR.instances.qutDescription.getData();
		var termCondition = CKEDITOR.instances.termCondition.getData();
		$("#doctbodyData > tr").each(

			function() {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined' &&
					fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
					};
				} else {
					if ($("#draftId").val()) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['draftId'] = $("#draftId").val();
				uploadData['documnentName'] = $(this).find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				if ($(this).find(".docNoclsss").val() != "" && $(this).find(".docNoclsss").val() != 'null' && fileName != "" && fileName != "null") {
					uploadList.push(uploadData);
					console.log(uploadData)

				}

			});
		setTimeout(function() {
			if (itemOptions.api.getDisplayedRowCount() > 0) {

				let itemList = [];
				let bomDetails = [];
				itemOptions.api.forEachNode(rowNode12 => {
					itemList.push({ ...rowNode12.data });
					if (rowNode12.data.level === 'L3') {
						let a = { itemId: rowNode12.data.parentSkuId, itemName: rowNode12.data.parentSkuName, bomDetails: rowNode12.data.bomDetails };
						bomDetails.push(a);
					}
				})

				itemOptions.api.forEachNode(function(rowNode, indrx) {

					if (rowNode.data.level === 'L2') {
						var obj = JSON.parse(JSON.stringify(rowNode.data));
						obj.draftId = $("#draftId").val();
						//obj.quotationNo = $("#qutNo").val();
						obj.version = $("#version").val();
						var version = $("#version").html();
						if (version == '' || version == null) {
							obj.version = 1;
						} else {
							obj.version = version;
						}
						obj.quotationDate = $("#quotationDate").val();
						//obj.quotationId = $("#quotationId").val();
						//obj.reference = $("#quotationReference").html();
						obj.qutValidDate = $("#qutValidDate").val();
						obj.custName = $("#custName").val();
						obj.custId = $("#custId").val();
						obj.endCustName = $("#endCustName").val();
						obj.subject = $("#subject").val();
						//obj.orderType= $("#orderType").val();
						//obj.scopematrix = scopematrix;
						obj.qutDescription = qutDescription;
						obj.termCondition = termCondition;
						obj.documentList = uploadList;
						obj.subTotal = 0.0;
						obj.grandTotal = 0.0;
						obj.qIGST = 0.0;
						obj.qCGST = 0.0;
						obj.qSGST = 0.0;
						obj.quotType = $('#quotType').val();
						obj.shippingHiddenId = $('#shippingHiddenId').val();
						obj.project = $('#project').val();
						var tt = $('#taxType').val();
						if (tt == 'true') {
							obj.taxType = true;
						} else {
							obj.taxType = false;
						}

						let jsonString1 = JSON.stringify(bomDetails);
						jsonString1 = jsonString1.replace(/[\u0000-\u001F\u007F-\u009F]/g, "")?.replace(/"/g, '\\"');
						obj.bomList = jsonString1;

						let jsonString = JSON.stringify(itemList);
						jsonString = jsonString.replace(/[\u0000-\u001F\u007F-\u009F]/g, "")?.replace(/"/g, '\\"');

						obj.itemDetails = jsonString;
						
						datas.push(obj);
					}

				});
				if (datas && datas.length > 0) {
					saveQuotationDraft(datas);
				} else {
					toastr.error('Please Add SKU');
				}

			} else {
				toastr.error('Please Add SKU');
			}
		}, 1000)

	}
}



function deleteQuotation() {
	$('.loader').show();
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].quotationId;
	$.ajax({
		type: "GET",
		url: "view-quotation-delete?id=" + id,
		async: false,
		success: function(response) {

			if (response.message == "Success") {
				$('.loader').hide();
				toastr.success('Quotation Deleted Successfully');
				viewQuotation();
				setTimeout(() => {
					if (gridOptions.api) {
						gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
					}
				}, 300);
				//location.reload();
			}

		},
		error: function(data) {
			$('.loader').hide();
		}
	});
}

//saveTableDataSale()
function saveAllQuotation(datas) {
	$('.loader').show();
	//$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "view-quotation-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				//$('.loader').hide();
				//$("body").removeClass("overlay");

				$('.loader').hide();
				viewQuotation();
				let employeeNameTop = $("#employeeNameTop").text();

				if (employeeNameTop) {
					toastr.success('Quotation Modified Successfully');
				} else {
					toastr.success('Quotation Saved Successfully');
				}

				$('#myGridDraft').addClass('d-none');
				$('#myGrid').removeClass('d-none');
				$('#viewQuotBtn').addClass('d-none');
				$('#viewDraftBtn').removeClass('d-none');
				$('#deleteQuot').removeClass('d-none');
				$('#deleteDraft').addClass('d-none');

				const exists = accessedRole.some(item => roleArray.includes(item));
				if (exists) {
					$('#approveBtn').removeClass('d-none');
				}

				$('#pdfButton').removeClass('d-none');
				$('#copyBtn').removeClass('d-none');
				$('#revisionBtn').removeClass('d-none');
				nextTab('quotationInfLiId');
				setTimeout(() => {
					const quotationId = response.body[0].itemId;
					const allNodes = [];

					gridOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});

					const rowNode = allNodes.find(node => node.data.quotationId == quotationId);

					if (rowNode) {
						rowNode.setSelected(true);
						gridOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
				$("#quotationId").val(response.body[0].itemId);

				bomitemOptions.api.setRowData([]);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something went to wrong!');
			}

		},
		error: function(error) {
			console.log(error)
			$('.loader').hide();
		}
	})

}

//Save Quotation Draft

function saveQuotationDraft(datas) {
	$('.loader').show();
	//$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "view-quotation-draftdata",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				nextTab('quotationInfLiId');
				toastr.success('Draft added successfully');
				bomitemOptions.api.setRowData([]);
				viewDraft();
				/* setTimeout(() => {
					const quotationId=response.body[0].itemId;
					const allNodes = [];
				    
					gridDraftOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});

					const rowNode = allNodes.find(node => node.data.quotationId == quotationId);
				    
					if (rowNode) {
						rowNode.setSelected(true);
						gridDraftOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				},300); */
				$("#draftId").val(response.body[0].itemId);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something went to wrong!');
			}

		},
		error: function(datas) {
			console.log(datas)
			$('.loader').hide();
		}
	})

}

function validFormData() {
	var custName = $("#custName").val();
	var qutValidDate = $("#qutValidDate").val();
	var subject = $("#subject").val();
	var project = $("#project").val();

	if (custName == null || custName.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}
	if (qutValidDate == null || qutValidDate.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Quotation Valid Date is required');
		return false;
	}
	if (subject == null || subject.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Subject is required');
		return false;
	}

	return true;
}


function validProductData() {
	return true;
}

function approveOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	var reference = '';
	var sub = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
			reference += ',';
		}
		selectedRowsString += selectedRow.quotationId;
		reference += selectedRow.reference;
		sub += selectedRow.subject;
	});

	var quotationId = selectedRowsString;
	var organization = $("#sessionOrganization").val();
	var orgDivision = $("#sessionOrgDivision").val();
	var sessionUserId = $("#sessionId").val();
	var baseUrl = $("#baseUrl").val();
	var url = baseUrl + "sales/view-quotation-pdf-downloads?quotationId=" + window.btoa(quotationId) + "&organization=" + window.btoa(organization) + "&orgDivision=" + window.btoa(orgDivision) + "&userId=" + window.btoa(sessionUserId);
	var approveStatus = 1;
	//$('#approveQuot').modal('show');
	$('.loader').show();

	$.ajax({
		type: "GET",
		url: "view-quotation-approve-th-ajax?approveStatus=" + approveStatus + "&quotationId=" + quotationId + "&reference=" + reference + "&url=" + window.btoa(url) + "&sub=" + sub,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				viewQuotation();
				toastr.success('Quotation Approved successfully');
				setTimeout(() => {
					const allNodes = [];
					gridOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});

					const rowNode = allNodes.find(node => node.data.quotationId == quotationId);

					if (rowNode) {
						rowNode.setSelected(true);
						gridOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
			} else {
				$('.loader').hide();
				//$('#approveQuot').modal('show');
			}

		},
		error: function(data) { }
	});
}

function rowSelectDraft() {
	var selectedRows = gridDraftOptions.api.getSelectedRows();
	var selectedNodes = gridDraftOptions.api.getSelectedNodes();
	$("#approveStatus").val('');
	var selectedData = selectedNodes.map(node => node.data);
	$("#previewLiId,#mailId").addClass("d-none");
	nextTab('quotationInfLiId');
	disableFields()
	if (selectedRows && selectedRows.length > 0) {
		var cName = selectedRows[0].custName;
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3").text(cName);
		var draftId = selectedData[0].draftId;
		editDraft(draftId);
		$('#editQuotation').removeClass("d-none");
		$('#deleteDraft').removeClass("d-none");
	} else {
		$("#previewLiId").addClass("d-none");
		$("#copyQout,#downloadPDF").attr("disabled", true);
		$('#pdfButton').addClass('d-none');
		$('#copyBtn').addClass('d-none');
		$('#approveBtn').addClass('d-none');
		$('#revisionBtn').addClass('d-none');
		$('#mailId').addClass('d-none');
		$('#mail').addClass('d-none');
		$('#quotationSaveBtn').removeClass('d-none');
		$('#editQuotation').addClass('d-none');
		$('#deleteQuot,#deleteDraft').addClass('d-none');
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3").text('');
		add();
	}
}




function closeModal1() {
	$("#myModal").modal('hide');

}

function deleteMainProductOnclick() {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t delete Product');
		return;
	}

	const selectedRows = itemOptions.api.getSelectedRows();
	let id = selectedRows[0].itemId;

	let dataset = [];
	itemOptions.api.forEachNode(a => {
		if (a.data.itemId === id || a.data.parentItemId === id) {
			dataset.push(a.data);
		}
	});
	itemOptions.api.applyTransaction({ remove: dataset });

	bomitemOptions.api.setRowData([]);

	const tooltip = document.querySelector('.tooltip');
	if (tooltip) {
		tooltip.remove();
	}

	toastr.success('Item Deleted Successfully');
}

function deleteProductOnclick(id) {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t delete SKU');
		return;
	}

	let dataset = [];
	itemOptions.api.forEachNode(a => {
		dataset.push(a.data);
	});

	let b = dataset?.filter(f => f.itemId === id);
	let c = dataset?.filter(f => f.parentSkuId === id);

	if (b && b.length > 0) {
		itemOptions.api.applyTransaction({ remove: [{ slNo: b[0].slNo }] });
	}

	if (c && c.length > 0) {
		itemOptions.api.applyTransaction({ remove: [{ slNo: c[0].slNo }] });
	}

	bomitemOptions.api.setRowData([]);

	const tooltip = document.querySelector('.tooltip');
	if (tooltip) {
		tooltip.remove();
	}

	toastr.success('Item Deleted Successfully');

}

function deleteProductOnclickBom(id) {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t delete BOM');
		return;
	}

	itemOptions.api.applyTransaction({ remove: [{ slNo: id }] });

	let pid = id.split('.').slice(0, 2).join('.');
	const rowNode = itemOptions.api.getRowNode(pid);

	let dataset = [];
	itemOptions.api.forEachNode(a => {
		if (a.data.slNo === pid) {
			a.data.type = '';
		}
		dataset.push(a.data);
	});

	itemOptions.api.setRowData(dataset);

	const tooltip = document.querySelector('.tooltip');
	if (tooltip) {
		tooltip.remove();
	}

	toastr.success('Item Deleted Successfully');
}

function deleteBom() {
    var status = $("#approveStatus").val();
    if (status === "Approved" || status === "Revised") {
        toastr.error("Quotation is approved, you can't delete BOM");
        return;
    }

    const selectedRows = bomitemOptions.api.getSelectedRows();
    bomitemOptions.api.applyTransaction({ remove: selectedRows });

    // Delay total calculation until grid is updated
    setTimeout(() => {
        let dataset = [];
        let totalPrice = 0;

        bomitemOptions.api.forEachNodeAfterFilterAndSort((node, index) => {
            node.data.slNo = (index + 1).toString();
            dataset.push(node.data);

            let lineTotal = parseFloat(node.data.lineTotal || 0);
            totalPrice += lineTotal;
        });

        bomitemOptions.api.setRowData(dataset);
        $("#totalBomPrice").val(totalPrice.toFixed(2));

        toastr.success("Item Deleted Successfully");
    }, 0);
}


/* customer AutoSearch */


function getCustomerList() {
	var search = $("#custName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "view-quotation-get-customer-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:-30px; font-weight:100; font-size:12px;" class="autocompletedata cp" onClick="selectAutocompleteValue1(\'' +
								response.body[i].custId +
								'\',\'' +
								response.body[i].custName +
								'\',\'' +
								response.body[i].custGSTNo +
								'\',\'' +
								response.body[i].taxType +
								'\',\'' +
								response.body[i].project +
								'\')">' +
								response.body[i].custName +
								'</li>';
						}
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;" onClick="selectAutocompleteValue()">' +
							"No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	} else {
		$("#project").val("").trigger("change");;
		$("#projectName").val("");
		$("#projectId").val("");
		$("#addre").hide();
		$("#suggesstion-box1_").hide();
	}
}

function selectAutocompleteValue1(custId, custName, custGSTNo, taxType, project) {
	if (custId) {
		$("#custId").val(custId);
		$("#custName").val(custName);
		$("#custGSTNo").val(custGSTNo);
		$("#taxType").val(taxType);
		$("#project").val(project).trigger("change");;
		$("#projectName").val(project);
		$("#projectId").val(project);
		$("#search").val(custName);
		$("#search").attr('data-procat', custId);
		$("#suggesstion-box1_").hide();
		$("#addre").show();
		hideShowS();
		//checkForDuplicate(key,counter);
		getAddressDetails(custId, "")
	} else {
		$("#custId").val("");
		$("#project").val("").trigger("change");;
		$("#projectName").val("");
		$("#projectId").val("");
		$("#custName").val("");
		$("#custGSTNo").val("");
		$("#taxType").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();


	}
}

function selectAutocompleteValue() {

	$("#custId").val("");

	$("#custName").val("");
	$("#custGSTNo").val("");
	$("#taxType").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box1_").hide();

}

function selectAutocompleteValueItem1(sku, productId, productName, brandId, brandName,
	hsnCode, unit, gstRate) {
	if (sku) {
		$("#itemName").val(window.atob(productName));
		$("#itemId").val(productId);
		$("#brandName").val(window.atob(brandName));
		$("#brandId").val(brandId);
		$("#sku").val(sku);
		$("#hsnCode").val(window.atob(hsnCode));
		$("#unit").val(window.atob(unit));
		$("#gstRate").val(window.atob(gstRate));
		$("#itemName").attr('data-procat', itemId);
		$("#suggesstion-box_").hide();
		hideShowS();
	} else {
		$("#itemId").val("");
		$("#itemName").val("");
		$("#gstRate").val("");
		$("#sku").val("");
		$("#brandName").val("");
		$("#brandId").val("");
		$("#hsnCode").val("");
		$("#unit").val("");
		$("#itemName").attr('data-procat', "");
		$("#suggesstion-box_").hide();

	}
}

function selectAutocompleteValueItem() {

	$("#itemId").val("");

	$("#gstRate").val("");
	$("#sku").val("");
	$("#itemName").val("");
	$("#itemName").attr('data-procat', "");
	$("#suggesstion-box_").hide();

}



function hideShowS() {
	var taxType = $("#taxType").val();
	if (taxType == "true") {
		$('#igstTR').hide();
		$('#cgstTR').show();
		$('#sgstTR').show();

	} else {
		$('#igstTR').show();
		$('#cgstTR').hide();
		$('#sgstTR').hide();

	}

}
var totalLine;

function calculateLineTotal() {
	var price = $("#unitPrice").val();
	var quantity = $("#quantity").val();

	var mul = price * quantity;
	$("#lineTotal").val((mul).toFixed(2));

}

function calculateSubTotal() { }

function getGSTRateCal() { }

/*
image upload fn
 */

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();
	var iURL = URL.createObjectURL(uFile);
	var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	$("#uploadedBillDiv_").html(LightImg);
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
		url: "view-quotation-upload-file",
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

function addSalesPerson() {
	$('#addSalespersonModel').modal('show');
}

function cancelDetails() {
	$('#addSalespersonModel').modal('hide');
	$("#spName").val('');
	$("#spGender").val('');
	$("#dobid").val('');
	$("#mobileno").val('');
	$("#personalmailid").val('');
	$("#addressid_").val('');
}

function checkAlphabet(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z. ]/g, '');
	tempVal = tempVal.replace(/^\w/, c => c.toUpperCase());

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (position == 1 && tempVal.charAt(0) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

function checkNumeric(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (tempVal.slice(-1) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

//Amount validation
function checkAmount(fieldId) {
	var myField = document.getElementById("openingBalance")
	var reg = /^\d{0,9}(\.\d{0,2})?$/;
	if (reg.test(myField.value)) {
		$("#" + fieldId).val();
		reg = '';
	} else {
		$("#" + fieldId).val(null);
	}
}



function selectAutocompleteValueep() {
	$("#projectId").val("");
	$("#project").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box4_").hide();
}

function selectAutocompleteValuep(project, projectName) {
	$("#projectId").val(project);
	$("#project").val(projectName);
	$("#search").val(projectName);
	$("#search").attr('data-procat', project);
	$("#suggesstion-box4_").hide();
}


//Sales person auto search

function selectAutocompleteValuee() {

	$("#salesPersonId").val("");

	$("#salespersonName").val("");
	$("#custGSTNoo").val("");
	$("#taxTypee").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

}

function selectAutocompleteValue2(salespersonId, spName, custGSTNo, taxType) {



	$("#salesPersonId").val(salespersonId);
	$("#salespersonName").val(spName);
	$("#custGSTNoo").val(custGSTNo);
	$("#taxTypee").val(taxType);
	$("#search").val(spName);
	$("#search").attr('data-procat', salespersonId);
	$("#suggesstion-box2_").hide();
	//	hideShowS();
	//checkForDuplicate(key,counter);


}


function cancelBtn() {
	$('#addAddressModel').modal('hide');
	//$("#billingInfo").hide();
}

function cancelBtn2() {
	$('#addAddressModel1').modal('hide');
	//$("#shippingInfo").hide();
}

function tcscancelBtn() {
	$('#manageTCsModel').modal('hide');
}




function saveFile1(event) {
	var uFile = $(uploadDoc_0)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_0").html("");
	if (extension[1] === "jpg" || extension[1] === "png" || extension[1] === "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] === "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon'></i></a>";
	} else if (extension[1] === "xls" || extension[1] === "xlsx" || extension[1] === "csv") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] === "doc" || extension[1] === "docx" || extension[1] === "dox") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}

	$("#uploadedBillDiv_0").html(LightImg);
	$("#imageName_0").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');
	$.ajax({
		type: "POST",
		url: "view-quotation-upload-file1",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) { },
		error: function(e) {

		}
	});
}

function selectAutocompleteValuee1() {

	$("#tcsId").val("");

	$("#tcsName").val("");
	$("#custGSTNoo1").val("");
	$("#taxTypee1").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box3_").hide();

}

function selectAutocompleteValue3(tcsId, taxName, rateId, taxType) {



	$("#tcsId").val(tcsId);
	$("#tcsName").val(taxName);
	$("#tcsValue").val(rateId);
	$("#taxTypee1").val(taxType);
	$("#search").val(taxName);
	$("#search").attr('data-procat', tcsId);
	$("#suggesstion-box3_").hide();
	//	hideShowS();
	//checkForDuplicate(key,counter);
	tcsCalculation();
	//tcsAmountCalculation();

}

function AddTCS() {

	$('#manageTCsModel').modal('show');

}


function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;

	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}

	let fnametext = '<div id="imageName_' + counter + '" class="imageName" style="margin-left: 2px;">' + fileName + '</div><span><i class="ti-close red close_sec1 deleteFileDoc" onclick="openDeleteConfirm(' + counter + ')"></i></span>';

	if (extension[1] === "jpg" || extension[1] === "png" || extension[1] === "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] === "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon'></i></a>";
	} else if (extension[1] === "xls" || extension[1] === "xlsx" || extension[1] === "csv") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] === "doc" || extension[1] === "docx" || extension[1] === "dox") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}


	$("#clickImg_" + counter).removeClass('ti-plus').addClass('ti-pencil');
	// $("#uploadHidden_"+ counter).val(fileName);
	$("#uploadedBillDiv_" + counter).html(LightImg + fnametext);

}

function checkEmptyQuot() {

	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclsss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error('Please Choose a File');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMoreQuot()
	}
}

function addMoreQuot() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:last").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclsss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	// $("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');
		selectInput.eq(0).attr('id', "docid_" + i);

		textInput.eq(1).attr('id', "docnoid_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(5).attr('id', "uploadedBillDiv_" + i);
		/*divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);*/
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

	$("#uploadedBillDiv_" + lengthOfTableRow).empty();

}

function checkForDuplicateEntry(event) {
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var count = 0;
	$(".docNoclsss").each(function() {
		if (document == $(this).val()) {
			count++;
		}
	})
	if (count >= 2) {
		toastr.error('Document Name Already Entered');
		return false;
	} else {
		return true;
	}

}

function openDeleteConfirm() {
	//$("#dltValue").val("");
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	//closeDeleteConfirm();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
			'<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>' +
			'<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
			'<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>' +
			'<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
			'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div> </td>' +
			'</tr>';
		$("#doctbodyData").append(tbl);
	}
}

function changeDateFormat(inputDate) { // expects Y-m-d
	var splitDate = inputDate.split('-');
	if (splitDate.count == 0) {
		return null;
	}
	var year = splitDate[0];
	var month = splitDate[1];
	var day = splitDate[2];
	return day + '-' + month + '-' + year;
}


//Edit Draft

function editDraft(draftId) {
	Cancel();
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "view-quotation-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				$("#quotationId").val(response.body[0].key);
			}
		},
		error: function(e) { }
	});

	agGrid.simpleHttpRequest({
		url: "view-quotation-draft-edit-new?draftId=" + draftId,
	}).then(
		function(data) {
			$('.loader').hide();
			// itemOptions.api.setRowData(data);

			let itemDetails = data[0]?.itemDetails;

			if (itemDetails) {
				let safeJson = itemDetails.replace(/[\u0000-\u001F]/g, "");
				let parsedData = JSON.parse(safeJson)
				itemOptions.api.setRowData(parsedData);
			} else {
				itemOptions.api.setRowData([]);
			}

			$("#draftId").val(data[0].draftId);
			$("#quotationDate").val(data[0].quotationDate);
			$("#qutValidDate").val(data[0].qutValidDate);
			$("#endCustName").val(data[0].endCustName);
			$("#subject").val(data[0].subject);
			$("#custName").val(data[0].custName);
			$("#custId").val(data[0].custId);
			$("#grandTotal").val(data[0].grandTotal);
			$("#qSGST").val(data[0].qSGST);
			$("#qCGST").val(data[0].qCGST);
			$("#subTotal").val(data[0].subTotal);
			$("#qIGST").val(data[0].qIGST);
			$("#taxType").val(data[0].taxType);
			$("#quotType").val(data[0].quotType);
			$("#shippingHiddenId").val(data[0].shippingHiddenId);
			$("#project").val(data[0].project).trigger("change");
			$("#projectName").val(data[0].projectName);
			/* if(data[0].quotType=="QTM0001"){
				$("#main_content1").hide();
			}else{
				$("#main_content1").show();
			} */
			//getAddressDetails(data[0].custId);
			getAddressDetails(data[0].custId, data[0].shippingHiddenId);
			CKEDITOR.instances['qutDescription'].setData(data[0].qutDescription);
			CKEDITOR.instances['termCondition'].setData(data[0].termCondition);

			$("#doctbodyData").empty();

			if (data[0].documentList != null && data[0].documentList != "") {
				let documentList = data[0].documentList;
				for (var i = 0; i < documentList.length; i++) {

					let cls = 'ti-plus';

					if (documentList[i].fileName) {
						cls = 'ti-pencil';
					}

					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword,application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr>'
						+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';

					$("#doctbodyData").append(tbl);
				}
			} else {
				let i = 0;
				let a = '<div class="form-group d-flex"><div class="">' +
					'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
					'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
					'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
					'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
					'<div id="validationDiv"></div></div>';

				var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
					+ '<td>' + a + '</td>'
					+ '</tr>';
				$("#doctbodyData").append(tbl);
			}


		});

}

function deleteDraft() {
	$('.loader').show();
	var selectedRows = gridDraftOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.draftId;
	});
	if (selectedRowsString) {
		var item = {};
		item.draftId = selectedRowsString;

		$.ajax({
			type: "POST",
			url: "view-quotation-draft-delete",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {

				//$('#deleteQuot').modal('hide');

				//$('#deleteDraft').attr("disabled", true);

				if (response.message == "Success") {
					$('.loader').hide();
					toastr.success('Draft Deleted successfully');
					viewDraft();
					Cancel();
				} else {

				}
			},
			error: function(data) {
				console.log(data)
				$('.loader').hide();
			}
		})
	}

}

function updateQuotationVersion(id, version) {
  if (/-\d+$/.test(id)) {
    // If version already exists, replace it
    return id.replace(/-\d+$/, `-${version}`);
  } else {
    // If no version, add it
    return `${id}-${version}`;
  }
}

//Create New Version 

function revision() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.quotationId;
	});

	showLoader();
	gridOptions.api.deselectAll();
	
	Cancel();
	enableFields();
	$('#approveStatus').val('');
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#quotationDate").val(newDate);
	
	if (selectedRowsString) {
	   setTimeout(()=>{
	   		agGrid.simpleHttpRequest({
			url: "view-quotation-revision-new?id=" + selectedRowsString,
		}).then(function(data) {
			hideLoader()
			if (!data || data.length === 0) {
				console.error("No data received for revision.");
				return;
			}

			const revisionData = data[0];
			if (!revisionData) return;
			
			let version = parseFloat(revisionData?.lastVersion || '0');
			let qId = revisionData?.quotationId;
			$("#quotationId").val(updateQuotationVersion(qId,version));

			let itemDetails = data[0]?.itemDetails;

			if (itemDetails) {
				let safeJson = itemDetails.replace(/[\u0000-\u001F]/g, "");
				let parsedData = JSON.parse(safeJson)
				itemOptions.api.setRowData(parsedData);
			} else {
				itemOptions.api.setRowData([]);
			}

			$("#version").html(revisionData.version);
			$("#quotationReference").html(revisionData.reference);

			$("#qutValidDate").val(revisionData.qutValidDate || "");
			$("#endCustName").val(revisionData.endCustName || "");
			$("#subject").val(revisionData.subject || "");
			$("#custName").val(revisionData.custName || "");
			$("#custId").val(revisionData.custId || "");
			$("#grandTotal").val(revisionData.grandTotal || "");
			$("#qSGST").val(revisionData.qSGST || "");
			$("#qCGST").val(revisionData.qCGST || "");
			$("#subTotal").val(revisionData.subTotal || "");
			$("#qIGST").val(revisionData.qIGST || "");
			$("#taxType").val(revisionData.taxType || "");
			$("#project").val(revisionData.project).trigger("change");
			$("#projectName").val(revisionData.projectName);

			getAddressDetails(revisionData.custId, revisionData.shippingHiddenId);
			$("#custName,#project").attr("disabled", true);
			if (CKEDITOR.instances['qutDescription']) {
				CKEDITOR.instances['qutDescription'].setData(revisionData.qutDescription || "");
			}
			if (CKEDITOR.instances['termCondition']) {
				CKEDITOR.instances['termCondition'].setData(revisionData.termCondition || "");
			}

			$("#doctbodyData").empty();

			if (revisionData.documentList && revisionData.documentList.length > 0) {
				let documentList = revisionData?.documentList;
				for (var i = 0; i < documentList.length; i++) {

					let cls = 'ti-plus';

					if (documentList[i].fileName) {
						cls = 'ti-pencil';
					}

					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr>'
						+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';

					$("#doctbodyData").append(tbl);
				}
			} else {
				let i = 0;
				let a = '<div class="form-group d-flex"><div class="">' +
					'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
					'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
					'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
					'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
					'<div id="validationDiv"></div></div>';

				var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
					+ '<td>' + a + '</td>'
					+ '</tr>';
				$("#doctbodyData").append(tbl);
			}
		});
	   },1000)

	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}

}

function copyQuotation() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.quotationId;
	});
	Cancel();
	enableFields();
	$('#approveStatus').val('');
	gridOptions.api.deselectAll();

	if (selectedRowsString) {
		agGrid.simpleHttpRequest({
			url: "view-quotation-revision-new?id=" + selectedRowsString,
		}).then(function(data) {

			itemOptions.api.setRowData(data);
			if (data.length > 0) {
				let qData = data[0];

				let itemDetails = data[0]?.itemDetails;

				if (itemDetails) {
					let safeJson = itemDetails.replace(/[\u0000-\u001F]/g, "");
					let parsedData = JSON.parse(safeJson)
					itemOptions.api.setRowData(parsedData);
				} else {
					itemOptions.api.setRowData([]);
				}

				$("#qutValidDate").val(qData.qutValidDate);
				$("#endCustName").val(qData.endCustName);
				$("#subject").val(qData.subject);
				$("#custName").val(qData.custName);
				$("#custId").val(qData.custId);
				$("#grandTotal").val(qData.grandTotal);
				$("#qSGST").val(qData.qSGST);
				$("#qCGST").val(qData.qCGST);
				$("#subTotal").val(qData.subTotal);
				$("#qIGST").val(qData.qIGST);
				$("#taxType").val(qData.taxType);
				$("#project").val(qData.project).trigger("change");
				$("#projectName").val(qData.projectName);

				getAddressDetails(qData.custId, qData.shippingHiddenId);
				CKEDITOR.instances['qutDescription'].setData(qData.qutDescription);
				CKEDITOR.instances['termCondition'].setData(qData.termCondition);

				$.ajax({
					type: "GET",
					url: "view-quotation-get-insertedid",
					success: function(response) {
						if (response.message === "success") {
							$("#quotationId").val(response.body[0].key);
						}
					},
					error: function(e) {
						console.error("Error fetching inserted ID:", e);
					}
				});

				let newDate = changeDateFormat((new Date()).toISOString().split('T')[0]);
				$("#quotationDate").val(newDate);

				$("#doctbodyData").empty();
				let documentList = qData.documentList;

				if (documentList && documentList.length) {
					for (var i = 0; i < documentList.length; i++) {

						let cls = 'ti-plus';

						if (documentList[i].fileName) {
							cls = 'ti-pencil';
						}

						let a = '<div class="form-group d-flex"><div class="">' +
							'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
							'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
							'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
							'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
							'<div id="validationDiv"></div></div>';

						var tbl = '<tr>'
							+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
							+ '<td>' + a + '</td>'
							+ '</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}
			}
		})
	} else {
		/*  $("#alert").modal('show');
		 document.getElementById("textId").innerHTML = "Please Select At Least One Record!"; */
	}
}



function cancelItemModel() {
	$('#addItemModel').modal('hide');
}

function selectCheckBoxx(id, lvl) {
	$(".benefitChk").prop("checked", false);
	$("#ccCheck_" + id).prop("checked", true);
	$("#tempCategoryId").val(id);
	$("#prLevelId").val(lvl);

}

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgemp').attr('src', '');
	$('#imgemp').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-quotation-upload-fileprofuct",
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

function check1(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	$("#" + fieldId).val(tempVal);
}



function cancelModalBtn() {
	$('#delete').modal('hide');

}

function cancelChangeAddressModel() {
	$('#changeAddressModel').modal('hide');
}

function selcetOption(addressId) {
	$.ajax({
		type: "GET",
		url: "view-quotation-shipping-dataedit?addressId=" + addressId,
		success: function(response) {
			if (response.code == "success") {
				$('#changeAddressModel').modal('hide');
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.ShippingAddress[0];

				var len = allData.length;
				$("#shippingHiddenId").val(addressId);
				$("#con1").val(allData.countryName);
				$("#bstate1").html(allData.stateName);
				$("#bcity1").html(allData.city);
				$("#bstreet11").html(allData.street1);
				$("#bstreet21").html(allData.street2);
				$("#bzipcode1").html(allData.zipCode);
				$("#bphone1").html(allData.phone);
				$("#bfax1").html(allData.fax);


			}
		},
		error: function(e) { }
	});


}

function deleteProduct() {

	$('#deleteProductModel').modal('show');
}

function cancelProductModelOnclick() {

	$('#deleteProductModel').modal('hide');
}



function vendorNameUpperr() {
	var text = $("#companyName").val();
	var result = text.toUpperCase();
	$("#companyName").val(result);
}

function productNameUpper() {
	var text = $("#productName").val();
	var result = text.toUpperCase();
	$("#productName").val(result);
}

function onQuickFilterChangedd() {
	gridOptionsProduct.api.setQuickFilter(document.getElementById('quickFilterr').value);
	var totalRowCount = gridOptionsProduct.api.getModel().getRowCount();

	$('#totalReq').find('span').html(gridOptionsProduct.api.getModel().getRowCount());
}

function cancelBarr() {
	var id = document.getElementById("closeKeyy");
	id.style.display = "block";

	if ($('#quickFilterr').val() == null || $('#quickFilterr').val() == "") {
		id.style.display = "none";
	}
}

function viewImage(id) {
	window.open("/document/document/" + id, '_blank');
}

/* save document file */
function saveFile2(event) {
	var uFile = $(uploadDocc_0)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDivv_0").html("");


	if (extension[1] === "jpg" || extension[1] === "png" || extension[1] === "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] === "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon'></i></a>";
	} else if (extension[1] === "xls" || extension[1] === "xlsx" || extension[1] === "csv") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] === "doc" || extension[1] === "docx" || extension[1] === "dox") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}
	$("#uploadedBillDivv_0").html(LightImg);
	$("#imageNamee_0").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');
	$.ajax({
		type: "POST",
		url: "view-quotation-item-image-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) { },
		error: function(e) {

		}
	});
}
var qtid = ''

function sendEmail() {
	$('.loader-modal').hide();
	$("modal-body").removeClass("overlay");
	$('#email-send').attr("disabled", false);
	$('#email-close').attr("disabled", false);
	$("#emailModal").modal('show');
	$("#customerNames").val(null);
	$('#customerCns').val(null);
	$("#customerMails").val(null);
	$("#customerCc").val(null);
	$("#customerBcc").val(null);
	$("#customerMsg").val(null);
	qtid = '';
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	$("#customerNames").val(selectedData.map(node => node.custName));
	qtid = selectedData.map(node => node.quotationId);
}

function cancelBtnMail() {
	$("#emailModal").modal('hide');
}
function clearMailField() {
	$('#multipleMailCustomerCc').val([]).trigger('chosen:updated');
	$('#multipleMailCustomerBcc').val([]).trigger('chosen:updated');
	$('#toHiddenIdCc').val('');
	$('#toHiddenIdBcc').val('');
	$('#customerMsg').val('');
	$('#customerCns').val('');
}
function addMail() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var quotationId = selectedData[0].quotationId;
	var customerNames = $("#customerNames").val();
	var customerCns = $('#customerCns').val();
	var customerMails = $("#customerMails").val();
	var customerCc = $("#toHiddenIdCc").val();
	var customerBcc = $("#toHiddenIdBcc").val();
	var customerMsg = $("#customerMsg").val();
	var organization = $("#sessionOrganization").val();
	var orgDivision = $("#sessionOrgDivision").val();
	var userId = $("#sessionId").val();
	var baseUrl = $('#baseURL').val();
	console.log("Base Url----->" + baseUrl);
	var url = baseUrl + "sales/view-quotation-pdf-downloads?quotationId=" + window.btoa(quotationId) + "&organization=" +
		window.btoa(organization) + "&orgDivision=" +
		window.btoa(orgDivision) + "&userId=" +
		window.btoa(userId);

	console.log("Base Url----->" + url);
	var validation = true;

	if (!validationUpdated("Customer Email Required", 'customerMails'))
		validation = false;

	if (validation) {
		$('.loader-modal').show();
		$("modal-body").addClass("overlay");
		$('#email-send').attr("disabled", true);
		$('#email-close').attr("disabled", true);
		$.ajax({
			type: "GET",
			url: "view-quotation-email-ajax?quotationId=" + quotationId + "&customerNames=" + customerNames + "&customerCns=" + customerCns + "&customerMails=" + customerMails + "&customerCc=" + customerCc + "&customerBcc=" + customerBcc + "&customerMsg=" + customerMsg + "&url=" + window.btoa(url),
			async: true,
			success: function(response) {
				if (response.message == "Success") {
					$('#emailModal').modal('hide');
					viewQuotation();
					toastr.success('Email Send Successfully');
					nextTab('quotationInfLiId');
					clearMailField();
					$('.loader-modal').hide();
				} else {
					toastr.error('Invalid Email Addresses');
					$('.loader-modal').hide();
				}

			},
			error: function(data) { }
		});
	}
}


//Pagination.
var pages;

function createPagination(pages, page) {
	pno = page;
	if ($("#totalPageno").val() == '') {
		var pages = 20;
	} else {
		var pages = $("#totalPageno").val();
		$("#currentPageno").val(page);
	}

	var str = '<ul>';
	var active;
	var pageCutLow = page - 1;
	var pageCutHigh = page + 1;
	// Show the Previous button only if you are on a page other than the first
	if (page > 1) {
		str += '<li class="page-item previous no"><a onclick="createPagination(pages, ' + (page - 1) + ')">Previous</a></li>';
	}
	// Show all the pagination elements if there are less than 6 pages total
	if (pages < 6) {
		for (let p = 1; p <= pages; p++) {
			active = page == p ? "active" : "no";
			str += '<li class="' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';
		}
	}
	// Use "..." to collapse pages outside of a certain range
	else {
		// Show the very first page followed by a "..." at the beginning of the
		// pagination section (after the Previous button)
		if (page > 2) {
			str += '<li class="no page-item"><a onclick="createPagination(pages, 1)">1</a></li>';
			if (page > 3) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page - 2) + ')">...</a></li>';
			}
		}
		// Determine how many pages to show after the current page index
		if (page === 1) {
			pageCutHigh += 2;
		} else if (page === 2) {
			pageCutHigh += 1;
		}
		// Determine how many pages to show before the current page index
		if (page === pages) {
			pageCutLow -= 2;
		} else if (page === pages - 1) {
			pageCutLow -= 1;
		}
		// Output the indexes for pages that fall inside the range of pageCutLow
		// and pageCutHigh
		for (let p = pageCutLow; p <= pageCutHigh; p++) {
			if (p === 0) {
				p += 1;
			}
			if (p > pages) {
				continue
			}

			active = page == p ? "active" : "no";
			str += '<li class="page-item ' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';

		}
		// Show the very last page preceded by a "..." at the end of the pagination
		// section (before the Next button)
		if (page < pages - 1) {
			if (page < pages - 2) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page + 2) + ')">...</a></li>';
			}
			str += '<li class="page-item no"><a onclick="createPagination(pages, pages)">' + pages + '</a></li>';
		}
	}
	// Show the Next button only if you are on a page other than the last
	if (page < pages) {
		str += '<li class="page-item next no"><a onclick="createPagination(pages, ' + (page + 1) + ')">Next</a></li>';
		changePagination(page);
	} else if (page <= pages) {
		//str += '<li class="page-item next no"><a onclick="createPagination(pages, '+(page+1)+')">Next</a></li>';
		changePagination(page);
	}
	str += '</ul>';
	// Return the pagination string to be outputted in the pug templates
	document.getElementById('pagination').innerHTML = str;
	return str;

}

/* New Script Added By Manoj */




function nextTab(id) {
	if (id === 'shippingAddressId') {
		var custName = $("#custName").val();
		if (custName == null || custName.trim() == "") {
			nextTab('quotationInfLiId');
			toastr.error('Customer Name Required');
			return false;
		}
	}
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}

var columnDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

},
{
	headerName: "Quotation Id",
	field: "quotationId",
	pinned: 'left',
	width: 150,
}, {
	headerName: 'Customer Name',
	field: "custName",
	width: 200,
}, {
	headerName: 'Amount',
	field: "totalAmount",
	type: 'rightAligned',
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Approve Status',
	field: "approveStatus",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.approveStatus == "Approved") {
			return '<div style="color:#0642f5">' + params.data.approveStatus + '</div>';
		} else if (params.data.approveStatus == "Revised") {
			return '<div style="color:#bf05ff">' + params.data.approveStatus + '</div>';
		} else {
			return '<div style="color:#a9a9a9">' + params.data.approveStatus + '</div>';
		}
	}
}, {
	headerName: 'Subject',
	field: "subject",
	width: 200,
}, {
	headerName: 'Valid Up To Date',
	field: "qutValidDate",
	width: 160,
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "PDF",
	cellStyle: {
		textAlign: 'center'
	},
	width: 100,
	cellRenderer: function(params) {
		if (params.data.quotationId) {
			return '<a id="registerId" onclick="qoutationPdfDownload(\''
				+ params.data.quotationId + '\')" href="javascript:void(0)"><i class="bi bi-cloud-download"> Download PDF</i></a>';
		} else {
			return '<a>N/A</a>';
		}
	},
}, {
	headerName: 'PO Status',
	field: "poStatus",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.poStatus == "Generated") {
			return '<div style="color:#0642f5">' + params.data.poStatus + '</div>';
		} else {
			return '<div style="color:#a9a9a9">' + params.data.poStatus + '</div>';
		}
	}

}, {
	headerName: 'PO Id',
	field: "poNo",
	width: 120,
}, {
	headerName: 'PO Date',
	field: "poDate",
	width: 120,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: 'Reference Id',
	field: "reference",
	width: 135,
}, {
	headerName: 'Created By',
	field: "createdBy",
	width: 150,
}, {
	headerName: 'Quotation Date',
	field: "quotationDate",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	}

}

];

var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',

	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	},

	onSelectionChanged: rowSelect,
	/*onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 800);
	}*/
};



var approveStatus = "";

function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();

	var selectedData = selectedNodes.map(node => node.data);
	$("#approveStatus").val('');
	if (selectedData.length > 0) {
		var quotationId = selectedData[0].quotationId;
		var approveStatus = selectedData[0].approveStatus;
		var poStatus = selectedData[0].poStatus;
		var cName = selectedData[0].custName;

		$('#approveStatus').val(approveStatus);

		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3").text(cName);

		editQuotation(quotationId);
		getQuotationPreviewDtls();

		$('#pdfButton,#addBtnId').removeClass('d-none');
		$('#copyBtn').removeClass('d-none');

		if (approveStatus === "Approved") {
			if (poStatus === 'Generated') {
				$('#revisionBtn').addClass('d-none');
			} else {
				$('#revisionBtn').removeClass('d-none');
			}
			$('#deleteQuot').addClass('d-none');
			$('#approveBtn').addClass('d-none');
			$('#mailId').removeClass('d-none');
			$('#mail').removeClass('d-none');
			$('#quotationSaveBtn').addClass('d-none');
			$('#editQuotation').addClass('d-none');
		} else if (approveStatus === "Revised") {
			$('#revisionBtn').addClass('d-none');
			$('#deleteQuot').addClass('d-none');
			$('#approveBtn').addClass('d-none');
			$('#mailId').addClass('d-none');
			$('#mail').addClass('d-none');
			$('#quotationSaveBtn').addClass('d-none');
			$('#editQuotation').addClass('d-none');
		} else {
			const exists = accessedRole.some(item => roleArray.includes(item));
			if (exists) {
				$('#approveBtn').removeClass('d-none');
			}
			$('#mailId,#revisionBtn').addClass('d-none');
			$('#mail').addClass('d-none');
			$('#quotationSaveBtn').removeClass('d-none');
			$('#editQuotation').removeClass('d-none');
			$('#deleteQuot').removeClass('d-none');
		}
		$("#copyQout,#downloadPDF").removeAttr("disabled");
		$("#previewLiId").removeClass("d-none");
	} else {
		$("#previewLiId,#addBtnId").addClass("d-none");
		$("#copyQout,#downloadPDF").attr("disabled", true);
		$('#pdfButton').addClass('d-none');
		$('#copyBtn').addClass('d-none');
		$('#approveBtn').addClass('d-none');
		$('#revisionBtn').addClass('d-none');
		$('#mailId').addClass('d-none');
		$('#mail').addClass('d-none');
		$('#quotationSaveBtn').removeClass('d-none');
		$('#editQuotation').addClass('d-none');
		$('#deleteQuot').addClass('d-none');
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#approveStatus").text('');
		add();
	}
}



const columnDefsa = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true

},
{
	headerName: "Address Id",
	field: "shippingId",
	hide: true,
},

{
	headerName: "Country",
	field: "country",
	width: 200,
	hide: true,
}, {
	headerName: "Country",
	field: "countryName",
	width: 200,
	flex: 1
}, {
	headerName: "State",
	field: "state",
	width: 200,
	hide: true,

}, {
	headerName: "State",
	field: "stateName",
	width: 200,
	flex: 1
}, {
	headerName: "City",
	field: "city",
	width: 200,
	flex: 1
}, {
	headerName: "Street",
	field: "street1",
	width: 200,
	flex: 1
}, {
	headerName: "Street",
	field: "street2",
	width: 200,
	flex: 1
}, {
	headerName: "Zip Code",
	field: "zipcode",
	width: 200,
	flex: 1
}, {
	headerName: "Phone",
	field: "phone",
	width: 200,
	flex: 1
}, {
	headerName: "Fax",
	field: "fax",
	cellStyle: {
		textAlign: 'left'
	},
	flex: 1
}, {
	headerName: "GSTIN",
	field: "gstIn",
	cellStyle: {
		textAlign: 'left'
	},
	flex: 1
}, {
	headerName: "Default Address",
	field: "defaultStatus",
	width: 200,
	cellStyle: {
		textAlign: 'center'
	},
	hide: true,
	cellRenderer: function(params) {
		if (params.data.defaultStatus == "Yes") {
			return '<div style="color:#0642f5">' + params.data.defaultStatus + '</div>';
		} else {
			return '<div style="color:#a9a9a9">' + params.data.defaultStatus + '</div>';
		}
	}
}
];

const gridSAOptions = {
	columnDefs: columnDefsa,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectAddress,
	/* getRowNodeId : function(data) {
		return data.customerId;
	} */
};
var itemDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Sl",
	field: "slNo",
	width: 50,
}, {
	headerName: 'SKU Id',
	field: "itemId",
	width: 100,
 cellRenderer: function(params) {
        const sku = params.value || '';
        return `
            <div style="display:flex; align-items:center; justify-content:space-between;">
                <span>${sku}</span>
                <i class="fa-solid fa-copy" style="cursor:pointer; color:#007bff;" title="Copy SKU" onclick="navigator.clipboard.writeText('${sku}').then(() => toastr.success('Copied: ${sku}'))"></i>
            </div>
        `;
    }
}, {
	headerName: 'Product/SKU',
	field: "itemName",
	flex: 2
}, {
	headerName: 'HSN Code',
	field: "hsnCode",
	width: 120,
	hide: true,
}, {

	headerName: 'Size',
	field: "sizeInMM",
	width: 115,
	hide: true,
},
{

	headerName: 'Thickness In MM',
	field: "thicknessInMM",
	width: 150,
	hide: true,
},
{
	headerName: 'Unit',
	field: "unitName",
	flex: 1,
}, {
	headerName: 'Unit',
	field: "unit",
	hide: true,
},
{
	headerName: 'Quantity',
	field: "quantity",
	type: 'rightAligned',
	flex: 1,
	// valueFormatter : currencyFormatter
}, {
	headerName: 'Price',
	field: "unitPrice",
	type: 'rightAligned',
	flex: 1,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Amount',
	field: "lineTotal",
	type: 'rightAligned',
	flex: 1,
	valueFormatter: indianNumberFormatterWithDecimal,
	aggFunc: 'sum'
}, {
	headerName: 'GST Rate',
	field: "gstRate",
	type: 'rightAligned',
	width: 100,
	hide: true,
	//valueFormatter : currencyFormatter,
}, {
	headerName: 'CGST',
	field: "itemCgst",
	type: 'rightAligned',
	width: 100,
	hide: true,
	//valueFormatter : currencyFormatter
}, {
	headerName: 'SGST',
	field: "itemSgst",
	type: 'rightAligned',
	width: 100,
	hide: true,
	//valueFormatter : currencyFormatter
}, {
	headerName: 'IGST',
	field: "itemIgst",
	type: 'rightAligned',
	width: 100,
	hide: true,
	//valueFormatter : currencyFormatter
}, {
	headerName: 'Taxable Amount',
	field: "taxableAmt",
	type: 'rightAligned',
	width: 155,
	hide: true,
	//valueFormatter : currencyFormatter
}, {
	headerName: 'Description',
	field: "itemDesc",
	flex: 2,
	cellRenderer: params => params.value
}, {
	headerName: 'bomDetails',
	field: "bomDetails",
	hide: true
}, {
	headerName: 'Action',
	field: "unitName",
	flex: 3,
	cellRenderer: createSKUButtons
}, {
	headerName: 'Type',
	field: "type",
	flex: 1,
	hide: true
}
];
var bomitemDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',

},
{
	headerName: "Sl",
	field: "slNo",
	width: 50,
}, {
	headerName: 'Item Name',
	field: "itemName",
	flex: 4
}, {
	headerName: 'Unit',
	field: "unitName",
	flex: 1
}, {
	headerName: 'Quantity',
	field: "qty",
	flex: 1,
	editable: true,
	type: 'rightAligned'
}, {
	headerName: 'Unit Price',
	field: "unitPrice",
	type: 'rightAligned',
	flex: 1,
	editable: true,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Amount',
	field: "lineTotal",
	type: 'rightAligned',
	flex: 1,
	valueFormatter: indianNumberFormatterWithDecimal,
},
];

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
	getRowNodeId: function(data) {
		return data.slNo;
	}
};

var bomitemOptions = {
	columnDefs: bomitemDefs,
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
	onSelectionChanged: rowSelectBomItem,
	getRowNodeId: function(data) {
		return data.slNo;
	},
	onCellValueChanged: params => {
		if (params.column.colId === "unitPrice" || params.column.colId === "qty") {

			let price = params.data.unitPrice ? parseFloat(params.data.unitPrice?.toString()) : 0;
			let qty = params.data.qty ? parseFloat(params.data.qty?.toString()) : 0;
			console.log(price, '  ', qty)
			params.node.setDataValue("lineTotal", price * qty);

			let totalPrice = '0';
			bomitemOptions.api.forEachNode(a => {
				totalPrice = parseFloat(totalPrice?.toString()) + parseFloat(a.data.lineTotal?.toString() || '0');
				console.log(totalPrice)
			});
			$("#totalBomPrice").val(parseFloat(totalPrice)?.toFixed(2));
		}
	}
};

function createSKUButtons(params) {
	const div = document.createElement("div");
	div.className = "d-flex gap-1 task-btn-sec";


	if (params.data.level === 'L1') {
		const addButton = document.createElement("button");
		addButton.className = "btn max-btn br-m-btn";
		addButton.innerHTML = '<i class="fa-solid fa-square-plus " aria-hidden="true"></i>';
		addButton.setAttribute("title", "Add SKU");
		addButton.setAttribute("data-bs-toggle", "tooltip");
		addButton.setAttribute("data-bs-placement", "top");
		setTimeout(() => {
			new bootstrap.Tooltip(addButton);
		}, 100);
		addButton.onclick = function() {
			toggleSection1(params.data.itemId);
		};

		div.appendChild(addButton);
	}

	if (params.data.level === 'L2') {

		if (params.data.type !== 'Added') {
			const addButton = document.createElement("button");
			addButton.className = "btn max-btn br-m-btn";
			addButton.innerHTML = '<i class="fa-solid fa-square-plus " aria-hidden="true"></i>';
			addButton.setAttribute("title", "Add BOM");
			addButton.setAttribute("data-bs-toggle", "tooltip");
			addButton.setAttribute("data-bs-placement", "top");
			setTimeout(() => {
				new bootstrap.Tooltip(addButton);
			}, 100);
			addButton.onclick = function() {
				toggleSection2(params.data.parentItemId, params.data.itemId, params.data.slNo);
			};

			div.appendChild(addButton);
		}

		const editButton = document.createElement("button");
		editButton.className = "btn max-btn br-m-btn";
		editButton.innerHTML = '<i class="fa-solid fa-square-pen" aria-hidden="true"></i>';
		editButton.setAttribute("title", "Edit SKU");
		editButton.setAttribute("data-bs-toggle", "tooltip");
		editButton.setAttribute("data-bs-placement", "top");
		setTimeout(() => {
			new bootstrap.Tooltip(editButton);
		}, 100);
		editButton.onclick = function() {
			editItemDetailsSku(params.data.slNo);
		};

		const deleteButton = document.createElement("button");
		deleteButton.className = "btn max-btn br-m-btn";
		deleteButton.innerHTML = '<i class="fa-solid fa-trash"></i>';
		deleteButton.setAttribute("title", "Delete SKU");
		deleteButton.setAttribute("data-bs-toggle", "tooltip");
		deleteButton.setAttribute("data-bs-placement", "top");
		setTimeout(() => {
			new bootstrap.Tooltip(deleteButton);
		}, 100);
		deleteButton.onclick = function() {
			deleteProductOnclick(params.data.itemId);
		};


		div.appendChild(editButton);
		div.appendChild(deleteButton);
	}

	if (params.data.level === 'L3') {

		const editButton = document.createElement("button");
		editButton.className = "btn max-btn br-m-btn";
		editButton.innerHTML = '<i class="fa-solid fa-square-pen" aria-hidden="true"></i>';
		editButton.setAttribute("title", "Edit BOM");
		editButton.setAttribute("data-bs-toggle", "tooltip");
		editButton.setAttribute("data-bs-placement", "top");
		setTimeout(() => {
			new bootstrap.Tooltip(editButton);
		}, 100);
		editButton.onclick = function() {
			editItemDetailsBom(params.data.parentItemId, params.data.parentSkuId, params.data.slNo);
		};

		const deleteButton = document.createElement("button");
		deleteButton.className = "btn max-btn br-m-btn";
		deleteButton.innerHTML = '<i class="fa-solid fa-trash"></i>';
		deleteButton.setAttribute("title", "Delete BOM");
		deleteButton.setAttribute("data-bs-toggle", "tooltip");
		deleteButton.setAttribute("data-bs-placement", "top");
		setTimeout(() => {
			new bootstrap.Tooltip(deleteButton);
		}, 100);
		deleteButton.onclick = function() {
			deleteProductOnclickBom(params.data.slNo);
		};


		div.appendChild(editButton);
		div.appendChild(deleteButton);
	}
	return div;
}


function indianNumberFormatterWithDecimal(params) {

	if (params.data.level === 'L1') {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return null;
	} else {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return '0.00';
	}

	const value = parseFloat(params.value).toFixed(2); // Ensure two decimal places
	const parts = value.split("."); // Split integer and decimal part
	const integerPart = parts[0]; // Integer part
	const decimalPart = parts[1]; // Decimal part (always two digits)

	// Handle numbers below 1000 separately
	if (integerPart.length <= 3) {
		return integerPart + "." + decimalPart;
	}

	// Format for numbers >= 1000
	const lastThree = integerPart.substring(integerPart.length - 3);
	const otherNumbers = integerPart.substring(0, integerPart.length - 3);
	const formattedValue =
		(otherNumbers ? otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," : "") + lastThree;

	return formattedValue + "." + decimalPart; // Append decimal part
}


function rowSelectItem() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var selectedQuoteRows = gridOptions.api.getSelectedRows();
	var quoteRowCount = 0;
	selectedQuoteRows.forEach(function(selectedRow, index) {
		quoteRowCount = quoteRowCount + 1;
	});
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		var status = $("#approveStatus").val();
		if (status == "Approved" || status == "Revised") {
			$(".br-dis").prop("disabled", true);
		} else {
			if (selectedRows[0].level === 'L1') {
				$(".br-dis").prop("disabled", false);
			} else {
				$(".br-dis").prop("disabled", true);
			}
		}

		let bomDetails = selectedRows[0]?.bomDetails;

		if (bomDetails && bomDetails.length > 0) {
			bomitemOptions.api.setRowData(bomDetails);
		} else {
			getBomDetails(selectedRows[0].quotationId, selectedRows[0].sku);
		}

	} else {
		$(".br-dis").prop("disabled", true);
	}

}

function rowSelectBomItem() {
	var selectedRows = bomitemOptions.api.getSelectedRows();
	var selectedQuoteRows = gridOptions.api.getSelectedRows();
	var quoteRowCount = 0;
	selectedQuoteRows.forEach(function(selectedRow, index) {
		quoteRowCount = quoteRowCount + 1;
	});
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		var status = $("#approveStatus").val();
		if (status == "Approved" || status == "Revised") {
			$(".br-dis-b").prop("disabled", true);
		} else {
			$(".br-dis-b").prop("disabled", false);
		}
		$(".br-dis-bom").prop("disabled", false);
	} else {
		$(".br-dis-b").prop("disabled", true);
		$(".br-dis-bom").prop("disabled", true);
		resetBom();
	}

}

function getBomDetails(qid, sku, slno) {
	if (sku) {
		$.ajax({
			type: "GET",
			url: "view-quotation-get-bomdetails?id=" + qid + "&sku=" + sku,
			success: function(resp) {
				if (resp.code == "success") {
					if (resp.body && resp.body[0]) {
						let data = JSON.parse(resp.body);

						if (data && data.length > 0) {
							let totalprice = 0.0;
							data.forEach((e, index) => {
								e.slNo = index + 1;
								e.mainSkuId = sku;
								let lt = e.lineTotal ? parseFloat(e.lineTotal) : 0.0;
								totalprice = totalprice + lt;
							});
							bomitemOptions.api.setRowData(data);
							$("#totalBomPrice").val(totalprice?.toFixed(2));


						} else {
							bomitemOptions.api.setRowData([]);
						}
					} else {
						bomitemOptions.api.setRowData([]);
					}
				} else {
					bomitemOptions.api.setRowData([]);
				}
			},
			error: function(e) {
				console.log(e);
				bomitemOptions.api.setRowData([]);
			}
		});
	}
}

function rowSelectAddress() {
	var selectedRows = gridSAOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#selectAddress,#closeAddress').removeClass('d-none');

	} else {
		$('#selectAddress,#closeAddress').addClass('d-none');
	}
}

function getStateDataOnEditMul(cid, sid, stateId) {
	var country = $("#" + cid).val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#" + sid).empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#" + sid).append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#" + sid).append(option);
					}
					$("#" + sid).val(stateId);
				}
			},
			error: function(e) { }
		});
	} else {
		$("#" + sid).empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#" + sid).append(option);
	}
}

function getAddressDetails(custId, shipId) {
	$.ajax({
		type: "GET",
		url: "view-quotation-get-address?id=" + custId,
		async: false,
		success: function(response) {
			if (response.message === "Success") {
				var shippingDetails = JSON.parse(response.body.shippingDetails);

				var validShippingDetails = shippingDetails.filter(item => item.delFlag === 0);

				if (shipId === "null" || shipId === "" || shipId === null) {
					gridSAOptions.api.setRowData(validShippingDetails);

					var firstShippingAddress = validShippingDetails.length > 0 ? validShippingDetails[0] : null;
					if (firstShippingAddress) {
						$("#shippingHiddenId").val(firstShippingAddress.shippingId || "");
						$("#country1").val(firstShippingAddress.country || "").attr("disabled", true);
						$("#states1").val(firstShippingAddress.state || "").attr("disabled", true);
						$("#city1").val(firstShippingAddress.city || "").attr("disabled", true);
						$("#street11").val(firstShippingAddress.street1 || "").attr("disabled", true);
						$("#street21").val(firstShippingAddress.street2 || "").attr("disabled", true);
						$("#zipCode1").val(firstShippingAddress.zipcode || "").attr("disabled", true);
						$("#phone1").val(firstShippingAddress.phone || "").attr("disabled", true);
						$("#fax1").val(firstShippingAddress.fax || "").attr("disabled", true);
						$("#gstIn1").val(firstShippingAddress.gstIn || "").attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', firstShippingAddress.state);
					} else {
						$("#shippingHiddenId").val("").attr("disabled", true);
						$("#country1").val("").attr("disabled", true);
						$("#states1").val("").attr("disabled", true);
						$("#city1").val("").attr("disabled", true);
						$("#street11").val("").attr("disabled", true);
						$("#street21").val("").attr("disabled", true);
						$("#zipCode1").val("").attr("disabled", true);
						$("#phone1").val("").attr("disabled", true);
						$("#fax1").val("").attr("disabled", true);
						$("#gstIn1").val("").attr("disabled", true);
					}
				} else {
					gridSAOptions.api.setRowData(validShippingDetails);
					var selectedShippingAddress = validShippingDetails.find(item => item.shippingId === shipId);
					if (selectedShippingAddress) {
						$("#shippingHiddenId").val(selectedShippingAddress.shippingId);
						$("#country1").val(selectedShippingAddress.country).attr("disabled", true);
						$("#states1").val(selectedShippingAddress.state).attr("disabled", true);
						$("#city1").val(selectedShippingAddress.city).attr("disabled", true);
						$("#street11").val(selectedShippingAddress.street1).attr("disabled", true);
						$("#street21").val(selectedShippingAddress.street2).attr("disabled", true);
						$("#zipCode1").val(selectedShippingAddress.zipcode).attr("disabled", true);
						$("#phone1").val(selectedShippingAddress.phone).attr("disabled", true);
						$("#fax1").val(selectedShippingAddress.fax).attr("disabled", true);
						$("#gstIn1").val(selectedShippingAddress.gstIn).attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', selectedShippingAddress.state);
					}
				}
			}
		}
	});
}

function closeTooltip(button) {
	let tooltip = bootstrap.Tooltip.getInstance(button);
	if (tooltip) {
		tooltip.hide();
	}
}

function changeAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		nextTab('quotationInfLiId');
		return;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	$('#mySAGrid').show();
	$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
	$('#changeAddress,#saveAddress').addClass('d-none');
	$('#closeAddress').removeClass('d-none');

}

function addAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		nextTab('quotationInfLiId');
		return;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	$('#mySAGrid').hide();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress,#addAddress').addClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').removeClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function closeAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec,#addAddress').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function saveAddress() {

	let fax = $("#fax2").val() ? $("#fax2").val() : "";
	let city = $("#city2").val();
	let phone = $("#phone2").val() ? $("#phone2").val() : "";
	let state = $("#states2").val();
	let stateName = $("#states2 option:selected").text();
	let country = $("#country2").val();
	let countryName = $("#country2 option:selected").text();
	let zipcode = $("#zipCode2").val();
	let street1 = $("#street12").val();
	let street2 = $("#street22").val() ? $("#street22").val() : "";
	let gstIn = $("#gstIn2").val();

	if (!country) {
		toastr.error('Country Required');
		return;
	}
	if (!state) {
		toastr.error('State Required');
		return;
	}
	if (!city) {
		toastr.error('City Required');
		return;
	}
	if (!street1) {
		toastr.error('Street 1 Required');
		return;
	}
	if (!zipcode) {
		toastr.error('Zipcode Required');
		return;
	}

	let obj = {
		"fax": fax,
		"city": city,
		"phone": phone,
		"state": state,
		"country": country,
		"delFlag": 0,
		"street1": street1,
		"street2": street2,
		"zipcode": zipcode,
		"stateName": stateName,
		"shippingId": generateUUID()?.toString(),
		"countryName": countryName,
		"gstIn": gstIn
	}



	if (zipcodeValid2) {

		let dataset = [];
		gridSAOptions.api.forEachNode(a => {
			dataset.push(a.data);
		})

		dataset.push(obj);

		let key = $("#custId").val();
		let code = JSON.stringify(dataset);

		let o = { key, code };

		$('.loader').show();
		$('body').addClass('overlay');

		$.ajax({
			type: "POST",
			url: "view-quotation-add-shipping-address",
			contentType: "application/json",
			data: JSON.stringify(o),
			success: function(resp) {
				$('.loader').hide();
				$('body').removeClass('overlay');
				if (resp.code === 'success') {
					toastr.success('Shipping address saved successfully');
					if (resp.body) {
						gridSAOptions.api.setRowData([]);
						gridSAOptions.api.setRowData(JSON.parse(resp.body));

						$('#mySAGrid').show();
						$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
						$('#saveAddress,#closeAddress').addClass('d-none');
						$('#addAddress,#changeAddress').removeClass('d-none');
						$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
					}

				} else {
					toastr.error(resp.message);
				}
			},
			error: data => {
				console.log(data)
				$('.loader').hide();
				$('body').removeClass('overlay');
				toastr.error('Something went wrong');
			}
		})
	} else {
		toastr.error('Zipcode is not valid');
		return;
	}
}

function selectAddress() {
	$('#changeAddress').removeClass('d-none');
	$('#shippingAddressSec').removeClass('d-none');
	//$('#selectAddress').addClass('d-none');
	$('#mySAGrid').hide();
	let selectedData = gridSAOptions.api.getSelectedRows();
	$("#shippingHiddenId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).attr("disabled", true);
	$("#states1").val(selectedData[0].state).attr("disabled", true);
	$("#city1").val(selectedData[0].city).attr("disabled", true);
	$("#street11").val(selectedData[0].street1).attr("disabled", true);
	$("#street21").val(selectedData[0].street2).attr("disabled", true);
	$("#zipCode1").val(selectedData[0].zipcode).attr("disabled", true);
	$("#phone1").val(selectedData[0].phone).attr("disabled", true);
	$("#fax1").val(selectedData[0].fax).attr("disabled", true);
	$("#gstIn1").val(selectedData[0].gstIn).attr("disabled", true);
	getStateDataOnEditMul('country1', 'states1', selectedData[0].state);
	gridSAOptions.api.deselectAll();
}

var zipcodeValid2;
function zipcodeVal2() {

	var zipcode = $('#zipCode2').val();

	var zipcodeid = /^\d{6}(-\d{6})?$/;
	var zipcodeid1 = /^\d{6}(-\d{5})?$/;
	if (zipcode != '') {
		if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {

			$("#error152").hide();
			zipcodeValid2 = true;
			return true;
		} else {
			$("#error152").show();
			$("#error152").html("Please enter a valid Zip Code No.");
			zipcodeValid2 = false;
			return false;
		}

	} else {
		$("#error152").hide();
		zipcodeValid2 = true;
		return true;
	}

}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function getStateDetails2() {

	var cname = $('#country2').val();

	$("#states2").empty();
	$("#states2").append('<option value="">Select</option>');

	if (cname) {
		$.ajax({
			type: "GET",
			url: "view-quotation-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states2").append(option);
					}
				}
			},
			error: function(e) { }
		});
	}
}

function changePagination(page) {
	$('.loader').show();
	pno = page;
	var pageno = page;
	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();
	agGrid.simpleHttpRequest({
		url: "view-quotation-through-ajax?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewQuotation;
		/* var len = allData.length;
		$('#totalItem').find('span').html(len); */
		gridOptions.api.setRowData(allData);

		var totalRowCount = gridOptions.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);

		if (allData.length > 0) {
			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;

		}


	});
}



var pno;

function viewQuotation() {
	var pages;
	var pageno = pno;
	
	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-quotation-through-ajax?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewQuotation;

		var totalRowCount = gridOptions.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);

		if (allData && allData.length > 0) {
			gridOptions.api.setRowData(allData);
			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;

			var firstRow = gridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				gridOptions.api.selectNode(firstRow, true);
			}

			bomitemOptions.api.setRowData([]);

		} else {
			gridOptions.api.setRowData([]);
			setTimeout(() => {
				addQuot();
			}, 1000);

		}
		// createPagination(pages, pageno);

	});
}

function editQuotation(id) {
	$('#draftButton').addClass('d-none');
	Cancel();
	nextTab('quotationInfLiId');
	$(".loader").show();
	setTimeout(function() {
		agGrid.simpleHttpRequest({
			url: "view-quotation-edit-new?id=" + id,
		}).then(
			function(data) {
				$('.loader').hide();
				$('#customerNames').val(data[0].custName);
				$('#customerIds').val(data[0].custId);

				let itemDetails = data[0]?.itemDetails;

				if (itemDetails) {
					let safeJson = itemDetails.replace(/[\u0000-\u001F]/g, "");
					let parsedData = JSON.parse(safeJson)
					itemOptions.api.setRowData(parsedData);
				} else {
					itemOptions.api.setRowData([]);
				}


				$("#quotationReference").html(data[0].reference);
				$("#quotationReference").val(data[0].reference);
				$("#quotationId").val(data[0].quotationId);
				$("#qutNo").val(data[0].quotationId);

				$("#version").html(data[0].version);

				$("#quotationDate").val(data[0].quotationDate);
				$("#qutValidDate").val(data[0].qutValidDate);
				$("#endCustName").val(data[0].endCustName);
				$("#subject").val(data[0].subject);
				//$("#orderType").val(data[0].orderType);
				$("#custName").val(data[0].custName).trigger("change");
				$("#custId").val(data[0].custId);
				$("#grandTotal").val(data[0].grandTotal);
				$("#qSGST").val(data[0].qSGST);
				$("#qCGST").val(data[0].qCGST);
				$("#subTotal").val(data[0].subTotal);
				$("#qIGST").val(data[0].qIGST);
				$("#taxType").val(data[0].taxType);
				$("#quotType").val(data[0].quotType);
				$("#shippingHiddenId").val(data[0].shippingHiddenId);
				$("#project").val(data[0].project).trigger("change");
				$("#projectName").val(data[0].projectName);

				/* if(data[0].quotType=="QTM0001"){
					$("#main_content1").hide();
				}else{
					$("#main_content1").show();
				} */
				//getScopeMatrix(data[0].quotType);
				getAddressDetails(data[0].custId, data[0].shippingHiddenId);
				$("#version").html(data[0].version);
				$("#customerMails").val('');
				$("#multipleMailCustomerCc_chosen").prop("disabled", false).trigger('chosen:updated');
				$("#multipleMailCustomerBcc_chosen").prop("disabled", false).trigger('chosen:updated');
				$('#multipleMailCustomerCc option, #multipleMailCustomerBcc option').prop('disabled', false);
				$("#customerMails").val(data[0].customerMails);

				setTimeout(() => {
					CKEDITOR.instances['qutDescription'].setData(data[0].qutDescription);
					CKEDITOR.instances['termCondition'].setData(data[0].termCondition);
					disableFields();
				}, 1000);

				//var lastVersion=(data[0].lastVersion);
				//var version=(data[0].version);

				if (approveStatus == 'Approved' || approveStatus == "Revised") {
					$("#save").hide();
					$("#newItem").hide();
					$("#saveTableData1").hide();
					$("#saveAttachmentBtn").hide();
				} else {
					$("#save").show();
					$("#newItem").show();
					$("#saveTableData1").show();
					$("#saveAttachmentBtn").show();
				}
				$("#doctbodyData").empty();

				if (data[0].documentList != null && data[0].documentList != "") {

					for (var i = 0; i < data[0]?.documentList.length; i++) {
						var documentList = data[0]?.documentList;
						let cls = 'ti-plus';

						if (documentList[i].fileName) {
							cls = 'ti-pencil';
						}
						let a = '<div class="form-group d-flex"><div class="">' +
							'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
							'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
							'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
							'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documentList[i].action + '</div></div>' +
							'<div id="validationDiv"></div></div>';

						var tbl = '<tr>' +
							'<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
							'<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>' +
							'<td><div class="form-group"> <input type="text" value="' + data[0].documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>' +
							'<td> ' + a + ' </td>' +
							'</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}


			});
	}, 3000);

}

function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;

	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if (cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});

}

function toggleSection() {
	var custName = $("#custName").val();
	if (custName == null || custName.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add Product');
		return;
	}

	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#sku").val('');
	$("#editProduct").val('').attr("disabled", false);
	$("#itemId").val('').attr("disabled", false);
	$("#itemName").val('').attr("disabled", false);
	$("#sizeInMM").val('').attr("disabled", false);
	$("#thicknessInMM").val('').attr("disabled", false);
	$("#unit").val('').attr("disabled", false);
	$("#quantity").val('').attr("disabled", false);
	$("#unitPrice").val('').attr("disabled", false);
	$("#gstRate").val('').attr("disabled", false);
	$("#lineTotal").val('').attr("disabled", false);
	/*if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData("");
	}*/

	bomitemOptions.api.setRowData([]);
}

function toggleSection1(itemId = '', skuId = '', t = '') {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add SKU');
		return;
	}

	getSKUDetails(itemId, skuId);

	$(".br-m-btn").hide();
	$(".br-s-btn-p").show();
	$("#skuItemName").val(itemId);
	$("#editProductSKU").val('').attr("disabled", false);
	$("#skuName").val('').trigger('change').attr("disabled", false);
	$("#unit").val('').attr("disabled", false);
	$("#quantity").val('').attr("disabled", false);
	$("#unitPrice").val('').attr("disabled", false);
	$("#lineTotal").val('').attr("disabled", false);
	// $("#skuDesc").val('').attr("disabled", false);
	if (t === '' && CKEDITOR.instances['skuDesc']) {
		CKEDITOR.instances['skuDesc'].setData("");
	}
	bomitemOptions.api.setRowData([]);
}

function toggleSection2(itemId, skuId, slNo, t = '') {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}
	
	if (t === '' && CKEDITOR.instances['bomDescription']) {
		CKEDITOR.instances['bomDescription'].setData("");
	}

	var selectedRows = gridOptions.api.getSelectedRows();

	let quotId = null;

	if (selectedRows && selectedRows.length > 0) {
		quotId = selectedRows[0].quotationId;
	}

	$("#totalBomPrice").val();

	bomitemOptions.api.setRowData([]);
	if (quotId) {
		const rowNode = itemOptions.api.getRowNode(slNo);
		if (rowNode) {
			let data = rowNode.data.bomDetails;
			bomitemOptions.api.setRowData(data);
			let totalPrice = '0';
			if (data && data.length > 0) {
				data.forEach(d => {
					let lineTotal = d.lineTotal ? d.lineTotal?.toString() : '0';
					totalPrice = parseFloat(totalPrice) + parseFloat(lineTotal);
				});
				$("#totalBomPrice").val(totalPrice?.toFixed(2));
			} else {
				$("#totalBomPrice").val('0.00');
				getBomDetails(quotId, skuId, slNo);
			}

		}
	} else {
		getBomDetails(quotId, skuId, slNo);
	}


	$("#bomDetailsDivId").removeClass("d-none");
	$(".br-m-btn,.br-m-btn-b").hide();
	$(".br-s-btn-b").show();

	$("#editProductBom,#bomItemId,#bomSkuId,#bomDescription").val('');
	$("#bomItemId").val(itemId);
	$("#bomSkuId").val(skuId);

	$("#prodSkuDtlsId").addClass("d-none");
}

function toggleSectionBom(t = '') {

	var custName = $("#custName").val();
	if (custName == null || custName.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	$(".br-m-btn-b").hide();
	$(".br-s-btn-b").show();

	$("#itemNameBom").val('').trigger('change');
	$("#bomDescription,#editProductBom,#bomItemId,#bomSkuId,#totalBomPrice").val('');
	
	if (t === '' && CKEDITOR.instances['bomDescription']) {
		CKEDITOR.instances['bomDescription'].setData("");
	}
}

function toggleSectionNewBom() {

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	var custName = $("#custName").val();
	if (custName == null || custName.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Customer Name Required');
		return false;
	}

	$('.br-s-btn-bom').show();
	$('.br-s-btn-b,.bomListView').hide();
    $('#bomFilterDiv').addClass('d-none');
	$("#bomItemName").val('').trigger('change');
	$("#bomUnit,#bomUnitId,#bomQty,#bomPrice,#bomAmount").val('');
}

function saveTableDataBom2() {
	let itemId = $("#bomItemName").val();
	let itemName = $("#bomItemName option:selected").text();
	let unitId = $("#bomUnitId").val();
	let unitName = $("#bomUnit").val();
	let qty = $("#bomQty").val();
	let unitPrice = $("#bomPrice").val();
	let lineTotal = $("#bomAmount").val();
	let mainSkuId = $("#bomSkuId").val();

	if (!itemId) {
		toastr.error('SKU Required');
		return false;
	}
	if (!qty) {
		toastr.error('Quantity Required');
		return false;
	}
	if (!unitPrice) {
		toastr.error('Unit Price Required');
		return false;
	}
	if (!lineTotal) {
		toastr.error('Amount Required');
		return false;
	}

	let dataset = [];
	let cnt = 0;
	let totalBomPrice = '0';
	bomitemOptions.api.forEachNode((a, index) => {
		dataset.push(a.data);
		cnt = index + 1;
		if (a.data?.lineTotal) {
			totalBomPrice = parseFloat(totalBomPrice) + parseFloat(a.data?.lineTotal);
		}
	});


	let selectedItemList = dataset.filter(f => f.itemId === itemId);

	if (selectedItemList && selectedItemList.length > 0) {
		toastr.error('This SKU already exists');
		return false;
	}

	let item = { itemId, itemName, unitId, unitName, qty, unitPrice, lineTotal, mainSkuId, slNo: cnt + 1 };
	dataset.push(item);

	totalBomPrice = parseFloat(totalBomPrice) + parseFloat(lineTotal);

	$("#totalBomPrice").val(totalBomPrice?.toFixed(2));

	bomitemOptions.api.setRowData(dataset);
	cancelItemDetailsBom2();

}

function cancelItemDetailsBom2() {
	$('.br-s-btn-bom').hide();
	$('.br-s-btn-b,.bomListView').show();

	$("#bomItemName").val('').trigger('change');
	$("#bomUnit,#bomUnitId,#bomQty,#bomPrice,#bomAmount").val('');
	$('#bomFilterDiv').removeClass('d-none');
}

function setDataDetails() {

	let unitId = $("#bomItemName option:selected").attr('data-unitid');
	let unit = $("#bomItemName option:selected").attr('data-unit');
	let price = $("#bomItemName option:selected").attr('data-price');

	$("#bomUnit").val(unit);
	$("#bomUnitId").val(unitId);
	$("#bomPrice").val(price);
	$("#bomAmount").val('0.00');
}

function getTotalAmount() {
	let price = $("#bomPrice").val() ? $("#bomPrice").val() : '0';
	let qty = $("#bomQty").val() ? $("#bomQty").val() : '0';

	let totalAmount = parseFloat(price) * parseFloat(qty);
	$("#bomAmount").val(totalAmount?.toFixed(2));
}

function cancelItemDetails() {
	itemOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#sku").val('');
	$("#editProduct").val(null);
	$("#itemId").val('');
	$("#itemName").val('').trigger('change');
	//$("#itemName").text('');
	$("#sizeInMM").val('');
	$("#thicknessInMM").val('');
	$("#unit").val('');
	$("#quantity").val('');
	$("#unitPrice").val('');
	$("#gstRate").val('');
	$("#lineTotal").val('');
	$("#itemDesc").val('');
	$("#hsnCode").val('');
	$("#productName").val('');
	$("#itemId").val('');
}
function cancelItemDetails1() {
	itemOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn-p").hide();

	$("#sku").val('');
	$("#editProductSKU").val(null);
	$("#skuName").val('').trigger('change');
	$("#sizeInMM").val('');
	$("#thicknessInMM").val('');
	$("#unit").val('');
	$("#quantity").val('');
	$("#unitPrice").val('');
	$("#gstRate").val('');
	$("#lineTotal").val('');
	$("#skuDesc").val('');
	$("#hsnCode").val('');
	$("#productName").val('');
	$("#skuItemName").val('');
}

function cancelItemDetailsBom() {
	bomitemOptions.api.deselectAll();
	$(".br-m-btn,.br-m-btn-b").show();
	$(".br-s-btn-b").hide();

	$("#itemNameBom").val('').trigger('change');
	$("#bomDescription,#editProductBom,#bomItemId,#bomSkuId,#totalBomPrice").val('');

	$("#bomDetailsDivId").addClass('d-none');
	$("#prodSkuDtlsId").removeClass("d-none");
}

function editItemDetails() {
	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t delete Product');
		return;
	}
	toggleSection();
	let selectedData = itemOptions.api.getSelectedRows();
	
	$("#editProduct").val(selectedData[0].slNo);
	$("#itemName").val(selectedData[0].itemId).select2();
	if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData(selectedData[0].itemDesc || "");
	}
}

function editItemDetailsSku(id) {
	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t edit SKU');
		return;
	}

	const rowNode = itemOptions.api.getRowNode(id);
	if (rowNode) {
		let data = rowNode.data;

		toggleSection1(data.parentItemId, data.itemId, 'E');
		$("#skuItemName").val(data.parentItemId);
		$("#editProductSKU").val(data.slNo);
		$("#unit").val(data.unit);
		$("#quantity").val(data.quantity);
		$("#unitPrice").val(data.unitPrice);
		$("#lineTotal").val(data.lineTotal);
		// $("#skuDesc").val(data.itemDesc);
		if (CKEDITOR.instances['skuDesc']) {
			CKEDITOR.instances['skuDesc'].setData(data.itemDesc || "");
		}
		$("#charNumSN span").text((data.itemDesc || "").length);

	}
}

function editItemDetailsBom(parentItemId, skuId, slNo) {
	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t edit BOM');
		return;
	}

	const rowNode = itemOptions.api.getRowNode(slNo);

	if (rowNode) {
		let data = rowNode.data;
		toggleSection2(parentItemId, skuId, slNo, 'E');
		$("#editProductBom").val(slNo);
		// $("#bomDescription").val(data.itemDesc);
		if (CKEDITOR.instances['bomDescription']) {
			CKEDITOR.instances['bomDescription'].setData(data.itemDesc || "");
		}
	}

}

function addQuot() {
	gridOptions.api.deselectAll();
	gridDraftOptions.api.deselectAll();
}

function add() {
	enableFields();
	$('.formValidation').remove();

	$("#country1,#states1,#city1,#street11,#street21,#zipCode1,#phone1,#fax1,#gstIn1").val('');
	gridSAOptions.api.setRowData([]);

	$("#quotationReference").html("");
	$("#version").html("");
	$("#quotationId").val("");
	$("#shippingHiddenId").val("");
	$("#qutName").val("");
	$("#quotationDate").val("");
	$("#custId").val("");
	$("#custName").val("");
	$("#reference").val("");
	$("#salesPerson").val("");
	$("#dealName").val("");
	$("#qutValidDate").val("");
	$("#endCustName").val("");
	$("#toDateCalendar").val("");
	$("#qutDescription").val("");
	$("#qutActive").val("");
	$("#qutCreatedBy").val("");
	$("#qutUpdatedOn").val("");
	$("#subTotal").val("");
	$("#grandTotal").val("");
	$("#slNo").val("");
	$("#sku").val("");
	$("#itemId").val("");
	$("#itemName").val("");
	$("#quantity").val("");
	$("#unitPrice").val("");
	$("#customerNames").val("");
	$("#customerIds").val("");
	$("#project").val("").trigger('change');
	$("#quotationReference").val("");
	$("#quotationReference").html("");
	// $(dropdown).empty();
	$("#quotType").val("");
	$("#custName").val("");
	$("#customerAddress").val("");
	$("#qutNo").val("");
	$("#draftId").val("");
	$("#approveStatus").val("");
	$('#mailId').addClass('d-none');
	$('#mail').addClass('d-none');
	$('#approveBtn,#editQuotation,#deleteDraft,#deleteQuot').addClass('d-none');
	$('#pdfButton').addClass('d-none');
	$('#copyBtn').addClass('d-none');
	//$('#shippingAddressId').addClass('d-none');
	//$('#shippingAddress').addClass('d-none');
	$('#revisionBtn').addClass('d-none');
	$('#quotationSaveBtn').removeClass('d-none');
	var sessionOrganization1 = $("#sessionOrganization1").val();
	itemOptions.api.setRowData();
	var subjectQuot = "Proposal for "
	$("#subject").val(subjectQuot);
	var customerNoteQuot = "<p>Dear Sir,</p><p>This refers to your meeting, and in line to our discussion, enclosed herewith our commercial proposal for&nbsp;</p>";
	var termConditionQuot = "<ul><li><strong>Deliver : At Site.</strong></li>" +
		"<li><strong>Payment Terms:</strong>&nbsp;90 days L.C. (45 days interest to our account and 45 days interest to the buyers account)</li>" +
		"<li><strong>Guarantee:</strong>&nbsp;Corporate Guarantee for 66 / 60 months.</li>" +
		"<li><strong>Inspection:</strong>&nbsp;is in our scope,&nbsp;<strong>however any support required for drawing approval shall work jointly.</strong></li>" +
		"<li><strong>Delivery:</strong>&nbsp;Delivery shall be start by 8-10 weeks. And shall closed within 3-5 months (monthly can be 100 units)</li>" +
		"<li><strong>Performance Guarantee:</strong>&nbsp;3 % BG and 7 % corporate Guarantee.</li>" +
		"</ul><p>Best Regards,<br /> For &amp; on behalf of Team&nbsp;<strong>AKANKSHA!!</strong></p>";

	CKEDITOR.instances.qutDescription.setData(customerNoteQuot);
	CKEDITOR.instances.termCondition.setData(termConditionQuot);
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#quotationDate").val(newDate);

	$.ajax({
		type: "GET",
		url: "view-quotation-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				$("#quotationId").val(response.body[0].key);
			}
		},
		error: function(e) { }
	});

	$("#doctbodyData").empty();
	/*var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
		'<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>' +
		'<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
		'<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>' +
		'<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
		'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>' +
		'</tr>';
	$("#doctbodyData").append(tbl);*/
	let i = 0;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
		+ '<td>' + a + '</td>'
		+ '</tr>';
	$("#doctbodyData").append(tbl);
	$('#draftButton').removeClass('d-none');
}

function getItemdetailsBySku() {
	var id = $("#skuName").val();
	if (id) {
		$.ajax({
			type: "GET",
			url: "view-quotation-get-item-bySku?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.itemDetails;
					if (allData != null) {
						$("#unit").val(allData.productUnit);
					} else {
						toastr.error("Something Went Wrong!");
						$("#skuName").val("").trigger('change');
						$("#unit").val("");
					}
				} else {
					toastr.error("Something Went Wrong!");
					$("#skuName").val("").trigger('change');
					$("#unit").val("");
				}
			},
			error: function(e) {
				console.log(e)
				toastr.error("Something Went Wrong!");
				$("#skuName").val("").trigger('change');
				$("#unit").val("");
			}
		});
	}
}

function getSKUDetails(itemId, skuId = '') {
	$("#skuName").empty();
	$("#skuName").append("<option value=''>Select</option>");
	if (itemId) {
		$.ajax({
			type: "GET",
			url: "view-quotation-get-sku-list?id=" + itemId,
			success: function(response) {
				if (response.code == "success") {
					if (response.body && response.body.length > 0) {
						let a = response.body;
						a.forEach(e => {
							$("#skuName").append("<option value='" + e.key + "'>" + e.name + "</option>");
						});
						$("#skuName").val(skuId).trigger('change');
					}
				}
			},
			error: function(e) {
				console.log(e);
			}
		});
	}
}

function viewDraft() {
	Cancel();
	$("#copyQout,#downloadPDF").attr("disabled", true);
	$('#myGridDraft').removeClass('d-none');
	$('#myGridDraft').removeClass('d-none');
	$('#myGrid').addClass('d-none');
	$('#approveBtn').addClass('d-none');
	$('#pdfButton').addClass('d-none');
	$('#copyBtn').addClass('d-none');
	$('#revisionBtn').addClass('d-none');
	$('#viewQuotBtn').removeClass('d-none');
	$('#viewDraftBtn').addClass('d-none');
	$('#deleteQuot').addClass('d-none');
	$('#deleteDraft').removeClass('d-none');
	$('#draftButton').removeClass('d-none');

	$("#quotSearchDiv").addClass("d-none");
	$("#quotDraftSearchDiv").removeClass("d-none");
	$("#approveStatus").val("");

	agGrid.simpleHttpRequest({
		url: "view-quotation-draft-ajax"
	}).then(function(data) {
		var len = data.length;

		$('#totalDraftList').find('span').html(len);
		gridDraftOptions.api.setRowData(data);

		if (data && data.length > 0) {
			gridDraftOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		} else {
			rowSelectDraft();
		}

		bomitemOptions.api.setRowData([]);
	});
}

function returnToQuotation() {
	Cancel();
	$('#myGridDraft').addClass('d-none');
	$('#myGrid').removeClass('d-none');
	$('#viewQuotBtn').addClass('d-none');
	$('#viewDraftBtn').removeClass('d-none');
	$('#deleteQuot').removeClass('d-none');
	$('#deleteDraft').addClass('d-none');
	const exists = accessedRole.some(item => roleArray.includes(item));
	if (exists) {
		$('#approveBtn').removeClass('d-none');
	}
	$('#pdfButton').removeClass('d-none');
	$('#copyBtn').removeClass('d-none');
	$('#revisionBtn').removeClass('d-none');

	$("#quotSearchDiv").removeClass("d-none");
	$("#quotDraftSearchDiv").addClass("d-none");
	viewQuotation();
	/*setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 500);*/
}

function disableFields() {
	let fieldIds = ["custName", "endCustName", "qutValidDate", "project", "subject"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	/* setTimeout(() => {
		console.log("Disabling dynamically added fields...");
		$("#doctbodyData").find("input, select, textarea, button").each(function () {
			$(this).prop("disabled", disable);
		});
	}, 300); */ // Increased delay to ensure elements are added

	// ✅ Ensure CKEditor Fields Are Ready Before Disabling
	// CKEDITOR.instances.qutDescription?.setReadOnly(true);
	// CKEDITOR.instances.termCondition?.setReadOnly(true);
	if (CKEDITOR.instances.qutDescription) {
	    CKEDITOR.instances.qutDescription.setReadOnly(true);
	} else {
	    CKEDITOR.on('instanceReady', function (evt) {
	        if (evt.editor.name === 'qutDescription') {
	            evt.editor.setReadOnly(true);
	        }
	    });
	}
	if (CKEDITOR.instances.termCondition) {
	    CKEDITOR.instances.termCondition.setReadOnly(true);
	} else {
	    CKEDITOR.on('instanceReady', function (evt) {
	        if (evt.editor.name === 'termCondition') {
	            evt.editor.setReadOnly(true);
	        }
	    });
	}
	
}

function enableFields() {
	let fieldIds = ["custName", "endCustName", "qutValidDate", "project", "subject"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});

	if (CKEDITOR?.instances?.qutDescription)
		CKEDITOR?.instances?.qutDescription?.setReadOnly(false);
	if (CKEDITOR?.instances?.termCondition)
		CKEDITOR?.instances?.termCondition?.setReadOnly(false);

}

function handleCustomerChange() {
	$('#shippingAddressId').removeClass('d-none');
	$('#shippingAddress').removeClass('d-none');
}

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged()
	}
}

function onQuickFilterChanged1() {
	gridDraftOptions.api
		.setQuickFilter(document.getElementById('quickFilter1').value);
	setTimeout(() => {
		if (gridDraftOptions.api) {
			gridDraftOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter1(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged1()
	}
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}
function resetBtn1() {
	$("#quickFilter1").val('');
	gridDraftOptions.api.setQuickFilter('');
	gridDraftOptions.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (gridDraftOptions.api) {
			gridDraftOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

$(document).ready(() => {
	document.getElementById("quantity").addEventListener("input", function(e) {
		this.value = this.value.replace(/[^0-9.]/g, '');
		if ((this.value.match(/\./g) || []).length > 1) {
			this.value = this.value.slice(0, -1);
		}
	});
	document.getElementById("unitPrice").addEventListener("input", function(e) {
		this.value = this.value.replace(/[^0-9.]/g, '');
		if ((this.value.match(/\./g) || []).length > 1) {
			this.value = this.value.slice(0, -1);
		}
	});
	document.getElementById("bomQty").addEventListener("input", function(e) {
		this.value = this.value.replace(/[^0-9.]/g, '');
		if ((this.value.match(/\./g) || []).length > 1) {
			this.value = this.value.slice(0, -1);
		}
	});
	document.getElementById("bomPrice").addEventListener("input", function(e) {
		this.value = this.value.replace(/[^0-9.]/g, '');
		if ((this.value.match(/\./g) || []).length > 1) {
			this.value = this.value.slice(0, -1);
		}
	});
})

function removeSpecialChars(input) {
	input.value = input.value.replace(/[^a-zA-Z0-9 ,./]/g, '');
}
function excelDownload() {
	var params = {
		fileName: 'Qoutation_List.csv', // Specify your custom filename here
	};
	gridOptions.api.exportDataAsCsv(params);
}
//
var pno = "";
function filterView() {
    var pages;
    var pageno = pno;

    var fDate = $("#fromDateQT").val();
    var tDate = $("#toDateQT").val();

	if (fDate && tDate) {
        var fromDateObj = new Date(fDate.split("-").reverse().join("-")); // assumes DD-MM-YYYY
        var toDateObj = new Date(tDate.split("-").reverse().join("-"));

        if (toDateObj < fromDateObj) {
            toastr.error("Please choose 'To Date' greater than or equal to 'From Date'");

            // Clear grid data if invalid date
            gridOptions.api.setRowData([]);
            $('#totalCandidate').find('span').html(0);
            $('#totalPageno').val(0);

            return; // Stop execution
        }
    }

    $(".loader").show();
    agGrid.simpleHttpRequest({
        url: "view-quotation-filter-data?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
    }).then(function(data) {
        $(".loader").hide();
        var jsonData = JSON.parse(data.body);
        var allData = jsonData.viewQuotation;

        // Check if data is valid
        if (allData && allData != 'null' && allData.length > 0) {
            gridOptions.api.setRowData(allData);

            var totalRowCount = gridOptions.api.getModel().getRowCount();
            $('#totalCandidate').find('span').html(totalRowCount);

            $('#totalPageno').val(allData[0].totalPageno);
            pages = allData[0].totalPageno;

            // Select the first row after data is set
            setTimeout(function() {
                const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
                if (firstRowNode) {
                    firstRowNode.setSelected(true);
                }
            }, 100);
        } else {
            gridOptions.api.setRowData([]);
            $('#totalCandidate').find('span').html(0);
            $('#totalPageno').val(0);
        }
    });
}
function onQuickFilterBom() {
	bomitemOptions.api.setQuickFilter(document.getElementById('quickFilterBom').value);
}
function resetBom() {
	$('#quickFilterBom').val("");
	onQuickFilterBom();
}