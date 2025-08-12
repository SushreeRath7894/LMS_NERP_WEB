package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectPlanningModel {
	
	
	private String planningId;
	private String parentId;
	private String planChildId;


	private String projectId;
	private String taskName;
	private String priority;
	private String assignedTo;
	private String preceders;
	private String notes;
	private String startDate;
	private String endDate;
	
	private String createdBy;
	private String CreatedOn;
	private String UpdatedBy;
	private String UpdatedOn;
	private String OrganizationName;
	private String OrganizationDivision;
	
	private String drpId;
	private String taskId;
	private String drpStatus;
	private String startPlannedDate;
	private String endPlannedDate;
	private String plannedHours;
	private String startDateActPlanned;
	private String endDateActPlanned;
	private String actualPlannedHours;
	private String notesMain;
	private String wImg;
	private String fileupload;
	
	private String imgName;
	
	
	
	
	public ProjectPlanningModel() {
		super();
	}




	public String getPlanningId() {
		return planningId;
	}




	public void setPlanningId(String planningId) {
		this.planningId = planningId;
	}




	public String getProjectId() {
		return projectId;
	}




	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}




	public String getTaskName() {
		return taskName;
	}




	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}




	public String getPriority() {
		return priority;
	}




	public void setPriority(String priority) {
		this.priority = priority;
	}




	public String getAssignedTo() {
		return assignedTo;
	}




	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}




	public String getPreceders() {
		return preceders;
	}




	public void setPreceders(String preceders) {
		this.preceders = preceders;
	}




	public String getNotes() {
		return notes;
	}




	public void setNotes(String notes) {
		this.notes = notes;
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




	public String getPlanChildId() {
		return planChildId;
	}




	public void setPlanChildId(String planChildId) {
		this.planChildId = planChildId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}





	public String getStartPlannedDate() {
		return startPlannedDate;
	}




	public void setStartPlannedDate(String startPlannedDate) {
		this.startPlannedDate = startPlannedDate;
	}




	public String getEndPlannedDate() {
		return endPlannedDate;
	}




	public void setEndPlannedDate(String endPlannedDate) {
		this.endPlannedDate = endPlannedDate;
	}




	public String getPlannedHours() {
		return plannedHours;
	}




	public void setPlannedHours(String plannedHours) {
		this.plannedHours = plannedHours;
	}




	public String getStartDateActPlanned() {
		return startDateActPlanned;
	}




	public void setStartDateActPlanned(String startDateActPlanned) {
		this.startDateActPlanned = startDateActPlanned;
	}




	public String getEndDateActPlanned() {
		return endDateActPlanned;
	}




	public void setEndDateActPlanned(String endDateActPlanned) {
		this.endDateActPlanned = endDateActPlanned;
	}




	public String getActualPlannedHours() {
		return actualPlannedHours;
	}




	public void setActualPlannedHours(String actualPlannedHours) {
		this.actualPlannedHours = actualPlannedHours;
	}




	public String getNotesMain() {
		return notesMain;
	}




	public void setNotesMain(String notesMain) {
		this.notesMain = notesMain;
	}




	public String getwImg() {
		return wImg;
	}




	public void setwImg(String wImg) {
		this.wImg = wImg;
	}




	public String getFileupload() {
		return fileupload;
	}




	public void setFileupload(String fileupload) {
		this.fileupload = fileupload;
	}




	public String getTaskId() {
		return taskId;
	}




	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}




	public String getDrpId() {
		return drpId;
	}




	public void setDrpId(String drpId) {
		this.drpId = drpId;
	}




	public String getDrpStatus() {
		return drpStatus;
	}




	public void setDrpStatus(String drpStatus) {
		this.drpStatus = drpStatus;
	}




	public String getImgName() {
		return imgName;
	}




	public void setImgName(String imgName) {
		this.imgName = imgName;
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
