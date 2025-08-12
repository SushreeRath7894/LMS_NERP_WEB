package nirmalya.aathithya.webmodule.edms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkFlowModel {
	private String documentId;
	private String resolution;
	private String employeeId;
	private String workFlowFolder;
	private String userId;
	private String organization; 
	private String orgDiv;
	private List<WorkFlowModel> workFowData;
	private String docId;
	private String docType;
	private String orderNo;
	private String type;
	private String status;
	private String workFlowId;
	private String parentStatus ;

	
	public WorkFlowModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getWorkFlowFolder() {
		return workFlowFolder;
	}

	public void setWorkFlowFolder(String workFlowFolder) {
		this.workFlowFolder = workFlowFolder;
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

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	
	

	

	public List<WorkFlowModel> getWorkFowData() {
		return workFowData;
	}

	public void setWorkFowData(List<WorkFlowModel> workFowData) {
		this.workFowData = workFowData;
	}
	
	

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}
	
	

	public String getParentStatus() {
		return parentStatus;
	}

	public void setParentStatus(String parentStatus) {
		this.parentStatus = parentStatus;
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
