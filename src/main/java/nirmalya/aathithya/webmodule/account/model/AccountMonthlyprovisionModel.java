package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountMonthlyprovisionModel {
	private String provisionId;
	private String categoryId;
	private String categoryName;
	private String projectcost;
	private String annualcost;
	private String difference;
	private String year;
	private String month;
	public AccountMonthlyprovisionModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getProvisionId() {
		return provisionId;
	}


	public void setProvisionId(String provisionId) {
		this.provisionId = provisionId;
	}


	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getProjectcost() {
		return projectcost;
	}


	public void setProjectcost(String projectcost) {
		this.projectcost = projectcost;
	}


	public String getAnnualcost() {
		return annualcost;
	}


	public void setAnnualcost(String annualcost) {
		this.annualcost = annualcost;
	}


	public String getDifference() {
		return difference;
	}


	public void setDifference(String difference) {
		this.difference = difference;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
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
