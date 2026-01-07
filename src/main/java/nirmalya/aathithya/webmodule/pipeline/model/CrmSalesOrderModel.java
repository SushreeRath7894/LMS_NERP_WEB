package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmSalesOrderModel {
	private String soId;
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
	private String poDate;
	private String filePoSale;
	private String salesOrder;
	private String sOrderDate;
	
	
	private String salesOwner;
	private String dealId;
	private String salesDealName;
	private String salesSubject;
	private String purchaseOrder;
	private String customerNo;
	private String dueDate;
	private String quoteId;
	private String quoteName;
	private String contactId;
	private String contactName;
	
	private String pending;
	private String exciseDuty;
	private String salesCarrier;
	private String salesOrderStatus;
	private String salesCommission;
	private String quoteAccountId;
	private String quoteAccountName;
	
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
	
	
	
	
	public String getDealId() {
		return dealId;
	}



	public void setDealId(String dealId) {
		this.dealId = dealId;
	}



	public String getQuoteId() {
		return quoteId;
	}



	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}



	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}



	public String getQuoteAccountId() {
		return quoteAccountId;
	}



	public void setQuoteAccountId(String quoteAccountId) {
		this.quoteAccountId = quoteAccountId;
	}





	public String getQuoteAccountName() {
		return quoteAccountName;
	}



	public void setQuoteAccountName(String quoteAccountName) {
		this.quoteAccountName = quoteAccountName;
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



	public String getPoDate() {
		return poDate;
	}



	public void setPoDate(String poDate) {
		this.poDate = poDate;
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

	
	


	public String getSoId() {
		return soId;
	}



	public void setSoId(String soId) {
		this.soId = soId;
	}



	public String getSalesOwner() {
		return salesOwner;
	}



	public void setSalesOwner(String salesOwner) {
		this.salesOwner = salesOwner;
	}



	public String getSalesDealName() {
		return salesDealName;
	}



	public void setSalesDealName(String salesDealName) {
		this.salesDealName = salesDealName;
	}



	public String getSalesSubject() {
		return salesSubject;
	}



	public void setSalesSubject(String salesSubject) {
		this.salesSubject = salesSubject;
	}



	public String getPurchaseOrder() {
		return purchaseOrder;
	}



	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}



	public String getCustomerNo() {
		return customerNo;
	}



	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}



	public String getDueDate() {
		return dueDate;
	}



	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}



	public String getQuoteName() {
		return quoteName;
	}



	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
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



	public String getPending() {
		return pending;
	}



	public void setPending(String pending) {
		this.pending = pending;
	}



	public String getExciseDuty() {
		return exciseDuty;
	}



	public void setExciseDuty(String exciseDuty) {
		this.exciseDuty = exciseDuty;
	}



	public String getSalesCarrier() {
		return salesCarrier;
	}



	public void setSalesCarrier(String salesCarrier) {
		this.salesCarrier = salesCarrier;
	}



	public String getSalesOrderStatus() {
		return salesOrderStatus;
	}



	public void setSalesOrderStatus(String salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
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
