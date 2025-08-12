package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtraExpenseModel {
	private String expenseId;
	private String budgetCategory;
	private String item;
	private String extraExpense;
	private String orgName;
	private String orgDivision;
	private String createdBy;
	
	
	
	public ExtraExpenseModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}


	public String getBudgetCategory() {
		return budgetCategory;
	}


	public void setBudgetCategory(String budgetCategory) {
		this.budgetCategory = budgetCategory;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public String getExtraExpense() {
		return extraExpense;
	}


	public void setExtraExpense(String extraExpense) {
		this.extraExpense = extraExpense;
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


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
