package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmDailyWorkModel {
	
	private String activityId;
	private String owner;
	private String activityName;
	private String activityStatus;
	private String todayDate;

	private String description;
	private String createdBy;
	private String createdOn;
	private String updatedOn;
	private String action;
	private String ownerName;
	
	
	


	public String getUpdatedOn() {
		return updatedOn;
	}





	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}





	public String getActivityId() {
		return activityId;
	}





	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}





	public String getOwner() {
		return owner;
	}





	public void setOwner(String owner) {
		this.owner = owner;
	}





	public String getActivityName() {
		return activityName;
	}





	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}





	public String getActivityStatus() {
		return activityStatus;
	}





	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}





	public String getTodayDate() {
		return todayDate;
	}





	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
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





	public String getAction() {
		return action;
	}





	public void setAction(String action) {
		this.action = action;
	}





	public String getOwnerName() {
		return ownerName;
	}





	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
