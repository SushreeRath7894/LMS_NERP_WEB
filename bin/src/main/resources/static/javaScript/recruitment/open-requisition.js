$(document).ready(function() {
	var gridDivRequi = document.querySelector('#myGridRequisition');
	new agGrid.Grid(gridDivRequi, gridOptionsRequi);

	gridOptionsRequi.api.setRowData([]);
	getRequisitionList();
});

$(document).ready(function() {
	$(".max-btn").on("click", function() {
		var parentCol = $(this).closest(".col-md-6, .col-md-12");

		if (parentCol.hasClass("col-md-6")) {
			parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
			parentCol.siblings(".col-md-6").hide();
		} else {
			parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
			parentCol.siblings(".col-md-6").show();
		}
	});

});

var columnDefsRequi = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Requisition ID",
	field: "requisitionId",
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.requisitionId + '</a>';
	}
}, {
	headerName: "Title",
	field: "jobTitle"
}, {
	headerName: 'Skills',
	field: "skills",
	hide: true,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {

		let encodedSkillsData = encodeURIComponent(JSON.stringify(params.data.skillsReq));

		return '<a onclick=skillsModal(decodeURIComponent(\'' + encodedSkillsData + '\'),\'' + params.data.requisitionId + '\') href="javascript:void(0)" title="Skills For Requisitions">'
			+ '<i class="fa fa-info-circle"> Required Skills </i>'
			+ '</a>';
	}

}, {
	headerName: "No. of positions",
	field: "noOfPositions",
	type: "rightAligned",
	width: 100,
	cellStyle: {
		textAlign: 'right'
	}
}, {
	headerName: "No. Of Rounds",
	field: "noOfRounds",
	hide: true,
	cellStyle: {
		textAlign: 'right'
	}
}, {
	headerName: "Min Exprience",
	field: "minExp",
	width: 100,
	cellStyle: {
		textAlign: 'right'
	}
}, {
	headerName: "Max Exprience",
	field: "maxExp",
	width: 100,
	cellStyle: {
		textAlign: 'right'
	}

}, {
	headerName: "Round Names",
	field: "roundNames"
}, {
	headerName: "Rounds UpdatedBy",
	field: "roundsUpdatedBy",
	hide: true,
}, {
	headerName: "Department",
	field: "departmentName"
}, {
	headerName: "Hiring Manager",
	field: "hiringManager"
}, {
	headerName: "Apply Start Date",
	field: "applyStartDate",
	width: 190,
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "Apply End Date",
	field: "applyEndDate",
	width: 190,
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "Band",
	field: "jobBand",
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "Join Date",
	field: "joinDate",
	cellStyle: {
		textAlign: 'center'
	}
}, {
	headerName: "CTC Cap",
	field: "minSalary",
	type: "rightAligned",
	valueFormatter: currencyFormatter,
}, {
	headerName: "Total Applicants",
	field: "applicants",
	type: "rightAligned",
}, {
	headerName: "Created Date",
	field: "createdOn",
	cellStyle: {
		textAlign: 'center'
	}
}];

var gridOptionsRequi = {
	columnDefs: columnDefsRequi,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectId
	//onSelectionChanged: onSelectionChangeRequi

};

function rowSelectId() {
	var selectedNodes = gridOptionsRequi.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	const skillsReqs = selectedData.map(node => node.skillsReq);
	const skillsData = JSON.stringify(skillsReqs[0]);
	var selectedRows = gridOptionsRequi.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		$("#requisitionId").val(reqId);
		getRequisitionRounds(reqId);
		/*skillsModal(skillsData, reqId);*/
		viewJobDetails(reqId);
	} else {
		$("#requisitionId").val("");
	}
};

function getRequisitionList() {
	agGrid.simpleHttpRequest({
		url: "review-hiring-requisition-list",
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);

		var allData = jsonData.requisitionData;

		if (allData != null) {
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			$('#openReqs').html(len);

			gridOptionsRequi.api.setRowData(allData);
			if (allData && allData.length > 0) {
				gridOptionsRequi.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});

			}
		} else {
			$('#totalReq').find('span').html("0");
			$('#openReqs').html("0");
			gridOptionsRequi.api.setRowData();
		}


	})
}

/*function skillsModal(data, reqId) {
	let dataRetrieved = JSON.parse(decodeURIComponent(data));
    
	$('#skillSetModal').modal('show');

	$('#skillReqId').html(reqId);

	let skillsTableBody = document.getElementById('skillsTableBody');

	skillsTableBody.innerHTML = '';

	dataRetrieved.forEach((skill) => {
		const row = document.createElement('tr');

		row.innerHTML = `
			<td>${skill.skillName}</td>
			<td>${skill.skillExp}</td>
			<td>${skill.skillRatings}</td>
		`;

		// Append the row to the table body
		skillsTableBody.appendChild(row);
	});
}*/

function fetchEmployeeList() {
	return agGrid.simpleHttpRequest({
		url: "review-hiring-get-employee-list"
	}).then(function(response) {
		if (response.code === "Success") {
			const employeeList = JSON.parse(response.body[0]);

			return employeeList;
		} else {
			console.log("Failed to fetch data");
			return [];
		}
	});
}

$(document).ready(function() {
	const $addInterviewRoundBtn = $('#addInterviewRound');
	const $timelineContainer = $('#timelineContainer');
	let roundCounter = 0;
	// Update the "Add Interview Round" button handler
	$addInterviewRoundBtn.on('click', function() {
		$('#error-msg').empty();
		$('#saveRoundsData').attr('disabled', false);
		fetchEmployeeList().then(function(employeeList) {
			// Create a new form dynamically
			const $inlineForm = $('<div>', { class: 'inline-form' }).html(
				`<input type="text" id="roundTitle" placeholder="Enter Round Title" required class="form-control mb-2">
	            <input type="hidden" id="roundId"/>
	            <label><strong>Interviewer(s)</strong></label>
	            <select id="interviewerSelect" multiple class="form-control mb-2 chosen-select" required>
	                <option value="" disabled>Select Interviewers</option>
	                ${employeeList.map(interviewer =>
					`<option value="${interviewer.empId}" data-email="${interviewer.empMail || ''}">${interviewer.empName}</option>`
				).join('')}
	            </select>
	            <textarea id="roundDescription" rows="2" placeholder="Enter Round Description" class="form-control mb-2"></textarea>
	            <button class="btn btn-success" id="saveRoundBtn">Add Round</button>
	            <input type="hidden" id="selectedInterviewersIds" name="selectedInterviewersIds">` // Hidden input for storing selected IDs
			);

			$timelineContainer.append($inlineForm);
			$inlineForm.show(); // Show the form

			// Apply Chosen to the select element
			$inlineForm.find('#interviewerSelect').chosen();

			// Handle save round button click
			$inlineForm.find('#saveRoundBtn').on('click', function() {
				const title = $('#roundTitle').val().trim();
				const description = $('#roundDescription').val().trim();
				const selectedInterviewers = $('#interviewerSelect').find('option:selected').map(function() {
					return {
						name: $(this).text(),
						email: $(this).data('email'),
						id: $(this).val() // Get the ID for the selected interviewer
					};
				}).get();

				if (!title) {
					alert('Please enter a title for the round.');
					return;
				}

				const selectedInterviewersIds = selectedInterviewers.map(interviewer => interviewer.id).join(',');

				$('#selectedInterviewersIds').val(selectedInterviewersIds); // Set the hidden input

				roundCounter++; // Increment round counter

				// Create a new timeline item with selected interviewers

				console.log("selectedInterviewers::", selectedInterviewers);
				console.log("selectedInterviewersIds::", selectedInterviewersIds);
				const $timelineItem = createTimelineItem(roundCounter, title, description, selectedInterviewers, selectedInterviewersIds, null);

				// Append the new round to the timeline
				$timelineContainer.append($timelineItem);

				// Clear the form and hide it
				$('#roundTitle').val('');
				$('#roundDescription').val('');
				$inlineForm.remove();

				// Add event listeners for edit and delete buttons
				addEventListeners($timelineItem);
			});
		});
	});

});

// Function to create a new timeline item
function createTimelineItem(roundNumber, title, description, interviewers, selectedInterviewersIds, roundId) {
	console.log()
	const interviewerNames = interviewers.map(interviewer => {
		return `<span class="interviewer-box" value="${interviewer.id}" data-email="${interviewer.email}">
            ${interviewer.name}
        </span>`;
	}).join(' ');

	const $timelineItem = $('<div>', { class: 'timeline-item', 'data-round-number': roundNumber, value: roundId === null ? 'null' : roundId }).html(`
        <div class="timeline-content">
            <input type="hidden" id="roundId" value="${roundId}"/>
            <h2>Round ${roundNumber}: <span class="round-title">${title}</span></h2>
            <p class="round-description">${description || 'No description provided.'}</p>
            <input type="hidden" id="interviewersIds" value="${selectedInterviewersIds}">
            <p><strong>Interviewers:</strong>
                <div class="interviewer-names-container" >
                    ${interviewerNames}
                </div>
            </p>
            <div class="action-icons">
                <i class="bi bi-pencil-square edit-round" title="Edit Round"></i>
                <i class="bi bi-trash delete-round" title="Delete Round"></i>
            </div>
        </div>
    `);

	return $timelineItem;
}

// Function to add event listeners for edit and delete actions
function addEventListeners($item) {
	// Edit functionality
	$item.find('.edit-round').on('click', function() {
		const title = $item.find('.round-title').text();
		const description = $item.find('.round-description').text();
		const roundNumber = parseInt($item.data('round-number'), 10);
		const interviewerNamesText = $item.find('.interviewer-names').text().trim();
		const selectedInterviewers = interviewerNamesText.split(',').map(name => ({
			name: name.trim(),
			email: '', // You can modify this to store emails if needed
			id: '' // You can retrieve IDs if stored in your backend or hidden fields
		}));

		// Populate the form with the existing round data
		const $inlineForm = $('<div>', { class: 'inline-form' }).html(`
            <input type="text" id="roundTitle" value="${title}" required class="form-control mb-2">
            <label><strong>Interviewer(s)</strong></label>
            <select id="interviewerSelect" multiple class="form-control mb-2 chosen-select" required>
                <option value="" disabled>Select Interviewers</option>
                ${employeeList.map(interviewer =>
			`<option value="${interviewer.empId}" ${selectedInterviewers.some(i => i.name === interviewer.empName) ? 'selected' : ''} data-email="${interviewer.empMail || ''}">${interviewer.empName}</option>`
		).join('')}
            </select>
            <textarea id="roundDescription" rows="2" class="form-control mb-2">${description === 'No description provided.' ? '' : description}</textarea>
            <button class="btn btn-success" id="saveRoundBtn">Save Round</button>
            <input type="hidden" id="selectedInterviewersIds" name="selectedInterviewersIds">` // Hidden input for storing selected IDs
		);

		$timelineContainer.append($inlineForm);
		$inlineForm.show();

		// Apply Chosen to the select element
		$inlineForm.find('#interviewerSelect').chosen();

		// Remove the original item
		$item.remove();

		// Handle save for the edited round
		$inlineForm.find('#saveRoundBtn').on('click', function() {
			const newTitle = $('#roundTitle').val().trim();
			const newDescription = $('#roundDescription').val().trim();
			const updatedInterviewers = $('#interviewerSelect').find('option:selected').map(function() {
				return {
					name: $(this).text(),
					email: $(this).data('email'),
					id: $(this).val() // Get the ID for the selected interviewer
				};
			}).get();

			if (!newTitle) {
				alert('Please enter a title for the round.');
				return;
			}

			// Store selected interviewer IDs in the hidden field (comma-separated)
			const selectedInterviewersIds = updatedInterviewers.map(interviewer => interviewer.id).join(',');

			$('#selectedInterviewersIds').val(selectedInterviewersIds); // Set the hidden input

			// Create the updated timeline item
			const $updatedTimelineItem = createTimelineItem(roundNumber, newTitle, newDescription, updatedInterviewers, null);

			// Insert the updated item in the correct position
			const $timelineItems = $('.timeline-item');
			let inserted = false;

			$timelineItems.each(function(i, item) {
				const currentRoundNumber = parseInt($(item).data('round-number'), 10);
				if (roundNumber < currentRoundNumber) {
					$updatedTimelineItem.insertBefore($(item));
					inserted = true;
					return false; // Break out of loop
				}
			});

			// If not inserted, append to the end
			if (!inserted) {
				$timelineContainer.append($updatedTimelineItem);
			}

			// Clear the form and hide it
			$('#roundTitle').val('');
			$('#roundDescription').val('');
			$inlineForm.remove();

			// Add event listeners for edit and delete buttons
			addEventListeners($updatedTimelineItem);
		});
	});

	// Delete functionality
	$item.find('.delete-round').on('click', function() {
		if (confirm('Are you sure you want to delete this round?')) {
			$item.remove();
			updateRoundNumbers();
		}
	});
}

// Function to update round numbers after deletion or editing
function updateRoundNumbers() {
	const $timelineItems = $('.timeline-item');
	let count = 0;
	$timelineItems.each(function() {
		count++;
		$(this).data('round-number', count);
		const $roundTitle = $(this).find('.round-title');
		$roundTitle.text($roundTitle.text().replace(/Round \d+:/, `Round ${count}:`));
	});
	roundCounter = count; // Update the global counter
}



function saveRoundsData() {

	const timelineItems = [];

	document.querySelectorAll('.timeline-item').forEach(item => {
		const value = item.getAttribute('value'); // Check the 'value' attribute
		if (value === 'null') { // Only process items where 'value' is 'null'
			const roundId = item.querySelector('#roundId').value;
			const roundNumber = item.getAttribute('data-round-number');
			const roundTitle = item.querySelector('.round-title').innerText;
			const roundDescription = item.querySelector('.round-description').innerText;
			const interviewerIds = item.querySelector('#interviewersIds').value.split(',');
			const interviewerNames = Array.from(item.querySelectorAll('.interviewer-box'))
				.map(interviewer => interviewer.innerText.trim());

			const interviewerEmails = Array.from(item.querySelectorAll('.interviewer-box'))
				.map(interviewer => interviewer.getAttribute('data-email'));

			const interviewers = interviewerIds.map((id, index) => ({
				id: id.trim(),
				name: interviewerNames[index].trim(),
				email: interviewerEmails[index] ? interviewerEmails[index].trim() : ''
			}));

			timelineItems.push({
				roundNumber: roundNumber,
				requisitionId: $("#requisitionId").val(),
				roundTitle: roundTitle,
				roundDescription: roundDescription,
				interviewers: interviewers,
				roundId: roundId
			});
		}
	});

	if (timelineItems.length === 0) {
		alert("No rounds to save.");
		return;
	}

	let roundDataObj = {
		rounds: timelineItems
	};

	console.log(roundDataObj);

	$.ajax({
		url: 'review-hiring-save-interview-rounds',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(roundDataObj),
		success: function(response) {
			if (response.code === "Success") {
				const requisitionId = $("#requisitionId").val();
				getRequisitionRounds(requisitionId);
				$('#saveRoundsData').attr('disabled', true);
				nextBtnFunction('requisitionDetailsTab');
				toastr.success('Rounds Saved Successfully');
			} else {
				alert("Error");
			}
		},
		error: function(xhr, status, error) {
			alert('Failed to save round: ' + error);
		}
	});
}


function getRequisitionRounds(requisitionId) {
	const candId = "";
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-rounds-list?requisitionId=" + requisitionId + "&candId=" + candId,
	}).then(function(response) {
		if (response.code === "Success") {
			const roundList = JSON.parse(response.body[0]);

			$('#timelineContainer').html("");

			if (!roundList || roundList.length === 0 || roundList[0] === null) {
				const messageDiv = $('<div class="container" id="error-msg" >' +
					'<div class="d-flex justify-content-center align-items-center" style="height: 341px;">' +
					'<h5 class="text-muted not-found">No rounds found for this requisition</h5>' +
					'</div>' +
					'</div>');

				$('#saveRoundsData').attr('disabled', true);
				$('#timelineContainer').append(messageDiv);
			} else {
				$('#saveRoundsData').attr('disabled', true);
				// Process the rounds data and add it to the timeline
				roundList.forEach((round) => {
					const interviewers = JSON.parse(round.interviewers);
					const interviewerIds = interviewers.map(interviewer => interviewer.id).join(',');

					const $timelineItem = createTimelineItem(
						round.roundOrder,
						round.title,
						round.description,
						interviewers,
						interviewerIds,
						round.roundId
					);

					const $timelineContainer = $('#timelineContainer');
					$timelineContainer.append($timelineItem);
					$('#roundTitle').val('');
					$('#roundDescription').val('');
					addEventListeners($timelineItem);
				});
			}
		} else {
			console.log("Failed to fetch data");
		}
	});
}

function viewJobDetails(reqid) {
	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-edit?id=" + reqid,
		async: false,
		success: function(response) {
			if (response.message === "Success" && response.body.length > 0) {
				const job = response.body[0];

				// Update job details
				$("#jobId").val(reqid);
				$("#job-title").text(job.jobTitle || "N/A");
				$("#jd-positionSummary").text(job.positionSummary || "N/A");
				$("#jd-positionResponsibility").text(job.positionResponsibility || "N/A");
				$("#jd-designation").text(job.designation || "N/A");
				$("#jd-band").text(job.band || "N/A");
				$("#jd-hiring-manager").text(job.hiringManager || "N/A");
				$("#jd-join-date").text(job.joinDate || "N/A");
				$("#jd-apply-date").text(job.applyStartDate || "N/A");
				$("#jd-end-date").text(job.applyEndDate || "N/A");
				$("#jd-positions").text(job.noPosition || "N/A");
				$("#jd-experience").text(`${job.minExp || "N/A"} - ${job.maxExp || "N/A"} years`);
				$("#jd-salary").text(`₹${job.minSalary || "N/A"} - ₹${job.maxSalary || "N/A"}`);
				$("#jd-status").text(job.activityStatus === "1" ? "Active" : "Inactive");
				$("#jd-apprv-status").text(job.approveStatus || "Pending");
				// Add skills dynamically
				$.ajax({
					type: "GET",
					url: "view-new-requi-mstr-edit-skill?reqId=" + reqid,
					async: false,
					success: function(skillResponse) {
						if (skillResponse.code === "success") {
							const skillsData = JSON.parse(skillResponse.body);
							const skillsContainer = $("#jobDetailsSec .jd-skills");
							skillsContainer.empty();

							if (skillsData.length > 0) {
								/*<i class="fas fa-check-circle text-success me-3 bg-web-clr-txt"></i>*/
								skillsData.forEach(skill => {
									skillsContainer.append(`
				                        <div class="col-md-4 mb-3">
				                            <div class="skill-card border rounded p-2 d-flex align-items-center">
				                               
				                                <div>
				                                    <span class="fw-bold">${skill.skillName}</span>
				                                    <small class="d-block text-muted">Experience: ${skill.skillValue} years</small>
				                                </div>
				                            </div>
				                        </div>
				                    `);
								});
							} else {
								skillsContainer.append(`
				                    <div class="col-12">
				                        <p class="text-muted">No skills specified</p>
				                    </div>
				                `);
							}
						} else {
							console.error("Failed to fetch skills data.");
						}
					},
					error: function(error) {
						console.error("Error fetching skills data:", error);
					}
				});


				// Footer details
				$("#created-on").text(job.createdOn || "N/A");
				$("#approved-by").text(job.approvedBy || "N/A");
			} else {
				console.error("Failed to fetch job details or no data found.");
			}
		},
		error: function(error) {
			console.error("Error fetching job details:", error);
		}
	});
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

