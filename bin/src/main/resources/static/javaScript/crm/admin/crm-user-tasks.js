let validation = true;
let taskId = "";
$(document).ready(function() {
	$('#customLoad').show();

	setTimeout(function() {
		$('#customLoad').hide();
		$('.loader-backdrop').hide();
	}, 3000);
});
/*----------------------------Task Grid Column def and Grid options--------------------------------*/

var columnTaskDefs = [
	/*{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},*/ {
		headerName: "Title",
		field: "title",

		width: 250,
		//hide: true,
		cellRenderer: function(params) {
			console.log(params, 'draftttt')
			return '<a href="javascript:void(0)">'
				+ params.data.title + '</a>';
		}
	},



	{ headerName: "Executive", field: "executiveName", width: 250, },
	{ headerName: "Lead First Name", field: "leadFirstName", width: 250, },
	{ headerName: "Lead Last Name", field: "leadLastName", width: 250, },
	{ headerName: "Status", field: "status", width: 250, },
	{ headerName: "Lead Mobile", field: "leadMobile", width: 250, },
	{ headerName: "Executive Id", field: "executiveId", width: 250, hide: true },
	{ headerName: "Created Date", field: "createdDate", width: 250, },
	{ headerName: "Description", field: "description", width: 250, hide: true },
	{ headerName: "Priority", field: "priority", width: 250 }

];


var taskgridOptions = {
	columnDefs: columnTaskDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'single',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	suppressExcelExport: true,
	onCellClicked: handleCellValueChanged,
	suppressRowClickSelection: false // Allow selecting rows by clicking on cells
};



/**
 * Ag-Grid Search bar settings
 */




/*function onQuickFilterChanged() {
	taskgridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = taskgridOptions.api.getDisplayedRowCount();
	var len = displayedRowCount;
	$('#totalTask').find('span').html(len);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";


	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";

	}

}
*/
/* ------------------- search bar for mygrid------------------------ */

function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	taskgridOptions.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	taskgridOptions.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = taskgridOptions.api.getDisplayedRowCount();
	$('#totalTask span').html(displayedRowCount);
}



/**
 * On Row Selection event of Ag-grid
 */

let taskIdd = '';
let leadName = '';
let leadMobile = '';
let assignedMailId = '';

function handleCellValueChanged() {
	//var isSelected = event.node.isSelected();


	//if (isSelected) {
	var selectedRowData = taskgridOptions.api.getSelectedRows()[0];
	console.log("Selected Row Data:", selectedRowData);

	$("#title").val(selectedRowData.title);
	CKEDITOR.instances.commentck.setData(selectedRowData.description);
	openTaskNav()

	$("#executive").val(selectedRowData.executiveId);

	if (selectedRowData.priority === "High") {
		$("#priority").val("1");
	} else if (selectedRowData.priority === "Medium") {
		$("#priority").val("2");
	} else if (selectedRowData.priority === "Low") {
		$("#priority").val("3");
	} else {
		swal("Something went wrong with priority!")
	}



	$("#taskId").val(selectedRowData.taskId);
	$("#status").val(selectedRowData.statusId);
	$("#leadFirstName").val(selectedRowData.leadFirstName);
	$("#leadLastName").val(selectedRowData.leadLastName);
	$("#leadMobile").val(selectedRowData.leadMobile);
	assignedMailId = selectedRowData.assignedMail;

	taskIdd = selectedRowData.taskId;
	leadName = selectedRowData.leadName;
	leadMobile = selectedRowData.leadMobile;


	$("#title").attr("disabled", "disabled");
	$("#executive").attr("disabled", "disabled");
	$("#priority").attr("disabled", "disabled");
	$("#leadFirstName").attr("disabled", "disabled");
	$("#leadLastName").attr("disabled", "disabled");
	$("#leadMobile").attr("disabled", "disabled");



	if (selectedRowData.statusId == 5) {

		$('#status').attr('disabled', true);
		$('#saveBtn').attr('disabled', true);
		$('#assignedLead').attr('disabled', true);

	} else {
		$('#status').attr('disabled', false);
		$('#saveBtn').attr('disabled', false);
		$('#assignedLead').attr('disabled', false);
	}
	$("#priority").attr("disabled", "disabled");

	/*} else {
		crmTaskCloseNav()
	}*/
}

/**
 * Mail Sucess Modal 
 */

function mailSucessAlert() {

	$('.email-alert').removeClass('collapse');
	$('.email-alert').addClass('fade-in');
	const alert = document.getElementById('alert');
	setTimeout(() => {
		$('.email-alert').addClass('collapse');
		$('.email-alert').removeClass('fade-in');
		$('.email-alert').removeClass('fade-out');

	}, 5000);

	setTimeout(() => {
		$('.email-alert').addClass('fade-out');
	}, 3000);
}

/**
 * Chnage Status of task by Executive
 */
function saveStatus() {


	let taskId = $("#taskId").val();
	let statusId = $("#status").val();
	let executive = $("#executive").val();

	let object = {
		"taskId": taskId,
		"status": statusId,
		"executiveId": executive
	}

	if (statusId == '5') {
		$("#myModal").modal('show');
		$("#rejectionReason").val("");
		$("#taskStatus1").val("");
		$('#myElement').empty();
		confirmRejectTask();

	}

	else {
		$.ajax({

			type: "POST",
			url: "crm-user-tasks/change-status",
			contentType: 'application/json',
			data: JSON.stringify(object),
			success: function(response) {
				$('.loader').show();
				if (response.code = "Success") {
					crmTaskCloseNav()
					viewTasks()
					getCrmNotifications()
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
 * Task Nav open and Close
 */

function openTaskNav() {

	if ($(document).width() > 1038) {

		$("#crm-task-sidenav").addClass("show-crm-task-sidenav")
		$("#crm-task-sidenav").removeClass("hide-crm-task-sidenav")
		$("#crm-task-sidenav").removeClass("mobile-view");
		//$("#taskAssign").attr("disabled", "disabled")
		$(".desc-task-error").css("display", "none")
		$(".desc-task-error").text("");
		$(".formValidation").remove();
		$("#leadFirstName").val("");
		$("#leadLastName").val("");
		$("#leadMobile").val("");
		$("#executive").val("");

	} else {

		$("#crm-task-sidenav").addClass("show-crm-task-sidenav");
		$("#crm-task-sidenav").addClass("mobile-view");
		$("#crm-task-sidenav").removeClass("hide-crm-task-sidenav");
		$(".closebtn").hide();
		$(".desc-task-error").css("display", "none")
		$(".desc-task-error").text("");
		$(".formValidation").remove();
		$("#leadFirstName").val("");
		$("#leadLastName").val("");
		$("#leadMobile").val("");
		$("#executive").val("");
	}


}
function crmTaskCloseNav() {
	$("#crm-task-sidenav").addClass("hide-crm-task-sidenav")
	$("#crm-task-sidenav").removeClass("show-crm-task-sidenav")
	$("#taskAssign").removeAttr("disabled")
	//clear all data
	CKEDITOR.instances['commentck'].setData("");
	$("#title").val("")
	$("#customerId").val("")
	$("#priority").val("")
	taskgridOptions.api.deselectAll();
}


/**
 * View Executive Assigned Tasks
 */
let assignedMail = '';
function viewTasks() {
	let userId = $("#userId").val();
	var rowData = [];
	taskgridOptions.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "admin-task-assign-user-tasks?userId=" + userId + "&type=",
	}).then(function(resp) {
		if (resp.code == "Success") {
			let data = JSON.parse(resp.body);
			$('#totalTask').find('span').html(data.length)
			taskgridOptions.api.setRowData(data);
		}
		$('.loader').hide();
	});
}

/**
 * getTaskRedirect Function
 */
function getTaskRedirect() {
	taskId = localStorage.getItem("taskId");

	if (taskId != "" && taskId != null) {
		agGrid.simpleHttpRequest({
			url: "admin-task-assign-task?taskId=" + taskId,
		}).then(function(resp) {
			if (resp.code == "Success") {
				let data = JSON.parse(resp.body);
				console.log("DATA ----------->>>>>>>>>>>" + data[0])

				localStorage.setItem("taskId", "");

				openTaskNav()

				$("#title").val(data[0].title);
				CKEDITOR.instances.commentck.setData(data[0].description);
				$("#executive").val(data[0].executiveId);
				$("#priority").val(data[0].priorityId);
				$("#taskId").val(data[0].taskId);
				$("#leadFirstName").val(data[0].firstName);
				$("#leadLastName").val(data[0].lastName);
				$("#leadMobile").val(data[0].mobile);

				$("#title").attr("disabled", "disabled");
				$("#executive").attr("disabled", "disabled");
				$("#priority").attr("disabled", "disabled");

			}
		});
	}
}

/** 
 * To chnage the status of task
*/
function chnageStatusOfTask(id, taskId) {


	agGrid.simpleHttpRequest({
		url: "admin-task-assign-chnage-status?id=" + id + "&taskId=" + taskId,
	}).then(function(resp) {
		if (resp.code == "Success") {
			$("#status").val(id);
			viewTasks()
		}
	});
}

/**
 * Document Ready
 */
$(document).ready(function() {
	$('#rejectTsk').attr('disabled', true);
	$('.loader').show();
	$('#totalTask').find('span').html(0)

	var taskgridDiv = document.querySelector('#myGridTask');
	new agGrid.Grid(taskgridDiv, taskgridOptions);

	var task = localStorage.getItem("taskId");

	if (task != "") {
		var id = "2"
		chnageStatusOfTask(id, task)
	}


	/**
	 * CKEDITOR initialization
	 */
	CKEDITOR.replace('commentck', {
		enterMode: CKEDITOR.ENTER_BR,
		height: 150,
		removePlugins: 'wsc',
		// config.enterMode = CKEDITOR.ENTER_BR,
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3
	});

	/**
	* Call get task function if its redirected from notification
	*/
	getTaskRedirect();

	/**
	* Task view function
	*/
	viewTasks();

	getCrmNotifications();

});

/*
*
* Getting Task Status value 
* Author: Pankaj Kumar 
*/
let taskStatus = '';
function getOptions(cursor) {

	taskStatus = cursor.options[cursor.selectedIndex].text;


	if (taskStatus == "blocked") {
		$('#rejectTsk').attr('disabled', false);
	} else {
		$('#rejectTsk').attr('disabled', true);
	}
}

/*function rejectTask(){
	$("#myModal").modal('show');
	$("#rejectionReason").val("");
	$("#taskStatus1").val("");
	$('#myElement').empty();
}*/


function confirmRejectTask() {
	//alert(assignedMailId);
	var executiveSelect = $("#executive");
	var selectedOption = executiveSelect.find(":selected");
	var executiveMail = selectedOption.data("code") || '';
	//alert(executiveMail)
	var rejectedReason = $('#rejectionReason').val();
	var taskStatus1 = $('#status').val();

	var validation = true;

	if (rejectedReason == null || rejectedReason == "") {
		$("#errorMsg").empty();
		validation = validationUpdated($("#errorMsg").append("Rejected Reasons Required"))
	}

	if (validation) {
		$("#myModal").modal('hide');
		$(".loader").show();

		$.ajax({
			tyoe: "GET",
			url: "admin-task-assign-reject-status?id=" + taskIdd + "&taskStatus=" + taskStatus1 + "&reasons=" + rejectedReason + "&assignedMailId=" + assignedMailId + "&executiveMail=" + executiveMail,
			success: function(response) {
				console.log(response)
				if (response.code == "Success") {
					crmTaskCloseNav();
					viewTasks();
					getCrmNotifications();
					mailSucessAlert();

					$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$(".loader").hide();
				}
			}

		})
	}
}

function createTaskLead() {
	var leadFirstName = $("#leadFirstName").val();
	var leadLastName = $("#leadLastName").val();
	var leadPhone = $("#leadMobile").val();

	localStorage.setItem('leadFirstName', leadFirstName);
	localStorage.setItem('leadLastName', leadLastName);
	localStorage.setItem('leadPhone', leadPhone);

	//getUrl(MOD029, fun, activity)
	getUrl(MOD029, sessionStorage.getItem("leadFun"), sessionStorage.getItem("leadAct"));

}

function getUrl(module, fun, activity) {

	/*let taskid = $("#taskId").val();
	localStorage.setItem("taskId", taskid);*/

	$.ajax({
		type: "GET",
		url: "/index-get-breadcrumb-data?moduleId=" + module + "&fun="
			+ fun + "&activity=" + activity,
		async: false,
		success: function(response) {
			if (response.message == "Unsuccess") {
				console.log(JSON.stringify(response));

				modOnclick(fun);
				callActivity(activity, response.body.actURL);

			}
		},
		error: function(data) {
		}
	});
}

/* 
 * Decrypt function 
 **/
function decrypt(ciphertext, key) {
	const bytes = CryptoJS.AES.decrypt(ciphertext, key);
	return bytes.toString(CryptoJS.enc.Utf8);
}

/**
 * Set Selected Task
 *  */
function getSelectedTaskId(taskid) {
	let id = decrypt(taskid, "notification")
	localStorage.setItem("taskId", id);
}


