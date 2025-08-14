
const columnDefs = [
	{
		//headerCheckboxSelection : true,
		checkboxSelection: true,
		width: 20,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',

	},
	{
		headerName: 'Ticket No',
		field: "tcktNo",
		pinned: 'left',
		//cellStyle: {textAlign: 'center'},
		/*cellRenderer: function(params) {
			return '<a onclick=editTicket("' + params.data.tcktNo
				+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
				+ params.data.tcktNo + '</a>';
		}*/
	}, {
		headerName: "Priority",
		field: "ticketPriority",
		cellStyle: { textAlign: 'center' },

	}, {
		headerName: "Ticket Status",
		field: "isClosed",
		cellStyle: { textAlign: 'center' },
		cellRenderer: function(params) {
			if (params.data.isClosed == "OPEN") {
				return '<div style="color:#0642f5">OPEN</div>';
			} else if (params.data.isClosed == "CLOSE") {
				return '<div style="color:#D2042D">CLOSED</div>';
			} else if (params.data.isClosed == "HOLD") {
				return '<div style="color:orange">ON HOLD</div>';
			} else {
				return '<div style="color:#a9a9a9">NOT ASSIGNED</div>';
			}

		}


	}, {
		headerName: "Raised On",
		field: "riseDate",
		cellStyle: { textAlign: 'center' }

	}, {
		headerName: "Ticket Location",
		field: "raisedAddress",
		cellStyle: { textAlign: 'center' }

	}, {
		headerName: "Complaint Type",
		field: "tckttype",

	}, {
		headerName: "Complaint Category",
		field: "tcktCat",

	}, {
		headerName: "Complaint Sub-Category",
		field: "tcktSubCat",

	}, {
		headerName: "Description",
		field: "desc",
	}, {
		headerName: 'Asset ID',
		field: "assetid",
		hide:true,
		cellStyle: { textAlign: 'center' },
		cellRenderer: function(params) {
			if (params.data.assetid != null && params.data.assetid != '') {
				return '<a onclick=redirectToAsset("' + params.data.assetid
					+ '") href="javascript:void(0)">' + params.data.assetid + '<i class="bi bi-arrow-bar-right"></i></a>';
			} else {
				return 'NA'
			}

		}
	}, {
		headerName: 'Asset Name',
		field: "assetName",

	}, {
		headerName: "Department",
		field: "department",
	}, {
		headerName: "Raised By",
		field: "raisedby",
	}, {
		headerName: 'Service Rating',
		field: "Rating",
		cellStyle: { textAlign: 'center' },
		cellRenderer: function(params) {
			var star = "";
			if (params.data.isClosed == "CLOSE") {
				for (var i = 1; i <= params.data.Rating; i++) {
					star = star + '<i class="bi bi-star-fill" style="color: #FAD02C;"></i>';
				} return star;
			} else {
				return "NA";
			}

		}
	},
	{
		headerName: 'Attachment',
		field: "attachment",
		hide:true,
		cellStyle: { textAlign: 'center' },
		cellRenderer: function(params) {
			return '<a onclick=viewAttachment("' + params.data.tcktNo
				+ '") href="javascript:void(0)"><i class="bi bi-images"> View</i></a>';
		}
	}];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 157,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChanged,
};

var ticketId = "";
var apstatus = "";
function onSelectionChanged() {
	console.log("Selection change-->")
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	ticketId = selectedData.map(node => node.tcktNo);
	close = selectedData.map(node => node.isClosed);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});

	console.log("Selection ticketId-->", ticketId);

	if (rowCount > 0) {
		if (close[0] == "NOT ASSIGNED") {
			$('#reqDltBtn').attr("disabled", false);
			$('#feedbackRateBtn').attr('disabled', true);
			
			
			
			$("#ticket-editBtn").attr("disabled",false);
				$("#save-btn").attr("disabled",false);
				$("#delete-btn").attr("disabled",false);

		} else if (close[0] == "CLOSE") {
			$('#feedbackRateBtn').attr('disabled', false);
		} else {
			$('#reqDltBtn').attr("disabled", true);
			$('#feedbackRateBtn').attr('disabled', true);
		}
		$('#raiseTckt').attr('disabled', true);
		$('#feedbackBtn').attr('disabled', false);
		//$('#feedbackRateBtn').attr('disabled', false);
		var id = selectedRows[0].tcktNo;
		editTicket(id);
		feedbackFun();
		feedbackRateFun();

	} else {
		$('#reqDltBtn').attr("disabled", true);
		$('#assignBtn').attr("disabled", true);
		$('#raiseTckt').attr('disabled', false);
		$('#feedbackBtn').attr('disabled', true);
		$('#feedbackRateBtn').attr('disabled', true);
	/*	$("#ticket-editBtn").attr("disabled",true);
		$("#save-btn").attr("disabled",true);
		$("#delete-btn").attr("disabled",true);*/
	}
}


$(document).ready(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	//Intial Api TO Fetch Ticket Data
	viewAllTickets();

	//Automatically Select And Call The Starting Data
	setTimeout(function() {
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		console.log("First Row Node:", firstRowNode);
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Select the first row
			var selectedRows = gridOptions.api.getSelectedRows();
			if (selectedRows.length > 0) {
				var id = selectedRows[0].tcktNo;
				console.log("Scheduled ID of first row:", id);
				editTicket(id);
			}
		}
	}, 500);
	onSelectionChanged();

	//$("#empAddress,#longitude,#latitude,#assetlocation,#locType,#description,#ticketSource,#ticketPriority,#assetList,#ticketSubCategory,#tctCat,#ticketType,#dept,#empName,#currentDate").prop("readonly",true);

	$("#ticketType,#tctCat,#ticketSubCategory,#assetList,#ticketPriority,#ticketSource,#locType").attr('disabled', true);
	$(".max-btn").on("click", function () {
	      var parentCol = $(this).closest(".col-md-6, .col-md-12");

	      if (parentCol.hasClass("col-md-6")) {
	          parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
	          parentCol.siblings(".col-md-6").hide().fadeOut(500);
	      } else {
	          parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
	          parentCol.siblings(".col-md-6").show().fadeIn(500);
	      }
	  });
});


var pno = 1;
function viewAllTickets() {
	$("#pagination").show()
	var pages;
	var pageno = pno;

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-agentTicket-data-view?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewTicket;

		if (allData != "" && allData != null && allData != 'null') {
			var len = allData.length;
			$('#totalReq').find('span').html(len);

			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;
		} else {
			$("#pagination").hide()
		}
		gridOptions.api.setRowData(allData);
		createPagination(pages, pageno);
		onSelectionChanged();
		$(".loader").hide();

	});

}

//Paginantion
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
	$('.loader').show();
	pno = page;
	var pageno = page;
	agGrid.simpleHttpRequest({
		url: "view-agentTicket-data-view?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewTicket;
		gridOptions.api.setRowData(allData);
		onSelectionChanged();
		var totalRowCount = gridOptions.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);

		if (allData != "" && allData != null && allData != 'null') {
			var len = allData.length;
			$('#totalReq').find('span').html(len);

			$('#totalPageno').val(allData[0].totalPageno);
			pages = allData[0].totalPageno;
		}

	});
}



function editTicket(ticketId) {
	$(".formValidation").remove();
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: 'view-agentTicket-edit?id=' + ticketId
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.editTicket;
		$("#startPagebtn").hide();
		//$("#myGrid").hide();
		$("#raiseTcktSession").show();
		if (allData[0].isClosed == "NOT ASSIGNED") {
			$('#save-btn').attr("disabled", false);
		} else {
			$('#save-btn').attr("disabled", true);
		}

		$('#docTbl').on('click', '.rmv1', function() {
			openDeleteConfirm();
			var value = $(this).parent("div").attr("id");

			$("#dltValue").val(value);
		});
		$("#doctbodyData").append(tbl);

		$("#tktNo").html(ticketId);
		//$("#pagination").hide()
		$("#editTktId").val(allData[0].ticketId);
		$("#empId").val(allData[0].empId);
		$("#currentDate").val(allData[0].tktDate);
		$("#empName").val(allData[0].empName);
		//$("#dept").val(allData[0].dept);
		getCurrentDepartment();
		$("#ticketType").val(allData[0].tktType);
		//$("#tctCat").val(allData[0].tktCategory);
		//$("#ticketSubCategory").val(allData[0].tktSubCategory);
		getCategory(allData[0].tktCategory);
		getSubCategory(allData[0].tktCategory, allData[0].tktSubCategory);
		$("#ticketPriority").val(allData[0].tktPriority);
		$("#ticketSource").val(allData[0].ticketSource);
		$("#description").val(allData[0].tktDesc);
		$("#locType").val(allData[0].tktLocType);
		addAddress(allData[0].tktLocType);
		$("#latitude").val(allData[0].tktLatitude);
		$("#longitude").val(allData[0].tktLongitude);
		$("#empAddress").val(allData[0].tktAddress);
		$("#assetlocation").val(allData[0].tktAddress);
		$("#assetList").val(allData[0].assetid);
		if (allData[0].assetid != null && allData[0].assetid != '') {
			$("#assetlistfield").show();
			$("#locationDiv").hide();
		}

		$("#doctbodyData").empty();
		var docUrl;
		var documentList = allData[0].document;
		if (documentList != null
			&& documentList != "") {

			for (var i = 0; i < documentList.length; i++) {

				if (documentList[i].docurl != null && documentList[i].docurl != 'null') {
					tbl = '<tr>'
						+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclss" id="docnoid_' + i + '"> </div></td>'
						+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-pencil" id="clickImg_' + i + '"></i> </label>'
						+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
						+ i
						+ '"name="userImage" onchange="saveMultiFile(event)" /> </div>'
						+ '</div> <input type="hidden" id="uploadHidden_' + i + '" value="' + documentList[i].docurl + '" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_' + i + '" align="center" class="uploadedBillCls"><div class="uploadicon position-l"><i class="bi bi-file-earmark"></i></div></div>'
						+ '<div id="imageName_' + i + '" class="imageName">'
						+ documentList[i].fileName
						+ '</div>'
						+ '<input type="hidden" id="editId_' + i + '" value="' + documentList[i].ticketId + '">'
						+ '<div id="dltImage_' + i + '" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				} else {
					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
						+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
						+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
						+ '</tr>';
					$("#doctbodyData").html(tbl);
				}
			}
		} else {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
				+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}


	});

}


function getCurrentDepartment() {
	$.ajax({
		type: "GET",
		url: "view-agentTicket-get-currdept",
		success: function(response) {
			if (response.code == "success") {

				var data = response.body[0];

				$("#dept").val(data.name);
				$("#deptId").val(data.key);
			}
		},
		error: function(e) {
		}
	});
	// return data;
}



function getCategory(cat) {
	$("#tctCat").empty();
	$("#ticketSubCategory").empty();
	var selectedValue = $("#ticketType").val();
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "view-agentTicket-getcategory?id=" + selectedValue,
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
		url: "view-agentTicket-getSubcategory?id=" + selectedValue,
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
		//$("#locationDiv").hide();
	} else {
		$("#assetlistfield").hide();
		//$("#locationDiv").show();
	}

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
				console.log("browser not supported");
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

var timeout;
function onQuickFilterChanged() {

	clearTimeout(timeout);
	timeout = setTimeout(function() {
		var value = $("#quickFilter").val();

		if (value == "" || value == null || value == 'null') {
			$("#currentPageno").val("1");
			viewAllTickets();
			$("#pagination").show();
			id1.style.display = "none";
		} else {
			$("#pagination").hide();
			agGrid.simpleHttpRequest({
				url: "view-agentTicket-data-view-search?searchValue=" + value,
			}).then(function(data) {
				$(".loader").hide();
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.viewTicket;
				gridOptions.api.setRowData(allData);
				onSelectionChanged()
				var totalRowCount = gridOptions.api.getModel().getRowCount();
				$('#totalCandidate').find('span').html(totalRowCount);

			});

		}
	}, 500);
}


function removeClassFromBtn() {
    $(".operation-btn").removeClass("operation-btn-active");
}


function ticketDetailsEdit() {
	var emp = $("#sessionEId").val();
	var empName = $("#sessionEName").val();
	const currentDate = new Date();
	removeClassFromBtn();
	$("#ticket-editBtn").addClass("operation-btn-active");

	const year = currentDate.getFullYear();
	const month = currentDate.getMonth() + 1; 
	const day = currentDate.getDate();

	var cdate = getDateTime(); 
	$("#empId").val(emp);
	$("#empName").val(empName);
	$("#currentDate").val(cdate);

	// Enable the select fields
	$("#ticketType, #tctCat, #ticketSubCategory, #assetList, #ticketPriority, #ticketSource").attr('disabled', false);
	
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

	//var datetime =year+"-"+month+"-"+day+" "+ hour + ":"+ minute + ":" + second;

	var datetime = day + "-" + month + "-" + year + " ";

	return datetime
}

//show position
function showPosition(position) {
	console.log("Call")
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




function masterSaveData() {
	
	var obj = {};
	var valid = true;
	var uploadList = [];
	var datas = [];
	removeClassFromBtn();
	$("#save-btn").addClass("operation-btn-active");
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
		setTimeout(function() {

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
			obj.description = $("#description").val();
			obj.locType = $("#locType").val();
			obj.latitude = $("#latitude").val();
			obj.longitude = $("#longitude").val();
			obj.documentList = uploadList;
			if (obj.ticketSubCategory == 'TSCAT005' || obj.ticketSubCategory == 'TSCAT006') {
				if (obj.assetList == null || obj.assetList == "") {
					valid = validationUpdated("Asset is Required", "assetList");
				}
			}
			if (obj.locType == 'Property') {
				obj.address = $("#assetlocation").val();
				obj.empAddress = $("#assetlocation").val();
				if ($("#assetlocation").val() == null || $("#assetlocation").val() == "") {
					valid = validationUpdated("Property Location is Required", "assetlocation");
				}
			} else {
				obj.address = $("#empAddress").val();
				obj.empAddress = $("#empAddress").val();
				if (obj.address == null || obj.address == "") {
					valid = validationUpdated("Address is Required", "empAddress");
				}
			}
			//else{
			if (obj.locType == null || obj.locType == "") {
				valid = validationUpdated("Location is Required", "locType");
			}
			//}
			if (obj.ticketType == null || obj.ticketType == "") {
				valid = validationUpdated("Ticket Type is Required", "ticketType");
			}
			if (obj.ticketCategory == null || obj.ticketCategory == "") {
				valid = validationUpdated("Category is Required", "tctCat");
			}
			if (obj.ticketSubCategory == null || obj.ticketSubCategory == "") {
				valid = validationUpdated("SubCategory is Required", "ticketSubCategory");
			}
			if (obj.ticketPriority == null || obj.ticketPriority == "") {
				valid = validationUpdated("Priority is Required", "ticketPriority");
			}
			if (obj.ticketSource == null || obj.ticketSource == "") {
				valid = validationUpdated("Source is Required", "ticketSource");
			}
			if (obj.description == null || obj.description == "") {
				valid = validationUpdated("Description is Required", "description");
			}

			datas.push(obj);
			//$('.loader').show();
			//$("body").removeClass("overlay");
			if (valid) {
				saveTicket(datas);
			}

		}, 1000)

	}

}



function saveTicket(data) {
	$('.loader').show();
	
	console.log("data-->" , data);
	
	
	$.ajax({
		type: "POST",
		url: "view-agentTicket-save-data",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Data Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

				viewAllTickets();
				onSelectionChanged();
				removeClassFromBtn();
						$("#add-btn").addClass("operation-btn-active");
						var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
							console.log("First Row Node:", firstRowNode);
							if (firstRowNode) {
								firstRowNode.setSelected(true); // Select the first row
								var selectedRows = gridOptions.api.getSelectedRows();
								if (selectedRows.length > 0) {
									var id = selectedRows[0].tcktNo;
									console.log("Scheduled ID of first row:", id);
									editTicket(id);
								}
								}

			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Something Went Wrong");
				$("#msgOkModal").removeClass("btn1");
				$("#msgOkModal").addClass("btn3");
				$("#msgModal").modal('show');

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


function checkEmptyMasterSave() {
		
		var infofileName = true;
		$(".formValidation").remove();
		$('.docNoclss').each(function() {
			if ($(this).val() == null || $(this).val() == "") {
				infofileName = false;
				validationModal("Document Name Required", $(this).attr('id'));
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
					$("#messageParagraph").text("Please Choose a File ");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					return false;
				}

			});
		}
		if (infofileName && mulDocInfo) {
			return true
		}
	}
	
	
function raiseNewTicket(){
	removeClassFromBtn();
		$("#add-btn").addClass("operation-btn-active");
		$("#save-btn").attr("disabled",false);
		$("#locType").val('');
		$("#latitude").val('');
		$("#longitude").val('');
		
		$("#empAddress").val('');
		$("#ticketType,#tctCat,#ticketSubCategory,#assetList,#ticketPriority,#ticketSource,#locType").attr('disabled', false);
		var emp = $("#sessionEId").val();
			var empName = $("#sessionEName").val();
			const currentDate = new Date();

			// Get the date, month, and year components
			const year = currentDate.getFullYear();
			const month = currentDate.getMonth() + 1; // Months are zero-based, so add 1 to get the correct month
			const day = currentDate.getDate();

			var cdate = getDateTime();
			$("#empId").val(emp);
			$("#empName").val(empName);
			$("#currentDate").val(cdate);

			$('#docTbl').on('click', '.rmv1', function() {
				openDeleteConfirm();
				var value = $(this).parent("div").attr("id");

				$("#dltValue").val(value);
			});
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
					+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
					+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
					+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
					+ '</tr>';
			$("#doctbodyData").append(tbl);
			
			$("#ticketType").val("");
			$("#tctCat").val("");
			$("#ticketSubCategory").val("");
			$("#ticketPriority").val("");
			$("#ticketSource").val("");
			$("#locationDiv").show();
			$('#tctCat').find('option:not(:first)').remove();
			$('#ticketSubCategory').find('option:not(:first)').remove();
			$("#assetlistfield").hide();
			gridOptions.api.deselectAll();
			$("#save-btn").attr("disabled",false);
			$("#editTktId").val('');
			

	
}	

function openDeleteConfirm() {
		$("#dltValue").val("");
		$('#deleteAttachment').modal('show');
	}
	
	function closeDeleteConfirm() {
		$("#dltValue").val("");
		$('#deleteAttachment').modal('hide');
	}
	
	function deleteFun() {
		console.log("click");
			$('#deleteAttachment').modal('show');
			removeClassFromBtn();
					$("#delete-btn").addClass("operation-btn-active");
		}
		
		function deleteAttachmentRow() {
					console.log("id-->",ticketId);
				if (ticketId) {
					$.ajax({
					type : "GET",
					url : "view-agentTicket-delete?id="+ ticketId,
					success : function(response) {
					 if (response.code == "success") {
						 closeDeleteConfirm();
							$("#messageParagraph").text("Ticket Deleted Sucessfully");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
							$('#delete').modal('hide');
							$('#reqDltBtn').attr('disabled', true);
							viewAllTickets();
							onSelectionChanged();
							
						} else {
							$("#messageParagraph").text("Something went to wrong!");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
						}
					},
					error : function(data) {
					}
				})
			} 
	}
	
	
	
	
	
	
	function feedbackFun(){
			$("#feedbackSendBtn").hide();
			var selectedNodes = gridOptions.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);
			var id= selectedData.map(node => node.tcktNo);
			var newchat="";
			agGrid.simpleHttpRequest(
					{
						url : 'view-agentTicket-chat-view?id=' + id[0]+"&type="+ "User"
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData=jsonData.Chat;
						if(allData!=null){
						allData.forEach(function(rowNode,index){
							if(rowNode.type=="User"){
								newchat +=`<div class="d-flex flex-row justify-content-end">
				                    <div>
				                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
				                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
				                    </div>
				                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
				                      alt="avatar 1" style="width: 45px; height: 100%;">
				                  </div>`;
							}else{
								newchat +=`<div class="d-flex flex-row justify-content-start">
									<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
				                      alt="avatar 1" style="width: 45px; height: 100%;">
			                    <div>
			                      <p class="small p-2 ms-3 mb-1 rounded-3 font-weight-bold params-desc" style="background-color: #f5f6f7;">${rowNode.desc}</p>
			                      <p class="small ms-3 mb-3 rounded-3 text-muted float-end">${rowNode.date}</p>
			                    </div>
			                  </div>`;
							}
							});
						}
						else{
							newchat +=`<div class="d-flex flex-row justify-content-center">
																
										                    <div>
										                      <p class="No-msg-para" >No messages yet. Start the conversation!</p>
										                      
										                    </div>
										                  </div>`;
						}
						$("#chatDiv").html(newchat);
						$("#feedbackSendBtn").show();
					});
			$('#feedbackModal').modal('show');
		}
		
		function sendFeedback(){
			
			var selectedNodes = gridOptions.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);
			var id= selectedData.map(node => node.tcktNo);
			var obj = {};
			var valid = true;
			var data = [];
			
			obj.ticketId =id[0];
			obj.resultDescription = $("#ticketFeedback").val();
			obj.ticketType = "User";

			if (obj.feedback == null || obj.feedback == "") {
				valid = validationUpdated("", "ticketFeedback");
			}
			data.push(obj);
			if(valid){
				loader.classList.remove("d-none");
				$("#feedbackSendBtn").hide();
			setTimeout(function() {
			$.ajax({
				type : "POST",
				url : "view-agentTicket-feedback-save",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(data),
				success : function(response) {
					if (response.code == "success") {
						var newchat="";
						var jsonData = JSON.parse(response.body);
						var allData=jsonData.Chat;
						allData.forEach(function(rowNode,index){
							if(rowNode.type=="User"){
								newchat +=`<div class="d-flex flex-row justify-content-end">
				                    <div>
				                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
				                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
				                    </div>
				                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
				                      alt="avatar 1" style="width: 45px; height: 100%;">
				                  </div>`;
							}else{
								newchat +=`<div class="d-flex flex-row justify-content-start">
			                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
			                      alt="avatar 1" style="width: 45px; height: 100%;">
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
						var newchat=`<div class="d-flex flex-row justify-content-end">
		                    <div>
		                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold">${obj.resultDescription}</p>
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
				error : function(response) {
					loader.classList.add("d-none");
					$("#feedbackSendBtn").show();
					$("body").removeClass("overlay");
					$("#messageParagraph").text("Something Went Wrong");
					$("#msgOkModal").removeClass("btn1");
					$("#msgOkModal").addClass("btn3");
					$("#msgModal").modal('show');
				}
			})
			}, 1000);
			}
		}
		
		
		
		
		function feedbackRateFun(){
				var selectedNodes = gridOptions.api.getSelectedNodes();
				var selectedData = selectedNodes.map(node => node.data);
				var rate= selectedData.map(node => node.Rating);
			    if (rate[0] > 0) {
			        $('.rating input.inputRate').each(function(index) {
			            if (index < rate[0]) {
			                $(this).prop('checked', true);
			            } else {
			                return false;
			            }
			        });
			    } else {
			        $('.rating input.inputRate').prop('checked', false);
			    }
				$('#feedbackRateModal').modal('show');
			}
			
			function sendRateFun() {
				var checkedIds = [];
		        $('.rating input.inputRate:checked').each(function() {
		            checkedIds.push(this.value);
		        });
				var checkedCount=checkedIds.join(', ');
				if (ticketId[0]) {
					$.ajax({
					type : "GET",
					url : "view-agentTicket-rate-save?id="+ ticketId[0]+"&rate="+checkedCount,
					success : function(response) {
					 if (response.code == "success") {
						 	$('#feedbackRateModal').modal('hide');
							$("#messageParagraph").text("Thank you for submitting your rating!");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
							$('#reqDltBtn').attr('disabled', true);
							viewAllTickets();
							onSelectionChanged();
							
						} else {
							$('#feedbackRateModal').modal('hide');
							$("#messageParagraph").text("Something went to wrong!");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
						}
					},
					error : function(data) {
					}
				})
			} 
		}
		
		
		
		