$(function() {
	viewDayBook();


	/* 	$("#fromDate").datetimepicker({
			format : 'd-m-Y',
			closeOnDateSelect : true,
			timepicker : false,
		})
		
		$("#toDate").datetimepicker({
			format : 'd-m-Y',
			closeOnDateSelect : true,
			timepicker : false,
		}) */


	var dateFormat = localStorage.getItem("dateFormat");
	$("#dayBooktoDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		//minDate : 0,
	}).on("change", function() {
		$('#dayBookfromDate').val($(this).val());
	})

	$('#dayBookfromDate').blur(function() {
		$("#dayBooktoDateCalendar").val($(this).val());
	})



});


function viewDayBook() {

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth() + 1;
	var year = today.getFullYear();
	day = (day < 10) ? '0' + day : day;
	month = (month < 10) ? '0' + month : month;
	var formattedDate = day + '-' + month + '-' + year;
	//console.log(formattedDate);

	var fromDate = formattedDate;
	var toDate = formattedDate;
	$('#dayBookfromDate').val(formattedDate);
	$('#todayDate').find('span').html(formattedDate);

	$.ajax({
		type: "GET",
		url: "day-book-report?fromDate=" + fromDate + "&toDate=" + toDate,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				$("#tblbody").empty();

				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#tblbody").append(emptybdy);
					$(".container").hide();
					$(".noDataFoundContainer").show();
				} else {
					for (var i = 0; i < response.body.length; i++) {
						var debBal = '';
						var creBal = '';
						if (response.body[i].debitAmount == '0') {
							debBal = '';
						} else {
							debBal = '' + response.body[i].debitAmount + '';
							debBal = '₹'.concat(amountFormatter(debBal));
						}
						if (response.body[i].creditAmount == '0') {
							creBal = '';
						} else {
							creBal = '' + response.body[i].creditAmount + '';
							creBal = '₹'.concat(amountFormatter(creBal));
						}
						var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #e8e8e8;';
						var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
							+ '<td>' + response.body[i].voucherDate + '</td>'
							+ '<td>' + response.body[i].ledgerName + '</td>'
							+ '<td>' + response.body[i].voucherType + '</td>'
							+ '<td>' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">' + debBal + '</td>'
							+ '<td class="alnright">' + creBal + '</td>'
							+ '</tr>';
						$("#tblbody").append(bdy);
					}
					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

					var totalbdy = '<tr align="left" valign="top">'
						+ '<td colspan="4" style="text-align: right;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#tblbody").append(totalbdy);
					$(".container").show();
					$(".noDataFoundContainer").hide();

				}

			}

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


function getDataVoucherType() {
	var voucherType = $("#dayBookVoucherType").val();
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth() + 1;
	var year = today.getFullYear();
	day = (day < 10) ? '0' + day : day;
	month = (month < 10) ? '0' + month : month;
	var formattedDate = day + '-' + month + '-' + year;
	//console.log(formattedDate);

	var inputData = $("#dayBookfromDate").val();
	if (inputData != "") {
		var fromDate = inputData;
		var toDate = inputData;
	}
	else {
		var fromDate = formattedDate;
		var toDate = formattedDate;
	}
	$("#tblbody").empty();
	$.ajax({
		type: "GET",
		url: "day-book-report-getVoucher?fromDate=" + fromDate + "&toDate=" + toDate + "&voucherType=" + voucherType,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				$("#tblbody").empty();

				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#tblbody").append(emptybdy);
				} else {
					for (var i = 0; i < response.body.length; i++) {
						var debBal = '';
						var creBal = '';
						if (response.body[i].debitAmount == '0') {
							debBal = '';
						} else {
							debBal = '' + response.body[i].debitAmount + '';
							debBal = '₹'.concat(amountFormatter(debBal));
						}
						if (response.body[i].creditAmount == '0') {
							creBal = '';
						} else {
							creBal = '' + response.body[i].creditAmount + '';
							creBal = '₹'.concat(amountFormatter(creBal));
						}
						var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #e8e8e8;';
						var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
							+ '<td>' + response.body[i].voucherDate + '</td>'
							+ '<td>' + response.body[i].ledgerName + '</td>'
							+ '<td>' + response.body[i].voucherType + '</td>'
							+ '<td>' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">' + debBal + '</td>'
							+ '<td class="alnright">' + creBal + '</td>'
							+ '</tr>';
						$("#tblbody").append(bdy);
					}
					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

					var totalbdy = '<tr align="left" valign="top">'
						+ '<td colspan="4" style="text-align: right;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#tblbody").append(totalbdy);
				}

			}

		}
	})
}


function getDateFilterData() {
	var inputDate = $("#dayBookfromDate").val();

	console.log("input data-->", inputDate);
	$("#todayDate").find('span').html(inputDate);

	$("#tblbody").empty();
	$.ajax({
		type: "GET",
		url: "day-book-report-getFilterData?inputDate=" + inputDate,
		async: false,
		success: function(response) {
			if (response.code == "Success") {
				$("#tblbody").empty();

				if (response.body.length == 0 || response.body.length == null) {
					var emptybdy = '<tr align="left" valign="top">'
						+ '<td colspan="6" style="text-align: center;">NO DATA FOUND</td>'
						+ '</tr>';
					$("#tblbody").append(emptybdy);
					$(".container").hide();
					$(".noDataFoundContainer").show();
					$("#dayBookPdf").addClass("d-none");
				} else {
					for (var i = 0; i < response.body.length; i++) {
						var debBal = '';
						var creBal = '';
						if (response.body[i].debitAmount == '0') {
							debBal = '';
						} else {
							debBal = '' + response.body[i].debitAmount + '';
							debBal = '₹'.concat(amountFormatter(debBal));
						}
						if (response.body[i].creditAmount == '0') {
							creBal = '';
						} else {
							creBal = '' + response.body[i].creditAmount + '';
							creBal = '₹'.concat(amountFormatter(creBal));
						}
						var rowColorStyle = i % 2 === 0 ? 'background-color: #ffffff;' : 'background-color: #e8e8e8;';
						var bdy = '<tr align="left" valign="top" style="' + rowColorStyle + '">'
							+ '<td>' + response.body[i].voucherDate + '</td>'
							+ '<td>' + response.body[i].ledgerName + '</td>'
							+ '<td>' + response.body[i].voucherType + '</td>'
							+ '<td>' + response.body[i].voucherId + '</td>'
							+ '<td class="alnright">' + debBal + '</td>'
							+ '<td class="alnright">' + creBal + '</td>'
							+ '</tr>';
						$("#tblbody").append(bdy);
					}
					var totalDebit = response.body.reduce((sum, entry) => sum + parseFloat(entry.debitAmount), 0);
					var totalCredit = response.body.reduce((sum, entry) => sum + parseFloat(entry.creditAmount), 0);

					var totalbdy = '<tr align="left" valign="top">'
						+ '<td colspan="4" style="text-align: right;"><strong>Total:</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalDebit.toFixed(2)) + '</strong></td>'
						+ '<td class="alnright"><strong>₹' + amountFormatter(totalCredit.toFixed(2)) + '</strong></td>'
						+ '</tr>';

					$("#tblbody").append(totalbdy);
					$(".container").show();
					$(".noDataFoundContainer").hide();
					$("#dayBookPdf").removeClass("d-none");
				}

			}

		}
	})
}

function downloadPdfData() {
	var inputDate = $("#dayBookfromDate").val();
	window.open("/account/day-book-Pdf?inputDate=" + inputDate, '_blank');
}





function simulateDownload() {
	$(".progress-bar").show();
	let progress = 0;
	const interval = setInterval(() => {
		if (progress >= 100) {
			clearInterval(interval);
			downloadPdfData();
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
	/*const downloadBtn = document.getElementById('downloadBtn');
	const progressFill = document.getElementById('progressFill');
	console.log("Done-->");
	if (!downloadBtn.classList.contains('success')) {
		simulateDownload();
	}*/
	downloadPdfData();
}


function onQuickFilterChangedDayBook() {
	let input = $('#quickFilterDayBook').val().toLowerCase();

	$('#DayBookTable tbody tr').filter(function() {
		let particulars = $(this).find('td:eq(1)').text().toLowerCase(); // 2nd column: Particulars
		$(this).toggle(particulars.indexOf(input) > -1);
	});
}


function SearchUserInputDayBook(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChangedDayBook();
	}
}

function resetBtnDayBook() {
	$('#quickFilterDayBook').val('');
	$('#DayBookTable tbody tr').show();
}