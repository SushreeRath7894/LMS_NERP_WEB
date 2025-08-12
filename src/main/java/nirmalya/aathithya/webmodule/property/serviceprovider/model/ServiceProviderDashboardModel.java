package nirmalya.aathithya.webmodule.property.serviceprovider.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceProviderDashboardModel {
	
	private String month;

	private String billraised;

	private String billcleared;
	
	private String totalos;
	
private String billpending;
	
	private String notice;
	
	
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getBillpending() {
		return billpending;
	}

	public void setBillpending(String billpending) {
		this.billpending = billpending;
	}


	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBillraised() {
		return billraised;
	}

	public void setBillraised(String billraised) {
		this.billraised = billraised;
	}

	public String getBillcleared() {
		return billcleared;
	}

	public void setBillcleared(String billcleared) {
		this.billcleared = billcleared;
	}
	

	public String getTotalos() {
		return totalos;
	}

	public void setTotalos(String totalos) {
		this.totalos = totalos;
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
