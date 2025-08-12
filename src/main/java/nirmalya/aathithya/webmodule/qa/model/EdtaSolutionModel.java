package nirmalya.aathithya.webmodule.qa.model;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
public class EdtaSolutionModel {
	
	private String edtacalciumId;
	private String revisionNumber;
	private String apstatus;
	private String approveby;
	private String dateOfIssue;
	private String average;
	private String dateOfPreparation;
	private String dateOfph;
	private String signature;
	private String issueDate;
	private String revisedDate;
	private String annecxure;
	private String wtofcaco3g;
	private String volIntitration;
	private String calculation;
	private String dateOfIssue2;
	private String average2;
	private String dateOfPreparation2;
	private String dateOfph2;
	private String signature2;
	private String wtofcaco3g2;
	private String volIntitration2;
	private String calculation2;
	
	private String slNo;
	private String createdBy;
	private String organization;
	private String orgDivision;
	private String type;
	
	
	
	public EdtaSolutionModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEdtacalciumId() {
		return edtacalciumId;
	}


	public void setEdtacalciumId(String edtacalciumId) {
		this.edtacalciumId = edtacalciumId;
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




	public String getAnnecxure() {
		return annecxure;
	}


	public void setAnnecxure(String annecxure) {
		this.annecxure = annecxure;
	}


	public String getRevisionNumber() {
		return revisionNumber;
	}


	public void setRevisionNumber(String revisionNumber) {
		this.revisionNumber = revisionNumber;
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


	public String getWtofcaco3g() {
		return wtofcaco3g;
	}


	public void setWtofcaco3g(String wtofcaco3g) {
		this.wtofcaco3g = wtofcaco3g;
	}


	public String getVolIntitration() {
		return volIntitration;
	}


	public void setVolIntitration(String volIntitration) {
		this.volIntitration = volIntitration;
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


	public String getWtofcaco3g2() {
		return wtofcaco3g2;
	}


	public void setWtofcaco3g2(String wtofcaco3g2) {
		this.wtofcaco3g2 = wtofcaco3g2;
	}


	public String getVolIntitration2() {
		return volIntitration2;
	}


	public void setVolIntitration2(String volIntitration2) {
		this.volIntitration2 = volIntitration2;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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
