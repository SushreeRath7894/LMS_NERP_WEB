/**
 * 
 */
package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class AccountCusModel {
	private String customerId;
	private String customerType;
	private String salutation;
	private String customerName;
	private String companyName;
	private String customerDisplayName;
	private String cusEmail;
	private String custMobile;
	private String custSkype;
	private String custDesignation;
	private String department;
	private String webSite;
	private String status;
	private String pan;
	private String currency;
	private String openingBalance;
	private String tdsRate;
	private String paymentTerms;
	private String enableDtls;
	private String portableLang;
	private String faceBook;
	private String twitter;
	private String country;
	private String states;
	private String city;
	private String street1;
	private String street2;
	private String zipCode;
	private String phone;
	private String fax;

	private String country1;
	private String states1;
	private String city1;
	private String street11;
	private String street21;
	private String zipCode1;
	private String phone1;
	private String fax1;

	private String salutation1;
	private String firstName;
	private String lastName;
	private String emailAdd;
	private String mobile;
	private String remarks;

	private String createdDate;
	private String createdTime;
	private String createdBy;

	private String shippingDetails;
	private String pointOfContact;
	private String gstIn;

	public String getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public String getPointOfContact() {
		return pointOfContact;
	}

	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}

	public String getCountry1() {
		return country1;
	}

	public void setCountry1(String country1) {
		this.country1 = country1;
	}

	public String getStates1() {
		return states1;
	}

	public void setStates1(String states1) {
		this.states1 = states1;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getStreet11() {
		return street11;
	}

	public void setStreet11(String street11) {
		this.street11 = street11;
	}

	public String getStreet21() {
		return street21;
	}

	public void setStreet21(String street21) {
		this.street21 = street21;
	}

	public String getZipCode1() {
		return zipCode1;
	}

	public void setZipCode1(String zipCode1) {
		this.zipCode1 = zipCode1;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerDisplayName() {
		return customerDisplayName;
	}

	public void setCustomerDisplayName(String customerDisplayName) {
		this.customerDisplayName = customerDisplayName;
	}

	public String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getCustSkype() {
		return custSkype;
	}

	public void setCustSkype(String custSkype) {
		this.custSkype = custSkype;
	}

	public String getCustDesignation() {
		return custDesignation;
	}

	public void setCustDesignation(String custDesignation) {
		this.custDesignation = custDesignation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getTdsRate() {
		return tdsRate;
	}

	public void setTdsRate(String tdsRate) {
		this.tdsRate = tdsRate;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getEnableDtls() {
		return enableDtls;
	}

	public void setEnableDtls(String enableDtls) {
		this.enableDtls = enableDtls;
	}

	public String getPortableLang() {
		return portableLang;
	}

	public void setPortableLang(String portableLang) {
		this.portableLang = portableLang;
	}

	public String getFaceBook() {
		return faceBook;
	}

	public void setFaceBook(String faceBook) {
		this.faceBook = faceBook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
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

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSalutation1() {
		return salutation1;
	}

	public void setSalutation1(String salutation1) {
		this.salutation1 = salutation1;
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

	public String getEmailAdd() {
		return emailAdd;
	}

	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
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
