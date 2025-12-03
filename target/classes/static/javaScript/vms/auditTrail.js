$(document).ready(
	function() {

		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);

		// Get today's date
		var today = new Date();

		// Format the date for the "from" and "to" fields
		var fromDate = ('0' + 1).slice(-2) + '-'
			+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
			+ today.getFullYear();
		var toDate = ('0' + today.getDate()).slice(-2) + '-'
			+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
			+ today.getFullYear();

		$("#fromDate").val(toDate);
		$("#toDate").val(toDate);

		$("#fromDateCalendar").datetimepicker({
			format: 'd-m-Y',
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		});

		$('#fromDate').blur(function() {
			$("#fromDateCalendar").val($(this).val());
		});

		var dateFormat = 'd-m-Y';

		$("#toDateCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#toDate').val($(this).val());
		});

		$('#toDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		});

		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var type = $('#auditTypeId').val();
		$.ajax({
			url: "view-audit-log-data?fromDate=" + fromDate
				+ "&toDate=" + toDate + "&type=" + type,
			type: 'GET',
			dataType: 'json',
			success: function(response) {
				var data = JSON.parse(response.body);
				//gridOptions.api.setRowData(data);
				var rowData = [];
				gridOptions.api.setRowData(rowData);
				gridOptions.api.setRowData(data);
				if (data && data.length > 0) {
					data.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}
			}
		});

		$("#quickFilter").on("keydown", function(event) {
			if (event.key === "Enter" || event.which === 13) {
				event.preventDefault();
				console.log("Enter key pressed, calling filter function...");
				onQuickFilterChanged();
			}
		});
	});

var columnDefs = [{
	headerCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: false,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true

}, {
	headerName: 'Audit Id',
	field: "auditId",
	hide: true

},
{
	headerName: 'Audit Type Id',
	field: "auditTypeId",

	cellStyle: {
		textAlign: 'left'
	}
}, {
	headerName: 'Audit Type',
	field: "auditType",

}, {
	headerName: 'Audit Action',
	field: "auditAction",

}, {
	headerName: 'Audit Created By',
	field: "auditCreatedBy",

}, {
	headerName: 'Audit Created On',
	field: "auditCreatedOn",

}
	, {
	headerName: "auditTenderEffectiveDate",
	field: "auditTenderEffectiveDate"
},
{
	headerName: "auditTenderPublishStatus",
	field: "auditTenderPublishStatus"
},
{
	headerName: "auditContractEffectiveDate",
	field: "auditContractEffectiveDate"
},
{
	headerName: "auditContractPublishStatus",
	field: "auditContractPublishStatus"
}

];

var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 240
	},
	onSelectionChanged: onSelectionChanged
};

function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	gridOptions.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}

function onSelectionChanged() {
	var selectedRow = gridOptions.api.getSelectedRows();
	console.log("Selected row--->", selectedRow);
	var auditAction = selectedRow[0].auditAction;
	var contractEffectiveDate = selectedRow[0].auditContractEffectiveDate;
	var tenderEffectiveDate = selectedRow[0].auditTenderEffectiveDate;
	var auditType = selectedRow[0].auditType;
	var tenderPublishvalue = selectedRow[0].auditTenderPublishStatus;
	var contractPublishValue = selectedRow[0].auditContractPublishStatus;
	var auditTypeID = selectedRow[0].auditTypeId;
	var createdon = selectedRow[0].auditCreatedOn;
	var createdby = selectedRow[0].auditCreatedBy;
	
	$("#header-text").text(`${auditType} Id: `);
	$("#historyId").text(`${auditTypeID}`);

	var htmlValue = `
	<div class="details-grid ">
														<div class="card  animate w-100 text-center">
															<div class="document-title ">${auditType} ID:</div>
															<div class="document-sub">
																<span id="documentId">${auditTypeID}</span>
															</div>
														</div>
														<div class="card animate w-100 text-center">
															<div class="document-title">Audit Type:</div>
															<div class="document-sub">
																<span id="createdOn">${auditType}</span>
															</div>
														</div>
														<div class="card animate w-100 text-center">
															<div class="document-title">Created Date:</div>
															<div class="document-sub">
																<span id="dueDate">${createdon}</span>
															</div>
														</div>
														<div class="card animate w-100 text-center">
															<div class="document-title">Effective Date:</div>
															<div class="document-sub">
																<span id="OCRLangauage">${tenderEffectiveDate ? tenderEffectiveDate : contractEffectiveDate}</span>
															</div>
														</div>
														<div class="card animate w-100 text-center">
															<div class="document-title">Created By:</div>
															<div class="document-sub">
																<span id="OCRLangauage">${createdby}</span>
															</div>
													</div>
												<div class="card animate w-100 text-center">
													<div class="document-title">Audit Action:</div>
													<div class="document-sub">
													<span id="OCRLangauage">${auditAction}</span>
												</div>
												</div>
													</div>
	`

	var audit_trail_div = $(".auditTrail-content");
	audit_trail_div.html('');
	audit_trail_div.append(htmlValue);
	let cards = document.querySelectorAll(".animate");
	let delay = 0;

	cards.forEach(card => {
		setTimeout(() => {
			card.style.opacity = "1";
			card.style.animation = "fadeInUp 0.8s ease-in-out forwards";
		}, delay);
		delay += 200; // Staggered delay for better effect
	});
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	gridOptions.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);
}

function viewFilteredData() {

	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();

	var type = $('#auditTypeId').val();
	$.ajax({
		url: "view-audit-log-data?fromDate=" + fromDate + "&toDate="
			+ toDate + "&type=" + type,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var data = JSON.parse(response.body);
			//gridOptions.api.setRowData(data);
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
		}
	});

}

function getAuditTypeData() {
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var type = $('#auditTypeId').val();
	$.ajax({
		url: "view-audit-log-data?fromDate=" + fromDate + "&toDate="
			+ toDate + "&type=" + type,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var data = JSON.parse(response.body);
			gridOptions.api.setRowData(data)
		}
	});
}
function resetBtn() {
	$("#quickFilter").val('');

	gridOptions.api.setQuickFilter('');

	gridOptions.api.refreshCells({ force: true });
}


function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}

document.addEventListener("DOMContentLoaded", function() {
	let cards = document.querySelectorAll(".animate");
	let delay = 0;

	cards.forEach(card => {
		setTimeout(() => {
			card.style.opacity = "1";
			card.style.animation = "fadeInUp 0.8s ease-in-out forwards";
		}, delay);
		delay += 200; // Staggered delay for better effect
	});
});
