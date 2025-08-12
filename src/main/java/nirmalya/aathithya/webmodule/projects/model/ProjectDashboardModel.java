package nirmalya.aathithya.webmodule.projects.model;

public class ProjectDashboardModel {
	private Double high;
	private Double medium;
	private Double low;
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getMedium() {
		return medium;
	}
	public void setMedium(Double medium) {
		this.medium = medium;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	
	private String projectName;
	private String ownerName;
	private String totalEmployee;
	private String budgetHour;
	private String allocatedHour;
	private String budget;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(String totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	public String getBudgetHour() {
		return budgetHour;
	}
	public void setBudgetHour(String budgetHour) {
		this.budgetHour = budgetHour;
	}
	public String getAllocatedHour() {
		return allocatedHour;
	}
	public void setAllocatedHour(String allocatedHour) {
		this.allocatedHour = allocatedHour;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	private String dept;
	private Integer project;
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	
}
