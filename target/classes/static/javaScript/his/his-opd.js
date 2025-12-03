
let activeTabId = '';
let checkboxesEnabled1 = false;
let checkboxesEnabled2 = false;
$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var gridDiv = document.querySelector('#treatmentGrid');
	new agGrid.Grid(gridDiv, gridOptionsTreatment);

	var gridDiv = document.querySelector('#testGridGrid');
	new agGrid.Grid(gridDiv, gridOptionsTest);

	/*var gridDiv = document.querySelector('#dietGridGrid');
	new agGrid.Grid(gridDiv, gridOptionsDiet);*/

	var gridDiv = document.querySelector('#notesGridGrid');
	new agGrid.Grid(gridDiv, gridOptionsNotes);

	//gridOptionsVital.api.setRowData();
	gridOptionsTreatment.api.setRowData();
	//gridOptionssymptoms.api.setRowData();
	gridOptionsTest.api.setRowData();
	//gridOptionsDiet.api.setRowData();
	gridOptionsNotes.api.setRowData();

	$(".br-s-btn").hide();
	$(".br-s-btn-tx").hide();
	$(".br-s-btn-tst").hide();
	$(".br-s-btn-d").hide();
	$(".br-s-btn-nt").hide();
	checkboxesEnabled1 = false;
	checkboxesEnabled2 = false;
	$('#medicinename').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#testName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#testName').change(function() {
		var selectedOption = $(this).find(':selected');
		$('#testCategory').val(selectedOption.attr('code'));
	});
	// date format FROM date
	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
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
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})

	$(".max-btn").on("click", function() {
		var parentCol = $(this).closest("col-md-6 pd-r pd-l");

		if (parentCol.hasClass("col-md-6")) {
			parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
			parentCol.siblings(".col-md-6").hide().fadeOut(500);
		} else {
			parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
			parentCol.siblings(".col-md-6").show().fadeIn(500);
		}
	});

	if ($('#vitalTab').hasClass('active')) {
		activeTabId = 'vital';
	}
	$(".nav-pills .nav-item .nav-link").on("click", function() {
		activeTabId = $(this).attr("href").substring(1);

	});
	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			onQuickFilterChanged()
		}
	});
	CKEDITOR.replace('notes', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	$("#prev").hide();
	
	$("#saveOt").hide();
    $("#cancelOt").hide();


});

/*for get patient and its type*/
function getAllPatientsDetails() {
	var fromdate = 1234;
	var todate = 1234;

	agGrid.simpleHttpRequest({
		url: "opd-manage-view?fromdate=" + fromdate + "&todate=" + todate
	}).then(function(response) {
		if (response.message === "Success") {

			const responseBody = JSON.parse(response.body);
			const patientData = responseBody.OPDRecords;

			window.gridApi.setRowData(patientData);

			if (patientData && patientData.length > 0) {
				window.gridApi.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});
			}

		} else {
			console.error("Failed to fetch data");
		}
	});
}

var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "OPD ID",
		field: "opdId",
	},
	{
		headerName: "Patient ID",
		field: "patientId",
	},
	{
		headerName: "Patient Name",
		field: "patientName"
	},
	{
		headerName: "Address",
		field: "patAddress"
	},
	{
		headerName: "Mobile",
		field: "patPhone"
	}];

// Define grid options
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {
		params.api.sizeColumnsToFit();
		window.gridApi = params.api;
		window.gridOptions = params.api;
		getAllPatientsDetails();
	},
	onSelectionChanged: rowSelect
};
let selectedPatientIds;
let selectedOpdIds;
function rowSelect() {
	var selectedRows = window.gridApi.getSelectedRows();

	if (selectedRows.length > 0) {
		$("#section21").show();
		var datas = selectedRows[0];
		selectedPatientIds = (datas.patientId);
		selectedOpdIds = (datas.opdId);
	} else {
		$("#section21").hide();
		selectedOpdIds = "";
		selectedPatientIds = "";
		getActivityDetails(activeTabId, selectedOpdIds);
	}

	var selectedRows = window.gridApi.getSelectedRows();

	if (selectedRows.length > 0) {
		var patient = selectedRows[0];
		selectedPatientIds = patient.patientId;
		var patientName = patient.patientName;
		var gender = patient.gender;
		var age = patient.age;

		$(".patientName").text(patientName);
		$(".gender").text(gender);
		$(".age").text(age);
		getActivityDetails(activeTabId, selectedOpdIds);
	} else {
		(selectedRows.length < 0 && activeTabId)
		if (activeTabId == 'vital') {
			clearAllField();
		} else if (activeTabId == 'treatment') {
			window.gridApi1.setRowData([]);

		} else if (activeTabId == 'diet') {
			window.gridApiDiet.setRowData([]);

		}
		$(".patientName").text('');
		$(".gender").text('');
		$(".age").text('');

	}

	editOtDetails()
}

var columnDefVital = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
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
	},
	{
		headerName: "Vital Id",
		field: "vitalId",
		hide: "true"
	}];

// Define grid options
/*var gridOptionsVital = {
	columnDefs: columnDefVital,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 2
	},
	onSelectionChanged: rowSelectVital
};*/

/*function rowSelectVital() {
	var selectedData = gridOptionsVital.api.getSelectedRows();
	if (selectedData && selectedData.length > 0) {
		$(".br-dis").attr("disabled", false);
	} else {
		$(".br-dis").attr("disabled", true);
	}
}
*/

var columnDefs1 = [
	{
		checkboxSelection: params => checkboxesEnabled1, // Dynamically enable/disable checkboxes
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Treatment Id",
		field: "treatmentId",
		hide: true,
	},
	{
		headerName: "Medicine Id",
		field: "medId",
		hide: true,
	},
	{
		headerName: "Medicine Name",
		field: "medName",
		flex: 2
	},
	{
		headerName: "Dosage",
		field: "dosage",
	},
	{
		headerName: "Frequency",
		field: "frequency"
	},
	{
		headerName: "Duration",
		field: "duration"
	},
	{
		headerName: "Instruction",
		field: "instruction",
	}];

// Define grid options
var gridOptionsTreatment = {
	columnDefs: columnDefs1,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1,
	},
	onGridReady: function(params) {
		window.gridApi1 = params.api;
		params.api.refreshCells({ force: true }); // Apply the disabled state on load
	},
	onSelectionChanged: rowSelect1,
};
function rowSelect1() {
	var selectedRows = window.gridApi1.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-tx").attr("disabled", false);
	} else {
		$(".br-dis-tx").attr("disabled", true);
	}
}

var columnDefs2 = [
	{
		checkboxSelection: params => checkboxesEnabled2,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Test Id",
		field: "testId",
		hide: true,
	},
	{
		headerName: "Test Name",
		field: "testName",
	},
	{
		headerName: "Test Category",
		field: "testCategory",
	}];

// Define grid options
var gridOptionsTest = {
	columnDefs: columnDefs2,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onGridReady: function(params) {
		console.log("Grid API initialized:", params.api);
		window.gridApi2 = params.api;
		params.api.refreshCells({ force: true }); // Apply the disabled state on load
	},
	onSelectionChanged: rowSelect2
};
function rowSelect2() {
	var selectedRows = window.gridApi2.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-tst").attr("disabled", false);
	} else {
		$(".br-dis-tst").attr("disabled", true);
	}
}
var columnDefDiet = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Diet Id",
		field: "dietId",
		hide: true,
	},
	{
		headerName: "Diet Type",
		field: "dietType",
		flex: 0.5
	},
	{
		headerName: "Specific Food Items",
		field: "specificFood",
	},
	{
		headerName: "Food Restrictions/Allergies",
		field: "restriction",
	},
	{
		headerName: "Supplement Recommendations",
		field: "suppliment",
	},
	{
		headerName: "Reason for Diet",
		field: "dietReason",
	},
	{
		headerName: "Plan Duration",
		field: "dietDuration",
		flex: 0.5
	}];

// Define grid options
var gridOptionsDiet = {
	columnDefs: columnDefDiet,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onGridReady: function(params) {
		console.log("Grid API initialized:", params.api);
		window.gridApiDiet = params.api;
	},
	onSelectionChanged: rowSelectDiet
};
function rowSelectDiet() {
	var selectedRows = window.gridApiDiet.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-d").attr("disabled", false);
	} else {
		$(".br-dis-d").attr("disabled", true);
	}
}

var columnDefNotes = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Note Id",
		field: "noteId",
		hide: true,
	},
	{
		headerName: "Notes",
		field: "notesDetail",

		cellRenderer: (params) => {
			if (!params.value) return ''; // Handle empty values

			// Decode Base64 if applicable
			const decodedValue = params.data.notesDetail && params.data.notesDetail !== 'null'
				? window.atob(params.data.notesDetail)
				: '';

			return decodedValue;
		}

	}];

// Define grid options
var gridOptionsNotes = {
	columnDefs: columnDefNotes,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onGridReady: function(params) {
		window.gridApiNotes = params.api;
	},
	onSelectionChanged: rowSelectNotes
};
function rowSelectNotes() {
	var selectedRows = window.gridApiNotes.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-nt").attr("disabled", false);
	} else {
		$(".br-dis-nt").attr("disabled", true);
	}
}



// You can use `selectedPatientIds` as needed
function saveVital() {
	var valid = true;
	var vitalData = {};
	vitalData.selectedPatientIds = selectedPatientIds;
	vitalData.selectedOpdIds = selectedOpdIds;
	vitalData['vitalId'] = $("#vitalId").val();
	vitalData['vitalIdSlNo'] = $("#vitalIdSlNo").val();
	vitalData['bodyTemp'] = $("#bodyTemp").val();
	vitalData['heartRate'] = $("#heartRate").val();
	vitalData['respRate'] = $("#respRate").val();
	vitalData['bloodPres'] = $("#bloodPres").val();
	vitalData['weight'] = $("#weight").val();
	vitalData['height'] = $("#height").val();
	vitalData['bmi'] = $("#bmi").val();

	console.log(vitalData);

	if (vitalData.selectedPatientIds == "" || vitalData.selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	} else {
		if (vitalData.bodyTemp == null || vitalData.bodyTemp.trim() === "") {
			toastr.error('Body Temprature Required');
			return;
		}
		if (valid) {
			saveVitalData(vitalData);
		}
	}

}

function saveVitalData(vitalData) {
	console.log("data is coming for save============> ", vitalData);
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-vital-details",
		contentType: "application/json",
		data: JSON.stringify(vitalData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				toastr.success(response.message);
				cancelVital();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});

}

function editVital() {
	const selectedRows = gridOptionsVital.api.getSelectedRows();
	const selectedRowId = selectedRows[0].vitalId; // Assuming 'id' is the key for the row ID
	const selectedvitalIdSlNo = selectedRows[0].vitalIdSlNo; // Assuming 'id' is the key for the row ID
	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-vital?vitalId=" + selectedRowId + "&vitalIdSlNo=" + selectedvitalIdSlNo
	}).then(function(response) {
		if (response.code === "Success") {
			addVital();
			const responseBody = JSON.parse(response.body[0]);
			const vitalDetails = responseBody.vitalDetails[0];
			$("#vitalId").val(vitalDetails.vitalId);
			$("#vitalIdSlNo").val(vitalDetails.vitalIdSlNo);
			$("#bodyTemp").val(vitalDetails.bodyTemp).prop("disabled", false);
			$("#heartRate").val(vitalDetails.heartRate).prop("disabled", false);
			$("#respRate").val(vitalDetails.respiRate).prop("disabled", false);
			$("#bloodPres").val(vitalDetails.bloodPress).prop("disabled", false);
			$("#weight").val(vitalDetails.weight).prop("disabled", false);
			$("#height").val(vitalDetails.height).prop("disabled", false);
			$("#bmi").val(vitalDetails.bmi).prop("disabled", false);
		} else {
			console.log("Failed to fetch data");
		}
	});
}

/*add treatment*/
function saveTratment() {
	$("#treatmentGrid1").hide();
	$("#treatmentGrid").show();
	$("#saveAllTratmentData").show();
	var validTreatData = true;
	var treatmentIds = $("#treatmentIds").val();
	var medId = $("#medicinename").val();
	var medName = $("#medicinename option:selected").text();
	var dosage = $("#dosage").val();
	var frequency = $("#frequency").val();
	var duration = $("#duration").val();
	var instruction = $("#instruction").val();
	var treatmentTypes = $("#treatmentTypes").val();
	let id = $("#txSlNo").val();

	if (selectedPatientIds == "" || selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	}
	if (medName == null || medName.trim() === "") {
		toastr.error('Medicine Name Required');
		return;
	}
	if (dosage == null || dosage.trim() === "") {
		toastr.error('Dosage Required ');
		return;
	}
	if (frequency == null || frequency.trim() === "") {
		toastr.error('Frequency Required');
		return;
	}

	if (validTreatData) {
		let dataset = [];
		let lastSelectedSlNo = '0';

		// Retrieve existing grid data
		gridOptionsTreatment.api.forEachNode((node) => {
			dataset.push(node.data);
			lastSelectedSlNo = node.data.id;
		});

		if (id != null && id != '') {
			// Update existing row
			const index = dataset.findIndex(obj => obj.id?.toString() === id?.toString());
			if (index !== -1) {
				dataset[index].id = id?.toString();
				dataset[index].medId = medId;
				dataset[index].medName = medName;
				dataset[index].dosage = dosage;
				dataset[index].frequency = frequency;
				dataset[index].duration = duration;
				dataset[index].instruction = instruction;
				dataset[index].treatmentTypes = treatmentTypes;
				dataset[index].delFlag = 0;
			}
		} else {
			// Add new row if not duplicate
			var treatmentData = {
				selectedPatientIds: selectedPatientIds,
				selectedOpdIds: selectedOpdIds,
				tratmentId: treatmentIds,
				medId: medId,
				medName: medName,
				dosage: dosage,
				frequency: frequency,
				duration: duration,
				instruction: instruction,
				treatmentTypes: treatmentTypes,
				delFlag: 0,
				id: generateUUID()
			};
			dataset.push(treatmentData);
		}
		gridOptionsTreatment.api.setRowData(dataset);
		$(".br-m-btn-tx").show();
		$(".br-s-btn-tx").hide();
	}
}


function saveAllTratmentData(dataset) {
	var totalRowCount1 = gridOptionsTreatment.api.getModel().getRowCount();
	if (totalRowCount1 > 0) {
		var treatmentData = {
			selectedPatientIds: selectedPatientIds,
			selectedOpdIds: selectedOpdIds
		};

		var datas = [];
		gridOptionsTreatment.api.forEachNode(function(rowNode, index) {
			var item1 = rowNode.data;
			datas.push(item1);
		});
	} else {
		toastr.error('At Least One Treatment Detail Is Required');
	}
	console.log('datas===', datas)
	if (datas) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-treatment-details",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success(response.message);
					
					cancelTreatment();
					getActivityDetails(activeTabId, selectedOpdIds);
					
				} else {
					toastr.error(response.message);
					$('.loader').hide();
				}
				$(".br-dis-tx").attr("disabled", true);
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
}

function editTreatmentDetails() {
	let selectedData = gridOptionsTreatment.api.getSelectedRows();
	if (selectedData[0].id) {
		addTreatment();
		$("#treatmentIds").val(selectedData[0].treatmentId);
		$("#medicinename").val(selectedData[0].medId).trigger('change');
		$("#dosage").val(selectedData[0].dosage);
		$("#frequency").val(selectedData[0].frequency);
		$("#duration").val(selectedData[0].duration);
		$("#instruction").val(selectedData[0].instruction);
		$("#treatmentTypes").val(selectedData[0].type);
		$("#txSlNo").val(selectedData[0].id);
	}
}
function deleteTreatment() {
	let selectedData = gridOptionsTreatment.api.getSelectedRows();
	if (selectedData[0].id) {
		gridOptionsTreatment.api.applyTransaction({ remove: selectedData });
	}
}
/*add test*/
function saveTest() {
	$("#testGridGrid").show();
		$("#saveAllTestData").show();
	var validTestData = true;
	var testId = $("#testId").val();
	var testItemId = $("#testName").val();
	var testName = $("#testName option:selected").text();
	var testCategory = $("#testCategory").val();
	let id = $("#tstSlNo").val();

	if (selectedPatientIds == "" || selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	}
	if (testName == null || testName.trim() === "") {
		toastr.error('Test Name Required');
		return;
	}

	if (validTestData) {
		let dataset = [];
		let lastSelectedSlNo = '0';

		// Retrieve existing grid data
		gridOptionsTest.api.forEachNode((node) => {
			dataset.push(node.data);
			lastSelectedSlNo = node.data.id;
		});

		if (id != null && id != '') {
			// Update existing row
			const index = dataset.findIndex(obj => obj.id?.toString() === id?.toString());
			if (index !== -1) {
				dataset[index].id = id?.toString();
				dataset[index].testId = testId;
				dataset[index].testItemId = testItemId;
				dataset[index].testName = testName;
				dataset[index].testCategory = testCategory;
				dataset[index].delFlag = 0;
			}
		} else {
			// Add new row if not duplicate
			var testData = {
				selectedPatientIds: selectedPatientIds,
				selectedOpdIds: selectedOpdIds,
				testId: testId,
				testItemId: testItemId,
				testName: testName,
				testCategory: testCategory,
				delFlag: 0,
				id: generateUUID()
			};
			dataset.push(testData);
		}
		gridOptionsTest.api.setRowData(dataset);
		$(".br-m-btn-tst").show();
		$(".br-s-btn-tst").hide();


	}
}

function saveAllTestData() {
	var totalRowCount1 = gridOptionsTest.api.getModel().getRowCount();
	if (totalRowCount1 > 0) {
		var testData = {
			selectedPatientIds: selectedPatientIds,
			selectedOpdIds: selectedOpdIds
		};

		var datas = [];
		gridOptionsTest.api.forEachNode(function(rowNode, index) {
			var item1 = rowNode.data;
			datas.push(item1);
		});
	} else {
		toastr.error('At Least One Test Detail Is Required');
	}
	console.log('datas===', datas)
	if (datas) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-opd-test-details",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success(response.message);
					cancelTest();
					getActivityDetails(activeTabId, selectedOpdIds);
				
				} else {
					$('.loader').hide();
					toastr.error(response.message);
				}
				$(".br-dis-tst").attr("disabled", true);
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
}

function editTestDetails() {
	let selectedData = gridOptionsTest.api.getSelectedRows();
	if (selectedData[0].id) {
		addTest();
		$("#testId").val(selectedData[0].testId);
		$("#testName").val(selectedData[0].testId).trigger('change');
		$("#testItemId").val(selectedData[0].testItemId);
		$("#testCategory").val(selectedData[0].testCategory);
		$("#tstSlNo").val(selectedData[0].id);
	}
}


function deleteTest() {
	let selectedData = gridOptionsTest.api.getSelectedRows();
	if (selectedData[0].id) {
		gridOptionsTest.api.applyTransaction({ remove: selectedData });
	}
}

function saveDiet() {
	var validDietData = true;
	var dietData = {};
	dietData.selectedPatientIds = selectedPatientIds;
	dietData.selectedOpdIds = selectedOpdIds;
	dietData['dietId'] = $("#dietIds").val();
	dietData['dietSlNo'] = $("#dietSlNo").val();
	dietData['dietType'] = $('input[name=diet]:checked').val();
	dietData['specificFood'] = $("#specificFood").val();
	dietData['foodRestrictions'] = $("#foodRestrictions").val();
	dietData['suppliments'] = $("#suppliments").val();
	dietData['reasonForDiet'] = $("#reasonForDiet").val();
	dietData['supDuration'] = $("#supDuration").val();

	console.log("Data is for test coming like this=======> ", dietData);


	if (dietData.selectedPatientIds == "" || dietData.selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	} else {
		if (dietData.dietType == null || dietData.dietType.trim() === "") {
			toastr.error("Diet Type Required");
			return;
		}
		if (dietData.specificFood == null || dietData.specificFood.trim() === "") {
			toastr.error("Specic Food Required");
			return;
		}
		if (dietData.foodRestrictions == null || dietData.foodRestrictions.trim() === "") {
			toastr.error("Food Restriction Required");
			return;
		}
		if (validDietData) {
			saveDietDetails(dietData);
		}

	}
}

function saveDietDetails(dietData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-diet-details",
		contentType: "application/json",
		data: JSON.stringify(dietData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
				cancelDiet()
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis-d").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}


function editDietDetails() {
	const selectedRows = gridOptionsDiet.api.getSelectedRows();
	const selectedRowId = selectedRows[0].dietId; // Assuming 'id' is the key for the row ID
	const selecteddietSlNo = selectedRows[0].dietSlNo; // Assuming 'id' is the key for the row ID
	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-diet?dietId=" + selectedRowId + "&dietSlNo=" + selecteddietSlNo
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			const dietDetails = responseBody.dietDetails[0];
			addDiet();
			$("#dietIds").val(dietDetails.dietId);
			$("#dietSlNo").val(dietDetails.dietSlNo);
			if (dietDetails.dietType === 0) {
				$('#veg').prop('checked', true);
				$('#non-veg').prop('checked', false);
			} else if (dietDetails.dietType === 1) {
				$('#non-veg').prop('checked', true);
				$('#veg').prop('checked', false);
			} else {
				$('#veg').prop('checked', false);
				$('#non-veg').prop('checked', false);
			}
			$("#specificFood").val(dietDetails.specificFood);
			$("#foodRestrictions").val(dietDetails.restriction);
			$("#suppliments").val(dietDetails.suppliment);
			$("#reasonForDiet").val(dietDetails.dietReason);
			$("#supDuration").val(dietDetails.dietDuration);

		} else {
			console.log("Failed to fetch data");
		}
	});
}



function deleteDiet() {
	var selectedRows = window.gridApiDiet.getSelectedRows();
	var datas = selectedRows[0];
	const dietId = (datas.dietId);
	agGrid.simpleHttpRequest({
		url: "opd-manage-delete-diet?dietId=" + dietId,
		type: "GET",
	}).then(function(response) {
		if (response.code === "Success") {
			getActivityDetails(activeTabId, selectedOpdIds);

		} else {
			console.log("Failed to fetch data");
		}
	});
}


function saveNote() {
	$("#notes").val(CKEDITOR.instances.notes.getData());
	var notesData = {};
	notesData.selectedPatientIds = selectedPatientIds;
	notesData.selectedOpdIds = selectedOpdIds;
	notesData['notesId'] = $("#notesId").val();
	notesData['notesIdSlNo'] = $("#notesIdSlNo").val();
	notesData['notes'] = window.btoa($("#notes").val());
	if (notesData.selectedPatientIds == "" || notesData.selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	} else {
		saveNotesDetails(notesData);

	}
}

function saveNotesDetails(notesData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-opd-notes-details",
		contentType: "application/json",
		data: JSON.stringify(notesData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
				cancelNote();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis-nt").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}

function editNoteDetails() {
	const selectedRows = gridOptionsNotes.api.getSelectedRows();
	const selectedRowId = selectedRows[0].noteId; // Assuming 'id' is the key for the row ID
	const selectednotesIdSlNo = selectedRows[0].notesIdSlNo; // Assuming 'id' is the key for the row ID
	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-notes?noteId=" + selectedRowId + "&notesIdSlNo=" + selectednotesIdSlNo
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			const noteDetails = responseBody.noteDetails[0];
			addNote();
			$("#notesId").val(noteDetails.noteId);
			$("#notesIdSlNo").val(noteDetails.notesIdSlNo);
			setTimeout(function() {
				CKEDITOR.instances.notes.setData(window.atob(noteDetails.notesDetail));
			}, 500)
		} else {
			console.log("Failed to fetch data");
		}
	});
}

function deleteNote() {
	var selectedRows = window.gridApiNotes.getSelectedRows();
	var datas = selectedRows[0];
	const noteId = (datas.noteId);
	agGrid.simpleHttpRequest({
		url: "opd-manage-delete-note?noteId=" + noteId,
		type: "GET",
	}).then(function(response) {
		if (response.code === "Success") {
			cancelDiet();
		} else {
			console.log("Failed to fetch data");
		}
	});
}
function saveOt() {
	var validOtData = true;
	var otData = {};
	otData.selectedPatientIds = selectedPatientIds;
	otData.selectedOpdIds = selectedOpdIds;
	otData['procedureName'] = $("#procedureName").val();
	otData['otId'] = $("#otId").val();
	otData['summary'] = $("#summary").val();
	otData['fromDate'] = $("#fromDate").val();
	otData['toDate'] = $("#toDate").val();

	console.log("Data is for test coming like this=======> ", otData);
	if (otData.selectedPatientIds == "" || otData.selectedPatientIds == null) {
		toastr.error('Please select a patient to provide OPD details.');
		return;
	} else {
		if (otData.procedureName == null || otData.procedureName.trim() === "") {
			toastr.error("Procedure Name Required");
			return;
		}
		if (otData.summary == null || otData.summary.trim() === "") {
			toastr.error("Procedure Details Required");
			return;
		}
		if (otData.fromDate == null || otData.fromDate.trim() === "") {
			toastr.error("From Date Required");
			return;
		}
		if (otData.toDate == null || otData.toDate.trim() === "") {
			toastr.error("To Date Required");
			return;
		}
		if (validOtData) {
			saveOtData(otData);
		}

	}
}
function saveOtData(otData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-ot-details",
		contentType: "application/json",
		data: JSON.stringify(otData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
				editOtDetails();
			} else {
				$('.loader').hide();
			}
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}


function editOtDetails() {
	var selectedRows = window.gridApi.getSelectedRows();
	var datas = selectedRows[0];
	var selectedPatientIds1 = (datas.patientId);
	var selectedOpdIds1 = (datas.opdId);

	$("#otId").val('');
	$("#procedureName").val('');
	$("#summary").val('');
	$("#fromDate").val('');
	$("#toDate").val('');

	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-ot?opd=" + selectedOpdIds1 + "&pat=" + selectedPatientIds1
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			const otDetails = responseBody.otDetails[0];
			$("#otId").val(otDetails.otId);
			$("#procedureName").val(otDetails.procedure);
			$("#summary").val(otDetails.summary);
			$("#fromDate").val(otDetails.fromDate);
			$("#toDate").val(otDetails.toDate);

		} else {
			console.log("Failed to fetch data");
		}
	});
}

/************************* common ********************************/
function getActivityDetails(activeTabId, selectedOpdIds) {
	agGrid.simpleHttpRequest({
		url: "opd-manage-view-types?types=" + activeTabId + "&opdId=" + selectedOpdIds
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body);
			
			console.log("Data abcccc  :", responseBody);

			const vitalDetails = responseBody.vitalDetails || [];
			
			if (vitalDetails.length > 0) {
				$("#addVital").hide();
			} else{
				$("#addVital").show();
			}
			setVitalDetails(vitalDetails);
			
		/*	const sympDetails = responseBody.sympDetails || [];
			gridOptionssymptoms.api.setRowData(sympDetails);*/

			const sympDetails = responseBody.sympDetails || [];
			setSymptomDetails(sympDetails);
			//gridOptionssymptoms.api.setRowData(sympDetails);



			const treatmentDetails = responseBody.treatmentDetails || [];
			if (treatmentDetails.length > 0) {
				$(".tx").hide();
				/*checkboxesEnabled1 = false;
				gridOptionsTreatment.api.setColumnDefs(columnDefs1);
				gridOptionsTreatment.api.redrawRows();*/
				//setTreatmentDetails(treatmentDetails);

				getTreatmentHistory();
				$("#treatmentGrid1").show();
				$("#treatmentGrid").hide();
			} else {
				$(".tx").show();
				checkboxesEnabled1 = true;
				gridOptionsTreatment.api.setColumnDefs(columnDefs1); // Reapply column definitions
				gridOptionsTreatment.api.redrawRows();
			}
			// setTreatmentDetails(treatmentDetails);
			//gridOptionsTreatment.api.setRowData(treatmentDetails);
			getTreatmentHistory();
			$("#treatmentGrid1").show();
			$("#treatmentGrid").hide();

			const testDetails = responseBody.testDetails || [];
			if (testDetails.length > 0) {

				$("#testGridGrid").hide();
				$("#testGridGrid1").show();
				getTestHistory();
				$(".tst").hide();
				checkboxesEnabled2 = false;
				gridOptionsTest.api.setColumnDefs(columnDefs2); // Reapply column definitions
				gridOptionsTest.api.redrawRows();
			} else {
				$("#testGridGrid").show();
				$("#testGridGrid1").hide();
				$("#testDetailContainer").hide();
				$(".tst").show();
				checkboxesEnabled2 = true;
				gridOptionsTest.api.setColumnDefs(columnDefs2); // Reapply column definitions
				gridOptionsTest.api.redrawRows();
			}
			//gridOptionsTest.api.setRowData(testDetails);
			$("#testGridGrid").hide();
			$("#testGridGrid1").show();
			getTestHistory();

			const dietDetails = responseBody.dietDetails || [];
			if (dietDetails.length > 0) {
				//gridOptionsDiet.api.setRowData(dietDetails);
				getDietHistory();
			} else {
				getDietHistory();
			}
			const noteDetails = responseBody.noteDetails || [];
			gridOptionsNotes.api.setRowData(noteDetails);
			
		  const otDetails = responseBody.otDetails || [];
			setOtDetails(otDetails);


		}
	});
}
function getActId() {
	//$("#addBtn").show();
	$("#addTreatment").show();
	$("#addTest").show();
	$("#saveBtn").hide();
	$("#editBtn").hide();
	$("#dltBtn").hide();
	$("#addBtn").hide();
	$("#addDiet").show();
	$("#addNote").show();
	clearAllField();

	const activeTabHref = $(event.target).attr("href");
	const activeTabId = activeTabHref.replace("#", "");

	next_prev_showhide(activeTabId);
	if (activeTabId == 'ot') {
		editOtDetails();
	} else {
		getActivityDetails(activeTabId, selectedOpdIds);
	}
}

function next_prev_showhide(id) {
	if (id == 'vital') {
		$("#next").show();
		$("#prev").hide();
	} else if (id == 'ot') {
		$("#next").hide();
		$("#prev").show();
	} else {
		$("#next").show();
		$("#prev").show();
	}
}

/*function for next and previous button*/

function nextTab() {
	$("#" + activeTabId).removeClass('active show');
	if (activeTabId == 'vital') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#symptomsTab").addClass('active');
		activeTabId = 'symptoms';
	}else if (activeTabId == 'symptoms') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#treatmentTab").addClass('active');
		activeTabId = 'treatment';
	} else if (activeTabId == 'treatment') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#testTab").addClass('active');
		activeTabId = 'test';
	} else if (activeTabId == 'test') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#dietTab").addClass('active');
		activeTabId = 'diet';
	} else if (activeTabId == 'diet') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#noteTab").addClass('active');
		activeTabId = 'note';
	} else if (activeTabId == 'note') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#otTab").addClass('active');
		activeTabId = 'ot';
			} else if (activeTabId == 'ot') {
				$("#vitalTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
				$("#prescriptionTab").addClass('active');
				activeTabId = 'prescription';
	}
	if (activeTabId == 'ot') {
		editOtDetails();
	} else {
		getActivityDetails(activeTabId, selectedOpdIds);
	}
	$("#" + activeTabId).addClass('active show');
	next_prev_showhide(activeTabId);
}
function prevTab() {
	$("#" + activeTabId).removeClass('active show');
	if (activeTabId == 'ot') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#noteTab").addClass('active');
		activeTabId = 'note';
	} else if (activeTabId == 'note') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#dietTab").addClass('active');
		activeTabId = 'diet';
	} else if (activeTabId == 'diet') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#testTab").addClass('active');
		activeTabId = 'test';
	} else if (activeTabId == 'test') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#treatmentTab").addClass('active');
		activeTabId = 'treatment';
	} else if (activeTabId == 'treatment') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#symptomsTab").addClass('active');
		activeTabId = 'symptoms';
	}else if (activeTabId == 'symptoms') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#vitalTab").addClass('active');
		activeTabId = 'vital';
	}else if (activeTabId == 'prescription') {
		$("#vitalTab,#symptomsTab,#treatmentTab,#testTab,#dietTab,#noteTab,#historyTab,#otTab,#prescriptionTab").removeClass('active');
		$("#otTab").addClass('active');
		activeTabId = 'ot';
	}
	if (activeTabId == 'ot') {
		editOtDetails();
	} else {
		getActivityDetails(activeTabId, selectedOpdIds);
	}
	$("#" + activeTabId).addClass('active show');
	next_prev_showhide(activeTabId);
}

/* ------------------- search bar for mygrid------------------------ */
function onQuickFilterChanged() {
	window.gridApi.setQuickFilter(document.getElementById('quickFilter').value);
	var firstRowNode = window.gridApi.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}
function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
}


function clearAllField() {
	$("#bodyTemp").val('').prop("disabled", false);;
	$("#heartRate").val('').prop("disabled", false);;
	$("#respRate").val('').prop("disabled", false);;
	$("#bloodPres").val('').prop("disabled", false);;
	$("#weight").val('').prop("disabled", false);;
	$("#height").val('').prop("disabled", false);;
	$("#bmi").val('').prop("disabled", false);;

	$("#vitalId").val('');
	$("#vitalIdSlNo").val('');
	$("#txSlNo").val('');
	$("#treatmentIds").val('');
	$("#medIds").val('');
	$("#medicinename").val('').trigger('change');
	$("#dosage").val('');
	$("#frequency").val('');
	$("#duration").val('');
	$("#instruction").val('');
	$("#treatmentTypes").val('');

	$("#tstSlNo").val('');
	$("#testIds").val('');
	$("#testItemId").val('');
	$("#testName").val('').trigger('change');
	$("#testCategory").val('');
	$("#remarks").val('');
	$('#veg').prop('checked', true);
	$("#dietIds").val('');
	$("#dietSlNo").val('');
	$("#dietType").val('');
	$("#specificFood").val('');
	$("#foodRestrictions").val('');
	$("#suppliments").val('');
	$("#reasonForDiet").val('');
	$("#supDuration").val('');
	$("#notesId").val('');
	$("#notesIdSlNo").val('');
	CKEDITOR.instances.notes.setData("");
}

function addVital() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	clearAllField()
}
function cancelVital() {
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	getActivityDetails(activeTabId, selectedOpdIds);
}
function addTreatment() {
	$("#saveAllTratmentData").hide();
	$(".br-m-btn-tx").hide();
	$(".br-s-btn-tx").show();
	clearAllField()
}
function cancelTreatment() {
	$(".br-m-btn-tx").show();
	$(".br-s-btn-tx").hide();
	$("#saveAllTratmentData").show();
}

function addTest() {
	$(".br-m-btn-tst").hide();
	$(".br-s-btn-tst").show();
	clearAllField();
	$("#testGridGrid1").hide();
	$("#saveAllTestData").hide();
	//$("#testGridGrid").show();
}
function cancelTest() {
	$(".br-m-btn-tst").show();
	$(".br-s-btn-tst").hide();
	$("#testGridGrid").show();
	$("#saveAllTestData").show();
}
function addDiet() {
	$(".br-m-btn-d").hide();
	$(".br-s-btn-d").show();
	clearAllField()
	$("#dietDetailContainer").hide();
	$("#dietDetailsContainer").hide();
}
function cancelDiet() {
	$(".br-m-btn-d").show();
	$(".br-s-btn-d").hide();
	$("#dietDetailContainer").hide();
	$("#dietDetailsContainer").show();

	getActivityDetails(activeTabId, selectedOpdIds)
}
function addNote() {
	$(".br-m-btn-nt").hide();
	$(".br-s-btn-nt").show();
	clearAllField()
}
function cancelNote() {
	$(".br-m-btn-nt").show();
	$(".br-s-btn-nt").hide();
	getActivityDetails(activeTabId, selectedOpdIds)
}
function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}


function setVitalDetails(vitalDetails) {
    let container = document.getElementById("opdDetailsContainer");
    container.innerHTML = "";

    if (vitalDetails.length === 0) {
        container.innerHTML = `<div class="no-treatment-message"><i class="fa fa-info-circle"></i> Vital Details Not Available</div>`;
        return;
    }

    // Sort by date descending
    vitalDetails.sort((a, b) => new Date(convertToISOFormat(b.dateTime)) - new Date(convertToISOFormat(a.dateTime)));

    // Get today's date (YYYY-MM-DD)
    let today = new Date().toISOString().split("T")[0];

    vitalDetails.forEach((vital, index) => {
        let isLast = index === vitalDetails.length - 1;

        // Convert `DD-MM-YYYY HH:MM:SS` to `YYYY-MM-DDTHH:MM:SS`
        let vitalISODate = convertToISOFormat(vital.dateTime);
        let vitalDate = new Date(vitalISODate).toISOString().split("T")[0]; // Extract only date part

        let formattedDateTime = formatDateTime(vital.dateTime);

        let card = document.createElement("div");
        card.className = "d-flex position-relative col-md-6";

        // Show edit button only if the vital's date is today
        let editButton = (vitalDate === today)
            ? `<a href="javascript:void(0);" class="edit-icon" onclick="editVitalDetails('${vital.vitalIdSlNo}','${vital.vitalId}')">
                <i class="fa fa-pen"></i>
              </a>`
            : '';

        card.innerHTML = `
            <div class="position-relative">
                <div class=""></div>
                ${!isLast ? '<div "></div>' : ''}
            </div>
            <div class="border rounded-2 p-3 mb-3 vital-card">
                <div class="d-flex justify-content-between">
                    <h6 class="fw-bold hide">Vital ID: ${vital.vitalId}</h6>
                    ${editButton}
                </div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Date Time:</span> ${vital.dateTime}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Blood Pressure:</span> ${vital.bloodPress}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Heart Rate:</span> ${vital.heartRate}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Body Temperature:</span> ${vital.bodyTemp}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Respiratory Rate:</span> ${vital.respiRate}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">BMI:</span> ${vital.bmi}</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Weight:</span> ${vital.weight} kg</div>
                <div class="mb-2 vt-dtl"><span class="fw-500">Height:</span> ${vital.height} cm</div>
            </div>
        `;

        container.appendChild(card);
    });
}

function convertToISOFormat(dateTime) {
    let parts = dateTime.split(" ");
    let dateParts = parts[0].split("-");
    let timePart = parts[1];

    return `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}T${timePart}`;
}


function convertToISOFormat(dateTime) {
    let parts = dateTime.split(" ");
    let dateParts = parts[0].split("-");
    let timePart = parts[1];

    return `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}T${timePart}`;
}


function formatDateTime(dateTime) {
	let [datePart, timePart] = dateTime.split(" ");
	let [day, month, year] = datePart.split("-");
	let [hour, minute] = timePart.split(":");

	let dateObj = new Date(`${year}-${month}-${day}T${hour}:${minute}`);

	let options = {
		year: "numeric",
		month: "short",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: true
	};

	return dateObj.toLocaleString("en-US", options);
}
function formatDateForSorting(dateTime) {
	let [datePart, timePart] = dateTime.split(" ");
	let [day, month, year] = datePart.split("-");
	return `${year}-${month}-${day} ${timePart}`;
}


function editVitalDetails(selectedvitalIdSlNo, selectedRowId) {

	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-vital?vitalId=" + selectedRowId + "&vitalIdSlNo=" + selectedvitalIdSlNo
	}).then(function(response) {
		if (response.code === "Success") {
			addVital();
			const responseBody = JSON.parse(response.body[0]);
			const vitalDetails = responseBody.vitalDetails[0];
			$("#vitalId").val(vitalDetails.vitalId);
			$("#vitalIdSlNo").val(vitalDetails.vitalIdSlNo);
			$("#bodyTemp").val(vitalDetails.bodyTemp).prop("disabled", false);
			$("#heartRate").val(vitalDetails.heartRate).prop("disabled", false);
			$("#respRate").val(vitalDetails.respiRate).prop("disabled", false);
			$("#bloodPres").val(vitalDetails.bloodPress).prop("disabled", false);
			$("#weight").val(vitalDetails.weight).prop("disabled", false);
			$("#height").val(vitalDetails.height).prop("disabled", false);
			$("#bmi").val(vitalDetails.bmi).prop("disabled", false);
		} else {
			console.log("Failed to fetch data");
		}
	});
}



/*function getTreatmentHistory() {

	$("#treatmentGrid1").show();

	agGrid.simpleHttpRequest({
		url: "opd-manage-view-treatment-his?types=" + activeTabId + "&patId=" + selectedPatientIds
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body);

		}
	});
}*/




let treatmentData = [];
function getTreatmentHistory() {
	var selectedRows = window.gridApi.getSelectedRows();
	
	var bookingId = selectedRows[0].opdId;
	
	$("#treatmentDetailContainer").empty();
	
	agGrid.simpleHttpRequest({
		url: "opd-manage-view-treatment-his?types=" + activeTabId + "&patId=" + selectedPatientIds + "&bookingId=" + bookingId
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			console.log("Parsed Response: ", responseBody);
			treatmentData = responseBody.treatmentHistory;
			renderGroupedTreatmentSummary();
		}
	});
}

function groupByOpdId(treatments) {
	if (!Array.isArray(treatments)) {
		console.error("Invalid treatment data received:", treatments);
		return [];
	}

	let groupedData = {};

	treatments.forEach(record => {
		if (!record) return;

		let opdId = record.opdId || "Unknown OPD ID";
		let doctorName = record.doctorName || "Unknown Doctor";
		let createdOn = record.createdOn ? new Date(record.createdOn) : new Date(0);
		let patientName = record.patientName || "Unknown Patient";

		if (!groupedData[opdId]) {
			groupedData[opdId] = {
				opdId,
				doctorName,
				createdOn,
				patientName,
				records: []
			};
		}
		groupedData[opdId].records.push(record);
	});

	return Object.values(groupedData).sort((a, b) => b.createdOn - a.createdOn);
}



function renderGroupedTreatmentSummary() {
	const container = document.getElementById("treatmentDetailsContainer");
	container.innerHTML = "";

	let groupedData = groupByOpdId(treatmentData);
	if (groupedData.length === 0) {
		container.innerHTML = `<div class="no-treatment-message"><i class="fa fa-info-circle"></i> No Treatment Records Available</div>`;
		return;
	}

	groupedData.forEach((group, index) => {
		let isLast = index === groupedData.length - 1;

		let row = document.createElement("div");
		row.className = "d-flex position-relative";

		row.innerHTML = `
            <div class="position-relative pe-3">
                <div class="timeline-circle"></div>
                ${!isLast ? '<div class="timeline-line"></div>' : ''}
            </div>
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm mb-flex">
                <div class="d-flex justify-content-between align-items-center top-margin fnt-13">
                    <h4 class="fw-bold fnt-13">Doctor: ${group.doctorName}</h4>
                    <span>${formatDateTime(group.createdOn)}</span> 
                </div>
                <p class="fnt-13">OPD ID: ${group.opdId} 
                    <a href="#" class="view-details ms-2" data-opd="${group.opdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p class="fnt-13">Patient: ${group.patientName}</p>
            </div>
        `;

		row.querySelector(".view-details").addEventListener("click", function(event) {
			event.preventDefault();
			const opdId = event.currentTarget.getAttribute("data-opd");
			renderTreatmentDetails(opdId);
		});

		container.appendChild(row);
	});
}


function renderTreatmentDetails(opdId) {
	$("#treatmentDetailContainer").empty();
	$("#treatmentGrid1").hide();
	$("#treatmentDetailContainer").show();

	const records = treatmentData.filter(item => item.opdId === opdId);
	const detailContainer = document.getElementById("treatmentDetailContainer");

	if (!detailContainer) {
		console.error("Treatment details container not found!");
		return;
	}

	let detailsHtml = `
	        <div class="border rounded-2 p-3 mt-3 treatment-detail-card line-height">
	            <h6 class="d-flex justify-content-between align-items-center">
	                Treatment Details for OPD ID: ${opdId}
	                <button class="btn-close-det border-0" onclick="closeTreatmentDetails()" title="Close">
	                    <i class="fa fa-times"></i>
	                </button>
	            </h6>
	
	            <p><strong>Doctor:</strong> ${records[0].doctorName}</p>
	            <p><strong>Patient:</strong> ${records[0].patientName}</p>
	            <p><strong>Created On:</strong> ${formatDateTime(records[0].createdOn)}</p>
	           
	            ${records.map((record, index) => `
	                <div class="border p-2 rounded mb-2">
	                    <h6 class="fw-bold d-flex align-items-center">
	                        <span class="index-circle">${index + 1}</span><strong> Medicine: </strong> ${record.medicineName}
	                    </h6>
	                    <p><strong>Dosage:</strong> ${record.dosage}</p>
	                    <p><strong>Duration:</strong> ${record.duration}</p>
	                    <p><strong>Frequency:</strong> ${record.frequency}</p>
	                    <p><strong>Instructions:</strong> ${record.instructions}</p>
	                </div>
	            `).join('')}
	        </div>
	    `;

	detailContainer.innerHTML = detailsHtml;
}


function formatDateTime(dateTime) {
	let dateObj = new Date(dateTime);
	let options = {
		year: "numeric",
		month: "short",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: true
	};
	return dateObj.toLocaleString("en-US", options);
}

function closeTreatmentDetails() {
	$("#treatmentGrid1").show();
	$("#treatmentDetailContainer").hide();
}


/*test history*/

let testData = [];

function getTestHistory() {

	$("#testDetailContainer").empty();
	agGrid.simpleHttpRequest({
		url: "opd-manage-view-test-his?types=" + activeTabId + "&patId=" + selectedPatientIds
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			console.log("Parsed Response: ", responseBody);
			testData = responseBody.testHistory;
			renderGroupedTestSummary();
		}
	});
}

function groupByOpdIdForTests(tests) {
	if (!Array.isArray(tests)) {
		console.error("Invalid test data received:", tests);
		return [];
	}

	let groupedData = {};

	tests.forEach(record => {
		if (!record) return;

		let opdId = record.opdId || "Unknown OPD ID";
		let doctorName = record.doctorName || "Unknown Doctor";
		let createdOn = record.createdOn ? new Date(record.createdOn) : new Date(0);
		let patientName = record.patientName || "Unknown Patient";

		if (!groupedData[opdId]) {
			groupedData[opdId] = {
				opdId,
				doctorName,
				createdOn,
				patientName,
				records: []
			};
		}
		groupedData[opdId].records.push(record);
	});

	return Object.values(groupedData).sort((a, b) => b.createdOn - a.createdOn);
}

function renderGroupedTestSummary() {
	const container = document.getElementById("testDetailsContainer");
	container.innerHTML = "";

	let groupedData = groupByOpdIdForTests(testData);
	if (groupedData.length === 0) {
		container.innerHTML = `<div class="no-treatment-message"><i class="fa fa-info-circle"></i> No Test Records Available</div>`;
		return;
	}

	groupedData.forEach((group, index) => {
		let isLast = index === groupedData.length - 1;

		let row = document.createElement("div");
		row.className = "d-flex position-relative";

		row.innerHTML = `
            <div class="position-relative pe-3">
                <div class="timeline-circle"></div>
                ${!isLast ? '<div class="timeline-line"></div>' : ''}
            </div>
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm">
                <div class="d-flex justify-content-between align-items-center top-margin fnt-13">
                    <h4 class="fw-bold fnt-13">Doctor: ${group.doctorName}</h4>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p class="fnt-13">OPD ID: ${group.opdId} 
                    <a href="#" class="view-test-details ms-2" data-opd="${group.opdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p class="fnt-13">Patient: ${group.patientName}</p>
            </div>
        `;

		// Add event listener to the eye icon
		row.querySelector(".view-test-details").addEventListener("click", function(event) {
			event.preventDefault();
			const opdId = event.currentTarget.getAttribute("data-opd");
			renderTestDetails(opdId);
		});

		container.appendChild(row);
	});
}


function renderTestDetails(opdId) {
	$("#testDetailContainer").empty();
	$("#testGridGrid1").hide();
	$("#testDetailContainer").show();
	

	const records = testData.filter(item => item.opdId === opdId);
	const detailContainer = document.getElementById("testDetailContainer");

	if (!detailContainer) {
		console.error("Test details container not found!");
		return;
	}

	let detailsHtml = `
	        <div class="border rounded-2 p-3 mt-3 test-detail-card line-height">
	            <h6 class="d-flex justify-content-between align-items-center">
	                Test Details for OPD ID: ${opdId}
	                <button class="btn-close-det border-0" onclick="closeTestDetails()" title="Close">
	                    <i class="fa fa-times"></i>
	                </button>
	            </h6>
	
	            <p><strong>Doctor:</strong> ${records[0].doctorName}</p>
	            <p><strong>Patient:</strong> ${records[0].patientName}</p>
	            <p><strong>Created On:</strong> ${formatDateTime(records[0].createdOn)}</p>
	           
	            ${records.map((record, index) => `
	                <div class="border p-2 rounded mb-2">
	                    <h6 class="fw-bold d-flex align-items-center">
	                        <span class="index-circle">${index + 1}</span><strong> Test : </strong> ${record.testName}
	                    </h6>
	                   
	                    <p><strong>Category:</strong> ${record.testCategory}</p>
	                </div>
	            `).join('')}
	        </div>
	    `;

	detailContainer.innerHTML = detailsHtml;
}

function closeTestDetails() {
	$("#testGridGrid1").show();
	$("#testDetailContainer").hide();
}

function formatDateTime(dateTime) {
	let dateObj = new Date(dateTime);
	let options = {
		year: "numeric",
		month: "short",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: true
	};
	return dateObj.toLocaleString("en-US", options);
}
/*diet history*/
let dietData = [];

function getDietHistory() {
	$("#dietDetailContainer").empty();
	agGrid.simpleHttpRequest({
		url: "opd-manage-view-diet-his?types=" + activeTabId + "&patId=" + selectedPatientIds
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			console.log("Parsed Response: ", responseBody);
			dietData = responseBody.dietHistory;
			renderGroupedDietSummary();
		}
	});
}

function groupByOpdIdForDiet(diets) {
	if (!Array.isArray(diets)) {
		console.error("Invalid diet data received:", diets);
		return [];
	}

	let groupedData = {};

	diets.forEach(record => {
		if (!record) return;

		let opdId = record.opdId || "Unknown OPD ID";
		let doctorName = record.doctorName || "Unknown Doctor";
		let createdOn = record.createdOn ? new Date(record.createdOn) : new Date(0);
		let patientName = record.patientName || "Unknown Patient";

		if (!groupedData[opdId]) {
			groupedData[opdId] = {
				opdId,
				doctorName,
				createdOn,
				patientName,
				records: []
			};
		}
		groupedData[opdId].records.push(record);
	});

	return Object.values(groupedData).sort((a, b) => b.createdOn - a.createdOn);
}


function renderGroupedDietSummary() {
	const container = document.getElementById("dietDetailsContainer");
	container.innerHTML = "";

	let groupedData = groupByOpdIdForDiet(dietData);
	if (groupedData.length === 0) {
		container.innerHTML = `<div class="no-treatment-message"><i class="fa fa-info-circle"></i> No Diet Records Available</div>`;
		return;
	}

	groupedData.forEach((group, index) => {
		let isLast = index === groupedData.length - 1;

		let row = document.createElement("div");
		row.className = "d-flex position-relative";

		row.innerHTML = `
            <div class="position-relative pe-3">
                <div class="timeline-circle"></div>
                ${!isLast ? '<div class="timeline-line"></div>' : ''}
            </div>
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm">
                <div class="d-flex justify-content-between align-items-center top-margin fnt-13">
                    <h4 class="fw-bold fnt-13">Doctor: ${group.doctorName}</h4>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p class="fnt-13">OPD ID: ${group.opdId} 
                    <a href="#" class="view-diet-details ms-2" data-opd="${group.opdId}" title="View Details">
                        <i class="fa fa-eye eye-icon"></i>
                    </a>
                </p>
                <p class="fnt-13">Patient: ${group.patientName}</p>
            </div>
        `;

		row.querySelector(".view-diet-details").addEventListener("click", function(event) {
			event.preventDefault();
			const opdId = event.currentTarget.getAttribute("data-opd");
			renderDietDetails(opdId);
		});

		container.appendChild(row);
	});
}


function renderDietDetails(opdId) {
	$("#dietDetailContainer").empty();
	$("#dietGrid1").hide();
	$("#dietDetailsContainer").hide();
	$("#dietDetailContainer").show();

	const records = dietData.filter(item => item.opdId === opdId);
	const detailContainer = document.getElementById("dietDetailContainer");

	if (!detailContainer) {
		console.error("Diet details container not found!");
		return;
	}

	let detailsHtml = `
        <div class="border rounded-2 p-3 mt-3 diet-detail-card line-height">
            <h6 class="d-flex justify-content-between align-items-center">
                Diet Details for OPD ID: ${opdId}
                <button class="btn-close-det border-0" onclick="closeDietDetails()" title="Close">
                    <i class="fa fa-times"></i>
                </button>
            </h6>

            <p><strong>Doctor:</strong> ${records[0].doctorName}</p>
            <p><strong>Patient:</strong> ${records[0].patientName}</p>
            <p><strong>Created On:</strong> ${formatDateTime(records[0].createdOn)}</p>
           
            ${records.map((record, index) => `
                <div class="border p-2 rounded mb-2">
                    <h6 class="fw-bold d-flex align-items-center">
                        <span class="index-circle">${index + 1}</span><strong> Diet Type: </strong> ${(record.dietType)}
                    </h6>
                    <p><strong>Reason:</strong> ${record.reason}</p>
                    <p><strong>Duration:</strong> ${record.duration}</p>
                    <p><strong>Supplement:</strong> ${record.suppliment}</p>
                    <p><strong>Restriction:</strong> ${record.restriction}</p>
                    <p><strong>Specific Food:</strong> ${record.specificFood}</p>
                </div>
            `).join('')}
        </div>
    `;

	detailContainer.innerHTML = detailsHtml;
}

function closeDietDetails() {
	$("#dietGridGrid").show();
	$("#dietDetailContainer").hide();
	$("#dietDetailsContainer").show();
}

function formatDateTime(dateTime) {
	let dateObj = new Date(dateTime);
	let options = {
		year: "numeric",
		month: "short",
		day: "2-digit",
		hour: "2-digit",
		minute: "2-digit",
		hour12: true
	};
	return dateObj.toLocaleString("en-US", options);
}


var columnDefSymptoms = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Symptoms Id",
		field: "symptomsId",
	},
	{
		headerName: "Remarks",
		field: "remarks",

	/*	cellRenderer: (params) => {
			if (!params.value) return ''; // Handle empty values

			// Decode Base64 if applicable
			const decodedValue = params.data.symptoms && params.data.symptoms !== 'null'
				? window.atob(params.data.symptoms)
				: '';

			return decodedValue;
		}
*/
	}];

// Define grid options
/*var gridOptionssymptoms = {
	columnDefs: columnDefSymptoms,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onGridReady: function(params) {
		window.gridApiSymptoms = params.api;
	},
	onSelectionChanged: rowSelectSymptoms
};
function rowSelectSymptoms() {
	var selectedRows = window.gridApiSymptoms.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-sm").attr("disabled", false);
	} else {
		$(".br-dis-sm").attr("disabled", true);
	}
}*/

function addSymptoms() {
	$(".br-m-btn-nt").hide();
	$(".br-s-btn-nt").show();
	
	$("#symptomContainer").hide();
	clearAllField()
}
function cancelSymptoms() {
	$(".br-m-btn-nt").show();
	$(".br-s-btn-nt").hide();
	getActivityDetails(activeTabId, selectedOpdIds);
	$("#symptomContainer").show();
}



function saveSymptoms() {
	var sympData = {};
	sympData.selectedPatientIds = selectedPatientIds;
	sympData.selectedOpdIds = selectedOpdIds;
	sympData['symptomsId'] = $("#symptomsId").val();
	sympData['remarks'] = $("#remarks").val();
		if (sympData.remarks == null || sympData.remarks.trim() === "") {
			toastr.error("Symptoms Details Required");
			return;
		} else {
		saveSympDetails(sympData);

	}
}

function saveSympDetails(sympData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-opd-symp-details",
		contentType: "application/json",
		data: JSON.stringify(sympData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
				cancelSymptoms();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis-nt").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}


function editSymptom(id) {
	/*const selectedRows = gridOptionssymptoms.api.getSelectedRows();
	const selectedRowId = selectedRows[0].symptomsId; // Assuming 'id' is the key for the row ID*/
	agGrid.simpleHttpRequest({
		url: "opd-manage-edit-sympt?symptomsId=" + id
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			const sympDetails = responseBody.sympDetails[0];
			addSymptoms();
			$("#symptomsId").val(sympDetails.symptomsId);
			$("#remarks").val(sympDetails.remarks);
		
		} else {
			console.log("Failed to fetch data");
		}
	});
}


function setSymptomDetails(sympDetails) {
    let container = document.getElementById("symptomContainer");
    container.innerHTML = ""; // Clear previous content

    if (sympDetails.length === 0) {
        container.innerHTML = `<div class="no-symtoms-message"><i class="fa fa-info-circle"></i> No Symptoms Available</div>`;
        return;
    }

    let timelineHtml = `<div class="vital-card">`;

    sympDetails.forEach((symptom) => {
        timelineHtml += `
            <div class="timeline-item">
                <div class="timeline-circle-sym"></div>
                <div class="timeline-content border rounded-2 p-3 mb-3">
                    <div class="d-flex justify-content-between">
                        <h6 class="fw-bold">Patient ID: ${symptom.patientId}</h6>
                        <a href="javascript:void(0);" class="edit-icon" onclick="editSymptom('${symptom.symptomsId}')">
                            <i class="fa fa-pen"></i>
                        </a>
                    </div>
                    <p><strong>Symptoms:</strong> ${symptom.remarks}</p>
                </div>
            </div>
        `;
    });

    timelineHtml += `</div>`;
    container.innerHTML = timelineHtml;
}


var columnDefOt = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Ot Id",
		field: "otId",
	},
	{
		headerName: "Procedure Name",
		field: "procedureName",
	}];

// Define grid options
var gridOptionsOt = {
	columnDefs: columnDefOt,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onGridReady: function(params) {
		window.gridOptionsOt = params.api;
	},
	onSelectionChanged: rowSelectOt
};
function rowSelectOt() {
	var selectedRows = window.gridOptionsOt.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-sm").attr("disabled", false);
	} else {
		$(".br-dis-sm").attr("disabled", true);
	}
}


function setOtDetails(otDetails) {
    let container = document.getElementById("otContainer");
    container.innerHTML = ""; // Clear previous content

    if (otDetails.length === 0) {
        container.innerHTML = `<div class="no-ot-message"><i class="fa fa-info-circle"></i> No Ot Available</div>`;
        return;
    }

    let timelineHtml = `<div class="vital-card">`;

    otDetails.forEach((ot) => {
        timelineHtml += `
            <div class="timeline-item">
                <div class="timeline-circle-sym"></div>
                <div class="timeline-content border rounded-2 p-3 mb-3">
                    <div class="d-flex justify-content-between">
                        <h6 class="fw-bold"></h6>
                        <a href="javascript:void(0);" class="edit-icon" onclick="editOt('${ot.otId}')">
                            <i class="fa fa-pen"></i>
                        </a>
                    </div>
                    <p><strong>Ot:</strong> ${ot.otId}</p>
                    <p><strong>Procedure Name:</strong> ${ot.procedureName}</p>
                </div>
            </div>
        `;
    });

    timelineHtml += `</div>`;
    container.innerHTML = timelineHtml;
}

function addOt(){
	$("#otdemo").show();
	$("#saveOt").show();
	$("#cancelOt").show();
	$("#addOt").hide();
	$("#otContainer").hide();
	}
	
function cancelOt(){
		$("#otContainer").show();
		$("#saveOt").hide();
		$("#addOt").show();
		$("#otdemo").hide();
		$("#cancelOt").hide();
	}


