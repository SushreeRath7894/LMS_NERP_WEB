package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivityTypeModel {
	private String activityType;
	private String activityTypeName;
	private String activityAction;
	private String activityUser;
	private String activitySummary;
	private String activityDes;
	private String activitySceduleDate;
	private String activityUnit;
	private String activitySceduledType;
	private Boolean activityStatus;
	private Boolean nextActivityStatus;
	private String nextActivity;
	private String recomandedActivity;
	private String nextActivityEmail;
	private String createdBy;
	private String action;
	public ActivityTypeModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public String getActivityAction() {
		return activityAction;
	}

	public void setActivityAction(String activityAction) {
		this.activityAction = activityAction;
	}

	public String getactivityUser() {
		return activityUser;
	}

	public void setactivityUser(String activityUser) {
		this.activityUser = activityUser;
	}

	public String getActivitySummary() {
		return activitySummary;
	}

	public void setActivitySummary(String activitySummary) {
		this.activitySummary = activitySummary;
	}

	public String getActivityDes() {
		return activityDes;
	}

	public void setActivityDes(String activityDes) {
		this.activityDes = activityDes;
	}

	public String getActivitySceduleDate() {
		return activitySceduleDate;
	}

	public void setActivitySceduleDate(String activitySceduleDate) {
		this.activitySceduleDate = activitySceduleDate;
	}

	public String getActivityUnit() {
		return activityUnit;
	}

	public void setActivityUnit(String activityUnit) {
		this.activityUnit = activityUnit;
	}

	public String getActivitySceduledType() {
		return activitySceduledType;
	}

	public void setActivitySceduledType(String activitySceduledType) {
		this.activitySceduledType = activitySceduledType;
	}

	public Boolean getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Boolean activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Boolean getNextActivityStatus() {
		return nextActivityStatus;
	}

	public void setNextActivityStatus(Boolean nextActivityStatus) {
		this.nextActivityStatus = nextActivityStatus;
	}

	public String getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(String nextActivity) {
		this.nextActivity = nextActivity;
	}

	public String getRecomandedActivity() {
		return recomandedActivity;
	}

	public void setRecomandedActivity(String recomandedActivity) {
		this.recomandedActivity = recomandedActivity;
	}

	public String getNextActivityEmail() {
		return nextActivityEmail;
	}

	public void setNextActivityEmail(String nextActivityEmail) {
		this.nextActivityEmail = nextActivityEmail;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

/*	public String getActivityUser() {
		return activityUser;
	}

	public void setActivityUser(String activityUser) {
		this.activityUser = activityUser;
	}*/

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
