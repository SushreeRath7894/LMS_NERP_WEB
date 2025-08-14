function operational() {
	operationalHeadCount();
	productionHeadData();
	var id = 'tickets';
	getAllReport(id);
	
}

function operationalHeadCount(){
	var organization=$("#operationalOrgSlect").find('option:selected').text();
	var division=$("#OperationalDivSelect").find('option:selected').text();
	var fromDate=$("#operationalFromDate").val();
	var toDate=$("#operationalToDate").val();
	var location=$("#location").val();
	
	  $.ajax({
        url: "manage-dashboard-operational-head-count-data?fromDate=" + fromDate + "&toDate=" + toDate + "&location=" + location + "&organization=" + organization+"&division="+division,
        type: 'GET',
        dataType: 'json',
        success: function(response){
            var jsonData = JSON.parse(response.body);
            var headCountData=jsonData.operationalHeadCount
            console.log("Head Count Data===>",headCountData)
             $("#ticketBacklogValue").text(headCountData[0].backlog);
             $("#firstResponseTimeValue").text(headCountData[0].firstResponseTime);
             $("#resolutionRateValue").text(headCountData[0].resolutionRate);
             $("#customerSatisfactionValue").text(headCountData[0].customerSatisfaction);
             $("#resolvedThisMonthValue").text(headCountData[0].resolvedOThisMonth);
             $("#averageFirstReplyTimeValue").text(headCountData[0].avgFirstReply);
        },
        error: function(error) {
            console.error(error);
        }
    });
}


function productionHeadData() {
	
	var organization=$("#operationalOrgSlect").find('option:selected').text();
	var division=$("#OperationalDivSelect").find('option:selected').text();
	var fromDate=$("#operationalFromDate").val();
	var toDate=$("#operationalToDate").val();
	var location=$("#location").val();
	
	$.ajax({
		type: "GET",

		url:"manage-dashboard-operational-tab-data?fromDate=" + fromDate +  "&toDate=" + toDate +"&location=" + location + "&organization=" + organization + "&division=" +division,  
		async: true,

		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				$("#tickets").text(allData[0].tickets);
				$("#backlog").text(allData[0].backlog);
				$("#overdue").text(allData[0].overdue);
				$("#assigned").text(allData[0].assigned);
				$("#unassigned").text(allData[0].unassigned);
				$("#escalated").text(allData[0].escalated);
				$("#reopened").text(allData[0].reopened);



			}
		}, error: function(data) {
			console.log(data);
		}
	})

}

function setActiveTab(id) {
  
    const boxes = document.querySelectorAll('.box');
    boxes.forEach(box => {
        box.classList.remove('active');
    });

    const activeBox = document.querySelector(`#tab-${id}`);
    if (activeBox) {
        activeBox.classList.add('active');
    }
}

function getAllReport(id) {
 setActiveTab(id)
	
	var organization=$("#operationalOrgSlect").find('option:selected').text();
	var division=$("#OperationalDivSelect").find('option:selected').text();
	var fromDate=$("#operationalFromDate").val();
	var toDate=$("#operationalToDate").val();
	var location=$("#location").val();
	 
	 
	
	$("#ticketAgGrid").hide();
	$("#backlogAgGrid").hide();
	$("#overdueAgGrid").hide();
	$("#assignedAgGrid").hide();
	$("#unAssignedAgGrid").hide();
	$("#escalatedAgGrid").hide();
	$("#grid7ScrapedTable").hide();

	 

	if (id == "tickets") {
		$("#ticketAgGrid").show().empty();
		$("#ticketTable").show();
		$("#backlogTable").hide();
		$("#overdueTable").hide();
		$("#assignedTable").hide();
		$("#unAssignedTable").hide();
		$("#escalatedTable").hide();
		$("#reopenedTable").hide();

		var gridDiv = document.querySelector('#ticketAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsTicket);

		$.ajax({
			type: "GET",
			url: "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate +"&location=" + location + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
                     var ticketData =jsonData;
					$("#ticketTable").show();
           
					gridOptionsTicket.api.setRowData([]);

					if (!ticketData || ticketData.length === 0) {
						gridOptionsTicket.api.setRowData([]);
					} else {
						gridOptionsTicket.api.setRowData(ticketData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "backlog") {
		$("#backlogAgGrid").show().empty();
		$("#backlogTable").show();
		$("#ticketTable").hide();
		$("#overdueTable").hide();
		$("#assignedTable").hide();
		$("#unAssignedTable").hide();
		$("#escalatedTable").hide();
		$("#reopenedTable").hide();

		var gridDiv = document.querySelector('#backlogAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsBacklog);

		$.ajax({
			type: "GET",
			url: "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate +"&location=" + location + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var backlogData = jsonData;

                     $("#backlogTable").show();
					gridOptionsBacklog.api.setRowData([]);

					if (!backlogData || backlogData.length === 0) {
						gridOptionsBacklog.api.setRowData([]);
					} else {
						gridOptionsBacklog.api.setRowData(backlogData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "overdue") {
		$("#overdueAgGrid").show().empty();
		$("#overdueTable").show();
		$("#ticketTable").hide();
		$("#backlogTable").hide();
		$("#assignedTable").hide();
		$("#unAssignedTable").hide();
		$("#escalatedTable").hide();
		$("#reopenedTable").hide();

		var gridDiv = document.querySelector('#overdueAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsOverdue);

		$.ajax({
			type: "GET",
			url:  "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate + "&location=" + location +"&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var overdueData = jsonData;

                    $("#overdueTable").show();
					gridOptionsOverdue.api.setRowData([]);
					if (!overdueData || overdueData.length === 0) {
						gridOptionsOverdue.api.setRowData([]);
					} else {
						gridOptionsOverdue.api.setRowData(overdueData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "assigned") {
		$("#assignedAgGrid").show().empty();
		$("#assignedTable").show();
		$("#overdueTable").hide();
		$("#ticketTable").hide();
		$("#backlogTable").hide();
		$("#unAssignedTable").hide();
		$("#escalatedTable").hide();
		$("#reopenedTable").hide();


		var gridDiv = document.querySelector('#assignedAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsAssigned);

		$.ajax({
			type: "GET",
			url:  "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate + "&location=" + location + "&toDate=" + toDate +"&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var assignedData = jsonData;
                     $("#assignedTable").show();
					gridOptionsAssigned.api.setRowData([]);
					if (!assignedData || assignedData.length === 0) {
						gridOptionsAssigned.api.setRowData([]);
					} else {
						gridOptionsAssigned.api.setRowData(assignedData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "unassigned") {
		$("#unAssignedAgGrid").show().empty();
		$("#unAssignedTable").show();
		$("#assignedTable").hide();
		$("#overdueTable").hide();
		$("#ticketTable").hide();
		$("#backlogTable").hide();
		$("#escalatedTable").hide();
		$("#reopenedTable").hide();


		var gridDiv = document.querySelector('#unAssignedAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsUnAssigned);

		$.ajax({
			type: "GET",
			url:  "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate + "&location=" + location +"&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var unAssignedData = jsonData;
                     
                       $("#unAssignedTable").show();
					gridOptionsUnAssigned.api.setRowData([]);
					if (!unAssignedData || unAssignedData.length === 0) {
						gridOptionsUnAssigned.api.setRowData([]);
					} else {
						gridOptionsUnAssigned.api.setRowData(unAssignedData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "escalated") {
		$("#escalatedAgGrid").show().empty();
		$("#escalatedTable").show();
		$("#unAssignedTable").hide();
		$("#assignedTable").hide();
		$("#overdueTable").hide();
		$("#backlogTable").hide();
		$("#ticketTable").hide();
		$("#reopenedTable").hide();


		var gridDiv = document.querySelector('#escalatedAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsEscalated);


		$.ajax({
			type: "GET",
			url:  "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate +"&location=" + location + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var escalatedData = jsonData;
                    
                      $("#escalatedTable").show();
					gridOptionsEscalated.api.setRowData([]);
					if (!escalatedData || escalatedData.length === 0) {
						gridOptionsEscalated.api.setRowData([]);
					} else {
						gridOptionsEscalated.api.setRowData(escalatedData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else if (id == "reopened") {
		$("#reopenedAgGrid").show().empty();
		$("#reopenedTable").show();
		$("#escalatedTable").hide();
		$("#unAssignedTable").hide();
		$("#assignedTable").hide();
		$("#overdueTable").hide();
		$("#backlogTable").hide();
		$("#ticketTable").hide();


		var gridDiv = document.querySelector('#reopenedAgGrid');
		new agGrid.Grid(gridDiv, gridOptionsReopened);


		$.ajax({
			type: "GET",
			url:  "manage-dashboard-operational-aggrid-data?id=" + id + "&fromDate=" + fromDate +"&location=" + location + "&toDate=" + toDate + "&organization=" + organization + "&division=" + division,
			async: true,
			success: function(response) {
				if (response.code === "success") {
					var jsonData = JSON.parse(response.body);
					var reopenedData = jsonData;
                      
                      $("#reopenedTable").show();
					gridOptionsReopened.api.setRowData([]);
					if (!reopenedData || reopenedData.length === 0) {
						gridOptionsReopened.api.setRowData([]);
					} else {
						gridOptionsReopened.api.setRowData(reopenedData);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	}
}
// Ag Grid Initialization for Ticket
const columnDefs1 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsTicket = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Backlog
const columnDefs2 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsBacklog = {
	columnDefs: columnDefs2,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Overdue
const columnDefs3 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsOverdue = {
	columnDefs: columnDefs3,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Asssigned
const columnDefs4 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsAssigned = {
	columnDefs: columnDefs4,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Unasssigned
const columnDefs5 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsUnAssigned = {
	columnDefs: columnDefs5,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Escalated
const columnDefs6 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsEscalated = {
	columnDefs: columnDefs6,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

//Ag Grid Initialization for Reopened
const columnDefs7 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'Ticket No',
		field: 'tcktNo',
		width: 200,
		pinned: 'left',
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Ticket Priority',
		field: 'ticketPriority',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Assigned To',
		field: 'assigneTo',
		width: 200,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'

	},
	{
		headerName: 'Raised On'
		, field: 'riseDate',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Complaint Type',
		field: 'tckttype',
		width: 200,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Category',
		field: 'tcktCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Complaint Sub Category',
		field: 'tcktSubCat',
		width: 180,
		cellStyle: { textAlign: 'left' },

	},
	{
		headerName: 'Description',
		field: 'desc',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Asset Id',
		field: 'assetid',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Department',
		field: 'department',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	{
		headerName: 'Raised By',
		field: 'raisedby',
		cellStyle: { textAlign: 'left' },
		width: 180
	},
	 

];

const gridOptionsReopened = {
	columnDefs: columnDefs7,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

function getOperationalFilterData(){
	 operational();
}
function resetOperationalData(){
	 var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#operationalFromDate").val(fromDate);
	$("#operationalToDate").val(toDate);
	getOperationalFilterData();
}