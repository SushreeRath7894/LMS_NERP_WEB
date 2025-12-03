$(function() {
	
	disableFields();
	$(".br-s-btn").hide();
	CKEDITOR.replace('qutDescription');

	pno = 1;
	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);

	var gridDiv = document.querySelector('#challanItem');
	new agGrid.Grid(gridDiv, gridOptionsChallan);

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
	}).on("change", function() {
		$('#ebillDate').val($(this).val());
	})
	$('#ebillDate').blur(function() {
		$("#ebillDateCalendar").val($(this).val());
	});
	$("#deliveryChallanDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
	}).on("change", function() {
		$('#deliveryChallanDate').val($(this).val());
	})
	$('#deliveryChallanDate').blur(function() {
		$("#deliveryChallanDateCalendar").val($(this).val());
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
	itemOptions.api.setRowData(itemDetails);
	/*if (itemOptions.gridApi) {
		itemOptions.gridApi.setRowData(itemDetails);
	}*/

	$("#addshippingAddressSec").addClass("d-none");
	const rowCount = gridOptionsChallan.api.getDisplayedRowCount();

	if (rowCount == 0) {
		add();
		console.log("Data exists in the grid.");
	} else {
		// No data in the grid
		console.log("Grid is empty.");
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
	
	viewDchallan();

});

const formatDMY = (date) => {
	  const day = String(date.getDate()).padStart(2, '0');
	  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
	  const year = date.getFullYear();
	  return `${day}-${month}-${year}`;
	};

var pno;
function viewDchallan() {
	var pages;
	var pageno = pno;
	
	let fromDate = $("#fromDate").val();
	let toDate = $("#toDate").val();

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-deliverychallan-through-ajax?pageno=" + pageno + "&fromDate=" + fromDate + "&toDate="+ toDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewDchallan;
		/* var len = allData.length;
		$('#totalItem').find('span').html(len); */
		if(allData && allData.length > 0) {
			gridOptionsChallan.api.setRowData(allData);
			var firstRow = gridOptionsChallan.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				gridOptionsChallan.api.selectNode(firstRow, true);
			}
		} else {
			gridOptionsChallan.api.setRowData([]);
			rowSelect();
		}
		
		

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
var columnDefsChallan = [
	{
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
		headerName: "Challan Id",
		field: "saleDeliveryChallan",
	},
	{
		headerName: 'Sales Order Id',
		field: "poId",
	},
	{
		headerName: 'Package Id',
		field: "salePackageId",
	},
	{
		headerName: 'Customer Name',
		field: "custName",
		width: 200,
	},
	{
		headerName: "PDF",
		cellStyle: {
			textAlign: 'center'
		},
		width: 100,
		cellRenderer: function(params) {
			if (params.data.saleDeliveryChallan) {
				return '<a id="challanPdfId" onclick="challanPdfDownload(\''
					+ params.data.saleDeliveryChallan + '\')" href="javascript:void(0)"><i class="bi bi-cloud-download"> Download PDF</i></a>';
			} else {
				return '<a>N/A</a>';
			}
		},
	},
	{
		headerName: 'Amount',
		field: "grandTotal",
		width: 120,
		type: 'rightAligned',
		valueFormatter: indianNumberFormatterWithDecimal
	},
	{
		headerName: 'Carrier',
		field: "carrier",
		width: 200,
		hide: true
	}, {
		headerName: 'Tracking URL',
		field: "tracking",
		width: 200,
		hide: true
	}, {
		headerName: 'Shipment Status',
		field: "shipmentStatus",
		width: 155,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.shipmentStatus == "Shipped") {
				return '<div style="color:#0642f5">'
					+ params.data.shipmentStatus + '</div>';
			} else {
				return '<div style="color:#ff8242">'
					+ params.data.shipmentStatus + '</div>';
			}
		},
		hide: true
	},
	{

		headerName: 'Invoice Status',
		field: "invoiceStatus",
		width: 155,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.invoiceStatus == "Generated") {
				return '<div style="color:#0642f5">'
					+ params.data.invoiceStatus + '</div>';
			} else {
				return '<div style="color:#ff8242">'
					+ params.data.invoiceStatus + '</div>';
			}
		}
	}, {
		headerName: 'Created On',
		field: "qutUpdatedOn",
		width: 130,
		cellStyle: {
			textAlign: 'center'
		}
	}];

/*function dcPdfDownload(id) {
	window.open("/sales/view-deliverychallan-pdf-downloads?dcId="+ window.btoa(id), '_blank');  
}*/

var gridOptionsChallan = {
	columnDefs: columnDefsChallan,
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 19,
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 130,
		height: 10
	},
	/*onFirstDataRendered: function(params) {
		setTimeout(function() {
			var firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 1000);
	},*/
	onSelectionChanged: rowSelect,
};
var did = "";
var rowCount = 0;
function rowSelect() {
	var selectedRows = gridOptionsChallan.api.getSelectedRows();

	if (selectedRows.length === 0) {
		newChallan();
		$('#challanCancel').removeClass('d-none');
		$('#deleteChallan').addClass('d-none');
		$('#editChallan').addClass('d-none');
		$('#addChallan').addClass('d-none');

		return;
	} else {
		getChallanPreviewById(selectedRows[0]?.saleDeliveryChallan);
		$('.del-preview').removeClass('d-none');
		$('#deleteChallan').removeClass('d-none');
		$('#editChallan').removeClass('d-none');
		$('#addChallan').removeClass('d-none');
		$('#challanCancel').addClass('d-none');
	}

	var selectedNodes = gridOptionsChallan.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var challanId = selectedData[0].saleDeliveryChallan;
	var shipmentStatus = selectedData[0].shipmentStatus;
	if (shipmentStatus == "Shipped") {
		$('#deleteChallan').addClass('d-none');
	} else {
		$('#deleteChallan').removeClass('d-none');
	}

	editdeliveryChallan(challanId);
	$("#modalForDownload").modal('hide');
}

var itemDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 8,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: "Sl No",
	field: "slNo",
	width: 80,

}, {
	headerName: 'SKU',
	field: "sku",
	width: 100,
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
	headerName: 'Item Description',
	field: "itemDesc",
	width: 200,
}, {
	headerName: 'Item Id',
	field: "itemId",
	width: 120,
	hide: true
}, {
	headerName: 'Item Desc',
	field: "itemDesc",
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

	headerName: 'Pending Quantity',
	field: "pendingQty",
	type: 'rightAligned',
	width: 100,
	hide: true,
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
	rowSelection: 'single',
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
		$('#deleteItem,#editItemBtn').attr("disabled", false);
		$('#newItem').attr("disabled", true);
	} else {
		$('#newItem').attr("disabled", false);
		$('#deleteItem,#editItemBtn').attr("disabled", true);
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
		resizable: true,
		pinned: 'left',
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
		width: 80,
	}, {
		headerName: "State",
		field: "state",
		width: 200,
		hide: true,

	}, {
		headerName: "State",
		field: "stateName",
		width: 120,
	}, {
		headerName: "City",
		field: "city",
		width: 120,
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
		width: 120,
	}, {
		headerName: "Phone",
		field: "phone",
		width: 100,
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
		hide:true,
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
		width: 120,
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
		url: "view-quotation-get-address?id=" + custId,
		async: false,
		success: function(response) {
			if (response.message === "Success") {
				var shippingDetails = JSON.parse(response.body.shippingDetails);

				var validShippingDetails = shippingDetails.filter(item => item.delFlag === 0);
				if (shipId === "null" || shipId === "" || shipId === null) {
					gridSAOptions.api.setRowData(validShippingDetails);

					var firstShippingAddress = validShippingDetails.length > 0 ? validShippingDetails[0] : null;
					console.log("1st Shipping Address------>", firstShippingAddress);
					if (firstShippingAddress) {
						$("#shippingHiddenId").val(firstShippingAddress.shippingId || "");
						$("#country1").val(firstShippingAddress.country || "").attr("disabled", true);
						$("#states1").val(firstShippingAddress.state || "").attr("disabled", true);
						$("#city1").val(firstShippingAddress.city || "").attr("disabled", true);
						$("#street11").val(firstShippingAddress.street1 || "").attr("disabled", true);
						$("#street21").val(firstShippingAddress.street2 || "").attr("disabled", true);
						$("#zipCode1").val(firstShippingAddress.zipcode || "").attr("disabled", true);
						$("#phone1").val(firstShippingAddress.phone || "").attr("disabled", true);
						$("#fax1").val(firstShippingAddress.fax || "").attr("disabled", true);
						$("#gstIn1").val(firstShippingAddress.gstIn || "").attr("disabled", true);
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
						$("#gstIn1").val("").attr("disabled", true);
					}
				} else {
					gridSAOptions.api.setRowData(validShippingDetails);
					console.log("Shipping Details during edit------>", validShippingDetails);
					var selectedShippingAddress = validShippingDetails.find(item => item.shippingId === shipId);
					if (selectedShippingAddress) {
						$("#shippingHiddenId").val(selectedShippingAddress.shippingId);
						$("#country1").val(selectedShippingAddress.country).attr("disabled", true);
						$("#states1").val(selectedShippingAddress.state).attr("disabled", true);
						$("#city1").val(selectedShippingAddress.city).attr("disabled", true);
						$("#street11").val(selectedShippingAddress.street1).attr("disabled", true);
						$("#street21").val(selectedShippingAddress.street2).attr("disabled", true);
						$("#zipCode1").val(selectedShippingAddress.zipcode).attr("disabled", true);
						$("#phone1").val(selectedShippingAddress.phone).attr("disabled", true);
						$("#fax1").val(selectedShippingAddress.fax).attr("disabled", true);
						$("#gstIn1").val(selectedShippingAddress.gstIn).attr("disabled", true);
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
			url: "view-deliverychallan-get-po?custId=" + custId,
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


	}
}
function updatePoDropdown(po,selPoId = '') {
	var $dropdown = $("#purchaseOrderId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select SO</option>');
	
	let d = [];
	
	if(po && po[0]) {
		d = JSON.parse(po[0]);
		console.log(JSON.parse(po[0]))
		console.log(d)
		d.forEach(function(a) {
			$dropdown.append('<option value="' + a.so_id + '">' + a.po_no + ' ( ' + a.so_id + ' )' + '</option>');
		});
		
		if(selPoId) {
			$("#purchaseOrderId").val(selPoId);
		} else {
			$("#purchaseOrderId").val('');
		}
	}
	
}
function onPoChange(poId, selectedPackageId = null) {
	itemOptions.api.setRowData([])
	$.ajax({
		type: "GET",
		url: "view-deliverychallan-get-package?poId=" + poId,
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
		console.log("Selected Values from multiple9: ", selectedValues);

		$('#toHiddenIdPackages').val(selectedValues ? selectedValues.join(',') : '');
		console.log("Updated Hidden Input Value (multiple9): ", $('#toHiddenIdPackages').val());
		fetchDataBasedOnSelection(selectedValues);
	});
	// $('#multiple9').prop('disabled', true); // Step 2: disable
	$('#multiple9').trigger('chosen:updated');
	console.log("Dropdown created successfully.");
}

function fetchDataBasedOnSelection(selectedValues) {

	let selectedValuesStr = selectedValues.join(',');
	console.log("Selected Value As String----------->", selectedValuesStr);

	$.ajax({
		type: "GET",
		url: "view-deliverychallan-get-item-details?selectedValuesStr=" + selectedValuesStr,
		dataType: "json",
		success: function(response) {
			console.log("Quotation Data:", response);
			if (response && response.body && Array.isArray(response.body) && response.body.length > 0) {
				try {
					let parsedData = JSON.parse(response.body[0]);
						
						if (parsedData && typeof parsedData.shippingAddress === 'string' && parsedData.shippingAddress.length > 0) {
						    const cid = $("#custId").val();
						    getAddressDetails(cid, parsedData.shippingAddress);
						}

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
						taxableAmt: item.taxableAmount,
						pendingQty: 0
					}));

					itemOptions.api.setRowData(gridData);
					itemOptions.api.hideOverlay();
					priceCalculation();

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

function newChallan() {
	enableFields();
	$("#showCustName1").html('')
	$("#showCustName2").html('')
	let fieldsToClear = [
		"#orderReceiveDate", "#salesReferenceId", "#purchaseOrderId", "#custGSTNo", "#approveStatus", "#salesOrderheadId",
		"#custId", "#custName", "#reference", "#tMode", "#vehicleNo", "#transporterId", "#transporterName", "#lrNumber", "#challanReferenceId", "#saleDeliveryChallan"
		, "#ebillNo", "#ebillDate", "#toHiddenIdPackages", "#transporterGst","#dcComment","#dcRemarks","#dcDesc"
	];
	$('#salesOrderheadId').html('');
	//$('#version').html('');
	//$('#savePoWo').removeClass('d-none')
	updatePoDropdown([]);
	fieldsToClear.forEach(field => $(field).val(""));
	$("#project").val("").trigger('change');
	let elementsToHide = ['#mailId', '#mail', '#approveBtn', '#pdfButton', '#shippingAddressId', '#shippingAddress'];
	elementsToHide.forEach(el => $(el).addClass('d-none'));
	itemOptions.api.setRowData();
	$('.del-preview').addClass('d-none');
	$.ajax({
		type: "GET",
		url: "view-deliverychallan-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				//console.log("Response--------------"+JSON.stringify(response.body[0].name));
				$("#challanReferenceId").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#deliveryChallanDate").val(newDate).prop("disable", true);
	//$("#packageDropdownContainer").html("");
	updatePackageDropdown([]);
	$("#qSGST").val('0.00');
	$("#qIGST").val('0.00');
	$("#qCGST").val('0.00');
	$("#qSGSTHid").val('0.00');
	$("#qIGSTHid").val('0.00');
	$("#qCGSTHid").val('0.00');
	$("#subTotal").val('0.00');
	$("#grandTotal").val('0.00');
}
function add() {
	//enableFields();
	gridOptionsChallan.api.deselectAll();
	newChallan();
}
function cancelChallan() {
	if (gridOptionsChallan.api) {
		gridOptionsChallan.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		let selectedRowa = gridOptionsChallan.api.getSelectedRows();
		disableFields(selectedRowa[0]?.status);
	}
	$('#addChallan').removeClass('d-none');
	$('#challanCancel').addClass('d-none');
	$('#deliveryChallanDate').val('');
	$('#challanReferenceId').val('');
}
/*function priceCalculation() {
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


	gTotal = parseFloat(val) + parseFloat(val2) + parseFloat(val3)
		+ parseFloat(val4); 

	$("#grandTotal").val(commaFormattedWithDecimal(gTotal))
	console.log(`sub total ${val},IGST ${val4},CGST ${val3},SGST ${val2},grand total ${gTotal}`)

}*/

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


	gTotal = parseFloat(val) + parseFloat(val2) + parseFloat(val3)
		+ parseFloat(val4); /*+ parseFloat(tcsAmount)*/

	$("#grandTotal").val(commaFormattedWithDecimal(gTotal))
	console.log(`sub total ${val},IGST ${val4},CGST ${val3},SGST ${val2},grand total ${gTotal}`)
	//$("#tcsAmount").val(tcsAmount.toFixed(2))

}
function calculateAdjustment() {
	var adjustment = $("#adjustment").val();
	if (adjustment == "" || adjustment == null || adjustment == "null") {
		adjustment = 0;
		$("#adjustment").val(adjustment);
	}
	var grandTotal = $("#grandTotal").val()?.replace(/,/g, "");
	if (grandTotal == "" || grandTotal == null || grandTotal == "null") {
		grandTotal = 0;
		$("#grandTotal").val(commaFormattedWithDecimal(grandTotal));
	}
	var subTotal = $("#subTotal").val()?.replace(/,/g, "");
	var qSGST = $("#qSGST").val()?.replace(/,/g, "");
	var qCGST = $("#qCGST").val()?.replace(/,/g, "");
	var qIGST = $("#qIGST").val()?.replace(/,/g, "");
	let tcsAmount = '0';
	var totVal = parseFloat(subTotal) + parseFloat(qSGST)
		+ parseFloat(qCGST) + parseFloat(qIGST) + parseFloat(tcsAmount);
	if (adjustment && totVal) {
		var add = parseFloat(adjustment) + parseFloat(totVal);
		$("#grandTotal").val(commaFormattedWithDecimal(add));
	} else {
		var add = 0;
		$("#grandTotal").val(commaFormattedWithDecimal(totVal));
	}
}



function eWayBillValidation() {
	var wayBillValid = true;
	var gt = $("#grandTotal").val()?.replace(/,/g, "");
	var taxType = $("#taxType").val();

	if (taxType == 'true') {
		if (gt >= 50000) {
			if (!$("#ebillDate").val()) {
				toastr.error("Way Bill Date Required");
				return wayBillValid = false;
			}
			if (!$("#ebillNo").val()) {
				toastr.error("Way Bill No Required");
				return wayBillValid = false;
			}
		}
	} else {
		if (!$("#ebillDate").val()) {
			toastr.error("Way Bill Date Required");
			return wayBillValid = false;
		}
		if (!$("#ebillNo").val()) {
			toastr.error("Way Bill No Required");
			return wayBillValid = false;
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
function saveDeliveryChallan() {


	if (validProductData() && validFormData()) {

		var datas = [];
		if (itemOptions.api.getDisplayedRowCount() > 0) {

			itemOptions.api.forEachNode(function(rowNode, indrx) {

				var obj = rowNode.data;
				obj.saleDeliveryChallan = $("#saleDeliveryChallan").val();
				//obj.saleDeliveryChallan = $("#deliverychallanheadId").html();
				obj.saleInvoiceId = $("#saleInvoiceId").val();
				obj.deliveryChallanDate = $("#deliveryChallanDate").val();
				obj.subTotal = $("#subTotal").val()?.replace(/,/g, "");
				obj.grandTotal = $("#grandTotal").val()?.replace(/,/g, "");
				obj.qIGST = $('#qIGST').val()?.replace(/,/g, "");
				obj.qCGST = $('#qCGST').val()?.replace(/,/g, "");
				obj.qSGST = $('#qSGST').val()?.replace(/,/g, "");
				obj.taxType = $('#taxType').val();
				obj.custId = $('#custId').val();
				obj.custName = $('#custName').val();
				obj.adjustment = $("#adjustment").val();
				obj.tcs = $("#tcsId").val();
				obj.tcsAmount = $("#tcsAmount").val();

				obj.challanType = $("#challanType").val();
				obj.salePackageId = $("#toHiddenIdPackages").val();
				obj.poId = $("#purchaseOrderId").val();
				obj.ebillNo = $("#ebillNo").val();
				obj.ebillDate = $("#ebillDate").val();
				obj.tMode = $("#tMode").val();
				obj.freight = $("#freight").val();
				obj.freigtCharge = $("#freigtCharge").val();
				obj.freigtTaxRate = $("#freigtTaxRate").val();
				obj.vehicleNo = $("#vehicleNo").val();
				obj.transporterId = $("#transporterId").val();
				obj.transporterName = $("#transporterName").val();
				obj.transporterGst = $("#transporterGst").val();
				obj.lrNumber = $("#lrNumber").val();
				obj.dcDesc = $("#dcDesc").val();
				obj.dcRemarks = $("#dcRemarks").val();
				obj.dcComment = $("#dcComment").val();
				obj.shippingHiddenId = $("#shippingHiddenId").val();
				obj.project = $("#projectId").val();

				obj.total = $("#total").val();
				datas.push(obj);

			});

		} else {
			var obj = {};

			obj.saleDeliveryChallan = $("#saleDeliveryChallan").val();
			//obj.saleDeliveryChallan = $("#deliverychallanheadId").html();
			obj.saleInvoiceId = $("#saleInvoiceId").val();
			obj.deliveryChallanDate = $("#deliveryChallanDate").val();
			obj.subTotal = $("#subTotal").val()?.replace(/,/g, "");
			obj.grandTotal = $("#grandTotal").val()?.replace(/,/g, "");
			obj.qIGST = $('#qIGST').val()?.replace(/,/g, "");
			obj.qCGST = $('#qCGST').val()?.replace(/,/g, "");
			obj.qSGST = $('#qSGST').val()?.replace(/,/g, "");
			obj.taxType = $('#taxType').val();

			obj.custId = $('#custId').val();
			obj.custName = $('#custName').val();

			obj.adjustment = $("#adjustment").val();
			obj.tcs = $("#tcsId").val();
			obj.tcsAmount = $("#tcsAmount").val();

			obj.challanType = $("#challanType").val();
			obj.salePackageId = $("#toHiddenIdPackages").val();
			obj.poId = $("#poId").val();
			obj.ebillNo = $("#ebillNo").val();
			obj.ebillDate = $("#ebillDate").val();
			obj.tMode = $("#tMode").val();
			obj.freight = $("#freight").val();
			obj.freigtCharge = $("#freigtCharge").val();
			obj.freigtTaxRate = $("#freigtTaxRate").val();
			obj.vehicleNo = $("#vehicleNo").val();
			obj.transporterId = $("#transporterId").val();
			obj.transporterName = $("#transporterName").val();
			obj.transporterGst = $("#transporterGst").val();
			obj.lrNumber = $("#lrNumber").val();
			obj.dcDesc = $("#dcDesc").val();
			obj.dcRemarks = $("#dcRemarks").val();
			obj.dcComment = $("#dcComment").val();
			obj.total = $("#total").val();
			obj.shippingHiddenId = $("#shippingHiddenId").val();
			obj.project = $("#projectId").val();
			datas.push(obj);



		}

		console.log(datas);
		saveAllDeliveryChallan(datas);
	}
}

function saveAllDeliveryChallan(datas) {
	console.log("ssssssssssssss-", JSON.stringify(datas));
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-deliverychallan-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				console.log(JSON.stringify(datas));
				toastr.success('Data saved successfully');

				viewDchallan();
				let saleDeliveryChallanId = datas.length > 0 ? datas[0].saleDeliveryChallan : "";

				setTimeout(() => {
					gridOptionsChallan.api.forEachNode((node) => {
						if (saleDeliveryChallanId == "") {
							let firstRow = gridOptionsChallan.api.getDisplayedRowAtIndex(0);
							if (firstRow) {
								firstRow.setSelected(true);
							}
						} else if (node.data.saleDeliveryChallan == saleDeliveryChallanId) {
							node.setSelected(true);
						}
					});
				}, 500);
			}
		},
		error: function(datas) {
			console.log(datas);
			$('.loader').hide();
		}
	});
}

function editdeliveryChallan(saleDeliveryChallan, shipmentStatus, invoiceStatus) {

	nextTab('salesInfLiId');
	disableFields();
	//Cancel();
	/*var shipmentStatus = shipmentStatus;
	var invoiceStatus = invoiceStatus;

	if (shipmentStatus == window.btoa("Shipped")
			|| invoiceStatus == window.btoa("Generated")) {
		$("#save").hide();

	} else {
		$("#save").show();
	}*/
	$('.loader').show();
	agGrid.simpleHttpRequest({
		url: 'view-deliverychallan-edit-new?id=' + saleDeliveryChallan
	}).then(function(data) {
		$('.loader').hide();
		console.log("=========================", data);
		$("#saleDeliveryChallan").val(data[0].saleDeliveryChallan);
		$("#challanReferenceId").val(data[0].saleDeliveryChallan);
		$("#custId").val(data[0].custId);
		$("#custName").val(data[0].custName).trigger("change");
		$("#showCustName2").html(data[0].custName);
		$("#showCustName1").html(data[0].custName);
		let poIdArray = data[0].poId ? data[0].poId.split(",") : [];
		
		$.ajax({
			type: "GET",
			url: "view-deliverychallan-get-edit-po?custId=" + data[0].custId + "&chId=" + data[0].saleDeliveryChallan,
			dataType: "json",
			success: function(response) {
				console.log("Quotation Data:", response);
				if (response && response.body) {
					updatePoDropdown(response.body,data[0].poId);
				}
			},
			error: function(xhr, status, error) {
				console.error("Error fetching quotation data:", error);
			}
		});
		
		//updatePoDropdown(poIdArray);
		setTimeout(() => {
			
			let selectedPackages = data[0].salePackageId ? data[0].salePackageId.split(",") : [];
			console.log("Selected Packages before calling updatePackageDropdown: ", selectedPackages);

			updatePackageDropdown(data[0].salePackageId.split(","), selectedPackages);
		}, 500);
		$('#multiple9').trigger("chosen:updated");

		//updatePackageDropdown(data[0].salePackageId)
		//$("#salePackageId").val(data[0].salePackageId);
		if (data[0].qutActive == "0") {
			$("#qutActive").prop("checked", false);
		} else {
			$("#qutActive").prop("checked", true);
		}
		let invoiceDate = data[0].invoiceDate;
		if (invoiceDate) {
			let formattedDate = invoiceDate.split("-").reverse().join("-");
			$("#deliveryChallanDate").val(formattedDate);
		}
		$("#challanType").val(data[0].challanType);
		$("#reference").val(data[0].reference);
		$("#challanReference").val(data[0].saleDeliveryChallan);
		$("#ebillNo").val(data[0].ebillNo);
		$("#ebillDate").val(data[0].ebillDate);
		// $("#grandTotal").val(data[0].grandTotal);
		$("#qSGST").val(commaFormattedWithDecimal(data[0].qSGST));
		$("#qIGST").val(commaFormattedWithDecimal(data[0].qIGST));
		$("#qCGST").val(commaFormattedWithDecimal(data[0].qCGST));
		$("#subTotal").val(commaFormattedWithDecimal(data[0].subTotal));
		$("#grandTotal").val(commaFormattedWithDecimal(data[0].grandTotal));
		//$("#custName").attr("disabled", true);
		//$("#poId").attr("disabled", true);
		//$("#salePackageId").attr("disabled", true);
		$("#freigtTaxRate").val(data[0].freigtTaxRate);
		$("#freigtCharge").val(data[0].freigtCharge);
		$("#tMode").val(data[0].tMode);
		$("#freight").val(data[0].freight);
		$("#totalFreightCharges").val(data[0].freigtCharge);
		$("#lrNumber").val(data[0].lrNumber);
		$("#transporterName").val(data[0].transporterName);
		$("#transporterGst").val(data[0].transporterGst);
		$("#transporterId").val(data[0].transporterId);
		$("#vehicleNo").val(data[0].vehicleNo);
		$("#dcRemarks").val(data[0].dcRemarks);
		$("#dcComment").val(data[0].dcComment);
		$("#dcDesc").val(data[0].dcDesc);

		$("#total").val(data[0].total);
		//$('#tMode').attr("disabled", true);
		$('#freight').attr("disabled", true);
		$('#freigtCharge').attr("disabled", true);
		$("#taxType").val(data[0].taxType);
		$("#shippingHiddenId").val(data[0].shippingHiddenId);
		$("#project").val(data[0].projectName);
		$("#projectId").val(data[0].project);

		getAddressDetails(data[0].custId, data[0].shippingHiddenId);
		itemOptions.api.setRowData(data);



	});

}
function disableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "tMode", "vehicleNo", "transporterId", "transporterName", "lrNumber", "transporterGst","dcRemarks","dcComment","dcDesc"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	$("#multiple9").prop("disabled", true).trigger("chosen:updated");
	$(".chosen-select").attr("disabled", true).trigger("chosen:updated");
	$(".chosen-container-multi").addClass("chosen-disabled");
}
function enableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "tMode", "vehicleNo", "transporterId", "transporterName", "lrNumber", "transporterGst","dcRemarks","dcComment","dcDesc"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	$("#multiple9").prop("disabled", false).trigger("chosen:updated");
	$(".chosen-select").removeAttr("disabled").trigger("chosen:updated");
	$(".chosen-container-multi").removeClass("chosen-disabled");

	$('#deleteChallan').addClass('d-none');
	//$('#downloadBtn').addClass('d-none');
	$('#editChallan').addClass('d-none');
	$('#addChallan').addClass('d-none');
	$('#challanCancel').removeClass('d-none');
	//$("#approveChallan").addClass("d-none");
}

function deleteChallanOnclick() {

	Swal.fire({
				title: 'Are you sure?',
				text: 'Do you really want to delete this order?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes, delete it!',
				cancelButtonText: 'No, keep it',
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
			}).then((result) => {
				if (result.value) {
					var selectedRows = gridOptionsChallan.api.getSelectedRows();
					var selectedRowsString = '';
					selectedRows.forEach(function(selectedRow, index) {
						if (index > 0) {
							selectedRowsString += ',';
						}
						selectedRowsString += selectedRow.saleDeliveryChallan;
					});
					if (selectedRowsString) {
						var item = {};
						item.saleDeliveryChallan = selectedRowsString;
				
						$.ajax({
							type: "POST",
							url: "view-deliverychallan-delete",
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify(item),
							success: function(response) {
				
								if (response.message == "Success") {
									viewDchallan();
									toastr.success('Data Deleted Successfully');
									if (gridOptionsChallan.api) {
										gridOptionsChallan.api.getDisplayedRowAtIndex(0)?.setSelected(true);
									}
								} else {
				
								}
							},
							error: function(data) {
								console.log(data)
							}
						})
					} else {
						$("#alert").modal('show');
						document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
					}
				}
			})

	

}
function onQuickFilterChanged() {
	gridOptionsChallan.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptionsChallan.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptionsChallan.api.setQuickFilter('');
	gridOptionsChallan.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptionsChallan.api) {
			gridOptionsChallan.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function checkNumeric() {

}
function getTransporterName() {

	var search = $("#transporterName").val();
	$("#transporterId").val('');
	$("#transporterGst").val('');
	if (search) {

		$.ajax({
			type: "GET",
			url: 'view-deliverychallan-get-transporter-details?searchValue=' + search,
			async: false,
			success: function(response) {
				console.log(response)
				var jsonData = JSON.parse(response.body);
				console.log(jsonData)
				if (jsonData != null) {

					$("#transporterName").css("background", "#FFF");
					var content = '<ul id="autocomplete-list1">';
					for (var i = 0; i < jsonData.length; i++) {
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#fff; background-color: var(--mainColor);"  class="autocompletedata cp" onClick="selectTransporterName(\''

							+ jsonData[i].transporterName
							+ '\',\''
							+ jsonData[i].transporterId
							+ '\',\''
							+ jsonData[i].transporterGst
							+ '\')">'
							+ jsonData[i].transporterName
							+ '</li>';
					}

					content += '</ul>';
					$("#suggesstion-boxdriver_").show();
					$("#suggesstion-boxdriver_").html(content);

				} else {
					$("#transporterName").css("background", "#FFF");
					var content = '<ul id="autocomplete-list1">';


					content += '</ul>';
					$("#suggesstion-boxdriver_").hide();
					$("#suggesstion-boxdriver_").html(content);
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	} else {

		$("#transporterName").val("");
		$("#suggesstion-boxdriver_").hide();

	}
}
function selectTransporterName(transporterName, transporterId, transporterGst) {
	$('.formValidation').remove();
	if (transporterName) {
		$("#transporterName").val(!transporterName || transporterName === "null" ? "" : transporterName);
		$("#transporterId").val(!transporterId || transporterId === "null" ? "" : transporterId);
		$("#transporterGst").val(!transporterGst || transporterGst === "null" ? "" : transporterGst);
		$("#search").val(!transporterName || transporterName === "null" ? "" : transporterName);
		$("#search").attr('data-procat', !transporterName || transporterName === "null" ? "" : transporterName);
		$("#suggesstion-boxdriver_").hide();

	} else {
		$("#transporterName").val("");
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxdriver_").hide();

	}
}
let challanIdGlobal = '';

function challanPdfDownload(challanIdd) {
/*	var challanId = '';
	if (challanIdd == "") {
		var selectedRows = gridOptionsChallan.api.getSelectedRows();
		var selectedNodes = gridOptionsChallan.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		challanId = selectedData[0].saleDeliveryChallan;
	} else {
		challanId = challanIdd;
	}
	if (challanId) {
		var organization = $("#sessionOrganization").val();
		var orgDivision = $("#sessionOrgDivision").val();
		var userId = $("#sessionId").val();
		window.open("/sales/view-challan-pdf-downloads?challanId=" + challanId, '_blank');
	}*/
	if (challanIdd) {
		challanIdGlobal = challanIdd;
	} else {
		const selectedNodes = gridOptionsChallan.api.getSelectedNodes();
		const selectedData = selectedNodes.map(node => node.data);
		if (selectedData.length > 0) {
			challanIdGlobal = selectedData[0].saleDeliveryChallan;
		}
	}
	
	// Show modal
	$('#modalForDownload').modal('show');
}

function downloadPDF() {
	let selectedCopies = [];

	$('#modalForDownload input[type="checkbox"]:checked').each(function () {
		selectedCopies.push($(this).val());
	});

	console.log("Selected Copy Types:", selectedCopies);
	console.log("Challan ID:", challanIdGlobal);

	if (challanIdGlobal) {
		let organization = $("#sessionOrganization").val();
		let orgDivision = $("#sessionOrgDivision").val();
		let userId = $("#sessionId").val();

		// Build URL with query parameters
		let copyParam = encodeURIComponent(selectedCopies.join(','));
		let url = `/sales/view-challan-pdf-downloads?challanId=${challanIdGlobal}&copies=${copyParam}`;

		window.open(url, '_blank');
	}

	$('#modalForDownload').modal('hide');
	$('#modalForDownload input[type="checkbox"]').prop('checked', false);
}


function closeTooltip(button) {
	let tooltip = bootstrap.Tooltip.getInstance(button);
	if (tooltip) {
		tooltip.hide();
	}
}

function changeAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		nextTab('quotationInfLiId');
		return;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	$('#mySAGrid').show();
	$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
	$('#changeAddress,#saveAddress').addClass('d-none');
	$('#closeAddress').removeClass('d-none');

}

function addAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		nextTab('quotationInfLiId');
		return;
	}

	var status = $("#approveStatus").val();
	if (status == "Approved" || status == "Revised") {
		toastr.error('Quotation is approved, you can\'t add BOM');
		return;
	}

	$('#mySAGrid').hide();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress,#addAddress').addClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').removeClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function closeAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec,#addAddress').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn").val('');
}

function saveAddress() {

	let fax = $("#fax2").val() ? $("#fax2").val() : "";
	let city = $("#city2").val();
	let phone = $("#phone2").val() ? $("#phone2").val() : "";
	let state = $("#states2").val();
	let stateName = $("#states2 option:selected").text();
	let country = $("#country2").val();
	let countryName = $("#country2 option:selected").text();
	let zipcode = $("#zipCode2").val();
	let street1 = $("#street12").val();
	let street2 = $("#street22").val() ? $("#street22").val() : "";
	let gstIn = $("#gstIn2").val();

	if (!country) {
		toastr.error('Country Required');
		return;
	}
	if (!state) {
		toastr.error('State Required');
		return;
	}
	if (!city) {
		toastr.error('City Required');
		return;
	}
	if (!street1) {
		toastr.error('Street 1 Required');
		return;
	}
	if (!zipcode) {
		toastr.error('Zipcode Required');
		return;
	}

	let obj = {
		"fax": fax,
		"city": city,
		"phone": phone,
		"state": state,
		"country": country,
		"delFlag": 0,
		"street1": street1,
		"street2": street2,
		"zipcode": zipcode,
		"stateName": stateName,
		"shippingId": generateUUID()?.toString(),
		"countryName": countryName,
		"gstIn": gstIn
	}



	if (zipcodeValid2) {

		let dataset = [];
		gridSAOptions.api.forEachNode(a => {
			dataset.push(a.data);
		})

		dataset.push(obj);

		let key = $("#custId").val();
		let code = JSON.stringify(dataset);

		let o = { key, code };

		$('.loader').show();
		$('body').addClass('overlay');

		$.ajax({
			type: "POST",
			url: "view-quotation-add-shipping-address",
			contentType: "application/json",
			data: JSON.stringify(o),
			success: function(resp) {
				$('.loader').hide();
				$('body').removeClass('overlay');
				if (resp.code === 'success') {
					toastr.success('Shipping address saved successfully');
					if (resp.body) {
						gridSAOptions.api.setRowData([]);
						gridSAOptions.api.setRowData(JSON.parse(resp.body));

						$('#mySAGrid').show();
						$('#shippingAddressSec,#addshippingAddressSec').addClass('d-none');
						$('#saveAddress,#closeAddress').addClass('d-none');
						$('#addAddress,#changeAddress').removeClass('d-none');
						$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
					}

				} else {
					toastr.error(resp.message);
				}
			},
			error: data => {
				console.log(data)
				$('.loader').hide();
				$('body').removeClass('overlay');
				toastr.error('Something went wrong');
			}
		})
	} else {
		toastr.error('Zipcode is not valid');
		return;
	}
}

function selectAddress() {
	$('#changeAddress').removeClass('d-none');
	$('#shippingAddressSec').removeClass('d-none');
	//$('#selectAddress').addClass('d-none');
	$('#mySAGrid').hide();
	let selectedData = gridSAOptions.api.getSelectedRows();
	$("#shippingHiddenId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).attr("disabled", true);
	$("#states1").val(selectedData[0].state).attr("disabled", true);
	$("#city1").val(selectedData[0].city).attr("disabled", true);
	$("#street11").val(selectedData[0].street1).attr("disabled", true);
	$("#street21").val(selectedData[0].street2).attr("disabled", true);
	$("#zipCode1").val(selectedData[0].zipcode).attr("disabled", true);
	$("#phone1").val(selectedData[0].phone).attr("disabled", true);
	$("#fax1").val(selectedData[0].fax).attr("disabled", true);
	$("#gstIn1").val(selectedData[0].gstIn).attr("disabled", true);
	getStateDataOnEditMul('country1', 'states1', selectedData[0].state);
	gridSAOptions.api.deselectAll();
}

var zipcodeValid2;
function zipcodeVal2() {

	var zipcode = $('#zipCode2').val();

	var zipcodeid = /^\d{6}(-\d{6})?$/;
	var zipcodeid1 = /^\d{6}(-\d{5})?$/;
	if (zipcode != '') {
		if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {

			$("#error152").hide();
			zipcodeValid2 = true;
			return true;
		} else {
			$("#error152").show();
			$("#error152").html("Please enter a valid Zip Code No.");
			zipcodeValid2 = false;
			return false;
		}

	} else {
		$("#error152").hide();
		zipcodeValid2 = true;
		return true;
	}

}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function getStateDetails2() {

	var cname = $('#country2').val();

	$("#states2").empty();
	$("#states2").append('<option value="">Select</option>');

	if (cname) {
		$.ajax({
			type: "GET",
			url: "view-quotation-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states2").append(option);
					}
				}
			},
			error: function(e) { }
		});
	}
}

function editItem() {
	$("#editItemBtn,#itemListSectionId,#totalCalcSectionId").addClass("d-none");
	$("#saveItemDetailsBtn,#closeItemDetailsBtn,#itemAddSectionId").removeClass("d-none");
	
	$("#selItemName,#selItemId,#selItemQty,#selItemPendingQty,#selItemDesc").val('');
	
	let selRow = itemOptions.api.getSelectedRows();
	
	if(selRow && selRow.length > 0) {
		$("#selItemName").val(selRow[0]?.itemName);
		$("#selItemId").val(selRow[0]?.sku);
		$("#selItemQty").val(selRow[0]?.quantity);
		$("#selItemPendingQty").val(selRow[0]?.pendingQty);
		$("#selItemDesc").val(selRow[0]?.itemDesc);
	}
}

function closeItemDetails() {
	$("#editItemBtn,#itemListSectionId,#totalCalcSectionId").removeClass("d-none");
	$("#saveItemDetailsBtn,#closeItemDetailsBtn,#itemAddSectionId").addClass("d-none");
	
	$("#selItemName,#selItemId,#selItemQty,#selItemPendingQty,#selItemDesc").val('');
}

function checkQty() {
	let selRow = itemOptions.api.getSelectedRows();
	
	if(selRow && selRow.length > 0) {
		let itemQty = selRow[0]?.quantity;
		let itemPndQty = selRow[0]?.pendingQty;
		let itemPckQty = selRow[0]?.packedQty;
		let inputQty = $("#selItemQty").val() || '0';
		
		if(parseFloat(inputQty) > (parseFloat(itemQty) + parseFloat(itemPndQty))) {
			$("#selItemQty").val('');
			$("#selItemPendingQty").val(parseFloat(itemQty) + parseFloat(itemPndQty));
			return showError('The quantity cannot exceed the available amount');
		} else {
			$("#selItemPendingQty").val(parseFloat(itemQty) + parseFloat(itemPndQty) - parseFloat(inputQty));
		}
	} else {
		return showError('Row is not selected');
	}
}

function saveItemDetails() {
	
	let inputQty = $("#selItemQty").val() || '0';
	let inputPndQty = $("#selItemPendingQty").val() || '0';
	

	const selectedNodes = itemOptions.api.getSelectedNodes();
	let selRows = itemOptions.api.getSelectedRows();
	
	let unitPrice = selRows[0]?.unitPrice || 0;
	let gstRate = selRows[0]?.gstRate || 0;
	let itemCgst = selRows[0]?.itemCgst || 0;
	let itemSgst = selRows[0]?.itemSgst || 0;
	let itemIgst = selRows[0]?.itemIgst || 0;
	
	let lineTotal = (inputQty * unitPrice) || 0;
	lineTotal = parseFloat(lineTotal?.toString())?.toFixed(2);
	
	let cgst = (lineTotal * gstRate / 200) || 0;
	let igst = (lineTotal * gstRate / 100) || 0;
	
	if (selectedNodes.length > 0) {
      	selectedNodes[0].setDataValue('quantity', inputQty);
	    selectedNodes[0].setDataValue('pendingQty', inputPndQty);
	    selectedNodes[0].setDataValue('lineTotal', lineTotal);
	     
      	if(itemCgst == itemSgst && parseFloat(itemSgst) != 0.0) {
			selectedNodes[0].setDataValue('itemIgst', '0');
			selectedNodes[0].setDataValue('itemCgst', parseFloat(cgst?.toString()).toFixed(2));
			selectedNodes[0].setDataValue('itemSgst', parseFloat(cgst?.toString()).toFixed(2));
			
			let totalPrice = parseFloat(lineTotal?.toString()) + parseFloat(cgst?.toString()) + parseFloat(cgst?.toString());
			selectedNodes[0].setDataValue('taxableAmt', parseFloat(totalPrice?.toString()).toFixed(2));
		} else if(parseFloat(itemIgst) != 0.0) {
			selectedNodes[0].setDataValue('itemIgst', parseFloat(igst?.toString()).toFixed(2));
			selectedNodes[0].setDataValue('itemCgst', '0');
			selectedNodes[0].setDataValue('itemSgst', '0');
			
			let totalPrice = parseFloat(lineTotal?.toString()) + parseFloat(igst?.toString());
			selectedNodes[0].setDataValue('taxableAmt', parseFloat(totalPrice?.toString()).toFixed(2));
		} else {
			selectedNodes[0].setDataValue('itemIgst', '0');
			selectedNodes[0].setDataValue('itemCgst', '0');
			selectedNodes[0].setDataValue('itemSgst', '0');
			
			let totalPrice = parseFloat(lineTotal?.toString());
			selectedNodes[0].setDataValue('taxableAmt', parseFloat(totalPrice?.toString()).toFixed(2));
		}
    }
    
    let subtotal = 0;
    let totalCgst = 0;
    let totalIgst = 0;
    let grandTotal = 0;
	itemOptions.api.forEachNode(a => {
	    subtotal += parseFloat(a?.data?.lineTotal?.toString() || '0');
	    totalCgst += parseFloat(a?.data?.itemCgst?.toString() || '0');
	    totalIgst += parseFloat(a?.data?.itemIgst?.toString() || '0');
	    grandTotal += parseFloat(a?.data?.taxableAmt?.toString() || '0');
	});
    
    $("#subTotal").val(commaFormattedWithDecimal(subtotal));
	$("#qIGST").val(commaFormattedWithDecimal(totalIgst));
	$("#qCGST").val(commaFormattedWithDecimal(totalCgst));
	$("#qSGST").val(commaFormattedWithDecimal(totalCgst));
	$("#grandTotal").val(commaFormattedWithDecimal(grandTotal));
    
    closeItemDetails();
}