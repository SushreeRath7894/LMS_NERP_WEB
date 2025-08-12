package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesInvoicePaymentModel {
	private String userId;
	private String salesInvoiceId;
	private String payPoId;
	private String totalAmount;
	private String payAmount;
	private String payRemarks;
	private String createdBy;
	private String organization;
	private String orgDivision;

	private String saleInvoice;
	private String poId;
	private String qutCreatedBy;
	private String invoiceDate;
	private Double grandTotal;
	private String custId;
	private String custName;
	private String paymentTermId;
	private String dueDate;
	private String status;
	private String rejectStatus;
	private String paymentStatus;
	private String approvalStatus;
	private String approvedBy;
	private String rejectedBy;
	private String paymentDate;
	private String qutUpdatedOn;
	private String quantity;
	private String quantitynew;
	private String paymentId;
	private String project;
	private String docName;
	private String paymentMode;
	private String chequeNo;
	private String chequeBankName;
	private String chequeBankBranch;
	private String chequeAccountNumber;
	private String bankSelect;
	private String onlineUpiID;
	private String transactionNumber;
	private String receiverBankName;
	private String receiverBankBranch;
	private String receiverIfscCode;
	private String receiverAccountNumber;
	private String receiverOnlineUpiID;
	private String receiverUpiTransactionID;
	private String vendorId;
	private String sapId;
	private String type;
	private String scheduleDate;
	private String amountPaid;
	private String methodOfAdj;
	private String invoiceNo;
	private String userRoleType;
	private String usedDebitNotes;
	private String payableAmountFinal;
	private String remainAmount;
	private String dueAmount;

	private String usedCreditNotes;
	private String receivableAmountFinal;
	private String bankSelectPayment;
	private String vendorName;
	private String transactionDate;

	public SalesInvoicePaymentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSalesInvoiceId() {
		return salesInvoiceId;
	}

	public void setSalesInvoiceId(String salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
	}

	public String getPayPoId() {
		return payPoId;
	}

	public void setPayPoId(String payPoId) {
		this.payPoId = payPoId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayRemarks() {
		return payRemarks;
	}

	public void setPayRemarks(String payRemarks) {
		this.payRemarks = payRemarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getSaleInvoice() {
		return saleInvoice;
	}

	public void setSaleInvoice(String saleInvoice) {
		this.saleInvoice = saleInvoice;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getQutCreatedBy() {
		return qutCreatedBy;
	}

	public void setQutCreatedBy(String qutCreatedBy) {
		this.qutCreatedBy = qutCreatedBy;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRejectStatus() {
		return rejectStatus;
	}

	public void setRejectStatus(String rejectStatus) {
		this.rejectStatus = rejectStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}

	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getQuantitynew() {
		return quantitynew;
	}

	public void setQuantitynew(String quantitynew) {
		this.quantitynew = quantitynew;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getChequeBankName() {
		return chequeBankName;
	}

	public void setChequeBankName(String chequeBankName) {
		this.chequeBankName = chequeBankName;
	}

	public String getChequeBankBranch() {
		return chequeBankBranch;
	}

	public void setChequeBankBranch(String chequeBankBranch) {
		this.chequeBankBranch = chequeBankBranch;
	}

	public String getChequeAccountNumber() {
		return chequeAccountNumber;
	}

	public void setChequeAccountNumber(String chequeAccountNumber) {
		this.chequeAccountNumber = chequeAccountNumber;
	}

	public String getBankSelect() {
		return bankSelect;
	}

	public void setBankSelect(String bankSelect) {
		this.bankSelect = bankSelect;
	}

	public String getOnlineUpiID() {
		return onlineUpiID;
	}

	public void setOnlineUpiID(String onlineUpiID) {
		this.onlineUpiID = onlineUpiID;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getReceiverBankName() {
		return receiverBankName;
	}

	public void setReceiverBankName(String receiverBankName) {
		this.receiverBankName = receiverBankName;
	}

	public String getReceiverBankBranch() {
		return receiverBankBranch;
	}

	public void setReceiverBankBranch(String receiverBankBranch) {
		this.receiverBankBranch = receiverBankBranch;
	}

	public String getReceiverIfscCode() {
		return receiverIfscCode;
	}

	public void setReceiverIfscCode(String receiverIfscCode) {
		this.receiverIfscCode = receiverIfscCode;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public String getReceiverOnlineUpiID() {
		return receiverOnlineUpiID;
	}

	public void setReceiverOnlineUpiID(String receiverOnlineUpiID) {
		this.receiverOnlineUpiID = receiverOnlineUpiID;
	}

	public String getReceiverUpiTransactionID() {
		return receiverUpiTransactionID;
	}

	public void setReceiverUpiTransactionID(String receiverUpiTransactionID) {
		this.receiverUpiTransactionID = receiverUpiTransactionID;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}

	public String getUsedDebitNotes() {
		return usedDebitNotes;
	}

	public void setUsedDebitNotes(String usedDebitNotes) {
		this.usedDebitNotes = usedDebitNotes;
	}

	public String getPayableAmountFinal() {
		return payableAmountFinal;
	}

	public void setPayableAmountFinal(String payableAmountFinal) {
		this.payableAmountFinal = payableAmountFinal;
	}

	public String getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getUsedCreditNotes() {
		return usedCreditNotes;
	}

	public void setUsedCreditNotes(String usedCreditNotes) {
		this.usedCreditNotes = usedCreditNotes;
	}

	public String getReceivableAmountFinal() {
		return receivableAmountFinal;
	}

	public void setReceivableAmountFinal(String receivableAmountFinal) {
		this.receivableAmountFinal = receivableAmountFinal;
	}

	public String getMethodOfAdj() {
		return methodOfAdj;
	}

	public void setMethodOfAdj(String methodOfAdj) {
		this.methodOfAdj = methodOfAdj;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getBankSelectPayment() {
		return bankSelectPayment;
	}

	public void setBankSelectPayment(String bankSelectPayment) {
		this.bankSelectPayment = bankSelectPayment;
	}

	
	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapperObj.writeValueAsString(this);
		} catch (IOException ex) {

			jsonStr = ex.toString();
		}
		return jsonStr;
	}
}
