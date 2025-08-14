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

let type = '';

$(document).ready(function() {
	const urlParams = new URLSearchParams(window.location.search);
		type = urlParams.get('id');
		
		if(type == null || type == 'null') {
			type = '';
		}

	$(".chosen-select").chosen();
	$("#empID").attr('disabled', true);
	$("#proceedBtn").attr('disabled', true);
	$("#rejectBtn").attr('disabled', true);
	$("#paySlipDownload").attr('disabled', true);
	$("#fSettlement-btn").attr('disabled', true);
	$("#proceedBtn").attr('disabled', true);
		if(type == 'self-service') {			
			$("#deleteOnClick,#editResign,#submitResign,#draftResign,#cancelResign,#next1").hide()	
			$("#inactiveEmployee").hide()
		   }else{
			$("#draftResign,#cancelResign").hide()
			$("#inactiveEmployee").show()
		   }
			
	$("#finalModalDiv").hide();
     $("#clearResg").hide();


	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDivClearance = document.querySelector('#myGridClearance');
	new agGrid.Grid(gridDivClearance, gridOptionsClearance);
	var gridDivPaySlip = document.querySelector('#myGridPaySlip');
	new agGrid.Grid(gridDivPaySlip, gridOptionsPaySlip);
	viewExitsAgGrig();
	//cancelResign();
	cancelExit();
	cancelClearanceEmp1();
	cancelFinal();
	selectClearanceOnChange();
	CKEDITOR.instances.commentck?.on('instanceReady', function() {
		this.setReadOnly(true);
	});
	var dateFormat = localStorage.getItem("dateFormat");
	var today = new Date();
	today.setDate(today.getDate());

	$("#fromDateCalenderNew").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : today,
	}).on("change", function() {
		$('#filterFromDateNew').val($(this).val());
	})
	$('#filterFromDateNew').blur(function() {
		$("#fromDateCalenderNew").val($(this).val());
	})
	$("#clearanceBeforeCalender").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : today,
	}).on("change", function() {
		$('#clearanceBefore').val($(this).val());
	})
	$('#clearanceBefore').blur(function() {
		$("#clearanceBeforeCalender").val($(this).val());
	})
	let resignDate = "";
	$("#resignDateCalendar").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		let resignDateStr = $(this).val();
		$('#resignDate').val(resignDateStr);
		dateChange();
		let parts = resignDateStr.split('-');
		resignDate = new Date(parts[2], parts[1] - 1, parts[0]);

		console.log("resign date-->", resignDate);
		$("#releaseDateCalendar").datetimepicker('destroy');

		$("#releaseDateCalendar").datetimepicker({
			format: 'd-m-Y',
			closeOnDateSelect: true,
			timepicker: false,
			minDate: resignDate // Set new minDate
		});
	})
	$('#resignDate').blur(function() {
		$("#resignDateCalendar").val($(this).val());
	})
	$("#releaseDateCalendar").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		minDate: resignDate,
	}).on("change", function() {
		$('#releaseDate').val($(this).val());
		dateChange();
	})
	$('#releaseDate').blur(function() {
		$("#releaseDateCalendar").val($(this).val());
	})
	$("#multipleResignationTo").chosen().change(function() {
		const selectedValue = $(this).val();
		disableOptions('multipleResignationCc', selectedValue)
	});
	$("#multipleResignationCc").chosen().change(function() {
		const selectedValue = $(this).val();
		disableOptions('multipleResignationTo', selectedValue)
	});
	CKEDITOR.replace('commentck', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	$('#docTbl').on('click', '.rmv1', function() {
		openDeleteConfirm();
		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
	});


	CKEDITOR.instances.commentck?.on('instanceReady', function() {
		this.setReadOnly(true);
	});
	CKEDITOR.instances.commentck?.setReadOnly(true);

	$("#doctbodyData").css("pointer-events", "none");


});
//Exit management
const columnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		pinned: 'left',
		filter: false,
		resizable: true
	}, {
		headerName: 'Exit Id',
		field: "employeeExit",
		width: 90,
		pinned: 'left',
		/*cellRenderer : function(params) {
			if(params.data.draftSts == "APPLIED"){
				if (params.data.financeStatus == "CLEARED") {
					let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
					return '<a id="entryId" onclick=openFinalModal(decodeURIComponent(\'' + encodedLogData + '\')) href="javascript:void(0)" title="Download Certificates">'
					 + '<i class="bi bi-arrow-down">' + params.data.employeeExit + '</i>' + '</a>';
				} else {
					return '<span  style="color:#808080">' + params.data.employeeExit + '</span>';
				}
			}else{
				return '<a onclick=editResignationApplyDraft("' + params.data.employeeExit
						+ '") href="javascript:void(0)" >' + params.data.employeeExit + '</a>';
			}
		}*/
	}, {
		headerName: 'Employee Id',
		field: "empID",
		width: 90,
		pinned: 'left'
	}, {
		headerName: 'Employee Name',
		field: "empName",
		width: 120,
		pinned: 'left'
	}, {
		headerName: 'Draft Status',
		field: "draftSts",
		width: 90,
		cellRenderer: function(params) {
			if (params.data.draftSts == "APPLIED") {
				return '<div style="color:#0642f5">APPLIED</div>';
			} else if (params.data.draftSts == "DRAFTED") {
				return '<div >DRAFTED</div>';// style="color:#ff8242"
			} else {
				return '';
			}
		}
	}, {
		field: "designationId",
		hide: true
	}, {
		headerName: 'Designation',
		field: "designation",
		width: 140,
	}, {
		headerName: 'Department',
		field: "deptName",
		width: 90,
	},
	{
		headerName: 'Reason',
		field: "reason",
		width: 140,
		valueFormatter: params => decodeHtmlEntities(params.value)
	}, {
		headerName: 'Overview',
		field: "logbook",
		hide: true,
		width: 110,
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
			return '<a id="id" onclick="openOverViewModal(\'' + encodedLogData + '\')" href="javascript:void(0)">' + '<i class="bi bi-list"> Overview </i>' + '</a>';
		}
	}, {
		headerName: 'Attachment',
		field: "documentURL",
		width: 80,
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.documentURL != null && params.data.documentURL !== "null") {
				var filename = extractFilename(params.data.documentURL);
				var fileExt = getFileExtension(filename);
				var fileIcon = getFileIcon(fileExt);

				return `<a href="${params.data.documentURL}" target="_blank">
		                    <i class="${fileIcon}" style="color: #BF05FF;"></i> 
		                </a>`;
			} else if (params.data.dURL != null && params.data.dURL !== "null") {
				var filename = extractFilename(params.data.dURL);
				var fileExt = getFileExtension(filename);
				var fileIcon = getFileIcon(fileExt);

				return `<a href="${params.data.dURL}" target="_blank">
		                    <i class="${fileIcon}" style="color: #BF05FF;"></i> 
		                </a>`;
			} else {
				return "Not Available";
			}
		}

	}, {
		headerName: 'Clearance Status',
		field: "empClrncStatus",
		width: 120,
		cellStyle: {
			textAlign: 'center'
		},/*cellRenderer : function(params) {
					let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
					if(params.data.empClrncStatus == "IN PROGRESS"){
						return '<a id="id" onclick="openClearanceDetailsView(decodeURIComponent(\'' + encodedLogData + '\'),\'' +params.data.employeeExit 
							+ '\')" href="javascript:void(0)">' + '<i class="bi bi-eye-fill"> In Progress </i>' + '</a>';
					}else if(params.data.empClrncStatus == "CLEARED"){
						return '<a id="id" onclick="openClearanceDetailsView(decodeURIComponent(\'' + encodedLogData + '\'),\'' + params.data.employeeExit 
							+ '\')" href="javascript:void(0)">' + '<i class="bi bi-eye-fill"> Cleared </i>' + '</a>' + '&nbsp;&nbsp;' + '<a id="id" onclick="showFormatNoDue(\'' 
							+ params.data.employeeExit + '\',\'SHOW\')" href="javascript:void(0)">' + '<i class="bi bi-download bi-xl" style="font-size:1.2em;"></i>' + '</a>';
					}else if(params.data.empClrncStatus == "NOT INITIATED"){
						return '<a id="id" onclick="openClearanceDetailsView(decodeURIComponent(\'' + encodedLogData + '\'),\'' + params.data.employeeExit 
							+ '\')" href="javascript:void(0)">' + '<i class="bi bi-eye-fill"> Pending </i>' + '</a>';
					}else{
						return 'NOT INITIATED'
					}   
				}*/
	}, {
		headerName: 'Final Settlement status',
		field: "financeStatus",
		width: 140,
		cellStyle: {
			textAlign: 'center'
		},/*cellRenderer : function(params) {
					let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
					if(params.data.financeStatus == "INPROGRESS"){
						return '<a id="id" onclick="openFinalSettlementView(decodeURIComponent(\'' + encodedLogData + '\'),\'' + params.data.settlementId  
							+ '\')" href="javascript:void(0)">' + '<i class="bi bi-cash"> INPROGRESS </i>' + '</a>';
					}else if(params.data.financeStatus == "CLEARED"){
						   return '<a id="id" onclick="openFinalSettlementPdf(\'' + params.data.settlementId  + '\')" href="javascript:void(0)">'
				             + '<i class="bi bi-download"> Download </i>' + '</a>';
					}else{
						return 'NOT INITIATED'
					}    
				}*/
	}, {
		headerName: 'Exit status',
		field: "regStatus",
		width: 120,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.empStatus == "ACTIVE") {
				return 'PENDING';
			} else if (params.data.empStatus == "INACTIVE") {
				return 'CLEARED';
			} else {
				return '';
			}
		}
	}, {
		headerName: 'Employee status',
		field: "empStatus",
		width: 120,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.empStatus == "ACTIVE") {
				return '<div style="color:#0642f5">ACTIVE</div>';
			} else if (params.data.empStatus == "INACTIVE") {
				return '<div style="color:#ff8242">INACTIVE</div>';
			} else {
				return '';
			}
		}
	}, {
		headerName: 'Resign Date',
		field: "resignDate",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Release Date',
		field: "releaseDate",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Notice Period(In Days)',
		field: "noticePeriod",
		width: 130,
		cellStyle: {
			textAlign: 'center'
		}
	}];
const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	onSelectionChanged: onChangeFunction,
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 15,
	domLayout: '550px',
	getRowNodeId: function(data) {
		return data.employeeExit;
	},
	rowClassRules: {
		'inactive-row': function(params) {
			if (params.data.empStatus === 'INACTIVE')
				return params.data.empStatus === 'INACTIVE';
		}
	},
};

function decodeHtmlEntities(text) {
	const parser = new DOMParser();
	const doc = parser.parseFromString(text, "text/html");
	return doc.body.textContent || "";
}
function onChangeFunction() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = selectedRows.length;
	setFinalSettlementData(selectedRows);

	console.log("selected rows-1->", selectedRows);

	if (rowCount > 0) {
		
		console.log("in rowcount")
		travelPrev('1');
		if(type == 'self-service') {
			$("#inactiveEmployee,#newResign").hide()
			
		   }else{
			$("#inactiveEmployee,#newResign").show()
		   }
		var firstRow = selectedRows[0];
		
		$("#exitModalId").text(firstRow.employeeExit);
		$("#exitModalId1").text(firstRow.employeeExit);
		$("#exitModalId2").text(firstRow.employeeExit);
		$("#exitModalId3").text(firstRow.employeeExit);
		$("#exitModalId4").text(firstRow.employeeExit);
		var exitStatus = firstRow.regStatus;
		var clearanceStatus = firstRow.empClrncStatus;
		var financeStatus = firstRow.financeStatus;
		var employeeStatus = firstRow.empStatus;
		var settlementId = firstRow.settlementId;
		var draftSts = firstRow.draftSts;
		
			
		editResignationApplyDraft(firstRow.employeeExit);
		//cancelResign();
		if (employeeStatus == "INACTIVE") {
			$("#inactiveEmployee").attr('disabled', true);
			$("#editResign").hide();

		} else {
			$("#inactiveEmployee").attr('disabled', false);
			$("#editResign").show();

		}


		if (draftSts == "APPLIED") {
			$("#editResign,#deleteOnClick").hide()
			$("#deleteResign").attr('disabled', true);
			$("#initiateBtn").show();
			$("#next1").show();
			if (exitStatus == "CLEARED" || exitStatus == "INITIATED" || exitStatus == "IN PROGRESS" || exitStatus == "NOT INITIATED") {
				$("#clearanceBtn, #next2").show();
				$("#initiateResign").attr('disabled', true);
				getClearanceData(firstRow.employeeExit);
				cancelExit();
			} else {
				$("#clearanceBtn, #next2").hide();
				$("#initiateResign").attr('disabled', false);
				getInitateClearData();
				initiateResign();
			}
			if (clearanceStatus == "CLEARED") {
				$("#finalBtn, #downloadBtn, #next3").show();
				$("#downLoadInitiate").attr('disabled', false);
				if (financeStatus == 'INPROGRESS') {
					$("#downloadBtn, #next4").hide();
					openFinalSettlementView(settlementId);
					$("#downLoadFinal").attr('disabled', true);
					$("#finalSettle").attr('disabled', false);
					finalSettle();
				} else if (financeStatus == 'CLEARED') {
					$("#downloadBtn, #next4").show();
					openFinalModal();
					openFinalSettlementView(settlementId);
					$("#downLoadFinal").attr('disabled', false);
					$("#finalSettle").attr('disabled', true);
					cancelFinal();
					$("#deleteOnClick").attr('disabled', true);
				} else {
					$("#downloadBtn, #next4").hide();
					finalSettlementModal();
					$("#downLoadFinal").attr('disabled', true);
					$("#finalSettle").attr('disabled', false);
					finalSettle();
				}
			} else {
				$("#downLoadInitiate").attr('disabled', true);
				$("#finalBtn, #downloadBtn, #next3").hide();
			}
		}
		else {
			$("#editResign").attr('disabled', false);
			$("#deleteResign").attr('disabled', false);
			$("#finalBtn, #initiateBtn, #clearanceBtn, #downloadBtn").hide();
		}

		if (draftSts == "DRAFTED" && employeeStatus != "INACTIVE") {
			console.log("drafted ");
			$("#next1").hide()
			$("#submitResign").show();
			$("#deleteOnClick").show();
		}
		else {
			$("#submitResign").hide();
			if (employeeStatus == "INACTIVE" && draftSts == "DRAFTED") {
				$("#deleteOnClick").hide();
				$("#next1").hide();
			}
		}

		let noticePeriod = calculateNoticePeriod(firstRow.resignDate, firstRow.releaseDate);
		$("#expNoticePeriodModal").val(noticePeriod);
	} else {
		$("#editResign").attr('disabled', false);
		$("#deleteResign").attr('disabled', false);
		$("#finalBtn, #initiateBtn, #clearanceBtn, #downloadBtn").hide();
		$("#exitModalId").text("");
		$("#exitModalId1").text("");
		$("#exitModalId2").text("");
		$("#exitModalId3").text("");
		$("#releaseDtModal").val("");
		$("#resignationDtModal").val("");
		$("#employeeNameModal").val("");
		$("#employeeIdModal").val("");
		$("#expNoticePeriodModal").val("");
		newResign();
		$("#resignTab").addClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#resignInfoTab").addClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
	}
	
	if(type == 'self-service') {
			$("#editLetter, #newLetter, #deleteLetter,#sendBtn").hide()
		}

	//handelNavTabs();
}



function handelNavTabs(activeTabs) {
    console.log("Active Tab in handel  -->", activeTabs);

	setTimeout(() => {
    if (activeTabs.length) {
        if (activeTabs.is(":visible")) {
            console.log("Clicking active tab...");
            activeTabs[0].click(); // Trigger native click instead of jQuery's trigger
        } else {
            console.log("Active tab is hidden, making it visible...");
            activeTabs.show().tab("show"); // For Bootstrap-based tabs
        }
    } else {
        console.log("No active tab found.");
    }
	}, 500);
}


function handleAddNew() {
	gridOptions.api.deselectAll();

	var today = new Date();
	var formattedToday =
		("0" + today.getDate()).slice(-2) + "-" +
		("0" + (today.getMonth() + 1)).slice(-2) + "-" +
		today.getFullYear();

	$('#resignDateCalendar').datetimepicker({
		format: 'd-m-Y',
		timepicker: false,
		closeOnDateSelect: true,
		value: today,
		//minDate: today
	});
	$("#deleteOnClick").hide();
}


function resignDateUpdate() {
	var resignDateStr = $("#resignDate").val(); // Get resign date as a string
	var parts = resignDateStr.split('-'); // Split 'dd-mm-yyyy' format

	if (parts.length !== 3) {
		console.error("Invalid resign date format!");
		return;
	}

	var resignDate = new Date(parts[2], parts[1] - 1, parts[0]); // Convert to Date object

	$('#releaseDateCalendar').datetimepicker({
		format: 'd-m-Y',
		timepicker: false,
		closeOnDateSelect: true,
		value: resignDateStr, // Pass the formatted date string
		//minDate: resignDate // Set resignDate as the minimum selectable date
	});
}

//Exit Management clearance
const columnDefsClearance = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: params => params.data.clearanceByIds === $("#sessionId").val(), // Only show checkbox if updatedDate is "1"
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: 'Department',
		field: "clearanceDeptName",
		width: 125,
		pinned: 'left'
	}, {
		headerName: 'clearanceId',
		field: "clearanceId",
		width: 170,
		hide: true,
	}, {
		headerName: 'Employee ID',
		field: "clearanceByIds",
		pinned: 'left',
		width: 130,
	}, {
		headerName: 'Clearance Status',
		field: "clearanceStatus",
		width: 170,
		cellRenderer: function(params) {
			if (params.data.clearanceStatus == "PROCEED") {
				return '<div style="color:#0642f5">CLEARED</div>';
			} else if (params.data.clearanceStatus == "PENDING") {
				return '<div >PENDING</div>';// style="color:#ff8242"
			}
		}
	}, {
		headerName: 'Clearance Employees',
		field: "clearanceEmployees",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			let encodedLogData = window.btoa(encodeURIComponent(JSON.stringify(params.data)));
			if (params.data.clearanceEmployees == "N/A") {
				return '<a id="entryId" onclick=addClearanceEmp(\'' + encodedLogData + '\') '
					+ ' href="javascript:void(0)" title="Add EMployees">'
					+ '<i class="bi bi-plus"> Add Employees </i>'
					+ '</a>';
			} else {
				return params.data.clearanceEmployees;
			}
		}
	}, {
		headerName: 'Clearance By',
		field: "clearanceGivenBy",
		width: 150,
	}, {
		headerName: 'Clearance Before',
		field: "clearanceBefore",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Recovery Amount',
		field: "recoveryAmount",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Remarks',
		field: "clearanceComment",
		width: 180,
		cellStyle: {
			textAlign: 'center'
		}
	}];
const gridOptionsClearance = {
	columnDefs: columnDefsClearance,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	onSelectionChanged: selectClearanceOnChange,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	getRowNodeId: function(data) {
		return data.exitId;
	}
};
function selectClearanceOnChange() {
	var selectedRows = gridOptionsClearance.api.getSelectedRows();
	var rowCount = selectedRows.length;
	if (rowCount > 0) {
		var firstRow = selectedRows[0];
		var clearanceStatus = firstRow.clearanceStatus;
		$("#exitModalId4").text(firstRow.clearanceId);
		$("#releaseDate1").val(firstRow.releaseDate);
		$("#resignDate1").val(firstRow.resignDate);
		$("#employeeNameModal1").val(firstRow.clearanceEmployees);
		$("#employeeIdModal1").val(firstRow.clearanceByIds);
		
		if (clearanceStatus == "PROCEED") {
			$("#clearResg").hide();
			$("#clearResg").attr('disabled', true);
			toastr.success("Clearance Recieved Already");
		} else {
			$("#clearResg").show();
			$("#clearResg").attr('disabled', false);
			
		}
	} else {
		$("#clearResg").attr('disabled', true);
	}
	
}

var columnDefsPaySlip = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	pinned: 'left',
	filter: false,
	resizable: true

}, {
	headerName: "Employee ID",
	field: "empId",
	cellStyle: {
		textAlign: 'center'
	},
	hide: true
}, {
	headerName: "PaySlip For",
	field: "month",
	cellStyle: {
		textAlign: 'center'
	},
	width: 250,
},
{
	headerName: "From Date",
	field: "fromDate",
	cellStyle: {
		textAlign: 'center'
	},
	hide: true
},
{
	headerName: "To Date",
	field: "toDate",
	cellStyle: {
		textAlign: 'center'
	},
	hide: true
},
{
	headerName: "Payslip",
	cellStyle: {
		textAlign: 'center'
	},
	width: 250,
	cellRenderer: function(params) {
		var s = "";
		s = ' <a href="#" class="grn-btn" onclick="payslipPdfDownload(\''
			+ params.data.empId
			+ '\',\''
			+ params.data.fromDate
			+ '\',\''
			+ params.data.toDate
			+ '\')"><i class="ti ti-download"></i> Payslip</a>';
		return s;
	},
}];
var gridOptionsPaySlip = {
	columnDefs: columnDefsPaySlip,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
		height: 30
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: paySlipOnChange
};
function paySlipOnChange() {
	var selectedRows = gridOptionsPaySlip.api.getSelectedRows();
	var rowCount = selectedRows.length;
	if (rowCount > 0) {
		$("#paySlipDownload").attr('disabled', false);
	} else {
		$("#paySlipDownload").attr('disabled', true);
	}
}