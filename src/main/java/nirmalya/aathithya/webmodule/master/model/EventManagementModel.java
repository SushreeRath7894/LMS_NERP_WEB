package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
	
public class EventManagementModel {
	private String eventId;
	private String eventName;
	private String fromDate;
	private String toDate;
	private String eventOrganiser;
	private String eventType;
	private String eventResponsible;
	private String evantMaxRegd;
	private String eventVanue;
	private String regdStartDate;
	private String regdEndDate;
	private String eventCreatedBy;
	private List<EventActivityModel> activity;
	private List<EventNotificationModel> notification;
	private String organization;
	private String orgDivision;
	private String approvedDate;
	private String approvedBy;
	private String rejectDate;
	private String rejectedBy;
	private String approveComment;
	private String status;
	private String empName;
	private String createdBy;
	private String createdOn;
	private String applyStatus;
	
	private String  trainingIds ; 
	private String  trainingNames ; 
	private String participants;
	
    private String startTime ;
    private String endTime;
    private String comment;
	
	
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEventOrganiser() {
		return eventOrganiser;
	}

	public void setEventOrganiser(String eventOrganiser) {
		this.eventOrganiser = eventOrganiser;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventResponsible() {
		return eventResponsible;
	}

	public void setEventResponsible(String eventResponsible) {
		this.eventResponsible = eventResponsible;
	}

	public String getEvantMaxRegd() {
		return evantMaxRegd;
	}

	public void setEvantMaxRegd(String evantMaxRegd) {
		this.evantMaxRegd = evantMaxRegd;
	}

	public String getEventVanue() {
		return eventVanue;
	}

	public void setEventVanue(String eventVanue) {
		this.eventVanue = eventVanue;
	}

	public String getRegdStartDate() {
		return regdStartDate;
	}

	public void setRegdStartDate(String regdStartDate) {
		this.regdStartDate = regdStartDate;
	}

	public String getRegdEndDate() {
		return regdEndDate;
	}

	public void setRegdEndDate(String regdEndDate) {
		this.regdEndDate = regdEndDate;
	}

	public List<EventActivityModel> getActivity() {
		return activity;
	}

	public void setActivity(List<EventActivityModel> activity) {
		this.activity = activity;
	}

	public List<EventNotificationModel> getNotification() {
		return notification;
	}

	public void setNotification(List<EventNotificationModel> notification) {
		this.notification = notification;
	}

	public String getEventCreatedBy() {
		return eventCreatedBy;
	}
	public void setEventCreatedBy(String eventCreatedBy) {
		this.eventCreatedBy = eventCreatedBy;
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

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	

	public String getTrainingIds() {
		return trainingIds;
	}

	public void setTrainingIds(String trainingIds) {
		this.trainingIds = trainingIds;
	}

	public String getTrainingNames() {
		return trainingNames;
	}

	public void setTrainingNames(String trainingNames) {
		this.trainingNames = trainingNames;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
