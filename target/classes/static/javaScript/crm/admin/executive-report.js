let newLeadOwnerId = ""
let newLeadOwnerName = ""

$(document).ready(function() {
	//$('.loader').show();
	$('#totalLead').find('span').html(0)

	var leadgridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(leadgridDiv, leadgridOptions);

	let userId = $("#executive").val();

	leadDetailsByExecutive(userId)


	/*
		$("#fromDate").datetimepicker({
			format: 'd-m-Y',
			closeOnDateSelect: true,
			timepicker: false,
		});
	
		$("#toDate").datetimepicker({
			format: 'd-m-Y',
			closeOnDateSelect: true,
			timepicker: false,
		});*/


	$("#leadTransferDetails").attr("disabled", "disabled")

});
/*----------------------------Lead Grid Column def and Grid options--------------------------------*/

var columnLeadDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'


}, {
	headerName: "Lead Id",
	field: "leadId",
	pinned: 'left',
	hide: true

}, {
	headerName: "Lead Name",
	field: "leadName",
	width: 200,
	pinned: 'left',
	cellRenderer: function(params) {
		console.log(params, 'draftttt')
		return '<a>' + params.data.leadName + '</a>';
	}
},
{ headerName: "Company", field: "company", width: 200, cellStyle: { textAlign: 'left' }, },
{
	headerName: "Lead Status",
	field: "leadStatus",
	width: 250,
	cellRenderer: params => {
		if (params.value === 'Qualified') {
			return `<div style="display: flex; align-items: center; justify-content: center;"><span style="font-weight: bold;color: #4CAF50 !important;">Qualified</span><i class="fa fa-check-circle-o" style="font-weight: var(--fa-style, 300)!important;font-size: 12px;margin-left: 5px;color: #4CAF50"></i></div>`;
		} else if (params.value === 'Approval Pending') {
			return `<div style="display: flex; align-items: center; justify-content: center;"><span style="font-weight: bold;color: #F29339 !important;">Approval Pending <i class="fa fa-hourglass-half" aria-hidden="true"></i></span></div>`;
		} else if (params.value === 'Junk Lead' || params.value === 'Lost Lead') {
			return `<div style="display: flex; align-items: center; justify-content: center;"><span style="font-weight: bold;color: #ff1240!important;">${params.value} <i class="fa fa-trash" aria-hidden="true"></i></span></div>`;
		} else if (params.value === 'Lead Won' || params.value === 'Lead Won') {
			return `<div style="display: flex; align-items: center; justify-content: center;"><span style="font-weight: bold;color: #e7b100!important;">${params.value} <i class="fa fa-trophy" aria-hidden="true"></i></span></div>`;
		} else {
			return `<div style="display: flex; align-items: center; justify-content: center;">${params.value}</div>`;
		}
	}
},
{ headerName: "Phone", field: "phone", width: 220, cellStyle: { textAlign: 'left' }, },


{

	headerName: "Action",
	width: 200,
	cellStyle: { textAlign: 'center' },
	cellRenderer: function(params) {
		var leadId = params.data.leadId;
		var contact = params.data.contactId;
		var leadName = "Action";
		if (contact == null || contact == "" || contact == "null") {
			return '<div><i class="fa fa-external-link" aria-hidden="true" aria-hidden="true" style="padding-right: 7px; color: #6a2bbf; cursor: pointer;" onclick="viewLeadDetails(\'' + leadId + '\')"></i><a onclick="viewLeadDetails(\'' + leadId + '\')" href="javascript:void(0)">' + leadName + '</a></div>';
		} else {
			return '<div><i class="fa fa-external-link" aria-hidden="true" aria-hidden="true" style="padding-right: 7px; color: #6a2bbf; cursor: pointer;" onclick="viewContactDetails(\'' + contact + '\')"></i><a onclick="viewContactDetails(\'' + contact + '\')" href="javascript:void(0)">' + leadName + '</a></div>';
		}

	}
},
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
//{ headerName: "Lead Status", field: "leadStatus", width: 200, hide: true },
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
	onSelectionChanged: onSelectionChangedExecutive,
	onRowSelected: handleRowSelected
};


function onSelectionChangedExecutive() {
	var selectedNodes = leadgridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadStatus = selectedData.map(node => node.leadStatus);
	console.log(`Lead Status Of The Selected Row Is${leadStatus}`)
	if( leadStatus=="Approval Pending"){
		$("#approveLead").removeClass("d-none");
		$("#rejectLead").removeClass("d-none");
	}else{	
		$("#approveLead").addClass("d-none");
		$("#rejectLead").addClass("d-none");}
}



/**
 * On Row Selection event of Ag-grid
 */
let leadMailId = '';
let leadOwnerMailId = '';
let prevLeadsOwner = '';
function handleRowSelected(event) {
	var isSelected = event.node.isSelected();
	if (isSelected) {
		var selectedRowData = event.node.data;
		console.log("Selected Row Data:", selectedRowData);
		$("#leadTransfer").removeAttr("disabled")
		$("#leadTransferDetails").removeAttr("disabled")
		$("#prevLeadOwner").text(selectedRowData.ownerName)
		$("#leadName").text(selectedRowData.leadName)
		$("#leadId").val(selectedRowData.leadId)
		leadMailId = selectedRowData.email;
		leadOwnerMailId = selectedRowData.ownerMail;
		prevLeadsOwner = selectedRowData.ownerName;
	}
}



/**
 * Lead Transfer Modal hide/show function
 */
function leadTransferModal() {
	$('#myModalAddEmail').modal('show');
}
function closeLeadTransferModal() {
	$('#myModalAddEmail').modal('hide');
	newLeadOwnerId = ""
	newLeadOwnerName = ""
	$("#leadTransfer").attr("disabled", "disabled")
	$("#leadTransferDetails").attr("disabled", "disabled")
	$("#prevLeadOwner").text("")
	$("#leadName").text("")
	$("#leadId").val("")
}

/**
 * Lead Trasfer 
 */

function transferType() {
	var id = $("#transferType").val();
	if (id == "Permanent") {
		$("#fromToDiv").hide();
		$("#newExecutive").show();
		$("#leadOwnerDiv").show();


	} else if (id == "Temporary") {
		$("#fromToDiv").show();
		$("#newExecutive").show();
		$("#leadOwnerDiv").show();
	} else {
		$("#fromToDiv").hide();
		$("#newExecutive").hide();
		$("#leadOwnerDiv").hide();
	}

}

/**
 * getNewLead function
 */
let tranExecutiveMailId = '';
function getNewLead(val, key, cur) {

	var selectedOption = cur.options[cur.selectedIndex];
	tranExecutiveMailId = selectedOption.getAttribute('data-code');


	newLeadOwnerId = val
	newLeadOwnerName = key


	var leadOwneree = $("#prevLeadOwner").text();
	if (leadOwneree == key) {
		$("#errorMsg").text("Oops! This Executive is already linked to this lead. Please choose another.");
		$("#newLeadOwner").text("")
	} else {
		$("#errorMsg").text("")
		$("#newLeadOwner").text(key)
	}

}

function leadTransfer() {
	//$('.loader').show();

	var adminMailId = $("#userMail").val();
	//var toMail = 

	var type = $("#transferType").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var newLeadOwner = $("#newLeadOwner").text();

	let obj = {
		"leadId": $("#leadId").val(),
		"newLeadOwnerName": newLeadOwnerName,
		"newLeadOwnerId": newLeadOwnerId,
		"fromDate": fromDate,
		"toDate": toDate,
		"transferType": type,
		"adminMailId": adminMailId,
		"leadMailId": leadMailId,
		"tranExecutiveMailId": tranExecutiveMailId,
		"leadOwnerMailId": leadOwnerMailId,
		"prevLeadsOwner": prevLeadsOwner


	}
	var validation = true;

	if (newLeadOwner == null || newLeadOwner == "") {
		validation = validationUpdated("Updated Executive Required",
			"newLeadOwner");

	}

	if (validation) {
		$.ajax({
			type: "GET",
			url: "admin-task-assign-lead-trasfer?leadId=" + obj.leadId
				+ "&leadNewOwner=" + newLeadOwnerName + "&leadNewOwnerId=" + newLeadOwnerId + "&fromDate=" + fromDate +
				"&toDate=" + toDate + "&transferType=" + type + "&adminMailId=" + adminMailId + "&leadMailId=" + leadMailId +
				"&tranExecutiveMailId=" + tranExecutiveMailId + "&leadOwnerMailId=" + leadOwnerMailId + "&prevLeadsOwner=" + prevLeadsOwner,
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					var data = JSON.parse(response.body);
					$("#executive").val(newLeadOwnerId)

					leadDetailsByExecutive(data.newLeadOwnerId)

					closeLeadTransferModal()//Close Modal for lead transfer
					//let userId = $("#executive").val();

					$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				} else {
					closeLeadTransferModal()
					$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			}
		});
	}
}

/**
 * Ag-Grid Search bar settings
 */


function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	leadgridOptions.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	leadgridOptions.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = leadgridOptions.api.getDisplayedRowCount();
	$('#totalLead span').html(displayedRowCount);
}


/**
 * Get the Lead details by Executive
 */
function getLeadDetails(executiveId) {
	resetQuickFilter();
	leadDetailsByExecutive(executiveId);

}

/**
 * Get the Lead data according to the executive selected
 */

function leadDetailsByExecutive(userId) {
	$('#totalLead').find('span').html("0");
	let pageno = 1;

	// Lead View api
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId,
	}).then(function(data) {
		if (data.code == "Success") {
			var resp = JSON.parse(data.body);

			console.log("Ashish ----->>>--->>>>--->>.", data)

			var len = resp.length;
			$('#totalLead').find('span').html(len);

			leadgridOptions.api.setRowData(resp);
			if (resp.length > 0) {
	
				$('#totalPageno').val(resp[0].totalPageno); 
				pages = resp[0].totalPageno;
	
			}
			createPagination(pages, pageno);

		} else {
			leadgridOptions.api.setRowData([])
		}

		$('.loader').hide();
	});
}

/**
 * redirect to lead details view
 */
function viewLeadDetails(id) {
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-leads-detail?id=" + id;
}

/**
 * redirect to Contact details view
 */
function viewContactDetails(id) {

	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-contacts-detail?id=" + id;
}


function showConversation() {
	$("#transferDetails").empty();
	$('#transferHistoryModal').modal('show');
	var selectedRows = leadgridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.leadId;
	});

	$.ajax({
		type: "GET",
		url: "admin-lead-transferred-history?id=" + selectedRowsString,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				console.log("M416" + JSON.stringify(response));
				if (response.body[0] === null) {
					// No transferred history found
					$("#transferDetails").html('<p style="font-weight: 600;margin-top: 155px;">No Transferred History Found</p>');
				} else {
					var res = JSON.parse(response.body[0]);
					console.log("Data" + res);
					for (var i = 0; i < res.length; i++) {
						var Permanent = '';
						var date = '';

						if (res[i].type == "Permanent") {
							Permanent = '<div class="transfer-type transfer-type-temporary">' + res[i].type + ' Transferred </div>'
						} else {
							Permanent = '<div class="transfer-type transfer-type-permanent">' + res[i].type + ' Transferred </div>'
						}

						if (res[i].type == "Temporary") {
							date = '<div class="row">' +
								'<div class="col-md-6 d-flex trns-margins">' +
								'<span>Date From:&nbsp;</span>' +
								'<p> ' + res[i].fromDate + '</p></div>' +
								'<div class="col-md-6 d-flex trns-margins">' +
								'<span>Date To:&nbsp;</span>' +
								'<p> ' + res[i].toDate + '</p></div>' +
								'</div>'
						} else {
							date = '<div class="row">' +
								'<div class="col-md-6 d-flex trns-margins">' +
								'<span>Date:&nbsp;</span>' +
								'<p> ' + res[i].transferDate + '</p></div>' +
								'</div>'
						}

						var data = '<div class="transfer-item">' +
							'<p class="trans-head">Transferred By: ' + res[i].transferredBy + '</p>' +
							Permanent +
							'<div class="lead-name">' + res[i].leadName + '</div>' +
							'<div class="lead-details">' +
							'<div class="row">' +
							'<div class="col-md-6 d-flex">' +
							'<span>Transferred From:&nbsp;</span>' +
							'<p>' + res[i].prevExecutive + '</p></div>' +
							'<div class="col-md-6 d-flex"><span>Transferred To:&nbsp;</span>' +
							'<p>' + res[i].curExecutive + '</p></div></div>' +
							date +
							'</div>' +
							'</div>'

						$("#transferDetails").append(data);
					}
				}
			}
		},
		error: function(data) { }
	});
}


function closeTransferModal() {
	$("#transferHistoryModal").modal('hide');
}

// for campaigns
function dateValid() {
	if ($("#fromDate").val() && $("#toDate").val())
		dateValidation('fromDate', 'toDate', 'From Date', 'To Date');
}