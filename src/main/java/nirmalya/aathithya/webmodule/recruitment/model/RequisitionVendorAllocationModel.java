package nirmalya.aathithya.webmodule.recruitment.model;

import java.util.List;

public class RequisitionVendorAllocationModel {

	private List<String> requisitionId;
	private List<String> vendorId;
	private String createdOn;
	private String createdBy;
	private String updatedOn;
	private String updatedBy;
	
	private String orgName;
	private String orgDiv;
	
	public RequisitionVendorAllocationModel() {
		super();
	}


	public List<String> getRequisitionId() {
		return requisitionId;
	}


	public void setRequisitionId(List<String> requisitionId) {
		this.requisitionId = requisitionId;
	}


	public List<String> getVendorId() {
		return vendorId;
	}


	public void setVendorId(List<String> vendorId) {
		this.vendorId = vendorId;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgDiv() {
		return orgDiv;
	}


	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	
}
