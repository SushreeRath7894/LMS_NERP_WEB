/**
 * 
 */
 $(document).ready(function() {
						var gridDiv = document.querySelector('#myGridpayslip');
						new agGrid.Grid(gridDiv, gridOptionspayslip);						
						$("#downloadpayslp").attr('disabled',true);
						getCurrentDateMonthYear();
						checkPayslipEligibility();
						viewPlaySlipView();
						checkPayslipEligibility();
					});
	var columnDefspayslip = [{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		rowSelection: 'single',
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},{
				headerName : "Employee ID",
				field : "employeeNo",
				width : 200,
			},
			{
				headerName : "Employee Name",
				field : "name",
				cellStyle : {
					textAlign : 'left'
				},
				width : 300,
			},
			{
				headerName : "DepartMent",
				field : "department",
				cellStyle : {
					textAlign : 'left'
				},
				width : 210,
			},
			{
				headerName : "PaySlip For",
				field : "month",
				cellStyle : {
					textAlign : 'center'
				},
				width : 230,
			},
			{
				headerName : "From Date",
				field : "fromDate",
				cellStyle : {
					textAlign : 'center'
				},
				width : 210,
			},
			{
				headerName : "To Date",
				field : "toDate",
				cellStyle : {
					textAlign : 'center'
				},
				width : 210,
			},
			{
				headerName : "Payslip",
				cellStyle : {
					textAlign : 'center'
				},
				cellRenderer : function(params) {
					var s = "";
					s = ' <a href="#" class="grn-btn" onclick="payslipPdfDownload(\''
							+ params.data.employeeNo
							+ '\',\''
							+ params.data.fromDate
							+ '\',\''
							+ params.data.toDate
							+ '\')"><i class="ti ti-download"></i> Payslip</a>';
					return s;
				},
				width : 250,
			} ];

	var gridOptionspayslip = {
		columnDefs : columnDefspayslip,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 150,
			height : 30
		},
		rowSelection : 'multiple',
		rowMultiSelectWithClick : true,
		onSelectionChanged : paySlipOnChange,
	};
	
	
	
	function payslipPdfDownload(empId, fromDate, toDate) {
		
		if(empId.slice(0,1) != '('){
			var emplist = '("' + empId + '")';
		}else{
			var emplist = empId;
		}
		if(toDate.slice(0,1) != '('){
			let parts = toDate.split("-");
			let formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
			 toDate = '("' + formattedDate + '")';
		}
		 var organization = $("#sessionOrganization").val();
		var orgDivision = $("#sessionOrgDivision").val();
		
		console.log(emplist)
		window.open("/master/payslip-pdf-download?fromDate="
				+ window.btoa(fromDate) + "&toDate=" + window.btoa(toDate)
				+ "&empId=" + window.btoa(emplist) + "&organization="
				+ window.btoa(organization) + "&orgDivision="
				+ window.btoa(orgDivision), '_blank'); 
	}
	
	/* -------------------search bar for mygrid------------------------ */

	function onQuickFilterChangedpayslip() {
		gridOptionspayslip.api
				.setQuickFilter(document.getElementById('quickFilterpayslip').value);
		 const filteredRows = gridOptionspayslip.api.getDisplayedRowCount();
		    $('#payslipcnt').find('span').html(filteredRows);
	}


	function cancelBarpayslip() {
		var id = document.getElementById("closeKeypayslip");
		id.style.display = "block";

		if ($('#quickFilterpayslip').val() == null || $('#quickFilterpayslip').val() == "") {
			id.style.display = "none";
		}
	}
	function calculation() {

		var total1 = 0.0;
		var total2 = 0.0;
		var basic = $("#basic").html();
		var hra = $("#hra").html();
		var additionalallowance = $("#additionalallowance").html();
		var medical = $("#medical").html();
		var lta = $("#lta").html();
		var variablepay = $("#variablepay").html();
		total1 = parseFloat(basic) + parseFloat(hra)
				+ parseFloat(additionalallowance) + parseFloat(medical)
				+ parseFloat(lta) + parseFloat(variablepay);

		var totalEarn = $("#totalearning").html(total1);

		var pf = $("#pf").html();
		var proftax = $("#proftax").html();
		var incometax = $("#incometax").html();
		var esic = $("#esic").html();
		var bonus = $("#bonus").html();
		var others = $("#others").html();

		total2 = parseFloat(pf) + parseFloat(proftax) + parseFloat(incometax)
				+ parseFloat(esic) + parseFloat(bonus) + parseFloat(others);

		var totalDeduction = $("#totalDeductions").html(total2);

	}

	function changeEmployee() {

		var empId = $("#name").val();
		var fromDate = $("#fromDate").val();
		var toDate = $("#endDate").val();
		var organization = $("#sessionOrganization").val();
		var orgDivision = $("#sessionOrgDivision").val();
		$.ajax({
			type : 'GET',
			url : "/master/view-payslip-personal-details?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&empId=" + empId
					+ "&organization=" + organization + "&orgDivision="
					+ orgDivision,
			contentType : false,
			success : function(response) {
				if (response.code == "success") {
					$("#empNo").html(response.body.employeeNo);
					$("#location").html(response.body.location);
					$("#Empname").html(response.body.name);
					$("#department").html(response.body.department);
					$("#bankName").html(response.body.bankName);
					$("#designation").html(response.body.designation);
					$("#bankAccountno").html(response.body.bankAccountno);
					$("#daysinMonth").html(response.body.daysinMonth);
					$("#esicNo").html(response.body.esicNo);
					$("#lop").html(response.body.lop);
					$("#pfuan").html(response.body.pfuan);
					$("#effectiveWorkdays").html(
							response.body.effectiveWorkdays);
					$("#panNo").html(response.body.panNo);
					$("#dob").html(response.body.dob);

					$("#basic").html(response.body.basic);
					$("#hra").html(response.body.hra);
					$("#additionalallowance").html(
							response.body.additionalallowance);
					$("#conve").html(response.body.conve);
					$("#others").html(response.body.others);
					$("#variablepay").html(response.body.variablepay);
					$("#pf").html(response.body.pf);
					$("#proftax").html(response.body.proftax);
					$("#incometax").html(response.body.incometax);
					$("#esic").html(response.body.esic);
					$("#advance").html(response.body.advance);
					$("#totalearning").html(response.body.totalearning);
					$("#totalDeductions").html(response.body.totalDeductions);
					$("#totalpay").html(response.body.totalpay);
					//calculation();

				}
			},
			error : function(e) {
				alert("error");
			}
		});

	}
	
	function checkPayslipEligibility() {
		$.ajax({
			type : 'GET',
			url : '/master/view-payslip-checkPayslipEligibility',
			contentType : false,
			success : function(response) {
				if (response.message == "Success") {
					//$("#name").val(response.body.key);
					if (response.body.name == "0") {
						$("#downloadpayslp").hide();
						$("#attndmonth").attr('disabled', true);
						toastr.success("YOU ARE NOT ELIGIBLE FOR PAYSLIP")
						
					} else {
						$("#downloadpayslp").show();
					}
				}
			},
			error : function(e) {
			}
		});
	}
	
	function downloadPayslipListCSV() {
		
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var empSort = selectedData?.map(node => node.employeeId);
		//var empSort = $("#empSort").val();
		if(empSort){
			var emplist = "";
			var fromDate = $("#fromDatePS").val();
			gridOptionspayslip.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			    emplist += '"' + rowNode.data.employeeNo + '",';
			});
			var selectedRows = gridOptionspayslip.api.getSelectedRows();
			var dateList = "";
			selectedRows.forEach(function(rowNode) {
				let parts = rowNode.toDate.split("-");
				let formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
				dateList += '"' + formattedDate + '",';
			});
			dateList = '(' + dateList.substring(0, dateList.length - 1) + ')';
			emplist = '(' + emplist.substring(0, emplist.length - 1) + ')';
			payslipPdfDownload(emplist, fromDate, dateList);
		}else{
			toastr.success("Select Employee From Dropdown")
				
				
		}
	}
	
	function paySlipOnChange(){
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var empSort = selectedData?.map(node => node.employeeId);
		//var empSort = $("#empSort").val();
		if(empSort){
		 var selectedRows = gridOptionspayslip.api.getSelectedRows();
		    var rowCount = selectedRows.length;
		    if (rowCount > 0) {
		    	$("#downloadpayslp").attr('disabled',false);
		    }else{
		    	$("#downloadpayslp").attr('disabled',true);
		    }
		} else{
			toastr.success("Select Employee From Dropdown")
				
				
		}
	}
	
	function viewPlaySlipView(){
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
		var selectedData = selectedNodes?.map(node => node.data);
		var id = selectedData?.map(node => node.employeeId);
		
		$('.loader').show();
		var empId = $("#name").val();
		var fromDate = $("#fromDatePS").val();
		var toDate = $("#endDatePS").val();
		agGrid.simpleHttpRequest({
			url : "payroll-report-view-payslip-list?empId=" + empId +"&fromDate=" + fromDate
				+ "&toDate=" + toDate + "&id=" + id,
		}).then(function(data) {
			$('.loader').hide();
			var len = data.length;
			$('#payslipcnt').find('span').html(len);
			gridOptionspayslip.api.setRowData(data);
		});
		}
//
function changeMonthTPS2() {
		var curdate = new Date();
		var attnyear = $("#attndyearPS2").val();
		var sday = $("#startDayForAttenPS").val();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();

		if($("#attndmonthPS2").val() === "all"){
			var year = $("#attndyearPS2").val();

			var todate = "31-12-" + year;
			$("#endDatePS").val(todate);
			$("#endDateCalendarPS").val(todate);
			getCurrentDateMonthYear();
			
		}else if (attnyear > curdate.getFullYear()) {
			$("#messageParagraph").text(
					"Please Choose Current Year Or Previous Year!");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$("#attndyearPS2").val(curdate.getFullYear())
			getCurrentDateMonthYear();
		} else {
			if (attnyear == curdate.getFullYear()) {
				var month = $("#attndmonth1").val();
				var mt = month - 1;
				if (mt > curdate.getMonth()) {
					$("#messageParagraph").text(
							"Please Choose Current month Or Previous Month!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
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
						$("#attndmonthPS2").val(month + 1);
					} else {
						$("#attndmonthPS2").val(month);
					}
					const lastDayOfMonth = new Date(year, month, 0).getDate();
					if (sday > 1) {
						if (month == "12") {
							var todate = (parseInt(sday) - 1) + "-"
									+ String(1).padStart(2, '0') + "-"
									+ (year + 1);
						} else {
							var todate = (parseInt(sday) - 1) + "-"
									+ String(nextmonth).padStart(2, '0') + "-"
									+ year;
						}
					} else {
						var todate = lastDayOfMonth + "-"
								+ String(month).padStart(2, '0') + "-" + year;
					}

					$("#endDatePS").val(todate);
					$("#endDateCalendarPS").val(todate);
				} else {
					getprocessYrMtDataPS2();
					
				}
			} else {
				getprocessYrMtDataPS2()
				
			}
		}
		viewPlaySlipView();
	}
	function changeMonthFPS1() {
		
		var curdate = new Date();
		var attnyear = $("#attndyearPS1").val();
		var sday = $("#startDayForAttenPS").val();
		var date = curdate.getDate();
		var mnth = curdate.getMonth();

		if($("#attndmonthPS1").val() === "all"){
			var year = $("#attndyearPS1").val();
			var year1 = $("#attndyearPS2").val();
			var fromdate = "01-01-" + year;
			var todate = "31-12-" + year1;

			$("#fromDatePS").val(fromdate);
			$("#fromDateCalendarPS").val(fromdate);
			$("#endDatePS").val(todate);
			$("#endDateCalendarPS").val(todate);
		//	getCurrentDateMonthYear();
			
		}else if (attnyear > curdate.getFullYear()) {
			$("#messageParagraph").text(
					"Please Choose Current Year Or Previous Year!");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$("#attndyearPS1").val(curdate.getFullYear())
			getCurrentDateMonthYear();
		} else {
			if (attnyear == curdate.getFullYear()) {
				var month = $("#attndmonthPS1").val();
				var mt = month - 1;
				if (mt > curdate.getMonth()) {
					$("#messageParagraph").text(
							"Please Choose Current month Or Previous Month!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
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
						$("#attndmonthPS1").val(month + 1);
					} else {
						$("#attndmonthPS1").val(month);
					}
					var fromdate = sday + "-" + String(month).padStart(2, '0')
							+ "-" + year;
					const lastDayOfMonth = new Date(year, month, 0).getDate();
					if (sday > 1) {
						if (month == "12") {
							var todate = (parseInt(sday) - 1) + "-"
									+ String(1).padStart(2, '0') + "-"
									+ (year + 1);
						} else {
							var todate = (parseInt(sday) - 1) + "-"
									+ String(nextmonth).padStart(2, '0') + "-"
									+ year;
						}
					} else {
						var todate = lastDayOfMonth + "-"
								+ String(month).padStart(2, '0') + "-" + year;
					}

					$("#fromDatePS").val(fromdate);
					$("#fromDateCalendarPS").val(fromdate);
				} else {
					getprocessYrMtDataPS1();
					
				}
			} else {
				
				getprocessYrMtDataPS1()
				
			}
		}

		viewPlaySlipView();
	}

//
function getCurrentDateMonthYear() {
		var curdate = new Date();
		var date = curdate.getDate()
		$("#attndyearPS1").val(curdate.getFullYear())
		$("#attndyearPS2").val(curdate.getFullYear())
		var sday = $("#startDayForAttenPS").val();
		if( $("#attndmonthPS1").val() === "all"){
			var fromdate = "01-01-" + $("#attndyearPS1").val();
			    todate = "31-12-" + $("#attndyearPS1").val(); // Default toDate to 31st December of the current year
		}else{
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
					$("#attndmonthPS1").val(month + 1);
				} else {
					$("#attndmonthPS1").val(month);
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
		}
		$("#fromDatePS").val(fromdate);
		$("#fromDateCalendarPS").val(fromdate);
		$("#endDatePS").val(todate);
		$("#endDateCalendarPS").val(todate);
	}
	
//
	function getprocessYrMtDataPS1() {
		
		var curdate = new Date();
		var month = $("#attndmonthPS1").val();
		var sday = $("#startDayForAttenPS").val();
		var attnyear = $("#attndyearPS1").val();
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

		$("#fromDatePS").val(fromdate);
		$("#fromDateCalendarPS").val(fromdate);
		//$("#endDate").val(todate);
		//$("#endDateCalendar").val(todate);
	}
function getprocessYrMtDataPS2() {
		
		var curdate = new Date();
		var month = $("#attndmonthPS2").val();
		var sday = $("#startDayForAttenPS").val();
		var attnyear = $("#attndyearPS2").val();
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

		$("#endDatePS").val(todate);
		$("#endDateCalendarPS").val(todate);
	}