package nirmalya.aathithya.webmodule.property.tenant.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TenantRentLedgerModel {
	
	private String propertyno;
	private String duerent;
	private String totalos;
	private String balanceos;
	private String month;
	private String receivedamount;
	
	public String getPropertyno() {
		return propertyno;
	}
	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}
	public String getDuerent() {
		return duerent;
	}
	public void setDuerent(String duerent) {
		this.duerent = duerent;
	}
	public String getTotalos() {
		return totalos;
	}
	public void setTotalos(String totalos) {
		this.totalos = totalos;
	}
	public String getBalanceos() {
		return balanceos;
	}
	public void setBalanceos(String balanceos) {
		this.balanceos = balanceos;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getReceivedamount() {
		return receivedamount;
	}
	public void setReceivedamount(String receivedamount) {
		this.receivedamount = receivedamount;
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