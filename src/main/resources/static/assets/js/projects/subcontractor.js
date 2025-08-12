	//setup the grid after the page has finished loading
	$(document).ready(function() {
		
		
		
		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#dateofnoticetoproceedIssued').val($(this).val());
		})

		$('#dateofnoticetoproceedIssued').blur(function() {
			$("#DateCalendar").val($(this).val());
		})
		
		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar1").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#datecontractExecuted').val($(this).val());
		})

		$('#datecontractExecuted').blur(function() {
			$("#DateCalendar1").val($(this).val());
		})
		
		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar2").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#dateinsuranceRecieved').val($(this).val());
		})

		$('#dateinsuranceRecieved').blur(function() {
			$("#DateCalendar2").val($(this).val());
		})

		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptions2);

		var gridDiv3 = document.querySelector('#myGrid3');
		new agGrid.Grid(gridDiv3, gridOptions3);
		
		agGrid.simpleHttpRequest({
			url : "project-sub-contractor-view-work"
		}).then(function(data) {
			var len = data.length;
			$('#totalWorks').find('span').html(len);
			gridOptions3.api.setRowData(data);
		});
		
		
		$('#deleteId').attr("disabled", true);
		$('#deleteId1').attr("disabled", true);

		agGrid.simpleHttpRequest({
			url : 'project-sub-contractor-view'
		}).then(function(data) {
			var jsonData = JSON.parse(data.body[0]);
			var allData = jsonData.SubContractor;
			console.log(allData)
			var len = allData.length;
			$('#totalConts').find('span').html(len);
			gridOptions2.api.setRowData(allData);
		});
	});
	
	function editId(data) {
		$("#demo").hide();
		$("#myGrid2").hide();
		$("#delete").hide();
		$("#yeshh1").hide();
		$("#rfqid").hide();
		$("#myGrid3").show();
		$("#yeshh").show();
		$("#child").show();
	}

	function openNav() {

		document.getElementById("mySidenav1").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";

		document.getElementById("yeshh1").style.width = "75%";
		$("#contractorid").val("");
		$("#totalWorks").text("");
		$("#contractorName").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#contractorType").val("");
		$("#gstIn").val("");
		$("#panId").val("");
		$("#address1").val("");
		$("#address2").val("");
		$("#city").val("");
		$("#state").val("");
		$("#pinId").val("");
		$("#status").val("");
	}

	function closeNav() {

		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("yeshh").style.width = "100%";
		
	}

	function openNav1() {
		
		$("#contractorid").val("");
		$("#totalWorks").text("");
		$("#contractorName").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#contractorType").val("");
		$("#gstIn").val("");
		$("#panId").val("");
		$("#address1").val("");
		$("#address2").val("");
		$("#city").val("");
		$("#state").val("");
		$("#pinId").val("");
		$("#status").val("");
		document.getElementById("mySidenav").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:200px;";

		document.getElementById("yeshh").style.width = "73%";
		
	}
	function deleteFun1() {
		$("#myGrid2").show();
		$("#myGrid3").hide();
		$("#yeshh").hide();
		$("#yeshh1").show();
		$("#delete").show();

		document.getElementById("main").style.width = "100%";
	}
	function closeNav1() {
		
		$('#deleteId').attr('disabled', true);
	    $('#addId').attr('disabled', false);
	$("#contractorid").val("");
		$("#totalWorks").text("");
		$("#contractorName").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#contractorType").val("");
		$("#gstIn").val("");
		$("#panId").val("");
		$("#address1").val("");
		$("#address2").val("");
		$("#city").val("");
		$("#state").val("");
		$("#pinId").val("");
		$("#status").val("");
		document.getElementById("mySidenav1").style.width = "0";
		document.getElementById("yeshh1").style.width = "100%";
		 
		
	}
	//main table

	var columnDefs2 = [
			{
				headerCheckboxSelection : true,
				headerCheckboxSelectionFilteredOnly : true,
				checkboxSelection : true,
				width : 10,
				sortable : false,
				filter : false,
				resizable : true
			},
			{
				headerName : "Contractor ID",
				field : "contractorId",
				cellRenderer : function(params) {
					return '<a id="contractorId" onclick=editSubcontractor("'
							+ params.data.contractorId
							+ '") href="javascript:void(0)">'
							+ params.data.contractorId + '</a>';

				},
			}, {
				headerName : "Contractor Name",
				field : "contractorName",
			}, {
				headerName : "Email",
				field : "email",
			}, {
				headerName : "Phone",
				field : "phone",
			}, {
				headerName : "Contractor Type",
				field : "contractorType",
			}, {
				headerName : "GSTIN",
				field : "gstIn",
			}, {
				headerName : "PAN",
				field : "panId",
			}, {
				headerName : "Address1",
				field : "address1",
			}, {
				headerName : "Address2",
				field : "address2",
			}, {
				headerName : "City",
				field : "city",
			}, {
				headerName : "State",
				field : "state",
			}, {
				headerName : "PIN",
				field : "pinId",
			}, {
				headerName : "Status",
				field : "status",
				cellRenderer: function(params) {
			if (params.data.status == "1") {
				return 'Active';
			} else {
				return 'Inactive';
			}
		}
			} ];

	var gridOptions2 = {
		columnDefs : columnDefs2,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 180,
			height : 10
		},
		rowSelection : 'single',
		rowMultiSelectWithClick : true,
		onSelectionChanged : deleteDetails

	};
	
	//child table
	var columnDefs3 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "WorkDetails ID",
		field : "subContractorId",
		cellRenderer : function(params) {
			return '<a id="subContractorId" onclick=editSubContractorWorkId("'
					+ params.data.subContractorId
					+ '") href="javascript:void(0)">'
					+ params.data.subContractorId + '</a>';

		},
	}, {
		headerName : "Project Name",
		field : "projectName",
	}, {
		headerName : "Task/Activity",
		field : "task"
	}, {
		headerName : "Scope of the Work",
		field : "scopeoftheWork"
	}, {
		headerName : "Duration of the Work",
		field : "durationoftheWork"
	}, {
		headerName : "License Verified",
		field : "licenseVerified"
	}, {
		headerName : "Statement of Intent Recieved",
		field : "statementofintentRecieved"
	}, {
		headerName : "Request to Sublet Recieved",
		field : "requesttosubletRecieved"
	}, {
		headerName : "Schedule of Work Recieved",
		field : "scheduleofworkRecieved"
	}, {
		headerName : "Drawings Provided",
		field : "drawingsProvided"
	}, {
		headerName : "Punchlist Complete",
		field : "punchlistComplete"
	}, {
		headerName : "Date of Notice to Proceed Issued",
		field : "dateofnoticetoproceedIssued"
	}, {
		headerName : "Date Contract Executed",
		field : "datecontractExecuted"
	}, {
		headerName : "Date Insurance Recieved",
		field : "dateinsuranceRecieved"
	}, {
		headerName : "Other Required Documentation",
		field : "otherrequiredDocumentation"
	} ];

	var gridOptions3 = {
		columnDefs : columnDefs3,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 242,
			height : 10
		},
		rowSelection : 'single',
		onSelectionChanged : onChangedSubContractorWorkDelete

	};

	function deleteFun() {
		$('#deleteModal').modal('show');
	}
	//main table add data

	function addContractor() {
		var datas = [];
		var rowCount = gridOptions2.api.getDisplayedRowCount();
		var validation = true;
		
				var item = {};
				item.contractorId = $("#contractorid").val();
				item.contractorName = $("#contractorName").val();
				item.email = $("#email").val();
				item.phone = $("#phone").val();
				item.contractorType = $("#contractorType").val();
				item.gstIn = $("#gstIn").val();
				item.panId = $("#panId").val();
				item.address1 = $("#address1").val();
				item.address2 = $("#address2").val();
				item.city = $("#city").val();
				item.state = $("#state").val();
				item.pinId = $("#pinId").val();
				item.status = $("#status").val();

				if (item.contractorName == null || item.contractorName == "") {
					validation = validationUpdated("Contractor Name Required",
							"contractorName");
				}
				if (item.email == null || item.email == "") {
					validation = validationUpdated("Email Required", "email");
				}
				if (item.phone == null || item.phone == "") {
					validation = validationUpdated("Phone Required", "phone");
				}
				if (item.contractorType == null || item.contractorType == "") {
					validation = validationUpdated("Contractor Type Required",
							"contractorType");
				}
				if (item.gstIn == null || item.gstIn == "") {
					validation = validationUpdated("GSTIN Required", "gstIn");
				}
				if (item.panId == null || item.panId == "") {
					validation = validationUpdated("PAN ID Required", "panId");
				}
				if (validation) {
					//datas.push(item);
					saveContractor(item);
					console.log(item)
				}
			

		
	}

	function saveContractor(item) {
		$.ajax({
			type : "POST",
			url : "project-sub-contractor-add",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(item),
			success : function(response) {
				if (response.code == "success") {
					agGrid.simpleHttpRequest({
						url : 'project-sub-contractor-view'
					}).then(function(data) {
						var jsonData = JSON.parse(data.body[0]);
						var allData = jsonData.SubContractor;
						console.log(allData)
						var len = allData.length;
						$('#totalConts').find('span').html(len);
						gridOptions2.api.setRowData(allData);
					});
					$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					closeNav1();
					$("#contractorid").val("");
					$("#totalWorks").text("");
					$("#contractorName").val("");
					$("#email").val("");
					$("#phone").val("");
					$("#contractorType").val("");
					$("#gstIn").val("");
					$("#panId").val("");
					$("#address1").val("");
					$("#address2").val("");
					$("#city").val("");
					$("#state").val("");
					$("#pinId").val("");
					$("#status").val("");
				}
			},
			error : function(data) {
			}
		});
	}

	//main table edit data 
	function editSubcontractor(contractorId) {
		agGrid.simpleHttpRequest({
			url : 'project-sub-contractor-edit?id=' + contractorId
		}).then(function(data) {
			var jsonData = JSON.parse(data.body[0]);
			var allData = jsonData.SubContractor;
			console.log(allData);
			$("#contractorid").val(contractorId);
			$("#totalWorks").text(contractorId);
			$("#contractorName").val(allData[0].contractorName);
			$("#email").val(allData[0].email);
			$("#phone").val(allData[0].phone);
			$("#contractorType").val(allData[0].contractorType);
			$("#gstIn").val(allData[0].gstIn);
			$("#panId").val(allData[0].panId);
			$("#address1").val(allData[0].address1);
			$("#address2").val(allData[0].address2);
			$("#city").val(allData[0].city);
			$("#state").val(allData[0].state);
			$("#pinId").val(allData[0].pinId);
			$("#status").val(allData[0].status);
			//gridOptions2.api.setRowData(allData);
			openNav();
		});
	}

	//main table delete data
	function deleteOnclick() {
		if (id) {
			$
					.ajax({
						type : "POST",
						url : "project-sub-contractor-delete?id=" + id,
						success : function(response) {
							if (response.message == "Success") {
								deleteFun();
								$("#messageParagraph").text(
										"Contractor Plan deleted sucessfully");
								$("#msgOkModal").removeClass("btn3");
								$("#msgOkModal").addClass("btn1");
								$("#msgModal").modal('show');
								$('#deleteModal').modal('hide');
								location.reload();
							} else {
								$("#messageParagraph").text(
										"Something went to wrong!");
								$("#msgOkModal").removeClass("btn3");
								$("#msgOkModal").addClass("btn1");
								$("#msgModal").modal('show');
							}
						},
						error : function(data) {
							console.log(data)
						}
					})
		}

	}

	function cancelModalBtn() {
		$("#deleteModalBtn").removeAttr("disabled");
	}

	var id = "";

	function deleteDetails() {
	  var selectedNodes = gridOptions2.api.getSelectedNodes();
	  var selectedData = selectedNodes.map(node => node.data);
	  id = selectedData.map(node => node.contractorId);
	  
	  var selectedRows = gridOptions2.api.getSelectedRows();
	  var rowCount = selectedRows.length;

	  if (rowCount > 0) {
	    $('#addId').attr('disabled', true);
	    $('#deleteId').attr('disabled', false);
	    
	    // Set the contractor ID in the hidden input field
	    $('#contractorid').val(id);
	    
	    // Set the contractor ID in the span element
	    $('#projectId1').text(id);
	    
	     agGrid.simpleHttpRequest({
			url : "project-sub-contractor-project-data-view?id="+id
		}).then(function(data) {
			gridOptions3.api.setRowData(data);
		});
		
	  } else {
	    $('#deleteId').attr('disabled', true);
	    $('#addId').attr('disabled', false);
	    
	    // Clear the contractor ID from the hidden input field
	    $('#contractorid').val('');
	    
	    // Clear the contractor ID from the span element
	    $('#projectId1').text('');
	  }
	  $("#myGrid3").show();
	}

	//child table save data 
	function addContractorWork() {
				obj = {};
				obj.subContractorId = $("#subContractorid").val();
				obj.projectId = $("#projectId1").html();
				obj.projectName = $("#projectName").val();
				obj.task = $("#task").val();
				obj.scopeoftheWork = $("#scopeoftheWork").val();
				obj.durationoftheWork = $("#durationoftheWork").val();
				obj.licenseVerified = $("#licenseVerified").val();
				obj.statementofintentRecieved = $("#statementofintentRecieved").val();
				obj.requesttosubletRecieved = $("#requesttosubletRecieved").val();
				obj.scheduleofworkRecieved = $("#scheduleofworkRecieved").val();
				obj.drawingsProvided = $("#drawingsProvided").val();
				obj.punchlistComplete = $("#punchlistComplete").val();
				obj.dateofnoticetoproceedIssued = $("#dateofnoticetoproceedIssued").val();
				obj.datecontractExecuted = $("#datecontractExecuted").val();
				obj.dateinsuranceRecieved = $("#dateinsuranceRecieved").val();
				obj.otherrequiredDocumentation = $("#otherrequiredDocumentation").val();
				console.log(obj);
				

				var validation = true;

				if (obj.projectName == null || obj.projectName == "") {
				validation = validationUpdated("Project Name Required",
					"projectName");
				}
				if (obj.task == null || obj.task == "") {
				validation = validationUpdated("Task Required",
					"task");
				}
				if (obj.scopeoftheWork == null || obj.scopeoftheWork == "") {
				validation = validationUpdated("Scope Of The Work Required", "scopeoftheWork");
				}
				if (obj.durationoftheWork == null || obj.durationoftheWork == "") {
				validation = validationUpdated("Duration Of The Work Required", "durationoftheWork");
				}
				if (obj.licenseVerified == null || obj.licenseVerified == "") {
				validation = validationUpdated("License Verification Required", "licenseVerified");
				}
				if (validation) {
				$.ajax({
				type : "POST",
				url : "project-sub-contractor-add-work",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					if (response.message == "Success") {

						$("#messageParagraph").text("Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						closeNav();
						agGrid.simpleHttpRequest({
							url : "project-sub-contractor-view-work"
						}).then(function(data) {
							var len = data.length;
							$('#totalWorks').find('span').html(len);
							gridOptions3.api.setRowData(data);
						});
					}
				},
				error : function(data) {
				} 
			})
		}
	}
	
	/* Function autosearch for get project name */
	function getProjectNameAutoSearch() {
		var search = $("#projectName").val();
		if (search == "") {
			$("#suggesstion-box1").hide();
		}
		if (search) {
			$
					.ajax({
						type : "GET",
						url : "project-sub-contractor-autosearch-projName?searchValue="
								+ search,
						success : function(response) {
							if (response.message == "success") {
								console.log(response);
								if (response.body.length != 0) {
									$("#search").css("background", "#FFF");
									var content = '<ul id="autocomplete-list" style="color:#ccc;">';
									for (var i = 0; i < response.body.length; i++) {
										
										content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue1(\''
												+ response.body[i].name
												+ '\',\''
												+ response.body[i].key
												+ '\')">'
												+ response.body[i].name
												+ '</li>';
									}
									content += '</ul>';
									$("#suggesstion-box1").show();
									$("#suggesstion-box1").html(content);

								} else {
									$("#search").css("background", "#FFF");
									var content = '<div id="autocomplete-list">';
									content += '<div onClick="selectAutocompleteValue1()">'
											+ "No Data Found" + '</div>';
									content += '</div>';
									$("#suggesstion-box1").show();
									$("#suggesstion-box1").html(content);

								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
		}

	}
	function selectAutocompleteValue1(name) {
		if (name) {
			$("#projectName").val(name);
			$("#search").val(name);
			$("#search").attr('data-procat', name);
			$("#suggesstion-box1").hide();

		} else {
			$("#projectName").val("");
			$("#search").val("");
			$("#search").attr('data-procat', "");
			$("#suggesstion-box1").hide();

		}
	}
	
	//child table edit data
	function editSubContractorWorkId(subContractorId)
	 {
		   
	$.ajax({
		type : "POST",
		url : "project-sub-contractor-work-edit?Id=" + subContractorId,
		dataType : 'json',
		contentType : 'application/json',
		data : subContractorId,
			success : function(response) {
				if (response.message == "success") {
					
				console.log("edit", response.body)
				$("#subContractorid").val(response.body[0].subContractorId);
				$("#projectName").val(response.body[0].projectName);
				$("#task").val(response.body[0].task);
				$("#scopeoftheWork").val(response.body[0].scopeoftheWork);
				$("#durationoftheWork").val(response.body[0].durationoftheWork);
				$("#licenseVerified").val(response.body[0].licenseVerified);
				$("#statementofintentRecieved").val(response.body[0].statementofintentRecieved);
				$("#requesttosubletRecieved").val(response.body[0].requesttosubletRecieved);
				$("#scheduleofworkRecieved").val(response.body[0].scheduleofworkRecieved);
				$("#drawingsProvided").val(response.body[0].drawingsProvided);
				$("#punchlistComplete").val(response.body[0].punchlistComplete);
				$("#dateofnoticetoproceedIssued").val(response.body[0].dateofnoticetoproceedIssued);
				$("#datecontractExecuted").val(response.body[0].datecontractExecuted);
				$("#dateinsuranceRecieved").val(response.body[0].dateinsuranceRecieved);
				$("#otherrequiredDocumentation").val(response.body[0].otherrequiredDocumentation);
				openNav1();
			}
		},
		
		error : function(data) {
		}
	});

	}
	
	//child atble delete data
	
	function deleteSubContractorWork() {
		var selectedRows = gridOptions3.api.getSelectedRows();
		 var id=selectedRows[0].subContractorId;
		
		
			$.ajax({
				type : "POST",
				url : "project-sub-contractor-work-delete?id="+ id,
				success : function(response) {
				 if (response.message == "Success") {
					 agGrid.simpleHttpRequest({
							url : "project-sub-contractor-view-work"
						}).then(function(data) {
							gridOptions3.api.setRowData(data);
						});

					 closeNav();
				} 
				},
				error : function(data) {
				console.log(data);
				}
			})
	} 
	
	var deleteid = "";
	function onChangedSubContractorWorkDelete() {
		var selectedRows = gridOptions3.api.getSelectedRows();

		deleteid = "";
		for (var i = 0; i < selectedRows.length; i++) {
			deleteid = deleteid + '"' + selectedRows[i].subContractorId + '",';

		}
		deleteid = deleteid.substring(0, deleteid.length - 1);

		var rowCount = 0;
		selectedRows.forEach(function() {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#deleteId1').attr("disabled", false);
			$('#addId1').hide();
		if (rowCount == 1) {
			$('#addId1').hide();
		}
		}else {
			$('#deleteId1').attr("disabled", true);
			$('#addId1').show();
			
		}

	}
	
	
	