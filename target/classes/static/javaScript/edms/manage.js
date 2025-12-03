
	$(document).ready(function() {
		//$("#user").show();
		$("#user1").hide();
		$("#user2").hide();
		$("#user3").hide();
		$("#demo").hide();
		$("#user4").hide();
		$("#accessUser1").hide();
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		 var gridDiv = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv, gridOptions1);  
		var gridDiv = document.querySelector('#myGrid3');
		new agGrid.Grid(gridDiv, gridOptions2); 
		var gridDiv = document.querySelector('#myGrid4');
		new agGrid.Grid(gridDiv, gridOptions3); 
		var gridDiv = document.querySelector('#myGrid5');
		new agGrid.Grid(gridDiv, gridOptions4); 
		var gridDiv = document.querySelector('#myGrid6');
		new agGrid.Grid(gridDiv, gridOptions5); 
		
		
		
		

		

		
		
  
	});
	
	
	var columnDefs = [
		{
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true

		},
		{
			headerName : "Eployee ID",
			field : "eId",
			width:150,
		}, {
			headerName : "Login Id",
			field : "logId",
			width:200,
		}, {
			headerName : "Name",
			field : "name",
			width:200,
		}, {
			headerName : "Department",
			field : "department",
			width:200,
		}, {
			headerName : 'Designation',
			field : "designation",
			width:200,
		}, {
			headerName : "Status",
			field : "status",
			width:200,
			
		},];
	 var columnDefs1 = [
		{
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true

		},
		{
			headerName : "Group Name",
			field:"groupname",
			width:250,
		}, {
			headerName : "Description",
			field:"description",
			width:250,
		}, {
			headerName : "Created By",
			field:"created_by",
			width:250,
		}, {
			headerName : "Created Date",
			field:"created_date",
			width:250,
		}, {
			headerName : 'Status',
			field:"status",
			width:200,
		}, ];
	 
	 var columnDefs2 = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true

			},
			{
				headerName : "Workflow ID",
				field:"wfId",
				width:250,
			}, {
				headerName : "Workflow Name",
				field:"workflowName",
				width:250,
			}, {
				headerName : "Description",
				field:"description",
				width:250,
			}, {
				headerName : "Created by",
				field:"created_by",
				width:250,
			}, {
				headerName : 'Status',
				field:'status',
				width:250,
			}, ];
	 var columnDefs3 = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true

			},
			{
				headerName : "User Id",
				field:"uId",
				width:400,
			}, {
				headerName : "Access",
				field:"access",
				width:400,
			}, {
				headerName : "Type",
				field:"type",
				width:400,
			}, ];
	 var columnDefs4 = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true

			},
			{
				headerName : "Name",
				field:"name",
				width:250,
			}, {
				headerName : "Description",
				field:"desc",
				width:250,
			}, {
				headerName : "Type",
				field:"type",
				width:250,
			}, {
				headerName : "Multiple",
				field:"multiple",
				width:250,
			}, {
				headerName : 'Actions',
				field:"actions",
				width:250,
			}, ];
	 var columnDefs5 = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true

			},
			{
				headerName : "Time",
				field:"time",
				width:250,
			}, {
				headerName : "User",
				field:"user",
				width:250,
			}, {
				headerName : "Object type",
				field:"objectType",
				width:250,
			}, {
				headerName : "Object",
				field:"object",
				width:250,
			}, {
				headerName : 'Action',
				field:"action",
				width:250,
			}, ];

// let the grid know which columns and what data to use
var rowdata1=[
	{
	    eId: "1001",
	    logId: "john.doe",
	    name: "John Doe",
	    department: "Engineering",
	    designation: "Software Engineer",
	    status: "Active",
	  },
	  {
	    eId: "1002",
	    logId: "jane.smith",
	    name: "Jane Smith",
	    department: "Marketing",
	    designation: "Marketing Manager",
	    status: "Active",
	  },
	  {
	    eId: "1003",
	    logId: "alex.johnson",
	    name: "Alex Johnson",
	    department: "Design",
	    designation: "Graphic Designer",
	    status: "Inactive",
	  },
	  {
	    eId: "1004",
	    logId: "emily.brown",
	    name: "Emily Brown",
	    department: "Support",
	    designation: "Customer Support Representative",
	    status: "Active",
	  },
	  {
	    eId: "1005",
	    logId: "michael.williams",
	    name: "Michael Williams",
	    department: "Finance",
	    designation: "Financial Analyst",
	    status: "Inactive",
	  },
];
var gridOptions = {
	columnDefs : columnDefs,
	rowData:rowdata1,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
};
 var rowdata2=[
	 {
		    groupname: "Development Team",
		    description: "Group responsible for software development",
		    created_by: "User A",
		    created_date: "2023-08-31",
		    status: "Active",
		  },
		  {
		    groupname: "Marketing Team",
		    description: "Group responsible for marketing campaigns",
		    created_by: "User B",
		    created_date: "2023-08-30",
		    status: "Active",
		  },
		  {
		    groupname: "Design Team",
		    description: "Group responsible for graphic design",
		    created_by: "User C",
		    created_date: "2023-08-29",
		    status: "Inactive",
		  },
		  {
		    groupname: "Support Team",
		    description: "Group responsible for customer support",
		    created_by: "User D",
		    created_date: "2023-08-28",
		    status: "Active",
		  },
		  {
		    groupname: "Finance Team",
		    description: "Group responsible for financial operations",
		    created_by: "User E",
		    created_date: "2023-08-27",
		    status: "Inactive",
		  },
 ];
var gridOptions1 = {
	columnDefs : columnDefs1,
	rowData:rowdata2,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
}; 

var rowdata3=[
	{
	    wfId: "wf123",
	    workflowName: "Approval Workflow",
	    description: "Workflow for document approval",
	    created_by: "User A",
	    status: "Active",
	  },
	  {
	    wfId: "wf456",
	    workflowName: "Review Workflow",
	    description: "Workflow for content review",
	    created_by: "User B",
	    status: "Inactive",
	  },
	  {
	    wfId: "wf789",
	    workflowName: "Task Workflow",
	    description: "Workflow for task management",
	    created_by: "User C",
	    status: "Active",
	  },
	  {
	    wfId: "wf101",
	    workflowName: "Bug Tracking Workflow",
	    description: "Workflow for bug tracking",
	    created_by: "User D",
	    status: "Active",
	  },
	  {
	    wfId: "wf202",
	    workflowName: "Reporting Workflow",
	    description: "Workflow for generating reports",
	    created_by: "User E",
	    status: "Inactive",
	  },

];
var gridOptions2 = {
	columnDefs : columnDefs2,
	rowData:rowdata3,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
}; 
var rowdata4=[
	{
	    uId: "user123",
	    access: "Read",
	    type: "Admin",
	  },
	  {
	    uId: "user456",
	    access: "Write",
	    type: "Editor",
	  },
	  {
	    uId: "user789",
	    access: "Read",
	    type: "Viewer",
	  },
	  {
	    uId: "user101",
	    access: "Read",
	    type: "Viewer",
	  },
	  {
	    uId: "user202",
	    access: "Write",
	    type: "Editor",
	  },

];
var gridOptions3 = {
	columnDefs : columnDefs3,
	rowData:rowdata4,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
};

var rowdata5=[
	{
	    name: "Item 1",
	    desc: "Description for Item 1",
	    type: "Type A",
	    multiple: false,
	    actions: "Edit, Delete",
	  },
	  {
	    name: "Item 2",
	    desc: "Description for Item 2",
	    type: "Type B",
	    multiple: true,
	    actions: "Edit, Delete",
	  },
	  {
	    name: "Item 3",
	    desc: "Description for Item 3",
	    type: "Type C",
	    multiple: true,
	    actions: "Edit, Delete",
	  },
	  {
	    name: "Item 4",
	    desc: "Description for Item 4",
	    type: "Type A",
	    multiple: false,
	    actions: "Edit, Delete",
	  },
	  {
	    name: "Item 5",
	    desc: "Description for Item 5",
	    type: "Type B",
	    multiple: true,
	    actions: "Edit, Delete",
	  },

];
var gridOptions4 = {
	columnDefs : columnDefs4,
	rowData:rowdata5,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
}; 
var rowdata6=[ {
    time: "2023-08-31 10:15 AM",
    user: "user123",
    objectType: "Document",
    object: "Document_A",
    action: "View",
  },
  {
    time: "2023-08-31 11:30 AM",
    user: "user456",
    objectType: "Image",
    object: "Image_XYZ",
    action: "Edit",
  },
  {
    time: "2023-08-31 2:45 PM",
    user: "user789",
    objectType: "Video",
    object: "Video_123",
    action: "Play",
  },
  {
    time: "2023-08-31 4:20 PM",
    user: "user101",
    objectType: "File",
    object: "File_ABC",
    action: "Download",
  },
  {
    time: "2023-08-31 5:15 PM",
    user: "user202",
    objectType: "Note",
    object: "Note_456",
    action: "Comment",
  },
];
var gridOptions5 = {
	columnDefs : columnDefs5,
	rowData:rowdata6,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChanged,
}; 

//function for row select parents
 
function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').attr('disabled', false);
		$("#reject").attr('disabled', false);
		$("#approve").attr('disabled', false);
	} else {
		$('#delete').attr('disabled', true);
		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
	}
} 

//function for ag grid 1
function openNav1() {
	
	document.getElementById("mySidenav1").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";
	document.getElementById("myGrid").style.width = "75%";
	
	$("#myGrid").show();
	$('#add1').attr('disabled', false);
	$("#reqDltBtn").attr('disabled', true);
	$("#add1").hide();
	$("#reqDltBtn").hide();
}
function closeNav1() {
	document.getElementById("mySidenav1").style.width = "0";
	document.getElementById("myGrid").style.width = "100%";
	 $("#myGrid").show();
	$('#add1').attr('disabled', false);
	$("#reqDltBtn").attr('disabled', true);
	$("#add1").show();
	$("#reqDltBtn").show(); 
}

 // for ag grid 2
function openNav2() {
	
	document.getElementById("mySidenav2").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";
	document.getElementById("myGrid2").style.width = "75%";
	
	$("#myGrid2").show();
	$('#add2').attr('disabled', false);
	$("#reqDltBtn2").attr('disabled', true);
	$("#add1").hide();
	$("#reqDltBtn2").hide();
	$("#add2").hide();
}
function closeNav2() {
	document.getElementById("mySidenav2").style.width = "0";
	document.getElementById("myGrid2").style.width = "100%";
	 $("#myGrid2").show();
	$('#add1').attr('disabled', false);
	$("#reqDltBtn2").attr('disabled', true);
	$("#add1").show();
	$("#reqDltBtn2").show();
	$("#add2").show();
}
function openNav3() {
	
	document.getElementById("mySidenav3").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";
	document.getElementById("myGrid5").style.width = "75%";
	$("#ttbtn4").hide();
	$("#reqDltBtn3").attr('disabled', true);
	
	/* $("#myGrid2").show();
	$('#add2').attr('disabled', false);
	$("#reqDltBtn2").attr('disabled', true);
	$("#add1").hide();
	$("#reqDltBtn2").hide(); */
}
function openNav4(){
	document.getElementById("mySidenav4").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";
	document.getElementById("myGrid6").style.width = "75%";
	$("#reqDltBtn4").attr('disabled', true);
	$("#ttbtn6").hide();
	
	
}
function closeNav3() {
	document.getElementById("mySidenav3").style.width = "0";
	document.getElementById("myGrid5").style.width = "100%";
	$("#ttbtn4").show();
	/*  $("#myGrid2").show();
	$('#add1').attr('disabled', false);
	$("#reqDltBtn2").attr('disabled', true);
	$("#add1").show();
	$("#reqDltBtn2").show(); */ 
}
function closeNav4(){
	document.getElementById("mySidenav4").style.width = "0";
	document.getElementById("myGrid6").style.width = "100%";
	$("#ttbtn6").show();
}
function openNav5(){
	document.getElementById("mySidenav5").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:400px;";
	document.getElementById("myGrid4").style.width = "75%";
	$("#reqDltBtn5").attr('disabled', true);
	$("#ttbtn5").hide();
	
}
function closeNav5(){
	document.getElementById("mySidenav5").style.width = "0";
	document.getElementById("myGrid4").style.width = "100%";
	$("#ttbtn5").show();
}
 function userOpen(){
	//location.reload();
	$("#user").show();
	$("#myGrid").show();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#tdiv2").hide();
	$("#totalReq2").hide();
	$("#totalReq2").show();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#user3").hide();
	$("#myGrid5").hide();
	$("#user4").hide();
	$("#myGrid6").hide();
	$("#tv5").hide();
	$("#tdiv4").hide();
	$("#demo").hide();
	$("#myGrid4").hide();
	$("#accessUser1").hide();
}
 function userGroupOpen(){
	//location.reload();
	$("#totalReq2").show();
	$("#user").hide();
	$("#user1").show();
	$("#myGrid2").show();
	$("#myGrid").hide();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#user3").hide();
	$("#myGrid5").hide();
	$("#user4").hide();
	$("#myGrid6").hide();
	$("#tdiv3").hide();
	$("#tv5").hide();
	$("#tdiv4").hide();
	$("#demo").hide();
	$("#myGrid4").hide();
	$("#accessUser1").hide();
} 
function userWorkflowOpen(){
	
	$("#user").hide();
	$("#myGrid").hide();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#user2").show();
	$("#myGrid3").show();
	$("#demo").hide();
	$("#user3").hide();
	$("#myGrid5").hide();
	$("#user4").hide();
	$("#myGrid6").hide();
	$("#tdiv3").hide();
	$("#tv5").hide();
	$("#tdiv4").hide();
	$("#myGrid4").hide();
	$("#accessUser1").hide();
}
function userMetadataOpen(){
	
	$("#user").hide();
	$("#myGrid").hide();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#demo").hide();
	$("#myGrid4").hide();
	$("#user3").show();
	$("#myGrid5").show();
	$("#user4").hide();
	$("#myGrid6").hide();
	$("#tdiv3").hide();
	$("#tv5").hide();
	$("#tdiv4").show();
	$("#totalReq4").show();
	$("#accessUser1").hide();

}
function accessControlOpen(){
	$("#user").hide();
	$("#myGrid").hide();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#tdiv4").hide();
	$("#totalReq4").hide();
	$("#user3").hide();
	$("#myGrid5").hide();
	$("#user4").hide();
	$("#myGrid6").hide();
	$("#tdiv3").hide();
	$("#tv5").hide();
	$("#demo").hide();
	$("#myGrid4").hide();
	//$("#myModal12").modal('show');
	$("#accessUser1").show();
	
}
function auditLogOpen(){
	$("#user").hide();
	$("#myGrid").hide();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#tdiv4").hide();
	$("#totalReq4").hide();
	$("#user3").hide();
	$("#myGrid5").hide();
	$("#demo").hide();
	$("#myGrid4").hide();
	$("#user4").show();
	$("#myGrid6").show();
	$("#tdiv3").hide();
	$("#tv5").show();
	$("#accessUser1").hide();
	
	
}
function newPlan(){
	
	$("#user").hide();
	$("#myGrid").hide();
	$("#user1").hide();
	$("#myGrid2").hide();
	$("#user2").hide();
	$("#myGrid3").hide();
	$("#demo").show();
	$("#myGrid4").show();
	$("#tdiv3").hide();
	$("#user4").hide();
	$("#tv5").hide();
	$("#tdiv4").hide();
	$("#accessUser1").hide();
	
}

 function cancel(){
	 
	  /* document.getElementById("demo").style.width = "0";
		document.getElementById("myGrid4").style.width = "100%"; */ 
	 
	 $("#demo").hide(); 
		$("#user2").show();	
		$("#myGrid3").show();
		
		$("#user").hide();
		$("#myGrid").hide();
		$("#user1").hide();
		$("#myGrid2").hide();
		//$("#demo").show();
		$("#myGrid4").show();
		$("#tdiv3").hide();
		$("#user4").hide();
		$("#tv5").hide();
		$("#tdiv4").hide();
		$("#accessUser1").hide();
 }