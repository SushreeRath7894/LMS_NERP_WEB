package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmLeadTaskModel {
	private String taskId;
	private String leadId;
	private String taskOwner;
	private String pageType;
	private String taskLead;
	private String taskSubject;
	private String dueDate;
	private String taskContactName;
	private String taskAccountName;
	private String taskStatus;
	private String taskPriority;
	private String reminderYesOrNo;	
	private String RepeateYesOrNo;
	private String reminderDateid;
	private String reminderTime;
	private String taskAlertBy;
	private String description;
	private String createdBy;
	private String createdOn;
	private String action;
	private String contactId;
	private String accountId;
	private String ownerName;
	private String taskDealId;
	private String taskContactId;
	private String taskAccountId;
	
	private String quoteId;
	private String soId;
	private String poId;
	private String invoiceId;
	
	
	private String totalPageno;
	private String totalOrderById;
	
	private String organization;
	private String orgDivision;
	
	
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

	public String getTotalPageno() {
		return totalPageno;
	}

	public void setTotalPageno(String totalPageno) {
		this.totalPageno = totalPageno;
	}

	public String getTotalOrderById() {
		return totalOrderById;
	}

	public void setTotalOrderById(String totalOrderById) {
		this.totalOrderById = totalOrderById;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTaskAccountId() {
		return taskAccountId;
	}

	public void setTaskAccountId(String taskAccountId) {
		this.taskAccountId = taskAccountId;
	}

	public String getTaskContactId() {
		return taskContactId;
	}

	public void setTaskContactId(String taskContactId) {
		this.taskContactId = taskContactId;
	}

	public String getTaskDealId() {
		return taskDealId;
	}

	public void setTaskDealId(String taskDealId) {
		this.taskDealId = taskDealId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getTaskLead() {
		return taskLead;
	}

	public void setTaskLead(String taskLead) {
		this.taskLead = taskLead;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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

	public String getReminderYesOrNo() {
		return reminderYesOrNo;
	}

	public void setReminderYesOrNo(String reminderYesOrNo) {
		this.reminderYesOrNo = reminderYesOrNo;
	}

	public String getRepeateYesOrNo() {
		return RepeateYesOrNo;
	}

	public void setRepeateYesOrNo(String repeateYesOrNo) {
		RepeateYesOrNo = repeateYesOrNo;
	}

	public String getReminderDateid() {
		return reminderDateid;
	}

	public void setReminderDateid(String reminderDateid) {
		this.reminderDateid = reminderDateid;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getTaskAlertBy() {
		return taskAlertBy;
	}

	public void setTaskAlertBy(String taskAlertBy) {
		this.taskAlertBy = taskAlertBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
