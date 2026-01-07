var acticityCount = 0;
var acticityCount1 = 0;
let toMail = '';
let ownerId = '';
let userId = '';
$(document).ready(function() {
	alert("hiis")
	getNameList();
	/**CUSTOMER CREATION */
	$(".convertLeads").hide()
	sessionStorage.setItem("customerID", "");
	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);

	$('.email-alert').addClass('collapse');
    $("#deletePrdBtn").attr("disabled", true);
    $('#delete').attr("disabled", true);

	watchLocationPermission();

	$(".addRowMacro").click(function() {
		var fieldName = $("#fieldName").val();
		var fieldValue = $("#fieldValue").val();
		var ligne = "<tr id='addMacroRow'><td class='checkedCls'><input type='checkbox' name='select'></td><td class='fieldName'>" + fieldName + "</td><td class='fieldValue'>" + fieldValue + "</td></tr>";
		$("table.rowDataTable").append(ligne);
	});
	$(".deleteRowMacro").click(function() {
		$("table.rowDataTable").find('input[name="select"]').each(function() {
			if ($(this).is(":checked")) {
				$(this).parents("table.rowDataTable tr").remove();
			}
		});
	});


	$("input[name=ReminderYesOrNo]:radio").click(function() {
		if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
			$('.reminderBtn').show();


		} else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
			$('.reminderBtn').hide();
			$('#reminderDateid').val("");
			$('#reminderTime').val("");
			$('#taskAlertBy').val("");

		}
	});
	$('#No').prop('checked', true);



	var dateFormat = localStorage.getItem("dateFormat");

	// Get today's date
	var today = new Date();
	// Convert it to a string in the format "YYYY-MM-DD"
	var todayString = today.toISOString().split('T')[0];
	$("#callStartDate11").attr('min', todayString);

	$("#reminderCalendar11").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: todayString, // Disable past dates
	}).on("change", function() {
		$('#callStartDate11').val($(this).val());
	});


	$("#toDateCalendarTime11").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#callStartTime11').val($(this).val());
	})

	$('#callStartTime11').blur(function() {
		$("#toDateCalendarTime11").val($(this).val());
	})


	$("#DateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#dueDate').val($(this).val());
	})

	$('#dueDate').blur(function() {
		$("#DateCalendar").val($(this).val());
	})

	$("#filterDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#dateFilter').val($(this).val());
	})

	$('#dateFilter').blur(function() {
		$("#filterDateCalendar").val($(this).val());
	})

	//Personal Details DOB Date
	$("#dobCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#dueDateid').val($(this).val());
	})

	$('#dueDateid').blur(function() {
		$("#dobCalendar").val($(this).val());
	})

	// Campaign Start Date 
    $("#startDate").attr('min', todayString);
	$("#startDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: todayString, // Disable past dates
	}).on("change", function() {
		$('#startDate').val($(this).val());
	});
	
	// Campaign End Date 
    $("#endDate").attr('min', todayString);
	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: todayString, 
	}).on("change", function() {
		$('#endDate').val($(this).val());
	});


	//Personal Details DOB Date
	$("#reminderCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#reminderDateid').val($(this).val());
	})

	$('#reminderDateid').blur(function() {
		$("#reminderCalendar").val($(this).val());
	})





	$("#toDateCalendarTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#reminderTime').val($(this).val());
	})

	$('#reminderTime').blur(function() {
		$("#toDateCalendarTime").val($(this).val());
	})


	//Meeting Calendar From Date

	$("#meetingFromDate").attr('min', todayString);

	$("#meetingCalendarFromDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: todayString, // Disable past dates
	}).on("change", function() {
		$('#meetingFromDate').val($(this).val());
	});
	
	$("#meetingToDate").attr('min', todayString);

	$("#meetingCalendarToDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: todayString, // Disable past dates
	}).on("change", function() {
		$('#meetingToDate').val($(this).val());
	});

	$("#meetingCalendarFromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#meetingFromTime').val($(this).val());
	})

	$('#meetingFromTime').blur(function() {
		$("#meetingCalendarFromTime").val($(this).val());
	})


	$("#meetingCalendarRepeatFromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#meetingRepeatFromTime').val($(this).val());
	})

	$('#meetingRepeatFromTime').blur(function() {
		$("#meetingCalendarRepeatFromTime").val($(this).val());
	})


	$("#meetingCalendarRepeatToTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#meetingRepeatToTime').val($(this).val());
	})

	$('#meetingRepeatToTime').blur(function() {
		$("#meetingCalendarRepeatToTime").val($(this).val());
	})


	$("#meetingCalendarToTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#meetingToTime').val($(this).val());
	})

	$('#meetingToTime').blur(function() {
		$("#meetingCalendarToTime").val($(this).val());
	})



	$("#mySidenavTask").hide();

	var maxField = 5; // Total 5 product fields we add

	var addButton = $('.add_button'); // Add more button selector

	var wrapper = $('.field_wrapper'); // Input fields wrapper



	var fieldHTML = `<div class="form-elements">
		<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label>Field:</label> <input type="text" class="form-control"
					id="field" placeholder="field" name="field[]">
			</div>
		</div>
			<div class="col-md-4">
			<div class="form-group">
				<label>Value:</label> <input type="text" class="form-control"
					id="value" placeholder="Value" name="value[]">
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
			<a href="javascript:void(0);" class="remove_button" title="Add field">Remove</a>
			</div>
			</div>
			</div>
		</div>`; //New input field html 

	var x = 1; //Initial field counter is 1

	$(addButton).click(function() {
		//Check maximum number of input fields
		if (x < maxField) {
			x++; //Increment field counter
			$(wrapper).append(fieldHTML);
		}
	});

	//Once remove button is clicked
	$(wrapper).on('click', '.remove_button', function(e) {
		e.preventDefault();
		$(this).parent().closest(".form-elements").remove();
		x--; //Decrement field counter
	});

	$('#seeMoreButton').on("click", function() {
		$('#scrollableSection').toggleClass('show-scrollable-section');
		$('#seeMoreButton').text($('#scrollableSection').hasClass('show-scrollable-section') ? 'See Less' : 'See More..');
	});



	//viewDetails();							
	$("#date").datetimepicker({
		format: "d-m-Y",
		closeOnDateSelect: true,
		minDate: new Date(),
		timepicker: false,
	});
	$("#pipelineId").val("");

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})

	$("#fromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$("#toTime").datetimepicker({
		format: "H:i",
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})


	$('select').on('change', function() {
		var value = this.value;
		if (value == "Task") {
			var leadId = $("#leadId").text();
			$('#taskLead').val(leadId);
			$('#myModalAddTask').modal('show');
			$('#taskSubject').val('');
			$('#dueDate').val('');
			$('#taskStatus').val('');
			$('#taskPriority').val('');
			$('#descriptionTask').val('');

		} else if (value == "Meeting") {
			$('div.formValidation').remove();
			$('#myModalAddMeeting').modal('show');
			
			var textContent = $('#leadNameHead').text();
			$("#meetingLead").val(textContent);
			$("#meetingTitle").val("");
			$("#meetingLocation").val("");
			$("#meetingFromDate").val("");
			$("#meetingFromTime").val("");
			$("#meetingToDate").val("");
			$("#meetingToTime").val("");
			$("#callEndTime11").val("");
			$("#meetingStatus").val("Scheduled");
			$("#meetingStatus").attr("disabled", true).css({ "background-color": "#e9ecef", "font-size": "16px" });
			$("#descriptionMeeting").val("");
			clearParticipantsFieldVal();

		} else if (value == "Call") {
			$('div.formValidation').remove();
			$("#callSubject").val("");
			$("#relatedType").val("");
			$("#relatedName").val("");
			$("#callPurpose").val("");
			$("#callType").val("");
			$("#callPurpose").val("");
			$("#callStatus").val('Scheduled');
			$("#callStartDate11").val("");
			$("#callStartTime11").val("");
			$("#callEndTime11").val("");
			$("#callType").val("");
			$("#callAgenda").val("");
			clearParticipantsFieldVal();




			var name = $("#leadNameHead").text();
			var LeadId = $("#leadId").text();

			selectAutocompleteValueLead(name, LeadId)
			$('#myModalAddCall').modal('show');
		}
	});

	$('.numberonly').keypress(function(e) {

		var charCode = (e.which) ? e.which : event.keyCode

		if (String.fromCharCode(charCode).match(/[^0-9]/g))

			return false;

	});


	$("#toMail").blur(function() {
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddress = $("#toMail").val();
		if (!emailReg.test(emailaddress)) {
		}
	});

	var mailgridDiv = document.querySelector('#myGridMail');
	new agGrid.Grid(mailgridDiv, mailgridOptions);

	var draftgridDiv = document.querySelector('#myGridDraft');
	new agGrid.Grid(draftgridDiv, draftgridOptions);

	var rowData = [];
	mailgridOptions.api.setRowData(rowData);
	draftgridOptions.api.setRowData(rowData);

	CKEDITOR.replace('commentck', {
		enterMode: CKEDITOR.ENTER_BR,
		height: 150,
		removePlugins: 'wsc',
		// config.enterMode = CKEDITOR.ENTER_BR,
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3
	});

	CKEDITOR.replace('commentck1', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3
	});


	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#myGridProduct');
	new agGrid.Grid(gridDiv, gridOptionsProduct);

	var gridDiv = document.querySelector('#myGridCampaigns');
	new agGrid.Grid(gridDiv, gridOptionsCampaigns);

	var gridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(gridDiv, gridOptionsLead);

	var gridDiv = document.querySelector('#myGridMeetings');
	new agGrid.Grid(gridDiv, gridOptionsInvitedMeetings);


	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})
	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})
	$('#toDate').blur(function() {
		$("#endDateCalendar").val($(this).val());
	})

	/**
	 * Added By Ashish Mishra For Activity Modal close
	 */
	document.querySelectorAll(".activity-close").forEach(function(element) {
		element.addEventListener("click", function() {
			document.getElementById('activities').value = "select";
			$(".inviteMeeting").val("clearOptions");
			hideInviteMettingSearchField();
		});
	});


	/**
	 * Added by Pankaj
	 */

	/* Encode the id's */
	var leadId = $("#leadId").text();

	$("body").addClass("overlay");
	
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail?id=" + leadId,
		success: function(response) {

			if (response.code == "Success") {
				toMail = response.body[0].email;
				$('#leadId').text(response.body[0].leadId);
				$('#leadOwner').text(response.body[0].leadOwner);
				$('#leadOwnerName').text(response.body[0].leadOwner);
				$('#leadOwnerId').text(response.body[0].leadOwnerId);

				$('#leadOwner1').text(response.body[0].leadOwner);
				$('#company').text(response.body[0].company);
				
				$('#leadName').text(response.body[0].firstName + " " + response.body[0].lastName);
				$('#leadNameHead').text(response.body[0].firstName + " " + response.body[0].lastName);
				$('#leadFirstName').text(response.body[0].firstName);
				$('#leadLastName').text(response.body[0].lastName);
				$('#leadCompanyHead').text(response.body[0].company);
				//$('#leadCompanyHead').attr('title', response.body[0].company).tooltip();
				$('#leadCompanyHead').tooltip('dispose').attr('title', response.body[0].company).tooltip();
				$('#createdBy').text(response.body[0].modifyBy);
				$('#updatedBy').text(response.body[0].modifyBy);
				$('#title').text(response.body[0].title);

				$('#referenceContact').text(response.body[0].referenceContact);

				$('#email').text(response.body[0].email);
				$('#email1').text(response.body[0].email);

				$('#cityView').text(response.body[0].city);
				$('#addressStreet').text(response.body[0].addressStreet);
				$('#zip').text(response.body[0].zip);
				$('#description').text(response.body[0].description);
				$('#phone').text(response.body[0].phone);
				$('#phone1').text(response.body[0].phone);
				$('#fax').text(response.body[0].fax);
				$("#projectDtls").text(response.body[0].fax)
				$('#mobile').text(response.body[0].mobile);
				$('#mobile1').text(response.body[0].mobile);
				$('#website').text(response.body[0].website);
				var websiteUrl = response.body[0].website;

				$('#website').text(websiteUrl);
				$('#website').attr('href', websiteUrl);

				$('#website').click(function(event) {
					event.preventDefault();
					if (!websiteUrl.startsWith('http://') && !websiteUrl.startsWith('https://')) {
						websiteUrl = 'http://' + websiteUrl;
					}
					window.open(websiteUrl, '_blank');
				});

				$('#leadSource').text(response.body[0].leadSource);
				$('#leadStatus').text(response.body[0].leadStatus);
				$('#leadStatus1').text(response.body[0].leadStatus);
				$('#industry').text(response.body[0].industry);
				$('#noOfEmp').text(response.body[0].noOfEmp);
				//$('#annualRevenue').text(response.body[0].annualRevenue);
				var annualRevenue = response.body[0].annualRevenue;
				var displayedRevenue = (annualRevenue !== null && annualRevenue !== undefined && annualRevenue !== '') ? parseFloat(annualRevenue).toFixed(2) : '0.00';
				$('#annualRevenue').text(displayedRevenue);


				$('#ratings').text(response.body[0].ratings);
				$('#tags').text(response.body[0].tags);
				$('#skypeId').text(response.body[0].skypeId);
				$('#secondaryEmail').text(response.body[0].secondaryEmail);
				$('#twitter').text(response.body[0].twitter);
				$('#countryView').text(response.body[0].country);
				//getStateDataOnEdit(response.body[0].states);
				$('#statesView').text(response.body[0].states);
				/* getNote(leadId); */
				getNoteDet(leadId, "1", "", "");
				getMail(leadId);
				getProduct(leadId);
				getCampaign(leadId);
				getTask(leadId);
				getAction(leadId);
				getMeeting(leadId);
				getCall(leadId);
				getActivity(leadId, "allTimeline");
				getProductActivity(leadId, "productTimeline");
				getDraft(leadId);
				getTimeline(leadId, "allTimeline");
				getCount(leadId);
				//getInvitedMeetings(leadId);
				ownerId = response.body[0].leadOwnerId;
				userId = $('#userId').val();

				// Lead Sttaus Dropdown List Hide 
				var leadStatusDropdown = document.getElementById('leadStatusList');
				var selectedStatusValue = response.body[0].leadStatusId;

				for (var i = 0; i < leadStatusDropdown.children.length; i++) {
					var listItem = leadStatusDropdown.children[i];
					var listItemKey = listItem.querySelector('a').getAttribute('data-key');

					if (listItemKey <= selectedStatusValue) {
						listItem.style.display = 'none';
					}
				}

				if (ownerId != userId) {
					$(".senMail").hide();
					$(".convertLeads").hide();
					$(".editLeadData").hide();
					$(".primarybtns").hide();
					$(".composebtn").hide();
					$("#activities").hide();
					$(".cancelLead").show();
					$(".statusEditIcon").hide();
					$(".editStatusBtn").attr('disabled', true);

					if (response.body[0].adminApprvStatus) {
						$(".apprv").hide();
					} else {
						if (response.body[0].leadStatus == 'Approval Pending') {
							$(".apprv").show();
						} else {
							$(".apprv").hide();
						}
					}

					if (response.body[0].leadStatus == "Junk Lead") {
						$(".apprv").hide();
					}


				}

				if (response.body[0].adminApprvStatus) {
					$(".sucessAprovBtn").show();
				}

				if (response.body[0].leadStatus == "Junk Lead") {
					$(".failureAprovBtn").show();
				}


				if (ownerId == userId) {
					$(".apprv").hide();
				}


				$("body").removeClass("overlay");
			}
		}
	});

	document.getElementById("ccMail").addEventListener("keyup", function() {


		let searchVal = "";

		searchVal = $("#ccMail").val();
		if (searchVal == "") {
			$("#suggesstion-mailBox_").hide();
		}

		searchVal != "" && searchVal != null && searchVal != "null" ? getMailsAutosearch(searchVal) : console.log("No Data Found!");
	});

});





/*
*
**** This Function used for get Invite the Participants in Call & Meetings 
*  /* For Contact Parcitipants
*/
function getParticipantLead(searchVal, dropdown) {

	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val()


	if (searchVal == "") {
		$(".suggesstion-boxmeetingLead1_").hide();
	}
	let searchId = $("#leadId").text();
	getNameListParticipants1(searchId, val, searchVal, "lead");
}

function getParticipantContact(searchVal, dropdown) {

	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val();

	if (searchVal == "") {
		$(".suggesstion-boxmeetingContact1_").hide();
	}
	let searchId = '';
	getNameListParticipants1(searchId, val, searchVal, "contact");
}

function getParticipantExcecutive(searchVal, dropdown) {
	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val()

	if (searchVal == "") {
		$(".suggesstion-boxmeetingExcutives_").hide();
	}
	let searchId = $("#userId").val();
	getNameListParticipants1(searchId, val, searchVal, "excutive");
}

function assignMeetings() {
	$('#myModalAddCall').modal('show');
}
function saveCampaignInfo() {
	var dataset = [];
	for (let i = 0; i < searchIDs.length; ++i) {

		item = {};
		item['leadId'] = searchIDs[i];
		item['campaignType'] = $("#campaignType").val();
		item['campaignName'] = $("#campaignName").val();
		item['campaignStatus'] = $("#campaignStatus").val();
		item['startDate'] = $("#startDate").val();
		item['endDate'] = $("#endDate").val();
		item['campExpectRevenue'] = $("#campExpectRevenue").val();
		dataset.push(item);
	}

	saveCampaign(dataset);
}



function saveCampaign(dataset) {

	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-campaigns",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text(
					"Data saved successfully");


				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#myGrid").show();
				$("#demo").hide();
				$("#new1").show();
				$("#demo1").hide();
				$("#delete1").show();
				$("#totalReq").show();
				$("#searchRowDiv").show();

			}
		},
		error: function(datas) {
		}
	})

}

function addMacroInfo() {
	var dataset = [];
	var emailType = $("input:radio[name=macroEmailType]:checked").val();


	$("#tbodyMacro > tr").each(function() {
		for (let i = 0; i < searchIDs.length; ++i) {
			item = {};

			item['leadId'] = searchIDs[i];
			item['toMail'] = emails[i];
			item['macroName'] = $("#macroName").val();
			item['macroDescription'] = $("#macroDescription").val();
			item['emailType'] = $("#emailType").val();
			item['fieldName'] = $(this).find(".fieldName").html();
			item['fieldValue'] = $(this).find(".fieldValue").html();

			dataset.push(item);
		}

	});


	saveMacro(dataset);
}



function saveMacro(dataset) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-macro",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text(
					"Data saved successfully");


				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#myGrid").show();
				$("#demo").hide();
				$("#new1").show();
				$("#demo1").hide();
				$("#delete1").show();
				$("#totalReq").show();
				$("#searchRowDiv").show();

			}
		},
		error: function(datas) {
		}
	})

}

function addTagsInfo() {
	var dataset = [];
	var isOverWrite = $("input:checkbox[name=IsOverwriteExistingTags]:checked").val();
	for (let i = 0; i < searchIDs.length; ++i) {
		item = {};
		item['leadId'] = searchIDs[i];
		item['tagsName'] = $("#tagsName").val();
		item['isOverWrite'] = isOverWrite;
		dataset.push(item);
	}

	saveTags(dataset);
}


function saveTags(dataset) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-tags",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text(
					"Data saved successfully");


				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#myGrid").show();
				$("#demo").hide();
				$("#new1").show();
				$("#demo1").hide();
				$("#delete1").show();
				$("#totalReq").show();
				$("#searchRowDiv").show();

			}
		},
		error: function(datas) {
		}
	})

}



function addMailInfo() {
	var dataset = [];
	for (let i = 0; i < searchIDs.length; ++i) {
		item = {};
		item['leadId'] = searchIDs[i];
		item['fromEmail'] = $("#fromEmail").val();
		item['toMail'] = emails[i];
		item['mailSubject'] = $("#mailSubject").val();
		item['docnoid'] = $("#docnoid_").val();
		item['attachment'] = $("#attachment").val();
		item['mailDescription'] = $("#description").val();

		dataset.push(item);
	}

	saveMail(dataset);
}

function saveMail(dataset) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-mail",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text(
					"Data saved successfully");


				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#myGrid").show();
				$("#demo").hide();
				$("#new1").show();
				$("#demo1").hide();
				$("#delete1").show();
				$("#totalReq").show();
				$("#searchRowDiv").show();

			}
		},
		error: function(datas) {
		}
	})

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

	if (extension[1] == "jpg" || extension[1] == "png") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox" || extension[1] == "docx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
}


function saveMultiFileForLead(event) {
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

	if (extension[1] == "jpg" || extension[1] == "png") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox" || extension[1] == "docx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
}


function openNavTask() {
	document.getElementById("mySidenavTask").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:420px;";

	document.getElementById("mainTask").style.width = "75%";
}

function checkForDealAdd() {
	var dealcheck = $("input:checkbox[name=dealcheck]:checked").val();
	if (dealcheck == 'on') {
		$('#ifCreateDeal').show();
	} else {
		$('#ifCreateDeal').hide();

	}
}

var productDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	//pinned: 'left',
},
{
	headerName: "Product Id",
	field: "productId",
	width: 130,
	//pinned: 'left',
	cellRenderer: function(params) {
		return '<a onclick=openDetails("' + params.data.productId + '") href="javascript:void(0)">' + params.data.productId + '</a>';
	}
},
{
	headerName: "Product Name",
	field: "productName",
	width: 200,
	//pinned: 'left',
},
{
	headerName: "Brand",
	field: "brand",
	cellStyle: { textAlign: 'center' }

},
{

	headerName: "Category",
	field: "productCategoryText",
	width: 250,
},
{
	headerName: "Mode",
	field: "mode",
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Status",
	field: "productStatus",
	cellStyle: { textAlign: 'center' }
},
{

	headerName: "Created By",
	field: "createdBy",
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Create Date",
	field: "createdDate",
	cellStyle: { textAlign: 'center' }
}]
var gridOptionsProduct = {
	columnDefs: productDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	}
};

/* -----------------Campaigns Grid Start------------------ */
var campaignDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Campaign Name",
	field: "campaignName",
	pinned: 'left',
	width: 150,
	cellRenderer: function(params) {
		if (ownerId != userId) {
			return '<span><strong>' + params.data.campaignName + '</strong></span>';

		} else {
			return '<a onclick="if ($(\'#userRole\').val() !== \'rol001\') { openCampaignDetails(\'' + params.data.campaignId + '\'); getUrl(\'MOD031\', \'' + sessionStorage.getItem("campaignFun") + '\', \'' + sessionStorage.getItem("campaignAct") + '\'); }" href="javascript:void(0)">' + params.data.campaignName + '</a>';

		}
	}
},
{
	headerName: "Status",
	field: "campaignStatus",
	width: 200,
	pinned: 'left',
},
{
	headerName: "Type",
	field: "campaignType",
	cellStyle: { textAlign: 'center' },
	width: 200,
	pinned: 'left',
},
{

	headerName: "Start Date",
	field: "startDate",
	width: 250,
	pinned: 'left',
},
{
	headerName: "End Date",
	field: "endDate",
	cellStyle: { textAlign: 'left' },
	width: 250,
},
{
	headerName: "Expected Revenue",
	field: "expectedRevenue",
	width: 250,
	cellStyle: { textAlign: 'right' }
},
{

	headerName: "Budgeted Cost",
	field: "budgetedCost",
	width: 250,
	cellStyle: { textAlign: 'right' }

}]
var gridOptionsCampaigns = {
	columnDefs: campaignDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	},
	onSelectionChanged: onSelectionGetCampaign,
	
};

/* -----------------Invited Meetings Grid Start------------------ */
var meetingsDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: false,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Title",
	field: "meetTitle",
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a href="javascript:void(0)">' + params.data.meetTitle + '</a>';
	}
},
{
	headerName: "Meeting From",
	field: "meetingFromDate",
	width: 200,
	pinned: 'left',
	cellRenderer: function(params) {
		// Format the date and time
		var formattedDate = formatDateTime(params.data.meetingFromDate);
		return formattedDate;
	}
},
{
	headerName: "Meeting To",
	field: "meetingToDate",
	width: 200,
	pinned: 'left',
	cellRenderer: function(params) {
		// Format the date and time
		var formattedDate = formatDateTime(params.data.meetingToDate);
		return formattedDate;
	}
},
{
	headerName: "Status",
	field: "meetStatus",
	width: 200,
	pinned: 'left',

}];
var gridOptionsInvitedMeetings = {
	columnDefs: meetingsDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	}
};

function formatDateTime(dateTimeStr) {
	// Correct regular expression to include space as a separator
	var parts = dateTimeStr.split(/[- :]/);

	// Adjust indices as needed based on the corrected regular expression
	var dateTime = new Date(parts[0], parts[1] - 1, parts[2], parts[3], parts[4]);

	var options = {
		weekday: 'long',
		month: 'short',
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		hour12: true,
	};

	var formattedDateTime = dateTime.toLocaleDateString('en-US', options);

	return formattedDateTime;
}
var leadDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Product Id",
	field: "productId",
	width: 130,
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a onclick=openDetails("' + params.data.productId + '")">' + params.data.productId + '</a>';
	}
},
{

	headerName: "Product Name",
	field: "productName",
	width: 250,
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Brand",
	field: "productBrand",
	width: 250,
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Category",
	field: "productCategories",
	width: 250,
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Created By",
	field: "createdBy",
	width: 250,
	cellStyle: { textAlign: 'left' }
},
{

	headerName: "Create Date",
	field: "createdDate",
	width: 250,
	cellStyle: { textAlign: 'left' },




}]
var gridOptionsLead = {
	columnDefs: leadDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10
	},
	onSelectionChanged: onSelectionChanged,
};

var columnDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Serial No",
		field: "goalId",
		width: 150,
		cellRenderer: function(params) {
			if (params.data.goalId) {

				return '<a onclick=editReimbruseimentTravel("'
					+ params.data.goalId
					+ '") href="javascript:void(0)">'
					+ params.data.goalId + '</a>';
			} else {
				return '<a onclick=editReimbruseimentTravel("'
					+ params.data.goalId
					+ '") href="javascript:void(0)">'
					+ params.data.goalId + '</a>';
			}
		}
	}, {
		headerName: "Field Name",
		field: "goalName",
		cellStyle: {
			textAlign: 'left'
		},
		width: 457,
	}, {
		headerName: "New Value",
		field: "goalDesc",
		cellStyle: {
			textAlign: 'left'
		},
		width: 600,
	}];


function filterNoteByDate() {
	$("#noteListWithDoc").empty();
	var filterDate = $("#dateFilter").val();
	var filterTitle = $("#noteTitleSearch").val();
	var leadId = $("#leadId").text();

	statuss = true;
	getNoteDet(leadId, "1", filterDate, filterTitle);
}


var statuss = true;
var leadId = $("#leadId").text();
function getNoteDet(leadId, cuPages, filterDate, filterTitle) {
	getNoteAll(leadId, cuPages, filterDate, filterTitle);
}




let doclen = '';
function editNote(id) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-edit-note?id=" + id,
		success: function(response) {
			var resp = JSON.parse(response.body[0]);
			$('#leadNoteId').val(resp.NoteDetails[0].noteId);
			$('#titleId').val(resp.NoteDetails[0].noteTitle);
			$('#noteId').val(resp.NoteDetails[0].noteDesc);
			$('#documentName').val(resp.NoteDetails[0].noteDocName);

			var doclist = JSON.parse(resp.NoteDetails[0].documentList);
			docListlen = doclist.length;
			$('#docListlen').val(docListlen);
			if (doclist.length > 0) {
				var LightImg = '';
				for (let i = 0; i < doclist.length; i++) {
					var ext = doclist[i].fileName.split(".");
					$("#divFiles").html(doclist[i].fileName);
					if (ext[1] == "jpg" || ext[1] == "png") {
						LightImg += '<input type="hidden" id="uploadHidden_' + i + '" value="' + doclist[i].fileName + '" class="uploadHidCls">'
							+ '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
							+ doclist[i].fileName + '")></i></a>' + doclist[i].fileName + '</div>';
					} else if (ext[1] == "pdf") {
						LightImg += '<input type="hidden" id="uploadHidden_' + i + '" value="' + doclist[i].fileName + '" class="uploadHidCls">'
							+ '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
							+ doclist[i].fileName + '")></i></a> ' + doclist[i].fileName + '</div>';
					} else {
						LightImg += "<div class='uploadicon position-l'> </div>";
					}
					$("#divFiles").html(LightImg);
				}

			} else {
				LightImg += '<tr><td>'
					+ '<div class="control-group position-r">'
					+ '<label class="custom-file-upload" for="fileUploader"'
					+ 'id="uploadFor_0"> <i class="ti-plus"></i>'
					+ '</label>'
					+ '<div class="controls">'
					+ '<input type="file" class="document" id="fileUploader"'
					+ 'name="userImage"  multiple onchange="updateList()"/>'
					+ '</div></div>'
					+ '<div id="divFiles">'
					+ '<input type="hidden" id="uploadHidden_" class="uploadHidCls"/>'
					+ '</div></td>'
					+ '<td><a class="primarybtns" onclick="saveNoteWithDoc()">Save</a></td>'
					+ '</tr>'
				$("#doctbodyData").html(LightImg);
			}

		}
	})
}
var noteid = '';
function deleteNote(id) {
	$("#deleteModal").show();
	noteid = id;
}

function deleteNoteOnclick() {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-delete-note?id=" + noteid,
		success: function(response) {

			if (response.message == "Success") {
				location.reload();
			}
		}
	});

}
function viewImage(id) {
	window.open("/document/crm/" + id, '_blank');
}
function cancelDeleteModalBtn() {
	$("#deleteModal").hide();
}
function getMail(leadId) {
	$("#countEmail").empty().append(0);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-mail?id=" + leadId,
	}).then(function(data) {

		var resp = JSON.parse(data.body[0]);
		if (resp.mailList != null && resp.mailList != "") {
			var len = resp.mailList.length;
			$('#totalEmp').find('span').html(len);
			$("#countEmail").empty().append(len);
			mailgridOptions.api.setRowData(resp.mailList);
		}

	});


}

function viewEmailDetails(id, tomail) {
	$('#myModalViewEmail').modal('show');

	var empId = $("#leadId").text();

	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-email-view?id1=" + empId + "&id2=" + id + "&id3=" + tomail,
		async: false,
		success: function(response) {

			if (response.message == "success") {
				$("#fromEmail1").val(response.body.fromEmail);
				$("#toMail1").val(response.body.toMail);
				$("#mailSubject1").val(response.body.mailSubject);
				$("#docName1").val(response.body.docnoid);



				$("#emailAttachmentView").text(response.body.attachment);
				$("#emailAttachmentView").attr("href", response.body.ownerImageLink);


				CKEDITOR.instances['commentck1'].setData(response.body.commentck);
				$('#myModalViewEmail').modal('show');



			}
		},
		error: function(data) {
			$("#reqId").val("");
			$("#date").val("");
			$("#eligibility").val("");
			$("#loanamt").val("");
			$("#ternure").val("");
			$("#intrestRate").val("");
			$("#status").val("");
		}
	});

}

function getCampaign(leadId) {
	$("#countCampaign").empty();
	agGrid.simpleHttpRequest({
		url: 'view-crm-leads-view-detail-campaign?id=' + leadId,

	}).then(function(data) {
		var len = data.body.length
		$("#countCampaign").append(len);

		gridOptionsCampaigns.api.setRowData(data.body);

	});
}

function openCampaignDetails(campaignId) {

	localStorage.setItem('campaignId', campaignId);


}



function editMeetingTitle(meetingId) {
	localStorage.setItem('meetingId', meetingId);

}

function editTask(taskId) {
	localStorage.setItem('activityTaskId', taskId);

}

function editLeadData() {
	var leadId = $("#leadId").text();
	localStorage.setItem('leadId', leadId);
	localStorage.setItem("edited", 1);
}

function getTask(leadId) {


	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-task?id=" + leadId,
		success: function(response) {

			if (response.message == "Success") {

				$("#taskActivity").empty(); // Clear the existing content
				trCount = '';
				var countItm = response.body.length;
				var countOpen = 0, countClose = 0;
				for (var i = 0; i < response.body.length; i++) {

					var taskStatus = response.body[i].taskStatus;
					taskId = response.body[i].taskId;

					let redirectUrl = "";
					if (ownerId != userId) {
						redirectUrl = '<span  href="javascript:void(0)" class="toptxt" >' + response.body[i].taskSubject + '</span>'
					}
					else {
						redirectUrl = '<a  href="javascript:void(0)" class="toptxt" onclick="editTask(\'' + taskId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("taskFun") + '\', \'' + sessionStorage.getItem("taskAct") + '\')">' + response.body[i].taskSubject + '</a>'
					}


					if (taskStatus != "Completed") {
						var mailRow = '<li>'
							+ redirectUrl
							+ '<div class="smalltxt">' + response.body[i].dueDate + '</div>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].taskOwner + '</div>'
							+ '<p>Status : ' + response.body[i].taskStatus + '</p>'
							+ '<p>Priority : ' + response.body[i].taskPriority + '</p>'

							+ '</li>';
						trCount = mailRow;
						$("#taskActivity").append(trCount);
						countOpen = countOpen + 1;

					} else {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].taskSubject + '</div>'
							+ '<div class="smalltxt">' + response.body[i].dueDate + '</div>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].taskOwner + '</div>'
							+ '<p>Status : ' + response.body[i].taskStatus + '</p>'
							+ '<p>Priority : ' + response.body[i].taskPriority + '</p>'

							+ '</li>';
						trCount = mailRow;
						$("#closedTaskActivity").append(trCount);
						countClose = countClose + 1;


					}


				}
				$("#openTasks").text(countOpen);
				localStorage.setItem("countOpen", countOpen);

				$("#closeTasks").text(countClose);
				localStorage.setItem("countClose", countClose);
				getCount(leadId);
				if (countItm < 1) {
					var mailRow = '<li>'
						+ '<div >No Record Found</div>'

						+ '</li>'
					trCount = mailRow;
					$("#taskActivity").append(trCount);
				}


			}
		}
	});
}







//getMeeting


function getMeeting(leadId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-meeting?id=" + leadId,
		success: function(response) {

			if (response.message == "Success") {
				$("#meetingActivity").empty(); // Clear the existing content
				trCount = '';
				var countItm = response.body.length;
				acticityCount += response.body.length;
				var countOpenM = 0, countCloseM = 0;
				for (var i = 0; i < response.body.length; i++) {

					var meetingStatus = response.body[i].meetingStatus;
					meetingId = response.body[i].meetingId;
					if (meetingStatus != "Completed") {

						let redirectUrl = "";

						if (ownerId != userId) {
							redirectUrl = '<span href="javascript:void(0)" class="toptxt">' + response.body[i].meetingTitle + '</span>'
						}
						else {
							redirectUrl = '<a href="javascript:void(0)" class="toptxt" onclick="editMeetingTitle(\'' + meetingId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("meetingFun") + '\',\'' + sessionStorage.getItem("meetingAct") + '\')">' + response.body[i].meetingTitle + '</a>'
						}

						var mailRow = '<li>'

							+ redirectUrl
							// + '<a href="javascript:void(0)" class="toptxt" onclick=editMeetingTitle("'+id+'")>' + response.body[i].meetingTitle + '</a>'
							+ '<div class="smalltxt">Meeting Status : ' + response.body[i].meetingStatus + '</div>'
							+ '<p>Meeting From : ' + response.body[i].meetingFromDate + ' - <span> ' + response.body[i].meetingFromTime + '</span></p>'
							+ '<p>Meeting To : ' + response.body[i].meetingToDate + ' - <span> ' + response.body[i].meetingToTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].meetingHost + '</div>'

							+ '</li>';

						trCount = mailRow;
						$("#meetingActivity").append(trCount);
						countOpenM = countOpenM + 1;


					} else {

						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].meetingTitle + '</div>'
							+ '<div class="smalltxt">Meeting Status : ' + response.body[i].meetingStatus + '</div>'
							+ '<p>Meeting From : ' + response.body[i].meetingFromDate + ' - <span> ' + response.body[i].meetingFromTime + '</span></p>'
							+ '<p>Meeting To : ' + response.body[i].meetingToDate + ' - <span> ' + response.body[i].meetingToTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].meetingHost + '</div>'

							+ '</li>';

						trCount = mailRow;
						$("#closedMeetingActivity").append(trCount);
						countCloseM = countCloseM + 1;

					}


				}

				$("#openMeetings").text(countOpenM);
				localStorage.setItem("countOpenM", countOpenM);
				$("#closeMeetings").text(countCloseM);
				localStorage.setItem("countCloseM", countCloseM);
				getCount(leadId);
				if (countItm < 1) {
					var mailRow = '<li>'
						+ '<div >No Record Found</div>'

						+ '</li>'
					trCount = mailRow;
					$("#meetingActivity").append(trCount);
				}

			}
		}
	});
}

function getCall(leadId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-call?id=" + leadId,
		success: function(response) {

			if (response.message == "Success") {
				$("#callActivity").empty(); // Clear the existing content
				trCount = '';
				var countItm = response.body.length;

				var countOpenC = 0, countCloseC = 0;
				for (var i = 0; i < response.body.length; i++) {

					var callStatus = response.body[i].callStatus;
					callId = response.body[i].callId;

					if (callStatus != "Completed") {

						let redirectUrl = "";

						if (ownerId != userId) {
							redirectUrl = '<span href="javascript:void(0)" class="toptxt" >' + response.body[i].callSubject + '</span>'
						} else {
							redirectUrl = '<a href="javascript:void(0)" class="toptxt" onclick="editCalls(\'' + callId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("callFun") + '\',\'' + sessionStorage.getItem("callAct") + '\')">' + response.body[i].callSubject + '</a>'

						}
						var mailRow = '<li>' 
					    + redirectUrl 
					    + '<p>Call Scheduled for: <span>' 
					    + response.body[i].callStartDate 
					    + '</span> - <span>' 
					    + response.body[i].callStartTime 
					    + '</span> to <span>' 
					    + response.body[i].callEndTime 
					    + '</span></p>'
					    + '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' 
					    + response.body[i].callOwner 
					    + '</div>'
					    + '<p>Call Status: ' 
					    + response.body[i].callStatus 
					    + '</p>'
					    + '</li>';

						trCount = mailRow;
						$("#callActivity").append(trCount);
						countOpenC = countOpenC + 1;

					}

					else {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].callSubject + '</div>'
							+ '<p>Call At : ' + response.body[i].callStartDate + ' - <span> ' + response.body[i].callStartTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].callOwner + '</div>'
							/*+ '<div class="smalltxt">Call Related To : ' + response.body[i].callToWhom + '</div>'*/
							+ '<p>Call Status : ' + response.body[i].callStatus + '</p>'

							+ '</li>';


						trCount = mailRow;
						$("#closedCallActivity").append(trCount);

						countCloseC = countCloseC + 1;

					}

				}

				$("#openCalls").text(countOpenC);
				localStorage.setItem("countOpenC", countOpenC);
				$("#closeCalls").text(countCloseC);
				localStorage.setItem("countCloseC", countCloseC);
				getCount(leadId);

				if (countItm < 1) {
					var mailRow = '<li>'
						+ '<div >No Record Found</div>'

						+ '</li>'
					trCount = mailRow;
					$("#callActivity").append(trCount);
				}
			}
		}
	});
}


function getCount(leadId) {

	var countOpen = parseInt(localStorage.getItem("countOpen")) || 0;
	var countOpenM = parseInt(localStorage.getItem("countOpenM")) || 0;
	var countOpenC = parseInt(localStorage.getItem("countOpenC")) || 0;

	var countClose = parseInt(localStorage.getItem("countClose")) || 0;
	var countCloseM = parseInt(localStorage.getItem("countCloseM")) || 0;
	var countCloseC = parseInt(localStorage.getItem("countCloseC")) || 0;


	var total = countOpen + countOpenM + countOpenC;
	var total1 = countClose + countCloseM + countCloseC;

	$("#countActivity").text(total);
	$("#countCloseActivity").text(total1);


}
// view Draft details
function getDraft(leadId) {

	$("#countDraft").empty();
	var rowData = [];
	draftgridOptions.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-draft?id=" + leadId,
	}).then(function(data) {

		var resp = JSON.parse(data.body[0]);
		if (resp.draftList != null && resp.draftList != "") {
			var len = resp.draftList.length;
			$("#countDraft").append(len);
			$('#totalEmp').find('span').html(len);
			draftgridOptions.api.setRowData(resp.draftList);
		} else {

			draftgridOptions.api.setRowData([]);
		}


	});


}
//addCallinfo

function addCallInfo() {



	var executiveSelect = $("#meetingHost");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';
	if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	}

	var id = $("#callToWhom").val();
	var obj = {};
	obj.pageType = "Lead";
	obj.callLeadId = $('#leadId').text();
	obj.callId = $('#callId').text();
	obj.ownerName = $('#leadOwner1').text();
	obj.callToWhom = $('#callToWhom').val();
	obj.leadName = $('#lName').val();
	obj.leadId = $('#leadId').val();

	obj.contactName = $('#cName').val();
	obj.contactId = $('#contactId').val();
	obj.relatedType = $('#relatedType').val();
	obj.relatedName = $('#relatedName').val();
	obj.relatedId = $('#relatedId').val();
	obj.callType = $('#callType').val();
	obj.callStatus = $('#callStatus').val();
	obj.callStartDate = $('#callStartDate11').val();
	obj.callStartTime = $('#callStartTime11').val();
	obj.callEndTime = $('#callEndTime11').val();

	obj.callOwner = $('#callOwner').val();
	obj.callSubject = $('#callSubject').val();
	obj.callReminder = $('#callReminder').val();
	obj.callPurpose = $('#callPurpose').val();
	obj.callAgenda = $('#callAgenda').val();
	obj.toMail = toMail;
	obj.ccMail = ccMeetingMail;
	obj.excutiveMail = executiveMail;
	/* FORM VALIDATION STARTS*/

	var validation = true;
	if (obj.callStatus == null || obj.callStatus == "") {
		validation = validationUpdated("Call Status Required",
			"callStatus");
	}

	if (obj.callSubject == null || obj.callSubject == "") {
		validation = validationUpdated("Subject Required",
			"callSubject");
	}
	if (obj.callPurpose == null || obj.callPurpose == "") {
		validation = validationUpdated("Call purpose Required",
			"callPurpose");
	}
	if (obj.callStartDate == null || obj.callStartDate == "") {
		validation = validationUpdated("Call Date Required",
			"callStartDate11");
	}
	if (obj.callStartTime == null || obj.callStartTime == "") {
		validation = validationUpdated("Start Time Required",
			"callStartTime11");
	}
	if (obj.callEndTime == null || obj.callEndTime == "") {
		validation = validationUpdated("End Time Required",
			"callEndTime11");
	}


	/* FORM VALIDATION ENDS*/
	var leadId = $("#leadId").html();
	if (validation) {
		closeModelCall();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-call-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					getActivity(leadId, "allTimeline");
					getTimeline(leadId, "allTimeline");
					getCall(leadId);
					mailSucessAlert();


					$("#messageParagraph").text("Call Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}

			},
			error: function(data) {

			}
		})
		document.getElementById('activities').value = "select";
	}

}

function getNameList1() {

	var relatedType = $("#relatedType").val();
	var searchVal = $("#relatedName").val();
	if (searchVal == "") {
		$("#suggesstion-box2_").hide();
	}
	if (relatedType && searchVal) {
		$.ajax({
			type: "GET",
			url: "view-crm-calls-autosearchDetailsRelated?id1=" + relatedType + "&id2=" + searchVal,
			dataType: 'json',
			contentType: 'application/json',
			success: function(response) {
				if (response.message == "success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue11(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteValue0()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})


	}


}

function selectAutocompleteValue11(name, relatedId) {

	if (name) {
		$("#relatedId").val(relatedId);
		$("#relatedName").val(name);
		$("#search").val(relatedId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box2_").hide();

	} else {
		$("#relatedId").val("");
		$("#relatedName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box2_").hide();

	}
}
function selectAutocompleteValue0() {

	$("#relatedId").val("");

	$("#personName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

}



function getNameList() {
alert("hii")
	var callToWhom = $("#callToWhom").val();
	var lName = $("#lName").val();
	var cName = $("#cName").val();
	if (lName == "") {
		$("#suggesstion-boxLead_").hide();
	}
	if (cName == "") {
		$("#suggesstion-boxContactCall_").hide();
	}

	if (callToWhom == 'Lead') {

		var searchVal = lName;

		$.ajax({
			type: "POST",
			url: "view-crm-calls-autosearchDetailsLead",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueLead(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxLead_").show();
						$("#suggesstion-boxLead_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteContactValueLead()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxLead_").show();
						$("#suggesstion-boxLead_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})
	}
	else {
		var searchVal = cName;
		$.ajax({
			type: "POST",
			url: "view-crm-calls-autosearchDetailsContact",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueContactCall(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxContactCall_").show();
						$("#suggesstion-boxContactCall_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectcontactcall()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxContactCall_").show();
						$("#suggesstion-boxContactCall_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})



	}


}



function selectAutocompleteValueLead(name, LeadId) {
alert("auto")
	if (name) {

		//$("#personName").val("");
		$("#leadId").val(LeadId);

		$("#lName").val(name);
		$("#search").val(LeadId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxLead_").hide();

	} else {
		$("#leadId").val("");

		$("#lName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxLead_").hide();

	}
}
function selectAutocompleteContactValueLead() {

	$("#leadId").val("");

	$("#lName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxLead_").hide();

}


function selectAutocompleteValueContactCall(name, LeadId) {

	if (name) {

		//$("#personName").val("");
		$("#contactId").val(LeadId);

		$("#cName").val(name);
		$("#search").val(LeadId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxContactCall_").hide();

	} else {
		$("#leadId").val("");

		$("#cName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxContactCall_").hide();

	}
}
function selectcontactcall() {

	$("#leadId").val("");

	$("#cName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxContactCall_").hide();

}



function selectAutocompleteValueContact(name, ContactId) {

	if (name) {

		//$("#personName").val("");
		$("#contactId").val(ContactId);

		$("#contactName").val(name);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxContact_").hide();

	} else {
		$("#contactId").val("");

		$("#contactName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxContact_").hide();

	}
}
function selectAutocompleteContactValue() {

	$("#contactId").val("");

	$("#contactName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxContact_").hide();

}
function editCalls(callId) {
	localStorage.setItem('callId', callId);
}


function getTimeline(leadId, type) {
	var promise3 = new Promise(function(resolve, reject) {

		$.ajax({
			type: "GET",
			url: "view-crm-leads-view-detail-activity?id=" + leadId + "&type=" + type,
			async: true,
			success: resolve,
			error: reject,
		});
	});
	promise3.then(function(response) {
		if (response.message === "Success") {
			$("#updatedDates").empty();
			var createdTime = response.body[0].createdTime;
			$("#updatedDates").append(createdTime);



		} else {
			console.log("Promise was rejected or the response was not successful.");
		}
	});
}

/**
 * Chnages For Activity Timeline Added by Ashish Mishra
 */
/**
 * getActivity
 */
function getActivity(leadId, type) {

	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-activity?id=" + leadId + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				$("#main-timeline-section").empty();
				var countItm = response.body.length;

				getActivityTimeline(response.body);


			}
		}
	});
}

function getProductActivity(leadId, type) {

	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-activity?id=" + leadId + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				$("#products-timeline-section").empty();
				var countItm = response.body.length;

				getActivityTimelineProducts(response.body);


			}
		}
	});
}

//convConAccDeal

function convConAccDeal() {

	var company = $("#company").text();
	var leadName = $("#leadName").text();

	$("#createNewAccount").text(company);
	$("#createNewContact").text(leadName);

	$("#headLeadName").text('(' + leadName);
	$("#headCompanyName").text(company + '.)');


	$("#leadcontent").hide();
	$("#convertedPage").show();
}

function cancelConvert() {
	$("#leadcontent").show();
	$("#convertedPage").hide();
}

var expanded = false;

function showCheckboxes() {
	var checkboxes = document.getElementById("checkboxes");
	if (!expanded) {
		checkboxes.style.display = "block";
		expanded = true;
	} else {
		checkboxes.style.display = "none";
		expanded = false;
	}
}


function showCheckboxesSecond() {
	var checkboxes = document.getElementById("checkboxes2");
	if (!expanded) {
		checkboxes.style.display = "block";
		expanded = true;
	} else {
		checkboxes.style.display = "none";
		expanded = false;
	}
}


function openNav() {

	$('#goalName').val("");
	$('#expectedResult').val("");
	$('#weightage').val("");

	document.getElementById("mySidenav").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";

	document.getElementById("main_content").style.width = "75%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
}

function openNav1() {

	$('#fromtime').val("");
	$('#totime').val("");

	document.getElementById("mySidenav1").style.cssText = "width: 250px; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";

	document.getElementById("main").style.width = "100%";
}

function closeNav1() {
	document.getElementById("mySidenav1").style.width = "0";
	document.getElementById("main").style.width = "100%";
}




var count = 0;
function allCheck() {
	count++;

	if (count == 1) {
		$('.checkCls').prop("checked", true);
	} else {
		count = 0;
		$('.checkCls').prop("checked", false);
	}
}

var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
}


const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	getRowNodeId: function(data) {
		return data.customerId;
	}
};




// for new button
function newBtn() {
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	//$("#myGrid").hide();
	$(".container").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();
	$("#deleteLead").hide();
	$("#demo").show();

}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$(".container").show();
	$("#searchRowDiv").show();
	$("#demo").hide();
	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMassConvert").hide();


	$('#pipelineId').val("");
	$('#oppertunity').val("");
	$('#expectedRevenue').val("");
	$('#probability').val("");
	$('#customer').val("");
	$('#email').val("");
	$('#phone').val("");
	$('#saleperson').val("");
	$('#salesTeam').val("");
	$('#date').val("");
	$("#star1").attr("checked", false);
	$("#star2").prop("checked", false);
	$("#star3").prop("checked", false);
	//$('#tags').val("");
	$('#campanyName').val("");
	$('#description').val("");
	$('#campanyName').val("");
	$('#addressStreet').val("");
	$('#addressStreet2').val("");
	$('#cityView').val("");
	$('#statesView').val("");
	$('#zip').val("");
	$('#countryView').val("");
	$('#website').val("");
	$('#language').val("");
	$('#contactName').val("");
	$('#tittle').val("");
	$('#jobPosition').val("");
	$('#mobile').val("");
	$('#referdBy').val("");
	$('#campaign').val("");
	$('#medium').val("");
	$('#source').val("");

	agGrid.simpleHttpRequest({
		url: "view-crm-leads-getDetails"
	}).then(function(data) {
		gridOptions.api.setRowData(data);
	});
}

var id = [];
function viewDetails() {
	$
		.ajax({
			type: "GET",
			url: "view-crm-leads-view-Data",
			async: false,
			success: function(response) {

				if (response.message == "Success") {
					div = '';

					for (var i = 0; i < response.body.length; i++) {
						var im = response.body[i].imageName;
						var img = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="' + im + '" width="80" alt="User" class="mr-3 img-thumbnail" /></a>';
						id = response.body[i].leadId;

						var xyx = '<div class="card mb-2"><div class="card-body p-2 p-sm-3 d-flex"><div class="col-md-8">'
							+ '<div class="media forum-item d-flex">'
							+ '<input type="checkbox" id="myCheckbox" name="myCheckbox" class="checkbox_check" value="' + id
							//+ '" onclick=clickCheckBox("'+id
							+ '" onclick=clickCheckBox("' + id + '","' + response.body[i].email
							+ '")><br>'
							+ '<a href="#" data-toggle="collapse" onclick=editLeadInfo("' + id
							+ '")><i class="fa fa-edit" aria-hidden="true" '
							+ '></i></a><div>'
							+ '<a href="#" data-toggle="collapse" onclick=deleteLeadInfo("' + id
							+ '")><i class="fa fa-trash" aria-hidden="true" '
							+ '></i></a></div>'
							+ img
							+ '<div>'
							+ '<div class="u_name">'
							+ '<a href="#" onclick=viewLeadDetails("' + id + '")>'
							+ response.body[i].firstName
							+ '</a>'
							+ '</div>'
							+ '<div class="d-flex1">'
							+ '<span>Phone : '
							+ '<u>'
							+ response.body[i].phone
							+ '</u></span><span>|</span><span>Email : '
							+ '<u>'
							+ response.body[i].email
							+ '</u></span> <span>|</span><span>Mobile : <u>'
							+ response.body[i].mobile
							+ '</u> </span> </div> <div class="d-flex1"> <span>Company : '
							+ '<u>'
							+ response.body[i].company
							+ '</u></span> <span>|</span><span>Title : '
							+ '<u>'
							+ response.body[i].title
							+ '</u></span><span>|</span><span>Lead Source : '
							+ '<u>'
							+ response.body[i].leadSource
							+ '</u></span> </div> </div> </div></div>'
							+ '<div class="col-md-4"><div class="media forum-item d-flex">'
							+ '<a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="40" alt="User" class="mr-3 img-thumbnail" />'
							+ '</a> <div> <div>Biraja P Nath</div><div>Feb 3,2022 02:56PM</div></div></div></div>'
							+ '</div></div>';

						div = xyx;
						$("#allDetails").append(div);

					}


					$("#runMacro").attr("disabled", true);
					$("#sentMail").attr("disabled", true);
					$("#createTask").attr("disabled", true);
					$("#tags").attr("disabled", true);


				}
			},
			error: function(data) {
			}
		});

}



function deleteLeadInfo(id) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-deleteDetails?id=" + id,
		success: function(response) {

			if (response.message == "Success") {
				location.reload();

			}
		}
	});
}

function checkCallDetails() {
	var id = $("#callToWhom").val();
	if (id == "Lead") {
		$(".leadNameCls").show();
		$(".contactNameCls").hide();
		$("#contactName").val("");
		$("#relatedType").prop('disabled', true);
		$("#relatedName").prop('disabled', true);
	}
	else {
		$(".contactNameCls").show();
		$(".leadNameCls").hide();
		$("#lName").val("");
		$("#relatedType").prop('disabled', false);
		$("#relatedName").prop('disabled', false);
	}
}


function editLeadInfo(id) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-editDetails?id=" + id,
		success: function(response) {

			if (response.message == "Success") {
				newBtn();
				$('#leadId').val(response.body[0].leadId);
				$('#leadOwner').val(response.body[0].leadOwner);
				$('#company').val(response.body[0].company);
				$('#firstName').val(response.body[0].firstName);
				$('#lastName').val(response.body[0].lastName);
				$('#title').val(response.body[0].title);
				$('#email').val(response.body[0].email);
				$('#phone').val(response.body[0].phone);
				$('#project').val(response.body[0].projectName);
				$('#projectId').val(response.body[0].fax);
				$('#mobile').val(response.body[0].mobile);
				$('#website').val(response.body[0].website);
				$('#leadSource').val(response.body[0].leadSource);
				$('#leadStatus').val(response.body[0].leadStatus);
				$('#industry').val(response.body[0].industry);
				$('#noOfEmp').val(response.body[0].noOfEmp);
				$('#annualRevenue').val(response.body[0].annualRevenue);
				$('#ratings').val(response.body[0].ratings);
				$('#tags').val(response.body[0].tags);
				$('#skypeId').val(response.body[0].skypeId);
				$('#secondaryEmail').val(response.body[0].secondaryEmail);

				var emailValue = response.body[0].emailOpt;
				if (emailValue == 'on') {
					$('#emailOpt').prop('checked', true);
				} else {
					$('#emailOpt').prop('checked', false);
				}


				$('#twitter').val(response.body[0].twitter);
				$('#countryView').val(response.body[0].country);
				getStateDataOnEdit(response.body[0].states);
				//$('#states').val(response.body[0].states);
				$('#cityView').val(response.body[0].city);
				$('#addressStreet').val(response.body[0].addressStreet);
				$('#zip').val(response.body[0].zip);
				$('#description').val(response.body[0].description);

				$('#imgLoc').attr('src', '');
				if (response.body[0].imageName != null && response.body[0].imageName != "") {
					$('#imgLoc').attr('src', response.body[0].imageName);
				} else {
					$('#imgLoc').attr('src', '../assets/images/noimage.jpg');
				}
			}
		}
	});
}

function stageProbabitity() {
	var dealStageVal = $('#dealStageCheck').find(":selected").text();
	if (dealStageVal == "Qualification") {
		$("#probability").val(10);
	}
	if (dealStageVal == "Needs Analysis") {
		$("#probability").val(20);
	}
	if (dealStageVal == "Value Proposition") {
		$("#probability").val(40);
	}
	if (dealStageVal == "Identify Makers") {
		$("#probability").val(60);
	}
	if (dealStageVal == "Price Quote") {
		$("#probability").val(75);
	}
	if (dealStageVal == "Negotiation") {
		$("#probability").val(90);
	}

	if (dealStageVal == "Closed Won") {
		$("#probability").val(100);
	}

	if (dealStageVal == "Closed Lost") {
		$("#probability").val(0);
	}

	if (dealStageVal == "Closed-Lost to Competition") {
		$("#probability").val(0);
	}
}



//Profile Image Upload & Delete Strats

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-crm-leads-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});
}

function deleteFile() {

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-crm-leads-delete-file",
		enctype: "multipart/form-data",
		contentType: false,
		/* data        : fileData, */
		processData: false,
		cache: false,
		success: function(response) {
		},
		error: function(e) {

		}
	});
}

//Profile Image Upload & Delete Ends



function getStateDetails() {

	var cname = $('#country').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "customer-modal-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDataOnEdit(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-crm-leads-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
					$("#states").val(stateId);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
	}
}

function addTaskInfo() {


	$("body").removeClass("overlay");

	var obj = {};
	obj.leadId = $('#leadId').text();
	obj.ownerName = $('#leadOwner1').text();
	obj.pageType = "Lead";
	obj.taskId = $('#taskId').text();
	obj.taskOwner = $('#taskOwner').val();

	obj.taskLead = $('#taskLead').val();
	obj.taskSubject = $('#taskSubject').val();
	obj.dueDate = $('#dueDate').val();
	obj.taskContactName = $('#taskContactName').val();
	obj.contactId = $('#contactId').val();
	obj.taskAccountName = $('#taskAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.taskStatus = $('#taskStatus').val();
	obj.taskPriority = $('#taskPriority').val();

	var ReminderYesOrNo = $("input[name='ReminderYesOrNo']:checked").val();
	obj.reminderYesOrNo = ReminderYesOrNo;

	var RepeateYesOrNo = $("input[name='RepeateYesOrNo']:checked").val();
	obj.repeateYesOrNo = RepeateYesOrNo;

	if (ReminderYesOrNo == 'Yes') {
		obj.reminderDateid = $('#reminderDateid').val();
		obj.reminderTime = $('#reminderTime').val();
		obj.taskAlertBy = $('#taskAlertBy').val();
	} else {
		obj.reminderDateid = "";
		obj.reminderTime = "";
		obj.taskAlertBy = "";
	}

	obj.description = $('#descriptionTask').val();

	var validation = true;

	if (obj.taskSubject == null || obj.taskSubject == "") {
		validation = validationUpdated("Task Subject Required",
			"taskSubject");
	}
	if (obj.dueDate == null || obj.dueDate == "") {
		validation = validationUpdated("Due Date Required",
			"dueDate");
	}
	if (obj.taskStatus == null || obj.taskStatus == "") {
		validation = validationUpdated("Task status Required",
			"taskStatus");
	}
	if (obj.taskPriority == null || obj.taskPriority == "") {
		validation = validationUpdated("Task priority Required",
			"taskPriority");
	}




	/* FORM VALIDATION ENDS*/
	var leadId = $("#leadId").html();
	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-task-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					// Show loader before making changes
					var leadId = $("#leadId").html();
					getTask(leadId);
					getActivity(leadId, "allTimeline");
					getTimeline(leadId, "allTimeline");
					getAction(leadId);
					closeModelTask();

					$('.loader').hide();
					$("#messageParagraph").text("Task Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');


				}


				$("body").removeClass("overlay");

			},
			error: function(data) {
				$('.loader').hide();


				$("body").removeClass("overlay");
			}
		});
	}
}
function getAccountList() {
	var search = $("#taskAccountName").val();

	if (search) {

		$.ajax({
			type: "POST",
			url: "view-crm-tasks-get-account-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.code == "Success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].custId
								+ '\',\''
								+ response.body[i].custName
								+ '\',\''
								+ response.body[i].custGSTNo
								+ '\',\''
								+ response.body[i].taxType
								+ '\')">'
								+ response.body[i].custName
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li onClick="selectAutocompleteValue()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})
	}

}

function selectAutocompleteValue1(accountId, accountName, custGSTNo, taxType) {

	if (accountId) {


		$("#accountId").val(accountId);

		$("#taskAccountName").val(accountName);

		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box1_").hide();
		//	hideShowS();
		//checkForDuplicate(key,counter);

	} else {

		$("#accountId").val("");

		$("#taskAccountName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();

	}
}

function getProductSearch() {

	var searchVal = $("#searchProduct").val();

	var rowCount = $('#countAssignProduct').val();
	var leadId = $("#leadId").text();
	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}
	var pageType = "Lead";

	if (searchVal == "") {
		$("#suggesstion-boxproduct_").hide();
	}
	if (searchVal) {

		$.ajax({
			type: "GET",
			url: "view-crm-leads-autosearchProduct?searchVal=" + searchVal + "&id=" + leadId + "&assigRow=" + assigRow + "&pageType=" + pageType,

			success: function(response) {
				if (response.message == "Success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueProduct(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxproduct_").show();
						$("#suggesstion-boxproduct_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteValueProduct()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxproduct_").show();
						$("#suggesstion-boxproduct_").html(content);
					}


					///////////////////////////////////

				}


			},
			error: function(data) {
			}
		});

	}
}


function selectAutocompleteValueProduct(productName, productCode) {
	$("#suggesstion-boxproduct_").hide();
	var rowCount = $('#countAssignProduct').val();
	var leadId = $("#leadId").text();
	var leadName = $("#leadName").text();
	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}
	var pageType = "Lead";
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-product-view?id=" + leadId + "&id2=" + assigRow + "&pageType=" + pageType + "&productCode=" + productCode,
		success: function(response) {
			if (response.message == "Success") {

				$('#leadNameProduct').html(leadName);

				var countItm = response.body.length;
				$("#productTblBody").empty();
				for (var i = 0; i < countItm; i++) {

					//trCount = '';
					var taxable = response.body[i].taxable;
					if (taxable == 'on') {
						taxable = 'True';
					} else {
						taxable = 'False';
					}
					var mailRow2 = '<tr><td>'
						+ '<input type="checkbox" id="productCheckbox" name="productCheckbox" class="checkbox_check mr-5" onclick=clickProductCheck("' + response.body[i].productId
						+ '")>'

						+ '</td><td><a href="#" class="trlink">' + response.body[i].productName + '</a></td>'
						+ '<td>' + response.body[i].productCode + '</td>'
						+ '<td><a href="#" class="trlink">' + response.body[i].productVendor + '</a></td>'
						+ '<td>$' + response.body[i].unitPrice + '</td>'
						+ '<td>' + response.body[i].tax + '</td>'
						+ '<td>' + taxable + '</td>'
						+ '</tr>';
					trCount = mailRow2;
					$("#productTblBody").append(mailRow2);

				}

				if (countItm < 1) {
					var mailRow = '<tr>'
						+ '<td colspan="6" align="center">No Record Found.</td></tr>';
					trCount = mailRow2;
					$("#productTblBody").append(mailRow2);
				}

			}


		},
		error: function(data) {
		}
	});
}

function getNameListContact() {
	var searchVal = $("#taskContactName").val();
	if (searchVal == "") {
		$("#suggesstion-boxcontact_").hide();
	}
	if (searchVal) {
		$.ajax({
			type: "POST",
			url: "view-crm-tasks-autosearchDetailsContact",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {

				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueContact(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxcontact_").show();
						$("#suggesstion-boxcontact_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteContactValue()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxcontact_").show();
						$("#suggesstion-boxcontact_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})
	}
}

function selectAutocompleteValueContact(name, ContactId) {
	if (name) {
		//$("#personName").val("");
		$("#contactId").val(ContactId);

		$("#taskContactName").val(name);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxcontact_").hide();

	} else {
		$("#contactId").val("");

		$("#taskContactName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxcontact_").hide();

	}
}
function selectAutocompleteContactValue() {

	$("#contactId").val("");

	$("#taskContactName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxcontact_").hide();

}


function mailSucessAlert() {

	$('.email-alert').removeClass('collapse');
	$('.email-alert').addClass('fade-in');
	const alert = document.getElementById('alert');
	setTimeout(() => {
		$('.email-alert').addClass('collapse');
		$('.email-alert').removeClass('fade-in');
		$('.email-alert').removeClass('fade-out');

	}, 5000);

	setTimeout(() => {
		$('.email-alert').addClass('fade-out');
	}, 3000);
}



function addMeetingInfo() {
	var executiveSelect = $("#meetingHost");
	
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';

	if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	}

	$("body").removeClass("overlay");

	var type = $('#relatedMeetingTo').val();
	var obj = {
		ownerName: $('#leadOwner1').text(),
		leadId: $('#leadId').text(),
		meetingType: "Lead",
		meetingId: $('#meetingId').text(),
		meetingTitle: $('#meetingTitle').val(),
		meetingLocation: $('#meetingLocation').val(),
		isThisOnlineMeeting: $('#isThisOnlineMeeting').val(),
		isAllDay: $('#isAllDay').val(),
		meetingFromDate: $('#meetingFromDate').val(),
		meetingFromTime: $('#meetingFromTime').val(),
		meetingToDate: $('#meetingToDate').val(),
		meetingToTime: $('#meetingToTime').val(),
		meetingHost: $('#meetingHost').val(),
		leadName: $('#meetingLead').val(),
		meetingLeadId: $('#leadId').val(),
		contactName: $('#meetingcontactName').val(),
		contactId: $('#contactId').val(),
		relatedMeetingTo: $('#relatedMeetingTo').val(),
		meetingStatus: $('#meetingStatus').val(),
		isRepeat: $('#isRepeat').val(),
		isAllDayRepeat: $('#isAllDayRepeat').val(),
		meetingRepeatFromDate: $('#meetingRepeatFromDate').val(),
		meetingRepeatFromTime: $('#meetingRepeatFromTime').val(),
		meetingRepeatToDate: $('#meetingRepeatToDate').val(),
		meetingCalendarRepeatToTime: $('#meetingCalendarRepeatToTime').val(),
		repeatType: $('#repeatType').val(),
		description: $('#descriptionMeeting').val(),
		participantId: JSON.stringify(participantData),
		toMail: toMail,
		ccMail: ccMeetingMail,
		excutiveMail: executiveMail
	};
	console.log("Value is coming for lead meeting"+obj)
	

	var validation = true;

	if (obj.meetingTitle == null || obj.meetingTitle == "") {
		validation = validationUpdated("Meeting title Required", "meetingTitle");
	}
	if (obj.meetingLocation == null || obj.meetingLocation == "") {
		validation = validationUpdated("Meeting Location Required", "meetingLocation");
	}
	if (obj.meetingFromDate == null || obj.meetingFromDate == "") {
		validation = validationUpdated("Meeting From Date Required", "meetingFromDate");
	}
	if (obj.meetingFromTime == null || obj.meetingFromTime == "") {
		validation = validationUpdated("Meeting From Time Required", "meetingFromTime");
	}
	if (obj.meetingToDate == null || obj.meetingToDate == "") {
		validation = validationUpdated("Meeting To Date Required", "meetingToDate");
	}
	if (obj.meetingToTime == null || obj.meetingToTime == "") {
		validation = validationUpdated("Meeting To Time Required", "meetingToTime");
	}
	if (obj.meetingStatus == null || obj.meetingStatus == "") {
		validation = validationUpdated("Meeting Status Required", "meetingStatus");
	}

	if (validation) {

		closeModelMeeting();
		$('.loader').show();

		var leadId = $("#leadId").html();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-meeting-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					getActivity(leadId, "allTimeline");
					getTimeline(leadId, "allTimeline");
					getMeeting(leadId);
					getMail(leadId);
					$("#messageParagraph").text("Meeting Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					mailSucessAlert();

					/*$("#msgOkModal").on("click", function() {
						mailSucessAlert();
					});*/
				}

				$("body").removeClass("overlay");
			},
			error: function(data) {
			}
		});
	}
}



function checkMeetingDetails() {
	var id = $("#relatedMeetingTo").val();
	if (id == "Lead") {
		$(".leadNameCls").show();
		$(".contactNameCls").hide();
		$("#contactName").val("");
		$("#relatedType").prop('disabled', true);
		$("#relatedName").prop('disabled', true);
	}
	else {
		$(".contactNameCls").show();
		$(".leadNameCls").hide();
		$("#leadName").val("");
		$("#relatedType").prop('disabled', false);
		$("#relatedName").prop('disabled', false);
	}
}



function getNameListParticipants() {
	$("#participantId").val("");
	var search = $("#meetingParticipants").val();
	if (search == "") {
		$("#suggesstion-boxpart_").hide();
	}
	if (search) {
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-autosearchDetailsContact",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {

				if (response.code == "Success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {

							content += '<li style="margin-left:0px; font-weight:400; font-size:14px; color:#343a40; background-color: #dbdbdb;" class="autocompletedata cp" onClick="selectAutocompleteValueParticipants(\''
								+ response.body[i].key + '\',\''
								+ response.body[i].name + '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '<li  >'
							+ '</li>';
						content += '</ul>';
						$("#suggesstion-boxpart_").show();
						$("#suggesstion-boxpart_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
							+ "No Data Found" + '</li>';
						content += '<li style="margin-left:-30px;" '
							+ '</li>';
						content += '</ul>';
						$("#suggesstion-boxpart_").show();
						$("#suggesstion-boxpart_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})

	}



}

var accumulatedComboNames = [];

function selectAutocompleteValueParticipants(participantId, meetingParticipants) {

	var selectedItems = document.getElementById('selected-items');
	$("#participantId").val(participantId);

	var currentComboName = $("#meetingParticipants").val();

	if (accumulatedComboNames.some(item => item.participantId === participantId)) {
		swal("This Participant already selected.");
		$("#suggesstion-boxpart_").hide();
		return;
	}

	if (currentComboName) {
		accumulatedComboNames.push({ meetingParticipants, participantId });

		const selectedItem = document.createElement('div');
		selectedItem.className = 'selected-item';
		selectedItem.innerHTML = `
						<span hidden>${participantId}</span>
						<span>${meetingParticipants}</span>
						<span class="remove-button" onclick="removeSelectedItem(this, '${meetingParticipants}', '${participantId}')">X</span>
					`;
		selectedItems.appendChild(selectedItem);

		$("#meetingParticipants").val("");
	} else {
		accumulatedComboNames = [{ meetingParticipants, participantId }];
		$("#meetingParticipants").val("");
	}
	var row = "<tr><td>" + participantId + "</td>" +
		"<td>" + meetingParticipants + "</td>" +
		//"<td>" + allPrice + "</td>" +
		"<td><span class='remove-button' onclick='removeSelectedItem(this, " + participantId + ")'>50</span></td>";

	$("#t_draggable2 tbody").append(row);
	$("#suggesstion-boxpart_").hide();
}


function removeSelectedItem(element, meetingParticipants, participantId) {
	const selectedItems = document.getElementById('selected-items');
	selectedItems.removeChild(element.parentNode);

	const index = accumulatedComboNames.findIndex(item => item.meetingParticipants === meetingParticipants && item.participantId === participantId);
	if (index !== -1) {
		accumulatedComboNames.splice(index, 1);
	}

	// Remove the corresponding row from the table
	$("#t_draggable2 tbody tr td:first-child:contains('" + participantId + "')").parent().remove();
}


function getNameLeadContactList() {
	var relatedMeetingTo = $("#relatedMeetingTo").val();
	var leadName = $("#meetingleadName").val();
	var contactName = $("#meetingcontactName").val();
	if (leadName == "") {
		$("#suggesstion-boxmeetingLead_").hide();
	}
	if (contactName == "") {
		$("#suggesstion-boxmeetingContact_").hide();
	}
	var leadId = $("#leadId").text();
	if (relatedMeetingTo == 'Lead') {
		var searchVal = leadName;
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-autosearchLead?leadId=" + leadId,
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteMeetingLeadValue2(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxmeetingLead_").show();
						$("#suggesstion-boxmeetingLead_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteMeetingLead2()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxmeetingLead_").show();
						$("#suggesstion-boxmeetingLead_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})
	}
	else {
		var searchVal = contactName;
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-autosearchContact",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueMeetingContact2(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxmeetingContact_").show();
						$("#suggesstion-boxmeetingContact_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteContactMeetingValue2()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxmeetingContact_").show();
						$("#suggesstion-boxmeetingContact_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})

	}


}

function selectAutocompleteMeetingLeadValue2(name, LeadId) {

	if (name) {

		//$("#personName").val("");
		$("#leadId").val(LeadId);

		$("#meetingleadName").val(name);
		$("#search").val(LeadId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxmeetingLead_").hide();

	} else {
		$("#leadId").val("");

		$("#meetingleadName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxmeetingLead_").hide();

	}
}
function selectAutocompleteMeetingLead2() {

	$("#leadId").val("");

	$("#meetingleadName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxmeetingLead_").hide();

}


function selectAutocompleteValueMeetingContact2(name, ContactId) {
	if (name) {
		$("#contactId").val(ContactId);

		$("#meetingcontactName").val(name);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxmeetingContact_").hide();

	} else {
		$("#contactId").val("");

		$("#meetingcontactName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxmeetingContact_").hide();

	}
}
function selectAutocompleteContactMeetingValue2() {

	$("#contactId").val("");

	$("#meetingcontactName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxmeetingContact_").hide();

}




function addCampaignInfo() {

	$("body").removeClass("overlay");

	var obj = {};
	obj.campaignId = $('#campaignId').text();
	obj.leadId = $('#leadId').text();
	obj.pageType = "Lead";
	obj.ownerName = $('#leadOwner1').text();
	obj.campaignOwner = $('#campaignOwner').val();
	obj.campaignType = $('#campaignType').val();
	obj.campaignName = $('#campaignName').val();
	obj.campaignStatus = $('#campaignStatus').val();
	obj.startDate = $('#startDate').val();
	obj.endDate = $('#endDate').val();
	obj.expectedRevenue = $('#expectedRevenue').val();
	obj.budgetedCost = $('#budgetedCost').val();
	obj.actualCost = $('#actualCost').val();
	obj.expectedResponse = $('#expectedResponse').val();
	obj.numberSent = $('#numberSent').val();
	obj.description = $('#campaignDescription').val();

	/* FORM VALIDATION STARTS*/

	var validation = true;
	//return false;

	if (obj.campaignType == null || obj.campaignType == "") {
		validation = validationUpdated("Campaign Type Required",
			"campaignType");
	}

	if (obj.campaignName == null || obj.campaignName == "") {
		validation = validationUpdated("Campaign Name Required",
			"campaignName");
	}

	if (obj.startDate == null || obj.startDate == "") {
		validation = validationUpdated("Start Date Required",
			"startDate");
	}

	if (obj.endDate == null || obj.endDate == "") {
		validation = validationUpdated("End Date Required",
			"endDate");
	}

	if (obj.campaignStatus == null || obj.campaignStatus == "") {
		validation = validationUpdated("Campaign Status Required",
			"campaignStatus");
	}


	var leadId = $("#leadId").html()

	//return false;
	/* FORM VALIDATION ENDS*/

	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-campaign-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					closeModelCampaign();
					getCampaign(leadId);
					getActivity(leadId, "allTimeline");
					getTimeline(leadId, "allTimeline");

					$('.loader').hide();
					$("#messageParagraph").text("Campaign Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');

				}

				$('.loader').hide();
				$("body").removeClass("overlay");


			},
			error: function(data) {

			}
		})
	}

}



function saveLeadInfo() {
	//var ratingId = $("input:radio[name=rating]:checked").val();
	var obj = {};

	obj.leadId = $('#leadId').val();
	obj.leadOwner = $('#leadOwner').val();
	obj.company = $('#company').val();
	obj.firstName = $('#firstName').val();
	obj.lastName = $('#lastName').val();
	obj.title = $('#title').val();
	obj.email = $('#email').val();
	obj.phone = $('#phone').val();
	obj.fax = $('#fax').val();
	obj.mobile = $('#mobile').val();
	obj.website = $('#website').val();
	obj.leadSource = $('#leadSource').val();
	obj.leadStatus = $('#leadStatus').val();
	obj.industry = $('#industry').val();
	obj.noOfEmp = $('#noOfEmp').val();
	obj.annualRevenue = $('#annualRevenue').val();
	obj.ratings = $('#ratings').val();
	obj.emailOpt = $('#emailOpt').val();
	obj.skypeId = $('#skypeId').val();
	obj.secondaryEmail = $('#secondaryEmail').val();
	obj.twitter = $('#twitter').val();
	obj.country = $('#countryView').val();
	obj.states = $('#statesView').val();
	obj.city = $('#cityView').val();
	obj.addressStreet = $('#addressStreet').val();
	obj.zip = $('#zip').val();
	obj.description = $('#description').val();
	obj.createdBy = $('#createdBy').val();

	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.leadOwner == null || obj.leadOwner == "") {
		validation = validationUpdated("Executive Required",
			"leadOwner");
	}

	if (obj.company == null || obj.company == "") {
		validation = validationUpdated("Lead company Required",
			"company");
	}


	if (obj.firstName == null || obj.firstName == "") {
		validation = validationUpdated("First Name Required",
			"firstName");
	}


	if (obj.lastName == null || obj.lastName == "") {
		validation = validationUpdated("Last Name Required",
			"lastName");
	}

	if (obj.title == null || obj.title == "") {
		validation = validationUpdated("Title Required",
			"title");
	}

	if (obj.phone == null || obj.phone == "") {
		validation = validationUpdated("Phone Required",
			"phone");
	}

	if (obj.email == null || obj.email == "") {
		validation = validationUpdated("Email Required",
			"email");
	}

	if (obj.mobile == null || obj.mobile == "") {
		validation = validationUpdated("Mobile Required",
			"mobile");
	}

	if (obj.leadSource == null || obj.leadSource == "") {
		validation = validationUpdated("Lead Source Required",
			"leadSource");
	}


	if (obj.leadStatus == null || obj.leadStatus == "") {
		validation = validationUpdated("Lead Status Required",
			"leadStatus");
	}

	if (obj.industry == null || obj.industry == "") {
		validation = validationUpdated("Lead Industry Required",
			"industry");
	}


	/*if (obj.noOfEmp == null || obj.noOfEmp == "") {
		validation = validationUpdated("Number Of Emp Required",
				"noOfEmp");
	}*/

	if (obj.country == null || obj.country == "") {
		validation = validationUpdated("Country Required",
			"country");
	}

	if (obj.states == null || obj.states == "") {
		validation = validationUpdated("State Required",
			"statesView");
	}


	if (obj.city == null || obj.city == "") {
		validation = validationUpdated("City Required",
			"cityView");
	}

	if (obj.addressStreet == null || obj.addressStreet == "") {
		validation = validationUpdated("Address Street Required",
			"addressStreet");
	}

	if (obj.zip == null || obj.zip == "") {
		validation = validationUpdated("Zip Street Required",
			"zip");
	}



	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-leads-add-lead-details",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					location.reload();

				}
			},
			error: function(data) {

			}
		})
	}

}






var activityDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	sortable: false,
	filter: false,
	resizable: true,
	width: 20
}, {
	headerName: "Activity Name",
	field: "operationName"
}, {
	headerName: "User Name",
	field: "operationBy"
}, {
	headerName: "Created Date",
	field: "operationOn",
	cellStyle: {
		textAlign: 'center'
	}

}];

var activityOptions = {
	columnDefs: activityDefs,

	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 420
	}

};

document.addEventListener('DOMContentLoaded', function() {

	var gridDiv = document.querySelector('#myGridActivity');
	new agGrid.Grid(gridDiv, activityOptions);

	var gridDiv1 = document.querySelector('#activity');
	new agGrid.Grid(gridDiv1, activityOptions);

});
function agGridActivity(pipelineId) {
	agGrid.simpleHttpRequest({
		url: 'view-crm-pipeline-activity-through-ajax?id=' + pipelineId
	}).then(function(data) {
		activityOptions.api.setRowData(data);
	});
}

//ag grid for activity log end

function viewLeadDetails(id) {
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-leads-detail?id=" + id;
	//window.location.href = rest+"view-crm-leads-detail/"+id;
}
//=-------------------------------------------Add Note-----------------------------------------

function saveNoteWithDoc() {
	watchLocationPermission();

	if (locationPermission) {

		$("body").removeClass("overlay");
		var item = {};
		var empId = $("#leadId").text();
		var titleId = $("#titleId").val();
		var noteId = $("#noteId").val();
		let folder = [];

		if (titleId != "" || titleId != null) {

			var imageValid = true;
			var uploadList = [];
			let x = [];
			let fileName = '';
			if (document.getElementById("fileUploader").files.length > 0) {
				for (let i = 0; i < document.getElementById("fileUploader").files.length; i++) {
					let uFile = document.getElementById("fileUploader").files[i];
					fileName = uFile.name;
					let data = [];
					let x = [];
					if (fileName != '' && fileName != 'undefined' && fileName != null) {
						let reader = new FileReader();
						reader.readAsDataURL(uFile);
						let obj1 = {};
						reader.onload = function() {
							data = reader.result.split(",");
							x.push(data[1]);

							obj1.fileName = uFile.name;
							obj1.documentFile = x;
							folder.push(obj1);
						};
					}

				}
			}

			if ($("#docListlen").val() > 0) {
				var div = document.getElementById('divFiles');
				let maxLen = div.querySelectorAll('input[type="hidden"]').length;
				if (maxLen > 0) {
					for (let i = 0; i < maxLen; i++) {
						fileName = document.getElementById('uploadHidden_' + i).value;
						x = [];
						let obj = {};
						obj.fileName = fileName;
						obj.imageNameEdit = fileName;
						folder.push(obj);
					}
				}
			}
			setTimeout(function() {

				item.leadId = empId;
				item.employeeId = $('#leadOwner1').text();
				item.leadNoteId = $('#leadNoteId').val();
				item.titleId = titleId;
				item.noteId = noteId;
				item.documentList = folder;
				item.latitude = latitude;
				item.longitude = longitude;

				saveLeadNoteDoc(JSON.stringify(item));
				console.log("Json Document======>>>>>>>>>>>>>>>>"+JSON.stringify(item));
				return false;

			}, 2000)

		} else {
		}
	} else {
		$("#messageParagraph").text(LocationMessage);
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}

}

function saveLeadNoteDoc(item) {
	$('.loader').show();
	var validation = true;

	if (item.titleId == null || item.titleId == "") {
		validation = validationUpdated("Title Required", "titleId");
		$('.loader').hide();
	}

	if (validation) {
		$('.loader').show();
		var leadId = $("#leadId").text();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-add-notes-ajax",
			dataType: "json",
			contentType: "application/json",
			data: item,
			success: function(response) {
				statuss = true;
				var leadId = $("#leadId").text();
				getNoteDet(leadId, "1", "", "");
				getCount(leadId);
				getTimeline(leadId, "allTimeline");
				getActivity(leadId, "allTimeline");
				$('.loader').hide();
				$("#messageParagraph").text("Note Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#titleId").val("");
				$("#noteId").val("");
				$("#divFiles").html("");

			},
			error: function(data) {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})  
	}
}

function AddCampaignModal(index) {
	$('#myModalAddCampaign').modal('show');

	$('#campaignType').val('');
	$('#campaignName').val('');
	$('#campaignStatus').val('');
	$('#startDate').val('');
	$('#endDate').val('');
	$('#expectedRevenue').val('');
	$('#budgetedCost').val('');
	$('#actualCost').val('');
	$('#expectedResponse').val('');
	$('#numberSent').val('');
	$('#campaignDescription').val('');

}


function addEmailModal(index) {

	$('#myModalAddEmail').modal('show');
	$('#toMail').val(toMail);

	$("#mailSubject").val("");
	$("#comment").val("");
	$("#docName").val("");
	$("#uploadList").val("");
	$("#bccMail").val("");
	$("#selected-mail-container").html("");
	CKEDITOR.instances['commentck'].setData("");
	//$("#selected-mail-container").html("");
	$('#uploadDoc_1').val('');
	$('#imageName_1').text('');
	$('#imageName_1').val('');
	$('#uploadedBillDiv_1').html('');
}

function closeModelEmail(index) {
	selectedDraftId="";
	$('#myModalAddEmail').modal('hide');
	$("#mailSubject").val("");
	$("#comment").val("");
	$("#docName").val("");
	$("#uploadList").val("");
	$("#ccMail").val("");
	$("#bccMail").val("");
	$("#selected-mail-container").html("");
	$("#bcc-mail-container").html("");
	draftId = null;
	removeErrorMsg();
	ccMailListData.clear();
	bccMailListData.clear();

	CKEDITOR.instances['commentck'].setData("");


}

function closeViewModelEmail(index) {
	$('#myModalViewEmail').modal('hide');
}

function closeViewModelAddProduct(index) {
	$('#addProductModal').modal('hide');
}



function closeModelCampaign(index) {
	$('#myModalAddCampaign').modal('hide');
}

function closeModelTask(index) {
	$('#myModalAddTask').modal('hide');
	document.getElementById('activities').value = "select";
}

function closeModelMeeting(index) {
	$('#myModalAddMeeting').modal('hide');
	document.getElementById('activities').value = "select";
	$(".inviteMeeting").val("clearOptions");
	hideInviteMettingSearchField()
}

function closeModelCall(index) {
	$('#myModalAddCall').modal('hide');
	document.getElementById('activities').value = "select";
	$(".inviteMeeting").val("clearOptions");
	hideInviteMettingSearchField()
}


//////////////////////////////////////////save lead Email

let bccMailData = '';

function saveLeadEmails() {
	bccMailData = '';
	var ececutiveMail = $("#userMail").val().trim();
	if (ececutiveMail !== "") {
		bccMailData += ececutiveMail;
	}

	bccMailListData.add(bccMailData);
	var commaSeparatedEmails = Array.from(ccMailListData).join(',');
	var bccMailList = Array.from(bccMailListData).join(',');

	$("body").removeClass("overlay");

	var item = {};
	if (ccMailListData.length > 0 && ccMailListData.endsWith(',')) {
		ccMailListData = ccMailListData.slice(0, -1);
	}



	var leadId = $("#leadId").text();
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName = $("#docName").val();
	var ccMail = commaSeparatedEmails;
	var bccMail = bccMailList;




	if (leadId) {

		var imageValid = true;
		var uploadList = [];
		$("#doctbodyDataforcontact > tr").each(function() {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var data = [];
			var x = [];
			if (fileName != '' && fileName != 'undefined' && fileName != null) {
				var lastIndex = fileName.lastIndexOf("\\");
				if (lastIndex >= 0) {
					fileName = fileName.substring(lastIndex + 1);
				}
				var reader = new FileReader();
				reader.readAsDataURL(uFile);

				reader.onload = function() {
					data = reader.result.split(",");
					x.push(data[1]);
				};
			} else {
				if ($("#draftId").val()) {
					fileName = $(this).find(".uploadHidCls").val();
				} else {
					x = [];
				}

			}
			uploadData = {};
			uploadData['documnentName'] = $("#documentName").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
			uploadList.push(uploadData);


		});
		setTimeout(function() {

			item.leadId = leadId;
			item.employeeId = $('#userId').val();
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			item.draftId = $('#draftId').val();
			item.ccMail = ccMail;
			item.bccMail = bccMail;
			saveLeadEmailDtls(item);

		}, 100)

	} else {
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}

function saveLeadEmailDtls(item) {

	var validation = true;


	if (item.mailSubject == null || item.mailSubject == "") {
		validation = validationUpdated("Subject Required", "mailSubject");
	}
	if (validation) {
		$('.loader').show();
		closeModelEmail();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add-emails-ajax",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.message == "success") {
					var leadId = $("#leadId").html();
					getActivity(leadId, "allTimeline");

					$.ajax({
						type: "GET",
						url: "view-crm-leads-delete-draft?id=" + selectedDraftId,
						success: function(response) {

							if (response.message == "Success") {

							}
						}
					});


					getDraft(leadId);
					draftId = null;
					$('.loader').hide();
					mailSucessAlert();
					$("#toMail").val("");
					$("#mailSubject").val("");
					$("#comment").val("");
					$("#docName").val("");
					$("#uploadList").val("");
					var leadId = $("#leadId").html();
					getMail(leadId);
				} else {

				}
			},
			error: function(data) {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		}) //ajax ends 
	}
}
function saveLeadDrafts() {
	bccMailData = '';
	var ececutiveMail = $("#userMail").val().trim();
	if (ececutiveMail !== "") {
		bccMailData += ececutiveMail;
	}

	bccMailListData.add(bccMailData);
	var commaSeparatedEmails = Array.from(ccMailListData).join(',');
	var bccMailList = Array.from(bccMailListData).join(',');
	var item = {};

	var leadId = $("#leadId").text();
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName = $("#docName").val();
	var ccMail = commaSeparatedEmails
	var bccMail = bccMailList
	var draft = $("#draftId").val();
	let draftId = '';

	if (draft == "" || draft == "null") {
		draftId = null;
	} else {
		draftId = draft;
	}
	if (leadId) {

		var imageValid = true;
		var uploadList = [];
		$("#doctbodyDataforcontact > tr").each(function() {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var data = [];
			var x = [];
			if (fileName != '' && fileName != 'undefined' && fileName != null) {
				var lastIndex = fileName.lastIndexOf("\\");
				if (lastIndex >= 0) {
					fileName = fileName.substring(lastIndex + 1);
				}
				var reader = new FileReader();
				reader.readAsDataURL(uFile);

				reader.onload = function() {
					data = reader.result.split(",");
					x.push(data[1]);
				};
			} else {
				if ($("#vendorRfqId").val()) {
					fileName = $(this).find(".uploadHidCls").val();
				} else {
					x = [];
				}

			}
			uploadData = {};
			uploadData['documnentName'] = $("#documentName").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadList.push(uploadData);


		});
		setTimeout(function() {
			item.leadId = leadId;
			item.draftId = (selectedDraftId != null && selectedDraftId != "") ? selectedDraftId : null;
			item.employeeId = $('#leadOwner1').text();
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			item.ccMail = ccMail;
			item.bccMail = bccMail;

			saveLeadDraftDtls(item);

		}, 100)

	} else {
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}
function saveLeadDraftDtls(item) {
	var validation = true;


	if (item.mailSubject == null || item.mailSubject == "") {
		validation = validationUpdated("Subject Required", "mailSubject");
	}
	if (validation) {
		closeModelEmail();
		var leadId = $("#leadId").text();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add-drafts",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.message == "success") {
					$("#messageParagraph").text("Draft Saved Successfully");
					getDraft(leadId);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				} else {

				}
			},
			error: function(data) {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	}
}

/**********************************ADD Product Script Start************************************** */
function addProduct() {
	
	$('#addProductModal').modal('show');
	
	$("#productSave").hide();
	agGrid.simpleHttpRequest({
		url: 'view-product-get-sku-listing?type=' + "both"

	}).then(function(data) {
		const len = data.length;
		gridOptionsProduct.api.setRowData(data);

	});
}

function profileSelect() {
	$('#addItemModel').modal('show');
	$('#addProductModal').modal('hide');
	$(".formValidation").remove();
	$("#mainItem").hide();
	$("#profile").show();
	$("#prCategoryId").val("");
	$("#tempCategoryId").val("");
	$("#prLevelId").val("");
	$("#productId").val("");
	$('#fileUpload').val('');
	$("#catDesc").text("");
	$("#vertical").empty();
	var img = '<li data-thumb="../assets/css/extend/sld-noimg.jpg"><img src="../assets/css/extend/sld-noimg.jpg" id="imgLoc" alt="Mountains" class="hvrbox-layer_bottom img-fluid"></li>';
	$("#vertical").append(img);
	$("#productName").val("");
	$("#brand").val("");
	$("#mode").val("");
	$("#hsnCodeProduct").val("");
	$("#sicCode").val("");
	$("#fileUpload").val("");
	$("#productheadId").html("");

	//$("#imgemp").val("");
	$("#prodStatus").prop("checked", false);
	closeNavSku();

	skuOptions.api.setRowData();
	purchaseOptions.api.setRowData();
	$('#productSave').hide();
	getBrandList();

	$("#reqTable").hide();


}
function getBrandList() {
	var id = 0;
	var option = $("<option></option>");
	$(option).val(null);
	$(option).html("Select");
	$.ajax({
		type: "GET",
		url: "view-product-get-brandList?id=" + id,
		success: function(response) {
			if (response.message == "success") {
				$("#brand").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#brand").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#brand").append(option);
				}
				if (rid != "" || rid != null || rid != "null") {
					$("#brand").val(rid);
				}
			}
		},
		error: function(e) {
		}
	});
}

function profilecancelbtn() {
	$('#addProductModal').modal('show');
	$('#addItemModel').modal('hide');
}

function toDataUrl(url, callback) {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var reader = new FileReader();
		reader.onloadend = function() {
			callback(reader.result);
		}
		reader.readAsDataURL(xhr.response);
	};
	xhr.open('GET', url);
	xhr.responseType = 'blob';
	xhr.send();
}

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgemp').attr('src', '');
	$('#imgemp').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-product-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});

}


function next() {
	$("#next").show();
	$("#main").show();
	$("#profile").show();
	/* $("#productSave").hide();	 */
	$("#accordionExample").hide();
	var data = [];
	$(".hvrbox-layer_bottom").each(function(i) {
		var xyz = "";
		toDataUrl($(this).attr("src"), function(myBase64) {

			xyz = myBase64;
			data.push(myBase64);
		});
	})

	obj = {};

	obj['productId'] = $("#productId").val();
	obj['productName'] = $("#productName").val();
	obj['brand'] = $("#brand").val();
	obj['mode'] = $("#mode").val();
	obj['hsnCode'] = $("#hsnCodeProduct").val();
	obj['sicCode'] = $("#sicCode").val();
	obj['productStatus'] = $("input[name='isActive']:checked").val();
	obj['productCategory'] = $("#prCategoryId").val();
	obj['productCategoryText'] = $("#catDesc").text();
	obj['imgList'] = data;
	obj['addedFrom'] = "crm";
	obj['leadId'] = $("#leadId").text();
	$(".formValidation").remove();
	allPValid = true;
	if ($("#productName").val() == null || $("#productName").val() == "") {
		allPValid = false;
		validationModal("Product Name Required", "productName");
	}
	/* if( $("#brand").val() == null || $("#brand").val() == ""){
		allPValid = false;
		validationModal("Brand Required","brand");
	}  */
	/* if( $("#mode").val() == null || $("#mode").val() == ""){
		allPValid = false;
		validationModal("Mode Required","mode");
	}  */
	if ($("#hsnCodeProduct").val() == null || $("#hsnCodeProduct").val() == "") {
		allPValid = false;
		validationModal("HSN/SAC Code Required", "hsnCodeProduct");
	}
	var productId = $('#productheadId').html();
	if (allPValid) {
		if (productId != "") {
			var rowCount = skuOptions.api.getDisplayedRowCount();
			if (rowCount > 0) {
				submitProduct(obj);
			} else {
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Please Select Atleast one Item");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#mySKUGrid").show();
				$("#mainItem").show();
			}

		} else {
			submitProduct(obj);
		}


	}
}

function categoryBtn() {
	//closeModal();
	$("#myModalCat").modal('show');
	$('#addItemModel').modal('hide');
	getCategoryList();

}
function getCategoryList() {
	$('.loader').show();
	$("body").addClass("overlay");
	$("#productDiv").modal("show");
	$.ajax({
		type: "POST",
		url: "view-product-get-total-list",
		dataType: "json",
		contentType: "application/json",
		success: function(response) {
			if (response.message == "Success") {
				$("#costCeneterCBDiv").empty();
				for (var i = 0; i < response.body.length; i++) {
					var row = "";
					if (response.body[i].categoryId == response.body[i].parentId) {
						if (response.body[i].nodeCount > 0) {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '>' + response.body[i].categoryName + '</td></tr>';
						} else {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_' + response.body[i].categoryId + '" value="' + response.body[i].categoryId +
								'" name="' + response.body[i].categoryName + '" onchange=selectCheckBox("' + response.body[i].categoryId + '","' + response.body[i].catLevel + '")>' + response.body[i].categoryName + '</td></tr>';
						}

					} else {
						if (response.body[i].nodeCount > 0) {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" data-node-pid="' + response.body[i].parentId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '>' + response.body[i].categoryName + '</td></tr>';
						} else {

							row = '<tr data-node-id="' + response.body[i].categoryId + '" data-node-pid="' + response.body[i].parentId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_' + response.body[i].categoryId + '" value="' + response.body[i].categoryId + '" name="' +
								response.body[i].categoryName + '" onchange=selectCheckBox("' + response.body[i].categoryId + '","' + response.body[i].catLevel + '")>' + response.body[i].categoryName + '</td></tr>';
						}
					}
					$("#costCeneterCBDiv").append(row);

				}

				var pcat = $("#prCategoryId").val();
				$(".benefitChk").prop("checked", false);
				$("#ccCheck_" + pcat).prop("checked", true);

				$('.loader').hide();
				$("body").removeClass("overlay");

				$('#basic').simpleTreeTable({
					expander: $('#expander'),
					collapser: $('#collapser'),
					store: 'session',
					storeKey: 'simple-tree-table-basic'
				});
			}
		}, error: function(data) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#productDiv").modal("show");
		}
	});
}


function selectCategory() {

	var id = $("#tempCategoryId").val();
	var lvl = $("#prLevelId").val();

	if (id && lvl) {
		$("#prCategoryId").val(id);
		if (lvl == 'L1') {
			var textDesc = $("#lbl_" + id).text();
			$("#catDesc").text(textDesc);
		} else {
			var res = lvl.substring(1, 1000000);

			var lastText = $("#lbl_" + id).text();
			var textDesc = "";
			for (i = res; i > 1; i--) {
				var pUL = $("#" + id).attr("data-node-pid");
				//pUL = pUL.substring(3, 10000000000000);
				var newText = $("#lbl_" + pUL).text();
				textDesc = newText + " > " + textDesc;
				id = pUL;
			}
			textDesc = textDesc + lastText;
			$("#catDesc").html(textDesc);
		}
	}
	$("#myModalCat").modal("hide");
	$('#addItemModel').modal('show');
	closeModal();
}

function closeModal() {
	$("#myModalCat").modal("hide");
	//$("#myModal").modal("empty");
}

function selectCheckBox(id, lvl) {
	$(".benefitChk").prop("checked", false);
	$("#ccCheck_" + id).prop("checked", true);
	$("#tempCategoryId").val(id);
	$("#prLevelId").val(lvl);

}
function openNavItem() {
	$(".formValidation").remove();
	var productId = $("#productId").val();
	var productName = $("#productName").val();

	//$("#skudiv").hide();
	$("#skuPrdId").text(productId);
	$("#isEdit").val("");
	$("#skuId").val("");
	$("#model").val("");
	$("#manufacture").val(productName);
	$("#variationType").val("");
	$("#variationValue").val("");
	$("#itemUnit").val("");
	$("#salePrice").val("0");
	$("#saleTax").val("");
	$("#saleCess").val("");
	document.getElementById("mySidenavItem").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom:4px;";

	document.getElementById("mainItem").style.width = "70%";
}
function closeNavSku() {
	$("#isEdit").val("");
	document.getElementById('skuId').readOnly = false;
	$("#skuId").val("");
	$("#model").val("");
	$("#manufacture").val("");
	$("#variationType").val("");
	$("#variationValue").val("");
	$("#itemUnit").val("");
	$("#salePrice").val("");
	$("#saleTax").val("");
	$("#saleCess").val("");
	document.getElementById("mySidenavItem").style.width = "0";
	document.getElementById("mainItem").style.width = "100%";
}

function submitProduct(dataset) {
	$.ajax({
		type: "POST",
		url: "/pipeline/view-product-save",
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {
				var leadId = $("#leadId").text();
				getProductActivity(leadId, "productTimeline");
				//returnPage();
				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-listing?type=' + "both"
				}).then(function(data) {
					gridOptionsProduct.api.setRowData(data);
				});
				var productIdd = $('#productId').val();
				if (productIdd != "") {
					profilecancelbtn();
				} else {

					$('.loader').hide();
					$("body").removeClass("overlay");

					$("#productId").val(response.body.productId);
					$("#productheadId").html(response.body.productId);

					$("#mySKUGrid").show();
					$("#mainItem").show();
					$('#deletess').attr("disabled", true);
				}
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				swal({
					title: response.code,
					text: response.message,
					type: "warning"
				})
			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})
}
function check1(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	$("#" + fieldId).val(tempVal);
}


function saveSKUData() {
	obj = {};

	obj.isEdit = $("#isEdit").val();
	obj.productId = $("#productId").val();
	obj.sku = $("#skuId").val();
	obj.model = $("#model").val();
	obj.manufacture = $("#manufacture").val();
	obj.variationType = $("#variationType").val();
	obj.variationValue = $("#variationValue").val();
	obj.unit = $("#itemUnit").val();
	var price = $("#salePrice").val();
	price = price.replaceAll(",", "");
	obj.salePrice = price;
	obj.saleTax = $("#saleTax").val();
	obj.saleCess = $("#saleCess").val();

	$(".formValidation").remove();
	allValid = true;

	if (obj.manufacture == null || obj.manufacture == "") {
		allValid = false;
		validationModal("Item Name Required", "manufacture");
	}


	if (obj.unit == null || obj.unit == "") {
		var mode = $("#mode").val();
		if (mode == "PMODE00004") {
			allValid = true;
		} else {
			allValid = false;
			validationModal("UOM Required", "itemUnit");
		}

	}
	if (obj.salePrice == null || obj.salePrice == "") {
		allValid = false;
		validationModal("Sale Price Required", "salePrice");
	}


	if (allValid) {
		submitProductDetails(obj);

	}

}
function submitProductDetails(dataset) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "/purchase/view-product-save-sku-dtls",
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {
				$("#productSave").show();

				$('.loader').hide();
				closeNavSku();
				var productId = $("#productId").val();
				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-by-product?id=' + productId
				}).then(function(data) {
					skuOptions.api.setRowData(data);

					$("#skuPurchase").empty();
					$("#skuPurchase").append("<option value>Select</option>");
					for (var i = 0; i < data.length; i++) {
						$("#skuPurchase").append("<option value='" + data[i].sku + "'>" + data[i].sku + "</option>");
					}
				});

				agGrid.simpleHttpRequest({
					url: 'view-product-get-purchase-by-product?id=' + productId
				}).then(function(data) {
					purchaseOptions.api.setRowData(data);
				});
			} else {
				$('.loader').hide();
				$("#messageParagraph").text("This SKU value is already exists.");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#productSave").show();
				/* swal({
					title: response.code,
					text: response.message,
					type: "warning"
				}) */
			}
		},
		error: function(response) {
		}
	})
}
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#mySKUGrid');
	new agGrid.Grid(gridDiv, skuOptions);
});

var columnSKUDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true
},
{
	headerName: "SKU",
	field: "sku",
	cellRenderer: function(params) {
		return '<a onclick=openSKUDetails("' + params.data.sku + '","' + params.data.productId + '") href="javascript:void(0)">' + params.data.sku + '</a>';
	}
},
{
	headerName: "Manufacture Item",
	field: "manufacture",
	width: 200,
},
{
	headerName: "Model",
	field: "model"
},
{
	headerName: "Variation Type",
	field: "variationType"
},
{
	headerName: "Variation Value",
	field: "variationValue"
},
{
	headerName: "UOM",
	field: "unit"
},
{
	headerName: "Unit Sale Price",
	field: "sPrice",
	cellStyle: { textAlign: 'right' }
},
{
	headerName: "Create Date",
	field: "createdDate",
	cellStyle: { textAlign: 'center' }
}
];



var skuOptions = {
	columnDefs: columnSKUDefs,
	rowSelection: 'multiple',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChangedsku
};

function onSelectionChangedsku() {
	var selectedRows = skuOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#deletess').attr("disabled", false);
		$('#newBtn2').attr("disabled", true);

	} else {
		$('#deletess').attr("disabled", true);
		$('#newBtn2').attr("disabled", false);

	}
}

function deleteskuDtls() {
	$("#deleteSKU").modal('show');
	$("#addItemModel").modal('hide');
}

function cancelModalSKUDtls() {
	$("#deleteSKU").modal('hide');
	$("#addItemModel").modal('show');
}

function deleteskud() {

	var selectedNodes = skuOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	sku = selectedData.map(node => node.sku);
	var id = $('#productId').val();
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "view-product-deleteSku?id=" + sku,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				$("#addItemModel").modal('show');
				$('#newBtn2').attr("disabled", false);
				$('#deletess').attr("disabled", true);

				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-by-product?id=' + id
				}).then(function(data) {
					skuOptions.api.setRowData(data);

					$("#skuPurchase").empty();
					$("#skuPurchase").append("<option value>Select</option>");
					for (var i = 0; i < data.length; i++) {

						$("#skuPurchase").append("<option value='" + data[i].sku + "'>" + data[i].sku + "</option>");
					}
				});
				closeNav();
				cancelModalSKUDtls();
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	})

}



function productSave() {



	var data = [];
	$(".hvrbox-layer_bottom").each(function(i) {
		var xyz = "";
		toDataUrl($(this).attr("src"), function(myBase64) {

			xyz = myBase64;
			data.push(myBase64);
		});


	})

	obj = {};

	obj['productId'] = $("#productId").val();
	obj['productName'] = $("#productName").val();
	obj['brand'] = $("#brand").val();
	obj['mode'] = $("#mode").val();
	obj['hsnCode'] = $("#hsnCode").val();
	obj['sicCode'] = $("#sicCode").val();
	obj['productStatus'] = $("input[name='isActive']:checked").val();
	obj['productCategory'] = $("#prCategoryId").val();
	obj['productCategoryText'] = $("#catDesc").text();
	obj['imgList'] = data;
	$(".formValidation").remove();
	allPValid = true;
	if ($("#productName").val() == null || $("#productName").val() == "") {
		allPValid = false;
		validationModal("Product Name Required", "productName");
	}
	/* if( $("#brand").val() == null || $("#brand").val() == ""){
		allPValid = false;
		validationModal("Brand Required","brand");
	} 
	if( $("#mode").val() == null || $("#mode").val() == ""){
		allPValid = false;
		validationModal("Mode Required","mode");
	}  */
	/* if( $("#hsnCode").val() == null || $("#hsnCode").val() == ""){
		allPValid = false;
		validationModal("HSN Code Required","hsnCode");
	} 
	 */


	var productId = $('#productheadId').html();


	if (allPValid) {
		if (productId != "") {
			var rowCount = skuOptions.api.getDisplayedRowCount();
			if (rowCount > 0) {
				submitProduct(obj);
			} else {
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Please Select Atleast one Item");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}

		} else {
			submitProduct(obj);
		}


	}

}

function scrollProducts() {
	$('html, body').animate({
		scrollTop: $("#products").offset().top - 250
	}, 500);
}


function scrollNotes() {
	$('html, body').animate({
		scrollTop: $("#notes").offset().top - 250
	}, 500);
}




function scrollOpenActivities() {
	$('html, body').animate({
		scrollTop: $("#open").offset().top - 250
	}, 500);
}


function scrollClosedActivities() {
	$('html, body').animate({
		scrollTop: $("#closed").offset().top - 250
	}, 500);
}


function scrollInvitedMeetings() {
	$('html, body').animate({
		scrollTop: $("#meetings").offset().top - 250
	}, 500);
}



function scrollEmails() {
	$('html, body').animate({
		scrollTop: $("#emails").offset().top - 250
	}, 500);
}

function scrollCampaigns() {
	$('html, body').animate({
		scrollTop: $("#campaigns").offset().top - 250
	}, 500);
}


var checkedIDs = [];
var checkedVal = [];
function clickProductCheck(id) {
	checkedVal.push(id);
	checkedIDs = $("#productTblBody input:checkbox:checked").map(function() {
		return $(this).val();
	}).get();
	var checkedLength = checkedIDs.length;

}


function addProductToLead() {

	var selectedRows = gridOptionsProduct.api.getSelectedRows();

	if (selectedRows.length > 0) {

		selectedRows.forEach((itm) => {

			itm.leadId = $("#leadId").html();
			itm.contactId = $("#contactId").text();
		});
		var leadId = $("#leadId").html();

		// Fetch existing data from the other table
		agGrid.simpleHttpRequest({
			url: 'view-crm-leads-view-detail-product?id=' + leadId,
		}).then(function(data) {
			var existingProductIds = data.body.map(item => item.productId);
			var duplicatesExist = false;
			var duplicateProductIds = []; // Define the array here
			var newProductIds = selectedRows
				.filter(row => !existingProductIds.includes(row.productId)) // Filter out existing products
				.map(row => row.productId);

			for (var i = 0; i < selectedRows.length; i++) {
				if (existingProductIds.includes(selectedRows[i].productId)) {
					duplicatesExist = true;
					duplicateProductIds.push(selectedRows[i].productId);
				}
			}


			if (duplicatesExist) {
				swal({
				    title: "Already Added " + duplicateProductIds.join(", "),
				    html: '<div><strong>Please remove these products</strong></div>',
				    type: "warning"
				});

			} else {
				$.ajax({
					type: "POST",
					url: "view-crm-leads-add-product",
					contentType: "application/json",
					data: JSON.stringify(selectedRows),
					success: function(response) {
						if (response.message === "Success") {
							var leadId = $("#leadId").text();
							getTimeline(leadId, "allTimeline");
							getProduct(leadId);
							getActivity(leadId, "allTimeline");
							getProductActivity(leadId, "productTimeline");

							$("#addProductModal").modal('hide');

							$("#messageParagraph").text("Product Added To The Lead!");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');


						}
					},
					error: function(error) {
						// Handle errors here
					}
				});
			}
		});
	} else {
		swal({
			title: "Product Not Selected",
			text: "Please select Products to assign.",
			type: "warning"
		});
	}
}


function getProduct(leadId) {
	$("#countProduct").empty();
	agGrid.simpleHttpRequest({
		url: 'view-crm-leads-view-detail-product?id=' + leadId,
	}).then(function(data) {
		var prdLength = data.body.length;
		$("#countProduct").append(prdLength);

		gridOptionsLead.api.setRowData(data.body);
	});
}

function updateList() {
	var input = document.getElementById('fileUploader');

	var HTML = "";
	for (var i = 0; i < input.files.length; ++i) {
		var filename = input.files.item(i).name;
		HTML += '<div id="filename' + i + '">'
			+ filename
			+ '<i class="bx bx-trash" onclick="delecteDocfile(' + i + ')"></i></div>';
	}

	$("#divFiles").append(HTML);
	/* output.innerHTML = HTML; */
}

function delecteDocfile(id) {
	//alert(id)
	var divId = "filename" + id;
	var elementToRemove = document.getElementById(divId);

	if (elementToRemove) {
		// Remove the element
		elementToRemove.parentNode.removeChild(elementToRemove);
	} else {
		console.log("Element not found: " + divId);
	}
}



var columnMailDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

}, {
	headerName: "Subject",
	field: "mailSubject",
	width: 200,
	cellStyle: {
		textAlign: 'left'
	},


}, {
	headerName: "Sent To",
	field: "toMail",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},


}, {
	headerName: "Sent Date",
	field: "createdDate",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},
}, {
	headerName: "CC",
	field: "ccMail",
	width: 350,
	cellStyle: {
		textAlign: 'left'

	},
}, {
	headerName: "BCC",
	field: "bccMail",
	width: 350,
	cellStyle: {
		textAlign: 'left'
	}
}
];

var mailgridOptions = {
	columnDefs: columnMailDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
};

var columnDraftDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

}, {
	headerName: "Draft Id",
	field: "draftId",
	width: 130,
	pinned: 'left',
	hide: true,
	cellRenderer: function(params) {


		return '<a onclick=editDraft("'
			+ params.data.draftId
			+ '") href="javascript:void(0)">'
			+ params.data.draftId + '</a>';
	},

}, {
	headerName: "Subject",
	field: "mailSubject",
	//pinned : 'left',
	width: 250, cellRenderer: function(params) {

		if (ownerId != userId) {
			return '<span("'
				+ params.data.draftId
				+ '")">'
				+ params.data.mailSubject + '</span>';
		} else {
			return '<a onclick=editDraft("'
				+ params.data.draftId
				+ '") href="javascript:void(0)">'
				+ params.data.mailSubject + '</a>';
		}
	},

}, {
	headerName: "Sent To",
	field: "toMail",
	//pinned : 'left',
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},

}, {
	headerName: "Sent Date",
	field: "createdDate",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},
}, {
	headerName: "Created By",
	field: "createdBy",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},
},
{
	headerName: "Action",
	field: "draftId",
	width: 200,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (ownerId != userId) {

			return '<span>' + '<i class="fa fa-trash fa-icon img-hover" aria-hidden="true"></i> </span>';
		} else {
			return '<a onclick=deleteDraft("'
				+ params.data.draftId
				+ '") href="javascript:void(0)">'
				+ '<i class="fa fa-trash fa-icon img-hover" aria-hidden="true"></i> </a>';

		}

	},
}, {
	headerName: "CC",
	field: "ccMail",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},
}, {
	headerName: "BCC",
	field: "bccMail",
	width: 250,
	cellStyle: {
		textAlign: 'left'
	},
}];


var draftgridOptions = {
	columnDefs: columnDraftDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
};

var draftid = '';
function deleteDraft(id) {
	$("#deleteDraftModal").show();
	draftid = id;
}

function deleteDraftOnclick() {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-delete-draft?id=" + draftid,
		success: function(response) {
			if (response.message == "Success") {
				var leadId = $("#leadId").text();
				getDraft(leadId);
				cancelDeleteDraftModalBtn();
				$("#messageParagraph").text("Draft Deleted Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
		}
	});

}
function cancelDeleteDraftModalBtn() {
	$("#deleteDraftModal").hide();
}
let selectedDraftId = '';
function editDraft(id) {
	selectedDraftId = id;
	var leadId = $("#leadId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-edit-draft?id1=" + leadId + "&id2=" + id,
		async: false,
		success: function(response) {

			if (response.message == "success") {
				$('#myModalAddEmail').modal('show');
				$("#closeMail").hide();
				$("#draftId").val(response.body.id);
				$("#fromEmail").val(response.body.fromEmail);
				$("#toMail").val(response.body.toMail);
				$("#mailSubject").val(response.body.mailSubject);
				$("#docName").val(response.body.docnoid);

				var ccMail = response.body.ccMail;
				var bccMail = response.body.bccMail;

				toggleCC();
				var emailListCc = ccMail.split(',');
				var emailListBcc = bccMail.split(',');
				emailListCc.forEach((data) => {
					selectAutocompleteMailValueForDraft(data);
				});
				emailListBcc.forEach((data) => {
					addEmailToBccContainer(data);
				});

				CKEDITOR.instances['commentck'].setData(response.body.commentck);
				var fileName = response.body.attachment;
				var tbl = '';
				if (fileName != null) {
					tbl += '<tr><td>'
						+ '<div class="control-group position-r">'
						+ '<label class="custom-file-upload" for="uploadDoc_1" id="uploadFor_1"> <i class="ti-plus"></i>'
						+ '</label><div class="controls">'
						+ '<input type="file" class="document" id="uploadDoc_1" name="userImage" accept="image/*" onchange="saveMultiFileForLead(event)" />'
						+ '</div></div>'
						+ '<input type="hidden" id="uploadHidden_1"  value="' + fileName + '" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls"></div>'
						+ '<div id="imageName_1" class="imageName">' + fileName + '</div>'
						+ '</td></tr>'
					$("#doctbodyDataforcontact").html(tbl);
				} else {
					imageName_1 = '';
					tbl += '<tr><td>'
						+ '<div class="control-group position-r">'
						+ '<label class="custom-file-upload" for="uploadDoc_1" id="uploadFor_1"> <i class="ti-plus"></i>'
						+ '</label><div class="controls">'
						+ '<input type="file" class="document" id="uploadDoc_1" name="userImage" accept="image/*" onchange="saveMultiFileForLead(event)" />'
						+ '</div></div>'
						+ '<input type="hidden" id="uploadHidden_1" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls"></div>'
						+ '<div id="imageName_1" class="imageName">  </div>'
						+ '</td></tr>'
					$("#doctbodyDataforcontact").html(tbl);
				}
			}
		},
		error: function(data) {
		}
	});
}



function backToPage(event) {
	event.preventDefault();
	$('.loader').show();
	if (ownerId == userId) {
		$('.loader').show();
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest + "view-crm-leads"
	} else if (ownerId != userId) {
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest + "admin-executive-report"
	}
	$('.loader').hide();
}


function toggleCC() {
	document.getElementById("ccField").style.display = "block";
	document.getElementById("bccField").style.display = "block";
}

function onSelectionChanged() {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	productId = selectedData.map(node => node.productId);
	var selectedRows = gridOptionsLead.api.getSelectedRows();
	id = "";
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + '"' + selectedRows[i].productId + '",';
	}
	id = id.substring(0, id.length - 1);
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$("#deletePrdBtn").attr("disabled", false);
	} else {
		$("#deletePrdBtn").attr("disabled", true);
	}
}

function hideInviteMettingSearchField() {
	$(".leadNameCls1").hide();
	$(".contactNameCls1").hide();
	$(".executiveNameCls1").hide();
	$(".meetingleadName1").val("");
	$(".leadId").val("");
	$(".meetingcontactName1").val("");
	$(".contactId").val("");
	$(".excutiveName").val("");
	$(".excutiveId").val("");
}


/* Customer Creation
*/

/**
* Add Customer before covert lead into contact
*/
function AddCustomer1() {

	var leadId = $("#leadId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail?id=" + leadId,
		success: function(response) {

			if (response.code == "Success") {
				$("#companyName").val(response.body[0].company);
				$("#customerName1").val(response.body[0].company);
				$("#customerDisplayName").val(response.body[0].company);
				$("#webSite").val(response.body[0].website);
				$('#custMobile').text(response.body[0].phone);
				$("#country").val(response.body[0].countryId);
				//$("#states").val(response.body[0].stateId);
				getStateDataOnEdit1(response.body[0].stateId);
				$('#city').val(response.body[0].city);
				$('#street1').val(response.body[0].addressStreet);
				$("#zipCode").val((response.body[0].zip));
				$('#fax').val(response.body[0].fax);

				//$("#city").val('');
			}
		}
	});

	$('.formValidation').remove();
	$('#addCustomerModel').modal('show');
	$("#whatsApp").val('');
	$("#gstNo").val('');
	$("#customerType").val('');
	$("#salutation").val('');
	//$("#customerName1").val('');
	//$("#companyName").val('');
	//$("#customerDisplayName").val('');
	$("#cusEmail").val('');
	$("#custMobile").val('');
	$("#custSkype").val('');
	$("#custDesignation").val('');
	$("#department").val('');
	//$("#webSite").val('');
	$("#status").val('');
	$("#pan").val('');
	$("#currency").val('');
	$("#openingBalance").val('');
	$("#paymentTerms").val('');
	$("#enableDtls").val('');
	$("#portableLang").val('');
	$("#faceBook").val('');
	$("#twitter").val('');
	//$("#country").val('');
	//$("#states").val('');
	//$("#city").val('');
	//$("#street1").val('');
	$("#street2").val('');
	//$("#zipCode").val('');
	//$("#phone").val('');
	$("#fax").val('');
	$("#country1").val('');
	$("#states1").val('');
	$("#city1").val('');
	$("#street11").val('');
	$("#street21").val('');
	$("#zipCode1").val('');
	$("#phone1").val('');
	$("#fax1").val('');
	$("#salutation1").val('');
	$("#street11").val('');
	$("#street21").val('');
	$("#zipCode1").val('');
	$("#phone1").val('');
	$("#fax1").val('');
	$("#salutation1").val('');

	$("#firstName").val('');
	$("#lastName").val('');
	$("#emailAdd").val('');
	$("#mobile").val('');
	$("#remarks").val('');
	$("#mainSa").hide();
	$("#contactPersonAcc").hide();
	$("#remarksAcc").hide();
	$("#btnDiv").show();
	$("#saveAdd").hide();
	$('#gstNoDiv').show();
	$('#next').show();
	$('#gstNo').val("");
}

function vendorNameUpperr() {
	var text = $("#companyName").val();
	var result = text.toUpperCase();
	$("#companyName").val(result);
}

function checkAlphabet(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z. ]/g, '');
	tempVal = tempVal.replace(/^\w/, c => c.toUpperCase());

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (position == 1 && tempVal.charAt(0) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

function checkNumeric(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (tempVal.slice(-1) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
}

function getStateDataOnEdit1(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "customer-modal-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1").append(option);
						$("#states").append(option);
					}
					$("#states1").val(stateId);
					$("#states").val(stateId);

				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1").append(option);
	}
}

function copyAsBillingAdd() {
	document.getElementById("mySidenav").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom:-369px;";

	document.getElementById("mainSa").style.width = "70%";
	var customerId = $("#customerId").text();

	$("#shippingId").text(customerId);
	var country = $('#country').val();
	var states = $('#states').val();
	var city = $('#city').val();
	var street1 = $('#street1').val();
	var street2 = $('#street2').val();
	var zipCode = $('#zipCode').val();
	var phone = $('#phone').val();
	var fax = $('#fax').val();

	$('#country1').val(country);
	//$('#states1').val(states);
	getStateDataOnEdit1(states);
	$('#city1').val(city);
	$('#street11').val(street1);
	$('#street21').val(street2);
	$('#zipCode1').val(zipCode);
	$('#phone1').val(phone);
	$('#fax1').val(fax);
}



/*
	Delete Product
*/

function deleteProduct() {
	let productIds = "";
	const productList = gridOptionsLead.api.getSelectedRows();

	productList.forEach(data => {
		productIds += data.productId + ',';
	})
	productIds = productIds.substring(0, productIds.length - 1) + "|" + $("#leadId").text();


	fetch('view-crm-leads-delete-product', {
		method: 'POST',
		headers: {
			'Content-Type': 'text/plain' // Set the content type to text/plain for sending string data
		},
		body: productIds // Provide the string data directly as the body
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Something went wrong!")
			}

			return response.json();
		}
		).then(data => {
			if (data.code == "Success") {
				getProduct($("#leadId").text());
				getActivity($("#leadId").text(), "allTimeline");
				getTimeline($("#leadId").text(), "allTimeline");
				getProductActivity($("#leadId").text(), "productTimeline");
				$("#messageParagraph").text(data.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#deletePrdBtn").attr("disabled", true);
			}
		}).catch(err => {
			console.error("Error While fetching data ", err)
		})
}



/**
 * Admin Approve Lead
 */

function adminApprove(id) {
	const leadId = btoa($("#leadId").text());

	$.ajax({
		type: "GET",
		url: "admin-approval?lead=" + leadId + "&rejectStatus=" + id,
		success: function(response) {
			if (response.code == "Success") {
				getCrmNotifications();
				if (response.body == null) {
					$("#messageParagraph").text("Rejected Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".failureAprovBtn").show();
					$(".apprv").hide();
				} else {
					convertLeadToNextStep(JSON.parse(response.body)[0]);
					$("#messageParagraph").text("Approved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".apprv").hide();
					$(".sucessAprovBtn").show();
					let leadId = $("#leadId").text();
					$.ajax({
						type: "GET",
						url: "view-crm-leads-view-detail?id=" + leadId,
						success: function(response) {
							if (response.code == "Success") {
								$('#leadStatus').text(response.body[0].leadStatus);
							}
						}
					});
				}
			}
		}
	});
}

// Update Lead Status Function From Lead Deatils Page

function updateLeadStatus(id) {
	var obj = {};
	const adminApprovalStatus = sessionStorage.getItem("adminApprvStatus");
	obj.leadStatus = id;
	obj.leadId = $("#leadId").text();
	if (obj.leadStatus == "TLSM00004" && adminApprovalStatus != true) {
		obj.adminApprvStatus = true;
		obj.statusUpdatedFrom = "leadDetailsPage";
	} else if (obj.leadStatus == "TLSM00010") {
		$("#messageParagraph").text("Waiting for Admin Approval!");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	} else {

		obj.leadOwner = $('#leadExecutive').val();
		obj.company = $('#company').val();
		obj.firstName = $('#firstName').val();
		obj.lastName = $('#lastName').val();
		obj.title = $('#title').val();
		obj.email = $('#email').val();
		obj.phone = $('#phone').val();
		obj.fax = $('#projectId').val();
		obj.mobile = $('#mobile').val();
		obj.website = $('#website').val();
		obj.referenceContact = $('#referenceContact').val();
		obj.leadSource = $('#leadSource').val();
		//obj.leadStatus = $('#leadStatus').val();
		obj.industry = $('#industry').val();
		obj.noOfEmp = $('#noOfEmp').val();
		obj.annualRevenue = $('#annualRevenue').val();
		obj.ratings = $('#ratings').val();
		obj.emailOpt = $('#emailOpt').val();
		obj.skypeId = $('#skypeId').val();
		obj.secondaryEmail = $('#secondaryEmail').val();
		obj.twitter = $('#twitter').val();
		obj.country = $('#country').val();
		obj.states = $('#states').val();
		obj.city = $('#city').val();
		obj.addressStreet = $('#addressStreet').val();
		obj.zip = $('#zip').val();
		obj.description = $('#description').val();
		obj.createdBy = $('#createdBy').val();
		obj.adminApprvStatus = false;
		obj.statusUpdatedFrom = "leadDetailsPage";
	}


	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-lead-details",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code == "Success") {
				let leadId = $("#leadId").text();
				$.ajax({
					type: "GET",
					url: "view-crm-leads-view-detail?id=" + leadId,
					success: function(response) {
						if (response.code == "Success") {
							$('#leadStatus').text(response.body[0].leadStatus);
							var leadStatusDropdown = document.getElementById('leadStatusList');
							var selectedStatusValue = response.body[0].leadStatusId;

							for (var i = 0; i < leadStatusDropdown.children.length; i++) {
								var listItem = leadStatusDropdown.children[i];
								var listItemKey = listItem.querySelector('a').getAttribute('data-key');

								if (listItemKey <= selectedStatusValue) {
									listItem.style.display = 'none';
								}
							}
						}
					}
				});
			}
		},
		error: function(data) {
		}

	});
}

/*
* Date Validation For Campaign
* From Date & To Date 
*/
// for Meetings
function dateValid() {
	if ($("#meetingFromDate").val() && $("#meetingToDate").val())
		dateValidation('meetingFromDate', 'meetingToDate', 'From Date', 'To Date');
}

// for campaigns
function dateValidCapaign() {
	if ($("#startDate").val() && $("#endDate").val())
		dateValidation('startDate', 'endDate', 'Start Date', 'End Date');
}

// Call From Time & To Time Validations
function timeValidCall() {
	if ($("#callStartTime11").val() && $("#callEndTime11").val())
		timeValidation("callStartTime11", "callEndTime11", "Call Start Time", "Call End Time");
}

// Meeting From Time & To Time Validations
function timeValidMeeting() {
	if ($("#meetingFromTime").val() && $("#meetingToTime").val())
		timeValidation("meetingFromTime", "meetingToTime", "Meeting From Time", "Meeting To Time");
}

function onQuickFilterChanged() {
	gridOptionsProduct.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	gridOptionsProduct.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
}


/*for delete campaign*/

function deleteCampaign() {
	var selectedRows = gridOptionsCampaigns.api.getSelectedRows();
	deleteId = "";
	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + selectedRows[i].campaignId + ',';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	$.ajax({
		type: "GET",
		url: "view-crm-campaigns-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				$(".modal-backdrop").hide();
				$("#messageParagraph").text("Campaign Deleted Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$('#delete').attr("disabled", true);
				$("#msgModal").modal('show');
				var leadId = $("#leadId").html()
				getCampaign(leadId);
			}
		}

	});
}

function onSelectionGetCampaign() {
	var selectedRows = gridOptionsCampaigns.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#delete').attr("disabled", false);

	} else {
		$('#delete').attr("disabled", true);

	}
}