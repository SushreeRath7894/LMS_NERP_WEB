var acticityCount = 0;
var acticityCount1 = 0;
var locationPermission = true;
var LocationMessage = "";
let userId = '';
let toMail = '';
let ownerId = '';
var coloredDeal = '';
let salesCustomerId = '';
let salesCustName = '';
let salesCustPhone = '';
let salesCustEmail = '';
$(document).ready(function() {
	$('#delete').attr("disabled", true);
	var callBackQuotesId = sessionStorage.getItem("callBackCrmQuotation");
	if (callBackQuotesId) {
		scrollQuotes();
	}
	sessionStorage.setItem("callBackCrmQuotation", "");

	// for sales order callback
	var callBackSalesId = sessionStorage.getItem("callBackCrmSales");
	if (callBackSalesId) {
		scrollSalesOrder();
	}
	sessionStorage.setItem("callBackCrmSales", "");

	// for Po order callback
	var callBackCrmPo = sessionStorage.getItem("callBackCrmPo");
	if (callBackCrmPo) {
		scrollPurchaseOrder();
	}
	sessionStorage.setItem("callBackCrmPo", "");

	// for Po order callback
	var callBackCrmSalesInvoice = sessionStorage.getItem("callBackCrmSalesInvoice");
	if (callBackCrmSalesInvoice) {
		scrollInvoices();
	}
	sessionStorage.setItem("callBackCrmSalesInvoice", "");

	/*Customer Creation Form Address Grid*/
	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);

	/**CUSTOMER CREATION */
	sessionStorage.setItem("customerID", "");
	$("#saveSaddressData").click(function() {
		var customerId = sessionStorage.getItem("customerID");
		$("#customerId").val(customerId);

		obj = {};

		obj.shippingId = $("#shippingId").val();
		obj.customerId = $("#customerId").val();
		obj.country1 = $('#country1').val();
		obj.states1 = $('#states1').val();
		obj.city1 = $('#city').val();
		obj.street11 = $('#street11').val();
		obj.street21 = $('#street21').val();
		obj.zipCode1 = $('#zipCode1').val();
		obj.phone1 = $('#phone1').val();
		obj.fax1 = $('#fax1').val()
		//obj.defaultStatus= $("input[name='isDefault']:checked").val();
		var ds = $("input[name='isDefault']:checked").val();
		if (ds == '1') {
			obj.defaultStatus = '1';
		} else {
			obj.defaultStatus = '0';
		}
		$(".formValidation").remove();
		allValid = true;

		if (obj.country1 == null || obj.country1 == "") {
			allValid = false;
			validationModal("Select County", "country1");
		}


		if (allValid) {

			submitAddressDetails(obj);
		}

	})
	/**CUSTOMER CREATION ENDS */

	coloredDeal = localStorage.getItem('noitificationDeal');

	$("#createQuotes a").addClass("quotes-dis");
	$("#createQuotes").addClass("quotebtn-div");
	sessionStorage.setItem("fromCRM", false);

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

	/**
	 * Added By Ashish Mishra For Activity Modal close
	 */
	document.querySelectorAll(".activity-close").forEach(function(element) {
		element.addEventListener("click", function() {
			document.getElementById('activities').value = "select";
		});
	});


	var d = new Date();

	var month = d.getMonth() + 1;
	var day = d.getDate();
	const monthNames = ["January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"
	];
	var year = d.getFullYear();
	var monthName = monthNames[d.getMonth()];

	var output = day + ' ' + monthName + ', ' + year;
	$('#todayDateUnderHistory').text(output);


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

	$("#deletePrdBtn").attr("disabled", true);

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


	$("#meetingCalendarToTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#meetingToTime').val($(this).val());
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
			$('div.formValidation').remove();
			userId = $("#userId").val();

			$('#contactOwner').val(userId);
			var contactId = $("#contactId").text();
			var accountId = $("#accountId").text();
			var accountName = $("#accountName").text();
			var firstName = $("#firstName").text();

			var lastName = $("#lastName").text();

			var contactName = firstName + " " + lastName;

			$('#taskContactName').val(contactName);
			$('#taskAccountName').val(accountName);
			$('#accountId').val(accountId);
			$('#contactId').val(contactId);
			$('#myModalAddTask').modal('show');

			$('#taskSubject').val('');
			$('#dueDate').val('');
			$('#taskStatus').val('');
			$('#taskPriority').val('');
			$('#descriptionTask').val('');

		} else if (value == "Meeting") {
			$('div.formValidation').remove();

			userId = $("#userId").text();
			// alert(userId);
			$('#meetingHost').val(userId);
			var contactId = $("#contactId").text();
			var accountId = $("#accountId").text();
			var accountName = $("#accountName").text();
			var firstName = $("#firstName").text();

			var lastName = $("#lastName").text();

			var contactName = firstName + " " + lastName;

			$('#meetingcontactName').val(contactName);
			$('#taskAccount').val(accountName);
			$('#accountId').val(accountId);
			$('#participantId').val(contactId);
			$('#myModalAddMeeting').modal('show');

           var textContent = $('#contactNameHead').text();
			$("#meetingContact").val(textContent);
           
			$('#meetingTitle').val('New Meeting');
			$('#meetingLocation').val('');
			$('#meetingFromDate').val('');
			$('#meetingFromTime').val('');
			$('#meetingToDate').val('');
			$('#meetingToTime').val('');
			$('#meetingStatus').val('Scheduled');
			$('#descriptionMeeting').val('');

		} else if (value == "Call") {
			$('div.formValidation').remove();
			$('#myModalAddCall').modal('show');


			$('#callSubject').val('');
			$('#callType').val('Outbound');
			$('#callStatus').val('Scheduled');
			$('#callStartDate11').val('');
			$('#callStartTime11').val('');
			$('#callEndTime11').val('Scheduled');
			$('#callPurpose').val('');
			$('#callAgenda').val('');


			var contactId = $("#contactId").text();
			userId = $("#userId").text();
			$('#callOwner').val(userId);
			var accountId = $("#accountId").text();

			var accountName = $("#accountName").text();
			var firstName = $("#firstName").text();

			var lastName = $("#lastName").text();

			var contactName = firstName + " " + lastName;
			$('#cName').val(contactName);
			$('#taskAccount1').val(accountName);
			$('#accountId').val(accountId);
			$('#contactId').val(contactId);

		}
	});

	var respDeal = (localStorage.getItem('conDeal'));
	var scrollDeal = (localStorage.getItem('redirectDeal'));
	console.log("===dataaaaaaaaaaaaa====>" + respDeal);
	if (respDeal) {
		$("#messageParagraph").text("Deal Added Successfully");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		scrollDeals();
	}
	if (scrollDeal) {
		scrollDeals();
	}
	localStorage.setItem('redirectDeal', "");
	localStorage.setItem('conDeal', "");
});

$(document).ready(function() {


	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#myGridStages');
	new agGrid.Grid(gridDiv, gridOptionsStages);

	var gridDiv = document.querySelector('#myGridProduct');
	new agGrid.Grid(gridDiv, gridOptionsProduct);


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

	var contactId = $("#contactId").text();

	var promise3 = new Promise(function(resolve, reject) {
		$.ajax({
			type: "GET",
			url: "view-crm-contacts-detailsview?id=" + contactId,
			async: true,
			success: resolve,
			error: reject,
		});
	});
	promise3.then(function(response) {
		if (response.message === "Success") {

			$("#customerId").val(response.body.customerId)
			var accountName = response.body.accountName;
			var accountId = response.body.accountId;
			var firstName = response.body.firstName;
			//alert(firstName)
			$("#firstName").text(firstName);
			var lastName = response.body.lastName;
			$("#lastName").text(lastName);
			//alert(lastName)

			console.log("accountName:", accountName);
			$('#accountNameElement').text(accountName);
		} else {
			console.log("Promise was rejected or the response was not successful.");
		}
	});

	/*------View all the data when the page is loading----------*/
	var contactId = $("#contactId").text();


	var contact = $("#pipelineId").text();
	var accountName = $("#accountName").text();

	$.ajax({
		type: "GET",
		url: "view-crm-contacts-detailsview?id=" + contactId,
		success: function(response) {

			if (response.message == "Success") {
				toMail = response.body.email;
				ownerId = response.body.contactOwnerId;
				salesCustomerId = response.body.customerId;
				salesCustEmail = response.body.salesCustEmail;
				salesCustPhone = response.body.salesCustPhone;
				salesCustName = response.body.salesCustName;
				$('#pipelineId').text(response.body.pipelineId);
				$('#contactOwner1').text(response.body.contactOwner);
				$('#contactOwner2').text(response.body.contactOwner);
				$('#leadSource').text(response.body.leadSource);
				$('#firstName').text(response.body.firstName);
				$('#lastName').text(response.body.lastName);
				$('#contactNameHead').text(response.body.firstName + " " + response.body.lastName);
				$('#accountNameHead').text(response.body.accountName);
				$('#accountName').text(response.body.accountName);
				$('#accountId').text(response.body.accountId);
				$('#contactName').text(response.body.firstName + " " + response.body.lastName);
				$('#title').text(response.body.title);
				$('#email').text(response.body.email);
				$('#email1').text(response.body.email);
				$('#department').text(response.body.department);
				$('#department1').text(response.body.department);
				$('#phone').text(response.body.phone);
				$('#phone1').text(response.body.phone);
				$('#homePhone').text(response.body.homePhone);
				$('#otherPhone').text(response.body.otherPhone);
				$('#projectId').val(response.body.fax);
				$("#project").text(response.body.projectName);
				$("#projectName").text(response.body.projectName);
				$('#mobile').text(response.body.mobile);
				$('#mobile1').text(response.body.mobile);
				$('#dateBirth').text(response.body.dateBirth);
				$('#assistant').text(response.body.assistant);
				$('#assistPhone').text(response.body.assistPhone);
				$('#emailOpt').text(response.body.emailOpt);
				$('#skypeId').text(response.body.skypeId);
				$('#secondaryEmail').text(response.body.secondaryEmail);
				$('#twitter').text(response.body.twitter);
				$('#reportingTo').text(response.body.reportingTo);
				$('#mailingStreet').text(response.body.mailingStreet);
				$('#otherMailing').text(response.body.otherMailing);
				$('#mailingCity').text(response.body.mailingCity);
				$('#otherCity').text(response.body.otherCity);
				$('#mailingState').text(response.body.mailingState);
				$('#otherState').text(response.body.otherState);
				$('#mailingZip').text(response.body.mailingZip);
				$('#otherZip').text(response.body.otherZip);
				$('#mailingCountry').text(response.body.mailingCountry);
				$('#otherCountry').text(response.body.otherCountry);
				$('#createdBy').text(response.body.vendorName);
				$('#updatedBy').text(response.body.vendorName);
				//getStateDataOnEdit(response.body[0].states);
				$('#description').text(response.body.description);
				$('#referenceContact').text(response.body.referenceContact);

				getNote(contact, "1", "", "");
				getMail(contact);
				getDraft(contact);
				getProduct(contact);
				getCampaign(contact);
				getTask(contact);
				getAction(contact);
				getMeeting(contact);
				//getInvitedMeetings(contact);
				getCall(contact);
				getDeals(contact);
				getActivity(contact, "allTimeline");
				getProductActivity(contact, "productTimeline");
				getTimeline(contact, "allTimeline");
				getCount(contact);
				getActionDeals(contact);
				getQuotes(contact);
				viewPurchaseOrder(contact);
				viewSalesInvoice(contact);
				viewSalesOrder(contact);
				$('.loader').hide();
				$("body").removeClass("overlay");
				if (ownerId != $("#userId").text()) {
					$("#mailBtn").hide();
					$("#editContactBtn").hide();
					$("#noteBtn").hide();
					$(".addDeals").hide();
					$("#createQuotes").hide();
					$("#addProductBtn").hide();
					$("#deleteProductBtn").hide();
					$("#openActivityBtn").hide();
					$("#composeMailBtn").hide();
					$("#addCampaignBtn").hide();
					$("#dealRedirect").hide();
					$("#quoteRedirect").hide();
					$("#salRedirect").hide();
					$("#poRedirect").hide();
					$("#invRedirect").hide();
					$("#clodeDealBtn").show();
				}
			}
		}
	});

	/// Cc Mail AUtosearching 
	document.getElementById("ccMail").addEventListener("keyup", function() {


		let searchVal = "";

		searchVal = $("#ccMail").val();
		if (searchVal == "") {
			$("#suggesstion-mailBox_").hide();
		}

		searchVal != "" && searchVal != null && searchVal != "null" ? getMailsAutosearch(searchVal) : console.log("No Data Found!");
	});

});

function getCustomerDetailsForSales() {
	var contactId = $("#contactId").text();
	$("#cusName").empty();
	$("#cusEmailModal").empty();
	$("#cusPhone").empty();

	$.ajax({
		type: "GET",
		url: "view-crm-contacts-detailsview?id=" + contactId,
		success: function(response) {

			if (response.message == "Success") {
				salesCustEmail = response.body.salesCustEmail;
				salesCustPhone = response.body.salesCustPhone;
				salesCustName = response.body.salesCustName;
				$("#cusName").append(salesCustName);
				$("#cusEmailModal").append(salesCustEmail);
				$("#cusPhone").append(salesCustPhone);
			}
		}
	});
}

/* AG-Grid for the Deals Stages Start */
var stagesDefs = [{
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
	headerName: "Stage",
	field: "stageName",
	width: 200,
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a>' + params.data.stageName + '</a>';
	}
},
{
	headerName: "Amount",
	field: "dealAmount",
	width: 200,
	cellStyle: { textAlign: 'right' },
	cellRenderer: function(params) {
		const formattedAmount = parseFloat(params.data.dealAmount).toFixed(2);
		return '<a>' + formattedAmount + '</a>';

	}
},
{
	headerName: "Probability(%)",
	field: "probability",
	width: 200,
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		return '<a>' + params.data.probability + '</a>';
	}

},
{
	headerName: "Expected Revenue",
	field: "expectedRevenue",
	width: 250,
	cellStyle: { textAlign: 'right' },
	cellRenderer: function(params) {
		const formattedRevenue = parseFloat(params.data.expectedRevenue).toFixed(2);
		return '<a>' + formattedRevenue + '</a>';
	}
},
{
	headerName: "Closing Date",
	field: "dealClosingDate",
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		return '<a>' + params.data.dealClosingDate + '</a>';
	}
},
{

	headerName: "Modified Time",
	field: "updatedOn",
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		return '<a>' + params.data.updatedOn + '</a>';
	}

}, {
	headerName: "Stages Summary",
	field: "stageSummary",
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		if (params.data.stageSummary === null || params.data.stageSummary === undefined) {
			return '<a>N/A</a>';
		} else {
			return '<a>' + params.data.stageSummary + '</a>';
		}
	}
},
{

	headerName: "Modified By",
	field: "updatedBy",
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		return '<a>' + params.data.updatedBy + '</a>';
	}

}]
var gridOptionsStages = {
	columnDefs: stagesDefs,
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

function showCustSucessModal() {
	$("#msgModalCrm").modal('hide');
	getCustomerDetailsForSales();
	$("#custVarifySucessModal").modal('show');
}

function redirectToSales() {
	if (redirectBtn == 'redirectToQuotes') {

		sessionStorage.setItem("fromCRM", "true");
		sessionStorage.setItem("crmContactId", $("#contactId").text());
		window.location.href = "/sales/view-quotation";

	} else if (redirectBtn == 'redirectToSo') {

		sessionStorage.setItem("fromCRM", "true");
		sessionStorage.setItem("crmContactId", $("#contactId").text());
		window.location.href = "/sales/view-saleorder";

	} else if (redirectBtn == 'redirectToPo') {

		sessionStorage.setItem("fromCRM", "true");
		sessionStorage.setItem("crmContactId", $("#contactId").text());
		window.location.href = "/sales/view-po-or-wo";

	} else if (redirectBtn == 'redirectToInv') {

		sessionStorage.setItem("fromCRM", "true");
		sessionStorage.setItem("crmContactId", $("#contactId").text());
		window.location.href = "/sales/view-saleInvoice";
	} else {

		$("#messageParagraph").text("Something Went Wrong!");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');

	}
}


/* Get Quotation function */
let redirectBtn = '';
function getQuotation() {
	$("#cusName").empty();
	$("#cusEmailModal").empty();
	$("#cusPhone").empty();
	$("#btnContent").empty();
	redirectBtn = "redirectToQuotes";
	if (salesCustomerId == "null" || salesCustomerId == "") {
		$("#custVarifyFailedModal").modal('show');
		$("#btnContent").append('Create Quotation');
	} else {
		$("#custVarifySucessModal").modal('show');
		$("#btnContent").append('Create Quotation');
		getCustomerDetailsForSales();
	}
}

function qoutationPdfDownload(quotationId) {

	var organization = $("#sessionOrganization").val();
	var orgDivision = $("#sessionOrgDivision").val();
	var userId = $("#sessionId").val();
	window.open("/sales/view-quotation-pdf-downloads?quotationId="
		+ window.btoa(quotationId) + "&organization="
		+ window.btoa(organization) + "&orgDivision="
		+ window.btoa(orgDivision) + "&userId="
		+ window.btoa(userId), '_blank');
}

/* Get Sales Order function */
function getSalesOrder() {
	$("#cusName").empty();
	$("#cusEmailModal").empty();
	$("#cusPhone").empty();
	redirectBtn = "redirectToSo";
	$("#btnContent").empty();
	if (salesCustomerId == "null" || salesCustomerId == "") {
		$("#custVarifyFailedModal").modal('show');
		$("#btnContent").append('Create SO')
	} else {
		$("#custVarifySucessModal").modal('show');
		$("#btnContent").append('Create SO');
	    getCustomerDetailsForSales();
	}
}

/* Get Purchase Order function */
function getPurchasesOrder() {
	$("#cusName").empty();
	$("#cusEmailModal").empty();
	$("#cusPhone").empty();
	redirectBtn = "redirectToPo";
	$("#btnContent").empty();
	if (salesCustomerId == "null" || salesCustomerId == "") {
		$("#custVarifyFailedModal").modal('show');
		$("#btnContent").append('Create PO');
	} else {
		$("#custVarifySucessModal").modal('show');
		$("#btnContent").append('Create PO');
		getCustomerDetailsForSales();
	}
}

/* Get Invoice function */
function getSalesInvoice() {
	$("#cusName").empty();
	$("#cusEmail").empty();
	$("#cusPhone").empty();
	redirectBtn = "redirectToInv";
	$("#btnContent").empty();
	if (salesCustomerId == "null" || salesCustomerId == "") {
		$("#custVarifyFailedModal").modal('show');
		$("#btnContent").append('Create Invoices');
	} else {
		$("#custVarifySucessModal").modal('show');
		$("#btnContent").append('Create Invoices');
		getCustomerDetailsForSales();
	}
}


function getParticipantLead(searchVal, dropdown) {

	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val()


	if (searchVal == "") {
		$(".suggesstion-boxmeetingLead1_").hide();
	}
	let searchId = '';
	searchVal != "" && searchVal != null && searchVal != "null" ? getNameListParticipants1(searchId, val, searchVal, "lead") : console.log("No Data Found!");
	//getNameListParticipants1(searchId, val, searchVal,"lead");
}

function getParticipantContact(searchVal, dropdown) {

	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val();

	if (searchVal == "") {
		$(".suggesstion-boxmeetingContact1_").hide();
	}
	let searchId = $("#pipelineId").text();
	searchVal != "" && searchVal != null && searchVal != "null" ? getNameListParticipants1(searchId, val, searchVal, "contact") : console.log("No Data Found!");

	//getNameListParticipants1(searchId, val, searchVal,"contact");
}

function getParticipantExcecutive(searchVal, dropdown) {
	var modal = dropdown.closest('.modal');

	let val = $('#' + modal.id + ' .inviteMeeting').val()

	if (searchVal == "") {
		$(".suggesstion-boxmeetingExcutives_").hide();
	}
	let searchId = $("#userId").text();

	searchVal != "" && searchVal != null && searchVal != "null" ? getNameListParticipants1(searchId, val, searchVal, "excutive") : console.log("No Data Found!");

	//getNameListParticipants1(searchId, val, searchVal,"excutive");
}

function saveCampaignInfo() {
	var dataset = [];
	console.log(searchIDs);
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

	console.log('dataset for campaign------' + JSON.stringify(dataset));
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
					"Data Saved Successfully");


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
	console.log(searchIDs);

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


	console.log('data set for add row------------' + JSON.stringify(dataset))
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
					"Data Saved Successfully");


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
	console.log(searchIDs);
	var isOverWrite = $("input:checkbox[name=IsOverwriteExistingTags]:checked").val();
	for (let i = 0; i < searchIDs.length; ++i) {
		item = {};
		item['leadId'] = searchIDs[i];
		item['tagsName'] = $("#tagsName").val();
		item['isOverWrite'] = isOverWrite;
		dataset.push(item);
	}

	console.log('dataset for tags------' + JSON.stringify(dataset));
	//return false;
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
					"Data Saved Successfully");


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
	console.log('emails--------------', emails);
	var dataset = [];
	console.log(searchIDs);
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

	console.log('dataset for task------' + JSON.stringify(dataset));
	//return false;
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
					"Data Saved Successfully");


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



function saveMultiFileForContact(event) {
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

	cellRenderer: function(params) {
		return '<a >' + params.data.productId + '</a>';
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
	cellStyle: { textAlign: 'left' }
},
{

	headerName: "Create Date",
	field: "createdDate",
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
/* -----------------Deals Grid Start------------------ */

var dealsDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Deal Name",
	field: "dealName",
	width: 150,
	pinned: 'left',
	cellRenderer: function(params) {
		if (ownerId != $("#userId").text()) {
			return '<a>' + params.data.dealName + '</a>';
		} else {
			return '<a onclick="openContactDeals(\'' + params.data.dealId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("dealFun") + '\',\'' + sessionStorage.getItem("dealAct") + '\')" href="javascript:void(0)">' + params.data.dealName + '</a>';
		}
	}
}, {
	headerName: "Deal Id",
	field: "dealId",
	hide: true,
},
{
	headerName: "Amount",
	field: "dealAmount",
	width: 150,
	cellStyle: { textAlign: 'right' },
	cellRenderer: function(params) {
		const value = parseFloat(params.value);
		if (!isNaN(value)) {
			return value.toFixed(2);
		} else {
			return params.value;
		}
	}
},
{
	headerName: "Stage",
	field: "dealStage",
	width: 300,
	cellStyle: { textAlign: 'right' }

},
{

	headerName: "Probability(%)",
	field: "probability",
	width: 250,
	cellStyle: { textAlign: 'center' }
},{

	headerName: "Deals Stages",
	width: 200,
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		var dealId = params.data.dealId;
		var dealsName = "Stages";
		return '<div><i class="fa fa-history" aria-hidden="true" style="padding-right: 7px; color: #6a2bbf; cursor: pointer;" onclick="viewDealsStages(\'' + dealId + '\')"></i><a onclick="viewDealsStages(\'' + dealId + '\')" href="javascript:void(0)">' + dealsName + '</a></div>';
	}
},
{
	headerName: "Closing Date",
	field: "dealClosingDate",
	width: 250,
	cellStyle: { textAlign: 'right' }
},
{

	headerName: "Deals Stages",
	width: 200,
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		var dealId = params.data.dealId;
		var dealsName = "Stages";
		return '<div><i class="fa fa-history" aria-hidden="true" style="padding-right: 7px; color: #6a2bbf; cursor: pointer;" onclick="viewDealsStages(\'' + dealId + '\')"></i><a onclick="viewDealsStages(\'' + dealId + '\')" href="javascript:void(0)">' + dealsName + '</a></div>';
	}
},
{
	headerName: "Type",
	field: "dealType",
	width: 250,
	cellStyle: { textAlign: 'right' }


}]

var gridOptionsDeals = {
	columnDefs: dealsDefs,
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
	onSelectionChanged: onSelectionChangedDeals,
	rowClassRules: {
		'highlight-row': (param) => {
			const dealId = param.data.dealId
			const id = localStorage.getItem('noitificationDeal');
			return dealId === id
		}
	}
};

var leadid = "";
function onSelectionChangedDeals() {
	var selectedNodes = gridOptionsDeals.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	leadid = selectedData.map(node => node.dealId);
	var selectedRows = gridOptionsDeals.api.getSelectedRows();
	id = "";
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + '"' + selectedRows[i].dealId + '",';
	}
	id = id.substring(0, id.length - 1);
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$("#createQuotes a").removeClass("quotes-dis");
		$("#createQuotes").removeClass("quotebtn-div");
	} else {
		$("#createQuotes a").addClass("quotes-dis");
		$("#createQuotes").addClass("quotebtn-div");
	}
}

/* -----------------Quotation Grid Start------------------ */
var quotesDefs = [{
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
	headerName: "QUOTATION NO.",
	field: "quotNo",
	pinned: 'left',
	width: 130,
	cellRenderer: function(params) {
		return '<a href="javascript:void(0)">' + params.data.quotNo + '</a>';
	}
},
{
	headerName: "Subject",
	field: "subject",
	width: 300,
	cellStyle: { textAlign: 'center' },

},
{
	headerName: "Quotation Type",
	field: "quoteType",
	width: 250,
	cellStyle: { textAlign: 'center' }

},
{

	headerName: "Customer Name",
	field: "customerName",
	width: 300,
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Created Date",
	field: "createdDate",
	width: 250,
	cellStyle: { textAlign: 'center' }
}, {
	headerName: "PDF",
	cellStyle: {
		textAlign: 'center'
	},
	width: 150,
	cellRenderer: function(params) {
		var s = "";
		s = ' <a href="#" class="grn-btn" onclick="qoutationPdfDownload(\'' + params.data.quotNo + '\')"><i class="ti ti-download"></i> PDF </a>';
		return s;
	},
},
]
var gridOptionsQuotes = {
	columnDefs: quotesDefs,
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


/* -----------------Invoice Grid Start------------------ */
var invoiceDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: false,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,

},
{
	headerName: "Invoice Id.",
	field: "invoiceId",
	width: 150,
	cellRenderer: function(params) {
		return '<a href="javascript:void(0)">' + params.data.invoiceId + '</a>';
	}
},
{
	headerName: "Invoice Date",
	field: "createdDate",
	width: 280,
	cellStyle: { textAlign: 'center' },

},
{
	headerName: "Customer Name",
	field: "customerName",
	width: 280,
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Amount",
	field: "totalAmt",
	width: 250,
	cellStyle: { textAlign: 'right' }
},{
	headerName : "Invoice",
	cellStyle : {
		textAlign : 'center'
	},
	cellRenderer : function(params) {
		var s = "";
		s = ' <a href="#" class="grn-btn" onclick="invoicePdfDownloadModal(\''+params.data.invoiceId+'\')"><i class="ti ti-download"></i> Invoice </a>';
		return s;
	},
	width: 140,
}
]
var gridOptionsInvoice = {
	columnDefs: invoiceDefs,
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

/* -----------------Sales Order Grid Start------------------ */
var salesOrderDefs = [{
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
	headerName: "Sales Order Id.",
	field: "salesOrderId",
	pinned: 'left',
	width: 140,
	cellRenderer: function(params) {
		return '<a href="javascript:void(0)">' + params.data.salesOrderId + '</a>';
	}
},
{
	headerName: "Order Type",
	field: "orderType",
	width: 280,
	cellStyle: { textAlign: 'center' },
	//pinned: 'left',
},
{
	headerName: "Customer Name",
	field: "customerName",
	width: 280,
	cellStyle: { textAlign: 'center' }

}, {
	headerName: "Created Date",
	field: "createdDate",
	width: 280,
	cellStyle: { textAlign: 'center' }

},
]
var gridOptionsSalesOrder = {
	columnDefs: salesOrderDefs,
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


/* -----------------Purchase Order Grid Start------------------ */
var poDefs = [{
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
	headerName: "PO ID",
	field: "poId",
	pinned: 'left',
	width: 120,
	cellRenderer: function(params) {
		return '<a href="javascript:void(0)">' + params.data.poId + '</a>';
	}
},
{
	headerName: "Quotation Id",
	field: "quoteId",
	width: 200,
	cellStyle: { textAlign: 'right' },

},
{
	headerName: "Refrence Id",
	field: "refrenceNo",
	width: 200,
	cellStyle: { textAlign: 'center' }
},
{

	headerName: "Order Tppe",
	field: "orderType",
	width: 200,
	cellStyle: { textAlign: 'center' }

},
{

	headerName: "Customer Name",
	field: "custName",
	width: 200,
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Created Date",
	field: "createdOn",
	width: 200,
	cellStyle: { textAlign: 'center' }

},
{
	headerName: "Sub Total",
	field: "subTotal",
	width: 200,
	cellStyle: { textAlign: 'right' }
},
{
	headerName: "Cgst",
	field: "cgst",
	width: 200,
	cellStyle: { textAlign: 'right' }
}, {
	headerName: "Sgst",
	field: "sgst",
	width: 200,
	cellStyle: { textAlign: 'right' }
}, {
	headerName: "Igst",
	field: "igst",
	width: 200,
	cellStyle: { textAlign: 'right' }
}, {
	headerName: "Total Amount",
	field: "totalAmt",
	width: 200,
	cellStyle: { textAlign: 'right' }
},

]
var gridOptionsPo = {
	columnDefs: poDefs,
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
	cellRenderer: function(params) {
		if (ownerId != $("#userId").text()) {
			return '<span><strong>' + params.data.campaignName + '</strong></span>';

		} else {
			return '<a onclick="openCampaignDetails(\'' + params.data.campaignId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("campaignFun") + '\', \'' + sessionStorage.getItem("campaignAct") + '\')" href="javascript:void(0)">' + params.data.campaignName + '</a>';

		}
	}
},
{
	headerName: "Status",
	field: "campaignStatus",
	width: 200,
	cellStyle: { textAlign: 'left' },
},
{
	headerName: "Type",
	field: "campaignType",
	cellStyle: { textAlign: 'left' }

},
{

	headerName: "Start Date",
	field: "startDate",
	width: 250,
},
{
	headerName: "End Date",
	field: "endDate",
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Expected Revenue",
	field: "expectedRevenue",
	cellStyle: { textAlign: 'right' }
},
{

	headerName: "Budgeted Cost",
	field: "budgetedCost",
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

}]
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
	var filterDate = $("#dateFilter").val();
	var filterTitle = $("#noteTitleSearch").val();
	var contact = $("#contactId").text();

	statuss = true;
	getNote(contact, "1", filterDate, filterTitle)
}


var statuss = true;
function getNote(contact, cuPages, filterDate, filterTitle) {
	getNoteAll(contact, cuPages, filterDate, filterTitle)
}

let doclen = '';
function editNote(id) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-edit-note?id=" + id,
		success: function(response) {
			var resp = JSON.parse(response.body[0]);
			console.log(resp.NoteDetails);
			$('#leadNoteId').val(resp.NoteDetails[0].noteId);
			$('#titleId').val(resp.NoteDetails[0].noteTitle);
			$('#noteId').val(resp.NoteDetails[0].noteDesc);
			$('#documentName').val(resp.NoteDetails[0].noteDocName);

			var doclist = JSON.parse(resp.NoteDetails[0].documentList);
			docListlen = doclist.length;
			$('#docListlen').val(docListlen);
			console.log(doclist, 'doclistdoclist');
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
				console.log(response);
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
function getMail(contactId) {

	$("#countEmail").empty().append(0);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-mail?id=" + contactId,
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = resp.mailList.length;
		$('#totalEmp').find('span').html(len);
		$("#countEmail").empty().append(len);
		console.log(resp.mailList, 'notice')
		mailgridOptions.api.setRowData(resp.mailList);
	});

}
function getDraft(contact) {
	$("#countDraft").empty();
	var rowData = [];
	draftgridOptions.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-draft?id=" + contact,
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = resp.draftList.length;
		$("#countDraft").append(len);
		$('#totalEmp').find('span').html(len);
		draftgridOptions.api.setRowData(resp.draftList);
	});
}

function addProductToLead() {

	var selectedRows = gridOptionsProduct.api.getSelectedRows();
	console.log("selectedRows", selectedRows);

	if (selectedRows.length > 0) {

		selectedRows.forEach((itm) => {
			itm.leadId = $("#leadId").html();
			itm.contactId = $("#contactId").text();
		});
		var contact = $("#contactId").text();

		// Fetch existing data from the other table
		agGrid.simpleHttpRequest({
			url: 'view-crm-leads-view-detail-product?id=' + contact,
		}).then(function(data) {
			console.log('---data----------------------', data.body);

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
							console.log('Response' + JSON.stringify(response));
							var contact = $("#pipelineId").text();
							getTimeline(contact, "allTimeline");
							getProduct(contact);
							getActivity(contact, "allTimeline");
							getProductActivity(contact, "productTimeline");

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

function getProduct(contact) {
	$("#countProduct").empty();
	agGrid.simpleHttpRequest({
		url: 'view-crm-leads-view-detail-product?id=' + contact,
	}).then(function(data) {
		console.log('---data----------------------', data.body);
		var prdLength = data.body.length;
		$("#countProduct").append(prdLength);
		gridOptionsLead.api.setRowData(data.body);
	});
}



function viewEmailDetails(id, tomail) {
	$('#myModalViewEmailContact').modal('show');

	var empId = $("#contactId").text();

	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-email-view?id1=" + empId + "&id2=" + id + "&id3=" + tomail,
		async: false,
		success: function(response) {

			if (response.message == "success") {
				console.log("edit", response.body)
				//$("#id").val(response.body.id);
				//$("#leadId").val(response.body.leadId);
				$("#fromEmail1").val(response.body.fromEmail);
				$("#toMail1").val(response.body.toMail);
				$("#mailSubject1").val(response.body.mailSubject);
				$("#docName1").val(response.body.docnoid);



				$("#emailAttachmentView").text(response.body.attachment);
				$("#emailAttachmentView").attr("href", response.body.ownerImageLink);


				CKEDITOR.instances['commentck1'].setData(response.body.commentck);
				$('#myModalViewEmailContact').modal('show');



			}
		},
		error: function(data) {
			console.log(data)
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

function getDeals(contact) {
	$("#countDeals").empty();
	var rowData = [];
	gridOptionsDeals.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-deals-through-ajax?id=' + contact
	}).then(function(data) {
		var len = data.body.length;
		$("#countDeals").append(len);
		gridOptionsDeals.api.setRowData(data.body);
		localStorage.setItem('noitificationDeal', "")
		var noRecordsMessage = document.getElementById('noRecordsMessage');
		var myGridDeals = document.getElementById('myGridDeals');

		if (data.body.length > 0) {
			// Data exists, hide the "No records found" message and show the Ag-Grid
			noRecordsMessage.style.display = 'none';
			myGridDeals.style.display = 'block';
			if (ownerId == $("#userId").text()) {
				$("#addDeals").show();
			}
		} else {
			// No data, show the "No records found" message and hide the Ag-Grid
			noRecordsMessage.style.display = 'block';
			myGridDeals.style.display = 'none';
			$("#addDeals").hide();
			$("#clodeDealBtn").hide();
		}
	});
}

// For Deals Stages

function viewDealsStages(id) {
	$("#dealStagesModal").modal("show");
	$.ajax({
		type: "GET",
		url: "view-crm-deals-detail-stage?id=" + id,
		success: function(response) {
			if (response.message == "Success") {
				var len = response.body.length;
				gridOptionsStages.api.setRowData(response.body);

			}
		}
	});
}

function closeDealStageModal() {
	$("#dealStagesModal").modal("hide");
}

// For View Quotations
function getQuotes(contact) {
	$("#countQuotes").empty();
	var rowData = [];
	gridOptionsQuotes.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-quotes-through-ajax?id=' + contact
	}).then(function(data) {
		if (data.code == "Success") {
			if (data.body.length > 0) {
				var resp = JSON.parse(data.body);
				var len = resp.length;
				$("#countQuotes").append(len);
				gridOptionsQuotes.api.setRowData(resp);
				quotesGrid.style.display = 'block';
				$("#recordsMessage").hide();
				$("#addQuotes").show();
			}
			else {
				quotesGrid.style.display = 'none';
				$("#recordsMessage").show();
				$("#addQuotes").hide();
			}

		}
	});
}


// For View Quotations
function viewPurchaseOrder(contact) {
	$("#countPurchasesorder").empty();
	var rowData = [];
	gridOptionsPo.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-purchase-through-ajax?id=' + contact
	}).then(function(data) {
		if (data.code == "Success") {
			if (data.body.length > 0) {
				var resp = JSON.parse(data.body);
				var len = resp.length;
				$("#countPurchasesorder").append(len);
				gridOptionsPo.api.setRowData(resp);

				$("#poGrid").show();
				$("#nodataPoMessage").hide();
				$("#addPo").show();
			} else {
				// No data, show the "No records found" message and hide the Ag-Grid
				$("#poGrid").hide();
				$("#nodataPoMessage").show();
				$("#addPo").hide();
			}
		}
	});
}

function viewSalesInvoice(contact) {
	$("#countInvoices").empty();
	var rowData = [];
	gridOptionsInvoice.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-invoices-through-ajax?id=' + contact
	}).then(function(data) {
		if (data.code == "Success") {
			if (data.body.length > 0) {
				var resp = JSON.parse(data.body);
				var len = resp.length;
				$("#countInvoices").append(len);
				gridOptionsInvoice.api.setRowData(resp);
				$("#invoiceGrid").show();
				$("#nodataInvMessage").hide();
				$("#addInvoice").show();
			} else {
				$("#invoiceGrid").hide();
				$("#nodataInvMessage").show();
				$("#addInvoice").hide();
			}
		}
	});
}

function viewSalesOrder(contact) {
	$("#countSalesorder").empty();
	var rowData = [];
	gridOptionsSalesOrder.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-sales-order-through-ajax?id=' + contact
	}).then(function(data) {
		if (data.code == "Success") {
			if (data.body.length > 0) {
				var resp = JSON.parse(data.body);
				var len = resp.length;
				$("#countSalesorder").append(len);
				gridOptionsSalesOrder.api.setRowData(resp);
				$("#salesOrderGrid").show();
				$("#nodataSalesMessage").hide();
				$("#addSalesOrder").show();
			} else {
				$("#salesOrderGrid").hide();
				$("#nodataSalesMessage").show();
				$("#addSalesOrder").hide();
			}
		}
	});
}


function getCampaign(contact) {
	$("#countCampaign").empty();
	var rowData = [];
	gridOptionsCampaigns.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "view-crm-contacts-view-detail-campaign?id=" + contact,

	}).then(function(data) {
		console.log('---daata----------------------', data.body)
		var len = data.body.length
		$("#countCampaign").append(len);

		gridOptionsCampaigns.api.setRowData(data.body);

	});
}
function editTask(taskId) {
	localStorage.setItem('activityTaskId', taskId);
}

function getTask(contact) {
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-task?id=" + contact,
		success: function(response) {

			if (response.message == "Success") {
				$("#taskActivity").empty(); // Clear the existing content
				trCount = '';
				var countItm = response.body.length;
				console.log("response for task-------" + JSON.stringify(response));
				//return false;
				var countOpen = 0, countClose = 0;
				for (var i = 0; i < response.body.length; i++) {

					var taskStatus = response.body[i].taskStatus;
					taskId = response.body[i].taskId;
					let redirectUrl = "";
					if (ownerId != $("#userId").val()) {
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
				getCount(contact);
				if (countItm < 1) {
					var mailRow = '<li>'
						+ '<div >No Record Found</div>'

						+ '</li>'
					trCount = mailRow;
					$("#taskActivity").append(trCount);
				}
				//console.log('response for leadId------'+JSON.stringify(response));

			}
		}
	});
}


// Function to handle "See More" button click
$(document).ready(function() {

	$("#myContainer").hide();
	$('#seeMoreButton').on("click", function() {
		$('#scrollableSection').toggleClass('show-scrollable-section');
		$('#seeMoreButton').text($('#scrollableSection').hasClass('show-scrollable-section') ? 'See Less' : 'See More..');
	});
});

function editMeetingTitle(meetingId) {
	localStorage.setItem('meetingId', meetingId);

}
//getMeeting

/*function getInvitedMeetings(contact) {

	$("#countInvitedMeet").empty();
	agGrid.simpleHttpRequest({
		url: 'view-crm-contact-invitedmeetings-detail?id=' + contact,

	}).then(function(data) {
		console.log('---daata----------------------', data.body)
		var len = data.body.length
		$("#countInvitedMeet").append(len);

		gridOptionsInvitedMeetings.api.setRowData(data.body);

	});
}*/
function getMeeting(contact) {
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-meeting?id=" + contact,
		success: function(response) {
			if (response.message == "Success") {
				$("#meetingActivity").empty();
				$("#closedMeetingActivity").empty();

				trCount = '';
				var countItm = response.body.length;
				var countOpenM = 0, countCloseM = 0;
				for (var i = 0; i < response.body.length; i++) {
					var meetingStatus = response.body[i].meetingStatus;
					var meetingId = response.body[i].meetingId;
					let redirectUrl = "";
					if (ownerId != $("#userId").text()) {
						redirectUrl = '<span href="javascript:void(0)" class="toptxt">' + response.body[i].meetingTitle + '</span>';
					} else {
						redirectUrl = '<a href="javascript:void(0)" class="toptxt" onclick="editMeetingTitle(\'' + meetingId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("meetingFun") + '\',\'' + sessionStorage.getItem("meetingAct") + '\')">' + response.body[i].meetingTitle + '</a>';
					}

					if (meetingStatus != "Completed") {
						var mailRow = '<li>'
							+ redirectUrl
							+ '<div class="smalltxt">Meeting Status : ' + response.body[i].meetingStatus + '</div>'
							+ '<p>Meeting From : ' + response.body[i].meetingFromDate + ' - <span> ' + response.body[i].meetingFromTime + '</span></p>'
							+ '<p>Meeting To : ' + response.body[i].meetingToDate + ' - <span> ' + response.body[i].meetingToTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].meetingHost + '</div>'
							+ '</li>';

						$("#meetingActivity").append(mailRow);
						countOpenM++;
					} else {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].meetingTitle + '</div>'
							+ '<div class="smalltxt">Meeting Status : ' + response.body[i].meetingStatus + '</div>'
							+ '<p>Meeting From : ' + response.body[i].meetingFromDate + ' - <span> ' + response.body[i].meetingFromTime + '</span></p>'
							+ '<p>Meeting To : ' + response.body[i].meetingToDate + ' - <span> ' + response.body[i].meetingToTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].meetingHost + '</div>'
							+ '</li>';

						$("#closedMeetingActivity").append(mailRow);
						countCloseM++;
					}
				}

				$("#openMeetings").text(countOpenM);
				localStorage.setItem("countOpenM", countOpenM);
				$("#closeMeetings").text(countCloseM);
				localStorage.setItem("countCloseM", countCloseM);
				getCount(contact);

				if (countItm < 1) {
					var mailRow = '<li><div>No Record Found</div></li>';
					$("#meetingActivity").append(mailRow);
				}
			}
		}
	});
}

//getCall

function addCallInfo() {

	var executiveSelect = $("#callOwner");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';

	if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	}

	var id = $("#callToWhom").val();
	var obj = {};
	obj.ownerName = $('#contactOwner2').text();
	obj.pageType = "Contact";
	obj.callContactId = $("#pipelineId").text();
	obj.callId = $('#callId').text();
	obj.callToWhom = $('#callToWhom').val();
	obj.leadName = $('#lName').val();
	obj.leadId = $('#leadId').val();
	//obj.callleadId = $('#leadId').val();
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
	obj.accountId = $('#accountId').val();
	obj.participantId = JSON.stringify(participantData);
	obj.toMail = toMail;
	obj.ccMail = ccMeetingMail;
	obj.excutiveMail = executiveMail;
	console.log(obj);//return false;

	console.log('response for leadId------' + JSON.stringify(obj));


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
	if (obj.callStartDate11 == null || obj.callStartDate11 == "") {
		validation = validationUpdated("Call Date Required",
			"callStartDate11");
	}
	if (obj.callStartTime11 == null || obj.callStartTime11 == "") {
		validation = validationUpdated("Call Time Required",
			"callStartTime11");
	}

	/* FORM VALIDATION ENDS*/
	var contact = $("#pipelineId").text();
	if (validation) {
		closeModelCall();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-detail-add-call-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.message == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					getActivity(contact, "allTimeline");
					getTimeline(contact, "allTimeline");
					getCall(contact);
					mailSucessAlert();

					$("#messageParagraph").text("Call Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');

				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

}

function getNameList1() {

	var relatedType = $("#relatedType").val();
	var searchVal = $("#relatedName").val();
	if (searchVal == "") {
		$("#suggesstion-box-relatedTO_").hide();
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
						$("#suggesstion-box-relatedTO_").show();
						$("#suggesstion-box-relatedTO_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteValue0()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-box-relatedTO_").show();
						$("#suggesstion-box-relatedTO_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
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
		$("#suggesstion-box-relatedTO_").hide();

	} else {
		$("#relatedId").val("");
		$("#relatedName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box-relatedTO_").hide();

	}
}
function selectAutocompleteValue0() {

	$("#relatedId").val("");

	$("#personName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box-relatedTO_").hide();

}



function getNameList() {

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
				console.log(data);
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
				console.log(data);
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
		$("#leadId").val(LeadId);

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

/*function getCall(contact) {
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-call?id=" + contact,
		success: function(response) {

			if (response.message == "Success") {
				$("#callActivity").empty();
				trCount = '';
				var countItm = response.body.length;
				var countOpenC = 0, countCloseC = 0;
				for (var i = 0; i < response.body.length; i++) {
					var callStatus = response.body[i].callStatus;
					callId = response.body[i].callId;
					let redirectUrl = "";

					if (ownerId != $("#userId").text()) {
						redirectUrl = '<span href="javascript:void(0)" class="toptxt" >' + response.body[i].callSubject + '</span>'
					} else {
						redirectUrl = '<a href="javascript:void(0)" class="toptxt" onclick="editCalls(\'' + callId + '\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("callFun") + '\',\'' + sessionStorage.getItem("callAct") + '\')">' + response.body[i].callSubject + '</a>'


					}
					if (callStatus != "Completed") {
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
							+ '<div class="smalltxt">Call Related To : ' + response.body[i].callToWhom + '</div>'
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
				getCount(contact);
				if (countItm < 1) {
					var mailRow = '<li>'
						+ '<div >No Record Found</div>'

						+ '</li>'
					trCount = mailRow;
					$("#callActivity").append(trCount);
				}
				//console.log('response for leadId------'+JSON.stringify(response));

			}
		}
	});
}*/
function getTimeline(contact, type) {
	var promise3 = new Promise(function(resolve, reject) {

		$.ajax({
			type: "GET",
			url: "view-crm-contacts-view-detail-activity?id=" + contact + "&type=" + type,
			async: true,
			success: resolve,
			error: reject,
		});
	});
	promise3.then(function(response) {
		if (response.message === "Success") {
			$("#updatedDates").empty();
			var activityName = response.body[0].activityName;
			var createdTime = response.body[0].createdTime;
			var createdOn = response.body[0].createdOn;
			$("#updatedDates").append(createdTime);

		} else {
			console.log("Promise was rejected or the response was not successful.");
		}
	});
}




function getActivity(contact, type) {
	var contact = $("#pipelineId").text();
	//alert('hello activity----------'+contact);return false;
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-activity?id=" + contact + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				getActivityTimeline(response.body);
				getTimeline(contact, "allTimeline");

			}
		}
	});
}


function getProductActivity(contact, type) {

	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-activity?id=" + contact + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				$("#products-timeline-section").empty();
				var countItm = response.body.length;

				getActivityTimelineProducts(response.body);


			}
		}
	});
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

	$("#demo").show();
	//$("#runMacro").attr("disabled", true);
	//$("#sentMail").attr("disabled", true);
	//$("#createTask").attr("disabled", true);
	//$("#tags").attr("disabled", true)

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
	$('#city').val("");
	$('#states').val("");
	$('#zip').val("");
	$('#country').val("");
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

function assignDeal() {
	var contactId = $("#contactId").text();
	var accountId = $("#accountId").text();
	var accountName = $("#accountName").text();
	var firstName = $("#firstName").text();

	var lastName = $("#lastName").text();

	var contactName = firstName + " " + lastName;


	localStorage.setItem('contactId', contactId);
	localStorage.setItem('accountId', accountId);
	localStorage.setItem('accountName', accountName);
	localStorage.setItem('contactName', contactName);
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
				console.log('response------' + JSON.stringify(response));
				$('#leadId').val(response.body[0].leadId);
				$('#leadOwner').val(response.body[0].leadOwner);
				$('#company').val(response.body[0].company);
				$('#firstName').val(response.body[0].firstName);
				$('#lastName').val(response.body[0].lastName);
				$('#title').val(response.body[0].title);
				$('#email').val(response.body[0].email);
				$('#phone').val(response.body[0].phone);
				$('#fax').val(response.body[0].fax);
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
					console.log('emailOpt-----------' + response.body[0].emailOpt);
					$('#emailOpt').prop('checked', true);
				} else {
					$('#emailOpt').prop('checked', false);
				}


				$('#twitter').val(response.body[0].twitter);
				$('#country').val(response.body[0].country);
				getStateDataOnEdit(response.body[0].states);
				//$('#states').val(response.body[0].states);
				$('#city').val(response.body[0].city);
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
		//$("#dname").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-crm-leads-stateList?id=" + cname,
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


	//obj.leadId = $('#contactOwner').text();
	obj.leadId = $('#leadId').text();
	obj.pageType = "Contact";
	obj.taskContactId = $("#pipelineId").text();
	obj.taskId = $('#taskId').text();
	obj.taskOwner = userId;
	obj.ownerName = $('#contactOwner2').text();
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

	obj.description = $('#description').val();

	console.log("Object mapping for Campaign--------" + JSON.stringify(obj));
	var validation = true;
	//return false;


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
	var contact = $("#pipelineId").text();
	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-task-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.message == "Success") {

					getAction(contact);
					getActivity(contact, "allTimeline");
					getTimeline(contact, "allTimeline");
					getTask(contact);
					closeModelTask();


					$("#messageParagraph").text("Task Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('.loader').hide();
				}

				$("body").removeClass("overlay");
			},
			error: function(data) {

				console.log(data);
			}
		})
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
						console.log("content " + content)
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
				console.log(data);
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

function getAccountList1() {

	var search = $("#taskAccount").val();

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
						var content = '<ul id="autocomplete-list2" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
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
						console.log("content " + content)
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list2">';
						content += '<li onClick="selectAutocompleteValue1()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	}

}

function selectAutocompleteValue2(accountId, accountName, custGSTNo, taxType) {

	if (accountId) {


		$("#accountId").val(accountId);

		$("#taskAccount").val(accountName);

		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box2_").hide();
		//	hideShowS();
		//checkForDuplicate(key,counter);

	} else {

		$("#accountId").val("");

		$("#taskAccount").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box2_").hide();

	}
}

function getAccountList2() {

	var search = $("#taskAccount1").val();

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
						var content = '<ul id="autocomplete-list3" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue3(\''
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
						console.log("content " + content)
						$("#suggesstion-box3_").show();
						$("#suggesstion-box3_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list3">';
						content += '<li onClick="selectAutocompleteValue2()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box3_").show();
						$("#suggesstion-box3_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	}

}

function selectAutocompleteValue3(accountId, accountName, custGSTNo, taxType) {

	if (accountId) {


		$("#accountId").val(accountId);

		$("#taskAccount1").val(accountName);

		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box3_").hide();
		//	hideShowS();
		//checkForDuplicate(key,counter);

	} else {

		$("#accountId").val("");

		$("#taskAccount1").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3_").hide();

	}
}


function getAccountList4() {

	var search = $("#taskAccount2").val();

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
						var content = '<ul id="autocomplete-list4" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue4(\''
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
						console.log("content " + content)
						$("#suggesstion-box4_").show();
						$("#suggesstion-box4_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list4">';
						content += '<li onClick="selectAutocompleteValue4()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box4_").show();
						$("#suggesstion-box4_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	}

}

function selectAutocompleteValue4(accountId, accountName, custGSTNo, taxType) {

	if (accountId) {


		$("#accountId").val(accountId);

		$("#taskAccount2").val(accountName);

		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box4_").hide();
		//	hideShowS();
		//checkForDuplicate(key,counter);

	} else {

		$("#accountId").val("");

		$("#taskAccount2").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3_").hide();

	}
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
				console.log(data);
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

function addMeetingInfo() {

	var executiveSelect = $("#meetingHost");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';

	if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	}

	$("body").removeClass("overlay");
	var type = $('#relatedMeetingTo').val();
	var obj = {};
	obj.meetingContactId = $("#pipelineId").text();
	obj.ownerName = $('#contactOwner2').text();
	obj.meetingType = "Contact";
	obj.meetingId = $('#meetingId').text();
	obj.meetingTitle = $('#meetingTitle').val();
	obj.meetingLocation = $('#meetingLocation').val();
	obj.isThisOnlineMeeting = $('#isThisOnlineMeeting').val();
	obj.isAllDay = $('#isAllDay').val();
	obj.meetingFromDate = $('#meetingFromDate').val();
	obj.meetingFromTime = $('#meetingFromTime').val();
	obj.meetingToDate = $('#meetingToDate').val();
	obj.meetingToTime = $('#meetingToTime').val();
	obj.meetingHost = $('#meetingHost').val();
	obj.leadName = $('#meetingleadName').val();
	obj.meetingLeadId = $('#leadId').val();
	obj.contactName = $('#meetingContact').val();
	obj.contactId = $('#contactId').val();
	obj.relatedMeetingTo = $('#relatedMeetingTo').val();
	obj.meetingStatus = $('#meetingStatus').val();
	obj.isRepeat = $('#isRepeat').val();
	obj.isAllDayRepeat = $('#isAllDayRepeat').val();
	obj.meetingRepeatFromDate = $('#meetingRepeatFromDate').val();
	obj.meetingRepeatFromTime = $('#meetingRepeatFromTime').val();
	obj.meetingRepeatToDate = $('#meetingRepeatToDate').val();
	obj.meetingCalendarRepeatToTime = $('#meetingCalendarRepeatToTime').val();
	obj.repeatType = $('#repeatType').val();
	obj.description = $('#description').val();
	obj.accountId = $('#accountId').val();
	obj.participantId = JSON.stringify(participantData);
	obj.toMail = toMail;
	obj.ccMail = ccMeetingMail;
	obj.excutiveMail = executiveMail;
	console.log(obj);
	console.log("Object meeting for Contact--------" + JSON.stringify(obj));


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

	var contact = $("#pipelineId").text();
	if (validation) {
		closeModelMeeting();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-meeting-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.code == "Success") {
					getActivity(contact,"allTimeline");
					getTimeline(contact,"allTimeline");
					getMeeting(contact);
					mailSucessAlert();


					$("#messageParagraph").text("Meeting Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}

				$('.loader').hide();
				$("body").removeClass("overlay");

			},
			error: function(data) {

				console.log(data);
			}
		})
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
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueParticipants(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxpart_").show();
						$("#suggesstion-boxpart_").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteParticipantsValue()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxpart_").show();
						$("#suggesstion-boxpart_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	}

}


function selectAutocompleteValueParticipants(name, participantId) {

	if (name) {
		//$("#personName").val("");
		$("#participantId").val(participantId);
		$("#meetingParticipants").val(name);
		$("#search").val(participantId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxpart_").hide();

	} else {
		$("#participantId").val("");

		$("#meetingParticipants").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxpart_").hide();

	}
}
function selectAutocompleteParticipantsValue() {

	$("#participantId").val("");

	$("#meetingParticipants").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxpart_").hide();

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

	if (relatedMeetingTo == 'Lead') {
		var searchVal = leadName;
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-autosearchLead",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteMeetingLeadValue(\''
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
						content += '<div onClick="selectAutocompleteMeetingLead()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxmeetingLead_").show();
						$("#suggesstion-boxmeetingLead_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
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
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueMeetingContact(\''
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
						content += '<div onClick="selectAutocompleteContactMeetingValue()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxmeetingContact_").show();
						$("#suggesstion-boxmeetingContact_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})



	}


}

function selectAutocompleteMeetingLeadValue(name, LeadId) {

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
function selectAutocompleteMeetingLead() {

	$("#leadId").val("");

	$("#meetingleadName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxmeetingLead_").hide();

}


function selectAutocompleteValueMeetingContact(name, ContactId) {
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
function selectAutocompleteContactMeetingValue() {

	$("#contactId").val("");

	$("#meetingcontactName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxmeetingContact_").hide();

}




function addCampaignInfo() {
	var obj = {};
	obj.campaignId = $('#campaignId').text();
	obj.campaignContactId = $("#pipelineId").text();
	obj.leadId = $('#leadId').val();
	obj.campaignOwner = $('#campaignOwner').val();
	obj.ownerName = $('#contactOwner2').text();
	obj.pageType = "Contact";
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
	obj.description = $('#description11').val();
	obj.accountId = $('#accountId').val();

	console.log("Object mapping for Campaign--------" + JSON.stringify(obj));
	/* FORM VALIDATION STARTS*/


	var validation = true;

	var contact = $("#contactId").html()
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

	/* FORM VALIDATION ENDS*/
	var contact = $("#pipelineId").text();
	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-detail-add-campaign-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.message == "Success") {

					closeModelCampaign();
					getCampaign(contact);
					getActivity(contact, "allTimeline");
					getTimeline(contact, "allTimeline");

					$('.loader').hide();
					$("#messageParagraph").text("Campaign Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {

				console.log(data);
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
	obj.country = $('#country').val();
	obj.states = $('#states').val();
	obj.city = $('#city').val();
	obj.addressStreet = $('#addressStreet').val();
	obj.zip = $('#zip').val();
	obj.description = $('#description').val();
	obj.createdBy = $('#createdBy').val();

	console.log(obj);

	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.leadOwner == null || obj.leadOwner == "") {
		validation = validationUpdated("Lead Owner Required",
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


	if (obj.noOfEmp == null || obj.noOfEmp == "") {
		validation = validationUpdated("Number Of Emp Required",
			"noOfEmp");
	}

	if (obj.country == null || obj.country == "") {
		validation = validationUpdated("Country Required",
			"country");
	}

	if (obj.states == null || obj.states == "") {
		validation = validationUpdated("State Required",
			"states");
	}


	if (obj.city == null || obj.city == "") {
		validation = validationUpdated("City Required",
			"city");
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
					console.log(response);
					location.reload();
				}
			},
			error: function(data) {

				//console.log(data);
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

	var gridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(gridDiv, gridOptionsLead);

	var gridDiv = document.querySelector('#myGridCampaigns');
	new agGrid.Grid(gridDiv, gridOptionsCampaigns);

	var gridDiv = document.querySelector('#myGridDeals');
	new agGrid.Grid(gridDiv, gridOptionsDeals);

	var gridDiv = document.querySelector('#myGridMeetings');
	new agGrid.Grid(gridDiv, gridOptionsInvitedMeetings);

	var gridDiv = document.querySelector('#quotesGrid');
	new agGrid.Grid(gridDiv, gridOptionsQuotes);

	var gridDiv = document.querySelector('#poGrid');
	new agGrid.Grid(gridDiv, gridOptionsPo);

	var gridDiv = document.querySelector('#invoiceGrid');
	new agGrid.Grid(gridDiv, gridOptionsInvoice);

	var gridDiv = document.querySelector('#salesOrderGrid');
	new agGrid.Grid(gridDiv, gridOptionsSalesOrder);


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
		$('.loader').show();
		//alert("Your Address -- "+$("#punchInLocation").val());
		$("body").removeClass("overlay");
		var item = {};
		var empId = $("#pipelineId").text();
		var titleId = $("#titleId").val();
		var noteId = $("#noteId").val();
		let folder = [];
		if (empId && titleId) {

			var imageValid = true;
			var uploadList = [];
			let x = [];
			let fileName = '';
			//alert(document.getElementById("fileUploader").files.length)
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
							console.log(folder, '@@@@@@@@')
						};
						console.log(folder, '@@@@@@@@')
					}

				};
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

				item.contactId = empId;
				item.employeeId = $('#contactOwner2').text();
				item.leadNoteId = $('#leadNoteId').val();
				item.titleId = titleId;
				item.noteId = noteId;
				item.documentList = folder;
				item.accountId = $('#accountId').text();
				item.latitude = latitude;
				item.longitude = longitude;
				//item.address = address; 

				console.log("employee document---------" + JSON.stringify(item));
				saveContactNoteDoc(JSON.stringify(item));

			}, 2000)

		} else {
			$("#messageParagraph").text("Note Title Required!");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$('.loader').hide();
		}
	} else {
		$("#messageParagraph").text(LocationMessage);
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}

}

function saveContactNoteDoc(item) {
	var contact = $("#pipelineId").text();
	console.log(item); //return false;
	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-notes-ajax",
		dataType: "json",
		contentType: "application/json",
		data: item,
		success: function(response) {
			getNote(contact, "1", "", "");
			getActivity(contact, "allTimeline");
			
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
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})  //ajax ends
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

////////////////////////////////////////////////////////////////////

let bccMailData = '';
function saveContactEmails() {

	bccMailData = '';
	var ececutiveMail = $("#userMail").val().trim();
	if (ececutiveMail !== "") {
		bccMailData += ececutiveMail;
	}

	bccMailListData.add(bccMailData);
	var commaSeparatedEmails = Array.from(ccMailListData).join(',');
	var bccMailList = Array.from(bccMailListData).join(',');
	var item = {};
	$('.loader').show();
	var empId = $("#pipelineId").text();
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName = $("#docName").val();
	var ccMail = commaSeparatedEmails;
	var bccMail = bccMailList;


	if (empId) {

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

			item.emailContactId = empId;
			item.employeeId = $('#contactOwner1').text();
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			item.draftId = $('#draftId').val();
			item.accountId = $('#accountId').text();
			item.ccMail = ccMail;
			item.bccMail = bccMail;
			console.log("employee document---------" + JSON.stringify(item));
			saveContactEmailDtls(item);

		}, 1000)

	} else {
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}

function saveContactEmailDtls(item) {
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
				var contact = $("#pipelineId").text();
				getActivity(contact, "allTimeline");
				mailSucessAlert();
				$("#toMail").val("");
				$("#mailSubject").val("");
				$("#comment").val("");
				$("#docName").val("");
				$("#uploadList").val("");
				$('.loader').hide();

				$.ajax({
					type: "GET",
					url: "view-crm-leads-delete-draft?id=" + selectedDraftId,
					success: function(response) {

						if (response.message == "Success") {

						}
					}
				});
				var contactId = $("#contactId").text();
				getMail(contactId);
				var contact = $("#pipelineId").text();
				getDraft(contact);
				/*$("#messageParagraph").text("Mail Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/

			} else {

				//	$('.loader').hide();
				//	$("body").removeClass("overlay");
			}
		},
		error: function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	}) //ajax ends 
}
function saveContactDrafts() {
	bccMailData = '';
	var ececutiveMail = $("#userMail").val().trim();
	if (ececutiveMail !== "") {
		bccMailData += ececutiveMail;
	}

	bccMailListData.add(bccMailData);
	var commaSeparatedEmails = Array.from(ccMailListData).join(',');
	var bccMailList = Array.from(bccMailListData).join(',');

	var item = {};
	var empId = $("#pipelineId").text();
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName = $("#docName").val();
	var ccMail = commaSeparatedEmails;
	var bccMail = bccMailList;


	if (empId) {

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
			item.draftId = (selectedDraftId != null && selectedDraftId != "") ? selectedDraftId : null;
			item.emailContactId = empId;
			item.employeeId = $('#contactOwner2').text();
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			item.ccMail = ccMail;
			item.bccMail = bccMail;
			saveContactDraftDtls(item);

		}, 100)

	} else {
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}
function saveContactDraftDtls(item) {
	var validation = true;


	if (item.mailSubject == null || item.mailSubject == "") {
		validation = validationUpdated("Subject Required", "mailSubject");
	}
	if (validation) {
		closeModelEmail();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add-drafts",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.message == "success") {
					$('.loader').hide();
					var contact = $("#pipelineId").text();
					getActivity(contact, "allTimeline");
					getDraft(contact);
					$("#messageParagraph").text("Draft Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');

				} else {

					//$('.loader').hide();
					//$("body").removeClass("overlay");
				}
			},
			error: function(data) {
				console.log(data);
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	}
}
function AddCampaignModal(index) {


	var accountId = $("#accountId").text();
	var accountName = $("#accountName").text();
	$('#taskAccount2').val(accountName);


	userId = $("#userId").text();
	// alert(userId);
	$('#campaignOwner').val(userId);
	$('#myModalAddCampaign').modal('show');



	//$('#campaignOwner').val("");
	$('#campaignType').val("");
	$('#campaignName').val("");
	$('#campaignStatus').val("");
	$('#startDate').val("");
	$('#endDate').val("");
	$('#expectedRevenue').val("");
	$('#budgetedCost').val("");
	$('#actualCost').val("");
	$('#expectedResponse').val("");
	$('#numberSent').val("");
	$('#description').text("");
}

function openCampaignDetails(campaignId) {
	localStorage.setItem('campaignId', campaignId);
}

function openContactDeals(dealId) {

	localStorage.setItem('dealId', dealId);
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
	$("#bcc-mail-container").html("");
	///CKEDITOR.instances['commentck'].setData("");
	$('#uploadDoc_1').val('');
	$('#imageName_1').text('');
	$('#imageName_1').val('');
	$('#uploadedBillDiv_1').html('');
}

function toggleCC() {
	document.getElementById("ccField").style.display = "block";
	document.getElementById("bccField").style.display = "block";
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
	removeErrorMsg();
	ccMailListData.clear();
	bccMailListData.clear();

	CKEDITOR.instances['commentck'].setData("");
}

function closeViewModelEmail(index) {
	$('#myModalViewEmailContact').modal('hide');
}

function closeViewModelAddProduct(index) {
	$('#addProductModal').modal('hide');
}


function closeModelCampaign(index) {
	$('#myModalAddCampaign').modal('hide');
}
function closeModelTask(index) {
	$('#myModalAddTask').modal('hide');
	document.getElementById('activities').value="select";
}

function closeModelMeeting(index) {
	$('#myModalAddMeeting').modal('hide');
	document.getElementById('activities').value="select";
}

function closeModelCall(index) {
	$('#myModalAddCall').modal('hide');
	document.getElementById('activities').value="select";
}

function getProductSearch() {

	var searchVal = $("#searchProduct").val();

	var rowCount = $('#countAssignProduct').val();
	var contactId = $("#pipelineId").text();
	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}

	//alert('searchVal---------'+searchVal);
	if (searchVal == "") {
		$("#suggesstion-boxproduct_").hide();
	}

	var pageType = "Contact";
	if (searchVal) {

		$.ajax({
			type: "GET",
			url: "view-crm-leads-autosearchProduct?searchVal=" + searchVal + "&id=" + contactId + "&assigRow=" + assigRow + "&pageType=" + pageType,

			success: function(response) {
				if (response.message == "Success") {
					console.log("product list for searchProductSearch----------" + JSON.stringify(response));
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
				console.log(data);
			}
		});

	}
}



function selectAutocompleteValueProduct(productName, productCode) {
	$("#suggesstion-boxproduct_").hide();
	var rowCount = $('#countAssignProduct').val();

	var contactId = $("#pipelineId").text();
	var leadName = $("#leadName").text();

	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}
	var pageType = "Contact";
	//alert(rowCount);	
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-product-view?id=" + contactId + "&id2=" + assigRow + "&pageType=" + pageType + "&productCode=" + productCode,
		success: function(response) {
			if (response.message == "Success") {
				console.log("console response for product---------------------------------", response.body.length)
				//return false;

				$('#leadNameProduct').html(leadName);

				var countItm = response.body.length;
				//alert('hello');
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
			console.log(data);
		}
	});
}

var checkedIDs = [];
var checkedVal = [];
function clickProductCheck(id) {
	checkedVal.push(id);
	checkedIDs = $("#productTblBody input:checkbox:checked").map(function() {
		return $(this).val();
	}).get();
	var checkedLength = checkedIDs.length;

	console.log("checkedVal---------" + checkedVal);

}

function addProductToContact() {
	var dataset = [];

	productCheckedId = "";
	for (var i = 0; i < checkedIDs.length; ++i) {
		productCheckedId = productCheckedId + '"' + checkedVal[i] + '",';
	}
	productCheckedId = productCheckedId.substring(0, productCheckedId.length - 1);
	console.log("productCheckedId--------------" + productCheckedId);

	for (let i = 0; i < checkedIDs.length; ++i) {
		item = {};
		item['productId'] = checkedVal[i];
		item['contactId'] = $("#pipelineId").text();
		item['pageType'] = "Contact";
		dataset.push(item);
	}

	console.log('dataset for product add------' + JSON.stringify(dataset));
	//return false;
	productAssign(dataset);
}


function productAssign(dataset) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-product?id=" + productCheckedId,
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text(
					"Data Saved Successfully");
				closeViewModelAddProduct();
				location.reload();

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

				$('#myModalAddTags').modal('hide');
			}
		},
		error: function(datas) {
		}
	})

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

function scrollDeals() {
	$('html, body').animate({
		scrollTop: $("#deals").offset().top - 250
	}, 500);
}


function scrollContacts() {
	$('html, body').animate({
		scrollTop: $("#contacts").offset().top - 250
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


function scrollQuotes() {
	$('html, body').animate({
		scrollTop: $("#quotes").offset().top - 250
	}, 500);
}

function scrollSalesOrder() {
	$('html, body').animate({
		scrollTop: $("#salesorder").offset().top - 250
	}, 500);
}


function scrollInvoices() {
	$('html, body').animate({
		scrollTop: $("#invoices").offset().top - 250
	}, 500);
}


function scrollMemberAccount() {
	$('html, body').animate({
		scrollTop: $("#memberaccount").offset().top - 250
	}, 500);
}


function scrollCases() {
	$('html, body').animate({
		scrollTop: $("#cases").offset().top - 250
	}, 500);
}

function scrollPurchaseOrder() {
	$('html, body').animate({
		scrollTop: $("#purchasesorder").offset().top - 250
	}, 500);
}

function scrollInvitesMeetings() {
	$('html, body').animate({
		scrollTop: $("#meetings").offset().top - 250
	}, 500);
}


$(document).ready(function() {

	$('.numberonly').keypress(function(e) {

		var charCode = (e.which) ? e.which : event.keyCode

		if (String.fromCharCode(charCode).match(/[^0-9]/g))

			return false;

	});

	$("#toMail").blur(function() {
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddress = $("#toMail").val();
		if (!emailReg.test(emailaddress)) {
			alert("Not a valid email!");
		}

		else {

		}
	});


});


$(document).ready(function() {
	$(document).on('click', "button", function(e) {
		$(this).closest('tr').remove();
	});
	var mailgridDiv = document.querySelector('#myGridMail');
	new agGrid.Grid(mailgridDiv, mailgridOptions);

	var draftgridDiv = document.querySelector('#myGridDraft');
	new agGrid.Grid(draftgridDiv, draftgridOptions);

	var rowData = [];
	mailgridOptions.api.setRowData(rowData);
	var rowData = [];
	draftgridOptions.api.setRowData(rowData);
});
var filelist = new Array();

function updateList() {
	var input = document.getElementById('fileUploader');
	var HTML = "<table>";
	console.log(input.files.item, "......... Input file data.")
	for (var i = 0; i < input.files.length; ++i) {
		//filelist[i] = input.files.item(i).name;
		filelist.push(input.files.item(i).name);
		HTML += "<tr><td>"
			+ filelist[i]
			+ "</td><td><button class='trash-btn'><i class='fa fa-trash'></i></button></td></tr>";
	}
	HTML += "</table>";
	$("#divFiles").append(HTML);
	/* output.innerHTML = HTML; */
	console.log(filelist);
}

var columnMailDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,


}, {
	headerName: "Subject",
	field: "mailSubject",
	cellStyle: {
		textAlign: 'left'
	},


}, {
	headerName: "Sent To",
	field: "toMail",
	cellStyle: {
		textAlign: 'left'
	},


}, {
	headerName: "Sent Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'left'
	},
}, {

	headerName: "CC",
	field: "ccMail",
	cellStyle: {
		textAlign: 'left'

	},
}, {
	headerName: "BCC",
	field: "bccMail",
	cellStyle: {
		textAlign: 'left'
	}
}
];

var mailgridOptions = {
	columnDefs: columnMailDefs,
	/* rowSelection : 'multiple',
*/
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


},
{
	headerName: "Draft Id",
	field: "draftId",
	hide: true,
	cellRenderer: function(params) {
		console.log(params, 'draftttt')
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

		if (ownerId != $("#userId").text()) {
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
	cellStyle: {
		textAlign: 'left'
	},

}, {
	headerName: "Sent Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'left'
	},
}, {
	headerName: "CC",
	field: "ccMail",
	cellStyle: {
		textAlign: 'left'

	},
}, {
	headerName: "BCC",
	field: "bccMail",
	cellStyle: {
		textAlign: 'left'
	}
},
{
	headerName: "Action",
	field: "draftId",
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		console.log(params, 'draftttt')
		return '<a onclick=deleteDraft("'
			+ params.data.draftId
			+ '") href="javascript:void(0)">'
			+ '<i class="fa fa-trash fa-icon img-hover" aria-hidden="true"></i> </a>';
	},
}
];

var draftgridOptions = {
	columnDefs: columnDraftDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10
	},
};
var draftid = '';
function deleteDraft(id) {
	$("#deleteDraftModal").show();
	draftid = id;
}
function cancelDeleteDraftModalBtn() {
	$("#deleteDraftModal").hide();
}

function deleteDraftOnclick() {

	$.ajax({
		type: "GET",
		url: "view-crm-leads-delete-draft?id=" + draftid,
		success: function(response) {
			if (response.message == "Success") {
				$("#deleteDraftModal").hide();
				var contactId = $("#contactId").text();
				var contact = $("#contactId").text();
				getMail(contactId);
				getDraft(contact);
			}
		}
	});

}
let selectedDraftId = '';
function editDraft(id) {
	selectedDraftId = id;
	var contactId = $("#contactId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-edit-draft?id1=" + contactId + "&id2=" + id,
		async: false,
		success: function(response) {

			if (response.message == "success") {
				console.log("edit", response.body)
				//$("#id").val(response.body.id);
				//$("#leadId").val(response.body.leadId);
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
			console.log(data)
		}
	});
}
function cacelContactView() {
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-contacts"
}

function editContactInfo() {

	var contactId = $("#contactId").text();

	localStorage.setItem('contactId', contactId);


}


function backToPage(event) {
	event.preventDefault();
	$('.loader').show();
	if (ownerId == $("#userId").text()) {
		$('.loader').show();
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest + "view-crm-leads"
	} else if (ownerId != $("#userId").text()) {
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest + "admin-executive-report"
	}
	$('.loader').hide();
}

function getCount(contact) {



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

$(document).ready(function() {
	// Event listener for the select element
	$("#relatedMeetingTo1").on("change", function() {
		checkMeetingDetails1();
	});
});

function checkMeetingDetails1() {
	var id = $("#relatedMeetingTo1").val();
	if (id == "Lead1") {
		$(".leadNameCls1").show();
		$(".contactNameCls1").hide();
		$("#meetingcontactName1").val(""); // Clear contact name input
		$("#contactId").val(""); // Clear contact ID input
		$("#relatedType").prop('disabled', true);
		$("#relatedName").prop('disabled', true);
	} else if (id == "Contact1") {
		$(".contactNameCls1").show();
		$(".leadNameCls1").hide();
		$("#meetingleadName1").val(""); // Clear lead name input
		$("#leadId").val(""); // Clear lead ID input
		$("#relatedType").prop('disabled', false);
		$("#relatedName").prop('disabled', false);
	} else {
		// Handle other cases if needed
		$(".leadNameCls1").hide();
		$(".contactNameCls1").hide();
		$("#meetingleadName1").val("");
		$("#leadId").val("");
		$("#meetingcontactName1").val("");
		$("#contactId").val("");
		$("#relatedType").prop('disabled', false);
		$("#relatedName").prop('disabled', false);
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
		return '<a >' + params.data.productId + '</a>';
	}
},
{
	headerName: "Product Name",
	field: "productName",
	width: 200,
	cellStyle: { textAlign: 'left' }
	//pinned: 'left',
},
{
	headerName: "Brand",
	field: "brand",
	cellStyle: { textAlign: 'left' }

},
{

	headerName: "Category",
	field: "productCategoryText",
	width: 250,
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Mode",
	field: "mode",
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Status",
	field: "productStatus",
	cellStyle: { textAlign: 'left' }
},
{

	headerName: "Created By",
	field: "createdBy",
	cellStyle: { textAlign: 'left' }
},
{
	headerName: "Create Date",
	field: "createdDate",
	cellStyle: { textAlign: 'left' }
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

// Products View Modal Strat

function addProduct() {
	$('#addProductModal').modal('show');
	$("#productSave").hide();
	agGrid.simpleHttpRequest({
		url: 'view-product-get-sku-listing?type=' + "both"

	}).then(function(data) {
		console.log(data)

		gridOptionsProduct.api.setRowData(data);

	});
}

function deleteProduct() {
	let productIds = "";
	const productList = gridOptionsLead.api.getSelectedRows();

	productList.forEach(data => {
		productIds += data.productId + ',';
	})
	productIds = productIds.substring(0, productIds.length - 1) + "|" + $("#contactId").text();

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
				getTimeline($("#contactId").text(), "allTimeline");
				getActivity($("#contactId").text(), "allTimeline");
				getProduct($("#contactId").text());
				getProductActivity($("#contactId").text(), "productTimeline");
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
				//$('#project').val(response.body[0].fax);

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
	//$("#project").val('');
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

function closeDeal() {
	$.ajax({
		type: "GET",
		url: "view-crm-deals-editDetails?id=" + coloredDeal,
		success: function(response) {
			if (response.message == "Success") {
				var obj = {};
				obj = response.body[0];
				obj.dealStage = "TSM0012"

				console.log("Deal Deatils With modification --- >> ", obj)
				$.ajax({
					type: "POST",
					url: "view-crm-deals-add",
					contentType: "application/json",
					data: JSON.stringify(obj),
					success: function(response) {
						if (response.code === "Success") {
							var contact = $("#pipelineId").text();
							getDeals(contact);
							getAdminNotification();
						}
					}
				});
			}
		}
	});
}

function createCustomer() {
	getQuotation1();


}
function getQuotation1() {
	closeCustomerrModal();
	$('.formValidation').remove();
	$('#addCustomerModel').modal('show');
	$("#whatsApp").val('');
	$("#gstNo").val('');
	$("#customerType").val('');
	$("#salutation").val('');
	$("#cusEmail").val('');
	$("#custMobile").val('');
	$("#custSkype").val('');
	$("#custDesignation").val('');
	$("#department").val('');
	$("#status").val('');
	$("#pan").val('');
	$("#currency").val('');
	$("#openingBalance").val('');
	$("#paymentTerms").val('');
	$("#enableDtls").val('');
	$("#portableLang").val('');
	$("#faceBook").val('');
	$("#twitter").val('');
	$("#street2").val('');
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
	var contactId = $("#contactId").text();
	$('#crmContactId').val(contactId);

}

function closeCustomerrModal() {
	$('#custVarifyFailedModal').modal('hide');
}

function closeCustomerrModal1() {
	$('#custVarifySucessModal').modal('hide');
}

/*
* Date Validation For Campaign, Call & Meetings
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
function timeValidMeeting(){
	if($("#meetingFromDate").val() == $("#meetingToDate").val()){
		if($("#meetingFromTime").val() && $("#meetingToTime").val()) {
		timeValidation("meetingFromTime", "meetingToTime", "Meeting From Time", "Meeting To Time");
	}
	}
	
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
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	$.ajax({
		type: "GET",
		url: "view-crm-campaigns-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				$(".modal-backdrop").hide();
				$('#delete').attr("disabled", true);
					$("#messageParagraph").text("Campaign Deleted Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				var contact = $("#pipelineId").text();
				getCampaign(contact);
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

function invoicePdfDownloadModal(saleInvoice) {
	//var copytype=$('input[name="copyType"]:checked').val();
	var organization = $("#sessionOrganization1").val();
	var orgDivision = $("#sessionOrgDivision").val();
	
	$('#invoiceModal').modal('hide');
	window.open("/sales/view-saleInvoice-pdf-downloads?saleInvoice="
			+ window.btoa(saleInvoice)+"&organization="
			/* + window.btoa(copytype)+ "&organization=" */
			+ window.btoa(organization) + "&orgDivision="
			+ window.btoa(orgDivision), '_blank');  
}