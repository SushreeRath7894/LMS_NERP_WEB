/**
 * 
 */

	$(document).ready(function() {
		    getCurrentDateMonthYear4();
			var gridDiv = document.querySelector('#myGridepf');
			new agGrid.Grid(gridDiv, gridOptionsepf);
			viewAllEpfData();
	})
	
	var columnDefsepf = [
	{
		headerName : "Employee ID",
		field : "empId",
		width : 180,
		pinned : 'left',
	}, {
		headerName : "Employee Name",
		field : "empName",
		pinned : 'left',
		cellStyle : {
			textAlign : 'left'
		},
		width : 300,
	}, {
		headerName : "DepartMent",
		field : "dept",
		hide: true,
		cellStyle : {
			textAlign : 'left'
		},
		width : 180,
	}, {
		headerName : "Sub Department",
		field : "subDept",
		hide: true,
		cellStyle : {
			textAlign : 'left'
		},
		width : 180,
	}, {
		headerName : "EPF No",
		field : "uanNo",
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
		width : 180,
	}, {
		headerName : "Employee Epf",
		field : "empEPF",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		width : 180,
	}, {
		headerName : "Employer Epf",
		field : "compEPF",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 150,
	}, {
		headerName : "Admin Charges",
		field : "adminCharge",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 180,
	}, {
		headerName : "EDLI Charges",
		field : "edliCharge",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 180,
	}, {
		headerName : "Total Epf",
		field : "total",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 150,
	}, {
		headerName : 'Payment Status',
		field : "paymentStatus",
		cellStyle : {
			textAlign : 'center'
		},
		width : 180,
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
	var gridOptionsepf = {
		columnDefs : columnDefsepf,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 150,
			height : 20
		},
		pinnedBottomRowData: rowData,
		 getRowStyle: function (params) {
			if (params.node.rowPinned) {
		      return { background: '#EAE7FA !important', fontWeight: 'bold' };
		    }
		    return null;
	  	}
	};
	
	/* -------------------search bar for mygrid------------------------ */

	function onQuickFilterChangedepf() {
		gridOptionsepf.api
				.setQuickFilter(document.getElementById('quickFilterepf').value);
		countFilteredRowsepf();
		 const filteredRows = gridOptionsepf.api.getDisplayedRowCount();
		    $('#epfcnt').find('span').html(filteredRows);
	}
	function countFilteredRowsepf(){
		var filteredRowCount = 0;
        var data = [];
        gridOptionsepf.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
		var totalEarning = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.totalEarning);
	    }, 0);

	    var empEPF = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.empEPF);
	    }, 0);

	    var compEPF = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.compEPF);
	    }, 0);
	    var adminCharge = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.adminCharge);
	    }, 0);
	    var edliCharge = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.edliCharge);
	    }, 0);
	    var total = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.total);
	    }, 0);
	    var totalRow = [{
	    		empId: 'Total',
	    		totalEarning: totalEarning.toFixed(2),
	    		empEPF: empEPF.toFixed(2),
	    		compEPF:compEPF.toFixed(2),
	    		adminCharge: adminCharge.toFixed(2),
	    		edliCharge: edliCharge.toFixed(2),
	    		total: total.toFixed(2)
	    }];
	    console.log("addedData",totalRow);
	    gridOptionsepf.api.setPinnedBottomRowData(totalRow);
	}

	function cancelBarepf() {
		var id = document.getElementById("closeKeyepf");
		id.style.display = "block";

		if ($('#quickFilterepf').val() == null || $('#quickFilterepf').val() == "") {
			id.style.display = "none";
		}
	}
	 function getCurrentDateMonthYear4() {
		var curdate = new Date();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		$("#attndyear4").val(curdate.getFullYear())
		var sday = $("#startDayForAtten4").val();
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
			$("#attndmonth4").val(month + 1);
		} else {
			$("#attndmonth4").val(month);
		}
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
		$("#fromDate4").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDate4").val(todate);
		$("#endDateCalendar").val(todate);
	}
	//viewAllEpfData
	function viewAllEpfData(){
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDate4").val();
		var toDate = $("#endDate4").val();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
			
		$('.loader').show();
		agGrid.simpleHttpRequest(
				{
					url : "payroll-report-view-epf-view-details?fromDate=" + fromDate
							+ "&toDate=" + toDate + "&stafftype=" + stafftype+ "&employedBy=" + employedBy + "&id=" + id
				}).then(function(data) {
					$('.loader').hide();
			var len = data.length;
			$('#epfcnt').find('span').html(len);
			gridOptionsepf.api.setRowData(data);
			countFilteredRowsepf();
		});
	}
	//changeType
	function changeTypeEpf() {
		viewAllEpfData();
	}
	//getEpfData
	function getEpfYrMtData(){
		var curdate = new Date();
		var month = $("#attndmonth4").val();
		var sday = $("#startDayForAtten4").val();
		var attnyear = $("#attndyear4").val();
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

		$("#fromDate4").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDate4").val(todate);
		$("#endDateCalendar").val(todate);
	}
	function changeMonthYearEpf(){
		var curdate = new Date();
		var attnyear = $("#attndyear4").val();
		var month = $("#attndmonth4").val();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		var sday = $("#startDayForAtten4").val();
		var mt=month-1;
	if(attnyear>curdate.getFullYear()){
			
			toastr.success("Please Choose Current Year Or Previous Year!");
			$("#attndyear").val(curdate.getFullYear())
			getCurrentDateMonthYear4();
			
	}else{	
		if(attnyear==curdate.getFullYear()){
		if(mt>curdate.getMonth()){
			
			toastr.success("Please Choose Current month Or Previous Month!");
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
				$("#attndmonth4").val(month + 1);
			} else {
				$("#attndmonth4").val(month);
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

			$("#fromDate4").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDate4").val(todate);
			$("#endDateCalendar").val(todate);
		}else{
			getEpfYrMtData();
			viewAllEpfData();
			}
		}else{
			getEpfYrMtData();
			viewAllEpfData();
		}
	}
}
	
	function downloadEpfCSV() {
		var rowCount = 0;
		gridOptionsepf.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		    if (rowNode.data.empId) {
		        rowCount++;
		    }
		});
		if (rowCount > 0) {
			var name = "EPF Details-" + $("#attndyear4 option:selected").text() +"-"+ $("#attndmonth4 option:selected").text();
		    var allColumns = gridOptionsepf.columnApi.getAllColumns();

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
		    gridOptionsepf.api.exportDataAsCsv(params);
		} else {
			toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
		    
		}
	}