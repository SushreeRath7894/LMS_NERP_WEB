package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HrmsEventDashboardModel {

	// About Organization
	private String about;

	// Birthdays
	private String bname;
	private String bdept;
	private String bgreet;

	// Marriage Anniversary
	private String mname1;
	private String mdept1;
	private String mgreet1;

	// Service Anniversary
	private String sname2;
	private String syear2;
	private String sgreet2;
	// Announcement
	private String date;
	private String announcement;
	private String details;

	private String sendMail;

	// Public Holiday
	private String holiday;
	private String fromDate;
	private String toDate;
	private String days;

	// Leave Policy
	private String leave;
	private String leavePolicy;
	private String leaveDetails;
	private String leaveStatus;
	// open position
	private String jobTitle;
	private String jobId;
	private String department;
	private String location;
	private String noOfPosition;

	public HrmsEventDashboardModel() {
		super();
	}

	public String getSendMail() {
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBdept() {
		return bdept;
	}

	public void setBdept(String bdept) {
		this.bdept = bdept;
	}

	public String getBgreet() {
		return bgreet;
	}

	public void setBgreet(String bgreet) {
		this.bgreet = bgreet;
	}

	public String getMname1() {
		return mname1;
	}

	public void setMname1(String mname1) {
		this.mname1 = mname1;
	}

	public String getMdept1() {
		return mdept1;
	}

	public void setMdept1(String mdept1) {
		this.mdept1 = mdept1;
	}

	public String getMgreet1() {
		return mgreet1;
	}

	public void setMgreet1(String mgreet1) {
		this.mgreet1 = mgreet1;
	}

	public String getSname2() {
		return sname2;
	}

	public void setSname2(String sname2) {
		this.sname2 = sname2;
	}

	public String getSyear2() {
		return syear2;
	}

	public void setSyear2(String syear2) {
		this.syear2 = syear2;
	}

	public String getSgreet2() {
		return sgreet2;
	}

	public void setSgreet2(String sgreet2) {
		this.sgreet2 = sgreet2;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
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

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getLeavePolicy() {
		return leavePolicy;
	}

	public void setLeavePolicy(String leavePolicy) {
		this.leavePolicy = leavePolicy;
	}

	public String getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(String leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}



	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNoOfPosition() {
		return noOfPosition;
	}

	public void setNoOfPosition(String noOfPosition) {
		this.noOfPosition = noOfPosition;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
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
