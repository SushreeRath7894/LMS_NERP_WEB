package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class SalesReturnModel {
	private String quotationId;
	private String salesOrder;
	private String saleReturnId;
	private String custId;
	private String custName;
	private String salesOrderId;
	private String qutUpdatedOn;
	private String resonForReturn;
	private String carrierId;
	private String qutActive; 
	private String qutDescription;
	private String qutCreatedBy;
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
	private Boolean taxType;
	private Double taxableAmt;
	private String unit;
	private String unitName;
	private String hsnCode;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private Double adjustment;
	private String tcsId;
	private Double tcsAmount;
	private String tcs;
	private String terms;
	private String returnDate;
	private String organization;
	private String orgDivision;
	private String invoiceId;
	private String poId;
	private String salesInvoiceId;
	private String approveStatus;
	private Double receivedQut;
	private Double pendingQut;
	private Double rtnQut;
	private Double debitNoteAmt;
	private String debitNoteId;
	List<InventoryVendorDocumentModel> documentList;
	public SalesReturnModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public String getSaleReturnId() {
		return saleReturnId;
	}
	public void setSaleReturnId(String saleReturnId) {
		this.saleReturnId = saleReturnId;
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
	public String getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}
	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
	}
	public String getResonForReturn() {
		return resonForReturn;
	}
	public void setResonForReturn(String resonForReturn) {
		this.resonForReturn = resonForReturn;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
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
	public Double getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}
	public String getTcsId() {
		return tcsId;
	}
	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}
	public Double getTcsAmount() {
		return tcsAmount;
	}
	public void setTcsAmount(Double tcsAmount) {
		this.tcsAmount = tcsAmount;
	}
	public String getTcs() {
		return tcs;
	}
	public void setTcs(String tcs) {
		this.tcs = tcs;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
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
	
	
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	
	
	public Double getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	
	
	public String getSalesInvoiceId() {
		return salesInvoiceId;
	}
	public void setSalesInvoiceId(String salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
	}
	
	public Double getReceivedQut() {
		return receivedQut;
	}
	public void setReceivedQut(Double receivedQut) {
		this.receivedQut = receivedQut;
	}
	public Double getPendingQut() {
		return pendingQut;
	}
	public void setPendingQut(Double pendingQut) {
		this.pendingQut = pendingQut;
	}
	public Double getRtnQut() {
		return rtnQut;
	}
	public void setRtnQut(Double rtnQut) {
		this.rtnQut = rtnQut;
	}
	
	
	public Double getDebitNoteAmt() {
		return debitNoteAmt;
	}
	public void setDebitNoteAmt(Double debitNoteAmt) {
		this.debitNoteAmt = debitNoteAmt;
	}
	public String getDebitNoteId() {
		return debitNoteId;
	}
	public void setDebitNoteId(String debitNoteId) {
		this.debitNoteId = debitNoteId;
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
