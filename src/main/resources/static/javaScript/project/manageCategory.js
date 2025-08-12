var allData = '';
document.addEventListener('DOMContentLoaded', function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	getCategoryData();


	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1);

	var activityId = "categoryTab";
	activityTabs(activityId)




});

var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Category Id',
		width: 500,
		field: "categoryId",


	}, {
		headerName: 'Category Name',
		width: 800,
		field: "categoryName",
	},

];
var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChangeData
};


var columnDefs1 = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Sub Category Id',
		width: 500,
		field: "subcategoryId",


	}, {
		headerName: 'Sub Category Name',
		width: 800,
		field: "subcategoryName",
	},

];

var gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: rowSelect
};


function activityTabs(activityId) {
	if (activityId == "categoryTab") {
		$("#categoryTab").removeClass('categoryTab');
		$("#subCategoryTab").addClass('subCategoryTab');

	} else if (activityId == "subCategoryTab") {
		$("#subCategoryTab").removeClass('subCategoryTab');
		$("#categoryTab").addClass('categoryTab');
	}
}



var allDataa = "";
var id = "";
var id2 = "";
function onSelectionChangeData() {

	var selected = gridOptions.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	for (var i = 0; i < selected.length; i++) {
		id = id + selected[i].categoryId;
	}
	id2 = id;

	if (rowCount > 0) {
		$('#deleteCategory').attr('disabled', false);
		$('#newSubCategory').attr("disabled", false);
		$('#newCategory').attr("disabled", true);
		editCategory(id);
		$('#categoryId,#categoryName').attr('disabled', true);

		$('#subcategoryId,#subcategoryName').attr('disabled', false);
		$("#subcategoryId").val("");
		$("#subcategoryName").val("");

		getSubCategoryList(id);
	} else {
		$('#deleteCategory').attr('disabled', true);
		$('#newSubCategory').attr("disabled", true);
		$('#newCategory').attr("disabled", false);

		$('#categoryId,#categoryName').attr('disabled', false);
		$('#subcategoryId,#subcategoryName').attr('disabled', true);
		$("#categoryId").val("");
		$("#categoryName").val("");
		$("#subcategoryId").val("");
		$("#subcategoryName").val("");
		gridOptions1.api.setRowData();

	}

	id = "";

}


function getCategoryData() {
	agGrid.simpleHttpRequest({
		url: "manage-category-view"
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		allData = jsonData.viewCaategoryData;
		console.log(allData)
		var len = allData.length;
		$('#totalReqs').find('span').html(len);
		gridOptions.api.setRowData(allData);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}
	});
}


function editCategory(idd) {
	var catnames = allData.filter(f => f.categoryId === idd);
	var cId = catnames[0].categoryId;
	var cName = catnames[0].categoryName;

	$("#categoryId").val(cId);
	$("#categoryName").val(cName);

}

function enableFieldsCat() {
	$('#categoryId,#categoryName').attr('disabled', false);
}


function saveCategory() {
	obj = {};
	obj.categoryId = $("#categoryId").val();
	obj.categoryName = $("#categoryName").val();



	var validation = true;

	if (obj.categoryName == null || obj.categoryName == "") {
		validation = validationUpdated("Category Name Required",
			"categoryName");
	}

	if (validation) {
		$.ajax({
			type: "POST",
			url: "manage-category-save-category",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {

					getCategoryData();
					$('#categoryId,#categoryName').attr('disabled', true);
					$("#categoryId").val("");
					$("#categoryName").val("");
				}
			},
			error: function(data) {
			}
		})
	}
}


//FOR DELETE

function deleteCategory() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].categoryId;
	$.ajax({
		type: "POST",
		url: "manage-category-deleteCategory?id=" + id,
		success: function(response) {
			if (response.message == "Success") {
				getCategoryData();
				$('#categoryId,#categoryName').attr('disabled', true);
				$("#categoryId").val("");
				$("#categoryName").val("");


			} else {

			}
		},
		error: function(data) {
			console.log(data)
		}
	})

}
function getSubCategoryList(id) {
	$("#categoryIdspan").val(id);
	agGrid.simpleHttpRequest({
		url: "manage-category-view-subcategory?cdId=" + id,
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		allDataa = jsonData.viewsubCaategoryData;
		console.log(allDataa)
		if (allDataa == null) {
			$('#totalReqss').find('span').html(0);
			gridOptions1.api.setRowData();
		} else {
			var len = allDataa.length;
			$('#totalReqss').find('span').html(len);
			gridOptions1.api.setRowData(allDataa);
		}

	});

}
var sid = "";
function rowSelect() {

	var selected = gridOptions1.api.getSelectedRows();
	var rowCount = 0;

	selected.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	for (var i = 0; i < selected.length; i++) {
		sid = sid + selected[i].subcategoryId;
	}

	if (rowCount > 0) {
		$('#deleteSubCategory').attr('disabled', false);
		$('#newSubCategory').attr("disabled", true);
		editSubCategory(sid);

		$('#subcategoryId,#subcategoryName').attr('disabled', true);

	} else {
		$('#deleteSubCategory').attr('disabled', true);
		$('#newSubCategory').attr("disabled", false);
		$('#subcategoryId,#subcategoryName').attr('disabled', false);
		$("#subcategoryId").val("");
		$("#subcategoryName").val("");

	}

	sid = "";

}



function saveSubCategory() {
	obj = {};
	obj.categoryId = $("#categoryIdspan").val();
	obj.subcategoryId = $("#subcategoryId").val();
	obj.subcategoryName = $("#subcategoryName").val();



	var validation = true;

	if (obj.subcategoryName == null || obj.subcategoryName == "") {
		validation = validationUpdated("Sub Category Name Required",
			"subcategoryName");
	}

	console.log(obj);


	if (validation) {
		$.ajax({
			type: "POST",
			url: "manage-category-save-subcategory",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {

					var cdId = $("#categoryIdspan").val();

					getSubCategoryList(cdId);

					$("#subcategoryId").val("");
					$("#subcategoryName").val("");
					$('#subcategoryId,#subcategoryName').attr('disabled', true);

				}
			},
			error: function(data) {
			}

		})
	}
}

function editSubCategory(sIdd) {
	//$('#deleteSubCategory').attr("disabled", true);
	var catnames = allDataa.filter(f => f.subcategoryId === sIdd);
	var cId = catnames[0].subcategoryId;
	var cName = catnames[0].subcategoryName;

	$("#subcategoryId").val(cId);
	$("#subcategoryName").val(cName);

}

function enableFieldsSubCat() {

	$('#subcategoryId,#subcategoryName').attr('disabled', false);

}


function deleteSubCategory() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var sd = selectedRows[0].subcategoryId;
	$.ajax({
		type: "POST",
		url: "manage-category-deleteSubCategory?id=" + sd,
		success: function(response) {
			if (response.message == "Success") {
				var cdId = $("#categoryIdspan").val();
				getSubCategoryList(cdId);
				$('#subcategoryId,#subcategoryName').attr('disabled', false);
				$("#subcategoryId").val("");
				$("#subcategoryName").val("");

			} else {

			}
		},
		error: function(data) {
			console.log(data)
		}
	})
}


function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	let len = gridOptions.api.getDisplayedRowCount();
	let firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	

}

function onQuickFilterChanged1() {
	gridOptions1.api.setQuickFilter(document.getElementById('quickFilter1').value);
	let len = gridOptions1.api.getDisplayedRowCount();
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

function cancelBar1() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter1').val() == null || $('#quickFilter1').val() == "") {
		id.style.display = "none";
	}
}
