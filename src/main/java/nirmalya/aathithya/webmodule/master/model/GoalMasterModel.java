package nirmalya.aathithya.webmodule.master.model;

public class GoalMasterModel {
	private String goalIdAuto;
	private String goalId;
	private String goalName;
	private String goalDesc;
	private String bandId;
	private String fromDate;
	private String toDate;
	private String createdBy;
	private String weightage;
	
	
	
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public GoalMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getGoalIdAuto() {
		return goalIdAuto;
	}
	public void setGoalIdAuto(String goalIdAuto) {
		this.goalIdAuto = goalIdAuto;
	}
	public String getGoalId() {
		return goalId;
	}
	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}
	public String getGoalName() {
		return goalName;
	}
	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}
	public String getGoalDesc() {
		return goalDesc;
	}
	public void setGoalDesc(String goalDesc) {
		this.goalDesc = goalDesc;
	}
	public String getBandId() {
		return bandId;
	}
	public void setBandId(String bandId) {
		this.bandId = bandId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
