let validation = true;

/*----------------------------Task Grid Column def and Grid options--------------------------------*/
 $(document).ready(function() {
        //$('#customLoad').show();
        
        setTimeout(function() {
            $('#customLoad').hide();
            $('.loader-backdrop').hide();
        }, 3000); 
 });
var columnTaskDefs = [
	{
		//headerCheckboxSelection: true,
		//checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},{
		headerName : "Task Id",
		field : "taskId",
		pinned : 'left',
		width: 250,
		hide: true,
		cellRenderer: function (params) {
			console.log(params, 'draftttt')
			return '<a onclick=viewTaskDetails("'
				+ params.data.taskId
				+ '") href="javascript:void(0)">'
				+ params.data.taskId + '</a>';
		}
	},
	{headerName : "Title", field : "title",width: 250},
	{headerName : "Executive", field : "executiveName",width: 250,}, 
	{headerName : "Lead Name", field : "leadName",width: 250,}, 
	{headerName : "Lead Mobile", field : "leadMobile",width: 250,}, 
	{headerName : "Executive Id", field : "executiveId",width: 250, hide:true},
	{headerName : "Created Date", field : "createdDate",width: 250,},
	{headerName : "Description", field : "description",width: 250, hide:true},
	{headerName : "Priority", field : "priority",width: 250,},
	{headerName : "Status", field : "status",width: 250,}
];

var taskgridOptions = {
	columnDefs: columnTaskDefs,
	suppressHorizontalScroll: false,
	rowSelection: 'multiple',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	suppressRowClickSelection: true,
	suppressExcelExport: true,
	onSelectionChanged: onSelectionChanged,
};

function onSelectionChanged(){
	var selectedRows = taskgridOptions.api.getSelectedRows();
	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	
	if (rowCount > 0) {
		$("#taskAssign").attr("disabled",true);
        $("#addTask").attr("disabled",true);
	} else {
		$("#taskAssign").attr("disabled",false);
		$("#addTask").attr("disabled",false);
	}
}
/**
 * Ag-Grid Search bar settings
 */

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
 * Task Nav open and Close
 */
function openTaskNav(){
	
	if($(document).width() > 1038) {
	$("#crm-task-sidenav").addClass("show-crm-task-sidenav")
	$("#crm-task-sidenav").removeClass("mobile-view");
	$("#crm-task-sidenav").removeClass("hide-crm-task-sidenav")
	$("#taskAssign").attr("disabled", "disabled")
	$(". desc-task-error").css("display", "none")
	$(". desc-task-error").text("");
	$(".formValidation").remove();
		//clear all data
	//CKEDITOR.instances['commentck'].setData("");
	$("#title").val("")
	$("#customerId").val("")
	$("#priority").val("")
	$("#leadFirstName").val("")
	$("#leadLastName").val("")
	$("#leadMobile").val("")
	
  }else {
    $("#crm-task-sidenav").addClass("show-crm-task-sidenav")
    $("#crm-task-sidenav").addClass("mobile-view");
    $(".closebtn").hide();
	$("#crm-task-sidenav").removeClass("hide-crm-task-sidenav")
	$("#taskAssign").attr("disabled", "disabled")
	$(". desc-task-error").css("display", "none")
	$(". desc-task-error").text("");
	$(".formValidation").remove();
		//clear all data
	//CKEDITOR.instances['commentck'].setData("");
	$("#title").val("")
	$("#customerId").val("")
	$("#priority").val("")
	$("#executive").val("")
	$("#leadFirstName").val("")
	$("#leadLastName").val("")
	$("#leadMobile").val("")
	  
  }
}
function crmTaskCloseNav(){
	$('div.formValidation').remove();
	$("#crm-task-sidenav").addClass("hide-crm-task-sidenav")
	$("#crm-task-sidenav").removeClass("show-crm-task-sidenav")
	$("#taskAssign").removeAttr("disabled")
	//clear all data
	//CKEDITOR.instances['commentck'].setData("");
	$("#title").val("");
	$("#customerId").val("");
	$("#priority").val("");
	$("#executive").val("");
	$("#leadFirstName").val("");
	$("#leadLastName").val("");
	$("#leadMobile").val("");
};

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
 * Add Task Api
 */
function addTask() {
	
    var executiveSelect = $("#executive");
    var selectedOption = executiveSelect.find(":selected");
    var executiveCode = selectedOption.data("code") || '';
 
	let obj = {}
	
	var description = $("#commentck").val();
    description = description.trim();
    obj.description = description;
	
	//obj.description = CKEDITOR.instances.commentck.getData();;
	obj.executive = $("#executive").val();
	obj.priority = $("#priority").val();
	obj.title = $("#title").val();
	obj.executiveMail = executiveCode;
	obj.leadFirstName = $("#leadFirstName").val();
	obj.leadLastName = $("#leadLastName").val();
	obj.leadMobile = $("#leadMobile").val();
	var validation = true;
	
    if(obj.title == null || obj.title == ""){
        validation = validationUpdated("Title Required", "title");
    } else if (obj.executive == null || obj.executive == ""){
		validation = validationUpdated("Executive Required", "executive");
	} else if (obj.priority == null || obj.priority == ""){
		validation = validationUpdated("Priority Required", "priority");
	} else {
		 $(".formValidation").text("");
        // Title is not empty, proceed to check description
        if(obj.description == null || obj.description == ""){
           validation = validationUpdated("Priority Required", "commentck");
            validation = false; // Set validation to false as there's an error
        }
    }
	console.log("Data For Task=======>",obj);
	if(validation){
		$('.loader').show();
		
		$.ajax({
	        type: "POST",
	        url: "admin-task-assign-add-task",
	        contentType: 'application/json',
	        data: JSON.stringify(obj),
	        success: function (response) {
				console.log(response);
				
				if(response.code == "Success"){
					$('.loader').hide();
					crmTaskCloseNav()
					viewTasks();
					mailSucessAlert();
					$("toggleSection").addClass("d-none");
					showSnackbar("Task Assigned Successfully");
				    closeTask();
				    
				}else{
					showSnackbar(response.message);
				}
			}
		});
	}
	
}

/**
 * View all Tasks
 */
function viewTasks(){
	
	agGrid.simpleHttpRequest({
		url: "admin-task-assign-all-tasks",
	}).then(function (resp) {
		if(resp.code == "Success"){
			let data = JSON.parse(resp.body);
			$('#totalTask').find('span').html(data.length)
			taskgridOptions.api.setRowData(data);
		}else{
			taskgridOptions.api.setRowData([]);
		}
		$('.loader').hide();
	});
}


/**
 * Document Ready
 */
$(document).ready(function(){
	//$('.loader').show();
	$('#totalTask').find('span').html(0)
	
	var taskgridDiv = document.querySelector('#myGridTask');
	new agGrid.Grid(taskgridDiv, taskgridOptions);
	
	
	//let userId = $("#executive").val();
	
	//leadDetailsByExecutive(userId)
	//$('.loader').hide();
	
	/**
	 * CKEDITOR initialization
	 */
	/*CKEDITOR.replace('commentck', {
		enterMode: CKEDITOR.ENTER_BR, 
	 	height: 150,
	 	removePlugins: 'wsc',
	 	// config.enterMode = CKEDITOR.ENTER_BR,
	 	scayt_autoStartup: true,
	 	scayt_maxSuggestions: 3
 	});*/
 	
 	/**
	  * Task view function
	  */
	 viewTasks();
});


