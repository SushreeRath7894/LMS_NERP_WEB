
let templateSectionOptions;
let templateSectionMainOptions;
$(document).ready(function() {
	var gridDiv = document.querySelector('#workFlowGrid');
	new agGrid.Grid(gridDiv, workFlowGridOptions);

	//workFlowGridOptions.api.setRowData(mainData.contracts);
	/*	var rowData = [];
		workFlowGridOptions.api.setRowData(rowData);
		workFlowGridOptions.api.setRowData(mainData.contracts);
		if (mainData.contracts && mainData.contracts.length > 0) {
			workFlowGridOptions.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});
		}*/
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});

	contractCreation();
	getAssignUser();




});

function initializeCKEditorDesc() {
	if (CKEDITOR.instances['tenderEditor']) {
		CKEDITOR.instances['tenderEditor'].destroy(true);
	}
	CKEDITOR.replace('tenderEditor');
}


function contractCreation() {
	var fromDate = '';
	var toDate = '';
	$.ajax({
		url: `view-contract-data?fromDate=${fromDate}&toDate=${toDate}`,
		method: "GET",
		dataType: "json",
		success: function(response) {
			if (response.code === "Success") {
				const parsedResponse = JSON.parse(response.body);
				var rowData = [];
				console.log("response -->", parsedResponse);
				parsedResponse.filter((element) => {
					if (element.publishStatus == "1") {
						rowData.push(element);
					}
				})
				workFlowGridOptions.api.setRowData(rowData);
				if (rowData && rowData.length > 0) {
					workFlowGridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}

			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data:", error);
		}
	});

}

function getAssignUser() {
	var userid = $("#userId").val();
	$.ajax({
		url: `workflow-approval-assignemp`,
		method: "GET",
		dataType: "json",
		success: function(response) {
			if (response.code === "Success") {
				const parsedResponse = JSON.parse(response.body);
				console.log("parsed json-->", parsedResponse);
				if (parsedResponse[0].EmpId == userid) {
					$("#approve-btn").show();
					$(".approve-btn-header").show();
				}
				else {
					$("#approve-btn").hide();
					$(".approve-btn-header").hide();
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data:", error);
		}
	});

}


function workflow_contract_Approval(id) {
	var userid = $("#userId").val();
	$.ajax({
		url: `workflow-approval-contact-approve?id=${id}`,
		method: "GET",
		dataType: "json",
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				contractCreation();
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data:", error);
			toastr.error(error);
		}
	});

}


function approveBtn() {
	let selectedRows = workFlowGridOptions.api.getSelectedRows();
	console.log("grid row-->", selectedRows);
	let contractId = selectedRows[0].contractId;
	workflow_contract_Approval(contractId);
}
var workFlowGridDefs = [
	{
		headerName: 'Contract Workflow templates',

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
				field: "contractId",
				width: 200
			},
			{
				headerName: "Contract Name",
				field: "contractName",
				width: 250
			}, {
				headerName: "Tender Name",
				field: "tenderName",
				width: 250
			},
			{
				headerName: "Vendor Id",
				field: "vendorId",
				hide: true,
				width: 200
			},
			{
				headerName: "Vendor Name",
				field: "vendorName",
				width: 200
			},
			{
				headerName: "Approve Status",
				field: "approveStatus",
				width: 200,
				cellRenderer: function(params) {
					return params.value == "1" ? 'Approved' : 'Pending';
				}
			},
			{
				headerName: "Approve By",
				field: "approverName",
				width: 200
			},
			{
				headerName: "Description",
				field: "templateDescription",
				width: 250
			},
			{
				headerName: "Created Date",
				field: "creationDate",
				width: 200
			}, {
				headerName: "Effective Date",
				field: "effectiveDate",
				width: 250
			}, {
				headerName: "status",
				field: "status",
				width: 230,
				cellRenderer: function(params) {
					return params.value == "1" ? 'Active' : 'Inactive';
				}
			}, {
				headerName: "Published Pdf",
				field: "tenderPdf",
				width: 230,
				cellStyle: {
					textAlign: 'center'
				},
				cellRenderer: function(params) {
					const pdfUrl = params.value;
					if (pdfUrl && pdfUrl !== "null") {
						return `
		                <a href="${pdfUrl}" target="_blank" title="Open PDF">
		                    <img src="../assets/images/pdf_demo.png" alt="PDF" style="width: 15px; height: 18px;"/>
		                </a>
		            `;
					} else {
						return '';
					}
				}
			},
			{
				headerName: "Published Status",
				field: "publishStatus",
				width: 230,
				cellStyle: {
					textAlign: 'center'
				},
				cellRenderer: function(params) {
					const status = params.value;
					if (status && status == "1") {
						return `
		                <a title="Published" style="color: green; text-decoration: none;">Published</a>`;
					} else {
						return `<a title="Published" style="color: #f83237; text-decoration: none;">Not Published</a>`;
					}
				}
			}
		]
	}];

var workFlowGridOptions = {
	columnDefs: workFlowGridDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	onSelectionChanged: rowselect,
	pagination: true,
	paginationPageSize: 10,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},

};

function addworkflow() {
	$("#markLostModal").modal("show");
}

function closeModal() {
	$("#markLostModal").modal("show");
}

function rowselect() {
	const selectedRows = workFlowGridOptions.api.getSelectedRows();
	console.log("rowss->", selectedRows);

	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		var contractid = selectedRows[0].contractId;
		$("#headercontractid").text(contractid);
		$("#templateName").val(selectedRows[0].contractName);
		$("#effectiveDate").val(selectedRows[0].effectiveDate);
		$("#date").val(selectedRows[0].creationDate);
		$("#tendername").val(selectedRows[0].tenderName);
		$("#vendorname").val(selectedRows[0].vendorName);
		$("#pdfLink").attr("href", selectedRows[0].contractPdf);
		$("#status").val(selectedRows[0].publishStatus == "1" ? "Publish" : " ");

		if (selectedRows[0].approveStatus === "1") {
			console.log("in if clause")
			$(".approve-btn-header").hide();
		}
		else {
			$(".approve-btn-header").show();
			console.log("in else clause")
		}
		CKEDITOR.instances['tenderEditor'].setData(selectedRows[0].templateDescription);
		//CKEDITOR.instances['tenderEditor'].setReadOnly(true);
	} else {
		$("#headercontractid").text("");
	}
}

function resetBtn() {
	$("#quickFilter").val('');

	workFlowGridOptions.api.setQuickFilter('');

	workFlowGridOptions.api.refreshCells({ force: true });
}


function onQuickFilterChanged() {
	workFlowGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	getMostClosestRow();
}


function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	workFlowGridOptions.api.setQuickFilter(searchValue);

	let rowCount = workFlowGridOptions.api.getModel().getRowCount();

	workFlowGridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			workFlowGridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}
function resetBtn() {
	$("#quickFilter").val('');
	workFlowGridOptions.api.setQuickFilter('');
	workFlowGridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		workFlowGridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				workFlowGridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}
