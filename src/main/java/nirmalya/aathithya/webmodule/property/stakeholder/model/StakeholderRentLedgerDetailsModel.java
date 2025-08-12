package nirmalya.aathithya.webmodule.property.stakeholder.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StakeholderRentLedgerDetailsModel {
	private String rentId;
	private String propNo;
	private String osamount;
	private String year;
	private String month;
	private String payAmount;
	private String rentOs;
	
	private String propName;
	private String tenantName;
	private String address;
	private String recdamount;
	private String Date;
	private String balance;
	private String status;
	private String createdby;
	private String createdOn;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRecdamount() {
		return recdamount;
	}


	public void setRecdamount(String recdamount) {
		this.recdamount = recdamount;
	}


	public String getDate() {
		return Date;
	}


	public void setDate(String date) {
		Date = date;
	}


	public String getBalance() {
		return balance;
	}


	public void setBalance(String balance) {
		this.balance = balance;
	}


	

	public String getPropName() {
		return propName;
	}


	public void setPropName(String propName) {
		this.propName = propName;
	}


	public String getTenantName() {
		return tenantName;
	}


	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	public String getRentId() {
		return rentId;
	}


	public void setRentId(String rentId) {
		this.rentId = rentId;
	}


	public String getPropNo() {
		return propNo;
	}


	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}


	public String getOsamount() {
		return osamount;
	}


	public void setOsamount(String osamount) {
		this.osamount = osamount;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getPayAmount() {
		return payAmount;
	}


	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}


	public String getRentOs() {
		return rentOs;
	}


	public void setRentOs(String rentOs) {
		this.rentOs = rentOs;
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
