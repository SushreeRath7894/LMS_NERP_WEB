package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectSubContractorWebModel {

	private String contractorId;
	private String contractorName;
	private String email;
	private String phone;
	private String contractorType;
	private String gstIn;
	private String panId;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String pinId;
	private String status;
	private String SubContractorId;
	private String projectId;
	private String projectName;
	private String task;
	private String scopeoftheWork;
	private String durationoftheWork;
	private String licenseVerified;
	private String statementofintentRecieved;
	private String requesttosubletRecieved;
	private String scheduleofworkRecieved;
	private String drawingsProvided;
	private String punchlistComplete;
	private String dateofnoticetoproceedIssued;
	private String datecontractExecuted;
	private String dateinsuranceRecieved;
	private String otherrequiredDocumentation;
	private String createdBy;
	private String CreatedOn;
	private String UpdatedBy;
	private String UpdatedOn;
	private String OrganizationName;
	private String OrganizationDivision;

	public ProjectSubContractorWebModel() {
		super();
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContractorType() {
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}

	public String getPanId() {
		return panId;
	}

	public void setPanId(String panId) {
		this.panId = panId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubContractorId() {
		return SubContractorId;
	}

	public void setSubContractorId(String subContractorId) {
		SubContractorId = subContractorId;
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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getScopeoftheWork() {
		return scopeoftheWork;
	}

	public void setScopeoftheWork(String scopeoftheWork) {
		this.scopeoftheWork = scopeoftheWork;
	}

	public String getDurationoftheWork() {
		return durationoftheWork;
	}

	public void setDurationoftheWork(String durationoftheWork) {
		this.durationoftheWork = durationoftheWork;
	}

	public String getLicenseVerified() {
		return licenseVerified;
	}

	public void setLicenseVerified(String licenseVerified) {
		this.licenseVerified = licenseVerified;
	}

	public String getStatementofintentRecieved() {
		return statementofintentRecieved;
	}

	public void setStatementofintentRecieved(String statementofintentRecieved) {
		this.statementofintentRecieved = statementofintentRecieved;
	}

	public String getRequesttosubletRecieved() {
		return requesttosubletRecieved;
	}

	public void setRequesttosubletRecieved(String requesttosubletRecieved) {
		this.requesttosubletRecieved = requesttosubletRecieved;
	}

	public String getScheduleofworkRecieved() {
		return scheduleofworkRecieved;
	}

	public void setScheduleofworkRecieved(String scheduleofworkRecieved) {
		this.scheduleofworkRecieved = scheduleofworkRecieved;
	}

	public String getDrawingsProvided() {
		return drawingsProvided;
	}

	public void setDrawingsProvided(String drawingsProvided) {
		this.drawingsProvided = drawingsProvided;
	}

	public String getPunchlistComplete() {
		return punchlistComplete;
	}

	public void setPunchlistComplete(String punchlistComplete) {
		this.punchlistComplete = punchlistComplete;
	}

	public String getDateofnoticetoproceedIssued() {
		return dateofnoticetoproceedIssued;
	}

	public void setDateofnoticetoproceedIssued(String dateofnoticetoproceedIssued) {
		this.dateofnoticetoproceedIssued = dateofnoticetoproceedIssued;
	}

	public String getDatecontractExecuted() {
		return datecontractExecuted;
	}

	public void setDatecontractExecuted(String datecontractExecuted) {
		this.datecontractExecuted = datecontractExecuted;
	}

	public String getDateinsuranceRecieved() {
		return dateinsuranceRecieved;
	}

	public void setDateinsuranceRecieved(String dateinsuranceRecieved) {
		this.dateinsuranceRecieved = dateinsuranceRecieved;
	}

	public String getOtherrequiredDocumentation() {
		return otherrequiredDocumentation;
	}

	public void setOtherrequiredDocumentation(String otherrequiredDocumentation) {
		this.otherrequiredDocumentation = otherrequiredDocumentation;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return UpdatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
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
