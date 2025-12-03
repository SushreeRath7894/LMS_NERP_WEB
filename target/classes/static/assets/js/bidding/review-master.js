$(document).ready(
			function() {
				
				
				var dateFormat = localStorage.getItem("dateFormat");
				$("#DateCalendar").datetimepicker({
					format : dateFormat,
					closeOnDateSelect : true,
					timepicker : false,
				}).on("change", function() {
					$('#cutoffDate').val($(this).val());
				})

				$('#cutoffDate').blur(function() {
					$("#DateCalendar").val($(this).val());
				})
				
				$("#collapseOne").show();
				$("#headingOne").show();

				$("#demo").hide();
				$("#main1").hide();

				agGrid.simpleHttpRequest({
					url : 'view-candidate-view-ajax'
				}).then(function(data) {
					var len = data.length;
					$('#totalCandidate').find('span').html(len);
					gridOptions.api.setRowData(data);
				});

				var columnDefs4 = [ {
					headerName : "Task/Activity",
					field : "activity",
					width : 300,
				}, {
					headerName : "Assigned To",
					field : "planned",
					width : 200,
					type : "rightAligned"
				}, {
					headerName : "Due Date",
					field : "forecast",
					width : 200,
					type : "rightAligned"
				}, {
					headerName : "Status",
					field : "variance",
					width : 200,
					type : "rightAligned",
				}, {
					headerName : "Attachment",
					field : "action",
					width : 200,
					type : "rightAligned",
				}, {
					headerName : "Remarks",
					field : "remarks",
					width : 180,
					type : "rightAligned"
				} ];

				const rowData = [ {
					activity : "Review Project Plan and Specification",
					planned : "Mr.Tusar Mohanty",
					forecast : "05-Jun-23",
					variance : "Done",
					action : "Mydoc.doc",
					remarks : "Submitted"
				}, {
					activity : "Pre-Qualification Questions",
					planned : "Mr.Tusar Mohanty",
					forecast : "05-Jun-23",
					variance : "Done",
					action : "Mydoc.doc",
					remarks : "Submitted"
				}, {
					activity : "Submission for Clarification",
					planned : "Mr.Tusar Mohanty",
					forecast : "05-Jun-23",
					variance : "Done",
					action : "Mydoc.doc",
					remarks : "Submitted"
				} ];

				var gridOptions4 = {
					columnDefs : columnDefs4,
					rowData : rowData,
					defaultColDef : {
						sortable : true,
						filter : true,
						resizable : true,
						width : 200,
						height : 20
					},
				};
				var gridDiv4 = document.querySelector('#activity');
				new agGrid.Grid(gridDiv4, gridOptions4);
				gridOptions4.api.setRowData(rowData);

				var columnDefs5 = [
						{
							headerName : "Sl. No.",
							field : "slno",
							width : 100,
						},
						{
							headerName : "Activity/Task",
							field : "activity",
							width : 150,
							type : "rightAligned"
						},
						{
							headerName : "Assigned To",
							field : "assigned",
							width : 150,
							type : "rightAligned"
						},
						{
							headerName : "Start Date",
							field : "startdate",
							width : 150,
							type : "rightAligned",
						},
						{
							headerName : "End Date",
							field : "enddate",
							width : 150,
							type : "rightAligned",
						},
						{
							headerName : "Status",
							field : "status",
							width : 150,
							type : "rightAligned"
						},
						{
							headerName : "Remarks",
							field : "remarks",
							width : 150,

							cellRenderer : function(params) {
								return '<a href="#" onclick="remarks()">'
										+ params.data.remarks + "</a>";
							},
							type : "rightAligned"
						} ];

				const rowData1 = [ {
					slno : "1",
					activity : "Technical Proposal",
					assigned : "Shardha Panda",
					startdate : "01-Jun-23",
					enddate : "02-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "1.1",
					activity : "Writing",
					assigned : "Aman Kumar",
					startdate : "01-Jun-23",
					enddate : "03-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "1.2",
					activity : "Review",
					assigned : "Manmayee Rath",
					startdate : "04-Jun-23",
					enddate : "04-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "1.3",
					activity : "Final Version Approval",
					assigned : "Tusar Mohanty",
					startdate : "05-Jun-23",
					enddate : "05-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "2",
					activity : "Financial Proposal",
					assigned : "Tusar Mohanty",
					startdate : "05-Jun-23",
					enddate : "05-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "2.1",
					activity : "Estimation and Costing",
					assigned : "Dayanidhi",
					startdate : "01-Jun-23",
					enddate : "02-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "2.2",
					activity : "Review",
					assigned : "Mr.Biraja",
					startdate : "03-Jun-23",
					enddate : "03-Jun-23",
					status : "Active",
					remarks : "completed"
				}, {
					slno : "2.3",
					activity : "Final Version Proposal",
					assigned : "Mr.Biraja",
					startdate : "04-Jun-23",
					enddate : "04-Jun-23",
					status : "Active",
					remarks : "completed"
				} ];

				var gridOptions5 = {
					columnDefs : columnDefs5,
					rowData : rowData1,
					defaultColDef : {
						sortable : true,
						filter : true,
						resizable : true,
						width : 200,
						height : 50
					},
				};
				var gridDiv5 = document.querySelector('#lastGrid');
				new agGrid.Grid(gridDiv5, gridOptions5);

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
				headerName : "Project ID",
				field : "projectId",
				hide : true,
				width : 150,

				cellRenderer : function(params) {
					return '<a instrno="editId" onclick=editId("'
							+ params.data.projectId
							+ '") href="javascript:void(0)">'
							+ params.data.projectId + '</a>';
				},
				cellStyle : {
					textAlign : 'center'
				}
			},
			{
				headerName : "Sl. No",
				field : "slno",
			},
			{
				headerName : "Date",
				field : "creationDate",
				width : 150
			},
			{
				headerName : "RFP No.",
				field : "rpfno",
			},
			{
				headerName : "RFP Date",
				field : "rfpdate",
				width : 150
			},
			{
				headerName : "CutOff Date",
				field : "cutoffDate",
				width : 150
			},
			{
				headerName : "Customer Name",
				field : "customerName",
			},
			{
				headerName : 'Location',
				field : "location",
				type : 'rightAligned',
				width : 130

			},
			{
				headerName : 'State',
				field : "state",
			},
			{
				headerName : 'Category',
				field : "category",
			},
			{
				headerName : 'Sub-Category',
				field : "subCategory",
			},
			{
				headerName : 'Owner',
				field : "owner",
			},
			{
				headerName : 'RFP Document',
				field : "rfpDocument",
				cellRenderer : function(params) {
					var s = "";
					s = ' <a href="#" class="grn-btn" onclick="downloadFile()"><i class="ti ti-download"></i></a>';
					return s;
				},
			}, {
				headerName : 'Pre-Qualification Questions',
				field : "pqq",
			}, {
				headerName : 'Clarifications',
				field : "clarifications",
			}, {
				headerName : 'Status',
				field : "status",

			} ];

	const rowData = [
	// Existing row data...

	{
		slno : "PP23/0001",
		creationDate : "03-May-2023",
		rpfno : "RFP001",
		rfpdate : "01-May-2023",
		cutoffDate : "26-Jun-23",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "civil work",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review"
	},

	{
		slno : "PP23/0002",
		creationDate : "03-May-2023",
		rpfno : "RFP002",
		rfpdate : "01-May-2023",
		cutoffDate : "26-Jun-23",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "painting",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review"
	},

	{
		slno : "PP23/0003",
		creationDate : "03-May-2023",
		rpfno : "RFP003",
		rfpdate : "01-May-2023",
		cutoffDate : "26-Jun-23",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "fabrication",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review"
	},

	// Add more data as needed
	];

	function addCandidate() {

		$('#hideTbl').hide();
		$('#buttonDetails').hide();
		$('#myGrid').hide();
		$('#demo').show();
		$("#btn1").show();
		$('#candidateId').val("");

		$("#collapseOne").collapse({
			toggle : false
		}).collapse('show');

	}

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	var gridOptions = {
		columnDefs : columnDefs,
		rowData : rowData,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'multiple',
		onSelectionChanged : onSelectionChanged
	};

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	function downloadFile() {
		window.open("../assets/img/Service.pdf");

	}

	$(document).ready(function() {

		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = today.getFullYear();

		today = mm + '-' + dd + '-' + yyyy;

		$('#fromDate').val(today);
		$('#toDate').val(today);
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);

		var dateFormat = localStorage.getItem("dateFormat");
		$("#fromDateCalendar").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		})

		$('#fromDate').blur(function() {
			$("#fromDateCalendar").val($(this).val());
		})

		//     date format TO date

		var dateFormat = localStorage.getItem("dateFormat");
		$("#toDateCalendar").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#toDate').val($(this).val());
		})

		$('#toDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		})

		agGrid.simpleHttpRequest({
			url : "manage-budget-view",
		}).then(function(data) {
			var len = data.length;
			$('#totalReq').find('span').html(len);
			if (len > 0) {
				gridOptions.api.setRowData(data);
			} else {
				gridOptions.api.setRowData();
			}
			console.log("manmayee", data)
		});
	});

	function search() {
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var fromDate = fromDate.split('-');
		var fromDate = fromDate[2] + '-' + fromDate[1] + '-' + fromDate[0];
		var toDate = toDate.split('-');
		var toDate = toDate[2] + '-' + toDate[1] + '-' + toDate[0];
		var vendor = $("#vendorCode").val();

		agGrid.simpleHttpRequest(
				{
					url : "processingcost-setdateview?fromDate=" + fromDate
							+ "&toDate=" + toDate + "&vendor=" + vendor,
				}).then(function(data) {
			var len = data.length;
			$('#totalReq').find('span').html(len);
			if (len > 0) {
				gridOptions.api.setRowData(data);
			} else {
				gridOptions.api.setRowData();
			}
		});

	}

	function onSelectionChanged() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var rowCount = 0;
		selectedRows.forEach(function(selectedRow, index) {
			rowCount = rowCount + 1;
		});

		if (rowCount > 0) {

			$("#reject").attr('disabled', false);
			$("#approve").attr('disabled', false);
		} else {

			$("#reject").attr('disabled', true);
			$("#approve").attr('disabled', true);
		}

	}

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function addId() {

		$("#demo").show();
		$("#myGrid").hide();
		$("#addId").hide();
		$("#searchRowDiv").hide();
		$("#collapseOne").show();
		$("#headingOne").show();

	}

	function ProceedID() {
		$("#demo").show();
		$("#main1").show();
		$("#main").hide();
		$("#myGrid").hide();
		$("#lastGrid").show();
		$("#addId").hide();
		$("#searchRowDiv").hide();
		$("#collapseOne").hide();
		$("#headingOne").show();
		$("#remarksDiv").hide();
	}

	function remarks() {
		$("#demo").hide();
		$("#child").hide();
		$("#remarksDiv").show();
	}

	function SubmitId2() {
		location.reload();
	}

	function cancelID() {
		location.reload();
	}