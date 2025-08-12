$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions1);
	gridOptions1.api.setRowData();
	$('#delete').attr('disabled', true);



	agGrid.simpleHttpRequest({
		url: "create-project-view"
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewProjectData;
		var len = allData.length;
		$('#totalPrj').find('span').html(len);
		gridOptions1.api.setRowData(allData);
		var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}
	})




	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
		+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div>'
		+ '<input type="hidden" id="editId_0" value="">'
		+ '<div id="dltImage_0" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
		+ '</tr>';
	$("#doctbodyData").append(tbl);


	$('#docTbl').on('click', '.rmv1', function() {
		openDeleteConfirm();
		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
	});

	let x = []
	gridOptions1.api.setRowData(x);


	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();

	//today = dd + '-' + mm + '-' + yyyy;

	today = dd + '-' + mm + '-' + yyyy;

	$('#creationID').val(today);

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		//format : dateFormat,
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#creationID').val($(this).val());
	})

	$('#creationID').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})


});

var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,

	},
	{
		headerName: "Project ID",
		field: "projectId",
		type: 'leftAligned',


	}, {
		headerName: "Project Name",
		field: "projectName",
		type: 'leftAligned',
		width: 250,

	}, {
		headerName: "Creation Date",
		field: "creationDate",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Location',
		field: "location",
		type: 'leftAligned',
		width: 230,
	}, {
		headerName: 'Country',
		field: "country2",
		type: 'leftAligned',
		width: 130,
		hide: true
	}, {
		headerName: "State",
		field: "stateid2",
		type: 'leftAligned',
		width: 150,
		hide: true
	}, {
		headerName: 'Pin',
		field: "pPin",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Project Incharge',
		field: "pIncharge",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Billing Name',
		field: "cName",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Address',
		field: "cAddress",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Country',
		field: "country",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing State',
		field: "stateid",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Pin',
		field: "cPin",
		width: 150,
		hide: true,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Email',
		field: "email",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Mobile',
		field: "mobile",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Remarks',
		field: "remark",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'status',
		field: "status",
		width: 150,
		type: 'leftAligned',

	}];
var gridOptions1 = {
	columnDefs: columnDefs,
	//rowData : rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChanged
};



var prjId = '';
var projectedId = '';
var projectedName = '';
var prjname = '';
function onSelectionChanged() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	for (var i = 0; i < selectedRows.length; i++) {
		prjId = prjId + selectedRows[i].projectId;
		projectedName = projectedName + selectedRows[i].projectName;
	}
	prjname = projectedName;
	projectedId = prjId;
	//callThrghProject(prjId);

	if (rowCount > 0) {
		editPage(prjId);
		$('#delete').attr('disabled', false);
		$("#reject").attr('disabled', false);
		$("#approve").attr('disabled', false);
		$("#add").attr('disabled', true);
		$("#createReq").attr('disabled', false);
		$("#createPo").attr('disabled', false);
		$('#projectId,#projectname,#creationID,#locationID,#stateID,#pinID,#pIncharge,#cname,#caddress,#cstate,#cPin,#email,#mobile,#remarks,#status,#projectType,#stateid2,#country2,#country,#stateid,#cityb,#street1,#street2,#gstNo,#shippingName,#shippingEmail,#shippingMobileNo,#shippingContact,#country1,#stateid1,#shippingCity,#shippingStreet1,#shippingStreet2,#shippingPin,#shippingGstNo',).prop('disabled', true);


	} else {
		$('#delete').attr('disabled', true);
		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
		$("#add").attr('disabled', false);
		$("#createReq").attr('disabled', true);
		$("#createPo").attr('disabled', true);
		$('#projectId,#projectname,#creationID,#locationID,#stateID,#pinID,#pIncharge,#cname,#caddress,#cstate,#cPin,#email,#mobile,#remarks,#status,#projectType,#stateid2,#country2,#country,#stateid,#cityb,#street1,#street2,#gstNo,#shippingName,#shippingEmail,#shippingMobileNo,#shippingContact,#country1,#stateid1,#shippingCity,#shippingStreet1,#shippingStreet2,#shippingPin,#shippingGstNo').prop('disabled', false);
		addId()
	}

	prjId = '';
	projectedName = '';

}


function editPage(id) {
	var editId = id.split(",");

	var projectId = editId[0];

	var shippingId = editId[1];

	$('#delete1').attr("disabled", true);


	$.ajax({
		type: "GET",
		url: "create-project-edit?id=" + projectId,
		success: function(response) {

			$("#projectId").val(response.body.projectId);
			$("#projectIdvalue").html(response.body.projectId);
			$("#projectname").val(response.body.projectName);
			$("#creationID").val(response.body.creationDate);
			$("#locationID").val(response.body.location);
			$("#country2").val(response.body.country2);
			$("#stateid2").val(response.body.stateid2);
			$("#pinID").val(response.body.pPin);
			$("#pIncharge").val(response.body.pIncharge);
			$("#cname").val(response.body.cName);
			$("#caddress").val(response.body.cAddress);
			$("#country").val(response.body.country);
			$("#stateid").val(response.body.stateid);
			$("#cityb").val(response.body.cityb);
			$("#street1b").val(response.body.street1b);
			$("#street2b").val(response.body.street2b);
			$("#cPin").val(response.body.cPin);
			$("#email").val(response.body.email);
			$("#mobile").val(response.body.mobile);
			$("#remarks").val(response.body.remark);
			$("#status").val(response.body.status);
			$("#street1").val(response.body.billingStreet1);
			$("#street2").val(response.body.billingStreet2);
			$("#gstNo").val(response.body.billingGstNo);
			$("#projectType").val(response.body.projectType);
			//alert(response.body.stateid1);
			//getStateDataOnEdit(response.body.stateid2);
			//getBillingStateDataOnEdit(response.body.stateid);
			//getShippingStateDataOnEdit(response.body.stateid1);
			//activityOptions.api.setRowData(response.body.shippingList);
			//gridOptionsProductSku.api.setRowData(response.body.productList);

			console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@", response.body);
			//addId();



			$("#doctbodyData").empty();
			var documentList = response.body.documentList;
			if (documentList != null
				&& documentList != "") {

				for (var i = 0; i < documentList.length; i++) {
					//console.log(JSON.stringify(data));

					//console.log('in for loop documentList========>>>>'+JSON.stringify(data[0].documentList[i].documnentName));
					var tbl = '<tr>'
						+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclss" id="docnoid_' + i + '"> </div></td>'
						+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-pencil" id="clickImg_' + i + '"></i> </label>'
						+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
						+ i
						+ '"name="userImage" onchange="saveMultiFile(event)" /> </div>'
						+ '</div> <input type="hidden" id="uploadHidden_' + i + '" value="' + documentList[i].fileName + '" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_' + i + '" align="center" class="uploadedBillCls"><div class="uploadicon position-l">'
						+ documentList[i].action
						+ '</div></div>'
						+ '<div id="imageName_' + i + '" class="imageName">'
						+ documentList[i].fileName
						+ '</div>'
						+ '<input type="hidden" id="editId_' + i + '" value="' + documentList[i].vendorRfqId + '">'
						+ '<div id="dltImage_' + i + '" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
						+ '</tr>';

					$("#doctbodyData").append(tbl);
				}
			} else {
				var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
					+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
					+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
					+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div>'
					+ '<input type="hidden" id="editId_0" value="">'
					+ '<div id="dltImage_0" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
					+ '</tr>';
				$("#doctbodyData").append(tbl);
			}
		}
	});
}


function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
		/* if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		} */
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				$("#messageParagraph").text("Please Choose a File ");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMore1()
	}
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
	if (extension[1] == "jpg" || extension[1] == "png"
		|| extension[1] == "jpeg") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx" || extension[1] == "csv") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l '> </div>";
	}
	//		var dltImg = "<i class='ti-close position-l rmv1' ></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	//		$("#dltImage_" + counter).html(dltImg);

	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}


function addMore1() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:first").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	$("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
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
		divInput.eq(4).attr('id', "uploadedBillDiv_" + i);
		divInput.eq(5).attr('id', "imageName_" + i);
		//divInput.eq(6).attr('id', "dltImage_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

}

function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}

//for closing modal box for delete ind product
function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}

function deletAttachmentRow() {
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	closeDeleteConfirm();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
			+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div>'
			+ '<input type="hidden" id="editId_0" value="">'
			+ '<div id="dltImage_0" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
			+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}


function addCreation() {
	if (validFormData()) {
		var datas = [];
		var imageValid = true;
		var uploadList = [];
		$("#doctbodyData > tr").each(function() {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var data = [];
			var x = [];
			if (fileName != '' && fileName != 'undefined' && fileName != null) {
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
				if ($("#projectId").val()) {
					fileName = $(this).find(".uploadHidCls").val();
				} else {
					x = [];
				}

			}
			uploadData = {};
			uploadData['projectId'] = $("#projectId").val();
			uploadData['documnentName'] = $(this).find(".docNoclss").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
			if ($(this).find(".docNoclss").val() != "" && $(this).find(".docNoclss").val() != 'null' && fileName != "" && fileName != "null") {
				uploadList.push(uploadData);
				console.log(uploadData)
			}
			console.log('DOC', uploadData)
		});

		setTimeout(function() {
			var obj = {};
			var item_data = [];
			var item_sku_data = [];

			/*activityOptions.api.forEachNode(function(rowNode, index) {
				console.log(rowNode)
				var item_obj = {};
				item_obj.shippingId = rowNode.data.shippingId;
				item_obj.shippingName = rowNode.data.shippingName;
				item_obj.shippingAddress = rowNode.data.shippingAddress;
				item_obj.country1 = rowNode.data.country1;
				item_obj.stateid1 = rowNode.data.stateid1;
				item_obj.shippingCity = rowNode.data.shippingCity;
				item_obj.shippingStreet1 = rowNode.data.shippingStreet1;
				item_obj.shippingStreet2 = rowNode.data.shippingStreet2;
				item_obj.shippingPin = rowNode.data.shippingPin;
				item_obj.shippingEmail = rowNode.data.shippingEmail;
				item_obj.shippingMobileNo = rowNode.data.shippingMobileNo;
				item_obj.shippingGstNo = rowNode.data.shippingGstNo;
				item_obj.shippingContact = rowNode.data.shippingContact;

				item_data.push(item_obj);

			});*/


			/*gridOptionsProductSku.api.forEachNode(function(rowNode, index) {
				item_sku_data.push(rowNode.data);
			});
			console.log('sku', item_sku_data)

*/
			obj.projectId = $("#projectId").val();
			obj.projectName = $("#projectname").val();
			obj.creationDate = $("#creationID").val();
			obj.location = $("#locationID").val();
			obj.country2 = $("#country2").val();
			obj.stateid2 = $("#stateid2").val();
			obj.pPin = $("#pinID").val();
			obj.pIncharge = $("#pIncharge").val();
			obj.cName = $("#cname").val();
			obj.cAddress = $("#caddress").val();
			obj.country = $("#country").val();
			obj.stateid = $("#stateid").val();
			obj.cityb = $("#cityb").val();
			obj.street1b = $("#street1b").val();
			obj.street2b = $("#street2b").val();
			obj.cPin = $("#cPin").val();
			obj.email = $("#email").val();
			obj.mobile = $("#mobile").val();
			obj.remark = $("#remarks").val();
			obj.status = $("#status").val();
			obj.billingStreet1 = $("#street1").val();
			obj.billingStreet2 = $("#street2").val();
			obj.billingGstNo = $("#gstNo").val();
			obj.documentList = uploadList;
			obj.shippingList = item_data;
			obj.productList = item_sku_data;
			obj.projectType = $("#projectType").val();

			datas.push(obj);
			console.log("dataset-------------------" + JSON.stringify(datas));

			saveAllQuotation(datas);
		}, 1000)
	}

}


function validFormData() {
	var allValid = true;

	if (!validationUpdated("Project Name Required", 'projectname'))
		allValid = false;
	if (!validationUpdated("Creation Date Required",
		'creationID'))
		allValid = false;

	if (!validationUpdated("Type Required",
		'projectType'))
		allValid = false;

	return allValid;
} function saveAllQuotation(datas) {

	console.log("dastssss" + datas)
	$.ajax({
		type: "POST",
		url: "create-project-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {

				/*$("#messageParagraph").text("Data Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				//$("#projectId").html(response.body[0].projectId);
				closeNav1();*/

				agGrid.simpleHttpRequest({
					url: "create-project-view"
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData = jsonData.viewProjectData;
					var len = allData.length;
					$('#totalPrj').find('span').html(len);
					gridOptions1.api.setRowData(allData);
					var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
					if (firstRowNode) {
						firstRowNode.setSelected(true); // Set the row as selected
					}
				});

			}

		},
		error: function(datas) {
			console.log(datas)
		}
	})

}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}


function onQuickFilterChanged() {
	gridOptions1.api.setQuickFilter(document.getElementById('quickFilter').value);
	let len = gridOptions1.api.getDisplayedRowCount();
	$('#totalPrj').find('span').html(len);
	let firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0);
	let id = firstRowNode.data.projectId;

}

function stateList2() {
	var countryId2 = $("#country2").val();
	if (countryId2) {
		$.ajax({
			type: "POST",
			url: "create-project-state-list",
			dataType: 'json',
			contentType: 'application/json',
			data: countryId2,
			success: function(response) {
				console.log("Response data:", response);
				if (response.message == "success") {
					console.log(response);
					$("#stateid2").empty();
					$("#stateid2").append("<option value=''>Select</option>");

					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid2").append(option);
					}
				}
			},
			error: function(data) {
				console.log(data);
				$("#stateid2").empty();
				$("#stateid2").append("<option value=''>Select</option>");
			}
		})
	} else {
		$("#stateid2").empty();
		$("#stateid2").append("<option value=''>Select</option>");
	}
}

//FOR PROJECT STATE EDIT 

function getStateDataOnEdit(stateid2) {
	var country2 = $("#country2").val();
	if (country2) {
		$.ajax({
			type: "GET",
			url: "create-project-state-list-project?id=" + country2,
			success: function(response) {
				if (response.message == "success") {
					$("#stateid2").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid2").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid2").append(option);
					}
					$("#stateid2").val(stateid2);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#stateid2").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid2").append(option);
	}
}


//for billing country dropdown

function stateList() {
	var countryId = $("#country").val();

	if (countryId) {
		$.ajax({
			type: "POST",
			url: "create-project-billing-state-list",
			dataType: 'json',
			contentType: 'application/json',
			data: countryId,
			success: function(response) {
				console.log("Response data:", response);
				if (response.message == "success") {
					console.log(response);
					$("#stateid").empty();
					$("#stateid").append("<option value=''>Select</option>");

					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid").append(option);
					}
				}
			},
			error: function(data) {
				console.log(data);
				$("#stateid").empty();
				$("#stateid").append("<option value=''>Select</option>");
			}
		})
	} else {
		$("#stateid").empty();
		$("#stateid").append("<option value=''>Select</option>");
	}
}


//FOR BILLING STATE EDIT 

function getBillingStateDataOnEdit(stateid) {
	var country = $("#country").val();

	if (country) {
		$.ajax({
			type: "GET",
			url: "create-project-state-list-billing?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#stateid").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid").append(option);
					}
					$("#stateid").val(stateid);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#stateid").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid").append(option);
	}
}

//FOR DELETE

function deleteProject() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var id = selectedRows[0].projectId;
	$.ajax({
		type: "POST",
		url: "create-project-delete?id=" + id,
		success: function(response) {
			if (response.message == "Success") {


				agGrid.simpleHttpRequest({
					url: "create-project-view"
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData = jsonData.viewProjectData;
					var len = allData.length;
					$('#totalPrj').find('span').html(len);
					gridOptions1.api.setRowData(allData);
					var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
					if (firstRowNode) {
						firstRowNode.setSelected(true); // Set the row as selected
					}
				});

			} else {

			}
		},
		error: function(data) {
			console.log(data)
		}
	})
}


function addId() {
	$("#projectId").val("");
	$("#projectname").val("");
	$("#creationID").val("");
	$("#locationID").val("");
	$("#stateID").val("");
	$("#pinID").val("");
	$("#pIncharge").val("");
	$("#cname").val("");
	$("#caddress").val("");
	$("#cstate").val("");
	$("#cPin").val("");
	$("#email").val("");
	$("#mobile").val("");
	$("#remarks").val("");
	$("#status").val("");
	$("#projectType").val("");
	$("#stateid2").val("");
	$("#country2").val("");
	$("#country").val("");
	$("#stateid").val("");
	$("#cityb").val("");
	$("#street1").val("");
	$("#street2").val("");
	$("#gstNo").val("");
	//
	$("#shippingName").val("");
	$("#shippingEmail").val("");
	$("#shippingMobileNo").val("");
	$("#shippingContact").val("");
	$("#country1").val("");
	$("#stateid1").val("");
	$("#shippingCity").val("");
	$("#shippingStreet1").val("");
	$("#shippingStreet2").val("");
	$("#shippingPin").val("");
	$("#shippingGstNo").val(""); 
$("#doctbodyData").val("");


}


function add1(){
addId();
	gridOptions1.api.deselectAll();
	$('#projectId,#projectname,#creationID,#locationID,#stateID,#pinID,#pIncharge,#cname,#caddress,#cstate,#cPin,#email,#mobile,#remarks,#status,#projectType,#stateid2,#country2,#country,#stateid,#cityb,#street1,#street2,#gstNo,#shippingName,#shippingEmail,#shippingMobileNo,#shippingContact,#country1,#stateid1,#shippingCity,#shippingStreet1,#shippingStreet2,#shippingPin,#shippingGstNo').prop('disabled', false);

}

function enableFields1(){
$('#projectId,#projectname,#creationID,#locationID,#stateID,#pinID,#pIncharge,#cname,#caddress,#cstate,#cPin,#email,#mobile,#remarks,#status,#projectType,#stateid2,#country2,#country,#stateid,#cityb,#street1,#street2,#gstNo,#shippingName,#shippingEmail,#shippingMobileNo,#shippingContact,#country1,#stateid1,#shippingCity,#shippingStreet1,#shippingStreet2,#shippingPin,#shippingGstNo').prop('disabled', false);

}

function next1(){
	$("#basic").addClass('basic');
	$("#billing").removeClass('billing');



	$(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
	$(".nav-link[href='#billing']").addClass("active"); // Add 'active' to Doctor Details nav-link

	$("#tab-billing").addClass("active").attr("aria-selected", "true");
	$("#tab-basic").removeClass("active").attr("aria-selected", "false");

	$("#billing").addClass("show active");
	$("#basic").removeClass("show active");

}


function prev1(){
	
	$("#basic").removeClass('basic');
	$("#billing").addClass('billing');



	$(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
	$(".nav-link[href='#basic']").addClass("active"); // Add 'active' to Doctor Details nav-link

	$("#tab-basic").addClass("active").attr("aria-selected", "true");
	$("#tab-billing").removeClass("active").attr("aria-selected", "false");

	$("#basic").addClass("show active");
	$("#billing").removeClass("show active");

}
