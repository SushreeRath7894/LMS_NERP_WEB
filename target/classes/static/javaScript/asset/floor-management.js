$(() => {
	$('#roomType').select2();  //#roomType
	$('#variationType').select2();
});
$(document).ready(function() {

	var gridDivFloor = document.querySelector('#myGridFloor');
	new agGrid.Grid(gridDivFloor, gridOptionsFloor);
	var gridDivRoom = document.querySelector('#myGridFloorDets');
	new agGrid.Grid(gridDivRoom, gridOptionsFloorDets);
	var gridDivRoom = document.querySelector('#myGridRoom');
	new agGrid.Grid(gridDivRoom, gridOptionsRoom);
	/*	cancelRoom();
		cancelFloorDets();*/
	viewBuildingFloor();

	/*	$('#quickFilter').on('keypress', function(e) {
			if (e.which === 13) { 
				onQuickFilterChanged();
			}
		});*//*
$(".br-m-btn").attr("disabled", false);
$('#addRoom').attr('disabled', false);
$(".br-dis").attr("disabled", true);
$('#editRoom').attr('editRoom', true);
$('#deleteRoom').attr('disabled', true);
*/
	$(".br-s-btn").hide();
});

function nextTab(id) {
	console.log(id);
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
	cancelRoom();
	cancelFloorDets();
	//	getDets();
}
/*				Grid Detailsss				*/

// 			Floor  Details grid	starts
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Location ID",
		field: "locationId",
	}, {
		headerName: "Location Name",
		field: "locationName"
	}, /*{
        headerName: "Location Code",
        field: "locationCode"
    },*/ {
		headerName: "Floor Sl No.",
		field: "floorSlNo"
	}, {
		headerName: "Floor Id",
		field: "floorId"
	}, {
		headerName: "Floor Code",
		field: "floorCode"
	}, {
		headerName: "Floor Name",
		field: "floorName"
	}, {
		headerName: "Room Count",
		field: "floorCount"
	},
];
var gridOptionsFloor = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		//       width: 149,
		height: 10
	},
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: onSelectionChangedFloor
};
function onSelectionChangedFloor() {
	var selectedNodes = gridOptionsFloor.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	treq = selectedData.map(node => node.locationId);

	var selectedRows = gridOptionsFloor.api.getSelectedRows();
	id = "";
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + '"' + selectedRows[i].locationId + '",';
	}
	id = id.substring(0, id.length - 1);
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	var uid = $("#sessionId").val();

	if (rowCount > 0) {
		viewFloorRooms();
		viewFloorDetss();
	} else {
		$("#floorId").html("");
		gridOptionsFloorDets.api.setRowData("");
		gridOptionsRoom.api.setRowData("");
	}
}
// 			Floor  Details grid	ends
// 			Room  Details grid	starts
var columnDefsRoom = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: false
	},
	{
		headerName: "Room ID",
		field: "roomId",
		width: 100,
	}, {
		headerName: "Room Type Id",
		field: "roomTypeid",
		hide: true
	}, {
		headerName: "Room Type",
		field: "roomType",
		width: 150,
	}, {
		headerName: "Room Name",
		field: "roomName"
	}, {
		headerName: "Bed Type Id",
		field: "roomVariationId",
		hide: true
	}, {
		headerName: "Bed Type",
		field: "roomVariation"
	}, {
		headerName: "Room Code",
		field: "roomCode"
	}, {
		headerName: "Room Sl No.",
		field: "roomSlNO"
	}, {
		headerName: "Room Height",
		field: "roomHeight"
	}, {
		headerName: "Room Width",
		field: "roomWidth"
	}, {
		headerName: "Room Length",
		field: "roomLength"
	},
];
var gridOptionsRoom = {
	columnDefs: columnDefsRoom,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		//    flex: 1,
		height: 10
	},
	onSelectionChanged: onSelectionChangedRoom
};
function onSelectionChangedRoom() {
	var selectedRows = gridOptionsRoom.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		var id = selectedRows[0].roomId;
		$(".br-dis").attr("disabled", false);
	} else {
		$(".br-dis").attr("disabled", true);
	}
}
// Room Grid ends
// 			Room  Details grid	starts
var columnDefsFloorDets = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: false
	},
	{
		headerName: "Detail ID",
		field: "detsId",
	}, {
		headerName: "Type",
		field: "type"
	}, {
		headerName: "Quantity",
		field: "quantity"
	}, {
		headerName: "Height",
		field: "height"
	}, {
		headerName: "Width",
		field: "width"
	}, {
		headerName: "Length",
		field: "length"
	},
];
var gridOptionsFloorDets = {
	columnDefs: columnDefsFloorDets,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		//     flex: 1,
		height: 10
	},
	onSelectionChanged: onSelectionChangedFloorDets
};
function onSelectionChangedFloorDets() {
	var selectedRows = gridOptionsFloorDets.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		var id = selectedRows[0].detsId;
		$(".br-dis").attr("disabled", false);
	} else {
		$(".br-dis").attr("disabled", true);
	}
}
// Room Grid ends
// view floor building details
function viewBuildingFloor() {
	agGrid.simpleHttpRequest({
		url: 'floor-management-view-building-floor'
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.Property;
		console.log("allData=====", allData)
		if (allData != null) {
			gridOptionsFloor.api.setRowData(allData);
			var firstRowNode = gridOptionsFloor.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		} else {
			$('#totalReq').find('span').html("0");
			gridOptionsFloor.api.setRowData("");
		}

	});
}
function viewFloorRooms() {
	var selectedRows = gridOptionsFloor.api.getSelectedRows();
	var id = selectedRows[0].floorId;

	/*console.clear();
	console.log("selectedRows[0] =>", selectedRows[0])*/

	$("#floorId").html(id);
	$("#floorId1").html(id);
	agGrid.simpleHttpRequest({
		url: 'floor-management-view-floor-rooms?id=' + id,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.Property;
		console.log("allData=====", allData)
		if (allData != null) {
			gridOptionsRoom.api.setRowData(allData);
			onSelectionChangedRoom();
		} else {
			$('#totalReq').find('span').html("0");
			gridOptionsRoom.api.setRowData("");
		}

	});
}
function cancelRoom() {
	gridOptionsRoom.api.deselectAll();
	$(".formValidation").remove();
	$("#myGridRoom").show();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
}

function editRoom() {
	addRoom();
	var selectedRows1 = gridOptionsRoom.api.getSelectedRows();
	id = selectedRows1[0].roomId;
	var selectedRow = "";
	gridOptionsRoom.api.forEachNode(function(rowNode, index) {
		if (rowNode.data.roomId == id) {
			selectedRow = rowNode;
		}
	});
	console.log("selectedRow", selectedRow)
	$("#roomId").html(selectedRow.data.roomId);
	$("#roomDBId").val(selectedRow.data.roomId);
	$("#roomCode").val(selectedRow.data.roomCode);
	$("#roomName").val(selectedRow.data.roomName);
	$("#roomType").val(selectedRow.data.roomTypeid).trigger('change');
	$("#roomHeight").val(selectedRow.data.roomHeight);
	$("#roomWidth").val(selectedRow.data.roomWidth);
	$("#roomLength").val(selectedRow.data.roomLength);
	$("#variationType").val(selectedRow.data.roomVariationId).trigger('change');
}
function addRoom() {
	$(".formValidation").remove();
	$("#myGridRoom").hide();
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#roomId").html("");
	$("#roomCode").val("");
	$("#roomName").val("");
	$("#roomType").val("").trigger('change');
	$("#roomHeight").val("");
	$("#roomWidth").val("");
	$("#roomLength").val("");
	$("#roomDBId").val("");
}
function saveRoom() {
	data = {};
	var valid = true;
	var selectedRows = gridOptionsFloor.api.getSelectedRows();
	var floorId = selectedRows[0].floorId;
	data.roomId = $("#roomDBId").val();
	data.roomCode = $("#roomCode").val();
	data.roomName = $("#roomName").val();
	data.roomType = $("#roomType").val();
	data.floorId = floorId;
	data.roomHeight = $("#roomHeight").val();
	data.roomWidth = $("#roomWidth").val();
	data.roomLength = $("#roomLength").val();
	data.variationType = $("#variationType").val();

	if (data.roomCode == null || data.roomCode == "") {
		toastr.error('Space Code is required');
		return;
	}
	if (data.roomName == null || data.roomName == "") {
		toastr.error('Space Name is required');
		return;
	}
	if (data.roomType == null || data.roomType == "") {
		toastr.error('Space Type is required');
		return;
	}
	if (valid) {
		$('.loader').show();
		$("body").addClass("overlay");
		submitRoom(data, data.roomId);
	}
}
function submitRoom(dataset, id) {
	console.log("ROOM DATA:::", dataset)
	$.ajax({
		type: "POST",
		url: "floor-management-save-room",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				if (id) {
					toastr.success("Room Updated Successfully");
				} else {
					toastr.success("Room Added Successfully");
				}
				viewFloorRooms();
				cancelRoom();
			} else {
				$('.loader').hide();
				toastr.success("Something Went Wrong");
			}
		},
		error: function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})
}
function deleteRoom() {
	$('#deletemodal').modal('show');
}
function cancelModalBtn() {
	$('.modal').modal('hide');
}
function deleteRoomOnClick() {
	var selectedRows = gridOptionsRoom.api.getSelectedRows();
	var id = selectedRows[0].roomId;
	if (id) {
		$.ajax({
			type: "POST",
			url: "floor-management-delete-room?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					gridOptionsRoom.api.deselectAll();
					viewFloorRooms();
					cancelModalBtn();
					toastr.success("Room Deleted Successfully");
					$('#deleteOwnerModal').modal('hide');

				} else {
					$('.loader').hide();
					toastr.success("Something went to wrong!");
					$('#deleteOwnerModal').modal('hide');
				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
		$('.loader').hide();
	}
}
//==========================================================================================================
//==========================================================================================================
//===========================================================================================================
//==========================================================================================================
//============================================================================================================
//============================================================================================================
//=========================================================================================================
function onQuickFilterChanged() {
	gridOptionsFloor.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	const firstRowNode = gridOptionsFloor.api.getDisplayedRowAtIndex(0);

	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}

function reset() {
	let data = $("#quickFilter").val();
	if (data != null && data != "") {
		$("#quickFilter").val("");
		onQuickFilterChanged();
	}
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

function cancelFloorDets() {
	gridOptionsFloorDets.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	$(".formValidation").remove();
	$("#floorDetsForm").hide();
	$("#addFloorDets").show();
	$("#editFloorDets").show();
	$("#deleteFloorDets").show();
	$("#myGridFloorDets").show();
	$("#saveFloorDets").hide();
	$("#cancelFloorDets").hide();
}
function editFloorDetails() {
	$("#myGridFloorDets").hide();
	$("#floorDetsForm").show();
	$("#addFloorDets").hide();
	$("#editFloorDets").hide();
	$("#deleteFloorDets").hide();
	$("#saveFloorDets").show();
	$("#cancelFloorDets").show();
}
function addFloorDets() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$(".formValidation").remove();
	editFloorDetails();
	$("#detailId").html("");
	$("#floorTypeId").val("");
	$("#type").val("");
	$("#quantity").val("");
	$("#height").val("");
	$("#width").val("");
	$("#length").val("");
}
function saveFloorDets() {
	data = {};
	var valid = true;
	var selectedRows = gridOptionsFloor.api.getSelectedRows();
	var floorId = selectedRows[0].floorId;
	data.detailId = $("#floorTypeId").val();
	data.floorId = floorId;
	data.type = $("#type").val();
	data.quantity = $("#quantity").val();
	data.height = $("#height").val();
	data.width = $("#width").val();
	data.length = $("#length").val();

	if (data.type == null || data.type == "") {
		toastr.error('Type is required');
		return;
	}
	if (data.quantity == null || data.quantity == "") {
		toastr.error('Quantity is required');
		return;
	}
	if (data.height == null || data.height == "") {
		toastr.error('Height is required');
		return;
	}
	if (data.width == null || data.width == "") {
		toastr.error('Width is required');
		return;
	}
	if (data.length == null || data.length == "") {
		toastr.error('Length is required');
		return;
	}
	if (valid) {
		$('.loader').show();
		$("body").addClass("overlay");
		submitFloorDets(data, data.detailId);
	}
}
function submitFloorDets(dataset, id) {
	console.log("floor details DATA:::", dataset)
	$.ajax({
		type: "POST",
		url: "floor-management-save-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				if (id) {
					toastr.success("Floor Details Updated Successfully");
				} else {
					toastr.success("Floor Details Added Successfully");
				}
				viewFloorDetss();
				cancelFloorDets();
			} else {
				$('.loader').hide();
				toastr.success("Something Went Wrong");
			}
		},
		error: function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})
}

function viewFloorDetss() {
	var selectedRows = gridOptionsFloor.api.getSelectedRows();
	var id = selectedRows[0].floorId;
	$("#floorId").html(id);
	agGrid.simpleHttpRequest({
		url: 'floor-management-view-floor-details?id=' + id,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.floorDetails;
		console.log("allData  floor details=====", allData)
		if (allData != null) {
			gridOptionsFloorDets.api.setRowData(allData);
			onSelectionChangedFloorDets();
			/*var firstRowNode = gridOptionsRoom.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}*/
		} else {
			$('#totalReq').find('span').html("0");
			gridOptionsFloorDets.api.setRowData("");
		}

	});
}

function editFloorDets() {
	addFloorDets();
	var selectedRows1 = gridOptionsFloorDets.api.getSelectedRows();
	id = selectedRows1[0].detsId;
	var selectedRow = "";
	gridOptionsFloorDets.api.forEachNode(function(rowNode, index) {
		if (rowNode.data.detsId == id) {
			selectedRow = rowNode;
		}
	});
	console.log("selectedRow", selectedRow)
	$("#detailId").html(selectedRow.data.detsId);
	$("#floorTypeId").val(selectedRow.data.detsId);
	$("#type").val(selectedRow.data.type);
	$("#quantity").val(selectedRow.data.quantity);
	$("#height").val(selectedRow.data.height);
	$("#width").val(selectedRow.data.width);
	$("#length").val(selectedRow.data.length);
}
function deleteFloorDets() {
	$('#deleteFloorDetsModal').modal('show');
}
function deleteFloorDetsOnClick() {
	var selectedRows = gridOptionsFloorDets.api.getSelectedRows();
	var id = selectedRows[0].detsId;
	if (id) {
		$.ajax({
			type: "POST",
			url: "floor-management-delete-floor-detail?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					viewFloorDetss();
					cancelModalBtn();
					onSelectionChangedFloor();
					toastr.success("Floor Details Deleted Successfully");
					$('#deleteOwnerModal').modal('hide');

				} else {
					$('.loader').hide();
					toastr.success("Something went to wrong!");
					$('#deleteOwnerModal').modal('hide');
				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
		$('.loader').hide();
	}
} function validatePositiveNumber(input) {
	let value = input.value;

	// Remove all characters except digits and a single dot
	value = value.replace(/[^0-9.]/g, '');

	// Only allow one decimal point
	const parts = value.split('.');
	if (parts.length > 2) {
		value = parts[0] + '.' + parts[1]; // Ignore extra dots
	}

	// Remove leading zeroes unless it's "0." format
	if (value !== '' && !value.startsWith('0.')) {
		value = value.replace(/^0+/, '');
	}

	input.value = value;
}