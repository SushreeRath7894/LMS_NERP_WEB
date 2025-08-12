package nirmalya.aathithya.webmodule.qa.model;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
public class SodiumHydroxideModel {

	
	private String sodiumId;
	private String issueDate;
	private String revisedDate;
	private String revisionNumber;
	private String apstatus;
	private String approveby;
	private String annecxure;
	private String dateOfIssue;
	private String average;
	private String dateOfPreparation;
	private String dateOfph;
	private String signature;
	private String wtofkhp;
	private String volsodium;
	private String p;
	private String calculation;
	private String type;
	
	private String dateOfIssue2;
	private String average2;
	private String dateOfPreparation2;
	private String dateOfph2;
	private String signature2;
	private String wtofkhp2;
	private String volsodium2;
	private String p2;
	private String calculation2;
	
	private String slNo;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	
	public SodiumHydroxideModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getSodiumId() {
		return sodiumId;
	}


	public void setSodiumId(String sodiumId) {
		this.sodiumId = sodiumId;
	}


	public String getP() {
		return p;
	}


	public void setP(String p) {
		this.p = p;
	}


	public String getP2() {
		return p2;
	}


	public void setP2(String p2) {
		this.p2 = p2;
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


	public String getRevisionNumber() {
		return revisionNumber;
	}


	public void setRevisionNumber(String revisionNumber) {
		this.revisionNumber = revisionNumber;
	}


	public String getAnnecxure() {
		return annecxure;
	}


	public void setAnnecxure(String annecxure) {
		this.annecxure = annecxure;
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


	public String getWtofkhp() {
		return wtofkhp;
	}


	public void setWtofkhp(String wtofkhp) {
		this.wtofkhp = wtofkhp;
	}


	public String getVolsodium() {
		return volsodium;
	}


	public void setVolsodium(String volsodium) {
		this.volsodium = volsodium;
	}


	public String getCalculation() {
		return calculation;
	}


	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}


	public String getDateOfIssue2() {
		return dateOfIssue2;
	}


	public void setDateOfIssue2(String dateOfIssue2) {
		this.dateOfIssue2 = dateOfIssue2;
	}


	public String getAverage2() {
		return average2;
	}


	public void setAverage2(String average2) {
		this.average2 = average2;
	}


	public String getDateOfPreparation2() {
		return dateOfPreparation2;
	}


	public void setDateOfPreparation2(String dateOfPreparation2) {
		this.dateOfPreparation2 = dateOfPreparation2;
	}


	public String getDateOfph2() {
		return dateOfph2;
	}


	public void setDateOfph2(String dateOfph2) {
		this.dateOfph2 = dateOfph2;
	}


	public String getSignature2() {
		return signature2;
	}


	public void setSignature2(String signature2) {
		this.signature2 = signature2;
	}


	public String getWtofkhp2() {
		return wtofkhp2;
	}


	public void setWtofkhp2(String wtofkhp2) {
		this.wtofkhp2 = wtofkhp2;
	}


	public String getVolsodium2() {
		return volsodium2;
	}


	public void setVolsodium2(String volsodium2) {
		this.volsodium2 = volsodium2;
	}


	public String getCalculation2() {
		return calculation2;
	}


	public void setCalculation2(String calculation2) {
		this.calculation2 = calculation2;
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
	
	
	public String getApstatus() {
		return apstatus;
	}


	public void setApstatus(String apstatus) {
		this.apstatus = apstatus;
	}


	public String getApproveby() {
		return approveby;
	}


	public void setApproveby(String approveby) {
		this.approveby = approveby;
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
