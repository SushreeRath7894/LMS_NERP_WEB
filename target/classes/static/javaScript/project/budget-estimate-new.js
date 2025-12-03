$(document).ready(function() {


	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1)

	gridOptions1.api.setRowData();


	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();
	agGrid.simpleHttpRequest({
		url: "view-budget-estimate-view-project",
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewProjectData;
		var len = allData.length;
		$('#totalCandidate').find('span').html(len);
		gridOptions1.api.setRowData(allData);
		var firstRowNode = gridOptions1.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}

	});

});

var columnDefs1 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,

	},
	{
		headerName: "Project ID",
		field: "projectId",
		type: 'leftAligned',


	}, {
		headerName: "Project Name",
		field: "projectName",
		type: 'leftAligned',
		width: 250,

	}, {
		headerName: "Creation Date",
		field: "creationDate",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Location',
		field: "location",
		type: 'leftAligned',
		width: 230,
	}, {
		headerName: 'Country',
		field: "country2",
		type: 'leftAligned',
		width: 130,
		hide: true
	}, {
		headerName: "State",
		field: "stateid2",
		type: 'leftAligned',
		width: 150,
		hide: true
	}, {
		headerName: 'Pin',
		field: "pPin",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Project Incharge',
		field: "pIncharge",
		type: 'leftAligned',
		width: 250,
	}, {
		headerName: 'Billing Name',
		field: "cName",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Address',
		field: "cAddress",
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Country',
		field: "country",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing State',
		field: "stateid",
		width: 150,
		type: 'leftAligned',
		hide: true
	}, {
		headerName: 'Billing Pin',
		field: "cPin",
		width: 150,
		hide: true,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Email',
		field: "email",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Billing Mobile',
		field: "mobile",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'Remarks',
		field: "remark",
		width: 150,
		type: 'leftAligned',
		hide: true

	}, {
		headerName: 'status',
		field: "status",
		width: 150,
		type: 'leftAligned',

	}];



var gridOptions1 = {
	columnDefs: columnDefs1,
	//rowData : rowData1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChanged
};


var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Activity",
		field: "budgetCategory",
		type: 'leftAligned',

	}, {
		headerName: "Activity ID",
		field: "budName",
		type: 'leftAligned',

		width: 150,
		cellRenderer: function(params) {
			if (params.data.budgetId == null || params.data.budgetId == undefined) {
				return '';
			} else {
				return '<a>'
					+ params.data.budgetId + '</a>&nbsp;<a class="plus-task" onclick=openTaskSection("' + params.data.budgetCategoryId + '","' + params.data.slNo + '","' + params.data.projectId + '")><i class="fa fa-plus-circle" aria-hidden="true"></i></a>';
			}

		},


	}, {
		headerName: "Task",
		field: "item",
		width: 150,
		type: 'leftAligned',
	}, {
		headerName: 'Unit',
		field: "unit",
		type: 'leftAligned',
		//valueFormatter : currencyFormatter,
		width: 130
	}, {
		headerName: "Quantity",
		field: "qty",
		width: 150,
		type: 'leftAligned',
	}, {
		headerName: "Rate",
		field: "rate",
		type: 'rightAligned',
	}, {
		headerName: "Duration",
		field: "nos",
		width: 150,
		type: 'leftAligned',
	}, {
		headerName: 'Mob/Demob',
		field: "mob",
		type: 'leftAligned',
		width: 130,
		hide: true
	}, {
		headerName: 'Extra Expenses',
		field: "actual",
		width: 150,
		type: 'rightAligned',

	}, {
		headerName: "Amount",
		field: "amount",
		width: 150,
		type: 'rightAligned',
	}, {
		headerName: 'Projected Expences',
		field: "projected",
		width: 150,
		type: 'rightAligned',

	},];


var gridOptions = {
	columnDefs: columnDefs,
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: rowSelect,
};


var id = '';
var pname = '';
function onSelectionChanged() {
	var selected = gridOptions1.api.getSelectedRows();
	var rowCount = 0;
	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});


	for (var i = 0; i < selected.length; i++) {
		id = id + selected[i].projectId;
		pname = pname + selected[i].projectName;
	}

	$("#projectId1").val(id);
	$("#projectIdName").text(pname);


	if (rowCount > 0) {
		callBudgetData(id);
		$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', true);

	} else {
		gridOptions.api.setRowData();
		$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', true);
		addId();
	}

	id = '';
	pname = '';
}
function callBudgetData(bIdd) {
	agGrid.simpleHttpRequest({
		url: "view-budget-estimate-view?id=" + bIdd
	}).then(function(data) {

		var jsonData = JSON.parse(data.body[0]);
		console.log('budget', jsonData);
		var allData = jsonData.viewBudgetData;

		if (jsonData.viewBudgetData === null) {
			console.log("Budget is null");
			let myArray = Array.isArray(jsonData) ? jsonData : [];
			if (myArray == null || myArray.length === 0) {
				$('.loader').hide();
				$("body").removeClass("overlay");
				gridOptions.api.setRowData();
			}
		} else {
			var len = allData.length;
			$('#totalCandidateBudget').find('span').html(len);
			console.log('DATA', allData)
			/* allData = allData.sort((e1, e2) =>
	  e1.budgetCategoryId.toLowerCase().localeCompare(e2.budgetCategoryId.toLowerCase())
	); */
			allData = allData.sort((a, b) => {
				// e1.budgetCategoryId.toLowerCase().localeCompare(e2.budgetCategoryId.toLowerCase())
				if (a.catSlNo < b.catSlNo) return -1;
				if (a.catSlNo > b.catSlNo) return 1;

				if (a.slNo < b.slNo) return -1;
				if (a.slNo > b.slNo) return 1;

				return 0;
			});
			let arr = [];
			let dataset = [];
			allData.forEach(e => {
				if (!arr.includes(e.budgetCategory)) {
					arr.push(e.budgetCategory);
					let obj = {};
					obj.budgetCategory = e.budgetCategory;
					obj.amount = e.totalamount;

					dataset.push(obj);
				}
				e.budgetCategory = '';
				dataset.push(e);
			});
			dataset.push(new Object({ "budgetCategory": "Total", "amount": allData[0].budgetTotalAmnt }))
			console.log('Budget Data', dataset)


			gridOptions.api.setRowData(dataset);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}



	});
}


var deleteId = "";
var budId = "";
function rowSelect() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		var selectedNodes = gridOptions.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		budId = selectedData.map(node => node.budgetId);
		editId(budId);
		$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', true);
		$('#delete').attr('disabled', false);


	} else {
		$('#delete').attr('disabled', true);
		$("#addId").attr('disabled', false);
		addId()
		$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', true);
	}

	budId = '';
}



function subCat() {

	var budgetCategory = $("#budgetCategory").val();

	if (budgetCategory) {
		$.ajax({
			type: "GET",
			url: "view-budget-estimate-SubCategoryList?id=" + budgetCategory,
			success: function(response) {

				if (response.message == "success") {
					$("#item").empty();

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


//calculation
function calculateTotal() {
	var qty = parseFloat($("#qty").val()) || 0;
	var rate = parseFloat($("#rate").val()) || 0;
	var extra = parseFloat($("#actual").val()) || 0;
	var nos = parseFloat($("#nos").val()) || 1;
	var crop = parseFloat($("#cropAmnt").val()) || 1;
	//alert(qty +" "+rate+" "+extra+" "+nos+" "+crop)
	if (isNaN(extra) || isNaN(nos) || isNaN(crop)) {
		$("#actual").val('0');
		$("#nos").val('1');
		extra = 0;
		nos = 1;
		crop = 1;
	}

	var totalAmt = qty * rate * nos * crop + extra;

	$("#amount").val(totalAmt.toFixed(2));
	$("#projected").val(totalAmt.toFixed(2));
}

function saveTableData() {
	var event = {};
	var validation = true;
	event.projectId = $("#projectId1").val();

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
	event.cropAmnt = $("#cropAmnt").val();
	event.parentSlNo = $("#parentSlNo").val();


	if (event.item == null || event.item == "") {
		validation = validationUpdated("Task Required", 'item');
	}

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-budget-estimate-add",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(event),

			success: function(response) {
				if (response.message == "success") {

					var prjdata = $("#projectId1").val();
					callBudgetData(prjdata);
					addId();
					$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', true);



				}

			},
			error: function(response) {

			}

		});
	}
}
function editId(idd) {
	var options = '<option value="">Select</option>';

	var prjId = $("#projectId1").val();
	agGrid.simpleHttpRequest(
		{
			//url : 'view-budget-estimate-edit?id='+ idd 
			url: "view-budget-estimate-edit?id=" + idd + "&id2=" + prjId,
		}).then(function(data) {
			var jsonData = JSON.parse(data.body[0]);
			var allData = jsonData.editBudgetData;
			// $('#projectId1').val(allData[0].projectId1);
			$('#budgetId').val(allData[0].budgetId);
			$('#budgetCategory').val(allData[0].budgetCategory);
			$('#item').val(allData[0].item);
			getstate(allData[0].budgetCategory, allData[0].item);
			$('#unit').val(allData[0].unit);
			$('#qty').val(allData[0].qty);
			$('#rate').val(allData[0].rate);
			$('#nos').val(allData[0].nos);
			$('#mob').val(allData[0].mob);
			$('#amount').val(allData[0].amount);
			$('#projected').val(allData[0].projected);
			$('#actual').val(allData[0].actual);
			$('#expenseId').val(allData[0].expenseId);
			$('#cropAmnt').val(allData[0].cropAmnt);

		});
}
function getstate(budgetCategory, item) {
	if (budgetCategory) {
		$.ajax({
			type: "GET",
			url: "view-budget-estimate-SubCategoryList?id=" + budgetCategory,
			dataType: 'json',
			contentType: 'application/json',
			data: budgetCategory,
			success: function(response) {
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
			error: function(data) {
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


function deleteModalfun() {
	var selectedRows = gridOptions.api.getSelectedRows();
	deleteId = "";
	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = selectedRows[i].budgetId;
	}

	$.ajax({
		type: "GET",
		url: "view-budget-estimate-delete?id=" + deleteId,
		success: function(response) {
			if (response.code == "Success") {
				var prjdata = $("#projectId1").val();
				callBudgetData(prjdata);
				$('#delete').attr('disabled', true);
				addId()

			}
		}

	});

	$('#delete').attr("disabled", true);
}

function addId() {
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
	$("#expenseId").val("");
}

function enableFields1() {
	$('#budgetId,#budgetCategory,#item,#unit,#qty,#rate,#nos,#mob,#amount,#projected,#actual,#expenseId').prop('disabled', false);

}
function openTaskSection(actId, slNo, projectId) {
	enableFields1();
	$("#budgetId").val("");
	$('#budgetCategory').val(actId);
	$("#parentSlNo").val(slNo);
	$('#item').val("");
	$('#unit').val("");
	$('#qty').val("");
	$('#rate').val("");
	$('#nos').val("");
	$('#mob').val("");
	$('#amount').val("");
	$('#projected').val("");
	$('#actual').val("");
	$("#expenseId").val("");
	$("#cropAmnt").val("");
	subCat();
}


function onQuickFilterChanged() {
	gridOptions1.api.setQuickFilter(document.getElementById('quickFilter').value);
	let len = gridOptions.api.getDisplayedRowCount();
	let firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	

}


function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
