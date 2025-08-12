package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistrationCrmReportModel {
	private String clientId;
	private String industry; 
	private String cmpName;
	private String cmpEmail;
	private String cmpPhone;
	private String cmpAddress; 
	private String cmpLandMark;
	private String cmpPinCode; 
	private String gst;
	private String pan;
	private String webSite;
	private String leadSource;
	private String leadstatus;
	private String totalEmp;
	private String empDesc;
	private String createdOn;
	private String orgName;
	private String orgDiv;
	private String industryName;
	private String dmId;
	private String dmName;
	private String dmEmail;
	private String dmPhone;
	private String dmDesignation;
	private String dmDob;
	private String dmMarriageDate;
	private String leadSourceName;
	private String leadstatusName;
	private String sectorName;
	private String createdBy;
	private String createdByName;
	private String profileImg;
	
	
	// visitor details
	private String visitorClientId;
	private String visitorClientName;
	private String visitorClientPhone;
	private String visitorClientAddress;
	private String visitorCallId;
	private String visitorCallDate;
	private String visitorCallTime;
	private String visitorCallDesc;
	private String visitorMeetingId;
	private String visitorMeetingDate;
	private String visitorMeetingTime;
	private String visitorMeetingDesc;
	
	private List<DirecterManagerCrmReportModel> dmList;

	public RegistrationCrmReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public String getCmpEmail() {
		return cmpEmail;
	}

	public void setCmpEmail(String cmpEmail) {
		this.cmpEmail = cmpEmail;
	}

	public String getCmpPhone() {
		return cmpPhone;
	}

	public void setCmpPhone(String cmpPhone) {
		this.cmpPhone = cmpPhone;
	}

	public String getCmpAddress() {
		return cmpAddress;
	}

	public void setCmpAddress(String cmpAddress) {
		this.cmpAddress = cmpAddress;
	}

	public String getCmpLandMark() {
		return cmpLandMark;
	}

	public void setCmpLandMark(String cmpLandMark) {
		this.cmpLandMark = cmpLandMark;
	}

	public String getCmpPinCode() {
		return cmpPinCode;
	}

	public void setCmpPinCode(String cmpPinCode) {
		this.cmpPinCode = cmpPinCode;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadstatus() {
		return leadstatus;
	}

	public void setLeadstatus(String leadstatus) {
		this.leadstatus = leadstatus;
	}

	public String getTotalEmp() {
		return totalEmp;
	}

	public void setTotalEmp(String totalEmp) {
		this.totalEmp = totalEmp;
	}

	public String getEmpDesc() {
		return empDesc;
	}

	public void setEmpDesc(String empDesc) {
		this.empDesc = empDesc;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getDmId() {
		return dmId;
	}

	public void setDmId(String dmId) {
		this.dmId = dmId;
	}

	public String getDmName() {
		return dmName;
	}

	public void setDmName(String dmName) {
		this.dmName = dmName;
	}

	public String getDmEmail() {
		return dmEmail;
	}

	public void setDmEmail(String dmEmail) {
		this.dmEmail = dmEmail;
	}

	public String getDmPhone() {
		return dmPhone;
	}

	public void setDmPhone(String dmPhone) {
		this.dmPhone = dmPhone;
	}

	public String getDmDesignation() {
		return dmDesignation;
	}

	public void setDmDesignation(String dmDesignation) {
		this.dmDesignation = dmDesignation;
	}

	public String getDmDob() {
		return dmDob;
	}

	public void setDmDob(String dmDob) {
		this.dmDob = dmDob;
	}

	public String getDmMarriageDate() {
		return dmMarriageDate;
	}

	public void setDmMarriageDate(String dmMarriageDate) {
		this.dmMarriageDate = dmMarriageDate;
	}

	public String getLeadSourceName() {
		return leadSourceName;
	}

	public void setLeadSourceName(String leadSourceName) {
		this.leadSourceName = leadSourceName;
	}

	public String getLeadstatusName() {
		return leadstatusName;
	}

	public void setLeadstatusName(String leadstatusName) {
		this.leadstatusName = leadstatusName;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getVisitorClientId() {
		return visitorClientId;
	}

	public void setVisitorClientId(String visitorClientId) {
		this.visitorClientId = visitorClientId;
	}

	public String getVisitorClientName() {
		return visitorClientName;
	}

	public void setVisitorClientName(String visitorClientName) {
		this.visitorClientName = visitorClientName;
	}

	public String getVisitorClientPhone() {
		return visitorClientPhone;
	}

	public void setVisitorClientPhone(String visitorClientPhone) {
		this.visitorClientPhone = visitorClientPhone;
	}

	public String getVisitorClientAddress() {
		return visitorClientAddress;
	}

	public void setVisitorClientAddress(String visitorClientAddress) {
		this.visitorClientAddress = visitorClientAddress;
	}

	public String getVisitorCallId() {
		return visitorCallId;
	}

	public void setVisitorCallId(String visitorCallId) {
		this.visitorCallId = visitorCallId;
	}

	public String getVisitorCallDate() {
		return visitorCallDate;
	}

	public void setVisitorCallDate(String visitorCallDate) {
		this.visitorCallDate = visitorCallDate;
	}

	public String getVisitorCallTime() {
		return visitorCallTime;
	}

	public void setVisitorCallTime(String visitorCallTime) {
		this.visitorCallTime = visitorCallTime;
	}

	public String getVisitorCallDesc() {
		return visitorCallDesc;
	}

	public void setVisitorCallDesc(String visitorCallDesc) {
		this.visitorCallDesc = visitorCallDesc;
	}

	public String getVisitorMeetingId() {
		return visitorMeetingId;
	}

	public void setVisitorMeetingId(String visitorMeetingId) {
		this.visitorMeetingId = visitorMeetingId;
	}

	public String getVisitorMeetingDate() {
		return visitorMeetingDate;
	}

	public void setVisitorMeetingDate(String visitorMeetingDate) {
		this.visitorMeetingDate = visitorMeetingDate;
	}

	public String getVisitorMeetingTime() {
		return visitorMeetingTime;
	}

	public void setVisitorMeetingTime(String visitorMeetingTime) {
		this.visitorMeetingTime = visitorMeetingTime;
	}

	public String getVisitorMeetingDesc() {
		return visitorMeetingDesc;
	}

	public void setVisitorMeetingDesc(String visitorMeetingDesc) {
		this.visitorMeetingDesc = visitorMeetingDesc;
	}

	public List<DirecterManagerCrmReportModel> getDmList() {
		return dmList;
	}

	public void setDmList(List<DirecterManagerCrmReportModel> dmList) {
		this.dmList = dmList;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
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
