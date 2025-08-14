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
$(document).ready(function () {

	const urlParams = new URLSearchParams(window.location.search);
		type = urlParams.get('id');
		
		if(type == null || type == 'null') {
			type = '';
		}
		
		if(type == 'self-service') {
			$("#editLetter,#deleteLetter,#downloadLetter").hide()
		}
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var data = []
	gridOptions.api.setRowData(data);
	viewData();
	cancelLetter();
	$('#editNotice').attr("disabled", true);
	$('#sendMail').attr("disabled", true);
	$('#replyBtn').attr("disabled", true);
	$('#sendOffline-btn').attr("disabled", true);
	$('#deleteBtn').attr("disabled", true);
	$('#empAutoSearch').hide();
	$('#addPageEmpName').hide();
	CKEDITOR.replace('noticeDescription', {
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
	var dateFormat = localStorage.getItem("dateFormat");
	$("#dateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#date').val($(this).val());
	})
	$('#date').blur(function() {
		$("#dateCalendar").val($(this).val());
	})
	
		$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			quickFilterGrid(gridOptions);
		}
	});
});

function onQuickFilterChanged() {
if(document.getElementById('quickFilter').value) {
			gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptions.api.getDisplayedRowAtIndex(0).setSelected(true);
		}

}
function reset(){
	let data = $("#quickFilter").val('');
	if(data!=null && data!=""){
		$("#quickFilter").val('');
		gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptions.api.getDisplayedRowAtIndex(0).setSelected(true);
	}
}
var columnDefs = [
	{
	   headerCheckboxSelection: false,
	   headerCheckboxSelectionFilteredOnly: true,
	   checkboxSelection: true,
	   width: 10,
	   sortable: false,
	   filter: false,
	   resizable: true,
	   pinned: 'left',
	},{
	   headerName: 'Letter ID',
	   field: "noticeId",
	   width: 150,
	   pinned: 'left',
	},{
	   headerName: 'Employee ID',
	   field: "empId",
	   width: 80,
	   pinned: 'left',
	  
	}, {
	   headerName: 'Employee Name',
	   field: "empName",
	   width: 160,
	   pinned: 'left',
	}, {
		headerName: 'Absent From',
		field: "start_date",
		width: 100,
		cellStyle: {
		      textAlign: 'center'
	   },
	},{
	   headerName: 'Reason Of Letter',
	   field: "reasonOfNotice",
	   width: 160,
	}, {
	   headerName: 'Notice Date',
	   field: "noticeDate",
	   width: 100,
	   cellStyle: {
	      textAlign: 'center'
	   },
	}, {
	   headerName: 'Letter Type',
	   field: "noticeType",
	   width: 140,
	}, {
	   headerName: 'Letter Type',
	   field: "noticeTypeId",
	   width: 140,
	   hide: true
	}, {
	   headerName: "Mode",
	   field: "sendMode",
	   width: 80,
	}, {
	   headerName: 'Letter',
	   field: "noticeAttachment",
	   width: 90,
	   cellStyle: {
	      textAlign: 'center'
	   },
	   cellRenderer: function (params) {
		   if(params.data.noticeId){
			   var s = "";
			      s = ' <a href="#" class="grn-btn" onclick="descriptionDownload(\'' + params.data.noticeId + '\')"><i class="fas fa-file-pdf"></i> Preview </a>';
			      return s;
			}else{
				 return '';
			}
	   }, 
	},/* {
	   headerName: 'Employee Response',
	   field: "employee_reason",
	   width: 190,
	   valueGetter: params => {
		    const remarks = params.data.employee_reason;
		    return (remarks && remarks !== 'null') ? base64ToUtf8(remarks) : ''; // Return decoded value or empty string
		}
	}, {
	   headerName: 'Response Date',
	   field: "replyDate",
	   width: 100,
	   cellStyle: {
	      textAlign: 'center'
	   },
	}, {
	   headerName: 'Employee Attachment',
	   field: "document",
	   width: 90,
	   cellStyle: {
	      textAlign: 'center'
	   },
	   cellRenderer : function(params) {
			if(params.data.document){
				return '<a id="id" onclick=viewEmployeeAttachment("'
				+ params.data.noticeId + '") href="javascript:void(0)">'
				+ '<i class="bi bi-arrow-right-circle"> View</i>'
				+ '</a>';
			}else{
			}
		}
	}, {
	   headerName: 'Remarks By HR',
	   field: "remarksByHR",
	   width: 140,
	   valueGetter: params => {
		    const remarks = params.data.remarksByHR;
		    return (remarks && remarks !== 'null') ? base64ToUtf8(remarks) : ''; // Return decoded value or empty string
		}
	}, {
	   headerName: 'Closed Date',
	   field: "closeDate",
	   width: 100,
	   cellStyle: {
	      textAlign: 'center'
	   },
	}, */{
	   headerName: 'Status',
	   field: "noticeStatus",
	   width: 100,
	}, {
	   headerName: 'Letter Description',
	   field: "noticeDesc",
	   width: 140,
	   hide: true
	}, {
	   headerName: 'Designation',
	   field: "designation",
	   width: 140,
	   hide: true
	}, {
	   headerName: 'Email Id',
	   field: "emailID",
	   width: 180,
	   hide: true
	}, {
	   headerName: 'Mobile',
	   field: "mobile",
	   width: 100,
	   hide: true,
	   cellStyle: {
	      textAlign: 'center'
	   },
	}, {
	   headerName: 'manager',
	   field: "manager",
	   width: 100,
	   hide: true,
	}];
const gridOptions = {
   columnDefs: columnDefs,
   defaultColDef: {
      sortable: true,
      filter: true,
      resizable: true,
      width: 150,
      height: 10
   },
   rowSelection: 'single',
   suppressRowClickSelection: true,
   pagination : true,
   paginationPageSize: 15,
   onSelectionChanged: onSelectionChanged,
};
var globId;
var employeeIdNot;
var employeeNameNot;
var employeeDeptNot;
var employeeDesgnNot;
var employeeManagerNot;
var employeeMobileNot;
var employeeAbsentFrom;
function onSelectionChanged() {
   var selectedRows = gridOptions.api.getSelectedRows();
   var rowCount = 0;
   selectedRows.forEach(function (selectedRow, index) {
      rowCount = rowCount + 1;
   });
   var status = selectedRows.map(node => node.noticeStatus);
   var notId = selectedRows.map(node => node.noticeId);
   var employee = selectedRows.map(node => node.empId);
   var employeeName = selectedRows.map(node => node.empName);
   var employeeDept = selectedRows.map(node => node.department);
   var employeeDesgn = selectedRows.map(node => node.designation);
   var employeeManager = selectedRows.map(node => node.manager);
   var employeeMobile = selectedRows.map(node => node.mobile);
   var employeeStartDate = selectedRows.map(node => node.start_date);
   
   globId = notId[0];
   employeeIdNot = employee[0];
   employeeNameNot = employeeName[0];
   employeeDeptNot = employeeDept[0];
   employeeDesgnNot =  employeeDesgn[0];
   employeeManagerNot = employeeManager[0];
   employeeMobileNot = employeeMobile[0];
   employeeAbsentFroms = employeeStartDate[0];
	   
	var mailStatus = selectedRows.map(node => node.sendMode);
	var noticeStatus = selectedRows.map(node => node.noticeStatus);
	var closeDate = selectedRows.map(node => node.closeDate);
	if (rowCount > 0) {
		$("#employeeNameTop").text(employeeName);
		$("#employeeNameTop1").text(employeeName);
		editLetterData(notId);
	    if (mailStatus[0] == "EMAIL" || mailStatus[0] == "OFFLINE" ) {
	
	        $('#editLetter, #sendMail, #deleteLetter').hide();
	    	$('#replyBtn').attr("disabled", false);
			cancelMail();
		}else{
			$("#downloadLetter").show();
			 if(type!='self-service') {
			 	$("#editLetter, #deleteLetter").show();
			 }
			sendMail();
		} 
 		if (noticeStatus[0] == "CLOSE") {
			$('#replyBtn').attr("disabled",true);
			
		}
	
	} else {
		$('#editLetter, #sendMail, #sendOffline-btn,#deleteLetter,#downloadLetter').hide();
	    $('#replyBtn').attr("disabled", true);
	    $('#newNotice').attr("disabled", false);
	    
	    $("#employeeName").val('');
	    $("#reportingManager").val('');
		$("#letterId1").html('');
		$("#letterId2").html('');
	    $("#empMobile").val('');
	    $("#empDept").val('');
	    $("#empDesgn").val('');
	    $("#noticeSubject").val('');
	    $("#noticeReason").val('');
	    $("#empNameAuto").val('');
	    $("#employeeId").val('');
	    $("#noticeType").val('');
	    CKEDITOR.instances.noticeDescription.setData("");
	    $("#empAutoSearch").show();
		$("#noticeMaster").show();
		$("#headerDiv").hide();
		$("#noticeID").html('');
		$("#noticeId").val('');
		$("#empNames").val('');
		$("#empMails").val('');
		$("#empCc").val('');
		$("#emailBody").val('');
		$("#attachMail").val('');
		$("#employeeNameTop,#employeeNameTop1").text('');
   } 
   
   if(type == 'self-service') {
			$("#newLetter,#editLetter, #newLetter, #next1, #deleteLetter,#sendBtn").hide()
		}
}