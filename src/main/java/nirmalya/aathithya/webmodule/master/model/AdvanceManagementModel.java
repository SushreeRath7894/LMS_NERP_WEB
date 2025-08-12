package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AdvanceManagementModel {
	private String advanceId;
	private String empID;
	private String empName;
	private String advanceApplyDate;
	private String status;
	private String reqPolicyId;
	private String applydate;
	private String eligibility;
	private double loanamt;
	private double loanamtrange;
	private String ternure;
	private String intrestRate;
	private String reason;
	private String createdBy;
	private String createdOn;
	private String approvedDate;
	private String approvedBy;
	private String rejectDate;
	private String rejectedBy;
	private String approveComment;
	private double emi;
	private double totalInterest;
	private String processStatus;
	private String organization;
	private String orgDivision;
	private String dueDate;
	private String dueDateE;
	private String year;
	private String month;
	private String yearMain;
	private String monthMain;

	private String advanceIdList;
	private String dateList;
	
	/*
	 * private String paymentMode; private String bankName; private String
	 * branchName; private String transactionDate; private String accNo; private
	 * String transactionNo; private String chequeNo;
	 */
	


public AdvanceManagementModel() {
	super();
	// TODO Auto-generated constructor stub
}

public String getAdvanceId() {
	return advanceId;
}

public void setAdvanceId(String advanceId) {
	this.advanceId = advanceId;
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

public String getAdvanceApplyDate() {
	return advanceApplyDate;
}

public void setAdvanceApplyDate(String advanceApplyDate) {
	this.advanceApplyDate = advanceApplyDate;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getReqPolicyId() {
	return reqPolicyId;
}

public void setReqPolicyId(String reqPolicyId) {
	this.reqPolicyId = reqPolicyId;
}

public String getApplydate() {
	return applydate;
}

public void setApplydate(String applydate) {
	this.applydate = applydate;
}

public String getEligibility() {
	return eligibility;
}

public void setEligibility(String eligibility) {
	this.eligibility = eligibility;
}

public double getLoanamt() {
	return loanamt;
}

public void setLoanamt(double loanamt) {
	this.loanamt = loanamt;
}

public String getTernure() {
	return ternure;
}

public void setTernure(String ternure) {
	this.ternure = ternure;
}

public String getIntrestRate() {
	return intrestRate;
}

public void setIntrestRate(String intrestRate) {
	this.intrestRate = intrestRate;
}

public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
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

public String getApprovedDate() {
	return approvedDate;
}

public void setApprovedDate(String approvedDate) {
	this.approvedDate = approvedDate;
}

public String getApprovedBy() {
	return approvedBy;
}

public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
}

public String getRejectDate() {
	return rejectDate;
}

public void setRejectDate(String rejectDate) {
	this.rejectDate = rejectDate;
}

public String getRejectedBy() {
	return rejectedBy;
}

public void setRejectedBy(String rejectedBy) {
	this.rejectedBy = rejectedBy;
}

public String getApproveComment() {
	return approveComment;
}

public void setApproveComment(String approveComment) {
	this.approveComment = approveComment;
}
public double getLoanamtrange() {
	return loanamtrange;
}

public void setLoanamtrange(double loanamtrange) {
	this.loanamtrange = loanamtrange;
}

public double getEmi() {
	return emi;
}

public void setEmi(double emi) {
	this.emi = emi;
}

public double getTotalInterest() {
	return totalInterest;
}

public void setTotalInterest(double totalInterest) {
	this.totalInterest = totalInterest;
}

public String getProcessStatus() {
	return processStatus;
}

public void setProcessStatus(String processStatus) {
	this.processStatus = processStatus;
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


public String getDueDate() {
	return dueDate;
}

public void setDueDate(String dueDate) {
	this.dueDate = dueDate;
}

public String getDueDateE() {
	return dueDateE;
}

public void setDueDateE(String dueDateE) {
	this.dueDateE = dueDateE;
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

public String getYearMain() {
	return yearMain;
}

public void setYearMain(String yearMain) {
	this.yearMain = yearMain;
}

public String getMonthMain() {
	return monthMain;
}

public void setMonthMain(String monthMain) {
	this.monthMain = monthMain;
}

public String getAdvanceIdList() {
	return advanceIdList;
}

public void setAdvanceIdList(String advanceIdList) {
	this.advanceIdList = advanceIdList;
}

public String getDateList() {
	return dateList;
}

public void setDateList(String dateList) {
	this.dateList = dateList;
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
