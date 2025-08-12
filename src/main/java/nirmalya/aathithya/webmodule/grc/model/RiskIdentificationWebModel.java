package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RiskIdentificationWebModel {

	private String identificationId;
	private String projectId;
	private String projectName;
	private String identificationApplyDate;
	private String desc;
	private String riskId;
	private String riskName;
	private String category;
	private String source;
	private String location;
	private String state;
	private String pPin;
	private String pIncharge;
	private String cName;
	private String cAddress;
	private String cState;
	private String cPin;
	private String email;
	private String mobile;
	private String remark;
	private String status;
	private String createdOn;
	private String createdBy;
	private String creationDate;
	private String OrganizationName;
	private String OrganizationDivision;
	
	
	public String getIdentificationId() {
		return identificationId;
	}


	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getIdentificationApplyDate() {
		return identificationApplyDate;
	}


	public void setIdentificationApplyDate(String identificationApplyDate) {
		this.identificationApplyDate = identificationApplyDate;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getRiskId() {
		return riskId;
	}


	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}


	public String getRiskName() {
		return riskName;
	}


	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getpPin() {
		return pPin;
	}


	public void setpPin(String pPin) {
		this.pPin = pPin;
	}


	public String getpIncharge() {
		return pIncharge;
	}


	public void setpIncharge(String pIncharge) {
		this.pIncharge = pIncharge;
	}


	public String getcName() {
		return cName;
	}


	public void setcName(String cName) {
		this.cName = cName;
	}


	public String getcAddress() {
		return cAddress;
	}


	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}


	public String getcState() {
		return cState;
	}


	public void setcState(String cState) {
		this.cState = cState;
	}


	public String getcPin() {
		return cPin;
	}


	public void setcPin(String cPin) {
		this.cPin = cPin;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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
