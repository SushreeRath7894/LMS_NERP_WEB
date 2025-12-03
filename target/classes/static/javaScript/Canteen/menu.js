
$(function() {

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	agGrid.simpleHttpRequest({
		url: "menu-throughAjax"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);

		gridOptions.api.setRowData(data);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
		if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}

	});
	$("#myGrid").show();
	$("#delete").attr("disabled", true);
});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})
//search bar

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

/*$('input,textarea').focus(
		function() {
			$(this).data('placeholder', $(this).attr('placeholder')).attr(
					'placeholder', '');
		}).blur(function() {
	$(this).attr('placeholder', $(this).data('placeholder'));
});*/

// column Defs
const columnDefs = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Item Id',
		field: "itemId",
		flex: 1,
	},

	{
		headerName: "Item Name",
		field: "itemName",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.itemName
				+ '</div>';
		}
	}, {
		headerName: "Price",
		field: "price",
		flex: 1,
		cellStyle: {
			textAlign: 'right'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.price
				+ '</div>';
		}
	}, {
		headerName: "Categry",
		field: "categry",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.categry
				+ '</div>';
		}
	}, {
		headerName: "Sub Categry",
		field: "subcategry",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.subcategry
				+ '</div>';
		}
	}, {
		headerName: "variant",
		field: "variant",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return '<div style="color:black;font-weight: bold;">'
				+ params.data.variant
				+ '</div>';
		}
	}, {
		headerName: "Active",
		field: "status",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.status == 1) {
				return '<div style="color:black;font-weight: bold;">Active</div>';
			} else {
				return '<div style="color:orange;font-weight: bold;">Inactive</div>';
			}
		}
	}

];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	//rowSelection : 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.itemId;
	}
};

/*function viewImage(id) {
	window.open("/document/shoukeen/" + id, '_blank');
}*/


var itemId = '';
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = 0;
	selectedData.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;

	});

	for (var i = 0; i < selectedRows.length; i++) {
		itemId = itemId + selectedRows[i].itemId;
		editPage(itemId);

	}
	itemId = '';

	if (rowCount > 0) {
		
	}else {
		$('#itemId').val("");
	$('#itemName').val("");
	$('#price').val("");
	$('#categry').val("");
	$('#subcategry').val("");
	$('#variant').val("");
	$('#status').val("");
	}


}
// for new button
function addBtn() {

	$('#itemId').val("");
	$('#itemName').val("");
	$('#price').val("");
	$('#categry').val("");
	$('#subcategry').val("");
	$('#variant').val("");
	$('#status').val("");

		gridOptions.api.deselectAll();
	

}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$("#demo").hide();

	$('#itemId').val("");
	$('#itemName').val("");
	$('#price').val("");
	$('#categry').val("");
	$('#subcategry').val("");
	$('#variant').val("");
	$('#status').val("");

	agGrid.simpleHttpRequest({
		url: "menu-throughAjax"
	}).then(function(data) {
		gridOptions.api.setRowData(data);
	});
}

$(document).ready(function() {
	$("#date").datetimepicker({
		format: "d-m-Y",
		closeOnDateSelect: true,
		minDate: new Date(),
		timepicker: false,
	});


});

// Edit & stage change 
function editPage(id) {


	$.ajax({
		type: "GET",
		url: "menu-edit?id=" + itemId,
		async: false,
		success: function(response) {
			console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {



				$("#itemId").text(response.body[0].itemId);
				$("#itemName").val(response.body[0].itemName);
				$("#price").val(response.body[0].price);
				$("#categry").val(response.body[0].categry);
				$("#subcategry").val(response.body[0].subcategry);
				$("#variant").val(response.body[0].variant);
				$("#status").val(response.body[0].status);

			}

		}
	})
}


function addIncentiveInfo() {

	var obj = {};

	obj.itemId = $('#itemId').val();
	obj.itemName = $('#itemName').val();
	obj.price = $('#price').val();
	obj.categry = $('#categry').val();
	obj.subcategry = $("#subcategry").val();
	obj.variant = $("#variant").val();
	obj.status = $('#status').val();

	console.log("object on add-----------" + JSON.stringify(obj));


	var validation = true;
	if (validation) {
		$.ajax({
			type: "POST",
			url: "menu-add-dtls",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					swal("Menu added successfully!", " ", "success");
					cancelBtn();
					agGrid.simpleHttpRequest({
						url: "menu-throughAjax"
					}).then(function(data) {
						gridOptions.api.setRowData(data);
					});


				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

}

function deleteIncentive() {
	$.ajax({
		type: "GET",
		url: "menu-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				swal("Menu deleted successfully!", " ", "success");
				agGrid.simpleHttpRequest({
					url: "menu-throughAjax"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});

			}
		}

	});

	$('#delete').attr("disabled", true);
}

function clubMemberGetDetail() {
	var clubMemberId = $('#clubmember').val();
	$.ajax({
		type: "GET",
		url: "menu-getMemberDetails?id=" + subcategry,
		async: false,
		success: function(response) {
			//console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {
				$("#rangefrom").val(response.body[0].memberRangeFrom);
				$("#rangeto").val(response.body[0].memberRangeTo);
			}

		}
	})
}

function getIncentiveStatus() {
	var categry = $('#categry').val();
	$.ajax({
		type: "GET",
		url: "menu-getIncentiveDetails?id=" + categry,
		async: false,
		success: function(response) {
			//console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {
				$("#incentivecode").val(response.body[0].itemId);
				//$("#rangeto").val(response.body[0].memberRangeTo);
			}

		}
	})
}

//country
function getStateList() {
	var cname = $('#categry').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "manage-customer-mstr-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#subcategry").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#subcategry").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#subcategry").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#subcategry").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#subcategry").append(option);
		$("#subcategry").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}

}

//state
function getDistDetails() {
	var cname = $('#subcategry').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "manage-customer-distList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#variant").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#variant").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#variant").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#variant").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#variant").append(option);
		$("#variant").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
	}
}