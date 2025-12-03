let type = '';
$(document).ready(function() {

	const urlParams = new URLSearchParams(window.location.search);
		type = urlParams.get('id');
		
		if(type == null || type == 'null') {
			type = '';
		}

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
			quickFilterGrid(gridOptionsEmployee);
		}
	});

	var gridDivm = document.querySelector('#myGridEmployee');
	new agGrid.Grid(gridDivm, gridOptionsEmployee);
	getAllEmployeeDetails();

	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);
	activityOptions.api.setRowData();



	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#dateOfBirth').val($(this).val());
	})

	$('#dateOfBirth').blur(function() {
		$("#DateCalendar").val($(this).val());
	})




	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		$('#fDate').val($(this).val());
	})

	$('#fDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	//     date format TO date

	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		$('#tDate').val($(this).val());
	})

	$('#tDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})
});
function onQuickFilterChanged() {
	
		if(document.getElementById('quickFilter').value) {
			gridOptionsEmployee.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptionsEmployee.api.getDisplayedRowAtIndex(0).setSelected(true);
		}

}
function reset(){
	let data = $("#quickFilter").val('');
	if(data!=null && data!=""){
		$("#quickFilter").val('');
		gridOptionsEmployee.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptionsEmployee.api.getDisplayedRowAtIndex(0).setSelected(true);
	}
}


function getAllEmployeeDetails() {

	agGrid.simpleHttpRequest({
		url: "insurance-view?type="+type
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.insuranceDetails;

		gridOptionsEmployee.api.setRowData(allData);

		var firstRowNode = gridOptionsEmployee.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}

	});
}




// ag grid

var columnDefsEmployee = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Employee Id",
	field: "employeeId",
	width: 110,
	pinned: 'left',
}, {
	headerName: "Employee Name",
	field: "firstName",
	pinned: 'left',
},
{
	headerName: "Department",
	field: "department",
	width: 150,

},
{
	headerName: "Designation",
	field: "designation",
	width: 150,
}, {
	headerName: "Manager",
	field: "manager"
}, {
	headerName: "Date Of Birth",
	field: "dob",
	cellStyle: {
		textAlign: 'center'
	},
	width: 135,
}, {
	headerName: "Mobile No",
	field: "mobileNo",
	type: "rightAligned",
	width: 110,
}, {
	headerName: "	Personal Mail",
	field: "personalMail"
}];

var gridOptionsEmployee = {
	columnDefs: columnDefsEmployee,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	onFirstDataRendered: function(params) {
		params.api.selectIndex(0, true, true);
	},
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 185,
		height: 10
	},
	onSelectionChanged: onSelectionChanged,
};
var employeeId = "";
function onSelectionChanged() {
	var selectedNodes = gridOptionsEmployee.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	empid = selectedData.map(node => node.employeeId);
	var selectedRows = gridOptionsEmployee.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});

	if (selectedRows.length > 0) {
		employeeId = selectedRows[0].employeeId;
		editInsuranceDetails(employeeId)
		getNomineDetails(employeeId)
		getInsuranceType();
		document.querySelector("a[href='#basic']").click();
		
		$("#employeeNameTop1").text(selectedRows[0].firstName);
		$("#employeeNameTop").text(selectedRows[0].firstName);
		
		if(type=='self-service') {
			$('input[name=inlineRadioOptions]').attr("disabled",true);
			$("#fromDateCalendar,#toDateCalendar,#fDate,#tDate").addClass("disabled-icon");
		}
		
	} else {
		document.querySelector("a[href='#basic']").click();
		$("#employeeId").val('');
		$("#firstName").val('');
		$("#department").val('');
		$("#dob").val('');
		$("#mobileNo").val('');
		$("#personalMail").val('');
		$("input[name='inlineRadioOptions']").prop("checked", false);
		grtDtls("");


	}
	$("#employeeIdNomine").val(employeeId);
	$("#employeeIdInsurance").val(employeeId);
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
	headerName: "Id",
	field: "nomineId",
	width: 150,
}, {
	headerName: "Insured Name",
	field: "insuredName",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Date Of Birth",
	field: "dateOfBirth",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Age",
	field: "age",
	width: 150,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Relation",
	field: "depRelation",
	width: 150,
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

var nomineId = "";
function rowSelectdata() {
	var selectedRows = activityOptions.api.getSelectedRows();

	if (selectedRows.length > 0) {
		nomineId = selectedRows[0].nomineId;
		$("#edit").prop('disabled', false);
		$("#deleteChild").prop('disabled', false);
	} else {
	
		$("#edit").prop('disabled', true);
		$("#deleteChild").prop('disabled', true);
	}

	nomineId = "";
}


function activityTabs(activityId) {
	if (activityId == "basic") {
		$("#basic").removeClass('hidden');
		$("#employeeDetails").addClass('hidden');
	} else if (activityId == "employeeDetails") {
		$("#basic").addClass('hidden');
		$("#employeeDetails").removeClass('hidden');
		$("#saveTableData").hide();
	$("#cancelData").hide();
$("#edit").prop('disabled', true);
	$("#deleteChild").prop('disabled', true);
	}
}

function cancelInsurance() {

	$("#demo").hide();

	$("#activity").show();
	$("#saveTableData").hide();
	$("#cancelData").hide();
	$("#add").show();
	$("#edit").show();
	$("#deleteChild").show();
	  $("#save").show();
	  $("#prev").show();

}

function addInsurance() {

	if (activityOptions.api) {
		activityOptions.api.deselectAll();
	}
	$("#activity").hide();
	$("#demo").show();
	$("#saveTableData").show();
	$("#cancelData").show();
	$("#add").hide();
	$("#edit").hide();
	$("#deleteChild").hide();
	$("#save").hide();
	$("#prev").hide();
	

	$("#nomineId").val('');
	$("#insuredName").val('');
	$("#dateOfBirth").val('');
	$("#age").val('');
	$("#depRelation").val('');

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
				toastr.success('Please select a valid date of birth (not in the future)');
				/*$("#messageParagraph").text("Please select a valid date of birth (not in the future)");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/
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


function editInsuranceDetails(employeeId) {
	$.ajax({
		type: "GET",
		url: "insurance-details-edit?Id=" + employeeId,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const insuranceDetails = JSON.parse(response.body).insuranceDetails;


				if (insuranceDetails == null) {
					$("#employeeId").val('');
					$("#firstName").val('');
					$("#department").val('');
					$("#dob").val('');
					$("#mobileNo").val('');
					$("#personalMail").val('');
				} else {
					$("#employeeId").val(insuranceDetails[0].employeeId || "");
					$("#firstName").val(insuranceDetails[0].firstName || "");
					$("#department").val(insuranceDetails[0].department || "");
					$("#dob").val(insuranceDetails[0].dob || "");
					$("#mobileNo").val(insuranceDetails[0].mobileNo || "");
					$("#personalMail").val(insuranceDetails[0].personalMail || "");
					$("#insuranceId").val(insuranceDetails[0].insuranceId || "");


					if (insuranceDetails[0].insuranceTypeId) {
						$("input[name='inlineRadioOptions'][value='" + insuranceDetails[0].insuranceTypeId + "']").prop("checked", true);
						grtDtls(insuranceDetails[0].insuranceTypeId);
					} else {
						$("input[name='inlineRadioOptions']").prop("checked", false);
						grtDtls("");
					}


				}


			}

		},


		error: function(error) {
			console.error("Error fetching patient details:", error);
		}
	});
}

/*add nomine*/


function masterSaveNomine() {
	var data = {};

	data.employeeId = $("#employeeId").val();
	data.nomineId = $("#nomineId").val();
	data.insuredName = $("#insuredName").val();
	data.dateOfBirth = $("#dateOfBirth").val();
	data.age = $("#age").val();
	data.depRelation = $("#depRelation").val();
	console.log(data, 'dataa')


	if (data.insuredName == "" || data.insuredName == null) {
		toastr.error("Insured Name Required");
		return;
	}
	if (data.dateOfBirth == "" || data.dateOfBirth == null) {
		toastr.error("Date Of Birth Required");
		return;
	}
	if (data.age == "" || data.age == null) {
		toastr.error("Age Required");
		return;
	}
	if (data.depRelation == "" || data.depRelation == null) {
		toastr.error("Relation Required");
		return;
	}

	saveNomine(data);
}


function saveNomine(data) {
	$.ajax({
		type: "POST",
		url: "insurance-nominee-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "Success") {
				toastr.success(response.message);

				var idd = $("#employeeId").text();
				cancelInsurance();
				getNomineDetails(employeeId)
                    $("#save").show();
                    $("#prev").show();
			}
		},
		error: function(response) {
			console.log(response);
		}
	})
}

function getNomineDetails(employeeId) {
	agGrid.simpleHttpRequest({
		url: 'insurance-nominee-view?employeeId=' + employeeId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.nomineDetails;
		console.log(allData)

		if (allData == null) {
			activityOptions.api.setRowData('');

		} else {
			activityOptions.api.setRowData(allData);

		}
	});
}


// edit nomine
function editInsurance() {

	var selectedRows = activityOptions.api.getSelectedRows();

	if (selectedRows.length > 0) {
		var nomineId = selectedRows[0].nomineId;
	}
console.log("nomineId==========",nomineId);
	$.ajax({
		type: "GET",
		url: "insurance-hrms-details-edit?id=" + nomineId,
		success: function(response) {
			if (response.code === "success") {
				addInsurance();
				var jsonData = JSON.parse(response.body);
					var allData=jsonData.insuranceDetails;
				$("#nomineId").val(allData[0].nomineId);
				$("#insuredName").val(allData[0].insuredName);
				$("#dateOfBirth").val(allData[0].dateOfBirth);
				$("#age").val(allData[0].age);
				$("#depRelation").val(allData[0].depRelation);
				
	$("#edit").prop('disabled', true);
	$("#deleteChild").prop('disabled', true);

			}
		},
		error: function(error) {
			console.error("Error fetching patient details:", error);
		}
	});
}

function grtDtls(id) {
	agGrid.simpleHttpRequest({
		url: 'insurance-name-view?id=' + id
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.nameDetails;

		if (allData && allData.length > 0) {
			$("#insuranceTypeId").val(allData[0].insuranceTypeId || "");
			$("#insuranceName").val(allData[0].insuranceName || "");
			$("#insuranceProvider").val(allData[0].insuranceProvider || "");
			$("#insuredAmount").val(allData[0].insuredAmount || "");
			$("#fDate").val(allData[0].fDate || "");
			$("#tDate").val(allData[0].tDate || "");
		} else {
			$("#insuranceTypeId").val("");
			$("#insuranceName").val("");
			$("#insuranceProvider").val("");
			$("#insuredAmount").val("");
			$("#fDate").val("");
			$("#tDate").val("");
		}
	})
}


function next() {
	if ($("#insuranceTypeId").val() == "" || $("#insuranceTypeId").val() == null) {
		toastr.error("Insurance Name Required");
		return;
	}
	$("#employeeDetails").removeClass('hidden');
	$("#basic").addClass('hidden');


	$(".nav-link").removeClass("active");
	$(".nav-link[href='#employeeDetails']").addClass("active");
	$("#tab-employeeDetails").addClass("active").attr("aria-selected", "true");
	$("#tab-basic").removeClass("active").attr("aria-selected", "false");

	$("#employeeDetails").addClass("show active");
	$("#basic").removeClass("show active");
	
	
		$("#saveTableData").hide();
	$("#cancelData").hide();
	$("#edit").prop('disabled', true);
	$("#deleteChild").prop('disabled', true);
	

}

function prev() {
	$("#basic").removeClass('hidden');
	$("#employeeDetails").addClass('hidden');


	$(".nav-link").removeClass("active");
	$(".nav-link[href='#basic']").addClass("active");
	$("#tab-basic").addClass("active").attr("aria-selected", "true");
	$("#tab-employeeDetails").removeClass("active").attr("aria-selected", "false");

	$("#basic").addClass("show active");
	$("#employeeDetails").removeClass("show active");
	
}


function masterSaveInsurance() {
	var data = {};

	data.employeeId = $("#employeeId").val();
	data.insuranceId = $("#insuranceId").val();
	data.insuranceTypeId = $("#insuranceTypeId").val();
	data.insuranceName = $("#insuranceName").val();
	data.insuranceProvider = $("#insuranceProvider").val();
	data.insuredAmount = $("#insuredAmount").val();
	data.fDate = $("#fDate").val();
	data.tDate = $("#tDate").val();
	console.log(data, 'dataa')

	// Assuming `gridOptions` is your AG Grid instance
	var allRows = [];
	activityOptions.api.forEachNode(function(rowNode) {
		allRows.push(rowNode.data);
	});

	console.log("allrows-->",allRows);
	
	if(allRows.length > 0){
		console.log("in if block");
		saveInsurance(data);
	}
	else{
		console.log("in else  block");
		toastr.error("Nominee Details Required");
		return;
	}

}
function saveInsurance(data) {
	$.ajax({
		type: "POST",
		url: "insurance-details-add",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "Success") {
				toastr.success(response.message);
				document.querySelector("a[href='#basic']").click();

				var idd = $("#employeeId").text();

			}
		},
		error: function(response) {
			console.log(response);
		}
	})
}


function deleteInsurance() {
	var selectedNodes = activityOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var nomineId = selectedData[0].nomineId;
	
	var selectedNodes = gridOptionsEmployee.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	empid = selectedData.map(node => node.employeeId);
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
			$.ajax({
				type: "GET",
				url: "insurance-nomine-details-delete?id=" + nomineId,
				success: function(response) {
					console.log("response.code---------",response.code);
					if (response.code == "success") {
						$(".loader").hide();
						getNomineDetails(empid);
						
	$("#edit").prop('disabled', true);
	$("#deleteChild").prop('disabled', true);
					} else {
						$(".loader").hide();
						
					}

				}
			});
		}
	});
}

function getInsuranceType() {
    agGrid.simpleHttpRequest({
        url: '/master/view-insurance-type'
    }).then(function(data) {
        console.log("Fetched Data:", data);
        
        let container = document.getElementById("insuranceTypeData");
        container.innerHTML = ""; // Clear previous entries

        data.forEach((item, index) => {
            if (item.insuranceId && item.insuranceName) {
                let radioDiv = document.createElement("div");
                radioDiv.className = "form-check form-check-inline";

                let radioInput = document.createElement("input");
                radioInput.className = "form-check-input";
                radioInput.type = "radio";
                radioInput.name = "insuranceOptions";
                radioInput.id = "insurance_" + index;
                radioInput.value = item.insuranceId;
                radioInput.setAttribute("onchange", `grtDtls('${item.insuranceId}')`);

                let label = document.createElement("label");
                label.className = "form-check-label";
                label.setAttribute("for", "insurance_" + index);
                label.textContent = item.insuranceName;

                radioDiv.appendChild(radioInput);
                radioDiv.appendChild(label);
                container.appendChild(radioDiv);
            }
        });
    });
}