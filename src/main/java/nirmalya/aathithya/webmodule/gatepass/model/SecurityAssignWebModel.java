package nirmalya.aathithya.webmodule.gatepass.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityAssignWebModel {
	private String securityAssignId;
	private String desc;
	private String assignDate;
	private String postGateTypeId;
	private String postGateType;
	private String securityNameId;
    private String securityName;
    private String type;
	private String inTime;
    private String outTime;
    private String mobile;
    private String shift;
	private Integer slNo;
	private String createdBy;
	private String createdOn;
	private String updatedon;
	private String updatedBy;
	private String OrganizationName; 
	private String OrganizationDivision;
	
	private String addManPower; 
	
	
	public String getSecurityAssignId() {
		return securityAssignId;
	}



	public void setSecurityAssignId(String securityAssignId) {
		this.securityAssignId = securityAssignId;
	}



	public String getDesc() {
		return desc;
	}



	public void setDesc(String desc) {
		this.desc = desc;
	}



	public String getAssignDate() {
		return assignDate;
	}



	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}



	public String getPostGateTypeId() {
		return postGateTypeId;
	}



	public void setPostGateTypeId(String postGateTypeId) {
		this.postGateTypeId = postGateTypeId;
	}



	public String getPostGateType() {
		return postGateType;
	}



	public void setPostGateType(String postGateType) {
		this.postGateType = postGateType;
	}



	public String getSecurityNameId() {
		return securityNameId;
	}



	public void setSecurityNameId(String securityNameId) {
		this.securityNameId = securityNameId;
	}



	public String getSecurityName() {
		return securityName;
	}



	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}



	public String getInTime() {
		return inTime;
	}



	public void setInTime(String inTime) {
		this.inTime = inTime;
	}



	public String getOutTime() {
		return outTime;
	}



	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}



	public String getShift() {
		return shift;
	}



	public void setShift(String shift) {
		this.shift = shift;
	}



	public Integer getSlNo() {
		return slNo;
	}



	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
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



	public String getUpdatedon() {
		return updatedon;
	}



	public void setUpdatedon(String updatedon) {
		this.updatedon = updatedon;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getAddManPower() {
		return addManPower;
	}



	public void setAddManPower(String addManPower) {
		this.addManPower = addManPower;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
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
