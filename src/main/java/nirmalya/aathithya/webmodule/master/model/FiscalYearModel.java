package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FiscalYearModel {

	private String yearId;
	private String year;
	private String monthDtls;
	private String createdBy;
	private String endMnth;
	
	public FiscalYearModel() {
		super();
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonthDtls() {
		return monthDtls;
	}

	public void setMonthDtls(String monthDtls) {
		this.monthDtls = monthDtls;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getEndMnth() {
		return endMnth;
	}

	public void setEndMnth(String endMnth) {
		this.endMnth = endMnth;
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
