package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineModel {
	private String pipelineId;
	private String oppertunity;
	private Double expectedRevenue;
	private Double probability;
	private String customer;
	private String email;
	private String phone;
	private String saleperson;
	private String salesTeam;
	private String expectedClosing;
	private String priority;
	private String tags;
	private String company;
	private String description;
	private String campanyName;
	private String addressStreet;
	private String addressStreet2;
	private String city;
	private String states;
	private String zip;
	private String country;
	private String website;
	private String language;
	private String contactName;
	private String tittle;
	private String jobPosition;
	private String mobile;
	private String referdBy;
	private String campaign;
	private String medium;
	private String source;
	private String createdBy;
	private String action;
	private Integer previousStage;
	private Integer currentStage;
	private String fromStage;
	private List<String> fromStageList = new ArrayList<String>();
	private List<String> toStageList = new ArrayList<String>();
	private List<String> createdDateList = new ArrayList<String>();
	private Integer toStage;
	private String createdDate;
	private Integer pipelineStatus;
	private String statusName;
	//lostReason
	private String lostReason;
	//Meeting
	private String meetingId;
	

	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private String location;
	private String summary;
	private String descriptionMeeting;
	private String title;
	private String customerId;
	
	
	

	


	public PipelineModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOppertunity() {
		return oppertunity;
	}

	public void setOppertunity(String oppertunity) {
		this.oppertunity = oppertunity;
	}

	public Double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(Double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSaleperson() {
		return saleperson;
	}

	public void setSaleperson(String saleperson) {
		this.saleperson = saleperson;
	}

	public String getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}

	public String getExpectedClosing() {
		return expectedClosing;
	}

	public void setExpectedClosing(String expectedClosing) {
		this.expectedClosing = expectedClosing;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCampanyName() {
		return campanyName;
	}

	public void setCampanyName(String campanyName) {
		this.campanyName = campanyName;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressStreet2() {
		return addressStreet2;
	}

	public void setAddressStreet2(String addressStreet2) {
		this.addressStreet2 = addressStreet2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReferdBy() {
		return referdBy;
	}

	public void setReferdBy(String referdBy) {
		this.referdBy = referdBy;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(Integer previousStage) {
		this.previousStage = previousStage;
	}

	public Integer getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Integer currentStage) {
		this.currentStage = currentStage;
	}

	public String getFromStage() {
		return fromStage;
	}

	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}

	public Integer getToStage() {
		return toStage;
	}

	public void setToStage(Integer toStage) {
		this.toStage = toStage;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getFromStageList() {
		return fromStageList;
	}

	public void setFromStageList(List<String> fromStageList) {
		this.fromStageList = fromStageList;
	}

	public List<String> getToStageList() {
		return toStageList;
	}

	public void setToStageList(List<String> toStageList) {
		this.toStageList = toStageList;
	}

	public List<String> getCreatedDateList() {
		return createdDateList;
	}

	public void setCreatedDateList(List<String> createdDateList) {
		this.createdDateList = createdDateList;
	}

	public Integer getPipelineStatus() {
		return pipelineStatus;
	}

	public void setPipelineStatus(Integer pipelineStatus) {
		this.pipelineStatus = pipelineStatus;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getLostReason() {
		return lostReason;
	}

	public void setLostReason(String lostReason) {
		this.lostReason = lostReason;
	}
//meeting
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
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

	public String getDescriptionMeeting() {
		return descriptionMeeting;
	}

	public void setDescriptionMeeting(String descriptionMeeting) {
		this.descriptionMeeting = descriptionMeeting;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
