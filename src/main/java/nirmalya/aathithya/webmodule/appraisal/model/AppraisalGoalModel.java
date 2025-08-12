package nirmalya.aathithya.webmodule.appraisal.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AppraisalGoalModel {
	
	private String goalId;
	private String goalName;
	private String financialYear;
	private String depGoalId;
	private String depGoalName;
	private String depList;
	private String desigGoalId;
	private String desigList;
	private String desigGoalName;
	private String createdBy;
	private String OrganizationName;
	private String OrganizationDivision;
	
	
	public String getGoalId() {
		return goalId;
	}
	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}
	public String getGoalName() {
		return goalName;
	}
	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getOrganizationName() {
		return OrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}
	public String getOrganizationDivision() {
		return OrganizationDivision;
	}
	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
	}
	
	
	
	public String getDepGoalId() {
		return depGoalId;
	}
	public void setDepGoalId(String depGoalId) {
		this.depGoalId = depGoalId;
	}
	public String getDepGoalName() {
		return depGoalName;
	}
	public void setDepGoalName(String depGoalName) {
		this.depGoalName = depGoalName;
	}
	public String getDepList() {
		return depList;
	}
	public void setDepList(String depList) {
		this.depList = depList;
	}
	
	
	public String getDesigGoalId() {
		return desigGoalId;
	}
	public void setDesigGoalId(String desigGoalId) {
		this.desigGoalId = desigGoalId;
	}
	public String getDesigList() {
		return desigList;
	}
	public void setDesigList(String desigList) {
		this.desigList = desigList;
	}

	public String getDesigGoalName() {
		return desigGoalName;
	}
	public void setDesigGoalName(String desigGoalName) {
		this.desigGoalName = desigGoalName;
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
