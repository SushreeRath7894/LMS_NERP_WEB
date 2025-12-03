let type = '';
$(document).ready(function() {

    const urlParams = new URLSearchParams(window.location.search);
    type = urlParams.get('id');

    if (type == null || type == 'null') {
        type = '';
    }

    $('#empName').select2({
        placeholder: "Select",
        allowClear: true
    });
    $("#patientTypeBox").hide();

    $(".max-btn").on("click", function() {
        var parentCol = $(this).closest(".col-md-6, .col-md-12");

        if (parentCol.hasClass("col-md-6")) {
            parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
            parentCol.siblings(".col-md-6").hide().fadeOut(500);
        } else {
            parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
            parentCol.siblings(".col-md-6").show().fadeIn(500);
        }
    });
    $("#quickFilter").on("keydown", function(event) {
        if (event.key === "Enter" || event.which === 13) {
            event.preventDefault();
            onQuickFilterChanged();
        }


    });

    if (type == 'self-service') {
        let empId = $("#sessionId").val();
        $('#empName').val(empId).trigger('change').attr('disabled', true);
    }

    var gridDivm = document.querySelector('#myGridAttendance');
    new agGrid.Grid(gridDivm, attendanceGridOptions);

    var dateFormat = localStorage.getItem("dateFormat");
    $("#dateCalendar").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
        maxDate: 0,
    }).on("change", function() {
        $('#fromDate').val($(this).val());
    })
    $('#fromDate').blur(function() {
        $("#dateCalendar").val($(this).val());
    })


    var dateFormat = localStorage.getItem("dateFormat");
    $("#dateCalendar1").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
        maxDate: 0,
    }).on("change", function() {
        $('#toDate').val($(this).val());
    })
    $('#toDate').blur(function() {
        $("#dateCalendar1").val($(this).val());
    })
    // Time
    $("#toDateCalendarTime").datetimepicker({
        format: 'H:i:s',
        closeOnDateSelect: false,
        timepicker: true,
        datepicker: false,
        step: 15,
        formatTime: 'H:i:s'
    }).on("change", function() {
        $('#time').val($(this).val());
    })

    $('#time').blur(function() {
        $("#toDateCalendarTime").val($(this).val());
    })

    $("#punchOutCalendarTime").datetimepicker({
        format: 'H:i:s',
        closeOnDateSelect: false,
        timepicker: true,
        datepicker: false,
        step: 15,
        formatTime: 'H:i:s'
    }).on("change", function() {
        $('#punchOutTime').val($(this).val());
    })

    $('#punchOutTime').blur(function() {
        $("#punchOutCalendarTime").val($(this).val());
    })


    var currDate = getCurrentDate();
    $("#fromDate").val(currDate);
    $("#toDate").val(currDate);

    getAllEmployeeAttendance();
});

function getCurrentDate() {
    let today = new Date();
    let year = today.getFullYear();
    let month = String(today.getMonth() + 1).padStart(2, '0');
    let day = String(today.getDate()).padStart(2, '0');
    return day + '-' + month + '-' + year;
}

var attendanceColumnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        maxWidth: 30,
        sortable: false,
        filter: false,
        resizable: true,
    }, {
        headerName: "ID",
        field: "employee_Id",
    }, {
        headerName: "Name",
        field: "name",
    },
    {
        headerName: "Date",
        field: "date",
    },
    {
        headerName: "Punch In",
        field: "punch_In",
    }, {
        headerName: "Punch Out",
        field: "punch_out",
    }, {
        headerName: "Shift",
        field: "shift",
    },
    /* {
     	headerName: "Total Hours",
     	field: "total_hours",
     }, */
    {
        headerName: "Status",
        field: "status",
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
    },{
        headerName: "Process Status",
        field: "processStatus",
    }
    
];

var attendanceGridOptions = {
    columnDefs: attendanceColumnDefs,
    rowSelection: 'single',
    pagination: true,
    paginationPageSize: 15,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 100
    },
    onSelectionChanged: onSelectionChanged,
};

function onSelectionChanged() {

    var selectedRows = attendanceGridOptions.api.getSelectedRows();

    if (selectedRows && selectedRows.length > 0) {
    
    	let empId = selectedRows[0].employee_Id;
    	let date = selectedRows[0].date;
    	let punchInDt = selectedRows[0].punch_In;
    	let punchOutDt = selectedRows[0].punch_out;
    	
    	let dArray = date.split('-');
    	
    	let targetDate = new Date(dArray[2],(dArray[1]-1),dArray[0]);
		let currentDate = new Date();
		let currentYmd = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());
    	
    	if(currentYmd.getTime() === targetDate.getTime()) {
    		if(punchInDt && punchOutDt) {
	    		$("#punchInBtn,#punchOutBtn").hide();
	    	} else if(punchInDt && !punchOutDt) {
	    		$("#punchOutBtn").show();
	    		$("#punchInBtn").hide();
	    	} else if(!punchInDt && !punchOutDt) {
	    		$("#punchInBtn").show();
	    		$("#punchOutBtn").hide();
	    	}
    	}
    	
        
    } else {
        $("#punchInBtn,#punchOutBtn").hide();
    }

}

function getCurrentDate() {
    const date = new Date();
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
}

function getTimeInMilliseconds(timeStr) {
    const [hours, minutes] = timeStr.split(':').map(Number);
    return hours * 60 * 60 * 1000 + minutes * 60 * 1000;
}

function getCurrentTimeInMilliseconds() {
    const now = new Date();
    const hours = now.getHours();
    const minutes = now.getMinutes();
    return hours * 60 * 60 * 1000 + minutes * 60 * 1000;
}

function getAllEmployeeAttendance() {

	$("#punchInBtn,#punchOutBtn").hide();

    const fromDate = $("#fromDate").val();
    const toDate = $("#toDate").val();
    const empid = $("#empName").val();
    let shift = "";

    $(".loader").show();
    agGrid.simpleHttpRequest({
        url: "master-attendance-view?fromDate=" + fromDate + "&toDate=" + toDate + "&empid=" + empid + "&shift=" + shift
    }).then(function(response) {
        $(".loader").hide();
        var jsonData = JSON.parse(response.body);
        var allData = jsonData.attendanceDetails;

        if (allData && allData.length > 0) {
            attendanceGridOptions.api.setRowData(allData);
        } else {
            attendanceGridOptions.api.setRowData([]);
        }

    });
}

function reset() {
    var currDate = getCurrentDate();
    $("#fromDate").val(currDate);
    $("#toDate").val(currDate);
    if(type!=='self-service') {
    	$("#empName").val("").trigger('change');
    }
    
    getAllEmployeeAttendance();
}


function downloadExcelFromGrid() {


    var selectedHeaders = ['ID', 'Name', 'Date', 'Punch In', 'Punch Out', 'Shift', 'Status'];

    var fromDate = $("#fromDate").val() || "FromDate";
    var toDate = $("#toDate").val() || "ToDate";
    var fileName = `Employee_Attendance_(${fromDate}_To_${toDate}).xlsx`;

    if (!attendanceGridOptions || !attendanceGridOptions.api) {
        console.error("Grid options are not initialized.");
        return;
    }

    var rowData = [];
    var columns = attendanceGridOptions.columnApi.getAllColumns();
    var fieldMap = {};

    columns.forEach(function(col) {
        var colDef = col.getColDef();
        if (colDef.headerName && colDef.field) {
            fieldMap[colDef.headerName] = colDef.field;
        }
    });
    attendanceGridOptions.api.forEachNodeAfterFilterAndSort(function(node) {
        var data = {};
        selectedHeaders.forEach(function(header) {
            var field = fieldMap[header];
            data[header] = field ? node.data[field] || '' : '';
        });
        rowData.push(data);
    });

    // Add total row
    rowData.push({
        /*"Employee ID": "",
        "Employee Name": "",*/
        "Date": "",
        "Punch In": "",
        "Punch Out": "",
        "Shift": "",
        "Status": "",
    });

    var ws = XLSX.utils.json_to_sheet(rowData, {
        header: selectedHeaders,
        skipHeader: false
    });
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Employee Attendance');
    XLSX.writeFile(wb, fileName);
}

function switchAttendanceDiv() {
    $("#assignAttendanceForm").show();
    $("#myGridAttendance").hide();

    $("#attendanceBtn").hide();
    $("#download").hide();

    let rowIndex = 0;
    rowIndex = attendanceGridOptions.api.getSelectedNodes()[0].rowIndex;

    let selectedNodes = attendanceGridOptions.api.getSelectedNodes();
    let selectedData = selectedNodes.map(node => node.data);
    let empId = selectedData.map(node => node.employee_Id);
    let empName1 = selectedData.map(node => node.name);
    let Date = selectedData.map(node => node.date);
    let Shift = selectedData.map(node => node.shift);
    let punchIn = selectedData.map(node => node.punch_In);
    let punchOut = selectedData.map(node => node.punch_out);
    $("#empId").val(empId);
    $("#empName1").val(empName1[0]);
    $("#date").val(Date);
    $("#shift").val(Shift);
    $("#time").val(punchIn);
    $("#punchOutTime").val(punchOut);

    $("#rowIndexGrid").val(rowIndex);

}

function cancelAssign() {
    $("#assignAttendanceForm").hide();
    $("#myGridAttendance").show();

    $("#attendanceBtn").show();
    $("#download").show();

    $("#empId").val(null);
    $("#empName1").val(null);
    $("#date").val(null);
    $("#shift").val(null);
    $("#time").val(null);
    $("#punchOutTime").val(null);
}

function getCurrentTime() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
}

function punchInOutUser(p) {

	var selectedRows = attendanceGridOptions.api.getSelectedRows();
	
	if(selectedRows && selectedRows.length) {
		let obj = {
			empId : selectedRows[0].employee_Id,
			date: selectedRows[0].date,
			type: p,
			time: getCurrentTime()
		};
		
		console.log(obj)
		$(".loader").show();
		$('body').addClass('overlay');
		
		$.ajax({
            type: "POST",
            url: "master-attendance-add",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function(response) {
            
            	$(".loader").hide();
				$('body').removeClass('overlay');
            	
                if (response.code == "success") {
                    getAllEmployeeAttendance();
                    if(p == 'PI') {
                    	toastr.success("Punched-in Successfully");
                    } else {
                    	toastr.success("Punched-out Successfully");
                    }
                    
                } else {
                    $(".loader").hide();
					$('body').removeClass('overlay');
                }

            },
            error: function(data) {
                $(".loader").hide();
				$('body').removeClass('overlay');
                console.log(data);
            }
        })
		
		
	} else {
		toastr.error("Select An Employee");
		return;
	}
	
	
}