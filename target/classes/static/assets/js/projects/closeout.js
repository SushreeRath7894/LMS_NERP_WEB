//Multiple Document Upload Ends

	$(document).ready(function() {

		$("#delete").prop("disabled", true);

		$("#cancelFridTrial").attr('disabled', true);
		$("#transportvalue").hide();

		$('#delete').attr('disabled', true);

		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);

		var today = new Date();
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		var yyyy = today.getFullYear();

		//today = dd + '-' + mm + '-' + yyyy;

		today = mm + '-' + dd + '-' + yyyy;

		$('#fromDate').val(today);
		$('#toDate').val(today);

		//gridOptionsNew.api.setRowData();

		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);

		var gridDivNew = document.querySelector('#myGridNew');
		new agGrid.Grid(gridDivNew, gridOptionsNew);

		var gridDiv4 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv4, gridOptions5);

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#cName').val($(this).val());
		})

		$('#cName').blur(function() {
			$("#DateCalendar").val($(this).val());
		})

		//     date format TO date

		var dateFormat = localStorage.getItem("dateFormat");
		$("#DateCalendar1").datetimepicker({
			//format : dateFormat,
			format : 'm-d-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#creationDate').val($(this).val());
		})

		$('#creationDate').blur(function() {
			$("#DateCalendar1").val($(this).val());
		})
		$("#demo").hide();
		$("#main").hide();
		$("#cancel").hide();

		//for viewProject

		agGrid.simpleHttpRequest({
			url : 'close-out-view'
		}).then(function(data) {
			var len = data.length;
			$('#totalReim').find('span').html(len);
			gridOptions.api.setRowData(data);
		});

		/* function getAllDataForProject(){
			var ProjectplanId=$("#ProjectplanId").val();
			agGrid.simpleHttpRequest({
				url : 'view-project-getdataOnSO?id=' + ProjectplanId
			
		});  */

		//for CRUD view.......
		agGrid.simpleHttpRequest({
			url : "close-out-view-lesson"
		}).then(function(data) {
			var len = data.length;
			$('#totalReq').find('span').html(len);
			gridOptions5.api.setRowData(data);
		});

	});

	var columnDefs5 = [
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
				headerName : "CloseOut ID",
				field : "closeOutId",
				//hide : true,
				width : 150,
				cellRenderer : function(params) {
					return '<a id="" onclick=editId("' + params.data.closeOutId
							+ '") href="javascript:void(0)">'
							+ params.data.closeOutId + '</a>';
				}
			}, {
				headerName : "WIN or Issue",
				field : "winorissue",
				width : 150,
				type : "leftAligned"
			}, {
				headerName : "Describe what happened",
				field : "description",
				width : 300,
				type : "leftAligned"
			}, {
				headerName : "What was the impact?",
				field : "impact",
				width : 310,
				type : "leftAligned"
			}, {
				headerName : "How Does This Change Future Projects?",
				field : "futureproject",
				width : 310,
				type : "leftAligned"
			}, {
				headerName : "Action Items",
				field : "action",
				width : 310,
				type : "leftAligned"
			} ];

	const rowData2 = [
			{
				activity : "ISSUE",
				planned : "Project was out sick for 2 weeks and there was no replacement,so we had to wait for her ",
				forecast : "The project was delayed 4 weeks and the client was upset.A $25000 credit was issued to the client",
				variance : "We need to have redundancy in the IT department to ensure there is always someone available",
				action : "Chat with CEO and HR about hiring additional IT help"
			},
			{
				activity : "WIN",
				planned : "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast : "This contract is gowing to double our revenue growth over the next 2 years",
				variance : "The new style for in-person client presentation should be used on more projects,when possible",
				action : "Share the new client presentation format with other teams"
			},
			{
				activity : "WIN",
				planned : "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast : "This contract is gowing to double our revenue growth over the next 2 years",
				variance : "The new style for in-person client presentation should be used on more projects,when possible",
				action : "Share the new client presentation format with other teams"
			} ];

	var gridOptions5 = {
		columnDefs : columnDefs5,
		//rowData: rowData2,
		rowSelection : 'single',
		onSelectionChanged : deleteDetails,
		groupSelectsChildren : true,
		suppressRowClickSelection : true,
		suppressAggFuncInHeader : true,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
	//rowSelection : 'single',
	onSelectionChanged : onSelectionChangedLessonLearned
	};

	var columnDefsNew = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "WIN or Issue",
		field : "winorissue",
		width : 150,
	}, {
		headerName : "Describe what happend",
		field : "description",
		width : 300,
		type : "rightAligned"
	}, {
		headerName : "What was the impact?",
		field : "impact",
		width : 310,
		type : "rightAligned"
	}, {
		headerName : "How Does This Change Future Projects?",
		field : "futureproject",
		width : 310,
		type : "rightAligned",
	}, {
		headerName : "Action Items",
		field : "action",
		width : 310,
		type : "rightAligned"
	} ];

	const rowdataNew = [
			{
				activity : "ISSUE",
				planned : "Project was out sick for 2 weeks and there was no replacement,so we had to wait for her ",
				forecast : "The project was delayed 4 weeks and the client was upset.A $25000 credit was issued to the client",
				variance : "We need to have redundancy in the IT department to ensure there is always someone available",
				action : "Chat with CEO and HR about hiring additional IT help"
			},
			{
				activity : "WIN",
				planned : "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast : "This contract is gowing to double our revenue growth over the next 2 years",
				variance : "The new style for in-person client presentation should be used on more projects,when possible",
				action : "Share the new client presentation format with other teams"
			},
			{
				activity : "WIN",
				planned : "The client was so happy with the final presentation that she offered us a 2 year exclusive contract!",
				forecast : "This contract is gowing to double our revenue growth over the next 2 years",
				variance : "The new style for in-person client presentation should be used on more projects,when possible",
				action : "Share the new client presentation format with other teams"
			} ];

	var gridOptionsNew = {
		columnDefs : columnDefsNew,
		//rowData : rowdataNew,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'multiple',
		onSelectionChanged : rowSelectNew
	};

	var columnDefs = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "Project ID",
		field : "projectId",

		width : 150,
		//onCellClicked: editId,
		/* cellRenderer : function(params){
		    return '<a (click)="editId()">1</a>'
		}, */
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Project Name",
		field : "pname",
	}, {
		headerName : 'Location',
		field : "location",
		type : 'leftAligned',
		//valueFormatter : currencyFormatter,
		width : 130

	}, {
		headerName : 'Customer',
		field : "customer",
	}, {
		headerName : 'Project Planned Date',
		field : "pPdate",
	}, {
		headerName : "Project Completion Date",
		field : "pCdate",
		width : 450
	} ];
	const rowData = [
			// Existing row data...

			{
				budgetId : "",
				budgetCategory : "Category 1",
				item : "Item 1",
				unit : "Unit 1",
				qty : 10,
				rate : 50,
				nos : 5,
				mob : "Mob 1",
				amount : 500,
				projected : 1000,
				actual : 800,
				createdBy : "John",
				createdOn : "2023-05-01",
				updatedBy : "Jane",
				updatedOn : "2023-05-10",
				projectID : "RFP001",
				projectName : "Iterarch Building Product PVT ltd",
				creationDate : "15-05-2023",
				location : "Panipat,Haryana",
				state : "Haryana",
				pPin : "132103",
				cName : "Customer 1",
				date : "01-05-2023",
				cAddress : "GRASIM INDUSTRIES LTD., 48-62, HSIIDC, INDUSTRIAL ESTATE REFINERY ROAD, PANIPAT",
				cState : "Haryana",
				cPin : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remark : "Some remarks",
				status : "Active",
			},

			{
				budgetId : "",
				budgetCategory : "Category 1",
				item : "Item 1",
				unit : "Unit 1",
				qty : 10,
				rate : 50,
				nos : 5,
				mob : "Mob 1",
				amount : 500,
				projected : 1000,
				actual : 800,
				createdBy : "John",
				createdOn : "2023-05-01",
				updatedBy : "Jane",
				updatedOn : "2023-05-10",
				projectID : "RFP002",
				projectName : "SMCC CONSTRUCTION INDIA LIMITED",
				creationDate : "15-05-2023",
				location : "Panipat,Haryana",
				state : "Haryana",
				pPin : "132103",
				cName : "Customer 2",
				date : "01-05-2023",
				cAddress : "PANIPAT REFINERY",
				cState : "Haryana",
				cPin : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remark : "Some remarks",
				status : "Active",
			},

			{
				budgetId : "",
				budgetCategory : "Category 1",
				item : "Item 1",
				unit : "Unit 1",
				qty : 10,
				rate : 50,
				nos : 5,
				mob : "Mob 1",
				amount : 500,
				projected : 1000,
				actual : 800,
				createdBy : "John",
				createdOn : "2023-05-01",
				updatedBy : "Jane",
				updatedOn : "2023-05-10",
				projectID : "RFP003",
				projectName : "Bhusan Power & Steel",
				creationDate : "15-05-2023",
				location : "BECHARAJI, GUJARAT",
				state : "GUJARAT",
				pPin : "382130",
				cName : "Customer 3",
				date : "01-05-2023",
				cAddress : "TDS LITHIUM-ION BATTERY GUJARAT PVT LTD.PLOT NO.1,2,3 & 9, BLOCK NO.334 & 335",
				cState : "GUJARAT",
				cPin : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remark : "Some remarks",
				status : "Active",
			},

			{
				budgetId : "",
				budgetCategory : "Category 1",
				item : "Item 1",
				unit : "Unit 1",
				qty : 10,
				rate : 50,
				nos : 5,
				mob : "Mob 1",
				amount : 500,
				projected : 1000,
				actual : 800,
				createdBy : "John",
				createdOn : "2023-05-01",
				updatedBy : "Jane",
				updatedOn : "2023-05-10",
				projectID : "RFP004",
				projectName : "Shree Cement",
				creationDate : "15-05-2023",
				location : "AURANGABAD, BIHAR",
				state : "BIHAR",
				pPin : "132103",
				cName : "Customer 4",
				date : "01-05-2023",
				cAddress : "Address 1",
				cState : "Customer State 1",
				cPin : "Customer Pin 1",
				email : "customer1@example.com",
				mobile : "1234567890",
				remark : "Some remarks",
				status : "Active",
			},
	// Add more data as needed
	];

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	var gridOptions = {
		columnDefs : columnDefs,
		//rowData: rowData,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'single',
		onSelectionChanged : rowSelect
	};

	$("#budgetId").click(function() {
		$("#a").css("display", "block");
		$("#b").css("display", "block");
	});

	function cancelbtn() {
		$('#reqDltBtn').attr('disabled', true);
		$("#myGrid").show();
		$("#btn1").hide();
		$("#demo").hide();
		$("#hideTbl").show();
		$("#buttonDetails").show();
	}
	var id = "";
	var id1 = "";

	function rowSelect() {
		//alert("anything");
		var selectedRows = gridOptions.api.getSelectedRows();
		var rowCount = 0;
		selectedRows.forEach(function(selectedRow, index) {
			rowCount = rowCount + 1;
		});

		for (var i = 0; i < selectedRows.length; i++) {
			id = id + selectedRows[i].projectId;
		}

		console.log("fffffffffffff", selectedRows[0].projectId);
		id = selectedRows[0].projectId;
		id1 = id;
		console.log(id1);

		if (rowCount > 0) {
			$('#delete').attr('disabled', false);
			//$("#reject").attr('disabled', false);
			//$('projectId').val(id);
			//$('projectId1').text(id);
		} else {
			$('#delete').attr('disabled', true);
			//$("#reject").attr('disabled', true);
			//$('projectId').val('');
			//$('projectId1').text('');
		}
	}

	function rowSelectNew() {
		var selected = gridOptionsNew.api.getSelectedRows();
		var rowCount = 0;
		selected.forEach(function(selectedRow, index) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#delete').attr('disabled', false);
		} else {
			$('#delete').attr('disabled', true);
		}

	}

	function deleteCustonClick() {
		var selectedRows = gridOptions.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += selectedRow.budgetId;
		});
		var item = {};
		item.budgetId = selectedRowsString;
		//alert(JSON.stringify(item));
		$.ajax({
			type : "POST",
			url : "close-out-delete",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(item),
			success : function(response) {
				if (response.message == "Success") {
					cancelModalBtn();
					location.reload();
					agGrid.simpleHttpRequest({
						url : "close-out-view"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});

				}
			}

		});
		$('#delete').attr("disabled", true);
	}

	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

	function cancel() {
		location.reload();
	}

	function saveTableData() {
		$("#myGrid").show();
		$("#addId").show();
		$("#searchRowDiv").show();
		var item = {};
		var data = 1;
		var rowEdit = $("#rowEdit").val();
		gridOptions.api.forEachNode(function(rowNode, index) {

			if (!rowEdit) {
				data = data + 1;
			}
		});

		item.projectName = $("#projectName").val();
		item.location = $("#location").val();
		item.pIncharge = $("#pIncharge").val();
		item.cName = $("#cName").val();
		item.creationDate = $("#creationDate").val();
		item.projectId = data;
		var datas = [];
		closeNav1();
		if (rowEdit) {
			var rowNode = gridOptions.api.getRowNode(rowEdit);
			rowNode.setData(item);

		} else {

			gridOptions.api.forEachNode(function(rowNode, index) {
				datas.push(rowNode.data);
			});

			datas.push(item)
			gridOptions.api.setRowData(datas);
		}
	}
	function newPage() {

		document.getElementById("mySidenav1").style.cssText = "width: 350px; position: absolute; right:-20px; overflow: hidden; height:auto; top:25px;";

		document.getElementById("upperline").style.width = "73%";

	}

	function cancel() {
		location.reload();
	}

	function openNav() {
		$("#projectId1").text(id);
		document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:220px;";
		document.getElementById("yeshh1").style.width = "75%";
		$("#cancel").hide();
		$('#addId').show();
		$("#save").show();
		$('#delete').hide();
		id="";

	}

	function closeNav() {
		$("#closeOutId").val("");
		$("#winorissue").val("");
		$("#description").val("");
		$("#impact").val("");
		$("#futureproject").val("");
		$("#action").val("");
		$('#delete').show();
		$('#add').show();
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("yeshh1").style.width = "100%";
	}

	function closeNav1() {

		document.getElementById("mySidenav1").style.width = "0";
		document.getElementById("upperline").style.width = "100%";
	}

	function savechild() {
		closeNav();
		var item = {};

		item.activity = $("#activity").val();
		item.planned = $("#location").val();
		item.forecast = $("#pIncharge").val();
		item.variance = $("#cName").val();
		item.action = $("#creationDate").val();

		//item.slnoId = data;
		//item.projectId = data;
		var datas = [];

		datas.push(item)
		gridOptions5.api.setRowData(datas);

	}

	/* 
	function savechild(){
		$("#activity").show();
		var item = {};
		var data=1;
		var rowEdit = $("#rowEdit").val();
		gridOptions5.api.forEachNode(function (rowNode, index) {
			
	        if (!rowEdit) {
	            data = data + 1;
	        }
	    });

		item.activity = $("#activity").val();
		item.planned = $("#location").val();
		item.forecast = $("#pIncharge").val();
		item.variance = $("#cName").val();
		item.action = $("#creationDate").val();
			var datas = [];
			closeNav();
			if (rowEdit) {
				var rowNode = gridOptions5.api.getRowNode(rowEdit);
				rowNode.setData(item);
				
			} else {

				gridOptions5.api.forEachNode(function(rowNode, index) {
					datas.push(rowNode.data);
				});

				datas.push(item)
				gridOptions5.api.setRowData(datas);
			}
			
			
	} */

	function search() {
		$('#deleteid').modal('show');
	}
	function cancelR() {
		$('#deleteid').modal('hide');
	}
	function copy() {

		const selectedRows1 = gridOptionsNew.api.getSelectedRows();

		console.log(selectedRows1);

		var datas = [];
		gridOptionsNew.api.forEachNode(function(rowNode, index) {
			datas.push(rowNode.data);

		});

		datas.push(selectedRows1)

		gridOptions5.api.setRowData(datas);

		cancelR();

	}

	//for dropdown list
	function getData() {
		var ProjectplanId = $("#projectDrpdwn").val();
		agGrid.simpleHttpRequest({
			url : 'close-out-view-project-getdataOnSO?id=' + ProjectplanId
		}).then(function(data) {
			console.log(data)
			gridOptionsNew.api.setRowData(data);
		});
	}

	//add
	function addDetails() {
		obj = {};
		var event = {};
		event.projectId = id1;
		id1 = "";

		obj.closeOutId = $("#closeOutId").val();
		obj.projectDrpdwn = $("#projectId1").html();
		obj.winorissue = $("#winorissue").val();
		obj.description = $("#description").val();
		obj.impact = $("#impact").val();
		obj.futureproject = $("#futureproject").val();
		obj.action = $("#action").val();
		console.log(obj);
		var validation = true;
		id = "";

		/*  if (obj.winorissue == null || obj.winorissue == "") {
		 validation = validationUpdated("WIN or ISSUE Required", "winorissue");
		 }
		 if (obj.fromDate == null || obj.fromDate == "") {
		 validation = validationUpdated("From Date Required", "fromDate");
		 }
		 if (obj.toDate == null || obj.toDate == "") {
		 validation = validationUpdated("To Date Required", "toDate");
		 }
		 if (obj.advanceReq == "1") {
		 validation = validationUpdated("Advance Amount Required",
		 "advanceAmount");
		 } 
		 */

		if (validation) {
			$.ajax({
				type : "POST",
				url : "close-out-add",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					console.log(response);
					if (response.message == "success") {
						$("#messageParagraph").text("Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						$("#add").show();
						$("#myGrid2").show();
						$("#cancel").hide();
						$("#save").hide();
						$("#form").hide();
						$("#new").hide();
						closeNav();

						//for add.....
						agGrid.simpleHttpRequest({
							url : "close-out-view-lesson"
						}).then(function(data) {
							var len = data.length;
							$('#totalReq').find('span').html(len);
							gridOptions5.api.setRowData(data);
						});

					}
				},
				error : function(data) {
				}
			})
		}
	}

	//crud-edit

	function editId(id) {
		$.ajax({
			type : "POST",
			url : "close-out-edit?Id=" + id,
			dataType : 'json',
			contentType : 'application/json',
			data : id,
			success : function(response) {
				if (response.message == "success") {

					console.log("edit", response.body)

					$("#closeOutId").val(response.body[0].closeOutId);
					$("#winorissue").val(response.body[0].winorissue);
					$("#description").val(response.body[0].description);
					$("#impact").val(response.body[0].impact);
					$("#futureproject").val(response.body[0].futureproject);
					$("#action").val(response.body[0].action);
					openNav();
				}
			},

			error : function(data) {
				console.log(data)
				$("#closeOutId").val("");
				$("#winorissue").val("");
				$("#description").val("");
				$("#impact").val("");
				$("#futureproject").val("");
				$("#action").val("");
			}
		});

	}

	//CRUD-Delete 

	var deleteId = "";
	function deleteDetails() {
		var selectedRows = gridOptions5.api.getSelectedRows();
		deleteId = "";
		for (var i = 0; i < selectedRows.length; i++) {
			deleteId = deleteId + '"' + selectedRows[i].closeOutId + '",';
		}
		deleteId = deleteId.substring(0, deleteId.length - 1);
		var rowCount = 0;
		selectedRows.forEach(function() {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').hide();
			if (rowCount == 1) {
				$('#add').hide();
				// Set the contractor ID in the span element
				$('#projectId1').text(id);
			}
		} else {
			$('#delete').attr("disabled", true);
			$('#add').show();
			// Clear the contractor ID from the span element
			$('#projectId1').text('');

		}

	}

	function deleteFun() {
		var selectedRows = gridOptions5.api.getSelectedRows();
		var id = selectedRows[0].closeOutId;

		$.ajax({
			type : "POST",
			url : "close-out-delete?id=" + id,
			success : function(response) {
				if (response.message == "Success") {
					agGrid.simpleHttpRequest({
						url : "close-out-view-lesson"
					}).then(function(data) {
						gridOptions5.api.setRowData(data);
					});

					closeNav();
				}
			},
			error : function(data) {
				console.log(data);
			}
		})
	}

	function save() {
		  var datas = [];
		  var obj = {};

		  // Check if there are any displayed rows in the grid
		  if (gridOptionsNew.api.getDisplayedRowCount() > 0) {
		    var selectedRowNode = null;

		    // Select the first row (or any specific row) from the grid
		    gridOptionsNew.api.forEachNode(function (rowNode, index) {
		      if (!selectedRowNode) {
		        // Assuming you want to select the first row found
		        selectedRowNode = rowNode;
		      }
		    });

		    // If a row is found, proceed with the rest of the logic
		    if (selectedRowNode) {
		      var obj = selectedRowNode.data;
		      obj.projectId = id;
		      datas.push(obj);
		      console.log(datas);
		    }
		  }

		  console.log(JSON.stringify(datas));
		  saveAllPackages(datas);

		  // Reload the page after saving the data
		  window.location.reload();
		}


	function saveAllPackages(datas) {
		console.log(datas, 'ggggg')
		$.ajax({
			type : "POST",
			url : "close-out-add-project",
			contentType : "application/json",
			data : JSON.stringify(datas),
			success : function(response) {
				if (response.message == "Success") {
					cancelR();

					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#msgModal").modal('show');
					agGrid.simpleHttpRequest({
						url : "close-out-project-view"
					}).then(function(data) {
						gridOptionsNew.api.setRowData(data);
					});
				}

			},
			error : function(datas) {
				console.log(datas)
			}
		})
	}

	function getDataa() {
		var projectDrpdwn = $("#projectDrpdwn").val();
		console.log(projectDrpdwn);
		if (projectDrpdwn) {
			agGrid.simpleHttpRequest({
				url : "close-out-project-view-data?id=" + projectDrpdwn,
			}).then(function(data) {
				gridOptionsNew.api.setRowData(data);
			});
		} else {
			alert("no data")
		}
	}
//
function onSelectionChangedLessonLearned(){
	var selectedRows = gridOptions5.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').attr('disabled', false);
		$("#addId").attr('disabled', true);
	} else {
		$('#delete').attr('disabled', true);
		$("#addId").attr('disabled', false);
	}
}