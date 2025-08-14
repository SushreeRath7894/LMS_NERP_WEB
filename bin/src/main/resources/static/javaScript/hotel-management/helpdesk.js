$(document).ready(function() {

	$("#serviceGrid").hide();
	$("#amenitiesGrid").hide();

	const gridDiv = document.querySelector('#appraisalGrid');
	new agGrid.Grid(gridDiv, gridOptionsDeg);
	gridOptionsDeg.api.setRowData([]);


	const gridDiv2 = document.querySelector('#serviceGrid');
	new agGrid.Grid(gridDiv2, gridOptionsSer);
	gridOptionsSer.api.setRowData([]);

	const gridDiv3 = document.querySelector('#amenitiesGrid');
	new agGrid.Grid(gridDiv3, gridOptionsAme);
	gridOptionsAme.api.setRowData([]);


	$("#roomNo,#ticketType,#assignTo,#roomNoSer,#serviceType,#assignToSer,#itemName,#roomNoAme,#serviceTypeAme,#assignToAme,#itemNameAme,#ticketSubCategory,#tctCat,#ticketPriorityAction,#itemCatSer").select2({
		placeholder: "Select ",
		allowClear: true
	});

	const userName = $('#userName').val();
	$("#receivedBy").val(userName);
	$("#receivedBySer").val(userName);
	$("#receivedByAme").val(userName);

	getCategory('');

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
		+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").html(tbl);

	var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
	var today = new Date();
	function formatDate(date, format) {
		let day = String(date.getDate()).padStart(2, '0');
		let month = String(date.getMonth() + 1).padStart(2, '0');
		let year = date.getFullYear();

		switch (format) {
			case "d-m-Y": return `${day}-${month}-${year}`;
			case "m-d-Y": return `${month}-${day}-${year}`;
			case "Y-m-d": return `${year}-${month}-${day}`;
			default: return `${day}-${month}-${year}`;
		}
	}

	var formattedDate = formatDate(today, dateFormat);

	$('#serviceDate').val(formattedDate);
	$('#amenitiesDate').val(formattedDate);
	$('#currentDate').val(formattedDate);


	$("#toDateCalendar3").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		maxDate: today,
		value: formattedDate
	}).on("change", function() {
		$('#serviceDate').val($(this).val());
	});

	$('#serviceDate').blur(function() {
		$("#toDateCalendar3").val($(this).val());
	});


	$("#toDateCalendar4").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		maxDate: today,
		value: formattedDate
	}).on("change", function() {
		$('#amenitiesDate').val($(this).val());
	});

	$('#amenitiesDate').blur(function() {
		$("#toDateCalendar4").val($(this).val());
	});


	ShowAgGrid('all');
	getRoomDetails();

	const mainActiveTab = $(".nav-link.active")[0];
	if (mainActiveTab) {
		handleTabClick(mainActiveTab);
	}
	const ticketActiveTab = $(".header-tab .nav-link.active")[0];
	if (ticketActiveTab) {
		getAllTickets(ticketActiveTab);
	}
	const now = new Date();
	let hours = now.getHours();
	const minutes = now.getMinutes().toString().padStart(2, '0');
	const ampm = hours >= 12 ? 'PM' : 'AM';

	hours = hours % 12;
	hours = hours ? hours : 12;
	const formattedHours = hours.toString().padStart(2, '0');

	const currentTime = `${formattedHours}:${minutes} ${ampm}`;

	$("#currentTime").val(currentTime);


});


let tabId = "";
function handleTabClick(element) {
	const tabTarget = element.getAttribute('data-bs-target');
	tabId = tabTarget.replace('#', '');
	
	console.log("tabid-->" , tabId);
	if (tabId === "complaints") {
		$("#serviceGrid").hide();
		$("#amenitiesGrid").hide();
		$("#appraisalGrid").show();
		$('button[data-bs-target="#all"]').trigger('click');
		//ShowAgGrid(targetedId);
	} else if (tabId === "services") {
		$("#serviceGrid").show();
		$("#appraisalGrid").hide();
		$("#amenitiesGrid").hide();
		$('button[data-bs-target="#all"]').trigger('click');
		//getAllServicesTypesDetails(targetedId);
	} else if (tabId === "amenities") {
		$("#serviceGrid").hide();
		$("#appraisalGrid").hide();
		$("#amenitiesGrid").show();
		$('button[data-bs-target="#all"]').trigger('click');
		//getAllServicesTypesDetails(targetedId);
	}
	else if (tabId === "B2B") {
		$('button[data-bs-target="#all"]').trigger('click');
	}
	else if (tabId === "B2c") {
		$('button[data-bs-target="#all"]').trigger('click');
	}
}

let targetedId;
function getAllTickets(btn) {
	const target = btn.getAttribute('data-bs-target');
	targetedId = target.substring(1);

	if (tabId == 'complaints') {
		ShowAgGrid(targetedId);
	} else if (tabId == 'services') {
		getAllServicesTypesDetails(targetedId);
	} else if (tabId == 'amenities') {
		getAllServicesTypesDetails(targetedId);
	}

}


function getCategory(cat) {
	$("#tctCat").empty();
	$("#ticketSubCategory").empty();
	var selectedValue = $("#ticketType").val();
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "view-department-view-tk-getcategory?id=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#tctCat").append(option);
				//$("#ticketSubCategory").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code", response.body[i].code);
					//$("#tctCat option:selected").attr("code");
					$("#tctCat").append(option);

				}
				$(".loader").hide();
				if (cat != '') {
					$("#tctCat").val(cat);
				}
			}
		},
		error: function(e) {
			$(".loader").hide();
		}
	});

}

function getRoomDetails() {
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "get-all-service-room-details",
		success: function(response) {
			if (response.code == "success") {
				// Clear and add default option
				$("#roomNoSer").empty().append('<option value="">Select</option>');

				for (var i = 0; i < response.body.length; i++) {
					var item = response.body[i];
					var option = $("<option></option>");
					option.val(item.key);
					option.html(item.name);
					option.attr("data-guest", item.code);      // Guest Name
					option.attr("data-custid", item.data);      // Customer ID
					$("#roomNoSer").append(option);
				}

				$(".loader").hide();
			}
		},
		error: function(e) {
			$(".loader").hide();
		}
	});
}
$(document).on("change", "#roomNoSer", function() {
	var selectedOption = $(this).find(":selected");
	var selectedGuest = selectedOption.data("guest");
	var selectedCustId = selectedOption.data("custid");

	$("#guestNameSer").val(selectedGuest || "");
	$("#custIdSer").val(selectedCustId || ""); // Or .val() if it's an input
});


function getCategoryAme(cat) {
	$("#tctCat").empty();
	$("#ticketSubCategory").empty();
	var selectedValue = $("#ticketTypeAme").val();
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "view-department-view-tk-getcategory?id=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#serviceTypeAme").append(option);
				//$("#ticketSubCategory").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code", response.body[i].code);
					//$("#tctCat option:selected").attr("code");
					$("#serviceTypeAme").append(option);

				}
				$(".loader").hide();
				if (cat != '') {
					$("#serviceTypeAme").val(cat);
				}
			}
		},
		error: function(e) {
			$(".loader").hide();
		}
	});

}

function addComplaints() {
	gridOptionsDeg.api.deselectAll();
	$("#roomNo").val('').trigger('change');
	$("#tctCat").val('').attr("disabled", false).trigger('change');
	$("#ticketPriorityAction").val('').trigger('change');
	$("#description").val('').attr("disabled", false);
	$("#docthead").val('');
	$("#doctbodyData").empty();
	$("#actionTaken").val('');
	$("#assignTo").val('').trigger('change');
	$("#ticketId").val('');

	$("#saveComplaints").removeClass("d-none");
	$("#cancelComplaints").removeClass("d-none");
	$("#editComplaints").addClass("d-none");
	$("#addComplaints").addClass("d-none");


	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
		+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").html(tbl);

}

function cancelComplaints() {
	//$("#addComplaints").removeClass("d-none");
	ShowAgGrid(targetedId);
}

function editComplaints() {
	$("#saveComplaints").removeClass("d-none");
	$("#cancelComplaints").removeClass("d-none");
	$("#editComplaints").addClass("d-none");
	$("#addComplaints").addClass("d-none");
	$("#reAssign").addClass("d-none");


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
	var extension = fileName.split(".").pop();
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");


	console.log("extension is-->", extension);
	if (extension == 'pdf' || extension == 'jpg' || extension == 'png' || extension == 'xls' || extension == 'doc' || extension == 'webp' || extension == 'xlsx' || extension == 'docx' || extension == 'txt') {
		window.st = 1;
	} else {
		toastr.error("Invalid file type! Please upload a valid document file. Supported formats: PDF, JPG, PNG, XLS, DOC, WEBP, XLSX, DOCX, TXT.");
		window.st = 0;

		var lengthOfTableRow1 = 0;
		$("#docTbl > #doctbodyData > tr").each(function() {
			lengthOfTableRow1 = lengthOfTableRow1 + 1;
		})
		var id = $("#dltValue").val();
		$("#" + id).closest('tr').remove();
		closeDeleteConfirm();
		if (lengthOfTableRow1 == 1) {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
				+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}
	}
	if (extension != null && extension != "") {
		$("#uploadHidden_" + counter).val('');
	}
	var LightImg = "";

	if (["jpg", "png", "jpeg", "webp"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-image img-color'></i></a></span>";
	} else if (extension.toLowerCase() === "pdf") {
		LightImg = "<span class='uploadicon position-l bdr-n'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-pdf img-color'></i></a></span>";
	} else if (["xls", "xlsx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-excel img-color'></i></a></span>";
	} else if (["doc", "docx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-word img-color'></i></a></span>";
	} else if (["mp4", "mov"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-video img-color'></i></a></span>";
	} else if (["mp3", "wav", "aac"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-audio img-color'></i></a></span>";
	} else {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file img-color'></i></a></span>";
	}

	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm(0)'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);
	$("#dltImage_" + counter).addClass("custom-file-delete");
	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");
	$("#dltImage_0").show();
}

function openDeleteConfirm(index) {
	$("#uploadedBillDiv_" + index).html("");
	$("#uploadHidden_" + index).val("");
	$("#uploadDoc_" + index).val("");
	$("#imageName_" + index).text("");

	$("#dltImage_" + index).hide();
	$("#uploadFor_" + index).show();
	$("#clickImg_" + index).addClass("ti-plus").removeClass("ti-pencil");

}



function saveComplaints() {

	var obj = {};
	var valid = true;
	var uploadList = [];
	var datas = [];
	if (valid) {
		$("#doctbodyData > tr").each(
			function(i) {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var fileNametxt = $(this).find('.imageName').text();
				var data = [];
				var x = [];
				if (fileNametxt != '' && fileNametxt != 'undefined' && fileNametxt != null) {
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
					}
					uploadData = {};
					uploadData['documnentName'] = $("#docnoid_" + i).val();
					uploadData['documentFile'] = x;
					uploadData['fileName'] = $(this).find('.imageName').text();
					uploadData['documentURL'] = $(this).find(".uploadHidCls").val();
					uploadData['imageNameEdit'] = $(this).find(
						".uploadHidCls").val();
					uploadList.push(uploadData);
				}

			});

		obj.ticketId = $("#ticketId").val();
		obj.empId = $("#empId").val();
		obj.date = $("#currentDate").val();
		obj.empName = $("#empName").val();
		obj.dept = $("#tctCat option:selected").attr("data-code");
		obj.ticketType = $("#ticketType").val();
		obj.ticketCategory = $('#tctCat').val();
		obj.ticketSubCategory = $("#ticketSubCategory").val();
		obj.assetList = $("#assetList").val();
		obj.ticketPriority = $("#ticketPriorityAction").val();
		obj.ticketSource = $("#ticketSource").val();
		obj.description = window.btoa($("#description").val());
		obj.locType = $("#locType").val();
		obj.latitude = $("#latitude").val();
		obj.longitude = $("#longitude").val();
		obj.empAddress = $("#roomNo").val();
		obj.actionTaken = window.btoa($("#actionTaken").val());
		obj.assignTo = $("#assignTo").val();
		obj.assignType = '3';
		obj.currentTime = $("#currentTime").val();


		obj.documentList = uploadList;

		if (obj.ticketType == null || obj.ticketType == "") {
			toastr.error('Ticket Type Required');
			return;
		}
		if (obj.ticketCategory == null || obj.ticketCategory == "") {
			toastr.error('Complaint Types Required');
			return;
		}

		if (obj.ticketPriority == null || obj.ticketPriority == "") {
			toastr.error('Priority Required');
			return;
		}

		if (obj.description == null || obj.description == "") {
			toastr.error('Description Required');
			return;
		}
		if (obj.assignTo == null || obj.assignTo == "") {
			toastr.error('Assigned To Employee Requuired !');
			return;
		}


		datas.push(obj);
		if (valid) {
			setTimeout(function() {
				saveTicket(datas);
			}, 1000)
		}
	}

}

function saveTicket(data) {
	console.log("datadata====", data)
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-department-view-tk-save-data",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {

				toastr.success(response.message);
				$('.loader').hide();
				$("body").removeClass("overlay");
				ShowAgGrid(targetedId);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something Went Wrong');
			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#messageParagraph").text("Something Went Wrong");
			$("#msgOkModal").removeClass("btn1");
			$("#msgOkModal").addClass("btn3");
			$("#msgModal").modal('show');
		}
	})
}


function ShowAgGrid(id) {

	agGrid.simpleHttpRequest({
		url: "view-ticket-by?id=" + id,
	}).then(function(data) {
		const jsonData = JSON.parse(data.body[0]);
		const ticketData = jsonData.viewTicket;

		if (ticketData === null || ticketData === "") {
			addComplaints();
			gridOptionsDeg.api.setRowData([]);
			$("#saveComplaints").removeClass("d-none");
			$("#cancelComplaints").removeClass("d-none");
			$("#editComplaints").addClass("d-none");
			$("#addComplaints").addClass("d-none");
			$("#reAssignSav").addClass("d-none");


		} else {
			const allData = ticketData.reverse();
			gridOptionsDeg.api.setRowData(allData);
			var firstRowNode = gridOptionsDeg.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		}
	});

}

function editTicket(ticketId) {
	$(".formValidation").remove();
	$(".loader").show();

	agGrid.simpleHttpRequest({
		url: 'view-department-view-tk-edit?id=' + ticketId
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.editTicket;


		$("#ticketId").val(ticketId);
		$("#editTktId").val(allData[0].ticketId);
		$("#empId").val(allData[0].empId);
		$("#currentDate").val(allData[0].tktDate);
		$("#empName").val(allData[0].empName).trigger('change');
		$("#ticketType").val(allData[0].tktType);
		getCategory(allData[0].tktCategory);
		$("#ticketPriorityAction").val(allData[0].tktPriority).trigger('change');
		$("#ticketSource").val(allData[0].ticketSource);
		$("#description").val(window.atob(allData[0].tktDesc));
		$("#locType").val(allData[0].tktLocType);
		$("#latitude").val(allData[0].tktLatitude);
		$("#longitude").val(allData[0].tktLongitude);
		$("#roomNo").val(allData[0].tktAddress).trigger('change');
		$("#assignTo").val(allData[0].assignToEmp).trigger('change');
		$("#assetList").val(allData[0].assetid);
		$("#assetList").val(allData[0].assetid);
		$("#actionTaken").val(window.atob(allData[0].actionTaken));
		$("#doctbodyData").empty();
		var documentList = allData[0].document;

		if (documentList && documentList.length > 0) {
			for (var i = 0; i < documentList.length; i++) {
				var file = documentList[i].fileName.split('/').pop();
				var extension = file.split('.').pop().toLowerCase();
				var iconClass = 'fa-file';
				var iconColor = 'gray';

				if (["jpg", "jpeg", "png", "webp"].includes(extension)) {
					iconClass = 'fa-file-image';
					iconColor = 'blue';
				} else if (extension === "pdf") {
					iconClass = 'fa-file-pdf';
					iconColor = 'red';
				} else if (["xls", "xlsx"].includes(extension)) {
					iconClass = 'fa-file-excel';
					iconColor = 'green';
				} else if (["doc", "docx"].includes(extension)) {
					iconClass = 'fa-file-word';
					iconColor = 'blue';
				}

				var iconName = documentList[i].docurl ? "ti-pencil" : "ti-plus";

				var LightImg = "<a class='example-image-link' href='" + documentList[i].docurl + "' target='_blank' title='" + file + "'><i class='fa " + iconClass + " fa fa-file-image img-color' ></i></a>";

				var tbl = '<tr>'
					+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check_' + i + '"><label for="check_' + i + '"></label></td>'
					+ '<td style="display:none"><div class="form-group"><select class="form-control documentclss" id="docid_' + i + '" onblur="removeValid(event);"><option value="">Select</option></select></div></td>'
					+ '<td><div class="form-group"><input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclss" id="docnoid_' + i + '"></div></td>'
					+ '<td class="d-flex align-items-center">'
					+ '<div class="control-group position-r">'
					+ '<label class="custom-file-upload" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"><i class="' + iconName + '" id="clickImg_' + i + '"></i></label>'
					+ '<div class="controls"><input type="file" class="document" id="uploadDoc_' + i + '" name="userImage" onchange="saveMultiFile(event)"></div>'
					+ '</div>'
					+ '<input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documentList[i].docurl + '">'
					+ '<div id="uploadedBillDiv_' + i + '" align="center" class="uploadedBillCls">' + LightImg + '</div>'
					+ '<div id="imageName_' + i + '" class="imageNameCus">' + file + '</div>'
					+ '<input type="hidden" id="editId_' + i + '" value="' + documentList[i].ticketId + '">'
					+ '<div id="dltImage_' + i + '" class="custom-file-delete">'
					+ "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm(" + i + ")'></i>"
					+ '</div>'
					+ '</td>'
					+ '</tr>';

				$("#doctbodyData").append(tbl);
				$("#dltImage_" + i).show();
			}
		} else {
			var emptyTbl = '<tr>'
				+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check_0"><label for="check_0"></label></td>'
				+ '<td style="display:none"><div class="form-group"><select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"><option value="">Select</option></select></div></td>'
				+ '<td><div class="form-group"><input type="text" class="form-control docNoclss" id="docnoid_0"></div></td>'
				+ '<td class="d-flex gap-2 align-items-center">'
				+ '<div class="control-group"><label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"><i class="ti-plus" id="clickImg_0"></i></label>'
				+ '<div class="controls"><input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)"></div></div>'
				+ '<input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div>'
				+ '<div id="imageName_0" class="imageName"></div>'
				+ '<div id="dltImage_0" class="custom-file-delete">'
				+ "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm(0)'></i></div>"
				+ '<input type="hidden" id="editId_0">'
				+ '</td>'
				+ '</tr>';
			$("#doctbodyData").append(emptyTbl);
			$("#dltImage_0").show();
		}

		$('#infoNew').show();
		$('#infoSave').hide();
		$('#infoCancel').hide();

		$('.custom-file-delete').show();

		$('#currentDate, #empName, #tctCat, #ticketSubCategory, #assetList, #ticketPriority, #ticketSource, #description, #locType, #latitude, #longitude, #assetlocation, #empAddress, #saveAttachmentBtn, .docNoclss')
			.attr('disabled', true);
	});
}




function rowSelectDesig() {
	var selectedRows = gridOptionsDeg.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const datas = selectedRows[0];
		const ticketId = (datas.tcktNo);
		editTicket(ticketId);
		const status = (datas.isClosed);
		if (status == "ASSIGNED") {
			$("#saveComplaints").addClass("d-none");
			$("#cancelComplaints").addClass("d-none");
			$("#editComplaints").addClass("d-none");
			$("#addComplaints").removeClass("d-none");
			$("#reAssign").addClass("d-none");
			$("#reAssignSav").addClass("d-none");

		} else if (status == "OPEN") {
			$("#saveComplaints").addClass("d-none");
			$("#cancelComplaints").addClass("d-none");
			$("#editComplaints").removeClass("d-none");
			$("#addComplaints").removeClass("d-none");
			$("#reAssign").addClass("d-none");
			$("#reAssignSav").addClass("d-none");

		} else if (status == "RE-OPEN") {
			$("#saveComplaints").addClass("d-none");
			$("#cancelComplaints").addClass("d-none");
			$("#editComplaints").addClass("d-none");
			$("#addComplaints").removeClass("d-none");
			$("#reAssign").removeClass("d-none");
			$("#reAssignSav").addClass("d-none");


		} else {
			$("#saveComplaints").addClass("d-none");
			$("#cancelComplaints").addClass("d-none");
			$("#editComplaints").addClass("d-none");
			$("#addComplaints").removeClass("d-none");
			$("#reAssign").addClass("d-none");
			$("#reAssignSav").addClass("d-none");
		}

	} else {
		$("#saveComplaints").removeClass("d-none");
		$("#reAssignSav").addClass("d-none");
		$("#cancelComplaints").removeClass("d-none");
		$("#editComplaints").addClass("d-none");
		$("#addComplaints").addClass("d-none");
		$("#reAssign").addClass("d-none");
		$("#editComplaints").addClass("d-none");
		$("#roomNo").val('').trigger('change');
		$("#tctCat").val('').attr("disabled", false).trigger('change');
		$("#ticketPriorityAction").val('').trigger('change');
		$("#description").val('').attr("disabled", false);
		$("#docthead").val('');
		$("#doctbodyData").empty();
		$("#actionTaken").val('');
		$("#assignTo").val('').trigger('change');
		$("#ticketId").val('');

		$("#saveComplaints").removeClass("d-none");
		$("#cancelComplaints").removeClass("d-none");
		$("#editComplaints").addClass("d-none");
		$("#addComplaints").addClass("d-none");


		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
			+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
			+ '</tr>';
		$("#doctbodyData").html(tbl);
	}

}

const columnDefsDeg = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: 'Ticket',
		field: "tcktNo",
		flex: 1
	},
	{
		headerName: 'User Name',
		field: "raisedby",
		flex: 1
	},
	{
		headerName: "Status",
		field: "isClosed",
		width: 130,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			const status = params.data.isClosed || "UNKNOWN";
			let badgeClass = "";

			switch (status) {
				case "OPEN":
					badgeClass = "badge-open";
					break;
				case "ASSIGNED":
					badgeClass = "badge-assigned";
					break;
				case "RE-ASSIGNED":
					badgeClass = "badge-reassigned";
					break;
				case "IN-PROGRESS":
					badgeClass = "badge-progress";
					break;
				case "RE-OPEN":
					badgeClass = "badge-reopen";
					break;
				case "CLOSE":
					badgeClass = "badge-closed";
					break;
				default:
					badgeClass = "badge-default";
			}

			return `<span class="badge ${badgeClass}">${status}</span>`;
		}
	},
	{
		headerName: "Date",
		field: "riseDate",
		flex: 1
	}, {
		headerName: "Description",
		field: "desc",
		flex: 1,
		valueGetter: params => {
			const descs = params.data.desc;
			return (descs && descs !== 'null') ? window.atob(descs) : '';
		}
	}, {
		headerName: "Created By",
		field: "createdBy",
		flex: 1
	}
];;

// Define grid options
const gridOptionsDeg = {
	columnDefs: columnDefsDeg,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectDesig
};




const columnDefsSer = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Service Id",
		field: "serviceId",
		flex: 1

	}, {
		headerName: "Room No",
		field: "roomId",
		flex: 1

	}, {
		headerName: "Date",
		field: "sevicedate",
		flex: 1

	}, {
		headerName: "Services",
		field: "bookingType",
		flex: 1

	},
	{
		headerName: "Status",
		field: "status",
		flex: 1
	}];

// Define grid options
const gridOptionsSer = {
	columnDefs: columnDefsSer,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectService
};



const columnDefsAme = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Service Id",
		field: "serviceId",
		flex: 1

	}, {
		headerName: "Room No",
		field: "roomId",
		flex: 1

	}, {
		headerName: "Date",
		field: "sevicedate",
		flex: 1

	}, {
		headerName: "Services",
		field: "bookingType",
		flex: 1

	},
	{
		headerName: "Status",
		field: "status",
		flex: 1
	}];

// Define grid options
const gridOptionsAme = {
	columnDefs: columnDefsAme,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectAme
};



const columnDefsAmeChild = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Item Id",
		field: "itemId",
		flex: 1

	},
	{
		headerName: "Item",
		field: "itemName",
		flex: 1

	}, {
		headerName: "Quantity",
		field: "quantity",
		flex: 1

	}, {
		headerName: "charges",
		field: "charges",
		flex: 1

	}, {
		headerName: "Tax",
		field: "taxes",
		flex: 1

	},
	{
		headerName: "Toatal",
		field: "total",
		flex: 1

	}, {
		headerName: "Remarks",
		field: "serveTime",
		flex: 1
	}];

// Define grid options
const gridOptionItem = {
	columnDefs: columnDefsAmeChild,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100
	},
	//onSelectionChanged: rowSelectItem
};






let globalItemList = [];
function getAllProductList(id) {

	let val = $("#" + id).val();

	agGrid.simpleHttpRequest({
		url: 'get-all-items-details?id=' + val,
	}).then(function(data) {
		if (data.code === 'success') {
			globalItemList = data.body;
		} else {
			globalItemList = [];
		}
	});
}

let newKraId = 0;
function addItemRow() {
	const id = $("#serviceType").val();
	if (id == null || id === "") {
		toastr.error("Select Service Type First !");
		return;
	}

	let dataset = [];
	$("#med-table tbody tr.med-parent-class").each(function(index) {
		let a = {
			med_id: $(this).find('.kra-name').val(),
			dose: $(this).find('.quantity').val(),
			storeid: $(this).find('.storeid').val(),
			unitid: $(this).find('.unitid').val(),
			allocid: $(this).find('.allocid').val(),
			binid: $(this).find('.binid').val(),
			amount: $(this).find('.price').val(),
			linetotal: $(this).find('.totalAmount').val(),
			batch: $(this).find('.batch').val(),
			discount: $(this).find('.discount').val(),
			gstRate: $(this).find('.gstRate').val(),
			cgst: $(this).find('.cgst').val(),
			sgst: $(this).find('.sgst').val(),
		};
		dataset.push(a);
	});

	// Check all necessary fields
	for (let i = 0; i < dataset.length; i++) {
		const item = dataset[i];

		if (!item.med_id) {
			toastr.error("Item Required");
			return;
		}

		if (!item.dose) {
			toastr.error("Quantity Required");
			return;
		}
		if (!item.amount) {
			toastr.error("Price Required");
			return;
		}
		if (!item.gstRate) {
			toastr.error("GST Rate Required");
			return;
		}
		if (!item.cgst) {
			toastr.error("CGST Required");
			return;
		}
		if (!item.sgst) {
			toastr.error("SGST Required");
			return;
		}
		if (!item.linetotal) {
			toastr.error("Total Amount Required");
			return;
		}
	}

	const rows = document.querySelectorAll('#med-tbody tr');
	let c = 0;
	rows.forEach((row, index) => {
		const inputs = row.querySelectorAll('input, select, textarea');
		const classInp = row.querySelectorAll('.quantity,.discount');
		inputs.forEach(el => el.disabled = true);
		classInp?.forEach(el => el.disabled = false);
		c++;
	});

	let dropdownOptions = `<option></option>`;
	globalItemList.forEach(kra => {
		dropdownOptions += `<option value="${kra.key}" data-prod="${kra.id}" data-unit="${kra.code}" data-unitname="${kra.data}" data-gst="${kra.createdBy}">${kra.name}</option>`;
	});

	const newRow = $(` 
					    <tr class="med-parent-class" data-kra-id="${newKraId}">
					        <td style="text-align: center" class="slno-td">${c + 1}</td>
					        <td>
					            <select class="form-control kra-name kra-select2" onchange="setPriceAndGst(${newKraId})">
					                ${dropdownOptions}
					            </select>
					        </td>
					        <td><input type="text" class="form-control quantity" onkeyup="calculateLineTotal(${newKraId})"></td>
					        <td><input type="text" class="form-control price" readonly><input type="hidden" class="prodid">
					        <input type="hidden" class="itemUnit"></td>
					        <td><input type="text" class="form-control discount" onkeyup="calculateLineTotal(${newKraId})"></td>
					        <td><input type="text" class="form-control amount" readonly></td>
					        <td><input type="text" class="form-control gstRate" readonly></td>
					        <td><input type="text" class="form-control cgst" readonly></td>
					        <td><input type="text" class="form-control sgst" readonly></td>
					        <td class="d-none"><input type="text" class="form-control igst" readonly></td>
					        <td><input type="text" class="form-control totalAmount" readonly></td>
					        <td>
					            <button class="btn go-btn remove-kra mr-2" onclick="removeRow(${newKraId})">
					                <i class="fas fa-trash"></i>
					            </button>
					        </td>
					    </tr>
					`);


	$('#med-table tbody').append(newRow);

	newRow.find('.kra-select2').select2({
		placeholder: "Item",
		allowClear: true,
		width: '100%'
	});

	newKraId++;
}


function setPriceAndGst(i) {
	const kraId = document.querySelector(`tr[data-kra-id="${i}"] .kra-name`).value;
	const selectedItem = globalItemList.find(item => item.key === kraId);

	if (selectedItem) {
		const row = document.querySelector(`tr[data-kra-id="${i}"]`);
		row.querySelector(".price").value = selectedItem.orgName || 0;
		row.querySelector(".gstRate").value = selectedItem.createdBy || 0;
		row.querySelector(".prodid").value = selectedItem.id || '';
		row.querySelector(".itemUnit").value = selectedItem.code || '';

		calculateLineTotal(i);
	}
}



function calculateLineTotal(i) {
	const quantityEl = document.querySelector(`tr[data-kra-id="${i}"] .quantity`);
	const priceEl = document.querySelector(`tr[data-kra-id="${i}"] .price`);
	const cgstEl = document.querySelector(`tr[data-kra-id="${i}"] .cgst`);
	const sgstEl = document.querySelector(`tr[data-kra-id="${i}"] .sgst`);
	const amountEl = document.querySelector(`tr[data-kra-id="${i}"] .amount`);
	const totalAmountEl = document.querySelector(`tr[data-kra-id="${i}"] .totalAmount`);
	const gstRateEl = document.querySelector(`tr[data-kra-id="${i}"] .gstRate`);
	const stockqtyEl = document.querySelector(`tr[data-kra-id="${i}"] .stockqty`);
	const discountEl = document.querySelector(`tr[data-kra-id="${i}"] .discount`);

	let quantity = parseFloat(quantityEl?.value) || 0;
	let price = parseFloat(priceEl?.value) || 0;
	let gstRate = parseFloat(gstRateEl?.value) || 0;
	let discount = parseFloat(discountEl?.value) || 0;

	// ❗ Validate discount range
	if (discount < 0 || discount > 100) {
		toastr.error("Discount must be between 0 and 100%");
		discountEl.value = '';
		discount = 0;
	}

	if (stockqtyEl) {
		const availableQty = parseInt(stockqtyEl.value) || 0;
		if (quantity > availableQty) {
			cgstEl.value = '0.00';
			sgstEl.value = '0.00';
			amountEl.value = '0.00';
			totalAmountEl.value = '0.00';
			updateTotalAmount();
			return;
		}
	}

	if (quantity === 0) {
		cgstEl.value = '0.00';
		sgstEl.value = '0.00';
		amountEl.value = '0.00';
		totalAmountEl.value = '0.00';
		updateTotalAmount();
		return;
	}

	let lineTotal = quantity * price;
	let discountedAmount = lineTotal * (1 - discount / 100);
	amountEl.value = discountedAmount.toFixed(2);
	let gst = discountedAmount * (gstRate / 100);
	let cgst = gst / 2;
	let sgst = gst / 2;
	let totalWithGst = discountedAmount + gst;

	cgstEl.value = isNaN(cgst) ? '0.00' : cgst.toFixed(2);
	sgstEl.value = isNaN(sgst) ? '0.00' : sgst.toFixed(2);
	totalAmountEl.value = isNaN(totalWithGst) ? '0.00' : totalWithGst.toFixed(2);

	updateTotalAmount();
}





function updateTotalAmount() {
	let total = 0;
	$("#med-table tbody tr.med-parent-class").each(function() {
		let t = $(this).find('input.totalAmount').val();
		total += t ? parseFloat(t) : 0;
	});

	$("#grantTotalAmount").text(total.toFixed(2));
}

function removeRow(i) {
	document.querySelectorAll(`#child-tr-${i}`).forEach(tr => tr.remove());
	document.querySelectorAll(`tr[data-kra-id="${i}"]`).forEach(tr => tr.remove());

	updateTotalAmount();
}


function setGuestName(select) {
	const selectedOption = select.options[select.selectedIndex];

	if (selectedOption && selectedOption.value) {
		const parts = selectedOption.value.split('|');
		const guestName = parts[2];
		const custId = selectedOption.getAttribute('data-custid');

		document.getElementById('guestNameAme').value = guestName || '';

		// Set customer ID in a div or hidden input
		document.getElementById('custIdAme').innerText = custId || '';
	} else {
		document.getElementById('guestNameAme').value = '';
		document.getElementById('custIdAme').innerText = '';
	}
}



/*all function for amenities item details*/
let newKraIdAme = 0;

function addItemRowAme() {
	const id = $("#serviceTypeAme").val();
	if (!id) {
		toastr.error("Select Service Type First!");
		return;
	}

	let dataset = [];
	$("#med-table-ame tbody tr.med-parent-class-ame").each(function() {
		let a = {
			med_id: $(this).find('.kra-name').val(),
			dose: $(this).find('.quantity').val(),
			amount: $(this).find('.price').val(),
			discount: $(this).find('.discount').val(),
			gstRate: $(this).find('.gstRate').val(),
			cgst: $(this).find('.cgst').val(),
			sgst: $(this).find('.sgst').val(),
			linetotal: $(this).find('.totalAmount').val()
		};
		dataset.push(a);
	});

	for (let item of dataset) {
		if (!item.med_id) return toastr.error("Item Required");
		if (!item.dose) return toastr.error("Quantity Required");
		if (!item.amount) return toastr.error("Price Required");
		if (!item.gstRate) return toastr.error("GST Rate Required");
		if (!item.cgst) return toastr.error("CGST Required");
		if (!item.sgst) return toastr.error("SGST Required");
		if (!item.linetotal) return toastr.error("Total Amount Required");
	}

	const rows = document.querySelectorAll('#med-tbody-ame tr');
	let c = rows.length;

	rows.forEach((row) => {
		const inputs = row.querySelectorAll('input, select, textarea');
		const classInp = row.querySelectorAll('.quantity, .discount');
		inputs.forEach(el => el.disabled = true);
		classInp.forEach(el => el.disabled = false);
	});

	let dropdownOptions = `<option></option>`;
	globalItemList.forEach(kra => {
		dropdownOptions += `<option value="${kra.key}" data-prod="${kra.id}" data-unit="${kra.code}" data-unitname="${kra.data}" data-gst="${kra.createdBy}">${kra.name}</option>`;
	});

	const newRow = $(` 
					    <tr class="med-parent-class-ame" data-kra-id="${newKraIdAme}">
					        <td style="text-align: center" class="slno-td">${c + 1}</td>
					        <td>
					            <select class="form-control kra-name kra-select2" onchange="setPriceAndGstAme(${newKraIdAme})">
					                ${dropdownOptions}
					            </select>
					        </td>
					        <td><input type="text" class="form-control quantity" onkeyup="calculateLineTotalAme(${newKraIdAme})"></td>
					        <td>
					            <input type="text" class="form-control price" readonly>
					            <input type="hidden" class="prodid">
					            <input type="hidden" class="itemUnit">
					        </td>
					        <td><input type="text" class="form-control discount" onkeyup="calculateLineTotalAme(${newKraIdAme})"></td>
					        <td><input type="text" class="form-control amount" readonly></td>
					        <td><input type="text" class="form-control gstRate" readonly></td>
					        <td><input type="text" class="form-control cgst" readonly></td>
					        <td><input type="text" class="form-control sgst" readonly></td>
					        <td class="d-none"><input type="text" class="form-control igst" readonly></td>
					        <td><input type="text" class="form-control totalAmount" readonly></td>
					        <td>
					            <button class="btn go-btn remove-kra mr-2" onclick="removeRowAme(${newKraIdAme})">
					                <i class="fas fa-trash"></i>
					            </button>
					        </td>
					    </tr>
					`);

	$('#med-table-ame tbody').append(newRow);

	newRow.find('.kra-select2').select2({
		placeholder: "Item",
		allowClear: true,
		width: '100%'
	});

	newKraIdAme++;
}


function setPriceAndGstAme(i) {
	const kraId = document.querySelector(`tr[data-kra-id="${i}"] .kra-name`)?.value;
	const selectedItem = globalItemList.find(item => item.key === kraId);

	if (selectedItem) {
		const row = document.querySelector(`tr[data-kra-id="${i}"]`);

		row.querySelector('.price').value = selectedItem.orgName || "0.00";
		row.querySelector('.gstRate').value = selectedItem.createdBy || "0.00";

		row.querySelector('.prodid').value = selectedItem.id || "";
		row.querySelector('.itemUnit').value = selectedItem.code || "";

		calculateLineTotalAme(i);
	}
}


function calculateLineTotalAme(i) {
	const row = document.querySelector(`tr[data-kra-id="${i}"]`);
	const quantity = parseFloat(row.querySelector('.quantity')?.value || 0);
	const price = parseFloat(row.querySelector('.price')?.value || 0);
	const gstRate = parseFloat(row.querySelector('.gstRate')?.value || 0);
	const discountEl = row.querySelector('.discount');
	let discount = parseFloat(discountEl?.value || 0);

	if (discount < 0 || discount > 100) {
		toastr.error("Discount must be between 0 and 100%");
		discountEl.value = '';
		discount = 0;
	}

	const amountEl = row.querySelector('.amount');
	const cgstEl = row.querySelector('.cgst');
	const sgstEl = row.querySelector('.sgst');
	const totalAmountEl = row.querySelector('.totalAmount');

	if (quantity === 0) {
		cgstEl.value = '0.00';
		sgstEl.value = '0.00';
		amountEl.value = '0.00';
		totalAmountEl.value = '0.00';
		updateTotalAmountAme();
		return;
	}

	let lineTotal = quantity * price;
	let discountedAmount = lineTotal * (1 - discount / 100);
	let gst = discountedAmount * (gstRate / 100);
	let cgst = gst / 2;
	let sgst = gst / 2;
	let totalWithGst = discountedAmount + gst;

	amountEl.value = discountedAmount.toFixed(2);
	cgstEl.value = cgst.toFixed(2);
	sgstEl.value = sgst.toFixed(2);
	totalAmountEl.value = totalWithGst.toFixed(2);

	updateTotalAmountAme();
}

function updateTotalAmountAme() {
	let total = 0;
	$("#med-table-ame tbody tr.med-parent-class-ame").each(function() {
		const t = parseFloat($(this).find("input.totalAmount").val() || 0);
		total += t;
	});
	$("#grantTotalAmountAme").text(total.toFixed(2));
}

function removeRowAme(i) {
	document.querySelectorAll(`tr[data-kra-id="${i}"]`).forEach(tr => tr.remove());
	updateTotalAmountAme();
}



function saveServices() {

	let roomNo = $("#roomNoSer").val();
	let guestName = $("#custIdSer").val();
	let date = $("#serviceDate").val();
	let prodCategory = $("#serviceType").val();
	let description = $("#descriptionSer").val();
	let receivedBy = $("#receivedBy").val();
	let assignTo = $("#assignToSer").val();
	let billing = $("#billing").val();
	let totalPrice = $("#grantTotalAmount").text();

	if (!roomNo) {
		toastr.error("Room No Required");
		return;
	}
	if (!prodCategory) {
		toastr.error("Service type Required");
		return;
	}


	let items = [];

	$("#med-table tbody tr.med-parent-class").each(function() {
		const row = $(this);
		const kraId = row.attr("data-kra-id");
		const select = row.find(".kra-select2");
		const selectedOption = select.find(":selected");
		const itemKey = selectedOption.val();
		const itemName = selectedOption.text();

		const quantity = parseFloat(row.find(".quantity").val()) || 0;
		const price = parseFloat(row.find(".price").val()) || 0;
		const discount = parseFloat(row.find(".discount").val()) || 0;
		const amount = parseFloat(row.find(".amount").val()) || 0;
		const gstRate = parseFloat(row.find(".gstRate").val()) || 0;
		const cgst = parseFloat(row.find(".cgst").val()) || 0;
		const sgst = parseFloat(row.find(".sgst").val()) || 0;
		const totalAmount = parseFloat(row.find(".totalAmount").val()) || 0;

		// ✅ Get hidden fields
		const prodid = row.find(".prodid").val() || '';
		const itemUnit = row.find(".itemUnit").val() || '';

		if (!itemKey) {
			toastr.error("Please select a valid item.");
			return false;
		}
		if (quantity <= 0) {
			toastr.error(`Enter quantity for ${itemName}`);
			return false;
		}

		items.push({
			itemKey,
			itemName,
			quantity,
			price,
			discount,
			amount,
			gstRate,
			cgst,
			sgst,
			totalAmount,
			prodid,
			itemUnit
		});
	});
	console.log("Final Data to Submit: ", items);
	const obj = {
		roomNo,
		guestName,
		serviceDate: date,
		serviceType: prodCategory,
		description,
		receivedBy,
		assignTo,
		billing,
		itemList: items,
		subTotal: totalPrice,
		types: tabId,

	};
	console.log(obj);
	$('body').addClass('overlay');

	$('.loader').show();

	$.ajax({
		type: "POST",
		url: "save-all-hotel-services",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: resp => {
			$('body').removeClass('overlay');
			$('.loader').hide();

			if (resp.code === 'success') {
				toastr.success(resp.message);
			} else {
				toastr.error(resp.message || "Something went wrong");
			}
		},
		error: err => {
			console.log(err);
			toastr.error("Something Went Wrong");
			$('body').removeClass('overlay');
			$('.loader').hide();
		}
	});
}



function saveAmenities() {
	let roomNo = ($("#roomNoAme").val() || '').split('|')[0].trim();
	let guestName = $("#custIdAme").text();
	let date = $("#amenitiesDate").val();
	let prodCategory = $("#serviceTypeAme").val();
	let description = $("#descriptionAme").val();
	let receivedBy = $("#receivedByAme").val();
	let assignTo = $("#assignToAme").val();
	let billing = $("#billingAme").val();
	let totalPrice = $("#grantTotalAmountAme").text();

	if (!roomNo) {
		toastr.error("Room No Required");
		return;
	}
	if (!prodCategory) {
		toastr.error("Amenity type Required");
		return;
	}

	let items = [];

	$("#med-table-ame tbody tr.med-parent-class-ame").each(function() {
		const row = $(this);
		const kraId = row.attr("data-kra-id");
		const select = row.find(".kra-select2");
		const selectedOption = select.find(":selected");
		const itemKey = selectedOption.val();
		const itemName = selectedOption.text();

		const quantity = parseFloat(row.find(".quantity").val()) || 0;
		const price = parseFloat(row.find(".price").val()) || 0;
		const discount = parseFloat(row.find(".discount").val()) || 0;
		const amount = parseFloat(row.find(".amount").val()) || 0;
		const gstRate = parseFloat(row.find(".gstRate").val()) || 0;
		const cgst = parseFloat(row.find(".cgst").val()) || 0;
		const sgst = parseFloat(row.find(".sgst").val()) || 0;
		const totalAmount = parseFloat(row.find(".totalAmount").val()) || 0;

		const prodid = row.find(".prodid").val() || '';
		const itemUnit = row.find(".itemUnit").val() || '';

		if (!itemKey) {
			toastr.error("Please select a valid item.");
			return false;
		}
		if (quantity <= 0) {
			toastr.error(`Enter quantity for ${itemName}`);
			return false;
		}

		items.push({
			itemKey,
			itemName,
			quantity,
			price,
			discount,
			amount,
			gstRate,
			cgst,
			sgst,
			totalAmount,
			prodid,
			itemUnit
		});
	});

	console.log("Amenity Items to Submit: ", items);

	const obj = {
		roomNo,
		guestName,
		serviceDate: date,
		serviceType: prodCategory,
		description,
		receivedBy,
		assignTo,
		billing,
		itemList: items,
		subTotal: totalPrice,
		types: tabId,
	};

	console.log("Final Amenity Data: ", obj);

	$('body').addClass('overlay');
	$('.loader').show();

	$.ajax({
		type: "POST",
		url: "save-all-hotel-services",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: resp => {
			$('body').removeClass('overlay');
			$('.loader').hide();

			if (resp.code === 'success') {
				toastr.success(resp.message);
			} else {
				toastr.error(resp.message || "Something went wrong");
			}
		},
		error: err => {
			console.log(err);
			toastr.error("Something Went Wrong");
			$('body').removeClass('overlay');
			$('.loader').hide();
		}
	});
}



function getAllServicesTypesDetails(activeTab) {

	agGrid.simpleHttpRequest({
		url: "view-services-by?id=" + tabId + "&tabId=" + activeTab,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body[0]);
		var allData = jsonData.serviceTypes;
		console.log('allData===', allData);

		if (!allData || allData.length === 0) {
			gridOptionsSer.api.setRowData([]);
			gridOptionsAme.api.setRowData([]);

			$("#saveComplaints").removeClass("d-none");
			$("#cancelComplaints").removeClass("d-none");
			$("#editComplaints").addClass("d-none");
			$("#addComplaints").addClass("d-none");


			$("#cancelAmenities").show();
			$("#addAmenities").hide();
			$("#saveAmenities").show();
			addServices();

			addAmenities();
			return;

		}

		$("#saveServices").addClass("d-none");
		$("#addServices").removeClass("d-none");
		$("#cancelServices").addClass("d-none");


		$("#cancelAmenities").hide();
		$("#addAmenities").show();
		$("#saveAmenities").hide();

		if (tabId === 'services') {
			gridOptionsSer.api.setRowData(allData);
			const firstRowNode = gridOptionsSer.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) firstRowNode.setSelected(true);

		} else if (tabId === 'amenities') {
			gridOptionsAme.api.setRowData(allData);
			const firstRowNode = gridOptionsAme.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) firstRowNode.setSelected(true);

		}
	});
}



function rowSelectService() {

	var selectedRows = gridOptionsSer.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const datas = selectedRows[0];
		const id = (datas.serviceId);
		getAllServiceById(id);
	}
}

function rowSelectAme() {

	var selectedRows = gridOptionsAme.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const datas = selectedRows[0];
		const id = (datas.serviceId);
		getAllServiceById(id)
	}
}



function getAllServiceById(id) {

	$('#med-table-ame tbody').empty();
	$('#med-table tbody').empty();
	$("#grantTotalAmount").text('');
	$("#grantTotalAmountAme").text('');

	agGrid.simpleHttpRequest({
		url: "view-services-by-service-id?id=" + id,
	}).then(function(data) {
		const jsonData = JSON.parse(data.body[0]);
		const allData = jsonData.serviceTypes;
		console.log('allData=== is coming------', allData);

		const bookingType = allData[0].bookingType;
		const roomId = allData[0].roomId;
		$(".loader").show();

		if (bookingType === 'services') {
			$("#saveServices").addClass("d-none");
			$("#addServices").removeClass("d-none");
			$("#cancelServices").addClass("d-none");


			$("#roomNoSer").val(allData[0].roomId).trigger('change').prop('disabled', true);
			$("#serviceDate").val(allData[0].sevicedate).prop('disabled', true);
			$("#billing").val(allData[0].billing).prop('disabled', true);
			$("#serviceType").val(allData[0].servCate).trigger('change').prop('disabled', true);
			$("#descriptionSer").val(allData[0].details).prop('disabled', true);
			$("#receivedBy").val(allData[0].receivedBy).prop('disabled', true);
			$("#assignToSer").val(allData[0].assignedTo).prop('disabled', true);
			setTimeout(function() {
				$("#assignToSer").trigger('change');
			}, 200);

			$("#billing").val(allData[0].billing);

			$('#med-table tbody').empty();
			newKraId = 0;
			const itemList = allData[0].itemDetails || [];

			setTimeout(function() {
				itemList.forEach(item => {
					appendServiceItemRow(item);
				});
			}, 300);


		} else if (bookingType === 'amenities') {
			$("#cancelAmenities").hide();
			$("#editAmenities").hide();
			$("#saveAmenities").hide();

			$("#roomNoAme option").filter(function() {
				return this.value.startsWith(roomId + '|');
			}).prop('selected', true).prop('disabled', true);
			$("#roomNoAme").trigger('change').prop('disabled', true);
			$("#amenitiesDate").val(allData[0].sevicedate).prop('disabled', true);
			$("#serviceTypeAme").val(allData[0].servCate).trigger('change').prop('disabled', true);
			$("#descriptionAme").val(allData[0].details).prop('disabled', true);
			$("#receivedByAme").val(allData[0].receivedBy).prop('disabled', true);
			$("#assignToAme").val(allData[0].assignedTo).trigger('change').prop('disabled', true);
			$("#billingAme").val(allData[0].billing).prop('disabled', true);


			$('#med-table-ame tbody').empty();
			newKraIdAme = 0;

			const itemList = allData[0].itemDetails || [];

			setTimeout(function() {
				itemList.forEach(item => {
					appendAmenityItemRow(item);
				});
			}, 300);


		}
		$(".loader").hide();
	});
}


function appendServiceItemRow(item) {

	let dropdownOptions = `<option></option>`;
	globalItemList.forEach(kra => {
		const selected = kra.key === item.itemKey ? 'selected' : '';
		dropdownOptions += `<option value="${kra.key}" data-prod="${kra.id}" data-unit="${kra.code}" data-unitname="${kra.data}" data-gst="${kra.createdBy}" ${selected}>${kra.name}</option>`;
	});

	const newRow = $(`
				<tr class="med-parent-class" data-kra-id="${newKraId}">
					<td style="text-align: center" class="slno-td"></td>
					<td>
						<select class="form-control kra-name kra-select2" onchange="setPriceAndGst(${newKraId})">
							${dropdownOptions}
						</select>
					</td>
					<td><input type="text" class="form-control quantity text-end" value="${(item.quantity || 0).toFixed(2)}" onkeyup="calculateLineTotal(${newKraId})" readonly></td>
					<td><input type="text" class="form-control price text-end" value="${(item.price || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control discount text-end" value="${(item.discount || 0).toFixed(2)}" onkeyup="calculateLineTotal(${newKraId})" readonly></td>
					<td><input type="text" class="form-control amount text-end" value="${(item.amount || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control gstRate text-end" value="${(item.gstRate || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control cgst text-end" value="${(item.cgst || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control sgst text-end" value="${(item.sgst || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control totalAmount text-end" value="${(item.totalAmount || 0).toFixed(2)}" readonly></td>
	
					<td>
						<button class="btn go-btn remove-kra mr-2" onclick="removeRow(${newKraId})">
							<i class="fas fa-trash"></i>
						</button>
					</td>
				</tr>
			`);


	$('#med-table tbody').append(newRow);
	newRow.find('.kra-select2').select2({ placeholder: "Item", allowClear: true, width: '100%' });
	updateRowNumbers('#med-table tbody');
	newKraId++;
	updateTotalAmount();
}


function appendAmenityItemRow(item) {
	let dropdownOptions = `<option></option>`;
	globalItemList.forEach(kra => {
		const selected = kra.key === item.itemKey ? 'selected' : '';
		dropdownOptions += `<option value="${kra.key}" data-prod="${kra.id}" data-unit="${kra.code}" data-unitname="${kra.data}" data-gst="${kra.createdBy}" ${selected}>${kra.name}</option>`;
	});

	const newRow = $(`
				<tr class="med-parent-class-ame" data-kra-id="${newKraIdAme}">
					<td style="text-align: center" class="slno-td"></td>
					<td>
						<select class="form-control kra-name kra-select2" onchange="setPriceAndGstAme(${newKraIdAme})" readonly>
							${dropdownOptions}
						</select>
					</td>
					<td><input type="text" class="form-control quantity text-end" value="${(item.quantity || 0).toFixed(2)}" onkeyup="calculateLineTotal(${newKraId})" readonly></td>
					<td><input type="text" class="form-control price text-end" value="${(item.price || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control discount text-end" value="${(item.discount || 0).toFixed(2)}" onkeyup="calculateLineTotal(${newKraId})" readonly></td>
					<td><input type="text" class="form-control amount text-end" value="${(item.amount || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control gstRate text-end" value="${(item.gstRate || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control cgst text-end" value="${(item.cgst || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control sgst text-end" value="${(item.sgst || 0).toFixed(2)}" readonly></td>
					<td><input type="text" class="form-control totalAmount text-end" value="${(item.totalAmount || 0).toFixed(2)}" readonly></td>
	
					<td>
						<button class="btn go-btn remove-kra mr-2" onclick="removeRowAme(${newKraIdAme})">
							<i class="fas fa-trash"></i>
						</button>
					</td>
				</tr>
			`);

	$('#med-table-ame tbody').append(newRow);
	newRow.find('.kra-select2').select2({ placeholder: "Item", allowClear: true, width: '100%' });
	updateRowNumbers('#med-table-ame tbody');
	newKraIdAme++;
	updateTotalAmountAme();
}




function updateRowNumbers(tbodySelector) {
	$(tbodySelector).find('tr').each(function(index) {
		$(this).find('.slno-td').text(index + 1);
	});
}



function addServices() {
	gridOptionsSer.api.deselectAll();

	$("#cancelServices").removeClass("d-none");
	$("#editServices").addClass("d-none");
	$("#saveServices").removeClass("d-none");
	$("#addServices").addClass("d-none");


	$('#med-table tbody').empty();
	$("#grantTotalAmount").text('');


	$("#roomNoSer").val('').trigger('change').prop('disabled', false);
	$("#serviceType").val('').trigger('change').prop('disabled', false);
	$("#descriptionSer").val('').prop('disabled', false);
	$("#billing").val('').prop('disabled', false);
	$("#receivedBy").text(userName).prop('disabled', false);
	$("#assignToSer").val('').prop('disabled', false).trigger('change');

}

function addAmenities() {
	gridOptionsAme.api.deselectAll();
	$("#cancelAmenities").show();
	$("#editAmenities").hide();
	$("#saveAmenities").show();
	$("#addAmenities").hide();
	$('#med-table-ame tbody').empty();
	$("#grantTotalAmountAme").text('');
	$("#roomNoAme").val("");
	$("#roomNoAme option").prop("disabled", false);
	$("#roomNoAme").trigger('change').prop('disabled', false);
	$("#serviceTypeAme").val('').trigger('change').prop('disabled', false);
	$("#descriptionAme").val('').prop('disabled', false);
	$("#receivedByAme").text(userName).prop('disabled', false);
	$("#assignToAme").val('').trigger('change').prop('disabled', false);
	$("#billingAme").val('').prop('disabled', false);
}


function cancelServices() {
	getAllServicesTypesDetails(targetedId);
	$("#addServices").show();
}

function cancelAmenities() {
	getAllServicesTypesDetails(targetedId);
	$("#addAmenities").show();
}



function onQuickFilterChanged() {
	let searchValue = document.getElementById('quickFilter').value;

	gridOptionsDeg.api.setQuickFilter(searchValue);
	gridOptionsSer.api.setQuickFilter(searchValue);
	gridOptionsAme.api.setQuickFilter(searchValue);

	//$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());

	getMostClosestRow();
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;

	[gridOptionsDeg, gridOptionsSer, gridOptionsAme].forEach(grid => {
		grid.api.setQuickFilter(searchValue);

		grid.api.forEachNodeAfterFilter((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				grid.api.ensureIndexVisible(node.rowIndex);
			}
		});
	});
}

function resetBtn() {
	$("#quickFilter").val('');

	[gridOptionsDeg, gridOptionsSer, gridOptionsAme].forEach(grid => {
		grid.api.setQuickFilter('');
		grid.api.refreshCells({ force: true });

		setTimeout(() => {
			grid.api.forEachNode((node, index) => {
				if (index === 0) {
					node.setSelected(true);
					grid.api.ensureIndexVisible(node.rowIndex);
				}
			});
		}, 50);
	});
}

function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13 || event.key === "Backspace" || event.keyCode === 8) {
		onQuickFilterChanged();
	}
}


/*reassign*/
function reAssignTicket() {
	$("#saveComplaints").addClass("d-none");
	$("#cancelComplaints").removeClass("d-none");
	$("#editComplaints").addClass("d-none");
	$("#addComplaints").addClass("d-none");
	$("#reAssign").addClass("d-none");
	$("#reAssignSav").removeClass("d-none");
}

/*new function*/
function assignSave() {
	var obj = {};
	var valid = true;

	if (valid) {

		obj.assignType = '3';
		obj.ticketId = $("#ticketId").val();
		obj.date = $("#currentDate").val();
		obj.time = $("#currentTime").val();
		obj.dept = $("#departmentAction").val();
		obj.ticketPriority = $("#ticketPriorityAction").val();
		obj.empId = $("#assignTo").val();



		if (obj.date == null || obj.date == "") {
			toastr.error('Date Required');
			return;
		}
		if (obj.time == null || obj.time == "") {
			toastr.error('Time Required');
			return;
		}


		if (obj.ticketPriority == null || obj.ticketPriority == "") {
			toastr.error('Priority Required');
			return;
		}

		if (valid) {
			saveTicketAction(obj, obj.ticketId);
		}
	}
}

function saveTicketAction(data, id) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-department-view-tk-save-action-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success('Ticket Assigned Successfully');
				ShowAgGrid('All');
				onSelectionChange();
				viewAssignedResult(id);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something Went Wrong');

			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#messageParagraph").text("Something Went Wrong");
			$("#msgOkModal").removeClass("btn1");
			$("#msgOkModal").addClass("btn3");
			$("#msgModal").modal('show');
			$("#actionModal").modal('hide');
		}
	})
}