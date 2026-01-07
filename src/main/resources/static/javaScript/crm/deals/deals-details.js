(function($) {

	function initOwlCarousel() {
		$('.therapy-slider').owlCarousel(
			{
				loop: false,
				autoplay: false,
				nav: true,
				autoplayHoverPause: true,
				responsiveClass: true,
				dots: false,
				margin: 30,
				nav: true,
				navText: ["<i class='bi bi-chevron-left'></i>", "<i class='bi bi-chevron-right'></i>"],
				responsive: {
					0: {
						items: 1,
					},
					768: {
						items: 2,
					},
					1200: {
						items: 4,
					}
				}
			}


		);
	}

	$(document).ready(function() {
		initOwlCarousel();
	});
})(jQuery);



$(document).ready(function() {
	watchLocationPermission();


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

				/* 	agGrid.simpleHttpRequest({
						url : "manage-job-card"
					}).then(function(data) {
						var len = data.length;
						$('#totalReq').find('span').html(len);
						gridOptions.api.setRowData(data);
					})
					//location.reload();
					gridOptions1.api.setRowData([]); */
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

$(document).ready(function() {

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


	var dateFormat = localStorage.getItem("dateFormat");

	$("#reminderCalendar11").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#callStartDate11').val($(this).val());
	})

	$('#callStartDate11').blur(function() {
		$("#reminderCalendar11").val($(this).val());
	})



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


	$("#startDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#startDate').val($(this).val());
	})

	$('#startDate').blur(function() {
		$("#startDateCalendar").val($(this).val());
	})


	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})

	$('#endDate').blur(function() {
		$("#endDateCalendar").val($(this).val());
	})



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
	$("#meetingCalendarFromDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#meetingFromDate').val($(this).val());
	})

	$('#meetingFromDate').blur(function() {
		$("#meetingCalendarFromDate").val($(this).val());
	})

	$("#meetingCalendarRepeatFromDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#meetingRepeatFromDate').val($(this).val());
	})

	$('#meetingRepeatFromDate').blur(function() {
		$("#meetingCalendarRepeatFromDate").val($(this).val());
	})


	$("#meetingCalendarToDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#meetingToDate').val($(this).val());
	})

	$('#meetingToDate').blur(function() {
		$("#meetingCalendarToDate").val($(this).val());
	})

	$("#meetingCalendarRepeatToDate").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#meetingRepeatToDate').val($(this).val());
	})

	$('#meetingRepeatToDate').blur(function() {
		$("#meetingCalendarRepeatToDate").val($(this).val());
	})



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
});


$(document).ready(function() {


	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);

	var gridDiv = document.querySelector('#myGridStages');
	new agGrid.Grid(gridDiv, gridOptionsStages);

	var mailgridDiv = document.querySelector('#myGridMail');
	new agGrid.Grid(mailgridDiv, mailgridOptions);



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


})

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
	pinned: 'left',
	cellRenderer: function(params) {
		return '<a>' + params.data.stageName + '</a>';
	}
},
{
	headerName: "Amount",
	field: "dealAmount",
	width: 200,
	pinned: 'left',
	cellStyle: { textAlign: 'right' },
	cellRenderer: function(params) {
		const formattedAmount = parseFloat(params.data.dealAmount).toFixed(2);
		return '<a>' + formattedAmount + '</a>';

	}
},
{
	headerName: "Probability(%)",
	field: "probability",
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
}/*,
		{
			headerName: "Stage Duration(Calender Days)",
		    field: "stageDuration",
		    cellStyle: {textAlign: 'center'}
		}*/,
{

	headerName: "Modified Time",
	field: "updatedOn",
	cellStyle: { textAlign: 'left' }

},
{

	headerName: "Modified By",
	field: "updatedBy",
	cellStyle: { textAlign: 'left' }

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


/* AG-Grid for the Deals Stages End */

/* AG-Grid for the Deals Mails Start */
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
	pinned: 'left'

}, {
	headerName: "Sent To",
	field: "toMail",
	pinned: 'left'

}, {
	headerName: "Sent Date",
	field: "createdDate",
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Sent By",
	field: "createdBy",
	cellStyle: {
		textAlign: 'center'
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
/* AG-Grid for the Deals Mails End */
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



$(function() {
	var dealId = $("#dealId").text();

	$("body").addClass("overlay");
	$.ajax({
		type: "GET",
		url: "view-crm-deals-ViewDetailPage?id=" + dealId,
		success: function(response) {

			if (response.message == "Success") {
				//return false;
				//$('#leadId').val(response.body[0].leadId);
				$('#dealOwner1').text(response.body[0].dealOwner);
				$('#dealStage1').text(response.body[0].dealStage);
				$('#dealProbabilty1').text(response.body[0].probability);
				$('#dealExpectedRevenue1').text(response.body[0].expectedRevenue);
				$('#dealClosingDate1').text(response.body[0].dealClosingDate);
				$('#dealOwnerName2').text(response.body[0].dealOwner);
				$('#dealName2').text(response.body[0].dealName);
				$('#dealNameHead').text(response.body[0].dealName);
				$('#dealAmountHead').text("₹ " + response.body[0].dealAmount);
				$('#dealAccountName2').text(response.body[0].dealAccountName);
				$('#dealType2').text(response.body[0].dealType);
				$('#dealNextStep2').text(response.body[0].nextStep);
				$('#dealLeadSource2').text(response.body[0].dealLeadSource);
				$('#dealContactName2').text(response.body[0].contactName);
				$('#dealModifiedBy2').text(response.body[0].createdBy);
				$('#dealAmount2').text(response.body[0].dealAmount);
				$('#dealClosingDate2').text(response.body[0].dealClosingDate);
				$('#dealStage2').text(response.body[0].dealStage);
				$('#dealProbailities2').text(response.body[0].probability);
				$('#dealExpectedRevenue2').text(response.body[0].expectedRevenue);
				$('#dealCampaignSourec2').text(response.body[0].campaignSource);
				$('#dealCreatedBy2').text(response.body[0].createdBy);
				$('#dealDescription').text(response.body[0].description);

				$(".therapy-content").removeClass("active");
				$("#" + response.body[0].stageId).addClass("active");

				//var createdTime = response.body[0].createdTime;
				var dealStartDate = response.body[0].createdDate.split("T")[0];
				$("#startDate").append(dealStartDate);

				var dealClosingDate = response.body[0].dealClosingDate;
				$("#closingDate").append(dealClosingDate);
				//	$("#TSM0006").addClass("active");
				getNoteDeals(dealId, "1", "", "");
				getMail(dealId);
				getProduct(dealId);
				getTask(dealId);
				getMeeting(dealId);
				getCall(dealId);
				getDealStage(dealId);
				getActivity(dealId, "allTimeline");
				getCount(dealId)
				$('.loader').hide();
				$("body").removeClass("overlay");

				if (response.body[0].dealStage == "Closed") {
					$("#noteBtn").hide();
				}

			}
		}
	});

});

/*function filterNoteByDate(){
	var filterDate = $("#dateFilter").val();
	var filterTitle = $("#noteTitleSearch").val();
	var dealId = $("#dealId").text();
	
	statuss = true;
	getNote(dealId,"1",filterDate,filterTitle)
}

var statuss = true;
function getNote(dealId,cuPages,filterDate,filterTitle){
	getNoteAll(dealId,cuPages,filterDate,filterTitle)
}*/

function filterNoteByDate() {
	$("#noteListWithDoc").empty();
	var filterDate = $("#dateFilter").val();
	var filterTitle = $("#noteTitleSearch").val();
	var dealId = $("#dealId").text();

	statuss = true;
	getNoteDeals(dealId, "1", filterDate, filterTitle);
}

var statuss = true;
var dealId = $("#dealId").text();
function getNoteDeals(dealId, cuPages, filterDate, filterTitle) {
	getNoteAll(dealId, cuPages, filterDate, filterTitle);
}

/*function getNoteDeals(leadId){
	$("#noteListWithDoc").empty();
	$("#countNote").empty().append(0);
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-note?id=" + leadId,
		success : function(response) {

			if (response.message == "Success") {
				 var len = response.body.length;
				 $("#countNote").empty().append(len);
				div = '';
				for (var i = 0; i < response.body.length; i++) {
					var image=response.body[i].noteDocLink;
					var leadImage=response.body[i].leadImageLink;
					var ownerImage=response.body[i].dealOwnerImageLink;
					var fileExtension="";
					if(image !=null){
						fileExtension = image.substr( (image.lastIndexOf('.') +1) );
					}else{
						fileExtension = "noImage";
					}
					
					if(fileExtension =="noImage"){
						var xyz='<div class="col-md-10"><table class="table table-borderless"> <tbody>'
								+'<tr><td width="10%">'
								+'<div class="thumb">'
								+'<img src="'+ownerImage+'" class="rounded-circle" style="width:50px;"/></div>'
								+'</td><td width="90%">'
								
								+'<div class="d-flex align-items-center justify-content-between">'
								+'<div class="fL">'
								+'<p class="titletxt">'+response.body[i].titleName+'</p>'
								+'<p class="desc">'+response.body[i].notedesc+'</p>'
								+'</div>'
								+'<div class="fR">'
								+'<a href="#" class="actionicon"><i class="bx bxs-pencil"></i></a>'
								+'<a href="#" class="actionicon"><i class="bx bx-trash"></i></a>'
								+'</div></div>'
								
								+'<div class="d-flex align-items-center mr-topnew">'
								+'<span class="greytext">Deal</span>'
								+'<span class="greytext prd-5">-</span>'
								+'<span class="bluetext"><a href="#">'+response.body[i].dealName+'</a></span>'
								+'<span class="dot"></span>'
								+'<span class="greytext">Add Note</span>'
								+'<span class="dot"></span>'
								+'<span class="greytext">'+response.body[i].createdDate+' by '+response.body[i].ownerName+'</span>'
								+'</div>'
								+'</td></tr></tbody></table></div><div class="col-md-2"></div>';
					}else if(fileExtension =="pdf"){
						var xyz='<div class="col-md-10"><table class="table table-borderless"> <tbody><tr><td width="10%">'
							+'<div class="thumb"><img  src="'+ownerImage+'" class="rounded-circle" style="width:50px;"/></div>'
							+'</td><td width="90%">'
							
							+'<div class="d-flex align-items-center justify-content-between">'
							+'<div class="fL">'
							+'<p class="titletxt">'+response.body[i].titleName+'</p>'
							+'<p class="desc">'+response.body[i].notedesc+'</p>'
							+'</div>'
							+'<div class="fR">'
							+'<a href="#" class="actionicon"><i class="bx bxs-pencil"></i></a>'
							+'<a href="#" class="actionicon"><i class="bx bx-trash"></i></a>'
							+'</div></div>'
							
							
							+'<div class=""><table class="table table-borderless"><tbody><tr><td><div class="bdr">'
							+'<a href="'+image+'" target="_blank">'
							+response.body[i].noteDoc+'</a></div><div class="scrtxt">'
							+'<a href="'+image+'" target="_blank">'+response.body[i].noteDoc+'</a>'
							+'</div>'
							+'<div class="d-flex align-items-center mr-topnew">'
							+'<span class="greytext">Deal</span>'
							+'<span class="greytext prd-5">-</span>'
							+'<span class="bluetext"><a href="#">'+response.body[i].dealName+'</a></span>'   
							+'<span class="dot"></span>'
							+'<span class="greytext">Add Note</span>'
							+'<span class="dot"></span>'
							+'<span class="greytext">'+response.body[i].createdDate+' by '+response.body[i].ownerName+'</span>'
							+'</div>'
							+'</td></tr><tbody></table></div></td></tr></tbody>'
							+'</table></div><div class="col-md-2"></div>';	
					}else{
						var xyz='<div class="col-md-10"><table class="table table-borderless"> <tbody><tr><td width="10%">'
							+'<div class="thumb"><img  src="'+ownerImage+'" class="rounded-circle" style="width:50px;"/></div>'
							+'</td><td width="90%">'
							
							+'<div class="d-flex align-items-center justify-content-between">'
							+'<div class="fL">'
							+'<p class="titletxt">'+response.body[i].titleName+'</p>'
							+'<p class="desc">'+response.body[i].notedesc+'</p>'
							+'</div>'
							+'<div class="fR">'
							+'<a href="#" class="actionicon"><i class="bx bxs-pencil"></i></a>'
							+'<a href="#" class="actionicon"><i class="bx bx-trash"></i></a>'
							+'</div></div>'
							
							+'<div class=""><table class="table table-borderless"><tbody><tr><td><div class="bdr">'
							+'<a href="'+image+'" target="_blank">'
							+'<img title="'+response.body[i].noteDoc+'" src="'+image+'" class="img-fluid"/></a></div><div class="scrtxt">'
							+'<a href="'+image+'" target="_blank">'+response.body[i].noteDoc+'</a>'
							+'</div>'
							+'<div class="d-flex align-items-center mr-topnew">'
							+'<span class="greytext">Deal</span>'
							+'<span class="greytext prd-5">-</span>'
							+'<span class="bluetext"><a href="#">'+response.body[i].dealName+'</a></span>'
							+'<span class="dot"></span>'
							+'<span class="greytext">Add Note</span>'
							+'<span class="dot"></span>'
							+'<span class="greytext">'+response.body[i].createdDate+' by '+response.body[i].ownerName+'</span>'
							+'</div>'
							+'</td></tr><tbody></table></div></td></tr></tbody>'
							+'</table></div><div class="col-md-2"></div>';	
					}
						
						div = xyz;
						$("#noteListWithDoc").append(div);

					} 
								
			}
		}
	});
	
}
*/

function getMail(dealId) {

	$("#countEmail").empty().append(0);
	agGrid.simpleHttpRequest({
		url: "view-crm-leads-view-detail-mail?id=" + dealId,
	}).then(function(data) {
		var len = data.body.length;
		$("#countEmail").empty().append(len);
		mailgridOptions.api.setRowData(data.body);
	});



}


function getProduct(dealId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-product?id=" + dealId,
		success: function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm = response.body.length;
				//return false;
				for (var i = 0; i < response.body.length; i++) {
					var productActive = response.body[i].productActive;
					if (productActive == 'on') {
						productActive = 'Active';
					} else {
						productActive = 'Inactive';
					}
					var mailRow = '<tr>'
						+ '<td><i class="bi bi-x-lg"></i></td>'
						+ '<td>' + response.body[i].productName + '</td>'
						+ '<td>' + response.body[i].productCode + '</td>'
						+ '<td>' + productActive + '</i></td>'
						+ '<td>' + response.body[i].productManufacturer + '</td>'
						+ '<td>' + response.body[i].supportStartDate + '</td>'
						+ '<td>' + response.body[i].supportEndDate + '</td>'

						+ '</tr>';
					trCount = mailRow;
					$("#productTblBodyPage").append(trCount);

				}
				$("#countAssignProduct").val(countItm);
				if (countItm < 1) {
					var mailRow = '<tr>'
						+ '<td colspan="7" align="center">No Record Found.</td></tr>';
					trCount = mailRow;
					$("#productTblBodyPage").append(trCount);
				}

			}
		}
	});
}



function viewEmailDetails(id, tomail) {
	$('#myModalViewEmailContact').modal('show');

	var empId = $("#dealId").text();

	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-email-view?id1=" + empId + "&id2=" + id + "&id3=" + tomail,
		async: false,
		success: function(response) {

			if (response.message == "success") {
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


//getDealStage

function getDealStage(dealId) {
	$("#countStage").empty();
	$.ajax({
		type: "GET",
		url: "view-crm-deals-detail-stage?id=" + dealId,
		success: function(response) {
			if (response.message == "Success") {
				var len = response.body.length;
				$("#countStage").append(len);
				gridOptionsStages.api.setRowData(response.body);

			}
		}
	});
}


function getCampaign(leadId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-campaign?id=" + leadId,
		success: function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm = response.body.length;
				for (var i = 0; i < response.body.length; i++) {
					var mailRow = '<tr>'
						+ '<td><i class="bi bi-x-lg"></i></td>'
						+ '<td>' + response.body[i].campaignName + '</td>'               //campaignName
						+ '<td>' + response.body[i].campaignStatus + '</td>'			    // campaignStatus
						+ '<td>' + response.body[i].campaignType + '</td>'               // campaignType
						+ '<td>' + response.body[i].startDate + '</td>'		      		// startDate
						+ '<td>' + response.body[i].endDate + '</td>'		            // endDate
						+ '<td>$' + response.body[i].expectedRevenue + '</td>'			// expectedRevenue
						+ 'td>$</td>'			                                    // budgetedCost
						+ '<td></td>'				                            // memberStatus
						+ '<td></td>'					                            // serviceStatus
						+ '</tr>';
					trCount = mailRow;
					$("#campaignTblBody").append(trCount);

				}
				if (countItm < 1) {
					var mailRow = '<tr>'
						+ '<td colspan="10" align="center">No Record Found.</td></tr>';
					trCount = mailRow;
					$("#campaignTblBody").append(trCount);
				}

			}
		}
	});
}

function getTask(dealId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-task?id=" + dealId,
		success: function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm = response.body.length;
				//return false;
				var countOpen = 0, countClose = 0;
				for (var i = 0; i < response.body.length; i++) {

					var taskStatus = response.body[i].taskStatus;
					if (taskStatus != "Completed") {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].taskSubject + '</div>'
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

			}
		}
	});
}


//getMeeting


function getMeeting(dealId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-meeting?id=" + dealId,
		success: function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm = response.body.length;
				//return false;
				var countOpenM = 0, countCloseM = 0;
				for (var i = 0; i < response.body.length; i++) {
					var meetingStatus = response.body[i].meetingStatus;
					if (meetingStatus != "Completed") {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].meetingTitle + '</div>'
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
				getCount(contact);
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

//getCall


function getCall(dealId) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-call?id=" + dealId,
		success: function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm = response.body.length;
				//return false;
				var countOpenC = 0, countCloseC = 0;
				for (var i = 0; i < response.body.length; i++) {
					var callStatus = response.body[i].callStatus;
					if (callStatus != "Completed") {
						var mailRow = '<li>'
							+ '<div class="toptxt">' + response.body[i].callSubject + '</div>'
							+ '<p>Call At : ' + response.body[i].callStartDate + ' - <span> ' + response.body[i].callStartTime + '</span></p>'
							+ '<div class="smalltxt"><i class="bi bi-person-fill"></i> ' + response.body[i].callOwner + '</div>'
							+ '<div class="smalltxt">Call Related To : ' + response.body[i].callToWhom + '</div>'
							+ '<p>Call Status : ' + response.body[i].callStatus + '</p>'

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

			}
		}
	});
}



//getActivity(dealId);

function getActivity(dealId, type) {
	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-activity?id=" + dealId + "&type=" + type,
		success: function(response) {
			if (response.message == "Success") {
				$("#main-timeline-section").empty();
				var countItm = response.body.length;
				var createdTime = response.body[0].createdTime;
				
				$("#updatedTimes").append(createdTime);

				getActivityTimeline(response.body);


			}
		}
	});
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

$(document).ready(function() {
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
			$('#myModalAddTask').modal('show');
		} else if (value == "Meeting") {
			$('#myModalAddMeeting').modal('show');
		} else if (value == "Call") {
			$('#myModalAddCall').modal('show');
		}
	});

});

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


//Profile Image Upload & Delete Ends



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
	var obj = {};
	obj.taskId = $('#taskId').text();
	obj.taskOwner = $('#taskOwner').val();
	obj.leadId = $('#leadId').val();
	obj.contactId = $('#contactId').val();

	obj.accountId = $('#accountId').val();

	obj.taskSubject = $('#taskSubject').val();
	obj.dueDate = $('#dueDate').val();

	obj.ownerName = $('#dealOwnerName2').text();
	obj.pageType = "Deal";
	obj.taskDealId = $('#dealId').text();
	obj.taskStatus = $('#taskStatus').val();
	obj.taskPriority = $('#taskPriority').val();
	var taskContactName = $('#contactName').val();
	obj.taskContactName = taskContactName;



	var dealAccountName = $('#dealAccountName').val();
	obj.taskAccountName = dealAccountName;




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

	//return false;

	var validation = true;
	//return false;
	/* 
		if (obj.taskOwner == null || obj.taskOwner == "") {
			validation = validationUpdated("Task Executive Required",
					"taskOwner");
		}
		if (obj.taskLead == null || obj.taskLead == "") {
			validation = validationUpdated("Task Lead Required",
					"taskLead");
		}
		if (obj.taskSubject == null || obj.taskSubject == "") {
			validation = validationUpdated("Task Subject Required",
					"taskSubject");
		}
		if (obj.dueDate == null || obj.dueDate == "") {
			validation = validationUpdated("Due Date Required",
					"dueDate");
		}
		if (obj.taskContactName == null || obj.taskContactName == "") {
			validation = validationUpdated("Task Contact Required",
					"taskContactName");
		}
		if (obj.taskContactName == null || obj.taskContactName == "") {
			validation = validationUpdated("Task Contact Required",
					"taskContactName");
		}
		if (obj.taskAccountName == null || obj.taskAccountName == "") {
			validation = validationUpdated("Task Account Name Required",
					"taskAccountName");
		}
		if (obj.taskContactName == null || obj.taskContactName == "") {
			validation = validationUpdated("Task Contact Required",
					"taskContactName");
		}
		if (obj.taskStatus == null || obj.taskStatus == "") {
			validation = validationUpdated("Task Status Required",
					"taskStatus");
		}
		if (obj.taskPriority == null || obj.taskPriority == "") {
			validation = validationUpdated("Task Status Required",
					"taskPriority");
		}
		if (obj.description == null || obj.description == "") {
			validation = validationUpdated("Task Status Required",
					"description");
		}
		if(ReminderYesOrNo=='Yes'){
		if (obj.reminderDateid == null || obj.reminderDateid == "") {
			validation = validationUpdated("Date Required",
					"reminderDateid");
		}
		if (obj.reminderTime == null || obj.reminderTime == "") {
			validation = validationUpdated("Reminder Time Required",
					"reminderTime");
		}
		if (obj.taskAlertBy == null || obj.taskAlertBy == "") {
			validation = validationUpdated("AlertBy Required",
					"taskAlertBy");
		}
		} */

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-task-dtls",
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
//obj.taskDealId = $('#dealId').text();
function addMeetingInfo() {
	var type = $('#relatedMeetingTo').val();
	var obj = {};
	obj.meetingId = $('#meetingId').text();
	obj.meetingDealId = $('#dealId').text();
	obj.meetingTitle = $('#meetingTitle').val();
	obj.ownerName = $('#dealOwnerName2').text();
	obj.meetingType = "Deal";
	obj.meetingLocation = $('#meetingLocation').val();
	obj.isThisOnlineMeeting = $('#isThisOnlineMeeting').val();
	obj.isAllDay = $('#isAllDay').val();
	obj.meetingFromDate = $('#meetingFromDate').val();
	obj.meetingFromTime = $('#meetingFromTime').val();
	obj.meetingToDate = $('#meetingToDate').val();
	obj.meetingToTime = $('#meetingToTime').val();
	obj.meetingHost = $('#meetingHost').val();
	obj.leadName = $('#meetingleadName').val();
	obj.leadId = $('#leadId').val();
	obj.meetingleadId = $('#leadId').val();
	obj.contactName = $('#meetingcontactName').val();
	obj.contactId = $('#contactId').val();
	obj.meetingParticipants = $('#meetingParticipants').val();
	obj.participantId = $('#participantId').val();
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

	/* FORM VALIDATION STARTS*/

	var validation = true;
	//return false;

	/* 	if (obj.meetingLocation == null || obj.meetingLocation == "") {
			   validation = validationUpdated("Meeting Location Required",
					   "meetingLocation");
		   }  
		 if (obj.meetingFromDate == null || obj.meetingFromDate == "") {
		   validation = validationUpdated("Meeting FromDate Required",
				   "meetingFromDate");
	   }  
		 if (obj.meetingFromTime == null || obj.meetingFromTime == "") {
		   validation = validationUpdated("Meeting FromTime Required",
				   "meetingFromTime");
	   }  
		 if (obj.meetingToDate == null || obj.meetingToDate == "") {
		   validation = validationUpdated("Meeting ToDate Required",
				   "meetingToDate");
	   }  
		 if (obj.meetingToTime == null || obj.meetingToTime == "") {
		   validation = validationUpdated("Meeting ToTime Required",
				   "meetingToTime");
	   }
		 if (obj.meetingHost == null || obj.meetingHost == "") {
		   validation = validationUpdated("Meeting Host Required",
				   "meetingHost");
	   }
		 if (obj.meetingParticipants == null || obj.meetingParticipants == "") {
		   validation = validationUpdated("Meeting Participants Required",
				   "meetingParticipants");
	   }
		 if (obj.relatedMeetingTo == null || obj.relatedMeetingTo == "") {
		   validation = validationUpdated("RelatedMeeting Required",
		   "relatedMeetingTo");
	   }
		 if (type=='Lead'){
			   if (obj.leadName == null || obj.leadName == "") {
				   validation = validationUpdated("Name Required",
				   "leadName");
			 }
		 	
		 }
		 if (type=='Contact'){
			   if (obj.relatedMeetingTo == null || obj.contactName == "") {
				   validation = validationUpdated("Name Required",
				   "contactName");
			 }
		 	
		 }

		  if (obj.isAllDay == null || obj.isAllDay == "") {
		   validation = validationUpdated("AllDay Field Required",
		   "isAllDay");
	   } 
	   if (obj.meetingStatus == null || obj.meetingStatus == "") {
		   validation = validationUpdated("Meeting Status Required",
		   "meetingStatus");
	   } */
	/* FORM VALIDATION ENDS*/
	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-meeting-dtls",
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

function addCallInfo() {
	var id = $("#callToWhom").val();
	var obj = {};
	obj.callId = $('#callId').text();
	obj.ownerName = $('#dealOwnerName2').text();
	obj.pageType = "Deal";
	obj.callDealId = $('#dealId').text();
	obj.callToWhom = $('#callToWhom').val();
	obj.leadName = $('#lName').val();
	obj.leadId = $('#leadId').val();
	obj.callleadId = $('#leadId').val();
	obj.contactName = $('#cName').val();
	obj.contactId = $('#contactId').val();
	obj.relatedType = $('#relatedType').val();
	obj.relatedName = $('#relatedName').val();
	obj.relatedId = $('#relatedId').val();
	obj.callType = $('#callType').val();
	obj.callStatus = $('#callStatus').val();
	obj.callStartDate = $('#callStartDate11').val();
	obj.callStartTime = $('#callStartTime11').val();

	obj.callOwner = $('#callOwner').val();
	obj.callSubject = $('#callSubject').val();
	obj.callReminder = $('#callReminder').val();
	obj.callPurpose = $('#callPurpose').val();
	obj.callAgenda = $('#callAgenda').val();

	/* FORM VALIDATION STARTS*/

	var validation = true;
	//return false;

	/*  if (obj.callToWhom == null || obj.callToWhom == "") {
		validation = validationUpdated("Call To Whome Required",
				"callToWhom");
	 } 
	 if (id == "Contact") {
		if (obj.contactName == null || obj.contactName == "") {
			validation = validationUpdated("Name Required",
					"contactName");
	 }
			if (obj.relatedType == null || obj.relatedType == "") {
				validation = validationUpdated("Related Type Required",
						"relatedType");
			 } 
			if (obj.relatedName == null || obj.relatedName == "") {
				validation = validationUpdated("Related Name Required",
						"relatedName");
			 } 
		    
		}
	 if (id == "Lead") {
			if (obj.leadName == null || obj.leadName == "") {
				validation = validationUpdated("Name Required",
						"leadName");
		 }
	 }
	 
	 if (obj.callType == null || obj.callType == "") {
			validation = validationUpdated("Call Type Required",
					"callType");
	 } 
	 if (obj.callStatus == null || obj.callStatus == "") {
			validation = validationUpdated("Status Required",
					"callStatus");
	 } 
	 if (obj.callStartDate == null || obj.callStartDate == "") {
			validation = validationUpdated("Start Date Required",
					"callStartDate");
	 }  
	 if (obj.callStartTime == null || obj.callStartTime == "") {
			validation = validationUpdated("Start Time Required",
					"callStartTime");
	 } 
	 if (obj.callOwner == null || obj.callOwner == "") {
			validation = validationUpdated("Call Executive Required",
					"callOwner");
	 }
	 if (obj.callSubject == null || obj.callSubject == "") {
			validation = validationUpdated("Subject Required",
					"callSubject");
	 } 
	 if (obj.callReminder == null || obj.callReminder == "") {
			validation = validationUpdated("Reminder Required",
					"callReminder");
	 } 
	 if (obj.callPurpose == null || obj.callPurpose == "") {
			validation = validationUpdated("Call Purpose Required",
					"callPurpose");
	 } 
	 if (obj.callAgenda == null || obj.callAgenda == "") {
			validation = validationUpdated("Call Agenda Required",
					"callAgenda");
	 }   */

	/* FORM VALIDATION ENDS*/
	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-call-dtls",
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

function addCampaignInfo() {
	var obj = {};
	obj.campaignId = $('#campaignId').text();
	obj.leadId = $('#leadId').text();
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
	obj.description = $('#description').val();

	/* FORM VALIDATION STARTS*/

	var validation = true;
	//return false;

	/* if (obj.taskOwner == null || obj.taskOwner == "") {
		validation = validationUpdated("Task Executive Required",
				"taskOwner");
	} */

	/* FORM VALIDATION ENDS*/

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-leads-detail-add-campaign-dtls",
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


//=-------------------------------------------Add Note-----------------------------------------

function saveNoteWithDoc() {

	watchLocationPermission();

	if (locationPermission) {
		var item = {};
		var dealId = $("#dealId").text();
		var titleId = $("#titleId").val();
		var noteId = $("#noteId").val();
		if (dealId && titleId) {

			var imageValid = true;
			var uploadList = [];
			$("#doctbodyData > tr").each(function() {
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

				item.dealId = dealId;
				//item.employeeId ="70001";
				item.employeeId = $('#dealOwnerName2').text();
				item.titleId = titleId;
				item.noteId = noteId;
				item.documentList = uploadList;
				item.latitude = latitude;
				item.longitude = longitude;

				saveLeadNoteDoc(item);

			}, 100)

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

function saveLeadNoteDoc(item) {
	var dealId = $("#dealId").text();
	$.ajax({
		type: "POST",
		url: "view-crm-leads-add-notes-ajax",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(item),
		success: function(response) {
			//getNote(dealId,"1", "","");
			getNoteDeals(dealId, "1", "", "");
			$("#messageParagraph").text("Note Added Successfully!");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$('.loader').hide();
			$("#titleId").val("");
			$("#noteId").val("");
			$("#divFiles").html("");


		},
		error: function(data) {
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	}) //ajax ends 
}


////////////////////////////////////////////////////////////////////




function AddCampaignModal(index) {
	$('#myModalAddCampaign').modal('show');
}


function addEmailModal(index) {
	$('#myModalAddEmail').modal('show');
}

function closeModelEmail(index) {
	$('#myModalAddEmail').modal('hide');
}

function closeViewModelEmail(index) {
	$('#myModalViewEmailContact').modal('hide');
}

function closeModelCampaign(index) {
	$('#myModalAddCampaign').modal('hide');
}

function closeModelTask(index) {
	$('#myModalAddTask').modal('hide');
}



function closeModelMeeting(index) {
	$('#myModalAddMeeting').modal('hide');
}




function closeModelCall(index) {
	$('#myModalAddCall').modal('hide');
}




function getProductSearch() {

	var searchVal = $("#searchProduct").val();

	var rowCount = $('#countAssignProduct').val();
	var dealId = $("#dealId").text();
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
	var pageType = "Deal";

	if (searchVal) {

		$.ajax({
			type: "GET",
			url: "view-crm-leads-autosearchProduct?searchVal=" + searchVal + "&id=" + dealId + "&assigRow=" + assigRow + "&pageType=" + pageType,

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
	var dealId = $("#dealId").text();
	var leadName = $("#leadName").text();
	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}
	var pageType = "Deal";
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-product-view?id=" + dealId + "&id2=" + assigRow + "&pageType=" + pageType + "&productCode=" + productCode,
		success: function(response) {
			if (response.message == "Success") {
				//return false;

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


function addProduct() {
	$('#addProductModal').modal('show');
	var rowCount = $('#countAssignProduct').val();
	var dealId = $("#dealId").text();
	var leadName = $("#leadName").text();
	var assigRow = "";
	if (rowCount > 0) {
		var assigRow = "Yes";
	} else {
		var assigRow = "No";
	}
	var pageType = "Deal";
	var productCode = "";
	$.ajax({
		type: "GET",
		url: "view-crm-leads-details-product-view?id=" + dealId + "&id2=" + assigRow + "&pageType=" + pageType + "&productCode=" + productCode,
		success: function(response) {


			if (response.message == "Success") {
				//return false;

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


var checkedIDs = [];
var checkedVal = [];
function clickProductCheck(id) {
	checkedVal.push(id);
	checkedIDs = $("#productTblBody input:checkbox:checked").map(function() {
		return $(this).val();
	}).get();
	var checkedLength = checkedIDs.length;


}

function addProductToDeal() {
	var dataset = [];

	productCheckedId = "";
	for (var i = 0; i < checkedIDs.length; ++i) {
		productCheckedId = productCheckedId + '"' + checkedVal[i] + '",';
	}
	productCheckedId = productCheckedId.substring(0, productCheckedId.length - 1);

	for (let i = 0; i < checkedIDs.length; ++i) {
		item = {};
		item['productId'] = checkedVal[i];
		item['dealId'] = $('#dealId').text();
		item['pageType'] = "Deal";
		dataset.push(item);
	}

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
					"Data saved successfully");
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


function closeViewModelAddProduct(index) {
	$('#addProductModal').modal('hide');
}

//---------------------------------------------------Start code for stage chnage
function changeStageForDeal(stageId, stageName, probability) {
	var dealAmount = parseInt($("#dealAmount2").text());
	var dealId = $("#dealId").text();
	var dealClosingDate = $("#dealClosingDate2").text();
	var probabilityPercentage = parseInt(probability);
	var expectedRevenue = ((dealAmount * probabilityPercentage) / 100);
	var dealOwner = $("#dealOwnerName2").text();
	var preStageName = $("#dealStage2").text();
	var obj = {};
	obj.dealId = dealId;
	obj.stageId = stageId;
	obj.stageName = stageName;
	obj.preStageName = preStageName;
	obj.dealAmount = dealAmount;
	obj.dealClosingDate = dealClosingDate;
	obj.probability = probabilityPercentage;
	obj.expectedRevenue = expectedRevenue;
	obj.dealOwner = dealOwner;
	var validation = true;
	//return false;


	/* FORM VALIDATION ENDS*/
	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-deals-detail-update-stage",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					//return false;
					location.reload();

				}
			},
			error: function(data) {

			}
		})
	}
}

////-------------------------------------------------contact autosearch

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



$(document).ready(function() {
	$(".therapy-content").click(function() {
		$(".therapy-content").removeClass("active");
		// $(".tab").addClass("active"); // instead of this do the below 
		$(this).addClass("active");
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

///////////send email


function saveDealEmails() {
	var item = {};

	var dealId = $("#dealId").text();
	var fromEmail = $("#fromEmail").val();
	var toMail = $("#toMail").val();
	var mailSubject = $("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName = $("#docName").val();

	if (dealId) {

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

			item.dealId = dealId;
			item.employeeId = $('#dealOwnerName2').text();
			item.fromEmail = fromEmail;
			item.toMail = toMail;
			item.mailSubject = mailSubject;
			item.commentck = comment;
			item.docName = docName;
			item.documentList = uploadList;
			saveDealEmailDtls(item);

		}, 100)

	} else {
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}

function saveDealEmailDtls(item) {
	$.ajax({
		type: "POST",
		url: "view-crm-contacts-add-emails-ajax",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(item),
		success: function(response) {
			//$('.loader').show();
			//$("body").removeClass("overlay");
			if (response.message == "success") {
				closeModelEmail();
				location.reload();
			} else {

				//$('.loader').hide();
				//$("body").removeClass("overlay");
			}
		},
		error: function(data) {
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	}) //ajax ends 
}




function scrollProduct() {
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
		scrollTop: $("#sales").offset().top - 250
	}, 500);
}

function scrollCases() {
	$('html, body').animate({
		scrollTop: $("#cases").offset().top - 250
	}, 500);
}

function scrollContactRoles() {
	$('html, body').animate({
		scrollTop: $("#stage").offset().top - 250
	}, 500);
}

function scrollCompetitors() {
	$('html, body').animate({
		scrollTop: $("#competitors").offset().top - 250
	}, 500);
}

function scrollStageHistory() {
	$('html, body').animate({
		scrollTop: $("#stageHistory").offset().top - 250
	}, 500);
}

function backToPage(event) {
	event.preventDefault();
	$('.loader').show();
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-deals"

	$('.loader').hide();
}

function editDealsInfo() {
	var dealId = $("#dealId").text();

	localStorage.setItem('dealId', dealId);
}

/*function getUrl(module, fun, activity) {
	$.ajax({
		type : "GET",
		url : "/index-get-breadcrumb-data?moduleId=" + module + "&fun="
				+ fun + "&activity=" + activity,
		async : false,
		success : function(response) {
			if (response.message == "Unsuccess") {
				
				modOnclick(fun);
				callActivity(activity, response.body.actURL);

			}
		},
		error : function(data) {
		}
	});
}  */

function getCount(dealId) {



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

function checkMeetingDetails() {
	var id = $("#relatedMeetingTo").val();
	if (id == "Lead") {
		$(".leadNameCls").show();
		$(".contactNameCls").hide();
		$(".accountNameCls").hide();
		$("#contactId").val("");
		$("#contactName").val("");
		$("#relatedType").prop('disabled', true);
		$("#relatedName").prop('disabled', true);
	}
	else {
		$(".contactNameCls").show();
		$(".accountNameCls").show();
		$(".leadNameCls").hide();
		$("#leadId").val("");
		$("#leadName").val("");
		$("#relatedType").prop('disabled', false);
		$("#relatedName").prop('disabled', false);
	}
}

function getNameLeadContactList3() {

	var relatedMeetingTo = $("#relatedMeetingTo").val();
	var leadName = $("#leadName").val();
	var contactName = $("#contactName").val();
	if (leadName == "") {
		$("#suggesstion-boxLead_").hide();
	}
	if (contactName == "") {
		$("#suggesstion-boxContact_1").hide();
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
						content += '<div onClick="selectAutocompleteLeadValue()">'
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
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueContact(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\')">'
								+ response.body[i].name
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-boxContact_1").show();
						$("#suggesstion-boxContact_1").html(content);

					}
					else {
						$("#search").css("background", "#FFF");
						var content = '<div id="autocomplete-list1">';
						content += '<div onClick="selectAutocompleteContactValue()">'
							+ "No Data Found" + '</div>';
						content += '</div>';
						$("#suggesstion-boxContact_1").show();
						$("#suggesstion-boxContact_1").html(content);
					}
				}
			},
			error: function(data) {
			}
		})



	}


}

function selectAutocompleteValueLead(name, LeadId) {

	if (name) {

		//$("#personName").val("");
		$("#leadId").val(LeadId);

		$("#leadName").val(name);
		$("#search").val(LeadId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxLead_").hide();

	} else {
		$("#leadId").val("");

		$("#leadName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxLead_").hide();

	}
}
function selectAutocompleteLeadValue() {

	$("#leadId").val("");

	$("#leadName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxLead_").hide();

}


function selectAutocompleteValueContact(name, ContactId) {

	$.ajax({
		type: "GET",
		url: "view-crm-meetings-get-deal-account-data?id=" + ContactId,
		success: function(response) {
			if (response.code == "Success") {
				var accountId = response.body[0].key;
				$("#accountId").val(accountId);

				var account = response.body[0].name;
				$("#dealAccountName").val(account);


			}
		}
	});

	if (name) {

		//$("#personName").val("");
		$("#contactId").val(ContactId);

		$("#contactName").val(name);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxContact_1").hide();


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
