// SALARY APPROVE STARTS

	$(document).ready(function() {
		getCurrentDateMonthYear2();
		var gridDiv = document.querySelector('#myGridsalApprove');
		new agGrid.Grid(gridDiv, gridOptionssalApprove);
		viewAllApproveData();
	})
	var columnDefssalApprove = [
				
				{
					headerName : "Employee ID",
					field : "empId",
					pinned : 'left',
					cellStyle : {
						textAlign : 'left'
					},
					width : 150,
				}, {
					headerName : "Name",
					field : "empName",
					pinned : 'left',
					cellStyle : {
						textAlign : 'left'
					},
					width : 150,
				}, {
					headerName : "Designation",
					field : "desigName",
					pinned : 'left',
					cellStyle : {
						textAlign : 'left'
					},
					width : 150,
				}, {
					headerName : "Department",
					field : "dept",
					hide: true,
					cellStyle : {
						textAlign : 'left'
					},
					width : 150,
				}, {
					headerName : "Workdays",
					field : "workDay",
					cellStyle : {
						textAlign : 'center'
					},
					width : 120,
				}, {
					headerName : "Present",
					field : "present",
					cellStyle : {
						textAlign : 'center'
					},
					width : 120,
				}, {
					headerName : "Leave",
					field : "leave",
					cellStyle : {
						textAlign : 'center'
					},
					width : 120,
				}, {
					headerName : "OFF Availed",
					field : "offday",
					cellStyle : {
						textAlign : 'center'
					},
					width : 130,
				}, {
					headerName : "Effective Workdays",
					field : "workingDay",
					cellStyle : {
						textAlign : 'center'
					},
					width : 180,
				}, {
					headerName : "Attendance(%)",
					field : "attendance",
					cellStyle : {
						textAlign : 'center'
					},
					width : 140,
				}, {
					headerName : "Basic",
					field : "basic",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
	
				}, {
					headerName : "Hra",
					field : "hra",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
	
				}, {
					headerName : "Conveyance Allowance",
					field : "conve",
					valueFormatter: indianNumberFormatter,
					width : 220,
					type : 'rightAligned',
				}, {
					headerName : "Washing Allowance",
					field : "washAllow",
					valueFormatter: indianNumberFormatter,
					width : 190,
					type : 'rightAligned',
				}, {
					headerName : "Medical Allowance",
					field : "medical",
					valueFormatter: indianNumberFormatter,
					type : 'rightAligned',
					width : 180,
				}, {
					headerName : "Special Allowance",
					field : "specialAllowance",
					valueFormatter: indianNumberFormatter,
					width : 180,
					type : 'rightAligned',
				}, {
					headerName : "Skill Development",
					field : "skillDev",
					valueFormatter: indianNumberFormatter,
					width : 180,
					type : 'rightAligned',
				}, {
					headerName : "Other Allowance",
					field : "otherAllow",
					valueFormatter: indianNumberFormatter,
					width : 160,
					type : 'rightAligned',
				}, {
					headerName : "Arear",
					field : "arear",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Bonus",
					field : "bonus",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Reward",
					field : "reward",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Over Time",
					field : "overTime",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				},{
					headerName : "Total Earning",
					field : "totalEarning",
					valueFormatter: indianNumberFormatter,
					width : 170,
					type : 'rightAligned',
	
				}, {
					headerName : "Epf",
					field : "empEPF",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
	
				}, {
					headerName : "Esi",
					field : "empESI",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
	
				}, {
					headerName : "Professional Tax",
					field : "profTax",
					valueFormatter: indianNumberFormatter,
					width : 170,
					type : 'rightAligned',
	
				}, {
					headerName : "TDS",
					field : "incTax",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Advance",
					field : "advance",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "LIC",
					field : "lic",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Wel. fund",
					field : "welfund",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Penalty",
					field : "otherpenamnt",
					valueFormatter: indianNumberFormatter,
					width : 120,
					hide: true,
					type : 'rightAligned',
				}, {
					headerName : "Other",
					field : "other",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Total Deduction",
					field : "totalDeduction",
					valueFormatter: indianNumberFormatter,
					width : 180,
					type : 'rightAligned',
	
				}, {
					headerName : "Net Pay",
					field : "netPay",
					valueFormatter: indianNumberFormatter,
					width : 120,
					type : 'rightAligned',
				}, {
					headerName : "Employer Epf",
					field : "compEPF",
					valueFormatter: indianNumberFormatter,
					width : 150,
					type : 'rightAligned',
				}, {
					headerName : "Admin Charges",
					field : "adminCharge",
					valueFormatter: indianNumberFormatter,
					width : 150,
					type : 'rightAligned',
				}, {
					headerName : "EDLI Charges",
					field : "edliCharge",
					valueFormatter: indianNumberFormatter,
					width : 130,
					type : 'rightAligned',
				}, {
					headerName : "Employer Esi",
					field : "compESI",
					valueFormatter: indianNumberFormatter,
					width : 150,
					type : 'rightAligned',
				}, {
					headerName : "Sub Department",
					field : "subDept",
					hide: true,
					width : 160,
				}, {
					headerName : "Father Name",
					field : "fatherName",
					hide: true,
					width : 150,
				} ];

	var rowData = [ {
	    	empId: 'Total'
	    },];
	var gridOptionssalApprove = {
		columnDefs : columnDefssalApprove,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 410,
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
	
	function onQuickFilterChangedsalApprove() {
		gridOptionssalApprove.api
				.setQuickFilter(document.getElementById('quickFiltersalApprove').value);
		countFilteredRowssalApprove();
		   const filteredRows = gridOptionssalApprove.api.getDisplayedRowCount();
		    $('#salapproved').find('span').html(filteredRows);
	}
	function countFilteredRowssalApprove() {
        var filteredRowCount = 0;
        var data = [];
        gridOptionssalApprove.api.forEachNodeAfterFilter(function (node) {
            filteredRowCount++;
            data.push(node.data);
        });
        var basic = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.basic);
	    }, 0);
		var hra = data.reduce(function (sum, row) {
		    return sum +parseFloat(row.hra);
		}, 0);
	    var conve = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.conve);
	    }, 0);
	    var washAllow = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.washAllow);
	    }, 0);
	    var medical = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.medical);
	    }, 0);
	    var specialAllowance = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.specialAllowance);
	    }, 0);
	    var skillDev = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.skillDev);
	    }, 0);
	    var otherAllow = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.otherAllow);
	    }, 0);
	    var arear = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.arear);
	    }, 0);
	    var bonus = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.bonus);
	    }, 0);
	    var reward = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.reward);
	    }, 0);
	    var overTime = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.overTime);
	    }, 0);
	    var totalEarning = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.totalEarning);
	    }, 0);
	    
	    var empEPF = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.empEPF);
	    }, 0);
	    var empESI = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.empESI);
	    }, 0);
	    var profTax = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.profTax);
	    }, 0);
	    var incTax = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.incTax);
	    }, 0);
	    var advance = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.advance);
	    }, 0);
	    var lic = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.lic);
	    }, 0);
	    var welfund = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.welfund);
	    }, 0);
	    var otherpenamnt = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.otherpenamnt);
	    }, 0);
	    var other = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.other);
	    }, 0);
	    var totalDeduction = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.totalDeduction);
	    }, 0);
	    
	    var netPay = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.netPay);
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
	    var compESI = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.compESI);
	    }, 0);


	    var workDay = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.workDay);
	    }, 0);
	    
	    var present = data.reduce(function (sum, row) {
	        return sum + parseFloat(row.present);
	    }, 0);
	    
	    var  offday= data.reduce(function (sum, row) {
	        return sum + parseFloat(row.offday);
	    }, 0);
	    
	    var  workingDay= data.reduce(function (sum, row) {
	        return sum + parseFloat(row.workingDay);
	    }, 0);
	    var  leave= data.reduce(function (sum, row) {
	        return sum + parseFloat(row.leave);
	    }, 0);
	    
	    var  attendance= parseFloat(workingDay/workDay * 100 || 0).toFixed(2);

	    
	    var totalRow = [{
	    		empId: 'Total',
	    		basic: basic.toFixed(2),
	    		hra: hra.toFixed(2),
	    		conve: conve.toFixed(2),
	    		washAllow: washAllow.toFixed(2),
	    		medical: medical.toFixed(2),
	    		specialAllowance: specialAllowance.toFixed(2),
	    		skillDev: skillDev.toFixed(2),
	    		otherAllow: otherAllow.toFixed(2),
	    		arear: arear.toFixed(2),
	    		bonus: bonus.toFixed(2),
	    		reward: reward.toFixed(2),
	    		overTime: overTime.toFixed(2),
	    		totalEarning: totalEarning.toFixed(2),
	    		empEPF: empEPF.toFixed(2),
	    		empESI: empESI.toFixed(2),
	    		profTax: profTax.toFixed(2),
	    		incTax: incTax.toFixed(2),
	    		advance: advance.toFixed(2),
	    		lic: lic.toFixed(2),
	    		welfund: welfund.toFixed(2),
	    		otherpenamnt: otherpenamnt.toFixed(2),
	    		other: other.toFixed(2),
	    		totalDeduction: totalDeduction.toFixed(2),
	    		netPay: netPay.toFixed(2),
	    		compEPF: compEPF.toFixed(2),
	    		adminCharge: adminCharge.toFixed(2),
	    		edliCharge: edliCharge.toFixed(2),
	    		compESI: compESI.toFixed(2),
	    		compEPF: compEPF.toFixed(2),
	    		workDay: workDay,
	    		present: present,
	    		offday: offday,
	    		workingDay: workingDay,
	    		//attendance: attendance,
	    		leave: leave

	    }];
	    console.log("addedDataSALARY======>>>>>>>>",totalRow);
	    gridOptionssalApprove.api.setPinnedBottomRowData(totalRow);
    }

	function cancelBarsalApprove() {
		var id = document.getElementById("closeKeysalApprove");
		id.style.display = "block";

		if ($('#quickFiltersalApprove').val() == null || $('#quickFiltersalApprove').val() == "") {
			id.style.display = "none";
		}
}
	
	//changeMonthYear
	function changeMonthYear2(){
			var curdate = new Date();
			var attnyear = $("#attndyear2").val();
			var month = $("#attndmonth2").val();
			var date = curdate.getDate();
			var mnth = curdate.getMonth();
			var sday = $("#startDayForAtten2").val();
			var mt=month-1;
		if(attnyear>curdate.getFullYear()){
			toastr.success("Please Choose Current Year Or Previous Year!")
				
				$("#attndyear").val(curdate.getFullYear())
				getCurrentDateMonthYear2();
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
					$("#attndmonth2").val(month + 1);
				} else {
					$("#attndmonth2").val(month);
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

				$("#fromDate2").val(fromdate);
				$("#fromDateCalendar").val(fromdate);
				$("#endDate2").val(todate);
				$("#endDateCalendar").val(todate);
			}else{
				getApproveYrMtData2();
				viewAllApproveData();
			}
		}else{
			getApproveYrMtData2();
			viewAllApproveData();
		}
		}
	}	
	 function getCurrentDateMonthYear2() {
			var curdate = new Date();
			var date = curdate.getDate();
			var mnth = curdate.getMonth();
			$("#attndyear2").val(curdate.getFullYear())
			var sday = $("#startDayForAtten2").val();
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
				$("#attndmonth2").val(month + 1);
			} else {
				$("#attndmonth2").val(month);
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
			$("#fromDate2").val(fromdate);
			$("#fromDateCalendar").val(fromdate);
			$("#endDate2").val(todate);
			$("#endDateCalendar").val(todate);
		}
	    
	  
	
	
	//viewAllApproveData
	function viewAllApproveData(){
		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDate2").val();
		var toDate = $("#endDate2").val();
		 var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		
		$('.loader').show();
		agGrid.simpleHttpRequest(
				{
					url : "payroll-report-view-approve-view-details?fromDate="
							+ fromDate + "&toDate=" + toDate+ "&stafftype=" + stafftype+"&employedBy="+employedBy+"&id="+id
				}).then(function(data) {
					$('.loader').show();
					var len = data.length;
					$('#salapproved').find('span').html(len);
				    $('.loader').hide();
				    gridOptionssalApprove.api.setRowData(data);
				    countFilteredRowssalApprove();
		});
	}
	//changeBand
	function changeTypeApprove() {
		viewAllApproveData();
	}
	//get approve Data
	function getApproveYrMtData2(){
		var curdate = new Date();
		var month = $("#attndmonth2").val();
		var sday = $("#startDayForAtten2").val();
		var attnyear = $("#attndyear2").val();
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

		$("#fromDate2").val(fromdate);
		$("#fromDateCalendar").val(fromdate);
		$("#endDate2").val(todate);
		$("#endDateCalendar").val(todate);
	}

	
	function downloadApproveCSV() {
	    var rowCount = 0;
	    var csvContent = "";
	    gridOptionssalApprove.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
	        if (rowNode.data.empId) {
	            rowCount++;
	        }
	    });
	    
	    if (rowCount > 0) {
	        var name = "Approve Salary -" + $("#attndmonth option:selected").text();
	        var allColumns = gridOptionssalApprove.columnApi.getAllColumns();
	        
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
	        
	        // Create CSV header
	        var headers = visibleColumns.map(col => '"' + col.getColDef().headerName + '"');
	        csvContent += headers.join(',') + '\n';
	        
	        // Create CSV data rows
	        gridOptionssalApprove.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
	            if (rowNode.data.empId) {
	                var data = visibleColumnIds.map(colId => '"' + rowNode.data[colId] + '"');
	                csvContent += data.join(',') + '\n';
	            }
	        });
	        
	        // Create a Blob with the CSV content
	        var blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
	        var link = document.createElement("a");
	        if (link.download !== undefined) {
	            var url = URL.createObjectURL(blob);
	            link.setAttribute("href", url);
	            link.setAttribute("download", name + ".csv");
	            link.style.visibility = 'hidden';
	            document.body.appendChild(link);
	            link.click();
	            document.body.removeChild(link);
	        }
	    } else {
	    	toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
	       
	    }
	}
	function totalUserApprvCSV() {

		var stafftype = $("#stafftype").val();
		var employedBy = $("#employedBy").val();
		var fromDate = $("#fromDate2").val();
		var toDate = $("#endDate2").val();

		var y=toDate.split("-");
		var m = $("#attndmonth2").val();
		var stafftype = $("#stafftype").val();
		const monthnamelist = ["January","February","March","April","May","June","July","August","September","October","November","December"];
		let monthname = monthnamelist[m-1];
	 	var monthYear=monthname+" "+y[2];
		var fromdate1 = fromDate.split("-").reverse().join("-");
		var todate1 = toDate.split("-").reverse().join("-");
		var date1 = new Date(fromdate1);
		var date2 = new Date(todate1);
		var diffDays = ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24))+1;
		var selectedNodes = gridOptionsEmployee.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);
			var id = selectedData.map(node => node.employeeId);
	    window.open("payroll-report-view-approve-view-details-excel?fromDate="
				+ fromDate + "&toDate=" + toDate+ "&stafftype=" + stafftype+"&employedBy="+employedBy+"&monthYear="+monthYear+"&days="+parseInt(diffDays) + "&id="+id);
		
		
				if (response.message == "Success") {
					toastr.success("NO DATA AVAILABLE FOR DOWNLOAD!")
				}
			
		} 
	
	/************************** Salary Approved Script End  ****************************/