$(document).ready(function() {


	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();

	$("#save1").hide();
	$("#saveExecutionPlanParent").hide();
	$("#saveExecutionPlanChild").hide();
	$("#cancel").hide();

	$("#mySidenavChild").hide();
	$("#mySidenavParent").hide();


	agGrid.simpleHttpRequest({
		url: "project-execution-view-through-ajax"
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewProjectData;
		allData = allData?.filter(data => data.status === "ACTIVE");
		var len = allData.length;
		$('#totalReqs').find('span').html(len);
		gridOptions.api.setRowData(allData);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}


	});


	var dateFormat = localStorage.getItem("dateFormat");



	$("#startDateCalendarChild").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#startDateChild').val($(this).val());
	})

	$('#startDateChild').blur(function() {
		$("#startDateCalendarChild").val($(this).val());
	})

	$("#endDateCalendarChild").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
	}).on("change", function() {
		$('#endDateChild').val($(this).val());
	})

	$('#endDateChild').blur(function() {
		$("#endDateCalendarChild").val($(this).val());
	})


	$("#receiveDateCalendar1").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0,
	}).on("change", function() {
		$('#receiveDate').val($(this).val());
	})

	$('#receiveDate').blur(function() {
		$("#receiveDateCalendar1").val($(this).val());
	})

	$("#toDateCalendarPo").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#date').val($(this).val());
	})

	$('#date').blur(function() {
		$("#toDateCalendarPo").val($(this).val());
	})


});



var columnDefs = [
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


var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',

	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.projectId;
	}

};









var id = "";
var id12 = "";
function rowSelect() {

	var selected = gridOptions.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	for (var i = 0; i < selected.length; i++) {
		id = id + selected[i].projectId;
	}


	id12 = id;

	if (rowCount > 0) {
		getProjectExecutionDetails(id);
	} else {
		$("#tbodyData12").empty();
		$("#mySidenavChild").hide();
		$("#mySidenavParent").hide();
		$("#saveExecutionPlanParent").hide();
		$("#saveExecutionPlanChild").hide();
		$("#cancel").hide();
		
		
		$("#projectPlannedCost").val("");
		$("#projectActualCost").val("");
		


	}
	$("#projectIdParent").text(id12);
	id = "";
	id12 = "";
}
function removeLeadingZeros(version) {
	if (version)
		return version.replace(/\b0+(\d+)/g, '$1');
	else return "";
}
var jsonData = '';
function getProjectExecutionDetails(exeId) {
	$("#exeId").empty();

	$("#tbodyData12").empty();
	agGrid.simpleHttpRequest(
		{
			url: 'project-execution-details-list?id=' + exeId
		}).then(function(data) {

			jsonData = JSON.parse(data.body[0]);
			if (jsonData == null) {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#tbodyData12").empty();
				$("#projectPlannedCost").val("");
				$("#projectActualCost").val("");
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#tbodyData12").empty();

				let arr = [];
				let sumPlanned = 0;
				let sumActual = 0;
				let sumQuantity = 0;
				for (var i = 0; i < jsonData.length; i++) {

					if (!arr.includes(jsonData[i].planningId)) {
						arr.push(jsonData[i].planningId);

						let sumPlanned = jsonData
							.filter(f => f.planningId === jsonData[i].planningId)
							.map(m => parseFloat(m.plannedcost) || 0)
							.reduce((s, a) => s + a, 0);

						let sumActual = jsonData
							.filter(f => f.planningId === jsonData[i].planningId)
							.map(m => parseFloat(m.actualcost) || 0)
							.reduce((s, a) => s + a, 0);

						let sumQuantity = jsonData
							.filter(f => f.planningId === jsonData[i].planningId)
							.map(m => parseFloat(m.totalQuantity) || 0)
							.reduce((s, a) => s + a, 0);

						$("#projectPlannedCost").val(sumPlanned);
						$("#projectActualCost").val(sumActual);

						let div = '<tr data-node-id="' + jsonData[i].planningId + '" class="abc" id="' + jsonData[i].planningId + '">'
							+ '<td  style="text-align-last: left !important" colspan="9"><strong>' +/* jsonData[i].planningId+' - '+ */jsonData[i].planningName + '</strong></td>'
							+ '<td class="firstnodec" colspan="1">' + parseFloat(sumPlanned).toFixed(2) + '</td>'
							/* +'<td class="firstnodec" colspan="1">'+parseFloat(sumQuantity).toFixed(2)+'</td>' */
							+ '<td class="firstnodec" colspan="1">' + parseFloat(sumActual).toFixed(2) + '</td></tr>';
						$("#tbodyData12").append(div);
					}



					if (jsonData[i].categoryid == jsonData[i].parentId) {

						var ext = jsonData[i].fileAttach.split(".");
						if (ext[1] == "pdf") {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ jsonData[i].nodeSlNo
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","1");></i></span><span class="mrg-lft"> </span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editCategory("' + jsonData[i].categoryid + '","' + jsonData[i].executionId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"><div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("' + jsonData[i].fileAttach + '")> </div></td>'
								/* +'<td class="firstnodec">'+jsonData[i].areaAcer+'</td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em;" onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						} else if (ext[1] == "jpg" || ext[1] == "png") {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ jsonData[i].nodeSlNo
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","1");></i></span><span class="mrg-lft"> </span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editCategory("' + jsonData[i].categoryid + '","' + jsonData[i].executionId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"><div class ="fa fa-picture-o" style="cursor: pointer; color: red;" onclick=viewImage("' + jsonData[i].fileAttach + '")> </div></td>'
								/* +'<td class="firstnodec"'+jsonData[i].areaAcer+'></td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em;" onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						} else {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ jsonData[i].nodeSlNo
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","1");></i></span><span class="mrg-lft"> </span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editCategory("' + jsonData[i].categoryid + '","' + jsonData[i].executionId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"></td>'
								/* +'<td class="firstnodec">'+jsonData[i].areaAcer+'</td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em;" onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						}

					} else {
						var ext = jsonData[i].fileAttach.split(".");
						if (ext[1] == "pdf") {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" data-node-pid="' + jsonData[i].parentId + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ removeLeadingZeros(jsonData[i].nodeSlNo)
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","2");></i></span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editSubCategory("' + jsonData[i].categoryid + '","' + jsonData[i].parentId + '","' + jsonData[i].projectId + '","' + jsonData[i].executionId + '","' + jsonData[i].planningId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"><div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("' + jsonData[i].fileAttach + '")></div></td>'
								/* +'<td class="firstnodec">'+jsonData[i].areaAcer+'</td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em; " onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						} else if (ext[1] == "jpg" || ext[1] == "png") {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" data-node-pid="' + jsonData[i].parentId + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ removeLeadingZeros(jsonData[i].nodeSlNo)
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","2");></i></span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editSubCategory("' + jsonData[i].categoryid + '","' + jsonData[i].parentId + '","' + jsonData[i].projectId + '","' + jsonData[i].executionId + '","' + jsonData[i].planningId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"><div class ="fa fa-picture-o" style="cursor: pointer; color: red;" onclick=viewImage("' + jsonData[i].fileAttach + '")></div></td>'
								/* +'<td class="firstnodec">'+jsonData[i].areaAcer+'</td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em; " onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						} else {
							content = '<tr data-node-id="' + jsonData[i].categoryid + '" data-node-pid="' + jsonData[i].parentId + '" class="abc" id="' + jsonData[i].categoryid + '">'
								+ '<td class="firstnode" width="300" id=lbl_' + jsonData[i].categoryid + '><'
								+ jsonData[i].categoryid
								+ '" value="'
								+ jsonData[i].catLevel
								+ '" name="'
								+ jsonData[i].categoryName
								+ '")>'
								+ removeLeadingZeros(jsonData[i].nodeSlNo)
								+ ' - '
								+ jsonData[i].categoryName
								+ '<span class="mrg-lft"><i class="fa fa-plus" style="height: 25px; width:25px; font-size: 1.30em; color:#6A2BBF;" onclick=openNav("' + jsonData[i].categoryid + '","' + jsonData[i].projectId + '","' + jsonData[i].planningId + '","2");></i></span><span><i class="fa fa-edit" style="height: 25px; width:25px; font-size: 1.40em; color:#6A2BBF;" onclick=editSubCategory("' + jsonData[i].categoryid + '","' + jsonData[i].parentId + '","' + jsonData[i].projectId + '","' + jsonData[i].executionId + '","' + jsonData[i].planningId + '")></i></span>'
								+ '</td>'
								/* +'<td class="firstnode">'+jsonData[i].days+'</td>' */
								+ '<td class="firstnode">' + jsonData[i].startDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].endDate + '</td>'
								+ '<td class="firstnode">' + jsonData[i].assignedTo + '</td>'
								+ '<td class="firstnode">' + jsonData[i].priorityName + '</td>'
								+ '<td class="firstnode">' + removeLeadingZeros(jsonData[i].preced) + '</td>'
								+ '<td class="firstnode">' + '<button class="badge badge-primary" style="border:none; outline:none; color:#6A2BBF;"  onclick=showRequsition("' + jsonData[i].reqid + '","' + jsonData[i].categoryid + '");>' + jsonData[i].reqid + '<i class="fas fa-plus fa-lg"></i> ' + '</button>' + '</td>'
								+ '<td class="firstnode">'/* +jsonData[i].days+" " */ + jsonData[i].plannedHrs + '</td>'
								+ '<td class="firstnode">' + jsonData[i].actualHrs + '</td>'
								+ '<td class="firstnodec">' + jsonData[i].plannedcost + '</td>'
								/* +'<td class="firstnodec">'+jsonData[i].totalQuantity+'</td>' */
								+ '<td class="firstnodec">' + jsonData[i].actualcost + '</td>'
								+ '<td class="firstnode"></td>'
								/* +'<td class="firstnodec">'+jsonData[i].areaAcer+'</td>' */
								+ '<td class="firstnode" align="center"><span class="mrg-lft"><i class="bi-arrow-right-square-fill" style="height: 25px; width:25px; font-size: 1.30em; " onclick=categoryDetails("' + jsonData[i].executionId + '","' + jsonData[i].projectId + '","' + jsonData[i].categoryid + '","' + jsonData[i].pstsname + '");></i></span></td></tr>';

						}

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
		});

}

function viewImage(id) {
	window.open("/document/document/" + id, '_blank');
}


/*Child */
function openNav(categoryId, pd, pl, sl) {
	$("#saveExecutionPlanParent").hide();
	$("#saveExecutionPlanChild").show();

	$("#mySidenavChild").show();
	$("#mySidenavParent").hide();
	$("#cancel").show();

	var subname = jsonData?.filter(f => f.categoryid === categoryId);

	var totalCrop = subname ? subname[0]?.totalCrop : "";
	var pAnme = subname[0].categoryName;
	var budgetCid = subname[0].categoryId;
	var budgetSubCid = subname[0].subcategoryId;
	var projectName = subname[0].projectName;
	//$("#areaAcerChildAdd").text(" "+totalCrop);
	$("#subCatParentSpanAdd").text(" " + pAnme);
	$("#budgetcategoryId").val(budgetCid);
	$("#budgetSubcategoryId").val(budgetSubCid);
	$("#projectNameAdd").val(projectName);

	var pID = categoryId;
	$("#planningIdChild").val(pl);
	$("#subCatParentSpanId").val(pID);
	$("#projectIdChild").val(pd)
	$("#slnovalChild").val(sl);
	$("#phaseChild").val("");
	$("#startDateChild").val("");
	$("#endDateChild").val("");
	$("#assignedToChild").val("");
	$("#precedChild").val("");
	$("#typeChild").val("");
	$("#qtyNeededChild").val("");
	$("#plannedHrsChild").val("");
	$("#actualHrsChild").val("");
	$("#requiDateChild").val("");
	$("#needDateChild").val("");
	$("#fileAttachChild").empty("");
	$("#assignChild").val("");
	$("#notesChild").val("");
	$("#estimatedCostChild").val("");
	$("#baselineChild").val("");
	$("#actualCostChild").val("");
	$("#prjstsChild").val("");
	//$("#acerChild").val(""); 

	$("#unitChild").val("");
	$("#quantityChild").val("");
	$("#rateChild").val("");
	$("#totalPriceChild").val("");

}

function editCategory(id, exeId) {
	$("#mySidenavChild").hide();
	$("#mySidenavParent").show();
	$("#cancel").show();
	$("#saveExecutionPlanParent").show();
	$("#saveExecutionPlanChild").hide();
	$("#catIdParent").val(id);
	$("#executionIdParent").val(exeId);

	agGrid.simpleHttpRequest({
		url: "project-execution-parent-edit?id=" + id + "&exeId=" + exeId,
	}).then(function(data) {


		var jsonData = JSON.parse(data.body);

		console.log('Parent Data', jsonData)

		if (jsonData[0].reqid == "AddRequisition") {
			$("#afterCreateReq").hide();
			$("#addReq").show();

		} else {
			$("#afterCreateReq").show();
			$("#addReq").hide();
		}



		$("#prjstsParent").val(jsonData[0].projectstatus);
		$("#phaseParent").val(jsonData[0].categoryName);
		$("#startDateParent").val(jsonData[0].startDate);
		$("#endDateParent").val(jsonData[0].endDate);
		$("#assignedToParent").val(jsonData[0].assignToId);
		$("#assignedToParent").val(jsonData[0].assignedToId);
		$("#ATnameParent").val(jsonData[0].assignedTo);
		$("#typeParent").val(jsonData[0].maintype);
		$("#qtyNeededParent").val(jsonData[0].qtyNeeded);
		$("#plannedHrsParent").val(jsonData[0].plannedHrs);
		$("#plannedHrsParentDate").val(jsonData[0].days + " " + jsonData[0].plannedHrs);
		$("#actualHrsParent").val(jsonData[0].actualHrs);
		$("#requiDateParent").val(jsonData[0].requiDate);
		$("#needDateParent").val(jsonData[0].needDate);
		$("#reqidParent").val(jsonData[0].reqid);
		$("#notesParent").val(jsonData[0].notes);
		$("#baselineParent").val(jsonData[0].basline);
		$("#actualCostParent").val(jsonData[0].actuacst);
		$("#editFileAttachParent").val(jsonData[0].fileAttach);
		$("#afterCreateReq").val(jsonData[0].reqid);
		$("#precedParent").val(jsonData[0].preced);

		$("#unitParent").val(jsonData[0].uom);
		$("#rateParent").val(jsonData[0].unitRate);
		$("#quantityParent").val(jsonData[0].quantity);
		$("#totalPriceParent").val(jsonData[0].estimate);




		editFileAttachParent = jsonData[0].fileAttach;
		var fileName = jsonData[0].fileAttach;

		if (fileName != null) {
			var ext = fileName.split(".");

			if (ext[1] == "jpg" || ext[1] == "png") {
				var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
					+ fileName + '")></i></a> </div>';
			} else if (ext[1] == "pdf") {
				var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
					+ fileName + '")></i></a> </div>';
			} else {
				var LightImg = "<div class='uploadicon position-l'> </div>";
			}
			$("#uploadedBillDiv_0Parent").html(LightImg);
		}
		fileName = "";
		/*if ((jsonData[0].createdby != $("#loginId").val())) {
			//	$("#acerParent").attr('disabled', true);
			$("#ATnameParent").attr('disabled', true);
			$("#typeParent").attr('disabled', true);
			$("#notesParent").attr('disabled', true);
			$("#baselineParent").attr('disabled', true);
			$("#uploadDoc_0Parent").attr('disabled', true);
		} else if ((jsonData[0].createdby == 'NL91210010')) {
			// $("#acerParent").attr('disabled', false);
			$("#ATnameParent").attr('disabled', false);
			$("#typeParent").attr('disabled', false);
			$("#notesParent").attr('disabled', false);

		} else {
			//  $("#acerParent").attr('disabled', false);
			$("#ATnameParent").attr('disabled', false);
			$("#typeParent").attr('disabled', false);
			$("#notesParent").attr('disabled', false);
		}
		if (jsonData[0].projectstatus == "TPM0003") {
			$("#prjstsParent").attr('disabled', true);
			$("#ATnameParent").attr('disabled', true);
		} else {
			$("#prjstsParent").attr('disabled', false);
			$("#ATnameParent").attr('disabled', false);
		}*/



	});

}

function saveExecutionPlanParent() {
	var datas = [];
	var event = {};
	event.executionId = $("#executionIdParent").val();
	event.categoryId = $("#catIdParent").val();
	event.projectId = $("#projectIdParent").text();
	event.phase = $("#phaseParent").val();
	event.startDate = $("#startDateParent").val();
	event.endDate = $("#endDateParent").val();
	event.assignedTo = $("#assignedToParent").val();
	event.maintype = $("#typeParent").val();
	event.qtyNeeded = $("#qtyNeededParent").val();
	event.plannedHrs = $("#plannedHrsParent").val();
	event.actualHrs = $("#actualHrsParent").val();
	event.requiDate = $("#requiDateParent").val();
	event.needDate = $("#needDateParent").val();
	event.reqid = $("#reqidParent").val();
	event.notes = $("#notesParent").val();
	event.actualCost = $("#actualCostParent").val();
	event.fileAttach = $("#editFileAttachParent").val();
	event.projectStatus = $("#prjstsParent").val();
	event.preced = $("#precedParent").val();
	event.estimatedCost = $("#totalPriceParent").val();
	event.unitPrice = $("#rateParent").val();
	event.quantity = $("#quantityParent").val();
	event.uom = $("#unitParent").val();

	datas.push(event);
	$.ajax({
		type: "POST",
		url: "project-execution-parent-add",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(datas),

		success: function(response) {
			if (response.message == "Success") {

				var iddsmain = response.body[0].projectId
				getProjectExecutionDetails(iddsmain);
				$("#cancel").hide();
				$("#mySidenavParent").hide();
				$("#mySidenavChild").hide();
				$("#saveExecutionPlanParent").hide();
				$("#saveExecutionPlanChild").hide();
			}

		},
		error: function(response) {

		}

	});
}



function editSubCategory(id,pId,proId,exeId,pl){
	openNav(id, "2");

	$("#subCatIdChild").val(id);
	$("#projectIdChild").val(proId);
	$("#executionIdChild").val(exeId);
	$("#planningIdChild").val(pl);




	agGrid.simpleHttpRequest({
		url: "project-execution-child-edit?id=" + id + "&exeId=" + exeId,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);

		if (jsonData[0].reqid == "AddRequisition") {
			$("#afterCreateReqChild").hide();
			$("#addReqChild").show();

		} else {
			$("#afterCreateReqChild").show();
			$("#addReqChild").hide();
		}
		$("#prjstsChild").val(jsonData[0].projectstatus);
		$("#phaseChild").val(jsonData[0].categoryName);
		$("#startDateChild").val(jsonData[0].startDate);
		$("#endDateChild").val(jsonData[0].endDate);
		$("#assignedToChild").val(jsonData[0].aTnameC);
		$("#assignChild").val(jsonData[0].assignedTo);
		$("#typeChild").val(jsonData[0].maintype);
		$("#qtyNeededChild").val(jsonData[0].qtyNeeded);
		$("#plannedHrsChild").val(jsonData[0].plannedHrs);
		$("#plannedHrsChildDate").val(jsonData[0].days + " " + jsonData[0].plannedHrs);
		$("#actualHrsChild").val(jsonData[0].actualHrs);
		$("#requiDateChild").val(jsonData[0].requiDate);
		$("#needDateChild").val(jsonData[0].needDate);
		$("#precedChild").val(jsonData[0].preced);
		$("#notesChild").val(jsonData[0].notes);
		$("#baselineChild").val(jsonData[0].basline);
		$("#actualCostChild").val(jsonData[0].actuacst);
		$("#editFileAttachChild").val(jsonData[0].fileAttach);
		$("#unitChild").val(jsonData[0].uom);
		$("#rateChild").val(jsonData[0].unitRate);
		$("#quantityChild").val(jsonData[0].quantity);
		$("#totalPriceChild").val(jsonData[0].estimate)

		editFileAttachChild = jsonData[0].fileAttach;
		$("#afterCreateReqChild").val(jsonData[0].reqid);
		//$("#acerChild").val(jsonData[0].areaAcer);
		var fileName = jsonData[0].fileAttach;
		if (fileName != null) {
			var ext = fileName.split(".");
			$("#imageName_0").html(fileName);

			if (ext[1] == "jpg" || ext[1] == "png") {
				var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
					+ fileName + '")></i></a> </div>';
			} else if (ext[1] == "pdf") {
				var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
					+ fileName + '")></i></a> </div>';
			} else {
				var LightImg = "<div class='uploadicon position-l'> </div>";
			}
			$("#uploadedBillDivChild_0").html(LightImg);
		}
		fileName = "";


		/* if((jsonData[0].createdby == $("#loginId").val()
				|| $("#loginId").val() == 'NL91210010')){
			//$("#acerChild").attr('disabled', false);
			$("#assignChild").attr('disabled', false);
			$("#notesChild").attr('disabled', false);
			$("#typeChild").attr('disabled', false);
			$("#precedChild").attr('disabled', false);
			
		}else{
			//$("#acerChild").attr('disabled', true);
			$("#assignChild").attr('disabled', true);
			$("#assignedToChild").attr('disabled', true);
			$("#typeChild").attr('disabled', true);
			
			$("#notesChild").attr('disabled', true);
			$("#notesChild").attr('disabled', true);
		} 
			
		 if(jsonData[0].projectstatus == "TPM0003"){
			 $("#prjstsChild").attr('disabled', true);
			 $("#assignChild").attr('disabled', true);
		}else{
			 $("#prjstsChild").attr('disabled', false);
			 $("#assignChild").attr('disabled', false);
		}
			
		*/


	});
}








function saveExecutionPlanChild() {
	var datas = [];
	var event = {};
	var validation = true;
	if (event.phase == null || event.phase == "") {
		validation = validationUpdated("Task Name Required", 'phaseChild');
	}
	if (validation) {
		event.executionId = $("#executionIdChild").val();
		event.parentId = $("#subCatParentSpanId").val();
		event.categoryId = $("#subCatIdChild").val();
		event.projectId = $("#projectIdChild").val();
		event.preced = $("#precedChild").val();
		event.phase = $("#phaseChild").val();
		event.startDate = $("#startDateChild").val();
		event.endDate = $("#endDateChild").val();
		event.assignedTo = $("#assignedToChild").val();
		event.maintype = $("#typeChild").val();
		event.qtyNeeded = $("#qtyNeededChild").val();
		event.plannedHrs = $("#plannedHrsChild").val();
		event.actualHrs = $("#actualHrsChild").val();
		event.requiDate = $("#requiDateChild").val();
		event.needDate = $("#needDateChild").val();
		event.reqid = $("#reqidChild").val();
		event.notes = $("#notesChild").val();
		event.actualCost = $("#actualCostChild").val();
		event.fileAttach = $("#editFileAttachChild").val();
		event.duration = $("#durationChildDate").val();
		event.planningId = $("#planningIdChild").val();
		event.projectStatus = $("#prjstsChild").val();
		event.budgetCategoryId = $("#budgetcategoryIdChild").val();
		event.projectName = $("#projectNameAddChild").val();
		event.estimatedCost = $("#totalPriceChild").val();
		event.unitPrice = $("#rateChild").val();
		event.quantity = $("#quantityChild").val();
		event.uom = $("#unitChild").val();

		datas.push(event);


		$.ajax({
			type: "POST",
			url: "project-execution-add",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),

			success: function(response) {
				if (response.message == "Success") {

					var idds = response.body[0].projectId
					getProjectExecutionDetails(idds);


					$("#cancel").hide();
					$("#mySidenavParent").hide();
					$("#mySidenavChild").hide();
					$("#saveExecutionPlanParent").hide();
					$("#saveExecutionPlanChild").hide();

				}
			},
			error: function(response) {

			}
		});
	}
}
function calculateDurationC() {
	var startDt = $("#startDateCalendarChildAdd").val();
	var endDt = $("#endDateChildAdd").val();

	agGrid.simpleHttpRequest({
		url: "project-execution-calculationdatetime?startDt=" + startDt + "&endDt=" + endDt
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		console.log('Hours', jsonData)
		$("#plannedHrsChildAdd").val(jsonData[0].hours);
		$("#durationChildDate").val(jsonData[0].days + " " + jsonData[0].hours);
		$("#durationChildAdd").val(jsonData[0].days);

	});
}


/*AutoSearch*/


function getPrecedNameAutoSearch() {
	var search = $("#precedChildAdd").val();
	if (search == "") {
		$("#suggesstion-box1").hide();
	}
	var selected = gridOptions.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var pId = "";
	for (var i = 0; i < selected.length; i++) {
		pId = pI + selected[i].projectId;
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-preced?searchValue="
					+ search + "&id=" + pId,
				success: function(response) {
					if (response.message == "success") {
						//$("#id").empty();
						if (response.body.length != 0) {

							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list" style="color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue1(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box1").show();
							$("#suggesstion-box1").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue1()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box1").show();
							$("#suggesstion-box1").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue1(key) {
	if (key) {
		$("#precedChildAdd").val(key);
		$("#precedChildAdd").html(key);
		$("#search").val(key);
		$("#search").attr('data-procat', key);
		$("#suggesstion-box1").hide();
		//		getDate1(key);

	} else {
		$("#precedChildAdd").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1").hide();

	}
}


function getPrecedNameAutoSearchParent() {
	var search = $("#precedParent").val();
	if (search == "") {
		$("#suggesstion-box11").hide();
	}
	var selected = gridOptions.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var pId = "";
	for (var i = 0; i < selected.length; i++) {
		pId = pId + selected[i].projectId;
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-preced?searchValue="
					+ search + "&id=" + pId,
				success: function(response) {
					if (response.message == "success") {
						//$("#id").empty();
						if (response.body.length != 0) {

							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list" style="color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue11(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box11").show();
							$("#suggesstion-box11").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue11()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box11").show();
							$("#suggesstion-box11").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue11(key) {
	if (key) {
		$("#precedParent").val(key);
		$("#precedParent").html(key);
		$("#search").val(key);
		$("#search").attr('data-procat', key);
		$("#suggesstion-box11").hide();
		//			getDate1(key);

	} else {
		$("#precedParent").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box11").hide();

	}
}
function getPrecedNameAutoSearchChild() {
	var search = $("#precedChild").val();
	if (search == "") {
		$("#suggesstion-box12").hide();
	}
	var selected = gridOptions.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var pId = "";
	for (var i = 0; i < selected.length; i++) {
		pId = pId + selected[i].projectId;
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-preced?searchValue="
					+ search + "&id=" + pId,
				success: function(response) {
					if (response.message == "success") {
						//$("#id").empty();
						if (response.body.length != 0) {

							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list" style="color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue12(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box12").show();
							$("#suggesstion-box12").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue12()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box12").show();
							$("#suggesstion-box12").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue12(key) {
	if (key) {
		$("#precedChild").val(key);
		$("#precedChild").html(key);
		$("#search").val(key);
		$("#search").attr('data-procat', key);
		$("#suggesstion-box12").hide();
		//				getDate1(key);

	} else {
		$("#precedChild").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box12").hide();

	}
}


function getAssignedToAutoSearch() {
	var search = $("#assignChildAdd").val();
	if (search == "") {
		$("#suggesstion-box2").hide();
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-assignTo?searchValue="
					+ search,
				success: function(response) {
					if (response.message == "success") {
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list" style="color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {

								content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue2(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].name
									+ '\')">'
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box2").show();
							$("#suggesstion-box2").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list">';
							content += '<div onClick="selectAutocompleteValue2()">'
								+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box2").show();
							$("#suggesstion-box2").html(content);

						}
					}
				},
				error: function(data) {
				}
			})
	}

}
function selectAutocompleteValue2(key, ame) {
	if (name) {
		$("#assignedToChildAdd").val(key);
		$("#assignChildAdd").val(name);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box2").hide();
		//getDate1(key);

	} else {
		$("#assignedToChildAdd").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box2").hide();

	}
}


function getAssignedToParentAutoSearch() {
	var search = $("#ATnameParent").val();

	console.log("searchhh in getAssignedToMainAutoSearch " + search)
	if (search == "") {
		$("#suggesstion-box3").hide();
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-assignTo?searchValue="
					+ search,
				success: function(response) {
					if (response.message == "success") {
						console.log(response.body);
						if (response.body.length != 0) {
							$("#search").css("background", "#6A2BBF");
							var content = '<ul id="autocomplete-list" style="color:#6A2BBF;">';
							for (var i = 0; i < response.body.length; i++) {
								console.log(response.body[i].key + " - " + response.body[i].name)

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
							$("#search").css("background", "#6A2BBF");
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
					console.log(data);
				}
			})
	}

}
function selectAutocompleteValue3(key, name) {

	if (name) {
		$("#ATnameParent").val(name);
		$("#assignedToParent").val(key);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box3").hide();
		//getDate1(key);

	} else {
		$("#assignedToParent").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3").hide();

	}

}

function getAssignedToAutoSearchChild() {
	var search = $("#assignChild").val();

	console.log("searchhh in getAssignedToMainAutoSearch " + search)
	if (search == "") {
		$("#suggesstion-box5").hide();
	}
	if (search) {
		$
			.ajax({
				type: "GET",
				url: "project-execution-autosearch-assignTo?searchValue="
					+ search,
				success: function(response) {
					if (response.message == "success") {
						console.log(response.body);
						if (response.body.length != 0) {
							$("#search").css("background", "#6A2BBF");
							var content = '<ul id="autocomplete-list" style="color:#6A2BBF;">';
							for (var i = 0; i < response.body.length; i++) {
								console.log(response.body[i].key + " - " + response.body[i].name)

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
					console.log(data);
				}
			})
	}

}
function selectAutocompleteValue5(key, name) {

	if (name) {
		$("#assignChild").val(name);
		$("#assignedToChild").val(key);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box5").hide();
		//getDate1(key);

	} else {
		$("#assignedToChild").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box5").hide();

	}
}

function calculationAmntC() {
	var qty = parseFloat($("#quantityChild").val()) || 0;
	var rate = parseFloat($("#rateChild").val()) || 0;

	var totalAmt = qty * rate;

	$("#totalPriceChild").val(totalAmt.toFixed(2));
}

function calculationAmntP() {
	var qty = parseFloat($("#quantityParent").val()) || 0;
	var rate = parseFloat($("#rateParent").val()) || 0;

	var totalAmt = qty * rate;

	$("#totalPriceParent").val(totalAmt.toFixed(2));
}

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	var len = displayedRowCount;
	$('#totalReqs').find('span').html(len);
}


function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
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
	if (extension[1] == "jpg" || extension[1] == "png"
		|| extension[1] == "jpeg") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></span>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></span>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></span>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></span>";
	} else {
		var LightImg = "<span class='uploadicon position-l '> </div>";
	}

	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm()'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);
	$("#dltImage_" + counter).addClass("custom-file-delete");

	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}
function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				$("#messageParagraph").text("Please Choose a File ");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMore1()
	}
}
function addMore1() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:first").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	$("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	$("#docTbl tbody tr:last").find(".dltImage").empty();

	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');

		selectInput.eq(0).attr('id', "docid_" + i);
		textInput.eq(1).attr('id', "docnoid_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(4).attr('id', "uploadedBillDiv_" + i);
		divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);

		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

}

function checkForDuplicateEntry(event) {
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var count = 0;
	$(".docNoclss").each(function() {
		if (document == $(this).val()) {
			count++;
		}
	})
	if (count >= 2) {
		$("#messageParagraph").text("Document Name Already Entered");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');

		return false;
	} else {
		return true;
	}

}
function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}
//for closeing modal box for dlt ind product
function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}

function deletAttachmentRow() {
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	closeDeleteConfirm();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
			+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div> </td>'
			+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}



/*Save Parent Data*/

function saveFileParent() {

	var uFile = $(uploadDoc_0Parent)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_0Parent").html("");

	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg" || extension[1] == "JPG") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDiv_0Parent").html(LightImg);
	$("#fileAttachParent").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$("#editFileAttachParent").val("");

	$.ajax({
		type: "POST",
		url: "project-execution-upload-file",
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
	fileName = "";

}


/*Save Child Data */

function saveFileChild() {

	var uFile = $(uploadDocChild_0)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDivChild_0").html("");

	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg" || extension[1] == "JPG") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDivChild_0").html(LightImg);
	$("#fileAttachChild").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$("#editFileAttachChild").val("");

	$.ajax({
		type: "POST",
		url: "project-execution-upload-file",
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
	fileName = "";
}

function cancel() {
	$("#mySidenavParent").hide();
	$("#mySidenavChild").hide();
	$("#saveExecutionPlanParent").hide();
	$("#saveExecutionPlanChild").hide();
	$("#cancel").hide();
}

