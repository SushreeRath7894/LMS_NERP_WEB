/**
 * 
 */
	 $(document).ready(function() {
		getCurrentDateMonthYearTDS();
		var gridDiv = document.querySelector('#myGridTds');
		new agGrid.Grid(gridDiv, gridOptionsTds);
		viewAllTdsData();
	})
	var columnDefsTds = [
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
		headerName : "TDS",
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
	var gridOptionsTds = {
		columnDefs : columnDefsTds,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 180,
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

	function onQuickFilterChangedTds() {
		gridOptionsTds.api
				.setQuickFilter(document.getElementById('quickFilterTds').value);
		countFilteredRowsTds();
		 const filteredRows = gridOptionsTds.api.getDisplayedRowCount();
		    $('#tdscnt').find('span').html(filteredRows);
	}
	function countFilteredRowsTds(){
		var filteredRowCount = 0;
        var data = [];
        gridOptionsTds.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
		var salary = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.salary);
	    }, 0);

	    var incTax = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.incTax);
	    }, 0);
	    var totalRow = [{
	    		empId: 'Total',
	    		salary: salary.toFixed(2),
	    		incTax: incTax.toFixed(2),
	    }];
	    gridOptionsTds.api.setPinnedBottomRowData(totalRow);
	}

	function cancelBarTds() {
		var id = document.getElementById("closeKeyTds");
		id.style.display = "block";

		if ($('#quickFilterTds').val() == null || $('#quickFilterTds').val() == "") {
			id.style.display = "none";
		}
	}
//
//getCurrentDateMonthYear
	function getCurrentDateMonthYearTDS(){
		var curdate = new Date();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();
		$("#attndyearTDS").val(curdate.getFullYear())
		var sday = $("#startDayForAttenTDS").val();
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
			$("#attndmonthTDS").val(month + 1);
		} else {
			$("#attndmonthTDS").val(month);
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

		$("#fromDateTDS").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDateTDS").val(todate);
		$("#endDateCalendar").val(todate);	
	}	
	//viewAllEsiData
	function viewAllTdsData(){
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDateTDS").val();
		var toDate = $("#endDateTDS").val();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		$('.loader').show();
	 	agGrid.simpleHttpRequest({
			url : "payroll-report-view-tax-view-details?fromDate="+fromDate+"&toDate="+toDate+"&stafftype="+stafftype
			+"&employedBy="+employedBy+"&id="+id
		}).then(function(data) {
			$('.loader').hide();
			var len = data.length;
			$('#tdscnt').find('span').html(len);
			gridOptionsTds.api.setRowData(data);
			countFilteredRowsTds();
		});
	}
	//changeType
		function changeTypeTds() {
			viewAllTdsData();
	}
		//getTaxYrMtData
		function getYrMtDataTDS(){
			var curdate = new Date();
			var month = $("#attndmonthTDS").val();
			var sday = $("#startDayForAttenTDS").val();
			var attnyear = $("#attndyearTDS").val();
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

			$("#fromDateTDS").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDateTDS").val(todate);
			$("#endDateCalendar").val(todate);
		}
		//changeMonthYear
		function changeMonthYearTDS(){
				var curdate = new Date();
				var attnyear = $("#attndyearTDS").val();
				var month = $("#attndmonthTDS").val();
				var date = curdate.getDate();
				var mnth = curdate.getMonth();
				var sday = $("#startDayForAttenTDS").val();
				var mt=month-1;
			if(attnyear>curdate.getFullYear()){
				toastr.success("Please Choose Current Year Or Previous Year!")
					
					$("#attndyearTDS").val(curdate.getFullYear())
					getCurrentDateMonthYearTDS();
					
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
						$("#attndmonthTDS").val(month + 1);
					} else {
						$("#attndmonthTDS").val(month);
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

					$("#fromDateTDS").val(fromdate);
					$("#fromDateCalendar").val(fromdate);
					$("#endDateTDS").val(todate);
					$("#endDateCalendar").val(todate);
				}else{
					getYrMtDataTDS();
					viewAllTdsData();
					}
				}else{
					getYrMtDataTDS();
					viewAllTdsData();
				}
			}
		}
 

function downloadTaxCSV() { 
	var rowCount = 0;
	gridOptionsTds.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
	    if (rowNode.data.empId) {
	        rowCount++;
	    }
	});
	if (rowCount > 0) {
		var name = "TDS -" + $("#attndyearTDS option:selected").text() +"-"+ $("#attndmonthTDS option:selected").text();
	    var allColumns = gridOptionsTds.columnApi.getAllColumns();

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

	    gridOptionsTds.api.exportDataAsCsv(params);
	} else {
		toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
	    
	}
	}