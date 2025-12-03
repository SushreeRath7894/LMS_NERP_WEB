
$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var vitalGridDiv = document.querySelector('#vitalGrid');
	new agGrid.Grid(vitalGridDiv, gridOptionsVital);
	
	var gridDiv = document.querySelector('#treatmentGrid');
	new agGrid.Grid(gridDiv, gridOptionsTreatment);
	
	var gridDiv = document.querySelector('#testGrid');
	new agGrid.Grid(gridDiv, gridOptionsTest);

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
		gridOptionsVital.api.setRowData();
	gridOptionsTreatment.api.setRowData();
	gridOptionsTest.api.setRowData();

$(".br-s-btn").hide();
	$(".br-s-btn-tx").hide();
	$(".br-s-btn-tst").hide();
	$(".br-s-btn-d").hide();

	agGrid.simpleHttpRequest({
		url: 'his-nurse-ipd-view'
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
});
   

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
];

var gridOptions = {
columnDefs: columnDefs,
//rowData: rowData, 
rowSelection: 'single',
suppressRowClickSelection: true,
defaultColDef: {
	flex: 1,
	minWidth: 80,
    sortable: true,
    filter: true,
    resizable: true,
},
	paginationAutoPageSize: true,
	pagination: true,
onSelectionChanged: rowSelect,

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
		url: 'his-nurse-ipd-viewAllDetailsByIPDID?patientId=' + patientId + "&ipdId=" + ipdId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		console.log("jsonData==", jsonData)
		const vitalDetails = jsonData.vitalDetails || [];
		gridOptionsVital.api.setRowData(vitalDetails);

		const treatmentDetails = jsonData.treatmentDetails || [];
		if (treatmentDetails.length > 0) {
			$(".tx").show();
		} else {
			$(".tx").show();
		}
		gridOptionsTreatment.api.setRowData(treatmentDetails);

        const testDetails = jsonData.testDetails || [];
		if (testDetails.length > 0) {
			$(".tst").show();
		} else {
			$(".tst").show();
		}
		gridOptionsTest.api.setRowData(testDetails);

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
		maxWidth: 30,
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
	},{
		headerName: "Staus",
		field: "vitalStatus",
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
		flex: 1,
		minWidth: 80
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
		maxWidth: 30,
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
	},{
		headerName: "Staus",
		field: "medicationStatus",
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
		minWidth: 80
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
	},{
		headerName: "Staus",
		field: "testStatus",
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
	$("#vitalStatus").val('');
}
function cancelVital() {
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
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
	data.vitalStatus = $("#vitalStatus").val();
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
		url: "his-nurse-save-vital-details",
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
		$("#vitalStatus").val(vitalDetails[0].vitalStatus);
		//alert(vitalDetails[0].vitalStatus)
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
	$("#medicationStatus").val('');
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
	var medicationStatus = $("#medicationStatus").val();
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
				dataset[index].medicationStatus = medicationStatus;
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
				medicationStatus: medicationStatus,
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
			url: "his-nurse-save-treatment-details",
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

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
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
		$("#medicationStatus").val(selectedData[0].medicationStatus);
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
	$("#testStatus").val('');
}
function cancelTest() {
	$(".br-m-btn-tst").show();
	$(".br-s-btn-tst").hide();
}


/*add test*/
function saveTest() {
	var validTestData = true;
	var testId = $("#testId").val();
	var testItemId = $("#testName").val();
	var testName = $("#testName option:selected").text();
	var testCategory = $("#testCategory").val();
	var testStatus = $("#testStatus").val();
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
				dataset[index].testStatus = testStatus;
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
				testStatus: testStatus,
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
			url: "his-nurse-save-test-details",
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
		url: "his-nurse-getDietMenu",
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
		url: "his-nurse-save-diet-details", // Replace with your actual API endpoint
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

var ipdIds = "";
function getActId(activityId) {

	if (activityId == "vital") {
		$("#vital").removeClass('hidden');
		$("#medication").addClass('hidden');
		$("#test").addClass('hidden');
		$("#dressing").addClass('hidden');
		$("#diet").addClass('hidden');

	} else if (activityId == "medication") {
		$("#vital").addClass('hidden');
		$("#medication").removeClass('hidden');
		$("#test").addClass('hidden');
		$("#dressing").addClass('hidden');
		$("#diet").addClass('hidden');

	} else if (activityId == "test") {
		$("#vital").addClass('hidden');
		$("#medication").addClass('hidden');
		$("#test").removeClass('hidden');
		$("#dressing").addClass('hidden');
		$("#diet").addClass('hidden');

	} else if (activityId == "dressing") {
		$("#vital").addClass('hidden');
		$("#medication").addClass('hidden');
		$("#test").addClass('hidden');
		$("#dressing").removeClass('hidden');
		$("#diet").addClass('hidden');
		
	} else if (activityId == "diet") {
		$("#vital").addClass('hidden');
		$("#medication").addClass('hidden');
		$("#test").addClass('hidden');
		$("#dressing").addClass('hidden');
		$("#diet").removeClass('hidden');

	}
	
	ipdIds = "";
}


function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	console.log(tabElement)
	const tab = new bootstrap.Tab(tabElement);
	console.log(tab)
	tab.show();
}