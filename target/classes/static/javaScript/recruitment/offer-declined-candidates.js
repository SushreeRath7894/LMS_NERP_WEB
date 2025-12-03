$(document).ready(function() {
	var gridDivSelected = document.querySelector('#myGrid');
	new agGrid.Grid(gridDivSelected, gridOptionsOfferDeclined);

	var gridDivSkill = document.querySelector('#myGridSkill');
	new agGrid.Grid(gridDivSkill, gridOptionsSkill);

	gridOptionsOfferDeclined.api.setRowData([]);
	gridOptionsSkill.api.setRowData([]);
	getOfferDeclinedCandidateList();

});

var columnDefsSelected = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
}, {
	headerName: "Candidate Id",
	field: "candidateId",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.candidateId + '</a>';

	},
}, {
	headerName: "Requisition Id",
	field: "requisitionId",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.requisitionId + '</a>';

	},
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',

}, {
	headerName: "Job Title",
	field: "title",
	cellRenderer: function(params) {
		return params.data.requisitionDetails[0].title

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

}, {
	headerName: "Job Location",
	field: "location",
	cellRenderer: function(params) {
		return params.data.requisitionDetails[0].location

	},

},
];

var gridOptionsOfferDeclined = {
	columnDefs: columnDefsSelected,
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
	//onSelectionChanged: changeScheduled

};

function rowSelectId() {
	var selectedNodes = gridOptionsOfferDeclined.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId= selectedData.map(node => node.requisitionId);
	const skillsReqs= selectedData.map(node => node.candidateId);
	const data = JSON.stringify(selectedData[0]);
	var selectedRows = gridOptionsOfferDeclined.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		openCandidateDetails(data);
		viewJobDetails(reqId);
	} else {
	}
};

function getOfferDeclinedCandidateList() {
	agGrid.simpleHttpRequest({
		url: "review-hiring-decline-candidates",
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);

		if (jsonData && Array.isArray(jsonData) && jsonData.length > 0) {
			gridOptionsOfferDeclined.api.setRowData(jsonData);
			if (jsonData.length > 0) {
				gridOptionsOfferDeclined.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});
			}
		} else {
			gridOptionsOfferDeclined.api.setRowData([]);
		}
	})
}

var columnDefsSkills = [{
	headerName: "Skills",
	field: "skills"
}, {
	headerName: "Self Rating",
	field: "level",
	width: 130,
}, {
	headerName: "Experience",
	field: "expSkills",
	width: 130,
},
{
	headerName: "Comments",
	field: "desc",
	width: 408,
}];

var gridOptionsSkill = {
	columnDefs: columnDefsSkills,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		height: 10
	},
	rowSelection: 'single',

};

function openCandidateDetails(data) {
	let allData = JSON.parse(data);

	$("#resumeCandidateId").val(allData.candidateId);

	if (allData.profileImg != null && allData.profileImg != '') {
		var dynamicPath = "/document/employee/" + allData.profileImg;
		$("#profileImg").attr("src", dynamicPath);
	} else if (allData.gender == "Male") {
		$("#profileImg").attr("src", "https://img.freepik.com/premium-photo/actor-digital-avatar-generative-ai_934475-9309.jpg");
	} else if (allData.gender == "Female") {
		$("#profileImg").attr("src", "https://img.freepik.com/premium-photo/3d-cartoon-avatar-girl-minimal-3d-character_652053-2327.jpg?w=360");
	} else {
		$("#profileImg").attr("src", "/assets/index-img/user.jpg");
	}


	$("#candidateMobile").html(allData.candidatePhone ? allData.candidatePhone : "N/A");
	$("#candidateEmailSpan").html(allData.email ? allData.email : "N/A");
	$("#commAddress").html(allData.candidateLoc ? allData.candidateLoc : "N/A");

	$("#candidateEducation").html(allData.candidateEducation ? allData.candidateEducation : "N/A");
	$("#candidateName").html(allData.candidateName);
	$("#expYrs").html(allData.candidateExp ? allData.candidateExp + " Years" : "");


	gridOptionsSkill.api.setRowData(allData.candidateSkill);

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