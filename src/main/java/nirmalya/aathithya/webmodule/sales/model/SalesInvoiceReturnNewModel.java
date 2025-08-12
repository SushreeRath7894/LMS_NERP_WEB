package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesInvoiceReturnNewModel {
	private String salesInreturn;
	private String salOrderId;
	private String saleInvoice;
	private String saleInvoiceId;
	private String salesOrderId;
	private String quotationId;
	private String qutActive;
	private String qutDescription;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private Double quantity;
	private Double quantitynew;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private Boolean taxType;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String salesActive;
	
	
	public String getSalesInreturn() {
		return salesInreturn;
	}


	public void setSalesInreturn(String salesInreturn) {
		this.salesInreturn = salesInreturn;
	}


	public String getSaleInvoice() {
		return saleInvoice;
	}


	public void setSaleInvoice(String saleInvoice) {
		this.saleInvoice = saleInvoice;
	}


	public String getSaleInvoiceId() {
		return saleInvoiceId;
	}


	public void setSaleInvoiceId(String saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
	}


	

	public String getSalOrderId() {
		return salOrderId;
	}


	public void setSalOrderId(String salOrderId) {
		this.salOrderId = salOrderId;
	}


	public String getSalesOrderId() {
		return salesOrderId;
	}


	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}


	public String getQuotationId() {
		return quotationId;
	}


	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}


	public String getQutActive() {
		return qutActive;
	}


	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
	}


	public String getQutDescription() {
		return qutDescription;
	}


	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
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


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public Integer getSlNo() {
		return slNo;
	}


	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
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


	public Double getQuantitynew() {
		return quantitynew;
	}


	public void setQuantitynew(Double quantitynew) {
		this.quantitynew = quantitynew;
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


	public Boolean getTaxType() {
		return taxType;
	}


	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
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


	public String getSalesActive() {
		return salesActive;
	}


	public void setSalesActive(String salesActive) {
		this.salesActive = salesActive;
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
