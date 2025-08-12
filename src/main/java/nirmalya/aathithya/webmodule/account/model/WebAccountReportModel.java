package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebAccountReportModel {
	private String transactionDate;
	private String vendorName;
	private String transactionVchType;
	private String transactionId;
	private String transactionDebitAmt;
	private String transactionCreditAmt;
	private String ledgerId;
	private String ledgerName;
	private String voucherType;
	private String voucherId;
	private String debitAmount;
	private String creditAmount;
	private String voucherDate;
	private String orgDiv;
	private String orgGst;
	private String orgAddress;
	
	public WebAccountReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(String ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
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
	 
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getTransactionVchType() {
		return transactionVchType;
	}

	public void setTransactionVchType(String transactionVchType) {
		this.transactionVchType = transactionVchType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
