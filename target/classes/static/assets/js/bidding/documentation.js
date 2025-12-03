//Multiple Document Upload Ends

	$(document).ready(function() {
		$("#demo").hide();
		$("#demo1").show();
		$("#myGrid1").show();
		$("#child").show();
		$("#upperOne1").show();

		$('.docTblCls').on('click', '.rmv1', function() {
			openDeleteConfirm();
			var value = $(this).parent("div").attr("id");
			$("#dltValue").val(value);
		});

		agGrid.simpleHttpRequest({
			url : 'view-candidate-view-ajax'
		}).then(function(data) {
			var len = data.length;
			$('#totalCandidate').find('span').html(len);
			gridOptions.api.setRowData(data);

		});

	});

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";
		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}

	var columnDefs = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Sl No.",
		field : "slno",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : 'Date',
		field : "date",
		type : 'date',
		width : 130,
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "RFP No.",
		field : "rfpno",
		cellRenderer : function(params) {
			return '<span style="color: blue;">' + params.value + '</span>';
		},
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "RFP Date",
		field : "rfpdate",
		width : 150,
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : 'Cut Off Date',
		field : "cutoff",
		type : 'rightAligned',
		width : 130,
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Customer Name",
		field : "custName",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Location",
		field : "loc",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "State",
		field : "state",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Category",
		field : "category",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Sub-Category",
		field : "subCategory",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Owner",
		field : "owner",
		cellStyle : {
			textAlign : 'center'
		},
		width : 150
	}, {
		headerName : "RFP Documents",
		field : "rfpdocs",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : 'Pre-Qualification Ques.',
		field : "preQualQues",
		type : 'rightAligned',
		cellStyle : {
			textAlign : 'center'
		},
		width : 130
	}, {
		headerName : 'Clarification',
		field : "clarity",
		width : 150,
		cellStyle : {
			textAlign : 'center'
		}

	}, {
		headerName : 'Status',
		field : "status",
		cellStyle : {
			textAlign : 'center'
		},
		width : 150,

	}, {
		headerName : 'Created By',
		field : "createdBy",
	}, {
		headerName : 'Created On',
		field : "createdOn",
	}, {
		headerName : 'Updated By',
		field : "updatedBy",
	}, {
		headerName : 'Updated On',
		field : "updatedOn",
	} ];
	const rowData = [ {
		slno : "1",
		creationDate : "03-May-2023",
		rfpno : "RFP001",
		rfpdate : "01-May-2023",
		cutoff : "26-Jun-23",
		custName : "Govt of Odisha",
		loc : "Cuttack",
		state : "Odisha",
		category : "Civil",
		subCategory : "Renovation",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review",
		date : "2024-05-12"
	},

	{
		slno : "2",
		creationDate : "06-May-2023",
		rfpno : "RFP002",
		rfpdate : "01-May-2023",
		cutoff : "26-Jun-23",
		custName : "Govt of Odisha",
		loc : "Cuttack",
		state : "Odisha",
		category : "Civil",
		subCategory : "Renovation",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review",
		date : "2024-05-12"
	},

	{
		slno : "3",
		creationDate : "13-May-2023",
		rfpno : "RFP003",
		rfpdate : "01-May-2023",
		cutoff : "26-Jun-23",
		custName : "Govt of Odisha",
		loc : "Cuttack",
		state : "Odisha",
		category : "Civil",
		subCategory : "Renovation",
		owner : "Mr.Sharaf",
		rfpDocument : "",
		pqq : "",
		clarifications : "",
		status : "Review",
		date : "2024-05-12"
	}, ];

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

	//child table
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
				headerName : "Sl No.",
				field : "slno1",
				cellStyle : {
					textAlign : 'center'
				}
			},
			{
				headerName : 'Check',
				field : "check",
				type : 'rightAligned',
				width : 100,
				cellStyle : {
					textAlign : 'center'
				}
			},
			{
				headerName : "Requirment",
				field : "requirment",
				width : 250,

			},
			{
				headerName : "Document",
				field : "doc",
				width : 250,

			},
			{
				headerName : 'Attachment',
				field : "attach",
				width : 150,
				cellRenderer : function(params) {
					var s = "";
					s = ' <a href="#" class="grn-btn" onclick="downloadFile()"><i class="ti ti-download"></i></a>';
					return s;
				},

			},
			{
				headerName : 'Copy',
				field : "copy",
				cellStyle : {
					textAlign : 'center'
				},
				width : 150,
				cellRenderer : function(params) {
					return '<a href="#" onclick="ClickCopy()" style="color: blue;">'
							+ params.data.copy + "</a>";
				},

			}, {
				headerName : 'Remarks',
				field : "remarks",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}
			}, {
				headerName : 'Created By',
				field : "createdBy",
			}, {
				headerName : 'Created On',
				field : "createdOn",
			}, {
				headerName : 'Updated By',
				field : "updatedBy",
			}, {
				headerName : 'Updated On',
				field : "updatedOn",
			} ];

	const rowData1 = [

			{
				slno1 : 1,
				check : true,
				requirment : "Legal entry",
				doc : "Certificate of Incorporation",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			// Second row
			{
				slno1 : 2,
				check : true,
				requirment : "Legal entry",
				doc : "GST Registration & PAN",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},

			{
				slno1 : 3,
				check : true,
				requirment : "GST Registration & PAN",
				doc : "Audited Balance sheet and Profit & Loss account",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},

			{
				slno1 : 4,
				check : true,
				requirment : "Sales Turn Over",
				doc : "Statutory Auditor's Certificate",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 5,
				check : true,
				requirment : "Net Worth",
				doc : "Statutory Auditor's Certificate",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 6,
				check : true,
				requirment : "Technical Capability",
				doc : "Completion Certificates from the client",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 7,
				check : true,
				requirment : "Technical Capability",
				doc : "Work Order + Self Certificate of Completion (Certified by the Statutory Auditor) ",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 8,
				check : true,
				requirment : "Technical Capability",
				doc : "Work Order + Phase Completion Certificate from the client",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 9,
				check : true,
				requirment : "Resources",
				doc : "Copy of the latest EPF deposit challan showing the number of subscribers ",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 10,
				check : true,
				requirment : "Consortium",
				doc : "Consortium document",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 11,
				check : true,
				requirment : "Loacal Existence of the company",
				doc : "Trade License/ Leased Agreement etc./ Declaration",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 12,
				check : true,
				requirment : "Net Worth",
				doc : "Statutory Auditors Certificate",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 13,
				check : true,
				requirment : "Black Listing",
				doc : "Self Declaration",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},
			{
				slno1 : 14,
				check : true,
				requirment : "EMD & Document Fee",
				doc : "Tender Fees Bank Draft only and EMD in shape of Bank Draft / Bank Guarantee ",
				attach : "",
				copy : "Click Here",
				remarks : ""
			}, {
				slno1 : 15,
				check : true,
				requirment : "Net Worth",
				doc : "Statutory Auditors Certificate",
				attach : "",
				copy : "Click Here",
				remarks : ""
			}, {
				slno1 : 16,
				check : true,
				requirment : "Other",
				doc : "Reference",
				attach : "",
				copy : "Click Here",
				remarks : ""
			},

	];

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
	var gridOptions1 = {
		columnDefs : columnDefs1,
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

	/* APPLY FOR REQUISITION ENDS */

	// setup the grid after the page has finished loading
	function downloadFile() {
		window.open("../assets/img/Service.pdf");

	}

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
		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1);

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

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function addId() {
		document.getElementById("demo").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto;";
		document.getElementById("main").style.width = "75%";
		$("#demo").show();
		$("#addId").hide();
		$("#rfqid").hide();
		$("#delete").hide();

	}
	function addId2() {

		$("#demo1").show();
		$("#myGrid1").hide();
		$("#addId2").hide();
		$("#rfqid").hide();
		$("#delete2").hide();
		$("#searchRowDiv").hide();
		$("#child").hide();

	}

	function cancel() {
		$("#delete2").hide();
		$("#addId2").hide();
		$("#cancel1").hide();
		$("#submit").hide();
		$("#edit").hide();
		$("#demo").hide();
		$("#child").hide();
		$("#myGrid1").hide();
		$("#myGrid").show();
		$("#addId").show();
		$("#rfqid").show();
		$("#delete").show();
		document.getElementById("main").style.width = "100%";
		document.getElementById("demo").style.width = "0";
	}

	function saveTableData() {

		cancel();
		var data = 1;
		var item = {};

		var rowEdit = $("#rowEdit").val();
		gridOptions.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		item.slno = data;
		item.date = $("#date").val();
		item.rfpno = $("#rfpno").val();
		item.rfpdate = $("#rfpdate").val();
		item.cutoff = $("#cutoff").val();
		item.custName = $("#custName").val();
		item.loc = $("#loc").val();
		item.state = $("#state").val();
		item.category = $("#category").val();
		item.subCategory = $("#subCategory").val();
		item.owner = $("#owner").val();
		item.rfpdocs = $("#rfpdocs").val();
		item.preQualQues = $("#preQualQues").val();
		item.clarity = $("#clarify").val();
		item.status = $("#status").val();
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
		cancel2();
		var data = 1;
		var item = {};

		var rowEdit = $("#rowEdit").val();
		gridOptions1.api.forEachNode(function(rowNode, index) {
			if (!rowEdit) {
				data = data + 1;
			}
		});
		item.slno1 = data;
		item.check = $("#check").val();
		item.doc = $("#doc").val();
		item.attach = $("#attach").val();
		item.copy = $("#copy").val();
		item.remarks = $("#remarks").val();
		var datas = [];
		if (rowEdit) {
			var rowNode = gridOptions1.api.getRowNode(rowEdit);
			rowNode.setData(item);
		} else {

			gridOptions1.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
			});

			datas.push(item)
			gridOptions1.api.setRowData(datas);
		}

	}

	function openNavAddActivity() {

		/* $("#activityDateId").val("");
		$("#activityStartTimeId").val("");
		$("#activityEndTimeId").val("");
		$("#activityId").val("");
		$("#activityRemarkId").val(""); */
		document.getElementById("mySidenav").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("myGrid1").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId2").hide();
		$("#delete2").hide();
	}
	function closeNav() {
		//$("#mainaddbtn").show();
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("myGrid1").style.width = "100%";
		$("#addId2").show();
		$("#cancelId").show();
		$("#addNotiId").show();
		$("#cancelNotiId").show();
	}
	function cancel2() {
		closeNav();
		$("#delete2").show();
	}

	function ClickCopy() {
		$('#myModal').modal('show');

	}

	function cancelCopy() {

		$('#myModal').modal('hide');

	}

	function submitCopy() {

		location.reload();

	}

	function changeImageColor() {
		var dropdown = document.getElementById("copyDocuments");
		var imageButton = document.getElementById("imageButton");

		if (dropdown.value !== "") {
			imageButton.classList.remove("btn-default");
			imageButton.classList.add("btn-primary");
		} else {
			imageButton.classList.remove("btn-primary");
			imageButton.classList.add("btn-default");
		}
	}