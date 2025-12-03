
$(document).ready(
	function() {


		var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
		tooltipTriggerList.forEach(function(tooltipTriggerEl) {
			new bootstrap.Tooltip(tooltipTriggerEl)
		});

		$('#clerenceBtn').attr("disabled", true);


		$("#bankName").select2({
			placeholder: "Select Bank",
			allowClear: true
		});

		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		var rowData = [];
		gridOptions.api.setRowData(rowData);

		$('#add').attr("disabled", true);

		var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";
		var fromDate = new Date();
		fromDate.setDate(1);
		var toDate = new Date();
		function formatDate(date) {
			var day = ("0" + date.getDate()).slice(-2); // Ensure 2-digit day
			var month = ("0" + (date.getMonth() + 1)).slice(-2); // Ensure 2-digit month
			var year = date.getFullYear();
			return day + '-' + month + '-' + year; // Return in dd-mm-YYYY format
		}

		// Format the fromDate and toDate for the datetimepicker
		var formattedFromDate = formatDate(fromDate);
		var formattedToDate = formatDate(toDate);
		$('#filterFromDate').val(formattedFromDate);
		$('#filterToDate').val(formattedToDate);
		$("#ClearanceDate").val(formattedToDate);
		console.log("formattedToDate::", formattedToDate,
			"formattedToDate::", formattedToDate)
		// Initialize the DateTimePicker for "from date"
		$('#fromDateCalender').datetimepicker({
			format: dateFormat, // Use the retrieved date format
			closeOnDateSelect: true,
			timepicker: false,
			value: formattedFromDate,
			formatDate: 'd-m-Y',
			maxDate: 0,
		}).on('change', function() {
			$('#filterFromDate').val($(this).val());
			filterByDate();
		});

		// Initialize the DateTimePicker for "to date"
		$('#toDateCalendar').datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			value: formattedToDate,
			formatDate: 'd-m-Y',
			maxDate: 0,
		}).on('change', function() {
			$('#filterToDate').val($(this).val());
			filterByDate(); // Call the filtering function on date change
		});

		// Synchronize manual input on blur
		$('#filterFromDate').blur(function() {
			$('#fromDateCalender').val($(this).val());
		});

		$('#filterToDate').blur(function() {
			$('#toDateCalendar').val($(this).val());
		});
		filter();

		$("#chqClrDateCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: 0,
		}).on("change", function() {
			$('#chqClrDate').val($(this).val());
		})

		$('#chqClrDate').blur(function() {
			$("#chqClrDateCalendar").val($(this).val());
		})



		$("#ClearanceCalender").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: 0,
		}).on("change", function() {
			$('#ClearanceDate').val($(this).val());
		})

		$('#ClearanceDate').blur(function() {
			$("#ClearanceCalender").val($(this).val());
		})

	});

function filter() {
	var bank = $('#bankName').val();
	var type = $('#type').val();
	let fromDate1 = $("#filterFromDate").val();
	let toDate1 = $("#filterToDate").val();
	let bankName = $('#bankName option:selected').text();

	$("#activeBankName").text(bankName);


	agGrid.simpleHttpRequest({
		url: "bank-reconciliation-view?fromDate=" + fromDate1 + "&toDate=" + toDate1 + "&bank=" + bank + "&type=" + type
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var viewData = jsonData.viewData || [];
		var viewSummary = jsonData.viewSummary;

		var len = viewData.length;
		$('#totalReq').find('span').html(len);

		// Set data to grid
		gridOptions.api.setRowData(viewData);

		// ✅ Calculate totals (as numbers, not strings)
		let totalDebit = 0;
		let totalCredit = 0;

		viewData.forEach(row => {
			totalDebit += parseFloat(row.debit_amt) || 0;
			totalCredit += parseFloat(row.credit_amt) || 0;
		});

		// ✅ Set raw numbers (not formatted) for valueFormatter to apply
		const pinnedRow = [{
			doc_ref_no: 'Total',
			debit_amt: totalDebit,
			credit_amt: totalCredit
		}];

		gridOptions.api.setPinnedBottomRowData(pinnedRow);

		// ✅ Update UI summaries
		if (viewSummary != null && viewData != 'null') {
			const summary = viewSummary[0] || {};

			$('#bookBalance').text(formatCrDr(summary.bookBalance));
			$('#chqIssuedUnclr').text(formatCrDr(summary.chqIssuedUnclr));
			$('#chqDepUnclr').text(formatCrDr(summary.chqDepUnclr));
			$('#bankDebit').text(formatCrDr(summary.bankDebit));
			$('#bankCredit').text(formatCrDr(summary.bankCredit));
			$('#bankStatementBal').text(formatCrDr(summary.bankStatementBal));

			$('#totalVoucher').text(summary.totalVoucher);
			$('#v_debit').text(formatINR(summary.v_debit));
			$('#v_credit').text(formatINR(summary.v_credit));
			$('#totalUnreconciledVch').text(summary.totalUnreconciledVch);
			$('#urv_debit').text(formatINR(summary.urv_debit));
			$('#urv_credit').text(formatINR(summary.urv_credit));
			$('#totalReconciledVch').text(summary.totalReconciledVch);
			$('#rv_debit').text(formatINR(summary.rv_debit));
			$('#rv_credit').text(formatINR(summary.rv_credit));
			$(".innercontent").removeClass("d-none");
		}
		else {
			$(".innercontent").addClass("d-none");
		}
	});
}


function formatCrDr(value) {
	const num = parseFloat(value) || 0;

	if (num === 0) {
		return '0.00';
	}
	const formatted = Math.abs(num).toLocaleString('en-IN', {
		minimumFractionDigits: 2,
		maximumFractionDigits: 2
	});
	return num < 0 ? `${formatted} CR` : `${formatted} DR`;
}
function formatINR(value) {
	const num = parseFloat(value);
	if (isNaN(num)) return '0.00';

	const parts = num.toFixed(2).split('.');
	const integerPart = parts[0];
	const decimalPart = parts[1];

	// Add commas in Indian format
	const lastThree = integerPart.slice(-3);
	const otherDigits = integerPart.slice(0, -3);

	const formattedInteger = otherDigits.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + (otherDigits ? "," : "") + lastThree;

	return formattedInteger + '.' + decimalPart;
}


function resetFilter() {
	// Reset dropdowns to first option
	document.getElementById("bankName").selectedIndex = 0;
	document.getElementById("type").selectedIndex = 0;

	// Date format from localStorage or default
	var dateFormat = localStorage.getItem("dateFormat") || "d-m-Y";

	// Prepare dates
	var fromDate = new Date();
	fromDate.setDate(1); // 1st day of current month

	var toDate = new Date(); // today

	// Format date to dd-mm-yyyy
	function formatDate(date) {
		var day = ("0" + date.getDate()).slice(-2);
		var month = ("0" + (date.getMonth() + 1)).slice(-2);
		var year = date.getFullYear();

		return day + '-' + month + '-' + year; // or adapt based on dateFormat
	}

	// Apply formatted dates to input fields
	document.getElementById("filterFromDate").value = formatDate(fromDate);
	document.getElementById("filterToDate").value = formatDate(toDate);
}

var colmnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: function(params) {
			return params.data.status != 'Reconciled';
		},
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: "Cheque Clearance Date",
		field: "cl_date",
		width: 150,
		pinned: 'left',
		cellStyle: params => {
			if (params.value === null || params.value === '') {
				return { backgroundColor: '#ffe6e6' }; // light red
			} else {
				return { backgroundColor: '#e6ffe6' }; // light green
			}
		}

	}, {
		headerName: 'Document Ref No',
		field: "doc_ref_no",
		width: 200,
	}, {
		headerName: 'Document Date',
		field: "doc_date",
		width: 200,
	}, {
		headerName: 'Cheque/Online No.',
		field: "ch_no",
	}, {
		headerName: 'Cheque/Online Date',
		field: "ch_date",
	},/* {
		headerName: 'Debit Amount',
		field: 'debit_amt',
		cellStyle: { textAlign: 'right' },
		valueFormatter: params => {
			const value = parseFloat(params.value);
			return isNaN(value) ? '' : value.toFixed(2);
		}
	}
, {
		headerName : 'Credit Amount',
		field : "credit_amt",
		cellStyle : {
			textAlign : 'right'
		} 
	},*/, {
		headerName: 'Debit Amount',
		field: 'debit_amt',
		cellStyle: { textAlign: 'right', fontWeight: params => params.node.rowPinned ? 'bold' : 'normal' },
		valueFormatter: params => {
			const value = parseFloat(params.value);
			return isNaN(value) ? '0.00' : formatINR(value);
		}
	},
	{
		headerName: 'Credit Amount',
		field: 'credit_amt',
		cellStyle: { textAlign: 'right', fontWeight: params => params.node.rowPinned ? 'bold' : 'normal' },
		valueFormatter: params => {
			const value = parseFloat(params.value);
			return isNaN(value) ? '0.00' : formatINR(value);
		}
	}
	,
	{
		headerName: 'Source Type',
		field: 'source_type',
		cellRenderer: function(params) {
			const value = params.value;
			if (value === 'Accounts Receivable') {
				return `<span title="Accounts Receivable" style="font-weight:bold;">
	                        <i class="fas fa-arrow-down text-success" style="font-size: 1.1rem;"></i> ${value}
	                    </span>`;
			} else if (value === 'Accounts Payable') {
				return `<span title="Accounts Payable" style="font-weight:bold;">
	                        <i class="fas fa-arrow-up text-danger" style="font-size: 1.1rem;"></i> ${value}
	                    </span>`;
			} else {
				return value || '';
			}
		}
	}, {
		headerName: 'Status',
		field: 'status',
		cellClassRules: {
			'status-green': params => params.value === 'Reconciled',
			'status-red': params => params.value !== 'Reconciled'
		},
		cellStyle: {
			fontWeight: 'bold',
			textAlign: 'center'
		}
	}, {
		headerName: 'Party Code',
		field: "party_code",
		width: 100,
	}, {
		headerName: 'Party Name',
		field: "party_name",
		width: 200,
	}, {
		headerName: 'Bank Account Number',
		field: "ac_no",
	}, {
		headerName: 'Bank Name',
		field: "bank_name",
	}, {
		headerName: 'Bank Branch',
		field: "bank_branch",
	}, {
		headerName: 'Narration',
		field: "narration",
		cellRenderer: params => params.value
	},];

var gridOptions = {
	columnDefs: colmnDefs,
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
	getRowNodeId: function(data) {
		return data.doc_ref_no;
	},
	getRowStyle: function(params) {
		if (params.node.rowPinned) {
			return {
				fontWeight: 'bold',
				backgroundColor: '#e6f2ff'  // light blue
			};
		}
	},

	onSelectionChanged: rowSelect
};
function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var status = selectedData.map(node => node.status);

	if (rowCount > 0) {
		$('#clerenceBtn').attr("disabled", false);

	} else {
		$('#clerenceBtn').attr("disabled", true);
	}
}

function downloadDetails() {
	const bankText = $("#bankName option:selected").text() || "ICICI Bank";
	const accountMatch = bankText.match(/(\d{6,})/);
	let bankAccountNo = "";
	let bankName = bankText;

	if (accountMatch) {
		bankAccountNo = accountMatch[1];
		bankName = bankText.replace(/[-–—]?[A-Z]{2,}-?\d{6,}/, '').trim();
		bankName = bankName.replace(/[-–—]*$/, '').trim();
	}

	const currency = "INR";
	const fromDate = $("#filterFromDate").val();
	const toDate = $("#filterToDate").val();
	const currentDate = new Date().toLocaleDateString('en-GB').split('/').join('_');
	const fileName = `Consolidated_Bank_Reconciliation_${fromDate}_${toDate}.xlsx`;
	const organization = $("#organization").val();
	const division = $("#division").val();

	const bookBalance = $("#bookBalance").text();
	const chqIssuedUnclr = $("#chqIssuedUnclr").text();
	const chqDepUnclr = $("#chqDepUnclr").text();
	const bankDebit = $("#bankDebit").text();
	const bankCredit = $("#bankCredit").text();
	const bankStatementBal = $("#bankStatementBal").text();

	const headersFromColDef = colmnDefs.filter(col => col.field && col.headerName).map(col => col.headerName);
	const fieldKeys = colmnDefs.filter(col => col.field && col.headerName).map(col => col.field);
	const totalCols = headersFromColDef.length;

	const rowData = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(node) {
		const flatRow = {};
		fieldKeys.forEach(key => {
			let value = node.data?.[key];
			if (key === 'debit_amt' || key === 'credit_amt') {
				let num = parseFloat(value);
				flatRow[key] = isNaN(num) ? 0.00 : parseFloat(num.toFixed(2));
			} else {
				flatRow[key] = value ?? '';
			}
		});
		rowData.push(flatRow);
	});
	const pinnedRowCount = gridOptions.api.getPinnedBottomRowCount();
	if (pinnedRowCount > 0) {
		const pinnedNode = gridOptions.api.getPinnedBottomRow(0);
		const pinnedData = pinnedNode.data;
		const pinnedRow = {};
		fieldKeys.forEach(key => {
			if (key === 'debit_amt' || key === 'credit_amt') {
				let num = parseFloat(pinnedData?.[key]);
				pinnedRow[key] = isNaN(num) ? 0.00 : parseFloat(num.toFixed(2));
			} else {
				pinnedRow[key] = pinnedData?.[key] ?? '';
			}
		});
		rowData.push(pinnedRow);
	}

	const creditColIndex = headersFromColDef.findIndex(header => header.toLowerCase().includes("credit"));
	const pad = Array(creditColIndex).fill("");

	const bankDetails = [
		[...pad, "Bank Name", bankName],
		[...pad, "Bank Account No.", bankAccountNo],
		[...pad, "Bank Currency", currency],
		[...pad, "As On Date", toDate || currentDate],
		[...pad, "Organization", organization || organization],
		[...pad, "Division", division || division]
	];

	const summaryData = [
		[...pad, "Add/Less", "Particulars", "Total Amount"],
		[...pad, "", "As per our book", bookBalance],
		[...pad, "Add", "Cheque issued but not yet cleared", chqIssuedUnclr],
		[...pad, "Less", "Cheque deposited yet to be cleared", chqDepUnclr],
		[...pad, "Less", "Amount debited by the bank", bankDebit],
		[...pad, "Add", "Amount credited by bank not accounted by us", bankCredit],
		[...pad, "", "Balance as per bank statement", bankStatementBal]
	];

	const headerSection = [
		["Consolidated Bank Reconciliation Detailed Report"],
		[],
		[...pad, "Summary Card", "", ""],
		[],
		...bankDetails,
		[],
		...summaryData,
		[],
		headersFromColDef
	];

	const ws = XLSX.utils.aoa_to_sheet(headerSection);
	XLSX.utils.sheet_add_json(ws, rowData, {
		origin: -1,
		header: fieldKeys,
		skipHeader: true
	});

	const summaryLabelRow = 2;
	const agGridHeaderRowIndex = headerSection.findIndex(row => JSON.stringify(row) === JSON.stringify(headersFromColDef));

	ws['!merges'] = [
		{ s: { r: 0, c: 0 }, e: { r: 0, c: totalCols - 1 } },
		{ s: { r: summaryLabelRow, c: creditColIndex }, e: { r: summaryLabelRow, c: creditColIndex + 2 } }
	];

	ws['!cols'] = headersFromColDef.map(() => ({ wch: 25 }));

	ws["A1"].s = {
		font: { bold: true, sz: 14, color: { rgb: "FFFFFF" } },
		alignment: { horizontal: "center", vertical: "center" },
		fill: { fgColor: { rgb: "4472C4" } }
	};

	const summaryHeaderCell = XLSX.utils.encode_cell({ r: summaryLabelRow, c: creditColIndex });
	ws[summaryHeaderCell].s = {
		font: { bold: true, sz: 13, color: { rgb: "000000" } },
		alignment: { horizontal: "center", vertical: "center" },
		fill: { fgColor: { rgb: "D9D9D9" } }
	};

	const summaryHeaderRow = summaryLabelRow + 2 + bankDetails.length + 1;
	for (let i = 0; i < 3; i++) {
		const cellRef = XLSX.utils.encode_cell({ r: summaryHeaderRow, c: creditColIndex + i });
		if (ws[cellRef]) {
			ws[cellRef].s = {
				font: { bold: true, color: { rgb: "000000" } },
				alignment: { horizontal: "center", vertical: "center" },
				fill: { fgColor: { rgb: "D9D9D9" } }
			};
		}
	}

	ws['!cols'][creditColIndex] = { wch: 20 };
	ws['!cols'][creditColIndex + 1] = { wch: 40 };
	ws['!cols'][creditColIndex + 2] = { wch: 25 };

	for (let i = 1; i < summaryData.length; i++) {
		const cellRef = XLSX.utils.encode_cell({ r: summaryHeaderRow + i, c: creditColIndex + 2 });
		if (ws[cellRef]) {
			ws[cellRef].s = {
				alignment: { horizontal: "right" },
				font: { bold: i === 1 || i === 6 }
			};
		}
	}
	fieldKeys.forEach((key, colIdx) => {
		const headerName = headersFromColDef[colIdx]?.toLowerCase();
		const isAmountField = key === 'debit_amt' || key === 'credit_amt' || (headerName.includes("amount") && !headerName.includes("narration"));

		for (let rowIdx = agGridHeaderRowIndex + 1; rowIdx < agGridHeaderRowIndex + 1 + rowData.length; rowIdx++) {
			const cellRef = XLSX.utils.encode_cell({ r: rowIdx, c: colIdx });
			if (!ws[cellRef]) continue;

			if (isAmountField) {
				ws[cellRef].t = 'n';
				ws[cellRef].z = '#,##,##0.00'; // Indian number format
				ws[cellRef].s = {
					alignment: { horizontal: "right" }
				};
			}
		}
	});

	const lastRowIdx = agGridHeaderRowIndex + rowData.length;
	for (let colIdx = 0; colIdx < fieldKeys.length; colIdx++) {
		const cellRef = XLSX.utils.encode_cell({ r: lastRowIdx, c: colIdx });
		if (ws[cellRef]) {
			ws[cellRef].z = '#,##,##0.00';
			ws[cellRef].s = {
				font: { bold: true, color: { rgb: "000000" } },
				alignment: { horizontal: "right" }
			};
		}
	}

	headersFromColDef.forEach((_, colIdx) => {
		const cellRef = XLSX.utils.encode_cell({ r: agGridHeaderRowIndex, c: colIdx });
		if (ws[cellRef]) {
			ws[cellRef].s = {
				font: { bold: true, color: { rgb: "FFFFFF" } },
				alignment: { horizontal: "center" },
				fill: { fgColor: { rgb: "4472C4" } },
				border: {
					top: { style: "thin", color: { rgb: "000000" } },
					bottom: { style: "thin", color: { rgb: "000000" } },
					left: { style: "thin", color: { rgb: "000000" } },
					right: { style: "thin", color: { rgb: "000000" } }
				}
			};
		}
	});

	const wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, "Bank Reconciliation");
	XLSX.writeFile(wb, fileName);
}

function onQuickFilterChanged(event) {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

function parseDateDMY(str) {
	const parts = str.split("-");
	const day = parseInt(parts[0], 10);
	const month = parseInt(parts[1], 10) - 1; // Month is 0-indexed
	const year = parseInt(parts[2], 10);
	return new Date(year, month, day);
}

function formatDate(date) {
	const day = ("0" + date.getDate()).slice(-2);
	const month = ("0" + (date.getMonth() + 1)).slice(-2);
	const year = date.getFullYear();
	return `${day}-${month}-${year}`;
}

function filterByDate() {
	let fromDateVal = $("#filterFromDate").val();
	let toDateVal = $("#filterToDate").val();

	if (!fromDateVal && !toDateVal) {
		$("#messageParagraph").text("Both From Date and To Date fields are required.");
	} else if (!fromDateVal) {
		$("#messageParagraph").text("From Date is required.");
	} else if (!toDateVal) {
		$("#messageParagraph").text("To Date is required.");
	} else {
		const fromDate = parseDateDMY(fromDateVal);
		const toDate = parseDateDMY(toDateVal);

		console.log("fromDate===", fromDate, "toDate===", toDate);

		if (toDate < fromDate) {
			$("#messageParagraph").text("To Date must be greater than or equal to From Date.");

			const defaultFromDate = new Date();
			defaultFromDate.setDate(1);

			const defaultToDate = new Date();

			$("#filterFromDate").val(formatDate(defaultFromDate));
			$("#filterToDate").val(formatDate(defaultToDate));
		} else {
			filter(); // Call your filtering logic
			return;
		}
	}

	$("#msgOkModal").removeClass("btn3").addClass("btn1");
	$("#msgModal").modal("show");
}

function clearance() {
	$('#clearanceModal').modal('toggle');
	$("#chqClrDate").val("");
	$("#remarks").val("");

	$(".clearence_card").removeClass("d-none");
	$(".innercontent").addClass("d-none");

	$("#saveClearanceBtn").removeClass("d-none");
	$("#cancelBtn").removeClass("d-none");
	$("#clerenceBtn").addClass("d-none");
}

function cancelBtn() {

	$(".clearence_card").addClass("d-none");
	$(".innercontent").removeClass("d-none");

	$("#saveClearanceBtn").addClass("d-none");
	$("#cancelBtn").addClass("d-none");
	$("#clerenceBtn").removeClass("d-none");
}

function saveClearance() {
	var validation = true;
	const selectedData = gridOptions.api.getSelectedNodes().map(node => node.data);
	const vid = selectedData.map(data => data.doc_ref_no);
	var chqClrDate = $("#ClearanceDate").val();
	var remarks = $("#clearanceRemark").val();
	if (chqClrDate == null || chqClrDate == "") {
		validation = validationUpdated("Cheque Clearance Date", "chqClrDate");
		toastr.error("Cheque Clearance Date Needed!");
	}
	if (validation) {
		$.ajax({
			type: "GET",
			url: "bank-reconciliation-clearance-save?vid=" + vid + "&chqClrDate=" + chqClrDate + "&remarks=" + remarks,
			async: false,
			success: function(response) {
				if (response.code == "success") {
					toastr.success("Cheque Cleared Successfully...");
					cancelBtn();
					filter();
					$('#clearanceModal').modal('hide');
				} else {

					toastr.success("Unexpected error. Please try again...");
				}

			},
			error: function(data) {
			}
		});
	} else {
		$("#clearanceModal").modal('show');
	}
}
