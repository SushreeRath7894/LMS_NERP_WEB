package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SulphuricAcidModel {

	
	private String sulphuricAcidId;
	private String normalityOfNaoh;
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
	private String volNh2so4;
	private String volNnaoh;
	private String calculation;
	private String apstatus;
	private String slNo;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String alcoholicNaohId;
	private String weightOfBa;
	private String volAlcoholicNnaoh;
	
	
	public SulphuricAcidModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getSulphuricAcidId() {
		return sulphuricAcidId;
	}


	public void setSulphuricAcidId(String sulphuricAcidId) {
		this.sulphuricAcidId = sulphuricAcidId;
	}


	public String getNormalityOfNaoh() {
		return normalityOfNaoh;
	}


	public void setNormalityOfNaoh(String normalityOfNaoh) {
		this.normalityOfNaoh = normalityOfNaoh;
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


	public String getVolNh2so4() {
		return volNh2so4;
	}


	public void setVolNh2so4(String volNh2so4) {
		this.volNh2so4 = volNh2so4;
	}


	public String getVolNnaoh() {
		return volNnaoh;
	}


	public void setVolNnaoh(String volNnaoh) {
		this.volNnaoh = volNnaoh;
	}


	public String getCalculation() {
		return calculation;
	}


	public void setCalculation(String calculation) {
		this.calculation = calculation;
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


	public String getAlcoholicNaohId() {
		return alcoholicNaohId;
	}


	public void setAlcoholicNaohId(String alcoholicNaohId) {
		this.alcoholicNaohId = alcoholicNaohId;
	}


	public String getWeightOfBa() {
		return weightOfBa;
	}


	public void setWeightOfBa(String weightOfBa) {
		this.weightOfBa = weightOfBa;
	}


	public String getVolAlcoholicNnaoh() {
		return volAlcoholicNnaoh;
	}


	public void setVolAlcoholicNnaoh(String volAlcoholicNnaoh) {
		this.volAlcoholicNnaoh = volAlcoholicNnaoh;
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
