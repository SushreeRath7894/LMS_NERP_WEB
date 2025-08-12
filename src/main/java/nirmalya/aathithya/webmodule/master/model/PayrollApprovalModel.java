package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PayrollApprovalModel {
	private String empId;
	private String bandId;
	private String desig;
	private String dept;
	private String subDept;
	private String componetId;
	private String amount;
	private String approvedBy;
	private String fromDate;
	private String toDate;
	private String financialYr;
	private String emplist;
	private String roleid;
	private String organization;
	private String orgDivision;
	public PayrollApprovalModel() {
		super();
	}
	
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getBandId() {
		return bandId;
	}

	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

	public String getComponetId() {
		return componetId;
	}

	public void setComponetId(String componetId) {
		this.componetId = componetId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFinancialYr() {
		return financialYr;
	}
	public void setFinancialYr(String financialYr) {
		this.financialYr = financialYr;
	}

	public String getEmplist() {
		return emplist;
	}

	public void setEmplist(String emplist) {
		this.emplist = emplist;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSubDept() {
		return subDept;
	}

	public void setSubDept(String subDept) {
		this.subDept = subDept;
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

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
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
