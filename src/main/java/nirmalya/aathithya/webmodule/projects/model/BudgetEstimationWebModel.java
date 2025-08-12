package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetEstimationWebModel {

	private String budgetId;
	private String budgetCategory;
	private String item;
	private String unit;
	private String qty;
	private String rate;
	private String nos;
	private String mob;
	private String amount;
	private String projected;
	private String actual;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String OrganizationName;
	private String OrganizationDivision;
	private String cropAmnt;
	private String parentSlNo;

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

	private String projectId;
	private String projectName;
	private String creationDate;
	private String location;
	private String stateId;
	private String pinId;
	private String projectIncharge;
	private String customerName;
	private String customerAddress;
	private String stateId1;
	private String pinId1;
	private String email;
	private String mobile;
	private String remarks;
	private String status;

	private String budgetCategoryId;
	private String itemId;
	private String unitName;
	private String expenseId;
	private List<BudgetEstimationWebModel> vendorList1;

	public String getBudgetCategoryId() {
		return budgetCategoryId;
	}

	public void setBudgetCategoryId(String budgetCategoryId) {
		this.budgetCategoryId = budgetCategoryId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getNos() {
		return nos;
	}

	public void setNos(String nos) {
		this.nos = nos;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProjected() {
		return projected;
	}

	public void setProjected(String projected) {
		this.projected = projected;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getProjectIncharge() {
		return projectIncharge;
	}

	public void setProjectIncharge(String projectIncharge) {
		this.projectIncharge = projectIncharge;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getStateId1() {
		return stateId1;
	}

	public void setStateId1(String stateId1) {
		this.stateId1 = stateId1;
	}

	public String getPinId1() {
		return pinId1;
	}

	public void setPinId1(String pinId1) {
		this.pinId1 = pinId1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<BudgetEstimationWebModel> getVendorList1() {
		return vendorList1;
	}

	public void setVendorList1(List<BudgetEstimationWebModel> vendorList1) {
		this.vendorList1 = vendorList1;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getCropAmnt() {
		return cropAmnt;
	}

	public void setCropAmnt(String cropAmnt) {
		this.cropAmnt = cropAmnt;
	}

	public String getParentSlNo() {
		return parentSlNo;
	}

	public void setParentSlNo(String parentSlNo) {
		this.parentSlNo = parentSlNo;
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
