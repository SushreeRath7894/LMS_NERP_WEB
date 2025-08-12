$(()=>{
	$('#carrierId').select2();  //#carrierId
	$('#country1').select2();  //#country1
	$('#states1').select2();  //#states1
});

$(function() {
	disableFields();
	pno = 1;
	viewShipment();
	var gridDiv = document.querySelector('#shipmentGrid');
	new agGrid.Grid(gridDiv, gridOptionsShipment);

	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);
	$('#mySAGrid').hide();

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
});
var pno;
function viewDchallan() {
	var pages;
	var pageno = pno;

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-deliverychallan-through-ajax?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewDchallan;
		/* var len = allData.length;
		$('#totalItem').find('span').html(len); */
		gridOptionsChallan.api.setRowData(allData);

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
var columnDefsShipment = [
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
		headerName: "Shipment Id",
		field: "salesShipmentId",
		pinned: 'left',
		width: 200,

	}, {
		headerName: 'Shipment Date',
		field: "qutUpdatedOn",
		width: 150,
	}, {
		headerName: 'Customer Name',
		field: "custName",
		width: 200,

	}, {
		headerName: 'Sales Order Id',
		field: "poId",
		width: 150,

	}, {
		headerName: 'Challan Id',
		field: "saleDeliveryChallan",
		width: 150,
	}, {

		headerName: 'Carrier',
		field: "carrierId",
		width: 150,
	}, {
		headerName: 'Tracking Id',
		field: "trackingId",
		width: 170,
	}, {
		headerName: 'Status',
		field: "shipmentStatus",
		width: 150,
		cellStyle: {
			textAlign: 'center'
		}
		/* }, {
			headerName : 'Shipping Charge',
			field : "shippingCharge",
			width : 150, */

	}];

var gridOptionsShipment = {
	columnDefs: columnDefsShipment,
	rowSelection: 'single',

	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 19,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 251,
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
};

var did = "";
var rowCount = 0;
function rowSelect() {
	var selectedRows = gridOptionsShipment.api.getSelectedRows();

	if (selectedRows.length === 0) {
		newShipment();
		$('#cancelShipment').removeClass('d-none');
		$('#deleteShipment').addClass('d-none');
		$('#editShipment').addClass('d-none');
		$('#addShipment').addClass('d-none');
		$("#showCustName1").html('')
		return;
	} else {
		$("#showCustName1").html(selectedRows[0]?.custName)
		$('#deleteShipment').removeClass('d-none');
		$('#editShipment').removeClass('d-none');
		$('#addShipment').removeClass('d-none');
		$('#cancelShipment').addClass('d-none');
	}

	var selectedNodes = gridOptionsShipment.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var shipmentId = selectedData[0].salesShipmentId;

	editSalesShipment(shipmentId);
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
var pno;
function viewShipment() {
	var pages;
	var pageno = pno;

	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "view-shipments-through-ajax?pageno=" + pageno,
	}).then(function(data) {
		$(".loader").hide();
		var jsonData = JSON.parse(data.body);
		var allData = jsonData.viewShipment;
		/* var len = allData.length;
		$('#totalItem').find('span').html(len); */
		gridOptionsShipment.api.setRowData(allData);

		var totalRowCount = gridOptionsShipment.api.getModel().getRowCount();
		$('#totalCandidate').find('span').html(totalRowCount);
	});
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
						$("#country1").val(selectedShippingAddress.country).trigger("change").attr("disabled", true);
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
	console.log("DATA@@@@@@@@@@@@@@@@@@",selectedData);
	$("#shippingHiddenId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).trigger("change").attr("disabled", true); 
	

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
			url: "view-shipments-get-po?custId=" + custId,
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
function updatePoDropdown(po) {
	var $dropdown = $("#purchaseOrderId");
	$dropdown.empty();
	$dropdown.append('<option value="">Select SO</option>');
	po.forEach(function(po) {
		$dropdown.append('<option value="' + po + '">' + po + '</option>');
	});
}
function onPoChange(poId) {
	$.ajax({
		type: "GET",
		url: "view-shipments-get-challan?poId=" + poId,
		dataType: "json",
		success: function(response) {
			console.log("Package Data:", response);
			if (response && response.body) {
				updateChallanDropdown(response.body);
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching package data:", error);
		}
	});
}
function updateChallanDropdown(challan) {
	var $dropdown = $("#deliveryChallan");
	$dropdown.empty();
	$dropdown.append('<option value="">Select Challan</option>');
	challan.forEach(function(po) {
		$dropdown.append('<option value="' + po + '">' + po + '</option>');
	});
}
function newShipment() {
	enableFields();
	$("#showCustName1").html('')
	let fieldsToClear = [
		"#orderReceiveDate", "#salesReferenceId", "#purchaseOrderId", "#deliveryChallan", "#shipmentOrder", "#carrierId",
		"#custId", "#custName", "#trackingId", "#trackingUrlId", "#contactNo", "#internalNotes", "#salesShipmentId"];
	$('#salesShipmentId').html('');
	updatePoDropdown([]);
	updateChallanDropdown([]);
	fieldsToClear.forEach(field => $(field).val(""));
	const date = new Date();
	let day = date.getDate();
	let month = date.getMonth() + 1;
	let year = date.getFullYear();
	let qutUpdatedOn = `${day}-${month}-${year}`;
	$("#shipmentDate").val(qutUpdatedOn);
	$.ajax({
		type: "GET",
		url: "view-shipments-get-insertedid",
		success: function(response) {
			if (response.message == "success") {
				$("#shipmentOrder").val(response.body[0].key);
			}
		},
		error: function(e) {
		}
	});
	$("#country1").val("").attr("disabled", true);
	$("#states1").val("").attr("disabled", true);
	$("#city1").val("").attr("disabled", true);
	$("#street11").val("").attr("disabled", true);
	$("#street21").val("").attr("disabled", true);
	$("#zipCode1").val("").attr("disabled", true);
	$("#phone1").val("").attr("disabled", true);
	$("#fax1").val("").attr("disabled", true);
	gridSAOptions.api.setRowData([]);
}
function add() {
	gridOptionsShipment.api.deselectAll();
	newShipment();
}
function cancelChallan() {
	if (gridOptionsShipment.api) {
		gridOptionsShipment.api.getDisplayedRowAtIndex(0)?.setSelected(true);
	}
	$('#addShipment').removeClass('d-none');
	$('#cancelShipment').addClass('d-none');
}
function validFormData() {
	var allValid = true;
	var poId = $("#purchaseOrderId").val();
	var deliveryChallan = $("#deliveryChallan").val();
	var custName = $("#custName").val();
		if (custName == null || custName.trim() == "") {
			nextTab('salesInfLiId');
			toastr.error('Customer name is required');
			return false;
		}
	if (!poId) {
		toastr.error('Sales Order Id Required');
		allValid = false;
		return false;
	}
	if (!deliveryChallan) {
		toastr.error('Delivery Challan Required');
		allValid = false;
		return false;
	}
	return allValid;
}

function validProductData() {
	return true;
}
function saveShipment() {
	if (validProductData() && validFormData()) {
		var datas = [];
		var obj = {};
		obj.salesShipmentId = $("#salesShipmentId").val();
		obj.poId = $("#purchaseOrderId").val();
		obj.saleDeliveryChallan = $("#deliveryChallan").val();
		obj.shipmentOrder = $("#shipmentOrder").val();
		obj.custId = $('#custId').val();
		obj.custName = $('#custName').val();
		obj.carrierId = $('#carrierId').val();
		obj.trackingId = $('#trackingId').val();
		obj.trackingUrlId = $('#trackingUrlId').val();
		obj.contactNo = $('#contactNo').val();
		obj.internalNotes = $('#internalNotes').val();
		obj.shippingHiddenId = $("#shippingHiddenId").val();
		//obj.project = $("#projectId").val();

		datas.push(obj);
		console.log(datas);
		saveAllShipments(datas);
	}
}

function saveAllShipments(datas) {
	console.log(JSON.stringify(datas))
	$('.loader').show();
	$
		.ajax({

			type: "POST",
			url: "view-shipments-add",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					toastr.success('Data saved successfully');
					viewShipment();
					let shipmentId = datas.length > 0 ? datas[0].salesShipmentId : "";

					setTimeout(() => {
						gridOptionsShipment.api.forEachNode((node) => {
							if (shipmentId == "") {
								let firstRow = gridOptionsShipment.api.getDisplayedRowAtIndex(0);
								if (firstRow) {
									firstRow.setSelected(true);
								}
							} else if (node.data.salesShipmentId == shipmentId) {
								node.setSelected(true);
							}
						});
					}, 500);
				}

			},
			error: function(datas) {
				console.log(datas)
				$('.loader').hide();
			}
		})

}
function editSalesShipment(salesShipmentId, poId, shipmentStatus) {
	disableFields();
	agGrid.simpleHttpRequest({
		url: 'view-shipments-edit-new?id=' + salesShipmentId
	}).then(
		function(data) {
			//getPakageEdit(data[0].salesOrderId,data[0].salePackageId);

			console.log("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + data);

			$("#saleDeliveryChallanId").val(data[0].saleDeliveryChallan);
			$("#salesShipmentId").val(data[0].salesShipmentId);
			$("#taxtype").val(data[0].taxType);
			$("#custId").val(data[0].custId);
			$("#custName").val(data[0].custName).trigger("change");
			$("#shippingHiddenId").val(data[0].shippingHiddenId);
			let poIdArray = data[0].poId ? data[0].poId.split(",") : [];
			updatePoDropdown(poIdArray);
			setTimeout(() => {
				$("#purchaseOrderId").val(data[0].poId);
			}, 500);
			let challanIdArray = data[0].saleDeliveryChallan ? data[0].saleDeliveryChallan.split(",") : [];
			updateChallanDropdown(challanIdArray);
			setTimeout(() => {
				$("#deliveryChallan").val(data[0].saleDeliveryChallan);
			}, 500);
			getAddressDetails(data[0].custId, data[0].shippingHiddenId);
			$("#shipmentOrder").val(data[0].shipmentOrder);
			$("#carrierId").val(data[0].carrierId).trigger("change");
			$("#shipmentDate").val(data[0].shipmentDate);
			$("#trackingId").val(data[0].trackingId);
			$("#trackingUrlId").val(data[0].trackingUrlId);
			$("#contactNo").val(data[0].contactNo);
			$("#internalNotes").val(data[0].internalNotes);

		});


}
function disableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "deliveryChallan", "carrierId", "shipmentDate", "shipmentDate", "trackingId", "trackingUrlId", "contactNo", "internalNotes"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});

}
function enableFields() {
	let fieldIds = ["custName", "purchaseOrderId", "deliveryChallan", "carrierId", "shipmentDate", "shipmentDate", "trackingId", "trackingUrlId", "contactNo", "internalNotes"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
}
function deleteChallanOnclick() {

	Swal.fire({
				title: 'Are you sure?',
				text: 'Do you really want to delete this shipment?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: 'Yes, delete it!',
				cancelButtonText: 'No, keep it',
				confirmButtonColor: '#d33',
				cancelButtonColor: '#3085d6',
			}).then((result) => {
				if (result.value) {
					var selectedRows = gridOptionsShipment.api.getSelectedRows();
					var id = selectedRows[0].salesShipmentId;
					var selectedRowsString = '';
					selectedRows.forEach(function(selectedRow, index) {
						if (index > 0) {
							selectedRowsString += ',';
						}
						selectedRowsString += selectedRow.salesShipmentId;
					});
					
					if (selectedRowsString) {
						var item = {};
						item.salesShipmentId = selectedRowsString;
				
						$.ajax({
							type: "POST",
							url: "view-shipments-delete?id=" + id,
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify(item),
							success: function(response) {
				
								if (response.message == "success") {
									console.log("response.messageresponse.message",response.message);
									viewShipment();
									toastr.success('Data Deleted Successfully');
									if (gridOptionsShipment.api) {
										gridOptionsShipment.api.getDisplayedRowAtIndex(0)?.setSelected(true);
									}
									 setTimeout(() => {
								        if (gridOptionsShipment.api) {
								            gridOptionsShipment.api.deselectAll();
								
								            const firstRowNode = gridOptionsShipment.api.getDisplayedRowAtIndex(0);
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

function enableFieldsEdit() {
	let fieldIds = ["custName", "purchaseOrderId", "deliveryChallan", "carrierId", "shipmentDate", "shipmentDate", "trackingId", "trackingUrlId", "contactNo", "internalNotes"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", false);
	});
	$('#cancelShipment').removeClass('d-none');
	$('#editShipment').addClass('d-none');
	$('#deleteShipment').addClass('d-none');
	$('#addShipment').addClass('d-none');
}

function cancelShipment() {
	if (gridOptionsShipment.api) {
		gridOptionsShipment.api.getDisplayedRowAtIndex(0)?.setSelected(true);
	}
	let fieldIds = ["custName", "purchaseOrderId", "deliveryChallan", "carrierId", "shipmentDate", "shipmentDate", "trackingId", "trackingUrlId", "contactNo", "internalNotes"];
	fieldIds.forEach(id => {
		$("#" + id).prop("disabled", true);
	});
	$('#addShipment').removeClass('d-none');
	$('#cancelShipment').addClass('d-none');
	$('#editShipment').removeClass('d-none');
	$('#deleteShipment').removeClass('d-none');
	
}