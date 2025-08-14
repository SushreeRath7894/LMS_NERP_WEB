$(() => {
	$('#doctor').select2();  //#doctor
	$('#departmentType').select2();  //#departmentType
	$('#appointmentTypeSelect').select2();  //#appointmentType
	$('#typeAmb').select2();  //#typeAmb
	$('#reqAmb').select2();  //#reqAmb
	$('#testName').select2();//testName
});
$(document).ready(function() {
	toggleFields("");
	var appointmentDiv = document.querySelector('#patientAppointmentGrid');
	new agGrid.Grid(appointmentDiv, appointmentGridOption);
	//appointmentGridOption.api.setRowData([]);
	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);

	var gridDiv = document.querySelector('#testGridGrid');
	new agGrid.Grid(gridDiv, gridOptionsTest);
	//gridOptions.api.setRowData();

	const activeButton = document.querySelector(".appointment-btn.active");
	if (activeButton) {
		filterAppointments('All', activeButton);
	}
	var dateFormat = localStorage.getItem("dateFormat");
	$("#apointmentClaendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: 0,
	}).on("change", function() {
		$('#dateOfAppointment').val($(this).val());
	})

	$('#dateOfAppointment').blur(function() {
		$("#apointmentClaendar").val($(this).val());
	})

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});

	$('#extPat').select2({
		placeholder: "Select",
		allowClear: true
	});
	$('#testName').select2({
		placeholder: "Select",
		allowClear: true
	});
});


function onQuickFilterChanged() {
	appointmentGridOption.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (appointmentGridOption.api) {
			appointmentGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function handleEnter(event) {
	if (event.key === "Enter") {
		onQuickFilterChanged()
	}
}
function resetBtn() {
	$("#quickFilter").val('');
	appointmentGridOption.api.setQuickFilter('');
	appointmentGridOption.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (appointmentGridOption.api) {
			appointmentGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

/*Ag Grid For Patient Appointment*/
var appointmentDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true
},
{
	headerName: "Appointment Id",
	field: "bookingId",
	width: 160
},
{
	headerName: "Patient Id",
	field: "patientId",
	width: 130
},
{
	headerName: "Date",
	field: "date",
	flex: 1,
},
{
	headerName: "Time",
	field: "time",
	flex: 1,

},
{
	headerName: "Type",
	field: "bookingType",
	flex: 1,

},
{
	headerName: "Status",
	field: "status",
	flex: 1,
},

];

var appointmentGridOption = {
	columnDefs: appointmentDefs,
	//rowData: appointmentData,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 19,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},

	onSelectionChanged: rowSelect,
	onGridReady: checkGridData,

};
function checkGridData() {
	let rowCount = appointmentGridOption.api.getDisplayedRowCount();
	if (rowCount == 0) {
		$('#addBtnId').removeClass('d-none');
		$('#deleteAppointment').addClass('d-none');
		$('#editAppointment').addClass('d-none');
		$('#cancelBtn').addClass('d-none');
	}
}
function rowSelect() {
	const selectedNodes = appointmentGridOption.api.getSelectedNodes();
	const selectedData = selectedNodes.map(node => node.data);

	if (selectedNodes.length > 0) {
		const appointmentType = selectedData[0].bookingType;
		const bookingId = selectedData[0].bookingId;

		$('#addBtnId').removeClass('d-none');

		if (appointmentType === "Ambulance" || appointmentType === "Radiology" || appointmentType === "Pathology") {
			$('#editAppointment').addClass('d-none');
			$('#deleteAppointment').removeClass('d-none');
		} else {
			// Show both Edit (Reschedule) and Delete
			$('#editAppointment').removeClass('d-none');
			$('#deleteAppointment').removeClass('d-none');
		}
		$('#cancelBtn').addClass('d-none');

		editAppointmentData(bookingId, appointmentType);
		console.log("Selected Node ----->", selectedData);
	} else {
		$('#cancelBtn').removeClass('d-none');
		$('#addBtnId').addClass('d-none');
		$('#editAppointment').addClass('d-none');
		$('#deleteAppointment').addClass('d-none');
		$('#addAppointmentDiv').removeClass('d-none');
		$('#saveAppointmentBtn').removeClass('d-none');

		$('#departmentType').val('');
		$('#doctor').val('');
		$('#docFee').val('');
		$('#dateOfAppointment').val('');
		$('#timeOfAppointment').val('');
		$('#appointmentDescription').val('');
		$('#appointmentId').val('');
		$("#typeAmb").val('');
		$("#reqAmb").val('');
		$('#appointmentTypeSelect').val('').trigger('change');
		enableFields();
	}
}

// for activity table
var activityDefs = [{
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: function(params) {
		return params.data.availability !== "1";
	},
	sortable: false,
	filter: false,
	resizable: true,
	width: 30,
	pinned: 'left',

},
{
	headerName: "Ambulance No",
	field: "item_name",
	flex: 1,
	pinned: 'left',
}, {
	headerName: "Type",
	field: "type",
	width: 150,
	hide: true,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Driver Id",
	field: "driverId",
	width: "150",
	hide: true,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Driver Name",
	field: "assigned_username",
	flex: 1,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Ambulance Name",
	field: "asset_code",
	flex: 1,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Driver Mobile No",
	field: "driverMob",
	width: "150",
	hide: true,
	cellStyle: {
		textAlign: 'center'
	},
}, {
	headerName: "Availability Status",
	field: "avl_status",
	flex: 1,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.avl_status == "Available") {
			return '<a style="color:green;font-weight: bold;">' + params.data.avl_status + '</a>';
		} else {
			return '<a style="color:red;font-weight: bold;">' + params.data.avl_status + '</a>';
		}
	}
}, {
	headerName: "Available On",
	field: "avl_on",
	flex: 1,
	cellStyle: {
		textAlign: 'center'
	},
	cellRenderer: function(params) {
		if (params.data.avl_on != "--") {
			return '<a style="font-weight: bold;">' + params.data.avl_on + '</a>';
		} else {
			return '--';
		}
	}
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
	var selectedRows = activityOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		$("#save").show();
	} else {
		$("#save").hide();
	}
}

function filterAppointments(type, clickedButton) {
	agGrid.simpleHttpRequest({
		url: 'appointment-get-all-bookings'
	}).then(function(data) {
		if (!data?.body) {
			appointmentGridOption.api.setRowData([]);
			document.getElementById("ambulanceDetails").innerHTML = '';
			document.getElementById("opdDetails").innerHTML = '';
			resetAppointmentUI();
			return;
		}
		var jsonData = JSON.parse(data?.body);
		console.log("AllData--<>", jsonData);
		//var allData = jsonData?.patientDetails || [];

		// Format date and time
		jsonData.forEach(item => {
			if (item.bookingDate) {
				const [datePart, timePart] = item.bookingDate.split(" ");
				const [year, month, day] = datePart.split("-");
				item.date = `${day}-${month}-${year}`;
				item.time = timePart.substring(0, 5);
			}
		});
		console.log("All Data For --------->", jsonData);
		// Filter agGrid rows
		let filteredData;
		if (type === 'All') {
			filteredData = jsonData;
		} else {
			filteredData = jsonData.filter(item => item.bookingType === type);
		}
		//Sorting The Data In Descending Order
		filteredData.sort((a, b) => {
			const getIdNumber = id => parseInt(id.split('/').pop());
			return getIdNumber(b.bookingId) - getIdNumber(a.bookingId);
		});


		appointmentGridOption.api.setRowData(filteredData);

		setTimeout(() => {
			if (appointmentGridOption.api) {
				appointmentGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
			}
		}, 300);
	});

	console.log("Clicked Button:", clickedButton);
	document.querySelectorAll('.appointment-btn').forEach(btn => btn.classList.remove('active'));
	clickedButton.classList.add('active');
}

var deptDoctorWithPrice = [];

function getDepartmentList(bookFromType, id = "") {
	var option = $("<option disabled></option>");
	$(option).val(null);
	$(option).html("Select");
	$.ajax({
		type: "GET",
		url: "appointment-department-list?id=" + bookFromType,
		success: function(response) {
			if (response.message == "success") {
				$("#departmentType").empty();
				var option = $("<option disabled></option>");
				$(option).val(null);
				$(option).html("Select");
				$("#departmentType").append(option);

				if (response.body && response.body.length > 0) {
					deptDoctorWithPrice = response.body;
				} else {
					deptDoctorWithPrice = [];
				}

				for (var i = 0; i < response.body.length; i++) {
					var option = $("<option></option>");
					$(option).val(response.body[i].key);
					$(option).html(response.body[i].name);
					$("#departmentType").append(option);
				}

				$("#departmentType").val(id);

			} else {
				deptDoctorWithPrice = [];
			}
		},
		error: function(e) { }
	});
}
//Onchange getDoctorsList
var doctorListWithPrice = [];

function getDoctorsList(id) {

	var department = $("#departmentType").val();
	if (department) {

		var defaultOption = $("<option disabled selected></option>");
		$(defaultOption).val(null);
		$(defaultOption).html("Select");

		$.ajax({
			type: "GET",
			url: "appointment-doctorList?from=" + bookFromType + "&deptId=" + department,
			success: function(response) {
				if (response.code === "success") {
					$("#doctor").empty();
					$("#doctor").append(defaultOption);
					var responseBody = JSON.parse(response.body);
					if (responseBody && responseBody.view && responseBody.view.length > 0) {
						doctorListWithPrice = responseBody.view;

						for (var i = 0; i < responseBody.view.length; i++) {
							var option = $("<option ></option>");
							$(option).val(responseBody.view[i].item_name);
							$(option).html(responseBody.view[i].item_user_name);

							$("#doctor").append(option);
						}
						if (id !== "0") {
							$("#doctor").val(id);
						} else {
							$("#docFee").val('0.00');
						}
					} else {
						doctorListWithPrice = [];
					}


				} else {
					doctorListWithPrice = [];
				}
			},
			error: function(e) {
				console.error("Error fetching doctor list:", e);
			}
		});
	} else {
		$("#doctor").empty();
		var option = $("<option disabled selected></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#doctor").append(option);
		$("#docFee").val("0.00")
	}
}

function getDocFee() {
	let doc = $("#doctor").val();
	let docFeeList = doctorListWithPrice.filter(a => a.item_name == doc);
	if (docFeeList && docFeeList.length > 0) {
		$("#docFee").val(docFeeList[0].billing_price?.toFixed(2));
	} else {
		$("#docFee").val('0.00');
	}
}
function getAllAmbulanceList(amblNo) {

	let from = 'Ambulance';

	agGrid.simpleHttpRequest({
		url: 'appointment-ambulance-list-view?from=' + bookFromType
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData = jsonData?.view;

		activityOptions.api.setRowData(allData);

		var rowIndexToSelect = -1;
		allData.forEach(function(rowData, index) {
			if (rowData.amublanceNo === amblNo) {
				rowIndexToSelect = index;
			}
		});

		/*activityOptions.api.forEachNode(function(node) {
			if (node.data.amublanceNo === amblNo) {
				node.setSelected(true);
				node.selectable = true;
			} else {
				node.setSelected(false);
				node.selectable = true;
			}
		});*/

		if (rowIndexToSelect === -1) {
			console.log("No row found with ambulanceNo:", amblNo);
		}
	});
}
var appointmentIdAdd = '';
function clearAllFieds() {
	let selectedNodes = appointmentGridOption.api.getSelectedNodes();
	let selectedData = selectedNodes.map(node => node.data);
	if (selectedNodes.length > 0) {
		appointmentIdAdd = selectedData[0].bookingId;
	}
	appointmentGridOption.api.deselectAll();
	$('#cancelBtn').removeClass('d-none');
	$('#addAppointmentDiv').removeClass('d-none');
	$('#saveAppointmentBtn').removeClass('d-none');
	$('#addBtnId').addClass('d-none');
	$('#departmentType').val('');
	$('#doctor').val('');
	$('#docFee').val('');
	$('#dateOfAppointment').val('');
	$('#timeOfAppointment').val('');
	$('#appointmentDescription').val('');
	$('#appointmentId').val('');
	$("#typeAmb").val('');
	$("#reqAmb").val('');
	const activeButton = document.querySelector(".appointment-btn.active");
	const onclickAttr = activeButton.getAttribute("onclick");
	const match = onclickAttr && onclickAttr.match(/filterAppointments\('(.+?)'/);
	const type = match ? match[1] : 'All';
	setTimeout(() => {
		if (type === "All") {
			$('#appointmentTypeSelect').val('').trigger('change');
		} else {
			console.log('Setting type to:', type);
			$('#appointmentTypeSelect').val(type).trigger('change');
		}
	}, 0);

	enableFields();
}
function cancel() {
	appointmentGridOption.api.forEachNode((node) => {
		if (appointmentIdAdd == "") {
			let firstRow = appointmentGridOption.api.getDisplayedRowAtIndex(0);
			if (firstRow) {
				firstRow.setSelected(true);
			}
		} else if (node.data.bookingId == appointmentIdAdd) {
			node.setSelected(true);
		}
	});
	$('#addBtnId').removeClass('d-none');
	$('#editAppointment').removeClass('d-none');
	$('#saveAppointmentBtn').addClass('d-none');
	$('#cancelBtn').addClass('d-none');
	let selectedNodes = appointmentGridOption.api.getSelectedNodes();
	let selectedData = selectedNodes.map(node => node.data);
	let appointmentId = selectedData[0].bookingId;
	let appointmentType = selectedData[0].bookingType;
	if (appointmentId) {
		editAppointmentData(appointmentId, appointmentType);
	}
}
let bookFromType = '';
function toggleFields(appointmentType) {

	if (appointmentType === "OPD") {
		document.getElementById("ambulanceDetails").innerHTML = '';
		document.getElementById("radiologyDetails").innerHTML = '';
		document.getElementById("opdDetails").innerHTML = '';
		document.getElementById("pathologyDetails").innerHTML = '';
		$('#dateOfAppointment').parent().parent().removeClass('d-none');
		$('#timeOfAppointment').parent().parent().removeClass('d-none');
		$('.opd-sec').removeClass('d-none');
		$('.amb-sec').addClass('d-none');
		$('.rad-sec').addClass('d-none');
		$('#activity').addClass('d-none');
		$("#testDetailsSection").addClass('d-none');
		bookFromType = 'OPD';
		getDepartmentList(bookFromType);
	} else if (appointmentType === "Ambulance") {
		document.getElementById("ambulanceDetails").innerHTML = '';
		document.getElementById("radiologyDetails").innerHTML = '';
		document.getElementById("opdDetails").innerHTML = '';
		document.getElementById("pathologyDetails").innerHTML = '';
		$("#dateOfAppointment").val('');
		$("#timeOfAppointment").val('');
		$('.opd-sec').addClass('d-none');
		$('.amb-sec').removeClass('d-none');
		$('.rad-sec').addClass('d-none');
		$('#dateOfAppointment').parent().parent().removeClass('d-none');
		$('#timeOfAppointment').parent().parent().removeClass('d-none');
		$('#activity').removeClass('d-none');
		$("#testDetailsSection").addClass('d-none');
		bookFromType = 'Ambulance';
		getAllAmbulanceList();
	} else if (appointmentType === "Radiology") {
		document.getElementById("ambulanceDetails").innerHTML = '';
		document.getElementById("radiologyDetails").innerHTML = '';
		document.getElementById("opdDetails").innerHTML = '';
		document.getElementById("pathologyDetails").innerHTML = '';
		$("#dateOfAppointment").val('');
		$("#timeOfAppointment").val('');
		$("#fullName").val('');
		$("#age").val('');
		$("#gender").val('');
		$("#mobNo").val('');
		$('#dateOfAppointment').parent().parent().removeClass('d-none');
		$('#timeOfAppointment').parent().parent().removeClass('d-none');
		$('.rad-sec').removeClass('d-none');
		$('.opd-sec').addClass('d-none');
		$('.amb-sec').addClass('d-none');
		$("#testDetailsSection").removeClass('d-none');
		$('#activity').addClass('d-none');
		$('.br-s-btn-tst').hide();
		getTestList('RAD');
		gridOptionsTest.api.setRowData([]);
	} else if (appointmentType === "Pathology") {
		document.getElementById("ambulanceDetails").innerHTML = '';
		document.getElementById("radiologyDetails").innerHTML = '';
		document.getElementById("opdDetails").innerHTML = '';
		document.getElementById("pathologyDetails").innerHTML = '';
		$("#dateOfAppointment").val('');
		$("#timeOfAppointment").val('');
		$("#fullName").val('');
		$("#age").val('');
		$("#gender").val('');
		$("#mobNo").val('');
		$('#dateOfAppointment').parent().parent().removeClass('d-none');
		$('#timeOfAppointment').parent().parent().removeClass('d-none');
		$('.rad-sec').removeClass('d-none');
		$('.opd-sec').addClass('d-none');
		$('.amb-sec').addClass('d-none');
		$("#testDetailsSection").removeClass('d-none');
		$('#activity').addClass('d-none');
		$('.br-s-btn-tst').hide();
		getTestList('PATH');
		gridOptionsTest.api.setRowData([]);
	} else if (appointmentType === "") {
		document.getElementById("ambulanceDetails").innerHTML = '';
		document.getElementById("radiologyDetails").innerHTML = '';
		document.getElementById("opdDetails").innerHTML = '';
		document.getElementById("pathologyDetails").innerHTML = '';
		$('.rad-sec').addClass('d-none');
		$('.opd-sec').addClass('d-none');
		$('.amb-sec').addClass('d-none');
		$('#dateOfAppointment').parent().parent().addClass('d-none');
		$('#timeOfAppointment').parent().parent().addClass('d-none');
		$('#activity').addClass('d-none');
		$("#testDetailsSection").addClass('d-none');

	}
}
function saveAppointment() {
	let appointmentType = $('#appointmentTypeSelect').val();
	if (appointmentType === "" || appointmentType == null) {
		toastr.error("Please Select Appointment Type");
	} else if (appointmentType === "OPD") {
		if ($("#departmentType").val() == null || $("#departmentType").val() == '') {
			toastr.error("Department required");
			return;
		}
		if ($("#doctor").val() == null || $("#doctor").val() == '') {
			toastr.error("Doctor required");
			return;
		}
		if ($("#dateOfAppointment").val() == null || $("#dateOfAppointment").val() == '') {
			toastr.error("Appointment Date required");
			return;
		}
		if ($("#timeOfAppointment").val() == null || $("#timeOfAppointment").val() == '') {
			toastr.error("Appointment Time required");
			return;
		}
		let opdAppointmentData = {
			appointmentId: $('#appointmentId').val(),
			department: $('#departmentType').val(),
			doctor: $('#doctor').val(),
			doctorFee: $('#docFee').val(),
			appointmentDate: $('#dateOfAppointment').val(),
			appointmentTime: $('#timeOfAppointment').val(),
			appointmentDescription: $('#appointmentDescription').val(),
			appointmentType: appointmentType
		}
		console.log("OPD Appointment Data---------->", opdAppointmentData);
		saveAllAppointmentData(opdAppointmentData);
	} else if (appointmentType === "Ambulance") {
		let ambulanceData = [];
		activityOptions.api.forEachNode(function(rowNode, index) {
			if (rowNode.isSelected()) {
				var item = rowNode.data;
				ambulanceData.push(item);
			}
		});
		if ($("#dateOfAppointment").val() == null || $("#dateOfAppointment").val() == '') {
			toastr.error("Appointment Date required");
			return;
		}
		if ($("#timeOfAppointment").val() == null || $("#timeOfAppointment").val() == '') {
			toastr.error("Appointment Time required");
			return;
		}
		if ($('#typeAmb').val() == null || $('#typeAmb').val() == '') {
			toastr.error("Ambulance Type required");
			return;
		}
		if (ambulanceData?.length == 0) {
			toastr.error("Select an ambulance");
			return;
		}
		let ambulanceAppointmentData = {
			appointmentId: $('#appointmentId').val(),
			appointmentDate: $('#dateOfAppointment').val(),
			appointmentTime: $('#timeOfAppointment').val(),
			ambulanceType: $('#typeAmb').val(),
			ambRequirements: $('#reqAmb').val(),
			ambulanceData: ambulanceData,
			appointmentType: appointmentType
		}
		console.log("Ambulance Appointment Data-------------->", ambulanceAppointmentData);
		saveAllAppointmentData(ambulanceAppointmentData);
	} else if (appointmentType === "Radiology") {
		savePatientWithTest("RAD");
	} else if (appointmentType === "Pathology") {
		savePatientWithTest("PATH");
	}
}
function saveAllAppointmentData(appointmentData) {
	let jsonAppointmentData = JSON.stringify(appointmentData);
	console.log("Json Stringyfy Data", jsonAppointmentData);
	const activeButton = document.querySelector(".appointment-btn.active");
	const onclickAttr = activeButton.getAttribute("onclick");
	const match = onclickAttr && onclickAttr.match(/filterAppointments\('(.+?)'/);
	const type = match ? match[1] : 'All';
	$.ajax({
		url: 'appointment-patient-save-data',
		type: 'POST',
		contentType: 'application/json',
		data: jsonAppointmentData,
		success: function(response) {
			if (response.code === "success") {
				toastr.success(response.message);
				agGrid.simpleHttpRequest({
					url: 'appointment-get-all-bookings'
				}).then(function(data) {
					var jsonData = JSON.parse(data?.body);
					console.log("AllData--<>", jsonData);
					jsonData.forEach(item => {
						if (item.bookingDate) {
							const [datePart, timePart] = item.bookingDate.split(" ");
							const [year, month, day] = datePart.split("-");
							item.date = `${day}-${month}-${year}`;
							item.time = timePart.substring(0, 5);
						}
					});
					let filteredData;
					if (type == "All") {
						filteredData = jsonData;
					} else {
						filteredData = jsonData.filter(item => item.bookingType === appointmentData.appointmentType);
					}
					filteredData.sort((a, b) => {
						const getIdNumber = id => parseInt(id.split('/').pop());
						return getIdNumber(b.bookingId) - getIdNumber(a.bookingId);
					});

					appointmentGridOption.api.setRowData(filteredData);

				});
				let appointmentId = appointmentData.appointmentId;
				setTimeout(() => {
					appointmentGridOption.api.forEachNode((node) => {
						if (appointmentId == "") {
							let firstRow = appointmentGridOption.api.getDisplayedRowAtIndex(0);
							if (firstRow) {
								firstRow.setSelected(true);
							}
						} else if (node.data.bookingId == appointmentId) {
							node.setSelected(true);
						}
					});
				}, 500);
			} else {
			}
		},
		error: function(xhr, status, error) {
			console.log("Error saving data:", error);
		}
	});
}
function formatDateTime(dateStr) {
	const date = new Date(dateStr);

	const day = String(date.getDate()).padStart(2, '0');
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const year = date.getFullYear();

	let hours = date.getHours();
	const minutes = String(date.getMinutes()).padStart(2, '0');
	const seconds = String(date.getSeconds()).padStart(2, '0');
	const ampm = hours >= 12 ? 'PM' : 'AM';
	hours = hours % 12;
	hours = hours ? hours : 12;
	hours = String(hours).padStart(2, '0');

	return `${day}-${month}-${year}, ${hours}:${minutes}:${seconds} ${ampm}`;
}

function editAppointmentData(id, type) {
	$('#addAppointmentDiv').addClass('d-none');
	$('#saveAppointmentBtn').addClass('d-none');
	$.ajax({
		url: 'appointment-edit-booking-data?bookingId=' + id + "&bookingType=" + type,
		type: 'GET',
		success: function(response) {
			if (response.code === "success") {
				const responseBody = JSON.parse(response.body);

				let bookingKey = '';

				switch (type) {
					case 'OPD':
						bookingKey = 'opdDetails';
						break;
					case 'Ambulance':
						bookingKey = 'ambulanceDetails';
						break;
					case 'Radiology':
						bookingKey = 'radiologyDetails';
						break;
					case 'Pathology':
						bookingKey = 'pathologyDetails';
						break;
					default:
						bookingKey = '';
				}

				//toggleFields(type);
				const bookingData = responseBody[bookingKey];
				$("#appointmentId").val(bookingData[0].bookingId);
				if (bookingData && bookingData.length > 0) {
					console.log("Fetched Data: ", bookingData[0]);
					//$("#appointmentTypeSelect").val(bookingData[0].bookingType).trigger('change');

					if (type === "Ambulance") {
						document.getElementById("opdDetails").innerHTML = '';
						document.getElementById("radiologyDetails").innerHTML = '';
						document.getElementById("pathologyDetails").innerHTML = '';
						const data = bookingData[0];
						const cardHTML = `
					  <div class="card p-3 mt-3 shadow-sm rounded-4">
						    <div class="card-body">
						        <h6 class="card-subtitle mb-3 colour-element">
						            <i class="bi bi-truck"></i> Ambulance Booking Details
						        </h6>
						        <div class="row mb-2">
						            <div class="col-6">
						                <i class="bi bi-hash colour-element"></i> <span class="fw-medium colour-element">Booking ID:</span> ${data.bookingId || '-'}
						            </div>
						            <div class="col-6">
						                <i class="bi bi-clipboard2-pulse colour-element"></i> <span class="fw-medium colour-element">Ambulance Number:</span> ${data.ambulanceNumber || '-'}
						            </div>
						        </div>
						        <div class="row mb-2">
						            <div class="col-6">
						                <i class="bi bi-person-badge colour-element"></i> <span class="fw-medium colour-element">Driver Name:</span> ${data.driverName || '-'}
						            </div>
						            <div class="col-6">
						                <i class="bi bi-telephone colour-element"></i> <span class="fw-medium colour-element">Driver Contact:</span> ${data.driverMobile || '-'}
						            </div>
						        </div>
						        <div class="row">
						            <div class="col-12">
						                <i class="bi bi-calendar-event colour-element"></i> <span class="fw-medium colour-element">Booking Date:</span> ${data.bookingDate ? formatDateTime(data.bookingDate) : '-'}
						            </div>
						        </div>
						    </div>
						</div>`;

						document.getElementById("ambulanceDetails").innerHTML = cardHTML;
					} else if (type === "OPD") {
						document.getElementById("ambulanceDetails").innerHTML = '';
						document.getElementById("radiologyDetails").innerHTML = '';
						document.getElementById("pathologyDetails").innerHTML = '';
						const data = bookingData[0];
						const opdCardHTML = `
									    <div class="card p-3 mt-3 shadow-sm rounded-4">
									        <div class="card-body">
									            <h6 class="card-subtitle mb-3 colour-element">
									                <i class="bi-hospital colour-element"></i> OPD Booking Details
									            </h6>
									            <div class="row mb-2">
									                <div class="col-6">
									                    <i class="bi bi-hash colour-element"></i> 
									                    <span class="fw-medium colour-element">Booking ID:</span> ${data.bookingId || '-'}
									                </div>
									                <div class="col-6">
									                    <i class="bi bi-person-badge colour-element"></i> 
									                    <span class="fw-medium colour-element">Doctor Name:</span> ${data.doctorName || '-'}
									                </div>
									            </div>
									            <div class="row mb-2">
									                <div class="col-6">
									                    <i class="bi bi-building colour-element"></i> 
									                    <span class="fw-medium colour-element">Department:</span> ${data.doctorDepartment || '-'}
									                </div>
									                <div class="col-6">
									                    <i class="bi bi-currency-rupee colour-element"></i> 
									                    <span class="fw-medium colour-element">Doctor Fee:</span> ${parseFloat(data.bookingFee || 0).toFixed(2)}
									                </div>
									            </div>
									            <div class="row">
									                <div class="col-12">
									                    <i class="bi bi-calendar-event colour-element"></i> 
									                    <span class="fw-medium colour-element">Booking Date:</span> ${data.bookingDate ? formatDateTime(data.bookingDate) : '-'}
									                </div>
									            </div>
									        </div>
									    </div>
									`;
						document.getElementById("opdDetails").innerHTML = opdCardHTML;

					} else if (type == "Radiology") {
						document.getElementById("opdDetails").innerHTML = '';
						document.getElementById("ambulanceDetails").innerHTML = '';
						document.getElementById("pathologyDetails").innerHTML = '';
						const data = bookingData[0];
						const patientData = responseBody.patientDetails?.[0] || {};

						const radiologyCardHTML = `
							<div class="card p-3 mt-3 shadow-sm rounded-4">
								<div class="card-body">
									<h6 class="card-subtitle mb-3 colour-element">
										<i class="bi bi-file-earmark-medical"></i> Radiology Booking Details
									</h6>
					
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-hash colour-element"></i> 
											<span class="fw-medium colour-element">Booking ID:</span> ${data.bookingId || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-file-earmark-medical colour-element"></i> 
											<span class="fw-medium colour-element">Test(s):</span> ${data.testName || '-'}
										</div>
									</div>
					
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-person colour-element"></i> 
											<span class="fw-medium colour-element">Patient Name:</span> ${patientData.name || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-calendar2-week colour-element"></i> 
											<span class="fw-medium colour-element">Age:</span> ${patientData.age || '-'}
										</div>
									</div>
					
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-gender-ambiguous colour-element"></i> 
											<span class="fw-medium colour-element">Gender:</span> ${patientData.gender || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-telephone colour-element"></i> 
											<span class="fw-medium colour-element">Mobile:</span> ${patientData.mobile || '-'}
										</div>
									</div>
					
									<div class="row">
										<div class="col-12">
											<i class="bi bi-calendar-event colour-element"></i> 
											<span class="fw-medium colour-element">Booking Date:</span> ${data.bookingDate ? formatDateTime(data.bookingDate) : '-'}
										</div>
									</div>
								</div>
							</div>`;

						document.getElementById("radiologyDetails").innerHTML = radiologyCardHTML;
					} else if (type == "Pathology") {
						document.getElementById("opdDetails").innerHTML = '';
						document.getElementById("ambulanceDetails").innerHTML = '';
						document.getElementById("radiologyDetails").innerHTML = '';

						const data = bookingData[0];
						const patientData = responseBody.patientDetails?.[0] || {};

						const pathologyCardHTML = `
							<div class="card p-3 mt-3 shadow-sm rounded-4">
								<div class="card-body">
									<h6 class="card-subtitle mb-3 colour-element">
										<i class="bi bi-droplet-half"></i> Pathology Booking Details
									</h6>
						
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-hash colour-element"></i> 
											<span class="fw-medium colour-element">Booking ID:</span> ${data.bookingId || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-droplet colour-element"></i> 
											<span class="fw-medium colour-element">Test(s):</span> ${data.testName || '-'}
										</div>
									</div>
						
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-person colour-element"></i> 
											<span class="fw-medium colour-element">Patient Name:</span> ${patientData.name || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-calendar2-week colour-element"></i> 
											<span class="fw-medium colour-element">Age:</span> ${patientData.age || '-'}
										</div>
									</div>
						
									<div class="row mb-2">
										<div class="col-6">
											<i class="bi bi-gender-ambiguous colour-element"></i> 
											<span class="fw-medium colour-element">Gender:</span> ${patientData.gender || '-'}
										</div>
										<div class="col-6">
											<i class="bi bi-telephone colour-element"></i> 
											<span class="fw-medium colour-element">Mobile:</span> ${patientData.mobile || '-'}
										</div>
									</div>
						
									<div class="row">
										<div class="col-12">
											<i class="bi bi-calendar-event colour-element"></i> 
											<span class="fw-medium colour-element">Booking Date:</span> ${data.bookingDate ? formatDateTime(data.bookingDate) : '-'}
										</div>
									</div>
								</div>
							</div>`;

						document.getElementById("pathologyDetails").innerHTML = pathologyCardHTML;


					}
				} else {
					toastr.warning("No booking data found.");
				}
			} else {
				toastr.error("Failed to fetch data");
			}
		},
		error: function() {
			toastr.error("Something went wrong with the request");
		}
	});
}
function disableFields() {
	const fieldsToDisable = [
		"#appointmentTypeSelect",
		"#departmentType",
		"#doctor",
		"#appointmentDescription"
	];

	fieldsToDisable.forEach(selector => {
		$(selector).prop("disabled", true);
	});
}
function enableFields() {
	const fieldsToDisable = [
		"#appointmentTypeSelect",
		"#departmentType",
		"#doctor",
		"#appointmentDescription"
	];

	fieldsToDisable.forEach(selector => {
		$(selector).prop("disabled", false);
	});
}
function rescheduleAppointment() {
	$('#cancelBtn').removeClass('d-none');
	$('#editAppointment').addClass('d-none');
	$('#addAppointmentDiv').removeClass('d-none');
	$('#saveAppointmentBtn').removeClass('d-none');
	let selectedNodes = appointmentGridOption.api.getSelectedNodes();
	let selectedData = selectedNodes.map(node => node.data);
	let bookingid = selectedData[0].bookingId;
	let appointmentType = selectedData[0].bookingType;
	toggleFields(appointmentType);
	$('#appointmentTypeSelect').val(appointmentType);
	disableFields();
	$.ajax({
		url: 'appointment-edit-booking-data?bookingId=' + bookingid + "&bookingType=" + appointmentType,
		type: 'GET',
		success: function(response) {
			if (response.code == 'success') {
				const responseBody = JSON.parse(response.body);

				const bookingKey = appointmentType === "OPD" ? "opdDetails" : "ambulanceDetails";
				//toggleFields(type);
				const bookingData = responseBody[bookingKey];
				$("#appointmentId").val(bookingData[0].bookingId);
				if (bookingData && bookingData.length > 0) {
					console.log("Fetched Data: ", bookingData[0]);
					//$("#appointmentTypeSelect").val(bookingData[0].bookingType).trigger('change');

					if (appointmentType === "Ambulance") {
						$('#activity').addClass('d-none');
						document.getElementById("opdDetails").innerHTML = '';
						const data = bookingData[0];
						let formattedDate = '-', formattedTime = '-';
						if (data.bookingDate) {
							const [datePart, timePart] = data.bookingDate.split(" ");
							const [year, month, day] = datePart.split("-");
							formattedDate = `${day}-${month}-${year}`;
							formattedTime = timePart.substring(0, 5);
						}

						$("#dateOfAppointment").val(formattedDate);
						$("#timeOfAppointment").val(formattedTime);
						$("#typeAmb").val(data.ambulanceType);
						$("#reqAmb").val(data.ambRequirement);
						const cardHTML = `
					  <div class="card p-3 mt-3 shadow-sm rounded-4">
						    <div class="card-body">
						        <h6 class="card-subtitle mb-3 colour-element">
						            <i class="bi bi-truck"></i> Ambulance Booking Details
						        </h6>
						        <div class="row mb-2">
						            <div class="col-6">
						                <i class="bi bi-hash colour-element"></i> <span class="fw-medium colour-element">Booking ID:</span> ${data.bookingId || '-'}
						            </div>
						            <div class="col-6">
						                <i class="bi bi-clipboard2-pulse colour-element"></i> <span class="fw-medium colour-element">Ambulance Number:</span> ${data.ambulanceNumber || '-'}
						            </div>
						        </div>
						        <div class="row mb-2">
						            <div class="col-6">
						                <i class="bi bi-person-badge colour-element"></i> <span class="fw-medium colour-element">Driver Name:</span> ${data.driverName || '-'}
						            </div>
						            <div class="col-6">
						                <i class="bi bi-telephone colour-element"></i> <span class="fw-medium colour-element">Driver Contact:</span> ${data.driverMobie || '-'}
						            </div>
						        </div>
						        <div class="row">
						            <div class="col-12">
						                <i class="bi bi-calendar-event colour-element"></i> <span class="fw-medium colour-element">Booking Date:</span> ${data.bookingDate ? new Date(data.bookingDate).toLocaleDateString('en-GB') : '-'}

						            </div>
						        </div>
						    </div>
						</div>`;

						document.getElementById("ambulanceDetails").innerHTML = cardHTML;
					} else if (appointmentType === "OPD") {
						document.getElementById("ambulanceDetails").innerHTML = '';
						const data = bookingData[0];

						let formattedDate = '-', formattedTime = '-';
						if (data.bookingDate) {
							const [datePart, timePart] = data.bookingDate.split(" ");
							const [year, month, day] = datePart.split("-");
							formattedDate = `${day}-${month}-${year}`;
							formattedTime = timePart.substring(0, 5);
						}

						$("#dateOfAppointment").val(formattedDate);
						$("#timeOfAppointment").val(formattedTime);
						$("#appointmentDescription").val(data.description);

						setTimeout(() => {
							$("#departmentType").val(data.doctorCategory).trigger('change');

							setTimeout(() => {
								$("#doctor").val(data.doctor).trigger('change');
								$("#docFee").val(parseFloat(data.bookingFee).toFixed(2));
							}, 200);
						}, 200);

					}
				} else {
					toastr.warning("No booking data found.");
				}
			}
		}
	})
}
function resetAppointmentUI() {
	$('#cancelBtn').addClass('d-none');
	$('#addBtnId').removeClass('d-none');
	$('#editAppointment').addClass('d-none');
	$('#deleteAppointment').addClass('d-none');
	$('#addAppointmentDiv').removeClass('d-none');
	$('#saveAppointmentBtn').removeClass('d-none');

	$('#departmentType').val('');
	$('#doctor').val('');
	$('#docFee').val('');
	$('#dateOfAppointment').val('');
	$('#timeOfAppointment').val('');
	$('#appointmentDescription').val('');
	$('#appointmentId').val('');
	$("#typeAmb").val('');
	$("#reqAmb").val('');
	//$('#appointmentTypeSelect').val('').trigger('change');

	enableFields();
}

function deleteAppointment() {
	let selectedNodes = appointmentGridOption.api.getSelectedNodes();
	let selectedData = selectedNodes.map(node => node.data);
	let appointmentId = selectedData[0].bookingId;
	let appointmentType = selectedData[0].bookingType;
	const activeButton = document.querySelector(".appointment-btn.active");
	const onclickAttr = activeButton.getAttribute("onclick");
	const match = onclickAttr && onclickAttr.match(/filterAppointments\('(.+?)'/);
	const type = match ? match[1] : 'All';
	$.ajax({
		url: 'appointment-delete-data?appointmentId=' + appointmentId + "&appointmentType=" + appointmentType,
		type: 'GET',
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				const rowCount = appointmentGridOption.api.getDisplayedRowCount();
				if (rowCount === 0) {
					resetAppointmentUI(); // or use your custom clear function
				}
				const activeButton = document.querySelector(".appointment-btn.active");
				if (activeButton) {
					filterAppointments(type, activeButton);
				}
			} else {
				toastr.error(response.message);
			}
		}
	})
}

/*Radiology Code*/

/*Test Ag Grid */
var columnDefs2 = [{
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
	headerName: "Test Name",
	field: "name",
},
{
	headerName: "Test Category",
	field: "data",
}
];

var gridOptionsTest = {
	columnDefs: columnDefs2,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: rowSelect2
};

function rowSelect2() {
	var selectedRows = gridOptionsTest.api.getSelectedRows();
	if (selectedRows && selectedRows.length > 0) {
		$(".br-dis-tst").attr("disabled", false);
	} else {
		$(".br-dis-tst").attr("disabled", true);
	}
}
function addTest() {
	$(".br-m-btn-tst").hide();
	$(".br-s-btn-tst").show();

	$("#testCategory,#testCategoryId").val('');
	$("#testName").val('').trigger('change');
}

function cancelTest() {
	$(".br-m-btn-tst").show();
	$(".br-s-btn-tst").hide();

	$("#testCategory,#testCategoryId").val('');
	$("#testName").val('').trigger('change');
}
function getTestList(type) {

	$("#testName").empty();
	$("#testName").append("<option value=''>Select</option>");

	$.ajax({
		type: "GET",
		url: "/lookup/lookups-testLists?type=" + type,
		success: function(response) {
			if (response.code == "success") {
				let data = JSON.parse(response.body)
				data = data.sort((a, b) => a.test_name.localeCompare(b.test_name));
				for (var i = 0; i < data.length; i++) {
					var option = $("<option></option>");
					$(option).val(data[i].test_id);
					$(option).html(data[i].test_name);
					$(option).attr('data-grp-id', data[i].group_id);
					$(option).attr('data-grp-name', data[i].group_name);
					$("#testName").append(option);
				}
			}
		},
		error: function(e) {
		}
	});
}

function setTestCategory() {
	let groupId = $("#testName option:selected").attr('data-grp-id');
	let groupName = $("#testName option:selected").attr('data-grp-name');

	$("#testCategory").val(groupName)
	$("#testCategoryId").val(groupId)
}

function saveTest() {
	let rowData = [];
	gridOptionsTest.api.forEachNode((node) => {
		rowData.push(node.data);
	});

	let testId = $("#testName").val();

	if (testId == null || testId == '') {
		toastr.error("Select test");
		return;
	}

	let selectedData = rowData?.filter(a => a.key == testId);

	if (selectedData && selectedData.length > 0) {
		toastr.error("This test is already added");
		$("#testCategory,#testCategoryId").val('');
		$("#testName").val('').trigger('change');
		return;
	}

	let testName = $("#testName option:selected").text();

	let obj = {};

	obj.key = testId;
	obj.name = testName;
	obj.code = $("#testCategoryId").val()
	obj.data = $("#testCategory").val()

	rowData.push(obj)

	gridOptionsTest.api.setRowData(rowData);
	cancelTest();

}
function deleteTest() {
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
			var selectedNodes = gridOptionsTest.api.getSelectedNodes();
			var selectedData = selectedNodes.map(node => node.data);

			let rowData = [];
			gridOptionsTest.api.forEachNode((node) => {
				rowData.push(node.data);
			});

			let result = rowData.filter(item1 => !selectedData.some(item2 => item1.key === item2.key));

			if (result.length == 0) {
				$(".br-dis-tst").attr("disabled", true);
			}

			gridOptionsTest.api.setRowData(result)

		}
	})
}
var mobValid1;

function mobVal1() {
	var mob = $('#mobNo').val();
	var phoneno = /^\d{10}$/;
	if (mob != '') {
		if (phoneno.test(mob)) {

			$("#error9").hide();

			mobValid1 = true;
			return true;
		} else {

			$("#error9").html(
				"please enter  10 digit mobile number");
			$("#error9").show();
			mobValid1 = false;
			return false;
		}
	} else {
		$("#error9").html(" Mobile No is Required");
		mobValid1 = false;
		return false;
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
function savePatientWithTest(appointmentType) {

	let patientId = "SSU/CUST/0000011"; /*$("#userId").val();*/
	let fullName = $("#fullName").val();
	let age = $("#age").val();
	let mobNo = $("#mobNo").val();
	let altMobNo = $("#altMobNo").val();
	let email = $("#email").val();
	let gender = $("#gender").val();
	let country = $("#country").val();
	let state = $("#states").val();
	let dist = $("#dist").val();
	let city = $("#cityId").val();
	let address = $("#address").val();
	let zipCode = $("#zipCode").val();
	let appointmentDate = $("#dateOfAppointment").val();
	let apppointmentTime = $("#timeOfAppointment").val();
	let type = appointmentType;

	if (appointmentDate == null || appointmentDate == '') {
		toastr.error("Appointment Date required");
		return;
	}

	if (apppointmentTime == null || apppointmentTime == '') {
		toastr.error("Appointment Time required");
		return;
	}

	if (fullName == null || fullName == '') {
		toastr.error("Full name required");
		return;
	}
	if (age == null || age == '') {
		toastr.error("Age required");
		return;
	}
	if (mobNo == null || mobNo == '') {
		toastr.error("Mobile number required");
		return;
	}
	if (gender == null || gender == '') {
		toastr.error("Gender required");
		return;
	}
	/* if (country == null || country == '') {
	  toastr.error("Country required");
	  return;
	}
	if (state == null || state == '') {
	  toastr.error("State required");
	  return;
	}
	if (dist == null || dist == '') {
	  toastr.error("District required");
	  return;
	}
	if (city == null || city == '') {
	  toastr.error("City required");
	  return;
	}
	if (address == null || address == '') {
	  toastr.error("Address required");
	  return;
	}
	if (zipCode == null || zipCode == '') {
	  toastr.error("Pincode required");
	  return;
	} */

	let testList = [];
	gridOptionsTest.api.forEachNode((node) => {
		testList.push(node.data);
	});

	if (testList.length == 0) {
		toastr.error("You have to add atleast one test")
		return;
	}

	let obj = { type, patientId, fullName, age, mobNo, altMobNo, email, gender, country, state, dist, city, address, zipCode, testList, apppointmentTime, appointmentDate };
	console.log("Object For Radiology------>", obj);

	$('.loader').show();
	$("body").addClass("overlay");

	const activeButton = document.querySelector(".appointment-btn.active");
	const onclickAttr = activeButton.getAttribute("onclick");
	const match = onclickAttr && onclickAttr.match(/filterAppointments\('(.+?)'/);
	const appType = match ? match[1] : 'All';
	$.ajax({
		type: "POST",
		url: "appointment-registration-with-test-list",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code == "success") {

				let test_id = '';

				if (response.body && response.body.length > 0) {
					test_id = response.body[0];
				}
				filterAppointments(appType, activeButton);
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success('Patient Appointment Registered successfully');

			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}

		},
		error: function(datas) {
			console.log(datas)
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})

}