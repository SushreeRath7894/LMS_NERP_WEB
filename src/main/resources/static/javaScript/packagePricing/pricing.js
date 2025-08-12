$(document).ready(function() {

	$("#service,#function").select2({
		placeholder: "Select ",
		allowClear: true
	});

	$('#packageName,#startDate,#endDate,#service,#function,#desc').prop('disabled', true);

	var gridDiv = document.querySelector('#myPackageGrid');
	new agGrid.Grid(gridDiv, packageGridOptions);
	viewPackageData();
})

//Package Column Definitions
var packageColumnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 10,
		suppressSizeToFit: true,
		pinned: 'left'
	},
	{
		headerName: "Package Id",
		field: "packageId",
		width: 120,
		pinned: 'left'
	},
	{
		headerName: "Package Name",
		field: "packageName",
		width: 120,
		pinned: 'left'
	},
	{
		headerName: "Start Date",
		field: "startDate",
		width: 120
	},
	{
		headerName: "End Date",
		field: "endDate",
		width: 100
	},
	{
		headerName: "Services",
		field: "services",
		width: 120
	},
	{
		headerName: "Functions",
		field: "function",
		width: 170
	},
	{
		headerName: "Description",
		field: "desc",
		width: 100
	}
];

//Package GridOption Here --------------->>>>>>>>>>>>>>>
var packageGridOptions = {
	columnDefs: packageColumnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectOperation
};

function rowSelectOperation() {
	var selectedRows = packageGridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const allPackageData = selectedRows[0];
		console.log("All Package Data :::->", allPackageData);

		$("#packageId").val(allPackageData.packageId);
		$("#packageName").val(allPackageData.packageName);
		
		let rawString = allPackageData.services; 
		let parsedArray = JSON.parse(rawString); 
		$("#service").val(parsedArray).trigger("change");

		$("#function").val(allPackageData.function).trigger("change");
		$("#startDate").val(allPackageData.startDate);
		$("#endDate").val(allPackageData.endDate);
		$("#desc").val(allPackageData.desc);
	}
	else
	{
		$("#packageId").val("");
		$("#packageName").val("");
		$("#startDate").val("");
		$("#endDate").val("");
		$("#service").val([]).trigger("change");
		$("#function").val([]).trigger("change");
		$("#desc").val("");
	}
}



function addPackageData() {
	
	packageGridOptions.api.deselectAll();
	$('#packageId,#packageName,#startDate,#endDate,#service,#function,#desc').prop('disabled', false);
	clearFields();
	$("#savePackageData").removeClass('d-none');
	$("#addPackageData").addClass('d-none');
	$("#deletePackageData").addClass('d-none');
	$("#editPackageData").addClass('d-none');
	$("#cancelPackageData").removeClass('d-none');
}

function editPackageData() {

	$('#packageName,#startDate,#endDate,#service,#function,#desc').prop('disabled', false);

	$("#photoLabel").removeClass('disabled-label');
	$("#editPackageData").addClass('d-none');
	$("#cancelPackageData").removeClass('d-none');
	$("#deletePackageData").addClass('d-none');
	$("#savePackageData").removeClass('d-none');
	$("#addPackageData").addClass('d-none');
}

function cancelPackageData() {
	
	viewPackageData();
	$('#packageName,#startDate,#endDate,#service,#function,#desc').prop('disabled', true);

	$("#savePackageData").addClass('d-none');
	$("#addPackageData").removeClass('d-none');
	$("#deletePackageData").removeClass('d-none');
	$("#editPackageData").removeClass('d-none');
	$("#cancelPackageData").addClass('d-none');
}


function clearFields() {
	$("#packageName,#startDate,#endDate,#desc").val("");
	$("#service,#function").val([]).trigger("change");
}


//Save Package Data ----->>>>>>>>>>>
function savePackageData() {
	let packageData = {};
	packageData.packageId = $("#packageId").val();
	packageData.packageName = $("#packageName").val();
	packageData.startDate = $("#startDate").val();
	packageData.endDate = $("#endDate").val();
	packageData.service = $("#service").val();
	packageData.function = $("#function").val();
	packageData.desc = $("#desc").val();

	if ($("#packageName").val() == "" || $("#packageName").val() == null) {
		toastr.error("PackageName is Required");
		return;
	}
	else if ($("#startDate").val() == "" || $("#startDate").val() == null) {
		toastr.error("StartDate is Required");
		return;
	}
	if ($("#endDate").val() == "" || $("#endDate").val() == null) {
		toastr.error("EndDate is Required");
		return;
	}
	else if ($("#service").val() == "" || $("#service").val() == null) {
		toastr.error("Service is Required");
		return;
	}
	if ($("#function").val() == "" || $("#function").val() == null) {
		toastr.error("Function is Required");
		return;
	}
	else if ($("#desc").val() == "" || $("#desc").val() == null) {
		toastr.error("Description is Required");
		return;
	}
	else {

		savePackageDataAPI(packageData);
	}

}


//Save Package Data(API) ----->>>>>>>>>>>
function savePackageDataAPI(packageData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-packageconfig-details",
		contentType: "application/json",
		data: JSON.stringify(packageData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				toastr.success(response.message);
				viewPackageData();
				cancelPackageData();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}



//View Package Data(API)
function viewPackageData() {
	
	agGrid.simpleHttpRequest({
		url: "packageData-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.packageDetails;
		packageGridOptions.api.setRowData(allData);

		var firstRowNode = packageGridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}

	});
}


//Delete Package Data --------------->>>>>>>>>>>>>>>>>>>>>
function deletePackageData() {
	var selectedRows = packageGridOptions.api.getSelectedRows();
	var id = selectedRows[0].packageId;
	agGrid.simpleHttpRequest({
		url: "packageData-delete?id=" + id,
	}).then(function(response) {
		if (response.code == "success") {
			toastr.success("Package Delete Successfully");
			viewPackageData();
		}

	});
}