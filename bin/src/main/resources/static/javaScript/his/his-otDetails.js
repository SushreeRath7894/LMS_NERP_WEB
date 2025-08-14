$(document).ready(function() {
	fetchSurgeons();
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

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();
	getAllPatientsDetails();

	var vitalGridDiv = document.querySelector('#vitalGrid');
	new agGrid.Grid(vitalGridDiv, gridOptionsVital);
	gridOptionsVital.api.setRowData();


	var nurseGridDiv = document.querySelector('#nurseGrid');
	new agGrid.Grid(nurseGridDiv, gridOptionsNurse);
	gridOptionsNurse.api.setRowData();
	
	var equipmentGridDiv = document.querySelector('#equipmentGrid');
	new agGrid.Grid(equipmentGridDiv, gridOptionsEquipment);
	gridOptionsEquipment.api.setRowData();

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
		+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").html(tbl);

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
		+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData1").html(tbl);

    
});

$(document).ready(function() {
    var surgenGridDiv = document.querySelector('#surgenGrid');
	new agGrid.Grid(surgenGridDiv, gridOptionsSurgen);
	gridOptionsSurgen.api.setRowData();
		
});

// ag grid

var columnDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Patient Name",
		field: "pName",
		flex: 1,
	},
	{
		headerName: "Patient Id",
		field: "patientId",
		flex: 1,
	},
	{
		headerName: "OPD/IPD Id",
		field: "opdId",
		flex: 1,

	},
	{
		headerName: "Procedure",
		field: "summary",
		flex: 1,

	},
	{
		headerName: "Tentive From Date",
		field: "fromDate",
		flex: 1,

	},
	{
		headerName: "Doctor Name",
		field: "doctorName",
		flex: 1,

	},
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
	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {
	},
	onSelectionChanged: rowSelect
};

let patientId="";
function rowSelect() {

	var selectedRows = gridOptions.api.getSelectedRows();
	console.log(selectedRows);
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = 0;
	selectedData.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (selectedRows.length > 0) {
		patientId = selectedRows[0].patientId;
		editOtDetails(patientId)

	} else {
		$("#fName").val('');
		$("#patientId").val('');
		$("#opdId").val('');
		$("#dateOfBirth").val('');
		$("#gender").val('');
		$("#mobNo").val('');
		$("#procedureName").val('');
		$("#fromDate").val('');
		$("#doctorName").val('');
		$("#summary").val('');
		gridOptionsSurgen.api.setRowData('');
		gridOptionsNurse.api.setRowData('');
		
	}


}

var columnDefVital = [
	{
		//headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 40,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Time & Date",
		field: "dateTime",
	},
	{
		headerName: "Body Temperature",
		field: "bodyTemp",
	},
	{
		headerName: "Heart Rate",
		field: "heartRate",
	},
	{
		headerName: "Respiratory Rate",
		field: "respiRate",
	},
	{
		headerName: "Blood Pressure",
		field: "bloodPress",
	},
	{
		headerName: "Weight",
		field: "weight",
	},
	{
		headerName: "Height",
		field: "height",
	}];

// Define grid options
var gridOptionsVital = {
	columnDefs: columnDefVital,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectVital
};

function rowSelectVital() {
	var selectedRows = window.gridApiNotes.getSelectedRows();
	if (selectedRows.length > 0) {
		$("#addBtn").hide();
		$("#dltBtn").show();
		$("#addNote").hide();
	} else {
		$("#addNote").show();
		$("#dltBtn").hide();
	}
}

var columnDefSurgen = [
	{
		//headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 40,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Procedure Name",
        field: "prc",
        hide: true
	},
	{
		 headerName: "Surgen List",
        field: "surgenList",
        width: 400,
	},
	 {
    headerName: 'Surgen Name',
    field: "surgenName",
    width: 400,
    cellStyle: { textAlign: 'center' },
    cellRenderer: (params) => {
        const selectElement = document.createElement('select');
        selectElement.name = "gridDropdown";
        selectElement.innerHTML = `<option value="">Loading...</option>`;
        fetchSurgeons().then(data => {
            if (data.length > 0) {
                selectElement.innerHTML = '<option value="">Select</option>' + 
                    data.map(item => `<option value="${item.empId}">${item.empName}</option>`).join('');
            } else {
                selectElement.innerHTML = `<option value="">No Data</option>`;
            }
        }).catch(error => {
            console.error("Error fetching surgeons:", error);
            selectElement.innerHTML = `<option value="">Error</option>`;
        });
        return selectElement;
    }
    

}
];

// Define grid options
var gridOptionsSurgen = {
	columnDefs: columnDefSurgen,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectSurgen
};

function rowSelectSurgen() {
	var selectedRows = window.gridApiNotes.getSelectedRows();
	console.log("selected rows-->",selectedRows);
}

function fetchSurgeons() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'ot-details-users-employee', 
            method: 'GET',
            success: function (response) {
                try {
                    const surgeonsString = response.body[0];  
                    const surgeons = JSON.parse(surgeonsString); 
                    resolve(surgeons);
                } catch (error) {
                    reject("Failed to parse response");
                }
            },
            error: function (err) {
                reject(err);
            }
        });
    });
}

function rowSelectSurgen() {
    var selectedRows = gridOptionsSurgen.api.getSelectedRows();
    console.log(selectedRows);
}




var columnDefNurse = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Procedure Name",
		field: "prc",
		hide: "true"
	},
	{
		headerName: "Nurse list",
		field: "surgenList",
		flex: 1,
	},
	 {
    headerName: 'Nurse Name',
    field: "surgenName",
    width: 400,
    cellStyle: { textAlign: 'center' },
    cellRenderer: (params) => {
        const selectElement = document.createElement('select');
        selectElement.name = "gridDropdown";
        selectElement.innerHTML = `<option value="">Loading...</option>`;
        fetchSurgeons().then(data => {
            if (data.length > 0) {
                selectElement.innerHTML = '<option value="">Select</option>' + 
                    data.map(item => `<option value="${item.empId}">${item.empName}</option>`).join('');
            } else {
                selectElement.innerHTML = `<option value="">No Data</option>`;
            }
        }).catch(error => {
            console.error("Error fetching surgeons:", error);
            selectElement.innerHTML = `<option value="">Error</option>`;
        });

        return selectElement;
    }
}];

// Define grid options
var gridOptionsNurse = {
	columnDefs: columnDefNurse,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,

	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {
	},
	onSelectionChanged: rowSelectNurse
};


function rowSelectNurse() {
	var selectedRows = gridOptionsSurgen.api.getSelectedRows();
	console.log(selectedRows);
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = 0;
	selectedData.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

}

function activityTabs(activityId) {
	if (activityId == "otInformation") {
		$("#otInformation").removeClass('hidden');
		$("#preoperation").addClass('hidden');
		$("#teamInformation").addClass('hidden');
		$("#procedureDetail").addClass('hidden');
		$("#observation").addClass('hidden');
		$("#attachment").addClass('hidden');

	} else if (activityId == "preoperation") {
		$("#otInformation").addClass('hidden');
		$("#preoperation").removeClass('hidden');
		$("#teamInformation").addClass('hidden');
		$("#procedureDetail").addClass('hidden');
		$("#observation").addClass('hidden');
		$("#attachment").addClass('hidden');

	} else if (activityId == "teamInformation") {
		$("#otInformation").addClass('hidden');
		$("#preoperation").addClass('hidden');
		$("#teamInformation").removeClass('hidden');
		$("#procedureDetail").addClass('hidden');
		$("#observation").addClass('hidden');
		$("#attachment").addClass('hidden');
		getSurgenList();

	} else if (activityId == "procedureDetail") {
		$("#otInformation").addClass('hidden');
		$("#preoperation").addClass('hidden');
		$("#teamInformation").addClass('hidden');
		$("#procedureDetail").removeClass('hidden');
		$("#observation").addClass('hidden');
		$("#attachment").addClass('hidden');

	} else if (activityId == "observation") {
		$("#otInformation").addClass('hidden');
		$("#preoperation").addClass('hidden');
		$("#teamInformation").addClass('hidden');
		$("#procedureDetail").addClass('hidden');
		$("#observation").removeClass('hidden');
		$("#attachment").addClass('hidden');

	} else if (activityId == "attachment") {
		$("#otInformation").addClass('hidden');
		$("#preoperation").addClass('hidden');
		$("#teamInformation").addClass('hidden');
		$("#procedureDetail").addClass('hidden');
		$("#observation").addClass('hidden');
		$("#attachment").removeClass('hidden');
	}

}


function getAllPatientsDetails() {

	agGrid.simpleHttpRequest({
		url: "ot-details-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.view;

		gridOptions.api.setRowData(allData);

		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true); 

		}

	});

}
function getSurgenList() {
	var prc = $("#procedureName").val();
	agGrid.simpleHttpRequest({
		url: "ot-details-surgen-view?Id=" + prc
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.viewSurgen;
		var allData1 = jsonData.viewNurse;

		gridOptionsSurgen.api.setRowData(allData);
		gridOptionsNurse.api.setRowData(allData1);


	});

}

function editOtDetails(patientId) {
	$.ajax({
		type: "GET",
		url: "ot-details-edit?Id=" + patientId,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const otDetails = JSON.parse(response.body).otDetails;

				console.log(otDetails, 'ot');

				if (otDetails == null) {
					$("#fName").val('');
					$("#patientId").val('');
					$("#opdId").val('');
					$("#dateOfBirth").val('');
					$("#gender").val('');
					$("#mobNo").val('');
					$("#procedureName").val('');
					$("#fromDate").val('');
					$("#doctorName").val('');
					$("#summary").val('');
					
				} else {
					$("#fName").val(otDetails[0].pName);
					$("#patientId").val(otDetails[0].patientId);
					$("#opdId").val(otDetails[0].opdId);
					$("#dateOfBirth").val(otDetails[0].dob);
					$("#gender").val(otDetails[0].gender);
					$("#mobNo").val(otDetails[0].mobNo);
					$("#procedureName").val(otDetails[0].procedureName);
					$("#fromDate").val(otDetails[0].fromDate);
					$("#doctorName").val(otDetails[0].doctorName);
					$("#summary").val(otDetails[0].summary);

				}


			}

		},


		error: function(error) {
			console.error("Error fetching patient details:", error);
		}
	});
}


function addVital() {

	$("#vitalGrid").hide();
	$("#demo").show();

	$("#bodyTemp").val('');
	$("#heartRate").val('');
	$("#respRate").val('');
	$("#bloodPres").val('');
	$("#weight").val('');
	$("#height").val('');
	$("#bmi").val('');
	clearVitalField()


}


function cancelVital() {

	$("#demo").hide();

	$("#vitalGrid").show();

}

 function saveSurgen() {
    var surgen = [];
    var surgenDropdownValues = []; 
    gridOptionsSurgen.api.forEachNode(function(rowNode) {
	console.log("Surgen--->",rowNode.data);
        surgen.push({
	
            list: rowNode.data.surgenList, 
            name: surgenDropdownValues.push(rowNode.data.surgenName)
        });
    });

    var nurse = [];
    gridOptionsNurse.api.forEachNode(function(rowNode) {
        nurse.push({
            list: rowNode.data.surgenList,
            name: rowNode.data.surgenName
        });
    });

    var surgenData = {
        patientId: patientId,
        opdId: opdId,
        surgen: surgen,
        nurse: nurse
    };

    console.log("Data is for test coming like this=======> ", surgenData);
     //saveSurgonList(surgenData);
}
	
	function saveSurgonList(surgenData) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "ot-details-save",
			contentType: "application/json",
			data: JSON.stringify(surgenData),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
	
	
	var columnDefEquipment = [
	{
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 40,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Equipment List",
		field: "equipmentList",
		width :"450"
	},
	{
		headerName: "Equipment Name",
		field: "equipmentName",
		width :"450"
	}];


var gridOptionsEquipment = {
	columnDefs: columnDefEquipment,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	pagination: true,
	paginationPageSize: 15,

	onSelectionChanged: rowSelectEquipment
};

function rowSelectEquipment() {
	var selectedRows = window.gridApiNotes.getSelectedRows();
	if (selectedRows.length > 0) {
		$("#addBtn").hide();
		$("#dltBtn").show();
		$("#addNote").hide();
	} else {
		$("#addNote").show();
		$("#dltBtn").hide();
	}
}


	agGrid.simpleHttpRequest({
		url: "ot-details-equipment-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.viewEquipment;

		gridOptionsEquipment.api.setRowData(allData);

		var firstRowNode = gridOptionsEquipment.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true); 

		}

	});


