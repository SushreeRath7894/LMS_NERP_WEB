package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OtherBenifitsModel {

	private String benifitId;
	private String empid;
	private String categoryId;
	private String benifit;
	private String effectiveDate;
	private String remark;
	private String createdBy;
	private String createdOn;
	private String organization;
	private String orgDivision;
	private String freqencyob;
	
	
	public OtherBenifitsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String getFreqencyob() {
		return freqencyob;
	}




	public void setFreqencyob(String freqencyob) {
		this.freqencyob = freqencyob;
	}




	public String getBenifitId() {
		return benifitId;
	}




	public void setBenifitId(String benifitId) {
		this.benifitId = benifitId;
	}




	public String getEmpid() {
		return empid;
	}




	public void setEmpid(String empid) {
		this.empid = empid;
	}




	public String getCategoryId() {
		return categoryId;
	}




	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}




	public String getBenifit() {
		return benifit;
	}




	public void setBenifit(String benifit) {
		this.benifit = benifit;
	}




	public String getEffectiveDate() {
		return effectiveDate;
	}




	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}




	public String getCreatedOn() {
		return createdOn;
	}




	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
