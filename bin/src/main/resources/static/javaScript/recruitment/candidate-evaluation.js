$(document).ready(function() {
	var gridDivResumes = document.querySelector('#myGridResumes');
	new agGrid.Grid(gridDivResumes, gridOptionsResume);

	var gridDivSkill = document.querySelector('#myGridSkill');
	new agGrid.Grid(gridDivSkill, gridOptionsSkill);
	gridOptionsResume.api.setRowData([]);
	getCandidateList();

	/* $(".round-toggle-btn").on("click", function () {
	   const $icon = $(this).find("i");
	   $(this).closest(".round").find(".feedback-list").slideToggle();
	   $icon.toggleClass("fa-caret-square-o-up fa-caret-square-o-down ");
   }); */
});

$(document).ready(function() {
	let flag = false;
	document.getElementById('goBtn').addEventListener('click', function() {
		flag = true;
		quickFilterGrid(gridOptionsResume);
	});

	document.getElementById('resetBtn').addEventListener('click', function() {
		document.getElementById('quickFilter').value = '';
		gridOptionsResume.api.setQuickFilter('');
		flag = false;
		$(".loader").show();
		selectFirstRow(gridOptionsResume);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function(event) {
		const inputValue = this.value.trim();

		if (event.key === 'Enter' && inputValue !== '') {
			flag = true;
			quickFilterGrid(gridOptionsResume);
		} else if (event.key === 'Backspace' && flag) {
			quickFilterGrid(gridOptionsResume);
		}
	});
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
	headerName: "Candidate Id",
	field: "candidateId",
	pinned: 'left',
	hide: true,
	cellRenderer: function(params) {
		let encodedLogData = encodeURIComponent(JSON.stringify(params.data));

		if (params.data.candidateId) {
			return '<a class="edit-css" href="javascript:void(0)" title="Candidate Id">'
				+ params.data.candidateId
				+ '</a>';

		} else {
			return params.data.candidateId;
		}
	}
}, {
	headerName: "Requisition ID",
	field: "requisitionId",
	pinned: 'left',
	hide: true,
	cellRenderer: function(params) {
		return '<a class="edit-css" href="javascript:void(0)" title="Requisition Id">'
			+ params.data.requisitionId
			+ '</a>';
	},

}, {
	headerName: "Candidate Name",
	field: "candidateName",
	pinned: 'left',
	cellRenderer: function(params) {
	    const name = params.value || '';
	    const createdOn = params.data.createdOn || '';
	    console.log(createdOn);

	    let createdDate;
	    if (createdOn) {
	        // Parse YYYY-MM-DD format
	        const [year, month, day] = createdOn.split('-').map(Number);
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

	    return `
	        <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
	            <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
	            ${badgeText ? `<span style="${badgeStyle}">${badgeText}</span>` : ''}
	        </div>
	    `;
	}
},{
  headerName: "Interview Status",
  field: "interviewStatus",
  width:100,
  cellRenderer: function(params) {
    const status = params.data.interviewStatus;
    const statusMap = {
      completed: { class: "bg-active-badge", label: "Completed" },
      pending: { class: "bg-pending", label: "Pending" },
	  scheduled: { class: "bg-primary-badge", label: "Scheduled" },
    };

    const { class: badgeClass, label } = statusMap[status] || {
      class: "bg-default",
      label: "Unknown",
    };

    return `<span class="badge ${badgeClass}" style=" display: inline-block; text-align: center;">${label}</span>`;
  },
},   {
    headerName: "Feedback Status",
    field: "feedbackStatus",
    width:100,
    cellRenderer: function(params) {
      const status = params.data.feedbackStatus;
      const statusMap = {
        completed: { class: "bg-active-badge", label: "Completed" },
        review: { class: "bg-created", label: "Reviewing" },
	  	null: { class: "bg-pending", label: "Pending" },
	   };

      const { class: badgeClass, label } = statusMap[status] || {
        class: "bg-default",
        label: "Unknown",
      };

      return `<span class="badge ${badgeClass}" style=" display: inline-block; text-align: center;">${label}</span>`;
    },
  },{
	headerName: "Job Title",
	field: "requisitionName",
	cellRenderer: function(params) {
		const title = params.data.requisitionName || '';
		const minExp = params.data.reqMinExp || '';
		const maxExp = params.data.reqMaxExp || '';

		let experience = '';
		if (minExp && maxExp) {
			experience = ` (${minExp}-${maxExp} Years)`;
		}

		return `<a>${title}${experience}</a>`;
	}
}, {
	headerName: "Source",
	field: "source"
}, {
	headerName: "Mobile",
	field: "mobileNo"
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
	field: "personalMailId"
},/*  {
	headerName : "Status",
	field : "joiningStatus"
}  */];

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
	onSelectionChanged: rowSelectId
	//onSelectionChanged: onSelectionChangeCandidate

};

function rowSelectId() {
	var selectedNodes = gridOptionsResume.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	const candidateId = selectedData.map(node => node.candidateId);
	const data = JSON.stringify(selectedData[0]);
	var selectedRows = gridOptionsResume.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		openCandidateDetails(data);
		$("#jobId").val(reqId);
		$("#candidateId").val(candidateId);
		getFeedbackData(reqId, candidateId);
		getCandidateInvDetailsById(candidateId, reqId);
	} else {
		$("#jobId").val("");
		$("#candidateId").val("");
	}
};
function parseDate(dateStr) {
       const [day, month, year] = dateStr.split("-");
       return new Date(`${year}-${month}-${day}`);
   }
   function getCandidateList() {
       agGrid.simpleHttpRequest({
           url: "review-hiring-get-candidate-evaluation"
       }).then(function(data) {
           if (data.body[0] == null || data.body[0] == "null") {
               gridOptionsResume.api.setRowData([]);
               $("#candidate-details").addClass("d-none");
               $("#no-cand-msg").removeClass("d-none");
               $(".no-candidate-fnd-msg").html("No Candidate Found");
           } else {
               $("#candidate-details").removeClass("d-none");
               $("#no-cand-msg").addClass("d-none");
               $(".no-candidate-fnd-msg").html("");
               var allData = JSON.parse(data.body);
               
               allData.sort((a, b) => {
                   const dateA = new Date(a.createdOn);
                   const dateB = new Date(b.createdOn);
                   return dateB - dateA; 
               });
               
               console.log("Sorted Data:", allData);
               
               if (allData != null) {
                   gridOptionsResume.api.setRowData(allData);
                   const candId = $("#candidateId").val();
                   const jobId = $("#jobId").val();
                   
                   if (candId && jobId) {
                       gridOptionsResume.api.forEachNode(function(node) {
                           if (node.data && node.data.requisitionId === jobId && node.data.candidateId === candId) {
                               node.setSelected(true);
                           }
                       });
                   } else {
                       // Select the first row (which will be the newest after sorting)
                       if (allData && allData.length > 0) {
                           gridOptionsResume.api.forEachNode(function(node) {
                               if (node.rowIndex === 0) {
                                   node.setSelected(true);
                               }
                           });
                       }
                   }
               } else {
                   $('#resumeSpan').html("0");
                   $('#totalReqResume').find('span').html("0");
                   gridOptionsResume.api.setRowData();
               }
           }
       });
   }

function openCandidateDetails(data) {
	let allData = JSON.parse(decodeURIComponent(data));
	$("#resumeCandidateId").val(allData.candidateId);
	$("#candidateMobile").html(allData.mobileNo ? allData.mobileNo : "N/A");
	$("#candidateEmailSpan").html(allData.personalMailId ? allData.personalMailId : "N/A");
	$("#commAddress").html(allData.address ? allData.address : "N/A");
	$("#candidateEducation").html(allData.candidateEducation);
	$("#candidateName").html(allData.candidateName);
	$("#expYrs").html(allData.experience ? allData.experience + " Years" : "");

	if (allData.candidateImage != null && allData.candidateImage != '') {
		$("#profileImg").attr("src", allData.candidateImage);
	} else {
		$("#profileImg").attr("src", "/assets/index-img/user.jpg");
	}


	gridOptionsSkill.api.setRowData(allData.candidateSkill);

	/*feedbackSkillsRatings(allData.candidateSkill);*/

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
	headerName: "Skill Description",
	field: "desc",
	width: 285,
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

function feedbackSkillsRatings(skills) {
	const skillsContainer = document.querySelector('.skills-container');
	skillsContainer.innerHTML = '';

	if (!skills || skills.length === 0) {
		const noSkillsMessage = document.createElement('div');
		noSkillsMessage.classList.add('no-skills-message');
		noSkillsMessage.textContent = 'No skills found';
		skillsContainer.appendChild(noSkillsMessage);
		return;
	}

	skills.forEach(skill => {
		const skillRow = document.createElement('div');
		skillRow.classList.add('row', 'mb-3', 'align-items-center');

		const skillLabelCol = document.createElement('div');
		skillLabelCol.classList.add('col-md-4');
		const skillLabel = document.createElement('label');
		skillLabel.classList.add('fw-bold', 'mb-0');
		skillLabel.textContent = skill.skills;
		skillLabelCol.appendChild(skillLabel);

		const ratingCol = document.createElement('div');
		ratingCol.classList.add('col-md-8', 'interview-rating', 'text-end');

		for (let i = 1; i <= 10; i++) {
			const radioInput = document.createElement('input');
			radioInput.type = 'radio';
			radioInput.name = skill.skills.toLowerCase().replace(/\s+/g, '-');
			radioInput.id = `${skill.skills.toLowerCase().replace(/\s+/g, '-')}-${i}`;
			radioInput.value = i;

			const radioLabel = document.createElement('label');
			radioLabel.setAttribute('for', radioInput.id);
			radioLabel.textContent = i;

			if (i <= 5) {
				//radioInput.classList.add('rating-low');
				radioLabel.classList.add('rating-low');
			} else if (i >= 6 && i <= 7) {
				//radioInput.classList.add('rating-medium');
				radioLabel.classList.add('rating-medium');
			} else {
				//radioInput.classList.add('rating-high');
				radioLabel.classList.add('rating-high');
			}


			ratingCol.appendChild(radioInput);
			ratingCol.appendChild(radioLabel);
		}

		skillRow.appendChild(skillLabelCol);
		skillRow.appendChild(ratingCol);

		skillsContainer.appendChild(skillRow);
	});
}

function interviewDetails(data) {
	$("#no-inv-msg").addClass("d-none");
	$("#scheduled-interviwers-details").removeClass("d-none")
	$("#scheduled-interviwers-details").html("");

	const container = document.getElementById('scheduled-interviwers-details');

	data.rounds.forEach((round, index) => {
		const roundDiv = document.createElement('div');
		roundDiv.classList.add('interview-timeline', 'mb-2');

		const roundHeader = document.createElement('div');
		roundHeader.classList.add('round', `inv-view_${data.candidateId}_${round.roundId}`);

		const isCompleted = round.interviewStatus === "completed";
		const roundHeaderContent = `
			    <div class="round-header">
			        <div class="status-badge selected">Round ${index + 1}</div>
			        <div class="round-info">
			            <h3>${round.roundName}</h3>
			            <p>
			                <span class="round-date">${data.requisitionName}</span>
			            </p>
			            
			             <div class="actions-round">
			                <div class="d-flex gap-2">
			                <button class="btn btn-primary edit-btn d-none" type="button" id="saveCandidateFeedback-${index + 1}" onclick="saveCandidateFeedback('${round.roundId}','${data.candidateId}','${data.requisitionId}','${round.feedbackId}');">Save</button>
			                    <button class="complete-btn ${isCompleted ? 'btn-completed' : ''}"
			                       data-index="${index}"
			                       ${isCompleted ? 'disabled' : ''}
			                       onclick="markAsComplete('${round.roundId}','${data.requisitionId}','${data.candidateId}');">
			                       <i class="fa fa-check-circle" aria-hidden="true"></i>
			                       ${isCompleted ? 'Completed' : 'Mark as Complete'}
			                    </button>
			                    
			                    ${isCompleted ? `
								<div class="inv-round-btn">
								   ${round.feedbackStatus !== "completed" ? `
								   <div class="dropdown">
								      <button class="btn btn-secondary" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								         <i class="fa fa-ellipsis-h" aria-hidden="true"></i>
								      </button>
								      <ul class="dropdown-menu">
								         ${round.feedbackStatus === "review" ? `
								         <li><a class="dropdown-item" data-bs-toggle="collapse" href="#collapseFeedback-${index + 1}" role="button" aria-expanded="false" aria-controls="collapseFeedback-${index + 1}" onclick="shareFeedback('${index + 1}');">Feedback</a></li>
								         ` : ''}
								         ${round.feedbackStatus !== "completed" && round.feedbackStatus !== "review" ? `
								         <li><a class="dropdown-item" href="#" onclick="reviewPending('${round.roundId}','${data.candidateId}','${data.requisitionId}','${round.feedbackId}');">Review Pending</a></li>
								         <li><a class="dropdown-item" data-bs-toggle="collapse" href="#collapseFeedback-${index + 1}" role="button" aria-expanded="false" aria-controls="collapseFeedback-${index + 1}" onclick="shareFeedback('${index + 1}');">Feedback</a></li>
								         ` : ''}
								      </ul>
								   </div>
								   ` : ''}
								</div>
								` : ''}

			                    <button class="round-toggle-btn toggle_btn_${data.candidateId}_${round.roundId}-${index + 1}" data-target="round-${index + 1}-details">
			                       <i class="fa fa-caret-down" aria-hidden="true"></i>
			                    </button>
			                </div>
			             </div>
			        </div>
			    </div>
			    ${round.interviewStatus === "completed" ? `
				  <div class="alert ${round.feedbackStatus === "completed" ? "alert-success" : "alert-warning"} invStatusMsg" role="alert" id="interviewStatusMsg">
				    <span id="systemMsg">
				      ${!round.feedbackStatus || round.feedbackStatus === null ?
					"Interview has been completed, but the feedback is pending." :
					round.feedbackStatus === "review" ?
						"Interview has been completed, but the feedback is under review." :
						"Interview has been completed, and the feedback has been successfully submitted. Thank you!"}
				    </span>
				  </div>
				` : ''}

				<div class="collapse mt-3" id="collapseFeedback-${index + 1}">
				  <div class="card card-body">
				      <i class="fa fa-close close-feedback" data-bs-toggle="collapse" href="#collapseFeedback-${index + 1}" role="button" aria-expanded="false" aria-controls="collapseFeedback-${index + 1}"  onclick="cancelFeedback('${index + 1}');"></i>
                   <h6 class="fw-bold inv-sec-header mb-1">Feedback on ${round.roundName} Performance</h6>
                   <p class="mb-2 text-muted">Share your brief feedback on the candidate's performance in the ${round.roundName}.</p>
                   <!-- Skills Rating Section -->
					<h6 class="fw-bold mb-4 inv-sec-header mt-2">Skills
						Assessment</h6>
					<div class="g-3 skills-container">
				    ${data.skillsReq.map(skill => `
				        <div class="row align-items-center">
				            <div class="col-md-4">
				                <label class="fw-bold mb-0">${skill.skillName || "Unknown Skill"}</label>
				            </div>
				            <div class="col-md-8 col-12 interview-rating text-end">
				                ${[...Array(10).keys()].map(i => `
				                    <input type="radio" name="skill_${skill.skillId}_${round.roundId}" 
				                           id="skill_${skill.skillId}_${round.roundId}_rating_${i + 1}" 
				                           value="${i + 1}" 
				                           data-skill-name="${skill.skillName || 'Unknown Skill'}">
				                    <label for="skill_${skill.skillId}_${round.roundId}_rating_${i + 1}">${i + 1}</label>
				                `).join('')}
				            </div>
				        </div>
				    `).join('')}
				</div>

					<h6 class="fw-bold mb-3 inv-sec-header">Feedback
						Comments</h6>
					<textarea class="form-control comments-box" rows="4"
						placeholder="Write detailed feedback here..." id="feedbackSummary_${round.roundId}_${data.candidateId}"></textarea>
						
						<div class="d-flex gap-3 mt-3">
					    <div class="form-check form-switch">
					        <input class="form-check-input" type="radio" name="recommendation_${round.roundId}_${data.candidateId}" id="stronglyRecommended_${round.roundId}_${data.candidateId}" value="Strongly Recommended">
					        <label class="form-check-label inv-sec-header" for="stronglyRecommended">Strongly Recommended</label>
					    </div>
					    <div class="form-check form-switch">
					        <input class="form-check-input" type="radio" name="recommendation_${round.roundId}_${data.candidateId}" id="recommended_${round.roundId}_${data.candidateId}" value="Recommended">
					        <label class="form-check-label inv-sec-header" for="recommended">Recommended</label>
					    </div>
					    <div class="form-check form-switch">
					        <input class="form-check-input" type="radio" name="recommendation_${round.roundId}_${data.candidateId}" id="notRecommended_${round.roundId}_${data.candidateId}" value="Not Recommended">
					        <label class="form-check-label inv-sec-header" for="notRecommended">Not Recommended</label>
					    </div>
					    <div class="form-check form-switch">
					        <input class="form-check-input" type="radio" name="recommendation_${round.roundId}_${data.candidateId}" id="maybe_${round.roundId}_${data.candidateId}" value="Maybe">
					        <label class="form-check-label inv-sec-header" for="maybe">Maybe</label>
					    </div>
					    <div class="form-check form-switch">
					        <input class="form-check-input" type="radio" name="recommendation_${round.roundId}_${data.candidateId}" id="noOpinion_${round.roundId}_${data.candidateId}" value="No Opinion">
					        <label class="form-check-label inv-sec-header" for="noOpinion">No Opinion</label>
					    </div>
					</div>

				  </div>  
				</div>
		      `;



		roundHeader.innerHTML = roundHeaderContent;

		const feedbackList = document.createElement('div');
		feedbackList.classList.add('feedback-list', `inv-view-feedback-list_${data.candidateId}_${round.roundId}`);
		feedbackList.style.display = 'none';

		// Interview details
		const feedbackContent = `
			    <div class="feedback">
			        <div class="feedback-header">
			            <h4>Interview Details</h4>
			            <div class="interview-details mt-2">
			                <div class="d-flex gap-3">
			                    <p class="comments">
			                        <i class="fas fa-map-marker-alt"></i> <span>${round.interviewLocation}</span>
			                    </p>
			                    <p class="comments">
			                        <i class="fas fa-calendar-alt"></i> <span>${round.interviewDate}</span>
			                    </p>
			                    <p class="comments">
			                        <i class="fas fa-clock"></i> <span>${round.interviewFromTime} - ${round.interviewToTime}</span>
			                    </p>
			                    <p class="comments">
			                        Duration: <span>${round.interviewDuration}</span>
			                    </p>
			                </div>
			                <hr>
			                <div class="job-position-details">
			                    <div class="job-header">
			                        <h2>${data.requisitionName}</h2>
			                        <span class="experience">Experience: ${data.reqMinExp} - ${data.reqMaxExp} Years</span>
			                    </div>
			                    <div class="job-info">
			                        <p>Required Skills</p>
			                         <table class="skills-table">
			                            <thead>
			                                <tr>
			                                    <th style="width: 77%"></th>
			                                    <th></th>
			                                </tr>
			                            </thead>
			                            <tbody>
			                                ${data.skillsReq.map(skill => `
			                                    <tr>
			                                        <td><i class="fa fa-check-circle"></i> ${skill.skillName}</td>
			                                        <td><strong>(${skill.skillExp} Years)</strong></td>
			                                    </tr>
			                                `).join('')}
			                            </tbody>
			                        </table>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
			`;

		feedbackList.innerHTML = feedbackContent;

		// Interviewer details
		const interviewerContent = `
                <div class="feedback">
                    <div class="feedback-header">
                        <h4>Interviewers</h4>
                        <div class="interviewer-names-container mt-2">
                            ${round.interviewers.map(interviewer => `
                                <span class="interviewer-box" value="${interviewer.interviewerId}" >
                                    ${interviewer.interviewerName}
                                </span>
                            `).join('')}
                        </div>
                    </div>
                </div>
            `;

		feedbackList.innerHTML += interviewerContent;
		roundDiv.appendChild(roundHeader);
		roundDiv.appendChild(feedbackList);
		container.appendChild(roundDiv);
		$(document).on("click", `.toggle_btn_${data.candidateId}_${round.roundId}-${index + 1}`, function() {
			let btn = "";
			let icon = "";
			icon = $(this).find("i");
			btn = $(this).closest(`.inv-view_${data.candidateId}_${round.roundId}`).parent().find(`.inv-view-feedback-list_${data.candidateId}_${round.roundId}`);
			btn.slideToggle();
			icon.toggleClass("fa-caret-up fa-caret-down ");
		});
	});




}

function markAsComplete(roundId, reqId, candidateId) {
	const type = "interview-completed";
	const url = `review-hiring-save-interviwer-resp?type=${type}&roundId=${roundId}&reqId=${reqId}&candidateId=${candidateId}`;

	agGrid.simpleHttpRequest({
		url: url
	}).then(function(data) {
		if (data.code == "Success") {
			getCandidateList();
			var selectedNodes = gridOptionsResume.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);
			const data = JSON.stringify(selectedData[0]);
			interviewDetails(data);
			toastr.success("Interview Status Updated Successfully")
		} else {
			console.warn("Something went wrong!");
		}
	});
}

function shareFeedback(index) {
	$(`#saveCandidateFeedback-${index}`).removeClass('d-none');
}

function cancelFeedback(index) {
	$(`#saveCandidateFeedback-${index}`).addClass('d-none');
}

function reviewPending(roundId, candidateId, requisitionId, feedbackId) {
	const feedbackData = {
		roundId: roundId,
		candidateId: candidateId,
		requisitionId: requisitionId,
		feedSummary: "",
		ratingDetails: [],
		recommendation: "",
		feedback: "review",
		feedbackId: feedbackId
	};

	const url = "review-hiring-save-feedback";
	$.ajax({
		url: url,
		method: "POST",
		data: JSON.stringify(feedbackData),
		contentType: "application/json",
		success: function(response) {
			getCandidateInvDetailsById(candidateId, requisitionId);
			toastr.success("Candidate is under review");

		},
		error: function(error) {
			console.error("Error saving feedback:", error);
		}
	});
}


function getCandidateInvDetailsById(candidateId, requisitionId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-candidate-evaluation-using-id?candidateId=" + candidateId + "&requisitionId=" + requisitionId,
	}).then(function(data) {
		var allData = JSON.parse(data.body);
		interviewDetails(allData[0]);
	});
}

function saveCandidateFeedback(roundId, candidateId, requisitionId, feedbackId) {
    const feedbackSummary = document.getElementById(`feedbackSummary_${roundId}_${candidateId}`).value.trim();

    // Validate feedback summary
    if (!feedbackSummary) {
        toastr.error("Please provide feedback summary");
        return;
    }

    const skills = [];
    const skillElements = document.querySelectorAll(`[name^="skill_"][name$="_${roundId}"]`);

    skillElements.forEach(skillElement => {
        const skillId = skillElement.name.split('_')[1];
        const rating = document.querySelector(`input[name="skill_${skillId}_${roundId}"]:checked`)?.value || null;

        if (rating) {
            const skillName = skillElement.getAttribute('data-skill-name') || "Unknown Skill";
            const existingSkill = skills.find(skill => skill.skillId === skillId);
            if (!existingSkill) {
                skills.push({
                    skillId: skillId,
                    skillName: skillName,
                    rating: rating
                });
            }
        }
    });

    // Validate at least one skill rating is provided
    if (skills.length === 0) {
        toastr.error("Please provide ratings for skill");
        return;
    }

    const recommendation = document.querySelector(`input[name="recommendation_${roundId}_${candidateId}"]:checked`)?.value || null;

    // Validate recommendation
    if (!recommendation) {
        toastr.error("Please select a recommendation");
        return;
    }

    const feedbackData = {
        roundId: roundId,
        candidateId: candidateId,
        requisitionId: requisitionId,
        feedSummary: feedbackSummary,
        ratingDetails: skills,
        recommendation: recommendation,
        feedback: "completed",
        feedbackId: feedbackId
    };

    console.log("Feedback Data:", feedbackData);

    const url = "review-hiring-save-feedback";
    $.ajax({
        url: url,
        method: "POST",
        data: JSON.stringify(feedbackData),
        contentType: "application/json",
        success: function(response) {
            toastr.success("Feedback saved successfully");
            getCandidateInvDetailsById(candidateId, requisitionId);
            getFeedbackData(requisitionId, candidateId);
			getCandidateList();
        },
        error: function(error) {
            console.error("Error saving feedback:", error);
            toastr.error("Failed to save feedback");
        }
    });
}

function getFeedbackData(reqId, candidateId) {
	const type = "interviwer";
	const url = `review-hiring-feedback-details?requiId=${reqId}&candId=${candidateId}&type=${type}`;

	agGrid.simpleHttpRequest({
		url: url
	}).then(function(data) {
		if (data.code == "success") {
			populateFeedbackData(data);
			$("#no-feedback-msg").addClass('d-none');
			$("#feedbacks-container").removeClass('d-none');
		} else {
			console.warn("Something went wrong!");
		}
	});
}

// Function to populate multiple feedback cards
function populateFeedbackData(data) {
	const feedbacks = JSON.parse(data.body[0]);

	const feedbacksContainer = document.getElementById('feedbacks-container');
	feedbacksContainer.innerHTML = '';

	if (!feedbacks || feedbacks.length === 0) {
		feedbacksContainer.innerHTML = `
            <div class="d-flex justify-content-center align-items-center" style="height: 250px;">
                <p class="text-muted">No Feedbacks Found</p>
            </div>
        `;
		return;
	}

	feedbacks.forEach((feedback, index) => {
		const feedbackCard = document.createElement('div');
		feedbackCard.classList.add('card', 'feedback-card');
		feedbackCard.innerHTML = `
            <div class="feedback-card-header" id="heading${index}" data-bs-toggle="collapse" data-bs-target="#collapse${index}" aria-expanded="false" aria-controls="collapse${index}">
                <div>
                    <h5>${feedback.requisitionName}</h5>
                    <p class="feedback-info">${feedback.roundName} | Date: ${feedback.createdOn} | Time: ${feedback.createdTime}</p>
                </div>
                <span>Click to Expand</span>
            </div>
            <div id="collapse${index}" class="collapse" aria-labelledby="heading${index}" data-bs-parent="#feedbacks-container">
                <div class="feedback-card-body">
                    <div class="mt-2">
                        <div class="card border rounded interview-card mb-2">
                            <div class="d-flex align-items-center gap-2 mb-4">
                                <!-- Interview Icon -->
                                <div class="icon-container mr-3">
                                    <i class="fa fa-suitcase fa-3x main-clr"></i>
                                </div>
                                <div>
                                    <h6 class="fw-bold inv-sec-header mb-1">Interview Round Details</h6>
                                   <p class="mb-0 text-muted fs-11">Here is the feedback you provided regarding the candidate's performance in the ${feedback.roundName}:</p>
                                </div>
                            </div>

                            <!-- Interview Details -->
                            <div class="row">
                                <div class="col-md-6">
                                    <p class="mb-2"><span class="fw-500">Round:</span> <span class="text-dark">${feedback.roundName}</span></p>
                                    <p class="mb-2"><span class="fw-500">Interviewer:</span> <span class="text-dark">${feedback.interviewerName}</span></p>
                                    
                                </div>
                                <div class="col-md-6">
                                    <!-- Job Details -->
                                    <p class="mb-2"><span class="fw-500">Job Title:</span> <span class="text-dark">${feedback.requisitionName}</span></p>
                                    <p class="mb-2"><span class="fw-500">Feedback Date:</span> <span class="text-dark">${feedback.createdOn} ${feedback.createdTime}</span></p>
                                    <!-- <p class="mb-2"><span class="fw-500">Location:</span> <span class="text-dark">New York, NY</span></p>
                                    <p class="mb-2"><span class="fw-500">Salary Range:</span> <span class="text-dark">$75,000 - $95,000</span></p>-->
                                </div>
                            </div>
                        </div>

                        <!-- Skills Rating Section -->
                        <div class="card border rounded interview-card mb-2">
                            <h6 class="fw-bold mb-4 inv-sec-header">Skills Assessment</h6>
                            <div class="g-3 skills-container" id="skillsRatings${index}">
                                <!-- Skills will be dynamically inserted here -->
                            </div>
                        </div>

                        <!-- Comments Section -->
                        <div class="card border rounded interview-card mb-2">
                            <h6 class="fw-bold mb-3 inv-sec-header">Feedback Comments</h6>
                            <textarea class="form-control comments-box" rows="4" readonly>${feedback.summary}</textarea>
                        </div>

                        <!-- Recommendation Section -->
                        <div class="card border rounded interview-card mb-2">
                            <div class="d-flex gap-4">
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="radio" name="recommendation${index}" id="stronglyRecommended${index}" value="strongly" ${feedback.recommendation === 'Strongly Recommended' ? 'checked' : ''} disabled>
                                    <label class="form-check-label inv-sec-header" for="stronglyRecommended${index}">Strongly Recommended</label>
                                </div>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="radio" name="recommendation${index}" id="recommended${index}" value="recommended" ${feedback.recommendation === 'Recommended' ? 'checked' : ''} disabled>
                                    <label class="form-check-label inv-sec-header" for="recommended${index}">Recommended</label>
                                </div>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="radio" name="recommendation${index}" id="notRecommended${index}" value="notRecommended" ${feedback.recommendation === 'Not Recommended' ? 'checked' : ''} disabled>
                                    <label class="form-check-label inv-sec-header" for="notRecommended${index}">Not Recommended</label>
                                </div>
                                 <div class="form-check form-switch">
							        <input class="form-check-input" type="radio" name="recommendation${index}" id="maybe${index}" value="maybe" ${feedback.recommendation === 'Maybe' ? 'checked' : ''} disabled >
							        <label class="form-check-label inv-sec-header" for="maybe">Maybe</label>
							    </div>
							    <div class="form-check form-switch">
							        <input class="form-check-input" type="radio" name="recommendation${index}" id="noOpinion${index}" value="noOpinion"  ${feedback.recommendation === 'No Opinion' ? 'checked' : ''} disabled>
							        <label class="form-check-label inv-sec-header" for="noOpinion">No Opinion</label>
							    </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

		// Add skill ratings dynamically for each feedback
		const skillsContainer = feedbackCard.querySelector(`#skillsRatings${index}`);
		feedback.skillsRatings.forEach(skill => {
			const skillElement = document.createElement('div');
			skillElement.classList.add('row', 'mb-2', 'align-items-center');
			skillElement.innerHTML = `
                <div class="col-md-3">
                    <label class="sub-header-new mb-0">${skill.skillName}</label>
                </div>
                <div class="col-md-9 interview-rating text-end">
                    ${[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map(rating => `
                        <input type="radio" name="skill${index}_${skill.skillId}" value="${rating}" ${rating == skill.rating ? 'checked' : ''} disabled><label>${rating}</label>
                    `).join('')}
                </div>
            `;
			skillsContainer.appendChild(skillElement);
		});

		// Append the feedback card to the container
		feedbacksContainer.appendChild(feedbackCard);
	});
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}