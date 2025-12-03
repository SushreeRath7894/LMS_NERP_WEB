
$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	/*var vitalGridDiv = document.querySelector('#vitalGrid');
	new agGrid.Grid(vitalGridDiv, gridOptionsVital);*/

	var gridDiv = document.querySelector('#treatmentGrid');
	new agGrid.Grid(gridDiv, gridOptionsTreatment);

	var gridDiv = document.querySelector('#testGrid');
	new agGrid.Grid(gridDiv, gridOptionsTest);
	
	/*var gridDiv = document.querySelector('#symptomsGrid');
	new agGrid.Grid(gridDiv, gridOptionssymptoms);*/

   //gridOptionssymptoms.api.setRowData();
	//gridOptionsVital.api.setRowData();
	gridOptionsTreatment.api.setRowData();
	gridOptionsTest.api.setRowData();

	$(".br-s-btn").hide();
	$(".br-s-btn-tx").hide();
	$(".br-s-btn-tst").hide();
	$(".br-s-btn-d").hide();
	$("#otdemo").hide();

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

	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			onQuickFilterChanged()
		}
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

	agGrid.simpleHttpRequest({
		url: 'manage-ipd-view'
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.patientDetails;
		console.log(allData)
		gridOptions.api.setRowData(allData);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	});
	
	
		$('#save').hide();
		$('#cancel').hide();
		$('#symptomsIdDiv').hide();
			$("#saveOt").hide();
			$("#cancelOt").hide();

		

});

/* ------------------- search bar for mygrid------------------------ */
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}

function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
}

var columnDefs = [
	{
		headerCheckboxSelection: false,
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
		flex: 1,
	},
	{
		headerName: "Patient ID",
		field: "patientId",
		flex: 1,
	},
	{
		headerName: "Patient Type",
		field: "patientType",
		flex: 1,
		hide: true
	}, {
		headerName: "Patient name",
		field: "pName",
		flex: 1,
	}, {
		headerName: "Address",
		field: "address",
		flex: 1,
	}, {
		headerName: "Mobile",
		field: "mobNo",
		flex: 0.7,
	}, {
		headerName: "Age",
		field: "age",
		flex: 0.5,
	},
	{
		headerName: "Gender",
		field: "gender",
		hide: true
	}
	, {
		headerName: "Status",
		field: "patientStatus",
		flex: 0.6,
	}];

var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		minWidth: 80,
		sortable: true,
		filter: true,
		resizable: true,
	},
	onSelectionChanged: rowSelect,
	paginationAutoPageSize: true,
	pagination: true,
};

let patientId;
let ipdId;
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();

	if (selectedRows.length > 0) {
		$("#section21").show();
		var datas = selectedRows[0];
		patientId = (datas.patientId);
		ipdId = (datas.bookingId);
		viewAllDetailsByIPDID();
		var patientName = datas.pName;
		var gender = datas.gender;
		var age = datas.age;

		$(".patientName").text(patientName);
		$(".gender").text(gender);
		$(".age").text(age);
	} else {
		$("#section21").hide();
		gridOptionsVital.api.setRowData();
		gridOptionsTreatment.api.setRowData();
		gridOptionsTest.api.setRowData();
		$(".patientName").text('');
		$(".gender").text('');
		$(".age").text('');
	}

}
function viewAllDetailsByIPDID() {
	agGrid.simpleHttpRequest({
		url: 'his-ipd-viewAllDetailsByIPDID?patientId=' + patientId + "&ipdId=" + ipdId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		console.log("jsonData==", jsonData)
		/*const vitalDetails = jsonData.vitalDetails || [];
		gridOptionsVital.api.setRowData(vitalDetails);*/
		
			const vitalDetails = jsonData.vitalDetails || [];
			
			if (vitalDetails.length > 0) {
				$("#addVital").hide();
			} else{
				$("#addVital").show();
			}
			setVitalDetails(vitalDetails);

     
			
			const sympDetails = jsonData.sympDetails || [];
			setSymptomDetails(sympDetails);
			


	const treatmentDetails = jsonData.treatmentDetails || [];
			if (treatmentDetails.length > 0) {
				$(".tx").hide();

				getTreatmentHistory();
				$("#treatmentGrid1").show();
				$("#treatmentGrid").hide();
			} else {
				$(".tx").show();
				checkboxesEnabled1 = true;
				gridOptionsTreatment.api.setColumnDefs(columnDefs1); 
				gridOptionsTreatment.api.redrawRows();
			}
			getTreatmentHistory();
			$("#treatmentGrid1").show();
			$("#treatmentGrid").hide();

		const testDetails = jsonData.testDetails || [];
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
			$("#testGridGrid").hide();
			$("#testGridGrid1").show();
			getTestHistory();

		
		const otDetails = jsonData.otDetails || [];
			setOtDetails(otDetails);

		const dietDetails = jsonData.dietDetails || [];
		console.log("dietDetails===", dietDetails);

		let savedDataContainer = document.getElementById('dietViewContainer');
		savedDataContainer.innerHTML = "";

		// Grouping data by dietId
		let groupedData = {};
		dietDetails.forEach(entry => {
			let dietId = entry.dietId;
			if (!groupedData[dietId]) {
				groupedData[dietId] = {
					fromDate: entry.fromDate,
					categories: []
				};
			}
			groupedData[dietId].categories.push(...entry.categories);
		});

		// Sort diet IDs in descending order
		let sortedDietIds = Object.keys(groupedData).sort((a, b) => b.localeCompare(a));

		// Rendering each dietId
		sortedDietIds.forEach(dietId => {
			let dietData = groupedData[dietId];

			let dietSection = document.createElement('div');
			dietSection.className = "diet-view-section";

			dietSection.innerHTML = `
                <div class="diet-header">
                    <span class="fw-bold">Diet ID: ${dietId}</span>
                    <p class="date-text">📅 From: ${dietData.fromDate}</p>
                </div>
                <div class="diet-entries"></div>
            `;
			let entriesContainer = dietSection.querySelector('.diet-entries');
			dietData.categories.forEach(entry => {
				let menuItems = entry.menu.split(", ").map(item => `<span class="menu-chip">${item}</span>`).join(" ");
				let noteContent = entry.note ? `<span class="note-badge">📝 ${entry.note}</span>` : "";

				let entryHTML = `
                    <div class="diet-entry">
                        <div class="category-label"> ${entry.category}</div>
                        <div class="menu-container">${menuItems} ${noteContent}</div>
                    </div>
                `;
				entriesContainer.innerHTML += entryHTML;
			});

			savedDataContainer.appendChild(dietSection);
		});
	});
}
var columnDefVital = [
	{
		//headerCheckboxSelection: true,
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
var gridOptionsVital = {
	columnDefs: columnDefVital,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: rowSelectVital
};


function rowSelectVital() {
	var selectedData = gridOptionsVital.api.getSelectedRows();
	if (selectedData && selectedData.length > 0) {
		$(".br-dis").attr("disabled", false);
	} else {
		$(".br-dis").attr("disabled", true);
	}
}
var columnDefs1 = [
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
		headerName: "Treatment Id",
		field: "treatmentId",
		hide: true,

	},
	{
		headerName: "Medicine Name",
		field: "medName",

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
		flex: 1
	},
	onSelectionChanged: rowSelect1
};


function rowSelect1() {
	var selectedRows = gridOptionsTreatment.api.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-tx").attr("disabled", false);
	} else {
		$(".br-dis-tx").attr("disabled", true);
	}
}

var columnDefs2 = [
	{
		//headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
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
	onSelectionChanged: rowSelect2
};


function rowSelect2() {
	var selectedRows = gridOptionsTest.api.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-tst").attr("disabled", false);
	} else {
		$(".br-dis-tst").attr("disabled", true);
	}
}


function addVital() {
	$(".br-m-btn").hide();
	$("#opdDetailsContainer").hide();
	$(".br-s-btn").show();
	$("#vitalId").val('');
	$("#patientId").val('');
	$("#ipdId").val('');
	$("#bodyTemp").val('');
	$("#heartRate").val('');
	$("#respRate").val('');
	$("#bloodPres").val('');
	$("#weight").val('');
	$("#height").val('');
	$("#bmi").val('');
	$("#viSlNo").val('');
}
function cancelVital() {
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	$("#opdDetailsContainer").show();
}
//save vital
function mastersaveVital() {
	var data = {};
	var valid = true;
	data.patientId = patientId;
	data.ipdId = ipdId;
	data.vitalId = $("#vitalId").val();
	data.bodyTemp = $("#bodyTemp").val();
	data.heartRate = $("#heartRate").val();
	data.respRate = $("#respRate").val();
	data.bloodPres = $("#bloodPres").val();
	data.weight = $("#weight").val();
	data.height = $("#height").val();
	data.bmi = $("#bmi").val();
	data.slno = $("#viSlNo").val();
	if (patientId == "" || patientId == null) {
		toastr.error('Please select a patient to provide IPD details.');
		return;
	}
	if (data.bodyTemp == null || data.bodyTemp.trim() === "") {
		toastr.error('Body Temprature Required');
		return;
	}
	if (valid) {
		saveVital(data);
	}
}
function saveVital(data) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "his-ipd-save-vital-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				viewAllDetailsByIPDID();
				cancelVital();
			} else {
				toastr.error(response.message);
			}
			$('.loader').hide();
			$(".br-dis").attr("disabled", true);
		},
		error: function(response) {
			console.log(response);
		}
	})
}
// edit vital 
function editVitalDetails() {
	const vitalDetails = gridOptionsVital.api.getSelectedRows();
	if (vitalDetails[0].viSlNo) {
		addVital();
		$("#vitalId").val(vitalDetails[0].vitalId);
		$("#patientId").val(vitalDetails[0].patientId);
		$("#ipdId").val(vitalDetails[0].ipdId);
		$("#bodyTemp").val(vitalDetails[0].bodyTemp);
		$("#heartRate").val(vitalDetails[0].heartRate);
		$("#respRate").val(vitalDetails[0].respiRate);
		$("#bloodPres").val(vitalDetails[0].bloodPress);
		$("#weight").val(vitalDetails[0].weight);
		$("#height").val(vitalDetails[0].height);
		$("#bmi").val(vitalDetails[0].bmi);
		$("#viSlNo").val(vitalDetails[0].viSlNo);
	} else {
		cancelVital();
	}
}
function addTreatment() {
	$(".br-m-btn-tx").hide();
	$(".br-s-btn-tx").show();
	$("#treatmentIds").val('');
	$("#medicinename").val('').trigger('change');
	$("#dosage").val('');
	$("#frequency").val('');
	$("#duration").val('');
	$("#instruction").val('');
	$("#treatmentTypes").val('');
}
function cancelTreatment() {
	$(".br-m-btn-tx").show();
	$(".br-s-btn-tx").hide();
}

/*save treatment*/
function saveTratment() {
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

	if (patientId == "" || patientId == null) {
		toastr.error('Please select a patient to provide IPD details.');
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
				patientId: patientId,
				ipdId: ipdId,
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
			patientId: patientId,
			ipdId: ipdId
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
			url: "his-ipd-save-treatment-details",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success(response.message);
					viewAllDetailsByIPDID();
					cancelTreatment();
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

// edit treatment
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

function addTest() {
	$(".br-m-btn-tst").hide();
	$(".br-s-btn-tst").show();
	$("#tstSlNo").val('');
	$("#testIds").val('');
	$("#testItemId").val('');
	$("#testName").val('').trigger('change');
	$("#testCategory").val('');
	$("#remarks").val('');
	$("#testDetailsContainer").hide();
	clearAllField();
	$("#testGridGrid1").hide();
	$("#saveAllTestData").hide();
	
	
}
function cancelTest() {
	$(".br-m-btn-tst").show();
	$(".br-s-btn-tst").hide();
	
	$("#testGridGrid").show();
	$("#saveAllTestData").show();
	$('#testGrid').show();
}
/*add test*/
function saveTest() {
	var validTestData = true;
	var testId = $("#testId").val();
	var testItemId = $("#testName").val();
	var testName = $("#testName option:selected").text();
	var testCategory = $("#testCategory").val();
	let id = $("#tstSlNo").val();

	if (patientId == "" || patientId == null) {
		toastr.error('Please select a patient to provide IPD details.');
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
				patientId: patientId,
				ipdId: ipdId,
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
		$('#testGrid').show();
	}
}

function saveAllTestData() {
	var totalRowCount1 = gridOptionsTest.api.getModel().getRowCount();
	if (totalRowCount1 > 0) {
		var testData = {
			patientId: patientId,
			ipdId: ipdId
		};

		var datas = [];
		gridOptionsTest.api.forEachNode(function(rowNode, index) {
			var item1 = rowNode.data;
			datas.push(item1);
		});
	} else {
		toastr.error('At Least One Test Detail Is Required');
	}
	if (datas) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "his-ipd-save-test-details",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success(response.message);
					viewAllDetailsByIPDID();
					cancelTest();
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


function newOt() {

	if (gridOptions.api) {
		gridOptions.api.deselectAll();
	}

	$("#otId").val('');
	$("#ipdIdOt").val('');
	$("#patientIdOt").val('');
	$("#summary").val('');
	$("#fromDate").val('');
	$("#toDate").val('');

}

function saveOt() {
	var validOtData = true;
	var otData = {};
	otData.patientId = patientId;
	otData.ipdId = ipdId;
	otData['procedureName'] = $("#procedureName").val();
	otData['otId'] = $("#otId").val();
	otData['summary'] = $("#summary").val();
	otData['fromDate'] = $("#fromDate").val();
	otData['toDate'] = $("#toDate").val();

	console.log("Data is for test coming like this=======> ", otData);
	if (otData.patientId == "" || otData.patientId == null) {
		toastr.error('Please select a patient to provide IPD details.');
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
		url: "his-ipd-save-ot-details",
		contentType: "application/json",
		data: JSON.stringify(otData),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
				viewAllDetailsByIPDID();
				
			} else {
				$('.loader').hide();
			}
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}

function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	console.log(tabElement)
	const tab = new bootstrap.Tab(tabElement);
	console.log(tab)
	tab.show();
}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

/*********************** *Diet Section ***********************/

///diet///
function addDiet() {
	$(".br-m-btn-d").hide();
	$(".br-s-btn-d").show();
	//$("#dietAddScreen").empty();
}
function cancelDiet() {
	$(".br-m-btn-d").show();
	$(".br-s-btn-d").hide();
	//$("#dietAddScreen").empty();
}

document.addEventListener("DOMContentLoaded", function() {
	getDietMenu();

	// Function to show/hide fields when checkbox is toggled
	window.toggleFields = function(key) {
		let elements = document.querySelectorAll('.' + key + 'Fields');
		let checkbox = document.getElementById(key + 'Check');

		elements.forEach(element => {
			if (checkbox.checked) {
				element.classList.remove('hidden'); // Show fields
			} else {
				element.classList.add('hidden'); // Hide fields
			}
		});

		// Apply custom checkbox color
		checkbox.style.accentColor = checkbox.checked ? "rgb(191, 5, 255)" : "";
	};
});

// Fetch diet menu from the backend
function getDietMenu() {
	$.ajax({
		type: "GET",
		url: "his-ipd-getDietMenu",
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				console.log("jsonData", jsonData);

				const categories = jsonData.categoryList;
				const menuList = jsonData.menuList;
				const dietAddScreen = document.getElementById('dietAddScreen');

				if (!dietAddScreen) {
					console.error("Element #dietAddScreen not found!");
					return;
				}

				dietAddScreen.innerHTML = "";
				if (categories) {
					categories.forEach(category => {
						let row = document.createElement('div');
						row.classList.add('row', 'mb-3');

						row.innerHTML = `
                            <div class="col-md-1 col-1 d-flex align-items-center check-align">
                                <input type="checkbox" id="${category.key}Check" onchange="toggleFields('${category.key}')">
                            </div>
                            <div class="col-md-3 col-3 d-flex align-items-center bdr-rt">
                                <label class="form-label">${category.name}</label>
                            </div>
							<div class="col-md-4 col-4 bdr-rt ${category.key}Fields hidden">
                            <div class="autocomplete">
                                <select id="menu_${category.key}" multiple></select>
                                <input type="hidden" id="hiddenMenuId_${category.key}">
                            </div>
							</div>
                            <div class="col-md-4 col-4 ${category.key}Fields hidden">
                                <input type="text" class="form-control" placeholder="Instruction...">
                            </div>
                        `;

						dietAddScreen.appendChild(row);

						// Filter menu items that belong to this category
						let options = '';
						menuList.forEach(item => {
							if (item.category_id === category.key) {
								options += `<option value="${item.item_id}">${item.item_name}</option>`;
							}
						});

						document.getElementById(`menu_${category.key}`).innerHTML = options;

						// Initialize SlimSelect for multi-select dropdown
						new SlimSelect({
							select: `#menu_${category.key}`,
							multiple: true,
							autocomplete: true,
							onChange: selectedOptions => {
								let selectedValues = selectedOptions.map(option => option.value);
								document.getElementById(`hiddenMenuId_${category.key}`).value = selectedValues.join(",");
							}
						});
					});
				}
			}
		},
		error: function(e) {
			console.error("Error fetching diet menu", e);
		}
	});
}

// Save Diet Function with Validation
function saveDiet() {
	let isValid = true;
	let selectedData = [];
	let categories = document.querySelectorAll('#dietAddScreen .row');
	let did = $("#dietId").val();

	// Iterate over categories
	categories.forEach(row => {
		let checkbox = row.querySelector('input[type="checkbox"]');
		if (!checkbox) return; // Skip rows without checkboxes

		let categoryKey = checkbox.id ? checkbox.id.replace('Check', '') : null;
		let categoryName = row.querySelector('label')?.innerText || 'Unknown Category';
		let menuSelect = row.querySelector('select');
		let instructionInput = row.querySelector('input[type="text"]');

		// Process checked categories
		if (checkbox.checked) {
			if (!menuSelect) {
				toastr.error(`Menu selection is required for ${categoryName}`);
				isValid = false;
				return;
			}

			let selectedMenus = [...menuSelect.selectedOptions].map(option => option.value);
			let instruction = instructionInput ? instructionInput.value.trim() : "";

			if (selectedMenus.length === 0) {
				toastr.error(`At least one menu item is required for ${categoryName}`);
				isValid = false;
			} else {
				selectedData.push({
					patientId: patientId,
					ipdId: ipdId,
					category: categoryKey || categoryName, // Ensure categoryKey is valid
					menu: selectedMenus,
					instruction: instruction,
					dietId: did
				});
			}
		}
	});

	// Stop execution if no valid data
	if (!isValid || selectedData.length === 0) {
		toastr.error("Please check at least one category.");
		return;
	}

	// Debugging: Check the collected data before sending
	console.log("Collected Data for POST:", JSON.stringify(selectedData));

	// Proceed with AJAX POST request
	$.ajax({
		type: "POST",
		url: "his-ipd-save-diet-details", // Replace with your actual API endpoint
		contentType: "application/json",
		data: JSON.stringify(selectedData),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success("Diet menu saved successfully!");
				viewAllDetailsByIPDID();
			} else {
				toastr.error("Failed to save diet menu. Please try again.");
			}
		},
		error: function(error) {
			toastr.error("Failed to save diet menu. Please try again.");
		}
	});
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
		headerName: "Symptoms",
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
var gridOptionssymptoms = {
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
}

function addSymptoms() {
	$(".br-m-btn").hide();
	$("#add").hide();
	$(".br-s-btn-nt").show();
	$("#symptomsId").val('');
	$("#patientId").val('');
	$("#ipdId").val('');
}
function cancelSymptoms() {
	$(".br-m-btn").show();
	$(".br-s-btn-nt").hide();
	$("#add").show();
	viewAllDetailsByIPDID();
}


function saveSymptoms() {
	var sympData = {};
	sympData.patientId = patientId;
	sympData.ipdId = ipdId;
	sympData['symptomsId'] = $("#symptomsId").val();
	sympData['remarks'] = $("#remarks").val();
 {
		saveSympDetails(sympData);

	}
}

function saveSympDetails(sympData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-ipd-symp-details",
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
	const selectedRows = gridOptionssymptoms.api.getSelectedRows();
	const selectedRowId = selectedRows[0].symptomsId;
	agGrid.simpleHttpRequest({
		url: "ipd-manage-edit-sympt?symptomsId=" + id
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body[0]);
			const sympDetails = responseBody.sympDetails;
			addSymptoms();
			$("#symptomsId").val(sympDetails.symptomsId);
			$("#remarks").val(sympDetails.remarks);
		
		} else {
			console.log("Failed to fetch data");
		}
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
            <div class="position-relative pe-3">
                <div class=""></div>
                ${!isLast ? '<div "></div>' : ''}
            </div>
            <div class="border rounded-2 p-3 mb-3 vital-card">
                <div class="d-flex justify-content-between">
                    <h6 class="fw-bold hide">Vital ID: ${vital.vitalId}</h6>
                    ${editButton}
                </div>
                <div class="mb-2"><span class="fw-bold">Date Time:</span> ${vital.dateTime}</div>
                <div class="mb-2"><span class="fw-bold">Blood Pressure:</span> ${vital.bloodPress}</div>
                <div class="mb-2"><span class="fw-bold">Heart Rate:</span> ${vital.heartRate}</div>
                <div class="mb-2"><span class="fw-bold">Body Temperature:</span> ${vital.bodyTemp}</div>
                <div class="mb-2"><span class="fw-bold">Respiratory Rate:</span> ${vital.respiRate}</div>
                <div class="mb-2"><span class="fw-bold">BMI:</span> ${vital.bmi}</div>
                <div class="mb-2"><span class="fw-bold">Weight:</span> ${vital.weight} kg</div>
                <div class="mb-2"><span class="fw-bold">Height:</span> ${vital.height} cm</div>              
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

function setSymptomDetails(sympDetails) {
    let container = document.getElementById("symptomContainer");
    container.innerHTML = ""; // Clear previous content

    if (sympDetails.length === 0) {
        container.innerHTML = `<div class="no-treatment-message"><i class="fa fa-info-circle"></i> No Symptoms Available</div>`;
        return;
    }

    let timelineHtml = `<div class="vital-card">`;

    sympDetails.forEach((symptom) => {
        timelineHtml += `
            <div class="timeline-item">
                <div class="timeline-circle-sym"></div>
                <div class="timeline-content border rounded-2 p-3 mb-3">
                    <div class="d-flex justify-content-between">
                        <h6 class="fw-bold"></h6>
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


let treatmentData = [];
function getTreatmentHistory() {
	$("#treatmentDetailContainer").empty();
	agGrid.simpleHttpRequest({
		url: 'his-ipd-manage-view-treatment?patientId=' + patientId + "&ipdId=" + ipdId
	}).then(function(response) {
		if (response.code === "Success") {
			const jsonData = JSON.parse(response.body[0]);
			console.log("Parsed Response: ", jsonData);
			treatmentData = jsonData.treatmentHistory;
			renderGroupedTreatmentSummary();
		}
	});
}

function renderGroupedTreatmentSummary() {
	$('#testGrid').hide();
	const container = document.getElementById("treatmentDetailsContainer");
	container.innerHTML = "";

	let groupedData = groupByIpdId(treatmentData);
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
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm w-100">
                <div class="d-flex justify-content-between align-items-center top-margin">
                    <h6 class="fw-bold">Doctor: ${group.doctorName}</h6>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p>IPD ID: ${group.ipdId} 
                    <a href="#" class="view-details ms-2" data-ipd="${group.ipdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p>Patient: ${group.patientName}</p>
            </div>
        `;

		row.querySelector(".view-details").addEventListener("click", function(event) {
			event.preventDefault();
			const ipdId = event.currentTarget.getAttribute("data-ipd");
			renderTreatmentDetails(ipdId);
		});

		container.appendChild(row);
	});
}


function renderTreatmentDetails(ipdId) {
	$("#treatmentDetailContainer").empty();
	$("#treatmentGrid1").hide();
	$("#treatmentDetailContainer").show();

	const records = treatmentData.filter(item => item.ipdId === ipdId);
	const detailContainer = document.getElementById("treatmentDetailContainer");

	if (!detailContainer) {
		console.error("Treatment details container not found!");
		return;
	}

	let detailsHtml = `
	        <div class="border rounded-2 p-3 mt-3 treatment-detail-card line-height">
	            <h6 class="d-flex justify-content-between align-items-center">
	                Treatment Details for IPD ID: ${ipdId}
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
function closeTreatmentDetails() {
	$("#treatmentGrid1").show();
	$("#treatmentDetailContainer").hide();
}

function groupByIpdId(treatments) {
	if (!Array.isArray(treatments)) {
		console.error("Invalid treatment data received:", treatments);
		return [];
	}

	let groupedData = {};

	treatments.forEach(record => {
		if (!record) return;

		let ipdId = record.ipdId || "Unknown IPD ID";
		let doctorName = record.doctorName || "Unknown Doctor";
		let createdOn = record.createdOn ? new Date(record.createdOn) : new Date(0);
		let patientName = record.patientName || "Unknown Patient";

		if (!groupedData[ipdId]) {
			groupedData[ipdId] = {
				ipdId,
				doctorName,
				createdOn,
				patientName,
				records: []
			};
		}
		groupedData[ipdId].records.push(record);
	});

	return Object.values(groupedData).sort((a, b) => b.createdOn - a.createdOn);
}

/*test history*/

let testData = [];

function getTestHistory() {

	$("#testDetailContainer").empty();
	agGrid.simpleHttpRequest({
		url: 'his-ipd-manage-view-test-his?patientId=' + patientId + "&ipdId=" + ipdId
	}).then(function(response) {
		if (response.code === "Success") {
			const jsonData = JSON.parse(response.body[0]);
			console.log("Parsed Response: ", jsonData);
			testData = jsonData.testHistory;
			renderGroupedTestSummary();
		}
	});
}

function groupByOpdIdForTests(tests) {
	
	console.log()
	if (!Array.isArray(tests)) {
		console.error("Invalid test data received:", tests);
		return [];
	}

	let groupedData = {};

	tests.forEach(record => {
		if (!record) return;

		let ipdId = record.ipdId || "Unknown iPD ID";
		let doctorName = record.doctorName || "Unknown Doctor";
		let createdOn = record.createdOn ? new Date(record.createdOn) : new Date(0);
		let patientName = record.patientName || "Unknown Patient";

		if (!groupedData[ipdId]) {
			groupedData[ipdId] = {
				ipdId,
				doctorName,
				createdOn,
				patientName,
				records: []
			};
		}
		groupedData[ipdId].records.push(record);
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
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm w-100">
                <div class="d-flex justify-content-between align-items-center top-margin">
                    <h6 class="fw-bold">Doctor: ${group.doctorName}</h6>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p>OPD ID: ${group.ipdId} 
                    <a href="#" class="view-test-details ms-2" data-opd="${group.ipdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p>Patient: ${group.patientName}</p>
            </div>
        `;

		// Add event listener to the eye icon
		row.querySelector(".view-test-details").addEventListener("click", function(event) {
			event.preventDefault();
			const ipdId = event.currentTarget.getAttribute("data-ipd");
			renderTestDetails(ipdId);
		});

		container.appendChild(row);
	});
}


function renderTestDetails(ipdId) {
	$("#testDetailContainer").empty();
	$("#testGridGrid1").hide();
	$("#testDetailContainer").show();

	const records = testData.filter(item => item.ipdId === ipdId);
	const detailContainer = document.getElementById("testDetailContainer");

	if (!detailContainer) {
		console.error("Test details container not found!");
		return;
	}

	let detailsHtml = `
	        <div class="border rounded-2 p-3 mt-3 test-detail-card line-height">
	            <h6 class="d-flex justify-content-between align-items-center">
	                Test Details for IPD ID: ${ipdId}
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
                    <p class="fnt-13"><strong>Ot:</strong> ${ot.otId}</p>
                    <p class="fnt-13"><strong>From Date:</strong> ${ot.fromDate}</p>
                    <p class="fnt-13"><strong>To Date:</strong> ${ot.toDate}</p>
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