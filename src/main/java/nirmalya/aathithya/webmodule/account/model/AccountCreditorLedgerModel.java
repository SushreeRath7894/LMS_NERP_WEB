package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountCreditorLedgerModel {

	private String transactionDate;
	private String transactionMode;
	private String transactionId;
	private String transactionAmnt;
	private String transactionDebitCredit;
	private String transactionVchType;
	private String totalBillAmnt;
	private String totalPaidAmnt;
	private String totalOutstandingAmnt;
	private String vendorName;
	private String vendorAddress;
	private String vendorMobile;
	private String vendorId;
	private String transactionDebitAmt;
	private String transactionCreditAmt;
	private String orgName;
	private String orgDiv;
	private String orgGst;
	private String orgAddress;
	private String paymentId;
	private String debitToVendor;
	private String creditToVendor;

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionAmnt() {
		return transactionAmnt;
	}

	public void setTransactionAmnt(String transactionAmnt) {
		this.transactionAmnt = transactionAmnt;
	}

	public String getTransactionDebitCredit() {
		return transactionDebitCredit;
	}

	public void setTransactionDebitCredit(String transactionDebitCredit) {
		this.transactionDebitCredit = transactionDebitCredit;
	}

	public String getTransactionVchType() {
		return transactionVchType;
	}

	public void setTransactionVchType(String transactionVchType) {
		this.transactionVchType = transactionVchType;
	}

	public String getTotalBillAmnt() {
		return totalBillAmnt;
	}

	public void setTotalBillAmnt(String totalBillAmnt) {
		this.totalBillAmnt = totalBillAmnt;
	}

	public String getTotalPaidAmnt() {
		return totalPaidAmnt;
	}

	public void setTotalPaidAmnt(String totalPaidAmnt) {
		this.totalPaidAmnt = totalPaidAmnt;
	}

	public String getTotalOutstandingAmnt() {
		return totalOutstandingAmnt;
	}

	public void setTotalOutstandingAmnt(String totalOutstandingAmnt) {
		this.totalOutstandingAmnt = totalOutstandingAmnt;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public String getVendorMobile() {
		return vendorMobile;
	}

	public void setVendorMobile(String vendorMobile) {
		this.vendorMobile = vendorMobile;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getTransactionDebitAmt() {
		return transactionDebitAmt;
	}

	public void setTransactionDebitAmt(String transactionDebitAmt) {
		this.transactionDebitAmt = transactionDebitAmt;
	}

	public String getTransactionCreditAmt() {
		return transactionCreditAmt;
	}

	public void setTransactionCreditAmt(String transactionCreditAmt) {
		this.transactionCreditAmt = transactionCreditAmt;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getOrgGst() {
		return orgGst;
	}

	public void setOrgGst(String orgGst) {
		this.orgGst = orgGst;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getDebitToVendor() {
		return debitToVendor;
	}

	public void setDebitToVendor(String debitToVendor) {
		this.debitToVendor = debitToVendor;
	}

	public String getCreditToVendor() {
		return creditToVendor;
	}

	public void setCreditToVendor(String creditToVendor) {
		this.creditToVendor = creditToVendor;
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
