/**
 * 
 */
 $(document).ready(function() {
			var gridDiv = document.querySelector('#myGridlic');
			new agGrid.Grid(gridDiv, gridOptionslic);
		    getCurrentDateMonthYearLIC();
			viewAllLicData();
	});
	var columnDefslic = [
	{
		headerName : "Employee ID",
		field : "empId",
		flex:1,
	}, {
		headerName : "Employee Name",
		field : "empName",
		cellStyle : {
			textAlign : 'left'
		},
		flex:1.5,
	}, {
		headerName : "DepartMent",
		field : "dept",
		hide: true,
		cellStyle : {
			textAlign : 'left'
		},
		flex:1,
	}, {
		headerName : "Gross pay",
		field : "netPay",
		valueFormatter: indianNumberFormatter,
		cellStyle : {
			textAlign : 'right'
		},
		flex:1,
	}, {
		headerName : "Lic",
		field : "lic",
		valueFormatter: indianNumberFormatter,
		type : 'rightAligned',
		flex:1,
	}, {
		headerName : 'Payment Status',
		field : "paymentStatus",
		cellStyle : {
			textAlign : 'center'
		},
		flex:1,
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
	var gridOptionslic = {
		columnDefs : columnDefslic,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			flex:1,
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

	function onQuickFilterChangedlic() {
		gridOptionslic.api
				.setQuickFilter(document.getElementById('quickFilterlic').value);
		countFilteredRowslic(); 
		const filteredRows = gridOptions.api.getDisplayedRowCount();
	    $('#liccnt').find('span').html(filteredRows);
	}
	function countFilteredRowslic(){
		var filteredRowCount = 0;
        var data = [];
        gridOptionslic.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
		var netPay = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.netPay);
	    }, 0);
	    var lic = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.lic);
	    }, 0);
	    var totalRow = [{
	    		empId: 'Total',
	    		netPay: netPay.toFixed(2),
	    		lic: lic.toFixed(2)
	    }];
	    console.log("addedData",totalRow);
	    gridOptionslic.api.setPinnedBottomRowData(totalRow);
	}

	function cancelBarlic() {
		var id = document.getElementById("closeKeylic");
		id.style.display = "block";

		if ($('#quickFilterlic').val() == null || $('#quickFilterlic').val() == "") {
			id.style.display = "none";
		}
	}
function getCurrentDateMonthYearLIC(){
	var curdate = new Date();
	var date = curdate.getDate();
	var mnth = curdate.getMonth();
	$("#attndyearLIC").val(curdate.getFullYear())
	var sday = $("#startDayForAttenLIC").val();
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
		$("#attndmonthLIC").val(month + 1);
	} else {
		$("#attndmonthLIC").val(month);
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

	$("#fromDateLIC").val(fromdate);
	$("#fromDateCalendar").val(fromdate);
	$("#endDateLIC").val(todate);
	$("#endDateCalendar").val(todate);
}
//viewAllLicData
function viewAllLicData(){
	var stafftype = $("#stafftype").val();
	var employedBy = $("#employedBy").val();
	var fromDate = $("#fromDateLIC").val();
	var toDate = $("#endDateLIC").val();
	var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
	
	$('.loader').show();

	agGrid.simpleHttpRequest(
			{
				url : "payroll-report-view-lic-view-details?fromDate=" + fromDate
						+ "&toDate=" + toDate+ "&employedBy=" + employedBy + "&stafftype=" + stafftype + "&id=" + id
			}).then(function(data) {
				$('.loader').hide();
		        var jsonData = JSON.parse(data.body);
		        var allData = jsonData.viewData;
		        console.log("allData---",allData)
				if(allData){
					var len = allData.length;
					$('#liccnt').find('span').html(len);
					gridOptionslic.api.setRowData(allData);
				}else{
					$('#liccnt').find('span').html('0');
					gridOptionslic.api.setRowData('');
				}
				countFilteredRowslic();
	});
}
//changeType
function changeTypelic() {
	viewAllLicData();
}
//getLicData
function getLicYrMtData(){
	var curdate = new Date();
	var month = $("#attndmonthLIC").val();
	var sday = $("#startDayForAttenLIC").val();
	var attnyear = $("#attndyearLIC").val();
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

	$("#fromDateLIC").val(fromdate);
	$("#fromDateCalendar").val(fromdate);
	$("#endDateLIC").val(todate);
	$("#endDateCalendar").val(todate);
}
//changeMonthYear
function changeMonthYearlic(){
		var curdate = new Date();
		var attnyear = $("#attndyearLIC").val();
		var month = $("#attndmonthLIC").val();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		var sday = $("#startDayForAttenLIC").val();
		var mt=month-1;
	if(attnyear>curdate.getFullYear()){
		toastr.success("Please Choose Current Year Or Previous Year!")
			
			$("#attndyear").val(curdate.getFullYear())
			getCurrentDateMonthYearLIC();
			
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
				$("#attndmonthLIC").val(month + 1);
			} else {
				$("#attndmonthLIC").val(month);
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

			$("#fromDateLIC").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDateLIC").val(todate);
			$("#endDateCalendar").val(todate);
		}else{
			getLicYrMtData();
			viewAllLicData();
			}
		}else{
			getLicYrMtData();
			viewAllLicData();
		}
	}
}
 
	function downloadLicCSV() {
		var rowCount = 0;
		gridOptionslic.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		    if (rowNode.data.empId) {
		        rowCount++;
		    }
		});
		if (rowCount > 0) {
			var name = "Lic Details-" + $("#attndyearLIC option:selected").text() +"-"+ $("#attndmonthLIC option:selected").text();
		    var allColumns = gridOptionslic.columnApi.getAllColumns();

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
		
		    gridOptionslic.api.exportDataAsCsv(params);
		} else {
			toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
		    
		}
	}