package nirmalya.aathithya.webmodule.training.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewStudyMaterialsModel {

	private String employeeId;
	private String status;
	private String assignedDate;
	private String assignedOn;
	private String assignId;
	private String scheduledOn;
	private String scheduledFromDate;
	private String scheduledToDate;
	private String orgDiv;
	private String org;
	private String scheduledHours;
	
	private String empName;
	private String gender;
	private String dob;
	private String department;
	private String subDepartment;
	private String designation;
	private String manager;
	private String mobileNo;
	private String personalMail;

	private String sku;
	private String categoryId;
	private String categoryName;
	private String parentId;
	private String parentName;
	private String studyMaterialId;
	private String docName;
	private String joiningDate;

	
	

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public ViewStudyMaterialsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStudyMaterialId() {
		return studyMaterialId;
	}

	public void setStudyMaterialId(String studyMaterialId) {
		this.studyMaterialId = studyMaterialId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}


	public String getScheduledOn() {
		return scheduledOn;
	}

	public void setScheduledOn(String scheduledOn) {
		this.scheduledOn = scheduledOn;
	}

	public String getScheduledFromDate() {
		return scheduledFromDate;
	}

	public void setScheduledFromDate(String scheduledFromDate) {
		this.scheduledFromDate = scheduledFromDate;
	}

	public String getScheduledToDate() {
		return scheduledToDate;
	}

	public void setScheduledToDate(String scheduledToDate) {
		this.scheduledToDate = scheduledToDate;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
	

	public String getScheduledHours() {
		return scheduledHours;
	}

	public void setScheduledHours(String scheduledHours) {
		this.scheduledHours = scheduledHours;
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
