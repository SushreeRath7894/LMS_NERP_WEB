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
public class AccountBranchModel {

	private String branchId;
	private String bank;
	private String branchName;
	private String ifscCode;
	private String contactNo;
	private String email;
	private String status;
	private String country;
	private String states;
	private String city;
	private String addressStreet;
	private String zip;
	private String address;
	private String createdDate;	
	private String createdTime;
	private String createdBy;
	private String orgName;
	private String orgDiv;
	
	

	public String getBranchId() {
		return branchId;
	}




	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}




	public String getBank() {
		return bank;
	}




	public void setBank(String bank) {
		this.bank = bank;
	}




	public String getBranchName() {
		return branchName;
	}




	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}




	public String getIfscCode() {
		return ifscCode;
	}




	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}




	public String getContactNo() {
		return contactNo;
	}




	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
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




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
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




	public String getOrgName() {
		return orgName;
	}




	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}




	public String getOrgDiv() {
		return orgDiv;
	}




	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
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
