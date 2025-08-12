package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetEstimationWebSubModel {

	private String projectId;
	private String budgetCategory;
	private String item;
	private List<BudgetEstimationWebModel> addData;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public List<BudgetEstimationWebModel> getAddData() {
		return addData;
	}

	public void setAddData(List<BudgetEstimationWebModel> addData) {
		this.addData = addData;
	}

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
