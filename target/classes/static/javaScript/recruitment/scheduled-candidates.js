$(document).ready(function() {
	var gridDivScheduled = document.querySelector('#myGridSchedule');
	new agGrid.Grid(gridDivScheduled, gridOptionsScheduled);

	getscheduledInterviewsList();

	$("#interviewerSelect").val('').trigger('chosen:updated');

	$(".chosen-select").chosen();
	$('#interviewerSelect').chosen();

	gridOptionsScheduled.api.setRowData([]);

});


$(document).ready(function() {
	let flag = false;
	document.getElementById('goBtn').addEventListener('click', function() {
		flag = true;
		quickFilterGrid(gridOptionsScheduled);
	});

	document.getElementById('resetBtn').addEventListener('click', function() {
		document.getElementById('quickFilter').value = '';
		gridOptionsScheduled.api.setQuickFilter('');
		flag = false;
		$(".loader").show();
		selectFirstRow(gridOptionsScheduled);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function(event) {
		const inputValue = this.value.trim();

		if (event.key === 'Enter' && inputValue !== '') {
			flag = true;
			quickFilterGrid(gridOptionsScheduled);
		} else if (event.key === 'Backspace' && flag) {
			quickFilterGrid(gridOptionsScheduled);
		}

	});
});


var columnDefsScheduled = [{
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
		return '<a class="edit-css" >' + params.data.candidateId + '</a>';

	},
}, {
	headerName: "Requisition Id",
	field: "requisitionId",
	pinned: 'left',
	hide: true,
	cellRenderer: function(params) {
		return '<a class="edit-css" >' + params.data.requisitionId + '</a>';

	},
}, {
	headerName: "Candidate Name",
	field: "candidateName",
	width: 150,
	cellRenderer: function(params) {
		const name = params.value || '';
		const createdOn = params.data.createdOn || '';

		// Calculate the difference between today and createdOn
		const today = new Date();
		const createdDate = new Date(createdOn);
		const timeDiff = today - createdDate;
		const daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); // Convert ms to days

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
			badgeText = 'Old';
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
},/*{
    headerName: "ResumeMatch %",
    field: "resumeMatchScore",
    cellRenderer: function(params) {
        if (params.value == null || params.value === undefined) return '';
        
        const score = Math.min(100, Math.max(0, Math.round(params.value)));
        const color = score >= 80 ? '#34a853' : 
                      score >= 60 ? '#FFC107' : 
                      score >= 40 ? '#FF9800' : '#F44336';
        
        return `
            <div style="width: 100%; height: 18px; background: #ececec; border-radius: 10px; overflow: hidden; margin-top: 5px;position: relative;">
                <div style="width: ${score}%; height: 100%; background: ${color};">
                    <span style="position: absolute; width: 100%; text-align: center; line-height: 19px; color: white; font-size: 10px; font-weight: bold;">
                        ${score}%
                    </span>
                </div>
            </div>
        `;
    },
    width: 130
},*/  	{
	headerName: "Resume Status",
	field: "resumeStatus",
	width: 120,
	cellRenderer: function(params) {
		const status = params.value || '';
		const isShortlisted = status.toLowerCase() === 'shortlisted';

		if (isShortlisted) {
			return `
	                <div style="
	                    display: flex;
	                    align-items: center;
	                    justify-content: center;
	                    height: 100%;
	                ">
	                    <span style="
						    background: rgba(52, 168, 83, 0.2);
						    color: #34a853;
						    font-size: 10px;
						    padding: 4px 8px;
						    border-radius: 12px;
						    display: inline-flex;
						    align-items: center;
						    backdrop-filter: blur(2px);
						    border: 1px solid #d6eedd;
						    font-weight: 500;
						    white-space: nowrap;
						    line-height: 1;
						    gap: 2px;
						
	                    ">
	                       <i class="fa fa-bolt" aria-hidden="true"></i> ${status}
	                    </span>
	                </div>
	            `;
		}
		return status;
	},
	cellStyle: {
		'display': 'flex',
		'align-items': 'center',
		'justify-content': 'center'
	}
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

}, {
	headerName: "Openings",
	field: "noOfPosition",
	hide: true,
	cellRenderer: function(params) {
		return params.data.requisitionDetails[0].noOfPosition

	},

},];

var gridOptionsScheduled = {
	columnDefs: columnDefsScheduled,
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
	var selectedNodes = gridOptionsScheduled.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const reqId = selectedData.map(node => node.requisitionId);
	const candidateId = selectedData.map(node => node.candidateId);
	const candidateName = selectedData.map(node => node.candidateName);
	var selectedRows = gridOptionsScheduled.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		getRequisitionRounds(reqId, candidateId);
		getFeedbackData(reqId, candidateId, "page-load", '');
		$("#jobRole").val(reqId);
		nextBtnFunction('condidateInterviewProgressTab');
		/*getCandidateOnRequisition(reqId, candidateId);*/
		viewJobDetails(reqId);
		clearFieldsValue();
		$(".candidateName").text(candidateName);
	} else {
		$("#jobRole").val("");
		$(".candidateName").text("");
	}
};

function getscheduledInterviewsList() {
	agGrid.simpleHttpRequest({
		url: "review-hiring-schedule-interviews",
	}).then(function(data) {
		if (data.body[0] != null && data.body[0] != "null") {
			var jsonData = JSON.parse(data.body);

			if (jsonData && Array.isArray(jsonData) && jsonData.length > 0) {
				jsonData.sort((a, b) => new Date(b.createdOn) - new Date(a.createdOn));

				$("#no-candTrackMsg").addClass("d-none");
				gridOptionsScheduled.api.setRowData(jsonData);
				if (jsonData.length > 0) {
					gridOptionsScheduled.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
			} else {
				gridOptionsScheduled.api.setRowData([]);
				$("#no-candTrackMsg").removeClass("d-none");
			}
		} else {
			gridOptionsScheduled.api.setRowData([]);
			$("#no-candTrackMsg").removeClass("d-none");
		}
	}).catch(function(error) {
		console.error('Error fetching interview schedule:', error);
	});
}


$(document).ready(function() {
	$('tbody tr').each(function() {
		var sum = 0;
		var count = 0;
		$(this).find('td.score').each(function() {
			var score = $(this).text().trim();
			if (!isNaN(score) && score !== "") {
				sum += parseInt(score);
				count++;
			}
		});

		var average = count > 0 ? (sum / count).toFixed(2) : 0;
		$(this).find('.average').text(average);
	});

	var totalSum = 0;
	var skillCount = 0;
	$('tbody tr').each(function() {
		var skillAverage = $(this).find('.average').text();
		if (!isNaN(skillAverage) && skillAverage !== "") {
			totalSum += parseFloat(skillAverage);
			skillCount++;
		}
	});

	var overallAverage = skillCount > 0 ? (totalSum / skillCount).toFixed(2) : 0;

	$('tfoot tr th.overall-summary').text(overallAverage);

	/* popover*/
	var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
	var popoverList = popoverTriggerList.map(function(popoverTriggerEl) {
		return new bootstrap.Popover(popoverTriggerEl)
	});

});

$(document).ready(function() {
	$(".feedback").each(function() {
		let total = 0;
		let count = 0;

		$(this)
			.find(".rating")
			.each(function() {
				let ratingValue = parseFloat($(this).text());
				if (!isNaN(ratingValue)) {
					total += ratingValue;
					count++;
				}
			});

		let average = count > 0 ? (total / count).toFixed(2) : 0;

		let fullStars = Math.floor(average);
		let halfStar = (average % 1) >= 0.5 ? 1 : 0;
		let emptyStars = 5 - fullStars - halfStar;
		let starRating = '';

		for (let i = 0; i < fullStars; i++) {
			starRating += '<span class="fa fa-star checked"></span>';
		}

		if (halfStar) {
			starRating += '<span class="fa fa-star-half-alt checked"></span>';
		}

		for (let i = 0; i < emptyStars; i++) {
			starRating += '<span class="fa fa-star"></span>';
		}

		$(".avgRating").text(average);
		$(this).find(".feedback-header").append(`<p class="average-rating">Average Rating: ${average} ${starRating}</p>`);
	});

	/*$(".toggle-btn").on("click", function () {
		$(this).closest(".feedback").find(".feedback-details").slideToggle();
		$(this).text($(this).text() === "+" ? "-" : "+");
	});
    
	$(".round-toggle-btn").on("click", function () {
		const $icon = $(this).find("i");
		$(this).closest(".round").find(".feedback-list").slideToggle();
		$icon.toggleClass("fa-caret-square-o-up fa-caret-square-o-down ");
	});*/

	// Toggle feedback details
	$(document).on("click", ".toggle-btn", function() {
		const $feedbackDetails = $(this).closest(".feedback").find(".feedback-details");
		$feedbackDetails.slideToggle(); // Toggle the visibility of feedback details
		$(this).text($(this).text() === "+" ? "-" : "+"); // Update the button text
	});

	// Toggle feedback list for a round
	$(document).on("click", ".round-toggle-btn", function() {
		const $icon = $(this).find("i");
		const $feedbackList = $(this).closest(".round").find(".feedback-list");
		$feedbackList.slideToggle(); // Toggle the visibility of the feedback list
		$icon.toggleClass("fa-caret-square-o-down fa-caret-square-o-up"); // Toggle the icon class
	});


	$("[role='tab']").click(function(e) {
		e.preventDefault();
		$(this).attr("aria-selected", "true");
		$(this).parent().siblings().children().attr("aria-selected", "false");
		var tabpanelShow = $(this).attr("href");
		$(tabpanelShow).attr("aria-hidden", "false");
		$(tabpanelShow).siblings().attr("aria-hidden", "true");
	});

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		minDate: 0,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})


	$("#toTimeCalander").datetimepicker({
		format: 'h:i A',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 1,
		formatTime: 'h:i A'
	}).on("change", function() {
		$('#toTime').val($(this).val());
	})
	$("#fromTimeCalander").datetimepicker({
		format: 'h:i A',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 1,
		formatTime: 'h:i A'
	}).on("change", function() {
		$('#fromTime').val($(this).val());
	})

	//joining date
	$("#joiningdateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#joiningdate').val($(this).val());
	})

	$('#joiningdate').blur(function() {
		$("#joiningdateCalendar").val($(this).val());
	})


});

function checkValidation() {
	const fromTimeStr = $('#fromTime').val();
	const toTimeStr = $('#toTime').val();

	if (fromTimeStr && toTimeStr) {
		// Parse times
		const fromTime = new Date(`1970-01-01T${convertTo24Hour(fromTimeStr)}:00`);
		const toTime = new Date(`1970-01-01T${convertTo24Hour(toTimeStr)}:00`);

		if (fromTime >= toTime) {
			toastr.error("From Time must be earlier than To Time.");

			$('#fromTime').val('');
			$('#toTime').val('');
			$('#totalDuration').val('');
		} else {
			const durationInMinutes = (toTime - fromTime) / (1000 * 60);
			const hours = Math.floor(durationInMinutes / 60);
			const minutes = durationInMinutes % 60;
			$('#totalDuration').val(`${hours} hr ${minutes} min`);
		}
	}
}

function convertTo24Hour(timeStr) {
	const [time, modifier] = timeStr.split(' ');
	let [hours, minutes] = time.split(':');
	if (modifier === 'PM' && hours !== '12') {
		hours = parseInt(hours, 10) + 12;
	} else if (modifier === 'AM' && hours === '12') {
		hours = '00';
	}
	return `${hours}:${minutes}`;
}

/*$(document).on('click', '.hr-nav-tabs.wizard li', function () {
	const clickedIndex = $(this).index();
	$('.hr-nav-tabs.wizard li').each(function (index) {
		if (index < clickedIndex) {
			$(this).removeClass('active skipped').addClass('completed');
		} else if (index > clickedIndex) {
			$(this).removeClass('active completed').addClass('skipped');
		} else {
			$(this).removeClass('completed skipped').addClass('active');
		}
	});
    
});*/

$(document).on('click', '.custom-tab-item a', function() {
	$('.custom-tab-item a').attr('aria-selected', 'false');
	$(this).attr('aria-selected', 'true');
});



let isProgrammaticNavigation = false;
let validRoundsCount = 0;
function getRequisitionRounds(requisitionId, candidateId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-rounds-list?requisitionId=" + requisitionId + "&candId=" + candidateId,
	}).then(function(response) {
		if (response.code === "Success") {
			console.log("roundList" + response.body[0]);
			const wizardContainer = document.querySelector(".wizard");
			if (response.body[0] == null) {
				wizardContainer.innerHTML = `<div class="container" id="error-msg" >
										    <div class="d-flex justify-content-center align-items-center" style="height: 341px;">
										        <p class="text-muted not-found">No Interview Rounds Added For This Candidate</p>
										    </div>
										</div>`;
				$("#interviewed").css("display", "none");
				$("#candidateStatusMsg").css("display", "none");
				$("#scheduleBttnPrevious").addClass("d-none");
				$("#scheduleBttnSelected").addClass("d-none");
				$("#interviewScheduleForm").addClass("d-none");
			} else {
				$("#scheduleBttnPrevious").removeClass("d-none");
				$("#scheduleBttnSelected").removeClass("d-none");
				$("#interviewScheduleForm").removeClass("d-none");
				const roundList = JSON.parse(response.body[0]);
				roundList.sort((a, b) => parseInt(a.roundOrder) - parseInt(b.roundOrder));
				/* const tabListContainer = document.querySelector(".custom-tablist-scroll ul");*/

				wizardContainer.innerHTML = "";
				/* tabListContainer.innerHTML = "";*/
				validRoundsCount = 0;
				let hasInterviewStatus = false;
				roundList.forEach((round, index) => {
					const li = document.createElement("li");

					if ((round.interviewStatus !== null && round.interviewStatus !== '') || (round.roundStatus !== null && round.roundStatus !== '')) {
						validRoundsCount++;
						hasInterviewStatus = true;
					}

					if (index === roundList.length - 1) {
						li.classList.add("special-last-round");
					}

					const a = document.createElement("a");
					a.href = `${round.roundId}`;
					a.setAttribute("data-toggle", "tab");
					a.setAttribute("aria-expanded", index === 0 ? "true" : "false");

					a.addEventListener("click", (event) => {
						if (!isProgrammaticNavigation) {
							event.preventDefault();
							// Determine active index when user clicks manually
							//handleRoundClick(round, requisitionId);
							handlePreviousButtonState(index);
						} else {
							isProgrammaticNavigation = false;
							handleRoundClick(round, requisitionId);
							handlePreviousButtonState(index);
						}
					});

					a.textContent = round.title;
					li.appendChild(a);
					wizardContainer.appendChild(li);
				});

				if (!hasInterviewStatus && wizardContainer.children.length > 0) {
					const firstLi = wizardContainer.children[0];
					firstLi.classList.add("active");
					handleRoundClick(roundList[0], requisitionId);
					handlePreviousButtonState(0); // default to first round
				}

				// If no interviewStatus exists, make the first round active
				/*if (!hasInterviewStatus && wizardContainer.children.length > 0) {
					wizardContainer.children[0].classList.add("active");
					handleRoundClick(roundList[0], requisitionId);
				}*/
				for (let i = 0; i < validRoundsCount; i++) {
					filterRounds('NEXT');
				}
			}
		} else {
			console.log("Failed to fetch data");
		}
	});
}

function handlePreviousButtonState(index) {
	if (index === 0) {
		if (validRoundsCount == 0) {
			$("#scheduleBttnPrevious").attr("disabled", true);
		} else {
			$("#scheduleBttnPrevious").attr("disabled", false);
		}
	} else {
		$("#scheduleBttnPrevious").attr("disabled", false);
	}
}


function feedbackDetails(roundId) {
	alert(roundId)
}

// Function to handle round click
function handleRoundClick(round, requisitionId) {
	var selectedRows = gridOptionsScheduled.api.getSelectedRows();
	var candidateId = selectedRows[0].candidateId;
	var candidateName = selectedRows[0].candidateName;
	var candidateEmail = selectedRows[0].email;


	$("#roundId").val(round.roundId);
	$("#candidateId").val(candidateId);
	$("#requisitionId").val(requisitionId);
	getInterviewRoundsDetails(round.roundId, requisitionId, candidateId, candidateName, candidateEmail);

	setInterviewRoundsDetails(round);

}

$(document).ready(function() {
	$('#secondCandidate').chosen();

	$('#secondCandidate').on('change', function() {
		const selectedCandidates = $(this).val();
		$('#candidateHiddenId').val(selectedCandidates ? selectedCandidates.join(',') : '');
	});
});

/*function getCandidateOnRequisition(reqId, candidateId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-candidates-list?requisitionId=" + reqId,
	}).then(function(response) {
		var firstSelectElement = document.getElementById("firstCandidate");
		var secondSelectElement = document.getElementById("secondCandidate");

		firstSelectElement.innerHTML = '';
		secondSelectElement.innerHTML = '';

		firstSelectElement.innerHTML = '<option value="" selected disabled>Select Candidate</option>';
		secondSelectElement.innerHTML = '<option value="" selected disabled>Select Candidate</option>';

		response.forEach(function(candidate) {
			var firstOption = document.createElement("option");
			firstOption.value = candidate.key;
			firstOption.textContent = candidate.name;

			if (candidate.key == candidateId) {
				firstOption.selected = true;
			}

			firstSelectElement.appendChild(firstOption);

			var secondOption = document.createElement("option");
			secondOption.value = candidate.key;
			secondOption.textContent = candidate.name;

			if (candidate.key == candidateId) {
				secondOption.disabled = true;
			}
			secondSelectElement.appendChild(secondOption);
		});

		firstSelectElement.disabled = false;
		$('#secondCandidate').trigger('chosen:updated');
		$('#firstCandidate').attr('disabled', true);
	});
}*/

function setInterviewRoundsDetails(round) {
	var selectedRows = gridOptionsScheduled.api.getSelectedRows();
	var candidateEmail = selectedRows[0].email;

	$("#title").val(round.title).attr("disabled", true);
	$("#description").val(round.description).attr("disabled", true);
	$("#candidateEmail").val(candidateEmail).attr("disabled", true);

	const $interviewerSelect = $("#interviewerSelect");
	$interviewerSelect.empty();
	let interviewers = [];
	try {
		interviewers = JSON.parse(round.interviewers);
	} catch (error) {
		console.error("Failed to parse interviewers data:", error);
	}
	interviewers.forEach(interviewer => {
		const option = `<option value="${interviewer.id}" id="${interviewer.id}" 
            ${interviewer.email ? `email="${interviewer.email}"` : ''}>${interviewer.name}</option>`;
		$interviewerSelect.append(option);
	});
	$interviewerSelect.trigger("chosen:updated");
	$("#interviewerSelect").val('').trigger('chosen:updated');

	$(".chosen-select").chosen();
	$('#interviewerSelect').chosen();
	$interviewerSelect.on('change', function() {
		let selectedIds = [];
		let selectedEmails = [];

		$(this).find('option:selected').each(function() {
			const selectedId = $(this).val();
			const selectedEmail = $(this).attr('email');

			selectedIds.push(selectedId);
			selectedEmails.push(selectedEmail);
		});
		$("#interviewerHiddenId").val(selectedIds.join(","));
		$("#interviewerEmail").val(selectedEmails.join(",")).attr("disabled", true);;
	});


}

$(document).ready(function() {
	$(".custom-tab-item .custom-tab-link").on("click", function(e) {
		e.preventDefault(); // Prevent default anchor behavior

		// Remove active state from all tabs
		$(".custom-tab-item").removeClass("active");
		$(".custom-tab-panel").attr("aria-hidden", "true").hide();

		// Add active state to the clicked tab
		$(this).parent(".custom-tab-item").addClass("active");

		// Show the corresponding tab panel
		const target = $(this).attr("href").substring(1); // Get target panel ID
		$(`#${target}`).attr("aria-hidden", "false").show();
	});


	$("#rejectCandidate").on("click", function() {
		candidateStatusChange('3');
	});

	$("#selectCandidate").on("click", function() {
		candidateStatusChange('1');
	});

	$("#holdCandidate").on("click", function() {
		candidateStatusChange('2');
	});

});

function getInterviewRoundsDetails(roundId, requisitionId, candidateId, candidateName, candidateEmail) {
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-rounds-deails?roundId=" + roundId + "&requisitionId=" + requisitionId + "&candidateId=" + candidateId,
	}).then(function(response) {
		$(".loader").hide();

		if (response.code === "Success") {
			const roundDetailsArray = JSON.parse(response.body[0]);

			if (Array.isArray(roundDetailsArray) && roundDetailsArray.length > 0 && roundDetailsArray[0] !== null) {
				const roundDetails = roundDetailsArray[0];
				$("#interviewScheduleForm").addClass('hidden');
				$("#interviewed").css("display", "block");
				$("#candidateName").text(candidateName);
				$("#candidateInvEmail").text(candidateEmail);

				$("#roundTitle").text(roundDetails.round_Title);
				$("#roundSummary").text(roundDetails.summary);
				$("#roundDate").text(roundDetails.formatted_date);
				$("#invTimes").text(roundDetails.fromTime + "-" + roundDetails.toTime);
				$("#invDates").text(roundDetails.formatted_date);
				$("#invDuration").text(roundDetails.duration);
				$("#scheduleInterviewBtn").hide();
				let statusColor = "";
				if (roundDetails.interview_status === "scheduled" || roundDetails.interview_status === "postpone") {
					statusColor = "#f2b601";
					$("#candidateStatusMsg").css("display", "none");
					updateButtonStates({ reject: true, select: true, hold: true, schedule: true });
				} else if (roundDetails.interview_status === "completed") {
					statusColor = "#20a600";
					$("#candidateStatusMsg").css("display", "none");
					$("#systemMsg").text("");
					$("#candInvStatus").text("");

					$("#candidateStatusMsg").removeClass("alert-success alert-warning alert-danger alert-info");
					if (roundDetails.round_status === "Pending") {
						$("#systemMsg").text("The candidate has been interviewed. No decision has been made yet; the status is ");
						$("#candInvStatus").text(roundDetails.round_status);
						$("#candidateStatusMsg").addClass("alert-warning").css("display", "block");
						updateButtonStates({ reject: false, select: false, hold: false, schedule: true });
					} else if (roundDetails.round_status === "Shortlist") {
						$("#systemMsg").html(`Candidate has been shortlisted by <strong>${roundDetails.status_updateBy}</strong> on <strong>${roundDetails.status_updatedOn}</strong> for the next round.`);
						$("#candidateStatusMsg").addClass("alert-success").css("display", "block");
						updateButtonStates({ reject: true, select: true, hold: true, schedule: false });
					} else if (roundDetails.round_status === "Hold") {
						$("#systemMsg").html(`Candidate has been placed on hold by <strong>${roundDetails.status_updateBy}</strong> on <strong>${roundDetails.status_updatedOn}</strong>. Final decision will be communicated soon.`);
						$("#candidateStatusMsg").addClass("alert-info").css("display", "block");
						updateButtonStates({ reject: false, select: false, hold: true, schedule: false });
					} else if (roundDetails.round_status === "Reject") {
						$("#systemMsg").html(`Candidate has been rejected by <strong>${roundDetails.status_updateBy}</strong> on <strong>${roundDetails.status_updatedOn}</strong>. We appreciate their time and effort throughout the process.`);
						$("#candidateStatusMsg").addClass("alert-danger").css("display", "block");
						updateButtonStates({ reject: true, select: true, hold: true, schedule: true });
					}
					else {
						updateButtonStates({ reject: false, select: false, hold: false, schedule: false });
					}

				} else {
					$("#roundTitle, #roundStatus, .roundStatusSpan, #roundSummary, #roundDate, #invTimes, #invDates, #invDuration").text("");
					return;
				}
				$("#roundStatus").text(roundDetails.interview_status).attr("style", `background-color: ${statusColor} !important;`);
				$(".roundStatusSpan").text(roundDetails.interview_status);

				const interviewersNames = roundDetails.interviewers_Name.split(',');
				const interviewersIds = roundDetails.interviewer_Id.split(',');
				const interviewersEmails = roundDetails.interviewers_email.split(',');

				const interviewersContainer = $(".interviewer-names-container").empty();

				interviewersNames.forEach((name, index) => {
					const id = interviewersIds[index] || "";
					const email = interviewersEmails[index] || "";

					const interviewerHTML = `
                        <span class="interviewer-box" value="${id}" data-email="${email}">
                            ${name.trim()}
                        </span>`;

					interviewersContainer.append(interviewerHTML);
				});
			} else {
				$("#candidateStatusMsg").css("display", "none");
				$("#systemMsg").text("");
				$("#candInvStatus").text("");
				$("#candidateName").text("");
				$("#candidateInvEmail").text("");
				$("#roundTitle").text("");
				$("#roundSummary").text("");
				$("#roundDate").text("");
				$("#invTimes").text("");
				$("#invDates").text("");
				$("#invDuration").text("");
				$("#roundStatus").text("");
				$(".roundStatusSpan").text("");
				$("#interviewed").css("display", "none");
				$("#interviewScheduleForm").removeClass('hidden');
				$("#scheduleInterviewBtn").show();
				updateButtonStates({ reject: true, select: true, hold: true, schedule: true });
				console.log("No Data Found");
			}
		}
	});
}

function updateButtonStates(states) {
	$("#rejectCandidate").prop("disabled", states.reject);
	$("#selectCandidate").prop("disabled", states.select);
	$("#holdCandidate").prop("disabled", states.hold);
	$("#scheduleBttnSelected").prop("disabled", states.schedule);
}


function candidateStatusChange(status) {
	const roundId = $("#roundId").val();
	const candidateId = $("#candidateId").val();
	const requisitionId = $("#requisitionId").val();
	$.ajax({
		type: "GET",
		url: "review-hiring-update-rounds?candId=" + candidateId + "&reqId=" + requisitionId + "&status=" + status + "&roundId=" + roundId,
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				getRequisitionRounds(requisitionId, candidateId);
			}
		}
	});
}

function filterRounds(action) {
	const wizardItems = document.querySelectorAll(".hr-nav-tabs.wizard li");
	let currentIndex = -1;

	wizardItems.forEach((item, index) => {
		if (item.classList.contains("active")) {
			currentIndex = index;
		}
	});

	if (action === "NEXT" && currentIndex < wizardItems.length - 1) {
		const nextIndex = currentIndex + 1;

		isProgrammaticNavigation = true;

		wizardItems[nextIndex].querySelector("a").click();

		wizardItems.forEach((item, index) => {
			if (index < nextIndex) {
				item.classList.add("completed");
				item.classList.remove("active");
			} else if (index === nextIndex) {
				item.classList.add("active");
				item.classList.remove("completed");
			} else {
				item.classList.remove("completed", "active");
			}
		});
	} else if (action === "PREVIOUS" && currentIndex > 0) {
		const previousIndex = currentIndex - 1;

		isProgrammaticNavigation = true;
		wizardItems[previousIndex].querySelector("a").click();

		wizardItems.forEach((item, index) => {
			if (index < previousIndex) {
				item.classList.add("completed");
				item.classList.remove("active");
			} else if (index === previousIndex) {
				item.classList.add("active");
				item.classList.remove("completed");
			} else {
				item.classList.remove("completed", "active");
			}
		});
	}
}

function setCurrentRoundId() {
	const activeLi = document.querySelector(".hr-nav-tabs.wizard li.active a");

	if (activeLi) {
		const roundId = activeLi.getAttribute("href");

		$("#roundId").val(roundId);
	} else {
		console.log("No active round found");
	}
}

function interviewSchedule() {
	var obj = {};

	var selectedRows = gridOptionsScheduled.api.getSelectedRows();
	var candId = selectedRows[0].candidateId;
	var candidateName = selectedRows[0].candidateName;
	var requisitionId = selectedRows[0].requisitionId;

	obj.fromDate = $('#fromDate').val();
	obj.toDate = $('#toDate').val();
	obj.fromTime = $('#fromTime').val();
	obj.toTime = $('#toTime').val();
	obj.location = $('#location').val();
	obj.summary = $('#summary').val();
	obj.totalDuration = $('#totalDuration').val();
	obj.description = $('#description').val();
	obj.title = $("#title").val();
	obj.interviewer = $("#interviewerHiddenId").val();
	obj.candidateId = candId;
	obj.requisitionId = requisitionId;
	obj.email = $("#candidateEmail").val();
	obj.interviewerEmail = $("#interviewerEmail").val();
	obj.meetingURL = $("#interviewURL").val();
	obj.candidateName = candidateName;
	obj.locationName = $('#location').find(":selected").text();
	obj.roundId = $("#roundId").val();

	if (obj.interviewer == null || obj.interviewer == "") {
		toastr.error("Select At Least One Interviewer");
		return;
	}

	if ($('#location').val() == null || $('#location').val() == "") {
		toastr.error("Location Required");
		return;
	}

	if (obj.fromDate == null || obj.fromDate == "") {
		toastr.error("Interview Date Required");
		return;
	}
	if (obj.fromTime == null || obj.fromTime == "") {
		toastr.error("From Time Required");
		return;
	}
	if (obj.toTime == null || obj.toTime == "") {
		toastr.error("To Time Required");
		return;
	}

	if (obj.summary == null || obj.summary == "") {
		toastr.error("Summary Required");
		return;
	}
	if (obj.description == null || obj.description == "") {
		toastr.error("Description Required");
		return;
	}
	if (obj.title == null || obj.title == "") {
		toastr.error("Title Required");
		return;
	}

	if (obj.meetingURL == null || obj.meetingURL == "") {
		toastr.error("Meeting Url Required");
		return;
	}


	/*if (!validationUpdated("Candidate Email Can't Be Blank", 'candidateEmail'))
		allValid = false;
	
	if (!validationUpdated("Interviewer Email Can't Be Blank", 'interviewerEmail'))
		allValid = false;

	  
	 if(obj.email != null || obj.email != ""){ 
		   validationMailCandidate = validateEmailsCandidate(obj.email);
		   console.log('validationMailCandidate>',validationMailCandidate)
	   }
	   
	  
	 if(obj.interviewerEmail != null || obj.interviewerEmail != ""){ 
		   validationMailInterviewer = validateEmailsInterviewer(obj.interviewerEmail);
		   console.log('validationMailInterviewer>',validationMailInterviewer)
	   }*/

	$(".loader").show();
	$.ajax({
		type: "POST",
		url: "review-hiring-schedule-interview",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code == "success") {
				const roundId = $("#roundId").val();
				var selectedRows = gridOptionsScheduled.api.getSelectedRows();
				var candidateId = selectedRows[0].candidateId;
				var candidateName = selectedRows[0].candidateName;
				var candidateEmail = selectedRows[0].email;
				getInterviewRoundsDetails(roundId, requisitionId, candidateId, candidateName, candidateEmail);

				$(".loader").hide();
				toastr.success(response.message);
				$('#scheduleBttn').attr("disabled", true);

			} else {
				$(".loader").hide();
				toastr.success(response.message);
				$('#scheduleBttn').attr("disabled", true);
			}
		},
		error: function(data) {
		}
	});
}

function forwardNextRound() {
	$("#interviewScheduleForm").addClass("hidden");
	$(".skipRemark").removeClass("hidden");
	$("#scheduleInterviewBtn").attr("disabled", true);
}

function backToForm() {
	$(".skipRemark").addClass("hidden");
	$("#interviewScheduleForm").removeClass("hidden");
	$("#scheduleInterviewBtn").attr("disabled", false);
}

function getFeedbackData(reqId, candidateId, type, ratingFilter) {
	let callUrl = "hr";
	const url = `review-hiring-feedback-details?requiId=${reqId}&candId=${candidateId}&callUrl=${callUrl}`;

	agGrid.simpleHttpRequest({
		url: url
	}).then(function(data) {
		if (data.code == "success") {
			populateFeedbackData(data);
			candidateEvaluationMatrix(data);
			/*const averages = processComparisonData(data);
			populateComparisonTable(averages, ratingFilter);*/

		} else {
			console.warn("Something went wrong!");
		}
	});
}

function populateFeedbackData(data) {
	const mainContainer = document.querySelector("#custom-tabs-link-1");
	mainContainer.innerHTML = "";
	const feedbackArray = JSON.parse(data.body[0]);
	if (feedbackArray == "null" || feedbackArray == null) {
		mainContainer.innerHTML = `<div class="container" id="error-msg" >
						    <div class="d-flex justify-content-center align-items-center" style="height: 341px;">
						        <p class="text-muted not-found">No Feedback Found</p>
						    </div>
						</div>`;

	} else {
		const groupedFeedback = feedbackArray.reduce((acc, feedback) => {
			if (!acc[feedback.roundId]) {
				acc[feedback.roundId] = [];
			}
			acc[feedback.roundId].push(feedback);
			return acc;
		}, {});

		const mainContainer = document.querySelector("#custom-tabs-link-1");

		mainContainer.innerHTML = "";

		Object.entries(groupedFeedback).forEach(([roundId, feedbacks], roundIndex) => {
			const roundSection = document.createElement("div");
			roundSection.className = "interview-timeline";

			// Round Header
			const roundHeaderHTML = `
			 <div class="round p-2">
                <div class="round-header">
                    <div class="status-badge selected">${roundIndex + 1} </div>
					<div class="d-flex justify-content-between flex-grow-1">
                        <div class="round-info">
							<h3 class="round-title">
								${feedbacks[0].roundName} - Interview Feedback
							</h3>
							<p class="round-meta">
								<strong>Date:</strong> <span class="round-date">${feedbacks[0].createdOn}</span>
							</p>
							
                        </div>
						
						<button class="round-toggle-btn" data-target="round-${roundIndex + 1}-details">
								<i class="fa fa-caret-down" aria-hidden="true"></i>
						</button>
						

				    </div>
                </div>

                <div class="feedback-list" style="display: none;">`;

			const feedbackItemsHTML = feedbacks
				.map((feedback) => {
					const totalRating = feedback.skillsRatings.reduce((sum, skill) => {
						const ratingValue = parseFloat(skill.rating);

						return sum + ratingValue;
					}, 0);

					const averageRating = totalRating / feedback.skillsRatings.length;

					return `
                <div class="feedback">
                    <div class="feedback-header">
                        <h4>
                            ${feedback.interviewerName}
							<span class="badge ${getRecommendationClass(feedback.recommendation)}"> ${getRecommendationIcon(feedback.recommendation)}</span>
                            <span class="badge bg-secondary avgRating">${averageRating.toFixed(2)}</span>
                        </h4>
                        <p>Role: ${feedback.requisitionName}</p>
                        <button class="toggle-btn">+</button>
                    </div>
                    <div class="feedback-details" style="display: none;">
                        <div class="d-flex gap-2 flex-wrap">
                            ${feedback.skillsRatings
							.map(
								(rating) => `
                            <div class="feedback-item border rounded feedback-score">
                                <span class="skill-name" title="${rating.skillName}">${rating.skillName}</span>
                                <div class="rating good">${rating.rating}</div>
                            </div>`
							)
							.join("")}
                        </div>
                        <p class="comments">
                            <strong>Comment:</strong> ${feedback.summary || "No comments available"}
                        </p>
                    </div>
                </div>
            `;
				})
				.join("");

			const roundFooterHTML = `
                </div>
            </div>
        `;

			roundSection.innerHTML = roundHeaderHTML + feedbackItemsHTML + roundFooterHTML;

			mainContainer.appendChild(roundSection);
		});

		if (Object.keys(groupedFeedback).length === 0) {
			mainContainer.innerHTML = `
            <div class="feedback-list">
                <p class="comments">
                    <strong>No Feedback Found</strong>
                </p>
            </div>
        `;
		}

	}
}

function getRecommendationClass(recommendation) {
	switch (recommendation) {
		case "Strongly Recommended":
			return "badge-strongly-recommended";
		case "Recommended":
			return "badge-recommended";
		case "Maybe":
			return "badge-maybe";
		case "Not Recommended":
			return "badge-not-recommended";
		case "No Opinion":
			return "badge-no-opinion";
		default:
			return "badge-default";
	}
}

function getRecommendationIcon(recommendation) {
	switch (recommendation) {
		case "Strongly Recommended":
			return '<i class="fa fa-thumbs-up" title="Highly Recommended"></i>';
		case "Recommended":
			return '<i class="fa fa-thumbs-up" title="Recommended"></i>';
		case "Maybe":
			return '<i class="fa fa-question-circle" title="Considered"></i>';
		case "Not Recommended":
			return '<i class="fa fa-thumbs-down" title="Not Recommended"></i>';
		case "No Opinion":
			return '<i class="fa fa-meh" title="No Opinion"></i>';
		default:
			return '<i class="fa fa-question" title="No Opinion"></i>';
	}
}




function candidateEvaluationMatrix(data) {
	const feedback = JSON.parse(data.body[0]);
	const table = document.querySelector(".evaluation-table");
	const thead = table.querySelector("thead");
	const tbody = table.querySelector("tbody");
	const tfoot = table.querySelector("tfoot");

	thead.innerHTML = "";
	tbody.innerHTML = "";
	tfoot.innerHTML = "";
	const errmsg = document.querySelector(".no-data-msg");
	errmsg.innerHTML = "";
	if (feedback == "null" || feedback == null) {

		errmsg.innerHTML = `<div class="container" id="error-msg" >
	        <div class="d-flex justify-content-center align-items-center" style="height: 341px;">
	            <p class="text-muted not-found">No Feedback Available Yet</p>
	        </div>
	    </div>`;

		$(".evaluation-table-wrapper").addClass('d-none');
	}

	else {
		$(".evaluation-table-wrapper").removeClass('d-none')

		const skills = [...new Set(feedback.flatMap(round => round.skillsRatings.map(skill => skill.skillName)))];
		const rounds = [...new Set(feedback.map(round => round.roundName))];

		const selfRatingsMap = {};
		feedback.forEach(round => {
			round.skillsReqRatings.forEach(reqSkill => {
				selfRatingsMap[reqSkill.reqSkillId] = reqSkill.reqScore;
			});
		});

		const headerRow1 = document.createElement("tr");
		const headerRow2 = document.createElement("tr");
		const headerRow3 = document.createElement("tr");

		headerRow1.innerHTML = `
        <th rowspan="3" class="fixed tbl-fix">Skills</th>
        <th rowspan="3" class="fixed tbl-fix stick-col-two">Required Rating</th>
    `;

		const interviewerMap = {};
		rounds.forEach(round => {
			const roundFeedback = feedback.filter(f => f.roundName === round);
			const interviewers = [...new Set(roundFeedback.map(r => r.interviewerName))];
			interviewerMap[round] = interviewers;
			const roundColumn = document.createElement("th");
			roundColumn.setAttribute("colspan", interviewers.length);
			roundColumn.textContent = round;
			headerRow1.appendChild(roundColumn);

			interviewers.forEach(interviewer => {
				const interviewerColumn = document.createElement("th");
				interviewerColumn.textContent = interviewer;
				headerRow2.appendChild(interviewerColumn);

				const scoreColumn = document.createElement("th");
				scoreColumn.textContent = "Score";
				headerRow3.appendChild(scoreColumn);
			});
		});

		headerRow1.innerHTML += `<th rowspan="3" class="tbl-fix stick-col">Average</th>`;
		thead.appendChild(headerRow1);
		thead.appendChild(headerRow2);
		thead.appendChild(headerRow3);

		skills.forEach(skill => {
			const row = document.createElement("tr");
			const skillCell = document.createElement("td");
			skillCell.classList.add("fixed", "tbl-fix");
			skillCell.textContent = skill;
			row.appendChild(skillCell);

			const skillId = feedback[0].skillsRatings.find(s => s.skillName === skill)?.skillId;
			const selfRating = selfRatingsMap[skillId] || "-";
			const selfRatingCell = document.createElement("td");
			selfRatingCell.classList.add("fixed", "tbl-fix", "stick-col-two");
			selfRatingCell.textContent = selfRating;
			row.appendChild(selfRatingCell);

			let totalRating = 0;
			let count = 0;

			rounds.forEach(round => {
				const roundFeedback = feedback.filter(f => f.roundName === round);
				const interviewers = interviewerMap[round];

				interviewers.forEach(interviewer => {
					const interviewerFeedback = roundFeedback.find(r => r.interviewerName === interviewer);
					const skillRating = interviewerFeedback?.skillsRatings.find(r => r.skillName === skill);
					const rating = skillRating ? skillRating.rating : "-";

					const scoreCell = document.createElement("td");
					scoreCell.classList.add("score");
					scoreCell.textContent = rating;
					row.appendChild(scoreCell);

					if (rating !== "-") {
						totalRating += parseInt(rating, 10);
						count++;
					}
				});
			});

			const averageCell = document.createElement("td");
			averageCell.classList.add("average");
			averageCell.textContent = count > 0 ? (totalRating / count).toFixed(2) : "-";
			row.appendChild(averageCell);

			tbody.appendChild(row);
		});

		const footerRow = document.createElement("tr");
		footerRow.innerHTML = `
        <th colspan="2" class="fixed tbl-fix">Summary</th>
    `;

		let totalAverage = 0;
		let totalCount = 0;

		rounds.forEach(round => {
			const roundFeedback = feedback.filter(f => f.roundName === round);
			const interviewers = interviewerMap[round];

			interviewers.forEach(interviewer => {
				const interviewerFeedback = roundFeedback.find(r => r.interviewerName === interviewer);
				const summaryCell = document.createElement("th");
				summaryCell.classList.add("interviewer-summary");

				if (interviewerFeedback) {
					const summary = interviewerFeedback.summary;
					summaryCell.setAttribute("title", summary);
					summaryCell.textContent = summary.length > 30 ? `${summary.slice(0, 30)}...` : summary;
				}

				footerRow.appendChild(summaryCell);
			});

			roundFeedback.forEach(f => {
				f.skillsRatings.forEach(skill => {
					if (!isNaN(skill.rating)) {
						totalAverage += parseFloat(skill.rating);
						totalCount++;
					}
				});
			});
		});

		const totalAverageCell = document.createElement("th");
		totalAverageCell.classList.add("overall-summary", "tbl-fix", "stick-col");
		totalAverageCell.textContent = totalCount > 0 ? (totalAverage / totalCount).toFixed(2) : "-";
		footerRow.appendChild(totalAverageCell);

		tfoot.appendChild(footerRow);
	}
}




function applyCandidateFilters() {

	const reqId = $("#jobRole").val();
	const firstCandidate = $("#firstCandidate").val();
	let candidateHiddenId = $("#candidateHiddenId").val();
	const ratingFilter = $("#jobRatings").val();
	if (!candidateHiddenId) {
		alert("No candidate selected. Please select a candidate before proceeding.");
		resetCandidateFilters();
		return;
	}

	candidateHiddenId += `,${firstCandidate}`;

	getFeedbackData(reqId, candidateHiddenId, "comparision-tab", ratingFilter);
}


function resetCandidateFilters() {
	const reqId = $("#jobRole").val();
	const firstCandidate = $("#firstCandidate").val();
	$("#secondCandidate").val("");
	$("#candidateHiddenId").val("");
	$("#jobRatings").val("");
	$('#secondCandidate').trigger('chosen:updated');
	getFeedbackData(reqId, firstCandidate, "page-load", '');
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

function clearFieldsValue() {
	$('#fromDate').val("");
	$('#toDate').val("");
	$('#fromTime').val("");
	$('#toTime').val("");
	$('#location').val("");
	$('#summary').val("");
	$('#totalDuration').val("");
	$('#description').val("");
	$("#title").val("");
	$("#interviewerHiddenId").val("");
	$("#candidateEmail").val("");
	$("#interviewerEmail").val("");
	$("#interviewURL").val("");
	$("#roundId").val("");
}