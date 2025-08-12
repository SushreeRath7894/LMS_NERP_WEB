package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ComplianceManagementModel {

	private String  complianceNo;
	private String  complianceId;
	private String  complianceName;
	private String  fileAttach;
	private String  purpose;
	private String  fromdate;
	private String  toDate;
	private String  status;
	private String createdBy;
	private String OrganizationName;
	private String OrganizationDivision;
	private String comDescription;
	private String projectId;
	private String noOfPeoples;
	
	
	
	public ComplianceManagementModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getComplianceNo() {
		return complianceNo;
	}
	public void setComplianceNo(String complianceNo) {
		this.complianceNo = complianceNo;
	}
	public String getComplianceId() {
		return complianceId;
	}
	public void setComplianceId(String complianceId) {
		this.complianceId = complianceId;
	}
	public String getComplianceName() {
		return complianceName;
	}
	public void setComplianceName(String complianceName) {
		this.complianceName = complianceName;
	}
	public String getFileAttach() {
		return fileAttach;
	}
	public void setFileAttach(String fileAttach) {
		this.fileAttach = fileAttach;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	

	public String getComDescription() {
		return comDescription;
	}
	public void setComDescription(String comDescription) {
		this.comDescription = comDescription;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	public String getNoOfPeoples() {
		return noOfPeoples;
	}
	public void setNoOfPeoples(String noOfPeoples) {
		this.noOfPeoples = noOfPeoples;
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
