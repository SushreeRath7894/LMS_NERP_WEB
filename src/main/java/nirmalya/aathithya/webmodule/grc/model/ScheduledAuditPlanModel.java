package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScheduledAuditPlanModel {
	 
	    private String scheduledId;

	    private String taskName;

	    private String planId;

	    private int status;

	    private String taskPriority;

	    private String description;

	    private String taskType;

	    private String taskUom;

	    private String maxRange;

	    private String minRange;
	    
	    private String record;
	    private String reference;

	    private String result;
	    private String date;
	    private String instanceId;
	    private String remark;
	    private String actionPlan;
	    
	    private String base64Image;
	    private String fileName;
	    private String documentUrl;
	    
	
	private String createdBy;
	private String orgName;
	private String orgDivision;
	
	 
  public ScheduledAuditPlanModel() {
	super();
	// TODO Auto-generated constructor stub
		}


public String getScheduledId() {
	return scheduledId;
}


public void setScheduledId(String scheduledId) {
	this.scheduledId = scheduledId;
}


public String getTaskName() {
	return taskName;
}


public void setTaskName(String taskName) {
	this.taskName = taskName;
}


public String getPlanId() {
	return planId;
}


public void setPlanId(String planId) {
	this.planId = planId;
}


 


public int getStatus() {
	return status;
}


public void setStatus(int status) {
	this.status = status;
}


public String getTaskPriority() {
	return taskPriority;
}


public void setTaskPriority(String taskPriority) {
	this.taskPriority = taskPriority;
}


public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


 


public String getTaskType() {
	return taskType;
}


public void setTaskType(String taskType) {
	this.taskType = taskType;
}


public String getTaskUom() {
	return taskUom;
}


public void setTaskUom(String taskUom) {
	this.taskUom = taskUom;
}


public String getMaxRange() {
	return maxRange;
}


public void setMaxRange(String maxRange) {
	this.maxRange = maxRange;
}


public String getMinRange() {
	return minRange;
}


public void setMinRange(String minRange) {
	this.minRange = minRange;
}


public String getResult() {
	return result;
}


public void setResult(String result) {
	this.result = result;
}


public String getCreatedBy() {
	return createdBy;
}


public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}


public String getOrgName() {
	return orgName;
}


public void setOrgName(String orgName) {
	this.orgName = orgName;
}


public String getOrgDivision() {
	return orgDivision;
}


public void setOrgDivision(String orgDivision) {
	this.orgDivision = orgDivision;
}
  

public String getBase64Image() {
	return base64Image;
}


public void setBase64Image(String base64Image) {
	this.base64Image = base64Image;
}



public String getFileName() {
	return fileName;
}


public void setFileName(String fileName) {
	this.fileName = fileName;
}


public String getDocumentUrl() {
	return documentUrl;
}


public void setDocumentUrl(String documentUrl) {
	this.documentUrl = documentUrl;
}


public String getDate() {
	return date;
}


public void setDate(String date) {
	this.date = date;
}


public String getInstanceId() {
	return instanceId;
}


public void setInstanceId(String instanceId) {
	this.instanceId = instanceId;
}


public String getRemark() {
	return remark;
}


public void setRemark(String remark) {
	this.remark = remark;
}


public String getActionPlan() {
	return actionPlan;
}


public void setActionPlan(String actionPlan) {
	this.actionPlan = actionPlan;
}


public String getRecord() {
	return record;
}


public void setRecord(String record) {
	this.record = record;
}


public String getReference() {
	return reference;
}


public void setReference(String reference) {
	this.reference = reference;
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
