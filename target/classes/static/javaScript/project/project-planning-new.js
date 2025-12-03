document.addEventListener('DOMContentLoaded', function() {


	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1);

	$("#mySidenav").hide();
	$("#mySidenavAll").hide();
	$("#save1").hide();
	$("#saveExecutionPlanAll").hide();
	$("#cancel").hide();
	
	
});
var id = "";
$(document).ready(function() {

	gridOptions1.api.setRowData();

	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-view"
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewProjectData;
		var len = allData.length;
		$('#totalReqs').find('span').html(len);
		gridOptions1.api.setRowData(allData);
		var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}
		id = data[0].projectId
	});


	var dateFormat = localStorage.getItem("dateFormat");

	$("#DateCalendarC").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#startDateC').val($(this).val());
		validateDatesC();
		calculateDurationC();
		//validateDatesRange();
	});

	$('#startDateC').blur(function() {
		$("#DateCalendarC").val($(this).val());
		validateDatesC();
		calculateDurationC();
		// validateDatesRange();
	});

	$("#EndDateCalendarC").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#endDateC').val($(this).val());
		validateDatesC();
		calculateDurationC();
		// validateDatesRange();
	});

	$('#endDateC').blur(function() {
		$("#EndDateCalendarC").val($(this).val());
		validateDatesC();
		calculateDurationC();
		//  validateDatesRange();
	});


	var dateFormat = localStorage.getItem("dateFormat");

	$("#DateCalendarMain").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#startDateMain').val($(this).val());
		validateDates();
		calculateDuration();
	});

	$('#startDateMain').blur(function() {
		$("#DateCalendarMain").val($(this).val());
		validateDates();
		calculateDuration();
	});

	$("#EndDateCalendarMain").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#endDateMain').val($(this).val());
		validateDates();
		calculateDuration();
	});

	$('#endDateMain').blur(function() {
		$("#EndDateCalendarMain").val($(this).val());
		validateDates();
		calculateDuration();
	});



});

var columnDefs1 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,

	},
	{
		headerName: "Project ID",
		field: "projectId",
		type: 'leftAligned',


	}, {
		headerName: "Project Name",
		field: "projectName",
		type: 'leftAligned',
		width: 250,

	}, {
		headerName: "Creation Date",
		field: "creationDate",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Location',
		field: "location",
		type: 'leftAligned',
		width: 230,
	}, {
		headerName: 'Country',
		field: "country2",
		type: 'leftAligned',
		width: 130,
		hide: true
	}, {
		headerName: "State",
		field: "stateid2",
		type: 'leftAligned',
		width: 150,
		hide: true
	}, {
		headerName: 'Pin',
		field: "pPin",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Project Incharge',
		field: "pIncharge",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Billing Name',
		field: "cName",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Address',
		field: "cAddress",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Country',
		field: "country",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing State',
		field: "stateid",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Pin',
		field: "cPin",
		width: 150,
		hide: true,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Email',
		field: "email",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Mobile',
		field: "mobile",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Remarks',
		field: "remark",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'status',
		field: "status",
		width: 150,
		type: 'leftAligned',

	}];
var gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		height: 20,
		//flex: 1,
		//minWidth: 100
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChanged
};


var id = "";
function onSelectionChanged() {

	var selected = gridOptions1.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	for (var i = 0; i < selected.length; i++) {
		id = id + selected[i].projectId;
	}

	id2 = id;


	if (rowCount > 0) {
		getplnschdtls(id);
	} else {
		$("#tbodyData12").empty();
		$("#mySidenav").hide();
		$("#mySidenavAll").hide();
		$("#save1").hide();
		$("#saveExecutionPlanAll").hide();
	}
	id = "";
}
function removeLeadingZeros(version) {
	if (version)
		return version.replace(/\b0+(\d+)/g, '$1');
	else return "";
}

var jsonData = '';
function getplnschdtls(id) {
	//		alert(prjid)
	$("#tbodyData12").empty();
	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-parentDatadtls?id=" + id
	}).then(function(data) {
		//if (response.message == "Success") {

		jsonData = JSON.parse(data.body[0]);
		if (jsonData == null) {
			$('.loader').hide();
			$("#tbodyData12").empty();
		} else {
			//var len = jsonData.length;
			$('.loader').hide();
			console.log('jsonData', jsonData)
			pId23 = data.body[0].projectplanId;
			$("#tbodyData12").empty();
			for (i = 0; i < jsonData.length; i++) {
				let arr = [];
				for (var i = 0; i < jsonData.length; i++) {

					if (!arr.includes(jsonData[i].planningId)) {
						arr.push(jsonData[i].planningId);
						let div = '<tr data-node-id="' + jsonData[i].planningId + '" class="abc" id="' + jsonData[i].planningId + '">'
							+ '<td  style="text-align-last: left !important" colspan="8"><strong>' +/* jsonData[i].planningId+' - '+ */jsonData[i].planningName + '</strong></td></tr>';
						$("#tbodyData12").append(div);
					}

					if (jsonData[i].categoryid == jsonData[i].parentid) {
						content = '<tr data-node-id="' + jsonData[i].categoryid + '" class="abc" id="' + jsonData[i].categoryid + '">'
							+ '<td class="firstnode" id=lbl_' + jsonData[i].categoryid + '><'
							+ jsonData[i].categoryid
							+ '" value="'
							+ jsonData[i].catlevel
							+ '" name="'
							+ jsonData[i].category
							+ '")>'
							+ jsonData[i].slnoId
							+ ' - '
							+ jsonData[i].category
							+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","1");></i></span ><span class="mrg-lft"> </span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editCategory("' + jsonData[i].categoryid + '","' + jsonData[i].planningId + '","' + jsonData[i].projectId + '","' + jsonData[i].totalCrop + '")></i></span>'
							+ '</td>'
							+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
							+ '<td class="firstnode">' + jsonData[i].projectName + '</td>'
							+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
							+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
							+ '<td class="firstnode">' + jsonData[i].asgnto + '</td>'
							/* + '<td class="firstnode">' + jsonData[i].days + '</td>' */
							+ '<td class="firstnode">' + jsonData[i].duration + '</td>'
							+ '<td class="firstnodec">' + jsonData[i].estimatePrice + '</td>'
							+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].predecessors) + '</td>'
							/* + '<td class="firstnode">' + jsonData[i].areaAcer + '</td>' */
							+ '<td class="firstnode">' + jsonData[i].notes + '</td>'
							+ '<td class="firstnode">' + jsonData[i].cretedby + '</td>'
							+ '<td class="firstnode">' + jsonData[i].projectstatusname + '</td>'


							+ '</tr>';

					} else {

						content = '<tr data-node-id="' + jsonData[i].categoryid + '" data-node-pid="' + jsonData[i].parentid + '" class="abc" id="' + jsonData[i].categoryid + '">'
							+ '<td class="firstnode" id=lbl_' + jsonData[i].categoryid + '><'
							+ jsonData[i].categoryid
							+ '" value="'
							+ jsonData[i].catlevel
							+ '" name="'
							+ jsonData[i].category
							+ '")>' + removeLeadingZeros(jsonData[i].slnoId)
							+ ' - '
							+ jsonData[i].category
							+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","1");></i></span><span class="mrg-lft"> </span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editSubCategory("' + jsonData[i].categoryid + '","' + jsonData[i].parentid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '")></i></span>'
							+ '</td>'
							+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
							+ '<td class="firstnode">' + jsonData[i].projectName + '</td>'
							+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
							+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
							+ '<td class="firstnode">' + jsonData[i].asgnto + '</td>'
							/* + '<td class="firstnode">' + jsonData[i].days + '</td>' */
							+ '<td class="firstnode">' + jsonData[i].duration + '</td>'
							+ '<td class="firstnodec">' + jsonData[i].estimatePrice + '</td>'
							+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].predecessors) + '</td>'
							/*  + '<td class="firstnode">' + jsonData[i].areaAcer + '</td>' */
							+ '<td class="firstnode">' + jsonData[i].notes + '</td>'
							+ '<td class="firstnode">' + jsonData[i].cretedby + '</td>'
							+ '<td class="firstnode">' + jsonData[i].projectstatusname + '</td>'
							+ '</tr>';

					}
					$("#tbodyData12").append(content);

				}
				$('#basic').simpleTreeTable({
					edatapander: $('#edatapander'),
					collapser: $('#collapser'),
					store: 'session',
					storeKey: 'simple-tree-table-basic'

				});




			}


			/* } else {
				swal({
					title: response.code,
					text: response.message,
					type: "warning"
				})
			} */

		}

	});
}

function openNav(categoryId, pd, ed, sl) {
	$("#mySidenav").show();
	$("#mySidenavAll").hide();
	$("#save1").show();
	$("#cancel").show();
	$("#saveExecutionPlanAll").hide();
	var subname = jsonData.filter(f => f.categoryid === categoryId);

	var subCatName = subname[0].subcategoryName;
	var pAnme = subname[0].category;
	$("#subCatgoryNameSpan").text(" " + subCatName);
	$("#subCatParentSpan").text(" " + pAnme);
	$("#subCatgoryNameSpanId").val(subname[0].subcategoryId);
	$("#stDateMain").val(subname[0].startDate);
	$("#edDateMain").val(subname[0].endDate);
	$("#totalCropSpanChild").text(subname[0].totalCrop);

	var pID = categoryId;
	$("#subCatParentSpanId").val(pID);
	$("#projectIdnav").val(pd);
	$("#plnIds").val(ed);
	$("#slnoval").val(sl);
	//$("#priorityMain").val("");
	$("#priorityC").val("");
	$("#startDateC").val("");
	$("#endDateC").val("");
	$("#phaseC").val("");
	$("#plaaningMainId").val("");
	//    $("#priorityC").val("");
	$("#startDateC").val("");
	$("#endDateC").val("");
	$("#assignedToC").val("");
	$("#ATnameMain").val("");
	$("#durationC").val("");
	$("#predecessorsC").val("");
	$("#phaseMain").val("");
	// $("#priorityMain").val("");
	$("#ATnameC").val("");
	$("#durationMain").val("");
	$("#predecessorsMain").val("");
	$("#notesC").val("");
	$("#phaseMain").val("");
	$("#assignedToMain").val("");
	$("#durationMainShow").val("");
	$("#predecessorsMain").val("");
	$("#ATnameC").val("");
	$("#notesMain").val("");
	//   $("#projectIdMain").text(prjid);
	$("#phaseC").empty();
	//  $("#priorityC").empty();

	$("#assignedToC").val("");
	$("#ATnameMain").empty();
	$("#durationCShow").val("");
	$("#predecessorsC").val("");
	$("#phaseMain").empty();

	$("#ATnameC").empty();
	$("#durationMain").empty();
	$("#predecessorsMain").empty();
	$("#notesC").empty();
	$("#phaseMain").empty();
	$("#assignedToMain").empty();
	$("#durationMainShow").empty();
	$("#predecessorsMain").empty();
	$("#ATnameC").val("");
	$("#notesMain").val("");
	$("#statusC").val("");
	//  $("#acerC").val("");

	$("#rateC").val("");
	$("#quantityC").val("");
	$("#totalPriceC").val("");
	$("#hoursplanC").val("");



}



function editCategory(id, exeId, prj2, totalCrp) {
	$("#mySidenav").hide();
	$("#mySidenavAll").show();
    $("#cancel").show();
	$("#planningid").val(exeId);
	$("#catIdMain").val(id);
	$("#projectIdMain").text(prj2);
	$("#totalCropMain").text(totalCrp);
	$("#error9").hide();

	$("#save1").hide();
	$("#saveExecutionPlanAll").show();


	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-parent-edit?id=" + id,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewEditParentData;
		$("#priorityMain").val(allData[0].priority);
		$("#startDateMain").val(allData[0].startDate);
		$("#endDateMain").val(allData[0].endDate);
		$("#assignedToMain").val(allData[0].assignedTo);
		$("#ATnameC").val(allData[0].aTnameC);
		$("#durationMain").val(allData[0].duration);
		$("#hoursplan").val(allData[0].hoursplan);
		$("#durationMainShow").val(allData[0].duration + " " + allData[0].hoursplan);
		$("#predecessorsMain").val(allData[0].predecessors);
		$("#notesMain").val(allData[0].notes);
		$("#phaseMain").val(allData[0].category);
		$("#plaaningMainId").val(allData[0].projectplanId);
		$("#statusMain").val(allData[0].projectstatus);
		$("#hoursplan").val(allData[0].plannedHrs);
		$("#planningName").val(allData[0].planningName);
		$("#unitMain").val(allData[0].unit);
		$("#quantityMain").val(allData[0].quantity);
		$("#rateMain").val(allData[0].unitRate);
		$("#totalPriceMain").val(allData[0].estimatePrice);

	});

}

function saveNav() {
	var event = {};
	var datas = [];
	var validation = true;
	if (event.cName == null || event.cName == "") {
		validation = validationUpdated("Task Name Required", 'phaseC');
	}
	if (event.priority == null || event.priority == "") {
		validation = validationUpdated("Priority Required", 'priorityC');
	}
	if (event.startDate == null || event.startDate == "") {
		validation = validationUpdated("Start Date Required", 'startDateC');
	}
	if (event.endDate == null || event.endDate == "") {
		validation = validationUpdated("End Date Required", 'endDateC');
	}
	if (validation) {
		event.plaaningMainId = $("#plaaningMainId").val();
		event.proplanid = $("#plnIds").val();
		event.parentid = $("#subCatParentSpanId").val();
		event.categoryid = $("#subCatId").val();
		event.projectId = $("#projectIdnav").val();
		event.cName = $("#phaseC").val();
		event.priority = $("#priorityC").val();
		event.startDate = $("#startDateC").val();
		event.endDate = $("#endDateC").val();
		event.assignedTo = $("#assignedToC").val();
		event.planhours = $("#hoursplanC").val();
		event.duration = $("#durationC").val();
		event.predecessors = $("#predecessorsC").val();
		event.notes = $("#notesC").val();
		event.category = $("#budgetcategory").val();
		event.subCategory = $("#subCatgoryNameSpanId").val();
		event.projectName = $("#projectName").val();
		event.location = $("#projectLocation").val();
		event.pIncharge = $("#projectinCharge").val();
		event.projectStatus = $("#statusC").val();
		event.estimatedPrice = $("#totalPriceC").val();
		event.unitPlan = $("#rateC").val();
		event.quantity = $("#quantityC").val();
		event.unit = $("#unitC").val();

		datas.push(event);
		console.log(event);

		$.ajax({
			type: "POST",
			url: "planning-scheduling-new-childdata-add",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),

			success: function(response) {
				if (response.message == "Success") {

					var id56 = response.body[0].projectId
					getplnschdtls(id56);

					$("#phaseC").val('');
					//$("#priorityC").val('');
					$("#startDateC").val('');
					$("#endDateC").val('');
					$("#ATnameMain").val('');
					$("#durationC").val('');
					$("#durationCShow").val('');
					$("#predecessorsC").val('');
					$("#notesC").val('');
					$("#mySidenav").hide();
					$("#mySidenavAll").hide();
					$("#save1").hide();
					$("#saveExecutionPlanAll").hide();
					$("#cancel").hide();
				}

			},
			error: function(response) {

			}

		});
	}
}

function saveExecutionPlanAll() {
	var event = {};
	var datas = [];
	var dataplan = $("#planningid").val();
	var validation = true;

	if (event.cName == null || event.cName == "") {
		validation = validationUpdated("Task Name Required", 'phaseMain');
	}
	if (event.priority == null || event.priority == "") {
		validation = validationUpdated("Priority Required", 'priorityMain');
	}
	if (event.startDate == null || event.startDate == "") {
		validation = validationUpdated("Start Date Required", 'startDateMain');
	}
	if (event.endDate == null || event.endDate == "") {
		validation = validationUpdated("End Date Required", 'endDateMain');
	}

	if (validation) {
		if (dataplan) {
			event.plaaningMainId = $("#plaaningMainId").val();
			event.proplanid = $("#planningid").val();
			event.categoryid = $("#catIdMain").val();
			event.projectId = $("#projectIdMain").text();
			event.cName = $("#phaseMain").val();
			event.priority = $("#priorityMain").val();
			event.startDate = $("#startDateMain").val();
			event.endDate = $("#endDateMain").val();
			event.assignedTo = $("#assignedToMain").val();
			event.duration = $("#durationMain").val();
			event.planhours = $("#hoursplan").val();
			event.predecessors = $("#predecessorsMain").val();
			event.notes = $("#notesMain").val();
			event.category = $("#budgetcategory").val();
			event.planningName = $("#planningName").val();
			event.projectName = $("#projectName").val();
			event.location = $("#projectLocation").val();
			event.pIncharge = $("#projectinCharge").val();
			event.projectStatus = $("#statusMain").val();
			event.estimatedPrice = $("#totalPriceMain").val();
			event.unitPlan = $("#rateMain").val();
			event.quantity = $("#quantityMain").val();
			event.unit = $("#unitMain").val();
			datas.push(event);
			console.log('eventIf', event)
			$.ajax({
				type: "POST",
				url: "planning-scheduling-new-parent-add",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(datas),

				success: function(response) {
					if (response.message == "Success") {

						$("#plnIds").val(response.body[0].proplanid);
						var id34 = response.body[0].projectId;
						getplnschdtls(id34)
						$("#mySidenav").hide();
						$("#mySidenavAll").hide();
						$("#save1").hide();
						$("#saveExecutionPlanAll").hide();
						$("#cancel").hide();
					}

				},
				error: function(response) {

				}

			});
		} else {
			event.plaaningMainId = $("#plaaningMainId").val();
			event.proplanid = $("#planningid").val();
			event.categoryid = $("#catIdMain").val();
			event.projectId = $("#projectIdMain").text();
			event.cName = $("#phaseMain").val();
			event.priority = $("#priorityMain").val();
			event.startDate = $("#startDateMain").val();
			event.endDate = $("#endDateMain").val();
			event.assignedTo = $("#assignedToMain").val();
			event.planhours = $("#hoursplan").val();
			event.duration = $("#durationMain").val();
			event.predecessors = $("#predecessorsMain").val();
			event.notes = $("#notesMain").val();
			event.category = $("#budgetcategory").val();
			event.planningName = $("#planningName").val();
			event.projectName = $("#projectName").val();
			event.location = $("#projectLocation").val();
			event.pIncharge = $("#projectinCharge").val();
			event.projectStatus = $("#statusMain").val();
			event.estimatedPrice = $("#totalPriceMain").val();
			event.unitPlan = $("#rateMain").val();
			event.quantity = $("#quantityMain").val();
			event.unit = $("#unitMain").val();
			datas.push(event);
			$.ajax({
				type: "POST",
				url: "planning-scheduling-new-parent-add",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(datas),

				success: function(response) {
					if (response.message == "Success") {


						$("#plnIds").val(response.body[0].proplanid);
						var id84 = response.body[0].projectId;
						getplnschdtls(id84)
						$("#mySidenav").hide();
						$("#mySidenavAll").hide();
						$("#save1").hide();
						$("#saveExecutionPlanAll").hide();
						$("#cancel").hide();

					}

				},
				error: function(response) {

				}

			});
		}
	}

}


function validateDates() {
	var startDate = $("#startDateMain").val();
	var endDate = $("#endDateMain").val();

	if (startDate && endDate) {
		var start = parseDate(startDate);
		var end = parseDate(endDate);

		if (start && end && start > end) {
			//  alert("Start date cannot be greater than end date. Please select a valid date range.");
			$("#error9").html("Start date cannot be greater than end date. Please select a valid date");
			$("#error9").show();
			$('#endDateMain').val('');
			$("#EndDateCalendarMain").val('');
		}

		if (start && end && start < end) {
			$("#error9").hide();
		}
	}
}

function validateDatesC() {
	var startDate = $("#startDateC").val();
	var endDate = $("#endDateC").val();



	if (startDate && endDate) {
		var start = parseDate(startDate);
		var end = parseDate(endDate);

		if (start && end && start > end) {
			//  alert("Start date cannot be greater than end date. Please select a valid date range.");
			$("#error10").html("Start date cannot be greater than end date. Please select a valid date");
			$("#error10").show();
			$('#endDateC').val('');
			$("#EndDateCalendarC").val('');
		}

		if (start && end && start < end) {
			$("#error10").hide();
		}
	}
}


function validateDatesRange() {
	var startDateMain = $('#stDateMain').val();
	var endDateMain = $('#edDateMain').val();
	var startDateC = $('#startDateC').val();


	if (startDateMain && endDateMain && startDateC) {
		var startMain = parseDate(startDateMain);
		var endMain = parseDate(endDateMain);
		var startC = parseDate(startDateC);

		if (startC < startMain || startC > endMain) {
			$("#error11").html("The Date between the main Parent start and end dates.");
			$("#error11").show();
			$('#startDateC').val('');
			$("#DateCalendarC").val('');
		} else {
			$("#error11").hide();
		}
	}
}

function parseDate(dateString) {
	var parts = dateString.split(/[\s-:]+/);
	var day = parseInt(parts[0], 10);
	var month = parseInt(parts[1], 10) - 1; // Month is zero-based
	var year = parseInt(parts[2], 10);
	var hour = parseInt(parts[3], 10);
	var minute = parseInt(parts[4], 10);

	return new Date(year, month, day, hour, minute);
}


function getAssignedToCAutoSearch() {
	var search = $("#ATnameC").val();

	if (search == "") {
		$("#suggesstion-box5").hide();
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "planning-scheduling-new-autosearch-assignTo?searchValue="
					+ search,
				success: function(response) {
					if (response.message == "success") {
						if (response.body.length != 0) {
							$("#search").css("background", "#6A2BBF");
							var content = '<ul id="autocomplete-list" style="color:#6A2BBF;">';
							for (var i = 0; i < response.body.length; i++) {

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue5(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box5").show();
							$("#suggesstion-box5").html(content);

						} else {
							$("#search").css("background", "#6A2BBF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue5()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box5").show();
							$("#suggesstion-box5").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue5(key, name) {

	if (name) {
		$("#ATnameC").val(name);
		$("#assignedToMain").val(key);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box5").hide();
		//getDate1(key);

	} else {
		$("#assignedToMain").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3").hide();

	}
}

function convertDateFormat(inputDate) {
	var parts = inputDate.split('-');
	var day = parseInt(parts[0], 10);
	var month = parseInt(parts[1], 10);
	var year = parseInt(parts[2], 10);

	var dateObject = new Date(year, month - 1, day);

	var formattedDate = (dateObject.getMonth() + 1) + '-' + dateObject.getDate() + '-' + dateObject.getFullYear();

	return formattedDate;
}


function calculateDuration() {
	var startDt = $("#startDateMain").val();
	var endDt = $("#endDateMain").val();


	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-calculationdatetime?startDt=" + startDt + "&endDt=" + endDt
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		$("#hoursplan").val(jsonData[0].hours);
		$("#durationMain").val(jsonData[0].days);
		$("#durationMainShow").val(jsonData[0].days + " " + jsonData[0].hours);

		var stsId = 'TPM0001'
		$("#statusMain").val(stsId);


	});


}


function calculateDurationC() {
	var startDt = $("#startDateC").val();
	var endDt = $("#endDateC").val();


	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-calculationdatetime?startDt=" + startDt + "&endDt=" + endDt
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		$("#hoursplanC").val(jsonData[0].hours);
		$("#durationC").val(jsonData[0].days);
		$("#durationCShow").val(jsonData[0].days + " " + jsonData[0].hours);

		var stsId = 'TPM0001'
		$("#statusC").val(stsId);


	});
}


function getPrecedNameAutoSearch() {
	var search = $("#predecessorsC").val();

	if (search == "") {
		$("#suggesstion-box1_").hide();
	}
	var pId = $("#plnIds").val();
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "planning-scheduling-new-autosearch-preced?searchValue="
					+ search + "&id=" + pId,
				success: function(response) {
					if (response.message == "success") {
						if (response.body.length != 0) {

							var content = '<ul id="autocomplete-list1">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue1(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							/*  content += '<li style="margin-left:-30px;""><a class="addCustnew" href="#">Add Item</a>'
								+ '</li>';  */
							content += '</ul>';
							$("#suggesstion-box1_").show();
							$("#suggesstion-box1_").html(content);

						} else {
							$("#itemName").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:-30px;font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
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
	} else {

		$("#suggesstion-box1_").hide();
	}
}
function selectAutocompleteValue1(key, name) {
	if (name) {
		$("#predecessorsC").val(key);
		$("#predecessorsCId").val(name);
		$("#search").val(key);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box1_").hide();
		//			getDate1(key);

	} else {
		$("#predecessorsC").val("");
		$("#predecessorsCId").val(name);
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();

	}
}


function getPrecedNameAutoSearchMain() {
	var search = $("#predecessorsMain").val();

	if (search == "") {
		$("#suggesstion-box11_").hide();
	}
	var pId = $("#planningid").val();
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "planning-scheduling-new-autosearch-preced?searchValue="
					+ search + "&id=" + pId,
				success: function(response) {
					if (response.message == "success") {
						if (response.body.length != 0) {

							var content = '<ul id="autocomplete-list1">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue11(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							/*  content += '<li style="margin-left:-30px;""><a class="addCustnew" href="#">Add Item</a>'
								+ '</li>';  */
							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);

						} else {
							$("#itemName").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1">';
							content += '<li style="margin-left:-30px;font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
								+ "No Data Found" + '</li>';

							content += '</ul>';
							$("#suggesstion-box11_").show();
							$("#suggesstion-box11_").html(content);
						}
					}
				},
				error: function(data) {
				}
			})
	} else {

		$("#suggesstion-box1_").hide();
	}
}


function selectAutocompleteValue11(key, name) {
	if (name) {
		$("#predecessorsMain").val(key);
		$("#predecessorsMainId").val(name + "" + key);
		$("#search").val(key);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box11_").hide();
		//			getDate1(key);

	} else {
		$("#predecessorsMain").val("");
		$("#predecessorsMainId").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box11_").hide();

	}
}


function editSubCategory(id, pId, proId, plId) {

	openNav(pId, "2");
	$("#subCatId").val(id);
	$("#projectIdnav").val(proId);
	$("#plnIds").val(plId);
	$("#error10").hide();
	$("#error11").hide();
	$("#cancel").show();

	//closeNavAll();
	agGrid.simpleHttpRequest({
		url: "planning-scheduling-new-child-edit-planning?id=" + id,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewEditChildData;
		$("#priorityC").val(allData[0].priority);
		$("#startDateC").val(allData[0].startDate);
		$("#endDateC").val(allData[0].endDate);
		$("#assignedToC").val(allData[0].assignedTo);
		$("#ATnameMain").val(allData[0].aTnameC);
		$("#durationC").val(allData[0].duration);
		$("#hoursplanC").val(allData[0].hoursplanC);
		$("#durationCShow").val(allData[0].duration + " " + allData[0].hoursplanC);
		$("#predecessorsC").val(allData[0].predecessors);
		$("#notesC").val(allData[0].notes);
		$("#phaseC").val(allData[0].category);
		$("#plaaningMainId").val(allData[0].projectplanId);
		$("#statusC").val(allData[0].projectstatus);
		$("#hoursplanC").val(allData[0].plannedHrs);
		$("#unitC").val(allData[0].unit);
		$("#quantityC").val(allData[0].quantity);
		$("#rateC").val(allData[0].unitRate);
		$("#totalPriceC").val(allData[0].estimatePrice);

	});
}


function getAssignedToMainAutoSearch() {
	var search = $("#ATnameMain").val();

	if (search == "") {
		$("#suggesstion-box3").hide();
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "planning-scheduling-new-autosearch-assignTo?searchValue="
					+ search,
				success: function(response) {
					if (response.message == "success") {
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list" style="color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {
								console
									.log(response.body[i].key
										+ " - "
										+ response.body[i].name)

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue3(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box3").show();
							$("#suggesstion-box3").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue3()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box3").show();
							$("#suggesstion-box3").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue3(key, name) {

	if (name) {
		$("#ATnameMain").val(name);
		$("#assignedToC").val(key);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box3").hide();
		//getDate1(key);

	} else {
		$("#assignedC").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3").hide();

	}
}


function calculationAmnt() {

	var qty = parseFloat($("#quantityMain").val()) || 0;
	var rate = parseFloat($("#rateMain").val()) || 0;

	var totalAmt = qty * rate;

	$("#totalPriceMain").val(totalAmt.toFixed(2));

}

function calculationAmntC() {
	var qty = parseFloat($("#quantityC").val()) || 0;
	var rate = parseFloat($("#rateC").val()) || 0;

	var totalAmt = qty * rate;

	$("#totalPriceC").val(totalAmt.toFixed(2));
}
function cancel() {
	$("#mySidenav").hide();
	$("#mySidenavAll").hide();
	$("#save1").hide();
	$("#saveExecutionPlanAll").hide();
	$("#cancel").hide();
    $(".formValidation").remove();

}

function onQuickFilterChanged1() {
	gridOptions1.api.setQuickFilter(document.getElementById('quickFilter1').value);
	let len = gridOptions.api.getDisplayedRowCount();
	let firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	

}


function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter1').val() == null || $('#quickFilter1').val() == "") {
		id.style.display = "none";
	}
}
