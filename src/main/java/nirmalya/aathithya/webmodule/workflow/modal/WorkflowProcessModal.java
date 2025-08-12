package nirmalya.aathithya.webmodule.workflow.modal;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkflowProcessModal {
	
	private String workflowData;
	private String userId;
	private String organization;
	private String orgDivision;
	public String getWorkflowData() {
		return workflowData;
	}
	public void setWorkflowData(String workflowData) {
		this.workflowData = workflowData;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
