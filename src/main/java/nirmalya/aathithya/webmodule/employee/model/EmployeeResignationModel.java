package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeResignationModel {
	private String resignationDraftId;
	private String resignationId;
	private String empId;
	private String empName;
	private String regTo;
	private String regCC;
	private String regToName;
	private String regCCName;
	private String subject;
	private String reason;
	private String resignDate;
	private String releaseDate;
	private String createdBy;
	private String status;
	private String organization;
	private String orgDivision;
	private String documentURL;

	private List<EmployeeResignationDocUploadModel> documentList;

	public EmployeeResignationModel() {
		super();
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}
	public List<EmployeeResignationDocUploadModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<EmployeeResignationDocUploadModel> documentList) {
		this.documentList = documentList;
	}

	public String getRegToName() {
		return regToName;
	}

	public void setRegToName(String regToName) {
		this.regToName = regToName;
	}

	public String getRegCCName() {
		return regCCName;
	}

	public void setRegCCName(String regCCName) {
		this.regCCName = regCCName;
	}

	public String getResignationDraftId() {
		return resignationDraftId;
	}

	public void setResignationDraftId(String resignationDraftId) {
		this.resignationDraftId = resignationDraftId;
	}

	public String getResignationId() {
		return resignationId;
	}

	public void setResignationId(String resignationId) {
		this.resignationId = resignationId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRegTo() {
		return regTo;
	}

	public void setRegTo(String regTo) {
		this.regTo = regTo;
	}

	public String getRegCC() {
		return regCC;
	}

	public void setRegCC(String regCC) {
		this.regCC = regCC;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResignDate() {
		return resignDate;
	}

	public void setResignDate(String resignDate) {
		this.resignDate = resignDate;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void data(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
