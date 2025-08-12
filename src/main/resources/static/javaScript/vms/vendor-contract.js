var columnDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 40,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Contract ID",
		field: "contractId",
		pinned: 'left',
		width: 120
	},
	{
		headerName: "Contract Name",
		field: "contractName",
		pinned: 'left',
		width: 200
	},
	{
		headerName: "Vendor Name",
		field: "vendorName",
	},
	{
		headerName: "Contract Type",
		field: "contractType",
	},
	{
		headerName: "Start Date",
		field: "startDate",
	},
	{
		headerName: "End Date",
		field: "endDate",
	},
	{
		headerName: "Contract Amount",
		field: "amount",
		valueFormatter: params => `$${params.value.toLocaleString()}`
	},
	{
		headerName: "Status",
		field: "status",
	},
	{
		headerName: "Created By",
		field: "createdBy",
	},
	{
		headerName: "Created On",
		field: "createdOn",
	},
];

var rowData = [
	{ contractId: "C001", contractName: "Annual IT Services Agreement", vendorName: "ABC Pvt Ltd", startDate: "2024-01-10", endDate: "2025-01-09", amount: 120000, status: "Active", contractType: "Tender", createdBy: "John Doe", createdOn: "2023-12-15" },
	{ contractId: "C002", contractName: "Software License Renewal", vendorName: "XYZ Solutions", startDate: "2023-07-01", endDate: "2024-06-30", amount: 95000, status: "Expired", contractType: "Invoice", createdBy: "Jane Smith", createdOn: "2023-06-10" },
	{ contractId: "C003", contractName: "Hardware Procurement", vendorName: "TechEdge Inc.", startDate: "2024-04-01", endDate: "2025-03-31", amount: 150000, status: "Active", contractType: "Purchase Order", createdBy: "Emily Rose", createdOn: "2024-03-20" },
	{ contractId: "C004", contractName: "Cloud Services Support", vendorName: "GreenSoft", startDate: "2024-02-15", endDate: "2025-02-14", amount: 78000, status: "Active", contractType: "Annual Maintenance", createdBy: "Michael Brown", createdOn: "2024-02-01" },
	{ contractId: "C005", contractName: "Building Maintenance", vendorName: "BuildRight", startDate: "2023-09-01", endDate: "2024-08-31", amount: 210000, status: "Terminated", contractType: "Service Level Agreement", createdBy: "Ava James", createdOn: "2023-08-10" },
	{ contractId: "C006", contractName: "Network Security Subscription", vendorName: "NetZone", startDate: "2024-03-01", endDate: "2024-08-31", amount: 35000, status: "Active", contractType: "Subscription", createdBy: "Chris Nolan", createdOn: "2024-02-25" },
	{ contractId: "C007", contractName: "Data Center Infrastructure", vendorName: "InfraTech", startDate: "2023-05-01", endDate: "2024-04-30", amount: 275000, status: "Expired", contractType: "Tender", createdBy: "Sarah Lee", createdOn: "2023-04-15" },
	{ contractId: "C008", contractName: "Cybersecurity Services", vendorName: "SecureCom", startDate: "2024-06-01", endDate: "2025-05-31", amount: 180000, status: "Pending", contractType: "Invoice", createdBy: "Mark Wayne", createdOn: "2024-05-20" },
	{ contractId: "C009", contractName: "Waste Management Services", vendorName: "EcoGreen Services", startDate: "2023-11-15", endDate: "2024-11-14", amount: 66000, status: "Active", contractType: "Subscription", createdBy: "Lilly Clark", createdOn: "2023-11-01" },
	{ contractId: "C010", contractName: "Aerial Survey Services", vendorName: "SkyBridge Ltd.", startDate: "2024-08-01", endDate: "2025-07-31", amount: 142000, status: "Pending", contractType: "Purchase Order", createdBy: "David Hill", createdOn: "2024-07-15" },
	{ contractId: "C011", contractName: "ERP Software License", vendorName: "FusionWare", startDate: "2023-03-20", endDate: "2024-03-19", amount: 99000, status: "Expired", contractType: "Invoice", createdBy: "Nina Patel", createdOn: "2023-03-05" },
	{ contractId: "C012", contractName: "Office Renovation Project", vendorName: "OmniBuild", startDate: "2024-05-05", endDate: "2025-05-04", amount: 310000, status: "Active", contractType: "Tender", createdBy: "Jason King", createdOn: "2024-04-25" },
	{ contractId: "C013", contractName: "Cloud Storage Services", vendorName: "AlphaCloud", startDate: "2023-10-01", endDate: "2024-09-30", amount: 43000, status: "Terminated", contractType: "Subscription", createdBy: "Rita Singh", createdOn: "2023-09-10" },
	{ contractId: "C014", contractName: "Customer Support Outsourcing", vendorName: "BrightServe", startDate: "2024-01-20", endDate: "2025-01-19", amount: 88000, status: "Active", contractType: "Service Level Agreement", createdBy: "Omar Reyes", createdOn: "2024-01-05" },
	{ contractId: "C015", contractName: "HVAC Maintenance Contract", vendorName: "MegaNet Ltd.", startDate: "2023-06-10", endDate: "2024-06-09", amount: 195000, status: "Expired", contractType: "Annual Maintenance", createdBy: "Helen Brooks", createdOn: "2023-05-30" },
	{ contractId: "C016", contractName: "Employee Laptops Purchase", vendorName: "NovaTech", startDate: "2024-04-10", endDate: "2025-04-09", amount: 112000, status: "Active", contractType: "Purchase Order", createdBy: "Ron Walker", createdOn: "2024-03-28" },
	{ contractId: "C017", contractName: "Urban Development Project", vendorName: "CityWorks", startDate: "2023-12-01", endDate: "2024-11-30", amount: 205000, status: "Pending", contractType: "Tender", createdBy: "Priya Mehta", createdOn: "2023-11-20" },
	{ contractId: "C018", contractName: "Accounting Software License", vendorName: "MatrixSoft", startDate: "2024-07-15", endDate: "2025-07-14", amount: 75000, status: "Active", contractType: "Invoice", createdBy: "Leo Chan", createdOn: "2024-07-01" },
	{ contractId: "C019", contractName: "IT Consulting Services", vendorName: "Helix Solutions", startDate: "2023-08-20", endDate: "2024-08-19", amount: 130000, status: "Expired", contractType: "Service Level Agreement", createdBy: "Tanya Wells", createdOn: "2023-08-05" },
	{ contractId: "C020", contractName: "Elevator Maintenance", vendorName: "ProSys Global", startDate: "2024-02-28", endDate: "2025-02-27", amount: 99000, status: "Active", contractType: "Annual Maintenance", createdBy: "Alex Rivera", createdOn: "2024-02-10" }
];

var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
	},
	//rowData: rowData,
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectId,
};

function rowSelectId() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const contractId = selectedData.map(node => node.contractId);
	const data = JSON.stringify(selectedData[0]);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].contractId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {

	} else {
	}
};

function getContractList() {
	gridOptions.api.setRowData(rowData);
	if (rowData && rowData.length > 0) {
		gridOptions.api.forEachNode(function(node) {
			if (node.rowIndex === 0) {
				node.setSelected(true);
			}
		});

	}

}

$(document).ready(function() {
	var gridDiv = document.querySelector('#vendorContractGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	getContractList();
});

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}