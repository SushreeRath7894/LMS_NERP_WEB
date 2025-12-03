$(function() {

	viewHomePage();
	viewWetCleanChecklist();
	fetchEmployeeList();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDiv = document.querySelector('#myNewGrid');
	new agGrid.Grid(gridDiv, gridOptionsNew);
	var gridDiv2 = document.querySelector('#myNewGrid2');
	new agGrid.Grid(gridDiv2, gridOptionsNew2);
	var gridDiv3 = document.querySelector('#myNewGrid3');
	new agGrid.Grid(gridDiv3, gridOptionsNew3);
	/*  const currentMonth = ("0" + (new Date().getMonth() + 1)).slice(-2);
	 $("#monthDropdown").val(currentMonth); */

	$("#myGrid").show();
	$('#deleteWebCleanChecklist').prop('disabled', true);
	$('#approveWebCleanChecklist').prop('disabled', true);

});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})

// column Defs

var columnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 20
	},

	{
		headerName: 'Checklist Id',
		field: "checklistId",
		width: 200,
		cellStyle: { textAlign: 'center' },
		cellRenderer: function(params) {
			let iconHtml = '';
			if (params.data.approveStatus === 1) {
				iconHtml = '<i class="bi bi-view-list"></i>';
			} else {
				iconHtml = '<i class="bi bi-pencil-square"></i>';
			}

			if (params.data.checklistId) {
				return '<a id="id" onclick="editWetCleanChecklist(\'' + params.data.checklistId + '\', '
					+ params.data.approveStatus + ', \'' + params.data.phaseStatus + '\')" href="javascript:void(0)">'
					+ params.data.checklistId + '</a> ' + iconHtml;
			} else {
				return '<a id="id" onclick="editWetCleanChecklist(\'' + params.data.checklistId + '\', '
					+ params.data.approveStatus + ', \'' + params.data.phaseStatus + '\')" href="javascript:void(0)">N/A</a> ' + iconHtml;
			}
		}
	},
	{
		headerName: 'Phase',
		field: "phaseStatus",
		width: 200,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Date',
		field: "issueDate",
		width: 200,
		cellStyle: { textAlign: 'center' }
	},

	{
    headerName: 'Pdf',
    field: "pdf",
    width: 200,
    cellStyle: { textAlign: 'center' },
    cellRenderer: function(params) {
        return `<i class="bi bi-file-earmark-pdf-fill" style="cursor: pointer; font-size: 20px;" onclick="downloadPdf('${params.data.checklistId}', '${params.data.phaseStatus}')"></i>`;
    }
},
	{
		headerName: 'Created By',
		field: "createdBy",
		width: 300,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Approve Status',
		field: "approveStatus",
		width: 250,
		cellStyle: { textAlign: 'center' }, // Common style for all cells
		cellRenderer: function(params) {
			var sts = params.data.approveStatus;
			if (sts == 1) {
				return '<span style="color: blue;">Approved</span>';
			} else {
				return 'Not Approved';
			}
		}
	}
];

// Grid options
var gridOptions = {
	columnDefs: columnDefs,
	rowData: [],
	rowSelection: 'single',
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	onSelectionChanged: onSelectionChange,
	domLayout: 'autoHeight'
};

let employeeList = [];

function fetchEmployeeList() {
	$.ajax({
		url: 'wet-clean-checklist-employee-list',
		method: 'GET',
		success: function(response) {
			// Parse and store the employee list from the response
			employeeList = JSON.parse(response.body);
		},
		error: function(error) {
			console.error('Error fetching employee list:', error);
		}
	});
}

function onSelectionChange() {
	var selectedRows = gridOptions.api.getSelectedRows();
    
	if (selectedRows.length > 0) {
		$('#deleteWebCleanChecklist').prop('disabled', false);
		$('#newButton').prop('disabled', true);
	} else {
		$('#newButton').prop('disabled', false);
		$('#deleteWebCleanChecklist').prop('disabled', true);
		$('#approveWebCleanChecklist').prop('disabled', true);
	}
	var approveStatus = selectedRows[0].approveStatus;

		if (approveStatus == 1) {
			$('#approveWebCleanChecklist').prop('disabled', true);
			$('#deleteWebCleanChecklist').prop('disabled', true);
		} else {
			$('#approveWebCleanChecklist').prop('disabled', false);
			$('#deleteWebCleanChecklist').prop('disabled', false);
		}
}



var columnDefsNew = [
	{
		headerName: 'Sl No',
		field: "checklistId",
		width: 100,
		cellStyle: { textAlign: 'center' },
	},
	{
		headerName: 'Activity',
		field: "activity",
		width: 500,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Start Time',
		field: "startTime",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput" type="time" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.checklistId}', 'startTime', this.value)" />`;
		}
	},
	{
		headerName: 'End Time',
		field: "endtime",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput" type="time" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.checklistId}', 'endtime', this.value)" />`;
		}
	},
	{
		headerName: 'Remark',
		field: "remark",
		width: 200,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.checklistId}', 'remark', this.value)" />`;
		}
	}
];

// Grid options
var gridOptionsNew = {
	columnDefs: columnDefsNew,
	rowData: [],
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	domLayout: 'autoHeight'
};

function updateData(id, field, value) {
	gridOptionsNew.api.forEachNode(function(node) {
		if (node.data.checklistId === id) {
			node.data[field] = value;

		}
	});
}

const checkedByList = [
	{ key: "OFC030", name: "PRAHALLAD BEHERA (OFC030)" },
	{ key: "OFC046", name: "SASANKA SEKHAR SAHOO (OFC046)" },
	// ... (other options)
];

// Define column definitions including the checkedBy dropdown column
var columnDefsNew2 = [
	{
		headerName: 'Sl No',
		field: "checklistId2",
		width: 120,
		hide:true,
		cellStyle: { textAlign: 'center' },
	},
	{
		headerName: 'Activity',
		field: "activity2",
		width: 400,
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Temp. in Celsius',
		field: "temp",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput1" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData2('${params.data.checklistId2}', 'temp', this.value)" />`;
		}
	},
	{
		headerName: 'Checked By',
		field: "checkedBy",
		width: 350,
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			const selectedValue = params.value || '';

			let selectHTML = `<select ${isDisabled} onchange="updateData2('${params.data.checklistId2}', 'checkedBy', this.value)">`;
			selectHTML += `<option value="">Select</option>`; // Default placeholder

			employeeList.forEach(employee => {
				const selected = employee.fullName === selectedValue ? 'selected' : '';
				selectHTML += `<option value="${employee.fullName}" ${selected}>${employee.fullName}</option>`;
			});

			selectHTML += `</select>`;
			return selectHTML;
		}
	},
	{
		headerName: 'Remark',
		field: "remark",
		width:180,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput1" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData2('${params.data.checklistId2}', 'remark', this.value)" />`;
		}
	}
];

var gridOptionsNew2 = {
	columnDefs: columnDefsNew2,
	rowData: [],
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	domLayout: 'autoHeight'
};

// Update function to modify data in gridOptionsNew2
function updateData2(date, field, value) {
	gridOptionsNew2.api.forEachNode(function(node) {
		if (node.data.checklistId2 === date) {  // Changed to match checklistId2
			node.data[field] = value;
		}
	});
}



// Example IDs or conditions to identify headers, modify as needed
const headerIds = ["4","5","6","7","8","9"]; // Add all header checklist IDs here if using checklistId

var columnDefsNew3 = [
	{
		headerName: 'Sl No',
		field: "checklistId3",
		width: 180,
		cellStyle: params => {
			// Bold for header rows
			if (headerIds.includes(params.data.checklistId3)) {
				return { textAlign: 'center', fontWeight: 'bold' };
			}
			return { textAlign: 'center' };
		}
	},
	{
		headerName: 'Activity',
		field: "activity3",
		width: 500,
		cellRenderer: (params) => {
			const isHeader = headerIds.includes(params.data.checklistId3);
			return isHeader ? `<strong>${params.value}</strong>` : params.value;
		},
		cellStyle: { textAlign: 'left' },
	},
	{
		headerName: 'Done And Not Done',
		field: "status",
		width: 200,
		cellRenderer: (params) => {
			const isHeader = headerIds.includes(params.data.checklistId3);
			if (isHeader) {
				return ''; // Leave blank for headers
			}
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `
				<select ${isDisabled} onchange="updateData3('${params.data.checklistId3}', 'status', this.value)">
					<option value="" ${!params.value ? 'selected' : ''}>Select</option>
					<option value="DONE" ${params.value === 'DONE' ? 'selected' : ''}>DONE</option>
					<option value="NOT DONE" ${params.value === 'NOT DONE' ? 'selected' : ''}>NOT DONE</option>
				</select>
			`;
		},
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Remark',
		field: "remark",
		width: 200,
		cellRenderer: (params) => {
			const isHeader = headerIds.includes(params.data.checklistId3);
			if (isHeader) {
				return ''; // Leave blank for headers
			}
			const isDisabled = params.data.approveSts === 1 ? 'disabled' : '';
			return `<input name="gridInput2" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData3('${params.data.checklistId3}', 'remark', this.value)" />`;
		},
		cellStyle: { textAlign: 'center' }
	}
];

// Grid options with row styling for headers
var gridOptionsNew3 = {
	columnDefs: columnDefsNew3,
	rowData: [],
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	getRowStyle: params => {
		// Style for header rows
		if (headerIds.includes(params.data.checklistId3)) {
			return { backgroundColor: '#f0f0f0', fontWeight: 'bold' };
		}
		return null;
	},
	domLayout: 'autoHeight'
};


// Update function to modify data in gridOptionsNew3
function updateData3(date, field, value) {
	gridOptionsNew3.api.forEachNode(function(node) {
		if (node.data.checklistId3 === date) {  // Changed to match checklistId3
			node.data[field] = value;
		}
	});
}

function deleteFun() {
		$('#delete').modal('show');
	}
function cancelModalBtnDlt() {
		$("#delete").modal("hide");
		gridOptions.api.deselectAll();
	}
function approveFun() {
		$('#approveModal').modal('show');
	}
function cancelModalBtnApr() {
	$("#approveModal").modal("hide");
	gridOptions.api.deselectAll();
}
 function validateSelection() {
        const dropdown = document.getElementById('selectionDropdown');
        const selectButton = document.getElementById('selectButton');
        
        // Enable button only if "PHASE01" or "PHASE02" is selected
        selectButton.disabled = !(dropdown.value === 'PHASE01' || dropdown.value === 'PHASE02');
        
    }
function openNewPage(phaseStatus) {
	
	$(".validation-error").remove();
    $("#selectedPhase").val('');
	let selectedPhase = $("#selectionDropdown").val() || phaseStatus;
	getWetCleanMasterData(selectedPhase);
	$("#selectedPhase").val(selectedPhase);
	$("#wetChecklistModal").modal('hide');
	$("#checklistId").val('');
	$("#WetCheckId").text('');
	$("#issueDate").val('');
	$("#peforationRecordDiv").show();
	$("#newButton").hide();
	$("#dwnldExcel").hide();
	$("#myGrid").hide();
	$("#myNewGrid").show();
	$("#totalFeChecklist").hide();
	$('#deleteWebCleanChecklist').hide();
	$('#approveWebCleanChecklist').hide();
	$('#totalWetCleanChecklist').hide();
	$("#inspectionDate").val('');
	$('#saveWetChecklist').prop('disabled', false);

	// Datepicker for Disposal Date
	$("#issueDateCalendar").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false
	}).on("change", function() {
		$('#issueDate').val($(this).val());
	});

	// Synchronize the inspection date field on blur
	$('#issueDate').blur(function() {
		$("#issueDateCalendar").val($(this).val());
	});
}

function openPageForEdit() {
    $('#deleteWebCleanChecklist').prop('disabled', false);
	$('#approveWebCleanChecklist').prop('disabled', false);
	$("#peforationRecordDiv").show();
	$("#newButton").hide();
	$("#dwnldExcel").hide();
	$("#myGrid").hide();
	$("#totalFeChecklist").hide();
	$('#deleteWebCleanChecklist').hide();
	$('#approveWebCleanChecklist').hide();
	$('#approvePerforationRecord').hide();
	

}

function viewHomePage() {
	$("#peforationRecordDiv").hide();
	$("#newButton").show();
	$("#dwnldExcel").show();
	$("#myGrid").show();
	$("#totalFeChecklist").show();
	$('#deleteWebCleanChecklist').show();
	$('#totalWetCleanChecklist').show();
	$('#approveWebCleanChecklist').show();
}
function cancelNewPage() {
	$("#peforationRecordDiv").hide();
	$("#newButton").show();
	$("#dwnldExcel").show();
	$("#myGrid").show();
	$("#totalFeChecklist").show();
	$('#deleteWebCleanChecklist').show();
	$('#totalWetCleanChecklist').show();
	$('#approveWebCleanChecklist').show();
    gridOptions.api.deselectAll();
}

function openNewModal() {
	$("#selectionDropdown").val('');
	$("#wetChecklistModal").modal('show');
	validateSelection();
}
function cancelModal() {
	$("#wetChecklistModal").modal('hide');
}
function getWetCleanMasterData(selectedPhase) {

	$.ajax({
		url: 'wet-clean-checklist-view?selectedPhase=' + selectedPhase,
		method: 'GET',
		success: function(response) {
			try {
				const parsedData = JSON.parse(response.body);
				let gridData1 = parsedData.grid1Data;
				let gridData2 = parsedData.grid2Data;
				let gridData3 = parsedData.grid3Data;
				gridOptionsNew.api.setRowData(gridData1);
				gridOptionsNew2.api.setRowData(gridData2);
				gridOptionsNew3.api.setRowData(gridData3);
			} catch (error) {
				console.error('Error parsing JSON:', error);
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}
function saveWetCleanData() {
	var checklistId = $("#checklistId").val();
	var issueDate = $("#issueDate").val();
	var selectedPhase = $("#selectedPhase").val();

	let allRowData = [];
	let allRowData2 = [];
	let allRowData3 = [];
	var isValid = true;

	$(".validation-error").remove();
	if (!issueDate) {
		$("#issueDate").after('<span class="validation-error" style="color:red;">Issue Date is required</span>');
		isValid = false;
	}
	if (!isValid) {
		return;  
	}

	let isDuplicateEntry = false;

	// Check for duplicate date and phase only if checklistId is null
	if (!checklistId) {
		// Check if the issue date already exists in issueDatesMap with the same phase
		if (issueDatesMap[issueDate] && issueDatesMap[issueDate].includes(selectedPhase)) {
			isDuplicateEntry = true;
		}

		// Show the modal if a duplicate date and phase was found
		if (isDuplicateEntry) {
			$("#messageParagraph").text("You Cannot Save The Data Twice For The Same Date and Phase");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			return;
		}
	}

	gridOptionsNew.api.forEachNode(function(node) {
		let rowData = {};

		gridOptionsNew.columnDefs.forEach(function(column) {
			const field = column.field;
			rowData[field] = node.data[field] || "";
		});

		allRowData.push(rowData);
	});

	gridOptionsNew2.api.forEachNode(function(node) {
		let rowData = {};

		gridOptionsNew2.columnDefs.forEach(function(column) {
			const field = column.field;
			rowData[field] = node.data[field] || "";
		});

		allRowData2.push(rowData);
	});

	gridOptionsNew3.api.forEachNode(function(node) {
		let rowData = {};

		gridOptionsNew3.columnDefs.forEach(function(column) {
			const field = column.field;
			rowData[field] = node.data[field] || "";
		});

		allRowData3.push(rowData);
	});

	let checklistJsonData = {
		checklistId: checklistId,
		issueDate: issueDate,
		selectedPhase: selectedPhase,
		allRowData: allRowData,
		allRowData2: allRowData2,
		allRowData3: allRowData3
	};

	let jsonData = JSON.stringify(checklistJsonData);
	console.log("Data For save=========>>>", jsonData);

	// AJAX call to save the data
	$.ajax({
		url: 'wet-clean-checklist-save-data',
		type: 'POST',
		contentType: 'application/json',
		data: jsonData,
		success: function(response) {
			if (response.code == "success") {
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				cancelNewPage();
				viewWetCleanChecklist();
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}

const issueDatesMap = {};  

function viewWetCleanChecklist() {
	$.ajax({
		url: 'wet-clean-checklist-data-view',
		method: 'GET',
		success: function(response) {
			try {
				const parsedData = response.body ? JSON.parse(response.body) : [];
				var len = parsedData.length;
				$('#totalWetCleanChecklist').find('span').html(len);

				for (let i = 0; i < len; i++) {
					let issueDate = parsedData[i].issueDate;
					let phaseStatus = parsedData[i].phaseStatus;

					// Initialize date entry in map if it doesn't exist
					if (!issueDatesMap[issueDate]) {
						issueDatesMap[issueDate] = [];
					}

					// Add the phase to the issue date entry if not already present
					if (!issueDatesMap[issueDate].includes(phaseStatus)) {
						issueDatesMap[issueDate].push(phaseStatus);
					}
				}

				gridOptions.api.setRowData(parsedData);
			} catch (error) {
				console.error('Error parsing JSON:', error);
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}


function editWetCleanChecklist(checklistId, approveStatus, phaseStatus) {
	
	openNewPage(phaseStatus);
	if (approveStatus == 1) {
		$('#saveWetChecklist').prop('disabled', true);
		$('input[name="gridInput"]').prop('disabled', true);
		$('input[name="gridInput1"]').prop('disabled', true);
		$('input[name="gridInput2"]').prop('disabled', true);
	} else {
		$('#saveWetChecklist').prop('disabled', false);
		$('input[name="gridInput"]').prop('disabled', false);
		$('input[name="gridInput1"]').prop('disabled', false);
		$('input[name="gridInput2"]').prop('disabled', false);
	}

	$.ajax({
		url: 'wet-clean-checklist-edit?checklistId=' + checklistId + '&phaseStatus=' + phaseStatus,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {

				var parsedBody = JSON.parse(response.body);
				let issueDate = parsedBody[0].issueDate;
				let phaseStatus = parsedBody[0].phaseStatus;
				let gridData1 = parsedBody[0].grid1Data;
				let gridData2 = parsedBody[0].grid2Data;
				let gridData3 = parsedBody[0].grid3Data;

				gridOptionsNew.api.setRowData(gridData1);
				gridOptionsNew2.api.setRowData(gridData2);
				gridOptionsNew3.api.setRowData(gridData3);
				$("#issueDate").val(issueDate);
				$("#checklistId").val(checklistId);
				$("#WetCheckId").text(checklistId);
				$("#selectedPhase").val(phaseStatus);

			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}
 

function deleteWebCleanChecklist() {
    $("#delete").modal("hide");
    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedNodes = gridOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);

    // Get the checklistId, issueDate, and phaseStatus from the first selected row
    var checklistId = selectedData[0].checklistId;
    var issueDate = selectedData[0].issueDate;
    var phaseStatus = selectedData[0].phaseStatus;

    console.log("Before Deletion - issueDatesMap:", issueDatesMap);

    $.ajax({
        type: "GET",
        url: "wet-clean-checklist-delete?checklistId=" + checklistId,
        async: false,
        success: function(response) {
            console.log(response);
            if (response.code == "success") {
                // Check if the issueDate entry exists and includes the phaseStatus
                if (issueDatesMap[issueDate]) {
                    // Find the index of the phaseStatus in the array
                    const phaseIndex = issueDatesMap[issueDate].indexOf(phaseStatus);
                    
                    // If phaseStatus exists in the array, remove it
                    if (phaseIndex !== -1) {
                        issueDatesMap[issueDate].splice(phaseIndex, 1);
                    }

                    // If the array for this date is now empty, delete the date entry
                    if (issueDatesMap[issueDate].length === 0) {
                        delete issueDatesMap[issueDate];
                    }
                }

                console.log("After Deletion - issueDatesMap:", issueDatesMap);

                viewWetCleanChecklist(); // Refresh checklist view
                $("#messageParagraph").text(response.message);
                $("#msgOkModal").removeClass("btn3").addClass("btn1");
                $("#msgModal").modal('show');

                // Disable and enable buttons as needed
                $('#deleteWebCleanChecklist').attr("disabled", true);
                $('#approveWebCleanChecklist').prop('disabled', true);
                $('#newButton').prop('disabled', false);
                $('#new').show();
            }
        },
        error: function(data) {
            console.error("Error during deletion:", data);
        }
    });
}


function approveWebCleanChecklist() {
	$("#approveModal").modal("hide");
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	var checklistId = selectedData[0].checklistId;
	$.ajax({
		type: "GET",
		url: "wet-clean-checklist-approve?checklistId=" + checklistId,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				viewWetCleanChecklist();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#deleteWebCleanChecklist').attr("disabled", true);
				$('#approveWebCleanChecklist').prop('disabled', true);
				$('#newButton').prop('disabled', false);
				$('#new').show();
			}

		},
		error: function(data) {
		}
	});
}
function downloadPdf(checklistId, phaseStatus) {
    const url = `/ticket/wet-clean-checklist-Pdf?checklistId=${checklistId}&phaseStatus=${phaseStatus}`;
    window.open(url, '_blank');
}
