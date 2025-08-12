package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ManageEmployeeWorkdetailsModel {

	private String employeeworkId;
	private String employeeId;
	private String startDate;
	private String endDate;
	private String jobTitle;
	private String jobType;
	private String jobTypeid;
	private String department;
	private String departmentid;
	private String subdepartment;
	private String subdepartmentid;
	private String timesheet;
	private String timesheetid;
	private String stafftype;
	
	private String employedBy;

	private String employmentStatus;
	private String employmentStatusid;
	private String degination;
	private String deginationid;
	private String band;
	private String bandid;
	private String manager;
	private String annualCTC;
	private String managerName;
	private String employeeName;
	private String organization;
	private String orgDivision;
	private String qrCode;
	private String workStatus;
	private String createdBy;


	public ManageEmployeeWorkdetailsModel() {
		super();
		// TODO Auto-generated constructor stub

	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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

	public String getEmployeeworkId() {
		return employeeworkId;
	}

	public void setEmployeeworkId(String employeeworkId) {
		this.employeeworkId = employeeworkId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getDegination() {
		return degination;
	}

	public void setDegination(String degination) {
		this.degination = degination;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getAnnualCTC() {
		return annualCTC;
	}

	public void setAnnualCTC(String annualCTC) {
		this.annualCTC = annualCTC;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getTimesheet() {
		return timesheet;
	}

	public String getJobTypeid() {
		return jobTypeid;
	}

	public void setJobTypeid(String jobTypeid) {
		this.jobTypeid = jobTypeid;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getTimesheetid() {
		return timesheetid;
	}

	public void setTimesheetid(String timesheetid) {
		this.timesheetid = timesheetid;
	}

	public String getEmploymentStatusid() {
		return employmentStatusid;
	}

	public void setEmploymentStatusid(String employmentStatusid) {
		this.employmentStatusid = employmentStatusid;
	}

	public void setTimesheet(String timesheet) {
		this.timesheet = timesheet;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getBandid() {
		return bandid;
	}

	public void setBandid(String bandid) {
		this.bandid = bandid;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getDeginationid() {
		return deginationid;
	}

	public void setDeginationid(String deginationid) {
		this.deginationid = deginationid;
	}

	public String getSubdepartment() {
		return subdepartment;
	}

	public void setSubdepartment(String subdepartment) {
		this.subdepartment = subdepartment;
	}

	public String getSubdepartmentid() {
		return subdepartmentid;
	}

	public void setSubdepartmentid(String subdepartmentid) {
		this.subdepartmentid = subdepartmentid;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getStafftype() {
		return stafftype;
	}

	public void setStafftype(String stafftype) {
		this.stafftype = stafftype;
	}

	public String getEmployedBy() {
		return employedBy;
	}

	public void setEmployedBy(String employedBy) {
		this.employedBy = employedBy;
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
