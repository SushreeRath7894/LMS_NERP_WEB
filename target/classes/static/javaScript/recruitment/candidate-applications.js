$(document).ready(function() {
	var gridDivResumes = document.querySelector('#myGridResumes');
	new agGrid.Grid(gridDivResumes, gridOptionsResume);

	var gridDivSkill = document.querySelector('#myGridSkill');
	new agGrid.Grid(gridDivSkill, gridOptionsSkill);

	getCandidateList('');
});

$(document).ready(function() {
	let flag = false; 

	document.getElementById('goBtn').addEventListener('click', function () {
		flag = true;
		quickFilterGrid(gridOptionsResume);
	});

	document.getElementById('resetBtn').addEventListener('click', function () {
		document.getElementById('quickFilter').value = '';
		gridOptionsResume.api.setQuickFilter('');
		flag = false; 
		$(".loader").show();
		selectFirstRow(gridOptionsResume);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function (event) {
		const inputValue = this.value.trim();

		if (event.key === 'Enter' && inputValue !== '') {
			flag = true;
			quickFilterGrid(gridOptionsResume);
		} else if (event.key === 'Backspace' && flag) {
			quickFilterGrid(gridOptionsResume);
		}
	});


	$('#shareToggle').change(function() {
		if ($(this).is(':checked')) {
			$('#shareButton').removeClass('d-none');
		} else {
			$('#shareButton').addClass('d-none');
		}
	});
});


var columnDefsResume = [{
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
	hide: true,
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.requisitionId + '</a>';
	}, pinned: 'left',

}, {
	headerName: "Candidate Id",
	field: "candidateId",
	pinned: 'left',
	width: 130,
	hide: true,
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.candidateId + '</a>';
	}
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	width: 150,
	cellRenderer: function(params) {
			const name = params.value || '';
			const createdOn = params.data.createdOn || '';

			const today = new Date();
			const createdDate = new Date(createdOn);
			const timeDiff = today - createdDate;
			const daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); 

			let badgeText = '';
			let badgeStyle = '';

			if (daysDiff === 0) {
				// Today: Green badge
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
				badgeText = `${daysDiff} days old`;
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

			if (badgeText) {
				return `
	                <div style="
	                    display: flex;
	                    align-items: center;
	                    height: 100%;
	                    gap: 5px;
	                ">
	                    <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
	                    <span style="${badgeStyle}">
	                        ${badgeText}
	                    </span>
	                </div>
	            `;
			}

			return `<div style="display: flex; align-items: center; height: 100%;"><span>${name}</span></div>`;
		},
	autoHeight: true,
	cellStyle: { 'display': 'flex', 'align-items': 'center' }
},{
	headerName: "Job Tittle",
	field: "requisitionName",

},/* {
	headerName: "ResumeMatch %",
	field: "resumeMatchScore",
	cellRenderer: function(params) {
		if (params.value == null || params.value === undefined) return '';

		const score = Math.min(100, Math.max(0, Math.round(params.value)));
		const color = score >= 75 ? '#34a853' :
			score >= 60 ? '#FFC107' :
				score >= 40 ? '#FF9800' : '#F44336';

		return `
            <div class="cand-res-scr">
                <div style="width: ${score}%; height: 100%; background: ${color};">
                    <span class="cand-res-sp">
                        ${score}%
                    </span>
                </div>
            </div>
        `;
	},
	width: 130
},*/ /*{
	headerName: "Analysis",
	field: "analysis",
	width: 130,
	cellRenderer: function(params) {
		const div = document.createElement('div');
		const resumeMatchScore = params.data.resumeMatchScore || 0;
		const roundedScore = Math.round(resumeMatchScore);

		if (roundedScore === 0) {
			div.className = 'generate-analysis';
			div.innerHTML = `
				<i class="fas fa-sync-alt" style="font-size: 12px;"></i>
				<span>Generate</span>
			`;
			div.addEventListener('click', () => viewAnalysisReport(`${params.data.candidateId}`, `${params.data.requisitionId}`));
		} else {
			div.className = 'cand-analysis';
			div.innerHTML = `
				<i class="fas fa-eye" style="font-size: 12px;"></i>
				<span>View Analysis</span>
			`;
			div.addEventListener('click', () => viewAnalysis());
		}
		return div;
	},
	cellStyle: {
		'display': 'flex',
		'align-items': 'center',
		'justify-content': 'center'
	}
},*/ {
	headerName: "Source",
	field: "source",
	width: 130
}, {
	headerName: "Mobile",
	field: "mobile"
}, {
	headerName: "DOB",
	field: "dob",

}, {
	headerName: "Gender",
	field: "gender",

}, {
	headerName: "Experience",
	field: "experience"
}, {
	headerName: "Email",
	field: "email"
},// New Resume Match Score column with progress bar
];

var gridOptionsResume = {
	columnDefs: columnDefsResume,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 190,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectId,
	onSelectionChanged: onSelectionChangeCandidate

};

function rowSelectId() {
	var selectedNodes = gridOptionsResume.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	const candidateName = selectedData.map(node => node.candidateName);
	const data = JSON.stringify(selectedData[0]);
	var selectedRows = gridOptionsResume.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		$(".candNameHead").text(candidateName);
		openCandidateDetails(data);
		viewJobDetails(reqId);
		$("#shortlistedCandidate").attr("disabled", false);
	} else {
		$(".candNameHead").text("");
		$("#shortlistedCandidate").attr("disabled", true);
	}
};

function getCandidateList(status) {
	var exp;
	if (status) {
		exp = status;
	} else {
		exp = '';
	}

	agGrid.simpleHttpRequest({
		url: "review-hiring-candidate-list?exp=" + exp,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);

		var allData = jsonData.candidateData;

		if (allData != null) {
			var len = allData.length;
			$('#totalReqResume').find('span').html(len);
			$('#resumeSpan').html(len);
			allData.sort((a, b) => new Date(b.createdOn) - new Date(a.createdOn));
			gridOptionsResume.api.setRowData(allData);
			if (allData && allData.length > 0) {
				const candId = $("#candId").val();
				const jobId = $("#jobId").val();
				if (candId && jobId) {
					gridOptionsResume.api.forEachNode(function(node) {
						if (node.data && node.data.requisitionId === jobId && node.data.candidateId === candId) {
							node.setSelected(true);
						}
					});
		         }	else {
						gridOptionsResume.api.forEachNode(function(node) {
							if (allData && allData.length > 0) {
								gridOptionsResume.api.forEachNode(function(node) {
									if (node.rowIndex === 0) {
										node.setSelected(true);
									}
								});
							}
						});

					}
			}
			$('.candidate-profile-contr').removeClass('d-none');
			$('#no-candidateMsg').addClass('d-none');
			$('#shortListBtn').removeClass('d-none');
			$('#no-jobdtlsMsg').addClass('d-none');
			$('#jobDetailsSec').removeClass('d-none');
			$("#shortlistedCandidate").attr("disabled", false);
		} else {
			/*$('#resumeSpan').html("0");
			$('#totalReqResume').find('span').html("0");*/
			$('.candidate-profile-contr').addClass('d-none');
			$('#no-candidateMsg').removeClass('d-none');
			$('#no-jobdtlsMsg').removeClass('d-none');
			$('#jobDetailsSec').addClass('d-none');
			$('#shortListBtn').addClass('d-none');
			$("#shortlistedCandidate").attr("disabled", true);
			gridOptionsResume.api.setRowData([]);
		}
	})
}

function openCandidateDetails(data) {
	let allData = JSON.parse(decodeURIComponent(data));
	$("#resumeCandidateId").val(allData.candidateId);

	if (allData.profileImg != null && allData.profileImg !== '') {
		var dynamicPath = "/document/employee/" + allData.profileImg;
		$("#profileImg").attr("src", dynamicPath);
	} else {
		$("#profileImg").attr("src", "/assets/index-img/user.jpg");
	}

	$("#candidateMobile").html(allData.mobile ? allData.mobile : "N/A");
	$("#candidateEmailSpan").html(allData.email ? allData.email : "N/A");
	$("#commAddress").html(allData.address ? allData.address : "N/A");
	$("#candidateEducation").html(allData.candidateEducation);
	$("#candidateName").html(allData.candidateName);
	$(".candidate-name").html(allData.candidateName);
	$(".candidate-email").html(allData.email);
	$("#candidateEmailDisplay").text(allData.email);
	$("#expYrs").html(allData.experience ? allData.experience + " Years" : "");
	gridOptionsSkill.api.setRowData(allData.candidateSkill);
	
	let preInterviewStatus = allData.preScreeningAiStstus;

	if (preInterviewStatus == 1) {
		$("#shareToggle").prop("checked", true).prop("disabled", true);
		$("#shareButton").addClass("d-none");
		$("#sharedToEmail").text(allData.email); 
		$("#candRespName").text(allData.candidateName); 
		$("#shareSuccessMessage").removeClass("d-none");
		$("#candidateRespMessage").removeClass("d-none");
		$('#shortlistedCandidate').addClass('d-none');
	} else {
		$("#shareToggle").prop("checked", false).prop("disabled", false);
		$("#shareButton").removeClass("d-none");
		$("#shareSuccessMessage").addClass("d-none");
		$("#candidateRespMessage").addClass("d-none");
		$('#shortlistedCandidate').removeClass('d-none');
	}

}





var columnDefsSkills = [{
	headerName: "Key Skills",
	field: "skills"
}, {
	headerName: "Self Rating",
	field: "level",
	width: 100,
}, {
	headerName: "Years of Exp",
	field: "expSkills",
	width: 100,
},
{
	headerName: "Skill Descriptionp",
	field: "desc",
	width: 300,
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

var candidateRequisitionPairs = [];

function onSelectionChangeCandidate() {
	var selectedRows = gridOptionsResume.api.getSelectedRows();
	var rowCount = selectedRows.length;

	candidateRequisitionPairs = []; // Reset the array on selection change

	selectedRows.forEach(function(selectedRow) {
		candidateRequisitionPairs.push({
			candidateId: selectedRow.candidateId,
			requisitionId: selectedRow.requisitionId
		});
	});

	var selectedNodes = gridOptionsResume.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	const candId = selectedData.map(node => node.candidateId);
	const candName = selectedData.map(node => node.candidateName);
	const jobName = selectedData.map(node => node.requisitionName);
	const resumeAnalyseData = selectedData.map(node => node.resumeAnalyseData);
	const candidateName = selectedData.map(node => node.candidateName);
	const data = JSON.stringify(selectedData[0]);

	if (rowCount > 0) {
		openCandidateDetails(data);
		viewJobDetails(reqId);
		$(".candNameHead").text(candidateName);
		nextBtnFunction('candidateDetailsTab');
		$('#candId').val(candId);
		$('#jobId').val(reqId);
		$('#candName').val(candName);
		$('#jobName').val(jobName);
		$('#shortlistedCandidate').attr("disabled", false);
		$('.candidate-profile-contr').removeClass('d-none');
		$('#no-candidateMsg').addClass('d-none');
		$('#jobDetailsSec').removeClass('d-none');
		console.log(resumeAnalyseData[0]);
		if (resumeAnalyseData && resumeAnalyseData.length > 0 && resumeAnalyseData[0]) {
			populateAnalysisData(resumeAnalyseData[0]);
			
		} else {
			resetAnalysisView();
		}



	} else {
		$(".candNameHead").text("");
		$('#shortlistedCandidate').attr("disabled", true);
		$('.candidate-profile-contr').addClass('d-none');
		$('#no-candidateMsg').removeClass('d-none');
		$('#jobDetailsSec').addClass('d-none');
		$('#candId').val("");
		$('#jobId').val("");
		$('#candName').val("");
		$('#jobName').val("");
	}
}

function shortListCandidates() {
	if (candidateRequisitionPairs.length === 0) {
		toastr.warning("No candidate selected.");
		return;
	}

	var data = { candidateList: [candidateRequisitionPairs[0]] }; // Send only the first selected candidate

	$(".loader").show();
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "review-hiring-candidate-shortlist",
			data: JSON.stringify(data),
			contentType: 'application/json',
			success: function(response) {
				$(".loader").hide();
				if (response.code === "success") {
					toastr.success(response.message);
					getCandidateList('');
					$("#candId").val("");
					$("#jobId").val("");
				} else {
					toastr.error(response.message);
				}
			},
			error: function() {
				$(".loader").hide();
				toastr.error("An error occurred.");
			}
		});
	}, 1000);
}


function viewJobDetails(reqid) {
	$.ajax({
		type: "GET",
		url: "review-hiring-job-details?id=" + reqid,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const data = JSON.parse(response.body);
				if (data.requisitionData.length > 0) {
					const job = data.requisitionData[0];
					// Update job details
					$("#jobId").val(job.requisitionId);
					$("#job-title").text(job.jobTitle || "N/A");
					$("#jd-positionSummary").text(job.summary || "N/A");
					$("#jd-positionResponsibility").text(job.responsibility || "N/A");
					$("#jd-designation").text(job.designation || "N/A");
					$("#jd-band").text(job.jobBand || "N/A");
					$("#jd-hiring-manager").text(job.hiringManager || "N/A");
					$("#jd-join-date").text(job.joinDate || "N/A");
					$("#jd-apply-date").text(job.applyStartDate || "N/A");
					$("#jd-end-date").text(job.applyEndDate || "N/A");
					$("#jd-positions").text(job.noOfPositions || "N/A");
					$("#jd-experience").text(`${job.minExp || "N/A"} - ${job.maxExp || "N/A"} years`);
					$("#jd-salary").text(`₹${job.minSalary || "N/A"}`);
					$("#jd-status").text(job.activityStatus === "Active" ? "Active" : "Inactive");
					$("#jd-apprv-status").text(job.approvalStatus || "Pending");
					$("#jobsPagePrev").addClass("d-none");
					$("#jobsPageNext").addClass("d-none");

					// Add skills dynamically
					const skillsContainer = $("#jobDetailsSec .jd-skills");
					skillsContainer.empty();

					if (job.skillsReq && job.skillsReq.length > 0) {
						job.skillsReq.forEach(skill => {
							skillsContainer.append(`
	                                <div class="col-md-4 mb-3">
	                                    <div class="skill-card border rounded p-2 d-flex align-items-center">
	                                        <div>
	                                            <span class="fw-bold">${skill.skillName}</span>
	                                            <small class="d-block text-muted">Experience: ${skill.skillExp} years</small>
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

					// Footer details
					$("#created-on").text(job.createdOn || "N/A");
					$("#approved-by").text(job.approvedBy || "N/A");
				} else {
					console.error("No requisition data found.");
				}
			} else {
				console.error("Failed to fetch job details.");
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


// AI ANalysis Report
document.addEventListener('DOMContentLoaded', function() {
	// Button click handlers
	document.querySelector('.btn-download').addEventListener('click', function() {
		alert('Download functionality would be implemented here');
	});

	document.querySelector('.btn-preview').addEventListener('click', function() {
		alert('Preview functionality would be implemented here');
	});

	// Animation for main circular progress bars (shown on Overall tab)
	const circles = document.querySelectorAll('.progress:not(.skills-progress)');

	circles.forEach(circle => {
		const percent = circle.getAttribute('data-percent');
		const circumference = 2 * Math.PI * 45;
		const offset = circumference - (percent / 100) * circumference;

		circle.style.strokeDashoffset = offset;

		// Change color based on percentage
		if (percent < 30) {
			circle.style.stroke = '#ff5252'; // Red for low
		} else if (percent < 70) {
			circle.style.stroke = '#ffc107'; // Yellow for medium
		} else {
			circle.style.stroke = 'rgb(52 168 83)'; // Green for high
		}
	});

	// Add event listener for when Skills tab is shown
	const skillsTab = document.getElementById('skills-tab');
	skillsTab.addEventListener('shown.bs.tab', function() {
		// Animation for skills circular progress bars
		const skillsCircles = document.querySelectorAll('.skills-progress');

		skillsCircles.forEach(circle => {
			const percent = circle.getAttribute('data-percent');
			const circumference = 2 * Math.PI * 45;
			const offset = circumference - (percent / 100) * circumference;

			circle.style.strokeDashoffset = offset;

			// Change color based on percentage
			if (percent < 30) {
				circle.style.stroke = '#ff5252'; // Red for low
			} else if (percent < 70) {
				circle.style.stroke = '#ffc107'; // Yellow for medium
			} else {
				circle.style.stroke = '#34a853'; // Green for high
			}
		});
	});
});

function viewAnalysisReport(candId, reqId) {
	showLoader('Starting analysis', 'Preparing data...');
	nextBtnFunction('candidateAnalysisTab');


	$.ajax({
		type: "GET",
		url: "review-hiring-get-candidate-analysis?candId=" + candId + "&reqId=" + reqId,
		async: true,
		success: function(response) {
			if (response.code === "success") {
				populateAnalysisData(response.body);
				hideLoader();
				saveAnalyzedData(response.body, candId, reqId);
			}
		}
	});
}

function populateAnalysisData(data) {

	$("#jdNextBtn").removeClass('d-none');
	$("#no-analyticsMsg").addClass('d-none');
	$(".resume-container").removeClass('d-none');
	$(".job-match-container").show();

	const skillsSection = $(".skills-section");
	skillsSection.empty();
	data.skillsEvaluation.skillsDetails.forEach(skill => {
		skillsSection.append(`<div class="skill-tag">${skill.skillName}</div>`);
	});
	// Set Overall Scores
	setProgressCircle('overall-progress', data.analysisSummary.overallScore);
	setProgressCircle('experience-progress', data.experienceEvaluation.score);
	setProgressCircle('skills-progress', data.skillsEvaluation.skillsScore);
	// Note: Education score not in API data - keeping static or add to API
	setProgressCircle('education-progress', data.educationEvaluation.score);
	setProgressCircle('achievements-progress', data.notableAchievements.score);

	// Update percentage text displays
	$('#overall-progress-div').text(data.analysisSummary.overallScore + '%');
	$('#experience-progress-div').text(data.experienceEvaluation.score + '%');
	$('#skills-progress-div').text(data.skillsEvaluation.skillsScore + '%');
	$('#education-progress-div').text(data.educationEvaluation.score + '%');
	$('#achievements-progress-div').text(data.notableAchievements.score + '%');

	// Set Analysis Sections
	$('#overview-content').html(data.cvAnalysisSummary.overview);

	// Technical Expertise
	$('#technical-expertise-content').html(`
        <p>${data.cvAnalysisSummary.technicalExpertise.summary}</p>
        ${data.cvAnalysisSummary.technicalExpertise.strengths.length ? `
        <p><strong>Strengths:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.technicalExpertise.strengths.map(s => `<li>${s}</li>`).join('')}
        </ul>
        ` : ''}
        ${data.cvAnalysisSummary.technicalExpertise.gaps.length ? `
        <p><strong>Gaps:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.technicalExpertise.gaps.map(g => `<li>${g}</li>`).join('')}
        </ul>
        ` : ''}
    `);

	// Leadership & Team Management
	$('#leadership-content').html(`
        <p>${data.cvAnalysisSummary.leadershipTeamManagement.summary}</p>
        ${data.cvAnalysisSummary.leadershipTeamManagement.strengths.length ? `
        <p><strong>Strengths:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.leadershipTeamManagement.strengths.map(s => `<li>${s}</li>`).join('')}
        </ul>
        ` : ''}
        ${data.cvAnalysisSummary.leadershipTeamManagement.gaps.length ? `
        <p><strong>Gaps:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.leadershipTeamManagement.gaps.map(g => `<li>${g}</li>`).join('')}
        </ul>
        ` : ''}
    `);

	// Growth & Performance Focus
	$('#growth-content').html(`
        <p>${data.cvAnalysisSummary.growthPerformanceFocus.summary}</p>
        ${data.cvAnalysisSummary.growthPerformanceFocus.strengths.length ? `
        <p><strong>Strengths:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.growthPerformanceFocus.strengths.map(s => `<li>${s}</li>`).join('')}
        </ul>
        ` : ''}
        ${data.cvAnalysisSummary.growthPerformanceFocus.gaps.length ? `
        <p><strong>Gaps:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.growthPerformanceFocus.gaps.map(g => `<li>${g}</li>`).join('')}
        </ul>
        ` : ''}
    `);

	// Areas for Development
	$('#development-areas-content').html(`
        <ul class="development-list">
            ${data.cvAnalysisSummary.areasForDevelopment.priorityAreas.map(a => `<li>${a}</li>`).join('')}
        </ul>
        <p><strong>Development Strategies:</strong></p>
        <ul class="development-list">
            ${data.cvAnalysisSummary.areasForDevelopment.developmentStrategies.map(s => `<li>${s}</li>`).join('')}
        </ul>
    `);

	// Cultural Fit
	$('#cultural-fit-content').html(`
        <p>${data.analysisSummary.culturalFit.assessment} (Score: ${data.analysisSummary.culturalFit.score})</p>
    `);

	// Salary Recommendation
	$('#salary-recommendation').text(`Recommended Salary: ${data.compensationAnalysis.recommendedRange}`);

	// Populate Skills Tab
	const skillsContainer = $('#skills-container');
	skillsContainer.empty();

	data.skillsEvaluation.skillsDetails.forEach(skill => {
		let colorClass;
		if (skill.skillScore >= 75) {
			colorClass = 'high';
		} else if (skill.skillScore >= 60) {
			colorClass = 'medium';
		} else if (skill.skillScore >= 40) {
			colorClass = 'low-medium';
		} else {
			colorClass = 'low';
		}
		skillsContainer.append(`
			    <div class="col-md-12">
			        <div class="progress-card d-flex align-items-center">
			            <div class="progress-circle-skills me-3" style="width: 80px; height: 80px;">
			                <svg viewBox="0 0 100 100" width="100%" height="100%" preserveAspectRatio="xMidYMid meet">
			                    <circle class="bg" cx="50" cy="50" r="45"></circle>
			                    <circle class="progress skills-progress ${colorClass}" 
			                            cx="50" cy="50" r="45" 
			                            data-percent="${skill.skillScore}" 
			                            id="skill-${skill.skillName.toLowerCase().replace(/\s+/g, '-')}-progress">
			                    </circle>
			                </svg>
			                <div class="percentage ${colorClass}">${skill.skillScore}%</div>
			            </div>
			            <div class="flex-grow-1">
			                <div class="progress-label-skills">${skill.skillName}</div>
			                <div class="progress-desc-skills">${skill.skillAssessment}</div>
			            </div>
			        </div>
			    </div>
			`);

		// Initialize the skill progress circle
		setProgressCircle(`skill-${skill.skillName.toLowerCase().replace(/\s+/g, '-')}-progress`, skill.skillScore);
	});

	// Populate CV Tab with detailed analysis
	const cvTabContent = $('#cv .cvAnalyzeData');
	cvTabContent.empty();

	cvTabContent.append(`
	        <div class="cv-analysis-container">
			<div class="ai-analyze-header mb-4"><i class="fa-solid fa-wand-magic-sparkles me-2"></i>Detailed CV Analysis by AI</div>
	            
	            <div class="mb-2">
	                <div class="section-title">Candidate Overview</div>
	                <p>${data.cvAnalysisSummary.overview}</p>
	            </div>
	            
	            <div class="mb-2">
	                <div class="section-title">Key Strengths</div>
	                <ul class="strengths-list development-list">
	                    ${data.cvAnalysisSummary.keyStrengths.map(strength => `<li>${strength}</li>`).join('')}
	                </ul>
	            </div>
	            
	            <div class="mb-2">
	                <div class="section-title">Critical Gaps</div>
	                <ul class="gaps-list development-list">
	                    ${data.cvAnalysisSummary.criticalGaps.map(gap => `<li>${gap}</li>`).join('')}
	                </ul>
	            </div>
	            
	            <div class="mb-2">
	                <div class="section-title">Final Recommendation</div>
	                <p class="recommendation-text">${data.cvAnalysisSummary.finalRecommendation}</p>
	            </div>
	            
	            <div class="mb-2">
	                <div class="section-title">Detailed Analysis Points</div>
	                <ul class="detailed-points development-list">
	                    ${data.cvAnalyzeData.map(point => `<li>${point}</li>`).join('')}
	                </ul>
	            </div>
	            
	            <div class="mb-2">
	                <div class="section-title">Development Plan</div>
	                <p><strong>Priority Areas:</strong></p>
	                <ul class="development-list">
	                    ${data.developmentPlan.priorityAreas.map(area => `<li>${area}</li>`).join('')}
	                </ul>
	                <p><strong>Training Recommendations:</strong></p>
	                <ul class="development-list">
	                    ${data.developmentPlan.trainingRecommendations.map(training => `<li>${training}</li>`).join('')}
	                </ul>
	                <p><strong>Estimated Time to Productivity:</strong> ${data.developmentPlan.timeToProductivity}</p>
	            </div>
	            
	            <div class="">
	                <div class="section-title">Compensation Analysis</div>
	                <p><strong>Recommended Salary Range:</strong> ${data.compensationAnalysis.recommendedRange}</p>
	                <p><em>${data.compensationAnalysis.rationale}</em></p>
	            </div>
	        </div>
	    `);

	// Update percentage text
	const score = data.analysisSummary.overallScore || 0;

	if (score > 80) {
		$(".cand-ev-sts").html(`<span class="candidateName-badge top-candidate">
											<i class="fas fa-star c-s-i"></i> Top Candidate</span>`);
	} else if (score >= 70 && score <= 80) {
		$(".cand-ev-sts").html(`<span class="candidateName-badge strong-candidate">
											 <i class="fas fa-bolt c-s-i"></i> Strong Candidate</span>`);
	}

	$(".job-match-percentage").html(score + "%");


	// Get progress circle and text elements
	const progressCircle = $(".job-match-circle svg .progress");
	const percentageText = $(".job-match-percentage");

	// Correct circumference calculation
	const radius = 45;  // Your circle radius
	const circumference = 2 * Math.PI * radius; // Formula for circumference

	// Correct stroke-dashoffset calculation
	const offset = circumference * (1 - (score / 100));

	// Remove existing color classes
	progressCircle.removeClass("high medium low-medium low");
	percentageText.removeClass("high medium low-medium low");

	// Apply color and class based on score
	if (score >= 75) {
		progressCircle.addClass("high");
		percentageText.css("color", "#34a853"); // Green
	} else if (score >= 60) {
		progressCircle.addClass("medium");
		percentageText.css("color", "#FFC107"); // Yellow
	} else if (score >= 40) {
		progressCircle.addClass("low-medium");
		percentageText.css("color", "#FF9800"); // Orange
	} else {
		progressCircle.addClass("low");
		percentageText.css("color", "#F44336"); // Red
	}

	// Apply correct stroke-dashoffset to progress bar
	progressCircle.css("stroke-dasharray", circumference); // Ensure full range
	progressCircle.css("stroke-dashoffset", offset);

	// Skills Summary
	$('#skills-summary-content').html(`<p>${data.analysisSummary.recommendationSummary}</p>`);

	// Initialize all progress circles
	initializeProgressCircles();
	$(".loader").hide();
}

function setProgressCircle(id, percent) {
	$(`#${id}`).attr('data-percent', percent);
}

function initializeProgressCircles() {
	$("[id$='-progress']").each(function() {
		const percent = parseInt($(this).attr("data-percent"));
		const circumference = 2 * Math.PI * 45;
		const offset = circumference - (percent / 100) * circumference;

		// Set color based on percentage
		let color;

		if (percent >= 75) {
			color = '#34a853';
		} else if (percent >= 60) {
			color = '#FFC107';
		} else if (percent >= 40) {
			color = '#FF9800';
		} else {
			color = '#ff5252';
		}

		$(this).css({
			"stroke-dasharray": circumference,
			"stroke-dashoffset": offset,
			"stroke": color
		});
	});
}

function resetAnalysisView() {
	// Show no analytics message and hide resume container
	$("#no-analyticsMsg").removeClass('d-none');
	$(".resume-container").addClass('d-none');
	$("#jdNextBtn").addClass('d-none');

	// Reset all progress circles
	$("[id$='-progress']").each(function() {
		$(this).attr('data-percent', 0);
		$(this).css({
			"stroke-dasharray": "0",
			"stroke-dashoffset": "0",
			"stroke": "#e0e0e0"
		});
	});

	// Clear all text content
	$('.skill-tag').remove();
	$('#overall-progress-div, #experience-progress-div, #skills-progress-div, #education-progress-div, #achievements-progress-div').text('0%');
	$('#overview-content, #technical-expertise-content, #leadership-content, #growth-content, #development-areas-content, #cultural-fit-content').empty();
	$('#salary-recommendation').text('Recommended Salary: N/A');
	$('#skills-container').empty();
	$('#cv .cvAnalyzeData').empty();
	$(".cand-ev-sts").empty();
	$(".job-match-container").hide();
	$(".job-match-percentage").text("0%");
	$('#skills-summary-content').empty();

	// Reset the main progress circle
	const progressCircle = $(".job-match-circle svg .progress");
	progressCircle.removeClass("high medium low-medium low");
	progressCircle.css({
		"stroke-dasharray": "0",
		"stroke-dashoffset": "0",
		"stroke": "#e0e0e0"
	});

	// Hide loader
	$(".loader").hide();
}

function closeAnalysis() {
	$("#candidateAnalysis").addClass('d-none');
	$("#jdNextBtn").addClass('d-none');
	nextBtnFunction('candidateDetailsTab');
}

function viewAnalysis() {
	nextBtnFunction('candidateAnalysisTab');
}

document.addEventListener('DOMContentLoaded', () => {
	const loaderBackdrop = document.getElementById('loaderBackdrop');

	window.showLoader = function(message = 'Analyzing content', submessage = 'AI is processing your request') {
		const textElement = loaderBackdrop.querySelector('.light-ai-text');
		const subtextElement = loaderBackdrop.querySelector('.light-ai-subtext');

		if (message) textElement.innerHTML = message.includes('<') ? message : `${message}<span class="light-ai-dots"></span>`;
		if (submessage) subtextElement.textContent = submessage;

		loaderBackdrop.style.display = 'flex';
	}

	window.hideLoader = function() {
		loaderBackdrop.style.display = 'none';
	}
});

function saveAnalyzedData(data, candId, reqId) {
	let overallScore = $('.job-match-percentage').text().replace('%', '');
	let obj = {
		analysisData: data,
		candidateId: candId,
		jobId: reqId,
		overallScore: overallScore

	};

	$.ajax({
		url: 'review-hiring-save-analyze-data',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				hideLoader();
				getCandidateList('');
			} else {
				toastr.error(response.message);
				hideLoader();
				
			}
		},
		error: function(xhr, status, error) {
			console.erro('Failed to save round: ' + error);
		}
	});
}

/*function sharePreScreening() {
	window.open("/pre-screening-with-ai", "_blank");
	
	
}
*/

function sharePreScreening() {
	$(".loader").show();
	const candidateEmail = document.getElementById("candidateEmailDisplay").textContent;
	const candidateId = $("#candId").val();
	const jobId = $("#jobId").val();
	const candName = $('#candName').val();
	const jobName = $('#jobName').val();

	let obj = {
		email: candidateEmail,
		candidateId: candidateId,
		jobId: jobId,
		candidateName:candName,
		jobName: jobName,
		status:1
	}


	$.ajax({
		url: 'review-hiring-sendPreScreeningMail',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(obj),
		success: function(response) {
			toastr.success(response.message);
			getCandidateList('');
			$(".loader").hide();
		},
		error: function() {
			toastr.error(response.message);
		}
	});
}

