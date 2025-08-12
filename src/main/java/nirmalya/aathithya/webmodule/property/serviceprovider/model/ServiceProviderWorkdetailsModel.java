package nirmalya.aathithya.webmodule.property.serviceprovider.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceProviderWorkdetailsModel {
	
	private String workid;
	
	private String propertyno;

	private String propertytype;

	private String propertyname;

	private String address;
	
	private String location;

	private String startdate;

	private String endate;
	
	private String area;	

	private String propertyvalue;

	private String monthlyamc;

	private String securitydeposit;

	private String vendorname;

	private String workorder;

	private String vendoremail;
	
	
	
	public String getWorkid() {
		return workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	public String getPropertyno() {
		return propertyno;
	}

	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}

	public String getPropertytype() {
		return propertytype;
	}

	public void setPropertytype(String propertytype) {
		this.propertytype = propertytype;
	}

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEndate() {
		return endate;
	}

	public void setEndate(String endate) {
		this.endate = endate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPropertyvalue() {
		return propertyvalue;
	}

	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}

	public String getMonthlyamc() {
		return monthlyamc;
	}

	public void setMonthlyamc(String monthlyamc) {
		this.monthlyamc = monthlyamc;
	}

	public String getSecuritydeposit() {
		return securitydeposit;
	}

	public void setSecuritydeposit(String securitydeposit) {
		this.securitydeposit = securitydeposit;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getWorkorder() {
		return workorder;
	}

	public void setWorkorder(String workorder) {
		this.workorder = workorder;
	}

	public String getVendoremail() {
		return vendoremail;
	}

	public void setVendoremail(String vendoremail) {
		this.vendoremail = vendoremail;
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
