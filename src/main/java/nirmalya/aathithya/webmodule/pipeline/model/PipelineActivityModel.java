package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineActivityModel {
	private String activityTypeId;
	private String activityType;
	private String duedate;
	private String summary;
	private String assignTo;
	private String activityTypeDesc;
	private String activityTypeCreatedBy;
	private String activityPipeline;
	private Boolean activityDoneStatus;
	private String createdOn;
	public PipelineActivityModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(String activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getActivityTypeDesc() {
		return activityTypeDesc;
	}
	public void setActivityTypeDesc(String activityTypeDesc) {
		this.activityTypeDesc = activityTypeDesc;
	}
	public String getActivityTypeCreatedBy() {
		return activityTypeCreatedBy;
	}
	public void setActivityTypeCreatedBy(String activityTypeCreatedBy) {
		this.activityTypeCreatedBy = activityTypeCreatedBy;
	}
	
	public String getActivityPipeline() {
		return activityPipeline;
	}
	public void setActivityPipeline(String activityPipeline) {
		this.activityPipeline = activityPipeline;
	}
	
	public Boolean getActivityDoneStatus() {
		return activityDoneStatus;
	}

	public void setActivityDoneStatus(Boolean activityDoneStatus) {
		this.activityDoneStatus = activityDoneStatus;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
