package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectFileuploadModelV1 {

	
	private String projectId;
	private String documentName;
	private String documentFileName;
	private String slNo;
	
	
	public ProjectFileuploadModelV1() {
		super();
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getDocumentName() {
		return documentName;
	}


	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	public String getDocumentFileName() {
		return documentFileName;
	}


	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}


	public String getSlNo() {
		return slNo;
	}


	public void setSlNo(String slNo) {
		this.slNo = slNo;
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
