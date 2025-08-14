$(document).ready(function() {
	
	var dateFormat = localStorage.getItem("dateFormat");
		$("#effectiveDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
		datepicker : true,
		scrollMonth : false,
	//minDate : new Date()
	}).on("change", function() {
		$('#effectiveDate').val($(this).val());
	})
	$('#effectiveDate').blur(
			function() {
				$("#effectiveDateCalendar").val(
						$(this).val());
			});
		
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		var rowData = [];
		gridOptions.api.setRowData(rowData);
		agGrid.simpleHttpRequest({
			url : "view-other-benifits-view-data"
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			console.log("json",jsonData);
			var allData=jsonData.BenifitData;
			console.log("allData",allData);
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
			
		}); 
		
	
		
		
	
		$('#reqDltBtn').attr('disabled', true);
		$('#assignBtn').attr('disabled', true);
		$('#deleteChild').attr('disabled', true);
		$('.collapse').on('show.bs.collapse', function() {
			$(this).siblings('.panel-heading').addClass('active');
		});

		$('.collapse').on('hide.bs.collapse', function() {
			$(this).siblings('.panel-heading').removeClass('active');
		});
		$("#newBtn").click(function() {

		})
		


var dateFormat = localStorage.getItem("dateFormat");
		
		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar3").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
			datepicker : true,
			scrollMonth : false,
		}).on("change", function() {
			$('#pdate').val($(this).val());
		})

		$('#pdate').blur(function() {
			$("#DateCalendar3").val($(this).val());
		})


	});
	
	
	
	/* -------------------search bar for mygrid------------------------ */


	var columnDefs = [
		{
			headerCheckboxSelection : false,
			headerCheckboxSelectionFilteredOnly : false,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true

		},
		{
			headerName : 'Benifit Id',
			field : "benifitId",
				cellRenderer : function(params) {
					if (params.data.approvestatus=="Pending") {
							return '<a id="benifitId" onclick=editBenifit("'
							+ params.data.benifitId+','
							+params.data.approvestatus
							+ '") href="javascript:void(0)">'
							+ params.data.benifitId + '</a>';
						}else{
							return '<div>'+params.data.benifitId+'</div>';
						}
					
					
					
					
					
					
			/*	return '<a id="benifitId" onclick=editRegister("'
				+params.data.benifitId+'","'+params.data.approvestatus
				+ '") href="javascript:void(0)">'
				+ params.data.benifitId + '</a>';

			
			*/
			}
			}, {
			headerName : 'Employee Id',
			field : "empid",
			cellStyle : {
				textAlign : 'center'
			}

           }, {
			headerName : 'Employee Name',
			field : "empName",
			cellStyle : {
				textAlign : 'center'
			},
			width : 190
	
	    }, {
			headerName : 'Category',
			field : "categoryName",
			cellStyle : {
				textAlign : 'center'
			}
			 }, {
			headerName : 'Frequency',
			field : "fname",
			cellStyle : {
				textAlign : 'center'
			}
			 }, {
			headerName : 'Benifit Amount',
			field : "benifit",
			cellStyle : {
				textAlign : 'center'
			}
		
	
		}, {
			headerName : 'Created By',
			field : "createdBy",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : 'Created On',
			field : "createdOn",
			cellStyle : {
				textAlign : 'center'
			}
			}, {
		
			headerName : 'Approve Status',
			field : "approvestatus",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if (params.data.approvestatus =="Approved") { 
					return '<div style="color:#0642f5">'+params.data.approvestatus+'</div>';
				}else {
					return '<div style="color:#a9a9a9">'+params.data.approvestatus+'</div>';
				}
				

			}
		}];

// let the grid know which columns and what data to use
var gridOptions = {
	columnDefs : columnDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	onSelectionChanged : onSelectionChangedB,
};

var benifitId = "";
var approvestatus="";
function onSelectionChangedB(){
		var selectedNodes = gridOptions.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 blockid= selectedData.map(node => node.benifitId);
		 var rowCount = 0;
		 selectedNodes.forEach(function(selectedNodes, index) {
				rowCount = rowCount + 1;
			});
		 
		 if (rowCount > 0) {
					$('#reqDltBtn').attr("disabled", false);
			$('#assignBtn').attr("disabled", false);
			$('#newBtn').attr('disabled', true);
			 	
				 if (selectedData.map(node => node.approvestatus)!="Approved"){
					 $('#assignBtn').attr("disabled", false);
					 $('#reqDltBtn').attr("disabled", false);
				     $('#newBtn').attr('disabled', true);
					 
				 }else{
					 $('#assignBtn').attr("disabled", true);
					 $('#reqDltBtn').attr("disabled", true);
				$('#newBtn').attr('disabled', false);
				 } 
					
			} else {
				$('#newBtn').attr("disabled", false);
				$('#assignBtn').attr("disabled", true);
				$('#reqDltBtn').attr("disabled", true);
				
			}
		}
/*function onSelectionChangedB() {
	
	var selectedNodes = gridOptions.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);
	 id= selectedData.map(node => node.benifitId);
	 approvestatus=selectedData.map(node => node.approvestatus);
	
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		if(approvestatus=='Approved'){
			$('#reqDltBtn').attr("disabled", true);
			$('#assignBtn').attr("disabled", true);
			$('#newBtn').attr('disabled', false);
		}else{
		$('#newBtn').attr('disabled', true);
		$('#reqDltBtn').attr("disabled", false);
		$('#assignBtn').attr("disabled", false);
		}
	}else {
		$('#reqDltBtn').attr("disabled", true);
		$('#assignBtn').attr("disabled", true);
		$('#newBtn').attr('disabled', false);
	}
}*/
	function openNav() {
		document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto;";
		document.getElementById("main").style.width = "75%";
		$("#cancel").show();
		$("#save").attr("disabled", false);
		$("#save").show();
		$("#add").attr("disabled", true);
		$("#delete").attr("disabled", true);
		$("#empid").val("");
		$("#empName").val("");
		$("#categoryId").val("");
		$("#benifit").val("");
		$("#effectiveDate").val("");
		$("#remark").val("");
	}
		function closeNav() {
	
		$("#empid").val("");
		$("#empidob").val("");
		$("#freqencyob").val("");
		$("#empName").val("");
		$("#categoryId").val("");
		$("#benifit").val("");
		$("#effectiveDate").val("");
		$("#remark").val("");
		$("#newBtn").attr("disabled", false);
		$("#delete").attr("disabled", true);
		$("#newBtn").show();
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("main").style.width = "100%";
	}
	
		/* Employee AutoSearch */

	function getEmployeeList() {
		var search = $("#empName").val();
		if (search) {
			$.ajax({
						type : "POST",
						url : "view-other-benifits-get-employee-list",
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
			$("#empid").val(empid);
			$("#empidob").val(empid);
			$("#empName").attr('data-procat', empid);
			$("#suggesstion-box_").hide();
		} else {
			$("#empid").val("");
			$("#empidob").val("");
			$("#empName").val("");
			$("#empName").attr('data-procat', "");
			$("#suggesstion-box_").hide();

		}
	}
	function setFrequency(val){
		agGrid.simpleHttpRequest(
					{
						url : 'view-other-benifits-get-frequency?category='+ val
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData=jsonData.Frequency;
						if(allData!=null){
							$("#freqencyob").val(allData[0].fname);
						}else{
							$("#freqencyob").val("");
						}
					});
	}
	function downloadDetails() { 
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
    gridOptions.api.exportDataAsCsv(dataset);
}

		function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";

		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}
	
	
	function addDetails() {
		obj = {};
		obj.benifitId = $("#benifitId").val();
		obj.empid = $("#empid").val();
		obj.categoryId = $("#categoryId").val();
		obj.benifit = $("#benifit").val();
		obj.effectiveDate = $("#effectiveDate").val();
		obj.remark = $("#remark").val();
		obj.freqencyob = $("#freqencyob").val();
  
		var validation = true;

		if (obj.categoryId == null || obj.categoryId == "") {
			validation = validationUpdated("Category Type Required", "categoryId");
		}
		
		if (validation) {
			console.log(obj)
			$('.loader').show();
			$.ajax({
				type : "POST",
				url : "view-other-benifits-details-save",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					if (response.code == "Success") {

						$("#messageParagraph").text(
						"Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						$("#add").show();
						$("#myGrid").show();
						$("#cancel").hide();
						$("#save").hide();
						$("#form").hide();
						$("#new").hide();
						closeNav();
							agGrid.simpleHttpRequest({
			url : "view-other-benifits-view-data"
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			console.log("json",jsonData);
			var allData=jsonData.BenifitData;
			console.log("allData",allData);
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
			
		}); 
						$('.loader').hide();
					}else{
						$("#messageParagraph").text(
						"Something went to wrong");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						$('.loader').hide();
					}
				},
				error : function(data) {
				}
			})
		}
	}
	

	
	function deleteFun() {
			var selectedRows = gridOptions.api.getSelectedRows();
					 var id=selectedRows[0].benifitId;
			
					$.ajax({
						type : "GET",
						url : "view-other-benifits-delete?id=" + id,
						async : false,
						success : function(response) {

							if (response.code == "success") {
									
								$("#messageParagraph").text("Deleted Successfully");
								$("#msgOkModal").removeClass("btn3");
								$("#msgOkModal").addClass("btn1");
								$("#msgModal").modal('show');
								 $('#assignBtn').attr("disabled", true);
					          $('#reqDltBtn').attr("disabled", true);
				             $('#newBtn').attr('disabled', false);
												closeNav();
							agGrid.simpleHttpRequest({
			url : "view-other-benifits-view-data"
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			console.log("json",jsonData);
			var allData=jsonData.BenifitData;
			console.log("allData",allData);
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
			
		}); 
								
							}

						},
					});
	

	}
	
	function approveFun(){
		 var selectedRows = gridOptions.api.getSelectedRows();
			 var id=selectedRows[0].benifitId;
			
			$.ajax({
				type : "GET",
				url : "view-other-benifits-approve?id=" + id,
				async : false,
				success : function(response) {

					if (response.code == "success") {
						
						$("#messageParagraph").text("Approved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						 $('#assignBtn').attr("disabled", true);
					     $('#reqDltBtn').attr("disabled", true);
				         $('#newBtn').attr('disabled', false);
															closeNav();
							agGrid.simpleHttpRequest({
			url : "view-other-benifits-view-data"
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			console.log("json",jsonData);
			var allData=jsonData.BenifitData;
			console.log("allData",allData);
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
			
		}); 
					}

				},
			});
		

	}
	
	function editBenifit(id){
		var data = id.split(",");
			var benifitId= data[0];
			var approvestatus = data[1];
			openNav();
		agGrid.simpleHttpRequest(
					{
						url : 'view-other-benifits-edit-benifits?benifitId='+ benifitId,
					}).then(function(data) {
						console.log("allDATA",data);
						var jsonData = JSON.parse(data.body);
						var allData=jsonData.edit;
						setFrequency(allData[0].categoryId);
						$("#benifitId").val(allData[0].benifitId);
						$("#empid").val(allData[0].empid);
						$("#empidob").val(allData[0].empid);
						$("#empName").val(allData[0].empName);
						$("#categoryId").val(allData[0].categoryId);
						$("#remark").val(allData[0].remark);
						$("#benifit").val(allData[0].benifit);
						$("#effectiveDate").val(allData[0].effectiveDate);
						
						
					});
	}