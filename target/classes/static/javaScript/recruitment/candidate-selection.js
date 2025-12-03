$(document).ready(function() {
	var gridDivSelected1 = document.querySelector('#myGrid');
	new agGrid.Grid(gridDivSelected1, gridOptions);

	var gridDivSelected2 = document.querySelector('#myGridSelected');
	new agGrid.Grid(gridDivSelected2, gridOptionsSelected);

	var gridDivSelected3 = document.querySelector('#myGridHold');
	new agGrid.Grid(gridDivSelected3, gridOptionsHold);

	var gridDivSelected4 = document.querySelector('#myGridDeclined');
	new agGrid.Grid(gridDivSelected4, gridOptionsOfferDeclined);

	var gridDivSelected = document.querySelector('#myGridAccepted');
	new agGrid.Grid(gridDivSelected, gridOptionsOfferAccepted);

	var gridDivSelected = document.querySelector('#myGridRejected');
	new agGrid.Grid(gridDivSelected, gridOptionsOfferRejected);

	gridOptions.api.setRowData([]);
	gridOptionsSelected.api.setRowData([]);
	gridOptionsHold.api.setRowData([]);
	gridOptionsOfferDeclined.api.setRowData([]);
	gridOptionsOfferAccepted.api.setRowData([]);
	gridOptionsOfferRejected.api.setRowData([]);
	getRequisitionListForCandidate();
	applyCandidateFilters();

	$('#secondCandidate').chosen();

	$('#secondCandidate').on('change', function() {
		const selectedCandidates = $(this).val();
		$('#candidateHiddenId').val(selectedCandidates ? selectedCandidates.join(',') : '');
	});

	$("#generateOffer").attr("disabled", true);
	$("#offerAccepted").attr("disabled", true);
	$("#offerDeclined").attr("disabled", true);
	$("#shareOfferLetter").attr("disabled", true);

});

$(document).ready(function() {
	document.getElementById('goBtn').addEventListener('click', function() {
		quickFilterGrid(gridOptions);
	});

	document.getElementById('resetBtn').addEventListener('click', function() {
		document.getElementById('quickFilter').value = '';
		gridOptions.api.setQuickFilter('');
		$(".loader").show();
		selectFirstRow(gridOptions);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function(event) {
		if (event.key === 'Enter' || event.key === 'Backspace') {
			quickFilterGrid(gridOptions);
		}
	});
});


var columnDefs = [{
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
	pinned: 'left',
	hide:true,
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.requisitionId + '</a>';
	}
}, {
	headerName: "Title",
	field: "jobTitle",
	pinned: 'left',
	cellRenderer: function(params) {
		        const jobTitle = params.data.jobTitle;
		        
		        const name = params.value || '';
		        const createdOn = params.data.createdOn || ''; 

		        let createdDate;
		        if (createdOn) {
		            const [day, month, year] = createdOn.split('-').map(Number);
		            createdDate = new Date(year, month - 1, day); 
		        }

		        const today = new Date();
		        const timeDiff = createdDate ? today - createdDate : 0;
		        const daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); 

		        let badgeText = '';
		        let badgeStyle = '';
		       

		        if (daysDiff === 0) {
		            badgeText = 'New';
		            badgeStyle = `
		                background: rgba(52, 168, 83, 0.2);
		                color: #34a853;
		                font-size: 10px;
		                font-weight: 600;
		                padding: 2px 6px 0px 7px;
		                border-radius: 10px;
		                display: inline-flex;
		                align-items: center;
		                backdrop-filter: blur(2px);
		                white-space: nowrap;
		                line-height: 17px;
		            `;
		        } else if (daysDiff >= 1 && daysDiff <= 6) {
		            // 1 to 6 days ago: Orange badge
		            badgeText = daysDiff === 1 ? '1 day ago' : `${daysDiff} days ago`;
		            badgeStyle = `
		                background: rgba(255, 165, 0, 0.2);
		                color: #ff8c00;
		                font-size: 10px;
		                font-weight: 600;
		                padding: 2px 6px 0px 7px;
		                border-radius: 10px;
		                display: inline-flex;
		                align-items: center;
		                backdrop-filter: blur(2px);
		                white-space: nowrap;
		                line-height: 17px;
		            `;
		        } else if (daysDiff >= 7 && daysDiff <= 20) {
		            // 7 to 20 days ago: Orange badge, show weeks for 7 or 14 days, otherwise days
		            if (daysDiff === 7) {
		                badgeText = '1 week ago';
		            } else if (daysDiff === 14) {
		                badgeText = '2 weeks ago';
		            } else {
		                badgeText = `${daysDiff} days ago`;
		            }
		            badgeStyle = `
		                background: rgba(255, 165, 0, 0.2);
		                color: #ff8c00;
		                font-size: 10px;
		                font-weight: 600;
		                padding: 2px 6px 0px 7px;
		                border-radius: 10px;
		                display: inline-flex;
		                align-items: center;
		                backdrop-filter: blur(2px);
		                white-space: nowrap;
		                line-height: 17px;
		            `;
		        } else if (daysDiff >= 30) {
		            badgeText = `${daysDiff} days Old`;
		            badgeStyle = `
		                background: rgba(128, 128, 128, 0.2);
		                color: #666;
		                font-size: 10px;
		                font-weight: 600;
		                padding: 2px 6px 0px 7px;
		                border-radius: 10px;
		                display: inline-flex;
		                align-items: center;
		                backdrop-filter: blur(2px);
		                white-space: nowrap;
		                line-height: 17px;
		            `;
		        } else {
		            badgeText = `${daysDiff} days ago`;
		            badgeStyle = `
		                background: rgba(255, 165, 0, 0.2);
		                color: #ff8c00;
		                font-size: 10px;
		                font-weight: 600;
		                padding: 2px 6px 0px 7px;
		                border-radius: 10px;
		                display: inline-flex;
		                align-items: center;
		                backdrop-filter: blur(2px);
		                white-space: nowrap;
		                line-height: 17px;
		            `;
		        }

		        // Combine job title, days badge, and open badge (if applicable)
		        return `
		            <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
		                <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
		                ${badgeText ? `<span style="${badgeStyle}">${badgeText}</span>` : ''}
		                
		            </div>
		        `;
		    }
}, {
	headerName: "Openings",
	field: "noOfPositions",
	type: "rightAligned",
	width: 100,
	cellStyle: {
		textAlign: 'right'
	}
}, {
	headerName: "Exprience",
	field: "minExp",
	width: 100,
	cellStyle: {
		textAlign: 'right'
	},
	cellRenderer: function(params) {
		return params.data.minExp + " - " + params.data.maxExp + " Years";
	}
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
}, /*{
	headerName: "Total Applicants",
	field: "applicants",
	type: "rightAligned",
},*/ {
	headerName: "Created Date",
	field: "createdOn",
	cellStyle: {
		textAlign: 'center'
	}
}];

var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectId

};

function rowSelectId() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		getRequisitionAllCandidatesList(reqId);
		getCandidateOnRequisition(reqId);
		getForwardedCandidatesList(reqId);
		getSelectedCandidateList(reqId);
		getHoldCandidateList(reqId);
		getOfferDeclinedCandidateList(reqId);
		getOfferAcceptedCandidateList(reqId);
		getRejectedCandidates(reqId);


		$("#jobRole").val(reqId);
	} else {
	}
};

function getRequisitionListForCandidate() {
    agGrid.simpleHttpRequest({
        url: "review-hiring-requisition-candidate-selection-list",
    }).then(function(data) {
        var jsonData = JSON.parse(data.body);
        var allData = jsonData.requisitionData;

        allData.sort((a, b) => parseDate(b.createdOn) - parseDate(a.createdOn));


        if (allData && allData.length > 0) {
            gridOptions.api.setRowData(allData);
            gridOptions.api.forEachNode(function(node) {
                if (node.rowIndex === 0) {
                    node.setSelected(true);
                }
            });
        } else {
            gridOptions.api.setRowData([]);
        }
    });
}

function parseDate(dateStr) {
       const [day, month, year] = dateStr.split("-");
       return new Date(`${year}-${month}-${day}`);
   }

function getRequisitionAllCandidatesList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-requisition-candidate-list?reqId=" + reqId,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body); // Parse once
		var allData = jsonData[0].candidateData;

		let timelineContainer = document.getElementById("candidateRoundsTimeline");
		timelineContainer.innerHTML = "";

		// Check if data is empty or null
		if (!allData || allData.length === 0) {
			$("#error-msg-candidate").removeClass("d-none");
			$("#candidateRoundsTimeline").addClass("d-none");
			return;
		}

		$("#error-msg-candidate").addClass("d-none");
		$("#candidateRoundsTimeline").removeClass("d-none");

		let roundsMap = {};
		allData.forEach(candidate => {
			if (!roundsMap[candidate.roundTitle]) {
				roundsMap[candidate.roundTitle] = [];
			}
			roundsMap[candidate.roundTitle].push(candidate);
		});

		Object.keys(roundsMap).forEach(roundTitle => {
			let roundCandidates = roundsMap[roundTitle];
			let candidateCount = roundCandidates.length;

			let timelineBlock = `
                <div class="timeline-middle">
                    <div class="timeline-circle"></div>
                </div>
                <div class="timeline-contents">
                    <div class="col-12 col-lg-12">
                        <div class="kanban-column">
                            <h6>${roundTitle} <span class="badge bg-dark">${candidateCount}</span></h6>
                            <div class="d-flex flex-wrap gap-3 tc-r">
                                ${roundCandidates.map(candidate => {
				let badgeClass = getStatusBadgeClass(candidate.roundStatus);
				let imageUrl = candidate.candidateImage && candidate.candidateImage.trim() !== ""
					? candidate.candidateImage
					: "https://www.w3schools.com/w3images/avatar2.png"; // Default static image

				return `
                                        <div class="candidate-card d-flex align-items-center p-2">
                                            <img src="${imageUrl}"
                                                 alt="${candidate.candidateName}" class="candidate-img">
                                            <div class="ms-2">
                                                <div class="candidate-name">${candidate.candidateName} </div>
                                                <div class="candidate-id">
                                                    <span class="badge avgRating">${candidate.averageRatings}</span>
                                                    <span class="badge ${badgeClass}">${candidate.roundStatus}</span>
                                                </div>
                                            </div>
                                        </div>
                                    `;
			}).join("")}
                            </div>
                        </div>
                    </div>
                </div>
            `;

			timelineContainer.innerHTML += timelineBlock;
		});
	});
}


/*function getRequisitionAllCandidatesListp(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-requisition-candidate-list?reqId=" + reqId,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body); // Parse once
		var allData = jsonData[0].candidateData;

		let roundsMap = {};
		allData.forEach(candidate => {
			if (!roundsMap[candidate.roundTitle]) {
				roundsMap[candidate.roundTitle] = [];
			}
			roundsMap[candidate.roundTitle].push(candidate);
		});

		let timelineContainer = document.getElementById("candidateRoundsTimeline");
		timelineContainer.innerHTML = ""; 

		Object.keys(roundsMap).forEach(roundTitle => {
			let roundCandidates = roundsMap[roundTitle];
			let candidateCount = roundCandidates.length;

			let timelineBlock = `
				<div class="timeline-middle">
					<div class="timeline-circle"></div>
				</div>
				<div class="timeline-contents">
					<div class="col-12 col-lg-12">
						<div class="kanban-column">
							<h6>${roundTitle} <span class="badge bg-dark">${candidateCount}</span></h6>
							<div class="d-flex flex-wrap gap-3 tc-r">
								${roundCandidates.map(candidate => {
									let badgeClass = getStatusBadgeClass(candidate.roundStatus);
									return `
										<div class="candidate-card d-flex align-items-center p-2">
											<img src="https://img.freepik.com/premium-photo/actor-digital-avatar-generative-ai_934475-9309.jpg"
												 alt="${candidate.candidateId}" class="candidate-img">
											<div class="ms-2">
												<div class="candidate-name">${candidate.candidateName} </div>
												<div class="candidate-id">
												<span class="badge avgRating">${candidate.averageRatings}</span>
												<span class="badge ${badgeClass}">${candidate.roundStatus}</span>
												
												</div>
											</div>
										</div>
									`;
								}).join("")}
							</div>
						</div>
					</div>
				</div>
			`;

			timelineContainer.innerHTML += timelineBlock;
		});
	});
}
*/
function getStatusBadgeClass(status) {
	switch (status.toLowerCase()) {
		case "pending":
			return "bg-secondary";
		case "shortlisted":
			return "bg-success";
		case "hold":
			return "bg-warning  text-dark";
		case "rejected":
			return "bg-danger";
		default:
			return "bg-dark";
	}
}


function shortListCand(data) {

	$(".loader").show();
	var reqId = $("#jobRole").val();
	$.ajax({
		url: 'review-hiring-shortlist-candidate?reqId=' + reqId,
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				getCandidateOnRequisition(reqId);
				getForwardedCandidatesList(reqId);
				resetCandidateFilters();
				$(".loader").hide();
			} else {
				toastr.error(response.message);
			}
		},
		error: function(xhr, status, error) {
			alert('Failed to save round: ' + error);
		}
	});

}

function shortlistCandidateForSelection() {
	let selectedCandidates = [];
	let candidateIds = [];

	$(".candidate-header.selected").each(function() {
		let candidateId = $(this).data("candidate-id");
		let overallAverage = $(this).closest("table").find("tfoot th.candi-score").eq($(this).index() - 1).text();

		selectedCandidates.push({
			candidateId: candidateId,
			requisitionId: $("#jobRole").val(),
			overallAverage: parseFloat(overallAverage)
		});
		candidateIds.push(candidateId);
	});

	console.log(selectedCandidates);
	let candidateObj = {
		candidateData: selectedCandidates,
		candidateIds: candidateIds
	};

	shortListCand(candidateObj);
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}


function getCandidateOnRequisition(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-candidates-list?requisitionId=" + reqId,
	}).then(function(response) {
		var secondSelectElement = document.getElementById("secondCandidate");

		secondSelectElement.innerHTML = '';

		/*firstSelectElement.innerHTML = '<option value="" selected disabled>Select Candidate</option>';*/
		/*secondSelectElement.innerHTML = '<option value="" selected disabled>Select Candidate</option>';*/

		response.forEach(function(candidate) {
			var firstOption = document.createElement("option");
			firstOption.value = candidate.key;
			firstOption.textContent = candidate.name;

			/*if (candidate.key == candidateId) {
				firstOption.selected = true;
			}
*/
			var secondOption = document.createElement("option");
			secondOption.value = candidate.key;
			secondOption.textContent = candidate.name;

			/*if (candidate.key == candidateId) {
				secondOption.disabled = true;
			}*/
			secondSelectElement.appendChild(secondOption);
		});

		$('#secondCandidate').trigger('chosen:updated');
	});
}

function applyCandidateFilters() {
	$(".loader").show();
	const reqId = $("#jobRole").val();
	const firstCandidate = $("#firstCandidate").val();
	let candidateHiddenId = $("#candidateHiddenId").val();
	const ratingFilter = $("#jobRatings").val();
	if (!candidateHiddenId) {
		resetCandidateFilters();
		return;
	}
	getFeedbackData(reqId, candidateHiddenId, ratingFilter);
}


function resetCandidateFilters() {
	const reqId = $("#jobRole").val();
	const firstCandidate = $("#firstCandidate").val();
	$("#secondCandidate").val("");
	$("#candidateHiddenId").val("");
	$("#jobRatings").val("");
	$('#secondCandidate').trigger('chosen:updated');
	$(".loader").hide();
	getFeedbackData(reqId, firstCandidate, "page-load", '');
}

function getFeedbackData(reqId, candidateId, ratingFilter) {
	const url = `review-hiring-feedback-details?requiId=${reqId}&candId=${candidateId}`;

	agGrid.simpleHttpRequest({
		url: url
	}).then(function(data) {
		if (data.code == "success") {
			const averages = processComparisonData(data);
			populateComparisonTable(averages, ratingFilter);

			$(".loader").hide();
		} else {
			console.warn("Something went wrong!");
		}
	});
}

function processComparisonData(feedbackData) {

	const candidatesData = {};
	const feedbackArray = JSON.parse(feedbackData.body[0]);
	const errmsg = document.querySelector(".no-comparision-data-msg");
	errmsg.innerHTML = "";
	if (feedbackArray == "null" || feedbackArray == null) {
		errmsg.innerHTML = `<div class="filter-container" id="error-msg">
				        <div class="d-flex justify-content-center align-items-center">
				            <p class="text-muted not-found" style="height:3px;">No Candidate Selected<p>
				        </div>
				    </div>`;
		$("#shortlistBtn").attr("disabled", true);
	}

	else {

		$("#shortlistBtn").attr("disabled", false);
		feedbackArray.forEach(feedback => {
			const candidateId = feedback.candidateId;
			const skillsRatings = feedback.skillsRatings;

			if (!candidatesData[candidateId]) {
				candidatesData[candidateId] = {};
			}

			skillsRatings.forEach(skill => {
				const skillName = skill.skillName;
				const rating = parseFloat(skill.rating);

				if (!candidatesData[candidateId][skillName]) {
					candidatesData[candidateId][skillName] = {
						total: 0,
						count: 0,
					};
				}

				candidatesData[candidateId][skillName].total += rating;
				candidatesData[candidateId][skillName].count += 1;
			});
		});

		// Calculate averages
		const averages = Object.keys(candidatesData).map(candidateId => {
			const skills = candidatesData[candidateId];
			const skillAverages = {};

			let overallTotal = 0;
			let overallCount = 0;

			Object.keys(skills).forEach(skillName => {
				const skillData = skills[skillName];
				const average = skillData.total / skillData.count;
				skillAverages[skillName] = average.toFixed(2);

				overallTotal += skillData.total;
				overallCount += skillData.count;
			});

			const overallAverage = (overallTotal / overallCount).toFixed(2);

			return {
				candidateId,
				skillAverages,
				overallAverage: parseFloat(overallAverage), // Ensure numeric sorting
				candidateName: feedbackArray.find(fb => fb.candidateId === candidateId).candidateName,
			};
		});

		// Sort candidates by overall average in descending order
		averages.sort((a, b) => b.overallAverage - a.overallAverage);

		return averages;
	}
}

function populateComparisonTable(averages, ratingFilter) {

	const tableBody = document.querySelector("#dataRows");
	const tableFoot = document.querySelector(".comparison-table tfoot");
	const tableHeader = document.querySelector("#headerRow");

	// Clear existing table rows and headers
	tableBody.innerHTML = "";
	tableFoot.innerHTML = "";
	tableHeader.innerHTML = ""; // Clear header for dynamic update

	// Filter candidates based on ratingFilter (show only candidates with rating >= ratingFilter)
	const filteredAverages = averages.filter(candidate => {
		return candidate.overallAverage >= ratingFilter || !ratingFilter;  // Show candidates with rating >= ratingFilter
	});

	if (filteredAverages.length === 0) {
		// If no candidates match the filter, display a "No candidates found" message
		const noCandidatesRow = document.createElement("tr");
		const noCandidatesCell = document.createElement("td");
		noCandidatesCell.colSpan = 100;  // Span across all columns
		noCandidatesCell.textContent = `No candidates found with a rating of ${ratingFilter} or higher.`;
		noCandidatesCell.style.textAlign = "center";
		noCandidatesCell.style.fontSize = "16px";
		noCandidatesCell.style.color = "#606060";
		noCandidatesCell.style.fontWeight = "600";
		noCandidatesRow.appendChild(noCandidatesCell);
		tableBody.appendChild(noCandidatesRow);

		return; // Exit the function early if no candidates are found
	}

	// Extract unique skill names
	const skillNames = new Set();
	filteredAverages.forEach(candidate => {
		Object.keys(candidate.skillAverages).forEach(skill => skillNames.add(skill));
	});

	// Create dynamic header
	const headerRow = document.createElement("tr");
	const skillHeaderCell = document.createElement("th");
	skillHeaderCell.classList.add("fixed");
	skillHeaderCell.textContent = "Skills";
	headerRow.appendChild(skillHeaderCell);

	filteredAverages.forEach(candidate => {
		const candidateHeaderCell = document.createElement("th");
		candidateHeaderCell.classList.add("candidate-header");
		candidateHeaderCell.setAttribute("data-candidate-id", candidate.candidateId);

		const checkbox = document.createElement("input");
		checkbox.type = "checkbox";
		checkbox.classList.add("form-check-input");

		checkbox.addEventListener("change", (event) => {
			if (event.target.checked) {
				candidateHeaderCell.classList.add("selected");
				candidateHeaderCell.style.backgroundColor = "rgb(89 0 120)";
			} else {
				candidateHeaderCell.classList.remove("selected");
				candidateHeaderCell.style.backgroundColor = "";
			}
		});

		const label = document.createElement("label");
		label.textContent = `${candidate.candidateName}`;
		label.style.marginLeft = "8px";

		candidateHeaderCell.appendChild(checkbox);
		candidateHeaderCell.appendChild(label);

		headerRow.appendChild(candidateHeaderCell);
	});

	tableHeader.appendChild(headerRow);

	// Populate skill rows
	skillNames.forEach(skillName => {
		const row = document.createElement("tr");
		const skillCell = document.createElement("td");
		skillCell.classList.add("fixed");
		skillCell.textContent = skillName;

		row.appendChild(skillCell);

		filteredAverages.forEach(candidate => {
			const scoreCell = document.createElement("td");
			scoreCell.classList.add("candi-score");
			scoreCell.textContent = candidate.skillAverages[skillName] || "N/A";
			row.appendChild(scoreCell);
		});

		tableBody.appendChild(row);
	});

	// Populate overall average row
	const overallRow = document.createElement("tr");
	const overallLabelCell = document.createElement("th");
	overallLabelCell.textContent = "Overall Average";

	overallRow.appendChild(overallLabelCell);

	filteredAverages.forEach(candidate => {
		const overallCell = document.createElement("th");
		overallCell.classList.add("candi-score");
		overallCell.textContent = candidate.overallAverage;
		overallRow.appendChild(overallCell);
	});

	tableFoot.innerHTML = "";
	tableFoot.appendChild(overallRow);
}

function getForwardedCandidatesList(reqId) {
    agGrid.simpleHttpRequest({
        url: "review-hiring-forwarded-candidate-list?reqId=" + reqId,
    }).then(function(data) {
        var jsonData = JSON.parse(data.body[0]);

        var forwardedContainer = document.getElementById("forwardedCandidateList");
        var rejectedContainer = document.getElementById("rejectedCandidateList");
        var selectedVendorsContainer = document.getElementById("selectedVendorsContainer");

        // Clear previous content
        forwardedContainer.innerHTML = "";
        rejectedContainer.innerHTML = "";
        selectedVendorsContainer.innerHTML = "";

        // Function to create the candidate card for forwarded/rejected list
        function createCandidateCard(candidate, status) {
            let imageUrl = candidate.candidateImage ? candidate.candidateImage : "https://www.w3schools.com/w3images/avatar2.png";
            let badgeClass = "";

            if (status === "Forwarded") {
                badgeClass = "bg-warning text-dark";
            } else if (status === "Rejected") {
                badgeClass = "bg-danger";
            } else if (status === "Hired") {
                badgeClass = "bg-success";
            } else if (status === "Hold") {
                badgeClass = "bg-info text-dark";
            }

            return `
                <div class="candidate-forward-card d-flex align-items-center p-2">
                    <img src="${imageUrl}" alt="${candidate.candidateId}" class="candidate-img">
                    <div class="ms-2">
                        <div class="candidate-name" value="${candidate.candidateId}">${candidate.candidateName}</div>
                        <div class="candidate-id">
                            <span class="badge candidate-badge-text ${badgeClass}">${status}</span>
                        </div>
                    </div>
                </div>
            `;
        }

        // Function to add forwarded candidates to the selected vendor container
        function addForwardedCandidateToVendors(candidate) {
            let imageUrl = candidate.candidateImage ? candidate.candidateImage : "https://www.w3schools.com/w3images/avatar2.png";
            selectedVendorsContainer.innerHTML += `
                <div class="selected-vendor-item">
                    <div class="d-flex justify-content-between align-items-center w-100">
                        <div class="d-flex gap-3">
                            <img src="${imageUrl}" alt="${candidate.candidateId}" class="candidate-img">
                            <div class="d-flex flex-column mt-1">
                                <div class="candidate-info">${candidate.candidateName} (${candidate.candidateId})</div>
                                <small class="d-flex gap-3 text-dark candidate-info-sts"> Forwarded By : ${candidate.forwardedBy} | Forwarded Date : ${candidate.forwardedOn}</small>
                            </div>
                        </div>
                        <div class="moderate-actions">
                            <span class="badge bg-secondary avgRating">${candidate.candidateAvg || 'N/A'}</span>
                            <select class="moderate-status">
                                <option selected value="" disabled>--:--</option>
                                <option value="selected">Shortlist</option>
                                <option value="hold">Hold</option>
                            </select>
                        </div>
                    </div>
                </div>
            `;
        }

        // Process forwarded candidates (show in both containers)
        if (jsonData.forwardedCandidates && jsonData.forwardedCandidates.length > 0) {
            jsonData.forwardedCandidates.forEach(candidate => {
                addForwardedCandidateToVendors(candidate);
                forwardedContainer.innerHTML += createCandidateCard(candidate, "Forwarded");
            });
            $("#moderatorRemarksDiv").removeClass("d-none");
        } else {
            selectedVendorsContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
                <p class="text-muted not-found">No candidates to moderate found</p>
            </div>`;
            $("#moderatorRemarksDiv").addClass("d-none");
        }

        // Process hold candidates (show in forwarded container only)
        if (jsonData.holdCandidates && jsonData.holdCandidates.length > 0) {
            jsonData.holdCandidates.forEach(candidate => {
                forwardedContainer.innerHTML += createCandidateCard(candidate, "Hold");
            });
        }

        // Process hired candidates (always show in forwarded container)
        if (jsonData.hiredCandidates && jsonData.hiredCandidates.length > 0) {
            jsonData.hiredCandidates.forEach(candidate => {
                forwardedContainer.innerHTML += createCandidateCard(candidate, "Hired");
            });
        }

        // If no candidates of any type, show empty state
        if (!jsonData.forwardedCandidates && !jsonData.holdCandidates && !jsonData.hiredCandidates) {
            forwardedContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
                <p class="text-muted not-found" style="height:3px;">No candidates found<p>
            </div>`;
        }

        // Process rejected candidates
        if (jsonData.rejectedCandidates && jsonData.rejectedCandidates.length > 0) {
            jsonData.rejectedCandidates.forEach(candidate => {
                rejectedContainer.innerHTML += createCandidateCard(candidate, "Rejected");
            });
        } else {
            rejectedContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
                <p class="text-muted not-found" style="height:3px;">No rejected candidates found<p>
            </div>`;
        }
    });
}
/*function getForwardedCandidatesList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-forwarded-candidate-list?reqId=" + reqId,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body[0]);

		var forwardedContainer = document.getElementById("forwardedCandidateList");
		var rejectedContainer = document.getElementById("rejectedCandidateList");
		var selectedVendorsContainer = document.getElementById("selectedVendorsContainer");

		// Clear previous content
		forwardedContainer.innerHTML = "";
		rejectedContainer.innerHTML = "";
		selectedVendorsContainer.innerHTML = "";

		// Function to create the candidate card for forwarded/rejected list
		function createCandidateCard(candidate, status) {
			let imageUrl = candidate.candidateImage ? candidate.candidateImage : "https://www.w3schools.com/w3images/avatar2.png";
			let badgeClass = "";

			if (status === "Forwarded") {
				badgeClass = "bg-warning text-dark";
			} else if (status === "Rejected") {
				badgeClass = "bg-danger";
			} else if (status === "Hired") {
				badgeClass = "bg-success";
			}

			return `
		        <div class="candidate-forward-card d-flex align-items-center p-2">
				<img src="${imageUrl}" alt="${candidate.candidateId}" class="candidate-img">
		            <div class="ms-2">
		                <div class="candidate-name" value="${candidate.candidateId}">${candidate.candidateName}</div>
		                <div class="candidate-id">
		                    <span class="badge candidate-badge-text ${badgeClass}">${status}</span>
		                </div>
		            </div>
		        </div>
		    `;
		}


		// Function to add forwarded candidates to the selected vendor container
		function addForwardedCandidateToVendors(candidate) {
			let imageUrl = candidate.candidateImage ? candidate.candidateImage : "https://www.w3schools.com/w3images/avatar2.png";
			selectedVendorsContainer.innerHTML += `
                <div class="selected-vendor-item">
                    <div class="d-flex justify-content-between align-items-center w-100">
					<div class="d-flex gap-3">
					<img src="${imageUrl}" alt="${candidate.candidateId}" class="candidate-img">
                        <div class="d-flex flex-column mt-1">
						<div class="candidate-info">${candidate.candidateName} (${candidate.candidateId})</div>
						<small class="d-flex gap-3 text-dark candidate-info-sts"> Forwarded By : ${candidate.forwardedBy} | Forwarded Date : ${candidate.forwardedOn}</small>
						</div>
						
						</div>
                        <div class="moderate-actions">
                            <span class="badge bg-secondary avgRating">${candidate.candidateAvg || 'N/A'}</span>
                            <select class="moderate-status">
                                <option selected value="" disabled>--:--</option>
                                <option value="selected">Shortlist</option>
                                <!-- <option value="re-interview">Re-Interview</option> -->
                                <option value="hold">Hold</option>
                            </select>
                        </div>
                    </div>
                </div>
            `;
		}

		// Check if forwarded candidates exist
		if (jsonData.forwardedCandidates && jsonData.forwardedCandidates.length > 0) {
			jsonData.forwardedCandidates.forEach(candidate => {
				addForwardedCandidateToVendors(candidate);
				forwardedContainer.innerHTML += createCandidateCard(candidate, "Forwarded");
			});
			$("#moderatorRemarksDiv").removeClass("d-none");
		} else {
			selectedVendorsContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
                <p class="text-muted not-found">No candidates found</p>
            </div>`;

			forwardedContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
				<h6 class="text-muted not-found">No forwarded candidates found</h6>
			</div>`;

			if (jsonData.hiredCandidates && jsonData.hiredCandidates.length > 0) {
				jsonData.hiredCandidates.forEach(candidate => {
					forwardedContainer.innerHTML += createCandidateCard(candidate, "Hired");
				});
			} else {
				forwardedContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
					                <p class="text-muted not-found" style="height:3px;">No forwarded candidates found<p>
					            </div>`;
			}
			$("#moderatorRemarksDiv").addClass("d-none");
		}

		if (jsonData.rejectedCandidates && jsonData.rejectedCandidates.length > 0) {
			jsonData.rejectedCandidates.forEach(candidate => {
				rejectedContainer.innerHTML += createCandidateCard(candidate, "Rejected");
			});
		} else {
			rejectedContainer.innerHTML = `<div class="d-flex justify-content-center align-items-center">
                <p class="text-muted not-found" style="height:3px;">No rejected candidates found<p>
            </div>`;
		}


	});
}*/


var columnDefsSelected = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 50,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.candidateName + " (" + params.data.candidateId + ")</a>";
	},
}, {
	headerName: "Final CTC",
	field: "finalCtc",
	pinned: 'left',
	cellStyle: { textAlign: "center" },
	cellRenderer: (params) => {
		console.log("Cell Renderer Params:", params); // Debugging: Check what params contain

		if (params.data.finalCtc && params.data.finalCtc !== "null" && params.data.finalCtc !== "undefined") {
			const encodedCtcJson = encodeURIComponent(params.data.finalOfferedCtcJson);

			return `
                <div>
                    <span>₹ ${params.data.finalCtc}</span>
                    <a href="#" class="see-breakup" 
                        onclick="seeBreakup('${params.data.candidateId}', '${params.data.candidateName}', '${params.value}','${encodedCtcJson}','${params.data.offerStatus}')"> (Breakdown)
                    </a>
                </div>
            `;
		} else {
			return `
                <a href="#" class="add-ctc" 
                    onclick="addFinalCtc('${params.data.candidateId}', '${params.data.candidateName}','0')">
                    <i class="fa fa-plus"></i> Add CTC
                </a>
            `;
		}
	}
},
 /*{
	headerName: "Final CTC",
	field: "finalCtc",
	width: 180,
	cellRenderer: function(params) {
		let container = document.createElement("div");
		container.classList.add("input-group", "mb-2");

		let prependDiv = document.createElement("div");
		prependDiv.classList.add("input-group-prepend");

		let prependText = document.createElement("div");
		prependText.classList.add("input-group-text", "final-ctc-currency");
		prependText.textContent = "₹";

		prependDiv.appendChild(prependText);

		let input = document.createElement("input");
		input.type = "text";
		input.classList.add("form-control", "final-ctc-input");
		input.placeholder = "Enter CTC";
		input.value = params.value || "";

		if (params.value && params.value.trim() !== "") {
			input.setAttribute("readonly", true);
		}

		let timeout;
		input.addEventListener("input", function() {
			clearTimeout(timeout);
			let newValue = input.value;

			timeout = setTimeout(() => {
				if (params.node && params.node.setDataValue) {
					params.node.setDataValue("finalCtc", newValue);
				}
			}, 500);
		});

		input.addEventListener("blur", function() {
			formatAmount(input);
		});

		container.appendChild(prependDiv);
		container.appendChild(input);

		return container;
	}
},*/ {
	headerName: "Offer Letter",
	field: "offerLetterId",
	width: 100,
	cellStyle: { textAlign: "center" },
	cellRenderer: function(params) {
		if (!params.value) return '';

		let icon = document.createElement("i");
		icon.classList.add("fas", "fa-file-pdf", "text-danger", "pdf-icon");
		icon.style.cursor = "pointer";
		icon.style.fontSize = "18px";

		let requisitionDetails = params.data.requisitionDetails || [];
		let bandId = requisitionDetails.length > 0 ? requisitionDetails[0].bandId : null;

		icon.addEventListener("click", function() {
			viewOfferLetter(params.value, params.data.candidateId, bandId);
		});

		let container = document.createElement("div");
		container.style.textAlign = "center";
		container.style.width = "100%";
		container.appendChild(icon);

		return container;
	},
}, {
	headerName: "Release Status",
	field: "offerStatus",
	cellRenderer: function(params) {
		let status = params.value ? params.value.toLowerCase() : "";
		let releasedBy = params.data.releasedBy || "Unknown";

		let span = document.createElement("span");

		if (status === "pending") {
			span.style.color = "orange";
			span.style.fontWeight = "bold";
			span.textContent = "Pending";
		} else if (status === "released") {
			let textSpan = document.createElement("span");
			textSpan.style.color = "green";
			textSpan.style.fontWeight = "bold";
			textSpan.textContent = "Released by ";

			let nameSpan = document.createElement("span");
			nameSpan.style.color = "blue";
			nameSpan.style.fontWeight = "bold";
			nameSpan.style.fontStyle = "italic";
			nameSpan.textContent = releasedBy;
			span.appendChild(textSpan);
			span.appendChild(nameSpan);
		} else {
			span.style.color = "gray";
			span.textContent = status.charAt(0).toUpperCase() + status.slice(1);
		}

		return span;
	}
}

	, {
	headerName: "Candidate Location",
	field: "candidateLoc",
}, {
	headerName: "Experience",
	field: "candidateExp",
}, {
	headerName: "Email",
	field: "email",
}, {
	headerName: "Phone No.",
	field: "candidatePhone",
}, {
	headerName: "Source",
	field: "source",
},];

var gridOptionsSelected = {
	columnDefs: columnDefsSelected,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectIdSelectedCand
};

function rowSelectIdSelectedCand() {
	var selectedNodes = gridOptionsSelected.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var selectedRows = gridOptionsSelected.api.getSelectedRows();

	var candidateId = selectedData.map(node => node.candidateId);
	var finalCtcs = selectedData.map(node => node.finalCtc);
	var offerStatuses = selectedData.map(node => node.offerStatus);

	var rowCount = selectedRows.length;
	let id = "";

	// Construct candidate IDs as a comma-separated string
	for (var i = 0; i < rowCount; i++) {
		id += (i > 0 ? "," : "") + '"' + selectedRows[i].candidateId + '"';
	}

	// 🚀 If no rows are selected, disable all buttons
	if (rowCount === 0) {
		$("#generateOffer").attr("disabled", true);
		$("#shareOfferLetter").attr("disabled", true);
		$("#offerAccepted").attr("disabled", true);
		$("#offerDeclined").attr("disabled", true);
		$("#candidateId").val("");
		return;
	}

	// Check if at least one selected row has a valid finalCtc
	let hasFinalCtc = finalCtcs.some(ctc => ctc != "null" && ctc != "" && ctc != null);
	let isOfferReleased = offerStatuses.some(status => status === "Released");

	if (hasFinalCtc) {
		$("#generateOffer").attr("disabled", true);
		
		$("#candidateId").val(candidateId);
		if (isOfferReleased) {
			$("#shareOfferLetter").attr("disabled", true);
			$("#offerAccepted").attr("disabled", false);
			$("#offerDeclined").attr("disabled", false);
		} else {
			$("#shareOfferLetter").attr("disabled", false);
			$("#offerAccepted").attr("disabled", true);
			$("#offerDeclined").attr("disabled", true);
		}
	} else {
		$("#generateOffer").attr("disabled", true);
		$("#shareOfferLetter").attr("disabled", true);
		$("#offerAccepted").attr("disabled", true);
		$("#offerDeclined").attr("disabled", true);
		$("#candidateId").val(candidateId);
	}
}




function getSelectedCandidateList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-selected-candidates?reqId=" + reqId,
	}).then(function(data) {

		if (!data.body || !Array.isArray(data.body) || data.body.length === 0 || data.body.every(item => item === null)) {
			gridOptionsSelected.api.setRowData([]);
		} else {
			var jsonData = JSON.parse(data.body);
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				gridOptionsSelected.api.setRowData(jsonData);
			} else {
				gridOptionsSelected.api.setRowData([]);
			}

		}
	});
}

var columnDefsHold = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.candidateName + " (" + params.data.candidateId + ")</a>";
	},
}, {
	headerName: "Candidate Location",
	field: "candidateLoc",

}, {
	headerName: "Exerience",
	field: "candidateExp",

}, {
	headerName: "Email",
	field: "email",

}, {
	headerName: "Phone No.",
	field: "candidatePhone",

}, {
	headerName: "Source",
	field: "source",

},
];

var gridOptionsHold = {
	columnDefs: columnDefsHold,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectIdHoldCand

};

let holdCand = "";
let jobId = "";
function rowSelectIdHoldCand() {
	var selectedNodes = gridOptionsHold.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var selectedRows = gridOptionsHold.api.getSelectedRows();

	var candidateId = selectedData.map(node => node.candidateId);
	var requisitionId = selectedData.map(node => node.requisitionId);

	var rowCount = selectedRows.length;
	let id = "";

	// Construct candidate IDs as a comma-separated string
	for (var i = 0; i < rowCount; i++) {
		id += (i > 0 ? "," : "") + '"' + selectedRows[i].candidateId + '"';
	}

	if (rowCount > 0) {
		$("#shortlisHoldtCandidate").attr("disabled",false);
		$("#rejectHoldtCandidate").attr("disabled",false);
		holdCand = candidateId;
		jobId = requisitionId;
	} else{
		$("#shortlisHoldtCandidate").attr("disabled",true);
		$("#rejectHoldtCandidate").attr("disabled",true);
		holdCand = "";
		jobId ="";
	}

}

function getHoldCandidateList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-hold-candidates?reqId=" + reqId,
	}).then(function(data) {
		if (!data.body || !Array.isArray(data.body) || data.body.length === 0 || data.body.every(item => item === null)) {
			gridOptionsHold.api.setRowData([]);
		} else {
			var jsonData = JSON.parse(data.body);
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				gridOptionsHold.api.setRowData(jsonData);
			} else {
				gridOptionsHold.api.setRowData([]);
			}

		}

	});
}


var columnDefsDeclined = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 50,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.candidateName + " (" + params.data.candidateId + ")</a>";
	},
}, {
	headerName: "Final CTC",
	field: "finalCtc",
	width: 120,
	valueFormatter: params => params.value ? `₹ ${indianCurreny(params.value)}` : "₹ 0",
	cellClass: "text-right"
}, {
	headerName: "Offer Letter",
	field: "offerLetterId",
	width: 100,
	cellStyle: { textAlign: "center" },
	cellRenderer: function(params) {
		if (!params.value) return '';

		let icon = document.createElement("i");
		icon.classList.add("fas", "fa-file-pdf", "text-danger", "pdf-icon");
		icon.style.cursor = "pointer";
		icon.style.fontSize = "18px";

		let requisitionDetails = params.data.requisitionDetails || [];
		let bandId = requisitionDetails.length > 0 ? requisitionDetails[0].bandId : null;

		icon.addEventListener("click", function() {
			viewOfferLetter(params.value, params.data.candidateId, bandId);
		});

		let container = document.createElement("div");
		container.style.textAlign = "center";
		container.style.width = "100%";
		container.appendChild(icon);

		return container;
	},
}, {
	headerName: "Release Status",
	field: "offerStatus",
	cellRenderer: function(params) {
		let status = params.value ? params.value.toLowerCase() : "";
		let releasedBy = params.data.releasedBy || "Unknown";

		let span = document.createElement("span");

		if (status === "pending") {
			span.style.color = "orange";
			span.style.fontWeight = "bold";
			span.textContent = "Pending";
		} else if (status === "released") {
			let textSpan = document.createElement("span");
			textSpan.style.color = "green";
			textSpan.style.fontWeight = "bold";
			textSpan.textContent = "Released by ";

			let nameSpan = document.createElement("span");
			nameSpan.style.color = "blue";
			nameSpan.style.fontWeight = "bold";
			nameSpan.style.fontStyle = "italic";
			nameSpan.textContent = releasedBy;
			span.appendChild(textSpan);
			span.appendChild(nameSpan);
		} else {
			span.style.color = "gray";
			span.textContent = status.charAt(0).toUpperCase() + status.slice(1);
		}

		return span;
	}
}

	, {
	headerName: "Candidate Location",
	field: "candidateLoc",
}, {
	headerName: "Experience",
	field: "candidateExp",
}, {
	headerName: "Email",
	field: "email",
}, {
	headerName: "Phone No.",
	field: "candidatePhone",
}, {
	headerName: "Source",
	field: "source",
},];

var gridOptionsOfferDeclined = {
	columnDefs: columnDefsDeclined,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,

};

function getOfferDeclinedCandidateList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-decline-candidates?reqId=" + reqId,
	}).then(function(data) {

		if (!data.body || !Array.isArray(data.body) || data.body.length === 0 || data.body.every(item => item === null)) {
			gridOptionsOfferDeclined.api.setRowData([]);
		} else {
			var jsonData = JSON.parse(data.body);
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				gridOptionsOfferDeclined.api.setRowData(jsonData);
			} else {
				gridOptionsOfferDeclined.api.setRowData([]);
			}

		}
	})
}

var columnDefsAccepted = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 50,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.candidateName + " (" + params.data.candidateId + ")</a>";
	},
}, {
	headerName: "Final CTC",
	field: "finalCtc",
	width: 100,
	valueFormatter: params => params.value ? `₹ ${indianCurreny(params.value)}` : "₹ 0",
	cellClass: "text-right"
}, {
	headerName: "Offer Letter",
	field: "offerLetterId",
	width: 100,
	cellStyle: { textAlign: "center" },
	cellRenderer: function(params) {
		if (!params.value) return '';

		let icon = document.createElement("i");
		icon.classList.add("fas", "fa-file-pdf", "text-danger", "pdf-icon");
		icon.style.cursor = "pointer";
		icon.style.fontSize = "18px";

		let requisitionDetails = params.data.requisitionDetails || [];
		let bandId = requisitionDetails.length > 0 ? requisitionDetails[0].bandId : null;

		icon.addEventListener("click", function() {
			viewOfferLetter(params.value, params.data.candidateId, bandId);
		});

		let container = document.createElement("div");
		container.style.textAlign = "center";
		container.style.width = "100%";
		container.appendChild(icon);

		return container;
	},
}, {
	headerName: "Joining Letter",
	field: "offerLetterId",
	width: 100,
	cellStyle: { textAlign: "center" },
	cellRenderer: function(params) {
		if (!params.value) return '';

		let icon = document.createElement("i");
		icon.classList.add("fas", "fa-file-pdf", "text-danger", "pdf-icon");
		icon.style.cursor = "pointer";
		icon.style.fontSize = "18px";

		let requisitionDetails = params.data.requisitionDetails || [];
		let bandId = requisitionDetails.length > 0 ? requisitionDetails[0].bandId : null;

		icon.addEventListener("click", function() {
			viewJoiningLetter(params.value, params.data.candidateId, bandId);
		});

		let container = document.createElement("div");
		container.style.textAlign = "center";
		container.style.width = "100%";
		container.appendChild(icon);

		return container;
	},
},{
	headerName: "Other Documents",
	field: "offerLetterId",
	width: 100,
	cellStyle: { textAlign: "center" },
	cellRenderer: function(params) {
		if (!params.value) return '';

		let icon = document.createElement("i");
		icon.classList.add("fas", "fa-file-pdf", "text-danger", "pdf-icon");
		icon.style.cursor = "pointer";
		icon.style.fontSize = "18px";

		let requisitionDetails = params.data.requisitionDetails || [];
		let bandId = requisitionDetails.length > 0 ? requisitionDetails[0].bandId : null;

		icon.addEventListener("click", function() {
			viewOtherLetter(params.value, params.data.candidateId, bandId);
		});

		let container = document.createElement("div");
		container.style.textAlign = "center";
		container.style.width = "100%";
		container.appendChild(icon);

		return container;
	},
},{
	headerName: "Release Status",
	field: "offerStatus",
	cellRenderer: function(params) {
		let status = params.value ? params.value.toLowerCase() : "";
		let releasedBy = params.data.releasedBy || "Unknown";

		let span = document.createElement("span");

		if (status === "pending") {
			span.style.color = "orange";
			span.style.fontWeight = "bold";
			span.textContent = "Pending";
		} else if (status === "released") {
			let textSpan = document.createElement("span");
			textSpan.style.color = "green";
			textSpan.style.fontWeight = "bold";
			textSpan.textContent = "Released by ";

			let nameSpan = document.createElement("span");
			nameSpan.style.color = "blue";
			nameSpan.style.fontWeight = "bold";
			nameSpan.style.fontStyle = "italic";
			nameSpan.textContent = releasedBy;
			span.appendChild(textSpan);
			span.appendChild(nameSpan);
		} else {
			span.style.color = "gray";
			span.textContent = status.charAt(0).toUpperCase() + status.slice(1);
		}

		return span;
	}
}

	, {
	headerName: "Candidate Location",
	field: "candidateLoc",
}, {
	headerName: "Experience",
	field: "candidateExp",
}, {
	headerName: "Email",
	field: "email",
}, {
	headerName: "Phone No.",
	field: "candidatePhone",
}, {
	headerName: "Source",
	field: "source",
},];

var gridOptionsOfferAccepted = {
	columnDefs: columnDefsAccepted,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15

};

function getOfferAcceptedCandidateList(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-accepted-candidates?reqId=" + reqId,
	}).then(function(data) {
		if (!data.body || !Array.isArray(data.body) || data.body.length === 0 || data.body.every(item => item === null)) {
			gridOptionsOfferAccepted.api.setRowData([]);
		} else {
			var jsonData = JSON.parse(data.body);
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				gridOptionsOfferAccepted.api.setRowData(jsonData);
			} else {
				gridOptionsOfferAccepted.api.setRowData([]);
			}

		}
	})
}

function saveCandidateData() {
	let candidates = [];
	let remarks = $("#moderatorRemark").val();
	document.querySelectorAll(".selected-vendor-item").forEach(item => {
		let candidateInfo = item.querySelector(".candidate-info").textContent.trim();
		let candidateId = candidateInfo.match(/\((.*?)\)/)[1];
		let requisitionId = $("#jobRole").val();
		let status = item.querySelector(".moderate-status").value || "Pending";

		candidates.push({
			candidateId,
			requisitionId,
			status
		});
	});

	let candidateData = {
		candidateDataObj: candidates,
		remarks: remarks
	};

	$(".loader").show();
	let requisitionId = $("#jobRole").val();
	$.ajax({
		url: 'review-hiring-shortlist-final-candidate',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(candidateData),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				$(".loader").hide();

				getForwardedCandidatesList(requisitionId);
				getRequisitionAllCandidatesList(requisitionId);
				getCandidateOnRequisition(requisitionId);
				getForwardedCandidatesList(requisitionId);
				getSelectedCandidateList(requisitionId);
				getHoldCandidateList(requisitionId);
				getOfferDeclinedCandidateList(requisitionId);
				getOfferAcceptedCandidateList(requisitionId);
				getRejectedCandidates(requisitionId);
			} else {
				toastr.error(response.message);
			}
		},
		error: function(xhr, status, error) {
			console.erro('Failed to save round: ' + error);
		}
	});

}

function shortlisHoldtCandidate(status){
	let candidateData = {
			candidateId: holdCand,
			jobId:jobId,
			status: status
		};

		$(".loader").show();

		$.ajax({
			url: 'review-hiring-shortlist-hold-candidate',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(candidateData),
			success: function(response) {
				if (response.code === "Success") {
					toastr.success(response.message);
					$(".loader").hide();
					getSelectedCandidateList(jobId);
				    getHoldCandidateList(jobId);
					getRejectedCandidates(jobId);
				} else {
					toastr.error(response.message);
				}
			},
			error: function(xhr, status, error) {
				console.erro('Failed to save round: ' + error);
			}
		});
	
}

var columnDefsRejected = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css">' + params.data.candidateName + " (" + params.data.candidateId + ")</a>";
	},
}, {
	headerName: "Candidate Location",
	field: "candidateLoc",

}, {
	headerName: "Exerience",
	field: "candidateExp",

}, {
	headerName: "Email",
	field: "email",

}, {
	headerName: "Phone No.",
	field: "candidatePhone",

}, {
	headerName: "Source",
	field: "source",

}
];

var gridOptionsOfferRejected = {
	columnDefs: columnDefsRejected,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 170,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15

};

function getRejectedCandidates(reqId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-rejected-candidates?reqId=" + reqId,
	}).then(function(data) {

		if (!data.body || !Array.isArray(data.body) || data.body.length === 0 || data.body.every(item => item === null)) {
			gridOptionsOfferRejected.api.setRowData([]);
		} else {
			var jsonData = JSON.parse(data.body);
			if (Array.isArray(jsonData) && jsonData.length > 0) {
				gridOptionsOfferRejected.api.setRowData(jsonData);
			} else {
				gridOptionsOfferRejected.api.setRowData([]);
			}

		}
	})
}

function generateOffer(salaryData, finalCtcSalary) {
	var selectedRows = gridOptionsSelected.api.getSelectedRows();

	if (selectedRows.length === 0) {
		toastr.error("Please select a candidate to generate an offer.");
		return;
	}

	// Validate Final CTC for each selected row
	for (let row of selectedRows) {
		/*if (!row.finalCtc || row.finalCtc.trim() === "" || row.finalCtc.toLowerCase() === "null") {
			toastr.error("Final CTC is required for candidate: " + row.candidateName);
			return;
		}*/

		// Extracting candidate details
		const candId = row.candidateId;
		const reqId = row.requisitionId;
		const finalCtc = row.finalCtc;
		const candidateName = row.candidateName;
		const candidateExp = row.candidateExp;
		const candidateLoc = row.candidateLoc;
		const candidatePhone = row.candidatePhone;
		const candidateEmail = row.email;
		const source = row.source;
		const gender = row.gender;

		// Extracting requisition details (assuming there's always at least one entry)
		let bandId = "";
		let jobTitle = "";
		let joinDate = "";
		let minSalary = "";
		let maxSalary = "";
		let designation = "";
		let department = "";
		let workHour = "";
		let applyStartDate = "";
		let applyEndDate = "";
		let responsibilities = "";
		let educationRequired = "";

		if (row.requisitionDetails && row.requisitionDetails.length > 0) {
			const reqDetails = row.requisitionDetails[0];
			bandId = reqDetails.bandId || "";
			jobTitle = reqDetails.title || "";
			joinDate = reqDetails.joinDate || "";
			minSalary = reqDetails.minSalary || "";
			maxSalary = reqDetails.maxSalary || "";
			designation = reqDetails.designation || "";
			department = reqDetails.department || "";
			workHour = reqDetails.workHour || "";
			applyStartDate = reqDetails.applyStartDate || "";
			applyEndDate = reqDetails.applyEndDate || "";
			responsibilities = reqDetails.responsibilities || "";
			educationRequired = reqDetails.educationRequired || "";
		}

		// Make AJAX call with extracted values
		$.ajax({
			url: `review-shortlist-candidate-hire?id=${candId}&reqId=${reqId}&bandid=${bandId}&finalCtc=${finalCtcSalary}&joiningdate=${joinDate}`,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(salaryData),
			success: function(response) {
				if (response.message === "Success") {
					toastr.success("Offer generated successfully!");
					let reqId = $("#jobRole").val();
					getSelectedCandidateList(reqId);
					closeCtcForm();
				} else {
					toastr.error("Failed to generate offer.");
				}
			},
			error: function(error) {
				toastr.error("An error occurred while generating the offer.");
				console.error(error);
			}
		});
	}
}

function viewOfferLetter(offerLetterId, candidateId, bandId) {
	generateOfferAggrid(offerLetterId, candidateId, bandId);
}

function viewJoiningLetter(offerLetterId, candidateId, bandId){
	window.open("/recruitment/joining-letter-pdf?candId=" + window.btoa(candidateId) + "&bandid=" + window.btoa(bandId) + "&offerLetter=" + window.btoa(offerLetterId), '_blank');
}

function viewOtherLetter(offerLetterId, candidateId, bandId){
	window.open("/recruitment/nda-letter-pdf?candId=" + window.btoa(candidateId) + "&bandid=" + window.btoa(bandId) + "&offerLetter=" + window.btoa(offerLetterId), '_blank');
}
/* Function for generate offer letter */
function generateOfferAggrid(offerLetter, candId, bandid) {

	$('#generateOfferLetter').attr("disabled", true);
	$('#joiningStatus').attr("disabled", true);

	window.open("/recruitment/offer-letter-pdf?candId=" + window.btoa(candId) + "&bandid=" + window.btoa(bandid) + "&offerLetter=" + window.btoa(offerLetter), '_blank');
}

function formatAmount(element) {
	let value = element.value;
	if (value) {
		let formattedValue = indianCurreny(value);
		element.value = formattedValue;
		$(element.id).val(value);

	}

}

function indianCurreny(value) {
	if (value !== null && value !== undefined) {
		value = value.replace(/[^\d.,]/g, '');

		var parts = value.toString().split('.');
		var integerPart = parts[0].replace(/,/g, '');
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

		if (decimalPart.length > 3) {
			decimalPart = decimalPart.substring(0, 3);
		}

		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}

function sendOfferLetter() {
	$(".loader").show();
	var selectedRows = gridOptionsSelected.api.getSelectedRows();

	if (selectedRows.length === 0) {
		toastr.error("Select at least one candidate to release an offer.");
		return;
	}

	if (selectedRows.length > 1) {
		toastr.error("Please select only one candidate at a time.");
		return;
	}

	let row = selectedRows[0];

	if (!row.finalCtc || row.finalCtc.trim() === "" || row.finalCtc.toLowerCase() === "null") {
		toastr.error("Offer letter is not generated for candidate: " + row.candidateName);
		return;
	}

	let jobTitle = "", joinDate = "", designation = "", department = "", workHour = "", bandId = "";

	if (row.requisitionDetails && row.requisitionDetails.length > 0) {
		let reqDetails = row.requisitionDetails[0];
		jobTitle = reqDetails.title || "";
		joinDate = reqDetails.joinDate || "";
		designation = reqDetails.designation || "";
		department = reqDetails.department || "";
		workHour = reqDetails.workHour || "";
		bandId = reqDetails.bandId || "";
	}

	let candidateData = {
		candidateId: row.candidateId,
		requisitionId: row.requisitionId,
		candidateName: row.candidateName,
		candidatePhone: row.candidatePhone,
		candidateEmail: row.email,
		offerLetterId: row.offerLetterId,
		finalCtc: row.finalCtc,
		jobTitle: jobTitle,
		joinDate: joinDate,
		designation: designation,
		department: department,
		workHour: workHour,
		bandId: bandId
	};

	$.ajax({
		type: "POST",
		url: "review-shortlist-candidate-offer-release",
		contentType: "application/json",
		data: JSON.stringify(candidateData),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				let reqId = $("#jobRole").val();
				getSelectedCandidateList(reqId);
				$("#shareOfferLetter").attr("disabled", true);
				$(".loader").hide();
			} else {
				toastr.error("Failed to generate offer.");
			}
		},
		error: function(error) {
			toastr.error("An error occurred while generating the offer.");
			console.error(error);
		}
	});
}

function offerAcceptance(status) {

	const candId = $("#candidateId").val();
	const reqId = $("#jobRole").val();

	$.ajax({
		type: "GET",
		url: "review-candidate-offer-acceptance?candId=" + candId + "&reqId=" + reqId + "&status=" + status,
		success: function(response) {
			if (response.code == "Success") {
				toastr.success(response.message);
				getSelectedCandidateList(reqId);
				getOfferDeclinedCandidateList(reqId)
				getOfferAcceptedCandidateList(reqId)
			}
		}
	});
}

function addFinalCtc(candidateId, candidateName,status) {
	if(status == '0'){
		$("#saveFinalCtc").removeClass('d-none').attr("disabled", false);
		$("#editFinalCtc").removeClass('d-none').attr("disabled", true);
	}
	$("#closeCtcForm").removeClass('d-none');
	$("#generateOffer").addClass('d-none');
	$("#shareOfferLetter").addClass('d-none');
	$("#offerAccepted").addClass('d-none');
	$("#offerDeclined").addClass('d-none');
	$("#slectedPrevBtn").addClass('d-none');
	$("#slectedNextBtn").addClass('d-none');
	$("#myGridSelected").addClass('d-none');
	$(".selectHeader").addClass('d-none');
	$(".finalCtcCalcDiv").removeClass('d-none');
	$("#ctcCandidateId").val(candidateId);
	$("#ctcCandidateName").val(candidateName);
}

function editFinalCtc() {
	$("#saveFinalCtc").attr("disabled", false);
	$("#band").prop("disabled", false);
	$("#basic").prop("disabled", false);
	$("#hra").prop("disabled", false);
	$("#convAllow").prop("disabled", false);
	$("#washAllow").prop("disabled", false);
	$("#specialallowance").prop("disabled", false);
	$("#medAllow").prop("disabled", false);
	$("#skillDev").prop("disabled", false);
	$("#lic").prop("disabled", false);
	$("#wFund").prop("disabled", false);

}

function seeBreakup(candidateId, candidateName, finalCtc, encodedCtcJson,offerStatus) {
	const decodedCtcJson = decodeURIComponent(encodedCtcJson);
	const ctcData = JSON.parse(decodedCtcJson);
	if(offerStatus == "Released"){
		$("#saveFinalCtc").addClass('d-none').attr("disabled", true);
		$("#editFinalCtc").addClass('d-none').attr("disabled", false);
	} else{
		$("#saveFinalCtc").removeClass('d-none').attr("disabled", true);
			$("#editFinalCtc").removeClass('d-none').attr("disabled", false);
	}
	
	// Call function with candidate details
	addFinalCtc(candidateId, candidateName,'1');
	$("#band").val(ctcData.band || "").prop("disabled", true);
	$("#basic").val(ctcData.basic || "").prop("disabled", true);
	$("#esi1").val(ctcData.esi || "").prop("disabled", true);
	$("#esicWage").val(ctcData.esicWage || "").prop("disabled", true);
	$("#hra").val(ctcData.hra || "").prop("disabled", true);
	$("#providentFund").val(ctcData.providentFund || "").prop("disabled", true);
	$("#mEmployerPf").val(ctcData.mEmployerPf || "").prop("disabled", true);
	$("#convAllow").val(ctcData.convAllow || "").prop("disabled", true);
	$("#pTax").val(ctcData.pTax || "").prop("disabled", true);
	$("#washAllow").val(ctcData.washAllow || "").prop("disabled", true);
	$("#lic").val(ctcData.lic || "").prop("disabled", true);
	$("#specialallowance").val(ctcData.specialallowance || "").prop("disabled", true);
	$("#wFund").val(ctcData.wFund || "").prop("disabled", true);
	$("#medAllow").val(ctcData.medAllow || "").prop("disabled", true);
	$("#skillDev").val(ctcData.skillDev || "").prop("disabled", true);
	$("#totalEarn").val(ctcData.totalEarn || "").prop("disabled", true);
	$("#totalDeduct").val(ctcData.totalDeduct || "").prop("disabled", true);
	$("#totalContribution").val(ctcData.totalContribution || "").prop("disabled", true);
	$("#netPay").val(ctcData.netPay || "").prop("disabled", true);
	$("#ctc").val(ctcData.ctc || "");
	
}


/*function seeBreakup(candidateId, candidateName, value, encodedCtcJson) {
	const decodedCtcJson = decodeURIComponent(encodedCtcJson);
	console.log(decodedCtcJson); // Now it's safe to use
}*/


function closeCtcForm() {
	$("#saveFinalCtc").addClass('d-none').attr("disabled", true);
	$("#editFinalCtc").addClass('d-none');
	$("#closeCtcForm").addClass('d-none');
	$("#generateOffer").removeClass('d-none');
	$("#shareOfferLetter").removeClass('d-none');
	$("#offerAccepted").removeClass('d-none');
	$("#offerDeclined").removeClass('d-none');
	$("#slectedPrevBtn").removeClass('d-none');
	$("#slectedNextBtn").removeClass('d-none');
	$("#myGridSelected").removeClass('d-none');
	$(".selectHeader").removeClass('d-none');
	$(".finalCtcCalcDiv").addClass('d-none');
	$("#ctcCandidateId").val("");
	$("#ctcCandidateName").val("");
	$("#band").val("");
	$("#basic").val("");
	$("#esi1").val("");
	$("#esicWage").val("");
	$("#hra").val("");
	$("#providentFund").val("");
	$("#mEmployerPf").val("");
	$("#convAllow").val("");
	$("#pTax").val("");
	$("#washAllow").val("");
	$("#lic").val("");
	$("#specialallowance").val("");
	$("#wFund").val("");
	$("#medAllow").val("");
	$("#skillDev").val("");
	$("#totalEarn").val("");
	$("#totalDeduct").val("");
	$("#totalContribution").val("");
	$("#netPay").val("");
}

function saveFinalCtc() {
	var finalCtc = $("#ctc").val();
	let salaryData = {
		basic: $("#basic").val() || "0.0",
		esi: $("#esi1").val() || "0.0",
		esicWage: $("#esicWage").val() || "0.0",
		hra: $("#hra").val() || "0.0",
		providentFund: $("#providentFund").val() || "0.0",
		mEmployerPf: $("#mEmployerPf").val() || "0.0",
		convAllow: $("#convAllow").val() || "0.0",
		pTax: $("#pTax").val() || "0.0",
		washAllow: $("#washAllow").val() || "0.0",
		lic: $("#lic").val() || "0.0",
		specialallowance: $("#specialallowance").val() || "0.0",
		wFund: $("#wFund").val() || "0.0",
		medAllow: $("#medAllow").val() || "0.0",
		skillDev: $("#skillDev").val() || "0.0",
		totalEarn: $("#totalEarn").val() || "0.0",
		totalDeduct: $("#totalDeduct").val() || "0.0",
		totalContribution: $("#totalContribution").val() || "0.0",
		netPay: $("#netPay").val() || "0.0",
		band: $("#band").val(),
		ctc: $("#ctc").val() || "0.0"
	};

	generateOffer(salaryData, finalCtc);

}

function totalCtc() {
    let totalEarn = parseFloat($("#totalEarn").val()) || 0;
    let totalContribution = parseFloat($("#totalContribution").val()) || 0;
    
    let totalCtcAmt = totalEarn + totalContribution;
    let yearlyCtc = totalCtcAmt * 12;
    
    $("#ctc").val(yearlyCtc.toFixed(2)); 
}



