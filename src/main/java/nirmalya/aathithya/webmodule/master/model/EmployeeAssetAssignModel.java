package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeAssetAssignModel {
	
	private String empAssetId;
	private String dept;
	private String subDept;
	private String deptName;
	private String subDeptName;
	private String empId;
	private String name;
	private String assignDate;
	private String Assetid;
	private String AssetName;
	private String createdby;
	private String updatedby;
	private String organization;
	private String orgDivision;
	
	
	public String getEmpAssetId() {
		return empAssetId;
	}
	public void setEmpAssetId(String empAssetId) {
		this.empAssetId = empAssetId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	
	
	public String getAssetid() {
		return Assetid;
	}
	public void setAssetid(String assetid) {
		Assetid = assetid;
	}
	public String getAssetName() {
		return AssetName;
	}
	public void setAssetName(String assetName) {
		AssetName = assetName;
	}
	
	
	
	
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSubDept() {
		return subDept;
	}
	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSubDeptName() {
		return subDeptName;
	}
	public void setSubDeptName(String subDeptName) {
		this.subDeptName = subDeptName;
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
