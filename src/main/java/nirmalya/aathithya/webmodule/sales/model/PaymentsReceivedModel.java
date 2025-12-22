package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class PaymentsReceivedModel {
	private String quotationId;
	private String depositTo;
	private String depositToId;
	private String accountName;
	private String paymentId;
	private String custId;
	private String custName;
	private String saleInvoiceId;
	private Double amountReceived;
	private Double bankCharges;
	private String payment;
	private String paymentMode;
	
	private String reference;
	private String internalNotes;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String organization;
	private String orgDivision;
	private String invoiceDate;
	private String invoiceAmount;
	private Double fullyPaymentAmount;
	private String checkBox;
	List<InventoryVendorDocumentModel> documentList;
	
	
	public String getDepositToId() {
		return depositToId;
	}



	public void setDepositToId(String depositToId) {
		this.depositToId = depositToId;
	}



	public String getQuotationId() {
		return quotationId;
	}



	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public String getPaymentId() {
		return paymentId;
	}



	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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



	public String getSaleInvoiceId() {
		return saleInvoiceId;
	}



	public void setSaleInvoiceId(String saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
	}



	public Double getAmountReceived() {
		return amountReceived;
	}



	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}



	public Double getBankCharges() {
		return bankCharges;
	}



	public void setBankCharges(Double bankCharges) {
		this.bankCharges = bankCharges;
	}



	public String getPayment() {
		return payment;
	}



	public void setPayment(String payment) {
		this.payment = payment;
	}



	public String getPaymentMode() {
		return paymentMode;
	}



	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}



	public String getDepositTo() {
		return depositTo;
	}



	public void setDepositTo(String depositTo) {
		this.depositTo = depositTo;
	}



	public String getReference() {
		return reference;
	}



	public void setReference(String reference) {
		this.reference = reference;
	}



	public String getInternalNotes() {
		return internalNotes;
	}



	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}



	public String getQutCreatedBy() {
		return qutCreatedBy;
	}



	public void setQutCreatedBy(String qutCreatedBy) {
		this.qutCreatedBy = qutCreatedBy;
	}



	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}



	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
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



	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}



	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}



	public String getInvoiceDate() {
		return invoiceDate;
	}



	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}



	public String getInvoiceAmount() {
		return invoiceAmount;
	}



	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}



	

	public Double getFullyPaymentAmount() {
		return fullyPaymentAmount;
	}



	public void setFullyPaymentAmount(Double fullyPaymentAmount) {
		this.fullyPaymentAmount = fullyPaymentAmount;
	}



	public String getCheckBox() {
		return checkBox;
	}



	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
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
