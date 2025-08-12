/**
 * 
 */
package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class BudgetModel {
	
	private String budgetId;
	private String budgetName;
	private String fiscalYear;
	private String budgetPeriod;
	private String incomeAccGroup;
	private String incomeAccGroupId;
	private String expenseAccGroup;
	private String expenseAccGroupId;
	
	private String assetAccGroup;
	private String assetAccGroupId;
	
	private String liabilityAccGroup;
	private String liabilityAccGroupId;
	
	private String equityAccGroup;
	private String equityAccGroupId;
	
	private String createdDate;	
	private String createdTime;
	private String createdBy;
	
	
	

	public String getBudgetId() {
		return budgetId;
	}




	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}




	public String getBudgetName() {
		return budgetName;
	}




	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}




	public String getFiscalYear() {
		return fiscalYear;
	}




	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}




	public String getBudgetPeriod() {
		return budgetPeriod;
	}




	public void setBudgetPeriod(String budgetPeriod) {
		this.budgetPeriod = budgetPeriod;
	}




	public String getIncomeAccGroup() {
		return incomeAccGroup;
	}




	public void setIncomeAccGroup(String incomeAccGroup) {
		this.incomeAccGroup = incomeAccGroup;
	}




	public String getIncomeAccGroupId() {
		return incomeAccGroupId;
	}




	public void setIncomeAccGroupId(String incomeAccGroupId) {
		this.incomeAccGroupId = incomeAccGroupId;
	}




	public String getExpenseAccGroup() {
		return expenseAccGroup;
	}




	public void setExpenseAccGroup(String expenseAccGroup) {
		this.expenseAccGroup = expenseAccGroup;
	}




	public String getExpenseAccGroupId() {
		return expenseAccGroupId;
	}




	public void setExpenseAccGroupId(String expenseAccGroupId) {
		this.expenseAccGroupId = expenseAccGroupId;
	}




	public String getAssetAccGroup() {
		return assetAccGroup;
	}




	public void setAssetAccGroup(String assetAccGroup) {
		this.assetAccGroup = assetAccGroup;
	}




	public String getAssetAccGroupId() {
		return assetAccGroupId;
	}




	public void setAssetAccGroupId(String assetAccGroupId) {
		this.assetAccGroupId = assetAccGroupId;
	}




	public String getLiabilityAccGroup() {
		return liabilityAccGroup;
	}




	public void setLiabilityAccGroup(String liabilityAccGroup) {
		this.liabilityAccGroup = liabilityAccGroup;
	}




	public String getLiabilityAccGroupId() {
		return liabilityAccGroupId;
	}




	public void setLiabilityAccGroupId(String liabilityAccGroupId) {
		this.liabilityAccGroupId = liabilityAccGroupId;
	}




	public String getEquityAccGroup() {
		return equityAccGroup;
	}




	public void setEquityAccGroup(String equityAccGroup) {
		this.equityAccGroup = equityAccGroup;
	}




	public String getEquityAccGroupId() {
		return equityAccGroupId;
	}




	public void setEquityAccGroupId(String equityAccGroupId) {
		this.equityAccGroupId = equityAccGroupId;
	}




	public String getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}




	public String getCreatedTime() {
		return createdTime;
	}




	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
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
