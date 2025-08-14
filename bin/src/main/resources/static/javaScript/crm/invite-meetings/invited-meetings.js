$(document).ready(function() {
	$('.loader').show();
	var gridDivInvitedMeetings = document.querySelector('#myGridMeetings');
	new agGrid.Grid(gridDivInvitedMeetings, gridOptionsInvitedMeetings);
	$("#totalReq").find('span').append(0);
	getInvitedMeetings()
});


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

/**
 * Ag-grid Date format
 **/
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

/**
 * Ag-Grid Search bar settings
 */


function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	gridOptionsInvitedMeetings.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	gridOptionsInvitedMeetings.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = gridOptionsInvitedMeetings.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);
}

/**
 * Invited meetings view api
 */
function getInvitedMeetings() {
	$("#totalReq").find('span').empty();

	agGrid.simpleHttpRequest({
		url: 'crm-invited-meetings-details',
	}).then(function(data) {
		if (data.code == "Success") {
			let resp = JSON.parse(data.body);
			var len = resp.length
			$("#totalReq").find('span').append(len);
			gridOptionsInvitedMeetings.api.setRowData(resp);

		} else {
			$("#totalReq").find('span').append(0);
			gridOptionsInvitedMeetings.api.setRowData([]);
		}
		$('.loader').hide();

	});
}