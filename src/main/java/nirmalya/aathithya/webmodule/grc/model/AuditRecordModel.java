package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditRecordModel { 

	private String audit_record_id;
	private String audit_schedule_id;
	private String audit_plan_id;
	private String audit_record_activity;
	private String audit_record_template; 
	private String auditee_Id; 
	private String audit_record_remarks;
	private String audit_record_dueDate;
	private String audit_record_status; 
	private String audit_record_publish_status;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String organizationName;
	private String organizationDivision;
	
	public AuditRecordModel() {
		super();
	}
	
	public String getAudit_record_id() {
		return audit_record_id;
	}

	public void setAudit_record_id(String audit_record_id) {
		this.audit_record_id = audit_record_id;
	}

	public String getAudit_schedule_id() {
		return audit_schedule_id;
	}

	public void setAudit_schedule_id(String audit_schedule_id) {
		this.audit_schedule_id = audit_schedule_id;
	}

	public String getAudit_plan_id() {
		return audit_plan_id;
	}

	public void setAudit_plan_id(String audit_plan_id) {
		this.audit_plan_id = audit_plan_id;
	}

	public String getAudit_record_activity() {
		return audit_record_activity;
	}

	public void setAudit_record_activity(String audit_record_activity) {
		this.audit_record_activity = audit_record_activity;
	}

	public String getAudit_record_template() {
		return audit_record_template;
	}

	public void setAudit_record_template(String audit_record_template) {
		this.audit_record_template = audit_record_template;
	}

	public String getAuditee_Id() {
		return auditee_Id;
	}

	public void setAuditee_Id(String auditee_Id) {
		this.auditee_Id = auditee_Id;
	}

	public String getAudit_record_remarks() {
		return audit_record_remarks;
	}

	public void setAudit_record_remarks(String audit_record_remarks) {
		this.audit_record_remarks = audit_record_remarks;
	}

	public String getAudit_record_dueDate() {
		return audit_record_dueDate;
	}

	public void setAudit_record_dueDate(String audit_record_dueDate) {
		this.audit_record_dueDate = audit_record_dueDate;
	}

	public String getAudit_record_status() {
		return audit_record_status;
	}

	public void setAudit_record_status(String audit_record_status) {
		this.audit_record_status = audit_record_status;
	}
	
	public String getAudit_record_publish_status() {
		return audit_record_publish_status;
	}

	public void setAudit_record_publish_status(String audit_record_publish_status) {
		this.audit_record_publish_status = audit_record_publish_status;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return organizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
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
