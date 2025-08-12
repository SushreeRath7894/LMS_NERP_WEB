let bookFromType = '';
$(document).ready(function() {
   
	bookFromType = 'IPD';

    $('#savePatient').attr("disabled", false);
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    $('#locdeletebtn').attr('disabled', true);
    
    $('.pay-tab').hide();

    var gridDiv = document.querySelector('#activity');
    new agGrid.Grid(gridDiv, activityOptions);
    // activityOptions.api.setRowData();

	//agGridOnLoad();
	getItemListAjax();
	//getPayModeListAjax();
	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendar").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $("#dateOfAppointment").val(todaysDate);
    })

    $('#dateOfAppointment').blur(function() {
        $("#DateCalendar").val($(this).val());
    })

    $(".br-s-btn-p").hide();
    
    $("#addBed").attr('disabled', true);
    CKEDITOR.replace('dignosys', {
        height: 150,
        removePlugins: 'wsc',
        scayt_autoStartup: true,
        scayt_maxSuggestions: 3,
        autoParagraph: false,
    });

	getDepartmentList('OPD');
	// paymentDetails();

	var gridDiv5 = document.querySelector('#myGridPDtls');
	new agGrid.Grid(gridDiv5, gridOptionsPDtls);
	
	gridOptionsPDtls.api.setRowData([]);

    $('#autoSearch').show();


    //for dateOf Appointment 

    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split('T')[0];
    let nwDt = formattedDate.split('-');
    $("#dateOfAppointment").val(nwDt[2]+'-'+nwDt[1]+'-'+nwDt[0]);


    $("#cancel").hide();
    $("#save").show();
    $('#delete').attr("disabled", true);
    //$("#patientType").hide();
    $("#autoSearch").hide();
    //$("#patientType").hide();

    //For fade validation
    setupInputValidation();

    $("#detailsInsurance").hide();

});

let globalItemList = [];
function getItemListAjax() {

    agGrid.simpleHttpRequest({
        url: 'his-billing-pharmacy-get-medicine-list'
    }).then(function(data) {
    	if(data.code === 'success') {
    		globalItemList = data.body;
    	} else {
    		globalItemList = []
    	}
    	agGridOnLoad();
    })
}

let globalPayModeList = [];
function getPayModeListAjax() {

    agGrid.simpleHttpRequest({
        url: 'his-billing-pharmacy-get-paymode-list'
    }).then(function(data) {
    	if(data.code === 'success') {
    		globalPayModeList = data.body;
    	} else {
    		globalPayModeList = []
    	}
    	paymentDetails();
    })
}

const columnDefsPDtls = [{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: 'Name',
		field: "depName",
		flex: 1
	}, {
		headerName: "Mobile Number",
		field: "depMobNo",
		flex: 1
	}, {
		headerName: "Relation",
		field: "depRelation",
		flex: 1
	}
];

const gridOptionsPDtls = {
	columnDefs: columnDefsPDtls,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowPSelect,
	getRowNodeId: function (data) {
		return data.id;
	},
};

function rowPSelect() {
	let selectedData = gridOptionsPDtls.api.getSelectedRows();
	if (selectedData && selectedData.length > 0) {
		$(".br-dis-p").attr("disabled", false);
	} else {
		$(".br-dis-p").attr("disabled", true);
	}
}

function togglePSection() {
	$(".br-m-btn-p").hide();
	$(".br-s-btn-p").show();

	$("#depId").val('');
	$("#depName").val('');
	$("#depMobNo").val('');
	$("#depRelation").val('');
	
	$("#depName,#depMobNo,#depRelation").attr('disabled',false);
}

function cancelPDetails() {
	$(".br-m-btn-p").show();
	$(".br-s-btn-p").hide();
	$(".formValidation2").hide();

	$("#depId").val('');
	$("#depName").val('');
	$("#depMobNo").val('');
	$("#depRelation").val('');
}

function editPDetails() {
    togglePSection();
    let selectedData = gridOptionsPDtls.api.getSelectedRows();

    $("#depId").val(selectedData[0].id);
    $("#depName").val(selectedData[0].depName);
    $("#depMobNo").val(selectedData[0].depMobNo);
    $("#depRelation").val(selectedData[0].depRelationId);
}

function saveDepChildData() {
	let custId = $("#patientId").val();
	
	if(custId) {
		directSaveToDb(custId);
	} else {
		savePDetails();
	};
}

function directSaveToDb(custId) {

    let depName = $("#depName").val();
    let depMobNo = $("#depMobNo").val();
    let depRelationId = $("#depRelation").val();

    if (depName == null || depName == "") {
        toastr.error('Dependant name required');
        return;
    }
    if (depMobNo == null || depMobNo == "") {
        toastr.error('Dependant mobile number required');
        return;
    }
    if (depRelationId == null || depRelationId == "") {
        toastr.error('Dependant relation required');
        return;
    }

    let data = {};

    data.id = custId;
    data.key = depRelationId;
    data.name = depName;
    data.code = depMobNo;

    $('.loader').show();
    $("body").addClass("overlay");

    $.ajax({
        type: "POST",
        url: "manage-ipd-add-dep-data",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            if (response.code === "success") {
            	cancelPDetails()
                if (response.body[0]) {
                    let data = JSON.parse(response.body);
                    gridOptionsPDtls.api.setRowData(data);
                } else {
                    gridOptionsPDtls.api.setRowData([]);
                }
                $('.loader').hide();
                $("body").removeClass("overlay");
            } else {
                $('.loader').hide();
                $("body").removeClass("overlay");
            }
        },
        error: function(response) {
            console.log(response);
            $('.loader').hide();
            $("body").removeClass("overlay");
        }
    });

}

function savePDetails() {

    let depName = $("#depName").val();
    let depMobNo = $("#depMobNo").val();
    let depRelationId = $("#depRelation").val();
    let depRelation = $("#depRelation option:selected").text();
    let id = $("#depId").val();

    let dataset = [];
    let lastSelectedSlNo = '0';
    gridOptionsPDtls.api.forEachNode((node) => {
        dataset.push(node.data);
        lastSelectedSlNo = node.data.id;
    });
    
    if (depName == null || depName == "") {
        toastr.error('Dependant name required');
        return;
    }
    if (depMobNo == null || depMobNo == "") {
        toastr.error('Dependant mobile number required');
        return;
    }
    if (depRelationId == null || depRelationId == "") {
        toastr.error('Dependant relation required');
        return;
    }

    if (id != null && id != '') {
        const index = dataset.findIndex(obj => obj.id?.toString() === id?.toString());
        if (index !== -1) {
            dataset[index].id = id?.toString();
            dataset[index].depMobNo = depMobNo;
            dataset[index].depRelationId = depRelationId;
            dataset[index].depName = depName;
            dataset[index].depRelation = depRelation;
        }
    } else {
        let obj = {};

        obj.depMobNo = depMobNo;
        obj.depRelationId = depRelationId;
        obj.depName = depName;
        obj.depRelation = depRelation;
        obj.id = generateUUID()?.toString();
        dataset.push(obj)
    }

    gridOptionsPDtls.api.setRowData(dataset);

    cancelPDetails();
    $(".br-dis-p").attr("disabled", true);
}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function deleteSubChildData() {
    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to delete this?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, keep it',
        confirmButtonColor: 'var(--mainColor)',
    }).then((result) => {
        if (result?.value) {

            let patientId = $("#patientId").val();
            let selectedRows = gridOptionsPDtls.api.getSelectedRows();
            let selectedId = selectedRows[0]?.id;

            let obj = {};

            obj.key = patientId;
            obj.code = selectedId?.toString();

            $.ajax({
                type: "POST",
                url: "manage-ipd-delete-child-data",
                contentType: "application/json",
                data: JSON.stringify(obj),
                success: function(resp) {
                    if (resp.code == "success") {
                    	if(resp.body[0]) {
                    		let data = JSON.parse(resp.body);
                        	gridOptionsPDtls.api.setRowData(data);
                    	} else {
                    		gridOptionsPDtls.api.setRowData([]);
                    	}
                        toastr.success(resp.message);
                        $('.br-dis-p').attr('disabled', true);
                    }
                },
                error: function(data) {
                    console.log(data)
                    toastr.error('Something went wrong');
                }
            });

        }
    });
}

function agGridOnLoad() {

	agGrid.simpleHttpRequest({
    	url: 'manage-ipd-view'
    }).then(function(data) {
        var jsonData = JSON.parse(data?.body);
        var allData = jsonData?.patientDetails;
        if(allData?.length > 0) {
	        gridOptions.api.setRowData(allData);
	        var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
	        if (firstRowNode) {
	            firstRowNode.setSelected(true); // Set the row as selected
	        }
        } else {
        	gridOptions.api.setRowData([]);
        	getBedLists();
        }
    });
}

function timeChange() {
    var occSdate = $("#occSdate").val();
    var occEdate = $("#occEdate").val();

    if (occSdate != '' && occEdate != '') {
        if (occSdate < occEdate) {

        } else {
            $("#occSdate").val("");
            $("#occEdate").val("");
            $('#dateModal1').modal('show');
        }
    }
}

function getCurrentDate() {
    var currentDate = new Date();

    var day = currentDate.getDate(); // Get the day (1-31)
    var month = currentDate.getMonth() + 1; // Get the month (0-11) and adjust it by adding 1
    var year = currentDate.getFullYear(); // Get the full year (e.g., 2022)

    // Format day, month, and year with leading zeros if necessary
    var formattedDay = day < 10 ? "0" + day : day;
    var formattedMonth = month < 10 ? "0" + month : month;

    // Construct the date string in dd-mm-yyyy format
    var formattedDate = formattedDay + "-" + formattedMonth + "-" + year;

    return formattedDate;
}

function getCurrentTime() {
    var currentTime = new Date();

    var hours = currentTime.getHours(); // Get the hours (0-23)
    var minutes = currentTime.getMinutes(); // Get the minutes (0-59)

    // Format hours, minutes, and seconds with leading zeros if necessary
    var formattedHours = hours < 10 ? "0" + hours : hours;
    var formattedMinutes = minutes < 10 ? "0" + minutes : minutes;

    // Construct the time string in HH:MM:SS format
    var formattedTime = formattedHours + ":" + formattedMinutes;

    return formattedTime;
}


/* ------------------- search bar for mygrid------------------------ */

function onQuickFilterChanged() {
    gridOptions.api
        .setQuickFilter(document.getElementById('quickFilter').value);
    var displayedRowCount = gridOptions.api.getDisplayedRowCount();
    var len = displayedRowCount;
    $('#totalReq').find('span').html(len);
}

function cancelBar() {
    var id = document.getElementById("closeKey");
    id.style.display = "block";


    if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
        id.style.display = "none";

    }

}


var columnDefs = [{
        headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        maxWidth: 30,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Booking ID",
        field: "bookingId",
    },
    {
        headerName: "Patient ID",
        field: "patientId",
    },
    {
        headerName: "Name",
        field: "pName",
    },
    {
        headerName: "Age",
        field: "age",
    },
    {
        headerName: "Address",
        field: "address",
    },
    {
        headerName: "Mobile",
        field: "mobNo",
    },
    {
        headerName: "Status",
        field: "patientStatus",
    },
    {
        headerName: "Payment Status",
        field: "pay_status",
        hide:true,
        cellRenderer: function(params) {
            if (params.data.pay_status == "Fully Paid") {
                return '<a style="color:blue;font-weight: bold;">Fully Paid</a>';
            } else if (params.data.pay_status == "Not Paid") {
                return '<a  style="color:red;font-weight: bold;">Not Paid</a>';
            } else if (params.data.pay_status == "Partial Paid") {
                return '<a style="color:black;font-weight: bold;">Partial Paid</a>';
            }
        }
    },
];

var gridOptions = {
    columnDefs: columnDefs,
    //rowData: rowData, 
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 100,
    },

    onSelectionChanged: rowSelect,
    paginationAutoPageSize: true,
    pagination: true,
};

var patientId = '';
var pName = '';

function rowSelect() {
    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedNodes = gridOptions.api.getSelectedNodes();

    console.log(selectedNodes);
    var selectedData = selectedNodes.map(node => node.data);
    var rowCount = 0;
    selectedRows.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
        $('#fullName, #altMob, #patientId, #insuranceName, #age, #gender, #nationality, #address, #cityId, #dist, #zipCode, #mobNo, #country, #email, #recommended, #occupation, #officeName, #income, #officeAddress, #patientStatus, #states, #doctor,#department,#dateOfAppointment,#description,#docFee,#avlSlot,#oName,#relation,#oMobNo,#insurance').attr('disabled', true);

    });
    for (var i = 0; i < selectedRows.length; i++) {
        patientId = patientId + selectedRows[i].patientId;
        pName = pName + selectedRows[i].pName;

    }


    if (rowCount > 0) {
    
    	$('.pay-tab').show();
    	$('#savePatient').hide();
    	
        editPatientDetails(selectedRows[0]?.bookingId);
        getPaymenDetails(selectedRows[0].bookingId);
        $("#pName").text(pName)
	    $("#pName1").text(pName)
	    $("#pName2").text(pName)
	    $("#pName3").text(pName)
	    getBedLists();
    } else {
		newButton();
    }

    

    patientId = '';
    pName = '';


}




//for new button
function newButton() {
    
    $('#insuranceName, #fullName, #country, #patientId, #age, #gender, #nationality, #address, #cityId, #zipCode, #mobNo, #altMob, #email, #recommended, #occupation, #officeName, #income, #officeAddress, #states, #doctor, #department,#dateOfAppointment,#description,#docFee,#avlSlot,#oName,#relation,#oMobNo,#insurance,#dist').attr('disabled', false);
    
    $("#patientId").val("");
    $("#fullName").val("");
    $("#age").val("");
    $("#gender").val("");
    $("#nationality").val("");
    $("#address").val("");
    $("#cityId").val("");
    $("#country").val("");
    $("#states").val("");
    $("#dist").val("");
    $("#zipCode").val("");
    $("#mobNo").val("");
    $("#altMob").val("");
    $("#email").val("");
    $("#recommended").val("");
    $("#occupation").val("");
    $("#officeName").val("");
    $("#income").val("");
    $("#officeAddress").val("");
    $("#department").val("");
    $("#doctor").val("");
    $("#description").val("");
    $("#autoSearch").val("");
    $("#docFee").val("");
    $("#avlSlot").val("");
    $("#oName").val("");
    $("#relation").val("");
    $("#oMobNo").val("");
    $("#patientStatus").val("1");
    $("#insuranceName").val("");
    $('#delete').attr("disabled", true);
    
    $("#states").empty();
    $("#dist").empty();
    $("#cityId").empty();
    $("#states").append("<option value=''>Select</option>");
    $("#dist").append("<option value=''>Select</option>");
    $("#cityId").append("<option value=''>Select</option>");
    
    //$("#patientType").hide();
    $('#new').prop('checked', true);
    $('#opd').prop('checked', true);
    $("#add").show();
    $("#searchDiv").show();
    $("#addPatient").show();
    $("#cancel").show();
    $('#savePatient').show();
    $("#myGrid").show();
    $("#patId").hide();
    $("#autoSearch").show();
    $("#pName").val("");

    $('#patList').css('opacity', 0);
    $('#searchRowDiv').css('opacity', 0);
    CKEDITOR.instances.dignosys.setData("");


    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split('T')[0];
    let nwDt = formattedDate.split('-');
    $("#dateOfAppointment").val(nwDt[2]+'-'+nwDt[1]+'-'+nwDt[0]);

    $("#detailsInsurance").hide();
    
    $("#pName").text('')
	    $("#pName1").text('')
	    $("#pName2").text('')
	    $("#pName3").text('')

    $('#savePatient').attr("disabled", false);
    $('#next').attr("disabled", false);

    
    $('.pay-tab').hide();
    
    gridOptionsPDtls.api.setRowData([]);
    $("input[name='patient'][id='cash']").prop("checked", true);
    
    getBedLists();

}

function next() {

    $("#openOkModal").modal('show');
    $("#next").attr('disabled', true);


}

function yesOnClick() {
    $("#openOkModal").modal('hide');
    $("#next").attr('disabled', true);
    $('#savePatient').attr("disabled", false);
}

function noOnclick() {
    $("#openOkModal").modal('hide');
    $("#next").attr('disabled', false);
    $("#savePatient").attr('disabled', false);

}


//for cancel button

function Cancel() {
    $('#savePatient').attr("disabled", false);
    $('#next').attr("disabled", true);
    $("#addPatient").hide();
    $("#doctorsDetail").show();
    $("#myGrid").show();
    $('#delete').attr("disabled", true);
    $("#add").show();
    $("#addPatient").hide();
    $("#cancel").hide();
    $("#save").hide();
    //$("#patientType").hide();
    $("#autoSearch").hide();
    $('#patList').css('opacity', 1);
    $('#searchRowDiv').css('opacity', 1);

    $("#salutation").val("");
    $("#fName").val("");
    $("#mName").val("");
    $("#lName").val("");
    $("#dateOfBirth").val("");
    $("#age").val("");
    $("#gender").val("");
    $("#maritialStatus").val("");
    $("#religion").val("");
    $("#nationality").val("");
    $("#address").val("");
    $("#city").val("");
    $("#country").val("");
    $("#states").val("");
    $("#dist").val("");
    $("#zipCode").val("");
    $("#mobNo").val("");
    $("#contactNo").val("");
    $("#email").val("");
    $("#recommended").val("");
    $("#occupation").val("");
    $("#officeName").val("");
    $("#income").val("");
    $("#officeAddress").val("");
    $("#department").val("");
    $("#doctor").val("");
    $("#dateOfAppointment").val("");
    $("#description").val("");
    $("#autoSearch").val("");
    $("#docFee").val("");
    $("#avlSlot").val("");
    $("#oName").val("");
    $("#relation").val("");
    $("#oMobNo").val("");
    $('#insurance').prop('checked', fasle);
    CKEDITOR.instances.dignosys.setData("");
    //location.reload();




}

function getCityDetails(cityId) {
    let dist = $('#dist').val();


    if (dist) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-ipd-city-list?id=" + dist,
            success: function(response) {
                if (response.message == "success") {
                    $("#cityId").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#cityId").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#cityId").append(option);
                    }
                    if (cityId) {
                        $("#cityId").val(cityId);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#cityId").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#cityId").append(option);
        $("#cityId").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}

//add patient starts from here
var datas = [];

function savePatient() {

	if($("#fullName").val() == null || $("#fullName").val() == '') {
		toastr.error("Patient full name required");
		prev();
		return;
	}
	if($("#age").val() == null || $("#age").val() == '') {
		toastr.error("Age required");
		prev();
		return;
	}
	if($("#mobNo").val() == null || $("#mobNo").val() == '') {
		toastr.error("Mobile number required");
		prev();
		return;
	}
	if($("#gender").val() == null || $("#gender").val() == '') {
		toastr.error("Gender required");
		prev();
		return;
	}
	if($("#country").val() == null || $("#country").val() == '') {
		toastr.error("Country required");
		prev();
		return;
	}
	if($("#states").val() == null || $("#states").val() == '') {
		toastr.error("State required");
		prev();
		return;
	}
	if($("#dist").val() == null || $("#dist").val() == '') {
		toastr.error("District required");
		prev();
		return;
	}
	if($("#cityId").val() == null || $("#cityId").val() == '') {
		toastr.error("City required");
		prev();
		return;
	}
	if($("#address").val() == null || $("#address").val() == '') {
		toastr.error("Address required");
		prev();
		return;
	}
	if($("#zipCode").val() == null || $("#zipCode").val() == '') {
		toastr.error("Pincode required");
		prev();
		return;
	}
	if($("#department").val() == null || $("#department").val() == '') {
		toastr.error("Department required");
		prev2();
		return;
	}
	
	/*let bedLength = $(".box-selected").length;
	
	if(bedLength == 0) {
		toastr.error("Select a bed");
		return;
	}*/

    var data = {};
    var dignosys = CKEDITOR.instances.dignosys.getData();

    data.patientId = $("#patientId").val();
    data.patientName = $("#fullName").val();
    data.patientType = $("#patientType").val();
    data.age = $("#age").val();
    data.gender = $("#gender").val();
    data.nationality = $("#nationality").val();
    data.add = $("#address").val();
    data.cityid = $("#cityId").val();
    data.countryid = $("#country").val();
    data.stateid = $("#states").val();
    data.dist = $("#dist").val();
    data.pincode = $("#zipCode").val();
    data.mobNo = $("#mobNo").val();
    data.mobNo2 = $("#altMob").val();
    data.email = $("#email").val();
    data.recommended = $("#recommended").val();
    data.occupation = $("#occupation").val();
    data.officeName = $("#officeName").val();
    data.income = $("#income").val();
    data.status = $("#patientStatus").val();
    data.officeAddress = $("#officeAddress").val();

    data.department = $("#department").val();
    data.driverId = $("#doctor").val(); // used for doctor
    data.isdate = $("#dateOfAppointment").val();
    data.description = $("#description").val();

    data.depName = $("#oName").val();
    data.depRelation = $("#relation").val();
    data.depMobNo = $("#oMobNo").val();

    // data.insurance = $("#insurance").val();
    // data.cash = $("#cash").val();
    data.insuranceName = $("#insuranceName").val();
    data.diagnosis = dignosys;
    
    data['type'] = bookFromType;
    data['amublanceNo'] = $(".box-selected").attr('data-val')
    
    var rowData = [];
	
	gridOptionsPDtls.api.forEachNode(node => rowData.push(node.data));;
    
    data['dependantList'] = JSON.stringify(rowData);

	console.log(data, 'dataa')
    
    savePlanDetails(data);

}

function savePlanDetails(data) {

	$('.loader').show();
    $("body").addClass("overlay");
    
    $.ajax({
        type: "POST",
        url: "manage-ipd-add",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            if (response.code == "Success") {
                toastr.success('IPD booked successfully');
                agGridOnLoad();
                prev();
                $('.loader').hide();
                $("body").removeClass("overlay");
            } else {
            	toastr.error(response.message);
            	$('.loader').hide();
                $("body").removeClass("overlay");
            }
        },
        error: function(response) {
            console.log(response);
            toastr.error('Something went wrong');
            $('.loader').hide();
            $("body").removeClass("overlay");
        }
    })
}


// Function to calculate age from dd-MM-yyyy formatted date
function calculateAges() {
    var dob = $('#dateOfBirth').val(); // Get the date of birth input value
    if (dob) {
        // Parse the input date in dd-MM-yyyy format
        var parts = dob.split('-'); // Split the string by "-"
        if (parts.length === 3) {
            var day = parseInt(parts[0], 10);
            var month = parseInt(parts[1], 10) - 1; // Months are 0-indexed in JavaScript
            var year = parseInt(parts[2], 10);

            var dobDate = new Date(year, month, day); // Create a valid Date object
            var currentDate = new Date();

            // Validate the parsed date
            if (isNaN(dobDate) || dobDate > currentDate) {
                // Show error for invalid or future dates
                $("#messageParagraph").text("Please select a valid date of birth (not in the future)");
                $("#msgOkModal").removeClass("btn3");
                $("#msgOkModal").addClass("btn1");
                $("#msgModal").modal('show');
                $('#dateOfBirth').val(""); // Clear the input field
                return;
            }

            // Calculate age
            var age = Math.floor((currentDate - dobDate) / (365.25 * 24 * 60 * 60 * 1000)); // Approximate years
            $('#age').val(age); // Set age value
            $('#age').prop('readOnly', true); // Make the field readonly
        } else {
            alert("Invalid date format. Please use dd-MM-yyyy.");
            $('#dateOfBirth').val(""); // Clear the input field
        }
    }
}

function getUrl(module, fun, activity) {

    $.ajax({
        type: "GET",
        url: "/index-get-breadcrumb-data?moduleId=" + module + "&fun=" +
            fun + "&activity=" + activity,
        async: false,
        success: function(response) {
            if (response.message == "Unsuccess") {
                console.log(JSON.stringify(response));

                modOnclick(fun);
                callActivity(activity, response.body.actURL);

            }
        },
        error: function(data) {}
    });
}

//edit patient starts from here
let getStates = '';

function editPatientDetails(id) {
    
    agGrid.simpleHttpRequest({
        url: "manage-ipd-edit?id=" + id,
    }).then(function(data) {

        var jsonData = JSON.parse(data?.body);
        var a = jsonData[0];
        $("#department").val(a?.deptid);
        $("#country").val(a?.country);
        getStateDetails(a?.state);
        $("#patientId").val(a?.custid);
        $("#fullName").val(a?.fullName);
        $("#age").val(a?.age);
        $("#gender").val(a?.gender);
        $("#nationality").val(a?.nationality);
        $("#address").val(a?.address);
        $("#zipCode").val(a?.pincode);
        $("#mobNo").val(a?.mob);
        $("#altMob").val(a?.altmob);
        $("#email").val(a?.email);
        $("#recommended").val(a?.recommend);
        $("#occupation").val(a?.occp);
        $("#officeName").val(a?.ofc);
        $("#income").val(a?.ofcinc?.toFixed(2));
        $("#officeAddress").val(a?.ofcaddr);
        setTimeout(function() {
                    getDistrictDetails(a?.dist);
                    getDoctorsList(a?.assigned_user);
                }, 1000);
        
                setTimeout(function() {
                    getCityDetails(a?.city);
                }, 2000);
        
        $("#dateOfAppointment").val(a?.bookdate);
        $("#description").val(a?.desc);
        
        if (a?.insuranceName == "") {
            $('#cash').prop('checked', true)
            $("#detailsInsurance").hide();
        } else {
            $("#detailsInsurance").show();
            $("#insuranceName").val(a?.insuranceName);
            $('#insurance').prop('checked', true)
        }
        
        if(a?.dep_data != null && a?.dep_data != '') {
                	let dep_data = JSON.parse(a?.dep_data)
                	gridOptionsPDtls.api.setRowData(dep_data);
                } else {
                	gridOptionsPDtls.api.setRowData([]);
                }
                
                getBedLists(a?.doctor);

        CKEDITOR.instances['dignosys'].setData(a?.diagnosis);

    });
}

//OnChange StateList
function getStateDetails(id) {
    var cname = $('#country').val();
    if (cname) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-patient-stateList?id=" + cname,
            success: function(response) {
                if (response.message == "success") {
                    $("#states").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#states").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#states").append(option);
                    }
                    if (id == "0") {

                    } else {
                        $("#states").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#states").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#states").append(option);
        $("#states").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}


//OnChange district
function getDistrictDetails(id) {
    let state = '';
    if ($('#states').val() == "" || $('#states').val() == null) {
        state = getStates;
    } else {
        state = $('#states').val();
    }

    if (state) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-patient-districtList?id=" + state,
            success: function(response) {
                if (response.message == "success") {
                    $("#dist").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#dist").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#dist").append(option);
                    }
                    if (id == "0") {

                    } else {

                        $("#dist").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#dist").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#dist").append(option);
        $("#dist").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}


function getStateModalDetails(id) {
    var cname = $('#countryModal').val();
    if (cname) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-patient-stateList?id=" + cname,
            success: function(response) {
                if (response.message == "success") {
                    $("#statesModal").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#statesModal").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#statesModal").append(option);
                    }
                    if (id == "0") {

                    } else {
                        $("#statesModal").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#statesModal").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#statesModal").append(option);
        $("#statesModal").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}

//OnChange district
function getDistrictModalDetails(id) {
    let state = '';
    if ($('#statesModal').val() == "" || $('#statesModal').val() == null) {
        state = getStates;
    } else {
        state = $('#statesModal').val();
    }

    if (state) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-patient-districtList?id=" + state,
            success: function(response) {
                if (response.message == "success") {
                    $("#distModal").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#distModal").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#distModal").append(option);
                    }
                    if (id == "0") {

                    } else {

                        $("#distModal").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#distModal").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#distModal").append(option);
        $("#distModal").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}



//For AutoSearch of Registered Patient

function selectAutocompleteValue20(patientId, pName, mobNo, email) {

    if (patientId) {
        $("#patientId").val(patientId);
        var index = pName.indexOf(' / ');
        var trimmedData = pName.substring(0, index);
        $("#pName").val(trimmedData);
        $("#search").val(pName);
        $("#search").attr('data-procat', patientId);
        $("#suggesstion-box20_").hide();

        getPatientDetails();
    } else {
        $("#patientId").val("");
        $("#pName").val("");


        $("#search").val("");
        $("#search").attr('data-procat', "");
        $("#suggesstion-box20_").hide();

    }
}


function getPatientList() {
    var search = $("#pName").val();
    if (search.length < 1) {
        $("#suggesstion-box20_").hide();
    }
    if (search) {
        $.ajax({
            type: "POST",
            url: "manage-ipd-patientList",
            dataType: 'json',
            contentType: 'application/json',
            data: search,
            success: function(response) {
                if (response.message == "success") {
                    if (response.body.length != 0) {
                        $("#search").css("background", "#FFF");
                        var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';

                        for (var i = 0; i < response.body.length; i++) {
                            content += '<li class="autocompletedata cp ps-List" onClick="selectAutocompleteValue20(\'' +
                                response.body[i].patientId +
                                '\',\'' +
                                response.body[i].pName +
                                '\',\'' +
                                response.body[i].email +
                                '\',\'' +
                                response.body[i].mobNo +
                                '\')">' +
                                response.body[i].pName +
                                '</li>';
                        }

                        content += '</ul>';
                        $("#suggesstion-box20_").show();
                        $("#suggesstion-box20_").html(content);

                    } else {
                        $("#search").css("background", "#FFF");
                        var content = '<ul id="autocomplete-list2">';
                        content += '<li onClick="selectAutocompleteValue()">' +
                            "No Data Found" + '</li>';
                        content += '</ul>';
                        $("#suggesstion-box20_").show();
                        $("#suggesstion-box20_").html(content);
                        $("#patientType").show();
                    }
                }
            },
            error: function(data) {
                console.log("Error:", data);
            }
        });
    }
}

function selectAutocompleteValue() {

    $("#patientId").val("");
    $("#pName").val("");
    $("#email").val("");
    $("#mobNo").val("");
    $("#pName").val("");
    $("#search").val("");
    $("#search").attr('data-procat', "");
    $("#suggesstion-box20_").hide();

}

function AddPatient() {

    $('#addPatientModel').modal('show');

}




//Onchange getDoctorsList
function getDoctorsList(id) {
    //alert(id)
    var department = $('#department').val();
    //alert(id)
    if (department) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-ipd-doctorList?id=" + department,
            success: function(response) {
                if (response.message == "success") {
                    $("#doctor").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#doctor").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#doctor").append(option);
                    }
                    if (id == '0') {

                    } else {
                        $("#doctor").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#doctor").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#doctor").append(option);
        $("#doctor").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}

//for get registered Patient details

function getPatientDetails() {
    var id = $("#patientId").val();
    console.log("dkjnlhslajlk" + id);
    if (id) {
        agGrid.simpleHttpRequest({
            url: "manage-ipd-edit?id=" + id,
        }).then(function(data) {

            var jsonData = JSON.parse(data.body);
            var allData = jsonData.patientDetails;
            console.log(allData, 'all')
            getStates = allData[0].state;
            alert(allData[0].date)
            $("#country").val(allData[0].country);
            getStateDetails(allData[0].state);
            $("#patientId").val(allData[0].patientId);
            $("#patientType").val(allData[0].admissionType);
            $("#fName").val(allData[0].firstName);
            $("#mName").val(allData[0].middleName);
            $("#lName").val(allData[0].lastName);
            $("#dateOfBirth").val(allData[0].dob);
            $("#age").val(allData[0].age);
            $("#gender").val(allData[0].gender);
            $("#nationality").val(allData[0].nationality);
            $("#address").val(allData[0].address);
            $("#city").val(allData[0].city);
            $("#zipCode").val(allData[0].pincode);
            $("#mobNo").val(allData[0].mobile);
            $("#contactNo").val(allData[0].contactNo);
            $("#email").val(allData[0].email);
            $("#recommended").val(allData[0].recommendBy);
            $("#occupation").val(allData[0].occupation);
            $("#officeName").val(allData[0].officeName);
            $("#income").val(allData[0].income);
            $("#officeAddress").val(allData[0].officeAddress);

            $("#oName").val(allData[0].depName);
            $("#Relation").val(allData[0].depRelation);
            $("#oMobNo").val(allData[0].depMobNo);


            if (allData[0].status === "Active") {
                $('#patientStatus').val("1");
            } else {
                $('#patientStatus').val("0");
            }

            $("#states").val(allData[0].state);
            getDistrictDetails(allData[0].dist);

            $("#department").val(allData[0].department);
            $("#dateOfAppointment").val(allData[0].date);
            $("#description").val(allData[0].description);
            getDoctorsList(allData[0].doctor);
            $("#doctor").val(allData[0].description);
            if (allData[0].insuranceName == "") {
                $('#cash').prop('checked', true)
                $("#detailsInsurance").hide();
            } else {
                $("#detailsInsurance").show();
                $("#insuranceName").val(allData[0].insuranceName);
                $('#insurance').prop('checked', true)

            }

            CKEDITOR.instances['dignosys'].setData(allData[0].dignosys);

        });
    } else {

        $("#salutation").val("");
        $("#fName").val("");
        $("#mName").val("");
        $("#lName").val("");
        $("#dateOfBirth").val("");
        $("#age").val("");
        $("#gender").val("");
        $("#maritialStatus").val("");
        $("#religion").val("");
        $("#nationality").val("");
        $("#address").val("");
        $("#city").val("");
        $("#country").val("");
        $("#states").val("");
        $("#dist").val("");
        $("#zipCode").val("");
        $("#mobNo").val("");
        $("#contactNo").val("");
        $("#email").val("");
        $("#recommended").val("");
        $("#occupation").val("");
        $("#officeName").val("");
        $("#income").val("");
        $("#officeAddress").val("");
        $("#docFee").val("");
        $("#docFee").val("");

    }
}


//For hide validation msg
function setupInputValidation() {

    var fieldIds = ['fName', 'lName', 'dateOfBirth', 'gender', 'address', 'city', 'country', 'states', 'dist', 'zipCode', 'contactNo', 'mobNo', 'email', 'income'];

    // Attach input event handler to each field
    fieldIds.forEach(function(id) {
        $('#' + id).on('input', function() {
            if ($(this).val().trim() !== "") {
                validationUpdated("", id);
            }
        });
    });
}

function hideDocFeeValidationMsg() {
    //alert("Hii");
    $("#docFee").next().hide();
    $("#avlSlot").next().hide();
}

function cancelBtn1() {
    $('#addPatientModel').modal('hide');
}


function addIpdInfo() {

    var data = {};

    data.patientId = $("#patientId").val();
    data.pTypeModal = $("#pTypeModal").val();
    data.sal = $("#sal").val();
    data.firstName = $("#firstName").val();
    data.middleName = $("#middleName").val();
    data.lastName = $("#lastName").val();
    data.dob = $("#dob").val();
    data.ageModal = $("#ageModal").val();
    data.genderModal = $("#genderModal").val();
    data.maritialStatusModal = $("#maritialStatusModal").val();
    data.religionModal = $("#religionModal").val();
    data.nationalityModal = $("#nationalityModal").val();
    data.addressModal = $("#addressModal").val();
    data.cityModal = $("#cityModal").val();
    data.countryModal = $("#countryModal").val();
    data.statesModal = $("#statesModal").val();
    data.distModal = $("#distModal").val();
    data.zipCodeModal = $("#zipCodeModal").val();
    data.mobNoModal = $("#mobNoModal").val();
    data.contactNoModal = $("#contactNoModal").val();
    data.emailModal = $("#emailModal").val();
    data.recommendedModal = $("#recommendedModal").val();
    data.occupationModal = $("#occupationModal").val();
    data.officeNameModal = $("#officeNameModal").val();
    data.incomeModal = $("#incomeModal").val();
    data.pStatus = $("#pStatus").val();
    data.officeAddressModal = $("#officeAddressModal").val();

    data.departmentModal = $("#departmentModal").val();
    data.doctorModal = $("#doctorModal").val();
    data.dateOfAppointmentModal = $("#dateOfAppointmentModal").val();
    data.descriptionModal = $("#descriptionModal").val();
    data.docFeeModal = $("#docFeeModal").val();
    data.avlSlotModal = $("#avlSlotModal").val();
    data.wardModal = $("#wardModal").val();
    data.bedModal = $("#bedModal").val();

    console.log("data", data);

    //return false;
    //validation starts

    var validation = true;

    if (data.firstName == null || data.firstName.trim() === "") {
        validation = validationUpdated("First Name Required", "fName");
    }
    if (data.lastName == null || data.lastName.trim() === "") {
        validation = validationUpdated("Last Name Required", "lastName");
    }

    if (data.dob == null || data.dob.trim() === "") {
        validation = validationUpdated("DateOfBirth Required", "dob");


    }

    if (data.genderModal == null || data.genderModal.trim() === "") {
        validation = validationUpdated("Gender Required", "genderModal");
    }

    if (data.addressModal == null || data.addressModal.trim() === "") {
        validation = validationUpdated("Address Required", "addressModal");

    }


    if (validation) {
        $.ajax({
            type: "POST",
            url: "manage-ipd-modalData-add",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                if (response.code == "Success") {
                    console.log(response);

                    $("#add").show();
                    $("#myGrid").hide();
                    $("#searchRowDiv").hide();
                    $("#demo").hide();
                    $("#addPatientModel").modal('hide');
                }
            },
            error: function(data) {}
        })
    }
}

//fetch bed

function bedList(id) {
    var ward = $('#ward').val();
    if (ward) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "manage-patient-bedList?id=" + ward,
            success: function(response) {
                if (response.message == "success") {
                    $("#bed").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#bed").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#bed").append(option);
                    }
                    if (id == "0") {

                    } else {
                        $("#bed").val(id);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#bed").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#bed").append(option);
        $("#bed").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
    }
}

function addBed() {
    $("#bedAlloatmentModal").modal('show');
}

function saveBed() {
    $("#bedAlloatmentModal").modal('hide');
}

function insurance() {

    if ($('#insurance').prop('checked', true)) {
        $("#detailsInsurance").show();

    } else {
        $("#detailsInsurance").hide();
    }

}

function cash() {
    $("#detailsInsurance").hide();
}

function openPaymentModal() {
    $("#paymentModal").modal('show');
}

function cancelModalBtn() {
    $("#paymentModal").modal('hide');
    $("#vendorName").val('');
    $("#vendorName").html('');
    $("#adjustment").attr("disabled", true);
    $("#amount").attr("disabled", true);
    $("#payingAmount").val('');
    $("#remainingAmtDiv").hide();
    $("#split").hide();

    // Hide Ag-grid On Cancel Button Click 
    var emptydata = [];
    gridOptions.api.setRowData(emptydata);
    $("#totalInvoice").find('span').html(0);
    $("#adjustment").val("--Select--");
    $("#amount").val('');
    $("#parentDiv").hide();

}

function checkNumberInput(id) {
    refreshGrid();
    var tagname = $("#" + id).val()
    var replaceValue = tagname.replace(/[^\d.]/g, '');
    var decimalIndex = replaceValue.indexOf('.');
    if (decimalIndex !== -1) {
        replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '');
    }
    $("#" + id).val(replaceValue);
    $("#payingAmount").val(truncatedValue);
    $("#remainAmount").val(truncatedValue);
}


function checkPayAmount() {
    var payAmount = $("#payingAmount").val();
    $("#remainAmount").val(payAmount);
}

function deletePatient() {
    $("#deleteModal").modal('show');
}

function cancelModalBtn() {
    $("#deleteModal").modal('hide');
}


function deleteFun() {

    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedRowsString = '';
    selectedRows.forEach(function(selectedRow, index) {
        if (index > 0) {
            selectedRowsString += ',';
        }

        selectedRowsString += selectedRow.patientId;
    });

    var id = selectedRowsString;
    $.ajax({
        type: "GET",
        url: "manage-ipd-delete?id=" + id,
        dataType: "json",
        contentType: "application/json",
        success: function(response) {
            if (response.message == "Success") {
                swal(" deleted successfully")
                cancelModalBtn();
                $('#delete').attr("disabled", true);
                $('#add').attr("disabled", true);
                agGrid.simpleHttpRequest({
                    url: 'manage-ipd-view'
                }).then(function(data) {
                    var jsonData = JSON.parse(data.body);
                    var allData = jsonData.ipdDetails;
                    console.log(allData)
                    gridOptions.api.setRowData(allData);


                });
            }
        },
        error: function(response) {
            console.log(response);
        }
    })
}

function validateForm() {
    let isValid = true;

    // Loop through each mandatory field and check if it is filled
    $('span.mandatory').each(function() {
        // Get the corresponding input/select field next to the mandatory asterisk
        let input = $(this).closest('.form-group').find('input, select, textarea');

        // Check if the field is empty or invalid
        if (input.val().trim() === '' || input.val() === 'Select') {
            isValid = false;
        }
    });

    // Enable or disable the Save button based on validation
    $('#next').prop('disabled', !isValid);
}


function next() {
    $("#otherDetail").removeClass('hidden');
    $("#registrationTab").addClass('hidden');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#otherDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-otherDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#otherDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#doctorsDetail").removeClass("show active");
}

function prev() {
    $("#registrationTab").removeClass('hidden');
    $("#otherDetail").addClass('hidden');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#registrationTab']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-registrationTab").addClass("active").attr("aria-selected", "true");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-bedsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#registrationTab").addClass("show active");
    $("#otherDetail").removeClass("show active");
    $("#doctorsDetail").removeClass("show active");
    $("#bedsDetail").removeClass("show active");
}

function next1() {

    $("#doctorsDetail").removeClass('hidden');
    $("#otherDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-doctorsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-bedsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#otherDetail").removeClass("show active");
    $("#bedsDetail").removeClass("show active");
}

function next3() {

    $("#bedsDetail").removeClass('hidden');
    $("#doctorsDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#bedsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-bedsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#bedsDetail").addClass("show active");
    $("#doctorsDetail").removeClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

function prev1() {

    $("#otherDetail").removeClass('hidden');
    $("#doctorsDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#otherDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-otherDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-bedsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#otherDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#doctorsDetail").removeClass("show active");
    $("#bedsDetail").removeClass("show active");
}

function next1() {

    $("#doctorsDetail").removeClass('hidden');
    $("#otherDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-doctorsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-bedsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#otherDetail").removeClass("show active");
    $("#bedsDetail").removeClass("show active");
}

function prev2() {

    $("#doctorsDetail").removeClass('hidden');
    $("#bedsDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-otherDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-bedsDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#bedsDetail").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

function next2() {

    $("#doctorsDetail").removeClass('hidden');
    $("#dependentDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-doctorsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registrationTab").removeClass("active").attr("aria-selected", "false");
    $("#tab-dependentDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registrationTab").removeClass("show active");
    $("#dependentDetail").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

//Mobile Number validation
var mobValid1;

function mobVal1() {
    var mob = $('#mobNo').val();
    var phoneno = /^\d{10}$/;
    if (mob != '') {
        if (phoneno.test(mob)) {

            $("#error9").hide();

            mobValid1 = true;
            return true;
        } else {

            $("#error9").html(
                "please enter  10 digit mobile number");
            $("#error9").show();
            mobValid1 = false;
            return false;
        }
    } else {
        $("#error9").html(" Mobile No is Required");
        mobValid1 = false;
        return false;
    }
}

//Mobile Number validation
var mobValid2;

function mobVal2() {
    var mob = $('#oMobNo').val();
    var phoneno = /^\d{10}$/;
    if (mob != '') {
        if (phoneno.test(mob)) {

            $("#error1").hide();

            mobValid2 = true;
            return true;
        } else {

            $("#error1").html(
                "please enter  10 digit mobile number");
            $("#error1").show();
            mobValid2 = false;
            return false;
        }
    } else {
        $("#error1").html(" Mobile No is Required");
        mobValid2 = false;
        return false;
    }
}

function mobVal3() {
    var mob = $('#contactNo').val();
    var phoneno = /^\d{10}$/;
    if (mob != '') {
        if (phoneno.test(mob)) {

            $("#error2").hide();

            mobValid3 = true;
            return true;
        } else {

            $("#error2").html(
                "please enter  10 digit mobile number");
            $("#error2").show();
            mobValid3 = false;
            return false;
        }
    } else {
        $("#error2").html(" Mobile No is Required");
        mobValid3 = false;
        return false;
    }
}

//Zipcode validation
var mobValid4;

function mobVal4() {
    var pin = $('#zipCode').val();
    var zipCode = /^\d{6}$/;
    if (pin != '') {
        if (zipCode.test(pin)) {

            $("#error3").hide();

            mobValid4 = true;
            return true;
        } else {

            $("#error3").html(
                "please enter  6 digit pin code");
            $("#error3").show();
            mobValid4 = false;
            return false;
        }
    } else {
        $("#error3").html(" Pin code is Required");
        mobValid4 = false;
        return false;
    }
}

function checkNumeric(fieldId) {
    var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
    const input = document.getElementById(fieldId);
    const position = input.selectionStart;
    if (tempVal.slice(-1) == ' ') {
        $("#" + fieldId).empty();
        tempVal = '';
    }
    $("#" + fieldId).val(tempVal);
}

function activityTabs(activityId) {
    if (activityId == "registrationTab") {
        $("#registrationTab").removeClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#doctorsDetail").addClass('hidden');
    } else if (activityId == "otherDetail") {
        $("#registrationTab").addClass('hidden');
        $("#otherDetail").removeClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#doctorsDetail").addClass('hidden');
    } else if (activityId == "dependentDetail") {
        $("#registrationTab").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").removeClass('hidden');
        $("#doctorsDetail").addClass('hidden');
    } else if (activityId == "doctorsDetail") {
        $("#registrationTab").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#doctorsDetail").removeClass('hidden');
    } else if (activityId == "bedsDetail") {
        $("#registrationTab").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#doctorsDetail").addClass('hidden');
        $("#bedsDetail").removeClass('hidden');
    }

}



// for activity table
var activityDefs = [{
    headerCheckboxSelectionFilteredOnly: true,
    checkboxSelection: function(params) {
        return params.data.availability !== "1";
    },
    sortable: false,
    filter: false,
    resizable: true,
    width: 30,
    pinned: 'left',

}, {
    headerName: "Relation",
    field: "depRelation",
    width: 250,
}, {
    headerName: "Name",
    field: "depName",
    width: 250,
    cellStyle: {
        textAlign: 'center'
    },
}, {
    headerName: "Mobile Number",
    field: "depMobNo",
    width: 250,
    cellStyle: {
        textAlign: 'center'
    },
}];

var activityOptions = {
    columnDefs: activityDefs,
    rowSelection: 'single',
    groupSelectsChildren: true,
    suppressRowClickSelection: true,
    suppressAggFuncInHeader: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 150
    },

    onSelectionChanged: rowSelectdata,
};


function rowSelectdata() {
    var selectedRows = activityOptions.api.getSelectedRows();
    if (selectedRows.length > 0) {
        $("#save").show();
    } else {
        $("#save").hide();
    }
}



function addDependent() {

    $("#activity").hide();
    $("#demo").show();

    $("#depRelation").val('');
    $("#depName").val('');
    $("#depMobNo").val('');



}


function saveDependent() {
    var item = {};
    var data = 1;
    var validation = true;
    var editDependent = $("#editDependent").val();
    if (item.depRelation == null || item.depRelation == "") {
        validation = validationUpdated("Dependent Relation Required", 'depRelation');
    }
    if (item.depName == null || item.depName == "") {
        validation = validationUpdated("Dependent Name Required", 'depName');
    }
    if (item.depMobNo == null || item.depMobNo == "") {
        validation = validationUpdated("Mobile Required", 'depMobNo');
    }
    if (validation) {
        item.slNo = data;
        activityOptions.api.forEachNode(function(rowNode, index) {
            if (!editDependent) {
                data = data + 1;
                item.slNo = data;
            } else {
                item.slNo = editDependent;
            }

        });
        item.depRelation = $('#depRelation').val();
        item.depName = $('#depName').val();
        item.depMobNo = $('#depMobNo').val();


        var datas = [];

        if (editDependent) {
            var rowNode = activityOptions.api.getSelectedRows(editDependent);
            rowNode.setData(item);
        } else {
            activityOptions.api.forEachNode(function(rowNode, index) {
                datas.push(rowNode.data);
            });
            datas.push(item)
            // activityOptions.api.setRowData(datas);
            $("#activity").show();
            $("#demo").hide();
        }
    }

}


function cancelDependent() {

    $("#demo").hide();

    $("#activity").show();

}

var mobValid2;

function mobVal2() {
    var mob = $('#depMobNo').val();
    var phoneno = /^\d{10}$/;
    if (mob != '') {
        if (phoneno.test(mob)) {

            $("#error1").hide();

            mobValid2 = true;
            return true;
        } else {

            $("#error1").html(
                "please enter  10 digit mobile number");
            $("#error1").show();
            mobValid2 = false;
            return false;
        }
    } else {
        $("#error1").html(" Mobile No is Required");
        mobValid2 = false;
        return false;
    }
}

var deptDoctorWithPrice = [];

function getDepartmentList(bookFromType,id="") {

    var option = $("<option></option>");
    $(option).val(null);
    $(option).html("Select");
    $.ajax({
        type: "GET",
        url: "manage-ipd-department-list?id=" + bookFromType,
        success: function(response) {
            if (response.message == "success") {
                $("#department").empty();
                var option = $("<option></option>");
                $(option).val(null);
                $(option).html("Select");
                $("#department").append(option);

                if (response.body && response.body.length > 0) {
                    deptDoctorWithPrice = response.body;
                } else {
                    deptDoctorWithPrice = [];
                }

                for (var i = 0; i < response.body.length; i++) {
                    var option = $("<option></option>");
                    $(option).val(response.body[i].key);
                    $(option).html(response.body[i].name);
                    $("#department").append(option);
                }

				$("#department").val(id);
				
            } else {
                deptDoctorWithPrice = [];
            }
        },
        error: function(e) {}
    });
}

var doctorListWithPrice = [];

function getDoctorsList(id) {

    var department = $("#department").val();
    if (department) {

        var defaultOption = $("<option></option>");
        $(defaultOption).val(null);
        $(defaultOption).html("Select");

        $.ajax({
            type: "GET",
            url: "manage-ipd-doctorList?from=" + 'OPD' + "&deptId=" + department,
            success: function(response) {
                if (response.code === "success") {
                    $("#doctor").empty();
                    $("#doctor").append(defaultOption);
                    var responseBody = JSON.parse(response.body);
                    if (responseBody && responseBody.view && responseBody.view.length > 0) {
                        doctorListWithPrice = responseBody.view;
                        
                        for (var i = 0; i < responseBody.view.length; i++) {
                            var option = $("<option></option>");
                            $(option).val(responseBody.view[i].item_name);
                            $(option).html(responseBody.view[i].item_user_name);

                            $("#doctor").append(option);
                        }
                        if (id !== "0") {
                            $("#doctor").val(id);
                        } else {
                        	$("#docFee").val('0.00');
                        }
                    } else {
                        doctorListWithPrice = [];
                    }


                } else {
                    doctorListWithPrice = [];
                }
            },
            error: function(e) {
                console.error("Error fetching doctor list:", e);
            }
        });
    } else {
        $("#doctor").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#doctor").append(option);
        $("#docFee").val("0.00")
    }
}

function mobVal3() {
    var mob = $('#altMob').val();
    var phoneno = /^\d{10}$/;
    if (mob != '') {
        if (phoneno.test(mob)) {

            $("#error2").hide();

            mobValid3 = true;
            return true;
        } else {

            $("#error2").html(
                "please enter  10 digit mobile number");
            $("#error2").show();
            mobValid3 = false;
            return false;
        }
    } else {
        $("#error2").html(" Mobile No is Required");
        mobValid3 = false;
        return false;
    }
}

function newButton1() {
	gridOptions.api.deselectAll();
}

function toggleBed(id) {
	$('.white-box').removeClass('box-selected');
	$("#"+id).addClass('box-selected');
	
}

function getBedLists(id='') {

	$("#bedListsSec").empty();

     $.ajax({
            type: "GET",
            url: "manage-ipd-bed-list",
            success: function(response) {
                if (response.code === "success") {
                    
                    if(response?.body[0]) {
                    	let data = JSON.parse(response.body[0])
                    	
                    	let a = '';
                    	
                    	data.forEach(d => {
                    		let c = '';
                    		if(d?.beds) {
                    			
                    			d?.beds?.forEach(e => {
                    			
                    				let eid = e.bed_id?.replaceAll('/','').replaceAll('-','');
                    			
                    				let cl1 = (e?.bed_status == 0) ? 'grey-clr' : 'pl-clr';
                    				let cl2 = (e?.bed_status == 0) ? '' : 'd-bed';
                    				let cl3 = (e?.bed_status == 0) ? 'onclick=toggleBed("'+eid+'")' : '';
                    			
	                    			c = c + '<div class="ward-sec '+cl2+'" '+cl3+'><div class="card white-box" id="'+eid+'" data-val="'+e.bed_id+'">'+
											'<div class="card-body align-content-center d-flex flex-column">'+
											'<i class="fas fa-bed '+cl1+'"></i> '+ e.bed_name +' </div></div></div>';
                    			});
                    		}
                    	
                    		a = a + '<div class="main-sec mrg-top" id="'+d.ward_id+'"><h6 class="title-txt">'+d.ward_name+'</h6><div class="row flex-wrap g-2">'+c+'</div></div>';
                    	});
                    	
                    	console.log(data)
                    	
                    	$("#bedListsSec").append(a);
                    	console.log(id,'id')
                    	if(id != null && id != '') {
                    		id = id?.replaceAll('/','').replaceAll('-','');
                    		$("#"+id).css('border','3px solid var(--mainColor)');
                    	} 
                    }

                }
            },
            error: function(e) {
                console.error("Error fetching doctor list:", e);
            }
        });
}