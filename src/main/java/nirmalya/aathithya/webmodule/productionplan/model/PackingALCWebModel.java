package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PackingALCWebModel {
	private String packId;
	private String id;
	private String changeOver;
	private String fromtime;
	private String toTime;
	private String clean;
	private String alcHrs;
	private String fromtime1;
	private String toTime1;
	private String fromtime2;
	private String toTime2;
	private String fromtime3;
	private String toTime3;
	private String jsonData;
	private String jsonData1;
	private String jsonData2;
	private String createdBy;
    private String organization;
	private String orgDivision;

	public String getPackId() {
		return packId;
	}
	public void setPackId(String packId) {
		this.packId = packId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChangeOver() {
		return changeOver;
	}
	public void setChangeOver(String changeOver) {
		this.changeOver = changeOver;
	}
	public String getFromtime() {
		return fromtime;
	}
	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getClean() {
		return clean;
	}
	public void setClean(String clean) {
		this.clean = clean;
	}
	public String getAlcHrs() {
		return alcHrs;
	}
	public void setAlcHrs(String alcHrs) {
		this.alcHrs = alcHrs;
	}
	public String getFromtime1() {
		return fromtime1;
	}
	public void setFromtime1(String fromtime1) {
		this.fromtime1 = fromtime1;
	}
	public String getToTime1() {
		return toTime1;
	}
	public void setToTime1(String toTime1) {
		this.toTime1 = toTime1;
	}
	public String getFromtime2() {
		return fromtime2;
	}
	public void setFromtime2(String fromtime2) {
		this.fromtime2 = fromtime2;
	}
	public String getToTime2() {
		return toTime2;
	}
	public void setToTime2(String toTime2) {
		this.toTime2 = toTime2;
	}
	public String getFromtime3() {
		return fromtime3;
	}
	public void setFromtime3(String fromtime3) {
		this.fromtime3 = fromtime3;
	}
	public String getToTime3() {
		return toTime3;
	}
	public void setToTime3(String toTime3) {
		this.toTime3 = toTime3;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getJsonData1() {
		return jsonData1;
	}
	public void setJsonData1(String jsonData1) {
		this.jsonData1 = jsonData1;
	}
	public String getJsonData2() {
		return jsonData2;
	}
	public void setJsonData2(String jsonData2) {
		this.jsonData2 = jsonData2;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
