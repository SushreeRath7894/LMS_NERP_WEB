$(document).ready(function() {
		$("#myGrid").hide();
		$("#myGrid1").hide();
		$("#cancelgrid").hide();
		$("#cancelgrid2").hide();
		$("#addgrid").hide();
		$("#addgrid2").hide();
		$("#basic2").hide();
		//$('#newTask').attr('disabled', true);
		$('#deleteTask').attr('disabled', true);

		var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptions2);
		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1);
		var gridDiv1 = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv1, gridOptions3);
		
		agGrid.simpleHttpRequest({
			url : "project-execution-view-through-ajax"
		}).then(function(data) {
			console.log(data)
			gridOptions2.api.setRowData(data);
		
		var firstRowNode = gridOptions2.api.getDisplayedRowAtIndex(0); // Get the first row node
		  if (firstRowNode) {
			  console.log('firstRowNodessss',firstRowNode)
		    firstRowNode.setSelected(true); // Set the row as selected
		  }
		/*var selectedRows=gridOptions2.api.getSelectedRows();
		projectId=selectedRows[0].projectId;
		console.log('projectId',projectId);*/
		//rowSelect();

		});
		
		
		
		$("#selectCategory").click(function(){
 			
			var data = {};
	    	data.executionId = $("#executionId").val();
	    	data= x;
	    	console.log(data,'jhgfds')
			$("#myModal").modal("hide");
	    	submitCategory(data);
		});
			
			var dateFormat = localStorage.getItem("dateFormat");
			$("#startDateCalendar").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#startDate').val($(this).val());
			})

			$('#startDate').blur(function() {
				$("#startDateCalendar").val($(this).val());
			})

			$("#endDateCalendar").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#endDate').val($(this).val());
			})

			$('#endDate').blur(function() {
				$("#endDateCalendar").val($(this).val());
			})
			
			$("#requiDateCalendar").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#requiDate').val($(this).val());
			})

			$('#requiDate').blur(function() {
				$("#requiDateCalendar").val($(this).val());
			})
			
			
			$("#needDateCalendar").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#needDate').val($(this).val());
			})

			$('#needDate').blur(function() {
				$("#needDateCalendar").val($(this).val());
			})
			
			
			$("#startDateCalendarMain").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#startDateMain').val($(this).val());
			})

			$('#startDateMain').blur(function() {
				$("#startDateCalendarMain").val($(this).val());
			})

			$("#endDateCalendarMain").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#endDateMain').val($(this).val());
			})

			$('#endDateMain').blur(function() {
				$("#endDateCalendarMain").val($(this).val());
			})
			
			$("#requiDateCalendarMain").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#requiDateMain').val($(this).val());
			})

			$('#requiDateMain').blur(function() {
				$("#requiDateCalendarMain").val($(this).val());
			})
			
			
			$("#needDateCalendarMain").datetimepicker({
				format : dateFormat,
				closeOnDateSelect : true,
				timepicker : false,
			}).on("change", function() {
				$('#needDateMain').val($(this).val());
			})

			$('#needDateMain').blur(function() {
				$("#needDateCalendarMain").val($(this).val());
			})
			

	});

	function submitCategory(data) {
		console.log(data,'ssss')
		$.ajax({
			type : "POST",
			url : "project-execution-tasks-save",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					var idds = response.body[0].projectId
					getProjectExecutionDetails(idds);
					
					
				}
			},
			error : function(data) {
				console.log(data)
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		});
	}


	var columnDefs2 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Project Id",
		field : "projectId",
	}, {
		headerName : "Project Name",
		field : "projectName",
	}, {
		headerName : "Creation Date",
		field : "creationDate",
		width : 150,
	}, {
		headerName : "Category",
		field : "category",
		width : 150,
	}, {
		headerName : 'Location',
		field : "location",
		width : 130,
	}, {
		headerName : "State",
		field : "stateId",
		width : 150,
	}, {
		headerName : 'Pin',
		field : "pinId",
	}, {
		headerName : 'Project Incharge',
		field : "projectIncharge",
	}, {
		headerName : 'Customer Name',
		field : "customerName",
	}, {
		headerName : 'Customer Address',
		field : "customerAddress",
	}, {
		headerName : 'State',
		field : "stateId1",
		width : 150,

	}, {
		headerName : 'Pin',
		field : "pinId1",
		width : 150,

	}, {
		headerName : 'Email',
		field : "email",
		width : 150,

	}, {
		headerName : 'Mobile',
		field : "mobile",
		width : 150,

	}, {
		headerName : 'Remarks',
		field : "remarks",
		width : 150,

	}, {
		headerName : 'Status',
		field : "status",
		width : 150,

	} ];

	var gridOptions2 = {
		columnDefs : columnDefs2,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'single',
		
		onSelectionChanged : rowSelect,
		getRowNodeId : function(data) {
			return data.projectId;
		}
	//onSelectionChanged : onSelectionChanged
	};
	var columnDefs1 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Slno.",
		field : "slNo",
		width : 150,
		
		 cellRenderer : function(params) {
		 return '<a instrno="editId" onclick=editId("'
		 + params.data.slNo
		 + '") href="javascript:void(0)">'
		 + params.data.slNo + '</a>';
		 }, 
		cellStyle : {
			textAlign : 'center'
		}
	},  {
		headerName : " catID",
		field : "categoryId",
		width : 150,
		hide : true,
	}, {
		headerName : " projectId",
		field : "projectId",
		width : 150,
		hide : true,
	}, {
		headerName : " Planned Start Date",
		field : "startDate",
		width : 150
	}, {
		headerName : "Planned End Date",
		field : "endDate",
		width : 150,
	}, {
		headerName : 'Phase/Task',
		field : "categoryName",
		type : 'rightAligned',
		//valueFormatter : currencyFormatter,
		width : 150
	}, {
		headerName : "Assigned To",
		field : "assignedTo",
		width : 150
	},{
		headerName : " Actual Start Date",
		field : "actualStartDate",
		width : 150
	}, {
		headerName : "Actual End Date",
		field : "actualEndDate",
		width : 150,
	},  {
		headerName : "Quality Needed",
		field : "qtyNeeded",
		width : 150
	}, {
		headerName : "Quality Received",
		field : "qtyRecieved",
		width : 150
	}, {
		headerName : "Planned Hours",
		field : "plannedHrs",
		width : 150
	}, {
		headerName : "Actual Hours",
		field : "actualHrs",
		width : 150
	}, {
		headerName : "Attachment",
		field : "fileAttach",
		width : 150,
		cellRenderer : function(params) {
			var div = "";
			if (params.data.fileAttach) {
				var ext = params.data.fileAttach.split(".");
				if (ext[1] == "pdf") {
					div = div
							+ " "
							+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("'
							+ params.data.fileAttach + '")> </div>';
				} else {
					div = div
							+ " "
							+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("'
							+ params.data.fileAttach + '")> </div>';
				}
			}
			return div;
		}
	}, {
		headerName : 'Notes',
		field : "notes",
		width : 150
	},{
		headerName : 'FeedBack',
		field : "feedBack",
		width : 150
	} ];
	var rowdata1=[];
	var gridOptions1 = {
			columnDefs : columnDefs1,
			rowData : rowdata1,
			defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 200,
				height : 20
			},
			rowSelection : 'single',
		onSelectionChanged : onSelectionChanged
		};
	var gridOptions3 = {
			columnDefs : columnDefs1,
			rowData : rowdata1,
			defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 200,
				height : 20
			},
			rowSelection : 'single',
		onSelectionChanged : onSelectionChanged
		};
	var deleteId = "";
function onSelectionChanged(){
	//$('#newTask').attr('disabled', false);
	$('#deleteTask').attr('disabled', false);
	var selected = gridOptions3.api.getSelectedRows();
		id = selected[0].categoryId ;
		id2 = selected[0].projectId;
		deleteId = "";

		for (var i = 0; i < selected.length; i++) {
			deleteId = selected[i].slNo;
		}

}
function deleteTaskDetail(){
	$("#deleteModal").modal('show');
}
function deleteFun(){
	console.log("delete ids--------------"+deleteId+" @@ " +id+"  @@@@ "+id2);

	$.ajax({
		type : "GET",
		url : "project-execution-delete-task?id=" + deleteId,
		success : function(response) {
			if (response.code == "Success") {
				$("#deleteModal").modal('hide');
				CategoryDetails(id,id2);

			}
		}

	});

	$('#delete').attr("disabled", true);
}
function setFromToDate() {
	$("#messageParagraph").text("Please choose to date greater than or equal to from date ");
	$("#msgOkModal").removeClass("btn3");
	$("#msgOkModal").addClass("btn1");
	$("#msgModal").modal('show');
	$("#startDate").val("");
	$("#endDate").val("");
}
function cancelPopUp(){
	$("#popup").modal('hide');
}

function getPrecedNameAutoSearch() {
	var search = $("#preced").val();
	if (search == "") {
		$("#suggesstion-box1").hide();
	}
	if (search) {
		$
				.ajax({
					type : "GET",
					url : "project-execution-autosearch-preced?searchValue="
							+ search,
					success : function(response) {
						if (response.message == "success") {
							console.log(response);
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list" style="color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									
									content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue1(\''
											+ response.body[i].key
											+ '\',\''
											+ response.body[i].name
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

function selectAutocompleteValue1(key) {
	if (key) {
		$("#preced").val(key);
		$("#preced").html(key);
		$("#search").val(key);
		$("#search").attr('data-procat', key);
		$("#suggesstion-box1").hide();
	//	getDate1(key);

	} else {
		$("#preced").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1").hide();

	}
}

function getAssignedToAutoSearch() {
	var search = $("#assign").val();
	console.log("searchhh in getAssignedToAutoSearch"+search)
	if (search == "") {
		$("#suggesstion-box2").hide();
	}
	if (search) {
		$
				.ajax({
					type : "GET",
					url : "project-execution-autosearch-assignTo?searchValue="
							+ search,
					success : function(response) {
						if (response.message == "success") {
							console.log(response);
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list" style="color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									
									content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue2(\''
											+ response.body[i].key
											+ '\',\''
											+ response.body[i].name
											+ '\')">'
											+ response.body[i].name
											+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-box2").show();
								$("#suggesstion-box2").html(content);

							} else {
								$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list">';
								content += '<div onClick="selectAutocompleteValue2()">'
										+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-box2").show();
								$("#suggesstion-box2").html(content);

							}
						}
					},
					error : function(data) {
						console.log(data);
					}
				})
	}

}
function selectAutocompleteValue2(key,name) {
	if (name) {
		$("#assignedTo").val(key);
		$("#assign").val(name);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box2").hide();
		console.log(key ,name)
		//getDate1(key);

	} else {
		$("#assignedTo").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box2").hide();

	}
}
function getAssignedToMainAutoSearch() {
	var search = $("#ATnameMain").val();

	console.log("searchhh in getAssignedToMainAutoSearch "+search)
	if (search == "") {
		$("#suggesstion-box3").hide();
	}
	if (search) {
		$
				.ajax({
					type : "GET",
					url : "project-execution-autosearch-assignTo?searchValue="
							+ search,
					success : function(response) {
						if (response.message == "success") {
							console.log(response.body);
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list" style="color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									console.log( response.body[i].key+" - "+ response.body[i].name)
									
									content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue3(\''
											+ response.body[i].key
											+ '\',\''
											+ response.body[i].name
											+ '\')">'
											+ response.body[i].name
											+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-box3").show();
								$("#suggesstion-box3").html(content);

							} else {
								$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list">';
								content += '<div onClick="selectAutocompleteValue3()">'
										+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-box3").show();
								$("#suggesstion-box3").html(content);

							}
						}
					},
					error : function(data) {
						console.log(data);
					}
				})
	}

}
function selectAutocompleteValue3(key,name) {

	if (name) {
		$("#ATnameMain").val(name);
		$("#assignedToMain").val(key);
		$("#search").val(name);
		$("#search").attr('data-procat', name);
		$("#suggesstion-box3").hide();
		//getDate1(key);

	} else {
		$("#assignedTo").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3").hide();

	}
}

function dateChange() {
	
	var fromdate = $('#startDate').val();
	var todate = $('#endDate').val();

	//alert("sDate "+sDate+" eDate "+eDate+" fromdate "+fromdate+" todate "+todate)
	 var date1 = convertDateFormat(sDate); //parent start date
	 var date2 = convertDateFormat(eDate);  //parent end date
	 var date3 = convertDateFormat(fromdate);  //child start date
	 var date4 = convertDateFormat(todate);  //child end date
	 
	 var d1 = new Date(date1);  //parent start date
	 var d2 = new Date(date2);  //parent end date
	 var d3 = new Date(date3);   //child start date 
	 var d4 = new Date(date4);  //child end date
	 var t1 = d1.getTime()/(60*60*1000); //parent start date time in hrs
	 var t2 = d2.getTime()/(60*60*1000);  //parent end date time in hrs
	 var t3 = d3.getTime()/(60*60*1000);  //child start date time in hrs
	 var t4 = d4.getTime()/(60*60*1000);  //child end date time in hrs
	 
	//alert(t1+" @ "+t2+" # "+t3+" $ "+t4)
	 
	
	if( t1 > t3 ||  t1 > t4 ){
		alert("Error paarent sd < child sd")
		$('#startDate').val("");
		$("#popup").modal('show');
	}
	else if( t2 < t3 ||  t2 < t4 ){
		alert("Error parent ed < child ed")
		$('#startDate').val("");
		$("#popup").modal('show');
	}
	else{
		alert("in else")
	         
	}
	
	var fd = fromdate.split("-");
	var td = todate.split("-");
	
	if (fromdate != '' && todate != '') {
		if(fd[2]<=td[2]){
			if(fd[1]==td[1]){
				if(fd[0]<=td[0]){
					
				}else{
					setFromToDate();
				}
			}else if(fd[1]<td[1]){
				
			}else{
				setFromToDate();
			}
			
		}else{
			setFromToDate();
		}
	}else{
		
	}
}

function dateChange1() {

	
	var fromdate = $('#asdate').val();
	var todate = $('#aedate').val();
	 var date1 = convertDateFormat(psd); //parent start date
	 var date2 = convertDateFormat(ped);  //parent end date
	 var date3 = convertDateFormat(fromdate);  //child start date
	 var date4 = convertDateFormat(todate);  //child end date
	 
	 var d1 = new Date(date1);  //parent start date
	 var d2 = new Date(date2);  //parent end date
	 var d3 = new Date(date3);   //child start date 
	 var d4 = new Date(date4);  //child end date
	 var t1 = d1.getTime()/(60*60*1000); //parent start date time in hrs
	 var t2 = d2.getTime()/(60*60*1000);  //parent end date time in hrs
	 var t3 = d3.getTime()/(60*60*1000);  //child start date time in hrs
	 var t4 = d4.getTime()/(60*60*1000);  //child end date time in hrs
	 
	//alert(t1+" @ "+t2+" # "+t3+" $ "+t4)
	 
	
	if( t1 > t3 ||  t1 > t4 ){
		alert("Error paarent sd < child sd")
		$('#startDate').val("");
		$("#popup").modal('show');
	}
	else if( t2 < t3 ||  t2 < t4 ){
		alert("Error parent ed < child ed")
		$('#startDate').val("");
		$("#popup").modal('show');
	}
	var fd = fromdate.split("-");
	var td = todate.split("-");	
	if (fromdate != '' && todate != '') {
		if(fd[2]<=td[2]){
			if(fd[1]==td[1]){
				if(fd[0]<=td[0]){
					
				}else{
					setFromToDate();
				}
			}else if(fd[1]<td[1]){
				
			}else{
				setFromToDate();
			}
			
		}else{
			setFromToDate();
		}
	}else{		
	}
}
function onSelectionChanged1(){
//	$('#newTask').attr('disabled', false);
	var selected = gridOptions3.api.getSelectedRows();
		id = selected[0].slNo ;
		//id2 = selected[0].projectId;
}
	var id ="";
	var id2="";
	function rowSelect() {
		
		$("#myGrid").hide();
		$("#myGrid1").hide();
		$("#basic2").hide();
		$("#table").show();
		$("#basic1").show();
			var selected = gridOptions2.api.getSelectedRows();
			var rowCount = 0;
			
			selected.forEach(function(selectedRow, index) {
				rowCount = rowCount + 1;
			});
			for (var i = 0; i < selected.length; i++) {
				id = id  + selected[i].projectId ;
			}
			getFromPlanning(id);
			getProjectExecutionDetails(id);
			id2=id;
			
			
			
			if (rowCount > 0) {
				$('#delete').attr('disabled', false);
			} else {
				$('#delete').attr('disabled', true);
			}
			id ="";
		}
	
	function getCategoryList() {
		var projectidd = $("#projectNameModal").val();
		
		$("#productCategoryCBDiv").empty();
	
		$
				.ajax({
					type : "GET",
					url : "project-execution-category-get-total-list?id="+ projectidd,
					dataType : "json",
					contentType : "application/json",
					success : function(response) {
						if (response.message == "Success") {
							$("#productCategoryCBDiv").empty();
							for (var i = 0; i < response.body.length; i++) {
								var row = "";
								if (response.body[i].categoryId == response.body[i].parentId) {

									row = '<tr data-node-id="'+response.body[i].categoryId+'" class="abc" id="'+response.body[i].categoryId+'">'
											+ '<td class="firstnode1" id=lbl_'+response.body[i].categoryId+'><input class="benefitChk" type="checkbox" id="ccCheck_'
											+ response.body[i].categoryId
											+ '" value="'
											+ response.body[i].catLevel
											+ '" name="'
											+ response.body[i].categoryName
											+ '" onchange=selectCheckBox("'
											+ response.body[i].categoryId
											+ '","'
											+ response.body[i].catLevel
											+ '","'
											+ response.body[i].projectId
											+ '","'
											+ response.body[i].nodeSlNo
											+ '","'
											+ response.body[i].parentId
											+ '","'
											+ response.body[i].categoryName
											+ '")>'
											+ response.body[i].nodeSlNo
											+' - '
											+ response.body[i].categoryName
											+ '</td></tr>';

								} else {

									row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].categoryId+'">'
											+ '<td class="firstnode1" id=lbl_'+response.body[i].categoryId+'><input class="benefitChk" type="checkbox" id="ccCheck_'
											+ response.body[i].categoryId
											+ '" value="'
											+ response.body[i].catLevel
											+ '" name="'
											+ response.body[i].categoryName
											+ '" onchange=selectCheckBox("'
											+ response.body[i].categoryId
											+ '","'
											+ response.body[i].catLevel
											+ '","'
											+ response.body[i].projectId
											+ '","'
											+ response.body[i].nodeSlNo
											+ '","'
											+ response.body[i].parentId
											+ '","'
											+ response.body[i].categoryName
											+ '")>'
											+ response.body[i].nodeSlNo
											+' - '
											+ response.body[i].categoryName
											+ '</td></tr>';

								}
								$("#productCategoryCBDiv").append(row);

							}

							var pcat = $("#prCategoryId").val();
							$(".benefitChk").prop("checked", false);
							$("#ccCheck_" + pcat).prop("checked", true);

							$('.loader').hide();
							$("body").removeClass("overlay");

							$('#basic1').simpleTreeTable({
								expander : $('#expander'),
								collapser : $('#collapser'),
								store : 'session',
								storeKey : 'simple-tree-table-basic'
							});
						} else {
							swal({
								title : response.code,
								text : response.message,
								type : "warning"
							})
						}
					},
					error : function(response) {
						console.log(response);
					}
				})
	}
	var x=[];
	function selectCheckBox(id, lvl,pid,slno,pidd,name) {
		var selectedRowsProject = gridOptions2.api.getSelectedRows();
		console.log(selectedRowsProject[0].projectId)
		var item ={};
		
		$("#ccCheck_" + id).prop("checked", true);
		var checkboxs = $(".benefitChk:checked").length;
		item.categoryId = id;
		item.catLevel = lvl;
		item.projectId =selectedRowsProject[0].projectId;
		item.nodeSlNo = slno;
		item.parentId = pidd;
		item.categoryName = name;
		item.executionId = $("#executionId").val();
		x.push(item);
		console.log(item,'ghghg');
	}

	function cancelR() {
		$('#myModal').modal('hide');
	}
	function submitR() {
		$('#myModal').modal('hide');
	}
	function convertDateFormat(inputDate) {
	    var parts = inputDate.split('-'); 
	    var day = parseInt(parts[0], 10);
	    var month = parseInt(parts[1], 10);
	    var year = parseInt(parts[2], 10);
	    
	    var dateObject = new Date(year, month - 1, day);
	    
	    var formattedDate = (dateObject.getMonth() + 1) + '-' + dateObject.getDate() + '-' + dateObject.getFullYear();
	    
	    return formattedDate;
	}
	function getProjectExecutionDetails(exeId) {
		$("#exeId").empty();
		alert(exeId)
		$("#tbodyData12").empty();
		obj = {};
		obj.projectId = exeId;
		$.ajax({
	        type: "POST",
	        url: "project-execution-details-list",
	        dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(obj),
	        success: function(response) {
	        	if (response.code == "success") {
	        		console.log(response)
					$("#tbodyData12").empty();
	        		 //if( response.body.length){
	        			 for (var i = 0; i < response.body.length; i++) {
	 						var row = "";
	 						var result = 0;
	 						var d1 = convertDateFormat(response.body[i].startDate)
	 		        		var d2 = convertDateFormat(response.body[i].endDate)
	 		        		 var date1 = new Date(d1);  
	 				         var date2 = new Date(d2);
	 				         var time_difference = date2.getTime() - date1.getTime();   
	 				         result = time_difference / (1000 * 60 * 60 * 24); 
	 				         var k = response.body[i].categoryId;
	 				         console.log("------ "+response.body[i].categoryId+" @ "+response.body[i].parentId+" @ "+response.body[i].startDate+" @ "+response.body[i].endDate+" k "+result+" = "+(time_difference/(1000 * 60 * 60 * 24)));
	 				        
	 						if (response.body[i].categoryId == response.body[i].parentId) {
	 							var ext = response.body[i].fileAttach.split(".");
	 							if (ext[1] == "pdf"){
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 									+ '<td class="firstnode" width="300" id=lbl_'+response.body[i].categoryId+'><'
	 									+ response.body[i].categoryId
	 									+ '" value="'
	 									+ response.body[i].catLevel
	 									+ '" name="'
	 									+ response.body[i].categoryName
	 									+  '")>'
	 									+ response.body[i].nodeSlNo
	 									+' - '
	 									+ response.body[i].categoryName
	 									+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","1");></i></span><span><i class="fa fa-edit" onclick=editCategory("'+response.body[i].categoryId+'","'+response.body[i].executionId+'")></i></span>'
	 									+ '</td>'
	 									+'<td class="firstnode">'+result+'</td>'
	 									+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 									+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 									+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 									+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 									+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 									+'<td class="firstnode"><div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("'+response.body[i].fileAttach+'")> </div></td>'
	 									+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 									+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 									+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';
	 							}
	 							else if ( ext[1] == "jpg" || ext[1] == "png" ){
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 									+ '<td class="firstnode" width="300" id=lbl_'+response.body[i].categoryId+'><'
	 									+ response.body[i].categoryId
	 									+ '" value="'
	 									+ response.body[i].catLevel
	 									+ '" name="'
	 									+ response.body[i].categoryName
	 									+  '")>'
	 									+ response.body[i].nodeSlNo
	 									+' - '
	 									+ response.body[i].categoryName
	 									+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","1");></i></span><span><i class="fa fa-edit" onclick=editCategory("'+response.body[i].categoryId+'","'+response.body[i].executionId+'")></i></span>'
	 									+ '</td>'
	 									+'<td class="firstnode">'+result+'</td>'
	 									+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 									+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 									+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 									+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 									+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 									+'<td class="firstnode"><div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("'+response.body[i].fileAttach+'")> </div></td>'
	 									+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 									+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 									+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';
	 							}
	 							else{
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 									+ '<td class="firstnode" width="300" id=lbl_'+response.body[i].categoryId+'><'
	 									+ response.body[i].categoryId
	 									+ '" value="'
	 									+ response.body[i].catLevel
	 									+ '" name="'
	 									+ response.body[i].categoryName
	 									+  '")>'
	 									+ response.body[i].nodeSlNo
	 									+' - '
	 									+ response.body[i].categoryName
	 									+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","1");></i></span><span><i class="fa fa-edit" onclick=editCategory("'+response.body[i].categoryId+'","'+response.body[i].executionId+'")></i></span>'
	 									+ '</td>'
	 									+'<td class="firstnode">'+result+'</td>'
	 									+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 									+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 									+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 									+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 									+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 									+'<td class="firstnode"></td>'
	 									+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 									+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 									+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';
	 							}
	 							
	 						} else {
	 							var ext = response.body[i].fileAttach.split(".");
	 							if (ext[1] == "pdf"){
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 									+ '<td class="firstnode" id=lbl_'+response.body[i].categoryId+'><'
	 									+ response.body[i].categoryId
	 									+ '" value="'
	 									+ response.body[i].catLevel
	 									+ '" name="'
	 									+ response.body[i].categoryName
	 									+  '")>'
	 									+ response.body[i].nodeSlNo
	 									+' - '
	 									+ response.body[i].categoryName
	 									+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","2");></i></span><span><i class="fa fa-edit" onclick=editSubCategory("'+response.body[i].categoryId+'","'+response.body[i].parentId+'","'+response.body[i].projectId+'","'+response.body[i].executionId+'")></i></span>'
	 									+ '</td>'
	 									+'<td class="firstnode">'+result+'</td>'
	 									+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 									+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 									+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 									+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 									+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 									+'<td class="firstnode"><div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("'+response.body[i].fileAttach+'")></div></td>'
	 									+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 									+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 									+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';
	 							}
	 							else if ( ext[1] == "jpg" || ext[1] == "png" ){
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 									+ '<td class="firstnode" id=lbl_'+response.body[i].categoryId+'><'
	 									+ response.body[i].categoryId
	 									+ '" value="'
	 									+ response.body[i].catLevel
	 									+ '" name="'
	 									+ response.body[i].categoryName
	 									+  '")>'
	 									+ response.body[i].nodeSlNo
	 									+' - '
	 									+ response.body[i].categoryName
	 									+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","2");></i></span><span><i class="fa fa-edit" onclick=editSubCategory("'+response.body[i].categoryId+'","'+response.body[i].parentId+'","'+response.body[i].projectId+'","'+response.body[i].executionId+'")></i></span>'
	 									+ '</td>'
	 									+'<td class="firstnode">'+result+'</td>'
	 									+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 									+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 									+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 									+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 									+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 									+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 									+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 									+'<td class="firstnode"><div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("'+response.body[i].fileAttach+'")></div></td>'
	 									+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 									+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 									+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';

	 							} else {
	 								row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].categoryId+'">'
	 								+ '<td class="firstnode" id=lbl_'+response.body[i].categoryId+'><'
	 								+ response.body[i].categoryId
	 								+ '" value="'
	 								+ response.body[i].catLevel
	 								+ '" name="'
	 								+ response.body[i].categoryName
	 								+  '")>'
	 								+ response.body[i].nodeSlNo
	 								+' - '
	 								+ response.body[i].categoryName
	 								+'<span class="mrg-lft"><i class="fa fa-plus" onclick=openNav("'+response.body[i].categoryId+'","'+response.body[i].projectId+'","'+response.body[i].startDate+'","'+response.body[i].endDate+'","2");></i></span><span><i class="fa fa-edit" onclick=editSubCategory("'+response.body[i].categoryId+'","'+response.body[i].parentId+'","'+response.body[i].projectId+'","'+response.body[i].executionId+'")></i></span>'
	 								+ '</td>'
	 								+'<td class="firstnode">'+result+'</td>'
	 								+'<td class="firstnode">'+response.body[i].preced+'</td>'
	 								+'<td class="firstnode">'+response.body[i].startDate+'</td>'
	 								+'<td class="firstnode">'+response.body[i].endDate+'</td>'
	 								+'<td class="firstnode">'+response.body[i].assignedTo+'</td>'
	 								+'<td class="firstnode">'+response.body[i].maintype+'</td>'
	 								+'<td class="firstnode">'+response.body[i].qtyNeeded+'</td>'
	 								+'<td class="firstnode">'+response.body[i].plannedHrs+'</td>'
	 								+'<td class="firstnode">'+response.body[i].actualHrs+'</td>'
	 								+'<td class="firstnode">'+response.body[i].requiDate+'</td>'
	 								+'<td class="firstnode">'+response.body[i].needDate+'</td>'
	 								+'<td class="firstnode"></td>'
	 								+'<td class="firstnode">'+response.body[i].reqid+'</td>'
	 								+'<td class="firstnode">'+response.body[i].notes+'</td>'
	 								+'<td class="firstnode" align="center"><span class="mrg-lft"><i class="fa fa-tasks" onclick=CategoryDetails("'+response.body[i].executionId+'","'+response.body[i].projectId+'","'+response.body[i].categoryId+'");></i></span></td></tr>';

	 							}
	 						}
	 						$("#tbodyData12").append(row);

	 					}
	        		/* }
	        		 else{
	        			 getFromPlanning(exeId);
	        		 }*/

					var pcat = $("#prCategoryId").val();
					$(".benefitChk").prop("checked", false);
					$("#ccCheck_" + pcat).prop("checked", true);

					$('.loader').hide();
					$("body").removeClass("overlay");

					$('#basic').simpleTreeTable({
						expander : $('#expander'),
						collapser : $('#collapser'),
						store : 'session',
						storeKey : 'simple-tree-table-basic'
					});
				} else {
					console.log("ellllllllllsssssssssseeeeeeeeeeeeeeee  "+exeId)
					swal({
						title : response.code,
						text : response.message,
						type : "warning"
					})
				}
	        }, error: function(data) {
	        	console.log(data)
	        	$('.loader').hide();
	        	$("body").removeClass("overlay");
	        }
		});
	}
	function getFromPlanning(pid){
			$.ajax({
				type : "GET",
				url : "project-execution-getFromPlanning?id=" + pid,
				async : false,
				success : function(response) {
					if (response.message == "Success") {
							getProjectExecutionDetails(pid);
						
					}
				},
				error : function(data) {
				}

			});
		
	}
	var sDate= "";
	var eDate= "";
	function getDate1(k){
		//var k=$("#preced").val();
		$.ajax({
			type : "GET",
			url : "project-execution-category-get-dates?id="+ k,
			dataType : "json",
			contentType : "application/json",
			success : function(response) {
				if (response.message == "success") {
					console.log(response)
					var sd =convertDateFormat(response.body[0].name);
					var d1 = new Date(sd);
					d1.setDate(d1.getDate() + 1);
					var day = d1.getDate();
					var month = d1.getMonth() + 1; // Months are zero-based
					var year = d1.getFullYear();
					day = (day < 10) ? '0' + day : day;
					 month = (month < 10) ? '0' + month : month;

					 d1= day +"-"+ month +"-" + year;
					startdate= $("#startDate").val(d1);
					}
				}
			});
		
		
	}
	function openNav(categoryId,pd,sd,ed,sl) {

			var pName = $("#lbl_"+categoryId).text();
			$("#subCatParentSpan").text(" "+pName);
			var pID = categoryId;
			sDate = sd;
			eDate = ed;
			$("#subCatParentSpanId").val(pID);
			$("#projectId").val(pd)
			$("#slnoval").val(sl);
			$("#phase").val("");
		     $("#startDate").val("");
             $("#endDate").val("");
		     $("#assignedTo").val("");
		     $("#preced").val("");
			$("#maintype").val("");
			$("#qtyNeeded").val("");	
		     $("#plannedHrs").val("");
			 $("#actualHrs").val("");
             $("#requiDate").val("");
			 $("#needDate").val("");
               $("#fileAttach").empty("");
               $("#assign").val("");
			 $("#reqid").val("");
 			$("#notes").val("");
 			 $("#uploadedBillDiv_0").empty("");
             $("#fileAttach").empty("");
             $("#uploadHidden_0").empty("");
	             $("#uploadDoc_0").empty("");
			           
		document.getElementById("mySidenav").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("mainId").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
	}
	function closeNav() {
		$("#subCatParentSpanId").val("");
			$("#projectId").val("")
		$("#phase").val("");
		     $("#startDate").val("");
             $("#endDate").val("");
		     $("#assignedTo").val("");
			$("#maintype").val("");
			$("#qtyNeeded").val("");	
		     $("#plannedHrs").val("");
			 $("#actualHrs").val("");
             $("#requiDate").val("");
			 $("#needDate").val("");
              $("#fileAttach").val("");
			 $("#reqid").val("");
 			$("#notes").val("");
 			 $("#uploadedBillDiv_0").empty("");
             $("#imageName_0").empty("");
             $("#uploadHidden_0").empty("");
	             $("#uploadDoc_0").empty("");
             
		
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("mainId").style.width = "100%";
		$("#addId").show();
		$("#cancelId").show();
		$("#addNotiId").show();
		$("#cancelNotiId").show();
	}
	
	function saveExecutionPlan(){
		var datas =[];
			var event = {};
		
			event.executionId = $("#executionId").val();
			event.parentId = $("#subCatParentSpanId").val();
			event.categoryId = $("#subCatId").val();
			event.projectId = $("#projectId").val();
			event.preced = $("#preced").val();
			event.phase = $("#phase").val();
			event.startDate = $("#startDate").val();
			event.endDate = $("#endDate").val();
			event.assignedTo = $("#assignedTo").val();
			event.maintype = $("#maintype").val();
			event.qtyNeeded = $("#qtyNeeded").val();
			event.plannedHrs = $("#plannedHrs").val();
			event.actualHrs = $("#actualHrs").val();
			event.requiDate = $("#requiDate").val();
			event.needDate = $("#needDate").val();
			event.reqid = $("#reqid").val();
			event.notes = $("#notes").val();
			datas.push(event);
			console.log("object on add budgetAll-----------" + JSON.stringify(datas));

				 $.ajax({
						type : "POST",
						url : "project-execution-add",
						dataType : "json",
						contentType : "application/json",
						data : JSON.stringify(datas),

						success : function(response) {
							if (response.message == "Success") {
								closeNav();
								//$('.loader').hide();
							
								$("#messageParagraph").text("Data Saved Successfully");
								$("#msgOkModal").removeClass("btn3");
								$("#msgOkModal").addClass("btn1");
								$("#msgModal").modal('show');
							
								var idds = response.body[0].projectId
								getProjectExecutionDetails(idds);
								
							}
						},
						error : function(response) {
							console.log(response);

						}
					}); 
	}
	function newPlan(){
		
		$("#projectIdMain").text(id2);
		$("#phaseMain").val("");
		     $("#startDateMain").val("");
             $("#endDateMain").val("");
		     $("#assignedToMain").val("");
			$("#maintypeMain").val("");
			$("#qtyNeededmain").val("");	
		     $("#plannedHrsMain").val("");
			 $("#actualHrsMain").val("");
             $("#requiDateMain").val("");
			 $("#needDateMain").val("");
               $("#fileAttachMain").val("");
			 $("#reqidMain").val("");
 			$("#notesMain").val("");

			 $("#uploadedBillDiv_0").empty("");
             $("#imageName_0").empty("");
            $("#uploadHidden_0").empty("");
            $("#uploadDoc_0").empty("");
		
		document.getElementById("mySidenavAll").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("mainId").style.width = "75%";
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
	}
	function closeNavAll() {
		$("#projectIdMain").text("");
		$("#phaseMain").val("");
		     $("#startDateMain").val("");
             $("#endDateMain").val("");
		     $("#assignedToMain").val("");
			$("#maintypeMain").val("");
			$("#qtyNeededmain").val("");	
		     $("#plannedHrsMain").val("");
			 $("#actualHrsMain").val("");
             $("#requiDateMain").val("");
			 $("#needDateMain").val("");
			 $("#reqidMain").val("");
 			$("#notesMain").val("");

			 $("#uploadedBillDiv_0").empty("");
             $("#imageName_0").empty("");
            $("#uploadHidden_0").empty("");
            $("#uploadDoc_0").empty("");
		document.getElementById("mySidenavAll").style.width = "0";
		document.getElementById("mainId").style.width = "100%";
		$("#addId").show();
		$("#cancelId").show();
		$("#addNotiId").show();
		$("#cancelNotiId").show();
	}
	
	function saveExecutionPlanAll(){
		var datas =[];
		var event = {};
	
		event.executionId = $("#executionIdMain").val();
		event.categoryId = $("#CatIdMain").val();
		event.projectId = $("#projectIdMain").text();
		event.phase = $("#phaseMain").val();
		event.startDate = $("#startDateMain").val();
		event.endDate = $("#endDateMain").val();
		event.assignedTo = $("#assignedToMain").val();
		event.maintype = $("#maintypeMain").val();
		event.qtyNeeded = $("#qtyNeededMain").val();
		event.plannedHrs = $("#plannedHrsMain").val();
		event.actualHrs = $("#actualHrsMain").val();
		event.requiDate = $("#requiDateMain").val();
		event.needDate = $("#needDateMain").val();
		event.reqid = $("#reqidMain").val();
		event.notes = $("#notesMain").val();
		datas.push(event);
		console.log("object on add budget-----------" + JSON.stringify(datas));

			 $.ajax({
					type : "POST",
					url : "project-execution-parent-add",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(datas),

					success : function(response) {
						if (response.message == "Success") {
							closeNavAll();
							//$('.loader').hide();
						
							$("#messageParagraph").text("Data Saved Successfully");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
						
							var iddsmain = response.body[0].projectId
							getProjectExecutionDetails(iddsmain);

						}

					},
					error : function(response) {
						console.log(response);

					}
					
				}); 
	}
	function editCategory(id,exeId) {
		
		closeNav();
		newPlan();
		$("#CatIdMain").val(id);
		$("#executionIdMain").val(exeId);
		var obj = {};
		var ctId = id;  
		obj.categoryId = ctId;
		obj.projectId = $("#projectIdMain").text();
		
			$.ajax({
		        type: "POST",
		        url: "project-execution-category-dtls-by-id",
		        dataType: "json",
		        contentType: "application/json",
		        data : JSON.stringify(obj),
		        success: function(response) {
		    		console.log(JSON.stringify(obj));

		            if (response.message == "Success") {
		            	console.log(response)
		            	$("#phaseMain").val(response.body.categoryName);
				        $("#startDateMain").val(response.body.startDate);
                        $("#endDateMain").val(response.body.endDate);
				        $("#assignedToMain").val(response.body.assignedTo);
				        $("#ATnameMain").val(response.body.assignedTo);
				        $("#maintypeMain").val(response.body.maintype);
				        $("#qtyNeededmain").val(response.body.qtyNeeded);	
		            	$("#plannedHrsMain").val(response.body.plannedHrs);
				        $("#actualHrsMain").val(response.body.actualHrs);
                        $("#requiDateMain").val(response.body.requiDate);
				        $("#needDateMain").val(response.body.needDate);
				        $("#preced").val(response.body.preced);
				        $("#reqidMain").val(response.body.reqid);
 						$("#notesMain").val(response.body.notes);
 						var fileName=response.body.fileAttach;
 			        	
 						if (fileName != null) {
							var ext = fileName.split(".");
	 
				           if (ext[1] == "jpg" || ext[1] == "png") {
								var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
									+ fileName + '")></i></a> </div>';
							}else if (ext[1] == "pdf") {
								var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
									+ fileName + '")></i></a> </div>';
							} else {
								var LightImg = "<div class='uploadicon position-l'> </div>";
							}  
				           console.log("parent"+LightImg);
							$("#uploadedBillDiv_0Main").html(LightImg);
						}
						fileName="";
		            }
		        }, error : function(data) {
		        	console.log(response)
		        	$("#phaseMain").val("");
				        $("#startDateMain").val("");
                        $("#endDateMain").val("");
				        $("#assignedToMain").val("");
				        $("#maintypeMain").val("");
				        $("#qtyNeededmain").val("");	
		            	$("#plannedHrsMain").val("");
				        $("#actualHrsMain").val("");
                        $("#requiDateMain").val("");
				        $("#needDateMain").val("");
                        $("#fileAttach").val("");
				        $("#reqidMain").val("");
 						$("#notesMain").val("");
 						 $("#uploadedBillDiv_0").empty("");
 			             $("#imageName_0").empty("");
 			            $("#uploadHidden_0").empty("");
			             $("#uploadDoc_0").empty("");
		        }
			});
		
	}
	function editSubCategory(id,pId,proId,exeId) {
	
		closeNavAll();
		openNav(pId,"2");
		$("#subCatId").val(id);
		$("#projectId").val(proId);
		$("#executionId").val(exeId);
		var obj = {};
		var ctId = id;  
		obj.categoryId = ctId;
		obj.projectId = $("#projectId").val();
		console.log(JSON.stringify(obj));
			$.ajax({
		        type: "POST",
		        url: "project-execution-get-category-dtls-by-id",
		        dataType: "json",
		        contentType: "application/json",
		         data : JSON.stringify(obj),
		        success: function(response) {
		        	
		              if (response.message == "Success") {
		            	  console.log(response)
		            	$("#phase").val(response.body.categoryName);
				        $("#startDate").val(response.body.startDate);
                        $("#endDate").val(response.body.endDate);
				        $("#assignedTo").val(response.body.assignedTo);
				        $("#ATname").val(response.body.assignedTo);
				        $("#maintype").val(response.body.maintype);
				        $("#qtyNeeded").val(response.body.qtyNeeded);	
		            	$("#plannedHrs").val(response.body.plannedHrs);
				        $("#actualHrs").val(response.body.actualHrs);
                        $("#requiDate").val(response.body.requiDate);
				        $("#needDate").val(response.body.needDate);
				        $("#preced").val(response.body.preced);
				        $("#reqid").val(response.body.reqid);
 						$("#notes").val(response.body.notes);
 						var fileName=response.body.fileAttach;
						if (fileName != null) {
							var ext = fileName.split(".");
							$("#imageName_0").html(fileName);
	 
				           if (ext[1] == "jpg" || ext[1] == "png") {
								var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
									+ fileName + '")></i></a> </div>';
							}else if (ext[1] == "pdf") {
								var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
									+ fileName + '")></i></a> </div>';
							} else {
								var LightImg = "<div class='uploadicon position-l'> </div>";
							}  
				           console.log("child"+LightImg);
							$("#uploadedBillDiv_0").html(LightImg);
						}
						fileName= "";
		            }
		        }, error : function(data) {
		        	console.log(data)
		        	$("#phase").val("");
				        $("#startDate").val("");
                        $("#endDate").val("");
				        $("#assignedTo").val("");
				        $("#maintype").val("");
				        $("#qtyNeeded").val("");	
		            	$("#plannedHrs").val("");
				        $("#actualHrs").val("");
                        $("#requiDate").val("");
				        $("#needDate").val("");
                       $("#fileAttach").val("");
				        $("#reqid").val("");
 						$("#notes").val("");
 			             $("#uploadHidden_0").empty("");
 			             $("#uploadDoc_0").empty("");
 			            $("#uploadedBillDiv_0").empty("");
 			             $("#imageName_0").empty("");
 					
		        }
			});
	}
	function saveFileMain(){

		var uFile = $(uploadDoc_0Main)[0].files[0];
		var fileName = event.currentTarget.value;
		var lastIndex = fileName.lastIndexOf("\\");
		if (lastIndex >= 0) {
			fileName = fileName.substring(lastIndex + 1);
		}
		var extension = fileName.split(".");
		var iURL = URL.createObjectURL(uFile);
		$("#uploadedBillDiv_0Main").html("");

		if (extension[1] == "jpg" || extension[1] == "png"|| extension[1] == "jpeg"|| extension[1] == "JPG") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
		}else if (extension[1] == "pdf") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
		} else {
			var LightImg = "<div class='uploadicon position-l'> </div>";
		}
		$("#uploadedBillDiv_0Main").html(LightImg);
		$("#fileAttach").html(fileName);
		var fileData = new FormData();
		fileData.append('file', uFile);
		fileData.append('path', 'none');
		console.log("aaaaaaaaa",fileData);

		$.ajax({
			type : "POST",
			url : "project-execution-upload-file",
			enctype : "multipart/form-data",
			contentType : false,
			data : fileData,
			processData : false,
			cache : false,
			success : function(response) {

			},
			error : function(e) {

			}
		});
		fileName="";
		
	}
	function saveFile() {

		var uFile = $(uploadDoc_0)[0].files[0];
		var fileName = event.currentTarget.value;
		var lastIndex = fileName.lastIndexOf("\\");
		if (lastIndex >= 0) {
			fileName = fileName.substring(lastIndex + 1);
		}
		var extension = fileName.split(".");
		var iURL = URL.createObjectURL(uFile);
		$("#uploadedBillDiv_0").html("");

		if (extension[1] == "jpg" || extension[1] == "png"|| extension[1] == "jpeg"|| extension[1] == "JPG") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
		}else if (extension[1] == "pdf") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
		} else {
			var LightImg = "<div class='uploadicon position-l'> </div>";
		}
		$("#uploadedBillDiv_0").html(LightImg);
		$("#fileAttach").html(fileName);
		var fileData = new FormData();
		fileData.append('file', uFile);
		fileData.append('path', 'none');
		console.log("aaaaaaaaa",fileData);

		$.ajax({
			type : "POST",
			url : "project-execution-upload-file",
			enctype : "multipart/form-data",
			contentType : false,
			data : fileData,
			processData : false,
			cache : false,
			success : function(response) {

			},
			error : function(e) {

			}
		});
		fileName="";
	}
	function viewImage(id){
		window.open(id,'_blank');
	}
	var tree={};
	function CategoryDetails(id,proId,catId){
		
		var obj = {};
		var execute = id;  
		
		
		obj.categoryId = catId;
		obj.projectId = proId;
		$("#table").hide();
		$("#basic1").hide();
		$("#basic2").show();
						
						agGrid.simpleHttpRequest({
							url : "project-execution-view-task?id=" + execute,
						}).then(function(data) {
							if(data.body.length!=0){
								$("#myGrid").show();
								console.log("in iff   "+JSON.stringify(data));
								gridOptions3.api.setRowData(data.body);
								gridOptions1.api.setRowData(data.body);
								$("#taskDets").val(data.body[0].categoryName);
								console.log(data.body[0].executionId+" ### "+data.body[0].categoryName)
				            	$("#taskDets").html(data.body[0].categoryName);
								 tree.executionId = data.body[0].executionId;
								 tree.categoryId = data.body[0].categoryId;
							} else{
								$.ajax({
							        type: "POST",
							        url: "project-execution-category-dtls-by-id",
							        dataType: "json",
							        contentType: "application/json",
							        data : JSON.stringify(obj),
							        success: function(response) {
							            if (response.message == "Success"){

							        		$("#myGrid").show();
							            	var datas=[];
											datas.push(response.body);
											console.log("in else "+JSON.stringify(datas));
											console.log(datas[0].executionId+" ### "+datas[0].categoryName)
							            	$("#taskDets").html(datas[0].categoryName);
											 tree.executionId = datas[0].executionId;
											 tree.categoryId = datas[0].categoryId;
									      
										}
							        }
									});
							       
					            }
								
						  });
			
			
	}
	function CancelTaskDetail(){
		$("#table").show();
		$("#basic1").show();
		$("#basic2").hide();
		$("#myGrid1").hide();
		$("#myGrid").hide();
	}
	var psd="";
	var ped="";
	function newTaskDetail(){
		document.getElementById("mySidenav2").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
		document.getElementById("myGrid1").style.width = "75%"; 
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
		$("#asdate").val("");
		$("#aedate").val("");
		$("#actual").val("");
		$("#qtyrecieve").val("");
		$("#note").val("");
		$("#uploadedBillDiv_0Detail").empty("");
		$("#uploadHidden_0Detail").empty("");
		 $("#imageName_0Detail").empty("");
		
		$.ajax({
	        type: "POST",
	        url: "project-execution-taskdetail",
	        dataType: "json",
	        contentType: "application/json",
	        data : JSON.stringify(tree),
	        success: function(response) {
	            if (response.message == "Success"){
	            	console.log(response);
	            	var datas=[];
					var event={};
					$("#catIds").val(response.body.categoryId);
					$("#projectId2").val(response.body.projectId);
					$("#phasetask").val(response.body.categoryName);
					psd = response.body.startDate;
					ped = response.body.endDate;
					$("#psdate").val(response.body.startDate);
					$("#pedate").val(response.body.endDate);
					$("#type").val(response.body.maintype);
					$("#assignTask").val(response.body.assignedTo);
					$("#qtyneed").val(response.body.qtyNeeded);
					$("#planned").val(response.body.plannedHrs);
					
	            }
	        }
		  });
	}
	function closeNav3(){
		document.getElementById("mySidenav2").style.width = "0%";
		document.getElementById("myGrid1").style.width = "100%";
		$("#addNotiId").show();
		$("#cancelNotiId").show();
		$("#addId").show();
		$("#cancelId").show();
	}
	function saveTableData(){
		var id = tree.executionId;
		closeNav3();
		var datas =[];
		var event = {};
		event.slNo = $("slNo").val();
		event.executionId = tree.executionId;
		event.categoryId = $("#catIds").val();
		event.projectId = $("#projectId").text();
		event.phase = $("#phasetask").val();
		event.startDate = $("#psdate").val();
		event.endDate = $("#pedate").val();
		event.assignedTo = $("#assignTask").val();
		event.maintype = $("#type").val();
		event.qtyNeeded = $("#qtyneed").val();
		event.plannedHrs = $("#planned").val();
		event.actualStartDate = $("#asdate").val();
		event.actualEndDate = $("#aedate").val();
		event.actualHrs = $("#actual").val();
		event.qtyRecieved = $("#qtyrecieve").val();
		event.notes = $("#note").val();
		
		event.feedBack = $("#feedBack").val();
		
		datas.push(event);
		console.log("object on add task detail-----------" + JSON.stringify(datas));

			 $.ajax({
					type : "POST",
					url : "project-execution-task-detail-add",
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(datas),

					success : function(response) {
						if (response.message == "Success") {
							closeNavAll();
							//$('.loader').hide();
						
							$("#messageParagraph").text("Data Saved Successfully");
							$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');
							
							//$("#myGrid1").show();
							$("#myGrid").show();
							agGrid.simpleHttpRequest({
							url : "project-execution-view-task?id=" + id,
							}).then(function(data) {
							console.log(data)
							gridOptions3.api.setRowData(data);
							
							
						});
						}

					},
					error : function(response) {
						console.log(response);

					}
					
				}); 
	}
	function saveFileDetail(){

		var uFile = $(uploadDoc_0Detail)[0].files[0];
		var fileName = event.currentTarget.value;
		var lastIndex = fileName.lastIndexOf("\\");
		if (lastIndex >= 0) {
			fileName = fileName.substring(lastIndex + 1);
		}
		var extension = fileName.split(".");
		var iURL = URL.createObjectURL(uFile);
		$("#uploadedBillDiv_0Detail").html("");

		if (extension[1] == "jpg" || extension[1] == "png"|| extension[1] == "jpeg"|| extension[1] == "JPG") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o' style='color: blue'></i></a></div>";
		}else if (extension[1] == "pdf") {
			var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o' style='color: red'></i></a></div>";
		} else {
			var LightImg = "<div class='uploadicon position-l'> </div>";
		}
		$("#uploadedBillDiv_0Detail").html(LightImg);
		$("#fileAttach").html(fileName);
		var fileData = new FormData();
		fileData.append('file', uFile);
		fileData.append('path', 'none');
		console.log("aaaaaaaaa",fileData);

		$.ajax({
			type : "POST",
			url : "project-execution-upload-file",
			enctype : "multipart/form-data",
			contentType : false,
			data : fileData,
			processData : false,
			cache : false,
			success : function(response) {

			},
			error : function(e) {

			}
		});
		fileName="";
		
	}
	function editId(slNo){
		
		$("#addNotiId").hide();
		$("#cancelNotiId").hide();
		$("#addId").hide();
		$("#cancelId").hide();
		$.ajax({
			type : "GET",
					url : "project-execution-edit-task?id=" + slNo,
					async : false,
					success : function(response) {
						console.log("response for edit cus------"
								+ JSON.stringify(response));
						if (response.message == "Success") {
							document.getElementById("mySidenav2").style.cssText = "width:25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:109px;";
							document.getElementById("myGrid1").style.width = "75%";
							event.categoryId = id;
							event.projectId = id2;
							$("#phasetask").val(response.body.categoryName);
							$("#psdate").val(response.body.startDate);
							$("#pedate").val(response.body.endDate);
							$("#type").val(response.body.maintype);
							$("#assignTask").val(response.body.assignedTo);
							$("#qtyneed").val(response.body.qtyNeeded);
							$("#planned").val(response.body.plannedHrs);
							$("#asdate").val(response.body.actualStartDate);
							$("#aedate").val(response.body.actualEndDate);
							$("#actual").val(response.body.actualHrs);
							$("#qtyrecieve").val(response.body.qtyRecieved);
							$("#note").val(response.body.notes);
							$('#slNo').val(response.body.slNo);
							$('#feedBack').val(response.body.feedBack);
							var fileName=response.body.fileAttach;
							console.log(fileName);
							if (fileName != null) {
								var ext = fileName.split(".");
		 
					           if (ext[1] == "jpg" || ext[1] == "png") {
									var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
										+ fileName + '")></i></a> </div>';
								}else if (ext[1] == "pdf") {
									var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
										+ fileName + '")></i></a> </div>';
								} else {
									var LightImg = "<div class='uploadicon position-l'> </div>";
								}  

								$("#uploadedBillDiv_0Detail").html(LightImg);
							}
						}
					}
				})
	}