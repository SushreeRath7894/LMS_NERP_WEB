
/**
 * Dashboard Reports Datas
 * @author: Sourav Mehta
 * Created on: 16-04-2024
 */
 
 

$(document).ready(function() {
	

	var gridDiv2 = document.querySelector('#callsGrid');
	new agGrid.Grid(gridDiv2, callsgridOptions);
	
	
	var gridDiv = document.querySelector('#meetingsGrid');
	new agGrid.Grid(gridDiv, gridOptionsMeetings);
	
	var gridDiv = document.querySelector('#tasksGrid');
	new agGrid.Grid(gridDiv, gridOptionsTasks);

});


/*Grid for Calls Start*/
var columnDefs = [
	{
		headerName: 'Call Id',		
		field: "callId",
		width: 100,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Subject",
		field: "subject",
		width: 200,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Lead Name",
		field: "leadName",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Executive Name",
		field: "executiveName",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Contact Name",
		field: "contactName",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Call Type",
		field: "calltype",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "On Going Status",
		field: "ongoingStatus",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Pupose",
		field: "purpose",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Start Date",
		field: "startDate",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Start Time",
		field: "startTime",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "End Time",
		field: "TTM_Call_EndTime",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Created Date",
		field: "createdOn",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
];

var callsgridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
};


/*Grid for Calls End*/

/*grid for Meetings Start*/

var columnDefs = [
	 {
		headerName: 'Meeting Id',
		field: "meetingId",
		width: 120,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Meeting Title",
		field: "meetingName",
		width: 200,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Lead Name",
		field: "leadName",
		width: 180,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Executive Name",
		field: "executiveName",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Company Name",
		field: "companyName",
		width: 180,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Frequency",
		field: "frequency",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Meeting From Date",
		field: "meetingFromDate",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "From Time",
		field: "meetingFromTime",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Meeting To Date",
		field: "meetingToDate",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "To Time",
		field: "meetingToTime",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Meeting Mode",
		field: "meetingMode",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Meeting Location",
		field: "meetingLocation",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Meeting Host",
		field: "hostOwner",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Agenda",
		field: "agenda",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Status",
		field: "meetingStatus",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	},
	
	{
		headerName: "Created On",
		field: "createdOn",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	}];

var gridOptionsMeetings= {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100,
		height: 10
	}
};
/*grid for Meetings End*/

/*grid for  tasks Start*/
var columnDefs = [
	{
		headerName: 'Task Id',
		field: "taskId",
		width: 120,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Subject",
		field: "subject",
		width: 200,
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Lead Name",
		field: "leadName",
		width: 160,
		cellStyle: {
			textAlign: 'left'
		}
	},{
		headerName: "Executive Name",
		field: "executiveName",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},
	
	{
		headerName: "Company Name",
		field: "companyName",
		width: 160,
		cellStyle: {
			textAlign: 'left'
		}
	},
	 {
		headerName: "Task Due Date",
		field: "dueDate",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Task Priority",
		field: "priority",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},
	
	{
		headerName: "Lead Executive",
		field: "leadExecutive",
		width: 160,
		cellStyle: {
			textAlign: 'left'
		}
	},
	
	 {
		headerName: "Status",
		field: "taskStatus",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Created On",
		field: "createdOn",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}];

var gridOptionsTasks = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100,
		height: 10
	}
};
/*grid for  tasks end*/

function activityHighChart(){
	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();
	var org = $("#organizationCrmActivities").find('option:selected').text();
	var orgDiv = $("#divisionCrmActivities").find('option:selected').text();
	var loc = $("#locationCrmActivities").val();
	var executive = $("#executiveActivitiesId").val();
	
	$.ajax({
		type: "GET",
		url: "crm-dashboard-activities-count-heading",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			executive: executive
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;	
				
                $("#totalMeetingsId").text(allData[0].totalMeetings);
				$("#totalCallsId").text(allData[0].totalCalls);
				$("#totalCallsDurationId").text(allData[0].totalCallsDuration);
				$("#totalTaskId").text(allData[0].totalTasks);				
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
	

	
	/*for lead calls*/
	
	$.ajax({
		type: "GET",
		url: "crm-dashboard-activity-leadsCalls",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			executive: executive
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;				
				callsgridOptions.api.setRowData(allData);
			}
		},
		
	});
	
	/*for lead meetings*/
	$.ajax({
		type: "GET",
		url: "crm-dashboard-activity-leadsMeetings",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			executive: executive
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				gridOptionsMeetings.api.setRowData(allData);
			}
		},
		
	});
	
	/*for lead task*/
	$.ajax({
		type: "GET",
		url: "crm-dashboard-activity-leadTasks",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			executive: executive
		},
		async: true,
		success: function(response) {
			if (response.code == "success") {
				
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;	
				gridOptionsTasks.api.setRowData(allData);
			}
		},
		
	});
		

	
}




