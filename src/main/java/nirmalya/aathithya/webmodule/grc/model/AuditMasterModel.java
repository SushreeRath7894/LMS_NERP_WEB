package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuditMasterModel {
	private String auditorId;
	private String auditorName;
	private String auditTypeId;
	private String agencyId;
	private String specialization;
	private String startDate;
	private String experience;
	private String mobileno;
	private String email;
	private String address;
	private String uploadedBillDiv;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String organizationName;
	private String organizationDivision;

	// params for audit category data
	private String audit_category_id;
	private String auditor_type;
	private String audit_category_code;
	private String audit_category_name;
	private String audit_category_objective;
	private String audit_category_status;

	private String inspectionorId;

	public AuditMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public String getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUploadedBillDiv() {
		return uploadedBillDiv;
	}

	public void setUploadedBillDiv(String uploadedBillDiv) {
		this.uploadedBillDiv = uploadedBillDiv;
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

	public String getAudit_category_id() {
		return audit_category_id;
	}

	public void setAudit_category_id(String audit_category_id) {
		this.audit_category_id = audit_category_id;
	}

	public String getAuditor_type() {
		return auditor_type;
	}

	public void setAuditor_type(String auditor_type) {
		this.auditor_type = auditor_type;
	}

	public String getAudit_category_code() {
		return audit_category_code;
	}

	public void setAudit_category_code(String audit_category_code) {
		this.audit_category_code = audit_category_code;
	}

	public String getAudit_category_name() {
		return audit_category_name;
	}

	public void setAudit_category_name(String audit_category_name) {
		this.audit_category_name = audit_category_name;
	}

	public String getAudit_category_objective() {
		return audit_category_objective;
	}

	public void setAudit_category_objective(String audit_category_objective) {
		this.audit_category_objective = audit_category_objective;
	}

	public String getAudit_category_status() {
		return audit_category_status;
	}

	public void setAudit_category_status(String audit_category_status) {
		this.audit_category_status = audit_category_status;
	}

	public String getInspectionorId() {
		return inspectionorId;
	}

	public void setInspectionorId(String inspectionorId) {
		this.inspectionorId = inspectionorId;
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
