$(document).ready(function() {
	/*$("#userGroupSection").hide();
	$("#accessSection").hide();
	$("#newAccessSection").hide();
	$("#auditSection").hide();
	$("#workflowSection").hide();*/

	/*var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var rowData = [];
	gridOptions.api.setRowData(rowData);*/

	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1);

	var rowData = [];
	gridOptions1.api.setRowData(rowData);

	/*var gridDiv2 = document.querySelector('#myGrid2');
	new agGrid.Grid(gridDiv2, gridOptions2);
	
	var rowData = [];
	gridOptions2.api.setRowData(rowData);
	
	var gridDiv3 = document.querySelector('#myGrid3');
	new agGrid.Grid(gridDiv3, gridOptions3);
	
	var rowData = [];
	gridOptions3.api.setRowData(rowData);
	
	var rowData = [];
	
	var gridDiv4 = document.querySelector('#myGridWorkFlow');
	new agGrid.Grid(gridDiv4, gridOptionsWorkFlow);
	gridOptionsWorkFlow.api.setRowData(rowData);*/


	/*agGrid.simpleHttpRequest({
		url : 'manage-control-get-user-listing'
	}).then(function(data) {
		console.log(data,'resppppp')
		var resp=JSON.parse(data.body[0]);
		var len = resp.EmployeeList.length;
		console.log(resp,'resppppp')
		$('#userSec').find('span').html(len);
		gridOptions.api.setRowData(resp.EmployeeList);
	});*/


	/*var gridDiv4 = document.querySelector('#myGrid4');
	new agGrid.Grid(gridDiv4, gridOptions4);
	
	var rowData = [];
	gridOptions4.api.setRowData(rowData);
	
	var gridDiv8 = document.querySelector('#myGrid8');
	new agGrid.Grid(gridDiv8, gridOptions8);
	
	var rowData = [];
	gridOptions8.api.setRowData(rowData);
	
	var gridDiv9 = document.querySelector('#myGridDocument');
	new agGrid.Grid(gridDiv9, gridOptions9);
	
	var rowData = [];
	gridOptions9.api.setRowData(rowData);*/




	disabledAllField();

	// Ajax Call For The Group Lisiting-->
	getUpdateGroupList();

	$("#seacrhInput").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});
});

var columnDefs = [
	{
		headerCheckboxSelection: true,
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
		width: 250,
	}, {
		headerName: "Login Id",
		field: "employeeId",
		width: 250,
	}, {
		headerName: "Name",
		field: "empName",
		width: 250,
	}, {
		headerName: "Department",
		field: "department",
		width: 250,
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 250,
	}, /*{
			headerName : "Status",
			field : "status",
			width:200,
			
		},*/];
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	/*groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,*/
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	//	onSelectionChanged : onSelectionChanged,
};

var columnDefs1 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: "Group ID",
		field: "groupId",
		width: 150,
		/*cellRenderer: function(params) {
			console.log(params)
			return '<a id="docId" onclick=editGroup("'
				+ params.data.groupId + '") href="javascript:void(0)">'
				+ params.data.groupId + '</a>';

		},*/
	}, {
		headerName: "Group Name",
		field: "groupName",
		width: 150,
	}, {
		headerName: "Description",
		field: "groupDescription",
		width: 150,
	}, {
		headerName: "Created By",
		field: "createdBy",
		width: 100,
	}, {
		headerName: 'Created Date',
		field: "createdDate",
		width: 150,
	}, {
		headerName: "Status",
		field: "status",
		width: 200,
		hide: true

	},];
var gridOptions1 = {
	columnDefs: columnDefs1,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChanged,
};

function onSelectionChanged() {
	var selectedNodes = gridOptions1.api.getSelectedNodes();
	selectedData = selectedNodes.map(node => node.data);

	console.log("Selected Nodes-->", selectedNodes);
	id = selectedData.map(node => node.groupId);

	var rowCount = 0;
	selectedNodes.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$("#groupId").text(id);
		editGroup(id);
	} else {
		$("#groupId").text('');
		$("#groupId").val('');
		$("#groupName").val('');
		$("#desc").val('');
		var selectedValues = [];
		autocomplete.setSelected(selectedValues);
	}


}


function disabledAllField() {
	$("#groupName").attr("disabled", true);
	$("#desc").attr("disabled", true);
	$("#addUserSectioon").css("pointer-events", "none");

}

function disabledFalse() {
	$("#groupName").attr("disabled", false);
	$("#desc").attr("disabled", false);
	$("#addUserSectioon").css("pointer-events", "all");

}

function userOpen() {
	$("#userSection").show();
	$("#userGroupSection").hide();
	/*$("#accessSection").hide();*/
	$("#newAccessSection").hide();
	$("#auditSection").hide();
	$("#workflowSection").hide();
	$("#myGridDocument").hide();
}
function userWorkflowOpen() {
	$("#userSection").hide();
	$("#userGroupSection").hide();
	$("#newAccessSection").hide();
	$("#auditSection").hide();
	$("#workflowSection").show();
	$("#myGridDocument").show();
	var userId = $("#userId").val();
	agGrid.simpleHttpRequest({
		url: 'manage-control-document-workflow?userId=' + userId,
	}).then(function(data) {
		console.log('data', data)
		const jsonString = data.body.map(item => item.replace(/'/g, '"'));
		// Convert to JSON array
		const jsonArray = jsonString.map(item => JSON.parse(item));
		console.log('jsonArray', jsonArray)
		var len = jsonArray.length;
		$('#documentList').find('span').html(len);
		gridOptions9.api.setRowData(jsonArray);
	});
}
function userGroupOpen() {
	$("#userSection").hide();
	$("#userGroupSection").show();
	$("#createGroup").hide();
	/*	$("#accessSection").hide();*/
	$("#newAccessSection").hide();
	$("#workflowSection").hide();
	$("#myGridDocument").hide();
	agGrid.simpleHttpRequest({
		url: 'manage-control-get-usergroup-listing'
	}).then(function(data) {
		console.log(data, 'resppppp')
		var resp = JSON.parse(data.body[0]);
		var len = resp.userGroupList.length;
		console.log(resp, 'resppppp')
		$('#userGroupSec').find('span').html(len);
		gridOptions1.api.setRowData(resp.userGroupList);
	});
}

//For User Search
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	var totalRowCount = gridOptions.api.getModel().getRowCount();
	$('#userSec').find('span').html(totalRowCount);
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

//For User Group Search
function onQuickFilterChanged1() {
	gridOptions1.api.setQuickFilter(document.getElementById('quickFilter1').value);
	var totalRowCount = gridOptions1.api.getModel().getRowCount();
	$('#userGroupSec').find('span').html(totalRowCount);
}
function cancelBar1() {
	var id = document.getElementById("closeKey1");
	id.style.display = "block";
	if ($('#quickFilter1').val() == null || $('#quickFilter1').val() == "") {
		id.style.display = "none";
	}
}

//For User List Access Control Search
function onQuickFilterChanged3() {
	gridOptions3.api.setQuickFilter(document.getElementById('quickFilter3').value);
	var totalRowCount = gridOptions3.api.getModel().getRowCount();
	$('#userList').find('span').html(totalRowCount);
}
function cancelBar3() {
	var id = document.getElementById("closeKey3");
	id.style.display = "block";
	if ($('#quickFilter3').val() == null || $('#quickFilter3').val() == "") {
		id.style.display = "none";
	}
}

//For User Group List Access Control Search
function onQuickFilterChanged4() {
	gridOptions4.api.setQuickFilter(document.getElementById('quickFilter4').value);
	var totalRowCount = gridOptions4.api.getModel().getRowCount();
	$('#groupList').find('span').html(totalRowCount);
}
function cancelBar4() {
	var id = document.getElementById("closeKey4");
	id.style.display = "block";
	if ($('#quickFilter4').val() == null || $('#quickFilter4').val() == "") {
		id.style.display = "none";
	}
}

//For Audit Log Search
function onQuickFilterChanged8() {
	gridOptions8.api.setQuickFilter(document.getElementById('quickFilter8').value);
	var totalRowCount = gridOptions8.api.getModel().getRowCount();
	$('#auditSec').find('span').html(totalRowCount);
}
function cancelBar8() {
	var id = document.getElementById("closeKey8");
	id.style.display = "block";
	if ($('#quickFilter8').val() == null || $('#quickFilter8').val() == "") {
		id.style.display = "none";
	}
}

function onQuickFilterChanged9() {

	gridOptions9.api.setQuickFilter(document.getElementById('quickFilter9').value);
	var totalRowCount = gridOptions9.api.getModel().getRowCount();
	$('#documentList').find('span').html(totalRowCount);
}
function cancelBar9() {
	var id = document.getElementById("closeKey9");
	id.style.display = "block";
	if ($('#quickFilter9').val() == null || $('#quickFilter9').val() == "") {
		id.style.display = "none";
	}
}
function newBtn() {
	$("#createGroup").show();
}
var autocomplete = '';
$(function() {
	autocomplete = new SlimSelect({
		select: '#multiple8',
		multiple: true,
		autocomplete: true,
		icon: "fa fa-times",
		onChange: key => {
			var data = [];
			for (var i = 0; i < key.length; i++) {

				data.push(key[i].value);
			}

			$("#toHiddenIdMeeting").val(data);
		},
	});
});
function saveGroup() {
	var selectElement = document.getElementById('multiple8');
	var selectedUsers = [];

	for (var i = 0; i < selectElement.selectedOptions.length; i++) {
		var option = selectElement.selectedOptions[i];
		var userObject = {
			employeeId: option.value,
			empName: option.text
		};
		selectedUsers.push(userObject);
	}
	obj = {};
	obj.groupId = $("#groupId").val();
	obj.groupName = $("#groupName").val();
	obj.groupDescription = $("#desc").val();
	obj.users = selectedUsers;

	console.log(obj, 'object');
	$.ajax({
		type: "POST",
		url: "manage-control-add-usergroup",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			console.log("res",response);
			toastr.success(response.message);
			$("#createGroup").hide();
			
			$(".btn-hs").show();
			getUpdateGroupList();
			disabledAllField();
		}
	});
}
function editGroup(id) {

	$("#createGroup").show();
	$.ajax({
		type: "GET",
		url: "manage-control-edit-usergroup?id=" + id,
		success: function(response) {
			var resp = JSON.parse(response.body[0]);
			console.log(resp.userGroupList[0], 'resp.userGroupList[0]')
			$("#groupId").val(resp.userGroupList[0].groupId);
			$("#groupName").val(resp.userGroupList[0].groupName);
			$("#desc").val(resp.userGroupList[0].groupDescription);

			var uidArray = [];
			var userList = resp.userGroupList[0].users;
			console.log('userList', userList)
			uidArray = userList.map(function(user) {
				return user.employeeId;
			});
			var selectedValues = uidArray;
			autocomplete.setSelected(selectedValues);

		}
	});
}


function accessControlOpen() {
	$("#userSection").hide();
	$("#userGroupSection").hide();
	$("#newAccessSection").show();
	$("#auditSection").hide();
	$("#workflowSection").hide();
	$("#myGridDocument").hide();

	agGrid.simpleHttpRequest({
		url: 'manage-control-get-user-listing'
	}).then(function(data) {
		console.log(data, 'resppppp')
		var resp = JSON.parse(data.body[0]);
		var len = resp.EmployeeList.length;
		console.log(resp, 'resppppp')
		$('#userList').find('span').html(len);
		gridOptions3.api.setRowData(resp.EmployeeList);
	});
	agGrid.simpleHttpRequest({
		url: 'manage-control-get-usergroup-listing'
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = resp.userGroupList.length;
		$('#groupList').find('span').html(len);
		gridOptions4.api.setRowData(resp.userGroupList);
	});
}


var columnDefs2 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: "Access ID",
		field: "accessId",
		width: 250,
	}, {
		headerName: "Employee Id",
		field: "employeeId",
		width: 250,
	}, {
		headerName: "Access Type",
		field: "accessType",
		width: 250,
	}, {
		headerName: "Remarks",
		field: "remarks",
		width: 250,
	}, {
		headerName: 'Created Date',
		field: "createdDate",
		width: 250,
	},];
var gridOptions2 = {
	columnDefs: columnDefs2,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	//	onSelectionChanged : onSelectionChanged,
};
var columnDefs3 = [
	{
		headerCheckboxSelection: true,
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
		width: 250,
		cellRenderer: function(params) {
			console.log(params)
			return '<a id="employeeId" href="javascript:void(0)">'
				+ params.data.employeeId + '</a>';

		},
	}, {
		headerName: "Name",
		field: "empName",
		width: 250,
	}, {
		headerName: "Department",
		field: "department",
		width: 250,
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 250,
	}, /*{
			headerName : "Status",
			field : "status",
			width:200,
			
		},*/];
var gridOptions3 = {
	columnDefs: columnDefs3,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChangedUser,
};
var columnDefs4 = [
	{
		headerCheckboxSelection: true,
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
var gridOptions4 = {
	columnDefs: columnDefs4,
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



var columnDefs9 = [
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
		width: 200,
		cellRenderer: function(params) {
			var userId = $("#userId").val();
			var createdBy = params.data.createdBy;
			if (createdBy.toString() == userId.toString()) {
				return params.data.docid;
			} else {
				return '<a id="docId" onclick=editDocument("'
					+ params.data.docid + '","' + window.btoa(params.data.docControl) + '") href="javascript:void(0)">'
					+ params.data.docid + '</a>';
			}

		}
	}, {
		headerName: "Date",
		field: "date",
		width: 200,
	}, {
		headerName: "Version",
		field: "version",
		width: 200,
	}, {
		headerName: "Type",
		field: "docControl",
		width: 200,
	}, {
		headerName: 'Name',
		field: "documentName",
		width: 200,
	}, {
		headerName: "Folder Name",
		field: "folderName",
		width: 200,
	}, {
		headerName: 'Access Workflow',
		field: "access",
		cellRenderer: function(params) {
			return '<a onclick=accessWorkFlow("' + params.data.docid + '","' + window.btoa(params.data.docControl) + '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i></a>';
		}
	}];
var gridOptions9 = {
	columnDefs: columnDefs9,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
};

function onSelectionChangedUser() {

	var selectedNodes = gridOptions3.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	var empid = selectedData[0].employeeId;
	let uid = window.btoa(empid);
	let accessType = 'user';
	let utype = window.btoa(accessType);
	window.open("/edms/manage-access?uid=" + uid + "&type=" + utype, "_self");
}
function onSelectionChangedUserGroup() {
	var selectedNodes = gridOptions4.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	console.log(selectedData);

	var groupid = selectedData[0].groupId;
	var users = window.btoa(JSON.stringify(selectedData[0].users));

	let uid = window.btoa(groupid);
	let accessType = 'usergroup';
	let utype = window.btoa(accessType);
	window.open("/edms/manage-access?uid=" + uid + "&type=" + utype + "&users=" + users, "_self");
}
function auditLogOpen() {
	$("#userSection").hide();
	$("#userGroupSection").hide();
	$("#newAccessSection").hide();
	$("#auditSection").show();
	agGrid.simpleHttpRequest({
		url: 'manage-control-get-auditlog-listing'
	}).then(function(data) {
		console.log(data, 'resppppp')
		var resp = JSON.parse(data.body[0]);
		var len = resp.AuditList.length;
		console.log(resp, 'resppppp')
		$('#auditSec').find('span').html(len);
		gridOptions8.api.setRowData(resp.AuditList);
	});
}
var columnDefs8 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: "Time",
		field: "time",
		width: 300,
	}, {
		headerName: "User",
		field: "createdBy",
		width: 300,
	}, {
		headerName: "Object",
		field: "fileObject",
		width: 300,
	}, {
		headerName: 'Action',
		field: "action",
		width: 400,
	},];
var gridOptions8 = {
	columnDefs: columnDefs8,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	suppressRowClickSelection: true,
	/*onSelectionChanged : onSelectionChangedUserGroup,*/
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
var workStatus = "";
function accessWorkFlow(id, type) {
	$("#workFlowModal").modal('show');

	$("#documentId").val(id);
	$("#documentType").val(window.atob(type));
	var rowData = [];
	gridOptionsWorkFlow.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "manage-control-view-workFlow?docId=" + id
	}).then(function(data) {

		if (data.code == "Success") {
			const jsonData = JSON.parse(data.body[0]);
			console.log('jsonDataWorkflow', jsonData)
			gridOptionsWorkFlow.api.setRowData(jsonData);
			$("#workFlowFolder").val(jsonData[0].workFlowFolder);
			$("#workFlowId").val(jsonData[0].workFlwId);
			$("#resolution").val(jsonData[0].resolution);
		} else {
			gridOptionsWorkFlow.api.setRowData([])
		}
		$('.loader').hide();

	});

}



function saveWorkFlow() {
	var datas = {};
	var allRowData = [];
	gridOptionsWorkFlow.api.forEachNode(function(node) {
		allRowData.push(node.data);
	});
	datas.resolution = $("#resolution").val();
	datas.workFlowFolder = $("#workFlowFolder").val();
	datas.docId = $("#documentId").val();
	datas.docType = $("#documentType").val();
	datas.workFowData = allRowData;
	datas.workFlowId = $("#workFlowId").val();
	var data = JSON.stringify(datas);
	console.log('allRowData', allRowData)
	console.log('datas', datas)
	$('.loader').show();
	$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "manage-control-workFlow-add",
		contentType: "application/json",
		data: data,
		success: function(response) {
			// Handle the success response if needed
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#folderUploadModal").modal('hide');

			$("#messageParagraph").text("WorkFlow Saved Sucessfully");
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



function editFolder() {
	disabledFalse();
}

function getUpdateGroupList() {
	agGrid.simpleHttpRequest({
		url: 'manage-control-get-usergroup-listing'
	}).then(function(data) {
		console.log(data, 'resppppp')
		var resp = JSON.parse(data.body[0]);
		var len = resp.userGroupList.length;
		console.log(resp, 'resppppp')
		$('#userGroupSec').find('span').html(len);
		//gridOptions1.api.setRowData(resp.userGroupList);
		window.gridOptions1.api.setRowData(resp.userGroupList);

		if (resp.userGroupList && resp.userGroupList.length > 0) {
			window.gridOptions1.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		}
	});
}

function newFolderDetailsAdd() {

	disabledFalse();
	$("#groupId").val('');
	$("#groupName").val('');
	$("#desc").val('');
	gridOptions1.api.deselectAll();
	var selectedValues = [];
	autocomplete.setSelected(selectedValues);
}



function resetBtn() {
	$("#seacrhInput").val('');
	gridOptions1.api.setQuickFilter('');
	gridOptions1.api.refreshCells({ force: true });
}

function onQuickFilterChanged() {
	gridOptions1.api.setQuickFilter(document.getElementById('seacrhInput').value);
}
