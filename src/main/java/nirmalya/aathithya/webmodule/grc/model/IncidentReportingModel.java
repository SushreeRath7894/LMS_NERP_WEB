package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentReportingModel {

	private String incidentNo;
	private String date;
	private String type;
	private String description;
	private String reportedBy;
	private String projectId;
	private String severity;
	private String actionTaken;
	private String location;
	private String latitude;
	private String longitude;
	private String status;
	private String organization;
	private String orgDivision;
	private String createdBy;
	
	
	public IncidentReportingModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getIncidentNo() {
		return incidentNo;
	}


	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getReportedBy() {
		return reportedBy;
	}


	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSeverity() {
		return severity;
	}


	public void setSeverity(String severity) {
		this.severity = severity;
	}


	public String getActionTaken() {
		return actionTaken;
	}


	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
