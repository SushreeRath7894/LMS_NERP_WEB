
function detailsSectionDiscuss(data) {
	console.log(data)
	const container = $('#discussionPointsContainer');
	container.empty();

	data.forEach((item, index) => {
		for (const key in item) {
			if (item.hasOwnProperty(key) && key.startsWith('point_')) {
				const value = item[key]; // Extract the discussion point content

				const newDiscussionPoint = $('<div>', { class: 'mb-3' });

				const input = $('<input>', {
					type: 'text',
					class: 'form-control',
					placeholder: `Discussion Point ${key.replace('point_', '')}`,
					value: value
				});

				newDiscussionPoint.append(input);
				container.append(newDiscussionPoint);
			}
		}
	});
}
function scheduleMeeting() {
	let currentTime = new Date();
	currentTime.setMinutes(currentTime.getMinutes() + 1);

	let startHours = String(currentTime.getHours()).padStart(2, '0');
	let startMinutes = String(currentTime.getMinutes()).padStart(2, '0');
	let endHours = String(currentTime.getHours() + 1).padStart(2, '0');


	let currentDate = new Date();
	let toDate = ('0' + currentDate.getDate()).slice(-2) + '-' + ('0' + (currentDate.getMonth() + 1)).slice(-2) + '-' + currentDate.getFullYear();
	//	let clickedDate = new Date(info.dateStr); 
	let todayDate = formatDate(toDate);
	// Open modal for new event
	openEventModal('', '', todayDate, startHours + ":" + startMinutes, false, '');
}

let meetingDate = "";
let meetingTime = "";
function getMeetingLists(meetingStatus) {
	$.ajax({
		url: 'meeting-calendar-view-meeting?meetingStatus=' + meetingStatus,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var parsedData = response.body ? JSON.parse(response.body) : [];
				var container = $("#getMeetingLists");
				container.empty();

				if (parsedData.length === 0) {
					$('#getMeetingLists').html('<div class="text-center" style="color: #6b2bbf;margin-top: 28px;"><img src="../assets/images/noMetingsFound.png" width="290" hight="240" style="margin-top: 4px;"><p style="margin-top: -28px;">No Completed Meeting Found</p></div>');
					return;
				}

				// Generate dynamic list for each meeting
				parsedData.forEach(function(meeting, index) {
					var meetingHosts = meeting.meetingHost.split(",").join(", ");
					var meetingStatus = meeting.meetingStatus === 0 ? 'OPEN' : 'COMPLETED';
					var meetingStatusClass = meetingStatus === 'OPEN' ? 'status-open' : 'status-completed';

					var div = `
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item" data-meeting-id="${meeting.meetingId}">
                            <div class="todo-indicator bg-warning"></div>
                            <div class="widget-content p-0">
                                <div class="widget-content-wrapper">
                                    <div class="mr-2">
                                        <div class="custom-checkbox custom-control" id="customCheckbox">
                                            <input class="custom-control-input meeting-checkbox"
                                                id="checkbox-${meeting.meetingId}" type="checkbox">
                                            <label class="custom-control-label"
                                                for="checkbox-${meeting.meetingId}">&nbsp;</label>
                                        </div>
                                    </div>
                                    <div class="widget-content-left">
                                        <div class="widget-heading">
                                            ${meeting.meetingName}
                                            <div class="badge badge-primary ml-2">${meeting.meetingMode}</div>
                                        </div>
                                        <div class="widget-subheading">
                                            <div>Hosted by: ${meetingHosts}</div>
                                            <div><strong>Frequency:</strong> <span class="px-2 custom-review-text ml-2 rounded frequency-bg">${meeting.meetingFrequency}</span> <span class="${meetingStatusClass}" style="margin-left: 10px;">${meetingStatus}</span></div>
                                            <br>
                                        </div>
                                    </div>
                                    <div class="widget-content-right">
                                        <button
                                            class="border-0 btn-transition btn btn-outline-primary"
                                            title="Edit" onclick="editMeetingDetails('${meeting.meetingId}')">
                                            <i class="fa fa-pencil-square" aria-hidden="true"></i>
                                        </button>
                                        <button
                                            class="border-0 btn-transition btn btn-outline-danger"
                                            title="Delete" onclick="deleteMeetingDetails('${meeting.meetingId}')">
                                            <i class="fa fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>`;

					container.append(div);
				});

				// Attach event delegation for dynamically created checkboxes
				attachCheckboxHandler();
			} else {
				console.error("Error fetching data");
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}

// Event delegation for dynamically created checkboxes
function attachCheckboxHandler() {
	$("#getMeetingLists").on("change", ".meeting-checkbox", function() {
		const isChecked = $(this).is(":checked");
		const meetingId = $(this).attr("id").split("-")[1];

		if (isChecked) {
			$(".meeting-checkbox").each(function() {
				if (this.id !== `checkbox-${meetingId}`) {
					$(this).prop("checked", false);
				}
			});
		} else {
			clearAllMeetingData();
		}
		editMeetingDetails(meetingId, isChecked);
	});
}
//
function uncheckCheckbox(meetingId) {
	const checkbox = $(`#checkbox-${meetingId}`);
	if (checkbox.length) {
		checkbox.prop('checked', false);
	}
}

// Format the date (DD-MM-YYYY to YYYY-MM-DD)
function formatDate(dateStr) {
	var dateParts = dateStr.split('-'); // Split DD-MM-YYYY
	return dateParts[2] + '-' + dateParts[1] + '-' + dateParts[0]; // Return YYYY-MM-DD
}

// Format the time (HH:mm:ss.SSSSSS to HH:mm)
function formatTime(timeStr) {
	return timeStr.split(':').slice(0, 2).join(':'); // Return HH:mm
}
function clearAllMeetingData() {
	// Clear all previously set data
	$('#overview .meeting-name-header').text('');
	$('#overview .custom-info').text('');
	$('#overview .location').next().text('');

	$('#meetingFrequency').val('');
	$('#startDate').val('');
	$('#startTime').val('');
	$('#endDate').val('');
	$('#endTime').val('');
	$('#meetingDays').val('');
	$('#agendaContent').html('<ul><li>No agenda available.</li></ul>');

	// Reset visibility of all fields
	$(".start-end-fields").removeClass('hidden');
	$(".meeting-until-field").addClass('hidden');
	$(".meeting-days-field").addClass('hidden');
}
function setMeetingDetailsTabAsDefault() {
	document.querySelectorAll('.nav-link').forEach((link) => {
		link.classList.remove('active');
		link.setAttribute('aria-selected', 'false');
		$("#editMeeting").show();
		$("#saveMeeting").hide();
		$("#saveScheduleDetails").hide();
		$("#prevTab").hide();
		$("#nextTab").show();
		$("#addMeeting").show();
		$("#cancelPostponeTab").hide();

	});

	const meetingDetailsTab = document.querySelector('#meetingDetails-tab .nav-link');
	meetingDetailsTab.classList.add('active');
	meetingDetailsTab.setAttribute('aria-selected', 'true');

	document.querySelectorAll('.tab-pane').forEach((tab) => {
		tab.classList.remove('show', 'active');
	});

	const meetingDetailsContent = document.querySelector('#meetingDetails');
	meetingDetailsContent.classList.add('show', 'active');
}
function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
}
function deleteMeetingDetails(meetingId) {
	var meetingStatus = $('#meetingTypeDropdown').val();

	$.ajax({
		type: "GET",
		url: "meeting-calendar-delete?meetingId=" + meetingId,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				calendar.refetchEvents();
				showSnackbar(response.message)
				getMeetingLists(meetingStatus);
			}

		},
		error: function(data) {
		}
	});
}

function checkMeetingForEdit() {
	$("#tabContent").find("input, select, textarea").prop("disabled", true);
	$("#documentInput").prop("disabled", false);
	$("#multiple8").prop("disabled", true).trigger("chosen:updated");
	$("#multiple9").prop("disabled", true).trigger("chosen:updated");
	$("#tabContent").find(".day").addClass("disabled");
	$("#addDiscuss").attr("disabled", true).addClass("disabled");
	$("#saveMeeting").prop("disabled", true);
	$(".required-discussion").prop("disabled", true);
	$("#monthlyDatePicker").addClass("disabled").find("button, .date-items").prop("disabled", true);
	$("#scroll-left").prop("disabled", true);
	$("#scroll-right").prop("disabled", true);
	$("#selected-dates").addClass("disabled").off("click");

}

function setDiscussionData(data) {
	const container = $('#discussContainer');
	container.empty();

	data.forEach((item, index) => {
		for (const key in item) {
			if (item.hasOwnProperty(key) && key.startsWith('point_')) {
				const value = item[key];

				const newDiscussionPoint = $('<div>', {
					class: 'input-group mb-2 diss'
				});

				const input = $('<input>', {
					type: 'text',
					class: 'form-control required-discussion',
					placeholder: `Discussion Point ${index + 1}. Add your detailed discussion point here.`,
					value: value
				});

				const removeButton = $('<button>', {
					class: 'btn btn-danger ml-2 discussion-btn-bg',
					html: '<i class="fa fa-times" aria-hidden="true"></i>',
					disabled: true,
					click: function() {
						newDiscussionPoint.remove();
						updateDiscussionPointNumbers();
					}
				});

				newDiscussionPoint.append(input);
				newDiscussionPoint.append(removeButton);

				container.append(newDiscussionPoint);
			}
		}
	});

	updateDiscussionPointNumbers();
}
/*function setDiscussionData(data) {
	const container = $('#discussContainer');
	container.empty();

	data.forEach((item, index) => {
		for (const key in item) {
			if (item.hasOwnProperty(key) && key.startsWith('point_')) {
				const value = item[key];

				const newDiscussionPoint = $('<div>', {
					class: 'input-group mb-2 diss'
				});

				const input = $('<input>', {
					type: 'text',
					class: 'form-control required-discussion',
					placeholder: `Discussion Point ${key.replace('point_', '')}`,
					value: value
				});

				newDiscussionPoint.append(input);
				container.append(newDiscussionPoint);
			}
		}
	});
}*/

function editMeetingDetails(meetingId) {
	$(".validation-error").remove();
	$('.discussion-error').remove();
	$("#hiddenMeetingId").val(meetingId);
	setMeetingDetailsTabAsDefault();
	$.ajax({
		url: 'meeting-calendar-edit-meeting?meetingId=' + meetingId,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var parsedBody = response.body;
				console.log("Meeting Data During Edit======>", parsedBody);
				var meetingTime = parsedBody[0].meetingTime;
				//var meetingDate = formatDate(parsedBody[0].meetingDate);
				checkMeetingForEdit();
				var [startHours, startMinutes] = meetingTime.split(":");
				// Call the `openEventModal` function with the formatted values
				//openEventModal('', '', meetingDate, startHours + ":" + startMinutes, false, '');
				$('#meetingId').val(parsedBody[0].meetingId);
				$('#meetingName').val(parsedBody[0].meetingName);
				// Set Meeting Host
				var meetingHostIds = parsedBody[0].meetingHost.split(",");
				$('#multiple8').val(meetingHostIds).trigger('chosen:updated');
				$('#toHiddenIdOrg').val(meetingHostIds.join(','));
				// Set Meeting Attendees
				var meetingAttendeesIds = parsedBody[0].meetingAttendees.split(",");
				$('#multiple9').val(meetingAttendeesIds).trigger('chosen:updated');
				$('#toHiddenIdAttendees').val(meetingAttendeesIds.join(','));
				// Remove selected hosts and attendees from dropdown options
				updateDisabledOptions();
				// Set the frequency dropdown value
				$('input[name="meetingMode"][value="' + parsedBody[0].meetingMode + '"]').prop('checked', true).trigger('change');
				$('#frequency').val(parsedBody[0].meetingFrequency).trigger('change');
				$('#meetingCrationTime').val(parsedBody[0].meetingTime ? formatTime(parsedBody[0].meetingTime) : '');
				$('#meetingCrationDate').val(parsedBody[0].meetingDate ? formatDate(parsedBody[0].meetingDate) : '');
				$('#meetingFromDate').val(parsedBody[0].fromDate ? formatDate(parsedBody[0].fromDate) : '');
				$('#meetingFromTime').val(parsedBody[0].fromTime ? formatTime(parsedBody[0].fromTime) : '');
				$('#meetingToDate').val(parsedBody[0].toDate ? formatDate(parsedBody[0].toDate) : '');
				$('#meetingToTime').val(parsedBody[0].toTime ? formatTime(parsedBody[0].toTime) : '');
				$('#repeatUntil').val(parsedBody[0].meetingUntil ? formatDate(parsedBody[0].meetingUntil) : '');
				var meetingDays = JSON.parse(parsedBody[0].meetingDays);
				meetingDays.forEach(day => {
					$('#weakSelect .day[data-day="' + day + '"]').addClass('selected');
				})
				$('#meetingLink').val(parsedBody[0].meetingUrl || '');
				$('#location').val(parsedBody[0].meetingLocation || '');
				$('#agenda').val(parsedBody[0].meetingAgenda || '');
				const discussionData = JSON.parse(parsedBody[0].discussionPoint);
				setDiscussionData(discussionData);
				const monthlyDatePicker = parsedBody[0].monthlyDates;
				console.log("Manoj======>", monthlyDatePicker);
				const selectedDates = monthlyDatePicker.split(",").map(date => parseInt(date.trim()));  

				const dateItemsContainer = document.querySelector(".date-items");

				if (dateItemsContainer) {
					const dateItems = dateItemsContainer.querySelectorAll(".date-item");

					dateItems.forEach(item => {
						const dateValue = parseInt(item.textContent.trim());  
						if (selectedDates.includes(dateValue)) {
							item.classList.add("selected");
						}
					});
				}

			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}
function cancelModal() {
	// Clear text input fields
	$(".validation-error").remove();
	$("input, select, textarea").prop("disabled", false);
	$("#multiple8_chosen").prop("disabled", false).trigger('chosen:updated');
	$("#multiple9_chosen").prop("disabled", false).trigger('chosen:updated');
	$('#multiple8 option, #multiple9 option').prop('disabled', false);
	$("#addDiscuss").attr("disabled", false).removeClass("disabled");
	$("#saveMeeting").prop("disabled", false);
	$(".day").removeClass("disabled");
	$('#meetingId').val('');
	$('#meetingName').val('');
	$('#meetingLink').val('');
	$('#location').val('');
	$('#agenda').val('');
	$('#meetingCrationDate').val('');
	$('#meetingCrationTime').val('');
	$('#meetingFromDate').val('');
	$('#meetingFromTime').val('');
	$('#meetingToDate').val('');
	$('#meetingToTime').val('');
	$('#repeatUntil').val('');
	$('#frequency').val('').trigger('change');
	$('input[name="meetingMode"]').prop('checked', false);
	$('#weakSelect .day').removeClass('selected');
	$('#multiple8').val([]).trigger('chosen:updated');
	$('#multiple9').val([]).trigger('chosen:updated');
	$('#discussContainer').empty();
	$('#toHiddenIdOrg').val('');
	$('#toHiddenIdAttendees').val('');
	$('#notesContent').val('');
	$('#eventModal').modal('hide');
	$("#onlineMode").prop("checked", true);
	toggleModeFields();
	var frequency =$("#frequency").val();
    toggleFrequencyFields(frequency);
}

function getVitalMeeting() {
	var currentDate = new Date().toISOString().split('T')[0];  // Format: YYYY-MM-DD
	var currentTime = new Date().toTimeString().split(' ')[0]; // Format: HH:mm:ss

	$.ajax({
		url: 'meeting-calendar-vital-meeting?currentDate=' + currentDate + '&currentTime=' + currentTime,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var $container = $('#yourContainer');
				var parsedBody = response.body;
				if (!parsedBody || parsedBody.length === 0) {
					// If no meeting data is found, show a message
					$container.html(`
                        <div class="text-center" style="margin-top: 6px;color: #6b2bbf;">
                        <img src="../assets/images/noRecentMeeting.png" width="240" hight="240">
                        </div>
                    `);
					return;
				}
				var meetingData = parsedBody[0];
				var attendeesDetails = parsedBody[0].attendeesDetails;
				var hostDetails = parsedBody[0].hostDetails;

				// Create and populate the meeting card dynamically
				var meetingCardHtml = `
                    <div class="card mb-2">
                        <div class="overflow-scroll-meeting">
                            <div class="p-4 custom-notes">
                                <div class="d-flex flex-row align-items-center custom-notes-title">
                                    <h4>${meetingData.meetingName}</h4>
                                    <span class="px-2 custom-review-text ml-2 rounded" id="showMeetingMode"></span>
                                    <span class="px-2 custom-review-text ml-2 rounded" id="meetingType">vital</span>
                                </div>
                                <div class="d-flex flex-row align-items-center align-content-center">
                                    <span class="custom-dot"></span>
                                    <span class="custom-info ml-1 meeting-mode" id="vitalMeetingMode"></span>
                                    <span class="location">
                                        <i class="fa fa-map-marker" aria-hidden="true" style="display: none;"></i>
                                        <i class="bi bi-link" style="display: none;"></i>
                                    </span>
                                    <span class="custom-info ml-1 meeting-location-url" id="vitalMeetingDestination"></span>
                                </div>
                            </div>
                            <div class="p-3 bg-white" style="margin-top: -25px;">
                                <div class="d-flex align-items-center"></div>
                                <div id="discussionPoints" class="discussion-points"></div>
                            </div>
                        </div>
                        <div class="p-3">
                        </div>
                    </div>
                `;

				// Append the created HTML to the container
				$('#yourContainer').empty().append(meetingCardHtml);  // Replace '#yourContainer' with the actual container ID or class.

				// Set meeting mode, destination, and other fields
				var meetingMode = meetingData.meetingMode || '';
				$('#showMeetingMode').text(meetingMode);
				$('#meetingType').text(meetingData.meetingType || 'vital');

				if (meetingMode.toLowerCase() === 'offline') {
					$('#showMeetingMode').addClass('offlineMeetingbg-colour').removeClass('onlineMeetingbg-colour');
					$('.location .fa-map-marker').show();
					$('.location .bi-link').hide();
					$('#vitalMeetingDestination').html(meetingData.meetingLocation || '');
				} else if (meetingMode.toLowerCase() === 'online') {
					$('#showMeetingMode').addClass('onlineMeetingbg-colour');
					$('.location .fa-map-marker').hide();
					$('.location .bi-link').show();
					var meetingUrl = meetingData.meetingUrl;
					$('#vitalMeetingDestination').html(`<a href="${meetingUrl}" target="_blank" rel="noopener noreferrer">${meetingUrl}</a>`);
				} else {
					$('.location .fa-map-marker, .location .bi-link').hide();
					$('#vitalMeetingDestination').text('');
					$('#showMeetingMode').removeClass('onlineMeetingbg-colour');
				}

				$('#vitalMeetingMode').text(meetingMode);
				$('#meetingDate').text(meetingData.meetingDate);
				$('#vitalMeetingLocation').text(meetingData.meetingLocation);

				// Set host and attendee images dynamically
				$('#userImagesContainer').empty();  // Clear existing images

				// Set host images
				hostDetails.forEach(function(host) {
					var hostImgSrc = host.hostImg;
					var hostImageHtml = `<img class="rounded-circle" src="${hostImgSrc}" width="40" height="40">`;
					$('#userImagesContainer').append(hostImageHtml);
				});

				// Set attendee images
				attendeesDetails.forEach(function(attendee) {
					var attendeeImgSrc = attendee.attendeeImg;
					var attendeeImageHtml = `<img class="rounded-circle" src="${attendeeImgSrc}" width="40" height="40">`;
					$('#userImagesContainer').append(attendeeImageHtml);
				});

				// Loop through and set discussion points dynamically
				var discussionHtml = '';
				var discussionPoints = JSON.parse(meetingData.discussionPoint);

				discussionPoints.forEach(function(point, index) {
					discussionHtml += `
                        <div class="discussion-point">
                            <p><strong>Point ${index + 1}:</strong> ${point['point_' + (index + 1)]}</p>
                        </div>
                    `;
				});

				// Set the discussion points dynamically
				$('#discussionPoints').html(discussionHtml);
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}

//Saving the discussion points and note on details section 
function saveMeetingDetailsSection() {
	let isValid = true;
	$(".validation-error").remove();
	var meetingAgenda = $('#agenda').val();
	if (!meetingAgenda) {
		$("#agenda").after('<span class="validation-error" style="color:red;">Meeting Agenda is required</span>');
		isValid = false;
	}
	$('.discussion-error').remove();
	$('.required-discussion').each(function() {
		const value = $.trim($(this).val());
		if (!value) {
			isValid = false;
			$(this).after('<span class="discussion-error" style="color: red;">This field is required.</span>');
		}
	});
	if (!isValid) {
		return;
	}
	let meetingId = $('#setMeetingId').val()
	let meetingStatus = $('#meetingTypeDropdown').val()
	let meetingData = {
		meetingId: meetingId,
		meetingNote: $('#agenda').val(),
		sectionDiscussPoint: getDiscussionPoints(),
		type: "meetingDetailsSection"
	}
	console.log("Meeting Data====>", meetingData)
	let jsonData = JSON.stringify(meetingData);
	$.ajax({
		url: 'meeting-calendar-save-data',
		type: 'POST',
		contentType: 'application/json',
		data: jsonData,
		success: function(response) {
			if (response.code === "success") {
				showSnackbar(response.message)
				//$('#eventModal').modal('hide');
				//getMeetingLists(meetingStatus);
				//setDiscussNote(meetingId);
				//getVitalMeeting();

			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
	console.log("Meeting Data In Details Section", meetingData);

}

function createMom() {
	const currentDate = new Date().toLocaleDateString('en-US', {
		month: 'short',
		day: 'numeric',
		year: 'numeric'
	});

	$('#momModalLabel').text(`Today's MOM: ${currentDate}`);
	$('#momTopic').val("").prop('disabled', false);
	gridOptionsMeetingDtls.api.setRowData([]);
	$('#createMeeting').show();
	$('#saveMeetingDetailsModal').show();
	$("#momModal").modal('show');
}

document.addEventListener('DOMContentLoaded', function() {
	var gridDiv4 = document.querySelector('#meetingDtlsGrid');
	new agGrid.Grid(gridDiv4, gridOptionsMeetingDtls);
	gridOptionsMeetingDtls.api.setRowData([]);

	var gridDivIssues = document.querySelector('#pendingIssueGrid');
	new agGrid.Grid(gridDivIssues, gridOptionsOpensIssues);
	gridOptionsOpensIssues.api.setRowData([]);

	var gridDivIssues1 = document.querySelector('#openTodayIssueGrid');
	new agGrid.Grid(gridDivIssues1, gridOptionsTodaysOpensIssues);
	gridOptionsTodaysOpensIssues.api.setRowData([]);

});

function openMeetingDetails() {
	document.getElementById("mySidenavPrevMeeting").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:146px;";
	document.getElementById("accordionExample1").style.width = "75%";
	issueRaisedEmp();

}

function issueRaisedEmp(meetingId) {
	//const meetingId = $("#setMeetingId").val();
	$.ajax({
		url: 'meeting-calendar-issue-raised-emp?meetingId=' + meetingId,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var parsedData = response.body ? JSON.parse(response.body) : [];

				const dropdown = $('#empNameAutoPrev');
				dropdown.find('option:not(:first)').remove();

				parsedData.forEach(emp => {
					const option = `<option value="${emp.ID}" data-code="${emp.empRoleNames}">${emp.empName}</option>`;
					dropdown.append(option);
				});
			}
		},
		error: function(error) {
			console.error("Error fetching employee data:", error);
		}
	});
}
function clearMomField() {
	$("#curTypePrev").val('');
	/*$("#momTopic").val('');*/
	$("#curDatePrev").val('');
	$("#curIssuePrev").val('');
	$("#issuePriority").val('');
	$("#empNameAutoPrev").val('');
	$("#empNameResolveAuto").val('');
	$("#prevAction").val('');
	$("#prevMeetingId").val('');
	$("#reportBy").val('');
}


function closeNavPrevMeeting() {
	gridOptionsMeetingDtls.api.setRowData([]);
	$('#momTopic').val("").prop('disabled', false);
	$("#curTypePrev").val('');
	$("#curDatePrev").val('');
	$("#curIssuePrev").val('');
	$("#empNameAutoPrev").val('');
	$("#empNameResolveAuto").val('');
	$("#prevAction").val('');
	$("#prevMeetingId").val('');
	$("#reportBy").val('');
}


var columnDefprevMeetingDtls = [

	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
		headerClass: 'left-aligned'  // Left-align the checkbox header
	},

	{
		headerName: "Sl No.",
		field: "slNo",
		width: 60,
		cellRenderer: function(params) {
			return '<a onclick="editMeetingIssueDtls(\'' + params.data.slNo + '\')" href="javascript:void(0)">' + params.data.slNo + '  <i class="fa fa-arrow-right" aria-hidden="true"></i></a>';
		},
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Issue',
		field: "issue",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		},
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Issue Raised By Id',
		field: "attendId",
		width: 200,
		hide: true,
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Issue Raised By',
		field: "attendName",
		width: 200,
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Action Plan Discussed',
		field: "curAction",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Job Responsibility',
		field: "issueResolveBy",
		width: 200,
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Job Responsibility Id',
		field: "issueResolveById",
		width: 200,
		headerClass: 'left-aligned',
		hide: true
	},
	{
		headerName: 'Role',
		field: "role",
		width: 100,
		headerClass: 'left-aligned',
		hide: true
	},
	{
		headerName: 'Type',
		field: "curType",
		width: 60,
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Target Time',
		field: "date",
		width: 80,
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Priority',
		field: "priority",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		},
		headerClass: 'left-aligned'
	},
	{
		headerName: 'Status',
		field: "issueProgressStatus",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		},
		headerClass: 'left-aligned'
	}, {
		headerName: 'Pending IssueId',
		field: "pendingIssueId",
		width: 50,
		hide: true
	}
];

var gridOptionsMeetingDtls = {
	columnDefs: columnDefprevMeetingDtls,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	onSelectionChanged: rowSelectAssignedIssue,
	getRowNodeId: function(data) {
		return data.slNo;
	}
};

function rowSelectAssignedIssue() {
	$("#tabContent").find("checkbox").prop("disabled", false);
	var selectedRows = gridOptionsMeetingDtls.api.getSelectedRows();
	var id = selectedRows.map(row => row.id);
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$("#deleteIssue").removeClass("hidden");
	} else {
		$("#deleteIssue").addClass("hidden");
	}
}

var columnOpensIssues = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Sl No.",
		field: "id",
		width: 70,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	},
	{
		headerName: 'Issue',
		field: "issue",
		width: 200,
		cellStyle: {
			textAlign: 'left',
		},
	},

	{
		headerName: 'Status',
		field: "issueStatus",
		width: 100,
		cellStyle: function(params) {
			const status = params.value;
			let color = '';

			switch (status) {
				case 'Pending':
					color = '#ff5a5a!important';
					break;
				case 'In Progress':
					color = '#5bc0de!important';
					break;
				case 'Assigned':
					color = '#009800 !important';
					break;
				case 'Closed':
					color = '#d9534f!important';
					break;
				default:
					color = '#d3d3d3!important';
			}

			return {
				color: color,
				textAlign: 'left',
				fontWeight: 'bold',
			};
		},
	},
	{
		headerName: 'Priority',
		field: "issuePriority",
		width: 100,
		cellStyle: function(params) {
			const priority = params.value;
			let color = '';

			switch (priority) {
				case 'High':
					color = '#d9534f!important';  // Red
					break;
				case 'Medium':
					color = '#ff9600 !important';  // Amber
					break;
				case 'Low':
					color = '#5bc0de!important';  // Light Blue
					break;
				default:
					color = '#d3d3d3!important';  // Gray for unknown priority
			}

			return {
				color: color,
				textAlign: 'left',
				fontWeight: 'bold', // Optional: makes text bold
			};
		},
	},
	{
		headerName: 'Deadline',
		field: "deadline_date",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	},
	{
		headerName: 'Assigned By',
		field: "createdBy",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true
	}, {
		headerName: 'Raised By',
		field: "raisedBy",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	}, {
		headerName: 'Assigned To',
		field: "assignedTo",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	},

	{
		headerName: 'Assigned On',
		field: "formatted_date",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	}
];


var gridOptionsOpensIssues = {
	columnDefs: columnOpensIssues,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	onSelectionChanged: rowSelectIssue

};

function rowSelectIssue() {
	$("#tabContent").find("checkbox").prop("disabled", false);
	var selectedRows = gridOptionsOpensIssues.api.getSelectedRows();
	var id = selectedRows.map(row => row.id);
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$("#removeIssue").prop("disabled", false);
		$("#issueId").val(id);
	} else {
		$("#removeIssue").prop("disabled", true);
		$("#issueId").val("");
	}
}


var columnTodayOpensIssues = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Sl No.",
		field: "id",
		width: 70,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	},
	{
		headerName: 'Issue',
		field: "issue",
		width: 300,
	},
	{
		headerName: 'Status',
		field: "issueStatus",
		width: 100,
		cellStyle: function(params) {
			const status = params.value;
			let color = '';

			switch (status) {
				case 'Pending':
					color = '#f0ad4e!important';
					break;
				case 'In Progress':
					color = '#5bc0de!important';
					break;
				case 'Resolved':
					color = '#5cb85c!important';
					break;
				case 'Closed':
					color = '#d9534f!important';
					break;
				default:
					color = '#d3d3d3!important';
			}

			return {
				color: color,
				textAlign: 'left',
				fontWeight: 'bold',
			};
		},
	},
	{
		headerName: 'Priority',
		field: "issuePriority",
		width: 100,
		cellStyle: function(params) {
			const priority = params.value;
			let color = '';

			switch (priority) {
				case 'High':
					color = '#d9534f!important';  // Red
					break;
				case 'Medium':
					color = '#f0ad4e!important';  // Amber
					break;
				case 'Low':
					color = '#5bc0de!important';  // Light Blue
					break;
				default:
					color = '#d3d3d3!important';  // Gray for unknown priority
			}

			return {
				color: color,
				textAlign: 'left',
				fontWeight: 'bold', // Optional: makes text bold
			};
		},
	},
	{
		headerName: 'Deadline',
		field: "deadline_date",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	}, {
		headerName: 'Deadline Date',
		field: "deadline",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	}, {
		headerName: 'Issue Type',
		field: "type",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	},

	{
		headerName: 'Assigned By',
		field: "createdBy",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	}, {
		headerName: 'Raised By',
		field: "raisedBy",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	}, {
		headerName: 'Assigned To',
		field: "assignedTo",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
		hide: true,
	},

	{
		headerName: 'Assigned On',
		field: "formatted_date",
		width: 100,
		cellStyle: {
			textAlign: 'left',
		},
	}
];


var gridOptionsTodaysOpensIssues = {
	columnDefs: columnTodayOpensIssues,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	onSelectionChanged: rowSelect
};

function rowSelect() {
	var selectedRows = gridOptionsTodaysOpensIssues.api.getSelectedRows();
	var id = selectedRows.map(row => row.id);
	var type = selectedRows.map(row => row.type);
	var issuePriority = selectedRows.map(row => row.issuePriority);
	var deadline = selectedRows.map(row => row.deadline);
	var raisedById = selectedRows.map(row => row.raisedById);
	var issue = selectedRows.map(row => row.issue);
	var actionPlan = selectedRows.map(row => row.actionPlan);
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	/*let id;
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + selectedRows[i].id;
	}*/

	if (rowCount > 0) {
		$('#openIssueId').val(id);
		$('#curTypePrev').val(type);
		$('#issuePriority').val(issuePriority);
		$('#curDatePrev').val(deadline);
		$('#empNameAutoPrev').val(raisedById);
		$('#curIssuePrev').text(issue);
		$('#prevAction').text(actionPlan);
		$("#toggleSection").removeClass("hidden");
	} else {
		$('#curTypePrev').val("");
		$('#issuePriority').val("");
		$('#curDatePrev').val("");
		$('#empNameAutoPrev').val("");
		$('#curIssuePrev').text("");
		$('#prevAction').text("");
		$("#toggleSection").addClass("hidden");
	}

}

var timeout;
function getEmployeeListResolve() {

	$("#employeeNameResolve").val('');
	$("#employeeIdResolve").val('');
	$('.loader-modal-autosearch').show();

	clearTimeout(timeout);
	timeout = setTimeout(function() {
		var search = $("#empNameResolveAuto").val();
		if (search) {
			$.ajax({
				type: "POST",
				url: "meeting-calendar-employeeAutosearch",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {

						var jsonData = JSON.parse(response.body);
						var allData = jsonData;
						if (allData != null && allData.length > 0) {
							var len = allData.length;
							//$("#empid").val("");
							var content = '<div id="autocomplete-container" class="autocomplete-container">';
							content += '<ul id="autocomplete-list1" class="sugg-cls-ul">';
							for (var i = 0; i < len; i++) {
								content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueResolve(\''
									+ window.btoa(allData[i].empId)
									+ '\',\''
									+ window.btoa(allData[i].name)
									+ '\')">'
									+ allData[i].empId
									+ " - "
									+ allData[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);
							$('.loader-modal-autosearch').hide();
						} else {
							$('.loader-modal-autosearch').hide();
							$("#empNameAutoResolve").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<font-weight:100; font-size:14px; color:#808080; background-color: #0909e4;"li onClick="selectAutocompleteValueResolve()">'
								+ "No Data Found" + '</li>';
							content += '</ul>';
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);
							$("#employeeIdResolve").val('');
							$("#employeeNameResolve").val('');
						}
					} else {
						$('.loader-modal-autosearch').hide();
						$("#suggesstion-box1_").hide();
						$("#employeeNameResolve").val('');
						$("#employeeIdResolve").val('');

					}
				},
				error: function(data) {
					console.log(data);
					$('.loader-modal-autosearch').hide();
					$("#suggesstion-box1_").hide();
					$("#employeeNameResolve").val('');
					$("#employeeIdResolve").val('');
				}
			});

		} else {
			$('.loader-modal-autosearch').hide();
			$("#suggesstion-box1_").hide();
		}
	}, 500);

}

function selectAutocompleteValueResolve(empId, empName) {
	if (empName) {
		$("#empNameResolveAuto").val(window.atob(empName));
		$("#employeeIdResolve").val(window.atob(empId));
		$("#suggesstion-box1_").hide();
	} else {
		$("#empNameResolveAuto").val('');
		$("#employeeIdResolve").val('');
		$("#suggesstion-box1_").hide();
	}
}

function saveTablePrevMeeting() {
	var editProduct = $("#prevMeetingId").val(); // Row ID to edit
	var currentempName = $('#empNameAutoPrev option:selected');
	var priority = $('#issuePriority option:selected');
	var item = {
		date: $('#curDatePrev').val(),
		issue: $('#curIssuePrev').val(),
		curType: $('#curTypePrev').val(),
		issueResolveBy: $('#toHiddenNameEmpAssignTo').val(),
		issueResolveById: $('#toHiddenIdEmpAssignTo').val(),
		attendName: currentempName.text(),
		role: currentempName.data('code'),
		curAction: $('#prevAction').val(),
		attendId: currentempName.val(),
		priority: priority.text(),
		pendingIssueId: $('#openIssueId').val()
	};

	if (editProduct) {
		// Update existing row
		var rowNode = gridOptionsMeetingDtls.api.getRowNode(editProduct);
		if (rowNode) {
			var currentData = rowNode.data;
			item.slNo = currentData.slNo;

			rowNode.setData({ ...currentData, ...item });
			gridOptionsMeetingDtls.api.refreshCells({ rowNodes: [rowNode] });
		} else {
			console.error("Row with ID " + editProduct + " not found.");
		}
	} else {
		// Add new row
		var rowData = [];
		gridOptionsMeetingDtls.api.forEachNode(function(node) {
			rowData.push(node.data);
		});

		var slNo = rowData.length + 1;
		gridOptionsMeetingDtls.api.applyTransaction({
			add: [{ slNo: slNo, ...item }]
		});
	}
	clearMomField();
}

function deleteIssue() {
	// Get the selected row
	var selectedNodes = gridOptionsMeetingDtls.api.getSelectedNodes();

	if (selectedNodes.length === 0) {
		console.warn("No row selected for deletion.");
		return;
	}
	var selectedNode = selectedNodes[0];
	var selectedRowData = selectedNode.data;
	gridOptionsMeetingDtls.api.applyTransaction({
		remove: [selectedRowData]
	});
	var allRows = [];
	gridOptionsMeetingDtls.api.forEachNode(function(node) {
		allRows.push(node.data);
	});

	allRows.forEach(function(rowData, index) {
		rowData.slNo = index + 1;
	});
	gridOptionsMeetingDtls.api.setRowData(allRows);

}



function closeMomModal() {
	$('#currentDate').text("");
	$('#momModalLabel').text("");
	$("#momModal").modal('hide');
	closeNavPrevMeeting();
}

function saveMeetingDetails() {
	const momData = [];
	const momTopic = $("#momTopic").val();
	const meetingId = $("#setMeetingId").val();
	const checkboxes = document.querySelectorAll("#attendeesContainerDiv .form-check-input");
	const selectedAttendees = [];

	checkboxes.forEach((checkbox) => {
		if (checkbox.checked) {
			const label = document.querySelector(`label[for="${checkbox.id}"]`);
			if (label) {
				selectedAttendees.push(label.innerText.trim());
			}
		}
	});
	const selectedNames = selectedAttendees.join(", ");

	if (!selectedNames || selectedNames.trim() === "") {
		$("#attendeesErrorMsg").html("At least one attendee must be selected.");
		return;
	}

	if (!momTopic || momTopic.trim() === "") {
		validationUpdated(`<span class="errMomTopic">MOM Topic is required</span>`,
			"momTopic");
		return;
	}

	let hasAtLeastOneRow = false;
	gridOptionsMeetingDtls.api.forEachNode(node => {
		if (node.data) {
			const rowData = { ...node.data, momTopic: momTopic, meetingId: meetingId, attendeesNames: selectedNames };
			momData.push(rowData);
			hasAtLeastOneRow = true;
		}
	});

	// If no data exists in the grid, create a placeholder entry with null values
	if (!hasAtLeastOneRow) {
		const placeholderRow = {
			momTopic,
			meetingId,
			attendeesNames: selectedNames,
			curType: null,
			issue: null,
			curAction: null,
			date: null,
			priority: null,
			attendId: null,
			role: null,
			issueResolveBy: null,
		};
		momData.push(placeholderRow);
	}

	/*if (!hasAtLeastOneRow) {
		$("#messageParagraph").text("At least one issue should be assigned.");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return; 
	}*/

	const payload = {
		momData: momData
	};


	$.ajax({
		url: 'meeting-calendar-save-mom-dtls',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(response) {
			if (response.code == "success") {
				showSnackbar(response.message);
				$('#momModal').modal('hide');
				let meetingId = $("#setMeetingId").val();
				setMomDetailsTabAsDefault();
				getMomsDetails(meetingId, '', '');
				getTodaysOpensIssues(meetingId);
				getOpensIssues(meetingId);
				refreshIssues();
				closeMomReport();
				$("#momTopic").val("");
				checkboxes.forEach((checkbox) => {
					checkbox.checked = false;
				});
			} else {
				showSnackbar(response.message);
			}
		},
		error: function(error) {
			console.error("Error saving data:", error);
		}
	});
}



/*function saveMeetingDetails11() {
	const momData = [];
	const momTopic = $("#momTopic").val();
	const meetingId = $("#setMeetingId").val();
	const checkboxes = document.querySelectorAll("#attendeesContainerDiv .form-check-input");
	const selectedAttendees = [];
	checkboxes.forEach((checkbox) => {
		if (checkbox.checked) {
			const label = document.querySelector(`label[for="${checkbox.id}"]`);
			if (label) {
				selectedAttendees.push(label.innerText.trim());
			}
		}
	});
	const selectedNames = selectedAttendees.join(", ");
	console.log("Selected Attendees:", selectedNames);
    
	gridOptionsMeetingDtls.api.forEachNode(node => {
		if (node.data) {
			const rowData = { ...node.data, momTopic: momTopic, meetingId: meetingId, attendeesNames:selectedNames};
			momData.push(rowData);
		}
	});

	const payload = {
		momData: momData
	};
	
	console.log(payload);

	$.ajax({
		url: 'meeting-calendar-save-mom-dtls',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(response) {
			if (response.code == "success") {
				showSnackbar(response.message)
				$('#momModal').modal('hide');
				let meetingId = $("#setMeetingId").val();
				setMomDetailsTabAsDefault();
				getMomsDetails(meetingId);
				getTodaysOpensIssues(meetingId);
				getOpensIssues(meetingId);
				refreshIssues();

			} else {
				showSnackbar(response.message)
			}

		},
		error: function(error) {
			console.error("Error saving data:", error);
		}
	});
}*/

/*function getMomsDetails(meetingId) {
	$.ajax({
		url: 'meeting-calendar-get-moms-list?meetingId=' + meetingId,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var parsedData = response.body ? JSON.parse(response.body) : [];
				$('#momListsCards').empty();

				if (parsedData.length === 0) {
					// If no data, show the "No MOM Found" message
					$('#momListsCards').html('<p class="text-center">No MOM found for this meeting</p>');
					return;
				}

				parsedData.forEach(item => {
					const cardHtml = `
					<div class="col-md-2">
						<a class="data-card-new" onclick="momDetailsData('${item.createdDate}');">
							<h3>${item.formatted_date}</h3>
							<h4>${item.meetingName}</h4>
						    
							<span class="link-text-new">
								Details <svg width="25" height="16" viewBox="0 0 25 16" fill="none" xmlns="http://www.w3.org/2000/svg">
									<path fill-rule="evenodd" clip-rule="evenodd" d="M17.8631 0.929124L24.2271 7.29308C24.6176 7.68361 24.6176 8.31677 24.2271 8.7073L17.8631 15.0713C17.4726 15.4618 16.8394 15.4618 16.4489 15.0713C16.0584 14.6807 16.0584 14.0476 16.4489 13.657L21.1058 9.00019H0.47998V7.00019H21.1058L16.4489 2.34334C16.0584 1.95281 16.0584 1.31965 16.4489 0.929124C16.8394 0.538599 17.4726 0.538599 17.8631 0.929124Z" fill="#753BBD" />
								</svg>
							</span>
						</a>
					</div>
				`;

					// Append the card to the section
					$('#momListsCards').append(cardHtml);
				});
			}
		},
		error: function(error) {
			console.error("Error fetching meeting data:", error);
		}
	});
}*/

function momDetailsData(date) {
	$("#momCloseBtn").removeClass("d-none");
	$("#momPdfBtn").removeClass("d-none");
	$(".filter-container").addClass("d-none");

	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split('T')[0];
	if (date < formattedDate) {
		$("#momEditPdfBtn").addClass("d-none");
		$("#momeditValidMessage").html("(Mom cannot be edited because the date has passed)");
	} else {
		$("#momEditPdfBtn").removeClass("d-none");
		$("#momeditValidMessage").html("(Mom can only be edited until today at 12 PM)");
	}
	const meetingId = $("#setMeetingId").val();

	$(".loader").show();

	$.ajax({
		url: `meeting-calendar-get-moms-details?meetingId=${meetingId}&momDate=${date}`,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				let parsedData = response.body ? JSON.parse(response.body) : {};
				const topicDiscussed = parsedData.topicDiscussed?.topic || "";
				const formattedDate = parsedData.topicDiscussed?.formatted_date || "";
				const meetingDetails = parsedData.details || [];
				const {
					meetingDate,
					meetingTime,
					meetingLocation,
					meetingHost,
					meetingAttendees,
					meetingAgenda,
					meetingName,
					meetingDiscussionPoint,
					meetingId,
					meetingMode
				} = meetingDetails[0] || {};
				let combinedDateTime = `${meetingDate} || ${meetingTime}`;
				$("#momDate").val(date);
				$("#momMeetingId").val(meetingId);
				$("#momReportModalLabel").text(`Minutes of Meeting (MOM) for ${formattedDate}`);
				$("#meetingDate").text(combinedDateTime || "-");
				$("#meetingLocation").text(meetingLocation || meetingMode);
				$("#meetingOrganizer").text(meetingHost || "-");
				$("#meetingChair").text(meetingHost || "-");
				$("#draftedDate").text(formattedDate || "-");
				$("#meetingTitle").text(meetingName || "-");

				const hosts = meetingHost?.split(", ") || []; // Split the meeting hosts into an array
				const attendeesList = [...(meetingAttendees?.split(", ") || [])].filter(Boolean); // Combine hosts and attendees, and remove any empty values
				$("#attendanceListMom").html(`
                <ul>
                 ${attendeesList.map(attendee => `<li>${attendee}</li>`).join("")}
                </ul>`);
				$("#agendaList").html(meetingAgenda ? `<ul>${meetingAgenda}</ul>` : `<ul>No agenda provided.</ul>`);
				if (topicDiscussed != "" && topicDiscussed != "null") {
					$(".mom-summary").show();
					$("#momSummary").html(`<ul>${topicDiscussed}</ul>`);
				} else {
					$(".mom-summary").hide();
				}

				const discussionPoints = meetingDiscussionPoint ? JSON.parse(meetingDiscussionPoint) : [];
				/*if(discussionPoints.length > 0){
					$(".discussion-points").show();
					$("#discussionSummary").html(`
					  ${discussionPoints.map((point, index) => {
					const discussionValues = Object.keys(point)
						.filter(key => key.startsWith("point_")).map(key => point[key]);
					return `
					<tr>
						<td>${index + 1}</td>
						<td>${discussionValues[0]}</td>
					</tr>
				   `;
						}).join("")}`);
				} else {
					$(".discussion-points").hide();
				}*/


				// Check if any meetingDetails contain valid data (non-null, non-empty)
				const hasValidData = meetingDetails.some(detail =>
					detail.issue != null && detail.issue !== "" && detail.issue !== "null" ||
					detail.curAction != null && detail.curAction !== "" && detail.curAction !== "null" ||
					detail.issueResolveBy != null && detail.issueResolveBy !== "" && detail.issueResolveBy !== "null" ||
					detail.date != null && detail.date !== "" && detail.date !== "null" ||
					detail.priority != null && detail.priority !== "" && detail.priority !== "null"
				);

				// If no valid data, hide the responsibilitiesAssigned table
				if (!hasValidData) {
					$("#responsibilitiesAssigned").hide();
					$("#responsibilitiesSection").hide();
				} else {
					$("#responsibilitiesAssigned").show();
					$("#responsibilitiesSection").show();
					// Render the table if there's valid data
					$("#responsibilitiesAssigned").html(`
				        ${meetingDetails.map((detail, index) => `
				            <tr>
				                <td>${index + 1}</td>
				                <td>${detail.issue != null && detail.issue !== "" ? detail.issue : "-"}</td>
				                <td>${detail.curAction != null && detail.curAction !== "" ? detail.curAction : "-"}</td>
				                <td>${detail.attendName != null && detail.attendName !== "" ? detail.attendName : "-"}</td>
				                <td>${detail.issueResolveBy != null && detail.issueResolveBy !== "" ? detail.issueResolveBy : "-"}</td>
				                <td>${detail.date != null && detail.date !== "" ? detail.date : "-"}</td>
				                <td>${detail.priority != null && detail.priority !== "" ? detail.priority : "-"}</td>
				            </tr>
				        `).join("")}
				    `).show(); // Ensure the section is shown if rendering
				}
				/*document.getElementById("meetingDetails").style.display = "block";*/
				$("#meetingDetails").removeClass("d-none");
				$("#momListsCards").addClass("d-none");
				$(".loader").hide();
				/*document.getElementById("momListsCards").style.display = "none";*/
			} else {
				console.error('Response code indicates failure:', response.message);
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX request failed:', error);
		}
	});
}


function meetingDeatislSectionTabs(type) {

	if (type == "overview" || type == "attendees") {
		$("#saveMeetingDetailsSection").hide();
	} else {
		$("#saveMeetingDetailsSection").show();
	}

}

function closeMomReportModal() {
	$("#momReportModal").modal('hide');
}
function downloadMomPdf() {
	var momDate = $("#momDate").val();
	var momMeetingId = $("#momMeetingId").val();
	const url = `/meeting/meeting-calendar-mom-Pdf?momMeetingId=${momMeetingId}&momDate=${momDate}`;
	window.open(url, '_blank');
}

function addDiscuss() {
	const container = $('#discussContainer');
	let isAnyFieldBlank = false;
	container.children().each(function() {
		const inputField = $(this).find('input');
		if (inputField.val().trim() === '') {
			isAnyFieldBlank = true;
			return false;
		}
	});

	if (isAnyFieldBlank) {
		/* alert('Please fill in all the discussion points before adding a new one.');*/
		return;
	}

	const newDiscussionPoint = $('<div>', {
		class: 'input-group mb-2 diss'
	});

	const input = $('<input>', {
		type: 'text',
		class: 'form-control required-discussion',
		placeholder: `${container.children().length + 1}. Add your detailed discussion point here.`
	});

	const removeButton = $('<button>', {
		class: 'btn btn-danger ml-2 discussion-btn-bg',
		html: '<i class="fa fa-times" aria-hidden="true"></i>',
		click: function() {
			newDiscussionPoint.remove();
			updateDiscussionPointNumbers();
		}
	});

	newDiscussionPoint.append(input);
	newDiscussionPoint.append(removeButton);
	container.append(newDiscussionPoint);
}

function updateDiscussionPointNumbers() {
	$('#discussContainer .diss').each(function(index) {
		$(this).find('input').attr('placeholder', `${index + 1}. Add your detailed discussion point here.`);
	});
}

function removeDiscuss(button) {
	$(button).closest('.diss').remove();
	updateDiscussionPointNumbers();
}