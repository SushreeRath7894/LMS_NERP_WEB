package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class taskCrmReportModel {
	private String taskId;
	private String taskExcutive; 
	private String taskLead;
	private String taskDecisionMaker;
	private String taskDecisionMakerName;
	private String taskLeadName;
	private String taskSubject;
	private String taskDeliveryDate;
	private String taskContactName; 
	private String taskAccountName;
	private String taskStatus; 
	private String taskPriority;
	private String taskRepeate;
	private String taskReminder;
	private String taskDesc;
	private String taskCreatedBy;
	private String taskCreatedOn;
	private String orgName;
	private String orgDiv;
	
// check in 
	private String taskLongitude;
	private String taskLatitude;
	private String taskCheckDate;
	private String taskCheckTime;
	private String ckeckAddress;
	private String ckeckDesc;
	
	public taskCrmReportModel() {
		super();
	}
 
	public String getTaskCheckDate() {
		return taskCheckDate;
	}
	public void setTaskCheckDate(String taskCheckDate) {
		this.taskCheckDate = taskCheckDate;
	}
	public String getCkeckDesc() {
		return ckeckDesc;
	}
	public void setCkeckDesc(String ckeckDesc) {
		this.ckeckDesc = ckeckDesc;
	}
	public String getTaskLongitude() {
		return taskLongitude;
	}
	public void setTaskLongitude(String taskLongitude) {
		this.taskLongitude = taskLongitude;
	}
	public String getTaskLatitude() {
		return taskLatitude;
	}
	public void setTaskLatitude(String taskLatitude) {
		this.taskLatitude = taskLatitude;
	}
	public String getTaskCheckTime() {
		return taskCheckTime;
	}
	public void setTaskCheckTime(String taskCheckTime) {
		this.taskCheckTime = taskCheckTime;
	}
	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getTaskExcutive() {
		return taskExcutive;
	}


	public void setTaskExcutive(String taskExcutive) {
		this.taskExcutive = taskExcutive;
	}


	public String getTaskLead() {
		return taskLead;
	}


	public void setTaskLead(String taskLead) {
		this.taskLead = taskLead;
	}


	public String getTaskLeadName() {
		return taskLeadName;
	}
	public void setTaskLeadName(String taskLeadName) {
		this.taskLeadName = taskLeadName;
	}
	public String getTaskSubject() {
		return taskSubject;
	}


	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}


	public String getTaskDeliveryDate() {
		return taskDeliveryDate;
	}


	public void setTaskDeliveryDate(String taskDeliveryDate) {
		this.taskDeliveryDate = taskDeliveryDate;
	}


	public String getTaskContactName() {
		return taskContactName;
	}


	public void setTaskContactName(String taskContactName) {
		this.taskContactName = taskContactName;
	}


	public String getTaskAccountName() {
		return taskAccountName;
	}


	public void setTaskAccountName(String taskAccountName) {
		this.taskAccountName = taskAccountName;
	}


	public String getTaskStatus() {
		return taskStatus;
	}


	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}


	public String getTaskPriority() {
		return taskPriority;
	}


	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}


	public String getTaskRepeate() {
		return taskRepeate;
	}


	public void setTaskRepeate(String taskRepeate) {
		this.taskRepeate = taskRepeate;
	}


	public String getTaskReminder() {
		return taskReminder;
	}


	public void setTaskReminder(String taskReminder) {
		this.taskReminder = taskReminder;
	}


	public String getTaskDesc() {
		return taskDesc;
	}


	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}


	public String getTaskCreatedBy() {
		return taskCreatedBy;
	}


	public void setTaskCreatedBy(String taskCreatedBy) {
		this.taskCreatedBy = taskCreatedBy;
	}


	public String getTaskCreatedOn() {
		return taskCreatedOn;
	}


	public void setTaskCreatedOn(String taskCreatedOn) {
		this.taskCreatedOn = taskCreatedOn;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgDiv() {
		return orgDiv;
	}


	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	
	public String getTaskDecisionMaker() {
		return taskDecisionMaker;
	}
	public void setTaskDecisionMaker(String taskDecisionMaker) {
		this.taskDecisionMaker = taskDecisionMaker;
	}
	
	public String getTaskDecisionMakerName() {
		return taskDecisionMakerName;
	}
	public void setTaskDecisionMakerName(String taskDecisionMakerName) {
		this.taskDecisionMakerName = taskDecisionMakerName;
	}
	
	public String getCkeckAddress() {
		return ckeckAddress;
	}
	public void setCkeckAddress(String ckeckAddress) {
		this.ckeckAddress = ckeckAddress;
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

