$(() => {
	$('#activityId').select2();  // #activityId
	$('#taskId').select2();  // #taskId
	$('#variantId').select2();  // #variantId
});

$(document).ready(()=>{
	$("#createProcessDiv").hide();
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var gridDiv1 = document.querySelector('#myGrid1');
	new agGrid.Grid(gridDiv1, gridOptions1);
	
	gridOptions.api.setRowData([]);
	gridOptions1.api.setRowData([]);
	
	getConfigData();
	
	$(".br-s-btn").hide();
})

function toggleSection() {
	$(".br-s-btn").show();
	$(".br-m-btn").hide();
	
	$("#createProcessDiv").show();
	$("#viewProcessDiv").hide();
}

function cancel() {
	$(".br-s-btn").hide();
	$(".br-m-btn").show();
	
	$("#cropProcessId").val('');
	$("#processDescription").val('');
	$("#activityId").val('');
	$("#taskId").empty();
	$("#taskId").append('<option value="">Select</option>');
	$("#variantId").empty();
	$("#variantId").append('<option value="">Select</option>');
	
	$("#createProcessDiv").hide();
	$("#viewProcessDiv").show();
}

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
		headerName: 'Config ID',
		width: 250,
		flex: 1,
		field: "cropId",
	}, {
		headerName: 'Config Name',
		width: 350,
		flex: 2,
		field: "cropName",
	}, {
		headerName: 'Status',
		width: 350,
		flex: 1,
		field: "cropStatus",
		cellRenderer: function(params) {
            if (params.data.cropStatus == "ACTIVE") {
                return '<div style="color:green">Active</div>';
            } else {
                return '<div>Inactive</div>';
            }
        }
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
	onSelectionChanged: onSelectionChangeData,
	paginationAutoPageSize: true,
	pagination: true,
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

	}, {
		headerName: 'Category',
		width: 200,
		field: "categoryName",
		flex:1,
	}, {
		headerName: 'Sub-Category',
		width: 200,
		field: "subCatName",
		flex:1,
	}, {
		headerName: 'Variant',
		width: 200,
		field: "variantName",
		flex:1,
	}, {
		headerName: 'Description',
		width: 200,
		field: "description",
		flex: 2,
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


var jsonDataa = "";
var id = "";
function onSelectionChangeData() {
	var selected = gridOptions.api.getSelectedRows();
	var rowCount = selected.length;

	$('#rowCountDisplay').text(rowCount);

	var id = "";

	for (var i = 0; i < selected.length; i++) {
		id += selected[i].cropId;
	}
	if (rowCount > 0) {
		$('#deleteCrop,#enableFieldsConfig').attr('disabled', false);
		$('#newCropProcess').attr("disabled", false);
		// $('#newCrop').attr("disabled", true);
		$('#cropId,#cropName,#cropDescription,#cropStatus').attr('disabled', true);
		$("#cropId").val(id);
		cropprocessView(id);
	} else {
		$('#deleteCrop,#enableFieldsConfig').attr('disabled', true);
		$('#newCropProcess').attr("disabled", true);
		$('#newCrop').attr("disabled", false);
		$('#cropId,#cropName,#cropDescription,#cropStatus').attr('disabled', false);
		$("#cropId").val("");
		$("#cropName").val("");
		$("#cropDescription").val("");
		$("#cropStatus").val("");
		gridOptions1.api.setRowData([]);
	}
	id = "";
}

function getConfigData() {
	agGrid.simpleHttpRequest({
		url: "project-configuration-view"
	}).then(function(data) {
		jsonData = JSON.parse(data.body);
		console.log(jsonData, 'json')
		var len = jsonData.length;
		$('#totalReqs').find('span').html(len);
		if(jsonData && jsonData.length > 0) {
			gridOptions.api.setRowData(jsonData);
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
			if (firstRowNode) {
				firstRowNode.setSelected(true); // Set the row as selected
			}
		} else {
			gridOptions.api.setRowData([]);
		}
	});
}


function cropprocessView(ccid) {
	agGrid.simpleHttpRequest({
		url: "project-configuration-process-view?id=" + ccid,
	}).then(function(data) {
		jsonDataa = JSON.parse(data.body);
		if (jsonDataa == null) {
			gridOptions1.api.setRowData([]);
		} else {
			jsonDataa = jsonDataa.sort((a, b) => {
			    if (a.slNo1 !== b.slNo1) {
			        return a.slNo1 - b.slNo1; 
			    }
			    return a.slNo2 - b.slNo2; 
			});
			let dataset = transformData(jsonDataa)
			gridOptions1.api.setRowData(dataset);
		}
	});
}

function transformData(data) {
  return data.map((item, index, arr) => {
    let prevItem = arr[index - 1];
    return {
      processId: item.processId,
      slNo1: item.slNo1,
      slNo2: item.slNo2,
      processId: item.processId,
      variantId: item.variantId,
      categoryId: item.categoryId,
      description: item.description,
      variantName: item.variantName,
      categoryName: prevItem && prevItem.slNo1 === item.slNo1 ? "" : item.categoryName,
      subCatId: item.subCatId,
      subCatName: prevItem && prevItem?.subCatId === item.subCatId ? "" : item.subCatName
    };
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
		sid = sid + selected[i].cropProcessId;
	}

	if (rowCount > 0) {
		$('.br-m-btn').attr('disabled', false);
	} else {
		$('.br-m-btn').attr('disabled', true);
	}
	sid = "";

}

function subCat() {

	var activityId = $("#activityId").val();
	
	$("#taskId").empty();
	var option = $("<option></option>");
	$(option).val('');
	$(option).html("Select");
	$("#taskId").append(option);
	
	$("#variantId").empty();
	var option = $("<option></option>");
	$(option).val('');
	$(option).html("Select");
	$("#variantId").append(option);

	if (activityId) {
		$.ajax({
			type: "GET",
			url: "project-configuration-SubCategoryList?id=" + activityId,
			success: function(response) {

				if (response.message == "success") {
					
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#taskId").append(option);
					}
				}
			}
		})
	}
};

function getVariant() {

	var taskId = $("#taskId").val();
	
	$("#variantId").empty();
	var option = $("<option></option>");
	$(option).val('');
	$(option).html("Select");
	$("#variantId").append(option);

	if (taskId) {
		$.ajax({
			type: "GET",
			url: "project-configuration-variantlist?id=" + taskId,
			success: function(response) {

				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#variantId").append(option);
					}
				}
			}
		})
	}
};


function saveCropProcess() {

	var validation = true;

	if ($("#activityId").val() == null || $("#activityId").val() == "") {
		toastr.error("Category required");
		return;
	}

	if ($("#taskId").val() == null || $("#taskId").val() == "") {
		toastr.error("Sub-Category required");
		return;
	}
	
	if ($("#variantId").val() == null || $("#variantId").val() == "") {
		toastr.error("Variant required");
		return;
	}

	obj = {};
	obj.cropId = $("#cropId").val();
	obj.cropProcessId = $("#cropProcessId").val();
	obj.activityId = $("#activityId").val();
	obj.taskId = $("#taskId").val();
	obj.variantId = $("#variantId").val();
	obj.cropProcessDescription = $("#processDescription").val();

	$('.loader').show();
	$("body").addClass("overlay");

	if (validation) {
		$.ajax({
			type: "POST",
			url: "project-configuration-save-CropProcess",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					toastr.success('Data saved successfully')
					cropprocessView(obj.cropId);
					cancel();
				} else {
					toastr.error(response.message)
				}
				
				$('.loader').hide();
	            $("body").removeClass("overlay");
			},
			error: function(data) {
				console.log(data)
				toastr.error('Something went wrong')
				
				$('.loader').hide();
	            $("body").removeClass("overlay");
			}
		})
	}
}
function enableFieldsSubConfig() {

	$('#cropProcessId,#activityId,#taskId,#qty,#cropProcessDescription,#amount,#duration,#unitId').attr('disabled', false);


}

function editCropProcess(sIdd) {
	console.log(jsonDataa);
	var catnames = jsonDataa.filter(f => f.cropProcessId === sIdd);
	var cId = catnames[0].cropProcessId;
	var cactivityId = catnames[0].activityId;
	var ctaskId = catnames[0].taskId;
	var cqty = catnames[0].qty;
	var ccropProcessDescription = catnames[0].cropProcessDescription;
	var camount = catnames[0].amount;
	var cduration = catnames[0].duration;
	var cunit = catnames[0].unit;
	$("#cropProcessId").val(cId);
	$("#activityId").val(cactivityId);
	$("#taskId").val(ctaskId);
	$("#qty").val(cqty);
	$("#cropProcessDescription").val(ccropProcessDescription);
	$("#amount").val(camount);
	$("#duration").val(cduration);
	$("#unitId").val(cunit);
	getSubTaskDetails(cactivityId, ctaskId);

	$('#activityId').attr("disabled", true);
	$('#taskId').attr("disabled", true);
	$('#deleteCropProcess').attr("disabled", true);


}

//FOR DELETE

function deleteCropProcess() {

	Swal.fire({
		title: 'Are you sure?',
	    text: 'Do you want to delete this?',
	    icon: 'warning',
	    showCancelButton: true,
	    confirmButtonText: 'Yes, delete it!',
	    cancelButtonText: 'No, keep it',
	    confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
	    if (result?.value) {
	        var selectedRows = gridOptions1.api.getSelectedRows();
			var sd = selectedRows[0].processId;
			$.ajax({
				type: "POST",
				url: "project-configuration-deleteCropProcess?id=" + sd,
				success: function(response) {
					if (response.message == "Success") {
					    toastr.success("Data deleted successfully");
						let cropId = $("#cropId").val();
						cropprocessView(cropId);
					} else {
						toastr.error(response.message);	
					}
				},
				error: function(data) {
					console.log(data)
					toastr.error('Something went wrong');
				}
			})
	    }
	})
	
}