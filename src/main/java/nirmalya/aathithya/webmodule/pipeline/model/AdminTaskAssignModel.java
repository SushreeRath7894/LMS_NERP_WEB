package nirmalya.aathithya.webmodule.pipeline.model;

public class AdminTaskAssignModel {
	
	public String description;
	public String executive;
	public String priority;
	public String title;
	public String createdBy;
	public String createdDate;
	public String status;
	public String org;
	public String orgDiv;
	
	public String taskId;
	public String executiveId;
	
	public String executiveMail;
	public String leadMobile;
	public String leadFirstName;
	public String leadLastName;
	
	
	
	public String getLeadFirstName() {
		return leadFirstName;
	}
	public void setLeadFirstName(String leadFirstName) {
		this.leadFirstName = leadFirstName;
	}
	public String getLeadLastName() {
		return leadLastName;
	}
	public void setLeadLastName(String leadLastName) {
		this.leadLastName = leadLastName;
	}
	public String getLeadMobile() {
		return leadMobile;
	}
	public void setLeadMobile(String leadMobile) {
		this.leadMobile = leadMobile;
	}
	public String getExecutiveMail() {
		return executiveMail;
	}
	public void setExecutiveMail(String executiveMail) {
		this.executiveMail = executiveMail;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getExecutiveId() {
		return executiveId;
	}
	public void setExecutiveId(String executiveId) {
		this.executiveId = executiveId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExecutive() {
		return executive;
	}
	public void setExecutive(String executive) {
		this.executive = executive;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getOrgDiv() {
		return orgDiv;
	}
	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	
	@Override
	public String toString() {
		return "AdminTaskAssignModel [description=" + description + ", executive=" + executive + ", priority="
				+ priority + ", title=" + title + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", status=" + status + "]";
	}

}
