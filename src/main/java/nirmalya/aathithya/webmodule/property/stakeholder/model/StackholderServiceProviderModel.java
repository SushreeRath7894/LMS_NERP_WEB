package nirmalya.aathithya.webmodule.property.stakeholder.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StackholderServiceProviderModel {
	private String propid;
	private String vndrid;
	private String vndrname;
	private String servicecategory;
	private String gstin;
	private String address;
	private String email;
	private String contperson;
	private String mobileno;
	private String remarks;
	private String createdby;
	private String createdOn;
	

	public String getPropid() {
		return propid;
	}


	public void setPropid(String propid) {
		this.propid = propid;
	}


	public String getVndrid() {
		return vndrid;
	}


	public void setVndrid(String vndrid) {
		this.vndrid = vndrid;
	}


	public String getVndrname() {
		return vndrname;
	}


	public void setVndrname(String vndrname) {
		this.vndrname = vndrname;
	}


	public String getServicecategory() {
		return servicecategory;
	}


	public void setServicecategory(String servicecategory) {
		this.servicecategory = servicecategory;
	}


	public String getGstin() {
		return gstin;
	}


	public void setGstin(String gstin) {
		this.gstin = gstin;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContperson() {
		return contperson;
	}


	public void setContperson(String contperson) {
		this.contperson = contperson;
	}


	public String getMobileno() {
		return mobileno;
	}


	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getCreatedby() {
		return createdby;
	}


	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
