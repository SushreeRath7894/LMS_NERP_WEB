$(()=>{
    $('#extPat').select2();  //#extPat
    $('#gender').select2();  //#gender
    $('#countryid_').select2();  //#countryid_
    $('#stateid_').select2();  //#stateid_
    $('#dist').select2();  //#dist
    $('#cityid_').select2();  //#cityid_
    $('#typeAmb').select2();  //#typeAmb
    $('#reqAmb').select2();  //#reqAmb
});

let bookFromType = '';
let userRolesAmbList = [];
$(document).ready(function() {

    bookFromType = 'Ambulance';

    $("#bookingAmbulance").show();
    $("#ambulanceDetail").hide();
    $("#paymentDetail").hide();
    $("#save").hide();

    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);

    getAllAmbulanceDetails();
    getAllAmbulanceList();

    var gridDiv = document.querySelector('#activity');
    new agGrid.Grid(gridDiv, activityOptions);
    gridOptions.api.setRowData();

    // activityOptions.api.setRowData();

    $('#reqDltBtn').attr('disabled', true);
    $('#assignBtn').attr('disabled', true);
    $('#approveBtn').attr('disabled', true);
    $('#deleteChild').attr('disabled', true);
    $('#openApprovalBtn').attr('disabled', true);
    $("#emplist").hide();
    $("#loclist").hide();
    $("#assigndate").hide();
    $('.collapse').on('show.bs.collapse', function() {
        $(this).siblings('.panel-heading').addClass('active');
    });
    $('.pay-tab').addClass('hidden');
    $("#ambulanceDetails").hide();
    $("#next1").hide();

    $('.collapse').on('hide.bs.collapse', function() {
        $(this).siblings('.panel-heading').removeClass('active');
    });
    currentDateAndTime();

    let userRolesAmb = $("#userRolesAmb").val();
    userRolesAmb = userRolesAmb?.replace("[", "")?.replace("]", "");

    userRolesAmbList = userRolesAmb?.split(",")?.map(a => {
        a = a?.trim();
        return a;
    });

    if (!userRolesAmbList?.includes('rol080')) {
        paymentDetails();
    } else {
        $('.pay-tab').addClass('hidden');
        $("#newBtn").hide();
    }

    $("#accRejSecDiv").hide();
    
    $('#extPat').select2({
		placeholder: "Select",
		allowClear: true
	});
	document.getElementById("existingPatientCheckbox").addEventListener("change", function() {
     let dropdown = document.getElementById("extPatDiv");
         dropdown.style.display = this.checked ? "block" : "none";
 		if (!this.checked) {
 			$("#extPat").val('').trigger('change');
            $(' #bookingid, #patientId, #patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add, #reasonambulance, #typeAmb, #reqAmb').prop('disabled', false) .val("");
        }
 	});
});
function getPatientDetailsById(selectedValue, value = null) {
	if(selectedValue){
	 $.ajax({
		type: "GET",
		url: "booking-ambulance-getPatientDetailsById?id=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
			var jsonData = JSON.parse(response.body);
			if(jsonData!=null){
        		$('#patientId, #patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add').prop('disabled', true).val("");
					$("#countryid_").val(jsonData[0].country);
				    getStateList(jsonData[0].state);
				    $("#patientId").val(jsonData[0].patientId);
				    $("#patientName").val(jsonData[0].patientName);
				    $("#age").val(jsonData[0].age);
				    $("#mobNo").val(jsonData[0].mob);
				    $("#mobNo2").val(jsonData[0].altMob);
				    $("#gender").val(jsonData[0].gender);
				    setTimeout(function() {
				        getDistrictDetails(jsonData[0].dist);
				    }, 2000);
				    setTimeout(function() {
				        getCityDetails(jsonData[0].city);
				    }, 3000);
				    $("#pincode").val(jsonData[0].zip);
				    $("#add").val(jsonData[0].address);
				}

			}
		},
		error: function(e) {
		}
	});
	}{
	$('#patientId, #patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add').prop('disabled', false).val("");	
	}
}
function getStateList(stateId) {
    var country = $("#countryid_").val();
    if (country) {
        $.ajax({
            type: "POST",
            url: "booking-ambulance-state-list",
            dataType: 'json',
            contentType: 'application/json',
            data: country,
            success: function(
                response) {
                if (response.message == "success") {
                    console.log(response);
                    $("#stateid_").empty();
                    $("#stateid_").append("<option value=''>Select</option>");
                    $("#cityid_").empty();
                    $("#cityid_").append("<option value=''>Select</option>");

                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#stateid_").append(option);
                    }

                    if (stateId) {
                        $("#stateid_").val(stateId);
                    }
                }
            },
            error: function(data) {
                console.log(data);
                $("#stateid_").empty();
                $("#stateid_").append("<option value=''>Select</option>");
                $("#cityid_").empty();
                $("#cityid_").append("<option value=''>Select</option>");
            }
        })
    } else {
        $("#stateid_").empty();
        $("#stateid_").append("<option value=''>Select</option>");
        $("#cityid_").empty();
        $("#cityid_").append("<option value=''>Select</option>");
    }
}



/* -------------------search bar for mygrid----------------------- */

function onQuickFilterChanged() {
    gridOptions.api
        .setQuickFilter(document.getElementById('quickFilter').value);
    var totalRowCount = gridOptions.api.getModel().getRowCount();
    $('#totalAsset').find('span').html(totalRowCount);
}

function cancelBar() {
    var id = document.getElementById("closeKey");
    id.style.display = "block";

    if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
        id.style.display = "none";
    }
}
var columnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: false,
        checkboxSelection: true,
        maxWidth: 30,
        sortable: false,
        filter: false,
        resizable: true,
        pinned: 'left',

    },
    {
        headerName: 'Booking ID',
        field: "bookingId",

    }, {
        headerName: 'Invoice ID',
        field: "inv_id",

    }, {
        headerName: 'Patient Name',
        field: "patientName",
        minWidth: 130,

    }, {
        headerName: 'Age',
        field: "age",
        cellStyle: {
            textAlign: 'center'
        },
        maxWidth: 60,
    }, {
        headerName: 'Mobile Number',
        field: "mobNo",
        minWidth: 130,
    }, {
        headerName: 'Address',
        field: "add",
        minWidth: 150,
    }, {
        headerName: 'Ambulance Name',
        field: "ambulanceName",
    }, {
        headerName: 'Driver Name',
        field: "driverId",
        minWidth: 120,
    }, {
        headerName: "Status",
        field: "status",
        cellRenderer: function(params) {
            if (params.data.status == "1") {
                return '<a style="color:green;font-weight: bold;">Acceped</a>';
            } else if (params.data.status == "2") {
                return '<a  style="color:red;font-weight: bold;">Rejected</a>';
            } else if (params.data.status == "0") {
                return '<a style="color:black;font-weight: bold;">Pending</a>';
            }
        }
    }, {
        headerName: "Payment Status",
        minWidth: 120,
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
    }
];

// let the grid know which columns and what data to use
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
        flex: 1,
        minWidth: 100,
    },
    onGridReady: function(params) {

        getAllAmbulanceDetails();
    },
    onSelectionChanged: rowSelect,
    paginationAutoPageSize: true,
    pagination: true,
};
//function for row select parents
var apstatus = "";
var bookingId = '';
var pName = '';


function rowSelect() {

    var selectedNodes = gridOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
	$("#extPat").val('').trigger('change');
	document.getElementById("existingPatientCheckbox").checked = false;
    if (selectedData.length > 0) {
        $('.pay-tab').removeClass('hidden');
        $(".nav-link").removeClass("active");
        $(".nav-link[href='#bookingAmbulance']").addClass("active");

        $("#bookingAmbulance").show();
        $("#ambulanceDetail").hide();
        $("#paymentDetail").hide();
		$("#patDiv").hide();

        $("#ambBookingId").text(selectedData[0].bookingId);
        $("#ambulanceNumber").text(selectedData[0].ambulanceName);
        $("#driverName").text(selectedData[0].driverId);
        $("#driverContact").text(selectedData[0].driverContact);

        const date = new Date(selectedData[0].isdate);
        const day = date.getDate();
        const month = date.toLocaleString("en-US", {
            month: "short"
        })
        const year = String(date.getFullYear());
        const formattedDate = `${day} ${month.toUpperCase()} ${year}`;
        const timeString = selectedData[0].occSdate;
        let [hours, minutes] = timeString.split(":").map(Number);
        const ampm = hours >= 12 ? "PM" : "AM";
        hours = hours % 12 || 12;
        const formattedTime = `${hours}:${minutes.toString().padStart(2, "0")} ${ampm}`;
        const dateTime = `${formattedDate},  ${formattedTime}`;
        $("#bookingDate").text(dateTime);

        if (!userRolesAmbList?.includes('rol080')) {
            getPaymenDetails(selectedData[0].bookingId);
            $("#next1").show();
        } else {
            $('.pay-tab').addClass('hidden');
            $("#newBtn").hide();
            if (selectedData[0].status == 0) {
                $("#accRejSecDiv").show();
            } else {
                $("#accRejSecDiv").hide();
            }
        }


        $("#activity").hide();

        $("#ambulanceDetails").show();

    } else {
        $('.pay-tab').addClass('hidden');
        $(".nav-link").removeClass("active");
        $(".nav-link[href='#bookingAmbulance']").addClass("active");

        $("#bookingAmbulance").show();
        $("#ambulanceDetail").hide();
        $("#paymentDetail").hide();
        $("#patDiv").show();
        $("#extPatDiv").hide();
        getPaymenDetails('');

        // Show the "Doctor Details" tab content
        $("#bookingAmbulance").show();
        $("#ambulanceDetail").hide();
        $("#paymentDetail").hide();

        $("#ambBookingId").text('');
        $("#ambulanceNumber").text('');
        $("#driverName").text('');
        $("#driverContact").text('');
        $("#bookingDate").text('');
        $('#patientId, #patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add, #reasonambulance, #typeAmb, #reqAmb').prop('disabled', false).val("");
        currentDateAndTime();
        $("#save").show();
        $("#ambulanceDetails").hide();
        $("#activity").show();
        $("#next1").hide();
    }

    apstatus = selectedData.map(node => node.status);
    var selectedRows = gridOptions.api.getSelectedRows();
    var rowCount = 0;
    selectedRows.forEach(function(i) {
        rowCount = rowCount + 1;
        $('#patientId, #patientName, #age, #mobNo, #isdate, #occSdate, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add, #reasonambulance, #typeAmb, #reqAmb').attr('disabled', true);
        $("#DateCalendar").hide();

    });

    for (var i = 0; i < selectedRows.length; i++) {
        bookingId = bookingId + selectedRows[i].bookingId;
        pName = selectedRows[i].patientName;
        age = selectedRows[i].age;
        mobNo = selectedRows[i].mobNo;
        isdate = selectedRows[i].isdate;
        occSdate = selectedRows[i].occSdate;
        mobNo2 = selectedRows[i].mobNo2;
        gender = selectedRows[i].gender;
        countryid_ = selectedRows[i].countryid_;
        stateid_ = selectedRows[i].stateid_;
        dist = selectedRows[i].dist;
        cityid_ = selectedRows[i].cityid_;
        pincode = selectedRows[i].pincode;
        reasonambulance = selectedRows[i].reasonambulance;
        add = selectedRows[i].add;
        typeAmb = selectedRows[i].typeAmb;
        reqAmb = selectedRows[i].reqAmb;
        ambulanceNo = selectedRows[i].ambulanceNo;
        getAllAmbulanceList(ambulanceNo)
        setBookingData(bookingid, pName, age, mobNo, isdate, occSdate, mobNo2, gender, countryid_, stateid_, dist, cityid_, pincode, reasonambulance, add, typeAmb, reqAmb);
    }

    $("#pName").text(pName)
    $("#pName1").text(pName)

    bookingId = '';
    pName = '';

}

function setBookingData(bookingid, pName, age, mobNo, isdate, occSdate, mobNo2, gender, countryid_, stateid_, dist, cityid_, pincode, reasonambulance, add, typeAmb, reqAmb) {
    $("#countryid_").val(countryid_).trigger('change');
    getStateList(stateid_);
    $("#bookingid").val(bookingid);
    $("#patientName").val(pName);
    $("#age").val(age);
    $("#mobNo").val(mobNo);
    $("#isdate").val(isdate);
    $("#occSdate").val(occSdate);
    $("#mobNo2").val(mobNo2);
    $("#gender").val(gender).trigger('change');
    setTimeout(function() {
        getDistrictDetails(dist);
    }, 2000);
    setTimeout(function() {
        getCityDetails(cityid_);
    }, 3000);
    $("#pincode").val(pincode);
    $("#reasonambulance").val(reasonambulance);
    $("#add").text(add);
    $("#typeAmb").val(typeAmb).trigger('change');
    $("#reqAmb").val(reqAmb).trigger('change');
    $("#save").hide();



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
        maxWidth: 30,
        pinned: 'left',

    },
    {
        headerName: "Ambulance No",
        field: "item_name",
        pinned: 'left',
        maxWidth: 130,
    }, {
        headerName: "Type",
        field: "type",
        hide: true,
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Driver Id",
        field: "driverId",
        hide: true,
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Driver Name",
        field: "assigned_username",
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Ambulance Name",
        field: "asset_code",
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Driver Mobile No",
        field: "driverMob",
        hide: true,
        cellStyle: {
            textAlign: 'center'
        },
    }, {
        headerName: "Availability Status",
        field: "avl_status",
        cellStyle: {
            textAlign: 'center'
        },
        cellRenderer: function(params) {
            if (params.data.avl_status == "Available") {
                return '<a style="color:green;font-weight: bold;">' + params.data.avl_status + '</a>';
            } else {
                return '<a style="color:red;font-weight: bold;">' + params.data.avl_status + '</a>';
            }
        }
    }, {
        headerName: "Available On",
        field: "avl_on",
        cellStyle: {
            textAlign: 'center'
        },
        cellRenderer: function(params) {
            if (params.data.avl_on != "--") {
                return '<a style="font-weight: bold;">' + params.data.avl_on + '</a>';
            } else {
                return '--';
            }
        }
    }
];


// let the grid know which columns and what data to use product table
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
        flex: 1,
        minWidth: 150,
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


//parents new plan
function newPlan() {
    $('#patientName, #age, #mobNo, #mobNo2, #gender, #countryid_, #stateid_, #dist, #cityid_, #pincode, #add, #reasonambulance, #typeAmb, #reqAmb').prop('disabled', false).val("");
    $("#bookingid").val("");
    $("#save").hide();
    $('.pay-tab').addClass('hidden');
    currentDateAndTime();
    if (gridOptions.api) {
        gridOptions.api.deselectAll();
    }
    if (activityOptions.api) {
        activityOptions.api.deselectAll();

        activityOptions.api.forEachNode(function(node) {
            if (node.data) {
                console.log(node.data)
                node.setSelected(false);
                node.selectable = true;
            }
        });
    }


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
    //$("#ambulanceDetail").removeClass('hidden');
    //$("#bookingAmbulance").addClass('hidden');


    $(".nav-link").removeClass("active");
    $(".nav-link[href='#ambulanceDetail']").addClass("active");
    // Activate the "Doctor Details" tab
    $("#tab-ambulanceDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-bookingAmbulance").removeClass("active").attr("aria-selected", "false");

    // Show the "Doctor Details" tab content
    $("#bookingAmbulance").hide();
    $("#ambulanceDetail").show();
    $("#paymentDetail").hide();

}

function next1() {
    $(".pay-tab").addClass("active");
    $(".nav-link").removeClass("active");
    $(".pay-tab a[href='#paymentDetail']").addClass("active");

    $("#tab-ambulanceDetail").removeClass("active").attr("aria-selected", "false");
    $("#tab-bookingAmbulance").removeClass("active").attr("aria-selected", "false");
    $("#tab-paymentDetail").addClass("active").attr("aria-selected", "true");

    $("#bookingAmbulance").hide();
    $("#ambulanceDetail").hide();
    $("#paymentDetail").show();
}


function prev() {

    $(".nav-link").removeClass("active");
    $(".nav-link[href='#bookingAmbulance']").addClass("active");

    $("#tab-bookingAmbulance").addClass("active").attr("aria-selected", "true");
    $("#tab-ambulanceDetail").removeClass("active").attr("aria-selected", "false");

    $("#bookingAmbulance").show();
    $("#ambulanceDetail").hide();
    $("#paymentDetail").hide();
}

function billingPrevious() {

    $(".nav-link").removeClass("active");
    $(".nav-link[href='#ambulanceDetail']").addClass("active");
    $("#tab-ambulanceDetail").addClass("active").attr("aria-selected", "true");
    $("#tab-bookingAmbulance").removeClass("active").attr("aria-selected", "false");

    $("#bookingAmbulance").hide();
    $("#ambulanceDetail").show();
    $("#paymentDetail").hide();
}


function cancel() {
    gridOptions.api.setQuickFilter(null);
    $('#quickFilter').val('');
    $('#totalAsset').find('span').html(gridOptions.api.getModel().getRowCount());


    $(".formValidation").remove();
    $("#totalAsset").show();
    $("#searchRowDiv").show();
    $(".btn-hs").show();
    $("#myGrid").show();
    $("#demo").hide();
    $("#reqDltBtn").attr('disabled', true);
    $("#newBtn").attr('disabled', false);

    $("#patientId").val('');
    $("#bookingid").val('');
    $("#patientName").val('');
    $("#age").val('');
    $("#mobNo").val('');
    $("#mobNo2").val('');
    $("#countryid_").val('');
    $("#stateid_").val('');
    $("#dist").val('');
    $("#cityid_").val('');
    $("#pincode").val('');
    $("#add").val('');
    $("#reasonambulance").val('');
    $("#gender").val('');

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

//master save

function masterSaveData() {


    /*if (mobValid1 && mobValid2 && validProductData()) {*/


    var ambulanceData = [];
    activityOptions.api.forEachNode(function(rowNode, index) {
        if (rowNode.isSelected()) {
            var item = rowNode.data;
            ambulanceData.push(item);
        }
    });

    if ($("#patientName").val() == null || $("#patientName").val() == '') {
        toastr.error("Patient name required");
        prev();
        return;
    }
    if ($("#age").val() == null || $("#age").val() == '') {
        toastr.error("Age required");
        prev();
        return;
    }
    if ($("#mobNo").val() == null || $("#mobNo").val() == '') {
        toastr.error("Mobile number required");
        prev();
        return;
    }
    if ($("#gender").val() == null || $("#gender").val() == '') {
        toastr.error("Gender required");
        prev();
        return;
    }
    if ($("#countryid_").val() == null || $("#countryid_").val() == '') {
        toastr.error("Country required");
        prev();
        return;
    }
    if ($("#stateid_").val() == null || $("#stateid_").val() == '') {
        toastr.error("State required");
        prev();
        return;
    }
    if ($("#dist").val() == null || $("#dist").val() == '') {
        toastr.error("District required");
        prev();
        return;
    }
    if ($("#cityid_").val() == null || $("#cityid_").val() == '') {
        toastr.error("City required");
        prev();
        return;
    }
    if ($("#pincode").val() == null || $("#pincode").val() == '') {
        toastr.error("Pincode required");
        prev();
        return;
    }
    if ($("#add").val() == null || $("#add").val() == '') {
        toastr.error("Address required");
        prev();
        return;
    }

    if (ambulanceData?.length == 0) {
        toastr.error("Select an ambulance");
        return;
    }


    var datas = [];

    var obj = {};
    obj.bookingId = $("#bookingid").val();
    obj.patientId = $("#patientId").val();
    obj.patientName = $("#patientName").val();
    obj.age = $("#age").val();
    obj.mobNo = $("#mobNo").val();
    obj.isdate = $("#isdate").val();
    obj.time = $("#occSdate").val();
    obj.mobNo2 = $("#mobNo2").val();
    obj.countryid = $("#countryid_").val();
    obj.stateid = $("#stateid_").val();
    obj.dist = $("#dist").val();
    obj.cityid = $("#cityid_").val();
    obj.pincode = $("#pincode").val();
    obj.add = $("#add").val();
    obj.reasonambulance = $("#reasonambulance").val();
    obj.gender = $("#gender").val();
    obj.typeAmb = $("#typeAmb").val();
    obj.reqAmb = $("#reqAmb").val();
    obj.amublanceNo = ambulanceData[0].item_name;
    obj.driverId = ambulanceData[0].assigned_user;
    obj.type = bookFromType;

    console.log(obj);
    saveAmbulaneBooking(obj);

}

function nextTab(id) {
    const tabElement = document.querySelector('#' + id + ' a');
    console.log(tabElement)
    const tab = new bootstrap.Tab(tabElement);
    tab.show();
}

/*}*/

function saveAmbulaneBooking(datas) {
    $('.loader').show();
    $("body").addClass("overlay");
    $.ajax({
        type: "POST",
        url: "booking-ambulance-add",
        contentType: "application/json",
        data: JSON.stringify(datas),
        success: function(response) {
            if (response.code == "Success") {
                $('.loader').hide();
                $("body").removeClass("overlay");
                toastr.success('Ambulance booked successfully');
                getAllAmbulanceDetails();
            } else {
                $('.loader').hide();
                $("body").removeClass("overlay");
            }

        },
        error: function(datas) {
            console.log(datas)
            $('.loader').hide();
            $("body").removeClass("overlay");
        }
    })


}

function validFormData() {
    var allValid = true;

    if (!validationUpdated("patient name Required", 'patientName')) {
        allValid = false;
    }
    if (!validationUpdated("Mobile No Required", 'mobNo')) {
        allValid = false;
    }

    if (!validationUpdated("Country Required", 'countryid_')) {
        allValid = false;
    }

    if (!validationUpdated("State Required", 'stateid_')) {
        allValid = false;
    }

    if (!validationUpdated("Dist Required", 'dist')) {
        allValid = false;
    }
    if (!validationUpdated("City Required", 'cityid_')) {
        allValid = false;
    }
    if (!validationUpdated("Pin code Required", 'pincode')) {
        allValid = false;
    }
    if (!validationUpdated("Address Required", 'add')) {
        allValid = false;
    }
    mobVal1();
    mobVal2();

    return allValid;
}

function validProductData() {
    var item = null;

    activityOptions.api.forEachNode(function(rowNode, index) {
        if (rowNode.isSelected()) {
            let selectedRows = rowNode.data;

            if (selectedRows.availability === "0") {
                console.log(selectedRows)
                item = selectedRows;
                console.log("Selected Row Data:", item);

            } else {
                console.log(selectedRows, 'aaa')

            }

        }

    });
    if (item) {
        console.log(item)
        return true;
    } else {
        toastr.error("Please Give one Available Ambulance");
        return false;
    }

}

// delete Ambulance
function deleteFun() {

    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedRowsString = '';
    selectedRows.forEach(function(selectedRow, index) {
        if (index > 0) {
            selectedRowsString += ',';
        }

        selectedRowsString += selectedRow.bookingId;
    });

    var id = selectedRowsString;
    $.ajax({
        type: "GET",
        url: "booking-ambulance-delete?id=" + id,
        dataType: "json",
        contentType: "application/json",
        success: function(response) {
            if (response.message == "Success") {
                getAllAmbulanceDetails();
            }
        },
        error: function(response) {
            console.log(response);
        }
    })
}

//OnChange district
function getDistrictDetails(dist) {
    let state = $('#stateid_').val();


    if (state) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "booking-ambulance-districtList?id=" + state,
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
                    if (dist) {
                        $("#dist").val(dist);
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


//OnChange City
function getCityDetails(cityid_) {
    let dist = $('#dist').val();


    if (dist) {
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $.ajax({
            type: "GET",
            url: "booking-ambulance-CityList?id=" + dist,
            success: function(response) {
                if (response.message == "success") {
                    $("#cityid_").empty();
                    var option = $("<option></option>");
                    $(option).val(null);
                    $(option).html("Select");
                    $("#cityid_").append(option);
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#cityid_").append(option);
                    }
                    if (cityid_) {
                        $("#cityid_").val(cityid_);
                    }
                }
            },
            error: function(e) {}
        });
    } else {
        $("#cityid_").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
        $("#cityid_").append(option);
        $("#cityid_").empty();
        var option = $("<option></option>");
        $(option).val(null);
        $(option).html("Select");
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
var mobValid3;

function mobVal3() {
    var mob = $('#mobNo2').val();
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
//Pincode validation
var mobValid2;

function mobVal2() {
    var pin = $('#pincode').val();
    var pincode = /^\d{6}$/;
    if (pin != '') {
        if (pincode.test(pin)) {

            $("#error1").hide();

            mobValid2 = true;
            return true;
        } else {

            $("#error1").html(
                "please enter  6 digit pin code");
            $("#error1").show();
            mobValid2 = false;
            return false;
        }
    } else {
        $("#error1").html(" Pin code is Required");
        mobValid2 = false;
        return false;
    }
}

function activityTabs(activityId) {
    if (activityId == "bookingAmbulance") {
        $("#bookingAmbulance").show();
        $("#ambulanceDetail").hide();
        $("#paymentDetail").hide();
    } else if (activityId == "ambulanceDetail") {
        $("#ambulanceDetail").show();
        $("#bookingAmbulance").hide();
        $("#paymentDetail").hide();
    } else if (activityId == "paymentDetail") {
        $("#paymentDetail").show();
        $("#bookingAmbulance").hide();
        $("#ambulanceDetail").hide();
    }

}

function getAllAmbulanceDetails() {

    agGrid.simpleHttpRequest({
        url: "booking-ambulance-view-through-ajax"
    }).then(function(data) {
        var jsonData = JSON.parse(data.body);
        var allData = jsonData?.viewBook;
        gridOptions.api.setRowData(allData);
        var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
        if (firstRowNode) {
            firstRowNode.setSelected(true);
        }

    });
}

function getAllAmbulanceList(amblNo) {

    let from = 'Ambulance';

    agGrid.simpleHttpRequest({
        url: 'booking-ambulance-list-view?from=' + bookFromType
    }).then(function(data) {
        var jsonData = JSON.parse(data.body);
        var allData = jsonData?.view;

        activityOptions.api.setRowData(allData);

        var rowIndexToSelect = -1;
        allData.forEach(function(rowData, index) {
            if (rowData.amublanceNo === amblNo) {
                rowIndexToSelect = index;
            }
        });

        /*activityOptions.api.forEachNode(function(node) {
        	if (node.data.amublanceNo === amblNo) {
        		node.setSelected(true);
        		node.selectable = true;
        	} else {
        		node.setSelected(false);
        		node.selectable = true;
        	}
        });*/

        if (rowIndexToSelect === -1) {
            console.log("No row found with ambulanceNo:", amblNo);
        }
    });
}

function currentDateAndTime() {

    var dateFormat = localStorage.getItem("dateFormat");
    $("#DateCalendar").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $("#isdate").val(todaysDate).prop('disabled', true);
    })

    $('#isdate').blur(function() {
        $("#DateCalendar").val($(this).val());
    })

    var todaysDate = getCurrentDate();
    $("#isdate").val(todaysDate);


    var dateFormat = localStorage.getItem("dateFormat");
    $("#DateCalendar3").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $('#occSdate').val($(this).val());
    })

    $('#occSdate').blur(function() {
        $("#DateCalendar3").val($(this).val());
    })

    $("#occSdate").datetimepicker({
        format: 'H:i',
        closeOnDateSelect: false,
        timepicker: true,
        datepicker: false,
        step: 15
    })
    var todaysTime = getCurrentTime();
    $("#occSdate").val(todaysTime);
    $("#save").hide();
}

function changeStatus(i) {
	let o = {};
	
	o.key = $("#ambBookingId").text();
	o.code = i;
	
	$('.loader').show();
    $("body").addClass("overlay");
    $.ajax({
        type: "POST",
        url: "booking-ambulance-change-status",
        contentType: "application/json",
        data: JSON.stringify(o),
        success: function(response) {
            if (response.code == "success") {
                $('.loader').hide();
                $("body").removeClass("overlay");
                
                if(i == 1) toastr.success('Accepted');
                else toastr.success('Rejected');
                
                getAllAmbulanceDetails();
            } else {
                $('.loader').hide();
                $("body").removeClass("overlay");
            }

        },
        error: function(datas) {
            console.log(datas)
            $('.loader').hide();
            $("body").removeClass("overlay");
        }
    })
}