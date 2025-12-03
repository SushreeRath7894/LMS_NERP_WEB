//Multiple Document Upload Ends
$(document).ready(function() {
	$('#docDetailsDiv').hide();
	$("#demo").hide();
	$("#newWord").hide();
	$("#documentUpload").hide();
	$("#folderUpload").hide();
	$("#editfolderUpload").hide();
	$("#folderPathName").hide();
	$("#uploadLink").hide();
	$("#documentDelete").attr('disabled', true);
	$("#modifyFolder").attr('disabled', true);
	$("#docHistory").attr('disabled', true);

	$("#accessAttachFile").hide();
	$("#accessAttach").hide();
	$("#docControls").val('Upload Document');
	$("#userListGrid").hide();
	$("#userGroupListGrid").hide();
	$("#next-btn-1").hide();
	naviGateUploadForm();
	//selectFirstRow();
	$("#folderTableStatus").hide();
	//employeeGrid();
	employeeList();
	userGroupList();
	/*var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);*/
	var gridDiv1 = document.querySelector('#myGridDocHistory');
	new agGrid.Grid(gridDiv1, gridOptionsDocHistory);
	var gridDiv2 = document.querySelector('#myGridWorkFlow');
	new agGrid.Grid(gridDiv2, gridOptionsWorkFlow);

	var gridDiv3 = document.querySelector('#userGrid');
	new agGrid.Grid(gridDiv3, gridOptionUsers);
	var rowData = [];
	gridOptionUsers.api.setRowData(rowData);

	var rowData2 = [];
	var gridDiv4 = document.querySelector('#userGroupGrid');
	new agGrid.Grid(gridDiv4, gridOptionUsersGroups);
	gridOptionUsersGroups.api.setRowData(rowData2);


	$('#docTbl').on('click', '.rmv1', function() {
		openDeleteConfirm();
		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);


	});
	$('#docTbl').on('click', '.rmv1', function() {

		DeleteConfirmAccess();
		var value1 = $(this).parent("div").attr("id");
		$("#dltValueAccess").val(value1);
	});

	//For Notification
	/*var docid = localStorage.getItem("docId");
	var notificationId = localStorage.getItem("notificationId");
	if (docid != "") {
		//api call from notification status update
		$.ajax({
			type: "GET",
			url: "document-control-notificationUpdate?id=" + notificationId,
			success: function(response) {
				if (response.message == "Success") {
					localStorage.removeItem("docId");
					localStorage.removeItem("createdBy");
					localStorage.removeItem("notificationId");
					var pages;
					var pageno = 1;
					var rowData = [];
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {
						if (data.code == "Success") {
							// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);
							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							console.log(jsonData.data, 'DocumentData');
						//	gridOptions.api.setRowData(jsonData.data);
							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}
							// Create pagination after setting row data
							createPagination(pages, pageno);
							setTimeout(() => {
								if (docid) {
									let rowNodeFound = false;
									gridOptions.api.forEachNode((node) => {
										if (node.data.docid === docid) {
											rowNodeFound = true;
											node.setSelected(true);
											gridOptions.api.ensureIndexVisible(node.rowIndex); // Ensure the row is visible
										}
									});
									if (!rowNodeFound) {
										console.warn("Row with docId not found.");
									}
								}
							}, 100); // Timeout to ensure grid is fully rendered
						} else {
							//gridOptions.api.setRowData([]);
						}
						$('.loader').hide();
					});
				} else {
					("error" + console.message)
				}

			}
		});
	}*/

	var pages;
	var pageno = 1;
	var rowData = [];
	//gridOptions.api.setRowData(rowData);

	/*agGrid.simpleHttpRequest({
		url: "document-control-view?pageno=" + pageno
	}).then(function(data) {

		if (data.code == "Success") {
			// Parse the JSON string to extract pages
			const jsonData = JSON.parse(data.body[0]);

			const len = jsonData.data.length;
			$('#document').find('span').html(len);
			console.log(jsonData.data, 'DocumentData');
			//	gridOptions.api.setRowData(jsonData.data);
			$("#totalReq").find('span').html(len);

			window.gridOptions.api.setRowData(jsonData.data);

			if (jsonData.data && jsonData.data.length > 0) {
				window.gridOptions.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});
			}

			if (len > 0) {
				$('#totalPageno').val(jsonData.pages);
				pages = jsonData.pages;
			}*/

	// Create pagination after setting row data
	/*createPagination(pages, pageno);
	setTimeout(() => {
		if (docid) {
			let rowNodeFound = false;
			gridOptions.api.forEachNode((node) => {

				if (node.data.docid === docid) {
					rowNodeFound = true;
					node.setSelected(true);
					gridOptions.api.ensureIndexVisible(node.rowIndex); // Ensure the row is visible
				}
			});
			if (!rowNodeFound) {
				console.warn("Row with docId not found.");
			}
		}
	}, 100); // Timeout to ensure grid is fully rendered

} else {
	gridOptions.api.setRowData([]);
}


$('.loader').hide();
});*/





	var gridDiv1 = document.querySelector('#myGridworkflow');
	new agGrid.Grid(gridDiv1, gridOptions1);
	CKEDITOR.replace('commentck', {
		height: 200,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});



	// for Rendering The FolderStructure
	viewWorkSpace()
		.then(() => {
			renderFolderView(currentFolderData);
			currentPath = [];
			$('#breadcrumb').text('');
			$('#backButton').click(goBack);
			$("#backButton").hide();
			//getFirstParentFolder();
			//disableAllTheField();
		})
		.catch((err) => {
			console.error('ERROR ', err.message);
		});

	disableAllTheField();

	$("#seacrhInput").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault(); // Stop the form from submitting
			console.log("Enter key pressed, calling filter function...");
			searchFolder(); // Call the filter function
		}
	});

});


function docHistory() {
	$("#documentHistoryModal").modal('show');
	var selectedRow = gridOptions.api.getSelectedRows();
	var docId = selectedRow[0].docid;
	var rowData = [];
	gridOptionsDocHistory.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "document-control-view-history?docId=" + docId
	}).then(function(data) {

		if (data.code == "Success") {// Parse the JSON string to extract pages
			const jsonData = JSON.parse(data.body[0]);
			console.log('jsonDataDocHistory', jsonData);
			//const len = jsonData.data.length;
			//$('#document').find('span').html(len);
			console.log(jsonData.data, 'aaaaaaaaa');
			gridOptionsDocHistory.api.setRowData(jsonData);


		} else {
			gridOptionsDocHistory.api.setRowData([])
		}
		$('.loader').hide();

	});
}

function workflowWiseFolderList(status) {
	$.ajax({
		type: "POST",
		url: "document-control-folderList",
		dataType: 'json',
		contentType: 'application/json',
		data: status,
		success: function(response) {
			console.log('##################', response);
			var stateDropdown = $("#workFlowFolder");
			stateDropdown.empty();

			// Add a default "Select" option
			var defaultOption = $("<option></option>");
			defaultOption.val("");
			defaultOption.html("Select");
			stateDropdown.append(defaultOption);

			for (var i = 0; i < response.body.length; i++) {
				var option = $("<option></option>");
				option.val(response.body[i].key);
				option.html(response.body[i].name);
				stateDropdown.append(option);
			}

			// Set default value to the "Select" option
			stateDropdown.val("");
		},
		error: function(data) {
			console.log(data);
			$("#workFlowFolder").empty();
			var defaultOption = $("<option></option>");
			defaultOption.val("");
			defaultOption.html("Select");
			$("#workFlowFolder").append(defaultOption);
		}
	});
}
var workStatus = "";
function startWorkFlow(folderName, documentName, id) {

	//var selectedRow = gridOptions.api.getSelectedRows();
	var docId = id;
	var folderName = folderName;
	var documentName = documentName;
	var docWorkFlow = folderName + ">  " + documentName;
	$("#workFlowModal").modal('show');
	$("#workFlowDoc").html(docWorkFlow);
	$("#workFlowFolder").val(folderName);
	console.log("doc name-->", docWorkFlow);
	var rowData = [];
	gridOptionsWorkFlow.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "document-control-view-workFlow?docId=" + docId
	}).then(function(data) {

		if (data.code == "Success") {// Parse the JSON string to extract pages
			const jsonData = JSON.parse(data.body[0]);
			gridOptionsWorkFlow.api.setRowData(jsonData);
			$("#workFlowFolder").val(jsonData[0].workFlowFolder);
			$("#workFlowId").val(jsonData[0].workFlwId);
			$("#resolution").val(jsonData[0].resolution);
			workflowWiseFolderList("1");
		} else {
			gridOptionsWorkFlow.api.setRowData([])
		}
		$('.loader').hide();

	});
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
var currentDate = new Date();
var day = String(currentDate.getDate()).padStart(2, '0');
var month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are zero-based
var year = currentDate.getFullYear();

var formattedDate = day + '-' + month + '-' + year;
var columnDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Document No",
		field: "docid",
		cellRenderer: function(params) {
			var userId = $("#userId").val();
			var createdBy = params.data.createdBy;
			if (createdBy.toString() == userId.toString()) {

				return params.data.docid;
			} else {
				if ((formattedDate.toString() == params.data.expirationDate.toString()) && params.data.expirationDate != null) {
					return params.data.docid;
				}
				else {
					return '<a id="docId" onclick=editDocument("'
						+ params.data.docid + '","' + window.btoa(params.data.docControl) + '") href="javascript:void(0)">'
						+ params.data.docid + '</a>';
				}
			}
		}
	}, {
		headerName: "Date",
		field: "date",
		width: "120"
	}, {
		headerName: "Version",
		field: "version",
		width: "100"
	}, {
		headerName: "Type",
		field: "docControl",
		width: "120"
	}, {
		headerName: 'Name',
		field: "documentName",
	},
	{
		headerName: "Created By",
		field: "empName",

	}, {
		headerName: "Folder Name",
		field: "folderName",
		width: "140"
	}, {
		headerName: "File",
		field: "fileName",
		width: "90",
		cellRenderer: function(params) {
			if (params.data.docControl == "Upload Folder") {
				const documentList = JSON.stringify(params.data.documentList);
				return `<a onclick='folderDocModal(${documentList})' href="javascript:void(0)"><i class="bi bi-pencil-square"></i></a>`;
			} else {
				var div = "";
				if (params.data.documentUrl) {
					console.log('params.data.documentUrl', params.data.documentUrl)
					var ext = params.data.documentUrl.split(".");
					if (ext[1] == "pdf") {
						div = div
							+ " "
							+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImageDoc("' + params.data.documentUrl + '")> </div>';
					} else {
						div = div
							+ " "
							+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImageDoc("' + params.data.documentUrl + '")> </div>';
					}
				}
				return div;
			}
		}
	}, {
		headerName: "Description",
		field: "description",
	}, {
		headerName: "Tags",
		field: "tags",
	}, {
		headerName: 'Access',
		field: "access",
		cellRenderer: function(params) {
			return '<a onclick=accessControlData("' + params.data.docid + '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i></a>';
		}
	},
	{
		headerName: "Access",
		field: "editUserAccess",
		hide: true
	}
];

var gridOptions = {
	columnDefs: columnDefs,
	//rowData : rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	getRowId: function(params) {
		return params.data.docid; // Replace `docid` with the unique identifier field in your data
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChanged1
};
var columnDefs1 = [{
	headerName: "User Id",
	field: "userId",
	width: 100,
}, {
	headerName: "Access",
	field: "access",
	width: 150,
}, {
	headerName: "Type",
	field: "type",
	width: 150,
},];
const rowData1 = [{
	userId: 1,
	access: "Read",
	type: "Admin",
},
{
	userId: 2,
	access: "Write",
	type: "User",
},
];

var gridOptions1 = {
	columnDefs: columnDefs1,
	rowData: rowData1,
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

var columnDefsDocHistory = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Document No",
		field: "documentId",
		width: "120"
	}, {
		headerName: "Date",
		field: "date",
		width: "100"
	}, {
		headerName: "Version",
		field: "documentVersion",
		width: "90"
	}, {
		headerName: "Document Name",
		field: "documentName",
	}, {
		headerName: 'Document Url',
		field: "documentUrl",
		width: 100,
		cellRenderer: function(params) {
			var dataimg = params.data.documentUrl.split(",");
			var div = "";
			for (var i = 0; i < dataimg.length; i++) {
				var ext = dataimg[i].split(".");
				if (ext[ext.length - 1] === "pdf") {
					div += '<div class="fa fa-file-pdf-o lg" style="cursor:pointer; color:blue" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="Taken" onclick=editView("'
						+ dataimg[i] + '")></div>';
				} else if (ext[ext.length - 1] === "xlsx" || ext[ext.length - 1] === "xls") {
					div += '<div class="fa fa-file-excel-o lg" style="cursor:pointer; color:blue" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="Taken" onclick=editView("'
						+ dataimg[i] + '")></div>';
				} else {
					div += '<div class="fa fa-image lg" style="cursor:pointer; color:blue" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="Taken" onclick=editView("'
						+ dataimg[i] + '")></div>';
				}
			}
			return div;
		}

	}, {
		headerName: "Access By",
		field: "accessBy",
	}, {
		headerName: "Created By",
		field: "createdBy",
	}
];

var gridOptionsDocHistory = {
	columnDefs: columnDefsDocHistory,
	//rowData : rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
};
var columnDefsWorkFlow = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Document No",
		field: "documentId"
	}, {
		headerName: "User Id",
		field: "employeeId",
		width: "120"
	}, {
		headerName: "User Name",
		field: "userName",
		width: "120"
	}, {
		headerName: "Order No",
		field: "orderNo",
		editable: function(params) {
			return params.data.type !== "Parallel";//&& params.data.status === 0;
		},
	}, {
		headerName: "Type",
		field: "type",
		cellRenderer: dropdownCellRenderer
	}, {
		headerName: "Status",
		field: "status",
		cellRenderer: function(params) {
			if (params.data.status == "1") {
				return '<div style="color:#008000">Approved</div>';
			} else if (params.data.status == "2") {
				return '<div style="color:#a9a9a9">Rejected</div>';
			} else {
				return '<div style="color:#a9a9a9">Pending</div>';
			}
		}
	}
];

var gridOptionsWorkFlow = {
	columnDefs: columnDefsWorkFlow,
	//rowData : rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onCellValueChanged: onCellValueChanged,
	stopEditingWhenCellsLoseFocus: true
};

var columnDefsUser = [
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
		headerName: "Employee ID",
		field: "employeeId",
		width: 150,
	}, {
		headerName: "Name",
		field: "empName",
		width: 250,
	}, {
		headerName: "Login Id",
		field: "employeeId",
		width: 200,
	}, {
		headerName: "Department",
		field: "department",
		width: 250,
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 250,
	}];
var gridOptionUsers = {
	columnDefs: columnDefsUser,
	rowSelection: 'multiple',
	onRowSelected: onUserRowSelection,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
};

var columnGroup = [
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
		headerName: "Group ID",
		field: "groupId",
		width: 250,
		cellRenderer: function(params) {
			console.log(params)
			return '<a id="docId" onclick=editGroup("'
				+ params.data.groupId + '") href="javascript:void(0)">'
				+ params.data.groupId + '</a>';

		},
	}, {
		headerName: "Group Name",
		field: "groupName",
		width: 250,
	}, {
		headerName: "Description",
		field: "groupDescription",
		width: 250,
	}, {
		headerName: "Created By",
		field: "createdBy",
		width: 250,
	}, {
		headerName: 'Created Date',
		field: "createdDate",
		width: 250,
	}, {
		headerName: "Status",
		field: "status",
		width: 200,

	},];
var gridOptionUsersGroups = {
	columnDefs: columnGroup,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChangedUserGroup,
};

function onSelectionChangedUserGroup() {
	var selectedNodes = gridOptionUsersGroups.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var groupid = selectedData[0].groupId;
	var users = JSON.stringify(selectedData[0].users);
	console.log(users);
}


function onUserRowSelection() {
	pickUser();
}


function folderDocModal(docList) {
	$("#statusTblDocFolder").empty();
	$("#folderUploadDocModal").modal('show');
	for (var i = 0; i < docList.length; i++) {
		var downloadIcon = '<i class="fas fa-download" style="cursor: pointer; margin-left: 8px;" onclick="downloadFile(\'' + docList[i].documentUrl + '\')"></i>';

		var divTag = '<tr>' +
			'<td><div class="form-group" style="width: 100%;">' +
			'<span class="progress-text">' + docList[i].documentName +
			'</span></div></div></td>' +
			'<td><div class="form-group" style="display: flex; align-items: center;">' +
			docList[i].fileName +
			downloadIcon + '</div></td>' +
			'</tr>';

		$("#statusTblDocFolder").append(divTag);

	}
}
function viewImageDoc(id) {
	window.open(id, '_blank');
}

function dropdownCellRenderer(params) {
	var select = document.createElement("select");
	select.className = "select";
	var options = ["--Select Type--", "Parallel", "Seriel"]; // Static options

	options.forEach(function(option) {
		var optionElement = document.createElement("option");
		optionElement.value = option;
		optionElement.text = option;
		if (option === params.value) {
			optionElement.selected = true;
		}
		select.appendChild(optionElement);
	});

	select.addEventListener("change", function() {
		params.setValue(select.value);
		if (select.value === "Parallel") {
			params.node.setDataValue('orderNo', 0);
		}
		params.api.refreshCells({ rowNodes: [params.node] });
	});

	return select;
}

function onCellValueChanged(params) {
	if (params.colDef.field === 'type' && params.newValue === 'Parallel') {
		var rowNode = params.node;
		rowNode.setDataValue('orderNo', 0);
	}
}



function editView(id) {
	window.open(id, '_blank');
}

function saveWorkFlow() {
	var datas = {};
	//var selectedRow = gridOptions.api.getSelectedRows();
	var id = $("#workFlowPageId").text();
	var type = $("#docControls").val();
	console.log('id', id)
	console.log('type', type)
	var allRowData = [];
	gridOptionsWorkFlow.api.forEachNode(function(node) {
		allRowData.push(node.data);
	});
	console.log('allRowData', allRowData)
	datas.resolution = $("#resolution").val();
	datas.workFlowFolder = $("#workFlowFolder").val();
	datas.docId = id;
	datas.docType = type;
	datas.workFowData = allRowData;
	datas.workFlowId = $("#workFlowId").val();
	setParentStatus(datas.workFowData);


	var data = JSON.stringify(datas);
	console.log('allRowData', allRowData)
	console.log('datas', datas)
	$('.loader').show();
	$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "document-control-workFlow-add",
		contentType: "application/json",
		data: data,
		success: function(response) {
			// Handle the success response if needed
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#folderUploadModal").modal('hide');
			var pages;
			var pageno = 1;
			var rowData = [];
			//	gridOptions.api.setRowData(rowData);
			agGrid.simpleHttpRequest({
				url: "document-control-view?pageno=" + pageno
			}).then(function(data) {

				if (data.code == "Success") {// Parse the JSON string to extract pages
					$("#documentUploadModal").modal('hide');
					$("#docDetailsDiv").hide();
					$("#modifyFolder").attr('disabled', true);
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							//gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".btn-hs").show();
					cancel();
				} else {
					//	gridOptions.api.setRowData([])
				}

				$('.loader').hide();

			});
			$("#messageParagraph").text("Workflow Saved Sucessfully");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			//tabAccess('manageControl');
			//$("#next-btn-1").hide();

			//$(".header-tab-link").addClass("showPointerEvent");
			//$("#Workflow").removeClass('active show')
			$(".loader").hide();

		},
		error: function(error) {
			// Handle the error if needed
		}
	});
}

function setParentStatus(workFowData) {
	// Sort the workFowData by orderNo
	workFowData.sort((a, b) => a.orderNo - b.orderNo);

	// Loop through the sorted data and set parentStatus
	for (let i = 0; i < workFowData.length; i++) {
		if (i === 0) {
			workFowData[i].parentStatus = null; // The first item has no parent
		} else {
			workFowData[i].parentStatus = workFowData[i - 1].orderNo;
		}
	}
}
var id = "";
var date = "";
var selectedData = "";
var documentName = "";
var folderName = "";

function onSelectionChanged1() {
	var docControl = "";
	var selectedNodes = gridOptions.api.getSelectedNodes();
	selectedData = selectedNodes.map(node => node.data);

	console.log("Selected Nodes-->", selectedNodes);
	id = selectedData.map(node => node.docid);
	date = selectedData.map(node => node.date);
	documentName = selectedData.map(node => node.documentName);
	folderName = selectedData.map(node => node.folderName);
	docControl = selectedData.map(node => node.docControl);
	var selectedRows = gridOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#docDetailsDiv').show();
		$('#documentDelete').attr('disabled', false);
		var createdBy = $("#userId").val();
		var userId = selectedRows[0].createdBy;
		if (userId == createdBy) {
			$('#docHistory').attr('disabled', true);
		}
		else {
			$('#docHistory').attr('disabled', false);
		}

		accessControl();
		reminderMail();
		//startWorkFlow();
	} else {
		$('#docDetailsDiv').hide();
		$('#documentDelete').attr('disabled', true);
		$("#modifyFolder").attr('disabled', true);
		$("#docHistory").attr('disabled', true);
	}

	onChangeIntailApiCall(folderName, documentName, id)


}



function onChangeIntailApiCall(folderName, documentName, id) {


	console.log("Ajax Call Successfully");
	var docDetails = folderName + ">  " + documentName;
	$("#docName").html(docDetails);
	$.ajax({
		type: "GET",
		url: 'document-control-details?docId=' + id,
		success: function(response) {
			if (response.code == "Success") {

				$('#auditLogBody').empty();

				$('#documentId').text(id);
				$("#documentid").text(id);

				$("#accesDocId").text(id);
				$("#reminderPageId").text(id);
				$("#historyId").text(id);
				$("#workFlowPageId").text(id);

				const jsonData = JSON.parse(response.body);
				console.log('jsonData', jsonData)
				var expiryDate = jsonData[0].expirationDate;
				$("#dueDate").html(expiryDate);
				$('#createdOn').text(jsonData[0].created_on);

				console.log("created On Text-->", jsonData[0].created_on);

				jsonData.forEach(item => {
					var row = '<tr>' +
						'<td>' + item.changed_on + '</td>' +
						'<td>' + item.created_by + '</td>' +
						'<td>' + item.action + '</td>' +
						'</tr>';
					$('#auditLogBody').append(row);
				});
			}
		},
		error: function(data) {
			console.log(data)
		}
	})


	$.ajax({
		type: "GET",
		url: 'document-control-version-details?docId=' + id,
		success: function(response) {
			if (response.code == "Success") {

				$('#versionControlBody').empty();

				$('#documentId').text(id);
				//$('#createdOn').text(date);

				const jsonData = JSON.parse(response.body);
				console.log('jsonData', jsonData)
				var expiryDate = jsonData[0].expirationDate;
				//$('#createdOn').text(jsonData[0].created_on);
				$("#dueDate").html(expiryDate);
				jsonData.forEach(item => {
					var row = '<tr>' +
						'<td>' + item.file_name + '</td>' +
						'<td>' + item.version + '</td>' +
						'<td>' + item.changed_on + '</td>' +
						'<td>' + item.action + '</td>' +
						'</tr>';
					$('#versionControlBody').append(row);
				});

			}
		},
		error: function(data) {
			console.log(data)
		}
	})
	var type = "";
	$.ajax({
		type: "GET",
		url: "document-control-edit?id=" + id,
		success: function(response) {
			if (response.message == "Success") {


				const jsonString = response.body.map(item =>
					item.replace(/(\w+)'(\w+)/g, '$1\u2019$2')
						.replace(/'/g, '"')
				);
				console.log("json string-->", jsonString);

				var jsonArray = "";
				try {
					jsonArray = jsonString.map(item => JSON.parse(item));
					console.log("jsonArray", jsonArray[0]);
				} catch (error) {
					console.error("JSON Parsing Error:", error.message);
				}


				console.log('jsonArray', jsonArray[0])
				type = jsonArray[0].accessType;
				var createdBy = jsonArray[0].createdBy;
				$("#docControls").val(jsonArray[0].docControl);
				$("#docnoid_").val(jsonArray[0].documentName);
				$("#docId").val(jsonArray[0].docid);
				$("#docUniqueId").val(jsonArray[0].docUniqueId);
				$("#version").val(jsonArray[0].version);
				$("#doc_desc").val(jsonArray[0].description);
				if (jsonArray[0].description.length > 50) {
					$(".read-more").show();
				}
				else {
					$(".read-more").hide();
				}
				$("#doc_tags").val(jsonArray[0].tags);
				$("#doc_folderName").val(jsonArray[0].folderWorkSpace);
				$("#createdBy").val(jsonArray[0].createdBy);
				$("#accessModifyStatus").val(jsonArray[0].accessModifyStatus);
				$("#accessVersion").val(jsonArray[0].accessVersion);
				var fileName = jsonArray[0].fileName;
				$(".uploadHidCls").val(jsonArray[0].docuemntControllData.originalFileName);
				console.log("original nm-->", jsonArray[0].docuemntControllData.originalFileName);
				//var folderName = jsonArray[0].orginalDocUrl;
				var folderName = jsonArray[0].docuemntControllData.documentUrl
				if (fileName != null) {
					$('#uploadHidden_0').val(fileName);
					var ext = jsonArray[0].fileName.split(".");
					$("#imageName_0").html(fileName);
					if (ext[1] == "jpg" || ext[1] == "png") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue"  onclick=viewImage("'
							+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
					} else if (ext[1] == "pdf") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
							+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
					} else if (ext[1] == "xls" || ext[1] == "xlsx") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-excel-o" style="color: red" onclick=viewImage("'
							+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
					} else if (ext[1] == "doc" || ext[1] == "dox" || ext[1] == "docx") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-word-o" style="color: red" onclick=viewImage("'
							+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
					} else if (ext[1] == "ppt" || ext[1] == "pptx") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-file-powerpoint-o" style="color: orange" onclick=viewImage("'
							+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
					} else {
						var LightImg = "<div class='uploadicon position-l'> </div>";
					}
					$("#uploadedBillDiv_0").html(LightImg);
				}

				var fileNameAccess = jsonArray[0].accessImage;
				var accessDocUrl = jsonArray[0].newDocUrl;
				if (fileNameAccess != null) {
					$('#uploadHidden1_0').val(fileNameAccess);
					var ext = fileNameAccess.split(".");
					$("#imageName1_0").html(fileNameAccess);
					if (ext[1] == "jpg" || ext[1] == "png") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue"  onclick=viewImage("'
							+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
					} else if (ext[1] == "pdf") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
							+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
					} else if (ext[1] == "xls" || ext[1] == "xlsx") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-excel-o" style="color: red" onclick=viewImage("'
							+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
					} else if (ext[1] == "doc" || ext[1] == "dox" || ext[1] == "docx") {
						var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-word-o" style="color: red" onclick=viewImage("'
							+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
					} else {
						var LightImg = "<div class='uploadicon position-l'> </div>";
					}
					$("#uploadedBillDiv1_0").html(LightImg);
				}

				//Check Wheather the userId is same as created by then only show
				var userId = $("#userId").val();
				if (createdBy == userId) {
					showActionTabAsPerUser();
					$("#docDetailsDiv").show();
				}
				else {
					hideActionTabAsPerUser();
					$("#docDetailsDiv").hide();
				}
				if (docControl = "Upload Folder" && type == "user" || type == "usergroup") {
					$("#modifyFolder").attr('disabled', false);
				} else {
					$("#modifyFolder").attr('disabled', true);
				}

				/*if (userId == jsonArray[0].createdBy) {
					$("#accessAttach").hide();
					$("#accessAttachFile").hide();
				} else {
					$("#accessAttach").show();
					$("#accessAttachFile").show();
				}*/

			} else
				("error" + console.message)
		}
	});

	var rowData = [];
	//gridOptionsWorkFlow.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "document-control-view-workFlow?docId=" + id
	}).then(function(data) {

		if (data.code == "Success") {// Parse the JSON string to extract pages
			const jsonData = JSON.parse(data.body[0]);


			/*if(jsonData!=null){
			if (jsonData.every(item => item.status === '1')) {
					$("#lockFile").attr('disabled', true); 
					$("#uploadDocVersion").attr('disabled', true); 
				}else{
					$("#lockFile").attr('disabled', false); 
					$("#uploadDocVersion").attr('disabled', false); 
				}
				}else{
					$("#lockFile").attr('disabled', false); 
					$("#uploadDocVersion").attr('disabled', false);
				}*/

		} else {
			//gridOptionsWorkFlow.api.setRowData([])
		}
		$('.loader').hide();

	});


	reminderMail(id);
	startWorkFlow(folderName, documentName, id);



}
function modifyFolder() {

	$("#folderUploadModal").modal("show");
	$("#modifyFolderDoc").show();
	$("#saveFolder").hide();
	$("#folderUpload").show();
	$("#newWord").hide();
	$("#documentUpload").hide();
	$("#uploadLink").hide();
	$("#demo").hide();
	$("#docnoid_").val('');
	$("#doc_folderName").val('');
	$("#uploadedBillDiv_0").empty();
	$("#imageName_0").empty();
	$("#dltImage_0").empty();
	$("#uploadDoc_0").val('');
	$("#imageName_0").val('');
	$("#doc_desc").val('');
	$("#doc_tags").val('');
	$("#uploadHidden_0").val('');
	$("#docId").val('');
	$("#statusTbl").empty();
	$("#doc_folderNameUpload").val('');
	$.ajax({
		type: "GET",
		url: "document-control-edit-uploadfolder?id=" + id,
		success: function(response) {
			if (response.message == "Success") {
				const jsonString = response.body.map(item => item.replace(/'/g, '"'));
				// Convert to JSON array
				jsonArr = jsonString.map(item => JSON.parse(item));
				$("#version3").val(jsonArr[0].version);
				documentList = jsonArr[0].documentList;
				//var folderName = documentList[0].documentName.split('/')[0];
				var folderName = documentList[0].documentName;
				$("#doc_folderNameUpload").val(jsonArr[0].folderName);
				$("#accessModifyStatus").val(jsonArr[0].accessModifyStatus);
				$("#docId").val(jsonArr[0].docid);
				$("#docControls").val(jsonArr[0].docControl);
				$("#accessVersion").val(jsonArr[0].accessVersion);
				$("#createdBy").val(jsonArr[0].createdBy);

			}
		}
	})
}
/ APPLY FOR REQUISITION ENDS /

// setup the grid after the page has finished loading
function cancelbtn() {
	$('#reqDltBtn').attr('disabled', true);
	$("#myGrid").show();
	$("#btn1").hide();
	$("#demo").hide();
	$("#powerpoint").hide();
	$("#hideTbl").show();
	$("#buttonDetails").show();
}

function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').attr('disabled', false);
		$("#reject").attr('disabled', false);
		$("#approve").attr('disabled', false);
	} else {
		$('#delete').attr('disabled', true);
		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
	}

}


function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	var totalRowCount = gridOptions.api.getModel().getRowCount();
	$('#document').find('span').html(totalRowCount);
}

function addId() {

	document.getElementById("mySidenav1").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:25px;";
	document.getElementById("upperOne").style.width = "73%";
}

function cancel() {
	$("#demo").hide();
	$("#newWord").hide();
	$("#docnoid_").val('');
	$("#doc_folderName").val('');
	$("#uploadedBillDiv_0").empty();
	$("#imageName_0").empty();
	$("#dltImage_0").empty();
	$("#uploadDoc_0").val('');
	$("#imageName_0").val('');
	$("#doc_desc").val('');
	$("#doc_tags").val('');
	$("#uploadHidden_0").val('');
}

function closeNav1() {

	document.getElementById("mySidenav1").style.width = "0";
	document.getElementById("upperOne").style.width = "100%";
}

function openSesame() {

	var k = $("#docControls").val();
	if (k == "Upload Folder") {

		$("#folderUploadModal").modal("show");
		$("#folderUpload").show();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#uploadLink").hide();
		$("#demo").hide();
		$("#docnoid_").val('');
		$("#doc_folderName").val('');
		$("#uploadedBillDiv_0").empty();
		$("#imageName_0").empty();
		$("#dltImage_0").empty();
		$("#uploadDoc_0").val('');
		$("#imageName_0").val('');
		$("#doc_desc").val('');
		$("#doc_tags").val('');
		$("#uploadHidden_0").val('');
		$("#docId").val('');
		$("#statusTbl").empty();
		$("#doc_folderNameUpload").val('');
		$("#saveFolder").show();
		$("#modifyFolderDoc").hide();

	}
	else if (k == "New Word Document") {

		$("#newWord").show();
		$("#demo").hide();
		$("#documentUpload").hide();
		$("#uploadLink").hide();
		$("#folderUpload").hide();

	}
	else if (k == "Upload Document") {

		$("#documentUploadModal").modal("show");
		$("#documentUpload").show();
		$("#newWord").hide();
		$("#demo").hide();
		$("#uploadLink").hide();
		$("#folderUpload").hide();
		$("#docnoid_").val('');
		$("#doc_folderName").val('');
		$("#uploadedBillDiv_0").empty();
		$("#imageName_0").empty();
		$("#dltImage_0").empty();
		$("#uploadDoc_0").val('');
		$("#imageName_0").val('');
		$("#doc_desc").val('');
		$("#doc_tags").val('');
		$("#uploadHidden_0").val('');
		$("#docId").val('');
		$("#accessAttach").hide();
		$("#accessAttachFile").hide();
	}
	else if (k == "Add a link") {
		$("#uploadLink").show();
		$("#lnkUploadModal").modal("show");
		$("#documentUpload").hide();
		$("#newWord").hide();
		$("#demo").hide();
		$("#folderUpload").hide();
	}

	else {
		$("#demo").hide();
		$("#powerpoint").hide();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#uploadLink").hide();
	}
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
	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm()'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);

	$("#dltImage_" + counter).addClass("custom-file-delete");
	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}


function saveMultiFile1(event) {
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
	$("#uploadedBillDiv1_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden1_" + counter).val('');
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
	//var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirmAccess()'></i>";
	var dltImg = "<i></i>";
	$("#uploadedBillDiv1_" + counter).html(LightImg);
	$("#imageName1_" + counter).html(fileName);
	$("#dltImage1_" + counter).html(dltImg);

	$("#dltImage1_" + counter).addClass("custom-file-delete");
	$("#clickImg1_" + counter).removeClass("ti-plus");
	$("#clickImg1_" + counter).addClass("ti-pencil");

}
function saveMultiFile2(event) {
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
	$("#uploadedBillDiv2_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden2_" + counter).val('');
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
	//var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirmAccess()'></i>";
	var dltImg = "<i></i>";
	$("#uploadedBillDiv2_" + counter).html(LightImg);
	$("#imageName2_" + counter).html(fileName);
	$("#dltImage2_" + counter).html(dltImg);

	$("#dltImage2_" + counter).addClass("custom-file-delete");
	$("#clickImg2_" + counter).removeClass("ti-plus");
	$("#clickImg2_" + counter).addClass("ti-pencil");

}
function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
	$('#deleteAttachmentAccess').modal('hide');
	deletAttachmentRowAccess();

}
function openDeleteConfirmAccess() {
	$("#dltValueAccess").val("");
	$('#deleteAttachmentAccess').modal('show');
	$('#deleteAttachment').modal('hide');
}



function deletAttachmentRow() {

	var id = $("#dltValue").val();
	var row = $("#" + id).closest('tr');
	row.find('.uploadedBillCls').html('');
	row.find('.imageName').html('');
	row.find('.uploadHidCls').val('');
	row.find('#dltImage_0').html('');
	row.find('input[type="file"]').val('');

	closeDeleteConfirm();
}
function deletAttachmentRowAccess() {
	$('#deleteAttachment').modal('hide');
	//var id = $("#dltValueAccess").val();

	//	console.log("Id-->", id);

	// only manuall one File Delete
	/*$('#uploadedBillDiv1_0' + id).html('');
	$('#imageName1_0' + id).html('');
	$('#uploadHidden1_0' + id).val('');
	$('#dltImage1_0' + id).html('');
	$('#uploadDoc1_0' + id).val('');*/

	$('#uploadedBillDiv_0').html('');
	$('#imageName_0').html('');
	$('#uploadHidden1_0').val('');
	$('#dltImage_0').html('');
	$('#uploadDoc1_0').val('');
	$("#clickImg_0").removeClass("ti-pencil");
	$("#clickImg_0").addClass("ti-plus");

	//	closeDeleteConfirmAccess();
}



function closeDeleteConfirmAccess() {
	$("#dltValueAccess").val("");
	$('#deleteAttachmentAccess').modal('hide');
}
function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
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

function addMore1() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:first").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclss").val("");
	$("#docTbl tbody tr:last").find(".docDesc").val("");
	$("#docTbl tbody tr:last").find(".docTags").val("");
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
		textInput.eq(4).attr('id', "doc_desc" + i);
		textInput.eq(5).attr('id', "doc_tags" + i);
		divInput.eq(4).attr('id', "uploadedBillDiv_" + i);
		divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");
}
function saveFileEdit() {
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
		url: "view-manage-employee-upload-file",
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


function modifyDoc() {
	$(".loader").show();

	var uploadList = [];

	$("#doctbodyData > tr").each(function() {
		var $row = $(this);

		// First document column
		var uFile1 = $row.find(".document")[0].files[0];
		var fileName1 = $row.find(".document").eq(0).val(); // Get value of the first .document input
		var data1 = [];
		var x1 = [];

		// Second document column
		var uFile2 = $row.find(".document").eq(1)[0].files[0];
		var fileName2 = $row.find(".document").eq(1).val(); // Get value of the second .document input
		var data2 = [];
		var x2 = [];

		// Handling first document file
		if (fileName1 && uFile1) {
			var lastIndex1 = fileName1.lastIndexOf("\\");
			if (lastIndex1 >= 0) {
				fileName1 = fileName1.substring(lastIndex1 + 1);
			}
			var reader1 = new FileReader();
			reader1.readAsDataURL(uFile1);
			reader1.onload = function() {
				data1 = reader1.result.split(",");
				x1.push(data1[1]);

				// After loading the first file, proceed with second document file
				handleSecondDocument();
			};
		} else {
			// If no file is selected for the first document, handle the second directly
			handleSecondDocument();
		}

		function handleSecondDocument() {
			// Handling second document file
			var currentDate = new Date();
			var day = currentDate.getDate();
			var month = currentDate.getMonth() + 1;
			var year = currentDate.getFullYear();
			var cDate = `${day}-${month}-${year}`;
			var createdDoc = $("#createdBy").val();
			var accessModifyStatus = $("#accessModifyStatus").val();
			var selectedOption = $("#selectedParentPath").val();
			var folderPath = selectedOption;
			var docVersion = "";
			if (accessModifyStatus == 0) {
				docVersion = 1;
			} else {
				var currentVersion = parseFloat($("#accessVersion").val());

				docVersion = (currentVersion + 0.1).toFixed(1);
			}
			if (fileName2 && uFile2) {
				var lastIndex2 = fileName2.lastIndexOf("\\");
				if (lastIndex2 >= 0) {
					fileName2 = fileName2.substring(lastIndex2 + 1);
				}
				var reader2 = new FileReader();
				reader2.readAsDataURL(uFile2);
				reader2.onload = function() {
					data2 = reader2.result.split(",");
					x2.push(data2[1]);



					// After loading both files, construct and push uploadData
					var uploadData = {
						docid: $("#docId").val(),
						docControl: $("#docControls").val(),
						date: cDate,
						folderPath: folderPath,
						documentName: $row.find(".docNoclss").val(),
						documentFile: x1, // Store data for first document
						documentFileAccessImage: x2, // Store data for second document
						fileName: fileName1,
						fileNameAccessImage: fileName2,
						originalFileNameAccessImage: fileName2,
						imageNameEdit: $row.find(".uploadHidCls").val(),
						imageNameEditAccessImage: $row.find(".uploadHidCls1").val(),
						description: $row.find(".docDesc").val(),
						docUniqueId: $("#docUniqueId").val(),
						tags: $row.find(".docTags").val(),
						folderName: $row.find(".documentclss option:selected").text(),
						version: docVersion,
						accessBy: createdDoc,


					};

					uploadList.push(uploadData);
					//checkUploadCompletion();
				};
			} else {
				// If no file is selected for the second document, proceed to construct uploadData
				var uploadData = {
					docid: $("#docId").val(),
					docControl: $("#docControls").val(),
					date: cDate,
					folderPath: folderPath,
					documentName: $row.find(".docNoclss").val(),
					documentFile: x1, // Store data for first document
					documentFileAccessImage: [], // Store data for second document
					fileName: fileName1,
					fileNameAccessImage: [],
					originalFileNameAccessImage: [],
					imageNameEdit: $row.find(".uploadHidCls").val(),
					imageNameEditAccessImage: $row.find(".uploadHidCls1").val(),
					description: $row.find(".docDesc").val(),
					docUniqueId: $("#docUniqueId").val(),
					tags: $row.find(".docTags").val(),
					folderName: $row.find(".documentclss option:selected").text(),
					version: docVersion,
				};

				uploadList.push(uploadData);
			}
		}
	});


	/*setTimeout(function() {
		saveAllDocumentModify(uploadList);
	}, 1000)*/




	function saveAllDocumentModify(datas) {

		$("#documentUpload").hide();
		//$('.loader').show();
		$.ajax({

			type: "POST",
			url: "document-control-modify",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					// console.log(JSON.stringify(datas))
					$("#documentUploadModal").modal('hide');
					$("#docDetailsDiv").hide();
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".btn-hs").show();
					cancel();
				} else {
					$('.loader').hide();
					$("#messageParagraph").text("Something Went Wrong");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".btn-hs").show();
					cancel();
				}

			},
			error: function(datas) {
				console.log(response, 'err resp');

				$('.loader').hide();
			}
		})

	}
}
function saveDoc(event) {
	event.preventDefault();
	$("#documentUploadModal").modal("hide");
	var datas = [];
	var imageValid = true;
	var uploadList = [];
	var valid = true;
	$("#doctbodyData > tr").each(
		function() {
			var uFile = $(this).find(".document")[0].files[0];
			var prevFile = $(this).find(".uploadHidCls").val();
			console.log("ufile-->", uFile);
			console.log("file name data-->", prevFile);
			//			var fileName = $(this).find(".document").val();
			var fileName = $(this).find(".document").val() !== undefined
				? $(this).find(".document").val()
				: $("#originalDocFileName").val();

			console.log("fileName-->", fileName);
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

				//x = $(this).find(".uploadHidCls").val() ? [$(this).find(".uploadHidCls").val()] : [];
				fileName = $(this).find(".uploadHidCls").val();

			}
			console.log("xx-->", x);

			uploadData = {};

			uploadData['docid'] = $("#docId").val() ? $("#docId").val() : '';
			var docType = $("#docControls").val();
			var selectedOption = $('#doc_folderName option:selected');
			var folderPath = $("#selectedParentPath").val();

			var currentDate = new Date();
			let day = currentDate.getDate();
			let month = currentDate.getMonth() + 1;
			let year = currentDate.getFullYear();
			let cDate = `${day}-${month}-${year}`;
			uploadData['docControl'] = docType;
			uploadData['date'] = cDate;

			uploadData['documentName'] = $(this)
				.find(".docNoclss").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadData['originalFileName'] = fileName;
			uploadData['imageNameEdit'] = $(this).find(
				".uploadHidCls").val();
			uploadData['description'] = $(this).find(".docDesc").val();
			uploadData['tags'] = $(this).find(".docTags").val();

			uploadData['folderName'] = $("#selectedFolderName").val()
			uploadData['folderId'] = $("#selectedFolderId").val();
			uploadData['folderPath'] = folderPath;


			if ($("#docId").val() == null || $("#docId").val() == '') {
				uploadData['version'] = 1;
			} else {
				var currentVersion = parseFloat($("#version").val());
				uploadData['version'] = (currentVersion + 0.1).toFixed(1);
			}
			uploadList.push(uploadData);

		});


	if (uploadList[0].documentName == "") {
		toastr.error('Document Name Needed.');
		valid = false;
		return;
	}

	/*if (uploadList[0].fileName == "") {
		console.log("docs name blank..");
		toastr.error('Document File Needed.');
		valid = false;
		return;
	}*/




	console.log("uploadlist -->", uploadList);

	if (valid) {
		setTimeout(function() {
			saveAllDocument(uploadList);
		}, 500)
	}







}

function saveAllDocument(datas) {

	$("#documentUpload").hide();
	//$('.loader').show();
	$.ajax({

		type: "POST",
		url: "document-control-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				// console.log(JSON.stringify(datas))
				$("#documentUploadModal").modal('hide');
				$("#docDetailsDiv").hide();
				var pages;
				var pageno = 1;
				//gridOptions.api.setRowData(rowData);
				/*agGrid.simpleHttpRequest({
					url: "document-control-view?pageno=" + pageno
				}).then(function(data) {

					if (data.code == "Success") {// Parse the JSON string to extract pages
						const jsonData = JSON.parse(data.body[0]);

						const len = jsonData.data.length;
						$('#document').find('span').html(len);
						//gridOptions.api.setRowData(jsonData.data);

						if (len > 0) {
							$('#totalPageno').val(jsonData.pages);
							pages = jsonData.pages;
						}

						createPagination(pages, pageno);
					} else {
						//gridOptions.api.setRowData([])
					}

					$('.loader').hide();

				});*/

				viewWorkSpace()
					.then(() => {
						renderFolderView(currentFolderData);
						currentPath = [];
						$('#breadcrumb').text('');
						$('#backButton').click(goBack);
						$("#backButton").hide();
						disableAllTheField();
					})
					.catch((err) => {
						console.error('ERROR ', err.message);
					});

				disableAllTheField();

				toastr.success('Data Saved Successfully');
				$(".read-more").hide();
				$(".btn-hs").show();
				cancel();
			} else {
				$('.loader').hide();
				toastr.error('Something Went Wrong');
				$(".btn-hs").show();
				$(".read-more").hide();
				cancel();
			}

		},
		error: function(datas) {
			console.log(response, 'err resp');

			$('.loader').hide();
		}
	})

}

function save() {
	var userId = $("#userId").val();

	var createdDoc = $("#createdBy").val();

	console.log("userd id-->", userId);

	console.log("created doc-->", createdDoc);
	var docId = $("#docId").val();
	console.log("doc id-->" , docId);
	saveDoc(event);
	/*if (userId != createdDoc && docId == "") {
		console.log("1st..");
		saveDoc(event);
	}
	else if (userId == createdDoc && docId != "") {
		saveDoc(event);
		console.log("2nd...");
	}
	else {
		modifyDoc();
		console.log("3rd....")
	}*/


}


function deleteFun() {
	$('#delete').modal('show');
}

// function for delete plan  
function deleteOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.docid;
	});

	var docId = selectedRowsString;

	if (docId) {
		$.ajax({
			type: "GET",
			url: 'document-control-delete?id=' + docId,
			success: function(response) {
				if (response.message == "Success") {
					var pages;
					var pageno = 1;
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							//	gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});

					cancel();
					$("#messageParagraph").text("File deleted sucessfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#delete').modal('hide');

				} else {
					$("#messageParagraph").text("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}

}
var deselectedRows = [];
function onRowSelected(event) {
	if (!event.node.isSelected()) {
		deselectedRows.push(event.data);
	}
}
var prevUsers = "";
function accessControl() {
	//var selectedNodes = gridOptions.api.getSelectedNodes();
	//selectedData = selectedNodes.map(node => node.data);
	//var id = selectedData.map(node => node.docid);
	var id = $("#historyId").text();
	$('#accessControlModal').modal('show');
	$('#documentId').val(id);

	$.ajax({
		type: "GET",
		url: "document-control-accessView?id=" + id,
		success: function(response) {
			if (response.message == "Success") {

				if (response.body.length > 0) {
					const jsonString = response.body.map(item => item.replace(/'/g, '"'));
					const jsonArray = jsonString.map(item => JSON.parse(item));
					var employeeIds = jsonArray.map(function(item) {
						return item.employeeId;
					});
					var employeeName = jsonArray.map(function(item) {
						return item.employeeName;
					});
					var jsonArrayData = employeeIds;
					gridOptionUsers.api.deselectAll();
					gridOptionUsers.api.forEachNode(function(node) {
						if (jsonArrayData.includes(node.data.employeeId)) {
							node.setSelected(true);
						}
					});
					prevUsers = gridOptionUsers.api.getSelectedRows();


					$("#selectedUser").val(employeeIds);
					$("#accessId").val(jsonArray[0].accessId);
					$("#selectedUserName").val(employeeName);
					$("#expirationDate").val(jsonArray[0].expiredDate);
					$("#remarks").val(jsonArray[0].remarks);
					$("#documentAccessType").val(jsonArray[0].accessType);
					var read = jsonArray[0].readStatus;
					if (read == "true") {
						$("#read").prop("checked", true);
					}
					var write = jsonArray[0].writeStatus;
					if (write == "true") {
						$("#write").prop("checked", true);
					}
					var deleteStatus = jsonArray[0].deleteStatus;
					if (deleteStatus == "true") {
						$("#deletee").prop("checked", true);
					}
					var accessType = jsonArray[0].accessType;
					if (accessType == "user") {
						$("#userListGrid").show();
						$("#userGroupListGrid").hide();
					}
					else if (accessType == "usergroup") {
						$("#userListGrid").hide();
						$("#userGroupListGrid").show();
					}
					else {
						$("#userListGrid").hide();
						$("#userGroupListGrid").hide();
					}
				}
				else {
					$("#selectedUser").val('');
					$("#accessId").val('');
					$("#selectedUserName").val('');
					$("#expirationDate").val('');
					$("#remarks").val('');
					$("#documentAccessType").val('');
					document.querySelectorAll('.styled-checkbox').forEach(function(checkbox) {
						checkbox.checked = false;
					});
					$("#userListGrid").hide();
					$("#userGroupListGrid").hide();
				}
			} else
				("error" + console.message)
		}
	});

}

function accessControlData(id) {
	$('#accessControlModal').modal('show');
	$('#documentId').val(id);

	$.ajax({
		type: "GET",
		url: "document-control-accessView?id=" + id,
		success: function(response) {
			if (response.message == "Success") {
				console.log('Response Body', response.body)
				const jsonString = response.body.map(item => item.replace(/'/g, '"'));
				// Convert to JSON array
				const jsonArray = jsonString.map(item => JSON.parse(item));
				console.log("EditAccess", jsonArray);
				var employeeIds = jsonArray.map(function(item) {
					return item.employeeId;
				});
				var employeeName = jsonArray.map(function(item) {
					return item.employeeName;
				});
				var jsonArrayData = employeeIds;
				gridOptionUsers.api.forEachNode(function(node) {
					if (jsonArrayData.includes(node.data.employeeId)) {
						node.setSelected(true);
					}
				});
				prevUsers = gridOptionUsers.api.getSelectedRows();
				$("#selectedUser").val(employeeIds);
				$("#accessId").val(jsonArray[0].accessId);
				$("#selectedUserName").val(employeeName);
				$("#expirationDate").val(jsonArray[0].expiredDate);
				$("#remarks").val(jsonArray[0].remarks);
				$("#documentAccessType").val(jsonArray[0].accessType);
				var read = jsonArray[0].readStatus;
				if (read == "true") {
					$("#read").prop("checked", true);
				}
				var write = jsonArray[0].writeStatus;
				if (write == "true") {
					$("#write").prop("checked", true);
				}
				var deleteStatus = jsonArray[0].deleteStatus;
				if (deleteStatus == "true") {
					$("#deletee").prop("checked", true);
				}
				var accessType = jsonArray[0].accessType;
				if (accessType == "user") {
					$('.button2').prop('disabled', true);
					$('.button1').prop('disabled', false);
				}
				else if (accessType == "usergroup") {
					$('.button1').prop('disabled', true);
					$('.button2').prop('disabled', false);
				}
				else {
					$('.button1').prop('disabled', false);
					$('.button2').prop('disabled', false);
				}
			} else
				("error" + console.message)
		}
	});
}
/*function accessControl1(){
	$('#myModal1').modal('show');
	
}*/
function reminderOpen() {
	$('#myModalReminder').modal('show');

}
function accessControlOpen() {
	$('#myModal').modal('show');
	$('#myModal1').modal('hide');
}
function retentionOpen() {
	$('#myModalRetention').modal('show');
}
function lockfileOpen() {
	$('#myModalLock').modal('show');
}
function newVersionOpen() {
	$('#uploadModal').modal('show');
}
function startWorkFlowOpen() {
	$('#myModalStartWorkflow').modal('show');
}






let totalProgress = 0;
let obj;


function convertToBase64() {
	const folderInput = document.getElementById('uploadDocFolder');
	const files = folderInput.files;

	let mainFolderName = files[0].webkitRelativePath;
	let Folder = [];
	let uploadList = [];
	const progressStep = 100 / files.length;
	var accessModifyStatus = $("#accessModifyStatus").val();
	var createdDoc = $("#createdBy").val();


	$("#folderTableStatus").show();
	$("#noFolderSelectedDesign").hide();

	for (let i = 0; i < files.length; i++) {
		let file = files[i]; // Define file variable within the loop
		let data = [];
		let x = [];
		let reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function() {
			data = reader.result.split(",");
			x.push(data[1]);

			updateProgress(100, file.name); // Pass file information to updateProgress

			let obj1 = {};
			obj1.fileName = file.name;
			obj1.type = file.type;
			obj1.documentName = file.webkitRelativePath;
			obj1.documentFile = x;
			uploadList.push(obj1);
		};
	}

	setTimeout(function() {
		obj = {};
		obj['docid'] = $("#docId").val();
		obj['docUniqueId'] = $("#controlWorkspaceId").val();
		var docType = $("#docControls").val();
		var currentDate = new Date();
		let day = currentDate.getDate();
		let month = currentDate.getMonth() + 1;
		let year = currentDate.getFullYear();
		let cDate = `${day}-${month}-${year}`;
		var selectedOption = $('#doc_folderNameUpload option:selected');
		var folderPath = selectedOption.data('code');
		obj['docControl'] = docType;
		obj['date'] = cDate;
		obj['documentList'] = uploadList;
		var folName = mainFolderName.split('/')[0];
		obj['documentName'] = folName;
		obj['folderName'] = $("#doc_folderNameUpload").val();
		obj['folderPath'] = folderPath;
		obj['tagFolder'] = $("#tagFolder").val();

		//var $("#docId").val();

		if ($("#docId").val() == null || $("#docId").val() == '') {
			obj['version'] = 1;
		} else if ($("#docId").val() != '' && accessModifyStatus == "0") {
			obj['version'] = $("#version3").val();
			obj['accessBy'] = createdDoc;
		} else if ($("#docId").val() != '' && accessModifyStatus == "1") {
			var currentVersion = parseFloat($("#accessVersion").val());
			obj['version'] = (currentVersion + 0.1).toFixed(1);
			obj['accessBy'] = createdDoc;
		}
		else {
			var version3 = $("#version3").val();
			var currentVersion = parseFloat($("#version3").val());
			obj['version'] = (currentVersion + 0.1).toFixed(1);
		}


		//console.log(obj, "FILE DATA WITH BASE 64 encoding");
	}, '000');




}

function updateProgress(progress, filename) {
	var divTag = '<tr><td><div class="form-group">' + filename + '</div></td><td><div class="form-group"><div id="loadingAnimation"><div id="loadingProgress" class="progress-bar" style="width:' + progress + '%"><span class="progress-text">' + progress + '%</span></div></div></div></td></tr>';
	$("#statusTbl").append(divTag);
}



function saveFolder() {
	$(".loader").show();
	var data = JSON.stringify(obj);

	console.log("Data-->", data)
	$("#folderUploadModal").modal('hide');
	$.ajax({
		type: "POST",
		url: "document-control-upload-folder",
		contentType: "application/json",
		data: data,
		success: function(response) {
			// Handle the success response if needed
			$("#folderUploadModal").modal('hide');
			var pages;
			var pageno = 1;
			var rowData = [];
			//gridOptions.api.setRowData(rowData);
			agGrid.simpleHttpRequest({
				url: "document-control-view?pageno=" + pageno
			}).then(function(data) {

				if (data.code == "Success") {// Parse the JSON string to extract pages
					$("#documentUploadModal").modal('hide');
					$("#docDetailsDiv").hide();
					$("#modifyFolder").attr('disabled', true);
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							//gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});
					$(".btn-hs").show();
					cancel();
				} else {
					//	gridOptions.api.setRowData([])
				}
				$('.loader').hide();
			});
			toastr.success('Folder Saved Sucessfully');
			$(".loader").hide();
		},
		error: function(error) {
			// Handle the error if needed
		}
	});
}


function modifyFolderDoc() {
	$(".loader").show();
	var uploadList = [];
	let obj1 = {};

	$("#statusTbl tr").each(function(index) {
		var $fileInput = $(this).find(".document");

		if ($fileInput.length === 0) {
			return; // Skip if no file input found in this row
		}

		var uFile = $fileInput[0].files[0];
		var fileName = $fileInput.val();

		if (fileName && uFile) {
			var data = [];
			var x = [];

			var lastIndex = fileName.lastIndexOf("\\");
			if (lastIndex >= 0) {
				fileName = fileName.substring(lastIndex + 1);
			}

			// Create a new FileReader for each file
			var reader = new FileReader();

			// Wrap the onload function in a closure to retain the correct fileName and row reference
			reader.onload = (function(fileName, $row, index) {
				return function(e) {
					data = e.target.result.split(",");
					x.push(data[1]);

					// Create uploadData object after reading file
					var uploadData = {};
					uploadData['docid'] = $("#docId").val();
					uploadData['documentFile'] = x;
					uploadData['fileName'] = fileName;
					uploadData['originalFileName'] = fileName;
					uploadData['imageNameEdit'] = $row.find(".uploadHidCls2").val();
					uploadData['originalDocFile'] = $("#originalFile_" + index).val();
					uploadData['docUniqueId'] = $("#docUniqueId_" + index).val();
					uploadData['folderName'] = $("#doc_folderNameUpload").val();
					uploadData['folderPathNameId'] = $("#folderPathNameId").html();
					var version = $("#version3").val();
					uploadData['version'] = (parseFloat(version) + 0.1).toFixed(1);
					uploadData['accessBy'] = $("#createdBy").val();
					var selectedOption = $('#doc_folderNameUpload option:selected');
					var folderPath = selectedOption.data('code');
					uploadData['folderPath'] = folderPath;

					uploadList.push(uploadData);

				};
			})(fileName, $(this), index); // Pass the index here

			// Read the file as Data URL
			reader.readAsDataURL(uFile);
		} else {
			console.warn('No file selected or file input not found.');
		}
	});

	setTimeout(function() {
		modifyFolder(uploadList);
	}, 1000)

}

function modifyFolder(datas) {
	console.log('datas', datas)
	$("#folderUploadModal").modal('hide');
	$.ajax({
		type: "POST",
		url: "document-control-folder-modify",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			// Handle the success response if needed

			$("#folderUploadModal").modal('hide');
			var pages;
			var pageno = 1;
			var rowData = [];
			//gridOptions.api.setRowData(rowData);
			agGrid.simpleHttpRequest({
				url: "document-control-view?pageno=" + pageno
			}).then(function(data) {

				if (data.code == "Success") {// Parse the JSON string to extract pages
					$("#documentUploadModal").modal('hide');
					$("#docDetailsDiv").hide();
					$("#modifyFolder").attr('disabled', true);
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							//gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".btn-hs").show();
					cancel();
				} else {
					//gridOptions.api.setRowData([])
				}

				$('.loader').hide();

			});
			$("#messageParagraph").text("Folder Saved Sucessfully");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$(".loader").hide();

		},
		error: function(error) {
			// Handle the error if needed
		}
	});
}
function closeModal() {
	var options = "Select";
	$("#docControls").val(options);
}

$(document).on('click', '.fileUpload', function() {
	saveFileEdit();
});

function downloadFile(fileUrl) {
	window.location.href = fileUrl;
}
var documentList = [];
var jsonArr = [];
/*function editDocument(id, type) {

	let uType = window.atob(type);

	if (uType == 'Upload Document') {
		$("#documentUpload").show();
		$("#newWord").hide();
		$("#demo").hide();
		$(".lnkUpload").hide();
		$("#documentUploadModal").modal('show');

		$.ajax({
			type: "GET",
			url: "document-control-edit?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
					console.log('Response Body', response.body)
					const jsonString = response.body.map(item => item.replace(/'/g, '"'));

					// Convert to JSON array
					const jsonArray = jsonString.map(item => JSON.parse(item));

					console.log('jsonArrayEdit', jsonArray)
					var readStatus = jsonArray[0].readStatus;
					var writeStatus = jsonArray[0].writeStatus;
					var deleteStatus = jsonArray[0].deleteStatus;
					var userId = $("#userId").val();
					if (userId == jsonArray[0].createdBy) {
						$("#accessAttach").hide();
						$("#accessAttachFile").hide();
					} else {
						$("#accessAttach").show();
						$("#accessAttachFile").show();
					}

					$("#docControls").val(jsonArray[0].docControl);
					$("#docnoid_").val(jsonArray[0].documentName);
					$("#docId").val(jsonArray[0].docid);
					$("#docUniqueId").val(jsonArray[0].docUniqueId);
					$("#version").val(jsonArray[0].version);
					$("#doc_desc").val(jsonArray[0].description);
					$("#doc_tags").val(jsonArray[0].tags);
					$("#doc_folderName").val(jsonArray[0].folderWorkSpace);
					$("#createdBy").val(jsonArray[0].createdBy);
					$("#accessModifyStatus").val(jsonArray[0].accessModifyStatus);
					$("#accessVersion").val(jsonArray[0].accessVersion);
					var fileName = jsonArray[0].fileName;
					var folderName = jsonArray[0].orginalDocUrl;
					console.log('folderName', folderName)
					if (fileName != null) {
						$('#uploadHidden_0').val(fileName);
						var ext = jsonArray[0].fileName.split(".");
						$("#imageName_0").html(fileName);
						if (ext[1] == "jpg" || ext[1] == "png") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue"  onclick=viewImage("'
								+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
						} else if (ext[1] == "pdf") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
								+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
						} else if (ext[1] == "xls" || ext[1] == "xlsx") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-excel-o" style="color: red" onclick=viewImage("'
								+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
						} else if (ext[1] == "doc" || ext[1] == "dox" || ext[1] == "docx") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-word-o" style="color: red" onclick=viewImage("'
								+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
						} else if (ext[1] == "ppt" || ext[1] == "pptx") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-file-powerpoint-o" style="color: orange" onclick=viewImage("'
								+ fileName + '","' + window.btoa(folderName) + '")></i></a> </div>';
						} else {
							var LightImg = "<div class='uploadicon position-l'> </div>";
						}
						console.log('LightImg', LightImg)
						$("#uploadedBillDiv_0").html(LightImg);
					}

					var fileNameAccess = jsonArray[0].accessImage;
					var accessDocUrl = jsonArray[0].newDocUrl;
					if (fileNameAccess != null) {
						$('#uploadHidden1_0').val(fileNameAccess);
						var ext = fileNameAccess.split(".");
						$("#imageName1_0").html(fileNameAccess);
						if (ext[1] == "jpg" || ext[1] == "png") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue"  onclick=viewImage("'
								+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
						} else if (ext[1] == "pdf") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
								+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
						} else if (ext[1] == "xls" || ext[1] == "xlsx") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-excel-o" style="color: red" onclick=viewImage("'
								+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
						} else if (ext[1] == "doc" || ext[1] == "dox" || ext[1] == "docx") {
							var LightImg = '<div class ="uploadicon position-l" id="imageId"><a class="example-image-link" target="_balnk"><i class="fa fa-file-word-o" style="color: red" onclick=viewImage("'
								+ fileNameAccess + '","' + window.btoa(accessDocUrl) + '")></i></a> </div>';
						} else {
							var LightImg = "<div class='uploadicon position-l'> </div>";
						}
						console.log('LightImg', LightImg)
						$("#uploadedBillDiv1_0").html(LightImg);
					}


					if (readStatus == "true" && writeStatus == "false") {
						$("#docnoid_").prop("disabled", true);
						$("#doc_folderName").prop("disabled", true);
						$("#doc_desc").prop("disabled", true);
						$("#doc_tags").prop("disabled", true);
						$("#uploadDoc_0").prop("disabled", true);
						$("#uploadDoc1_0").prop("disabled", true);
					}
					else if (readStatus == "true" && writeStatus == "true") {
						$("#docnoid_").prop("disabled", false);
						$("#doc_folderName").prop("disabled", false);
						$("#doc_desc").prop("disabled", false);
						$("#doc_tags").prop("disabled", false);
						$("#uploadDoc_0").prop("disabled", true);
						$("#uploadDoc1_0").prop("disabled", false);
					} else {
						$("#docnoid_").prop("disabled", false);
						$("#doc_folderName").prop("disabled", false);
						$("#doc_desc").prop("disabled", false);
						$("#doc_tags").prop("disabled", false);
						$("#uploadDoc_0").prop("disabled", false);
						$("#uploadDoc1_0").prop("disabled", false);
					}


				} else
					("error" + console.message)
			}
		});
	}
	else if (uType == 'Upload Folder') {
		$("#editfolderUpload").show();
		$(".lnkUpload").hide();
		$("#saveFolder").hide();
		$("#folderPath").hide();
		$("#folderUploadModal").modal('show');
		$("#statusTbl").empty();
		$.ajax({
			type: "GET",
			url: "document-control-edit-uploadfolder?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
					const jsonString = response.body.map(item => item.replace(/'/g, '"'));
					const jsonArr = jsonString.map(item => JSON.parse(item));
					console.log('jsonArrFolderdfghj', jsonArr)
					var folderPathId = ((jsonArr[0].folderName.replace(" - ", "\\") + "\\") + "" + jsonArr[0].documentName);
					$("#version3").val(jsonArr[0].version);
					$("#doc_folderNameUpload").val(jsonArr[0].folderName);
					$("#docId").val(jsonArr[0].docid);
					$("#folderPathNameId").html(folderPathId);
					$("#createdBy").val(jsonArr[0].createdBy);
					var readStatus = jsonArr[0].readStatus;
					var writeStatus = jsonArr[0].writeStatus;
					var deleteStatus = jsonArr[0].deleteStatus;

					if (readStatus == "true" && writeStatus == "false") {

						$('#uploadDoc2_0').prop('disabled', true);
					}
					else if (readStatus == "true" && writeStatus == "true") {
						$("#docnoid_").prop("disabled", false);
						$("#doc_folderName").prop("disabled", false);
						$("#doc_desc").prop("disabled", false);
						$("#doc_tags").prop("disabled", false);
						$("#uploadDoc_0").prop("disabled", true);
					} else {
						$("#docnoid_").prop("disabled", false);
						$("#doc_folderName").prop("disabled", false);
						$("#doc_desc").prop("disabled", false);
						$("#doc_tags").prop("disabled", false);
						$("#uploadDoc_0").prop("disabled", false);
					}

					var documentListFolder = jsonArr[0].documentList;

					console.log('documentListFolder', documentListFolder)
					for (var i = 0; i < documentListFolder.length; i++) {
						var progress = 100; // Replace this with the actual progress value if available
						var downloadIcon = '<i class="fas fa-download" style="cursor: pointer; margin-left: 8px;" onclick="downloadFile(\'' + documentListFolder[i].documentUrl + '\')"></i>';

						var divTag = '<tr>' +
							'<td><div class="form-group" style="display: flex; align-items: center;">' +
							documentListFolder[i].fileName +
							'<input type="hidden" id="originalFile_' + i + '" value="' + documentListFolder[i].originalFile + '">' +
							'<input type="hidden" id="docUniqueId_' + i + '" value="' + jsonArr[i].docUniqueId + '">' +
							downloadIcon +
							'<input type="file" class="document" id="uploadDoc2_' + i + '" name="userImage" accept="*" onchange="saveMultiFile2(event)" style="margin-left: 8px;" />' +
							'<input type="hidden" id="uploadHidden2_' + i + '" class="uploadHidCls2">' +
							'<div id="uploadedBillDiv2_' + i + '" align="center" class="uploadedBillCls"></div>' +
							'<div id="imageName2_' + i + '" class="imageName"></div>' +
							'<div id="dltImage2_' + i + '"></div>' + '</div></td>' +
							'<td><div class="form-group" style="width: 100%;"><div id="loadingAnimation">' +
							'<div id="loadingProgress" class="progress-bar" style="width:' + progress + '%; height: 30px; font-size: 16px;">' +
							'<span class="progress-text">' + progress + '%</span></div></div></div></td>' +
							'</tr>';

						$("#statusTbl").append(divTag); // Append to tbody with id "statusTbl"

					}
					jsonArr.forEach((item, index) => {
						var fileNameAccess = item.newDocUrl;
						console.log('fileNameAccess', fileNameAccess);

						if (fileNameAccess != null) {
							$('#uploadHidden2_' + index).val(fileNameAccess);
							var ext = fileNameAccess.split(".").pop();
							$("#imageName2_" + index).html(fileNameAccess);
							var LightImg = "<div class='uploadicon position-l'> </div>";
							if (ext === "jpg" || ext === "png") {
								LightImg = '<div class="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-picture-o" style="color: blue" onclick=viewImageFolderDoc("' + fileNameAccess + '","' + window.btoa(item.folderName) + '")></i></a></div>';
							} else if (ext === "pdf") {
								LightImg = '<div class="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImageFolderDoc("' + fileNameAccess + '","' + window.btoa(item.folderName) + '")></i></a></div>';
							} else if (ext === "xls" || ext === "xlsx") {
								LightImg = '<div class="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-file-excel-o" style="color: green" onclick=viewImageFolderDoc("' + fileNameAccess + '","' + window.btoa(item.folderName) + '")></i></a></div>';
							} else if (ext === "doc" || ext === "docx") {
								LightImg = '<div class="uploadicon position-l" id="imageId"><a class="example-image-link" target="_blank"><i class="fa fa-file-word-o" style="color: blue" onclick=viewImageFolderDoc("' + fileNameAccess + '","' + window.btoa(item.folderName) + '")></i></a></div>';
							}

							$("#uploadedBillDiv2_" + index).html(LightImg);
						}
					});
				}
			}
		})
	}
	else if (uType = 'Add a Link') {
		$("#uploadLink").show();
		$("#lnkUploadModal").modal('show');
		$.ajax({
			type: "GET",
			url: "document-control-edit-link?id=" + id,
			success: function(response) {
				if (response.message == "Success") {

					const jsonString = response.body.map(item => item.replace(/'/g, '"'));
					// Convert to JSON array
					const jsonArray = jsonString.map(item => JSON.parse(item));
					console.log('jsonArray', jsonArray)
					$("#docControls").val(jsonArray[0].docControl);
					$("#linkName").val(jsonArray[0].linkName);
					$("#linkUrl").val(jsonArray[0].linkUrl);
					$("#docId").val(jsonArray[0].docid);
					$("#version2").val(jsonArray[0].version);
					$("#desc").val(jsonArray[0].description);
					$("#tags").val(jsonArray[0].tags);
				}
			}
		})
	}
}*/
function downloadDocument(url) {
	window.location.href = url;
}
function deleteFolderDoc(id) {
	var divId = "docName1" + id;
	var fileName = "upload_" + id;
	var elementToRemove = document.getElementById(divId);
	var fileToRemove = document.getElementById(fileName).value;
	// Check if the element exists before trying to remove it
	if (elementToRemove) {
		// Remove the element
		elementToRemove.parentNode.removeChild(elementToRemove);
	} else {
		console.log("Element not found: " + divId);
	}
	var fileList = documentList.filter(item => item.fileName !== fileToRemove);

	let obj = {};
	obj['docid'] = jsonArr[0].docid;
	obj['docControl'] = jsonArr[0].docControl;
	obj['date'] = jsonArr[0].date;
	obj['documentList'] = fileList;
	obj['folderName'] = jsonArr[0].folderName;
	obj['imageNameEdit'] = fileToRemove;
	let currentVersion = parseFloat($("#version3").val());
	obj['version'] = (currentVersion + 0.1).toFixed(1);
	//	console.log(obj, "FILE");
	$.ajax({
		type: "POST",
		url: "document-control-delete-folder-files",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			$("#editfolderUpload").hide();
			var pages;
			var pageno = 1;
			var rowData = [];
			//	gridOptions.api.setRowData(rowData);
			agGrid.simpleHttpRequest({
				url: "document-control-view?pageno=" + pageno
			}).then(function(data) {

				if (data.code == "Success") {// Parse the JSON string to extract pages
					const jsonData = JSON.parse(data.body[0]);

					const len = jsonData.data.length;
					$('#document').find('span').html(len);
					//	gridOptions.api.setRowData(jsonData.data);

					if (len > 0) {
						$('#totalPageno').val(jsonData.pages);
						pages = jsonData.pages;
					}

					createPagination(pages, pageno);
				} else {
					//gridOptions.api.setRowData([])
				}

				$('.loader').hide();

			});
			$("#messageParagraph").text("File Deleted Successfully");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$(".btn-hs").show();
		}
	});
}
function viewImage(id, folderName) {
	var folderName = window.atob(folderName);
	var folder = folderName.split('-').map(part => part.trim());
	var f = folder[0] + "/" + folder[1];

	//window.open("/document/dms/" + f + "/" + id, '_blank');
	window.open(folderName);
}
function viewImageFolderDoc(id, folderName) {
	//window.open(id, '_blank');
	alert(id)
	window.open(id);
}
function saveLink() {
	let obj = {};
	var docType = $("#docControls").val();
	var currentDate = new Date();
	let day = currentDate.getDate();
	let month = currentDate.getMonth() + 1;
	let year = currentDate.getFullYear();
	let cDate = `${day}-${month}-${year}`;
	obj.docid = $("#docId").val();
	obj.docControl = docType;
	obj.date = cDate;
	obj.linkName = $("#linkName").val();
	obj.linkUrl = $("#linkUrl").val();
	obj.description = $("#desc").val();
	obj.tags = $("#tags").val();
	if ($("#docId").val() == null || $("#docId").val() == '') {
		obj.version = 1;
	} else {
		var currentVersion = parseFloat($("#version2").val());
		obj.version = (currentVersion + 0.1).toFixed(1);
	}
	$.ajax({
		type: "POST",
		url: "document-control-add-link",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {

			$("#lnkUploadModal").modal('hide');
			var pages;
			var pageno = 1;
			var rowData = [];
			//	gridOptions.api.setRowData(rowData);
			agGrid.simpleHttpRequest({
				url: "document-control-view?pageno=" + pageno
			}).then(function(data) {

				if (data.code == "Success") {// Parse the JSON string to extract pages
					const jsonData = JSON.parse(data.body[0]);

					const len = jsonData.data.length;
					$('#document').find('span').html(len);
					//	gridOptions.api.setRowData(jsonData.data);
					if (len > 0) {
						$('#totalPageno').val(jsonData.pages);
						pages = jsonData.pages;
					}
					createPagination(pages, pageno);
				} else {
					//	gridOptions.api.setRowData([])
				}
				$('.loader').hide();
			});
			toastr.success('Link Saved Successfully');
			$(".btn-hs").show();
		}
	});
}

//Pagination
var pages;
function createPagination(pages, page) {
	/*if ($("#totalPageno").val() == '') {
	var pages = 10;
} else {
	var pages = $("#totalPageno").val();
}*/

	var str = '<ul>';
	var active;
	var pageCutLow = page - 1;
	var pageCutHigh = page + 1;
	// Show the Previous button only if you are on a page other than the first
	if (page > 1) {
		str += '<li class="page-item previous no"><a onclick="createPagination(' + pages + ', ' + (page - 1) + ')">Previous</a></li>';
	}
	// Show all the pagination elements if there are less than 6 pages total
	if (pages < 6) {
		for (let p = 1; p <= pages; p++) {
			active = page == p ? "active" : "no";
			str += '<li class="' + active + '"><a onclick="createPagination(' + pages + ', ' + p + ')">' + p + '</a></li>';
		}
	}
	// Use "..." to collapse pages outside of a certain range
	else {
		// Show the very first page followed by a "..." at the beginning of the
		// pagination section (after the Previous button)
		if (page > 2) {
			str += '<li class="no page-item"><a onclick="createPagination(' + pages + ', 1)">1</a></li>';
			if (page > 3) {
				str += '<li class="out-of-range"><a onclick="createPagination(' + pages + ', ' + (page - 2) + ')">...</a></li>';
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
			str += '<li class="page-item ' + active + '"><a onclick="createPagination(' + pages + ', ' + p + ')">' + p + '</a></li>';

		}
		// Show the very last page preceded by a "..." at the end of the pagination
		// section (before the Next button)
		if (page < pages - 1) {
			if (page < pages - 2) {
				str += '<li class="out-of-range"><a onclick="createPagination(' + pages + ', ' + (page + 2) + ')">...</a></li>';
			}
			str += '<li class="page-item no"><a onclick="createPagination(pages, pages)">' + pages + '</a></li>';
		}
	}
	// Show the Next button only if you are on a page other than the last
	if (page < pages) {
		str += '<li class="page-item next no"><a onclick="createPagination(' + pages + ', ' + (page + 1) + ')">Next</a></li>';
		changePagination(page);
	} else if (page <= pages) {
		//str += '<li class="page-item next no"><a onclick="createPagination(pages, '+(page+1)+')">Next</a></li>';
		changePagination(page);
	}
	str += '</ul>';
	// Return the pagination string to be outputted in the pug templates
	//document.getElementById('pagination').innerHTML = str;
	return str;

}

function changePagination(page) {
	$('.loader').show();
	var pageno = page;
	agGrid.simpleHttpRequest({
		url: "document-control-view?pageno=" + pageno
	}).then(function(data) {
		if (data.code == "Success") {// Parse the JSON string to extract pages
			const jsonData = JSON.parse(data.body[0]);
			const len = jsonData.data.length;
			$('#document').find('span').html(len);
			//	gridOptions.api.setRowData(jsonData.data);

			//window.gridOptions.api.setRowData(jsonData.data);

			if (jsonData.data && jsonData.data.length > 0) {
				/*window.gridOptions.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});*/
			}

			if (len > 0) {
				$('#totalPageno').val(jsonData.pages);
				pages = jsonData.pages;
			}

		}
		$('.loader').hide();

	});
}
function merg() {
	console.log(selectedData, 'aaaaaaaaaa');

	//	let files = selectedData.map(item=>item.fileName);*/
	//console.log(files,'bbb');
	$.ajax({
		type: "POST",
		url: "document-control-merg-files",
		contentType: "application/json",
		data: JSON.stringify(selectedData),
		success: function(response) {

		}
	});
}

function userDocAccesss(id) {
	if (id == "0") {
		$('.button1').addClass('selected-btn');
		$('.button2').removeClass('selected-btn1');
		$("#usersModal").modal('show');
		$("#accessControlModal").modal('hide');
		$("#userListGrid").show();
		$("#userGroupListGrid").hide();
	} else if (id == "1") {
		$('.button2').addClass('selected-btn1');
		$('.button1').removeClass('selected-btn');
		$("#userGroupModal").modal('show');
		$("#accessControlModal").modal('hide');
		$("#userListGrid").hide();
		$("#userGroupListGrid").show();
	}
}

function cancelAccessControlModal() {
	$("#accessControlModal").modal('hide');
	$("#selectedUser").val("");
	$('.button2').removeClass('selected-btn1');
	$('.button1').removeClass('selected-btn');
	$("#remarks").text("");
	$("#expirationDate").val("");
}

function closeUserModal() {
	$("#usersModal").modal('hide');
	$("#accessControlModal").modal('show');

}
function closeUserGroupModal() {
	$("#userGroupModal").modal('hide');
	$("#accessControlModal").modal('show');
}

function pickUser() {
	var selectedRows = gridOptionUsers.api.getSelectedRows();
	prevUsers = selectedRows;
	if (selectedRows.length > 0) {
		var employeeId = selectedRows.map(selectedRows => selectedRows.employeeId);
		var empName = selectedRows.map(selectedRows => selectedRows.empName);
		$("#selectedUser").val(employeeId);
		$("#selectedUserName").val(empName);
		$("#documentAccessType").val("user");
		closeUserModal();
	} else {
		/*$("#messageParagraph").text("No User Selected");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');*/
	}
}

function pickUserGroup() {
	var selectedRows = gridOptionUsersGroups.api.getSelectedRows();
	if (selectedRows.length > 0) {
		var groupId = selectedRows[0].groupId;
		$("#selectedUser").val(groupId);
		const employeeNames = selectedRows[0].users.map(user => user.empName);
		$("#selectedUserName").val(employeeNames);
		$("#documentAccessType").val("usergroup");
		closeUserGroupModal();
	} else {
		$("#messageParagraph").text("No User Group Selected");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}

}

function giveAccess() {

	if ($("#documentAccessType").val() == 'user') {

		let obj = {};
		var listItem = [];
		//var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsUsers = gridOptionUsers.api.getSelectedRows();
		//var id = selectedRows[0].docid;

		var fileId = $("#accesDocId").text();

		console.log('prevUsers', prevUsers);
		var userDeselect = deselectedRows;
		userDeselect.forEach(function(user) {
			user.status = true;
		});
		var prevUsersSet = new Set(prevUsers.map(user => user.employeeId));

		// Filter selectedRowsUsers to find users not in prevUsers
		var newUsers = selectedRowsUsers.filter(user => !prevUsersSet.has(user.employeeId));

		newUsers.forEach(function(user) {
			user.status = false;
		});


		obj.employeeId = $("#selectedUser").val();
		obj.accessId = $("#accessId").val();
		obj.empName = $("#selectedUserName").val();
		obj.accessType = $("#documentAccessType").val();
		obj.readStatus = $("#read").is(":checked");
		obj.writeStatus = $("#write").is(":checked");
		obj.deleteStatus = $("#deletee").is(":checked");
		obj.remarks = $("#remarks").val();
		obj.expirationDate = $("#expirationDate").val();
		obj.document = fileId;
		obj.accessEmployee = selectedRowsUsers;
		obj.newUsers = newUsers;
		obj.userDeselect = userDeselect;
		listItem.push(obj);


		$.ajax({
			type: "POST",
			url: "document-control-access-add",
			contentType: "application/json",
			data: JSON.stringify(listItem),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					$("#selectedUserName").val('');
					$("#selectedUserName").empty();
					// console.log(JSON.stringify(datas))
					$("#accessControlModal").modal('hide');
					$("#docDetailsDiv").hide();
					document.querySelectorAll('.styled-checkbox').forEach(function(checkbox) {
						checkbox.checked = false;
					});
					$("#remarks").val('');
					gridOptionUsers.api.deselectAll();
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {

						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);

							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							//	gridOptions.api.setRowData(jsonData.data);

							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}

							createPagination(pages, pageno);
						} else {
							//gridOptions.api.setRowData([])
						}

						$('.loader').hide();

					});
					toastr.success('Data Saved Successfully');
					$(".btn-hs").show();
					cancel();
				}

			}
		});

	} //else if ($("#documentAccessType").val() == 'usergroup') {
	else {

		var selectedNodes = gridOptionUsersGroups.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var usersGroup = selectedData[0].users;
		/*	users.forEach(user => {
				result.push({
					"employeeId": user.uid,
					"name": user.name,
					"document": $("#documentId").val(),
					"accessType": $("#documentAccessType").val(),
					"groupId": $("#selectedUser").val(),
					"readStatus": $("#read").is(":checked"),
					"writeStatus": $("#write").is(":checked"),
					"deleteStatus": $("#deletee").is(":checked"),
					"remarks": $("#remarks").val()
				});
			});*/

		let obj = {};
		var listItem = [];
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsUsers = gridOptionUsers.api.getSelectedRows();
		var selectedRowsUsersGroups = gridOptionUsersGroups.api.getSelectedRows();
		var id = $("#accesDocId").text();

		//obj.groupId = selectedData[0].groupId;
		obj.accessType = $("#documentAccessType").val();
		obj.readStatus = $("#read").is(":checked");
		obj.writeStatus = $("#write").is(":checked");
		obj.deleteStatus = $("#deletee").is(":checked");
		obj.remarks = $("#remarks").val();
		obj.expirationDate = $("#expirationDate").val();
		obj.groupId = selectedRowsUsersGroups[0].groupId;
		obj.document = id;
		obj.accessEmployee = usersGroup;
		//obj.groupId = selectedData[0].groupId;
		listItem.push(obj);


		$.ajax({
			type: "POST",
			url: "document-control-access-add",
			contentType: "application/json",
			data: JSON.stringify(listItem),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					// console.log(JSON.stringify(datas))
					$("#accessControlModal").modal('hide');
					$("#docDetailsDiv").hide();
					var pages;
					var pageno = 1;
					//gridOptions.api.setRowData(rowData);
					/*agGrid.simpleHttpRequest({
						url: "document-control-view?pageno=" + pageno
					}).then(function(data) {
	
						if (data.code == "Success") {// Parse the JSON string to extract pages
							const jsonData = JSON.parse(data.body[0]);
	
							const len = jsonData.data.length;
							$('#document').find('span').html(len);
							gridOptions.api.setRowData(jsonData.data);
	
							if (len > 0) {
								$('#totalPageno').val(jsonData.pages);
								pages = jsonData.pages;
							}
	
							createPagination(pages, pageno);
						} else {
							gridOptions.api.setRowData([])
						}
	
						$('.loader').hide();
	
					});*/
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".btn-hs").show();
					cancel();
				}

			}
		});
	}
}
function uploadDocVersion() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	selectedData = selectedNodes.map(node => node.data);
	var id = selectedData.map(node => node.docid);
	var type = selectedData.map(node => node.docControl);

	//editDocument(id, type);

	if (type == 'Upload Document') {
		$("#documentUpload").show();
		$("#newWord").hide();
		$("#demo").hide();
		$(".lnkUpload").hide();
		$("#documentUploadModal").modal('show');

		$("#docnoid_").val('');
		$("#doc_folderName").val('');
		$("#uploadedBillDiv_0").empty();
		$("#imageName_0").empty();
		$("#dltImage_0").empty();
		$("#uploadDoc_0").val('');
		$("#imageName_0").val('');
		$("#doc_desc").val('');
		$("#doc_tags").val('');
		$("#uploadHidden_0").val('');
		$("#accessAttach").hide();
		$("#accessAttachFile").hide();
		$.ajax({
			type: "GET",
			url: "document-control-edit?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
				

					const jsonString = response.body.map(item =>
						item.replace(/(\w+)'(\w+)/g, '$1\u2019$2')
							.replace(/'/g, '"')
					);
					console.log("json string-->", jsonString);

					var jsonArray = "";
					try {
						jsonArray = jsonString.map(item => JSON.parse(item));
						console.log("jsonArray", jsonArray[0]);
					} catch (error) {
						console.error("JSON Parsing Error:", error.message);
					}
					$("#docControls").val(jsonArray[0].docControl);
					$("#docId").val(jsonArray[0].docid);
					$("#version").val(jsonArray[0].version);
					$("#createdBy").val(jsonArray[0].createdBy);
					$("#doc_folderName").val(jsonArray[0].folderName).prop('disabled', true);



				} else
					("error" + console.message)
			}
		});
	}
	else if (type == 'Upload Folder') {
		/*	$("#folderUploadModal").show();
			$(".lnkUpload").hide();*/

		$("#folderUploadModal").modal("show");
		$("#folderUpload").show();
		$("#newWord").hide();
		$("#documentUpload").hide();
		$("#uploadLink").hide();
		$("#demo").hide();
		$("#doc_folderNameUpload").val('');
		$("#tagFolder").val('');
		$("#statusTbl").empty();
		$("#saveFolder").show();
		$("#modifyFolderDoc").hide();
		$.ajax({
			type: "GET",
			url: "document-control-edit-uploadfolder?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
					const jsonString = response.body.map(item => item.replace(/'/g, '"'));
					// Convert to JSON array
					jsonArr = jsonString.map(item => JSON.parse(item));
					$("#version3").val(jsonArr[0].version);
					$("#docControls").val(jsonArr[0].docControl);
					$("#docId").val(jsonArr[0].docid);
					$("#doc_folderNameUpload").val(jsonArr[0].folderName).prop('disabled', true);
					var id = $("#docId").val();
					//documentList = jsonArr[0].documentList;
					//var folderName = documentList[0].documentName.split('/')[0];
					//console.log('folderName', jsonArr[0])

				}
			}
		})
	}
	else if (type = 'Add a Link') {
		$("#uploadLink").show();
		$("#lnkUploadModal").modal('show');
		$.ajax({
			type: "GET",
			url: "document-control-edit-link?id=" + id,
			success: function(response) {
				if (response.message == "Success") {

					const jsonString = response.body.map(item => item.replace(/'/g, '"'));
					// Convert to JSON array
					const jsonArray = jsonString.map(item => JSON.parse(item));
					$("#docControls").val(jsonArray[0].docControl);
					//$("#linkName").val(jsonArray[0].linkName);
					//$("#linkUrl").val(jsonArray[0].linkUrl);
					$("#docId").val(jsonArray[0].docid);
					$("#version2").val(jsonArray[0].version);
					//$("#desc").val(jsonArray[0].description);
					//$("#tags").val(jsonArray[0].tags);
				}
			}
		})
	}
}

function cancelBtnMail() {
	$("#mailModal").modal('hide');
}

function getUserMailId(docId) {
	var userid = $("#userId").val();

	//var selectedNodes = gridOptions.api.getSelectedNodes();
	//var selectedData = selectedNodes.map(node => node.data);
	var docid = docId;
	$("#mailModal").modal('show');
	agGrid.simpleHttpRequest({
		url: 'document-control-get-alluserEmailId?userid=' + userid + "&docid=" + docid
	}).then(function(data) {

		if (data.length > 0) {
			$("#subject").val(data[0].filePath);
			$("#docid").val(data[0].docid);
			$("#groupId").val(data[0].groupId);
			$("#fileName").val(data[0].fileName);
			$("#documentUrl").val(data[0].documentUrl);
			var empNames = [];
			var empmailId = [];
			var empIds = [];
			var filePaths = [];
			var docids = [];
			var groupIds = [];
			var fileNames = [];
			var documentUrls = [];



			if (data && data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					var empName = data[i].empName;
					var empEmail = data[i].empEmail;

					var empId = data[i].empId;
					var filePath = data[i].filePath;

					var docid = data[i].docid;
					var groupId = data[i].groupId;

					var fileName = data[i].fileName;
					var documentUrl = data[i].documentUrl;


					if (typeof empName === 'string' || typeof empEmail === 'string') {
						empNames.push(empName);
						empmailId.push(empEmail);

						empIds.push(empId);
						filePaths.push(filePath);

						docids.push(docid);
						groupIds.push(groupId);


						fileNames.push(fileName);
						documentUrls.push(documentUrl);

					}

					/*if (typeof empEmail === 'string') {
						empmailId.push(empEmail);
					}*/


				}
			}

			var empNamesString = empNames.join(', ');
			var empEmailIdsString = empmailId.join(', ');

			var empIdString = empIds.join(', ');
			var filePathString = filePaths.join(', ');

			var docidString = docids.join(', ');
			var groupIdsString = groupIds.join(', ');

			var fileNamesString = fileNames.join(', ');
			var documentUrlsString = documentUrls.join(', ');



			$("#empName").val(empNamesString);
			$("#emailId").val(empEmailIdsString);

			$("#empId").val(empIdString);
			//$("#filePath").val(filePathString); 
			//$("#subject").val(filePathString); 

			//$("#docid").val(docidString);
			//$("#groupId").val(groupIdsString); 

			//$("#fileName").val(fileNamesString);
			//$("#documentUrl").val(documentUrlsString); 



			var date = (new Date()).toISOString().split('T')[0];
			var newDate = changeDateFormat(date);
			$("#mailDate").val(newDate);
		}

	});
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
function addMail() {
	var userid = $("#userId").val();
	//var selectedNodes = gridOptions.api.getSelectedNodes();
	//var selectedData = selectedNodes.map(node => node.data);
	//var docid = selectedData.map(node => node.docid).join(',');

	var docid = $("#reminderPageId").text();

	var mailDate = $("#mailDate").val();
	var subject = $('#subject').val();
	var messages = $("#messages").val();
	var empName = $("#empName").val();
	var emailId = $("#emailId").val();
	var empId = $("#empId").val();
	var docid = $("#docid").val();
	var groupId = $("#groupId").val();
	var fileName = $("#fileName").val();
	var documentUrl = $("#documentUrl").val();

	$('#mailModal').modal('show');
	var validation = true;
	if (!validationUpdated("Date Required", 'mailDate'))
		validation = false;
	if (!validationUpdated("Subject Required", 'subject'))
		validation = false;
	if (!validationUpdated("Messages Required", 'messages'))
		validation = false;

	if (!validationUpdated("Mail To Required", 'emailId'))
		validation = false;

	if (validation) {
		$('.loader-modal').show();
		$("modal-body").addClass("overlay");
		$.ajax({
			type: "GET",
			url: "document-control-send-email-toall?userid=" + userid + "&mailDate=" + mailDate +
				"&subject=" + subject + "&messages=" + messages + "&emailId=" + emailId +
				"&empId=" + empId + "&empName=" + empName + "&docid=" + docid + "&fileName=" + fileName +
				"&groupId=" + groupId + "&documentUrl=" + documentUrl,
			async: false,
			success: function(response) {
				if (response.code == "success") {

					$("#messageParagraph").text("Reminder Send Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#mailModal').modal('hide');

				} else {
					$('.loader').hide();
				}

			},
			error: function(data) {
			}
		});
	}
}

function lockFile() {
	$("#lockFileModal").modal('show');
	var selectedRows = gridOptions.api.getSelectedRows();
	var docName = selectedRows[0].documentName;
	$("#lockFileDocName").html(docName);

}
function saveLock() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var item = {};
	item.docid = selectedRows[0].docid;
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "document-control-lockFile",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				$(".loader").hide();
				var pageno = 1;
				var rowData = [];
				gridOptions.api.setRowData(rowData);
				agGrid.simpleHttpRequest({
					url: "document-control-view?pageno=" + pageno
				}).then(function(data) {

					if (data.code == "Success") {// Parse the JSON string to extract pages
						const jsonData = JSON.parse(data.body[0]);

						const len = jsonData.data.length;
						$('#document').find('span').html(len);
						gridOptions.api.setRowData(jsonData.data);

						if (len > 0) {
							$('#totalPageno').val(jsonData.pages);
							pages = jsonData.pages;
						}

						createPagination(pages, pageno);
					} else {
						gridOptions.api.setRowData([])
					}

					$('.loader').hide();

				});
				$("#docDetailsDiv").hide();
				$("#lockFileModal").modal('hide');
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#demo").hide();
				$("#myGrid").show();


			},
			error: function(data) {
				$('.loader').hide();
			}
		});
	}, 1000)

}


//-----------------

function tabAccess(tabName) {

	accessControl();
	if (typeof tabName !== "string") {
		return;
	}
	disableAllTabs();
	$("#" + tabName).show();

}


function disableAllTabs() {
	$("#manageControl").hide();
	$("#history").hide();
	$("#accessControlPage").hide();
	$("#Reminders").hide();
}


function closeAllForms() {
	$("#docuementUploadDiv").hide();
	$("#folderUploadModal").hide();
	$("#linkUploadDiv").hide();

}

function naviGateUploadForm() {

	var documentType = $("#docControls").val();



	if (documentType == "Upload Document") {
		closeAllForms();
		$("#docuementUploadDiv").show();

	}
	else if (documentType == "Upload Folder") {
		closeAllForms();
		$("#folderUploadModal").show();

	}
	else if (documentType == "Add a link") {
		closeAllForms();
		$("#linkUploadDiv").show();
	}

}


function saveFormUpload() {

	var documentType = $("#docControls").val();

	if (documentType == "Upload Document") {
		console.log("Upload Document");
		save();
	}
	else if (documentType == "Upload Folder") {
		console.log("Upload Folder");
		saveFolder();
	}
	else if (documentType == "Add a link") {
		console.log("Upload Link");
		saveLink();
	}

}



function selectFirstRow() {

	//Automatically Select And Call The Starting Data
	setTimeout(function() {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Select the first row
			var selectedRows = gridOptions.api.getSelectedRows();
			if (selectedRows.length > 0) {
				var id = selectedRows[0].docid;
				var documentName = selectedRows[0].documentName;
				var folderName = selectedRows[0].folderName;
				//editRequisition(id, status);
				onChangeIntailApiCall(folderName, documentName, id)
			}
		}
	}, 1000);
}



//grid intializer

function employeeList() {

	agGrid.simpleHttpRequest({
		url: 'manage-control-get-user-listing'
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = resp.EmployeeList.length;
		gridOptionUsers.api.setRowData(resp.EmployeeList);
		$("#userList").find('span').html(len);
	});

}


function userGroupList() {

	agGrid.simpleHttpRequest({
		url: 'manage-control-get-usergroup-listing'
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = resp.userGroupList.length;
		gridOptionUsersGroups.api.setRowData(resp.userGroupList);
		$("#userGroup").find('span').html(len);
	});
}

function reminderMail(id) {
	getUserMailId(id)
}


function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	var docId = $("#accesDocId").text();
	if (tabElement.id == "reminderTab") {
		getUserMailId(docId);

	}
	else if (tabElement.id == "workflowTab") {
		startWorkFlow(folderName, documentName, document_id);
	}
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);

		tabTrigger.show();
	}
}

function previousButton(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	var docId = $("#accesDocId").text();
	if (tabElement.id == "reminderTab") {
		getUserMailId(docId);

	}
	else if (tabElement.id == "workflowTab") {
		startWorkFlow(folderName, documentName, document_id);
	}
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}


//  Global Variables For Rendering Of Folders..

let currentPath = [];
let currentFolderData = null;
let intialData = null;
let jsonData = [];
let currentPathDetails = [];
let folderPath = "";
let parentId = "";
let selectedWorkspaceId = '';
let inputWorkspaceId = '';
let GroupData = [];
let workSpaceId = "";
let commonAggridRow = [];
let selectedActivity = "";
let viewTemplate = "";




function viewWorkSpace() {
	return new Promise((resolve, reject) => {
		$.ajax({
			type: "GET",
			url: "workspace-new-folderDetails",
			success: function(response) {
				if (response.message == "Success") {
					const jsonArray = JSON.parse(response.body).filter(item => item !== null);
					jsonData = jsonArray;
					currentFolderData = jsonData.filter(item => item.parentId === "");
					intialData = currentFolderData;

					resolve();
				} else {
					reject(new Error("Error fetching workspace data"));
				}
			},
			error: function(err) {
				reject(err); // Reject the promise if there's an error with the AJAX call
			}
		});
	});
}


// Function to render the folders and files
/*function renderFolderView(folderData) {
	const folderView = $('#folderView');
	folderView.empty(); // Clear the current view

	folderData.forEach(item => {
		const icon = $('<div></div>').addClass('icon');
		const img = $('<img>').attr('src', item.type === 'folder' ? '../assets/images/folder.png' : '../assets/images/file-icon.png');
		const name = $('<div></div>').addClass('folderText').text(item.name || item.documentDetails.documentName);

		const createdOn = $('<div></div>').addClass('created-on hideElement').text(item.createdon || '');
		const createdBy = $('<div></div>').addClass('created-by hideElement').text(item.createdBy || '');
		const editIcon = $('<i></i>').addClass('bi bi-three-dots-vertical text-secondary edit-icon informationIcon').css({
			cursor: 'pointer',
			fontSize: '1.2rem'
		}).attr({
			'data-bs-toggle': 'tooltip',
			'data-bs-placement': 'top',
			'title': 'Folder Details'
		});
		editIcon.click((event) => {
			event.stopPropagation();
			handleSingleClick(item);
		});
		if (item.type === 'folder') {
			icon.click(() => openFolder(item));
			icon.append(img).append(name).append(createdOn).append(createdBy);
		} else if (item.type === 'file') {
			icon.click(() => window.open(item.documentDetails.documentUrl, '_blank'));
			icon.append(img).append(name).append(createdOn).append(createdBy).append(editIcon);
		}

		folderView.append(icon);
	});

	$("#new").click(addFolder);
	viewTemplate == "list" ? getListView() : getGridView();
}*/

/*function renderFolderView(folderData) {

	console.log("=============================> ", folderData)
	const folderView = $('#folderView');
	folderView.empty(); // Clear the current view

	folderData.forEach(item => {
		const icon = $('<div></div>').addClass('icon');
		let img;

		if (item.type === 'folder') {
			img = $('<img>').attr('src', '../assets/images/folder.png');
		} else if (item.type === 'file') {
			const fileName = item.documentDetails.fileName || item.documentDetails.originalFileName || '';
			const fileExt = fileName.split('.').pop().toLowerCase();

			if (['jpg', 'jpeg', 'png', 'gif', 'webp'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-file-image icon-size');
			} else if (fileExt === 'pdf') {
				img = $('<i></i>').addClass('bi bi-file-earmark-pdf icon-size');
			} else if (['mp4', 'webm', 'avi', 'mov'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-file-earmark-play-fill icon-size');
			} else if (['mp3', 'wav', 'ogg'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-file-music icon-size');
			} else if (['txt', 'doc', 'docx'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-file-text icon-size');
			} else {
				img = $('<i></i>').addClass('bi bi-file-earmark icon-size');
			}
		}
		const name = $('<div></div>').addClass('folderText').text(item.name || item.documentDetails.documentName);
		const createdOn = $('<div></div>').addClass('created-on hideElement').text(item.createdon || '');
		const createdBy = $('<div></div>').addClass('created-by hideElement').text(item.createdBy || '');

		const editIcon = $('<i></i>').addClass('bi bi-three-dots-vertical text-secondary edit-icon informationIcon').css({
			cursor: 'pointer',
			fontSize: '1.2rem'
		}).attr({
			'data-bs-toggle': 'tooltip',
			'data-bs-placement': 'top',
			'title': 'File Details'
		});
		editIcon.click((event) => {
			event.stopPropagation();
			handleSingleClick(item);
		});

		if (item.type === 'folder') {
			icon.click(() => openFolder(item));
			icon.append(img).append(name).append(createdOn).append(createdBy);
		} else if (item.type === 'file') {
			icon.click(() => window.open(item.documentDetails.documentUrl, '_blank'));
			icon.append(img).append(name).append(createdOn).append(createdBy).append(editIcon);
		}

		folderView.append(icon);
	});

	$("#new").click(addFolder);
	viewTemplate == "list" ? getListView() : getGridView();
}
*/
function renderFolderView(folderData) {
	console.log("=============================> ", folderData);
	const folderView = $('#folderView');
	folderView.empty(); // Clear the current view

	folderData.forEach(item => {
		const icon = $('<div></div>').addClass('icon');
		let img;

		if (item.type === 'folder') {
			img = $('<img>').attr('src', '../assets/images/folder.png');
		} else if (item.type === 'file') {
			const fileName = item.documentDetails.fileName || item.documentDetails.originalFileName || '';
			const fileExt = fileName.split('.').pop().toLowerCase();

			if (['jpg', 'jpeg', 'png', 'gif', 'webp'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-filetype-jpg icon-size');
			} else if (fileExt === 'pdf') {
				img = $('<i></i>').addClass('bi bi-file-earmark-pdf icon-size');
			} else if (['mp4', 'webm', 'avi', 'mov'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-filetype-mp4 icon-size');
			} else if (['mp3', 'wav', 'ogg'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-file-music icon-size');
			} else if (['txt', 'doc', 'docx'].includes(fileExt)) {
				img = $('<i></i>').addClass('bi bi-filetype-txt icon-size');
			} else {
				img = $('<i></i>').addClass('bi bi-file-earmark icon-size');
			}
		}

		const name = $('<div></div>').addClass('folderText').text(item.name || item.documentDetails.documentName);
		const createdOn = $('<div></div>').addClass('created-on hideElement').text(item.createdon || '');
		const createdBy = $('<div></div>').addClass('created-by hideElement').text(item.createdBy || '');

		const editIcon = $('<i></i>').addClass('bi bi-three-dots-vertical text-secondary edit-icon informationIcon').css({
			cursor: 'pointer',
			fontSize: '1.2rem'
		}).attr({
			'data-bs-toggle': 'tooltip',
			'data-bs-placement': 'top',
			'title': 'File Details'
		});
		editIcon.click((event) => {
			event.stopPropagation();
			handleSingleClick(item);
		});

		if (item.type === 'folder') {
			icon.click(() => openFolder(item));
			icon.append(img).append(name).append(createdOn).append(createdBy);
		} else if (item.type === 'file') {
			const fileUrl = item.documentDetails.documentUrl;
			const fileName = item.documentDetails.fileName || item.documentDetails.originalFileName || '';
			const fileExt = fileName.split('.').pop().toLowerCase();

			icon.click(() => {
				if (['mp4', 'webm', 'avi', 'mov'].includes(fileExt)) {
					const newTab = window.open();
					newTab.document.write(`
						<!DOCTYPE html>
						<html>
						<head><title>${fileName}</title></head>
						<body style="margin:0;display:flex;justify-content:center;align-items:center;height:100vh;background:#000;">
							<video width="90%" controls autoplay>
								<source src="${fileUrl}" type="video/${fileExt}">
								Your browser does not support the video tag.
							</video>
						</body>
						</html>
					`);
					newTab.document.close();
				} else if (['mp3', 'wav', 'ogg'].includes(fileExt)) {
					const newTab = window.open();
					newTab.document.write(`
						<!DOCTYPE html>
						<html>
						<head><title>${fileName}</title></head>
						<body style="margin:0;display:flex;justify-content:center;align-items:center;height:100vh;background:#000;">
							<audio controls autoplay>
								<source src="${fileUrl}" type="audio/${fileExt}">
								Your browser does not support the audio tag.
							</audio>
						</body>
						</html>
					`);
					newTab.document.close();
				} else {
					window.open(fileUrl, '_blank');
				}
			});

			icon.append(img).append(name).append(createdOn).append(createdBy).append(editIcon);
		}

		folderView.append(icon);
	});

	$("#new").click(addFolder);
	viewTemplate == "list" ? getListView() : getGridView();
}



//Function for get new icon
function getNewIcon() {
	const icon = $('<div id="new"></div>').addClass('icon');
	const img = $('<img>').attr('src', '../assets/images/newfoldericon.png');
	const name = $('<div></div>').text("New");
	icon.append(img).append(name);

	return icon;
}

// Function to open a folder and update the current view
function openFolder(folder) {

	console.log("Folder-->", folder);
	var userAcces = JSON.parse(folder.userAccess);
	//console.log("FolderName-->userAcces",userAcces);
	$("#add-btn").show();
	$("#workspaceId").val('');
	workSpaceId = folder.workspaceId;
	$("#workSpaceGrid").hide();
	folderPath += folder.name + "/";
	currentPath.push(folder.name);
	parentId = folder.workspaceId;
	// to Get Updated Path Name
	$("#parentFolderPath").val(folder.name);
	$("#parentFolderName").val(folder.name);
	$("#workspaceid").html(parentId);
	//$(".accessControlId").text(item.workspaceId);
	$("#newFolderInputDiv").show();
	$("#workspaceId").val('');
	$("#parentId").val(parentId);

	//Use Only in the Folder Upload
	$("#controlWorkspaceId").val(folder.workspaceId);

	/*$("#description").val(folder.desc);
	$("#defaultTags").val(folder.tags != null ? tags : "");
	$("#accessType").val(folder.accessType == "PUBLIC" ? "2" : "1");*/


	var path = currentPath[currentPath.length - 1];

	currentPathDetails = path;

	currentFolderData = jsonData.filter(item => item.parentId === folder.workspaceId);
	renderFolderView(currentFolderData);
	updateBreadcrumb(folder);
	$('#backButton').show(); // Show the back button when inside a folder
}

// Function to update the breadcrumb navigation
function updateBreadcrumb(folder) {
	const pathNames = currentPath.map(name => {
		const folder = jsonData.find(item => item.name === name && item.type === 'folder');
		return folder ? folder.name : '';
	});
	$('#breadcrumb').text(pathNames.join(' > '));
	/*if (pathNames.length == 0) {
		getFirstParentFolder();
	}*/

	noFolderFound();

	$("#accessType").val('');
	$("#accessType").attr("disabled", false);
	$("#accessType").css("background", "#ffffff");
	viewTemplate == "list" ? getListView() : getGridView();
	if (folder) {

		$("#selectedFolderId").val(folder.workspaceId);
		$("#selectedParentPath").val(folder.parentFolderPath);
		$("#selectedFolderName").val(folder.name);
	}
}
// Function to go back to the previous folder
function goBack() {


	if (currentPath.length > 1) {
		currentPath.pop(); // Remove the last folder from the path
		const previousFolderName = currentPath[currentPath.length - 1];

		const previousFolder = jsonData.find(item => item.name === previousFolderName && item.type === "folder");
		const previousWorkspaceId = previousFolder ? previousFolder.workspaceId : "";
		const currentFolderData = jsonData.filter(item => item.parentId === previousWorkspaceId);
		const pathNames = currentPath.map(name => {
			const folder = jsonData.find(item => item.name === name && item.type === 'folder');
			return folder ? folder.name : '';
		});

		$("#parentFolderPath").val(pathNames[pathNames.length - 1]);
		$("#parentFolderName").val(pathNames[pathNames.length - 1]);
		$("#workspaceid").html(previousWorkspaceId);
		$("#workspaceId").val('');
		$("#parentId").val(previousWorkspaceId);

		renderFolderView(currentFolderData);
		updateBreadcrumb(previousFolder);


		//document.getElementById("workSpaceCreation").click();
	} else {
		currentPath.pop(); // Remove the last item in the path (which is the root folder)
		// Render the root folder view
		const currentFolderData = jsonData.filter(item => item.parentId === "");

		renderFolderView(currentFolderData);
		updateBreadcrumb();
		$('#backButton').hide(); // Hide the back button when at the root
	}
}

// Function to add a new folder
function addFolder() {
	var path = currentPath[currentPath.length - 1];

	currentPathDetails = path;
	$("#parentFolderName").val(path);
	$("#newFolderName").val('');
	$("#accessType").val('');
	$("#defaultTags").val('');
	$("#description").val('');
	if (path === undefined || path === "") {
		$("#parentFolderPath").val('');
		parentId = "";
	}
}

function getListView() {
	$(".listView").addClass("iconActive");
	$(".gridView").removeClass("iconActive");
	$(".created-by").show();
	$(".created-on").show();
	$("#folderView").addClass("folderView");
	$(".icon").addClass("iconview");
	$(".folderText").addClass("folderTextView");
	$(".created-by").addClass("folderTextView");
	$(".created-on").addClass("folderTextView");
	viewTemplate = "list";
	$(".folderHeader").show();
	$(".informationIcon").removeClass(".bi bi-three-dots-vertical");
	$(".informationIcon").addClass(".bi bi-folder-symlink-fill");
	$(".informationIcon").addClass("edit-icon-listview");
	$(".informationIcon").removeClass("edit-icon");
}

function getGridView() {
	$(".gridView").addClass("iconActive");
	$(".listView").removeClass("iconActive");
	$("#folderView").removeClass("folderView");
	$(".icon").removeClass("iconview");
	$(".created-by").hide();
	$(".created-on").hide();
	$(".folderText").removeClass("folderTextView");
	viewTemplate = "grid";
	$(".folderHeader").hide();
	$(".informationIcon").addClass(".bi bi-three-dots-vertical");
	$(".informationIcon").removeClass(".bi bi-folder-symlink-fill");
	$(".informationIcon").addClass("edit-icon");
	$(".informationIcon").removeClass("edit-icon-listview");
}

function noFolderFound() {
	const folderView = document.getElementById('folderView');
	if (!folderView.hasChildNodes()) {
		// Create the no-folder design
		const noFolder = document.createElement('div');
		noFolder.className = 'no-folder';
		noFolder.innerHTML = `
	              <div class="noFolderIcon">📁</div>
	              <div class="message">No Folder Found</div>
	              <div class="description">It seems like the folder you are looking for does not exist or has been removed. Please check the folder name or create a new folder.</div>
	          `;

		// Append the no-folder design as a child
		folderView.appendChild(noFolder);
		$(".folderHeader").css("opacity", "0");
	}
	else {
		$(".folderHeader").css("opacity", "1");
	}
}

function folderSearch() {
	var quickFilterValue = $('#folderSearchValue').val().trim();

	if (quickFilterValue.length > 1) {

		const filteredData = currentFolderData.filter((element) =>
			element.name.toLowerCase().includes(quickFilterValue.toLowerCase())
		);


		renderFolderView(filteredData);
	} else {
		renderFolderView(currentFolderData);
	}

	viewTemplate == "list" ? getListView() : getGridView();
}


function disableAllTheField() {
	$("#docControls").attr("disabled", true);
	$("#docnoid_").val('');
	$("#docnoid_").attr("disabled", true);
	$("#doc_desc").val('');
	$("#doc_desc").attr("disabled", true);
	$("#doc_tags").val('');
	$("#doc_tags").attr("disabled", true);
}

function editItems() {

	$("#docControls").attr("disabled", false);
	$("#docnoid_").attr("disabled", false);
	$("#doc_desc").attr("disabled", false);
	$("#doc_tags").attr("disabled", false);
	//$("#next-btn-1").hide();
	//$(".header-tab-link").addClass("showPointerEvent");
}

//global Variables To Handel Folder Names
var document_id = "";
var folderName = "";
var documentName = "";

function handleSingleClick(item) {
	document_id = item.documentDetails.docid;
	folderName = item.documentDetails.folderName;
	documentName = item.documentDetails.documentName;
	documentimage = item.documentDetails.documentUrl
	$("#next-btn-1").show();

	$(".header-tab-link").removeClass("showPointerEvent");
	onChangeIntailApiCall(folderName, documentName, document_id);

}


function newFolderDetailsAdd() {
	$("#docControls").attr("disabled", false);
	$("#docnoid_").attr("disabled", false);
	$("#doc_desc").attr("disabled", false);
	$("#doc_tags").attr("disabled", false);
	$("#next-btn-1").hide();
	$(".header-tab-link").addClass("showPointerEvent");
	$(".read-more").hide();

	//$("#selectedFolderName").val('');
	//$("#selectedParentPath").val('');
	//$("#selectedFolderId").val('');
	$("#uploadHidden_0").val('');
	$("#imageName_0").html('');
	$("#imageId").html('');
	$("#doc_desc").val('');
	$("#doc_tags").val('');
	$("#docnoid_").val('');
	$("#createdBy").val('');
	$("#docId").val('');
}


function hideActionTabAsPerUser() {
	$("#next-btn-1").hide();
	$(".header-tab-link").addClass("showPointerEvent");
}

function showActionTabAsPerUser() {
	$("#next-btn-1").show();
	$(".header-tab-link").removeClass("showPointerEvent");
}


function searchFolder() {
	var searchValue = $("#seacrhInput").val().trim().toLowerCase();
	var folderElements = $(".folderText").toArray();

	folderElements.forEach((element, index) => {
		var folderName = element.innerText.trim().toLowerCase();
		var parentDiv = $(element).closest('.icon');

		if (folderName.includes(searchValue) || searchValue === "") {
			parentDiv.css("display", "flex");
		} else {
			parentDiv.css("display", "none");
		}
	});

}



function resetBtn() {
	$("#seacrhInput").val('');
	searchFolder();
}

function toggleReadMore(textarea) {
	const readMore = textarea.nextElementSibling;
	if (textarea.value.length > 50) { // Show tooltip if long text
		textarea.setAttribute("title", textarea.value);
		readMore.style.display = "inline";
	} else {
		textarea.removeAttribute("title");
		readMore.style.display = "none";
	}
}


function showFullDescription(span) {
	const textarea = span.previousElementSibling;
	var inputElementdoc_descvalue = $("#doc_desc").val();
	$("#view-desc-box").val(inputElementdoc_descvalue)
	$("#edit-desc-box").show();
	$("#fileUploadParentDiv").hide();
}

function saveSections() {
	$("#edit-desc-box").hide();
	$("#fileUploadParentDiv").show();
	var viewDesc = $("#view-desc-box").val();
	$("#doc_desc").val(viewDesc);
}

function cancelTemplateSection() {
	$("#edit-desc-box").hide();
	$("#fileUploadParentDiv").show();
}