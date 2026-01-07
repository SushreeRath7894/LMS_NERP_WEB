package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CallCrmReportModel {
	private String callId;
	private String callSubject; 
	private String callDate; 
	private String callTime;
	private String callStatus;
	private String callReminder;
	private String callDesc;
	private String callCreatedBy;
	private String callCreatedByName;
	private String callCreatedOn;
	private String orgName;
	private String orgDiv;
	private String callLead;
	private String callLeadName;
	private String callDecisionMaker;
	private String callDecisionMakerName;
	
	// check in 
	private String longitude;
	private String latitude;
	private String checkDate;
	private String checkTime;
	private String ckeckAddress;
	private String ckeckDesc;
			
	public CallCrmReportModel() {
		super();
	}
 
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCkeckDesc() {
		return ckeckDesc;
	}

	public void setCkeckDesc(String ckeckDesc) {
		this.ckeckDesc = ckeckDesc;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCallSubject() {
		return callSubject;
	}

	public void setCallSubject(String callSubject) {
		this.callSubject = callSubject;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public String getCallReminder() {
		return callReminder;
	}

	public void setCallReminder(String callReminder) {
		this.callReminder = callReminder;
	}

	public String getCallDesc() {
		return callDesc;
	}

	public void setCallDesc(String callDesc) {
		this.callDesc = callDesc;
	}

	public String getCallCreatedBy() {
		return callCreatedBy;
	}

	public void setCallCreatedBy(String callCreatedBy) {
		this.callCreatedBy = callCreatedBy;
	}

	public String getCallCreatedOn() {
		return callCreatedOn;
	}

	public void setCallCreatedOn(String callCreatedOn) {
		this.callCreatedOn = callCreatedOn;
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

	public String getCallLead() {
		return callLead;
	}

	public void setCallLead(String callLead) {
		this.callLead = callLead;
	}

	public String getCallLeadName() {
		return callLeadName;
	}

	public void setCallLeadName(String callLeadName) {
		this.callLeadName = callLeadName;
	}

	public String getCallDecisionMaker() {
		return callDecisionMaker;
	}

	public void setCallDecisionMaker(String callDecisionMaker) {
		this.callDecisionMaker = callDecisionMaker;
	}

	public String getCallDecisionMakerName() {
		return callDecisionMakerName;
	}

	public void setCallDecisionMakerName(String callDecisionMakerName) {
		this.callDecisionMakerName = callDecisionMakerName;
	}

	public String getCallCreatedByName() {
		return callCreatedByName;
	}

	public void setCallCreatedByName(String callCreatedByName) {
		this.callCreatedByName = callCreatedByName;
	}

	public String getCkeckAddress() {
		return ckeckAddress;
	}

	public void setCkeckAddress(String ckeckAddress) {
		this.ckeckAddress = ckeckAddress;
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
