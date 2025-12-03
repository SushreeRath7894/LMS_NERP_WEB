$(document).ready(function() {
	var gridDivSelected = document.querySelector('#myGrid');
	new agGrid.Grid(gridDivSelected, gridOptionsSelected);

	gridOptionsSelected.api.setRowData([]);
	getWorkflowList();

});

var columnDefsSelected = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "BPM ID",
	field: "bpmId",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.bpmId + '</a>';
	},
}, {
	headerName: "BPM Name",
	field: "bpmName",
	width: 660,
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.bpmName + '</a>';
	},
}
];

var gridOptionsSelected = {
	columnDefs: columnDefsSelected,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
};

function getWorkflowList() {
	var bpmData = [
		{ "bpmId": "BPM001", "bpmName": "Recruitment" },
		{ "bpmId": "BPM002", "bpmName": "HRMS" },
		{ "bpmId": "BPM003", "bpmName": "CRM" },
		{ "bpmId": "BPM004", "bpmName": "Sales" },
		{ "bpmId": "BPM005", "bpmName": "Order Management System" },
		{ "bpmId": "BPM006", "bpmName": "Procurement" },
		{ "bpmId": "BPM007", "bpmName": "Inventory" },
		{ "bpmId": "BPM008", "bpmName": "Finance & Accounting" },
		{ "bpmId": "BPM009", "bpmName": "Supply Chain Management" },
		{ "bpmId": "BPM010", "bpmName": "Manufacturing & Production" },
		{ "bpmId": "BPM011", "bpmName": "Customer Relationship Management" },
		{ "bpmId": "BPM012", "bpmName": "Project Management" },
		{ "bpmId": "BPM013", "bpmName": "E-commerce" },
		{ "bpmId": "BPM014", "bpmName": "Marketing Automation" },
		{ "bpmId": "BPM015", "bpmName": "Document Management" }
	];


	gridOptionsSelected.api.setRowData(bpmData);
	if (bpmData.length > 0) {
		gridOptionsSelected.api.forEachNode(function(node) {
			if (node.rowIndex === 0) {
				node.setSelected(true);
			}
		});
	}
}

function saveDiagramData(data) {
	var obj = {};
	obj.workflowData = data;

	$.ajax({
		type: "POST",
		url: "save-workflow-process",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.message == "Success") {
			}
		}, error: function(data) {

		}
	});

}
