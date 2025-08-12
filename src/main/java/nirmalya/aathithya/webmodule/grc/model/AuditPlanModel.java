package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

 

public class AuditPlanModel {
	
	private String planId;
	private String auditTypeId;
	private String catid;
	private String auditLocation;
	private String startDate;
	private String endDate;
	private String frequency;
	private String assignDate;
	private String remark;
	 
 
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String taskType;
	private String taskUOM;
	 
	private String record;
	private String reference;
	private String minRange;
	private String maxRange;
	private String taskName;
	private String taskPriority;
	private String taskDescription;
	private List<AuditPlanModel> checkList;
	
	public AuditPlanModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
 
  
	 	
	public String getPlanId() {
		return planId;
	}
 
	public void setPlanId(String planId) {
		this.planId = planId;
	}
 
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	  
	public String getAuditTypeId() {
		return auditTypeId;
	}
 

	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}
 
	public String getCatid() {
		return catid;
	}
 

	public void setCatid(String catid) {
		this.catid = catid;
	}
 

	public String getAuditLocation() {
		return auditLocation;
	}
 

	public void setAuditLocation(String auditLocation) {
		this.auditLocation = auditLocation;
	}
 
	public String getStartDate() {
		return startDate;
	}
 
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
 
	public String getEndDate() {
		return endDate;
	}
 
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
 
	public String getFrequency() {
		return frequency;
	}
 

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
 

	public String getAssignDate() {
		return assignDate;
	}
 

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
 

	public String getRemark() {
		return remark;
	}
 
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getTaskType() {
		return taskType;
	}
 
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
 
	public String getTaskUOM() {
		return taskUOM;
	}
 
	public void setTaskUOM(String taskUOM) {
		this.taskUOM = taskUOM;
	}
 
	public String getMinRange() {
		return minRange;
	}
 
	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}
 
	public String getMaxRange() {
		return maxRange;
	}
 

	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}
 
	public String getTaskName() {
		return taskName;
	}
 
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
 
	public String getTaskPriority() {
		return taskPriority;
	}

 
	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}
 
	public String getTaskDescription() {
		return taskDescription;
	}
 
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
 
	public List<AuditPlanModel> getCheckList() {
		return checkList;
	}
 
	public void setCheckList(List<AuditPlanModel> checkList) {
		this.checkList = checkList;
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
