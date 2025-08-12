package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeReportsModel {
	private String employeeId;
	private String employeeName;
	private String gender;
	private String dob;
	private String bloodGroup;
	private String maritialStatus;
	private String nationality;
	private String fatherName;
	private String motherName;
	private String mobile;
	private String personalMail;
	private String workMail;
	private String personalAddress;
	private String permanentAddress;
	private String panNo;
	private String pfNo;
	private String sicNo;
	private String aadharNo;
	private String department;
	private String subDepartment;
	private String designation;
	private String manager;
	
	private String overTimeDate;
	private String overTimeStart;
	private String overTimeEnd;
	private String overTimeDuration;
	private String assignedBy;
	private String date;
	private String punchInTime;
	private String punchOutTime;
	private String punchInlocation;
	private String punchOutlocation;
	private String totalTime; 

	private String empExit;
	private String empName;
	private String empDesg;
	private String resgDate;
	private String salary;
	private String empBonous;
	private String empNotice;
	private String empRec;
	private String resgReas;
	private String resgTo;
	private String resgCc;
	private String resgSub;
 
	public EmployeeReportsModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}

	public String getWorkMail() {
		return workMail;
	}

	public void setWorkMail(String workMail) {
		this.workMail = workMail;
	}

	public String getPersonalAddress() {
		return personalAddress;
	}

	public void setPersonalAddress(String personalAddress) {
		this.personalAddress = personalAddress;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPfNo() {
		return pfNo;
	}

	public void setPfNo(String pfNo) {
		this.pfNo = pfNo;
	}

	public String getSicNo() {
		return sicNo;
	}

	public void setSicNo(String sicNo) {
		this.sicNo = sicNo;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getEmpExit() {
		return empExit;
	}

	public void setEmpExit(String empExit) {
		this.empExit = empExit;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDesg() {
		return empDesg;
	}

	public void setEmpDesg(String empDesg) {
		this.empDesg = empDesg;
	}

	public String getResgDate() {
		return resgDate;
	}

	public void setResgDate(String resgDate) {
		this.resgDate = resgDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEmpBonous() {
		return empBonous;
	}

	public void setEmpBonous(String empBonous) {
		this.empBonous = empBonous;
	}

	public String getEmpNotice() {
		return empNotice;
	}

	public void setEmpNotice(String empNotice) {
		this.empNotice = empNotice;
	}

	public String getEmpRec() {
		return empRec;
	}

	public void setEmpRec(String empRec) {
		this.empRec = empRec;
	}

	public String getResgReas() {
		return resgReas;
	}

	public void setResgReas(String resgReas) {
		this.resgReas = resgReas;
	}

	public String getResgTo() {
		return resgTo;
	}

	public void setResgTo(String resgTo) {
		this.resgTo = resgTo;
	}

	public String getResgCc() {
		return resgCc;
	}

	public void setResgCc(String resgCc) {
		this.resgCc = resgCc;
	}

	public String getResgSub() {
		return resgSub;
	}

	public void setResgSub(String resgSub) {
		this.resgSub = resgSub;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPunchInTime() {
		return punchInTime;
	}

	public void setPunchInTime(String punchInTime) {
		this.punchInTime = punchInTime;
	}

	public String getPunchOutTime() {
		return punchOutTime;
	}

	public void setPunchOutTime(String punchOutTime) {
		this.punchOutTime = punchOutTime;
	}

	public String getPunchInlocation() {
		return punchInlocation;
	}

	public void setPunchInlocation(String punchInlocation) {
		this.punchInlocation = punchInlocation;
	}

	public String getPunchOutlocation() {
		return punchOutlocation;
	}

	public void setPunchOutlocation(String punchOutlocation) {
		this.punchOutlocation = punchOutlocation;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getOverTimeDate() {
		return overTimeDate;
	}

	public void setOverTimeDate(String overTimeDate) {
		this.overTimeDate = overTimeDate;
	}

	public String getOverTimeStart() {
		return overTimeStart;
	}

	public void setOverTimeStart(String overTimeStart) {
		this.overTimeStart = overTimeStart;
	}

	public String getOverTimeEnd() {
		return overTimeEnd;
	}

	public void setOverTimeEnd(String overTimeEnd) {
		this.overTimeEnd = overTimeEnd;
	}

	public String getOverTimeDuration() {
		return overTimeDuration;
	}

	public void setOverTimeDuration(String overTimeDuration) {
		this.overTimeDuration = overTimeDuration;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
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
