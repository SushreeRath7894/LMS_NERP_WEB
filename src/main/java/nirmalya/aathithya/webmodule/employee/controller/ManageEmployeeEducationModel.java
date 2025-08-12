package nirmalya.aathithya.webmodule.employee.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageEmployeeEducationModel {

	private String empId;
	private String eduId;
	private String qualification;
	private String instiname;
	private String passyear;
	private String docName;
	private String createdBy;
	private String organization;
	private String orgDivision;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEduId() {
		return eduId;
	}
	public void setEduId(String eduId) {
		this.eduId = eduId;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getInstiname() {
		return instiname;
	}
	public void setInstiname(String instiname) {
		this.instiname = instiname;
	}
	public String getPassyear() {
		return passyear;
	}
	public void setPassyear(String passyear) {
		this.passyear = passyear;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
