package nirmalya.aathithya.webmodule.budget.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssignDeptBudgetModel {
	private String id;
	private String particulars;
	private String madeBudget;
	private String departmentId;
	private String departmentName;
	private String departmentHod;
	private String groupId;
	private String fyId;
	private String fyName;
	private String currencyId;
	private String currencyName;
	private String inflation;
	private String quarter;
	private String createdBy;
	private String lastYearBudget;
	private String thisYearBudget;
	private String changePercent;
	private String remark;
	private String oldBudgetAmnt;
	private String UpdatedBudgetAmnt;
	private String UpdatedDateTime;
	
	private String scndLastYearIncmAmnt;
	private String lastYearIncmAmnt;
	private String thisYearIncmAmnt;
	private String incmChangPrcnt;
	private String scndLastYearExpnsAmnt;
	private String lastYearExpnsAmnt;
	private String thisYearExpnsAmnt;
	private String expnsChangPrcnt;
	private String scndLastYearNetIncmAmnt;
	private String lastYearNetIncmAmnt;
	private String thisYearNetIncmAmnt;
	private String netIncmChangPrcnt;
	
	private String parentId;
	private String parentName;
	private String level;
	private String childAvailable;
	private String groupType;
	
	private String budgetFor;
	private String createdOn;
	private String assignedOrNot;
	private String orgName;
	private String orgDivision;

	
	
	public AssignDeptBudgetModel() {
		super();
		// TODO Auto-generated constructor stub
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




	public String getAssignedOrNot() {
		return assignedOrNot;
	}




	public void setAssignedOrNot(String assignedOrNot) {
		this.assignedOrNot = assignedOrNot;
	}




	public String getBudgetFor() {
		return budgetFor;
	}




	public void setBudgetFor(String budgetFor) {
		this.budgetFor = budgetFor;
	}




	public String getCreatedOn() {
		return createdOn;
	}




	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}




	public String getParentId() {
		return parentId;
	}




	public void setParentId(String parentId) {
		this.parentId = parentId;
	}




	public String getParentName() {
		return parentName;
	}




	public void setParentName(String parentName) {
		this.parentName = parentName;
	}




	public String getLevel() {
		return level;
	}




	public void setLevel(String level) {
		this.level = level;
	}




	




	public String getChildAvailable() {
		return childAvailable;
	}




	public void setChildAvailable(String childAvailable) {
		this.childAvailable = childAvailable;
	}




	public String getGroupType() {
		return groupType;
	}




	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}




	public String getRemark() {
		return remark;
	}




	public String getOldBudgetAmnt() {
		return oldBudgetAmnt;
	}




	public void setOldBudgetAmnt(String oldBudgetAmnt) {
		this.oldBudgetAmnt = oldBudgetAmnt;
	}




	public String getUpdatedBudgetAmnt() {
		return UpdatedBudgetAmnt;
	}




	public void setUpdatedBudgetAmnt(String updatedBudgetAmnt) {
		UpdatedBudgetAmnt = updatedBudgetAmnt;
	}




	public String getUpdatedDateTime() {
		return UpdatedDateTime;
	}




	public void setUpdatedDateTime(String updatedDateTime) {
		UpdatedDateTime = updatedDateTime;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getFyName() {
		return fyName;
	}



	public void setFyName(String fyName) {
		this.fyName = fyName;
	}



	public String getCurrencyId() {
		return currencyId;
	}



	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}



	public String getCurrencyName() {
		return currencyName;
	}



	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}



	public String getInflation() {
		return inflation;
	}



	public void setInflation(String inflation) {
		this.inflation = inflation;
	}



	public String getScndLastYearIncmAmnt() {
		return scndLastYearIncmAmnt;
	}



	public void setScndLastYearIncmAmnt(String scndLastYearIncmAmnt) {
		this.scndLastYearIncmAmnt = scndLastYearIncmAmnt;
	}



	public String getLastYearIncmAmnt() {
		return lastYearIncmAmnt;
	}



	public void setLastYearIncmAmnt(String lastYearIncmAmnt) {
		this.lastYearIncmAmnt = lastYearIncmAmnt;
	}



	public String getThisYearIncmAmnt() {
		return thisYearIncmAmnt;
	}



	public void setThisYearIncmAmnt(String thisYearIncmAmnt) {
		this.thisYearIncmAmnt = thisYearIncmAmnt;
	}



	public String getIncmChangPrcnt() {
		return incmChangPrcnt;
	}



	public void setIncmChangPrcnt(String incmChangPrcnt) {
		this.incmChangPrcnt = incmChangPrcnt;
	}



	public String getScndLastYearExpnsAmnt() {
		return scndLastYearExpnsAmnt;
	}



	public void setScndLastYearExpnsAmnt(String scndLastYearExpnsAmnt) {
		this.scndLastYearExpnsAmnt = scndLastYearExpnsAmnt;
	}



	public String getLastYearExpnsAmnt() {
		return lastYearExpnsAmnt;
	}



	public void setLastYearExpnsAmnt(String lastYearExpnsAmnt) {
		this.lastYearExpnsAmnt = lastYearExpnsAmnt;
	}



	public String getThisYearExpnsAmnt() {
		return thisYearExpnsAmnt;
	}



	public void setThisYearExpnsAmnt(String thisYearExpnsAmnt) {
		this.thisYearExpnsAmnt = thisYearExpnsAmnt;
	}



	public String getExpnsChangPrcnt() {
		return expnsChangPrcnt;
	}



	public void setExpnsChangPrcnt(String expnsChangPrcnt) {
		this.expnsChangPrcnt = expnsChangPrcnt;
	}



	public String getScndLastYearNetIncmAmnt() {
		return scndLastYearNetIncmAmnt;
	}



	public void setScndLastYearNetIncmAmnt(String scndLastYearNetIncmAmnt) {
		this.scndLastYearNetIncmAmnt = scndLastYearNetIncmAmnt;
	}



	public String getLastYearNetIncmAmnt() {
		return lastYearNetIncmAmnt;
	}



	public void setLastYearNetIncmAmnt(String lastYearNetIncmAmnt) {
		this.lastYearNetIncmAmnt = lastYearNetIncmAmnt;
	}



	public String getThisYearNetIncmAmnt() {
		return thisYearNetIncmAmnt;
	}



	public void setThisYearNetIncmAmnt(String thisYearNetIncmAmnt) {
		this.thisYearNetIncmAmnt = thisYearNetIncmAmnt;
	}



	public String getNetIncmChangPrcnt() {
		return netIncmChangPrcnt;
	}



	public void setNetIncmChangPrcnt(String netIncmChangPrcnt) {
		this.netIncmChangPrcnt = netIncmChangPrcnt;
	}



	public String getLastYearBudget() {
		return lastYearBudget;
	}



	public void setLastYearBudget(String lastYearBudget) {
		this.lastYearBudget = lastYearBudget;
	}



	public String getThisYearBudget() {
		return thisYearBudget;
	}



	public void setThisYearBudget(String thisYearBudget) {
		this.thisYearBudget = thisYearBudget;
	}




	public String getChangePercent() {
		return changePercent;
	}



	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}



	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getFyId() {
		return fyId;
	}


	public void setFyId(String fyId) {
		this.fyId = fyId;
	}


	public String getQuarter() {
		return quarter;
	}


	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}


	public String getId() {
		return id;
	} 


	public void setId(String id) {
		this.id = id;
	} 


	public String getParticulars() {
		return particulars;
	} 


	public void setParticulars(String particulars) {
		this.particulars = particulars;
	} 

	public String getMadeBudget() {
		return madeBudget;
	} 


	public void setMadeBudget(String madeBudget) {
		this.madeBudget = madeBudget;
	} 
	
	
	public String getDepartmentId() {
		return departmentId;
	} 


	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	



	public String getDepartmentHod() {
		return departmentHod;
	}


	public void setDepartmentHod(String departmentHod) {
		this.departmentHod = departmentHod;
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
