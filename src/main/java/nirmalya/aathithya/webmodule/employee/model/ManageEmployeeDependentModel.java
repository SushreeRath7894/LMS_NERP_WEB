package nirmalya.aathithya.webmodule.employee.model;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;


public class ManageEmployeeDependentModel {
	
	private String dependentId;
	private String employeeId;
	
	
	private String dependentType;
	private String dfirstName;
	private String dlastName;
	private String depdob;
	private String gender;
	private String genderid;
	private String drelationship;
	private String relationshipName;
	private String dependentTypeName;
	private String organization;
	private String orgDivision;

	
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


	public String getRelationshipName() {
		return relationshipName;
	}


	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}


	public String getDependentTypeName() {
		return dependentTypeName;
	}


	public void setDependentTypeName(String dependentTypeName) {
		this.dependentTypeName = dependentTypeName;
	}


	private String createdBy;
	
	
	public ManageEmployeeDependentModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getDependentId() {
		return dependentId;
	}


	public void setDependentId(String dependentId) {
		this.dependentId = dependentId;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getDependentType() {
		return dependentType;
	}


	public void setDependentType(String dependentType) {
		this.dependentType = dependentType;
	}


	public String getDfirstName() {
		return dfirstName;
	}


	public void setDfirstName(String dfirstName) {
		this.dfirstName = dfirstName;
	}


	public String getDlastName() {
		return dlastName;
	}


	public void setDlastName(String dlastName) {
		this.dlastName = dlastName;
	}


	public String getDepdob() {
		return depdob;
	}


	public void setDepdob(String depdob) {
		this.depdob = depdob;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getDrelationship() {
		return drelationship;
	}


	public void setDrelationship(String drelationship) {
		this.drelationship = drelationship;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getGenderid() {
		return genderid;
	}


	public void setGenderid(String genderid) {
		this.genderid = genderid;
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


