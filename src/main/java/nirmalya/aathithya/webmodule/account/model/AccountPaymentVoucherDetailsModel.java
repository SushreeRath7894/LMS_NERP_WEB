package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountPaymentVoucherDetailsModel {

	
	private String journalVoucher;
    private Double totalAmount;
    private Integer currentStageNo;
    private String approveStatus;
    private String approveStatusName;
    private String createdOn;
    
    @JsonProperty("TRANSACTION_DATE")
    private String transactiondate;
    
    private String description;
    private String vendorName;
    private String invoiceId;
    private String paymentId;
    private String debitAccountName;
    private String debitAccountLedgerid;
    private String creditAccountName;
    private String creditLedgerId;
    private Integer transactionOrder;
    private String voucherType;
    private AccountPaymentVoucherRowDetails voucherDetails;
    private String createrName;
    private String createrId;
    private String totalPayAmount;
    private String voucherTypeName;
    private String ledgerAddress;
    
    
	public String getJournalVoucher() {
		return journalVoucher;
	}
	public void setJournalVoucher(String journalVoucher) {
		this.journalVoucher = journalVoucher;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getCurrentStageNo() {
		return currentStageNo;
	}
	public void setCurrentStageNo(Integer currentStageNo) {
		this.currentStageNo = currentStageNo;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getApproveStatusName() {
		return approveStatusName;
	}
	public void setApproveStatusName(String approveStatusName) {
		this.approveStatusName = approveStatusName;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getTRANSACTION_DATE() {
		return transactiondate;
	}
	public void setTRANSACTION_DATE(String tRANSACTION_DATE) {
		transactiondate = tRANSACTION_DATE;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getDebitAccountName() {
		return debitAccountName;
	}
	public void setDebitAccountName(String debitAccountName) {
		this.debitAccountName = debitAccountName;
	}
	public String getCreditAccountName() {
		return creditAccountName;
	}
	public void setCreditAccountName(String creditAccountName) {
		this.creditAccountName = creditAccountName;
	}
	public Integer getTransactionOrder() {
		return transactionOrder;
	}
	public void setTransactionOrder(Integer transactionOrder) {
		this.transactionOrder = transactionOrder;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public AccountPaymentVoucherRowDetails getVoucherDetails() {
		return voucherDetails;
	}
	public void setVoucherDetails(AccountPaymentVoucherRowDetails voucherDetails) {
		this.voucherDetails = voucherDetails;
	}
    
    
    
	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public String getDebitAccountLedgerid() {
		return debitAccountLedgerid;
	}
	public void setDebitAccountLedgerid(String debitAccountLedgerid) {
		this.debitAccountLedgerid = debitAccountLedgerid;
	}
	public String getCreditLedgerId() {
		return creditLedgerId;
	}
	public void setCreditLedgerId(String creditLedgerId) {
		this.creditLedgerId = creditLedgerId;
	}
	
	
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	
	public String getTotalPayAmount() {
		return totalPayAmount;
	}
	public void setTotalPayAmount(String totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
	public String getVoucherTypeName() {
		return voucherTypeName;
	}
	public void setVoucherTypeName(String voucherTypeName) {
		this.voucherTypeName = voucherTypeName;
	}
	public String getLedgerAddress() {
		return ledgerAddress;
	}
	public void setLedgerAddress(String ledgerAddress) {
		this.ledgerAddress = ledgerAddress;
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
