function getValidationModal(msg) {
	$("#messageParagraph").text(msg)
	$("#msgModal").modal("show");
}

var calendar;
$(document).ready(function() {

	var calendarEl = document.getElementById('calendar');
	calendar = new FullCalendar.Calendar(calendarEl, {
		height: '100%',
		expandRows: true,
		slotMinTime: '06:00',
		slotMaxTime: '22:00',
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
			right: 'multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay,listWeek,nextTwoDays'
		},
		initialView: 'dayGridMonth',
		editable: true,
		selectable: true,
		nowIndicator: true,
		dayMaxEvents: 0,
		eventTimeFormat: {
			hour: 'numeric',    // Hour in numeric format
			minute: '2-digit',  // Minute in 2-digit format
			meridiem: 'short'     // Disable default meridiem formatting
		  },
		  eventContent: function(arg) {
			let time = arg.timeText; // The default time text
			if (time) {
			  time = time.replace(/(am|pm)$/i, ' $1').toUpperCase(); // Add space before AM/PM
			}
			return { html: `${time} - ${arg.event.title}` }; // Customize event content
		  },
		navLinks: true,
		customButtons: {
			nextTwoDays: {
				text: 'Next 2 Days',
				click: function() {
					var today = new Date();
					calendar.changeView('customTwoDay', today);
				}
			}
		},
		views: {
			customTwoDay: {
				type: 'timeGrid',
				duration: { days: 2 },
				buttonText: '2 day',
				slotDuration: '01:00:00'
			}
		},
		events: function(fetchInfo, successCallback) {
			console.log("fetchInfo --> ", fetchInfo);
			var promise = getEvent(fetchInfo)
			promise
				.then(response => {
					if (response.code == "success") {
						let parseBody = JSON.parse(response.body);

						parseBody.forEach(event => {
							if (event.status === 'Cancelled') {
								event.className = 'cancelled-event';
								event.rendering = 'background';
							} else if (event.status === 'Completed') {
								event.className = 'completed-event';
							}
						});
						successCallback(parseBody);
					} else {
						//getValidationModal(response.message);
						successCallback([]);
					}
				})
				.catch(error => {
					successCallback([]);
					//getValidationModal('Something Went Wrong while fetching Events.')
					console.error(error);
				});
		},
		dateClick: function(info) {
			cancelModal();
			$('#allDay').prop('checked', false);
			$("#eventEndTime").attr("disabled", false);
			$("#eventStartTime").attr("disabled", false);

			let currentTime = new Date();
			currentTime.setMinutes(currentTime.getMinutes() + 1);

			let startHours = String(currentTime.getHours()).padStart(2, '0');
			let startMinutes = String(currentTime.getMinutes()).padStart(2, '0');
			let endHours = String(currentTime.getHours() + 1).padStart(2, '0');


			let currentDate = new Date();
			let clickedDate = new Date(info.dateStr);


			if (clickedDate < currentDate.setHours(0, 0, 0, 0)) {
				getValidationModal('Cannot add meetings to past dates.')
				return;
			}


			// Open modal for new event
			openEventModal('', '', info.dateStr, startHours + ":" + startMinutes, false, '');
		},
		eventClick: function(info) {
			$("#postponed-tab").removeClass("d-none");
			let meetingId = info.event.id;
			getCalendarMeetingDeatils(meetingId);
		},
		datesSet: function(info) {
			console.log('New date range: ', info.startStr, 'to', info.endStr);
			let promise = getEvent(info);
			promise
				.then(response => {
					console.log("AKM-->>", response);
					if (response.code == "success") {
						let parseBody = JSON.parse(response.body);
						calendar.addEvent(parseBody)
					}
				})
				.catch(error => {
					//getValidationModal('Something Went Wrong while fetching Events.')
					console.error(error);
				});
			//calendar.addEvent(resp.body);
		},
		eventDrop: function(info) {
			let eventStart = info.event.start;
			let currentDateTime = new Date();

			if (eventStart < currentDateTime) {
				getValidationModal('Cannot set event to a past time.');
				info.revert();
			} else {
				// Proceed with the update if the new time is valid
				updateEventLocally(info.event);
			}
		},
		eventResize: function(info) {
			let eventEnd = info.event.end;
			let currentDateTime = new Date();

			if (eventEnd < currentDateTime) {
				getValidationModal('Cannot extend event to a past time.');
				info.revert(); // Revert to the original size
			} else {
				updateEventLocally(info.event);
			}
		},
	});

	calendar.render();

	function getDiscussionPoints() {
		const container = document.getElementById("discussContainer");
		const discussionDivs = container.querySelectorAll('.diss');
		const discussionPoints = [];

		discussionDivs.forEach((div, index) => {
			const input = div.querySelector('.form-control');
			const value = input.value.trim();

			if (value) {
				discussionPoints.push({
					[`point_${index + 1}`]: value
				});
			}
		});

		console.log("Discussion Points:", discussionPoints);
		return discussionPoints;
	}

	function showSnackbar(message) {
		const snackbar = document.getElementById("snackbar");
		snackbar.textContent = message;
		snackbar.className = "snackbar show";
		setTimeout(() => {
			snackbar.className = snackbar.className.replace("show", "");
		}, 10000);
	}

	// Save new or edited event
	$('#saveMeeting').click(function() {
		var meetingAgenda = $('#agenda').val();
		//var meetingPoints=getDiscussionPoints();
		$(".validation-error").remove();
		var isValid = true;
		if (!meetingAgenda) {
			$("#agenda").after('<span class="validation-error">Meeting Agenda is required</span>');
			isValid = false;
		}
		$('.discussion-error').remove();
		$('.required-discussion').each(function() {
			const value = $.trim($(this).val());
			if (!value) {
				isValid = false;
				$(this).parent().after('<span class="discussion-error">This field is required.</span>');
			}
		});

		if (!isValid) {
			return;
		}
		
		var frequency = $('#frequency').val();
		var meetFromDate = "";
		var meetToDate = "";
		
		if(frequency == 'one-time'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingFromDate').val();
		} else if(frequency == 'daily'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingToDate').val();
		} else if(frequency == 'weekly'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingToDate').val();
		} else if(frequency == 'monthly-same-date'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingToDate').val();
		} else if(frequency == 'monthly-same-day'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingToDate').val();
		} else if(frequency == 'monthly-last-working-day'){
			meetFromDate = $('#meetingFromDate').val();
			meetToDate = $('#meetingToDate').val();
		} 
		
		let meetingData = {
			id: $('#saveEvent').data('eventId'),
			meetingId: $('#meetingId').val(),
			meetingName: $('#meetingName').val(),
			meetingCrationDate: $('#meetingCrationDate').val(),
			meetingCrationTime: $('#meetingCrationTime').val(),
			host: $('#toHiddenIdOrg').val(),
			attendees: $('#toHiddenIdAttendees').val(),
			frequency: document.getElementById("frequency").value,
			fromDate: meetFromDate,
			fromTime: $('#meetingFromTime').val(),
			toDate: meetToDate,
			toTime: $('#meetingToTime').val(),
			mode: document.querySelector('input[name="meetingMode"]:checked').value,
			meetingLink: $('#meetingLink').val(),
			meetingLocation: $('#location').val(),
			meetingAgenda: $('#agenda').val(),
			/*meetingRepeatUntil: $('#repeatUntil').val(),*/
			meetingDiscussionPoints: getDiscussionPoints(),
			meetingDays: getSelectedDays(),
			daysFilter: getSelectedDays().join(','),
			monthlyDates: $('#selected-dates').text()
			/*allmeetings: getAllBetweensMeetings()*/
		};
		saveMeeting(meetingData)
		$('#eventModal').modal('hide');

	});

	$('#deleteEvent').click(function() {
		let id = $('#saveEvent').data('eventId');
		if (id) {
			deleteEventLocally(id);
			$('#eventModal').modal('hide');
		} else {
			getValidationModal('No event selected to delete.');
		}
	});

	function saveMeeting(meetingData) {
		let jsonData = JSON.stringify(meetingData);
		let meetingStatus = $('#meetingTypeDropdown').val();
		// AJAX call to save the data
		$.ajax({
			url: 'meeting-calendar-save-data',
			type: 'POST',
			contentType: 'application/json',
			data: jsonData,
			success: function(response) {
				if (response.code === "success") {
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

	// Disabling time fields when All Day is checked
	$('#allDay').change(function() {
		let isChecked = this.checked;
		$('#eventStartTime, #eventEndTime').prop('disabled', isChecked).val('');

		if (isChecked) {
			// Set end date to current date + 1 day
			let currentDate = new Date($('#eventStartDate').val());
			currentDate.setDate(currentDate.getDate() + 1);
			let endDate = currentDate.toISOString().split('T')[0]; // Convert to YYYY-MM-DD format
			$('#eventEndDate').val(endDate);
		} else {
			let startDate = $('#eventStartDate').val();
			$('#eventEndDate').val(startDate);
		}

		//$('#eventEndDate, #eventStartDate').prop('disabled', isChecked);

	});


	function formatLocalDate(date) {

		const startDate = new Date(date);
		const year = startDate.getFullYear();
		const month = ('0' + (startDate.getMonth() + 1)).slice(-2);
		const day = ('0' + startDate.getDate()).slice(-2);
		return `${year}-${month}-${day}`;
	}

	function getEvent(info) {
		var start = formatLocalDate(info.startStr);
		var end = formatLocalDate(info.endStr);

		return new Promise(function(resolve, reject) {
			$.ajax({
				url: "meeting-calendar-get-meetings?startDate=" + start + "&endtDate=" + end,
				method: 'GET',
				contentType: 'application/json',
				success: function(response) {
					console.log("Meeting response::::::", response)
					resolve(response)
				},
				error: function(error) {
					console.error('Error updating event:', error);
					reject(error)
				}
			});
		});
	}


});

function openEventModal(id, meetingName, meetingCreatedDate, meetingCreatedTime, allDay, url) {
	$('#meetingName').val(meetingName);
	$('#meetingCrationDate').val(meetingCreatedDate).attr("disabled", true);
	$('#meetingCrationTime').val(meetingCreatedTime).attr("disabled", true);;
	$('#meetingFromDate').val(meetingCreatedDate);
	$('#meetingFromTime').val(meetingCreatedTime);
	let currentDateTime = new Date();
	let formattedDate = currentDateTime.toISOString().split('T')[0];
	currentDateTime.setHours(currentDateTime.getHours() + 1);
	let formattedTime = currentDateTime.toTimeString().split(' ')[0].substring(0, 5);
	$('#meetingToDate').val(meetingCreatedDate);
	$('#meetingToTime').val(formattedTime);
	$('#saveEvent').data('eventId', id);
	$('#eventModal').modal('show');
}
// Function to get day names
function getDayName(date) {
	const days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	return days[date.getDay()];
}

// Function to get transformed date-day-time objects
function getTransformedDates(startDate, endDate, filterDays, startTime, endTime) {
	const resultArray = [];
	let currentDate = new Date(startDate);
	const stopDate = new Date(endDate);

	// Normalize filterDays to a set for faster lookup
	const filterDaysSet = new Set(filterDays.map(day => day.trim().toLowerCase()));

	// Populate resultArray
	while (currentDate <= stopDate) {
		const dayName = getDayName(currentDate);
		if (filterDaysSet.size === 0 || filterDaysSet.has(dayName.toLowerCase())) {
			const formattedDate = currentDate.toISOString().split('T')[0];
			resultArray.push({
				day: dayName,
				startDate: formattedDate,
				endDate: formattedDate,
				startTime: startTime,
				endTime: endTime
			});
		}
		currentDate.setDate(currentDate.getDate() + 1); // Increment by one day
	}

	return resultArray;
}

function getAllBetweensMeetings() {
	const startDate = $('#meetingFromDate').val();
	const endDate = $('#meetingToDate').val();
	const days = getSelectedDays();
	var daysFilter = days.join(',');
	const startTime = $('#meetingFromTime').val() || "00:00";
	const endTime = $('#meetingToTime').val() || "23:59";

	if (!startDate || !endDate) {
		alert('Please select both start and end dates.');
		return;
	}
	if (new Date(startDate) > new Date(endDate)) {
		alert('Start date cannot be after the end date.');
		return;
	}

	console.log('Start Date:', startDate, 'End Date:', endDate);
	console.log('Start Time:', startTime, 'End Time:', endTime);
	const filterDays = daysFilter
		? daysFilter.split(',').map(day => day.trim())
		: [];
	console.log('Selected Days:', filterDays);
	const transformedDates = getTransformedDates(startDate, endDate, filterDays, startTime, endTime);
	console.log('TransFormatted Dates Days:', transformedDates);
	return transformedDates;
}


function getCalendarMeetingDeatils(id) {
	$.ajax({
		url: 'meeting-calendar-get-each-meeting-details?id=' + id,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				var meeting = response.body ? JSON.parse(response.body) : [];
				var meetingId = meeting[0].meetingId;
				editMeetingDetails(meetingId);
				$("#dateFields").addClass('hidden');
				$("#meetingDetailsRemark").addClass('hidden');
				$('#calenderMeetingId').val(meeting[0].id);
				$('#meetingModalLabelName').text(meeting[0].meeting);
				$('#meetingTime').text(meeting[0].date + ", " + meeting[0].days);
				$('#meetingAgenda').text(meeting[0].meetingAgenda);
				$('#meetingResponseMessage').show();
				$("#meetingDetailsRemark").addClass('hidden');
				$("#meetingRemark").val("").attr("disabled", false);
				$('#addDiscussionPointLink').show();
				$("#discussionPointInput").addClass('hidden').val("");
				if (meeting[0].discussPoints != "null" && meeting[0].discussPoints != "" && meeting[0].discussPoints != null) {
					addDiscussionPointLink();
					$("#discussionPointTextarea").val(meeting[0].discussPoints);
				}
				
				// Check the meeting end time
				var currentDateTime = new Date();
				var meetingEndDateTime = new Date(`${meeting[0].startDate}T${meeting[0].endTime}:00`);
				var isMeetingEnded = currentDateTime > meetingEndDateTime;

				if (meeting[0].status == 'Completed') {
					$('#meetingDetailsStatus').html('<span class="px-2 custom-review-text-completed ml-2 rounded">' + meeting[0].status + '</span>');
					$('#changeCalendarMeeting').hide();
					$('#cancelCalendarMeeting').hide();
					$('#saveMeetingChanges').hide();
					$('#addDiscussionPointLink').hide();
					$('#completeCalendarMeeting').hide();
					$('#meetingsDocuments').hide();
					$('#meetingResponseMessage').removeClass('alert-warning alert-danger');
					$('#meetingResponseMessage').text("The meeting has been successfully completed on this date.").addClass('alert-success');
				} else if (meeting[0].status == 'Postpone') {
					
					$('#saveMeetingChanges').show();
					$("#meetingRemark").val(meeting[0].meetingRemark);
					$('#completeCalendarMeeting').show();
					// Enable or disable the button based on meeting end time
					if (isMeetingEnded) {
						$('#completeCalendarMeeting').attr('disabled', false);
						$('#completeCalendarMeeting').removeClass('disabled');
						$('#changeCalendarMeeting').hide();
				        $('#cancelCalendarMeeting').hide();
				        $('#addDiscussionPointLink').hide();
				        $('#meetingsDocuments').hide();
					} else {
						$('#completeCalendarMeeting').attr('disabled', true);
						$('#completeCalendarMeeting').addClass('disabled');
						$('#changeCalendarMeeting').show();
					    $('#cancelCalendarMeeting').show();
					    $('#addDiscussionPointLink').show();
					    $('#meetingsDocuments').show();
					}
					$('#meetingDetailsStatus').html('<span class="px-2 custom-review-text-postpond ml-2 rounded">' + meeting[0].status + '</span>');
					$('#meetingResponseMessage').removeClass('alert-success alert-danger');
					$('#meetingResponseMessage').html("The meeting has been postpone from <strong>" + meeting[0].previousDateTime + "</strong> to <strong>" + meeting[0].date + "</strong>.").addClass('alert-warning');
				} else if (meeting[0].status == 'Open') {
					$('#saveMeetingChanges').show();
					$('#meetingResponseMessage').hide();
					$('#completeCalendarMeeting').show();
					// Enable or disable the button based on meeting end time
					if (isMeetingEnded) {
						$('#completeCalendarMeeting').attr('disabled', false);
						$('#completeCalendarMeeting').removeClass('disabled');
						$('#changeCalendarMeeting').hide();
				        $('#cancelCalendarMeeting').hide();
				        $('#addDiscussionPointLink').hide();
				        $('#meetingsDocuments').hide();
					} else {
						$('#completeCalendarMeeting').attr('disabled', true);
						$('#completeCalendarMeeting').addClass('disabled');
						$('#changeCalendarMeeting').show();
					    $('#cancelCalendarMeeting').show();
					    $('#addDiscussionPointLink').show();
					    $('#meetingsDocuments').show();
					}
					$('#meetingDetailsStatus').html('<span class="px-2 custom-review-text-open ml-2 rounded">' + meeting[0].status + '</span>');
				} else {
					$('#changeCalendarMeeting').hide();
					$('#cancelCalendarMeeting').hide();
					$('#saveMeetingChanges').hide();
					$('#meetingResponseMessage').removeClass('alert-success alert-warning');
					$('#meetingDetailsStatus').html('<span class="px-2 custom-review-text ml-2 rounded">' + meeting[0].status + '</span>');
					$('#meetingResponseMessage').text("The meeting has been cancelled.").addClass('alert-danger');
					$("#meetingDetailsRemark").removeClass('hidden');
					$('#addDiscussionPointLink').hide();
					$('#completeCalendarMeeting').hide();
					 $('#meetingsDocuments').hide();
					$("#meetingRemark").val(meeting[0].meetingRemark).attr("disabled", true);
				}
				$('#meetingDetailsDate').val(meeting[0].startDate);
				$('#meetingDetailsFromTime').val(meeting[0].startTime);
				$('#meetingDetailsToTime').val(meeting[0].endTime);



				// Open the modal
				$('#calMeetingModal').modal('show');


			} else {
				console.error('Failed to fetch meeting details:', response.message);
			}
		},
		error: function(error) {
			console.error('Error fetching meeting details:', error);
		}
	});
}


function toggleRemark() {
	$("#meetingDetailsRemark").toggleClass('hidden');
}

function closeMeetingDetailsModal() {
	$("#dateFields").addClass('hidden');
}

function saveCalendarMeetingResponse() {
	/*uploadDocument();*/
	/*const fileInput = document.getElementById('documentInput');
    const files = fileInput.files;
    const meetingDocuments = [];
    for (let i = 0; i < files.length; i++) {
        meetingDocuments.push({ filename: files[i].name });
    }*/
    /*console.log("File names list:", meetingDocuments);*/
  

let calendarDetails = {
    id: $("#calenderMeetingId").val(),
    remark: $("#meetingRemark").val(),
    date: $("#meetingDetailsDate").val(),
    start: $("#meetingDetailsFromTime").val(),
    end: $("#meetingDetailsToTime").val(),
    type: $("#meetingEditType").val(),
    discussPoints: $("#discussionPointTextarea").val(),
    discussPointsType: $("#discussionPointAdd").val(),
    frequency: $("#frequency").val(),
    meetingId: $("#meetingId").val(),
    /*meetingDocuments:meetingDocuments*/
}

console.log("Data:::::::", calendarDetails);

 
	$.ajax({
		url: 'meeting-calendar-save-calendar-details',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(calendarDetails),
		success: function(response) {
			if (response.code === "success") {
				calendar.refetchEvents();
				showSnackbar(response.message)
				$("#calMeetingModal").modal('hide');
				var meetingId = $("#calenderMeetingId").val();
				getCalendarMeetingDeatils(meetingId);
				getMeetingLists('openMeeting');
				
			} else {
				showSnackbar(response.message)
				$("#calMeetingModal").modal('show');
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}

function addDiscussionPointLink() {
	$("#discussionPointInput").removeClass('hidden');
	$("#discussionPointTextarea").attr("disabled", false);
}