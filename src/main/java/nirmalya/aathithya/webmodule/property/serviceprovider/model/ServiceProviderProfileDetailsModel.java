package nirmalya.aathithya.webmodule.property.serviceprovider.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceProviderProfileDetailsModel {


	private String registerationid;

	private String name;

	private String permanentaddress;

	private String communicationaddress;

	private String email;

	private String mobileno;

	private String profileimage;

	





	public String getRegisterationid() {
		return registerationid;
	}



	public void setRegisterationid(String registerationid) {
		this.registerationid = registerationid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPermanentaddress() {
		return permanentaddress;
	}



	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}



	public String getCommunicationaddress() {
		return communicationaddress;
	}



	public void setCommunicationaddress(String communicationaddress) {
		this.communicationaddress = communicationaddress;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getMobileno() {
		return mobileno;
	}



	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}



	public String getProfileimage() {
		return profileimage;
	}



	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
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
