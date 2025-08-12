package nirmalya.aathithya.webmodule.recruitment.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HireActionModel {

	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private String location;
	private String summary;
	private List<String> hiringManager;
	private String createdBy;
	private List<String> candidateId;
	private String requisitionId;
	private String candId;
	private String firstName;
	private String hiringManagerName;
	private String editId;
	private String hiringManagerId;
	private List<String> candEmail;
	private List<String> interviewerMail;
	private String title;
	private String locationName;
	private String description;
	private String emailId;
	private String personalMail;

	private String feedbackId;
	private String position;
	private String createDate;
	private String modeOfInt;
	private String intViewerId;
	private String intViewerName;
	private String designationId;
	private String designationName;
	private Integer slNo;
	private String ratingCatName;
	private String ratingCat;
	private String ratingTypeName;
	private String ratingType;
	private String rating;
	private String comment;
	private String feedSummary;
	private String feedback;
	private String intViewSubmitSts;
	private String orgName;
	private String orgDivision;

	public List<String> getCandEmail() {
		return candEmail;
	}

	public void setCandEmail(List<String> candEmail) {
		this.candEmail = candEmail;
	}

	public List<String> getHiringManager() {
		return hiringManager;
	}

	public void setHiringManager(List<String> hiringManager) {
		this.hiringManager = hiringManager;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<String> getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(List<String> candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandId() {
		return candId;
	}

	public void setCandId(String candId) {
		this.candId = candId;
	}

	public String getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHiringManagerName() {
		return hiringManagerName;
	}

	public void setHiringManagerName(String hiringManagerName) {
		this.hiringManagerName = hiringManagerName;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
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

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getHiringManagerId() {
		return hiringManagerId;
	}

	public void setHiringManagerId(String hiringManagerId) {
		this.hiringManagerId = hiringManagerId;
	}

	public List<String> getInterviewerMail() {
		return interviewerMail;
	}

	public void setInterviewerMail(List<String> interviewerMail) {
		this.interviewerMail = interviewerMail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModeOfInt() {
		return modeOfInt;
	}

	public void setModeOfInt(String modeOfInt) {
		this.modeOfInt = modeOfInt;
	}

	public String getIntViewerId() {
		return intViewerId;
	}

	public void setIntViewerId(String intViewerId) {
		this.intViewerId = intViewerId;
	}

	public String getIntViewerName() {
		return intViewerName;
	}

	public void setIntViewerName(String intViewerName) {
		this.intViewerName = intViewerName;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public String getRatingCatName() {
		return ratingCatName;
	}

	public void setRatingCatName(String ratingCatName) {
		this.ratingCatName = ratingCatName;
	}

	public String getRatingCat() {
		return ratingCat;
	}

	public void setRatingCat(String ratingCat) {
		this.ratingCat = ratingCat;
	}

	public String getRatingTypeName() {
		return ratingTypeName;
	}

	public void setRatingTypeName(String ratingTypeName) {
		this.ratingTypeName = ratingTypeName;
	}

	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getIntViewSubmitSts() {
		return intViewSubmitSts;
	}

	public void setIntViewSubmitSts(String intViewSubmitSts) {
		this.intViewSubmitSts = intViewSubmitSts;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
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
