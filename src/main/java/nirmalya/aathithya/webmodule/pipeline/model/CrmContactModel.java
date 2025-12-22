package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
public class CrmContactModel {
	
	private String pipelineId;
	private String contactId;
	private String contactOwner;
	private String contactOwnerId;
	private String leadSource;
	private String firstName;
	private String lastName;
	private String accountId;
	private String accountName;
	private String title;
	private String email;
	private String contactName;
	
	private String vendorName;
	private String department;
	private String phone;
	private String homePhone;
	private String otherPhone;
	private String fax;
	private String mobile;
	private String dateBirth;
	private String assistant;
	private String assistPhone;
	private String emailOpt;
	private String skypeId;
	private String secondaryEmail;
	private String twitter;
	private String reportingTo;
	
	private String mailingStreet;
	private String otherMailing;
	private String mailingCity;
	private String otherCity;
	
	private String mailingState;
	private String otherState;
	private String mailingZip;
	private String otherZip;
	
	private String mailingCountry;
	private String otherCountry;
	
	private String createdBy;
	private String updatedBy;
	private String description;
	private String action;
	private String createdDate;
	private String createdTime;
	private String ownerImage;
	private String referenceContact;
	private String updatedDate;
	
	private String totalPageno;
	private String totalOrderById;
	
	private String organization;
	private String orgDivision;
	private String customerId; 
	private String projectName;
	
	private String salesCustName;
	private String salesCustPhone; 
	private String salesCustEmail;
	
	public String getSalesCustName() {
		return salesCustName;
	}
	public void setSalesCustName(String salesCustName) {
		this.salesCustName = salesCustName;
	}
	public String getSalesCustPhone() {
		return salesCustPhone;
	}
	public void setSalesCustPhone(String salesCustPhone) {
		this.salesCustPhone = salesCustPhone;
	}
	public String getSalesCustEmail() {
		return salesCustEmail;
	}
	public void setSalesCustEmail(String salesCustEmail) {
		this.salesCustEmail = salesCustEmail;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContactOwnerId() {
		return contactOwnerId;
	}
	public void setContactOwnerId(String contactOwnerId) {
		this.contactOwnerId = contactOwnerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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


	public String getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}


	public String getReferenceContact() {
		return referenceContact;
	}


	public void setReferenceContact(String referenceContact) {
		this.referenceContact = referenceContact;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public String getOwnerImage() {
		return ownerImage;
	}


	public void setOwnerImage(String ownerImage) {
		this.ownerImage = ownerImage;
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


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}




	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}




	public CrmContactModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
	public String getPipelineId() {
		return pipelineId;
	}




	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}




	public String getContactOwner() {
		return contactOwner;
	}




	public void setContactOwner(String contactOwner) {
		this.contactOwner = contactOwner;
	}




	public String getLeadSource() {
		return leadSource;
	}




	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getAccountName() {
		return accountName;
	}




	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getDepartment() {
		return department;
	}




	public void setDepartment(String department) {
		this.department = department;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getHomePhone() {
		return homePhone;
	}




	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}




	public String getOtherPhone() {
		return otherPhone;
	}




	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}




	public String getFax() {
		return fax;
	}




	public void setFax(String fax) {
		this.fax = fax;
	}




	public String getMobile() {
		return mobile;
	}




	public void setMobile(String mobile) {
		this.mobile = mobile;
	}




	public String getDateBirth() {
		return dateBirth;
	}




	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}




	public String getAssistant() {
		return assistant;
	}




	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}




	public String getAssistPhone() {
		return assistPhone;
	}




	public void setAssistPhone(String assistPhone) {
		this.assistPhone = assistPhone;
	}




	public String getEmailOpt() {
		return emailOpt;
	}




	public void setEmailOpt(String emailOpt) {
		this.emailOpt = emailOpt;
	}




	public String getSkypeId() {
		return skypeId;
	}




	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}




	public String getSecondaryEmail() {
		return secondaryEmail;
	}




	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}




	public String getTwitter() {
		return twitter;
	}




	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}




	public String getReportingTo() {
		return reportingTo;
	}




	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}




	public String getMailingStreet() {
		return mailingStreet;
	}




	public void setMailingStreet(String mailingStreet) {
		this.mailingStreet = mailingStreet;
	}




	public String getOtherMailing() {
		return otherMailing;
	}




	public void setOtherMailing(String otherMailing) {
		this.otherMailing = otherMailing;
	}




	public String getMailingCity() {
		return mailingCity;
	}




	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}




	public String getOtherCity() {
		return otherCity;
	}




	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}




	public String getMailingState() {
		return mailingState;
	}




	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}




	public String getOtherState() {
		return otherState;
	}




	public void setOtherState(String otherState) {
		this.otherState = otherState;
	}




	public String getMailingZip() {
		return mailingZip;
	}




	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}




	public String getOtherZip() {
		return otherZip;
	}




	public void setOtherZip(String otherZip) {
		this.otherZip = otherZip;
	}




	public String getMailingCountry() {
		return mailingCountry;
	}




	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}




	public String getOtherCountry() {
		return otherCountry;
	}




	public void setOtherCountry(String otherCountry) {
		this.otherCountry = otherCountry;
	}




	public String getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
