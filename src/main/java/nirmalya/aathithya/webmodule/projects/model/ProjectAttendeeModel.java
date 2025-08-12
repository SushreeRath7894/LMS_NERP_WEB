package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectAttendeeModel {
	
	private String nameId;
	private String designation;
	private String OrganizationName;
	private String OrganizationDivision;
	
	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return OrganizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
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
