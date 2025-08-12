var mainData = {
	"contracts": [
		{
			"id": "6b52",
			"templateId": "OMC/2024/CM/001",
			"templateName": "Template A",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description A\n"

		},
		{
			"id": "7c83",
			"templateId": "OMC/2024/CM/002",
			"templateName": "Template B",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description B\n"

		},
		{
			"id": "8d94",
			"templateId": "OMC/2024/CM/003",
			"templateName": "Template C",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description C\n"
		},
		{
			"id": "9e05",
			"templateId": "OMC/2024/CM/004",
			"templateName": "Template D",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description D\n"
		},
		{
			"id": "af16",
			"templateId": "OMC/2024/CM/005",
			"templateName": "Template E",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description E\n"
		},
		{
			"id": "b027",
			"templateId": "OMC/2024/CM/006",
			"templateName": "Template F",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description F\n"
		},
		{
			"id": "c138",
			"templateId": "OMC/2024/CM/007",
			"templateName": "Template G",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description G\n"
		},
		{
			"id": "d249",
			"templateId": "OMC/2024/CM/008",
			"templateName": "Template H",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description H\n"
		},
		{
			"id": "e350",
			"templateId": "OMC/2024/CM/009",
			"templateName": "Template I",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description I\n"
		},
		{
			"id": "f461",
			"templateId": "OMC/2024/CM/010",
			"templateName": "Template J",
			"creationDate": "03-09-2024",
			"status": "",
			"remark": "",
			"renewalDate": "",
			"frequency": "",
			"templateDescription": "Description J\n"
		}
	]
}

$(document).ready(function() {
	var gridDiv = document.querySelector('#complianceRiskGrid');
	new agGrid.Grid(gridDiv, contractNegotationGridOptions);

	contractNegotationGridOptions.api.setRowData(mainData.contracts);

	var rowData = [];
	contractNegotationGridOptions.api.setRowData(rowData);
	contractNegotationGridOptions.api.setRowData(mainData.contracts);
	if (mainData.contracts && mainData.contracts.length > 0) {
		contractNegotationGridOptions.api.forEachNode(function(node) {
			if (node.rowIndex === 0) {
				node.setSelected(true); // Select the first row
			}
		});
	}
	updateStatus('closureStatus', 'Completed');
});

var negotiationGridDefs = [
	{
		headerName: 'Contracts',

		children: [
			{
				headerCheckboxSelection: false,
				headerCheckboxSelectionFilteredOnly: true,
				checkboxSelection: true,
				sortable: false,
				filter: false,
				resizable: true,
				width: 20
			},

			{
				headerName: "Contract Id",
				field: "templateId",
				width: 200
			},
			{
				headerName: "Contract Name",
				field: "templateName",
				width: 450
			},
			{
				headerName: "Description",
				field: "templateDescription",
				width: 430
			},
			{
				headerName: "Created Date",
				field: "creationDate",
				width: 200
			}, {
				headerName: "status",
				field: "status",
				width: 330
			}, {
				headerName: "Remark",
				field: "remark",
				width: 200
			},
			{
				headerName: "Date",
				field: "date",
				width: 150
			},
			{
				headerName: "Renewal Date",
				field: "renewalDate",
				width: 150
			},
			{
				headerName: "Frequency",
				field: "frequency",
				width: 150
			}
		]
	}];

var contractNegotationGridOptions = {
	columnDefs: negotiationGridDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},
	onSelectionChanged: onSelectionChangedContract,

};

function onSelectionChangedContract() {
	var selectedRows = contractNegotationGridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	const complianceSection = document.getElementById('complianceSection');
	if (rowCount > 0) {
		var selectedNodes = contractNegotationGridOptions.api.getSelectedNodes();
		
		console.log("selectednode -->" , selectedRows);
		var contractId = selectedRows[0].templateId;
		$("#headercontractid").text(contractId);
		var selectedData = selectedNodes.map(node => node.data);
		var templateId = selectedData.map(node => node.templateId);
		let contrData = mainData.contracts.filter(data => data.templateId == templateId);
	} else {
		$("#headercontractid").text('');
	}
}

var selectedStatusField;
var selectedStatusValue;

function updateStatus(statusField, statusValue) {
	var selectedRows = contractNegotationGridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		// Store the selected field and value for use in confirmation
		selectedStatusField = statusField;
		selectedStatusValue = statusValue;

		// Update the modal text with the new status
		document.getElementById('newStatusLabel').innerText = statusValue;

		// Show or hide modal sections based on status
		if (statusField === 'closureStatus' || statusField === 'terminationStatus') {
			document.getElementById('remarkSection').style.display = 'block';
			document.getElementById('renewalSection').style.display = 'none';
		} else if (statusField === 'renewalStatus') {
			document.getElementById('remarkSection').style.display = 'block';
			document.getElementById('renewalSection').style.display = 'block';
		}

		// Show the modal
		$('#statusChangeModal').modal('show');
		$("#remarkText").val("");
	} else {
		alert("Please select a contract.");
	}
}




$(document).ready(function() {


	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
	document.getElementById('confirmStatusChange').addEventListener('click', function() {
		var selectedRows = contractNegotationGridOptions.api.getSelectedRows();
		console.log("Selected Rows Before Update:", selectedRows);

		selectedRows.forEach(function(row) {
			console.log("Row Before Update:", row);

			// Update the status field with the selected value
			row[selectedStatusField] = selectedStatusValue;
			var currentDate = getCurrentDate();

			// Add additional fields based on the status
			if (selectedStatusField === 'closureStatus' || selectedStatusField === 'terminationStatus') {
				row['remark'] = document.getElementById('remarkText').value;
				row['status'] = selectedStatusValue;
				row['date'] = currentDate;
			} else if (selectedStatusField === 'renewalStatus') {
				row['renewalDate'] = document.getElementById('renewalDate').value;
				row['frequency'] = document.getElementById('renewalFrequency').value;
				row['remark'] = document.getElementById('remarkText').value;
				row['status'] = selectedStatusValue;
			}

			console.log("Row After Update:", row);

			// Update the main data source
			var dataIndex = mainData.contracts.findIndex(contract => contract.id === row.id);
			if (dataIndex !== -1) {
				mainData.contracts[dataIndex] = { ...mainData.contracts[dataIndex], ...row };
			}

			// Refresh the grid
			//	contractNegotationGridOptions.api.setRowData(mainData.contracts);
			var rowData = [];
			contractNegotationGridOptions.api.setRowData(rowData);
			contractNegotationGridOptions.api.setRowData(mainData.contracts);
			if (mainData.contracts && mainData.contracts.length > 0) {
				contractNegotationGridOptions.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true); // Select the first row
					}
				});
			}
		});

		// Close the modal
		$('#statusChangeModal').modal('hide');
	});



});



function getCurrentDate() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth() + 1; // Months are zero-based
	var year = today.getFullYear();

	// Pad single digits with a leading zero
	day = day < 10 ? '0' + day : day;
	month = month < 10 ? '0' + month : month;

	return day + '-' + month + '-' + year;
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	console.log("Element-->", tabElement);
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		console.log("tabTrigger-->", tabTrigger);
		tabTrigger.show();
	}
}

function resetBtn() {
	$("#quickFilter").val('');
	contractNegotationGridOptions.api.setQuickFilter('');
	contractNegotationGridOptions.api.refreshCells({ force: true });
}
function onQuickFilterChanged(event) {
	contractNegotationGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}
