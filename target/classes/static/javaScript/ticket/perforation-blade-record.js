$(function() {
    
	viewHomePage();
	viewPerforationData();
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDiv = document.querySelector('#myNewGrid');
	new agGrid.Grid(gridDiv, gridOptionsNew);
	/*  const currentMonth = ("0" + (new Date().getMonth() + 1)).slice(-2);
	 $("#monthDropdown").val(currentMonth); */
 
	$("#myGrid").show();
	$('#deletePerforationRecord').prop('disabled', true);
	$('#approvePerforationRecord').prop('disabled', true);
populateYears();
updateGridData();
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
    headerName: 'id',
    field: "recordId",
    width: 150,
    cellStyle: { textAlign: 'center' },
    cellRenderer: function(params) {
        let iconHtml = '';
        if (params.data.approveStatus == 1) {
            iconHtml = '<i class="bi bi-view-list"></i>';   
        } else {
            iconHtml = '<i class="bi bi-pencil-square"></i>';   
        }

        if (params.data.recordId) {
            return '<a id="id" onclick="editPerforationRecord(\'' 
                + params.data.recordId + '\', ' 
                + params.data.approveStatus + ')" href="javascript:void(0)">'
                + params.data.recordId + '</a> ' + iconHtml;
        } else {
            return '<a id="id" onclick="editPerforationRecord(\'' 
                + params.data.recordId + '\', ' 
                + params.data.approveStatus + ')" href="javascript:void(0)">N/A</a> ' + iconHtml;
        }
    }
},
{
		headerName: 'Date',
		field: "createdOn",
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
        return `<i class="bi bi-file-earmark-pdf-fill" style="cursor: pointer; font-size: 20px;" onclick="downloadPdf('${params.data.recordId}')"></i>`;
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

function onSelectionChange() {
    var selectedRows = gridOptions.api.getSelectedRows();
    
    if (selectedRows.length > 0) {
        var approveStatus = selectedRows[0].approveStatus;

        if (approveStatus == 1) {
            $('#approvePerforationRecord').prop('disabled', true);
        } else {
            $('#approvePerforationRecord').prop('disabled', false);
        }

        $('#deletePerforationRecord').prop('disabled', false);  
        $('#newButton').prop('disabled', true);  
    } else {
		$('#newButton').prop('disabled', false); 
        $('#deletePerforationRecord').prop('disabled', true);
        $('#approvePerforationRecord').prop('disabled', true);
        
    }
}



var columnDefsNew = [

	 {
                headerName: 'Date',
                field: "perforationDate",
                width: 100,
                cellStyle: { textAlign: 'center' },
            },
            {
                headerName: 'TRACK-1/2',
                field: "track1",
                width: 150,
                cellStyle: { textAlign: 'center' },
                cellRenderer: (params) => {
                    const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
                    return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track1', this.value)" />`;
                }
            },
            {
                headerName: 'TRACK-3/4',
                field: "track3",
                width: 150,
                cellStyle: { textAlign: 'center' },
                cellRenderer: (params) => {
                    const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
                    return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track3', this.value)" />`;
                }
            },
            {
                headerName: 'TRACK-5/6',
                field: "track5",
                width: 150,
                cellStyle: { textAlign: 'center' },
                cellRenderer: (params) => {
                    const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
                    return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track5', this.value)" />`;
                }
            },
            {
                headerName: 'TRACK-7/8',
                field: "track7",
                width: 150,
                cellStyle: { textAlign: 'center' },
                cellRenderer: (params) => {
                    const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
                    return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track7', this.value)" />`;
                }
            },
            {
                headerName: 'TRACK-9/10',
                field: "track9",
                width: 150,
                cellStyle: { textAlign: 'center' },
                cellRenderer: (params) => {
                    const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
                    return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track9', this.value)" />`;
                }
            },
	{
		headerName: 'TRACK-11/12',
		field: "track11",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track11', this.value)" />`;
		}
	},
	{
		headerName: 'TRACK-13/14',
		field: "track13",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
			 const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track13', this.value)" />`;
		}
	},
	{
		headerName: 'TRACK-15/16',
		field: "track15",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
	         const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track15', this.value)" />`;
		}
	},
	{
		headerName: 'TRACK-17/18',
		field: "track17",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
	         const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track17', this.value)" />`;
		}
	},
	{
		headerName: 'TRACK-19/20',
		field: "track19",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
	         const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'track19', this.value)" />`;
		}
	},
	{
		headerName: 'Remark',
		field: "remark",
		width: 150,
		cellStyle: { textAlign: 'center' },
		cellRenderer: (params) => {
	         const isDisabled = params.data.approveSts === 1 ? 'disabled' : ''; 
			return `<input name="gridInput" type="text" value="${params.value || ''}" ${isDisabled} oninput="updateData('${params.data.perforationDate}', 'remark', this.value)" />`;
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

function updateData(date, field, value) {
    gridOptionsNew.api.forEachNode(function (node) {
        if (node.data.perforationDate === date) {
            node.data[field] = value;  
        }
    });
}

// Function To generate Year Dynamically By Using Id
function populateYears() {
    let select = document.getElementById('yearDropdown');
    let currentYear = new Date().getFullYear();
    let startYear = currentYear - 5;  // Adjust the range of years as needed
    let endYear = currentYear + 5;

    for (let year = startYear; year <= endYear; year++) {
        let option = document.createElement('option');
        option.value = year;
        option.text = year;
        select.add(option);

        // Set the current year as selected
        if (year === currentYear) {
            option.selected = true;
        }
    }

    // Populate the months for the selected year and update grid data
    populateMonths(currentYear);

    // Add event listener to update months and grid data when the year changes
    select.addEventListener('change', function() {
        const selectedYear = parseInt(this.value);
        populateMonths(selectedYear);  // Populate months for the selected year
        updateGridData(); // Update the grid based on the newly selected year and month
    });
}
		
// Function to generate months dynamically
function populateMonths(year) {
	let monthSelect = document.getElementById('monthDropdown');
	monthSelect.innerHTML = ''; // Clear previous options

	let months = [
		'January', 'February', 'March', 'April', 'May', 'June',
		'July', 'August', 'September', 'October', 'November', 'December'
	];

	months.forEach((month, index) => {
		let option = document.createElement('option');
		option.value = index + 1; // Month numbers from 1 to 12
		option.text = month;
		monthSelect.add(option);

		// Set the current month as selected if the year is the current year
		if (year === new Date().getFullYear() && index === new Date().getMonth()) {
			option.selected = true;
		}
	});
}
function updateGridData() {
    const monthSelect = document.getElementById('monthDropdown');
    const month = parseInt(monthSelect.value); // Should be 1-based, e.g., 11 for November
    const year = new Date().getFullYear(); // Set to current year

    const daysInMonth = new Date(year, month, 0).getDate(); // Correct number of days in selected month
    const rowData = [];

    for (let day = 1; day <= daysInMonth; day++) {
        const date = new Date(year, month - 1, day); // Subtract 1 to adjust for zero-based month indexing
        const formattedDate = date.toLocaleDateString('en-GB', { day: '2-digit', month: 'long' });

        rowData.push({
            perforationDate: formattedDate,
            track1: '',
            approveSts: 0 // Customize as needed
        });
    }

    gridOptionsNew.api.setRowData(rowData);
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

function openNewPage() {
	$('#yearDropdown').prop('disabled', false);
	$('#monthDropdown').prop('disabled', false);
	$('#saveFeChecklist').prop('disabled', false); 
	$("#perforationRecordId").text('');
    $("#recordId").val('');
    $("#peforationRecordDiv").show();
    $("#newButton").hide();
    $("#dwnldExcel").hide();
    $("#myGrid").hide();
    $("#myNewGrid").show();
    $("#totalFeChecklist").hide();
    $('#deletePerforationRecord').hide();
    $('#approvePerforationRecord').hide();
    $('#totalPerforationRecord').hide();
     $("#inspectionDate").val('');
   //  populateYears();
     updateGridData();
 
}

function openPageForEdit(){
	
	$("#peforationRecordDiv").show();
	$("#newButton").hide();
	$("#dwnldExcel").hide();
	$("#myGrid").hide();
	$("#totalFeChecklist").hide();
	$('#deletePerforationRecord').hide();
	$('#totalPerforationRecord').hide();
	$('#approvePerforationRecord').hide();

}

function viewHomePage() {
	$("#peforationRecordDiv").hide();
	$("#newButton").show();
	$("#dwnldExcel").show();
	$("#myGrid").show();
	$("#totalFeChecklist").show();
	$('#deletePerforationRecord').show();
	$('#totalPerforationRecord').show();
	$('#approvePerforationRecord').show();
 
}
function cancelPage() {
	$("#peforationRecordDiv").hide();
	$("#newButton").show();
	$("#dwnldExcel").show();
	$("#myGrid").show();
	$("#totalFeChecklist").show();
	$('#deletePerforationRecord').show();
	$('#totalPerforationRecord').show();
	$('#approvePerforationRecord').show();
	gridOptions.api.deselectAll();
}
function savePerforationRecord() {
	let allRowData = [];

	var recordId = $("#recordId").val();
	var recordYear = $("#yearDropdown").val();
	var recordMonth = $("#monthDropdown").val();
	
	let isDuplicateMonth = false;
	
		if (!recordId) {
			// Compare the months and years
			if (years.includes(recordYear) && months.includes(recordMonth)) {
				isDuplicateMonth = true;
			}
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

	let recordJsonData = {
		recordId: recordId,
		recordYear: recordYear,
		recordMonth: recordMonth,
		rows: allRowData
	};

	let jsonData = JSON.stringify(recordJsonData);
   console.log("Record Ag Grid Data======>",jsonData);
	// AJAX call to save the data
	$.ajax({
		url: 'perforation-blade-record-save-data',
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
				viewPerforationData();
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}
 
var months=[];
var years=[];
var approveStsGlobal="";
function viewPerforationData() {
    $.ajax({
        url: 'perforation-blade-record-view',
        method: 'GET',
        success: function(response) {
            try {
                const parsedData = response.body ? JSON.parse(response.body) : []; 
                var len = parsedData.length;
                approveStsGlobal = parsedData[0].approveStatus;
                for (let i = 0; i < len; i++) {
                    const recordMonth = parsedData[i].month;
                    months.push(recordMonth);
                }
                 for (let i = 0; i < len; i++) {
                    const recordYear = parsedData[i].year;
                    years.push(recordYear);
                }
 
                $('#totalPerforationRecord').find('span').html(len);
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
                    return b.recordId.localeCompare(a.recordId);
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

function editPerforationRecord(recordId, approveSts) {
	  if (recordId) {
		 
        $('#yearDropdown').prop('disabled', true);
        $('#monthDropdown').prop('disabled', true);
    } else {
        $('#yearDropdown').prop('disabled', false);
        $('#monthDropdown').prop('disabled', false);
    }
    if (approveSts == 1) {
        $('#saveFeChecklist').prop('disabled', true);  
        $('input[name="gridInput"]').prop('disabled', true);
        // Disable year and month dropdowns
    } else {
        $('#saveFeChecklist').prop('disabled', false); 
        $('input[name="gridInput"]').prop('disabled', false);
        // Enable year and month dropdowns
    }

    $.ajax({
        url: 'perforation-blade-record-edit?recordId=' + recordId,
        method: 'GET',
        success: function(response) {
            if (response.code === "success") {
                openPageForEdit();
                var parsedBody = JSON.parse(response.body);
                var perforationData = parsedBody[0].perforationGridData; 
                
                $("#recordId").val(recordId);
                $("#perforationRecordId").text(recordId);
                $("#yearDropdown").val(parsedBody[0].year);
                $("#monthDropdown").val(parsedBody[0].month);
                
                perforationData.forEach(row => {
                    row.approveSts = approveSts;  
                });
                
                console.log("Perforation Data in Edit======>", perforationData);
                gridOptionsNew.api.setRowData(perforationData);
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX Error:', status, error);
        }
    });
}


function downloadPdf(recordId){
	 const url = `/ticket/perforation-blade-record-Pdf?recordId=${recordId}`;
    window.open(url, '_blank');

}

function deletePerforationRecord() {
	 $("#delete").modal("hide");
    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedNodes = gridOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);

    if (selectedData.length === 0) return; // No selection, exit early

    var recordId = selectedData[0].recordId;
    var recordMonth = selectedData[0].month;
    var recordYear = selectedData[0].year;

    $.ajax({
        type: "GET",
        url: "perforation-blade-record-delete?recordId=" + recordId,
        async: false,
        success: function(response) {
            if (response.code === "success") {
                // Remove the month and year of the deleted record from the arrays
                months = months.filter(month => month !== recordMonth);
                years = years.filter(year => year !== recordYear);

                $("#messageParagraph").text(response.message);
                $("#msgOkModal").removeClass("btn3").addClass("btn1");
                $("#msgModal").modal('show');

                $('#deletePerforationRecord').attr("disabled", true);
                $('#approvePerforationRecord').prop('disabled', true);
                $('#newButton').prop('disabled', false);
                $('#new').show();

                // Refresh the data
                viewPerforationData();
            }
        },
        error: function(data) {
            console.error('Delete AJAX error:', data);
        }
    });
}

function approvePerforationRecord(){
	 $("#approveModal").modal("hide");
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);


	var recordId = selectedData[0].recordId;
	$.ajax({
		type: "GET",
		url: "perforation-blade-record-approve?recordId=" + recordId,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				viewPerforationData();
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#deletePerforationRecord').attr("disabled", true);
				$('#approvePerforationRecord').prop('disabled', true);
				$('#newButton').prop('disabled', false);
				$('#new').show();

			}

		},
		error: function(data) {
		}
	});
}