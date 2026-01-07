package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmCustomerModel {

	private String customerId;
	private String executive;
	private String customerName;
	private String companyName;
	private String companyOwner;
	private String customerMail;
	private String customerPhone;
	private String designation;
	private String companyGst;
	private String companyPAN;
	private String referenceContact;
	private String leadSource;
	private String leadStatus;
	private String industry;
	private String decisionMaker1;
	private String email1;
	private String phone1;
	private String designation1;
	private String dob1;
	private String marriageAnniversary1;
	
	private String decisionMaker2;
	private String email2;
	private String phone2;
	private String designation2;
	private String dob2;
	private String marriageAnniversary2;
	
	private String decisionMaker3;
	private String email3;
	private String phone3;
	private String designation3;
	private String dob3;
	private String marriageAnniversary3;
	
	private String country;
	private String states;
	private String city;
	private String addressStreet;
	private String zip;
	private String description;
	
	private String createdDate;
	private String updatedDate;
	private String createdBy;

	private String createdTime;
	
	

	public String getCreatedTime() {
		return createdTime;
	}





	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getCustomerId() {
		return customerId;
	}





	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}





	public String getExecutive() {
		return executive;
	}





	public void setExecutive(String executive) {
		this.executive = executive;
	}





	public String getCustomerName() {
		return customerName;
	}





	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}





	public String getCompanyName() {
		return companyName;
	}





	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}





	public String getCompanyOwner() {
		return companyOwner;
	}





	public void setCompanyOwner(String companyOwner) {
		this.companyOwner = companyOwner;
	}





	public String getCustomerMail() {
		return customerMail;
	}





	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}





	public String getCustomerPhone() {
		return customerPhone;
	}





	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}





	public String getDesignation() {
		return designation;
	}





	public void setDesignation(String designation) {
		this.designation = designation;
	}





	public String getCompanyGst() {
		return companyGst;
	}





	public void setCompanyGst(String companyGst) {
		this.companyGst = companyGst;
	}





	public String getCompanyPAN() {
		return companyPAN;
	}





	public void setCompanyPAN(String companyPAN) {
		this.companyPAN = companyPAN;
	}





	public String getReferenceContact() {
		return referenceContact;
	}





	public void setReferenceContact(String referenceContact) {
		this.referenceContact = referenceContact;
	}





	public String getLeadSource() {
		return leadSource;
	}





	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}





	public String getLeadStatus() {
		return leadStatus;
	}





	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}





	public String getIndustry() {
		return industry;
	}





	public void setIndustry(String industry) {
		this.industry = industry;
	}





	public String getDecisionMaker1() {
		return decisionMaker1;
	}





	public void setDecisionMaker1(String decisionMaker1) {
		this.decisionMaker1 = decisionMaker1;
	}





	public String getEmail1() {
		return email1;
	}





	public void setEmail1(String email1) {
		this.email1 = email1;
	}





	public String getPhone1() {
		return phone1;
	}





	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}





	public String getDesignation1() {
		return designation1;
	}





	public void setDesignation1(String designation1) {
		this.designation1 = designation1;
	}





	public String getDob1() {
		return dob1;
	}





	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}





	public String getMarriageAnniversary1() {
		return marriageAnniversary1;
	}





	public void setMarriageAnniversary1(String marriageAnniversary1) {
		this.marriageAnniversary1 = marriageAnniversary1;
	}





	public String getDecisionMaker2() {
		return decisionMaker2;
	}





	public void setDecisionMaker2(String decisionMaker2) {
		this.decisionMaker2 = decisionMaker2;
	}





	public String getEmail2() {
		return email2;
	}





	public void setEmail2(String email2) {
		this.email2 = email2;
	}





	public String getPhone2() {
		return phone2;
	}





	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}





	public String getDesignation2() {
		return designation2;
	}





	public void setDesignation2(String designation2) {
		this.designation2 = designation2;
	}





	public String getDob2() {
		return dob2;
	}





	public void setDob2(String dob2) {
		this.dob2 = dob2;
	}





	public String getMarriageAnniversary2() {
		return marriageAnniversary2;
	}





	public void setMarriageAnniversary2(String marriageAnniversary2) {
		this.marriageAnniversary2 = marriageAnniversary2;
	}





	public String getDecisionMaker3() {
		return decisionMaker3;
	}





	public void setDecisionMaker3(String decisionMaker3) {
		this.decisionMaker3 = decisionMaker3;
	}





	public String getEmail3() {
		return email3;
	}





	public void setEmail3(String email3) {
		this.email3 = email3;
	}





	public String getPhone3() {
		return phone3;
	}





	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}





	public String getDesignation3() {
		return designation3;
	}





	public void setDesignation3(String designation3) {
		this.designation3 = designation3;
	}





	public String getDob3() {
		return dob3;
	}





	public void setDob3(String dob3) {
		this.dob3 = dob3;
	}





	public String getMarriageAnniversary3() {
		return marriageAnniversary3;
	}





	public void setMarriageAnniversary3(String marriageAnniversary3) {
		this.marriageAnniversary3 = marriageAnniversary3;
	}





	public String getCountry() {
		return country;
	}





	public void setCountry(String country) {
		this.country = country;
	}





	public String getStates() {
		return states;
	}





	public void setStates(String states) {
		this.states = states;
	}





	public String getCity() {
		return city;
	}





	public void setCity(String city) {
		this.city = city;
	}





	public String getAddressStreet() {
		return addressStreet;
	}





	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}





	public String getZip() {
		return zip;
	}





	public void setZip(String zip) {
		this.zip = zip;
	}





	public String getCreatedDate() {
		return createdDate;
	}





	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}





	public String getUpdatedDate() {
		return updatedDate;
	}





	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}





	public String getCreatedBy() {
		return createdBy;
	}





	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
