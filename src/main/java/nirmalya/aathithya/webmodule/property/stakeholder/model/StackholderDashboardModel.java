package nirmalya.aathithya.webmodule.property.stakeholder.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StackholderDashboardModel {
	private String propertytype;
	private String count;
	private String district;
	
	private String latefee;

	private String rentfee;
	
	private String totalos;
	
	private String mainexpenses;

	
	
	
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


	public String getMainexpenses() {
		return mainexpenses;
	}


	public void setMainexpenses(String mainexpenses) {
		this.mainexpenses = mainexpenses;
	}


	public String getPropertytype() {
		return propertytype;
	}

	public void setPropertytype(String propertytype) {
		this.propertytype = propertytype;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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
