let type = '';
$(function() {
	pno = 1;
	
	openGRNSection()
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var gridDiv = document.querySelector('#myGridItem');
	new agGrid.Grid(gridDiv, itemOptions);
	
	var salesGridDiv = document.querySelector('#salesPriceGrid');
	new agGrid.Grid(salesGridDiv, salesGridOptions);

	var dateFormat = localStorage.getItem("dateFormat");
	$("#effectDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0,
	}).on("change", function() {
		$('#effectDate').val($(this).val());
	})

	$('#effectDate').blur(function() {
		$("#effectDateCalendar").val($(this).val());
	})

	salesGridOptions.api.setRowData([]);
	itemOptions.api.setRowData([]);
	gridOptions.api.setRowData([]);
	$(".br-s-btn").hide();
	$('#itemName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#categoryId').select2({
	  placeholder: "Select",
	  allowClear: true,
	});
	
	
	$('#subcategory').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#variationType').select2({
		placeholder: "Select",
		allowClear: true
	});
	
	$(".select2-selection__clear").removeAttr("title");
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
});

function openGRNSection() {
	viewGrnProduct();
	$("#quickFilter1").val('');
	$("#itemListBtn,#myGrid,#grnSearchSec").removeClass('d-none');
	$("#grnInvoiceBtn,#myGridItem,#itemSearchSec").addClass('d-none');
	if(itemOptions?.api)
	itemOptions.api.deselectAll();
}

function openItemSection() {
	viewSalesPriceData();
	$("#quickFilter").val('');
	$("#itemListBtn,#myGrid,#grnSearchSec").addClass('d-none');
	$("#grnInvoiceBtn,#myGridItem,#itemSearchSec").removeClass('d-none');
	if(gridOptions?.api)
	gridOptions.api.deselectAll();
}

var colmnDefs = [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: 'SKU',
		field: "sku_id",
		width: 80,
		flex:1
	}, {
		headerName: 'Item Name',
		field: "item_name",
		width: 200,
		flex:3
	}, {
		headerName: 'Quantity',
		field: "quantity",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},
		flex:1
	}, {
		headerName: 'Unit',
		field: "unit",
		width: 70,
		flex:1
	}, {
		headerName: 'Date',
		field: "date",
		width: 80,
		flex:1
	}, {
		headerName: 'Category',
		field: "category",
		width: 100,
		flex:1
	}, {
		headerName: "GRN",
		field: "vreification_id",
		flex:2
	}
];

var colmnDefsItem = [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "SKU",
		field: "sku_name",
		flex:1
	}, {
		headerName: 'Category',
		field: "category_name",
		width: 80,
		flex:1,
	}, {
		headerName: 'Sub-Category',
		field: "subcat_name",
		width: 200,
		flex:1,
	}, {
		headerName: 'Variant',
		field: "variant_name",
		width: 100,
		flex:1,
	},
];

var gridOptions = {
	columnDefs: colmnDefs,
	rowSelection: 'single',
	onSelectionChanged: onRowSelection,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120,
		height: 10
	},
	onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 300);
	}
};

var itemOptions = {
	columnDefs: colmnDefsItem,
	rowSelection: 'single',
	onSelectionChanged: onRowSelectItem,
	suppressRowClickSelection: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 20,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120,
		height: 10
	},
};

function onRowSelection() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	
	$('#addSalesPriceBtn').removeClass('d-none');
	$('#cancelBtn').addClass('d-none');
	$('#purchaseDiv').removeClass('d-none');
	$("#purchaseAmount").val('0.00');
	$("#productId").val('');
	$("#grnId").val('');
	salesGridOptions.api.setRowData([]);
	if(selectedRows && selectedRows.length > 0) {
		var grnId = selectedData[0].vreification_id;
		$("#grnId").val(grnId);
		$("#purchaseAmount").val(selectedRows[0]?.price);
		$("#categoryId,#subcategory,#variationType,#itemName").attr('disabled',true);
		$("#categoryId").val(selectedRows[0]?.category_id).trigger('change');
		getsubcategoryList(selectedRows[0]?.category_id, selectedRows[0]?.subcat, selectedRows[0]?.variant, selectedRows[0]?.sku_id)
	} else {
		$("#categoryId,#subcategory,#variationType,#itemName").removeAttr('disabled');
		$("#categoryId,#subcategory,#variationType,#itemName").val('').trigger('change');
	}
	
}

var colmnDefsSales = [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Batch No",
		field: "batchNo",
		flex:1
	}, {
		headerName: 'Purchase Price',
		field: "purchasePrice",
		width: 80,
		flex:1,
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Landing Price',
		field: "landingPrice",
		width: 200,
		flex:1,
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'MRP',
		field: "mrp",
		width: 100,
		type: 'rightAligned',
		flex:1,
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Sales Price',
		field: "salesPrice",
		width: 200,
		flex:1,
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: 'Effective Date',
		field: "effectDate",
		width: 200,
		flex:1,
		valueFormatter: (params) => {
	      if (!params.value) return "";
	      const dateParts = params.value.split("-"); 
	      return `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`; 
	    },
	}
];

var salesGridOptions = {
	columnDefs: colmnDefsSales,
	rowSelection: 'single',
	onSelectionChanged: onSalesRowSelect,
	suppressRowClickSelection: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120,
		height: 10
	},
};

function onSalesRowSelect() {
	var selectedRows = salesGridOptions.api.getSelectedRows();
	var selectedQuoteRows = gridOptions.api.getSelectedRows();
	var quoteRowCount = 0;
	selectedQuoteRows.forEach(function(selectedRow, index) {
		quoteRowCount = quoteRowCount + 1;
	});
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$(".br-dis").prop("disabled", false);
	} else {
		$(".br-dis").prop("disabled", true);
	}
}

function onRowSelectItem() {

	var selectedRows = itemOptions.api.getSelectedRows();
	
	$('#addSalesPriceBtn').removeClass('d-none');
	$('#cancelBtn').addClass('d-none');
	$('#purchaseDiv').removeClass('d-none');
	$("#purchaseAmount").val('0.00');
	$("#productId").val('');
	$("#grnId").val('');
	salesGridOptions.api.setRowData([]);
	if(selectedRows && selectedRows.length > 0) {
		$("#categoryId,#subcategory,#variationType,#itemName").attr('disabled',true);
		$("#categoryId").val(selectedRows[0]?.category_id).trigger('change');
		getsubcategoryList(selectedRows[0]?.category_id, selectedRows[0]?.subcat_id, selectedRows[0]?.variant_id, selectedRows[0]?.sku_id)
		getPriceDetails(selectedRows[0]?.sku_id);
	} else {
		$("#categoryId,#subcategory,#variationType,#itemName").removeAttr('disabled');
		$("#categoryId,#subcategory,#variationType,#itemName").val('').trigger('change');
	}
	
}

function indianNumberFormatterWithDecimal(params) {

	if(params.data.level === 'L1') {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return null;
	} else {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return '0.00';
	}
		
    const value = parseFloat(params.value).toFixed(2); // Ensure two decimal places
    const parts = value.split("."); // Split integer and decimal part
    const integerPart = parts[0]; // Integer part
    const decimalPart = parts[1]; // Decimal part (always two digits)

    // Handle numbers below 1000 separately
    if (integerPart.length <= 3) {
        return integerPart + "." + decimalPart;
    }

    // Format for numbers >= 1000
    const lastThree = integerPart.substring(integerPart.length - 3);
    const otherNumbers = integerPart.substring(0, integerPart.length - 3);
    const formattedValue =
        (otherNumbers ? otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," : "") + lastThree;

    return formattedValue + "." + decimalPart; // Append decimal part
}

var pno;
function viewGrnProduct() {
	var pages;
	var pageno = pno;
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-sales-price-data-through-ajax?pageno=" + pageno + "&type=" + type
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		if(jsonData) {
			var allData = jsonData?.AllocationData;
			gridOptions.api.setRowData(allData);
	
			var totalRowCount = gridOptions.api.getModel().getRowCount();
			$('#totalCandidate').find('span').html(totalRowCount);
		} else {
			gridOptions.api.setRowData([]);
		}
		
	});

}
var pno1 = 1;
function viewSalesPriceData() {
	var pages;
	var pageno = pno1;
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-sales-price-data-item-wise?pageno=" + pageno 
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		if(jsonData) {
			var allData = jsonData?.AllocationData;
			itemOptions.api.setRowData(allData);
			
			const firstRow = itemOptions.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
			    firstRow.setSelected(true);
			}
			
		} else {
			itemOptions.api.setRowData([]);
		}
		
	});

}

function getPriceDetails(skuId) {
	agGrid.simpleHttpRequest({
		url: "view-sales-price-product-details?skuId=" + skuId 
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		if(jsonData) {
			var allData = jsonData?.AllocationData;
			salesGridOptions.api.setRowData(allData);
		} else {
			salesGridOptions.api.setRowData([]);
		}
		
	});
}

function getProductDetails(grnId, skuId) {
	$.ajax({
		url: 'view-sales-price-product-details?grnId=' + grnId + '&skuId=' + skuId,
		method: 'GET',
		success: function(response) {
			if (response.code === "success") {
				let productDetails = JSON.parse(response.body);
				let salesPriceDetails = productDetails[0].salesPriceDetails;
				salesGridOptions.api.setRowData(salesPriceDetails);
				$('#categoryId').val(productDetails[0].productCategory).attr('disabled', true);
				getsubcategoryList(productDetails[0].productCategory, productDetails[0].subCategory, productDetails[0].variationType);
				$('#itemName').val(productDetails[0].skuId).trigger('change').attr('disabled', true);
				$('#purchasePrice').val(productDetails[0].purchasePrice);
				$('#productName').text(productDetails[0].productName);
				$('#skuView').text(productDetails[0].skuId);
				//$('#productId').val(productDetails[0].productId);
				$('#grnId').val(grnId);
			}
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error:', status, error);
		}
	});
}

function getsubcategoryList(categoryId, selectedSubCategoryId='', selectedVariationType='', selectedSku = '') {
	
	$("#subcategory").empty();
	$("#subcategory").append("<option value=''>Select</option>");
	$("#variationType").empty();
	$("#variationType").append("<option value=''>Select</option>");
	$("#itemName").empty();
	$("#itemName").append("<option value=''>Select</option>");
	
	if(categoryId) {
		$.ajax({
			type: "GET",
			url: "view-sales-price-get-subcategory?id=" + categoryId,
			success: function(response) {
				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#subcategory").append(option);
					}
					if (selectedSubCategoryId) {
						$("#subcategory").val(selectedSubCategoryId);
						setTimeout(function() {
							getVariationList(selectedSubCategoryId, selectedVariationType, selectedSku);
						}, 100);
						$("#subcategory").trigger("change").attr('disabled', true);
					}
				}
			},
			error: function(e) {
				console.error("Error fetching subcategories:", e);
			}
		});
	}
}

//getVariationList
function getVariationList(id,vid='',selectedSku='') {
	
	$("#variationType").empty();
	$("#variationType").append("<option value=''>Select</option>");
	$("#itemName").empty();
	$("#itemName").append("<option value=''>Select</option>");
	
	if(id) {
		$.ajax({
			type: "GET",
			url: "view-sales-price-get-variation-list?id=" + id,
			success: function(response) {
				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#variationType").append(option);
					}
					if (vid != '') {
						$("#variationType").val(vid).trigger('change');
						getItemList(vid,selectedSku)
					}
				}
			},
			error: function(e) { 
				console.log(e)
			}
		});
	}
}

function getItemList(id,itemId=''){
	
	$("#itemName").empty();
	$("#itemName").append("<option value=''>Select</option>");
	
	if(id) {
		$.ajax({
			type: "GET",
			url: "view-sales-price-get-item-list?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					if(response.body) {
						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#itemName").append(option);
						}
						if (itemId != '') {
							$("#itemName").val(itemId).trigger('change');
						} else {
							itemId = response.body[0]?.key;
							$("#itemName").val(itemId).trigger('change');
						}
					}
					
				}
			},
			error: function(e) { 
				console.log(e)
			}
		});
	}
}

/*function getVariationList(subCategoryId, selectedVariationType) {
	$.ajax({
		type: "GET",
		url: "view-sales-price-get-variation-list?id=" + subCategoryId,
		success: function(response) {
			if (response.message == "success") {
				$("#variationType").empty();
				var option = $("<option></option>");
				$(option).val(null).html("Select");
				$("#variationType").append(option);

				// Populate variations
				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#variationType").append(option);
				}
				if (selectedVariationType) {
					$("#variationType").val(selectedVariationType).trigger('change').attr('disabled', true);
				}
			}
		},
		error: function(e) {
			console.error("Error fetching variations:", e);
		}
	});
}*/
function toggleSection() {
	
	let category = $("#categoryId").val();
	let subcategory = $("#subcategory").val();
	let variant = $("#variationType").val();
	let item = $("#itemName").val();
	
	if(!category) {
		toastr.error('Category Required');
		return;
	}
	if(!subcategory) {
		toastr.error('Sub-Category Required');
		return;
	}
	if(!variant) {
		toastr.error('Variant Required');
		return;
	}
	if(!item) {
		toastr.error('SKU Required');
		return;
	}
	
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#batchNo").val('');
	
	let purchaseAmount = $("#purchaseAmount").val();
	if(purchaseAmount) {
		$("#purchasePrice").val(parseFloat(purchaseAmount)?.toFixed(2));
	} else {
		$("#purchasePrice").val('');
	}
	
	$("#landingPrice").val('');
	$("#mrp").val('');
	$("#salesPrice").val('');
}
function cancelItemDetails() {
	salesGridOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#batchNo").val('');
	$("#landingPrice").val('');
	$("#mrp").val('');
	$("#salesPrice").val('');
	$("#effectDate").val('');
	$("#purchasePrice").val('');
}
function saveTableData() {
	// Get input field values
	let batchNo = $("#batchNo").val();
	let purchasePrice = $("#purchasePrice").val();
	purchasePrice = purchasePrice ? purchasePrice : "0";
	let landingPrice = $("#landingPrice").val();
	landingPrice = landingPrice ? landingPrice : "0";
	let mrp = $("#mrp").val();
	mrp = mrp ? mrp : "0";
	let effectDate = $('#effectDate').val();
	let salesPrice = $("#salesPrice").val();

	if (effectDate == null || effectDate == "") {
		toastr.error(' Effective Date Required');
		return;
	}
	if (mrp == null || mrp == "") {
		toastr.error('MRP Required');
		return;
	}
	if (salesPrice == null || salesPrice == "") {
		toastr.error('Sales Price Required');
		return;
	}
	
	let formattedDate = effectDate.split("-").reverse().join("-");
	// Prepare row data
	let newRow = {
		batchNo: batchNo,
		purchasePrice: purchasePrice,
		landingPrice: landingPrice,
		mrp: mrp,
		salesPrice: salesPrice,
		effectDate: formattedDate
	};

	let gridApi = salesGridOptions.api;
	gridApi.applyTransaction({ add: [newRow] });
	cancelItemDetails()
}

function saveSalesPrice() {
	let productId = $('#productId').val();
	let sku = $('#itemName').val();
	let category = $('#categoryId').val();
	let subcat = $('#subcategory').val();
	let variant = $('#variationType').val();
	let grnId = $('#grnId').val();
	let gridApi = salesGridOptions.api;
	let allRows = gridApi.getDisplayedRowAtIndex();
	let dataset = [];
	gridApi.forEachNode(function(node) {
		let rowData = node.data;
		rowData.productId = productId;
		rowData.sku = sku;
		rowData.category = category;
		rowData.grnId = grnId;
		dataset.push(rowData);
	});
	
	if(!category) {
		toastr.error('Category Required');
		return;
	}
	if(!subcat) {
		toastr.error('Sub-Category Required');
		return;
	}
	if(!variant) {
		toastr.error('Variant Required');
		return;
	}
	if(!sku) {
		toastr.error('SKU Required');
		return;
	}
	
	if(!dataset || dataset.length === 0) {
		toastr.error('Price Details Are Missing');
		return;
	}
	
	gridOptions.api.deselectAll();
	$.ajax({
		type: "POST",
		url: "view-sales-price-save-data",
		contentType: "application/json",
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.code == "success") {
				toastr.success('Sales Price Added Successfully');
				$('#itemName,#categoryId,#subcategory,#variationType').val('').trigger('change');
				$('#productId,#grnId').val('');
				gridApi.setRowData([]);
				openItemSection()
			} else {
				toastr.error(response?.message);
			}
		},
		error: function(xhr, status, error) {
			console.error("Error:", error);
			toastr.error(error?.message);
		}
	});
}

function deleteItemOnclick() {
	var selectedRows = salesGridOptions.api.getSelectedRows();
	
	if(selectedRows && selectedRows.length > 0) {
	
		if(selectedRows[0]?.slNo) {
			let slNo = selectedRows[0]?.slNo;
			
			$.ajax({
				type: "GET",
				url: "view-sales-price-delete-item?slNo=" + slNo,
				async: false,
				success: function(response) {
					if (response.code == "success") {
						toastr.success('Price Details Deleted Successfully');
						getPriceDetails($("#itemName").val());
					}
				},
				error: function(data) {
					$('.loader').hide();
					console.log(data)
				}
			});
		} else {
			salesGridOptions.api.applyTransaction({
		    	remove: selectedRows
		    });
		    toastr.success('Price Details Deleted Successfully');
		}
	
	} else {
		toastr.error('Select a Row to Delete');
	}

}

function onQuickFilterChanged() {
	itemOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
	    const firstRowNode = itemOptions.api.getDisplayedRowAtIndex(0);
	    if (firstRowNode) {
	        itemOptions.api.getRowNode(firstRowNode.id).setSelected(true);
	    }
	}, 0);
	
}

function resetBtn() {
	$("#quickFilter").val('');
	itemOptions.api.setQuickFilter('');
	itemOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		if (itemOptions.api) {
			itemOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function onQuickFilterChanged1() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter1').value);
	setTimeout(() => {
	    const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	    if (firstRowNode) {
	        gridOptions.api.getRowNode(firstRowNode.id).setSelected(true);
	    }
	}, 0);
}

function resetBtn1() {
	$("#quickFilter1").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

$(document).ready(()=>{

	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
	    if (event.key === "Enter") {
	        onQuickFilterChanged();
	    }
	});
	
	document.getElementById("quickFilter1").addEventListener("keydown", function(event) {
	    if (event.key === "Enter") {
	        onQuickFilterChanged1();
	    }
	});

});

function getItemdetailsBySku() {
	var id = $("#itemName").val();
	if (id) {
		$.ajax({
			type: "GET",
			url: "view-sales-price-get-item-bySku?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.itemDetails;
					if (allData != null) {
						$('#productId').val(allData.productId)
					} else {
						toastr.error("Something Went Wrong!");
						$('#productId').val('');
					}
				} else {
					toastr.error("Something Went Wrong!");
					$('#productId').val('');
				}
			},
			error: function(e) { }
		});
	}
}
function addSalesPrice() {
	gridOptions.api.deselectAll();
	itemOptions.api.deselectAll();
	$('#categoryId').val('').attr('disabled', false);
	$('#subcategory').val('').attr('disabled', false);
	$('#variationType').val('').attr('disabled', false);
	$('#itemName').val('').trigger('change').attr('disabled', false);
	salesGridOptions.api.setRowData([]);
	$("#purchasePrice").val('');
	$("#productId").val('');
	$("#grnId").val('');
	$("#productName").text('');
	$("#skuView").text('');
	$('#purchaseDiv').addClass('d-none');
	$('#addSalesPriceBtn').addClass('d-none');
	$('#cancelBtn').removeClass('d-none');
}
function cancelSalesPrice(){
	$('#addSalesPriceBtn').removeClass('d-none');
	$('#cancelBtn').addClass('d-none');
	setTimeout(() => {
						if (gridOptions.api) {
							gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
						}
					}, 300);
}
