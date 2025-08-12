package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DichlorophenolIndophenolModel {
	private String dcpipId;
	private String dateOfIssue;
	private String average;
	private String dateOfPreparation;
	private String dateOfph;
	private String signature;
	private String type;
	private String issueDate;
	private String revisedDate;
	private String revisionDate;
	private String annecxure;
	private String weightOfAscorbicAcid;
	private String volWorkingStandard;
	private String dilutionD;
	private String v1;
	private String tv;
	private String blank;
	private String dyeEquivalent;
	private String apstatus;
	private String slNo;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	public DichlorophenolIndophenolModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getDcpipId() {
		return dcpipId;
	}

	public void setDcpipId(String dcpipId) {
		this.dcpipId = dcpipId;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getDateOfPreparation() {
		return dateOfPreparation;
	}

	public void setDateOfPreparation(String dateOfPreparation) {
		this.dateOfPreparation = dateOfPreparation;
	}

	public String getDateOfph() {
		return dateOfph;
	}

	public void setDateOfph(String dateOfph) {
		this.dateOfph = dateOfph;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getRevisedDate() {
		return revisedDate;
	}

	public void setRevisedDate(String revisedDate) {
		this.revisedDate = revisedDate;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getAnnecxure() {
		return annecxure;
	}

	public void setAnnecxure(String annecxure) {
		this.annecxure = annecxure;
	}

	public String getWeightOfAscorbicAcid() {
		return weightOfAscorbicAcid;
	}

	public void setWeightOfAscorbicAcid(String weightOfAscorbicAcid) {
		this.weightOfAscorbicAcid = weightOfAscorbicAcid;
	}

	public String getVolWorkingStandard() {
		return volWorkingStandard;
	}

	public void setVolWorkingStandard(String volWorkingStandard) {
		this.volWorkingStandard = volWorkingStandard;
	}

	public String getDilutionD() {
		return dilutionD;
	}

	public void setDilutionD(String dilutionD) {
		this.dilutionD = dilutionD;
	}

	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getDyeEquivalent() {
		return dyeEquivalent;
	}

	public void setDyeEquivalent(String dyeEquivalent) {
		this.dyeEquivalent = dyeEquivalent;
	}

	public String getApstatus() {
		return apstatus;
	}

	public void setApstatus(String apstatus) {
		this.apstatus = apstatus;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
