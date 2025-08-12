package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryPoModel {

	private String poId;
	private String reqId;
	private String desc;
	private String reqType;
	private String reqPrior;
	private String location;
	private String locationId;
	private String costCenter;
	private String costCenterId;
	private String itemName;
	private String itemId;
	private String VendorName;
	private String vendorId;
	private String receiveDate;
	private String startDate;
	private String expireDate;
	private Double totalMaxAmount;
	private String paymentTerm;
	private String approveStatus;
	private String createdBy;
	private String createdOn;
	private String approvedDate;
	private String completeDate;
	private String onholdDate;
	private String legalTerm;
	private String legalTermDesc;
	private String holdStatus;
	private String moduleId;
	private String componentId;
	private String subComponentId;
	

	private List<InventoryProductModel> productList;
	private String OrganizationName;
	private String OrganizationDivision;

	public InventoryPoModel() {
		super();
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqPrior() {
		return reqPrior;
	}

	public void setReqPrior(String reqPrior) {
		this.reqPrior = reqPrior;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getVendorName() {
		return VendorName;
	}

	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public Double getTotalMaxAmount() {
		return totalMaxAmount;
	}

	public void setTotalMaxAmount(Double totalMaxAmount) {
		this.totalMaxAmount = totalMaxAmount;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
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

	public String getLegalTerm() {
		return legalTerm;
	}

	public void setLegalTerm(String legalTerm) {
		this.legalTerm = legalTerm;
	}

	public String getLegalTermDesc() {
		return legalTermDesc;
	}

	public void setLegalTermDesc(String legalTermDesc) {
		this.legalTermDesc = legalTermDesc;
	}

	public List<InventoryProductModel> getProductList() {
		return productList;
	}

	public void setProductList(List<InventoryProductModel> productList) {
		this.productList = productList;
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

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
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
