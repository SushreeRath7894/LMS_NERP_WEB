package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineMeetingReminderModel {

	private String reminder;
	private String reminderType;
	private Integer reminderBefore;
	private String reminderUnit;
	private String createdBy;
	private String action;

	public PipelineMeetingReminderModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}

	public Integer getReminderBefore() {
		return reminderBefore;
	}

	public void setReminderBefore(Integer reminderBefore) {
		this.reminderBefore = reminderBefore;
	}

	public String getReminderUnit() {
		return reminderUnit;
	}

	public void setReminderUnit(String reminderUnit) {
		this.reminderUnit = reminderUnit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

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
