	$(document).ready(function() {
	
		const gridDiv = document.querySelector('#appraisalGrid');
		new agGrid.Grid(gridDiv, gridOptionsDeg);
		gridOptionsDeg.api.setRowData([]);
	
	
		const mainActiveTab = $(".nav-link.active")[0];
		if (mainActiveTab) {
			handleTabClick(mainActiveTab);
		}
	
		$("#prev1").hide();
		$(".btn-bill-v").hide();
		getPayModeListAjax();
		$("#billingAmount").val("0.00");
		
	
	});
	
	function nextTab(id) {
		const tabElement = document.querySelector('#' + id + ' a');
		const tab = new bootstrap.Tab(tabElement);
		tab.show();
	}
	
	function backToPrevPage() {
		nextTab('bills-tab')
	}
	
	function billingPrevious() {
		nextTab('balsheet-tab');
	}
	
	function toNextPage() {
		nextTab('paymentDetailsLi');
	}
	
	
	let tabId = "";
	function handleTabClick(element) {
		const tabTarget = element.getAttribute('data-bs-target');
		tabId = tabTarget.replace('#', '');
		if (tabId === "allGuest") {
			getAllTypesCustomer();
		} else if (tabId === "corporate") {
			getAllTypesCustomer();
		} else if (tabId === "corporateGuest") {
			getAllTypesCustomer();
		} else if (tabId === "otherGuest") {
			getAllTypesCustomer();
		}
	}
	
	
	function getAllTypesCustomer() {
		agGrid.simpleHttpRequest({
			url: "get-all-cust-details?id=" + tabId,
		}).then(function(data) {
			var jsonData = JSON.parse(data.body[0]);
			if (jsonData == null || jsonData == " ") {
				gridOptionsDeg.api.setRowData([]);
				getOrderList('')
				getAllServiceById('');
				$("#grandTotal").text('0.00');
				$("#pendingAmount").text('0.00');
			} else {
				var allData = jsonData.allCustDetails;
				gridOptionsDeg.api.setRowData(allData);
				var firstRowNode = gridOptionsDeg.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
			}
	
	
		});
	
	}
	
	
	
	const columnDefsDeg = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			maxWidth: 30,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Booking Id",
			field: "bookingId",
	
		}, {
			headerName: "Customer Id",
			field: "custId",
	
		},
		{
			headerName: "Check-In Id",
			field: "checkInId",
	
		}, {
			headerName: "Room No",
			field: "roomNo",
	
		}, {
			headerName: "Guest Type",
			field: "guestType",
	
		},
		{
			headerName: "Check In Date",
			field: "checkInDateTime",
			valueFormatter: (params) => {
				const date = new Date(params.value);
				if (isNaN(date)) return '';
	
				const day = date.getDate();
				const daySuffix =
					day % 10 === 1 && day !== 11 ? 'st' :
						day % 10 === 2 && day !== 12 ? 'nd' :
							day % 10 === 3 && day !== 13 ? 'rd' : 'th';
	
				const month = date.toLocaleString('default', { month: 'long' });
				const year = date.getFullYear();
	
				let hours = date.getHours();
				const minutes = date.getMinutes().toString().padStart(2, '0');
				const ampm = hours >= 12 ? 'PM' : 'AM';
				hours = hours % 12 || 12;
	
				return `${day}${daySuffix} ${month} ${year} ${hours}:${minutes} ${ampm}`;
			}
		},
		{
			headerName: "Advance Deposit",
			field: "advanceAmnt",
			valueFormatter: (params) => parseFloat(params.value).toFixed(2),
			cellStyle: { textAlign: 'right' }
		},
		{
			headerName: "Outstanding",
			field: "totalAmnt",
			valueFormatter: (params) => parseFloat(params.value).toFixed(2),
			cellStyle: { textAlign: 'right' }
		},
		{
			headerName: "Grand Total",
			field: "grandTotal",
			hide: true
	
		}, {
			headerName: "Due Amnt",
			field: "pendingAmount",
			hide: true
	
		}, {
			headerName: "Cust Ledger Id",
			field: "custLedgerId",
			hide: true
	
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
			width: 100,
			flex: 1
		},
		pagination: true,
		paginationPageSize: 15,
	
		onSelectionChanged: getAllBillingDetails
	};
	
	function getAllBillingDetails() {
		var selectedRows = gridOptionsDeg.api.getSelectedRows();
		if (selectedRows.length > 0) {
			const datas = selectedRows[0];
			const id = (datas.bookingId);
			getAllServiceById(id);
			getOrderList(id)
			$("#grandTotal").text(parseFloat(datas.grandTotal).toFixed(2));
			$("#pendingAmount").text(parseFloat(datas.pendingAmount).toFixed(2));

		} else {
			
		}
	}
	
	
	function getAllServiceById(book_id) {
		agGrid.simpleHttpRequest({
			url: "get-all-hotel-balace-sheet?book_id=" + book_id
		}).then(function(resp) {
			let dataset = [];

			// Make sure body is an array with valid first element
			if (Array.isArray(resp?.body) && resp.body[0]) {
				try {
					dataset = JSON.parse(resp.body[0]);
				} catch (e) {
					console.error("Failed to parse JSON:", e);
					dataset = [];
				}
			}

			if (dataset.length > 0) {
				// Sort by full_dt and load into grid
				dataset = dataset.sort((a, b) => new Date(a.full_dt) - new Date(b.full_dt));
				gridOptionsBS.api.setRowData(dataset);
			} else {
				// Empty grid
				gridOptionsBS.api.setRowData([]);
			}

			// Calculate totals (0.00 if dataset is empty)
			const totalCharges = dataset.reduce((sum, row) => sum + (row.charge_amount || 0), 0);
			const totalPayment = dataset.reduce((sum, row) => sum + (row.pay_amount || 0), 0);
			const due = totalCharges - totalPayment;
			const dueCharges = due > 0 ? 0 : due;
			const duePayment = due > 0 ? due : 0;

			// Update totals at bottom
			gridOptionsBS.api.setPinnedBottomRowData([{
				sku_desc: 'Total',
				charge_amount: totalCharges.toFixed(2),
				pay_amount: totalPayment.toFixed(2)
			}, {
				sku_desc: 'Due',
				charge_amount: dueCharges.toFixed(2),
				pay_amount: duePayment.toFixed(2)
			}]);
		});
	}

	
	
	
	function allowOnlyNumbers(input) {
		let value = input.value;
		value = value.replace(/[^0-9.]/g, '');
	
		const parts = value.split('.');
		if (parts.length > 2) {
			value = parts[0] + '.' + parts.slice(1).join('').replace(/\./g, '');
		}
	
		input.value = value;
	}
	
	
	let globalPayModeList = [];
	function getPayModeListAjax() {
	
	
		agGrid.simpleHttpRequest({
			url: 'his-billing-pharmacy-get-paymode-list'
		}).then(function(data) {
			if (data.code === 'success') {
				globalPayModeList = data.body;
				addBillRow();
			} else {
				globalPayModeList = []
			}
			//paymentDetails();
		})
	}
	
	function initiateBillingProcess() {
	
		let billingAmount = $("#billingAmount").val();
		if (billingAmount == '' || billingAmount == '0.00' || billingAmount == 0) {
			toastr.error("Billing Amount Can't Be 0 Or Left Blank");
			return;
		}
	
		let billDetails = [];
		$("#bill-table tbody tr.bill-parent-class").each(function(index) {
			let a = {
				billamount: billingAmount,
				bankaccount: $("#methodBankSelect").val(),
				costcenter: $("#methodCostCenterSelect").val(),
				remarks: $("#methodPayRemarks").val(),
				slno: index + 1,
				pay_mode: $(this).find('.pay_mode').val(),
				pay_mode_name: $(this).find('.pay_mode option:selected').text(),
				transaction_no: $(this).find('.transaction_no').val(),
				bank: $(this).find('.bank').val(),
				amount: $(this).find('.amount').val(),
			};
			billDetails.push(a);
		})
	
		for (let i = 0; i < billDetails.length; i++) {
			const item = billDetails[i];
	
			if (!item.pay_mode) {
				toastr.error("Payment Mode Required");
				return;
			}
			if (!item.transaction_no && item.pay_mode_name != 'CASH') {
				toastr.error("Transaction/Cheque Number Required");
				return;
			}
			if (!item.amount) {
				toastr.error("Amount Required");
				return;
			}
	
		}
	
		console.log(billDetails);
	
		let selectedRows = gridOptionsDeg.api.getSelectedRows();
		let custLedgerId = '';
		let custId = '';
		let bookId = '';
		if (selectedRows && selectedRows[0]) {
			custLedgerId = selectedRows[0]?.custLedgerId;
			custId = selectedRows[0]?.custId;
			bookId = selectedRows[0]?.bookingId;
		} else {
			toastr.error("Please select one row to pay");
			return;
		}
	
	
		let costCenter = $("#methodCostCenterSelect").val();
		let debitLedgerId = $("#methodBankSelect").val();
		let creditLedgerId = custLedgerId;
		let totalAmount = $("#billingAmount").val();
		let grandTotal = $("#grandTotal").text();
	
		let obj = {
			costCenter, totalAmount, voucherType: 'RECEIPT', description: 'IPD Billing', debitLedgerId, creditLedgerId, billDetails, custId, bookId, grandTotal
		};
	
		console.log(obj);
		submitJournal(obj);
	}
	
	function submitJournal(dataset) {
		swal.fire({
			title: "Are you sure want to Pay?",
			text: "Once paid,Can't revert back !",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#BF05FF",
			confirmButtonText: "Pay",
			showLoaderOnConfirm: true,
			reverseButtons: true,
			confirmButtonAriaLabel: 'Thumbs up, great!',
			cancelButtonText: 'Cancel',
			cancelButtonAriaLabel: 'Thumbs down',
			preConfirm: () => {
				return new Promise((resolve) => {
					setTimeout(() => {
						resolve()
					}, 3000)
				})
			}
		}).then((result) => {
			if (result.value) {
				$('.loader').show();
				$("body").addClass("overlay");
	
				$.ajax({
					type: "POST",
					url: "opd-billing-pay",
					dataType: "json",
					contentType: "application/json",
					data: JSON.stringify(dataset),
					success: function(response) {
						if (response.code == "201" || response.code == "200") {
							$('.loader').hide();
							$("body").removeClass("overlay");
							toastr.success("Paid Successfully");
							getAllTypesCustomer();
							clearValue();
						} else {
							$('.loader').hide();
							$("body").removeClass("overlay");
							toastr.error(response.message);
						}
					},
					error: function(response) {
						$('.loader').hide();
						$("body").removeClass("overlay");
						toastr.error("Something went wrong");
					}
				})
			}
		})
	}
	
	function clearValue() {
		$("#billingAmount").val("0.00");
		$("#methodBankSelect").val('TLM0453').trigger('channge');
		$("#methodCostCenterSelect").val('cc027').trigger('channge');
		$("#methodPayRemarks").val('');
		clearBillTableValues();
	}
	
	function clearBillTableValues() {
	  $("#bill-table tbody tr").each(function () {
	    $(this).find('.pay_mode').val('PMOD001').trigger('change');
	    $(this).find('.transaction_no').val('');
	    $(this).find('.bank').val('');
	    $(this).find('.amount').val('');
	  });
	}

