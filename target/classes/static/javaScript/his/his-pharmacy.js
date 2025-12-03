let activeTabId = '';
$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	getAllPatientsDetails();
	
	var gridDiv = document.querySelector('#treatmentGrid');
	new agGrid.Grid(gridDiv, gridOptionsTreatment);
        $("#editBtn").hide();
		$("#saveBtn").hide();
		$("#dltBtn").hide();
		activeTabId = 'treatment';
		// Handle Add button click
		$("#addBtn").on("click", function() {
			if (activeTabId == 'treatment') {
				alert(activeTabId)
				$("#addTreatment").show();
				$("#addBtn").hide();
				clearVitalField();
				
				tratmentId = "";
			}
		});
	
})


// ag grid

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
		headerName: "ID",
		field: "patientId",
		flex: 1,
	},
	{
		headerName: "Name",
		field: "pName",
		flex: 1,

	},
	{
		headerName: "Address",
		field: "address",
		flex: 1,

	},
	{
		headerName: "Mobile",
		field: "mobNo",
		flex: 1,

	},
	{
		headerName: "Date of Birth",
		field: "dateOfBirth",
		flex: 1,

	},
	{
		headerName: "Gender",
		field: "gender",
		flex: 1,

	},
	{
		headerName: "Status",
		field: "patientStatus",
		flex: 1,
	},
];

// Define grid options
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {

		// Fetch data on grid ready
		getAllPatientsDetails();
	},
	onSelectionChanged: rowSelect
};


var columnDefs1 = [
		{
			headerCheckboxSelection: true,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 40,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Treatment Id",
			field: "treatmentId",
			hidden: true,
			cellRenderer: function(params) {
				return '<a onclick="editTreatmentDetails(\'' + params.data.treatmentId + '\')" href="javascript:void(0)">'
					+ params.data.treatmentId + '</a>';
			}
		},
		{
			headerName: "Medicine Name",
			field: "medName",
		},
		{
			headerName: "Dosage",
			field: "dosage",
		},
		{
			headerName: "Frequency",
			field: "frequency"
		},
		{
			headerName: "Duration",
			field: "duration"
		},
		{
			headerName: "Instruction",
			field: "instruction",
		}, {
			headerName: "Type",
			field: "type",
			cellRenderer: function(params) {
				const typeMapping = {
					"0": "Good",
					"1": "Normal",
					"2": "Serious"
				};
				return typeMapping[params.value] || "";
			}
		}];
	
	// Define grid options
	var gridOptionsTreatment = {
		columnDefs: columnDefs1,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 120
		},
		pagination: true,
		paginationPageSize: 15,
	
		onGridReady: function(params) {
			console.log("Grid API initialized:", params.api);
			window.gridApi1 = params.api;
		},
	
	
		onSelectionChanged: rowSelect1
	};
	
	
var patientId = '';
var pName = '';
	function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	console.log(selectedRows);
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = 0;
	selectedData.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	for (var i = 0; i < selectedRows.length; i++) {
		patientId = patientId + selectedRows[i].patientId;
		pName = pName + selectedRows[i].pName;
	}


	$("#pName").text(pName);
	$("#pID").text(patientId);


	if (rowCount > 0) {
		
	}
	else {

	}

	patientId = '';
	pName = '';
	customerId = '';

}
function rowSelect1() {
		var selectedRows = window.gridApi1.getSelectedRows();
		if (selectedRows.length > 0) {
	
			$("#addBtn").hide();
			$("#dltBtn").show();
			$("#addTreatment").hide();
		} else {
			$("#addTreatment").show();
			$("#dltBtn").hide();
		}
	}
function getAllPatientsDetails() {

	agGrid.simpleHttpRequest({
		url: "pharmacy-his-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.patientDetails;

		gridOptions.api.setRowData(allData);

		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected

		}

	});
	
}



	
	function getActId() {
		//$("#addBtn").show();
		$("#addTreatment").show();
		
		$("#saveBtn").hide();
		$("#editBtn").hide();
		$("#dltBtn").hide();
		$("#addBtn").hide();
		
		clearVitalField();
	
		const activeTabHref = $(event.target).attr("href");
	
		const activeTabId = activeTabHref.replace("#", "");
	   alert($("#patientId").val());
	
		getVitalDetails(activeTabId, selectedOpdIds);
	}
	
	
	function clearVitalField() {
		$(".formValidation").remove();
		
		$("#treatmentIds").val('');
		$("#medicinename").val('');
		$("#dosage").val('');
		$("#frequency").val('');
		$("#duration").val('');
		$("#instruction").val('');
		$("#treatmentTypes").val('');
	
	}

function getVitalDetails(activeTabId, selectedOpdIds) {
	alert(activeTabId)
	alert(selectedOpdIds)
		agGrid.simpleHttpRequest({
			url: "pharmacy-his-view-treatment?types=" + activeTabId + "&opdId=" + selectedOpdIds
		}).then(function(response) {
			if (response.code === "Success") {
				
				const treatmentDetails = responseBody.treatmentDetails || null;
				
					
	
						$("#saveBtn").show();
						clearVitalField();
						
						$("#addBtn").hide();
						$("#editBtn").hide();
				
				// Generalized handling for different details
				const detailHandlers = [
					{ details: treatmentDetails, gridApi: window.gridApi1 }
				];
	
				detailHandlers.forEach(({ details, gridApi }) => {
					if (details && details.length > 0) {
						gridApi.setRowData(details);
					} else {
						gridApi.setRowData([]);
					}
				});
				
			}
		});
	}