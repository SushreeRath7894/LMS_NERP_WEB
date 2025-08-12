package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SafetyAssesmentWebModel {

	private String safetyId;
	private String projectId;
	private String categoryId;
	private String categoryName;
	private String categoryCode;
	private String actionItem;
	private String assessedBy;
	private String assessDate;
	private String assessNotes;
	private String require;
	private String remarks;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String organizationName;
	private String organizationDivision;
	
	public SafetyAssesmentWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSafetyId() {
		return safetyId;
	}
	public void setSafetyId(String safetyId) {
		this.safetyId = safetyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getActionItem() {
		return actionItem;
	}
	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}
	public String getAssessedBy() {
		return assessedBy;
	}
	public void setAssessedBy(String assessedBy) {
		this.assessedBy = assessedBy;
	}
	public String getAssessDate() {
		return assessDate;
	}
	public void setAssessDate(String assessDate) {
		this.assessDate = assessDate;
	}
	public String getAssessNotes() {
		return assessNotes;
	}
	public void setAssessNotes(String assessNotes) {
		this.assessNotes = assessNotes;
	}
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
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
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationDivision() {
		return organizationDivision;
	}
	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
	}
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
