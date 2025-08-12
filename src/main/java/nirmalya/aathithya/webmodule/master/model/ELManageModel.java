package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ELManageModel {
	
	private String earnLeaveID ;
	private String  empId ;
	private String employeeName;
	private String year;
	private String totalEL;
	private String prevEL;
	private String availedEL;
	private String assignedEL;
	private String balanceEL;
    private String organization;
	private String orgDivision;
	private String createdBy;
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
	public String getEarnLeaveID() {
		return earnLeaveID;
	}
	public void setEarnLeaveID(String earnLeaveID) {
		this.earnLeaveID = earnLeaveID;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getTotalEL() {
		return totalEL;
	}
	public void setTotalEL(String totalEL) {
		this.totalEL = totalEL;
	}
	public String getPrevEL() {
		return prevEL;
	}
	public void setPrevEL(String prevEL) {
		this.prevEL = prevEL;
	}
	public String getAvailedEL() {
		return availedEL;
	}
	public void setAvailedEL(String availedEL) {
		this.availedEL = availedEL;
	}
	public String getAssignedEL() {
		return assignedEL;
	}
	public void setAssignedEL(String assignedEL) {
		this.assignedEL = assignedEL;
	}
	public String getBalanceEL() {
		return balanceEL;
	}
	public void setBalanceEL(String balanceEL) {
		this.balanceEL = balanceEL;
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
}
