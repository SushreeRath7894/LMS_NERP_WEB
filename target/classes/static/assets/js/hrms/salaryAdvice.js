/**
 * 
 */
	/************************** Salary Advice Script Start  ****************************/
	$(document).ready(function() {
		getCurrentDateMonthYear3();

		var gridDiv = document.querySelector('#myGridsalAdvice');
		new agGrid.Grid(gridDiv, gridOptionssalAdvice);
		viewAllSalaryAdviceData();
	})
	var columnDefssalAdvice = [

	{
		headerName : "Employee ID",
		field : "empId",
		pinned : 'left',
		width : 120,
	}, {
		headerName : "Employee Name",
		field : "empName",
		pinned : 'left',
		cellStyle : {
			textAlign : 'left'
		},
		width : 205,
	}, {
		headerName : "DepartMent",
		field : "dept",
		cellStyle : {
			textAlign : 'left'
		},
		width : 200,
	}, {
		headerName : "Sub Department",
		field : "subDept",
		cellStyle : {
			textAlign : 'left'
		},
		width : 200,
	}, {
		headerName : "Bank Name",
		field : "bankAccountName",
		cellStyle : {
			textAlign : 'left'
		},
		width : 170,
	}, {
		headerName : "Bank Account",
		field : "bankAccount",
		width : 170,
	}, {
		headerName : "Salary",
		field : "salary",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 110,
	},{
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
	var gridOptionssalAdvice = {
		columnDefs : columnDefssalAdvice,
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
	
	/* -------------------search bar for Salary Advice	------------------------ */

	function onQuickFilterChangedsalAdvice() {
		gridOptionssalAdvice.api
				.setQuickFilter(document.getElementById('quickFiltersalAdvice').value);
		countFilterRowssalAdvice();
		 const filteredRows = gridOptionssalAdvice.api.getDisplayedRowCount();
		    $('#saladvice').find('span').html(filteredRows);
	}
	function countFilterRowssalAdvice(){
		var filteredRowCount = 0;
        var data = [];
        gridOptionssalAdvice.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
        var salary = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.salary);
	    }, 0);
	    var totalRow = [{
	    		empId: 'Total',
	    		salary: salary.toFixed(2),
	    }];
	    gridOptionssalAdvice.api.setPinnedBottomRowData(totalRow);
	}

	function cancelBarsalAdvice() {
		var id = document.getElementById("closeKeysalAdvice");
		id.style.display = "block";

		if ($('#quickFiltersalAdvice').val() == null || $('#quickFiltersalAdvice').val() == "") {
			id.style.display = "none";
		}
	}

	//viewAllSalaryAdviceData
	function viewAllSalaryAdviceData(){
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDate3").val();
		var toDate = $("#endDate3").val();
		 var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		
		$('.loader').show();
		agGrid.simpleHttpRequest(
				{
					url : "payroll-report-view-salary-advice-view-details?fromDate="
							+ fromDate + "&toDate=" + toDate + "&stafftype=" + stafftype+ "&employedBy=" + employedBy + "&id=" + id
				}).then(function(data) {
					$('.loader').hide();
					var len = data.length;
					$('#saladvice').find('span').html(len);
					gridOptionssalAdvice.api.setRowData(data);
					countFilterRowssalAdvice();
		});
	}
	//changeBand
	function changeType() {
		viewAllSalaryAdviceData();
	}
	//getSalaryAdviceData
	function getSalaryAdviceYrMtData(){
		var curdate = new Date();
		var month = $("#attndmonth3").val();
		var sday = $("#startDayForAtten3").val();
		var attnyear = $("#attndyear3").val();
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

		$("#fromDate3").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDate3").val(todate);
		$("#endDateCalendar").val(todate);
	}
	//changeMonthYear
	function changeMonthYearsalAdvice(){
			var curdate = new Date();
			var attnyear = $("#attndyear3").val();
			var month = $("#attndmonth3").val();
			var date = curdate.getDate();
			var mnth = curdate.getMonth();
			var sday = $("#startDayForAtten3").val();
			var mt=month-1;
		if(attnyear>curdate.getFullYear()){
				toastr.success("Please Choose Current Year Or Previous Year!")
				
				$("#attndyear").val(curdate.getFullYear())
				getCurrentDateMonthYear3();
				viewAllApproveData();
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
					$("#attndmonth").val(month + 1);
				} else {
					$("#attndmonth").val(month);
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

				$("#fromDate3").val(fromdate);
				$("#fromDateCalendar").val(fromdate);
				$("#endDate3").val(todate);
				$("#endDateCalendar").val(todate);
			}else{
				getSalaryAdviceYrMtData();
				viewAllSalaryAdviceData();
				}
			}else{
				getSalaryAdviceYrMtData();
				viewAllSalaryAdviceData();
			}
		}
	}
 function getCurrentDateMonthYear3() {
		var curdate = new Date();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		$("#attndyear3").val(curdate.getFullYear())
		var sday = $("#startDayForAtten3").val();
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
			$("#attndmonth3").val(month + 1);
		} else {
			$("#attndmonth3").val(month);
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
		$("#fromDate3").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDate3").val(todate);
		$("#endDateCalendar").val(todate);
	}
		function downloadSalaryAdvaiceCSV() {
			var rowCount = 0;
			gridOptionssalAdvice.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			    if (rowNode.data.empId) {
			        rowCount++;
			    }
			});
			if (rowCount > 0) {
				var name = "Salary Advice-" + $("#attndyear3 option:selected").text() +"-"+ $("#attndmonth3 option:selected").text();
			    var allColumns = gridOptionssalAdvice.columnApi.getAllColumns();

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
                    } else if (params.value == 0) {
                        return "Pending";
                    } else {
                        return "";
                    }
                }
                return params.value;
            }

			    };
			    gridOptionssalAdvice.api.exportDataAsCsv(params);
			} else {
				toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
			    
			}	
		}
	/************************** Salary Advice Script End  ****************************/