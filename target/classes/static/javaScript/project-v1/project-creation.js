let type = '';

$(() => {
	$('#status').select2();  // #status
	$('#projectType').select2();  // #projectType
});

$(document).ready(function () {

	const urlParams = new URLSearchParams(window.location.search);
	type = urlParams.get('id');

	if (type == null || type == 'null') {
		type = '';
	}

	$("#quickFilter").on("keydown", function (event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
	getAllProject();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData([]);

	var gridDiv4 = document.querySelector('#docDtlsGrid');
	new agGrid.Grid(gridDiv4, gridOptionsDocumrntDtls);
	gridOptionsDocumrntDtls.api.setRowData("");

	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function () {
		$("#date").val($(this).val());
	});

	$("#save").click(function () {
		saveProject();
	});

	/*$("#editBtn").prop("disabled", true);
	$("#docSaveBtn").prop("disabled", true);
	$("#cancelBtn").prop("disabled", true);
	$("#deleteBtn").prop("disabled", true);
	$("#save").prop("disabled", true);*/

	//$("#addDocBtn").prop("disabled", true);
	$("#editBtn").addClass('d-none');
	$("#docSaveBtn").addClass('d-none');
	$("#cancelBtn").addClass('d-none');
	$("#deleteBtn").addClass('d-none');
	$("#save").addClass('d-none');
});

function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}

function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}


// Function to add a new document row
function addDocument() {
	/*$("#docSaveBtn").prop("disabled", false);
	$("#cancelBtn").prop("disabled", false);*/

	$("#addDocBtn").addClass('d-none');
	$("#docSaveBtn").removeClass('d-none');
	$("#cancelBtn").removeClass('d-none');


	const toggleSection = $("#toggleSection");
	const docDtlsGrid = $("#docDtlsGrid");
	dynamicColumn();

	if (toggleSection.css("display") === "none" || toggleSection.css("display") === "") {
		toggleSection.css("display", "block");
		docDtlsGrid.css("display", "none");
		docDtlsGrid.parent().addClass('d-none');
	}

}

let documentCount = 1;

function addMore1() {

	documentCount++;


	var tbl = '<tr id="docRow_' + documentCount + '">'
		+ '<td><div class="form-group"><input type="text" value="" class="form-control docNoclss" id="docnoid_' + documentCount + '"></div></td>'
		+ '<td class="d-flex gap-2 align-items-center"><div class="control-group">'
		+ '<label class="custom-file-upload m-0" for="uploadDoc_' + documentCount + '" id="uploadFor_' + documentCount + '">'
		+ '<i class="ti-plus" id="clickImg_' + documentCount + '"></i></label>'
		+ '<div class="controls"><input type="file" class="document" id="uploadDoc_' + documentCount + '" name="userImage" onchange="saveMultiFile(event, ' + documentCount + ')" /></div></div>'
		+ '<input type="hidden" id="uploadHidden_' + documentCount + '" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_' + documentCount + '" align="center" class="uploadedBillCls order-3"></div>'
		+ '<div id="imageName_' + documentCount + '" class="imageName"></div>'
		+ '<input type="hidden" id="editId_' + documentCount + '" value="">'
		+ '<div id="dltImage_' + documentCount + '" class="custom-file-delete m-0 p-0 h-auto">'
		+ '<i class="ti-close rmv1" onclick="openDeleteConfirm(' + documentCount + ')"></i></div></td>'
		+ '</tr>';


	$("#doctbodyData").append(tbl);
}


function openDeleteConfirm(docId) {

	$('#docRow_' + docId).remove();
}


function resetDocumentForm() {
	$("#docnoid_").val('');
	$("#imageName_0").val('');
	$("#slNo").val('');
	$("#imageName_0").html('');
	$("input[type='text'], input[type='file'], input[type='number'], textarea").val('');
	$("#docDtlsGrid tbody tr").each(function () {
		$(this).find(".attachment-column").text('');
		$(this).find(".name-column").text('');
	});
}


$('#docTbl').on('click', '.rmv1', function () {
	var docId = $(this).closest('tr').attr('id').split('_')[1];
	openDeleteConfirm(docId);
});


function cancelDocForm() {
	/*$("#cancelBtn").prop("disabled", true);
	$("#docSaveBtn").prop("disabled", true);*/
	//$("#editBtn").prop("disabled",false);
	//$("#deleteBtn").prop("disabled",false);

	$("#addDocBtn").removeClass('d-none')
	$("#cancelBtn").addClass('d-none');
	$("#docSaveBtn").addClass('d-none');

	const toggleSection = $("#toggleSection");
	const docDtlsGrid = $("#docDtlsGrid");

	toggleSection.css("display", "none");
	docDtlsGrid.parent().removeClass('d-none');
	docDtlsGrid.css("display", "block");

	$("#docnoid_").val('');
	$("#slNo").val('');

	gridOptionsDocumrntDtls.api.deselectAll();
}


function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function () {
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function () {
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
		var LightImg = "<div class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-image'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx" || extension[1] == "csv") {
		var LightImg = "<div class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<div class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon m-0 p-0'> </div>";
	}

	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);


	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}


function saveDoc() {
	var slNos = $("#docSlNo").val();
	var currentData = [];
	var maxSlNo = 0;


	if (gridOptionsDocumrntDtls.api) {
		gridOptionsDocumrntDtls.api.forEachNode(function (node) {
			currentData.push(node.data);
			if (node.data.slNo > maxSlNo) {
				maxSlNo = node.data.slNo;
			}
		});
	}


	var fileProcessingPromises = [];

	$("#doctbodyData > tr").each(function (index) {
		var docName = $(this).find(".docNoclss").val();
		var uFile = $(this).find(".document")[0]?.files[0];
		var fileName = $(this).find(".document").val();


		if (!docName || docName.trim() === "") {

			toastr.error("Document Name is required.");
			const toggleSection = $("#toggleSection");
			const docDtlsGrid = $("#docDtlsGrid");

			if (toggleSection.css("display") === "none" || toggleSection.css("display") === "") {
				toggleSection.css("display", "block");
				docDtlsGrid.css("display", "none");
				docDtlsGrid.parent().addClass('d-none');
			}
			return false;
		}
		if (!fileName || fileName.trim() === "") {
			const toggleSection = $("#toggleSection");
			const docDtlsGrid = $("#docDtlsGrid");

			if (toggleSection.css("display") === "none" || toggleSection.css("display") === "") {
				toggleSection.css("display", "block");
				docDtlsGrid.css("display", "none");
				docDtlsGrid.parent().addClass('d-none');
			}

			toastr.error("Attachment is required.");

			return false;
		}

		if (fileName) {
			var lastIndex = fileName.lastIndexOf("\\");
			if (lastIndex >= 0) {
				fileName = fileName.substring(lastIndex + 1);
			}
		}

		var filePromise = new Promise((resolve) => {
			if (uFile) {
				var reader = new FileReader();
				reader.readAsDataURL(uFile);

				reader.onload = function () {
					var base64Content = reader.result.split(",")[1];

					if (slNos) {
						var existingRow = currentData.find(row => row.slNo == slNos);
						if (existingRow) {

							existingRow.docName = docName;
							existingRow.doc = fileName;
							existingRow.base64Content = base64Content;

							resolve();
							return;
						}
					}


					currentData.push({
						slNo: maxSlNo + 1,
						docName: docName,
						doc: fileName,
						base64Content: base64Content,
					});
					maxSlNo++;
					resolve();
				};
			} else {

				if (slNos) {
					var existingRow = currentData.find(row => row.slNo == slNos);
					if (existingRow) {
						existingRow.docName = docName;
						existingRow.doc = fileName;
						existingRow.base64Content = null;

						resolve();
						return;
					}
				}

				currentData.push({
					slNo: maxSlNo + 1,
					docName: docName,
					doc: fileName,
					base64Content: null,
				});
				maxSlNo++;
				resolve();
			}
		});

		fileProcessingPromises.push(filePromise);
		const toggleSection = $("#toggleSection");
		const docDtlsGrid = $("#docDtlsGrid");

		if (toggleSection.css("display") === "block" || toggleSection.css("display") === "") {
			toggleSection.css("display", "none");
			docDtlsGrid.css("display", "block");
			docDtlsGrid.parent().removeClass('d-none');
		}
		/*$("#docSaveBtn").prop("disabled", true);
		$("#cancelBtn").prop("disabled", true);*/

		$("#docSaveBtn").addClass('d-none');
		$("#cancelBtn").addClass('d-none');
		$("#addDocBtn").removeClass('d-none');
	});

	Promise.all(fileProcessingPromises).then(() => {
		gridOptionsDocumrntDtls.api.setRowData(currentData);
		//cancelDocForm();


	});


}


function editDocDtls() {
	/*$("#editBtn").prop("disabled", true);
	$("#deleteBtn").prop("disabled", true);*/

	$("#editBtn").addClass('d-none');
	$("#deleteBtn").addClass('d-none');

	addDocument();
	var selectedNodes = gridOptionsDocumrntDtls.api.getSelectedNodes();
	if (selectedNodes.length > 0) {
		var rowNode = selectedNodes[0];
		var rowData = rowNode.data;

		console.log(rowData)


		$("#docSlNo").val(rowData.slNo);
		$("#doctbodyData").empty();

		var tbl = '<tr id="docRow_1">'
			+ '<td><div class="form-group"><input type="text" value="" class="form-control docNoclss" id="docnoid_1"></div></td>'
			+ '<td class="d-flex gap-2 align-items-center"><div class="control-group">'
			+ '<label class="custom-file-upload m-0" for="uploadDoc_1" id="uploadFor_1">'
			+ '<i class="ti-plus" id="clickImg_1"></i></label>'
			+ '<div class="controls"><input type="file" class="document" id="uploadDoc_1" name="userImage" onchange="saveMultiFile(event, 1)" /></div></div>'
			+ '<input type="hidden" id="uploadHidden_1" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls order-3"></div>'
			+ '<div id="imageName_1" class="imageName"></div>'
			+ '<input type="hidden" id="editId_1" value="">'
			+ '<div id="dltImage_1" class="custom-file-delete m-0 p-0 h-auto">'
			+ '<i class="ti-close rmv1" onclick="openDeleteConfirm(1)"></i></div></td>'
			+ '</tr>';

		$("#doctbodyData").append(tbl);

		if (rowData.base64Content) {

			$("#uploadedBillDiv_" + rowData.slNo).html('<img src="data:image/png;base64,' + rowData.base64Content + '" alt="Document"/>');
			$("#imageName_" + rowData.slNo).text(rowData.doc);
		}
		$("#docnoid_" + rowData.slNo).val(rowData.docName);
		//rowNode.setSelected(true);
	}
}


function deleteDoc() {
	var selectedNodes = gridOptionsDocumrntDtls.api.getSelectedNodes();
	if (selectedNodes.length > 0) {

		var rowNode = selectedNodes[0];
		var rowData = rowNode.data;

		gridOptionsDocumrntDtls.api.applyTransaction({remove: [rowData]});

	}
}

function saveProject() {
	$("#addProjectBtn").removeClass("active");
	var uploadList = [];
	var uploadListDetails = [];
	gridOptionsDocumrntDtls.api.forEachNode(function (rowNode) {
		const rowData = rowNode.data;

		if (rowData.docName && rowData.doc) {
			const uploadData = {
				projectId: $("#projectId").val(),
				documentName: rowData.docName || "",
				documentFileName: rowData.doc || "",
				slNo: rowData.slNo || "",
			};
			uploadList.push(uploadData);
			console.log("Prepared upload data:", uploadData);
		}

		if (rowData.docName && rowData.doc) {
			const documentName = rowData.docName ? rowData.docName.substring(rowData.docName.lastIndexOf("/") + 1) : "";
			const documentFileName = rowData.doc ? rowData.doc.substring(rowData.doc.lastIndexOf("/") + 1) : "";

			const uploadDataDetails = {
				projectId: $("#projectId").val(),
				documentName: documentName,
				documentFileName: documentFileName,
				slNo: rowData.slNo || "",
			};

			uploadListDetails.push(uploadDataDetails);
			console.log("Prepared upload data:", uploadDataDetails);
		}


	});

	// Create the project object
	var projectData = {
		projectId: $("#projectId").val(),
		projectName: $("#projectName").val(),
		projectIncharge: $("#projectIncharge").val(),
		address: $("#address").val(),
		documentList1: uploadListDetails,
		projectType: $("#projectType").val(),
		date: $("#date").val(),
		status: $("#status").val(),
		pinCode: $("#pinCode").val(),
	};


	console.log("Final project data to be saved:", projectData);

	uploadFiles();

	var validation = true;

	if (projectData.projectName == null || projectData.projectName == "") {
		toastr.error('Project Name Required');
		return;
	}
	if (projectData.date == null || projectData.date == "") {
		toastr.error('Date Required');
		return;
	}
	if (projectData.status == null || projectData.status == "") {
		toastr.error('Status Required');
		return;
	}
	/*if (projectData.projectType == null || projectData.projectType == "") {
		toastr.error('Project Type required');
		return;
	}*/
	/*if (projectData.pinCode == null || projectData.pinCode == "") {
		toastr.error('Pin Code Rrequired');
		return;
	}*/

	if (projectData.address == null || projectData.address == "") {
		toastr.error('Project Address Required');
		return;
	}


	if (validation) {
		saveProjectData(projectData);
	}
}

function uploadFiles() {
	var uploadList = [];

	gridOptionsDocumrntDtls.api.forEachNode(function (rowNode) {
		const rowData = rowNode.data;
		if (rowData.docName && rowData.doc) {
			const uploadData = {
				documentFileName: rowData.doc || "",
				file: rowData.base64Content
			};
			uploadList.push(uploadData);
		}
	});

	if (uploadList.length === 0) {
		console.error("No files found for upload.");
		return;
	}

	const formData = new FormData();

	uploadList.forEach(item => {
		try {
			const byteCharacters = atob(item.file);
			const byteNumbers = new Array(byteCharacters.length);
			for (let i = 0; i < byteCharacters.length; i++) {
				byteNumbers[i] = byteCharacters.charCodeAt(i);
			}
			const byteArray = new Uint8Array(byteNumbers);

			const blob = new Blob([byteArray], {type: "application/octet-stream"});
			formData.append("files", blob, item.documentFileName);
		} catch (error) {
			console.error(`Error processing file: ${item.documentFileName}`, error);
		}
	});

	$.ajax({
		url: 'upload-files-project',
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false,
		success: function (response) {
			console.log("Upload Success:", response);
		},
		error: function (jqXHR, textStatus, errorThrown) {
			console.error("Upload Error:", textStatus, errorThrown);
		}
	});
}


function saveProjectData(datas) {
	console.log("Data to be sent:", datas);

	$.ajax({
		type: "POST",
		url: "add-project-creation",
		contentType: "application/json",
		data: JSON.stringify([datas]),
		success: function (Response) {
			if (Response.code === "Success") {

				toastr.success(Response.message)
				getAllProject();
				$("#meetingDtls").addClass("disableEditDoc");
			} else {
				console.error("Error:", response.message);
			}
		},
		error: function (error) {
			console.error("AJAX Error:", error);
		}
	});
}


/*column defination for project creation*/
var columnDefs = [
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
		headerName: "Project ID",
		field: "projectId",
		flex: 1

	},
	{
		headerName: "Project Name",
		field: "projectname",
		flex: 1
	},
	{
		headerName: "Incharge Name",
		field: "projectInc",
		flex: 1

	},

	{
		headerName: "Creation Date",
		field: "creationDate",
		flex: 1
	}];

// Define grid options
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200
	},
	pagination: true,
	paginationPageSize: 15,
	/*onGridReady: function(params) {
        params.api.sizeColumnsToFit();
        window.gridApi = params.api;
        window.gridOptions = params.api;

    }*/
	onSelectionChanged: rowSelect
};


var columnDefDocumentDtls = [
	{
		checkboxSelection: true,
		width: 40
	},

	{
		headerName: "Sl No.",
		field: "slNo",
		width: 80,
		flex:1
		/*cellRenderer: function(params) {
            return '<a onclick="editDocDtls()" href="javascript:void(0)">' + params.data.slNo + '</a>';
        }*/
	},
	{
		headerName: 'Document Name',
		field: "docName",
		width: 300,
		cellStyle: {
			textAlign: 'left'
		},
		flex:4
	}
	, {
		headerName: 'Document',
		field: "doc",
		width: 300,
		cellRenderer: function (params) {
			var fullImagePath = params.data.doc; // This is the full path of the image
			var imageName = fullImagePath.substring(fullImagePath.lastIndexOf("/") + 1); // Extract image name from the URL
			return '<a href="' + fullImagePath + '" target="_blank">' + imageName + '</a>';
		},
		flex:4
	}];
var gridOptionsDocumrntDtls = {
	columnDefs: columnDefDocumentDtls,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: false,
	suppressAggFuncInHeader: true,
	getRowNodeId: function (data) {
		return data.slNo;
	},
	onSelectionChanged: rowSelectDoc
};

function rowSelectDoc() {
	var selectedRows = gridOptionsDocumrntDtls.api.getSelectedRows();
	if (selectedRows.length > 0) {
		/*$("#editBtn").prop("disabled", false);
		$("#deleteBtn").prop("disabled", false);
		$("#addDocBtn").prop("disabled", true);
		$("#docSaveBtn").prop("disabled", true);*/

		$("#editBtn").removeClass('d-none');
		$("#deleteBtn").removeClass('d-none');
		$("#addDocBtn").addClass('d-none');
		$("#docSaveBtn").addClass('d-none');
	} else {
		/*$("#editBtn").prop("disabled", true);
		$("#deleteBtn").prop("disabled", true);
		$("#addDocBtn").prop("disabled", false);
		$("#docSaveBtn").prop("disabled", true);*/

		$("#editBtn").addClass('d-none');
		$("#deleteBtn").addClass('d-none');
		$("#addDocBtn").removeClass('d-none');
		$("#docSaveBtn").addClass('d-none');
	}
}


function dynamicColumn() {
	$("#doctbodyData").empty();

	var tbl = '<tr id="docRow_1">'
		+ '<td><div class="form-group"><input type="text" value="" class="form-control docNoclss" id="docnoid_1"></div></td>'
		+ '<td class="d-flex gap-2 align-items-center"><div class="control-group">'
		+ '<label class="custom-file-upload" for="uploadDoc_1" id="uploadFor_1">'
		+ '<i class="ti-plus" id="clickImg_1"></i></label>'
		+ '<div class="controls"><input type="file" class="document" id="uploadDoc_1" name="userImage" onchange="saveMultiFile(event, 1)" /></div></div>'
		+ '<input type="hidden" id="uploadHidden_1" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls order-3"></div>'
		+ '<div id="imageName_1" class="imageName"></div>'
		+ '<input type="hidden" id="editId_1" value="">'
		+ '<div id="dltImage_1" class="custom-file-delete m-0 p-0 h-auto">'
		+ '<i class="ti-close rmv1" onclick="openDeleteConfirm(1)"></i></div></td>'
		+ '</tr>';

	$("#doctbodyData").append(tbl);

}

function getAllProject() {

	agGrid.simpleHttpRequest({
		url: "view-all-project?type=" + type
	}).then(function (response) {
		if (response.code === "Success") {
			//$("#save").prop("disabled", true);
			
			const responseBody = JSON.parse(response.body);
			const projectDetails = responseBody.projectDetails;


			var newRowData = projectDetails.reverse();

			gridOptions.api.setRowData(newRowData);

			if (newRowData && newRowData.length > 0) {
				gridOptions.api.forEachNode(function (node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});
			}

		} else {
			console.error("Failed to fetch data");
		}
	});

}

function rowSelect() {
	//$("#save").prop("disabled", true);
	var selectedRows = gridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		var datas = selectedRows[0];
		var projectId = (datas.projectId);
		$("#projectId").text(projectId);
		$("#project-name-header").text(datas.projectname);
		$(".formValidation").remove();
		getAllProjectById(projectId);
		$("#addProjectBtn").removeClass("active");
		$("#meetingDtls").addClass("disableEditDoc");
		$("#save").addClass("d-none");

	} else {
		$("#save").removeClass("d-none");
		//$("#save").prop("disabled", false);
		$(".formValidation").remove();
		$("#projectId").text('');
		gridOptionsDocumrntDtls.api.setRowData("");
		$("#projectId").val('').prop("disabled", false);
		$("#projectName").val('').prop("disabled", false);
		$("#projectIncharge").val('').prop("disabled", false);
		$("#address").val('').prop("disabled", false);
		$("#projectType").val('').prop("disabled", false);
		$("#date").val('').prop("disabled", false);
		$("#status").val('').prop("disabled", false);
		$("#pinCode").val('').prop("disabled", false);
		$("#addProjectBtn").addClass("active");
		$("#meetingDtls").removeClass("disableEditDoc");
		$("#project-name-header").text('');
	}


}

function getAllProjectById(projectId) {
	agGrid.simpleHttpRequest({
		url: "view-all-project-by-id?id=" + projectId
	}).then(function (response) {
		if (response.message === "Success") {
			try {
				const responseBody = JSON.parse(response.body[0]);
				const projectDetails = responseBody[0];
				
				let stats = projectDetails?.status?.toString() || "";

				$("#projectId").val(projectDetails.projectId || "").prop("disabled", true);
				$("#projectName").val(projectDetails.projectName || "").prop("disabled", true);
				$("#projectIncharge").val(projectDetails.projectInc || "").prop("disabled", true);
				$("#date").val(projectDetails.creationDate || "").prop("disabled", true);
				$("#status").val(stats).trigger('change').prop("disabled", true);
				$("#projectType").val(projectDetails.projectType || "").trigger('change').prop("disabled", true);
				$("#pinCode").val(projectDetails.pinCode || "").prop("disabled", true);
				$("#address").val(projectDetails.address || "").prop("disabled", true);


				if (projectDetails.docDtls && Array.isArray(projectDetails.docDtls)) {
					const documentData = projectDetails.docDtls.length > 0
						? projectDetails.docDtls.map((doc, index) => ({
							slNo: index + 1,
							docName: doc.documentName || "",
							doc: doc.fileName || ""
						}))
						: [];

					gridOptionsDocumrntDtls.api.setRowData(documentData);
				} else {
					gridOptionsDocumrntDtls.api.setRowData([]);
				}

			} catch (e) {
				console.error("Error parsing response data", e);
				gridOptionsDocumrntDtls.api.setRowData([]);
			}
		}
	})
}


function addNewProject() {

	$(".formValidation").remove();
	gridOptionsDocumrntDtls.api.setRowData("");
	gridOptions.api.deselectAll();
	$("#save").prop("disabled", false);
	$("#projectId").val('').prop("disabled", false);
	$("#projectName").val('').prop("disabled", false);
	$("#projectIncharge").val('').prop("disabled", false);
	$("#address").val('').prop("disabled", false);
	$("#projectType").val('').prop("disabled", false);
	$("#date").val('').prop("disabled", false);
	$("#status").val('').prop("disabled", false);
	$("#pinCode").val('').prop("disabled", false);
	documentList1 = "";
	$("#meetingDtls").removeClass("disableEditDoc");
	$("#addProjectBtn").removeClass("active");
	$("#save").removeClass("d-none");
	$("#project-name-header").text('');

}

function editProject() {
	$("#projectId").prop("disabled", false);
	$("#projectName").prop("disabled", false);
	$("#projectIncharge").prop("disabled", false);
	$("#address").prop("disabled", false);
	$("#projectType").prop("disabled", false);
	$("#date").prop("disabled", false);
	$("#status").prop("disabled", false);
	$("#pinCode").prop("disabled", false);
	$("#meetingDtls").removeClass("disableEditDoc");
	$("#addProjectBtn").removeClass("active");
	$("#save").removeClass("d-none");
	//$("#addDocBtn").prop("disabled", false);
}

/*	function showSnackbar(message) {
    const snackbar = document.getElementById("snackbar");
    snackbar.textContent = message;
    snackbar.className = "snackbar show";
    setTimeout(() => {
        snackbar.className = snackbar.className.replace("show", "");
    }, 3000);
}*/


/*function onQuickFilterChanged() {
    gridOptions.api
        .setQuickFilter(document.getElementById('quickFilter').value);
    var displayedRowCount = gridOptions.api.getDisplayedRowCount();

    var len = displayedRowCount;
    $('#totalReq').find('span').html(len);
}*/

function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	if (gridOptions.api) {
		gridOptions.api.setQuickFilter(quickFilterValue);
	}
	updateTotalTaskCount();
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	if (gridOptions.api) {
		gridOptions.api.setQuickFilter(null);
	}
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	if (window.gridApi) {
		var displayedRowCount = gridOptions.api.getDisplayedRowCount();
		$('#totalReq span').html(displayedRowCount);
	}
}


function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({force: true});
}