package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VendorContactMasterModel {
	private String vendorId;
	private String contactId;
	private String contactName;
	private String contactDesignation;
	private String contactFunction;
	private String location;
	private String phone;
	private String email;
	private String createdOn;
	private String createdBy;
	private String primaryStatusContact;
	private String module;
	private String component;
	private String subcomponent;
	private String pwd;
	private String contactStatus;
	private String OrganizationName; 
	private String OrganizationDivision;
	public VendorContactMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactDesignation() {
		return contactDesignation;
	}
	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	public String getContactFunction() {
		return contactFunction;
	}
	public void setContactFunction(String contactFunction) {
		this.contactFunction = contactFunction;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getPrimaryStatusContact() {
		return primaryStatusContact;
	}
	public void setPrimaryStatusContact(String primaryStatusContact) {
		this.primaryStatusContact = primaryStatusContact;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getSubcomponent() {
		return subcomponent;
	}
	public void setSubcomponent(String subcomponent) {
		this.subcomponent = subcomponent;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	public String getOrganizationName() {
		return OrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}
	public String getOrganizationDivision() {
		return OrganizationDivision;
	}
	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
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
