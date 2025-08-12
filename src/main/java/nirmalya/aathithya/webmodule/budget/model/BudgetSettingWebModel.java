package nirmalya.aathithya.webmodule.budget.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetSettingWebModel {
	
	private String budgetSetId;
	private String currentYear;
	private String lastYear;
	private String secondlastYear;
	
	public BudgetSettingWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getBudgetSetId() {
		return budgetSetId;
	}

	public void setBudgetSetId(String budgetSetId) {
		this.budgetSetId = budgetSetId;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getLastYear() {
		return lastYear;
	}


	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getSecondlastYear() {
		return secondlastYear;
	}

	public void setSecondlastYear(String secondlastYear) {
		this.secondlastYear = secondlastYear;
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
