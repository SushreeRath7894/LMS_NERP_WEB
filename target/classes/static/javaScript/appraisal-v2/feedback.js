$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData([]);

	$("#feedbackDiv").hide();
	$("#saveRemarks").hide();
	$("#cancelBtn").hide();


	$(".chosen-select").chosen({
		no_results_text: "Oops, nothing found!"
	});
	$('[data-bs-toggle="tooltip"]').tooltip();

	getFinancialYear();
	getEmpDetails();
});

/*getting list of all employee*/
function getEmpDetails() {
	const finYear = $("#financialYear").val();
	agGrid.simpleHttpRequest({
		url: "get-all-360-employee-list?finYear=" + finYear,
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body);
			const goalDetails = responseBody.employees;
			if (goalDetails == null) {
				gridOptions.api.setRowData([]);
				getEmployeBandGoalDetails('', '');
				const tableBody = $("#kra-table-dep tbody");
				tableBody.empty();

				$("input[name='rating']").prop("checked", false);
				$("#summary1").val('');
				$("#requirementDescription").val('');
				$("#getEmpDiv").hide();

				$("input[name='ratingMan']").prop("checked", false);
				$("#summary2").val('');
				$("#managerResponse").val('');
				getAllFeedback('');
			} else {
				var newRowData = goalDetails.reverse();
				gridOptions.api.setRowData(newRowData);
				if (newRowData && newRowData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
			}

		} else {
			console.error("Failed to fetch data");
		}
	});

}

const appraisalColumnDefs = [
	{ headerCheckboxSelection: true, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
	{ headerName: "Employee ID", field: "empId" },
	{ headerName: "Name", field: "empName" },
	{ headerName: "Designation", field: "designationName" },
	{ headerName: "Band", field: "bandName" },
	{ headerName: "Email ID", field: "personalMail" },
	{ headerName: "Band Id", field: "bandId", hide: true }
];

// ag-Grid options
const gridOptions = {
	columnDefs: appraisalColumnDefs,
	defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 19,
	rowData: [],
	onSelectionChanged: rowSelect
};

function rowSelect() {

	var selectedRows = gridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const datas = selectedRows[0];
		const bandId = (datas.bandId);
		const empId = (datas.empId);
		/*$("#empNames").text(datas.empName);*/
		getEmployeBandGoalDetails(bandId, empId);
		getAllKraLists(bandId);
		getAllFeedback(empId);

	} else {
		getEmployeBandGoalDetails('', '');
		getAllKraLists('');
		getAllFeedback('');
		const tableBody = $("#kra-table-dep tbody");
		tableBody.empty();
		
	}
}


function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

function getEmployeBandGoalDetails(id, empId) {
	const finYear = $("#financialYear").val();
	agGrid.simpleHttpRequest({
		url: "get-all-desig-goal-by-emp?id=" + id + "&empId=" + empId + "&finYear=" + finYear,
	}).then(function(response) {
		if (response.code === "Success" && response.body && Array.isArray(response.body)) {
			const parsedBody = JSON.parse(response.body[0]);


			if (parsedBody.desigGoalDetailsEmp && parsedBody.desigGoalDetailsEmp.length > 0) {

				populateResponse(parsedBody.desigGoalDetailsEmp);

			}
			if (parsedBody.desigGoalDetailsEmp && parsedBody.desigGoalDetailsEmp.length == null || parsedBody.desigGoalDetailsEmp && parsedBody.desigGoalDetailsEmp.length == "") {
				const tableBody = $("#kra-table-dep tbody");
				tableBody.empty();
				return;
			} else {
				parsedBody.desigGoalDetailsEmp.forEach(goal => {
					$("input[name='rating'][value='" + goal.ratings + "']").prop("checked", true);
					$("input[name='ratingMan'][value='" + goal.ratingMan + "']").prop("checked", true);
					$("#summary1").val(goal.goalRemark);
					$("#summary2").val(goal.goalRemarkMan);
					$("#selfAppId").val(goal.appraisalId);
					$("#requirementDescription").val(goal.empRequirement).prop("disabled", true);
					$("#managerResponse").val(goal.manResponse).prop("disabled", true);
				});
			}
		}
	});
}

function populateResponse(empGoalDetails) {
	const tableBody = $("#kra-table-dep tbody");
	tableBody.empty();
	const userIdLogin = document.getElementById("userId").value;
	var selectedRows = gridOptions.api.getSelectedRows();
	const datas = selectedRows[0];
	const empId = datas.empId;

	if (!empGoalDetails || !Array.isArray(empGoalDetails)) {
		console.error("Invalid employee goal details format", empGoalDetails);
		return;
	}


	let goalCounter = 0;

	empGoalDetails.forEach((emp, empIndex) => {
		if (!emp || !Array.isArray(emp.employeeAppDetails)) {
			console.warn(`Skipping invalid employee data at index ${empIndex}`, emp);
			return;
		}

		emp.employeeAppDetails.forEach((designation, desigIndex) => {
			if (!designation || !designation.designationId || !designation.designationName) {
				console.warn(`Skipping invalid designation at index ${desigIndex}`, designation);
				return;
			}

			goalCounter++;
			let desigHtml = `
										                <tr class="designation-header">
										                    <td colspan="6" class="designation-style">
										                        ${goalCounter}. ${designation.designationName} (${designation.designationId})
										                    </td>
										                </tr>`;
			tableBody.append(desigHtml);

			let kraCounter = 0;
			if (!designation.kras || !Array.isArray(designation.kras)) {
				console.warn(`No valid KRAs for Designation ${designation.designationId}`);
				return;
			}

			designation.kras.forEach((kra, kraIndex) => {
				if (!kra || !kra.kraId || !kra.kraName) {
					console.warn(`Skipping invalid KRA at index ${kraIndex}`, kra);
					return;
				}

				kraCounter++;
				let kraHtml = `
										                    <tr class="kra-header">
										                        <td colspan="6" class="kra-style">
										                            ${goalCounter}.${kraCounter} ${kra.kraName} (${kra.kraId})
										                        </td>
										                    </tr>`;
				tableBody.append(kraHtml);

				let kpiCounter = 0;
				if (!kra.kpis || !Array.isArray(kra.kpis)) {
					console.warn(`No valid KPIs for KRA ${kra.kraId}`);
					return;
				}

				kra.kpis.forEach((kpi, kpiIndex) => {
					if (!kpi || !kpi.kpiName) {
						console.warn(`Skipping invalid KPI at index ${kpiIndex}`, kpi);
						return;
					}

					kpiCounter++;
					let selectId = `rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
					let managerSelectId = `manager-rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
					let managerNoteId = `manager-note-${desigIndex}-${kraIndex}-${kpiIndex}`;
					let notesId = `notes-${desigIndex}-${kraIndex}-${kpiIndex}`;

					let kpiHtml = `
										                        <tr class="kpi-row">
										                            <td>${goalCounter}.${kraCounter}.${kpiCounter} ${kpi.kpiName}</td>
										                            <td>
										                                <input type="number" class="form-control weightage-input" value="${kpi.weightage || 0}" disabled>
										                            </td>
										                            <td>
										                                <select class="form-control ratings-dropdown" id="${selectId}">
										                                    <option value="">Select Rating</option>
										                                    <option value="1">1 - Poor</option>
										                                    <option value="2">2 - Fair</option>
										                                    <option value="3">3 - Good</option>
										                                    <option value="4">4 - Very Good</option>
										                                    <option value="5">5 - Excellent</option>
										                                </select>
										                            </td>
										                            <td>
										                                <input type="text" class="form-control notes-input" value="${kpi.note || ''}">
										                            </td>
										                            <td>
										                                <select class="form-control manager-ratings-dropdown" id="${managerSelectId}">
										                                    <option value="">Select Rating</option>
										                                    <option value="1">1 - Poor</option>
										                                    <option value="2">2 - Fair</option>
										                                    <option value="3">3 - Good</option>
										                                    <option value="4">4 - Very Good</option>
										                                    <option value="5">5 - Excellent</option>
										                                </select>
										                            </td>
										                            <td>
										                                <input type="text" class="form-control manager-notes-input" id="${managerNoteId}" value="${kpi.manNote || ''}">
										                            </td>
										                        </tr>`;
					tableBody.append(kpiHtml);

					$(`#${CSS.escape(selectId)}`).select2({
						placeholder: "Select Rating",
						allowClear: true
					});
					$(`#${selectId}`).val(kpi.rating || "").trigger('change');

					$(`#${CSS.escape(managerSelectId)}`).select2({
						placeholder: "Select Rating",
						allowClear: true
					});
					if (kpi.manRating) {
						$(`#${managerSelectId}`).val(kpi.manRating).trigger('change');
					} else {
						$("input[name='ratingMan']").prop("checked", false);
					}

				});
			});
		});
	});

	// Disable fields based on user role
	if (userIdLogin === empId) {
		$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
		$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
		$("input[name='ratingMan']").prop("disabled", true);
		$("input[name='rating']").prop("disabled", true);
		$("#summary2").prop("disabled", true);
	} else {
		$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
		$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
		$("input[name='rating']").prop("disabled", true);
		$("input[name='ratingMan']").prop("disabled", true);
		$("#summary1").prop("disabled", true);
		$("#summary2").prop("disabled", true);
	}
}


/*Configuration starts from here*/

function onQuickFilterChanged() {

	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	getMostClosestRow();
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);

		}
	});
}


function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	getMostClosestRow();
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		gridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);

				gridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);

	getFinancialYear();
	getEmpDetails();
}

function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13 || event.key === "Backspace" || event.keyCode === 8) {
		onQuickFilterChanged();
	}
}



function addFeedback() {
	$("#employeeName").val([]).trigger("chosen:updated");

	$("#feedbackDiv").show();
	$("#managerRemarksContainer").hide();
	$("#saveRemarks").show();
	$("#cancelBtn").show();
	$("#remarkBtn").hide();
	CKEDITOR.replace('description');

	$("#feedbackDetailsContainer").hide();
}

function setEmailFromDropdown() {
	const emailInput = document.getElementById("email");

	const selectedOptions = document.querySelectorAll("#employeeName option:checked");
	const selectedDropdownEmails = Array.from(selectedOptions)
		.map(option => option.getAttribute("data-code"))
		.filter(email => email);
	const allDropdownOptions = document.querySelectorAll("#employeeName option");
	const allDropdownEmails = Array.from(allDropdownOptions)
		.map(option => option.getAttribute("data-code"))
		.filter(email => email);
	const currentEmails = emailInput.value
		.split(',')
		.map(email => email.trim())
		.filter(email => email);
	const updatedEmails = currentEmails.filter(email =>
		!allDropdownEmails.includes(email) || selectedDropdownEmails.includes(email)
	);
	const mergedEmails = Array.from(new Set([...updatedEmails, ...selectedDropdownEmails]));
	emailInput.value = mergedEmails.join(', ') + (mergedEmails.length > 0 ? ', ' : '');
}


function savemanagerRemarks() {
	$("#savemanagerRemarks").hide();

	if ($('#employeeName option:selected').length === 0) {
		toastr.error("Employee Required !");
		return;
	}

	if ($("#email").val().trim() === "") {
		toastr.error("Email Required !");
		return;
	}
	
    const finYear=$("#financialYear").val();
   
	const descriptionHtml = CKEDITOR.instances.description.getData();
	const descriptionText = $("<div>").html(descriptionHtml).text().trim();

	if (descriptionText === "") {
		toastr.error("Description Required !");
		return;
	}

	if ($("#remarks").val().trim() === "") {
		toastr.error("Remarks Required !");
		return;
	}

	const selectedRows = gridOptions.api.getSelectedRows();
	const datas = selectedRows[0];
	const bandId = datas.bandId;
	const empId = datas.empId;
	const empName = datas.empName;

	const managerList = [];
	const selectedEmails = [];

	$('#employeeName option:selected').each(function() {
		const email = $(this).attr("data-code");
		selectedEmails.push(email);

		managerList.push({
			managerId: $(this).val(),
			managerName: $(this).text(),
			email: email
		});
	});

	const allInputEmails = $("#email").val()
		.split(',')
		.map(email => email.trim())
		.filter(email => email);

	const extraEmails = allInputEmails.filter(email => !selectedEmails.includes(email));

	extraEmails.forEach(email => {
		managerList.push({
			managerId: "",
			managerName: "",
			email: email
		});
	});

	// KRA list
	const kraList = [];
	$('#kra-table tbody tr.read-only').each(function() {
		kraList.push({
			kraId: $(this).attr('data-kra-id'),
			kraName: $(this).find('.kra-name').val(),
			notes: $(this).find('.notes').val(),
			rating: $(this).find('.ratings-dropdown').val()
		});
	});

	if (kraList.length < 1) {
		toastr.error("At least one KRA is required!");
		return;
	}

	// Final object
	const managerRemarksData = {
		empId: empId,
		bandId: bandId,
		empName: empName,
		reviewId: $("#reviewId").val(),
		description: descriptionText,
		remarks: $("#remarks").val(),
		managerList: managerList,
		kraList: kraList,
		finYear:finYear
	};

	console.log(managerRemarksData);
	saveManagerRemarksData(managerRemarksData)
}


function saveManagerRemarksData(managerRemarksData) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-manager-remark-details",
		contentType: "application/json",
		data: JSON.stringify(managerRemarksData),
		success: function(response) {
			if (response.code == "Success") {
				toastr.success(response.message);
				$(".formValidation").remove();
				$('.loader').hide();
				cancelRemarks();
				
				var selectedRows = gridOptions.api.getSelectedRows();
				var selectedRows = gridOptions.api.getSelectedRows();
				const datas = selectedRows[0];
				const empId = (datas.empId);
				getAllFeedback(empId)
			} else {
				$('.loader').show();
			}
		},
		error: function(datas) {
			console.log(datas);
		}
	});
}

function cancelRemarks() {
	$("#managerRemarksContainer").show();
	$("#feedbackDiv").hide();
	$("#remarkBtn").hide();
	$("#addFeedback").show();
	$("#saveRemarks").hide();
	$("#cancelBtn").hide();
	$("#remarkBtn").show();
	clearRemarksForm();
	$("#feedbackDetailsContainer").show();
};
function clearRemarksForm() {
	$("#reviewId").val('').prop('disabled', false);
	$("#employeeName").val('').trigger('change').prop('disabled', false);
	$("#email").val('').prop('disabled', false);
	$("#description").val('').prop('disabled', false);
	$("#remarks").val('').prop('disabled', false);
	$("#kra-table tbody").empty();
}


let kraListGlobal = [];

function getAllKraLists(id) {
	agGrid.simpleHttpRequest({
		url: "get-all-kra-lists?id=" + id,
	}).then(function(response) {
		if (response.code === "Success" && response.body && Array.isArray(response.body)) {
			const parsedBody = JSON.parse(response.body[0]);

			if (parsedBody.kraLists && Array.isArray(parsedBody.kraLists)) {
				kraListGlobal = parsedBody.kraLists.map(item => JSON.parse(item.kra));
			}
		}
	});
}

/*All function for kra and kpi*/
function addKra() {
	$("#feedbackDiv").show();

	let lastRow = $('#kra-table tbody tr:last');
	if (lastRow.length && !lastRow.hasClass('read-only')) {
		toastr.error("Please save the current KRA before adding a new one.");
		return;
	}

	let lastKraId = 0;
	const selectedKras = [];


	$('#kra-table tbody tr').each(function() {
		let kraText = $(this).attr('data-kra-id');
		if (kraText) {
			let kraNumber = parseFloat(kraText.split('.')[1]);
			if (!isNaN(kraNumber) && kraNumber > lastKraId) {
				lastKraId = kraNumber;
			}
		}

		const selectedVal = $(this).find('.kra-name').val();
		if (selectedVal) {
			selectedKras.push(selectedVal);
		}
	});

	const newKraId = `1.${lastKraId + 1}`;

	let dropdownOptions = `<option></option>`;
	kraListGlobal.forEach(kra => {
		const disabled = selectedKras.includes(kra.kraName) ? 'disabled' : '';
		dropdownOptions += `<option value="${kra.kraName}" ${disabled}>${kra.kraName}</option>`;
	});

	const newRow = $(`
					<tr data-kra-id="${newKraId}">
						<td>
							<select class="form-control kra-name kra-select2">
								${dropdownOptions}
							</select>
						</td>
						<td>
							<input type="text" class="form-control notes" placeholder="Enter Notes">
						</td>
						<td class="text-center">
							<button class="btn go-btn save-kra">
								<i class="fa-solid fa-floppy-disk"></i>
							</button>
							<button class="btn go-btn edit-kra" style="display:none;">
								<i class="fa fa-pencil-square"></i>
							</button>
							<button class="btn go-btn remove-kra">
								<i class="fas fa-trash"></i>
							</button>
						</td>
					</tr>
				`);

	$('#kra-table tbody').append(newRow);

	newRow.find('.kra-select2').select2({
		placeholder: "Select KRA",
		allowClear: true,
		width: '100%'
	});
}


$(document).on('click', '.save-kra', function() {
	const row = $(this).closest('tr');
	const kraName = row.find('.kra-name').val().trim();
	const notes = row.find('.notes').val().trim();

	if (!kraName || !notes) {
		toastr.error('All fields are required!');
		return;
	}

	row.addClass('read-only');
	row.find('.save-kra').hide();
	row.find('.edit-kra').show();

	row.find('.kra-name, .notes')
		.prop('disabled', true)
		.css({
			'border': 'none',
			'background': 'transparent',
			'box-shadow': 'none'
		});


});

$(document).on('click', '.edit-kra', function() {
	const row = $(this).closest('tr');
	row.removeClass('read-only');
	row.find('.edit-kra').hide();
	row.find('.save-kra').show();

	row.find('.kra-name, .notes')
		.prop('disabled', false)
		.css({
			'border': '',
			'background': '',
			'box-shadow': ''
		});

});

$(document).on('click', '.remove-kra', function() {
	$(this).closest('tr').remove();
});



function getAllFeedback(id) {
	cancelRemarks();
	$("#feedbackDetailsContainer").empty().show();
     const finYear=$("#financialYear").val();
	agGrid.simpleHttpRequest({
		url: "get-feedback-details?id=" + id + "&finYear=" + finYear,
	}).then(function(response) {
		if (response.code === "Success" && response.body && Array.isArray(response.body)) {
			const parsedBody = JSON.parse(response.body[0]);
			const feedbackList = parsedBody.feedbackDetails || [];

			if (feedbackList.length === 0) {
				const noDataMsg = $(`
					<div class="vital-card p-3 mb-3 text-center shadow-sm">
						<span class="no-data">No feedback data is available !</span>
					</div>
				`);
				$("#feedbackDetailsContainer").append(noDataMsg);
				return;
			}

			feedbackList.forEach((fb, index) => {
				const managerNames = (fb.managerDetails || []).map(m => m.managerName).filter(Boolean).join(", ") || "Unknown";
				const createdOn = formatDateTime(fb.TMFD_CreatedOn);
				const feedbackId = fb.feedbackId;

				const card = $(`
		<div class="vital-card p-3 mb-3 shadow-sm">
			<div class="d-flex justify-content-between align-items-center">
				<div>
					<h6 class="fw-bold">Forwarded To: ${managerNames}</h6>
					<p class="mb-1">Forwarded On: ${createdOn}</p>
				</div>
				<a href="#" class="view-feedback" data-index="${index}" title="View Details">
					<i class="fa fa-eye fa-lg eye-icon"></i>
				</a>
			</div>
		</div>
	`);


				card.find(".view-feedback").on("click", function(e) {
					e.preventDefault();
					populateFeedbackForm(feedbackList[index]);
				});

				$("#feedbackDetailsContainer").append(card);
			});
		} else {
			// Handle failed response or invalid format
			const errorMsg = $(`
				<div class="vital-card p-3 mb-3 text-center shadow-sm">
					<p class="mb-0 fw-bold text-danger">Error fetching feedback details.</p>
				</div>
			`);
			$("#feedbackDetailsContainer").append(errorMsg);
		}
	});
}

function populateFeedbackForm(feedback) {
	$("#feedbackDetailsContainer").hide();
	$("#feedbackDiv").show();
	$("#cancelBtn").show();
	$("#remarkBtn").hide();
	$("#reviewId").val(feedback.feedbackId);

	const managerIds = (feedback.managerDetails || []).map(md => md.managerId).filter(id => id);
	$("#employeeName").val(managerIds).trigger("chosen:updated").prop('disabled', true);

	const emails = (feedback.managerDetails || []).map(md => md.email).filter(e => e).join(", ");
	$("#email").val(emails);

	CKEDITOR.replace('description');
	if (CKEDITOR.instances['description']) {
		CKEDITOR.instances['description'].setData(feedback.objective || "");

	}

	$("#remarks").val(feedback.remarks || "").prop('disabled', true);

	$("#kra-table tbody").empty();

	(feedback.kraDetails || []).forEach(kra => {
		const row = $(`
					<tr class="read-only" data-kra-id="${kra.kraId}">
						<td>${kra.kraName}</td>
						<td>${kra.notes}</td>
						<td class="text-center">
							<button class="btn go-btn remove-kra" disabled>
								<i class="fas fa-trash"></i>
							</button>
							
							<button class="btn go-btn save-kra" disabled>
								<i class="fa-solid fa-floppy-disk"></i>
							</button>
								<button class="btn go-btn edit-kra" style="display:none;">
								<i class="fa fa-pencil-square"></i>
							</button>
							
						</td>
					</tr>
				`);

		row.find(".remove-kra").on("click", function() {
			row.remove();
		});

		$("#kra-table tbody").append(row);
	});
}
function formatDateTime(dateStr) {
	const d = new Date(dateStr);

	const day = d.getDate();
	const suffix = (day) => {
		if (day > 3 && day < 21) return 'th';
		switch (day % 10) {
			case 1: return 'st';
			case 2: return 'nd';
			case 3: return 'rd';
			default: return 'th';
		}
	};

	const months = [
		"January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"
	];

	const formattedDate = `${day}${suffix(day)} ${months[d.getMonth()]} ${d.getFullYear()}`;

	// Format time as HH:MM
	const hours = String(d.getHours()).padStart(2, '0');
	const minutes = String(d.getMinutes()).padStart(2, '0');
	const formattedTime = `${hours}:${minutes}`;

	return `${formattedDate} ${formattedTime}`;
}

function getFinancialYear() {
	const currentYear = new Date().getFullYear();
	const dropdowns = [
		document.getElementById('financialYear'),
	];

	dropdowns.forEach(dropdown => {
		dropdown.innerHTML = '<option value="">Select</option>';
	});

	for (let i = currentYear - 5; i <= currentYear + 5; i++) {
		const financialYear = `${i}-${i + 1}`;

		dropdowns.forEach(dropdown => {
			const option = document.createElement('option');
			option.value = financialYear;
			option.textContent = financialYear;
			if (i === currentYear) {
				option.selected = true;
			}

			dropdown.appendChild(option);
		});
	}
	$("#financialYear").select2({
		placeholder: "Select",
		allowClear: true
	});

}

function getAllDesignationGoal() {
	getEmpDetails();
}
