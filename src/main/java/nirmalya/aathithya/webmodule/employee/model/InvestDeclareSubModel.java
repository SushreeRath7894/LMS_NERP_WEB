package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InvestDeclareSubModel {
	
	private String fromDate;
	private String toDate;
	private String ampount;
	private String actualAmt;
	private String maxPayment;
	private String subheader;
	private String subheaderid;

	
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

	public String getAmpount() {
		return ampount;
	}
	public void setAmpount(String ampount) {
		this.ampount = ampount;
	}
	public String getMaxPayment() {
		return maxPayment;
	}
	public void setMaxPayment(String maxPayment) {
		this.maxPayment = maxPayment;
	}
	
	
	public String getSubheader() {
		return subheader;
	}
	public void setSubheader(String subheader) {
		this.subheader = subheader;
	}
	
	public String getActualAmt() {
		return actualAmt;
	}

	public void setActualAmt(String actualAmt) {
		this.actualAmt = actualAmt;
	}

	public String getSubheaderid() {
		return subheaderid;
	}
	public void setSubheaderid(String subheaderid) {
		this.subheaderid = subheaderid;
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
