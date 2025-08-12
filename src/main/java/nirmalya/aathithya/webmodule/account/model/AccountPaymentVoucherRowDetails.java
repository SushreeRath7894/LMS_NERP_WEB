package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountPaymentVoucherRowDetails {

	@JsonProperty("Supporting_docNo")
	private String supportingDocNo;

	@JsonProperty("Supplier_Invoice_No")
	private String supplierInvoiceNo;

	@JsonProperty("DueDate")
	private String dueDate;

	@JsonProperty("costcenter")
	private String costcenter;

	@JsonProperty("OriginalVoucherAmount")
	private String originalVoucherAmount;

	@JsonProperty("TDSDeducted")
	private String tdsDeducted;

	@JsonProperty("AdvancePaid")
	private String advancePaid;

	@JsonProperty("BalanceAmount")
	private String balanceAmount;

	@JsonProperty("AmountINR")
	private String amountINR;

	@JsonProperty("AmountInWords")
	private String amountInWords;

	public String getSupportingDocNo() {
		return supportingDocNo;
	}

	public void setSupportingDocNo(String supportingDocNo) {
		this.supportingDocNo = supportingDocNo;
	}

	public String getSupplierInvoiceNo() {
		return supplierInvoiceNo;
	}

	public void setSupplierInvoiceNo(String supplierInvoiceNo) {
		this.supplierInvoiceNo = supplierInvoiceNo;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getOriginalVoucherAmount() {
		return originalVoucherAmount;
	}

	public void setOriginalVoucherAmount(String originalVoucherAmount) {
		this.originalVoucherAmount = originalVoucherAmount;
	}

	public String getTdsDeducted() {
		return tdsDeducted;
	}

	public void setTdsDeducted(String tdsDeducted) {
		this.tdsDeducted = tdsDeducted;
	}

	public String getAdvancePaid() {
		return advancePaid;
	}

	public void setAdvancePaid(String advancePaid) {
		this.advancePaid = advancePaid;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getAmountINR() {
		return amountINR;
	}

	public void setAmountINR(String amountINR) {
		this.amountINR = amountINR;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
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
