package nirmalya.aathithya.webmodule.recruitment.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReviewHiringProcessModel {

	private List<ReviewHiringProcessModel> candidateList;
	private String requisitionId;
	private String candidateId;
	private String candidateName;
	private String organization;
	private String orgDivision;
	private String createdBy;
	private String email;
	private String interviewerEmail;
	private String title;
	private String interviewer;
	private String description;
	private String summary;
	private String totalDuration;
	private String location;
	private String toTime;
	private String fromTime;
	private String toDate;
	private String fromDate;
	private String candId;
	private String feedbackId;
	private String position;
	private String modeOfInt;
	private String intViewerName;
	private String designationName;
	private String feedSummary;
	private String feedback;
	private String bandid;
	private String salary;
	private String joiningdate;
	private String meetingURL;
	private String roundId;
	private String skillReview;
	private String recommendation;

	private List<ReviewHiringRatingModel> ratingDetails;

	public ReviewHiringProcessModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getCandidateName() {
		return candidateName;
	}


	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}


	public String getMeetingURL() {
		return meetingURL;
	}

	public void setMeetingURL(String meetingURL) {
		this.meetingURL = meetingURL;
	}

	public String getJoiningdate() {
		return joiningdate;
	}

	public String getInterviewerEmail() {
		return interviewerEmail;
	}

	public void setInterviewerEmail(String interviewerEmail) {
		this.interviewerEmail = interviewerEmail;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public void setJoiningdate(String joiningdate) {
		this.joiningdate = joiningdate;
	}

	public String getBandid() {
		return bandid;
	}

	public void setBandid(String bandid) {
		this.bandid = bandid;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCandId() {
		return candId;
	}

	public void setCandId(String candId) {
		this.candId = candId;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getModeOfInt() {
		return modeOfInt;
	}

	public void setModeOfInt(String modeOfInt) {
		this.modeOfInt = modeOfInt;
	}

	public String getIntViewerName() {
		return intViewerName;
	}

	public void setIntViewerName(String intViewerName) {
		this.intViewerName = intViewerName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getFeedSummary() {
		return feedSummary;
	}

	public void setFeedSummary(String feedSummary) {
		this.feedSummary = feedSummary;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public List<ReviewHiringRatingModel> getRatingDetails() {
		return ratingDetails;
	}

	public void setRatingDetails(List<ReviewHiringRatingModel> ratingDetails) {
		this.ratingDetails = ratingDetails;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public List<ReviewHiringProcessModel> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<ReviewHiringProcessModel> candidateList) {
		this.candidateList = candidateList;
	}

	public String getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getRoundId() {
		return roundId;
	}


	public void setRoundId(String roundId) {
		this.roundId = roundId;
	}
	
	public String getSkillReview() {
		return skillReview;
	}


	public void setSkillReview(String skillReview) {
		this.skillReview = skillReview;
	}


	public String getRecommendation() {
		return recommendation;
	}


	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
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
