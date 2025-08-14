$(function() {

	var gridDivAcc = document.querySelector('#myGridAccountStatement');
	new agGrid.Grid(gridDivAcc, gridOptionsAccountStatement);

	agGrid.simpleHttpRequest({
		url: "manage-ledger-view"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);

		var rowData = [];
		gridOptionsAccountStatement.api.setRowData(rowData);

		gridOptionsAccountStatement.api.setRowData(data);

		if (data && data.length > 0) {
			gridOptionsAccountStatement.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});
		}

	});

	DateAndYear();

	var dateFormat = localStorage.getItem("dateFormat") || 'Y-m-d'; // Add default format

	// Initialize From Date picker
	$("#fromDateAccountStatement").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		onSelectDate: function(selectedDate) {
			// Set minDate on To Date picker when From Date is selected
			$("#toDateAccountStatement").datetimepicker({
				minDate: selectedDate
			});
		}
	});

	// Initialize To Date picker
	$("#toDateAccountStatement").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false
	});

	// Initialize calendar icon pickers
	$("#toDateCalendarAccountStatement").click(function() {
		$("#fromDateAccountStatement").datetimepicker("show");
	});

	$("#toDateCalendar2AccountStatement").click(function() {
		$("#toDateAccountStatement").datetimepicker("show");
	});

	// Optional: Sync dates if needed
	$('#fromDateAccountStatement').on('change', function() {
		var fromDate = $(this).val();
		var toDate = $('#toDateAccountStatement').val();

		if (!toDate || new Date(fromDate) > new Date(toDate)) {
			$('#toDateAccountStatement').val(fromDate);
		}
	});
	var currentDate = new Date();
	if (currentDate.getMonth() >= 3) {
		var startYear = currentDate.getFullYear();
		var endYear = startYear + 1;
	} else {

		var startYear = currentDate.getFullYear() - 1;
		var endYear = startYear + 1;
	}
	var startFinancialYear = new Date(startYear, 3, 1);
	var endFinancialYear = new Date(endYear, 2, 31);
	var startDateFormatted = formatDate(startFinancialYear);
	var endDateFormatted = formatDate(endFinancialYear);
	console.log("Financial Year: " + startDateFormatted + " to "
		+ endDateFormatted);

	$("#statementYear")
		.text(startDateFormatted + " To " + endDateFormatted);

});

function formatDate(date) {
	var months = ['January', 'February', 'March', 'April', 'May', 'June',
		'July', 'August', 'September', 'October', 'November',
		'December'];
	var day = date.getDate();
	var month = months[date.getMonth()];
	var year = date.getFullYear();
	var dayFormatted = (day < 10) ? '0' + day : day;

	return dayFormatted + ' ' + month + ' ' + year;
}

function DateAndYear() {

	var today = new Date();

	// Format today's date (toDate)
	var todate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();

	// Get the first date of the current month
	var firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
	var fromDate = ('0' + firstDay.getDate()).slice(-2) + '-'
		+ ('0' + (firstDay.getMonth() + 1)).slice(-2) + '-'
		+ firstDay.getFullYear();

	// Set the values to the input fields
	$("#toDateAccountStatement").val(todate);
	$("#fromDateAccountStatement").val(fromDate);


	var fiscalyear = "";
	var today = new Date();
	if ((today.getMonth() + 1) <= 3) {
		fiscalyear = (today.getFullYear() - 1) + "-" + today.getFullYear()

	} else {
		fiscalyear = today.getFullYear() + "-" + (today.getFullYear() + 1)

	}
	$("#orderStatusFilter").val(fiscalyear);

	$("#thisYear").text(today.getFullYear());
	$("#nextYear").text(today.getFullYear() + 1);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

function amountFormatter(value) {
	if (value !== null && value !== undefined) {
		var parts = value.toString().split('.');
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		var formattedIntegerPart = new Intl.NumberFormat('en-IN')
			.format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}

function currenyAmountFormat(value) {
	if (value !== null && value !== undefined) {
		var valueStr = value.toString().replace(/[^0-9.-]+/g, '');

		var parts = valueStr.split('.');
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		var formattedIntegerPart = new Intl.NumberFormat('en-IN')
			.format(integerPart);

		var currencySymbol = value.toString().match(/[^0-9.-]+/g) ? value
			.toString().match(/[^0-9.-]+/g)[0] : '';
		return currencySymbol + formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}

const columnDefsAccountStatement = [
	{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 8,
		maxWidth: 30,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Ledger Id',
		field: "leadgerId",
		width: 150,

	},

	{
		headerName: "Ledger Name",
		field: "ledgername",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Account Group",
		field: "groupId",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		}
	},
	{
		headerName: "Opening Balance",
		field: "openinbalanceDate",
		width: 200,
		cellStyle: {
			textAlign: 'left'
		},
		cellRenderer: function(params) {
			if (params.data.openinbalanceDate > 0) {
				return params.data.openinbalanceDate + " DR"
			}
			else if (params.data.openinbalanceDate == "0.00") {
				return params.data.openinbalanceDate
			}

			else if (params.data.openinbalanceDate < 0) {
				var data = params.data.openinbalanceDate.split("-");
				console.log("data is-->", data);
				return data[1] + " CR"
			}
		}
	},
	{
		headerName: "View Account Statement",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return `
				        <a href="javascript:void(0)" 
				           onclick="openAccountStatementDiv('${params.node.id}', '${params.data.leadgerId}', '${params.data.ledgername}')" title="View Transaction History">
				          <i class="fas fa-eye"></i>
				        </a>
				    `;
		}
	}];

const gridOptionsAccountStatement = {
	columnDefs: columnDefsAccountStatement,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10,
		flex: 1
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.leadgerId;
	}
};


function openAccountStatementDiv(rowId, LedgerId, LedgerName) {
	const rowNode = gridOptionsAccountStatement.api.getRowNode(rowId);
	if (rowNode) {
		rowNode.setSelected(true);
	}

	$("#accountStatementDetailsDiv").removeClass("d-none");
	$("#accountStatementLeftGridDiv").addClass("d-none");
}


function accountStatementBackBtn() {
	$("#accountStatementDetailsDiv").addClass("d-none");
	$("#accountStatementLeftGridDiv").removeClass("d-none");

}
function rowSelect() {
	var selectedRows = gridOptionsAccountStatement.api.getSelectedRows();
	var bankId = selectedRows[0].leadgerId;
	var bankName = btoa(selectedRows[0].ledgername);
	var rowCount = 0;

	var selectedNodes = gridOptionsAccountStatement.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		viewAccountStateDetails(bankId, bankName);
	}
	else {
		console.log("Not Selected");
	}
}

function DateToSting(date) {

	var dateString = date;

	var parts = dateString.split("-");
	var day = parts[0];
	var month = parts[1];
	var year = parts[2];

	var monthNames = ["January", "February", "March", "April", "May",
		"June", "July", "August", "September", "October", "November",
		"December"];
	var monthName = monthNames[parseInt(month, 10) - 1];

	var formattedDate = day + " " + monthName + " " + year;
	return formattedDate;

}

function viewAccountStateDetails(id, name) {

	$('#ledgerId').val(id);
	$('#ledgerName').text(atob(name));
	$("#AccLedgerName").text(atob(name));

	$("#searchRowDiv").hide();
	$("#totalLedger").hide();
	$("#voucherNumber").text(id);

	//call filter function according to from date and to date
	/* viewSearchData();
	DateAndYear(); */

	var currentFromDate = DateToSting($("#fromDateAccountStatement").val());
	var currentTodate = DateToSting($("#toDateAccountStatement").val());

	$("#thisYear").text(currentFromDate);
	$("#nextYear").text(currentTodate);

	$
		.ajax({
			type: "GET",
			url: "account-statement-mothlySummary?id=" + id,
			async: false,
			success: function(response) {
				if (response.code == "Success") {

					$("#montlyReport").empty();
					$("#monthlyDetails").empty();
					for (var i = 0; i < response.body.length; i++) {

						var debitAmount = parseFloat(response.body[i].debitAmount);
						var creditAmount = parseFloat(response.body[i].creditAmount);
						var finalBalance = 0.00;

						console.log("debitAmount-->", debitAmount);
						console.log("creditAmount-->", creditAmount);

						if (debitAmount > 0) {
							finalBalance = finalBalance - debitAmount;

							console.log("finalBal in debit-->",
								finalBalance);

						}
						if (creditAmount > 0) {
							finalBalance = finalBalance + creditAmount;
							console.log("finalBal in creditAmount-->",
								finalBalance);
						}

						var monthName = '';
						var monthNum = response.body[i].voucherDate;
						if (monthNum == '6') {
							monthName = "June";
						} else if (monthNum == '7') {
							monthName = "July";
						} else if (monthNum == '8') {
							monthName = "August";
						} else if (monthNum == '9') {
							monthName = "September";
						} else if (monthNum == '10') {
							monthName = "October";
						} else if (monthNum == '11') {
							monthName = "Novemeber";
						} else if (monthNum == '12') {
							monthName = "December";
						} else if (monthNum == '1') {
							monthName = "January";
						} else if (monthNum == '2') {
							monthName = "February";
						} else if (monthNum == '3') {
							monthName = "March";
						} else if (monthNum == '4') {
							monthName = "April";
						} else {
							monthName = "May";
						}

						var rows = '<tr align="left" valign="top">'

							+ '<td><a class="monthNameLink" onclick="viewDetails('
							+ monthNum
							+ ')" href="javascript:void(0)">'
							+ monthName
							+ '</a></td>'
							+ '<td class="alnright" width="15%">₹'
							+ amountFormatter(response.body[i].debitAmount)
							+ '</td>'
							+ '<td class="alnright" width="15%">₹'
							+ amountFormatter(response.body[i].creditAmount)
							+ '</td>'
							+ '<td class="alnright" width="20%">₹'
							+ amountFormatter(finalBalance
								.toFixed(2)) + '</td>'

						$("#montlyReport").append(rows);
					}

					$(".ledgertable").show();
					$("#agGridTable").hide();
					$("#demo").hide();
				}

			}
		})

}

var inputMonth = "";
function viewDetails(id) {
	inputMonth = id;
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();
	$("#toDate2").val(todate);
	$("#fromDate2").val(fromDate);

	var fiscalyear = "";
	var today = new Date();
	if ((today.getMonth() + 1) <= 3) {
		fiscalyear = (today.getFullYear() - 1) + "-" + today.getFullYear()

	} else {
		fiscalyear = today.getFullYear() + "-" + (today.getFullYear() + 1)

	}
	$("#orderStatusFilter2").val(fiscalyear);
	var ledgerId = $("#ledgerId").val();

	$
		.ajax({
			type: "GET",
			url: "account-statement-mothlyDetails?month=" + id
				+ "&ledgerId=" + ledgerId,
			async: false,
			success: function(response) {
				if (response.code == "Success") {

					console.log("length-->", response.body.length);

					if (response.body.length == 0
						|| response.body.length == null) {
						var emptybdy = '<tr align="left" valign="top">'
							+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
							+ '</tr>';
						$("#monthlyDetails").append(emptybdy);
						$("#downAccountStatementPdfId").addClass("fadeBtn");
						$("#downloadExcelId").addClass("fadeBtn");
					} else {
						$("#downAccountStatementPdfId").removeClass("fadeBtn");
						$("#downloadExcelId").removeClass("fadeBtn");
						for (var i = 0; i < response.body.length; i++) {

							var debBal = '';
							var creBal = '';
							if (response.body[i].debitAmount == '0') {
								debBal = '';
							} else {
								debBal = ''
									+ response.body[i].debitAmount
									+ '';
								debBal = '₹'.concat(debBal);
							}
							if (response.body[i].creditAmount == '0') {
								creBal = '';
							} else {
								creBal = ''
									+ response.body[i].creditAmount
									+ '';
								creBal = '₹'.concat(creBal);
							}

							var rows = '<tr align="left" valign="top">'
								+ '<td>'
								+ response.body[i].voucherDate
								+ '</td>' + '<td>'
								+ response.body[i].ledgerName
								+ '</td>' + '<td>'
								+ response.body[i].voucherType
								+ '</td>' + '<td>'
								+ response.body[i].voucherId
								+ '</td>' + '<td class="alnright">'
								+ currenyAmountFormat(debBal)
								+ '</td>' + '<td class="alnright">'
								+ currenyAmountFormat(creBal)
								+ '</td>';
							+'</tr>';

							$("#monthlyDetails").append(rows);
						}
						var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
						var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

						var totalbdy = '<tr align="left" valign="top">'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td style="text-align: right;"><strong>Total:</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
							+ '</tr>';

						$("#monthlyDetails").append(totalbdy);
					}

				}
			}
		})

	$(".ledgertable").hide();
	$("#agGridTable").hide();
	$("#demo").show();
}
function backPage() {
	$(".ledgertable").hide();
	$("#agGridTable").show();
	$("#demo").hide();
	$("#searchRowDiv").show();
	$("#totalLedger").show();
	$("#monthlyDetails").empty();
	$("#fromDateAccountStatement").val('');
	$("#toDateAccountStatement").val('');
}
function backPage1() {
	$(".ledgertable").show();
	$("#agGridTable").hide();
	$("#demo").hide();
}

function onQuickFilterChanged() {
	$(".ti-search srchicon").hide();
	gridOptionsAccountStatement.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var length = gridOptionsAccountStatement.api.getDisplayedRowCount();
	$("#totalLedger").find('span').html(length);
}

function viewSearchData() {
	var ledgerid = $("#ledgerId").val();
	var ledgerName = $("#ledgerName").text();
	var fromDate = $("#fromDateAccountStatement").val();
	var toDate = $("#toDateAccountStatement").val();

	var currentFromDate = DateToSting(fromDate);
	var currentTodate = DateToSting(toDate);

	console.log("from-->", fromDate);
	console.log("todate -->", toDate);

	$("#thisYear").text(currentFromDate);
	$("#nextYear").text(currentTodate);

	$
		.ajax({
			type: "GET",
			url: "account-statement-FilterData?ledgerId=" + ledgerid
				+ "&fromDate=" + fromDate + "&toDate=" + toDate,
			async: false,
			success: function(response) {
				if (response.code == "Success") {

					$("#monthlyDetails").empty();

					if (response.body.length == 0
						|| response.body.length == null) {
						var emptybdy = '<tr align="left" valign="top">'
							+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
							+ '</tr>';
						$("#monthlyDetails").append(emptybdy);
						$("#downAccountStatementPdfId").addClass("fadeBtn");
						$("#downloadExcelId").addClass("fadeBtn");

					} else {
						$("#downAccountStatementPdfId").removeClass("fadeBtn");
						$("#downloadExcelId").removeClass("fadeBtn");
						for (var i = 0; i < response.body.length; i++) {

							var debBal = '';
							var creBal = '';
							if (response.body[i].debitAmount == '0') {
								debBal = '';
							} else {
								debBal = ''
									+ response.body[i].debitAmount
									+ '';
								debBal = '₹'.concat(debBal);
							}
							if (response.body[i].creditAmount == '0') {
								creBal = '';
							} else {
								creBal = ''
									+ response.body[i].creditAmount
									+ '';
								creBal = '₹'.concat(creBal);
							}

							var rows = '<tr align="left" valign="top">'
								+ '<td>'
								+ response.body[i].voucherDate
								+ '</td>' + '<td>'
								+ response.body[i].ledgerName
								+ '</td>' + '<td>'
								+ response.body[i].voucherType
								+ '</td>' + '<td>'
								+ response.body[i].voucherId
								+ '</td>' + '<td class="alnright">'
								+ currenyAmountFormat(debBal)
								+ '</td>' + '<td class="alnright">'
								+ currenyAmountFormat(creBal)
								+ '</td>';
							+'</tr>';

							$("#monthlyDetails").append(rows);
						}
						var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
						var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

						var totalbdy = '<tr align="left" valign="top">'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td style="text-align: right;"><strong>Total:</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
							+ '</tr>';

						$("#monthlyDetails").append(totalbdy);

					}
				}

			}
		})

}

function downloadExcelData() {
	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var todate = toDateString.toString();

	var ledgerName = "";

	var inputFromDate = $("#fromDateAccountStatement").val();
	var inputToDate = $("#toDateAccountStatement").val();

	if (inputFromDate && inputToDate) {

		ledgerName = $("#ledgerName").text() + "( " + inputFromDate + " To " + inputToDate + " )" + ".xlsx";
	}
	else {
		ledgerName = $("#ledgerName").text() + "( " + fromDate + " To " + todate + " )" + ".xlsx";

	}


	var tableData = [];

	var headerRow = [];
	$(".AccountstatementTable thead th").each(function() {
		headerRow.push($(this).text());
	});
	tableData.push(headerRow);

	$(".AccountstatementTable tbody tr").each(function() {
		var rowData = [];
		$(this).find('td').each(function() {
			rowData.push($(this).text());
		});
		tableData.push(rowData);
	});

	var ws = XLSX.utils.aoa_to_sheet(tableData);

	var wb = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(wb, ws, "Sheet1");

	XLSX.writeFile(wb, ledgerName);
}

function downAccountStatementPdf() {
	var ledgerid = $("#ledgerId").val();
	var ledgerName = $("#ledgerName").text();
	var fromDate = $("#fromDateAccountStatement").val();
	var toDate = $("#toDateAccountStatement").val();
	var month = inputMonth;

	window.open("/account/account-statement-Pdf?ledgerid=" + ledgerid + "&ledgerName=" + ledgerName + "&fromDate=" + fromDate + "&toDate=" + toDate + "&month=" + month, '_blank');

}

function tabBackBtn() {
	$("#monthlyShowingTable").show();
	$("#demo").hide();
	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();
	$("#toDateAccountStatement").val(todate);
	$("#fromDateAccountStatement").val(fromDate);
	$("#monthlyDetails").empty();
}

function onQuickFilterChangedAccStatement() {
	gridOptionsAccountStatement.api
		.setQuickFilter(document.getElementById('quickFilterAccountStatement').value);
	var rowCount = gridOptionsAccountStatement.api.getDisplayedRowCount();
	$('#totalReq').find('span').html(rowCount);
}

function SearchUserInputAccStatement(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChangedAccStatement();
	}
}

function getMostClosestRowAccountStatement() {
	let searchValue = document.getElementById('quickFilterBannk').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptionsAccountStatement.api.getModel().getRowCount();

	gridOptionsAccountStatement.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptionsAccountStatement.api.ensureIndexVisible(node.rowIndex);
		}
	});
}

function resetBtnAccStatement() {
	$("#quickFilterAccountStatement").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	onQuickFilterChangedAccStatement();
	setTimeout(() => {
		gridOptionsAccountStatement.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptionsAccountStatement.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}