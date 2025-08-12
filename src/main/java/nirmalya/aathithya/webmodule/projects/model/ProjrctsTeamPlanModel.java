package nirmalya.aathithya.webmodule.projects.model;

public class ProjrctsTeamPlanModel {

	private String projectId;
	private String employeeId;
	private String employeeName;
	private Double hourlyCost;
	private Double billingRate;
	private Double totalHour;
	private String location;
	private String locationName;
	private String chartOfAccount;
	private String chartOfAccountName;
	private String costCenter;
	private String costCenterName;
	private String createdOn;
	private String createdBy;
	private String teamPlanId;
	public String getTeamPlanId() {
		return teamPlanId;
	}

	public void setTeamPlanId(String teamPlanId) {
		this.teamPlanId = teamPlanId;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Double getHourlyCost() {
		return hourlyCost;
	}
	public void setHourlyCost(Double hourlyCost) {
		this.hourlyCost = hourlyCost;
	}
	public Double getBillingRate() {
		return billingRate;
	}
	public void setBillingRate(Double billingRate) {
		this.billingRate = billingRate;
	}
	public Double getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(Double totalHour) {
		this.totalHour = totalHour;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getChartOfAccount() {
		return chartOfAccount;
	}
	public void setChartOfAccount(String chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getChartOfAccountName() {
		return chartOfAccountName;
	}

	public void setChartOfAccountName(String chartOfAccountName) {
		this.chartOfAccountName = chartOfAccountName;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	
}
