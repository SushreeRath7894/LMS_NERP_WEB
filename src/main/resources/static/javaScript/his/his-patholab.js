let type, bookFromType = '';
$(document).ready(function() {

	type = 'PATH';
    bookFromType = 'PATH';

    var activityId = "bloodAccesionTab";
    activityTabs(activityId)

    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    getAllPatientsDetails();

    var gridDivbloodAccesion = document.querySelector('#myGridbloodAccesion');
    new agGrid.Grid(gridDivbloodAccesion, gridOptionsBloodAccesion);


    var gridDivprocessing = document.querySelector('#myGridProcessing');
    new agGrid.Grid(gridDivprocessing, gridOptionsProcessing);


    $('#collectBtns').attr('disabled', true);
    $('#nextBTNs').attr('disabled', true);
	$("#lab-container").empty();
    var gridDiv2 = document.querySelector('#myGridSecond');
    if (gridDiv2) {
        new agGrid.Grid(gridDiv2, gridOptions2);
        gridOptions2.api.setRowData();
    } else {
        console.error("Grid container not found");
    }
    
    getTestList(type);
    
    $("#quickFilter").on("keydown", function(event) {
  		if (event.key === "Enter" || event.which === 13) {
  			event.preventDefault();
  			onQuickFilterChanged();
  		}
  	});
})

function onQuickFilterChanged() {
		gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	    if (firstRowNode) {
	        firstRowNode.setSelected(true);
	    }
	}

	function resetList() {
		let data = $("#quickFilter").val();
		if(data) {
			$("#quickFilter").val('');
			onQuickFilterChanged();
		}
	}

// ag grid

var columnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        maxWidth: 30,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Booking Id",
        field: "book_id",
        cellRenderer: (params) => {
        	
        	if(params.data.book_id) {
        		return params.data.test_id;
        	} else {
        		return params.data.test_id;
        	}
        	
        },
        flex:1
    },
    {
        headerName: "Patient Id",
        field: "cust_id",
        flex:1
    },
    {
        headerName: "Name",
        field: "cust_name",
        flex:1
    },
    {
        headerName: "Address",
        field: "address",
        flex:1
    },
    {
        headerName: "Mobile",
        field: "cust_mob",
        flex:1
    },
    {
        headerName: "Age",
        field: "age",
        flex:1
    },
    {
        headerName: "Gender",
        field: "gender",
        flex:1
    },
    {
        headerName: "Status",
        field: "pay_status",
        cellRenderer: function(params) {
            if (params.data.pay_status == "Fully Paid") {
                return '<span style="color:var(--mainColor);font-weight: bold;">Fully Paid</span>';
            } else if (params.data.pay_status == "Not Paid") {
                return '<span  style="color:red;font-weight: bold;">Not Paid</span>';
            } else if (params.data.pay_status == "Partial Paid") {
                return '<span style="color:black;font-weight: bold;">Partial Paid</span>';
            }
        },
        flex:1

    }
];

// Define grid options
var gridOptions = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 100,
    },
    pagination: true,
    paginationPageSize: 15,
    onSelectionChanged: rowSelect
};


var columnDefsBloodSample = [{
        /*headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,*/
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Blood Test Id",
        field: "testId",
        flex: 1,

    },
    {
        headerName: "Blood Test Name",
        field: "testName",
        flex: 1,

    },
    {
        headerName: "Generated Id",
        field: "orderStatus",
        cellRenderer: function(params) {
            if (params.data.orderStatus == "NOT GENERATED" || params.data.orderStatus == "NOT GENERATED") {
                return '<div style="color:#ff8242">NOT GENERATED</div>';
            } else {
                return '<div style="color:#034694">' + params.data.orderId + '</div>';
            }
        },
        cellStyle: {
            textAlign: 'center'
        },

    },
    {
        headerName: "Invoice Id",
        field: "geneatedId",
        flex: 1,
        hide: true

    },

];

// Define grid options
var gridOptionsBloodSample = {
    columnDefs: columnDefsBloodSample,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
    },

    onSelectionChanged: rowSelectBloodSample
};



var columnDefsBloodAccesion = [{
        /*headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,*/
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Blood Test Id",
        field: "testId",
        hide: true,


    },
    {
        headerName: "Blood Test Name",
        field: "testName",
        hide: true

    },
    {
        headerName: "Test Id",
        field: "geneatedId",
        flex: 1,
        editable: true,

    }, {
        headerName: 'QR COde',
        flex: 1,
        cellRenderer: function(params) {
            if (params.data.qrCode == "0" || params.data.qrCode == "") {
                return '<div style="color:#ff8242">Not Available</div>';
            } else {
                return '<a id="id" onclick=viewQr("' +
                    params.data.qrCode + '","' +
                    params.data.orderId + '") href="javascript:void(0)">' +
                    '<div style="color:#034694"><i class="bi bi-person-badge"> Download</i></div>' +
                    '</a>';
            }
        },
        cellStyle: {
            textAlign: 'center'
        },

        width: 390
    },


];

// Define grid options
var gridOptionsBloodAccesion = {
    columnDefs: columnDefsBloodAccesion,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
    },

    onSelectionChanged: rowSelectBloodSample
};



var columnDefsProcessing = [{
        /*headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,*/
        checkboxSelection: true,
        maxWidth: 30,
        hide: true,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Blood Test Id",
        field: "testId",

    },
    {
        headerName: "Blood Test Name",
        field: "testName",

    },
    {
        headerName: "Generated Id",
        field: "orderId",
        editable: true,

    }, {
        headerName: 'QR Code',
        cellRenderer: function(params) {
            if (params.data.qrCode == "0" || params.data.qrCode == null || params.data.qrCode == "") {
                return '<div style="color:#ff8242">Not Available</div>';
            } else {
                return '<a id="id" onclick=viewQr("' +
                    params.data.qrCode + '","' +
                    params.data.orderId + '") href="javascript:void(0)">' +
                    '<div style="color:#034694"><i class="bi bi-person-badge"> Download</i></div>' +
                    '</a>';
            }
        },
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Invoice Id",
        field: "geneatedId",
        hide: true

    },

];

// Define grid options
var gridOptionsProcessing = {
    columnDefs: columnDefsProcessing,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 100
    },

    onSelectionChanged: rowSelectProcessing
};


function viewQr(qr, id) {
    var qrId = "";
    qrId = qr;
    window.open("/document/staffQrCode/" + qrId, '_blank');
}

function getAllPatientsDetails(id='') {

    agGrid.simpleHttpRequest({
        url: "his-patholab-view"
    }).then(function(response) {
        var jsonData = JSON.parse(response?.body);
        var allData = jsonData;

		if(allData && allData.length > 0) {
			gridOptions.api.setRowData(allData);

	        if(id) {
            	gridOptions.api.forEachNode(node => {
            	    if (node.data.test_id === id) {
            	        node.setSelected(true);
            	    }
            	});
            } else {
            	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
                if (firstRowNode) {
                    firstRowNode.setSelected(true);
                }
            }
		} else {
			gridOptions.api.setRowData([]);
			rowSelect()
		}

    });
}




function getAllPatientsDetailsBloodAccesion(id) {

	$('#collectBtns').attr('disabled',true);
	
    agGrid.simpleHttpRequest({
        url: "his-patholab-testInvoice-view?id=" + id
    }).then(function(response) {
        var jsonData = JSON.parse(response?.body);
        var allData = jsonData?.patientDetails;

		if(allData && allData.length > 0) {
			gridOptionsBloodAccesion.api.setRowData(allData);
			if(allData[0].qrCode !== '0') {
				$("#collectBtns").hide();
				getAllPatientsDetailsProcessing(id)
			} else {
				$("#collectBtns").show();
				gridOptionsProcessing.api.setRowData([]);
				gridOptions2.api.setRowData([]);
			}
		} else {
			gridOptionsBloodAccesion.api.setRowData([]);
			gridOptionsProcessing.api.setRowData([]);
			gridOptions2.api.setRowData([]);
			$("#collectBtns").hide();
		}
        


    });
}
var allDataP = "";

function getAllPatientsDetailsProcessing(id) {
    agGrid.simpleHttpRequest({
        url: "his-patholab-bloodtest-view?id=" + id
    }).then(function(response) {
        var jsonData = JSON.parse(response?.body);
        allDataP = jsonData?.patientDetails;

		if(allDataP && allDataP.length > 0) {
			gridOptionsProcessing.api.setRowData(allDataP);
			if(allDataP[0].qrCode !== '0') {
				$("#qrCodeBtn").hide();
				getTestNameList(id)
			} else {
				$("#qrCodeBtn").show();
			}
		} else {
			gridOptionsProcessing.api.setRowData([]);
			$("#qrCodeBtn").hide();
		}
        


    });
}


var patientId = '';
var pName = '';
var customerId = '';

function rowSelect() {
    var selectedRows = gridOptions.api.getSelectedRows();
    console.log(selectedRows);
    var selectedData = selectedRows.map(node => node.data);
    var rowCount = 0;
    selectedData.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
        $('#opd, #emergency, #country, #patientId, #fName, #mName, #lName, #dateOfBirth, #age, #depMobNo, #religion, #nationality, #address, #city, #zipCode, #mobNo, #contactNo, #email, #recommended, #occupation, #officeName, #income, #officeAddress, #patientStatus, #states, #doctor,#department,#dateOfAppointment,#description,#depName,#avlSlot,#altMobNo,#gender,#depRelation,#dist').attr('disabled', true);
    });

    for (var i = 0; i < selectedRows.length; i++) {
        patientId = patientId + selectedRows[i].cust_id;
        pName = pName + selectedRows[i].cust_name;
    }

	$("#lab-container").empty();
    $("#pName").text(pName);

    $('.patientName').text(pName); //set the pName whereever the class patientName gets called.

    $("#patientId").val('')
$("#proceedPayBtn").hide()
    if (rowCount > 0) {
    	$('.patDiv').hide();
    	disableAllFields()
    	$("#resetAppBtn,#saveNewPatBtn,.tst").hide();
    	$("#bloodAccesionTabLi,#processingTabLi,#reportEntryTabLi,#labReportsTabDiv").hide();
    	getPatientAndTestDetails(selectedRows[0].test_id);
    	if(selectedRows[0].pay_status == 'Fully Paid') {
    		$('.pay-tab,.report-tab').show();
    		$("#bloodAccesionTabLi,#processingTabLi,#reportEntryTabLi").show()
    		nextTab('bloodAccesionTabLi')
    		getAllPatientsDetailsBloodAccesion(selectedRows[0].test_id);
    	} else {
    		$('.pay-tab,.report-tab').hide();
    		$("#bloodAccesionTabLi,#processingTabLi,#reportEntryTabLi,#labReportsTabDiv").hide();
    		if(selectedRows[0].invoice_id) {
    			$('.pay-tab').show();
    			nextTab('paymentDetailLi')
    		} else {
    			nextTab('registrationTabLi')
    			$("#proceedPayBtn").show()
    		}
    	}
    	
    	if(selectedRows[0]?.pfrom === 'IPD') {
    	 	$("#proceedPayBtn").text('Add To Bed');
        } else {
        	$("#proceedPayBtn").text('Pay Now');
        }
        
    } else {
		$("#pName").text('');
        nextTab('registrationTabLi')
        newPatient()
		gridOptionsProcessing.api.setRowData([]);
		gridOptions2.api.setRowData([]);
		$("#bloodAccesionTabLi,#processingTabLi,#reportEntryTabLi,#labReportsTabDiv").hide();
    }

    patientId = '';
    pName = '';
    customerId = '';

}
var orderId = "";

function rowSelectBloodSample() {
    var selectedRows = gridOptionsBloodAccesion.api.getSelectedRows();
    var selectedNodes = gridOptionsBloodAccesion.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
    let qrCode = selectedData.map(node => node.qrCode);
    var rowCount = 0;
    selectedRows.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
    });
    if (rowCount > 0) {
        if (qrCode == '0') {
            $('#nextBTNs').attr('disabled', true);
            $('#collectBtns').attr("disabled", false);
            //$('#saveDetails').attr('disabled', true);
        } else {
            $('#nextBTNs').attr('disabled', false);
            $('#collectBtns').attr("disabled", true);
            $('#saveDetails').attr('disabled', false);
        }



    } else {
        $('#collectBtns').attr("disabled", true);
        //$('#saveDetails').attr('disabled', true);
        $('#nextBTNs').attr('disabled', true);

    }

}
var qrCode = "";
var testId = "";

function rowSelectProcessing() {
    var selectedRows = gridOptionsProcessing.api.getSelectedRows();
    var selectedNodes = gridOptionsProcessing.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
    console.log(selectedData)
    orderId = selectedData.map(node => node.orderStatus);
    qrCode = selectedData.map(node => node.qrCode);
    testId = selectedData.map(node => node.testId);

    var rowCount = 0;
    selectedRows.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
    });
    console.log(rowCount);

    /*
    	if (rowCount > 0) {
    		$('#nextBTNs2').attr('disabled', false);
    
    	} else {
    		$('#nextBTNs2').attr('disabled', true);
    
    	}*/

    qrCode = "";
    orderId = "";
    testId = "";

}


function rowSelectResult() {
    var selectedRows = gridOptions2.api.getSelectedRows();
    var selectedNodes = gridOptionsProcessing.api.getSelectedNodes();
    /*var selectedData = selectedNodes.map(node => node.data);
	 console.log(selectedData)
	 orderId=selectedData.map(node => node.orderId);
	 qrCode=selectedData.map(node => node.qrCode);
*/
    var rowCount = 0;
    selectedRows.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
    });
    console.log(rowCount);


}

function collectBtns() {

    var selectedRows = gridOptionsBloodAccesion.api.getSelectedRows();
    var selectedRowsString = '';
    var selectTest = '';
    selectedRows.forEach(function(selectedRow, index) {
        if (index > 0) {
            selectedRowsString += ',';
        }
        selectedRowsString += selectedRow.geneatedId;
        selectTest += selectedRow.testName;
    });
    var geneatedId = selectedRowsString;
    var test = selectTest;
    var status = 1;

    var obj = {};
    obj.bloddSampleId = geneatedId;
    obj.patientId = selectedRows[0].cust_id;
    obj.orderId = selectedRows[0].book_id;
    console.log(obj)
    $.ajax({
        type: "POST",
        url: "his-patholab-sampleTest-add",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function(response) {
            if (response.message == "Success") {
                toastr.success('Sample collected successfully');
                getAllPatientsDetailsBloodAccesion(geneatedId);
            }

        },
        error: function(datas) {
            console.log(datas)
            $('.loader').hide();
        }
    })



}
/*
function saveTests(data) {
	console.log(data)

	$.ajax({
		type: "POST",
		url: "his-patholab-sampleTest-add",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.message == "Success") {
				$("#messageParagraph").text(
					"Sample Collected Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				getAllPatientsDetailsBloodSample();

			}

		},
		error: function(datas) {
			console.log(datas)
			$('.loader').hide();
		}
	})
}
*/

//Column definitions for the test list AG Grid
var columnDefs2 = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: false,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },

    {
        headerName: "Group ID",
        field: "grpID",
        width: 300,
        hide: true
    },
    {
        headerName: "Group Name",
        field: "grpName",
        flex: 1
    },
    {
        headerName: "Test ID",
        field: "testId",
        width: 300,
        hide: true
    },
    {
        headerName: "Test Name",
        field: "testName",
        flex: 1
    },
    {
        headerName: "Unit Id",
        field: "unitId",
        width: 170,
        hide: true
    },
    {
        headerName: "Order Id",
        field: "orderId",
        width: 170,
        hide: true
    },
    {
        headerName: "Unit",
        field: "unit",
        flex: 1
    },
    {
        headerName: "Actual Value",
        field: "actualValue",
        flex: 1,
        editable: true,
        cellEditor: 'agTextCellEditor',
        cellEditorParams: {
            maxLength: 10
        }
    },
    {
        headerName: "Range",
        field: "range",
        flex: 1,
    },
];




// Initialize AG Grid with empty rowData
var gridOptions2 = {
    columnDefs: columnDefs2,
    suppressRowClickSelection: true,

    onCellClicked: function(event) {
        if (event.colDef.field === 'actualValue') { // Check if the clicked cell is "Result"
            event.api.startEditingCell({
                rowIndex: event.rowIndex,
                colKey: event.column.getColId() // Use the column's ID
            });
        }
    },

    rowSelection: 'multiple',
    onSelectionChanged: rowSelectResult
};
/*

var gridOptions3 = {
		columnDefs: columnDefs3,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			height: 20,
			//flex: 1,
			//minWidth: 100
		},
		rowSelection: 'multiple',
		suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChangedResourse
	};*/
var pdId = "";

function activityTabs(activityId) {
    /*if(activityId == "bloodSampleTestTab"){
    	$("#bloodSampleTestTab").removeClass('bloodSampleTestTab');
    	$("#bloodAccesionTab").addClass('bloodAccesionTab');
    	$("#processingTab").addClass('processingTab');
    	$("#reportEntryTab").addClass('reportEntryTab');
    	$("#doctorSigningTab").addClass('doctorSigningTab');
    	$("#labReportsTab").addClass('labReportsTab');
    	
    	
    } else*/
    if (activityId == "bloodAccesionTab") {
        //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
        $("#bloodAccesionTab").removeClass('bloodAccesionTab');
        $("#processingTab").addClass('processingTab');
        $("#reportEntryTab").addClass('reportEntryTab');
        $("#doctorSigningTab").addClass('doctorSigningTab');
        $("#labReportsTab").addClass('labReportsTab');

        /*pdId = $("#pID").text();
        getAllPatientsDetailsBloodAccesion(pdId);*/

    } else if (activityId == "processingTab") {
        //$('#nextBTNs2').attr('disabled', true);
        //	$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
        $("#bloodAccesionTab").addClass('bloodAccesionTab');
        $("#processingTab").removeClass('processingTab');
        $("#reportEntryTab").addClass('reportEntryTab');
        $("#doctorSigningTab").addClass('doctorSigningTab');
        $("#labReportsTab").addClass('labReportsTab');

        /*pdId = $("#pID1").text();
        getAllPatientsDetailsProcessing(pdId);*/

    } else if (activityId == "reportEntryTab") {

        //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
        $("#bloodAccesionTab").addClass('bloodAccesionTab');
        $("#processingTab").addClass('processingTab');
        $("#reportEntryTab").removeClass('reportEntryTab');
        $("#doctorSigningTab").addClass('doctorSigningTab');
        $("#labReportsTab").addClass('labReportsTab');



    } else if (activityId == "labReportsTab") {
        //	$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
        $("#bloodAccesionTab").addClass('bloodAccesionTab');
        $("#processingTab").addClass('processingTab');
        $("#reportEntryTab").addClass('reportEntryTab');
        $("#doctorSigningTab").addClass('doctorSigningTab');
        $("#labReportsTab").removeClass('labReportsTab');

        /*pdId = $("#customerId").val();
		window.open("/his/his-patholab-report-pdf?id=" + window.btoa(pdId), '_blank');
*/


    }
    pdId = "";
}

function nextBTNs2() {
    // $("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").removeClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');




    //	$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").removeClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#reportEntryTab']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-reportEntryTab").addClass("active").attr("aria-selected", "true");
    $("#tab-bloodAccesionTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-processingTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorSigningTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-labReportsTab").removeClass("active").attr("aria-selected", "false");
    //	$("#tab-bloodSampleTestTab").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#reportEntryTab").addClass("show active");
    //$("#bloodSampleTestTab").removeClass("show active");
    $("#bloodAccesionTab").removeClass("show active");
    $("#processingTab").removeClass("show active");
    $("#doctorSigningTab").removeClass("show active");
    $("#labReportsTab").removeClass("show active");

}



function nextBTNs3() {
    // $("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#labReportsTab").removeClass('doctorSigningTab');




    //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#labReportsTab").removeClass('doctorSigningTab');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#labReportsTab']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-labReportsTab").addClass("active").attr("aria-selected", "true");
    $("#tab-bloodAccesionTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-processingTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-reportEntryTab").removeClass("active").attr("aria-selected", "false");
    //$("#tab-bloodSampleTestTab").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#labReportsTab").addClass("show active");
    //$("#bloodSampleTestTab").removeClass("show active");
    $("#bloodAccesionTab").removeClass("show active");
    $("#processingTab").removeClass("show active");
    $("#reportEntryTab").removeClass("show active");


}



function nextBTNs1() {
    // $("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").removeClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');




    //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").removeClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#processingTab']").addClass("active"); // Add 'active' to Doctor Details nav-link

    $("#tab-processingTab").addClass("active").attr("aria-selected", "true");
    $("#tab-bloodAccesionTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorSigningTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-reportEntryTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-labReportsTab").removeClass("active").attr("aria-selected", "false");
    //$("#tab-bloodSampleTestTab").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#processingTab").addClass("show active");
    //$("#bloodSampleTestTab").removeClass("show active");
    $("#bloodAccesionTab").removeClass("show active");
    $("#doctorSigningTab").removeClass("show active");
    $("#reportEntryTab").removeClass("show active");
    $("#labReportsTab").removeClass("show active");
    
}


function prev2() {

    // $("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").removeClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');




    //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").removeClass('bloodAccesionTab');
    $("#processingTab").addClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#bloodAccesionTab']").addClass("active"); // Add 'active' to Doctor Details nav-link

    $("#tab-bloodAccesionTab").addClass("active").attr("aria-selected", "true");
    $("#tab-processingTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorSigningTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-reportEntryTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-labReportsTab").removeClass("active").attr("aria-selected", "false");
    //$("#tab-bloodSampleTestTab").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#bloodAccesionTab").addClass("show active");
    $("#processingTab").removeClass("show active");
    $("#doctorSigningTab").removeClass("show active");
    $("#reportEntryTab").removeClass("show active");
    $("#labReportsTab").removeClass("show active");




}



function prev3() {

    // $("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").removeClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');




    //$("#bloodSampleTestTab").addClass('bloodSampleTestTab');
    $("#bloodAccesionTab").addClass('bloodAccesionTab');
    $("#processingTab").removeClass('processingTab');
    $("#reportEntryTab").addClass('reportEntryTab');
    $("#doctorSigningTab").addClass('doctorSigningTab');
    $("#labReportsTab").addClass('labReportsTab');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#processingTab']").addClass("active"); // Add 'active' to Doctor Details nav-link

    $("#tab-processingTab").addClass("active").attr("aria-selected", "true");
    $("#tab-bloodAccesionTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorSigningTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-reportEntryTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-labReportsTab").removeClass("active").attr("aria-selected", "false");
    //$("#tab-bloodSampleTestTab").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#processingTab").addClass("show active");
    $("#bloodAccesionTab").removeClass("show active");
    $("#doctorSigningTab").removeClass("show active");
    $("#reportEntryTab").removeClass("show active");
    $("#labReportsTab").removeClass("show active");

}


function saveFile() {

    var uFile = $(uploadDoc_0)[0].files[0];
    var fileName = event.currentTarget.value;
    var lastIndex = fileName.lastIndexOf("\\");
    if (lastIndex >= 0) {
        fileName = fileName.substring(lastIndex + 1);
    }
    var extension = fileName.split(".");
    var iURL = URL.createObjectURL(uFile);
    $("#uploadedBillDiv_0").html("");

    if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
        var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
    } else if (extension[1] == "pdf") {
        var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
    } else {
        var LightImg = "<div class='uploadicon position-l'> </div>";
    }
    $("#uploadedBillDiv_0").html(LightImg);
    $("#imageName_0").html(fileName);
    var fileData = new FormData();
    fileData.append('file', uFile);
    fileData.append('path', 'none');

    $.ajax({
        type: "POST",
        url: "his-patholab-upload-file",
        enctype: "multipart/form-data",
        contentType: false,
        data: fileData,
        processData: false,
        cache: false,
        success: function(response) {

        },
        error: function(e) {

        }
    });
}

let testResultData = [];

function getTestNameList(id) {

    agGrid.simpleHttpRequest({
        url: "his-patholab-getTestNameList?id=" + id
    }).then(function(response) {
        var jsonData = JSON.parse(response?.body);
        var allData = jsonData?.patientDetails;

        if (allData && allData.length > 0) {
            testResultData = allData;
            $("#labReportsTabDiv").show();
        } else {
            $("#labReportsTabDiv").hide();
        }

        if (allData && allData.length > 0) {
            $("#doctNotes").val(allData[0].doctor_notes)
            $("#masterId").val(allData[0].masterId)
            
            gridOptions2.api.setRowData(allData);
        } else {
        	gridOptions2.api.setRowData([]);
        }
        
        getLabReport(id)

    });
}


function qrCodeBtn() {
    var bloodData = []
    var displayRowCount = gridOptionsProcessing.api.getDisplayedRowCount();
    var bookingId = "";
    var status = 1;
    var datas = [];
    for (var i = 0; i < displayRowCount; i++) {
        var rowData = gridOptionsProcessing.api.getDisplayedRowAtIndex(i).data;
        bookingId = rowData.geneatedId;
        var obj = {};
        var data = {};

        obj.bloddSampleId = rowData.geneatedId;
        obj.patientId = rowData.custId;
        obj.testName = rowData.testName;
        obj.testId = rowData.testId;
        obj.skuId = rowData.skuId;
        obj.skuName = rowData.skuName;
        obj.orderId = status;
        bloodData.push(obj);

    }
    data.bloddSampleId = bookingId;
    data.bloodDataList = bloodData;
    datas.push(data)
    saveTestNames(datas);
}

function saveTestNames(data) {
    $.ajax({
        type: "POST",
        url: "his-patholab-testNames-qrcode-add",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            if (response.message == "Success") {
                toastr.success('Sample processed Successfully');
                getAllPatientsDetailsProcessing(data[0]?.bloddSampleId)
				getTestNameList(data[0].bloddSampleId)
            }

        },
        error: function(datas) {
            console.log(datas)
            $('.loader').hide();
        }
    })
}


function saveDetails() {

    var report = [];
    gridOptions2.api.forEachNode(function(rowNode, index) {
        var item = rowNode.data;
        item.doctor_notes = $("#doctNotes").val();
        report.push(item);

    });
    console.log(report)
    var datas = [];
    var obj = {};
    obj.bloddSampleId = report[0].orderId;
    obj.patientId = $("#pID2").text();
    obj.doctNotes = $("#doctNotes").val();
    obj.customerId = $("#customerId").val();
    obj.bloodDataList = report;

    datas.push(obj);
    console.log(datas)
    masterSave(datas);
 
}

function masterSave(datas) {
    $('.loader').show();
    console.log(datas);
    $.ajax({
        type: "POST",
        url: "his-patholab-actualValue-add",
        contentType: "application/json",
        data: JSON.stringify(datas),
        success: function(response) {
         $('.loader').hide();
            if (response.message == "Success") {
               
                toastr.success("Data entered successfully");
                getTestNameList(datas[0].bloddSampleId);
				getLabReport(datas[0].bloddSampleId)
            }

        },
        error: function(datas) {
            console.log(datas)
            $('.loader').hide();
        }
    })


}