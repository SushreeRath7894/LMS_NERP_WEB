	var pno;
 	function getAllJobView(){
 		var pages;
 	 	var pageno=pno;
	    agGrid.simpleHttpRequest({
	        url: "view-job-manage-all-data?pageno="+pageno+"&type="+'all',
	    }).then(function(data) {
	        var jsonData = JSON.parse(data.body[0]);
console.log("view-job-manage-all-data jsonData===",jsonData);
	        if (jsonData.viewTicket === null) {
	            var data = []; // Initialize data as empty array
	            gridOptions.api.setRowData(data);
	            $('#totalReq').find('span').html(0); // Set total count to 0
	            $("#pagination").hide();
	            $('.loader').hide();
	        } else {
	            var allData = jsonData.viewTicket;
	            if (allData != null && allData.length > 0) {
	                var len = allData.length;
	                $('#totalReq').find('span').html(len);
	                $('#totalPageno').val(allData[0].totalPageno);
	 				pages=allData[0].totalPageno;
	            }
				console.log("allData viewww----",allData)
	            gridOptions.api.setRowData(allData);
	            if (selectedttkid) {
				    gridOptions.api.forEachNode(function(node) {
				        if (node.data.id === selectedttkid) {  // Match the unique identifier
				            node.setSelected(true);
				        }
				    });
				}else{
				var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
				    firstRowNode.setSelected(true);
		        }					
				}
	            $('.loader').hide();
	        }
	   //    createPagination(pages, pageno);
	    });
	}
	
function viewTicketDetails(id){
	var selectedRow="";
	var assetId='';
	gridOptions.api.forEachNode(function(rowNode, index) {
			if (rowNode.data.tcktNo ==id) {
				selectedRow = rowNode.data;
			}
		 });
		if(selectedRow){
				console.log("selectedRow",selectedRow);
				if(selectedRow.assetid == '' || selectedRow.assetid == null || selectedRow.assetid == ' '){
					assetId='N/A';
				}else{
					assetId= selectedRow.assetid;
				}
				$("#tckttype").html(selectedRow.tckttype);
				$("#ticketPriority").html(selectedRow.ticketPriority);
				$("#tcktCat").html(selectedRow.tcktCat);
				$("#tcktSubCat").html(selectedRow.tcktSubCat);
				$("#assigndate").html(selectedRow.assigndate);
				$("#assigntime").html(selectedRow.assigntime);
				$("#assetid").html(assetId);
				$("#assetLocation").html(selectedRow.assetLocation );
				$("#isClosed").html(selectedRow.isClosed);
				$("#status").html(selectedRow.status);
				$("#assignmentType").html(selectedRow.assignmentType);
				$("#type").html(selectedRow.type);
			}else{
				$("#tckttype").html("");
				$("#ticketPriority").html("");
				$("#tcktCat").html("");
				$("#tcktSubCat").html("");
				$("#assigndate").html("");
				$("#assigntime").html("");
				$("#assetid").html("" );
				$("#assetLocation").html("");
				$("#isClosed").html("");
				$("#status").html("");
				$("#assignmentType").html("");
				$("#type").html("");
			}
		
}
	function showForm(ticketType) {
        if (ticketType === 'preventive') {
            document.getElementById('preventiveForm').style.display = 'block';
            document.getElementById('correctiveForm').style.display = 'none';
        } else if (ticketType === 'corrective') {
            document.getElementById('preventiveForm').style.display = 'none';
            document.getElementById('correctiveForm').style.display = 'block';
        }
    }
    // Example functions (to be implemented according to your needs)
    function removeValid(event) {
        // Example validation removal logic
    }
    function downloadResultDetails() {
        // Example function for download
    }
    function filterData() {
        // Example function to filter data
    }
    function checkReview(event) {
        // Example review status checking logic
    }
    function checkEmpty() {
        // Example function for form validation or submit checks
    }
function viewLogsForTicket(tckid){
		var div = '';
			 agGrid.simpleHttpRequest(
					{
						url : 'view-department-view-tk-ticket-history?id='+ tckid
					}).then(function(data) {
					 	var jsonData = JSON.parse(data.body);
							if(jsonData!=null){
								activityForViewLogDet.api.setRowData(jsonData);
								setHistoryView(jsonData);
							}else{
								activityForViewLogDet.api.setRowData("");
								$("#historyDiv").html("");
							}
							});
		
}
function setHistoryView(data){
		console.log(data);
		$("#historyDiv").html("");
	//	loader.classList.add("d-none");
		var	newchat='';
		data.forEach(function(rowNode,index){
			newchat +=`<div class="timeline-item">
		        <div class="timeline-circle">${index + 1}</div>
				 <div class="timeline-content">
		        <h2 class="tkt-title">Ticket ${rowNode.assign_type}, ${rowNode.created_on} </h2>
		          <div class="time-box d-flex justify-content-between align-items-center row">`;
				 
			 if(rowNode.assign_type == 'RAISED' && rowNode.open_status == 'NOT ASSIGNED'){
	        	  newchat +=`
	        		  <div class="timeln-txt">Raised By : ${rowNode.raised_by}</div>
						 <div class="timeln-txt">Details : ${rowNode.desc}</div>
	        	  `;
	          }else if(rowNode.assign_type == 'ASSIGN' && rowNode.open_status == "OPEN"){
		        	  newchat +=`
		        		  <div class="timeln-txt">Assigned By : ${rowNode.assign_to}</div>
							 <div class="timeln-txt">Assigned Status : ${rowNode.curr_sts}</div>
		        	  `;
		       }else if(rowNode.assign_type == 'CLOSED'){
		        	  newchat +=`
		        		  <div class="timeln-txt">Closed By : ${rowNode.assign_to}</div>
							 <div class="timeln-txt">Closed Status : ${rowNode.assign_status}</div>
		        	  `;
		       }
		          
		  // add more         
			newchat +=`</div>
				   <div class="time-box d-flex justify-content-between align-items-center time-lmr">
				      <div class="timeln-txt"> Ticket Status : ${rowNode.open_status}</div>  
				  </div>
		        </div>
		      </div>`;
			});

		$("#historyDiv").html(newchat);
	}
function sendFeedback(){
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var id= selectedData.map(node => node.tcktNo);
		var obj = {};
		var valid = true;
		var data = [];
		
		obj.ticketId =id[0];
		obj.resultDescription = $("#ticketFeedback").val();
		obj.ticketType = "Admin";

		if (obj.feedback == null || obj.feedback == "") {
			valid = validationUpdated("", "ticketFeedback");
		}
		data.push(obj);
		if(valid){
			loader.classList.remove("d-none");
			$("#feedbackSendBtn").hide();
		$('.loader').show();
		setTimeout(function() {
		$.ajax({
			type : "POST",
			url : "view-department-view-tk-feedback-save",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					var newchat="";
					var jsonData = JSON.parse(response.body);
					if(jsonData){
						var allData=jsonData.Chat;
					allData.forEach(function(rowNode,index){
						if(rowNode.type=="Admin"){
							newchat +=`<div class="d-flex flex-row justify-content-end">
			                    <div>
			                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
			                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
			                    </div>
			                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
			                      alt="avatar 1" style="width: 45px; height: 100%;">
			                  </div>`;
						}else{
							newchat +=`<div class="d-flex flex-row justify-content-start">
		                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
		                      alt="avatar 1" style="width: 45px; height: 100%;">
		                    <div>
		                      <p class="small p-2 ms-3 mb-1 rounded-3 font-weight-bold params-desc" style="background-color: #f5f6f7;">${rowNode.desc}</p>
		                      <p class="small ms-3 mb-3 rounded-3 text-muted float-end">${rowNode.date}</p>
		                    </div>
		                  </div>`;
						}
						});
					$("#ticketFeedback").val("");
					$("#chatDiv").html(newchat);
					loader.classList.add("d-none");
					$("#feedbackSendBtn").show();
					}else{
						$('.loader').hide();
					var newchat=`<div class="d-flex flex-row justify-content-end">
	                    <div>
	                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own">${obj.resultDescription}</p>
	                      <p class="small me-3 mb-3 rounded-3 text-danger">Failed to sent</p>
	                    </div>
	                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
	                      alt="avatar 1" style="width: 45px; height: 100%;">
	                  </div>`;
	                  $("#ticketFeedback").val("");
					$("#chatDiv").append(newchat);
					loader.classList.add("d-none");
					$("#feedbackSendBtn").show();
					}
				} else {
					$('.loader').hide();
					var newchat=`<div class="d-flex flex-row justify-content-end">
	                    <div>
	                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own">${obj.resultDescription}</p>
	                      <p class="small me-3 mb-3 rounded-3 text-danger">Failed to sent</p>
	                    </div>
	                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
	                      alt="avatar 1" style="width: 45px; height: 100%;">
	                  </div>`;
	                  $("#ticketFeedback").val("");
					$("#chatDiv").append(newchat);
					loader.classList.add("d-none");
					$("#feedbackSendBtn").show();
				}
			},
			error : function(response) {
				loader.classList.add("d-none");
				$("#feedbackSendBtn").show();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Something Went Wrong");
				$("#msgOkModal").removeClass("btn1");
				$("#msgOkModal").addClass("btn3");
				$("#msgModal").modal('show');
			}
		})
		}, 1000);
		}
	}
function feedbackFun(){
		loader.classList.remove("d-none");
		$("#feedbackSendBtn").hide();
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var id= selectedData.map(node => node.tcktNo);
		var newchat="";
		var len=id.length;
		if(len >0){
			agGrid.simpleHttpRequest(
					{
						url : 'view-department-view-tk-chat-view?id=' + id[0]+"&type="+ "Admin"
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData=jsonData.Chat;
						if(allData!=null){
						allData.forEach(function(rowNode,index){
							if(rowNode.type=="Admin"){
								newchat +=`<div class="d-flex flex-row justify-content-end">
				                    <div>
				                      <p class="small p-2 me-3 mb-1 text-white rounded-3 bg-own font-weight-bold params-desc">${rowNode.desc}</p>
				                      <p class="small me-3 mb-3 rounded-3 text-success">${rowNode.date}</p>
				                    </div>
				                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
				                      alt="avatar 1" style="width: 45px; height: 100%;">
				                  </div>`;
							}else{
								newchat +=`<div class="d-flex flex-row justify-content-start">
			                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
			                      alt="avatar 1" style="width: 45px; height: 100%;">
			                    <div>
			                      <p class="small p-2 ms-3 mb-1 rounded-3 font-weight-bold params-desc" style="background-color: #f5f6f7;">${rowNode.desc}</p>
			                      <p class="small ms-3 mb-3 rounded-3 text-muted float-end">${rowNode.date}</p>
			                    </div>
			                  </div>`;
							}
							});
						} else {
						    // If allData is null or undefined
						    newchat = `<p class="text-center text-muted">No chat history available.</p>`;
						}
						$("#chatDiv").html(newchat);
						loader.classList.add("d-none");
						$("#feedbackSendBtn").show();
					});
		}else{
		}
	}
function infoAssign(){
		var obj = {};
		var valid = true;
		
		if(valid){
			  
			obj.assignType = '3';
			obj.ticketId=$("#tktNo1").html();
			obj.date=$("#dateAction").val();
			obj.time=$("#timeAction").val();
			obj.dept=$("#departmentAction").val();
			obj.ticketPriority=$("#ticketPriorityAction").val();
			
			if(type=="Employee"){
		    	obj.empId=$("#employeeAction").val();
		    	obj.dept=$("#departmentAction").val();
		    	obj.type="Employee";
		    	
				if (obj.empId == null || obj.empId == "") {
					valid = validationUpdated("Employee is Required", "employeeAction");
				 }
				if (obj.dept == null || obj.dept == "") {
					valid = validationUpdated("Department is Required", "departmentAction");
				 }
		    }else if(type=="Vendor"){
		    	obj.empId=$("#vendorAction1").val();
		    	obj.dept="";
		    	obj.type="Vendor";
		    	if (obj.empId == null || obj.empId == "") {
					valid = validationUpdated("Vendor is Required", "vendorAction1");
				 }
		    }					
		}
		if (obj.date == null || obj.date == "") {
			valid = validationUpdated("Date is Required", "dateAction");
		 }
		if (obj.time == null || obj.time == "") {
			valid = validationUpdated("Time is Required", "timeAction");
		 }

		if (obj.ticketPriority == null || obj.ticketPriority == "") {
			valid = validationUpdated("Priority is Required", "ticketPriorityAction");
		 }

		if(valid){
			saveTicketAction(obj,obj.ticketId);
		}
	}
	
	
	
	function saveTicketAction(data,id) {
		$('.loader').show();
		$.ajax({
			type : "POST",
			url : "view-department-view-tk-save-action-details",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#actionModal").modal('hide');
					ShowAgGrid('All');
					onSelectionChange();
					viewAssignedResult(id);
				} else {
					$('.loader').hide();
					$("body").removeClass("overlay");
					$("#messageParagraph").text("Something Went Wrong");
					$("#msgOkModal").removeClass("btn1");
					$("#msgOkModal").addClass("btn3");
					$("#msgModal").modal('show');
					$("#actionModal").modal('hide');

				}
			},
			error : function(response) {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Something Went Wrong");
				$("#msgOkModal").removeClass("btn1");
				$("#msgOkModal").addClass("btn3");
				$("#msgModal").modal('show');
				$("#actionModal").modal('hide');
			}
		})
	}
var type="Employee";
	function openAssignEmployee(e,val){
		if(val === 'Employee'){			
			$("#vendorid").hide();
			$("#departmentid").show();
			$("#employeeid").show();
			e.preventDefault();
			type="Employee";			
			}else if(val === 'Vendor'){			
			$("#departmentid").hide();
			$("#employeeid").hide();
			$("#vendorid").show();
			 type="Vendor";
			 e.preventDefault();
		}		

		
	}	
	function viewAssignedResult(id=null){	
	//	alert(id)
	//let output = id.split(',').map(item => `" ${item}"`).join(',');
		var div = '';
		if(id){
			 agGrid.simpleHttpRequest(
						{
							url : 'view-department-view-tk-assigned-result?id='+ id
						}).then(function(data) {
							
							var jsonData = JSON.parse(data.body);
							console.log("jsonData ticket assignmentt========== ",jsonData);
							console.log("jsonData length==",jsonData.length)
							if(jsonData==null){
								$("#dateAction").val("");
								$("#timeAction").val("");
								$("#ticketPriorityAction").val("");
								$("#vendorAction1").val("");
						    	$("#employeeAction").val("");
						    	$("#departmentAction").val("");
						    	infoAssignEdit();
								openAssignEmployee(event,'Employee');
						    	
							}else{
								for(i=0;i<jsonData.length;i++){
									var allData=jsonData[i];
									if((allData.assign_type == 'ASSIGN' && allData.open_status == 'ASSIGNED') || 
											(allData.assign_type == 'MAINTAIN' && allData.open_status == 'REOPEN')){
										console.log("Assign data===",allData)
										if(allData.dept_id){
											$("#assignEmp").addClass("active");
											$("#assignVen").removeClass("active");
											openAssignEmployee(event,'Employee');
								    	$("#departmentAction").val(allData.dept_id);
								    	getEmpByDeptAction(allData.dept_id,allData.assignedId);
										}else{
											$("#assignEmp").removeClass("active");
											$("#assignVen").addClass("active");
											openAssignEmployee(event,'Vendor');
										$("#vendorAction1").val(allData.assignedId);
										}
									//	$("#tktNo1").html(allData.ticket_id);
										$("#dateAction").val(allData.date);
										$("#timeAction").val(allData.time);
								    //	$("#employeeAction").val(allData.assignedId);
										$("#ticketPriorityAction").val(allData.priority);
										infoAssignCancel();
										$('#infoAssign').hide();
									}
								}
								
							}
							
					});
		}else{
			$("#dateAction").val("");
			$("#timeAction").val("");
			$("#ticketPriorityAction").val("");
			$("#vendorAction1").val("");
	    	$("#employeeAction").val("");
	    	$("#departmentAction").val("");
	    	infoAssignEdit();
		}
	}
	function infoAssignEdit(){
		$('#dateAction, #timeAction, #departmentAction, #employeeAction, #ticketPriorityAction, #vendorAction1').attr('disabled', false);
		$('#DateCalendarAction').show();
		$('#toDateCalendarTimeAction').show();
		$('#infoAssignCancel').show();
		$('#infoAssign').show();
		$('#infoAssignEdit').hide();
	}
	function infoAssignCancel(){
		$('#dateAction, #timeAction, #departmentAction, #employeeAction, #ticketPriorityAction, #vendorAction1').attr('disabled', true);
		$('#infoAssign').hide();
		$('#infoAssignCancel').hide();
		$('#infoAssignEdit').show();
		$('#DateCalendarAction').hide();
		$('#toDateCalendarTimeAction').hide();
	}
	
function getEmpByDeptAction(selectedValue,value=null) {
 
    $.ajax({
		type : "GET",
		url : "view-department-view-tk-getemployee?deptid=" + selectedValue,
		success : function(response) {
			if (response.code == "success") {
			 	$("#employeeAction").empty();
				var option = $("<option></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#employeeAction").append(option);
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$(option).attr("data-code",response.body[i].code);
					$("#employeeAction").append(option);
				}
				if(value){
					$("#employeeAction").val(value);
				}
			} 
		},
		error : function(e) {
		}
	});
}