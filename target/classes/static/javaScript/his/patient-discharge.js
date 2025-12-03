$(document).ready(function() {
	var dischargeDiv = document.querySelector('#myDischargeGrid');
	new agGrid.Grid(dischargeDiv, dischargeGridOption);
	dischargeGridOption.api.setRowData([]);

	dischargeGridView();

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});

	var dateFormat = localStorage.getItem("dateFormat");
	$("#dischargeDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0,
	}).on("change", function() {
		$('#dateOfDischarge').val($(this).val());
	})

	$('#dateOfDischarge').blur(function() {
		$("#dischargeDateCalendar").val($(this).val());
	})

	$("#admitDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0,
	}).on("change", function() {
		$('#admitDate').val($(this).val());
	})

	$('#admitDate').blur(function() {
		$("#admitDateCalendar").val($(this).val());
	})

	$(".br-s-btn-tx").hide();

	CKEDITOR.replace('finalDiagnosis', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('historyClinical', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	CKEDITOR.replace('followOfAdvice', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
	$("#doctbodyData").empty();
	let i = 0;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
		+ '<td>' + a + '</td>'
		+ '</tr>';
	$("#doctbodyData").append(tbl);

	$('#dischargeType').select2({
		placeholder: "Select",
		allowClear: true
	});
});

var dischargeColumnDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
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
	headerName: "Name",
	field: "pName",
	flex: 1,

},
{
	headerName: "Age",
	field: "age",
	flex: 1,

},
{
	headerName: "Payment Status",
	field: "pay_status",
	flex: 1,
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
{
	headerName: "Discharge Status",
	field: "dischargeStatus",
	width: 135,
	cellRenderer: function(params) {
		if (params.data.dischargeStatus == 1) {
			return '<a style="color:green; font-weight: bold;">Discharged <i class="fa fa-check-circle" style="color:green; margin-left:5px;"></i></a>';
		} else {
			return '<a style="color:red; font-weight: bold;">Not Discharged <i class="fa fa-times-circle" style="color:red; margin-left:5px;"></i></a>';
		}

	}
},
{
	headerName: "Address",
	field: "address",
	flex: 1,

},
{
	headerName: "Mobile",
	field: "mobNo",
	flex: 1,

},
{
	headerName: "Status",
	field: "patientStatus",
	flex: 1,
	hide: true
},

];

var dischargeGridOption = {
	columnDefs: dischargeColumnDefs,
	//rowData: rowData, 
	rowSelection: 'single',
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 19,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},

	onSelectionChanged: rowSelect,
	onGridReady: checkGridData,

};
function checkGridData() {
	let rowCount = dischargeGridOption.api.getDisplayedRowCount();
	if (rowCount === 0) {
		console.log("Grid is empty!");

	}
}
let selectedPatientIds;
function rowSelect() {
	var selectedNodes = dischargeGridOption.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	let bookingId = selectedData.map(item => item.bookingId);
	let dischargeStatus = selectedData.map(item => item.dischargeStatus);
	console.log("Discharge Status----->",dischargeStatus);
	selectedPatientIds = selectedData.map(item => item.patientId);
	console.log("SELECTED ROW DATA--------->", selectedData);
	dischargeStatus[0] == 1 ? ($("#downloadPDFBtn").removeClass('d-none'),$("#saveDischargeBtn").addClass('d-none'),$("#deleteDischargeBtn").removeClass('d-none')):($("#downloadPDFBtn").addClass('d-none'),$("#saveDischargeBtn").removeClass('d-none'),$("#deleteDischargeBtn").addClass('d-none'));
	if (!selectedNodes || selectedNodes.length === 0) {
	} else {
		getPatientDetails(bookingId);
		getTreatmentHistory();
		getTestHistory();
	}
}
function dischargeGridView() {

	agGrid.simpleHttpRequest({
		url: 'manage-ipd-view'
	}).then(function(data) {
		var jsonData = JSON.parse(data?.body);
		var allData = jsonData?.patientDetails;
		console.log(allData)
		if (allData?.length > 0) {
			dischargeGridOption.api.setRowData(allData);
			var firstRowNode = dischargeGridOption.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		} else {
			dischargeGridOption.api.setRowData([]);
		}
	});
}
function onQuickFilterChanged() {
	dischargeGridOption.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (dischargeGridOption.api) {
			dischargeGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged()
	}
}
function resetBtn() {
	$("#quickFilter").val('');
	dischargeGridOption.api.setQuickFilter('');
	dischargeGridOption.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (dischargeGridOption.api) {
			dischargeGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}
function getPatientDetails(id) {
	nextTab('information-tab');
	$.ajax({
		url: 'patient-discharge-get-details?bookingId=' + id,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				let patientDetails = response.body;
				console.log("Patient Details------->", patientDetails);
				$('#bookingId').val(id);
				$('#patientName').val(patientDetails[0].patientName);
				$('#patientId').val(patientDetails[0].patientId);
				$('#ipdId').val(patientDetails[0].ipdId);
				$('#admitDate').val(patientDetails[0].admitDate);
				$('#patientNameHead').text(patientDetails[0].patientName);
				$('#patientNameHead1').text(patientDetails[0].patientName);
				$('#patientNameHead2').text(patientDetails[0].patientName);
				$('#patientNameHead3').text(patientDetails[0].patientName);
				$('#dischargeAddress').val(patientDetails[0].patientAddress);
				$('#consultantName').val(patientDetails[0].doctorName);
				$('#doctorId').val('');
				$('#doctorId').val(patientDetails[0].doctorId);

				let dischargeDetails = patientDetails[0].dischargeDetails;
				console.log(dischargeDetails);
				$('#dischargeId').val(dischargeDetails.dischargeId || '');
				$('#dateOfDischarge').val(dischargeDetails.dischargeDate || '');
				$('#timeOfDischarge').val(dischargeDetails.dischargeTime || '');
				$('#bedNo').val(dischargeDetails.patientBed || '');
				$('#wardNo').val(dischargeDetails.patientWard || '');
				$('#dischargeType').val(dischargeDetails.dischargeType || '').trigger('change');

				CKEDITOR.instances['finalDiagnosis'].setData(dischargeDetails.finalDiagnosis);
				CKEDITOR.instances['historyClinical'].setData(dischargeDetails.clinicalNotes);
				CKEDITOR.instances['followOfAdvice'].setData(dischargeDetails.followAdvice);

				$("#doctbodyData").empty();
				if (dischargeDetails.dischargeDocuments != null && dischargeDetails.dischargeDocuments != "") {
					let documents = dischargeDetails.dischargeDocuments;
					console.log(documents);
					for (var i = 0; i < documents.length; i++) {
						let cls = 'ti-plus';
						if (documents[i].fileName) {
							cls = 'ti-pencil';
						}
						let a = '<div class="form-group d-flex"><div class="">' +
							'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="' + cls + '" id="clickImg_' + i + '"></i> </label> ' +
							'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">' +
							'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value="' + documents[i].fileName + '"> <div class="uploadedBillCls mt-2">' +
							'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '">' + documents[i].action + '</div></div>' +
							'<div id="validationDiv"></div></div>';

						var tbl = '<tr>' +
							'<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
							'<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>' +
							'<td><div class="form-group"> <input type="text" value="' + documents[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>' +
							'<td> ' + a + ' </td>' +
							'</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">' +
						'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
						'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">' +
						'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
						'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
						'<div id="validationDiv"></div></div>';

					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>' + a + '</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}

			} else {
				console.error("Error fetching data");
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});

}
function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}

/*Treatment Section Start*/
let treatmentData = [];
function getTreatmentHistory() {
	$("#treatmentDetailContainer").empty();
	let activeTabId = 'treatment';
	agGrid.simpleHttpRequest({
		url: "opd-manage-view-treatment-his?types=" + activeTabId + "&patId=" + selectedPatientIds
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
            <div class="border rounded-2 p-3 mb-3 vital-card shadow-sm">
                <div class="d-flex justify-content-between align-items-center top-margin">
                    <h6 class="fw-bold">Doctor: ${group.doctorName}</h6>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p>OPD ID: ${group.opdId} 
                    <a href="#" class="view-details ms-2" data-opd="${group.opdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p>Patient: ${group.patientName}</p>
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
/*Test Details*/
let testData = [];

function getTestHistory() {

	$("#testDetailContainer").empty();
	let activeTabId = 'test';
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
                <div class="d-flex justify-content-between align-items-center top-margin">
                    <h6 class="fw-bold">Doctor: ${group.doctorName}</h6>
                    <span>${formatDateTime(group.createdOn)}</span>
                </div>
                <p>OPD ID: ${group.opdId} 
                    <a href="#" class="view-test-details ms-2" data-opd="${group.opdId}" title="View Details">
                        <i class="fa fa-eye"></i>
                    </a>
                </p>
                <p>Patient: ${group.patientName}</p>
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

/*Document Section*/
function checkEmptyQuot() {

	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclsss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error('Please Choose a File');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMoreQuot()
	}
}
function addMoreQuot() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:last").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclsss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	// $("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');
		selectInput.eq(0).attr('id', "docid_" + i);

		textInput.eq(1).attr('id', "docnoid_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(5).attr('id', "uploadedBillDiv_" + i);
		/*divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);*/
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

	$("#uploadedBillDiv_" + lengthOfTableRow).empty();

}
function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;

	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}

	let fnametext = '<div id="imageName_' + counter + '" class="imageName" style="margin-left: 2px;">' + fileName + '</div><span><i class="ti-close red close_sec1 deleteFileDoc" onclick=openDeleteConfirm(' + counter + ')></i></span>';

	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-pdf custom-file-icon'></i> </a>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}

	$("#clickImg_" + counter).removeClass('ti-plus').addClass('ti-pencil');
	// $("#uploadHidden_"+ counter).val(fileName);
	$("#uploadedBillDiv_" + counter).html(LightImg + fnametext);

}
function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;

	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if (cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});

}
function readFileAsDataURL(file) {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.onload = () => {
			const data = reader.result.split(",");
			resolve(data[1]);
		};
		reader.onerror = reject;
		reader.readAsDataURL(file);
	});
}

async function saveDischargeData() {
	let uploadList = [];
	var finalDiagnosis = CKEDITOR.instances.finalDiagnosis.getData();
	var historyClinical = CKEDITOR.instances.historyClinical.getData();
	var followOfAdvice = CKEDITOR.instances.followOfAdvice.getData();

	const rows = $("#doctbodyData > tr").toArray();

	for (const row of rows) {
		var uFile = $(row).find(".document")[0].files[0];
		var fileName = $(row).find(".document").val();
		let x = [];

		if (fileName) {
			var lastIndex = fileName.lastIndexOf("\\");
			if (lastIndex >= 0) {
				fileName = fileName.substring(lastIndex + 1);
			}

			if (uFile) {
				const base64Data = await readFileAsDataURL(uFile);
				x.push(base64Data);
			}
		} else {
			if ($("#quotationId").val()) {
				fileName = $(row).find(".uploadHidCls").val();
			}
		}

		uploadData = {
			dischargeId: $("#dischargeId").val(),
			documnentName: $(row).find(".docNoclsss").val(),
			documentFile: x,
			fileName: fileName,
			imageNameEdit: $(row).find(".uploadHidCls").val(),
		};

		if ($(row).find(".docNoclsss").val() && fileName) {
			uploadList.push(uploadData);
		}
	}
	let dischargeDate=$('#dateOfDischarge').val();
	let dischargeTime=$('#timeOfDischarge').val();
	let dischargeType=$('#dischargeType').val();
	
	if (dischargeDate == null || dischargeDate.trim() == "") {
        nextTab('information-tab');
        toastr.error('Discharge Date Required');
        return false;
    }
    if (dischargeTime == null || dischargeTime.trim() == "") {
        nextTab('information-tab');
        toastr.error('Discharge Time Required');
        return false;
    }
    if (dischargeType == null || dischargeType.trim() == "") {
        nextTab('information-tab');
        toastr.error('Discharge Type Required');
        return false;
    }
    if (followOfAdvice == null || followOfAdvice.trim() == "") {
        nextTab('advice-tab');
        toastr.error('Follow Of Advice Required');
        return false;
    }

	let dischargeDatas = {
		dischargeId: $('#dischargeId').val(),
		patientName: $('#patientName').val(),
		patientId: $('#patientId').val(),
		ipdId: $('#ipdId').val(),
		bookingId: $('#bookingId').val(),
		admitDate: $('#admitDate').val(),
		bedNo: $('#bedNo').val(),
		wardNo: $('#wardNo').val(),
		dischargeDate: $('#dateOfDischarge').val(),
		dischargeTime: $('#timeOfDischarge').val(),
		dischargeAddress: $('#dischargeAddress').val(),
		dischargeType: $("#dischargeType").val(),
		doctorId: $("#doctorId").val(),
		finalDiagnosis: finalDiagnosis,
		historyClinical: historyClinical,
		followOfAdvice: followOfAdvice,
		docList: uploadList
	};

	console.log("Discharge Data---------->", dischargeDatas);
	saveAllDischargeDate(dischargeDatas);
}

function saveAllDischargeDate(dischargeDatas) {
	let jsonData = JSON.stringify(dischargeDatas);
	console.log("JSON STRINGIFY DATA----------->", jsonData);
	$.ajax({
		url: 'patient-discharge-save-data',
		type: 'POST',
		contentType: 'application/json',
		data: jsonData,
		success: function(response) {
			if (response.code === "success") {
				dischargeGridView();
				dischargeGridOption.api.deselectAll();
				toastr.success('Patient Discharged successfully');
				nextTab('information-tab');
				setTimeout(() => {
					const bookingId = dischargeDatas.bookingId;
					const allNodes = [];

					dischargeGridOption.api.forEachNode((node) => {
						allNodes.push(node);
					});

					const rowNode = allNodes.find(node => node.data.bookingId == bookingId);

					if (rowNode) {
						rowNode.setSelected(true);
						dischargeGridOption.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
				/*calendar.refetchEvents();
				showSnackbar(response.message)
				$('#eventModal').modal('hide');
				getMeetingLists(meetingStatus);
				cancelModal();
				getVitalMeeting();
				setMeetingDetailsTabAsDefault();
			} else {
				showSnackbar(response.message)
				$('#eventModal').modal('show');*/
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}
function dischargePdfDownload() {
	var selectedNodes = dischargeGridOption.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var bookingId = selectedData[0].bookingId;
	window.open("/his/patient-discharge-get-details-for-pdf?bookingId=" +
		bookingId, '_blank');
}
function deleteDischarge(){
	var selectedNodes = dischargeGridOption.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var bookingId = selectedData[0].bookingId;
	$.ajax({
		url:'patient-discharge-delete-data?bookingId='+bookingId,
		type:'get',
		dataType:'json',
		success:function(response){
			if(response.code == "success"){
				toastr.success('Discharge Data Deleted Successfully')
				dischargeGridView();
				setTimeout(() => {
					const allNodes = [];

					dischargeGridOption.api.forEachNode((node) => {
						allNodes.push(node);
					});

					const rowNode = allNodes.find(node => node.data.bookingId == bookingId);

					if (rowNode) {
						rowNode.setSelected(true);
						dischargeGridOption.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
			}else{
				
			}
		},
		error: function() {
        toastr.error("Error in API call");
	}
	});
}