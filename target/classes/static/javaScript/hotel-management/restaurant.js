	$(document).ready(function() {
	
		var initial = $('input[name="orderType"]:checked').val();
		$('#' + initial).show();
	
		$("#serviceGrid").hide();
		$("#amenitiesGrid").hide();
		$("#kitchenGrid").hide();
	
		const gridDiv = document.querySelector('#appraisalGrid');
		new agGrid.Grid(gridDiv, gridOptionsDeg);
		gridOptionsDeg.api.setRowData([]);
	
	
		const gridDiv2 = document.querySelector('#serviceGrid');
		new agGrid.Grid(gridDiv2, gridOptionsSer);
		gridOptionsSer.api.setRowData([]);
	
		const gridDiv3 = document.querySelector('#amenitiesGrid');
		new agGrid.Grid(gridDiv3, gridOptionsAme);
		gridOptionsAme.api.setRowData([]);
	
	
		const gridDiv4 = document.querySelector('#kitchenGrid');
		new agGrid.Grid(gridDiv4, gridOptionsKit);
		gridOptionsKit.api.setRowData([]);
	
	
		const mainActiveTab = $(".nav-link.active")[0];
		if (mainActiveTab) {
			handleTabClick(mainActiveTab);
		}
	
		const ticketActiveTab = $(".header-tab .nav-link.active")[0];
		if (ticketActiveTab) {
			//getAllTickets(ticketActiveTab);
		}
	
		getAllProductList();
	
		$('#roomNoDineIn, #roomNoServ').select2({
			placeholder: "Item",
			allowClear: true,
			width: '100%'
		});

	
	
		var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
		var today = new Date();
		function formatDate(date, format) {
			let day = String(date.getDate()).padStart(2, '0');
			let month = String(date.getMonth() + 1).padStart(2, '0');
			let year = date.getFullYear();
	
			switch (format) {
				case "d-m-Y": return `${day}-${month}-${year}`;
				case "m-d-Y": return `${month}-${day}-${year}`;
				case "Y-m-d": return `${year}-${month}-${day}`;
				default: return `${day}-${month}-${year}`;
			}
		}
	
		var formattedDate = formatDate(today, dateFormat);
	
		$('#dieInn').val(formattedDate);
		$('#takeAwayDate').val(formattedDate);
		$('#serviceInn').val(formattedDate);
	
	
	
	
		$("#toDateCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: today,
			value: formattedDate
		}).on("change", function() {
			$('#serviceDate').val($(this).val());
		});
	
		$('#serviceDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		});
	
		$("#toDateCalendar1").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: today,
			value: formattedDate
		}).on("change", function() {
			$('#serviceDate').val($(this).val());
		});
	
		$('#serviceDate').blur(function() {
			$("#toDateCalendar1").val($(this).val());
		});
	
		$("#toDateCalendar2").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: today,
			value: formattedDate
		}).on("change", function() {
			$('#serviceDate').val($(this).val());
		});
	
		$('#serviceDate').blur(function() {
			$("#toDateCalendar2").val($(this).val());
		});
	});
	
	
	let tabId = "";
	function handleTabClick(element) {
		const tabTarget = element.getAttribute('data-bs-target');
		tabId = tabTarget.replace('#', '');
	
		console.log("tabid-->", tabId);
		if (tabId === "complaints") {
			$("#serviceGrid").hide();
			$("#amenitiesGrid").hide();
			$("#appraisalGrid").show();
			$("#kitchenGrid").hide();
			$('button[data-bs-target="#all"]').trigger('click');
			//ShowAgGrid(targetedId);
		} else if (tabId === "services") {
			$("#serviceGrid").show();
			$("#appraisalGrid").hide();
			$("#amenitiesGrid").hide();
			$("#kitchenGrid").hide();
			$('button[data-bs-target="#all"]').trigger('click');
			//getAllServicesTypesDetails(targetedId);
		} else if (tabId === "amenities") {
			$("#serviceGrid").hide();
			$("#appraisalGrid").hide();
			$("#amenitiesGrid").show();
			$("#kitchenGrid").hide();
			$('button[data-bs-target="#all"]').trigger('click');
			//getAllServicesTypesDetails(targetedId);
		} else if (tabId === "kitchen") {
			$("#serviceGrid").hide();
			$("#appraisalGrid").hide();
			$("#amenitiesGrid").hide();
			$("#kitchenGrid").show();
			$('button[data-bs-target="#all"]').trigger('click');
			//getAllServicesTypesDetails(targetedId);
		}
		else if (tabId === "B2B") {
			$('button[data-bs-target="#all"]').trigger('click');
		}
		else if (tabId === "B2c") {
			$('button[data-bs-target="#all"]').trigger('click');
		}
	}
	
	
	
	
	
	function radioBtnToggle(e) {
		var selected = $(e).val();
		$('.content-box').hide();
		$('#' + selected).show();
	
		clearItemTable();
	}
	
	
	
	function handelLeftTabBtn(e) {
		console.log("tab innnertext-->", e.innerText);
		let btn_text = e.innerText;
		$(".operation_btn").addClass("d-none");
		if (btn_text == "POS" || btn_text == "Payment Receipt") {
			$('.operation_btn').removeClass('active');
			$("#B2B").click();
			$(".b2c_btn").removeClass("d-none");
			$("#B2B").addClass("active");
		}
		else if (btn_text == "Orders") {
			$(".b2c_btn").addClass("d-none");
			$('.operation_btn').removeClass('active');
		}
		else if (btn_text == "Kitchen") {
			$(".b2c_btn").addClass("d-none");
			$('.operation_btn').removeClass('active');
		}
	}
	
	function handel_operation_btn(btn) {
		var activeTabTarget = $('.nav-pills .nav-link.active').data('bs-target');
		console.log("Active Tab Target:", activeTabTarget);
	
		$('.operation_btn').removeClass('active');
		console.log("btn-->", btn);
		if (btn.innerText == "B2B" && activeTabTarget == "#services") {
			$("#B2C_content").removeClass("d-none");
			$("#B2B_content").addClass("d-none");
		}
		else if (btn.innerText == "B2C" && activeTabTarget == "#services") {
			$("#B2C_content").addClass("d-none");
			$("#B2B_content").removeClass("d-none");
	
		}
	
		if (btn.innerText == "B2B" && activeTabTarget == "#amenities") {
			$("#B2C_Content2").removeClass("d-none");
			$("#B2B_Content2").addClass("d-none");
		}
		else if (btn.innerText == "B2C" && activeTabTarget == "#amenities") {
			$("#B2C_Content2").addClass("d-none");
			$("#B2B_Content2").removeClass("d-none");
	
		}
		$(btn).addClass('active');
	}
	
	
	let targetedId;
	function getAllTickets(btn) {
		const target = btn.getAttribute('data-bs-target');
		targetedId = target.substring(1);
	
		if (tabId == 'complaints') {
			//	ShowAgGrid(targetedId);
		} else if (tabId == 'services') {
			//	getAllServicesTypesDetails(targetedId);
		} else if (tabId == 'amenities') {
			//	getAllServicesTypesDetails(targetedId);
		}
	
	}
	
	
	
	const columnDefsDeg = [
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
			headerName: 'Order Id',
			field: "orderId",
			width: 150,
		},
		{
			headerName: 'Order Date',
			field: "orderDate",
			width: 150,
		},
		{
			headerName: "Order For",
			field: "orderFor",
			width: 150,
		},
		{
			headerName: "Table No",
			field: "tableNo",
			width: 150,
		}, {
			headerName: "Room Guest",
			field: "isGuest",
			width: 130,
		}, {
			headerName: "Room No",
			field: "roomNo",
			width: 145,
		}, {
			headerName: "Timing",
			field: "timing",
			width: 150,
		}, {
			headerName: "Attendant",
			field: "attendant",
		}, {
			headerName: "Total",
			field: "total",
			width: 150,
		}
	];
	
	// Define grid options
	const gridOptionsDeg = {
		columnDefs: columnDefsDeg,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
		},
		pagination: true,
		paginationPageSize: 15,
	
		//onSelectionChanged: rowSelectDesig
	};
	
	
	
	
	const columnDefsSer = [
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
			headerName: 'Order Id',
			field: "orderId",
			width: 150,
		},
		{
			headerName: 'Order Date',
			field: "orderDate",
			width: 150,
		},
		{
			headerName: "Order For",
			field: "orderFor",
			width: 150,
		},
		{
			headerName: "Table No",
			field: "tableNo",
			width: 150,
		}, {
			headerName: "Room Guest",
			field: "isGuest",
			width: 130,
		}, {
			headerName: "Room No",
			field: "roomNo",
			width: 145,
		}, {
			headerName: "Timing",
			field: "timing",
			width: 150,
		}, {
			headerName: "Attendant",
			field: "attendant",
		}, {
			headerName: "Total",
			field: "total",
			width: 150,
		}];
	
	// Define grid options
	const gridOptionsSer = {
		columnDefs: columnDefsSer,
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
	
		//onSelectionChanged: rowSelectService
	};
	
	
	
	const columnDefsAme = [
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
			headerName: 'Invoice Id',
			field: "orderId",
			flex: 1
		},
		{
			headerName: 'Date',
			field: "invDte",
			flex: 1
		},
		{
			headerName: "Order For",
			field: "orderFor",
			flex: 1
		},
		, {
			headerName: "Attandant",
			field: "attandant",
			flex: 1
		}, {
			headerName: "Amount",
			field: "amount",
			flex: 1
		}, {
			headerName: "Status",
			field: "status",
			flex: 1
		}];
	
	// Define grid options
	const gridOptionsAme = {
		columnDefs: columnDefsAme,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
		},
		pagination: true,
		paginationPageSize: 15,
	
		//onSelectionChanged: rowSelectAme
	};
	
	
	const columnDefsKit = [
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
			headerName: 'Order Id',
			field: "orderId",
			width: 150,
		},
		{
			headerName: 'Order Date',
			field: "orderDate",
			width: 150,
		},
		{
			headerName: "Order For",
			field: "orderFor",
			width: 150,
		},
		{
			headerName: "Table No",
			field: "tableNo",
			width: 150,
		}, {
			headerName: "Room Guest",
			field: "isGuest",
			width: 130,
		}, {
			headerName: "Room No",
			field: "roomNo",
			width: 145,
		}, {
			headerName: "Timing",
			field: "timing",
			width: 150,
		}, {
			headerName: "Attendant",
			field: "attendant",
		}, {
			headerName: "Total",
			field: "total",
			width: 150,
		}];
	
	// Define grid options
	const gridOptionsKit = {
		columnDefs: columnDefsKit,
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
	
		//onSelectionChanged: rowSelectAme
	};
	function handleRoomGuestToggle(radio) {
		const selectedValue = radio.value;
		console.log("Room Guest selection:", selectedValue);
	
		if (selectedValue === 'yes') {
			$("#dineInnRoom").removeClass("d-none");
		} else {
			$("#dineInnRoom").addClass("d-none");
		}
	}
	
	
	let globalItemList = [];
	function getAllProductList() {
		agGrid.simpleHttpRequest({
			url: 'get-all-restaurant-item',
		}).then(function(data) {
			if (data.code === 'success') {
				globalItemList = data.body;
				addItemRow();
			} else {
				globalItemList = [];
			}
		});
	}
	
	let newKraId = 0;
	function addItemRow() {
		let dataset = [];
		let isValid = true;
	
		$("#med-table tbody tr.med-parent-class").each(function() {
			let a = {
				med_id: $(this).find('.kra-name').val(),
				dose: $(this).find('.quantity').val(),
				notes: $(this).find('.itemNotes').val(),
				amount: $(this).find('.price').val(),
				discount: $(this).find('.discount').val(),
				afterDiscount: $(this).find('.amount').val(),
				gstRate: $(this).find('.gstRate').val(),
				cgst: $(this).find('.cgst').val(),
				sgst: $(this).find('.sgst').val(),
				linetotal: $(this).find('.totalAmount').val()
			};
			dataset.push(a);
		});
	
		for (let item of dataset) {
			if (!item.med_id) return toastr.error("Item Required");
			if (!item.dose) return toastr.error("Quantity Required");
			if (!item.amount) return toastr.error("Price Required");
			if (!item.gstRate) return toastr.error("GST Rate Required");
			if (!item.cgst) return toastr.error("CGST Required");
			if (!item.sgst) return toastr.error("SGST Required");
			if (!item.linetotal) return toastr.error("Total Amount Required");
		}
	
		const rows = document.querySelectorAll('#med-tbody tr');
		let c = rows.length;
	
		rows.forEach((row) => {
			const inputs = row.querySelectorAll('input, select, textarea');
			const editableInputs = row.querySelectorAll('.quantity,.discount');
			inputs.forEach(el => el.disabled = true);
			editableInputs.forEach(el => el.disabled = false);
		});
	
		let dropdownOptions = `<option></option>`;
		globalItemList.forEach(kra => {
			dropdownOptions += `<option value="${kra.key}" data-prod="${kra.id}" data-unit="${kra.code}" data-unitname="${kra.data}" data-gst="${kra.createdBy}">${kra.name}</option>`;
		});
	
		const newRow = $(`
			<tr class="med-parent-class" data-kra-id="${newKraId}">
				<td style="text-align: center" class="slno-td">${c + 1}</td>
				<td>
					<select class="form-control kra-name kra-select2" onchange="setPriceAndGst(${newKraId})">
						${dropdownOptions}
					</select>
					<input type="hidden" class="prodid">
					<input type="hidden" class="itemUnit">
				</td>
				<td><input type="text" class="form-control quantity" onkeyup="calculateLineTotal(${newKraId})"></td>
				<td><input type="text" class="form-control itemNotes"></td>
				<td><input type="text" class="form-control price" readonly></td>
				<td><input type="text" class="form-control discount" onkeyup="calculateLineTotal(${newKraId})"></td>
				<td><input type="text" class="form-control amount" readonly></td>
				<td><input type="text" class="form-control gstRate" readonly></td>
				<td><input type="text" class="form-control cgst" readonly></td>
				<td><input type="text" class="form-control sgst" readonly></td>
				<td><input type="text" class="form-control totalAmount" readonly></td>
				<td>
					<button class="btn go-btn remove-kra mr-2" onclick="removeRow(${newKraId})">
						<i class="fas fa-trash"></i>
					</button>
				</td>
			</tr>
		`);
	
		$('#med-tbody').append(newRow);
	
		// Apply select2 plugin
		setTimeout(() => {
			newRow.find('.kra-select2').select2({
				placeholder: "Item",
				allowClear: true,
				width: '100%'
			});
		}, 0);
	
		newKraId++;
	}
	
	
	
	
	function setPriceAndGst(i) {
		const row = document.querySelector(`tr[data-kra-id="${i}"]`);
		const selectEl = row.querySelector(".kra-name");
		const kraId = selectEl.value;
		const selectedItem = globalItemList.find(item => item.key === kraId);
	
		if (selectedItem) {
			row.querySelector(".price").value = selectedItem.orgName || 0;
			row.querySelector(".gstRate").value = selectedItem.createdBy || 0;
	
			const prodInput = row.querySelector(".prodid");
			if (prodInput) prodInput.value = selectedItem.id || '';
	
			const unitInput = row.querySelector(".itemUnit");
			if (unitInput) unitInput.value = selectedItem.code || '';
	
			calculateLineTotal(i);
		}
	}
	
	
	
	function calculateLineTotal(i) {
		const row = document.querySelector(`tr[data-kra-id="${i}"]`);
	
		const quantity = parseFloat(row.querySelector(".quantity")?.value) || 0;
		const price = parseFloat(row.querySelector(".price")?.value) || 0;
		const gstRate = parseFloat(row.querySelector(".gstRate")?.value) || 0;
		let discount = parseFloat(row.querySelector(".discount")?.value) || 0;
	
		const cgstEl = row.querySelector(".cgst");
		const sgstEl = row.querySelector(".sgst");
		const amountEl = row.querySelector(".amount");
		const totalAmountEl = row.querySelector(".totalAmount");
		const stockqtyEl = row.querySelector(".stockqty");
	
		// Discount validation
		if (discount < 0 || discount > 100) {
			toastr.error("Discount must be between 0 and 100%");
			row.querySelector(".discount").value = '';
			discount = 0;
		}
	
		// Stock check
		if (stockqtyEl) {
			const availableQty = parseInt(stockqtyEl.value) || 0;
			if (quantity > availableQty) {
				cgstEl.value = sgstEl.value = amountEl.value = totalAmountEl.value = '0.00';
				updateTotalAmount();
				return;
			}
		}
	
		// If no quantity
		if (quantity === 0) {
			cgstEl.value = sgstEl.value = amountEl.value = totalAmountEl.value = '0.00';
			updateTotalAmount();
			return;
		}
	
		// Calculation
		const base = quantity * price;
		const discounted = base * (1 - discount / 100);
		const gst = discounted * (gstRate / 100);
		const cgst = gst / 2;
		const sgst = gst / 2;
		const total = discounted + gst;
	
		amountEl.value = discounted.toFixed(2);
		cgstEl.value = cgst.toFixed(2);
		sgstEl.value = sgst.toFixed(2);
		totalAmountEl.value = total.toFixed(2);
	
		updateTotalAmount();
	}
	
	
	function updateTotalAmount() {
		let total = 0;
		$("#med-table tbody tr.med-parent-class").each(function() {
			let t = $(this).find('input.totalAmount').val();
			total += t ? parseFloat(t) : 0;
		});
	
		$("#grantTotalAmount").val(total.toFixed(2));
	}
	function removeRow(i) {
		document.querySelectorAll(`#child-tr-${i}`).forEach(tr => tr.remove());
		document.querySelectorAll(`tr[data-kra-id="${i}"]`).forEach(tr => tr.remove());
	
		updateTotalAmount();
		updateSerialNumbers();
	}
	
	
	function updateSerialNumbers() {
		$("#med-table tbody tr.med-parent-class").each(function(index) {
			$(this).find(".slno-td").text(index + 1);
		});
	}
	function clearItemTable() {
		$("#med-table tbody").empty();
		$("#grantTotalAmount").val("0.00");
		newKraId = 0;
		addItemRow();
	}
	
	/*function for save order details*/
	
		

	function saveOrderDetails() {
	    let restServiceType = $('input[name="orderType"]:checked').val();
	    let isGuest = $('input[name="roomGuest"]:checked').val();

	    let roomNo = '', date = '', time = '', attendant = '', mobileNo = '',table='' ;

	    if (restServiceType === 'dinein') {
	        if (isGuest === 'yes') {
	            roomNo = $('#roomNoDineIn').val();
	        }
	        date = $('#dieInn').val();
	        time = $('#timeDineInn').val();
	        table = $('#tableNo').val();
			attendant = $('#dineInnAttandant').val();
	    } else if (restServiceType === 'takeaway') {
	        date = $('#takeAwayDate').val();
	        time = $('#selectTimeTakeAway').val();
	        attendant = $('#takeAwayAttandant').val();
	        mobileNo = $('#mobileNo').val();
	    } else if (restServiceType === 'roomservice') {
	        roomNo = $('#roomNoServ').val();
	        date = $('#serviceInn').val();
	        time = $('#timeService').val();
	        attendant = $('#attandantService').val();
	        mobileNo = $('#mobileNo').val();
	    }

	  // Validate room number for dinein and roomservice
	    if ((restServiceType === 'dinein' && isGuest === 'yes' || restServiceType === 'roomservice') && !roomNo) {
	        toastr.error("Please select a room number.");
	        return;
	    }

	    // Item Collection
	    let items = [];
	    let validItems = true;

	    $("#med-table tbody tr.med-parent-class").each(function () {
	        const row = $(this);
	        const select = row.find(".kra-select2");
	        const selectedOption = select.find(":selected");
	        const itemKey = selectedOption.val();
	        const itemName = selectedOption.text();

	        const quantity = parseFloat(row.find(".quantity").val()) || 0;
			const notes = row.find(".itemNotes").val() || '';
	        const price = parseFloat(row.find(".price").val()) || 0;
	        const discount = parseFloat(row.find(".discount").val()) || 0;
	        const amount = parseFloat(row.find(".amount").val()) || 0;
	        const gstRate = parseFloat(row.find(".gstRate").val()) || 0;
	        const cgst = parseFloat(row.find(".cgst").val()) || 0;
	        const sgst = parseFloat(row.find(".sgst").val()) || 0;
	        const totalAmount = parseFloat(row.find(".totalAmount").val()) || 0;

	        const prodid = row.find(".prodid").val() || '';
	        const itemUnit = row.find(".itemUnit").val() || '';

	        if (!itemKey) {
	            toastr.error("Please select a valid item.");
	            validItems = false;
	            return false;
	        }

	        if (quantity <= 0) {
	            toastr.error(`Enter quantity for ${itemName}`);
	            validItems = false;
	            return false;
	        }

	        items.push({
	            itemKey,
	            itemName,
	            quantity,
				notes,
	            price,
	            discount,
	            amount,
	            gstRate,
	            cgst,
	            sgst,
	            totalAmount,
	            prodid,
	            itemUnit
	        });
	    });

	    if (!validItems) return;

	    // Prepare final object
	    const obj = {
	        serviceType: restServiceType,
	        roomNo,
	        date,
	        time,
	        attendant,
	        mobileNo,
	        itemList: items,
	        table
	    };

	    console.log("Submitting Order: ", obj);
	    // Loader and Submit
	    $('body').addClass('overlay');
	    $('.loader').show();

	    $.ajax({
	        type: "POST",
	        url: "save-all-restaurant-orders",
	        contentType: "application/json",
	        data: JSON.stringify(obj),
	        success: function (resp) {
	            $('body').removeClass('overlay');
	            $('.loader').hide();

	            if (resp.code === 'success') {
	                toastr.success(resp.message);
	            } else {
	                toastr.error(resp.message || "Something went wrong");
	            }
	        },
	        error: function (err) {
	            console.error(err);
	            toastr.error("Something went wrong");
	            $('body').removeClass('overlay');
	            $('.loader').hide();
	        }
	    });
	}
