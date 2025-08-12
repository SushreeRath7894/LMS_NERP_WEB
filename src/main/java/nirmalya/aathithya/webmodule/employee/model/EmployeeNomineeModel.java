package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeNomineeModel {
	private String nomineId;
	private String employeeId;
	private String insuredName;
	private String dateOfBirth;
	private String age;
	private String depRelation;
	
	private String createdBy;
	
	private String organization;
	private String orgDivision;
	
	private String insuranceId;
	private String insuranceTypeId;
	private String insuranceName;
	private String insuranceProvider;
	private String insuredAmount;
	private String fDate;
	private String tDate;
	

	

	public EmployeeNomineeModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public String getNomineId() {
		return nomineId;
	}





	public void setNomineId(String nomineId) {
		this.nomineId = nomineId;
	}





	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getInsuredName() {
		return insuredName;
	}


	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getDepRelation() {
		return depRelation;
	}


	public void setDepRelation(String depRelation) {
		this.depRelation = depRelation;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	
	

	public String getInsuranceId() {
		return insuranceId;
	}


	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}


	public String getInsuranceName() {
		return insuranceName;
	}


	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}


	public String getInsuranceProvider() {
		return insuranceProvider;
	}


	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}


	public String getInsuredAmount() {
		return insuredAmount;
	}


	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}


	public String getfDate() {
		return fDate;
	}


	public void setfDate(String fDate) {
		this.fDate = fDate;
	}


	public String gettDate() {
		return tDate;
	}


	public void settDate(String tDate) {
		this.tDate = tDate;
	}


	public String getInsuranceTypeId() {
		return insuranceTypeId;
	}


	public void setInsuranceTypeId(String insuranceTypeId) {
		this.insuranceTypeId = insuranceTypeId;
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
