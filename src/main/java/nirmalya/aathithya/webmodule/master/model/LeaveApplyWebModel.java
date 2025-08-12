package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LeaveApplyWebModel {

	private String leaveId;
	private String empID;
	private String empName;
	private String leaveApplyDate;
	private String status;
	private String leaveTypeId;
	private String leaveType;
	private String fromDate;
	private String toDate;
	private Double totalLeave;
	private String reason;
	private Double availableLeave;
	private String createdBy;
	private Integer rowId;
	private String createdOn;
	private String approvedDate;
	private String approvedBy;
	private String rejectDate;
	private String rejectedBy;
	private String approveComment;
	private String organization;
	private String orgDivision;

	private String leaveavailable;
	private String leavenewid;
	private String leaveperiod;

	private String fileName;
	private String documentURL;
	private String documentFileBase;
	private String selfType;
	private String approvedLeaveList;
	private String halfDayCheckbox;

	public LeaveApplyWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLeaveavailable() {
		return leaveavailable;
	}

	public void setLeaveavailable(String leaveavailable) {
		this.leaveavailable = leaveavailable;
	}

	public String getLeavenewid() {
		return leavenewid;
	}

	public void setLeavenewid(String leavenewid) {
		this.leavenewid = leavenewid;
	}

	public String getLeaveperiod() {
		return leaveperiod;
	}

	public void setLeaveperiod(String leaveperiod) {
		this.leaveperiod = leaveperiod;
	}

	public String getRejectDate() {
		return rejectDate;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public String getEmpID() {
		return empID;
	}

	public String getEmpName() {
		return empName;
	}

	public String getLeaveApplyDate() {
		return leaveApplyDate;
	}

	public String getStatus() {
		return status;
	}

	public String getLeaveTypeId() {
		return leaveTypeId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public Double getTotalLeave() {
		return totalLeave;
	}

	public String getReason() {
		return reason;
	}

	public Double getAvailableLeave() {
		return availableLeave;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setLeaveApplyDate(String leaveApplyDate) {
		this.leaveApplyDate = leaveApplyDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public void setTotalLeave(Double totalLeave) {
		this.totalLeave = totalLeave;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setAvailableLeave(Double availableLeave) {
		this.availableLeave = availableLeave;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getDocumentFileBase() {
		return documentFileBase;
	}

	public void setDocumentFileBase(String documentFileBase) {
		this.documentFileBase = documentFileBase;
	}

	public String getSelfType() {
		return selfType;
	}

	public void setSelfType(String selfType) {
		this.selfType = selfType;
	}

	public String getApprovedLeaveList() {
		return approvedLeaveList;
	}

	public void setApprovedLeaveList(String approvedLeaveList) {
		this.approvedLeaveList = approvedLeaveList;
	}

	public String getHalfDayCheckbox() {
		return halfDayCheckbox;
	}

	public void setHalfDayCheckbox(String halfDayCheckbox) {
		this.halfDayCheckbox = halfDayCheckbox;
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
