/* @Author: Pankaj Kumar
*  Date: 17-04-2024
*/
let tabType = '';

// Json Predefined Objects
let filterObj = {
	type: "lead",
	fromDate: "",
	toDate: "",
	toDayLeads: false,
	convertedLead: false,
	nonConvertedLead: false,
	leadByProject: false,
	leadByProduct: false,
	leadStatusList: "",
	leadSourceList: ""
};

let contactObj = {
	type: "contact",
	fromDate: "",
	toDate: "",
	deals: false,
	quotations: false,
	salesInvoice: false,
	purchaseOrder: false,
	invoices: false,
	industriesList: ""
};

let meetingsObj = {
	type: "meeting",
	fromDate: "",
	toDate: "",
	toDayMeetings: false,
	meetingsByAccount: "",
	meetingsByStatus: "",
	meetingsByDurations: "",
};

let dealsObj = {
	type: "deals",
	fromDate: "",
	toDate: "",
	dealMinAmount: 0.00,
	dealMaxAmount: 0.00,
	dealsByStages: "",
	dealsClosingMonth: false,
};

let callsObj = {
	type: "calls",
	fromDate: "",
	toDate: "",
	toDayCalls: false,
	callsByAccount: "",
	callsByStatus: "",
	callsByDurations: "",
};

let fYearStartDate = '';
let getCurrentDate = '';
var previousFromDateStr = '';
var previousToDateStr = '';

$(document).ready(function() {

	// Ag grid Intilization
	var leadgridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(leadgridDiv, leadgridOptions);

	var contactgridDiv = document.querySelector('#myGridContacts');
	new agGrid.Grid(contactgridDiv, contactgridOptions);

	var meetinggridDiv = document.querySelector('#myGridMeetings');
	new agGrid.Grid(meetinggridDiv, gridOptionsMeetings);
	
	var dealsgridDiv = document.querySelector('#myGridDeals');
	new agGrid.Grid(dealsgridDiv, gridOptionsDeals);
	
	var callridDiv = document.querySelector('#myGridCalls');
	new agGrid.Grid(callridDiv, gridOptionsCalls);

	// Set Financial Year Date & Current Date
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var financialYearStartDate = new Date(currentYear, 3, 1);
	if (currentDate < financialYearStartDate) {
		financialYearStartDate.setFullYear(currentYear - 1);
	}
	fYearStartDate = formatDate(financialYearStartDate);
	getCurrentDate = formatDate(currentDate);
	$('#fromdate').val(fYearStartDate);
	$('#todate').val(getCurrentDate);
	$('#fromdateContacts').val(fYearStartDate);
	$('#todateContacts').val(getCurrentDate);
	$('#fromdateMeeting').val(fYearStartDate);
	$('#todateMeeting').val(getCurrentDate);
	$('#fromdateDeals').val(fYearStartDate);
	$('#todateDeals').val(getCurrentDate);
	$('#fromdateCall').val(fYearStartDate);
	$('#todateCall').val(getCurrentDate);

	$('#fromdate,#todate').click(function() {
		previousFromDateStr = $('#fromdate').val();
		previousToDateStr = $('#todate').val();
	});

	$('#fromdateContacts,#todateContacts').click(function() {
		previousFromDateStr = $('#fromdateContacts').val();
		previousToDateStr = $('#todateContacts').val();
	});

	$('#fromdateMeeting,#todateMeeting').click(function() {
		previousFromDateStr = $('#fromdateMeeting').val();
		previousToDateStr = $('#todateMeeting').val();
	});
	
	$('#fromdateDeals,#todateDeals').click(function() {
		previousFromDateStr = $('#fromdateDeals').val();
		previousToDateStr = $('#todateDeals').val();
	});
	
	$('#fromdateCall,#todateCall').click(function() {
			previousFromDateStr = $('#fromdateCall').val();
			previousToDateStr = $('#todateCall').val();
    });

});


// Validate Date 
function dateValidation(id) {
	let fromDate = '';
	let toDate = '';
	
	if (id == "fromdate" || id == "todate") {
		fromDate = $('#fromdate').val();
		toDate = $('#todate').val();
	} else if (id == "fromdateContacts" || id == "todateContacts") {
		fromDate = $('#fromdateContacts').val();
		toDate = $('#todateContacts').val();
	} else if (id == "fromdateMeeting" || id == "todateMeeting") {
		fromDate = $('#fromdateMeeting').val();
		toDate = $('#todateMeeting').val();
	} else if (id == "fromdateDeals" || id == "todateDeals") {
		fromDate = $('#fromdateDeals').val();
		toDate = $('#todateDeals').val();
	} else if (id == "fromdateCall" || id == "todateCall") {
		fromDate = $('#fromdateCall').val();
		toDate = $('#todateCall').val();
	}
	
	const res = dateValid(fromDate, toDate);
	if (!res) {
		if (tabType == "contacts") {
			$('#fromdateContacts').val(previousFromDateStr);
			$('#todateContacts').val(previousToDateStr);
		} else if (tabType == "meetings") {
			$('#fromdateMeeting').val(previousFromDateStr);
			$('#todateMeeting').val(previousToDateStr);
		} else if (tabType == "deals") {
			$('#fromdateDeals').val(previousFromDateStr);
			$('#todateDeals').val(previousToDateStr);
		} else if (tabType == "calls") {
			$('#fromdateCall').val(previousFromDateStr);
			$('#todateCall').val(previousToDateStr);
		}
		else {
			$('#fromdate').val(previousFromDateStr);
			$('#todate').val(previousToDateStr);
		}
	}
}

function dateValid(fromDate, toDate) {

	if (fromDate > toDate) {
		$("#messageParagraph").text("From date should be less than to date");
		$("#msgOkModal").removeClass("btn3").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}
	return true;
}

function formatDate(date) {
	var year = date.getFullYear();
	var month = padZeros(date.getMonth() + 1);
	var day = padZeros(date.getDate());
	return year + '-' + month + '-' + day;
}

function padZeros(num) {
	return (num < 10 ? '0' : '') + num;
}

$(document).ready(function() {
	let url = "crm-leads-data?fromDate=" + fYearStartDate + "&toDate=" + getCurrentDate;

	// Promise.all() Used to send both AJAX requests simultaneously
	Promise.all([
		sendAjaxRequest(url, filterObj),
		sendAjaxRequest(url, contactObj),
		sendAjaxRequest(url, meetingsObj),
		sendAjaxRequest(url, callsObj),
		sendAjaxRequest(url, dealsObj)
	]).then(function(responses) {

		var leadResponse = responses[0];
		var contactResponse = responses[1];
		var meetingsResponse = responses[2];
		var callsResponse = responses[3];
		var dealsResponse = responses[4];

		leadgridOptions.api.setRowData(leadResponse);
		contactgridOptions.api.setRowData(contactResponse);
		gridOptionsMeetings.api.setRowData(meetingsResponse);
		gridOptionsCalls.api.setRowData(callsResponse);
		gridOptionsDeals.api.setRowData(dealsResponse);
		

	}).catch(function(error) {
		console.error("Error: Happens", error);
	});
});

/*Price Slider Js*/
$(document).ready(function() {
    const rangeInput = document.querySelectorAll(".range-input input"),
        priceInput = document.querySelectorAll(".price-input input"),
        range = document.querySelector(".slider .progress");
    let priceGap = 1000;

    priceInput.forEach((input) => {
        input.addEventListener("input", (e) => {
            let minPrice = parseInt(priceInput[0].value),
                maxPrice = parseInt(priceInput[1].value);

            if (maxPrice - minPrice >= priceGap && maxPrice <= rangeInput[1].max) {
                if (e.target.className === "input-min") {
                    rangeInput[0].value = minPrice;
                    range.style.left = (minPrice / rangeInput[0].max) * 100 + "%";
                } else {
                    rangeInput[1].value = maxPrice;
                    range.style.right = 100 - (maxPrice / rangeInput[1].max) * 100 + "%";
                }
            }
        });
    });

    rangeInput.forEach((input) => {
        input.addEventListener("input", (e) => {
            let minVal = parseInt(rangeInput[0].value),
                maxVal = parseInt(rangeInput[1].value);

            if (maxVal - minVal < priceGap) {
                if (e.target.className === "range-min") {
                    rangeInput[0].value = maxVal - priceGap;
                } else {
                    rangeInput[1].value = minVal + priceGap;
                }
            } else {
                priceInput[0].value = minVal;
                priceInput[1].value = maxVal;
                range.style.left = (minVal / rangeInput[0].max) * 100 + "%";
                range.style.right = 100 - (maxVal / rangeInput[1].max) * 100 + "%";
            }
        });
    });
});

function sendAjaxRequest(url, data) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			type: "POST",
			url: url,
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code === "Success") {
					resolve(JSON.parse(response.body));
				} else if (response.code === "Failed") {
					leadgridOptions.api.setRowData([]);
					contactgridOptions.api.setRowData([]);
					gridOptionsMeetings.api.setRowData([]);
					resolve();
				}
			},
			error: function(error) {
				reject(error);
			}
		});
	});
}

/*Lead Grid Columns Starts*/

var columnLeadDefs = [{
	headerCheckboxSelection: true, checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'
},
{ headerName: "Lead Id", field: "leadId", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Lead Name", field: "leadName", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Company", field: "company", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Lead Status", field: "leadStatus", width: 220, cellStyle: { textAlign: 'left' }, },
{ headerName: "Phone", field: "phone", width: 220, cellStyle: { textAlign: 'left' }, },
{ headerName: "Email", field: "email", width: 250, cellStyle: { textAlign: 'left' }, },
{ headerName: "Created Date", field: "createdDate", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Lead Source", field: "leadSource", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Lead Owner", field: "ownerName", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "First Name", field: "fiirstName", width: 200, hide: true },
{ headerName: "Last Name", field: "lastName", width: 200, hide: true },
{ headerName: "Title", field: "leadTitle", width: 200, hide: true },
{ headerName: "Mobile", field: "mobile", width: 200, hide: true },
{ headerName: "Fax", field: "leadFax", width: 200, hide: true },
{ headerName: "Website", field: "website", width: 200, hide: true },
{ headerName: "Lead Status", field: "leadStatus", width: 200, hide: true },
{ headerName: "Industry", field: "leadIndustry", width: 200, hide: true },
{ headerName: "No. Of Employee", field: "noOfEmployee", width: 200, hide: true },
{ headerName: "Annual Revenue", field: "leadRevenue", width: 200, hide: true },
{ headerName: "Ratings", field: "leadRatings", width: 200, hide: true },
{ headerName: "Created On", field: "createdTime", width: 200, hide: true },
{ headerName: "Updated On", field: "updatedDate", width: 200, hide: true },
{ headerName: "Street", field: "street", width: 200, hide: true },
{ headerName: "City", field: "city", width: 200, hide: true },
{ headerName: "State", field: "leadState", width: 200, hide: true },
{ headerName: "Country", field: "leadCountry", width: 200, hide: true },
{ headerName: "Description", field: "leadDesc", width: 200, hide: true },
{ headerName: "Skype Id", field: "skypeId", width: 200, hide: true },
{ headerName: "Email Opt", field: "leadEmailOpt", width: 200, hide: true },
{ headerName: "Secondary Email", field: "secondaryEmail", width: 200, hide: true },
{ headerName: "Twitter", field: "leadTwitter", width: 200, hide: true },
{ headerName: "Converted To lead", field: "leadConverted", width: 200, hide: true },
];

var leadgridOptions = {
	columnDefs: columnLeadDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	suppressRowClickSelection: true,
	suppressExcelExport: true,
};

/*Lead Grid Columns Ends*/

/*Contacts Grid Columns Starts*/
var columnContactDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'
},
{ headerName: "Contact Id", field: "contactId", width: 200, },
{ headerName: "Contact Name", field: "contactname", width: 200, },
{ headerName: "Phone", field: "contactPhone", width: 200, },
{ headerName: "Email", field: "contactEmail", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Company", field: "accountName", width: 200, },
{ headerName: "Contact Owner", field: "contactOwner", width: 200, },
{ headerName: "Created Date", field: "createdDate", width: 200, },];

var contactgridOptions = {
	columnDefs: columnContactDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'multiple',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	suppressRowClickSelection: true,
};

/*Contacts Grid Columns Ends*/

/*Meetings Grid Columns Start*/

var columnDefsMeetings = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'
},
{ headerName: 'Meeting Id', field: "meetingId", width: 200, },
{ headerName: "Meeting Title", field: "meetingTitle", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "From", field: "meetingFromDateTime", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "To", field: "meetingToDateTime", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "Related To", field: "meetingCallRelatedTo", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "Meeting Status", field: "meetingStatus", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "Meeting Owner", field: "meetingOwner", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "Created On", field: "meetingCreatedOn", width: 200, cellStyle: { textAlign: 'left' } },
{ headerName: "Meeting Summary", field: "meetingSummary", width: 200, cellStyle: { textAlign: 'left' }, hide: true }];

var gridOptionsMeetings = {
	columnDefs: columnDefsMeetings,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10
	}
};
/*Meetings Grid Columns Ends*/

/*Deal Grid Options Start*/
var columnDealDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

}, {
	headerName: "Deal Name",
	field: "dealName",
	width: 220,
	pinned: 'left'}, {
headerName: "Amount",field: "dealAmount",width: 220,cellStyle: { textAlign: 'right' },cellRenderer: function(params) {return params.data.dealAmount.toFixed(2);},}, {
headerName: "Closing Date",field: "dealClosingDate",width: 220,cellStyle: { textAlign: 'center' },}, {
headerName: "Stage",field: "dealStage",width: 250,cellStyle: { textAlign: 'center' },}, {
headerName: "Contact Name",field: "contactName",width: 200,cellStyle: { textAlign: 'center' },}, {
headerName: "Account Name",field: "dealAccountName",width: 200,cellStyle: { textAlign: 'center' },}, {
headerName: "Deal Owner",field: "dealOwner",width: 200,cellStyle: { textAlign: 'center' },}, {
headerName: "Created Date",field: "createdDate",width: 200,cellStyle: { textAlign: 'center' },},];

var gridOptionsDeals = {
	columnDefs: columnDealDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'multiple',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	suppressRowClickSelection: true,
};
/*Deal Grid Options End*/

/*Calls Grids Columns Start*/
var columnDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'
}, { headerName: 'Call Id', field: "callId", },
{ headerName: "Subject", field: "callSubject", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Call Type", field: "callType", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Call Start Date", field: "callStartDate", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Call Start Time", field: "callStartTime", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Call End Time", field: "callEndTime", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Status", field: "callOngoingStatus", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Lead Name", field: "callLeadName", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Contact Name", field: "contactname", cellStyle: { textAlign: 'left', width: 200, } },
{ headerName: "Task Executive", field: "callHostOwner", cellStyle: { textAlign: 'left', width: 200, } }];

var gridOptionsCalls = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10
	}
};
/*Calls Grids Columns Ends*/

// Function to initialize the checkboxes
function initializeCheckboxes() {
	$(".child-checkbox").prop('checked', false);
	$('.child-checkboxes').hide();
}


// Function to toggle child checkboxes visibility for Status
function toggleChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.status-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.status-child').prop('checked', false);
	}
}

//  Function to toggle child checkboxes visibility for Sources
function toggleSourceChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.source-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.source-child').prop('checked', false);
	}
}

/*Function For Leads Industries Childs*/
function toggleAccountChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.industries-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.industries-child').prop('checked', false);
	}
}

/*Function For Meeting Account Childs*/
function meetingAccChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.meetingsacc-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.accounts-child').prop('checked', false);
	}
}

/*Function For Meeting Status Childs*/
function meetingStatusChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.meetstatus-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.meetStatus-child').prop('checked', false);
	}
}
/*Function For Meeting duration*/
function meetingDurationChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.meetDuration-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.meetDuration-child').prop('checked', false);
	}
}
/*Function For deals deals stages*/
function dealsStagesChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.dealsStages-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.dealsStages-child').prop('checked', false);
	}
}

/*function for calls callAccChildCheckboxes*/
function callAccChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.callsAcc-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.callaccounts-child').prop('checked', false);
	}
}

/*functions for callstatus childboxes*/
function callStatusChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.callstatus-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.meetStatus-child').prop('checked', false);
	}
}

/*Functions for callDuration child boxes*/
function callsDurationChildCheckboxes(checkbox) {
	var childCheckboxes = $(checkbox).closest('.card-body').find('.callDuration-child-checkbox');

	if (checkbox.checked) {
		childCheckboxes.show();
	} else {
		childCheckboxes.hide();
		$('.meetDuration-child').prop('checked', false);
	}
}

// Event listener for child checkboxes
$(".child-checkbox").on('click', function() {
	var childCheckbox = $(this);
	var parentCheckbox = childCheckbox.closest('.card-body').find('.parent-checkbox');
	var siblingCheckboxes = childCheckbox.closest('.child-checkboxes').find('.child-checkbox');

	if (childCheckbox.prop('checked')) {
		parentCheckbox.prop('checked', true);
	} else {
		var allUnchecked = true;
		siblingCheckboxes.each(function() {
			if ($(this).prop('checked')) {
				allUnchecked = false;
				return false; // Exit the loop early if any checkbox is checked
			}
		});
		if (allUnchecked) {
			parentCheckbox.prop('checked', false);
		}
	}
});

function filterLeads() {
	// For Status Child Checkbox
	var checkedValues = [];
	$('.status-child:checked').each(function() {
		checkedValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var statusChildString = "(" + checkedValues.join(",") + ")";

	// For Source Child Checkbox
	var checkedValuesSources = [];
	$('.source-child:checked').each(function() {
		checkedValuesSources.push("\\\"" + $(this).val() + "\\\"");
	});
	var sourceChildJson = "(" + checkedValuesSources.join(",") + ")";

	let fromDate = $("#fromdate").val();
	let toDate = $("#todate").val();

	var isCheckedTodayLead = $("#todayLead").prop('checked');
	var isCheckedConLead = $("#converLead").prop('checked');
	var isCheckedNonConLead = $("#nonConverted").prop('checked');
	var isCheckedLeadProject = $("#leadProject").prop('checked');
	var isCheckedLeadProduct = $("#leadProducts").prop('checked');

	filterObj.fromDate = fromDate;
	filterObj.toDate = toDate;
	filterObj.toDayLeads = isCheckedTodayLead;
	filterObj.convertedLead = isCheckedConLead;
	filterObj.nonConvertedLead = isCheckedNonConLead;
	filterObj.leadByProject = isCheckedLeadProject;
	filterObj.leadByProduct = isCheckedLeadProduct;
	filterObj.leadStatusList = statusChildString == "()" ? "" : statusChildString;
	filterObj.leadSourceList = sourceChildJson == "()" ? "" : sourceChildJson;
    
	let url = "crm-leads-data?fromDate=" + fromDate + "&toDate=" + toDate;

	sendAjaxRequest(url, filterObj).then((resp) => {
		console.log(resp)
		leadgridOptions.api.setRowData(resp);
	});

}

function handleTabClick(id,resetId) {
	tabType = id;
	$('.form-check-input').prop('checked', false);
	resetFilters(resetId);
}

// Restet the Filtered Elements
function resetFilters(id) {
	let url = "crm-leads-data?fromDate=" + fYearStartDate + "&toDate=" + getCurrentDate;
	let data = {};

	if (id === "lead") {
		data = {
			type: "lead",
			fromDate: "",
			toDate: "",
			toDayLeads: false,
			convertedLead: false,
			nonConvertedLead: false,
			leadByProject: false,
			leadByProduct: false,
			leadStatusList: "",
			leadSourceList: ""
		};
		$('#fromdate').val(fYearStartDate);
		$('#todate').val(getCurrentDate);
		leadgridOptions.api.setRowData([]);
	} else if (id === "contact") {
		data = {
			type: "contact",
			fromDate: "",
			toDate: "",
			deals: false,
			quotations: false,
			salesInvoice: false,
			purchaseOrder: false,
			invoices: false,
			industriesList: ""
		};
		$('#fromdateContacts').val(fYearStartDate);
		$('#todateContacts').val(getCurrentDate);
		contactgridOptions.api.setRowData([]);
	} else if (id === "meetings") {
		data = {
			type: "meeting",
			fromDate: "",
			toDate: "",
			toDayMeetings: false,
			meetingsByAccount: "",
			meetingsByStatus: "",
			meetingsByDurations: "",
		};
		$('#fromdateMeeting').val(fYearStartDate);
		$('#todateMeeting').val(getCurrentDate);
		gridOptionsMeetings.api.setRowData([]);
	} else if (id === "deals") {
		data = {
			type: "deals",
			fromDate: "",
			toDate: "",
			dealMinAmount: 0.00,
			dealMaxAmount: 0.00,
			dealsByStages: "",
			dealsClosingMonth: false,
		};
		$('#fromdateDeals').val(fYearStartDate);
		$('#todateDeals').val(getCurrentDate);
		gridOptionsDeals.api.setRowData([]);
	} else if (id === "calls") {
		data = {
			type: "calls",
			fromDate: "",
			toDate: "",
			toDayCalls: false,
			callsByAccount: "",
			callsByStatus: "",
			callsByDurations: "",

		};
		$('#fromdateCall').val(fYearStartDate);
		$('#todateCall').val(getCurrentDate);
		gridOptionsCalls.api.setRowData([]);
	}

	sendAjaxRequest(url, data).then((resp) => {
		if (id === "lead") {
			leadgridOptions.api.setRowData(resp);
		} else if (id === "contact") {
			contactgridOptions.api.setRowData(resp);
		} else if (id === "meetings") {
			gridOptionsMeetings.api.setRowData(resp);
		} else if (id === "deals") {
			gridOptionsDeals.api.setRowData(resp);
		} else if (id === "calls") {
			gridOptionsCalls.api.setRowData(resp);
		}
	});

	$('.form-check-input').prop('checked', false);
}


// Export Reports Data Into Csv Format
function downloadReport(id) {
	let gridOptions, gridApi;

	if (id === "lead") {
		gridOptions = leadgridOptions;
		gridApi = leadgridOptions.api;
	} else if (id === "contact") {
		gridOptions = contactgridOptions;
		gridApi = contactgridOptions.api;
	} else if (id === "meetings") {
		gridOptions = gridOptionsMeetings;
		gridApi = gridOptionsMeetings.api;
	} else if (id === "deals") {
		gridOptions = gridOptionsDeals;
		gridApi = gridOptionsDeals.api;
	} else if (id === "calls") {
		gridOptions = gridOptionsCalls;
		gridApi = gridOptionsCalls.api;
	} 
	
	else {
		console.error("Invalid grid id specified");
		return;
	}

	let allColumns = gridOptions.columnApi.getAllColumns();
	let exportColumns = allColumns.filter(col => !col.userProvidedColDef.suppressExcelExport);

	gridApi.exportDataAsCsv({
		columnKeys: exportColumns.map(col => col.colId),
	});
}

// Filter Contact Function
function filterContact() {
	// For Status Child Checkbox
	var checkedIndusriesValues = [];
	$('.industries-child:checked').each(function() {
		checkedIndusriesValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var industriesChildString = "(" + checkedIndusriesValues.join(",") + ")";

	let fromDate = $("#fromdateContacts").val();
	let toDate = $("#todateContacts").val();


	var isCheckedContactDeals = $("#contactDeals").prop('checked');
	var isCheckedContactQuotation = $("#contactQuotation").prop('checked');
	var isCheckedContactSalesInv = $("#contactSalesInv").prop('checked');
	var isCheckedContactPo = $("#contactPo").prop('checked');
	var isCheckedContactInvoice = $("#contactInvoice").prop('checked');

	contactObj.fromDate = fromDate;
	contactObj.toDate = toDate;
	contactObj.deals = isCheckedContactDeals;
	contactObj.quotations = isCheckedContactQuotation;
	contactObj.salesInvoice = isCheckedContactSalesInv;
	contactObj.purchaseOrder = isCheckedContactPo;
	contactObj.invoices = isCheckedContactInvoice;
	contactObj.industriesList = industriesChildString == "()" ? "" : industriesChildString;

	let url = "crm-leads-data?fromDate=" + fromDate + "&toDate=" + toDate;

	sendAjaxRequest(url, contactObj).then((resp) => {
		console.log(resp)
		contactgridOptions.api.setRowData(resp);
	});

}


// Filter Meetings Functions
function filterMeetings() {
	// For Status Child Checkbox
	var checkedAccValues = [];
	$('.accounts-child:checked').each(function() {
		checkedAccValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var meetAccChildString = "(" + checkedAccValues.join(",") + ")";

	var checkedMeetStatusValues = [];
	$('.meetStatus-child:checked').each(function() {
		checkedMeetStatusValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var meetStatusChildString = "(" + checkedMeetStatusValues.join(",") + ")";

	var checkedMeetDurationsValues = [];
	$('.meetDuration-child:checked').each(function() {
		checkedMeetDurationsValues.push($(this).val());
	});

	var meetDurationsChildString = '';

	if (checkedMeetDurationsValues.length === 1) {
		meetDurationsChildString = checkedMeetDurationsValues[0];
	} else if (checkedMeetDurationsValues.length > 1) {
		// Check if both checkboxes are checked
		if (checkedMeetDurationsValues.includes('below30') && checkedMeetDurationsValues.includes('upto30')) {
			meetDurationsChildString = 'both';
		} else {
			meetDurationsChildString = checkedMeetDurationsValues.join(",");
		}
	}

	let fromDate = $("#fromdateMeeting").val();
	let toDate = $("#todateMeeting").val();

	var isCheckedTodayMeetings = $("#todayMeetings").prop('checked');

	meetingsObj.fromDate = fromDate;
	meetingsObj.toDate = toDate;
	meetingsObj.toDayMeetings = isCheckedTodayMeetings;
	meetingsObj.meetingsByAccount = meetAccChildString == "()" ? "" : meetAccChildString;
	meetingsObj.meetingsByStatus = meetStatusChildString == "()" ? "" : meetStatusChildString;
	meetingsObj.meetingsByDurations = meetDurationsChildString == "()" ? "" : meetDurationsChildString;

	let url = "crm-leads-data?fromDate=" + fromDate + "&toDate=" + toDate;

	sendAjaxRequest(url, meetingsObj).then((resp) => {
		gridOptionsMeetings.api.setRowData(resp);
	});

}

/*Filter Deals Functions*/
function filterDeals(){
	const minAmount = parseInt($(".input-min").val());
    const maxAmount = parseInt($(".input-max").val());
    let fromDate = $("#fromdateDeals").val();
	let toDate = $("#todateDeals").val();
    
    var checkedStagesValues = [];
	$('.dealsStages-child:checked').each(function() {
		checkedStagesValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var dealsStagesChildString = "(" + checkedStagesValues.join(",") + ")";
	
	var isCheckedThisMonthDeals = $("#thisMonthDeals").prop('checked');
	
	dealsObj.fromDate = fromDate;
	dealsObj.toDate = toDate;
	dealsObj.dealsClosingMonth = isCheckedThisMonthDeals;
	dealsObj.dealsByStages = dealsStagesChildString == "()" ? "" : dealsStagesChildString;
	dealsObj.dealMinAmount = minAmount;
	dealsObj.dealMaxAmount = maxAmount;
    
    let url = "crm-leads-data?fromDate=" + fromDate + "&toDate=" + toDate;

	sendAjaxRequest(url, dealsObj).then((resp) => {
		gridOptionsDeals.api.setRowData(resp);
	});
	
}  

//Filter calls function
function filterCalls() {
	// For account Child Checkbox
	var checkedCallValues = [];
	$('.callaccounts-child:checked').each(function() {
		checkedCallValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var callAccChildString = "(" + checkedCallValues.join(",") + ")";

	// For status Child Checkbox
	var checkedCallStatusValues = [];
	$('.callStatus-child:checked').each(function() {
		checkedCallStatusValues.push("\\\"" + $(this).val() + "\\\"");
	});
	var callStatusChildString = "(" + checkedCallStatusValues.join(",") + ")";

	var checkedCallDurationsValues = [];
	$('.callDuration-child:checked').each(function() {
		checkedCallDurationsValues.push($(this).val());


	});
	var callDurationsChildString = '';

	if (checkedCallDurationsValues.length === 1) {
		callDurationsChildString = checkedCallDurationsValues[0];
	} else if (checkedCallDurationsValues.length > 1) {
		// Check if both checkboxes are checked
		if (checkedCallDurationsValues.includes('below30') && checkedCallDurationsValues.includes('upto30')) {
			callDurationsChildString = 'both';
		} else {
			callDurationsChildString = checkedCallDurationsValues.join(",");
		}
	}

	let fromDate = $("#fromdateCall").val();
	let toDate = $("#todateCall").val();

	var isCheckedTodayCalls = $("#todayCalls").prop('checked');

	callsObj.fromDate = fromDate;
	callsObj.toDate = toDate;
	callsObj.toDayCalls = isCheckedTodayCalls;
	callsObj.callsByAccount = callAccChildString == "()" ? "" : callAccChildString;
	callsObj.callsByStatus = callStatusChildString == "()" ? "" : callStatusChildString;
	callsObj.callsByDurations = callDurationsChildString == "()" ? "" : callDurationsChildString;

	let url = "crm-leads-data?fromDate=" + fromDate + "&toDate=" + toDate;

	sendAjaxRequest(url, callsObj).then((resp) => {
		gridOptionsCalls.api.setRowData(resp);
	});
}
