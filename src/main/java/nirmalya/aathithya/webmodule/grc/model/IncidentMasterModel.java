package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentMasterModel {

	private String incidentCode;
	private String incidentType;
	private String incidentDesc;
	private String status;
	private String organization;
	private String orgDivision;
	private String createdBy;
	
	
	public IncidentMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getIncidentCode() {
		return incidentCode;
	}


	public void setIncidentCode(String incidentCode) {
		this.incidentCode = incidentCode;
	}


	public String getIncidentType() {
		return incidentType;
	}


	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}


	public String getIncidentDesc() {
		return incidentDesc;
	}


	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
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
