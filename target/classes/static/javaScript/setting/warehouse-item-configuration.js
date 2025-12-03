let type = '';
$(document).ready(function() {

    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    gridOptions.api.setRowData();
    
    const urlParams = new URLSearchParams(window.location.search);
	type = urlParams.get('id');
	
	if(type == null || type == 'null') {
		type = '';
	}
    
    getAllWarehouseDetails(type);

    var gridDiv = document.querySelector('#activity');
    new agGrid.Grid(gridDiv, activityOptions);
    activityOptions.api.setRowData();

    $("#itemAllocation").show();
    $('.collapse').on('show.bs.collapse', function() {
        $(this).siblings('.panel-heading').addClass('active');
    });

    $('.collapse').on('hide.bs.collapse', function() {
        $(this).siblings('.panel-heading').removeClass('active');
    });
	
	$("#edits").attr("disabled",true);
    $("#delete").attr("disabled",true);
    
    $('#sku').select2({
        placeholder: "Select",
        allowClear: true          
    });
    
});

function activityTabs(activityId) {
    if (activityId == "itemAllocation") {
        $("#itemAllocation").removeClass('hidden');
    }
}

function getAllWarehouseDetails(type) {

    agGrid.simpleHttpRequest({
        url: "warehouse-item-configuration-view-warehouse?type="+type
    }).then(function(data) {
        var jsonData = JSON.parse(data.body);
        var allData = jsonData.viewWarehouse;
        gridOptions.api.setRowData(allData);
        var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
        if (firstRowNode) {
            firstRowNode.setSelected(true);
        }

    });
}


var columnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: false,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true,
    },
    {
        headerName: 'Warehouse ID',
        field: "warehouseId",
        flex: 1,

    }, {
        headerName: 'Warehouse Name',
        field: "warehouseName",
        flex: 1,

    }, {
        headerName: 'Address',
        field: "address",
        flex: 3,
    }
];

// let the grid know which columns and what data to use
var gridOptions = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    groupSelectsChildren: true,
    suppressRowClickSelection: true,
    suppressAggFuncInHeader: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true
    },
    onSelectionChanged: rowSelect,
};

//function for row select parents
var whId = '';
var whName = '';

function rowSelect() {
    var selectedNodes = gridOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);

    if (selectedData && selectedData.length > 0) {
        whId = selectedData.map(node => node.warehouseId);
        whName = selectedData.map(node => node.warehouseName);
        getAllocationItemDetails(whId);
        $("#pId").text(whId)
        $("#pName").text(whName)
    } else {
        $("#pId").text('')
        $("#pName").text('')
    }



}

function getAllocationItemDetails(wh) {
    agGrid.simpleHttpRequest({
        url: "warehouse-item-configuration-view-allocate-item?whid=" + wh + "&type="+type,
    }).then(function(data) {
        var jsonData = JSON.parse(data.body);
        var allData = jsonData.allocate_item;
        activityOptions.api.setRowData(allData);
        if (allData != null && allData != 'null') {
            var firstRowNode = activityOptions.api.getDisplayedRowAtIndex(0);
            if (firstRowNode) {
                firstRowNode.setSelected(true);
            }
        } else {
            $('#sku').val('').trigger('change');
            $('#minQty').val('');
            $("#edit").val('');
        }

    });

}


// for activity table
var activityDefs = [{
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        sortable: false,
        filter: false,
        resizable: true,
        width: 30,
    },
    {
        headerName: "SKU/Material Code",
        field: "sku",
        width: "180",
        flex:1,
    }, {
        headerName: "Material Name",
        field: "itemName",
        width: 400,
        flex:3,
    }, {
        headerName: "Min Quantity",
        field: "minQty",
        width: "180",
        flex:1,
        cellStyle: {
            textAlign: 'right'
        },
    }
];


// let the grid know which columns and what data to use product table
var activityOptions = {
    columnDefs: activityDefs,
    rowSelection: 'single',
    groupSelectsChildren: true,
    suppressRowClickSelection: true,
    suppressAggFuncInHeader: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 150
    },
    onSelectionChanged: rowSelectdata,
};


function rowSelectdata() {
    var selectedNodes = activityOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
    var rowCount = 0;
    selectedData.forEach(function(selectedNodes, index) {
        rowCount = rowCount + 1;
    });
    if (rowCount > 0) {
        var sku1 = selectedData.map(node => node.sku);
        var minQty1 = selectedData.map(node => node.minQty);
        disableFields();
        $("#sku").val(sku1).trigger('change');
        $("#minQty").val(minQty1);
        $("#edit").val('Yes');
        $("#edits").attr("disabled",false);
        $("#delete").attr("disabled",false);
    } else {
        $('#sku').val('').trigger('change');
        $('#minQty').val('');
        $("#edit").val('');
        $("#edits").attr("disabled",true);
        $("#delete").attr("disabled",true);
    }

}

function resetData() {
	if(!$("#edit").val()) {
		$('#sku').val('').trigger('change');
	    $('#minQty').val('');
	    $("#edit").val('');
	}
}

//master save
function masterSaveData() {
    var allValid = true;
    var sku = $("#sku").val();
    var minQty = $("#minQty").val();
    var whid = $("#pId").text();
    var edits = $("#edit").val();

	if(sku == "" || sku == null || sku == undefined) {
		toastr.error("Material name required");
		return false;
	}
	if(minQty == "" || minQty == null || minQty == undefined) {
		toastr.error("Minimum qty required");
		return false;
	}

    if (allValid) {
        $('.loader').show();
        $.ajax({
            type: "GET",
            url: "warehouse-item-configuration-save-allocate-item?sku=" + sku + "&whid=" + whid + "&minQty=" + minQty + "&edit=" + edits,
            success: function(response) {
                if (response.code == "success") {
                    $('.loader').hide();
                    getAllocationItemDetails(whid);
					toastr.success("Data modified successfully");
                } else {
                    $('.loader').hide();
                    toastr.error(response.message);
                }
            },
            error: function(data) {
                console.log(data);
                $('.loader').hide();
                toastr.error("Something went wrong");
            }
        })
    }
}

// delete
function deleteDetails() {
    var selectedNodes = activityOptions.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
    var rowCount = 0;
    selectedData.forEach(function(selectedNodes, index) {
        rowCount = rowCount + 1;
    });
    if (rowCount > 0) {
    
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
	        
	        	var sku = selectedData.map(node => node.sku);
		        var whid = $("#pId").text();
		        $('.loader').show();
		        $.ajax({
		            type: "GET",
		            url: "warehouse-item-configuration-delete-allocate-item?sku=" + sku + "&whid=" + whid,
		            success: function(response) {
		                if (response.code == "success") {
		                    $('.loader').hide();
		                    getAllocationItemDetails(whid);
							toastr.success("Data deleted successfully");
		                } else {
		                    $('.loader').hide();
		                    toastr.error(response.message);
		                }
		            },
		            error: function(data) {
		                console.log(data);
		                $('.loader').hide();
		                toastr.error("Something went wrong");
		            }
		        })
	        
	        }
	    });
        
    } else {
        toastr.error("Please select at least one row to delete!");
    }

}


function checkNumeric(fieldId) {
    var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
    const input = document.getElementById(fieldId);
    const position = input.selectionStart;
    if (tempVal.slice(-1) == ' ') {
        $("#" + fieldId).empty();
        tempVal = '';
    }
    $("#" + fieldId).val(tempVal);
}

function addDetails() {
    $('#sku').val('').trigger('change');
    $('#minQty').val('');
    $("#edit").val('');
    enableFields();
    activityOptions.api.deselectAll();
}

function disableFields() {
    $('#sku').attr("disabled", true);
    $('#minQty').attr("disabled", true);
}

function enableFields() {
    $('#sku').attr("disabled", false);
    $('#minQty').attr("disabled", false);
}
//search bar

function onQuickFilterChanged() {
    gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
    var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
    if (firstRowNode) {
        firstRowNode.setSelected(true);
    }
}

function cancelBar() {
    if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
        onQuickFilterChanged();
    }
}