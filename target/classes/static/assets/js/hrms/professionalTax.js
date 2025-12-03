/**
 * 
 */
$(document).ready(function() {
		getCurrentDateMonthYear6();
		var gridDiv = document.querySelector('#myGridTax');
		new agGrid.Grid(gridDiv, gridOptionsTax);
		viewAllTaxData();
	})
	var columnDefsTax = [
	{
		headerName : "Employee ID",
		field : "empId",
		pinned : 'left',
		width : 200,
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
		headerName : "PAN No",
		field : "panNo",
		cellStyle : {
			textAlign : 'cener'
		},
		width : 210,
	}, {
		headerName : "Salary",
		field : "salary",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 200,
	}, {
		headerName : "Professional Tax",
		field : "incTax",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		width : 200,
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
	var gridOptionsTax = {
		columnDefs : columnDefsTax,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 180,
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
	
	/* -------------------search bar for mygrid------------------------ */

	function onQuickFilterChangedTax() {
		gridOptionsTax.api
				.setQuickFilter(document.getElementById('quickFilterTax').value);
		   countFilteredRows();
		   const filteredRows = gridOptions.api.getDisplayedRowCount();
		    $('#taxcnt').find('span').html(filteredRows);
	}
	function countFilteredRowsTax() {
        var filteredRowCount = 0;
        var data = [];
        gridOptionsTax.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
        var salary = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.salary || 0);
	    }, 0);

	    var incTax = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.incTax || 0);
	    }, 0);
	    var totalRow = [{
	    		empId: 'Total',
	    		salary: salary.toFixed(2),
	    		incTax: incTax.toFixed(2),
	    }];
	    console.log("addedData",totalRow);
	    gridOptionsTax.api.setPinnedBottomRowData(totalRow);
    }

	function cancelBarTax() {
		var id = document.getElementById("closeKeyTax");
		id.style.display = "block";

		if ($('#quickFilterTax').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}
	
	//getCurrentDateMonthYear
	function getCurrentDateMonthYear6(){
		var curdate = new Date();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		$("#attndyearPT").val(curdate.getFullYear())
		var sday = $("#startDayForAttenPT").val();
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
			$("#attndmonthPT").val(month + 1);
		} else {
			$("#attndmonthPT").val(month);
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

		$("#fromDatePT").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDatePT").val(todate);
		$("#endDateCalendar").val(todate);	
	}
	//viewAllEsiData
	function viewAllTaxData(){
		
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDatePT").val();
		var toDate = $("#endDatePT").val();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		
		$('.loader').show();
	 	agGrid.simpleHttpRequest({
			url : "payroll-report-view-proffesional-tax-view-details?fromDate="+fromDate+"&toDate="+toDate+"&stafftype="
			+stafftype+"&employedBy="+employedBy + "&id=" + id
		}).then(function(data) {
			$('.loader').hide();
			var len = data.length;
			$('#taxcnt').find('span').html(len);
			gridOptionsTax.api.setRowData(data);
			countFilteredRowsTax();
		});
	}
	//changeType
		function changeTypeTax() {
			viewAllTaxData();
	}
		
		//changeMonthYear
		function changeMonthYearPTax(){
				var curdate = new Date();
				var attnyear = $("#attndyearPT").val();
				var month = $("#attndmonthPT").val();
				var date = curdate.getDate();
				var mnth = curdate.getMonth();
				var sday = $("#startDayForAttenPT").val();
				var mt=month-1;
			if(attnyear>curdate.getFullYear()){
					toastr.success("Please Choose Current Year Or Previous Year!")
					$("#attndyearPT").val(curdate.getFullYear())
					getCurrentDateMonthYear6();
					
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
						$("#attndmonthPT").val(month + 1);
					} else {
						$("#attndmonthPT").val(month);
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

					$("#fromDatePT").val(fromdate);
					$("#fromDateCalendar").val(fromdate);
					$("#endDatePT").val(todate);
					$("#endDateCalendar").val(todate);
				}else{
					getPTaxYrMtData();
					viewAllTaxData();
					}
				}else{
					getPTaxYrMtData();
					viewAllTaxData();
				}
			}
		}
 

function downloadPTCSV() { 
	var rowCount = 0;
	gridOptionsTax.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
	    if (rowNode.data.empId) {
	        rowCount++;
	    }
	});
	if (rowCount > 0) {
		var name = "PT -" + $("#attndyearPT option:selected").text() +"-"+ $("#attndmonthPT option:selected").text();
	    var allColumns = gridOptionsTax.columnApi.getAllColumns();

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
	    gridOptionsTax.api.exportDataAsCsv(params);
	} else {
		toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
	    
	}
}


function downloadPdf(){
	var stafftype = $("#stafftype").val();
	var employedBy = $("#employedBy").val();
	var fromDate = $("#fromDatePT").val();
	var toDate = $("#endDatePT").val();
var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
	// Construct the URL with query parameters
	var url = "payroll-report-professional-tax-Pdf?stafftype=" + encodeURIComponent(stafftype) +
	          "&employedBy=" + encodeURIComponent(employedBy) +
	          "&fromDate=" + encodeURIComponent(fromDate) +
	          "&toDate=" + encodeURIComponent(toDate)+
 			  "&id=" + encodeURIComponent(id);

	// Open the URL in a new tab
	window.open(url, '_blank');

	
}


//getTaxYrMtData
		//getTaxYrMtData
		function getPTaxYrMtData(){
			var curdate = new Date();
			var month = $("#attndmonthPT").val();
			var sday = $("#startDayForAttenPT").val();
			var attnyear = $("#attndyearPT").val();
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

			$("#fromDatePT").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDatePT").val(todate);
			$("#endDateCalendar").val(todate);
		}