package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AdvanceManagementModelNew {

private String reqId;
private String date;
private String eligibility;
private double loanamt;
private String ternure;
private Double intrestRate;
private String raeson;
private String createdBy;
private String status;
private String organization;
private String orgDivision;



public AdvanceManagementModelNew() {
	super();
	// TODO Auto-generated constructor stub
}

public String getReqId() {
	return reqId;
}
public void setReqId(String reqId) {
	this.reqId = reqId;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getEligibility() {
	return eligibility;
}
public void setEligibility(String eligibility) {
	this.eligibility = eligibility;
}
public double getLoanamt() {
	return loanamt;
}
public void setLoanamt(double loanamt) {
	this.loanamt = loanamt;
}
public String getTernure() {
	return ternure;
}
public void setTernure(String ternure) {
	this.ternure = ternure;
}

public Double getIntrestRate() {
	return intrestRate;
}
public void setIntrestRate(Double intrestRate) {
	this.intrestRate = intrestRate;
}
public String getRaeson() {
	return raeson;
}
public void setRaeson(String raeson) {
	this.raeson = raeson;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
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
