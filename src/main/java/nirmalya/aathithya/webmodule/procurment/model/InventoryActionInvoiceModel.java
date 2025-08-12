package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryActionInvoiceModel {

	private String invId;
	private String invDate;
	private String invNo;
	private String poId;
	private String itemId;
	private String itemName;
	private Double invAmount;
	private String approveStatus;
	private String dueDate;
	private String paymentStatus;
	private String createdBy;
	private String createdOn;
	private String paymentTerm;
	private String vendorId;
	private String vendorLocId;
	private String vendorContactId;
	private String companyContactId;
	private Double totalValue;
	private String paymentDueIn;
	private String comments;
	private String payemtDueDate;
	private String moduleId;
	private String componentId;
	private String subComponentId;
	private String activeDate;
	private String completeDate;
	private String onholdDate;
	List<InventoryProductModel> productList;
	List<InventoryVendorDocumentModel> documentList;
	private String isSaveByVendor;
	private String deliverNoteStatus;
	private String OrganizationName;
	private String OrganizationDivision;
	private Double subTotal;
	private Double sgst;
	private Double cgst;
	private Double igst;
	private Double adjustment;
	private String tcs;
	private Double tcsAmt;
	public InventoryActionInvoiceModel() {
		super();
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getInvAmount() {
		return invAmount;
	}

	public void setInvAmount(Double invAmount) {
		this.invAmount = invAmount;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorLocId() {
		return vendorLocId;
	}

	public void setVendorLocId(String vendorLocId) {
		this.vendorLocId = vendorLocId;
	}

	public String getVendorContactId() {
		return vendorContactId;
	}

	public void setVendorContactId(String vendorContactId) {
		this.vendorContactId = vendorContactId;
	}

	public String getCompanyContactId() {
		return companyContactId;
	}

	public void setCompanyContactId(String companyContactId) {
		this.companyContactId = companyContactId;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public String getPaymentDueIn() {
		return paymentDueIn;
	}

	public void setPaymentDueIn(String paymentDueIn) {
		this.paymentDueIn = paymentDueIn;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<InventoryProductModel> getProductList() {
		return productList;
	}

	public void setProductList(List<InventoryProductModel> productList) {
		this.productList = productList;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getPayemtDueDate() {
		return payemtDueDate;
	}

	public void setPayemtDueDate(String payemtDueDate) {
		this.payemtDueDate = payemtDueDate;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getSubComponentId() {
		return subComponentId;
	}

	public void setSubComponentId(String subComponentId) {
		this.subComponentId = subComponentId;
	}

	public String getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public String getOnholdDate() {
		return onholdDate;
	}

	public void setOnholdDate(String onholdDate) {
		this.onholdDate = onholdDate;
	}

	public String getIsSaveByVendor() {
		return isSaveByVendor;
	}

	public void setIsSaveByVendor(String isSaveByVendor) {
		this.isSaveByVendor = isSaveByVendor;
	}

	public String getDeliverNoteStatus() {
		return deliverNoteStatus;
	}

	public void setDeliverNoteStatus(String deliverNoteStatus) {
		this.deliverNoteStatus = deliverNoteStatus;
	}
	

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return OrganizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
	}
	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public String getTcs() {
		return tcs;
	}

	public void setTcs(String tcs) {
		this.tcs = tcs;
	}

	public Double getTcsAmt() {
		return tcsAmt;
	}

	public void setTcsAmt(Double tcsAmt) {
		this.tcsAmt = tcsAmt;
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
