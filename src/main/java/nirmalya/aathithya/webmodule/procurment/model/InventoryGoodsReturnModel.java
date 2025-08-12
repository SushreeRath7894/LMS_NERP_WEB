package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryGoodsReturnModel {

	private String returnNoteId;
	private String noteId;
	private String invDate;
	private String invNo;
	private String poId;
	private String poDate;
	private String dispatchDate;
	private String deliveryMethodId;
	private String deliveryMethodName;
	private String trackingNo;
	private String estDeliveryDate;
	private Double outStandQty;
	private String status;
	private String createdBy;
	private String createdOn;
	private String activeDate;
	private String completeDate;
	private String onholdDate;

	private String moduleId;
	private String componentId;
	private String subComponentId;

	private Double totalInvQty;
	private Double totalDnQty;
	private Double totalReceiveQty;
	private Double totalNotReceiveQty;
	private Double totalOutStandingQty;
	private Double totalReturnQty;
	private String deliveryNote;
	List<InventoryProductModel> productList;
	private List<InventoryVendorDocumentModel> documentList;
	private String OrganizationName;
	private String OrganizationDivision;

	public InventoryGoodsReturnModel() {
		super();
	}

	public String getReturnNoteId() {
		return returnNoteId;
	}

	public void setReturnNoteId(String returnNoteId) {
		this.returnNoteId = returnNoteId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
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

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getDeliveryMethodId() {
		return deliveryMethodId;
	}

	public void setDeliveryMethodId(String deliveryMethodId) {
		this.deliveryMethodId = deliveryMethodId;
	}

	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}

	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getEstDeliveryDate() {
		return estDeliveryDate;
	}

	public void setEstDeliveryDate(String estDeliveryDate) {
		this.estDeliveryDate = estDeliveryDate;
	}

	public Double getOutStandQty() {
		return outStandQty;
	}

	public void setOutStandQty(Double outStandQty) {
		this.outStandQty = outStandQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Double getTotalInvQty() {
		return totalInvQty;
	}

	public void setTotalInvQty(Double totalInvQty) {
		this.totalInvQty = totalInvQty;
	}

	public Double getTotalDnQty() {
		return totalDnQty;
	}

	public void setTotalDnQty(Double totalDnQty) {
		this.totalDnQty = totalDnQty;
	}

	public Double getTotalReceiveQty() {
		return totalReceiveQty;
	}

	public void setTotalReceiveQty(Double totalReceiveQty) {
		this.totalReceiveQty = totalReceiveQty;
	}

	public Double getTotalNotReceiveQty() {
		return totalNotReceiveQty;
	}

	public void setTotalNotReceiveQty(Double totalNotReceiveQty) {
		this.totalNotReceiveQty = totalNotReceiveQty;
	}

	public Double getTotalOutStandingQty() {
		return totalOutStandingQty;
	}

	public void setTotalOutStandingQty(Double totalOutStandingQty) {
		this.totalOutStandingQty = totalOutStandingQty;
	}

	public Double getTotalReturnQty() {
		return totalReturnQty;
	}

	public void setTotalReturnQty(Double totalReturnQty) {
		this.totalReturnQty = totalReturnQty;
	}

	public List<InventoryProductModel> getProductList() {
		return productList;
	}

	public void setProductList(List<InventoryProductModel> productList) {
		this.productList = productList;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
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
