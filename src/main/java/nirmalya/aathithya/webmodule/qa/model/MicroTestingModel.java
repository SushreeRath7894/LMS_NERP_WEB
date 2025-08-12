package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MicroTestingModel {
	private String mictotestingId;
	private String monthYear;
	private String equipmentName;
	private String equipmentId;
	private String maximunAllowedTime;
	private String date;
	private String lightOn;
	private String lightOff;
	private String refNo;
	private String issueNo;
	private String dateOfIssue;
	private String workingHrs;
	private String workingHrsTotal;
	private String sign;
	private String organization;
	private String orgDivision;
	private String createdBy;
	
	private String month;
	private String year;
	
	
	
	
	public MicroTestingModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getMictotestingId() {
		return mictotestingId;
	}

	public void setMictotestingId(String mictotestingId) {
		this.mictotestingId = mictotestingId;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getMaximunAllowedTime() {
		return maximunAllowedTime;
	}

	public void setMaximunAllowedTime(String maximunAllowedTime) {
		this.maximunAllowedTime = maximunAllowedTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLightOn() {
		return lightOn;
	}

	public void setLightOn(String lightOn) {
		this.lightOn = lightOn;
	}

	public String getLightOff() {
		return lightOff;
	}

	public void setLightOff(String lightOff) {
		this.lightOff = lightOff;
	}

	public String getWorkingHrs() {
		return workingHrs;
	}

	public void setWorkingHrs(String workingHrs) {
		this.workingHrs = workingHrs;
	}

	public String getWorkingHrsTotal() {
		return workingHrsTotal;
	}

	public void setWorkingHrsTotal(String workingHrsTotal) {
		this.workingHrsTotal = workingHrsTotal;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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
