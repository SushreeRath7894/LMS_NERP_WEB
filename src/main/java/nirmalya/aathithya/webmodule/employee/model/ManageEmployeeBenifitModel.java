package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageEmployeeBenifitModel {
	
	private String ebenifitId;
	private String employeeId;
	private List<String> benefits;
	private String ebenifitName;
	private String Createdby;
	private String Createdon;
	
	
	public ManageEmployeeBenifitModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public List<String> getBenefits() {
		return benefits;
	}


	public void setBenefits(List<String> benefits) {
		this.benefits = benefits;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getEbenifitId() {
		return ebenifitId;
	}


	public void setEbenifitId(String ebenifitId) {
		this.ebenifitId = ebenifitId;
	}


	public String getEbenifitName() {
		return ebenifitName;
	}


	public void setEbenifitName(String ebenifitName) {
		this.ebenifitName = ebenifitName;
	}


	public String getCreatedby() {
		return Createdby;
	}


	public void setCreatedby(String createdby) {
		Createdby = createdby;
	}


	public String getCreatedon() {
		return Createdon;
	}


	public void setCreatedon(String createdon) {
		Createdon = createdon;
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
