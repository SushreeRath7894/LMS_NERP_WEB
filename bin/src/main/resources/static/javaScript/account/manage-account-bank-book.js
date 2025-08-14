$(function() {

	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendarbankBook").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#bankbookfromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#toDateCalendarbankBook").val($(this).val());
	})

	$("#toDateCalendar2bankBook").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#bankbooktoDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar2bankBook").val($(this).val());
	})

	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	agGrid.simpleHttpRequest({
		url: "bank-report-view"
	}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);
		var rowData = [];
		gridOptions.api.setRowData(rowData);

		gridOptions.api.setRowData(data);

		if (data && data.length > 0) {
			gridOptions.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});
		}

	});
});



//search bar

function onQuickFilterChangedBank() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilterBannk').value);
	var rowCount = gridOptions.api.getDisplayedRowCount();
	$('#totalReq').find('span').html(rowCount);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilterBannk').val() == null || $('#quickFilterBannk').val() == "") {
		id.style.display = "none";
	}
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



const columnDefs = [
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
		maxWidth: 120,
		hide: true
		//hide:true,

	},

	{
		headerName: "Bank Name",
		field: "ledgername",
		width: 200,
	},
	{
		headerName: "Bank Account Number",
		field: "groupId",
		width: 200,
		
	},
	{
		headerName: "Bank IFSC Code",
		field: "lname",
		width: 200
	},
	{
		headerName: "Account Holder name",
		field: "address",
		width: 200,
		
	},
	{
		headerName: "Group",
		field: "groupId",
		width: 200,
		hide: true
	}, {
		headerName: "PAN",
		field: "panitn",
		width: 200,
		hide: true
	}, {
		headerName: "View Bank Transactions",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			return `
		        <a href="javascript:void(0)" 
		           onclick="openTransDiv('${params.node.id}', '${params.data.leadgerId}', '${params.data.ledgername}')" title="View Transaction History">
		          <i class="fas fa-eye"></i>
		        </a>
		    `;
		}
	},
	, {
		headerName: "Opening Bakance",
		field: "openinbalanceDate",
		width: 200,
		cellRenderer: function(params) {
			return amountFormatter(params.value);
		}
	}
];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		height: 10,
		flex: 1,
		minWidth: 100,
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.leadgerId;
	}
};

function openTransDiv(rowId, id, name) {

	const rowNode = gridOptions.api.getRowNode(rowId);
	if (rowNode) {
		rowNode.setSelected(true);
	}
	$("#bankDetailsGridDiv").addClass("d-none");
	$("#bankTransDetailsDiv").removeClass("d-none");
	$("#bankbookName").text(name);

}

function bankTransDivBack() {
	$("#bankDetailsGridDiv").removeClass("d-none");
	$("#bankTransDetailsDiv").addClass("d-none");
	$("#bankbookName").text('');
}


function rowSelect() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var bankId = selectedRows[0].leadgerId;
	var bankName = btoa(selectedRows[0].ledgername);
	var rowCount = 0;

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		viewBankBookDetails(bankId, bankName);
	}
	else {
		console.log("Not Selected");
	}
}
function viewBankBookDetails(id, ledgerName) {

	$("#searchRowDiv").hide();
	$("#bankBookLedgerid").val(id);
	$("#voucherNumber").text(id);
	var voucherType = '';
	$("#openinbalanceDate").html("");

	$("#openBal").val("");
	$("#finalBal").val("");

	var today = new Date();
	var dateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = dateString.toString();
	var todate = dateString.toString();

	$("#bankbooktoDate").val(todate);
	$("#bankbookfromDate").val(fromDate);

	$.ajax({
		type: "GET",
		url: "bank-report-filterdata?id=" + id + "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + todate,
		async: false,
		success: function(response) {
			if (response.code == "Success") {

				$("#tblBody").empty();
				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#tblBody").append(emptybdy);
					$("#downloadPdfId").addClass("fadeBtn");
					$("#downloadExcelId").addClass("fadeBtn");
				} else {
					$("#downloadPdfId").removeClass("fadeBtn");
					$("#downloadExcelId").removeClass("fadeBtn");
					var openBalance;
					if (response.body[0].openingBalance > 0 || response.body[0].openingBalance != null && response.body[0].openingBalance != 0) {

						$("#openinbalanceDate").html("Opening Balance : " + amountFormatter(response.body[0].openingBalance));
						$("#openBal").val(response.body[0].openingBalance);
						openBalance = response.body[0].openingBalance;
					}
					else if (response.body[0].openingBalance == 0) {

						if (response.body[0].intialbalance == null) {
							openBalance = 0;
							$("#openinbalanceDate").html("Opening Balance : " + "0.00");
						}
						else if (response.body[0].intialbalance != null) {
							openBalance = response.body[0].intialbalance;
							$("#openBal").val(response.body[0].intialbalance);
							$("#openinbalanceDate").html("Opening Balance : " + (amountFormatter(response.body[0].intialbalance)));

						}
					}

					var finalBalance = parseFloat(openBalance);
					for (var i = 0; i < response.body.length; i++) {

						var debitAmount = parseFloat(response.body[i].debitAmount);
						var creditAmount = parseFloat(response.body[i].creditAmount);

						if (debitAmount > 0 && debitAmount != null) {
							finalBalance = finalBalance - debitAmount;

						}

						else if (creditAmount > 0 && creditAmount != null) {
							finalBalance = finalBalance + creditAmount;
						}


						var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #f3f3f3;';
						var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
							+ '<td style="text-align: left;">' + response.body[i].voucherDate + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].ledgerName + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].voucherType + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].debitAmount) + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].creditAmount) + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(finalBalance.toFixed(2)) + '</td>'
							+ '</tr>';
						$("#tblBody").append(bdy);
					}

					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);
					$("#finalBal").val(finalBalance);

					var totalbdy = '<tr align="center" valign="top">'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: left;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(finalBalance.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#tblBody").append(totalbdy);
				}


				$(".ledgertable").show();
				$("#agGridTable").hide();


			}
			//$("#ledgerVcrName").text(ledgerName);
		}
	})
}
function backPage() {
	$(".ledgertable").hide();
	$("#searchRowDiv").show();
	$("#agGridTable").show();
	$("#tblBody").empty();
	$("#bankBookVoucherType").val("");
	$("#bankbookfromDate").val("");
	$("#bankbooktoDate").val("");
}

function getDataVoucherType() {
	var voucherType = $("#bankBookVoucherType").val();
	var ledgerId = $("#bankBookLedgerid").val();
	$("#bankbooktoDate").val("");
	$("#bankbookfromDate").val("");
	$.ajax({
		type: "GET",
		url: "ledger-voucher-details-wrtVoucherType?id=" + ledgerId + "&voucherType=" + voucherType,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				$("#tblBody").empty();
				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#tblBody").append(emptybdy);
				} else {
					for (var i = 0; i < response.body.length; i++) {
						var bdy = '<tr align="left" valign="top">'
							+ '<td style="text-align: left;">' + response.body[i].voucherDate + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].ledgerName + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].voucherType + '</td>'
							+ '<td style="text-align: left;">' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].debitAmount) + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].creditAmount) + '</td>'
							+ '</tr>';
						$("#tblBody").append(bdy);
					}
					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

					var totalbdy = '<tr align="left" valign="top">'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
						+ '<td colspan="1" style="text-align: left;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#tblBody").append(totalbdy);

				}
				$(".ledgertable").show();
				$("#agGridTable").hide();
			}
			//$("#ledgerVcrName").text(atob(ledgerName));
		}
	})
}

function viewBankBookFilteredData() {
	var voucherType = $("#bankBookVoucherType").val();
	var id = $("#bankBookLedgerid").val();
	var fromDate = $("#bankbookfromDate").val();
	var todate = $("#bankbooktoDate").val();

	var validation = true;
	if (fromDate == null || fromDate == "") {
		swal("Please Provide From Date");
		validation = false;
	} else if (todate == null || todate == "") {
		swal("Please Provide To Date");
		validation = false;
	}
	$("#openinbalanceDate").html("");
	$("#openBal").val("");
	$("finalBal").val("");
	if (validation) {
		$.ajax({
			type: "GET",
			url: "bank-report-filterdata?id=" + id + "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + todate,

			//url : "bank-report-filterdata?id=" + ledgerId + "&voucherType=" + voucherType + "&fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
			async: false,
			success: function(response) {
				if (response.code == "Success") {
					$("#tblBody").empty();
					if (response.body.length == 0 || response.body.length == null) {
						var emptybdy = '<tr align="left" valign="top">'
							+ '<td colspan="7" style="text-align: center;">NO DATA FOUND</td>'
							+ '</tr>';
						$("#tblBody").append(emptybdy);
						$("#downloadPdfId").addClass("fadeBtn");
						$("#downloadExcelId").addClass("fadeBtn");

						console.log("Yeah..")
					} else {
						$("#downloadPdfId").removeClass("fadeBtn");
						$("#downloadExcelId").removeClass("fadeBtn");
						var openBalance;
						if (response.body[0].openingBalance > 0 || response.body[0].openingBalance != null && response.body[0].openingBalance != 0) {

							$("#openinbalanceDate").html("Opening Balance : " + amountFormatter(response.body[0].openingBalance));
							$("#openBal").val(response.body[0].openingBalance);
							openBalance = response.body[0].openingBalance;
						}
						else if (response.body[0].openingBalance == 0) {
							openBalance = response.body[0].intialbalance;
							$("#openBal").val(response.body[0].intialbalance);
							$("#openinbalanceDate").html("Opening Balance : " + amountFormatter(response.body[0].intialbalance));
						}




						var finalBalance = parseFloat(openBalance);

						console.log("bodyLength--> ", response.body.length);
						for (var i = 0; i < response.body.length; i++) {

							var debitAmount = parseFloat(response.body[i].debitAmount);
							var creditAmount = parseFloat(response.body[i].creditAmount);

							if (debitAmount > 0 && debitAmount != null) {
								finalBalance = finalBalance - debitAmount;
							} else if (creditAmount > 0 && creditAmount != null) {
								finalBalance = finalBalance + creditAmount;
							}

							// Set row background color based on even/odd index
							var rowColor = i % 2 === 0 ? '#ffffff;' : '#f3f3f3;';

							var bdy = '<tr align="left" valign="top" style="background-color: ' + rowColor + ';">'
								+ '<td style="text-align: left;">' + response.body[i].voucherDate + '</td>'
								+ '<td style="text-align: left;">' + response.body[i].ledgerName + '</td>'
								+ '<td style="text-align: left;">' + response.body[i].voucherType + '</td>'
								+ '<td style="text-align: left;">' + response.body[i].voucherId + '</td>'
								+ '<td class="alnright">₹' + amountFormatter(response.body[i].debitAmount) + '</td>'
								+ '<td class="alnright">₹' + amountFormatter(response.body[i].creditAmount) + '</td>'
								+ '<td class="alnright">₹' + amountFormatter(finalBalance.toFixed(2)) + '</td>'
								+ '</tr>';

							$("#tblBody").append(bdy);
						}

						var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
						var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);
						$("#finalBal").val(finalBalance);

						var totalbdy = '<tr align="left" valign="top">'
							+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
							+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
							+ '<td colspan="1" style="text-align: right; border: none!important;opacity: 0;"><strong></strong></td>'
							+ '<td colspan="1" style="text-align: left;"><strong>Total:</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(finalBalance.toFixed(2)) + '</strong></td>'
							+ '</tr>';

						$("#tblBody").append(totalbdy);
					}

					$(".ledgertable").show();
					$("#agGridTable").hide();
				}

			}
		})
	}
}

function downloadPdfData() {
	var ledgerId = $("#bankBookLedgerid").val();
	var voucherType = $("#bankBookVoucherType").val();
	var fromDate = $("#bankbookfromDate").val();
	var toDate = $("#bankbooktoDate").val();
	var ledgerName = $("#bankbookName").html();
	ledgerName = ledgerName.replace(/<br>/g, "");
	var openBalance = $("#openBal").val();
	var finalBal = $("#finalBal").val();

	window.open("/account/bank-report-pdf?id=" + ledgerId + "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate + "&ledgerName=" + ledgerName + "&openBalance=" + openBalance + "&finalBal=" + finalBal, '_blank');

}

function downloadBankAccountHistory() {
	var ledgerId = $("#bankBookLedgerid").val(); // Optional
	var ledgerName = $("#bankbookName").text().trim() + ".xlsx";

	var table = $(".bankAccountHistoryTable"); // Directly select the table
	if (table.length === 0) {
		alert("Bank Account History table not found.");
		return;
	}

	var tableData = [];

	// Extract header row
	var headerRow = [];
	table.find("thead th").each(function() {
		headerRow.push($(this).text().trim());
	});
	tableData.push(headerRow);

	// Extract body rows
	table.find("tbody tr").each(function() {
		var rowData = [];
		$(this).find("td").each(function() {
			rowData.push($(this).text().trim());
		});
		tableData.push(rowData);
	});

	// Create Excel sheet
	var worksheet = XLSX.utils.aoa_to_sheet(tableData);
	var workbook = XLSX.utils.book_new();
	XLSX.utils.book_append_sheet(workbook, worksheet, "Bank History");

	// Trigger file download
	XLSX.writeFile(workbook, ledgerName);
}



function SearchUserInputBank(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChangedBank();
	}
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilterBannk').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}

function resetBtnBank() {
	$("#quickFilterBannk").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	onQuickFilterChangedBank();
	setTimeout(() => {
		gridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}
