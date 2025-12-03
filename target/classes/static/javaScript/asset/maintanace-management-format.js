	$(document).ready(function() {
		$('#assignEmp').hide();
		$('#assignVen').hide();
		$('.docNoclss').prop('disabled', true);
		$('.custom-file-upload').prop('disabled', true);
		$('.loader').show();
		pno = 1;
	
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
		});
		$("#toDateCalendarTimeAction").datetimepicker({
			format: 'h:i A',
			closeOnDateSelect: false,
			timepicker: true,
			datepicker: false,
			step: 1,
			formatTime: 'h:i A'
		}).on("change", function() {
			$('#timeAction').val($(this).val());
		})
	
		$('#timeAction').blur(function() {
			$("#toDateCalendarTimeAction").val($(this).val());
		});
	
		$("#dateCalender").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			minDate: new Date().toLocaleDateString(undefined, { year: 'numeric', month: '2-digit', day: '2-digit' }),
		}).on("change", function() {
			$('#corrDate').val($(this).val());
		})
		$('#corrDate').blur(function() {
			$("#dateCalender").val($(this).val());
		});
		$("#timeCalender").datetimepicker({
			format: 'h:i A',
			closeOnDateSelect: false,
			timepicker: true,
			datepicker: false,
			step: 1,
			formatTime: 'h:i A'
		}).on("change", function() {
			$('#corrTime').val($(this).val());
		})
	
		$('#corrTime').blur(function() {
			$("#timeCalender").val($(this).val());
		});
	
		$("#fromDateCalender").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#filterFromDate').val($(this).val());
		})
		$('#filterFromDate').blur(function() {
			$("#fromDateCalender").val($(this).val());
		});
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		var gridEqDiv2 = document.querySelector('#activityLog');
		new agGrid.Grid(gridEqDiv2, activityForViewLogDet);
	
		var gridEqDiv = document.querySelector('#equipmentGrid');
		new agGrid.Grid(gridEqDiv, activityEquipOptions);
	
	
		var gridDiv = document.querySelector('#preventiveGrid');
		new agGrid.Grid(gridDiv, activityOptions1)
	
		getAllJobView();
		closeNav();
		cancelMain();
		cancelMain1();
		$('#equipmentGrid').hide();
		$('#AddEquip1').hide();
		document.getElementById("quickFilter").addEventListener("keydown", function(event) {
			if (event.key === "Enter") {
				onQuickFilterChanged()
			}
		});
	
	});
	function onQuickFilterChanged() {
		gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	}
	
	function reset() {
		$('#quickFilter').val("");
		onQuickFilterChanged();
	}
	//main grid
	const columnDefs = [
		{
			//headerCheckboxSelection : true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true,
			pinned: 'left',
		}, {
			headerName: 'Ticket No',
			field: "tcktNo",
			pinned: 'left',
			width: 95,
		}, /*{
			headerName: 'Status',
			field: "status",
			width: 95,
			cellStyle: {
				textAlign: 'center'
			},
			cellRenderer: function(params) {
				if (params.data.status == "Accepted") {
					return '<div style="color:#0642f5">Accepted</div>';
				} else if (params.data.status == "Revoked") {
					return '<div style="color:#D2042D">Revoked</div>';
				} else {
					return '<div style="color:#a9a9a9">Pending</div>';
				}
			}
		}*/
		{
		  headerName: 'Status',
		  field: "status",
		  width: 110, 
		  cellStyle: {
		    textAlign: 'center'
		  },
		  cellRenderer: function(params) {
		    let status = params.data.status;
		    let badgeClass = '';
		    let text = '';

		    switch (status) {
		      case "Accepted":
		        badgeClass = "badge badge-accepted";
		        text = "Accepted";
		        break;
		      case "Revoked":
		        badgeClass = "badge badge-revoked";
		        text = "Revoked";
		        break;
		      default:
		        badgeClass = "badge badge-pending";
		        text = "Pending";
		    }

		    return `<span class="${badgeClass}">${text}</span>`;
		  }
		},/*{
			headerName: "Ticket Status",
			field: "isClosed",
			cellStyle: { textAlign: 'center' },
			width: 110,
			cellRenderer: function(params) {
				if (params.data.uploadstatus == 1) {
					return '<div style="color:#0642f5">COMPLETED</div>';
				} else {
					return '<div style="color:#a9a9a9">INCOMPLETE</div>';
				}
			}
		}*/
		{
		  headerName: "Ticket Status",
		  field: "isClosed",
		  cellStyle: { textAlign: 'center' },
		  width: 110,
		  cellRenderer: function(params) {
		    let uploadStatus = params.data.uploadstatus;
		    let badgeClass = '';
		    let text = '';

		    if (uploadStatus === 1) {
		      badgeClass = "badge badge-completed";
		      text = "COMPLETED";
		    } else {
		      badgeClass = "badge badge-incomplete";
		      text = "INCOMPLETE";
		    }

		    return `<span class="${badgeClass}">${text}</span>`;
		  }
		}, {
			headerName: "Type",
			field: "type",
			width: 110
		}, {
			headerName: "Job Type",
			field: "assignmentType",
			width: 110
		}, {
			headerName: "Job id",
			field: "id",
			width: 110,
			hide: true,
		}, {
			headerName: "assetLocation",
			field: "assetLocation",
			width: 110
		}, {
			headerName: "Priority",
			field: "ticketPriority",
			width: 110
		}, {
			headerName: "Location",
			field: "raisedAddress",
			width: 110
		}, {
			headerName: "uploadstatus",
			field: "uploadstatus",
			width: 110,
			hide: true,
		}, {
			headerName: 'Asset ID',
			field: "assetid",
			cellStyle: { textAlign: 'center' },
			cellRenderer: function(params) {
				if (params.data.assetid != null && params.data.assetid != '') {
					return '<a onclick=redirectToAsset("' + params.data.assetid
						+ '") href="javascript:void(0)">' + params.data.assetid + '<i class="bi bi-arrow-bar-right"></i></a>';
				} else {
					return 'N/A'
				}
			}
		}, {
			headerName: "Assigned Date",
			field: "assigndate",
			width: 110,
			cellStyle: { textAlign: 'center' },
			hide: true,
		}, {
			headerName: "Assigned Time",
			field: "assigntime",
			width: 110,
			cellStyle: { textAlign: 'center' },
			hide: true,
		}, {
			headerName: "Assigned By",
			field: "assignedby",
		}, {
			headerName: "Type",
			field: "tckttype",
		}, {
			headerName: "Category",
			field: "tcktCat",
			hide: true,
		}, {
			headerName: "Sub-Category",
			field: "tcktSubCat",
			hide: true,
		},/*{
					headerName : "Ticket Desc",
					field : "desc",
					width : 300,
				}*/
		{
			headerName: "Ticket Desc",
			field: "desc",
			width: 300,
			valueGetter: params => {
				const descs = params.data.desc;
				return (descs && descs !== 'null') ? window.atob(descs) : '';
			}
		}, {
			headerName: "Raised By",
			field: "raisedby",
		},];
	
	var gridOptions = {
		columnDefs: columnDefs,
		rowSelection: 'single',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		pagination: true,
		paginationPageSize: 15,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 150
		},
		onSelectionChanged: onSelectionChanged,
	};
	let selectedttkid = '';
	function onSelectionChanged() {
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);;
		console.log("selectedData===========================", selectedData.map(node => node));
		id = selectedData.map(node => node.tcktNo);
		selectedttkid = id;
		assignid = selectedData.map(node => node.id);
		assType = selectedData.map(node => node.assignmentType);
		assTId = selectedData.map(node => node.assignmentTypeId);
		assTypeId = selectedData.map(node => node.assTypeId);
		assignmentType = selectedData.map(node => node.assignmentType);
		var tcktType = selectedData.map(node => node.type);
		var id = selectedData.map(node => node.id);
		baseid = selectedData.map(node => node.baseid);
		var tcktNo = selectedData.map(node => node.tcktNo);
		var selectedRows = gridOptions.api.getSelectedRows();
		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			var adhr = $("#adhr").val();
			if (adhr != 'adhr') {
				$("#ticketAssign").hide();
				$('#ticketAction').show();
				$('#ticketHistory').hide();
				$('#ticketSts').hide();
				$('#ticketChat').hide();
				$('#next33').hide();
				$('#next2').hide();
				$('#next02').show();
				$('#prev1').hide();
				$('#prev01').show();
			} else {
				$("#ticketAssign").show();
				$('#ticketAction').show();
				$('#ticketHistory').show();
				$('#ticketSts').show();
				$('#ticketChat').show();
				$('#next33').show();
				$('#next2').show();
				$('#next02').hide();
				$('#prev1').show();
				$('#prev01').hide();
				viewLogsForTicket(tcktNo);
				feedbackFun();
				viewAssignedResult(tcktNo);
			}
			viewTicketDetails(tcktNo);
	
			if (tcktType == 'Corrective') {
				$("#rejectBtn, #acceptBtn").show();
				$("#actionCorrect").show();
				$("#actionAsset").hide();
				showForm('corrective');
				showResultModalView(id);
			} else {
				$("#rejectBtn, #acceptBtn").hide();
				$("#actionAsset").show();
				$("#actionCorrect").hide();
				showForm('preventive');
				showResultModal();
			}
			$("#tcktId").html(tcktNo);
			$("#tcktId1").html(tcktNo);
			$("#tcktId2").html(tcktNo);
			$("#tcktId5").html(tcktNo);
			$("#assetBoxInProgress").val(tcktNo);
			$("#tktNo1").html(tcktNo);
	
			$("#tktNo2").html(tcktNo);
			$("#tktNo3").html(tcktNo);
			$("#tktNo4").html(tcktNo);
			if (rowCount > 0) {
				$("#tcktId").html(tcktNo);
				$("#tcktId1").html(tcktNo);
				$("#tcktId2").html(tcktNo);
				if (selectedData.map(node => node.status) == "Pending") {
					$('#next02').hide();
					$('#resultBtn').hide();
					$('#acceptBtn').show();
					$('#rejectBtn').show();
					$('#ticketAction').hide();
					$('.feedbackInputArea').hide();
					travelPrev('1');
				} else if (selectedData.map(node => node.status) == "Accepted") {
					$('#ticketAction').show();
					$('.feedbackInputArea').show();
					$('#next02').show();
					if (selectedData.map(node => node.uploadstatus) >= 1) {
						$('#resultBtn').hide();
						$('#saveInspection').hide();
						$('#saveInspection1').hide();
					} else {
						if (selectedData.map(node => node.isClosed) == "CLOSE") {
							$('#resultBtn').hide();
							$('#saveInspection').hide();
							$('#saveInspection1').hide();
						} else {
							$('#resultBtn').show();
							$('#saveInspection').show();
							$('#saveInspection1').show();
						}
					}
					$('#acceptBtn').hide();
					$('#rejectBtn').hide();
				} else {
					$('#resultBtn').hide();
					$('#acceptBtn').hide();
					$('#rejectBtn').hide();
					$('#ticketAction').hide();
					$('#next02').hide();
				}
	
				$('#modalTcktIDAction').html(id);
				$('#atype').html(assType);
				$('#assignTypeId').val(assTypeId);
				$('#assignTId').val(assTId);
				$('#asgnId').val(assignid);
			} else {
				$("#tcktId").html("");
				$('#resultBtn').hide();
				$('#acceptBtn').hide();
				$('#rejectBtn').hide();
				travelPrev('1');
			}
		} else {
			travelPrev('1');
			$("#tckttype").html("");
			$("#ticketPriority").html("");
			$("#tcktCat").html("");
			$("#tcktSubCat").html("");
			$("#assigndate").html("");
			$("#assigntime").html("");
			$("#assetid").html("");
			$("#assetLocation").html("");
			$("#isClosed").html("");
			$("#status").html("");
			$("#assignmentType").html("");
			$("#type").html("");
			$('#ticketAssign').hide();
			$('#ticketAction').hide();
			$('#ticketHistory').hide();
			$('#ticketSts').hide();
			$('#ticketChat').hide();
			$('#acceptBtn').hide();
			$('#rejectBtn').hide();
			$('#next2').hide();
			$('#next02').hide();
		}
	
	}
	// history ticket Grid
	var activityForViewLog = [
		{
			headerName: "Date and Time",
			field: "created_on",
		}, {
			headerName: "Ticket ID",
			field: "ticket_id",
			hide: true,
		}, {
			headerName: "Owner",
			field: "assign_to",
			width: 175
		}, {
			headerName: "Activity",
			field: "assign_type",
	
		}, {
			headerName: "Status",
			field: "assign_status",
			hide: true,
		}, {
			headerName: "Status",
			field: "open_status",
	
		}];
	//let the grid know which columns and what data to use product table
	var activityForViewLogDet = {
		columnDefs: activityForViewLog,
		rowSelection: 'multiple',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 175
		}
	};
	// corrective equipment Grid
	var activityEqDefs = [
		{
			headerCheckboxSelection: true,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			sortable: false,
			filter: false,
			resizable: true,
			width: 30,
			pinned: 'left'
		},
		{
			headerName: "Asset Id",
			field: "assetId",
			hide: true,
		}, {
			headerName: "Equipement Category",
			field: "equipementCat",
			cellStyle: {
				textAlign: 'center'
			},
			//	hide: true,
		}, {
			headerName: "Equipement Subcategory",
			field: "equipementScat",
			cellStyle: {
				textAlign: 'center'
			},
			//	hide: true,
		}, {
			headerName: "Equipement Category",
			field: "equipementCat1",
			cellStyle: {
				textAlign: 'center'
			},
			hide: true,
		}, {
			headerName: "Equipement Subcategory",
			field: "equipementScat1",
			cellStyle: {
				textAlign: 'center'
			},
			hide: true,
		}, {
			headerName: "Equipement Quantity",
			field: "equipementQty",
			cellStyle: {
				textAlign: 'center'
			},
		}, {
			headerName: "Equipement Remark",
			field: "description",
			cellStyle: {
				textAlign: 'center'
			},
		}];
	//let the grid know which columns and what data to use product table
	var activityEquipOptions = {
		columnDefs: activityEqDefs,
		rowSelection: 'multiple',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 170
		}
	
	};
	
	var activityDefs1 = [
		{
			headerName: "Occurance Time",
			field: "occuranceTime",
			//   hide: true,
			flex: 2,  // Adjusts width automatically
			cellStyle: {
				textAlign: 'left'
			},
	
			width: 150,
		}, {
			headerName: "Uploaded On",
			field: "uploadedOn",
			flex: 2, // Adjusts width automatically
			cellStyle: {
				textAlign: 'left'
			},
			//    hide: true,
		}, {
			headerName: "Uploaded By",
			field: "createdByName",
			flex: 0.6, // Adjusts width automatically
			cellStyle: {
				textAlign: 'left'
			},
			hide: true,
		},
		{
			headerName: "Status",
			field: "status",
			flex: 0.7,  // Adjusts width, slightly smaller than other columns
			hide: true,
			cellStyle: {
				textAlign: 'left'
			},
			cellRenderer: function(params) {
				if (params.data.status == 0) {
					return '<input class="checkbox" type="checkbox" name="checkBoxName" id="' + "status" + params.rowIndex + '">';
				} else {
					return '<input class="checkbox" type="checkbox" name="checkBoxName" id="' + "status" + params.rowIndex + '" checked>';
				}
			},
			width: 100,
		},
		{
			headerName: "Sl No",
			field: "policyName",
			flex: 1,  // Adjusts width automatically
			cellStyle: {
				textAlign: 'left'
			},
			width: 100,
		},
		{
			headerName: "Task Description",
			field: "description",
			flex: 2,  // Larger flex to allocate more space for descriptions
			cellStyle: {
				textAlign: 'left'
			},
		},
		{
			headerName: "Priority",
			field: "priority",
			flex: 1,  // Adjusts width automatically
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Type",
			hide: true,
			field: "tasktype",
			flex: 1,
		},
		{
			headerName: "Unit",
			field: "uom",
			flex: 1,
		},
		{
			headerName: "Min Range",
			field: "minrange",
			flex: 1,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Max Range",
			field: "maxrange",
			flex: 1,
			cellStyle: {
				textAlign: 'left'
			}
		},
		{
			headerName: "Result",
			field: "result",
			cellClass: function(params) {
				if (params.data.taskStatus != "COMPLETED") {
					return 'custom-color-column';
				} else {
					return 'custom-secondary-column';
				}
			},
			flex: 1,
			editable: function(params) {
				return params.data.taskStatus != "COMPLETED";
			}
		},
		{
			headerName: "Review",
			field: "reviewSts",
			flex: 2,
			cellRenderer: function(params) {
				if (params.data.taskStatus != "COMPLETED") {
					return `
	                 <select id="reviewSts${params.rowIndex}" oninput="checkReview(event);">
	                     <option value="">Select</option>
	                     <option value="OKAY"${params.data.reviewSts == 'OKAY' ? ' selected' : ''}>OKAY</option>
	                     <option value="NOT OKAY"${params.data.reviewSts == 'NOT OKAY' ? ' selected' : ''}>NOT OKAY</option>
	                 </select>
	             `;
				} else {
					return params.data.reviewSts;
				}
	
			}
		},
		{
			headerName: "Remarks",
			field: "remark",
			cellClass: function(params) {
				if (params.data.taskStatus != "COMPLETED") {
					return 'custom-color-column';
				} else {
					return 'custom-secondary-column';
				}
			},
			editable: function(params) {
				return params.data.taskStatus != "COMPLETED";
			},
			flex: 2,  // Larger space for remarks
			cellStyle: {
				textAlign: 'left'
			}
		}
	];
	
	// let the grid know which columns and what data to use product table
	var gridApi, columnApi;
	var activityOptions1 = {
		columnDefs: activityDefs1,
		rowSelection: 'single',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 200
		},
		singleClickEdit: true,
		stopEditingWhenCellsLoseFocus: true,
		onGridReady: function(params) {
			gridApi = params.api;
			columnApi = params.columnApi;
		},/*
	    onCellValueChanged: function (params) {
	        if (params.colDef.field === 'result') {
	            checkValidQt(params);
	        
	    }}*/
	};
	
