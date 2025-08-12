package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LtmrRecordModel {
	private String type;
	private String ltmrId;
	private String month;
	private String dryBulbTemp;
	private String rhId;
	private String refNo;
	private String issueNo;
	private String dateOfIssue;
	private String date;
	private String time;
	private String rhPercent;
	private String wetTemp;
	private String dryTemp;
	private String sign;
	private String organization;
	private String orgDivision;
	private String createdBy;
	private String productTypeField;

	public LtmrRecordModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLtmrId() {
		return ltmrId;
	}

	public void setLtmrId(String ltmrId) {
		this.ltmrId = ltmrId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDryBulbTemp() {
		return dryBulbTemp;
	}

	public void setDryBulbTemp(String dryBulbTemp) {
		this.dryBulbTemp = dryBulbTemp;
	}

	public String getRhId() {
		return rhId;
	}

	public void setRhId(String rhId) {
		this.rhId = rhId;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRhPercent() {
		return rhPercent;
	}

	public void setRhPercent(String rhPercent) {
		this.rhPercent = rhPercent;
	}

	public String getWetTemp() {
		return wetTemp;
	}

	public void setWetTemp(String wetTemp) {
		this.wetTemp = wetTemp;
	}

	public String getDryTemp() {
		return dryTemp;
	}

	public void setDryTemp(String dryTemp) {
		this.dryTemp = dryTemp;
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

	public String getProductTypeField() {
		return productTypeField;
	}

	public void setProductTypeField(String productTypeField) {
		this.productTypeField = productTypeField;
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
