package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeShiftSchedulingModel {

	private String empId;
	private String currentShift;
	private String revisedShift;
	private String revisedDate;
	private String shiftRemarks;

	private String organization;
	private String orgDivision;
	private String createdBy;
	private String currentGroup;
	private String revisedToDate;

	public EmployeeShiftSchedulingModel() {
		super();
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getShiftRemarks() {
		return shiftRemarks;
	}

	public void setShiftRemarks(String shiftRemarks) {
		this.shiftRemarks = shiftRemarks;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCurrentShift() {
		return currentShift;
	}

	public void setCurrentShift(String currentShift) {
		this.currentShift = currentShift;
	}

	public String getRevisedShift() {
		return revisedShift;
	}

	public void setRevisedShift(String revisedShift) {
		this.revisedShift = revisedShift;
	}

	public String getRevisedDate() {
		return revisedDate;
	}

	public void setRevisedDate(String revisedDate) {
		this.revisedDate = revisedDate;
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

	public String getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}

	public String getRevisedToDate() {
		return revisedToDate;
	}

	public void setRevisedToDate(String revisedToDate) {
		this.revisedToDate = revisedToDate;
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
