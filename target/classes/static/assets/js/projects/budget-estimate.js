$(document).ready(function() {
		
		 
			var gridDiv = document.querySelector('#myGrid');
			new agGrid.Grid(gridDiv, gridOptions);

			var gridDiv1 = document.querySelector('#myGrid1');
			new agGrid.Grid(gridDiv1, gridOptions1)

			var gridDiv2 = document.querySelector('#myGrid2');
			new agGrid.Grid(gridDiv2, gridOptionsNew)
			
			gridOptions1.api.setRowData();
			gridOptions.api.setRowData();
			gridOptionsNew.api.setRowData();
	
			$("#delete").show();
			$('#delete').attr('disabled', true);
			 agGrid.simpleHttpRequest({
				url : "view-budget-estimate-view-project",
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.viewProjectData;
				gridOptions1.api.setRowData(allData);
				var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
				if (firstRowNode) {
					console.log('firstRowNodessss', firstRowNode)
					firstRowNode.setSelected(true); // Set the row as selected
				}
				
			}) ;
			 
	});
 
		var columnDefs1 = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	},{
		headerName : "Project Id",
		field : "projectId",
		type : 'leftAligned',
	}, {
			headerName : "Project Name",
			field : "projectName",
			type : 'leftAligned',
		}, {
			headerName : "Creation Date",
			field : "creationDate",
			width : 150,
			type : 'leftAligned',
		}, {
			headerName : 'Location',
			field : "location",
			type : 'leftAligned',
			width : 130
		}, {
			headerName : 'Country',
			field : "country2",
			type : 'leftAligned',
			width : 130
		}, {	
			headerName : "State",
			field : "stateid2",
			type : 'leftAligned',
			width : 150
		}, {
			headerName : 'Pin',
			field : "pPin",
			type : 'leftAligned',
		}, {
			headerName : 'Project Incharge',
			field : "pIncharge",
			type : 'leftAligned',
		}, {
			headerName : 'Billing Name',
			field : "cName",
			type : 'leftAligned',
		}, {
			headerName : 'Billing Address',
			field : "cAddress",
			hide:true,
			type : 'leftAligned',
		}, {
			headerName : 'Billing Country',
			field : "country",
			width : 150,
			type : 'leftAligned',
		}, {
			headerName : 'Billing State',
			field : "stateid",
			width : 150,
			type : 'leftAligned',
		}, {
			headerName : 'Billing Pin',
			field : "cPin",
			width : 150,
			hide : true,
			type : 'leftAligned',

		}, {
			headerName : 'Billing Email',
			field : "email",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'Billing Mobile',
			field : "mobile",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'Remarks',
			field : "remark",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'status',
			field : "status",
			width : 150,
			type : 'leftAligned',

		} ];

	var columnDefs = [
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
				headerName : "Budget ID",
				field : "budgetId",
				type : 'leftAligned',

				width : 150,
					cellRenderer : function(params) {
					return '<a instrno="editId" onclick=editId("'
							+ params.data.budgetId
							+ '") href="javascript:void(0)">'
							+ params.data.budgetId + '</a>';
				}, 
				
			}, {
				headerName : "Budget Category",
				field : "budgetCategory",
				type : 'leftAligned',
			}, {
				headerName : "Items",
				field : "item",
				width : 150,
				type : 'leftAligned',
			}, {
				headerName : 'Units',
				field : "unit",
				type : 'leftAligned',
				//valueFormatter : currencyFormatter,
				width : 130
			}, {
				headerName : "Quantity",
				field : "qty",
				width : 150,
				type : 'leftAligned',
			}, {
				headerName : "Rate",
				field : "rate",
				type : 'rightAligned',
			}, {
				headerName : "Duration",
				field : "nos",
				width : 150,
				type : 'leftAligned',
			}, {
				headerName : 'Mob/Demob',
				field : "mob",
				type : 'leftAligned',
				width : 130
			}, {
				headerName : 'Extra Expenses',
				field : "actual",
				width : 150,
				type : 'rightAligned',

			}, {
				headerName : "Amount",
				field : "amount",
				width : 150,
				type : 'rightAligned',
			}, {
				headerName : 'Projected Expences',
				field : "projected",
				width : 150,
				type : 'rightAligned',

			}, ];


	var gridOptions = {
		columnDefs : columnDefs,
	//	rowData : rowData,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 20
		},
		rowSelection : 'single',
		onSelectionChanged : rowSelect,
	};

	var gridOptions1 = {
		columnDefs : columnDefs1,
		//rowData : rowData1,
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

	var columnDefsNew = [ 
		{
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true
		},{
		headerName : "Budget Category",
		field : "budgetCategory",
		width : 200,
	}, {
		headerName : "Budget Category Id",
		field : "budgetCategoryId",
		hide : true,
		width : 200,
	}, {
		headerName : 'Budget Sub-Category',
		field : "item",
		width : 200
	}, {
		headerName : "Budget Sub-Category Id",
		field : "itemId",
		hide : true,
		width : 200,
	}
	];

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
			//onSelectionChanged : onSelectionChangedNew
		};
	function onQuickFilterChanged() {
		gridOptions1.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	function onQuickFilterChanged1() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter1').value);
	}
	function cancelR() {
		$('#deleteid').modal('hide');
		$("#delete").show();
	}
function addId() {

		document.getElementById("mySidenav").style.cssText = "width: 30%; position: absolute; right:-20px; overflow: hidden; height:auto; top:15px;";
		document.getElementById("main").style.width = "70%";
		$("#newId").hide();
		$("#budgetId").val("");
		$('#budgetCategory').val("");
		$('#item').val("");
		$('#unit').val("");
		$('#qty').val("");
		$('#rate').val("");
		$('#nos').val("");
		$('#mob').val("");
		$('#amount').val("");
		$('#projected').val("");
		$('#actual').val("");
		$("#searchRowDiv").show();
		$("#expenseId").val("");

	}
	
function cancel() {
	document.getElementById("mySidenav").style.width = "0%";
	document.getElementById("main").style.width = "100%";
	$("#newId").show();
	$("#delete").show();
	$("#budgetId").val("");
	$('#budgetCategory').val("");
	$('#item').val("");
	$('#unit').val("");
	$('#qty').val("");
	$('#rate').val("");
	$('#nos').val("");
	$('#mob').val("");
	$('#amount').val("");
	$('#projected').val("");
	$('#actual').val("");
	$("#searchRowDiv").show();
	$("#expenseId").val("");
	$('#delete').attr('disabled', true);
	$("#addId").attr('disabled', false);
	
}

function subCat() {
	
	var budgetCategory = $("#budgetCategory").val();
	
	if (budgetCategory) {
		$.ajax({
					type : "GET",
					url : "view-budget-estimate-SubCategoryList?id="+ budgetCategory,
					success : function(response) {
						
						if (response.message == "success") {
							$("#item").empty();
							/*$("#item").append(
							"<option value=''>Select</option>");*/
							var option = $("<option></option>");
							$(option).val(null);
							$(option).html("Select");
							$("#item").append(option);
							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option).val(response.body[i].key);
								$(option).html(response.body[i].name);
								$("#item").append(option);
							}
						}
					}
				})
	}
};

function extraExpenses(){
	$("#extraExpenseModal").modal('show');
	$("#extraExpense").val('');
}
function cancelExtraExpenses(){
$("#extraExpenseModal").modal('hide');
}
//
function saveExtraExpenses() {
	var budgetCategory = $("#budgetCategory").val();

	var obj = {};
	obj.expenseId = $("#expenseId").val();
	obj.budgetCategory = budgetCategory;
	obj.item =  $("#item").val();
	obj.extraExpense = $("#extraExpense").val();
	var validation = true;

	if (obj.extraExpense == null || obj.extraExpense == "") {
		validation = validationUpdated("Extra Expense Required", "extraExpense");
	}
	
	if (validation) {
		$(".formValidation").remove();
		$.ajax({
			type : "POST",
			url : "view-budget-estimate-extra-expense-add",
			contentType : "application/json",
			data : JSON.stringify(obj),
			success : function(response) {
				if (response.message == "Success") {
					$("#messageParagraph").text(
							"Payment Term Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#extraExpenseModal').modal('hide');
					getExpenseList();
				}
				if (response.code == '1062') {
					$("#messageParagraph").text("This expenses is already exists.");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#brandModal').modal('hide');
					getExpenseList();
				}
				
			},
			error : function(data) {
			}
		})
	}
}

function getExpenseList() {
	
    var item2 = $("#item").val();
    var options='<option value="">Select</option>';
    if (item2) {
        $.ajax({
            type: "POST",
            url: "view-budget-estimate-getExpenseList",
            dataType: 'json',
            contentType: 'application/json',
            data: item2,
            success: function(response) {
            	 console.log("Response data:", response);
                if (response.message == "success") {
                	$("#expenseId").empty();
                	var jsonData = JSON.parse(response.body);
					var allData=jsonData.expensedata;
					 allData.forEach(function(rowNode){
						options += '<option value="'+rowNode.expenseId+'">'+rowNode.extraExpense+'</option>';
						  });
					 $("#expenseId").html(options);
                }
            },
            error: function(response) {
                console.log(response);
                $("#expenseId").empty();
                $("#expenseId").append("<option value=''>Select</option>");
            }
        })
    } else {
        $("#expenseId").empty();
        $("#expenseId").append("<option value=''>Select</option>");
    }
}
//calculation
function calculateTotal() {
    var qty = parseFloat($("#qty").val()) || 0;
    var rate = parseFloat($("#rate").val()) || 0;
    var extra = parseFloat($("#actual").val()) || 0;

    // Check if extra is null or an empty string
    if (isNaN(extra)) {
        $("#actual").val('0');
        extra = 0;
    }

    var totalAmt = qty * rate + extra;
    
    $("#amount").val(totalAmt);
	$("#projected").val(totalAmt);
}

function onSelectionChanged() {
	var selected = gridOptions1.api.getSelectedRows();
	var rowCount = 0;
	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
		id = selected[0].projectId;
		$("#projectId1").text(id);

	if (rowCount > 0) {
		$('#delete').attr('disabled', true);
	} else {
		$('#delete').attr('disabled', true);
	}
	agGrid.simpleHttpRequest({
		url : "view-budget-estimate-view?id="+id
		}).then(function(data) {
			
			var jsonData = JSON.parse(data.body[0]);
			console.log('budget',jsonData);
			var allData = jsonData.viewBudgetData;
			
			if (jsonData.viewBudgetData === null) {
				  console.log("Budget is null");
				  let myArray = Array.isArray(jsonData) ? jsonData : [];
				  if(myArray == null || myArray.length === 0){
						$('.loader').hide();
						$("body").removeClass("overlay");
						gridOptions.api.setRowData();
					}
				} else {
					var len = allData.length;
					gridOptions.api.setRowData(allData);
					$('.loader').hide();
					$("body").removeClass("overlay");
				}
			
				
			
		});
	
}

function saveTableData() {
	var event = {};
	event.projectId = $("#projectId1").text();
	
	event.budgetId = $("#budgetId").val();
	event.budgetCategory = $("#budgetCategory").val();
	event.item = $("#item").val();
	event.unit = $("#unit").val();
	event.qty = $("#qty").val();
	event.rate = $("#rate").val();
	event.nos = $("#nos").val();
	event.mob = $("#mob").val();
	event.amount = $("#amount").val();
	event.projected = $("#projected").val();
	event.actual = $("#actual").val();
	event.expenseId = $("#expenseId").val();
	console.log("object on add budget-----------" + JSON.stringify(event));
 
		 $.ajax({
				type : "POST",
				url : "view-budget-estimate-add",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(event),

				success : function(response) {
					if (response.message == "success") {
						
						$("#messageParagraph").text("Data Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						
						cancel();
						var prjdata = $("#projectId1").text();
						agGrid.simpleHttpRequest({
							url : "view-budget-estimate-view?id="+prjdata
							}).then(function(data) {
								var jsonData = JSON.parse(data.body[0]);
								console.log('budget',jsonData);
								var allData = jsonData.viewBudgetData;
								
								if (jsonData.viewBudgetData === null) {
									  console.log("Budget is null");
									  let myArray = Array.isArray(jsonData) ? jsonData : [];
									  if(myArray == null || myArray.length === 0){
											$('.loader').hide();
											$("body").removeClass("overlay");
											gridOptions.api.setRowData();
										}
									} else {
										var len = allData.length;
										gridOptions.api.setRowData(allData);
										$('.loader').hide();
										$("body").removeClass("overlay");
									}
							});

						
					
					}

				},
				error : function(response) {
					console.log(response);

				}
			
			}); 

		 

}
function editId(idd) {
	var options='<option value="">Select</option>';
	
    addId();
	getExpenseList();
	agGrid.simpleHttpRequest(
			{
				url : 'view-budget-estimate-edit?id='+ idd
			}).then(function(data) {
				var jsonData = JSON.parse(data.body[0]);
				var allData=jsonData.editBudgetData;
				console.log("vvv",jsonData)
				console.log(allData);
				// $('#projectId1').val(allData[0].projectId1);
				$('#budgetId').val(allData[0].budgetId);
				$('#budgetCategory').val(allData[0].budgetCategory);
				$('#item').val(allData[0].item);
				getstate(allData[0].budgetCategory,allData[0].item);
				$('#unit').val(allData[0].unit);
				$('#qty').val(allData[0].qty);
				$('#rate').val(allData[0].rate);
				$('#nos').val(allData[0].nos);
				$('#mob').val(allData[0].mob);
				$('#amount').val(allData[0].amount);
				$('#projected').val(allData[0].projected);
				$('#actual').val(allData[0].actual);
				$('#expenseId').val(allData[0].expenseId);
				
				
				getExtraExpense(allData[0].item,allData[0].expenseId);
				
			});
}
function getstate(budgetCategory, item) {
	if (budgetCategory) {
		$.ajax({
					type : "GET",
					url : "view-budget-estimate-SubCategoryList?id="+ budgetCategory,
					dataType : 'json',
					contentType : 'application/json',
					data : budgetCategory,
					success : function(response)
					{
						if (response.message == "success") {
							$("#item").empty();
							$("#item").append(
									"<option value=''>Select</option>");

							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option).val(response.body[i].key);
								$(option).html(response.body[i].name);
								$("#item").append(option);
							}
							$("#item").val(item);
						}
					},
					error : function(data) {
						console.log(data);
						$("#item").empty();
						$("#item").append(
								"<option value=''>Select</option>");
					}
				})
	} else {
		$("#item").empty();
		$("#item").append("<option value=''>Select</option>");
	}

}

function getExtraExpense(item2, exp) {
	    var options='<option value="">Select</option>';
	    if (item2) {
	        $.ajax({
	            type: "POST",
	            url: "view-budget-estimate-getExpenseList",
	            dataType: 'json',
	            contentType: 'application/json',
	            data: item2,
	            success: function(response) {
	            	 console.log("Response data:", response);
	                if (response.message == "success") {
	                	//$("#expenseId").empty();
	                	var jsonData = JSON.parse(response.body);
						var allData=jsonData.expensedata;
						 allData.forEach(function(rowNode){
							options += '<option value="'+rowNode.expenseId+'">'+rowNode.extraExpense+'</option>';
							  });
						
						 $('#expenseId').html(options).promise().done(function() {
							  $("#expenseId").val(exp);
							});	 
						
	                }
	            },
	            error: function(response) {
	                console.log(response);
	                $("#expenseId").empty();
	                $("#expenseId").append("<option value=''>Select</option>");
	            }
	        })
	    } else {
	        $("#expenseId").empty();
	        $("#expenseId").append("<option value=''>Select</option>");
	    }
	
	
		
	}
	
var deleteId = "";
function rowSelect() {

var selectedRows = gridOptions.api.getSelectedRows();
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
function deleteModalfun(){
	$("#deleteModal").modal('show');
}

function deleteFun(){
	var selectedRows = gridOptions.api.getSelectedRows();
	deleteId = "";
	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = selectedRows[i].budgetId;
	}
		console.log("delete ids--------------"+deleteId);

		$.ajax({
			type : "GET",
			url : "view-budget-estimate-delete?id=" + deleteId,
			success : function(response) {
				if (response.code == "Success") {
					cancel();
					var dataprj = $("#projectId1").text();
					agGrid.simpleHttpRequest({
						url : "view-budget-estimate-view?id="+dataprj
						}).then(function(data) {
							var jsonData = JSON.parse(data.body[0]);
							console.log('budget',jsonData);
							var allData = jsonData.viewBudgetData;
							
							if (jsonData.viewBudgetData === null) {
								  console.log("Budget is null");
								  let myArray = Array.isArray(jsonData) ? jsonData : [];
								  if(myArray == null || myArray.length === 0){
										$('.loader').hide();
										$("body").removeClass("overlay");
										gridOptions.api.setRowData();
									}
								} else {
									var len = allData.length;
									gridOptions.api.setRowData(allData);
									$('.loader').hide();
									$("body").removeClass("overlay");
								}
						});

				}
			}

		});

		$('#delete').attr("disabled", true);
	}
