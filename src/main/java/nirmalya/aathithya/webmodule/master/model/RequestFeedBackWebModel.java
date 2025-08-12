package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestFeedBackWebModel {
	
	private String addId;
	private String employeeId;
	private String name;
	private String role;
	private List<String> empList = new ArrayList<String>();
	private List<String> empNameList = new ArrayList<String>();
	private String empName;
	private String createdBy;
	private String createdOn;
	private String messages;
	
	private String empId;
	private String empName1;
	private String empDesc;
	private Boolean empStatus;
	
	
	
	
	public String getAddId() {
		return addId;
	}
	public void setAddId(String addId) {
		this.addId = addId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
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
	
	
	
	public List<String> getEmpList() {
		return empList;
	}
	public void setEmpList(List<String> empList) {
		this.empList = empList;
	}
	public List<String> getEmpNameList() {
		return empNameList;
	}
	public void setEmpNameList(List<String> empNameList) {
		this.empNameList = empNameList;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName1() {
		return empName1;
	}
	public void setEmpName1(String empName1) {
		this.empName1 = empName1;
	}
	public String getEmpDesc() {
		return empDesc;
	}
	public void setEmpDesc(String empDesc) {
		this.empDesc = empDesc;
	}
	public Boolean getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(Boolean empStatus) {
		this.empStatus = empStatus;
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
