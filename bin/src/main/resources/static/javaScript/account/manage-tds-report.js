$(function() {

	getCurrentFinancialYear();
	var dateFormat = localStorage.getItem("dateFormat");
	function parseDate(dateStr, format) {
		var parts = dateStr.split('-');
		return new Date(parts[2], parts[1] - 1, parts[0]);
	}

	function updateDatePickers(yearStart, yearEnd) {
		var minDate = `01-04-${yearStart}`;
		var maxDate = `31-03-${yearEnd}`;
		var minDateParsed = parseDate(minDate, dateFormat);
		var maxDateParsed = parseDate(maxDate, dateFormat);

		$("#fromDateCalendarTDS").datetimepicker('destroy').datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			minDate: minDateParsed,
			maxDate: maxDateParsed
		}).on("change", function() {
			$('#fromDateTDS').val($(this).val());
		});

		$('#fromDateTDS').blur(function() {
			$("#fromDateCalendarTDS").val($(this).val());
		});

		$("#toDateCalendarTDS").datetimepicker('destroy').datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			minDate: minDateParsed,
			maxDate: maxDateParsed
		}).on("change", function() {
			$('#toDateTDS').val($(this).val());
		});

		$('#toDateTDS').blur(function() {
			$("#toDateCalendarTDS").val($(this).val());
		});
	}

	function handleDropdownChange() {
		var datee = $("#orderStatusFilter").val();
		var yearRange = datee.split('-');
		if (yearRange.length === 2) {
			var yearStart = yearRange[0];
			var yearEnd = yearRange[1];
			updateDatePickers(yearStart, yearEnd);
		}
	}

	$("#orderStatusFilter").change(function() {
		handleDropdownChange();
	});


	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var toDate = toDateString.toString();

	$("#toDateTDS").val(toDate);
	$("#fromDateTDS").val(fromDate);
	handleDropdownChange();
	viewTdsReceivable();

	var gridDivTds = document.querySelector('#myGridTds');
	new agGrid.Grid(gridDivTds, gridOptionsTds);

	$("#myGrid").show();
	$("#delete").attr("disabled", true);


});

$(function() {
	$('.collapse').on('show.bs.collapse', function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

})

function getCurrentFinancialYear() {
	var fiscalyear = "";
	var today = new Date();
	if ((today.getMonth() + 1) <= 3) {
		fiscalyear = (today.getFullYear() - 1) + "-" + today.getFullYear()
	} else {
		fiscalyear = today.getFullYear() + "-" + (today.getFullYear() + 1)
	}
	$("#orderStatusFilter").val(fiscalyear);
	return fiscalyear
}

//search bar
function onQuickFilterChanged() {
	gridOptionsTds.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var displayedRowCount = gridOptionsTds.api.getDisplayedRowCount();
	console.log("row count-->", displayedRowCount);

	var len = displayedRowCount;
	$('#totalReq').find('span').html(len);
}

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

function statusFormatter(params) {
	return params.value === 'true' ? 'Active' : 'Inactive';
}

// column Defs
const columnDefsTds = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: false,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Voucher ID',
		field: "voucherId",
		width: 200,
		hide: true,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	},
	{
		headerName: 'ID',
		field: "grnId",
		width: 130,
		cellStyle: {
			textAlign: 'Center',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	},

	{
		headerName: "Date",
		field: "voucherDate",
		width: 180,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: "Particulars",
		field: "particularName",
		width: 200,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	},
	{
		headerName: "Vendor Name",
		field: "vendorName",
		width: 250,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	}, {
		headerName: "Invoice ID",
		field: "invoiceId",
		width: 150,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		}
	},

	{
		headerName: "TAXABLE AMOUNT",
		field: "Taxableamt",
		width: 150,
		hide: true,
		cellStyle: {
			textAlign: 'right',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	},

	{
		headerName: "INVOICE VALUE",
		field: "ACTUALINVVAL",
		width: 150,
		cellStyle: {
			textAlign: 'right',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	},
	{
		headerName: "TDS Amount",
		field: "tdsAmount",
		width: 150,
		cellStyle: {
			textAlign: 'right',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return value;
		}
	},
	{
		headerName: "TDS Rate(%)",
		field: "tdsRate",
		width: 150,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return value;
		}
	},

	{
		headerName: "NET AMOUNT VALUE",
		field: "NetAmount",
		width: 150,
		cellStyle: {
			textAlign: 'right',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var value = params.value;
			return amountFormatter(value);
		}
	}, {
		headerName: "Status",
		field: "paymentStatus",
		width: 150,
		cellStyle: {
			textAlign: 'left',
			fontFamily: 'Montserrat, sans-serif',
			fontSize: 'smaller'
		},
		cellRenderer: function(params) {
			var statusStyle = getStatusStyle(params.data.paymentStatus);
			return `<div style="${statusStyle}">${params.data.paymentStatus}</div>`;
		}
	}];

const gridOptionsTds = {
	columnDefs: columnDefsTds,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	/* onSelectionChanged : rowSelect,
	getRowNodeId : function(data) {
		return data.bankId;
	} */
};

function getStatusStyle(paymentStatus) {
	console.log("paymentStatus-->", paymentStatus);
	switch (paymentStatus) {
		case "Paid":
			return 'color: blue; font-weight: bold;';
		case "Received":
			return 'color: blue; font-weight: bold;';
		case "UnPaid":
			return 'color: red; font-weight: bold;';
		case "Not Received":
			return 'color: red; font-weight: bold;';

	}
}

var deleteId = "";
function rowSelect() {
	//alert('hello select');
	var selectedRows = gridOptionsTds.api.getSelectedRows();
	deleteId = "";

	for (var i = 0; i < selectedRows.length; i++) {
		deleteId = deleteId + '"' + selectedRows[i].bankId + '",';

	}
	deleteId = deleteId.substring(0, deleteId.length - 1);

	var rowCount = 0;

	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').prop("disabled", false).css("opacity", 1);
		$('#add').prop("disabled", true).css("opacity", 0.4);

	} else {

		$('#delete').prop("disabled", true).css("opacity", 0.4);
		$('#add').prop("disabled", false).css("opacity", 1);
	}

}
// for new button
function newBtn() {
	//	alert('hello');
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$("#demo").show();

}
// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$("#demo").hide();

	$('#bankId').text("");
	$('#bankName').val("");
	$('#status').val("");
	$('#description').val("");
	$('#add').prop("disabled", false).css("opacity", 1);

	agGrid.simpleHttpRequest({
		url: "manage-tds-report-throughAjax"
	}).then(function(data) {
		gridOptionsTds.api.setRowData(data);
	});
}

// Edit & stage change 
function editPage(id) {
	//alert(id);
	$(".loader").show();
	var editId = id.split(",");

	var bankId = editId[0];

	var modal = editId[1];
	//alert('bankId------'+bankId);
	$("#demo").show();

	$.ajax({
		type: "GET",
		url: "view-account-bank-edit?id=" + bankId,
		async: false,
		success: function(response) {
			console.log("response for edit------"
				+ JSON.stringify(response));
			if (response.code == "Success") {

				$("#add").hide();
				$("#copy").hide();
				$("#delete").hide();
				$("#myGrid").hide();
				$("#searchRowDiv").hide();
				$("#totalReq").hide();
				$("#statusDiv").hide();
				$("#idDiv").hide();
				$("#collapseFour").hide();
				$("#headingFour").hide();
				$("#myGridActivity").hide();
				$(".loader").hide();
				$("#demo").show();

				$(".container").hide();

				var bankId = $("#bankId").text(response.body[0].bankId);

				$("#bankName").val(response.body[0].bankName);
				$("#status").val(response.body[0].status);

				var statuset = response.body[0].status;
				console.log("Status----", statuset);

				if (statuset == "true") {
					$("#status").prop('selectedIndex', 1);
				} else {
					$("#status").prop('selectedIndex', 2);
				}

				$('#description').val(response.body[0].description);

			}
		}
	})
}

// Amount Formatter - Comma Separation INR Standard
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

var tabSelectiontext = "";
// Filtered Data TDS
function viewFilteredDataTDS() {
	$('.loader').show();
	var fromDateFilter = $("#fromDateTDS").val();
	var toDateFilter = $("#toDateTDS").val();
	var tdsLedgerId = "";
	var activeStatus = $("#activeStatus").val();
	if (activeStatus == "Pay") {
		tdsLedgerId = $("#tdsPayableLedgerId").val();
	} else if (activeStatus == "Receive") {
		tdsLedgerId = $("#tdsReceivableLedgerId").val();
	}
	var validation = true;

	if (validation) {
		agGrid.simpleHttpRequest({
			url: "manage-tds-report-filteredData?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter + "&tdsLedgerId=" + tdsLedgerId + "&activeStatus=" + activeStatus,
		}).then(function(data) {
			$('.loader').hide();



			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewTdsList;
			if (allData == "" || allData == "null" || allData == null) {
				$("#downloadPdfBtnId").prop("disabled", true);
				$('#totalReq').find('span').html('0');
				gridOptionsTds.api.setRowData();
				$("#totalAmountFooter").val("0.00");
				$(".noDataFoundContainer").show();
				$(".container").hide();
				if (tabSelectiontext == "receivable") {
					$(".noDataPara").html("No TDS receivable records are found at this time. This might indicate no deductions have been reported or your data has not been updated. Kindly verify your transaction details or revisit this section for future updates.")
				}
				else {
					$(".noDataPara").html("No TDS payable records are currently available. This could be due to the absence of transactions or incomplete updates in your records. Please review your financial entries or check back later for the latest information.")
				}

			} else {
				$("#downloadPdfBtnId").prop("disabled", false);
				var len = allData.length;
				$('#totalReq').find('span').html(len);
				gridOptionsTds.api.setRowData(allData);


				console.log("all Data tDS -->", allData);
				var totalSum = allData.reduce(function(acc, curr) {
					return acc + parseFloat(curr.tdsAmount.replace(/,/g, ''));
				}, 0);
				$("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
				$(".noDataFoundContainer").hide();
				$(".container").show();
				if (tabSelectiontext == "receivable") {
					$(".download-para").html("The TDS Receivable Report offers a complete summary of the tax deducted at source receivable by you. Download the PDF to track your receivables and streamline your financial records.")
				}
				else {
					$(".download-para").html("The TDS Payable Report provides a detailed overview of your tax deducted at source liabilities. Download the PDF to review your payable amounts and ensure timely compliance with tax regulations.")
				}
			}
		});
	}
}

function downloadPdfTds() {
	var fromDateFilter = $("#fromDate").val();
	var toDateFilter = $("#toDate").val();
	var tdsLedgerId = "";
	var reportType = "";
	var activeStatus = $("#activeStatus").val();
	if (activeStatus == "Pay") {
		tdsLedgerId = $("#tdsPayableLedgerId").val();
		reportType = $("#tdsPayable").text();
	} else if (activeStatus == "Receive") {
		tdsLedgerId = $("#tdsReceivableLedgerId").val();
		reportType = $("#tdsReceivable").text();
	}

	window.open("/account/tds-amount-pdf?fromDate=" + fromDateFilter + "&toDate=" + toDateFilter + "&tdsLedgerId=" + tdsLedgerId + "&activeStatus=" + activeStatus + "&reportType=" + reportType, '_blank');
}

function viewTdsReceivable() {
	$("#tdsReceivable").addClass("tabActive");
	$("#tdsPayable").removeClass("tabActive");
	$("#tdsReceivableSelectionDiv").show();
	$("#tdsPayableSelectionDiv").hide();
	$("#tdsReceivableLedgerId").val("");
	$("#tdsPayableLedgerId").val("");
	$("#activeStatus").val("Receive");
	tabSelectiontext = "receivable";
	viewFilteredDataTDS()
}

function viewTdsPayable() {
	$("#tdsReceivable").removeClass("tabActive");
	$("#tdsPayable").addClass("tabActive");
	$("#tdsReceivableSelectionDiv").hide();
	$("#tdsPayableSelectionDiv").show();
	$("#tdsPayableLedgerId").val("");
	$("#tdsReceivableLedgerId").val("");
	$("#activeStatus").val("Pay");
	tabSelectiontext = "payable";
	viewFilteredDataTDS()
}


function simulateDownload() {
	$(".progress-bar").show();
	let progress = 0;
	const interval = setInterval(() => {
		if (progress >= 100) {
			clearInterval(interval);
			downloadPdfTds();
			downloadBtn.textContent = "Download Complete";
			downloadBtn.classList.add('success');
		} else {
			progress += 25;
			progressFill.style.width = `${progress}%`;
			progressFill.textContent = `${progress}%`;
		}
	}, 500);
}


function PdfDownloadButton() {
	const downloadBtn = document.getElementById('downloadBtn');
	const progressFill = document.getElementById('progressFill');
	console.log("Done-->");
	if (!downloadBtn.classList.contains('success')) {
		simulateDownload();
	}
}



function downloadExcelTds() {
	var activeStatus = $("#activeStatus").val();

	alert(activeStatus);

	if (activeStatus == "Receive") {

		const selectedHeaders = [
			'ID',
			'DATE',
			'PARTICULARS',
			'VENDOR NAME',
			'INVOICE ID',
			'INVOICE VALUE',
			'TDS AMOUNT',
			'TDS RATE(%)',
			'NET AMOUNT VALUE',
			'STATUS',
			'TDS SECTION'
		];

		const fieldsToSum = [
			'INVOICE VALUE',
			'TDS AMOUNT',
			'NET AMOUNT VALUE'
		];

		// Get fromDate and toDate values
		const fromDate = $("#fromDate").val();
		const toDate = $("#toDate").val();

		// Dynamically get company name (orgDiv) from first row of grid
		let companyName = "ORGANIZATION NAME"; // default fallback
		const firstNode = gridOptionsTds.api.getDisplayedRowAtIndex(0);
		if (firstNode && firstNode.data && firstNode.data.orgDiv) {
			companyName = firstNode.data.orgDiv.toUpperCase();
		}

		const currentDate = new Date().toLocaleDateString('en-GB', {
			day: '2-digit',
			month: '2-digit',
			year: 'numeric'
		}).split('/').join('_');

		const fileName = 'TDS RECEIVABLE REPORT_' + currentDate + '.xlsx';
		const rowData = [];
		const fieldMap = {};

		// Map column headers to fields
		gridOptionsTds.columnApi.getAllColumns().forEach((col) => {
			const colDef = col.getColDef();
			if (colDef.headerName && colDef.field) {
				fieldMap[colDef.headerName.toUpperCase()] = colDef.field;
			}
		});

		const totals = {};
		fieldsToSum.forEach(header => totals[header] = 0);

		// Collect row data
		gridOptionsTds.api.forEachNodeAfterFilterAndSort((node) => {
			const row = {};
			selectedHeaders.forEach((header) => {
				const field = fieldMap[header.toUpperCase()];
				let value = node.data?.[field] ?? '';

				// Sum numeric fields
				if (fieldsToSum.includes(header)) {
					const num = parseFloat(value.toString().replace(/,/g, ''));
					totals[header] += isNaN(num) ? 0 : num;
					value = amountFormatter(isNaN(num) ? 0 : num.toFixed(2));
				}

				row[header] = value;
			});
			rowData.push(row);
		});

		// Add total row
		const totalRow = {};
		selectedHeaders.forEach(header => {
			if (fieldsToSum.includes(header)) {
				totalRow[header] = amountFormatter(totals[header].toFixed(2));
			} else if (header === 'ID') {
				totalRow[header] = 'Total';
			} else {
				totalRow[header] = '';
			}
		});
		rowData.push(totalRow);

		// Create top rows
		const headingRow1 = {}, headingRow2 = {}, dividerRow = {}, spacerRow = {};
		selectedHeaders.forEach(header => {
			headingRow1[header] = '';
			headingRow2[header] = '';
			dividerRow[header] = '';
			spacerRow[header] = '';
		});

		headingRow1[selectedHeaders[0]] = companyName;
		headingRow2[selectedHeaders[0]] = `TDS RECEIVABLE REPORT From ${fromDate} To ${toDate}`;
		dividerRow[selectedHeaders[0]] = '―'.repeat(80);

		rowData.unshift(spacerRow);
		rowData.unshift(dividerRow);
		rowData.unshift(headingRow2);
		rowData.unshift(headingRow1);

		// Convert to sheet
		const ws = XLSX.utils.json_to_sheet(rowData, {
			header: selectedHeaders,
			skipHeader: true
		});

		// Merge and style heading rows
		ws['!merges'] = [
			{ s: { r: 0, c: 0 }, e: { r: 0, c: selectedHeaders.length - 1 } },
			{ s: { r: 1, c: 0 }, e: { r: 1, c: selectedHeaders.length - 1 } },
			{ s: { r: 2, c: 0 }, e: { r: 2, c: selectedHeaders.length - 1 } }
		];

		ws['A1'] = {
			v: companyName,
			t: 's',
			s: {
				font: { bold: true, sz: 16 },
				alignment: { horizontal: 'center', vertical: 'center' },
				fill: { fgColor: { rgb: 'DCE6F1' } }
			}
		};

		ws['A2'] = {
			v: `TDS RECEIVABLE REPORT From ${fromDate} To ${toDate}`,
			t: 's',
			s: {
				font: { bold: true, sz: 14 },
				alignment: { horizontal: 'center', vertical: 'center' },
				fill: { fgColor: { rgb: 'E2EFDA' } }
			}
		};

		ws['A3'] = {
			v: '―'.repeat(80),
			t: 's',
			s: {
				alignment: { horizontal: 'center', vertical: 'center' },
				font: { color: { rgb: "666666" } }
			}
		};

		// Style headers
		selectedHeaders.forEach((header, index) => {
			const cellAddress = XLSX.utils.encode_cell({ c: index, r: 3 });
			if (!ws[cellAddress]) return;

			ws[cellAddress].v = header.toUpperCase();
			ws[cellAddress].s = {
				fill: { fgColor: { rgb: "5784c4" } },
				font: { bold: true, color: { rgb: "FFFFFF" } },
				alignment: { horizontal: "center", vertical: "center" },
				border: {
					top: { style: "thin", color: { rgb: "000000" } },
					bottom: { style: "thin", color: { rgb: "000000" } },
					left: { style: "thin", color: { rgb: "000000" } },
					right: { style: "thin", color: { rgb: "000000" } }
				}
			};



			const colLetter = XLSX.utils.encode_col(index);
			const colRange = XLSX.utils.decode_range(ws['!ref']);
			for (let r = 4; r <= colRange.e.r; r++) {
				const cellRef = `${colLetter}${r + 1}`;
				if (!ws[cellRef]) continue;

				if (fieldsToSum.includes(header) || header === 'TDS RATE(%)') {
					ws[cellRef].s = {
						alignment: { horizontal: "right" }
					};
				}
			}
		});


		// Set column widths
		ws['!cols'] = selectedHeaders.map(header => {
			switch (header) {
				case 'ID': return { wch: 10 };
				case 'DATE': return { wch: 15 };
				case 'PARTICULARS': return { wch: 30 };
				case 'VENDOR NAME': return { wch: 35 };
				case 'INVOICE ID':
				case 'TDS AMOUNT':
				case 'STATUS':
					return { wch: 18 };
				default:
					return { wch: 20 };
			}
		});

		// Export workbook
		const wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, 'TDS Receivable Report');
		XLSX.writeFile(wb, fileName);

	} else {
		const selectedHeaders = [
			'ID',
			'DATE',
			'PARTICULARS',
			'VENDOR NAME',
			'INVOICE ID',
			'INVOICE VALUE',
			'TDS AMOUNT',
			'TDS RATE(%)',
			'NET AMOUNT VALUE',
			'STATUS'
		];

		const fieldsToSum = [
			'INVOICE VALUE',
			'TDS AMOUNT',
			'NET AMOUNT VALUE'
		];

		// Get fromDate and toDate values
		const fromDate = $("#fromDate").val();
		const toDate = $("#toDate").val();

		// Dynamically get company name (orgDiv) from first row of grid
		let companyName = "ORGANIZATION NAME"; // default fallback
		const firstNode = gridOptionsTds.api.getDisplayedRowAtIndex(0);
		if (firstNode && firstNode.data && firstNode.data.orgDiv) {
			companyName = firstNode.data.orgDiv.toUpperCase();
		}

		const currentDate = new Date().toLocaleDateString('en-GB', {
			day: '2-digit',
			month: '2-digit',
			year: 'numeric'
		}).split('/').join('_');

		const fileName = 'TDS PAYABLE REPORT_' + currentDate + '.xlsx';
		const rowData = [];
		const fieldMap = {};

		// Map column headers to fields
		gridOptionsTds.columnApi.getAllColumns().forEach((col) => {
			const colDef = col.getColDef();
			if (colDef.headerName && colDef.field) {
				fieldMap[colDef.headerName.toUpperCase()] = colDef.field;
			}
		});

		const totals = {};
		fieldsToSum.forEach(header => totals[header] = 0);

		// Collect row data
		gridOptionsTds.api.forEachNodeAfterFilterAndSort((node) => {
			const row = {};
			selectedHeaders.forEach((header) => {
				const field = fieldMap[header.toUpperCase()];
				let value = node.data?.[field] ?? '';

				// Sum numeric fields
				if (fieldsToSum.includes(header)) {
					const num = parseFloat(value.toString().replace(/,/g, ''));
					totals[header] += isNaN(num) ? 0 : num;
					value = amountFormatter(isNaN(num) ? 0 : num.toFixed(2));
				}

				row[header] = value;
			});
			rowData.push(row);
		});

		// Add total row
		const totalRow = {};
		selectedHeaders.forEach(header => {
			if (fieldsToSum.includes(header)) {
				totalRow[header] = amountFormatter(totals[header].toFixed(2));
			} else if (header === 'ID') {
				totalRow[header] = 'Total';
			} else {
				totalRow[header] = '';
			}
		});
		rowData.push(totalRow);

		// Create top rows
		const headingRow1 = {}, headingRow2 = {}, dividerRow = {}, spacerRow = {};
		selectedHeaders.forEach(header => {
			headingRow1[header] = '';
			headingRow2[header] = '';
			dividerRow[header] = '';
			spacerRow[header] = '';
		});

		headingRow1[selectedHeaders[0]] = companyName;
		headingRow2[selectedHeaders[0]] = `TDS PAYABLE REPORT From ${fromDate} To ${toDate}`;
		dividerRow[selectedHeaders[0]] = '―'.repeat(80);

		rowData.unshift(spacerRow);
		rowData.unshift(dividerRow);
		rowData.unshift(headingRow2);
		rowData.unshift(headingRow1);

		// Convert to sheet
		const ws = XLSX.utils.json_to_sheet(rowData, {
			header: selectedHeaders,
			skipHeader: true
		});

		// Merge and style heading rows
		ws['!merges'] = [
			{ s: { r: 0, c: 0 }, e: { r: 0, c: selectedHeaders.length - 1 } },
			{ s: { r: 1, c: 0 }, e: { r: 1, c: selectedHeaders.length - 1 } },
			{ s: { r: 2, c: 0 }, e: { r: 2, c: selectedHeaders.length - 1 } }
		];

		ws['A1'] = {
			v: companyName,
			t: 's',
			s: {
				font: { bold: true, sz: 16 },
				alignment: { horizontal: 'center', vertical: 'center' },
				fill: { fgColor: { rgb: 'DCE6F1' } }
			}
		};

		ws['A2'] = {
			v: `TDS PAYABLE REPORT From ${fromDate} To ${toDate}`,
			t: 's',
			s: {
				font: { bold: true, sz: 14 },
				alignment: { horizontal: 'center', vertical: 'center' },
				fill: { fgColor: { rgb: 'E2EFDA' } }
			}
		};

		ws['A3'] = {
			v: '―'.repeat(80),
			t: 's',
			s: {
				alignment: { horizontal: 'center', vertical: 'center' },
				font: { color: { rgb: "666666" } }
			}
		};

		// Style headers
		selectedHeaders.forEach((header, index) => {
			const cellAddress = XLSX.utils.encode_cell({ c: index, r: 3 });
			if (!ws[cellAddress]) return;

			ws[cellAddress].v = header.toUpperCase();
			ws[cellAddress].s = {
				fill: { fgColor: { rgb: "5784c4" } },
				font: { bold: true, color: { rgb: "FFFFFF" } },
				alignment: { horizontal: "center", vertical: "center" },
				border: {
					top: { style: "thin", color: { rgb: "000000" } },
					bottom: { style: "thin", color: { rgb: "000000" } },
					left: { style: "thin", color: { rgb: "000000" } },
					right: { style: "thin", color: { rgb: "000000" } }
				}
			};



			const colLetter = XLSX.utils.encode_col(index);
			const colRange = XLSX.utils.decode_range(ws['!ref']);
			for (let r = 4; r <= colRange.e.r; r++) {
				const cellRef = `${colLetter}${r + 1}`;
				if (!ws[cellRef]) continue;

				if (fieldsToSum.includes(header) || header === 'TDS RATE(%)') {
					ws[cellRef].s = {
						alignment: { horizontal: "right" }
					};
				}
			}
		});


		// Set column widths
		ws['!cols'] = selectedHeaders.map(header => {
			switch (header) {
				case 'ID': return { wch: 20 };
				case 'DATE': return { wch: 15 };
				case 'PARTICULARS': return { wch: 40 };
				case 'VENDOR NAME': return { wch: 35 };
				case 'INVOICE ID':
				case 'TDS AMOUNT':
				case 'STATUS':
					return { wch: 18 };
				default:
					return { wch: 20 };
			}
		});

		// Export workbook
		const wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, 'TDS Payable Report');
		XLSX.writeFile(wb, fileName);
	}
}
