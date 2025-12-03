function paymentDetails() {

    var gridDiv = document.querySelector('#payment');
    new agGrid.Grid(gridDiv, paymentGridOption);

    paymentGridOption.api.setRowData([]);

    /* Radio Button select */
    var $radios = $('input:radio[name=CashChequeOrOnline]');
    if ($radios.is(':checked') === false) {
        $radios.filter('[value=Cash]').prop('checked', true);
        $(".bankDiv").hide();
        $(".chequeDiv").hide();
        $(".upiDiv").hide();
        $(".bankSelectionDiv").hide();
        $("#bankSelectPayment").val("");
        $("#currentBalanceBank").val("");
        $("#updatedBalanceBank").val("");
    }

    $("input[name=CashChequeOrOnline]:radio").click(function() {

        if ($('input[name=CashChequeOrOnline]:checked').val() == "Cash") {
            $(".bankDiv").hide();
            $(".chequeDiv").hide();
            $(".upiDiv").hide();
            $(".bankSelectionDiv").hide();
            $("#bankSelectPayment").val("");
            $("#currentBalanceBank").val("");
            $("#updatedBalanceBank").val("");
            $("#chequeNo").val("");
            $("#chequeBankName").val("");
            $("#chequeBankBranch").val("");
            $("#chequeAccountNumber").val("");
            $("#payRemarks").val("");
            $("#bankSelect").val("");
            $("#transactionNumber").val("");
            $("#receiverBankBranch").val("");
            $("#receiverBankName").val("");
            $("#receiverIfscCode").val("");
            $("#receiverAccountNumber").val("");
            $("#onlineUpiID").val("");
            $("#receiverOnlineUpiID").val("");
            $("#receiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnline]:checked').val() == "Cheque") {
            $(".bankDiv").hide();
            $(".chequeDiv").show();
            $(".upiDiv").hide();
            $(".bankSelectionDiv").show();
            $("#bankSelectPayment").val("");
            $("#currentBalanceBank").val("");
            $("#updatedBalanceBank").val("");
            $("#chequeNo").val("");
            $("#chequeBankName").val("");
            $("#chequeBankBranch").val("");
            $("#chequeAccountNumber").val("");
            $("#payRemarks").val("");
            $("#bankSelect").val("");
            $("#transactionNumber").val("");
            $("#receiverBankBranch").val("");
            $("#receiverBankName").val("");
            $("#receiverIfscCode").val("");
            $("#receiverAccountNumber").val("");
            $("#onlineUpiID").val("");
            $("#receiverOnlineUpiID").val("");
            $("#receiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnline]:checked').val() == "Online") {
            $(".bankDiv").show();
            $(".chequeDiv").hide();
            $(".upiDiv").hide();
            $(".bankSelectionDiv").show();
            $("#bankSelectPayment").val("");
            $("#currentBalanceBank").val("");
            $("#updatedBalanceBank").val("");
            $("#chequeNo").val("");
            $("#chequeBankName").val("");
            $("#chequeBankBranch").val("");
            $("#chequeAccountNumber").val("");
            $("#payRemarks").val("");
            $("#bankSelect").val("");
            $("#transactionNumber").val("");
            $("#receiverBankBranch").val("");
            $("#receiverBankName").val("");
            $("#receiverIfscCode").val("");
            $("#receiverAccountNumber").val("");
            $("#onlineUpiID").val("");
            $("#receiverOnlineUpiID").val("");
            $("#receiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnline]:checked').val() == "Upi") {
            $(".bankDiv").hide();
            $(".chequeDiv").hide();
            $(".upiDiv").show();
            $(".bankSelectionDiv").show();
            $("#bankSelectPayment").val("");
            $("#currentBalanceBank").val("");
            $("#updatedBalanceBank").val("");
            $("#chequeNo").val("");
            $("#chequeBankName").val("");
            $("#chequeBankBranch").val("");
            $("#chequeAccountNumber").val("");
            $("#payRemarks").val("");
            $("#bankSelect").val("");
            $("#transactionNumber").val("");
            $("#receiverBankBranch").val("");
            $("#receiverBankName").val("");
            $("#receiverIfscCode").val("");
            $("#receiverAccountNumber").val("");
            $("#onlineUpiID").val("");
            $("#receiverOnlineUpiID").val("");
            $("#receiverUpiTransactionID").val("");
        }
    });

    //radio button setting for invoice payment
    var $methodRadios = $('input:radio[name=CashChequeOrOnlineMethod]');
    if ($methodRadios.is(':checked') === false) {
        $methodRadios.filter('[value=Cash]').prop('checked', true);
        $(".methodBankDiv").hide();
        $(".methodChequeDiv").hide();
        $(".methodUpiDiv").hide();
        $(".bankSelectionMethodDiv").hide();
        $("#bankSelectPaymentMethod").val("");
        $("#currentBalanceBankMethod").val("");
        $("#updatedBalanceBankMethod").val("");
    }

    $("input[name=CashChequeOrOnlineMethod]:radio").click(function() {

        if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cash") {
            $(".methodBankDiv").hide();
            $(".methodChequeDiv").hide();
            $(".methodUpiDiv").hide();
            $(".bankSelectionMethodDiv").hide();
            $("#bankSelectPaymentMethod").val("");
            $("#currentBalanceBankMethod").val("");
            $("#updatedBalanceBankMethod").val("");
            $("#methodChequeNo").val("");
            $("#methodChequeBankName").val("");
            $("#methodChequeBankBranch").val("");
            $("#methodChequeAccountNumber").val("");
            $("#methodPayRemarks").val("");
            $("#methodBankSelect").val("");
            $("#methodTransactionNumber").val("");
            $("#methodReceiverBankBranch").val("");
            $("#methodReceiverBankName").val("");
            $("#methodReceiverIfscCode").val("");
            $("#methodReceiverAccountNumber").val("");
            $("#methodOnlineUpiID").val("");
            $("#methodReceiverOnlineUpiID").val("");
            $("#methodReceiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Cheque") {
            $(".methodBankDiv").hide();
            $(".methodChequeDiv").show();
            $(".methodUpiDiv").hide();
            $(".bankSelectionMethodDiv").show();
            $("#bankSelectPaymentMethod").val("");
            $("#currentBalanceBankMethod").val("");
            $("#updatedBalanceBankMethod").val("");
            $("#methodChequeNo").val("");
            $("#methodChequeBankName").val("");
            $("#methodChequeBankBranch").val("");
            $("#methodChequeAccountNumber").val("");
            $("#methodPayRemarks").val("");
            $("#methodBankSelect").val("");
            $("#methodTransactionNumber").val("");
            $("#methodReceiverBankBranch").val("");
            $("#methodReceiverBankName").val("");
            $("#methodReceiverIfscCode").val("");
            $("#methodReceiverAccountNumber").val("");
            $("#methodOnlineUpiID").val("");
            $("#methodReceiverOnlineUpiID").val("");
            $("#methodReceiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Online") {
            $(".methodBankDiv").show();
            $(".methodChequeDiv").hide();
            $(".methodUpiDiv").hide();
            $(".bankSelectionMethodDiv").show();
            $("#bankSelectPaymentMethod").val("");
            $("#currentBalanceBankMethod").val("");
            $("#updatedBalanceBankMethod").val("");
            $("#methodChequeNo").val("");
            $("#methodChequeBankName").val("");
            $("#methodChequeBankBranch").val("");
            $("#methodChequeAccountNumber").val("");
            $("#methodPayRemarks").val("");
            $("#methodBankSelect").val("");
            $("#methodTransactionNumber").val("");
            $("#methodReceiverBankBranch").val("");
            $("#methodReceiverBankName").val("");
            $("#methodReceiverIfscCode").val("");
            $("#methodReceiverAccountNumber").val("");
            $("#methodOnlineUpiID").val("");
            $("#methodReceiverOnlineUpiID").val("");
            $("#methodReceiverUpiTransactionID").val("");
        } else if ($('input[name=CashChequeOrOnlineMethod]:checked').val() == "Upi") {
            $(".methodBankDiv").hide();
            $(".methodChequeDiv").hide();
            $(".methodUpiDiv").show();
            $(".bankSelectionMethodDiv").show();
            $("#bankSelectPaymentMethod").val("");
            $("#currentBalanceBankMethod").val("");
            $("#updatedBalanceBankMethod").val("");
            $("#methodChequeNo").val("");
            $("#methodChequeBankName").val("");
            $("#methodChequeBankBranch").val("");
            $("#methodChequeAccountNumber").val("");
            $("#methodPayRemarks").val("");
            $("#methodBankSelect").val("");
            $("#methodTransactionNumber").val("");
            $("#methodReceiverBankBranch").val("");
            $("#methodReceiverBankName").val("");
            $("#methodReceiverIfscCode").val("");
            $("#methodReceiverAccountNumber").val("");
            $("#methodOnlineUpiID").val("");
            $("#methodReceiverOnlineUpiID").val("");
            $("#methodReceiverUpiTransactionID").val("");
        }
    });

}

var columnDefsPayment = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: false,
        checkboxSelection: true,
        maxWidth: 30,
        sortable: false,
        filter: false,
        resizable: true,
        pinned: 'left',
    },
    {
        headerName: 'Product',
        field: "productName",
    }, {
        headerName: 'SKU',
        field: "productSku",
    }, {
        headerName: 'Quantity',
        field: "quantity",
        cellStyle: {
            textAlign: 'right'
        },
		cellClass: 'right-align'
    }, {
        headerName: 'Price',
        field: "unitPrice",
        cellStyle: {
            textAlign: 'right'
        },
		cellClass: 'right-align'
    }, {
        headerName: 'Total Price',
        field: "lineAmnt",
        cellStyle: {
            textAlign: 'right'
        },
        cellClass: 'right-align'
    }
];

// let the grid know which columns and what data to use
var paymentGridOption = {
    columnDefs: columnDefsPayment,
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        flex: 1,
        minWidth: 100,
    },
    onSelectionChanged: rowSelectPayment,
};


var paymentDetailsForSelectedUser = [];

function getPaymenDetails(patId) {

	$('#bill-table tbody').empty();
	
	$('#methodBankSelect').val($('#methodBankSelect option:first').val()).trigger('change');
	$('#methodCostCenterSelect').val($('#methodCostCenterSelect option:first').val()).trigger('change');
	
	$("#methodPayRemarks").val('');

    agGrid.simpleHttpRequest({
        url: "manage-ambulance-payment?id=" + patId
    }).then(function(response) {
        if (response.message === "Success") {

            let responseBody;
            try {
                responseBody = JSON.parse(response.body);
            } catch (error) {
                console.error("Error parsing response body:", error);
                return;
            }

            const paymentDetails = responseBody.paymentDetails;
            paymentDetailsForSelectedUser = paymentDetails;
            if (paymentDetails && paymentDetails.length > 0) {
            	console.log(paymentDetails)
                paymentGridOption.api.setRowData([]);
                $(bookingIds).text(paymentDetails[0].bookingId || "N/A");
                $(invoiceIds).text(paymentDetails[0].invoiceId || "N/A");
                $("#accountSubGroupId").val(paymentDetails[0].accountSubGroup || "");

                $(subTotal).text(paymentDetails[0].subTotalAmnt || "0.00");
                $(cgst).text(paymentDetails[0].cgst || "0.00");
                $(sgst).text(paymentDetails[0].sgst || "0.00");
                $(grandTotal).text(paymentDetails[0].totalAmnt || "0.00");
                $(billingAmount).val("0.00");
                
                let bill_details = paymentDetails[0]?.bill_details;
                if(bill_details) {
                	let merged_arr = [];
                	bill_details.forEach(a => {
                		let arr = JSON.parse(a?.pay_list);
                		merged_arr = merged_arr.concat(arr);
                	})
                	createPaidTable(merged_arr)
                } else {
                	$("#pendingAmount").text(paymentDetails[0].totalAmnt || "0.00");
                	$("#pendingAmntTR").removeClass('d-none');
                	addBillRow();
                }
                
            } else {
                console.warn("No payment details found.");
                paymentGridOption.api.setRowData([]);
                $(bookingIds).text("N/A");
                $(invoiceIds).text("N/A");
                $(subTotal).text("0.00");
                $(cgst).text("0.00");
                $(sgst).text("0.00");
                $(grandTotal).text("0.00");
                $("#pendingAmount").text("0.00");
                $("#billingAmount").val("0.00");
                $("#pendingAmntTR").addClass('d-none');
                addBillRow();
            }
			
			if(paymentDetails && paymentDetails.length > 0) {
				if (paymentDetails[0].pay_status == 'Fully Paid') {
	                $("#payBtn,.btn-bill-v").hide();
	                $("#methodBankSelect,#methodCostCenterSelect,#methodPayRemarks").attr("disabled",true);
	            } else {
	                $("#payBtn,.btn-bill-v").show();
	                $("#methodBankSelect,#methodCostCenterSelect,#methodPayRemarks").attr("disabled",false);
	            }
			} else {
				$("#payBtn,.btn-bill-v").hide();
				$("#methodBankSelect,#methodCostCenterSelect,#methodPayRemarks").attr("disabled",false);
			}
            
        } else {
            console.error("Failed to fetch data, message:", response.message);
        }
    })
}

function createPaidTable(dataset) {
	$('#bill-table tbody').empty();
	
	if(dataset && dataset.length > 0) {
		
		let dropdownOptions = `<option></option>`;
	  	globalPayModeList.forEach((kra,index) => {
	  		const selected = index === 0 ? ' selected' : '';
	  		dropdownOptions += `<option value="${kra.key}" ${selected}>${kra.name}</option>`;
	  	});
	  	
	  	let totalAmount = 0;
	  	let methodBankSelect = '';
	  	let methodCostCenterSelect = '';
	  	let methodPayRemarks = '';
	  	dataset.forEach((d,c)=>{
	  		let price = d?.amount ? parseFloat(d.amount)?.toFixed(2) : '0.00';
	  		
	  		methodBankSelect = d?.bankaccount;
	  		methodCostCenterSelect = d?.costcenter;
	  		methodPayRemarks = d?.remarks;
	  		
	  		totalAmount = totalAmount + parseFloat(price);
	  		let newRow = $(`
	  			<tr class="bill-parent-class-2" data-bill-id="${newBillId}">
	  				<td style="text-align: center" class="slno-td">${c+1}</td>
	  				<td>
	  					<select class="form-control pay_mode" disabled id="paymodeselect_${c}">
	  						${dropdownOptions}
	  					</select>
	  				</td>
	  				<td>
	  					<input type="text" class="form-control transaction_no" placeholder="Transaction/Cheque Number" value="${d?.transaction_no}" disabled>
	  				</td>
	  				<td>
	  					<input type="text" class="form-control bank" placeholder="Bank Name" disabled  value="${d?.bank}">
	  				</td>
	  				<td>
	  					<input type="text" class="form-control amount" placeholder="Amount" onkeyup=allowOnlyNumbers(this);validateEachAmount(${newBillId}) disabled  value="${price}">
	  				</td>
	  			</tr>
	  		`); 
	  		
	  		$('#bill-table tbody').append(newRow);
	  		$("#paymodeselect_"+c).val(d?.pay_mode);
	  		
	  		newBillId = c+1;
	  	})
	  	
	  	let grandTotal = $("#grandTotal").text() || '0.00';
	  	let pendingAmnt = parseFloat(grandTotal) - totalAmount;
	  	
	  	if(pendingAmnt === 0 || pendingAmnt == '0.00') {
	  		$("#pendingAmntTR").addClass('d-none');
	  	} else {
	  		$("#pendingAmntTR").removeClass('d-none');
	  	}
	  	
	  	$("#methodBankSelect").val(methodBankSelect).trigger('change');
	  	$("#methodCostCenterSelect").val(methodCostCenterSelect).trigger('change');
	  	$("#methodPayRemarks").val(methodPayRemarks);
	  	
	  	$("#pendingAmount").text(pendingAmnt ? pendingAmnt.toFixed(2) : '0.00' );
		
	} else {
		addBillRow();
	}
}

function rowSelectPayment() {
    var selectedRows = paymentGridOption.api.getSelectedRows();
    if (selectedRows.length > 0) {
        //$('#payBtn').removeClass('hidden');
    } else {
        //	$('#payBtn').addClass('hidden');
    }
}

function initiateBillingProcess() {

	let billingAmount = $("#billingAmount").val();
	if(billingAmount == '' || billingAmount == '0.00' || billingAmount == 0) {
		toastr.error("Billing Amount Can't Be 0 Or Left Blank");
		return;
	} 
	
	let billDetails = [];
	$("#bill-table tbody tr.bill-parent-class").each(function(index) {
		let a = {
			billamount : billingAmount,
			bankaccount : $("#methodBankSelect").val(),
			costcenter : $("#methodCostCenterSelect").val(),
			remarks : $("#methodPayRemarks").val(),
			slno : index + 1,
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
  	    
  	    if(!item.pay_mode) {
  	    	toastr.error("Payment Mode Required");
  	    	return;
  	    }
  	    if(!item.transaction_no && item.pay_mode_name != 'CASH') {
  	    	toastr.error("Transaction/Cheque Number Required");
  	    	return;
  	    }
  	    if(!item.amount) {
  	    	toastr.error("Amount Required");
  	    	return;
  	    }
  	    
  	}
	
	console.log(billDetails);

    let selectedInvoice = [];
    let inv = {};

    inv['invoiceId'] = paymentDetailsForSelectedUser[0].invoiceId;
    inv['outstandingAmount'] = $("#billingAmount").val();
    inv['vendorId'] = paymentDetailsForSelectedUser[0].customerId;
    inv['legerid'] = $("#methodBankSelect").val();
    inv['rcvType'] = 'againstRef';
    selectedInvoice.push(inv);

    var dataset = [];
    let item = {};

    item['costCenter'] = $("#methodCostCenterSelect").val();
    item['journalVoucher'] = '';
    item['totalAmount'] = paymentDetailsForSelectedUser[0].totalAmnt;
    item['description'] = 'Billing';
    item['voucherDate'] = currentFormattedDate();
    item['fromAccountSubGroup'] = $("#methodBankSelect").val();
    item['fromName'] = '';
    item['fromAmount'] = $("#billingAmount").val();
    item['voucherType'] = "RECEIPT";
    item['listTdsId'] = '';
    item['taxType'] = '';
    item['paymentType'] = 'againstRef';
    dataset.push(item);

    obj = {};
    obj['costCenter'] = $("#methodCostCenterSelect").val();
    obj['journalVoucher'] = '';
    obj['totalAmount'] = paymentDetailsForSelectedUser[0].totalAmnt;
    obj['description'] = 'Payment for ambulance booking';
    obj['voucherDate'] = currentFormattedDate();
    obj['toAccountSubGroup'] = paymentDetailsForSelectedUser[0].accountSubGroup;
    obj['toName'] = '';
    obj['toAmount'] = $("#billingAmount").val();
    obj['voucherType'] = "RECEIPT";
    obj['listTdsId'] = '';
    obj['taxType'] = '';
    obj['paymentType'] = 'againstRef';
    obj['paymentDesc'] = '';
    obj['advOrNewRfAmount'] = '';
    obj['invoiceObj'] = JSON.stringify(selectedInvoice);
    obj['advReceiveObj'] = JSON.stringify([]);
    obj['billDetails'] = JSON.stringify(billDetails);
    dataset.push(obj);

    console.log(dataset);
    submitJournal(dataset);
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
                url: "/account/receipt-voucher-add-journal",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(dataset),
                success: function(response) {
                    if (response.code == "201" || response.code == "200") {
                        $('.loader').hide();
                        $("body").removeClass("overlay");
                        toastr.success("Paid Successfully");
                        if(bookFromType == 'OPD') {
                        	getAllPatientsDetails();
                        }
                        if(bookFromType == 'Ambulance') {
                        	getAllAmbulanceDetails();
                        }
                        if(bookFromType == 'PHARMACY') {
                        	getAllPatientsDetails($('#bookingIds').text());
                        }
                        if(type == 'RAD' || type == 'PATH') {
                        	getAllPatientsDetails($('#bookingIds').text());
                        }
                        $("#payBtn").hide();
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
            }) //ajax ends
        }
    }) //swal function block ends
}

const currentFormattedDate = () => {
    const currentDate = new Date();
    const day = String(currentDate.getDate()).padStart(2, '0'); // Ensure 2-digit day
    const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const year = currentDate.getFullYear();

    return `${day}-${month}-${year}`;
};

function allowOnlyNumbers(input) {
    let value = input.value;
    value = value.replace(/[^0-9.]/g, '');

    const parts = value.split('.');
    if (parts.length > 2) {
        value = parts[0] + '.' + parts.slice(1).join('').replace(/\./g, '');
    }

    input.value = value;
}
