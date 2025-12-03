/**
 * 
 */

$(document).ready(function() {
		getCurrentDateMonthYear5();
			var gridDiv = document.querySelector('#myGridEsi');
			new agGrid.Grid(gridDiv, gridOptionsEsi);
			viewAllEsiData();			
 		})
	var columnDefsEsi = [

	{
		headerName : "Employee ID",
		field : "empId",
		width : 180,
		pinned : 'left',
	}, {
		headerName : "Employee Name",
		field : "empName",
		cellStyle : {
			textAlign : 'left'
		},
		width : 300,
		pinned : 'left',
	}, {
		headerName : "DepartMent",
		field : "dept",
		hide: true,
		cellStyle : {
			textAlign : 'left'
		},
		width : 180,
		pinned : 'left',
	}, {
		headerName : "Sub Department",
		field : "subDept",
		hide: true,
		cellStyle : {
			textAlign : 'left'
		},
		width : 180,
	}, {
		headerName : "ESIC No",
		field : "esicNo",
		cellStyle : {
			textAlign : 'center'
		},
		width : 200,
	}, {
		headerName : "Gross pay",
		field : "totalEarning",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		width : 200,
	}, {
		headerName : "Employee Esi",
		field : "empESI",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		width : 200,
	}, {
		headerName : "Employer Esi",
		field : "compESI",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		width : 200,
	},{
		headerName : "Total Esi",
		field : "total",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		width : 200,
	}, {
		headerName : 'Payment Status',
		field : "paymentStatus",
		cellStyle : {
			textAlign : 'center'
		},
		cellRenderer : function(params) {
			if (params.data.paymentStatus == 1) {
				return '<div style="color:#0642f5">Paid</div>';
			} else if (params.data.paymentStatus == 0) {
				return '<div style="color:#a9a9a9">Pending</div>';
			} else {
				return '';
			}
		}

	} ];

	var rowData = [ {
	    	empId: 'Total'
	    },];
	var gridOptionsEsi = {
		columnDefs : columnDefsEsi,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 150,
			height : 20
		},
		rowSelection : 'single',
		rowMultiSelectWithClick : true,
		pinnedBottomRowData: rowData,
		 getRowStyle: function (params) {
			if (params.node.rowPinned) {
		      return { background: '#EAE7FA !important', fontWeight: 'bold' };
		    }
		    return null;
	  	}
	};
	
	function onQuickFilterChangedEsi() {
		gridOptionsEsi.api
				.setQuickFilter(document.getElementById('quickFilterEsi').value);
		countFilteredRowsEsi();
		 const filteredRows = gridOptionsEsi.api.getDisplayedRowCount();
		    $('#esicnt').find('span').html(filteredRows);
	}
function countFilteredRowsEsi(){
	var filteredRowCount = 0;
    var data = [];
    gridOptionsEsi.api.forEachNodeAfterFilter(function (node) {
        filteredRowCount++;
        data.push(node.data);
    });
    var totalEarning = data.reduce(function (sum, row) {
        return sum + parseFloat(row.totalEarning);
    }, 0);
    var empESI = data.reduce(function (sum, row) {
        return sum + parseFloat(row.empESI);
    }, 0);
    var compESI = data.reduce(function (sum, row) {
        return sum + parseFloat(row.compESI);
    }, 0);
    var total = data.reduce(function (sum, row) {
        return sum + parseFloat(row.total);
    }, 0);
    var totalRow = [{
    		empId: 'Total',
    		totalEarning: totalEarning.toFixed(2),
    		empESI: empESI.toFixed(2),
    		compESI: compESI.toFixed(2),
    		total: total.toFixed(2)
    }];
    console.log("addedData",totalRow);
    gridOptionsEsi.api.setPinnedBottomRowData(totalRow);
}
	function cancelBarEsi() {
		var id = document.getElementById("closeKeyEsi");
		id.style.display = "block";

		if ($('#quickFilterEsi').val() == null || $('#quickFilterEsi').val() == "") {
			id.style.display = "none";
		}
	}
	//viewAllEsiData
	function viewAllEsiData(){
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDate5").val();
		var toDate = $("#endDate5").val();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
			
			
		$('.loader').show();
		agGrid.simpleHttpRequest(
				{
					url : "payroll-report-view-esi-view-details?fromDate="
							+ fromDate + "&toDate=" + toDate
							+ "&stafftype=" + stafftype+ "&employedBy=" + employedBy + "&id=" + id
				}).then(function(data) {
					$('.loader').hide();
			var len = data.length;
			$('#esicnt').find('span').html(len);
			gridOptionsEsi.api.setRowData(data);
			countFilteredRowsEsi();
		});
	}
	//changeType
		function changeTypeEsi() {
			viewAllEsiData();
		}
		//getEsiData
		function getEsiYrMtData(){
			var curdate = new Date();
			var month = $("#attndmonth5").val();
			var sday = $("#startDayForAtten5").val();
			var attnyear = $("#attndyear5").val();
			if (parseInt(sday) > 1) {
				if (month == 1) {
					var year = attnyear - 1;
					month = 12;
				} else {
					var year = attnyear;
					month = month - 1;
				}
			} else {
				var year = attnyear;
			}

			var nextmonth = parseInt(month) + 1;

			var fromdate = sday + "-" + String(month).padStart(2, '0') + "-" + year;
			const lastDayOfMonth = new Date(year, month, 0).getDate();
			if (sday > 1) {
				if (month == "12") {
					var todate = (parseInt(sday) - 1) + "-"
							+ String(1).padStart(2, '0') + "-" + (year + 1);
				} else {
					var todate = (parseInt(sday) - 1) + "-"
							+ String(nextmonth).padStart(2, '0') + "-" + year;
				}
			} else {
				var todate = lastDayOfMonth + "-" + String(month).padStart(2, '0')
						+ "-" + year;
			}

			$("#fromDate5").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDate5").val(todate);
			$("#endDateCalendar").val(todate);
		}
		
	
	 

		function downloadEsiCSV() {
			var rowCount = 0;
			gridOptionsEsi.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			    if (rowNode.data.empId) {
			        rowCount++;
			    }
			});
			if (rowCount > 0) {
				var name = "ESI Details-" + $("#attndyear5 option:selected").text() +"-"+ $("#attndmonth5 option:selected").text();
			    var allColumns = gridOptionsEsi.columnApi.getAllColumns();

			    allColumns.forEach(col => {
			        if (col.getColDef().headerName) {
			            col.getColDef().headerName = col.getColDef().headerName.toUpperCase();
			        }
			    });

			    var visibleColumns = allColumns.filter(col => {
			        const headerName = col.getColDef().headerName;
			        const isColumnVisible = !col.getColDef().hide;
			        return headerName !== undefined && headerName !== null && isColumnVisible;
			    });

			    var visibleColumnIds = visibleColumns.map(col => col.getColId());

			    const params = {
			        skipHeader: false,
			        columnGroups: false,
			        skipFooters: false,
			        skipGroups: false,
			        skipPinnedTop: false,
			        skipPinnedBottom: false,
			        allColumns: true,
			        onlySelected: false,
			        suppressQuotes: false,
			        fileName: name + '.csv',
			        sheetName: 'Sheet1',
			        customHeader: null,
			        customFooter: null,
			        columnKeys: visibleColumnIds,
					processCellCallback: function(params) {
	                if (params.column.getColId() === "paymentStatus") {
	                    if (params.value == 1) {
	                        return "Paid";
	                    }else if (params.value == 0) {
	                        return "Pending";
	                    } else {
	                        return "";
	                    }
	                }
	                return params.value;
	            }
	
			    };
			  
			    gridOptionsEsi.api.exportDataAsCsv(params);
			} else {
				toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
			    
			}	
		}
		
//
function getCurrentDateMonthYear5(){
	var curdate = new Date();
	var date = curdate.getDate();
	var mnth = curdate.getMonth();
	$("#attndyear5").val(curdate.getFullYear())
	var sday = $("#startDayForAtten5").val();
	if (parseInt(sday) > 1) {
		if (parseInt(date) > parseInt(sday)) {
			var month = (curdate.getMonth() + 1);
			var year = curdate.getFullYear();
		} else {
			var month = curdate.getMonth();
			if (month == 0) {
				var year = curdate.getFullYear() - 1;
				month = 12;
			} else {
				var month = curdate.getMonth();
				var year = curdate.getFullYear();
			}
		}
	} else {
		var month = (curdate.getMonth() + 1);
		var year = curdate.getFullYear();
	}

	var nextmonth = month + 1;
	if (parseInt(sday) > 1) {
		$("#attndmonth5").val(month + 1);
	} else {
		$("#attndmonth5").val(month);
	}

	var fromdate = sday + "-"
			+ String(month).padStart(2, '0') + "-" + year;
	const lastDayOfMonth = new Date(year, month, 0)
			.getDate();
	if (sday > 1) {
		if (month == "12") {
			var todate = (parseInt(sday) - 1) + "-"
					+ String(1).padStart(2, '0') + "-"
					+ (year + 1);
		} else {
			var todate = (parseInt(sday) - 1) + "-"
					+ String(nextmonth).padStart(2, '0')
					+ "-" + year;
		}
	} else {
		var todate = lastDayOfMonth + "-"
				+ String(month).padStart(2, '0') + "-"
				+ year;
	}

	$("#fromDate5").val(fromdate);
	$("#fromDateCalendar").val(fromdate);
	$("#endDate5").val(todate);
	$("#endDateCalendar").val(todate);	
}

function changeMonthYearEsi(){
			var curdate = new Date();
			var attnyear = $("#attndyear5").val();
			var month = $("#attndmonth5").val();
			var date = curdate.getDate();
			var mnth = curdate.getMonth();
			var sday = $("#startDayForAtten5").val();
			var mt=month-1;
		if(attnyear>curdate.getFullYear()){
				toastr.success("Please Choose Current Year Or Previous Year!")
				$("#attndyear").val(curdate.getFullYear())
				getCurrentDateMonthYear5();
				//viewAllApproveData();
		}else{	
			if(attnyear==curdate.getFullYear()){
			if(mt>curdate.getMonth()){
				toastr.success("Please Choose Current month Or Previous Month!")

				if (parseInt(sday) > 1) {
					if (parseInt(date) > parseInt(sday)) {
						var month = (curdate.getMonth() + 1);
						var year = attnyear;
					} else {
						var month = curdate.getMonth();
						if (month == 0) {
							var year = attnyear - 1;
							month = 12;
						} else {
							var month = curdate.getMonth();
							var year = attnyear;
						}
					}
				} else {
					var month = (curdate.getMonth() + 1);
					var year = attnyear;
				}

				var nextmonth = month + 1;
				if (parseInt(sday) > 1) {
					$("#attndmonth5").val(month + 1);
				} else {
					$("#attndmonth5").val(month);
				}

				var fromdate = sday + "-"
						+ String(month).padStart(2, '0') + "-" + year;
				const lastDayOfMonth = new Date(year, month, 0)
						.getDate();
				if (sday > 1) {
					if (month == "12") {
						var todate = (parseInt(sday) - 1) + "-"
								+ String(1).padStart(2, '0') + "-"
								+ (year + 1);
					} else {
						var todate = (parseInt(sday) - 1) + "-"
								+ String(nextmonth).padStart(2, '0')
								+ "-" + year;
					}
				} else { 
					var todate = lastDayOfMonth + "-"
							+ String(month).padStart(2, '0') + "-"
							+ year;
				}

				$("#fromDate5").val(fromdate);
				$("#fromDateCalendar").val(fromdate);
				$("#endDate5").val(todate);
				$("#endDateCalendar").val(todate);
			}else{
				getEsiYrMtData();
				viewAllEsiData();
				}
			}else{
				getEsiYrMtData();
				viewAllEsiData();
			}
		}
	}