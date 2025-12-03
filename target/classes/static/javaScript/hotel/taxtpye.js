$(document).ready(function() {
	var GridDiv = document.querySelector('#myGridBuild');
	new agGrid.Grid(GridDiv, gridOptionsBuild);
	viewBuilding();

	var secGridDiv = document.querySelector('#mysecGridBuild');
	new agGrid.Grid(secGridDiv, secgridOptionsBuild);
	getAllTaxDetails();
});


function showFullDetails() {
	document.getElementById("mysecGridContainer").style.display = "none";
	const detailsSection = document.getElementById("hiddenDetails");
	detailsSection.style.display = "block";
	const inputs = detailsSection.querySelectorAll("input, textarea, select");
	inputs.forEach(input => {
		if (input.type === "checkbox" || input.type === "radio") {
			input.checked = false;
		} else {
			input.value = "";
		}
	});
}

function prevStep() {
	document.getElementById("step2").style.display = "none";
	document.getElementById("step1").style.display = "block";
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`#${tablink}`);
	console.log(tabElement)

	if (tabElement) {

		tabElement.click();
	}
}
function addServiceDetails() {
	document.getElementById("mysecGridBuild").style.display = "none";
	const detailsSection = document.getElementById("hiddenDetails");
	detailsSection.style.display = "block";
	const inputs = detailsSection.querySelectorAll("input, textarea, select");
	inputs.forEach(input => {
		if (input.type === "checkbox" || input.type === "radio") {
			input.checked = false;
		} else {
			input.value = "";
		}
	});

	$("#addServiceDetails").addClass("d-none");
	$("#cancelTaxBtn").removeClass("d-none");
	$("#editServicesBtn").addClass("d-none");
	$("#saveTaxType").removeClass("d-none");
}

function cancelService() {
	$("#addServiceDetails").removeClass("d-none");
	$("#cancelTaxBtn").addClass("d-none");
	$("#saveTaxType").addClass("d-none");
	$("#hiddenDetails").hide();
	$("#mysecGridBuild").show();
	$("#editServicesBtn").removeClass("d-none");
}


function prevBtnFunction(tablink) {
	const tabElement = document.querySelector(`#${tablink}`);
	console.log(tabElement)

	if (tabElement) {
		tabElement.click();
	}
}
var columnDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	pinned: 'left',
	resizable: true
},
{
	headerName: "PropertyID",
	field: "locationId",

},
{
	headerName: "Code",
	field: "locationCode",
	hide: true,
	cellRenderer: function(params) {
		return 'Location';
	}
}, {
	headerName: "Name",
	field: "locationName",
	pinned: 'left',
}, {
	headerName: "Property",
	field: "locationType"
}, {
	headerName: "Address",
	field: "locStreet"
}, {
	headerName: "City",
	field: "locCity"
}, {
	headerName: "Ownership",
	field: "locOwnership"
}, {
	headerName: "State",
	field: "locState"
}, {
	headerName: "Country",
	field: "locCountry"
}, {
	headerName: "Height",
	field: "locHeight"
}, {
	headerName: "Width",
	field: "locWidth"
}, {
	headerName: "Length",
	field: "locLength"
}, {
	headerName: "Status",
	field: "locStatus",
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "Create Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'center'
	}
}
];
var gridOptionsBuild = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: onSelectionChanged,
	paginationAutoPageSize: true,
	pagination: true,
	getRowNodeId: function(data) {
		return data.locationId;
	}
};
var locSel = '';
function onSelectionChanged() {
	var selectedNodes = gridOptionsBuild.api.getSelectedNodes();
	if (selectedNodes.length > 0) {
		const locationId = selectedNodes[0].data.locationId;
		getAllTaxDetails(locationId);
	}
}


var seccolumnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		pinned: 'left',
		resizable: true
	},
	//{ headerName: "Tax Type ID", field: "taxtypeId" },
	{ headerName: "Service Name", field: "service_Name",width: 325, },
	{ headerName: "Tax %", field: "tax", sortable: true },
	{ headerName: "HSN Code", field: "hSN_Code" }

];

var secgridOptionsBuild = {
	columnDefs: seccolumnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	pagination: true,
	paginationPageSize: 15,

};
function onSecGridSelectionChanged() {
	var selectedNodes = secgridOptionsBuild.api.getSelectedNodes();
	if (selectedNodes.length > 0) {
		const selectedData = selectedNodes[0].data;
		populateFormFields(selectedData);
	}
}
function populateFormFields(data) {
	$('#service_Name').val(data.service_Name);
	$('#tax').val(data.tax);
	$('#hSN_Code').val(data.hSN_Code);

}
secgridOptionsBuild.onSelectionChanged = function() {
	const selectedNodes = secgridOptionsBuild.api.getSelectedNodes();
	if (selectedNodes.length > 0) {
		$('#editServiceBtn').prop('disabled', false); 
	} else {
		$('#editServiceBtn').prop('disabled', true);
	}
};


function viewBuilding(id = null, tab = null) {
	agGrid.simpleHttpRequest({
		url: 'hotel-management-view'
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.Property;
		if (allData != null) {
			gridOptionsBuild.api.setRowData(allData);
			if (id != null) {
				locSel = id;
				gridOptionsBuild.api.forEachNode(function(node) {
					if (String(node.data.locationId) === String(id)) {
						node.setSelected(true);
					}
				});
			} else {
				console.log("else")
				locSel = '';
				var firstRowNode = gridOptionsBuild.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
			}
		} else {
			$('#totalReq').find('span').html("0");
			gridOptionsBuild.api.setRowData("");
		}
	});

}

//edit function call and row select 


function onCheckboxClick() {
	var selectedNodes = gridOptionsBuild.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const locationId = selectedData.map(node => node.locationId);
	var selectedRows = gridOptionsBuild.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].locationId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {

	} else {
	}
}
function onEditServiceButtonClick() {
	const selectedNodes = secgridOptionsBuild.api.getSelectedNodes();
	if (selectedNodes.length > 0) {
		const selectedData = selectedNodes[0].data;
		const taxtypeId = selectedData.taxtypeId;

		if (taxtypeId) {
			editTaxData(taxtypeId);

			document.getElementById("mysecGridBuild").style.display = "none";
			document.getElementById("hiddenDetails").style.display = "block";

			$("#addServiceDetails").addClass("d-none");
			$("#cancelTaxBtn").removeClass("d-none");
			$("#editServicesBtn").addClass("d-none");
			$("#saveTaxType").removeClass("d-none");
		}
	} else {
		toastr.error("Please select a row to edit.");
	}
}

//edit


function editTaxData(taxtypeId) {

	agGrid.simpleHttpRequest({

		url: "taxtype-details-edit?taxtypeId=" + taxtypeId,
	}).then(function(response) {
		if (response.code === "Success") {
			console.log(response.body[0]);
			const responseBody = JSON.parse(response.body[0]);

			$("#locationId").val(responseBody[0].locationId);
			$("#taxtypeId").val(responseBody[0].taxtypeId);
			$("#service_Name").val(responseBody[0].service_Name);
			$("#tax").val(responseBody[0].tax);
			$("#hSN_Code").val(responseBody[0].hSN_Code)

		} else {
			console.log("Failed to fetch data");
		}
	});

}



function candidateTaxDtls() {
	var selectedNodes = gridOptionsBuild.api.getSelectedNodes();
	if (selectedNodes.length === 0) {
		toastr.error("Please select a building from the list.");
		return;
	}

	var selectedData = selectedNodes[0].data;
	var locationId = selectedData.locationId;
	let taxData = {};

	taxData['taxtypeId'] = $("#taxtypeId").val();

	taxData['locationId'] = locationId;
	taxData['service_Name'] = $("#service_Name").val();
	taxData['tax'] = $("#tax").val();
	taxData['hSN_Code'] = $("#hSN_Code").val();
	if ($("#service_Name").val() == "" || $("#service_Name").val() == null) {
		toastr.error("Service_Name is Required");
		return;
	}
	if ($("#tax").val() == "" || $("#tax").val() == null) {
		toastr.error("Tax% is Required");
		return;
	} if ($("#hSN_Code").val() == "" || $("#hSN_Code").val() == null) {
		toastr.error("HSN_Code is Required");
		return;
	}


	saveTaxData(taxData);
	document.getElementById("mysecGridContainer").style.display = "block";
	document.getElementById("hiddenDetails").style.display = "none";

}

function saveTaxData(studentData) {
	$.ajax({
		type: "POST",
		url: "save-taxtype-details",
		contentType: "application/json",
		data: JSON.stringify(studentData),
		success: function(response) {
			if (response.code === "Success") {
				$('.loader').hide();
				toastr.success(response.message);
				cancelService();
				getAllTaxDetails();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
		},
		error: function(datas) {
			$('.loader').hide();
			toastr.error("Something went wrong. Please try again.");
		}
	});
}

function getAllTaxDetails() {
	agGrid.simpleHttpRequest({
		url: "property-taxtype-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.taxtypeDetails || [];
		secgridOptionsBuild.api.setRowData(allData);
	});

}

