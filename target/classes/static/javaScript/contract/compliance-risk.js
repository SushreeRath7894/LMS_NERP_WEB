
$(document).ready(function() {
	var gridDiv = document.querySelector('#complianceRiskGrid');
	new agGrid.Grid(gridDiv, contractNegotationGridOptions);

	$("body").on("click", ".delete-compliance", function() {
		$(this).closest(".section").remove();
		console.log("call delete btn")
		updateSectionNumbers(); // Update section numbers after removal
	});

	$("body").on("click", ".delete-risk", function() {
		$(this).closest(".section").remove();
		updateRiskSection(); // Update section numbers after removal
	});

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});
});

var negotiationGridDefs = [
	{
		headerName: 'Contract  templates',

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
			},
			{
				headerName: "Compliance Details",
				field: "compliance",
				width: 230,
				hide: true,
				cellStyle: {
					textAlign: 'center'
				}
			},
			{
				headerName: "Risk Details",
				field: "risk",
				width: 230,
				hide: true,
				cellStyle: {
					textAlign: 'center'
				}
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

	console.log("selectedRows-->", selectedRows);

	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	const complianceSection = document.getElementById('complianceSection');
	if (rowCount > 0) {
		var contractid = selectedRows[0].templateId
		var selectedNodes = contractNegotationGridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var templateId = selectedData.map(node => node.templateId);
		$("#headercontractid").text(selectedRows[0].contractId);
		$("#headercontractid2").text(selectedRows[0].contractId);

		let complianceDetails = selectedRows[0].compliance;
		complianceGridOptions.api.setRowData(complianceDetails);
		let riskDetails = selectedRows[0].risk;
		riskGridOptions.api.setRowData(riskDetails);

	} else {
		//complianceSection.classList.add('hidden');
		complianceGridOptions.api.setRowData([]);
		riskGridOptions.api.setRowData([]);
		$("#headercontractid").text('');
		$("#headercontractid2").text('');
	}
}

let complianceGridOptions;
let riskGridOptions;
$(document).ready(function() {
	// Compliance Grid Configuration
	const complianceColumnDefs = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			sortable: false,
			filter: false,
			resizable: true,
			width: 20
		},
		{ headerName: 'Category Name', field: 'categoryName', width: 300 },
		{ headerName: 'Action', field: 'action', width: 300 },
		{ headerName: 'Parameter', field: 'parameter', width: 260 }
	];

	complianceGridOptions = {
		columnDefs: complianceColumnDefs,
		rowData: [],
		domLayout: 'autoHeight',
		onSelectionChanged: complianceSelect,
	};


	$("#deleteFactor").attr("disabled", true);
	$("#newFactor").attr("disabled", false);



	// Risk Grid Configuration
	const riskColumnDefs = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			sortable: false,
			filter: false,
			resizable: true,
			width: 20
		},
		{ headerName: 'Category Name', field: 'categoryName', width: 300 },
		{ headerName: 'Action', field: 'action', width: 300 },
		{ headerName: 'Parameter', field: 'parameter', width: 260 }
	];

	riskGridOptions = {
		columnDefs: riskColumnDefs,
		rowData: [],
		domLayout: 'autoHeight',
		onSelectionChanged: riskRowSelect,
	};

	$("#deleterisk").attr("disabled", true);
	$("#addMultiRisk").attr("disabled", false);

	// Initialize Grids
	new agGrid.Grid(document.getElementById('complianceGrid'), complianceGridOptions);
	new agGrid.Grid(document.getElementById('riskGrid'), riskGridOptions);

	// Add compliance entry
	$("#addMultiCompliance").click(function() {


		$("#complianceGrid").hide();
		$("#complianceContainer").append(createCompliance());
		$("#complianceContainer").show();
	});

	// Add risk entry
	$("#addMultiRisk").click(function() {
		$("#riskGrid").hide();
		$("#riskContainer").append(createRisk());
		$("#riskContainer").show();
	});

	// Save compliance data and add to grid
	$("body").on("click", ".save-compliance", function() {
		const complianceElement = $(this).closest(".compliance");
		const categoryName = complianceElement.find(".category-name").val();
		const action = complianceElement.find(".action").val();
		const parameter = complianceElement.find(".parameter").val();

		if (categoryName.trim() !== "" && action.trim() !== "" && parameter.trim() !== "") {
			const complianceObject = {
				categoryName: categoryName,
				action: action,
				parameter: parameter
			};

			// Add to compliance grid
			complianceGridOptions.api.applyTransaction({ add: [complianceObject] });
			complianceElement.remove();

		} else {
			swal("Please enter Category Name, Action, and Parameter!", " ", "error");
		}

		$("#complianceGrid").show();
	});

	// Save risk data and add to grid
	$("body").on("click", ".save-risk", function() {
		const riskElement = $(this).closest(".risk");
		const categoryName = riskElement.find(".category-name").val();
		const action = riskElement.find(".action").val();
		const parameter = riskElement.find(".parameter").val();

		if (categoryName.trim() !== "" && action.trim() !== "" && parameter.trim() !== "") {
			const riskObject = {
				categoryName: categoryName,
				action: action,
				parameter: parameter
			};

			// Add to risk grid
			riskGridOptions.api.applyTransaction({ add: [riskObject] });
			riskElement.remove();
		} else {
			swal("Please enter Category Name, Action, and Parameter!", " ", "error");
		}
		$("#riskGrid").show();
	});

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});

	contractCreation();
});

let complianceCounter = 0;
let savedCompliances = [];

function createCompliance(categoryName = "", action = "", parameter = "") {
	complianceCounter++;
	return `
        <div class="card mb-3 compliance">
            <div class="card-header d-flex justify-content-between align-items-center">
                <input type="text" class="form-control w-50 category-name" placeholder="Enter Category Name" value="${categoryName}">
				<div class="operations-Buttons">
						<i class="fa fa-trash delete-compliance"></i>
						<i class="fa-solid fa-floppy-disk save-compliance"></i>
						</div>
            </div>
            <div class="card-body">
                <input type="text" class="form-control mb-2 action" placeholder="Enter Action" value="${action}">
                <input type="text" class="form-control parameter" placeholder="Enter Parameter" value="${parameter}">
            </div>
            
        </div>`;
}

let riskCounter = 0;
let savedRisks = [];

function createRisk(categoryName = "", action = "", parameter = "") {
	riskCounter++;
	return `
        <div class="card mb-3 risk">
            <div class="card-header d-flex justify-content-between align-items-center">
                <input type="text" class="form-control w-50 category-name" placeholder="Enter Category Name" value="${categoryName}">
				<div class="operations-Buttons">
										<i class="fa fa-trash delete-risk"></i>
										<i class="fa-solid fa-floppy-disk save-risk"></i>
										</div>
				 </div>
            <div class="card-body">
                <input type="text" class="form-control mb-2 action" placeholder="Enter Action" value="${action}">
                <input type="text" class="form-control parameter" placeholder="Enter Parameter" value="${parameter}">
            </div>
        </div>`;
}


function updateSectionNumbers() {
	/*$(".section-number").each(function(index) {
		$(this).text(index + 1);
	});*/

	$("#complianceContainer").html('');
	$("#complianceContainer").hide();
	$("#complianceGrid").show();
}

function updateRiskSection() {
	$("#riskContainer").html('');
	$("#riskContainer").hide();
	$("#riskGrid").show();
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
function onQuickFilterChanged() {
	contractNegotationGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}


function contractCreation() {
	var fromDate = '';
	var toDate = '';
	let exitContractid = $("#headercontractid").text();
	$.ajax({
		url: `compliance-risk-contractdetails?fromDate=${fromDate}&toDate=${toDate}`,
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
				contractNegotationGridOptions.api.setRowData(rowData);
				/*if (rowData && rowData.length > 0) {
					contractNegotationGridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}*/
				checkSelectedRow(exitContractid);

			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data:", error);
		}
	});

}



function deleteFactor1() {
	var selectedRows = complianceGridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	let allRowData = [];
	let totalMark = 0;
	complianceGridOptions.api.forEachNode((node) => {
		allRowData.push(node.data);
	});
	const updatedData = allRowData.filter(row => !selectedRows.includes(row));
	updatedData.forEach((row, index) => {
		row.slno = index + 1;
		totalMark += parseInt(row.maxmark);
	});
	complianceGridOptions.api.setRowData(updatedData);
	toastr.success('Compliance  Delete  Successfully');
	$("#deleteFactor").attr("disabled", true);
	$("#newFactor").attr("disabled", false);
}


function complianceSelect() {

	var selectedRows = complianceGridOptions.api.getSelectedRows();
	console.log("rowss--->", selectedRows);
	if (selectedRows.length > 0) {
		$("#deleteFactor").attr("disabled", false);
		$("#newFactor").attr("disabled", true);
	}
	else {
		$("#deleteFactor").attr("disabled", true);
		$("#newFactor").attr("disabled", false);
	}

}

function riskRowSelect() {

	var selectedRows = riskGridOptions.api.getSelectedRows();
	console.log("rowss--->", selectedRows);
	if (selectedRows.length > 0) {
		$("#deleterisk").attr("disabled", false);
		$("#addMultiRisk").attr("disabled", true);
	}
	else {
		$("#deleterisk").attr("disabled", true);
		$("#addMultiRisk").attr("disabled", false);
	}
}



function saveCompliance() {
	var obj = {};
	var allValid = true;
	var datas = [];
	let exitContractid = $("#headercontractid").text();
	var dataOfGrid1 = [];
	var contractid = $("#headercontractid").text();
	complianceGridOptions.api.forEachNode(function(rowNode, index) {
		dataOfGrid1.push(rowNode.data);
		console.log("rowdata-->", rowNode)
	});
	dataOfGrid1.forEach(item => {
	    item.action = item.action.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	    item.categoryName = item.categoryName.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	    item.parameter = item.parameter.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	});

	obj.grid1List = dataOfGrid1;
	obj.contractid = contractid;
	if (allValid) {
		datas.push(obj);
		console.log("datas addedd====", datas)
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "compliance-risk-complianceadd",
			async: true,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					console.log(response);
					contractCreation();
					setTimeout(() => {
						checkSelectedRow(exitContractid); // Call after a short delay
					}, 500); // Adjust delay as needed
					$('.loader').hide();
				}
				else {
					toastr.success(response.message);
					$('.loader').hide();
				}

			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}


function checkSelectedRow(id) {
	console.log("in new function", id)
	if (id) {
		contractNegotationGridOptions.api.forEachNode(function(node) {
			if (node.data && node.data.contractId === id) {
				console.log("in if clause")
				node.setSelected(true); // Select the matching row
				return; // Stop further iteration once found
			}
		});
	} else {
		var firstRowNode = contractNegotationGridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	}
}




function saveRisk() {
	var obj = {};
	var allValid = true;
	var datas = [];
	let exitContractid = $("#headercontractid").text();
	var dataOfGrid1 = [];
	var contractid = $("#headercontractid").text();
	riskGridOptions.api.forEachNode(function(rowNode, index) {
		dataOfGrid1.push(rowNode.data);
		console.log("rowdata-->", rowNode)
	});
	dataOfGrid1.forEach(item => {
	    item.action = item.action.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	    item.categoryName = item.categoryName.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	    item.parameter = item.parameter.trim().replace(/'/g, '=#').replace(/"/g, '=$');
	});

	obj.grid1List = dataOfGrid1;
	obj.contractid = contractid;
	if (allValid) {
		datas.push(obj);
		console.log("datas addedd====", datas)
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "compliance-risk-riskAssesmentAdd",
			async: true,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					contractCreation();
					setTimeout(() => {
						checkSelectedRow(exitContractid); // Call after a short delay
					}, 500); // Adjust delay as needed
					$('.loader').hide();
				}
				else {
					toastr.success(response.message);
					$('.loader').hide();
				}

			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}


function risk() {
	var selectedRows = riskGridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	let allRowData = [];
	let totalMark = 0;
	riskGridOptions.api.forEachNode((node) => {
		allRowData.push(node.data);
	});
	const updatedData = allRowData.filter(row => !selectedRows.includes(row));
	updatedData.forEach((row, index) => {
		row.slno = index + 1;
		totalMark += parseInt(row.maxmark);
	});
	riskGridOptions.api.setRowData(updatedData);
	toastr.success('Risk  Delete  Successfully');
	$("#deleterisk").attr("disabled", true);
	$("#addMultiRisk").attr("disabled", false);
}
