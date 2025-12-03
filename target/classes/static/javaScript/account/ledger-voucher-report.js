$(function() {

	var dateFormat = localStorage.getItem("dateFormat");
	$("#toDateCalendarLedgerVoucher").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#ledgerVoucherfromDate').val($(this).val());
	})

	$('#ledgerVoucherfromDate').blur(function() {
		$("#toDateCalendarLedgerVoucher").val($(this).val());
	})

	$("#toDateCalendar2LedgerVoucher").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#ledgerVouchertoDate').val($(this).val());
	})

	$('#ledgerVouchertoDate').blur(function() {
		$("#toDateCalendar2LedgerVoucher").val($(this).val());
	})

	var gridDiv = document.querySelector('#myGridLedgerVchr');
	new agGrid.Grid(gridDiv, gridOptionsLedger);

	agGrid.simpleHttpRequest({
		url: "manage-ledger-view"
	}).then(function(data) {
		console.log(JSON.stringify(data))
		var len = data.length;
		$('#totalReq').find('span').html(len);

		//gridOptionsLedger.api.setRowData(data);

		var rowData = [];
		gridOptionsLedger.api.setRowData(rowData);

		gridOptionsLedger.api.setRowData(data);

		if (data && data.length > 0) {
			gridOptionsLedger.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true); // Select the first row
				}
			});
		}
	});


	console.log("ledger voucher report run success----->");
});



//search bar

function onQuickFilterChangedLedger() {
	gridOptionsLedger.api
		.setQuickFilter(document.getElementById('quickFilterLedgerVoucher').value);
	var rowCount = gridOptionsLedger.api.getDisplayedRowCount();
	$('#totalReq').find('span').html(rowCount);
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilterLedgerVoucher').val() == null || $('#quickFilterLedgerVoucher').val() == "") {
		id.style.display = "none";
	}
}


const columnDefsLedgerVchr = [
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
		maxWidth: 150,
		//hide:true,

	},

	{
		headerName: "Ledger Name",
		field: "ledgername",
		maxWidth: 200,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Group",
		field: "groupId",
		width: 150,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "PAN",
		field: "panitn",
		width: 180,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Address",
		field: "address",
		width: 250,
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "View Ledger Transaction",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		/*cellRenderer: function(params) {
			return `<a onclick="openLedgerDetailsView('${params.node.id}${params.data.ledgerId}','${btoa(params.data.ledgerName)}')" 
					href="javascript:void(0)" 
					title="View Ledger Transactions History">
					<i class="fas fa-eye"></i>
				</a>`;
		}*/

		cellRenderer: function(params) {
			return `
				        <a href="javascript:void(0)" 
				           onclick="openLedgerDetailsView('${params.node.id}', '${params.data.leadgerId}', '${params.data.ledgername}')" title="View Transaction History">
				          <i class="fas fa-eye"></i>
				        </a>
				    `;
		}
	}];
'${params.node.id}'

const gridOptionsLedger = {
	columnDefs: columnDefsLedgerVchr,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 200,
		flex: 1
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	getRowNodeId: function(data) {
		return data.leadgerId;
	}
};


function openLedgerDetailsView(rowId, LedgerId, LedgerName) {
    setTimeout(() => {
        const rowNode = gridOptionsLedger.api.getRowNode(rowId);
        if (rowNode) {
            rowNode.setSelected(true);
        }
        $("#ledgerVoucherGridSection").addClass("d-none");
        $("#ledgerVoucherDetailsDiv").removeClass("d-none");
    }, 100);
}


function closeLedgerVoucherDetails() {
	$("#ledgerVoucherGridSection").removeClass("d-none");
	$("#ledgerVoucherDetailsDiv").addClass("d-none");

}
function rowSelect() {
	var selectedRows = gridOptionsLedger.api.getSelectedRows();

	console.log("ledger voucher selected -->", selectedRows);
	var bankId = selectedRows[0].leadgerId;
	var bankName = btoa(selectedRows[0].ledgername);
	var rowCount = 0;

	var selectedNodes = gridOptionsLedger.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		viewLedgerDetails(bankId, bankName);
	}
	else {
		console.log("Not Selected");
	}
}
function viewLedgerDetails(id, ledgerName) {
	//alert("viewledgerDetails--------"+ledgerName);
	$("#searchRowDiv").hide();
	$("#ledgerIdVoucher").val(id);
	$("#voucherNumber").text(id);
	console.log("ledger name --->", atob(ledgerName));
	$("#ledgerVcrNameText").text(atob(ledgerName));
	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var todate = toDateString.toString();

	$("#ledgerVouchertoDate").val(todate);
	$("#ledgerVoucherfromDate").val(fromDate);
	/* 	$.ajax({
			type : "GET",
			url : "ledger-voucher-details?id=" + id,
			async : false,
			success : function(response) {
				if (response.code == "Success") {
					$("#tblBody").empty();
					if (response.body.length == 0 || response.body.length == null) {
						var emptybdy='<tr align="left" valign="top">'
							+'<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
							+'</tr>';
						$("#tblBody").append(emptybdy);
					}else{
						for(var i=0;i<response.body.length;i++){
							var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #bcb6b6;';
							var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
								+'<td style="text-align: center;">'+response.body[i].voucherDate+'</td>'
								+'<td style="text-align: center;">'+response.body[i].ledgerName+'</td>'
								+'<td style="text-align: center;">'+response.body[i].voucherType+'</td>'
								+'<td style="text-align: center;">'+response.body[i].voucherId+'</td>'
								+'<td class="alnright">₹'+amountFormatter(response.body[i].debitAmount)+'</td>'
								+'<td class="alnright">₹'+amountFormatter(response.body[i].creditAmount)+'</td>'
								+'</tr>';
						  $("#tblBody").append(bdy);		
						}
						var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
						var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);
						
						var totalbdy = '<tr align="left" valign="top">'
							 + '<td class="alnright"><strong>' +'</strong></td>'
							 + '<td class="alnright"><strong>' +'</strong></td>'
							 + '<td class="alnright"><strong>' +'</strong></td>'
							+ '<td style="text-align: right;"><strong>Total:</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
							+ '</tr>';
						    
						$("#tblBody").append(totalbdy);
					}
					
					
					$(".ledgertable").show();
					$("#agGridTable").hide();
				}
				$("#ledgerVcrName").text(atob(ledgerName));
			}
		}) */
	viewLedgerFilteredData();
}
function backPage() {
	$(".ledgertable").hide();
	$("#searchRowDiv").show();
	$("#agGridTable").show();
	$("#ledgerVoucherTbody").empty();
	$("#ledgerVoucherReportType").val("");
	$("#ledgerVoucherfromDate").val("");
	$("#ledgerVouchertoDate").val("");
}

function getDataVoucherType() {
	var voucherType = $("#ledgerVoucherReportType").val();
	var ledgerId = $("#ledgerIdVoucher").val();
	$.ajax({
		type: "GET",
		url: "ledger-voucher-details-wrtVoucherType?id=" + ledgerId + "&voucherType=" + voucherType,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				$("#ledgerVoucherTbody").empty();
				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#ledgerVoucherTbody").append(emptybdy);
				} else {
					for (var i = 0; i < response.body.length; i++) {
						var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #f3f3f3;';
						var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
							//var bdy='<tr align="left" valign="top">'
							+ '<td style="text-align: center;">' + response.body[i].voucherDate + '</td>'
							+ '<td style="text-align: center;">' + response.body[i].ledgerName + '</td>'
							+ '<td style="text-align: center;">' + response.body[i].voucherType + '</td>'
							+ '<td style="text-align: center;">' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].debitAmount) + '</td>'
							+ '<td class="alnright">₹' + amountFormatter(response.body[i].creditAmount) + '</td>'
							+ '</tr>';
						$("#ledgerVoucherTbody").append(bdy);
					}
					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

					var totalbdy = '<tr align="left" valign="top">'
						+ '<td class="alnright"><strong>' + '</strong></td>'
						+ '<td class="alnright"><strong>' + '</strong></td>'
						+ '<td class="alnright"><strong>' + '</strong></td>'
						+ '<td  style="text-align: right;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#ledgerVoucherTbody").append(totalbdy);

				}
				$(".ledgertable").show();
				$("#agGridTable").hide();
			}
			//$("#ledgerVcrName").text(atob(ledgerName));
		}
	})
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


function viewLedgerFilteredData() {

	console.log("ledger voucher filter data is called-->")
	var voucherType = $("#ledgerVoucherReportType").val();
	var ledgerId = $("#ledgerIdVoucher").val();
	var fromDateFilter = $("#ledgerVoucherfromDate").val();
	var toDateFilter = $("#ledgerVouchertoDate").val();

	var validation = true;
	if (fromDateFilter == null || fromDateFilter == "") {
		toastr.error("Please Provide From Date");
		validation = false;
	} else if (toDateFilter == null || toDateFilter == "") {
		toastr.error("Please Provide To Date");
		validation = false;
	}

	if (validation) {
		$.ajax({
			type: "GET",
			url: "ledger-voucher-details-wrtDate?id=" + ledgerId + "&voucherType=" + voucherType + "&fromDate=" + fromDateFilter + "&toDate=" + toDateFilter,
			async: false,
			success: function(response) {
				if (response.code == "Success") {
					$("#ledgerVoucherTbody").empty();
					if (response.body.length == 0 || response.body.length == null) {
						var emptybdy = '<tr align="left" valign="top">'
							+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
							+ '</tr>';
						$("#ledgerVoucherTbody").append(emptybdy);
						$("#downloadLedgerPdfId").addClass("fadeBtn");
						$("#downloadExcelId").addClass("fadeBtn");
					} else {
						$("#downloadLedgerPdfId").removeClass("fadeBtn");
						$("#downloadExcelId").removeClass("fadeBtn");
						for (var i = 0; i < response.body.length; i++) {
							var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #f3f3f3;';
							var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
								//var bdy='<tr align="left" valign="top">'
								+ '<td style="text-align: center;">' + response.body[i].voucherDate + '</td>'
								+ '<td style="text-align: center;">' + response.body[i].ledgerName + '</td>'
								+ '<td style="text-align: center;">' + response.body[i].voucherType + '</td>'
								+ '<td style="text-align: center;">' + response.body[i].voucherId + '</td>'
								+ '<td class="alnright">₹' + amountFormatter(response.body[i].debitAmount) + '</td>'
								+ '<td class="alnright">₹' + amountFormatter(response.body[i].creditAmount) + '</td>'
								+ '</tr>';
							$("#ledgerVoucherTbody").append(bdy);
						}
						var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
						var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

						var totalbdy = '<tr align="left" valign="top">'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td class="alnright"><strong>' + '</strong></td>'
							+ '<td  style="text-align: right;"><strong>Total:</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
							+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
							+ '</tr>';

						$("#ledgerVoucherTbody").append(totalbdy);
					}

					$(".ledgertable").show();
					$("#agGridTable").hide();
				}

			}
		})
	}
}


function downloadExcelData() {
	var today = new Date();
	var fromDateString = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var toDateString = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	var fromDate = fromDateString.toString();
	var todate = toDateString.toString();

	var ledgerName = "";

	var inputFromDate = $("#ledgerVoucherfromDate").val();
	var inputToDate = $("#ledgerVouchertoDate").val();

	if (inputFromDate && inputToDate) {

		ledgerName = $("#ledgerVcrNameText").text() + "( " + inputFromDate + " To " + inputToDate + " )" + ".xlsx";
	}
	else {
		ledgerName = $("#ledgerVcrNameText").text() + "( " + fromDate + " To " + todate + " )" + ".xlsx";

	}
	var tableData = [];

	var headerRow = [];
	$(".statementTable thead th").each(function() {
		headerRow.push($(this).text());
	});
	tableData.push(headerRow);

	$(".statementTable tbody tr").each(function() {
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

function downloadLedgerPdf() {
	var ledgerId = $("#ledgerIdVoucher").val();
	var voucherType = $("#ledgerVoucherReportType").val();
	var fromDate = $("#ledgerVoucherfromDate").val();
	var toDate = $("#ledgerVouchertoDate").val();
	window.open("/account/ledger-voucher-Pdf?ledgerId=" + ledgerId + "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate, '_blank');
}

function SearchUserInputLedger(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChangedLedger();
	}
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilterLedgerVoucher').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}

function resetBtnLedger() {
	$("#quickFilterLedgerVoucher").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	onQuickFilterChangedLedger();
	setTimeout(() => {
		gridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}