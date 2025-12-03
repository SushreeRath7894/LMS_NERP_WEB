
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,

		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Payment ID",
		field: "paymentId",
		pinned: 'left',
		width: 200,
		/*cellRenderer: function(params) {
			return '<a onclick=getPaymentDetails("' + params.data.paymentId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.paymentId + '</a>';
		}*/
	}, {
		headerName: 'Sales Invoice ID',
		field: "salesInvoiceId",
		width: 150,
	}, {

		headerName: 'PO ID',
		field: "poId",
		width: 150,
	}, {

		headerName: 'Paid Amount',
		field: "payAmount",
		width: 200,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	}, {
		headerName: 'Payment Status',
		field: "paymentStatus",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.INVOICE_STATUS === null) {
				return '<div style="color: red; font-weight: bold;">Pending</div>';
			} else {
				var statusStyle = getStatusStyle(params.data.paymentStatus);
				return `<div style="${statusStyle}">${params.data.paymentStatus}</div>`;
			}
		}

	}, {
		headerName: 'Approval Status',
		field: "approvalStatus",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.approvalStatus == "Rejected") {
				return '<div style="color:red;font-weight: bold;">' + params.data.approvalStatus + '</div>';
			} if (params.data.approvalStatus == "Approved") {
				return '<div style="color:blue;font-weight: bold;">' + params.data.approvalStatus + '</div>';
			} else {
				return '<div style="color:orange;font-weight: bold;">' + params.data.approvalStatus + '</div>';
			}
		}

	}, {
		headerName: 'Approved By',
		field: "approvedBy",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
	}, {

		headerName: 'Reject By',
		field: "rejectedBy",
		width: 150,
	}, {
		headerName: 'Payment Datetime',
		field: "paymentDate",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">' + params.data.paymentDate + '</div>';

		}

	}];

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
		width: 251,
		height: 10
	},
	onSelectionChanged: rowSelect,
};
$(document)
	.ready(
		function() {
			$("#documentDetails").show();
			$('#docTbl').on('click', '.rmv1', function() {
				openDeleteConfirm();
				var value = $(this).parent("div").attr("id");
				$("#dltValue").val(value);
			});

			//var ld = localStorage.getItem('ReqData');
			//alert("ld============"+ld);
			$('.loader').show();
			var today = new Date();
			var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
			var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
			var fromDate = fromDateString.toString();
			var todate = toDateString.toString();
			$("#toDate").val(todate);
			$("#fromDate").val(fromDate);
			viewFilteredData();
			$('#delete').attr("disabled", true);

			var gridDiv = document.querySelector('#myGrid');
			new agGrid.Grid(gridDiv, gridOptions);
			gridOptions.api.setRowData();
			$("#Cancel").hide();
			$("#addData").hide();
			$("#save").hide();
			$('#delete').attr("disabled", true);
			$('#paymentReject').attr("disabled", true);
			$('#paymentApprove').attr("disabled", true);


			$("#Cancel").click(function() {
				$('.loader').show();
				$("body").addClass("overlay");
				$("#reqTable").show();
				$(".btn-hs").show();
				$("#myGrid").show();
				$("#addData").hide();


				agGrid.simpleHttpRequest({
					url: "view-paymentsreceived-through-ajax"
				}).then(function(data) {
					var len = data.length;
					$('#totalCandidate').find('span').html(len);
					//	gridOptions.api.setRowData(data);
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

				$("#PaymentheadId").html('');
				$("#itemId").val('');
				$("#itemName").val('');
				$("#quantity").val('');
				$("#taxType").val('');

				$('.loader').hide();
				$("body").removeClass("overlay");
			});
			/*sales Invoice page*/
			var ld = localStorage.getItem('ReqData');
			//alert("lddddddd"+ld);
			var data = JSON.parse(ld);
			if (data) {
				$('#hideTbl').hide();
				$("#add").hide();
				$("#myGrid").hide();
				$("#save").show();
				$("#Cancel").show();
				$("#addData").show();
				$("#totalCandidate").hide();
				$("#dwnld").hide();
				$("#upld").hide();
				$("#salesOrderId").val(data[0].salesOrder);
				$("#taxType").val(data[0].taxType);
				$("#custId").val(data[0].custId);
				$("#custName").val(data[0].custName);
				getAddressDetails(data[0].custId);
				//alert("custId"+custId);
				//alert("saleInvoice"+saleInvoice);
				//getSalesOrder(data[0].custId,data[0].salesOrder);
				console.log("dataset for challan-------", JSON.stringify(data[0]));
				//	return false;
				hideShowS();
				localStorage.setItem('ReqData', null);
			} else {
				$("#salesOrderId").val('New');
			}


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


function getStatusStyle(paymentStatus) {
	switch (paymentStatus) {
		case "Fully Paid":
			return 'color: green; font-weight: bold;';
		case "Pending":
			return 'color: red; font-weight: bold;';
		case "Partial Paid":
			return 'color: orange; font-weight: bold;';
		default:
			return 'color: black; font-weight: bold;';
	}
}

function add() {
	$("#documentDetails").show();
	//alert("hi");
	$("#searchRowDiv").hide();
	$("#delete").hide();
	$('#hideTbl').hide();
	$("#add").hide();
	$("#myGrid").hide();
	$("#save").show();
	$("#Cancel").show();
	$("#addData").show();
	$("#totalCandidate").hide();
	$("#dwnld").hide();
	$("#upld").hide();
	$("#addre").hide();
	$("#PaymentheadId").html('');
	$("#itemId").val('');
	$("#itemName").val('');
	$("#quantity").val('');
	$("#taxType").val('');
	$("#salesOrderId").val('');
	$("#custId").val();
	$("#custName").val();
	$("#receivedFullamount").val();
	const date = new Date();
	let day = date.getDate();
	let month = date.getMonth() + 1;
	let year = date.getFullYear();
	let qutUpdatedOn = `${day}-${month}-${year}`;
	$("#paymentDate").val(qutUpdatedOn);

	$.ajax({
		type: "GET",
		url: "view-paymentsreceived-get-payment",
		success: function(response) {
			if (response.message == "success") {
				//console.log("Response--------------"+JSON.stringify(response.body[0].name));
				$("#payment").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});


	$.ajax({
		type: "GET",
		url: "view-paymentsreceived-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				//console.log("Response--------------"+JSON.stringify(response.body[0].name));
				$("#paymentReference").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});
}

function Cancel() {
	$('.formValidation').remove();
	// location.reload();
	$("#totalCandidate").show();
	$("#searchRowDiv").show();
	$("#fbtn").show();
	$("#delete").show();
	$("#add").show();
	$("#myGrid").show();
	$("#save").hide();
	$("#Cancel").hide();
	$("#addData").hide();
	$('#hideTbl').show();

	$("#dwnld").show();
	$("#upld").show();
	$('#delete').attr("disabled", true);
	$("#invoiceTableDetails").empty();

}
/* taxtype fn */
function hideShowS() {
	var taxType = $("#taxType").val();
	if (taxType == "true") {
		$('#igstTR').show();
		$('#cgstTR').hide();
		$('#sgstTR').hide();
	} else {
		$('#igstTR').hide();
		$('#cgstTR').show();
		$('#sgstTR').show();
	}
}

function save() {


	if (validProductData() && validFormData()) {
		var datas = [];
		var imageValid = true;
		var uploadList = [];
		$("#doctbodyData > tr").each(

			function() {
				//alert("hyy")
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined'
					&& fileName != null) {
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
				uploadData['documnentName'] = $(this).find(".docNoclss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				uploadList.push(uploadData);

				console.log(uploadData)

			});


		setTimeout(function() {
			//	if (itemOptions.api.getDisplayedRowCount() > 0) {

			var obj = {};
			obj.paymentId = $("#paymentId").val();
			obj.paymentId = $("#PaymentheadId").html();
			obj.saleInvoiceId = $("#saleInvoiceId").val();
			obj.amountReceived = $("#amountReceived").val();
			obj.bankCharges = $("#bankCharges").val();
			obj.payment = $('#payment').val();
			obj.paymentMode = $('#paymentMode').val();
			obj.depositTo = $('#depositToId').val();
			obj.reference = $('#reference').val();

			obj.custId = $('#custId').val();
			obj.custName = $('#custName').val();
			obj.checkBox = $(
				"#receivedFullamount:checkbox:checked").val();
			obj.internalNotes = $("#internalNotes").val();
			obj.fullyPaymentAmount = $("#fullyPaymentAmount").val();

			alert(obj.checkBox)

			if (obj.checkBox == undefined) {
				obj.checkBox = "1";
			} else {
				obj.checkBox = "0";
			}

			obj.documentList = uploadList;

			console.log('Object', obj)
			datas.push(obj);

			savePaymentReceived(datas);
			//}
		}, 1000)

	}
}
function savePaymentReceived(datas) {
	console.log(JSON.stringify(datas))
	$
		.ajax({

			type: "POST",
			url: "view-paymentsreceived-add",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.message == "Success") {

					console.log(JSON.stringify(datas))

					$("#messageParagraph").text(
						"Data saved successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#saleInvoiceId").val(
						response.body[0].saleInvoiceId);
					$("#amountReceived").val(
						response.body[0].amountReceived);
					$("#bankCharges").val(response.body[0].bankCharges);
					$("#payment").val(response.body[0].payment);
					$("#paymentMode").val(response.body[0].paymentMode);
					$("#depositTo").val(response.body[0].depositTo);
					$("#reference").val(response.body[0].reference);
					$("#custId").val(response.body[0].custId);
					$("#custName").val(response.body[0].custName);
					$("#internalNotes").val(
						response.body[0].internalNotes);
					$("#fullyPaymentAmount").val(
						response.body[0].fullyPaymentAmount);

					if (data[0].checkBox == "1") {
						$("#receivedFullamount").prop("checked", true);
					} else {
						$("#receivedFullamount").prop("checked", false);
					}

					$("#PaymentheadId")
						.html(response.body[0].paymentId);
					$("#doctbodyData").empty();
					var documentList = response.body.documentList;
					console.log('documentList', documentList)
					if (documentList != null && documentList != "") {
						console.log('documentList1', documentList)
						for (var i = 0; i < documentList.length; i++) {
							var tbl = '<tr>'
								+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
								+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
								+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclss" id="docnoid_' + i + '"> </div></td>'
								+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-pencil" id="clickImg_' + i + '"></i> </label>'
								+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
								+ i
								+ '" name="userImage" onchange="saveMultiFile(event)" /> </div>'
								+ '</div> <input type="hidden" id="uploadHidden_' + i + '" value="' + documentList[i].fileName + '" class="uploadHidCls">'
								+ '<div id="uploadedBillDiv_' + i + '" align="center" class="uploadedBillCls"><div class="uploadicon position-l">'
								+ documentList[i].action
								+ '</div></div>'
								+ '<div id="imageName_' + i + '" class="imageName">'
								+ documentList[i].fileName
								+ '</div>'
								+ '<div id="dltImage_' + i + '" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div>'
								+ '<input type="hidden" id="editId_' + i + '" value="' + documentList[i].vendorRfqId + '">'
								+ '</td>' + '</tr>';

							$("#doctbodyData").append(tbl);
							console.log('tbl', tbl)

						}
					} else {
						var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
							+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
							+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
							+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="custom-file-upload"></div> <input type="hidden" id="editId_0"> </td>'
							+ '</tr>';
						$("#doctbodyData").append(tbl);
					}

				}

			},
			error: function(datas) {
				console.log(datas)
			}
		})

}

function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
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
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l '> </div>";
	}
	var dltImg = "<i class='ti-close position-l rmv1'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);

	$("#dltImage_" + counter).addClass("custom-file-delete");
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
		divInput.eq(6).attr('id', "dltImage_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

}

function checkForDuplicateEntry(event) {
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var count = 0;
	$(".docNoclss").each(function() {
		if (document == $(this).val()) {
			count++;
		}
	})
	if (count >= 2) {
		$("#messageParagraph").text("Document Name Already Entered");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');

		return false;
	} else {
		return true;
	}

}
function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}
//for closeing modal box for dlt ind product
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
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div> </td>'
			+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}

//validation
function validFormData() {
	var allValid = true;
	/* if (!validationUpdated("Sales Order Id. Required",
			'salesOrderId'))
		allValid = false; */
	/* if (!validationUpdated("Store Name Required", 'storeId'))
		allValid = false; */
	return allValid;
}

function validProductData() {
	return true;
}

function editPaymentReeived(paymentId, saleInvoiceId) {
	//alert("salesOrderId++++++++++"+salesOrderId);
	//Cancel();

	$("#searchRowDiv").hide();

	$("#myGrid").hide();
	$("#reqTable").hide();
	$(".btn-hs").hide();
	$("#addData").show();
	$("#add").hide();
	$("#save").show();
	$("#Cancel").show();
	$("#totalCandidate").hide();
	$("#dwnld").hide();
	$("#upld").hide();
	$("#addre").hide();
	$("#documentDetails").show();

	agGrid
		.simpleHttpRequest({
			url: 'view-paymentsreceived-edit-new?id=' + paymentId
		})
		.then(
			function(data) {
				getSalesInvoiceList(data[0].custId, 'edit',
					data[0].saleInvoiceId);

				console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS"
					+ data);
				$("#PaymentheadId").html(data[0].paymentId);
				$("#taxtype").val(data[0].taxType);

				//getPackageId();
				$("#custId").val(data[0].custId);
				$("#custName").val(data[0].custName);
				$("#saleInvoiceId").val(data[0].saleInvoiceId);
				$("#amountReceived").val(data[0].amountReceived);
				$("#bankCharges").val(data[0].bankCharges);
				$("#paymentDate").val(data[0].qutUpdatedOn);
				$("#paymentMode").val(data[0].paymentMode);
				$("#payment").val(data[0].payment);
				$("#depositTo").val(data[0].depositTo);
				$("#depositToId").val(data[0].depositToId);
				$("#reference").val(data[0].reference);
				$("#internalNotes").val(data[0].internalNotes);
				$("#fullyPaymentAmount").val(
					data[0].fullyPaymentAmount);

				if (data[0].checkBox == "0") {
					$("#receivedFullamount").prop("checked", true);
				} else {
					$("#receivedFullamount").prop("checked", false);
				}

				getEditTaxType(data[0].saleInvoiceId);
				//	getsalesOrderData1(data[0].salesOrderId);
				getAllDataForSalesInvoice1(data[0].saleInvoiceId);
				$("#doctbodyData").empty();
				var documentList = data[0].documentList;
				if (documentList != null && documentList != "") {
					for (var i = 0; i < documentList.length; i++) {

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
						+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}

			});

}

function getEditTaxType(search) {

	$.ajax({
		type: "POST",
		url: "view-paymentsreceived-get-customer-list",
		dataType: 'json',
		contentType: 'application/json',
		data: search,
		success: function(response) {
			if (response.message == "success") {
				console.log("response data" + response.body[0].taxType)
				$("#taxType").val(response.body[0].taxType);
				hideShowS();
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}
/* 
Delete  
 */
function deleteQuotation() {

	$('#deleteQuot').modal('show');
}
function cancelModalBtn() {

	$("#deleteModalBtn").removeAttr("disabled");
}

function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var paymentId = selectedRows[0].paymentId;
	var rowCount = 0;
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		/* if (selectedData.map(node => node.approvalStatus)=="Pending"){
			$('#delete').attr("disabled", false);
			$('#paymentReject').attr("disabled", false);
			$('#paymentApprove').attr("disabled", false);
		} else {
			$('#delete').attr("disabled", true);
			$('#paymentReject').attr("disabled", true);
			$('#paymentApprove').attr("disabled", true);
		} */
		$('#delete').attr("disabled", false);
		$('#paymentReject').attr("disabled", false);
		$('#paymentApprove').attr("disabled", false);
		getPaymentDetails(paymentId);

	} else {
		$('#delete').attr("disabled", true);
		$('#paymentReject').attr("disabled", true);
		$('#paymentApprove').attr("disabled", true);

	}

}

function deleteOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].paymentId;
	$.ajax({
		type: "POST",
		url: "view-paymentsreceived-delete?id=" + id,
		success: function(response) {
			if (response.message == "success") {
				location.reload();
				agGrid.simpleHttpRequest({
					url: 'view-paymentsreceived-through-ajax'
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
				Cancel();
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}

function onQuickFilterChanged() {
	$(".ti-search srchicon").hide();
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var totalRowCount = gridOptions.api.getModel().getRowCount();

	$('#totalCandidate').find('span').html(gridOptions.api.getModel().getRowCount());
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

/* customer AutoSearch */

function getCustomerList() {
	var search = $("#custName").val();
	//alert('hello get cust1 search--------'+search);
	if (search) {
		//alert('hello get cust2');
		$
			.ajax({
				type: "POST",
				url: "view-paymentsreceived-get-customer-list",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {
						console.log("response data"
							+ JSON.stringify(response))
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
									+ response.body[i].custId
									+ '\',\''
									+ response.body[i].custName
									+ '\',\''
									+ response.body[i].custGSTNo
									+ '\',\''
									+ response.body[i].taxType
									+ '\')">'
									+ response.body[i].custName
									+ '</li>';
							}
							content += '<li style="margin-left:-30px;"  onclick="AddCustomer();"><a class="addCust" href="#">Add New Customer</a>'
								+ '</li>';
							content += '</ul>';
							//console.log("content " + content)
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;" onClick="selectAutocompleteValue()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;"  onclick="AddCustomer();"><a class="addCust" href="#">Add New Customer</a>'
								+ '</li>';
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
	}

}
function AddCustomer() {

	$('#addCustomerModel').modal('show');

}
function cancelBtn1() {
	$('#addCustomerModel').modal('hide');
}
function selectAutocompleteValue1(custId, custName, custGSTNo, taxType) {

	if (custId) {
		//alert(custName)

		$("#custId").val(custId);

		$("#custName").val(custName);
		$("#custGSTNo").val(custGSTNo);
		$("#taxType").val(taxType);
		$("#search").val(custName);
		$("#search").attr('data-procat', custId);
		$("#suggesstion-box1_").hide();

		$("#addre").show();

		hideShowS();
		//checkForDuplicate(key,counter);
		getAddressDetails(custId);
		getSalesInvoiceList(custId, "add", "");

	} else {

		$("#custId").val("");

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

function getSalesInvoiceList(custId, id1, id2) {
	//alert(id2)
	//alert("parameter1");
	// var cname = $('#c//ustId').val();
	if (custId) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-salesinvoicelist?id=" + custId
				+ "&type=" + id1,
			success: function(response) {
				if (response.message == "success") {
					//console.log("Salesinvoice===="+JSON.stringify(response.body))

					$("#saleInvoiceId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#saleInvoiceId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].name);
						$(option).html(response.body[i].name);
						$("#saleInvoiceId").append(option);
					}
					if (id2 != "") {
						$("#saleInvoiceId").val(id2);
					}

				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#saleInvoiceId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("saleInvoiceId");
		$("#saleInvoiceId").append(option);
		$("#saleInvoiceId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#salesOrderId").append(option);
	}
}

function getSalesOrder(custId, salesOrder) {
	//alert("parameter2");

	if (custId) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");

		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-salesorderlist?id=" + custId,
			success: function(response) {
				if (response.message == "success") {
					//console.log("Salesorder===="+JSON.stringify(response.body))

					$("#salesOrderId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#salesOrderId").append(option);
					for (var i = 0; i < response.body.length; i++) {

						if (response.body[i].name == salesOrder) {
							$(option).val(response.body[i].name);
							$(option).html(response.body[i].name);
							$("#salesOrderId").append(option);
							//alert("hello");
						}

					}

					if (salesOrderId == null) {
						$("#salesOrderId").val(null);
					} else {
						$("#salesOrderId").val(salesOrderId);
					}

				}
			},
			error: function(e) {
			}
		});

	} else {
		$("#salesOrderId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("salesOrderId");
		$("#salesOrderId").append(option);
		$("#salesOrderId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");

	}

}
/* ============================START================================= */


function getPakageEdit(sorder, salepkg) {

	if (sorder != '') {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-get-PackageId?id=" + sorder,
			success: function(response) {
				if (response.message == "success") {
					$("#salePackageId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#salePackageId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#salePackageId").append(option);
					}
				}

				$("#salePackageId").val(salepkg);
			},
			error: function(e) {
			}
		});
	} else {
		$("#salePackageId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#salePackageId").append(option);
		$("#salePackageId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getPackageId() {

	var cname = $('#salesOrderId').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-get-PackageId?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#salePackageId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#salePackageId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#salePackageId").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#salePackageId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#salePackageId").append(option);
		$("#salePackageId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

/* 	===========================END======================================== */

function getAddressDetails(custId) {

	//var Id=$("#custName").val();
	$.ajax({
		type: "GET",
		url: "view-paymentsreceived-get-address?id=" + custId,

		async: false,
		success: function(response) {
			if (response.message == "Success") {

				console.log(response.body)
				$("#con").html(response.body.country);
				$("#bstate").html(response.body.states);
				$("#bcity").html(response.body.city);
				$("#bstreet1").html(response.body.street1);
				$("#bstreet2").html(response.body.street2);
				$("#bzipcode").html(response.body.zipCode);
				$("#bphone").html(response.body.phone);
				$("#bfax").html(response.body.fax);

				$("#con1").html(response.body.country1);
				$("#bstate1").html(response.body.states1);
				$("#bcity1").html(response.body.city1);
				$("#bstreet11").html(response.body.street11);
				$("#bstreet21").html(response.body.street21);
				$("#bzipcode1").html(response.body.zipCode1);
				$("#bphone1").html(response.body.phone1);
				$("#bfax1").html(response.body.fax1);

			}
		}
	});
}
//add Customer Info

function addCustomerInfo() {
	//alert('hello');return false;
	var obj = {};
	obj.customerId = $('#customerId').text();
	obj.customerType = $('#customerType').val();
	obj.salutation = $('#salutation').val();
	obj.customerName = $('#customerName').val();
	obj.companyName = $('#companyName').val();
	obj.customerDisplayName = $('#customerDisplayName').val();
	obj.cusEmail = $('#cusEmail').val();
	obj.custMobile = $('#custMobile').val();
	obj.custSkype = $('#custSkype').val();

	obj.custDesignation = $('#custDesignation').val();
	obj.department = $('#department').val();
	obj.webSite = $('#webSite').val();
	obj.status = $('#status').val();
	obj.pan = $('#pan').val();
	obj.currency = $('#currency').val();
	obj.openingBalance = $('#openingBalance').val();
	obj.paymentTerms = $('#paymentTerms').val();
	obj.enableDtls = $('#enableDtls').val();
	obj.portableLang = $('#portableLang').val();

	obj.faceBook = $('#faceBook').val();
	obj.twitter = $('#twitter').val();
	obj.country = $('#country').val();
	obj.states = $('#states').val();
	obj.city = $('#city').val();
	obj.street1 = $('#street1').val();
	obj.street2 = $('#street2').val();
	obj.zipCode = $('#zipCode').val();
	obj.phone = $('#phone').val();
	obj.fax = $('#fax').val();

	obj.country1 = $('#country1').val();
	obj.states1 = $('#states1').val();
	obj.city1 = $('#city').val();
	obj.street11 = $('#street11').val();
	obj.street21 = $('#street21').val();
	obj.zipCode1 = $('#zipCode1').val();
	obj.phone1 = $('#phone1').val();
	obj.fax1 = $('#fax1').val();

	obj.salutation1 = $('#salutation1').val();
	obj.firstName = $('#firstName').val();
	obj.lastName = $('#lastName').val();
	obj.emailAdd = $('#emailAdd').val();
	obj.mobile = $('#mobile').val();
	obj.remarks = $('#remarks').val();

	console.log("object on add customer-----------" + JSON.stringify(obj));
	//return false;

	/* FORM VALIDATION STARTS*/

	var validation = true;

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-paymentsreceived-adds",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {
					console.log(response);

					$("#add").hide();
					$("#copy").show();
					$("#delete").hide();
					$("#totalReq").show();
					$("#myGrid").hide();
					$("#searchRowDiv").hide();
					$("#demo").hide();
					$("#addCustomerModel").modal('hide');

				}
			},
			error: function(data) {

				//console.log(data);
			}
		})
	}

}
function addnewAddress() {

	$('#addAddressModel').modal('show');

}
function addnewAddress1() {

	$('#addAddressModel1').modal('show');

}
function cancelBtn() {
	$('#addAddressModel').modal('hide');
}
function cancelBtn2() {
	$('#addAddressModel1').modal('hide');
}
function tcscancelBtn() {
	$('#manageTCsModel').modal('hide');
}

function addCustomerInfo2() {
	var obj = {};
	var Id = $("#custId").val();

	obj.customerId = $("#custId").val();
	obj.country = $("#countryb").val();
	obj.states = $("#statesb").val();
	obj.city = $("#cityb").val();
	obj.street1 = $("#street1b").val();
	obj.street2 = $("#street2b").val();
	obj.zipCode = $("#zipCodeb").val();
	obj.phone = $("#phoneb").val();
	obj.fax = $("#faxb").val();

	$.ajax({
		type: "POST",
		url: "view-paymentsreceived-add-cust-billingaddress",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text("Data saved successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				//$("#myGrid").show();
				$("#billingInfo").hide();

			}
		},
		error: function(data) {
		}
	})

}

function addCustomerInfo1() {
	var obj = {};
	var Id = $("#custId").val();

	obj.customerId = $("#custId").val();
	obj.country1 = $("#country1s").val();
	obj.states1 = $("#states1s").val();
	obj.city1 = $("#city1s").val();
	obj.street11 = $("#street11s").val();
	obj.street21 = $("#street21s").val();
	obj.zipCode1 = $("#zipCode1s").val();
	obj.phone1 = $("#phone1s").val();
	obj.fax1 = $("#fax1s").val();

	$.ajax({
		type: "POST",
		url: "view-paymentsreceived-add-cust-shippingaddress",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text("Data saved successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				//$("#myGrid").show();
				$("#shippingInfo").hide();

			}
		},
		error: function(data) {
		}
	})

}

function showDisplay() {
	var title = $('#salutation').val();
	var name = $('#customerName').val();
	var fullName = title + ' ' + name;
	$('#customerDisplayName').val(fullName);
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
			url: "view-paymentsreceived-stateList?id=" + cname,
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

function getStateDetails1() {

	var cname = $('#country1').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1").append(option);
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDetailsb() {

	var cname = $('#countryb').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#statesb").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#statesb").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#statesb").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#statesb").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#statesb").append(option);
		$("#statesb").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDetailss() {

	var cname = $('#country1s').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states1s").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1s").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1s").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1s").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1s").append(option);
		$("#states1s").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function copyAsBillingAdd() {

	var country = $('#country').val();
	var states = $('#states').val();
	var city = $('#city').val();
	var street1 = $('#street1').val();
	var street2 = $('#street2').val();
	var zipCode = $('#zipCode').val();
	var phone = $('#phone').val();
	var fax = $('#fax').val();

	$('#country1').val(country);
	$('#states1').val(states);
	getStateDataOnEdit1(states);
	$('#city1').val(city);
	$('#street11').val(street1);
	$('#street21').val(street2);
	$('#zipCode1').val(zipCode);
	$('#phone1').val(phone);
	$('#fax1').val(fax);
}

function getStateDataOnEdit1(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-paymentsreceived-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1").append(option);
					}
					$("#states1").val(stateId);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1").append(option);
	}
}

function downloadDetails() {
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
	gridOptions.api.exportDataAsCsv(dataset);
}

/* ============================START================================= */
function getAllDataForSalesInvoice() {
	var saleInvoiceId = $("#saleInvoiceId").val();
	//	alert("salesInvoice+++++"+saleInvoiceId);
	agGrid
		.simpleHttpRequest(
			{
				url: 'view-paymentsreceived-get-salesinvoicedata?id='
					+ saleInvoiceId
			})
		.then(
			function(data) {
				console.log("salesInvoice-----------"
					+ JSON.stringify(data));

				trCount = '';
				var countItm = data.length;
				//alert("countItm"+countItm)

				//return false;
				console.log("count response mail----------"
					+ countItm);
				for (var i = 0; i < data.length; i++) {
					$("#amountHiddenId").val(data[i].grandTotal);

					//alert(data[i].itemName);
					var itemRow = '<tr>'
						//+'<td><input class="form-check-input" type="checkbox" id="gridCheck2"></td>'
						+ '<td>'
						+ '<div class="d-flex align-content-center">'
						+ '<div class="textsec">'
						+ '<div class="name-txt">'
						+ data[i].invoiceDate
						+ '</div>'
						+ '</div>'
						+ '</div>'
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].saleInvoice
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].grandTotal
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].grandTotal
						+ '</td>'
						+ '<td style="text-align:center"><input type="text" id="fullyPaymentAmount"></td>'
						+ '</tr>';
					trCount = itemRow;
					$("#invoiceTableDetails").append(trCount);

				}
			});

}

function getAllDataForSalesInvoice1(saleInvoiceId) {
	//	var saleInvoiceId=$("#saleInvoiceId").val();
	//alert("salesInvoice+++++"+saleInvoiceId);
	agGrid
		.simpleHttpRequest(
			{
				url: 'view-paymentsreceived-get-salesinvoicedata?id='
					+ saleInvoiceId
			})
		.then(
			function(data) {
				console.log("salesInvoice-----------"
					+ JSON.stringify(data));

				trCount = '';
				var countItm = data.length;
				//alert("countItm"+countItm)

				//return false;
				var fullyPaymentAmount = "";
				console.log("count response mail----------"
					+ countItm);
				for (var i = 0; i < data.length; i++) {
					//alert(data[i].itemName);
					fullyPaymentAmount = data[i].grandTotal;
					//$("#fullyPaymentAmount").val(fullyPaymentAmount);
					var itemRow = '<tr>'
						//+'<td><input class="form-check-input" type="checkbox" id="gridCheck2"></td>'
						+ '<td>'
						+ '<div class="d-flex align-content-center">'
						+ '<div class="textsec">'
						+ '<div class="name-txt">'
						+ data[i].invoiceDate
						+ '</div>'
						+ '</div>'
						+ '</div>'
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].saleInvoice
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].grandTotal
						+ '</td>'
						+ '<td style="text-align:center">'
						+ data[i].grandTotal
						+ '</td>'
						+ '<td style="text-align:center"><input type="text" value="' + fullyPaymentAmount + '" id="fullyPaymentAmount"></td>'
						+ '</tr>';
					trCount = itemRow;
					$("#invoiceTableDetails").append(trCount);

				}
			});

}

/* ============================END================================= */

//Account auto search
function getAccountList() {
	var search = $("#depositTo").val();

	if (search) {

		$
			.ajax({
				type: "POST",
				url: "view-paymentsreceived-get-account-list",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {
						console.log("response data"
							+ JSON.stringify(response))
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list2" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;" class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
									+ response.body[i].depositToId
									+ '\',\''
									+ response.body[i].accountName
									+ '\',\''
									+ response.body[i].custGSTNoo
									+ '\',\''
									+ response.body[i].taxTypee
									+ '\')">'
									+ response.body[i].accountName
									+ '</li>';
							}
							content += '<li style="margin-left:-30px;"  onclick="AddAccount();"><a class="addCust" href="#">Add Account</a>'
								+ '</li>';
							content += '</ul>';
							//console.log("content " + content)
							$("#suggesstion-box2_").show();
							$("#suggesstion-box2_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;" onClick="selectAutocompleteValuee()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;"  onclick="AddAccount();"><a class="addCust" href="#">Add Account</a>'
								+ '</li>';
							content += '</ul>';
							$("#suggesstion-box2_").show();
							$("#suggesstion-box2_").html(content);
						}
					}
				},
				error: function(data) {
					console.log(data);
				}
			})
	}

}

function selectAutocompleteValuee() {

	$("#depositToId").val("");

	$("#depositTo").val("");
	$("#custGSTNoo").val("");
	$("#taxTypee").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

}

function selectAutocompleteValue2(depositToId, accountName, custGSTNo,
	taxType) {

	$("#depositToId").val(depositToId);
	$("#depositTo").val(accountName);
	$("#custGSTNoo").val(custGSTNo);
	$("#taxTypee").val(taxType);
	$("#search").val(accountName);
	$("#search").attr('data-procat', depositToId);
	$("#suggesstion-box2_").hide();
	//	hideShowS();
	//checkForDuplicate(key,counter);

}

function getEditTaxTypee(search) {

	$.ajax({
		type: "POST",
		url: "view-paymentsreceived-get-account-list",
		dataType: 'json',
		contentType: 'application/json',
		data: search,
		success: function(response) {
			if (response.message == "success") {
				console.log("response data" + response.body[0].taxType)
				$("#taxTypee").val(response.body[0].taxType);
				hideShow()();
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}

$(document).ready(function() {

	$("#receivedFullamount").click(function() {

		if ($("#receivedFullamount").prop('checked') == true) {
			var value = $("#amountHiddenId").val();
			$("#amountReceived").val(value);
			$("#fullyPaymentAmount").val(value);
		} else {
			$("#amountReceived").val("");
			$("#fullyPaymentAmount").val("");
		}
	});

});

function addAmount() {
	$('#addAmountPopup').modal('show');
}
function approveResultbtn() {
	$('#addAmountPopup').modal('hide');
}
function cancelPaymentBtn() {
	$('#invPayment').modal('hide');
}
function savePayment() {
	var bulkAmount = $("#bulkAmount").val();
	var userId = $("#userId").val();
	$.ajax({
		type: "GET",
		url: "view-paymentsreceived-savePayment?userId=" + userId + "&bulkAmount=" + bulkAmount,
		async: false,
		success: function(response) {

			if (response.message == "Success") {
				//cancel();
				$("#messageParagraph").text("Amount Added Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#addAmountPopup').modal('hide');
				//viewGateInDetails();

			}

		},
	});
}

function approvePayment() {
	$('#approveModal').modal('show');
}

function approveResultbtn() {
	$('#approveModal').modal('hide');
}

function approveResult() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var paymentIds = [];
	selectedRows.forEach(function(row) {
		paymentIds.push(row.paymentId);
	});
	var paymentIdsString = paymentIds.join(',');
	console.log(paymentIdsString);

	var invoiceId = selectedRows[0].paymentId;
	var userId = $("#userId").val();
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-payment-received-approval?invoiceId=" + btoa(paymentIdsString) + "&userId=" + userId,
		async: false,
		success: function(response) {

			if (response.code == "success") {
				//cancel();
				$("#messageParagraph").text("Approved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#approveModal').modal('hide');
				//viewGateInDetails();
				agGrid.simpleHttpRequest({
					url: "view-paymentsreceived-through-ajax"
				}).then(function(data) {
					var len = data.length;
					$('#totalCandidate').find('span').html(len);
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
					$('#delete').attr("disabled", true);
					$('#paymentReject').attr("disabled", true);
					$('#paymentApprove').attr("disabled", true);
				});
			}

		},
	});

}

function rejectPayment() {
	$('#rejectModal').modal('show');
}

function rejectResultbtn() {
	$('#rejectModal').modal('hide');
}

function rejectResult() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var invoiceId = selectedRows[0].salesInvoiceId;
	var userId = $("#userId").val();
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-payment-received-reject?invoiceId=" + btoa(invoiceId) + "&userId=" + userId,
		async: false,
		success: function(response) {

			if (response.code == "success") {
				//cancel();
				$("#messageParagraph").text("Rejected Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#rejectModal').modal('hide');
				//viewGateInDetails();
				agGrid.simpleHttpRequest({
					url: "view-paymentsreceived-through-ajax"
				}).then(function(data) {
					var len = data.length;
					$('#totalCandidate').find('span').html(len);
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

					$('#delete').attr("disabled", true);
					$('#paymentReject').attr("disabled", true);
					$('#paymentApprove').attr("disabled", true);
				});
			}

		},
	});

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

function downloadExcelFromGrid() {
	var selectedHeaders = ['Payment ID', 'Sales Invoice ID', 'PO ID', 'Paid Amount', 'Payment Status',
		'Approval Status', 'Approved By', 'Reject By', 'Payment Datetime'];
	var currentDate = new Date().toLocaleDateString('en-GB', {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	});
	currentDate = currentDate.split('/').join('_');
	var fileName = 'Payment_Received ' + currentDate + '.xlsx';

	var grandTotalAmount = 0;
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
			if (field === 'Paid Amount') {
				value = amountFormatter(parseFloat(value).toFixed(2));
			}
			data[header] = value;
		});


		rowData.push(data);

		var grandAmount = parseFloat(node.data[fieldMap['Paid Amount']]) || 0;

		grandTotalAmount += grandAmount;
	});

	grandTotalAmount = amountFormatter(grandTotalAmount.toFixed(2));

	var totalRow = {
		'Payment ID': "Total",
		'Sales Invoice ID': "",
		'PO ID': "",
		'Paid Amount': grandTotalAmount,
		'Payment Status': "",
		'Approval Status': "",
		'Approved By': "",
		'Reject By': "",
		'Payment Datetime': ""
	};

	rowData.push(totalRow);
	var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, ' Payment Received');
	XLSX.writeFile(wb, fileName);
}


function getPaymentDetails(id) {
	var editId = id.split(",");
	var paymentId = editId[0];
	var modal = editId[1];
	$("#salesInvoiceTableList").empty();
	$("#debitNoteTableList").empty();
	$("#methodOfAdjTableList").empty();
	$("#invPayment").modal("show");

	$.ajax({
		type: "GET",
		url: "view-paymentsreceived-details?paymentId=" + paymentId,
		async: false,
		success: function(response) {
			var jsonObject = JSON.parse(response.body);

			if (jsonObject.viewInvoicePaymentData != null && jsonObject.viewInvoicePaymentData != "null" && jsonObject.viewInvoicePaymentData != "") {
				var emptyBodyViewMethodsOfAdjData = '<tr align="left" valign="top">'
					+ '<td colspan="3" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				$("#methodOfAdjTableList").append(emptyBodyViewMethodsOfAdjData);
				//for invoice lists
				var totalBulkAmount = 0.00;
				var invoiceLen = jsonObject.viewInvoicePaymentData.length;
				for (var i = 0; i < invoiceLen; i++) {
					var getInvoice = '<tr>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].invoiceId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].poId + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].vendorName + '</td>'
						+ '<td style="text-align:right">' + amountFormatter(parseFloat(jsonObject.viewInvoicePaymentData[i].dueAmount).toFixed(2)) + '</td>'
						+ '<td style="text-align:right">' + amountFormatter(parseFloat(jsonObject.viewInvoicePaymentData[i].paidAmount).toFixed(2)) + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewInvoicePaymentData[i].payStatus + '</td>'
						+ '</tr>';
					$("#salesInvoiceTableList").append(getInvoice);
					totalBulkAmount = totalBulkAmount + parseFloat(jsonObject.viewInvoicePaymentData[i].paidAmount);

				}
				$("#bulkCustAmount").val(amountFormatter(totalBulkAmount.toFixed(2)));
				$('input[name="CashChequeOrOnline"][value="' + jsonObject.viewInvoicePaymentData[0].paymentMode + '"]').prop('checked', true);
				var paymentMode = jsonObject.viewInvoicePaymentData[0].paymentMode;
				$('.bankDiv, .chequeDiv, .upiDiv').hide();
				if (paymentMode === 'Cash') {
					$("#payRemarks").val(jsonObject.viewInvoicePaymentData[0].payRemarks);
				} else if (paymentMode === 'Cheque') {
					$('.chequeDiv').show();
					$("#chequeBankName").val(jsonObject.viewInvoicePaymentData[0].chequeBankName);
					$("#chequeBankBranch").val(jsonObject.viewInvoicePaymentData[0].chequeBankBranch);
					$("#chequeAccountNumber").val(jsonObject.viewInvoicePaymentData[0].chequeAccountNumber);
					$("#chequeNo").val(jsonObject.viewInvoicePaymentData[0].chequeNo);
					$("#payRemarks").val(jsonObject.viewInvoicePaymentData[0].payRemarks);
				} else if (paymentMode === 'Online') {
					$('.bankDiv').show();
					$("#bankSelect").val(jsonObject.viewInvoicePaymentData[0].bankSelect);
					$("#transactionNumber").val(jsonObject.viewInvoicePaymentData[0].transactionNumber);
					$("#receiverBankName").val(jsonObject.viewInvoicePaymentData[0].receiverBankName);
					$("#receiverBankBranch").val(jsonObject.viewInvoicePaymentData[0].receiverBankBranch);
					$("#receiverIfscCode").val(jsonObject.viewInvoicePaymentData[0].receiverIfscCode);
					$("#receiverAccountNumber").val(jsonObject.viewInvoicePaymentData[0].receiverAccountNumber);
					$("#payRemarks").val(jsonObject.viewInvoicePaymentData[0].payRemarks);
				} else if (paymentMode === 'Upi') {
					$('.upiDiv').show();
					$("#onlineUpiID").val(jsonObject.viewInvoicePaymentData[0].onlineUpiID);
					$("#receiverOnlineUpiID").val(jsonObject.viewInvoicePaymentData[0].receiverOnlineUpiID);
					$("#receiverUpiTransactionID").val(jsonObject.viewInvoicePaymentData[0].receiverUpiTransactionID);
					$("#payRemarks").val(jsonObject.viewInvoicePaymentData[0].payRemarks);
				}
				// for used debit notes list
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
				} else {
					var emptyBodyViewDebitNoteListUsed = '<tr align="left" valign="top">'
						+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#debitNoteTableList").append(emptyBodyViewDebitNoteListUsed);
				}
			}

			if (jsonObject.viewMethodsOfAdjData != null && jsonObject.viewMethodsOfAdjData != "null" && jsonObject.viewMethodsOfAdjData != "") {
				var emptyBodyViewDebitNoteListUsed = '<tr align="left" valign="top">'
					+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				$("#salesInvoiceTableList").append(emptyBodyViewDebitNoteListUsed);
				var emptyBodyViewDebitNoteGenerated = '<tr align="left" valign="top">'
					+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
					+ '</tr>';
				$("#debitNoteTableList").append(emptyBodyViewDebitNoteGenerated);
				var len = jsonObject.viewMethodsOfAdjData.length;
				for (var i = 0; i < len; i++) {
					var newMethodOfAdj = '<tr>'
						+ '<td style="text-align:center">' + jsonObject.viewMethodsOfAdjData[i].vendorName + '</td>'
						+ '<td style="text-align:center">' + jsonObject.viewMethodsOfAdjData[i].poId + '</td>'
						+ '<td style="text-align:right">' + amountFormatter(parseFloat(jsonObject.viewMethodsOfAdjData[i].paidAmount).toFixed(2)) + '</td>'
						+ '</tr>';

					$("#methodOfAdjTableList").append(newMethodOfAdj);

				}
				$("#bulkCustAmount").val(amountFormatter(jsonObject.viewMethodsOfAdjData[0].paidAmount));
				$('input[name="CashChequeOrOnline"][value="' + jsonObject.viewMethodsOfAdjData[0].paymentMode + '"]').prop('checked', true);
				var paymentMode = jsonObject.viewMethodsOfAdjData[0].paymentMode;
				$('.bankDiv, .chequeDiv, .upiDiv').hide();
				if (paymentMode === 'Cash') {
					$("#payRemarks").val(jsonObject.viewMethodsOfAdjData[0].payRemarks);
				} else if (paymentMode === 'Cheque') {
					$('.chequeDiv').show();
					$("#chequeBankName").val(jsonObject.viewMethodsOfAdjData[0].chequeBankName);
					$("#chequeBankBranch").val(jsonObject.viewMethodsOfAdjData[0].chequeBankBranch);
					$("#chequeAccountNumber").val(jsonObject.viewMethodsOfAdjData[0].chequeAccountNumber);
					$("#chequeNo").val(jsonObject.viewMethodsOfAdjData[0].chequeNo);
					$("#payRemarks").val(jsonObject.viewMethodsOfAdjData[0].payRemarks);
				} else if (paymentMode === 'Online') {
					$('.bankDiv').show();
					$("#bankSelect").val(jsonObject.viewMethodsOfAdjData[0].bankSelect);
					$("#transactionNumber").val(jsonObject.viewMethodsOfAdjData[0].transactionNumber);
					$("#receiverBankName").val(jsonObject.viewMethodsOfAdjData[0].receiverBankName);
					$("#receiverBankBranch").val(jsonObject.viewMethodsOfAdjData[0].receiverBankBranch);
					$("#receiverIfscCode").val(jsonObject.viewMethodsOfAdjData[0].receiverIfscCode);
					$("#receiverAccountNumber").val(jsonObject.viewMethodsOfAdjData[0].receiverAccountNumber);
					$("#payRemarks").val(jsonObject.viewMethodsOfAdjData[0].payRemarks);
				} else if (paymentMode === 'Upi') {
					$('.upiDiv').show();
					$("#onlineUpiID").val(jsonObject.viewMethodsOfAdjData[0].onlineUpiID);
					$("#receiverOnlineUpiID").val(jsonObject.viewMethodsOfAdjData[0].receiverOnlineUpiID);
					$("#receiverUpiTransactionID").val(jsonObject.viewMethodsOfAdjData[0].receiverUpiTransactionID);
					$("#payRemarks").val(jsonObject.viewMethodsOfAdjData[0].payRemarks);
				}
			}
		}
	})
}


function viewFilteredData() {

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();

	agGrid.simpleHttpRequest({
		url: "view-paymentsreceived-through-ajax?fromdate=" + fromDate + "&todate=" + toDate
	}).then(function(data) {
		$('.loader').hide();
		var len = data.length;
		$('#totalCandidate').find('span').html(len);
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