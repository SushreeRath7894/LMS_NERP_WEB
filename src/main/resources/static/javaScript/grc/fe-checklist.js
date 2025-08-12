$(function() {

	viewHomePage();
	viewChecklistData();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDiv = document.querySelector('#myNewGrid');
	new agGrid.Grid(gridDiv, gridOptionsNew);
	/*  const currentMonth = ("0" + (new Date().getMonth() + 1)).slice(-2);
	 $("#monthDropdown").val(currentMonth); */
 
	$("#myGrid").show();
	$('#deleteChecklist').prop('disabled', true);
	$('#approveChecklist').prop('disabled', true);

});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})


//updating grid with date fields
// Function to update grid dates
	function updateGridDates() {
		const refillingDate = $("#refillingDate").val();
		const nextDueDate = $("#nextDueDate").val();
		const inspectionDate = $("#inspectionDate").val();

		const updatedData = gridOptionsNew.api.getDisplayedRowCount();

		for (let i = 0; i < updatedData; i++) {
			const rowNode = gridOptionsNew.api.getDisplayedRowAtIndex(i);
			rowNode.setDataValue('refillingDate', refillingDate);
			rowNode.setDataValue('nextDueDate', nextDueDate);
			rowNode.setDataValue('lastDateInspection', inspectionDate);
		}
	}
//Updating The Next Due Date 	
	function setNextDueDate(refillingDate) {
		var dateParts = refillingDate.split('-');
		var refillingDay = parseInt(dateParts[0]);
		var refillingMonth = parseInt(dateParts[1]) - 1;
		var refillingYear = parseInt(dateParts[2]);

		var nextDueDate = new Date(refillingYear + 1, refillingMonth, refillingDay - 1);
		var formattedNextDueDate = ('0' + nextDueDate.getDate()).slice(-2) + '-' +
			('0' + (nextDueDate.getMonth() + 1)).slice(-2) + '-' + nextDueDate.getFullYear();

		$("#nextDueDate").val(formattedNextDueDate);
	}
 
// column Defs
 
var columnDefs = [
	 {
        headerCheckboxSelection: false,  
        checkboxSelection: true,         
        width: 20                          
    },

	{
    headerName: 'id',
    field: "checklistId",
    width: 150,
    cellStyle: { textAlign: 'center' },
    cellRenderer: function(params) {
        let iconHtml = '';
        if (params.data.approveStatus == 1) {
            iconHtml = '<i class="bi bi-view-list"></i>';   
        } else {
            iconHtml = '<i class="bi bi-pencil-square"></i>';   
        }

        if (params.data.checklistId) {
            return '<a id="id" onclick="editChecklist(\'' 
                + params.data.checklistId + '\', ' 
                + params.data.approveStatus + ')" href="javascript:void(0)">'
                + params.data.checklistId + '</a> ' + iconHtml;
        } else {
            return '<a id="id" onclick="editChecklist(\'' 
                + params.data.checklistId + '\', ' 
                + params.data.approveStatus + ')" href="javascript:void(0)">N/A</a> ' + iconHtml;
        }
    }
},
{
		headerName: 'Date',
		field: "date",
		width: 150,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Year',
		field: "year",
		width: 200,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Month',
		field: "monthName",
		width: 200,
		cellStyle: { textAlign: 'center' }
	},
	{
    headerName: 'Pdf',
    field: "pdf",
    width: 200,
    cellStyle: { textAlign: 'center' },
    cellRenderer: function(params) {
        return `<i class="bi bi-file-earmark-pdf-fill" style="cursor: pointer; font-size: 20px;" onclick="downloadPdf('${params.data.checklistId}')"></i>`;
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
				return '<span style="color: lightblue;">Approved</span>';
			} else {
				return 'Not Approved';
			}
		}
	}, {
		headerName: 'Created On',
		field: "createdOn",
		width: 250,
		cellStyle: { textAlign: 'center' }
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

function onSelectionChange() {
    var selectedRows = gridOptions.api.getSelectedRows();
    
    if (selectedRows.length > 0) {
        var approveStatus = selectedRows[0].approveStatus;

        if (approveStatus == 1) {
            $('#approveChecklist').prop('disabled', true);
        } else {
            $('#approveChecklist').prop('disabled', false);
        }

        $('#deleteChecklist').prop('disabled', false);  
        $('#newButton').prop('disabled', true);  
    } else {
		$('#newButton').prop('disabled', false); 
        $('#deleteChecklist').prop('disabled', true);
        $('#approveChecklist').prop('disabled', true);
        
    }
}



var columnDefsNew = [

	{
		headerName: 'id',
		field: "feChecklistId",
		width: 100,
		hide:true,
		cellStyle: { textAlign: 'center' },

	},
	{
		headerName: 'Location',
		field: "location",
		width: 150,
		pinned: 'left'
	},
	{
		headerName: 'Location Type',
		field: "locationType",
		width: 200,
		pinned: 'left'
	},
	{
		headerName: 'Type',
		field: 'type',
		width: 100,
		pinned: 'left'
	},
	{
		headerName: 'weight',
		field: "weight",
		width: 80,
	},
	{
		headerName: 'Date Of Refilling',
		field: "refillingDate",
		width: 150,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Next Due Date',
		field: "nextDueDate",
		width: 150,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Last Date Of inspection',
		field: "lastDateInspection",
		width: 150,
		cellStyle: { textAlign: 'center' }
	},
	{
		headerName: 'Gauge',
		field: "gauge",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'gauge', this.value)" />`;
		}
	},
	{
		headerName: 'CAP',
		field: "cap",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'cap', this.value)" />`;
		}
	},
	{
		headerName: 'Hose/Nozzel',
		field: "hose",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'hose', this.value)" />`;
		}
	},
	{
		headerName: 'Pin',
		field: "pin",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'pin', this.value)" />`;
		}
	},
	{
		headerName: 'Lock',
		field: "lock",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'lock', this.value)" />`;
		}
	},
	{
		headerName: 'Colour',
		field: "colour",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'colour', this.value)" />`;
		}
	},
	{
		headerName: 'Dust',
		field: "dust",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'dust', this.value)" />`;
		}
	},
	{
		headerName: 'Status',
		field: "status",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
	         const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.feChecklistId}', 'status', this.value)" />`;
		}
	}

];

//Grid options
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
    gridOptionsNew.api.forEachNode(function (node) {
        if (node.data.feChecklistId === id) {
            node.data[field] = value;  
        }
    });
}



function openNewPage() {
    $("#checklistId").val('');
    $("#feCheckListDiv").show();
    $("#newButton").hide();
    $("#dwnldExcel").hide();
    $("#myGrid").hide();
    $("#myNewGrid").show();
    $("#totalFeChecklist").hide();
    $('#deleteChecklist').hide();
    $('#approveChecklist').hide();
    $('#totalChecklist').hide();
     $("#inspectionDate").val('');

    var today = new Date();
    var refilllingDate = "24-07-2024";
    var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
    $("#refillingDate").val(refilllingDate);

    // Datepicker for Date of Refilling
    $("#refillingDateCalendar").datetimepicker({
        format: 'd-m-Y',
        closeOnDateSelect: true,
        timepicker: false
    }).on("change", function() {
        var refilllingDate = $(this).val();
        $('#refillingDate').val(refilllingDate);
        setNextDueDate(refilllingDate);

        setMinInspectionDate(refilllingDate);
        updateGridDates();
    });
    
    // Synchronize the refilling date field on blur
    $('#refillingDate').blur(function() {
        $("#refillingDateCalendar").val($(this).val());
    });
    
    	// Datepicker for Last Date of Inspection
	$("#inspectionDateCalendar").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false
	}).on("change", function() {
		$('#inspectionDate').val($(this).val());

		updateGridDates();
	});

	// Synchronize the inspection date field on blur
	$('#inspectionDate').blur(function() {
		$("#inspectionDateCalendar").val($(this).val());
	});

    setMinInspectionDate(refilllingDate);
    // Set the Next Due Date on page load
    setNextDueDate(refilllingDate);

    // Update the grid with the new dates
    var nextDueDate = $("#nextDueDate").val();
 
    // Check and update dates
    checkAndUpdateDates(toDate, nextDueDate);

    // Get values from the input fields
    const refillingDate = $("#refillingDate").val();
    const inspectionDate = $("#inspectionDate").val();
    const nextDueDatee=$("#nextDueDate").val();

    $.ajax({
        url: 'fe-checklist-get-master-data',
        method: 'GET',
        success: function(response) {
            try {
                const parsedData = JSON.parse(response.body);

                // Update each row with the fetched date values
                const updatedData = parsedData.map(item => {
                    return {
                        ...item,
                        refillingDate: refillingDate,
                        nextDueDate: nextDueDatee,
                        lastDateInspection: inspectionDate
                    };
                });

                console.log(updatedData);

                // Set the updated data to the grid
                gridOptionsNew.api.setRowData(updatedData);

            } catch (error) {
                console.error('Error parsing JSON:', error);
            }
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error('AJAX Error:', status, error);
        }
    });
}

function openPageForEdit(){
	
	$("#feCheckListDiv").show();
	$("#newButton").hide();
	$("#dwnldExcel").hide();
	$("#myGrid").hide();
	$("#totalFeChecklist").hide();
	$('#deleteChecklist').hide();
	$('#totalChecklist').hide();
	$('#approveChecklist').hide();

}

function viewHomePage() {
	$("#feCheckListDiv").hide();
	$("#newButton").show();
	$("#dwnldExcel").show();
	$("#myGrid").show();
	$("#totalFeChecklist").show();
    $('#deleteChecklist').show();
    $('#totalChecklist').show();
        $('#approveChecklist').show();


}
function saveFeChecklist() {
	let allRowData = [];

	var checklistId = $("#checklistId").val();
	var refillingDate = $("#refillingDate").val();
	var nextDueDate = $("#nextDueDate").val();
	var inspectionDate = $("#inspectionDate").val();
	if (!inspectionDate) {
		$("#messageParagraph").text("Please fill in the Inspection Date field.");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return; // Stop further execution if validation fails
	}

	// Split the inspection date and create a Date object
	var inspectionDateParts = inspectionDate.split("-");
	var inspectionDateObj = new Date(inspectionDateParts[2], inspectionDateParts[1] - 1, inspectionDateParts[0]);

	// Get the month and year of the inspection date
	var inspectionMonth = inspectionDateObj.getMonth();
	var inspectionYear = inspectionDateObj.getFullYear();  

	// Initialize a flag to track if a duplicate month and year is found
	let isDuplicateMonth = false;

	// Check only if checklistId is null
	if (!checklistId) {
		// Loop through each inspection date
		inspectionDates.forEach(inspectionDate => {
			const dateParts = inspectionDate.split("-");
			const dateObj = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);
			const beforeInspectionMonth = dateObj.getMonth();
			const beforeInspectionYear = dateObj.getFullYear(); // Get the year

			// Compare the months and years
			if (beforeInspectionMonth === inspectionMonth && beforeInspectionYear === inspectionYear) {
				isDuplicateMonth = true;
			}
		});

		// Show the modal if a duplicate month and year was found
		if (isDuplicateMonth) {
			$("#messageParagraph").text("You Cannot Save The Data Twice For The Same Month and Year");
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

	let checklistJsonData = {
		checklistId: checklistId,
		refillingDate: refillingDate,
		nextDueDate: nextDueDate,
		inspectionDate: inspectionDate,
		rows: allRowData
	};

	let jsonData = JSON.stringify(checklistJsonData);

	// AJAX call to save the data
	$.ajax({
		url: 'fe-checklist-save-data',
		type: 'POST',
		contentType: 'application/json',
		data: jsonData,
		success: function(response) {
			if (response.code == "success") {
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				viewHomePage();
				viewChecklistData();
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}
 
var inspectionDates=[];
var approveStsGlobal="";
function viewChecklistData() {
    $.ajax({
        url: 'fe-checklist-view',
        method: 'GET',
        success: function(response) {
            try {
                const parsedData = JSON.parse(response.body);
                var len = parsedData.length;
                approveStsGlobal = parsedData[0].approveStatus;

                for (let i = 0; i < len; i++) {
                    const inspectionDate = parsedData[i].inspectionDate;
                    inspectionDates.push(inspectionDate);
                }
                console.log("Inspection Date =====>", inspectionDates);
                $('#totalChecklist').find('span').html(len);
                console.log(parsedData);

                const monthNames = [
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                ];

                // Iterate over parsedData and replace the month number with the name
                parsedData.forEach(function(item) {
                    if (item.month && item.month >= 1 && item.month <= 12) {
                        item.monthName = monthNames[item.month - 1];
                    } else {
                        item.monthName = "Invalid Month";
                    }
                });

                // Sort parsedData in descending order based on checklistId
                parsedData.sort(function(a, b) {
                    // Compare the checklistId values, ensuring they are treated as strings
                    return b.checklistId.localeCompare(a.checklistId);
                });

                // Set the sorted data to the grid
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

function editChecklist(checklistId,approveSts) {
 
	 if (approveSts == 1) {
        $('#saveFeChecklist').prop('disabled', true);  
        $('input[name="gridInput"]').prop('disabled', true);
    } else {
        $('#saveFeChecklist').prop('disabled', false); 
        $('input[name="gridInput"]').prop('disabled', false);
    }
    $.ajax({
        url: 'fe-checklist-edit?checklistId=' + checklistId,
        method: 'GET',
        success: function(response) {
            if (response.code === "success") {
				openPageForEdit();
                var parsedBody = JSON.parse(response.body);
                var checklistData = parsedBody[0].checklistGridData; 
                    $("#checklistId").val(checklistId);
                	$("#refillingDate").val(parsedBody[0].refillingDate);
	                 $("#inspectionDate").val(parsedBody[0].lastDateInspection);
	                 $("#nextDueDate").val(parsedBody[0].nextDueDate);
                 checklistData.forEach(row => {
                    row.approveSts = approveSts;  
                });
                console.log("Checklist Data in Edit======>", checklistData);

              gridOptionsNew.api.setRowData(checklistData);
             
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX Error:', status, error);
        }
    });
}

function downloadPdf(checklistId){
	 const url = `/grc/fe-checklist-Pdf?checklistId=${checklistId}`;
    window.open(url, '_blank');

}

function deleteChecklist(){
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
		url: "fe-checklist-delete?checklistId=" + checklistId,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				viewChecklistData();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#deleteChecklist').attr("disabled", true);
				$('#approveChecklist').prop('disabled', true);
				$('#newButton').prop('disabled', false);
				$('#new').show();

			}

		},
		error: function(data) {
		}
	});
}

function checkAndUpdateDates(todayStr, nextDueDate) {
    // Parse today's date
    var parts = todayStr.split('-');
    var day = parseInt(parts[0], 10);
    var month = parseInt(parts[1], 10) - 1; // JavaScript months are 0-indexed
    var year = parseInt(parts[2], 10);
    var today = new Date(year, month, day);

    // Check if nextDueDate is provided and valid
    if (nextDueDate) {
        var nextDueParts = nextDueDate.split('-');
        var nextDueDay = parseInt(nextDueParts[0], 10);
        var nextDueMonth = parseInt(nextDueParts[1], 10) - 1;
        var nextDueYear = parseInt(nextDueParts[2], 10);
        var nextDueDateObj = new Date(nextDueYear, nextDueMonth, nextDueDay);

        // Compare the dates: if nextDueDate is today or in the past, update refillingDate and nextDueDate
        if (today >= nextDueDateObj) {
            // Update refilling date to today's date
            var formattedToday = ('0' + today.getDate()).slice(-2) + '-' +
                                 ('0' + (today.getMonth() + 1)).slice(-2) + '-' +
                                 today.getFullYear();
            $('#refillingDate').val(formattedToday); // Set refilling date to today's date

            // Set next year's due date
            var nextYearDueDate = new Date(today);
            nextYearDueDate.setFullYear(today.getFullYear() + 1);
            var formattedNextYearDueDate = ('0' + nextYearDueDate.getDate()).slice(-2) + '-' +
                                           ('0' + (nextYearDueDate.getMonth() + 1)).slice(-2) + '-' +
                                           nextYearDueDate.getFullYear();
            $('#nextDueDate').val(formattedNextYearDueDate); // Set next year's due date
        }
    }
}
function setMinInspectionDate(refillingDate) {
    // Split the refilling date string into day, month, year parts
    var dateParts = refillingDate.split('-');
    var refillingDay = parseInt(dateParts[0], 10);
    var refillingMonth = parseInt(dateParts[1], 10) - 1; // JS months are zero-indexed
    var refillingYear = parseInt(dateParts[2], 10);

    // Create a new Date object using the refilling date
    var refillingDateObj = new Date(refillingYear, refillingMonth, refillingDay);

       // Destroy the datetimepicker to reinitialize it
    $("#inspectionDateCalendar").datetimepicker('destroy');

    // Reinitialize datetimepicker with the new minDate
    $("#inspectionDateCalendar").datetimepicker({
        format: 'd-m-Y',
        closeOnDateSelect: true,
        timepicker: false,
        minDate: refillingDateObj // Pass the Date object
    });
}

function approveChecklist(){
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
		url: "fe-checklist-approve?checklistId=" + checklistId,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				viewChecklistData();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#deleteChecklist').attr("disabled", true);
				$('#approveChecklist').prop('disabled', true);
				$('#newButton').prop('disabled', false);
				$('#new').show();

			}

		},
		error: function(data) {
		}
	});
}