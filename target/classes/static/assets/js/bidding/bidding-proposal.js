//Multiple Document Upload Ends

	$(document).ready(function() {

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#dateId').val($(this).val());
		})

		$('#dateId').blur(function() {
			$("#DateCalendar").val($(this).val());
		})

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar1").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#startDate').val($(this).val());
		})

		$('#startDate').blur(function() {
			$("#DateCalendar1").val($(this).val());
		})

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar2").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#endDate').val($(this).val());
		})

		$('#endDate').blur(function() {
			$("#DateCalendar2").val($(this).val());
		})

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar3").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#rfpDate').val($(this).val());
		})

		$('#rfpDate').blur(function() {
			$("#DateCalendar3").val($(this).val());
		})

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar4").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#cutoffDate').val($(this).val());
		})

		$('#cutoffDate').blur(function() {
			$("#DateCalendar4").val($(this).val());
		})

		$("#collapseOne").show();
		$("#headingOne").show();

		$("#demo").hide();
		agGrid.simpleHttpRequest({
			url : 'view-candidate-view-ajax'
		}).then(function(data) {
			var len = data.length;
			$('#totalCandidate').find('span').html(len);
			gridOptions.api.setRowData(data);

		});

		var gridDiv4 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv4, gridOptions4);
	});

	var columnDefs4 = [ {
		headerName : "Sl.No",
		field : "slnoId1",
	}, {
		headerName : "Activity/Task",
		field : "activity",
		width : 300,
	}, {
		headerName : "Assigned To",
		field : "assignedTo",
		width : 310,
	}, {
		headerName : "Start Date",
		field : "startDate",
		width : 310,
	}, {
		headerName : "End Date",
		field : "endDate",
		width : 310,
	}, {
		headerName : "Attachment",
		field : "attachment",
		width : 310,
	}, {
		headerName : "Version",
		field : "version",
		width : 310,
	}, {
		headerName : "Status",
		field : "status",
		width : 310,
	}, {
		headerName : "Remarks",
		field : "remarks",
		width : 310,
		cellRenderer : function(params) {
			var remarks = params.data.remarks;
			return '<span title="' + remarks + '">' + remarks + '</span>';
		},
	} ];

	const rowData = [ {
		slnoId1 : "1",
		activity : "Technical Proposal",
		assignedTo : "",
		startDate : "",
		endDate : "",
		attachment : "",
		version : "",
		status : "",
		remarks : "",
	}, {
		slnoId1 : "1.1",
		activity : "Draft Version",
		assignedTo : "ABC",
		startDate : "01-Jun-23",
		endDate : "03-Jun-23",
		attachment : "TechProposal.doc",
		version : "1.0",
		status : "Submitted",
		remarks : "Draft Submitted For Review",
	}, {
		slnoId1 : "1.2",
		activity : "Review",
		assignedTo : "XYZ",
		startDate : "04-Jun-23",
		endDate : "04-Jun-23",
		attachment : "TechProposal.doc",
		version : "1.1",
		status : "Reviewed",
		remarks : "Comments Given",
	}, {
		slnoId1 : "1.3",
		activity : "Revised Version",
		assignedTo : "ABC",
		startDate : "05-Jun-23",
		endDate : "05-Jun-23",
		attachment : "TechProposal.doc",
		version : "1.2",
		status : "Submitted",
		remarks : "Comments incorporated",
	}, {
		slnoId1 : "1.4",
		activity : "Review and Approval",
		assignedTo : "Tusar Mohanty",
		startDate : "05-Jun-23",
		endDate : "05-Jun-23",
		attachment : "TechProposal.doc",
		version : "1.2",
		status : "Approved",
		remarks : "reviewed and approved",
	}, {
		slnoId1 : "2",
		activity : "Financial Proposal",
		assignedTo : "",
		startDate : "",
		endDate : "",
		attachment : "",
		version : "",
		status : "",
		remarks : "",
	}, {
		slnoId1 : "2.1",
		activity : "Estimation & Costing",
		assignedTo : "Dayanidhi",
		startDate : "01-Jun-23",
		endDate : "02-Jun-23",
		attachment : "FinProposal.doc",
		version : "1.0",
		status : "Submitted",
		remarks : "Draft Submitted For Review",
	}, {
		slnoId1 : "2.2",
		activity : "Review",
		assignedTo : "Biraja",
		startDate : "03-Jun-23",
		endDate : "03-Jun-23",
		attachment : "FinProposal.doc",
		version : "1.1",
		status : "Reviewed",
		remarks : "Comments Given",
	}, {
		slnoId1 : "2.3",
		activity : "Revised Version",
		assignedTo : "Dayanidhi",
		startDate : "04-Jun-23",
		endDate : "04-Jun-23",
		attachment : "FinProposal.doc",
		version : "1.2",
		status : "Submitted",
		remarks : "comments incorporated",
	}, {
		slnoId1 : "2.4",
		activity : "Final Version Approval",
		assignedTo : "",
		startDate : "04-Jun-23",
		endDate : "04-Jun-23",
		attachment : "FinProposal.doc",
		version : "1.2",
		status : "Approved",
		remarks : "reviewed and approved",
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
	//rowSelection : 'single',
	//onSelectionChanged : onSelectionChanged
	};

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";
		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}

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
				headerName : "Sl. No",
				field : "slnoId",
				cellRenderer : function(params) {
					return '<a href="#" onclick="addId()">'
							+ params.data.slnoId + "</a>";
				},
			},
			{
				headerName : "Date",
				field : "dateId",
				width : 150
			},
			{
				headerName : "RFP No.",
				field : "rpfNo",
			},
			{
				headerName : "RFP Date",
				field : "rfpDate",
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
				field : "prequalificationQuestions",
			}, {
				headerName : 'Clarifications',
				field : "clarifications",
			}, {
				headerName : 'Status',
				field : "status",

			} ];

	const rowData1 = [
	// Existing row data...

	{
		slnoId : "1",
		dateId : "03-May-23",
		rpfNo : "AAGPN5568",
		rfpDate : "01-May-2023",
		cutoffDate : "26-June-2023",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "Civil Work",
		owner : "Mr. Sharaf",
		rfpDocument : "rfp.pdf",
		prequalificationQuestions : "preq.pdf",
		clarifications : "clr.pdf",
		status : "PROPOSAL",
	},

	{
		slnoId : "2",
		dateId : "03-july-23",
		rpfNo : "AAGPN5568",
		rfpDate : "01-July-2023",
		cutoffDate : "26-June-2023",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "painting",
		owner : "Mr. Sharaf",
		rfpDocument : "rfp.pdf",
		prequalificationQuestions : "preq.pdf",
		clarifications : "clr.pdf",
		status : "PROPOSAL",
	},

	{
		slnoId : "3",
		dateId : "03-May-23",
		rpfNo : "AAGPN5568",
		rfpDate : "01-May-2023",
		cutoffDate : "26-June-2023",
		customerName : "Govt of Odisha",
		location : "Cuttack",
		state : "Odisha",
		category : "Manpower",
		subCategory : "Fabrication",
		owner : "Mr. Sharaf",
		rfpDocument : "rfp.pdf",
		prequalificationQuestions : "preq.pdf",
		clarifications : "clr.pdf",
		status : "PROPOSAL",
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
		rowData : rowData1,
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

	/* APPLY FOR REQUISITION ENDS */

	// setup the grid after the page has finished loading
	$(document).ready(function() {

		$("#cancelFridTrial").attr('disabled', true);
		$("#transportvalue").hide();

		$('#delete').attr('disabled', true);

		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = today.getFullYear();

		//today = dd + '-' + mm + '-' + yyyy;

		today = mm + '-' + dd + '-' + yyyy;

		$('#fromDate').val(today);
		$('#toDate').val(today);
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);

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

	function cancelbtn() {
		$('#reqDltBtn').attr('disabled', true);
		$("#myGrid").show();
		$("#btn1").hide();
		$("#demo").hide();
		$("#hideTbl").show();
		$("#buttonDetails").show();
	}

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

	function deleteCustonClick() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += selectedRow.budgetId;
		});
		var item = {};
		item.budgetId = selectedRowsString;
		//alert(JSON.stringify(item));
		$.ajax({
			type : "POST",
			url : "manage-budget-delete",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(item),
			success : function(response) {
				if (response.message == "Success") {
					cancelModalBtn();
					location.reload();
					agGrid.simpleHttpRequest({
						url : "manage-budget-view"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});

				}
			}

		});
		$('#delete').attr("disabled", true);
	}

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function addId() {
		$("#demo").show();
		$("#myGrid").show();
		$("#myGrid1").show();
		$("#addId").show();
		$("#addId1").show();
		$("#searchRowDiv").show();
		$("#collapseOne").show();
		$("#headingOne").show();
		$("#upperOne").show();
	}

	function addId1() {
		$("#demo").show();
		$("#myGrid").show();
		$("#myGrid1").show();
		$("#addId1").hide();
	}

	function cancel() {
		location.reload();
	}

	/* function saveTableData() {
		var event = {};
		var valid = true;
		event.budgetId = $("#budgetId").val();
		event.item = $("#item").val();
		event.budgetCategory = $("#budgetCategory").val();
		event.uoms = $("#uoms").val();
		event.unit = $("#unit").val();

		console.log(event)
		if (valid) {
			$.ajax({
				type : "POST",
				url : "manage-budget-add",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(event),
				success : function(response) {
					if (response.message == "Success") {
						//$('.loader').hide();
						$("#messageParagraph").text("Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						location.reload();

					}

				},
				error : function(response) {
					console.log(response);

				}
			});
		}

	} */

	function saveTableData() {
		var item = {};
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		gridOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		item.dateId = $("#dateId").val();
		item.rpfNo = $("#rfpno").val();
		item.rfpDate = $("#rfpDate").val();
		item.cutoffDate = $("#cutoffDate").val();
		item.customerName = $("#customerName").val();
		item.location = $("#location").val();
		item.state = $("#state").val();
		item.category = $("#category").val();
		item.subCategory = $("#subCategory").val();
		item.owner = $("#owner").val();
		item.rfpDocument = $("#rfpDocument").val();
		item.prequalificationQuestions = $("#PreQualification").val();
		item.clarifications = $("#clarification").val();
		item.status = $("#status").val();

		item.slnoId = data;
		var datas = [];
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
	}

	function saveTableData1() {

		var item = {};
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		gridOptions4.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		item.activity = $("#activity").val();
		item.assignedTo = $("#assignedTo").val();
		item.startDate = $("#startDate").val();
		item.endDate = $("#endDate").val();
		item.attachment = $("#attachment").val();
		item.version = $("#version").val();
		item.status1 = $("#status1").val();
		item.remarks = $("#remarks").val();

		item.slnoId1 = data;
		var datas = [];
		if (rowEdit) {
			var rowNode = gridOptions4.api.getRowNode(rowEdit);
			rowNode.setData(item);
		} else {

			gridOptions4.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
			});

			datas.push(item)
			gridOptions4.api.setRowData(datas);
		}
	}

	function mySideNavFun() {
		document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";
		document.getElementById("main").style.width = "75%";

		document.getElementById("addId1").style.cssText = "display: none";
		document.getElementById("delete1").style.cssText = "display: none";
	}

	function mySideNavFun1() {
		document.getElementById("mySideNav1").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px; z-index: 10;";
		document.getElementById("main").style.width = "75%";

		document.getElementById("addId1").style.cssText = "display: none";
		document.getElementById("delete1").style.cssText = "display: none";

	}

	function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("main").style.width = "100%";

		document.getElementById("addId1").style.cssText = "display: auto";
		document.getElementById("delete1").style.cssText = "display: auto";
	}

	function closeNavFun1() {
		document.getElementById("mySideNav1").style.width = "0";
		document.getElementById("main").style.width = "100%";

		document.getElementById("addId1").style.cssText = "display: auto";
		document.getElementById("delete1").style.cssText = "display: auto";
	}

	function submitNav() {
		location.reload();
	}