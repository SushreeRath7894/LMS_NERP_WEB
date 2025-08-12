package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PayrollMasterModel {
	private String gradesalaryId;
	private String bandName;
	private String salaryComponent;
	private String salaryComponentName;
	private String amount;
	private String calculationType;
	
	private String organization;
	private String orgDivision;
	

	public PayrollMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGradesalaryId() {
		return gradesalaryId;
	}

	public void setGradesalaryId(String gradesalaryId) {
		this.gradesalaryId = gradesalaryId;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public String getSalaryComponent() {
		return salaryComponent;
	}

	public void setSalaryComponent(String salaryComponent) {
		this.salaryComponent = salaryComponent;
	}

	public String getSalaryComponentName() {
		return salaryComponentName;
	}

	public void setSalaryComponentName(String salaryComponentName) {
		this.salaryComponentName = salaryComponentName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
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
