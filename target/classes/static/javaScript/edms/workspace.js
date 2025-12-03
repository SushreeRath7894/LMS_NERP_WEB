//import * as validationFile from "../edms/variableNvalidation.js";
//import * as workspaceBtn from "../edms/workSpaceBtn.js";
/*workspaceBtn.newBtn.addEventListener('click', newPlans);
workspaceBtn.cancel.addEventListener('click', cancel);
workspaceBtn.reqDltBtn.addEventListener('click', deleteFun);
workspaceBtn.deleteMdl.addEventListener('click', deleteOnclick);*/
let GroupData = [];


function checkPraentFolderDulicate(){
	
	 var options = document.querySelectorAll("#parentFolderName option");
        options.forEach(function(option) {
            var code = option.getAttribute("data-code"); 
            if (code !== null && code !== "") {
                option.disabled = true;
                option.style.backgroundColor = '#dddddd'; 
                option.style.color = '#999999'; 
            }
        });
}
function newPlans() {
	hideShowMultipleElement(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], false);
	hideShowMultipleElement(["#demo"], true);
	//validationFile.hideShowMultipleElements(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], false);
	closeNav();
	accessType()
	//validationFile.hideShowMultipleElements(["#demo"], true);
	$("#itemname").val('');
	$("#description").val('');
	$("#rowEdit").val('');
	$("#deleteChild").attr('disabled', true);
	$("#newchild").attr('disabled', false);
	$("#save").attr('disabled', false);
	//checkPraentFolderDulicate();
	  var rowData = [
        { siNo: 1, activityName: 'Read',userId:'',userName:'',groupId:'',groupName:''},
        { siNo: 2, activityName: 'Write',userId:'',userName:'',groupId:'',groupName:''},
        { siNo: 3, activityName: 'Delete',userId:'',userName:'',groupId:'',groupName:''}

    ];
	  rowData.forEach(row => {
        row.userId = '';
        row.userName = '';
        row.groupId = '';
        row.groupName = '';
    });
	activityOptions.api.setRowData(rowData);
}
function accessType(){
	var type=$("#accessType").val();
	if(type=='1'){
		$("#activity").show();
		$("#main").show();
		$("#demo").show();
	}else{
		$("#activity").hide();
		$("#main").hide();
	}
}

function spaceChecker() {
    var folderNameInput = $("#newFolderName");

    // Remove spaces as the user types
    folderNameInput.on("input", function() {
        var inputValue = folderNameInput.val();
        var sanitizedValue = inputValue.replace(/\s/g, ""); // Remove spaces
        folderNameInput.val(sanitizedValue);
    });

}



$(document).ready(function() {
	
	//checkPraentFolderDulicate();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);
	
	   var rowData = [
        { siNo: 1, activityName: 'Read',userId:'',userName:'',groupId:'',groupName:''},
        { siNo: 2, activityName: 'Write',userId:'',userName:'',groupId:'',groupName:''},
        { siNo: 3, activityName: 'Delete',userId:'',userName:'',groupId:'',groupName:''}
    ];
		  rowData.forEach(row => {
		        row.userId = '';
		        row.userName = '';
		        row.groupId = '';
		        row.groupName = '';
		    });
       activityOptions.api.setRowData(rowData);

	
	viewWOrkspaceData();

	//activityOptions.api.setRowData();
	$('#reqDltBtn').attr('disabled', true);
	$('#deleteChild').attr('disabled', true);
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	$('.collapse').on('hide.bs.collapse', function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});


});

function viewWOrkspaceData(){
		agGrid.simpleHttpRequest({
		url: 'work-space-view'
	}).then(function(data) {
		const jsonString = data.body.map(item => item.replace(/'/g, '"'));
		// Convert to JSON array
		const jsonArray = jsonString.map(item => JSON.parse(item));
		var len = jsonArray.length;
		$('#totalWorkSpace').find('span').html(len);
		gridOptions.api.setRowData(jsonArray);
		
	});
}

/* -------------------search bar for mygrid------------------------ */

/*function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}*/


function cancelBars() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
var columnDefs = [
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
		headerName: 'WorkSpaceId',
		width: 200,
		field: "workspaceId",cellRenderer: function(params) {
		return '<a id="farmId" onclick=editWorkspace("'
			+ params.data.workspaceId
			+ '") href="javascript:void(0)">'
			+ params.data.workspaceId + '</a>';

	}

	}, {
		headerName: 'Folder Name',
		width: 200,
		field: "parentFolderName",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Type',
		width: 100,
		field: "accessTypeName",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Created By',
		field: "owner",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Created Date',
		field: "createdOn",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Description',
		field: "description",
		width: 400,
	}, {
		headerName: 'Parent Folder Name',
		field: "parentFolderName",
		width: 200,
	}, {
		headerName: 'New Folder Name',
		field: "newFolderName",
		width: 200,
	}, {
		headerName: 'Access',
		field: "access",
		width: 200,
	}, {
		headerName: 'Owner',
		field: "owner",
		width: 200,
	}, {
		headerName: 'Due Date',
		field: "due_date",
		width: 400,
	}, {
		headerName: 'Default Tags',
		field: "defaultTags",
		width: 400,
	}, {
		headerName: 'Read',
		field: "read",
		width: 400,
		hide:true
	}, {
		headerName: 'Write',
		field: "write",
		width: 400,
		hide:true
	}, {
		headerName: 'Delete',
		field: "delete",
		width: 400,
		hide:true
	}];

// let the grid know which columns and what data to use
var rowdata = [];
var gridOptions = {
	columnDefs: columnDefs,
	rowData: rowdata,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	onSelectionChanged: onSelectionChanged,
	onRowClicked: function(params) {
		// This function will be called when a row is clicked
		const clickedRowData = params.data; // Get the data of the clicked row
		console.log('Row Clicked:', clickedRowData);
		editWorkspace(clickedRowData.workspaceId);

	},
};
//function for row select parents
var id = "";

function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	id = selectedData.map(node => node.qcId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newBtn').attr('disabled', true);
	} else {
		$('#newBtn').attr('disabled', false);
	}
	var deleteStatus=selectedRows[0].delete;
	if(deleteStatus=='true'){
		$('#reqDltBtn').attr("disabled", false);
	}else{
		$('#reqDltBtn').attr("disabled", true);
	}
}
// for activity table
var activityDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	},
	{
		headerName: "Activity",
		field: "activityName",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},cellRenderer: function(params) {
			if (params.data.partId) {
				return '<a onclick=editProduct("' + params.data.activityName
					+ '") href="javascript:void(0)">'
					+ params.data.activityName + '</a>';
			} else {
				return '<a onclick=editProduct("' + params.data.activityName
					+ '") href="javascript:void(0)">'
					+ params.data.activityName + '</a>';
			}
		}
	}, {
		headerName: "User Id",
		field: "userId",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "User Name",
		field: "userName",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Group Id",
		field: "groupId",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Group Name",
		field: "groupName",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
	}];


// let the grid know which columns and what data to use product table
var activityOptions = {
	columnDefs: activityDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200
	},
	//onSelectionChanged: onSelectionChangeChild,
	getRowNodeId: function(data) {
		return data.activityName;
	}
};
//function for row select parents
var childid = "";
function onSelectionChangeChild() {
	var selectedNodes = activityOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	childid = selectedData.map(node => node.slnoId);
	var selectedRows = activityOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newchild').attr('disabled', true);
		$('#deleteChild').attr('disabled', false);
	} else {
		$('#newchild').attr('disabled', false);
		$('#deleteChild').attr('disabled', true);
	}
}

function editWorkspace(id) {
	
	$
		.ajax({
			type: "GET",
			url: "work-space-edit?id=" + id,
			success: function(response) {
				if (response) {
					newPlans();
					console.log('RESPONSE ::::',response.body)
				
				// Replace single quotes with double quotes
				const jsonString = response.body.map(item => item.replace(/'/g, '"'));

				// Convert to JSON array
				const jsonArray = jsonString.map(item => JSON.parse(item));
				console.log('jsonArray',jsonArray)
					//activityOptions.api.setRowData(jsonData.accessControl);
					$("#workspaceId").val(jsonArray[0].workspaceId);
					$("#parentFolderName").val(jsonArray[0].parentFolderId);
					$("#newFolderName").val(jsonArray[0].newFolderName);
					$("#accessType").val(jsonArray[0].accessType);
					$("#owner").val(jsonArray[0].owner);
					$("#owner").prop('disabled', true);
					$("#defaultTags").val(jsonArray[0].defaultTags);
					$("#description").val(jsonArray[0].description);
					accessType();
					var createdBy=$("#sessionId").val();
					console.log('createdBy',createdBy);
					var userAccess=jsonArray[0].userDtls;
					var groupUserAccess=jsonArray[0].groupDtls;
					console.log('userAccess',userAccess);
					if (createdBy!==jsonArray[0].ownerId) {
					var matchedUsers = userAccess.map(user => user.userId === createdBy ? user : null)
					        .filter(user => user !== null);
					if(Array.isArray(matchedUsers) && matchedUsers.length > 0){
					    var readStatus=matchedUsers[0].read;
					    var writeStatus=matchedUsers[0].write;
	                    if(readStatus==true && writeStatus==true){
							$("#save").attr('disabled', false);
						}else if(readStatus==true && writeStatus==false){
							$("#save").attr('disabled', true);
						}else if(readStatus==false && writeStatus==true){
							$("#save").attr('disabled', false);
						}else if(readStatus==false && writeStatus==false){
							$("#save").attr('disabled', true);
						}
					}else{
						 $("#save").attr('disabled', false);
					}
						var matchedGroupUsers = groupUserAccess.map(user => user.employeeId === createdBy ? user : null)
					        .filter(user => user !== null);
					if(Array.isArray(matchedGroupUsers) && matchedGroupUsers.length > 0){
					    console.log('matchedGroupUsers',matchedGroupUsers);
					    var readGroup=matchedGroupUsers[0].read;
					    var writeGroup=matchedGroupUsers[0].write;
	                    if(readGroup==true && writeGroup==true){
							$("#save").attr('disabled', false);
						}else if(readGroup==true && writeGroup==false){
							$("#save").attr('disabled', true);
						}else if(readGroup==false && writeGroup==true){
							$("#save").attr('disabled', false);
						}else if(readGroup==false && writeGroup==false){
							$("#save").attr('disabled', true);
						}
					 }else{
						$("#save").attr('disabled', false);
					}
					}
					// Extract and organize user IDs based on access types
						let readUserIds = [];
						let writeUserIds = [];
						let deleteUserIds = [];
						let readUserNames = [];
						let writeUserNames = [];
						let deleteUserNames = [];
						
						jsonArray.forEach(control => {
						    control.userDtls.forEach(user => {
						        if (user.read) {
						            readUserIds.push(user.userId);
						            readUserNames.push(user.userName);
						        }
						        if (user.write) {
						            writeUserIds.push(user.userId);
						            writeUserNames.push(user.userName);
						        }
						        if (user.delete) {
						            deleteUserIds.push(user.userId);
						            deleteUserNames.push(user.userName);
						        }
						    });
						});
						
						
						
						// Prepare row data for AG Grid
						let rowData = [
						    { activityName: 'Read', userId: readUserIds.join(', '),userName: readUserNames.join(', ')
								, groupId: jsonArray[0].groupIdForRead,groupName: jsonArray[0].groupNameForRead},
						    { activityName: 'Write', userId: writeUserIds.join(', '),userName: writeUserNames.join(', ')
								, groupId: jsonArray[0].groupIdForWrite,groupName: jsonArray[0].groupNameForWrite },
						    { activityName: 'Delete', userId: deleteUserIds.join(', '),userName: deleteUserNames.join(', ') 
								, groupId: jsonArray[0].groupIdForDelete,groupName: jsonArray[0].groupNameForDelete}
						];
						
						// Set the row data in AG Grid
						activityOptions.api.setRowData(rowData);
						

					//activityOptions.api.setRowData(jsonData.accessControl);
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
}

//parents new plan

//function for cancel
function cancel() {
	hideShowMultipleElement(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	hideShowMultipleElement(["#demo"], false);

	//validationFile.hideShowMultipleElements(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	closeNav();
	//validationFile.hideShowMultipleElements(["#demo"], false);


	$(".formValidation").remove();
	$("#reqDltBtn").attr('disabled', true);
	$("#newBtn").attr('disabled', false);

	$("#qcId").val('');
	$("#itemname").val('');
	$("#itemid").val('');
	$("#description").val();
	closeNav();
}
// function for openNav child	
function openNav() {
	$("#parameterName").val('');
	$("#parameterId").val('');
	$("#parameterValue").val('');
	$("#userName").val('');
	$("#userSearchId").val('');
	$("#rowEdit").val(null);
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:380px;";
	document.getElementById("main").style.width = "75%";
	
}
//function for closeNav child
function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
	$("#parameterName").val('');
	$("#parameterId").val('');
	$("#parameterValue").val('');
	$("#rowEdit").val(null);
}
//master save data
function masterSaveData() {
	

	var event = {};
	var accessControl = [];
	var userDtls = [];
	var groupDtls = [];
	var notification = [];
	activityOptions.api.forEachNode(function(rowNode, index) {
		accessControl.push(rowNode.data);
	});
	var parentId=$("#parentFolderName").val();
	var parentName="";
	if(parentId==""){
		 parentName="";
	}else{
		parentName=$("#parentFolderName option:selected").text();
	}
	event.workspaceId = $("#workspaceId").val();
	event.parentFolderId = parentId;
	event.parentFolderName=parentName;
	event.newFolderName = $("#newFolderName").val();
	event.accessType = $("#accessType").val();
	event.accessTypeName = $("#accessType option:selected").text();
	event.owner = $("#owner").val();
	event.ownerId = $("#ownerId").val();
	event.orgName = $("#orgName").val();
	event.orgDivision = $("#orgDivision").val();
	event.defaultTags = $("#defaultTags").val();
	event.description = $("#description").val();
	var access=accessControl;
	
	for (var i = 0; i < access.length; i++) {
    var activity = access[i];
		    console.log('activity',activity)
		    if (activity.userId && activity.userName) {
		        var ids = activity.userId.split(', ');
		        var names = activity.userName.split(', ');
		        
		        for (var j = 0; j < ids.length; j++) {
		            var userDtl = {
		                userId: ids[j],
		                userName: names[j] || '',
		                read: activity.activityName == "Read" ? true : false,
						write: activity.activityName == "Write" ? true : false,
						delete: activity.activityName == "Delete" ? true : false
		            };
		            userDtls.push(userDtl);
		        }
		    }
		}
	event.userDtls=userDtls;
	event.groupDtls=GroupData;
	var data = JSON.stringify(event);

    console.log('userDtls ::::',userDtls)
    console.log('groupDtls ::::',groupDtls)
    console.log('event ::::',event)
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "work-space-add-new",
		dataType: "json",
		contentType: "application/json",
		data: data,
		success: function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				cancel();
				location.reload();
			    viewWOrkspaceData();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				closeNav()
			} else {
				$('.loader').hide();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	})
}

function savePlanDetails(datas) {
	
	console.log(datas);
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "qc-master-add",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				cancel();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				closeNav()
			} else {
				$('.loader').hide();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	}) //ajax ends
}


//For Workspace Search Bar
	function onQuickFilterChanged() {
		gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
		var totalRowCount = gridOptions.api.getModel().getRowCount();
		$('#totalWorkSpace').find('span').html(totalRowCount);
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";
		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}

	function saveTableData() {
	  
		var selectedData = [];
		var selectedGroup = [];
	
	$('.selected-name').each(function() {
	    var name = $(this).attr('value'); // Retrieve the name
	    var id = $(this).find('.selected-user-id').val(); // Retrieve the ID
	    
	    // Check if the ID is already in selectedData
	    var exists = selectedData.some(item => item.userId === id);
	    
	    if (!exists) {
	        selectedData.push({ userName: name, userId: id });
	    }
	});
	
	$('.selected-group-name').each(function() {
	    var name = $(this).attr('value'); // Retrieve the name
	    var id = $(this).find('.selected-group-id').val(); // Retrieve the ID
	     console.log('name',name)
 		 console.log('id',id)
	    // Check if the ID is already in selectedData
	    var exists = selectedGroup.some(item => item.groupId === id);
	    console.log('exists',exists)
	    if (!exists) {
	        selectedGroup.push({ groupName: name, groupId: id });
	    }
	});
	
	console.log('selectedData', selectedData);
	console.log('selectedGroup', selectedGroup);
	
	// comma-separated strings
	var userNames = selectedData.map(item => item.userName).join(', ');
	var userIds = selectedData.map(item => item.userId).join(', ');
	var groupNames = selectedGroup.map(item => item.groupName).join(', ');
	var groupIds = selectedGroup.map(item => item.groupId).join(', ');
	
	console.log('userNames', userNames);
	console.log('userIds', userIds);
	console.log('groupNames', groupNames);
	console.log('groupIds', groupIds);
	
	// Get the current row data from the grid
	var allRowData = [];
	activityOptions.api.forEachNode(node => allRowData.push(node.data));
	
	
	var rowEdit = $("#rowEdit").val();  
	var rowToUpdate = allRowData.find(row => row.activityName === rowEdit);
	
	if (rowToUpdate) {
	    // Update the row with the new comma-separated strings
	    rowToUpdate.userName = userNames;
	    rowToUpdate.userId = userIds;
  		rowToUpdate.groupName = groupNames;
	    rowToUpdate.groupId = groupIds;
	    
	 
	    activityOptions.api.setRowData(allRowData);
	}
	
	console.log('Updated Row:', rowToUpdate);
	closeNav();
	}









var rowNodeEditData="";
function editProduct(id) {
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:380px;";
	document.getElementById("main").style.width = "75%";
	$("#groupList").hide();
	$("#selectedParticipants").hide();
	rowNodeEditData = activityOptions.api.getRowNode(id);
	var editData=rowNodeEditData;
	$("#rowEdit").val(id);
	var rowNodeId=$("#rowEdit").val();
	$("#userName").val('');
	$("#userSearchId").val('');
	$(".selected-user-container").html('');
	$(".selected-group-container").html('');
	$("#groupName").val('');
	$("#groupSearchId").val('');
	$("#selectedParticipants1").hide();
	$("#selectedParticipants").show();
	console.log('rowNodeEditData',rowNodeEditData)
	
userType();
}
//editing the plan details parent table


// function for delete plan  
function deleteOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.workspaceId;
	});

	var eventId = selectedRowsString;
	var parentFolder=selectedRows[0].parentFolderName;
	var newFolder=selectedRows[0].newFolderName;
	if (eventId) {
		$.ajax({
			type: "GET",
			url: 'work-space-delete?id=' + eventId +"&parentFolder="+parentFolder +"&newFolder="+newFolder,
			success: function(response) {
				if (response.message == "Success") {
					viewWOrkspaceData();
                    //checkPraentFolderDulicate();
					cancel();
					$("#messageParagraph").text("WorkSpace deleted sucessfully");
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

// delete selected record from child ag grid
function deleteDetailsOnclick() {
	$('.modal').hide();
	var selectedRows = activityOptions.api.getSelectedRows();
	activityOptions.api.applyTransaction({
		remove: selectedRows
	});
	cancelModalProductBtn();
}
//for closeing modal box for dlt  product
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteDetails').modal('hide');
}

function deleteFun() {
	$('#delete').modal('show');
}

function deleteDetails() {
	$('#deleteDetails').modal('show');
	closeNav();
}

function cancelModalBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
		id.style.display = "none";
	}
}
//function for download
function downloadDetails() {
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
	gridOptions.api.exportDataAsCsv(dataset);
}


function getparameterId() {
	var parmId = $("#parameterName").val();
	$("#parameterId").val(parmId);
}

function getItemid() {
	var parmId = $("#itemname").val();
	$("#itemid").val(parmId);
}

function userType(){
	$("#userSearchId").val('');
	$("#userName").val('');
	$("#groupSearchId").val('');
	$("#groupName").val('');
	var type=$("#operationType").val();
	if(type=='user'){
		$("#userList").show();
		$("#userNameList").show();
		$("#groupList").hide();
		$("#groupNameList").hide();
		$(".selected-user-container").empty();
		var editData=rowNodeEditData.data.userId;
	if(editData!='undefined' || editData!=''){
	    let userIds = rowNodeEditData.data.userId.split(', ');
		let userNames = rowNodeEditData.data.userName.split(', ');
		console.log('userIds',userIds)
		console.log('userNames',userNames)
	 if(userIds!=''){
		userIds.forEach((userId, index) => {
	    let userName = userNames[index];
	    let editUser = '<div class="selected-name" value="'+userName+'">' +userName +
	                   '<div class="remove-icon" onclick="removeSelectedName(this)">x</div>' +
	                   '<input type="hidden" class="selected-user-id" value="' + userId + '">' +
	                   '</div>';
	   $(".selected-user-container").append(editUser);
	   });
	
	}
	
	
   }
	}else{
		$("#groupList").show();
		$("#groupNameList").show();
		$("#userList").hide();
		$("#userNameList").hide();
		$("#selectedParticipants1").show();
		$(".selected-group-container").empty();
		var editData=rowNodeEditData.data;
		var groupIds=rowNodeEditData.data.groupId;
		if(editData!='undefined' || editData!=''){
		if (rowNodeEditData.data.groupId && rowNodeEditData.data.groupName) {
	   	$("#groupList").show();
		$("#selectedParticipants1").show();
        $("#userList").hide();
        $("#userNameList").hide();
 
 if(groupIds!=''){
	
		let groupIds = rowNodeEditData.data.groupId.split(',');
		let groupNames = rowNodeEditData.data.groupName.split(',');
		
		console.log('groupIds',groupIds)
		console.log('groupNames',groupNames)
	 
		groupIds.forEach((groupId, index) => {
	    let groupName = groupNames[index];
	    let editUser = '<div class="selected-group-name" value="'+groupName+'">' +groupName +
	                   '<div class="remove-icon" onclick="removeSelectedGroupName(this)">x</div>' +
	                   '<input type="hidden" class="selected-group-id" value="' + groupId + '">' +
	                   '</div>';
	   $(".selected-group-container").append(editUser);
	   });
	}
     }
	}

	}
}


function getUserNameList() {
			var type = $("#operationType").val();
			var userName = $("#userName").val();
			
			if(type=='' || type==null){
				$("#messageParagraph").text("Please Select USER/GROUP");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}else{
			if (userName == "") {
				$("#suggesstion-boxUser_").hide();
			}
			if (type == 'user') {
				var searchVal = userName;
				$.ajax({
					type: "POST",
					url: "work-space-autosearchUser",
					dataType: 'json',
					contentType: 'application/json',
					data: searchVal,
					success: function (response) {
						if (response.code == "Success") {
						console.log('RESPONSE',response.body)
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list1" class="searhlist-dsg">';
								for (var i = 0; i < response.body.length; i++) {
									content += '<li class="autocompletedata cp" style="margin-left: -24px;font-weight: 600;" onClick="selectAutocompleteValue(\''
										+ response.body[i].name
										+ '\',\''
										+ response.body[i].key
										+ '\')">'
										+ response.body[i].name
										+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-boxUser_").show();
								$("#suggesstion-boxUser_").html(content);

							} else {
								//$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list1">';
								content += '<div onClick="selectAutocompleteValue()">'
									+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-boxUser_").show();
								$("#suggesstion-boxUser_").html(content);
							}
						}
					},
					error: function (data) {
						console.log(data);
					}
				})
			}

}

		}
		function selectAutocompleteValue(name, id) {
		    if (name) {
		        // Set the data-procat attribute and hide the suggestion box
		        $("#search").attr('data-procat', name);
		        $("#suggesstion-boxUser_").hide();
		        
		        var selectedData = $('.selected-user-container');
		        
		        var newUser = '<div class="selected-name" value="' + name + '">' +
		                          name +
		                          '<input type="hidden" class="selected-user-id" value="' + id + '">' +
		                          '<div class="remove-icon" onclick="removeSelectedName(this)">×</div>' +
		                          '</div>';
		                          
		        selectedData.html(selectedData.html() + newUser);
		
		    } else {
		        // Reset the input fields and hide the suggestion box if no name is provided
		        $("#userSearchId").val("");
		        $("#userName").val("");
		        $("#search").val("");
		        $("#search").attr('data-procat', "");
		        $("#suggesstion-boxUser_").hide();
		    }
		}
			var selectedNames = [];
			var selectedIds = [];
			function removeSelectedName(element) {
			    var selectedName = $(element).closest('.selected-name');
			    var name = selectedName.attr('value');
			    var id = selectedName.find('.selected-user-id').val();
				console.log('selectedName:', selectedName);
				console.log('name', name);
				console.log('id', id);
			    // Remove the name and ID from the selected lists
			    selectedNames = selectedNames.filter(item => item !== name);
				console.log('selectedNames Names:', selectedNames);
			    selectedIds = selectedIds.filter(item => item !== id);
				console.log('selectedIds:', selectedIds);
			
			    // Remove the element from the DOM
			    selectedName.remove();
			
			    console.log('Updated Names:', selectedNames);
			    console.log('Updated IDs:', selectedIds);
			}
		
			var selectedGroupNames = [];
			var selectedGroupIds = [];
			function removeSelectedGroupName(element) {
			    var selectedGroupName = $(element).closest('.selected-group-name');
			    var name = selectedGroupName.attr('value');
			    var id = selectedGroupName.find('.selected-group-id').val();
				console.log('selectedName:', selectedGroupName);
				console.log('name', name);
				console.log('id', id);
			    // Remove the name and ID from the selected lists
			    selectedGroupNames = selectedGroupNames.filter(item => item !== name);
				console.log('selectedNames Names:', selectedGroupNames);
			    selectedGroupIds = selectedGroupIds.filter(item => item !== id);
				console.log('selectedIds:', selectedGroupIds);
			
			    // Remove the element from the DOM
			    selectedGroupName.remove();
			
			    console.log('Updated Names:', selectedNames);
			    console.log('Updated IDs:', selectedIds);
			}
		
		let UserAutoSearch = [];
		function getUserGroupList() {
			var type = $("#operationType").val();
			var groupName = $("#groupName").val();
			
			if(type=='' || type==null){
				$("#messageParagraph").text("Please Select USER/GROUP");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}else{
			if (groupName == "") {
				$("#suggesstion-boxGroup_").hide();
			}
			if (type == 'group') {
				var searchVal = groupName;
				$.ajax({
					type: "POST",
					url: "work-space-autosearchGroup",
					dataType: 'json',
					contentType: 'application/json',
					data: searchVal,
					success: function (response) {
						if (response.code == "Success") {
						UserAutoSearch = []
						UserAutoSearch = response.body;
						console.log('RESPONSE',response.body)
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list1" class="searhlist-dsg">';
								for (var i = 0; i < response.body.length; i++) {
									content += '<li class="autocompletedata cp" style="margin-left: -24px;font-weight: 600;" onClick="selectAutocompleteGroupValue(\''
										+ response.body[i].name
										+ '\',\''
										+ response.body[i].key
										+ '\')">'
										+ response.body[i].name
										+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-boxGroup_").show();
								$("#suggesstion-boxGroup_").html(content);

							} else {
								//$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list1">';
								content += '<div onClick="selectAutocompleteValue()">'
									+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-boxGroup_").show();
								$("#suggesstion-boxGroup_").html(content);
							}
						}
					},
					error: function (data) {
						console.log(data);
					}
				})
			}

}

		}

		function selectAutocompleteGroupValue(name, id) {
    if (name) {
        // Filter user data based on the ID
        var userData = UserAutoSearch.filter(data => {
            return data.key.includes(id);
        });
        
        console.log(userData);
        
        var groupUser = JSON.parse(userData[0].code);
        var accessType = $("#rowEdit").val();
        
        console.log('groupEmployee', groupUser);
        
        // Map groupUser to include access types and group information
        let updatedEmployees = groupUser.map(user => {
            user.read = accessType == "Read" ? true : false;
            user.write = accessType == "Write" ? true : false;
            user.delete = accessType == "Delete" ? true : false;
            user.GroupId = id;
            user.GroupName = name;
            return user;
        });
        
        // Update GroupData array with new and existing data
        GroupData = GroupData.length > 0 ? [...GroupData, ...updatedEmployees] : updatedEmployees;
        console.log(GroupData);
        
        // Update search input and suggestion box
        $("#search").attr('data-procat', name);
        $("#suggesstion-boxGroup_").hide();
        
        // Update selected group container
        var selectedGroupData = $('.selected-group-container');
        var existingGroupIds = selectedGroupData.find('.selected-group-id').map(function() {
            return $(this).val();
        }).get();
        
        // Only add if the ID is not already present
        if (!existingGroupIds.includes(id)) {
            var newGroup = '<div class="selected-group-name" value="' + name + '">' +
                               name +
                               '<input type="hidden" class="selected-group-id" value="' + id + '">' +
                               '<div class="remove-icon" onclick="removeSelectedGroupName(this)">×</div>' +
                               '</div>';
                               
            selectedGroupData.append(newGroup);
        }

    } else {
     
        $("#groupSearchId").val("");
        $("#groupName").val("");
        $("#search").val("");
        $("#search").attr('data-procat', "");
        $("#suggesstion-boxGroup_").hide();
    }
}

