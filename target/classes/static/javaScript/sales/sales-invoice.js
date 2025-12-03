let itype = '';
$(() => {
	$('#type').select2();  //#type
	$('#purchaseOrderId').select2();  //#purchaseOrderId
	$('#tMode').select2();  //#tMode
	$('#country1').select2();  //#country1
	$('#states1').select2();  //#states1
	
});

$(function() {
	const urlParams = new URLSearchParams(window.location.search);
	itype = urlParams.get('id');
	
	if (itype === 'Performa-Invoice') {
	    $('#eWayBillSection').hide();
	    $('#type').val("Perform");
		$('#type').select2({}).trigger('select2:selecting');
	} else if (itype === 'Tax-Invoice') {
	    $('#eWayBillSection').show();
	    $('#type').val("Others");
		$('#type').select2({}).trigger('select2:selecting');		
	} else {
	    itype = '';
	}
	disableFields();
	$("#type").prop("disabled", true);
	$(".br-s-btn").hide();
	pno = 1;

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);

	var gridDiv = document.querySelector('#salesItem');
	new agGrid.Grid(gridDiv, gridOptionssales);

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);
	$('#mySAGrid').hide();
	var dateFormat = localStorage.getItem("dateFormat");
	$("#ebillDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#ebillDate').val($(this).val());
	})
	$('#ebillDate').blur(function() {
		$("#ebillDateCalendar").val($(this).val());
	});

	$("#invoiceDateCalendarInvoice").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#invoiceDate').val($(this).val());
	})
	$('#invoiceDate').blur(function() {
		$("#invoiceDateCalendarInvoice").val($(this).val());
	});

	$("#dueDateCalendarInvoice").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#dueDate').val($(this).val());
	})
	$('#dueDate').blur(function() {
		$("#dueDateCalendarInvoice").val($(this).val());
	});

	$("#dateofSupplyCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		//minDate : new Date()
	}).on("change", function() {
		$('#dateofSupply').val($(this).val());
	})
	$('#dateofSupply').blur(function() {
		$("#dateofSupplyCalendar").val($(this).val());
	});


	$('#itemName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#project').select2({
		placeholder: "Select",
		allowClear: true
	});
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});

	var itemDetails = [];
	if (itemOptions.gridApi) {
		itemOptions.gridApi.setRowData(itemDetails);
	}
	
	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendar").datetimepicker({
	    format: dateFormat,
	    closeOnDateSelect: true,
	    timepicker: false,
	    //minDate : 0,
	}).on("change", function() {
	    $('#fromDate').val($(this).val());
	})
	
	$('#fromDate').blur(function() {
	    $("#toDateCalendar").val($(this).val());
	})
	
	$("#toDateCalendar2").datetimepicker({
	    format: dateFormat,
	    closeOnDateSelect: true,
	    timepicker: false,
	    //minDate : 0,
	}).on("change", function() {
	    $('#toDate').val($(this).val());
	})
	
	$('#toDate').blur(function() {
	    $("#toDateCalendar2").val($(this).val());
	})
	
	const currentDate = new Date();
	const startOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
	
	$("#fromDate,#toDateCalendar").val(formatDMY(startOfMonth));
	$("#toDate,#toDateCalendar2").val(formatDMY(currentDate));
	
	viewDsales();
	
});

const formatDMY = (date) => {
	  const day = String(date.getDate()).padStart(2, '0');
	  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
	  const year = date.getFullYear();
	  return `${day}-${month}-${year}`;
	};
	
var pno;
function viewDsales(id="") {
	var pages;
	var pageno = pno;
	
	let fromDate = $("#fromDate").val();
	let toDate = $("#toDate").val();
	
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-saleInvoice-through-ajax?type=" + itype + "&fromDate=" + fromDate + "&toDate=" + toDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.invoices;
		if (!Array.isArray(allData)) {
			allData = [];
			newsales();
		}
		allData = allData.sort((a, b) => new Date(b.createdOn) - new Date(a.createdOn));
		gridOptionssales.api.setRowData(allData);

		setTimeout(() => {
			if(id) {
				const rowNode = gridOptionssales.api.getRowNode(id);
					if (rowNode) {
					  rowNode.setSelected(true);
					}
			} else {
				gridOptionssales.api.forEachNode((node) => {
					let firstRow = gridOptionssales.api.getDisplayedRowAtIndex(0);
					if (firstRow) {
						firstRow.setSelected(true);
					}
	
				});
			}
			
		}, 1000);

	});
}
function amountFormatter(value) {
	if (value !== null && value !== undefined) {
		var parts = value.toString().split('.');
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}
var columnDefssales = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Sales Invoice Id",
		field: "saleInvoice",
		width: 170,
	},
	{
		headerName: 'Sales Order No',
		field: "poId",
		width: 130,
	},
	{
		headerName: 'Customer Name',
		field: "customerName",
		width: 200,
	},
	{
		headerName: 'Customer Id',
		field: "customerId",
		width: 200,
		hide: true
	}, {
		headerName: 'invoice Type',
		field: "invoiceType",
		width: 120,
		hide: true,
		cellRenderer: function(params) {
			if (params.data.invoiceType == "Others") {
				return '<div>Tax Invoice</div>';
			} else {
				return '<div>Performa Invoice</div>';
			}
		}
	}, {
		headerName: 'Invoice Date',
		field: "invoiceDate",
		width: 100,
	}, {
		headerName: 'Amount',
		field: "totalAmount",
		width: 100,
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	}, {
		headerName: "PDF",
		field: "list",
		width: 100,
		cellRenderer: function(params) {
			if (params.data.saleInvoice || params.data.saleInvoice) {
				return '<a id="registerId" onclick="invoicePdfDownloadModal(\''
					+ params.data.saleInvoice + '\', \'' + params.data.invoiceType +
					'\')" href="javascript:void(0)"><i class="bi bi-cloud-download"> Download PDF</i></a>';
			} else {
				return '<a>N/A</a>';
			}
		},
	}, {
		headerName: 'Payment Term(Days)',
		field: "paymentTerm",
		width: 200,
	}, {
		headerName: 'Due Date',
		field: "dueDate",
		width: 100,
	}, {
		headerName: 'Status',
		field: "status",
		width: 100,
	}, {
		headerName: 'Created On',
		field: "createdOn",
		width: 130,

	}];

var gridOptionssales = {
	columnDefs: columnDefssales,
	rowSelection: 'single',

	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 1000);
	},
	onSelectionChanged: rowSelect,
	paginationAutoPageSize: true,
	pagination: true,
	getRowNodeId : function(data) {
		return data.saleInvoice;
	}
};
var did = "";
var rowCount = 0;
function rowSelect() {
	var selectedRows = gridOptionssales.api.getSelectedRows();
	console.log("selected rowss-->", selectedRows);

	let val = '';
	if (selectedRows.length === 0) {
		newsales();
		nextTab('salesInfLiId');
		$('#salesCancel').removeClass('d-none');
		$('#deletesales').addClass('d-none');
		$('#editsales').addClass('d-none');
		$('#addsales').addClass('d-none');
		$('#approveSales').addClass('d-none');
		$('#saveSo').removeClass('d-none');
		return;
	} else {
		$("#previewInvoiceId").removeClass('d-none');
		$("#previewInvoice").removeClass('d-none');
		let typeOfInvoice = selectedRows[0]?.invoiceType;
		if (typeOfInvoice == "Others") {
			$('#performaFrag').addClass('d-none');
			$('#taxFrag').removeClass('d-none');
			getPerformarInvoiceData1(selectedRows[0]?.saleInvoice, selectedRows[0]?.invoiceType)
		} else {
			$('#performaFrag').removeClass('d-none');
			$('#taxFrag').addClass('d-none');
			getPerformarInvoiceData(selectedRows[0]?.saleInvoice, selectedRows[0]?.invoiceType)
		}

		$('#deletesales').removeClass('d-none');
		// $('#editsales').removeClass('d-none');
		$('#addsales').removeClass('d-none');
		$('#salesCancel').addClass('d-none');

		if (selectedRows[0].status == "Pending") {
			val = 'Pending';
			$('#approveSales,#editsales,#saveSo').removeClass('d-none');
		} else {
			val = 'Approved';
			$('#approveSales,#editsales,#saveSo').addClass('d-none');
		}
		var selectedNodes = gridOptionssales.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		var salesId = selectedData[0].saleInvoice;
		var shipmentStatus = selectedData[0].shipmentStatus;
		if (shipmentStatus == "Shipped") {
			$('#deletesales').addClass('d-none');
		} else {
			$('#deletesales').removeClass('d-none');
		}

		editdeliverysales(salesId, val);
	}


}

var itemDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: false,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Sl No",
	field: "slNo",
	width: 60,
	pinned: 'left',
}, {
	headerName: 'SKU',
	field: "sku",
	width: 80,
	    cellRenderer: function(params) {
        const sku = params.value || '';
        return `
            <div style="display:flex; align-items:center; justify-content:space-between;">
                <span>${sku}</span>
                <i class="fa-solid fa-copy" style="cursor:pointer; color:#007bff;" title="Copy SKU" onclick="navigator.clipboard.writeText('${sku}').then(() => toastr.success('Copied: ${sku}'))"></i>
            </div>
        `;
    }
}, {
	headerName: 'Item Name',
	field: "itemName",
	width: 200,
}, {
	headerName: 'Item Desc',
	field: "itemDesc",
	width: 120,
	cellRenderer: params => params.value
}, {
	headerName: 'Item Id',
	field: "itemId",
	width: 120,
	hide: true
}, {
	headerName: 'Unit',
	field: "unit",
	width: 120,
	hide: true
}, {
	headerName: 'HSN Code',
	field: "hsnCode",
	width: 120,
}, {
	headerName: 'Quantity',
	field: "quantity",
	type: 'rightAligned',
	width: 100,
}, {
	headerName: 'Unit',
	field: "unitName",
	width: 70,
}, {
	headerName: 'Unit Price',
	field: "unitPrice",
	type: 'rightAligned',
	width: 150,
	valueFormatter: indianNumberFormatterWithDecimal

}, {
	headerName: 'Discount',
	field: "discount",
	type: 'rightAligned',
	width: 120,
	//hide : true,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Gst Rate',
	field: "gstRate",
	type: 'rightAligned',
	width: 100,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Amount',
	field: "lineTotal",
	type: 'rightAligned',
	width: 100,
	valueFormatter: indianNumberFormatterWithDecimal,
}, {

	headerName: 'CGST',
	field: "itemCgst",
	type: 'rightAligned',
	width: 70,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'SGST',
	field: "itemSgst",
	type: 'rightAligned',
	width: 70,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'IGST',
	field: "itemIgst",
	type: 'rightAligned',
	width: 70,
	valueFormatter: indianNumberFormatterWithDecimal
}, {
	headerName: 'Taxable Amount',
	field: "taxableAmt",
	type: 'rightAligned',
	width: 170,
	valueFormatter: indianNumberFormatterWithDecimal
},];

var itemOptions = {
	columnDefs: itemDefs,
	rowSelection: 'multiple',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
		height: 10,
	},
	onSelectionChanged: rowSelectItem,
	getRowNodeId: function(data) {
		return data.slNo;
	}
}
function rowSelectItem() {
	var selectedRows = itemOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#deleteItem').attr("disabled", false);
		$('#newItem').attr("disabled", true);
	} else {
		$('#newItem').attr("disabled", false);
		$('#deleteItem').attr("disabled", true);
	}
}

const columnDefsa = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: "Address Id",
		field: "shippingId",
		hide: true,
		cellRenderer: function(params) {
			return '<a onclick=openDetails("' + params.data.addressId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.addressId + '</a>';
		},
		cellRenderer: function(params) {
			return '<a onclick=("' + params.data.addressId + '","' + params.data.customerId + '") href="javascript:void(0)">' + params.data.addressId + '</a>';
		}
	},

	{
		headerName: "Country",
		field: "country",
		width: 200,
		hide: true,
	}, {
		headerName: "Country",
		field: "countryName",
		width: 200,
	}, {
		headerName: "State",
		field: "state",
		width: 200,
		hide: true,

	}, {
		headerName: "State",
		field: "stateName",
		width: 200,
	}, {
		headerName: "City",
		field: "city",
		width: 200,
	}, {
		headerName: "Street",
		field: "street1",
		width: 200,
	}, {
		headerName: "Street",
		field: "street2",
		width: 200,
	}, {
		headerName: "Zip Code",
		field: "zipcode",
		width: 200,
	}, {
		headerName: "Phone",
		field: "phone",
		width: 200,
	}, {
		headerName: "Fax",
		field: "fax",
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Default Address",
		field: "defaultStatus",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.defaultStatus == "Yes") {
				return '<div style="color:#0642f5">' + params.data.defaultStatus + '</div>';
			} else {
				return '<div style="color:#a9a9a9">' + params.data.defaultStatus + '</div>';
			}
		}
	}];

const gridSAOptions = {
	columnDefs: columnDefsa,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectAddress,
	/* getRowNodeId : function(data) {
		return data.customerId;
	} */
};
function rowSelectAddress() {
	var selectedRows = gridSAOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#selectAddress').removeClass('d-none');

	} else {
		$('#selectAddress').addClass('d-none');
	}
}
function nextTab(id) {
	if (id === 'shippingAddressId') {
		var custName = $("#custName").val();
		if (custName == null || custName.trim() == "") {
			nextTab('salesInfLiId');
			toastr.error('Customer name is required');
			return false;
		}
	}
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}

function handleCustomerChange() {
	$('#shippingAddressId').removeClass('d-none');
	$('#shippingAddress').removeClass('d-none');
}
function getAddressDetails(custId, shipId) {
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-get-address?id=" + custId + "&shipId=" + shipId,
		async: false,
		success: function(response) {
			console.log("addresss=====>>>>>>>>>>", response)
			if (response.message === "Success") {
				var shippingDetails = JSON.parse(response.body.shippingDetails);

				var validShippingDetails = shippingDetails.filter(item => item.delFlag === 0);
				if (shipId === "null" || shipId === "" || shipId === null) {
					gridSAOptions.api.setRowData(validShippingDetails);

					var firstShippingAddress = validShippingDetails.length > 0 ? validShippingDetails[0] : null;
					console.log("1st Shipping Address------>", firstShippingAddress);
					if (firstShippingAddress) {
						$("#shippingHiddenId").val(firstShippingAddress.shippingId || "");
						$("#country1").val(firstShippingAddress.country || "").trigger('change').attr("disabled", true)
						$("#states1").val(firstShippingAddress.state || "").attr("disabled", true);
						$("#city1").val(firstShippingAddress.city || "").attr("disabled", true);
						$("#street11").val(firstShippingAddress.street1 || "").attr("disabled", true);
						$("#street21").val(firstShippingAddress.street2 || "").attr("disabled", true);
						$("#zipCode1").val(firstShippingAddress.zipcode || "").attr("disabled", true);
						$("#phone1").val(firstShippingAddress.phone || "").attr("disabled", true);
						$("#fax1").val(firstShippingAddress.fax || "").attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', firstShippingAddress.state);
					} else {
						$("#shippingHiddenId").val("").attr("disabled", true);
						$("#country1").val("").attr("disabled", true);
						$("#states1").val("").attr("disabled", true);
						$("#city1").val("").attr("disabled", true);
						$("#street11").val("").attr("disabled", true);
						$("#street21").val("").attr("disabled", true);
						$("#zipCode1").val("").attr("disabled", true);
						$("#phone1").val("").attr("disabled", true);
						$("#fax1").val("").attr("disabled", true);
					}
				} else {
					gridSAOptions.api.setRowData(validShippingDetails);
					console.log("Shipping Details during edit------>", validShippingDetails);
					var selectedShippingAddress = validShippingDetails.find(item => item.shippingId === shipId);
					if (selectedShippingAddress) {
						$("#shippingHiddenId").val(selectedShippingAddress.shippingId);
						$("#country1").val(selectedShippingAddress.country).trigger('change').attr("disabled", true);
						$("#states1").val(selectedShippingAddress.state).attr("disabled", true);
						$("#city1").val(selectedShippingAddress.city).attr("disabled", true);
						$("#street11").val(selectedShippingAddress.street1).attr("disabled", true);
						$("#street21").val(selectedShippingAddress.street2).attr("disabled", true);
						$("#zipCode1").val(selectedShippingAddress.zipcode).attr("disabled", true);
						$("#phone1").val(selectedShippingAddress.phone).attr("disabled", true);
						$("#fax1").val(selectedShippingAddress.fax).attr("disabled", true);
						getStateDataOnEditMul('country1', 'states1', selectedShippingAddress.state);
					}
				}
			}
		}
	});
}
function changeDateFormat(inputDate) {
	var splitDate = inputDate.split('-');
	if (splitDate.count == 0) {
		return null;
	}
	var year = splitDate[0];
	var month = splitDate[1];
	var day = splitDate[2];
	return day + '-' + month + '-' + year;
}
function getStateDataOnEditMul(cid, sid, stateId) {
	var country = $("#" + cid).val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#" + sid).empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#" + sid).append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#" + sid).append(option);
					}
					$("#" + sid).val(stateId);
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#" + sid).empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#" + sid).append(option);
	}
}
function changeAddress() {
	$('#mySAGrid').show();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress').addClass('d-none');

}
function selectAddress() {
	$('#changeAddress').removeClass('d-none');
	$('#shippingAddressSec').removeClass('d-none');
	//$('#selectAddress').addClass('d-none');
	$('#mySAGrid').hide();
	let selectedData = gridSAOptions.api.getSelectedRows();
	console.log(gridSAOptions);
	$("#shippingHiddenId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).trigger('change').attr("disabled", true);
	$("#states1").val(selectedData[0].state).attr("disabled", true);
	$("#city1").val(selectedData[0].city).attr("disabled", true);
	$("#street11").val(selectedData[0].street1).attr("disabled", true);
	$("#street21").val(selectedData[0].street2).attr("disabled", true);
	$("#zipCode1").val(selectedData[0].zipcode).attr("disabled", true);
	$("#phone1").val(selectedData[0].phone).attr("disabled", true);
	$("#fax1").val(selectedData[0].fax).attr("disabled", true);
	getStateDataOnEditMul('country1', 'states1', selectedData[0].state);
	gridSAOptions.api.deselectAll();
}

function getItemdetailsBySku() {
	var id = $("#itemName").val();
	if (id) {
		$.ajax({
			type: "GET",
			url: "view-quotation-get-item-bySku?id=" + id,
			success: function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.itemDetails;
					console.log("Product Details=====>", allData)
					if (allData != null) {
						$("#sku").val(allData.productSku);
						$("#unit").val(allData.productUnit);
						$("#hsnCode").val(allData.hsnCode);
						$("#productName").val(allData.productName);
						$("#itemId").val(allData.productId);
					} else {
						toastr.error("Something Went Wrong!");
						$("#itemName").val("").trigger('change');
						$("#sku").val("");
						$("#unit").val("");
						$("#model").val("");
						$("#unit").val("");
						$("#hsnCode").val("");
						$("#itemId").val("");
					}
				} else {
					toastr.error("Something Went Wrong!");
					$("#itemName").val("").trigger('change');
					$("#skuName").val("");
					$("#itemId").val("");
					$("#model").val("");
					$("#unit").val("");
					$("#hsnCode").val("");
					$("#itemId").val("");
				}
			},
			error: function(e) { }
		});
	}
}
function getCustomerList() {
	var search = $("#custName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "view-quotation-get-customer-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					console.log("response data" + JSON.stringify(response))
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:-30px; font-weight:100; font-size:12px;" class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].custId
								+ '\',\''
								+ response.body[i].custName
								+ '\',\''
								+ response.body[i].custGSTNo
								+ '\',\''
								+ response.body[i].taxType
								+ '\')">'
								+ response.body[i].custName
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;" onClick="selectAutocompleteValue()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	} else {
		$("#addre").hide();
		$("#suggesstion-box1_").hide();
	}
}
function handleCustomerChange() {
	$('#shippingAddressId').removeClass('d-none');
	$('#shippingAddress').removeClass('d-none');
}
function selectAutocompleteValue1(custId, custName, custGSTNo, taxType) {
	if (custId) {
		$("#custId").val(custId);
		$("#custName").val(custName);
		$("#custGSTNo").val(custGSTNo);
		$("#taxType").val(taxType);
		$("#search").val(custName);
		$("#search").attr('data-procat', custId);
		$("#suggesstion-box1_").hide();
		$("#addre").show();
		//hideShowS();
		//checkForDuplicate(key,counter);
		getAddressDetails(custId, "")
		$.ajax({
			type: "GET",
			url: "view-saleInvoice-get-po?custId=" + custId,
			dataType: "json",
			success: function(response) {
				console.log("Quotation Data:", response);
				if (response && response.body) {
					updatePoDropdown(response.body);
				}
			},
			error: function(xhr, status, error) {
				console.error("Error fetching quotation data:", error);
			}
		});
	} else {
		$("#custId").val("");
		$("#custName").val("");
		$("#custGSTNo").val("");
		$("#taxType").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();
		$("#salesPO").val('');

	}
}
function updatePoDropdown(po, poId = null) {
	var $dropdown = $("#purchaseOrderId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select PO</option>');
	po.forEach(function(po) {
		$dropdown.append('<option value="' + po[0] + '" code="' + po[1] + '">' + po[1] + '</option>');
	});
}
function onPoChange(poId, selectedPackageId = null) {

	let type = $("#type").val();
	if (type === 'Perform') {
		fetchDataBasedOnSaleOrder(poId);
	} else {
		$.ajax({
			type: "GET",
			url: "view-saleInvoice-get-challan-by-po?poId=" + poId,
			dataType: "json",
			success: function(response) {
				console.log("Package Data:", response);
				updatePackageDropdown(response.body, selectedPackageId);
			},
			error: function(xhr, status, error) {
				console.error("Error fetching package data:", error);
			}
		});
	}
}

function updatePackageDropdown(data, selectedItems = []) {
	console.log("Data For Drop down---->", data);
	console.log("Array For Drop down---->", data)
	if (!Array.isArray(data)) {
		data = [];
	}
	if (!Array.isArray(selectedItems)) {
		selectedItems = [];
	}

	// Construct dropdown HTML
	let dropdownHTML = `
        <select id="multiple9" class="chosen-select" multiple>
            ${data.length > 0
			? data.map(item => `<option value="${item}" ${selectedItems.includes(item) ? "selected" : ""}>${item}</option>`).join('')
			: ''
		}
        </select>
        <input type="hidden" id="toHiddenIdPackages">
    `;

	// Inject dropdown HTML into container
	$("#packageDropdownContainer").html(dropdownHTML);

	// Initialize Chosen.js
	$(".chosen-select").chosen({
		width: "100%",
		placeholder_text_multiple: "No packages available",
		no_results_text: "No results found!"
	});
	if (selectedItems.length > 0) {
		$('#toHiddenIdPackages').val(selectedItems.join(','));
		console.log("Pre-selected Hidden Input Value: ", $('#toHiddenIdPackages').val());
	}

	// Event: Update hidden input when user selects values
	$('#multiple9').on('change', function() {
		var selectedValues = $(this).val();
		itemOptions.api.setRowData("");
		$("#tMode").val("");
		$("#lrNumber").val("");
		$("#transporterName").val("");
		$("#transporterId").val("");
		$("#piRemarks").val("");
		$("#adjustment").val(0.00);
		$("#vehicleNo").val("");
		$("#shippingHiddenId").val("");
		$("#dateofSupply").val("");
		$("#ebillNo").val("");
		$("#ebillDate").val("");
		console.log("Selected Values from multiple9: ", selectedValues);

		$('#toHiddenIdPackages').val(selectedValues ? selectedValues.join(',') : '');
		console.log("Updated Hidden Input Value (multiple9): ", $('#toHiddenIdPackages').val());
		fetchDataBasedOnSelection(selectedValues);
	});
	//$('#multiple9').prop('disabled', true); // Step 2: disable
	$('#multiple9').trigger('chosen:updated');
	console.log("Dropdown created successfully.");
}

function fetchDataBasedOnSelection(selectedValues) {
	let selectedValuesStr = selectedValues.join(',');
	  const cid = $("#custId").val();
	console.log("Selected Value As String----------->", selectedValuesStr);
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-items-by-challan?selectedValuesStr=" + selectedValuesStr,
		dataType: "json",
		success: function(response) {
			if (response && response.body && Array.isArray(response.body) && response.body.length > 0) {
				try {
					let parsedData = JSON.parse(response.body[0]);
					console.log(parsedData)
					if (!parsedData || !parsedData.items || !Array.isArray(parsedData.items)) {
						console.warn("No items found in the response. Displaying 'NO ROWS TO SHOW'");
						itemOptions.api.setRowData([]); // Clear the grid
						itemOptions.api.showNoRowsOverlay(); // Show no rows message
						return;
					}

					let gridData = parsedData.items.map((item, index) => ({
						slNo: index + 1,
						sku: item.sku,
						itemId: item.itemId,
						unit: item.unit,
						hsnCode: item.hsnCode || "",
						itemName: item.itemName,
						itemDesc: item.itemDesc,
						quantity: item.totalPackedQuantity,
						unitName: item.unitName || "",
						unitPrice: item.unitPrice,
						discount: item.discount,
						gstRate: item.gstRate,
						lineTotal: item.totalAmount,
						itemCgst: item.cgst,
						itemSgst: item.sgst,
						itemIgst: item.igst,
						taxableAmt: item.taxableAmount
					}));
					itemOptions.api.setRowData(gridData);
					itemOptions.api.hideOverlay();
					priceCalculation();
					$("#tMode").val(parsedData.items[0].transportMode).trigger('change');
					$("#lrNumber").val(parsedData.items[0].lrNumber);
					$("#transporterName").val(parsedData.items[0].transportName);
					$("#transporterId").val(parsedData.items[0].transportId);
					$("#vehicleNo").val(parsedData.items[0].vechNo);
					$("#shippingHiddenId").val(parsedData.items[0].shipAddress);
					getAddressDetails(cid, parsedData.items[0].shipAddress);
					$("#dateofSupply").val(parsedData.items[0].dcDate);
					$("#ebillNo").val(parsedData.items[0].ebillNo);
					$("#ebillDate").val(parsedData.items[0].ebillDate);
					$("#paymentTermId").val(parsedData.items[0].payTerm);
					$("#piRemarks").val(parsedData.items[0].dcRemarks);

				} catch (error) {
					console.error("Error parsing response body:", error);
					itemOptions.api.setRowData([]);
					itemOptions.api.showNoRowsOverlay();
				}
			} else {
				console.warn("Invalid response format: Missing 'body' array, showing 'NO ROWS TO SHOW'");
				itemOptions.api.setRowData([]);
				itemOptions.api.showNoRowsOverlay();
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching quotation data:", error);
			itemOptions.api.setRowData([]);
			itemOptions.api.showNoRowsOverlay();
		}
	});
}

function fetchDataBasedOnSaleOrder(selectedValues) {
	var piid=$("#saleDeliverysales").val();
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-items-by-order?selectedValuesStr=" + selectedValues,
		dataType: "json",
		success: function(response) {
			console.log("items by sales Data:", response);
			if (response && response.body && Array.isArray(response.body) && response.body.length > 0) {
				try {
					let parsedData = JSON.parse(response.body[0]);

					if (!parsedData || !parsedData.items || !Array.isArray(parsedData.items)) {
						console.warn("No items found in the response. Displaying 'NO ROWS TO SHOW'");
						itemOptions.api.setRowData([]); // Clear the grid
						itemOptions.api.showNoRowsOverlay(); // Show no rows message
						return;
					}

					let gridData = parsedData.items.map((item, index) => ({
						slNo: index + 1,
						sku: item.sku,
						itemId: item.itemId,
						unit: item.unit,
						hsnCode: item.hsnCode || "",
						itemName: item.itemName,
						itemDesc: item.itemDesc,
						quantity: item.quantity,
						unitName: item.unitName || "",
						unitPrice: item.unitPrice,
						discount: item.discount,
						gstRate: item.gstRate,
						lineTotal: item.totalAmount,
						itemCgst: item.cgst,
						itemSgst: item.sgst,
						itemIgst: item.igst,
						taxableAmt: item.taxableAmount
					}));
					itemOptions.api.setRowData(gridData);
					itemOptions.api.hideOverlay();
					priceCalculation();
					$("#tMode").val(parsedData.items[0].transportMode).trigger('change');
					$("#lrNumber").val(parsedData.items[0].lrNumber);
					$("#transporterName").val(parsedData.items[0].transportName);
					$("#transporterId").val(parsedData.items[0].transportId);
					$("#vehicleNo").val(parsedData.items[0].vechNo);
					$("#shippingHiddenId").val(parsedData.items[0].shipAddress);
					$("#dateofSupply").val(parsedData.items[0].dcDate);
					$("#ebillNo").val(parsedData.items[0].ebillNo);
					$("#ebillDate").val(parsedData.items[0].ebillDate);
					if(piid==="" || piid===null){
						$("#paymentTermId").val(parsedData.items[0].payTerm);
					}

				} catch (error) {
					console.error("Error parsing response body:", error);
					itemOptions.api.setRowData([]);
					itemOptions.api.showNoRowsOverlay();
				}
			} else {
				console.warn("Invalid response format: Missing 'body' array, showing 'NO ROWS TO SHOW'");
				itemOptions.api.setRowData([]);
				itemOptions.api.showNoRowsOverlay();
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching quotation data:", error);
			itemOptions.api.setRowData([]);
			itemOptions.api.showNoRowsOverlay();
		}
	});
}

function newsales() {
	enableFields();

	itemOptions.api.setRowData("");
	$("#tMode").val("").trigger('change');
	$("#lrNumber").val("");
	$("#dcComment").val("");
	$("#dcRemarks").val("");
	$("#transporterName").val("");
	$("#transporterId").val("");
	$("#vehicleNo").val("");
	$("#shippingHiddenId").val("");
	$("#dateofSupply").val("");
	$("#ebillNo").val("");
	$("#ebillDate").val("");
	$("#invoiceDate").val("");
	$("#invoiceDate").empty();
	$("#paymentTermId").val("");
	$("#dueDate").val("");
	$("#piRemarks").val("");
	$("#previewInvoiceId").addClass('d-none');
	$("#previewInvoice").addClass('d-none');
	$("#adjustment").val(0.00);
	$("#type").val("").trigger('change');

	$("#showCustName1").html('')
	$("#showCustName2").html('')
	let fieldsToClear = [
		"#orderReceiveDate", "#salesReferenceId", "#purchaseOrderId", "#custGSTNo", "#approveStatus", "#salesOrderheadId",
		"#custId", "#custName", "#reference", "#tMode", "#vehicleNo", "#transporterId", "#transporterName", "#lrNumber", "#salesReferenceId", "#saleDeliverysales"
		, "#ebillNo", "#ebillDate", "#toHiddenIdPackages", "#piRemarks", "#adjustment"
	];
	$('#salesOrderheadId').html('');
	$("#saleDeliverysales").val('');
	//$('#version').html('');
	//$('#savePoWo').removeClass('d-none')
	updatePoDropdown([]);
	fieldsToClear.forEach(field => $(field).val(""));
	$("#project").val("").trigger('change');
	let elementsToHide = ['#mailId', '#mail', '#approveBtn', '#pdfButton', '#shippingAddressId', '#shippingAddress'];
	elementsToHide.forEach(el => $(el).addClass('d-none'));
	itemOptions.api.setRowData();
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#invoiceDate").val(newDate).prop("disable", true);
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#deliverysalesDate").val(newDate).prop("disable", true);
	$("#approveSales").addClass("d-none");
	$("#packageDropdownContainer").html("");
	updatePackageDropdown([]);
	if (itype === 'Performa-Invoice') {
	    $('#type').val("Perform");
		$('#type').select2({}).trigger('select2:selecting');
	} else if (itype === 'Tax-Invoice') {
	    $('#type').val("Others");
		$('#type').select2({}).trigger('select2:selecting');		
	}
	getIdentity();
	openFields();
	$("#qSGST").val('0.00');
	$("#qIGST").val('0.00');
	$("#qCGST").val('0.00');
	$("#qSGSTHid").val('0.00');
	$("#qIGSTHid").val('0.00');
	$("#qCGSTHid").val('0.00');
	$("#subTotal").val('0.00');
	$("#grandTotal").val('0.00');
}
function getIdentity() {
	var type = $("#type").val();
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-get-insertedid?type=" + type,
		success: function(response) {
			if (response.message == "success") {
				$("#salesReferenceId").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});
}
function add() {
	//enableFields();
	gridOptionssales.api.deselectAll();
	newsales();
}
function cancelsales() {
	if (gridOptionssales.api) {
		gridOptionssales.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		let selectedRowa = gridOptionssales.api.getSelectedRows();
		disableFields(selectedRowa[0]?.status);
	}

}
function priceCalculation() {
	var grid = [];
	itemOptions.api.forEachNode(function(rowNode, index) {
		grid.push(rowNode.data);
	});
	var len = grid.length;
	var val = 0;
	var val2 = 0;
	var val3 = 0;
	var val4 = 0;

	var taxType1 = $("#taxType").val();
	for (i = 0; i < len; i++) {
		if (taxType1 == 'true') {
			val2 = parseFloat(val2) + parseFloat(grid[i].itemSgst);
			val3 = parseFloat(val3) + parseFloat(grid[i].itemCgst);
		} else {
			val4 = parseFloat(val4) + parseFloat(grid[i].itemIgst);
		}

		val = parseFloat(val) + parseFloat(grid[i].lineTotal);

	}
	$("#subTotal").val(commaFormattedWithDecimal(val));
	$("#qIGST").val(commaFormattedWithDecimal(val4));
	$("#qCGST").val(commaFormattedWithDecimal(val3));
	$("#qSGST").val(commaFormattedWithDecimal(val2));

	var gTotal = 0.0;
	var adj = $("#adjustment").val();

	gTotal = parseFloat(val) + parseFloat(val2) + parseFloat(val3)
		+ parseFloat(val4) + parseFloat(adj);

	$("#grandTotal").val(commaFormattedWithDecimal(gTotal))
	console.log(`sub total ${val},IGST ${val4},CGST ${val3},SGST ${val2},grand total ${gTotal}`)


}
function eWayBillValidation() {
	var wayBillValid = true;
	var gt = $("#grandTotal").val();
	var taxType = $("#taxType").val();
	var type = $("#type").val();
	if(type == 'Others') {
		if (taxType == 'true') {
		if (gt >= 50000) {
			if (!$("#ebillDate").val()) {
				toastr.error("Way Bill Date Required");
				wayBillValid = false;
			}
			if (!$("#ebillNo").val()) {
				toastr.error("Way Bill No Required");
				wayBillValid = false;
			}
		}
	} else {
		if (!$("#ebillDate").val()) {
			toastr.error("Way Bill Date Required");
			wayBillValid = false;
		}
		if (!$("#ebillNo").val()) {
			toastr.error("Way Bill No Required");
			wayBillValid = false;
		}
	}
	}
	

	return wayBillValid;
}


function validFormData() {
	var allValid = true;
	/* 	 if (!validationUpdated("Freight Required", 'freight'))
				allValid = false;  */
	return allValid;
}

function validProductData() {
	return true;
}
// 
function saveDeliverysales() {
	if ($("#type").val() == null || $("#type").val() == "") {
		$("#purchaseOrderId").val('').select2();
		showError("Please Select Invoice Type First");
		nextTab('salesInfLiId');
		return false;
	}

	if ($("#custId").val() == null || $("#custId").val() == "") {
		showError("Customer Required");
		nextTab('salesInfLiId');
		return false;
	}

	if (validProductData() && validFormData() && eWayBillValidation()) {

		var datas = [];
		if (itemOptions.api.getDisplayedRowCount() > 0) {
			itemOptions.api.forEachNode(function(rowNode, indrx) {
				var obj = rowNode.data;
				obj.saleDeliverysales = $("#saleDeliverysales").val();
				obj.type = $("#type").val();
				obj.custId = $('#custId').val();
				obj.custName = $('#custName').val();
				obj.poId = $("#purchaseOrderId").val();
				obj.challanIds = $("#toHiddenIdPackages").val();
				obj.saleInvoice = $("#salesReferenceId").val();
				obj.invoiceDate = $("#invoiceDate").val();
				obj.paymentTerm = $("#paymentTermId").val();
				obj.dueDate = $("#dueDate").val();
				obj.dateOfSupply = $("#dateofSupply").val();
				obj.vehicleNo = $("#vehicleNo").val();
				obj.transporterId = $("#transporterId").val();
				obj.transporterName = $("#transporterName").val();
				obj.lrNumber = $("#lrNumber").val();
				obj.otherreference = $("#otherreference").val();
				obj.destination = $("#destination").val();

				obj.subTotal = $("#subTotal").val()?.replace(/,/g, "");
				obj.grandTotal = $("#grandTotal").val()?.replace(/,/g, "");
				obj.qIGST = $('#qIGST').val()?.replace(/,/g, "");
				obj.qCGST = $('#qCGST').val()?.replace(/,/g, "");
				obj.qSGST = $('#qSGST').val()?.replace(/,/g, "");
				obj.taxType = $('#taxType').val();
				obj.adjustment1 = $("#adjustment").val()?.replace(/,/g, "");
				obj.ebillNo = $("#ebillNo").val();
				obj.ebillDate = $("#ebillDate").val();
				obj.tMode = $("#tMode").val();
				obj.shippingHiddenId = $("#shippingHiddenId").val();
				obj.total = $("#total").val();
				obj.piRemarks = $("#piRemarks").val();
				datas.push(obj);
			});
		} else {
			var obj = {};
			obj.saleDeliverysales = $("#saleDeliverysales").val();
			obj.type = $("#type").val();
			obj.custId = $('#custId').val();
			obj.custName = $('#custName').val();
			obj.poId = $("#purchaseOrderId").val();
			obj.challanIds = $("#toHiddenIdPackages").val();
			obj.saleInvoice = $("#salesReferenceId").val();
			obj.invoiceDate = $("#invoiceDate").val();
			obj.paymentTerm = $("#paymentTerm").val();
			obj.dueDate = $("#dueDate").val();
			obj.dateOfSupply = $("#dateofSupply").val();
			obj.vehicleNo = $("#vehicleNo").val();
			obj.transporterId = $("#transporterId").val();
			obj.transporterName = $("#transporterName").val();
			obj.lrNumber = $("#lrNumber").val();
			obj.otherreference = $("#otherreference").val();
			obj.destination = $("#destination").val();

			obj.subTotal = $("#subTotal").val()?.replace(/,/g, "");
			obj.grandTotal = $("#grandTotal").val()?.replace(/,/g, "");
			obj.qIGST = $('#qIGST').val()?.replace(/,/g, "");
			obj.qCGST = $('#qCGST').val()?.replace(/,/g, "");
			obj.qSGST = $('#qSGST').val()?.replace(/,/g, "");
			obj.taxType = $('#taxType').val();
			obj.type = $("#type").val();
			obj.adjustment1 = $("#adjustment").val()?.replace(/,/g, "");
			obj.ebillNo = $("#ebillNo").val();
			obj.ebillDate = $("#ebillDate").val();
			obj.tMode = $("#tMode").val();
			obj.total = $("#total").val();
			obj.shippingHiddenId = $("#shippingHiddenId").val();
			obj.project = $("#projectId").val();
			obj.piRemarks = $("#piRemarks").val();
			datas.push(obj);
		}



		console.log("add Dats=====", datas);
		saveAllDeliverysales(datas);
	}
}

function saveAllDeliverysales(datas) {
	console.log("ssssssssssssss-", JSON.stringify(datas));
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-saleInvoice-add-data",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				toastr.success('Data saved successfully');

				viewDsales(); // Reloads grid data

				let saleDeliverysalesId = datas.length > 0 ? datas[0].saleDeliverysales : "";


				// Wait until grid is ready and rows are rendered
				setTimeout(() => {
					gridOptionssales.api.forEachNode((node) => {
						if (saleDeliverysalesId && node.data.saleDeliverysales === saleDeliverysalesId) {
							node.setSelected(true);
						}
					});

					// If ID wasn't found, select first row
					if (!saleDeliverysalesId) {
						const firstRow = gridOptionssales.api.getDisplayedRowAtIndex(0);
						if (firstRow) {
							firstRow.setSelected(true);
						}
					}
				}, 500); // Adjust timeout based on how long your grid takes to load
			}
		},
		error: function(datas) {
			console.log(datas);
			$('.loader').hide();
		}
	});
}
function polist(custId) {
	$.ajax({
		type: "GET",
		url: "view-saleInvoice-get-po?custId=" + custId,
		dataType: "json",
		success: function(response) {
			console.log("Quotation Data:", response);
			if (response && response.body) {
				updatePoDropdown(response.body);
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching quotation data:", error);
		}
	});
}
async function editdeliverysales(saleDeliverysales, val = '') {

	if (saleDeliverysales) {
		nextTab('salesInfLiId');
		disableFields(val);
		$('.loader').show();
		agGrid.simpleHttpRequest({
			url: 'view-saleInvoice-edit-new?id=' + saleDeliverysales
		}).then(function(data) {

			var data = JSON.parse(data.body);
			var itemList = data.itemList;
			console.log("jsonData==========================", data)
			console.log("itemList====", itemList)
			$('.loader').hide();
			$('#purchaseOrderId').select2();

			setTimeout(function() {
				if ($("#purchaseOrderId option[value='" + data.poId + "']").length === 0) {
					$("#purchaseOrderId").append(
						$("<option></option>").val(data.poId).text(data.poId) // Adjust label if needed
					);
				}
				$("#purchaseOrderId").val(data.poId).trigger('change');
			}, 1000);
			$("#taxType").val(data.taxType);
			$("#saleDeliverysales").val(data.saleDeliverysales);
			$("#salesReferenceId").val(data.saleDeliverysales);
			$("#custId").val(data.custId);
			$("#custName").val(data.custName).trigger("change");
			$("#showCustName2").html(data.custName);
			$("#showCustName1").html(data.custName);
			$('#showCustName1').attr('title', $('#showCustName1').text());



			$('#purchaseOrderId').select2();

			polist(data.custId, data);
			setTimeout(() => {

				$("#purchaseOrderId").val(data.poId);
				//onPoChange();
				let selectedPackages = data.challanIds ? data.challanIds.split(",") : [];
				console.log("Selected challanIds before calling updatePackageDropdown: ", selectedPackages);
				console.log("data.challanIds.split", data.challanIds.split(","));
				updatePackageDropdown(data?.challanList?.split(","), selectedPackages);

			}, 2000);
			$('#multiple9').trigger("chosen:updated");

			$("#packageDropdownContainer").find("*").prop("disabled", true);


			$("#invoiceDate").val(data.invoiceDate);
			$("#dueDate").val(data.dueDate);
			$("#dateofSupply").val(data.dateOfSupply);
			$("#piRemarks").val(data?.remarks);
			$("#paymentTermId").val(data?.paymentTerm);
			$("#reference").val(data.reference);
			$("#salesReference").val(data.saleDeliverysales);
			$("#ebillNo").val(data.eWayBillNo);
			$("#ebillDate").val(data.eWayBillDate);


			$("#tMode").val(data.tmode).trigger('change');
			$("#lrNumber").val(data.lrNumber);
			$("#transporterName").val(data.transporterName);
			$("#transporterId").val(data.transporterId);
			$("#vehicleNo").val(data.vechNo);
			$('#type').val(data.type);
			$('#type').select2({}).trigger('select2:selecting');
			openFields();
			$("#total").val(data.total);



			$("#shippingHiddenId").val(data.shippingHiddenId);
			if (data.shipStatus == '1') {
				$("#changeAddress").addClass('d-none');
			} else {
				$("#changeAddress").removeClass('d-none');
			}

			getAddressDetails(data.custId, data.shippingHiddenId);

			//getStateDataOnEditMul('country1', 'states1', selectedData[0].state);
			$("#subTotal").val(commaFormattedWithDecimal(data.subTotal));
			$("#qSGST").val(commaFormattedWithDecimal(data.sgst));
			$("#qIGST").val(commaFormattedWithDecimal(data.igst));
			$("#qCGST").val(commaFormattedWithDecimal(data.cgst));
			$("#adjustment").val(data.adjustment);
			$("#grandTotal").val(commaFormattedWithDecimal(data.total));
			console.log("itemList====", itemList)
			let gridData = itemList.map((item, index) => ({
				slNo: index + 1,
				sku: item.sku,
				itemId: item.itemId,
				unit: item.unit || "",
				hsnCode: item.hsnCode || "",
				itemName: item.itemName || "",
				itemDesc: item.itemDesc || "",
				quantity: item.quantity,
				unitName: item.unitName || "",
				unitPrice: item.unitPrice,
				discount: item.discount,
				gstRate: item.gstRate,
				lineTotal: item.lineTotal,
				itemCgst: item.itemCgst,
				itemSgst: item.itemSgst,
				itemIgst: item.itemIgst,
				taxableAmt: item.taxableAmt
			}));
			itemOptions.api.setRowData(gridData);

		});

	}
}

function openFields() {
	let type = $("#type").val();
	$('#multiple9').val([]).trigger('chosen:updated');
	itemOptions.api.setRowData([]);
	$("#subTotal,#adjustment,#qSGST,#qCGST,#qIGST,#grandTotal").val('0.00');
	if (type === 'Perform') {
		$(".div-tax").addClass("d-none");
		let so = $("#purchaseOrderId").val();
		if (so) {
			fetchDataBasedOnSaleOrder(so);
		}
	} else {
		$(".div-tax").removeClass("d-none");
		let so = $("#purchaseOrderId").val();
		if (so) {
			onPoChange(so);
		}
	}
}

function disableFields(val) {
	let fieldIds = ["type", "custName", "purchaseOrderId", "tMode", "vehicleNo", "transporterId", "transporterName", "lrNumber",
		"paymentTermId", "dueDate", "invoiceDate", "dateofSupply", "ebillNo", "ebillDate", "piRemarks", "adjustment"];
	$("#invoiceDateCalendarInvoice, #dueDateCalendarInvoice, #dateofSupplyCalendar").hide();
	$("#packageDropdownContainer").find("*").prop("disabled", true);
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	$("#multiple9").prop("disabled", true).trigger("chosen:updated");
	$(".chosen-select").attr("disabled", true).trigger("chosen:updated");
	$(".chosen-container-multi").addClass("chosen-disabled");
	$('#multiple9').prop('disabled', true);
	$('#deletesales').removeClass('d-none');
	if (val != 'Approved') {
		$('#editsales,#approveSales').removeClass('d-none');
	}
	$('#addsales').removeClass('d-none');
	$('#downloadBtn').removeClass('d-none');
	$('#salesCancel').addClass('d-none');
}
function enableFields(ty = null) {
	let fieldIds = ["purchaseOrderId", "tMode", "vehicleNo", "transporterId", "transporterName", "lrNumber",
		"paymentTermId", "dueDate", "invoiceDate", "dateofSupply", "ebillNo", "ebillDate", "piRemarks", "adjustment"];
	$("#invoiceDateCalendarInvoice, #dueDateCalendarInvoice, #dateofSupplyCalendar").show();
	if (ty === "E") {
		$("#custName").prop("disabled", true);
	} else {
		$("#custName").prop("disabled", false);
	}

	$("#packageDropdownContainer").find("*").prop("disabled", false);
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	$("#multiple9").prop("disabled", false).trigger("chosen:updated");
	$(".chosen-select").removeAttr("disabled").trigger("chosen:updated");
	$(".chosen-container-multi").removeClass("chosen-disabled");

	$('#deletesales').addClass('d-none');
	$('#downloadBtn').addClass('d-none');
	$('#editsales').addClass('d-none');
	$('#addsales').addClass('d-none');
	$('#salesCancel').removeClass('d-none');
	$("#approveSales").addClass("d-none");
}
function deletesalesOnclick() {
	var selectedRows = gridOptionssales.api.getSelectedRows();
	var id = selectedRows[0].saleInvoice;
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.saleInvoice;
	});
	if (selectedRowsString) {
		var item = {};
		item.saleDeliverysales = selectedRowsString;
		console.log("item to delete====", item);
		$.ajax({
			type: "POST",
			url: "view-saleInvoice-delete?id=" + id,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {

				if (response.message == "success") {
					viewDsales();
					toastr.success('Data Deleted Successfully');
					if (gridOptionssales.api) {
						gridOptionssales.api.getDisplayedRowAtIndex(0)?.setSelected(true);
					}
					setTimeout(() => {
						if (gridOptionssales.api) {
							gridOptionssales.api.deselectAll();

							const firstRowNode = gridOptionssales.api.getDisplayedRowAtIndex(0);
							if (firstRowNode) {
								firstRowNode.setSelected(true);
							}
						}
					}, 1000);
				} else {

				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}

}
function onQuickFilterChanged() {
	gridOptionssales.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	if (gridOptionssales.api) {
		gridOptionssales.api.getDisplayedRowAtIndex(0)?.setSelected(true);
	}
}

function resetBtn() {
	$("#quickFilter").val('');
	onQuickFilterChanged()
	/*gridOptionssales.api.setQuickFilter('');
	gridOptionssales.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptionssales.api) {
			gridOptionssales.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);*/
}
let invoiceIdGlobal = '';
let invoiceTypeGlobal = '';

function invoicePdfDownloadModal(id, type) {
	// Store both ID and Type globally
	if (id) {
		invoiceIdGlobal = id;
	}
	if (type) {
		invoiceTypeGlobal = type;
	}

	// Show the modal for copy selection
	$('#modalForDownload').modal('show');
}


function downloadPDF() {
	let selectedCopies = [];

	// Get all checked checkboxes
	$('#modalForDownload input[type="checkbox"]:checked').each(function () {
		selectedCopies.push($(this).val());
	});

	console.log("Selected Copy Types:", selectedCopies);
	console.log("Invoice ID:", invoiceIdGlobal);
	console.log("Invoice Type:", invoiceTypeGlobal);

	// Proceed only if both ID and type are present
	if (invoiceIdGlobal && invoiceTypeGlobal) {
		let organization = $("#sessionOrganization").val();
		let orgDivision = $("#sessionOrgDivision").val();
		let userId = $("#sessionId").val();

		// Join and encode selected copy types
		let copyParam = encodeURIComponent(selectedCopies.join(','));

		// Open the download link in a new tab
		let url = "/sales/view-saleInvoice-pdf-downloads?id=" + window.btoa(invoiceIdGlobal)
			+ "&type=" + window.btoa(invoiceTypeGlobal)
			+ "&copies=" + window.btoa(copyParam);

		window.open(url, '_blank');
	}

	// Hide modal and reset checkboxes
	$('#modalForDownload').modal('hide');
	$('#modalForDownload input[type="checkbox"]').prop('checked', false);
}


function closeModal(){
	$("#modalForDownload").modal('hide');
}
function approveOnclick() {

	var selectedRows = gridOptionssales.api.getSelectedRows();

	var id1 = selectedRows[0].saleInvoice;
	var type = selectedRows[0].invoiceType;
	var selectedRowsString = '';
	var saleInvoice = "";
	var invoiceIdArray = [];
	for (var i = 0; i < selectedRows.length; i++) {
		saleInvoice = saleInvoice + '"' + selectedRows[i].saleInvoice + '",';
		invoiceIdArray.push(selectedRows[i].saleInvoice);
	}
	saleInvoice = saleInvoice.substring(0, saleInvoice.length - 1);
	var id = saleInvoice;
	var JsonInvArrayId = JSON.stringify(invoiceIdArray);
	var comment = "Approved";
	var updatedId = selectedRows[0].saleInvoice;

	$.ajax({
		type: "GET",
		url: "view-saleInvoice-approve?id=" + encodeURIComponent(JsonInvArrayId) + "&comment=" + encodeURIComponent(comment),
		async: false,
		success: function(response) {

			if (true) {
				toastr.success("Approved Successfully")
				viewDsales(id1);
				/*gridOptionssales.api.refreshCells({ force: true });
				gridOptionssales.api.deselectAll();
				uncheckApprovedRows();*/

				$('#delete').attr("disabled", true);
				$('#reqAppvBtn').attr("disabled", true);
				$('#revision').attr("disabled", true);
				$('#add').attr("disabled", false);
				$('#invoiceApprove').attr("disabled", true);
				$('#paymentInvoice').attr("disabled", true);
				$('#reject').attr("disabled", true);

			}

		},
	});
}
// Calculate Adjustment and update Grand Total
function calculateAdjustment() {
	// Helper: Get numeric value from input (comma-safe)
	function getNumericValue(selector) {
		var val = $(selector).val();
		if (val === "" || val === null || val === "null") return 0;
		val = val.toString().replace(/,/g, "");
		var num = parseFloat(val);
		return isNaN(num) ? 0 : num;
	}

	// Helper: Format number with Indian commas
	function formatWithCommas(val) {
		return val.toLocaleString("en-IN", {
			minimumFractionDigits: 2,
			maximumFractionDigits: 2
		});
	}

	// Get values
	var adjustment = getNumericValue("#adjustment");
	var total = getNumericValue("#subTotal");
	var qSGST = getNumericValue("#qSGST");
	var qCGST = getNumericValue("#qCGST");
	var qIGST = getNumericValue("#qIGST");
	var totVal = total + qSGST + qCGST + qIGST;
	var grandTotal = totVal + adjustment;
	$("#grandTotal").val(formatWithCommas(grandTotal));
}
function validateAdjustment() {
	var val = $("#adjustment").val().toString().replace(/,/g, "");

	if (val === "-") {
		return;
	}

	var isValid = /^[-]?\d*(\.\d{0,2})?$/.test(val);

	if (!isValid) {
		toastr.error("Invalid input for Adjustment. Resetting to 0.00");
		$("#adjustment").val("0.00");
	} else {
		var numericVal = parseFloat(val);
		if (isNaN(numericVal)) {
			numericVal = 0;
		}
		$("#adjustment").val(
			numericVal.toLocaleString("en-IN", {
				minimumFractionDigits: 2,
				maximumFractionDigits: 2
			})
		);
	}

	calculateAdjustment();
}





