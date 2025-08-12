package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectPlanningSchedulingWebModel {

	private String projectId;
	private String projectName;
	private String creationDate;
	private String location;
	private String state;
	private String pPin;
	private String pIncharge;
	private String cName;
	private String cAddress;
	private String cState;
	private String cPin;
	private String email;
	private String mobile;
	private String remark;
	private String status;
	private String projectplanId;
	private String proplanid;
	private String projectStatus;
	private String subCategory;
	private String categoryid;
	private String subCategoryid;
	private String slnoId;
	private String priority;
	private String taskName;
	private String startDate;
	private String endDate;
	private String assignedTo;
	private String duration;
	private String predecessors;
	private String notes;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String organizationName;
	private String organizationDivision;
	private String planschid;
	private String plaaningMainId;
	private String planhours;
	private String projectHealth;
	private String category;
	private String parentid;
	private String catlevel;
	private String nodecount;
	private String estimatedPrice;
	private String planningName;
	private String areaAcer;
	

	private String quantity;
	private String unit;
	private String scopeOfWork;
	
	private String quantityPlan;
	private String unitPlan;
	
	private String vendorName;
	private String vendorId;
	
	private String priorityName;
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getProplanid() {
		return proplanid;
	}

	public void setProplanid(String proplanid) {
		this.proplanid = proplanid;
	}

	public String getCatlevel() {
		return catlevel;
	}

	public void setCatlevel(String catlevel) {
		this.catlevel = catlevel;
	}
	
	public String getNodecount() {
		return nodecount;
	}

	public void setNodecount(String nodecount) {
		this.nodecount = nodecount;
	}
	
	private List <PlanningSchedulesubModel> copylist;
	

	public List<PlanningSchedulesubModel> getCopylist() {
		return copylist;
	}

	public void setCopylist(List<PlanningSchedulesubModel> copylist) {
		this.copylist = copylist;
	}

	public ProjectPlanningSchedulingWebModel() {
		super();
		
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

	public String getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(String estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getpPin() {
		return pPin;
	}

	public void setpPin(String pPin) {
		this.pPin = pPin;
	}

	public String getpIncharge() {
		return pIncharge;
	}

	public void setpIncharge(String pIncharge) {
		this.pIncharge = pIncharge;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcAddress() {
		return cAddress;
	}

	public void setcAddress(String cAddress) {
		this.cAddress = cAddress;
	}

	public String getcState() {
		return cState;
	}

	public void setcState(String cState) {
		this.cState = cState;
	}

	public String getcPin() {
		return cPin;
	}

	public void setcPin(String cPin) {
		this.cPin = cPin;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectplanId() {
		return projectplanId;
	}

	public void setProjectplanId(String projectplanId) {
		this.projectplanId = projectplanId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public String getSubCategoryid() {
		return subCategoryid;
	}

	public void setSubCategoryid(String subCategoryid) {
		this.subCategoryid = subCategoryid;
	}

	public String getSlnoId() {
		return slnoId;
	}

	public void setSlnoId(String slnoId) {
		this.slnoId = slnoId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(String predecessors) {
		this.predecessors = predecessors;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return organizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		this.organizationDivision = organizationDivision;
	}

	public String getPlaaningMainId() {
		return plaaningMainId;
	}

	public void setPlaaningMainId(String plaaningMainId) {
		this.plaaningMainId = plaaningMainId;
	}
	
	

	public String getPlanschid() {
		return planschid;
	}

	public void setPlanschid(String planschid) {
		this.planschid = planschid;
	}
	
	public String getPriorityName() {
		return priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getPlanhours() {
		return planhours;
	}

	public void setPlanhours(String planhours) {
		this.planhours = planhours;
	}

	public String getProjectHealth() {
		return projectHealth;
	}

	public void setProjectHealth(String projectHealth) {
		this.projectHealth = projectHealth;
	}

	public String getPlanningName() {
		return planningName;
	}

	public void setPlanningName(String planningName) {
		this.planningName = planningName;
	}

	public String getAreaAcer() {
		return areaAcer;
	}

	public void setAreaAcer(String areaAcer) {
		this.areaAcer = areaAcer;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public String getQuantityPlan() {
		return quantityPlan;
	}

	public void setQuantityPlan(String quantityPlan) {
		this.quantityPlan = quantityPlan;
	}

	public String getUnitPlan() {
		return unitPlan;
	}

	public void setUnitPlan(String unitPlan) {
		this.unitPlan = unitPlan;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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
