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
let selectedActivity = ""; // Global variable to hold the current filter
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
function renderFolderView(folderData) {

	console.log("FolderData-->", folderData);
	const folderView = $('#folderView');
	folderView.empty(); // Clear the current view
	const iconHtml = getNewIcon();
	folderView.append(iconHtml);
	// Filter folderData to include only folders

	const foldersOnly = folderData.filter(item => item.type === 'folder');

	foldersOnly.forEach(item => {
		// Create the folder or file icon
		const icon = $('<div></div>').addClass('icon');
		const img = $('<img>').attr('src', item.type === 'folder' ? '../assets/images/folder.png' : '../assets/images/file-icon.png');
		const name = $('<div></div>').addClass('folderText').text(item.name || item.docUrl.split('/').pop());

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
			// Handle the edit icon action here
			handleEditIconClick(item);
		});

		icon.append(img).append(name).append(createdOn).append(createdBy).append(editIcon);
		//parentId=$("#parentId").val(item.workspaceId);
		//icon.append(img).append(name);

		// If the item is a folder, set click event to open it
		if (item.type === 'folder') {
			icon.click(() => openFolder(item));
		} else if (item.type === 'file') {
			icon.click(() => window.open(item.docUrl, '_blank'));
		}

		folderView.append(icon);
	});
	$("#new").click(addFolder);
	viewTemplate == "list" ? getListView() : getGridView();
}

//Function for get new icon
function getNewIcon() {
	accessType()
	const icon = $('<div id="new"></div>').addClass('icon');
	const img = $('<img>').attr('src', '../assets/images/newfoldericon.png');
	const name = $('<div></div>').text("New");
	icon.append(img).append(name);

	return icon;
}

// Function to open a folder and update the current view
let globalOpenFolderValue = "";
function openFolder(folder) {
	$("#add-btn").show();
	globalOpenFolderValue = folder;
	$("#workspaceId").val('');
	accessType()
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

	/*$("#description").val(folder.desc);
	$("#defaultTags").val(folder.tags != null ? tags : "");
	$("#accessType").val(folder.accessType == "PUBLIC" ? "2" : "1");*/


	var path = currentPath[currentPath.length - 1];

	getAccessDetails(workSpaceId);
	currentPathDetails = path;

	currentFolderData = jsonData.filter(item => item.parentId === folder.workspaceId);
	renderFolderView(currentFolderData);
	updateBreadcrumb();
	$('#backButton').show(); // Show the back button when inside a folder
}

// Function to update the breadcrumb navigation
function updateBreadcrumb() {
	//const pathNames = currentPath.map(id => jsonData.find(item => item.workspaceId === id));
	//console.log('pathNames',pathNames);
	//$('#breadcrumb').text(pathNames.join(' > '));
	const pathNames = currentPath.map(name => {
		const folder = jsonData.find(item => item.name === name && item.type === 'folder');
		return folder ? folder.name : '';
	});

	$('#breadcrumb').text(pathNames.join(' > '));

	if (pathNames.length == 0) {
		getFirstParentFolder();
	}

	noFolderFound();

	$("#accessType").val('');
	$("#accessType").attr("disabled", false);
	$("#accessType").css("background", "#ffffff");

	handleAccessTypeChange();

	viewTemplate == "list" ? getListView() : getGridView();

}


// Function to go back to the previous folder
function goBack() {

	if (currentPath.length > 1) {
		currentPath.pop(); // Remove the last folder from the path
		const previousFolderName = currentPath[currentPath.length - 1];

		// Find the workspaceId for the previous folder
		const previousFolder = jsonData.find(item => item.name === previousFolderName && item.type === "folder");
		const previousWorkspaceId = previousFolder ? previousFolder.workspaceId : "";


		// Filter the data for folders and files under the previous workspace
		const currentFolderData = jsonData.filter(item => item.parentId === previousWorkspaceId);

		// to Get Updated Path Name
		const pathNames = currentPath.map(name => {
			const folder = jsonData.find(item => item.name === name && item.type === 'folder');
			return folder ? folder.name : '';
		});

		/*const pathDetails = currentPath.map(name => {
			const folder = jsonData.find(item => item.name === name && item.type === 'folder');
			if (folder) {
				return {
					name: folder.name,
					desc: folder.desc || '',
					tags: folder.tags || [],
					accessType: folder.accessType || ''
				};
			}
			return null;
		}).filter(item => item !== null); // Filter out null values if no folder matches


		console.log("pathdetails-->", pathDetails);

		$("#description").val(pathDetails.desc);
		$("#defaultTags").val(pathDetails.tags != null ? tags : "");
		$("#accessType").val(pathDetails.accessType == "PUBLIC" ? "2" : "1");*/

		$("#parentFolderPath").val(pathNames[pathNames.length - 1]);
		$("#parentFolderName").val(pathNames[pathNames.length - 1]);

		$("#workspaceid").html(previousWorkspaceId);
		$("#workspaceId").val('');
		$("#parentId").val(previousWorkspaceId);

		renderFolderView(currentFolderData);
		updateBreadcrumb();

		$("#createWorkspace").addClass('active show');
		$("#workSpaceDetails").show();
		$(".accessLink").css("opacity", "0");
		$(".accessLink").css("pointer-events", "none");
		$("#readAccess").removeClass('active show');
		$("#add-btn").show();
		$("#next-btn-parent").hide();
		$("#parentSaveBtn").show();
		$("#deleteAccess").removeClass('active show');
		document.getElementById("workSpaceCreation").click();
	} else {
		currentPath.pop(); // Remove the last item in the path (which is the root folder)
		// Render the root folder view
		const currentFolderData = jsonData.filter(item => item.parentId === "");

		renderFolderView(currentFolderData);
		updateBreadcrumb();
		$('#backButton').hide(); // Hide the back button when at the root

	}

	//handelDivElementAfterSave();
}



// Function to add a new folder
function addFolder() {
	/*	accessType();
		var path = currentPath[currentPath.length - 1];
	
		getAccessDetails(workSpaceId);
		currentPathDetails = path;
		$("#parentFolderName").val(path);
		$("#workspaceModal").modal('show');
		$("#newFolderName").val('');
		$("#accessType").val('');
		$("#defaultTags").val('');
		$("#description").val('');
	
		if (path === undefined || path === "") {
	
			$("#parentFolderPath").val('');
			parentId = "";
		}*/


	accessType();
	var path = currentPath[currentPath.length - 1];

	getAccessDetails(workSpaceId);
	currentPathDetails = path;
	$("#parentFolderName").val(path);
	$("#newFolderName").val('');
	$("#accessType").val('');
	$("#defaultTags").val('');
	$("#description").val('');
	$("#workspaceModal").modal('show');

	if (path === undefined || path === "") {

		$("#parentFolderPath").val('');
		parentId = "";
	}



}



function getAccessDetails(workSpaceId) {
	agGrid.simpleHttpRequest({
		url: "workspace-new-getAccesDetails?id=" + workSpaceId
	}).then(function(response) {
		if (response.code === "Success") {



			var responseData = JSON.parse(response.body);
			var accessData = responseData.AccessDetails;


			if (accessData[0].parentId != "" && accessData[0].parentfoldername != "" && accessData[0].accessType == "RESTRICTED") {
				$("#accessType").val("1");
				$("#accessType").attr("disabled", true);
				document.getElementById("accessType").style.setProperty("background", "#00000029", "important");
				handleAccessTypeChange();

			}
			else {
				$("#accessType").val('');
				$("#accessType").attr("disabled", false);
				$("#accessType").css("background", "#ffffff");

			}

		} else {
			console.log("Failed to fetch data");
		}
	});
}

function saveWorkSpace() {





	var event = {};
	var accessControl = [];
	var userDtls = [];
	var groupDtls = [];
	var notification = [];
	var validation = true;
	activityOptions.api.forEachNode(function(rowNode, index) {
		accessControl.push(rowNode.data);
	});

	event.workspaceId = $("#workspaceId").val();
	event.parentId = parentId;
	event.parentFolderName = $("#parentFolderName").val();
	event.newFolderName = $("#newFolderName").val();
	event.accessType = $("#accessType").val();
	event.accessTypeName = $("#accessType option:selected").text();
	event.owner = $("#owner").val();
	event.ownerId = $("#ownerId").val();
	event.orgName = $("#orgName").val();
	event.orgDivision = $("#orgDivision").val();
	event.defaultTags = $("#defaultTags").val();
	event.description = $("#description").val().replace(/'/g, '#').replace(/"/g, '$');
	event.parentFolderPath = $("#parentFolderPath").val();
	event.currentPathDetails = currentPathDetails;
	var access = accessControl;

	for (var i = 0; i < access.length; i++) {
		var activity = access[i];
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
	event.userDtls = userDtls;
	event.groupDtls = GroupData;
	var data = JSON.stringify(event);

	console.log('event ::::', event);


	console.log("Form save Data-->", data);





	if ($("#newFolderName").val() == "") {
		toastr.error('New Folder Name Required');
		validation = false;
	}
	else {

		if ($("#accessType").val() == "") {
			toastr.error('Type Required');
			validation = false;
		}
	}
	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "workspace-new-save",
			dataType: "json",
			contentType: "application/json",
			data: data,
			success: function(response) {
				$('.loader').hide();
				if (response.code === "success") {
					$("#workspaceModal").modal('hide');
					toastr.success(response.message);
					$("#parent-cancel-btn").hide();

					$.ajax({
						type: "GET",
						url: "workspace-new-folderDetails",
						success: function(folderResponse) {
							if (folderResponse.message === "Success") {
								/*const jsonArray = JSON.parse(folderResponse.body).filter(item => item !== null);
								const currentFolderData = jsonArray.filter(item => item.parentId === "");

								console.log("Updated Folder Data:", folderResponse.body);
								console.log("jsonArray-->" ,jsonArray);
								console.log("currentFolderData-->",currentFolderData);	

								dynamicUpdateOfFolder(currentFolderData);
*/

								viewWorkSpace()
									.then(() => {
										renderFolderView(currentFolderData);
										currentPath = [];
										$('#breadcrumb').text('');
										$("#backButton").hide();
										getFirstParentFolder();
										disableAllTheField();
									})
									.catch((err) => {
										console.error('ERROR ', err.message);
									});

								handelDivElementAfterSave();
							} else {
								console.error("Failed to fetch updated folder details:", folderResponse.message);
							}
						},
						error: function(err) {
							console.error("Error fetching folder details:", err);
						}
					});

					// Display success message


				} else {
					$('.loader').hide();
					toastr.error(response.message);
					$("#parent-cancel-btn").show();
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}

function accessType() {

	var type = $("#accessType").val();
	if (type == '1') {
		$("#workSpaceGrid").show();
	} else {
		$("#workSpaceGrid").hide();
	}
}


//update function for userAccess

function handleAccessTypeChange() {
	var type = $("#accessType").val();
	if (type == '1') {
		$("#workSpaceGrid").show();
		$(".accessLink").css("opacity", "1");
		$(".accessLink").css("pointer-events", "all");
		$("#next-btn-parent").show();

		$("#parentSaveBtn").hide();
	} else {
		$("#workSpaceGrid").hide();
		$(".accessLink").css("opacity", "0");
		$(".accessLink").css("pointer-events", "none");
		$("#next-btn-parent").hide();
		$("#parentSaveBtn").show();
	}
}


var rowNodeEditData = "";
function editProduct(id) {
	//	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:380px;";
	//document.getElementById("main").style.width = "75%";
	$("#groupList").hide();
	//$("#workSpaceDetails").hide();
	$("#workSpaceGrid").show();
	$("#accessDiv ").show();
	$("#selectedParticipants").hide();
	rowNodeEditData = activityOptions.api.getRowNode(id);
	var editData = rowNodeEditData;
	$("#rowEdit").val(id);
	var rowNodeId = $("#rowEdit").val();
	$("#userName").val('');
	$("#userSearchId").val('');
	$(".selected-user-container").html('');
	$(".selected-group-container").html('');
	$("#groupName").val('');
	$("#groupSearchId").val('');
	$("#selectedParticipants1").hide();
	$("#selectedParticipants").show();
	$("#workSpaceAccessSave").hide();
	$("#workSpaceSideNavSave").show();

	userType();
}
function userType() {
	$("#userSearchId").val('');
	$("#userName").val('');
	$("#groupSearchId").val('');
	$("#groupName").val('');
	var type = $("#operationType").val();
	if (type == 'user') {
		$("#userList").show();
		$("#userNameList").show();
		$("#groupList").hide();
		$("#groupNameList").hide();
		$(".selected-user-container").empty();
		var editData = rowNodeEditData.data.userId;
		if (editData != 'undefined' || editData != '') {
			let userIds = rowNodeEditData.data.userId.split(', ');
			let userNames = rowNodeEditData.data.userName.split(', ');
			if (userIds != '') {
				userIds.forEach((userId, index) => {
					let userName = userNames[index];
					let editUser = '<div class="selected-name" value="' + userName + '">' + userName +
						'<div class="remove-icon" onclick="removeSelectedName(this)">x</div>' +
						'<input type="hidden" class="selected-user-id" value="' + userId + '">' +
						'</div>';
					$(".selected-user-container").append(editUser);
				});

			}


		}
	} else {
		$("#groupList").show();
		$("#groupNameList").show();
		$("#userList").hide();
		$("#userNameList").hide();
		$("#selectedParticipants1").show();
		$(".selected-group-container").empty();
		var editData = rowNodeEditData.data;
		var groupIds = rowNodeEditData.data.groupId;
		if (editData != 'undefined' || editData != '') {
			if (rowNodeEditData.data.groupId && rowNodeEditData.data.groupName) {
				$("#groupList").show();
				$("#selectedParticipants1").show();
				$("#userList").hide();
				$("#userNameList").hide();

				if (groupIds != '') {

					let groupIds = rowNodeEditData.data.groupId.split(',');
					let groupNames = rowNodeEditData.data.groupName.split(',');


					groupIds.forEach((groupId, index) => {
						let groupName = groupNames[index];
						let editUser = '<div class="selected-group-name" value="' + groupName + '">' + groupName +
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
// Initialize the folder view when the document is ready
$(document).ready(function() {
	$("#workSpaceGrid").hide();
	$("#accessDiv ").hide();
	$("#workSpaceAccessSave").show();
	$("#commonAggrid").hide();
	$("#workSpaceSideNavSave").hide();
	viewWorkSpace()
		.then(() => {
			renderFolderView(currentFolderData);
			$('#backButton').click(goBack);
			getFirstParentFolder();
		})
		.catch((err) => {
			console.error('ERROR ', err.message);
		});

	disableAllTheField();
	$(".folderHeader").hide();



	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);

	var rowData = [
		{ siNo: 1, activityName: 'Read', userId: '', userName: '', groupId: '', groupName: '' },
		{ siNo: 2, activityName: 'Write', userId: '', userName: '', groupId: '', groupName: '' },
		{ siNo: 3, activityName: 'Delete', userId: '', userName: '', groupId: '', groupName: '' }
	];
	rowData.forEach(row => {
		row.userId = '';
		row.userName = '';
		row.groupId = '';
		row.groupName = '';
	});
	commonAggridRow = rowData;
	activityOptions.api.setRowData(rowData);

	$("#seacrhInput").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault(); // Stop the form from submitting
			console.log("Enter key pressed, calling filter function...");
			searchFolder(); // Call the filter function
		}
	});

});

// for activity table
var activityDefs = [
	{
		headerName: "Activity",
		field: "activityName",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
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
		width: 175,
		cellStyle: {
			textAlign: 'center'
		},
	}];



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
	getRowNodeId: function(data) {
		return data.activityName;
	},
	isExternalFilterPresent: () => !!selectedActivity, // Check if a filter is active
	doesExternalFilterPass: (node) => {
		// Pass only rows that match the selected activity
		return selectedActivity ? node.data.activityName === selectedActivity : true;
	},
};

function getUserNameList() {
	var type = $("#operationType").val();
	var userName = $("#userName").val();

	if (type == '' || type == null) {
		$("#messageParagraph").text("Please Select USER/GROUP");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	} else {
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
				success: function(response) {
					if (response.code == "Success") {
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
				error: function(data) {
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
	// Remove the name and ID from the selected lists
	selectedNames = selectedNames.filter(item => item !== name);
	selectedIds = selectedIds.filter(item => item !== id);

	// Remove the element from the DOM
	selectedName.remove();

}

var selectedGroupNames = [];
var selectedGroupIds = [];
function removeSelectedGroupName(element) {
	var selectedGroupName = $(element).closest('.selected-group-name');
	var name = selectedGroupName.attr('value');
	var id = selectedGroupName.find('.selected-group-id').val();
	// Remove the name and ID from the selected lists
	selectedGroupNames = selectedGroupNames.filter(item => item !== name);
	selectedGroupIds = selectedGroupIds.filter(item => item !== id);

	// Remove the element from the DOM
	selectedGroupName.remove();

}

let UserAutoSearch = [];
function getUserGroupList() {
	var type = $("#operationType").val();
	var groupName = $("#groupName").val();

	if (type == '' || type == null) {
		$("#messageParagraph").text("Please Select USER/GROUP");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	} else {
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
				success: function(response) {
					if (response.code == "Success") {
						UserAutoSearch = []
						UserAutoSearch = response.body;
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
				error: function(data) {
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

function saveTableData(event) {
	event.preventDefault();

	// Initialize or reset selected data arrays
	let selectedData = [];
	let selectedGroup = [];

	// Collect selected user data
	$('.selected-name').each(function() {
		let name = $(this).attr('value'); // Retrieve the name
		let id = $(this).find('.selected-user-id').val(); // Retrieve the ID

		// Check for duplicate entries
		let exists = selectedData.some(item => item.userId === id);
		if (!exists) {
			selectedData.push({ userName: name, userId: id });
		}
	});

	// Collect selected group data
	$('.selected-group-name').each(function() {
		let name = $(this).attr('value'); // Retrieve the name
		let id = $(this).find('.selected-group-id').val(); // Retrieve the ID

		// Check for duplicate entries
		let exists = selectedGroup.some(item => item.groupId === id);
		if (!exists) {
			selectedGroup.push({ groupName: name, groupId: id });
		}
	});

	// Create comma-separated strings
	let userNames = selectedData.map(item => item.userName).join(', ');
	let userIds = selectedData.map(item => item.userId).join(', ');
	let groupNames = selectedGroup.map(item => item.groupName).join(', ');
	let groupIds = selectedGroup.map(item => item.groupId).join(', ');


	// Retrieve all rows from the grid
	let allRowData = [];
	activityOptions.api.forEachNode(node => allRowData.push({ ...node.data }));

	// Find the row to update
	//let rowEdit = $("#rowEdit").val();

	let rowEdit = getTheActiveTabName();

	let rowToUpdateIndex = allRowData.findIndex(row => row.activityName === rowEdit);

	if (rowToUpdateIndex !== -1) {
		// Update the identified row
		allRowData[rowToUpdateIndex] = {
			...allRowData[rowToUpdateIndex],
			userName: userNames,
			userId: userIds,
			groupName: groupNames,
			groupId: groupIds,
		};

		// Update the grid with the modified data
		activityOptions.api.setRowData(allRowData);

		$("#userName").val('');
		$("#userSearchId").val('');
	} else {
		console.error('Row to update not found!');
	}

	closeNav();
}


function closeWorkspaceModal() {
	$('#workspaceModal').modal('hide');
	$("#parentFolderPath").val('');
	$("#workspaceId").val('');
	$("#parentId").val('');
	$("#newFolderName").val('');
	$("#accessType").val('');
	$("#defaultTags").val('');
	$("#description").val('');
	$("#rowEdit").val(null);
}

function closeUserGroupModal() {
	$('#workspaceModal').modal('hide');
	$("#parentFolderPath").val('');
	$("#workspaceId").val('');
	$("#parentId").val('');
	$("#newFolderName").val('');
	$("#accessType").val('');
	$("#defaultTags").val('');
	$("#description").val('');
	$("#rowEdit").val(null);
}

//function for closeNav child
function closeNav() {
	//$("#workSpaceGrid").hide();
	//$("#accessDiv").hide();
	//$("#workSpaceDetails").show();
	//$("#workSpaceSideNavSave").hide();
	//$("#workSpaceAccessSave").show();
	$("#parameterName").val('');
	$("#parameterId").val('');
	$("#parameterValue").val('');
	$("#rowEdit").val(null);
}


//function to get the first folder details on page render
function getFirstParentFolder() {
	var parentFolder = intialData[0];
	var userAccessData = JSON.parse(parentFolder.userAccess);
	if (parentFolder) {
		parentId = parentFolder.parentId;
		$("#parentFolderName").val(parentFolder.name);
		$("#workspaceId").val(parentFolder.workspaceId);
		$("#workspaceid").html(parentFolder.workspaceId);
		$("#accessType").val(userAccessData.accessTypeName == "PUBLIC" ? "2" : "1");
		$("#defaultTags").val(userAccessData.defaultTags);
		$("#description").val(userAccessData.description);
		$("#newFolderInputDiv").hide();
	}

}

//function to add new Folder Details
function newFolderDetailsAdd() {
	$("#newFolderInputDiv").show();
	//parentId = "";
	$("#parentFolderName").val("");
	$("#workspaceId").val("");
	$("#workspaceid").html("");


	$("#newFolderName").prop("readonly", false);
	$("#accessType").attr("disabled", false);
	$("#defaultTags").prop("readonly", false);
	$("#description").prop("readonly", false);

	var workspaceParentId = $("#parentId").val();
	getAccessDetails(workspaceParentId);


	addFolder();
}

//Function To Dynamic Handel The Folder Details for the List View

function dynamicUpdateOfFolder(folderData) {
	const folderView = $('#folderView');
	folderView.empty(); // Clear the current view
	/*const iconHtml = getNewIcon();
	folderView.append(iconHtml);*/

	folderData.forEach(item => {
		const icon = $('<div></div>').addClass('icon');
		const img = $('<img>').attr('src', item.type === 'folder' ? '../assets/images/folder.png' : '../assets/images/file-icon.png');
		const name = $('<div></div>').text(item.name || (item.docUrl ? item.docUrl.split('/').pop() : ''));

		icon.append(img).append(name);

		if (item.type === 'folder') {
			icon.click(() => openFolder(item));
		} else if (item.type === 'file') {
			icon.click(() => window.open(item.docUrl, '_blank'));
		}

		folderView.append(icon);
	});

	$("#new").off('click').on('click', addFolder);
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


function disableAllTheField() {
	$("#newFolderName").val('');
	$("#newFolderName").prop("readonly", true);
	$("#accessType").val('');
	$("#accessType").attr("disabled", true);
	$("#defaultTags").val('');
	$("#defaultTags").prop("readonly", true);
	$("#description").val('');
	$("#description").prop("readonly", true);
}


function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val().trim();

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


function createWorkspace() {
	$("#commonAggrid").hide();
	$("#workSpaceDetails").show();
}

function readAccess() {
	emptyHtml();
	//$("#commonAggrid").show();
	appendAccessControl("readAccessControl");
	setTimeout(() => {
		// intializeGrid();
	}, 10);
	editProduct("Read");

	hideRows("Read");
}

function writeAccess() {
	//$("#commonAggrid").show();
	emptyHtml();
	appendAccessControl("writeAccessControler");
	//intializeGrid();
	editProduct("Write");

	hideRows("Write");
}

function deleteAccess() {
	//$("#commonAggrid").show();
	emptyHtml();
	appendAccessControl("deleteAccessControler");
	//intializeGrid();
	editProduct("Delete");
	hideRows("Delete");

}


function appendAccessControl(parentElementId) {
	var htmlContent = `

        <div class="row" id="accessDiv">
            <div class="col-lg-12">
                <div class="form-group">
                    <input type="hidden" class="form-control" id="rowEdit">
                </div>
            </div>
			
			<div class="col-lg-12 participantsList" id="userNameList">
									               <div class="form-group" id="selectedParticipants">
									                   <label> Selected User</label>
									                   <div class="selected-user-container"></div>
									               </div>
									           </div>
											   
											   <div class="col-lg-12 participantsList" id="groupNameList">
											                   <div class="form-group" id="selectedParticipants1">
											                       <label> Selected Group</label>
											                       <div class="selected-group-container"></div>
											                   </div>
											               </div>
			<div class="col-lg-12">
			                <div class="form-group">
			                   <button class="btn btn-primary edit-btn" style="float:right" id="save1" onclick="saveTableData(event)">SAVE</button>
			                </div>
			            </div>
						
						
						

            <div class="col-md-6">
                <div class="form-group">
                    <label>User/Group</label>
                    <div class="select">
                        <select id="operationType" onblur="removeValid(event);" onchange="userType();">
                            <option value="user">User-Id</option>
                            <option value="group">User-Group</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="col-md-6 leadNameCls" id="userList">
                <div class="form-group">
                    <label> USER LIST</label> 
                    <input type="text" id="userName" placeholder="Enter User Name" class="form-control" onkeyup='getUserNameList()'>
                    <input type="hidden" id="userSearchId">
                    <div id="suggesstion-boxUser_" class="sugg-cls" style="margin-top: 5px;"></div>
                </div>
            </div>

           

            <div class="col-md-6 leadNameCls" id="groupList">
                <div class="form-group">
                    <label> Group LIST</label>
                    <input type="text" id="groupName" placeholder="Enter Group Name" class="form-control" onkeyup='getUserGroupList()'>
                    <input type="hidden" id="groupSearchId">
                    <div id="suggesstion-boxGroup_" class="sugg-cls" style="margin-top: 5px;"></div>
                </div>
            </div>

            
        </div>

     
    `;

	// Append the HTML content to the parent element
	var parentElement = document.getElementById(parentElementId);

	parentElement.innerHTML = htmlContent;
}



function emptyHtml() {
	$("#deleteAccessControler").html('');
	$("#writeAccessControler").html('');
	$("#readAccessControl").html('');
}


function getTheActiveTabName() {

	const activeLink = document.querySelector(".nav-link.active");

	if (activeLink) {
		const activeId = activeLink.getAttribute("id");
		return activeId;

	}
}


//function to hide and show rows manually

function hideRows(activity) {

	selectedActivity = activity;
	activityOptions.api.onFilterChanged();

	let data = [];

	activityOptions.api.forEachNode((node) => {
		data.push(node.data);
	});


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

let globalItem = "";
function handleEditIconClick(item) {
	globalItem = item;
	$("#parent-cancel-btn").show();
	$("#parentFolderPath").val(item.parentFolderPath);
	$("#parentFolderName").val(item.parentFolderPath);
	$("#workspaceid").html(item.workspaceId);

	var userAccessData = JSON.parse(item.userAccess);
	
	console.log(userAccessData)
	$("#accessType").val(userAccessData.accessTypeName == "PUBLIC" ? "2" : "1");
	$("#defaultTags").val(userAccessData.defaultTags);
	$("#description").val(userAccessData.description);
	$("#newFolderName").val(userAccessData.newFolderName);
	handleAccessTypeChange();
	$("#workspaceId").val(item.workspaceId);
	$(".accessControlId").text(item.workspaceId);

	folderPath += item.name + "/";
	currentPath.push(item.name);

	var path = currentPath[currentPath.length - 1];

	currentPathDetails = path;

	//for userlist

	var userListdata = userAccessData.userDtls;

	let readUsers = [];
	let readUserIds = [];

	let writeUsers = [];
	let writeUserIds = [];

	let deleteUsers = [];
	let deleteUserIds = [];


	userListdata.forEach((element) => {
		if (element.read === true) {
			readUsers.push(element.userName);
			readUserIds.push(element.userId);
		}
		if (element.write === true) {
			writeUsers.push(element.userName);
			writeUserIds.push(element.userId);
		}
		if (element.delete === true) {
			deleteUsers.push(element.userName);
			deleteUserIds.push(element.userId);
		}
	});

	const readUsersString = readUsers.join(', ');
	const readUserIdsString = readUserIds.join(', ');

	const writeUsersString = writeUsers.join(', ');
	const writeUserIdsString = writeUserIds.join(', ');

	const deleteUsersString = deleteUsers.join(', ');
	const deleteUserIdsString = deleteUserIds.join(', ');


	//UserGroup list

	var userGroupList = userAccessData.groupDtls;

	let readUsersGroup = [];
	let readUserIdsGroup = [];

	let writeUsersGroup = [];
	let writeUserIdsGroup = [];

	let deleteUsersGroup = [];
	let deleteUserIdsGroup = [];

	userGroupList.forEach((element) => {
		if (element.read === true) {
			readUsersGroup.push(element.GroupName);
			readUserIdsGroup.push(element.GroupId);
		}
		if (element.write === true) {
			writeUsersGroup.push(element.GroupName);
			writeUserIdsGroup.push(element.GroupId);
		}
		if (element.delete === true) {
			deleteUsersGroup.push(element.GroupName);
			deleteUserIdsGroup.push(element.GroupId);
		}
	});

	const readUsersGroupString = readUsersGroup.join(', ');
	const readUserIdsGroupString = readUserIdsGroup.join(', ');

	const writeUsersGroupString = writeUsersGroup.join(', ');
	const writeUserIdsGroupString = writeUserIdsGroup.join(', ');

	const deleteUsersGroupString = deleteUsersGroup.join(', ');
	const deleteUserIdsGroupString = deleteUserIdsGroup.join(', ');


	const rowData = [
		{ siNo: 1, activityName: 'Read', userId: readUserIdsString, userName: readUsersString, groupId: readUserIdsGroupString, groupName: readUsersGroupString },
		{ siNo: 2, activityName: 'Write', userId: writeUserIdsString, userName: writeUsersString, groupId: writeUserIdsGroupString, groupName: writeUsersGroupString },
		{ siNo: 3, activityName: 'Delete', userId: deleteUserIdsString, userName: deleteUsersString, groupId: deleteUserIdsGroupString, groupName: deleteUsersGroupString }
	];


	activityOptions.api.setRowData(rowData);



	$("#newFolderName").prop("readonly", false);
	$("#accessType").attr("disabled", false);
	$("#defaultTags").prop("readonly", false);
	$("#description").prop("readonly", false);
	$("#newFolderInputDiv").show();
	$("#add-btn").hide();


	//userAccessData.accessTypeName == "PUBLIC" ? $("#commonAggrid").hide() : $("#commonAggrid").show();
}


function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement.id == "Read") {
		readAccess();
	}
	else if (tabElement.id == "Write") {
		writeAccess();
	}
	else if (tabElement.id == "Delete") {
		deleteAccess();
	}
	else if (tabElement.id == "createWorkspace") {
		createWorkspace();
	}
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);

		tabTrigger.show();
	}
}

function previousButton(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);
	if (tabElement.id == "Read") {
		readAccess();
	}
	else if (tabElement.id == "Write") {
		writeAccess();
	}
	else if (tabElement.id == "Delete") {
		deleteAccess();
	}
	else if (tabElement.id == "createWorkspace") {
		createWorkspace();
		$("#createWorkspace").addClass('active show');
		$("#readAccess").removeClass('active show');
		$("#workSpaceDetails").show();
	}


	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}


function handelDivElementAfterSave() {
	emptyHtml();
	createWorkspace();
	$("#createWorkspace").addClass('active show');
	$("#workSpaceDetails").show();
	$(".accessLink").css("opacity", "0");
	$(".accessLink").css("pointer-events", "none");
	$("#readAccess").removeClass('active show');
	$("#add-btn").show();
	$("#next-btn-parent").hide();
	$("#parentSaveBtn").show();
	$("#deleteAccess").removeClass('active show');
	getFirstParentFolder();
	document.getElementById("workSpaceCreation").click();
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

function cancelWorkSpaceBtn() {
	var workspaceid = globalOpenFolderValue.workspaceId;
	$("#parentId").val(workspaceid);
	$("#workspaceId").val('');
	$("#parentFolderName").val(globalOpenFolderValue.name);
	$("#newFolderName").val('');
	$("#description").val('');
	$("#parent-cancel-btn").hide();
	//$("#add-btn").show();
}