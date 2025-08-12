let bookFromType = '';
$(document).ready(function() {
	bookFromType = 'OPD';
    // bookFromType = sessionStorage.getItem('textValue');
    // bookFromType = bookFromType != null ? bookFromType : 'OPD';
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
    
    $('#extPat').select2({
		placeholder: "Select",
		allowClear: true
	});

    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    
    var gridDiv5 = document.querySelector('#myGridPDtls');
	new agGrid.Grid(gridDiv5, gridOptionsPDtls);
	
	gridOptionsPDtls.api.setRowData([]);
	
	$(".br-s-btn-p").hide();
    
    getAllPatientsDetails();
    currentDateAndTime();
    getPayModeListAjax();
    $('.pay-tab').addClass('hidden');

    //paymentDetails();
    getDepartmentList(bookFromType);
    
    document.getElementById("existingPatientCheckbox").addEventListener("change", function() {
     let dropdown = document.getElementById("extPatDiv");
         dropdown.style.display = this.checked ? "block" : "none";
 		if (!this.checked) {
 			$("#extPat").val('').trigger('change');
 			newButton();
        }
 	});
});


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

function getPatientDetailsById(selectedValue, value = null) {
    if (selectedValue) {
        $.ajax({
            type: "GET",
            url: "reception-getPatientDetailsById?id=" + selectedValue,
            success: function(response) {
                if (response.code == "success") {
                    var jsonData = JSON.parse(response.body);
                    if (jsonData != null) {
                        $('#patientId, #fullName, #age, #mobNo, #altMobNo, #gender, #email, #nationality, #country, #states, #dist, #cityId, #zipCode, #address, #occupation, #officeName, #income, #officeAddress').prop('disabled', true).val("");
                        $("#depName,#depId,#depMobNo,#depRelation").val('');
                        gridOptionsPDtls.api.setRowData([]);
                        $("#country").val(jsonData[0].country);
                        getStateDetails(jsonData[0].state);
                        $("#patientId").val(jsonData[0].patientId);
                        $("#fullName").val(jsonData[0].patientName);
                        $("#age").val(jsonData[0].age);
                        $("#mobNo").val(jsonData[0].mob);
                        $("#altMobNo").val(jsonData[0].altMob);
                        $("#gender").val(jsonData[0].gender);
                        setTimeout(function() {
                            getDistrictDetails(jsonData[0].dist);
                        }, 2000);
                        setTimeout(function() {
                            getCityDetails(jsonData[0].city);
                        }, 3000);
                        $("#zipCode").val(jsonData[0].zip);
                        $("#address").val(jsonData[0].address);
                        $("#occupation").val(jsonData[0].occp);
                        $("#officeName").val(jsonData[0].ofc);
                        $("#officeAddress").val(jsonData[0].ofcadd);
                        $("#income").val(jsonData[0]?.ofcinc?.toFixed(2));
                        
                        if(jsonData[0]?.dep_data != null && jsonData[0]?.dep_data != '') {
		                	let dep_data = JSON.parse(jsonData[0]?.dep_data)
		                	gridOptionsPDtls.api.setRowData(dep_data);
		                } else {
		                	gridOptionsPDtls.api.setRowData([]);
		                }
                    }

                }
            },
            error: function(e) {}
        });
    } {
        $('#patientId, #patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add').prop('disabled', false).val("");
    }
}

function getAllPatientsDetails() {

    var fromDate = "";
    var toDate = "";

    agGrid.simpleHttpRequest({
        url: "reception-view?fromDate=" + fromDate + "&toDate=" + toDate + "&from=" + bookFromType
    }).then(function(response) {
        var jsonData = JSON.parse(response.body);
        var allData = jsonData.patientDetails;

        gridOptions.api.setRowData(allData);

        var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
        if (firstRowNode) {
            firstRowNode.setSelected(true);
        }

    });
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
        url: "reception-add-dep-data",
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

function onQuickFilterChanged() {
    var quickFilterValue = $('#quickFilter').val();
    if (window.gridApi) {
        window.gridApi.setQuickFilter(quickFilterValue);
    }
    updateTotalTaskCount();
}

function cancelBar() {
    var closeKey = $('#closeKey');
    closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
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

function mobVal3() {
    var mob = $('#altMobNo').val();
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


// ag grid

var columnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: false,
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
    onGridReady: function(params) {

        // Fetch data on grid ready
        getAllPatientsDetails();
    },
    onSelectionChanged: rowSelect
};


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

var patientId = '';
var pName = '';

function rowSelect() {
    var selectedRows = gridOptions.api.getSelectedRows();
    
    document.getElementById("existingPatientCheckbox").checked = false;

    if (selectedRows.length > 0) {

        $(".reg").addClass("active");
        $('.pay-tab').removeClass('hidden');
        
        $(".patDiv").hide();

        var selectedData = selectedRows.map(node => node.data);

        $('#opd, #fullName, #emergency, #country, #patientId, #fName, #mName, #lName, #dateOfBirth, #age, #depMobNo, #religion, #nationality, #address, #cityId, #zipCode, #mobNo, #contactNo, #email, #recommended, #occupation, #officeName, #income, #officeAddress, #patientStatus, #states, #doctor,#department,#dateOfAppointment,#description,#depName,#avlSlot,#altMobNo,#gender,#depRelation,#dist,#docFee,#avlSlot').attr('disabled', true);

        for (var i = 0; i < selectedRows.length; i++) {
            patientId = patientId + selectedRows[i].patientId;
            pName = pName + selectedRows[i].pName;
            
        }
		editPatientDetails(selectedRows[0]?.bookingId);
		getPaymenDetails(selectedRows[0].bookingId);

        $("#pName").text(pName)
        $("#pName1").text(pName)
        $("#pName2").text(pName)

        patientId = '';
        pName = '';
		
		$("#save").hide();

    } else {
        newButton();
        /*$('.pay-tab').addClass('hidden');
        $("#registration").removeClass('hidden');
        $("#doctorsDetail").addClass('hidden');

        $(".nav-link").removeClass("active");
        $(".nav-link[href='#registration']").addClass("active");

        $("#tab-registration").addClass("active").attr("aria-selected", "true");
        $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");
        $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");
        $("#tab-paymentDetail").removeClass("active").attr("aria-selected", "false");


        $("#registration").addClass("show active");
        $("#doctorsDetail").removeClass("show active");
        $("#otherDetail").removeClass("show active");
        $("#paymentDetail").removeClass("show active");*/
    }
}

function rowPSelect() {
	let selectedData = gridOptionsPDtls.api.getSelectedRows();
	if (selectedData && selectedData.length > 0) {
		$(".br-dis-p").attr("disabled", false);
	} else {
		$(".br-dis-p").attr("disabled", true);
	}
}

function newButton1(){
gridOptions.api.deselectAll();
}

function newButton() {
    if (window.gridApi) {
        window.gridApi.deselectAll();
    }
    $("#patientId").val("");
    $("#fullName").val("");
    $("#fName").val("");
    $("#mName").val("");
    $("#lName").val("");
    $("#dateOfBirth").val("");
    $("#age").val("");
    $("#gender").val("");
    $("#religion").val("");
    $("#nationality").val("");
    $("#address").val("");
    $("#cityId").val("");
    $("#country").val("");
    $("#states").val("");
    $("#dist").val("");
    $("#zipCode").val("");
    $("#mobNo").val("");
    $("#contactNo").val("");
    $("#email").val("");
    $("#recommended").val("");
    $("#patientStatus").val("");
    $("#occupation").val("");
    $("#officeName").val("");
    $("#income").val("");
    $("#officeAddress").val("");
    $("#department").val("");
    $("#doctor").val("");
    $("#dateOfAppointment").val("");
    $("#description").val("");
    $("#autoSearch").val("");
    $("#docFee").val("0.00");
    $("#avlSlot").val("");
    $("#patientStatus").val('Active');

    $("#depName").val("");
    $("#depRelation").val("");
    $("#mName").val("");
    $("#lName").val("");
    $("#dateOfBirth").val("");

    $("#altMobNo").val("");
    $("#depMobNo").val("");
    $("#pName,#pName1,#pName2").text("");
    
    $("#states").empty();
    $("#dist").empty();
    $("#cityId").empty();
    $("#states").append("<option value=''>Select</option>");
    $("#dist").append("<option value=''>Select</option>");
    $("#cityId").append("<option value=''>Select</option>");
    
    $('.pay-tab').addClass('hidden');
    
    $("#save").show();
    $(".patDiv").show();
    $("#extPatDiv").hide();
    // 
    gridOptionsPDtls.api.setRowData([]);
    
    enableFields()


    // Attach event listener to input fields to check validation on input or change
    /*$('input, select, textarea').on('input change', function() {
    	validateForm();
    });*/

    // Additionally check on page load (in case of pre-filled fields)
    //validateForm();
    //function for cancel

    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split('T')[0];
    //$("#dateOfAppointment").val(formattedDate).attr('disabled', true);
    //$("#avlSlot").val("").attr('disabled', true);

    currentDateAndTime();



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
    $("#doctorsDetail").removeClass('hidden');
    $("#registration").addClass('hidden');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-doctorsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registration").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registration").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

function prev() {
    $("#registration").removeClass('hidden');
    $("#doctorsDetail").addClass('hidden');


    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#registration']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-registration").addClass("active").attr("aria-selected", "true");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#registration").addClass("show active");
    $("#doctorsDetail").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

function next1() {

    $("#otherDetail").removeClass('hidden');
    $("#doctorsDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#otherDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-otherDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registration").removeClass("active").attr("aria-selected", "false");
    $("#tab-doctorsDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#otherDetail").addClass("show active");
    $("#registration").removeClass("show active");
    $("#doctorsDetail").removeClass("show active");
}

function prev1() {

    $("#doctorsDetail").removeClass('hidden');
    $("#otherDetail").addClass('hidden');

    $(".nav-link").removeClass("active"); // Remove 'active' from all nav-links
    $(".nav-link[href='#doctorsDetail']").addClass("active"); // Add 'active' to Doctor Details nav-link
    // Activate the "Doctor Details" tab
    $("#tab-doctorsDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-registration").removeClass("active").attr("aria-selected", "false");
    $("#tab-otherDetail").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#doctorsDetail").addClass("show active");
    $("#registration").removeClass("show active");
    $("#otherDetail").removeClass("show active");
}

function activityTabs(activityId) {
    if (activityId == "registration") {
        $("#registration").removeClass('hidden');
        $("#doctorsDetail").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#paymentDetail").addClass('hidden');
    } else if (activityId == "doctorsDetail") {
        $("#registration").addClass('hidden');
        $("#doctorsDetail").removeClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#paymentDetail").addClass('hidden');
    } else if (activityId == "otherDetail") {
        $("#registration").addClass('hidden');
        $("#doctorsDetail").addClass('hidden');
        $("#otherDetail").removeClass('hidden');
        $("#paymentDetail").addClass('hidden');
    } else if (activityId == "paymentDetail") {
        $("#registration").addClass('hidden');
        $("#doctorsDetail").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#paymentDetail").removeClass('hidden');
    }

}



function calculateAges() {
    var dob = $('#dateOfBirth').val();
    if (dob) {

        var parts = dob.split('-');
        if (parts.length === 3) {
            var day = parseInt(parts[0], 10);
            var month = parseInt(parts[1], 10) - 1;
            var year = parseInt(parts[2], 10);

            var dobDate = new Date(year, month, day);
            var currentDate = new Date();


            if (isNaN(dobDate) || dobDate > currentDate) {

                $("#messageParagraph").text("Please select a valid date of birth (not in the future)");
                $("#msgOkModal").removeClass("btn3");
                $("#msgOkModal").addClass("btn1");
                $("#msgModal").modal('show');
                $('#dateOfBirth').val("");
                $('#age').val("").prop('readOnly', true);
                return;
            }

            var age = Math.floor((currentDate - dobDate) / (365.25 * 24 * 60 * 60 * 1000));
            $('#age').val(age);
            $('#age').prop('readOnly', true);
        } else {
            $('#dateOfBirth').val("");
            $('#age').val("").prop('readOnly', true);
        }
    }
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
            url: "reception-stateList?id=" + cname,
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
            url: "reception-districtList?id=" + state,
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

//Onchange getDoctorsList
var doctorListWithPrice = [];

function getDoctorsList(id) {

    var department = $("#department").val();
    if (department) {

        var defaultOption = $("<option></option>");
        $(defaultOption).val(null);
        $(defaultOption).html("Select");

        $.ajax({
            type: "GET",
            url: "reception-doctorList?from=" + bookFromType + "&deptId=" + department,
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

function getDocFee() {
    let doc = $("#doctor").val();
    let docFeeList = doctorListWithPrice.filter(a => a.item_name == doc);
    if (docFeeList && docFeeList.length > 0) {
        $("#docFee").val(docFeeList[0].billing_price?.toFixed(2));
    } else {
        $("#docFee").val('0.00');
    }
}

// add
var datas = [];

function savePatient() {

    $(".formValidation").remove();

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
		prev1();
		return;
	}
	if($("#doctor").val() == null || $("#doctor").val() == '') {
		toastr.error("Doctor required");
		prev1();
		return;
	}

    var data = {};

    // Collecting Patient details
    data['patientId'] = $("#patientId").val();
    data['patientName'] = $("#fullName").val();
    data['age'] = $("#age").val();
    data['mobNo'] = $("#mobNo").val();
    data['mobNo2'] = $("#altMobNo").val();
    data['email'] = $("#email").val();
    data['gender'] = $("#gender").val();
    data['nationality'] = $("#nationality").val();
    data['countryid'] = $("#country").val();
    data['stateid'] = $("#states").val();
    data['dist'] = $("#dist").val();
    data['cityid'] = $("#cityId").val();
    data['add'] = $("#address").val();
    data['pincode'] = $("#zipCode").val();
    data['recommended'] = $("#recommended").val();
    data['status'] = $("#patientStatus").val();

    // Doctor details
    data['department'] = $("#department").val();
    data['amublanceNo'] = $("#doctor").val(); // used to save doctor id
    data['docFee'] = $("#docFee").val();
    data['isdate'] = $("#dateOfAppointment").val();
    data['time'] = $("#currentTime").val();
    data['description'] = $("#description").val();

    // others details
    data['occupation'] = $("#occupation").val();
    data['officeName'] = $("#officeName").val();
    data['income'] = $("#income").val();
    data['officeAddress'] = $("#officeAddress").val();

    // Dependent details
    data['dependantName'] = $("#depName").val();
    data['dependantMobNo'] = $("#depMobNo").val();
    data['dependantRelation'] = $("#depRelation").val();

    data['type'] = bookFromType;
    
    var rowData = [];
	
	gridOptionsPDtls.api.forEachNode(node => rowData.push(node.data));;
    
    data['dependantList'] = JSON.stringify(rowData);
    
    console.log(data)

    savePlanDetails(data);

}


let lastPatientId = "";

function savePlanDetails(data) {

    $('.loader').show();
    $("body").addClass("overlay");
    setTimeout(function() {
        $.ajax({
            type: "POST",
            url: "reception-add",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                if (response.code === "Success") {
                    toastr.success("OPD booked successfully");
                    getAllPatientsDetails();
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
    }, 1000);
}

//edit patient starts from here
let getStates = '';

function editPatientDetails(id) {
    $("#myGrid").show();
    $("#addPatient").show();
    $("#doctorsDetail").show();
    $("#pagination").show();
    $("#filter").hide();
    $("#cancel").show();
    //$("#patientType").hide();
    $('#patList').css('opacity', 0);
    $('#from').css('opacity', 0);
    $('#to').css('opacity', 0);

    $.ajax({
        type: "GET",
        url: "reception-edit?id=" + id,
        async: false,
        success: function(response) {
            if (response.code === "success") {
                const patientData = JSON.parse(response.body);
                
                console.log('patientData==',patientData)
                let a = patientData[0];

                /*if (patientData.admissionType == "OPD") {
                    $('#opd').prop('checked', true);
                } else if (patientData.admissionType == "EMERGENCY") {
                    $('#emergency').prop('checked', true);
                }*/

                getStates = a?.state;
                $("#country").val(a?.country);
                getStateDetails(a?.state);
                $("#patientId").val(a?.custid);
                $("#fullName").val(a?.fullName);
                $("#age").val(a?.age);
                $("#gender").val(a?.gender);
                $("#address").val(a?.address);
                //$("#city").val(a?.city);
                $("#zipCode").val(a?.pincode);
                $("#mobNo").val(a?.mob);
                $("#email").val(a?.email);
                $("#recommended").val(a?.recommend);
                $("#occupation").val(a?.occp);
                $("#officeName").val(a?.ofc);
                $("#income").val(a?.ofcinc?.toFixed(2));
                $("#officeAddress").val(a?.ofcaddr);
                $("#altMobNo").val(a?.altmob);
                $("#nationality").val(a?.nationality);
                $("#dateOfAppointment").val(a?.bookdate);
                $("#currentTime").val(a?.booktime);

                getDistrictDetails(a?.dist);
                setTimeout(function() {
                    getCityDetails(a?.city);
                }, 1000);

                // $("#department").val(a?.deptid);
                getDepartmentList(bookFromType,a?.deptid);
                $("#description").val(a?.desc);
                setTimeout(() => {
				  getDoctorsList(a?.doctor);
				}, 1000);
                
                $("#docFee").val(a?.bookamnt?.toFixed(2));
                if(a?.dep_data != null && a?.dep_data != '') {
                	let dep_data = JSON.parse(a?.dep_data)
                	gridOptionsPDtls.api.setRowData(dep_data);
                } else {
                	gridOptionsPDtls.api.setRowData([]);
                }
                
                
            }
        },
        error: function(error) {
            console.error("Error fetching patient details:", error);
        }
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
                url: "reception-delete-child-data",
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

function enableFields() {
    $('#opd, #emergency,#age, #fullName, #country, #patientId, #fName, #mName, #lName, #dateOfBirth, #age, #depMobNo, #religion, #nationality, #address, #cityId, #zipCode, #mobNo, #contactNo, #email, #recommended, #occupation, #officeName, #income, #officeAddress, #patientStatus, #states, #doctor,#department,#dateOfAppointment,#description,#depName,#avlSlot,#altMobNo,#gender,#depRelation,#dist').attr('disabled', false);
}

function checkNumberInput(id) {

    var tagname = $("#" + id).val()
    var replaceValue = tagname.replace(/[^\d.]/g, '');
    var decimalIndex = replaceValue.indexOf('.');
    if (decimalIndex !== -1) {
        replaceValue = replaceValue.substring(0, decimalIndex + 1) + replaceValue.substring(decimalIndex + 1).replace(/\./g, '');
    }
    $("#" + id).val(replaceValue);

}


function checkPayAmount() {
    var payAmount = $("#payingAmount").val();
    $("#remainAmount").val(payAmount);
}

function amountPayBtn() {

    $(".formValidation").remove();
    var valid = true;
    var obj = {};

    // Collecting payment details
    obj.payRemarks = $("#methodPayRemarks").val();
    obj.paymentMode = $('input[name=CashChequeOrOnlineMethod]:checked').val();
    obj.chequeNo = $("#methodChequeNo").val();
    obj.chequeBankName = $("#methodChequeBankName").val();
    obj.chequeBankBranch = $("#methodChequeBankBranch").val();
    obj.chequeAccountNumber = $("#methodChequeAccountNumber").val();
    obj.bankSelect = $("#methodBankSelect").val();
    obj.transactionNumber = $("#methodTransactionNumber").val();
    obj.receiverBankName = $("#methodReceiverBankName").val();
    obj.receiverBankBranch = $("#methodReceiverBankBranch").val();
    obj.receiverIfscCode = $("#methodReceiverIfscCode").val();
    obj.receiverAccountNumber = $("#methodReceiverAccountNumber").val();
    obj.onlineUpiID = $("#methodOnlineUpiID").val();
    obj.receiverOnlineUpiID = $("#methodReceiverOnlineUpiID").val();
    obj.receiverUpiTransactionID = $("#methodReceiverUpiTransactionID").val();
    obj.methodOfAdj = $("#adjustment").val();
    obj.vendorId = $("#vendorId").val();
    obj.payAmount = $("#payingAmount").val();
    obj.doctorFees = $("#doctorFees").val();
    obj.ambulanceFees = $("#ambulanceFees").val();
    obj.bankSelectPayment = $("#bankSelectPaymentMethod").val();


    // Validation
    if (valid) {
        var parentData = datas[datas.length - 1];

        parentData.payList.push(obj);
        savePlanDetails(parentData);
    }
}


/*@author saurav*/

function getCityDetails(cityId) {
    let dist = $('#dist').val();


    if (dist) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "reception-opd-city-list?id=" + dist,
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

//current date and time 
function currentDateAndTime() {
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

    var todaysDate = getCurrentDate();
    $("#dateOfAppointment").val(todaysDate);
    $("#DateCalendar").hide(DateCalendar);


    //

    $("#DateCalendar1").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $("#dateOfAppointment").val(todaysDate);
    })

    $('#dateOfAppointment').blur(function() {
        $("#DateCalendar1").val($(this).val());
    })

    var todaysDate = getCurrentDate();
    $("#dateOfAppointment").val(todaysDate);
    //


    var dateFormat = localStorage.getItem("dateFormat");
    $("#DateCalendar3").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $('#currentTime').val($(this).val());
    })

    $('#currentTime').blur(function() {
        $("#DateCalendar3").val($(this).val());
    })

    $("#currentTime").datetimepicker({
        format: 'H:i',
        closeOnDateSelect: false,
        timepicker: true,
        datepicker: false,
        step: 15
    })
    var todaysTime = getCurrentTime();
    $("#currentTime").val(todaysTime).prop('disabled', true);
}

function getCurrentDate() {
    var currentDate = new Date();

    var day = currentDate.getDate();
    var month = currentDate.getMonth() + 1;
    var year = currentDate.getFullYear();


    var formattedDay = day < 10 ? "0" + day : day;
    var formattedMonth = month < 10 ? "0" + month : month;


    var formattedDate = formattedDay + "-" + formattedMonth + "-" + year;

    return formattedDate;
}

function getCurrentTime() {
    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();

    var formattedHours = hours < 10 ? "0" + hours : hours;
    var formattedMinutes = minutes < 10 ? "0" + minutes : minutes;

    var formattedTime = formattedHours + ":" + formattedMinutes;

    return formattedTime;
}

// getDepartmentList
var deptDoctorWithPrice = [];

function getDepartmentList(bookFromType,id="") {
    var option = $("<option></option>");
    $(option).val(null);
    $(option).html("Select");
    $.ajax({
        type: "GET",
        url: "reception-department-list?id=" + bookFromType,
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