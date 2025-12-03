$(function() {
	disableFields();
	document.getElementById("discount").value = "0";
	CKEDITOR.replace('termCondition', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);
	gridSAOptions.api.setRowData();

	var gridDiv = document.querySelector('#item');
	new agGrid.Grid(gridDiv, itemOptions);
	itemOptions.api.setRowData();
$("#addshippingAddressSec").addClass("d-none");

	pno = 1;
	
	$('#docTbl').on('click', '.rmv1', function() {

		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
		openDeleteConfirm();

	});

	/* setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);  
		}
	},500); */
	
	$('#mySAGrid').hide();
	const today = new Date();
	var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
	//var today = new Date();
	var formattedDate = ("0" + today.getDate()).slice(-2) + "-" +
		("0" + (today.getMonth() + 1)).slice(-2) + "-" +
		today.getFullYear();
	$("#poDate").val(formattedDate);
	$("#poDateDateCalendar").val(formattedDate);

	$("#poDateDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		value: formattedDate
	}).on("change", function() {
		$('#poDate').val($(this).val());
	});

	$('#poDate').blur(function() {
		$("#poDateDateCalendar").val($(this).val());
	});

	$("#expectedShipmentDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		datepicker: true,
		scrollMonth: false,
		minDate: 0
		//minDate : new Date()
	}).on("change", function() {
		$('#expectedShipmentDate').val($(this).val());
	})
	$('#expectedShipmentDate').blur(
		function() {
			$("#expectedShipmentDateCalendar").val(
				$(this).val());
		});
//
$("#toDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#toDateQT').val($(this).val());
					})

					$('#toDateQT').blur(function () {
						$("#toDateQTCalendar").val($(this).val());
					})

//
					$("#fromDateQTCalendar").datetimepicker({
						format: dateFormat,
						closeOnDateSelect: true,
						//minDate: new Date(),
						timepicker: false,
					}).on("change", function () {
						$('#fromDateQT').val($(this).val());
					})

					$('#fromDateQT').blur(function () {
						$("#fromDateQTCalendar").val($(this).val());
					})


    
const currentYear = today.getFullYear();
const currentMonth = today.getMonth();

const fyStartYear = currentMonth < 3 ? currentYear - 1 : currentYear;
const firstDayOfFY = new Date(fyStartYear, 3, 1);

$("#fromDateQT").val(formatDate(firstDayOfFY));
$("#toDateQT").val(formatDate(today));	
		viewPoWo();

	$(".br-s-btn").hide();

	$('#itemName').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#project').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#quotationId').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#unit').select2({
		placeholder: "Select",
		allowClear: true
	});
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
	
	CKEDITOR.replace('itemDesc', {
		height: 150,
		removePlugins: 'wsc',
		scayt_autoStartup: true,
		scayt_maxSuggestions: 3,
		autoParagraph: false,
	});
});
    // Format helper (DD-MM-YYYY)
    function formatDate(date) {
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    }
var pno;
function viewPoWo(poId='') {
	var pages;
	var pageno = pno;

	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();	
			

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-po-or-wo-through-ajax?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData?.viewPoWo;
		if(allData && allData.length > 0) {
			gridOptions.api.setRowData(allData);
			if(poId) {
				gridOptions.api.forEachNode(node => {
				    if(node.data.referenceId === poId) {
				    	node.setSelected(true);
				    }
				});
			} else {
				let firstRow = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRow) {
					firstRow.setSelected(true);
				}
			}
			
		} else {
			gridOptions.api.setRowData([]);
		}

	});
}


function nextTab(id) {
	if (id === 'shippingAddressId') {
		var custName = $("#custName").val();
		if (custName == null || custName.trim() == "") {
			nextTab('quotationInfLiId');
			toastr.error('Customer Name Required');
			return false;
		}
	}
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
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
		width: 90,
		hide: true,
	}, {
		headerName: "Country",
		field: "countryName",
		width: 90,
	}, {
		headerName: "State",
		field: "state",
		width: 90,
		hide: true,

	}, {
		headerName: "State",
		field: "stateName",
		width: 90,
	}, {
		headerName: "City",
		field: "city",
		width: 90,
	}, {
		headerName: "Street",
		field: "street1",
		width: 90,
	}, {
		headerName: "Street",
		field: "street2",
		width: 90,
	}, {
		headerName: "Zip Code",
		field: "zipcode",
		width: 90,
	}, {
		headerName: "Phone",
		field: "phone",
		width: 90,
	}, {
		headerName: "Fax",
		field: "fax",
		cellStyle: {
			textAlign: 'left'
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
var itemDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',

	},
	{
		headerName: "SlNo",
		field: "slNo",
		width: 60,
		type: 'leftAligned',
		pinned: 'left',
		headerClass: 'custom-header-left',
		valueFormatter: (params) => params.node ? params.node.rowIndex + 1 : ''
	}, {
		headerName: 'SKU',
		field: "sku",
		type: 'leftAligned',
		width: 80,
		pinned: 'left',
		headerClass: 'custom-header-left',
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
		type: 'leftAligned',
		width: 100,
		headerClass: 'custom-header-left'
	},
	{
		headerName: 'HSN Code',
		field: "hsnCode",
		width: 120,
		hide: true,
	},
	{
		headerName: 'ITEM ID',
		field: "itemId",
		width: 120,
		hide: true,
	},
	{

		headerName: 'Size',
		field: "sizeInMM",
		type: 'leftAligned',
		width: 80,
		headerClass: 'custom-header-left',
		hide: true
	},
	{

		headerName: 'Thickness In MM',
		field: "thicknessInMM",
		type: 'leftAligned',
		width: 90,
		headerClass: 'custom-header-left',
		hide: true
	},
	{
		headerName: 'Unit',
		field: "unitName",
		type: 'leftAligned',
		width: 60,
		headerClass: 'custom-header-left'
	}, {
		headerName: 'Unit',
		field: "unit",
		hide: true,
	},
	{
		headerName: 'Quantity',
		field: "quantity",
		type: 'leftAligned',
		width: 80,
		headerClass: 'custom-header-left'
		//	valueFormatter : currencyFormatter
	}, {
		headerName: 'Unit Price',
		field: "unitPrice",
		type: 'rightAligned',
		width: 90,
		headerClass: 'custom-header-left',
		valueFormatter: indianNumberFormatterWithDecimal,

	}, {
		headerName: 'Discount (%)',
		field: "discount",
		type: 'rightAligned',
		width: 90,
		headerClass: 'custom-header-left'
		//valueFormatter : currencyFormatter
	}, {
		headerName: 'Amount',
		field: "lineTotal",
		type: 'rightAligned',
		width: 100,
		valueFormatter: indianNumberFormatterWithDecimal,
		aggFunc: 'sum',
		headerClass: 'custom-header-left'
	}, {
		headerName: 'GST Rate (%)',
		field: "gstRate",
		type: 'rightAligned',
		width: 80,
		headerClass: 'custom-header-left',
		//hide: true,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'CGST',
		field: "itemCgst",
		type: 'rightAligned',
		width: 80,
		headerClass: 'custom-header-left',
		//hide: true,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'SGST',
		field: "itemSgst",
		type: 'rightAligned',
		width: 80,
		headerClass: 'custom-header-left',
		//hide: true,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'IGST',
		field: "itemIgst",
		type: 'rightAligned',
		width: 80,
		headerClass: 'custom-header-left',
		//hide: true,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'Taxable Amount',
		field: "taxableAmt",
		type: 'rightAligned',
		width: 120,
		headerClass: 'custom-header-left',
		//hide: true,
		valueFormatter: indianNumberFormatterWithDecimal,
	}, {
		headerName: 'Description',
		field: "itemDesc",
		width: 183,
		headerClass: 'custom-header-left',
		cellRenderer: params => params.value
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
		height: 10
	},
	onSelectionChanged: rowSelectItem,
	getRowNodeId: function(data) {
		return data.slNo;
	}
};
function rowSelectItem() {
	var selectedRows = itemOptions.api.getSelectedRows();
	var selectedQuoteRows = gridOptions.api.getSelectedRows();

	if (selectedQuoteRows.length > 0) {
		var approveStatus = $("#approveStatus").val();
		$(".br-dis").prop("disabled", approveStatus === 'Approved');
		return;
	}
	var rowCount = selectedRows.length;
	$(".br-dis").prop("disabled", rowCount === 0);
}

var columnDefs = [
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
		headerName: "Po ID",
		field: "referenceId",
		pinned: 'left',
		width: 150
		/*cellRenderer: function(params) {
			return '<a onclick=editPo("'
				+ params.data.referenceId + '","'
				+ params.data.approveStatus
				+ '") href="javascript:void(0)">'
				+ params.data.referenceId + '</a>';
		}*/

	},{


		headerName: 'Created By',
		field: "qutCreatedBy",
		width: 100,
	},{
		headerName: 'Customer Name',
		field: "custName",
		width: 200,
	},{
		headerName: "Po No.",
		field: "poNo",
		width: 120
	},
	{

		headerName: 'Po Date',
		field: "poDate",
		width: 80,
		cellStyle: {
			textAlign: 'center'
		}
	},{
		headerName: 'PO Amount',
		field: "grandTotal",
		type: 'rightAligned',
		width: 100,
		cellStyle: {
			textAlign: 'right'
		},
		valueFormatter: indianNumberFormatterWithDecimal
	},
	{
		headerName: "Quotation No",
		field: "quotationId",
		width: 132,
	},
	{
		headerName: "Reference No",
		field: "reference",
		width: 134,
	},{
		headerName: "PO Ref No",
		field: "poRef",
		width: 132,
	},
	{
		headerName: "Version",
		field: "version",
		width: 70,
	},{
		headerName: 'Project',
		field: "project",
		width: 90,
	},
	{
		headerName: 'Approve Status',
		field: "approveStatus",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.approveStatus == "Approved") {
				return '<div style="color:#0642f5">' + params.data.approveStatus + '</div>';
			} else if (params.data.approveStatus == "Revised") {
				return '<div style="color:#bf05ff">' + params.data.approveStatus + '</div>';
			} else {
				return '<div style="color:#a9a9a9">' + params.data.approveStatus + '</div>';
			}
		}
	}, {
		headerName: "Order Type",
		field: "orderType",
		width: 100,
	},
	{

		headerName: "Order Status",
		field: "salesOrderStatus",
		width: 130,
	},
	{

		headerName: "Order Date",
		field: "salesOrderDate",
		width: 130,
	},
	{
		headerName: " Order Id",
		field: "salesOrderId",
	},
	

	{
		headerName: 'Expected Delivery',
		field: "expectedShipmentDate",
		width: 130,
		cellStyle: {
			textAlign: 'center'
		}
	},
	 ];

var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	onSelectionChanged: rowSelect,
	// getRowNodeId: params => params.data.referenceId
};

function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedNodes = gridOptions.api.getSelectedNodes();

	$('#updateSOId').prop('checked', false);
	// If no row is selected, reset UI and return
	if (selectedNodes.length === 0) {
		$('#addPobtn').addClass('d-none');
		$('#deleteQuot').addClass('d-none');
		$('#poApproveBtn').addClass('d-none');
		$('#revisionBtn').addClass('d-none');
		$('#editPo').addClass('d-none');
		$('#savePoWo').removeClass('d-none');
		$('#cancelPobtn').removeClass('d-none');
		$('#add').attr("disabled", false);
		$("#updateSODiv").addClass("d-none");
		addNewpo();
		return;
	}

	var selectedData = selectedNodes.map(node => node.data);

	if (selectedData.length > 0) {
		$("#updateSODiv").addClass("d-none");
		var referenceId = selectedData[0].referenceId;
		var approveStatus = selectedData[0].approveStatus;
		var salesOrderStatus = selectedData[0].salesOrderStatus;
		$("#approveStatus").val(approveStatus);
		editPo(referenceId);
		$('#cancelPobtn').addClass('d-none');
		$('#addPobtn').removeClass('d-none');
		$(".br-dis").attr("disabled",true);
		if (approveStatus === 'Approved') {
			$('#updateSODiv').addClass('d-none');
			$('#revisionBtn').removeClass('d-none');
			$('#deleteQuot').addClass('d-none');
			$('#poApproveBtn').addClass('d-none');
			$('#editPo').addClass('d-none');
			$('#savePoWo').addClass('d-none');
		} else if (approveStatus === 'Revised') {
			$('#deleteQuot').addClass('d-none');
			$('#poApproveBtn').addClass('d-none');
			$('#revisionBtn').addClass('d-none');
			$('#editPo').addClass('d-none');
			$('#savePoWo').addClass('d-none');
			$('#updateSODiv').addClass('d-none');
		} else {
			$('#poApproveBtn').removeClass('d-none');
			$('#editPo').removeClass('d-none');
			$('#savePoWo').removeClass('d-none');
			$('#deleteQuot').removeClass('d-none');
			$('#revisionBtn').addClass('d-none');
			$('#updateSODiv').addClass('d-none');
		}

		$('#add').attr("disabled", true);
	}
}

function getEditTaxType(search) {

	$.ajax({
		type: "POST",
		url: "view-po-or-wo-get-customer-list",
		dataType: 'json',
		contentType: 'application/json',
		data: search,
		success: function(response) {
			if (response.message == "success") {
				console.log("response data" + response.body[0].taxType)
				$("#taxType").val(response.body[0].taxType);
				//hideShowS();
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}
/* customer AutoSearch */
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
			url: "view-po-or-wo-get-quotation?custId=" + custId,
			dataType: "json",
			success: function(response) {
				console.log("Quotation Data:", response);
				if (response && response.body) {
					updateQuotationDropdown(response.body);
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
function updateQuotationDropdown(quotations) {
	var $dropdown = $("#quotationId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select Quotation</option>');
	quotations.forEach(function(quotation) {
		$dropdown.append('<option value="' + quotation + '">' + quotation + '</option>');
	});
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
function getAddressDetails(custId, shipId) {
	$.ajax({
		type: "GET",
		url: "view-quotation-get-address?id=" + custId,
		async: false,
		success: function(response) {
			if (response.message === "Success") {
				var shippingDetails = JSON.parse(response.body.shippingDetails);

				var validShippingDetails = shippingDetails?.filter(item => item.delFlag === 0);
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
					var selectedShippingAddress = validShippingDetails?.find(item => item.shippingId === shipId);
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
function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;

	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}
	
	let fnametext = '<div id="imageName_'+counter+'" class="imageName" style="margin-left: 2px;">'+fileName+'</div><span><i class="ti-close red close_sec1 deleteFileDoc" onclick=openDeleteConfirm('+counter+')></i></span>';
	
	if (extension[1] === "jpg" || extension[1] === "png" || extension[1] === "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] === "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon'></i></a>";
	} else if (extension[1] === "xls" || extension[1] === "xlsx" || extension[1] === "csv") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] === "doc" || extension[1] === "docx" || extension[1] === "dox") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
	}
	$("#clickImg_"+counter).removeClass('ti-plus').addClass('ti-pencil');
	// $("#uploadHidden_"+ counter).val(fileName);
	$("#uploadedBillDiv_" + counter).html(LightImg+fnametext);

}
function checkEmptyQuot() {

	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclsss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		}
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error('Please Choose a File');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMoreQuot()
	}
}
function addMoreQuot() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:last").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclsss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	// $("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');
		selectInput.eq(0).attr('id', "docid_" + i);

		textInput.eq(1).attr('id', "docnoid_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(5).attr('id', "uploadedBillDiv_" + i);
		/*divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);*/
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");
	
	$("#uploadedBillDiv_"+lengthOfTableRow).empty();

}

function checkForDuplicateEntry(event) {
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var count = 0;
	$(".docNoclsss").each(function() {
		if (document == $(this).val()) {
			count++;
		}
	})
	if (count >= 2) {
		toastr.error('Document Name Already Entered');
		return false;
	} else {
		return true;
	}

}
function openDeleteConfirm1() {
	//$("#dltValue").val("");
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	//closeDeleteConfirm();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
			+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div> </td>'
			+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}

function openDeleteConfirm(id) {
	let count = $(".uploadHidCls").length;
	
	let i = id;
	let a = '<div class="form-group d-flex"><div class="">' +
		    '<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> ' +
		    '<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">' +
		    '</div> <input type="hidden" id="uploadHidden_' + i + '" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">' +
		    '<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_' + i + '"></div></div>' +
		    '<div id="validationDiv"></div></div>';
	
	document.querySelectorAll(".uploadHidCls").forEach(input => {
		let cnt = input?.id?.split('_')[1];
		if(cnt == id) {
			input.closest("tr").querySelector("td:last-child").innerHTML = a;
		}
	});
	
}

function onQuotationChange(quotationId) {
	$.ajax({
		type: "GET",
		url: "view-po-or-wo-get-quotation-details?quotationId=" + quotationId,
		dataType: "json",
		success: function(response) {
			if (response.code === 'success') {
				let quotationDetails = JSON.parse(response.body);
				console.log("Quotation Details:", quotationDetails);

				if (quotationDetails.quotationData && quotationDetails.quotationData.length > 0) {
					
					let quotation = quotationDetails.quotationData[0];
					console.log("Quotation Object:", quotation);
					$("#endCustomerName").val(quotation?.endCustName);
					$('#project').val(quotation.projectId).trigger('change');
					$('#reference').val(quotation.quotationReference);
					CKEDITOR.instances['termCondition'].setData(quotation.termsAndCondition);
					let itemDetails = quotation.itemDetails;
					itemOptions.api.setRowData(itemDetails);
					let docDetails = quotation.documentDetails;
					// Ensure docDetails exists and has data
					if (docDetails && docDetails.length > 0) {
						// Clear existing table data to avoid duplication
						$("#doctbodyData").empty();
						
						
						for (var i = 0; i < docDetails.length; i++) {
							console.log('Processing document:', docDetails[i]);
							
							let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-pencil" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value="' + docDetails[i].fileName + '"> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'">'+docDetails[i].action+'</div></div>'+
									'<div id="validationDiv"></div></div>';

							var tbl = '<tr>'
								+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
								+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_' + i + '" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
								+ '<td><div class="form-group"> <input type="text" value="' + docDetails[i].documentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
								+ '<td>'+a+'</td>'
								+ '</tr>';
							$("#doctbodyData").append(tbl);
						}
					} else {
						
						$("#doctbodyData").empty();
					
						let i = 0;
						let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'"></div></div>'+
									'<div id="validationDiv"></div></div>';
					
						// If no documents exist, display an empty row
						var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
							+ '<td>'+a+'</td>'
							+ '</tr>';

						$("#doctbodyData").append(tbl);
					}
					
					let dataset = [];
					gridSAOptions.api.forEachNode(a=> {
						dataset.push(a.data);
					})
					//console.log('All Shipping Address==',dataset);
					
					let filteredAddress = dataset.filter(f => f.shippingId === quotation.shippingId);
					let firstShippingAddress = filteredAddress && filteredAddress.length > 0 ? filteredAddress[0] : '';
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
							$('#mySAGrid').show();
							$('#shippingAddressSec').addClass('d-none');
							$('#changeAddress').addClass('d-none');
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
					
					priceCalculation();
				}
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching quotation data:", error);
		}
	});
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
						$("#unit").val(allData.productUnit).select2();
						$("#hsnCode").val(allData.hsnCode);
						$("#productName").val(allData.productName);
						$("#itemId").val(allData.productId);
					} else {
						toastr.error("Something Went Wrong!");
						$("#itemName").val("").trigger('change');
						$("#sku").val("");
						$("#unit").val("").select2();
						$("#model").val("");
						$("#unit").val("").select2();
						$("#hsnCode").val("");
						$("#itemId").val("");
					}
				} else {
					toastr.error("Something Went Wrong!");
					$("#itemName").val("").trigger('change');
					$("#skuName").val("");
					$("#itemId").val("");
					$("#model").val("");
					$("#unit").val("").select2();
					$("#hsnCode").val("");
					$("#itemId").val("");
				}
			},
			error: function(e) { }
		});
	}
}
function toggleSection() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#sku").val('');
	$("#editProduct").val('').attr("disabled", false);
	$("#itemId").val('').attr("disabled", false);
	$("#itemName").val('').trigger('change').attr("disabled", false);
	$("#sizeInMM").val('').attr("disabled", false);
	$("#thicknessInMM").val('').attr("disabled", false);
	$("#unit").val('').select2().attr("disabled", false);
	$("#quantity").val('').attr("disabled", false);
	$("#unitPrice").val('').attr("disabled", false);
	$("#gstRate").val('').attr("disabled", false);
	$("#lineTotal").val('').attr("disabled", false);
	$("#itemDesc").val('').attr("disabled", false);
}
function cancelItemDetails() {
	itemOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#sku").val('');
	$("#editProduct").val(null);
	$("#itemId").val('');
	$("#itemName").val('').trigger('change');
	//$("#itemName").text('');
	$("#sizeInMM").val('');
	$("#thicknessInMM").val('');
	$("#unit").val('').select2();
	$("#quantity").val('');
	$("#unitPrice").val('');
	$("#gstRate").val('');
	$("#lineTotal").val('');
	$("#itemDesc").val('');
	$("#hsnCode").val('');
	$("#productName").val('');
	$("#itemId").val('');
	$("#discount").val('');
	$("#gstRate").val('');
	CKEDITOR.instances.itemDesc.setData("");
}
function editItemDetails() {
	toggleSection();
	let selectedData = itemOptions.api.getSelectedRows();
	console.log("Item Details During Edit Time------->", selectedData);
	$("#sku").val(selectedData[0].sku);
	$("#editProduct").val(selectedData[0].slNo);
	$("#itemId").val(selectedData[0].itemId);
	$("#itemName").val(selectedData[0].sku).select2();
	$("#sizeInMM").val(selectedData[0].sizeInMM);
	$("#thicknessInMM").val(selectedData[0].thicknessInMM);
	$("#unit").val(selectedData[0].unit).select2();
	$("#quantity").val(selectedData[0].quantity);
	$("#unitPrice").val(selectedData[0].unitPrice);
	$("#gstRate").val(selectedData[0].gstRate);
	$("#lineTotal").val(selectedData[0].lineTotal);
	// $("#itemDesc").val(selectedData[0].itemDesc);
	$("#charNumSN span").text((selectedData[0].itemDesc || "").length);
	$("#hsnCode").val(selectedData[0].hsnCode);
	let discountValue = selectedData[0].discount;
	$("#discount").val(discountValue);
	
	if (CKEDITOR.instances['itemDesc']) {
		CKEDITOR.instances['itemDesc'].setData(selectedData[0].itemDesc || "");
	}

}
function saveTableData() {
	var editProduct = $("#editProduct").val();
	var item = {};
	var data = 1;
	var validation = true;
	
	let itemDesc = CKEDITOR.instances['itemDesc'].getData();
	itemDesc = itemDesc?.replace(/\s*\n\s*/g, '');

	if (!$('#itemName').val().trim()) {
		toastr.error("Item Name Required");
		validation = false;
		return false;
	}
	if (!$('#sku').val().trim()) {
		toastr.error("Stock Keeping Unit Required");
		validation = false;
		return false;
	}
	if (!$('#unit').val().trim()) {
		toastr.error("Unit Required");
		validation = false;
		return false;
	}
	if (!$('#quantity').val().trim()) {
		toastr.error("Quantity Required");
		validation = false;
		return false;
	}

	if (!$('#unitPrice').val().trim()) {
		toastr.error("Unit Price Required");
		validation = false;
		return false;
	}

	/*if (!$('#lineTotal').val().trim()) {
		toastr.error("Line Total Required");
		validation = false;
		return false;
	}*/

	if (validation) {
		item.slNo = data;
		itemOptions.api.forEachNode(function(rowNode, index) {

			if (!editProduct) {
				data = data + 1;
				item.slNo = data;
			} else {
				item.slNo = editProduct;
			}
		});

		item.itemId = $('#itemId').val();
		item.sku = $('#sku').val();
		item.hsnCode = $('#hsnCode').val();
		item.itemName = $('#itemName option:selected').text();
		item.quantity = $('#quantity').val();
		item.unit = $('#unit').val();
		item.unitName = $("#unit option:selected").text();
		item.unitPrice = $('#unitPrice').val();
		item.discount = $('#discount').val() ? $('#discount').val() : '0';
		item.gstRate = $('#gstRate').val() ? $('#gstRate').val() : '0';
		item.lineTotal = $('#lineTotal').val();
		item.sizeInMM = $('#sizeInMM').val();
		item.thicknessInMM = $('#thicknessInMM').val();
		item.itemDesc = itemDesc;
		var taxType = $("#taxType").val();
		if (taxType == 'true') {

			item.itemCgst = (item.lineTotal * item.gstRate) / 200;
			item.itemSgst = (item.lineTotal * item.gstRate) / 200;
			item.itemIgst = 0;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemCgst
				+ item.itemSgst;


		} else {
			item.itemCgst = 0;
			item.itemSgst = 0;
			item.itemIgst = (item.lineTotal * item.gstRate) / 100;
			item.taxableAmt = parseFloat(item.lineTotal) + item.itemIgst;
		}
		//item.slNo = data;
		var quot = [];

		if (editProduct) {
			var rowNode = itemOptions.api.getRowNode(editProduct);
			rowNode.setData(item);
		} else {
			itemOptions.api.forEachNode(function(rowNode, index) {
				quot.push(rowNode.data);
			});
			quot.push(item)
			itemOptions.api.setRowData(quot);


		}
		priceCalculation();
		cancelItemDetails();
		$("#sku").val('');
		$("#itemId").val('');
		$("#itemName").val('').trigger('change');
		$("#quantity").val('');
		$("#unit").val('').select2();
		$("#unitPrice").val('');
		$("#discount").val('');
		$("#gstRate").val('');
		$("#lineTotal").val('');
		$("#sizeInMM").val('');
		$("#thicknessInMM").val('');
		$("#itemDesc").val('');
		$("#editProduct").val(null);
	}

}
var totalLine;
function calculateLineTotal() {
	var price = $("#unitPrice").val();
	var quantity = $("#quantity").val();
	var discount = $("#discount").val();
	var discountAmount = $("#discountAmount").val();

	if (discountAmount == '1') {
		if (price && quantity) {
			var mul = price * quantity;

			if (discount > mul) {

				$("#discount").val("0");
				totalLine = mul - discount;
				$("#lineTotal").val((mul).toFixed(2));
			} else {
				totalLine = mul - discount;
				$("#lineTotal").val((totalLine).toFixed(2));
			}

		} else {
			var mul = 0;
			$("#lineTotal").val((mul).toFixed(2));

		}
	} else {
		if (price && quantity) {
			var mul = price * quantity;
			if (discount > mul) {

				$("#discount").val("0");
				totalLine1 = (mul / 100) * discount;
				totalLine = mul - totalLine1;
				$("#lineTotal").val((mul).toFixed(2));
			} else {
				totalLine1 = (mul / 100) * discount;
				totalLine = mul - totalLine1;
				$("#lineTotal").val((totalLine).toFixed(2));
			}

		} else {
			var mul = 0;
			$("#lineTotal").val((mul).toFixed(2));

		}
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
	var grandTotal = $("#grandTotal").val();
	if (grandTotal == "" || grandTotal == null || grandTotal == "null") {
		grandTotal = 0;
		$("#grandTotal").val(commaFormattedWithDecimal(grandTotal));
	}
	var subTotal = parseFloat($("#subTotal").val().replace(/,/g, ''));
	var qSGST = parseFloat($("#qSGST").val().replace(/,/g, ''));
	var qCGST = parseFloat($("#qCGST").val().replace(/,/g, ''));
	var qIGST = parseFloat($("#qIGST").val().replace(/,/g, ''));
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
//Sales person auto search

function getSalesPersonList() {
	var search = $("#salespersonName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "view-po-or-wo-get-salesperson-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.message == "success") {
					console.log("response data" + JSON.stringify(response));
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#fff; background-color: #0909e4;" class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
								+ response.body[i].salespersonId + '\',\''
								+ response.body[i].spName + '\')">'
								+ response.body[i].spName + '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);
					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:-30px; font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;" onClick="selectAutocompleteValuee()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box2_").show();
						$("#suggesstion-box2_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	} else {
		$("#suggesstion-box2_").hide();
	}
}


function selectAutocompleteValuee() {
	$("#salesPersonId").val("");
	$("#salespersonName").val("");
	$("#custGSTNoo").val("");
	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();
}

function selectAutocompleteValue2(salespersonId, spName) {
	$("#salesPersonId").val(salespersonId);
	$("#salespersonName").val(spName);
	$("#search").val(spName);
	$("#search").attr('data-procat', salespersonId);
	$("#suggesstion-box2_").hide();
}
function validFormData() {


	var custName = $("#custName").val();
	var poDate = $("#poDate").val();
	var expectedShipmentDate = $("#expectedShipmentDate").val();
	var poNo = $("#poNo").val();
	var orderType = $('#orderType').val();
	var project = $('#project').val();

	if (custName == null || custName.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Customer Name Required');
		return false;
	} if (poNo == null || poNo.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('PO/WO No Required');
		return false;
	}
	if (poDate == null || poDate.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('PO Date Required');
		return false;
	}
	if (expectedShipmentDate == null || expectedShipmentDate.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Expected Delivery Date Required');
		return false;
	}
	if (project == null || project.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Project Required');
		return false;
	}
	if (orderType == null || orderType.trim() == "") {
		nextTab('quotationInfLiId');
		toastr.error('Order Type Required');
		return false;
	}
	return true;

}

function validProductData() {
	return true;
}
function savePoOrWo() {
	if (validProductData() && validFormData()) {
		var datas = [];
		var imageValid = true;
		var uploadList = [];
		var terms = CKEDITOR.instances.termCondition.getData();

		$("#doctbodyData > tr").each(
			function() {
				var uFile = $(this).find(".document")[0]?.files[0];
				var fileName = $(this).find(".document").val();
				var data = [];
				var x = [];
				if (fileName != '' && fileName != 'undefined'
					&& fileName != null) {
					var lastIndex = fileName.lastIndexOf("\\");
					if (lastIndex >= 0) {
						fileName = fileName.substring(lastIndex + 1);
					}
					var reader = new FileReader();
					reader.readAsDataURL(uFile);

					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
					};
				} else {
					if ($("#referenceId").val()) {
						fileName = $(this).find(".uploadHidCls").val();
					} else {
						x = [];
					}

				}
				uploadData = {};
				uploadData['referenceId'] = $("#referenceId").val();
				uploadData['documnentName'] = $(this).find(".docNoclsss").val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = fileName;
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				uploadList.push(uploadData);
				console.log(uploadData)

				/*  if($(this).find(".docNoclss").val()!="" && $(this).find(".docNoclss").val()!='null' && fileName!="" && fileName!="null"){
					uploadList.push(uploadData);
					console.log(uploadData)
					
				}  */

			});

		setTimeout(function() {
			if (itemOptions.api.getDisplayedRowCount() > 0) {
				itemOptions.api.forEachNode(function(rowNode, indrx) {
					var obj = rowNode.data;
					obj.referenceId = $("#referenceId").val();
					obj.version = $("#version").val();
					var version = $("#version").html();
					if (version == '' || version == null) {
						obj.version = 1;
					}
					else {
						obj.version = version;
					}
					obj.quotationId = $("#quotationId").val();
					obj.poNo = $("#poNo").val();
					obj.poRef = $("#poRef").val();
					obj.poDate = $("#poDate").val();
					obj.reference = $('#reference').val()
					obj.custId = $("#custId").val();
					obj.custName = $("#custName").val();
					obj.qutDescription = $("#qutDescription").val();
					obj.subTotal = parseFloat($("#subTotal").val().replace(/,/g, ''));
					obj.grandTotal = parseFloat($("#grandTotal").val().replace(/,/g, ''));
					obj.qIGST = parseFloat($("#qIGST").val().replace(/,/g, '')); 
					obj.qCGST = parseFloat($("#qCGST").val().replace(/,/g, ''));
					obj.qSGST = parseFloat($("#qSGST").val().replace(/,/g, ''));

					var tt = $('#taxType').val();
					if (tt == 'true') {
						obj.taxType = true;
					} else {
						obj.taxType = false;
					}

					/*obj.orderReceiveTime = $('#orderReceiveTime').val();
					obj.qutActive = $("#qutActive:checkbox:checked").val();*/
					obj.expectedShipmentDate = $('#expectedShipmentDate').val();
					obj.paymentTermId = $('#paymentTermId').val();
					obj.orderType = $("#orderType").val();
					obj.deliveryMethodId = $('#deliveryMethodId').val();
					obj.salesPerson = $('#salespersonName').val();
					//obj.salesPerson = $('#salesPersonId').val();
					obj.reference = $("#reference").val();
					obj.terms = terms;
					obj.tcs = $("#tcsId").val();
					obj.tcsAmount = $("#tcsAmount").val();
					obj.shippingHiddenId = $("#shippingHiddenId").val();
					obj.advance = $('#advance').val();
					obj.project = $('#project').val();
					obj.endCustomerName = $('#endCustomerName').val();
					obj.isUpdateSo = $('#updateSOId').is(':checked');
					obj.documentList = uploadList;
					console.log('Object--------For Po------->', obj)
					datas.push(obj);
				});
				console.log(datas)
				savePurchaseOrder(datas);

			} else {
				toastr.error("Items Details Required");
			}
		}, 1000)

	}
}
function savePurchaseOrder(datas) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "view-po-or-wo-add",
		contentType: "application/json",
		data: JSON.stringify(datas),
		success: function(response) {
			if (response.message == "Success") {
				console.log("response=="+response)
				$('.loader').hide();
				let poId = datas.length > 0 ? datas[0].referenceId : "";
				let poRefId = datas.length > 0 ? datas[0].poRef : "";
				 viewPoWo(poId);
				if (!poId && !poRefId) {
					toastr.success('PO/WO Saved Successfully');
				} else if (poId && poRefId) {
					toastr.success('PO/WO Modified Successfully');
				} else {
					toastr.success('PO/WO Revised Successfully');
				}

			} else {
				$('.loader').hide();
				toastr.error(response.code);
			}

		},
		error: function(datas) {
			console.log(datas)
		}
	})

}
function Cancel() {
	//$("#reqTable, .btn-hs, #myGrid, #sendEmail").show();
	//$("#addData, #suggesstion-box1_, #suggesstion-box2_, #suggesstion-box4_").hide();

	let fieldsToClear = [
		"#shippingHiddenId", "#quotationId", "#quotationReference", "#qutName", "#custId", "#custName",
		"#custGSTNo", "#qutValidDate", "#qutDescription", "#qutActive", "#qutUpdatedOn", "#itemId", "#itemName",
		"#quantity", "#unitPrice", "#subject", "#customerAddress", "#project", "#sku", "#itemIgst", "#itemCgst",
		"#itemSgst", "#salespersonName", "#salesPersonId", "#dealName", "#terms", "#version", "#quotationDate",
		"#reference", "#salesPerson", "#qutCreatedBy", "#subTotal", "#grandTotal", "#slNo", "#qutNo", "#draftId",
		"#toDateCalendar", "#quotType", "#projectName","#adjustment"
	];

	fieldsToClear.forEach(field => $(field).val(""));
	itemOptions.api.setRowData();
	//CKEDITOR.instances.qutDescription.setData("");
	CKEDITOR.instances.termCondition.setData("");
	$("#project").val("").trigger('change');
	$("#quotationReference").html("");
	$("#quotationReference").val("");
	$("#version").html("");
	$("#version").val("");
	$('.formValidation').remove();

	$("#doctbodyData").empty().append(
		'<tr>' +
		'<td style="display:none" align="center" class="pdb-24">' +
		'<input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
		'<td style="display:none"><div class="form-group">' +
		'<select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">' +
		'<option value="">Select</option> </select> </div></td>' +
		'<td><div class="form-group">' +
		'<input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>' +
		'<td> <div class="control-group position-r">' +
		'<label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0">' +
		'<i class="ti-plus" id="clickImg_0"></i> </label>' +
		'<div class="controls">' +
		'<input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />' +
		'</div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
		'<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>' +
		'<div id="imageName_0" class="custom-file-upload"></div>' +
		'<input type="hidden" id="editId_0"> </td>' +
		'</tr>'
	);

	//$('#delete, #reqAppvBtn, #copyQuotation, #purchaseOrder').attr("disabled", true);
	// $('#add').attr("disabled", false);
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
function cancel() {
	viewPoWo();
	$('#addPobtn').removeClass('d-none');
	$('#cancelPobtn').addClass('d-none');
}
function addNewpo() {
	$('#updateSOId').prop('checked', false);
	enableFields();
	$("#custName1").html('');
	$("#custName2").html('');
	$("#custName3").html('');
	$("#custName4").html('');
	$('.formValidation').remove();
	let fieldsToClear = [
		"#quotationReference", "#version", "#quotationId", "#shippingHiddenId", "#qutName", "#quotationDate",
		"#custId", "#custName", "#reference", "#salesPerson", "#dealName", "#qutValidDate", "#toDateCalendar",
		"#qutDescription", "#qutActive", "#qutCreatedBy", "#qutUpdatedOn", "#subTotal", "#grandTotal",
		"#slNo", "#sku", "#itemId", "#itemName", "#quantity", "#unitPrice", "#customerNames", "#customerIds",
		"#quotationReference", "#quotationReference", "#poNoheadId", "#discount", "#gstRate", "#lineTotal",
		"#salesOrder", "#salesPersonId", "#salespersonName", "#poDate", "#orderReceiveTime",
		"#expectedShipmentDate", "#paymentTermId", "#orderType", "#deliveryMethodId", "#salesPersonId",
		"#terms", "#qIGST", "#qCGST", "#qSGST", "#itemIgst", "#itemCgst", "#itemSgst", "#adjustment",
		"#tcsAmount", "#quotType", "#customerAddress", "#qutNo", "#draftId", "#approveStatus", "#poNo", "#referenceId", "#advance","#adjustment","#poRef"
	];
	$("#approveStatus").val('');
	$("#endCustomerName").val('');
	$('#poNoheadId').html('');
	$('#version').html('');
	$('#savePoWo').removeClass('d-none')
	updateQuotationDropdown([]);
	fieldsToClear.forEach(field => $(field).val(""));
	$("#project").val("").trigger('change');
	let elementsToHide = ['#mailId', '#mail', '#approveBtn', '#pdfButton', '#shippingAddressId', '#shippingAddress'];
	elementsToHide.forEach(el => $(el).addClass('d-none'));
	itemOptions.api.setRowData();
	CKEDITOR.instances.termCondition.setData("");
	let date = (new Date()).toISOString().split('T')[0];
	let newDate = changeDateFormat(date);
	$("#poDate").val(newDate);
	$("#doctbodyData").empty();
	
	let i = 0;
						let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'"></div></div>'+
									'<div id="validationDiv"></div></div>';
	
	let tbl = `<tr> 
        <td style="display:none" align="center" class="pdb-24">
            <input class="checkCls" type="checkbox" id="check2"><label for="check2"></label>
        </td>
        <td style="display:none">
            <div class="form-group">
                <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">
                    <option value="">Select</option>
                </select>
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" value="" class="form-control docNoclsss" id="docnoid_0">
            </div>
        </td>
        <td>${a}</td></tr>`;

	$("#doctbodyData").append(tbl);
}
function add() {
	gridOptions.api.deselectAll();
	addNewpo();
}

function editPo(referenceId) {
	Cancel();
	nextTab('quotationInfLiId');
	
	$('.loader').show();

	agGrid.simpleHttpRequest({
		url: 'view-po-or-wo-edit-new?id=' + referenceId
	})
		.then(
			function(data) {
				$('.loader').hide();
				console.log(data);
				$("#poNoheadId").html(data[0]?.referenceId);
				$("#referenceId").val(data[0]?.referenceId);
				$("#custId").val(data[0]?.custId);
				$("#custName").val(data[0]?.custName).trigger("change");
				$("#custName1").html(data[0]?.custName);
				$("#custName2").html(data[0]?.custName);
				$("#custName3").html(data[0]?.custName);
				$("#custName4").html(data[0]?.custName);
				let quotationIdArray = data[0]?.quotationId ? data[0]?.quotationId.split(",") : [];
				updateQuotationDropdown(quotationIdArray);
				setTimeout(() => {
					$("#quotationId").val(data[0]?.quotationId).select2();
				}, 500);
				//$("#custName").val(data[0]?.custName);
				$("#shippingHiddenId").val(data[0]?.shippingHiddenId);
				//getAddressDetails(data[0]?.custId)
				getAddressDetails(data[0]?.custId, data[0]?.shippingHiddenId);
				$("#qutDescription").val(data[0]?.qutDescription);
				$("#taxType").val(data[0]?.taxType);



				$("#salesOrder").val(data[0]?.salesOrder);
				$("#endCustomerName").val(data[0]?.endCustomerName);
				//$("#storeId").val(data[0]?.storeId);
				$("#poRef").val(data[0]?.poRef);
				$("#poNo").val(data[0]?.poNo);
				$("#poDate").val(data[0]?.poDate);
				$("#expectedShipmentDate").val(
					data[0]?.expectedShipmentDate);
				$("#paymentTermId").val(data[0]?.paymentTermId);
				$("#deliveryMethodId")
					.val(data[0]?.deliveryMethodId);
				$("#orderType").val(data[0]?.orderType);
				$("#salesPersonId").val(data[0]?.salesPersonId);
				$("#salespersonName").val(data[0]?.salesPerson);
				$("#tcsName").val(data[0]?.tcs);
				$("#tcsId").val(data[0]?.tcsId);
				$("#tcsValue").val(data[0]?.tcsRate);
				$("#tcsAmount").val(data[0]?.tcsAmount);
				//$("#terms").val(data[0]?.terms);
				$("#reference").val(data[0]?.reference);
				$("#grandTotal").val(commaFormattedWithDecimal(data[0]?.grandTotal));
				$("#qSGST").val(commaFormattedWithDecimal(data[0]?.qSGST));
				$("#qCGST").val(commaFormattedWithDecimal(data[0]?.qCGST));
				$("#subTotal").val(commaFormattedWithDecimal(data[0]?.subTotal));
				$("#qIGST").val(commaFormattedWithDecimal(data[0]?.qIGST));
				$("#version").html(data[0]?.version);
				$("#advance").val(data[0]?.advance);
				$("#project").val(data[0]?.project).trigger("change");
				$("#projectName").val(data[0]?.projectName);
				$("#advance").val(data[0]?.advance);
				getEditTaxType(data[0]?.custName);
				//hideShowS();
				itemOptions.api.setRowData(data);
				priceCalculation();
				CKEDITOR.instances['termCondition'].setData(data[0]?.terms);
				$("#doctbodyData").empty();
				var documentList = data[0]?.documentList;
				if (documentList != null && documentList != "") {
					for (var i = 0; i < documentList.length; i++) {
					
						let cls = 'ti-plus';
						
						if(documentList[i].fileName) {
							cls = 'ti-pencil';
						}
					
						let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="'+cls+'" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'">'+documentList[i].action+'</div></div>'+
									'<div id="validationDiv"></div></div>';

						var tbl = '<tr>'
							+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
							+ '<td>'+a+'</td>'
							+ '</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'"></div></div>'+
									'<div id="validationDiv"></div></div>';
				
					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>'+a+'</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}
				disableFields();
			});

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
function approveRequestOnclick() {

	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].referenceId;
	var idRef = selectedRows[0].poRef;
	$.ajax({
		type: "GET",
		url: "view-po-or-wo-approve?id=" + id+"&poRef="+idRef,
		async: false,
		success: function(response) {

			if (response.code == "success") {
				toastr.success('Approved successfully');
				viewPoWo();
				editPo(referenceId);
				setTimeout(() => {
					const allNodes = [];
					gridOptions.api.forEachNode((node) => {
						allNodes.push(node);
					});
					const rowNode = allNodes.find(node => node.data.referenceId == id);
					if (rowNode) {
						rowNode.setSelected(true);
						gridOptions.api.ensureNodeVisible(rowNode);
					} else {
						console.log("Customer row not found in grid.");
					}
				}, 500);
			}

		},
	});
}
function deleteOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.referenceId;
	});
	if (selectedRowsString) {
		var item = {};
		item.referenceId = selectedRowsString;
		$.ajax({
			type: "POST",
			url: "view-po-or-wo-delete",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {

				if (response.message == "Success") {
					toastr.success('Purchase Order Deleted Successfully');
					viewPoWo();
					setTimeout(() => {
						if (gridOptions.api) {
							gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
						}
					}, 500);
				} else {

				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		toastr.error('Please Select Atleast one Record !');
	}

}
function deleteProductOnclick() {
	var selectedRows = itemOptions.api.getSelectedRows();
	itemOptions.api.applyTransaction({
		remove: selectedRows

	});
	toastr.success('Item Deleted Successfully');
	//DELETE GST,SUBTOTAL,GRANDTOTAL,SGST,CGST,IGST
	var sum = parseFloat($("#subTotal").val().replace(/,/g, ''));
	var gstRate = $("#gstRate").val();
	var qCGST = parseFloat($("#qCGST").val().replace(/,/g, ''));
	var qSGST = parseFloat($("#qSGST").val().replace(/,/g, ''));

	var qIGST = parseFloat($("#qIGST").val().replace(/,/g, ''));
	var grandTotal = parseFloat($("#grandTotal").val().replace(/,/g, ''));
	var itemIgst = $("#itemIgst").val();
	var itemCgst = $("#itemCgst").val();
	var itemSgst = $("#itemSgst").val();
	var len = selectedRows.length;
	for (var i = 0; i < len; i++) {

		sum = sum - selectedRows[i].lineTotal;
		var taxType = $("#taxType").val()
		if (taxType == "true") {
			selectedRows[i].itemIgst = selectedRows[i].lineTotal
				* selectedRows[i].gstRate / 100;
			qIGST = qIGST - selectedRows[i].itemIgst;
			qCGST = qCGST - selectedRows[i].itemCgst;
			qSGST = qSGST - selectedRows[i].itemSgst;
			grandTotal = sum + qIGST;
			var gTotal1 = Math.round(grandTotal);
			var adjustment = (gTotal1 - grandTotal);

		} else {
			selectedRows[i].itemIgst = selectedRows[i].lineTotal
				* selectedRows[i].gstRate / 100;
			qIGST = qIGST - selectedRows[i].itemIgst;
			qCGST = qCGST - selectedRows[i].itemCgst;
			qSGST = qSGST - selectedRows[i].itemSgst;
			grandTotal = sum + qIGST;
			var gTotal1 = Math.round(grandTotal);
			var adjustment = (gTotal1 - grandTotal);


		}

	}
	console.log(`item gst ${itemIgst} , item cgst${itemCgst} ,item sgst ${itemSgst}, sum ${sum} ,qigst ${qIGST},qcgst ${qCGST} ,qSGST ${qSGST}`)
	$("#itemIgst").val(itemIgst);
	$("#itemCgst").val(itemCgst);
	$("#itemSgst").val(itemSgst);
	$("#subTotal").val(commaFormattedWithDecimal(sum));
	$("#qIGST").valcommaFormattedWithDecimal((qIGST));
	$("#qCGST").val(commaFormattedWithDecimal(qCGST));
	$("#qSGST").val(commaFormattedWithDecimal(qSGST));
	$("#grandTotal").val(commaFormattedWithDecimal(gTotal1))
	if (adjustment > 0) {
		$("#adjustment").val(commaFormattedWithDecimal(adjustment))
	}
	else {
		var adjust = Math.abs(adjustment);
		$("#adjustment").val(commaFormattedWithDecimal(adjust))
	}
	priceCalculation();
	//tcsCalculation();
	//calculateAdjustment();
}
function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}

function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}
function disableFields() {
	let fieldIds = ["custName", "quotationId", "endCustomerName", "qutValidDate", "project", "poNo", "poDate", "subject", "expectedShipmentDate", "paymentTermId", "deliveryMethodId", "project", "orderType", "advance", "salespersonName","savePoWo"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	/* setTimeout(() => {
		console.log("Disabling dynamically added fields...");
		$("#doctbodyData").find("input, select, textarea, button").each(function () {
			$(this).prop("disabled", disable);
		});
	}, 300); */ // Increased delay to ensure elements are added

	// ✅ Ensure CKEditor Fields Are Ready Before Disabling
	//CKEDITOR.instances.qutDescription?.setReadOnly(true);
	// CKEDITOR.instances.termCondition?.setReadOnly(true);
	if (CKEDITOR.instances.termCondition) {
	    CKEDITOR.instances.termCondition.setReadOnly(true);
	} else {
	    CKEDITOR.on('instanceReady', function (evt) {
	        if (evt.editor.name === 'termCondition') {
	            evt.editor.setReadOnly(true);
	        }
	    });
	}
	

}
function enableFields() {
	let fieldIds = ["custName", "quotationId", "endCustomerName", "qutValidDate", "project", "poNo", "poDate", "subject", "expectedShipmentDate", "paymentTermId", "deliveryMethodId", "project", "orderType", "advance", "salespersonName","savePoWo"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	/* setTimeout(() => {
		console.log("Disabling dynamically added fields...");
		$("#doctbodyData").find("input, select, textarea, button").each(function () {
			$(this).prop("disabled", disable);
		});
	}, 300); */ // Increased delay to ensure elements are added

	// ✅ Ensure CKEditor Fields Are Ready Before Disabling
	CKEDITOR.instances.qutDescription?.setReadOnly(false);
	CKEDITOR.instances.termCondition?.setReadOnly(false);
	
	$('#poApproveBtn').addClass('d-none');
	$('#deleteQuot').addClass('d-none');
	$('#addPobtn').addClass('d-none');
	$('#editPo').addClass('d-none');
	
}

function disableOthersFields() {
	let fieldIds = ["custName", "quotationId", "endCustomerName", "qutValidDate", "project", "poNo", "poDate", "subject", "expectedShipmentDate", "paymentTermId", "deliveryMethodId", "project", "orderType", "advance", "salespersonName","savePoWo"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	
	$('#poApproveBtn').addClass('d-none');
	$('#deleteQuot').addClass('d-none');
	$('#addPobtn').addClass('d-none');
	$('#editPo').addClass('d-none');
	
}

function indianNumberFormatterWithDecimal(params) {
	if (params.value === null || params.value === '' || params.value === undefined || isNaN(params.value)) return "0.00";

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

function closeTooltip(button) {
    let tooltip = bootstrap.Tooltip.getInstance(button);
    if (tooltip) {
        tooltip.hide();
    }
}

/*function closeAddress() {
	$('#mySAGrid').hide();
    $('#shippingAddressSec,#addAddress').removeClass('d-none');
    $('#changeAddress').removeClass('d-none');
    $('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
}*/





function changeAddress() {

	let custId = $("#custId").val();
	if (!custId) {
		toastr.error('Customer Name Required');
		//nextTab('quotationInfLiId');
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
		//nextTab('quotationInfLiId');
		return;
	}

	

	$('#mySAGrid').hide();
	$('#shippingAddressSec').addClass('d-none');
	$('#changeAddress,#addAddress').addClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').removeClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
}

function closeAddress() {
	$('#mySAGrid').hide();
	$('#shippingAddressSec,#addAddress').removeClass('d-none');
	$('#changeAddress').removeClass('d-none');
	$('#addshippingAddressSec,#closeAddress,#saveAddress').addClass('d-none');
	$("#states2").empty();
	$("#states2").append("<option value=''>Select</option>");
	$("#shippingHiddenId2,#shippingId2,#country2,#states2,#city2,#street12,#street22,#zipCode2,#phone2,#fax2,#gstIn2").val('');
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

function checkAlphabet(fieldId) {

	var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z. ]/g, '');
	tempVal = tempVal.replace(/^\w/, c => c.toUpperCase());

	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	if (position == 1 && tempVal.charAt(0) == ' ') {
		$("#" + fieldId).empty();
		tempVal = '';
	}
	$("#" + fieldId).val(tempVal);
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

//Amount validation
function checkAmount(fieldId) {
	var myField = document.getElementById("openingBalance")
	var reg = /^\d{0,9}(\.\d{0,2})?$/;
	if (reg.test(myField.value)) {
		$("#" + fieldId).val();
		reg = '';
	} else {
		$("#" + fieldId).val(null);
	}
}
function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function saveAddress() {

	let fax = $("#fax2").val() ? $("#fax2").val() : "";
	let gstIn = $("#gstIn2").val() ? $("#gstIn2").val() : "";
	let city = $("#city2").val();
	let phone = $("#phone2").val() ? $("#phone2").val() : "";
	let state = $("#states2").val();
	let stateName = $("#states2 option:selected").text();
	let country = $("#country2").val();
	let countryName = $("#country2 option:selected").text();
	let zipcode = $("#zipCode2").val();
	let street1 = $("#street12").val();
	let street2 = $("#street22").val() ? $("#street22").val() : "";

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
			url: "view-po-or-wo-add-shipping-address",
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

function getStateDetails2() {

	var cname = $('#country2').val();

	$("#states2").empty();
	$("#states2").append('<option value="">Select</option>');

	if (cname) {
		$.ajax({
			type: "GET",
			url: "view-po-or-wo-stateListData?id=" + cname,
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
//function for download
function excelDownload() {
	var params = {
		fileName: 'PO/WO_list.csv', // Specify your custom filename here
	};
	gridOptions.api.exportDataAsCsv(params);
}
 
function revision() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var referenceIdd = selectedData[0].referenceId;
	Cancel();
	nextTab('quotationInfLiId');
	// enableFields();
	disableOthersFields();
		$('#addPobtn').addClass('d-none');
		$('#revisionBtn').addClass('d-none');
		$('#savePoWo').removeClass('d-none');
		$('#cancelPobtn').removeClass('d-none');
		$('#savePoWo').attr("disabled", false);
		$("#approveStatus").val("Revised");
	$('.loader').show();

	agGrid.simpleHttpRequest({
		url: 'view-po-or-wo-edit-new?id=' + referenceIdd
	})
		.then(
			function(data) {
				$('.loader').hide();
				console.log(data);
				$('#updateSODiv').removeClass('d-none');
				$("#poNoheadId").html('');
				$("#referenceId").val('');
				$("#custId").val(data[0]?.custId);
				$("#custName").val(data[0]?.custName).trigger("change");
				$("#custName1").html('');
				$("#custName2").html('');
				$("#custName3").html('');
				$("#custName4").html('');
				let quotationIdArray = data[0]?.quotationId ? data[0]?.quotationId.split(",") : [];
				updateQuotationDropdown(quotationIdArray);
				setTimeout(() => {
					$("#quotationId").val(data[0]?.quotationId).select2();
				}, 500);
				//$("#custName").val(data[0]?.custName);
				$("#shippingHiddenId").val(data[0]?.shippingHiddenId);
				//getAddressDetails(data[0]?.custId)
				getAddressDetails(data[0]?.custId, data[0]?.shippingHiddenId);
				$("#qutDescription").val(data[0]?.qutDescription);
				$("#taxType").val(data[0]?.taxType);
 
				$("#poRef").val(data[0]?.poRef);
				$("#poNo").val(data[0]?.poNo);
				$("#poDate").val(data[0]?.poDate);
				$("#expectedShipmentDate").val(
					data[0]?.expectedShipmentDate);
				$("#paymentTermId").val(data[0]?.paymentTermId);
				$("#deliveryMethodId")
					.val(data[0]?.deliveryMethodId);
				$("#orderType").val(data[0]?.orderType);
				$("#salesPersonId").val(data[0]?.salesPersonId);
				$("#salespersonName").val(data[0]?.salesPerson);
				$("#tcsName").val(data[0]?.tcs);
				$("#tcsId").val(data[0]?.tcsId);
				$("#tcsValue").val(data[0]?.tcsRate);
				$("#tcsAmount").val(data[0]?.tcsAmount);
				//$("#terms").val(data[0]?.terms);
				$("#reference").val(data[0]?.reference);
				$("#grandTotal").val(commaFormattedWithDecimal(data[0]?.grandTotal));
				$("#qSGST").val(commaFormattedWithDecimal(data[0]?.qSGST));
				$("#qCGST").val(commaFormattedWithDecimal(data[0]?.qCGST));
				$("#subTotal").val(commaFormattedWithDecimal(data[0]?.subTotal));
				$("#qIGST").val(commaFormattedWithDecimal(data[0]?.qIGST));
				$("#version").html(data[0]?.version);
				$("#advance").val(data[0]?.advance);
				$("#project").val(data[0]?.project).trigger("change");
				$("#projectName").val(data[0]?.projectName);
				$("#advance").val(data[0]?.advance);
				getEditTaxType(data[0]?.custName);
				//hideShowS();
				itemOptions.api.setRowData(data);
				priceCalculation();
				CKEDITOR.instances['termCondition'].setData(data[0]?.terms);
				$("#doctbodyData").empty();
				var documentList = data[0]?.documentList;
				if (documentList != null && documentList != "") {
					for (var i = 0; i < documentList.length; i++) {
					
						let cls = 'ti-plus';
						
						if(documentList[i].fileName) {
							cls = 'ti-pencil';
						}
					
						let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="'+cls+'" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value="' + documentList[i].fileName + '"> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'">'+documentList[i].action+'</div></div>'+
									'<div id="validationDiv"></div></div>';

						var tbl = '<tr>'
							+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + documentList[i].documnentName + '" class="form-control docNoclsss" id="docnoid_' + i + '"> </div></td>'
							+ '<td>'+a+'</td>'
							+ '</tr>';

						$("#doctbodyData").append(tbl);
					}
				} else {
					
					let i = 0;
					let a = '<div class="form-group d-flex"><div class="">'+
									'<label class="input-group-text btn go-btn h-auto" for="uploadDoc_' + i + '" id="uploadFor_' + i + '"> <i class="ti-plus" id="clickImg_' + i + '"></i> </label> '+
									'<input type="file" class="form-control document" id="uploadDoc_' + i + '" accept=".jpeg, .jpg, .png, .pdf, .csv, .xls, .xlsx,application/msword, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="saveMultiFile(event)">'+
									'</div> <input type="hidden" id="uploadHidden_'+i+'" class="uploadHidCls" value=""> <div class="uploadedBillCls mt-2">'+
	                        		'<div class="d-flex gap-2" style="align-items: center;" id="uploadedBillDiv_'+i+'"></div></div>'+
									'<div id="validationDiv"></div></div>';
				
					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclsss" id="docnoid_"></div></td>'
						+ '<td>'+a+'</td>'
						+ '</tr>';
					$("#doctbodyData").append(tbl);
				}
			});

}

//
var pno = "";
function filterView(){
	var pages;
	var pageno = pno;
	
	var fDate = $("#fromDateQT").val();
	var tDate = $("#toDateQT").val();
	
	if (fDate && tDate) {
        var fromDateObj = new Date(fDate.split("-").reverse().join("-")); // assumes DD-MM-YYYY
        var toDateObj = new Date(tDate.split("-").reverse().join("-"));

        if (toDateObj < fromDateObj) {
            toastr.error("Please choose 'To Date' greater than or equal to 'From Date'");

            // Clear grid data if invalid date
            gridOptions.api.setRowData([]);
			add();
          //  $('#totalCandidate').find('span').html(0);
           // $('#totalPageno').val(0);
			
            return; // Stop execution
        }
    }
	
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url : "view-po-or-wo-filter-data?pageno=" + pageno + "&fDate=" + fDate + "&tDate=" + tDate,
	}).then(function(data) {
		$(".loader").hide();
		        var jsonData = JSON.parse(data.body);
        var allData = jsonData?.viewPoWo;

        if (allData && allData.length > 0) {
            gridOptions.api.setRowData(allData);

           // var totalRowCount = gridOptions.api.getModel().getRowCount();
           // $('#totalCandidate').find('span').html(totalRowCount);

            //$('#totalPageno').val(allData[0].totalPageno);
          //  pages = allData[0].totalPageno;

            setTimeout(function() {
                if (typeof poId !== 'undefined' && poId) {
                    gridOptions.api.forEachNode(node => {
                        if (node.data.referenceId === poId) {
                            node.setSelected(true);
                        }
                    });
                } else {
                    const firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
                    if (firstRowNode) {
                        firstRowNode.setSelected(true);
                    }
                }
            }, 100);

        } else {
            gridOptions.api.setRowData([]);
			add();
          //  $('#totalCandidate').find('span').html(0);
            //$('#totalPageno').val(0);
        }
    });

}