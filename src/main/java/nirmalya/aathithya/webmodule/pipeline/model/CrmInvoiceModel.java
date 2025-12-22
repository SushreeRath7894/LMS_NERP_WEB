package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmInvoiceModel {
	private String invoiceId;
	private String custGSTNo;
	private String qutValidDate; 
	private String qutDescription;
	private String qutActive; 
	private String qutCreatedBy;
	private String createdOn;
	private String qutUpdatedOn;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private Double quantity;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private String active;
	private Boolean taxType;
	private String qItmCode;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String poNo;
	private String filePoSale;
	private String salesOrder;
	private String sOrderDate;
	private String invoiceOwner;
	private String invoiceSalesOrder;
	private String subject;
	private String poNumber;
	private String invoiceDate;
	
	private String exciseDuty;
	private String dueDate;
	private String invoiceStatus;
	private String salesCommission;
	private String contactId;
	
	private String contactName;
	private String accountId;
	private String dealAccountName;
		
	private String billingStreet;
	private String shippingStreet;
	private String billingCity;
	private String shippingCity;
	private String billingCode;
	
	private String shippingCode;
	private String billingState;
	private String shippingState;
	private String billingCountry;
	private String shippingCountry;
	
	private String description;
	private String termCondition;


	public String getInvoiceId() {
		return invoiceId;
	}



	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}



	public String getInvoiceOwner() {
		return invoiceOwner;
	}



	public void setInvoiceOwner(String invoiceOwner) {
		this.invoiceOwner = invoiceOwner;
	}



	public String getInvoiceSalesOrder() {
		return invoiceSalesOrder;
	}



	public void setInvoiceSalesOrder(String invoiceSalesOrder) {
		this.invoiceSalesOrder = invoiceSalesOrder;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getInvoiceDate() {
		return invoiceDate;
	}



	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}



	public String getInvoiceStatus() {
		return invoiceStatus;
	}



	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}



	public String getAccountId() {
		return accountId;
	}



	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	public String getDealAccountName() {
		return dealAccountName;
	}



	public void setDealAccountName(String dealAccountName) {
		this.dealAccountName = dealAccountName;
	}



	public String getPoNumber() {
		return poNumber;
	}



	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}




	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public String getBillingStreet() {
		return billingStreet;
	}



	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}



	public String getShippingStreet() {
		return shippingStreet;
	}



	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}



	public String getBillingCity() {
		return billingCity;
	}



	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}



	public String getShippingCity() {
		return shippingCity;
	}



	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}



	public String getBillingCode() {
		return billingCode;
	}



	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}



	public String getShippingCode() {
		return shippingCode;
	}



	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}



	public String getBillingState() {
		return billingState;
	}



	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}



	public String getShippingState() {
		return shippingState;
	}



	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}



	public String getBillingCountry() {
		return billingCountry;
	}



	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}



	public String getShippingCountry() {
		return shippingCountry;
	}



	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getTermCondition() {
		return termCondition;
	}



	public void setTermCondition(String termCondition) {
		this.termCondition = termCondition;
	}



	public String getCustGSTNo() {
		return custGSTNo;
	}



	public void setCustGSTNo(String custGSTNo) {
		this.custGSTNo = custGSTNo;
	}



	public Integer getSlNo() {
		return slNo;
	}



	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}



	public String getItemId() {
		return itemId;
	}



	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	
	
	public String getSalesOrder() {
		return salesOrder;
	}



	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}



	public String getsOrderDate() {
		return sOrderDate;
	}



	public void setsOrderDate(String sOrderDate) {
		this.sOrderDate = sOrderDate;
	}


	public String getFilePoSale() {
		return filePoSale;
	}



	public void setFilePoSale(String filePoSale) {
		this.filePoSale = filePoSale;
	}



	public Double getItemIgst() {
		return itemIgst;
	}



	public void setItemIgst(Double itemIgst) {
		this.itemIgst = itemIgst;
	}



	public Double getItemCgst() {
		return itemCgst;
	}



	public void setItemCgst(Double itemCgst) {
		this.itemCgst = itemCgst;
	}



	public Double getItemSgst() {
		return itemSgst;
	}



	public void setItemSgst(Double itemSgst) {
		this.itemSgst = itemSgst;
	}



	public String getSku() {
		return sku;
	}



	public void setSku(String sku) {
		this.sku = sku;
	}



	public String getqItmCode() {
		return qItmCode;
	}



	public void setqItmCode(String qItmCode) {
		this.qItmCode = qItmCode;
	}



	public Boolean getTaxType() {
		return taxType;
	}



	public String getPoNo() {
		return poNo;
	}



	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}



	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}


	public String getQutValidDate() {
		return qutValidDate;
	}



	public void setQutValidDate(String qutValidDate) {
		this.qutValidDate = qutValidDate;
	}


	public String getQutDescription() {
		return qutDescription;
	}



	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
	}



	public String getQutActive() {
		return qutActive;
	}



	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
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



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public Double getQuantity() {
		return quantity;
	}



	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}



	public Double getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}



	public Double getDiscount() {
		return discount;
	}



	public void setDiscount(Double discount) {
		this.discount = discount;
	}



	public Double getGstRate() {
		return gstRate;
	}



	public void setGstRate(Double gstRate) {
		this.gstRate = gstRate;
	}

	public Double getLineTotal() {
		return lineTotal;
	}



	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}



	public Double getSubTotal() {
		return subTotal;
	}



	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}



	public Double getqIGST() {
		return qIGST;
	}



	public void setqIGST(Double qIGST) {
		this.qIGST = qIGST;
	}



	public Double getqCGST() {
		return qCGST;
	}



	public void setqCGST(Double qCGST) {
		this.qCGST = qCGST;
	}



	public Double getqSGST() {
		return qSGST;
	}



	public void setqSGST(Double qSGST) {
		this.qSGST = qSGST;
	}



	public Double getGrandTotal() {
		return grandTotal;
	}



	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}



	public String getActive() {
		return active;
	}



	public void setActive(String active) {
		this.active = active;
	}

	
	public String getDueDate() {
		return dueDate;
	}



	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getContactId() {
		return contactId;
	}



	public void setContactId(String contactId) {
		this.contactId = contactId;
	}



	public String getContactName() {
		return contactName;
	}



	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getExciseDuty() {
		return exciseDuty;
	}



	public void setExciseDuty(String exciseDuty) {
		this.exciseDuty = exciseDuty;
	}

	
	public String getSalesCommission() {
		return salesCommission;
	}



	public void setSalesCommission(String salesCommission) {
		this.salesCommission = salesCommission;
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
