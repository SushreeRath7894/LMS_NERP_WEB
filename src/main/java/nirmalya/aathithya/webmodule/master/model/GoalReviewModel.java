package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


public class GoalReviewModel {

	private String name;
	private String designation;
	private String goalId;
	private String goalName;
	private String expectedResults;
	private String weightage;
	private String selfReview;
	private String managersReview;
	private String summary;
	private String recommandation;
	private String createdBy;
	
	private String employeeId;
	private String firstName;
	private String gender;
	private String dob;
	private String mobileNo;
	private String personalMail;
	private String workMail;
	
	private String goalAssignedId;
	private String sessionId;
	private String comment;
	private String reviewId;
	private String assignedEmpId;
	
	private String meetingNo;
	private String meetingDate;
	private String startTime;
	private String endTime;
	private String meetingSubject;
	private String participantDept;
	private String participants;
	private String meetingComment;
	private String meetingFileName;
	
	private List<GoalReviewDocumentModel> documentList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getGoalId() {
		return goalId;
	}
	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}
	public String getGoalName() {
		return goalName;
	}
	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}
	public String getExpectedResults() {
		return expectedResults;
	}
	public void setExpectedResults(String expectedResults) {
		this.expectedResults = expectedResults;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getSelfReview() {
		return selfReview;
	}
	public void setSelfReview(String selfReview) {
		this.selfReview = selfReview;
	}
	public String getManagersReview() {
		return managersReview;
	}
	public void setManagersReview(String managersReview) {
		this.managersReview = managersReview;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getRecommandation() {
		return recommandation;
	}
	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getPersonalMail() {
		return personalMail;
	}
	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}
	public String getWorkMail() {
		return workMail;
	}
	public void setWorkMail(String workMail) {
		this.workMail = workMail;
	}
	
	public String getGoalAssignedId() {
		return goalAssignedId;
	}
	public void setGoalAssignedId(String goalAssignedId) {
		this.goalAssignedId = goalAssignedId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getAssignedEmpId() {
		return assignedEmpId;
	}
	public void setAssignedEmpId(String assignedEmpId) {
		this.assignedEmpId = assignedEmpId;
	}
	public String getMeetingNo() {
		return meetingNo;
	}
	public void setMeetingNo(String meetingNo) {
		this.meetingNo = meetingNo;
	}
	public String getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
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
	public String getMeetingSubject() {
		return meetingSubject;
	}
	public void setMeetingSubject(String meetingSubject) {
		this.meetingSubject = meetingSubject;
	}
	public String getParticipantDept() {
		return participantDept;
	}
	public void setParticipantDept(String participantDept) {
		this.participantDept = participantDept;
	}
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	public String getMeetingComment() {
		return meetingComment;
	}
	public void setMeetingComment(String meetingComment) {
		this.meetingComment = meetingComment;
	}
	public String getMeetingFileName() {
		return meetingFileName;
	}
	public void setMeetingFileName(String meetingFileName) {
		this.meetingFileName = meetingFileName;
	}
	public List<GoalReviewDocumentModel> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<GoalReviewDocumentModel> documentList) {
		this.documentList = documentList;
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
