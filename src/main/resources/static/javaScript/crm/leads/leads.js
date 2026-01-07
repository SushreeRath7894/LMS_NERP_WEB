let userId = '';
$(document).ready(function() {
	var leadgridDiv = document.querySelector('#myLeadGrid');
	new agGrid.Grid(leadgridDiv, gridOptionsLead);
	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateLeadCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		//minDate: new Date(),
		timepicker: false,
	}).on("change", function() {
		$('#toDateLead').val($(this).val());
	});

	$('#toDateLead').blur(function() {
		$("#toDateLeadCalendar").val($(this).val());
	});

	//
	$("#fromDateLeadCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		//minDate: new Date(),
		timepicker: false,
	}).on("change", function() {
		$('#fromDateLead').val($(this).val());
	});

	$('#fromDateLead').blur(function() {
		$("#fromDateLeadCalendar").val($(this).val());
	});
	const today = new Date();
	const currentYear = today.getFullYear();
	const currentMonth = today.getMonth();

	const fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
	const firstDayOfFY = new Date(fyStartYear, 3, 1);

	$("#fromDateLead").val(formatDate(firstDayOfFY));
	$("#toDateLead").val(formatDate(today));
	$("#leadStatus option[value='TLSM00004'], \
	  #leadStatus option[value='TLSM00010'], \
	  #leadStatus option[value='TLSM00009'], \
	  #leadStatus option[value='TLSM00011'], \
	  #leadStatus option[value='TLSM00005'], \
	  #leadStatus option[value='TLSM00006']").remove();

	$(".br-dis").prop("disabled", true);
	$("#openSection").prop("disabled", false);
	//var userId = $("#userId").val();
	setLeadTabAsDefault();
	userId = $("#userId").val();
	$("#leadExecutive").val(userId);

	var leadId = (localStorage.getItem('leadId'));
	if (leadId)
		//editLeadInfo(leadId);

		if (localStorage.getItem('edited') == 1) {

			$("#email").prop("disabled", true);
			$("#mobile").prop("disabled", true);
			$("#phone").prop("disabled", true);
		} else {
			$("#email").prop("disabled", false);
			$("#mobile").prop("disabled", false);
			$("#phone").prop("disabled", false);
		}

	localStorage.setItem('leadId', "");
	localStorage.setItem('edited', 0);

	// Call Back From Sales Quotation

	var conId = sessionStorage.getItem("callBackContactId");
	if (conId) {
		viewContactDetails(conId);
	}
	sessionStorage.setItem("callBackContactId", "");


	$("input[name=ReminderYesOrNo]:radio").click(function() {
		if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
			$('#isReminderOnDiv').show();

		} else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
			$('#isReminderOnDiv').hide();
			$('#reminderDateid').val("");
			$('#reminderTime').val("");
			$('#taskAlertBy').val("");


		}
	});
	$("#industry").select2({
		placeholder: "Select",
		allowClear: true,
		tags: true,
		createTag: function(params) {
			return {
				id: params.term,
				text: params.term + " (new)",
				newOption: true
			};
		},
		templateResult: function(data) {
			if (data.newOption) {
				return $('<span>' + data.text + '</span>');
			}
			return data.text;
		}
	});

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
		</div>`;

	var x = 1;

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


	/* calling function at the time of Page Loading */
	viewLeadAggridData();
	/*Decesion Makers Ag-Grid*/
	var rowDataDecesionMakers = [];
	var gridDiv = document.querySelector('#myGridDecesionMakers');
	new agGrid.Grid(gridDiv, gridOptionsDecesionMakers);
	var gridDiv1 = document.querySelector('#myGridMeeting');
	new agGrid.Grid(gridDiv1, gridOptionsMeeting);
	gridOptionsDecesionMakers.api.setRowData(rowDataDecesionMakers);

	$("#tabMenu .nav-link").click(function(event) {
		const $clickedTab = $(this);
		const tabId = $clickedTab.attr("href");

		const $activeTab = $("#tabMenu .nav-link.active");

		switch (tabId) {
			case "#leadDecision":
				closeSection();
				break;

		}

		$clickedTab.tab("show");

	});
});
function setLeadTabAsDefault() {
	document.querySelectorAll('.nav-link').forEach((link) => {
		link.classList.remove('active');
		link.setAttribute('aria-selected', 'false');
	});
	const leadTab = document.querySelector('#leadInformation-tab a.nav-link');
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
	const leadInformationTab = document.querySelector('#leadInformation');
	if (leadInformationTab) {
		leadInformationTab.classList.add('show', 'active');
	} else {
		console.error('Lead tab content pane not found.');
	}
}

$(document).ready(function() {

	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);


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


	/* Added Ck Editor in the mail */

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





});


function viewLeadAggridData() {
	var pages;
	var pageno = 1;
	var rowData = [];
	gridOptionsLead.api.setRowData(rowData);
	var fromDate = $("#fromDateLead").val();
	var toDate = $("#toDateLead").val();

	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
	}).then(function(data) {
		if (data.code === "Success") {
			var resp = JSON.parse(data?.body);

			if (resp && resp.length > 0) {
				var len = resp?.length;
				$('#totalReq').find('span').html(len);
				gridOptionsLead.api.setRowData(resp);
				$('#pagination,.ag-paging-panel').show();
				//createPagination(pages, pageno);
			} else {
				gridOptionsLead.api.setRowData([]);
				$('#pagination,.ag-paging-panel').hide();
			}
		} else {
			gridOptionsLead.api.setRowData([]);
			$('#pagination,.ag-paging-panel').hide();
		}

		$('.loader').hide();
	});

}


$(document).ready(function() {
	$("#leadTimeline").attr("disabled", true)
	$("#demo").hide();
	$("#demoSendEmail").hide();
	$("#macroContainer").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();

	$('.numberonly').keypress(function(e) {

		var charCode = (e.which) ? e.which : event.keyCode

		if (String.fromCharCode(charCode).match(/[^0-9]/g))

			return false;

	});

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

	var leadFirstName = localStorage.getItem('leadFirstName');
	var leadLastName = localStorage.getItem('leadLastName');
	var leadMobile = localStorage.getItem('leadPhone');
	//alert(leadName)

	if (leadFirstName) {

		newBtn();
		$("#firstName").val(leadFirstName);
		$("#lastName").val(leadLastName);
		$("#phone").val(leadMobile);

		localStorage.setItem('leadFirstName', "");
		localStorage.setItem('leadLastName', "");
		localStorage.setItem('leadPhone', "");

		$("#firstName").prop("disabled", true);
		$("#lastName").prop("disabled", true);
		//$("#phone").prop("disabled", false);
	}

});
function getLeadStatusHtml(value) {
	if (value === 'Qualified') {
		return `<span style="font-weight: bold;color: #4CAF50;">Qualified</span><i class="fa fa-check-circle-o" style="font-size: 12px;margin-left: 5px;color: #4CAF50"></i>`;
	} else if (value === 'Approval Pending') {
		return `<span style="font-weight: bold;color: #F29339;">Approval Pending <i class="fa fa-hourglass-half" aria-hidden="true"></i></span>`;
	} else if (value === 'Junk Lead' || value === 'Lost Lead') {
		return `<span style="font-weight: bold;color: #ff1240;">${value} <i class="fa fa-trash" aria-hidden="true"></i></span>`;
	} else if (value === 'Lead Won') {
		return `<span style="font-weight: bold;color: #e7b100;">${value} <i class="fa fa-trophy" aria-hidden="true"></i></span>`;
	} else {
		return `<span>${value}</span>`;
	}
}
/*----------------------------Lead Grid Columns--------------------------------*/

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
	/*cellRenderer: function(params) {
		var leadStatus = params.data.leadStatus;
		if (leadStatus === 'Junk Lead' || leadStatus === 'Lost Lead' || leadStatus === 'Approval Pending') {
			return '<a>' + params.data.leadName + '</a>';
		} else {
			return '<a onclick=editLeadInfo("'
				+ params.data.leadId
				+ '") href="javascript:void(0)">'
				+ params.data.leadName + '</a>';
		}

	}*/
},
{ headerName: "Lead Owner", field: "ownerName", width: 180, cellStyle: { textAlign: 'left' }, },
{ headerName: "Company", field: "company", width: 150, cellStyle: { textAlign: 'left' }, },
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
{ headerName: "Mobile Number", field: "mobile", width: 150, cellStyle: { textAlign: 'left' }, },
{ headerName: "Email", field: "email", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Created Date", field: "createdDate", width: 200, cellStyle: { textAlign: 'left' }, },
{ headerName: "Lead Source", field: "leadSource", width: 200, cellStyle: { textAlign: 'left' }, },
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
	onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 2000);
	},
};


// Functions for Lead Status 
function leadStatus() {

	var options = document.querySelectorAll("#parentFolderName option");
	options.forEach(function(option) {
		var code = option.getAttribute("data-code");
		if (code !== null && code !== "") {
			option.disabled = true;
			option.style.backgroundColor = '#dddddd';
			option.style.color = '#999999';
		}
	});
}

/* --------------------------------Ag-Grid ColumnDefs End------------------- */
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
		item['campaignId'] = $("#campaignId").val();
		item['campaignOwner'] = $("#campaignOwner").val();
		item['budgetedCost'] = $("#budgetedCost").val();
		item['actualCost'] = $("#actualCost").val();
		item['expectedResponse'] = $("#expectedResponse").val();
		item['numberSent'] = $("#numberSent").val();
		item['campExpectRevenue'] = $("#campExpectRevenue").val();
		item['description'] = $("#description").val();
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
				$('#myModalAddCampaign').modal('hide');


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

				$('#myModalAddTags').modal('hide');
			}
		},
		error: function(datas) {
		}
	})

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

function addMailInfo() {
	console.log('emails--------------', emails);
	var dataset = [];
	item = {};
	console.log(searchIDs);
	for (let i = 0; i < searchIDs.length; ++i) {

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

			item['leadId'] = searchIDs[i];
			item['fromEmail'] = $("#fromEmail").val();
			item['toMail'] = emails[i];
			item['mailSubject'] = $("#mailSubject").val();
			item['commentck'] = CKEDITOR.instances.commentck.getData();
			item['docName'] = $("#docName").val();
			item['documentList'] = uploadList;
			//dataset.push(item);
			saveMail(item);

		}, 1000)
	}

	console.log('dataset for task------' + JSON.stringify(item));
	saveMail(dataset);
}

function saveMail(item) {
	console.log("employee document11111111111---------" + JSON.stringify(item));
	console.log(item);
	$.ajax({
		type: "POST",
		url: "view-crm-contacts-add-emails-ajax",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(item),
		success: function(response) {

			if (response.message == "success") {
				$('#myModalAddEmail').modal('hide');
				closeModelEmail();
				location.reload();
			} else {


			}
		},
		error: function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
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


	$.ajax({
		type: "POST",
		url: "view-crm-leads-upload-file",
		enctype: "multipart/form-data",
		//contentType : false,
		data: fileName,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});
}

function openNavTask() {
	document.getElementById("mySidenavTask").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:420px;";

	document.getElementById("mainTask").style.width = "75%";
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



var searchIDs = [];
var emails = [];

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

var decesionMakersColumnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 20,
		minWidth: 20, // Prevent collapsing
		maxWidth: 50,
		suppressSizeToFit: true // Prevent resizing for checkbox column
	},
	{
		headerName: "Name",
		field: "dmName",
		width: 150,
		minWidth: 100,
		flex: 1 // Allow column to grow/shrink
	},
	{
		headerName: "Designation",
		field: "dmDesignation",
		width: 100,
		minWidth: 80,
		flex: 1
	},
	{
		headerName: "Email",
		field: "dmEmail",
		width: 200,
		minWidth: 150,
		type: "centerAligned",
		flex: 1
	},
	{
		headerName: "Phone No.",
		field: "dmPhone",
		width: 120,
		minWidth: 100,
		type: "centerAligned",
		flex: 1
	}
];

var gridOptionsDecesionMakers = {
	columnDefs: decesionMakersColumnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		minWidth: 50,
		flex: 1
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChangedDecesion,
	rowMultiSelectWithClick: false,
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
function onSelectionChangedDecesion() {
	var selectedRows = gridOptionsDecesionMakers.api.getSelectedRows();
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();

	if (selectedRows.length === 0) {
		$(".br-dis").prop("disabled", true);
		return;
	}

	var selectedData = selectedNodes.map(node => node.data);
	let leadStatusArray = selectedData.map(node => node.leadStatus);

	let isLostLead = leadStatusArray.includes("Lost Lead");

	$(".br-dis").prop("disabled", isLostLead);
}


var meetingColumnDefs = [

	{
		headerName: "Name",
		field: "dmName",
		width: 250,
		cellRenderer: function(params) {
			return `<a href="javascript:void(0);" class="editable-name" onclick="editDecesionMakersData('${params.data.dmId}')">${params.value}</a>`;
		}
	},
	{ headerName: "Designation", field: "dmDesignation", width: 215, },
	{ headerName: "Email", field: "dmEmail", width: 250, type: "centerAligned" },
	{ headerName: "Phone No.", field: "dmPhone", width: 250, type: "centerAligned" },
	{ headerName: "Created Date", field: "dmDate", width: 250, type: "centerAligned" },
	{
		headerName: "Action",
		field: "",
		width: 170,
		type: "centerAligned",
		cellRenderer: function(params) {
			return `<a href="javascript:void(0);"><i class="fas fa-trash delete-icon" onclick="deleteDecesionMakersData('${params.data.dmId}')"></i></a>`;
		}
	}
];

var gridOptionsMeeting = {
	columnDefs: meetingColumnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 195,
		height: 10
	},
	rowSelection: 'single',
	rowMultiSelectWithClick: true
};
function runMacro() {
	$("#macroContainer").show();
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

	$("#demo").hide();

	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function sendEmail() {

	$('#myModalAddEmail').modal('show');
	var emailString = emails.join(",");
	$("#toMail").val(emailString);
	$("#toMail").attr('disabled', 'disabled');


	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	//$("#myGrid").hide();
	$(".container").show();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").show();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function createTask() {

	$('#myModalAddTask').modal('show');
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$(".container-fluid").show();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").show();

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function closeModelTags() {

	$('#myModalAddTags').modal('hide');
	$(".container").show();
}
function closeModelEmail() {

	$('#myModalAddEmail').modal('hide');
	$(".container").show();
}
function closeModelTask() {

	$('#myModalAddTask').modal('hide');
	$(".container").show();
}

function closeModelCampaign() {

	$('#myModalAddCampaign').modal('hide');
	$(".container").show();
}

function createTags() {

	$('#myModalAddTags').modal('show');
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	//$("#myGrid").hide();
	$(".container").show();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").show();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function setReminder() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").show();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();

}

function massUpdate() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();


	$("#demoSetReminder").hide();
	$("#demoMassUpdate").show();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function addToCampaign() {
	$('#myModalAddCampaign').modal('show');
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	//$("#myGrid").hide();
	$(".container").show();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();


	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").show();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function updateResponse() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").show();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function printMailingLabes() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").show();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function mailMerge() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").show();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
}

function massConvert() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").show();
	$("#demoDeleteAction").hide();
}

function deleteAction() {
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

	$("#demo").hide();
	$("#macroContainer").hide();
	$("#demoSendEmail").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").show();
}

// for new button
function newBtn() {
	$("#leadStatus option[value='TLSM00004'], #leadStatus option[value='TLSM00009'], #leadStatus option[value='TLSM00011']").prop("disabled", true);
	$("#email").removeClass("is-valid is-invalid");
	/*doHideShow(["#deleteLead", "#runMacro", "#sentMail", "#createTask", "#tags", "#action", "#addLead", "#add",
		"#copy", "#delete", "#searchRowDiv", "#totalReq", "#statusDiv", "#idDiv",
		"#collapseFour", "#headingFour", "#myGridActivity", "#importLead", "#leadTimeline"], false);*/
	//$("#demo").show();
	//$("#myLeadGrid").hide();
	//$("#pagination").hide();
	var userId = $("#userId").val();
	$("#leadExecutive").val(userId);
	$("#leadStatus").val('TLSM00001').attr("disabled", true).css("background-color", "#f0f3f1").trigger('change');
	$("#project").attr("disabled", false)
	//sessionStorage.setItem("adminApprvStatus", );


}
function addNewLead() {
	$('#addNewLeadBtn').addClass('d-none');
	$("#cancelNewLead").removeClass("d-none")
	$("#cancelLeadBtn").removeClass("d-none")
	$("#addNewLead").addClass("d-none")
	$("#editBtn").addClass("d-none")
	$('#openSection').attr("disabled", false);
	$("#fileUpload").val('');
	gridOptionsLead.api.deselectAll();
	cancelBtn();
	newBtn();
	enableFields();
	$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4").text('');
	setTimeout(function() {
		$('#leadStatus').attr("disabled", true);
	}, 300); // 300ms delay
}
function cancelNewLead() {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	// Remove form validation and toggle button visibility
	$('div.formValidation').remove();
	$('#addNewLeadBtn').removeClass('d-none');
	$("#cancelNewLead").addClass("d-none");
	$("#cancelLeadBtn").addClass("d-none");
	$("#addNewLead").removeClass("d-none");

	if (gridOptionsLead.api) {
		if (selectedData.length > 0) {
			var leadId = selectedData[0].leadId;

			gridOptionsLead.api.deselectAll();

			gridOptionsLead.api.forEachNode(function(node) {
				if (node.data.leadId === leadId) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptionsLead.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		}
	}
}
// for cancel button
function cancelBtn() {
	$('div.formValidation').remove();
	$("#email").prop("disabled", false);
	$("#mobile").prop("disabled", false);
	$("#phone").prop("disabled", false);
	$("#company").prop("disabled", false);
	$("#firstName").prop("disabled", false);
	$("#lastName").prop("disabled", false);

	//doHideShow(["#demo", "#copy", "#deleteLead", "#totalReq", ".container", "#searchRowDiv", "#addLead", "#runMacro", "#sentMail", "#createTask", "#tags", "#importLead", "#leadTimeline"], true);

	$("#demo").hide();
	$('#myModalAddTags').modal('hide');
	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMassConvert").hide();
	$("#leadTimeline").show();
	$("#myLeadGrid").show();
	$("#pagination").show();


	$('#leadId').val("");
	$('#project').val("").trigger('change');
	$('#leadExecutive').val("");
	$('#company').val("");
	$('#firstName').val("");
	$('#lastName').val("");
	$('#title').val("");
	$('#email').val("");
	$('#phone').val("");
	$('#fax').val("");
	$('#mobile').val("");
	$('#website').val("");
	$('#referenceContact').val("");
	$('#leadSource').val("").trigger('change');
	$('#leadStatus').val("");
	$('#industry').val("").trigger('change');
	$('#noOfEmp').val("");
	$('#annualRevenue').val("");
	$('#ratings').val("").trigger('change');
	$('#emailOpt').val("");
	$('#skypeId').val("");
	$('#secondaryEmail').val("");
	$('#twitter').val("");
	$('#country').val("").trigger('change');
	$('#states').val("").trigger('change');
	$('#city').val("");
	$('#addressStreet').val("");
	$('#zip').val("");
	$('#description').val("");
	$('#createdBy').val("");
	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');
	$("#project").val("");
	$("#projectId").val("");
	gridOptionsDecesionMakers.api.setRowData([]);
	rowDataDecesionMakers = [];


	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$('#emailOpt').prop('checked', false);

	var selectElement = document.getElementById("leadStatus");

	selectElement.querySelectorAll("option").forEach(function(option) {
		option.removeAttribute("disabled");
	});

}


/*function deleteLeadInfo() {
	$("#deleteModal").show();
}

function deleteLeadOnclick() {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-deleteDetails?id=" + leadid,
		success: function(response) {

			if (response.message == "Success") {
				console.log(response);
				location.reload();

			}
		}
	});
}*/


function deleteLeadInfo() {
	// Get selected rows' IDs
	var selectedRows = gridOptionsLead.api.getSelectedNodes();
	var selectedIds = selectedRows.map(function(node) {
		return node.data.leadId; // Assuming leadId is the property that stores the ID
	});

	if (selectedIds.length > 0) {
		$("#deleteModal").show();
		$(".modal-backdrop").show();
		$("#deleteModalConfirmBtn").on("click", function() {
			deleteLeadOnclick(selectedIds);
			$("#deleteModal").hide();
		});
	} else {
		// Inform the user that no rows are selected for deletion
		$("#messageParagraph").text("Please select at least one row for deletion.");
	}


}

function deleteLeadOnclick(selectedIds) {
	$.ajax({
		type: "POST", // Use POST for bulk delete
		url: "view-crm-leads-deleteDetails?id=" + leadid,
		contentType: "application/json",
		data: JSON.stringify({ ids: selectedIds }),
		success: function(response) {
			if (response.message == "Success") {
				$("#deleteModal").hide();
				$("#messageParagraph").text("Lead Deleted Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#deleteLead').attr('disabled', true);
				$('#addLead').attr('disabled', false);
				$("#runMacro").attr("disabled", true);
				$("#sentMail").attr("disabled", true);
				$("#createTask").attr("disabled", true);
				$("#tags").attr("disabled", true);
				$("#action").attr("disabled", true);
				$(".actionCls").hide();

				console.log(response);
				var pages;
				var pageno = 1;
				var rowData = [];
				gridOptionsLead.api.setRowData(rowData);
				var fromDate = $("#fromDateLead").val();
				var toDate = $("#toDateLead").val();
				agGrid.simpleHttpRequest({
					url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
				}).then(function(data) {
					var resp = JSON.parse(data.body);
					if (data.body == null) {
						$('#totalReq').find('span').html(0);
					} else {
						var len = resp.length;
						$('#totalReq').find('span').html(len);

						gridOptionsLead.api.setRowData(resp);
						if (resp.length > 0) {

							$('#totalPageno').val(resp[0].totalPageno);
							pages = resp[0].totalPageno;

						}
						//createPagination(pages, pageno);
					}

					$('.loader').hide();

				});
			}
		}
	});
}
function disableFields() {
	let fieldIds = [
		"custName", "purchaseOrderId", "orderReceiveDate", "expectedShipmentDate", "project",
		"leadExecutive", "company", "firstName", "lastName", "title", "email",
		"phone", "project", "mobile", "website", "referenceContact", "leadSource",
		"industry", "noOfEmp", "annualRevenue", "ratings", "skypeId",
		"secondaryEmail", "twitter", "country", "states", "city", "addressStreet",
		"zip", "description", "saveLeadInfoBtn", "openSection", "leadStatus"
	];

	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	//$(".br-dis").prop("disabled", true);

}
function enableFields() {
	let fieldIds = [
		"custName", "purchaseOrderId", "orderReceiveDate", "expectedShipmentDate", "project",
		"leadExecutive", "company", "firstName", "lastName", "title", "email",
		"phone", "project", "mobile", "website", "referenceContact", "leadSource",
		"industry", "noOfEmp", "annualRevenue", "ratings", "skypeId",
		"secondaryEmail", "twitter", "country", "states", "city", "addressStreet",
		"zip", "description", "saveLeadInfoBtn", "openSection", "leadStatus"
	];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	//$(".br-dis").prop("disabled", false);
}

function cancelDeleteModalBtn() {
	$("#deleteModal").hide();
	$('#deleteLead').attr('disabled', false);
	$('#addLead').attr('disabled', true);
	$("#runMacro").attr("disabled", true);
	$("#sentMail").attr("disabled", true);
	$("#createTask").attr("disabled", true);
	$("#tags").attr("disabled", true);
	$("#action").attr("disabled", true);
	$(".actionCls").hide();

}
let ownerId = '';
let selectedStatusValue = '';
function editLeadInfo(id) {
	//disableFields();
	//alert('hello');
	$.ajax({
		type: "GET",
		url: "view-crm-leads-editDetails?id=" + id,
		success: function(response) {

			if (response.code == "Success") {
				newBtn();
				console.log('response------' + JSON.stringify(response));
				$("#leadStatus").attr("disabled", false).removeAttr("style");
				$('#leadId').val(response.body[0].leadId);
				$('#leadExecutive').val(response.body[0].leadOwner);
				$('#company').val(response.body[0].company);
				$('#firstName').val(response.body[0].firstName);
				$('#lastName').val(response.body[0].lastName);
				$('#title').val(response.body[0].title);
				$('#email').val(response.body[0].email);
				$('#phone').val(response.body[0].phone);
				//$('#projectId').val(response.body[0].fax);
				$('#project').val(response.body[0].fax).trigger('change');
				$('#mobile').val(response.body[0].mobile);
				$('#website').val(response.body[0].website);
				ownerId = response.body[0].leadOwner;
				/*Decesion Makers Data*/
				const referenceContactString = response.body[0].referenceContact;
				const referenceContactData = JSON.parse(referenceContactString);
				gridOptionsDecesionMakers.api.setRowData(referenceContactData);
				rowDataDecesionMakers = referenceContactData;
				//$('#referenceContact').val(response.body[0].referenceContact);
				$('#leadSource').val(response.body[0].leadSource).trigger('change');

				//Admin approval status
				sessionStorage.setItem("adminApprvStatus", response.body[0].adminApprvStatus);
				$("#project").prop('disabled', true);


				setTimeout(function() {
					$('#industry').val(response.body[0].industry).trigger('change');
				}, 1000);
				$('#noOfEmp').val(response.body[0].noOfEmp);
				$('#annualRevenue').val(response.body[0].annualRevenue).trigger('input');
				$('#ratings').val(response.body[0].ratings).trigger('change');
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
				$('#country').val(response.body[0].country).trigger('change');
				getStateDataOnEdit(response.body[0].states);
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
				$("#leadStatus option[value='TLSM00004'], \
					  #leadStatus option[value='TLSM00010'], \
					  #leadStatus option[value='TLSM00009'], \
					  #leadStatus option[value='TLSM00011'], \
					  #leadStatus option[value='TLSM00005'], \
					  #leadStatus option[value='TLSM00006']").remove();
				const $leadStatus = $("#leadStatus");
				const selectedStatusValue = response.body[0].leadStatus;
				const hiddenStatusMap = {
					"TLSM00010": "Approval Pending",
					"TLSM00004": "Qualified",
					"TLSM00005": "Proposed",
					"TLSM00006": "Negotiated",
					"TLSM00011": "Lead Won",
					"TLSM00009": "Lost Lead"
				};
				// Check if the selectedStatusValue exists in dropdown
				if ($leadStatus.find("option[value='" + selectedStatusValue + "']").length === 0) {
					$leadStatus.append(`<option value="${selectedStatusValue}">${hiddenStatusMap[selectedStatusValue] || "Unknown Status"}</option>`);
				}
				// Disable the dropdown for restricted statuses
				const disableStatuses = ["TLSM00003", "TLSM00004", "TLSM00005", "TLSM00006", "TLSM00007", "TLSM00008", "TLSM00009", "TLSM00011"];

				if (disableStatuses.includes(selectedStatusValue)) {
					$leadStatus.prop("disabled", true);
				}
				// Now select the value and disable dropdown if needed
				$leadStatus.val(selectedStatusValue).trigger("change");
				$('#reason').val(response.body[0].reason).prop("disabled", true);
				doHideShow(["#deleteLead", "#runMacro", "#sentMail", "#createTask", "#tags", "#action", "#addLead", "#leadTimeline"], false)

				disableFields();


			}

		}
	});
}

//Profile Image Upload & Delete Strats

function saveFileLeadImage() {
	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();
	const ext = fileName.split(".")[1];

	if (ext == "png" || ext == "jpg" || ext == "jpeg") {
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
		console.log('file data-------->', fileData);
		$.ajax({
			type: "POST",
			url: "view-crm-leads-upload-file",
			enctype: "multipart/form-data",
			contentType: false,
			data: fileData,
			processData: false,
			cache: false,
			success: function(response) {
				if (response.message != "")
					console.info("Image Added Successfully.")
			},
			error: function(e) {
				console.error(e)
			}
		});
	}
}

function deleteFileLeadImage() {
	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();
	$('#fileUpload').val("");
	$('#fileUpload').empty();
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

function showField() {
	$('#searchAnnualRevenue').toggle();
}
function showField1() {
	$('#searchCity').toggle();
}
function showField2() {
	$('#searchCompany').toggle();
}
function showField3() {
	$('#searchConvertedAccount').toggle();
}
function showField4() {
	$('#searchConvertedContact').toggle();
}
function showField5() {
	$('#searchConvertedDeal').toggle();
}
function showField6() {
	$('#searchCountry').toggle();
}
function showField7() {
	$('#searchCreatedBy').toggle();
}
function showField8() {
	$('#searchCreatedTime').toggle();
}
function showField9() {
	$('#searchEmail').toggle();
}
function showField10() {
	$('#searchEmailOptOut').toggle();
}
function showField11() {
	$('#searchFax').toggle();
}
function showField12() {
	$('#searchFirstName').toggle();
}
function showField13() {
	$('#searchIndustry').toggle();
}
function showField14() {
	$('#searchLastActivityTime').toggle();
}
function showField15() {
	$('#searchLastName').toggle();
}
function showField16() {
	$('#searchLeadConversionTime').toggle();
}
function showField17() {
	$('#searchLeadName').toggle();
}
function showField18() {
	$('#searchLeadOwner').toggle();
}
function showField19() {
	$('#searchLeadSource').toggle();
}
function showField20() {
	$('#searchLeadStatus').toggle();
}
function showField21() {
	$('#searchMobile').toggle();
}
function showField22() {
	$('#searchModifiedBy').toggle();
}
function showField23() {
	$('#searchModifiedTime').toggle();
}
function showField24() {
	$('#searchNoOfEmployees').toggle();
}
function showField25() {
	$('#searchPhone').toggle();
}
function showField26() {
	$('#searchRating').toggle();
}
function showField27() {
	$('#searchSalutation').toggle();
}
function showField28() {
	$('#searchSecondaryEmail').toggle();
}
function showField29() {
	$('#searchSkypeID').toggle();
}
function showField30() {
	$('#searchState').toggle();
}
function showField31() {
	$('#searchStreet').toggle();
}
function showField32() {
	$('#searchModifiedByTag').toggle();
}
function showField33() {
	$('#searchTitle').toggle();
}
function showField34() {
	$('#searchTwitter').toggle();
}
function showField35() {
	$('#searchUnsubscribedMode').toggle();
}
function showField36() {
	$('#searchUnsubscribedTime').toggle();
}
function showField37() {
	$('#searchWebsite').toggle();
}
function showField38() {
	$('#searchZipCode').toggle();
}


function filter() {
	obj = {};

	obj.leadOwner = $('#searchLeadOwner').val();
	obj.company = $('#searchCompany').val();
	obj.firstName = $('#searchFirstName').val();
	obj.lastName = $('#searchLastName').val();
	obj.title = $('#searchTitle').val();
	obj.email = $('#searchEmail').val();
	obj.phone = $('#searchPhone').val();
	obj.fax = $('#searchFax').val();
	obj.mobile = $('#searchMobile').val();
	obj.website = $('#searchWebsite').val();
	obj.leadSource = $('#searchLeadSource').val();
	obj.leadStatus = $('#searchLeadStatus').val();
	obj.industry = $('#searchIndustry').val();
	obj.noOfEmp = $('#searchNoOfEmployees').val();
	obj.annualRevenue = $('#searchAnnualRevenue').val();
	obj.ratings = $('#searchRating').val();
	obj.emailOpt = $('#searchEmailOptOut').val();
	obj.skypeId = $('#searchSkypeID').val();
	obj.secondaryEmail = $('#searchSecondaryEmail').val();
	obj.twitter = $('#searchTwitter').val();
	obj.country = $('#searchCountry').val();
	obj.states = $('#searchState').val();
	obj.city = $('#searchCity').val();
	obj.addressStreet = $('#searchStreet').val();
	obj.zip = $('#searchZipCode').val();
	obj.createdBy = $('#searchCreatedBy').val();

	obj.searchConvertedAccount = $('#searchConvertedAccount').val();
	obj.searchConvertedContact = $('#searchConvertedContact').val();
	obj.searchConvertedDeal = $('#searchConvertedDeal').val();
	obj.searchCreatedTime = $('#searchCreatedTime').val();
	obj.searchLastActivityTime = $('#searchLastActivityTime').val();
	obj.searchLeadConversionTime = $('#searchLeadConversionTime').val();
	obj.searchLeadName = $('#searchLeadName').val();
	obj.searchModifiedBy = $('#searchModifiedBy').val();
	obj.searchModifiedTime = $('#searchModifiedTime').val();
	obj.searchSalutation = $('#searchSalutation').val();
	obj.searchModifiedByTag = $('#searchModifiedByTag').val();
	obj.searchUnsubscribedMode = $('#searchUnsubscribedMode').val();
	obj.searchUnsubscribedTime = $('#searchUnsubscribedTime').val();

	console.log("response of obj----" + JSON.stringify(obj));

	$.ajax({
		type: "POST",
		url: "view-crm-leads-view-Data-search",
		contentType: "application/json",
		async: false,
		data: JSON.stringify(obj),
		success: function(response) {

			if (response.message == "Success") {

				console.log(response);

				div = '';
				$("#allDetails").empty();


				for (var i = 0; i < response.body.length; i++) {

					var im = response.body[i].imageName;
					var img = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="new_img"> <img src="' + im + '" width="70"  height= "70" alt="User" class="mr-3 img-thumbnail" /></a>';

					var oim = response.body[i].ownerImage;
					var oimg = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="' + oim + '" width="40" alt="User" class="mr-3 img-thumbnail" /></a>';



					id = response.body[i].leadId;
					//$(".mainDivRecord").remove(div);
					var fullName = response.body[i].firstName + '&nbsp' + response.body[i].lastName;
					var companyName = response.body[i].company;
					div = div + '<div class="card mb-2 mainDivRecord"><div class="card-body p-2 p-sm-3 d-flex"><div class="col-md-8">'
						+ '<div class="media forum-item d-flex">'
						+ '<div class="d-flex flex-column chk">'
						+ '<input type="checkbox" id="myCheckbox" name="myCheckbox" class="checkbox_check mr-5" value="' + id
						//+ '" onclick=clickCheckBox("'+id
						+ '" onclick=clickCheckBox("' + id + '","' + response.body[i].email
						+ '")>'
						+ '<a href="#" data-toggle="collapse" onclick=editLeadInfo("' + id
						+ '")><i class="fa fa-edit fa-icon img-hover" aria-hidden="true" '
						+ '></i></a><div>'
						+ '<a href="#" data-toggle="collapse" onclick=deleteLeadInfo("' + id
						+ '")><i class="fa fa-trash fa-icon img-hover" aria-hidden="true" '
						+ '></i></a></div></div>'
						+ img
						+ '<div>'
						+ '<div class="u_name">'
						+ '<a href="#" onclick=viewLeadDetails("' + id + '")>'
						+ fullName
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
						+ companyName
						+ '</u></span> <span>|</span><span>Title : '
						+ '<u>'
						+ response.body[i].title
						+ '</u></span><span>|</span><span>Lead Source : '
						+ '<u>'
						+ response.body[i].leadSource
						+ '</u></span> </div> </div> </div></div>'
						+ '<div class="col-md-4"><div class="media forum-item d-flex">'
						//+ '<a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="40" alt="User" class="mr-3 img-thumbnail" />'
						+ oimg
						+ '</a> <div> <div>'
						+ response.body[i].leadOwner
						+ '</div><div>'
						+ response.body[i].createdDate
						+ ' '
						+ response.body[i].createdTime
						+ '</div></div></div></div>'
						+ '</div></div>';





				}

				$("#allDetails").append(div);

				$("#runMacro").attr("disabled", true);
				$("#sentMail").attr("disabled", true);
				$("#createTask").attr("disabled", true);
				$("#tags").attr("disabled", true);
				$("#action").attr("disabled", true);


			}

		},
		error: function(data) {
		}
	});

}

function getStateDetails() {

	var cname = $('#country').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");

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
					$("#states").val(stateId).trigger('change');
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
	var dataset = [];
	console.log(searchIDs);
	for (let i = 0; i < searchIDs.length; ++i) {
		console.log(searchIDs[i]);
		item = {};
		item['leadId'] = searchIDs[i];
		item['taskContactName'] = $("#taskContactName").val();
		item['contactId'] = $("#contactId").val();
		item['taskAccountName'] = $("#taskAccountName").val();
		item['accountId'] = $("#accountId").val();
		item['taskStatus'] = $("#taskStatus").val();
		item['taskSubject'] = $("#taskSubject").val();
		item['taskDueDate'] = $("#dueDateid").val();
		item['taskPriority'] = $("#taskPriority").val();
		item['leadOwner'] = $("#taskOwner").val();

		var ReminderYesOrNo = $("input[name='ReminderYesOrNo']:checked").val();
		item['reminderYesOrNo'] = ReminderYesOrNo;
		item['reminderDate'] = $("#reminderDateid").val();
		item['reminderTime'] = $("#reminderTime").val();
		item['taskAlertBy'] = $("#taskAlertBy").val();

		var RepeateYesOrNo = $("input[name='RepeateYesOrNo']:checked").val();
		item['repeateYesOrNo'] = RepeateYesOrNo;
		item['description'] = $('#description').val();
		dataset.push(item);
	}

	console.log('dataset for task------' + JSON.stringify(dataset));
	saveTask(dataset);

}

function saveTask(dataset) {
	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-task",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {

				$("#messageParagraph").text("Data saved successfully");
				console.log(response);
				location.reload();
				//return false;
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

				agGrid.simpleHttpRequest({
					url: "manage-job-card"
				}).then(function(data) {
					var len = data.length;
					$('#totalReq').find('span').html(len);
					gridOptions.api.setRowData(data);
				})
				location.reload();
				gridOptions1.api.setRowData([]);
			}
		},
		error: function(datas) {
		}
	})

}



function validationUpdated11(msg, id) {
	$("#" + id).nextAll('div.formValidation').remove(); // Remove only validation messages with class 'formValidation'
	$('#' + id).on('input', function() {
		if ($.trim($(this).val())) {
			$(this).nextAll('div.formValidation').remove(); // Remove validation message if input is provided
		}
	});

	if (!$.trim($('#' + id).val())) {
		var div = "<div class='formValidation'>" + msg + "</div>";
		$('#' + id).after(div);
		return false;
	} else {
		return true;
	}
}
function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
}
function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}
function saveLeadInfo() {
    const tabs = ["#leadInformation", "#leadAddress", "#leadStatusTab"];

    for (let i = 0; i < tabs.length; i++) {
        if (!validateCurrentTab(tabs[i])) {
            $("#tabMenu .nav-link[href='" + tabs[i] + "']").tab("show");
            return;
        }
    }

    const decesionMakers = [];
    gridOptionsDecesionMakers.api.forEachNode(node => {
        decesionMakers.push(node.data);
    });

    if (!decesionMakers || decesionMakers.length === 0) {
        nextTab('leadDecision-tab');
        toastr.error('Decision Makers Required');
        return;
    }

    const obj = {};
    const adminApprovalStatus = sessionStorage.getItem("adminApprvStatus");

    obj.leadStatus = $('#leadStatus').val();
    obj.leadId = $('#leadId').val();

    let folder = [];
    const filePromises = [];

    const fileInput = document.getElementById("fileUpload");
    if (fileInput.files.length > 0) {
        for (let i = 0; i < fileInput.files.length; i++) {
            const uFile = fileInput.files[i];
            const fileName = uFile.name;

            if (fileName) {
                const fileReaderPromise = new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.readAsDataURL(uFile);
                    reader.onload = () => {
                        const base64Data = reader.result.split(",")[1];
                        folder.push({
                            fileName: fileName,
                            documentFile: [base64Data]
                        });
                        resolve();
                    };
                    reader.onerror = error => reject(error);
                });

                filePromises.push(fileReaderPromise);
            }
        }
    }

    if ($("#docListlen").val() > 0) {
        const div = document.getElementById('divFiles');
        const hiddenFiles = div.querySelectorAll('input[type="hidden"]');

        hiddenFiles.forEach(element => {
            const fileName = element.value;
            folder.push({
                fileName: fileName,
                imageNameEdit: fileName
            });
        });
    }

    Promise.all(filePromises).then(() => {
        obj.documentList = folder;

        if (obj.leadStatus === "TLSM00004" && adminApprovalStatus !== "true") {
            obj.adminApprvStatus = true;
            obj.statusUpdatedFrom = "";
        } else if (obj.leadStatus === "TLSM00010") {
            toastr.error("Waiting for Admin Approval!");
            return;
        } else {
            obj.leadOwner = $('#leadExecutive').val();
            obj.company = $('#company').val();
            obj.firstName = $('#firstName').val();
            obj.lastName = $('#lastName').val();
            obj.title = $('#title').val();
            obj.email = $('#email').val();
            obj.phone = $('#phone').val();
            obj.fax = $('#project').val();
            obj.mobile = $('#mobile').val();
            obj.website = $('#website').val();
            obj.referenceContact = JSON.stringify(decesionMakers);
            obj.leadSource = $('#leadSource').val();
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
            obj.statusUpdatedFrom = "";
            obj.noteId = $("#noteId").val();
            obj.titleId = $("#titleId").val();
        }

        console.log("Final Object before sending:", obj);

        $.ajax({
            type: "POST",
            url: "view-crm-leads-add-lead-details",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (response) {
                if (response.code === "Success") {
                    setLeadTabAsDefault();
                    $('#prevLead, #saveTask').addClass('d-none');
                    $('#addNewLead, #nextLead').removeClass('d-none');
                    toastr.success(response.message);
                    $('.loader').hide();
                    cancelBtn();

					var pageno = 1;
					var rowData = [];
					gridOptionsLead.api.setRowData(rowData);
					var fromDate = $("#fromDateLead").val();
					var toDate = $("#toDateLead").val();
					agGrid.simpleHttpRequest({
						url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate,
					}).then(function(data) {
						var resp = JSON.parse(data.body);
						gridOptionsLead.api.setRowData(resp);

						if (resp.length > 0) {
							$('#totalPageno').val(resp[0].totalPageno);
							var pages = resp[0].totalPageno;
							//createPagination(pages, pageno);
						}
						$('.loader').hide();
					});

                    setTimeout(() => {
                        gridOptionsLead.api.forEachNode(node => {
                            if (!obj.leadId) {
                                const firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
                                if (firstRow) firstRow.setSelected(true);
                            } else if (node.data.leadId === obj.leadId) {
                                node.setSelected(true);
                            }
                        });
                    }, 1000);

                    $("#leadStatus option[value='TLSM00004'], \
                       #leadStatus option[value='TLSM00010'], \
                       #leadStatus option[value='TLSM00009'], \
                       #leadStatus option[value='TLSM00011'], \
                       #leadStatus option[value='TLSM00005'], \
                       #leadStatus option[value='TLSM00006']").remove();

                    const industryDropdown = $('#industry');
                    const responseIndustryId = response.body;

                    if (industryDropdown.find(`option[value="${responseIndustryId}"]`).length === 0) {
                        const tagOption = $('#industry option[data-select2-tag="true"]').last();
                        if (tagOption.length > 0) {
                            const newText = tagOption.val();
                            tagOption.remove();
                            industryDropdown.append(`<option value="${responseIndustryId}">${newText}</option>`);
                        }
                    }
                }
            },
            error: function (data) {
                console.error("Error:", data);
            }
        });

    }).catch(error => {
        console.error("Error reading files:", error);
    });
}




function getAccountList() {
	//alert("JJJ");
	var search = $("#taskAccountName").val();

	if (search) {

		$.ajax({
			type: "POST",
			url: "view-crm-tasks-get-account-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
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


	} else {

		$("#accountId").val("");

		$("#taskAccountName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();

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
				if (response.message == "success") {

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



// Edit & stage change 
function editPage(id) {

	var editId = id.split(",");

	var pipelineId = editId[0];
	var modal = editId[1];
	$("#demo").show();

	$.ajax({
		type: "GET",
		url: "view-crm-pipeline-edit?id=" + pipelineId,
		async: false,
		success: function(response) {
			console.log(response);
			if (response.message == "Success") {
				agGridActivity(pipelineId);
				$("#pipelineId").val(response.body.pipelineId);
				$("#oppertunity").val(response.body.oppertunity);
				$("#expectedRevenue").val(response.body.expectedRevenue);
				$("#probability").val(response.body.probability);
				$('#customer').val(response.body.customer);
				$('#email').val(response.body.email);
				$('#phone').val(response.body.phone);
				$('#saleperson').val(response.body.saleperson);
				$('#salesTeam').val(response.body.salesTeam);
				$('#date').val(response.body.expectedClosing);
				let index = response.body.priority;
				$('#star' + index).prop('checked', true);
				$('#tags').val(response.body.tags);
				$('#company').val(response.body.company);
				$('#description').val(response.body.description);
				$('#campanyName').val(response.body.campanyName);
				$('#addressStreet').val(response.body.addressStreet);
				$('#addressStreet2').val(response.body.addressStreet2);
				$('#city').val(response.body.city);
				$('#states').val(response.body.states).trigger('change');
				$('#zip').val(response.body.zip);
				$('#country').val(response.body.country).trigger('change');
				$('#website').val(response.body.website);
				$('#language').val(response.body.language);
				$('#contactName').val(response.body.contactName);
				$('#tittle').val(response.body.tittle);
				$('#jobPosition').val(response.body.jobPosition);
				$('#mobile').val(response.body.mobile);
				$('#referdBy').val(response.body.referdBy);
				$('#campaign').val(response.body.campaign);
				$('#medium').val(response.body.medium);
				$('#source').val(response.body.source);
				$('#stageStatus').val(response.body.pipelineStatus);
				//for stage active status
				if (response.body.pipelineStatus == 1) {
					$("#stages1").attr("class", "btn btn-primary");
					$("#stages2").attr("class", "btn btn-outline-primary");
					$("#stages3").attr("class", "btn btn-outline-primary");
					$("#stages4").attr("class", "btn btn-outline-primary");
				}
				if (response.body.pipelineStatus == 2) {
					$("#stages2").attr("class", "btn btn-primary");
					$("#stages1").attr("class", "btn btn-outline-primary");
					$("#stages3").attr("class", "btn btn-outline-primary");
					$("#stages4").attr("class", "btn btn-outline-primary");
				}
				if (response.body.pipelineStatus == 3) {
					$("#stages3").attr("class", "btn btn-primary");
					$("#stages1").attr("class", "btn btn-outline-primary");
					$("#stages2").attr("class", "btn btn-outline-primary");
					$("#stages4").attr("class", "btn btn-outline-primary");
				}
				if (response.body.pipelineStatus == 4) {
					$("#stages4").attr("class", "btn btn-primary");
					$("#stages2").attr("class", "btn btn-outline-primary");
					$("#stages3").attr("class", "btn btn-outline-primary");
					$("#stages1").attr("class", "btn btn-outline-primary");
				}
				if (modal == '1') {
					$("#add").hide();
					$("#copy").hide();
					$("#delete").hide();
					$("#totalReq").hide();
					$("#myGrid").hide();
					$("#searchRowDiv").hide();
					$("#save").hide();
					$("#cancel").show();
					$("#statusDiv").show();
					$("#idDiv").show();
					$("#collapseFour").show();
					$("#headingFour").show();
					$("#myGridActivity").show();

					$("#pipelineHeadId").html(response.body.pipelineId);
					$("#oppertunity").attr('disabled', true);
					$("#expectedRevenue").attr('disabled', true);
					$("#probability").attr('disabled', true);
					$("#customer").attr('disabled', true);
					$("#email").attr('disabled', true);
					$("#phone").attr('disabled', true);
					$("#saleperson").attr('disabled', true);
					$("#salesTeam").attr('disabled', true);
					$("#date").attr('disabled', true);
					$(".rating").attr('disabled', true);
					$("#tags").attr('disabled', true);
					$("#company").attr('disabled', true);
					$("#website").attr('disabled', true);
					$("#language").attr('disabled', true);
					$("#contactName").attr('disabled', true);
					$("#tittle").attr('disabled', true);
					$("#jobPosition").attr('disabled', true);
					$("#mobile").attr('disabled', true);
					$("#referdBy").attr('disabled', true);
					$("#campaign").attr('disabled', true);
					$("#medium").attr('disabled', true);
					$("#source").attr('disabled', true);


				} else {

					$("#add").hide();
					$("#copy").hide();
					$("#delete").hide();
					$("#totalReq").hide();
					$("#myGrid").hide();
					$("#searchRowDiv").hide();
					$("#statusDiv").hide();
					$("#idDiv").hide();
					$("#collapseFour").hide();
					$("#headingFour").hide();
					$("#myGridActivity").hide();
					$("#mySidenav").hide();

					$("#oppertunity").attr('disabled', false);
					$("#expectedRevenue").attr('disabled', false);
					$("#probability").attr('disabled', false);
					$("#customer").attr('disabled', false);
					$("#email").attr('disabled', false);
					$("#phone").attr('disabled', false);
					$("#saleperson").attr('disabled', false);
					$("#salesTeam").attr('disabled', false);
					$("#date").attr('disabled', false);
					$(".rating").attr('disabled', false);
					$("#tags").attr('disabled', false);
					$("#company").attr('disabled', false);
					$("#website").attr('disabled', false);
					$("#language").attr('disabled', false);
					$("#contactName").attr('disabled', false);
					$("#tittle").attr('disabled', false);
					$("#jobPosition").attr('disabled', false);
					$("#mobile").attr('disabled', false);
					$("#referdBy").attr('disabled', false);
					$("#campaign").attr('disabled', false);
					$("#medium").attr('disabled', false);
					$("#source").attr('disabled', false);

				}
			}
		}
	})
}

/***************************        stage change         ****************************************/

function addStages(event) {

	var pipelineId = $("#pipelineId").val();
	var stages = event.currentTarget.value;
	var previousStage = $("#stageStatus").val();
	if (stages != previousStage) {
		$
			.ajax({
				type: "GET",
				url: "view-crm-pipeline-addStages?id=" + pipelineId
					+ "&stage=" + stages + "&previousStage="
					+ previousStage,
				success: function(response) {

					if (response.message == "success") {

						if (stages == 1) {
							$("#stages1").attr("class",
								"btn btn-primary");
							$("#stages2").attr("class",
								"btn btn-outline-primary");
							$("#stages3").attr("class",
								"btn btn-outline-primary");
							$("#stages4").attr("class",
								"btn btn-outline-primary");
						}
						if (stages == 2) {
							$("#stages2").attr("class",
								"btn btn-primary");
							$("#stages1").attr("class",
								"btn btn-outline-primary");
							$("#stages3").attr("class",
								"btn btn-outline-primary");
							$("#stages4").attr("class",
								"btn btn-outline-primary");
						}
						if (stages == 3) {
							$("#stages3").attr("class",
								"btn btn-primary");
							$("#stages1").attr("class",
								"btn btn-outline-primary");
							$("#stages2").attr("class",
								"btn btn-outline-primary");
							$("#stages4").attr("class",
								"btn btn-outline-primary");
						}
						if (stages == 4) {
							$("#stages4").attr("class",
								"btn btn-primary");
							$("#stages2").attr("class",
								"btn btn-outline-primary");
							$("#stages3").attr("class",
								"btn btn-outline-primary");
							$("#stages1").attr("class",
								"btn btn-outline-primary");
						}
						agGrid
							.simpleHttpRequest(
								{
									url: 'view-crm-pipeline-activity-through-ajax?id='
										+ pipelineId
								}).then(
									function(data) {
										activityOptions.api
											.setRowData(data);
									});

					} else {
						swal({
							title: response.code,
							text: response.message,
							type: "warning"
						});
					}
				},
				error: function(data) {
					console.log(data);
				}
			});
	}

}


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

function viewContactDetails(id) {

	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-contacts-detail?id=" + id;
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
function viewImage(id) {
	window.open("/document/crm/" + id, '_blank');
}
function editNote(id) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-edit-note?id=" + id,
		success: function(response) {
			const resp = JSON.parse(response.body[0]);
			console.log("note data==", resp)
			if (!resp.NoteDetails) {
				// If no NoteDetails, reset fields and UI
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
			console.log('Note Details----------->', noteDetails);
			$('#leadNoteId').val(noteDetails.noteId || '');
			$('#titleId').val(noteDetails.noteTitle || '');
			$('#noteId').val(noteDetails.noteDesc || '');
			$('#documentName').val(noteDetails.noteDocName || '');

			const doclist = Array.isArray(noteDetails.documentList) ? noteDetails.documentList : [];
			const docListLen = doclist.length;
			$('#docListlen').val(docListLen);

			if (docListLen > 0) {
				let fileHtml = '<table id="fileTable"><tbody>';
				console.log("doclist=======>>>>", doclist)
				for (let i = 0; i < doclist.length; i++) {
					const fileName = doclist[i].fileName;
					const desc = doclist[i].desc;
					const fileUrl = doclist[i].fileUrl;
					const ext = fileName.split('.').pop().toLowerCase();
					let fileIcon = '';

					// Determine file icon
					if (["jpg", "jpeg", "png"].includes(ext)) {
						fileIcon = `<i class="bi bi-image" style="color: blue; cursor: pointer;" onclick="viewImage('${fileName}')"></i>`;
					} else if (ext === "pdf") {
						fileIcon = `<i class="bi bi-file-earmark-pdf" style="color: red; cursor: pointer;" onclick="viewImage('${fileName}')"></i>`;
					} else {
						fileIcon = `<i class="bi bi-file-earmark" style="color: gray; cursor: pointer;" onclick="viewImage('${fileName}')"></i>`;
					}

					// File row with Name, View Icon, and Delete Icon
					fileHtml += `
                        <tr id="filename${i}">
                            <td class="border-0 fs--13">${desc}</td>
                            <td class="border-0 fs--13">${fileIcon}</td>
                            <td class="border-0 fs--13">
                                <i class="bi bi-trash" style="cursor: pointer; color: red;" onclick="deleteDocfile(${i})"></i>
                            </td>
                        </tr>`;
				}

				fileHtml += '</tbody></table>';
				$("#divFiles").html(fileHtml);
			} else {
				$("#doctbodyData").html(getEmptyUploaderHtml());
			}
		},
		error: function(err) {
			console.error("Error fetching note details:", err);
		}
	});
}

// Function to return the default empty uploader UI
function getEmptyUploaderHtml() {
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
}




var leadid = "";
var contactId = "";
function onSelectionChanged() {
	setLeadTabAsDefault();

	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	let leadStatus = selectedData.map(node => node.leadStatus);

	if (selectedData.length > 0) {
		$("#openSection").prop("disabled", leadStatus == "Lost Lead" ? true : false);
		leadStatus == "Created" || leadStatus == "Contacted" ? $("#editBtn").removeClass("d-none") : $("#editBtn").addClass("d-none");
		leadid = selectedData.map(node => node.leadId);
		editLeadInfo(leadid);
		//editNote(leadid);

		contactId = selectedData.map(node => node.contactId);
		$("#contactId").val(contactId);

		var selectedRows = gridOptionsLead.api.getSelectedRows();
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4").text(selectedRows[0]?.leadName);

		var id = "";

		for (var i = 0; i < selectedRows.length; i++) {
			id = id + '"' + selectedRows[i].leadId + '",';
		}

		if (id.length > 0) {
			id = id.substring(0, id.length - 1);
		}
		$('#addNewLeadBtn').removeClass('d-none');
		$("#cancelLeadBtn").addClass("d-none")
	} else {
		$('#addNewLeadBtn').addClass('d-none');
		$("#cancelLeadBtn").removeClass("d-none");
		$("#addNewLead").addClass("d-none");
		cancelBtn();
		newBtn();
		$("#employeeNameTop,#employeeNameTop1,#employeeNameTop2,#employeeNameTop3,#employeeNameTop4").text('');
		$("#editBtn").addClass("d-none");
		enableFields();
	}
}




function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}


function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	gridOptionsLead.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = gridOptionsLead.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);
}




//Pagination
var pages;
function createPagination(pages, page) {

	if ($("#totalPageno").val() == '') {
		var pages = 10;
	} else {
		var pages = $("#totalPageno").val();
	}

	var str = '<ul>';
	var active;
	var pageCutLow = page - 1;
	var pageCutHigh = page + 1;
	// Show the Previous button only if you are on a page other than the first
	if (page > 1) {
		str += '<li class="page-item previous no"><a onclick="createPagination(pages, ' + (page - 1) + ')">Previous</a></li>';
	}
	// Show all the pagination elements if there are less than 6 pages total
	if (pages < 6) {
		for (let p = 1; p <= pages; p++) {
			active = page == p ? "active" : "no";
			str += '<li class="' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';
		}
	}
	// Use "..." to collapse pages outside of a certain range
	else {
		// Show the very first page followed by a "..." at the beginning of the
		// pagination section (after the Previous button)
		if (page > 2) {
			str += '<li class="no page-item"><a onclick="createPagination(pages, 1)">1</a></li>';
			if (page > 3) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page - 2) + ')">...</a></li>';
			}
		}
		// Determine how many pages to show after the current page index
		if (page === 1) {
			pageCutHigh += 2;
		} else if (page === 2) {
			pageCutHigh += 1;
		}
		// Determine how many pages to show before the current page index
		if (page === pages) {
			pageCutLow -= 2;
		} else if (page === pages - 1) {
			pageCutLow -= 1;
		}
		// Output the indexes for pages that fall inside the range of pageCutLow
		// and pageCutHigh
		for (let p = pageCutLow; p <= pageCutHigh; p++) {
			if (p === 0) {
				p += 1;
			}
			if (p > pages) {
				continue
			}

			active = page == p ? "active" : "no";
			str += '<li class="page-item ' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';

		}
		// Show the very last page preceded by a "..." at the end of the pagination
		// section (before the Next button)
		if (page < pages - 1) {
			if (page < pages - 2) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page + 2) + ')">...</a></li>';
			}
			str += '<li class="page-item no"><a onclick="createPagination(pages, pages)">' + pages + '</a></li>';
		}
	}
	// Show the Next button only if you are on a page other than the last
	if (page < pages) {
		str += '<li class="page-item next no"><a onclick="createPagination(pages, ' + (page + 1) + ')">Next</a></li>';
		changePagination(page);
	} else if (page <= pages) {
		//str += '<li class="page-item next no"><a onclick="createPagination(pages, '+(page+1)+')">Next</a></li>';
		changePagination(page);
	}
	str += '</ul>';
	// Return the pagination string to be outputted in the pug templates
	document.getElementById('pagination').innerHTML = str;
	return str;

}


function changePagination(page) {
	$('.loader').show();
	var pageno = page;
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId,
	}).then(function(data) {
		var resp = JSON.parse(data.body);

		var len = resp.length;
		$('#totalReq').find('span').html(len);

		gridOptionsLead.api.setRowData(resp);

		if (resp.length > 0) {

			$('#totalPageno').val(resp[0].totalPageno);
			pages = resp[0].totalPageno;

		}
		$('.loader').hide();
	});
}

/**************************************************** Export Import CSV Ends */

function openImportModal() {
	$("#importModal").show();
	$('#csvName').html('');
	$(".saveBtndiv").hide();
}



function viewTimeline() {

	var selectedRows = gridOptionsLead.api.getSelectedRows();

	// Check the number of selected rows
	if (selectedRows.length > 1) {
		$("#messageParagraph").html("<strong>You can see only one lead's timeline at a time.</strong>");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return;
	}

	// Open the modal only if one row is selected
	$("#myModalTimeline").modal('show');

	var selectedRowsString = selectedRows.map(row => row.leadId).join(',');
	var selectedLeadNames = selectedRows.map(row => row.leadName).join(',');
	var selectedLeadCompany = selectedRows.map(row => row.company).join(',');
	$("#leadNameOnHead").text(selectedLeadCompany);

	// Define a mapping of stages to colors
	var stageColors = {
		'Created': '#41516C', // Created
		'Attempted to contact': '#FBCA3E', // Attempted to contact
		'Contacted': '#1B5F8C', // Contacted
		'Qualified': '#02bf37', // Qualified
		'Proposed': '#02b8bf',
		'Order Promised': '#bf8802',
		'Order Booked': '#7802bf',
		'Lead Won': '#da9100 ',
		'Junk Lead': '#ff1240', // Junk Lead
		'Lost Lead': '#ee2f54'  // Lost Lead
	};

	console.log("Stage Colors:", stageColors);

	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-lead-status-timeline?id=" + selectedRowsString,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				var timelineData = JSON.parse(response.body[0]);

				console.log("Timeline Data:", timelineData);

				$("#statusTimeline ul").empty();

				timelineData.forEach(function(item) {
					var listItem = $("<li>");

					listItem.append("<div class='date'>" + item.leadstatus + "</div>");
					listItem.append("<div class='title'>" + item.createdOn + "</div>");
					listItem.append("<div class='descr'><strong>Description: </strong>" + getDescription(item.leadstatus) + "</div>");
					if (item.leadstatus == 'Junk Lead') {
						listItem.append("<div class='created-by'><strong>Rejected By: </strong>" + item.createdBy + "</div>");
					} else if (item.leadstatus == 'Qualified') {
						listItem.append("<div class='created-by'><strong>Approved By: </strong>" + item.createdBy + "</div>");
					}
					else {
						listItem.append("<div class='created-by'><strong>Executive: </strong>" + item.createdBy + "</div>");
					}
					//listItem.append("<div class='created-by'><strong>Executive: </strong>" + item.createdBy + "</div>");

					var accentColor = stageColors[item.leadstatus];
					console.log("Accent Color for", item.leadstatus, ":", accentColor);
					if (accentColor) {
						listItem.css("--accent-color", accentColor);
					}

					$("#statusTimeline ul").append(listItem);
				});
			}
		},
		error: function(data) { }
	});
}


// Function to get description based on lead status
function getDescription(leadstatus) {
	switch (leadstatus) {
		case "Created":
			return "The lead has been created in the system but has not yet been contacted.";
		case "Attempted to contact":
			return "An attempt has been made to contact the lead, but there has been no response.";
		case "Contacted":
			return "The lead has been successfully contacted.";
		case "Qualified":
			return "The lead has been qualified as a potential customer.";
		case "Junk Lead":
			return "The lead is not qualified or not relevant to the business.";
		case "Lost Lead":
			return "The lead has been lost, meaning the opportunity to convert them into a customer has been missed.";
		case 'Proposed':
			return "The proposal has been submitted to the lead.";
		case 'Order Promised':
			return "The order has been promised to the lead.";
		case 'Order Booked':
			return "The order has been booked with the lead.";
		case 'Lead Won':
			return "The lead has been successfully converted into a customer.";
		default:
			return "No description found";
	}
}


function closeImportModal() {
	$("#importModal").hide();
}
function exportLeadDetails() {

	let allColumns = gridOptionsLead.columnApi.getAllColumns();
	let exportColumns = allColumns.filter(col => !col.userProvidedColDef.suppressExcelExport);
	gridOptionsLead.api.exportDataAsCsv({
		columnKeys: exportColumns,
	});
}

let item = {};
function onClickBrowse(event) {
	var file = event.target.files[0].name;
	var fileNameWithIcon = '<i class="fas fa-file-alt showFile" style="color: #18d07b;"></i>&nbsp;' + file; // Example icon, adjust as needed
	$("#csvName").html(fileNameWithIcon);

	var uFile = $('#file-Upload')[0].files[0];
	var fileName = uFile.name;
	let data = [];
	let x = [];
	var uploadList = [];

	if (fileName != '' && fileName != 'undefined' && fileName != null) {
		let reader = new FileReader();
		reader.readAsDataURL(uFile);
		let obj = {};
		reader.onload = function() {
			data = reader.result.split(",");
			x.push(data[1]);
		};
		uploadData = {};

		uploadData['documentFile'] = x;
		uploadData['fileName'] = fileName;
		uploadList.push(uploadData);
	}

	item.leadOwner = $('#userId').val();
	item.documentList = uploadList;
	//saveLeadCSVDtls(item);
	console.log(item, 'item@@@@@@@@')
	$(".saveBtndiv").show();

}

function saveLeadCsvData() {
	saveLeadCSVDtls(item);
}


function saveLeadCSVDtls(item) {

	console.log(item, 'item@@@@@@@@')
	$.ajax({
		type: "POST",
		url: "view-crm-leads-save-csv",
		contentType: "application/json",
		data: JSON.stringify(item),
		success: function(response) {
			closeImportModal();
			showSnackbar("Leads Data Imported Successfully");

			var pages;
			var pageno = 1;
			var rowData = [];
			gridOptionsLead.api.setRowData(rowData);

			agGrid.simpleHttpRequest({
				url: "view-crm-leads-view-Data?pageno=" + pageno + "&userId=" + userId,
			}).then(function(data) {
				var resp = JSON.parse(data.body);

				var len = resp.length;
				$('#totalReq').find('span').html(len);
				gridOptionsLead.api.setRowData(resp);
				if (resp.length > 0) {

					$('#totalPageno').val(resp[0].totalPageno);
					pages = resp[0].totalPageno;

				}
				//createPagination(pages, pageno);
			});
		}
	})
}


// Function to check if a value has a specific length
function isValidLength(value, length) {
	return value.trim().length === length;
}

// Function to check if the email is in a valid format
function isValidEmail(email) {
	// Regular expression for a basic email format check
	var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return emailRegex.test(email);
}


/**
 * Project Creation & selection
 */
function getProjectList() {
	var search = $("#project").val();
	if (search) {

		$
			.ajax({
				type: "POST",
				url: "view-quotation-get-project-list",
				dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {
						console.log("response data"
							+ JSON.stringify(response))
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list4" >';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#fff;     background-color: #0909e4;" class="autocompletedata cp" onClick="selectAutocompleteValuep(\''
									+ response.body[i].project
									+ '\',\''
									+ response.body[i].projectName
									+ '\')">'
									+ response.body[i].projectName
									+ '</li>';
							}


							content += '<li style="margin-left:-30px;background-color:#082958;color:#fff;" onclick="addProject();"><a class="addCust" href="#">Add Project</a>'
								+ '</li>';
							content += '</ul>';

							//console.log("content " + content)
							$("#suggesstion-box4_").show();
							$("#suggesstion-box4_").html(content);

						} else {
							$("#search").css("background", "#FFF");

							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc;      background-color: #0909e4;" onClick="selectAutocompleteValueep()">'
								+ "No Data Found" + '</li>';
							content += '<li style="margin-left:-30px;"  onclick="addProject();"><a class="addCust" href="#">Add Project</a>'
								+ '</li>';
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
	} else {
		$("#suggesstion-box4_").hide();
	}

}

function selectAutocompleteValueep() {
	$("#projectId").val("");
	$("#project").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box4_").hide();
}

function selectAutocompleteValuep(project, projectName) {
	$("#projectId").val(project);
	$("#project").val(projectName);
	$("#search").val(projectName);
	$("#search").attr('data-procat', project);
	$("#suggesstion-box4_").hide();
}

function addProject() {
	sessionStorage.setItem("projectFromCRM", "true");
	AddProject()
}

/*mail validation*/
function validateEmail() {
	var email = $("#email").val();
	var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	if (!emailRegex.test(email)) {
		return false;
	}
	return true;
}
function validateEmail1() {
	function validateEmail(email) {
		var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailRegex.test(email);
	}

	$("#email").on("input", function() {
		var email = $(this).val();
		var isValid = validateEmail(email);
		if (isValid) {
			$(this).removeClass("is-invalid").addClass("is-valid");
		} else {

			$(this).removeClass("is-valid").addClass("is-invalid");
		}
	});
}

/*For Lead Organization Makers*/

function openNavReference() {
	var currentDate = new Date();
	getCurrentDate = formatDatee(currentDate);
	$('#dmDate').val(getCurrentDate);
	document.getElementById("mySidenav1").style.cssText = "width: 25%; position: absolute; overflow: hidden; height: auto; top: 1181px;";
	document.getElementById("desecion-makers-div").style.width = "75%";
}

function closeNavReference() {
	document.getElementById("mySidenav1").style.width = "0";
	document.getElementById("desecion-makers-div").style.width = "100%";
}


var rowDataDecesionMakers = [];
function saveDecesionMakersData() {

	var dmId = $('#dmId').val();
	var dmName = $('#dmName').val();
	var dmDesignation = $('#dmDesignation').val();
	var dmEmail = $('#dmEmail').val();
	var dmPhone = $('#dmPhone').val();


	var validation = true;

	var newRow = {
		dmId: dmId || Date.now().toString(),
		dmName: dmName,
		dmDesignation: dmDesignation,
		dmEmail: dmEmail,
		dmPhone: dmPhone

	};
	var existingRowIndex = rowDataDecesionMakers.findIndex(row => row.dmId === dmId);


	if (newRow.dmName == null || newRow.dmName == "") {
		toastr.error("Full Name Required");
		return false;
	}

	if (newRow.dmDesignation == null || newRow.dmDesignation == "") {
		toastr.error("Designation Required");
		return false;
	}

	if (validation) {
		if (existingRowIndex !== -1) {
			rowDataDecesionMakers[existingRowIndex] = newRow;
		} else {
			rowDataDecesionMakers.push(newRow);
		}
		gridOptionsDecesionMakers.api.setRowData(rowDataDecesionMakers);

		$('#dmId').val('');
		$('#dmName').val('');
		$('#dmDesignation').val('');
		$('#dmEmail').val('');
		$('#dmPhone').val('');
		$('#dmDate').val('');
		$('.br-m-btn').prop('disabled', true);

		closeSection();
	}
};

function editDecesionMakersData() {
	toggleSection();
	//var selectedRows = gridOptionsDecesionMakers.api.getSelectedRows();
	var selectedNodes = gridOptionsDecesionMakers.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var dmId = selectedData[0].dmId;
	var selectedRow = rowDataDecesionMakers.find(row => row.dmId === dmId);
	if (!selectedRow) {
		//alert('Row not found');
		return;
	}


	$('#dmId').val(selectedRow.dmId);
	$('#dmName').val(selectedRow.dmName);
	$('#dmDesignation').val(selectedRow.dmDesignation);
	$('#dmEmail').val(selectedRow.dmEmail);
	$('#dmPhone').val(selectedRow.dmPhone);


	//openNavReference();
}

function deleteDecesionMakersData() {
	var selectedNodes = gridOptionsDecesionMakers.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var dmId = selectedData[0].dmId;
	rowDataDecesionMakers = rowDataDecesionMakers.filter(row => row.dmId !== dmId);
	gridOptionsDecesionMakers.api.setRowData(rowDataDecesionMakers);
	// Clear the form fields after deleting
	$('#dmId').val('');
	$('#dmName').val('');
	$('#dmDesignation').val('');
	$('#dmEmail').val('');
	$('#dmPhone').val('');
	$('#dmDate').val('');
	$(".br-dis").prop("disabled", true);
}

function formatDatee(date) {
	var year = date.getFullYear();
	var month = padZeros(date.getMonth() + 1);
	var day = padZeros(date.getDate());
	return year + '-' + month + '-' + day;
}

function padZeros(num) {
	return (num < 10 ? '0' : '') + num;
}
function onLeadStatusChange(status) {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	$('#reason').val('');
	$('#reason').prop("disabled", false);

	if (status == "TLSM00009") {
		$('#reasonField').removeClass('d-none');
	} else {
		$('#reasonField').addClass('d-none');
	}
	/*if (status == "TLSM00001" && selectedData.length > 0) {
		$('#saveLeadInfoBtn').prop("disabled", true);
	} else {
		$('#saveLeadInfoBtn').prop("disabled", false);
	}*/
}
//function for download
function excelDownload() {
	var params = {
		fileName: 'Lead_list.csv', // Specify your custom filename here
	};
	gridOptions.api.exportDataAsCsv(params);
}
function editLeadOnclick() {
	$("#editBtn").addClass("d-none");
	$("#addNewLeadBtn").addClass("d-none");
	$("#cancelLeadBtn").removeClass("d-none");
	enableFields();
	$('#saveLeadInfoBtn').attr("disabled", false);
}
function formatDate(date) {
	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
	const year = date.getFullYear();
	return `${day}-${month}-${year}`;
}
function filterLeadView() {
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
function resetLeadView() {
	let today = new Date();
	let currentYear = today.getFullYear();
	let currentMonth = today.getMonth();

	let fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
	let firstDayOfFY = new Date(fyStartYear, 3, 1);
	$("#fromDateLead").val(formatDate(firstDayOfFY));
	$("#toDateLead").val(formatDate(today));
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