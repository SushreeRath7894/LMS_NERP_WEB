package nirmalya.aathithya.webmodule.samudyamproduction.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CheckingDetailsModel {
	private String checkingid;
	private String checkingname;
	private String statusid;
	private String status;
	private String remarks;

	public CheckingDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCheckingid() {
		return checkingid;
	}

	public void setCheckingid(String checkingid) {
		this.checkingid = checkingid;
	}

	public String getCheckingname() {
		return checkingname;
	}

	public void setCheckingname(String checkingname) {
		this.checkingname = checkingname;
	}

	public String getStatusid() {
		return statusid;
	}

	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
