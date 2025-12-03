
let filteredId = 1;
$(() => {
	$('#ticketType').select2(); // #ticketType
	$('#tctCat').select2(); // #tctCat
	$('#ticketSubCategory').select2(); // #ticketSubCategory
	$('#assetList').select2(); // #assetList
	$('#ticketPriority').select2(); // #ticketPriority
	$('#ticketSource').select2(); // #ticketSource
	$('#locType').select2(); // #locType

	$('#departmentAction').select2(); // #departmentAction
	$('#ticketPriorityAction').select2(); // #ticketPriorityAction
	$('#vendorAction1').select2(); // #vendorAction1

});


$(document).ready(function() {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	filteredId = urlParams.get('id');
	$('#ratingSendBtn').hide();
	$('input[name="rating').on('click', function() {
		$('#ratingSendBtn').show();
	});
	$('#empName').select2({
		placeholder: "Select User",
		allowClear: true
	});
	$('#employeeAction').select2({
		placeholder: "Select",
		allowClear: true
	});
	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			onQuickFilterChanged()
		}
	});

	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendarAction").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: new Date().toLocaleDateString(undefined, { year: 'numeric', month: '2-digit', day: '2-digit' }),
	}).on("change", function() {
		$('#dateAction').val($(this).val());
	})

	$('#dateAction').blur(function() {
		$("#DateCalendarAction").val($(this).val());
	})

	$("#toDateCalendarTimeAction").datetimepicker({
	    format: 'h:i A',
	    closeOnDateSelect: false,
	    timepicker: true,
	    datepicker: false,
	    step: 1,
	    formatTime: 'h:i A',
	    minTime: new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'}), // Set the current time as the minimum time
	}).on("change", function() {
	    $('#timeAction').val($(this).val());
	});


	$('#timeAction').blur(function() {
		$("#toDateCalendarTimeAction").val($(this).val());
	})

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridEqDiv2 = document.querySelector('#activityLog');
	new agGrid.Grid(gridEqDiv2, activityForViewLogDet);

	infoCancel();

	addAddress('');
	ShowAgGrid('All');
	openAssignEmployee(event, 'Employee');

});

const columnDefs = [
	{
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: 'Ticket',
		field: "tcktNo",
		width: 77
	},
	{
		headerName: 'User Name',
		field: "raisedby",
		width: 150,
	},
	{
		headerName: "Status",
		field: "isClosed",
		width: 90,
		cellRenderer: function(params) {
			if (params.data.isClosed === "OPEN") {
				return '<div style="color:#0642f5">OPEN</div>';
			} else if (params.data.isClosed === "ASSIGNED") {
				return '<div style="color:#c9b036">ASSIGNED</div>';
			} else if (params.data.isClosed === "IN-PROGRESS") {
				return '<div style="color:#3aa9a0">IN-PROGRESS</div>';
			} else if (params.data.isClosed === "CLOSE") {
				return '<div style="color:#bf05ff">CLOSED</div>';
			} else if (params.data.isClosed === "RE-OPEN") {
				return '<div style="color:#D2042D">RE-OPEN</div>';
			} else if (params.data.isClosed === "RE-ASSIGNED") {
				return '<div style="color:#ff8c00">RE-ASSIGNED</div>';
			} else {
				return '<div style="color:#a9a9a9">' + params.data.isClosed + '</div>';
			}


		}
	},
	{
		headerName: 'Source',
		field: "source",
	}, {
		headerName: "Date",
		field: "riseDate",
	}, {
		headerName: "Description",
		field: "desc",
		width: 300,
		valueGetter: params => {
			const descs = params.data.desc;
			return (descs && descs !== 'null') ? window.atob(descs) : ''; // Return decoded value or empty string
		}
	}, {
		headerName: "Created By",
		field: "createdBy",
		width: 150,
	}
];

// let the grid know which columns and what data to use
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: onSelectionChange,
	paginationAutoPageSize: true,
	pagination: true,
};
var ticketId = '';
function onSelectionChange() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	id = selectedData.map(node => node.tcktNo);
	currAssType = selectedData.map(node => node.currAssignType);
	currAssTypeStatus = selectedData.map(node => node.currAssignTypeStatus);
	assignid = selectedData.map(node => node.assignid);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	var tcktNoList = selectedData.map(node => node.tcktNo).join(",");
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	let output = tcktNoList.split(',').map(item => `" ${item}"`).join(',');
	//		data = selectedRows[0];
	ticketId = output;

	$('#inspectionBtn').attr("disabled", false);
	$('#actionBtn').attr("disabled", false);
	$('#modalTcktID').html(id);
	$('#modalTcktIDAction').html(id);
	if (rowCount > 0) {
		var adhr = $("#adhr").val();
		if (adhr != 'adhr') {
			$("#assignTabBtn").hide();
			$("#next1").hide();
			$("#next11").show();
			$("#prev2").hide();
			$("#prev22").show();
		} else {
			$("#assignTabBtn").show();
			$("#next1").show();
			$("#next11").hide();
			$("#prev2").show();
			$("#prev22").hide();
		}
		$("#historyTabBtn").show();
		$("#stsTabBtn").show();
		$("#chatTabBtn").show();
		$("#rateTabBtn").show();

		if (rowCount == 1) {
			editTicket(id);
			viewLogsForTicket(id);
			viewAssignedResult(id);
			feedbackFun();
			if (selectedData.map(node => node.isClosed) == 'CLOSE') {
				$('#rateTabBtn').show();
				$('#next5').show();
				$('#infoEdit').hide();
				$('#infoReopen').show();
			} else if (selectedData.map(node => node.isClosed) != 'OPEN') {
				$('#rateTabBtn').show();
				$('#next5').show();
				$('#infoEdit').hide();
				$('#infoReopen').hide();
			} else {
				travelPrev('1');
				$('#rateTabBtn').hide();
				$('#next5').hide();
				$('#infoEdit').show();
				$('#infoReopen').hide();
			}

			$("#tktNo1").html(tcktNoList);
			$("#tktNo2").html(tcktNoList);
			$("#tktNo3").html(tcktNoList);
			$("#tktNo4").html(tcktNoList);
			$("#tktNo5").html(tcktNoList);
		} else {
			$("#tktNo1").html(tcktNoList);
			$("#tktNo2").html(tcktNoList);
			$("#tktNo3").html(tcktNoList);
			$("#tktNo4").html(tcktNoList);
			$("#tktNo5").html(tcktNoList);
			cleanForm();
			$('#infoReopen').hide();
			$('#infoEdit').hide();
			activityForViewLogDet.api.setRowData("");
			var newchat = `<p class="text-center text-muted">No chat history available.</p>`;
			$("#chatDiv").html(newchat);
			loader.classList.add("d-none");
			$("#feedbackSendBtn").hide();
			$("#historyDiv").html("");
			feedbackFun();
			$("#dateAction").val("");
			$("#timeAction").val("");
			$("#ticketPriorityAction").val("");
			$("#vendorAction1").val("");
			$("#employeeAction").val("").trigger('change');
			$("#departmentAction").val("");
		}

		if (selectedData.map(node => node.inspectStatus) == 0) {
			$('#inspectionBtn').attr("disabled", false);
			$('#actionBtn').attr("disabled", false);
			$('#gotoBtn').attr("disabled", true);
		} else if (selectedData.map(node => node.inspectStatus) == 2) {
			$('#inspectionBtn').attr("disabled", true);
			$('#actionBtn').attr("disabled", true);
			$('#gotoBtn').attr("disabled", false);
		} else {
			$('#inspectionBtn').attr("disabled", true);
			$('#actionBtn').attr("disabled", true);
			$('#gotoBtn').attr("disabled", true);

		}

		if (selectedData[0].isClosed == "CLOSE") {
			$('#closeTicketBtn').attr("disabled", true);
			$('#gotoBtn').attr("disabled", true);
		} else {
			$('#closeTicketBtn').attr("disabled", false);
		}

		$('#feedbackBtn').attr('disabled', false);
		$('#modalTcktID').html(id);
		$('#modalTcktIDAction').html(id);
	} else {
		travelPrev('1');
		$("#next1").hide();
		$("#next11").hide();
		$("#assignTabBtn").hide();
		$("#historyTabBtn").hide();
		$("#stsTabBtn").hide();
		$("#chatTabBtn").hide();
		$("#rateTabBtn").hide();
		$("#tktNo1").html(tcktNoList);
		cleanForm();
		$('#infoEdit').hide();
		$('#infoReopen').hide();
		activityForViewLogDet.api.setRowData("");
		var newchat = `<p class="text-center text-muted">No chat history available.</p>`;
		$("#chatDiv").html(newchat);
		loader.classList.add("d-none");
		$("#feedbackSendBtn").hide();
		$("#historyDiv").html("");
		feedbackFun();
		$("#dateAction").val("");
		$("#timeAction").val("");
		$("#ticketPriorityAction").val("");
		$("#vendorAction1").val("");
		$("#employeeAction").val("").trigger('change');
		$("#departmentAction").val("");
		$("#empName").val("").trigger('change');
	}
}
var activityForViewLog = [
	{
		headerName: "Date and Time",
		field: "created_on",
	}, {
		headerName: "Ticket ID",
		field: "ticket_id",
		hide: true
	}, {
		headerName: "Owner",
		field: "assign_to",
		width: 190
	}, {
		headerName: "Activity",
		field: "assign_type",

	}, {
		headerName: "Status",
		field: "assign_status",
		hide: true
	}, {
		headerName: "Status",
		field: "open_status",

	}];


//let the grid know which columns and what data to use product table
var activityForViewLogDet = {
	columnDefs: activityForViewLog,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	}
};

function viewLogsForTicket(tckid) {
	var div = '';
	agGrid.simpleHttpRequest(
		{
			url: 'view-department-view-tk-ticket-history?id=' + tckid
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			if (jsonData != null) {
				activityForViewLogDet.api.setRowData(jsonData);
				setHistoryView(jsonData);
			} else {
				activityForViewLogDet.api.setRowData("");
				$("#historyDiv").html("");
			}
		});

}
var pno = 1;
function ShowAgGrid(val) {
	if(val==='All'){
		$('#infoNew').show();
	}else{
		$('#infoNew').hide();
	}
	$('#pagination').show();
	var pages;
	var pageno = pno;
	priorityVal = val;
	var activity = 'Action';
	$('.loader').show();
	agGrid.simpleHttpRequest({
		url: "view-department-view-tk-prioritywise-view?id=" + filteredId + "&pageno=" + pageno + "&activity=" + activity,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body[0]);
		var allData = jsonData.viewTicket;
		console.log('allData===', allData)
		if (allData != "" && allData != null && allData != 'null') {
			var len = allData.length;
			$('#totalReq').find('span').html(len);

			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;
		} else {
			infoNew();
			infoEdit();
			$("#assignTabBtn").hide();
			$("#historyTabBtn").hide();
			$("#stsTabBtn").hide();
			$("#chatTabBtn").hide();
			$("#rateTabBtn").hide();
			$('#pagination').hide();
			pages = 0;
		}
		$(".loader").hide();
		if (filteredId !== 'all') {
			let filteredData;
			if (filteredId.toLowerCase() === "assigned") {
				// Include both "ASSIGNED" and "RE-ASSIGNED"
				filteredData = allData.filter(f => f.isClosed === "ASSIGNED" || f.isClosed === "RE-ASSIGNED");
			} else {
				// Normal filtering for other statuses
				filteredData = allData.filter(f => f.isClosed === filteredId.toUpperCase());
			}
			gridOptions.api.setRowData(filteredData);
		} else {
			gridOptions.api.setRowData(allData);
		}
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		} else {
			onSelectionChange();
		}
		$('#inspectionBtn').attr("disabled", true);
		$('#closeTicketBtn').attr("disabled", true);
		$('#actionBtn').attr("disabled", true);
		$('#gotoBtn').attr("disabled", true);
		$('#feedbackBtn').attr('disabled', true);
	});

}

//Pagination.
var pages;
function createPagination(pages, page) {
	pno = page;
	if ($("#totalPageno").val() == '') {
		var pages = 20;
	} else {
		var pages = $("#totalPageno").val();
		$("#currentPageno").val(page);
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
	//  $('.loader').show(); 
	pno = page;
	var pageno = page;
	var activity = sessionStorage.getItem('actName');

	agGrid.simpleHttpRequest({
		url: "view-agentTicket-prioritywise-view?id=" + priorityVal + "&pageno=" + pageno + "&activity=" + activity,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body[0]);
		var allData = jsonData.viewTicket;

		if (allData != "" && allData != null && allData != 'null') {
			var len = allData.length;
			$('#totalReq').find('span').html(len);

			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;
		}
		gridOptions.api.setRowData(allData);
		$(".loader").hide();
	});
}

function getEmpByDeptAction(selectedValue, value = null) {

	$.ajax({
		type: "GET",
		url: "view-department-view-tk-getemployee?deptid=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
				$("#employeeAction").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#employeeAction").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code", response.body[i].code);
					$("#employeeAction").append(option);
				}
				if (value) {
					$("#employeeAction").val(value).trigger('change');
				}
			}
		},
		error: function(e) {
		}
	});
}
function getCategory(cat) {
	$("#tctCat").empty();
	$("#ticketSubCategory").empty();
	var selectedValue = $("#ticketType").val();
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "view-department-view-tk-getcategory?id=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#tctCat").append(option);
				//$("#ticketSubCategory").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code", response.body[i].code);
					//$("#tctCat option:selected").attr("code");
					$("#tctCat").append(option);

				}
				$(".loader").hide();
				if (cat != '') {
					$("#tctCat").val(cat);
				}
			}
		},
		error: function(e) {
			$(".loader").hide();
		}
	});

}
function getSubCategory(cat, scat) {
	if (cat == '') {
		var selectedValue = $("#tctCat").val();
	} else {
		selectedValue = cat;
	}
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "view-department-view-tk-getSubcategory?id=" + selectedValue,
		success: function(response) {
			if (response.code == "success") {
				$("#ticketSubCategory").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#ticketSubCategory").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);

					$("#ticketSubCategory").append(option);
				}
				$(".loader").hide();
				if (scat != '') {
					$("#ticketSubCategory").val(scat);
				}
			}
		},
		error: function(e) {
			$(".loader").hide();
		}
	});
}
function getAssetList() {
	var scat = $("#ticketSubCategory").val();
	if (scat == 'TSCAT005' || scat == 'TSCAT006') {
		$("#assetlistfield").show();
		$("#assetList").val('');
	} else {
		$("#assetlistfield").hide();
	}

}
function editTicket(ticketId) {
	$(".formValidation").remove();
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: 'view-department-view-tk-edit?id=' + ticketId
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.editTicket;
		$("#startPagebtn").hide();
		$("#raiseTcktSession").show();
		if (allData[0].isClosed == "NOT ASSIGNED") {
			$('#save').attr("disabled", false);
		} else {
			$('#save').attr("disabled", true);
		}

		$('#docTbl').on('click', '.rmv1', function() {
			openDeleteConfirm();
			var value = $(this).parent("div").attr("id");

			$("#dltValue").val(value);
		});
		$("#doctbodyData").append(tbl);

		$("#tktNo").html(ticketId);
		//	$("#pagination").hide();
		$("#editTktId").val(allData[0].ticketId);
		$("#empId").val(allData[0].empId);
		$("#currentDate").val(allData[0].tktDate);
		$("#empName").val(allData[0].empName).trigger('change');;

		$("#ticketType").val(allData[0].tktType);

		feedbackRateFun(allData[0].ticketRating);
		getCategory(allData[0].tktCategory);
		getSubCategory(allData[0].tktCategory, allData[0].tktSubCategory);
		$("#ticketPriority").val(allData[0].tktPriority);
		$("#ticketSource").val(allData[0].ticketSource);
		$("#description").val(window.atob(allData[0].tktDesc));
		$("#locType").val(allData[0].tktLocType);
		addAddress(allData[0].tktLocType);
		$("#latitude").val(allData[0].tktLatitude);
		$("#longitude").val(allData[0].tktLongitude);
		$("#empAddress").val(allData[0].tktAddress);
		$("#assetlocation").val(allData[0].tktAddress);
		$("#assetList").val(allData[0].assetid);
		getAssetList();
		if (allData[0].assetid != null && allData[0].assetid != '') {
			$("#assetlistfield").show();
			$("#locationDiv").show();
		}

		$("#doctbodyData").empty();
		var docUrl;
		var documentList = allData[0].document;
		if (documentList != null && documentList != "") {
			for (var i = 0; i < documentList.length; i++) {
				var file = documentList[i].fileName.split('/').pop();
				var extension = documentList[i].fileName.split('.').pop();
					if (extension == "jpg" || extension == "png"
						|| extension == "jpeg") {
						var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + documentList[i].docurl + "' title='" + file + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
					} else if (extension == "pdf") {
						var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + documentList[i].docurl + "' title='" + file + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
					} else {
						var LightImg = "<div class='uploadicon position-l'> </div>";
					}
				var tbl = '<tr>'
					+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclss" id="docnoid_' + i + '"> </div></td>'
					+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-pencil" id="clickImg_' + i + '"></i> </label>'
					+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
					+ i
					+ '"name="userImage" onchange="saveMultiFile(event)" /> </div>'
					+ '</div> <input type="hidden" id="uploadHidden_' + i + '" value="' + documentList[i].docurl + '" class="uploadHidCls">'
					+ '<div id="uploadedBillDiv_' + i + '" align="center" class="uploadedBillCls"><div class="uploadicon position-l">'
					+ LightImg
					+ '</div></div>'
					+ '<div id="imageName_' + i + '" class="imageName">'
					+ documentList[i].fileName
					+ '</div>'
					+ '<input type="hidden" id="editId_' + i + '" value="' + documentList[i].ticketId + '">'
					+ '<div id="dltImage_' + i + '" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick=""></i></div> </td>'
					+ '</tr>';

				$("#doctbodyData").append(tbl);
			}
		} else {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
				+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}

		$('#infoNew').show();
		$('#infoSave').hide();
		$('#infoCancel').hide();
		$('.custom-file-upload').hide();
		$('.custom-file-delete').hide();
		$('#currentDate, #empName, #ticketType, #tctCat, #ticketSubCategory, #assetList, #ticketPriority, #ticketSource, #description, #locType, #latitude,  #longitude, #assetlocation, #empAddress, #saveAttachmentBtn, .docNoclss').attr('disabled', true);
	});
}

function addAddress(value) {

	var dropdown;
	if (value == "" || value == null) {
		dropdown = $("#locType").val();
	} else {
		dropdown = value;
	}

	switch (dropdown) {
		case "geoTag":
			$("#latitudeAdd").show();
			$("#longitudeAdd").show();
			$("#add").show();
			$("#loclist").hide();
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(showPosition);//here "showPosition" is a function which is declared outside this document.ready
			} else {

			}
			break;

		case "OTHER":
			$("#add").show();
			$("#latitudeAdd").hide();
			$("#longitudeAdd").hide();
			var isReadOnly = false;
			$("#empAddress").prop("readonly", isReadOnly);
			$("#empAddress").val("");
			$("#loclist").hide();
			break;

		case "Property":
			$("#empAddress").val("");
			$("#latitudeAdd").val("");
			$("#longitudeAdd").val("");
			$("#latitudeAdd").hide();
			$("#longitudeAdd").hide();
			$("#loclist").show();
			$("#add").hide();
			break;

		case "":
			$("#empAddress").val("");
			$("#latitudeAdd").val("");
			$("#longitudeAdd").val("");
			$("#latitudeAdd").hide();
			$("#longitudeAdd").hide();
			$("#add").hide();
			$("#loclist").hide();
			break;
	}
}

//show position
function showPosition(position) {
	$("#latitude").val("");
	$("#longitude").val("");
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	$("#latitude").val(latitude);
	$("#longitude").val(longitude);

	var locAPI = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
		+ latitude
		+ ","
		+ longitude
		+ "&key=AIzaSyD-o-8txzrqCvKZaf35i-zILm2ooG851uE";

	$("#latitude").val(latitude);
	var lat1 = $("#latitude").val();
	$("#longitude").val(longitude);
	var lon1 = $("#longitude").val();
	var lat2 = $("#latitude2").val();
	var lon2 = $("#longitude2").val();

	if (lon2 == "" || lat2 == "") {
		$.get({
			url: locAPI,
			success: function(data) {
				console.log("api data----",data);
				var loc = $("#location1").val();
				var city = loc.split(',');
				var plc = data.results[0].formatted_address;
				var isReadOnly = true;
				$("#empAddress").prop("readonly", isReadOnly);
				$("#empAddress").val(plc);

			}

		});
	} else {

		$.get({
			url: locAPI,
			success: function(data) {
				var place = data.results[0].formatted_address;
				var isReadOnly = true;
				$("#empAddress").prop("readonly", isReadOnly);
				$("#empAddress").val(place);
			}

		});
	}
}

var type = "Employee";
function openAssignEmployee(e, val) {
	if (val === 'Employee') {
		$("#assignEmp").addClass("active");
		$("#assignVen").removeClass("active");
		$("#vendorid").hide();
		$("#departmentid").show();
		$("#employeeid").show();
		e?.preventDefault();
		type = "Employee";
	} else if (val === 'Vendor') {
		$("#assignVen").addClass("active");
		$("#assignEmp").removeClass("active");
		$("#departmentid").hide();
		$("#employeeid").hide();
		$("#vendorid").show();
		type = "Vendor";
		e?.preventDefault();
	}


}
//checkEmpty()  


function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			toastr.error("Document Name Required", $(this).attr('id'));
			return;
		}
	});

	var mulDocInfo = true;
	if (infofileName == true) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error("Please Choose a File");
				return false;
			}

		});
	}
	if (infofileName == true && mulDocInfo == true) {
		addMore1();
	}
	event.preventDefault();
}
function checkEmptyMasterSave() {

	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			toastr.error("Document Name Required", $(this).attr('id'));
			return false;
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error("Please Choose a File");
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		return true
	}
}
//addmore doc fun for notice
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
//function to check duplicate entry of document name
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
		toastr.error("This document name has already been entered.");
		return false;
	} else {
		return true;
	}

}
function infoCancel() {
	$('#infoNew').show();
	$('#infoEdit').show();
	$('#infoSave').hide();
	$('#infoCancel').hide();
	$('#currentDate, #empName, #ticketType, #tctCat, #ticketSubCategory, #assetList, #ticketPriority, #ticketSource, #description, #locType, #latitude,  #longitude, #assetlocation, #empAddress, #saveAttachmentBtn, .docNoclss').attr('disabled', true);
	$('.custom-file-upload').hide();
	$('.custom-file-delete').hide();
}
function infoEdit() {
	$('#ticketType, #tctCat, #ticketSubCategory, #assetList, #ticketPriority, #ticketSource, #description, #locType, #latitude,  #longitude, #assetlocation, #empAddress, #saveAttachmentBtn, .docNoclss').attr('disabled', false);
	$('#infoSave').show();
	$('#infoCancel').show();
	$('#infoNew').hide();
	$('#infoEdit').hide();
	$('.custom-file-upload').show();
	$('.custom-file-delete').show();
}
async function infoNew() {
	gridOptions.api.deselectAll();
	await new Promise(resolve => setTimeout(resolve, 100));
	$("#assignTabBtn").hide();
	$("#historyTabBtn").hide();
	$("#stsTabBtn").hide();
	$("#chatTabBtn").hide();
	$("#rateTabBtn").hide();
	$("#next1").hide();
	$("#empName").val("").trigger('change');
	var emppId = $("#sessionEId").val();
	var adhr = $("#adhr").val();
	if (adhr != 'adhr') {
		$("#empName").val(emppId).trigger('change');
		$('#empName').attr("disabled", true);
	} else {
		$('#empName').attr("disabled", false);
	}
}
function cleanForm() {

	$("#assetlistfield").hide();
	$("#locationDiv").show();
	$("#editTktId").val('');

	$("#empId").val('');
	$("#currentDate").val('');
	$("#dept").val('');

	$("#ticketType").val('');
	$("#ticketCategory").val('');
	$("#ticketSubCategory").val('');
	$("#tctCat").empty();
	$("#ticketSubCategory").empty();
	$("#ticketPriority").val('');
	$("#ticketSource").val('');

	$("#tctCat").val('');
	$("#assetList").val('');
	$("#ticketSubCategory").val('');
	$("#ticketPriority").val('');
	$("#ticketSource").val('');

	$("#description").val('');
	$("#locType").val('');
	$("#latitude").val('');
	$("#longitude").val('');
	$("#empAddress").val('');
	$("#assetlocation").val('');
	$("#doctbodyData").html("");
	$("#tktNo").html("");
	addAddress('');

	const currentDate = new Date();
	const day = String(currentDate.getDate()).padStart(2, '0');       // Adds leading zero if needed
	const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
	const year = currentDate.getFullYear();

	// Format the date as DD-MM-YYYY
	const formattedDate = `${day}-${month}-${year}`;
	$("#currentDate").val(formattedDate);

	$('#docTbl').on('click', '.rmv1', function() {
		openDeleteConfirm();
		var value = $(this).parent("div").attr("id");

		$("#dltValue").val(value);
	});
	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
		+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").append(tbl);
}
function getDateTime() {
	var currentdate = new Date();

	month = '' + (currentdate.getMonth() + 1);
	day = '' + currentdate.getDate();
	year = currentdate.getFullYear();

	hour = '' + currentdate.getHours();
	minute = '' + currentdate.getMinutes();
	second = currentdate.getSeconds();

	if (month.length < 2) {
		month = '0' + month;
	}
	if (day.length < 2) {
		day = '0' + day;
	}
	if (hour.length < 2) {
		day = '0' + hour;
	}
	if (minute.length < 2) {
		day = '0' + minute;
	}
	if (second.length < 2) {
		day = '0' + second;
	}
	var datetime = day + "-" + month + "-" + year + " ";

	return datetime
}
function infoSave() {
	var obj = {};
	var valid = true;
	var uploadList = [];
	var datas = [];
	if (valid) {
		$("#doctbodyData > tr").each(
			function(i) {
				var uFile = $(this).find(".document")[0].files[0];
				var fileName = $(this).find(".document").val();
				var fileNametxt = $(this).find('.imageName').text();
				var data = [];
				var x = [];
				if (fileNametxt != '' && fileNametxt != 'undefined' && fileNametxt != null) {
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
					}
					uploadData = {};
					uploadData['documnentName'] = $("#docnoid_" + i).val();
					uploadData['documentFile'] = x;
					uploadData['fileName'] = $(this).find('.imageName').text();
					uploadData['documentURL'] = $(this).find(".uploadHidCls").val();
					uploadData['imageNameEdit'] = $(this).find(
						".uploadHidCls").val();
					uploadList.push(uploadData);
				}
				if (uploadList.length > 0) {
					valid = checkEmptyMasterSave();
				}
			});


		obj.ticketId = $("#editTktId").val();
		obj.empId = $("#empId").val();
		obj.date = $("#currentDate").val();
		obj.empName = $("#empName").val();
		obj.dept = $("#tctCat option:selected").attr("data-code");
		obj.ticketType = $("#ticketType").val();
		obj.ticketCategory = $('#tctCat').val();
		obj.ticketSubCategory = $("#ticketSubCategory").val();
		obj.assetList = $("#assetList").val();
		obj.ticketPriority = $("#ticketPriority").val();
		obj.ticketSource = $("#ticketSource").val();
		obj.description = window.btoa($("#description").val());
		obj.locType = $("#locType").val();
		obj.latitude = $("#latitude").val();
		obj.longitude = $("#longitude").val();
		obj.documentList = uploadList;
		if (obj.empName == null || obj.empName == "") {
			toastr.error('User Name Required');
			return;
		}
		if (obj.ticketType == null || obj.ticketType == "") {
			toastr.error('Ticket Type Required');
			return;
		}
		if (obj.ticketCategory == null || obj.ticketCategory == "") {
			toastr.error('Category Required');
			return;
		}
		if (obj.ticketSubCategory == null || obj.ticketSubCategory == "") {
			toastr.error('SubCategory Required');
			return;
		}
		if (obj.ticketSubCategory == 'TSCAT005' || obj.ticketSubCategory == 'TSCAT006') {
			if (obj.assetList == null || obj.assetList == "") {
				toastr.error('Asset Required');
				return;
			}
		}
		if (obj.ticketPriority == null || obj.ticketPriority == "") {
			toastr.error('Priority Required');
			return;
		}
		if (obj.ticketSource == null || obj.ticketSource == "") {
			toastr.error('Source Required');
			return;
		}
		if (obj.description == null || obj.description == "") {
			toastr.error('Description Required');
			return;
		}
		if (obj.locType == null || obj.locType == "") {
			toastr.error('Location Required');
			return;
		}

		if (obj.locType == 'Property') {
			obj.address = $("#assetlocation").val();
			obj.empAddress = $("#assetlocation").val();
			if ($("#assetlocation").val() == null || $("#assetlocation").val() == "") {
				toastr.error('Property Location Required');
				return;
			}
		} else {
			obj.address = $("#empAddress").val();
			obj.empAddress = $("#empAddress").val();
			if (obj.address == null || obj.address == "") {
				toastr.error('Address Required');
				return;
			}
		}

		datas.push(obj);
		if (valid) {
			setTimeout(function() {
				saveTicket(datas);
			}, 1000)
		}
	}

}
function getActiveNav() {
	const activeNav = document.querySelector('#viewNav .nav-link.active'); // Get the active nav link
	if (activeNav && activeNav.onclick) {
		activeNav.onclick(); // Trigger its onclick function
	} else {

	}
}
function saveTicket(data) {
	console.log("datadata====", data)
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-department-view-tk-save-data",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				$('.loader').hide();
				$("body").removeClass("overlay");
				ShowAgGrid('All');
				getActiveNav();
				onSelectionChange();

			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something Went Wrong');
			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#messageParagraph").text("Something Went Wrong");
			$("#msgOkModal").removeClass("btn1");
			$("#msgOkModal").addClass("btn3");
			$("#msgModal").modal('show');
		}
	})
}
function openDeleteConfirm() {
	$("#dltValue").val("");
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete this attachment?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			deleteAttachmentRow();
		}
	});
}

function deleteAttachmentRow() {
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
			+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
			+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}
//Document upload section 

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

function assignSave() {
	var obj = {};
	var valid = true;

	if (valid) {

		obj.assignType = '3';
		obj.ticketId = $("#tktNo1").html();
		obj.date = $("#dateAction").val();
		obj.time = $("#timeAction").val();
		obj.dept = $("#departmentAction").val();
		obj.ticketPriority = $("#ticketPriorityAction").val();

		if (obj.date == null || obj.date == "") {
			toastr.error('Date Required');
			return;
		}
		if (obj.time == null || obj.time == "") {
			toastr.error('Time Required');
			return;
		}
		if (type == "Employee") {
			obj.empId = $("#employeeAction").val();
			obj.dept = $("#departmentAction").val();
			obj.type = "Employee";
			if (obj.dept == null || obj.dept == "") {
				toastr.error('Department Required');
				return;
			}
			if (obj.empId == null || obj.empId == "") {
				toastr.error('Employee Required');
				return;
			}

		} else if (type == "Vendor") {
			obj.empId = $("#vendorAction1").val();
			obj.dept = "";
			obj.type = "Vendor";
			if (obj.empId == null || obj.empId == "") {
				toastr.error('Vendor Required');
				return;
			}
		}
	}

	if (obj.ticketPriority == null || obj.ticketPriority == "") {
		toastr.error('Priority Required');
		return;
	}

	if (valid) {
		saveTicketAction(obj, obj.ticketId);
	}
}



function saveTicketAction(data, id) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-department-view-tk-save-action-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.code == "success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success('Ticket Assigned Successfully');
				ShowAgGrid('All');
				onSelectionChange();
				viewAssignedResult(id);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.error('Something Went Wrong');

			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#messageParagraph").text("Something Went Wrong");
			$("#msgOkModal").removeClass("btn1");
			$("#msgOkModal").addClass("btn3");
			$("#msgModal").modal('show');
			$("#actionModal").modal('hide');
		}
	})
}

function viewAssignedResult(id = null) {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var st = selectedData.map(node => node.isClosed);
	var div = '';
	if (id) {
		agGrid.simpleHttpRequest(
			{
				url: 'view-department-view-tk-assigned-result?id=' + id
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				console.log("assign data====", jsonData)
				if (jsonData == null) {
					$("#dateAction").val("");
					$("#timeAction").val("");
					$("#ticketPriorityAction").val("");
					$("#vendorAction1").val("");
					$("#employeeAction").val("").trigger('change');
					$("#departmentAction").val("");
					infoAssignCancel('');
					openAssignEmployee(event, 'Employee');
					$('#infoAssign').show();
					$('#infoAssignEdit').hide();
				} else {
					infoAssignCancel('');
    					var lastAssigned = jsonData.filter(item => item.dept_id !== null).pop();
    					var depta=lastAssigned ? lastAssigned.dept_id : null;
    					var empa=lastAssigned ? lastAssigned.assignedId : null;
    					if(depta){
								$("#departmentAction").val(depta);
								getEmpByDeptAction(depta,empa);
							}
    					
					for (i = 0; i < jsonData.length; i++) {
						var allData = jsonData[i];
						if ((allData.assign_type === 'ASSIGN') ||
							(allData.assign_type == 'MAINTAIN' && allData.open_status == 'REOPEN')) {
							if (allData.dept_id) {
								$("#assignEmp").addClass("active");
								$("#assignVen").removeClass("active");
								openAssignEmployee(event, 'Employee');
								$("#departmentAction").val(allData.dept_id);
								getEmpByDeptAction(allData.dept_id, allData.assignedId);
							} else {
								$("#assignEmp").removeClass("active");
								$("#assignVen").addClass("active");
								openAssignEmployee(event, 'Vendor');
								$("#vendorAction1").val(allData.assignedId);
							}
							$("#dateAction").val(allData.date);
							$("#timeAction").val(allData.time);
							$("#ticketPriorityAction").val(allData.priority);
							$('#assignSave').hide();
							$('#infoAssign').hide();
							if (st == 'ASSIGNED' || st == 'RE-ASSIGNED' || st == 'RE-OPEN') {
								$('#infoAssignEdit').show();
							} else {
								$('#infoAssignEdit').hide();
							}

						} else {
							if (st == 'OPEN') {
								$('#infoAssign').show();
							} else {
								$('#infoAssign').hide();
							}
							if (st == 'ASSIGNED' || st == 'RE-ASSIGNED' || st == 'RE-OPEN') {
								$('#infoAssignEdit').show();
							} else {
								$('#infoAssignEdit').hide();
							}
							$('#infoAssignCancel').hide();
							$('#assignSave').hide();
						}
						if(i==jsonData.length){
							
						}
					}
				}

			});
	} else {
		$('#infoAssignCancel').hide();
		$('#assignSave').hide();
		$('#infoAssign').show();
		$('#infoAssignEdit').hide();
		infoAssignCancel('');
		openAssignEmployee(event, '');
	}
}
function infoAssignEdit() {
	$('#dateAction, #timeAction, #departmentAction, #employeeAction, #ticketPriorityAction, #vendorAction1').attr('disabled', false);
	$('#DateCalendarAction').show();
	$('#toDateCalendarTimeAction').show();
	$('#infoAssignCancel').show();
	$('#assignSave').show();
	$('#infoAssignEdit').hide();
	$("#dateAction").val("");
	$("#timeAction").val("");
	$("#ticketPriorityAction").val("");
	$("#vendorAction1").val("");
	$("#employeeAction").val("").trigger('change');
	$("#departmentAction").val("");
	$('#infoAssign').hide();
	$('#assignEmp').show();
	$('#assignVen').show();
	const currentDate = new Date();
	const day = String(currentDate.getDate()).padStart(2, '0');       // Adds leading zero if needed
	const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
	const year = currentDate.getFullYear();

	// Format the date as DD-MM-YYYY
	const formattedDate = `${day}-${month}-${year}`;
	$("#dateAction").val(formattedDate);
}
function infoAssignCancel(id) {
	$('#dateAction, #timeAction, #departmentAction, #employeeAction, #ticketPriorityAction, #vendorAction1').attr('disabled', true);
	$('#assignSave').hide();
	$('#infoAssignCancel').hide();
	$('#DateCalendarAction').hide();
	$('#toDateCalendarTimeAction').hide();
	$("#dateAction").val("");
	$("#timeAction").val("");
	$("#ticketPriorityAction").val("");
	$("#vendorAction1").val("");
	$("#employeeAction").val("").trigger('change');
	$("#departmentAction").val("");
	$('#assignEmp').hide();
	$('#assignVen').hide();

	if (id == 'c') {
		viewAssignedResult($("#tktNo1").html());
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var st = selectedData.map(node => node.isClosed);
		if (st == 'OPEN') {
			$('#infoAssign').show();
		} else {
			$('#infoAssign').hide();
		}
		if (st == 'ASSIGNED' || st == 'RE-ASSIGNED' || st == 'RE-OPEN') {
			$('#infoAssignEdit').show();
		} else {
			$('#infoAssignEdit').hide();
		}
	}
}

function checkEmptyAssign() {
	$("#empAddress").val($("#assetlocation option:selected").text());
}
function sendFeedback() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var id = selectedData.map(node => node.tcktNo);
	var obj = {};
	var valid = true;
	var data = [];

	obj.ticketId = id[0];
	obj.resultDescription = $("#ticketFeedback").val();
	var sessionEmpId = $("#sessionEId").val();
	var emppId = $("#empName").val();
	if (emppId == sessionEmpId) {
		obj.ticketType = "User";
	} else {
		obj.ticketType = "Admin";
	}

	if (obj.feedback == null || obj.feedback == "") {
		valid = validationUpdated("", "ticketFeedback");
	}
	data.push(obj);
	if (valid) {
		loader.classList.remove("d-none");
		$("#feedbackSendBtn").hide();
		$('.loader').show();
		setTimeout(function() {
			$.ajax({
				type: "POST",
				url: "view-department-view-tk-feedback-save",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(response) {
					if (response.code == "success") {
						$('.loader').hide();
						var newchat = "";
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.Chat;
						allData.forEach(function(rowNode, index) {
							if (rowNode.type == "Admin") {
								newchat += `<div class="d-flex flex-row justify-content-end">
			                    <div>
			                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
			                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
			                    </div>
			                    <img src="../assets/img/User.webp"
			                      alt="avatar 1" style="width: 45px; height: 100%; border-radius:50%;">
			                  </div>`;
							} else {
								newchat += `<div class="d-flex flex-row justify-content-start">
		                    <img src="../assets/img/customer.webp"
		                      alt="avatar 1" style="width: 45px; height: 100%; border-radius:50%;">
		                    <div>
		                      <p class="small p-2 ms-3 mb-1 rounded-3 font-weight-bold params-desc" style="background-color: #f5f6f7;">${rowNode.desc}</p>
		                      <p class="small ms-3 mb-3 rounded-3 text-muted float-end">${rowNode.date}</p>
		                    </div>
		                  </div>`;
							}
						});
						$("#ticketFeedback").val("");
						$("#chatDiv").html(newchat);
						loader.classList.add("d-none");
						$("#feedbackSendBtn").show();
					} else {
						$('.loader').hide();
						var newchat = `<div class="d-flex flex-row justify-content-end">
	                    <div>
	                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own">${obj.resultDescription}</p>
	                      <p class="small me-3 mb-3 rounded-3 text-danger">Failed to sent</p>
	                    </div>
	                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
	                      alt="avatar 1" style="width: 45px; height: 100%;">
	                  </div>`;
						$("#ticketFeedback").val("");
						$("#chatDiv").append(newchat);
						loader.classList.add("d-none");
						$("#feedbackSendBtn").show();
					}
				},
				error: function(response) {
					loader.classList.add("d-none");
					$("#feedbackSendBtn").show();
					$("body").removeClass("overlay");
					toastr.error('Something Went Wrong');
					return;
				}
			})
		}, 1000);
	}
}

function feedbackFun() {
	loader.classList.remove("d-none");
	$("#feedbackSendBtn").hide();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var id = selectedData.map(node => node.tcktNo);
	var newchat = "";
	var len = id.length;
	if (len > 0) {
		agGrid.simpleHttpRequest(
			{
				url: 'view-department-view-tk-chat-view?id=' + id[0] + "&type=" + "Admin"
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.Chat;
				if (allData != null) {
					allData.forEach(function(rowNode, index) {
						if (rowNode.type == "Admin") {
							newchat += `<div class="d-flex flex-row justify-content-end">
				                    <div>
				                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
				                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
				                    </div>
				                    <img src="../assets/img/customer.webp"
				                      alt="avatar 1" style="width: 45px; height: 100%; border-radius:50%;">
				                  </div>`;
						} else {
							newchat += `<div class="d-flex flex-row justify-content-start">
			                    <img src="../assets/img/user.webp"
			                      alt="avatar 1" style="width: 45px; height: 100%; border-radius:50%;">
			                    <div>
			                      <p class="small p-2 ms-3 mb-1 rounded-3 font-weight-bold params-desc" style="background-color: #f5f6f7;">${rowNode.desc}</p>
			                      <p class="small ms-3 mb-3 rounded-3 text-muted float-end">${rowNode.date}</p>
			                    </div>
			                  </div>`;
						}
					});
				} else {
					// If allData is null or undefined
					newchat = `<p class="text-center text-muted">No chat history available.</p>`;
				}
				$("#chatDiv").html(newchat);
				loader.classList.add("d-none");
				$("#feedbackSendBtn").show();
			});
	} else {
	}
}

function sendRateFun() {
	var checkedCount = $('input[name="rating"]:checked').val();
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].tcktNo;
	if (id) {
		$.ajax({
			type: "GET",
			url: "view-department-view-tk-rate-save?id=" + id + "&rate=" + checkedCount,
			success: function(response) {
				if (response.code == "success") {
					$('#feedbackRateModal').modal('hide');
					toastr.success("Thank you for submitting your rating!");
					$('#reqDltBtn').attr('disabled', true);
					onSelectionChange();
				} else {
					$('#feedbackRateModal').modal('hide');
					toastr.error('Something went wrong');
				}
			},
			error: function(data) {
			}
		})
	}
}
function feedbackRateFun(rate) {
	const ratingInputs = document.querySelectorAll('input[name="rating"]');
	if (rate[0] > 0) {
		$('input[name="rating"]').val([rate[0]]);
		ratingInputs.forEach(input => input.disabled = true);
	} else {
		var eid =$("#sessionEId").val();
		if(eid==$("#empName").val()){
			$('input[name="rating"]').prop('checked', false);
			ratingInputs.forEach(input => input.disabled = false);		
		}else{
		$('input[name="rating"]').val([rate[0]]);
		ratingInputs.forEach(input => input.disabled = true);			
		}
	}
	$('#ratingSendBtn').hide();
}
function setHistoryView(data) {
	console.log(data);
	$("#historyDiv").html("");
	//	loader.classList.add("d-none");
	var newchat = '';
	data.forEach(function(rowNode, index) {
		newchat += `<div class="timeline-item">
		        <div class="timeline-circle">${index + 1}</div>
				 <div class="timeline-content">
		        <h2 class="tkt-title">Ticket ${rowNode.assign_type}, ${rowNode.created_on} </h2>
		          <div class="time-box d-flex justify-content-between align-items-center row">`;

		if (rowNode.assign_type == 'RAISED' && rowNode.open_status == 'NOT ASSIGNED') {
			newchat += `
	        		  <div class="timeln-txt">Raised By : ${rowNode.raised_by}</div>
						 <div class="timeln-txt">Details : ${rowNode.desc}</div>
	        	  `;
		} else if (rowNode.assign_type == 'ASSIGN' && rowNode.open_status == "OPEN") {
			newchat += `
		        		  <div class="timeln-txt">Assigned By : ${rowNode.assign_to}</div>
							 <div class="timeln-txt">Assigned Status : ${rowNode.curr_sts}</div>
		        	  `;
		} else if (rowNode.assign_type == 'CLOSED') {
			newchat += `
		        		  <div class="timeln-txt">Closed By : ${rowNode.assign_to}</div>
							 <div class="timeln-txt">Closed Status : ${rowNode.assign_status}</div>
		        	  `;
		}

		// add more         
		newchat += `</div>
				   <div class="time-box d-flex justify-content-between align-items-center time-lmr">
				      <div class="timeln-txt"> Ticket Status : ${rowNode.open_status}</div>  
				  </div>
		        </div>
		      </div>`;
	});

	$("#historyDiv").html(newchat);
}
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}

function deleteOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].tcktNo;
	if (id == null || id == '') {
		toastr.error('There is no ticket selected to delete');
		return;
	}
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete this?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			$.ajax({
				type: "GET",
				url: "view-department-view-tk-delete?id=" + id,
				success: function(response) {
					if (response.code == "success") {
						toastr.success('Ticket Deleted Sucessfully');
						ShowAgGrid('All');
					} else {
						toastr.error('Something Went Wrong');
					}
				}

			});
		}
	});
}
function reopenOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].tcktNo;
	if (id == null || id == '') {
		toastr.error('There is no ticket selected to delete');
		return;
	}
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to reopen this?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, reopen it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			$.ajax({
				type: "GET",
				url: "view-department-view-tk-reopen?id=" + id,
				success: function(response) {
					if (response.code == "success") {
						toastr.success('Ticket Reopen Sucessfully');
						ShowAgGrid('All');
					} else {
						toastr.error('Something Went Wrong');
					}
				}

			});
		}
	});
}
function travelNext(tab) {
	if (tab == 1) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").addClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").addClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 2) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").addClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").addClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 3) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").addClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").addClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 4) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").addClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").addClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 5) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").addClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").addClass('active');
	} else {
		$("#ticketTab").addClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").addClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	}
}
function travelPrev(tab) {
	if (tab == 1) {
		$("#ticketTab").addClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").addClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 2) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").addClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").addClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 3) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").addClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").addClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 4) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").addClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").addClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").removeClass('active');
	} else if (tab == 5) {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").addClass('active');
		$("#RateUs").removeClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").addClass('active');
		$("#rateTabBtn").removeClass('active');
	} else {
		$("#ticketTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#historyTab").removeClass('active');
		$("#stsTab").removeClass('active');
		$("#chatTab").removeClass('active');
		$("#RateUs").addClass('active');

		$("#ticketTabBtn").removeClass('active');
		$("#assignTabBtn").removeClass('active');
		$("#historyTabBtn").removeClass('active');
		$("#stsTabBtn").removeClass('active');
		$("#chatTabBtn").removeClass('active');
		$("#rateTabBtn").addClass('active');
	}
}
function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
}
function quickFilterFAQ() {
	let input = document.getElementById("quickFilterFAQ").value.toLowerCase();
	let accordionItems = document.querySelectorAll(".accordion-item");

	accordionItems.forEach(item => {
		let questionText = item.querySelector(".accordion-button").innerText.toLowerCase();

		if (questionText.includes(input)) {
			item.style.display = "block";
		} else {
			item.style.display = "none";
		}
	});
}
function resetFAQ() {
	$('#quickFilterFAQ').val("");
	quickFilterFAQ();
}