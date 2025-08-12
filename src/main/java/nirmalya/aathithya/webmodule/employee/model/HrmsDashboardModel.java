package nirmalya.aathithya.webmodule.employee.model;

public class HrmsDashboardModel {
	private String UserId;
    private String Name;
	private String LeaveApplyDate;
	private String ApprovedBy;
	private String ApprovedDate;
	private String Mobile;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLeaveApplyDate() {
		return LeaveApplyDate;
	}
	public void setLeaveApplyDate(String leaveApplyDate) {
		LeaveApplyDate = leaveApplyDate;
	}
	public String getApprovedDate() {
		return ApprovedDate;
	}
	public void setApprovedDate(String approvedDate) {
		ApprovedDate = approvedDate;
	}
	public String getApprovedBy() {
		return ApprovedBy;
	}
	public void setApprovedBy(String approvedBy) {
		ApprovedBy = approvedBy;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	
	
	
	

}
