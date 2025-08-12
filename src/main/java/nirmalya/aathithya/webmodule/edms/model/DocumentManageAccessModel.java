package nirmalya.aathithya.webmodule.edms.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;

public class DocumentManageAccessModel {

	private String accessId;
	private String employeeId;
	private String document;
	private String accessType;
	private String groupId; 
	private String readStatus;
	private String writeStatus;
	private String deleteStatus;
	private String remarks;
	private String createdBy;
	private String updatedBy;
	private String organization;
	private String orgDivision;
	private List<DocumentManageAccessModel> accessEmployee;
	private List<DocumentManageAccessModel> userDeselect;
	private List<DocumentManageAccessModel> newUsers;
	private String expirationDate;
	private String empName;
	private String status;
	
	public DocumentManageAccessModel() {
		super();
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public String getWriteStatus() {
		return writeStatus;
	}

	public void setWriteStatus(String writeStatus) {
		this.writeStatus = writeStatus;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
	
	
	
	

	public List<DocumentManageAccessModel> getAccessEmployee() {
		return accessEmployee;
	}

	public void setAccessEmployee(List<DocumentManageAccessModel> accessEmployee) {
		this.accessEmployee = accessEmployee;
	}

	
	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	

	public List<DocumentManageAccessModel> getUserDeselect() {
		return userDeselect;
	}

	public void setUserDeselect(List<DocumentManageAccessModel> userDeselect) {
		this.userDeselect = userDeselect;
	}

	public List<DocumentManageAccessModel> getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(List<DocumentManageAccessModel> newUsers) {
		this.newUsers = newUsers;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
