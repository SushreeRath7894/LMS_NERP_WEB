package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountantSectionWebModel {

	private String reqId;
	private String reqName;
	private String empID;
	private String empName;
	private String amount;
	private String paidAmount;
	private String paymentStatus;
	private String paymentOption;
	private String paymentBy;
	private String paymentDate;
	private double comment;
	
	
	private String createdBy;
	private String createdOn;
	private String organization;
	private String orgDivision;
	
	public AccountantSectionWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String getPaidAmount() {
		return paidAmount;
	}




	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}




	public String getReqId() {
		return reqId;
	}




	public void setReqId(String reqId) {
		this.reqId = reqId;
	}




	public String getReqName() {
		return reqName;
	}




	public void setReqName(String reqName) {
		this.reqName = reqName;
	}




	public String getEmpID() {
		return empID;
	}




	public void setEmpID(String empID) {
		this.empID = empID;
	}




	public String getEmpName() {
		return empName;
	}




	public void setEmpName(String empName) {
		this.empName = empName;
	}




	public String getAmount() {
		return amount;
	}




	public void setAmount(String amount) {
		this.amount = amount;
	}




	public String getPaymentStatus() {
		return paymentStatus;
	}




	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}




	public String getPaymentOption() {
		return paymentOption;
	}




	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}




	public String getPaymentBy() {
		return paymentBy;
	}




	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}




	public String getPaymentDate() {
		return paymentDate;
	}




	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}




	public double getComment() {
		return comment;
	}




	public void setComment(double comment) {
		this.comment = comment;
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
