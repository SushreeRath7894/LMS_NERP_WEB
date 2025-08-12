package nirmalya.aathithya.webmodule.property.tenant.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TenantPropertyDetailsModel {
	
	private String propertyno;

	private String propertytype;

	private String propertyname;

	private String address;
	
	private String location;

	private String startdate;

	private String endate;
	
	private String area;	

	private String propertyvalue;

	private String rent;

	private String securitydeposite;

	private String tenantname;

	private String agreementdoc;

	private String tenantemail;

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

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getSecuritydeposite() {
		return securitydeposite;
	}

	public void setSecuritydeposite(String securitydeposite) {
		this.securitydeposite = securitydeposite;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}

	public String getAgreementdoc() {
		return agreementdoc;
	}

	public void setAgreementdoc(String agreementdoc) {
		this.agreementdoc = agreementdoc;
	}

	public String getTenantemail() {
		return tenantemail;
	}

	public void setTenantemail(String tenantemail) {
		this.tenantemail = tenantemail;
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
