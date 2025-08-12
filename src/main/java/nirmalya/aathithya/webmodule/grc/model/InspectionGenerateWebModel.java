package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


public class InspectionGenerateWebModel {
	private String  inspectionId;
	private String projectId;
	private String projectName;
	private String site;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String organizationName;
	private String organizationDivision;

	private String serialNo;
	private String categoryId;
	private String categoryName;
	private String subCategoryId;
	private String subCategoryName;
	private String type;
	private String assignedTo;
	private String startDate;
	private String endDate;
	private String assignedToName;
	private List<InspectionGenerateWebSubModel> subModel;
	

	public String getInspectionId() {
		return inspectionId;
	}


	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
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


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
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


	public List<InspectionGenerateWebSubModel> getSubModel() {
		return subModel;
	}


	public void setSubModel(List<InspectionGenerateWebSubModel> subModel) {
		this.subModel = subModel;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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


	public String getSubCategoryId() {
		return subCategoryId;
	}


	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}


	public String getSubCategoryName() {
		return subCategoryName;
	}


	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getAssignedTo() {
		return assignedTo;
	}


	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getAssignedToName() {
		return assignedToName;
	}


	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
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
