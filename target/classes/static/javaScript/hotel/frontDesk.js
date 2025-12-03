let dataset = [
	{
		id: '1',
		title: 'Room 101 - Sharma Family (Deluxe)',
		start: '2025-05-20T12:00:00',
		end: '2025-05-22T10:00:00',
		color: '#FFA500',
		extendedProps: {
			guests: 4,
			phone: '+91 9876543210',
			status: 'confirmed',
			notes: 'Early check-in requested'
		}
	},
	{
		id: '2',
		title: 'Room 201 - Patel Business (Suite)',
		start: '2025-05-21T14:00:00',
		end: '2025-05-23T11:00:00',
		color: '#4169E1',
		extendedProps: {
			guests: 2,
			phone: '+91 8765432109',
			status: 'confirmed',
			notes: 'Airport pickup needed'
		}
	},
	{
		id: '3',
		title: 'Room 102 - Kumar Wedding Party (3 Rooms)',
		start: '2025-05-22T10:00:00',
		end: '2025-05-25T12:00:00',
		color: '#32CD32',
		extendedProps: {
			guests: 6,
			phone: '+91 7654321098',
			status: 'confirmed',
			notes: 'Honeymoon suite decoration'
		}
	},
	{
		id: '4',
		title: 'Room 301 - Gupta Family (Standard)',
		start: '2025-05-23T13:00:00',
		end: '2025-05-26T10:00:00',
		color: '#9370DB',
		extendedProps: {
			guests: 3,
			phone: '+91 6543210987',
			status: 'confirmed',
			notes: 'Extra bed required'
		}
	},
	{
		id: '5',
		title: 'Room 202 - Singh Business (Deluxe)',
		start: '2025-05-24T16:00:00',
		end: '2025-05-27T09:00:00',
		color: '#FFA500',
		extendedProps: {
			guests: 2,
			phone: '+91 9432109876',
			status: 'confirmed',
			notes: 'Vegetarian meals only'
		}
	},
	{
		id: '6',
		title: 'Room 302 - Iyer Family (Suite)',
		start: '2025-05-25T12:00:00',
		end: '2025-05-28T11:00:00',
		color: '#4169E1',
		extendedProps: {
			guests: 5,
			phone: '+91 8321098765',
			status: 'confirmed',
			notes: 'Anniversary celebration'
		}
	},
	{
		id: '7',
		title: 'Room 103 - Nair Couple (Standard)',
		start: '2025-05-26T14:00:00',
		end: '2025-05-29T10:00:00',
		color: '#9370DB',
		extendedProps: {
			guests: 2,
			phone: '+91 7210987654',
			status: 'tentative',
			notes: 'Waiting for confirmation'
		}
	},
	{
		id: '8',
		title: 'Room 203 - Mehta Group (4 Rooms)',
		start: '2025-05-27T10:00:00',
		end: '2025-05-30T12:00:00',
		color: '#32CD32',
		extendedProps: {
			guests: 8,
			phone: '+91 6109876543',
			status: 'confirmed',
			notes: 'Corporate booking'
		}
	}
];

let calendar = '';
$(document).ready(() => {
	toggleList();
	var calendarEl = document.getElementById('calendar');
	calendar = new FullCalendar.Calendar(calendarEl, {
		height: '100%',
		expandRows: true,
		slotMinTime: '06:00',
		slotMaxTime: '22:00',
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
			right: 'multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay,listWeek,nextTwoDays'
		},
		initialView: 'dayGridMonth',
		editable: true,
		selectable: true,
		nowIndicator: true,
		dayMaxEvents: 0,
		eventTimeFormat: {
			hour: 'numeric', // Hour in numeric format
			minute: '2-digit', // Minute in 2-digit format
			meridiem: 'short' // Disable default meridiem formatting
		},
		eventContent: function(arg) {
			/*  let time = arg.timeText; // The default time text
			 if (time) {
				 time = time.replace(/(am|pm)$/i, ' $1').toUpperCase(); // Add space before AM/PM
			 } */
			return {
				html: `${arg.event.title}`
			}; // Customize event content
		},
		navLinks: true,
		customButtons: {
			nextTwoDays: {
				text: 'Next 2 Days',
				click: function() {
					var today = new Date();
					calendar.changeView('customTwoDay', today);
				}
			}
		},
		views: {
			customTwoDay: {
				type: 'timeGrid',
				duration: {
					days: 2
				},
				buttonText: '2 day',
				slotDuration: '01:00:00'
			}
		},
		events: dataset,

		dateClick: function(info) {
			$('#allDay').prop('checked', false);
			$("#eventEndTime").attr("disabled", false);
			$("#eventStartTime").attr("disabled", false);

			let currentTime = new Date();
			currentTime.setMinutes(currentTime.getMinutes() + 1);

			let startHours = String(currentTime.getHours()).padStart(2, '0');
			let startMinutes = String(currentTime.getMinutes()).padStart(2, '0');
			let endHours = String(currentTime.getHours() + 1).padStart(2, '0');


			let currentDate = new Date();
			let clickedDate = new Date(info.dateStr);


			if (clickedDate < currentDate.setHours(0, 0, 0, 0)) {
				alert('Cannot add meetings to past dates.')
				return;
			}
		},
		eventClick: function(info) {

		},
		datesSet: function(info) {

		},
		eventDrop: function(info) {

			let eventStart = info.event.start;
			let currentDateTime = new Date();

			if (eventStart < currentDateTime) {
				alert('Cannot set event to a past time.');
				info.revert();
			} else {
				// Proceed with the update if the new time is valid
			}
		},
		eventResize: function(info) {
			let eventEnd = info.event.end;
			let currentDateTime = new Date();

			if (eventEnd < currentDateTime) {
				alert('Cannot extend event to a past time.');
				info.revert(); // Revert to the original size
			} else {
			}
		},
	});

	calendar.render();
	console.log("Calendar events:", calendar.getEvents());
})

$(document).ready(function() {

	/*$("#saveGoal").hide();
	$("#cancelGoal").hide();

	$("#saveCorporateGuest").hide();
	$("#cancelDepGoal").hide();

	$("#saveDesigGoal").hide();
	$("#cancelDesigGoal").hide();*/


	var gridDiv = document.querySelector('#myGridDep');
	new agGrid.Grid(gridDiv, gridOptionsDep);
	let type = "Corporates,Other Guest,Corporate Guest";
	getCorporateDetails(type);

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	getReservationHotelDetails();
	gridOptions.api.setRowData([]);
	gridOptionsDep.api.setRowData([]);

	//reservation
	$("#guestType").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#bookingCustomerName").select2({
		placeholder: "Select the Customer You Want to Book For ",
		allowClear: true
	});

	$("#roomType").select2({
		placeholder: "Select ",
		allowClear: true
	});

	$("#country").select2({
		placeholder: "Select Country",
		allowClear: true
	});

	$("#state").select2({
		placeholder: "Select State",
		allowClear: true
	});

	$("#city").select2({
		placeholder: "Select City",
		allowClear: true
	});

	$("#source").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#status").select2({
		placeholder: "Select ",
		allowClear: true
	});
	//checkin
	$("#guestTypeCheckin").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#roomTypeCheckin").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#roomNumber").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#paymentStatus").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#statusCheckin").select2({
		placeholder: "Select ",
		allowClear: true
	});
	//checkout
	$("#checkoutRoomNumber").select2({
		placeholder: "Select ",
		allowClear: true
	});
	$("#checkoutRoomType").select2({
		placeholder: "Select ",
		allowClear: true
	});

	/*gridOptions.api.addEventListener('selectionChanged', function() {
		const selectedRow = gridOptions.api.getSelectedRows()[0]; // Get the first selected row
		if (selectedRow) {
			populateCheckinTabFromReservation(selectedRow); // Pass selected row data to the function
			populateCheckoutTabFromReservation(selectedRow);
		}
	});*/
	$('#goToCheckinBtn').on('click', function() {
		const tabTrigger = new bootstrap.Tab(document.querySelector('button[data-bs-target="#operation-checkInTab"]'));
		tabTrigger.show();
		setDataOnCheckin();
	});
	$('#goToCheckoutBtn').on('click', function() {
		const tabTrigger = new bootstrap.Tab(document.querySelector('button[data-bs-target="#operation-checkOutTab"]'));
		tabTrigger.show();
	});

	//When The User Click On The Enter Button also The Search Features Work .
	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			onQuickFilterChanged();
		} else if (event.key === "Escape") {
			ClearBtn();
		}
	});

	$('#reservationForm').find('input, select, button,textarea').prop('disabled', true);


	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
		+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").html(tbl);




});

$(document).ready(function() {
	$('#idImage').on('change', function(e) {
		const files = e.target.files;
		const previewContainer = $('#imagePreviewContainer');
		const fileNameDisplay = $('#photoFileName');

		// Clear previous previews
		previewContainer.empty();

		if (files.length > 0) {
			// Update the file name display
			fileNameDisplay.text(files.length + ' file(s) selected');

			// Process each selected file
			$.each(files, function(index, file) {
				if (!file.type.match('image.*')) {
					return; // Skip non-image files
				}

				const reader = new FileReader();

				reader.onload = function(e) {
					// Create image preview element
					const imgWrapper = $('<div>').css({
						'position': 'relative',
						'width': '100px',
						'height': '100px',
						'border': '1px solid #969696'
					});

					const img = $('<img>').attr('src', e.target.result).css({
						'width': '100%',
						'height': '100%',
						'object-fit': 'cover',
						'cursor': 'pointer'
					});

					// Open image in new tab when clicked
					img.on('click', function() {
						const imageWindow = window.open();
						$(imageWindow.document.body).html(
							$('<img>').attr('src', e.target.result).css({
								'max-width': '100%',
								'max-height': '100%'
							})
						);
					});

					imgWrapper.append(img);
					previewContainer.append(imgWrapper);
				};

				reader.readAsDataURL(file);
			});
		} else {
			fileNameDisplay.text('No files chosen');
		}
	});
});

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
	var extension = fileName.split(".").pop();
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");


	console.log("extension is-->", extension);
	if (extension == 'pdf' || extension == 'jpg' || extension == 'png' || extension == 'xls' || extension == 'doc' || extension == 'webp' || extension == 'xlsx' || extension == 'docx' || extension == 'txt') {
		window.st = 1;
	} else {
		toastr.error("Invalid file type! Please upload a valid document file. Supported formats: PDF, JPG, PNG, XLS, DOC, WEBP, XLSX, DOCX, TXT.");
		window.st = 0;

		var lengthOfTableRow1 = 0;
		$("#docTbl > #doctbodyData > tr").each(function() {
			lengthOfTableRow1 = lengthOfTableRow1 + 1;
		})
		var id = $("#dltValue").val();
		$("#" + id).closest('tr').remove();
		if (lengthOfTableRow1 == 1) {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
				+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}
	}
	if (extension != null && extension != "") {
		$("#uploadHidden_" + counter).val('');
	}
	var LightImg = "";

	if (["jpg", "png", "jpeg", "webp"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-image img-color'></i></a></span>";
	} else if (extension.toLowerCase() === "pdf") {
		LightImg = "<span class='uploadicon position-l bdr-n'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-pdf img-color'></i></a></span>";
	} else if (["xls", "xlsx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-excel img-color'></i></a></span>";
	} else if (["doc", "docx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-word img-color'></i></a></span>";
	} else if (["mp4", "mov"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-video img-color'></i></a></span>";
	} else if (["mp3", "wav", "aac"].includes(extension.toLowerCase())) {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-audio img-color'></i></a></span>";
	} else {
		LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file img-color'></i></a></span>";
	}

	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm(0)'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);
	$("#dltImage_" + counter).addClass("custom-file-delete");
	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");
	$("#dltImage_0").show();
}

function openDeleteConfirm(index) {
	$("#uploadedBillDiv_" + index).html("");
	$("#uploadHidden_" + index).val("");
	$("#uploadDoc_" + index).val("");
	$("#imageName_" + index).text("");

	$("#dltImage_" + index).hide();
	$("#uploadFor_" + index).show();
	$("#clickImg_" + index).addClass("ti-plus").removeClass("ti-pencil");

}





var columnDefsDep = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 10,
		suppressSizeToFit: true,
	},
	{
		headerName: "Customer ID",
		field: "regId",
		width: 150,
		hide: true
	},
	{
		headerName: "Name",
		field: "custName",
		width: "200",
		cellStyle: { textAlign: "leftAligned" },
		cellRenderer: function(params) {

			const name = params.value || '';
			const createdOn = params.data.createdOn || '';

			let createdDate;
			if (createdOn) {
				const [day, month, year] = createdOn.split('-').map(Number);
				createdDate = new Date(year, month - 1, day);
			}

			const today = new Date();
			const timeDiff = createdDate ? today - createdDate : 0;
			const daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));

			let badgeText = '';
			let badgeStyle = '';

			// Determine days-based badge
			if (!createdOn || isNaN(daysDiff)) {
				// Handle invalid or missing date, show only job title and open badge (if applicable)
				return `
			                <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
			                    <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
			                </div>
			            `;
			}

			if (daysDiff === 0) {
				badgeText = 'New';
				badgeStyle = `
			                background: rgba(52, 168, 83, 0.2);
			                color: #34a853;
			                font-size: 10px;
			                font-weight: 600;
			                padding: 2px 6px 0px 7px;
			                border-radius: 10px;
			                display: inline-flex;
			                align-items: center;
			                backdrop-filter: blur(2px);
			                white-space: nowrap;
			                line-height: 17px;
			            `;
			} else if (daysDiff >= 1 && daysDiff <= 6) {
				// 1 to 6 days ago: Orange badge
				badgeText = daysDiff === 1 ? '1 day ago' : `${daysDiff} days ago`;
				badgeStyle = `
			                background: rgba(255, 165, 0, 0.2);
			                color: #ff8c00;
			                font-size: 10px;
			                font-weight: 600;
			                padding: 2px 6px 0px 7px;
			                border-radius: 10px;
			                display: inline-flex;
			                align-items: center;
			                backdrop-filter: blur(2px);
			                white-space: nowrap;
			                line-height: 17px;
			            `;
			} else if (daysDiff >= 7 && daysDiff <= 20) {
				// 7 to 20 days ago: Orange badge, show weeks for 7 or 14 days, otherwise days
				if (daysDiff === 7) {
					badgeText = '1 week ago';
				} else if (daysDiff === 14) {
					badgeText = '2 weeks ago';
				} else {
					badgeText = `${daysDiff} days ago`;
				}
				badgeStyle = `
			                background: rgba(255, 165, 0, 0.2);
			                color: #ff8c00;
			                font-size: 10px;
			                font-weight: 600;
			                padding: 2px 6px 0px 7px;
			                border-radius: 10px;
			                display: inline-flex;
			                align-items: center;
			                backdrop-filter: blur(2px);
			                white-space: nowrap;
			                line-height: 17px;
			            `;
			} else if (daysDiff >= 30) {
				badgeText = `${daysDiff} days Old`;
				badgeStyle = `
			                background: rgba(128, 128, 128, 0.2);
			                color: #666;
			                font-size: 10px;
			                font-weight: 600;
			                padding: 2px 6px 0px 7px;
			                border-radius: 10px;
			                display: inline-flex;
			                align-items: center;
			                backdrop-filter: blur(2px);
			                white-space: nowrap;
			                line-height: 17px;
			            `;
			} else {
				badgeText = `${daysDiff} days ago`;
				badgeStyle = `
			                background: rgba(255, 165, 0, 0.2);
			                color: #ff8c00;
			                font-size: 10px;
			                font-weight: 600;
			                padding: 2px 6px 0px 7px;
			                border-radius: 10px;
			                display: inline-flex;
			                align-items: center;
			                backdrop-filter: blur(2px);
			                white-space: nowrap;
			                line-height: 17px;
			            `;
			}

			// Combine job title, days badge, and open badge (if applicable)
			return `
			            <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
			                <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
			                ${badgeText ? `<span style="${badgeStyle}">${badgeText}</span>` : ''}
			            </div>
			        `;
		}
	},
	{
		headerName: "Status",
		field: "status",
		width: 100,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			const status = params.data.status;
			const statusMap = {
				Active: { class: "bg-active-badge", label: "Active" },
				Inactive: { class: "bg-close", label: "Inactive" },
			};

			const { class: badgeClass, label } = statusMap[status] || statusMap["Created"];

			return `<span class="badge ${badgeClass}" style="width: 70px; display: inline-block; text-align: center;">${label}</span>`;
		},
	},
	{
		headerName: "Customer Type",
		field: "custType",
		width: "150",


	},
	{
		headerName: "Email",
		field: "email",
		width: "150"
	},
	{
		headerName: "Phone",
		field: "phone",
		width: "150"
	},
	{
		headerName: "Address",
		field: "address1",
		width: 250,
		valueGetter: function(params) {
			const addr1 = params.data.address1 || '';
			const addr2 = params.data.address2 || '';
			return `${addr1}${addr2 ? ', ' + addr2 : ''}`;
		}
	},
	{
		headerName: "City",
		field: "city",
		width: "100"

	},
	{
		headerName: "State",
		field: "state",
		width: "100"

	},

	{
		headerName: "Country",
		field: "country",
		width: "100"

	},
	{
		headerName: "Postal Code",
		field: "postalcode",
		width: "150"

	},
	{
		headerName: "Payment Frequency",
		field: "pf",
		width: "150",
		hide: true

	},
	{
		headerName: "Created On",
		field: "createdOn",
		width: "150",

	}
];

// Define grid options
var gridOptionsDep = {
	columnDefs: columnDefsDep,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 100
	},
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelect
};

function rowSelect() {
	var selectedRows = gridOptionsDep.api.getSelectedRows();


	if (selectedRows.length > 0) {
		const regId = selectedRows[0].regId;
		const custName = selectedRows[0].custName;
		const custType = selectedRows[0].custType;

		editCorporate(regId);
		$(".custName").html(custName + " (" + custType + ")");
	} else {
		$(".custName").html("");
	}
}


document.addEventListener("DOMContentLoaded", function() {
	$("#name, #custType, #tax, #email, #phone, #address1, #address2, #country, #state, #city, #postalCode, #nextReminder, #fname, #lname, #corporate, #idProof, #empId, #photo")
		.attr("disabled", true);
	$("#photoLabel").addClass('disabled-label');

	document.querySelectorAll(".nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleTabClick(target);
		});
	});

	document.querySelectorAll(".custom-tab-group .nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleFilterClick(target);
		});
	});

	document.getElementById("photo").addEventListener("change", function() {
		const fileName = this.files[0] ? this.files[0].name : "No file chosen";
		document.getElementById("photoFileName").textContent = fileName;
	});


});

function filter(type) {
	let status = "ALL";

	switch (type) {
		case '0':
			status = "ALL";
			break;
		case '1':
			status = "Active";
			break;
		case '2':
			status = "Awaiting";
			break;
		case '3':
			status = "Upcoming";
			break;
		default:
			status = "ALL";
	}


	document.querySelectorAll('.child').forEach(btn => {
		btn.classList.remove('active');
	});


	let activeBtn = Array.from(document.querySelectorAll('.child'))
		.find(btn => btn.getAttribute('onclick').includes(`filter('${type}')`));

	if (activeBtn) {
		activeBtn.classList.add('active');
	}

	getReservationHotelDetails(status);
}



function handleTabClick(target) {
	if (target === "#frontdesk-operation") {
		document.querySelector('[data-bs-target="#operation-reservationTab"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#operation-reservationTab"]').click();
		document.querySelector('[data-bs-target="#contacts-info"]').closest("li").style.display = "none";
		/*document.querySelector('[data-bs-target="#contacts-frequency"]').closest("li").style.display = "none";*/
		document.querySelector('[data-bs-target="#operation-checkOutTab"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#operation-checkInTab"]').closest("li").style.display = "block";
		document.querySelector('#holeContainer').style.display = "block";
		document.querySelector('#dashboard').style.display = "none";
		document.querySelector('#dashboard').style.display = "none";

	} else if (target === "#frontdesk-contacts") {
		document.querySelector('[data-bs-target="#operation-reservationTab"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#contacts-info"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#operation-checkOutTab"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#operation-checkInTab"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#contacts-info"]').click();
		/*document.querySelector('[data-bs-target="#contacts-frequency"]').closest("li").style.display = "block";*/
		document.querySelector('#holeContainer').style.display = "block";
		document.querySelector('#dashboard').style.display = "none";
		//handleFilterClick('#corporates');
	} else if (target === "#frontdesk-dashboard") {
		document.querySelector('[data-bs-target="#operation-reservationTab"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#contacts-info"]').closest("li").style.display = "none";
		/*document.querySelector('[data-bs-target="#contacts-frequency"]').closest("li").style.display = "none";*/
		document.querySelector('#holeContainer').style.display = "none";
		document.querySelector('#dashboard').style.display = "block";
	}
}
function goToOperationFromContacts() {
	const operationTabBtn = document.querySelector('button[data-bs-toggle="pill"][data-bs-target="#frontdesk-operation"]');

	if (operationTabBtn) {
		gridOptions.api.deselectAll();
		const tab = new bootstrap.Tab(operationTabBtn);
		tab.show();

		operationTabBtn.addEventListener('shown.bs.tab', function() {
			handleTabClick('#frontdesk-operation');
			let contactName = $("#name").val();
			let guestType = $("#custType").val();
			var selectedRows = gridOptionsDep.api.getSelectedRows();
			const regId = selectedRows[0].regId;
			if (!contactName) {
				toastr.error("Contact Name is Required");
				return;
			} if (!guestType) {
				toastr.error("Customer type is Required");
				return;
			}

			// Calculate dates
			const today = new Date();
			const tomorrow = new Date(today);
			tomorrow.setDate(today.getDate() + 1);

			const dayAfterTomorrow = new Date(tomorrow);
			dayAfterTomorrow.setDate(tomorrow.getDate() + 1);

			// Format dates as YYYY-MM-DD (HTML date input format)
			const formatDate = (date) => {
				return date.toISOString().split('T')[0];
			};
			$("#custName").val("");
			$("#checkinDate").val(formatDate(tomorrow));
			$("#checkoutDate").val(formatDate(dayAfterTomorrow));
			$("#bookingCustomerName").val(regId).trigger('change');
			$("#roomType").val("").trigger('change');
			$("#guestType").val(guestType).trigger('change');
			$("#noofRooms").val("");
			$("#noofAdults").val("");
			$("#noofChildren").val("");
			$("#totalAmount").val("");
			$("#advanceAmount").val("");
			$('#editReservationBtn').addClass('d-none');
			$('#addReservationBtn').addClass('d-none');
			$('#goToCheckinBtn').addClass('d-none');
			$('#saveReservationBtn').removeClass('d-none');
			$('#cancelReservationBtn').removeClass('d-none');
			$('#reservNextBtn').addClass('d-none');
			$("#mobileNo").val("");
			$("#noOfNights").val("");
			$("#reservationRemarks").val("");
			$("#idProofNumber").val("");
			$("#idProofType").val("");
			$('#reservationForm').find('input, select,button,textarea').not('#guestType,select[name="guestType"]').prop('disabled', false);
			const tbody = document.getElementById('assignedRoomBody');
			tbody.innerHTML = '';

			$('#editReservationBtn').addClass('d-none');
			$('#saveReservationBtn').removeClass('d-none').css('display', 'inline-block');

			$("#firstName").focus();
		}, { once: true });

	} else {
		console.error("Operation tab button not found!");
	}
}

function setDataOnCheckin() {
	const bookingCustomerName = $("#bookingCustomerName").val();
	const guestType = $("#guestType").val();
	const reservationType = $("#reservationType").val();
	const custName = $("#custName").val();
	const mobileNo = $("#mobileNo").val();
	const idProofType = $("#idProofType").val();
	const idProofNumber = $("#idProofNumber").val();

	const totalAmount = parseFloat($("#totalAmount").val()) || 0;
	const advanceAmount = parseFloat($("#advanceAmount").val()) || 0;
	const chkPendingAmt = totalAmount - advanceAmount;

	let paymentStatus = "";
	if (chkPendingAmt === 0) {
		paymentStatus = "full paid";
	} else if (chkPendingAmt === totalAmount) {
		paymentStatus = "unpaid";
	} else {
		paymentStatus = "partial";
	}

	$("#checkinCustomerName").val(bookingCustomerName);
	$("#guestTypeCheckin").val(guestType).trigger('change');
	$("#chkrReservationType").val(reservationType).trigger('change');
	$("#chkContactName").val(custName);
	$("#chkContactPhone").val(mobileNo);
	$("#chkIdProofType").val(idProofType);
	$("#chkIdProofNumber").val(idProofNumber);
	$("#chktotalAmt").val(totalAmount.toFixed(2));
	$("#chkPaidAmt").val(advanceAmount.toFixed(2));
	$("#chkPendingAmt").val(chkPendingAmt.toFixed(2));
	$("#paymentStatus").val(paymentStatus).trigger('change');
}



function custSelect(id) {
	if (id === "Corporates") {
		$("#contactsNameDiv").removeClass("d-none");
		$("#firstnameDiv").addClass("d-none");
		$("#lastnameDiv").addClass("d-none");
		$("#paymentFrequencyDiv").removeClass("d-none");
		$("#reminderDiv").removeClass("d-none");
		$("#idproofDiv").addClass("d-none");
		$("#empIdDiv").addClass("d-none");
		$("#photoDiv").addClass("d-none");
		$("#corparateDiv").addClass("d-none");
	} else if (id === "Corporate Guest") {
		$("#contactsNameDiv").addClass("d-none");
		$("#corparateDiv").removeClass("d-none");
		$("#firstnameDiv").removeClass("d-none");
		$("#lastnameDiv").removeClass("d-none");
		$("#paymentFrequencyDiv").addClass("d-none");
		$("#reminderDiv").addClass("d-none");
		$("#idproofDiv").removeClass("d-none");
		$("#empIdDiv").removeClass("d-none");
		$("#photoDiv").removeClass("d-none");
	} else if (id === "Other Guest") {
		$("#contactsNameDiv").addClass("d-none");
		$("#firstnameDiv").removeClass("d-none");
		$("#lastnameDiv").removeClass("d-none");
		$("#paymentFrequencyDiv").addClass("d-none");
		$("#reminderDiv").addClass("d-none");
		$("#idproofDiv").removeClass("d-none");
		$("#empIdDiv").addClass("d-none");
		$("#photoDiv").removeClass("d-none");
		$("#corparateDiv").addClass("d-none");
	}
}


//Handle The Filter Click -->>>
function handleFilterClick(target) {

	if (target === "#corporates") {
		getCorporateDetails("Corporates");
	} else if (target === "#corporate-guests") {
		getCorporateDetails("Corporate Guest");
	} else if (target === "#other-guests") {
		getCorporateDetails("Other Guest");
	} else if (target === "#all-guests") {
		getCorporateDetails("All Guest,Other Guest,Corporate Guest,Corporates");
	}
}

//AG GridSearch Features And Functionality --->>>>
function SearchUserInput(event) {
	const value = event.target.value;
	gridOptionsDep.api.setQuickFilter(value);
}



function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();

	gridOptions.api.setQuickFilter(quickFilterValue);
}

function ClearBtn() {

	var quickFilterValue = $('#quickFilter').val('');
	gridOptions.api.setQuickFilter('');
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

// Column Definitions
var columnDefs = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 10,
		suppressSizeToFit: true,
	},
	{
		headerName: "Booking Id",
		field: "bookingId",
		width: 120
	},
	{
		headerName: "Customer Id",
		field: "customerId",
		width: 120
	},
	{
		headerName: "Customer",
		field: "custName",
		width: 120
	},
	{
		headerName: "Contact Name",
		field: "contactName",
		width: 120
	},
	{
		headerName: "Contact No",
		field: "mobileNo",
		width: 100
	},
	{
		headerName: "Check-In Date",
		field: "checkinDate",
		width: 120
	},
	{
		headerName: "Check-Out Date",
		field: "checkoutDate",
		width: 130
	},
	{
		headerName: "Booking Date",
		field: "bookingDate",
		width: 170
	},
	{
		headerName: "Guest Type",
		field: "guestType",
		width: 100
	},
	{
		headerName: "No of Rooms",
		field: "noofRooms",
		width: 120
	},
	{
		headerName: "No of Adults",
		field: "noofAdults",
		width: 120
	},
	{
		headerName: "No of Children",
		field: "noofChildren",
		width: 120
	},
	{
		headerName: "Status",
		field: "status",
		width: 100
	}
];



// Grid Options
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
	onSelectionChanged: rowSelectOperation
};

function rowSelectOperation() {
	var selectedRows = gridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		const bookingId = selectedRows[0].bookingId;
		$("#bookingId").val(bookingId);
		editReservationHotel(bookingId)
	}
}


//For Saving Corporate Details -->>>>
function saveCorporate() {


	if ($("#name").val() == "" || $("#name").val() == null) {
		toastr.error("Name is Required");
		return;
	}
	/*else if ($("#tax").val() == "" || $("#tax").val() == null) {
		toastr.error("Tax/ GSTIN is Required");
		return;
	}*/
	else if ($("#email").val() == "" || $("#email").val() == null) {
		toastr.error("Email is Required");
		return;
	}
	else if ($("#phone").val() == "" || $("#phone").val() == null) {
		toastr.error("Phone is Required");
		return;
	}
	else if ($("#address1").val() == "" || $("#address1").val() == null) {
		toastr.error("Address1 is Required");
		return;
	}
	else if ($("#address2").val() == "" || $("#address2").val() == null) {
		toastr.error("Address2 is Required");
		return;
	}
	else if ($("#country").val() == "" || $("#country").val() == null) {
		toastr.error("Country is Required");
		return;
	}
	else if ($("#state").val() == "" || $("#state").val() == null) {
		toastr.error("State is Required");
		return;
	}
	else if ($("#city").val() == "" || $("#city").val() == null) {
		toastr.error("City is Required");
		return;
	}
	else if ($("#postalCode").val() == "" || $("#postalCode").val() == null) {
		toastr.error("PostalCode is Required");
		return;
	}
	else if ($('input[name="pf"]:checked').val() === undefined || $('input[name="pf"]:checked').val() === null || $('input[name="pf"]:checked').val() === "") {
		toastr.error("Set Your Payment Frequency First");
		return;
	}
	else if ($('input[name="status"]:checked').val() === undefined || $('input[name="status"]:checked').val() === null || $('input[name="status"]:checked').val() === "") {
		toastr.error("Set Your StatusValue First");
		return;
	}
	else if ($('#nextReminder').val() === "" || $('#nextReminder').val() === null) {
		toastr.error("Please Set the next Reminder Date");
		return;
	}
	else {

		let corporateDetails = {}
		corporateDetails['custId'] = $("#customerId").val();
		corporateDetails['custType'] = $("#custType").val();
		corporateDetails['guestType'] = "Corporate";
		corporateDetails['name'] = $("#name").val();
		corporateDetails['tax'] = $("#tax").val();
		corporateDetails['email'] = $("#email").val();
		corporateDetails['phone'] = $("#phone").val();
		corporateDetails['address1'] = $("#address1").val();
		corporateDetails['address2'] = $("#address2").val();
		corporateDetails['country'] = $("#country").val();
		corporateDetails['state'] = $("#state").val();
		corporateDetails['city'] = $("#city").val();
		corporateDetails['postalCode'] = $("#postalCode").val();
		corporateDetails['pf'] = $('input[name="pf"]:checked').val();
		corporateDetails['status'] = $('input[name="status"]:checked').val();
		corporateDetails['nextReminder'] = $('#nextReminder').val();
		//corporateDetails['photo'] = $('#photo')[0].files[0];
		corporateDetails['photoName'] = null;
		corporateDetails['photo'] = null;

		corporateDetailsAPI(corporateDetails);

	}

}

// Saving Corporate Details API -->>>>
function corporateDetailsAPI(corporateDetails) {
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "save-corporate-details",
		contentType: "application/json",
		data: JSON.stringify(corporateDetails),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				toastr.success(response.message);
				cancelCorporate();
			} else {
				toastr.error(response.message);
				$('.loader').hide();
			}
			$(".br-dis").attr("disabled", true);
		},
		error: function(datas) {
			console.log(datas);
		}
	});

}

//For Adding a New Corporate Details --->>>>
function addCorporate() {
	$("#saveGuestData").removeClass('d-none');
	$("#addGuestData").addClass('d-none');
	$("#startBooking").addClass('d-none');
	$("#editGuestData").addClass('d-none');
	$("#cancelGuestData").removeClass('d-none');
	clearFields();
}

function editCorporateData() {
	$("#name, #custType, #tax, #email, #phone, #address1, #address2, #country, #state, #city, #postalCode, #nextReminder, #fname, #lname, #corporate, #idProof, #empId, #photo")
		.attr("disabled", false);
	$("#photoLabel").removeClass('disabled-label');
	$("#editGuestData").addClass('d-none');
	$("#cancelGuestData").removeClass('d-none');
	$("#startBooking").addClass('d-none');
	$("#saveGuestData").removeClass('d-none');
	$("#addGuestData").addClass('d-none');
}

function cancelCorporate() {
	$("#saveGuestData").addClass('d-none');
	$("#addGuestData").removeClass('d-none');
	$("#startBooking").removeClass('d-none');
	$("#editGuestData").removeClass('d-none');
	$("#cancelGuestData").addClass('d-none');
	let type = "Corporates,Other Guest,Corporate Guest";
	getCorporateDetails(type);
	$("#name, #custType, #tax, #email, #phone, #address1, #address2, #country, #state, #city, #postalCode, #nextReminder, #fname, #lname, #corporate, #idProof, #empId, #photo")
		.attr("disabled", true);
	$("#photoLabel").addClass('disabled-label');
}
//For Clear All Fields -->>>
function clearFields() {

	$("#name, #custType, #tax, #email, #phone, #address1, #address2, #country, #state, #city, #postalCode, #nextReminder, #fname, #lname, #corporate, #idProof, #empId, #photo")
		.attr("disabled", false);
	$("#photoLabel").removeClass('disabled-label');
	$("#name").val("")
	$("#tax").val("");
	$("#email").val("")
	$("#customerId").val("")
	$("#phone").val("")
	$("#address1").val("")
	$("#address2").val("")
	$("#country").val('').trigger('change');
	$("#state").val('').trigger('change');
	$("#city").val('').trigger('change');
	$("#postalCode").val("")
	$('input[name="pf"]').prop('checked', false);
	$('input[name="status"]').prop('checked', false);
	$('#nextReminder').val("");
	$("#fname").val("");
	$("#lname").val("");
	$("#corporate").val("");
	$("#idProof").val("");
	$("#empId").val("");
	$('#idImage').val("");
	$('#photoFileName').text('No files chosen');
	$('#imagePreviewContainer').empty();
	gridOptionsDep.api.deselectAll();


}

function saveGuestData() {
	const custType = $("#custType").val()

	if (custType == "Corporates") {
		saveCorporate();

	} else if (custType == "Corporate Guest") {
		saveCorporateGuest();
	} else if (custType == "Other Guest") {
		saveOtherGuest();
	} else {
		toastr.error("Please Select Guest Type.")
	}
}


//save the CorporateGuest Details --->>>
function saveCorporateGuest() {

	if ($("#fname").val() == "" || $("#fname").val() == null) {
		toastr.error("FirstName is Required");
		return;
	}
	else if ($("#lname").val() == "" || $("#lname").val() == null) {
		toastr.error("LastName is Required");
		return;
	}
	else if ($("#email").val() == "" || $("#email").val() == null) {
		toastr.error("Email is Required");
		return;
	}
	else if ($("#phone").val() == "" || $("#phone").val() == null) {
		toastr.error("Phone is Required");
		return;
	}
	else if ($("#address1").val() == "" || $("#address1").val() == null) {
		toastr.error("Address1 is Required");
		return;
	}
	else if ($("#address2").val() == "" || $("#address2").val() == null) {
		toastr.error("Address2 is Required");
		return;
	}
	else if ($("#country").val() == "" || $("#country").val() == null) {
		toastr.error("Country is Required");
		return;
	}
	else if ($("#state").val() == "" || $("#state").val() == null) {
		toastr.error("State is Required");
		return;
	}
	else if ($("#city").val() == "" || $("#city").val() == null) {
		toastr.error("City is Required");
		return;
	}
	else if ($("#postalCode").val() == "" || $("#postalCode").val() == null) {
		toastr.error("PostalCode is Required");
		return;
	}
	else if ($("#corporate").val() == "" || $("#corporate").val() == null) {
		toastr.error("Corporate is Required");
		return;
	}
	else if ($("#idProof").val() == "" || $("#idProof").val() == null) {
		toastr.error("IdProof is Required");
		return;
	}
	else if ($("#empId").val() == "" || $("#empId").val() == null) {
		toastr.error("EmployeeId is Required");
		return;
	}
	else if ($('input[name="status"]:checked').val() === undefined || $('input[name="status"]:checked').val() === null || $('input[name="status"]:checked').val() === "") {
		toastr.error("Set Your StatusValue First");
		return;
	}
	/*else if ($('#photo')[0].files.length === 0) {
		toastr.error("You Have To Upload Atleast One Photo");
		return;
	}*/

	else {
		let corporateGuestDetails = {}
		corporateGuestDetails['fName'] = $("#fname").val();
		corporateGuestDetails['lName'] = $("#lname").val();
		corporateGuestDetails['fullName'] = $("#fname").val() + " " + $("#lname").val();
		corporateGuestDetails['custType'] = $("#custType").val();
		corporateGuestDetails['guestType'] = "Corporate Guest";
		corporateGuestDetails['email'] = $("#email").val();
		corporateGuestDetails['phone'] = $("#phone").val();
		corporateGuestDetails['address1'] = $("#address1").val();
		corporateGuestDetails['address2'] = $("#address2").val();
		corporateGuestDetails['country'] = $("#country").val();
		corporateGuestDetails['state'] = $("#state").val();
		corporateGuestDetails['city'] = $("#city").val();
		corporateGuestDetails['postalCode'] = $("#postalCode").val();
		corporateGuestDetails['corporate'] = $("#corporate").val();
		corporateGuestDetails['idProof'] = $("#idProof").val();
		corporateGuestDetails['empId'] = $("#empId").val();
		corporateGuestDetails['status'] = $('input[name="status"]:checked').val();
		let imageFile = $('#idImage')[0].files[0];
		if (imageFile) {
			const fileExtension = imageFile.name.split('.').pop().toLowerCase();
			corporateGuestDetails['photoName'] = `${generateUUID()}.${fileExtension}`;
			const reader = new FileReader();
			reader.onload = function(e) {
				corporateGuestDetails['photo'] = e.target.result;
				corporateDetailsAPI(corporateGuestDetails);
			};
			reader.readAsDataURL(imageFile);
		}

	}
}

//Save The OtherGuest's Information ---->>>
function saveOtherGuest() {

	if ($("#fname").val() == "" || $("#fname").val() == null) {
		toastr.error("FirstName is Required");
		return;
	}
	else if ($("#lname").val() == "" || $("#lname").val() == null) {
		toastr.error("LastName is Required");
		return;
	}
	else if ($("#email").val() == "" || $("#email").val() == null) {
		toastr.error("Email is Required");
		return;
	}
	else if ($("#phone").val() == "" || $("#phone").val() == null) {
		toastr.error("Phone is Required");
		return;
	}
	else if ($("#address1").val() == "" || $("#address1").val() == null) {
		toastr.error("Address1 is Required");
		return;
	}
	else if ($("#address2").val() == "" || $("#address2").val() == null) {
		toastr.error("Address2 is Required");
		return;
	}
	else if ($("#country").val() == "" || $("#country").val() == null) {
		toastr.error("Country is Required");
		return;
	}
	else if ($("#state").val() == "" || $("#state").val() == null) {
		toastr.error("State is Required");
		return;
	}
	else if ($("#city").val() == "" || $("#city").val() == null) {
		toastr.error("City is Required");
		return;
	}
	else if ($("#postalCode").val() == "" || $("#postalCode").val() == null) {
		toastr.error("PostalCode is Required");
		return;
	}
	else if ($("#idProof").val() == "" || $("#idProof").val() == null) {
		toastr.error("IdProof is Required");
		return;
	}
	else if ($('input[name="status"]:checked').val() === undefined || $('input[name="status"]:checked').val() === null || $('input[name="status"]:checked').val() === "") {
		toastr.error("Set Your StatusValue First");
		return;
	}
	else if ($('#idImage')[0].files.length === 0) {
		toastr.error("You Have To Upload At Least One Photo");
		return;
	}
	else {
		let otherGuestDetails = {}

		otherGuestDetails['fName'] = $("#fname").val();
		otherGuestDetails['lName'] = $("#lname").val();
		otherGuestDetails['fullName'] = $("#fname").val() + " " + $("#lname").val();
		otherGuestDetails['guestType'] = "Other Guest";
		otherGuestDetails['custType'] = $("#custType").val();
		otherGuestDetails['email'] = $("#email").val();
		otherGuestDetails['phone'] = $("#phone").val();
		otherGuestDetails['address1'] = $("#address1").val();
		otherGuestDetails['address2'] = $("#address2").val();
		otherGuestDetails['country'] = $("#country").val();
		otherGuestDetails['state'] = $("#state").val();
		otherGuestDetails['city'] = $("#city").val();
		otherGuestDetails['postalCode'] = $("#postalCode").val();
		otherGuestDetails['idProof'] = $("#idProof").val();
		otherGuestDetails['status'] = $('input[name="status"]:checked').val();
		//otherGuestDetails['photo'] = $('#photo')[0].files[0];

		let imageFile = $('#idImage')[0].files[0];
		if (imageFile) {
			const fileExtension = imageFile.name.split('.').pop().toLowerCase();
			otherGuestDetails['photoName'] = `${generateUUID()}.${fileExtension}`;
			const reader = new FileReader();
			reader.onload = function(e) {
				otherGuestDetails['photo'] = e.target.result;
				corporateDetailsAPI(otherGuestDetails);
			};
			reader.readAsDataURL(imageFile);
		}


	}
}

function generateUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		const r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

/*//For Save AllGuest Information --->>>
function saveAllGuest() {

	if ($("#fname").val() == "" || $("#fname").val() == null) {
		toastr.error("FirstName is Required");
		return;
	}
	else if ($("#lname").val() == "" || $("#lname").val() == null) {
		toastr.error("LastName is Required");
		return;
	}
	else if ($("#email").val() == "" || $("#email").val() == null) {
		toastr.error("Email is Required");
		return;
	}
	else if ($("#phone").val() == "" || $("#phone").val() == null) {
		toastr.error("Phone is Required");
		return;
	}
	else if ($("#address1").val() == "" || $("#address1").val() == null) {
		toastr.error("Address1 is Required");
		return;
	}
	else if ($("#address2").val() == "" || $("#address2").val() == null) {
		toastr.error("Address2 is Required");
		return;
	}
	else if ($("#country").val() == "" || $("#country").val() == null) {
		toastr.error("Country is Required");
		return;
	}
	else if ($("#state").val() == "" || $("#state").val() == null) {
		toastr.error("State is Required");
		return;
	}
	else if ($("#city").val() == "" || $("#city").val() == null) {
		toastr.error("City is Required");
		return;
	}
	else if ($("#postalCode").val() == "" || $("#postalCode").val() == null) {
		toastr.error("PostalCode is Required");
		return;
	}
	else if ($("#idProof").val() == "" || $("#idProof").val() == null) {
		toastr.error("IdProof is Required");
		return;
	}
	else if ($('input[name="status"]:checked').val() === undefined || $('input[name="status"]:checked').val() === null || $('input[name="status"]:checked').val() === "") {
		toastr.error("Set Your StatusValue First");
		return;
	}
	else if ($('#photo')[0].files.length === 0) {
		toastr.error("You Have To Upload Atleast One Photo");
		return;
	}
	else {
		let allGuestDetails = {}


		allGuestDetails['fName'] = $("#fname").val();
		allGuestDetails['lName'] = $("#lname").val();
		allGuestDetails['fullName'] = $("#fname").val() + " " + $("#lname").val();
		allGuestDetails['email'] = $("#email").val();
		allGuestDetails['custType'] = $("#custType").val()
		allGuestDetails['guestType'] = "All Guest";
		allGuestDetails['phone'] = $("#phone").val();
		allGuestDetails['address1'] = $("#address1").val();
		allGuestDetails['address2'] = $("#address2").val();
		allGuestDetails['country'] = $("#country").val();
		allGuestDetails['state'] = $("#state").val();
		allGuestDetails['city'] = $("#city").val();
		allGuestDetails['postalCode'] = $("#postalCode").val();
		allGuestDetails['idProof'] = $("#idProof").val();
		allGuestDetails['status'] = $('input[name="status"]:checked').val();
		allGuestDetails['photo'] = $('#photo')[0].files[0];


		console.log("Data For allGuestDetails ->>", allGuestDetails);
		corporateDetailsAPI(allGuestDetails);
	}
}
*/
//OnChange StateList
function getStateDetails(id) {

	var cname = $('#country').val();
	if (cname) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "corporate-stateList?id=" + cname,
			success: function(response) {
				if (response.message == "success") {
					$("#state").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#state").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#state").append(option);
					}
					if (id == "0") {

					} else {
						$("#state").val(id);
					}
				}
			},
			error: function(e) { }
		});
	} else {
		$("#state").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#state").append(option);
		$("#state").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
	}
}



//Get City Details
function getCityDetails(city) {
	let dist = $('#state').val();

	if (dist) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "corporate-city-list?id=" + dist,
			success: function(response) {
				if (response.message == "success") {
					$("#city").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#city").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#city").append(option);
					}
					if (city) {
						$("#city").val(city);
					}
				}
			},
			error: function(e) { }
		});
	} else {
		$("#city").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#city").append(option);
		$("#city").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
	}
}


//Corporate View --->>>>
function getCorporateDetails(type) {

	agGrid.simpleHttpRequest({
		url: "corporatedetails-view?type=" + type
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.corporateDetails;
		gridOptionsDep.api.setRowData(allData.reverse());

		var firstRowNode = gridOptionsDep.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}

		var select = document.getElementById('bookingCustomerName');
		var checkInSelect = document.getElementById('checkinCustomerName');

		select.innerHTML = '<option value="" selected disabled>Select the Customer You Want to Book For</option>';

		// Add new options from the data
		allData.forEach(function(customer) {
			var option = document.createElement('option');
			option.value = customer.regId;
			option.text = customer.custName + " (" + customer.regId + ")";
			option.dataset.custType = customer.custType;
			select.appendChild(option);
		});

		allData.forEach(function(customer) {
			var option = document.createElement('option');
			option.value = customer.regId;
			option.text = customer.custName + " (" + customer.regId + ")";
			option.dataset.custType = customer.custType;
			checkInSelect.appendChild(option);
		});


	});
}



function editCorporate(regId) {
	$.ajax({
		type: "GET",
		url: "corporateDetails-edit?id=" + regId,
		success: function(response) {
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.corporateDetails;
				$("#custType").val(allData[0].custType);
				custSelect(allData[0].custType);
				$("#customerId").val(regId);
				$("#name").val(allData[0].name);
				$("#fname").val(allData[0].fName);
				$("#lname").val(allData[0].lName);
				$("#tax").val(allData[0].tax);
				$("#email").val(allData[0].email);
				$("#phone").val(allData[0].phone);
				$("#address1").val(allData[0].address1);
				$("#address2").val(allData[0].address2);
				$("#country").val(allData[0].country).trigger('change');
				$("#state").val(allData[0].state).trigger('change');
				$("#city").val(allData[0].city).trigger('change');
				$("#postalCode").val(allData[0].postalcode);
				$("#idProof").val(allData[0].idProof);
				$("#empId").val(allData[0].empId);
				$("#nextReminder").val(allData[0].paymentReminderDate);
				$("#corporate").val(allData[0].corporate);
				$('input[name="pf"][value="' + allData[0].pf + '"]').prop("checked", true);
				$('input[name="status"][value="' + allData[0].status + '"]').prop("checked", true);

				// Handle image preview
				const previewContainer = $('#imagePreviewContainer');
				const fileNameDisplay = $('#photoFileName');
				previewContainer.empty(); // Clear previous previews

				if (allData[0].idName) {
					// Update file name display
					fileNameDisplay.text('1 file loaded');

					// Create image preview element
					const imgWrapper = $('<div>').css({
						'position': 'relative',
						'width': '100px',
						'height': '100px',
						'border': '1px solid #969696'
					});

					const img = $('<img>').attr('src', allData[0].idName).css({
						'width': '100%',
						'height': '100%',
						'object-fit': 'cover',
						'cursor': 'pointer'
					});

					// Open image in new tab when clicked
					img.on('click', function() {
						const imageWindow = window.open();
						$(imageWindow.document.body).html(
							$('<img>').attr('src', allData[0].idName).css({
								'max-width': '100%',
								'max-height': '100%'
							})
						);
					});

					imgWrapper.append(img);
					previewContainer.append(imgWrapper);
				} else {
					fileNameDisplay.text('No files chosen');
				}

				setTimeout(function() {
					getStateDetails(allData[0].state);
				}, 500);
				setTimeout(function() {
					getCityDetails(allData[0].city);
				}, 800);
			}
		},
		error: function(data) {
			console.log(data);
			$("#custId").val('');
			$("#custType").val('');
			$("#name").val('');
			$("#tax").val('');
			$("#email").val('');
			$("#phone").val('');
			$("#address1").val('');
			$("#address2").val('');
			$("#country").val('').trigger('change');
			$("#state").val('').trigger('change');
			$("#city").val('').trigger('change');
			$("#postalCode").val('');
			$("#pf").val('');
			$("#status").val('');
			$("#idProof").val("");
			$("#nextReminder").val('');
			$('#imagePreviewContainer').empty();
			$('#photoFileName').text('No files chosen');
		}
	});
}
function nextBtn() {

	const reservationTab = document.querySelector('#operation-reservationTab.active');
	const checkinTab = document.querySelector('#operation-checkInTab.active');

	if (reservationTab) {

		document.querySelector('button[data-bs-target="#operation-checkInTab"]').click();
	} else if (checkinTab) {

		document.querySelector('button[data-bs-target="#operation-checkOutTab"]').click();
	} else {

		console.log("Already in the Checkout tab or no more tabs to navigate.");
	}
}

function prevBtn() {
	// Get the current active tab
	const checkinTab = document.querySelector('#operation-checkInTab.active');
	const checkoutTab = document.querySelector('#operation-checkOutTab.active');

	if (checkoutTab) {
		// If Check-out tab is active, switch to Check-in tab
		document.querySelector('button[data-bs-target="#operation-checkInTab"]').click();
	} else if (checkinTab) {
		// If Check-in tab is active, switch to Reservation tab
		document.querySelector('button[data-bs-target="#operation-reservationTab"]').click();
	} else {
		// Already in Reservation tab or handle as needed
		console.log("Already in the Reservation tab or no previous tabs to navigate.");
	}
}


function addNewReservation() {

	gridOptions.api.deselectAll();
	$("#bookingId").val("");
	$("#customerId").val("");
	$("#checkinDate").val("");
	$("#checkoutDate").val("");
	$("#bookingCustomerName").val("").trigger('change');
	$("#guestType").val("").trigger('change');
	$("#roomType").val("").trigger('change');
	$("#custName").val("");
	$("#mobileNo").val("");
	$("#noofRooms").val("");
	$("#noofAdults").val("");
	$("#noofChildren").val("");
	$("#totalAmount").val("");
	$("#noOfNights").val("");
	$("#idProofType").val("");
	$("#idProofNumber").val("");
	$("#reservationRemarks").val("");
	$("#advanceAmount").val("");
	$("#source").val("").trigger('change');
	$("#status").val("").trigger('change');
	const tbody = document.getElementById('assignedRoomBody');
	tbody.innerHTML = '';

	$('#reservationForm').find('input, select, button,textarea').not('#guestType,select[name="guestType"]').prop('disabled', false);
	$('#editReservationBtn').addClass('d-none');
	$('#addReservationBtn').addClass('d-none');
	$('#goToCheckinBtn').addClass('d-none');
	$('#saveReservationBtn').removeClass('d-none');
	$('#cancelReservationBtn').removeClass('d-none');
	$('#reservNextBtn').addClass('d-none');


}

function custSelection(value) {
	var selectedOption = $('#bookingCustomerName option:selected');
	$('#customerId').val(value);
	var custType = selectedOption.data('cust-type');
	$("#guestType").val(custType).trigger('change');
}

//For Reservation Tab Start Here -->>>>>
function saveReservationHotel() {
	const tbody = document.getElementById('assignedRoomBody');
	const rows = tbody.querySelectorAll('tr');
	const roomAssignments = [];

	rows.forEach((row, index) => {
		const roomType = row.querySelector(`#assignedRoomType${index}`).value;
		const floor = row.querySelector(`#assignedFloor${index}`).value;
		const rate = parseFloat(row.querySelector(`#assignedRate${index}`).value) || 0;
		const checkinDate = row.querySelector(`#checkinDate${index}`).value;
		const checkoutDate = row.querySelector(`#checkoutDate${index}`).value;

		const timeDiff = new Date(checkoutDate) - new Date(checkinDate);
		const nights = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
		const displayNights = Math.max(1, nights);
		const rowTotal = rate * displayNights;

		const rowData = {
			roomType: roomType,
			floor: floor,
			rate: rate,
			checkinDate: checkinDate,
			checkoutDate: checkoutDate,
			days: displayNights,
			totalAmount: rowTotal.toFixed(2)
		};

		roomAssignments.push(rowData);
	});

	let reservationdata = {};
	reservationdata.bookingId = $("#bookingId").val();
	reservationdata.customerId = $("#customerId").val();
	reservationdata.reservationType = $("#reservationType").val();
	reservationdata.noOfNights = $("#noOfNights").val();
	reservationdata.idProofType = $("#idProofType").val();
	reservationdata.idProofNumber = $("#idProofNumber").val();
	reservationdata.reservationRemarks = $("#reservationRemarks").val();
	reservationdata.guestType = $("#guestType").val();
	reservationdata.mobileNo = $("#mobileNo").val();
	reservationdata.custName = $("#custName").val();
	reservationdata.noofRooms = $("#noofRooms").val();
	reservationdata.noofAdults = $("#noofAdults").val();
	reservationdata.noofChildren = $("#noofChildren").val();
	reservationdata.totalAmount = $("#totalAmount").val();
	reservationdata.advanceAmount = $("#advanceAmount").val();
	reservationdata.source = $("#source").val();
	reservationdata.status = $("#status").val();
	reservationdata.roomTypeData = roomAssignments;

	// Validation
	if (reservationdata.customerId == "" || reservationdata.customerId == null) {
		toastr.error("Customer required");
		return;
	}
	if (reservationdata.guestType == "" || reservationdata.guestType == null) {
		toastr.error("Guest Type is required");
		return;
	}
	if (reservationdata.custName == "" || reservationdata.custName == null) {
		toastr.error("Contact Name is required");
		return;
	}
	if (reservationdata.mobileNo == "" || reservationdata.mobileNo == null) {
		toastr.error("Contact Mobile Number is required");
		return;
	}
	if (reservationdata.noofRooms == "" || reservationdata.noofRooms == null) {
		toastr.error("Number of Rooms is required");
		return;
	}
	if (reservationdata.noofAdults == "" || reservationdata.noofAdults == null) {
		toastr.error("Number of Adults is required");
		return;
	}
	if (reservationdata.noofChildren == "" || reservationdata.noofChildren == null) {
		toastr.error("Number of Children is required");
		return;
	}
	if (reservationdata.noOfNights == "" || reservationdata.noOfNights == null) {
		toastr.error("Number of Nights required");
		return;
	}
	if (reservationdata.source == "" || reservationdata.source == null) {
		toastr.error("Source is required");
		return;
	}
	if (reservationdata.status == "" || reservationdata.status == null) {
		toastr.error("Status is required");
		return;
	}
	if (reservationdata.idProofType == "" || reservationdata.idProofType == null) {
		toastr.error("Id proof is required");
		return;
	}
	if (reservationdata.idProofNumber == "" || reservationdata.idProofNumber == null) {
		toastr.error("Id proof number required");
		return;
	}
	if (reservationdata.totalAmount == "" || reservationdata.totalAmount == null) {
		toastr.error("Total Amount is required");
		return;
	}
	if (reservationdata.advanceAmount == "" || reservationdata.advanceAmount == null) {
		toastr.error("Advance Amount is required");
		return;
	}
	if (roomAssignments.length === 0) {
		toastr.error("At least one room is required");
		return;
	}

	console.log("reservationdata::::", reservationdata);
	saveReservation(reservationdata);
}

function saveReservation(reservationdata) {
	$.ajax({
		type: "POST",
		url: "savehotel-reservation-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(reservationdata),
		success: function(response) {
			if (response.code == "Success") {
				toastr.success(response.message);

				cancelReservationHotel();
			}
		},
		error: function(response) {
			console.log(response);
		}
	});
}

//View
function getReservationHotelDetails(statusFilter = "ALL") {
	agGrid.simpleHttpRequest({
		url: "get-hotel-reservation-view"
	}).then(function(response) {
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.hotelreservationDetails;

		let filteredData = allData;
		if (statusFilter !== "ALL") {
			filteredData = allData.filter(item =>
				item.status && item.status.toLowerCase() === statusFilter.toLowerCase()
			);
		}

		gridOptions.api.setRowData(filteredData.reverse());

		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	});
}

//Edit

function editReservationHotel(bookingId) {
	$.ajax({
		type: "GET",
		url: "hotel-reservation-edit?bookingId=" + bookingId,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.reservationDetails; // Single object with reservation details

				// Populate common reservation fields
				$("#bookingId").val(allData.bookingId);
				$("#bookingCustomerName").val(allData.custId).trigger('change');
				$("#guestType").val(allData.guestType).trigger('change');
				$("#mobileNo").val(allData.mobileNo);
				$("#custName").val(allData.custName);
				$("#reservationType").val(allData.reservationType);
				$("#noOfNights").val(allData.noofNight);
				$("#idProofType").val(allData.idType);
				$("#idProofNumber").val(allData.idNo);
				$("#reservationRemarks").val(allData.remarks);
				$("#noofRooms").val(allData.noofRooms);
				$("#noofAdults").val(allData.noofAdults);
				$("#noofChildren").val(allData.noofChildren);
				$("#totalAmount").val(allData.totalAmount);
				$("#advanceAmount").val(allData.advanceAmount);
				$("#source").val(allData.source).trigger('change');
				$("#status").val(allData.status).trigger('change');
				setDataOnCheckin();

				// Clear existing rows in the room assignment table
				const tbody = document.getElementById('assignedRoomBody');
				const tbodyCheckIn = document.getElementById('chekinRoomBody');
				tbody.innerHTML = '';
				tbodyCheckIn.innerHTML = '';

				// Fetch room types to populate the room type dropdown
				fetch('get-hotel-room-type-list')
					.then(response => response.json())
					.then(data => {
						const roomTypes = data.body;

						// Iterate through rooms array and populate table rows
						allData.roomTypeData.forEach((room, index) => {
							// Create options for room type select
							let roomTypeOptions = '<option value="">Select</option>';
							roomTypes.forEach(type => {
								roomTypeOptions += `<option value="${type.key}" ${type.key === room.roomType ? 'selected' : ''}>${type.name}</option>`;
							});

							// Create a new row
							const row = document.createElement('tr');
							row.innerHTML = `
                                <td>
                                    <select id="assignedRoomType${index}" class="form-select">
                                        ${roomTypeOptions}
                                    </select>
                                </td>
                                <td><input type="text" id="assignedFloor${index}" class="form-control" value="${room.roomFloor}" /></td>
                                <td><input type="number" id="assignedRate${index}" class="form-control room-rate" value="${room.rate}" placeholder="0.00 ₹" /></td>
                                <td>
                                    <input type="date" id="checkinDate${index}" class="form-control checkin-date" value="${room.checkInDate}" min="${room.checkInDate}">
                                </td>
                                <td>
                                    <input type="date" id="checkoutDate${index}" class="form-control checkout-date" value="${room.checkOutDate}" min="${room.checkInDate}">
                                </td>
								<td><span id="days${index}">${room.days}</span></td>
								<td><span id="rowTotal${index}">${room.totalAmount} ₹</span></td>
                                <td>
                                    <button type="button" class="btn btn-danger" onclick="removeAssignedRoomRow(this)" disabled="disabled">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </td>
                            `;

							tbody.appendChild(row);

							const rowCheckIn = document.createElement('tr');
							rowCheckIn.innerHTML = `
                                <td>
                                    <select id="checkInRoomType${index}" class="form-select">
                                        ${roomTypeOptions}
                                    </select>
                                </td>
                                <td><input type="text" id="checkInFloor${index}" class="form-control" value="${room.roomFloor}" /></td>
                                <td><input type="text" id="checkInRooms${index}" class="form-control room-rate" /></td>
                                <td>
                                    <input type="date" id="checkinDate${index}" class="form-control checkin-date"  min="${room.checkInDate}">
                                </td>
								<td>
	                                <input type="time" id="checkinTime${index}" class="form-control">
	                            </td>
                            `;


							tbodyCheckIn.appendChild(rowCheckIn);


						});
					})
			}
		}
	});
}

function toggleList() {
	$("#calendar-container").hide();
	$("#myGrid").show();
	$("#showCalendar").removeClass('d-none');
	$("#showMeetingList").addClass('d-none');
}

function toggleCal() {
	$("#showMeetingList").removeClass('d-none');
	$("#showCalendar").addClass('d-none');
	$("#calendar-container").show();
	$("#myGrid").hide();
	setTimeout(() => calendar.updateSize(), 10);
}


//Check-in Tabs Handle Here ---->>
function saveCheckin() {
	let checkinData = {};

	const rows = document.querySelectorAll('#chekinRoomBody tr');
	const roomDataList = [];

	rows.forEach((row, index) => {
		const roomType = row.querySelector(`#checkInRoomType${index}`)?.value || '';
		const floor = row.querySelector(`#checkInFloor${index}`)?.value || '';
		const rooms = row.querySelector(`#checkInRooms${index}`)?.value || '';
		const checkinDate = row.querySelector(`#checkinDate${index}`)?.value || '';
		const checkinTime = row.querySelector(`#checkinTime${index}`)?.value || '';

		roomDataList.push({
			roomType,
			floor,
			rooms,
			checkinDate,
			checkinTime
		});
	});
	
	checkinData.checkinId = $("#checkinId").val();
	checkinData.bookingId = $("#bookingId").val();
	checkinData.customerId = $("#customerId").val();
	checkinData.guestType = $("#guestTypeCheckin").val();
	checkinData.chkReservationType = $("#chkrReservationType").val();
	checkinData.chkContactName = $("#chkContactName").val();
	checkinData.chkContactPhone = $("#chkContactPhone").val();
	checkinData.chkIdProofType = $("#chkIdProofType").val();
	checkinData.chkIdProofNumber = $("#chkIdProofNumber").val();
	checkinData.chktotalAmt = $("#chktotalAmt").val();
	checkinData.chkPaidAmt = $("#chkPaidAmt").val();
	checkinData.chkPendingAmt = $("#chkPendingAmt").val();
	checkinData.paymentStatus = $("#paymentStatus").val();
	checkinData.paymentAmount = $("#chkpayAmt").val();
	checkinData.checkedInBy = $("#checkedInBy").val();
	checkinData.checkInRemarks = $("#checkInRemarks").val();
	checkinData.status = $("#statusCheckin").val();
	checkinData.roomDataList = roomDataList;
	

	console.log('checkin data===========>', checkinData);

	if (checkinData.customerId == "" || checkinData.customerId == null) {
		toastr.error("Customer Required");
		return;
	}

	if (checkinData.bookingId == "" || checkinData.bookingId == null) {
		toastr.error("Select reservation Of the customer");
		return;
	}

	if (checkinData.guestType == "" || checkinData.guestType == null) {
		toastr.error("Guest Type Required");
		return;
	}


	if (checkinData.chkReservationType == "" || checkinData.chkReservationType == null) {
		toastr.error("Reservation Type Required");
		return;
	}

	if (checkinData.chkContactName == "" || checkinData.chkContactName == null) {
		toastr.error("Contact Name Required");
		return;
	}

	if (checkinData.chkContactPhone == "" || checkinData.chkContactPhone == null) {
		toastr.error("Contact Phone Required");
		return;
	}

	if (checkinData.chkIdProofType == "" || checkinData.chkIdProofType == null) {
		toastr.error("Id Type Required");
		return;
	}

	if (checkinData.chkIdProofNumber == "" || checkinData.chkIdProofNumber == null) {
		toastr.error("Id Number Required");
		return;
	}

	if (checkinData.paymentStatus == "" || checkinData.paymentStatus == null) {
		toastr.error("Payment Status Required");
		return;
	}


	if (checkinData.checkedInBy == "" || checkinData.checkedInBy == null) {
		toastr.error("Checked In By Required");
		return;
	}

	saveCheckinData(checkinData);
}

$(document).ready(function () {
    $('#chkpayAmt').on('input', function () {
        var totalAmt = parseFloat($('#chktotalAmt').val()) || 0;
        var paidAmt = parseFloat($('#chkPaidAmt').val()) || 0;
        var payAmt = parseFloat($(this).val()) || 0;

        var maxPayable = totalAmt - paidAmt;

        if (payAmt > maxPayable) {
            toastr.error("Pay amount cannot be more than remaining amount (" + maxPayable.toFixed(2) + ")");
            $(this).val('');
            $('#chkPendingAmt').val((totalAmt - paidAmt).toFixed(2));
            return;
        }

        var pendingAmt = totalAmt - (paidAmt + payAmt);
        $('#chkPendingAmt').val(pendingAmt.toFixed(2));
    });
});


function saveCheckinData(checkinData) {
	$.ajax({
		type: "POST",
		url: "save-hotel-checkin-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(checkinData),
		success: function(response) {

			if (response.code === "Success") {
				toastr.success(response.message);
			}
		},
		error: function(response) {
			console.log(response);
		}
	});
}
//set data in Check-in Tab
/*function populateCheckinTabFromReservation(selectedRow) {


	$('#checkinId').val('');
	$('#bookingId').val(selectedRow.bookingId);
	$('#guestTypeCheckin').val(selectedRow.guestType).trigger('change');
	$('#roomTypeCheckin').val(selectedRow.roomType).trigger('change');
	$('#noofChild').val(selectedRow.noofChildren);
	$('#totalAmountCheckin').val(selectedRow.totalAmount);
	$('#advanceAmountCheckin').val(selectedRow.advanceAmount);

	// Clear fields
	$('#checkinDateTime').val('');
	$('#stayLength').val('');
	$('#roomNumber').val('').trigger('change');
	$('#rate').val('');
	$('#roomtariff').val('');
	$('#taxAmount').val('');
	$('#paymentStatus').val('').trigger('change');
	$('#paymentAmount').val('');
	$('#statusCheckin').val('').trigger('change');

	const statusSelect = document.getElementById("statusCheckin");
	statusSelect.innerHTML = `
	<option value="checked-In" selected>Checked-In</option>
  `;
	statusSelect.disabled = false;


}*/
function enableReservationForm() {
	$('#reservationForm').find('input, select,button,textarea').not('#guestType, select[name="guestType"]').prop('disabled', false);
	$('#editReservationBtn').addClass('d-none');
	$('#addReservationBtn').addClass('d-none');
	$('#goToCheckinBtn').addClass('d-none');
	$('#saveReservationBtn').removeClass('d-none');
	$('#cancelReservationBtn').removeClass('d-none');
	$('#reservNextBtn').addClass('d-none');
}

function cancelReservationHotel() {
	$('#reservationForm').find('input, select,button,textarea').prop('disabled', true);
	$('#editReservationBtn').removeClass('d-none');
	$('#saveReservationBtn').addClass('d-none');
	$('#cancelReservationBtn').addClass('d-none');
	$('#addReservationBtn').removeClass('d-none');
	$('#goToCheckinBtn').removeClass('d-none');
	$('#reservNextBtn').removeClass('d-none');
	getReservationHotelDetails();
}


//calculate payment Amount
function calculatePaymentAmount() {
	const rate = parseFloat($("#rate").val()) || 0;
	const tax = parseFloat($("#taxAmount").val()) || 0;
	const advance = parseFloat($("#advanceAmountCheckin").val()) || 0;

	let total = parseFloat($("#totalAmountCheckin").val()) || 0;

	if (rate > 0 && total === 0) {
		total = rate;
		$("#totalAmountCheckin").val(total.toFixed(2));
	}

	const payment = (total + tax) - advance;
	$("#paymentAmount").val(payment.toFixed(2));
}

function editCheckinHotel() {
	let id = "CHK0004";
	$.ajax({
		type: "GET",
		url: "hotel-checkin-edit?checkinId=" + id,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body);
				var data = jsonData.checkinDetails;

				console.log("Check-in Edit Data --->", data);

				$("#checkinId").val(data[0].checkinId || "");
				$("#bookingId").val(data[0].bookingId || "");
				$("#checkinDateTime").val(data[0].checkinDateTime || "");
				$("#guestTypeCheckin").val(data[0].guestType || "").trigger('change');
				$("#roomTypeCheckin").val(data[0].roomType || "").trigger('change');
				$("#roomNumber").val(data[0].roomNumber || "");
				$("#noofChild").val(data[0].noofChild || "");
				$("#rate").val(data[0].rate || "");
				$("#roomtariff").val(data[0].roomtariff || "");
				$("#taxAmount").val(data[0].taxAmount || "");
				$("#totalAmountCheckin").val(data[0].totalAmount || "").trigger('change');
				$("#advanceAmountCheckin").val(data[0].advanceAmount || "").trigger('change');
				$("#stayLength").val(data[0].stayLength || "");
				$("#paymentStatus").val(data[0].paymentStatus || "");
				$("#paymentAmount").val(data[0].paymentAmount || "");
				$("#statusCheckin").val(data[0].status || "").trigger('change');
			}
		},
	});
}

function populateCheckoutTabFromReservation(selectedRow) {

	var guestName = (selectedRow.firstName || '') + ' ' + (selectedRow.lastName || '');
	$('#checkoutGuestName').val(guestName.trim());


	$('#checkoutRoomNumber').val(selectedRow.roomNumber).trigger('change');


	$('#checkoutRoomType').val(selectedRow.roomType).trigger('change');


	$('#checkoutCheckinDate').val(selectedRow.checkinDate || '');


	$('#checkoutCheckoutDate').val(selectedRow.checkoutDate || '');

}

document.addEventListener(
	'DOMContentLoaded',
	function() {
		// Get CSS variables (if you want to use them)
		const rootStyles = getComputedStyle(document.documentElement);
		const primaryColor = rootStyles.getPropertyValue(
			'--primary-color').trim();
		const secondaryColor = rootStyles.getPropertyValue(
			'--secondary-color').trim();
		const successColor = rootStyles.getPropertyValue(
			'--success-color').trim();
		const warningColor = rootStyles.getPropertyValue(
			'--warning-color').trim();
		const dangerColor = rootStyles.getPropertyValue(
			'--danger-color').trim();
		const infoColor = rootStyles.getPropertyValue(
			'--info-color').trim();
		const accentColor = rootStyles.getPropertyValue(
			'--accent-color').trim();

		// Today's Room Status Chart
		Highcharts
			.chart(
				'roomStatusChart',
				{
					chart: {
						type: 'column'
					},
					title: {
						text: null
					},
					xAxis: {
						categories: ['Clean',
							'Dirty', 'Block',
							'Occupied'],
						crosshair: true
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Number of Rooms'
						}
					},
					tooltip: {
						headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
						pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
							+ '<td style="padding:0"><b>{point.y} rooms</b></td></tr>',
						footerFormat: '</table>',
						shared: true,
						useHTML: true
					},
					plotOptions: {
						column: {
							pointPadding: 0.2,
							borderWidth: 0
						}
					},
					colors: [successColor,
						warningColor,
						dangerColor,
						secondaryColor],
					series: [{
						name: 'Rooms',
						data: [11, 5, 2, 10],
						colorByPoint: true
					}]
				});

		// Today's Maintenance Chart
		Highcharts
			.chart(
				'maintenanceChart',
				{
					chart: {
						type: 'pie'
					},
					title: {
						text: 'Complaint Status'
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
					},
					accessibility: {
						point: {
							valueSuffix: '%'
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<b>{point.name}</b>: {point.percentage:.1f} %'
							}
						}
					},
					colors: [successColor,
						warningColor, infoColor],
					series: [{
						name: 'Complaints',
						colorByPoint: true,
						data: [{
							name: 'Closed',
							y: 33
						}, {
							name: 'Ongoing',
							y: 22
						}, {
							name: 'Pending',
							y: 45
						}]
					}]
				});

		// Weekly Room Status Chart
		Highcharts
			.chart(
				'weeklyRoomStatusChart',
				{
					chart: {
						type: 'column'
					},
					title: {
						text: 'Weekly Room Status Overview'
					},
					xAxis: {
						categories: ['Mon',
							'Tue', 'Wed',
							'Thu', 'Fri',
							'Sat', 'Sun'],
						crosshair: true
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Number of Rooms'
						}
					},
					tooltip: {
						headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
						pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
							+ '<td style="padding:0"><b>{point.y} rooms</b></td></tr>',
						footerFormat: '</table>',
						shared: true,
						useHTML: true
					},
					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},
					colors: [successColor,
						warningColor,
						dangerColor,
						secondaryColor],
					series: [
						{
							name: 'Clean',
							data: [12, 10,
								13, 11, 9,
								8, 11]
						},
						{
							name: 'Dirty',
							data: [4, 6, 3,
								5, 7, 8, 5]
						},
						{
							name: 'Block',
							data: [1, 2, 1,
								2, 1, 2, 2]
						},
						{
							name: 'Occupied',
							data: [8, 7, 8,
								7, 8, 7, 7]
						}]
				});

		// Weekly Maintenance Chart
		Highcharts.chart('weeklyMaintenanceChart', {
			chart: {
				type: 'line'
			},
			title: {
				text: 'Weekly Complaint Trends'
			},
			xAxis: {
				categories: ['Mon', 'Tue', 'Wed', 'Thu',
					'Fri', 'Sat', 'Sun']
			},
			yAxis: {
				title: {
					text: 'Number of Complaints'
				}
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: true
					},
					enableMouseTracking: true
				}
			},
			colors: [successColor, warningColor,
				infoColor],
			series: [{
				name: 'Closed',
				data: [3, 2, 4, 3, 5, 4, 3]
			}, {
				name: 'Ongoing',
				data: [2, 3, 1, 2, 1, 2, 2]
			}, {
				name: 'Pending',
				data: [4, 5, 4, 5, 4, 4, 4]
			}]
		});

		// Monthly Room Status Chart
		Highcharts
			.chart(
				'monthlyRoomStatusChart',
				{
					chart: {
						type: 'column'
					},
					title: {
						text: 'Monthly Room Status Overview'
					},
					xAxis: {
						categories: ['Week 1',
							'Week 2', 'Week 3',
							'Week 4'],
						crosshair: true
					},
					yAxis: {
						min: 0,
						title: {
							text: 'Average Rooms'
						}
					},
					tooltip: {
						headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
						pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
							+ '<td style="padding:0"><b>{point.y} rooms</b></td></tr>',
						footerFormat: '</table>',
						shared: true,
						useHTML: true
					},
					plotOptions: {
						column: {
							stacking: 'normal'
						}
					},
					colors: [successColor,
						warningColor,
						dangerColor,
						secondaryColor],
					series: [{
						name: 'Clean',
						data: [11, 10, 12, 11]
					}, {
						name: 'Dirty',
						data: [5, 6, 4, 5]
					}, {
						name: 'Block',
						data: [2, 1, 2, 2]
					}, {
						name: 'Occupied',
						data: [7, 8, 7, 7]
					}]
				});

		// Monthly Maintenance Chart
		Highcharts
			.chart(
				'monthlyMaintenanceChart',
				{
					chart: {
						type: 'pie'
					},
					title: {
						text: 'Monthly Complaint Summary'
					},
					tooltip: {
						pointFormat: '{series.name}: <b>{point.y} ({point.percentage:.1f}%)</b>'
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<b>{point.name}</b>: {point.y} ({point.percentage:.1f}%)'
							},
							showInLegend: true
						}
					},
					colors: [successColor,
						warningColor, infoColor],
					series: [{
						name: 'Complaints',
						colorByPoint: true,
						data: [{
							name: 'Closed',
							y: 65
						}, {
							name: 'Ongoing',
							y: 25
						}, {
							name: 'Pending',
							y: 10
						}]
					}]
				});
	});


function addAssignedRoomRow() {
	const tbody = document.getElementById('assignedRoomBody');
	const rows = tbody.querySelectorAll('tr');
	const lastRow = rows[rows.length - 1];

	// Prevent adding a new row if the last row is incomplete
	if (lastRow) {
		const roomType = lastRow.querySelector(`#assignedRoomType${rows.length - 1}`).value;
		const floor = lastRow.querySelector(`#assignedFloor${rows.length - 1}`).value;
		const rate = lastRow.querySelector(`#assignedRate${rows.length - 1}`).value;
		const checkin = lastRow.querySelector(`#checkinDate${rows.length - 1}`).value;
		const checkout = lastRow.querySelector(`#checkoutDate${rows.length - 1}`).value;

		if (!roomType || !floor || !rate || !checkin || !checkout) {
			toastr.error("Please fill all fields in the current row before adding a new one.");
			return;
		}
	}

	// Make AJAX call to get room types
	fetch('get-hotel-room-type-list')
		.then(response => response.json())
		.then(data => {
			const roomTypes = data.body;
			const index = rows.length;
			const row = document.createElement('tr');

			// Create options for room type select
			let roomTypeOptions = '<option value="">Select</option>';
			roomTypes.forEach(type => {
				roomTypeOptions += `<option value="${type.key}">${type.name}</option>`;
			});

			// Get current date and next day for default dates
			const today = new Date();
			const tomorrow = new Date();
			tomorrow.setDate(today.getDate() + 1);

			// Format dates as YYYY-MM-DD for input[type="date"]
			const formatDate = (date) => date.toISOString().split('T')[0];

			row.innerHTML = `
	                <td>
	                    <select id="assignedRoomType${index}" class="form-select">
	                        ${roomTypeOptions}
	                    </select>
	                </td>
	                <td><input type="text" id="assignedFloor${index}" class="form-control" /></td>
	                <td><input type="number" id="assignedRate${index}" class="form-control room-rate" placeholder="0.00 ₹"/></td>
	                <td>
	                    <input type="date" id="checkinDate${index}" class="form-control checkin-date" 
	                           value="${formatDate(today)}" min="${formatDate(today)}">
	                </td>
	                <td>
	                    <input type="date" id="checkoutDate${index}" class="form-control checkout-date" 
	                           value="${formatDate(tomorrow)}" min="${formatDate(tomorrow)}">
	                </td>
	                <td><span id="days${index}">1</span></td>
	                <td><span id="rowTotal${index}">0.00 ₹</span></td>
	                <td>
	                    <button type="button" class="btn btn-danger" onclick="removeAssignedRoomRow(this)">
	                        <i class="fas fa-trash-alt"></i>
	                    </button>
	                </td>
	            `;

			tbody.appendChild(row);

			// Get references to the new inputs and spans
			const rateInput = document.getElementById(`assignedRate${index}`);
			const checkinInput = document.getElementById(`checkinDate${index}`);
			const checkoutInput = document.getElementById(`checkoutDate${index}`);
			const daysSpan = document.getElementById(`days${index}`);
			const rowTotalSpan = document.getElementById(`rowTotal${index}`);

			// Function to calculate row days and total
			const calculateRowTotal = () => {
				const rate = parseFloat(rateInput.value) || 0;
				const checkinDate = new Date(checkinInput.value);
				const checkoutDate = new Date(checkoutInput.value);

				// Calculate number of nights
				const timeDiff = checkoutDate - checkinDate;
				const nights = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));

				// Update days display (minimum 1 night)
				const displayNights = Math.max(1, nights);
				daysSpan.textContent = displayNights;

				// Calculate row total
				const rowTotal = rate * displayNights;
				rowTotalSpan.textContent = `${rowTotal.toFixed(2)} ₹`;

				// Update grand total
				updateGrandTotal();
			};

			// Add event listeners
			rateInput.addEventListener('input', calculateRowTotal);
			checkinInput.addEventListener('change', function() {
				checkoutInput.min = this.value;
				if (new Date(checkoutInput.value) < new Date(this.value)) {
					checkoutInput.value = this.value;
				}
				calculateRowTotal();
			});
			checkoutInput.addEventListener('change', calculateRowTotal);

			// Initial calculation
			calculateRowTotal();
		})
		.catch(error => {
			console.error('Error fetching room types:', error);
			toastr.error("Failed to load room types. Please try again.");
		});
}

function updateGrandTotal() {
	const tbody = document.getElementById('assignedRoomBody');
	const rows = tbody.querySelectorAll('tr');
	let grandTotal = 0;

	rows.forEach((row, index) => {
		const rowTotalText = row.querySelector(`#rowTotal${index}`).textContent;
		const rowTotal = parseFloat(rowTotalText.replace(' ₹', '')) || 0;
		grandTotal += rowTotal;
	});

	// Update the total amount field (numeric value only for type="number")
	$('#totalAmount').val(grandTotal.toFixed(2));
}

function removeAssignedRoomRow(button) {
	const row = button.closest('tr');
	row.remove();
	updateGrandTotal();
}


