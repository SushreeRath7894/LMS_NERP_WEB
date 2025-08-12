package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditScheduleModel {
	
	private String auditScheduleId;
	private String assigndate;
	private String auditorId;
	private String agencyAuditorName; 
	private String auditType;
	private String auditCategory; 
	private String auditLocation; 
	private String auditFrequency;
	private String planId;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	public AuditScheduleModel() {
		super();
	}
	
	
	 



	public String getAuditScheduleId() {
		return auditScheduleId;
	}
 
	public void setAuditScheduleId(String auditScheduleId) {
		this.auditScheduleId = auditScheduleId;
	}

 
	public String getAssigndate() {
		return assigndate;
	}
 
	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}
 
	public String getAuditorId() {
		return auditorId;
	}
 
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
 
	public String getAgencyAuditorName() {
		return agencyAuditorName;
	}
 
	public void setAgencyAuditorName(String agencyAuditorName) {
		this.agencyAuditorName = agencyAuditorName;
	}
 
	public String getAuditType() {
		return auditType;
	}
 
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
 
	public String getAuditCategory() {
		return auditCategory;
	}
 
	public void setAuditCategory(String auditCategory) {
		this.auditCategory = auditCategory;
	}
 
	public String getAuditLocation() {
		return auditLocation;
	}
 
	public void setAuditLocation(String auditLocation) {
		this.auditLocation = auditLocation;
	}
 
	public String getAuditFrequency() {
		return auditFrequency;
	}
 
	public void setAuditFrequency(String auditFrequency) {
		this.auditFrequency = auditFrequency;
	}
 
	public String getPlanId() {
		return planId;
	}
 
	public void setPlanId(String planId) {
		this.planId = planId;
	}
 
	public String getCreatedOn() {
		return createdOn;
	}
 
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
 
	public String getUpdatedBy() {
		return updatedBy;
	}
 
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
 
	public String getUpdatedOn() {
		return updatedOn;
	}
 
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
 
	public String getCreatedBy() {
		return createdBy;
	}
 
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
