var id = "";
$(document).ready(function() {
	$("#browseBtn").click(function() {

		$("#myModal").modal('show');


	})

	gridOptionsNew.api.setRowData();


	/*agGrid.simpleHttpRequest({
		url: "planning-scheduling-view"
	}).then(function(data) {
		alert("hahaha")
	//	var len = data.length;
	//	$('#totalReqs').find('span').html(len);
		gridOptions1.api.setRowData(data);
		var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
			  if (firstRowNode) {
			    firstRowNode.setSelected(true); // Set the row as selected
			  }
		console.log(data, 'dartaaa')
		id = data[0].projectId
		projectData(id);
		getCategoryList(id);
	});*/


	$("#selectCategory").click(function() {
		$("#myModalSelected").modal("show");
		$("#myModal").modal("hide");
		closeNav();


	});

});

function projectData(id) {
	$
		.ajax({
			type: "GET",
			url: "planning-scheduling-getfirst-row-data?id=" + id,
			success: function(response) {

				var len = response.length;
				$('#totalReq').find('span').html(len);
				gridOptions.api.setRowData(response);
				console.log(response)
			}
		});
}

function openNav(catId,pId,planId) {
	console.log(planId)
	$("#catId").val(catId);
	$("#pId").val(pId);
	$("#serviceName").val('');
	$("#date").val('');
	$("#time").val('');
	$("#description").val('');
	// $("#toPlace").val('');
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:250px;";

	document.getElementById("main").style.width = "75%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
	$("#planId").val('');
	$("#date").val('');
	$("#time").val('');
	$("#description").val('');
	//$("#toPlace").val('');
	//$("#rowEdit").val(null);
}

$(function() {
	$("input[name='advanceReq']").click(function() {
		if ($("#No").is(":checked")) {
			$("#amount").hide();
			$("#advanceAmount").val("");

		} else {
			$("#amount").show();
		}
	});
});

var count1 = 0;
function allCheck1() {
	count1++;

	if (count1 == 1) {
		$('.checkCls1').prop("checked", true);
	} else {
		count1 = 0;
		$('.checkCls1').prop("checked", false);
	}
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
		id.style.display = "none";
	}
}


$(document).ready(function() {

	var userid = $("#sessionId").val();
	var userrole = $("#sessionRole").val();
	var roleid = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleid = roleid + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleid = roleid.substring(0, roleid.length - 1);

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})



	//     date format TO date

	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date(),
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})

	$('#endDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})



	///TO_date
	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#startDate').val($(this).val());
	})

	$('#startDate').blur(function() {
		$("#DateCalendar").val($(this).val());
	})


	$("#DateCalendar1").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})

	$('#endDate').blur(function() {
		$("#DateCalendar").val($(this).val());
	})


	$("#statusDiv").hide();
	$('#reqDltBtn').attr('disabled', true);
	$('#approve').attr('disabled', true);
	$('#reject').attr('disabled', true);
	$('#deleteChild').attr('disabled', true);
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	$('.collapse').on('hide.bs.collapse', function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});
	$("#newBtn").click(function() {
		$("#myGrid").hide();
		$("#reqTable").hide();
		//$("#searchDiv").hide();
		$(".btn-hs").show();
		$("#btndiv").hide();
		$("#listdiv").hide();
		$("#totalReq").hide();
		$("#searchRowDiv").hide();
		$("#ttbtn").hide();
		$("#demo").show();

	})

});

/* -------------------search bar for mygrid------------------------ */

function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}


function submitYearData(dataset) {
	$.ajax({
		type: "POST",
		url: "planning-scheduling-save-year",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "success") {
				/* $('.loader').hide();
				$("body").removeClass("overlay"); */
				closeNav();

				var div = '<div id="' + response.body.yearId + '" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("' + response.body.yearId + '","' + response.body.year + '","' + response.body.monthDtls + '")>' +
					'<a href="javascript:void(0)"><div style="font-size: 34px;">' + response.body.year + '</div> ' +
					'<span style="font-size: 15px;">' + response.body.monthDtls + '</span></a></div>';
				$("#yearDiv").append(div);
			}
		}, error: function(data) {
			console.log(data)
			/* $('.loader').hide();
			$("body").removeClass("overlay"); */
		}
	});
}

function getYearDataList() {
	$("#yearDiv").empty();
	//$('.loader').show();
	//$("body").addClass("overlay");
	$.ajax({
		type: "POST",
		url: "planning-scheduling-get-year-list",
		dataType: "json",
		contentType: "application/json",
		data: "A",
		success: function(response) {
			if (response.message == "success") {
				console.log(response.body)

				var id = response.body[0].year;
				var mnth = response.body[0].monthDtls;
				mnth = mnth.replaceAll(" ", "");
				mnth = mnth.split("-");
				setMonthList(mnth[0], mnth[2]);
				for (var i = 0; i < response.body.length; i++) {
					var mnth = response.body[i].monthDtls;
					mnth = mnth.replaceAll(" ", "");
					mnth = mnth.split("-");
					var start = mnth[0];
					var end = mnth[2];
					var div = '<div id="' + response.body[i].yearId + '" class="databasebox_mail divcls" style="text-align: center !important;" onclick=openDetails("' + response.body[i].yearId + '","' + response.body[i].year + '","' + start + '","' + end + '")>' +
						'<a href="javascript:void(0)"><div style="font-size: 34px;">' + response.body[i].year + '</div> ' +
						'<span style="font-size: 15px;">' + response.body[i].monthDtls + '</span></a></div>';
					$("#yearDiv").append(div);



					if (i == 0) {
						$("#" + response.body[i].yearId).removeClass("databasebox_mail");
						$("#" + response.body[i].yearId).addClass("databasebox_mailactive");
					}
				}

				$("#hiddenYear").val(response.body[0].year);

				getBudgetPlanData(id);

			}
		}, error: function(data) {
			console.log(data)
		}
	});
}


function cancel() {
	$(".formValidation").remove();
	$("#reqTable").show();
	//$("#searchDiv").show();
	$(".btn-hs").show();
	$("#myGrid").show();
	$("#myGrid1").show();
	$("#demo").hide();
	$("#totalReqs").show();
	$("#totalReq").show();
	$("#searchRowDiv").show();
	var userid = $("#sessionId").val();
	var userrole = $("#sessionRole").val();
	var roleid = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleid = roleid + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleid = roleid.substring(0, roleid.length - 1);

	$("#employeeId").val('');
	$("#fromDate").val('');
	$("#toDate").val('');
	$("#planId").val('');
	$("#date").val('');
	$("#time").val('');
	$("#description").val('');
	//$("#toPlace").val('');
	$("#rowEdit").val(null);
};


//master save data

function masterSaveData() {
	var item = {};
	var valid = true;
	var data = 1;
	var rowEdit = $("#rowEdit").val();
	if (true) {

		gridOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});

		item.category = $("#category").val();
		item.subCategory = $("#subCategory").val();
		item.projectName = $("#projectName").val();
		item.projectLocation = $("#projectLocation").val();
		item.projectinCharge = $("#projectinCharge").val();
		item.planId = $("#planId").val();
		item.priority = $("#priority").val();
		item.taskName = $("#taskName").val();
		item.startDate = $("#startDate").val();
		item.endDate = $("#endDate").val();
		item.assignedTo = $("#assignedTo").val();
		item.duration = $("#duration").val();
		item.predecessors = $("#predecessors").val();
		item.notes = $("#notes").val();
		item.slnoId = data;
		item.projectplanId = data;
		var datas = [];

		if (valid) {
			cancel();
			if (rowEdit) {
				var rowNode = gridOptions.api.getRowNode(rowEdit);
				rowNode.setData(item);
			} else {

				gridOptions.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				gridOptions.api.setRowData(datas);
			}
		} else {
			$('#demo').show();

		}
	}
}


$("#newBtn").click(function() {
	$("#myGrid").hide();
	$("#reqTable").hide();
	$(".btn-hs").hide();
	$("#demo").show();
	$("#totalReq").hide();
	$("#searchRowDiv").hide();
	$("#ttbtn").hide();
})

var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Plan Id',
		width: 150,
		field: "slnoId",
		/* cellRenderer : function(params) {
					return '<a id="projectplanId" onclick=editTravel("'
					+ params.data.projectplanId
					+ '") href="javascript:void(0)">'
					+ params.data.projectplanId + '</a>';
		} */
		/* 				cellRenderer : function(params) {
							return '<a id="requisitionId" onclick=editTravel("'
									+ params.data.travelingReqId+','
									+ params.data.employeeId+','+params.data.status
									+ '") href="javascript:void(0)">'
									+ params.data.travelingReqId + '</a>';
		
						} */
	}, {
		headerName: 'Category',
		width: 250,
		field: "category",
	}, {
		headerName: 'Sub Category',
		width: 250,
		field: "subCategory",
	}, {
		headerName: 'Project Name',
		field: "projectName",
		width: 250,
	}, {
		headerName: 'Project Location',
		field: "location",
		width: 250,
		cellStyle: {
			textAlign: 'center'
		}
	},
	{
		headerName: 'Project In Charge',
		field: "pIncharge",
		width: 300,
		cellStyle: {
			textAlign: 'center'
		}
	},/* {
		headerName: 'Serial No',
		field: "slnoId",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Priority',
		field: "priority",
		width: 150,
	}, {
		headerName: 'Task Name',
		width: 150,
		field: "taskName",
		 cellRenderer : function(params) {
			if (params.data.status =="Pending") { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status =="Forwarded") {
				return '<div style="color:#ffa500">Forwarded</div>';
			} else if (params.data.status == "Approved") {
				return '<div style="color:#0642f5">Approved</div>';
			} else {
				return '<div style="color:#ff8242">Rejected</div>';
			}	
		} 
	}, {
		headerName: 'Start Date',
		field: "startDate",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		},
		 				cellRenderer : function(params) {
							if (params.data.advanceReq=='0') {
								return 'NO';
							} else {
								return 'YES';
							}
						} 
	}, {
		headerName: 'End Date',
		width: 150,
		field: "endDate",
	}, {
		headerName: 'Assigned To',
		width: 180,
		field: "assignedTo"

	}, {
		headerName: 'Duration',
		field: "duration"
	}, {
		headerName: 'Predecessors',
		width: 180,
		field: "predecessors"

	}, {
		headerName: 'Notes',
		field: "notes",
		width: 200,
	}*/

];


var columnDefs1 = [ /*{
		 headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, */ {
		headerName: "Project ID",
		field: "projectId",
	}, {
		headerName: "Project Name",
		field: "projectName",
	}, {
		headerName: "Creation Date",
		field: "creationDate",
		width: 150,
	}, {
		headerName: 'Location',
		field: "location",
		width: 130,
	}, {
		headerName: "State",
		field: "state",
		width: 150,
	}, {
		headerName: 'Pin',
		field: "pPin",
	}, {
		headerName: 'Project Incharge',
		field: "pIncharge",
	}, {
		headerName: 'Customer Name',
		field: "cName",
	}, {
		headerName: 'Customer Address',
		field: "cAddress",
	}, {
		headerName: 'State',
		field: "cState",
		width: 150,

	}, {
		headerName: 'Pin',
		field: "cPin",
		width: 150,

	}, {
		headerName: 'Email',
		field: "email",
		width: 150,

	}, {
		headerName: 'Mobile',
		field: "mobile",
		width: 150,

	}, {
		headerName: 'Remarks',
		field: "remark",
		width: 150,

	}, {
		headerName: 'Status',
		field: "status",
		width: 150,

	}];



// let the grid know which columns and what data to use
var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'multiple',
	//onSelectionChanged : onSelectionChanged
};


var gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20,
		flex: 1,
		minWidth: 100
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChanged
};

var columnDefsNew = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		id: 'selectionColumn',
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {

		headerName: "SL No.",
		field: "slnoId",
		width: 80,
	}, {

		headerName: " Category",
		field: "category",
		width: 150,
	}, {
		headerName: ' Sub-Category',
		field: "subCategory",
		width: 150
	}, {
		headerName: 'Project Name',
		field: "projectName",
		width: 150,
	}, {

		headerName: " Project Location",
		field: "location",
		width: 150,
	}, {
		headerName: ' Project-In-Charge',
		field: "pIncharge",
		width: 150
	},
];

var gridOptionsNew = {
	columnDefs: columnDefsNew,
	//rowData : rowdataNew,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'multiple',
	onSelectionChanged : selectData
};
var copyId="";
function selectData(){
	copyId="";
	var selectedRows = gridOptionsNew.api.getSelectedRows();
	var selecteds = gridOptions1.api.getSelectedRows();
	for (var i = 0; i < selectedRows.length; i++) {
		copyId = copyId  + selectedRows[i].slnoId + ',';
	}
	copyId = copyId.substring(0, copyId.length - 1);
	var id = selecteds[0].projectId;
	console.log(id+"  @@  "+copyId)
	
}
// let the grid know which columns and what data to use product table
/* var activityOptions = {
	columnDefs : activityDefs,
	rowData: rowData1,
	rowSelection : 'multiple',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 200
	},
	onSelectionChanged : onSelectionChangeChild,
	getRowNodeId : function(data) {
		return data.slnoId;
	}
}; */

var id = "";
var creq = "";
function onSelectionChangeChild() {
	var selectedNodes = activityOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	creq = selectedData.map(node => node.slnoId);
	var empID = selectedData.map(node => node.employeeId);

	var selectedRows = activityOptions.api.getSelectedRows();
	id = "";
	for (var i = 0; i < selectedRows.length; i++) {

		id = id + '"' + selectedRows[i].travelingReqId + '",';
		console.log(selectedRows[i].status);
	}
	id = id.substring(0, id.length - 1);

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newchild').attr('disabled', true);
		$('#deleteChild').attr('disabled', false);

	} else {
		$('#newchild').attr('disabled', false);
		$('#deleteChild').attr('disabled', true);

	}
}


var id = "";
var treq = "";
function onSelectionChanged() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var id = selectedRows[0].projectId;
	projectData(id);

}
// for editing child table of shift 
/*function editRow(rowNo) {
	var rowNode = activityOptions.api.getRowNode(rowNo);

	openNav();

	$("#rowEdit").val(rowNo);
	$("#planId").val(rowNode.data.planId);
	$("#serviceName").val(rowNode.data.serviceName);
	$("#date").val(rowNode.data.date);
	$("#time").val(rowNode.data.time);
	$("#description").val(rowNode.data.description);
	//	$("#toPlace").val(rowNode.data.toPlace);
}*/

//editing the employee shift details parent table


// delete selected record from ag grid
function deleteDetailsOnclick() {
	$('.modal').hide();
	var selectedRows = activityOptions.api.getSelectedRows();
	activityOptions.api.applyTransaction({
		remove: selectedRows
	});
	//$('#newchild').show();
	//$('#deletechild').hide;
	cancelModalProductBtn();

}

//for closeing modal box for dlt  product
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteDetails').modal('hide');
	//$('#newchild').show();
	//$('#deletechild').hide;
}

function deleteFun() {
	$('#delete').modal('show');
}

function deleteDetails() {
	$('#deleteDetails').modal('show');
}

function cancelModalBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
}

// save data in aggrid table sidenav save

function saveTableData() {
	var item = {};
	var valid = true;
	var data = 1;
	var rowEdit = $("#rowEdit").val();
	if (true) {

		activityOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		item.planId = $("#planId").val();
		item.priority = $("#priority").val();
		item.taskName = $("#taskName").val();
		item.startDate = $("#startDate").val();
		item.endDate = $("#endDate").val();
		item.assignedTo = $("#assignedTo").val();
		item.duration = $("#duration").val();
		item.predecessors = $("#predecessors").val();
		item.notes = $("#notes").val();
		item.slnoId = data;
		var datas = [];

		if (item.priority == null || item.priority == "") {
			valid = validationUpdated("Priority is Required", "priority");

		}
		if (item.taskName == null || item.taskName == "") {
			valid = validationUpdated("Task Name Required", "taskName");

		}
		if (item.startDate == null || item.startDate == "") {
			valid = validationUpdated("Start Date Required", "startDate");

		}
		if (item.endDate == null || item.endDate == "") {
			valid = validationUpdated("End Date Required", "endDate");

		}
		if (item.assignedTo == null || item.assignedTo == "") {
			valid = validationUpdated("Assigned To Required", "assignedTo");

		}
		if (item.duration == null || item.duration == "") {
			valid = validationUpdated("Duration Required", "duration");

		}
		if (item.predecessors == null || item.predecessors == "") {
			valid = validationUpdated("Predecessors Required", "predecessors");

		}
		if (item.notes == null || item.notes == "") {
			valid = validationUpdated("Notes Required", "notes");

		}

		if (valid) {
			closeNav();
			if (rowEdit) {
				var rowNode = activityOptions.api.getRowNode(rowEdit);
				rowNode.setData(item);
			} else {

				activityOptions.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				activityOptions.api.setRowData(datas);
			}
		} else {
			$('#mySidenav').show();

		}
	}
}
// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);


	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1)

	var gridDiv2 = document.querySelector('#myGrid2');
	new agGrid.Grid(gridDiv2, gridOptionsNew)


});

function switchPage() {



	$("#myGrid1").hide();
	$("#totalReq").hide();
	$("#totalReqs").hide();

	$("#date").val('');
	$("#time").val('');
	$("#description").val('');
	//$("#toPlace").val('');
	$("#rowEdit").val(null);
	$("#fromDate").val();
	$("#toDate").val();
	$("#employeeId").val();
	$("#placeName").val();
	$("#purpose").val();
	$("#advanceReq").val();
	var selectedRows = gridOptions.api.getSelectedRows();
	
	
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.itemId;
	});
	////$('.loader').show();
	//$("body").addClass("overlay");
	////$('.loader').hide();
	//$("body").removeClass("overlay");
	$("#deleteChild").attr('disabled', true);
	$("#newchild").attr('disabled', false);
	var selectedRows = gridOptions1.api.getSelectedRows();
	console.log('selectedRows########',selectedRows)
	var id = selectedRows[0].projectId;
	getplnschdtls(id);
	var projectname = selectedRows[0].projectName;
	var location = selectedRows[0].location;
	var inchcarge = selectedRows[0].pIncharge;

	$("#projectId").val(id);
	$("#projectName").val(projectname);
	$("#projectLocation").val(location);
	$("#projectinCharge").val(inchcarge);
}
function getplnschdtls(id) {
	$("#tbodyData12").empty();
	agGrid.simpleHttpRequest({
		url: "planning-scheduling-getscheduledtls?id=" + id
	}).then(function(response) {
		if (response.message == "Success") {
			console.log("responseeeeeeeee", response);
			$("#tbodyData12").empty();
			for (i = 0; i < response.body.length; i++) {
				console.log(i);
				if (response.body[i].categoryid == response.body[i].parentid) {
					content = '<tr data-node-id="' + response.body[i].categoryid + '" class="abc" id="' + response.body[i].categoryid + '">'
						+ '<td class="firstnode" id=lbl_' + response.body[i].categoryid + '><'
						+ response.body[i].categoryid
						+ '" value="'
						+ response.body[i].catlevel
						+ '" name="'
						+ response.body[i].category
						+ '")>'
						+ response.body[i].category
						+ '<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("' + response.body[i].categoryid + '","' + response.body[i].projectId + '","' + response.body[i].projectplanId + '","1");></i></span><span><i class="fa fa-edit" onclick=editCategory("' + response.body[i].categoryid + '","' + response.body[i].projectplanId + '")></i></span>'
						+ '</td>'
						+ '<td class="firstnode">' + response.body[i].priority + '</td>'
						+ '<td class="firstnode">' + id + '</td>'
						+ '<td class="firstnode">' + response.body[i].startDate + '</td>'
						+ '<td class="firstnode">' + response.body[i].endDate + '</td>'
						+ '<td class="firstnode">' + response.body[i].assignedTo + '</td>'
						+ '<td class="firstnode">' + response.body[i].duration + '</td>'
						+ '<td class="firstnode">' + response.body[i].predecessors + '</td>'
						+ '<td class="firstnode">' + response.body[i].notes + '</td>'

						+ '</tr>';

				} else {

					content = '<tr data-node-id="' + response.body[i].categoryid + '" data-node-pid="' + response.body[i].parentid + '" class="abc" id="' + response.body[i].categoryid + '">'
						+ '<td class="firstnode" id=lbl_' + response.body[i].categoryid + '><'
						+ response.body[i].categoryid
						+ '" value="'
						+ response.body[i].catlevel
						+ '" name="'
						+ response.body[i].category
						+ '")>'
						+ response.body[i].category
						+ '<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("' + response.body[i].categoryid + '","' + response.body[i].projectId +  '","' + response.body[i].projectplanId + '","1");></i></span><span><i class="fa fa-edit" onclick=editSubCategory("' + response.body[i].categoryid + '","' + response.body[i].projectplanId + '")></i></span>'
						+ '</td>'
						+ '<td class="firstnode">' + response.body[i].priority + '</td>'
						+ '<td class="firstnode">' + id + '</td>'
						+ '<td class="firstnode">' + response.body[i].startDate + '</td>'
						+ '<td class="firstnode">' + response.body[i].endDate + '</td>'
						+ '<td class="firstnode">' + response.body[i].assignedTo + '</td>'
						+ '<td class="firstnode">' + response.body[i].duration + '</td>'
						+ '<td class="firstnode">' + response.body[i].predecessors + '</td>'
						+ '<td class="firstnode">' + response.body[i].notes + '</td>'
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
		} else {
			swal({
				title: response.code,
				text: response.message,
				type: "warning"
			})
		}


	});
}

/* -------------------function for approve button----------------- */
var empid = "";
var empname = "";
function approveRequisition() {
	empid = $("#sessionId").val();
	empname = $("#sessionName").val();
	comment = $("#comment").val();
	var userrole = $("#sessionRole").val();

	var roleID = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleID = roleID + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleID = roleID.substring(0, roleID.length - 1);

	$.ajax({
		type: "GET",
		url: "travel-requisition-approve?approveId=" + treq + "&name=" + empid + "&comment=" + comment + "&roleid=" + roleID,
		async: false,
		success: function(response) {

			if (response.message == "Success") {
				cancel();
				$('#approve').attr('disabled', true);
				$('#reqDltBtn').attr('disabled', true);
				$('#reject').attr('disabled', true);
				$('#newBtn').attr('disabled', false);
				$("#comment").val(null);
			}

		},
		error: function(data) {
		}
	});
}

/*-----------------------function for reject button------------------- */
var rejempid = "";
var rejempname = "";
function rejectRequsition() {
	rejempid = $("#sessionId").val();
	rejempname = $("#sessionName").val();
	comment = $("#comment").val();
	var userrole = $("#sessionRole").val();

	var roleid = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleid = roleid + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleid = roleid.substring(0, roleid.length - 1);
	$.ajax({
		type: "GET",
		url: "travel-requisition-reject?rejectId=" + treq + "&name=" + rejempid + "&comment=" + comment + "&roleid=" + roleid,
		async: false,
		success: function(response) {

			if (response.message == "Success") {
				cancel();

				$('#approve').attr('disabled', true);
				$('#reqDltBtn').attr('disabled', true);
				$('#reject').attr('disabled', true);
				$('#newBtn').attr('disabled', false);
				$("#comment").val(null);
			}

		},
		error: function(data) {
		}
	});
}

/* Function for commentModal show */
function rejectRequistionModal() {

	$('#commentModal').modal('toggle');
	$("#approveLeaveSubmitBtn").hide();
	$("#rejectLeaveSubmitBtn").show();
}
function approveRequistionModal() {

	$('#commentModal').modal('toggle');
	$("#approveLeaveSubmitBtn").show();
	$("#rejectLeaveSubmitBtn").hide();

}

function downloadDetails() {
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
	gridOptions.api.exportDataAsCsv(dataset);
}

function check1(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	var val = parseInt(tempVal);
	if (val >= 100) {
		$("#" + fieldId).val(val);
	}
	else {
		$("#messageParagraph").text(
			"Please add more than Rs.100 ");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		$("#" + fieldId).val(null);

	}
}
function checkAmount1(fieldId) {
	var myField = document.getElementById("advanceAmount")
	var reg = /^\d{0,8}(\.\d{0,2})?$/;
	if (reg.test(myField.value)) {
		$("#" + fieldId).val();
		reg = '';
	} else {
		$("#" + fieldId).val(null);
	}
}


function check(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	$("#" + fieldId).val(tempVal);
}
function setFromToDate() {
	$("#messageParagraph").text("Please choose to date greater than or equal to from date ");
	$("#msgOkModal").removeClass("btn3");
	$("#msgOkModal").addClass("btn1");
	$("#msgModal").modal('show');
	$("#fromDate").val("");
	$("#toDate").val("");
}
function dateChange() {

	var fromdate = $('#fromDate').val();
	var todate = $('#toDate').val();
	var fd = fromdate.split("-");
	var td = todate.split("-");

	if (fromdate != '' && todate != '') {
		if (fd[2] <= td[2]) {
			if (fd[1] == td[1]) {
				if (fd[0] <= td[0]) {

				} else {
					setFromToDate();
				}
			} else if (fd[1] < td[1]) {

			} else {
				setFromToDate();
			}

		} else {
			setFromToDate();
		}
	} else {

	}
}

function category() {
	if ($("#category").val("Manpower")); {
		$("#subCategory").val("Fabrication");
		$("#subCategory").val("Painting");


	}
}

function search() {
	$('#deleteid').modal('show');
	var selectedRows = gridOptions1.api.getSelectedRows();
	var item = {};
	var id = selectedRows[0].projectId;

	$
		.ajax({
			type: "GET",
			url: "planning-scheduling-projectlist?id=" + id,
			success: function(response) {

				if (response.message == "Success") {
					$("#projectNameModal").empty();
					$("#projectNameModal").append(
						"<option value=''>Select</option>");
					for (var i = 0; i < response.body.length; i++) {

						var option = $("<option ></option>");
						$(option).val(response.body[i].projectId);
						$(option).html(response.body[i].projectName);
						$("#projectNameModal").append(option);


					}

				}
				else {
					$("#projectNameModal").append(
						"<option value=''>Select</option>");
				}
			}
		});

}
function cancelR() {
	$('#deleteid').modal('hide');
}
var length = 0;
function copy() {
	var item = {};
	var selectedRow = gridOptions1.api.getSelectedRows();
	item.projectId = selectedRow[0].projectId;
	item.priority = $("#projectNameModal").val();


	var selectedRows = gridOptionsNew.api.getSelectedRows();
	var sino = [];
	selectedRows.forEach(function(selectedRows, index) {
		var obj = {}
		obj.slnoId = selectedRows.slnoId;
		sino.push(obj);
	});

	item.copylist = sino;
	gridOptions.api.setRowData([]);
	console.log(JSON.stringify(item))
	$.ajax({
		type: "POST",
		url: "planning-scheduling-copyproject",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(item),
		success: function(response) {
			gridOptionsNew.api.setRowData([]);

			$('#deleteid').modal('hide');
			var len = response.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(response);
		}

	});
}

function getProjectId() {
	gridOptionsNew.api.setRowData([]);
	var selectedRows = gridOptions1.api.getSelectedRows();
	var id = selectedRows[0].projectId;
	console.log(id)
	var projectplId = $("#projectNameModal").val();
	console.log(projectplId)
	agGrid.simpleHttpRequest({
		url: 'planning-scheduling-getProjectDetails?id=' + projectplId + "&rowid=" + id
	}).then(function(data) {
		console.log(data)
		gridOptionsNew.api.setRowData(data);
		length = data.length;

	});
}

function closeModal() {
	location.reload()
}

function openCat() {
	window.location.href = "/projects/project-category";
}

function getCategoryList(id) {
	$("#costCeneterCBDiv").empty();
	$.ajax({
		type: "GET",
		url: "planning-scheduling-get-total-list?id=" + id,
		dataType: "json",
		contentType: "application/json",
		success: function(response) {
			if (response.message == "Success") {
				console.log(response.body)
				if (response.body.length > 0) {
					console.log("responseeeeeeeeeee" + JSON.stringify(response.body));
					for (var i = 0; i < response.body.length; i++) {
						var row = "";
						if (response.body[i].categoryId == response.body[i].parentId) {

							row = '<tr data-node-id="' + response.body[i].categoryId + '" class="abc" id="' + response.body[i].categoryId + '">'
								+ '<td class="firstnode1" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_'
								+ response.body[i].categoryId
								+ '" value="'
								+ response.body[i].catLevel
								+ '" name="'
								+ response.body[i].categoryName
								+ '"onchange="selectCheckBox(\''+ response.body[i].categoryId+ '\',\''
								+ response.body[i].catLevel
								+ '\',\''
								+ response.body[i].projectId
								+ '\',\''
								+ response.body[i].nodeSlNo
								+ '\',\''
								+ response.body[i].categoryName
								+ '\',\'' + response.body[i].parentId + '\')">'
								+ response.body[i].categoryName
								+ '</td></tr>';

						} else {

							row = '<tr data-node-id="' + response.body[i].categoryId + '" data-node-pid="' + response.body[i].parentId + '" class="abc" id="' + response.body[i].categoryId + '">'
								+ '<td class="firstnode1" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_'
								+ response.body[i].categoryId
								+ '" value="'
								+ response.body[i].catLevel
								+ '" name="'
								+ response.body[i].categoryName
								+ '" onchange="selectCheckBox(\''
								+ response.body[i].categoryId
								+ '\',\''
								+ response.body[i].catLevel
								+ '\',\''
								+ response.body[i].projectId
								+ '\',\''
								+ response.body[i].nodeSlNo
								+ '\',\''
								+ response.body[i].categoryName
								+ '\',\''
								+ response.body[i].parentId
								+ '\')">'
								+ response.body[i].categoryName
								+ '</td></tr>';

						}
						$("#costCeneterCBDiv").append(row);
					}

					$('.loader').hide();
					$("body").removeClass("overlay");

					$('#basic1').simpleTreeTable({
						expander: $('#expander'),
						collapser: $('#collapser'),
						store: 'session',
						storeKey: 'simple-tree-table-basic'
					});

				}
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		},
		error: function(response) {
			console.log(response);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})
}
var x = [];
function selectCheckBox(id, lvl, pid, slno, pidd, name) {
	var selectedRowsProject = gridOptions1.api.getSelectedRows();
	console.log(selectedRowsProject[0].projectId)
	var item = {};

	$("#ccCheck_" + id).prop("checked", true);
	var checkboxs = $(".benefitChk:checked").length;
	item.categoryid = id;
	item.catlevel = lvl;
	item.projectId = selectedRowsProject[0].projectId;
	item.slnoId = slno;
	item.parentid = pidd;
	item.category = name;
	x.push(item);

}


function editCategory(id, exeId) {
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:250px;";

	document.getElementById("main").style.width = "75%";
	$("#CatIdMain").val(id);
	$("#planningid").val(exeId);
	$.ajax({
		type: "GET",
		url: "planning-scheduling-child-edit?id=" + id + "&planid=" + exeId,
		success: function(response) {
			if (response.message == "Success") {
				$("#priorityC").val(response.body.priority);
				$("#startDateC").val(response.body.startDate);
				$("#endDateC").val(response.body.endDate);
				$("#assignedToC").val(response.body.assignedTo);
				$("#durationC").val(response.body.duration);
				$("#predecessorsC").val(response.body.predecessors);
				$("#notesC").val(response.body.notes);

			}
		}
	});

}
function editSubCategory(id, exeId) {
	
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:250px;";

	document.getElementById("main").style.width = "75%";
	$("#CatIdMain").val(id);
	$("#planningid").val(exeId);
	$.ajax({
		type: "GET",
		url: "planning-scheduling-child-edit?id=" + id + "&planid=" + exeId,
		success: function(response) {
			if (response.message == "Success") {
				$("#priorityC").val(response.body.priority);
				$("#startDateC").val(response.body.startDate);
				$("#endDateC").val(response.body.endDate);
				$("#assignedToC").val(response.body.assignedTo);
				$("#durationC").val(response.body.duration);
				$("#predecessorsC").val(response.body.predecessors);
				$("#notesC").val(response.body.notes);


			}
		}
	});

}
function saveselected() {
	var planid = $("#planningid").val();
	var id2 = $("#catId").val();
	
	console.log(planid+" @@ "+id2)
	
	var selectedRows = gridOptions1.api.getSelectedRows();
	var id1 = selectedRows[0].projectId;
	if (id2) {
		alert("in iff"+id2)
		var org = {};
		org.proplanid = planid;
		org.categoryid = id2;
		org.priority = $("#priorityC").val();
		org.startDate = $("#startDateC").val();
		org.endDate = $("#endDateC").val();
		org.assignedTo = $("#assignedToC").val();
		org.duration = $("#durationC").val();
		org.predecessors = $("#predecessorsC").val();
		org.notes = $("#notesC").val();
		console.log(JSON.stringify(org));
		console.log('org', org);

		$.ajax({
			type: "POST",
			url: "planning-scheduling-childdata",
			contentType: "application/json",
			data: JSON.stringify(org),
			success: function(response) {
				if (response.message == "Success") {
					$("#myModal").modal("hide");
					$("#myModalSelected").modal("hide");
					//$('.loader').hide();
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					getplnschdtls(id1);


				}
			}

		});
	}
	else {
		
		var name = selectedRows[0].projectName;
		var pId = selectedRows[0].projectId
		alert("in else" +pId)
		
		$("#tbodyData12").empty();
		$("#myModal").modal("hide");
		$("#myModalSelected").modal("hide");
		x.forEach((el, i) => {
			el.priority = $("#priority").val();
			el.startDate = $("#startDate").val();
			el.endDate = $("#endDate").val();
			el.assignedTo = $("#assignedTo").val();
			el.duration = $("#duration").val();
			el.predecessors = $("#predecessors").val();
			el.notes = $("#notes").val();
		})
		console.log(JSON.stringify(x));
		var obj = {};
		obj.planschid = planid;
		obj.projectId = id;
		obj.taskmodel = x;
		$.ajax({
			type: "POST",
			url: "planning-scheduling-datasavechild",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					//$('.loader').hide();
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					getplnschdtls(id1);


				}
			}

		});
	}
}