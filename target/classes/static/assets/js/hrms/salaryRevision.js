/**
 * 
 */

	 $(document).ready(function() {
		var gridDiv = document.querySelector('#myGridslrev');
		new agGrid.Grid(gridDiv, gridOptionsslrev);

		closeNavSal();	
		$("#save").hide();
		$("#cancel").hide();
		$("#Cancel").hide();
		$("#approvesalrvsn").hide();
		$("#salaryrevid").hide();
		
 		agGrid.simpleHttpRequest(
				{
					url : 'payroll-report-view-salary-revision-bandcalc?band=TJM00002'
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData=jsonData.bandData;
					allData.forEach(function(rowNode,index){
						if(rowNode.component=='EEM001'){
							hideAgData('basic',rowNode.visibility);
						}else if(rowNode.component=='EEM002'){
							hideAgData('hra',rowNode.visibility);
						}else if(rowNode.component=='EEM003'){
							hideAgData('addAllow',rowNode.visibility);
						}else if(rowNode.component=='EEM022'){
							hideAgData('conve',rowNode.visibility);
						}else if(rowNode.component=='EEM005'){
							hideAgData('medical',rowNode.visibility);
						}else if(rowNode.component=='EEM024'){
							hideAgData('washAllow',rowNode.visibility);
						}else if(rowNode.component=='EEM004'){
							hideAgData('lta',rowNode.visibility);
						}else if(rowNode.component=='EEM026'){
							hideAgData('skillDev',rowNode.visibility);
						}else if(rowNode.component=='EEM012'){
							hideAgData('other',rowNode.visibility);
						}else if(rowNode.component=='EEM029'){
							hideAgData('specialallowance',rowNode.visibility);
						}
						
					});
					
				});
				
		//date format Start date
var dateFormat = localStorage.getItem("dateFormat");
		// Initialize effectiveDateFrom date picker
		$("#effectiveDateFromCalendar").datetimepicker({
		    format: dateFormat,
		    closeOnDateSelect: true,
		    timepicker: false,
		    minDate: 0, // Prevent past dates
		}).on("change", function() {
		    var selectedFromDate = $(this).val();
		    $('#effectiveDateFrom').val(selectedFromDate);
		    
		    $("#effectiveDateToCalendar").datetimepicker("setOptions", {
		        minDate: selectedFromDate // Set the minDate to the selected effectiveDateFrom
		    });
		    validateDateRange();
		});

		$('#effectiveDateFrom').blur(function() {
		    $("#effectiveDateFromCalendar").val($(this).val());
		});

		// Initialize effectiveDateTo date picker
		$("#effectiveDateToCalendar").datetimepicker({
		    format: dateFormat,
		    closeOnDateSelect: true,
		    timepicker: false,
		    minDate: 0, // Prevent past dates by default
		}).on("change", function() {
			 $('#effectiveDateTo').val($(this).val());
			 validateDateRange();
		});

		$('#effectiveDateTo').blur(function() {
		    $("#effectiveDateToCalendar").val($(this).val());
		});
		


		$('#deleteslrev').attr('disabled', true);	
		$('#addslrev').attr('disabled', true);
		$('#approveslrev').attr('disabled', true);
	});

	/* -------------------search bar for mygrid1------------------------ */

	function onQuickFilterChangedslrev() {
		gridOptionsslrev.api
				.setQuickFilter(document.getElementById('quickFilterslrev').value);
		var totalRowCount = gridOptionsslrev.api.getModel().getRowCount();
		$('#slrevcnt').find('span').html(totalRowCount);
	}

	function cancelBarslrev() {
		var id = document.getElementById("closeKeyslrev");
		id.style.display = "block";

		if ($('#quickFilterslrev').val() == null || $('#quickFilterslrev').val() == "") {
			id.style.display = "none";
		}
	}

	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	$('.collapse').on('hide.bs.collapse', function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});

	var columnDefsslrev = [{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		//checkboxSelection : true,
		 checkboxSelection: params => params.data.updatedDate === "1", // Only show checkbox if updatedDate is "1"
		width : 10,
		sortable : false,
		filter : false,
		resizable : true,
		pinned : 'left',
	
	},{
		headerName : 'Employee Id',
		field : "empId",
		pinned : 'left',
		width : 120,
		cellRenderer : function(params) {
			return '<a id="" onclick=editSalaryRevision("'
					+ params.data.editId + '","'+ params.data.status
					+ '") href="javascript:void(0)"> '
					+ params.data.empId + '  ' + '<i class="bi bi-pencil-square"></i></a>';
		},
		
	}, {
		headerName : 'Name',
		field : "name",
		pinned : 'left'
	},{
		headerName : ' Designation',
		field : "ndesg"
	},{
		headerName : ' Joining Date',
		field : "doj",
	}, {
		headerName : 'Effective Date From ',
		field : "effectiveFromDate",
		width : 190
	},{
		headerName : 'Effective Date To',
		field : "effectiveToDate",
		width : 180
	}, {
		headerName : 'Band/Grade',
		field : "band",
		width : 130
	},  {
		headerName : 'Basic',
		field : "basic",
		valueFormatter: indianNumberFormatter,
	 	type : "rightAligned",
		width : 100
	}, {
		headerName : 'House Rent Allowance(HRA)',
		field : "hra",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 240
	},{
		headerName : 'Conveyance Allowance',
		field : "convAllow",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 220
	}, {
		headerName : 'Washing Allowance',
		field : "washAllow",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 200
	}, {
		headerName : 'Special Allowance',
		field : "specialallowance",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 200
	}, {
		headerName : 'Medical Allowance',
		field : "medAllow",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 200
	}, {
		headerName : 'Skill Development',
		field : "skillDev",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 200
	},{
		headerName : 'Gross Salary',
		field : "totalEarn",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 130
	}, {
		headerName : 'ESI Employee',
		field : "esi",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	},{
		headerName : 'Provident Fund',
		field : "providentFund",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	}, {
		headerName : 'Professional Tax',
		field : "pTax",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 180
	}, {
		headerName : 'LIC/Insurance',
		field : "lic",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	},{
		headerName : 'Wel Fund',
		field : "wFund",
		type : "rightAligned",
		width : 150
	},{
		headerName : 'Total Deduction',
		field : "totalDeduct",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 200
	},{
		headerName : 'Net Salary',
		field : "netPay",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	},{
		headerName : 'ESI Employer',
		field : "esicWage",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	}  ,{
		headerName : 'Employer PF',
		field : "mEmployerPf",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	} ,{
		headerName : 'salary difference',
		field : "salary_difference",
		valueFormatter: indianNumberFormatter,
		type : "rightAligned",
		width : 150
	} ,{
		headerName : "Report",
		cellStyle : {
			textAlign : 'center'
		},
		cellRenderer : function(params) {
				var s = "";
			if(params.data.updatedDate == "1" && params.data.salary_difference > 0 ){
				s = ' <a href="#" class="repo-btn" onclick="reportDownload(\''
					+ params.data.empId
					+ '\',\''
					+ params.data.effectiveFromDate
					+ '\',\''
					+ params.data.effectiveToDate
					+ '\')"><i class="ti ti-download"></i> Report </a>';
			return s;
			}else{
				return s;
			}
				
		
		},
	},{
		headerName : 'Status',
		field : "status",
		width : 100,
		cellRenderer : function(params) {
			if (params.data.status == 1) { 
				return '<div style="color:#0642f5">Approved</div>';
			} else{
				return '<div style="color:#a9a9a9">Pending</div>';
			}
		}
	} ];

	var gridOptionsslrev = {
		columnDefs : columnDefsslrev,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 180,
			height : 10
		},
		rowSelection : 'single',
		onSelectionChanged : rowSelectDataslrev,
		suppressRowClickSelection : true,
		getRowNodeId : function(data) {
			return data.editId;
		}
	}
	function rowSelectDataslrev() {
		var selectedRows = gridOptionsslrev.api.getSelectedRows();

		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount == 1) {
			var sts = selectedRows[0].status;
			if(sts != 0){
				$('#approveslrev').attr("disabled", true);
				$('#addslrev').attr("disabled", false);
				$('#deleteslrev').attr("disabled", true);

			}else{
				$('#approveslrev').attr("disabled", false);
				$('#addslrev').attr("disabled", true);
				$('#deleteslrev').attr("disabled", false);

			}
		}else if(rowCount > 1){
			var count = 0;
			var c = 0;
			selectedRows.forEach(function(rowNode) {
				c++;
			    if (rowNode.status != 0) {
			        count++;
			    }
			});
			$('#addslrev').attr("disabled", true);
			if(c == count){
				$('#approveslrev').attr("disabled", true);
			}else{
				$('#approveslrev').attr("disabled", false);
			}
		}else {
			$('#deleteslrev').attr("disabled", true); 
			$('#addslrev').attr("disabled", true);
			$('#approveslrev').attr("disabled", true);
		}

	}
	
	
function reportDownload(empId,effectiveFromDate,effectiveToDate){
		
		window.open("/master/view-salary-revision-pdf-download?empId="
				+ window.btoa(empId) + "&effectiveFromDate=" + window.btoa(effectiveFromDate)
				+ "&effectiveToDate=" + window.btoa(effectiveToDate), '_blank');
	}

	function add() {
		$("#empId").val("");
		$("#name").val("");
		$("#pdesg").val("");
		$("#ndesg").val("");
		$("#effectiveDate").val("");
		$("#band").val("");
		$("#basic").val("");
		$("#hra").val("");
		$("#addAllow").val("");
		$("#conve").val("");
		$("#other").val("");
		$("#specialallowance").val("");
		$("#editId").val("");
		$("#dept").val("");
		$("#subDept").val("");
		$("#ctc").val("");

		$("#deleteslrev").hide();
		$("#addData").show();
		$("#addslrev").hide();

		//$("#save").hide();
		$("#myGridslrev").hide();
		$("#tab1").show();
		$("#save").show();
		$("#submitBtnBtn").show();
		$("#Cancel").show();
		$("#hideTbl").hide();

	}
	function updateTotalEarning(){
		var bandSalary=$("#band").val();
		if(bandSalary=='' || bandSalary==null){
			toastr.error("Please Choose Band First!!")
			
		}else{
				var basic = parseFloat(document.getElementById("basic").value) || 0;
			//	var da = parseFloat(document.getElementById("da").value) || 0;
				var hra = parseFloat(document.getElementById("hra").value) || 0;
				var convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
				var specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
				var skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
				var medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
				var washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
						
				var totalEarnings = basic + hra + convAllow + specialallowance + skillDev + 
						medAllow + washAllow ; 
			
				$("#totalEarn").val(totalEarnings);
			}
		}
		function updateTotalDeducts(){
			var bandSalary=$("#band").val();
			if(bandSalary=='' || bandSalary==null){
				toastr.error("Please Choose Band First!!")
				
			}else{
				var providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
			//	var tds = parseFloat(document.getElementById("tds").value) || 0;
				var esi = parseFloat(document.getElementById("esi1").value) || 0;
				var pTax = parseFloat(document.getElementById("pTax").value) || 0;
			//	var salAdv = parseFloat(document.getElementById("salAdv").value) || 0;
				var wFund = parseFloat(document.getElementById("wFund").value) || 0;
			//	var insAmt = parseFloat(document.getElementById("insAmt").value) || 0;
				var lic = parseFloat(document.getElementById("lic").value) || 0;
							
				var totalDeductss = providentFund + esi + pTax  + lic + wFund ;
			$("#totalDeduct").val(totalDeductss);
			}
		}
		function updateTotalContribution(){
		
			var esicWage = parseFloat(document.getElementById("esicWage").value) || 0;
		//	var yGratuity = parseFloat(document.getElementById("yGratuity").value) || 0;
		//	var mGratuity = parseFloat(document.getElementById("mGratuity").value) || 0;
			var mEmployerPf = parseFloat(document.getElementById("mEmployerPf").value) || 0;

			var totalContribution =  esicWage + mEmployerPf;
			// mBonus + pfWages + pensionWage + wageChecking + ptwage + epsEmployer + edliWage +  yGratuity + mGratuity +
			$("#totalContribution").val(totalContribution);
		}
		function updateNetPay(){
			var bandSalary=$("#band").val();
			if(bandSalary=='' || bandSalary==null){
				toastr.error("Please Choose Band First!!")
				
			}else{
				var basic = parseFloat(document.getElementById("basic").value) || 0;
				var hra = parseFloat(document.getElementById("hra").value) || 0;
				var convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
				var specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
				var skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
				var medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
				var washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
			
				var totalEarnings = basic + hra + convAllow + specialallowance + skillDev + 
						medAllow + washAllow ; 
			
				var providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
				var esi = parseFloat(document.getElementById("esi1").value) || 0;
				var pTax = parseFloat(document.getElementById("pTax").value) || 0;
				var lic = parseFloat(document.getElementById("lic").value) || 0;
				var wFund = parseFloat(document.getElementById("wFund").value) || 0;
				
				var totalDeductss =  providentFund + esi + pTax +  lic + wFund ;
			
			
				var  netpayss=totalEarnings-totalDeductss;
				$("#netPay").val(netpayss);
			
			}
		}
		function checkNumeric(fieldId) {
			
			 var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
			    
			    const input = document.getElementById(fieldId);
			    const position = input.selectionStart;	
			    if(tempVal.slice(-1) == ' '){
			     	$("#" + fieldId).empty();
			     	tempVal = '';
			     } 
			   $("#" + fieldId).val(tempVal);
			} 
	function saveSalData() {
		var data1 = {};
		var datas = [];
		var validation = true;
		 $("#tblShw").each(
				function() { 
				//	var datas = [];
					data1.editId = $("#editId").val();
					data1.empId = document.getElementById("empId").value;
					data1.name = document.getElementById("name").value;
					data1.subdept = $("#subDept").val();//document.getElementById("subDept").value;
					data1.dept = $("#dept").val(); //document.getElementById("dept").value;
					data1.pdesg = document.getElementById("pdesg").value;
					data1.ndesg = document.getElementById("ndesg").value;
					data1.band = $("#band").val(); 
					data1.joiningDate = $("#doj").val(); //document.getElementById("band").value;
			//		data1.empCatSalary = $("#empCatSalary").val();
					
					var fromDate = document.getElementById("effectiveDateFrom").value;
				//	var toDate = document.getElementById("effectiveDateTo").value;
					var validation = true;
					
					if (fromDate === '' || fromDate === null) {
						toastr.error("Add Effective From Date First!!")
					    
					    validation = false;
					} else {
					    data1.effectiveFromDate = fromDate;
					}
					
					/*if (toDate === '' || toDate === null) {
						toastr.success("Add Effective To Date First!!")
					   
					    validation = false;
					} else {
					    data1.effectiveToDate = toDate;
					}*/
					
					/*if (validation && new Date(fromDate) >= new Date(toDate)) {
					    document.getElementById("effectiveDateTo").value = '';
					    toastr.success("Effective To Date Should exceed From Date!!")
					    validation = false;
					}*/
					
					//    data1.ctc = parseFloat(document.getElementById("ctc").value) || 0;
					    
					    data1.basic = parseFloat(document.getElementById("basic").value) || 0;
						data1.providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
						
					//	data1.da = parseFloat(document.getElementById("da").value) || 0;
					//	data1.tds = parseFloat(document.getElementById("tds").value) || 0;
						
						data1.hra = parseFloat(document.getElementById("hra").value) || 0;
						data1.esi = parseFloat(document.getElementById("esi1").value) || 0;
						
						data1.convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
						data1.pTax = parseFloat(document.getElementById("pTax").value) || 0;
						
						data1.specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
					//	data1.salAdv = parseFloat(document.getElementById("salAdv").value) || 0;
						
						data1.skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
						data1.wFund = parseFloat(document.getElementById("wFund").value) || 0;
						
						data1.medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
				//		data1.insAmt = parseFloat(document.getElementById("insAmt").value) || 0;
						
						data1.washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
						data1.lic = parseFloat(document.getElementById("lic").value) || 0;
					
						data1.mEmployerPf = parseFloat(document.getElementById("mEmployerPf").value) || 0;
						data1.esicWage = parseFloat(document.getElementById("esicWage").value) || 0;
						
						data1.totalContribution =  parseFloat(document.getElementById("totalContribution").value) || 0;
						data1.totalEarn = parseFloat(document.getElementById("totalEarn").value) || 0;
						data1.totalDeduct = parseFloat(document.getElementById("totalDeduct").value) || 0;

						data1.netPay = parseFloat(document.getElementById("netPay").value) || 0;
					datas.push(data1);
				});	
		   if (validation) {
				$.ajax({
					type : "POST",
					url : "payroll-report-view-salary-revision-save",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(data1),
					success : function(response) {
						if (response.code == "Success") {
							closeNavSal(); 
							toastr.success("Data Saved successfully")
							
							/*var userid = $("#sessionId").val();
							var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
							var selectedData = selectedNodes?.map(node => node.data);
							var id = selectedData?.map(node => node.employeeId);
							
							agGrid.simpleHttpRequest({
								url : "payroll-report-view-salary-revision-view?userid=" + userid + "&id=" +id,
							}).then(function(data) {
								console.log("data1===",data)
								var len = data.length;
								$('#slrevcnt').find('span').html(len);
								gridOptionsslrev.api.setRowData(data);
							}); */
						}
					},
					error : function(response) {
					}
				}) 
		 }  
	}
	function editSalaryRevision(id,sts=null) {
		if(sts == 2 || sts == 1){
			$('#save1').hide();
		}
		editingSalary(id);
	}
	function editingSalary(id){
		$.ajax({
			type : "GET",
			url : "payroll-report-view-salary-revision-edit?Id=" + id,
			success : function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData=jsonData.editData;
					var len = allData.length;
					console.log("DATA=================",allData);

					$("#salDept").hide();
					//$("#salaryModal").modal('show');
					$("#salaryrevid").show();
					$("#approvesalrvsn").hide();
					$("#myGridslrev").hide();
					$("#slrevcnt").hide();
					$("#slrev").hide();
					
					
 					$("#doj").attr('disabled',true);
					$("#dojDateCalendar").attr('disabled',true);
					$("#dept").attr('disabled',true);
					$("#subDept").attr('disabled',true);
					$("#empId").attr('disabled',true);
					$("#pdesg").attr('disabled',true);
					$("#sdept").removeClass("select");
					$("#ssdept").removeClass("select");
					$("#semp").removeClass("select");
					$("#empId").val(allData[0].empId);
					$("#editId").val(allData[0].editId);
					$("#name").val(allData[0].empName);
					$("#pdesg").val(allData[0].pdesg);
					$("#ndesg").val(allData[0].ndesg);
					$("#effectiveDateFrom").val(allData[0].effectiveDateFrom);
					$("#effectiveDateTo").val(allData[0].effectiveDateTo);
					$("#doj").val(allData[0].joiningDate);
					$("#band").val(allData[0].band);
					$("#dept").val(allData[0].dept);
					$("#subDept").val(allData[0].subdept);
					$("#ctc").val(allData[0].ctc);
					$("#basic").val(allData[0].basic);
					$("#providentFund").val(allData[0].providentFund);
					$("#hra").val(allData[0].hra);
					$("#esi1").val(allData[0].esi);
					$("#convAllow").val(allData[0].convAllow);
					$("#pTax").val(allData[0].pTax);
					$("#specialallowance").val(allData[0].specialallowance);
					$("#salAdv").val(allData[0].salAdv);
					$("#skillDev").val(allData[0].skillDev);
					$("#wFund").val(allData[0].wFund);
					$("#medAllow").val(allData[0].medAllow);
					$("#insAmt").val(allData[0].insAmt);
					$("#washAllow").val(allData[0].washAllow);
					$("#lic").val(allData[0].lic);
					$("#bonus").val(allData[0].bonus);
					$("#socy").val(allData[0].socy);
					$("#overTime").val(allData[0].overTime);
					$("#fine").val(allData[0].fine);
					$("#misc").val(allData[0].misc);
					$("#damage").val(allData[0].damage);
					$("#da").val(allData[0].da);
					$("#tds").val(allData[0].tds);
					$("#otherEarn").val(allData[0].otherEarn);
					$("#otherDeduct").val(allData[0].otherDeduct);
					$("#totalEarn").val(allData[0].totalEarn);
					$("#totalDeduct").val(allData[0].totalDeduct);
					$("#netPay").val(allData[0].netPay);
					$("#mEmployerPf").val(allData[0].mEmployerPf);
					$("#esicWage").val(allData[0].esicWage);
					$("#totalContribution").val(allData[0].totalContribution);
					
				}

			},
			error : function(data) {
				$("#empId").val("");
				$("#name").val("");
				$("#pdesg").val("");
				$("#ndesg").val("");
				$("#effectiveDate").val("");
				$("#band").val("");
				$("#editId").val("");
			}
		});
	}
	
	
	/* function for delete */
	function deleteslrevDetails() {
	
		 var selectedNodes = gridOptionsslrev.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 var id= selectedData.map(node => node.editId);
			if(id){
				$.ajax({
					type : "POST",
					url : "payroll-report-view-salary-revision-delete?id=" + id,
					success : function(response) {
						if (response.message == "Success") { 
							closeNavSal();
							toastr.success("Data Deleted successfully")
							
						}
					},
					error : function(data) {
						console.log(data);
					}
				})
}

	}
	function getPeriod() {
		var empName = $("#fID").val();
		if (empName) {

			$.ajax({
				type : 'GET',
				url : '/master/view-salary-revision-date-ajax?name=' + empName,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {
						for (var i = 0; i < response.body.length; i++) {
							$("#period1").val(response.body[i].key);
							$("#period2").val(response.body[i].name);

						}
					}
				},
				error : function(e) {

				}
			});
		} else {
			$("#period1").val("");
			$("#period2").val("");

		}
	}

	function getName() {

		var NameList = $("#empId").val();
		if (NameList) {
			$.ajax({
				type : 'GET',
				url : '/master/view-salary-revision-nameDesignation-ajax?name='
						+ NameList,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {
						for (var i = 0; i < response.body.length; i++) {
							$("#name").val(response.body[i].key);
							$("#pdesg").val(response.body[i].name);
							$("#band").val(response.body[i].code);

						}
					}
				},
				error : function(e) {

				}
			});
		} else {
			$("#name").val("");
			$("#pdesg").empty();

			var option3 = $("<option></option>");
			$(option3).val(null);
			$(option3).html("Select");
			$("#pdesg").append(option3);

		}

	}

	function closeNavSal() {
		gridOptionsslrev.api.deselectAll();
		var userid = $("#sessionId").val();
		//$("#salaryModal").modal('hide');
		$("#salaryrevid").hide();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		agGrid.simpleHttpRequest({
			url : "payroll-report-view-salary-revision-view?userid=" + userid + "&id=" + id,
		}).then(function(data) {
			console.log("data2===",data)
			var len = data.length;
			$('#slrevcnt').find('span').html(len);
			gridOptionsslrev.api.setRowData(data);
		});
		$('#deleteslrev').show();
		$('#addslrev').show();
		$('#searchRowDiv').show();
		$("#slrev").show();
		$("#approvesalrvsn").hide();
		$("#salaryrevid").hide();
		$("#myGridslrev").show();
		$("#slrevcnt").show();
		
		$('#deleteslrev').attr("disabled", true); 
		$('#addslrev').attr("disabled", true);
		$('#approveslrev').attr("disabled", true);
		//location.reload();
		document.getElementById("finYear").selectedIndex = 0;
	}
	//Open Nav for Exit Management
	function openNavslrev() {
		$(".formValidation").remove();
		$("#editId").val("");
		$('#save1').show();
		$("#salDept").hide();
		$("#myGridslrev").hide();
		$("#slrevcnt").hide();
		$("#slrev").hide();
		$("#approvesalrvsn").hide();
		//$("#salaryModal").modal('show');
		$("#salaryrevid").show();
		var selectedRows = gridOptionsslrev.api.getSelectedRows();
		var id=selectedRows[0].editId;
		$.ajax({
			type : "GET",
			url : "payroll-report-view-salary-revision-edit?Id=" + id,
			success : function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData=jsonData.editData;
					var len = allData.length;
					$("#salDept").hide();
					$("#salaryrevid").show();
					//$("#salaryModal").modal('show');
 					$("#doj").attr('disabled',true);
					$("#dojDateCalendar").attr('disabled',true);
					$("#dept").attr('disabled',true);
					$("#subDept").attr('disabled',true);
					$("#empId").attr('disabled',true);
					$("#pdesg").attr('disabled',true);
					
					
					
					$("#sdept").removeClass("select");
					$("#ssdept").removeClass("select");
					$("#semp").removeClass("select");
					//getEmpList(allData[0].empId)
					
					$("#empId").val(allData[0].empId);
					$("#editId").val("");
					$("#name").val(allData[0].empName);
					$("#pdesg").val(allData[0].pdesg);
					$("#ndesg").val(allData[0].ndesg);
					
				/*	var effectiveDateTo = allData[0].effectiveDateTo;
					var dateParts = effectiveDateTo.split("-");
					var day = parseInt(dateParts[0], 10);
					var month = parseInt(dateParts[1], 10) - 1; // Month is 0-based in JavaScript Date
					var year = parseInt(dateParts[2], 10);
					var dateTo = new Date(year, month, day);
					dateTo.setDate(dateTo.getDate() + 1);
					var newEffectiveDateFrom = ("0" + dateTo.getDate()).slice(-2) + "-" +
					        ("0" + (dateTo.getMonth() + 1)).slice(-2) + "-" + dateTo.getFullYear();
					$("#effectiveDateFrom").val(newEffectiveDateFrom);*/
					
					//$("#effectiveDateFrom").val(allData[0].effectiveDateFrom);
					$("#effectiveDateFrom").val("");
					//$("#effectiveDateTo").val("");
					$("#doj").val(allData[0].joiningDate);
					$("#band").val(allData[0].band);
					//getBandData();
					$("#dept").val(allData[0].dept);
					$("#subDept").val(allData[0].subdept);
					$("#ctc").val(allData[0].ctc);
					$("#basic").val(allData[0].basic);
					$("#providentFund").val(allData[0].providentFund);
					$("#hra").val(allData[0].hra);
					$("#esi1").val(allData[0].esi);
					$("#convAllow").val(allData[0].convAllow);
					$("#pTax").val(allData[0].pTax);
					$("#specialallowance").val(allData[0].specialallowance);
					$("#salAdv").val(allData[0].salAdv);
					$("#skillDev").val(allData[0].skillDev);
					$("#wFund").val(allData[0].wFund);
					$("#medAllow").val(allData[0].medAllow);
					$("#insAmt").val(allData[0].insAmt);
					$("#washAllow").val(allData[0].washAllow);
					$("#lic").val(allData[0].lic);
					$("#bonus").val(allData[0].bonus);
					$("#socy").val(allData[0].socy);
					$("#overTime").val(allData[0].overTime);
					$("#fine").val(allData[0].fine);
					$("#misc").val(allData[0].misc);
					$("#damage").val(allData[0].damage);
					$("#da").val(allData[0].da);
					$("#tds").val(allData[0].tds);
					$("#otherEarn").val(allData[0].otherEarn);
					$("#otherDeduct").val(allData[0].otherDeduct);
					$("#totalEarn").val(allData[0].totalEarn);
					$("#totalDeduct").val(allData[0].totalDeduct);
					$("#netPay").val(allData[0].netPay);
					$("#mEmployerPf").val(allData[0].mEmployerPf);
					$("#esicWage").val(allData[0].esicWage);
					$("#totalContribution").val(allData[0].totalContribution);
				}

			},
			error : function(data) {
				$("#empId").val("");
				$("#name").val("");
				$("#pdesg").val("");
				$("#ndesg").val("");
				$("#effectiveDate").val("");
				$("#band").val("");
				$("#editId").val("");
			}
		});
		
	}
	function getSubDept() {
		var DeptId = $("#dept").val();
		//$('.loader').show();
		if (DeptId) {
			$.ajax({
				type : "GET",
				url : "/master/view-salary-revision-getSubDepartmentByDept?DeptId="
						+ DeptId,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {
						//$('.loader').hide();

						$("#subDept").empty();
						$("#subDept")
								.append("<option value=''>Select</option>");
						$("#empId").empty();
						$("#empId")
								.append("<option value=''>Select</option>");

						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#subDept").append(option);
						}
					}
				},
				error : function(data) {
					$("#subDept").empty();
					$("#subDept").append("<option value=''>Select</option>");

				}
			})
		} else {
			$("#subDept").empty();
			$("#subDept").append("<option value=''>Select</option>");
		}

	}
	function getSubDeptEdit(subDeptId) {
		var  DeptId = $("#dept").val();
		if (DeptId) {
			$.ajax({
				type : "GET",
				url : "/master/view-salary-revision-getSubDepartmentByDept?DeptId="
						+ DeptId,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {

						$("#subDept").empty();
						$("#subDept")
								.append("<option value=''>Select</option>");
						$("#empId").empty();
						$("#empId")
								.append("<option value=''>Select</option>");

						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#subDept").append(option);
						}
						$("#subDept").val(subDeptId);
					}
				},
				error : function(data) {
					$("#subDept").empty();
					$("#subDept").append("<option value=''>Select</option>");

				}
			})
		} else {
			$("#subDept").empty();
			$("#subDept").append("<option value=''>Select</option>");
		}

	}
	function getEmpListBySubDept() {
		var subDeptId = $("#subDept").val();
		$('.loader').show();
		if (subDeptId) {
			$("#empId").val("");
			$.ajax({
				type : "GET",
				url : "/master/view-salary-revision-getEmpListBySubDept?subDeptId="
						+ subDeptId,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {
						$('.loader').hide();

						$("#empId").empty();
						$("#empId")
								.append("<option value=''>Select</option>");

						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].name);
							$(option).html(response.body[i].name);
							$("#empId").append(option);
						}
					}
				},
				error : function(data) {
					console.log(data);
					$("#empId").empty();
					$("#empId").append("<option value=''>Select</option>");

				}
			})
		} else {
			$("#empId").empty();
			$("#empId").append("<option value=''>Select</option>");
		}

	}
	
	 function check(fieldId) {   
			var myField = document.getElementById("ctc")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 } 
	 function check1(fieldId) {   
			var myField = document.getElementById("basic")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 }
	 
	 function check2(fieldId) {   
			var myField = document.getElementById("hra")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 }
	 
	 function check3(fieldId) {   
			var myField = document.getElementById("addAllow")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 }
	 function check4(fieldId) {   
			var myField = document.getElementById("conve")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 }
	 function check5(fieldId) {   
			var myField = document.getElementById("other")
			 var reg = /^\d{0,8}(\.\d{0,2})?$/;   
			 if (reg.test(myField.value))
			 {       
				 $("#" + fieldId).val();
				 reg = '';  
			 }else{        
				 $("#" + fieldId).val(null);    
				 }
			 }
	
	function getEmpList(empid) {
			$.ajax({
				type : "GET",
				url : "/master/view-salary-revision-getEmployeeList",
				contentType : false,
				success : function(response) {
					if (response.message == "success") {

						$("#empId").empty();
						$("#empId")
								.append("<option value=''>Select</option>");

						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].key);
							$("#empId").append(option);
						}
						$("#empId").val(empid);
					}
				},
				error : function(data) {
					$("#empId").empty();
					$("#empId").append("<option value=''>Select</option>");

				}
			})

	}
	
	/* Employee AutoSearch */

	function getEmployeeList() {
		var search = $("#empName").val();
		if (search) {
			$.ajax({
						type : "POST",
						url : "/master/view-salary-revision-get-employee-list",
						dataType : 'json',
						contentType : 'application/json',
						data : search,
						success : function(response) {
							if (response.message == "success") {
								if (response.body.length != 0) {
									$("#empid").val("");
									$("#empName").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
									for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem1(\''
												+ response.body[i].key
												+ '\',\''
												+ window.btoa(response.body[i].name)
												+ '\')">'
												+ response.body[i].key
												+ " - "
												+ response.body[i].name
												+ '</li>';
									}
									content += '</ul>';
									$("#suggesstion-box_").show();
									$("#suggesstion-box_").html(content);

								} else {
									$("#empName").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1">';
									content += '<font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
											+ "No Data Found" + '</li>';
									content += '</ul>';
									$("#suggesstion-box_").show();
									$("#suggesstion-box_").html(content);
								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
		}else{
			$("#empid").val("");
			$("#empName").val("");
			$("#suggesstion-box_").hide();
		}
	}
	function selectAutocompleteValueItem1(empid, empName) {
		if (empid) {
			  $("#error1").hide();
			$("#empName").val(window.atob(empName));
			$("#name").val(window.atob(empName));
			$("#empId").val(empid);
			$("#empName").attr('data-procat', empid);
			$("#suggesstion-box_").hide();
			getName();
			getSubDeptEdit(empid)
		} else {
			$("#empId").val("");
			$("#empName").val("");
			$("#name").val("");
			$("#empName").attr('data-procat', "");
			$("#suggesstion-box_").hide();
			$("#pdesg").val("");

		}
	}
	
	function getSubDeptEdit(empid) {
		if (empid) {
			$.ajax({
				type : "GET",
				url : "/master/view-salary-revision-subanddepartment?empid="
						+ empid,
				contentType : false,
				success : function(response) {
					if (response.message == "success") {
						$("#dept").val(response.body[0].key);
						$("#subDept").val(response.body[0].name);
					}
				},
				error : function(data) {
					

				}
			})
		} else {
			
		}

	}
	
	
	function putData(dataId,amount,calctype,visible){
		if(visible=='E'){
			$(dataId+'div').show();
			}else{
				$(dataId+'div').hide();
				$(dataId).val('0');
			}
		/* if($("#ctc").val()!=null && $("#ctc").val()!=''){ */
		
		if(calctype=='3'){
			var ctc = $("#ctc").val();
			var total=(parseFloat(ctc)/100)*amount;
			$(dataId).val(parseFloat(total).toFixed(2));
		}else if(calctype=='2'){
			
			
			var basic = $("#basic").val();
			var earings = $("#totalEarn").val();
			if($("#basic").val()){
				if(dataId == '#providentFund' || dataId == '#mEmployerPf'){
					if(basic <= 15000){
						var total=(parseFloat(basic)/100)*amount;
						$(dataId).val(parseFloat(total).toFixed(0));
					}else{
						$(dataId).val(parseFloat(1800).toFixed(0));
					}
				}else if(dataId == '#esi1' || dataId == '#esicWage'){
					if(earings <= 21000){
						var total=(parseFloat(earings)/100)*amount;
						$(dataId).val(parseFloat(total).toFixed(0));
					}else{
						$(dataId).val(parseFloat(0).toFixed(0));
					}
				}else if(dataId == '#pTax'){
					var earings1 = $("#totalEarn").val()*12;
					if(earings1 < 160000){
						$(dataId).val(parseFloat(0).toFixed(0));
					}else if(earings1 > 160000 && earings < 300000){
						$(dataId).val(parseFloat(125).toFixed(0));
					}else if(earings1 > 300000){
						$(dataId).val(parseFloat(200).toFixed(0));
					}else{
						$(dataId).val(parseFloat(0).toFixed(0));
					}
				}else if(dataId == '#wFund'){
					var earings = $("#totalEarn").val();
					var total=(parseFloat(earings)/100)*amount;
						$(dataId).val(parseFloat(total).toFixed(0));
				}else{
					var total=(parseFloat(basic)/100)*amount;
					$(dataId).val(parseFloat(total).toFixed(0));
				}
			}else{
				$(dataId).val("");
			}
			
		}else if(calctype=='1'){
			$(dataId).val(parseFloat(amount).toFixed(2));
		}else if(calctype=='4'){
			var total=parseInt($("#ctc").val())-(parseInt($("#basic").val())+parseInt($("#hra").val())+parseInt($("#addAllow").val())+parseInt($("#conve").val())+parseInt($("#medical").val())+parseInt($("#washAllow").val())+parseInt($("#lta").val())+parseInt($("#skillDev").val()));
			if(total>0){
				$(dataId).val(parseFloat(total).toFixed(2));
			}else{
				$(dataId).val('0.0');
			}
		}/* else{
		} */ 
			updateTotalEarning();
			updateTotalDeducts();
			updateTotalContribution();
			updateNetPay();
	}
	function getBandData() {
	 	var band = $("#band").val();
	 	if(band){
	 		agGrid.simpleHttpRequest(
					{
						url : 'payroll-report-view-salary-revision-bandcalc?band='+ band
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData=jsonData.bandData;
						allData.forEach(function(rowNode,index){
							if(rowNode.component=='EEM007'){
								putData('#providentFund',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}else if(rowNode.component=='EEM008'){
								putData('#esi1',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}else if(rowNode.component=='EEM010'){
								putData('#mEmployerPf',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}else if(rowNode.component=='EEM011'){
								putData('#esicWage',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}else if(rowNode.component=='EEM006'){
								putData('#pTax',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}else if(rowNode.component=='EEM040'){
								putData('#wFund',rowNode.amount,rowNode.calctype,rowNode.visibility);
							}
						});
						
						
						
					});
	 	}else{
	 		var validation = validationUpdated("Band is required", "band");
	 	}
		
	}

	function hideAgData(fieldName,visible) {
		  if (visible!='E') {
		    gridOptionsslrev.columnApi.setColumnVisible(fieldName, false);
		  }
		}
	function approveslrev(){
		$("#approvesalrvsn").show();
		$("#myGridslrev").hide();
		$("#slrevcnt").hide();
		$("#slrev").hide();
		$("#salaryrevid").hide();
	}
	function approveClose(){
		$("#approvesalrvsn").hide();
		$("#myGridslrev").show();
		$("#slrevcnt").show();
		$("#slrev").show();
		$("#salaryrevid").hide();
	}
	
	function approveSalary(data){
		var status = 0;
		if(data == 1){
			status = 1;
		}else if(data == 2){
			status = 2;
		}
		var selectedRows = gridOptionsslrev.api.getSelectedRows();	
	//	var id = selectedRows[0].editId;
		var emplist = "";
		selectedRows.forEach(function(rowNode) {
		    emplist += '"' + rowNode.editId + '",';
		});

		emplist = '(' +emplist.substring(0, emplist.length - 1) +')';
		
		console.log(emplist);
		var id1 = selectedRows[0].empId;
		Swal.fire({
				title: 'Are you sure?',
				text: 'Do you want to approve this salary?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes, approve it!',
				cancelButtonText: 'No, keep it',
				confirmButtonColor: 'var(--mainColor)',
			}).then((result) => {
		if (result?.value) {
		$.ajax({
			type : "GET",
			url : "payroll-report-view-salary-revision-approve?id="+ emplist+"&sts="+ status,
			success : function(response) {
			 if (response.code == "success") {
				 toastr.success(response.message)
				 
					$('#addslrev').attr("disabled", true);
					$('#deleteslrev').attr("disabled", true);
					$('#approveslrev').attr("disabled", true);
					
					var userid = $("#sessionId").val();
					var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
					var selectedData = selectedNodes?.map(node => node.data);
					var id = selectedData?.map(node => node.employeeId);
					agGrid.simpleHttpRequest({
						url : "payroll-report-view-salary-revision-view?userid=" + userid + "&id=" + id,
					}).then(function(data) {
						console.log("data3===",data)
						var len = data.length;
						$('#slrevcnt').find('span').html(len);
						gridOptionsslrev.api.setRowData(data);
						if (data.length > 0) {
							$("#saladd").hide();
							} else {
								$("#saladd").show();
							}
					}); 
				} else {
					toastr.error("Something went to wrong!")
					
				}
			},
		error : function(data) {
		}
	})
		}
	});
}
	function getListForEmp(){
		gridOptionsslrev.api.setQuickFilter(document.getElementById('empSort').value);
	    var totalRowCount = gridOptionsslrev.api.getModel().getRowCount();
		$('#slrevcnt').find('span').html(totalRowCount);
	}
	var formattedStartDate, endDate;

	function getFinancialYearList() {
		
	    // Extract the financial year string from the input element
	    var finYear = document.getElementById('finYear').value;
	    // Split the financial year string to get the start and end years
	   if(finYear){
		   var years = finYear.split('-');
		    var startYear = parseInt(years[0], 10);
		    var endYear = parseInt(years[1], 10);
		    var startDate = new Date(startYear, 3, 1); 
			var endDate = new Date(endYear, 2, 31);
		    formattedStartDate = formatDate(startDate);
		    formattedEndDate = formatDate(endDate);
		    if( formattedStartDate && formattedEndDate ){
		    	filterByFinancialYear();
		    }
	   }else{
		   var userid = $("#sessionId").val();
			var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
			
		   $('.loader').show();
			agGrid.simpleHttpRequest({
				url : "payroll-report-view-salary-revision-view?userid=" + userid + "&id=" + id,
			}).then(function(data) {
				console.log("data4===",data)
				$('.loader').hide();
				var len = data.length;
				$('#slrevcnt').find('span').html(len);
				gridOptionsslrev.api.setRowData(data);
			});
	   }
	}
	function formatDate(date) {
		
	    var day = date.getDate();

	    var month = date.getMonth() + 1;
	    var year = date.getFullYear();
	    // Pad single digit day and month with a leading zero
	    var formattedDay = (day < 10 ? '0' : '') + day;
	    var formattedMonth = (month < 10 ? '0' : '') + month;
	    // Format the date as yyyy-mm-dd
	    return year + '-' + formattedMonth + '-' + formattedDay;
	}
	function filterByFinancialYear(){
		var userid = $("#sessionId").val();
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		$('.loader').show();
		agGrid.simpleHttpRequest({
			url : "payroll-report-view-salary-revision-view-by-year?userid=" + userid + "&startDate=" 
			+ formattedStartDate + "&endDate=" + formattedEndDate + "&id=" + id,
		}).then(function(data) {
			$('.loader').hide();
			var len = data.length;
			$('#slrevcnt').find('span').html(len);
			gridOptionsslrev.api.setRowData(data);
		});
	}
	function downloadSalaryRevCSV() {
		var dataset = [];
		gridOptionsslrev.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		    dataset.push(rowNode.data);
		});
		var name = "Salary Details-" + $("#empSort option:selected").text();
		   var allColumns = gridOptionsslrev.columnApi.getAllColumns();
		   var visibleColumns = allColumns.filter(col => !col.getColDef().hide);
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
                if (params.column.getColId() === "status") {
                   if (params.value == 1) {
                        return "Approved";
                    }else {
                        return "Pending";
                    }
                }
                return params.value;
            }
        };
		  
		gridOptionsslrev.api.exportDataAsCsv(params);
}
	
	function validateDateRange() {
	    var fromDateStr = $('#effectiveDateFrom').val();
	    var toDateStr = $('#effectiveDateTo').val();

	    if (!fromDateStr || !toDateStr) {
	        return; // Don't proceed if either date is empty
	    }
	    var fromDateParts = fromDateStr.split('-');
	    var toDateParts = toDateStr.split('-');
	    
	    var fromDate = new Date(fromDateParts[2], fromDateParts[1] - 1, fromDateParts[0]);
	    var toDate = new Date(toDateParts[2], toDateParts[1] - 1, toDateParts[0]);

	    if (isNaN(fromDate.getTime()) || isNaN(toDate.getTime())) {
	    }
	    if (toDate <= fromDate) {
	    	toastr.error("Effective To Date should exceed Effective From Date!")
	    	
	        $('#effectiveDateTo').val(''); // Clear the effectiveToDate if validation fails
	    }
	}