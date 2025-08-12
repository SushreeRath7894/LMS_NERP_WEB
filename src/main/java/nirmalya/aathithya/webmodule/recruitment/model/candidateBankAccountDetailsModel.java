package nirmalya.aathithya.webmodule.recruitment.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class candidateBankAccountDetailsModel {
	
	private String accountNo;
	private String bankName;
	private String bankAddress;
	private String ifscCode;
	private String bankId;
	private String candidateid;
	private String orgName;
	private String orgDiv;
	private String createdBy;
	private String document;
	
	public candidateBankAccountDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	
	public String getCandidateid() {
		return candidateid;
	}
	public void setCandidateid(String candidateid) {
		this.candidateid = candidateid;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonStr;
		
		try {
			jsonStr = mapper.writeValueAsString(this);
		}catch(IOException ex) {
			jsonStr = ex.toString();
		}
		return jsonStr;
	}
}
