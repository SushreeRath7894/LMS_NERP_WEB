package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageLegalDocumentModel {

	private String legalId;
	private String documentId;
	private String vendorId;
	private String vehicleId;
	private String notifyId;
	private String lastIssueId;
	private String emailId;
	private String expireDate;
	private String smsId;
	private String chargeId;
	private String fileUpload;
	private String sms; 
	private String email;
	 
	
	  public String getSms() {
		  return sms; 
		  }
	  
	  public void setSms(String sms) { 
		  this.sms = sms; 
		  }
	  
	  public String getEmail() {
		  return email;
		  }
	  
	  public void setEmail(String email) { 
		  this.email = email;
		  }
	 


	public String getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getLegalId() {
		return legalId;
	}

	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getLastIssueId() {
		return lastIssueId;
	}

	public void setLastIssueId(String lastIssueId) {
		this.lastIssueId = lastIssueId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public void setCreatedBy(String userId) {
		// TODO Auto-generated method stub

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
