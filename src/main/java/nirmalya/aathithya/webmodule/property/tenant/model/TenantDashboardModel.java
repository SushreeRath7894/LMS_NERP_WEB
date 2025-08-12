package nirmalya.aathithya.webmodule.property.tenant.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TenantDashboardModel {
	
	private String month;

	private String latefee;

	private String rentfee;
	
	private String totalos;
	
	private String notice;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLatefee() {
		return latefee;
	}

	public void setLatefee(String latefee) {
		this.latefee = latefee;
	}

	public String getRentfee() {
		return rentfee;
	}

	public void setRentfee(String rentfee) {
		this.rentfee = rentfee;
	}
	
	public String getTotalos() {
		return totalos;
	}

	public void setTotalos(String totalos) {
		this.totalos = totalos;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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
