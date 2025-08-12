$(document).ready(function() {
		var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData([]);

	getAllPatientsDetails();

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
   
    $(".br-s-btn-tx").hide();
});

var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "IPD ID",
		field: "bookingId",
	},
	{
		headerName: "Patient ID",
		field: "patientId",
	},
	{
		headerName: "Patient Name",
		field: "pName"
	},
	{
		headerName: "Age",
		field: "age"
	},
	{
		headerName: "Mobile",
		field: "mobNo"
	}, {
        headerName: "Payment Status",
        field: "pay_status",
        flex: 1,
        cellRenderer: function(params) {
            if (params.data.pay_status == "Fully Paid") {
                return '<a style="color:blue;font-weight: bold;">Fully Paid</a>';
            } else if (params.data.pay_status == "Not Paid") {
                return '<a  style="color:red;font-weight: bold;">Not Paid</a>';
            } else if (params.data.pay_status == "Partial Paid") {
                return '<a style="color:black;font-weight: bold;">Partial Paid</a>';
            }
        }
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
		flex: 1
	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {
		params.api.sizeColumnsToFit();
		window.gridApi = params.api;
		window.gridOptions = params.api;
		getAllPatientsDetails();
	},
	onSelectionChanged: rowSelect
};

let patientId="";
function rowSelect(event) {
	var selectedRows = event.api.getSelectedRows(); // safer access
	console.log(selectedRows);
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = selectedData.length;

	if (selectedRows.length > 0) {
		patientId = selectedRows[0].patientId;
		editIpdDetails(patientId);
	} else {
		$("#patientName").val('');
		$("#age").val('');
		$("#gender").val('');
		$("#mobNo").val('');
		$("#email").val('');
		$("#address").val('');
		$("#wordNo").val('');
		$("#bedNo").val('');
		$("#roomNo").val('');
		$("#consuDoctor").val('');
		$("#department").val('');
		$("#admitDate").val('');
	}
}
function getAllPatientsDetails() {
	var fromdate = 1234;
	var todate = 1234;

	agGrid.simpleHttpRequest({
		url: "ipd-billing-manage-view?fromdate=" + fromdate + "&todate=" + todate
	}).then(function(response) {
		if (response.message === "Success") {

			const responseBody = JSON.parse(response.body);
			const patientData = responseBody.IPDRecords;

			window.gridApi.setRowData(patientData);

			if (patientData && patientData.length > 0) {
				window.gridApi.forEachNode(function(node) {
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
function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

/*function resetBtn() {
	$("#quickFilter").val('');
	dischargeGridOption.api.setQuickFilter('');
	dischargeGridOption.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (dischargeGridOption.api) {
			dischargeGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function nextTab(id) {
    const tabElement = document.querySelector('#' + id + ' a');
    const tab = new bootstrap.Tab(tabElement);
    tab.show();
}*/

function editIpdDetails(patientId) {
	$.ajax({
		type: "GET",
		url: "ipd-billing-details-edit?Id=" + patientId,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const ipdDetails = JSON.parse(response.body).ipdDetails;

				console.log(ipdDetails, 'opd');

				if (ipdDetails == null) {
					$("#patientName").val('');
					$("#age").val('');
					$("#gender").val('');
					$("#mobNo").val('');
					$("#email").val('');
					$("#address").val('');
					/*$("#wordNo").val('');
					$("#bedNo").val('');
					$("#roomNo").val('');*/
					$("#consuDoctor").val('');
					$("#department").val('');
					$("#admitDate").val('');
					
				} else {
					$("#patientName").val(ipdDetails[0].patientName);
					$("#age").val(ipdDetails[0].age);
					$("#gender").val(ipdDetails[0].gender);
					$("#mobNo").val(ipdDetails[0].mobNo);
					$("#email").val(ipdDetails[0].email);
					$("#address").val(ipdDetails[0].address);
					/*$("#wordNo").val(opdDetails[0].wordNo);
					$("#bedNo").val(opdDetails[0].bedNo);
					$("#roomNo").val(opdDetails[0].roomNo);*/
					$("#consuDoctor").val(ipdDetails[0].consuDoctor);
					$("#department").val(ipdDetails[0].department);
					$("#admitDate").val(ipdDetails[0].admitDate);

				}


			}

		},
		error: function(error) {
			console.error("Error fetching patient details:", error);
		}
	});
}