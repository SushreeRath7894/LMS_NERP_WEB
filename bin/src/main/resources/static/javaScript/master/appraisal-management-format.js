$(document).ready(function() {
	$("#download").attr("disabled", true);
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDiv2 = document.querySelector('#categoryGrid');
	new agGrid.Grid(gridDiv2, gridOptions2);
	gridOptions.api.hideOverlay();
	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1);
	var gridDiv3 = document.querySelector('#categoryGrid1');
	new agGrid.Grid(gridDiv3, gridOptions3);
	/*		
		var gridDiv3 = document.querySelector('#employeeListGrid');
		new agGrid.Grid(gridDiv3, employeeGridOptions);
		var rowData = [];
		gridOptions1.api.setRowData(rowData);*/
	$("#categoryidPara").hide();
	$("#mySidenav1").hide();
	$("#deleteFactor").attr("disabled", true);
	$("#assignId").attr("disabled", true);
	$("#approveKeyFact").attr("disabled", true);
	$("#employeeListModal").modal('hide');
	$("#confirmModal").modal('hide');
	$("#successMOdal").modal('hide');
	var responseData = [];
	fetchData();
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	var financialYearStart = curYear;
	var financialYearEnd = curYear + 1;
	if (curDate.getMonth() < 3) {
		financialYearStart -= 1;
		financialYearEnd -= 1;
	}
	var financialYear = financialYearStart + '-' + financialYearEnd;
	$("#attndyear").val(financialYear);
	changeMonthYear();
	var dateFormat = localStorage.getItem("dateFormat");
	var today = new Date();
	$("#applyStartCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
		handelDates(document.getElementById("fromDate"));
	})
	$('#fromDate').blur(function() {
		$("#applyStartCalendar").val($(this).val());
		handelDates(document.getElementById("fromDate"));
	});
	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDatePicker").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
		handelDates(document.getElementById("toDate"));
	})
	$('#toDate').blur(function() {
		$("#toDatePicker").val($(this).val());
		handelDates(document.getElementById("toDate"));
	})


	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault(); // Stop the form from submitting
			onQuickFilterChanged(); // Call the filter function
		}
	});

});
function handelDates(event) {
	var elementValue = $(event).val();
	if (elementValue != "") {
		$(event).siblings(".formValidation").hide();
	} else {
		$(event).siblings(".formValidation").show();
	}
}
function handelDropDown(element) {
	var elementValue = $(element).val();
	if (elementValue != "") {
		$(element).siblings(".formValidation").hide();
	} else {
		$(element).siblings(".formValidation").show();
	}
}
function changeMonthYear() {
	var selectedYear = parseInt($("#attndyear").val());
	console.log("Selected Year:", selectedYear);
	var fromDate = new Date(selectedYear, 4 - 1, 1);
	console.log("From Date:", fromDate);
	var toDate = new Date(selectedYear + 1, 3 - 1, 31);
	console.log("To Date:", toDate);
	$("#fromDate").val(formatDate(fromDate));
	$("#toDate").val(formatDate(toDate));
}
function getFinancialYear() {
	const currentYear = new Date().getFullYear();
	const nextYear = currentYear + 1;
	//return `${currentYear}-${nextYear}`;
	var financialYear = `${currentYear}-${nextYear}`;
	$("#attndyear").val(financialYear);
}
function formatDate(date) {
	var day = date.getDate();
	var month = date.getMonth() + 1; // Months are zero-indexed
	var year = date.getFullYear();
	return (day < 10 ? '0' : '') + day + '-' + (month < 10 ? '0' : '') + month + '-' + year;
}
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	getMostClosestRow();
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true); 
			gridOptions.api.ensureIndexVisible(node.rowIndex); 
		}
	});
}
function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	    setTimeout(() => {
	        gridOptions.api.forEachNode((node, index) => {
	            if (index === 0) {
	                node.setSelected(true); 
	                gridOptions.api.ensureIndexVisible(node.rowIndex); 
	            }
	        });
	    }, 50); 
}
function cancelBar() {
	//	var id = document.getElementById("closeKey");
	//	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
function updateTotalRow(gridApi) {
	const assementMark = calculateTotal('assementMark', gridApi);
	var totalRow = [{
		factors: 'Total',
		maxmark: "100",
		assementMark: assementMark,
		editable: false
	}];
	gridOptions2.api.setPinnedBottomRowData(totalRow);
}
function calculateTotal(field, gridApi) {
	let total = 0;
	gridApi.forEachNode(function(node) {
		total += parseFloat(node.data[field]) || 0;
	});
	return total;
}
// main grid details
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Factor id",
		field: "categoryid",
		width: 100,
		/*cellRenderer: function(params) {
			if (params.data.categoryid && params.data.approvalStatus == "0") {
				return '<a onclick=editAppraisal("' + params.data.categoryid + '") href="javascript:void(0)">'
					+ params.data.categoryid + '<i class="bi bi-pencil-square"></i></a>';
			} else if (params.data.categoryid && params.data.approvalStatus == "1") {
				return '<a  onclick = editAppraisal("' + params.data.categoryid + '") href="javascript:void(0)">'
					+ params.data.categoryid + '<i class="bi bi-view-list"></i></a>';
			}
		}*/
	}, {
		headerName: "Factor Name",
		field: "categoryName",
		cellStyle: {
			textAlign: 'left'
		},
		width: 100,
	}, {
		headerName: "Financial Year",
		field: "fincialyear",
		cellStyle: { textAlign: 'left' },
		width: 100,
	}, {
		headerName: "From Date",
		field: "fromDate",
		width: 120,
	}, {
		headerName: "To Date",
		field: "toDate",
		width: 120,
	}, {
		headerName: "Approval Status",
		field: "approvalStatus",
		width: 100,
		cellRenderer: function(params) {
			if (params.data.approvalStatus == "0") {
				return '<span class="pendingStatus" style="color:#a9a9a9">Pending</span>'
			} else {
				return '<span class="approveStatus" style="color:#0642f5">Approved</span>'
			}
		}
	}, {
		headerName: "Created By",
		field: "createdBy",
		width: 120,
	}];
var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 410,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectExpoler,
	overlayNoRowsTemplate: '<span>No rows to show</span>',
	pagination: true,
	paginationPageSize: 15,
	domLayout: '400px'
};
function onSelectExpoler() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var cateoryId = "";
	var approveStatus = "";
	var AssignEmpStatus = "";
	if (rowCount > 0) {
		approveStatus = selectedRows[0].approvalStatus;
		AssignEmpStatus = selectedRows[0].AssignEmpStatus;
		cateoryId = selectedRows[0].categoryid;
		editAppraisal(cateoryId);
		cancelKeyFact();
		cancelFactor1();
		travelPrev('1');
		editAppraisalSelf(cateoryId);
		$("#deleteFactor").attr("disabled", true);
		$("#newFactor").attr("disabled", true);
	}
	if (rowCount > 0 && approveStatus == "1" && AssignEmpStatus === "1") {
		$("#assignId").attr("disabled", true);
		$("#newKeyFact").attr("disabled", false);
		$("#editKeyFact").attr("disabled", true);
		$("#approveKeyFact").attr("disabled", true);
		$("#deleteKeyFact").attr("disabled", true);
		$("#assignKeyFact").attr("disabled", true);
		$("#assignBtn, #selfAppBtn, #next1, #next2").show();
		fetchAssignData(cateoryId);
	} else if (rowCount > 0 && approveStatus == "1") {
		$("#assignId").attr("disabled", false);
		$("#newKeyFact").attr("disabled", false);
		$("#editKeyFact").attr("disabled", true);
		$("#approveKeyFact").attr("disabled", true);
		$("#deleteKeyFact").attr("disabled", true);
		$("#assignKeyFact").attr("disabled", false);
		gridOptions1.api.setRowData();
		$("#assignBtn, #next1").show();
		$("#selfAppBtn, #next2").hide();
	} else if (rowCount > 0) {
		$("#newKeyFact").attr("disabled", false);
		$("#editKeyFact").attr("disabled", false);
		$("#approveKeyFact").attr("disabled", false);
		$("#deleteKeyFact").attr("disabled", false);
		$("#assignKeyFact").attr("disabled", true);
		gridOptions1.api.setRowData();
		$("#assignBtn, #selfAppBtn, #next1").hide();
	} else {
		$("#assignId").attr("disabled", true);
		$("#newKeyFact").attr("disabled", false);
		$("#editKeyFact").attr("disabled", true);
		$("#approveKeyFact").attr("disabled", true);
		$("#deleteKeyFact").attr("disabled", true);
		$("#assignKeyFact").attr("disabled", true);
		gridOptions1.api.setRowData();
		$("#assignBtn, #selfAppBtn, #next1").hide();
	}
}
// main form child grid details
var gridApi;
var columnDefs2 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Sl No",
		field: "slno",
		width: 65,
	}, {
		headerName: "Factors",
		field: "factors",
		editable: true,
		cellStyle: {
			textAlign: 'left'
		},
		width: 840,
	}, {
		headerName: "Max Mark",
		field: "maxmark",
		cellStyle: {
			textAlign: 'left'
		},
		width: 120,
	}, {
		headerName: "Reporting Officer Assessment",
		field: "assementMark",
		editable: true,
		hide: true,
		cellStyle: function(params) {
			const api = params.api || params.node.gridOptions.api;
			const isPinnedRow = params.node.rowPinned;
			if (isPinnedRow) {
				return null;
			}
			return { textAlign: 'left', background: '#eae7fa8f !important' };
		},
		width: 200,
		valueParser: function(params) {
			const numericValue = parseFloat(params.newValue);
			if (!isNaN(numericValue)) {
				return numericValue;
			} else {
				return params.oldValue;
			}
		}
	}];
var rowData = [{
	empId: 'Total'
},];
var gridOptions2 = {
	columnDefs: columnDefs2,
	suppressRowClickSelection: true,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 210,
		height: 10,
		singleClickEdit: true,
		stopEditingWhenCellsLoseFocus: true
	},
	pinnedBottomRowData: rowData,
	rowSelection: 'single',
	onSelectionChanged: tabelRowModification,
	onCellValueChanged: function(event) {
		var rowData = event.data;
		if (!event.node.rowPinned) {
			if (parseInt(rowData.assementMark) > parseInt(rowData.maxmark)) {
				rowData[event.colDef.field] = event.oldValue;
				event.api.applyTransaction({ update: [rowData] });
				/*Swal.fire({
					icon: "error",
					title: "Oops...",
					text: "Assessment Mark Cannot Be Greater Than Max Mark!"
				});*/
				
				toastr.error('Assessment Mark Cannot Be Greater Than Max Mark!');
			} else {
				if (parseInt(rowData.assementMark) > 0) {
					updateTotalRow(event.api);
				}
			}
		}
	},
	getRowStyle: function(params) {
		const api = params.api || params.node.gridOptions.api;
		// Ensure the grid API is available
		if (!api) {
			console.error("Grid API is not available.");
			return null;
		}
		const rowCount = api.getDisplayedRowCount();
		const isLastRow = params.node.rowIndex === rowCount - 1;
		// Skip styling for the last row
		if (params.node.rowPinned || isLastRow) {
			return null; // No style for the last row
		}
		return { background: '#ffffff !important' }; // Style for other rows
	},
};
function tabelRowModification() {
	var selectedRows = gridOptions2.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$("#deleteFactor").attr("disabled", false);
		$("#newFactor").attr("disabled", true);
	} else {
		$("#deleteFactor").attr("disabled", true);
		$("#newFactor").attr("disabled", false);
	}
}
var data2 = {};
var columnDefs1 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: false,
		width: 0,
		hide: true,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: "Assign iD",
		field: "assignid",
		width: 180,
	}, {
		headerName: "Employee id",
		field: "empId",
		cellStyle: {
			textAlign: 'left'
		},
		width: 120,
	}, {
		headerName: "Employee Name",
		field: "empName",
		cellStyle: {
			textAlign: 'left'
		},
		width: 280,
	}, {
		headerName: "Category Name",
		field: "categoryName",
		cellStyle: {
			textAlign: 'left'
		},
		width: 180,
	}];
var gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 410,
		height: 20
	},
	rowSelection: 'multiple',
};
var columnDefs3 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: false,
		width: 10,
		hide: true,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Sl No",
		field: "slno",
		width: 80,
	}, {
		headerName: "Factors",
		field: "factors",
		cellStyle: {
			textAlign: 'left'
		},
		width: 600,
	}, {
		headerName: "Max Mark",
		field: "maxmark",
		cellStyle: { textAlign: 'center' },
		width: 200,
	}, {
		headerName: "Self Assessment",
		field: "selfAssesment",
		width: 200,
		cellStyle: { textAlign: 'center' },
		editable: function(params) {
			return !params.data.selfAssesment;
		},
		valueParser: function(params) {
			const newValue = params.newValue;
			if (newValue) {
				const isAlpha = /[a-zA-Z]/.test(newValue);
				const isPercentage = newValue.includes('%');
				if (isAlpha && !isPercentage) {
					return params.oldValue;
				} else {
					const numericValue = parseFloat(newValue);
					if (!isNaN(numericValue)) {
						return newValue;
					} else {
						return params.oldValue;
					}
				}
			}
		},
		cellRenderer: function(params) {
			if (params.value === 'null' || params.value === null) {
				return ''; // Replace 'null' with an empty string
			}
			return params.value; // Display the value if it exists
		}
	}, {
		headerName: "Reporting Officer Assessment",
		field: "assementMark",
		editable: false,
		hide: true,
		cellStyle: { textAlign: 'left' },
		width: 200,
	}, {
		headerName: "Remarks",
		field: "selfRemark",
		editable: true,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.value === 'null' || params.value === null) {
				return ''; // Replace 'null' with an empty string
			}
			return params.value; // Display the value if it exists
		},
		width: 250
	},];
var gridOptions3 = {
	columnDefs: columnDefs3,
	suppressRowClickSelection: true,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 210,
		height: 10,
		singleClickEdit: true,
		stopEditingWhenCellsLoseFocus: true
	},
	rowSelection: 'multiple',
	onGridReady: function(params) {
		updateTotalRow1(params.api);
		gridApi = params.api;
	},
	onCellValueChanged: function(event) {
		var rowData = event.data;
		if (!event.node.rowPinned) {
			if (parseInt(rowData.selfAssesment) > parseInt(rowData.maxmark) || parseInt(rowData.assementMark) > parseInt(rowData.maxmark)) {
				rowData[event.colDef.field] = event.oldValue;
				event.api.applyTransaction({ update: [rowData] });
				/*Swal.fire({
					icon: "error",
					title: "Oops...",
					text: "Assessment Mark Cannot Be Greater Than Max Mark!"
				});*/
				toastr.error('Assessment Mark Cannot Be Greater Than Max Mark!');
			} else {
				if (parseInt(rowData.assementMark) > 0 || parseInt(rowData.selfAssesment) > 0) {
					updateTotalRow1(event.api);
				}
			}
		}

	},
};
function updateTotalRow1(gridApi) {
	const assementMark = calculateTotal('assementMark', gridApi);
	const selfAssessmentMark = calculateTotal('selfAssesment', gridApi);

	gridApi.setPinnedBottomRowData([{
		factors: 'Total',
		maxmark: "100",
		selfAssesment: selfAssessmentMark > 0 ? selfAssessmentMark : "",
		assementMark: assementMark > 0 ? assementMark : "",
		editable: false
	}]);
}

function addNewData() {
	gridOptions.api.deselectAll();
	$("#factorCategory").val("");
	$("#categoryId").val("");
	$("#factorId1").text("");
}


function deleteKeyFact() {
	$('.loader').show();
	var selectedRows = gridOptions.api.getSelectedRows();
	if (selectedRows) {
		var approveStatus = selectedRows[0].approvalStatus;
		var categoryId = selectedRows[0].categoryid;
		console.log("selected rows-->", selectedRows);

		if (approveStatus == "0") {
			$.ajax({
				type: "GET",
				url: "appraisal-management-delete?id=" + categoryId,
				success: function(response) {
					console.log("response-->", response)
					if (response.code == "success") {
						toastr.success(response.message);
						fetchData();
						$('.loader').hide();
					}
					else {
						toastr.error(response.message);
						$('.loader').hide();
					}
				},
				error: function() {
					$('.loader').hide();
				}
			});
		}
	}
}
var txtLen = 0;
function textCount(event) {
	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
}