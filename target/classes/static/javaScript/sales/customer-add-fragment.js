$(() => {
	$('#customerType').select2();  //#customerType
	$('#status').select2();  //#status
	$('#currency').select2();  //#currency
	$('#portableLang').select2();  //#portableLang
	$('#country').select2();  //#country
	$('#country1').select2();  //#country1
	$('#states').select2();  //#states
	$('#states1').select2();  //#states1
});

$(function() {

	$("input[name=ReminderYesOrNo]:radio").click(function() {
		if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
			$('.reminderBtn').show();

		} else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
			$('.reminderBtn').hide();
			$('#reminderDateid').val("");
			$('#reminderTime').val("");
			$('#taskAlertBy').val("");

		}
	});

	var dateFormat = localStorage.getItem("dateFormat");
	$("#startDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#startDate').val($(this).val());
	})

	$('#startDate').blur(function() {
		$("#startDateCalendar").val($(this).val());
	})

	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})

	$('#endDate').blur(function() {
		$("#endDateCalendar").val($(this).val());
	})

	$("#toDateCalendarTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	}).on("change", function() {
		$('#reminderTime').val($(this).val());
	})

	$('#reminderTime').blur(function() {
		$("#toDateCalendarTime").val($(this).val());
	})
	$(".br-dis").attr("disabled", true);
 
});

$(function() {

	/*var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);*/
	var gridDiv = document.querySelector('#mySAGrid');
	new agGrid.Grid(gridDiv, gridSAOptions);
	var gridDiv3 = document.querySelector('#myGridPocDtls');
	new agGrid.Grid(gridDiv3, gridOptionsPocDtls);
	gridOptionsPocDtls.api.setRowData([]);
	gridSAOptions.api.setRowData([]);
	/*agGrid.simpleHttpRequest({
		url: "/sales/view-customer-throughAjax"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);

		if (data && data.length > 0) {
			gridOptions.api.setRowData(data);
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		} else {
			gridOptions.api.setRowData([]);
			addNewCust()
		}


	});*/
	/* setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 1000); */

	/* agGrid.simpleHttpRequest({
		url : "view-customer-shippingdetails"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);
	
		gridSAOptions.api.setRowData(data);
	
	});
	 */


	$("#myGrid").show();
	$("#delete").attr("disabled", true);
});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})
//search bar

/* function onQuickFilterChanged() {
	gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
	var totalRowCount = gridOptions.api.getModel().getRowCount();
	
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
} */

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

$('input,textarea').focus(
	function() {
		$(this).data('placeholder', $(this).attr('placeholder')).attr(
			'placeholder', '');
	}).blur(function() {
		$(this).attr('placeholder', $(this).data('placeholder'));
	});

var count = 0;
function allCheck() {
	count++;

	if (count == 1) {
		$('.checkCls').prop("checked", true);
	} else {
		count = 0;
		$('.checkCls').prop("checked", false);
	}
}

var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
}
// column Defs
var columnDefsPocDtls = [{
	headerCheckboxSelection: false,
	checkboxSelection: true,
	width: 8,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left',
},
{
	headerName: " Poc Id",
	field: "id",
	hide: true,

},
{
	headerName: 'First Name',
	field: "firstName",
	flex: 1
}, {
	headerName: "Last Name",
	field: "lastName",
	flex: 1
}, {
	headerName: "Email",
	field: "emailAdd",
	flex: 1,
}, {
	headerName: "Mobile",
	field: "mobile",
	flex: 1,
}
];
var gridOptionsPocDtls = {
	columnDefs: columnDefsPocDtls,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowPocSelect,
	getRowNodeId: function(data) {
		return data.id;
	},
};
function rowPocSelect() {
	let selectedData = gridOptionsPocDtls.api.getSelectedRows();
	if (selectedData && selectedData.length > 0) {
		$(".br-dis-poc").attr("disabled", false);
	} else {
		$(".br-dis-poc").attr("disabled", true);
	}

}
var columnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',

	},
	{
		headerName: 'Customer Id',
		field: "customerId",
		pinned: 'left',
		width: 120
		/* cellRenderer: function (params) {
			return '<a onclick=editPage("' + params.data.customerId
				+ ',' + '0' + '") href="javascript:void(0)">'
				+ params.data.customerId + '</a>';

			/* <a onclick=editPage("' + params.data.customerId + '") href="javascript:void(0)">' + params.data.customerId + '</a>'; 
		} */
	},

	{
		headerName: "Customer Name",
		field: "customerDisplayName",
		pinned: 'left',
		cellStyle: {
			textAlign: 'left'
		}

	}, {
		headerName: "Pan No.",
		field: "pan",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Gst No.",
		field: "gstNo",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}

	}, {
		headerName: "Country",
		field: "country",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "State",
		field: "states",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "City",
		field: "city",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Address",
		field: "street1",
		width: 120,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Email",
		field: "cusEmail",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Work Phone",
		field: "custMobile",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Customer Type",
		field: "customerType",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Payment Term",
		field: "paymentTerms",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Receivable",
		field: "openingBalance",
		width: 100,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Status",
		field: "status",
		width: 80,
		cellStyle: {
			textAlign: 'left'
		}
	}];

var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.customerId;
	},
	pagination: true,  // Enable pagination
	paginationPageSize: 15,
	onGridReady: function(params) {
		setTimeout(() => {
			const firstRow = params.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				params.api.selectNode(firstRow, true);
			}
		}, 100);
	}
};





var columnDefsa = [
	{
		headerCheckboxSelection: false,
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
	},{
		headerName: "GSTIN",
		field: "gstIn",
		cellStyle: {
			textAlign: 'left'
		}
	},  {
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

var gridSAOptions = {
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

var deleteId = "";
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	deleteId = "";
	if (selectedRows.length === 0) {
		newCustomer();
		$('#cancelCustBtn').removeClass('d-none');
		$('#deleteCustBtn').addClass('d-none');
		$('#editCustBtn').addClass('d-none');
		$('#addCustBtn').addClass('d-none');
		return;
	} else {
		$('#deleteCustBtn').removeClass('d-none');
		$('#editCustBtn').removeClass('d-none');
		$('#addCustBtn').removeClass('d-none');
		$('#cancelCustBtn').addClass('d-none');
	}
	if (selectedRows.length > 0) {
		let firstSelectedCustomerId = selectedRows[0].customerId;

		editPage(firstSelectedCustomerId);
	}

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '"' + selectedRows[i].customerId + '",';
	}
	deleteId = deleteId.substring(0, deleteId.length - 1);
	console.log(deleteId);

	if (selectedRows.length > 0) {

	} else {

	}
}


function rowSelectAddress() {
	var selectedRows = gridSAOptions.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$(".br-dis").attr("disabled", false);
		$("#addAddressBtn").attr("disabled", true);

	} else {
		$(".br-dis").attr("disabled", true);
		$("#addAddressBtn").attr("disabled", false);
	}
}
// for new button
function newBtn() {
	$('.formValidation').remove();
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").show();
	$("#save").hide();
	$("#next").show();

	$("#remarksAcc").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").show();
	$("#mainSa").hide();
	$("#contactPersonAcc").hide();
	$("#remarksAcc").hide();
	$("#btnDiv").show();
	$('#gstNoDiv').show();
	$('#gstNo').val("");
}
// for cancel button
function cancelBtn() {
	$('.loader').show();
	$("body").addClass("overlay");
	$('.formValidation').remove();
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	//$("#demo").hide();
	$("#contactPersonAcc").hide();
	$("#mainSa").hide();
	$("#remarksAcc").hide();
	$("#btnDiv").hide();
	$("#customerId").text("");
	$("#customerId").val("");
	$('#customerType').val("").trigger('change');
	customerType();
	$('#salutation').val("");
	$('#customerName').val("");
	$('#companyName').val("");
	$('#customerDisplayName').val("");
	$('#cusEmail').val("");

	$('#custMobile').val("");
	$('#custSkype').val("");
	$('#custDesignation').val("");
	$('#department').val("");
	$('#webSite').val("");
	$('#status').val("").trigger('change');
	$('#whatsApp').val("");

	$('#pan').val("");
	$('#gstNo').val("");
	$('#currency').val("").trigger('change');
	$('#openingBalance').val("");
	$('#paymentTerms').val("");
	$('#enableDtls').val("");
	$('#enableDtls').prop('checked', false);
	$('#portableLang').val("").trigger('change');
	$('#whatsApp').val("");
	$('#faceBook').val("");
	$('#twitter').val("");
	$('#country').val("").trigger('change');
	$('#states').val("").trigger('change');
	$('#city').val("");
	$('#street1').val("");
	$('#street2').val("");
	$('#zipCode').val("");
	$('#phone').val("");
	$('#fax').val("");
	

	$('#country1').val("").trigger('change');
	$('#states1').val("").trigger('change');
	$('#city1').val("");
	$('#street11').val("");
	$('#street21').val("");
	$('#zipCode1').val("");
	$('#phone1').val("");
	$('#fax1').val("");
	$('#gstIn1').val("");
	$('#salutation1').val("");
	$('#firstName').val("");
	$('#lastName').val("");
	$('#emailAdd').val("");
	$('#mobile').val("");
	$('#remarks').val("");
	$('#delete').attr("disabled", true);
	$('.loader').hide();
	$("body").removeClass("overlay");
	agGrid.simpleHttpRequest({
		url: "view-customer-throughAjax"
	}).then(function(data) {
		gridOptions.api.setRowData(data);
	});
}

$(document).ready(function() {
	customerType();
	$('#deleteAddressDtls').attr("disabled", true);
	$("#date").datetimepicker({
		format: "d-m-Y",
		closeOnDateSelect: true,
		minDate: new Date(),
		timepicker: false,
	});
	$("#customerId").val("");

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})

	$("#fromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$("#toTime").datetimepicker({
		format: "H:i",
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$("#mainSa").hide();
	$("#contactPersonAcc").hide();
	$("#remarksAcc").hide();
	$("#btnDiv").hide();



});


var fullShippingDetails = [];
var fullPocDetails = [];
//Edit & stage change 
function editPage(id) {
	disableField()
	$('.formValidation').remove();
	fullShippingDetails = [];
	fullPocDetails = [];
	nextTab('customerInfLiId');
	$.ajax({
		type: "GET",
		url: "view-customer-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log("response for edit cus------" + JSON.stringify(response));
			if (response.code == "Success") {
				$('.loader').hide();

				var customer = response.body[0];

				$('#customerId').val(customer.customerId);
				$('#customerIdHead').text(customer.customerId);
				$('#customerIdHeadNameAdd').text(customer.customerName);
				$('#customerIdHeadNamePoc').text(customer.customerName);
				$('#customer').val(customer.customerId).prop("disabled", true);
				$('#customerType').val(customer.customerType).trigger('change');
				$('#salutation').val(customer.salutation);
				$('#customerName').val(customer.customerName);
				$('#customerIdHeadName').text(customer.customerName);
				$('#companyName').val(customer.companyName);
				$('#customerDisplayName').val(customer.customerDisplayName);
				$('#cusEmail').val(customer.cusEmail);
				$('#custMobile').val(customer.custMobile);
				$('#custSkype').val(customer.custSkype);
				$('#custDesignation').val(customer.custDesignation);
				$('#department').val(customer.department);
				$('#webSite').val(customer.webSite);
				$('#status').val(customer.status).trigger('change');
				$('#pan').val(customer.pan);
				$('#gstNo').val(customer.gstNo);
				$('#currency').val(customer.currency).trigger('change');
				$('#openingBalance').val(customer.openingBalance);
				$('#paymentTerms').val(customer.paymentTerms);
				$('#portableLang').val(customer.portableLang).trigger('change');
				$('#whatsApp').val(customer.whatsApp);
				$('#faceBook').val(customer.faceBook);
				$('#twitter').val(customer.twitter);
				$('#country').val(customer.country).trigger('change');
				$('#states').val(customer.states).trigger('change');
				getStateDataOnEdit(customer.states);
				$('#city').val(customer.city);
				$('#street1').val(customer.street1);
				$('#street2').val(customer.street2);
				$('#zipCode').val(customer.zipCode);
				$('#phone').val(customer.phone);
				$('#fax').val(customer.fax);
				

				customerType();

				// Store full data including delFlag 1
				fullShippingDetails = customer.shippingDetails ? JSON.parse(customer.shippingDetails) : [];
				fullPocDetails = customer.pocDetails ? JSON.parse(customer.pocDetails) : [];
				console.log("Full Shipping Details------", fullShippingDetails);
				console.log("Full Shipping Details------", fullPocDetails);

				// Filter and show only delFlag 0
				var filteredShippingDetails = fullShippingDetails.filter(item => item.delFlag === 0);
				gridSAOptions.api.setRowData(filteredShippingDetails);

				var filteredPocDetails = fullPocDetails.filter(item => item.delFlag === 0);
				gridOptionsPocDtls.api.setRowData(filteredPocDetails);
			}
		}
	});
}




//addVendorInfo

function addCustomerInfo() {
	var selectedNodes = gridOptionsLead.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var leadId = selectedData[0].leadId;
	// gridOptions.api.deselectAll();
	var count = 0;
	var obj = {};
	obj.leadId=leadId;
	obj.customerId = $('#customerId').val();
	obj.customerType = $('#customerType').val();
	obj.salutation = $('#salutation').val();
	obj.customerName = $('#customerName').val();
	obj.companyName = $('#companyName').val();
	obj.customerDisplayName = $('#customerDisplayName').val();
	obj.cusEmail = $('#cusEmail').val();
	obj.custMobile = $('#custMobile').val();
	obj.custSkype = $('#custSkype').val();
	obj.custDesignation = $('#custDesignation').val();
	obj.department = $('#department').val();
	obj.webSite = $('#webSite').val();
	obj.status = $('#status').val();
	obj.pan = $('#pan').val();
	obj.gstNo = $('#gstNo').val();
	obj.currency = $('#currency').val();
	obj.openingBalance = $('#openingBalance').val();
	obj.paymentTerms = $('#paymentTerms').val();
	obj.portableLang = $('#portableLang').val();
	obj.whatsApp = $('#whatsApp').val();
	obj.faceBook = $('#faceBook').val();
	obj.twitter = $('#twitter').val();
	obj.country = $('#country').val();
	obj.states = $('#states').val();
	obj.city = $('#city').val();
	obj.street1 = $('#street1').val();
	obj.street2 = $('#street2').val();
	obj.zipCode = $('#zipCode').val();
	obj.phone = $('#phone').val();
	obj.fax = $('#fax').val();
	

	let visibleShippingDetails = [];
	gridSAOptions.api.forEachNode((node) => {
		visibleShippingDetails.push(node.data);
	});

	let visiblePocDetails = [];
	gridOptionsPocDtls.api.forEachNode((node) => {
		visiblePocDetails.push(node.data);
	});

	let allShippingDetails = [
		...visibleShippingDetails,
		...fullShippingDetails.filter(item => item.delFlag === 1)
	];

	let allPocDetails = [
		...visiblePocDetails,
		...fullPocDetails.filter(item => item.delFlag === 1)
	];

	obj.shippingDetails = JSON.stringify(allShippingDetails);
	obj.pocDetails = JSON.stringify(allPocDetails);
	console.log("Data for customer====>", obj);

	/* FORM VALIDATION STARTS*/
	var validation = true;
	var ctype = $('#customerType').val();
	if (ctype == null || ctype == "") {
		nextTab('customerInfLiId');
		toastr.error('Customer Type Required');
		return;
	}
	if (obj.customerName == null || obj.customerName == "") {
		nextTab('customerInfLiId');
		toastr.error('Customer Name Required');
		return;
	}
	if (ctype === "Business") {
		if (!obj.companyName || obj.companyName.trim() === "") {
			nextTab('customerInfLiId');
			toastr.error('Company Name Is Required For Business Type');
			return;
		}
	} else if (ctype === "Individual") {
		if (!obj.customerName || obj.customerName.trim() === "") {
			nextTab('customerInfLiId');
			toastr.error('Customer Name Is Required For Individual Type');
			return;
		}
	}

	if (obj.cusEmail) {
		const validPan = pmailVal();
		if (!validPan) {
			nextTab('customerInfLiId');
			toastr.error('Please Enter Valid Email');
			count += 1;
			return;
		}
	}

	/*if (ctype != 'Export') {

		if (obj.pan == null || obj.pan == "") {
			nextTab('customerInfLiId');
			toastr.error('PAN Card Required');
			return;
		}

		const validPan = pancardVal();
		if (!validPan) {
			nextTab('customerInfLiId');
			toastr.error('Please Enter Valid PAN');
			count += 1;
			return;
		}
	}

	if (ctype === "Business") {
		if (!obj.gstNo) {
			nextTab('customerInfLiId');
			toastr.error('GST Number Required');
			return;
		}

		const validGST = ValidateGSTNumber();
		if (!validGST) {
			nextTab('customerInfLiId');
			toastr.error('Please Enter Valid GST No');
			return;
		}
	}*/


	if (obj.country == null || obj.country == "") {
		nextTab('customerAddressLiId');
		toastr.error('Country Name Required');
		return;
	}
	if (obj.states == null || obj.states == "") {
		nextTab('customerAddressLiId');
		toastr.error('State Name Required');
		return;
	}
	if (obj.city == null || obj.city == "") {
		nextTab('customerAddressLiId');
		toastr.error('City Required');
		return;
	}
	if (obj.street1 == null || obj.street1 == "") {
		nextTab('customerAddressLiId');
		toastr.error('Street Required');
		return;
	}
	if (obj.zipCode == null || obj.zipCode == "") {
		nextTab('customerAddressLiId');
		toastr.error('Zip Code Required');
		return;
	}
	const validZip = zipCodeVald();
	if (!validZip) {
		nextTab('customerAddressLiId');
		toastr.error('Please Enter Valid Zip Code');
		count += 1;
		return;
	}

	/*if (visibleShippingDetails == null || visibleShippingDetails.length == 0) {
		nextTab('customerAddressLiId');
		toastr.error('One Shipping Address Required');
		return;
	}*/

	if (visiblePocDetails == null || visiblePocDetails.length == 0) {
		nextTab('customerPOCLiId');
		toastr.error('One Point Of Contact Required');
		return;
	}


	/* FORM VALIDATION ENDS*/

	console.log("Object on add customer-----------", obj);
 
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "/sales/view-customer-add",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			$('.loader').hide();
			if (response.message == "Success") {
				console.log(response);
				toastr.success('Customer Added Successfully');
				$('#customerDetails').addClass('d-none');
				$('#convertLeadTocustomer').addClass('d-none');
				$('#leadStatusDiv').removeClass('d-none');
				$('#prevTimeline').removeClass('d-none');
				viewLeadAggridData();
				setTimeout(() => {
					gridOptionsLead.api.forEachNode((node) => {
						if (leadId == "") {
							let firstRow = gridOptionsLead.api.getDisplayedRowAtIndex(0);
							if (firstRow) {
								firstRow.setSelected(true);
							}
						} else if (node.data.leadId == leadId) {
							node.setSelected(true);
						}
					});
				}, 1000);
			} else {
				toastr.error(response.message);
			}
		},
		error: function(data) {
			$('.loader').hide();
		}
	});
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

// Email validation
var pmailValid;
function pmailVal() {
	var mail = $('#cusEmail').val().trim().toLowerCase();
	var mid = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
	/*  /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/; */

	if (mail != "") {
		if (mid.test(mail)) {
			$("#error3").hide();
			pmailValid = true;
			return true;
		} else {
			$("#error3").show();
			pmailValid = false;
			return false;
		}

	} else {
		$('#cusEmail').val("");
		$("#error3").hide();
		pmailValid = true;
		return true;
	}

}
// Email validation
var pmailValid1;
function pmailVal1() {
	var mail = $('#emailAdd').val();
	var mid = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
	/*/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;*/

	if (mail != "") {
		if (mid.test(mail)) {
			$("#error6").hide();
			pmailValid1 = true;
			return true;
		} else {
			$("#error6").show();
			$("#error6").html("Please enter a valid email id.");
			pmailValid = false;
			return false;
		}

	} else {
		$('#emailAdd').val("");
		$("#error6").hide();
		pmailValid1 = true;
		return true;
	}

}

//pancard card validation 

var pancardValid;

function pancardVal() {
	var ctype = $('#customerType').val();
	if (!ctype) {
		toastr.error('Customer Type Required.');
	}
	if (ctype == 'Business' || ctype == 'Individual') {
		var pancard = $('#pan').val();

		var pancardid = /([A-Z]){5}([0-9]){4}([A-Z]){1}$/;
		var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;

		if (pancard != '' && pancard.toUpperCase() === pancard && !format.test(pancard)) {
			if (pancardid.test(pancard)) {
				pancardValid = true;
				return true;
			} else {
				//toastr.error('Please enter a valid pan card no.');
				//  $('#panno').val("");
				pancardValid = false;
				return false;
			}

		} else {
			$('#pan').val("");
			pancardValid = false;
			return false;

		}

	} else {

	}

	/*   var pancard = $('#pan').val();
	  
	  var pancardid = /([A-Z]){5}([0-9]){4}([A-Z]){1}$/;
	  var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
	  
	  if (pancard != '' && pancard.toUpperCase() === pancard && !format.test(pancard)) {
		  if (pancardid.test(pancard)) {
			  $("#error4").hide();
			  pancardValid = true;
			  return true;
		  } else {
		  	
			  $("#error4").show();
			  $("#error4").html("Please enter a valid pan card no.");
			//  $('#panno').val("");
			  pancardValid = false;
			  return false;
		  }

	  } else {
		  $('#pan').val("");
		  pancardValid = false;
		  return false;
	   
	  }
*/
}
// gst no  validation

var GSTValid;
function ValidateGSTNumber() {
	var gstNumber = $('#gstNo').val();
	var expr = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/;
	if (gstNumber != "") {
		if (expr.test(gstNumber)) {
			GSTValid = true;
			return true;
		} else {
			//toastr.error('Please enter  valid GST Number.');
			GSTValid = false;
			return false;
		}
	} else {

		$('#gstNo').val("");
		//$("#error5").hide();
		GSTValid = true;
		return true;
	}
}


function zipCodeVald() {

	var zip = $('#zipCode').val();

	// Regular expression for Indian PIN code
	const zipPattern = /^[1-9][0-9]{5}$/;

	if (zip && (/^\d+$/.test(zip))) {
		// Test the pincode against the regular expression
		if (zipPattern.test(zip)) {
			$("#errorZip").hide();
			$('#zipCode').val(zip);
			return true;
		} else {
			//$("#errorZip").show();
			//toastr.error('Please enter a valid Zip.');
			//$("#errorZip").html("Please enter a valid Zip.");
			$('#zipCode').val(zip);
			return false;
		}
	} else {
		$('#zipCode').val('');
		return false;

	}

}

function check1(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	$("#" + fieldId).val(tempVal);
}

function deleteCustomer() {
	let customerId = $("#customerId").val();

	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you really want to delete this customer?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: '#d33',
		cancelButtonColor: '#3085d6',
	}).then((result) => {
		if (result.value) {
			$('.loader').show();

			$.ajax({
				type: "GET",
				url: "view-customer-delete-id?id=" + customerId,
				success: function(response) {
					$('.loader').hide();

					if (response.code === "Success") {
						toastr.success('Customer Deleted Successfully.');
						nextTab('customerInfLiId');
						cancelBtn();
						agGrid.simpleHttpRequest({
							url: "view-customer-throughAjax"
						}).then(function(data) {
							if (data && data.length > 0) {
								gridOptions.api.setRowData(data);
								if (gridOptions.api) {
									gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
								}
							} else {
								gridOptions.api.setRowData([]);
							}

						});

					} else {
						toastr.error('Failed to delete customer.');
					}
				},
				error: function() {
					$('.loader').hide();
					toastr.error('Something went wrong while deleting the customer.');
				}
			});
		}
	});
}


function saveFile() {
	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-crm-vendors-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});
}

function deleteFile() {
	$('#fileUpload').val("");
	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-crm-vendors-delete-file",
		enctype: "multipart/form-data",
		contentType: false,
		/* data        : fileData, */
		processData: false,
		cache: false,
		success: function(response) {
		},
		error: function(e) {

		}
	});
}

function getStateDetails1() {

	var cname = $('#country1').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1").append(option);
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDetails() {

	var cname = $('#country').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDataOnEdit(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
					$("#states").val(stateId).trigger('change');
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
	}
}

function getStateDataOnEdit1(stateId) {
	var country = $("#country").val();
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states1").append(option);
					}
					$("#states1").val(stateId).trigger('change');
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states1").append(option);
	}
}

/* function copyAsBillingAdd() {
	document.getElementById("mySidenav").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom:-369px;";
	
	document.getElementById("mainSa").style.width = "70%";
	 var customerId = $("#customerId").text();
	
	$("#shippingId").text(customerId); 
	var country = $('#country').val();
	var states = $('#states').val();
	var city = $('#city').val();
	var street1 = $('#street1').val();
	var street2 = $('#street2').val();
	var zipCode = $('#zipCode').val();
	var phone = $('#phone').val();
	var fax = $('#fax').val();
	
	$('#country1').val(country);
	//$('#states1').val(states);
	getStateDataOnEdit1(states);
	$('#city1').val(city);
	$('#street11').val(street1);
	$('#street21').val(street2);
	$('#zipCode1').val(zipCode);
	$('#phone1').val(phone);
	$('#fax1').val(fax);
} */

function openNav() {
	/* var customerId = $("#customerId").text();
	$("#shippingId").text(customerId); */
	$("#shippingId").val("");
	$("#country1").val("").trigger('change');
	$("#states1").val("").trigger('change');
	$("#city1").val("");
	$("#street11").val("");
	$("#street21").val("");
	$("#zipCode1").val("");
	$("#phone1").val("");
	$("#fax1").val("");
	$("#gstIn1").val("");
	$("#addStatus").prop("checked", false);
	document.getElementById("mySidenav").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom:-369px;";

	document.getElementById("mainSa").style.width = "70%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("mainSa").style.width = "100%";
}
function showDisplay() {
	var ctype = $('#customerType').val();
	if (ctype == 'Business') {
		var compName = $('#companyName').val();
		$('#customerDisplayName').val(compName);
		$('#customerName').val(compName);
	} else if (ctype == 'Individual') {
		var title = $('#salutation').val();
		var name = $('#customerName').val();
		//var fullName = title + ' ' + name;
		var fullName = name;
		$('#customerDisplayName').val(fullName);
		$('#customerName').val(name);
	} else {
		var compName = $('#companyName').val();
		$('#customerDisplayName').val(compName);
		$('#customerName').val(compName);
	}

}
function customerType() {
	var ctype = $('#customerType').val();
	if (ctype == 'Business') {
		$('#salutationDiv').hide();
		$('#customerNameDiv').hide();
		$('#companyNameDiv').show();
		$('#custDesignationDiv').show();
		$('#departmentDiv').show();
		$('#gstNoDiv').show();
	} else if (ctype == 'Individual') {
		$('#salutationDiv').show();
		$('#customerNameDiv').show();
		$('#companyNameDiv').hide();
		$('#custDesignationDiv').hide();
		$('#departmentDiv').hide();
		$('#gstNoDiv').show();
	} else {
		$('#gstNoDiv').hide();
		$('#salutationDiv').hide();
		$('#customerNameDiv').hide();
		$('#companyNameDiv').show();
		$('#custDesignationDiv').show();
		$('#departmentDiv').show();
	}

}

/* 	function checksum(g){
		let regTest = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$/.test(g)
		 if(regTest){
			let a=65,b=55,c=36;
			return Array['from'](g).reduce((i,j,k,g)=>{ 
			   p=(p=(j.charCodeAt(0)<a?parseInt(j):j.charCodeAt(0)-b)*(k%2+1))>c?1+(p-c):p;
			   return k<14?i+p:j==((c=(c-(i%c)))<10?c:String.fromCharCode(c+b));
			},0); 
		}
		return regTest
	}
 */

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



function getGstData() {
	var gstNo = $("#gstNo").val();
	if (gstNo.length == 15) {
		$('.loader').show();
		$.ajax({
			type: "GET",
			url: "view-customer-getgstno?id=" + gstNo,
			success: function(response) {
				const obj = JSON.parse(response.body);
				if (obj.error == false) {
					$("#gstNo").val(obj.data.gstin);
					$("#companyName").val(obj.data.lgnm);

					$("#status").val(obj.data.sts).trigger('change');
					$("#customerDisplayName").val(obj.data.lgnm);
					$("#customerName").val(obj.data.lgnm);
					$("#city").val(obj.data.pradr.addr.loc);
					$("#street1").val(obj.data.pradr.addr.bno + ', ' + obj.data.pradr.addr.bnm);
					$("#street2").val(obj.data.pradr.addr.st);
					$("#zipCode").val(obj.data.pradr.addr.pncd);
					$('#country').val('cntry001').trigger('change');
					getStateDataOnGST(obj.data.pradr.addr.stcd, 'cntry001');
					$('#states').val();
					$('.loader').hide();
				}
				else {
					$("#messageParagraph").text("Please Enter Valid GST No.");
					//$("#messageParagraph").text(obj.data);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');

					$("#gstNo").val('');
					$('.loader').hide();
				}
			},
			error: function(e) {


			}
		});
	} else {

		//$("#gstNo").val('');


	}
}

function getStateDataOnGST(stateId, country) {
	if (country) {
		$.ajax({
			type: "GET",
			url: "view-customer-stateList?id=" + country,
			success: function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					var tempState = '';
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>")
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);;
						if (response.body[i].name == stateId) {
							tempState = response.body[i].key;
						}

					}

					$("#states").val(tempState).trigger('change');
				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
	}
}

//Zip Code  validation 

var zipcodeValid;

function zipcodeVal() {
	var ctype = $('#customerType').val();
	if (ctype == 'Business' || ctype == 'Individual') {
		var zipcode = $('#zipCode').val();

		var zipcodeid = /^\d{6}(-\d{6})?$/;
		var zipcodeid1 = /^\d{6}(-\d{5})?$/;
		if (zipcode != '') {
			if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {

				$("#error14").hide();
				zipcodeValid = true;
				return true;
			} else {
				$("#error14").show();
				zipcodeValid = false;
				return false;
			}

		} else {
			$("#error14").hide();
			zipcodeValid = true;
			return true;
		}
	} else {

	}

}
var zipcodeValid1;

function zipcodeVal1() {
	var ctype = $('#customerType').val();
	if (ctype == 'Business' || ctype == 'Individual') {
		var zipcode = $('#zipCode1').val();

		var zipcodeid = /^\d{6}(-\d{6})?$/;
		var zipcodeid1 = /^\d{6}(-\d{5})?$/;
		if (zipcode != '') {
			if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {

				$("#error15").hide();
				zipcodeValid1 = true;
				return true;
			} else {
				$("#error15").show();
				//$("#error15").html("Please enter a valid Zip Code No.");
				zipcodeValid1 = false;
				return false;
			}

		} else {
			$("#error15").hide();
			zipcodeValid1 = true;
			return true;
		}
	} else {

	}

}

function shippingZipCOdeValid() {
    var zipcode = $('#zipCode1').val();

    var zipcodeid = /^\d{6}(-\d{6})?$/;
    var zipcodeid1 = /^\d{6}(-\d{5})?$/;
    if (zipcode != '') {
        if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {
            return true;
        } else {
            return false;
        }
    } else {
        return true;
    }
}


function vendorNameUpper() {
	var text = $("#customerName").val();
	var result = text.toUpperCase();
	$("#customerName").val(result);
}
function vendorNameUpperr() {
	var text = $("#companyName").val();
	var result = text.toUpperCase();
	$("#companyName").val(result);
}




function nextTab(id) {
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}

function toggleSection() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();

	$("#shippingId").val('');
	$("#country1").val('').trigger('change').attr("disabled", false);
	$("#states1").val('').trigger('change').attr("disabled", false);
	$("#city1").val('').attr("disabled", false);
	$("#street11").val('').attr("disabled", false);
	$("#street21").val('').attr("disabled", false);
	$("#zipCode1").val('').attr("disabled", false);
	$("#phone1").val('').attr("disabled", false);
	$("#fax1").val('').attr("disabled", false);
	$("#gstIn1").val('').attr("disabled", false);
}
function togglePocSection() {
	$(".br-m-btn-poc").hide();
	$(".br-s-btn-poc").show();

	$("#idPoc").val('');
	$("#firstName").val('').attr("disabled", false);
	$("#lastName").val('').attr("disabled", false);
	$("#emailAdd").val('').attr("disabled", false);
	$("#mobile").val('').attr("disabled", false);
}

function cancelDetails() {
	$(".br-m-btn").show();
	$(".br-s-btn").hide();

	$("#shippingId").val('');
	$("#country1").val('').trigger('change');
	$("#states1").val('').trigger('change');
	$("#city1").val('');
	$("#street11").val('');
	$("#street21").val('');
	$("#zipCode1").val('');
	$("#phone1").val('');
	$("#fax1").val('');
	$("#gstIn1").val('');

}
function cancelPocDetails() {
	$(".br-m-btn-poc").show();
	$(".br-s-btn-poc").hide();

	$("#idPoc").val('');
	$("#firstName").val('');
	$("#lastName").val('');
	$("#emailAdd").val('');
	$("#mobile").val('');

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
					$("#" + sid).val(stateId).trigger('change');
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
function editShippingAddress() {
	toggleSection();
	let selectedData = gridSAOptions.api.getSelectedRows();
	console.log(selectedData);
	$("#shippingId").val(selectedData[0].shippingId);
	$("#country1").val(selectedData[0].country).trigger('change');
	$("#states1").val(selectedData[0].state).trigger('change');
	$("#city1").val(selectedData[0].city);
	$("#street11").val(selectedData[0].street1);
	$("#street21").val(selectedData[0].street2);
	$("#zipCode1").val(selectedData[0].zipcode);
	$("#phone1").val(selectedData[0].phone);
	$("#fax1").val(selectedData[0].fax);
	$("#gstIn1").val(selectedData[0].gstIn);
	getStateDataOnEditMul('country1', 'states1', selectedData[0].state);

}

$(document).ready(() => {
	$(".br-s-btn").hide();
	$(".br-s-btn-dl").hide();
	$(".br-s-btn-poc").hide();
	$(".br-s-btn-b").hide();
	$(".br-s-btn-p").hide();
})
function saveShippingDetails() {
	let country = $("#country1").val();
	let countryName = $("#country1 option:selected").text();
	let state = $("#states1").val();
	let stateName = $("#states1 option:selected").text();
	let city = $("#city1").val();
	let street1 = $("#street11").val();
	let street2 = $("#street21").val();
	let zipcode = $("#zipCode1").val();
	let phone = $("#phone1").val();
	let fax = $("#fax1").val();
	let gstIn = $("#gstIn1").val();
	let shippingId = $("#shippingId").val();

	let dataset = [];
	let lastSelectedSlNo = '0';
	gridSAOptions.api.forEachNode((node) => {
		dataset.push(node.data);
		lastSelectedSlNo = node.data.id;
	});

	if (shippingId != null && shippingId != '') {
		const index = dataset.findIndex(obj => obj.shippingId === shippingId);
		if (index !== -1) {
			dataset[index].shippingId = shippingId;
			dataset[index].country = country;
			dataset[index].countryName = countryName;
			dataset[index].state = state;
			dataset[index].stateName = stateName;
			dataset[index].city = city;
			dataset[index].street1 = street1;
			dataset[index].street2 = street2;
			dataset[index].zipcode = zipcode;
			dataset[index].phone = phone;
			dataset[index].fax = fax;
			dataset[index].gstIn = gstIn;
			dataset[index].delFlag = 0;
		}
	} else {
		let obj = {};

		obj.country = country;
		obj.countryName = countryName;
		obj.state = state;
		obj.stateName = stateName;
		obj.city = city;
		obj.street1 = street1;
		obj.street2 = street2;
		obj.zipcode = zipcode;
		obj.phone = phone;
		obj.fax = fax;
		obj.gstIn = gstIn;
		obj.delFlag = 0;
		obj.shippingId = generateUUID()?.toString();
		dataset.push(obj)
		console.log("Data for shipping address========>", dataset);
	}

	if (country != null || country != '' || state != null || state != '' || city != null || city != ''
		|| street1 != null || street1 != '' || zipcode != null || zipcode != '') {
		if (country == null || country == "") {
			nextTab('customerAddressLiId');
			toastr.error('Country Required For Shipping Address');
			return;
		}
		if (state == null || state == "") {
			nextTab('customerAddressLiId');
			toastr.error('State Required For Shipping Address');
			return;
		}
		if (city == null || city == "") {
			nextTab('customerAddressLiId');
			toastr.error('City Required For Shipping Address');
			return;
		}
		if (street1 == null || street1 == "") {
			nextTab('customerAddressLiId');
			toastr.error('Street 1 Required For Shipping Address');
			return;
		}
		if (zipcode == null || zipcode == "") {
			nextTab('customerAddressLiId');
			toastr.error('Zipcode Required For Shipping Address');
			return;
		}
		const validZip = shippingZipCOdeValid();
		if (!validZip) {
			toastr.error('Please Enter Valid Zipcode');
			count += 1;
			return;
		}
	}

	gridSAOptions.api.setRowData(dataset);

	cancelDetails();
	$(".br-dis").attr("disabled", true);
}
function savePocDetails() {
	let firstName = $("#firstName").val();
	let lastName = $("#lastName").val();
	let mobile = $("#mobile").val();
	let emailAdd = $("#emailAdd").val();
	let id = $("#idPoc").val();

	let dataset = [];
	let lastSelectedSlNo = '0';
	gridOptionsPocDtls.api.forEachNode((node) => {
		dataset.push(node.data);
		lastSelectedSlNo = node.data.id;
	});

	if (id != null && id != '') {
		const index = dataset.findIndex(obj => obj.id === id);
		if (index !== -1) {
			dataset[index].id = id;
			dataset[index].firstName = firstName;
			dataset[index].lastName = lastName;
			dataset[index].mobile = mobile;
			dataset[index].emailAdd = emailAdd;
			dataset[index].delFlag = 0;
		}
	} else {
		let obj = {};

		obj.firstName = firstName;
		obj.lastName = lastName;
		obj.mobile = mobile;
		obj.emailAdd = emailAdd;
		obj.id = generateUUID()?.toString();
		obj.delFlag = 0;
		dataset.push(obj)
	}

	if (firstName == null || firstName == "") {
		nextTab('customerPOCLiId');
		toastr.error('First name required');
		return;
	}
	if (emailAdd == null || emailAdd == "") {
		nextTab('customerPOCLiId');
		toastr.error('Email id required');
		return;
	}
	if (mobile == null || mobile == "") {
		nextTab('customerPOCLiId');
		toastr.error('Mobile number required');
		return;
	}

	gridOptionsPocDtls.api.setRowData(dataset);

	cancelPocDetails();
	$(".br-dis-poc").attr("disabled", true);
}

function editPocDetails() {
	togglePocSection();
	let selectedData = gridOptionsPocDtls.api.getSelectedRows();

	$("#idPoc").val(selectedData[0].id);
	$("#firstName").val(selectedData[0].firstName);
	$("#lastName").val(selectedData[0].lastName);
	$("#emailAdd").val(selectedData[0].emailAdd);
	$("#mobile").val(selectedData[0].mobile);
}
function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0;
		const v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

function newCustomer() {
	$("#customer").val("");
	$("#whatsApp").val("");
	$("#customerName").val("");
	$("#gstNo").val("");
	$("#customerIdHeadName").text("");
	$("#customerIdHeadNamePoc").text("");
	$("#customerIdHeadNameAdd").text("");
	$("#customerIdHead").text("");
	enableField();
	cancelBtn();
	gridSAOptions.api.setRowData([]);
	gridOptionsPocDtls.api.setRowData([]);
}
function addNewCust() {
	gridOptions.api.deselectAll();
	newCustomer();
}
function cancelCust() {
	$('#customerDetails').addClass('d-none');
	$('#convertLeadTocustomer').removeClass('d-none');
	$('#leadStatusDiv').removeClass('d-none');
	$('#prevTimeline').removeClass('d-none');
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
function enableField() {
	let fields = [
		'salutation', 'customerType', 'companyName', 'customerName', 'customerDisplayName', 'cusEmail', 'custMobile',
		'custSkype', 'custDesignation', 'department', 'status', 'webSite', 'pan', 'currency',
		'openingBalance', 'paymentTerms', 'portableLang', 'tds', 'gstNumber', 'vendorCategory',
		'whatsApp', 'tanNo', 'faceBook', 'twitter', 'country', 'city', 'states', 'street1',
		'street2', 'zipCode', 'phone', 'fax', 'country1', 'states1', 'city1', 'street11',
		'street21', 'zipCode1', 'phone1', 'fax1','gstIn1', 'salutation1', 'firstName', 'lastName',
		'emailAdd', 'mobile', 'gstNo'
	];

	fields.forEach(id => {
		$('#' + id).attr("disabled", false);
	});
}


function disableField() {
	let fields = [
		'salutation', 'customerType', 'companyName', 'customerName', 'customerDisplayName', 'cusEmail', 'custMobile',
		'custSkype', 'custDesignation', 'department', 'status', 'webSite', 'pan', 'currency',
		'openingBalance', 'paymentTerms', 'portableLang', 'tds', 'gstNumber', 'vendorCategory',
		'whatsApp', 'tanNo', 'faceBook', 'twitter', 'country', 'city', 'states', 'street1',
		'street2', 'zipCode', 'phone', 'fax', 'country1', 'states1', 'city1', 'street11',
		'street21', 'zipCode1', 'phone1', 'fax1','gstIn1', 'salutation1', 'firstName', 'lastName',
		'emailAdd', 'mobile', 'gstNo'
	];

	fields.forEach(id => {
		$('#' + id).attr("disabled", true);
	});
}


// for cancel button
function cancelBtn() {
	let fields = [
		'customerId', 'salutation', 'customerType', 'companyName', 'customerName', 'customerDisplayName', 'cusEmail',
		'custMobile', 'custSkype', 'custDesignation', 'department', 'status', 'webSite', 'pan',
		'currency', 'openingBalance', 'paymentTerms', 'portableLang', 'tds', 'gstNumber',
		'vendorCategory', 'whatsAppNo', 'tanNo', 'faceBook', 'twitter', 'country', 'city', 'states',
		'street1', 'street2', 'zipCode', 'phone', 'fax', 'country1', 'states1', 'city1', 'street11',
		'street21', 'zipCode1', 'phone1', 'fax1','gstIn1', 'salutation1', 'firstName', 'lastName',
		'emailAdd', 'mobile'
	];

	fields.forEach(id => {
		$('#' + id).val("");
	});
}

function deleteSelectedDetails(gridOptions, type) {
	let dataset = [];
	let customerId = $('#customerId').val();

	let cnt = 0;
	gridOptions.api.forEachNode((node) => {
		dataset.push({ ...node.data });
		cnt++;
	});

	let selectedRows = gridOptions.api.getSelectedRows();


	if (selectedRows.length === 0) {
		toastr.error("Please select a row to delete.");
		return;
	}

	if (type === "shipping" && cnt === 1) {
		toastr.error("You cannot delete the shipping address because at least one must be saved.");
		return;
	} else if (type === "poc" && cnt === 1) {
		toastr.error("You cannot delete the point of contact because at least one must be saved.");
		return;
	}

	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete the selected row(s)?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: '#d33',
		cancelButtonColor: '#3085d6',
	}).then((result) => {
		console.log("Swal Result:", result);

		if (result.value) {
			console.log("User confirmed deletion.");

			if (!customerId) {
				gridOptions.api.applyTransaction({ remove: selectedRows });
				return;
			} else {
				let matchKey = type === "shipping" ? "shippingId" : "id";
				selectedRows.forEach((selectedRow) => {
					let index = dataset.findIndex(row => row[matchKey] == selectedRow[matchKey]);
					if (index !== -1) {
						dataset[index] = {
							...dataset[index],
							delFlag: 1,
							customerId: customerId,
							type: type
						};
					}
				});
				if (type === "shipping") {
					dataset = [...dataset, ...fullShippingDetails.filter(item => item.delFlag === 1)];
				} else if (type === "poc") {
					dataset = [...dataset, ...fullPocDetails.filter(item => item.delFlag === 1)];
				}

				console.log("Updated Dataset:", dataset);

				$.ajax({
					type: "POST",
					url: "view-customer-delete-child-data",
					contentType: "application/json",
					data: JSON.stringify(dataset),
					success: function(response) {
						console.log("AJAX Success Response:", response);
						if (response.code === "success") {
							const responseData = JSON.parse(response.body);
							let updatedData = [];

							if (type === "shipping") {
								updatedData = responseData.shippingDetails.filter(item => item.delFlag === 0);
								fullShippingDetails = responseData.shippingDetails;
								console.log("FullShipping Details", fullShippingDetails);
							} else if (type === "poc") {
								updatedData = responseData.pocDetails.filter(item => item.delFlag === 0);
								fullPocDetails = responseData.pocDetails;
								console.log("FullPoc Details", fullPocDetails);
							}

							gridOptions.api.setRowData(updatedData);

							toastr.success(`${type} details deleted successfully!`);
						} else {
							toastr.error(`Failed to delete ${type} details.`);
						}
					},
					error: function(xhr, status, error) {
						console.error("AJAX Error:", error);
						toastr.error(`Error while deleting ${type} details.`);
					}
				});
			}


		} else {
			console.log("User canceled deletion.");
		}
	});

}


/*function onQuickFilterChanged() {
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
}*/
function duplicatePanCheck() {
	var panCheck = $('#pan').val();
	$.ajax({
		type: "GET",
		url: "view-customer-panduplicate?id=" + panCheck,
		async: false,
		success: function(response) {
			//alert("response------"+ JSON.stringify(response));
			if (response.message == "Success") {
				if (panCheck == response.body[0].pan && panCheck != "") {
					toastr.error('Pan number already registered!');
					$("#pan").val('');
				}


			}

		}
	})

}