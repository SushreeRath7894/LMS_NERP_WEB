package nirmalya.aathithya.webmodule.gatepass.model;

public class EmployeeAttendance {

	private String employeeID;
	private String department;
	private String mysqlDate;
	private String mysqlDatetime;

	// Default constructor
	public EmployeeAttendance() {
		// Empty constructor
	}

	// Getter and setter methods for employeeID
	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	// Getter and setter methods for department
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	// Getter and setter methods for mysqlDate
	public String getMysqlDate() {
		return mysqlDate;
	}

	public void setMysqlDate(String mysqlDate) {
		this.mysqlDate = mysqlDate;
	}

	// Getter and setter methods for mysqlDatetime
	public String getMysqlDatetime() {
		return mysqlDatetime;
	}

	public void setMysqlDatetime(String mysqlDatetime) {
		this.mysqlDatetime = mysqlDatetime;
	}

	// Override toString() method for debugging purposes
	@Override
	public String toString() {
		return "EmployeeData [employeeID=" + employeeID + ", department=" + department + ", mysqlDate=" + mysqlDate
				+ ", mysqlDatetime=" + mysqlDatetime + "]";
	}

}
