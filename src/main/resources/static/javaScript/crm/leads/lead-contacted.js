
let roleArray = [];
let accessedRole = ['rol001', 'rol011'];
$(document)
	.ready(
		function() {

			var docDet = `<div class="control-group">
														<label class="custom-file-upload" for="uploadDoc_1"> 
															<i class="ti-plus"></i>
														</label>
														<div class="controls">
															<input type="file" class="document" id="uploadDoc_1"
																accept=".jpeg, .jpg, .png, .pdf" name="userImage"
																onchange="saveMultiFileDoc1(event)" />
														</div>
													</div>
													<input type="hidden" id="uploadHidden1" class="uploadHidCls">
													<div id="uploadedBillDiv_1" class="uploadedBillCls order-3"></div>
													<div id="imageName_1" class="imageName"></div>
													<div id="validationDiv1"></div>`;
			$("#documentDiv").html(docDet);
			$("#leadStatus option[value='TLSM00009'], \
			  #leadStatus option[value='TLSM00011'], \
			  #leadStatus option[value='TLSM00001'], \
			  #leadStatus option[value='TLSM00005'], \
			  #leadStatus option[value='TLSM00006']").remove();

			setLeadTabAsDefault();
			document.getElementById("onlineMode").checked = true;
			toggleModeFields();
			toggleFrequencyFields();
			// $('#multiple9').chosen();


			document.querySelectorAll('.custom-tab').forEach(tab => {
				tab.addEventListener('click', function() {
					document.querySelectorAll('.custom-tab').forEach(t => t.classList.remove('active'));
					document.querySelectorAll('.custom-tab-content').forEach(content => content.classList.remove('active'));

					this.classList.add('active');
					const target = document.querySelector(this.dataset.target);
					if (target) target.classList.add('active');

					if (this.dataset.target === "#draftTab") {
						$("#editMailIcon").addClass("d-none");
						$("#deleteDraftIcon").addClass("d-none");
						draftgridOptions.api.deselectAll();
					} else {
						draftgridOptions.api.deselectAll();
						// Hide the icons when leaving the Draft tab
						$("#editMailIcon").addClass("d-none");
						$("#deleteDraftIcon").addClass("d-none");
					}
				});
			});


			var leadgridDiv = document.querySelector('#myLeadGrid');
			new agGrid.Grid(leadgridDiv, gridOptionsLead);

			var mailgridDiv = document.querySelector('#myGridMail');
			new agGrid.Grid(mailgridDiv, mailgridOptions);

			var draftgridDiv = document.querySelector('#myGridDraft');
			new agGrid.Grid(draftgridDiv, draftgridOptions);

			var productGrid = document.querySelector('#leadProductGrid');
			new agGrid.Grid(productGrid, productGridOptions);
			userId = $("#userId").val();
			setTimeout(() => {
				if (gridOptionsLead.api) {
					gridOptionsLead.api.getDisplayedRowAtIndex(0)?.setSelected(true);
				}
			}, 1000);
			let ccMailListData = new Set();
			let bccMailListData = new Set();
			let isValidEmail = false;
			$("#ccMail").on('keyup', function(event) {
				const email = $(this).val().trim();

				// Trigger AJAX search only if input is not empty
				if (email.length > 0) {
					getMailsAutosearch(email);
				}

				if (event.keyCode === 32 || event.keyCode === 13) {
					if (isValidEmail) {
						addEmailToContainer(email);
						ccMailListData.add(email);
						$(this).val('');
						isValidEmail = false;
					}
				} else {
					isValidEmail = isValidEmailCc(email);
				}
			});

			$("#bccMail").on('keyup', function(event) {
				const email = $(this).val().trim();

				// Trigger AJAX search only if input is not empty
				/*if (email.length > 0) {
					getMailsAutosearch(email);
				}*/

				if (event.keyCode === 32 || event.keyCode === 13) {
					if (isValidEmail) {
						addEmailToBccContainer(email);
						bccMailListData.add(email);
						$(this).val('');
						isValidEmail = false;
					}
				} else {
					isValidEmail = isValidEmailBcc(email);
				}
			});

			$("#tabMenu .nav-link").click(function(event) {
				const $clickedTab = $(this);
				const tabId = $clickedTab.attr("href");

				const $activeTab = $("#tabMenu .nav-link.active");

				switch (tabId) {
					case "#leadMeeting":
						closeMeetingSection();
						break;
					case "#leadCall":
						closeCallSection();
						break;
					case "#leadTask":
						closeSection();
						break;
					case "#leadEmail":
						closeMailSection();
						break;
					case "#leadNote":
						closeNote();
						break;
				}

				$clickedTab.tab("show");

			});

			$("#quickFilter").on("keydown", function(event) {
				if (event.key === "Enter" || event.which === 13) {
					event.preventDefault();
					onQuickFilterChanged();
				}
			});

			let myRole = $("#userRole").val();
			if (myRole) {
				myRole = myRole?.replace('[', '')?.replace(']', '');
				roleArray = myRole.split(',').map(item => item.trim());
			}
			$('#leadProduct').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#skuSelect').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#frequency').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#meetingStatus').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#meetingHost').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#callOwner').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#callStatus').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#callPurpose').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#taskStatus').select2({
				placeholder: "Select",
				allowClear: true
			});
			$('#taskPriority').select2({
				placeholder: "Select",
				allowClear: true
			});
			$(".br-s-btn").hide();
			CKEDITOR.replace('itemDesc', {
				height: 150,
				removePlugins: 'wsc',
				scayt_autoStartup: true,
				scayt_maxSuggestions: 3,
				autoParagraph: false,
			});
			var dateFormat = localStorage.getItem("dateFormat");
			$("#toDateLeadCalendar").datetimepicker({
				format: dateFormat,
				closeOnDateSelect: true,
				//minDate: new Date(),
				timepicker: false,
			}).on("change", function() {
				$('#toDateLeadC').val($(this).val());
			});

			$('#toDateLeadC').blur(function() {
				$("#toDateLeadCalendar").val($(this).val());
			});

			//
			$("#fromDateLeadCalendar").datetimepicker({
				format: dateFormat,
				closeOnDateSelect: true,
				//minDate: new Date(),
				timepicker: false,
			}).on("change", function() {
				$('#fromDateLeadC').val($(this).val());
			});

			$('#fromDateLeadC').blur(function() {
				$("#fromDateLeadCalendar").val($(this).val());
			});
			const today = new Date();
			const currentYear = today.getFullYear();
			const currentMonth = today.getMonth();

			const fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
			const firstDayOfFY = new Date(fyStartYear, 3, 1);

			$("#fromDateLeadC").val(formatDatee(firstDayOfFY));
			$("#toDateLeadC").val(formatDatee(today));
			viewLeadAggridData();
		});

/* Function FOr CC and BCC Mail Start  */
function isValidEmailCc(email) {
	const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (email.trim() === '') {
		$("#errorMsg").html('<div class="sucessValidation"></div>').hide();
		return false;
	} else if (re.test(email.trim())) {
		if (isEmailComplete(email)) {
			$("#errorMsg").html('<div class="sucessValidation"></div>').show();
			return true;
		}
	} else {
		$("#errorMsg").html('<div class="formValidation"></div>').show();
		return false;
	}
}

function isValidEmailBcc(email) {
	const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (email.trim() === '') {
		$("#suggesstion-bccmailBox_").html('<div class="sucessValidation"></div>').hide();
		return false;
	} else if (re.test(email.trim())) {
		if (isEmailComplete(email)) {
			$("#suggesstion-bccmailBox_").html('<div class="sucessValidation"></div>').show();
			return true;
		}
	} else {
		$("#suggesstion-bccmailBox_").html('<div class="formValidation"></div>').show();
		return false;
	}
}

function removeErrorMsg() {
	var parentDivCc = document.getElementById("errorMsg");
	var parentDivBcc = document.getElementById("suggesstion-bccmailBox_");
	var childDivErrorCc = document.querySelector("#errorMsg .formValidation");
	var childDivSucessCc = document.querySelector("#errorMsg .sucessValidation");
	var childDivErrorBcc = document.querySelector("#suggesstion-bccmailBox_ .formValidation");
	var childDivSucessBcc = document.querySelector("#suggesstion-bccmailBox_ .sucessValidation");

	// Remove error message in CC
	if (childDivErrorCc) {
		parentDivCc.removeChild(childDivErrorCc);
	}

	// Remove success message in CC
	if (childDivSucessCc) {
		parentDivCc.removeChild(childDivSucessCc);
	}

	// Remove error message in BCC
	if (childDivErrorBcc) {
		parentDivBcc.removeChild(childDivErrorBcc);
	}

	// Remove success message in BCC
	if (childDivSucessBcc) {
		parentDivBcc.removeChild(childDivSucessBcc);
	}
	$("#suggesstion-mailBox_").html("");
}


function isEmailComplete(email) {
	return email.indexOf("@") !== -1 && email.indexOf("@") !== 0 && email.indexOf("@") !== email.length - 1;
}

function getMailsAutosearch(searchVal) {
	$.ajax({
		type: "POST",
		url: "view-crm-lead-mail-autosearch",
		dataType: "json",
		contentType: "text/plain",
		data: searchVal,
		success: function(response) {
			if (response.code === "Success") {
				handleMailSearchResults(response.body);
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching mail suggestions:", xhr.responseText || error);
		}
	});
}


function handleMailSearchResults(results) {
	if (results.length != 0) {
		$("#suggesstion-mailBox_").show();
		$("#suggesstion-mailBox_").html(generateAutocompleteMailList(results, 'selectAutocompleteMailValue'));
	} else {
		$("#suggesstion-mailBox_").show();
	}
}


function generateAutocompleteMailList(items, clickFunction) {
	var content = '<ul id="autocomplete-list1" style="margin-left: -27px;font-size: 14px!important; font-weight: 500;">';
	for (var i = 0; i < items.length; i++) {
		content += '<div class="selected-item badge rounded-pill bg-info text-dark mail-search"><li class="autocompletedata cp" onClick="' + clickFunction + '(\'' +
			items[i].name + '\')">' +
			items[i].name + '</li><span class="remove-item badge rounded-pill bg-info text-dark"></span></div>';
	}
	content += '</ul>';
	return content;
}



function selectAutocompleteMailValue(name) {


	if ($("#selected-mail-container").find('.selected-item:contains("' + name + '")').length > 0) {
		// Item is already selected, show a message 
		showSnackbar("Mail is already selected!");
		return false;
	}

	//ccMailListData.add(name);

	if (name && name.trim() !== "") {
		// Clear the Set if it's empty
		if (ccMailListData.size === 0) {
			ccMailListData.clear();
		}
		// Add the email to the Set
		ccMailListData.add(name.trim());
	}

	addEmailToContainer(name);
}

function selectAutocompleteMailValueForDraft(name) {

	//ccMailListData.add(name);
	if (name && name.trim() !== "") {
		// Clear the Set if it's empty
		if (ccMailListData.size === 0) {
			ccMailListData.clear();
		}
		// Add the email to the Set
		ccMailListData.add(name.trim());
	}

	addEmailToContainer(name);
}

function addEmailToContainer(name) {
	removeErrorMsg();

	var manuallyTypedEmail = name;

	if (manuallyTypedEmail && manuallyTypedEmail.trim() !== '' && !emailExistsInContainer(manuallyTypedEmail)) {
		$("#selected-mail-container").append('<div class="selected-item badge rounded-pill bg-info text-dark">' + manuallyTypedEmail + '<span class="remove-item badge rounded-pill bg-info text-dark" onclick="removeSelectedCcMail(this)"><i class="fas fa-times fa-lg"></i></span></div>');
	}

	$("#ccMail").val("");
	$("#suggesstion-mailBox_").hide();
}

// Function to add email to BCC container
function addEmailToBccContainer(email) {
	var manuallyTypedEmail = email;
	if (manuallyTypedEmail && manuallyTypedEmail.trim() !== '' && !bccemailExistsInContainer(manuallyTypedEmail)) {
		$("#bcc-mail-container").append('<div class="selected-item badge rounded-pill bg-info text-dark">' + email + '<span class="remove-item badge rounded-pill bg-info text-dark" onclick="removeBccEmail(this)"><i class="fas fa-times fa-lg"></i></span></div>');
	}
}

function emailExistsInContainer(email) {
	return $("#selected-mail-container").find('.selected-item:contains("' + email + '")').length > 0;
}

function bccemailExistsInContainer(email) {
	return $("#bcc-mail-container").find('.selected-item:contains("' + email + '")').length > 0;
}

function removeSelectedCcMail(element) {
	var parentDiv = element.parentNode;
	var email = parentDiv.textContent.trim();
	ccMailListData.delete(email);
	parentDiv.parentNode.removeChild(parentDiv);
}

// Function to remove BCC email
function removeBccEmail(element) {
	var parentDiv = element.parentNode;
	var email = parentDiv.textContent.trim();
	bccMailListData.delete(email);
	parentDiv.parentNode.removeChild(parentDiv);
}
/* Function FOr CC and BCC Mail End */

function viewLeadAggridData() {
	var pages;
	var pageno = 1;
	var rowData = [];
	gridOptionsLead.api.setRowData(rowData);
	var fromDate = $("#fromDateLeadC").val();
	var toDate = $("#toDateLeadC").val();

	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
	}).then(function(data) {
		console.log("lead Grid=============>", data);
		if (data.code === "Success") {
			var resp = JSON.parse(data.body);

			// Filter the data for specific leadStatus values
			var filteredData = resp.filter(function(lead) {
				return lead.leadStatus === "Contacted" ||
					lead.leadStatus === "Qualified" ||
					lead.leadStatus === "Approval Pending" ||
					lead.leadStatus === "Proposed" ||
					lead.leadStatus === "Negotiated";
			});
			console.log("Filter Data====>", filteredData);
			var len = filteredData.length;
			$('#totalReq').find('span').html(len);

			gridOptionsLead.api.setRowData(filteredData); // Set filtered data to the grid

			if (filteredData.length > 0) {
				$('#totalPageno').val(filteredData[0].totalPageno);
				pages = filteredData[0].totalPageno;
			}

		//	createPagination(pages, pageno);
		} else {
			gridOptionsLead.api.setRowData([]);
		}

		$('.loader').hide();
	});
}

function getLeadStatusHtml(value) {
	if (value === 'Qualified') {
		return `<span style="font-weight: bold;color: #4CAF50;">Qualified</span><i class="bi-check-circle" style="font-size: 12px;margin-left: 5px;color: #4CAF50"></i>`;
	} else if (value === 'Approval Pending') {
		return `<span style="font-weight: bold;color: #F29339;">Approval Pending <i class="bi-hourglass-split" aria-hidden="true"></i></span>`;
	} else if (value === 'Junk Lead' || value === 'Lost Lead') {
		return `<span style="font-weight: bold;color: #ff1240;">${value} <i class="bi-trash" aria-hidden="true"></i></span>`;
	} else if (value === 'Lead Won') {
		return `<span style="font-weight: bold;color: #e7b100;">${value} <i class="bi-trophy" aria-hidden="true"></i></span>`;
	} else if (value === 'Proposed') {
		return `<span style="font-weight: bold;color: #2196F3;">Proposed <i class="bi-lightbulb" aria-hidden="true"></i></span>`;
	} else if (value === 'Negotiated') {
		return `<span style="font-weight: bold;color: #9C27B0;">Negotiated <i class="bi-arrow-left-right" aria-hidden="true"></i></span>`;
	} else {
		return `<span>${value}</span>`;
	}
}

var columnLeadDefs = [{
	headerCheckboxSelection: false,
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
	width: 150,
	pinned: 'left',
},
{ headerName: "Lead Owner", field: "ownerName", width: 200, cellStyle: { textAlign: 'center' }, },
{ headerName: "Company", field: "company", width: 200, cellStyle: { textAlign: 'center' }, },
{
	headerName: "Lead Status",
	field: "leadStatus",
	width: 180,
	cellStyle: { textAlign: 'left' },
	cellRenderer: params => {
		const div = document.createElement("div");
		div.style.display = "flex";
		div.style.alignItems = "center";
		div.style.justifyContent = "flex-start";
		div.style.textAlign = "left";
		div.innerHTML = getLeadStatusHtml(params.value);
		return div;
	}
},
{ headerName: "Mobile Number", field: "mobile", width: 220, cellStyle: { textAlign: 'center' }, },
{ headerName: "Email", field: "email", width: 250, cellStyle: { textAlign: 'center' }, },
{ headerName: "Created Date", field: "createdDate", width: 200, cellStyle: { textAlign: 'center' }, },
{ headerName: "Lead Source", field: "leadSource", width: 200, cellStyle: { textAlign: 'center' }, },
{ headerName: "First Name", field: "fiirstName", width: 200, hide: true },
{ headerName: "Last Name", field: "lastName", width: 200, hide: true },
{ headerName: "Title", field: "leadTitle", width: 200, hide: true },
{ headerName: "Phone", field: "phone", width: 200, hide: true },
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
{
	headerName: "Lead Id",
	field: "leadId",
	width: 150

}
];

var gridOptionsLead = {
	columnDefs: columnLeadDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'single',
	pagination: true,
	paginationAutoPageSize: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10,
	},
	suppressRowClickSelection: false,
	suppressExcelExport: true,
	onSelectionChanged: onSelectionChanged,
	onGridReady: checkGridData,

};
function checkGridData() {
	let rowCount = gridOptionsLead.api.getDisplayedRowCount();
	let leadIDDD = "";
	if (rowCount === 0) {
		console.log("Grid is empty!");
		editNote(leadIDDD);
		$("#leadid").text('');
		$("#contactId").val('');
		$("#leadStatus").val('');
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4,#employeeNameTop5,#employeeNameTop6").text('');
		$(".mk-dis").prop("disabled", true);
		$("#leadStatus").prop("disabled", true);
		mailgridOptions.api.setRowData([]);
		draftgridOptions.api.setRowData([]);
		productGridOptions.api.setRowData([]);
	}
}
var leadid = "";
var contactId = "";
var ownerId = '';
function onSelectionChanged() {
	setLeadTabAsDefault();
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	if (!selectedNodes || selectedNodes.length === 0) {
		console.log("No data available in gridOptionsLead.");

		$("#leadid").text('');
		$("#contactId").val('');
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4,#employeeNameTop5,#employeeNameTop6").text('');
		$(".mk-dis").prop("disabled", true);
		$('#saveTask').removeClass('d-none');
		$('#approveLead').addClass('d-none');

		return;
	}
	var selectedData = selectedNodes.map(node => node.data);
	leadid = selectedData.map(node => node.leadId).join(",");
	ownerId = selectedData.map(node => node.ownerId).join(",");
	contactId = selectedData.map(node => node.contactId).join(",");
	let leadStatus = selectedData.map(node => node.leadStatus);
	if (!selectedNodes || selectedNodes.length === 0) {
		console.log("No data available in gridOptionsLead.");
		$("#leadid").text('');
		$("#contactId").val('');
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4,#employeeNameTop5,#employeeNameTop6").text('');
		$(".mk-dis").prop("disabled", true);
		$('#saveTask').addClass('d-none');
		$('#approveLead').addClass('d-none');
		$("#leadStatus").val('');
		getActivity("allTimeline");
		editLeadInfo(leadid);
		editNote(leadid);
		getTask(leadid);
		getCallForLead(leadid);
		getMeetingForLead(leadid);
		getMail(leadid);
		getDraft(leadid);
		viewProductOnLead(leadid);
		$("#leadStatus").prop("disabled", true);
		return;
	}

	if (leadStatus == 'Approval Pending') {
		$('#saveTask').addClass('d-none');
		const exists = accessedRole.some(item => roleArray.includes(item));
		if (exists) {
			$('#approveLead').removeClass('d-none');
		}

	} else if (leadStatus == 'Qualified') {
		$('#saveTask').addClass('d-none');
		$('#approveLead').addClass('d-none');
	} else {
		$('#saveTask').removeClass('d-none');
		$('#approveLead').addClass('d-none');
	}
	editLeadInfo(leadid);
	editNote(leadid);
	getActivity("allTimeline");
	getTask(leadid);
	getCallForLead(leadid);
	getMeetingForLead(leadid);
	getMail(leadid);
	getDraft(leadid);
	viewProductOnLead(leadid);

	$("#noteForm").hide();
	$("#contactId").val(contactId);
	$("#leadid").text(leadid);

	var selectedRows = gridOptionsLead.api.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".mk-dis").prop("disabled", leadStatus == "Qualified");
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4,#employeeNameTop5,#employeeNameTop6").text(selectedRows[0]?.leadName);
	} else {
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4,#employeeNameTop5,#employeeNameTop6").text('');
	}


}



var columnMailDefs = [{
	headerName: "Subject",
	field: "mailSubject",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1

}, {
	headerName: "Sent To",
	field: "toMail",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1

}, {
	headerName: "Sent Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1
}, {

	headerName: "CC",
	field: "ccMail",
	cellStyle: {
		textAlign: 'left'

	},
	minWidth: 100,
	flex: 1
}, {
	headerName: "BCC",
	field: "bccMail",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1
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
	onGridReady: function(params) {
		mailgridOptions.api = params.api;
		mailgridOptions.columnApi = params.columnApi;
		params.api.sizeColumnsToFit();
	},
	onGridSizeChanged: function(params) {
		params.api.sizeColumnsToFit();
	},
	suppressAutoSize: false,
	maintainColumnOrder: true
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

}, {
	headerName: "Subject",
	field: "mailSubject",
	minWidth: 100,
	flex: 1
	//pinned : 'left',
	/* width: 250, cellRenderer: function(params) {

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
	}, */

}, {
	headerName: "Sent To",
	field: "toMail",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1

}, {
	headerName: "Sent Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1
}, {
	headerName: "CC",
	field: "ccMail",
	cellStyle: {
		textAlign: 'left'

	},
	minWidth: 100,
	flex: 1
}, {
	headerName: "BCC",
	field: "bccMail",
	cellStyle: {
		textAlign: 'left'
	},
	minWidth: 100,
	flex: 1
}
];

var draftgridOptions = {
	columnDefs: columnDraftDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10,
	},
	onSelectionChanged: onSelectionChangedDraft,
	onGridReady: function(params) {
		draftgridOptions.api = params.api;
		draftgridOptions.columnApi = params.columnApi;
		params.api.sizeColumnsToFit();
	},
	onGridSizeChanged: function(params) {
		params.api.sizeColumnsToFit();
	},
	suppressAutoSize: false,
	maintainColumnOrder: true
};
function onSelectionChangedDraft() {
	var selectedNodes = draftgridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var draftId = selectedData.map(node => node.draftId);
	if (draftId.length > 0) {
		$("#editMailIcon").removeClass("d-none");
		$("#deleteDraftIcon").removeClass("d-none");
	} else {
		$("#editMailIcon").addClass("d-none");
		$("#deleteDraftIcon").addClass("d-none");
	}
}
var productDefs = [{
	headerCheckboxSelection: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	minWidth: 20, // Prevent collapsing
	maxWidth: 50,
	suppressSizeToFit: true


},
{
	headerName: "Product Name",
	field: "productName",
	width: 150,
	minWidth: 100,
	flex: 1

}, {
	headerName: "Product Id",
	field: "productId",
	hide: true,
}, {
	headerName: "SKU Name",
	field: "skuName",
	width: 150,
	minWidth: 100,
	flex: 1

}, {
	headerName: "Sku Id",
	field: "skuId",
	hide: true,
},
{
	headerName: "Description",
	field: "itemDesc",
	width: 150,
	minWidth: 100,
	flex: 1,
	cellRenderer: params => params.value
}
];

var productGridOptions = {
	columnDefs: productDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10,
		minWidth: 50,
		flex: 1
	},
	onSelectionChanged: onSelectionProduct,
	onGridReady: function(params) {
		gridOptionsDecesionMakers.api = params.api;
		gridOptionsDecesionMakers.columnApi = params.columnApi;
		params.api.sizeColumnsToFit();
	},
	onGridSizeChanged: function(params) {
		params.api.sizeColumnsToFit();
	},
	suppressAutoSize: false,
	maintainColumnOrder: true
};
function onSelectionProduct() {
	var selectedRows = productGridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$(".br-dis").prop("disabled", false);
	} else {
		$(".br-dis").prop("disabled", true);
	}
}
function updateDisabledOptions() {
	var selectedValues8 = $('#multipleMailCc').val() || [];
	var selectedValues9 = $('#multipleMailBcc').val() || [];

	// Disable options in multiple9 based on selected values in multiple8
	$('#multipleMailBcc option').each(function() {
		if (selectedValues8.includes($(this).val())) {
			$(this).attr('disabled', 'disabled');
		} else {
			$(this).removeAttr('disabled');
		}
	});

	// Disable options in multiple8 based on selected values in multiple9
	$('#multipleMailCc option').each(function() {
		if (selectedValues9.includes($(this).val())) {
			$(this).attr('disabled', 'disabled');
		} else {
			$(this).removeAttr('disabled');
		}
	});

	// Trigger chosen updates if using Chosen jQuery plugin
	$('#multipleMailCc').trigger('chosen:updated');
	$('#multiple9').trigger('chosen:updated');
}

function viewImage(id) {
	window.open("/document/crm/" + id, '_blank');
}

function updateList() {
	var input = document.getElementById('fileUploader');
	var fileContainer = document.getElementById('divFiles');

	fileContainer.innerHTML = "";

	var HTML = "";
	for (var i = 0; i < input.files.length; ++i) {
		var filename = input.files.item(i).name;
		HTML += `<div id="filename${i}">
                    ${filename} 
                    <i class="bx bx-trash" onclick="deleteDocfile(${i})" style="cursor: pointer; color: red;"></i>
                 </div>`;
	}

	fileContainer.innerHTML = HTML;
}


function deleteDocfile(id) {
	var divId = "filename" + id;
	var elementToRemove = document.getElementById(divId);

	if (elementToRemove) {
		// Remove the element
		elementToRemove.parentNode.removeChild(elementToRemove);
	} else {
		console.log("Element not found: " + divId);
	}
}

let uploadedDocument = null;

function saveMultiFileDoc1(event) {
	$(".formValidation").remove();
	var fileInput = $("#uploadDoc_1")[0].files[0];
	if (!fileInput) return;

	var fileName = event.currentTarget.value.split("\\").pop();
	var extension = fileName.split(".").pop().toLowerCase();
	var fileReader = new FileReader();
	var iURL = URL.createObjectURL(fileInput);

	fileReader.onload = function(e) {
		uploadedDocument = {
			fileName: fileName,
			documentFile: e.target.result.split(",")[1],
		};

		let iconHtml = "";
		let iconClass = "";

		if (extension === "jpg" || extension === "png" || extension === "jpeg") {
			iconClass = "fa-solid fa-file-image custom-file-icon";
		} else if (extension === "pdf") {
			iconClass = "fa-solid fa-file-pdf custom-file-icon";
		} else if (extension === "xls" || extension === "xlsx") {
			iconClass = "fa-solid fa-file-excel custom-file-icon";
		} else if (extension === "doc" || extension === "docx") {
			iconClass = "fa-solid fa-file-word custom-file-icon";
		}

		if (iconClass) {
			iconHtml = `
				<a style='margin-left: 10px' class='example-image-link' href='${iURL}' title='${fileName}' target='_blank'>
					<i class='${iconClass}'></i>
				</a>
			`;
		}

		let fileNameHtml = `
			<div id="imageName_1" class="imageName" style="margin-left: 2px;">${fileName}</div>
			<span><i class="ti-close red close_sec1 deleteFileDoc ml-5" onclick="deleteFile1();"></i></span>
		`;

		$("#uploadedBillDiv_1").html(iconHtml + fileNameHtml);
		$("#clickImg_1").removeClass("ti-plus").addClass("ti-pencil");
	};

	fileReader.readAsDataURL(fileInput);
}

function saveNote() {
	let data = {};

	data.leadStatus = $("#leadStatus").val();
	let selectedRows = gridOptionsLead.api.getSelectedRows();
	let leadId = selectedRows[0].leadId;
	data.leadId = leadId;
	data.noteId = $("#noteId").val();
	data.leadNoteId = $("#leadNoteId").val();
	data.titleId = $("#titleId").val();

	// ✅ Attach the uploaded document (if available)
	if (uploadedDocument) {
		// Ensure `documentFile` is always an array
		if (typeof uploadedDocument.documentFile === "string") {
			uploadedDocument.documentFile = [uploadedDocument.documentFile];
		}

		data.documentList = [uploadedDocument];
	} else {
		data.documentList = [];
	}

	// Debug JSON before sending
	console.log("Final Data Sent to Server:", JSON.stringify(data, null, 2));


	let isValid = true;
	if (!data.titleId) {
		toastr.error("Note Title is required");
		isValid = false;
		return false;
	}
	if (!data.noteId) {
		toastr.error("Note Subject is required");
		isValid = false;
		return false;
	}
	if (!isValid) {
		return false;

	}

	console.log("Final Data Sent to Server:", JSON.stringify(data));

	saveNewNote(data);
}
function saveNewNote(data) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-notes-ajax",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			editNote(data.leadId);
			getActivity("allTimeline");
			uploadedDocument = null;
		},
		error: function(data) {
			console.log(data);
			$(".loader").hide();
			$("body").removeClass("overlay");
		},
	});
}


function editNote(id) {
	$("#noteForm").hide();
	$("#noteData").show();
	$("#openNote").show();
	$("#saveNote").hide();
	$("#cancelNote").hide();

	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-edit-note?id=" + id,
		success: function(response) {
			const resp = JSON.parse(response.body[0]);
			console.log("note data==", resp);

			$("#noteContainer").html('');

			if (!resp.NoteDetails || !Array.isArray(resp.NoteDetails) || resp.NoteDetails.length === 0) {
				$("#noteContainer").html(`<div class="no-note-message"><i class="fa fa-info-circle"></i> Note Details Not Available</div>`);
				return;
			}

			const noteDetails = resp.NoteDetails;
			const container = document.getElementById("noteContainer");

			let hasValidNotes = false;

			noteDetails.forEach((note, index) => {
				if (!note.noteTitle.trim() && !note.noteDesc.trim()) {
					return;
				}

				hasValidNotes = true;
				let isLast = index === noteDetails.length - 1;
				let card = document.createElement("div");
				card.className = "col";

				let fileHtml = "";
				if (Array.isArray(note.documentList) && note.documentList.length > 0) {
					fileHtml += `<div id="fileTable" class="row row-cols-auto g-2">`;
					note.documentList.forEach((doc) => {
						const fileName = doc.fileName;
						const ext = fileName.split('.').pop().toLowerCase();
						let fileIcon = "";

						if (["jpg", "jpeg", "png"].includes(ext)) {
							fileIcon = `<i class="bi bi-image" style="color: blue; cursor: pointer; margin-left: 0.5rem;" onclick="viewImage('${fileName}')"></i>`;
						} else if (ext === "pdf") {
							fileIcon = `<i class="bi bi-file-earmark-pdf" style="color: red; cursor: pointer; margin-left: 0.5rem;" onclick="viewImage('${fileName}')"></i>`;
						} else {
							fileIcon = `<i class="bi bi-file-earmark" style="color: gray; cursor: pointer; margin-left: 0.5rem;" onclick="viewImage('${fileName}')"></i>`;
						}
						fileHtml += `<span class="file-name">${fileName}</span> <span>${fileIcon}</span>`;
					});
					fileHtml += `</div>`;
				}

				card.innerHTML = `       
                    <div class="note-card fs--13">	
                        <a href="javascript:void(0);" class="note-icon" onclick="openNote('${note.noteId}')">
                            <i class="fa fa-pen"></i>
                        </a>
                        <div class="mb-2">
                            <span class="fw-bold">Title:</span> <span>${note.noteTitle || 'N/A'}</span>
                        </div>
                        <div class="mb-2">
                            <span class="fw-bold">Note:</span> <span class="content-control">${note.noteDesc || 'N/A'}</span>
                        </div>
                        <div class="mb-2 position-absolute bottom-0 d-inline-flex gap-2">
                            <span class="fw-bold">Attachments:</span>
                            ${fileHtml || "No Attachments"}
                        </div>
                    </div>`;

				container.appendChild(card);
			});

			if (!hasValidNotes) {
				container.innerHTML = `<div class="no-note-message"><i class="fa fa-info-circle"></i> Note Details Not Available</div>`;
			}
		},
		error: function(err) {
			console.error("Error fetching note details:", err);
		}
	});
}


/*function getEmptyUploaderHtml() {
	return `
		<tr>
			<td>
				<div class="control-group position-r">
					<label class="custom-file-upload" for="fileUploader" id="uploadFor_0">
						<i class="ti-plus"></i>
					</label>
					<div class="controls">
						<input type="file" class="document" id="fileUploader" name="userImage" multiple onchange="updateList()"/>
					</div>
				</div>
				<div id="divFiles">
					<input type="hidden" id="uploadHidden_" class="uploadHidCls"/>
				</div>
			</td>
		</tr>`;
}*/


function closeNote() {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	leadid = selectedData.map(node => node.leadId).join(",");
	editNote(leadid);
	$("#noteForm").hide();
	$("#noteData").show();
	$("#openNote").show();
	$("#saveNote").hide();
	$("#cancelNote").hide();
	$('#uploadDoc_1').val('');
	$('#imageName_1').empty();
	$('#uploadedBillDiv_1').empty();
	uploadedDocument = null;
	$('#prevNote').prop('disabled', false);
	$('#nextMeeting').prop('disabled', false);
}
function openNote1() {
	$('#prevNote').prop('disabled', true);
	$('#nextMeeting').prop('disabled', true);
	$("#noteForm").show();
	$("#noteData").hide();
	$('#leadNoteId').val('');
	$('#titleId').val('');
	$('#noteId').val('');
	$('#leadNoteId').val('');
	/*$('#documentName').val('');
	$('#docListlen').val(0);
	$("#divFiles").html('');*/
	/*let uniqueIndex = new Date().getTime();
	$("#doctbodyData").html(getEmptyUploaderHtml(uniqueIndex));*/
	$("#saveNote").show();
	$("#cancelNote").show();
	$("#openNote").hide();
	$('#uploadDoc_1').val('');
	$('#imageName_1').empty();
	$('#uploadedBillDiv_1').empty();
	var docDet = `<div class="control-group">
														<label class="custom-file-upload" for="uploadDoc_1"> 
															<i class="ti-plus"></i>
														</label>
														<div class="controls">
															<input type="file" class="document" id="uploadDoc_1"
																accept=".jpeg, .jpg, .png, .pdf" name="userImage"
																onchange="saveMultiFileDoc1(event)" />
														</div>
													</div>
													<input type="hidden" id="uploadHidden1" class="uploadHidCls">
													<div id="uploadedBillDiv_1" class="uploadedBillCls order-3"></div>
													<div id="imageName_1" class="imageName"></div>
													<div id="validationDiv1"></div>`;
	$("#documentDiv").html(docDet);
}

function openNote(id) {
	$("#saveNote").show();
	$("#cancelNote").show();
	$("#openNote").hide();
	$("#noteForm").show();
	$("#noteData").hide();
	$('#prevNote').prop('disabled', true);
	$('#nextMeeting').prop('disabled', true);


	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-editNoteDataByNoteId?id=" + id,
		success: function(response) {
			try {
				const resp = JSON.parse(response.body[0] || '{}');
				console.log("Parsed Response:", resp);

				if (!resp.NoteDetails || resp.NoteDetails.length === 0) {
					console.log("No NoteDetails found, resetting fields.");
					$('#leadNoteId').val('');
					$('#titleId').val('');
					$('#noteId').val('');
					$('#documentName').val('');
					$('#docListlen').val(0);
					$("#divFiles").html('');
					$("#doctbodyData").html(getEmptyUploaderHtml());
					return;
				}

				const noteDetails = resp.NoteDetails[0];
				console.log('Note Details:', noteDetails);

				$('#leadNoteId').val(noteDetails.noteId || '');
				$('#titleId').val(noteDetails.noteTitle || '');
				$('#noteId').val(noteDetails.noteDesc || '');
				$('#documentName').val(noteDetails.noteDocName || '');

				var docDet = `<div class="control-group">
                                <label class="custom-file-upload" for="uploadDoc_1"> 
                                    <i class="ti-plus"></i>
                                </label>
                                <div class="controls">
                                    <input type="file" class="document" id="uploadDoc_1"
                                        accept=".jpeg, .jpg, .png, .pdf" name="userImage"
                                        onchange="saveMultiFileDoc1(event)" />
                                </div>
                            </div>
                            <input type="hidden" id="uploadHidden1" class="uploadHidCls">
                            <div id="uploadedBillDiv_1" class="uploadedBillCls order-3"></div>
                            <div id="imageName_1" class="imageName"></div>
                            <div id="validationDiv1"></div>`;
				$("#documentDiv").html(docDet);

				if (noteDetails.documentList && noteDetails.documentList.length > 0) {
					let fileUrl = noteDetails.documentList[0].fileUrl;
					let fileName = noteDetails.documentList[0].fileName;
					uploadedDocument = {  // Ensure uploadedDocument is populated when editing
						fileName: fileName,
						documentFile: fileUrl
					};
					if (fileName) {
						$("#imageName_1").html(fileName);

						let fileIcon = `<div class ="d-flex gap-2">
                                            <a class="example-image-link uploadicon m-0 p-0" target="_blank">
                                                <i class="fa-solid fa-file" style="color: blue" onclick=viewImage('${fileName}')></i>
                                            </a>
                                            <span class="uploadicon m-0 p-0"><i class="ti-close red" onclick="deleteFile1();"></i></span>
                                        </div>`;

						$("#uploadedBillDiv_1").html(fileIcon);
					}
				}

			} catch (err) {
				console.error("Error processing response:", err);
			}
		},
		error: function(err) {
			console.error("Error fetching note details:", err);
		}
	});
}
function deleteFile1() {
	$('#fileUpload').val("");
	$('#imgemp').attr('src', '');
	$('#imgemp').attr('src', '../assets/images/noimage.jpg');

	$('#uploadDoc_1').empty();
	$('#imageName_1').empty();
	$('#uploadedBillDiv_1').empty();
	$("#imageName_1").html("");
	uploadedDocument = null;
}


var selectedStatusValue = '';

function editLeadInfo(id) {
	closeSection();

	$.ajax({
		type: "GET",
		url: "view-crm-leads-editDetails?id=" + id,
		success: function(response) {
			if (response.code === "Success") {
				console.log('response------For Lead', response);

				$('#leadId').val(response.body[0].leadId);
				$('#leadExecutive').val(response.body[0].leadOwner);
				$('#company').val(response.body[0].company);
				$('#firstName').val(response.body[0].firstName);
				$('#lastName').val(response.body[0].lastName);
				$('#title').val(response.body[0].title);
				$('#email').val(response.body[0].email);
				$('#phone').val(response.body[0].phone);
				$('#projectId').val(response.body[0].fax);
				$('#project').val(response.body[0].projectName);
				$('#mobile').val(response.body[0].mobile);
				$('#website').val(response.body[0].website);

				// Re-enable and clean status dropdown
				let $leadStatus = $('#leadStatus');
				$leadStatus.prop("disabled", false).removeAttr("style");

				selectedStatusValue = response.body[0].leadStatus;

				// Map of hidden statuses with display labels
				const hiddenStatusMap = {
					"TLSM00010": "Approval Pending",
					"TLSM00004": "Qualified",
					"TLSM00005": "Proposed",
					"TLSM00006": "Negotiated"
				};
				// Clean up previously appended statuses
				$("#leadStatus option[value='TLSM00005'], \
				  #leadStatus option[value='TLSM00006'], \
				  #leadStatus option[value='TLSM00010']").remove();

				if (selectedStatusValue === "TLSM00010") {
					if ($("#leadStatus option[value='TLSM00010']").length === 0) {
						$('#leadStatus').append(`<option value="TLSM00010">Approval Pending</option>`);
					}
					$('#leadStatus').val(selectedStatusValue).prop("disabled", true);
				} else if (selectedStatusValue === "TLSM00004") {
					if ($("#leadStatus option[value='TLSM00004']").length === 0) {
						$('#leadStatus').append(`<option value="TLSM00004">Qualified</option>`);
					}
					$('#leadStatus').val(selectedStatusValue).prop("disabled", true);
				} else if (selectedStatusValue === "TLSM00005" || selectedStatusValue === "TLSM00006") {
					if ($("#leadStatus option[value='" + selectedStatusValue + "']").length === 0) {
						const label = selectedStatusValue === "TLSM00005" ? "Proposed" : "Negotiated";
						$('#leadStatus').append(`<option value="${selectedStatusValue}">${label}</option>`);
					}
					$('#leadStatus').val(selectedStatusValue).prop("disabled", true);
				} else {
					$('#leadStatus').val(selectedStatusValue).prop("disabled", false).trigger('change');
				}

				// Append and disable dropdown if hidden status is selected
				if (hiddenStatusMap[selectedStatusValue]) {
					if ($leadStatus.find("option[value='" + selectedStatusValue + "']").length === 0) {
						$leadStatus.append(`<option value="${selectedStatusValue}">${hiddenStatusMap[selectedStatusValue]}</option>`);
					}
					$leadStatus.val(selectedStatusValue).prop("disabled", true);
				} else {
					$leadStatus.val(selectedStatusValue).trigger('change');
				}
			}
		}
	});
}


function toggleFrequencyFields(val) {
	$("#meetingFromDate").val("");
	$("#meetingToDate").val("");
	$("#meetingFromTime").val("");
	$("#meetingToTime").val("");
	$(".day").removeClass("selected");
	$(".date-item").removeClass("selected");
	$("#selected-dates").html("");

	const days = document.querySelectorAll(".day");

	// Remove existing event listeners by cloning elements
	days.forEach(day => {
		const newDay = day.cloneNode(true);
		day.parentNode.replaceChild(newDay, day);
	});
	const updatedDays = document.querySelectorAll(".day");
	const frequency = $("#frequency").val();
	if (frequency === "monthly-same-day") {
		updatedDays.forEach(day => {
			day.addEventListener("click", function() {
				updatedDays.forEach(d => d.classList.remove("selected"));
				day.classList.add("selected");
			});
		});
	} else if (frequency === "weekly") {
		updatedDays.forEach(day => {
			day.addEventListener("click", function() {
				day.classList.toggle("selected");
			});
		});
	}

	if (!val) {
		$("#meetingFromDateDiv").addClass("d-none");
		$("#meetingToDateDiv").addClass("d-none");
		$("#fromToDateFields").addClass("d-none");
		$("#weakSelect").addClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
		return;
	}

	if (val == 'one-time') {
		$("#meetingToDateDiv").addClass("d-none");
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingFromDateLabel").html(`Meeting Date <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").addClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
	} else if (val == 'daily') {
		$("#meetingToDateDiv").removeClass("d-none");
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingToDateLabel").html(`Meeting Until <span class="text-danger">*</span>`);
		$("#meetingFromDateLabel").html(`Meeting Starts <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").addClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
	} else if (val == 'weekly') {
		$("#meetingToDateDiv").removeClass("d-none");
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingToDateLabel").html(`Meeting Until <span class="text-danger">*</span>`);
		$("#meetingFromDateLabel").html(`Meeting Starts <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").removeClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
	} else if (val == 'monthly-same-date') {
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingFromDateLabel").html(`Start Date <span class="text-danger">*</span>`);
		$("#meetingToDateDiv").removeClass("d-none");
		$("#meetingToDateLabel").html(`End Date <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").addClass("d-none");
		$("#monthlyDatePicker").removeClass("d-none");
	} else if (val == 'monthly-same-day') {
		$("#meetingToDateDiv").removeClass("d-none");
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingToDateLabel").html(`End Date <span class="text-danger">*</span>`);
		$("#meetingFromDateLabel").html(`Start Date <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").removeClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
	} else if (val == 'monthly-last-working-day') {
		$("#meetingToDateDiv").removeClass("d-none");
		$("#meetingFromDateDiv").removeClass("d-none");
		$("#meetingToDateLabel").html(`End Date <span class="text-danger">*</span>`);
		$("#meetingFromDateLabel").html(`Start Date <span class="text-danger">*</span>`);
		$("#fromToDateFields").removeClass("d-none");
		$("#weakSelect").addClass("d-none");
		$("#monthlyDatePicker").addClass("d-none");
	}

	/* const frequency = document.getElementById('frequency').value;
	document.getElementById('weakSelect').style.display = (frequency === 'weekly') ? 'block' : 'none'; */



}

function toggleModeFields() {
	const onlineMode = document.getElementById('onlineMode').checked;
	document.getElementById('onlineFields').style.display = onlineMode ? 'block' : 'none';
	document.getElementById('offlineFields').style.display = onlineMode ? 'none' : 'block';
}

document.addEventListener('DOMContentLoaded', function() {
	const today = new Date().toISOString().split("T")[0];
	document.getElementById('meetingFromDate').setAttribute('min', today);
	document.getElementById('meetingToDate').setAttribute('min', today);

	const fromDate = document.getElementById('meetingFromDate');
	const fromTime = document.getElementById('meetingFromTime');
	const toDate = document.getElementById('meetingToDate');
	const toTime = document.getElementById('meetingToTime');

	fromDate.addEventListener('change', () => {
		toDate.setAttribute('min', fromDate.value);
	});

	fromTime.addEventListener('change', () => {
		if (fromDate.value === toDate.value) {
			toTime.setAttribute('min', fromTime.value);
		} else {
			toTime.removeAttribute('min');
		}
	});

	toDate.addEventListener('change', () => {
		if (fromDate.value === toDate.value) {
			toTime.setAttribute('min', fromTime.value);
		} else {
			toTime.removeAttribute('min');
		}
	});
});

document.addEventListener("DOMContentLoaded", function() {
	const days = document.querySelectorAll(".day");
	const selectedList = document.getElementById("selected-list");

	days.forEach(day => {
		day.addEventListener("click", function() {
			day.classList.toggle("selected");
			updateSelectedDays();
		});
	});

	function updateSelectedDays() {
		const selectedDays = document.querySelectorAll(".day.selected");
		selectedList.innerHTML = "";

		selectedDays.forEach(day => {
			const listItem = document.createElement("li");
			listItem.textContent = day.dataset.day;
			selectedList.appendChild(listItem);
		});
	}

	const today = new Date().toISOString().split('T')[0]; // Get current date in YYYY-MM-DD format
	document.getElementById('repeatUntil').setAttribute('min', today);
});
$(document).ready(function() {
	const dateContainer = document.querySelector('.date-items');
	const selectedDatesDisplay = document.getElementById('selected-dates');
	const scrollLeftBtn = document.getElementById('scroll-left');
	const scrollRightBtn = document.getElementById('scroll-right');

	let scrollAmount = 0;
	const scrollStep = 150; // Reduced scroll step for compact design

	// Generate date items (1-31)
	for (let i = 1; i <= 31; i++) {
		const dateItem = document.createElement('div');
		dateItem.classList.add('date-item');
		dateItem.textContent = i.toString().padStart(2, '0'); // Add leading zero
		dateItem.addEventListener('click', () => {
			dateItem.classList.toggle('selected'); // Toggle selection
			updateSelectedDates();
		});
		dateContainer.appendChild(dateItem);
	}

	function updateSelectedDates() {
		const selectedItems = document.querySelectorAll('.date-item.selected');
		const selectedNumbers = Array.from(selectedItems).map(item => item.textContent);
		selectedDatesDisplay.textContent = selectedNumbers.length > 0 ? selectedNumbers.join(',') : 'None';
	}

	// Scroll left function
	scrollLeftBtn.addEventListener('click', () => {
		scrollAmount -= scrollStep;
		if (scrollAmount <= 0) {
			scrollAmount = 0;
			scrollLeftBtn.disabled = true;
		}
		scrollRightBtn.disabled = false;
		dateContainer.style.transform = `translateX(-${scrollAmount}px)`;
	});

	// Scroll right function
	scrollRightBtn.addEventListener('click', () => {
		const containerWidth = document.querySelector('.date-picker-container').clientWidth;
		const maxScroll = dateContainer.scrollWidth - containerWidth;
		scrollAmount += scrollStep;
		if (scrollAmount >= maxScroll) {
			scrollAmount = maxScroll;
			scrollRightBtn.disabled = true;
		}
		scrollLeftBtn.disabled = false;
		dateContainer.style.transform = `translateX(-${scrollAmount}px)`;
	});
});

function getSelectedDays() {
	const selectedDays = Array.from(document.querySelectorAll('.week-picker .day.selected'))
		.map(day => day.getAttribute('data-day'));

	console.log(selectedDays);
	return selectedDays;
}
function setLeadTabAsDefault() {
	document.querySelectorAll('.nav-link').forEach((link) => {
		link.classList.remove('active');
		link.setAttribute('aria-selected', 'false');
	});
	const leadTab = document.querySelector('#contactStatusTab-tab a.nav-link');
	if (leadTab) {
		leadTab.classList.add('active');
		leadTab.setAttribute('aria-selected', 'true');
	} else {
		console.error('Lead tab element not found.');
		return;
	}

	document.querySelectorAll('.tab-pane').forEach((tab) => {
		tab.classList.remove('show', 'active');
	});
	const leadNoteTab = document.querySelector('#contactStatusTab');
	if (leadNoteTab) {
		leadNoteTab.classList.add('show', 'active');
	} else {
		console.error('Lead Note tab content pane not found.');
	}
}

function getTask(leadId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-task?id=" + leadId,
		success: function(response) {
			if (response.message == "Success") {
				// Clear existing content
				$("#taskActivity").empty();
				$("#closedTaskActivity").empty();

				let countOpen = 0, countClose = 0;

				response.body.forEach(task => {
					const { taskId, taskOwner, taskSubject, dueDate, taskPriority, taskStatus } = task;

					// Create task card (Always Clickable)
					let taskCard = `
						<div class="card-task clickable-card" onclick="editPage('${taskId}')" style="cursor: pointer;">
							<div class="task-header">
								${taskSubject}
							</div>
							<div class="task-info">
								<span>${dueDate}</span> | <i class="bi bi-person-fill"></i> ${taskOwner}
							</div>
							<p>Status: ${taskStatus}</p>
							<p>Priority: ${taskPriority}</p>
						</div>`;

					if (taskStatus !== "Completed") {
						$("#taskActivity").append(taskCard);
						countOpen++;
					} else {
						$("#closedTaskActivity").append(taskCard);
						countClose++;
					}
				});
				if (countOpen === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#taskActivity").append(noRecord);
				}
				if (countClose === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#closedTaskActivity").append(noRecord);
				}
				// Update counts
				$("#openTasks").text(countOpen);
				localStorage.setItem("countOpen", countOpen);
				$("#closeTasks").text(countClose);
				localStorage.setItem("countClose", countClose);

				getCount(leadId);

			}
		}
	});
}


function editPage(id) {
	toggleSection();
	$("#deleteTaskIcon").removeClass("d-none");
	$.ajax({
		type: "GET",
		url: "view-crm-tasks-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log(response);
			if (response.message == "Success") {
				$('#campaignOwner').val(userId);
				var dueDate = response.body[0].dueDate;
				dueDate = dueDate.split(' ')[0];
				$("#taskStatus option[value='Completed']").prop("disabled", false);
				var reminderDateid = response.body[0].reminderDateid;
				reminderDateid = reminderDateid.split(' ')[0];

				$("#taskId").text(response.body[0].taskId);
				console.log("taskId-------------------" + response.body[0].taskOwner);
				$("#taskOwner").val(response.body[0].taskOwner);
				$("#taskSubject").val(response.body[0].taskSubject);
				$('#dueDate').val(dueDate);
				$('#taskStatus').val(response.body[0].taskStatus).trigger('change');
				let taskStatus = response.body[0].taskStatus;
				if (taskStatus == "Completed") {
					disableTaskFields();
				} else {
					enableTaskFields();
				}
				$('#taskPriority').val(response.body[0].taskPriority).trigger('change');
				$('#description').val(response.body[0].description);

				var tskLead = response.body[0].taskLead;
				var conTsk = response.body[0].contactId;

				if (tskLead == null || tskLead == "") {
					$("#relatedMeetingTo").val('Contact').prop('disabled', true).css('background-color', '#e9ecef');
					$('#contactDiv').show();
					$('#accountDiv').show();
					$('#leadDiv').hide();
					$("#contactName").prop('disabled', true).css('background-color', '#e9ecef');

					var name = response.body[0].taskContactName;
					var ContactId = response.body[0].contactId;

					selectAutocompleteContact(name, ContactId, '')
				}
				else if (conTsk == null || conTsk == "") {

					$("#relatedMeetingTo").val('Lead').prop('disabled', true).css('background-color', '#e9ecef');
					$('#leadDiv').show();
					$('#contactDiv').hide();
					$('#accountDiv').hide();
					$("#taskLead").val(response.body[0].taskLead).prop('disabled', true).css('background-color', '#e9ecef');
				}

				var repeateYesOrNo = response.body[0].repeateYesOrNo;
				var reminderYesOrNo = response.body[0].reminderYesOrNo;
				$('input[name="ReminderYesOrNo"][value="' + reminderYesOrNo + '"]').prop('checked', true);
				$('#description').val(response.body[0].description);
				//for stage active status

			}
		}
	})
}
function disableTaskFields() {
	$("#saveTaskIcon,#deleteTaskIcon,#taskSubject, #dueDate,#taskStatus,#taskPriority,#description").prop("disabled", true);
	$(".discussion-btn-bg, #weakSelect .day, .date-items .date-item").addClass("disabled");
}

function enableTaskFields() {
	$("#saveTaskIcon,#deleteTaskIcon,#taskSubject, #dueDate,#taskStatus,#taskPriority,#description").prop("disabled", false);
	$(".discussion-btn-bg, #weakSelect .day, .date-items .date-item").removeClass("disabled");
}
function deleteTaskOnclick() {
	var deleteId = $("#taskId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-tasks-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				console.log(response);
				toastr.success("Task Deleted Successfully")
				closeSection();
				var selectedRows = gridOptionsLead.api.getSelectedRows();
				if (selectedRows.length > 0) {
					var leadId = selectedRows[0].leadId;
					console.log("Selected Lead ID:", leadId);
					getTask(leadId);
					getActivity("allTimeline");
				} else {
					console.log("No row selected");
				}
			}
		}

	});
}
function addTaskInfo() {

	var obj = {};
	var selectedRows = gridOptionsLead.api.getSelectedRows();
	var leadId = selectedRows[0].leadId;
	obj.leadId = leadId;
	obj.taskId = $('#taskId').text();
	obj.taskOwner = $('#taskOwner').val();
	obj.taskLead = $('#taskLead').val();
	obj.pageType = "Lead";
	obj.taskSubject = $('#taskSubject').val();
	obj.dueDate = $('#dueDate').val();
	obj.taskContactName = $('#contactName').val();
	obj.contactId = $('#contactId').val();
	obj.taskAccountName = $('#dealAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.taskStatus = $('#taskStatus').val();
	obj.taskPriority = $('#taskPriority').val();

	var ReminderYesOrNo = $("input[name='ReminderYesOrNo']:checked").val();
	obj.reminderYesOrNo = ReminderYesOrNo;

	var RepeateYesOrNo = $("input[name='RepeateYesOrNo']:checked").val();
	//alert(RepeateYesOrNo);
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

	console.log("Printed Data" + JSON.stringify(obj));
	/* FORM VALIDATION STARTS*/

	var validation = true;

	if (obj.taskSubject == null || obj.taskSubject == "") {
		toastr.error("Task Subject Required");
		return false;
	}
	if (obj.dueDate == null || obj.dueDate == "") {
		toastr.error("Due Date Required");
		return false;
	}
	if (obj.taskStatus == null || obj.taskStatus == "") {
		toastr.error("Task Status Required");
		return false;
	}
	if (obj.taskPriority == null || obj.taskPriority == "") {
		toastr.error("Task Priority Required");
		return false;
	}


	/* FORM VALIDATION ENDS*/

	if (validation) {

		$.ajax({
			type: "POST",
			url: "view-crm-tasks-add-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.message == "Success") {
					toastr.success('Task Saved Successfully');
					//cancelBtn();
					closeSection();
					var selectedRows = gridOptionsLead.api.getSelectedRows();
					if (selectedRows.length > 0) {
						var leadId = selectedRows[0].leadId;
						console.log("Selected Lead ID:", leadId);
						getTask(leadId);
						getMail(leadid);
						getActivity("allTimeline");
					} else {
						console.log("No row selected");
					}

				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

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
/* Call Section Start */
function toggleSection() {
	enableTaskFields();
	$(".formValidation").remove();
	$("#taskLead").prop('disabled', false);
	$("#taskFields").removeClass("d-none");
	$("#saveTaskIcon").removeClass("d-none");
	$("#openContainer").addClass("d-none");
	$("#addTaskIcon").addClass("d-none");
	$("#closeContainer").addClass("d-none");
	$("#closeIcon").removeClass("d-none");
	var selectedRows = gridOptionsLead.api.getSelectedRows();
	var leadId = selectedRows[0].leadId;
	//var leadId = $("#leadId").text();
	$("#taskId").text('');
	$('#taskLead').val(leadId);
	$('#taskSubject').val('');
	$('#dueDate').val('');
	$('#taskStatus').val('').trigger('change');
	$('#taskPriority').val('').trigger('change');
	$('#description').val('');
	$("#taskStatus option[value='Completed']").prop("disabled", true);

	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadName = selectedData.map(node => node.leadName);
	$("#taskLead").val(leadName);
	$('#prevTask').prop('disabled', true);
	$('#nextTask').prop('disabled', true);
}
function closeSection() {
	$("#deleteTaskIcon").addClass("d-none");
	$("#taskFields").addClass("d-none");
	$("#openContainer").removeClass("d-none");
	$("#closeContainer").removeClass("d-none");
	$("#closeIcon").addClass("d-none");
	$("#addTaskIcon").removeClass("d-none");
	$("#saveTaskIcon").addClass("d-none");
	$(".br-dis").prop("disabled", true);
	$('#prevTask').prop('disabled', false);
	$('#nextTask').prop('disabled', false);
}

function toggleCallSection() {
	enableCallFields();
	$("#closeCallIcon").removeClass("d-none");
	$("#callFields").removeClass("d-none");
	$("#saveCallIcon").removeClass("d-none");
	$("#addCallIcon").addClass("d-none");
	$("#openCallContainer").addClass("d-none");
	$("#closeCallContainer").addClass("d-none");
	$('div.formValidation').remove();
	$('#myModalAddCall').modal('show');
	$('#callSubject').val('');

	$('#callType').val('Outbound');
	$('#callStatus').val('Scheduled').trigger('change');
	$('#callStartDate').val('');
	$('#callPurpose').val('').trigger('change');
	$('#callAgenda').val('');
	$('#callRemark').val('');
	//var contactId = $("#contactId").val();
	var userId = $("#userId").val();
	$('#callOwner').val(userId).trigger('change');
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadName = selectedData.map(node => node.leadName);
	var leadMail = selectedData.map(node => node.email);
	var contactId = selectedData.map(node => node.contactId);
	$("#leadMail").val(leadMail);
	$('#callLeadName').val(leadName);
	//$('#contactName').val(leadName);
	///$('#taskAccount1').val(accountName);
	//$('#accountId').val(accountId);
	$('#contactId').val(contactId);
	$("#callToWhom").val('').prop("disabled", false);
	//$("#leadName").val('').prop("disabled", false);
	$("#selectParticipents").prop('disabled', false);
	$("#relatedType").val('');
	$("#relatedName").val('');
	$("#callType").val('Outbound');
	$("#callStatus").val('Scheduled').trigger('change');
	$("#callEndTime").val('');
	$("#callStartTime").val('');
	$("#callSubject").val('');
	$("#callReminder").val('');
	$("#callAgenda").val('');
	$(".inviteMeeting relatedMeetingTo1").val('');
	$(".selected-excutives-container .selected-item").empty();
	$(".inviteMeeting.relatedMeetingTo1").val('clearOptions');
	$(".leadNameCls1").hide();
	$(".contactNameCls1").hide();
	$(".executiveNameCls1").hide();
	$("#callStatus option[value='Completed']").prop("disabled", true);
	// var contactId=selectedRows[0].contactId;
	if (contactId && selectedData.length > 0 && selectedData[0].leadStatus === "Qualified") {
		$("#callToWhom").val('Contact').prop("disabled", true);
	} else {
		$("#callToWhom").val('Lead').prop("disabled", true);
	}
	if ($("#callToWhom").val() === 'Lead') {
		$(".leadNameCls").show();
		$("#contactDiv").hide();
		$("#accountDiv").hide();
	} else if ($("#callToWhom").val() === 'Contact') {
		$("#contactDiv").show();
		$(".leadNameCls").hide();
		$("#accountDiv").show();
	} else {

		$(".leadNameCls").hide();
		$("#contactDiv").hide();
		$("#accountDiv").hide();
	}
	$('#callId').text('');
	$('#leadId').val(leadid);
	$('#prevCall').prop('disabled', true);
	$('#nextCall').prop('disabled', true);
}
function closeCallSection() {
	$("#closeCallIcon").addClass("d-none");
	$("#callFields").addClass("d-none");
	$("#addCallIcon").removeClass("d-none");
	$("#openCallContainer").removeClass("d-none");
	$("#closeCallContainer").removeClass("d-none");
	$("#deleteCallIcon").addClass("d-none");
	$("#saveCallIcon").addClass("d-none");
	$('#leadId').val('');
	$('#prevCall').prop('disabled', false);
	$('#nextCall').prop('disabled', false);
}

function generateCallCard(call) {
	const { callId, callSubject, callStartDate, callStartTime, callEndTime, callOwner, callStatus } = call;
	console.log(`Generating call card for Call ID: ${callId}`);

	// Create the call card (Always Clickable)
	return `
	        <div class="card-task clickable-card" onclick="editCallPage('${callId}')" style="cursor: pointer;">
	            <div class="task-header">
	                <span class="toptxt">${callSubject}</span>
	            </div>
	            <div class="task-info">
	                <span>Call Scheduled for: ${callStartDate} ${callStartTime} - ${callEndTime}</span>
	            </div>
	            <div class="task-info">
	                <i class="bi bi-person-fill"></i> ${callOwner}
	            </div>
	            <p class="task-info">Status: ${callStatus}</p>
	        </div>`;
}

function getCallForLead(leadId) {
	$.ajax({
		type: "GET",
		url: `view-crm-leads-view-detail-call?id=${leadId}`,
		success: function(response) {
			if (response.message === "Success") {
				$("#callActivity").empty();
				$("#closedCallActivity").empty();

				let countOpenC = 0, countCloseC = 0;

				response.body.forEach(call => {
					const cardHTML = generateCallCard(call);

					if (call.callStatus !== "Completed") {
						$("#callActivity").append(cardHTML);
						countOpenC++;
					} else {
						$("#closedCallActivity").append(cardHTML);
						countCloseC++;
					}
				});
				if (countOpenC === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#callActivity").append(noRecord);
				}
				if (countCloseC === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#closedCallActivity").append(noRecord);
				}

				// Update counts
				$("#openCalls").text(countOpenC);
				localStorage.setItem("countOpenC", countOpenC);
				$("#closeCalls").text(countCloseC);
				localStorage.setItem("countCloseC", countCloseC);
				getCount(leadId); // Fixed typo (leadId instead of leadid)
			}
		}
	});
}

function getParticipantLead(searchVal, dropdown) {

	let val = $(".inviteMeeting").val()


	if (searchVal == "") {
		$(".suggesstion-boxmeetingLead1_").hide();
	}
	let searchId = '';
	getNameListParticipants1(searchId, val, searchVal, "lead");
}

function getParticipantContact(searchVal, dropdown) {

	let val = $(".inviteMeeting").val()

	if (searchVal == "") {
		$(".suggesstion-boxmeetingContact1_").hide();
	}


	let searchId = '';
	getNameListParticipants1(searchId, val, searchVal, "contact");
}

function getParticipantExcecutive(searchVal, dropdown) {


	let val = $(".inviteMeeting").val()

	if (searchVal == "") {
		$(".suggesstion-boxmeetingExcutives_").hide();
	}
	let searchId = userId;
	getNameListParticipants1(searchId, val, searchVal, "excutive");
}
function addCallInfo() {

	var executiveSelect = $("#callOwner");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';

	/* if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	} */

	let toMail = '';
	var leadMail = $('#leadMail').val();
	var contactMailId = $('#contactMail').val();

	if (leadMail !== null && leadMail !== "") {

		toMail = leadMail;
	} else if (contactMailId !== null && contactMailId !== "") {

		toMail = contactMailId;
	}



	var id = $("#callToWhom").val();
	var obj = {};
	obj.callId = $('#callId').text();
	obj.callToWhom = $('#callToWhom').val();
	obj.leadName = $('#callLeadName').val();
	obj.contactName = $('#callContactName').val();
	obj.contactId = $('#contactId').val();
	if (obj.contactId) {
		obj.pageType = "Contact";
		obj.leadId = "";
	} else {
		obj.pageType = "Lead";
		var selectedRows = gridOptionsLead.api.getSelectedRows();
		var leadId = selectedRows[0].leadId;
		obj.leadId = leadId;
	}
	obj.relatedType = $('#relatedType').val();
	obj.relatedName = $('#relatedName').val();
	obj.relatedId = $('#relatedId').val();
	obj.callType = $('#callType').val();
	obj.callStatus = $('#callStatus').val();
	obj.callStartDate = $('#callStartDate').val();
	obj.callStartTime = $('#callStartTime').val();
	obj.callEndTime = $('#callEndTime').val();

	obj.callOwner = $('#callOwner').val();
	obj.callSubject = $('#callSubject').val();
	obj.callReminder = $('#callReminder').val();
	obj.callPurpose = $('#callPurpose').val();
	obj.callAgenda = $('#callAgenda').val();
	obj.accountName = $('#dealAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.callRemark = $('#callRemark').val();
	//obj.participantId = JSON.stringify(participantData);
	obj.toMail = toMail;
	//obj.ccMail = ccMeetingMail;
	obj.excutiveMail = executiveMail;
	console.log(obj); //return false;

	console.log("Prepared Object:", obj);
	var callStatus = $('#callStatus').val();
	var callRemark = $('#callRemark').val();
	// FORM VALIDATION STARTS
	var validation = true;

	if (!obj.callToWhom) {
		toastr.error("Call To Whom is required");
		validation = false;
		return;
	}
	if (!obj.callSubject) {
		toastr.error("Subject is required");
		validation = false;
		return;
	}
	if (!obj.callType) {
		toastr.error("Call Type is required");
		validation = false;
		return;
	}
	if (!obj.callStatus) {
		toastr.error("Status is required");
		validation = false;
		return;
	}
	if (!obj.callPurpose) {
		toastr.error("Call Purpose is required");
		validation = false;
		return;
	}
	if (!obj.callStartDate) {
		toastr.error("Start Date is required");
		validation = false;
		return;
	}
	if (!obj.callStartTime) {
		toastr.error("Start Time is required");
		validation = false;
		return;
	}
	if (!obj.callOwner) {
		toastr.error("Call Executive is required");
		validation = false;
		return;
	}
	if (!obj.callEndTime) {
		toastr.error("End Time is required");
		validation = false;
		return;
	}

	if (!obj.callAgenda) {
		toastr.error("Call Agenda is required");
		validation = false;
		return;
	}
	if (callStatus !== "" && (callStatus === "Deferred" || callStatus === "Completed")) {
		if (!callRemark) {
			toastr.error("Remark is required");
			return false;
		}
	}
	// FORM VALIDATION ENDS
	console.log("Validation Status:", validation);

	/* FORM VALIDATION ENDS*/
	if (validation) {
		//cancelBtn();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-calls-add-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.code == "Success") {
					$('.loader').hide();
					toastr.success('Call Saved Successfully');
					closeCallSection();
					var selectedRows = gridOptionsLead.api.getSelectedRows();
					var leadId = selectedRows[0].leadId;
					getCallForLead(leadId);
					getMail(leadId);
					getActivity("allTimeline");
				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

}
/*function timeValidCall() {
	if ($("#callStartTime").val() && $("#callEndTime").val())
		timeValidation("callStartTime", "callEndTime", "Call Start Time", "Call End Time");
}*/
// Edit & stage change 
function editCallPage(id) {
	toggleCallSection();
	$("#deleteCallIcon").removeClass("d-none");
	$.ajax({
		type: "GET",
		url: "view-crm-calls-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log(response);
			if (response.message == "Success") {
				$("#callStatus option[value='Completed']").prop("disabled", false);
				$("#relatedMeetingTo").val('').prop("disabled", false);
				$("#selectParticipents").prop('disabled', true);
				$(".participantsList").show();
				//agGridActivity(pipelineId);
				var callStartDate = response.body[0].callStartDate;
				callStartDate = callStartDate.split(' ')[0];
				var callId = $("#callId").text(response.body[0].callId);
				$("#callToWhom").val(response.body[0].callToWhom);
				$("#personName").val(response.body[0].personName);
				$("#personId").val(response.body[0].personId);
				$("#relatedType").val(response.body[0].relatedType);
				$("#relatedId").val(response.body[0].relatedId);
				$('#relatedName').val(response.body[0].relatedName);
				$('#callType').val(response.body[0].callType);
				$('#callStatus').val(response.body[0].callStatus).trigger('change');
				$('#callStartDate').val(callStartDate);
				$('#callStartTime').val(response.body[0].callStartTime);
				$('#callEndTime').val(response.body[0].callEndTime);
				let callStatus = response.body[0].callStatus;
				if (callStatus == "Completed") {
					disableCallFields();
				} else {
					enableCallFields();
				}

				$('#callOwner').val(response.body[0].callOwner).trigger('change');
				$('#callSubject').val(response.body[0].callSubject);
				$('#callReminder').val(response.body[0].callReminder);
				$('#callPurpose').val(response.body[0].callPurpose).trigger('change');
				$('#callAgenda').val(response.body[0].callAgenda);
				$('#callRemark').val(response.body[0].callRemark);

				$('#leadName').val(response.body[0].leadName);
				$('#leadId').val(response.body[0].leadId);
				$('#contactName').val(response.body[0].contactName);
				$('#contactId').val(response.body[0].contactId);
				$('#contactMail').val(response.body[0].toMail);
				$('#leadMail').val(response.body[0].ccMail);

				var tskLead = response.body[0].leadId;

				var conTsk = response.body[0].contactId;

				if (tskLead == null || tskLead == "") {

					$("#callToWhom").val('Contact');
					$('#contactDiv').show();
					$('#accountDiv').show();
					$('#leadDiv').hide();
					$('#contactName').prop('disabled', true);
					$('#contactName').css({
						'background-color': '#e9ecef',
					});
					$('#callToWhom').prop('disabled', true);
					$('#callToWhom').css({
						'background-color': '#e9ecef',
					});

					var name = response.body[0].contactName;
					var ContactId = response.body[0].contactId;

					selectAutocompleteContact(name, ContactId)
				} else if (conTsk == null || conTsk == "") {

					$("#callToWhom").val('Lead');
					$('#leadDiv').show();
					$('#contactDiv').hide();
					$('#accountDiv').hide();
					$("#leadId").val(response.body[0].leadId);
					$('#leadName').prop('disabled', true);
					$('#leadName').css({
						'background-color': '#e9ecef',
					});
					$('#callToWhom').prop('disabled', true);
					$('#callToWhom').css({
						'background-color': '#e9ecef',
					});
				}

				var participants = JSON.parse(response.body[0].participantId);
				console.log("Parsed Data-------" + JSON.stringify(participants));

				if (participants.findIndex(item => item.type === "lead") >= 0) {

					$("#relatedMeetingTo1").val("Lead1");
				} else if (participants.findIndex(item => item.type === "contact") >= 0) {

					$("#relatedMeetingTo1").val("Contact1");
				} else if (participants.findIndex(item => item.type === "executive") >= 0) {

					$("#relatedMeetingTo1").val("executive");
				}

				participants.forEach((data) => {
					if (data.type == 'lead') {

						//selectAutocompleteMeetingLeadValue(data.name, data.id, data.email);
						checkMeetingDetails1(data.type);

					} else if (data.type == 'contact') {

						//selectAutocompleteValueMeetingContact(data.name, data.id, data.email);
						checkMeetingDetails1(data.type);

					} else if (data.type == 'executive') {

						//selectAutocompleteValueMeetingExcutive(data.name, data.id, data.email);
						checkMeetingDetails1(data.type);

					}
				});

				var relatedId = response.body[0].callToWhom

				if (relatedId == 'Lead') {
					$(".leadNameCls").show();
					$(".contactNameCls").hide();
					$('#leadName').val(response.body[0].leadName);
					//$('#leadId').val(response.body[0].leadId);
				}
				if (relatedId == 'Contact') {
					$(".leadNameCls").hide();
					$(".contactNameCls").show();
					$('#contactName').val(response.body[0].contactName);
					//$('#contactId').val(response.body[0].contactId);
				}
				//for stage active status


			}
		}
	})
}
function disableCallFields() {
	$("#deleteCallIcon,#saveCallIcon,#callOwner, #callSubject,#callStatus, #callPurpose, #callStartDate, #callStartTime, #callEndTime, #callAgenda,#callRemark ").prop("disabled", true);
	$(" .day, .date-items .date-item").addClass("disabled");
}

function enableCallFields() {
	$("#deleteCallIcon,#saveCallIcon,#callOwner, #callSubject,#callStatus, #callPurpose, #callStartDate, #callStartTime, #callEndTime, #callAgenda,#callRemark ").prop("disabled", false);
	$(".day, .date-items .date-item").removeClass("disabled");
}

function deleteCallOnclick() {
	var callId = $("#callId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-calls-delete-id?id=" + callId,
		success: function(response) {
			if (response.message == "Success") {
				toastr.success('Call Deleted Successfully');
				console.log(response);
				closeCallSection();
				getCallForLead(leadid);
				getActivity("allTimeline");
			}
		}

	});
}
/* Call Section End */
/* Meeting  Section Start */
function generateMeetingCard(meeting) {
	console.log(`Generating meeting card for meeting ID: ${meeting.meetingId}`);

	const { meetingId, meetingTitle, meetingStatus, meetingFromDate, meetingFromTime, meetingToDate, meetingToTime, meetingHost } = meeting;

	// Create meeting card (Always Clickable)
	return `
        <div class="card-task clickable-card" onclick="editMeetingPage('${meetingId}')" style="cursor: pointer;">
            <div class="task-header">
                <span class="toptxt">${meetingTitle}</span>
            </div>
            <div class="task-info">
                <span>Meeting From: ${meetingFromDate} ${meetingFromTime} - ${meetingToDate} ${meetingToTime}</span>
            </div>
            <div class="task-info">
                <i class="bi bi-person-fill"></i> ${meetingHost}
            </div>
            <p class="task-info">Status: ${meetingStatus}</p>
        </div>`;
}

function getMeetingForLead(leadId) {
	$.ajax({
		type: "GET",
		url: `view-crm-leads-view-detail-meeting?id=${leadId}`,
		success: function(response) {
			if (response.message === "Success") {
				$("#meetingActivity").empty();
				$("#closedMeetingActivity").empty();

				let countOpenM = 0, countCloseM = 0;

				response.body.forEach(meeting => {
					const cardHTML = generateMeetingCard(meeting);

					if (meeting.meetingStatus !== "Completed") {
						$("#meetingActivity").append(cardHTML);
						countOpenM++;
					} else {
						$("#closedMeetingActivity").append(cardHTML);
						countCloseM++;
					}
				});
				if (countOpenM === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#meetingActivity").append(noRecord);
				}
				if (countCloseM === 0) {
					const noRecord = '<div class="card-task"><div class="task-info">No Record Found</div></div>';
					$("#closedMeetingActivity").append(noRecord);
				}

				$("#openMeetings").text(countOpenM);
				localStorage.setItem("countOpenM", countOpenM);
				$("#closeMeetings").text(countCloseM);
				localStorage.setItem("countCloseM", countCloseM);
				getCount(leadId);
			}
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
	$('#meetingStatus').val('').trigger('change');
	$('#eventModal').modal('hide');
	$("#onlineMode").prop("checked", true);
	$('#meetingRemark').val('');
	toggleModeFields();
}
function setMeetingDatesAndTimes() {
	let currentTime = new Date();
	let currentDate = new Date();
	let startHours = String(currentTime.getHours()).padStart(2, '0');
	let startMinutes = String(currentTime.getMinutes()).padStart(2, '0');
	let endTime = new Date(currentTime);
	endTime.setHours(currentTime.getHours() + 1);

	let endHours = String(endTime.getHours()).padStart(2, '0');
	let endMinutes = String(endTime.getMinutes()).padStart(2, '0');

	let formattedDate = currentDate.toISOString().split('T')[0];

	$('#meetingCrationDate').val(formattedDate);
	$('#meetingCrationTime').val(`${startHours}:${startMinutes}`);
	$('#meetingFromDate').val(formattedDate);
	$('#meetingFromTime').val(`${startHours}:${startMinutes}`);
	$('#meetingToDate').val(formattedDate);
	$('#meetingToTime').val(`${endHours}:${endMinutes}`);
}
function toggleMeetingSection() {
	enableMeetingFields();
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var status = selectedData.map(node => node.leadStatus);
	var leadOrContactName = selectedData.map(node => node.leadName);
	var company = selectedData.map(node => node.company);
	var leadOrContactMail = selectedData.map(node => node.email);
	var leadId = selectedData.map(node => node.leadId);
	var contactId = selectedData.map(node => node.contactId);
	var accountId = selectedData.map(node => node.accountId);
	console.log(`Here Is My Name${leadOrContactName} and my company name ${company} and my mail Id is ${leadOrContactMail} id======>${leadId}  and ${contactId} account id ${accountId}`);

	var type = "";
	if (status == "Qualified") {
		$(".leadNameCls").hide();
		$("#contactDiv").show();
		$("#accountDiv").show();
		$("#contactName").val(leadOrContactName);
		$("#contactId").val(contactId);
		$("#contactMail").val(leadOrContactMail);
		$("#dealAccountName").val(company);
		$("#accountId").val(accountId);

	} else {
		$(".leadNameCls").show();
		$("#contactDiv").hide();
		$("#accountDiv").hide();
		$("#leadName").val(leadOrContactName);
		$("#leadId").val(leadId);
		$("#leadMail").val(leadOrContactMail);
	}

	$("#addMeetingIcon").addClass("d-none");
	$("#closeMeetingIcon").removeClass("d-none");
	$("#meetingFields").removeClass("d-none");
	$("#SaveMeetingIcon").removeClass("d-none");
	$("#openMeetingContainer").addClass("d-none");
	$("#closeMeetingContainer").addClass("d-none");
	$('#meetingHost').val(userId).trigger('change');
	$('#meetingId').text("");
	$(".formValidation").remove()

	cancelModal();
	setMeetingDatesAndTimes();
	//getTheEmployeeList();
	$("#meetingStatus option[value='Completed']").prop("disabled", true);
	$('#prevMeeting').prop('disabled', true);
	$('#nextMeetingBtn').prop('disabled', true);
}
function closeMeetingSection() {
	$("#addMeetingIcon").removeClass("d-none");
	$("#closeMeetingIcon").addClass("d-none");
	$("#meetingFields").addClass("d-none");
	$("#openMeetingContainer").removeClass("d-none");
	$("#closeMeetingContainer").removeClass("d-none");
	$("#deleteMeetingIcon").addClass("d-none");
	$("#SaveMeetingIcon").addClass("d-none");
	$("#contactName").val("");
	$("#contactId").val("");
	$("#contactMail").val("");
	$("#dealAccountName").val("");
	$("#accountId").val("");

	$("#leadName").val("");
	$("#leadId").val("");
	$("#leadMail").val("");
	$('#prevMeeting').prop('disabled', false);
	$('#nextMeetingBtn').prop('disabled', false);

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
function getTheEmployeeList(attendees = [], hostId = '') {
	$.ajax({
		url: 'view-crm-calls-get-all-attendees?hostId=' + hostId,
		type: 'get',
		success: function(response) {
			try {
				const data = JSON.parse(response.body);
				const employees = data.EmployeesData;
				const leads = data.Leads;
				console.log("Leads------>", leads);
				console.log("Employess------>", employees);

				let dropdownHTML = '<select id="employeeDropdown" name="employee" class="chosen-select" multiple>';
				dropdownHTML += '<option value="" disabled>Select Employee or Lead</option>';

				// Add employees
				employees.forEach(function(employee) {
					let isSelected = attendees.some(att => att.id === employee.employeeId);
					let isDisabled = employee.employeeId === hostId;
					dropdownHTML += `<option value="${employee.employeeId}" data-name="${employee.employeeName}" ${isSelected ? 'selected' : ''} ${isDisabled ? 'disabled' : ''}>${employee.employeeName}</option>`;
				});

				// Add leads
				// Add leads
				if (Array.isArray(leads)) {
					leads.forEach(function(lead) {
						let isSelected = attendees.some(att => att.id === lead.leadId);
						dropdownHTML += `<option value="${lead.leadId}" data-name="${lead.fullName}" ${isSelected ? 'selected' : ''}>${lead.fullName} (Lead)</option>`;
					});
				}


				dropdownHTML += '</select>';
				$("#employeeDropdownContainer").html(dropdownHTML);

				$(".chosen-select").chosen({ width: "100%" });

			} catch (error) {
				console.error('Error processing API response:', error);
			}
		},
		error: function(error) {
			console.error('Error in AJAX request:', error);
		}
	});
}



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
function addMeetingInfo() {

	var selectElement = document.getElementById("meetingHost");
	var selectedOption = selectElement.options[selectElement.selectedIndex];
	var meetingHostName = selectedOption.textContent;

	let meetingStatus = $("#meetingStatus").val()

	var executiveSelect = $("#meetingHost");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';
	/* if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
		ccMeetingMail = ccMeetingMail.slice(0, -1);
	} */

	let toMail = '';
	var leadMail = $('#leadMail').val();
	var contactMailId = $('#contactMail').val();

	if (leadMail !== null && leadMail !== "") {
		toMail = leadMail;
	} else if (contactMailId !== null && contactMailId !== "") {
		toMail = contactMailId;
	}
	var selectedOptions = $("#employeeDropdown option:selected");
	var participants = [];

	selectedOptions.each(function() {
		var id = $(this).val();
		var name = $(this).data("name");
		participants.push({ id: id, name: name });
	});

	console.log("Selected Participants:::::::", participants);

	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var status = selectedData.map(node => node.leadStatus);
	var type = "";
	if (status == "Qualified") {
		type = "Contact";
	} else {
		type = "Lead";
	}

	var obj = {};
	obj.type = type;
	obj.meetingId = $('#meetingId').text();
	obj.meetingTitle = $('#meetingName').val();
	var meetingMode = document.querySelector('input[name="meetingMode"]:checked').value;
	obj.meetingMode = meetingMode,
		obj.meetingHost = $('#toHiddenIdOrg').val();
	obj.meetingLocation = $('#location').val();
	obj.meetingUrl = $('#meetingLink').val();
	obj.leadName = $('#leadName').val();
	var selectedRows = gridOptionsLead.api.getSelectedRows();
	var leadId = selectedRows[0].leadId;
	obj.leadId = leadId;
	obj.contactName = $('#contactName').val();
	obj.contactId = $('#contactId').val();
	obj.accountName = $('#dealAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.meetingType = type;
	var meetingFrequency = $('#frequency').val();
	obj.meetingFrequency = meetingFrequency;
	var meetFromDate = "";
	var meetToDate = "";

	if (meetingFrequency == 'one-time') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingFromDate').val();
	} else if (meetingFrequency == 'daily') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingToDate').val();
	} else if (meetingFrequency == 'weekly') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingToDate').val();
	} else if (meetingFrequency == 'monthly-same-date') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingToDate').val();
	} else if (meetingFrequency == 'monthly-same-day') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingToDate').val();
	} else if (meetingFrequency == 'monthly-last-working-day') {
		meetFromDate = $('#meetingFromDate').val();
		meetToDate = $('#meetingToDate').val();
	}
	obj.monthlyDates = $('#selected-dates').text()
	obj.creationDate = $('#meetingCrationDate').val();
	obj.creationTime = $('#meetingCrationTime').val();
	obj.meetingFromDate = meetFromDate;
	obj.meetingFromTime = $('#meetingFromTime').val();
	obj.meetingToDate = meetToDate;
	obj.meetingToTime = $('#meetingToTime').val();
	obj.meetingHost = $('#meetingHost').val();
	obj.daysFilter = getSelectedDays().join(',');
	obj.discussionPoint = getDiscussionPoints();
	obj.leadName = $('#leadName').val();
	obj.leadId = $('#leadId').val();
	obj.contactName = $('#contactName').val();
	obj.contactId = $('#contactId').val();
	obj.accountName = $('#dealAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.meetingAgenda = $('#agenda').val();
	obj.meetingRemark = $('#meetingRemark').val();
	obj.attendees = participants;
	//obj.meetingParticipants = $('#meetingParticipants').val();
	//obj.participantId = $('#participantId').val();
	//obj.relatedMeetingTo = $('#relatedMeetingTo').val();
	obj.meetingStatus = $('#meetingStatus').val();
	//obj.isRepeat = $('#isRepeat').val();
	//obj.isAllDayRepeat = $('#isAllDayRepeat').val();
	//obj.meetingRepeatFromDate = $('#meetingRepeatFromDate').val();
	/* obj.meetingRepeatFromTime = $('#meetingRepeatFromTime').val();
	obj.meetingRepeatToDate = $('#meetingRepeatToDate').val();
	obj.meetingCalendarRepeatToTime = $('#meetingRepeatToTime').val();
	obj.repeatType = $('#repeatType').val();
	obj.description = $('#description').val();
	obj.participantId = JSON.stringify(participantData); */
	obj.toMail = toMail;
	//obj.ccMail = ccMeetingMail;
	obj.excutiveMail = executiveMail;
	obj.summary = $("#summary").val();
	//obj.meetingHostName = meetingHostName;
	console.log(obj);//return false;
	/* FORM VALIDATION STARTS*/
	var status = $('#meetingStatus').val();
	var remark = $('#meetingRemark').val();
	var validation = true;
	if (!obj.meetingTitle || obj.meetingTitle === "") {
		toastr.error("Meeting Name is required");
		validation = false;
		return false;
	}
	if (!meetingFrequency || meetingFrequency === "") {
		toastr.error("Meeting Frequency is required");
		validation = false;
		return false;
	}
	if (!meetFromDate || meetFromDate === "") {
		toastr.error("Meeting Start Date is required");
		validation = false;
		return false;
	}
	if (!meetToDate || meetToDate === "") {
		toastr.error("Meeting End Date is required");
		validation = false;
		return false;
	}
	if (!obj.meetingFromTime || obj.meetingFromTime === "") {
		toastr.error("Meeting Start Time is required");
		validation = false;
		return false;
	}
	if (!obj.meetingToTime || obj.meetingToTime === "") {
		toastr.error("Meeting End Time is required");
		validation = false;
		return false;
	}

	if (!meetingMode) {
		$('input[name="meetingMode"]').parent().after('<span class="validation-error" style="color:red;">Meeting Mode is required</span>');
		toastr.error("Meeting Mode is required");
		validation = false;
		return false;
	} else if (meetingMode === "online") {
		if (!$("#meetingLink").val()) {
			toastr.error("Meeting URL is required for online meetings");
			validation = false;
			return false;
		}
	} else if (meetingMode === "offline") {
		if (!$("#location").val()) {
			toastr.error("Meeting Location is required");
			validation = false;
			return false;
		}
	}
	if (status !== "" && (status === "Deferred" || status === "Completed")) {
		if (!remark) {
			toastr.error("Remark is required");
			return false;
		}
	}
/*	if (participants.length === 0) {
		toastr.error("At least one participant is required");
		validation = false;
		return false;
	}*/
	/* FORM VALIDATION ENDS*/
	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-add-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.code == "Success") {
					//cancelBtn();
					$('.loader').hide();
					toastr.success('Meeting Saved Successfully');
					closeMeetingSection();
					var selectedRows = gridOptionsLead.api.getSelectedRows();
					var leadId = selectedRows[0].leadId;
					getMeetingForLead(leadId);
					getMail(leadId);
					getActivity("allTimeline");
				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

}
function formatDate(dateStr) {
	var dateParts = dateStr.split('-');
	return dateParts[2] + '-' + dateParts[1] + '-' + dateParts[0];
}

function formatTime(timeStr) {
	return timeStr.split(':').slice(0, 2).join(':');
}
//Edit & stage change 
function editMeetingPage(id) {
	toggleMeetingSection();
	$("#deleteMeetingIcon").removeClass("d-none");
	$.ajax({
		type: "GET",
		url: "view-crm-meetings-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log(JSON.stringify(response));
			if (response.message == "Success") {
				console.log("Response For Edit Meeting =======>", response.body);
				//agGridActivity(pipelineId);
				$("#meetingStatus option[value='Completed']").prop("disabled", false);
				let meetingData = response.body[0];
				console.log(response.body[0].summary)
				var meetingId = $("#meetingId").text(response.body[0].meetingId);
				$("#meetingName").val(response.body[0].meetingTitle);
				$('#frequency').val(response.body[0].meetingFrequency).trigger('change');
				$('input[name="meetingMode"][value="' + response.body[0].meetingMode + '"]').prop('checked', true).trigger('change');
				$("#location").val(response.body[0].meetingLocation);
				$("#meetingLink").val(response.body[0].meetingUrl);
				$('#meetingHost').val(response.body[0].meetingHost).trigger('change');
				$('#agenda').val(response.body[0].meetingAgenda);
				let creationTime = response.body[0].creationTime;
				if (creationTime) {
					let [hours, minutes] = creationTime.split(":").map(Number);
					if (hours < 12) {
						hours += 12;
					}
					let formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
					$('#meetingCrationTime').val(formattedTime);
				}
				$('#meetingCrationDate').val(formatDate(response.body[0].creationDate));
				$('#meetingFromDate').val(formatDate(response.body[0].meetingFromDate));
				$('#meetingFromTime').val(response.body[0].meetingFromTime);
				$('#meetingToDate').val(formatDate(response.body[0].meetingToDate));
				$('#meetingToTime').val(response.body[0].meetingToTime);
				$('#meetingStatus').val(response.body[0].meetingStatus).trigger('change');
				$('#meetingRemark').val(response.body[0].meetingRemark);
				//$('#meetingHost').val(response.body[0].meetingHost);
				var meetingDays = response.body[0].daysFilter;
				if (typeof meetingDays === 'string') {
					if (meetingDays.includes(',')) {
						meetingDays = meetingDays.split(',');
					} else {
						meetingDays = [meetingDays];
					}
				}

				meetingDays.forEach(day => {
					$('#weakSelect .day[data-day="' + day.trim() + '"]').addClass('selected'); // Add trim() to clean up spaces
				});
				$('#contactMail').val(response.body[0].toMail);
				$('#leadMail').val(response.body[0].ccMail);
				var attendees = response.body[0].attendees;
				var hostId = response.body[0].meetingHost;
				console.log("Attendees======>", attendees);

				getTheEmployeeList(attendees, hostId);


				var tskLead = response.body[0].leadId;
				var conTsk = response.body[0].contactId;
				const discussionData = response.body[0].discussionPoint;
				setDiscussionData(discussionData);

				const monthlyDatePicker = response.body[0].monthlyDates;
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

				$('#leadName').val(response.body[0].leadName);
				$('#leadId').val(response.body[0].leadId);
				$('#contactName').val(response.body[0].contactName);
				$('#contactId').val(response.body[0].contactId);
				$(".discussion-btn-bg").prop("disabled", false);

				if (meetingData.meetingStatus === "Completed") {
					disableMeetingFields();
				} else {
					enableMeetingFields();
				}

			}
		}
	})
}
function disableMeetingFields() {
	$("#meetingName,#SaveMeetingIcon,#deleteMeetingIcon,#addDiscuss, #frequency, input[name='meetingMode'], #location, #meetingLink, #meetingHost, #agenda, #meetingCrationTime, #meetingCrationDate, #meetingFromDate, #meetingFromTime, #meetingToDate, #meetingToTime, #meetingStatus, #contactMail, #leadMail, #leadName, #leadId, #contactName, #contactId,#meetingRemark").prop("disabled", true);
	$(".discussion-btn-bg, #weakSelect .day, .date-items .date-item").addClass("disabled");
	$("#addDiscuss").addClass("disabled").on("click.preventDisabled", function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
	});

}

function enableMeetingFields() {
	$("#meetingName,#SaveMeetingIcon,#deleteMeetingIcon,#addDiscuss, #frequency, input[name='meetingMode'], #location, #meetingLink, #meetingHost, #agenda, #meetingCrationTime, #meetingCrationDate, #meetingFromDate, #meetingFromTime, #meetingToDate, #meetingToTime, #meetingStatus, #contactMail, #leadMail, #leadName, #leadId, #contactName, #contactId,#meetingRemark").prop("disabled", false);
	$(".discussion-btn-bg, #weakSelect .day, .date-items .date-item").removeClass("disabled");
	$("#addDiscuss").removeClass("disabled").off("click.preventDisabled");
}
function deleteMeetingOnclick() {
	var deleteId = $("#meetingId").text();
	$.ajax({
		type: "GET",
		url: "view-crm-meetings-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				toastr.success('Meeting Deleted Successfully');
				console.log(response);
				closeMeetingSection();
				getMeetingForLead(leadid);
				getActivity("allTimeline");
			}
		}

	});

	$('#delete').attr("disabled", true);
}

/*Mail Section */

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
function getMail(contactId) {
	$("#countEmail").empty().append(0);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-mail?id=" + contactId,
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);
		var len = 0;
		if (resp.mailList && Array.isArray(resp.mailList)) {
			len = resp.mailList.length;
			if (len > 0) {
				mailgridOptions.api.setRowData(resp.mailList);
			} else {
				mailgridOptions.api.setRowData([]);
			}
		} else {
			mailgridOptions.api.setRowData([]);
		}
		$('#totalEmp').find('span').html(len);
		$("#countEmail").empty().append(len);
	});
}

function toggleMailSection() {
	$('#multipleMailCc').chosen();
	$('#multipleMailBcc').chosen();

	$('#multipleMailCc').on('change', function() {
		var selectedValues8 = $(this).val();
		console.log("Selected Values from multipleMailCc: ", selectedValues8);

		$('#toHiddenIdCc').val(selectedValues8 ? selectedValues8.join(',') : '');
		console.log("Updated Hidden Input Value (multipleMailCc): ", $('#toHiddenIdCc').val());

		updateDisabledOptions();
	});

	$('#multipleMailBcc').on('change', function() {
		var selectedValues9 = $(this).val();
		console.log("Selected Values from multipleMailBcc: ", selectedValues9);

		$('#toHiddenIdBcc').val(selectedValues9 ? selectedValues9.join(',') : '');
		console.log("Updated Hidden Input Value (multipleMailBcc): ", $('#toHiddenIdBcc').val());

		updateDisabledOptions();
	});
	$("#doctbodyData").empty().append(
		'<tr>' +
		'<td style="display:none" align="center" class="pdb-24">' +
		'<input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
		'<td style="display:none"><div class="form-group">' +
		'<select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">' +
		'<option value="">Select</option> </select> </div></td>' +
		'<td><div class="form-group">' +
		'<input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
		'<td> <div class="control-group position-r">' +
		'<label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">' +
		'<i class="ti-plus" id="clickImg_0"></i> </label>' +
		'<div class="controls">' +
		'<input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />' +
		'</div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
		'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>' +
		'<input type="hidden" id="editId_0"> </td>' +
		'</tr>'
	);
	$('#multipleMailCc').val([]).trigger('chosen:updated');
	$('#multipleMailBcc').val([]).trigger('chosen:updated');
	$('#editMailIcon').addClass('d-none')
	$('#deleteDraftIcon').addClass('d-none')
	$("#myGridMail").addClass('d-none')
	$("#addMailIcon").addClass('d-none')
	$("#myModalAddEmail").removeClass('d-none')
	$("#closeMailIcon").removeClass('d-none')
	$("#sendIcon").removeClass('d-none')
	$("#saveMailIcon").removeClass('d-none')
	$('#editMailIcon').addClass('d-none')
	$('#deleteDraftIcon').addClass('d-none')
	// $("#deleteMailIcon").removeClass('d-none')
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var toMail = selectedData.map(node => node.email);
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
	$('#uploadHidden_1').val('');

	$('#imageName_1').text('');
	$('#imageName_1').val('');
	$('#uploadedBillDiv_1').html('');
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
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}

	let fnametext = '<div id="imageName_' + counter + '" class="imageName" style="margin-left: 2px;">' + fileName + '</div><span><i class="ti-close red close_sec1 deleteFileDoc ml-5" onclick=openDeleteConfirm(' + counter + ')></i></span>';

	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-pdf custom-file-icon'></i> </a>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}

	$("#clickImg_" + counter).removeClass('ti-plus').addClass('ti-pencil');
	// $("#uploadHidden_"+ counter).val(fileName);
	$("#uploadedBillDiv_" + counter).html(LightImg + fnametext);

}
function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;

	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">' +
		'</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		'<div id="validationDiv"></div></div>';

	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if (cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});

}
function closeMailSection() {
	$("#myGridMail").removeClass('d-none')
	$("#addMailIcon").removeClass('d-none')
	$("#myModalAddEmail").addClass('d-none')
	$("#closeMailIcon").addClass('d-none')
	$("#sendIcon").addClass('d-none')
	$("#saveMailIcon").addClass('d-none')
	$("#deleteMailIcon").addClass('d-none')

	selectedDraftId = "";
	$("#mailSubject").val("");
	$("#comment").val("");
	$("#docName").val("");
	$("#uploadList").val("");
	$("#ccMail").val("");
	$("#bccMail").val("");
	$("#selected-mail-container").html("");
	$("#bcc-mail-container").html("");
	$("#draftId").val("");
	$("#commentck").val("");

	removeErrorMsg();
	ccMailListData.clear();
	bccMailListData.clear();
}
function toggleCC() {
	$("#bccField").removeClass('d-none');
	$("#ccField").removeClass('d-none');
}

function saveMultiFileForLead(event) {
	var counter = event.currentTarget.id.split("_")[1]; // Get the counter ID
	var currentFldId = "#uploadDoc_" + counter;
	var uFile = $(currentFldId)[0].files[0];

	if (!uFile) return;

	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}

	var extension = fileName.split(".");
	extension = extension[extension.length - 1].toLowerCase();
	console.log("extension", extension);
	var iURL = URL.createObjectURL(uFile); // Create preview URL

	// Generate file preview icon
	var LightImg = "";
	if (extension == "jpg" || extension == "png") {
		var LightImg = "<div class='uploadicon m-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-image'></i></a></div>";
	} else if (extension == "pdf") {
		var LightImg = "<div class='uploadicon m-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension == "xls" || extension == "xlsx") {
		var LightImg = "<div class='uploadicon m-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension == "doc" || extension == "dox" || extension == "docx") {
		var LightImg = "<div class='uploadicon m-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon m-0'> </div>";
	}

	// **New Feature: Add View & Delete Icons**
	var iconsHTML = `
        <a href="${iURL}" target="_blank" title="View File">
            <i class="ti-eye" style="font-size:18px; margin-right:10px; color:green;"></i>
        </a>
        <i class="ti-trash" style="font-size:18px; color:red; cursor:pointer;" onclick="removeImage('1')"></i>
    `;

	// Update the UI with the preview + icons
	$("#uploadedBillDiv_" + counter).html(iconsHTML);
	$("#imageName_" + counter).html(fileName);
}

// **Delete Function: Clears the Uploaded Image**
function removeImage(counter) {
	$("#uploadedBillDiv_" + counter).html(""); // Remove preview
	$("#imageName_" + counter).html(""); // Remove file name
	$("#uploadDoc_" + counter).val(""); // Reset file input
}


function getActivity(type) {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadId = selectedData.map(node => node.leadId);
	//alert('hello activity----------'+contact);return false;
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-view-detail-activity?id=" + leadId + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				getActivityTimeline(response.body);
				//getTimeline(leaveId, "allTimeline");

			}
		}
	});
}
let bccMailData = '';
let selectedDraftId = '';

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


	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadIdArray = selectedData.map(node => node.leadId);
	var leadId = leadIdArray.length > 0 ? leadIdArray[0] : "";
	var leadId = leadId;
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = $("#commentck").val();
	var docName = $("#docName").val();
	var ccMail = $("#toHiddenIdCc").val();             /*`"${$("#multipleMailCc").val().join(',')}"`;*/
	var bccMail = $("#toHiddenIdBcc").val();            /* `"${$("#multipleMailBcc").val().join(',')}"`;*/



	if (leadId) {

		var imageValid = true;
		var uploadList = [];
		$("#doctbodyData > tr").each(

			function() {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined' &&
					fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
						console.log("jdcdsjhd sdk  ---->", x)
					};
				} else {
					if (leadId) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['documnentName'] = $(this).find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				if ($(this).find(".docNoclsss").val() != "" && $(this).find(".docNoclsss").val() != 'null' && fileName != "" && fileName != "null") {
					uploadList.push(uploadData);

				}

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
			console.log("Lead Mail======>", item);
			saveLeadEmailDtls(item);

		}, 100)

	} else {

		showSnackbar("Save the personal details first!");
	}
}

function saveLeadEmailDtls(item) {

	var validation = true;


	if (item.mailSubject == null || item.mailSubject == "") {
		toastr.error("Mail Subject Is Required");
		validation = false;
		return;
	}
	if (validation) {
		$('.loader').show();
		//closeModelEmail();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add-emails-ajax",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.message == "success") {
					toastr.success('Email Sent successfully');
					var selectedNodes = gridOptionsLead.api.getSelectedNodes();
					var selectedData = selectedNodes.map(node => node.data);
					var leadId = selectedData.map(node => node.leadId);
					getActivity("allTimeline");
					$.ajax({
						type: "GET",
						url: "view-crm-leads-delete-draft?id=" + selectedDraftId,
						success: function(response) {

							if (response.message == "Success") {

							}
						}
					});
					closeMailSection();
					getDraft(leadId);
					draftId = null;
					$('.loader').hide();
					//mailSucessAlert();
					$("#toMail").val("");
					$("#mailSubject").val("");
					$("#comment").val("");
					$("#docName").val("");
					$("#uploadList").val("");
					//var leadId = $("#leadId").html();
					getMail(leadId);
					getActivity("allTimeline");
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
	console.log(`bcc mail ${bccMailList} and cc mail ${commaSeparatedEmails}`)
	var item = {};

	var selectedRows = gridOptionsLead.api.getSelectedRows();
	var leadId = selectedRows[0].leadId;
	var leadId = leadId;
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = $("#commentck").val();
	var docName = $("#docName").val();
	var ccMail = $("#toHiddenIdCc").val();             /*`"${$("#multipleMailCc").val().join(',')}"`;*/
	var bccMail = $("#toHiddenIdBcc").val();            /* `"${$("#multipleMailBcc").val().join(',')}"`;*/
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
		$("#doctbodyData > tr").each(

			function() {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined' &&
					fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
						console.log("jdcdsjhd sdk  ---->", x)
					};
				} else {
					if (leadId) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['documnentName'] = $(this).find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				if ($(this).find(".docNoclsss").val() != "" && $(this).find(".docNoclsss").val() != 'null' && fileName != "" && fileName != "null") {
					uploadList.push(uploadData);

				}

			});

		setTimeout(function() {
			item.leadId = leadId;
			item.draftId = (selectedDraftId != null && selectedDraftId != "") ? selectedDraftId : null;
			var selectedNodes = gridOptionsLead.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);
			var leadOwner = selectedData.map(node => node.ownerName);
			console.log("lead", leadOwner);
			item.employeeId = leadOwner[0];
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			item.ccMail = ccMail;
			item.bccMail = bccMail;
			console.log("Lead darft=====>", item)
			//return false;
			saveLeadDraftDtls(item);

		}, 100)

	} else {
		toastr.error("Save the personal details first!");
	}
}
function saveLeadDraftDtls(item) {
	var validation = true;


	if (item.mailSubject == null || item.mailSubject == "") {
		toastr.error("Mail Subject Is Required");
		validation = false;
		return;
	}
	if (validation) {
		//closeModelEmail();
		var leadId = $("#leadId").val();
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add-drafts",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.message == "success") {
					closeMailSection();
					toastr.success('Draft Saved Successfully');
					var selectedNodes = gridOptionsLead.api.getSelectedNodes();
					var selectedData = selectedNodes.map(node => node.data);
					var leadId = selectedData.map(node => node.leadId);
					getDraft(leadId);
					getActivity("allTimeline");

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

function editDraft(id) {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	if (selectedData.length === 0) {
		console.error("No row selected");
		return;
	}

	var status = selectedData[0].leadStatus;
	var contact = selectedData[0].contactId;
	var lead = selectedData[0].leadId;

	var leadOrContact = (status === "Qualified") ? contact : lead;

	console.log(`Status of selected row: ${status}, Draft ID: ${id}, Lead or Contact ID: ${leadOrContact}`);

	selectedDraftId = id;
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-edit-draft?id1=" + leadOrContact + "&id2=" + id,
		async: false,
		success: function(response) {
			if (response.message === "success") {
				$('.custom-tab[data-target="#emailTab"]').addClass('active');
				$('.custom-tab[data-target="#draftTab"]').removeClass('active');
				$('#emailTab').addClass('active');
				$('#draftTab').removeClass('active');

				toggleMailSection();
				$('#editMailIcon').addClass('d-none');
				$('#deleteDraftIcon').addClass('d-none');
				$("#sendIcon").removeClass('d-none');
				$('#draftId').val(response.body.id);
				$('#fromEmail').val(response.body.fromEmail);
				$('#toMail').val(response.body.toMail);
				$('#mailSubject').val(response.body.mailSubject);
				$('#docName').val(response.body.docnoid);
				$('#commentck').val(response.body.commentck);
				$('#multipleMailCc').val([]).trigger('chosen:updated');
				$('#multipleMailBcc').val([]).trigger('chosen:updated');

				var ccMail = (response.body.ccMail || '').replace(/^"|"$/g, '').split(',');
				var bccMail = (response.body.bccMail || '').replace(/^"|"$/g, '').split(',');

				toggleCC();

				/*var emailListCc = ccMail.split(',');
				var emailListBcc = bccMail.split(',');*/

				console.log(ccMail)
				console.log(bccMail)
				$('#multipleMailCc').val(ccMail).trigger('chosen:updated');
				$('#toHiddenIdCc').val(ccMail.join(','));
				$('#multipleMailBcc').val([]).val(bccMail).trigger('chosen:updated');
				$('#toHiddenIdBcc').val(bccMail.join(','));

				$("#doctbodyData").empty();

				let dataBody = response.body;
				if (dataBody.docnoid && dataBody.attachment && dataBody.ownerImageLink) {
					let documentName = dataBody.docnoid;
					let fileName = dataBody.attachment;
					let fileLink = dataBody.ownerImageLink;

					let actionHTML = `<a href="${fileLink}" target="_blank">
                        <i class='fa-solid fa-file-image custom-file-icon'></i>
                      </a>`;

					let fileInputHTML = `<div class="form-group d-flex">
				    <div class="">
				        <label class="input-group-text btn go-btn h-auto" for="uploadDoc_0" id="uploadFor_0">
				            <i class="ti-pencil" id="clickImg_0"></i>
				        </label>
				        <input type="file" class="form-control document" id="uploadDoc_0" 
				               accept=".jpeg, .jpg, .png, .pdf" onchange="saveMultiFile(event)">
				    </div>
				    <input type="hidden" id="uploadHidden_0" class="uploadHidCls" value="${fileName}">
				    <div class="uploadedBillCls mt-2">
				        <div class="d-flex gap-2 align-items-center" id="uploadedBillDiv_0">
				            ${actionHTML}
				            <div id="imageName_0" class="imageName" style="margin-left: 2px;">${fileName}</div>
				            <span><i class="ti-close red close_sec1 deleteFileDoc" onclick="openDeleteConfirm(0)"></i></span>
				        </div>
				    </div>
				    <div id="validationDiv"></div>
				</div>`;


					let tblRow = `<tr>
                    <td style="display:none" align="center" class="pdb-24">
                        <input class="checkCls" type="checkbox" id="check2"><label for="check2"></label>
                    </td>
                    <td style="display:none">
                        <div class="form-group">
                            <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">
                                <option value="">Select</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div class="form-group">
                            <input type="text" value="${documentName}" class="form-control docNoclsss" id="docnoid_0">
                        </div>
                    </td>
                    <td>${fileInputHTML}</td>
                  </tr>`;

					$("#doctbodyData").append(tblRow);
				} else {
					$("#doctbodyData").empty().append(
						'<tr>' +
						'<td style="display:none" align="center" class="pdb-24">' +
						'<input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
						'<td style="display:none"><div class="form-group">' +
						'<select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">' +
						'<option value="">Select</option> </select> </div></td>' +
						'<td><div class="form-group">' +
						'<input type="text" value="" class="form-control docNoclsss" id="docnoid_0"></div></td>' +
						'<td> <div class="control-group position-r">' +
						'<label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">' +
						'<i class="ti-plus" id="clickImg_0"></i> </label>' +
						'<div class="controls">' +
						'<input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />' +
						'</div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
						'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>' +
						'<input type="hidden" id="editId_0"> </td>' +
						'</tr>'
					);
				}

			}
		},
		error: function(data) {
			console.error("Error while fetching draft details:", data);
		}
	});
}

function deleteDraftOnclick() {
	var selectedNodes = draftgridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var draftId = selectedData.map(node => node.draftId);
	$.ajax({
		type: "GET",
		url: "view-crm-leads-delete-draft?id=" + draftId,
		success: function(response) {
			if (response.message == "Success") {
				toastr.success('Draft Deleted Successfully');
				var selectedNodes = gridOptionsLead.api.getSelectedNodes();
				var selectedData = selectedNodes.map(node => node.data);
				var leadId = selectedData.map(node => node.leadId);
				getMail(leadId);
				getDraft(leadId);
				$("#editMailIcon").addClass("d-none");
				$("#deleteDraftIcon").addClass("d-none");
			}
		}
	});

}

function updateLeadStatus() {
	var obj = {};
	const adminApprovalStatus = sessionStorage.getItem("adminApprvStatus");
	obj.leadStatus = $('#leadStatus').val();
	var selectedRows = gridOptionsLead.api.getSelectedRows();
	var leadId = selectedRows[0].leadId;
	obj.leadId = leadId;

	if (obj.leadStatus == "TLSM00004") {
		obj.adminApprvStatus = false;
		obj.statusUpdatedFrom = "leadDetailsPage";
		adminApprove('0');
	} else if (obj.leadStatus == "TLSM00010") {
		toastr.error('Waiting for Admin Approval!');
		return;
	} else {
		obj.adminApprvStatus = false;
		obj.statusUpdatedFrom = "leadDetailsPage";
	}
	console.log("Data To Update The Status Of The lead", obj);

	// Proceed with the AJAX request
	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-lead-details",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				//toastr.success(response.message);
				// cancelBtn();
				viewLeadAggridData();
				setTimeout(() => {
					gridOptionsLead.api.forEachNode((node) => {
						if (leadId == "") {
							let firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
							if (firstRow) {
								firstRow.setSelected(true);
							}
						} else if (node.data.leadId == leadId) {
							node.setSelected(true);
						}
					});
				}, 1000);
			}
		},
		error: function(data) {
			console.error("Error updating lead status:", data);
		}
	});
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
function onQuickFilterChanged() {
	gridOptionsLead.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptionsLead.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}
function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
}
function onQuickFilterChanged() {
	gridOptionsLead.api.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptionsLead.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptionsLead.api.setQuickFilter('');
	gridOptionsLead.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptionsLead.api) {
			gridOptionsLead.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}
function adminApprove(id) {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadId = selectedData.map(node => node.leadId);
	$.ajax({
		type: "GET",
		url: "admin-approval?lead=" + leadId + "&rejectStatus=" + id,
		success: function(response) {
			if (response.code == "Success") {
				//getCrmNotifications();
				if (response.body == null) {
					toastr.success('Rejected Successfully');
				} else {
					console.log("Data For Convert To Next Step=====>", JSON.parse(response.body)[0]);
					convertLeadToNextStep(JSON.parse(response.body)[0]);
					toastr.success('Qualified Successfully')
					//let leadId = $("#leadId").val();
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
				var pageno = 1;
				var executiveId = $('#userId').val();
				var fromDate = $("#fromDateLeadC").val();
				var toDate = $("#toDateLeadC").val();
				agGrid.simpleHttpRequest({
					url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + executiveId + "&fromDate=" + fromDate + "&toDate=" + toDate,
				}).then(function(data) {
					if (data.code == "Success") {
						var resp = JSON.parse(data.body);
						//var len = resp.length;
						//$('#totalLead').find('span').html(len);
						var filteredData = resp.filter(function(lead) {
							return lead.leadStatus === "Contacted" ||
								lead.leadStatus === "Qualified" ||
								lead.leadStatus === "Approval Pending" ||
								lead.leadStatus === "Proposed" ||
								lead.leadStatus === "Negotiated";
						});
						gridOptionsLead.api.setRowData(filteredData);

					} else {
						gridOptionsLead.api.setRowData([])
					}
					setTimeout(() => {
						gridOptionsLead.api.forEachNode((node) => {
							if (leadId == "") {
								let firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
								if (firstRow) {
									firstRow.setSelected(true);
								}
							} else if (node.data.leadId == leadId) {
								node.setSelected(true);
							}
						});
					}, 1000);
					$('.loader').hide();
				});
			}
		}
	});
}

function convertLeadToNextStep(data) {
	var obj = {};
	//var dealcheck = $("input:checkbox[name=dealcheck]:checked").val();
	obj.leadOwnerId = data.leadOwner
	obj.leadId = data.leadId
	obj.firstName = data.firstName
	obj.lastName = data.lastName
	obj.accountName = data.company
	obj.email = data.email
	obj.phone = data.phone
	obj.fax = data.projectId
	obj.website = data.website
	obj.title = data.title
	obj.mobile = data.mobile
	obj.skypeId = data.skypeId
	obj.secondaryEmail = data.secondaryEmail
	obj.twitter = data.twitter
	obj.description = data.description
	obj.referenceContact = data.referenceContact
	obj.dealcheck = dealcheck;
	obj.dealAmount = $('#dealAmountCheck').val();
	obj.dealName = $('#dealNameCheck').val();
	obj.dealClosingDate = $('#dealClosingDateCheck').val();
	obj.dealStage = $('#dealStageCheck').val();
	obj.dealCampaignSource = $('#dealCampaignSource').val();
	obj.dealContactRole = $('#dealContactRole').val();
	obj.probability = $('#probability').val();

	console.log('converted data response--------------' + JSON.stringify(obj));

	var validation = true;

	$.ajax({
		type: "POST",
		url: "view-crm-leads-detail-converted",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.message == "Success") {

				sessionStorage.setItem("contact_Id", response.body[0].contactId)
				sessionStorage.setItem("account_Id", response.body[0].contactId)

			}
		},
		error: function(data) {

			console.log(data);
		}
	})

}

function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	if (!tabElement) return;

	const tabId = tabElement.getAttribute("href");

	switch (tabId) {
		case "#leadMeeting":
			closeMeetingSection();
			break;
		case "#leadCall":
			closeCallSection();
			break;
		case "#leadTask":
			closeSection();
			break;
		case "#leadEmail":
			closeMailSection();
			break;
		case "#leadNote":
			closeNote();
			break;
	}

	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}

function onLeadStatusChange(status) {
	if (status == "TLSM00003") {
		$('#saveTask').prop("disabled", true);
	} else {
		$('#saveTask').prop("disabled", false);
	}

}
function excelDownload() {
	var params = {
		fileName: 'Contacted_Lead_list.csv', // Specify your custom filename here
	};
	gridOptionsLead.api.exportDataAsCsv(params);
}
/*Code For Product Tab*/
function toggleProductSection() {
	let selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadStatus = selectedData[0].leadStatus;
	if (leadStatus != "Contacted") {
		toastr.error("You Can't Add Product");
		return false;
	}
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#leadProduct").val('');
	$("#skuSelect").val('').trigger('change');
	CKEDITOR.instances?.itemDesc.setData("");
}
function cancelItemDetails() {

	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#leadProduct").val('').trigger('change');
	$("#skuSelect").val('').trigger('change');
	CKEDITOR.instances?.itemDesc.setData("");
	productGridOptions.api.deselectAll();
}
function onProductChange(id) {
	let descData = $('#leadProduct option:selected').text();
	if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData(descData || "");
	}
	$.ajax({
		type: "GET",
		url: "crm-lead-contacted-sku-list?id=" + id,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				var skuSelect = $('#skuSelect');
				skuSelect.empty();

				skuSelect.append('<option value="" disabled selected>Select SKU</option>');

				response.body.forEach(function(item) {
					let value = item[0];
					let text = item[1];
					skuSelect.append('<option value="' + value + '">' + text + '</option>');
				});
			}
		},
		error: function(e) {
			console.error("Error fetching SKU list:", e);
		}
	});
}
function addProductToGrid() {
	var productId = $('#leadProduct').val();
	var productName = $('#leadProduct option:selected').text();
	var skuId = $('#skuSelect').val();
	var skuName = $('#skuSelect option:selected').text();
	let itemDesc = CKEDITOR.instances['itemDesc'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');
	if (!productId) {
		toastr.error("Please select Product");
		return false;
	}
	if (!skuId) {
		toastr.error("Please select SKU.");
		return false;
	}
	if (!itemDesc.trim()) {
		toastr.error("Item Description Required");
		validation = false;
		return false;
	}

	var newRow = {
		productId: productId,
		productName: productName,
		skuId: skuId,
		skuName: skuName,
		itemDesc: itemDesc
	};

	console.log("New data ---", newRow);

	var selectedNodes = productGridOptions.api.getSelectedNodes();

	if (selectedNodes.length > 0) {
		let selectedNode = selectedNodes[0];
		selectedNode.setData(newRow);
	} else {
		let existingData = productGridOptions.api.getDisplayedRowCount() > 0
			? productGridOptions.api.getRenderedNodes().map(node => node.data)
			: [];

		existingData.push(newRow);
		productGridOptions.api.setRowData(existingData);
	}

	saveTableData();
}


function saveTableData() {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	if (selectedData.length === 0) {
		alert("Please select a lead.");
		return;
	}

	var leadId = selectedData[0].leadId;

	// Get all rows from the product grid
	var rowData = [];
	productGridOptions.api.forEachNode(function(node) {
		rowData.push({
			leadId: leadId,
			productId: node.data.productId,
			skuId: node.data.skuId,
			productName: node.data.productName,
			skuName: node.data.skuName,
			itemDesc: node.data.itemDesc

		});
	});
	console.log("row data ", rowData)
	/*if (rowData.length === 0) {
		alert("No product data to save.");
		return;
	}*/

	console.log("Saving data for leadId:", leadId, rowData);

	$.ajax({
		type: "POST",
		url: "crm-lead-contacted-add-product",
		contentType: "application/json",
		data: JSON.stringify(rowData),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success(response.message);
				cancelItemDetails();
				viewProductOnLead();
			} else {
				toastr.error(response.message);
			}
		},
		error: function(error) {
			console.error("Error saving data:", error);
			alert("An error occurred while saving the data.");
		}
	});
}
function viewProductOnLead(leadId) {
	if (!leadId) {
		var selectedNodes = gridOptionsLead.api.getSelectedNodes();
		if (!selectedNodes.length) {
			console.warn("No lead selected.");
			return;
		}
		var selectedData = selectedNodes.map(node => node.data);
		leadId = selectedData[0].leadId;
	}

	$.ajax({
		type: "GET",
		url: "crm-lead-contacted-get-product?leadId=" + leadId,
		success: function(response) {
			if (response.code === "success" && response.body && response.body.length > 0) {
				try {
					var parsed = JSON.parse(response.body[0]);
					if (parsed.productList && Array.isArray(parsed.productList)) {
						productGridOptions.api.setRowData(parsed.productList);
					} else {
						productGridOptions.api.setRowData([]);
					}
				} catch (err) {
					console.error("Error parsing product list:", err);
					productGridOptions.api.setRowData([]);
				}
			} else {
				productGridOptions.api.setRowData([]);
			}
		},
		error: function(e) {
			console.error("Error fetching SKU list:", e);
		}
	});
}
function editItemDetails() {
	toggleProductSection();
	let selectedData = productGridOptions.api.getSelectedRows();
	console.log(selectedData)
	setTimeout(() => {
		if (CKEDITOR.instances['itemDesc']) {
			CKEDITOR.instances['itemDesc'].setData(selectedData[0].itemDesc || "");
		}
	}, 500);

	$("#leadProduct").val(selectedData[0].productId).trigger('change');
	setTimeout(() => {
		$("#skuSelect").val(selectedData[0].skuId).trigger('change');
	}, 500);
}
function deleteProductOnclick() {
	let selectedData = productGridOptions.api.getSelectedRows();
	let selectedLeadData = gridOptionsLead.api.getSelectedRows();
	let productId = selectedData[0].productId;
	let skuId = selectedData[0].skuId;
	let leadId = selectedLeadData[0].leadId;

	$.ajax({
		type: "GET",
		url: "crm-lead-contacted-delete-product?productId=" + productId + "&skuId=" + skuId + "&leadId=" + leadId,
		success: function(response) {
			if (response.code === "success") {
				toastr.success(response.message);
				viewProductOnLead(leadId);
			}
		},
		error: function(e) {
			console.error("Error fetching SKU list:", e);
		}
	});
}
function removeSpecialChars(input) {
	input.value = input.value.replace(/[^a-zA-Z0-9 ,./]/g, '');
}
function onChangeOfMeetingStatus(value) {
	if (value == "Completed") {
		$('#remarkDiv').removeClass('d-none');
	} else {
		$('#remarkDiv').addClass('d-none');
	}
}
function callOnChange(value) {
	if (value == "Completed") {
		$('#callRemarkDiv').removeClass('d-none');
	} else {
		$('#callRemarkDiv').addClass('d-none');
	}
}
function formatDatee(date) {
	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
	const year = date.getFullYear();
	return `${day}-${month}-${year}`;
}
function filterLeadContactedView() {
	viewLeadAggridData();
	if (gridOptionsLead.api) {
		setTimeout(() => {
			const firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				firstRow.setSelected(true);
				gridOptionsLead.api.ensureIndexVisible(0);
			} else {
				console.log("No rows available to select.");
			}
		}, 300);
	} else {
		console.error("Grid API is not available.");
	}
}
function resetLeadContactedView() {
	let today = new Date();
	let currentYear = today.getFullYear();
	let currentMonth = today.getMonth();

	let fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
	let firstDayOfFY = new Date(fyStartYear, 3, 1);
	$("#fromDateLeadC").val(formatDatee(firstDayOfFY));
	$("#toDateLeadC").val(formatDatee(today));
	viewLeadAggridData();
	if (gridOptionsLead.api) {
		setTimeout(() => {
			const firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				firstRow.setSelected(true);
				gridOptionsLead.api.ensureIndexVisible(0);
			} else {
				console.log("No rows available to select.");
			}
		}, 300);
	} else {
		console.error("Grid API is not available.");
	}
}