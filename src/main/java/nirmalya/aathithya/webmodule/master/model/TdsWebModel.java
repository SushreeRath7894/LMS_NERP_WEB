package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TdsWebModel {

	private String tdsId;
	private String empId;
	private String empName;
	private String fromDate;
	private String toDate;
	private String fyear;
	private String tdsAmnt;
	private String createdBy;
	private String orgName;
	private String orgDivision;
	private String year;
	private String month;
	private String approveStatus;

	public TdsWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTdsId() {
		return tdsId;
	}

	public void setTdsId(String tdsId) {
		this.tdsId = tdsId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public String getFyear() {
		return fyear;
	}

	public void setFyear(String fyear) {
		this.fyear = fyear;
	}

	public String getTdsAmnt() {
		return tdsAmnt;
	}

	public void setTdsAmnt(String tdsAmnt) {
		this.tdsAmnt = tdsAmnt;
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

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
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

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
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
