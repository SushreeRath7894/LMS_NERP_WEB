package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmpMonthlyWagePlusModel {
	
	private String wagePlusId;
	private String empId;
	private String empName;
	private String fromDate;
	private String toDate;
	private String fyear;
	private String component;
	private String wageamnt;
	
//	private String otherpenamnt;
//	private String davda;
//	private String attBonus;
//	private String specialAllowance;
//	private String ot;
//	private String miscEarning;
//	private String otherallow;
//	private String socy;
//	private String insurance;
	
	private String attBonus;
    private String penalty;
    private String arer;
    private String salAdv;
    private String otherallow;
    private String otherdeduct;
    private String reward;
    
	private String createdBy;
	private String orgName;
	private String orgDivision;
	private String year;
	private String month;
	private String approveStatus;
	
	public EmpMonthlyWagePlusModel() {
		super();
	}
	public String getWagePlusId() {
		return wagePlusId;
	}
	public void setWagePlusId(String wagePlusId) {
		this.wagePlusId = wagePlusId;
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
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getWageamnt() {
		return wageamnt;
	}
	public void setWageamnt(String wageamnt) {
		this.wageamnt = wageamnt;
	}
	public String getAttBonus() {
		return attBonus;
	}
	public void setAttBonus(String attBonus) {
		this.attBonus = attBonus;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public String getArer() {
		return arer;
	}
	public void setArer(String arer) {
		this.arer = arer;
	}
	public String getSalAdv() {
		return salAdv;
	}
	public void setSalAdv(String salAdv) {
		this.salAdv = salAdv;
	}
	public String getOtherallow() {
		return otherallow;
	}
	public void setOtherallow(String otherallow) {
		this.otherallow = otherallow;
	}
	public String getOtherdeduct() {
		return otherdeduct;
	}
	public void setOtherdeduct(String otherdeduct) {
		this.otherdeduct = otherdeduct;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
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