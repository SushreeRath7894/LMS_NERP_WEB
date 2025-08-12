package nirmalya.aathithya.webmodule.budget.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FinancialYearWebModel {
	private String financialYearId;
	private String financialYearName;
	private String financialYearStartDate;
	private String financialYearEndDate;
	private String description;
	private String createdBy;
	private String orgName;
	private String orgDivision;
	
	
	
	
	public FinancialYearWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgDivision() {
		return orgDivision;
	}


	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}


	public String getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(String financialYearId) {
		this.financialYearId = financialYearId;
	}
	public String getFinancialYearName() {
		return financialYearName;
	}
	public void setFinancialYearName(String financialYearName) {
		this.financialYearName = financialYearName;
	}
	public String getFinancialYearStartDate() {
		return financialYearStartDate;
	}
	public void setFinancialYearStartDate(String financialYearStartDate) {
		this.financialYearStartDate = financialYearStartDate;
	}
	public String getFinancialYearEndDate() {
		return financialYearEndDate;
	}
	public void setFinancialYearEndDate(String financialYearEndDate) {
		this.financialYearEndDate = financialYearEndDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
