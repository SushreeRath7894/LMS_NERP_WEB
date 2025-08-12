package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentCasualAnalysisModel {

	private String assignNo;	
	private String assignTo;
	private String task;
	private String dueDate;
	private String remarks;
	private String casualAnalysis;
	private String status;
	private String closeDate;
	private String organization;
	private String orgDivision;
	private String createdBy;
	
	public IncidentCasualAnalysisModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getAssignNo() {
		return assignNo;
	}


	public void setAssignNo(String assignNo) {
		this.assignNo = assignNo;
	}


	public String getAssignTo() {
		return assignTo;
	}


	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getCasualAnalysis() {
		return casualAnalysis;
	}

	public void setCasualAnalysis(String casualAnalysis) {
		this.casualAnalysis = casualAnalysis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
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
