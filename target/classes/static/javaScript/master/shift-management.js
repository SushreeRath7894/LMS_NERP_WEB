document.addEventListener("DOMContentLoaded", function() {
	let targetSectionR = document.querySelector('#collapseWidthR');
	let targetSectionL = document.querySelector('#collapseWidthL');
	let collapseButtonR = document.querySelector('#collapseButtonR');
	let collapseButtonL = document.querySelector('#collapseButtonL');
	let expandIconR = document.querySelector('#expandIconR');
	let expandIconL = document.querySelector('#expandIconL');
	let shrinkIconL = document.querySelector('#shrinkIconL');
	let shrinkIconR = document.querySelector('#shrinkIconR');

	collapseButtonR.addEventListener('click', function() {
		if (targetSectionR.classList.contains('expanded')) {
			targetSectionR.classList.remove('expanded');
			targetSectionL.classList.remove('hidden');
			expandIconR.classList.remove('d-none');
			shrinkIconR.classList.add('d-none');
		} else {
			targetSectionR.classList.add('expanded');
			targetSectionL.classList.add('hidden');
			expandIconR.classList.add('d-none');
			shrinkIconR.classList.remove('d-none');
		}
	});
	collapseButtonL.addEventListener('click', function() {
		if (targetSectionL.classList.contains('expanded')) {
			targetSectionL.classList.remove('expanded');
			targetSectionR.classList.remove('hidden');
			expandIconL.classList.remove('d-none');
			shrinkIconL.classList.add('d-none');
		} else {
			targetSectionL.classList.add('expanded');
			targetSectionR.classList.add('hidden');
			expandIconL.classList.add('d-none');
			shrinkIconL.classList.remove('d-none');
		}
	});
});
$(document).ready(function() {
	var dateFormat = localStorage.getItem("dateFormat");

	var today = new Date();
	today.setDate(today.getDate() + 1);
	$("#fromDateCalender").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: today,
	}).on("change", function() {
		$('#filterFromDate').val($(this).val());
	})
	$('#filterFromDate').blur(function() {
		$("#fromDateCalender").val($(this).val());
	})
	$("#fromDateCalenderNew").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: today,
	}).on("change", function() {
		$('#filterFromDateNew').val($(this).val());
	})

	$('#filterFromDateNew').blur(function() {
		$("#fromDateCalenderNew").val($(this).val());
	})

	var gridDiv = document.querySelector('#myGridShift');
	new agGrid.Grid(gridDiv, shiftOptions);

	var gridDiv1 = document.querySelector('#myGridEmployee');
	new agGrid.Grid(gridDiv1, gridOptions);

	var gridDiv2 = document.querySelector('#myGridWrongShift');
	new agGrid.Grid(gridDiv2, activityOptions);

	var gridDiv3 = document.querySelector('#myGridAllEmp');
	new agGrid.Grid(gridDiv3, activityOptionsAllEmp);
	cancelAssign();
	viewShift();
	viewAllEmployee();
	rowSelectOnchange();
});

function reset() {
	$("#quickFilter").val('');
	$("#quickFilterEmp").val('');
	$("#quickFilterWShift").val('');
	$("#quickFilterAShift").val('');
	onQuickFilterChanged('R');
	onQuickFilterChangedEmp();
	onQuickFilterChangedWShift();
	onQuickFilterChangedAShift();
}
function resetEmp() {
	$("#quickFilterEmp").val('');
	onQuickFilterChangedEmp();
}
function resetWShift() {
	$("#quickFilterWShift").val('');
	onQuickFilterChangedWShift();
}
function resetAShift() {
	$("#quickFilterAShift").val('');
	onQuickFilterChangedAShift();
}

var empIdAll = [];
var selectedOptions = {};
$(function() {

	var autocomplete1 = new SlimSelect({
		select: '#employeeDropdown',
		multiple: true,
		autocomplete: true,
		icon: "fa fa-times",
		onChange: function(key) {
			var newSelectedOptions = {};
			key.forEach(function(option) {
				var empId = option.value;
				var empName = option.text;
				var department = option.data.department;
				var designation = option.data.designation;

				newSelectedOptions[empId] = {
					empName: empName,
					department: department,
					designation: designation
				};
				
				console.log(selectedOptions)

				if (!selectedOptions[empId]) {
					empIdAll.push(empId)
					addRow(empId, empName, department, designation);
				}
			});

			for (var empId in selectedOptions) {
				if (!newSelectedOptions[empId]) {
					removeRow(empId);
					empIdAll = empIdAll.filter(id => id !== empId);
				}
			}

			selectedOptions = newSelectedOptions;

			/*   if (empIdAll.length > 0) {
				  removeValidationMessage("toHiddenDesignation");
			  } */
		},
	});
});
function addRow(empId, empIdName, department, designation) {
	var empName = empIdName.split("-")[1].trim();
	var newRow = `<tr id="row-${empId}">
	                    <td>${empId}</td>
	                    <td>${empName}</td>
	                    <td>${department}</td>
	                    <td>${designation}</td>
	                  </tr>`;
	$('#empDtlsNew').append(newRow);
}
function removeRow(empId) {
	$('#row-' + empId).remove();
}
// myGridWrongShift
var columnDefsItem = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: 'Employee Id',
		width: 120,
		field: "empId",
		pinned: 'left',

	}, {
		headerName: 'Employee Name',
		field: "empName",
		pinned: 'left',
		width: 220,
	}, {
		headerName: 'Group',
		field: "group",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Assigned Shift',
		field: "assignedShift",
		width: 140,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Current Shift',
		field: "currShift",
		width: 140,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Date',
		field: "date",
		width: 140,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Punch-In',
		field: "punchIn",
		width: 130,
	}, {
		headerName: 'punch-Out',
		field: "punchOut",
		width: 130,
	}, {
		headerName: 'Employment By',
		field: "employmentBy",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		}

	}/*, {
		headerName: 'Status',
		field: "status",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			// Create a span element to hold the status text
			var span = document.createElement('span');
			span.innerHTML = params.value;

			// Apply color based on the status
			if (params.value === 'Approved') {
				span.style.color = 'blue';
			} else if (params.value === 'Rejected') {
				span.style.color = 'red';
			} else if (params.value === 'Pending') {
				span.style.color = 'green';
			}

			return span;
		}

	}*/];
var activityOptions = {
	columnDefs: columnDefsItem,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 220,
		height: 10
	},
};
// myGridEmployee	
var columnDefs = [
	{
		headerName: 'Employee Id',
		width: 120,
		field: "empId",
		pinned: 'left',
		cellClass: function(params) {
			if (params.data.status === 'Present') {
				return 'blueClsWT';
			} else if (params.data.status === 'Absent') {
				return 'redClsWT';
			}
		}
	}, {
		headerName: 'Employee Name',
		field: "empName",
		pinned: 'left',
		width: 178
	}, {
		headerName: ' Shift',
		field: "shift",
		width: 85,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 130,
	}, {
		headerName: 'Attendance',
		field: "status",
		width: 125,
		hide:true,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			// Create a span element to hold the status text
			var span = document.createElement('span');
			span.innerHTML = params.value;

			// Apply color based on the status
			if (params.value === 'Present') {
				span.style.color = 'blue';
			} else if (params.value === 'Absent') {
				span.style.color = 'red';
			}

			return span;
		}
	}, {
		headerName: 'Date',
		field: "date",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Punch-In',
		field: "punchIn",
		width: 115,
	}, {
		headerName: 'punch-Out',
		field: "punchOut",
		width: 115,
	}, {
		headerName: 'Employment By',
		field: "employmentBy",
		width: 145,

	}, {
		headerName: 'Department',
		field: "department",
		width: 125,
	},];
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 190
	},
	//onSelectionChanged : onSelectionChanged,
};
// myGridShift
var columnDefsShift = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		maxWidth: 30,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: 'Shift',
		field: "shift",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Shift From',
		field: "shiftFrom",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Shift To',
		field: "shiftTo",
		cellStyle: {
			textAlign: 'center'
		},
	}
];
var shiftOptions = {
	columnDefs: columnDefsShift,
	rowSelection: 'single',
	onSelectionChanged: onShiftChange,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1,
		minWidth: 150
	},
};
function onShiftChange() {
	var selectedNodes = shiftOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var shift = selectedData.map(node => node.shift);

	viewEmployee(shift);
	viewWrongEmployee(shift);
}
// employee all 
var columnDefsAllEmp = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		/* checkboxSelection: function(params) {
			return params.data.rn === 1 || params.data.status === 'PENDING';
		}, */
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: 'slNo',
		width: 120,
		field: "slNo",
		pinned: 'left',
		hide: true
	}, {
		headerName: 'Employee Id',
		width: 120,
		field: "empId",
		pinned: 'left',
	}, {
		headerName: 'Employee Name',
		field: "empName",
		pinned: 'left',
		width: 180,
	}, {
		headerName: 'Efective From Date',
		field: "effectiveDt",
		width: 175,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Effective To Date',
		field: "effectiveDtTo",
		width: 160,
		hide: true,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'shift',
		field: "shift",
		width: 85,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Revise Shift',
		field: "revisedShift",
		width: 130,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 130,
	}, {
		headerName: 'Department',
		field: "department",
		width: 130,
	}, {
		headerName: 'Remarks',
		field: "remarks",
		width: 190,
		valueGetter: params => {
			const remarks = params.data.remarks;
			return (remarks && remarks !== 'null') ? window.atob(remarks) : ''; // Return decoded value or empty string
			 
		}
	}, {
		headerName: 'Status',
		field: "status",
		width: 95,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.value === 'APPROVED') {
				return '<span style="color: green; font-weight: bold;">' + params.value + '</span>';
			} else {
				return params.value;
			}
		}
	}];
var activityOptionsAllEmp = {
	columnDefs: columnDefsAllEmp,
	rowSelection: 'multiple',
	onSelectionChanged: rowSelectOnchange,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 220,
		height: 10
	},

};
function rowSelectOnchange() {
	var selectedRows = activityOptionsAllEmp.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		/*for (var i = 0; i < selectedRows.length; i++) {
			var status = selectedRows[i].status;
			if (status == "PENDING") {
				$('#swapBtn').attr('disabled', true);
				$('#approveBtn').attr('disabled', false);;
				$('#deleteBtn').attr('disabled', false);
				$('#newBtn').attr('disabled', true);
			} else if (status == "APPROVED") {
				$('#swapBtn').attr('disabled', false);
				$('#approveBtn').attr('disabled', true);
				$('#deleteBtn').attr('disabled', true);
				$('#newBtn').attr('disabled', true);
			} else {
				$('#swapBtn').attr('disabled', true);
				$('#approveBtn').attr('disabled', true);
				$('#deleteBtn').attr('disabled', true);
				$('#newBtn').attr('disabled', false);
			}
		}*/
		
		let pendingStatusCount = selectedRows?.filter(f => f.status === 'PENDING')?.length;
		let approvedStatusCount = selectedRows?.filter(f => f.status === 'APPROVED')?.length;
		if(pendingStatusCount > 0 && pendingStatusCount === selectedRows.length) {
			$('#swapBtn').attr('disabled', true);
			$('#approveBtn').attr('disabled', false);;
			$('#deleteBtn').attr('disabled', false);
			$('#newBtn').attr('disabled', true);
		} else if(approvedStatusCount > 0 && approvedStatusCount === selectedRows.length) {
			$('#swapBtn').attr('disabled', false);
			$('#approveBtn').attr('disabled', true);
			$('#deleteBtn').attr('disabled', true);
			$('#newBtn').attr('disabled', true);
		} else {
			$('#swapBtn').attr('disabled', true);
			$('#approveBtn').attr('disabled', true);
			$('#deleteBtn').attr('disabled', true);
			$('#newBtn').attr('disabled', false);
		}
	} else {
		$('#swapBtn').attr('disabled', true);
		$('#approveBtn').attr('disabled', true);
		$('#deleteBtn').attr('disabled', true);
		$('#newBtn').attr('disabled', false);
	}
}
function viewShift() {
	agGrid.simpleHttpRequest({
		url: "shift-management-view-shift"
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		shiftOptions.api.setRowData(jsonData);
		var firstRowNode = shiftOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
		$(".loader").hide();
		$("body").removeClass("overlay");
	});
}
function viewEmployee(sec) {
	$("#shift1").html(sec);
	$("#shift2").html(sec);
	$('.loader-modal').show();
	$("body").addClass("overlay");
	agGrid.simpleHttpRequest({
		url: "shift-management-employee-view?sec=" + sec
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.details;
		if (allData == "" || allData == null || allData == 'null') {
			len = 0;
			var rowData = [];
			gridOptions.api.setRowData(rowData);
			$('#totalReq').find('span').html(len);
		} else {
			len = allData.length;
			gridOptions.api.setRowData(allData);
			$('#totalReq').find('span').html(len);
		}
		$(".loader").hide();
		$("body").removeClass("overlay");
	});
}
function viewWrongEmployee(sec) {
	$(".loader").show();
	$("body").addClass("overlay");

	var today = new Date();
	var toDayDate = today.toISOString().split('T')[0];
	var dateParts = toDayDate.split("-");
	var newDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
	var fromDate = newDate;
	var toDate = newDate;

	agGrid.simpleHttpRequest({
		url: "shift-management-view-wrong-emp?sec=" + sec + "&fromDate=" + fromDate + "&toDate=" + toDate,
	}).then(function(data) {
		if (data.body == "" || data.body == null || data.body == 'null') {
			len = 0;
			$('#totalReq2').find('span').html('0');

			activityOptions.api.setRowData('');
		} else {
			var allData = JSON.parse(data.body);
			len = allData.length;
			activityOptions.api.setRowData(allData);
			$('#totalReq2').find('span').html(len);
		}
		$(".loader").hide();
		$("body").removeClass("overlay");
	});
}
function viewAllEmployee() {
	$(".loader").show();
	$("body").addClass("overlay");
	agGrid.simpleHttpRequest({
		url: "shift-management-view-allEmployees",
	}).then(function(data) {
		if (data.body == "" || data.body == null || data.body == 'null') {
			$(".loader").hide();
			$("body").removeClass("overlay");
		} else {
			var allData = JSON.parse(data.body);
			activityOptionsAllEmp.api.setRowData(allData);
			$("#swapBtn").attr("disabled",true);
			$("#newBtn").removeAttr("disabled");
			$(".loader").hide();
			$("body").removeClass("overlay");
		}
	});
}
function travelPrev(tab) {
	if (tab == 1) {
		$("#employeeListTab").addClass('active');
		$("#wrongShiftTab").removeClass('active');
		$("#assignShiftTab").removeClass('active');

		$("#empTab").addClass('active');
		$("#wrngTab").removeClass('active');
		$("#assignTab").removeClass('active');
	} else if (tab == 2) {
		$("#employeeListTab").removeClass('active');
		$("#wrongShiftTab").addClass('active');
		$("#assignShiftTab").removeClass('active');

		$("#empTab").removeClass('active');
		$("#wrngTab").addClass('active');
		$("#assignTab").removeClass('active');
	} else {
		$("#employeeListTab").removeClass('active');
		$("#wrongShiftTab").removeClass('active');
		$("#assignShiftTab").addClass('active');

		$("#empTab").removeClass('active');
		$("#wrngTab").removeClass('active');
		$("#assignTab").addClass('active');
	}
}
function travelNext(tab) {
	if (tab == 1) {
		$("#employeeListTab").removeClass('active');
		$("#wrongShiftTab").addClass('active');
		$("#assignShiftTab").removeClass('active');

		$("#empTab").removeClass('active');
		$("#wrngTab").addClass('active');
		$("#assignTab").removeClass('active');
	} else if (tab == 2) {
		$("#employeeListTab").removeClass('active');
		$("#wrongShiftTab").removeClass('active');
		$("#assignShiftTab").addClass('active');

		$("#empTab").removeClass('active');
		$("#wrngTab").removeClass('active');
		$("#assignTab").addClass('active');
	} else {
		$("#employeeListTab").addClass('active');
		$("#wrongShiftTab").removeClass('active');
		$("#assignShiftTab").removeClass('active');

		$("#empTab").addClass('active');
		$("#wrngTab").removeClass('active');
		$("#assignTab").removeClass('active');
	}
}
function cancelAssign() {
	$(".formValidation").remove();
	$("#assignShiftForm").hide();
	$("#myGridAllEmp").show();
	$("#aShiftSerachSecId").removeClass('d-none');
	$("#swapShiftForm").hide();
	$("#newBtn, #swapBtn, #approveBtn, #deleteBtn").show();
	empIdAll = [];
	selectedOptions = {};
}
function cancelSwap() {
	$(".formValidation").remove();
	$("#assignShiftForm").hide();
	$("#swapShiftForm").hide();
	$("#myGridAllEmp").show();
	$("#newBtn, #swapBtn, #approveBtn, #deleteBtn").show();
	$("#aShiftSerachSecId").removeClass('d-none');
}
function AssignDetails() {
	$("#empDtlsNew").empty();
	$(".formValidation").remove();
	$("#assignShiftForm").show();
	$("#myGridAllEmp").hide();
	$("#aShiftSerachSecId").addClass('d-none');
	$("#newBtn, #swapBtn, #approveBtn, #deleteBtn").hide();
	$("#filterFromDateNew").val('');
	$("#remarks").val('');
	$("#shiftNew").val('');
	$("#empDtlsNew").val('');

	getEmpList();


}
function swapDetails() {
	$(".formValidation").remove();
	$("#swapShiftForm").show();
	$("#myGridAllEmp").hide();
	$("#newBtn, #swapBtn, #approveBtn, #deleteBtn").hide();
	$("#filterFromDateNew").val('');
	$("#remarks").val('');
	$("#shiftNew").val('');
	$("#aShiftSerachSecId").addClass('d-none');
	swapDetails1();
}
function getEmpList() {
	var options = '';
	$("#employeeDropdown").html('');
	agGrid.simpleHttpRequest({
		url: 'employee-shift-assign-emp-dropDown'
	}).then(function(data) {
		$('.loader-modal').hide();
		var jsonData = JSON.parse(data.body);
		if (jsonData != null) {
			jsonData.forEach(function(rowNode) {
				options += '<option value="' + rowNode.empId + '" data-department="' + rowNode.department + '" data-designation="' + rowNode.designation + '">' + rowNode.empId + '-' + rowNode.empName + '</option>';
			});
			$("#employeeDropdown").html(options);
		} else {
			$("#employeeDropdown").html('');
		}
	});
}
function changeDateFormat(date) {
	var dateParts = date.split("-");
	return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
}
function swapDetails1() {
	$(".formValidation").remove();
	var selectedRows = activityOptionsAllEmp.api.getSelectedRows();
	var rowCount = 0;
	var data = [];
	var today = new Date();
	
	today.setDate(today.getDate() + 1);
	var toDayDate = today.toISOString().split('T')[0];
	var newDate = changeDateFormat(toDayDate);

	$("#filterFromDate").val(newDate);
	$('#groupRemarks').val('');
	$("#groupTypeModal").val('');
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	$('#empDtls').empty();
	for (var i = 0; i < selectedRows.length; i++) {
		var row = selectedRows[i];
		var currDate = new Date();
		var effDateList = row?.effectiveDt?.split('-');
		var effDate = effDateList[2]+'-'+effDateList[1]+'-'+effDateList[0];
		var newRow = `<tr>
		                        <td>${row.empId}</td>
		                        <td>${row.empName ? row.empName : ''}</td>
		                        <td>${row.department ? row.department : ''}</td>
		                        <td>${row.designation ? row.designation : ''}</td>
		                        <td>${(new Date(effDate) <= currDate) ? row.revisedShift : row.shift}</td>
		                        <td>${row.effectiveDt}</td>
		                      </tr>`;
		$('#empDtls').append(newRow);
	}
}
function saveAssign() {
	var validation = true;
	var dataAll = [];

	if (empIdAll.length === 0) {
		toastr.error('Employee required');
		return
	}
	
	if($("#filterFromDateNew").val() == null || $("#filterFromDateNew").val() == '') {
		toastr.error('Date required');
		return;
	}

	var groupData = $("#shiftNew").val();

	if (groupData == null || groupData == "") {
		toastr.error('Shift required');
		return;
	}

	for (var i = 0; i < empIdAll.length; i++) {
		var obj = {};
		obj.empId = empIdAll[i];
		obj.currentShift = $("#shiftNew").val();
		obj.revisedShift = $("#shiftNew").val();
		obj.revisedDate = $("#filterFromDateNew").val();
		obj.shiftRemarks = window.btoa($("#remarks").val());
		dataAll.push(obj);
	}
	if (validation) {
		addshiftDetails(dataAll);
	}

}
function addshiftDetails(data) {
	$(".loader").show();
	$.ajax({
		type: "POST",
		url: "shift-management-add-shift",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {
				$(".loader").hide();
				toastr.success("Data saved successfully");
				/*$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
				viewAllEmployee();
				cancelAssign();
			} else {
				$(".loader").hide();
				toastr.error("Data saved successfully");
				/*$("#messageParagraph").text("Something Wnt Wrong.");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
			}
		},
		error: function(data) {
		}
	})
}
function approveShift() {
	var id = "";
	var selectedRows = activityOptionsAllEmp.api.getSelectedRows();

	if (selectedRows.length > 0) {
		selectedRows.forEach(function(row) {
			id += '"' + row.slNo + '",';
		});
		id = id.slice(0, -1);
		var finalId = "(" + id + ")";
	} else {
	}
	approveAll(finalId, "APPROVE");
}
function deleteShift() {
	var id = "";
	var selectedRows = activityOptionsAllEmp.api.getSelectedRows();

	if (selectedRows.length > 0) {
		selectedRows.forEach(function(row) {
			id += '"' + row.slNo + '",';
		});
		id = id.slice(0, -1);
		var finalId = "(" + id + ")";
	} else {
	}
	approveAll(finalId, "DELETE");

}
function approveAll(slNo, flag) {
	if (slNo) {
		$.ajax({
			type: "GET",
			url: "shift-management-assign-approve?slNo=" + slNo + "&flag=" + flag,
			async: false,
			success: function(response) {
				if (response.code == "success") {
					$(".loader").hide();

					if (flag == "APPROVE") {
						toastr.success("Shift approved successfully");
						/*$("#messageParagraph").text("Shift Approved Successfully");
						$("#msgOkModal").removeClass("btn btn-primary edit-btn");
						$("#msgOkModal").addClass("btn btn-primary edit-btn");
						$("#msgModal").modal('show');
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');	*/
						$('#swapBtn').attr('disabled', true);
						$('#approveBtn').attr('disabled', true);
						$('#deleteBtn').attr('disabled', true);
						$('#newBtn').attr('disabled', false);
					} else {
						toastr.success("Shift deleted successfully");
						/*$("#messageParagraph").text("Shift Deleted Successfully");
						$("#msgOkModal").removeClass("btn btn-primary edit-btn");
						$("#msgOkModal").addClass("btn btn-primary edit-btn");
						$("#msgModal").modal('show');*/
						$('#swapBtn').attr('disabled', true);
						$('#approveBtn').attr('disabled', true);
						$('#deleteBtn').attr('disabled', true);
						$('#newBtn').attr('disabled', false);
					}
					viewAllEmployee();
					cancelAssign();
					//viewEmployee(shift);
				} else {
					$(".loader").hide();
					toastr.error("Something went wrong. Try after some time.");
					/*$("#messageParagraph").text("Something Went Wrong. Try After some time.");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
				}
			},
			error: function(data) {
			}
		});
	}
}
function saveSwap() {
	var validation = true;
	var groupData = $("#groupTypeModal").val();

	if (groupData == null || groupData == "") {
		toastr.error('Shift required');
		return;
	}

	var selectedRows = activityOptionsAllEmp.api.getSelectedRows();
	var rowCount = 0;

	var data = [];
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});

	for (var i = 0; i < selectedRows.length; i++) {
	
		var currDate = new Date();
		var effDateList = selectedRows[i]?.effectiveDt?.split('-');
		var effDate = effDateList[2]+'-'+effDateList[1]+'-'+effDateList[0];
	
		var obj = {};
		obj.empId = selectedRows[i].empId;
		obj.currentShift = (new Date(effDate) <= currDate) ? selectedRows[i].revisedShift : selectedRows[i].shift;
		obj.revisedShift = $("#groupTypeModal").val();
		obj.revisedDate = $("#filterFromDate").val();
		obj.groupRemarks = window.btoa($("#groupRemarks").val());
		data.push(obj);
	}

	if (validation) {
		addshiftDetails(data);
	}
}

function onQuickFilterChanged(id='') {

	if(document.getElementById('quickFilter').value || id == 'R') {
		shiftOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
		var firstRowNode = shiftOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		$('#totalReq').find('span').html(shiftOptions.api.getModel().getRowCount());
	}
}

function onQuickFilterChangedEmp() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilterEmp').value);
}

function onQuickFilterChangedWShift() {
	activityOptions.api.setQuickFilter(document.getElementById('quickFilterWShift').value);
}
function onQuickFilterChangedAShift() {
	activityOptionsAllEmp.api.setQuickFilter(document.getElementById('quickFilterAShift').value);
}

function enterKeyToGo(event) {
    if (event.key === "Enter" || event.keyCode === 13) {
        onQuickFilterChanged()
    }
}
function enterKeyToGoEmp(event) {
    if (event.key === "Enter" || event.keyCode === 13) {
        onQuickFilterChangedEmp()
    }
}
function enterKeyToGoWShift(event) {
    if (event.key === "Enter" || event.keyCode === 13) {
        onQuickFilterChangedWShift()
    }
}
function enterKeyToGoAShift(event) {
    if (event.key === "Enter" || event.keyCode === 13) {
        onQuickFilterChangedAShift()
    }
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
function closeKey() {
	shiftOptions.api.setQuickFilter(null); $('#quickFilter').val('');
	document.getElementById('closeKey').style.display = 'none';
	$('#totalAsset').find('span').html(shiftOptions.api.getModel().getRowCount());
}