package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrganisationTypeModel { 

	//// organisation type model
	private String orgId;
	private String orgName;
	private String status;

	//// organisation holiday model
	private String holidayId;
	private String holidayName;
	private String fromDate;
	private String toDate;
	private String totalHoliday;
	
	//bank model
	
	private String bankId;
	private String bankName;
	private String description;
	private Boolean bankStatus;
	private String createdBy;
	
	//announcement model
	private String announcementId;
	private String dateAnnounce;
	private String ancdtlSub;
	private String ancdtlDetails;
	private String ancdtlURL;
	private String ancdtlStatus;
	private String createdByAnnouncement;
	private String organization;
	private String orgDivision;
	//Leave policy model
	private String leavePolicyId;
	private String leaveFromDate;
	private String leaveToDate;
	private String leavePolicyDtls;
	private String leavePolicyStatus;
	private String leavePolicyCreatedBy;
	private String leavePolicyOrgName;
	private String leavePolicyorgDivision;


	public OrganisationTypeModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
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

	public String getTotalHoliday() {
		return totalHoliday;
	}

	public void setTotalHoliday(String totalHoliday) {
		this.totalHoliday = totalHoliday;
	}
	
	

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(Boolean bankStatus) {
		this.bankStatus = bankStatus;
	}
	
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

	

	public String getDateAnnounce() {
		return dateAnnounce;
	}

	public void setDateAnnounce(String dateAnnounce) {
		this.dateAnnounce = dateAnnounce;
	}

	public String getAncdtlSub() {
		return ancdtlSub;
	}

	public void setAncdtlSub(String ancdtlSub) {
		this.ancdtlSub = ancdtlSub;
	}

	public String getAncdtlDetails() {
		return ancdtlDetails;
	}

	public void setAncdtlDetails(String ancdtlDetails) {
		this.ancdtlDetails = ancdtlDetails;
	}

	public String getAncdtlURL() {
		return ancdtlURL;
	}

	public void setAncdtlURL(String ancdtlURL) {
		this.ancdtlURL = ancdtlURL;
	}

	public String getAncdtlStatus() {
		return ancdtlStatus;
	}

	public void setAncdtlStatus(String ancdtlStatus) {
		this.ancdtlStatus = ancdtlStatus;
	}

	public String getCreatedByAnnouncement() {
		return createdByAnnouncement;
	}

	public void setCreatedByAnnouncement(String createdByAnnouncement) {
		this.createdByAnnouncement = createdByAnnouncement;
	}
    
 
	public String getLeavePolicyId() {
		return leavePolicyId;
	}

	public void setLeavePolicyId(String leavePolicyId) {
		this.leavePolicyId = leavePolicyId;
	}

 

	public String getLeaveFromDate() {
		return leaveFromDate;
	}

	public void setLeaveFromDate(String leaveFromDate) {
		this.leaveFromDate = leaveFromDate;
	}

	public String getLeaveToDate() {
		return leaveToDate;
	}

	public void setLeaveToDate(String leaveToDate) {
		this.leaveToDate = leaveToDate;
	}

	public String getLeavePolicyDtls() {
		return leavePolicyDtls;
	}

	public void setLeavePolicyDtls(String leavePolicyDtls) {
		this.leavePolicyDtls = leavePolicyDtls;
	}

	public String getLeavePolicyStatus() {
		return leavePolicyStatus;
	}

	public void setLeavePolicyStatus(String leavePolicyStatus) {
		this.leavePolicyStatus = leavePolicyStatus;
	}

	public String getLeavePolicyCreatedBy() {
		return leavePolicyCreatedBy;
	}

	public void setLeavePolicyCreatedBy(String leavePolicyCreatedBy) {
		this.leavePolicyCreatedBy = leavePolicyCreatedBy;
	}

	public String getLeavePolicyOrgName() {
		return leavePolicyOrgName;
	}

	public void setLeavePolicyOrgName(String leavePolicyOrgName) {
		this.leavePolicyOrgName = leavePolicyOrgName;
	}

	public String getLeavePolicyorgDivision() {
		return leavePolicyorgDivision;
	}

	public void setLeavePolicyorgDivision(String leavePolicyorgDivision) {
		this.leavePolicyorgDivision = leavePolicyorgDivision;
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
