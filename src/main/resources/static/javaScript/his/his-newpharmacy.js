$(document).ready(function() {

    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    getAllPatientsDetails();
    gridOptions.api.setRowData();

    var gridDiv = document.querySelector('#activity');
    new agGrid.Grid(gridDiv, activityOptions);
    activityOptions.api.setRowData();
    
    $('.btn-dis').attr('disabled',true);
    $(".btn-med").hide();
    
    $('#item').select2({
		placeholder: "Select",
		allowClear: true
	});

});


function getAllPatientsDetails() {

    agGrid.simpleHttpRequest({
        url: 'his-billing-pharmacy-view'
    }).then(function(data) {
        var jsonData = JSON.parse(data?.body);
        var allData = jsonData;
        if(allData && allData.length > 0) {
        	gridOptions.api.setRowData(allData);
	        var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	        if (firstRowNode) {
	            firstRowNode.setSelected(true);
	        }
        } else {
        	gridOptions.api.setRowData([]);
        	activityOptions.api.setRowData([]);
        }
        
    });
}


// ag grid

var columnDefs = [{
        headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Booking Id",
        field: "book_id",
        flex: 1,
        cellRenderer: function(params) {
        	return params.data.test_id;
        }
    },
    {
        headerName: "Patient Id",
        field: "cust_id",
        flex: 1,
    },
    {
        headerName: "Name",
        field: "cust_name",
        flex: 1,
    },
    {
        headerName: "Address",
        field: "address",
        flex: 1,
    },
    {
        headerName: "Mobile",
        field: "cust_mob",
        flex: 1,
    },
    {
        headerName: "Age",
        field: "age",
        flex: 1,
    },
    {
        headerName: "Gender",
        field: "gender",
        flex: 1,
    }
];

// Define grid options
var gridOptions = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
    },
    pagination: true,
    paginationPageSize: 15,
    onSelectionChanged: rowSelect
};

function rowSelect() {
    var selectedRows = gridOptions.api.getSelectedRows();
    var selectedData = selectedRows.map(node => node.data);
    var rowCount = 0;
    selectedData.forEach(function(selectedRow, index) {
        rowCount = rowCount + 1;
    });
    
    if(rowCount > 0) {
    	console.log(selectedRows)
    	if(selectedRows[0].med_dtls && selectedRows[0].med_dtls.length > 0) {
    		activityOptions.api.setRowData(selectedRows[0].med_dtls);
    	} else {
    		activityOptions.api.setRowData([]);
    	}
    } else {
    	activityOptions.api.setRowData([]);
    }

}


var activityDefs = [{
	headerCheckboxSelection : true,
	headerCheckboxSelectionFilteredOnly : true,
	checkboxSelection : true,
	sortable : false,
	filter : false,
	resizable : true,
	width : 30
}, {
    headerName: "Medicine",
    field: "med_name",
    width: 250,
}, {
    headerName: "Quantity",
    field: "quantity",
    width: 250,
    cellStyle: {
        textAlign: 'center'
    },
}, {
    headerName: "Amount",
    field: "amount",
    width: 250,
    cellStyle: {
        textAlign: 'center'
    },
}, {
    headerName: "Batch",
    field: "batch",
    width: 250,
    cellStyle: {
        textAlign: 'center'
    },
}];

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
    var selectedRows = activityOptions.api.getSelectedRows();
    if (selectedRows.length > 0) {
        console.log(selectedRows[0])
        if(selectedRows[0].test_m_id) {
        	$('#edit-med-btn').removeAttr('disabled');
        } else {
        	$('.btn-dis').removeAttr('disabled');
        }
    } else {
        $('.btn-dis').attr('disabled',true);
    }
}

function activityTabs(activityId) {
    if (activityId == "registration") {
        $("#registration").removeClass('hidden');
        $("#doctorsDetail").addClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#paymentDetail").addClass('hidden');
    } else if (activityId == "doctorsDetail") {
        $("#registration").addClass('hidden');
        $("#doctorsDetail").removeClass('hidden');
        $("#otherDetail").addClass('hidden');
        $("#dependentDetail").addClass('hidden');
        $("#paymentDetail").addClass('hidden');
    }

}

function addItems() {

	activityOptions.api.deselectAll();

    $("#activity").hide();
    $("#payment").hide();
    $("#demo").show();
    $(".btn-med").show();
    $(".btn-med-v").hide();

    $("#item").val('').trigger('change');
    $("#quantity").val('');
    $("#amount").val('');
    $("#batch").val('');
    $("#prodId").val('');
    $("#unitId").val('');
    $("#unit").val('');

}

function editItems() {

    $("#activity").hide();
    $("#payment").hide();
    $("#demo").show();
    $(".btn-med").show();
    $(".btn-med-v").hide();

    $("#quantity").val('');
    $("#amount").val('');
    $("#batch").val('');
    $("#prodId").val('');
    $("#unitId").val('');
    $("#unit").val('');
    
    $("#item").attr('disabled',true);
    
    var selectedRows = activityOptions.api.getSelectedRows();
    var data = selectedRows[0];
    $("#item").val(data?.med_id).trigger('change');
    var prod_id = $('#item option:selected').attr('data-prod');
    var unit_id = $('#item option:selected').attr('data-unit');
    var unit_name = $('#item option:selected').attr('data-unitname');
    
    $("#prodId").val(prod_id);
    $("#unitId").val(unit_id);
    $("#unit").val(unit_name);

}

function setUnit() {
	var prod_id = $('#item option:selected').attr('data-prod');
    var unit_id = $('#item option:selected').attr('data-unit');
    var unit_name = $('#item option:selected').attr('data-unitname');
    
    $("#prodId").val(prod_id);
    $("#unitId").val(unit_id);
    $("#unit").val(unit_name);
}

function cancelMedFun() {

	$(".btn-med").hide();
    $(".btn-med-v").show();
    $("#activity").show();
    $("#demo").hide();
	
	$("#item").val('').trigger('change');
    $("#quantity").val('');
    $("#amount").val('');
    $("#batch").val('');
    $("#prodId").val('');
    $("#unitId").val('');
    $("#unit").val('');
    
    $("#item").removeAttr('disabled');
    
}

function openBatchSection() {
	alert('id')
}


// save
function saveItems() {
    var item = {};
    var validation = true;
    var editItems = $("#editItems").val();

    // Validate inputs
    if (!$('#item').val()) {
        validation = validationUpdated("Item Required", 'item');
    }
    if (!$('#quantity').val()) {
        validation = validationUpdated("Quantity Required", 'quantity');
    }
    if (!$('#amount').val()) {
        validation = validationUpdated("Amount Required", 'amount');
    }

    if (validation) {
        item.item = $('#item').find('option:selected').text();
        item.quantity = parseFloat($('#quantity').val() || 0);
        item.amount = parseFloat($('#amount').val() || 0);
        item.batch = $('#batch').val();
        item.gstRate = 18; // Example: Fixed GST rate of 18%

        // Calculate GST and Taxable Amount
        item.cgst = (item.amount * item.gstRate) / 200;
        item.sgst = (item.amount * item.gstRate) / 200;
        item.taxableAmt = parseFloat(item.amount) + item.cgst + item.sgst;


        var datas = [];
        if (editItems) {
            var rowNode = activityOptions.api.getSelectedRows(editItems);
            rowNode.setData(item);
        } else {
            activityOptions.api.forEachNode(function(rowNode) {
                datas.push(rowNode.data);
            });
            datas.push(item);
            activityOptions.api.setRowData(datas);

            $("#activity").show();
            $("#demo").hide();
            $("#payment").show();

            priceCalculation();
        }
    }
}

function priceCalculation() {
    var grid = [];
    activityOptions.api.forEachNode(function(rowNode) {
        grid.push(rowNode.data);
    });


    var subTotal = 0;
    var totalSGST = 0;
    var totalCGST = 0;

    // Calculate totals
    grid.forEach(item => {
        subTotal += parseFloat(item.amount || 0);
        totalSGST += parseFloat(item.sgst || 0);
        totalCGST += parseFloat(item.cgst || 0);
    });

    $("#subTotal").html(subTotal.toFixed(2));
    $("#cgst").html(totalCGST.toFixed(2));
    $("#sgst").html(totalSGST.toFixed(2));
    $("#grandTotal").html((subTotal + totalSGST + totalCGST).toFixed(2));
}