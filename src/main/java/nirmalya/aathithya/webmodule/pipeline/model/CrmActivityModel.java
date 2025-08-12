package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmActivityModel {

	private String activityId;
	private String leadId;
	private String activityName;
	private String activityType;
	private String leadOwner;

	private String createdTime;
	private String createdBy;
	private String createdOn;
	private String taskSubject;

	private String callScheduledWith;
	private String meetingFromDate;
	private String meetingFromTime;
	private String meetingToDate;
	private String meetingToTime;

	private String noteTitle;
	private String noteDesc;
	private String noteDocName;

	private String mailSubject;
	private String mailSentTo;
	private String participant;
	private String callStartDate;
	private String callStartTime;

	private String contactId;
	private String productId;
	
	private String activityDesc;
	private String salesTypes;
	private String activityFromDate;
	private String activityToDate;
	private String activityFromTime;
	private String activityToTime;
	private String previousStatus;
	private String currentStatus;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getLeadOwner() {
		return leadOwner;
	}

	public void setLeadOwner(String leadOwner) {
		this.leadOwner = leadOwner;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
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

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getCallScheduledWith() {
		return callScheduledWith;
	}

	public void setCallScheduledWith(String callScheduledWith) {
		this.callScheduledWith = callScheduledWith;
	}

	public String getMeetingFromDate() {
		return meetingFromDate;
	}

	public void setMeetingFromDate(String meetingFromDate) {
		this.meetingFromDate = meetingFromDate;
	}

	public String getMeetingFromTime() {
		return meetingFromTime;
	}

	public void setMeetingFromTime(String meetingFromTime) {
		this.meetingFromTime = meetingFromTime;
	}

	public String getMeetingToDate() {
		return meetingToDate;
	}

	public void setMeetingToDate(String meetingToDate) {
		this.meetingToDate = meetingToDate;
	}

	public String getMeetingToTime() {
		return meetingToTime;
	}

	public void setMeetingToTime(String meetingToTime) {
		this.meetingToTime = meetingToTime;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteDesc() {
		return noteDesc;
	}

	public void setNoteDesc(String noteDesc) {
		this.noteDesc = noteDesc;
	}

	public String getNoteDocName() {
		return noteDocName;
	}

	public void setNoteDocName(String noteDocName) {
		this.noteDocName = noteDocName;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailSentTo() {
		return mailSentTo;
	}

	public void setMailSentTo(String mailSentTo) {
		this.mailSentTo = mailSentTo;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getCallStartDate() {
		return callStartDate;
	}

	public void setCallStartDate(String callStartDate) {
		this.callStartDate = callStartDate;
	}

	public String getCallStartTime() {
		return callStartTime;
	}

	public void setCallStartTime(String callStartTime) {
		this.callStartTime = callStartTime;
	}
	
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getSalesTypes() {
		return salesTypes;
	}

	public void setSalesTypes(String salesTypes) {
		this.salesTypes = salesTypes;
	}

	public String getActivityFromDate() {
		return activityFromDate;
	}

	public void setActivityFromDate(String activityFromDate) {
		this.activityFromDate = activityFromDate;
	}

	public String getActivityToDate() {
		return activityToDate;
	}

	public void setActivityToDate(String activityToDate) {
		this.activityToDate = activityToDate;
	}

	public String getActivityFromTime() {
		return activityFromTime;
	}

	public void setActivityFromTime(String activityFromTime) {
		this.activityFromTime = activityFromTime;
	}

	public String getActivityToTime() {
		return activityToTime;
	}

	public void setActivityToTime(String activityToTime) {
		this.activityToTime = activityToTime;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
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
