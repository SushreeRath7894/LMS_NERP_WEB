package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeGroupSchedulingModel {

	private String empId;
	private String currentGroup;
	private String revisedGroup;
	private String revisedDate;
	private String groupRemarks;;

	private String organization;
	private String orgDivision;
	private String createdBy;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getCurrentGroup() {
		return currentGroup;
	}
	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}
	public String getRevisedGroup() {
		return revisedGroup;
	}
	public void setRevisedGroup(String revisedGroup) {
		this.revisedGroup = revisedGroup;
	}
	public String getRevisedDate() {
		return revisedDate;
	}
	public void setRevisedDate(String revisedDate) {
		this.revisedDate = revisedDate;
	}
	public String getGroupRemarks() {
		return groupRemarks;
	}
	public void setGroupRemarks(String groupRemarks) {
		this.groupRemarks = groupRemarks;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
