package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectCreationWebModelV1 {
	private String projectId;
	private String projectName;
	private String projectIncharge;
	private String date;
	private String status;
	private String projectType;
	private String pinCode;
	private String address;
	private String createdBy;
	private String CreatedOn;
	private String UpdatedBy;
	private String UpdatedOn;
	private String OrganizationName;
	private String OrganizationDivision;
	List<ProjectFileuploadModelV1> documentList1;
	
	public ProjectCreationWebModelV1() {
		super();
	}

	
	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getProjectType() {
		return projectType;
	}


	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}


	public String getPinCode() {
		return pinCode;
	}


	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedOn() {
		return CreatedOn;
	}


	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}


	public String getUpdatedBy() {
		return UpdatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}


	public String getUpdatedOn() {
		return UpdatedOn;
	}


	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
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
	


	public List<ProjectFileuploadModelV1> getDocumentList1() {
		return documentList1;
	}


	public void setDocumentList1(List<ProjectFileuploadModelV1> documentList1) {
		this.documentList1 = documentList1;
	}


	public String getProjectIncharge() {
		return projectIncharge;
	}


	public void setProjectIncharge(String projectIncharge) {
		this.projectIncharge = projectIncharge;
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
